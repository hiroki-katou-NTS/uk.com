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
            /// paydayProccessing = resp[][0];
            /// payDay = resp[][1];
            services.getData().done(function(resp: Array<any>) {
                /// Lặp từng dòng, số dòng cố định là 5 được
                for (let i in resp) {
                    let index = parseInt(i) + 1;
                    let _row = new TableRowItem(
                        index,
                        "",
                        [{ index: -1, label: "" }],
                        [{ index: -1, label: "" }],
                        true,
                        [{ index: -1, label: "" }],
                        [{ index: -1, label: "" }]
                    );
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
                            for (let i in payDays) {
                                let rec: any = payDays[i],
                                    index: number = parseInt(i) + 1,
                                    payDate: Date = new Date(rec.payDate),
                                    stdDate: Date = new Date(rec.stdDate),
                                    label: string = nts.uk.time.formatDate(payDate, "yyyy/MM/dd") + '(' + payDate['getDayJP']() + ')|' + nts.uk.time.formatDate(stdDate, "yyyy/MM/dd");

                                if (!rec.payBonusAtr && !rec.sparePayAtr) {
                                    var ym = nts.uk.time.parseYearMonth(rec.processingYm);
                                    if (ym.success) {
                                        _sel001Data.push(new SelectItem(ym.year, ym.year.toString()));
                                    }
                                }

                                if (payDate.getFullYear() == cym.year && payDate.getMonth() == cym.month - 1) {
                                    cspd = index;
                                }
                                _sel002Data.push(new common.SelectItem({ index: index, label: label }));

                                debugger;
                                let pym = nts.uk.time.parseYearMonth(rec.processingYm);
                                if (pym.success) {
                                    _sel004Data.push(new SelectItem(pym.year, pym.year.toString()));
                                    _sel005Data.push(new SelectItem(pym.month, pym.month.toString()));
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
                        _row.sel001(cym.year);
                        _row.sel002(cspd);
                        _row.sel003(item[0].bonusAtr);
                        _row.sel004(bcym.year);
                        _row.sel005(bcym.month);

                    }
                    _records.push(_row);
                }
                self.items(_records);
            });
        }

        btn001Click(item, event) {

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

    class TableRowItem {
        parent: ViewModel;
        index: KnockoutObservable<number>;
        label: KnockoutObservable<string>;
        sel001: KnockoutObservable<number>;
        sel002: KnockoutObservable<number>;
        sel003: KnockoutObservable<boolean>;
        sel004: KnockoutObservable<number>;
        sel005: KnockoutObservable<number>;

        sel001Data: KnockoutObservableArray<SelectItem>;
        sel002Data: KnockoutObservableArray<SelectItem>;
        sel004Data: KnockoutObservableArray<SelectItem>;
        sel005Data: KnockoutObservableArray<SelectItem>;

        constructor(index: number, label: string, sel001: Array<SelectItem>, sel002: Array<SelectItem>, sel003: boolean, sel004: Array<SelectItem>, sel005: Array<SelectItem>) {
            this.index = ko.observable(index);
            this.label = ko.observable(label);

            this.sel001Data = ko.observableArray(sel001);
            if (sel001[0])
                this.sel001 = ko.observable(sel001[0].index);

            this.sel002Data = ko.observableArray(sel002);
            if (sel002[0])
                this.sel002 = ko.observable(sel002[0].index);

            this.sel003 = ko.observable(sel003);

            this.sel004Data = ko.observableArray(sel004);
            if (sel004[0])
                this.sel004 = ko.observable(sel004[0].index);

            this.sel005Data = ko.observableArray(sel005);
            if (sel005[0])
                this.sel005 = ko.observable(sel005[0].index);
        }

        enable(): boolean {
            return this.label() != '';
        }

        showModalDialogB(item, event): void {
            let self = this;
            nts.uk.ui.windows.setShared('dataRow', item);
            nts.uk.ui.windows.sub.modal("../b/index.xhtml", { width: 730, height: 645, title: '支払日の設定' })
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

    class SelectItem {
        constructor(public index: number, public label: string) {
        }
    }
}