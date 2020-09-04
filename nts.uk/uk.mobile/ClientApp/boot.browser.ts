import '@views/index';

import '@app/index';
import '@app/plugins';

import { Vue } from '@app/provider';
import { RootApp } from '@app/core';
import { obj, browser } from '@app/utils';

import { SideMenuBar, NavMenuBar, SideMenu } from '@app/components';
import { resources, Language, LanguageBar, } from '@app/plugins/i18n';

// Vue.config.silent = true;
Vue.config.devtools = true;
// Vue.config.productionTip = false;

new (RootApp.extend({
    components: {
        'nav-bar': NavMenuBar,
        'side-bar': SideMenuBar,
        'language-bar': LanguageBar
    },
    computed: {
        pgName: {
            get() {
                return Language.pgName || 'app_name';
            }
        }
    },
    beforeCreate() {
        const self = this;

        self.$mask('show', { message: true, opacity: 0.75 });

        browser.private
            .then((prid: boolean) => {
                if (browser.version === 'Safari 10' && prid) {
                    self.$modal.warn({ messageId: 'Msg_1533' });
                }
            })
            .then(() => self.$auth.token)
            .then((tk: string | null) => {
                if (tk) {
                    // xử lý các hàm lấy dữ liệu cần thiết ở đây
                    SideMenu.reload();
                }
            })
            .then(() => self.$http.resources)
            .then((resr: any) => {
                obj.merge(resources.jp, resr, true);
            })
            .then(() => {
                Language.refresh();
            })
            .then(() => {
                self.$mask('hide');
            })
            .catch(() => {
                self.$mask('hide');
            });
    }
}))().$mount(document.querySelector('body>#app_uk_mobile'));

Object.assign(window, { Vue });
