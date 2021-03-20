package com.develop.rs_school.thecatapi

import kotlin.test.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TestSpec : Spek({
    describe("Get filename from https://cdn2.thecatapi.com/images/2nk.jpg") {
        it("result should be 2nk") {
            assertEquals(getFilenameFromUrl("https://cdn2.thecatapi.com/images/2nk.jpg"), "2nk")
        }
    }
})
