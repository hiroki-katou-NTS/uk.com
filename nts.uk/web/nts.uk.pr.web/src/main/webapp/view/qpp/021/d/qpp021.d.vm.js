var qpp021;
(function (qpp021) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.isEnaleFromParent = ko.observable(true);
                    this.isvisibleItem = ko.observable(true);
                    var self = this;
                    self.zeroItemSetting = ko.observableArray([
                        new ItemModel(1, "項目名の登録の設定を優先する"),
                        new ItemModel(2, "個別にッ設定する")
                    ]);
                    self.zeroItemSettingCode = ko.observable(2);
                    self.switchItemList = ko.observableArray([
                        new ItemModel(1, "する"),
                        new ItemModel(2, "しない")
                    ]);
                    self.zeroAmountOutputCode = ko.observable(2);
                    self.zeroTimeClassificationCode = ko.observable(1);
                    self.totalTaxableOutputCode = ko.observable(1);
                    self.yearlyHolidaysClassificationCode = ko.observable(1);
                    self.kindPaymentOutputCode = ko.observable(1);
                    self.selectPrintYearMonth = ko.observableArray([
                        new ItemModel(1, "現在処理年月の2ヶ月前"),
                        new ItemModel(2, "現在処理年月の1か月前"),
                        new ItemModel(3, "現在処理年月"),
                        new ItemModel(4, "現在処理年月の翌月"),
                        new ItemModel(5, "現在処理年月の2ヶ月後")
                    ]);
                    self.selectPrintYearMonthCode = ko.observable(3);
                    self.outputNameDesignation = ko.observableArray([
                        new ItemModel(1, "個人情報より取得する"),
                        new ItemModel(2, "項目名より取得する"),
                    ]);
                    self.outputNameDesignationCode = ko.observable(1);
                    self.outputDepartment = ko.observableArray([
                        new ItemModel(1, "部門コードを出力する"),
                        new ItemModel(2, "部門名を出力する"),
                        new ItemModel(3, "出力しない"),
                    ]);
                    self.outputDepartmentCode = ko.observable(2);
                    self.borderLineWidth = ko.observableArray([
                        new ItemModel(1, "太い"),
                        new ItemModel(2, "標準"),
                        new ItemModel(3, "細い    "),
                    ]);
                    self.borderLineWidthCode = ko.observable(2);
                    self.outputCompanyNameCode = ko.observable(1);
                    self.shadedSectionCode = ko.observable(1);
                    self.numberOutputDependentCode = ko.observable(1);
                    self.incomeTaxClassificationCode = ko.observable(1);
                    self.insuranceFollowingOutputCode = ko.observable(1);
                    self.personalAddressCode = ko.observable(1);
                    self.personNameCode = ko.observable(1);
                    self.companyAddressCode = ko.observable(1);
                    self.companyNameCode = ko.observable(1);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var RadioBoxGroupModel = (function () {
                function RadioBoxGroupModel(rbCode, rbName) {
                    this.rbCode = rbCode;
                    this.rbName = rbName;
                }
                return RadioBoxGroupModel;
            }());
            var SwitchButtonModel = (function () {
                function SwitchButtonModel(sbCode, sbName) {
                    this.sbCode = sbCode;
                    this.sbName = sbName;
                }
                return SwitchButtonModel;
            }());
            var ItemModel = (function () {
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qpp021.d || (qpp021.d = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.d.vm.js.map