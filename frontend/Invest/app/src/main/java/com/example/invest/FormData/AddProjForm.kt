package com.example.innova.FormData

import com.example.charmrides.Validation.ValidationResult

class AddProjForm(
    private var telephone:String,
    private var budget:String,
    private var description:String
) {

    fun validateBudget(): ValidationResult {

        return if(budget.isEmpty()){
            ValidationResult.Empty("Please Enter Budget")
        } else{
            ValidationResult.Valid
        }
    }
    fun validateTelephone():ValidationResult{
        // Regex to validate password with alphaNumerics,and minimum 8 chars
        val telPattern = "^\\d{10}$"
        return if(telephone.isEmpty()){
            ValidationResult.Empty("Please Enter Telephone")
        } else if (!telephone.matches(telPattern.toRegex())) {
            ValidationResult.Invalid("Invalid ex: 0765654332") // Password is invalid
        }else{
            ValidationResult.Valid
        }
    }

    fun validateProjDes(): ValidationResult {
        return when {
            description.isEmpty() -> ValidationResult.Empty("Please Enter Description")
            else -> ValidationResult.Valid
        }
    }
}