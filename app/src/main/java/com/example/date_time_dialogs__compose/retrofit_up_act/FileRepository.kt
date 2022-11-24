package com.example.date_time_dialogs__compose.retrofit_up_act

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class FileRepository {

    suspend fun uploadImage(file: File): Boolean {
        return try {
            FileApi.instance.uploadImage(
                image = MultipartBody.Part
                    .createFormData(
                        name = "image",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
            )
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } catch (e: HttpException) {
            e.printStackTrace()
            false
        }
    }
}