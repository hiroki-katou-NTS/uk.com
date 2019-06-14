import { obj, auth as authentication, storage } from '@app/utils';
import { Vue, VueConstructor } from '@app/provider';

/**
 * Authentication plugin for UK Mobile
 * Extend once object $auth to all view model
 * Method: login, logout
 * Properity: user, token
 */
export const auth = {
    install: (vue: VueConstructor<Vue>) => {
        // clear cookie method
        let clearAuthentication = () => {
            storage.local.removeItem('user');
            storage.local.removeItem('csrf');

            storage.cookie.removeItem('sescon');
        };

        vue.mixin({
            beforeCreate() {
                let self = this;

                obj.extend(self, {
                    $auth: {
                        login: (data: any) => {
                            let lapi: string = 'ctx/sys/gateway/login/submit/mobile';

                            return self.$http
                                .post(lapi, data)
                                .then((resp: { data: any }) => {
                                    self.$http.get('/ctx/sys/gateway/login/mobile/token')
                                        .then((v: { data: string | any }) => {
                                            if (typeof v.data !== 'string') {
                                                clearAuthentication();
                                            } else {
                                                storage.local.setItem('csrf', v.data);
                                            }

                                            return typeof v.data === 'string';
                                        })
                                        .then((v: boolean) => {
                                            if (v) {
                                                self.$http.post('/view-context/user')
                                                    .then((resp: { data: any }) => {
                                                        storage.local.setItem('user', resp.data);
                                                    });
                                            }
                                        });

                                    return resp.data;
                                });
                        },
                        logout: () => {
                            let lapi: string = 'sys/portal/webmenu/logout';

                            return Promise
                                .resolve()
                                // check login?
                                .then(() => authentication.valid)
                                // call api clear cookie cache (server)
                                .then((v) => v && self.$http.post(lapi))
                                // clear cookie cache (client)
                                .then(clearAuthentication)
                                // go to login page
                                .then(() => self.$goto('login'));
                        }
                    }
                });

                // get token of current user logined
                Object.defineProperty(self.$auth, 'token', {
                    get() {
                        return new Promise((resolve) => {
                            let token = authentication.token;

                            if (!token) {
                                resolve(null);

                                self.$goto('login');
                            } else {
                                resolve(token);
                            }
                        });
                    }
                });

                // get user info
                Object.defineProperty(self.$auth, 'user', {
                    get() {
                        return new Promise((resolve) => {
                            let user = authentication.user;

                            if (!user) {
                                resolve(null);
                                
                                self.$goto('login');
                            } else {
                                resolve(user);
                            }
                        });
                    }
                });
            }
        });
    }
};