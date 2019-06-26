import { dom } from '@app/utils';
import { Vue, DirectiveBinding } from '@app/provider';

Vue.directive('tree', {
    inserted(el: HTMLElement, binding: DirectiveBinding) {
        let item: { $h: { rank: number; childs: Array<any>; collapse: boolean; refresh: () => void; } } = binding.value;

        if (item && item.$h) {
            // init indent class
            dom.addClass(el, `indent-${(item.$h.rank || 0) + 1}`);

            if (item.$h.childs.length) {
                let $col = dom.create('i', { class: 'fas fa-chevron-down collapse' });
                el.prepend($col);

                // tslint:disable-next-line: no-string-literal
                dom.registerEventHandler($col, 'click', el['_uk_c_evt'] = () => {
                    item.$h.collapse = !item.$h.collapse;

                    // refresh item
                    item.$h.refresh();

                    if (item.$h.collapse) {
                        dom.setAttr($col, 'class', 'fas fa-chevron-down collapse');
                    } else {
                        dom.setAttr($col, 'class', 'fas fa-chevron-right collapse');
                    }
                });
            }
        }
    },
    update(el: HTMLElement, binding: DirectiveBinding) {
        let item: { $h: { rank: number; show: boolean; } } = binding.value;

        if (item && item.$h) {
            if (!item.$h.show) {
                dom.addClass(el, 'd-none');
            } else {
                dom.removeClass(el, 'd-none');
            }

            // init indent class
            dom.addClass(el, `indent-${(item.$h.rank || 0) + 1}`);
        }
    },
    unbind(el: HTMLElement) {
        // tslint:disable-next-line: no-string-literal
        dom.removeEventHandler(el, 'click', el['_uk_c_evt']);
    }
});