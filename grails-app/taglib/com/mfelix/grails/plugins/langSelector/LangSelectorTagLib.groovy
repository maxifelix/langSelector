package com.mfelix.grails.plugins.langSelector

import org.apache.commons.lang.LocaleUtils

class LangSelectorTagLib {
    static namespace = 'langs'

    /**
     * Render language selector. Examples:<br/>
     * {@code &lt;langs:selector langs="es, en, en_US, pt_BR, pt, pt_pt"/&gt;}
     * <p/>
     * {@code &lt;langs:selector langs="es, en, en_US, pt_BR, pt, pt_pt" url="${createLink(action: 'list', controller: 'libro', params: [paramun: 123])}"/&gt;}
     * <p/>
     * {@code &lt;langs:selector langs="es, en, en_US, pt_BR, pt, pt_pt" default="es" /&gt;}
     * <p/>
     * The required attribute "langs" tells the plugin which flags to show, if pay attention the values are the ISO 3166-1 alpha-2 code for languages and a countries, also are the same of the suffixes of "message properties" files.
     * <p/>
     * Optionally if you want to redirect always to the same url when changing the language (this is helpful to avoid doing a GET with post data) use the url parameter, and this provided url will be used instead of the actual one.
     * <p/>
     * From version 0.3 you optionally can set the default flag to be highlighted when the user enters for first time in the app or has a new fresh session.
     * <p/>
     * @attr langs REQUIRED Comma separated list of locales that available for selection.
     * @attr default Code of default locale
     * @attr url The url will be used instead of the actual one.
     */
    def selector = { attrs ->
        List<String> localeCodesList = attrs.langs?.toString()?.split(',')?.toList()
        if (!localeCodesList) {
            throw new Exception("Error getting value of required attribute 'langs'. Accepted value for example is: es,en_US,en")
        }
        String defaultLang = attrs.default?.trim()
        String url = attrs.url
        Locale selected = session["org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE"]
        // if not set in session, get it from attrs
        selected = selected ? selected : new Locale(defaultLang)
        // if no default is set get default locale
        selected = selected ? selected : Locale.getDefault()
        if (url == null) {
            url = request.getRequestURI() + '?'
            String query = request.getQueryString() ? request.getQueryString().replace('lang=' + selected.toString(), '') : ''
            if (query != '' && !query.endsWith('&')) {
                query += '&'
            }
            url += query + 'lang='
        } else {
            url += url.contains('?') ? '&lang=' : '?lang='
        }
        Map<String, String> supported = StaticConfig.config
        Map flags = [:]
        localeCodesList.each { String localeCode ->
            String language = localeCode.contains('_') ? localeCode.substring(0, localeCode.indexOf('_')) : localeCode
            String country = localeCode.contains('_') ? localeCode.substring(localeCode.indexOf('_') + 1, localeCode.length()) : supported[language.toLowerCase().trim()]
            if (country) {
                flags[localeCode.trim()] = country.toLowerCase().trim()
            } else {
                log.error "No country flag found for: ${language} please check configuration."
            }
        }
        // distinction selected or default style opacity
        String selectedClass = ''
        String notSelectedClass = 'opacitiy_not_selected'
        out << render(template: '/langSelector/selector', plugin: 'langSelector', model: [flags: flags, selectedClass: selectedClass, notSelectedClass: notSelectedClass, selected: selected, uri: url])
    }

    /** This tag includes the css stylesheet that helps you identify which language is selected */
    def resources = {
        out << """<link rel='stylesheet' href="${resource(plugin: 'langSelector', dir: 'css', file: 'langSelector.css')}" />"""
    }
}
