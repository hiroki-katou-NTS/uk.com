/// <reference path="../qmm005.ts"/>
module qmm005.b {
    export class ViewModel {
        toggle: KnockoutObservable<boolean> = ko.observable(true);
        processingNo: KnockoutObservable<number> = ko.observable(0);
        lbl002: KnockoutObservable<string> = ko.observable('');
        lbl003: KnockoutObservable<string> = ko.observable('');
        lbl005: KnockoutObservable<string> = ko.observable('');
        inp001: KnockoutObservable<number> = ko.observable(0);
        lst001: KnockoutObservable<number> = ko.observable(0);
        lst001Data: KnockoutObservableArray<common.SelectItem> = ko.observableArray([]);
        lst002Data: KnockoutObservableArray<TableRowItem> = ko.observableArray([]);
        dataSources: Array<PaydayDto> = [];
        dirty: IDirty = {
            inp001: new nts.uk.ui.DirtyChecker(this.inp001),
            lst001: new nts.uk.ui.DirtyChecker(this.lst001),
            lst002: new nts.uk.ui.DirtyChecker(this.lst002Data),
            isDirty: function() {
                return this.inp001.isDirty() || this.lst002.isDirty();
            },
            reset: function() {
                this.inp001.reset();
                this.lst001.reset();
                this.lst002.reset();
            }
        };
        constructor() {
            let self = this;
            
            // toggle width of dialog
            self.toggle.subscribe(function(v) {
                if (v) {
                    nts.uk.ui.windows.getSelf().setWidth(1020);
                } else {
                    nts.uk.ui.windows.getSelf().setWidth(1465);
                }
            });
            self.toggle.valueHasMutated();

            // processingNo
            let dataRow = nts.uk.ui.windows.getShared('dataRow');
            self.processingNo(dataRow.index());
            self.lbl002(dataRow.index());
            self.lbl003(dataRow.label());

            self.lst001.subscribe(function(v) {
                if (v && v != 0 && v != parseInt(self.dirty.lst001.initialState)) {
                    if (self.dirty.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function() {
                            let lst001Data = self.lst001Data(),
                                currentItem = _.find(lst001Data, function(item) { return item.index == v; });
                            if (currentItem) {
                                self.inp001(currentItem.value);
                                self.reloadData(currentItem.value);
                            }
                        }).ifNo(function() {
                            self.lst001(parseInt(self.dirty.lst001.initialState));
                        });
                    } else {
                        let lst001Data = self.lst001Data(),
                            currentItem = _.find(lst001Data, function(item) { return item.index == v; });
                        if (currentItem) {
                            self.inp001(currentItem.value);
                            self.reloadData(currentItem.value);
                        }
                    }
                }
            });

