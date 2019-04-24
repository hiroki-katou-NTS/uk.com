import { Vue, _ } from '@app/provider';
import { component } from '@app/core/component';
import { SideMenu, NavMenu } from '@app/services';

@component({
    route: '/ccg/007/e',
    style: require('./style.scss'),
    template: require('./index.html'),
    validations: {
        model: {
            newPassword: {
                required: true,
                checkSame: {
                    test(value) {
                        return this.model.newPassword === this.model.newPasswordConfirm;
                    }, message: 'Msg_961'
                }
            },
            newPasswordConfirm: {
                required: true,
                checkSame: {
                    test(value) {
                        return this.model.newPasswordConfirm === this.model.newPassword;
                    }, message: 'Msg_961'
                }
            }
        }
    }, 
    name: 'resetPass'
})
export class ResetPassComponent extends Vue {

    // @Prop({ default: '' })
    // id!: string;
    public id: string;

    public policy = {
        lowestDigits: 0,
        alphabetDigit: 0,
        numberOfDigits: 0,
        symbolCharacters: 0,
        historyCount: 0,
        validPeriod: 0,
        isUse: false
    };

    public model = {
        newPassword: '',
        newPasswordConfirm: '',
        userName: '',
        contractCode: '',
        loginId: '',
        userId: ''
    };

    public created() {
        let self = this;
        self.id = self.$route.query.id as string;

        self.$http.post(servicePath.getUserName + self.id)
        .then((res: { data: LoginInfor}) => {
            let user: LoginInfor = res.data;
            self.model.userName = user.userName;
            self.model.contractCode = user.contractCode;
            self.model.loginId = user.loginId;
            self.model.userId = user.userId;
            return user;
        }).then((user) => {
            return self.$http.post(servicePath.getPasswordPolicy + self.model.contractCode);
        }).then((res: { data: PassWordPolicy}) => {
            let policy: PassWordPolicy = res.data;
            self.policy.lowestDigits = policy.lowestDigits;
            self.policy.alphabetDigit = policy.alphabetDigit;
            self.policy.numberOfDigits = policy.numberOfDigits;
            self.policy.symbolCharacters = policy.symbolCharacters;
            self.policy.historyCount = policy.historyCount;
            self.policy.validPeriod = policy.validityPeriod;
            self.policy.isUse  = policy.isUse;
        });
    }

    public mounted() {
        // Hide top & side menu
        NavMenu.visible = false;
        SideMenu.visible = false;
    }

    public destroyed() {
        // Show menu
        NavMenu.visible = true;
        SideMenu.visible = true;
    }

    public changePass() {
        this.$validate();
        if (!this.$valid) {
            return;                   
        }

        let self = this, 
            command: ResetPasswordCommand = { embeddedId: self.id,
                                                newPassword: self.model.newPassword,
                                                confirmNewPassword: self.model.newPasswordConfirm,
                                                userId: self.model.userId };

        self.$mask('show');
        
        // submitChangePass
        self.$http.post(servicePath.changePass, command).then(() => {
            return {
                loginId: _.padEnd(self.model.loginId, 12, ' '),
                password: self.model.newPassword,
                contractCode: self.model.contractCode,
                contractPassword: null
            };
        }).then((loginData) => self.$http.post(servicePath.submitLogin, loginData))
        .then((messError: any) => {
            // Remove LoginInfo
            self.$goto({ name: 'HomeComponent', params: { screen: 'login' } });
        }).catch((res) => {
            // Return Dialog Error
            self.$mask('hide');
            self.showMessageError(res);
        });
    }

    public showMessageError(res: any) {
        // check error business exception
        if (!res.businessException) {
            return;
        }

        /** TODO: show error message */
        if (Array.isArray(res.errors)) {
            // nts.uk.ui.dialog.bundledErrors(res);
        } else {
            // nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
        }
        this.$modal.error(res.message);
    }
}

const servicePath = {
    getPasswordPolicy: 'ctx/sys/gateway/changepassword/getPasswordPolicy/',
    changePass: 'ctx/sys/gateway/changepassword/submitforgotpass',
    getUserName: 'ctx/sys/gateway/changepassword/getUserNameByURL/',
    submitLogin: 'ctx/sys/gateway/login/submit/form1'
};

interface LoginData {
    loginId: string;
    password: string;
    contractCode: string;
    contractPassword: string;
}

interface ResetPasswordCommand {
    embeddedId: string;
    newPassword: string;
    confirmNewPassword: string;
    userId: string;
}

interface LoginInfor {
    loginId: string;
    userName: string;
    userId: string;
    contractCode: string;
}

interface PassWordPolicy {
    notificationPasswordChange: number;
    loginCheck: boolean;
    initialPasswordChange: boolean;
    isUse: boolean;
    historyCount: number;
    lowestDigits: number;
    validityPeriod: number;
    numberOfDigits: number;
    symbolCharacters: number;
    alphabetDigit: number; 
}