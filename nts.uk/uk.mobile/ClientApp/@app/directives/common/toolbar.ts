import { dom } from '@app/utils';
import { Vue } from '@app/provider';
import { NavMenu } from '@app/services';

Vue.directive('toolbar', {
    unbind() {
        NavMenu.visible = true;
    },
    inserted(el: HTMLElement) {
        NavMenu.visible = false;
        dom.addClass(el, "navbar bg-primary fixed-top");
    }
});