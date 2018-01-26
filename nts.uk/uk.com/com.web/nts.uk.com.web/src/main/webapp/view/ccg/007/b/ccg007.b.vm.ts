module nts.uk.pr.view.ccg007.b {
    export module viewmodel {
        import SystemConfigDto = service.SystemConfigDto;
        import ContractDto = service.ContractDto;
        import blockUI = nts.uk.ui.block;
        export class ScreenModel {
            loginId: KnockoutObservable<string>;
            password: KnockoutObservable<string>;
            contractCode: KnockoutObservable<string>;
            contractPassword: KnockoutObservable<string>;
            isSaveLoginInfo: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.loginId = ko.observable('');
                self.password = ko.observable('');
                self.contractCode = ko.observable('');
                self.contractPassword = ko.observable('');
                self.isSaveLoginInfo = ko.observable(true);
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                //get system config
                //get local contract info
                blockUI.invisible();
                nts.uk.characteristics.restore("contractInfo").done(function(data) {
                    self.contractCode(data?data.contractCode:"");
                    self.contractPassword(data?data.contractPassword:"");
                    service.checkContract({ contractCode: data ? data.contractCode : "", contractPassword: data ? data.contractPassword : "" }).done(function(data: any) {
                        if (data) {
                            if (data.showContract) {
                                self.openContractAuthDialog();
                            }
                            else {
                                //Get login ID and set here
                                nts.uk.characteristics.restore("form1LoginInfo").done(function(loginInfo) {
                                    if (loginInfo) {
                                        self.loginId(loginInfo.loginId);
                                    }
                                });
                            }
                        }
                        else {
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
                });
            }

            private submitLogin() {
                var self = this;
                var submitData: any = {};
                submitData.loginId = nts.uk.text.padRight(_.escape(self.loginId()), " ", 12);
                submitData.password = _.escape(self.password());
                submitData.contractCode = _.escape(self.contractCode());
                submitData.contractPassword = _.escape(self.contractPassword());
                
                blockUI.invisible();
                if (!nts.uk.ui.errors.hasError()) {
                    service.submitLogin(submitData).done(function() {
                        nts.uk.request.login.keepUsedLoginPage();
                        nts.uk.characteristics.remove("form1LoginInfo").done(function() {
                            if (self.isSaveLoginInfo()) {
                                nts.uk.characteristics.save("form1LoginInfo", { loginId: _.escape(self.loginId()) }).done(function() {
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
        }
    }
}