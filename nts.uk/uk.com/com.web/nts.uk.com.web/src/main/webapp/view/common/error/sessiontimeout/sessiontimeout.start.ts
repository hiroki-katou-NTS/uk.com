/// <reference path="../../../../lib/nittsu/ui/viewcontext.d.ts" />
/// <reference path="../../../../lib/nittsu/nts.uk.com.web.nittsu.bundles.d.ts" />
/// <reference path="../../../../lib/generic/jquery/jquery.d.ts" />



module common.error.system {
    __viewContext.ready(function() {
        var screenModel = new ScreenModel();
        __viewContext.bind(screenModel);
        screenModel.contractConfirmation();
    });

    interface ContractInfo {
        contractCode: string;
        password: string;
        issueUrl: string;
        requestUrl: string;
    }

    interface AuthenticateInfo {
        useSamlSso: boolean;
        authenUrl: string;
    }

    class ScreenModel {
        constructor() {
        }

        public gotoLogin() {
            nts.uk.characteristics.restore("loginMode").done(mode => {
                let rgc = nts.uk.ui.windows.rgc();
                if (mode) {
                    rgc.nts.uk.request.login.jumpToUsedSSOLoginPage();
                } else {
                    rgc.nts.uk.request.login.jumpToUsedLoginPage();
                }
            });
        }

        public contractConfirmation() {
            nts.uk.characteristics.restore("contractInfo").done(contractInfo => {
                // localstrageにテナント情報がある場合
                if(contractInfo !== undefined){
                    var contract: ContractInfo = {
                        contractCode: contractInfo ? contractInfo.contractCode : "",
                        password: contractInfo ? contractInfo.contractPassword : "",
                        issueUrl: location.href,
                        requestUrl: this.getParam("requestUrl", location.href)
                    };
                    this.contractAuthentication(contract);
                } else {
                    // localstrageにテナント情報がない場合
                    this.openContractAuthDialog();
                }
            })
        }

        public contractEntered() {
            var contract: ContractInfo = {
                contractCode: $("#tenantCode").val(),
                password: $("#tenantPass").val(),
                issueUrl: location.href,
                requestUrl: this.getParam("requestUrl", location.href)
            }
            this.contractAuthentication(contract);
        }

        public contractAuthentication(contractInfo: ContractInfo) {
            this.submitForm(contractInfo).done(authenticateInfo => {
                if(authenticateInfo.useSamlSso){
                    // SSO運用している場合
                    this.sendSamlRequest(authenticateInfo.authenUrl);
                } else {
                    // SSO運用していない場合
                    this.gotoLogin();
                }
            })
        }

        public submitForm(data: ContractInfo): JQueryPromise<AuthenticateInfo> {
            var servicePath = {
                authenticate: "ctx/sys/gateway/singlesignon/saml/authenticate"
            }
            return nts.uk.request.ajax(servicePath.authenticate, data);
        }

        public sendSamlRequest(requestUrl: string) {
            location.href = requestUrl;
        }

        public getParam(name:string, url:string) {
            if (!url) url = window.location.href;
            name = name.replace(/[\[\]]/g, "\\$&");
            var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                results = regex.exec(url);
            if (!results) return null;
            if (!results[2]) return '';
            return decodeURIComponent(results[2].replace(/\+/g, " "));
        }
        
        private openContractAuthDialog() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/ccg/007/a/index.xhtml", {
                height: 300,
                width: 400,
                title: nts.uk.resource.getText("CCG007_9"),
                dialogClass: 'no-close'
            }).onClosed(() => {
                var contract: ContractInfo = {
                    contractCode: nts.uk.ui.windows.getShared('contractCode'),
                    password: nts.uk.ui.windows.getShared('contractPassword'),
                    issueUrl: location.href,
                    requestUrl: this.getParam("requestUrl", location.href)
                };
                this.contractAuthentication(contract);
            });
        }

    }
}

