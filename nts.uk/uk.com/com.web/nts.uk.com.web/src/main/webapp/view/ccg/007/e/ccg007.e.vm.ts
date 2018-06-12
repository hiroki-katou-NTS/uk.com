module nts.uk.pr.view.ccg007.e {
    export module viewmodel {
        import blockUI = nts.uk.ui.block;
        import CallerParameter = service.CallerParameter;
        import ChangePasswordCommand = service.ChangePasswordCommand;

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
                
                if (nts.uk.ui.errors.hasError()) {
                    return;                   
                }
                
                blockUI.invisible();
                
                //add command
                let command: ChangePasswordCommand = new ChangePasswordCommand(self.passwordCurrent(), self.passwordNew(), self.passwordNewConfirm());
                
                //submitChangePass
                service.submitChangePass(command).done(function () {
                    let returnedData = {
                            submit: true
                        };
                    nts.uk.ui.windows.setShared("childData", returnedData, false);
                    
                    self.closeDialog();
                    blockUI.clear();
                }).fail(function(res) {
                    //Return Dialog Error
                    nts.uk.ui.dialog.alertError(res.message);
                    blockUI.clear();
                });
                
            }
            
            //open dialog I 
            OpenDialogI() {
                let self = this;

                nts.uk.ui.windows.sub.modal('/view/ccg/007/i/index.xhtml',{
                    width : 520,
                    height : 300
                }).onClosed(function(): any {})
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