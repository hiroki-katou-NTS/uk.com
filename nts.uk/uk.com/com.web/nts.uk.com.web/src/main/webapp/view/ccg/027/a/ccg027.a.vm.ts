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
        
        SetCC: KnockoutObservable<boolean>;
        SetBCC: KnockoutObservable<boolean>;
        SetReply: KnockoutObservable<boolean>;
        SetSubject: KnockoutObservable<boolean>;
        SetBody: KnockoutObservable<boolean>;
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
            
            self.SetCC = ko.observable(nts.uk.ui.windows.getShared("SetCC"));
            self.SetBCC = ko.observable(nts.uk.ui.windows.getShared("SetBCC"));
            self.SetReply = ko.observable(nts.uk.ui.windows.getShared("SetReply"));
            self.SetSubject = ko.observable(nts.uk.ui.windows.getShared("SetSubject"));
            self.SetBody = ko.observable(nts.uk.ui.windows.getShared("SetBody"));
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
            var kt = true;
            self.ListReturnCC(self.clean(self.mailAddressCC().replace(/\s/g, '').split(";")));
            self.ListReturnBCC(self.clean(self.mailAddressBCC().replace(/\s/g, '').split(";")));

            // remove email = ""
            self.ListReturnCC(self.ListReturnCC.remove(function(item) { return item.length > 0; }));
            self.ListReturnBCC(self.ListReturnBCC.remove(function(item) { return item.length > 0; }));

            var re = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            $('#mailAddressCC').ntsError('clear');
            $('#mailAddressBCC').ntsError('clear');
            $('#senderAddress').ntsError('clear');
            $('#mailRely').ntsError('clear');
            
            if (self.ListReturnCC().length > 0) {
                for (let entry of self.ListReturnCC()) {
                    if (!re.test(entry) || entry.length > 256) {
                        $('#mailAddressCC').ntsError('set', { messageId: "Msg_1097" });
                        //nts.uk.ui.dialog.alertError({ messageId: "Msg_1097" });
                        kt = false;
                        break;
                    }
                }
                if (self.ListReturnCC().length > 100) {
                    $('#mailAddressCC').ntsError('set', { messageId: "Msg_1098" });
                    //nts.uk.ui.dialog.alertError({ messageId: "Msg_1098" });
                    kt = false;
                }
            }
            if (self.ListReturnBCC().length > 0) {
                for (let entry of self.ListReturnBCC()) {
                    if (!re.test(entry) || entry.length > 256) {
                        $('#mailAddressBCC').ntsError('set', { messageId: "Msg_1099" });
                        //nts.uk.ui.dialog.alertError({ messageId: "Msg_1099" });
                        kt = false;
                        break;
                    }
                }
                if (self.ListReturnBCC().length > 100) {
                    $('#mailAddressBCC').ntsError('set', { messageId: "Msg_1100" });
                    //nts.uk.ui.dialog.alertError({ messageId: "Msg_1100" });
                    kt = false;
                }
            }

            if (self.sendingAddressChecks() && self.senderAddress().length != 0) {
                if (self.clean(self.senderAddress().replace(/\s/g, '').split(";")).length > 1) {
                    $('#senderAddress').ntsError('set', { messageId: "Msg_1113" });
                    //nts.uk.ui.dialog.alertError({ messageId: "Msg_1113" });
                    kt = false;
                }else if(!re.test(self.senderAddress())) {
                    $('#senderAddress').ntsError('set', { messageId: "Msg_1112" });
                    //nts.uk.ui.dialog.alertError({ messageId: "Msg_1112" });
                    kt = false;
                }
            }
            
            if (self.clean(self.mailRely().replace(/\s/g, '').split(";")).length > 1 && self.mailRely().length != 0) {
                $('#mailRely').ntsError('set', { messageId: "Msg_1115" });
                //nts.uk.ui.dialog.alertError({ messageId: "Msg_1115" });
                kt = false;
            }else if (!re.test(self.mailRely()) && self.mailRely().length != 0) {
                $('#mailRely').ntsError('set', { messageId: "Msg_1114" });
                //nts.uk.ui.dialog.alertError({ messageId: "Msg_1114" });
                kt = false;
            }
            $('#mailRely').ntsError('check');
            $('senderAddress').ntsError('check');
            return kt;
        }
        clean(arr: string[]): string[]{
             for (var i = 0; i < arr.length; i++) {
                if (arr[i] == "") {         
                  arr.splice(i, 1);
                  i--;
                }
              }
              return arr;
        }

        decision() {
            //console.time('decision');
            var self = this;
            
            
            if (self.checkContenListMail() && !$(".nts-input").ntsError("hasError")) {
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
          //console.timeEnd('decision');
            
        }

        cancel_Dialog(): any {
            //console.time('cancel_Dialog');
            nts.uk.ui.windows.setShared("MailSettings", null);
            nts.uk.ui.windows.close();
            //console.timeEnd('cancel_Dialog');
        }

    }
}