            self.inp001.subscribe(function(v) {
                if (v) {
                    self.lbl005("(" + v["yearInJapanEmpire"]() + ")");
                } else {
                    self.lbl005("");
                }
                if (self.lst001() == null) {
                    for (let i = 0; i < 12; i++) {
                        let row = self.lst002Data()[i] as TableRowItem;
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
            let self = this,
                lst001Data: Array<common.SelectItem> = [],
                dataRow = nts.uk.ui.windows.getShared('dataRow');

            services.getData(self.processingNo()).done(function(resp: Array<PaydayDto>) {
                if (resp && resp.length > 0) {
                    self.dataSources = _.orderBy(resp, ["processingYm"], ["asc"]);
                    for (let i: number = 0, rec: PaydayDto; rec = self.dataSources[i]; i++) {
                        let year = rec.processingYm["getYearInYm"](),
                            yearIJE = year + "(" + year["yearInJapanEmpire"]() + ")";

                        if (!_.find(lst001Data, function(item) { return item.value == year; })) {
                            lst001Data.push(new common.SelectItem({ index: lst001Data.length + 1, label: yearIJE, value: year, selected: year == dataRow.sel001() }));
                        }
                    }
                    self.lst001Data(lst001Data);

                    // Selected year match with parent row
                    let selectRow = _.find(lst001Data, function(item) { return item.selected; });
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
            let self = this,
                lst002Data: Array<TableRowItem> = [],
                dataRow = nts.uk.ui.windows.getShared('dataRow');

            // Redefined year value
            year = year || dataRow.sel001();

            let dataSources = self.dataSources.filter(function(item) { return item.processingYm["getYearInYm"]() == year; });

            for (let i: number = 0, rec: PaydayDto; rec = self.dataSources[i]; i++) {
                let rec: PaydayDto = dataSources[i];
                if (rec) {
                    let $moment = moment(new Date(rec.payDate)),
                        month = $moment.month() + 1,
                        sel002Data: Array<common.SelectItem> = [];

                    for (var j: number = 1; j <= $moment.daysInMonth(); j++) {
                        var date = moment(new Date($moment.year(), $moment.month(), j));
                        sel002Data.push(new common.SelectItem({
                            index: j,
                            label: date.format("YYYY/MM/DD"),
                            value: date.toDate()
                        }));
                    }

                    let row = _.find(lst002Data, function(item) { return item.month() == month; });
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
                    } else {
                        row.sel001(rec.payBonusAtr == 1 ? true : false);
                    }
                }
            }
            self.lst002Data(lst002Data);
            self.dirty.reset();
        }

        toggleColumns(item, event): void {
            let self = this;
            self.toggle(!self.toggle());
        }

        showModalDialogE(item, event): void {
            nts.uk.ui.windows.setShared('viewModelB', item);
            nts.uk.ui.windows.sub.modal("../e/index.xhtml", { width: 540, height: 600, title: '処理区分の追加', dialogClass: "no-close" })
                .onClosed(() => { });
        }

        newData(item, event) {
            let self = this;
            if (self.dirty.isDirty()) {
                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function() {
                    self.lst001(null);
                    self.inp001(null);
                    self.dirty.reset();
                })
            } else {
                self.lst001(null);
                self.inp001(null);
                self.dirty.reset();
            }
        }

        saveData(item, event) {
            let self = this;
            debugger;
        }

        deleteData(item, event) {
            let self = this;

        }

        closeDialog(item, event) {
            let self = this;
            if (self.dirty.isDirty()) {
                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function() { nts.uk.ui.windows.close(); });
            } else {
                nts.uk.ui.windows.close();
            }
        }
    }

    interface ITableRowItem {
        month: number;
        year: number;
        sel001: boolean;
        sel002: number;
        sel002Data: Array<common.SelectItem>;
        inp003: Date;
        inp004: string;
        inp005: string;
        inp006: Date;
        inp007: Date;
        inp008: Date;
        inp009: Date;
        inp010: number;
    }

    class TableRowItem {
        month: KnockoutObservable<number> = ko.observable(0);
        year: KnockoutObservable<number> = ko.observable(0);
        sel001: KnockoutObservable<boolean> = ko.observable(false);
        sel002: KnockoutObservable<number> = ko.observable(0);
        sel002L: KnockoutObservable<string> = ko.observable('');
        inp003: KnockoutObservable<Date> = ko.observable(null);
        inp004: KnockoutObservable<string> = ko.observable('');
        inp005: KnockoutObservable<string> = ko.observable('');
        inp006: KnockoutObservable<Date> = ko.observable(null);
        inp007: KnockoutObservable<Date> = ko.observable(null);
        inp008: KnockoutObservable<Date> = ko.observable(null);
        inp009: KnockoutObservable<Date> = ko.observable(null);
        inp010: KnockoutObservable<number> = ko.observable(0);

        sel002Data: KnockoutObservableArray<common.SelectItem> = ko.observableArray([]);

        constructor(param: ITableRowItem) {
            let self = this;
            self.month(param.month);
            self.year(param.year);
            self.year.subscribe(function(v) {
                self.sel001(false);
                let sel002Data: Array<common.SelectItem> = [], $moment = moment(new Date(v, self.month() - 1, 1));
                for (let i = 1; i <= $moment.daysInMonth(); i++) {
                    let date = moment(new Date(v, self.month() - 1, i));
                    sel002Data.push(new common.SelectItem({
                        index: i,
                        label: date.format("YYYY/MM/DD"),
                        value: date.toDate()
                    }));
                }
                self.sel002Data(sel002Data);
                self.sel002.valueHasMutated();
                self.inp003(new Date(v, self.month() - 1, 1))
                self.inp004(new Date(v, self.month() - 1, 0)["formatYearMonth"]("/"))
                self.inp005(new Date(v, self.month() - 1, 1)["formatYearMonth"]("/"));
                self.inp006(new Date(v, self.month() - 1, 0));
                self.inp007(new Date(v, self.month() - 1, 1));
                self.inp008(new Date(v + 1, 0, 1));
                self.inp009(new Date(v, self.month(), 0));
                self.inp010(new Date(v, self.month() - 1, 1)["getWorkDays"]());
            });
            self.year.valueHasMutated();

            self.sel001(param.sel001);
            self.sel002(param.sel002);
            self.sel002.subscribe(function(v) {
                if (v) {
                    let currentSel002 = _.find(self.sel002Data(), function(item) { return item.index == v; });
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

    interface PaydayDto {
        processingNo: number;
        payBonusAtr: number;
        processingYm: number;
        sparePayAtr: number;
        payDate: string;
        stdDate: string;
        accountingClosing: string;
        socialInsLevyMon: number;
        socialInsStdDate: string;
        incomeTaxStdDate: string;
        neededWorkDay: number;
        empInsStdDate: string;
        stmtOutputMon: number;
    }

    interface IDirty {
        inp001: nts.uk.ui.DirtyChecker,
        lst001: nts.uk.ui.DirtyChecker,
        lst002: nts.uk.ui.DirtyChecker,
        isDirty: any,
        reset: any
    }
}