package com.example.innobuzz.services

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class WhatsAppService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()
            val className = event.className?.toString()

            if (packageName == "com.whatsapp" && className == "com.whatsapp.HomeActivity") {
                Toast.makeText(this, "WhatsApp launched", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInterrupt() {
        // Not used
    }
}