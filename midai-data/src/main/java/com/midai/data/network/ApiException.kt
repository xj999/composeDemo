package com.midai.data.network

import java.io.IOException
import java.lang.RuntimeException

open class ApiException(private val mErrorCode: Int, errorMessage: String?) :  IOException(errorMessage)