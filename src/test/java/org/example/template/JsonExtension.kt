package org.example.template

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class JsonExtension : BeforeAllCallback, ExtensionContext.Store.CloseableResource {

  override fun close() {
  }

  override fun beforeAll(context: ExtensionContext) {
    loadMapperModules()
  }

}
