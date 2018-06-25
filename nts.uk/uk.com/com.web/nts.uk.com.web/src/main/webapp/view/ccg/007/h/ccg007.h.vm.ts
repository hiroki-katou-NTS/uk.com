module nts.uk.pr.view.ccg007.h {
    export module viewmodel {
        import blockUI = nts.uk.ui.block;
        import ForgotPasswordCommand = service.ForgotPasswordCommand;
        import SubmitData = service.SubmitData;

        export class ScreenModel {

            userName: KnockoutObservable<string>;
            userId: KnockoutObservable<string>;
            passwordNew: KnockoutObservable<string>;
            passwordNewConfirm: KnockoutObservable<string>;

            constructor() {
                var self = this;

                self.userName = ko.observable(null);
                self.userId = ko.observable(null);
                self.passwordNew = ko.observable(null);
                self.passwordNewConfirm = ko.observable(null);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui
                nts.uk.ui.block.invisible();

                //get userName
                service.getUserNameByLoginId(localStorage.getItem('contractCode'), localStorage.getItem('loginId')).done(function(data) {
                    self.userName(data.userName);
                    self.userId(data.userId);
                    //remove loginId and contractCode in LocalStorage
                    
                });

                dfd.resolve();

                //clear block
                nts.uk.ui.block.clear();

                return dfd.promise();
            }

            /**
             * Submit
             */
            public submit(): void {
                let self = this;

                //check hasError
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                
                //check userId null
                if (_.isEmpty(self.userId())) {
                    return;
                }

                blockUI.invisible();

                //add command
                let command: ForgotPasswordCommand = new ForgotPasswordCommand(localStorage.getItem('url'), self.userId(), self.passwordNew(), self.passwordNewConfirm());

                service.submitForgotPass(command).done(function() {
                    localStorage.removeItem('url');
                    
                    var submitData = <SubmitData>{};
                    
                    //Set SubmitData
                    submitData.loginId = nts.uk.text.padRight(_.escape(localStorage.getItem('loginId')), " ", 12);
                    submitData.password = _.escape(self.passwordNew());
                    submitData.contractCode = _.escape(localStorage.getItem('contractCode'));
                    submitData.contractPassword = _.escape(localStorage.getItem('contractPassword'));
                    
                    localStorage.removeItem('loginId');
                    localStorage.removeItem('contractCode');
                    localStorage.removeItem('contractPassword');
                    
                    //login
                    service.submitLogin(submitData).done(function(messError) {
                        //Remove LoginInfo
                        nts.uk.request.jump("/view/ccg/008/a/index.xhtml", { screen: 'login' });
                        blockUI.clear();
                    });
                    
                }).fail(function(res) {
                    //Return Dialog Error
                    self.showMessageError(res);
                    blockUI.clear();
                });
            }

            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                let dfd = $.Deferred<any>();

                // check error business exception
                if (!res.businessException) {
                    return;
                }

                // show error message
                if (Array.isArray(res.errors)) {
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }

            //open dialog I 
            OpenDialogI() {
                let self = this;

                nts.uk.ui.windows.sub.modal('/view/ccg/007/i/index.xhtml', {
                    width: 520,
                    height: 300
                }).onClosed(function(): any { })
            }
        }
    }
}