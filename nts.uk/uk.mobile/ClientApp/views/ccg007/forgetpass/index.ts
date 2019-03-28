import { Vue } from '@app/provider';
import { component, Watch, Prop } from '@app/core/component';
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
    
    @Prop({ default: () => ({}) })
    params!: any;

    companies: Array<ICompany> = [];
    contractCode: string = '';
    companyCode: string = '';
    contractPass: string = '';

    model = {
        employeeCode: ''
    }

    created() {
        this.contractCode = this.params.contractCode;
        this.contractPass = this.params.contractPass;
        this.companies = this.params.companies;
        this.companyCode = this.params.companyCode;
        this.model.employeeCode = this.params.employeeCode;
    }

    sendMail() {
        let self = this, submitData: any = {};
        self.$validate()
        if(!self.$valid){
            return;
        }
        submitData.companyCode = _.escape(self.companyCode);
        submitData.employeeCode = _.escape(self.model.employeeCode);
        submitData.contractCode = _.escape(self.contractCode);
        submitData.contractPassword = _.escape(self.contractPass);
        self.$mask("show");
        self.$http.post(servicePath.sendMail, submitData).then((result: { data: Array<SendMailReturn>}) => {
            self.$goto({ name: 'malSent', params: { companyCode: self.companyCode, 
                                                            employeeCode: self.model.employeeCode,
                                                            contractCode: self.contractCode} });
        }).catch((res:any) => {
            //Return Dialog Error
            self.$mask("hide");
            if (!_.isEqual(res.message, "can not found message id")){
                self.$dialogError({ messageId: res.messageId, messageParams: res.parameterIds });
            } else {
                self.$dialogError({ messageId: res.messageId });
            }
        });
    }

    goBack(){
        this.$goto({ name: 'login', params: { companyCode: this.companyCode, 
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