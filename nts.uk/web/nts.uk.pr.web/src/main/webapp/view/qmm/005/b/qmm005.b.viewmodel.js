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
                        $('#B_LST_001').ntsListBox('deselectAll');
                    }
                });
                self.start();
            }
            ViewModel.prototype.start = function () {
                var self = this;
                var lst002Data = [];
                b.services.getData(self.index()).done(function (resp) {
                    if (resp && resp.length > 0) {
                        var lst001Data = [], dataRow = nts.uk.ui.windows.getShared('dataRow');
                        var _loop_1 = function(i, rec) {
                            var year = rec.processingYm["getYearInYm"](), yearIJE = year + "(" + year["yearInJapanEmpire"]() + ")", row = {};
                            if (!_.find(lst001Data, function (item) { return item.value == year; })) {
                                lst001Data.push(new qmm005.common.SelectItem({ index: i + 1, label: yearIJE, value: year, selected: year == dataRow.sel001() }));
                            }
                        };
                        for (var i = 0, rec = void 0; i <= 11, rec = resp[i]; i++) {
                            _loop_1(i, rec);
                        }
                        self.lst001Data(lst001Data);
                        var selectRow = _.find(lst001Data, function (item) { return item.selected; });
                        if (selectRow) {
                            self.lst001(selectRow.index);
                        }
                    }
                });
                for (var i = 1; i <= 12; i++) {
                    lst002Data.push(new TableRowItem({
                        index: i,
                        label: '',
                        sel001: i % 2 == 0 ? true : false,
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
                }
                self.lst002Data(lst002Data);
            };
            ViewModel.prototype.toggleColumns = function (item, event) {
                $('.toggle').toggleClass('hidden');
                $(event.currentTarget).parent('td').toggleClass('checkbox-cols');
                ($(event.currentTarget).text() == "-" && $(event.currentTarget).text('+')) || $(event.currentTarget).text('-');
                if (!$('.toggle').hasClass('hidden')) {
                    nts.uk.ui.windows.getSelf().setWidth(1515);
                }
                else {
                    nts.uk.ui.windows.getSelf().setWidth(1070);
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
                var self = this;
                self.index = ko.observable(param.index);
                self.label = ko.observable(param.label);
                self.sel002 = ko.observable(0);
                self.sel001 = ko.observable(param.sel001);
                self.sel002Data = ko.observableArray(param.sel002Data);
                if (param.sel002Data[0]) {
                    self.sel002(param.sel002Data[0].index);
                }
                self.inp003 = ko.observable(param.inp003);
                self.inp004 = ko.observable(param.inp004);
                self.inp005 = ko.observable(param.inp005);
                self.inp006 = ko.observable(param.inp006);
                self.inp007 = ko.observable(param.inp007);
                self.inp008 = ko.observable(param.inp008);
                self.inp009 = ko.observable(param.inp009);
                self.inp010 = ko.observable(param.inp010);
            }
            TableRowItem.prototype.toggleCalendar = function (item, event) {
                $(event.currentTarget).parent('div').find('input[type=text]').trigger('click');
            };
            return TableRowItem;
        }());
    })(b = qmm005.b || (qmm005.b = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.b.viewmodel.js.map