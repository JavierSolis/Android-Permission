package dev.javiersolis.permission

interface IRequestPermission {
    fun requestPermission(permission: String, rationale: String)
    fun hasPermission(permission: String):Boolean
}