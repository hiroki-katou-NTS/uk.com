// TreeGrid Node
module qpp014.e {
    export class ScreenModel {
        //E_LST_003
        e_errorList: KnockoutObservableArray<ItemModel_E_LST_003>;
        e_resultList: KnockoutObservableArray<ItemModel_E_LST_004>;
        //        columns_E_LST_003: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_E_LST_003: KnockoutObservable<any>;
        currentCode_E_LST_004: KnockoutObservable<any>;
        timer: nts.uk.ui.sharedvm.KibanTimer;
        dateOfPayment: KnockoutObservable<any>;
        data: KnockoutObservableArray<any>;
        numberOfPerson: KnockoutObservable<number>;
        processingState: KnockoutObservable<string>;
        numberOfProcessSuccess: KnockoutObservable<number>;
        numberOfProcessFail: KnockoutObservable<number>;
        processingYM: KnockoutObservable<string>;

        constructor() {
            var self = this;
            $('#successful').css('display', 'none');
            $('#error').css('display', 'none');
            $('#stop').css('display', 'none');
            self.e_errorList = ko.observableArray([]);
            self.e_resultList = ko.observableArray([]);
            self.numberOfPerson = ko.observable(0);
            self.processingState = ko.observable(null);
            self.numberOfProcessSuccess = ko.observable(0);
            self.numberOfProcessFail = ko.observable(0);
            self.timer = new nts.uk.ui.sharedvm.KibanTimer('timer');
            for (let i = 1; i < 10; i++) {
                self.e_errorList.push(new ItemModel_E_LST_003('00' + i, '基本給', "description " + i));
            }
            for (let i = 1; i < 10; i++) {
                self.e_resultList.push(new ItemModel_E_LST_004('00' + i, "description " + i));
            }
            self.data = ko.observableArray(nts.uk.ui.windows.getShared("dataFromD"));
            //fix data to add to db BANK_TRANSFER
            //            for (let i = 1; i < 8; i++) {
            //                self.dataFixed.push(new TestArray("companyNameKana" + i, "99900000-0000-0000-0000-00000000000" + i, "depCode" + i,
            //                    moment.utc(Math.floor(Math.random() * (2999 - 1900 + 1) + 1900).toString() + Math.floor(Math.random() * (12 - 10 + 1) + 10).toString() + Math.floor(Math.random() * (28 - 1 + 1) + 1).toString(), 'YYYYMMDD').toISOString(),
            //                    Math.floor(Math.random() * 2), Math.floor(Math.random() * 1001), 1, Math.floor(Math.random() * 2), Math.floor(Math.random() * (2999 - 1900 + 1) + 1900).toString() + Math.floor(Math.random() * (12 - 10 + 1) + 10).toString(),
            //                    { branchId: "00000000" + i, bankNameKana: "frBankKNN" + i, branchNameKana: "frBranchKNN" + i, accountAtr: Math.floor(Math.random() * 2), accountNo: "00" + i },
            //                    { branchId: "10000000" + i, bankNameKana: "toBankKNN" + i, branchNameKana: "toBranchKNN" + i, accountAtr: Math.floor(Math.random() * 2), accountNo: "00" + i, accountNameKana: "toAccKNName" + i }));
            //            }
            self.currentCode_E_LST_003 = ko.observable();
            self.currentCode_E_LST_004 = ko.observable();
            self.dateOfPayment = ko.observable(moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("YYYY/MM/DD"));
            self.processingYM = ko.observable(nts.uk.time.formatYearMonth(nts.uk.ui.windows.getShared("processingYMNotConvert")));
        }

