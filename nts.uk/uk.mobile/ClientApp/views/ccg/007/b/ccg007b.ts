import { _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage } from '@app/utils';
import { NavMenu, SideMenu } from '@app/services';
import { CCG007Login } from '../common/common';

@component({
    route: '/ccg/007/b',
    style: require('./style.scss'),
    template: require('./index.html'),
    validations: {
        model: {
            comp: {
                required: true
            },
            employeeCode: {
                required: true,
                constraint: 'EmployeeCode'
            },
            password: {
                required: true
            }
        }
    },
    name: 'login',
    constraints: ['nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode']
})
export class LoginComponent extends CCG007Login {

    @Prop({ default: () => ({}) })
    public params!: any;

    public companies: Array<ICompany> = [];
    public contractCode: string = '';
    public defaultContractCode: string = '000000000000';
    public contractPass: string = '';

    public model = {
        comp: '',
        employeeCode: '',
        password: '',
        autoLogin: [true],
        ver: ''
    };

    public created() {
        let self = this;

        if (!_.isNil(self.params.contractCode)) {
            self.contractCode = self.params.contractCode;
            self.contractPass = self.params.contractPass;
            self.checkEmpCodeAndCompany();
        } else {
            Promise.resolve()
                .then(() => storage.local.getItem('contractInfo'))
                .then((value: any) => {
                    if (!_.isNil(value)) {
                        self.contractCode = value.contractCode;
                        self.contractPass = value.contractPassword;
                    }
                }).then(() => {
                    return this.$http.post(servicePath.checkContract, {
                        contractCode: self.contractCode || self.defaultContractCode,
                        contractPassword: self.contractPass
                    });
                }).then((rel: { data: any }) => {
                    if (rel.data.onpre) {
                        self.contractCode = self.defaultContractCode;
                        self.contractPass = null;

                        storage.local.setItem('contractInfo', { contractCode: self.contractCode, contractPassword: self.contractPass });
                    } else {
                        if (rel.data.showContract && !rel.data.onpre) {
                            self.$goto({ name: 'contractAuthentication' });
                        }
                    }
                }).then(() => {
                    self.checkEmpCodeAndCompany();
                }).catch((res) => {

                });
        }

        this.$http.post(servicePath.ver)
            .then((response: { data: any }) => {
                self.model.ver = response.data.ver;
            });
    }

    public mounted() {
        // Hide top & side menu
        NavMenu.visible = false;
        SideMenu.visible = false;
    }

    public checkEmpCodeAndCompany() {
        let self = this,
            companyCode = '',
            employeeCode = '';

        Promise.resolve().then(() => {
            if (!_.isEmpty(self.params.companies)) {
                return { data: self.params.companies };
            } else {
                return this.$http.post(servicePath.getAllCompany + self.contractCode);
            }
        }).then((response: { data: Array<ICompany> }) => self.companies = response.data)
            .then(() => {
                if (!_.isNil(self.params.employeeCode)) {
                    return Promise.resolve(self.params.employeeCode);
                } else {
                    return storage.local.getItem('employeeCode');
                }
            }).then((empCode: string) => {
                if (!_.isNil(empCode)) {
                    employeeCode = empCode;
                }
            }).then(() => {
                if (!_.isNil(self.params.companyCode)) {
                    return Promise.resolve(self.params.companyCode);
                } else {
                    return storage.local.getItem('companyCode');
                }
            }).then((compCode: string) => {
                if (!_.isNil(compCode)) {
                    companyCode = compCode;
                }
            }).then(() => {
                if (_.isEmpty(self.companies) || _.isNil(_.find(self.companies, (com: ICompany) => com.companyCode === companyCode))) {
                    self.model.comp = self.companies[0].companyCode;
                    self.model.employeeCode = '';
                } else {
                    self.model.comp = companyCode;
                    self.model.employeeCode = employeeCode;
                }
            });
    }

    public destroyed() {
        // Show menu
        NavMenu.visible = true;
        SideMenu.visible = true;
    }

    public login() {
        let self = this;

        super.login({
            companyCode: self.model.comp,
            employeeCode: self.model.employeeCode,
            password: self.model.password,
            contractCode: self.contractCode,
            contractPassword: self.contractPass,
            loginDirect: false
        }, () => self.model.password = '',
            self.model.autoLogin[0]);
    }

    public forgetPass() {
        this.$goto({
            name: 'forgetPass', params: {
                contractCode: this.contractCode,
                contractPass: this.contractPass,
                companyCode: this.model.comp,
                employeeCode: this.model.employeeCode,
                companies: this.companies
            }
        });

    }
}

const servicePath = {
    checkContract: 'ctx/sys/gateway/login/checkcontract',
    getAllCompany: 'ctx/sys/gateway/login/getcompany/',
    ver: 'ctx/sys/gateway/login/build_info_time'
};

interface ICompany {
    companyCode: string;
    companyId: string;
    companyName: string;
}