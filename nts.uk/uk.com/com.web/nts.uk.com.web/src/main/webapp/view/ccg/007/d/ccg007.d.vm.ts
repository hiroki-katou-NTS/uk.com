module nts.uk.pr.view.ccg007.d {
    export module viewmodel {
        import SystemConfigDto = service.SystemConfigDto;
        import ContractDto = service.ContractDto;
        export class ScreenModel {
            employeeCode: KnockoutObservable<string>;
            password: KnockoutObservable<string>;
            companyList: KnockoutObservableArray<CompanyItemModel>;
            selectedCompanyCode: KnockoutObservable<string>;
            isSaveLoginInfo: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.employeeCode = ko.observable('');
                self.password = ko.observable('');
                self.companyList = ko.observableArray([]);
                self.selectedCompanyCode = ko.observable('');
                self.isSaveLoginInfo = ko.observable(true);
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                //get system config
                nts.uk.characteristics.restore("contractInfo").done(function(data) {
                    service.checkContract({ contractCode: data ? data.contractCode : "", contractPassword: data ? data.contractPassword : "" }).done(function(showContractData: any) {
                        if (showContractData.showContract) {
                            self.openContractAuthDialog();
                        }
                        else {
                            self.getEmployeeLoginSetting(data.contractCode);
                        }
                        dfd.resolve();
                    }).fail(function() {
                        alert();
                        //TODO システムエラー画面へ遷移する    
                    });
                });
                dfd.resolve();
                return dfd.promise();
            }

            //TODO when invalid contract 
            private openContractAuthDialog() {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/ccg/007/a/index.xhtml", {
                    height: 320,
                    width: 500,
                    title: nts.uk.resource.getText("CCG007_9"),
                    dialogClass: 'no-close'
                }).onClosed(() => {
                });
            }

            private getEmployeeLoginSetting(contractCode: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.getEmployeeLoginSetting(contractCode).done(function(data) {
                    if (data.gotoForm1) {
                        nts.uk.request.jump("/view/ccg/007/b/index.xhtml");
                    }
                    else {
                        service.getAllCompany(contractCode).done(function(data: Array<CompanyItemModel>) {
                            //get list company from server 
                            self.companyList(data);
//                            self.companyList([new CompanyItemModel("1234", "会社1"), new CompanyItemModel("0001", "会社2"), new CompanyItemModel("0002", "会社3"), new CompanyItemModel("0002", "会社3"), new CompanyItemModel("0002", "会社3"), new CompanyItemModel("0002", "会社9"), new CompanyItemModel("0002", "会社6"), new CompanyItemModel("0002", "会社8")]);
                            if (data.length > 0) {
                                self.selectedCompanyCode(self.companyList()[0].code);
                            }
                            //get local storage info and set here
                            nts.uk.characteristics.restore("form3LoginInfo").done(function(loginInfo) {
                                if (loginInfo) {
                                    self.selectedCompanyCode(loginInfo.companyCode);
                                    self.employeeCode(loginInfo.employeeCode);
                                }
                                dfd.resolve();
                            });
                        });
                    }
                });
                return dfd.promise();
            }

            private submitLogin() {
                var self = this;
                service.submitLogin({ companyCode: _.escape(self.selectedCompanyCode()), employeeCode: _.escape(self.employeeCode()), password: _.escape(self.password()) }).done(function() {
                    nts.uk.characteristics.remove("form3LoginInfo");
                    if (self.isSaveLoginInfo()) {
                        nts.uk.characteristics.save("form3LoginInfo", { companyCode:_.escape( self.selectedCompanyCode()), employeeCode: _.escape(self.employeeCode()) }).done(function() {
                            nts.uk.request.jump("/view/ccg/015/a/index.xhtml");
                        });
                    } else {
                        //TODO confirm kiban team promise for remove
                        setTimeout(function() {
                            nts.uk.request.jump("/view/ccg/015/a/index.xhtml");
                        }, 1000);
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
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