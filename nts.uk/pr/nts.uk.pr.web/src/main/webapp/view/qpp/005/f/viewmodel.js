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
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    var detailItemFromParentScreen = nts.uk.ui.windows.getShared('value');
                                    var employee = nts.uk.ui.windows.getShared('employee');
                                    var nowYearMonth = new Date();
                                    //Get current year month
                                    var baseYearmonth = nowYearMonth.getFullYear().toString() + (nowYearMonth.getMonth() + 1).toString();
                                    // Set value for 通勤費合計 textbox 
                                    self.totalCommuteEditor.value = ko.observable(detailItemFromParentScreen.value);
                                    // Set value for 課税通勤費 textbox 
                                    self.taxCommuteEditor.value = ko.observable(detailItemFromParentScreen.commuteAllowTaxImpose);
                                    // Set value for  1か月分通勤費 textbox 
                                    self.oneMonthCommuteEditor.value = ko.observable(detailItemFromParentScreen.commuteAllowMonth);
                                    // Set value for 余り textbox 
                                    self.oneMonthRemainderEditor.value = ko.observable(detailItemFromParentScreen.commuteAllowFraction);
                                    return dfd.promise();
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
