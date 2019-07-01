import { obj, dom, storage } from '@app/utils';
import { Vue, VueConstructor, moment } from '@app/provider';

const style = dom.create('style', {
    type: 'text/css',
    rel: 'stylesheet',
    text: 'body { font-family: "Meiryo UI"; }'
});

const resources: {
    [lang: string]: {
        [key: string]: string;
    }
} = {
    jp: {
        'jp': '日本',
        'vi': 'Tiếng Việt',
        'app_name': '勤次郎',
        'plz_wait': 'お待ちください',
        'cancel': 'キャンセル',
        'remove': '削除',
        'accept': '設定',
        'yes': 'はい',
        'no': 'いいえ',
        'close': '閉じる',
        'warn': 'エラー',
        'info': '情報',
        'error': 'エラー',
        'confirm': '確認',
        'filter_menu': 'Enter keyword for filter menu'
    },
    vi: {
        'jp': '日本',
        'vi': 'Tiếng Việt',
        'app_name': 'UK Mobile',
        'plz_wait': 'Vui lòng chờ trong giây lát...',
        'cancel': 'Hủy bỏ',
        'remove': 'Xóa',
        'accept': 'Chấp nhận',
        'yes': 'Đồng ý',
        'no': 'Không',
        'close': 'Đóng',
        'warn': 'Cảnh báo',
        'info': 'Thông báo',
        'error': 'Cảnh báo lỗi',
        'confirm': 'Xác nhận',
        'filter_menu': 'Nhập ký tự bất kỳ để lọc menu'
    }
};

const language = new Vue({
    data: {
        current: 'jp',
        watchers: [],
        pgName: ''
    },
    watch: {
        current: {
            immediate: true,
            handler(lg: string) {
                if (lg === 'jp') {
                    document.head.appendChild(style);
                    // update locale of moment
                    moment.updateLocale('ja', { weekdays: ['月', '火', '水', '木', '金', '土', '日'] });

                } else if (document.head.contains(style)) {
                    document.head.removeChild(style);
                    // update locale of moment
                    moment.updateLocale(lg, { weekdays: lg !== 'vi' ? [] : ['T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'CN'] });
                }
            }
        },
        pgName(name: string) {
            let title = document.querySelector('head>title') as HTMLElement;

            if (title) {
                title.innerHTML = `${getText('app_name')}: ${getText(name)}`;
            }
        }
    },
    methods: {
        change(lang: string) {
            this.current = lang;

            storage.local.setItem('lang', lang);
        },
        watch(callback: Function) {
            let self = this;
            self.watchers.push(self.$watch('current', (v: string) => {
                callback(v);
            }));
        }
    },
    destroyed() {
        [].slice.call(this.watchers).forEach((w: Function) => w());
    }
});

const Language = {
    set pgName(name: string) {
        language.pgName = name;
    },
    get current() {
        return language.current;
    },
    set current(lang: string) {
        language.current = lang;

        storage.local.setItem('lang', lang);
    },
    i18n(resource: string, params?: { [key: string]: string }) {
        return getText(resource, params);
    },
    watch(callback: Function) {
        language.watch(callback);
    },
    refresh() {
        language.current = '';
        language.current = (storage.local.getItem('lang') as string) || 'jp';
    }
};

const LanguageBar = {
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
};

const i18n = {
    install(vue: VueConstructor<Vue>, lang: string) {
        language.current = lang || (storage.local.getItem('lang') as string) || 'jp';

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
};

const getText: any = (resource: string | number, params?: string | string[] | { [key: string]: string }) => {
    let lng = Language.current,
        i18lang = resources[lng],
        groups: { [key: string]: string } = {};

    if (!!params) {
        if (!obj.isString(params)) {
            obj.extend(groups, params);
        } else {
            obj.extend(groups, { '0': params.toString() });
        }
    }

    // accept numbet raw
    resource = resource.toString();

    if (resource) {
        [].slice.call(resource.match(/(#.+)|(#{.+})|({#.+})|({\d+})/g) || [])
            .map((match: string) => match.replace(/[\#\{\}]/g, ''))
            .forEach((key: string) => {
                if (!obj.has(groups, key)) {
                    obj.set(groups, key, key);
                }
            });
        let result = (i18lang[resource] || resource)
            .replace(/(#.+)|(#{.+})|({#.+})|({\d+})/g, (match: string) => {
                let key = match.replace(/[\#\{\}]/g, '');

                return getText((groups[key] || key), groups);
            }) || resource;

        return result.toString();
    }

    return '';
};

export { i18n, Language, resources, LanguageBar };
