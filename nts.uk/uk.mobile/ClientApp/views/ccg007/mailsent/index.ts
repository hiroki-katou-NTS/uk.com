import { Vue } from '@app/provider';
import { component, Watch } from '@app/core/component';
import { characteristics } from "@app/utils/storage";
import { _ } from "@app/provider";

@component({
    route: '/ccg/007/f',
    style: require('./style.scss'),
    resource: require('./resources.json'),
    template: require('./index.html'),
    validations: {
        model: {
            comp: {
                required: true
            },
            employeeCode: {
                required: true
            }
        }
    }, 
    name: 'mailSent'
})
export class MailSentComponent extends Vue {

    contractCode: string = '';
    compapyCode: string = '';
    employeeCode: string = '';
    created() {
        let params = this.$route.params;
        this.contractCode = params.contractCode;
        this.compapyCode = params.companyCode;
        this.employeeCode = params.employeeCode;
    }

    toLogin() {
        this.$router.push({ name: 'login', params: { companyCode: this.compapyCode, 
                                                        employeeCode: this.employeeCode,
                                                        contractCode: this.contractCode
                                                } });
    }
}