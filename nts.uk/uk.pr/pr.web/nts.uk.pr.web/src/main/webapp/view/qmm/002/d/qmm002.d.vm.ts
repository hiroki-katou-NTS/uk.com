module nts.uk.pr.view.qmm002.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        bankList: KnockoutObservableArray<any>;
        dataSource: KnockoutObservableArray<Bank>;
        selectedCode: KnockoutObservable<string>;
        selectedBank: KnockoutObservable<Bank>;
        updateMode: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            var self = this;
            self.bankList = ko.observableArray([]);
            self.dataSource = ko.observableArray([]);
            self.selectedCode = ko.observable(null);
            self.selectedBank = ko.observable(new Bank("", "", "", ""));
            self.selectedCode.subscribe(val => {
                nts.uk.ui.errors.clearAll();
                if (val == null) {
                    self.selectedBank(new Bank("", "", "", ""));
                    self.updateMode(false);
                    $("#D3_2").focus();
                } else {
                    let bank = _.find(self.dataSource(), b => {return b.code() == val});
                    self.selectedBank(bank);
                    self.updateMode(true);
                    $("#D3_3").focus();
                }
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible()
            qmm002.a.service.getAllBank().done((data: Array<any>) => {
                if (_.isEmpty(data)) {
                    self.selectedCode(null);
                } else {
                    let lstBank = [], lstDisp = [];
                    data.forEach(b => {
                        lstBank.push(new Bank(b.code, b.name, b.kanaName, b.memo));
                        lstDisp.push({code: b.code, name: b.name, displayText: b.code + " " + b.name});
                    });
                    self.dataSource(lstBank);
                    self.bankList(lstDisp);
                    self.selectedCode(data[0].code);
                }
                dfd.resolve();
            }).fail(error => {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        close() {
            nts.uk.ui.windows.close();
        }

        createNew() {
            let self = this;
            if (self.selectedCode() == null)
                self.selectedCode.valueHasMutated();
            else
                self.selectedCode(null);
        }
        
        register() {
            let self = this;
            $(".nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                let command = ko.toJS(self.selectedBank());
                ko.utils.extend(command, { updateMode: self.updateMode() });
                qmm002.a.service.registerBank(command).done(() => {
                    qmm002.a.service.getAllBank().done((data: Array<any>) => {
                        let lstBank = [], lstDisp = [];
                        data.forEach(b => {
                            lstBank.push(new Bank(b.code, b.name, b.kanaName, b.memo));
                            lstDisp.push({code: b.code, name: b.name, displayText: b.code + " " + b.name});
                        });
                        self.dataSource(lstBank);
                        self.bankList(lstDisp);
                        info({ messageId: "Msg_15" }).then(() => {
                            if (self.selectedCode() == command.code)
                                self.selectedCode.valueHasMutated();
                            else
                                self.selectedCode(command.code);
                        });
                    }).fail(error => {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }).fail(error => {
                    alertError(error).then(() => {
                        if (error.messageId == "Msg_3") $("#D3_2").focus();
                    });
                }).always(() => {
                    block.clear();
                });
            }
        }
        
    }
    
    class Bank {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        kanaName: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        constructor(code: string, name: string, kana: string, memo: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.kanaName = ko.observable(kana);
            this.memo = ko.observable(memo);
        }
    }
        
}
