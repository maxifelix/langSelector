<g:set var="locales" value="${(List<Locale>)locales}"/>
<g:set var="selected" value="${(Locale)selected}"/>
<div id="lang_selector" class="lang_selector">
    <g:each in="${locales}" var="locale">
        <a href="${uri + locale.toLanguageTag()}" title="Change language to ${locale.displayName}"  class="lang_link">
            <span class="lang_flag ${locale == selected ? 'opacity_selected' : 'opacity_not_selected'}">
                <img src="${resource(plugin: 'langSelector', dir: 'images/flags/png', file: locale.country.toLowerCase() + '.png')}" border="0">
            </span>
        </a>
    </g:each>
</div>