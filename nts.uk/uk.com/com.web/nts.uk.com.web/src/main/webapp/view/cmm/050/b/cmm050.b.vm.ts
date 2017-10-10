module nts.uk.com.view.cmm050.b {
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {
            emailAuth: KnockoutObservable<string>;
            emailAuth1: KnockoutObservable<string>;
            
            emailAuthOption: any;
            
            constructor(){
                let _self = this;
                
                let params = getShared('CMM050Params');
                
                _self.emailAuth = ko.observable(params.emailAuth);
                _self.emailAuth1 = ko.observable(null);
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
            
            public testSendMail() {
                //TODO: pending
                nts.uk.ui.windows.close();
            }
        }
    }
    
}