package grails.plugin.langSelector

import grails.test.mixin.TestFor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(LangSelectorTagLib)
class LangSelectorSpecSpec extends Specification {
    static final LOCALE_FROM_RESOLVER = new Locale('xx')
    static final ANY_LOCALE_CODE = 'yy'
    static final LOCALE_FROM_ATTR = new Locale('zz')

    @Unroll
    void "selector(): #sessionLocale #langs #defaultLang #url"() {
        given:
        session[SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME] = sessionLocale
        InputStream inputStream = getClass().getResourceAsStream("selector-${num}.html")
        String expectHtml = removeWhitespace(inputStream.text)
        String generatedHtml = removeWhitespace(tagLib.selector(langs: langs, default: defaultLang, url: url).toString())
        expect:
        generatedHtml == expectHtml
        where:
        num | sessionLocale  | langs       | defaultLang | url
        0   | null           | 'en, pt_BR' | null        | null
        1   | null           | 'en, pt_BR' | null        | 'libro/list'
        2   | null           | 'en, pt_BR' | 'pt'        | 'libro/list'
        3   | Locale.ENGLISH | 'en, pt_BR' | 'pt'        | 'libro/list'
    }

    String removeWhitespace(String str) {
        return str.replace(' ', '').replace('\n', '').replace('\r', '')
    }

    @Unroll
    void "parseLocale(): #localeCode #locale"() {
        expect:
        tagLib.parseLocale(localeCode) == locale
        where:
        localeCode | locale
        'es'       | new Locale('es')
        'en_US'    | new Locale('en', 'US')
        'en-US'    | new Locale('en', 'US')
        'en-us'    | new Locale('en', 'US')
        'invalid!' | null
        null       | null
    }

    @Unroll
    void "selectLang(): #sessionLocale #defaultLocale #locale"() {
        given:
        session[SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME] = sessionLocale
        expect:
        tagLib.selectLang(defaultLocale) == locale
        where:
        sessionLocale        | defaultLocale             | locale
        LOCALE_FROM_RESOLVER | ANY_LOCALE_CODE           | LOCALE_FROM_RESOLVER
        null                 | LOCALE_FROM_ATTR.language | LOCALE_FROM_ATTR
        null                 | null                      | Locale.default
    }

    @Unroll
    void "generateUrl(): #url #urlWithParam"() {
        expect:
        tagLib.generateUrl(url) == urlWithParam
        where:
        url                       | urlWithParam
        ''                        | '?'
        null                      | '?'
        '/libro'                  | '/libro?'
        '/libro?offset=1'         | '/libro?offset=1&'
        '/libro?offset=1&'        | '/libro?offset=1&'
        '/libro?lang=uk'          | '/libro?'
        '/libro?lang=uk&'         | '/libro?'
        '/libro?lang=uk&offset=1' | '/libro?offset=1&'
    }
}
