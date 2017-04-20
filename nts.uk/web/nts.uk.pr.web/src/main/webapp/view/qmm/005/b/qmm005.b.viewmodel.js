/// <reference path="../qmm005.ts"/>
var qmm005;
(function (qmm005) {
    var b;
    (function (b) {
        class ViewModel {
            constructor() {
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
                let self = this;
                // toggle width of dialog
                self.toggle.subscribe(function (v) {
                    if (v) {
                        nts.uk.ui.windows.getSelf().setWidth(1020);
                    }
                    else {
                        nts.uk.ui.windows.getSelf().setWidth(1465);
                    }
                });
                self.toggle.valueHasMutated();
                // processingNo
                let dataRow = nts.uk.ui.windows.getShared('dataRow');
                self.processingNo(dataRow.index());
                self.lbl002(dataRow.index());
                self.lbl003(dataRow.label());
                self.lst001.subscribe(function (v) {
                    if (v && v != 0 && v != parseInt(self.dirty.lst001.initialState)) {
                        if (self.dirty.isDirty()) {
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                                let lst001Data = self.lst001Data(), currentItem = _.find(lst001Data, function (item) { return item.index == v; });
                                if (currentItem) {
                                    self.inp001(currentItem.value);
                                    self.reloadData(currentItem.value);
                                }
                            }).ifNo(function () {
                                self.lst001(parseInt(self.dirty.lst001.initialState));
                            });
                        }
                        else {
                            let lst001Data = self.lst001Data(), currentItem = _.find(lst001Data, function (item) { return item.index == v; });
                            if (currentItem) {
                                self.inp001(currentItem.value);
                                self.reloadData(currentItem.value);
                            }
                        }
                    }
                });
                self.inp001.subscribe(function (v) {
                    if (v) {
                        self.lbl005("(" + v["yearInJapanEmpire"]() + ")");
                    }
                    else {
                        self.lbl005("");
                    }
                    if (self.lst001() == null) {
                        for (let i = 0; i < 12; i++) {
                            let row = self.lst002Data()[i];
                            if (row) {
                                row.year(v || new Date().getFullYear());
                                row.year.valueHasMutated();
                            }
                        }
                    }
                });
                self.start();
            }
            start() {
                let self = this, lst001Data = [], dataRow = nts.uk.ui.windows.getShared('dataRow');
                b.services.getData(self.processingNo()).done(function (resp) {
                    if (resp && resp.length > 0) {
                        self.dataSources = _.orderBy(resp, ["processingYm"], ["asc"]);
                        for (let i = 0, rec; rec = self.dataSources[i]; i++) {
                            let year = rec.processingYm["getYearInYm"](), yearIJE = year + "(" + year["yearInJapanEmpire"]() + ")";
                            if (!_.find(lst001Data, function (item) { return item.value == year; })) {
                                lst001Data.push(new qmm005.common.SelectItem({ index: lst001Data.length + 1, label: yearIJE, value: year, selected: year == dataRow.sel001() }));
                            }
                        }
                        self.lst001Data(lst001Data);
                        // Selected year match with parent row
                        let selectRow = _.find(lst001Data, function (item) { return item.selected; });
                        if (selectRow) {
                            self.lst001(selectRow.index);
                            self.lst001.valueHasMutated();
                            // Load data to table
                            self.reloadData(selectRow.value);
                        }
                    }
                });
            }
            reloadData(year) {
                console.log(_.filter(this.dataSources, (item) => { return item.processingYm == 201707; }));
                let self = this, lst002Data = [], dataRow = nts.uk.ui.windows.getShared('dataRow');
                // Redefined year value
                year = year || dataRow.sel001();
                let dataSources = self.dataSources.filter(function (item) { return item.processingYm["getYearInYm"]() == year; });
                for (let i = 0, rec; rec = self.dataSources[i]; i++) {
                    let rec = dataSources[i];
                    if (rec) {
                        let $moment = moment(new Date(rec.payDate)), month = $moment.month() + 1, sel002Data = [];
                        for (var j = 1; j <= $moment.daysInMonth(); j++) {
                            var date = moment(new Date($moment.year(), $moment.month(), j));
                            sel002Data.push(new qmm005.common.SelectItem({
                                index: j,
                                label: date.format("YYYY/MM/DD"),
                                value: date.toDate()
                            }));
                        }
                        let row = _.find(lst002Data, function (item) { return item.month() == month; });
                        if (!row) {
                            row = new TableRowItem({
                                month: month,
                                year: year,
                                sel001: rec.payBonusAtr == 1 ? true : false,
                                sel002: $moment.date(),
                                sel002Data: sel002Data,
                                inp003: new Date(rec.stdDate),
                                inp004: rec.socialInsLevyMon["formatYearMonth"]("/"),
                                inp005: rec.stmtOutputMon["formatYearMonth"]("/"),
                                inp006: new Date(rec.socialInsStdDate),
                                inp007: new Date(rec.empInsStdDate),
                                inp008: new Date(rec.incomeTaxStdDate),
                                inp009: new Date(rec.accountingClosing),
                                inp010: rec.neededWorkDay
                            });
                            lst002Data.push(row);
                        }
                        else {
                            row.sel001(rec.payBonusAtr == 1 ? true : false);
                        }
                    }
                }
                self.lst002Data(lst002Data);
                self.dirty.reset();
            }
            toggleColumns(item, event) {
                let self = this;
                self.toggle(!self.toggle());
            }
            showModalDialogE(item, event) {
                nts.uk.ui.windows.setShared('viewModelB', item);
                nts.uk.ui.windows.sub.modal("../e/index.xhtml", { width: 540, height: 600, title: '処理区分の追加', dialogClass: "no-close" })
                    .onClosed(() => { });
            }
            newData(item, event) {
                let self = this;
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
            }
            saveData(item, event) {
                let self = this, lst002Data = ko.toJS(self.lst002Data()), data = [];
                for (var i in lst002Data) {
                    let item = lst002Data[i], dataSources = _.clone(self.dataSources), processingYm = parseInt((item.year + '' + item.month)['formatYearMonth']()), filterProcessings = _.filter(dataSources, function (item) { return item.processingYm == processingYm; });
                    // update salary info
                    let salary = _.find(filterProcessings, function (item) { return item.payBonusAtr == 0; });
                    {
                        salary.processingNo = self.processingNo();
                        salary.payBonusAtr = 0;
                        salary.processingYm = parseInt((item.year + '' + item.month)['formatYearMonth']());
                        salary.sparePayAtr = 0;
                        salary.payDate = moment.utc(item.sel002Data[item.sel002 - 1].label).toISOString();
                        salary.stdDate = moment.utc(item.inp003).toISOString();
                        salary.accountingClosing = moment.utc(item.inp009).toISOString();
                        salary.socialInsLevyMon = parseInt(item.inp004["formatYearMonth"]());
                        salary.socialInsStdDate = moment.utc(item.inp006).toISOString();
                        salary.incomeTaxStdDate = moment.utc(item.inp008).toISOString();
                        salary.neededWorkDay = item.inp010;
                        salary.empInsStdDate = moment.utc(item.inp007).toISOString();
                        salary.stmtOutputMon = parseInt(item.inp005["formatYearMonth"]());
                    }
                    // If month has bonus
                    if (item.sel001 == true) {
                        if (filterProcessings.length == 1) {
                            // Add new bonus data
                            filterProcessings.push({
                                processingNo: self.processingNo(),
                                payBonusAtr: 1,
                                processingYm: parseInt((item.year + '' + item.month)['formatYearMonth']()),
                                sparePayAtr: 0,
                                payDate: moment.utc(item.sel002Data[item.sel002 - 1].label).toISOString(),
                                stdDate: moment.utc(item.inp003).toISOString(),
                                accountingClosing: moment.utc(item.inp009).toISOString(),
                                socialInsLevyMon: parseInt(item.inp004["formatYearMonth"]()),
                                socialInsStdDate: moment.utc(item.inp006).toISOString(),
                                incomeTaxStdDate: moment.utc(item.inp008).toISOString(),
                                neededWorkDay: item.inp010,
                                empInsStdDate: moment.utc(item.inp007).toISOString(),
                                stmtOutputMon: parseInt(item.inp005["formatYearMonth"]())
                            });
                        }
                        else {
                            // update exist bonus infomation
                            let bonus = _.find(filterProcessings, function (item) { return item.payBonusAtr == 1; });
                            if (bonus) {
                                //bonus.processingNo = self.processingNo();
                                //bonus.payBonusAtr = 0;
                                //bonus.processingYm = parseInt((item.year + '' + item.month)['formatYearMonth']());
                                //bonus.sparePayAtr = 0;
                                bonus.payDate = moment.utc(bonus.payDate).toISOString();
                                bonus.stdDate = moment.utc(bonus.stdDate).toISOString();
                                bonus.accountingClosing = moment.utc(bonus.accountingClosing).toISOString();
                                //bonus.socialInsLevyMon = parseInt(item.inp004["formatYearMonth"]());
                                bonus.socialInsStdDate = moment.utc(bonus.socialInsStdDate).toISOString();
                                bonus.incomeTaxStdDate = moment.utc(bonus.incomeTaxStdDate).toISOString();
                                bonus.neededWorkDay = item.inp010;
                                bonus.empInsStdDate = moment.utc(bonus.empInsStdDate).toISOString();
                            }
                        }
                    }
                    else {
                        filterProcessings = _.filter(filterProcessings, function (item) { return item.payBonusAtr == 0; });
                    }
                    data = data.concat(filterProcessings);
                }
                data = _.orderBy(data, ["payBonusAtr", "processingYm"], ["asc", "asc"]);
                debugger;
                b.services.updatData({ processingNo: self.processingNo(), payDays: data }).done(() => { });
            }
            deleteData(item, event) {
                let self = this;
                // Note
                /// No document for this action
            }
            closeDialog(item, event) {
                let self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function () { nts.uk.ui.windows.close(); });
                }
                else {
                    nts.uk.ui.windows.close();
                }
            }
        }
        b.ViewModel = ViewModel;
        class TableRowItem {
            constructor(param) {
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
                let self = this;
                self.month(param.month);
                self.year(param.year);
                self.year.subscribe(function (v) {
                    self.sel001(false);
                    let sel002Data = [], $moment = moment(new Date(v, self.month() - 1, 1));
                    for (let i = 1; i <= $moment.daysInMonth(); i++) {
                        let date = moment(new Date(v, self.month() - 1, i));
                        sel002Data.push(new qmm005.common.SelectItem({
                            index: i,
                            label: date.format("YYYY/MM/DD"),
                            value: date.toDate()
                        }));
                    }
                    self.sel002Data(sel002Data);
                    self.sel002.valueHasMutated();
                    self.inp003(moment.utc([v, self.month() - 1, 1]).toDate());
                    self.inp004(moment.utc([v, self.month() - 1, 0]).format("YYYY/MM"));
                    self.inp005(moment.utc([v, self.month() - 1, 1]).format("YYYY/MM"));
                    self.inp006(moment.utc([v, self.month() - 1, 0]).toDate());
                    self.inp007(moment.utc([v, self.month() - 1, 1]).toDate());
                    self.inp008(moment.utc([v + 1, 0, 1]).toDate());
                    self.inp009(moment.utc([v, self.month(), 0]).toDate());
                    self.inp010(moment.utc([v, self.month() - 1, 1]).toDate()["getWorkDays"]());
                });
                self.year.valueHasMutated();
                self.sel001(param.sel001);
                self.sel002(param.sel002);
                self.sel002.subscribe(function (v) {
                    if (v) {
                        let currentSel002 = _.find(self.sel002Data(), function (item) { return item.index == v; });
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
        }
    })(b = qmm005.b || (qmm005.b = {}));
})(qmm005 || (qmm005 = {}));
