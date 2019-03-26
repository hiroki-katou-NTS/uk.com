import '@app/index';
import '@views/index';

import '@app/utils/bootstrap';

import { router } from '@app/core/router';
import { Vue, Vuex, VueRouter } from '@app/provider';
import { ajax, i18n, mask, modal, toast, click, validate, Language } from '@app/plugins';

import { LanguageBar } from '@app/plugins';
import { SideMenuBar, NavMenuBar } from '@app/components';

Vue.use(i18n);
Vue.use(mask);
Vue.use(modal);
Vue.use(toast);
Vue.use(click);
Vue.use(validate);

// use ajax request
Vue.use(ajax, 'webapi');

Vue.use(Vuex);
Vue.use(VueRouter);

//Vue.config.silent = true;
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
    }
});