package org.example.template

import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class, JsonExtension::class)
internal class EventBusExtensionTest {

  @Test
  internal fun should_send_and_receive_a_message(vertx: Vertx, test: VertxTestContext) {
    val eventBus = vertx.eventBus()

    eventBus.on(Message::class) {
      assertThat(it).isEqualTo(Message("ciao"))
      test.completeNow()
    }

    eventBus.emit(Message("ciao"))
  }

  data class Message(val text: String)
}
