var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp005;
                (function (qpp005) {
                    var f;
                    (function (f) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    /**
                                     * 通勤費　合計
                                     */
                                    self.totalCommuteCheck = new CheckBox();
                                    /**
                                     * １ヵ月分通勤費
                                     */
                                    self.oneMonthCheck = new CheckBox();
                                    self.oneMonthCheck.isChecked = ko.observable(false);
                                    /**
                                     * 課税通勤費
                                     */
                                    self.taxCommuteCheck = new CheckBox();
                                    self.taxCommuteCheck.isChecked = ko.observable(false);
                                    /**
                                     * 通勤費合計 テキストボックス（金額）
                                     */
                                    self.totalCommuteEditor = new NumberEditor();
                                    self.totalCommuteEditor.isEnable = self.totalCommuteCheck.isChecked;
                                    /**
                                     * 課税通勤費 テキストボックス（金額）
                                     */
                                    self.taxCommuteEditor = new NumberEditor();
                                    self.taxCommuteEditor.isEnable = ko.computed(function () {
                                        return self.totalCommuteCheck.isChecked() && self.taxCommuteCheck.isChecked();
                                    });
                                    /**
                                     * 1か月分通勤費 テキストボックス（金額）
                                     */
                                    self.oneMonthCommuteEditor = new NumberEditor();
                                    self.oneMonthCommuteEditor.isEnable = self.oneMonthCheck.isChecked;
                                    /**
                                     * 余り（端数）
                                     */
                                    self.oneMonthRemainderEditor = new NumberEditor();
                                    self.oneMonthRemainderEditor.isEnable = self.oneMonthCheck.isChecked;
                                    /**
                                     * Commute items
                                     */
                                    self.commuteDividedByMonth = new CommuteDividedByMonth();
                                    self.commuteDividedByMonth.isEnable = self.oneMonthCheck.isChecked;
                                    self.commuteNotaxLimitItem = ko.observable();
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    var detailItemFromParentScreen = nts.uk.ui.windows.getShared('value');
                                    var employee = nts.uk.ui.windows.getShared('employee');
                                    var baseYearmonth = nts.uk.ui.windows.getShared('processingYM');
                                    // Set value for 通勤費合計 textbox 
                                    self.totalCommuteEditor.value = ko.observable(detailItemFromParentScreen.value);
                                    // Set value for 課税通勤費 textbox 
                                    self.taxCommuteEditor.value = ko.observable(detailItemFromParentScreen.commuteAllowTaxImpose);
                                    // Set value for  1か月分通勤費 textbox 
                                    self.oneMonthCommuteEditor.value = ko.observable(detailItemFromParentScreen.commuteAllowMonth);
                                    // Set value for 余り textbox 
                                    self.oneMonthRemainderEditor.value = ko.observable(detailItemFromParentScreen.commuteAllowFraction);
                                    qpp005.f.service.getCommute(employee.personId, baseYearmonth).done(function (res) {
                                        qpp005.f.service.getCommuteNotaxLimit("01").done(function (res) {
                                            self.commuteNotaxLimitItem(new CommuteNotaxLimitItem(res.commuNotaxLimitCode, res.commuNotaxLimitName, res.commuNotaxLimitValue));
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.settingButtonClick = function () {
                                    var self = this;
                                    var detailItemFromParentScreen = nts.uk.ui.windows.getShared('value');
                                    // Check checked item
                                    if (!self.totalCommuteCheck.isChecked() || !self.oneMonthCheck.isChecked()) {
                                        alert("対象データがありません。");
                                        return;
                                    }
                                    // Check item is exist
                                    if (detailItemFromParentScreen.itemCode == "") {
                                        alert("更新対象のデータが存在しません。");
                                        return;
                                    }
                                    nts.uk.ui.windows.setShared('totalCommuteEditor', ko.toJS(self.totalCommuteEditor.value));
                                    nts.uk.ui.windows.setShared('taxCommuteEditor', ko.toJS(self.taxCommuteEditor.value));
                                    nts.uk.ui.windows.setShared('oneMonthCommuteEditor', ko.toJS(self.oneMonthCommuteEditor.value));
                                    nts.uk.ui.windows.setShared('oneMonthRemainderEditor', ko.toJS(self.oneMonthRemainderEditor.value));
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.cancelButtonClick = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var CheckBox = (function () {
                                function CheckBox() {
                                    var self = this;
                                    self.isChecked = ko.observable(true);
                                }
                                return CheckBox;
                            }());
                            viewmodel.CheckBox = CheckBox;
                            var NumberEditor = (function () {
                                function NumberEditor() {
                                    var self = this;
                                    self.option = ko.mapping.fromJS(new uk.ui.option.CurrencyEditorOption({ grouplength: 3,
                                        decimallength: 0,
                                        currencyformat: 'JPY',
                                        width: "80" }));
                                }
                                return NumberEditor;
                            }());
                            viewmodel.NumberEditor = NumberEditor;
                            var CommuteDividedByMonth = (function () {
                                function CommuteDividedByMonth() {
                                    var self = this;
                                    self.commuteItems = ko.observableArray([
                                        new CommuteDividedItemsByMonth("1", "１ヶ月"),
                                        new CommuteDividedItemsByMonth("2", "２ヶ月"),
                                        new CommuteDividedItemsByMonth("3", "３ヶ月"),
                                        new CommuteDividedItemsByMonth("4", "４ヶ月"),
                                        new CommuteDividedItemsByMonth("5", "５ヶ月"),
                                        new CommuteDividedItemsByMonth("6", "６ヶ月"),
                                    ]);
                                    self.selectedCode = ko.observable("1");
                                }
                                return CommuteDividedByMonth;
                            }());
                            function CommuteNotaxLimitItem(code, name, value) {
                                this.code = code;
                                this.name = name;
                                this.value = value;
                            }
                            function CommuteDividedItemsByMonth(code, name) {
                                this.code = code;
                                this.name = name;
                            }
                        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
                    })(f = qpp005.f || (qpp005.f = {}));
                })(qpp005 = view.qpp005 || (view.qpp005 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
