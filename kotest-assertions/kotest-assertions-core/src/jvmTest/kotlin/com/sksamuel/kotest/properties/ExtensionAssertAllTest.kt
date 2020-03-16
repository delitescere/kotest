package com.sksamuel.kotest.properties

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.lt
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldHaveSameLengthAs
import io.kotest.properties.assertAll
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

fun reverse(a: String): String = a.reversed()
fun concat(a: String, b: String): String = a + b

class ExtensionAssertAllTest : StringSpec({

  "reverse should maintain string length" {
    ::reverse.assertAll { input, output ->
      input.shouldHaveSameLengthAs(output)
    }
  }

  "KFunction1 should report errors" {
    shouldThrow<AssertionError> {
      ::reverse.assertAll { input, _ ->
        input.shouldHaveSameLengthAs("qwqew")
      }
    }.message shouldBe """Property failed for
Arg 0: <empty string>
after 1 attempts
Caused by: <empty string> should have the same length as "qwqew""""
  }

  "concat should have consistent lengths" {
    ::concat.assertAll { a, b, output ->
      output.shouldHaveLength(a.length + b.length)
    }
  }
  "!KFunction2 should report errors" {
    shouldThrow<AssertionError> {
      ::concat.assertAll { _, _, output ->
        output.length shouldBe lt(5)
      }
    }.message shouldBe """Property failed for
Arg 0: <empty string>
Arg 1: "aaaaa" (shrunk from
abc
123
)
after 3 attempts
Caused by: 9 should be < 5"""
  }
})
