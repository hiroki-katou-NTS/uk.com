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
                required: true,
                maxLength: 12,
                constraint: 'EmployeeCode',
                anyHalf: true
            },
            password: {
                required: true
            }
        }
    },
    name: 'login',
    constraints: [ 'nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode' ]
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
        autoLogin: [true],
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
            }).catch((res) => {

            });
        }
        if(!_.isEmpty(self.params.companies)){
            self.companies = self.params.companies;
            self.checkEmpCodeAndCompCode();
        } else {
            this.$http.post(servicePath.getAllCompany + self.contractCode).then((response: { data: Array<ICompany>; }) => {
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
        let self = this, companyCode = '', employeeCode = '';
        Promise.resolve().then(() => {
            if(!_.isNil(self.params.employeeCode)){
                return Promise.resolve(self.params.employeeCode);
            } else {
                return characteristics.restore("employeeCode");
            }
        }).then((empCode: string) => {
            if (!_.isNil(empCode)) {
                employeeCode = empCode;
            }
        }).then(() => {
            if(!_.isNil(self.params.companyCode)){
                return Promise.resolve(self.params.companyCode);
            } else {
                return characteristics.restore("companyCode");
            }
        }).then((compCode: string) => {
            if (!_.isNil(compCode)) {
                companyCode = compCode;
            }
        }).then(() => {
            if(_.isEmpty(self.companies) || _.isNil(_.find(self.companies, (com: ICompany) => com.companyCode === companyCode))){
                self.model.comp = self.companies[0].companyCode;
                self.model.employeeCode = '';
            } else {
                self.model.comp = companyCode;
                self.model.employeeCode = employeeCode;
            }
        })
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
        submitData.companyCode = self.model.comp;
        submitData.employeeCode = self.model.employeeCode;
        submitData.password = self.model.password;
        submitData.contractCode = self.contractCode;
        submitData.contractPassword = self.contractPass;
        self.$mask("show");
        this.$http.post(servicePath.submitLogin, submitData).then((res: { data: CheckChangePass}) => {
            if (res.data.showContract) {
                self.authenticateContract();
            }
            else {
                //check MsgError
                if (!_.isEmpty(res.data.msgErrorId) || res.data.showChangePass) {
                    if (res.data.showChangePass) {
                        self.$goto({ name: 'changepass' }, () => this.$destroy());
                    } else {
                        self.model.password = "";
                        /** TODO: wait for dialog error method */
                        self.$toastError(res.data.msgErrorId);
                        // self.$dialogError({ messageId: res.data.msgErrorId });
                    }
                    self.$mask("hide");
                } else {
                    characteristics.remove("companyCode")
                        .then(() => characteristics.save("companyCode", self.model.comp))
                        .then(() => characteristics.remove("employeeCode"))
                        .then(() => {
                            if (self.model.autoLogin[0] === true) {
                                characteristics.save("employeeCode", self.model.employeeCode);
                            }
                        }).then(() => self.toHomePage());
                }
            }
        }).catch((res: { data: any}) => {
            //Return Dialog Error
            self.$mask("hide");
            if (!_.isEqual(res.data.message, "can not found message id")) {
                /** TODO: wait for dialog error method */
                self.$toastError(res.data.messageId);
                // self.$dialogError({ messageId: res.messageId, messageParams: res.parameterIds });
            } else {
                self.$toastError(res.data.messageId);
                // self.$dialogError({ messageId: res.messageId });
            }
        });
    }

    toHomePage(){
        this.$goto({ name: 'HomeComponent', params: { screen: 'login' } }, () => this.$destroy());
    }

    authenticateContract(){
        this.$goto({ name: 'contractAuthentication' }, () => this.$destroy());
    }

    forgetPass(){
        this.$goto({ name: 'forgetPass', params: {
            contractCode: this.contractCode,
            contractPass: this.contractPass,
            companyCode: this.model.comp,
            employeeCode: this.model.employeeCode,
            companies: this.companies
        }}, () => this.$destroy());

    }
}

const servicePath = {
    checkContract: "ctx/sys/gateway/login/checkcontract",
    submitLogin: "ctx/sys/gateway/login/submit/mobile",
    getAllCompany: "ctx/sys/gateway/login/getcompany/",
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