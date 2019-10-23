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
                // check MsgError
                if (!_.isEmpty(res.data.msgErrorId) || res.data.showChangePass) {
                    if (res.data.showChangePass) {
                        self.$goto({ name: 'changepass', params: _.merge({}, 
                                    submitData, 
                                    { oldPassword: submitData.password, mesId: res.data.msgErrorId, saveInfo }) 
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
                            if (!_.isEmpty(res.data.successMsg)) {
                                return self.$modal.info({ messageId: res.data.successMsg });
                            }
                        }).then(() => {
                            if (saveInfo) {
                                characteristics.save('employeeCode', submitData.employeeCode);
                            }
                        }).then(() => toHomePage(self));
                }
            }
        }).catch((res: any) => {
            // Return Dialog Error
            self.$mask('hide');
            if (!_.isEqual(res.message, 'can not found message id')) {
                self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
            } else {
                self.$modal.error({ messageId: res.messageId });
            }
        });
    }

    export function toHomePage(self) {
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
    }

    interface LoginParam {
        companyCode: string;
        employeeCode: string;
        password: string;
        contractCode: string;
        contractPassword: string;
    }
}