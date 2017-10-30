module nts.uk.com.view.cmm050.b {
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {
            emailFrom: KnockoutObservable<string>;
            emailTo: KnockoutObservable<string>;
            
            emailAuthOption: any;
            
            constructor(){
                let _self = this;
                
                let params = getShared('CMM050Params');
                
                _self.emailFrom = ko.observable(params.emailAuth);
                _self.emailTo = ko.observable(params.emailAuth);
                _self. emailAuthOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "350px"
                }));
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
            public testSendMail() {
                let _self = this;
                var dfd = $.Deferred<void>();
                
                var data = new model.MailServerTest(
                        _self.emailFrom(),
                        _self.emailTo(),
                        new model.MailContents());
                
                service.testMailServerSetting(data).done(function(){
                    nts.uk.ui.dialog.alert({ messageId: "Msg_534" });
                    dfd.resolve();
                }).fail(function(error){
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                });
                
                return dfd.promise();
            }
        }
    }
    
}