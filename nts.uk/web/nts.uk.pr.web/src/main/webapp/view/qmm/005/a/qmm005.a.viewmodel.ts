/// <reference path="../qmm005.ts"/>
/// <reference path="qmm005.a.start.ts" />
module qmm005.a {
    export class ViewModel {
        items: KnockoutObservableArray<TableRowItem> = ko.observableArray([]);
        dirty: nts.uk.ui.DirtyChecker = new nts.uk.ui.DirtyChecker(this.items);

        constructor() {
            let self = this;
            self.start();
        }

        start() {
            let self = this;
            let _records: Array<TableRowItem> = [];
            /// get data from server
            services.getData().done(function(resp: Array<any>) {
                /// repeat row (5 records paydayProcessing)
                for (let i in resp) {
                    let index = parseInt(i) + 1;
                    let _row = new TableRowItem({
                        index: index,
                        label: "",
                        sel001Data: [new common.SelectItem({ index: -1, label: "" })],
                        sel002Data: [new common.SelectItem({ index: -1, label: "" })],
                        sel003: true,
                        sel004Data: [new common.SelectItem({ index: -1, label: "" })],
                        sel005Data: [new common.SelectItem({ index: -1, label: "" })]
                    });

                    let item = _.find(resp, function(item) { return item && item.paydayProcessingDto && item.paydayProcessingDto.processingNo == index; });

                    if (item) {
                        _row.index(item.paydayProcessingDto.processingNo);
                        _row.label(item.paydayProcessingDto.processingName);
                        let _sel001Data: Array<common.SelectItem> = [];
                        let _sel002Data: Array<common.SelectItem> = [];
                        let _sel004Data: Array<common.SelectItem> = [];
                        let _sel005Data: Array<common.SelectItem> = [];
                        let cym = nts.uk.time.parseYearMonth(item.paydayProcessingDto.currentProcessingYm);
                        let bcym = nts.uk.time.parseYearMonth(item.paydayProcessingDto.bcurrentProcessingYm);
                        let cspd = 0, bcspdy = 0, cspdm = 0;

                        // Salary
                        if (item.paydayDtos) {
                            let payDays = _.orderBy(item.paydayDtos, ['processingYm'], ['desc']);
                            for (let j = 0; j < payDays.length; j++) {
                                let rec = payDays[j] as PaydayDto,
                                    payDate: Date = new Date(rec.payDate),
                                    month: number = payDate.getMonth() + 1,
                                    stdDate: Date = new Date(rec.stdDate),
                                    label: string = nts.uk.time.formatDate(payDate, "yyyy/MM/dd") + '(' + payDate['getDayJP']() + ')|' + nts.uk.time.formatDate(stdDate, "yyyy/MM/dd"),
                                    ym = nts.uk.time.parseYearMonth(rec.processingYm);

                                if (ym.success) {
                                    _sel001Data.push(new common.SelectItem({ index: ym.year, label: ym.year.toString() }));
                                }
                                _sel002Data.push(new common.SelectItem({ index: month, label: label }));

                                if (payDate.getFullYear() === cym.year && month === cym.month) {
                                    cspd = month;
                                }
                            }
                        }

                        // Bonus
                        if (item.paydayBonusDtos) {
                            let payDays = _.orderBy(item.paydayBonusDtos, ['processingYm'], ['desc']);
                            for (let j = 0; j < payDays.length; j++) {
                                let rec = payDays[j] as PaydayDto;
                                let pym = nts.uk.time.parseYearMonth(rec.processingYm);
                                if (pym.success) {
                                    _sel004Data.push(new common.SelectItem({ index: pym.year, label: pym.year.toString() }));
                                    _sel005Data.push(new common.SelectItem({ index: pym.month, label: pym.month.toString() + '月' }));
                                }
                            }
                        }


                        // Filter duplicate data option
                        _sel001Data = _.uniqWith(_sel001Data, _.isEqual);
                        _row.sel001Data(_sel001Data);

                        _sel002Data = _.uniqWith(_sel002Data, _.isEqual);
                        _row.sel002Data(_sel002Data);

                        _sel004Data = _.uniqWith(_sel004Data, _.isEqual);
                        _row.sel004Data(_sel004Data);

                        _sel005Data = _.uniqWith(_sel005Data, _.isEqual);
                        _row.sel005Data(_sel005Data);

                        // Current processing year
                        _row.sel001(cym.year); // processing year
                        _row.sel002(cspd); // processing month
                        _row.sel003(item.paydayProcessingDto.bonusAtr == 1 ? true : false); // Year has bonus?
                        _row.sel004(bcym.year); // bonus in year
                        _row.sel005(bcym.month); // bonus in month
                    }
                    _records.push(_row);
                }
                self.items(_records);

                // Reset dirty on start success
                self.dirty.reset();
            });
        }

