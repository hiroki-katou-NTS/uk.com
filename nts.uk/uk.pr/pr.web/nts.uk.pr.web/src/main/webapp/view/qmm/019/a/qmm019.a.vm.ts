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

    export class ScreenModel {

        statementLayoutList: KnockoutObservableArray<StatementLayout>;
        currentHistoryId: KnockoutObservable<string>;
        currentStatementLayoutCode: KnockoutObservable<string>;
        statementLayoutHistData: KnockoutObservable<StatementLayoutHistData>;
        headers: any;
        columns: KnockoutObservableArray<any>;

        constructor() {
            let self = this;

            self.statementLayoutList = ko.observableArray([
                new StatementLayout({
                    statementCode: "1",
                    statementName: "1",
                    history: [
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "a"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "b"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "c"
                        },
                    ]
                }),
                new StatementLayout({
                    statementCode: "2",
                    statementName: "2",
                    history: [
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "d"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "e"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "f"
                        },
                    ]
                }),
                new StatementLayout({
                    statementCode: "3",
                    statementName: "3",
                    history: [
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "g"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "h"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "j"
                        },
                    ]
                })
            ]);

            self.currentHistoryId = ko.observable(null);
            self.currentStatementLayoutCode = ko.observable(null);
            self.statementLayoutHistData = ko.observable(null);
            self.headers = ko.observableArray(["Item Value Header","Item Text Header"]);

            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'nodeText', width: 100 }
            ]);

            self.currentHistoryId.subscribe(x => {
                if(x && (x != "")) {
                    let data: YearMonthHistory;

                    for(let statementLayout: StatementLayout in self.statementLayoutList()) {
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
        statementLayoutSet: StatementLayoutSet;

        // mode of screen
        checkCreate: KnockoutObservable<boolean>;

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
            } else {
                self.statementCode = null;
                self.statementName = ko.observable(null);
                self.historyId = null;
                self.startMonth = ko.observable(null);
                self.endMonth = ko.observable(null);
                self.statementLayoutSet = ko.observable(null);
                self.checkCreate = ko.observable(true);
            }

            nts.uk.ui.errors.clearAll();
        }
    }

    class StatementLayoutSet {
        histId: string;
        layoutPattern: KnockoutObservable<number>;
        listSettingByCtg: Array<SettingByCtg> ;

        constructor(data: IStatementLayoutSet) {
            let self = this;

            if(data) {
                self.histId = data.histId;
                self.layoutPattern = ko.observable(data.layoutPattern);
                self.listSettingByCtg = ko.observableArray(data.listSettingByCtg.map(i => new SettingByCtg(i)));
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

        constructor(data: ISettingByCtg) {
            let self = this;

            if(data) {
                self.ctgAtr = data.ctgAtr;
                self.listLineByLineSet = ko.observableArray(data.listLineByLineSet.map(i => new LineByLineSetting(i)));
            } else {
                self.ctgAtr = null;
                self.listLineByLineSet = ko.observableArray([]);
            }
        }
    }

    class LineByLineSetting {
        printSet: KnockoutObservable<number>;
        lineNumber: KnockoutObservable<number>;
        listSetByItem: KnockoutObservableArray<SettingByItem>;

        constructor(data: ILineByLineSetting) {
            let self = this;

            if(data) {
                self.printSet = ko.observable(data.printSet);
                self.lineNumber = ko.observable(data.lineNumber);
                self.listSetByItem = ko.observableArray(data.listSetByItem.map(i => new SettingByItem(i)));
            } else {
                self.printSet = ko.observable(null);
                self.lineNumber = ko.observable(null);
                self.listSetByItem = ko.observableArray([]);
            }
        }
    }

    class SettingByItem {
        itemPosition: KnockoutObservable<number>;
        itemId: KnockoutObservable<string>;
        shortName: KnockoutObservable<string>;
        paymentItemDetailSet: IPaymentItemDetail;
        deductionItemDetailSet: IDeductionItemDetail;

        constructor(data: ISettingByItem) {
            let self = this;

            if(data) {
                self.itemPosition = ko.observable(data.itemPosition);
                self.itemId = ko.observable(data.itemId);
                self.shortName = ko.observable(data.shortName);
                self.paymentItemDetailSet = data.paymentItemDetailSet;
                self.deductionItemDetailSet = data.deductionItemDetailSet;
            } else {
                self.itemPosition = ko.observable(null);
                self.itemId = ko.observable(null);
                self.shortName = ko.observable(null);
                self.paymentItemDetailSet = null;
                self.deductionItemDetailSet = null;
            }
        }
    }
}