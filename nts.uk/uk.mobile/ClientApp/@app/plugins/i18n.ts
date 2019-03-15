import { Vue, VueConstructor } from '@app/provider';

const resources: {
    [lang: string]: {
        [key: string]: string;
    }
} = {
    jp: {
        'jp': '日本',
        'vi': 'Tiếng Việt',
        'app_name': '勤次郎'
    },
    vi: {
        'jp': '日本',
        'vi': 'Tiếng Việt',
        'app_name': 'UK Mobile'
    }
}, language = new Vue({
    data: {
        current: 'jp'
    },
    methods: {
        change: function (lang: string) {
            this.current = lang;

            localStorage.setItem('lang', lang);
        }
    }
}), LanguageBar = {
    template: `<template v-if="button">
            <div class="dropdown">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                    {{current | i18n}} <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li class="dropdown-item" v-for="lg in languages" v-on:click="change(lg)"><a>{{lg | i18n}}</a></li>
                </ul>
            </div>
        </template>
        <template v-else>
            <li class="nav-item dropdown">
                <a class="nav-item nav-link dropdown-toggle mr-md-2">{{current | i18n}}</a>
                <div class="dropdown-menu dropdown-menu-right">
                    <a class="dropdown-item" v-for="lg in languages" v-on:click="change(lg)">{{lg | i18n}}</a>
                </div>
            </li>
        </template>`,
    prop: ['button'],
    data: function () {
        return {
            button: false
        }
    },
    methods: {
        change: language.change
    },
    computed: {
        current: () => language.current,
        languages: () => Object.keys(resources)
    }
}, i18n = {
    install(vue: VueConstructor<Vue>, lang: string) {
        language.current = lang || localStorage.getItem('lang') || 'jp';

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
