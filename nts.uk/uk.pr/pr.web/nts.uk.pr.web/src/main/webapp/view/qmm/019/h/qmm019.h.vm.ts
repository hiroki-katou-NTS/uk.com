module nts.uk.pr.view.qmm019.h.viewmodel {
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import IStatementLayout = nts.uk.pr.view.qmm019.share.model.IStatementLayout;
    import getLayoutPatternText = nts.uk.pr.view.qmm019.share.model.getLayoutPatternText;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;

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
            self.layoutPatternSelected = ko.observable(0);

            self.isClone.subscribe(value => {
                self.isEnable(value == shareModel.SpecCreateAtr.COPY);
            });

            self.statementLayoutCodeSelected.subscribe(value => {
                let statementLayout: IStatementLayout = _.find(self.statementLayoutList(), (item: IStatementLayout) => {
                    return item.statementCode == value;
                });

                self.layoutPatternClone(statementLayout.history[0].layoutPattern);
                self.layoutPatternCloneText(getLayoutPatternText(statementLayout.history[0].layoutPattern));
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
                } else {
                    //TODO
                }

                block.clear();
            });

            dfd.resolve();
            return dfd.promise();
        }

        decide() {
            let self = this;
            block.invisible();

            let histIdNew = nts.uk.util.randomId();
            let layoutPattern = self.isClone() == 0 ? self.layoutPatternSelected() : self.layoutPatternClone();
            let command: StatementLayoutCommand = new StatementLayoutCommand(self.isClone(), histIdNew,
                    self.histIdClone(), self.statementCode(), self.statementName(), self.startDate(), layoutPattern);

            $("#B1_6").trigger("validate");
            if(!nts.uk.ui.errors.hasError()) {
                service.addStatementLayout(command).done(() => {
                    setShared("QMM019_H_TO_A_PARAMS", { isRegistered: false, histID: histIdNew});
                    nts.uk.ui.windows.close();
                }).fail(err => {
                    //TODO
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
        statementCode: string;
        statementName: string;
        startDate: number;
        layoutPattern: number;

        constructor(isClone: number, histIdNew: string, histIdClone: string,
                    statementCode: string, statementName: string, startDate: number, layoutPattern: number) {
            this.isClone = isClone;
            this.histIdNew = histIdNew;
            this.histIdClone = histIdClone;
            this.statementCode = statementCode;
            this.statementName = statementName;
            this.startDate = startDate;
            this.layoutPattern = layoutPattern;
        }
    }
}