import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { _ } from "@app/provider";

@component({
    route: '/ccg/007/c',
    style: require('./style.scss'),
    resource: require('./resources.json'),
    template: require('./index.html'),
    validations: {
        model: {
            newPassword: {
                required: true,
                checkSame: {
                    test(value){
                        return value === this.model.newPasswordConfirm;
                    }, message: '新しいパスワードと新しいパスワード（確認用）はマッチしてない'
                }
            },
            newPasswordConfirm: {
                required: true,
                checkSame: {
                    test(value){
                        return value === this.model.newPassword;
                    }, message: '新しいパスワードと新しいパスワード（確認用）はマッチしてない'
                }
            }
        }
    }, 
    name: 'resetPass'
})
export class ResetPassComponent extends Vue {

    @Prop({ default: '' })
    id!: string;

    policy = {
        lowestDigits: 0,
        alphabetDigit: 0,
        numberOfDigits: 0,
        symbolCharacters: 0,
        historyCount: 0,
        validPeriod: 0,
    }

    model = {
        newPassword: '',
        newPasswordConfirm: '',
        userName: '',
        contractCode: '',
        loginId: '',
        userId: ''
    }

    created() {
        let self = this;
        this.$http.post(servicePath.getUserName + this.id).then((res: { data: LoginInfor}) => {
            let user: LoginInfor = res.data;
            self.model.userName = user.userName;
            self.model.contractCode = user.contractCode;
            self.model.loginId = user.loginId;
            self.model.userId = user.userId;
            this.$http.post(servicePath.getPasswordPolicy + user.contractCode).then((res: { data: PassWordPolicy}) => {
                let policy: PassWordPolicy = res.data;
                self.policy.lowestDigits = policy.lowestDigits;
                self.policy.alphabetDigit = policy.alphabetDigit;
                self.policy.numberOfDigits = policy.numberOfDigits;
                self.policy.symbolCharacters = policy.symbolCharacters;
                self.policy.historyCount = policy.historyCount;
                self.policy.validPeriod = policy.validityPeriod;
            });
        });
    }

    changePass() {
        this.$validate();
        if (!this.$valid) {
            return;                   
        }

        let self = this, 
            command: ResetPasswordCommand = { embeddedId: this.id,
                                                newPassword: this.model.newPassword,
                                                confirmNewPassword: this.model.newPasswordConfirm,
                                                userId: this.model.userId };

        this.$mask("show");
        
        //submitChangePass
        this.$http.post(servicePath.changePass, command).then(function () {
            let loginData = {
                loginId: _.padEnd(_.escape(self.model.loginId), 12, ' '),
                password: this.model.newPassword,
                contractCode: this.model.contractCode,
                contractPassword: null
            }
            
            //login
            this.$http.post(servicePath.submitLogin, loginData).then((messError: any) => {
                //Remove LoginInfo
                self.$goto({ name: 'toppage', params: { screen: 'login' } });
            }).catch((res:any) => {
                //Return Dialog Error
                self.$mask("hide");
                self.showMessageError(res);
            });
            
        }).catch((res) => {
            //Return Dialog Error
            self.$mask("hide");
            self.showMessageError(res);
        });
    }

    showMessageError(res: any) {
        // check error business exception
        if (!res.businessException) {
            return;
        }

        /** TODO: show error message */
        if (Array.isArray(res.errors)) {
            //nts.uk.ui.dialog.bundledErrors(res);
        } else {
            //nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
        }
    }
}

const servicePath = {
    getPasswordPolicy: "ctx/sys/gateway/securitypolicy/getPasswordPolicy106953 ",
    changePass: "ctx/sys/gateway/changepassword/submitchangepass",
    getUserName: "ctx/sys/gateway/changepassword/getUserNameByURL/",
    submitLogin: "ctx/sys/gateway/login/submit/form1"
}

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