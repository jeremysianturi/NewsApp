package com.example.core.utils

import java.io.IOException

class ApiException(message: String) : IOException(message)
class ApiExceptionCode(code: String) : IOException(code)
class NoInternetException(message: String) : IOException(message)