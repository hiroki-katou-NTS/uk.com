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
                    console.log(resp);
                    let lst001Data: Array<common.SelectItem> = [],
                        dataRow = nts.uk.ui.windows.getShared('dataRow');
                    for (let i: number = 0, rec: PaydayDto; rec = resp[i]; i++) {
                        let year = rec.processingYm["getYearInYm"](),
                            yearIJE = year + "(" + year["yearInJapanEmpire"]() + ")";
                        if (!_.find(lst001Data, function(item) { return item.value == year; })) {
                            lst001Data.push(new common.SelectItem({ index: i + 1, label: yearIJE, value: year, selected: year == dataRow.sel001() }));
                        }
                    }
                    self.lst001Data(lst001Data);

                    /// Select tạm ra một object (kiband có sửa phần này)
                    let selectRow = _.find(lst001Data, function(item) { return item.selected; });
                    if (selectRow) {
                        self.lst001(selectRow.index);
                    }
                }
            });

            let items: Array<TableRowItem> = [];
            for (let i = 1; i <= 12; i++) {
                items.push(new TableRowItem({
                    index: i,
                    label: '',
                    sel001: i % 2 == 0 ? true : false,
                    sel002Data: [new common.SelectItem({ index: -1, label: "" })],
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
            self.lst002Data(items);
        }

        toggleColumns(item, event): void {
            $('.toggle').toggleClass('hidden');
            $(event.currentTarget).parent('td').toggleClass('checkbox-cols');
            ($(event.currentTarget).text() == "-" && $(event.currentTarget).text('+')) || $(event.currentTarget).text('-');
            if (!$('.toggle').hasClass('hidden')) {
                nts.uk.ui.windows.getSelf().setWidth(1515);
            } else {
                nts.uk.ui.windows.getSelf().setWidth(1070);
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
        sel002Data: Array<common.SelectItem>;
        inp003: Date;
        inp004: Date;
        inp005: Date;
        inp006: Date;
        inp007: Date;
        inp008: Date;
        inp009: Date;
        inp010: number;
    }

    class TableRowItem {
        index: KnockoutObservable<number>;
        label: KnockoutObservable<string>;
        sel001: KnockoutObservable<boolean>;
        sel002: KnockoutObservable<number>;
        inp003: KnockoutObservable<Date>;
        inp004: KnockoutObservable<Date>;
        inp005: KnockoutObservable<Date>;
        inp006: KnockoutObservable<Date>;
        inp007: KnockoutObservable<Date>;
        inp008: KnockoutObservable<Date>;
        inp009: KnockoutObservable<Date>;
        inp010: KnockoutObservable<number>;

        sel002Data: KnockoutObservableArray<common.SelectItem>;

        constructor(param: ITableRowItem) {
            let self = this;
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