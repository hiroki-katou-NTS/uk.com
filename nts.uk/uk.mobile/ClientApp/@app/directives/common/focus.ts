import { Vue } from '@app/provider';

Vue.directive('focus', {
    // directive definition
    inserted: function (el: HTMLInputElement) {
        el.focus()
    }    
});