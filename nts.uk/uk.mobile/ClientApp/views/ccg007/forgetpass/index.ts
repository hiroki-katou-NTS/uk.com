import { Vue } from '@app/provider';
import { component, Watch } from '@app/core/component';
import { characteristics } from "@app/utils/storage";
import { _ } from "@app/provider";

@component({
    route: '/ccg/007/d',
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
    name: 'forgetPass'
})
export class ForgetPassComponent extends Vue {

    companies: Array<ICompany> = [];
    contractCode: string = '';
    companyCode: string = '';
    contractPass: string = '';

    model = {
        employeeCode: ''
    }

    created() {
        let params = this.$route.params;
        this.contractCode = params.contractCode;
        this.contractPass = params.contractPass;
        this.companies = JSON.parse(params.companies);
        this.companyCode = params.companyCode;
        this.model.employeeCode = params.employeeCode;
    }

    sendMail() {
        let self = this, submitData: any = {};
        submitData.companyCode = _.escape(self.companyCode);
        submitData.employeeCode = _.escape(self.model.employeeCode);
        submitData.contractCode = _.escape(self.contractCode);
        submitData.contractPassword = _.escape(self.contractPass);
        this.$mask("show");
        this.$http.post(servicePath.sendMail, submitData).then((result: { data: Array<SendMailReturn>}) => {
            this.$router.push({ name: 'malSent', params: { companyCode: this.companyCode, 
                                                            employeeCode: this.model.employeeCode,
                                                            contractCode: this.contractCode} });
        }).catch((res:any) => {
            //Return Dialog Error
            this.$mask("hide");
            if (!_.isEqual(res.message, "can not found message id")){
                self.$dialogError({ messageId: res.messageId, messageParams: res.parameterIds });
            } else {
                self.$dialogError({ messageId: res.messageId });
            }
        });
    }

    goBack(){
        this.$router.push({ name: 'login', params: { companyCode: this.companyCode, 
                                                        employeeCode: this.model.employeeCode,
                                                        contractCode: this.contractCode} });
    }
}

const servicePath = {
    sendMail: "ctx/sys/gateway/sendmail/mobile"
}

interface ICompany {
    companyCode: string;
    companyId: string;
    companyName: string;
}

interface CallerParameter {
    loginId: string;
    contractCode: string;
    contractPassword: string;
}

interface SendMailReturn {
    url: string;
}