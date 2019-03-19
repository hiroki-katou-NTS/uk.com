import { Vue } from '@app/provider';
import { component, Watch } from '@app/core/component';

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
    companies: Array<ICompany> = [];
    model = {
        comp: 5,
        username: '',
        password: ''
    }

    created() {
        this.$http.post("/ctx/sys/gateway/login/getcompany", { id: 1000 }).then((response: { data: Array<ICompany>; }) => {
            this.companies = response.data;
        });
    }

    login(a: number, b: number, c: number) {
        localStorage.setItem('csrf', 'uk.mobile'); //this.model.username);

        //this.$router.push({ name: 'HTMLDocumentsComponent' });
    }
}

interface ICompany {
    companyCode: string;
    companyId: string;
    companyName: string;
}