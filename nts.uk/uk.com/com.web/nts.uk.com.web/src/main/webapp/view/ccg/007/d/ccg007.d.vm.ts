module nts.uk.pr.view.ccg007.d {
    export module viewmodel {
        import SystemConfigDto = service.SystemConfigDto;
        import ContractDto = service.ContractDto;
        export class ScreenModel {
            companyCode: KnockoutObservable<string>;
            employeeCode: KnockoutObservable<string>;
            password: KnockoutObservable<string>;
            companyList: KnockoutObservableArray<CompanyItemModel>;
            selectedCompanyCode: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.companyCode = ko.observable('');
                self.employeeCode = ko.observable('');
                self.password = ko.observable('');
                self.companyList = ko.observableArray([]);
                self.selectedCompanyCode = ko.observable('');
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
                }).fail(function() {
                    alert();
                    //TODO システムエラー画面へ遷移する    
                });
                service.getAllCompany().done(function(data: Array<CompanyItemModel>) {
                    //TODO get list company from server 
                    //                    self.companyList(data);
                    self.companyList([new CompanyItemModel("01", "no1"), new CompanyItemModel("02", "no2"), new CompanyItemModel("03", "no3")]);
                    self.selectedCompanyCode(data[0].code);
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

            private submitLogin() {
                var self = this;
                service.submitLogin({ companyCode: self.companyCode(), employeeCode: self.employeeCode(), password: self.password() }).done(function() {
                    //TODO check login    
                });
            }
        }
        export class CompanyItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}