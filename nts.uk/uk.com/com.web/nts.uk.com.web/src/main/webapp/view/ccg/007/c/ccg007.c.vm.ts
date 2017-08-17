module nts.uk.pr.view.ccg007.c {
    export module viewmodel {
        import SystemConfigDto = service.SystemConfigDto;
        import ContractDto = service.ContractDto;
        import blockUI = nts.uk.ui.block;
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
                blockUI.invisible();
                nts.uk.characteristics.restore("contractInfo").done(function(data) {
                    service.checkContract({ contractCode: data ? data.contractCode : "", contractPassword: data ? data.contractPassword : "" }).done(function(showContractData: any) {
                        if (showContractData) {
                            if (showContractData.showContract) {
                                self.openContractAuthDialog();
                            }
                            else {
                                self.getEmployeeLoginSetting(data.contractCode);
                            }
                        }
                        else {
                            //TODO システムエラー画面へ遷移する
                        }
                        blockUI.clear();
                        dfd.resolve();
                    }).fail(function() {
                        dfd.resolve();
                        blockUI.clear();
                    });
                }).fail(function() {
                    dfd.resolve();
                    blockUI.clear();
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
                blockUI.invisible();
                service.submitLogin({ companyCode: _.escape(self.companyCode()), employeeCode: _.escape(self.employeeCode()), password: _.escape(self.password()) }).done(function() {
                    nts.uk.characteristics.remove("form2LoginInfo");
                    if (self.isSaveLoginInfo()) {
                        nts.uk.characteristics.save("form2LoginInfo", { companyCode: _.escape(self.companyCode()), employeeCode: _.escape(self.employeeCode()) }).done(function() {
                            nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                        });
                    } else {
                        //TODO confirm kiban team promise for remove
                        setTimeout(function() {
                            nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                        }, 1000);
                    }
                    blockUI.clear();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                    blockUI.clear();
                });
            }
        }
    }
}