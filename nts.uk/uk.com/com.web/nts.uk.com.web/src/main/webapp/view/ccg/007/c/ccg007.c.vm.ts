module nts.uk.pr.view.ccg007.c {
    export module viewmodel {
        import SystemConfigDto = service.SystemConfigDto;
        import ContractDto = service.ContractDto;
        export class ScreenModel {
            companyCode: KnockoutObservable<string>;
            employeeCode: KnockoutObservable<string>;
            password: KnockoutObservable<string>;
            isSaveLoginInfo: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.companyCode = ko.observable('');
                self.employeeCode = ko.observable('');
                self.password = ko.observable('');
                self.isSaveLoginInfo = ko.observable(true);
            }

            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                //get system config
                //TODO get local contract info
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
                        dfd.resolve();
                        //TODO システムエラー画面へ遷移する    
                    });
                }).fail(function() {
                    alert();
                    dfd.resolve();
                    //TODO システムエラー画面へ遷移する    
                });
                return dfd.promise();
            }

            private getEmployeeLoginSetting(contractCode: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.getEmployeeLoginSetting(contractCode).done(function(data) {
                    if (data.gotoForm1) {
                        nts.uk.request.jump("/view/ccg/007/b/index.xhtml");
                    }
                    else {
                        //TODO get login ID and set here
                        nts.uk.characteristics.restore("form2LoginInfo").done(function(loginInfo) {
                            if (loginInfo) {
                                self.companyCode(loginInfo.companyCode);
                                self.employeeCode(loginInfo.employeeCode);
                            }
                            dfd.resolve();
                        });
                    }
                });
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

            private submitLogin() {
                var self = this;
                service.submitLogin({ companyCode: self.companyCode(), employeeCode: self.employeeCode(), password: self.password() }).done(function() {
                    nts.uk.characteristics.remove("form2LoginInfo");
                    if (self.isSaveLoginInfo()) {
                        nts.uk.characteristics.save("form2LoginInfo", { companyCode: self.companyCode(), employeeCode: self.employeeCode() }).done(function() {
                            nts.uk.request.jump("/view/ccg/015/a/index.xhtml");
                        });
                    } else {
                        nts.uk.request.jump("/view/ccg/015/a/index.xhtml");
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                });
            }
        }
    }
}