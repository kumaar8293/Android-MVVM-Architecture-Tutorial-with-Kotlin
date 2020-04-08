package com.mukesh.mvvmarchitecturetutorialkotlin.data.network

import com.mukesh.mvvmarchitecturetutorialkotlin.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

abstract class SafeApiRequest {

    //This function will handle the response from any API request

    //Hence we are making API request we will use suspended function
    /**
     * In the following suspend function which is type ANY ,
     * is taking a suspend function call as a parameter
     * call function doesn't take any parameter
     */

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke() // This will simply call the api and get response

        if (response.isSuccessful) {
            //If response is successful then we will simply return the body
            return response.body()!!
        } else {
            //If response is failed then we will throw an custom exception
            val error = response.errorBody()?.string()

            val errorMessage = StringBuilder()
            error?.let {
                //If error is not null then we will through the exception message and code
                //If we did not get the error in JSON format then we will through error code

                try {
                    //Converting error->it (in string) to JSONObject and if it throws any exception
                    // means its not in JSONObject format then will go to catch block
                    val message = JSONObject(it).getString("message")
                    errorMessage.append(message)
                } catch (e: JSONException) {
                  //  errorMessage.append("\n")
                }
                errorMessage.append("\nError code ${response.code()}")
            }
            //Once we created our exception object we will through an exception

            throw ApiException(errorMessage.toString())
        }

    }

}