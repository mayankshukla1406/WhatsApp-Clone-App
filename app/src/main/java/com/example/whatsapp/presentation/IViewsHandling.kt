package com.example.whatsapp.presentation

interface IViewsHandling {

    fun hideProgressBar() {}
    fun showProgressBar() {}
    fun showError(error : String) {}
    fun changeViewsVisibility() {}
    fun showHomePage() {}
    fun dismissOtpBottomSheetDialogFragment() {}
}