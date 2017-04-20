var qmm005;
(function (qmm005) {
    var a;
    (function (a) {
        var ViewModel = (function () {
            function ViewModel() {
                this.items = ko.observableArray([]);
                this.dirty = new nts.uk.ui.DirtyChecker(this.items);
                var self = this;
                self.start();
            }
            ViewModel.prototype.start = function () {
                var self = this;
                var _records = [];
                a.services.getData().done(function (resp) {
                    var _loop_1 = function(i) {
                        var index = parseInt(i) + 1;
                        var _row = new TableRowItem({
                            index: index,
                            label: "",
                            sel001Data: [new qmm005.common.SelectItem({ index: -1, label: "" })],
                            sel002Data: [new qmm005.common.SelectItem({ index: -1, label: "" })],
                            sel003: true,
                            sel004Data: [new qmm005.common.SelectItem({ index: -1, label: "" })],
                            sel005Data: [new qmm005.common.SelectItem({ index: -1, label: "" })]
                        });
                        var item = _.find(resp, function (item) { return item && item.paydayProcessingDto && item.paydayProcessingDto.processingNo == index; });
                        if (item) {
                            _row.index(item.paydayProcessingDto.processingNo);
                            _row.label(item.paydayProcessingDto.processingName);
                            var _sel001Data = [];
                            var _sel002Data = [];
                            var _sel004Data = [];
                            var _sel005Data = [];
                            var cym = nts.uk.time.parseYearMonth(item.paydayProcessingDto.currentProcessingYm);
                            var bcym = nts.uk.time.parseYearMonth(item.paydayProcessingDto.bcurrentProcessingYm);
                            var cspd = 0, bcspdy = 0, cspdm = 0;
                            if (item.paydayDtos) {
                                var payDays = _.orderBy(item.paydayDtos, ['processingYm'], ['desc']);
                                for (var j = 0; j < payDays.length; j++) {
                                    var rec = payDays[j], payDate = new Date(rec.payDate), month = payDate.getMonth() + 1, stdDate = new Date(rec.stdDate), label = nts.uk.time.formatDate(payDate, "yyyy/MM/dd") + '(' + payDate['getDayJP']() + ')|' + nts.uk.time.formatDate(stdDate, "yyyy/MM/dd"), ym = nts.uk.time.parseYearMonth(rec.processingYm);
                                    if (ym.success) {
                                        _sel001Data.push(new qmm005.common.SelectItem({ index: ym.year, label: ym.year.toString() }));
                                    }
                                    _sel002Data.push(new qmm005.common.SelectItem({ index: month, label: label }));
                                    if (payDate.getFullYear() === cym.year && month === cym.month) {
                                        cspd = month;
                                    }
                                }
                            }
                            if (item.paydayBonusDtos) {
                                var payDays = _.orderBy(item.paydayBonusDtos, ['processingYm'], ['desc']);
                                for (var j = 0; j < payDays.length; j++) {
                                    var rec = payDays[j];
                                    var pym = nts.uk.time.parseYearMonth(rec.processingYm);
                                    if (pym.success) {
                                        _sel004Data.push(new qmm005.common.SelectItem({ index: pym.year, label: pym.year.toString() }));
                                        _sel005Data.push(new qmm005.common.SelectItem({ index: pym.month, label: pym.month.toString() + '月' }));
                                    }
                                }
                            }
                            _sel001Data = _.uniqWith(_sel001Data, _.isEqual);
                            _row.sel001Data(_sel001Data);
                            _sel002Data = _.uniqWith(_sel002Data, _.isEqual);
                            _row.sel002Data(_sel002Data);
                            _sel004Data = _.uniqWith(_sel004Data, _.isEqual);
                            _row.sel004Data(_sel004Data);
                            _sel005Data = _.uniqWith(_sel005Data, _.isEqual);
                            _row.sel005Data(_sel005Data);
                            _row.sel001(cym.year);
                            _row.sel002(cspd);
                            _row.sel003(item.paydayProcessingDto.bonusAtr == 1 ? true : false);
                            _row.sel004(bcym.year);
                            _row.sel005(bcym.month);
                        }
                        _records.push(_row);
                    };
                    for (var i in resp) {
                        _loop_1(i);
                    }
                    self.items(_records);
                    self.dirty.reset();
                });
            };
            ViewModel.prototype.saveData = function (item, event) {
                var self = this, items = self.items();
                var data = [];
                for (var i = 0, row; row = items[i]; i++) {
                    if (row.label() != '') {
                        data.push({
                            processingNo: row.index(),
                            processingName: row.label(),
                            dispSet: 0,
                            currentProcessingYm: parseInt((row.sel001() + '' + row.sel002())['formatYearMonth']()),
                            bonusAtr: row.sel003() === true ? 1 : 0,
                            bcurrentProcessingYm: parseInt((row.sel004() + '' + row.sel005())['formatYearMonth']())
                        });
                    }
                }
                a.services.updatData({ paydayProcessings: data }).done(function (resp) {
                    self.start();
                });
            };
            ViewModel.prototype.btn002Click = function (item, event) {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function () {
                        location.href = "../../../qmp/005/b/index.xhtml";
                    });
                }
                else {
                    location.href = "../../../qmp/005/b/index.xhtml";
                }
            };
            ViewModel.prototype.btn003Click = function (item, event) {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function () {
                        location.href = "../../../qmp/005/b/index.xhtml";
                    });
                }
                else {
                    location.href = "../../../qmp/005/b/index.xhtml";
                }
            };
            ViewModel.prototype.btn004Click = function (item, event) {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function () {
                        location.href = "../../../qmp/005/b/index.xhtml";
                    });
                }
                else {
                    location.href = "../../../qmp/005/b/index.xhtml";
                }
            };
            ViewModel.prototype.btn005Click = function (item, event) {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function () {
                        location.href = "../../../qmp/005/b/index.xhtml";
                    });
                }
                else {
                    location.href = "../../../qmp/005/b/index.xhtml";
                }
            };
            return ViewModel;
        }());
        a.ViewModel = ViewModel;
        var TableRowItem = (function () {
            function TableRowItem(param) {
                this.index = ko.observable(0);
                this.label = ko.observable('');
                this.sel001 = ko.observable(0);
                this.sel002 = ko.observable(0);
                this.sel003 = ko.observable(false);
                this.sel004 = ko.observable(0);
                this.sel005 = ko.observable(0);
                this.sel001Data = ko.observableArray([]);
                this.sel002Data = ko.observableArray([]);
                this.sel004Data = ko.observableArray([]);
                this.sel005Data = ko.observableArray([]);
                this.index(param.index);
                this.label(param.label);
                this.sel001Data(param.sel001Data);
                if (param.sel001Data[0])
                    this.sel001(param.sel001Data[0].index);
                this.sel002Data(param.sel002Data);
                if (param.sel002Data[0])
                    this.sel002(param.sel002Data[0].index);
                this.sel003(param.sel003);
                this.sel004Data(param.sel004Data);
                if (param.sel004Data[0])
                    this.sel004(param.sel004Data[0].index);
                this.sel005Data(param.sel005Data);
                if (param.sel005Data[0])
                    this.sel005(param.sel005Data[0].index);
            }
            TableRowItem.prototype.enable = function () {
                return this.label() != '';
            };
            TableRowItem.prototype.showModalDialogB = function (item, event) {
                var self = this;
                nts.uk.ui.windows.setShared('dataRow', item);
                nts.uk.ui.windows.sub.modal("../b/index.xhtml", { width: 1020, height: 730, title: '支払日の設定', dialogClass: "no-close" })
                    .onClosed(function () {
                    __viewContext["viewModel"].start();
                });
            };
            TableRowItem.prototype.showModalDialogC = function (item, event) {
                var self = this;
                nts.uk.ui.windows.setShared('dataRow', item);
                nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 800, height: 350, title: '処理区分の追加', dialogClass: "no-close" })
                    .onClosed(function () {
                    __viewContext["viewModel"].start();
                });
            };
            TableRowItem.prototype.showModalDialogD = function (item, event) {
                var self = this;
                nts.uk.ui.windows.setShared('dataRow', item);
                nts.uk.ui.windows.sub.modal("../d/index.xhtml", { width: 800, height: 370, title: '処理区分の編集', dialogClass: "no-close" })
                    .onClosed(function () {
                    __viewContext["viewModel"].start();
                });
            };
            return TableRowItem;
        }());
    })(a = qmm005.a || (qmm005.a = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.a.viewmodel.js.map