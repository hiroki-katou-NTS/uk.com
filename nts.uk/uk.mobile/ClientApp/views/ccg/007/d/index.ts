import { storage } from '@app/utils';
import { Vue, _ } from '@app/provider';
import { SideMenu, NavMenu } from '@app/services';
import { component, Prop } from '@app/core/component';

@component({
    route: '/ccg/007/d',
    style: require('./style.scss'),
    template: require('./index.vue'),
    validations: {
        companyCode: {
            required: true
        },
        employeeCode: {
            required: true,
            anyHalf: true
        }
    },
    name: 'ccg007d'
})
export class Ccg007dComponent extends Vue {
    @Prop({ default: () => ({}) })
    public readonly params!: any;

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
        let self = this,
            params = self.params;

        // get list company from param or service
        if (params.companies && params.companies.length) {
            self.companies = params.companies;
        } else {
            self.$http
                .post(`/${servicePath.getAllCompany}/${self.contractCode}`)
                .then((response: { data: Array<ICompany> }) => {
                    self.companies = response.data;
                });
        }

        // get compCode from param or storage
        if (params.companyCode) {
            self.companyCode = params.companyCode;
        } else {
            self.companyCode = storage.local.getItem('companyCode') as string;
        }

        // get emplCode from param or storage
        if (params.employeeCode) {
            self.employeeCode = params.employeeCode;
        } else {
            self.employeeCode = storage.local.getItem('employeeCode') as string;
        }
    }


    public destroyed() {
        // Show menu
        NavMenu.visible = true;
        SideMenu.visible = true;
    }

    public sendMail() {
        let self = this,
            submitData: any = {};

        self.$validate();

        if (!self.$valid) {
            return;
        }

        submitData.companyCode = self.companyCode;
        submitData.employeeCode = self.employeeCode;
        submitData.contractCode = self.contractCode;
        submitData.contractPassword = self.contractPass;

        self.$mask('show');

        self.$http.post(servicePath.sendMail, submitData)
            .then((result: { data: Array<SendMailReturn> }) => {
                self.$mask('hide');

                if (!_.isEmpty(result.data)) {
                    self.$goto
                        .password
                        .mail({
                            companyCode: self.companyCode,
                            employeeCode: self.employeeCode,
                            contractCode: self.contractCode
                        });
                }
            }).catch((res: any) => {
                // Return Dialog Error
                self.$mask('hide');

                if (!_.isEqual(res.message, 'can not found message id')) {
                    /** TODO: wait for dialog error method */
                    self.$modal.error({
                        messageId: res.messageId,
                        messageParams: res.parameterIds
                    });
                    // self.$modal.error(res.message);
                } else {
                    self.$modal.error(res.message);
                }
            });
    }

    public goBack() {
        this.$goto
            .login({
                companyCode: this.companyCode,
                employeeCode: this.employeeCode,
                contractCode: this.contractCode,
                contractPass: this.contractPass,
                companies: this.companies
            });
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