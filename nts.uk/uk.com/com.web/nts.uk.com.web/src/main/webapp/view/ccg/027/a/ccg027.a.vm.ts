module nts.uk.com.view.ccg027.a.viewmodel {


    export class ScreenModel {

        sendingAddressChecks: KnockoutObservable<boolean> = ko.observable(false);
        sendingAddressEnable: KnockoutObservable<boolean> = ko.observable(true);

        ListReturnCC: KnockoutObservableArray<string>;
        ListReturnBCC: KnockoutObservableArray<string>;

        mailAddressCC: KnockoutObservable<string>;
        mailAddressBCC: KnockoutObservable<string>;
        subject: KnockoutObservable<string>;
        text: KnockoutObservable<string>;
        mailRely: KnockoutObservable<string>;
        wording: KnockoutObservable<string>;

        senderAddress: KnockoutObservable<string>;

        constructor() {
            var self = this;

            self.ListReturnCC = ko.observableArray([]);
            self.ListReturnBCC = ko.observableArray([]);

            self.sendingAddressChecks(nts.uk.ui.windows.getShared("sendingAddressCheck"));
            self.senderAddress = ko.observable("");
            self.mailAddressCC = ko.observable(nts.uk.ui.windows.getShared("MailSettings").mailAddressCC.toString().replace(/,/g, ";"));
            self.mailAddressBCC = ko.observable(nts.uk.ui.windows.getShared("MailSettings").mailAddressBCC.toString().replace(/,/g, ";"));
            self.subject = ko.observable(nts.uk.ui.windows.getShared("MailSettings").subject);
            self.text = ko.observable(nts.uk.ui.windows.getShared("MailSettings").text);
            self.mailRely = ko.observable(nts.uk.ui.windows.getShared("MailSettings").mailRely);
            self.wording = ko.observable(nts.uk.ui.windows.getShared("wording"));

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            if (self.sendingAddressChecks()) {
                // initial screen
                service.mailServer().done(function(data: any) {
                    if (data) {
                        if (data.useAuth === 1) {
                            self.senderAddress = ko.observable(data.emailAuthencation);
                            self.sendingAddressEnable = ko.observable(false);
                        } else {
                            self.senderAddress = ko.observable(nts.uk.ui.windows.getShared("senderAddress"));
                        }
                    } else {
                        self.senderAddress = ko.observable(nts.uk.ui.windows.getShared("senderAddress"));
                    }
                    dfd.resolve();
                });
            } else {
                dfd.resolve();
            }
            return dfd.promise();
        }

        checkContenListMail(): boolean {
            var self = this;
            self.ListReturnCC(self.mailAddressCC().replace(/\s/g, '').split(";"));
            self.ListReturnBCC(self.mailAddressBCC().replace(/\s/g, '').split(";"));

            // remove email = ""
            self.ListReturnCC(self.ListReturnCC.remove(function(item) { return item.length > 0; }));
            self.ListReturnBCC(self.ListReturnBCC.remove(function(item) { return item.length > 0; }));

            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if (self.ListReturnCC().length > 0) {
                for (let entry of self.ListReturnCC()) {
                    if (!re.test(entry.toLowerCase()) || entry.length > 256) {
                        $('#mailAddressCC').ntsError('set', { messageId: "Msg_1097" });
                        //nts.uk.ui.dialog.alertError({ messageId: "Msg_1097" });
                        return false;
                    }
                }
                if (self.ListReturnCC().length > 100) {
                    $('#mailAddressCC').ntsError('set', { messageId: "Msg_1098" });
                    //nts.uk.ui.dialog.alertError({ messageId: "Msg_1098" });
                    return false;
                }
            }
            if (self.ListReturnBCC().length > 0) {
                for (let entry of self.ListReturnBCC()) {
                    if (!re.test(entry.toLowerCase()) || entry.length > 256) {
                        $('#mailAddressBCC').ntsError('set', { messageId: "Msg_1099" });
                        //nts.uk.ui.dialog.alertError({ messageId: "Msg_1099" });
                        return false;
                    }
                }
                if (self.ListReturnBCC().length > 100) {
                    $('#mailAddressBCC').ntsError('set', { messageId: "Msg_1100" });
                    //nts.uk.ui.dialog.alertError({ messageId: "Msg_1100" });
                    return false;
                }
            }

            if (self.sendingAddressChecks() && self.senderAddress().length != 0) {
                if (!re.test(self.senderAddress().toLowerCase())) {
                    $('#senderAddress').ntsError('set', { messageId: "Msg_1112" });
                    //nts.uk.ui.dialog.alertError({ messageId: "Msg_1112" });
                    return false;
                }
                if (self.senderAddress().length > 256) {
                    $('#senderAddress').ntsError('set', { messageId: "Msg_1113" });
                    //nts.uk.ui.dialog.alertError({ messageId: "Msg_1113" });
                    return false;
                }
            }

            if (!re.test(self.mailRely().toLowerCase()) && self.mailRely().length != 0) {
                $('#mailRely').ntsError('set', { messageId: "Msg_1114" });
                //nts.uk.ui.dialog.alertError({ messageId: "Msg_1114" });
                return false;
            }
            if (self.mailRely().length > 256 && self.mailRely().length != 0) {
                $('#mailRely').ntsError('set', { messageId: "Msg_1115" });
                //nts.uk.ui.dialog.alertError({ messageId: "Msg_1115" });
                return false;
            }
            return true;
        }


        decision() {
            var self = this;
            if (self.checkContenListMail()) {
                var MailSettings = ({
                    subject: self.subject(),
                    text: self.text(),
                    mailAddressCC: self.ListReturnCC(),
                    mailAddressBCC: self.ListReturnBCC(),
                    mailRely: self.mailRely()
                });
                nts.uk.ui.windows.setShared("senderAddress", self.senderAddress());
                nts.uk.ui.windows.setShared("MailSettings", MailSettings);
                nts.uk.ui.windows.close();
            }
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.setShared("MailSettings", null);
            nts.uk.ui.windows.close();
        }

    }
}