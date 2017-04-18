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
                self.timer = new nts.uk.ui.sharedvm.KibanTimer('timer');
                for (var i_1 = 1; i_1 < 100; i_1++) {
                    self.e_errorList.push(new ItemModel_E_LST_003('00' + i_1, '基本給', "description " + i_1));
                }
                var command1 = {
                    companyNameKana: "companyNameKana1",
                    personId: "personId1",
                    departmentCode: "depCode1",
                    paymentDate: moment.utc('20000101', 'YYYYMMDD').toISOString(),
                    paymentBonusAtr: 0,
                    paymentMoney: 12210,
                    processingNo: 0,
                    sparePaymentAtr: 0,
                    processingYM: "201410",
                    fromBank: { branchId: "50000000001", bankNameKana: "frBankKNName", branchNameKana: "frBranchKNName", accountAtr: 0, accountNo: "100" },
                    toBank: { branchId: "50000000002", bankNameKana: "toBankKNName", branchNameKana: "toBranchKNName", accountAtr: 0, accountNo: "200", accountNameKana: "toAccKNName" },
                };
                var command2 = {
                    companyNameKana: "companyNameKana2",
                    personId: "personId2",
                    departmentCode: "depCode2",
                    paymentDate: moment.utc('20000102', 'YYYYMMDD').toISOString(),
                    paymentBonusAtr: 1,
                    paymentMoney: 12212,
                    processingNo: 1,
                    sparePaymentAtr: 1,
                    processingYM: "201411",
                    fromBank: { branchId: "50000000011", bankNameKana: "frBankKNName2", branchNameKana: "frBranchKNName2", accountAtr: 1, accountNo: "100" },
                    toBank: { branchId: "50000000012", bankNameKana: "toBankKNName2", branchNameKana: "toBranchKNName2", accountAtr: 1, accountNo: "200", accountNameKana: "toAccKNName2" },
                };
                var command3 = {
                    companyNameKana: "companyNameKana3",
                    personId: "personId3",
                    departmentCode: "depCode3",
                    paymentDate: moment.utc('20000103', 'YYYYMMDD').toISOString(),
                    paymentBonusAtr: 1,
                    paymentMoney: 12213,
                    processingNo: 0,
                    sparePaymentAtr: 0,
                    processingYM: "201412",
                    fromBank: { branchId: "50000000021", bankNameKana: "frBankKNName3", branchNameKana: "frBranchKNName", accountAtr: 1, accountNo: "300" },
                    toBank: { branchId: "50000000031", bankNameKana: "toBankKNName3", branchNameKana: "toBranchKNName", accountAtr: 0, accountNo: "300", accountNameKana: "toAccKNName3" },
                };
                self.dataFixed = ko.observableArray([]);
                self.dataFixed.push(command1);
                self.dataFixed.push(command2);
                self.dataFixed.push(command3);
                self.currentCode_E_LST_003 = ko.observable();
                self.dateOfPayment = ko.observable(moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("YYYY/MM/DD"));
                self.processingDate = ko.observable(nts.uk.ui.windows.getShared("processingDate"));
            }
            ScreenModel.prototype.startPage = function () {
                var self = this;
                var dfd = $.Deferred();
                _.forEach(self.dataFixed(), function (bankTransfer) {
                    $.when(self.addBankTransfer(bankTransfer)).done(function () {
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                });
                return dfd.promise();
            };
            ScreenModel.prototype.addBankTransfer = function (bankTransfer) {
                var command = {
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
                    .done(function () { })
                    .fail(function () { });
            };
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.setShared("closeDialog", true, true);
                nts.uk.ui.windows.close();
            };
            ScreenModel.prototype.goToScreenGOrH = function () {
                nts.uk.ui.windows.close();
            };
            ScreenModel.prototype.stopProcessing = function () {
                var self = this;
                self.timer.end();
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
                this.fromBank.branchId = fromBank.branchId;
                this.fromBank.bankNameKana = fromBank.bankNameKana;
                this.fromBank.branchNameKana = fromBank.branchNameKana;
                this.fromBank.accountAtr = fromBank.accountAtr;
                this.fromBank.accountNo = fromBank.accountNo;
                this.toBank.branchId = toBank.branchId;
                this.toBank.bankNameKana = toBank.bankNameKana;
                this.toBank.branchNameKana = toBank.branchNameKana;
                this.toBank.accountAtr = toBank.accountAtr;
                this.toBank.accountNo = toBank.accountNo;
                this.toBank.accountNameKana = toBank.accountNameKana;
            }
            return TestArray;
        }());
        e.TestArray = TestArray;
    })(e = qpp014.e || (qpp014.e = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.e.viewmodel.js.map