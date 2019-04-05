import '@app/index';
import '@views/index';

import '@app/utils/bootstrap';

import { router } from '@app/core/router';
import { Vue, Vuex, VueRouter } from '@app/provider';
import { bstrp, ajax, resources, i18n, mask, modal, dialog, click, validate, Language } from '@app/plugins';

import { obj } from '@app/utils';
import { LanguageBar } from '@app/plugins';
import { SideMenuBar, NavMenuBar } from '@app/components';

// use ajax request
Vue.use(ajax, 'webapi');

Vue.use(bstrp);
Vue.use(modal);
Vue.use(dialog);

Vue.use(i18n);
Vue.use(mask);

Vue.use(click);
Vue.use(validate);

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
    },
    mounted() {
        const self = this,
            rapi = '/i18n/resources/mobile/get';

        self.$http.get(rapi)
            .then(resp => {
                obj.merge(resources, {
                    jp: resp.data
                });
            });
    }
});