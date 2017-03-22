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
            services.getData().done(function(resp: Array<any>) {
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
                        if (item[1]) {
                            var payDays = _.orderBy(item[1], ['processingNo'], ['asc']);
                            for (let i in payDays) {
                                let rec: any = payDays[i];
                                if (rec.processingNo == index && !rec.payBonusAtr && !rec.sparePayAtr) {
                                    var ym = nts.uk.time.parseYearMonth(rec.processingYm);
                                    if (ym.success) {
                                        _sel001Data.push(new SelectItem(ym.year, ym.year + ''));
                                    }
                                }
                            }
                        }
                        _sel001Data = _.orderBy(_sel001Data, ['index'], ['desc']);
                        _row.sel001Data(_sel001Data);
                        
                        // Năm được chọn???
                        _row.sel001(_sel001Data[0].index);
                        
                        let _sel002Data: Array<common.SelectItem> = [];

                        if (item[1]) {
                            for (let i in item[1]) {
                                let index: number = parseInt(i) + 1,
                                    rec: any = item[1][i],
                                    payDate: Date = new Date(rec.payDate),
                                    stdDate: Date = new Date(rec.stdDate),
                                    label: string = nts.uk.time.formatDate(payDate, "yyyy/MM/dd") + '(' + payDate['getDayJP']() + ')|' + nts.uk.time.formatDate(stdDate, "yyyy/MM/dd");
                                _sel002Data.push(new common.SelectItem({ index: index, label: label }));
                            }

                            if (_sel002Data.length) {
                                _row.sel002Data(_sel002Data);
                                _row.sel002(_sel002Data[0].index);
                            }
                        }

                        _row.sel003(item[0].bonusAtr);

                        let bCurrentProcessingYm = nts.uk.time.parseYearMonth(item[0].bcurrentProcessingYm);
                        _row.sel004(bCurrentProcessingYm.year);

                        let _sel004Data = [{ index: bCurrentProcessingYm.year, label: '' + bCurrentProcessingYm.year }];
                        _row.sel004Data(_sel004Data);

                        _row.sel005(bCurrentProcessingYm.month);

                        let _sel005Data = [{ index: bCurrentProcessingYm.month, label: bCurrentProcessingYm.month + '月' }];
                        _row.sel005Data(_sel005Data);
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