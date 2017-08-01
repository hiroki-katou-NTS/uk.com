module nts.uk.pr.view.ccg007.c {
    export module viewmodel {
        import SystemConfigDto = service.SystemConfigDto;
        import ContractDto = service.ContractDto;
        export class ScreenModel {
            companyCode: KnockoutObservable<string>;
            employeeCode: KnockoutObservable<string>;
            password: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.companyCode = ko.observable('');
                self.employeeCode = ko.observable('');
                self.password = ko.observable('');
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                //get system config
                service.getLoginForm().done(function(data: any) {
                    if (data.showContract != "show contract") {
                        self.companyCode(data);
                    }
                    else {
                        self.openContractAuthDialog();
                    }
                    dfd.resolve();
                }).fail(function() {
                    alert();
                    dfd.resolve();
                    //TODO システムエラー画面へ遷移する    
                });
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

            private submitLogin() {
                var self = this;
                service.submitLogin({ companyCode: self.companyCode(),employeeCode: self.employeeCode(),password: self.password() }).done(function() {
                    //TODO check login    
                });
            }
        }
    }
}