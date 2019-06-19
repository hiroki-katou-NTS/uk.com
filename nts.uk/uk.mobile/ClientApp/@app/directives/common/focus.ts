import { Vue } from '@app/provider';

Vue.directive('focus', {
    // directive definition
    inserted(el: HTMLInputElement) {
        setTimeout(() => el.focus(), 25);
    }
});