import { Vue, VueConstructor } from '@app/provider';

const toast = {
    install(vue: VueConstructor<Vue>) {
        let dialog = () => ({
            template: `<div><span>{{params.msg | i18n}}</span>
                <div class="modal-footer text-center">
                    <button class="btn btn-link" v-on:click="$close('yes')">{{ 'yes' | i18n }}</button>
                    <button class="btn btn-link" v-on:click="$close('cancel')">{{ 'cancel' | i18n }}</button>
                </div>
            </div>`,
            props: {
                params: {
                    msg: String
                }
            }
        });

        vue.prototype.$toastAlert = function (msg: string, options?: any) {
            return new Promise((resolve) => {
                this.$modal(dialog(), { msg }, {
                    type: 'popup',
                    title: 'alert',
                    animate: {
                        show: 'slideInDown',
                        hide: 'slideOutDown'
                    }
                })
                    .onClose(f => {
                        resolve(f);
                    });
            });
        };

        vue.prototype.$toastError = function (msg: string, options?: any) {
            return new Promise((resolve) => {
                this.$modal(dialog(), { msg }, {
                    type: 'popup',
                    title: 'error',
                    animate: {
                        show: 'slideInDown',
                        hide: 'slideOutDown'
                    }
                })
                    .onClose(f => {
                        resolve(f);
                    });
            });
        };

        vue.prototype.$toastConfirm = function (msg: string, options?: any) {
            return new Promise((resolve) => {
                this.$modal(dialog(), { msg }, {
                    type: 'popup',
                    title: 'confirm',
                    animate: {
                        show: 'slideInDown',
                        hide: 'slideOutDown'
                    }
                })
                    .onClose(f => {
                        resolve(f);
                    });
            });
        };
    }
};

export { toast };