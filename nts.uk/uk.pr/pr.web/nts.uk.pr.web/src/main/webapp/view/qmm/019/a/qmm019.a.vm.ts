module nts.uk.pr.view.qmm019.a.viewmodel {
    import block = nts.uk.ui.block;
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
    import StatementPrintAtr = nts.uk.pr.view.qmm019.share.model.StatementPrintAtr;
    import CategoryAtr = nts.uk.pr.view.qmm019.share.model.CategoryAtr;
    import IItemRangeSet = nts.uk.pr.view.qmm019.share.model.IItemRangeSet;
    import PaymentTotalObjAtr = nts.uk.pr.view.qmm019.share.model.PaymentTotalObjAtr;
    import PaymentCaclMethodAtr = nts.uk.pr.view.qmm019.share.model.PaymentCaclMethodAtr;
    import DeductionTotalObjAtr = nts.uk.pr.view.qmm019.share.model.DeductionTotalObjAtr
    import getLayoutPatternText = nts.uk.pr.view.qmm019.share.model.getLayoutPatternText;
    import getLayoutPatternContent = nts.uk.pr.view.qmm019.share.model.getLayoutPatternContent;
    import StatementLayoutPattern = nts.uk.pr.view.qmm019.share.model.StatementLayoutPattern;
    import getText = nts.uk.resource.getText;
    import IYearMonthHistory = nts.uk.pr.view.qmm019.share.model.IYearMonthHistory;

    export class ScreenModel {
        newHistId: string;

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
                if (x && (x != "")) {
                    if (self.statementLayoutHistData().checkCreate()) {
                        if (self.newHistId != x) {
                            self.newHistId = null;
                            self.loadListData();
                        } else {
                            return;
                        }
                    }

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
                        self.statementLayoutHistData(new StatementLayoutHistData(null, false));
                    }
                } else {
                    self.statementLayoutHistData(new StatementLayoutHistData(null, false));
                }

                nts.uk.ui.errors.clearAll();
            });
            if(self.isIE()){
                $("#A8_2").css({ "top": "4px" });
                $("#A7_2").css({ "top": "4px" });
            }
        }
        private isIE() {
            const match = navigator.userAgent.search(/(?:Edge|MSIE|Trident\/.*; rv:)/);
            let isIE = false;

            if (match !== -1) {
                isIE = true;
            }

            return isIE;
        }

        calculatePrintLine() {
            let self = this;
            let totalLines = 0;
            let printLines = 0;
            let noPrintLines = 0;
            let printLinesPayment = 0;
            let noPrintLinesPayment = 0;
            let printLinesDedu = 0;
            let noPrintLinesDedu = 0;
            let printLinesTime = 0;
            let noPrintLinesTime = 0;
            let printLinesReport = 0;
            let noPrintLinesReport = 0;

            switch (self.statementLayoutHistData().statementLayoutSet().layoutPattern()) {
                case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_ONE_PERSON:
                case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_TWO_PERSON:
                case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_THREE_PERSON:
                case StatementLayoutPattern.LASER_PRINT_A4_LANDSCAPE_TWO_PERSON:
                case StatementLayoutPattern.LASER_CRIMP_PORTRAIT_ONE_PERSON:
                    for(let settingByCtg: SettingByCtg of self.statementLayoutHistData().statementLayoutSet().listSettingByCtg()) {
                        totalLines += settingByCtg.listLineByLineSet().length;

                        for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                            if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                                printLines++;
                            } else {
                                noPrintLines++;
                            }
                        }
                    }

                    self.statementLayoutHistData().usedLines(printLines + "行 (+非表示" + noPrintLines + "行)");
                    break;
                case StatementLayoutPattern.LASER_CRIMP_LANDSCAPE_ONE_PERSON:
                    for(let settingByCtg: SettingByCtg of self.statementLayoutHistData().statementLayoutSet().listSettingByCtg()) {
                        if(settingByCtg.ctgAtr == CategoryAtr.PAYMENT_ITEM) {
                            for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                                    printLinesPayment++;
                                } else {
                                    noPrintLinesPayment++;
                                }
                            }
                        } else if(settingByCtg.ctgAtr == CategoryAtr.DEDUCTION_ITEM) {
                            for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                                    printLinesDedu++;
                                } else {
                                    noPrintLinesDedu++;
                                }
                            }
                        } else if(settingByCtg.ctgAtr == CategoryAtr.ATTEND_ITEM) {
                            for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                                    printLinesTime++;
                                } else {
                                    noPrintLinesTime++;
                                }
                            }
                        } else if(settingByCtg.ctgAtr == CategoryAtr.REPORT_ITEM) {
                            for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                                    printLinesReport++;
                                } else {
                                    noPrintLinesReport++;
                                }
                            }
                        }
                    }

                    self.statementLayoutHistData().usedLines("支給" + printLinesPayment + "行(＋非表示" + noPrintLinesPayment + "行) " +
                                                            "控除" + printLinesDedu + "行(＋非表示" + noPrintLinesDedu + "行) " +
                                                            "勤怠" + printLinesTime + "行(＋非表示" + noPrintLinesTime + "行) " +
                                                            "記事" + printLinesReport + "行(＋非表示" + noPrintLinesReport + "行) ");
                    break;
                case StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON:
                    for(let settingByCtg: SettingByCtg of self.statementLayoutHistData().statementLayoutSet().listSettingByCtg()) {
                        if(settingByCtg.ctgAtr == CategoryAtr.PAYMENT_ITEM) {
                            for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                                    printLinesPayment++;
                                } else {
                                    noPrintLinesPayment++;
                                }
                            }
                        } else if(settingByCtg.ctgAtr == CategoryAtr.DEDUCTION_ITEM) {
                            for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                                    printLinesDedu++;
                                } else {
                                    noPrintLinesDedu++;
                                }
                            }
                        } else if(settingByCtg.ctgAtr == CategoryAtr.ATTEND_ITEM) {
                            for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                                    printLinesTime++;
                                } else {
                                    noPrintLinesTime++;
                                }
                            }
                        } else if(settingByCtg.ctgAtr == CategoryAtr.REPORT_ITEM) {
                            for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                                if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                                    printLinesReport++;
                                } else {
                                    noPrintLinesReport++;
                                }
                            }
                        }
                    }

                    self.statementLayoutHistData().usedLines("支給" + printLinesPayment + "行(＋非表示" + noPrintLinesPayment + "行) " +
                                                            "控除" + printLinesDedu + "行(＋非表示" + noPrintLinesDedu + "行) " +
                                                            "勤怠" + printLinesTime + "行(＋非表示" + noPrintLinesTime + "行) " +
                                                            "記事" + printLinesReport + "行(＋非表示" + noPrintLinesReport + "行) ");
                    break;
                default:
                    return "";
            }
        }

        loadLayoutHistData(code: string, histId: string) {
            let self = this;
            block.invisible();

            service.getStatementLayoutHistData(code, histId).done(function(data: IStatementLayoutHistData) {
                if(data) {
                    self.statementLayoutHistData(new StatementLayoutHistData(data, false));
                    self.calculatePrintLine();
                    $("#A3_4").focus();
                } else {
                    self.statementLayoutHistData(new StatementLayoutHistData(null, false));
                }

                block.clear();
            }).fail(() => {
                self.statementLayoutHistData(new StatementLayoutHistData(null, false));

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

                block.clear();
                dfd.resolve();
            }).fail(() => {
                block.clear();
                dfd.resolve();
            })

            return dfd.promise();
        }

        public create(): void {
            let self = this;

            nts.uk.ui.errors.clearAll();
            nts.uk.ui.windows.sub.modal('../h/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_H_TO_A_PARAMS");
                let histID = params.histID;

                if(params && params.isRegistered) {
                    self.loadListData().done(function() {
                        let matchKey: boolean = _.filter(self.statementLayoutList(), function (o: StatementLayout) {
                            return _.filter(o.history, function (h: YearMonthHistory) {
                                return h.historyId == histID;
                            }).length > 0;
                        }).length > 0;

                        if (matchKey) {
                            self.currentHistoryId(histID);
                            //self.currentHistoryId.valueHasMutated();
                        } else if (self.statementLayoutList().length > 0) {
                            let histLength = self.statementLayoutList()[0].history.length;
                            if (histLength > 0) {
                                self.currentHistoryId(self.statementLayoutList()[0].history[0].historyId);
                            }
                        }
                    });
                }

                $("#A3_4").focus();
            });
        }

        public createIfEmpty(): void {
            let self = this;

            setTimeout(function() {
                nts.uk.ui.errors.clearAll();
            }, 2000);

            nts.uk.ui.windows.sub.modal('../h/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_H_TO_A_PARAMS");
                let histID = params.histID;

                if(params && params.isRegistered) {
                    self.loadListData().done(function() {
                        let matchKey: boolean = _.filter(self.statementLayoutList(), function (o: StatementLayout) {
                            return _.filter(o.history, function (h: YearMonthHistory) {
                                return h.historyId == histID;
                            }).length > 0;
                        }).length > 0;

                        if (matchKey) {
                            self.currentHistoryId(histID);
                            //self.currentHistoryId.valueHasMutated();
                        } else if (self.statementLayoutList().length > 0) {
                            let histLength = self.statementLayoutList()[0].history.length;
                            if (histLength > 0) {
                                self.currentHistoryId(self.statementLayoutList()[0].history[0].historyId);
                            }
                        }
                    });

                    $("#A3_4").focus();
                } else {
                    nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                }
            });
        }

        public registered(): void {
            let self = this;
            let oldHist = self.statementLayoutHistData().historyId;

            if(!nts.uk.ui.errors.hasError()) {
                // clear menu to use ko.toJS
                for(let settingByCtg: SettingByCtg of self.statementLayoutHistData().statementLayoutSet().listSettingByCtg()) {
                    for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                        for(let settingByItem: SettingByItem of lineByLineSetting.listSetByItem()) {
                            settingByItem.menu = null;
                        }
                    }
                }
                let data = ko.toJS(self.statementLayoutHistData);

                // modify data
                for(let settingByCtg of data.statementLayoutSet.listSettingByCtg) {
                    delete settingByCtg.parent;
                    delete settingByCtg.addLine;
                    delete settingByCtg.showCtg;

                    for(let lineByLineSetting of settingByCtg.listLineByLineSet) {
                        delete lineByLineSetting.parent;
                        delete lineByLineSetting.editLine;

                        lineByLineSetting.lineNumber = settingByCtg.listLineByLineSet.indexOf(lineByLineSetting) + 1;

                        for(let settingByItem of lineByLineSetting.listSetByItem) {
                            delete settingByItem.parent;
                            delete settingByItem.delete;
                            delete settingByItem.openSetting;

                            settingByItem.itemPosition = lineByLineSetting.listSetByItem.indexOf(settingByItem) + 1;
                        }

                        _.remove(lineByLineSetting.listSetByItem, function (item: SettingByItem) {
                            return item.deleted || _.isEmpty(item.itemId);
                        });
                    }
                }

                block.invisible();
                service.updateStatementLayoutHistData(data).done(() => {
                    block.clear();
                    self.statementLayoutHistData().checkCreate(false);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                        self.loadListData().done(function() {
                            let matchKey: boolean = _.filter(self.statementLayoutList(), function(o: StatementLayout) {
                                return _.filter(o.history, function(h: YearMonthHistory) {
                                    return h.historyId == oldHist;
                                }).length > 0;
                            }).length > 0;

                            if(matchKey) {
                                if (self.currentHistoryId() == oldHist) {
                                    self.currentHistoryId.valueHasMutated();
                                } else {
                                    self.currentHistoryId(oldHist);
                                }
                            } else if(self.statementLayoutList().length > 0) {
                                let histLength = self.statementLayoutList()[0].history.length;
                                if(histLength > 0) {
                                    self.currentHistoryId(self.statementLayoutList()[0].history[0].historyId);
                                }
                            }

                            $("#A3_4").focus();
                        });
                    });
                }).fail(err => {
                    block.clear();
                    console.log(err);
                });
            }
        }

        public outputExcel(): void {
            modal("/view/qmm/019/p/index.xhtml");
        }

        public addHistory(): void {
            let self = this;

            setShared("QMM019_A_TO_B_PARAMS", {code: self.currentStatementLayoutCode()});
            nts.uk.ui.windows.sub.modal('../b/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_B_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    let statementCode = params.code;
                    let startMonth =  params.startMonth;
                    let itemHistoryDivision =  params.itemHistoryDivision;
                    let layoutPattern = params.layoutPattern;
                    let histId = nts.uk.util.randomId();

                    service.getInitStatementLayoutHistData(statementCode, histId, startMonth, itemHistoryDivision, layoutPattern).done(function (data: IStatementLayoutHistData) {
                        if(data) {
                            self.addHistoryItem(statementCode, histId, startMonth, layoutPattern);
                            self.statementLayoutHistData(new StatementLayoutHistData(data, true));
                            self.currentHistoryId(histId);
                            self.calculatePrintLine();

                            $("#A3_4").focus();
                        }
                    });
                }
            });
        }

        public addHistoryItem(statementCode: string, newHistId: string, startMonth: number, layoutPattern: number) {
            let self = this;
            self.newHistId = newHistId;
            let layout: StatementLayout = _.find(self.statementLayoutList(), (item: StatementLayout) => {
                return item.statementCode == statementCode;
            });

            let lastHistoryItem: YearMonthHistory = _.maxBy(layout.history, (item: YearMonthHistory) => {
                return item.endMonth;
            });

            let startYM = moment.utc(startMonth, "YYYYMM");
            let endYM = 999912;
            let endYMOld = startYM.add(-1, "M");

            lastHistoryItem.endMonth = parseInt(endYMOld.format("YYYYMM"));
            lastHistoryItem.updateNodeText();
            let iHist: IYearMonthHistory = <IYearMonthHistory> {
                historyId: newHistId,
                startMonth: startMonth,
                endMonth: endYM,
                layoutPattern: layoutPattern
            };
            let newHist: YearMonthHistory = new YearMonthHistory(statementCode, iHist);
            layout.history.unshift(newHist);
        }

        public editHistory(): void {
            let self = this;
            let oldCode = self.currentStatementLayoutCode();
            let oldHist = self.currentHistoryId();

            setShared("QMM019_A_TO_C_PARAMS", {code: self.currentStatementLayoutCode(), histId: self.currentHistoryId()});
            nts.uk.ui.windows.sub.modal('../c/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_C_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    self.loadLayoutHistData(self.currentStatementLayoutCode(), self.currentHistoryId());
                    self.loadListData().done(function() {
                        if(params.isEdit) {
                            let matchKey: boolean = _.filter(self.statementLayoutList(), function(o: StatementLayout) {
                                return _.filter(o.history, function(h: YearMonthHistory) {
                                    return h.historyId == oldHist;
                                }).length > 0;
                            }).length > 0;

                            if(matchKey) {
                                self.currentHistoryId(oldHist);
                                //self.currentHistoryId.valueHasMutated();
                            } else if(self.statementLayoutList().length > 0) {
                                let histLength = self.statementLayoutList()[0].history.length;
                                if(histLength > 0) {
                                    self.currentHistoryId(self.statementLayoutList()[0].history[0].historyId);
                                }
                            }
                        } else {
                            let matchCode: Array<StatementLayout> = _.filter(self.statementLayoutList(), function(o: StatementLayout) {
                                return o.statementCode == oldCode;
                            });

                            if(matchCode.length > 0) {
                                let histLength = matchCode[0].history.length;
                                if(histLength > 0) {
                                    self.currentHistoryId(matchCode[0].history[0].historyId);
                                }
                            } else if(self.statementLayoutList().length > 0){
                                let histLength = self.statementLayoutList()[0].history.length;
                                if(histLength > 0) {
                                    self.currentHistoryId(self.statementLayoutList()[0].history[0].historyId);
                                }
                            } else {
                                self.createIfEmpty();
                            }
                        }
                    });
                }

                $("#A3_4").focus();
            });
        }

        public modifyLog(): void {

        }
    }

    export class StatementLayoutHistData {
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
        layoutPatternText: string;
        layoutPatternContent: string;
        usedLines: KnockoutObservable<string>;

        constructor(data: IStatementLayoutHistData, isCreate: boolean) {
            let self = this;

            if(data) {
                self.statementCode = data.statementCode;
                self.statementName = ko.observable(data.statementName);
                self.historyId = data.historyId;
                self.startMonth = ko.observable(data.startMonth);
                self.endMonth = ko.observable(data.endMonth);
                self.statementLayoutSet = ko.observable(new StatementLayoutSet(data.statementLayoutSet));

                self.checkCreate = ko.observable(isCreate);
                self.startMonthText = ko.observable(nts.uk.time.parseYearMonth(data.startMonth).format());
                self.endMonthText = ko.observable(nts.uk.time.parseYearMonth(data.endMonth).format());
                self.layoutPatternText = getLayoutPatternText(data.statementLayoutSet.layoutPattern);
                self.layoutPatternContent = getLayoutPatternContent(data.statementLayoutSet.layoutPattern);
            } else {
                self.statementCode = null;
                self.statementName = ko.observable(null);
                self.historyId = null;
                self.startMonth = ko.observable(null);
                self.endMonth = ko.observable(null);
                self.statementLayoutSet = ko.observable(new StatementLayoutSet(null));

                self.checkCreate = ko.observable(false);
                self.startMonthText = ko.observable(null);
                self.endMonthText = ko.observable(null);
                self.layoutPatternText = "";
                self.layoutPatternContent = "";
            }

            self.usedLines = ko.observable("");

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

                data.listSettingByCtg.sort((ctg1: ISettingByCtg, ctg2: ISettingByCtg) => {
                    return ctg1.ctgAtr - ctg2.ctgAtr;
                });
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
        isShowCtg: KnockoutObservable<boolean> = ko.observable(false);

        constructor(data: ISettingByCtg, parent: StatementLayoutSet) {
            let self = this;

            if(data) {
                self.ctgAtr = data.ctgAtr;
                _.orderBy(data.listLineByLineSet, "lineNumber");
                data.listLineByLineSet.sort((line1: ILineByLineSetting, line2: ILineByLineSetting) => {
                    return line1.lineNumber - line2.lineNumber;
                });
                self.listLineByLineSet = ko.observableArray(data.listLineByLineSet.map(i => new LineByLineSetting(i, null, self)));
            } else {
                self.ctgAtr = null;
                self.listLineByLineSet = ko.observableArray([]);
            }

            self.parent = parent;
            self.isShowCtg(self.listLineByLineSet().length > 0);
        }

        public addLine(): void {
            let self = this;
            let totalLine = 0;
            let printLineInCtg = 0;
            let noPrintLineInCtg = 0;

            for(let settingByCtg: SettingByCtg of self.parent.listSettingByCtg()) {
                for(let lineByLineSetting: LineByLineSetting of settingByCtg.listLineByLineSet()) {
                    if(lineByLineSetting.printSet() == StatementPrintAtr.PRINT) {
                        totalLine++;
                    }
                }
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
                    __viewContext['screenModel'].calculatePrintLine();
                }
            });
        }

        public showCtg(): void {
            let self = this;
            let totalLine = 0;
            let printLineInCtg = 0;
            let noPrintLineInCtg = 0;

            for(let settingByCtg: SettingByCtg of self.parent.listSettingByCtg()) {
                totalLine += settingByCtg.listLineByLineSet().length;
            }

            setShared("QMM019_A_TO_L_PARAMS", {
                layoutPattern: self.parent.layoutPattern(),
                totalLine: totalLine,
                ctgAtr: self.ctgAtr,
                printLineInCtg: printLineInCtg,
                noPrintLineInCtg: noPrintLineInCtg,
                isAddCategory: true
            });

            nts.uk.ui.windows.sub.modal('../l/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_L_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    self.isShowCtg(true);
                    self.listLineByLineSet.push(new LineByLineSetting(null, params.printSet, self));
                    __viewContext['screenModel'].calculatePrintLine();
                }
            });
        }

        public editCategory(): void {
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

            setShared("QMM019_A_TO_L_PARAMS", {
                layoutPattern: self.parent.layoutPattern(),
                totalLine: totalLine,
                ctgAtr: self.ctgAtr,
                printLineInCtg: printLineInCtg,
                noPrintLineInCtg: noPrintLineInCtg,
                isAddCategory: false
            });

            nts.uk.ui.windows.sub.modal('../l/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_L_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    if(params.printSet == 2) {
                        self.listLineByLineSet.removeAll();
                        self.isShowCtg(false);
                    } else {
                        for(let lineByLineSetting :LineByLineSetting of self.listLineByLineSet()) {
                            lineByLineSetting.printSet(params.printSet);
                        }
                    }

                    __viewContext['screenModel'].calculatePrintLine();
                }
            });
        }
    }

    class LineByLineSetting {
        printSet: KnockoutObservable<number>;
        lineNumber: KnockoutObservable<number>;
        listSetByItem: KnockoutObservableArray<SettingByItem>;

        parent: SettingByCtg;
        hasFixed: KnockoutObservable<boolean> = ko.observable(false);

        constructor(data: ILineByLineSetting, newPrintSet: number, parent: SettingByCtg) {
            let self = this;
            let listFullItem: Array<ISettingByItem>;
            self.parent = parent;

            if(data) {
                self.printSet = ko.observable(data.printSet);
                self.lineNumber = ko.observable(data.lineNumber);
                listFullItem = data.listSetByItem;
            } else {
                self.printSet = ko.observable(newPrintSet);
                self.lineNumber = ko.observable(self.parent.listLineByLineSet().length + 1);
                listFullItem = [];
            }

            self.listSetByItem = ko.observableArray([]);
            for(let i = 1; i <= 9; i++) {
                let itemInPosition:Array<ISettingByItem> = listFullItem.filter(item => item.itemPosition == i);
                self.listSetByItem.push(itemInPosition.length > 0 ? new SettingByItem(itemInPosition[0], self) : new SettingByItem(null, self));
            }

            for(let settingByItem: SettingByItem of self.listSetByItem()) {
                if(settingByItem.isFixed()) self.hasFixed(true);
            }

            // check hasFixed line when swap item fixed
            self.listSetByItem.subscribe((newValue) => {
                let hasFixed = false;
                for(let settingByItem: SettingByItem of newValue) {
                    if(settingByItem.isFixed()) hasFixed = true;
                }
                newValue[0].parent.hasFixed(hasFixed);
            })
        }

        public editLine(): void {
            let self = this;
            let totalLine = 0;
            let printLineInCtg = 0;
            let noPrintLineInCtg = 0;
            let haveItemBreakdownInsite = false;

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

            for(let settingByItem: SettingByItem of self.listSetByItem()) {
                if((settingByItem.paymentItemDetailSet != null) &&
                        (String(settingByItem.paymentItemDetailSet.calcMethod) == String(PaymentCaclMethodAtr.BREAKDOWN_ITEM)) &&
                        ((String(settingByItem.paymentItemDetailSet.totalObj) == String(PaymentTotalObjAtr.INSIDE)) || (String(settingByItem.paymentItemDetailSet.totalObj) == String(PaymentTotalObjAtr.INSIDE_ACTUAL)))) {
                    haveItemBreakdownInsite = true;
                }

                if((settingByItem.deductionItemDetailSet != null) && (String(settingByItem.deductionItemDetailSet.calcMethod) == String(PaymentCaclMethodAtr.BREAKDOWN_ITEM)) &&
                    ((String(settingByItem.deductionItemDetailSet.totalObj) == String(PaymentTotalObjAtr.INSIDE)) || (String(settingByItem.deductionItemDetailSet.totalObj) == String(PaymentTotalObjAtr.INSIDE_ACTUAL)))) {
                    haveItemBreakdownInsite = true;
                }
            }

            setShared("QMM019_A_TO_K_PARAMS", {
                layoutPattern: self.parent.parent.layoutPattern(),
                totalLine: totalLine,
                ctgAtr: self.parent.ctgAtr,
                printLineInCtg: printLineInCtg,
                noPrintLineInCtg: noPrintLineInCtg,
                printSet: self.printSet(),
                haveItemBreakdownInsite: haveItemBreakdownInsite
            });

            nts.uk.ui.windows.sub.modal('../k/index.xhtml').onClosed(() => {
                let params = getShared("QMM019_K_TO_A_PARAMS");

                if(params && params.isRegistered) {
                    if((params.printSet == StatementPrintAtr.PRINT) || (params.printSet == StatementPrintAtr.DO_NOT_PRINT)) {
                        self.printSet(params.printSet);

                        if(params.printSet == StatementPrintAtr.DO_NOT_PRINT) {
                            for(let settingByItem: SettingByItem of self.listSetByItem()) {
                                if(settingByItem.paymentItemDetailSet != null) {
                                    if (settingByItem.paymentItemDetailSet.totalObj == PaymentTotalObjAtr.INSIDE.toString()) {
                                        settingByItem.paymentItemDetailSet.totalObj = PaymentTotalObjAtr.OUTSIDE.toString();
                                    } else if (settingByItem.paymentItemDetailSet.totalObj == PaymentTotalObjAtr.INSIDE_ACTUAL.toString()) {
                                        settingByItem.paymentItemDetailSet.totalObj = PaymentTotalObjAtr.OUTSIDE_ACTUAL.toString();
                                    }
                                }

                                if(settingByItem.deductionItemDetailSet != null) {
                                    if (settingByItem.deductionItemDetailSet.totalObj == DeductionTotalObjAtr.INSIDE.toString()) {
                                        settingByItem.deductionItemDetailSet.totalObj = DeductionTotalObjAtr.OUTSIDE.toString();
                                    }
                                }
                            }
                        }
                    } else {
                        self.parent.listLineByLineSet.remove(self);
                    }

                    __viewContext['screenModel'].calculatePrintLine();
                }
            });
        }

        public preventStopSort(data, event, ui): void {
            let dragItem: SettingByItem = data.item;
            let listItemSource: Array<SettingByItem> = data.sourceParent();
            let listItemTarget: Array<SettingByItem> = data.targetParent();
            let checkCategory = dragItem.parent.parent.ctgAtr != listItemTarget[0].parent.parent.ctgAtr;
            let checkPrintLine = dragItem.parent.printSet() != listItemTarget[0].parent.printSet();
            let checkDragToOtherLine = dragItem.parent.lineNumber() != listItemTarget[0].parent.lineNumber();

            if(checkCategory || checkPrintLine) {
                data.cancelDrop = true;
            } else if(checkDragToOtherLine) {
                let targetIndex = data.targetIndex;
                if(targetIndex == 9) targetIndex = 8;
                let targetItem = listItemTarget[targetIndex];
                let category: SettingByCtg = dragItem.parent.parent;
                let categoryIndex = category.ctgAtr;
                let targetLineIndex: number;
                let sourceLineIndex: number;

                for(let i = 0; i < category.listLineByLineSet().length; i++) {
                    if(category.listLineByLineSet()[i].lineNumber() == targetItem.parent.lineNumber()) {
                        targetLineIndex = i;
                    }

                    if(category.listLineByLineSet()[i].lineNumber() == listItemSource[0].parent.lineNumber()) {
                        sourceLineIndex = i;
                    }
                }

                data.cancelDrop = true;

                setTimeout(function() {
                    let sourceItem = __viewContext['screenModel'].statementLayoutHistData().statementLayoutSet().
                    listSettingByCtg()[categoryIndex].listLineByLineSet()[sourceLineIndex].listSetByItem()[data.sourceIndex];
                    let targetItem = __viewContext['screenModel'].statementLayoutHistData().statementLayoutSet().
                    listSettingByCtg()[categoryIndex].listLineByLineSet()[targetLineIndex].listSetByItem()[targetIndex];

                    let temp = sourceItem.parent;
                    sourceItem.parent = targetItem.parent;
                    targetItem.parent = temp;

                    __viewContext['screenModel'].statementLayoutHistData().statementLayoutSet().listSettingByCtg()[categoryIndex].
                    listLineByLineSet()[sourceLineIndex].listSetByItem()[data.sourceIndex] = targetItem;
                    __viewContext['screenModel'].statementLayoutHistData().statementLayoutSet().listSettingByCtg()[categoryIndex].
                    listLineByLineSet()[targetLineIndex].listSetByItem()[targetIndex] = sourceItem;

                    __viewContext['screenModel'].statementLayoutHistData().statementLayoutSet().listSettingByCtg()[categoryIndex].
                    listLineByLineSet()[targetLineIndex].listSetByItem.valueHasMutated();
                    __viewContext['screenModel'].statementLayoutHistData().statementLayoutSet().listSettingByCtg()[categoryIndex].
                    listLineByLineSet()[sourceLineIndex].listSetByItem.valueHasMutated();
                }, 50);
            }

            $(".limited-label-view").remove();
        }
    }

    class SettingByItem {
        itemPosition: KnockoutObservable<number>;
        itemId: KnockoutObservable<string>;
        shortName: KnockoutObservable<string>;
        paymentItemDetailSet: IPaymentItemDetail;
        deductionItemDetailSet: IDeductionItemDetail;
        itemRangeSet: IItemRangeSet;

        parent: LineByLineSetting;
        deleted: KnockoutObservable<boolean>;
        id: KnockoutObservable<string>;
        menu: nts.uk.ui.contextmenu.ContextMenu;
        isFixed: KnockoutObservable<boolean> = ko.observable(false);

        constructor(data: ISettingByItem, parent: LineByLineSetting) {
            let self = this;

            if(data) {
                self.itemPosition = ko.observable(data.itemPosition);
                self.itemId = ko.observable(data.itemId);
                self.shortName = ko.observable(data.shortName);
                self.paymentItemDetailSet = data.paymentItemDetailSet;
                self.deductionItemDetailSet = data.deductionItemDetailSet;
                self.itemRangeSet = data.itemRangeSet;

                if((self.paymentItemDetailSet != null) && ((self.paymentItemDetailSet.salaryItemId == "F001") ||
                        (self.paymentItemDetailSet.salaryItemId == "F002") || (self.paymentItemDetailSet.salaryItemId == "F003"))) {
                    self.isFixed(true);
                }

                if((self.deductionItemDetailSet != null) && (self.deductionItemDetailSet.salaryItemId == "F114")) {
                    self.isFixed(true);
                }
            } else {
                self.itemPosition = ko.observable(null);
                self.itemId = ko.observable(null);
                self.shortName = ko.observable("＋");
                self.paymentItemDetailSet = null;
                self.deductionItemDetailSet = null;
                self.itemRangeSet = null;
            }

            self.parent = parent;
            self.deleted = ko.observable(false);
            self.id = ko.observable("item_" + self.parent.parent.ctgAtr + "_" + self.parent.lineNumber() + "_" + self.itemId());

            if(!self.isFixed() && self.itemId() != null) {
                self.menu = new nts.uk.ui.contextmenu.ContextMenu("." + self.id(), [
                    new nts.uk.ui.contextmenu.ContextMenuItem(
                        "Delete",
                        getText('QMM019_235'),
                        () => { self.delete() },
                        "ui-icon ui-icon-trash",
                        true,
                        true
                    ),
                    new nts.uk.ui.contextmenu.ContextMenuItem(
                        "Resume",
                        getText('QMM019_234'),
                        () => { self.delete() },
                        "ui-icon ui-icon-clipboard",
                        false,
                        true
                    )
                ]);
            }

            self.deleted.subscribe(deleted => {
                if(deleted == true) {
                    self.menu.setVisibleItem(false, 0);
                    self.menu.setVisibleItem(true, 1);
                } else {
                    self.menu.setVisibleItem(false, 1);
                    self.menu.setVisibleItem(true, 0);
                }
            });
            self.itemId.subscribe(id => {
                if(!_.isEmpty(id) && (self.menu == null)) {
                    self.id("item_" + self.parent.parent.ctgAtr + "_" + self.parent.lineNumber() + "_" + id);

                    self.menu = new nts.uk.ui.contextmenu.ContextMenu("." + self.id(), [
                        new nts.uk.ui.contextmenu.ContextMenuItem(
                            "Delete",
                            getText('QMM019_235')/*"Xóa"*/,
                            () => { self.delete() },
                            "ui-icon ui-icon-trash",
                            true,
                            true
                        ),
                        new nts.uk.ui.contextmenu.ContextMenuItem(
                            "Resume",
                            getText('QMM019_234')/*bỏ xóa*/,
                            () => { self.delete() },
                            "ui-icon ui-icon-clipboard",
                            false,
                            true
                        )
                    ]);
                }
            })
        }

        public delete(): void {
            let self = this;

            if(self.deleted()) {
                self.deleted(false);
            } else {
                self.deleted(true);
            }
        }

        public openSetting(): void {
            let self = this;

            let listItemSetting: Array<string> = [];
            let listLineInCtg = self.parent.parent.listLineByLineSet();

            for (let lineByLine: LineByLineSetting of listLineInCtg) {
                for(let item: SettingByItem of lineByLine.listSetByItem()) {
                    if (item.itemId() != null) listItemSetting.push(item.itemId());
                }
            }

            if(!self.isFixed()) {
                switch (self.parent.parent.ctgAtr) {
                    case CategoryAtr.PAYMENT_ITEM: {
                        setShared("QMM019_A_TO_D_PARAMS", {
                            printSet: this.parent.printSet(),
                            yearMonth: __viewContext['screenModel'].statementLayoutHistData().startMonth(),
                            itemNameCode: self.itemId(),
                            listItemSetting: listItemSetting,
                            detail: self.paymentItemDetailSet,
                            itemRangeSet: self.itemRangeSet
                        });

                        nts.uk.ui.windows.sub.modal('../d/index.xhtml').onClosed(() => {
                            let params = getShared("QMM019D_RESULTS");

                            if(params) {
                                params.detail.histId = __viewContext['screenModel'].statementLayoutHistData().historyId;
                                params.itemRangeSet.histId = __viewContext['screenModel'].statementLayoutHistData().historyId;
                                params.itemRangeSet.salaryItemId = params.itemNameCode;
                                self.shortName(params.shortName);
                                self.itemId(params.itemNameCode);
                                self.paymentItemDetailSet = params.detail;
                                self.itemRangeSet = params.itemRangeSet;
                            }
                        });
                        break;
                    }
                    case CategoryAtr.DEDUCTION_ITEM: {
                        setShared("QMM019_A_TO_E_PARAMS", {
                            printSet: this.parent.printSet(),
                            yearMonth: __viewContext['screenModel'].statementLayoutHistData().startMonth(),
                            itemNameCode: self.itemId(),
                            listItemSetting: listItemSetting,
                            detail: self.deductionItemDetailSet,
                            itemRangeSet: self.itemRangeSet
                        });

                        nts.uk.ui.windows.sub.modal('../e/index.xhtml').onClosed(() => {
                            let params = getShared("QMM019E_RESULTS");

                            if(params) {
                                params.detail.histId = __viewContext['screenModel'].statementLayoutHistData().historyId;
                                params.itemRangeSet.histId = __viewContext['screenModel'].statementLayoutHistData().historyId;
                                params.itemRangeSet.salaryItemId = params.itemNameCode;
                                self.shortName(params.shortName);
                                self.itemId(params.itemNameCode);
                                self.deductionItemDetailSet = params.detail;
                                self.itemRangeSet = params.itemRangeSet;
                            }
                        });
                        break;
                    }
                    case CategoryAtr.ATTEND_ITEM: {
                        setShared("QMM019_A_TO_F_PARAMS", {
                            printSet: this.parent.printSet(),
                            yearMonth: __viewContext['screenModel'].statementLayoutHistData().startMonth(),
                            itemNameCode: self.itemId(),
                            listItemSetting: listItemSetting,
                            itemRangeSet: self.itemRangeSet
                        });

                        nts.uk.ui.windows.sub.modal('../f/index.xhtml').onClosed(() => {
                            let params = getShared("QMM019F_RESULTS");

                            if(params) {
                                params.itemRangeSet.histId = __viewContext['screenModel'].statementLayoutHistData().historyId;
                                params.itemRangeSet.salaryItemId = params.itemNameCode;
                                self.shortName(params.shortName);
                                self.itemId(params.itemNameCode);
                                self.itemRangeSet = params.itemRangeSet;
                            }
                        });
                        break;
                    }
                    case CategoryAtr.REPORT_ITEM: {
                        setShared("QMM019_A_TO_G_PARAMS", {
                            printSet: this.parent.printSet(),
                            yearMonth: __viewContext['screenModel'].statementLayoutHistData().startMonth(),
                            itemNameCode: self.itemId(),
                            listItemSetting: listItemSetting
                        });
                        nts.uk.ui.windows.sub.modal('../g/index.xhtml').onClosed(() => {
                            let params = getShared("QMM019G_RESULTS");

                            if(params) {
                                self.shortName(params.shortName);
                                self.itemId(params.itemNameCode);
                            }
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
}