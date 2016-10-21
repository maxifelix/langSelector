# Grails Language Selector Plugin

[![Build Status](https://travis-ci.org/stokito/langSelector.png?branch=master)](https://travis-ci.org/stokito/langSelector)

This plugin provides a simple to use tag, to display flags for countries of the languages your application supports, that when clicked, changes the language of your application using the i18n support provided by Grails.

Flag icons 16x16 pixels are from [FamFamFam](http://www.famfamfam.com/lab/icons/flags/)

## Usage
Add the next tag to your gsp.

```xml
<langs:selector langs="es, en, en_US, pt_BR, pt, pt_pt"/>
<langs:selector langs="es, en, en_US, pt_BR, pt, pt_pt"
       url="${createLink(action: 'list', controller: 'libro', params: [paramun: 123])}"/>
<langs:selector langs="es, en, en_US, pt_BR, pt, pt_pt" default="es" />
```

The required attribute `langs` tells the plugin which flags to show, if pay attention the values are the ISO 3166-1 alpha-2 code for languages and a countries, also are the same of the suffixes of `messages.properties` files.

Optionally if you want to redirect always to the same url when changing the language (this is helpful to avoid doing a GET with post data) use the url parameter, and this provided url will be used instead of the actual one.

From version 0.3 you optionally can set the default flag to be highlighted when the user enters for first time in the app or has a new fresh session.

This tag includes the css stylesheet that helps you identify which language is selected:
```xml
<langs:resources/>
```



### Difference with `<g:localeSelect/>` tag
You can also use standard Grails [<g:localeSelect/>](http://grails.org/doc/latest/ref/Tags/localeSelect.html).
It shows a combo-box with all known locales and can't show a country flag.
But it my might be useful if you don't want to use this plugin. Here is example:
```gsp
<form method="get">
    <g:localeSelect name="lang" value="${Locale.ENGLISH}"/>
    <input type="submit" value="Change locale"/>
</form>
```

## Configuration.
Optionally you can add this property to the `Config.groovy` to tell the plugin which flag display for the language. By default has the value shown below. Its a map that in the keys are the languages and the values are the countries.
```groovy
grails.plugin.langSelector.langFlags = [
	'es': 'es',
	'en': 'gb',
	'fr': 'fr',
	'da': 'dk',
	'de': 'de',
	'it': 'it',
	'ja': 'jp',
	'nl': 'nl',
	'ru': 'ru',
	'th': 'th',
	'zh': 'cn',
	'pt': 'pt'
]
```

## Tips & tricks
1 . The i18n support its executed in a filter attached to all controllers in your app, so in order to get it working in '/', you have to point it to a controller in the `grails-app/conf/UrlMappings.groovy` file.

Here is an example:
```groovy
'/'(controller: 'oneController', action: 'oneAction')
```

2 . Some versions of Grails when create an app generate the english message properties without the corresponding suffix, this prevents the i18n support to work properly.
You should change the name of the file to get it working, this way: `messages.properties` -> `messages_en.properties`

## Plugin history

### v3.0.0-SNAPSHOT Migration to grails 3, improvment and fix tests




*            - version 0.3.3-wf use asset plugin if available to display images   
*            - version 0.4 <langs:resource/>` tag is deprecated use standard `<r:require module="langSelector"/>` 
* 2011-02-02 - version 0.3 Added 'default' param, to set the default flag to be highlighted.
* 2010-10-20 - version 0.2 Fixed bug in generated url when it had params, added url param
* 2010-09-04 - version 0.1.1 Bug fixed - some missing trim()
* 2010-08-27 - version 0.1 First release
