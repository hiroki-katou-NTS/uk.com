import { characteristics } from '@app/utils/storage';
import { _ } from '@app/provider';

export namespace ccg007 {

    export function login(servicePath: string, self: any, submitData: LoginParam, resetForm: Function, saveInfo: boolean) {

        self.$validate();
        if (!self.$valid) {
            return;
        }
        
        self.$mask('show');
        self.$http.post(servicePath, submitData).then((res: { data: CheckChangePass }) => {
            if (res.data.showContract) {
                authenticateContract(self);
            } else {
                if (!_.isEmpty(res.data.successMsg)) {
                    self.$modal.info({ messageId: res.data.successMsg }).then(() => {
                        processAfterLogin(res, self, submitData, resetForm, saveInfo);
                    });
                } else {
                    processAfterLogin(res, self, submitData, resetForm, saveInfo);
                }
            }
        }).catch((res: any) => {
            // Return Dialog Error
            self.$mask('hide');
            if (!_.isEqual(res.message, 'can not found message id')) {
                self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
            } else {
                self.$modal.error(res.messageId);
            }
        });
    }

    function processAfterLogin(res: any, self: any, submitData: LoginParam, resetForm: Function, saveInfo: boolean) {
        if (!_.isEmpty(res.data.msgErrorId) && res.data.msgErrorId == 'Msg_1517') {
            // 確認メッセージ（Msg_1517）を表示する{0}【残り何日】
            self.$modal.confirm({ messageId: res.data.msgErrorId, messageParams: [res.data.spanDays.toString()]}).then((code) => {
                if (code === 'yes') {
                    self.$goto({ name: 'changepass', params: _.merge({}, 
                        submitData, 
                        { oldPassword: submitData.password, mesId: res.data.msgErrorId, saveInfo, changePassReason: 'Msg_1523', spanDays: res.data.spanDays }) 
                    });
                } else {
                    characteristics.remove('companyCode')
                                    .then(() => characteristics.save('companyCode', submitData.companyCode))
                                    .then(() => characteristics.remove('employeeCode'))
                                    .then(() => {
                                        if (saveInfo) {
                                            characteristics.save('employeeCode', submitData.employeeCode);
                                        }
                                    }).then(() => toHomePage(self));
                }
            });
        } else {
            // check MsgError
            if (!_.isEmpty(res.data.msgErrorId) || res.data.showChangePass) {
                if (res.data.showChangePass) {
                    self.$goto({ name: 'changepass', params: _.merge({}, 
                                submitData, 
                                { oldPassword: submitData.password, mesId: res.data.msgErrorId, saveInfo, 
                                    changePassReason: res.data.changePassReason }) 
                            });
                } else {
                    resetForm();
                    /** TODO: wait for dialog error method */
                    self.$modal.error({ messageId: res.data.msgErrorId });
                }
                self.$mask('hide');
            } else {
                characteristics.remove('companyCode')
                    .then(() => characteristics.save('companyCode', submitData.companyCode))
                    .then(() => characteristics.remove('employeeCode'))
                    .then(() => {
                        if (saveInfo) {
                            characteristics.save('employeeCode', submitData.employeeCode);
                        }
                    }).then(() => toHomePage(self));
            }
        }
    }

    export function toHomePage(self) {
        self.$mask('hide');
        self.$goto({ name: 'HomeComponent', params: { screen: 'login' } });
    }

    export function authenticateContract(self) {
        self.$goto({ name: 'contractAuthentication' });
    }
    
    export const submitLogin =  'ctx/sys/gateway/login/submit/mobile';

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
    }
}