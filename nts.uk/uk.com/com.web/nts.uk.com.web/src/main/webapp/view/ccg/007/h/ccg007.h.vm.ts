module nts.uk.pr.view.ccg007.h {
    export module viewmodel {
        import blockUI = nts.uk.ui.block;
        import CallerParameter = service.CallerParameter;

        export class ScreenModel {
            
            loginId: KnockoutObservable<string>;
            passwordCurrent: KnockoutObservable<string>;
            passwordNew: KnockoutObservable<string>;
            passwordNewConfirm: KnockoutObservable<string>;
            
            // Parameter from caller screen.
            callerParameter: CallerParameter;
            
            constructor(parentData: CallerParameter) {
                var self = this;
                
                self.loginId = ko.observable(null);
                self.passwordCurrent = ko.observable(null);
                self.passwordNew = ko.observable(null);
                self.passwordNewConfirm = ko.observable(null);
                
                //parent data
                self.callerParameter = parentData;
            }
            
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui
                nts.uk.ui.block.invisible();
                
                self.loginId(self.callerParameter.loginId);
                
                dfd.resolve();
                
                //clear block
                nts.uk.ui.block.clear();

                return dfd.promise();
            }
            
            /**
             * Submit
             */
            public submit(): void{
                let self = this;
                
                blockUI.invisible();
                
                service.submitForgotPass(self.callerParameter.url).done(function () {
                    self.closeDialog();
                    nts.uk.request.jump("/view/ccg/008/a/index.xhtml", { screen: 'login' });
                    blockUI.clear();
                }).fail(function(res) {
                    //Return Dialog Error
                    nts.uk.ui.dialog.alertError(res.message);
                    blockUI.clear();
                });
                
            }
            
            /**
             * close dialog
             */
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
    }
}