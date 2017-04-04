module qmm005.a {
    export class ViewModel {
        items: KnockoutObservableArray<TableRowItem>;
        constructor() {
            let self = this;
            self.items = ko.observableArray([]);
            self.start();
        }

        start() {
            let self = this;
            let _records: Array<TableRowItem> = [];
            /// Service trả về một mảng 2 chiều gồm 2 bảng paydayProcessing và payDay
            services.getData().done(function(resp: Array<any>) {
                /// Lặp từng dòng, số dòng cố định là 5 được
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
                    _row.parent = self;
                    let item = _.find(resp, function(o) { return o && o[0] && o[0].processingNo == index; });
                    if (item) {
                        _row.index(item[0].processingNo);
                        _row.label(item[0].processingName);
                        let _sel001Data: Array<common.SelectItem> = [];
                        let _sel002Data: Array<common.SelectItem> = [];
                        let _sel004Data: Array<common.SelectItem> = [];
                        let _sel005Data: Array<common.SelectItem> = [];
                        let cym = nts.uk.time.parseYearMonth(item[0].currentProcessingYm);
                        let bcym = nts.uk.time.parseYearMonth(item[0].bcurrentProcessingYm);
                        let cspd = 0, bcspdy = 0, cspdm = 0;
                        if (item[1]) {
                            var payDays = _.orderBy(item[1], ['processingYm'], ['desc']);
                            for (let j = 0; j < payDays.length; j++) {
                                let rec = payDays[j] as PaydayDto,
                                    payDate: Date = new Date(rec.payDate),
                                    month: number = payDate.getMonth() + 1,
                                    stdDate: Date = new Date(rec.stdDate),
                                    label: string = nts.uk.time.formatDate(payDate, "yyyy/MM/dd") + '(' + payDate['getDayJP']() + ')|' + nts.uk.time.formatDate(stdDate, "yyyy/MM/dd");

                                // Trường hợp là lương
                                if (rec.payBonusAtr === 0 && rec.sparePayAtr === 0) {
                                    var ym = nts.uk.time.parseYearMonth(rec.processingYm);
                                    if (ym.success) {
                                        _sel001Data.push(new common.SelectItem({ index: ym.year, label: ym.year.toString() }));
                                    }
                                    _sel002Data.push(new common.SelectItem({ index: month, label: label }));

                                    if (payDate.getFullYear() === cym.year && month === cym.month) {
                                        cspd = month;
                                    }
                                }

                                // Chưa thấy cập nhật trường hợp dữ liệu của thưởng?
                                if (rec.payBonusAtr === 1 && rec.sparePayAtr === 0) {
                                    let pym = nts.uk.time.parseYearMonth(rec.processingYm);
                                    if (pym.success) {
                                        _sel004Data.push(new common.SelectItem({ index: pym.year, label: pym.year.toString() }));
                                        _sel005Data.push(new common.SelectItem({ index: pym.month, label: pym.month.toString() + '月' }));
                                    }
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

                        // Năm được là năm hiện tại
                        _row.sel001(cym.year); // năm xử lý
                        _row.sel002(cspd); // Năm tháng của năm xử lý
                        _row.sel003(item[0].bonusAtr == 1 ? true : false); // Năm có thưởng hay không?
                        _row.sel004(bcym.year); // Thường vào năm nào (khác năm có thưởng = năm xử lý), VD: Năm 2017 có thưởng nhưng năm 2018 mới nhận thưởng
                        _row.sel005(bcym.month); //Thưởng vào tháng nào.
                    }
                    _records.push(_row);
                }
                self.items(_records);
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
            location.href = "../../../qmp/005/b/index.xhtml";
        }

        btn003Click(item, event): void {

        }

        btn004Click(item, event): void {

        }

        btn005Click(item, event): void {

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
        parent: ViewModel;
        index: KnockoutObservable<number>;
        label: KnockoutObservable<string>;
        sel001: KnockoutObservable<number>;
        sel002: KnockoutObservable<number>;
        sel003: KnockoutObservable<boolean>;
        sel004: KnockoutObservable<number>;
        sel005: KnockoutObservable<number>;

        sel001Data: KnockoutObservableArray<common.SelectItem>;
        sel002Data: KnockoutObservableArray<common.SelectItem>;
        sel004Data: KnockoutObservableArray<common.SelectItem>;
        sel005Data: KnockoutObservableArray<common.SelectItem>;

        constructor(param: ITableRowItem) {
            this.index = ko.observable(param.index);
            this.label = ko.observable(param.label);

            this.sel001Data = ko.observableArray(param.sel001Data);
            if (param.sel001Data[0])
                this.sel001 = ko.observable(param.sel001Data[0].index);

            this.sel002Data = ko.observableArray(param.sel002Data);
            if (param.sel002Data[0])
                this.sel002 = ko.observable(param.sel002Data[0].index);

            this.sel003 = ko.observable(param.sel003);

            this.sel004Data = ko.observableArray(param.sel004Data);
            if (param.sel004Data[0])
                this.sel004 = ko.observable(param.sel004Data[0].index);

            this.sel005Data = ko.observableArray(param.sel005Data);
            if (param.sel005Data[0])
                this.sel005 = ko.observable(param.sel005Data[0].index);
        }

        enable(): boolean {
            return this.label() != '';
        }

        showModalDialogB(item, event): void {
            let self = this;
            nts.uk.ui.windows.setShared('dataRow', item);
            nts.uk.ui.windows.sub.modal("../b/index.xhtml", { width: 1020, height: 730, title: '支払日の設定' })
                .onClosed(function() { self.parent.start(); });
        }

        showModalDialogC(item, event): void {
            let self = this;
            nts.uk.ui.windows.setShared('dataRow', item);
            nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 800, height: 350, title: '処理区分の追加' })
                .onClosed(function() { self.parent.start(); });
        }

        showModalDialogD(item, event): void {
            let self = this;
            nts.uk.ui.windows.setShared('dataRow', item);
            nts.uk.ui.windows.sub.modal("../d/index.xhtml", { width: 800, height: 370, title: '処理区分の編集' })
                .onClosed(function() { self.parent.start(); });
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