import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: '/access/login',
    style: require('./style.scss'),
    resource: require('./resources.json'),
    template: require('./index.html'),
    validations: {
        model: {
            comp: {
                required: true
            },
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
        comp: 5,
        username: '',
        password: ''
    }

    login(a: number, b: number, c: number) {
        //localStorage.setItem('csrf', this.model.username);

        /*this.$http.post("http://localhost:8080/nts.uk.com.web/webapi/ctx/sys/gateway/login/getcompany", {}).then(v => {
            debugger;
        });*/

        console.log(this, a, b, c);

        //this.$router.push({ name: 'HomeComponent' });
    }
}