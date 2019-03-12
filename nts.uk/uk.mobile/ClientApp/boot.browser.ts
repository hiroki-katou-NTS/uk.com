import '@app/index';
import '@views/index';

import '@app/utils/bootstrap';

import { router } from '@app/core/router';
import { ajax, i18n, modal, validate } from '@app/plugins';

import { Vue, Vuex, VueRouter } from '@app/provider';

Vue.use(ajax);
Vue.use(i18n);
Vue.use(modal);
Vue.use(validate);

Vue.use(Vuex);
Vue.use(VueRouter);

//Vue.config.silent = true;
//Vue.config.devtools = false;
Vue.config.productionTip = false;

new Vue({
    router,
    el: document.querySelector('body>div')
});