        startPage(): void {
            var self = this;
            var index = ko.observable(0);
            self.numberOfPerson(self.data().length);
            if (self.numberOfPerson() > 0) {
                self.processingState('データの作成中');
            } else {
                self.stopProcessing();
            }
            self.timer.start();

            var cmd = {
                processingNoOfScreenE: nts.uk.ui.windows.getShared("processingNo"),
                payDateOfScreenE: moment.utc(nts.uk.ui.windows.getShared("dateOfPayment")).toISOString(),
                sparePayAtrOfScreenE: nts.uk.ui.windows.getShared("sparePayAtr"),
            }
            $.when(qpp014.e.service.removeBankTransfer(cmd))
                .done(function() {
                    _.forEach(self.data(), function(bankTransfer) {
                        self.addBankTransfer(bankTransfer).done(function() {
                            //if add data to DB success, go to dialog "Success"
                            if (self.numberOfProcessSuccess() == self.numberOfPerson()) {
                                self.timer.end();
                                self.processingState('完了');
                                nts.uk.ui.windows.setShared("closeDialog", false, true);
                                $('#successful').css('display', '');
                                $('#process').css('display', 'none');
                                $('#error').css('display', 'none');
                                $('#stop').css('display', 'none');
                            }
                            if (self.numberOfProcessSuccess() + self.numberOfProcessFail() == self.numberOfPerson()) {
                                self.timer.end();
                                self.processingState('完了');
                                nts.uk.ui.windows.setShared("closeDialog", false, true);
                                $('#successful').css('display', 'none');
                                $('#process').css('display', 'none');
                                $('#error').css('display', '');
                                $('#stop').css('display', 'none');
                                nts.uk.ui.windows.getSelf().setHeight(595);
                            }
                        }).fail(function(res) { });
                    });
                }).fail();

        }

        /**
         * insert data to DB BANK_TRANSFER
         */
        addBankTransfer(bankTransfer): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            var command = {
                processingNoOfScreenE: nts.uk.ui.windows.getShared("processingNo"),
                payDateOfScreenE: moment.utc(nts.uk.ui.windows.getShared("dateOfPayment")).toISOString(),
                sparePayAtrOfScreenE: nts.uk.ui.windows.getShared("sparePayAtr"),
                processingYMOfScreenE: nts.uk.ui.windows.getShared("processingYMNotConvert"),
                personId: bankTransfer.pId,
            }

            qpp014.e.service.addBankTransfer(command)
                .done(function() {
                    self.numberOfProcessSuccess(self.numberOfProcessSuccess() + 1);
                    dfd.resolve();
                })
                .fail(function(res) {
                    self.numberOfProcessFail(self.numberOfProcessFail() + 1);
                    dfd.resolve(res);
                });
            return dfd.promise();
        }

        /**
         * close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.setShared("closeDialog", true, true);
            nts.uk.ui.windows.close();
        }

        /**
         * go to screen G or H
         */
        goToScreenGOrH(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * stop processing
         */
        stopProcessing(): void {
            var self = this;
            self.timer.end();
            self.processingState('完了');
            nts.uk.ui.windows.setShared("closeDialog", false, true);
            $('#successful').css('display', 'none');
            $('#process').css('display', 'none');
            $('#error').css('display', 'none');
            $('#stop').css('display', '');
            nts.uk.ui.windows.getSelf().setHeight(595);
        }
    }

    export class ItemModel_E_LST_003 {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }

    export class ItemModel_E_LST_004 {
        target: string;
        status: string;

        constructor(target: string, status: string) {
            this.target = target;
            this.status = status;
        }
    }

    export class FromBank {
        branchId: string
        bankNameKana: string
        branchNameKana: string
        accountAtr: number
        accountNo: string

        constructor(branchId: string, bankNameKana: string, branchNameKana: string, accountAtr: number, accountNo: string) {
            this.branchId = branchId;
            this.bankNameKana = bankNameKana;
            this.branchNameKana = branchNameKana;
            this.accountAtr = accountAtr;
            this.accountNo = accountNo;
        }
    }

    export class ToBank {
        branchId: string
        bankNameKana: string
        branchNameKana: string
        accountAtr: number
        accountNo: string
        accountNameKana: string

        constructor(branchId: string, bankNameKana: string, branchNameKana: string, accountAtr: number, accountNo: string, accountNameKana: string) {
            this.branchId = branchId;
            this.bankNameKana = bankNameKana;
            this.branchNameKana = branchNameKana;
            this.accountAtr = accountAtr;
            this.accountNo = accountNo;
            this.accountNameKana = accountNameKana;
        }
    }

};
