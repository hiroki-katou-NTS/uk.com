import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { characteristics } from "@app/utils/storage";
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
        let self = this;
        self.contractCode = self.params.contractCode;
        self.contractPass = self.params.contractPass;
        new Promise((resolve, reject) => {
            resolve();
        }).then(() => {
            if(_.isEmpty(self.params.companies)){
                self.$http.post(servicePath.getAllCompany).then((response: { data: Array<ICompany>; }) => {
                    self.companies = response.data;
                });
            } else {
                self.companies = self.params.companies;
            }
        }).then(() => {
            if(_.isNil(self.params.companyCode)){
                characteristics.restore("companyCode").then((compCode: any) => {
                    self.companyCode = compCode;
                });
            } else {
                self.companyCode = self.params.companyCode;
            }
        }).then(() => {
            if(_.isNil(self.params.employeeCode)){
                characteristics.restore("employeeCode").then((empCode: any) => {
                    self.employeeCode = empCode;
                });
            } else {
                self.employeeCode = self.params.employeeCode;
            }
        });
        
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
                /** TODO: wait for dialog error method */
                self.$toastError(res.messageId);
                // self.$dialogError({ messageId: res.messageId, messageParams: res.parameterIds });
            } else {
                self.$toastError(res.messageId);
                // self.$dialogError({ messageId: res.messageId });
            }
        });
    }

    goBack(){
        this.$goto({ name: 'login', params: { companyCode: this.companyCode, 
                                                        employeeCode: this.employeeCode,
                                                        contractCode: this.contractCode,
                                                        contractPass: this.contractPass,
                                                        companies: this.companies} });
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