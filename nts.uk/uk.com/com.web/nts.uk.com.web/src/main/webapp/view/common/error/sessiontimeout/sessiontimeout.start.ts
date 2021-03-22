module common.error.system {
    @bean()
    export class ScreenModel extends ko.ViewModel {
        constructor() {
            super();

            const vm = this;

            vm.$window.header(false);
        }

        gotoLogin() {
            nts.uk.characteristics
                .restore("loginMode")
                .done(mode => {
                    let rgc = nts.uk.ui.windows.rgc();

                    if (mode) {
                        rgc.nts.uk.request.login.jumpToUsedSSOLoginPage();
                    } else {
                        rgc.nts.uk.request.login.jumpToUsedLoginPage();
                    }
                });
        }
    }
}