        btn001Click(item, event) {
            let self = this, items = self.items();
            var data: Array<PaydayProcessingDto> = [];
            for (var i = 0, row; row = items[i] as TableRowItem; i++) {
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

            services.updatData({ paydayProcessings: data }).done(function(resp) {
                self.start();
            });
        }

        // Navigate to qmp/005/b/index.xhtml
        btn002Click(item, event): void {
            let self = this;
            if (self.dirty.isDirty()) {
                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function() {
                    location.href = "../../../qmp/005/b/index.xhtml";
                });
            } else {
                location.href = "../../../qmp/005/b/index.xhtml";
            }
        }

        btn003Click(item, event): void {
            let self = this;
            if (self.dirty.isDirty()) {
                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function() {
                    location.href = "../../../qmp/005/b/index.xhtml";
                });
            } else {
                location.href = "../../../qmp/005/b/index.xhtml";
            }
        }

        btn004Click(item, event): void {
            let self = this;
            if (self.dirty.isDirty()) {
                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function() {
                    location.href = "../../../qmp/005/b/index.xhtml";
                });
            } else {
                location.href = "../../../qmp/005/b/index.xhtml";
            }
        }

        btn005Click(item, event): void {
            let self = this;
            if (self.dirty.isDirty()) {
                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?").ifYes(function() {
                    location.href = "../../../qmp/005/b/index.xhtml";
                });
            } else {
                location.href = "../../../qmp/005/b/index.xhtml";
            }
        }
    }

    interface ITableRowItem {
        index: number;
        label: string;
        sel001Data: Array<common.SelectItem>;
        sel002Data: Array<common.SelectItem>;
        sel003: boolean;
        sel004Data: Array<common.SelectItem>;
        sel005Data: Array<common.SelectItem>;
    }

    class TableRowItem {
        index: KnockoutObservable<number> = ko.observable(0);
        label: KnockoutObservable<string> = ko.observable('');
        sel001: KnockoutObservable<number> = ko.observable(0);
        sel002: KnockoutObservable<number> = ko.observable(0);
        sel003: KnockoutObservable<boolean> = ko.observable(false);
        sel004: KnockoutObservable<number> = ko.observable(0);
        sel005: KnockoutObservable<number> = ko.observable(0);

        sel001Data: KnockoutObservableArray<common.SelectItem> = ko.observableArray([]);
        sel002Data: KnockoutObservableArray<common.SelectItem> = ko.observableArray([]);
        sel004Data: KnockoutObservableArray<common.SelectItem> = ko.observableArray([]);
        sel005Data: KnockoutObservableArray<common.SelectItem> = ko.observableArray([]);

        constructor(param: ITableRowItem) {
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

        enable(): boolean {
            return this.label() != '';
        }

        showModalDialogB(item, event): void {
            let self = this;
            nts.uk.ui.windows.setShared('dataRow', item);
            nts.uk.ui.windows.sub.modal("../b/index.xhtml", { width: 1020, height: 730, title: '支払日の設定', dialogClass: "no-close" })
                .onClosed(function() {
                    __viewContext.viewModel.start();
                });
        }

        showModalDialogC(item, event): void {
            let self = this;
            nts.uk.ui.windows.setShared('dataRow', item);
            nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 800, height: 350, title: '処理区分の追加', dialogClass: "no-close" })
                .onClosed(function() {
                    __viewContext.viewModel.start();
                });
        }

        showModalDialogD(item, event): void {
            let self = this;
            nts.uk.ui.windows.setShared('dataRow', item);
            nts.uk.ui.windows.sub.modal("../d/index.xhtml", { width: 800, height: 370, title: '処理区分の編集', dialogClass: "no-close" })
                .onClosed(function() {
                    __viewContext.viewModel.start();
                });
        }
    }

    interface PaydayDto {
        companyCode: string;
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

    interface PaydayProcessingDto {
        processingNo: number,
        processingName: string,
        dispSet: number,
        currentProcessingYm: number,
        bonusAtr: number,
        bcurrentProcessingYm: number
    }
}