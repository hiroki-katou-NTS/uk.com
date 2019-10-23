import { dom } from '@app/utils';
import { Vue, DirectiveBinding, VNode } from '@app/provider';

Vue.directive('float-action', {
    unbind(el: HTMLElement, binding: DirectiveBinding, vnode: VNode, oldVnode: VNode) {
        let $vm = vnode.context;

        $vm.$mask('hide');
    },
    inserted(el: HTMLElement, binding: DirectiveBinding, vnode: VNode, oldVnode: VNode) {
        let $vm = vnode.context;

        el.appendChild(dom.create('a', {
            html: '<i class="fas fa-plus"></i>',
            'class': 'btn btn-danger btn-lg btn-floating'
        }));

        dom.addClass(el, 'fixed-action-btn');

        let ul = el.querySelector('ul');

        if (ul && !dom.hasClass(ul, 'list-unstyled')) {
            dom.addClass(ul, 'list-unstyled');

            [].slice.call(el.querySelectorAll('ul.list-unstyled>li'))
                .forEach((element) => {
                    dom.addClass(element, 'btn btn-floating');
                });

            dom.registerGlobalEventHandler(el, 'click', 'a.btn-floating', () => {
                dom.toggleClass(el, 'active');

                if (!dom.hasClass(el, 'active')) {
                    $vm.$mask('hide');
                } else {
                    dom.addClass(document.body, 'modal-open');
                    
                    $vm.$mask('show', 0.02)
                        .on(() => $vm.$mask('hide'), () => {
                            dom.removeAttr(ul, 'style');
                            dom.removeClass(el, 'active');
                            dom.removeClass(document.body, 'modal-open');
                        });

                    dom.setAttr(ul, 'style', `height: ${57 * [].slice.call(ul.querySelectorAll('li.btn-floating')).length}px`);
                }
            });

            dom.registerGlobalEventHandler(el, 'click', 'li.btn-floating', () => $vm.$mask('hide'));
        }
    }
});