import '@app/index';
import '@views/index';

import { router } from '@app/core/router';
import { Vue, Vuex, VueRouter, _ } from '@app/provider';
import {
    tojs,
    bstrp,
    ajax,
    auth,
    resources,
    i18n,
    mask,
    modal,
    dialog,
    picker,
    validate,
    Language,
    LanguageBar
} from '@app/plugins';

import { obj, browser } from '@app/utils';
import { SideMenuBar, NavMenuBar, SideMenu } from '@app/components';

// use ajax request
Vue.use(ajax, 'webapi');
// use aut
Vue.use(auth);

Vue.use(tojs);
Vue.use(bstrp);
Vue.use(modal);
Vue.use(dialog);

Vue.use(i18n);
Vue.use(mask);
Vue.use(picker);

Vue.use(validate);

Vue.use(Vuex);
Vue.use(VueRouter);

Vue.config.silent = true;
Vue.config.devtools = true;
Vue.config.productionTip = false;

new Vue({
    router,
    components: {
        'nav-bar': NavMenuBar,
        'side-bar': SideMenuBar,
        'language-bar': LanguageBar
    },
    el: document.querySelector('body>#app_uk_mobile'),
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
            .then(() => {
                self.$auth.token
                    .then((tk: string | null) => {
                        if (tk) {
                            // xử lý các hàm lấy dữ liệu cần thiết ở đây
                            SideMenu.reload();
                        }
                    })
                    .then(() => {
                        self.$http.resources
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
                    })
                    .catch(() => {
                        self.$mask('hide');
                    });
            })
            .catch(() => {
                self.$mask('hide');
            });
    }
});