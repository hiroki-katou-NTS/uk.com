module nts.uk.pr.view.qmm019.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import modal = nts.uk.ui.windows.sub.modal
    import StatementLayout = nts.uk.pr.view.qmm019.share.model.StatementLayout;
    import IStatementLayout = nts.uk.pr.view.qmm019.share.model.IStatementLayout;
    import IStatementLayoutHistData = nts.uk.pr.view.qmm019.share.model.IStatementLayoutHistData;
    import IStatementLayoutSet = nts.uk.pr.view.qmm019.share.model.IStatementLayoutSet;
    import ILineByLineSetting = nts.uk.pr.view.qmm019.share.model.ILineByLineSetting;
    import ISettingByItem = nts.uk.pr.view.qmm019.share.model.ISettingByItem;
    import IDeductionItemDetail = nts.uk.pr.view.qmm019.share.model.IDeductionItemDetail;
    import IPaymentItemDetail = nts.uk.pr.view.qmm019.share.model.IPaymentItemDetail;
    import ISettingByCtg = nts.uk.pr.view.qmm019.share.model.ISettingByCtg;
    import YearMonthHistory = nts.uk.pr.view.qmm019.share.model.YearMonthHistory;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import _viewModel = nts.uk.ui._viewModel;
    import StatementPrintAtr = nts.uk.pr.view.qmm019.share.model.StatementPrintAtr;
    import CategoryAtr = nts.uk.pr.view.qmm019.share.model.CategoryAtr;

    export class ScreenModel {

        statementLayoutList: KnockoutObservableArray<StatementLayout>;
        currentHistoryId: KnockoutObservable<string>;
        currentStatementLayoutCode: KnockoutObservable<string>;
        statementLayoutHistData: KnockoutObservable<StatementLayoutHistData>;

        constructor() {
            let self = this;

            self.statementLayoutList = ko.observableArray([]);
            self.currentHistoryId = ko.observable(null);
            self.currentStatementLayoutCode = ko.observable(null);
            self.statementLayoutHistData = ko.observable(null);

            self.currentHistoryId.subscribe(x => {
                if(x && (x != "")) {
                    let data: YearMonthHistory;

                    for(let statementLayout: StatementLayout of self.statementLayoutList()) {
                        let mapData = _.filter(statementLayout.history, function(o) {
                            return x == o.historyId;
                        });

                        if(mapData.length > 0) data = mapData[0];
                    }

                    if(data) {
                        self.currentStatementLayoutCode(data.code);
                        self.loadLayoutHistData(data.code, data.historyId);
                    } else {
                        self.statementLayoutHistData(new StatementLayoutHistData(null));
                    }
                } else {
                    self.statementLayoutHistData(new StatementLayoutHistData(null));
                }

                nts.uk.ui.errors.clearAll();
            });
        }

        loadLayoutHistData(code: string, histId: string) {
            let self = this;
            block.invisible();

            service.getStatementLayoutHistData(code, histId).done(function(data: IStatementLayoutHistData) {
                if(data) {
                    self.statementLayoutHistData(new StatementLayoutHistData(data));
                } else {
                    self.statementLayoutHistData(new StatementLayoutHistData(null));
                }

                block.clear();
            }).fail(() => {
                self.statementLayoutHistData(new StatementLayoutHistData(null));

                block.clear();
            });
        }

        loadListData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();

            service.getAllStatementLayoutAndHist().done(function(data: Array<IStatementLayout>) {
                let statementLayoutList = data.map(x => new StatementLayout(x));
                self.statementLayoutList(statementLayoutList);

                // ????????????????????
                if(statementLayoutList.length <= 0) {
                    self.currentHistoryId(null);
                    self.statementLayoutHistData(new StatementLayoutHistData(null));
                } else {
                    self.currentHistoryId(null);
                    self.statementLayoutHistData(new StatementLayoutHistData(null));
                }

                block.clear();
                dfd.resolve();
            }).fail(() => {
                block.clear();
                dfd.resolve();
            });

            return dfd.promise();
        }

        public create(): void {
            let self = this;

            nts.uk.ui.windows.sub.modal('../h/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_H_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    //TODO init create mode
                }

                //TODO $("#C3_8").focus();
            });
        }

        public registered(): void {

        }

        public outputExcel(): void {
            modal("/view/qmm/019/d/index.xhtml");
        }

        public addHistory(): void {
            let self = this;

            setShared("QMM019_A_TO_B_PARAMS", {code: self.currentStatementLayoutCode()});
            nts.uk.ui.windows.sub.modal('../b/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_B_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    let histID = params.histID;
                    self.loadListData();
                    self.currentHistoryId(histID);
                    //TODO init create mode
                }

                //TODO $("#C3_8").focus();
            });
        }

        public editHistory(): void {
            let self = this;

            setShared("QMM019_A_TO_C_PARAMS", {code: self.currentStatementLayoutCode(), histId: self.currentHistoryId()});
            nts.uk.ui.windows.sub.modal('../c/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_C_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    //TODO init create mode
                }

                //TODO $("#C3_8").focus();
            });
        }

        public modifyLog(): void {
            modal("/view/qmm/019/e/index.xhtml");
        }
    }

    class StatementLayoutHistData {
        statementCode: string;
        statementName: KnockoutObservable<string>;
        historyId: string;
        startMonth: KnockoutObservable<number>;
        endMonth: KnockoutObservable<number>;
        statementLayoutSet: KnockoutObservable<StatementLayoutSet>;

        // mode of screen
        checkCreate: KnockoutObservable<boolean>;

        // not submit
        startMonthText: KnockoutObservable<string>;
        endMonthText: KnockoutObservable<string>;

        constructor(data: IStatementLayoutHistData) {
            let self = this;

            if(data) {
                self.statementCode = data.statementCode;
                self.statementName = ko.observable(data.statementName);
                self.historyId = data.historyId;
                self.startMonth = ko.observable(data.startMonth);
                self.endMonth = ko.observable(data.endMonth);
                self.statementLayoutSet = ko.observable(new StatementLayoutSet(data.statementLayoutSet));

                self.checkCreate = ko.observable(false);
                self.startMonthText = ko.observable(nts.uk.time.parseYearMonth(data.startMonth).format());
                self.endMonthText = ko.observable(nts.uk.time.parseYearMonth(data.endMonth).format());
            } else {
                self.statementCode = null;
                self.statementName = ko.observable(null);
                self.historyId = null;
                self.startMonth = ko.observable(null);
                self.endMonth = ko.observable(null);
                self.statementLayoutSet = ko.observable(new StatementLayoutSet(null));

                self.checkCreate = ko.observable(true);
                self.startMonthText = ko.observable(null);
                self.endMonthText = ko.observable(null);
            }

            nts.uk.ui.errors.clearAll();
        }
    }

    class StatementLayoutSet {
        histId: string;
        layoutPattern: KnockoutObservable<number>;
        listSettingByCtg: KnockoutObservableArray<SettingByCtg>;

        constructor(data: IStatementLayoutSet) {
            let self = this;

            if(data) {
                self.histId = data.histId;
                self.layoutPattern = ko.observable(data.layoutPattern);
                self.listSettingByCtg = ko.observableArray(data.listSettingByCtg.map(i => new SettingByCtg(i, self)));
            } else {
                self.histId = null;
                self.layoutPattern = ko.observable(null);
                self.listSettingByCtg = ko.observableArray([]);
            }
        }
    }

    class SettingByCtg {
        ctgAtr: number;
        listLineByLineSet: KnockoutObservableArray<LineByLineSetting>;
        parent: StatementLayoutSet;

        constructor(data: ISettingByCtg, parent: StatementLayoutSet) {
            let self = this;

            if(data) {
                self.ctgAtr = data.ctgAtr;
                self.listLineByLineSet = ko.observableArray(data.listLineByLineSet.map(i => new LineByLineSetting(i, null, self)));
            } else {
                self.ctgAtr = null;
                self.listLineByLineSet = ko.observableArray([]);
            }

            self.parent = parent;
        }

        public addLine(): void {
            let self = this;
            let totalLine = 0;
            let printLineInCtg = 0;
            let noPrintLineInCtg = 0;

            for(let settingByCtg: SettingByCtg of self.parent.listSettingByCtg()) {
                totalLine += settingByCtg.listLineByLineSet().length;
            }

            for(let lineByLineSetting: LineByLineSetting of self.listLineByLineSet()) {
                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                    printLineInCtg++;
                } else {
                    noPrintLineInCtg++;
                }
            }

            setShared("QMM019_A_TO_J_PARAMS", {
                layoutPattern: self.parent.layoutPattern(),
                totalLine: totalLine,
                ctgAtr: self.ctgAtr,
                printLineInCtg: printLineInCtg,
                noPrintLineInCtg: noPrintLineInCtg
            });

            nts.uk.ui.windows.sub.modal('../j/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_J_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    self.listLineByLineSet.push(new LineByLineSetting(null, params.printSet, self));
                }

                //TODO $("#C3_8").focus();
            });
        }
    }

    class LineByLineSetting {
        printSet: KnockoutObservable<number>;
        lineNumber: KnockoutObservable<number>;
        listSetByItem: KnockoutObservableArray<SettingByItem>;
        parent: SettingByCtg;

        constructor(data: ILineByLineSetting, newPrintSet: number, parent: SettingByCtg) {
            let self = this;
            let listFullItem: Array<ISettingByItem>;

            if(data) {
                self.printSet = ko.observable(data.printSet);
                self.lineNumber = ko.observable(data.lineNumber);
                listFullItem = data.listSetByItem;
            } else {
                self.printSet = ko.observable(newPrintSet);
                self.lineNumber = ko.observable(null);
                listFullItem = [];
            }

            self.parent = parent;

            self.listSetByItem = ko.observableArray([]);
            for(let i = 1; i <= 9; i++) {
                let itemInPosition:Array<ISettingByItem> = listFullItem.filter(item => item.itemPosition == i);
                self.listSetByItem.push(itemInPosition.length > 0 ? new SettingByItem(itemInPosition[0], self) : new SettingByItem(null, self));
            }
        }

        public editLine(): void {
            let self = this;
            let totalLine = 0;
            let printLineInCtg = 0;
            let noPrintLineInCtg = 0;

            for(let settingByCtg: SettingByCtg of self.parent.parent.listSettingByCtg()) {
                totalLine += settingByCtg.listLineByLineSet().length;
            }

            for(let lineByLineSetting: LineByLineSetting of self.parent.listLineByLineSet()) {
                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                    printLineInCtg++;
                } else {
                    noPrintLineInCtg++;
                }
            }

            setShared("QMM019_A_TO_K_PARAMS", {
                layoutPattern: self.parent.parent.layoutPattern(),
                totalLine: totalLine,
                ctgAtr: self.parent.ctgAtr,
                printLineInCtg: printLineInCtg,
                noPrintLineInCtg: noPrintLineInCtg,
                printSet: self.printSet()
            });

            nts.uk.ui.windows.sub.modal('../k/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_K_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    if((params.printSet == StatementPrintAtr.PRINT) || (params.printSet == StatementPrintAtr.DO_NOT_PRINT)) {
                        self.printSet(params.printSet);
                    } else {
                        self.parent.listLineByLineSet.remove(self);
                    }
                }

                //TODO $("#C3_8").focus();
            });
        }
    }

    class SettingByItem {
        itemPosition: KnockoutObservable<number>;
        itemId: KnockoutObservable<string>;
        shortName: KnockoutObservable<string>;
        paymentItemDetailSet: IPaymentItemDetail;
        deductionItemDetailSet: IDeductionItemDetail;
        parent: LineByLineSetting;

        constructor(data: ISettingByItem, parent: LineByLineSetting) {
            let self = this;

            if(data) {
                self.itemPosition = ko.observable(data.itemPosition);
                self.itemId = ko.observable(data.itemId);
                self.shortName = ko.observable(data.shortName);
                self.paymentItemDetailSet = data.paymentItemDetailSet;
                self.deductionItemDetailSet = data.deductionItemDetailSet;
            } else {
                self.itemPosition = ko.observable("+");
                self.itemId = ko.observable(null);
                self.shortName = ko.observable(null);
                self.paymentItemDetailSet = null;
                self.deductionItemDetailSet = null;
            }

            self.parent = parent;
        }

        public openSetting(): void {
            let self = this;

            let listItemSetting: Array<string> = [];
            let listLineInCtg = self.parent.parent.listLineByLineSet();

            for (let lineByLine: LineByLineSetting of listLineInCtg) {
                for(let item: SettingByItem of lineByLine.listSetByItem) {
                    if (item.itemId() != null) listItemSetting.push(item.itemId());
                }
            }

            switch (self.parent.parent.ctgAtr) {
                case CategoryAtr.PAYMENT_ITEM: {
                    setShared("QMM019_A_TO_D_PARAMS", {
                        listItemSetting: listItemSetting,
                        itemId: self.itemId(),
                        detail: self.paymentItemDetailSet
                    });

                    nts.uk.ui.windows.sub.modal('../d/index.xhtml').onClosed(() => {

                    });
                    break;
                }
                case CategoryAtr.DEDUCTION_ITEM: {
                    setShared("QMM019_E_TO_D_PARAMS", {
                        listItemSetting: listItemSetting,
                        itemId: self.itemId(),
                        detail: self.deductionItemDetailSet
                    });

                    nts.uk.ui.windows.sub.modal('../e/index.xhtml').onClosed(() => {

                    });
                    break;
                }
                case CategoryAtr.ATTEND_ITEM: {
                    nts.uk.ui.windows.sub.modal('../f/index.xhtml').onClosed(() => {

                    });
                    break;
                }
                case CategoryAtr.REPORT_ITEM: {
                    setShared("QMM019_E_TO_G_PARAMS", {
                        listItemSetting: listItemSetting,
                        itemId: self.itemId()
                    });
                    nts.uk.ui.windows.sub.modal('../g/index.xhtml').onClosed(() => {

                    });
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }
}