import { Vue, VueConstructor, ComponentOptions } from '@app/provider';

`<div class="modal show">
    <div class="toast fade show">
        <div class="toast-header">
            <strong class="mr-auto">Bootstrap</strong>
            <small class="text-muted">just now</small>
            <button type="button" class="ml-2 mb-1 close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            See? Just like this.
        </div>
    </div>
</div>`

const toast = {
    install(vue: VueConstructor<Vue>) {
        let dialog = () => ({
            template: `<div><span>{{params.msg | i18n}}</span>
                <div class="modal-footer text-right">
                    <button class="btn btn-link" v-on:click="$close('yes')">{{ 'yes' | i18n }}</button>
                    <button class="btn btn-link" v-on:click="$close('cancel')">{{ 'cancel' | i18n }}</button>
                </div>
            </div>`,
            props: {
                params: {
                    msg: String
                }
            }
        }) as ComponentOptions<Vue>;

        vue.mixin({
            beforeCreate() {
                let self = this,
                    $toast: { [key: string]: any } = {};

                ['warn', 'info', 'error'].forEach($type => {

                });
            }
        });

        vue.prototype.$toastWarn = function (msg: string, options?: any) {
            return new Promise((resolve) => {
                this.$modal(dialog(), { msg }, {
                    type: 'popup',
                    title: 'alert',
                    animate: {
                        show: 'slideInDown',
                        hide: 'slideOutDown'
                    }
                }).then(f => {
                    resolve(f);
                });
            });
        };

        vue.prototype.$toastAlert = function (msg: string, options?: any) {
            return new Promise((resolve) => {
                this.$modal(dialog(), { msg }, {
                    type: 'popup',
                    title: 'alert',
                    animate: {
                        show: 'fadeIn',
                        hide: 'fadeOut'
                    }
                }).then(f => {
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
                }).then(f => {
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
                }).then(f => {
                    resolve(f);
                });
            });
        };
    }
};

export { toast };