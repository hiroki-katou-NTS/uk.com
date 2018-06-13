module nts.uk.pr.view.ccg007.g {
    export module viewmodel {
        import blockUI = nts.uk.ui.block;
        import CallerParameter = service.CallerParameter;
        import SendMailInfoFormGCommand = service.SendMailInfoFormGCommand;
        

        export class ScreenModel {
            
            companyCode: KnockoutObservable<string>;
            companyName: KnockoutObservable<string>;
            employeeCode: KnockoutObservable<string>;
            
            // Parameter from caller screen.
            callerParameter: CallerParameter;
            
            constructor(parentData: CallerParameter) {
                var self = this;
                
                self.companyCode = ko.observable(null);
                self.companyName = ko.observable(null);
                self.employeeCode = ko.observable(null);
                
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
                
                self.companyName(self.callerParameter.companyName);
                self.employeeCode(self.callerParameter.employeeCode);
                
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
                
                //add command
                let command: SendMailInfoFormGCommand = new SendMailInfoFormGCommand(self.companyCode, self.employeeCode, self.callerParameter.contractCode);
                
                service.submitSendMail(command).done(function () {
                    if (!nts.uk.util.isNullOrEmpty(data.url)){
                        nts.uk.ui.dialog.info({ messageId: "Msg_207" }).then(() => {
                            blockUI.invisible();
                            self.OpenDialogH(data.url);
                            blockUI.clear();
                        });
                    }
                }).fail(function(res) {
                    //Return Dialog Error
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                    blockUI.clear();
                });
                
            }
            
            //open dialog H 
            private OpenDialogH(url: string) {
                let self = this;
                
                //set LoginId to dialog
                nts.uk.ui.windows.setShared('parentCodes', {
                    loginId: self.callerParameter.loginId,
                    contractCode: self.callerParameter.contractCode,
                    url: url
                }, true);

                nts.uk.ui.windows.sub.modal('/view/ccg/007/h/index.xhtml',{
                    width : 700,
                    height : 350
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