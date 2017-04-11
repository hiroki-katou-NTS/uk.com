var qmm005;
(function (qmm005) {
    var a;
    (function (a) {
        var ViewModel = (function () {
            function ViewModel() {
                var self = this;
                self.items = ko.observableArray([]);
                self.start();
            }
            ViewModel.prototype.start = function () {
                var self = this;
                var _records = [];
                a.services.getData().done(function (resp) {
                    var _loop_1 = function(i) {
                        var index = parseInt(i) + 1;
                        var _row = new TableRowItem(index, "", [{ index: -1, label: "" }], [{ index: -1, label: "" }], true, [{ index: -1, label: "" }], [{ index: -1, label: "" }]);
                        _row.parent = self;
                        var item = _.find(resp, function (o) { return o && o[0] && o[0].processingNo == index; });
                        if (item) {
                            _row.index(item[0].processingNo);
                            _row.label(item[0].processingName);
                            var currentProcessingYm = nts.uk.time.parseYearMonth(item[0].currentProcessingYm);
                            _row.sel001(currentProcessingYm.year);
                            var _sel001Data = [new qmm005.common.SelectItem({ index: currentProcessingYm.year, label: '' + currentProcessingYm.year })];
                            _row.sel001Data(_sel001Data);
                            var _sel002Data = [];
                            if (item[1]) {
                                for (var i_1 in item[1]) {
                                    var index_1 = parseInt(i_1) + 1, rec = item[1][i_1], payDate = new Date(rec.payDate), stdDate = new Date(rec.stdDate), label = nts.uk.time.formatDate(payDate, "yyyy/MM/dd") + '(月)|' + nts.uk.time.formatDate(stdDate, "yyyy/MM/dd");
                                    _sel002Data.push(new qmm005.common.SelectItem({ index: index_1, label: label }));
                                }
                                if (_sel002Data.length) {
                                    _row.sel002Data(_sel002Data);
                                    _row.sel002(_sel002Data[0].index);
                                }
                            }
                            _row.sel003(item[0].bonusAtr);
                            var bCurrentProcessingYm = nts.uk.time.parseYearMonth(item[0].bcurrentProcessingYm);
                            _row.sel004(bCurrentProcessingYm.year);
                            var _sel004Data = [{ index: bCurrentProcessingYm.year, label: '' + bCurrentProcessingYm.year }];
                            _row.sel004Data(_sel004Data);
                            _row.sel005(bCurrentProcessingYm.month);
                            var _sel005Data = [{ index: bCurrentProcessingYm.month, label: bCurrentProcessingYm.month + '月' }];
                            _row.sel005Data(_sel005Data);
                        }
                        _records.push(_row);
                    };
                    for (var i in resp) {
                        _loop_1(i);
                    }
                    self.items(_records);
                });
            };
            ViewModel.prototype.btn002Click = function (item, event) {
                location.href = "../../../qmp/005/b/index.xhtml";
            };
            ViewModel.prototype.btn003Click = function (item, event) {
            };
            ViewModel.prototype.btn004Click = function (item, event) {
            };
            ViewModel.prototype.btn005Click = function (item, event) {
            };
            return ViewModel;
        }());
        a.ViewModel = ViewModel;
        var TableRowItem = (function () {
            function TableRowItem(index, label, sel001, sel002, sel003, sel004, sel005) {
                this.index = ko.observable(index);
                this.label = ko.observable(label);
                this.sel001Data = ko.observableArray(sel001);
                if (sel001[0])
                    this.sel001 = ko.observable(sel001[0].index);
                this.sel002Data = ko.observableArray(sel002);
                if (sel002[0])
                    this.sel002 = ko.observable(sel002[0].index);
                this.sel003 = ko.observable(sel003);
                this.sel004Data = ko.observableArray(sel004);
                if (sel004[0])
                    this.sel004 = ko.observable(sel004[0].index);
                this.sel005Data = ko.observableArray(sel005);
                if (sel005[0])
                    this.sel005 = ko.observable(sel005[0].index);
            }
            TableRowItem.prototype.enable = function () {
                return this.label() != '';
            };
            TableRowItem.prototype.showModalDialogB = function (item, event) {
                var self = this;
                nts.uk.ui.windows.setShared('dataRow', item);
                nts.uk.ui.windows.sub.modal("../b/index.xhtml", { width: 1173, height: 645, title: '給与処理月を翌月へ更新  ' })
                    .onClosed(function () { self.parent.start(); });
            };
            TableRowItem.prototype.showModalDialogC = function (item, event) {
                var self = this;
                nts.uk.ui.windows.setShared('dataRow', item);
                nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 800, height: 350, title: '給与処理月を翌月へ更新  ' })
                    .onClosed(function () { self.parent.start(); });
            };
            TableRowItem.prototype.showModalDialogD = function (item, event) {
                var self = this;
                nts.uk.ui.windows.setShared('dataRow', item);
                nts.uk.ui.windows.sub.modal("../d/index.xhtml", { width: 800, height: 370, title: '給与処理月を翌月へ更新  ' })
                    .onClosed(function () { self.parent.start(); });
            };
            return TableRowItem;
        }());
        var SelectItem = (function () {
            function SelectItem(index, label) {
                this.index = index;
                this.label = label;
            }
            return SelectItem;
        }());
    })(a = qmm005.a || (qmm005.a = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.a.viewmodel.js.map