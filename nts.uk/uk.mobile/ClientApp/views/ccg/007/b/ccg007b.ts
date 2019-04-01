import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { characteristics } from "@app/utils/storage";
import { NavMenu, SideMenu } from "@app/services";

@component({
    route: '/ccg/007/b',
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
            },
            password: {
                required: true
            }
        }
    },
    name: 'login'
})
export class LoginComponent extends Vue {
    
    @Prop({ default: () => ({}) })
    params!: any;

    companies: Array<ICompany> = [];
    contractCode: string = '';
    defaultContractCode: string = '000000000000';
    contractPass: string = '';

    model = {
        comp: '',
        employeeCode: '',
        password: '',
        autoLogin: [false],
        ver: ''
    }
    
    created() {
        let self = this;
        if(!_.isNil(self.params.contractCode)){
            self.contractCode = self.params.contractCode;
            self.contractPass = self.params.contractPass;
        } else {
            characteristics.restore("contractInfo").then((value: any) => {
                if (!_.isNil(value)) {
                    self.contractCode = value.contractCode;
                    self.contractPass = value.contractPassword;
                }
            }).then(() => { 
                return this.$http.post(servicePath.checkContract, { contractCode: self.contractCode, contractPassword: self.contractPass });
            }).then((rel: { data: any }) => {
                if(rel.data.onpre) {
                    self.contractCode = self.defaultContractCode;
                    self.contractPass = null;
                    characteristics.remove("contractInfo")
                        .then(() => characteristics.save("contractInfo", { contractCode: self.contractCode, contractPassword: self.contractPass }));
                } else {
                    if (rel.data.showContract && !rel.data.onpre) {
                        self.authenticateContract();
                    }
                }
            });
        }
        if(!_.isEmpty(self.params.companies)){
            self.companies = self.params.companies;
            self.checkEmpCodeAndCompCode();
        } else {
            this.$http.post(servicePath.getAllCompany).then((response: { data: Array<ICompany>; }) => {
                self.companies = response.data;
                self.checkEmpCodeAndCompCode();
            });
        }
        this.$http.post(servicePath.ver).then((response: { data: any }) => {
            self.model.ver = response.data.ver;
        });

        // Hide top & side menu
        NavMenu.visible = false;
        SideMenu.visible = false;
    }

    checkEmpCodeAndCompCode(){
        let self = this;
        if(!_.isNil(self.params.companyCode)){
            self.model.comp = self.params.companyCode;
        } else {
            characteristics.restore("companyCode").then((compCode: any) => {
                if (!_.isNil(compCode)) {
                    self.model.comp = compCode;
                }
            });
        }
        if(!_.isNil(self.params.employeeCode)){
            self.model.employeeCode = self.params.employeeCode;
        } else {
            characteristics.restore("employeeCode").then((empCode: any) => {
                if (!_.isNil(empCode)) {
                    self.model.employeeCode = empCode;
                }
            });
        }
    }

    destroyed() {
        // Show menu
        NavMenu.visible = true;
        SideMenu.visible = true;
    }

    login() {
        let self = this, submitData: any = {};
        self.$validate();
        if(!self.$valid){
            return;
        }
        submitData.companyCode = _.escape(self.model.comp);
        submitData.employeeCode = _.escape(self.model.employeeCode);
        submitData.password = _.escape(self.model.password);
        submitData.contractCode = _.escape(self.contractCode);
        submitData.contractPassword = _.escape(self.contractPass);
        self.$mask("show");
        this.$http.post(servicePath.submitLogin, submitData).then((res: { data: CheckChangePass}) => {
            if (res.data.showContract) {
                self.authenticateContract();
            }
            else {
                //check MsgError
                if (!_.isEmpty(res.data.msgErrorId) || res.data.showChangePass) {
                    if (res.data.showChangePass) {
                        self.$goto({ name: 'changepass' });
                    } else {
                        self.model.password = "";
                        /** TODO: wait for dialog error method */
                        self.$toastError(res.data.msgErrorId);
                        // self.$dialogError({ messageId: res.data.msgErrorId });
                    }
                    self.$mask("hide");
                } else {
                    characteristics.remove("companyCode")
                        .then(() => characteristics.save("companyCode", _.escape(self.model.comp)))
                        .then(() => characteristics.remove("employeeCode"))
                        .then(() => {
                            if (self.model.autoLogin) {
                                characteristics.save("employeeCode", _.escape(self.model.employeeCode));
                            }
                        }).then(() => self.toHomePage());
                }
            }
        }).catch((res: any) => {
            //Return Dialog Error
            self.$mask("hide");
            if (!_.isEqual(res.message, "can not found message id")) {
                /** TODO: wait for dialog error method */
                self.$toastError(res.messageId);
                // self.$dialogError({ messageId: res.messageId, messageParams: res.parameterIds });
            } else {
                self.$toastError(res.messageId);
                // self.$dialogError({ messageId: res.messageId });
            }
        });
    }

    toHomePage(){
        this.$goto({ name: 'HomeComponent', params: { screen: 'login' } });
    }

    authenticateContract(){
        this.$goto({ name: 'contractAuthentication' });
    }

    forgetPass(){
        this.$goto({ name: 'forgetPass', params: {
            contractCode: this.contractCode,
            contractPass: this.contractPass,
            companyCode: this.model.comp,
            employeeCode: this.model.employeeCode,
            companies: this.companies
        }});

    }
}

const servicePath = {
    checkContract: "ctx/sys/gateway/login/checkcontract",
    submitLogin: "ctx/sys/gateway/login/submit/mobile",
    getAllCompany: "ctx/sys/gateway/login/getcompany",
    ver: "ctx/sys/gateway/login/build_info_time"
}

interface ICompany {
    companyCode: string;
    companyId: string;
    companyName: string;
}

interface CheckChangePass {
    showChangePass: boolean;
    msgErrorId: string;
    showContract: boolean;
}