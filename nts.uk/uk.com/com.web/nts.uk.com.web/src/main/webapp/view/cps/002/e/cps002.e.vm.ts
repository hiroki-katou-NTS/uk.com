module cps002.e.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import modal = nts.uk.ui.windows.sub.modal;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;

    export class ViewModel {
        cardNoMode: boolean = false;
        txtEmployeeCode: KnockoutObservable<string> = ko.observable("");
        txtCardNo: KnockoutObservable<string> = ko.observable("");
        generateEmCode: KnockoutObservable<string> = ko.observable("");
        constructor() {
            let self = this;
            self.cardNoMode = getShared("cardNoMode");
            if (self.cardNoMode) {
                $("#txtCardNo").focus();
            } else {
                $("#txtEmployeeCode").focus();
            }
            self.generateEmCode(getShared("value"));
        }

        getCode() {
            let self = this;
            self.cardNoMode ? self.getCardNo() : self.getEmlCode();
        }

        getEmlCode() {
            let self = this;
            service.getEmlCode(self.txtEmployeeCode()).done(function(emCode) {
                self.generateEmCode(emCode);

            }).fail(function() {
                alertError({ messageId: "Msg_505" });
            });
        }

        getCardNo() {
            let self = this;
            service.getCardNo(self.txtCardNo()).done(function(emCode) {
                self.generateEmCode(emCode);

            }).fail(function() {
                alertError({ messageId: "Msg_505" });
            });
        }

        returnEmCode() {
            let self = this;
            setShared("CPS002_PARAM", self.generateEmCode());
            close();
        }

        close() {
            close();
        }
    }
}