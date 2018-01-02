module nts.uk.pr.view.ccg007.d {
    export module viewmodel {
        import SystemConfigDto = service.SystemConfigDto;
        import ContractDto = service.ContractDto;
        import blockUI = nts.uk.ui.block;
        export class ScreenModel {
            employeeCode: KnockoutObservable<string>;
            password: KnockoutObservable<string>;
            companyList: KnockoutObservableArray<CompanyItemModel>;
            selectedCompanyCode: KnockoutObservable<string>;
            isSaveLoginInfo: KnockoutObservable<boolean>;
            contractCode: KnockoutObservable<string>;
            contractPassword: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.employeeCode = ko.observable('');
                self.password = ko.observable('');
                self.companyList = ko.observableArray([]);
                self.selectedCompanyCode = ko.observable('');
                self.isSaveLoginInfo = ko.observable(true);
                self.contractCode = ko.observable('');
                self.contractPassword = ko.observable('');
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                //get system config
                blockUI.invisible();
                nts.uk.characteristics.restore("contractInfo").done(function(data) {
                    self.contractCode(data?data.contractCode:"");
                    self.contractPassword(data?data.contractPassword:"");
                    service.checkContract({ contractCode: data ? data.contractCode : "", contractPassword: data ? data.contractPassword : "" }).done(function(showContractData: any) {
                        if (showContractData) {
                            if (showContractData.showContract) {
                                self.openContractAuthDialog();
                            }
                            else {
                                if (data) {
                                    self.getEmployeeLoginSetting(data.contractCode);
                                }
                                else {
                                    nts.uk.request.jump("/view/ccg/007/b/index.xhtml");
                                }
                            }
                            dfd.resolve();
                        }
                        else {
                        }
                        blockUI.clear();
                    }).fail(function() {
                        dfd.resolve();
                        blockUI.clear();
                    });
                });
                dfd.resolve();
                return dfd.promise();
            }

            //when invalid contract 
            private openContractAuthDialog() {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/ccg/007/a/index.xhtml", {
                    height: 300,
                    width: 400,
                    title: nts.uk.resource.getText("CCG007_9"),
                    dialogClass: 'no-close'
                }).onClosed(() => {
                    var contractCode = nts.uk.ui.windows.getShared('contractCode');
                    var contractPassword = nts.uk.ui.windows.getShared('contractPassword');
                    self.contractCode(contractCode);
                    self.contractPassword(contractPassword);
                    service.getAllCompany().done(function(data: Array<CompanyItemModel>) {
                        //get list company from server 
                        self.companyList(data);
                        if (data.length > 0) {
                            self.selectedCompanyCode(self.companyList()[0].companyCode);
                        }
                    });
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
                        service.getAllCompany().done(function(data: Array<CompanyItemModel>) {
                            //get list company from server 
                            self.companyList(data);
                            if (data.length > 0) {
                                self.selectedCompanyCode(self.companyList()[0].companyCode);
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
                var submitData: any = {};
                submitData.companyCode = _.escape(self.selectedCompanyCode());
                submitData.employeeCode = _.escape(self.employeeCode());
                submitData.password = _.escape(self.password());
                submitData.contractCode = _.escape(self.contractCode());
                submitData.contractPassword = _.escape(self.contractPassword());
                blockUI.invisible();
                service.submitLogin(submitData).done(function() {
                    nts.uk.request.login.keepUsedLoginPage();
                    nts.uk.characteristics.remove("form3LoginInfo").done(function() {
                        if (self.isSaveLoginInfo()) {
                            nts.uk.characteristics.save("form3LoginInfo", { companyCode: _.escape(self.selectedCompanyCode()), employeeCode: _.escape(self.employeeCode()) }).done(function() {
                                nts.uk.request.jump("/view/ccg/008/a/index.xhtml", {screen: 'login'});
                            });
                        } else {
                            nts.uk.request.jump("/view/ccg/008/a/index.xhtml", {screen: 'login'});
                        }
                    });
                    blockUI.clear();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                    blockUI.clear();
                });
            }
        }
        export class CompanyItemModel {
            companyId: string;
            companyCode: string;
            companyName: string;
        }
    }
}