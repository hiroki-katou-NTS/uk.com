module nts.uk.pr.view.qmm019.h.viewmodel {
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import IStatementLayout = nts.uk.pr.view.qmm019.share.model.IStatementLayout;
    import getLayoutPatternText = nts.uk.pr.view.qmm019.share.model.getLayoutPatternText;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {

        cloneList: KnockoutObservableArray<shareModel.BoxModel>;
        isClone: KnockoutObservable<number>;

        statementLayoutList: KnockoutObservableArray<IStatementLayout>;
        statementLayoutCodeSelected: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        layoutPatternClone: KnockoutObservable<number>;
        layoutPatternCloneText: KnockoutObservable<string>;
        histIdClone: KnockoutObservable<string>;
        statementCode: KnockoutObservable<string>;
        statementName: KnockoutObservable<string>;
        startDate: KnockoutObservable<number>;
        startDateJp: KnockoutObservable<string>;
        layoutPatternData: KnockoutObservableArray<any>;
        layoutPatternColumns: Array<any>;
        layoutPatternSelected: KnockoutObservable<number>;

        constructor() {
            let self = this;

            self.cloneList = ko.observableArray(shareModel.getSpecCreateAtr());
            self.isClone = ko.observable(shareModel.SpecCreateAtr.NEW);

            self.statementLayoutList = ko.observableArray([]);
            self.statementLayoutCodeSelected = ko.observable(null);

            self.isEnable = ko.observable(false);
            self.layoutPatternClone = ko.observable(null);
            self.layoutPatternCloneText = ko.observable("");
            self.histIdClone = ko.observable(null);
            self.statementCode = ko.observable(null);
            self.statementName = ko.observable(null);
            self.startDate = ko.observable(null);
            self.startDateJp = ko.observable(null);

            self.layoutPatternData = ko.observableArray(shareModel.getLayoutPatternData());
            self.layoutPatternSelected = ko.observable(0);
            self.layoutPatternColumns = [
                {headerText: '', key: 'id', dataType: 'string', width: '50px', hidden: true},
                {headerText: getText('QMM019_50'), key: 'printerType', dataType: 'string', width: '130px'},
                {headerText: getText('QMM019_51'), key: 'paper', dataType: 'string', width: '70px'},
                {headerText: getText('QMM019_52'), key: 'direction', dataType: 'string', width: '70px'},
                {headerText: getText('QMM019_53'), key: 'numberPersonInPage', dataType: 'string', width: '120px'},
                {headerText: getText('QMM019_54'), key: 'numberOfDisplayItem', dataType: 'string', width: '300px'},
                {headerText: getText('QMM019_55'), key: 'remarks', dataType: 'string', width: '100px'}
            ];

            self.isClone.subscribe(value => {
                self.isEnable(value == shareModel.SpecCreateAtr.COPY);
            });

            self.statementLayoutCodeSelected.subscribe(value => {
                if(!_.isEmpty(value)) {
                    let statementLayout: IStatementLayout = _.find(self.statementLayoutList(), (item: IStatementLayout) => {
                        return item.statementCode == value;
                    });

                    if(statementLayout) {
                        self.layoutPatternClone(statementLayout.history[0].layoutPattern);
                        self.layoutPatternCloneText(getLayoutPatternText(statementLayout.history[0].layoutPattern));
                        self.histIdClone(statementLayout.history[0].historyId);
                    }
                }
            });

            self.startDate.subscribe(value => {
                let dateJp = nts.uk.time.yearmonthInJapanEmpire(value);
                if(dateJp == null){
                    self.startDateJp(null);
                }else{
                    self.startDateJp(dateJp.toString());
                }
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();

            service.getAllStatementLayoutAndLastHist().done(function(data: Array<IStatementLayout>) {
                self.statementLayoutList(data);

                if(data.length > 0) {
                    self.statementLayoutCodeSelected(data[0].statementCode);
                }

                block.clear();
            });

            dfd.resolve();
            return dfd.promise();
        }

        decide() {
            let self = this;

            nts.uk.ui.errors.clearAll();
            $(".check-validate").trigger("validate");

            if(!nts.uk.ui.errors.hasError()) {
                let histIdNew = nts.uk.util.randomId();
                let startDate = nts.uk.time.parseYearMonth(self.startDate()).toValue();
                let command: StatementLayoutCommand = new StatementLayoutCommand(self.isClone(), histIdNew, self.histIdClone(),
                    self.layoutPatternClone(), self.statementCode(), self.statementName(), startDate, self.layoutPatternSelected(), self.statementLayoutCodeSelected());

                block.invisible();
                service.addStatementLayout(command).done(() => {
                    block.clear();

                    setShared("QMM019_H_TO_A_PARAMS", { isRegistered: true, code: self.statementCode(), histID: histIdNew});
                    nts.uk.ui.windows.close();
                }).fail(err => {
                    block.clear();

                    // if(err.messageId == "Msg_3") {
                    //     $("#H1_10").ntsError('set', { messageId: "Msg_3" });
                    //     $("#H1_10").focus();
                    // } else if(err.messageId == "MsgQ_33") {
                    //     $("#grid").ntsError('set', { messageId: "MsgQ_33" });
                    //     $("#grid").focus();
                    // }
                    nts.uk.ui.dialog.alertError({ messageId: err.messageId });
                });
            }
        }

        cancel() {
            setShared("QMM019_H_TO_A_PARAMS", { isRegistered: false});
            nts.uk.ui.windows.close();
        }
    }

    class StatementLayoutCommand {
        isClone: number;
        histIdNew: string;
        histIdClone: string;
        layoutPatternClone: number;
        statementCode: string;
        statementName: string;
        startDate: number;
        layoutPattern: number;
        statementCodeClone: string;

        constructor(isClone: number, histIdNew: string, histIdClone: string, layoutPatternClone: number,
                    statementCode: string, statementName: string, startDate: number, layoutPattern: number, statementCodeClone: string) {
            this.isClone = isClone;
            this.histIdNew = histIdNew;
            this.histIdClone = histIdClone;
            this.layoutPatternClone = layoutPatternClone;
            this.statementCode = statementCode;
            this.statementName = statementName;
            this.startDate = startDate;
            this.layoutPattern = layoutPattern;
            this.statementCodeClone = statementCodeClone;
        }
    }
}