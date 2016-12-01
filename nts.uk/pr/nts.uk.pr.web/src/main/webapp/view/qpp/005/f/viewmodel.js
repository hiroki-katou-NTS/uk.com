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
                                    self.totalCommuteEditor.value = ko.observable("10000");
                                    /**
                                     * 課税通勤費 テキストボックス（金額）
                                     */
                                    self.taxCommuteEditor = new NumberEditor();
                                    self.taxCommuteEditor.isEnable = ko.computed(function () {
                                        return self.totalCommuteCheck.isChecked() && self.taxCommuteCheck.isChecked();
                                    });
                                    self.taxCommuteEditor.value = ko.observable("200000");
                                    /**
                                     * 1か月分通勤費 テキストボックス（金額）
                                     */
                                    self.oneMonthCommuteEditor = new NumberEditor();
                                    self.oneMonthCommuteEditor.isEnable = self.oneMonthCheck.isChecked;
                                    self.oneMonthCommuteEditor.value = ko.observable("250000");
                                    /**
                                     * 余り（端数）
                                     */
                                    self.oneMonthRemainderEditor = new NumberEditor();
                                    self.oneMonthRemainderEditor.isEnable = self.oneMonthCheck.isChecked;
                                    self.oneMonthRemainderEditor.value = ko.observable("300000");
                                    /**
                                     * Commute items
                                     */
                                    self.commuteDividedByMonth = new CommuteDividedByMonth();
                                    self.commuteDividedByMonth.isEnable = self.oneMonthCheck.isChecked;
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
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
