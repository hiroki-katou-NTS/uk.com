import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: '/access/login',
    style: require('./style.scss'),
    resource: require('./resources.json'),
    template: require('./index.html'),
    validations: {
        model: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        }
    }
})
export class LoginComponent extends Vue {
    model = {
        username: '',
        password: ''
    }
}