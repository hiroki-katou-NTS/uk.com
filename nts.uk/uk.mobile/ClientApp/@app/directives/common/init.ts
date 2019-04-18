import { Vue, VNode } from '@app/provider';

Vue.directive('init', {
    bind: function (el: HTMLElement, binding: any, vnode: VNode) {
        vnode.context[binding.arg] = binding.value;
    }
});