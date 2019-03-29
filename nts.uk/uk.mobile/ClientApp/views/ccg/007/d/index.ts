import { Vue } from '@app/provider';
import { component, Watch, Prop } from '@app/core/component';
import { characteristics } from "@app/utils/storage";
import { _ } from "@app/provider";
import { SideMenu, NavMenu } from '@app/services';

@component({
    route: '/ccg/007/d',
    style: require('./style.scss'),
    resource: require('./resources.json'),
    template: require('./index.html'),
    validations: {
        companyCode: {
            required: true
        },
        employeeCode: {
            required: true
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
    employeeCode: ''

    created() {
        this.contractCode = this.params.contractCode;
        this.contractPass = this.params.contractPass;
        this.companies = this.params.companies;
        this.companyCode = this.params.companyCode;
        this.employeeCode = this.params.employeeCode;
        if(_.isEmpty(this.companies)){
            this.getAllCompany().then((response: { data: Array<ICompany>; }) => {
                this.companies = response.data;
                if(_.isNil(this.params.companyCode)){
                    characteristics.restore("companyCode").then((compCode: any) => {
                        if (!_.isNil(compCode)) {
                            this.companyCode = compCode;
                        }
                    });
                }
                if(_.isNil(this.params.employeeCode)){
                    characteristics.restore("employeeCode").then((empCode: any) => {
                        if (!_.isNil(empCode)) {
                            this.employeeCode = empCode;
                        }
                    });
                }
            });
        }

        // Hide top & side menu
        NavMenu.visible = false;
        SideMenu.visible = false;
    }

    destroyed() {
        // Show menu
        NavMenu.visible = true;
        SideMenu.visible = true;
    }

    sendMail() {
        let self = this, submitData: any = {};
        self.$validate()
        if(!self.$valid){
            return;
        }
        submitData.companyCode = _.escape(self.companyCode);
        submitData.employeeCode = _.escape(self.employeeCode);
        submitData.contractCode = _.escape(self.contractCode);
        submitData.contractPassword = _.escape(self.contractPass);
        self.$mask("show");
        self.$http.post(servicePath.sendMail, submitData).then((result: { data: Array<SendMailReturn>}) => {
            if (!_.isEmpty(result.data)){
                self.$goto({ name: 'mailSent', params: { companyCode: self.companyCode, 
                    employeeCode: self.employeeCode,
                    contractCode: self.contractCode} });
            }
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
                                                        employeeCode: this.employeeCode,
                                                        contractCode: this.contractCode,
                                                        companies: this.companies} });
    }

    getAllCompany(): Promise<any> {
        return this.$http.post(servicePath.getAllCompany);
    }
}

const servicePath = {
    getAllCompany: "ctx/sys/gateway/login/getcompany",
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