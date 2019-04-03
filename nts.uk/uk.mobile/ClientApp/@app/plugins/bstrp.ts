import { dom } from '@app/utils';
import { Vue, VueConstructor } from '@app/provider';

const bstrp = {
    install(vue: VueConstructor<Vue>) {
        vue.mixin({
            mounted() {
                let root = this.$el as HTMLElement;

                // tabs
                ((el: HTMLElement) => {
                    [].slice.call(el.querySelectorAll('.nav.nav-tabs .nav-link.active, .nav.nav-pills .nav-link.active'))
                        .forEach((link: HTMLElement) => link.click());
                })(root);

                // checkgroup
                ((el: HTMLElement) => {
                    [].slice.call(el.querySelectorAll('.btn-group-toggle input'))
                        .forEach((input: HTMLInputElement) => {
                            let label = input.parentElement as HTMLElement;

                            dom.setAttr(label, 'class', 'btn btn-secondary');

                            if (label) {
                                if (input.checked) {
                                    dom.addClass(label, 'btn-primary');
                                    dom.removeClass(label, 'btn-secondary');
                                } else {
                                    dom.removeClass(label, 'btn-primary');
                                    dom.addClass(label, 'btn-secondary');
                                }
                            }
                        });
                })(root);
            }
        });
    }
};

export { bstrp };