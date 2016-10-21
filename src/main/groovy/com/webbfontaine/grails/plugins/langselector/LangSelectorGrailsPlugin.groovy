package com.webbfontaine.grails.plugins.langselector

import grails.plugins.*

class LangSelectorGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.1 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = 'Language Selector using Grails i18n support.' // Headline display name of the plugin
    def author = 'Maximiliano Felix'
    def authorEmail = 'maxi.felix@gmail.com'
    def description = '''\\
Simple to use tag, lets you change language of your application. It is based in i18n support provided by Grails.
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "https://github.com/webbfontaine/langSelector/"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "Webbfontaine", url: "http://www.webbfontaine.com/" ]

    // Any additional developers beyond the author specified above.
    def developers = [ [ name: "Tarik Chrouki", email: "tarik.chrouki@webbfontaine.com" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [system: 'GitHub', url: 'https://github.com/Webbfontaine/langSelector/issues']


    // Online location of the plugin's browseable source code.
    def scm = [url: 'https://github.com/Webbfontaine/langSelector']

}
