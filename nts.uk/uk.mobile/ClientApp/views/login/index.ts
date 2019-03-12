import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: '/access/login',
    style: require('./style.scss'),
    template: require('./index.html')
})
export class LoginComponent extends Vue {
}