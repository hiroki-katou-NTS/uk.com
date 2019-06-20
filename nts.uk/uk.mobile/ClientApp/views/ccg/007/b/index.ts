import { _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage, auth } from '@app/utils';
import { NavMenu, SideMenu } from '@app/services';
import { CCG007Login } from '../common/ccg007base';

const DEFAULT_CONTRACT: string = '000000000000';

@component({
    route: '/ccg/007/b',
    style: require('./style.scss'),
    template: require('./index.vue'),
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
    name: 'ccg007b',
    constraints: ['nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode']
})
export class Ccg007BComponent extends CCG007Login {

    @Prop({ default: () => ({}) })
    public params!: any;

    public companies: Array<ICompany> = [];
    public contractCode: string = '';
    public contractPass: string = '';

    public model = {
        comp: '',
        employeeCode: '',
        password: '',
        autoLogin: true,
        ver: ''
    };

    public created() {
        let self = this;

        if (!_.isNil(self.params.contractCode)) {
            self.contractCode = self.params.contractCode;
            self.contractPass = self.params.contractPass;
            self.checkEmpCodeAndCompany();
        } else {
            self.$auth.contract
                .then((value: any) => {
                    if (!_.isNil(value)) {
                        self.contractCode = value.code;
                        self.contractPass = value.password;
                    }
                }).then(() => {
                    return this.$http
                        .post(servicePath.checkContract, {
                            contractCode: self.contractCode || DEFAULT_CONTRACT,
                            contractPassword: self.contractPass
                        });
                }).then((rel: { data: any }) => {
                    if (rel.data.onpre) {
                        self.contractCode = DEFAULT_CONTRACT;
                        self.contractPass = null;

                        storage.local.setItem('contract', { code: self.contractCode, password: self.contractPass });
                    } else {
                        if (rel.data.showContract && !rel.data.onpre) {
                            self.$goto('ccg007a');
                        }
                    }
                }).then(() => {
                    self.checkEmpCodeAndCompany();
                }).catch((res) => {

                });
        }

        this.$http
            .post(servicePath.ver)
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
            params = self.params,
            remb = auth.remember;

        Promise.resolve()
            .then(() => {
                if (!_.isEmpty(self.params.companies)) {
                    return { data: self.params.companies };
                } else {
                    return this.$http.post(servicePath.getAllCompany + self.contractCode);
                }
            })
            .then((response: { data: Array<ICompany> }) => self.companies = response.data)
            .then(() => {
                if (params.companyCode) {
                    self.model.comp = params.companyCode;
                } else if (remb) {
                    self.model.comp = remb.companyCode;
                }
                
                if (params.employeeCode) {
                    self.model.employeeCode = params.employeeCode;
                } else if (remb) {
                    self.model.employeeCode = remb.employeeCode;
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
            saveInfo: self.model.autoLogin,
            companyCode: self.model.comp,
            employeeCode: self.model.employeeCode,
            password: self.model.password,
            contractCode: self.contractCode,
            contractPassword: self.contractPass,
            loginDirect: false
        }, () => self.model.password = '');
    }

    public forgetPass() {
        let self = this,
            params: any = _.pick(self, ['contractCode', 'contractPass', 'companies']);

        _.extend(params, {
            companyCode: self.model.comp,
            employeeCode: self.model.employeeCode
        });

        self.$goto
            .password
            .forget(params);
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