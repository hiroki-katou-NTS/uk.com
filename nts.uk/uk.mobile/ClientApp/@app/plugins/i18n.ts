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
        current: 'jp',
        watchers: [],
        pgName: ''
    },
    methods: {
        change: function (lang: string) {
            this.current = lang;

            localStorage.setItem('lang', lang);
        },
        watch: function (callback: Function) {
            let self = this;
            self.watchers.push(self.$watch('current', (v: string) => {
                callback(v);
            }));
        }
    },
    destroyed() {
        [].slice.call(this.watchers).forEach((w: Function) => w());
    }
}), Language = {
    set pgName(name: string) {
        language.pgName = name;
    },
    get current() {
        return language.current;
    },
    set current(lang: string) {
        language.current = lang;

        localStorage.setItem('lang', lang);
    },
    i18n(resource: string, params?: { [key: string]: string }) {
        return getText(resource, params);
    },
    watch(callback: Function) {
        language.watch(callback);
    }
}, LanguageBar = {
    template: `
        <li class="nav-item dropdown">
            <a class="nav-item nav-link dropdown-toggle mr-md-2">{{current | i18n}}</a>
            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" v-for="lg in languages" v-on:click="change(lg)">{{lg | i18n}}</a>
            </div>
        </li>`,
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

        vue.mixin({
            computed: {
                pgName: {
                    get() {
                        return language.pgName;
                    },
                    set(name: string) {
                        language.pgName = name || '';
                    }
                }
            }
        });
    }
}, getText: any = (resource: string, params?: { [key: string]: string }) => {
    let lng = Language.current,
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

export { i18n, Language, resources, LanguageBar };
