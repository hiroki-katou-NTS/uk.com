import { Vue, _ } from '@app/provider';

export class CCG007Login extends Vue {
    protected login(submitData: LoginParam, resetForm: Function, saveInfo: boolean) {
        let self = this;

        self.$validate();

        if (!self.$valid) {
            return;
        }

        self.$mask('show');

        self.$auth.login(submitData)
            .then((data: CheckChangePass) => {
                if (data.showContract) {
                    // goto Contract authentication
                    self.$goto('ccg007a');
                } else {
                    if (!_.isEmpty(data.successMsg)) {
                        self.$modal.info({ messageId: data.successMsg })
                            .then(() => {
                                self.processAfterLogin(data, submitData, resetForm, saveInfo);
                            });
                    } else {
                        self.processAfterLogin(data, submitData, resetForm, saveInfo);
                    }
                }
            }).catch((res: any) => {
                // Return Dialog Error
                self.$mask('hide');

                if (!_.isEqual(res.message, 'can not found message id')) {
                    if (_.isEmpty(res.messageId)) {
                        self.$modal.error(res.message);
                    } else {
                        self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
                    }
                } else {
                    self.$modal.error(res.messageId);
                }
            });
    }

    protected processAfterLogin(data: any, submitData: LoginParam, resetForm: Function, saveInfo: boolean) {
        let self = this;

        self.$mask('hide');

        if (!_.isEmpty(data.msgErrorId) && data.msgErrorId === 'Msg_1517' && !submitData.loginDirect) {
            // 確認メッセージ（Msg_1517）を表示する{0}【残り何日】
            self.$modal.confirm({ messageId: data.msgErrorId, messageParams: [data.spanDays.toString()] })
                .then((code) => {
                    if (code === 'yes') {
                        self.$goto({
                            name: 'changepass',
                            params: _.merge(submitData,
                                {
                                    saveInfo,
                                    oldPassword: submitData.password,
                                    mesId: data.msgErrorId,
                                    changePassReason: 'Msg_1523',
                                    spanDays: data.spanDays
                                })
                        });
                    } else {
                        submitData.loginDirect = true;
                        this.login(submitData, resetForm, saveInfo);
                    }
                });
        } else {
            // check MsgError
            if ((!_.isEmpty(data.msgErrorId) && data.msgErrorId !== 'Msg_1517') || data.showChangePass) {
                if (data.showChangePass) {
                    self.$goto({
                        name: 'changepass', params: _.merge({},
                            submitData,
                            {
                                oldPassword: submitData.password, mesId: data.msgErrorId, saveInfo,
                                changePassReason: data.changePassReason
                            })
                    });
                } else {
                    resetForm();
                    /** TODO: wait for dialog error method */
                    self.$modal.error({ messageId: data.msgErrorId });
                }
            } else {
                self.$goto.home({ screen: 'login' });
            }
        }

    }
}

interface CheckChangePass {
    showChangePass: boolean;
    msgErrorId: string;
    showContract: boolean;
    successMsg: string;
    spanDays: number;
    changePassReason: string;
}

interface LoginParam {
    companyCode: string;
    employeeCode: string;
    password: string;
    contractCode: string;
    contractPassword: string;
    loginDirect: boolean;
}