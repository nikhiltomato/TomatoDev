package com.wework.zombiedev.api

import com.wework.zombiedev.Resource
import com.wework.zombiedev.data.Status

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceTest {

    @Test
    fun exception() {
        val exception = Exception("test")
        val apiResponse = Resource.error(exception.message!!, exception)
        Assert.assertEquals("test", apiResponse.error)
        Assert.assertEquals(Status.ERROR, apiResponse.status)
    }

    @Test
    fun success() {
        val resource = Resource.success("test")
        Assert.assertEquals("test", resource.data)
        Assert.assertEquals(Status.SUCCESS, resource.status)
    }
}
