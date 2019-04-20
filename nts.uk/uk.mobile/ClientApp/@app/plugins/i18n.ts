import { obj } from '@app/utils';
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
    watch: {
        pgName(name: string) {
            let title = document.querySelector('head>title') as HTMLElement;

            if (title) {
                title.innerHTML = `${getText('app_name')}: ${getText(name)}`;
            }
        }
    },
    methods: {
        change (lang: string) {
            this.current = lang;

            localStorage.setItem('lang', lang);
        },
        watch (callback: Function) {
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
    },
    refresh() {
        language.current = '';
        language.current = localStorage.getItem('lang') || 'jp';
    }
}, LanguageBar = {
    template: `
        <li class="nav-item dropdown">
            <a class="nav-item nav-link dropdown-toggle mr-md-2">{{current | i18n}}</a>
            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" v-for="lg in languages" v-on:click="change(lg)">{{lg | i18n}}</a>
            </div>
        </li>`,
    methods: {
        change(lg: string) {
            language.change(lg);

            this.$emit('change', lg);
        }
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
}, getText: any = (resource: string, params?: string | string[] | { [key: string]: string }) => {
    let lng = Language.current,
        i18lang = resources[lng],
        groups: { [key: string]: string } = {};

    if (!!params) {
        if (!obj.isString(params)) {
            obj.extend(groups, params);
        } else {
            obj.extend(groups, { '0': params });
        }
    }

    if (resource) {
        [].slice.call(resource.match(/(#{.+})|({#.+})|({\d+})/g) || [])
            .map((match: string) => match.replace(/[\#\{\}]/g, ''))
            .forEach((key: string) => {
                if (!obj.has(groups, key)) {
                    obj.set(groups, key, key);
                }
            });

        return ((i18lang[resource] || resource)
            .replace(/(#{.+})|({#.+})|({\d+})/g, (match: string) => {
                let key = match.replace(/[\#\{\}]/g, '');

                return getText((groups[key] || key), groups);
            }) || resource).toString();
    }

    return '';
};

export { i18n, Language, resources, LanguageBar };
