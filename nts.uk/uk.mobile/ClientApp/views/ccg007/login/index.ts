import { Vue } from '@app/provider';
import { component, Watch, Prop } from '@app/core/component';
import { characteristics } from "@app/utils/storage";
import { _ } from "@app/provider";
import { ajax } from "@app/plugins";

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
            self.contractCode = this.params.contractCode;
        } else {
            characteristics.restore("contractInfo").then((value: any) => {
                if(!_.isNil(value)){
                    self.$data.contractCode = value.contractCode;
                    self.$data.contractPass = value.contractPassword;
                }
    
                self.checkContract({ contractCode: self.contractCode, contractPassword: self.contractPass }).then((rel) => {
                    if(rel.data.onpre) {
                        self.contractCode = self.$data.defaultContractCode;
                        self.contractPass = null;
                        characteristics.remove("contractInfo");
                        characteristics.save("contractInfo", { contractCode: self.contractCode, contractPassword: self.contractPass });
                    } else {
                        if (rel.data.showContract && !rel.data.onpre) {
                            //self.openContractAuthDialog();
                        } 
                    }
                });
            });
        }
        this.getAllCompany().then((response: { data: Array<ICompany>; }) => {
            this.companies = response.data;
            if(!_.isNil(this.params.companyCode)){
                this.model.comp = this.params.companyCode;
            } else {
                characteristics.restore("companyCode").then((compCode: any) => {
                    if(!_.isNil(compCode)){
                        let currentComp = _.find(this.companies, (comp: ICompany) => { comp.companyCode === compCode }) as ICompany;
                        if(!_.isNil(currentComp)) {
                            this.model.comp = currentComp.companyId;
                        }
                    }
                });
            }
            if(!_.isNil(this.params.employeeCode)){
                this.model.employeeCode = this.params.employeeCode;
            } else {
                characteristics.restore("employeeCode").then((empCode: any) => {
                    if(!_.isNil(empCode)){
                        this.model.employeeCode = empCode;
                    }
                });
            }
        });
        this.getVersion().then((response: { data: any; }) => {
            this.model.ver = response.data.ver;
        });
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
        self.submitLogin(submitData).then((messError: CheckChangePass) => {
            if (messError.showContract) {
                // self.openContractAuthDialog();
            }
            else {
                //check MsgError
                if (!_.isEmpty(messError.msgErrorId) || messError.showChangePass) {
                    if (messError.showChangePass) {
                        self.$goto({ name: 'changepass' });
                    } else {
                        self.model.password = "";
                        self.$dialogError({ messageId: messError.msgErrorId });
                    }
                    self.$mask("hide");
                } else {
                    // login.keepUsedLoginPage("/nts.uk.com.web/view/ccg/007/d/index.xhtml");
                    characteristics.remove("companyCode").then(function() {
                        characteristics.save("companyCode", _.escape(self.model.comp)).then(function() {
                            characteristics.remove("employeeCode").then(function() {
                                if (self.model.autoLogin) {
                                    characteristics.save("employeeCode", _.escape(self.model.employeeCode)).then(function() {
                                        self.toHomePage();
                                        });
                                } else {
                                    self.toHomePage();
                                }
                            });
                        });
                    });
                }
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

    toHomePage(){
        this.$goto({ name: 'toppage' });
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

    checkContract(data: any): Promise<any> {
        return this.$http.post(servicePath.checkContract, JSON.stringify(data));
    }
    
    submitLogin(data: any): Promise<any> {
        return this.$http.post(servicePath.submitLogin, data);
    }
    
    getVersion(): Promise<any> {
        return this.$http.post(servicePath.ver);
    }

    getAllCompany(): Promise<any> {
        return this.$http.post(servicePath.getAllCompany);
    }
}

const option = {
    
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