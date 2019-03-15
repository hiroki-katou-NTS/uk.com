import '@app/index';
import '@views/index';

import '@app/utils/bootstrap';

import { router } from '@app/core/router';
import { Vue, Vuex, VueRouter } from '@app/provider';
import { ajax, i18n, modal, click, validate } from '@app/plugins';

import { LanguageBar } from '@app/plugins';

Vue.use(i18n);
Vue.use(modal);
Vue.use(click);
Vue.use(validate);

// use ajax request
Vue.use(ajax, 'webapi');

Vue.use(Vuex);
Vue.use(VueRouter);

//Vue.config.silent = true;
//Vue.config.devtools = false;
Vue.config.productionTip = false;

new Vue({
    router,
    components: {
        'language-bar': LanguageBar
    },
    el: document.querySelector('body>div')
});