module nts.uk.pr.view.qmm019.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import IStatementLayout = nts.uk.pr.view.qmm019.share.model.IStatementLayout;
    import YearMonthHistory = nts.uk.pr.view.qmm019.share.model.YearMonthHistory;
    import setShared = nts.uk.ui.windows.setShared;

    export class ScreenModel {
        statementCode: KnockoutObservable<string> = ko.observable(null);
        statementName: KnockoutObservable<string> = ko.observable(null);
        startMonth: KnockoutObservable<number>;
        yearMonthJapan: KnockoutObservable<string>;

        // ItemHistoryDivision  switch button
        itemHistoryDivisionList: KnockoutObservableArray<shareModel.BoxModel>;
        itemHistoryDivision: KnockoutObservable<number>;

        layoutPatternData: KnockoutObservableArray<any>;
        layoutPatternColumns: Array<any>;
        layoutPatternIdSelected: KnockoutObservable<number>;

        layoutPatternClone: number;

        constructor() {
            let self = this;
            let params = getShared("QMM019_A_TO_B_PARAMS");

            if(params) {
                self.statementCode(params.code);
            }

            self.startMonth = ko.observable(null);
            self.yearMonthJapan = ko.observable("");

            self.startMonth.subscribe(x => {
                let dateJp = nts.uk.time.yearmonthInJapanEmpire(x);
                if(dateJp == null){
                    self.yearMonthJapan(null);
                }else{
                    self.yearMonthJapan(dateJp.toString());
                }
            });

            self.itemHistoryDivisionList = ko.observableArray([
                new shareModel.BoxModel(0, getText('QMM019_46', [""])),
                new shareModel.BoxModel(1, getText('QMM019_47'))
            ]);
            self.itemHistoryDivision = ko.observable(0);

            self.layoutPatternData = ko.observableArray(shareModel.getLayoutPatternData());
            self.layoutPatternIdSelected = ko.observable(0);
            self.layoutPatternColumns = [
                    {headerText: '', key: 'id', dataType: 'string', width: '50px', hidden: true},
                    {headerText: getText('QMM019_50'), key: 'printerType', dataType: 'string', width: '130px'},
                    {headerText: getText('QMM019_51'), key: 'paper', dataType: 'string', width: '70px'},
                    {headerText: getText('QMM019_52'), key: 'direction', dataType: 'string', width: '70px'},
                    {headerText: getText('QMM019_53'), key: 'numberPersonInPage', dataType: 'string', width: '120px'},
                    {headerText: getText('QMM019_54'), key: 'numberOfDisplayItem', dataType: 'string', width: '300px'},
                    {headerText: getText('QMM019_55'), key: 'remarks', dataType: 'string', width: '100px'}
                ];
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();

            service.getStatementLayoutAndLastHist(self.statementCode()).done(function(data: IStatementLayout) {
                if(data) {
                    self.statementName(data.statementName);

                    if (data.history.length > 0) {
                        self.startMonth(data.history[0].startMonth);
                        self.layoutPatternClone = data.history[0].layoutPattern;
                        self.itemHistoryDivision(0);
                        self.itemHistoryDivisionList = ko.observableArray([
                            new shareModel.BoxModel(0, getText('QMM019_46', [nts.uk.time.formatYearMonth(self.startMonth())])),
                            new shareModel.BoxModel(1, getText('QMM019_47'))
                        ]);
                    } else {
                        self.itemHistoryDivision(1);
                    }
                }

                block.clear();
                dfd.resolve();
            }).fail(() => {
                block.clear();
                dfd.resolve();
            });

            return dfd.promise();
        }

        decide(){
            let self = this;
            $("#B1_6").trigger("validate");

            if(!nts.uk.ui.errors.hasError()) {
                service.getHistByCidAndCodeAndAfterDate(self.statementCode(), self.startMonth()).done(function (yearMonthHistory: Array<YearMonthHistory>) {
                    if((yearMonthHistory != null) && (yearMonthHistory.length > 0)) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_79" });
                    } else {
                        setShared("QMM019_B_TO_A_PARAMS", {
                                                              isRegistered: true,
                                                              code: self.statementCode(),
                                                              startMonth: self.startMonth(),
                                                              itemHistoryDivision: self.itemHistoryDivision(),
                                                              layoutPattern: self.itemHistoryDivision() == 1 ? self.layoutPatternIdSelected() : self.layoutPatternClone
                                                          });
                        nts.uk.ui.windows.close();
                    }
                });
            }
        }

        cancel(){
            setShared("QMM019_B_TO_A_PARAMS", { isRegistered: false});
            nts.uk.ui.windows.close();
        }
    }

}