import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { characteristics } from '@app/utils/storage';
import { SideMenu, NavMenu } from '@app/services';

@component({
    route: '/ccg/007/d',
    style: require('./style.scss'),
    template: require('./index.html'),
    validations: {
        companyCode: {
            required: true
        },
        employeeCode: {
            required: true,
            anyHalf: true
        }
    },
    name: 'forgetPass'
})
export class ForgetPassComponent extends Vue {

    @Prop({ default: () => ({}) })
    public params!: any;

    public companies: Array<ICompany> = [];
    public contractCode: string = '';
    public companyCode: string = '';
    public contractPass: string = '';
    public employeeCode: string = '';

    public created() {
        let self = this;
        self.contractCode = self.params.contractCode;
        self.contractPass = self.params.contractPass;
        
        self.checkEmpCodeAndCompany();
    }

    public mounted() {
        // Hide top & side menu
        NavMenu.visible = false;
        SideMenu.visible = false;
    }

    public checkEmpCodeAndCompany() {
        let self = this, companyCode = '', employeeCode = '';
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
                return characteristics.restore('employeeCode');
            }
        }).then((empCode: string) => {
            if (!_.isNil(empCode)) {
                employeeCode = empCode;
            }
        }).then(() => {
            if (!_.isNil(self.params.companyCode)) {
                return Promise.resolve(self.params.companyCode);
            } else {
                return characteristics.restore('companyCode');
            }
        }).then((compCode: string) => {
            if (!_.isNil(compCode)) {
                companyCode = compCode;
            }
        }).then(() => {
            if (_.isEmpty(self.companies) || _.isNil(_.find(self.companies, (com: ICompany) => com.companyCode === companyCode))) {
                self.companyCode = self.companies[0].companyCode;
                self.employeeCode = '';
            } else {
                self.companyCode = companyCode;
                self.employeeCode = employeeCode;
            }
        });
    }


    public destroyed() {
        // Show menu
        NavMenu.visible = true;
        SideMenu.visible = true;
    }

    public sendMail() {
        let self = this, submitData: any = {};
        self.$validate();
        if (!self.$valid) {
            return;
        }
        submitData.companyCode = self.companyCode;
        submitData.employeeCode = self.employeeCode;
        submitData.contractCode = self.contractCode;
        submitData.contractPassword = self.contractPass;
        self.$mask('show');
        self.$http.post(servicePath.sendMail, submitData).then((result: { data: Array<SendMailReturn> }) => {
            self.$mask('hide');
            if (!_.isEmpty(result.data)) {
                self.$goto({ name: 'mailSent', params: { companyCode: self.companyCode, 
                                                        employeeCode: self.employeeCode,
                                                        contractCode: self.contractCode} });
            }
        }).catch((res: any) => {
            // Return Dialog Error
            self.$mask('hide');
            if (!_.isEqual(res.message, 'can not found message id')) {
                /** TODO: wait for dialog error method */
                // self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
                self.$modal.error(res.message);
            } else {
                self.$modal.error(res.message);
            }
        });
    }

    public goBack() {
        this.$goto({    name: 'login', 
                        params: { companyCode: this.companyCode, 
                                                employeeCode: this.employeeCode,
                                                contractCode: this.contractCode,
                                                contractPass: this.contractPass,
                                                companies: this.companies} });
    }
}

const servicePath = {
    getAllCompany: 'ctx/sys/gateway/login/getcompany/',
    sendMail: 'ctx/sys/gateway/sendmail/mobile'
};

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