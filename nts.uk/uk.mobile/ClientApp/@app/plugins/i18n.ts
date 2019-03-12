import { Vue, VueConstructor } from '@app/provider';

const resources: {
    [lang: string]: {
        [key: string]: string;
    }
} = {
    en: {
        'en': 'English',
        'jp': 'Japan',
        'vi': 'Tiếng Việt'
    },
    vi: {
        'en': 'English',
        'jp': 'Japan',
        'vi': 'Tiếng Việt'
    },
    jp: {
        'en': 'English',
        'jp': 'Japan',
        'vi': 'Tiếng Việt'
    }
}, language = new Vue({
    data: {
        current: 'vi'
    },
    methods: {
        change: function (lang: string) {
            this.current = lang;

            localStorage.setItem('lang', lang);
        }
    }
}), LanguageBar = {
    template: `<div class="dropdown">
        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
            {{$i18n(current)}} <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
            <li class="dropdown-item" v-for="lg in languages" v-on:click="change(lg)"><a>{{$i18n(lg)}}</a></li>
        </ul>
    </div>`,
    methods: {
        change: language.change
    },
    computed: {
        current: () => language.current,
        languages: () => Object.keys(resources)
    }
}, i18n = {
    install(vue: VueConstructor<Vue>, lang: string) {
        language.current = lang || localStorage.getItem('lang') || 'en';

        vue.filter('i18n', getText);
        vue.prototype.$i18n = getText;
    }
}, getText: any = (resource: string, params?: { [key: string]: string }) => {
    let lng = language.current,
        i18lang = resources[lng],
        groups: { [key: string]: string } = params || {};

    [].slice.call(resource.match(/#{.+}/g) || [])
        .map((match: string) => match.replace(/[\#\{\}]/g, ''))
        .forEach((key: string) => groups[key] = key);

    return ((i18lang[resource.replace(/(^#|#{.+})/, '').trim()] || resource)
        .replace(/#{.+}/g, (match: string) => {
            let key = match.replace(/[\#\{\}]/g, '');

            return getText((groups[key] || key || '').replace(/^#/, ''), groups);
        }) || resource).toString();
};

export { i18n, language, resources, LanguageBar };
