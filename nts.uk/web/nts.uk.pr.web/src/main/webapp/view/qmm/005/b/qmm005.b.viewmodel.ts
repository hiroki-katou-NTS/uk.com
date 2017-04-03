module qmm005.b {
    export class ViewModel {
        index: KnockoutObservable<number> = ko.observable(0);
        lbl002: KnockoutObservable<string> = ko.observable('');
        lbl003: KnockoutObservable<string> = ko.observable('');
        lbl005: KnockoutObservable<string> = ko.observable('');
        inp001: KnockoutObservable<number> = ko.observable(0);
        lst001: KnockoutObservable<number> = ko.observable(0);
        lst001Data: KnockoutObservableArray<common.SelectItem> = ko.observableArray([]);
        lst002Data: KnockoutObservableArray<TableRowItem> = ko.observableArray([]);
        constructor() {
            let self = this;
            // processingNo
            let dataRow = nts.uk.ui.windows.getShared('dataRow');
            self.index(dataRow.index());
            self.lbl002(dataRow.index() + '.');
            self.lbl003(dataRow.label());

            self.lst001.subscribe(function(v) {
                if (v) {
                    let lst001Data = self.lst001Data(),
                        currentItem = _.find(lst001Data, function(item) { return item.index == v; });
                    if (currentItem) {
                        self.inp001(currentItem.value);
                    }
                }
            });

            self.inp001.subscribe(function(v) {
                if (v) {
                    self.lbl005("(" + v["yearInJapanEmpire"]() + ")");
                } else {
                    self.lbl005("");

                    // Clear all selected value
                    $('#B_LST_001').ntsListBox('deselectAll');
                }
            });
            self.start();
        }

        start() {
            let self = this;
            services.getData(self.index()).done(function(resp: Array<PaydayDto>) {
                if (resp && resp.length > 0) {
                    resp = _.orderBy(resp, ["processingYm"], ["asc"]);
                    let lst001Data: Array<common.SelectItem> = [],
                        lst002Data: Array<TableRowItem> = [],
                        dataRow = nts.uk.ui.windows.getShared('dataRow');
                    for (let i: number = 0, rec: PaydayDto; i <= 11, rec = resp[i]; i++) {
                        if (rec) {
                            let year = rec.processingYm["getYearInYm"](),
                                yearIJE = year + "(" + year["yearInJapanEmpire"]() + ")",
                                index = i + 1,
                                sel002Data: Array<common.SelectItem> = [],
                                $moment = moment(rec.payDate),
                                row = new TableRowItem({
                                    index: index,
                                    label: index + '月の設定',
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

                            for (var j: number = 1; j <= $moment.daysInMonth(); j++) {
                                var date = moment(new Date($moment.year(), $moment.month(), j));
                                sel002Data.push(new common.SelectItem({
                                    index: j,
                                    label: date.format("YYYY/MM/DD"),
                                    value: date.toDate()
                                }));
                            }

                            row.sel002Data(sel002Data);
                            lst002Data.push(row);

                            if (!_.find(lst001Data, function(item) { return item.value == year; })) {
                                lst001Data.push(new common.SelectItem({ index: i + 1, label: yearIJE, value: year, selected: year == dataRow.sel001() }));
                            }
                        }
                    }
                    self.lst001Data(lst001Data);
                    self.lst002Data(lst002Data);

                    /// Select tạm ra một object (kiband có sửa phần này)
                    let selectRow = _.find(lst001Data, function(item) { return item.selected; });
                    if (selectRow) {
                        self.lst001(selectRow.index);
                    }
                }
            });
        }

        toggleColumns(item, event): void {
            $('.toggle').toggleClass('hidden');
            $(event.currentTarget).parent('td').toggleClass('checkbox-cols');
            ($(event.currentTarget).text() == "-" && $(event.currentTarget).text('+')) || $(event.currentTarget).text('-');
            if (!$('.toggle').hasClass('hidden')) {
                nts.uk.ui.windows.getSelf().setWidth(1465);
            } else {
                nts.uk.ui.windows.getSelf().setWidth(1020);
            }
        }

        showModalDialogC(item, event): void {
            nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 682, height: 370, title: '処理区分の追加' })
                .onClosed(() => { });
        }

        showModalDialogD(item, event): void {
            nts.uk.ui.windows.sub.modal("../d/index.xhtml", { width: 682, height: 410, title: '処理区分の追加' })
                .onClosed(() => { });
        }

        showModalDialogE(item, event): void {
            nts.uk.ui.windows.sub.modal("../e/index.xhtml", { width: 540, height: 600, title: '処理区分の追加' })
                .onClosed(() => { });
        }

        initNewData(item, event) {
            let self = this;
            debugger;
            self.inp001(null);
            self.lst001(null);
        }

        saveData(item, event) {
            let self = this;
            debugger;
        }

        deleteData(item, event) {
            let self = this;
            debugger;
        }

        closeDialog(item, event) { nts.uk.ui.windows.close(); }
    }

    class Model {
        processingNo: KnockoutObservable<number>;
        processingYm: KnockoutObservable<number>;

    }

    interface ITableRowItem {
        index: number;
        label: string;
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
        index: KnockoutObservable<number> = ko.observable(0);
        label: KnockoutObservable<string> = ko.observable('');
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
            self.index(param.index);
            self.label(param.label);
            self.sel001(param.sel001);
            self.sel002(param.sel002);

            self.inp003(param.inp003);
            self.inp004(param.inp004);
            self.inp005(param.inp005);
            self.inp006(param.inp006);
            self.inp007(param.inp007);
            self.inp008(param.inp008);
            self.inp009(param.inp009);
            self.inp010(param.inp010);

            self.sel002Data(param.sel002Data);

            self.sel002.subscribe(function(v) {
                if (v) {
                    let currentSel002 = _.find(self.sel002Data(), function(item) { return item.index == v; });
                    if (currentSel002) {
                        self.sel002L(currentSel002.value["getDayJP"]());
                    }
                }
            });
            self.sel002.valueHasMutated();
        }

        toggleCalendar(item, event): void {
            $(event.currentTarget).parent('div').find('input[type=text]').trigger('click');
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
}