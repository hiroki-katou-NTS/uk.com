/// <reference path="../qmm005.ts"/>
var qmm005;
(function (qmm005) {
    var b;
    (function (b) {
        var ViewModel = (function () {
            function ViewModel() {
                this.toggle = ko.observable(true);
                this.processingNo = ko.observable(0);
                this.lbl002 = ko.observable('');
                this.lbl003 = ko.observable('');
                this.lbl005 = ko.observable('');
                this.inp001 = ko.observable(0);
                this.lst001 = ko.observable(0);
                this.lst001Data = ko.observableArray([]);
                this.lst002Data = ko.observableArray([]);
                this.dataSources = [];
                this.enabled = ko.observable(true);
                this.dirty = {
                    inp001: new nts.uk.ui.DirtyChecker(this.inp001),
                    lst001: new nts.uk.ui.DirtyChecker(this.lst001),
                    lst002: new nts.uk.ui.DirtyChecker(this.lst002Data),
                    isDirty: function () {
                        return this.inp001.isDirty() || this.lst002.isDirty();
                    },
                    reset: function () {
                        this.inp001.reset();
                        this.lst001.reset();
                        this.lst002.reset();
                    }
                };
                var self = this;
                // toggle width of dialog
                self.toggle.subscribe(function (v) {
                    if (v) {
                        $('#contents-area').css('width', '');
                        $('#contents-area').css('padding-bottom', '');
                        if (window["large"]) {
                            nts.uk.ui.windows.getSelf().setWidth(1025);
                        }
                        else {
                            $('#contents-area').css('padding-bottom', '10px');
                            nts.uk.ui.windows.getSelf().setWidth(1035);
                        }
                    }
                    else {
                        if (window["large"]) {
                            nts.uk.ui.windows.getSelf().setWidth(1465);
                        }
                        else {
                            $('#contents-area').css('width', '1465px');
                            $('#contents-area').css('padding-bottom', '10px');
                            nts.uk.ui.windows.getSelf().setWidth(1300);
                        }
                    }
                });
                self.toggle.valueHasMutated();
                // processingNo
                var dataRow = nts.uk.ui.windows.getShared('dataRow');
                self.processingNo(dataRow.index());
                self.lbl002(dataRow.index());
                self.lbl003(dataRow.label());
                self.lst001.subscribe(function (v) {
                    var value = v && !!parseInt(v.toString());
                    self.enabled(value);
                    if (value && v != parseInt(self.dirty.lst001.initialState)) {
                        if (self.dirty.isDirty()) {
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function () {
                                var lst001Data = self.lst001Data(), currentItem = _.find(lst001Data, function (item) { return item.index == v; });
                                if (currentItem) {
                                    self.inp001(currentItem.value);
                                    self.reloadData(currentItem.value);
                                }
                            }).ifNo(function () {
                                self.lst001(parseInt(self.dirty.lst001.initialState));
                            });
                        }
                        else {
                            var lst001Data = self.lst001Data(), currentItem = _.find(lst001Data, function (item) { return item.index == v; });
                            if (currentItem) {
                                self.inp001(currentItem.value);
                                self.reloadData(currentItem.value);
                            }
                        }
                    }
                });
                self.inp001.subscribe(function (v) {
                    var value = v && !!parseInt(v.toString());
                    if (value) {
                        self.lbl005("(" + v["yearInJapanEmpire"]() + ")");
                    }
                    else {
                        self.lbl005("");
                    }
                    if (self.lst001() == null || self.lst001() == undefined || !self.lst001()) {
                        for (var i = 0; i < 12; i++) {
                            var row = self.lst002Data()[i];
                            if (row) {
                                row.year(v && parseInt(v.toString()) || new Date().getFullYear());
                                row.year.valueHasMutated();
                            }
                        }
                    }
                });
                self.start();
            }
            ViewModel.prototype.start = function () {
                var self = this, dfd = $.Deferred(), lst001Data = [], dataRow = nts.uk.ui.windows.getShared('dataRow');
                b.services.getData(self.processingNo()).done(function (resp) {
                    if (resp && resp.length > 0) {
                        self.dataSources = _.orderBy(resp, ["processingYm"], ["asc"]);
                        var _loop_1 = function(i, rec) {
                            var year = rec.processingYm["getYearInYm"](), yearIJE = year + "(" + year["yearInJapanEmpire"]() + ")";
                            if (!_.find(lst001Data, function (item) { return item.value == year; })) {
                                lst001Data.push(new qmm005.common.SelectItem({ index: lst001Data.length + 1, label: yearIJE, value: year, selected: year == dataRow.sel001() }));
                            }
                        };
                        for (var i = 0, rec = void 0; rec = self.dataSources[i]; i++) {
                            _loop_1(i, rec);
                        }
                        self.lst001Data(lst001Data);
                        // Selected year match with parent row
                        var selectRow = _.find(lst001Data, function (item) { return item.selected; });
                        if (selectRow) {
                            self.lst001(selectRow.index);
                            self.lst001.valueHasMutated();
                            // Load data to table
                            self.reloadData(selectRow.value);
                            dfd.resolve();
                        }
                    }
                });
                return dfd.promise();
            };
            ViewModel.prototype.reloadData = function (year) {
                var self = this, lst002Data = [], dataRow = nts.uk.ui.windows.getShared('dataRow');
                // Redefined year value
                year = year || dataRow.sel001();
                var dataSources = self.dataSources.filter(function (item) { return item.processingYm["getYearInYm"]() == year; });
                var _loop_2 = function(i, rec) {
                    var rec_1 = dataSources[i];
                    if (rec_1) {
                        var $moment = moment.utc(rec_1.payDate), month_1 = $moment.month() + 1, sel002Data = [];
                        for (j = 1; j <= $moment.daysInMonth(); j++) {
                            date = moment.utc([$moment.year(), $moment.month(), j]);
                            sel002Data.push(new qmm005.common.SelectItem({
                                index: j,
                                label: date.format("YYYY/MM/DD"),
                                value: date.toDate()
                            }));
                        }
                        var row = _.find(lst002Data, function (item) { return item.month() == month_1; });
                        if (!row) {
                            row = new TableRowItem({
                                month: month_1,
                                year: year,
                                sel001: rec_1.payBonusAtr == 1 ? true : false,
                                sel002: $moment.date(),
                                sel002Data: sel002Data,
                                inp003: moment.utc(rec_1.stdDate).toDate(),
                                inp004: rec_1.socialInsLevyMon["formatYearMonth"]("/"),
                                inp005: rec_1.stmtOutputMon["formatYearMonth"]("/"),
                                inp006: moment.utc(rec_1.socialInsStdDate).toDate(),
                                inp007: moment.utc(rec_1.empInsStdDate).toDate(),
                                inp008: moment.utc(rec_1.incomeTaxStdDate).toDate(),
                                inp009: moment.utc(rec_1.accountingClosing).toDate(),
                                inp010: rec_1.neededWorkDay
                            });
                            lst002Data.push(row);
                        }
                        else {
                            row.sel001(rec_1.payBonusAtr == 1 ? true : false);
                        }
                    }
                };
                var j, date;
                for (var i = 0, rec = void 0; rec = self.dataSources[i]; i++) {
                    _loop_2(i, rec);
                }
                self.lst002Data(lst002Data);
                self.dirty.reset();
            };
            ViewModel.prototype.toggleColumns = function (item, event) {
                var self = this;
                self.toggle(!self.toggle());
            };
            ViewModel.prototype.showModalDialogE = function (item, event) {
                var self = this;
                nts.uk.ui.windows.setShared('viewModelB', item);
                nts.uk.ui.windows.sub.modal("../e/index.xhtml", { width: 540, height: 600, title: '処理区分の追加', dialogClass: "no-close" })
                    .onClosed(function () {
                    var model = nts.uk.ui.windows.getShared("viewModelE");
                    if (model) {
                        // update data to current processing year month when close dialog
                        for (var i in self.lst002Data()) {
                            if (model.flectionStartMonth <= parseInt(i.toString()) + 1) {
                                var row = self.lst002Data()[i];
                                if (row) {
                                    if (model.sel003) {
                                        row.sel002(new Date(row.year(), row.month(), 0).getDate());
                                    }
                                    if (model.sel004) {
                                        row.inp003(moment.utc([row.year(), row.month() - 1, 1]).toDate());
                                    }
                                    if (model.sel005) {
                                        row.inp004(moment.utc(new Date(row.year(), row.month() - 1, 0)).format("YYYY/MM"));
                                    }
                                    if (model.sel006) {
                                        row.inp006(moment.utc(new Date(row.year(), row.month() - 1, 0)).toDate());
                                    }
                                    if (model.sel007) {
                                        row.inp007(moment.utc(new Date(row.year(), row.month() - 1, 0).setDate(1)).toDate());
                                    }
                                    if (model.sel008) {
                                        row.inp008(moment.utc([row.year() + 1, row.month() - 1, 1]).toDate());
                                    }
                                    if (model.sel009) {
                                        row.inp009(moment.utc([row.year(), row.month() - 1, 1]).toDate());
                                    }
                                }
                            }
                        }
                    }
                });
            };
            ViewModel.prototype.newData = function (item, event) {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function () {
                        self.lst001(null);
                        self.inp001(null);
                        self.dirty.reset();
                    });
                }
                else {
                    self.lst001(null);
                    self.inp001(null);
                    self.dirty.reset();
                }
            };
            ViewModel.prototype.saveData = function (item, event) {
                var self = this, lst002Data = ko.toJS(self.lst002Data()), data = [];
                var _loop_3 = function() {
                    var item_1 = lst002Data[i], dataSources = _.clone(self.dataSources), processingYm = parseInt((item_1.year + '' + item_1.month)['formatYearMonth']()), filterProcessings = _.filter(dataSources, function (item) { return item.processingYm == processingYm; });
                    // update salary info
                    var salary = _.find(filterProcessings, function (item) { return item.payBonusAtr == 0; });
                    if (salary != undefined) {
                        salary.processingNo = self.processingNo();
                        salary.payBonusAtr = 0;
                        salary.processingYm = parseInt((item_1.year + '' + item_1.month)['formatYearMonth']());
                        salary.sparePayAtr = 0;
                        salary.payDate = moment.utc(item_1.sel002Data[item_1.sel002 - 1].label).toISOString();
                        salary.stdDate = moment.utc(item_1.inp003).toISOString();
                        salary.accountingClosing = moment.utc(item_1.inp009).toISOString();
                        salary.socialInsLevyMon = parseInt(item_1.inp004["formatYearMonth"]());
                        salary.socialInsStdDate = moment.utc(item_1.inp006).toISOString();
                        salary.incomeTaxStdDate = moment.utc(item_1.inp008).toISOString();
                        salary.neededWorkDay = item_1.inp010;
                        salary.empInsStdDate = moment.utc(item_1.inp007).toISOString();
                        salary.stmtOutputMon = parseInt(item_1.inp005["formatYearMonth"]());
                    }
                    else {
                        // Add new salary data
                        filterProcessings.push({
                            processingNo: self.processingNo(),
                            payBonusAtr: 0,
                            processingYm: parseInt((self.inp001() + '' + item_1.month)['formatYearMonth']()),
                            sparePayAtr: 0,
                            payDate: moment.utc(item_1.sel002Data[item_1.sel002 - 1].label).toISOString(),
                            stdDate: moment.utc(item_1.inp003).toISOString(),
                            accountingClosing: moment.utc(item_1.inp009).toISOString(),
                            socialInsLevyMon: parseInt(item_1.inp004["formatYearMonth"]()),
                            socialInsStdDate: moment.utc(item_1.inp006).toISOString(),
                            incomeTaxStdDate: moment.utc(item_1.inp008).toISOString(),
                            neededWorkDay: item_1.inp010,
                            empInsStdDate: moment.utc(item_1.inp007).toISOString(),
                            stmtOutputMon: parseInt(item_1.inp005["formatYearMonth"]())
                        });
                    }
                    // If month has bonus
                    if (item_1.sel001 == true) {
                        if (filterProcessings.length == 1) {
                            // Add new bonus data
                            filterProcessings.push({
                                processingNo: self.processingNo(),
                                payBonusAtr: 1,
                                processingYm: parseInt((self.inp001() + '' + item_1.month)['formatYearMonth']()),
                                sparePayAtr: 0,
                                payDate: moment.utc(item_1.sel002Data[item_1.sel002 - 1].label).toISOString(),
                                stdDate: moment.utc(item_1.inp003).toISOString(),
                                accountingClosing: moment.utc(item_1.inp009).toISOString(),
                                socialInsLevyMon: parseInt(item_1.inp004["formatYearMonth"]()),
                                socialInsStdDate: moment.utc(item_1.inp006).toISOString(),
                                incomeTaxStdDate: moment.utc(item_1.inp008).toISOString(),
                                neededWorkDay: item_1.inp010,
                                empInsStdDate: moment.utc(item_1.inp007).toISOString(),
                                stmtOutputMon: parseInt(item_1.inp005["formatYearMonth"]())
                            });
                        }
                        else {
                            // update exist bonus infomation
                            var bonus = _.find(filterProcessings, function (item) { return item.payBonusAtr == 1; });
                            if (bonus) {
                                //bonus.processingNo = self.processingNo();
                                //bonus.payBonusAtr = 0;
                                bonus.processingYm = parseInt((self.inp001() + '' + item_1.month)['formatYearMonth']());
                                //bonus.sparePayAtr = 0;
                                bonus.payDate = moment.utc(bonus.payDate).toISOString();
                                bonus.stdDate = moment.utc(bonus.stdDate).toISOString();
                                bonus.accountingClosing = moment.utc(bonus.accountingClosing).toISOString();
                                //bonus.socialInsLevyMon = parseInt(item.inp004["formatYearMonth"]());
                                bonus.socialInsStdDate = moment.utc(bonus.socialInsStdDate).toISOString();
                                bonus.incomeTaxStdDate = moment.utc(bonus.incomeTaxStdDate).toISOString();
                                bonus.neededWorkDay = item_1.inp010;
                                bonus.empInsStdDate = moment.utc(bonus.empInsStdDate).toISOString();
                            }
                        }
                    }
                    else {
                        filterProcessings = _.filter(filterProcessings, function (item) { return item.payBonusAtr == 0; });
                    }
                    // push info to model
                    data = data.concat(filterProcessings);
                };
                for (var i in lst002Data) {
                    _loop_3();
                }
                data = _.orderBy(data, ["payBonusAtr", "processingYm"], ["asc", "asc"]);
                b.services.updatData({ processingNo: self.processingNo(), payDays: data }).done(function () {
                    //nts.uk.ui.dialog.info("sucessfull");
                    var lst001 = parseInt(self.dirty.lst001.initialState);
                    self.dirty.reset();
                    if (!lst001) {
                        lst001 = parseInt(self.inp001().toString());
                    }
                    self.start().then(function () {
                        self.dirty.reset();
                        var selected = _.find(self.lst001Data(), function (ii) { return ii.value == lst001; });
                        if (selected) {
                            self.lst001(selected.index);
                        }
                        else {
                            self.lst001(lst001);
                        }
                        self.dirty.reset();
                    });
                });
            };
            ViewModel.prototype.deleteData = function (item, event) {
                var self = this;
                // Note
                /// No document for this action
            };
            ViewModel.prototype.closeDialog = function (item, event) {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function () { nts.uk.ui.windows.close(); });
                }
                else {
                    nts.uk.ui.windows.close();
                }
            };
            return ViewModel;
        }());
        b.ViewModel = ViewModel;
        var TableRowItem = (function () {
            function TableRowItem(param) {
                this.month = ko.observable(0);
                this.year = ko.observable(0);
                this.sel001 = ko.observable(false);
                this.sel002 = ko.observable(0);
                this.sel002L = ko.observable('');
                this.inp003 = ko.observable(null);
                this.inp004 = ko.observable('');
                this.inp005 = ko.observable('');
                this.inp006 = ko.observable(null);
                this.inp007 = ko.observable(null);
                this.inp008 = ko.observable(null);
                this.inp009 = ko.observable(null);
                this.inp010 = ko.observable(0);
                this.sel002Data = ko.observableArray([]);
                var self = this;
                self.month(param.month);
                self.year(param.year);
                self.year.subscribe(function (v) {
                    if (v) {
                        self.sel001(false);
                        var sel002Data = [], $moment = moment(new Date(v, self.month() - 1, 1));
                        for (var i = 1; i <= $moment.daysInMonth(); i++) {
                            var date = moment(new Date(v, self.month() - 1, i));
                            sel002Data.push(new qmm005.common.SelectItem({
                                index: i,
                                label: date.format("YYYY/MM/DD"),
                                value: date.toDate()
                            }));
                        }
                        self.sel002Data(sel002Data);
                        self.sel002.valueHasMutated();
                        self.inp003(moment.utc([v, self.month() - 1, 1]).toDate());
                        self.inp004(moment.utc(new Date(v, self.month() - 1, 0)).format("YYYY/MM"));
                        self.inp005(moment.utc([v, self.month() - 1, 1]).format("YYYY/MM"));
                        self.inp006(moment.utc(new Date(v, self.month() - 1, 0)).toDate());
                        self.inp007(moment.utc([v, self.month() - 1, 1]).toDate());
                        self.inp008(moment.utc([v + 1, 0, 1]).toDate());
                        self.inp009(moment.utc(new Date(v, self.month(), 0)).toDate());
                        self.inp010(moment.utc([v, self.month() - 1, 1]).toDate()["getWorkDays"]());
                    }
                });
                self.year.valueHasMutated();
                self.sel001(param.sel001);
                self.sel002(param.sel002);
                self.sel002.subscribe(function (v) {
                    if (v) {
                        var currentSel002 = _.find(self.sel002Data(), function (item) { return item.index == v; });
                        if (currentSel002) {
                            self.sel002L(currentSel002.value["getDayJP"]());
                        }
                    }
                });
                self.sel002.valueHasMutated();
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
            return TableRowItem;
        }());
    })(b = qmm005.b || (qmm005.b = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.b.viewmodel.js.map