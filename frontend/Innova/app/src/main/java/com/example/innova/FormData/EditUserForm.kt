package com.example.innova.FormData

import com.example.charmrides.Validation.ValidationResult

class EditUserForm(
    private var name:String,
    private var tel:String,
    private var description:String
) {
    fun validateTelephone():ValidationResult{
        // Regex to validate password with alphaNumerics,and minimum 8 chars
        val telPattern = "^\\d{10}$"
        return if(tel.isEmpty()){
            ValidationResult.Empty("Please Enter Telephone")
        } else if (!tel.matches(telPattern.toRegex())) {
            ValidationResult.Invalid("Invalid ex: 0765654332") // Password is invalid
        }else{
            ValidationResult.Valid
        }
    }
    fun validateName(): ValidationResult {
        return when {
            name.isEmpty() -> ValidationResult.Empty("Please Enter Name")
            else -> ValidationResult.Valid
        }
    }
    fun validateDescription(): ValidationResult {
        return when {
            description.isEmpty() -> ValidationResult.Empty("Please Enter Description")
            else -> ValidationResult.Valid
        }
    }

}