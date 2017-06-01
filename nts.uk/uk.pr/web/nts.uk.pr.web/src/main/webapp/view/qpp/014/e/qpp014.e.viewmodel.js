// TreeGrid Node
var qpp014;
(function (qpp014) {
    var e;
    (function (e) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                $('#successful').css('display', 'none');
                $('#error').css('display', 'none');
                self.e_errorList = ko.observableArray([]);
                self.numberOfPerson = ko.observable(0);
                self.processingState = ko.observable(null);
                self.numberOfProcessSuccess = ko.observable(0);
                self.numberOfProcessFail = ko.observable(0);
                self.timer = new nts.uk.ui.sharedvm.KibanTimer('timer');
                for (var i_1 = 1; i_1 < 100; i_1++) {
                    self.e_errorList.push(new ItemModel_E_LST_003('00' + i_1, '基本給', "description " + i_1));
                }
                self.dataFixed = ko.observableArray([]);
                //fix data to add to db BANK_TRANSFER
                for (var i_2 = 1; i_2 < 4; i_2++) {
                    self.dataFixed.push(new TestArray("companyNameKana" + i_2, "99900000-0000-0000-0000-00000000000" + i_2, "depCode" + i_2, moment.utc(Math.floor(Math.random() * (2999 - 1900 + 1) + 1900).toString() + Math.floor(Math.random() * (12 - 10 + 1) + 10).toString() + Math.floor(Math.random() * (28 - 1 + 1) + 1).toString(), 'YYYYMMDD').toISOString(), Math.floor(Math.random() * 2), Math.floor(Math.random() * 1001), 1, Math.floor(Math.random() * 2), Math.floor(Math.random() * (2999 - 1900 + 1) + 1900).toString() + Math.floor(Math.random() * (12 - 10 + 1) + 10).toString(), { branchId: "00000000" + i_2, bankNameKana: "frBankKNN" + i_2, branchNameKana: "frBranchKNN" + i_2, accountAtr: Math.floor(Math.random() * 2), accountNo: "00" + i_2 }, { branchId: "10000000" + i_2, bankNameKana: "toBankKNN" + i_2, branchNameKana: "toBranchKNN" + i_2, accountAtr: Math.floor(Math.random() * 2), accountNo: "00" + i_2, accountNameKana: "toAccKNName" + i_2 }));
                }
                self.currentCode_E_LST_003 = ko.observable();
                self.dateOfPayment = ko.observable(moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("YYYY/MM/DD"));
                self.processingYM = ko.observable(nts.uk.time.formatYearMonth(nts.uk.ui.windows.getShared("processingYMNotConvert")));
            }
            ScreenModel.prototype.startPage = function () {
                var self = this;
                var index = ko.observable(0);
                self.numberOfPerson(self.dataFixed().length);
                if (self.numberOfPerson() > 0) {
                    self.processingState('データの作成中');
                }
                else {
                    self.stopProcessing();
                }
                self.timer.start();
                _.forEach(self.dataFixed(), function (bankTransfer) {
                    $.when(self.addBankTransfer(bankTransfer)).done(function () {
                        //if add data to DB success, go to dialog "Success"
                        if (self.numberOfProcessSuccess() == self.numberOfPerson()) {
                            self.timer.end();
                            self.processingState('完了');
                            nts.uk.ui.windows.setShared("closeDialog", false, true);
                            $('#successful').css('display', '');
                            $('#stop').css('display', 'none');
                            $('#error').css('display', 'none');
                        }
                    }).fail(function (res) {
                    });
                });
            };
            /**
             * insert data to DB BANK_TRANSFER
             */
            ScreenModel.prototype.addBankTransfer = function (bankTransfer) {
                var self = this;
                var dfd = $.Deferred();
                var command = {
                    processingNoOfScreenE: nts.uk.ui.windows.getShared("processingNo"),
                    payDateOfScreenE: moment.utc(nts.uk.ui.windows.getShared("dateOfPayment")).toISOString(),
                    sparePayAtrOfScreenE: nts.uk.ui.windows.getShared("sparePayAtr"),
                    processingYMOfScreenE: nts.uk.ui.windows.getShared("processingYMNotConvert"),
                    companyNameKana: bankTransfer.companyNameKana,
                    personId: bankTransfer.personId,
                    departmentCode: bankTransfer.departmentCode,
                    paymentDate: bankTransfer.paymentDate,
                    paymentBonusAtr: bankTransfer.paymentBonusAtr,
                    paymentMoney: bankTransfer.paymentMoney,
                    processingNo: bankTransfer.processingNo,
                    sparePaymentAtr: bankTransfer.sparePaymentAtr,
                    processingYM: bankTransfer.processingYM,
                    fromBank: { branchId: bankTransfer.fromBank.branchId, bankNameKana: bankTransfer.fromBank.bankNameKana, branchNameKana: bankTransfer.fromBank.branchNameKana, accountAtr: bankTransfer.fromBank.accountAtr, accountNo: bankTransfer.fromBank.accountNo },
                    toBank: { branchId: bankTransfer.toBank.branchId, bankNameKana: bankTransfer.toBank.bankNameKana, branchNameKana: bankTransfer.toBank.branchNameKana, accountAtr: bankTransfer.toBank.accountAtr, accountNo: bankTransfer.toBank.accountNo, accountNameKana: bankTransfer.toBank.accountNameKana },
                };
                qpp014.e.service.addBankTransfer(command)
                    .done(function () {
                    self.numberOfProcessSuccess(self.numberOfProcessSuccess() + 1);
                    dfd.resolve();
                })
                    .fail(function (res) {
                    self.numberOfProcessFail(self.numberOfProcessFail() + 1);
                    dfd.resolve(res);
                });
                return dfd.promise();
            };
            /**
             * close dialog
             */
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.setShared("closeDialog", true, true);
                nts.uk.ui.windows.close();
            };
            /**
             * go to screen G or H
             */
            ScreenModel.prototype.goToScreenGOrH = function () {
                nts.uk.ui.windows.close();
            };
            /**
             * stop processing
             */
            ScreenModel.prototype.stopProcessing = function () {
                var self = this;
                self.timer.end();
                self.processingState('完了');
                nts.uk.ui.windows.setShared("closeDialog", false, true);
                $('#successful').css('display', 'none');
                $('#stop').css('display', 'none');
                $('#error').css('display', '');
                nts.uk.ui.windows.getSelf().setHeight(595);
            };
            return ScreenModel;
        }());
        e.ScreenModel = ScreenModel;
        var ItemModel_E_LST_003 = (function () {
            function ItemModel_E_LST_003(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_E_LST_003;
        }());
        e.ItemModel_E_LST_003 = ItemModel_E_LST_003;
        var TestArray = (function () {
            function TestArray(companyNameKana, personId, departmentCode, paymentDate, paymentBonusAtr, paymentMoney, processingNo, sparePaymentAtr, processingYM, fromBank, toBank) {
                this.companyNameKana = companyNameKana;
                this.personId = personId;
                this.departmentCode = departmentCode;
                this.paymentDate = paymentDate;
                this.paymentBonusAtr = paymentBonusAtr;
                this.paymentMoney = paymentMoney;
                this.processingNo = processingNo;
                this.sparePaymentAtr = sparePaymentAtr;
                this.processingYM = processingYM;
                this.fromBank = fromBank;
                this.toBank = toBank;
            }
            return TestArray;
        }());
        e.TestArray = TestArray;
        var FromBank = (function () {
            function FromBank(branchId, bankNameKana, branchNameKana, accountAtr, accountNo) {
                this.branchId = branchId;
                this.bankNameKana = bankNameKana;
                this.branchNameKana = branchNameKana;
                this.accountAtr = accountAtr;
                this.accountNo = accountNo;
            }
            return FromBank;
        }());
        e.FromBank = FromBank;
        var ToBank = (function () {
            function ToBank(branchId, bankNameKana, branchNameKana, accountAtr, accountNo, accountNameKana) {
                this.branchId = branchId;
                this.bankNameKana = bankNameKana;
                this.branchNameKana = branchNameKana;
                this.accountAtr = accountAtr;
                this.accountNo = accountNo;
                this.accountNameKana = accountNameKana;
            }
            return ToBank;
        }());
        e.ToBank = ToBank;
    })(e = qpp014.e || (qpp014.e = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.e.viewmodel.js.map