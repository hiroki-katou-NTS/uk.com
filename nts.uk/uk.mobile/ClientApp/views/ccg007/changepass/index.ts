import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { _ } from "@app/provider";
import { NavMenu, SideMenu } from '@app/services';

@component({
    route: '/ccg/007/c',
    style: require('./style.scss'),
    resource: require('./resources.json'),
    template: require('./index.html'),
    validations: {
        model: {
            currentPassword: {
                required: true
            },
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
    name: 'changepass'
})
export class ChangePassComponent extends Vue {

    policy = {
        lowestDigits: 0,
        alphabetDigit: 0,
        numberOfDigits: 0,
        symbolCharacters: 0,
        historyCount: 0,
        validPeriod: 0,
    }

    model = {
        currentPassword: '',
        newPassword: '',
        newPasswordConfirm: '',
        userName: ''
    }

    created() {
        let self = this;
        Promise.all([this.$http.post(servicePath.getPasswordPolicy), this.$http.post(servicePath.getUserName)]).then((values: Array<any>) => {
            let policy: PassWordPolicy = values[0].data, user: LoginInfor = values[1].data;
            self.model.userName = user.userName;
            self.policy.lowestDigits = policy.lowestDigits;
            self.policy.alphabetDigit = policy.alphabetDigit;
            self.policy.numberOfDigits = policy.numberOfDigits;
            self.policy.symbolCharacters = policy.symbolCharacters;
            self.policy.historyCount = policy.historyCount;
            self.policy.validPeriod = policy.validityPeriod;
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

    changePass() {
        this.$validate();
        if (!this.$valid) {
            return;                   
        }

        let self = this, 
            command: ChangePasswordCommand = new ChangePasswordCommand(self.model.currentPassword, 
                                                                        self.model.newPassword, 
                                                                        self.model.newPasswordConfirm);

        this.$mask("show");
        
        //submitChangePass
        this.$http.post(servicePath.changePass, command).then(function () {
            this.$goto({ name: 'toppage' });
        }).catch((res) => {
            //Return Dialog Error
            this.$mask("hide");
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
    getPasswordPolicy: "ctx/sys/gateway/securitypolicy/getPasswordPolicy",
    changePass: "ctx/sys/gateway/changepassword/submitchangepass",
    getUserName: "ctx/sys/gateway/changepassword/username/mobile"
}

class ChangePasswordCommand {
    oldPassword: string;
    newPassword: string;
    confirmNewPassword: string;
    
    constructor(oldPassword: string, newPassword: string, confirmNewPassword: string) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }
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