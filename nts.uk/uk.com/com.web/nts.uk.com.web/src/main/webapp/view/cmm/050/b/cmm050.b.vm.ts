module nts.uk.com.view.cmm050.b {
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {
            emailFrom: KnockoutObservable<string>;
            emailTo: KnockoutObservable<string>;
            
            testButtonEnable: KnockoutObservable<boolean>;
            
            emailAuthOption: any;
            
            constructor(){
                let _self = this;
                
                _self.emailFrom = ko.observable("");
                _self.emailTo = ko.observable("");
                _self. emailAuthOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "350px"
                }));
                
                _self.testButtonEnable = ko.observable(true);
                
               _self.emailFrom.subscribe(function(emailString){
                   if(emailString.trim().length <= 0){
                        _self.emailFrom(emailString.trim());
                    }
                });
               _self.emailTo.subscribe(function(emailString){
                   if(emailString.trim().length <= 0){
                        _self.emailTo(emailString.trim());
                    }
               });
            }
            
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let _self = this;
                
                let params = getShared('CMM050Params');
                
                _self.emailFrom(params.emailAuth);
                _self.emailTo(params.emailAuth);
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
             /**
             * Close dialog.
             */
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }
            
            /**
             * test send mail
             */
            public testSendMail() :void {
                let _self = this;
                
                _self.testButtonEnable(false);
                
                if(_self.emailFrom().length <= 0){
                     $('#email1').ntsError('set', {messageId:"Msg_533"});
                    _self.testButtonEnable(true);
                    return;
                }
                 if(_self.emailTo().length <= 0){
                     $('#email2').ntsError('set', {messageId:"Msg_539"});
                     _self.testButtonEnable(true);
                    return;
                }
                
                 // Validate
                if (_self.hasError()) {
                    _self.testButtonEnable(true);
                    return;
                }
                
                var data = new model.MailServerTest(
                        _self.emailFrom(),
                        _self.emailTo(),
                        new model.MailContents());
                
                service.testMailServerSetting(data).done(function(){
                    nts.uk.ui.dialog.alert({ messageId: "Msg_534" });
                    _self.testButtonEnable(true);
                }).fail(function(error){
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                    _self.testButtonEnable(true);
                });
                
            }
            
            /**
             * getLabelPreview
             */
            public getLabelPreview(): any {
                let _self = this;
                
                let nameJP: string = nts.uk.resource.getText("CMM050_26");
                let lstComponent: string[] = nameJP.split("\n");
                
                let result: any = {first: null, second: null};
                
                if (lstComponent.length <= 0) {
                    result.first = nameJP;
                } else {
                    result.first = lstComponent[0];
                    result.second = lstComponent[1];
                    result.third = lstComponent[2];
                }
                return result;
            }
            
            /**
             * Check Errors all input.
             */
            private hasError(): boolean {
                let _self = this;
                _self.clearErrors();
               
                $('#email1').ntsEditor("validate");
                $('#email2').ntsEditor("validate");
                
                if ($('.nts-input').ntsError('hasError')) {
                    _self.testButtonEnable(true);
                    return true;
                }
                return false;
            }

            /**
             * Clear Errors
             */
            private clearErrors(): void {
    
                 // Clear errors
                $('#email1').ntsEditor("clear");
                $('#email2').ntsEditor("clear");
               
                // Clear error inputs
                $('.nts-input').ntsError('clear');
            }
        }
    }
    
}