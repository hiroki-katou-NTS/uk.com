module nts.uk.pr.view.ccg007.b {
    export module viewmodel {
        import SystemConfigDto = service.SystemConfigDto;
        import ContractDto = service.ContractDto;
        import blockUI = nts.uk.ui.block;
        export class ScreenModel {
            loginId: KnockoutObservable<string>;
            password: KnockoutObservable<string>;
            isSaveLoginInfo: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.loginId = ko.observable('');
                self.password = ko.observable('');
                self.isSaveLoginInfo = ko.observable(true);
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                //get system config
                //get local contract info
                blockUI.invisible();
                nts.uk.characteristics.restore("contractInfo").done(function(data) {
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
                });
            }

            private submitLogin() {
                var self = this;
                blockUI.invisible();
                if (!nts.uk.ui.errors.hasError()) {
                    service.submitLogin({ loginId: _.escape(self.loginId()), password: _.escape(self.password()) }).done(function() {
                        nts.uk.characteristics.remove("form1LoginInfo").done(function() {
                            if (self.isSaveLoginInfo()) {
                                nts.uk.characteristics.save("form1LoginInfo", { loginId: _.escape(self.loginId()) }).done(function() {
                                    nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                                });
                            } else {
                                nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
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