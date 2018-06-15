module nts.uk.pr.view.ccg007.f {
    export module viewmodel {
        import blockUI = nts.uk.ui.block;
        import CallerParameter = service.CallerParameter;
        import SendMailReturnDto = service.SendMailReturnDto;

        export class ScreenModel {
            
            loginId: KnockoutObservable<string>;
            
            // Parameter from caller screen.
            callerParameter: CallerParameter;
            
            constructor(parentData: CallerParameter) {
                var self = this;
                
                self.loginId = ko.observable(null);
                
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
                
                service.submitSendMail(self.callerParameter).done(function (data: SendMailReturnDto) {
                    if (!nts.uk.util.isNullOrEmpty(data.url)){
                        nts.uk.ui.dialog.info({ messageId: "Msg_207" }).then(() => {
                            blockUI.invisible();
                            self.OpenDialogH(data.url);
                            blockUI.clear();
                        });
                    }
                }).fail(function(res) {
                    //Return Dialog Error
                    nts.uk.ui.dialog.alertError(res.message);
                    blockUI.clear();
                });
                
            }
            
            //open dialog H 
            private OpenDialogH(url: string) {
                let self = this;
                
                //set LoginId and contractCode to LocalStorage
                localStorage.setItem('loginId', self.loginId());
                localStorage.setItem('contractCode', self.callerParameter.contractCode);
                localStorage.setItem('url', url);
                
                //jump CCG007H
                nts.uk.request.jump("/view/ccg/007/h/index.xhtml");
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