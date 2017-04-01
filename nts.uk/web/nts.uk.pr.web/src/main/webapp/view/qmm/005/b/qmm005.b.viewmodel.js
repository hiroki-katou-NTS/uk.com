var qmm005;
(function (qmm005) {
    var b;
    (function (b) {
        var ViewModel = (function () {
            function ViewModel() {
                this.index = ko.observable(0);
                this.lbl002 = ko.observable('');
                this.lbl003 = ko.observable('');
                this.lbl005 = ko.observable('');
                this.inp001 = ko.observable(0);
                this.lst001 = ko.observable(0);
                this.lst001Data = ko.observableArray([]);
                this.lst002Data = ko.observableArray([]);
                var self = this;
                // processingNo
                var dataRow = nts.uk.ui.windows.getShared('dataRow');
                self.index(dataRow.index());
                self.lbl002(dataRow.index() + '.');
                self.lbl003(dataRow.label());
                self.lst001.subscribe(function (v) {
                    if (v) {
                        var lst001Data = self.lst001Data(), currentItem = _.find(lst001Data, function (item) { return item.index == v; });
                        if (currentItem) {
                            self.inp001(currentItem.value);
                        }
                    }
                });
                self.inp001.subscribe(function (v) {
                    if (v) {
                        self.lbl005("(" + v["yearInJapanEmpire"]() + ")");
                    }
                    else {
                        self.lbl005("");
                        // Clear all selected value
                        $('#B_LST_001').ntsListBox('deselectAll');
                    }
                });
                self.start();
            }
            ViewModel.prototype.start = function () {
                var self = this;
                b.services.getData(self.index()).done(function (resp) {
                    if (resp && resp.length > 0) {
                        var lst001Data = [], lst002Data = [], dataRow = nts.uk.ui.windows.getShared('dataRow');
                        var _loop_1 = function(i, rec) {
                            if (rec) {
                                var year_1 = rec.processingYm["getYearInYm"](), yearIJE = year_1 + "(" + year_1["yearInJapanEmpire"]() + ")", index = i + 1;
                                lst002Data.push(new TableRowItem({
                                    index: index,
                                    label: index + '月の設定',
                                    sel001: rec.payBonusAtr == 1 ? true : false,
                                    sel002: 0,
                                    sel002Data: [new qmm005.common.SelectItem({ index: -1, label: "" })],
                                    inp003: new Date(),
                                    inp004: new Date(),
                                    inp005: new Date(),
                                    inp006: new Date(),
                                    inp007: new Date(),
                                    inp008: new Date(),
                                    inp009: new Date(),
                                    inp010: 0
                                }));
                                if (!_.find(lst001Data, function (item) { return item.value == year_1; })) {
                                    lst001Data.push(new qmm005.common.SelectItem({ index: i + 1, label: yearIJE, value: year_1, selected: year_1 == dataRow.sel001() }));
                                }
                            }
                        };
                        for (var i = 0, rec = void 0; i <= 11, rec = resp[i]; i++) {
                            _loop_1(i, rec);
                        }
                        self.lst001Data(lst001Data);
                        self.lst002Data(lst002Data);
                        /// Select tạm ra một object (kiband có sửa phần này)
                        var selectRow = _.find(lst001Data, function (item) { return item.selected; });
                        if (selectRow) {
                            self.lst001(selectRow.index);
                        }
                    }
                });
            };
            ViewModel.prototype.toggleColumns = function (item, event) {
                $('.toggle').toggleClass('hidden');
                $(event.currentTarget).parent('td').toggleClass('checkbox-cols');
                ($(event.currentTarget).text() == "-" && $(event.currentTarget).text('+')) || $(event.currentTarget).text('-');
                if (!$('.toggle').hasClass('hidden')) {
                    nts.uk.ui.windows.getSelf().setWidth(1465);
                }
                else {
                    nts.uk.ui.windows.getSelf().setWidth(1020);
                }
            };
            ViewModel.prototype.showModalDialogC = function (item, event) {
                nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 682, height: 370, title: '処理区分の追加' })
                    .onClosed(function () { });
            };
            ViewModel.prototype.showModalDialogD = function (item, event) {
                nts.uk.ui.windows.sub.modal("../d/index.xhtml", { width: 682, height: 410, title: '処理区分の追加' })
                    .onClosed(function () { });
            };
            ViewModel.prototype.showModalDialogE = function (item, event) {
                nts.uk.ui.windows.sub.modal("../e/index.xhtml", { width: 540, height: 600, title: '処理区分の追加' })
                    .onClosed(function () { });
            };
            ViewModel.prototype.initNewData = function (item, event) {
                var self = this;
                debugger;
                self.inp001(null);
                self.lst001(null);
            };
            ViewModel.prototype.saveData = function (item, event) {
                var self = this;
                debugger;
            };
            ViewModel.prototype.deleteData = function (item, event) {
                var self = this;
                debugger;
            };
            ViewModel.prototype.closeDialog = function (item, event) { nts.uk.ui.windows.close(); };
            return ViewModel;
        }());
        b.ViewModel = ViewModel;
        var Model = (function () {
            function Model() {
            }
            return Model;
        }());
        var TableRowItem = (function () {
            function TableRowItem(param) {
                this.index = ko.observable(0);
                this.label = ko.observable('');
                this.sel001 = ko.observable(false);
                this.sel002 = ko.observable(0);
                this.inp003 = ko.observable(null);
                this.inp004 = ko.observable(null);
                this.inp005 = ko.observable(null);
                this.inp006 = ko.observable(null);
                this.inp007 = ko.observable(null);
                this.inp008 = ko.observable(null);
                this.inp009 = ko.observable(null);
                this.inp010 = ko.observable(0);
                this.sel002Data = ko.observableArray([]);
                var self = this;
                self.index(param.index);
                self.label(param.label);
                self.sel002(param.sel002);
                self.sel001(param.sel001);
                self.inp003(param.inp003);
                self.inp004(param.inp004);
                self.inp005(param.inp005);
                self.inp006(param.inp006);
                self.inp007(param.inp007);
                self.inp008(param.inp008);
                self.inp009(param.inp009);
                self.inp010(param.inp010);
                self.sel002Data(param.sel002Data);
            }
            TableRowItem.prototype.toggleCalendar = function (item, event) {
                $(event.currentTarget).parent('div').find('input[type=text]').trigger('click');
            };
            return TableRowItem;
        }());
    })(b = qmm005.b || (qmm005.b = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.b.viewmodel.js.map