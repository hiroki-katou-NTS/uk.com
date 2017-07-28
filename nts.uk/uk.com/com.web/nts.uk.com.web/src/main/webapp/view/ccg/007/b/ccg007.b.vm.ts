module nts.uk.pr.view.ccg007.b {
    export module viewmodel {
        import SystemConfigDto = service.SystemConfigDto;
        import ContractDto = service.ContractDto;
        export class ScreenModel {
            loginId: KnockoutObservable<string>;
            password: KnockoutObservable<string>;
            parentLayoutId: KnockoutObservable<string>;
            newTopPageCode: KnockoutObservable<string>;
            newTopPageName: KnockoutObservable<string>;
            isDuplicateCode: KnockoutObservable<boolean>;
            check: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.loginId = ko.observable('');
                self.password = ko.observable('');
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                //get system config
                service.getLoginForm().done(function(data: any) {
                    if (data) {
                        self.loginId(data);
                    }
                    else {
                        self.openContractAuthDialog();
                    }
                }).fail(function() {
                    //TODO システムエラー画面へ遷移する    
                });
                dfd.resolve();
                return dfd.promise();
            }

            //TODO when invalid contract 
            private openContractAuthDialog() {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/ccg/007/a/index.xhtml", {
                    height: 320, width: 400,
                    title: "契約認証",
                    dialogClass: 'no-close'
                }).onClosed(() => {
                });
            }
            
            private submitLogin(){
                var self = this;
                service.submitLogin({loginId: self.loginId(),password: self.password()}).done(function(){
                //TODO check login    
                });
            }
        }
    }
}