package com.birthday.kotlin.ui.add

import android.Manifest
import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.birthday.kotlin.BuildConfig
import com.birthday.kotlin.R
import com.birthday.kotlin.databinding.FragmentAddBirthdayBinding
import com.birthday.kotlin.ui.BaseFragment
import com.birthday.kotlin.utils.ImageUtils.createImageFile
import com.birthday.kotlin.utils.ImageUtils.getBitmapFromUri
import com.birthday.kotlin.utils.ImageUtils.rotateImageIfRequired
import com.birthday.kotlin.utils.extention.empty
import com.birthday.kotlin.utils.extention.showDialogWithList
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddBirthdayFragment : BaseFragment(R.layout.fragment_add_birthday) {

    private val binding: FragmentAddBirthdayBinding by viewBinding()
    private val viewModel: AddBirthdayViewModel by viewModels()

    lateinit var currentPhotoPath: String
    private lateinit var profileImageUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupCollectors()
    }

    override fun onResume() {
        super.onResume()
        executeCallsWhenResume()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupViews() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addBtn.setOnClickListener {
            viewModel.addBirthday()
        }

        binding.photoIv.setOnClickListener {
            activity?.showDialogWithList(
                R.string.set_profile_photo,
                resources.getStringArray(R.array.set_profile_photo)
            ) { _, which ->
                when (which) {
                    0 -> {
                        //Check for CAMERA permission
                        checkForPermission()
                    }
                    1 -> {
                        executeImagePicker.launch("image/*")
                    }
                }
            }
        }

        binding.dobEt.doAfterTextChanged {
            it?.let {
                binding.dobEt.setSelection(it.length)
            }
        }

        binding.phoneEt.setOnTouchListener { view, event ->
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.phoneEt.right - binding.phoneEt.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    permissionsBuilder(Manifest.permission.READ_CONTACTS).build().send {
                        if (it.allGranted()) {
                            resultLauncher.launch(null)
                        }
                    }
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }

    override fun executeCallsWhenResume() {

    }

    override fun setupCollectors() {
        collectFlow(viewModel.addResponse) {
            if (it.isSuccess) {
                viewModel.cleanForm()
                showMessage(binding.root, R.string.birthday_added_successfully)
            } else {
                showMessage(binding.root, it.exceptionOrNull().toString())
            }
        }

        collectFlow(viewModel.photo) {
            it?.let {
                binding.photoIv.setImageBitmap(it)
            } ?: kotlin.run {
                binding.photoIv.setImageResource(R.drawable.ic_baseline_person_add_24)
            }
        }
    }

    private fun checkForPermission() {
        permissionsBuilder(Manifest.permission.CAMERA).build().send {
            if (it.allGranted()) {
                val imageFile = createImageFile(requireContext())
                currentPhotoPath = imageFile.absolutePath
                profileImageUri = FileProvider.getUriForFile(
                    requireContext(),
                    "${BuildConfig.APPLICATION_ID}.provider",
                    imageFile
                )
                executeTakePicture.launch(profileImageUri)
            }
        }
    }

    private var executeImagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let { uri ->
                val bitmap = getBitmapFromUri(uri, requireActivity())
                bitmap?.let {
                    viewModel.setPhoto(it)
//                    setUpProfileViewModel.setPictureURL(uri)
                }
            }
        }

    private val executeTakePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                val bitmap = getBitmapFromUri(profileImageUri, requireActivity())
                bitmap?.let {
                    rotateImageIfRequired(it, currentPhotoPath).let {
                        viewModel.setPhoto(it)
                    }
//                    setUpProfileViewModel.setPictureURL(profileImageUri)
                }
            }
        }


    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.PickContact()) { uri ->
            viewModel.setPhoneNumber(retrievePhoneNumber(uri))
        }

    @SuppressLint("Range")
    fun retrievePhoneNumber(uri: Uri): String {
        var phoneNumber = String.empty()

        val phone: Cursor? = activity?.contentResolver?.query(uri, null, null, null, null)
        phone?.let {
            if (it.moveToFirst()) {

                val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                val hasNumber =
                    (it.getInt(it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))

                if (hasNumber > 0) {
                    val phoneCursor: Cursor? = activity?.contentResolver?.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id),
                        null
                    )
                    phoneCursor?.let {
                        if (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(
                                phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                        }
                        phoneCursor.close()
                    }
                }
            }
            it.close()
        }
        return phoneNumber
    }
}