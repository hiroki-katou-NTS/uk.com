module nts.uk.pr.view.qmm019.h.viewmodel {
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import IStatementLayout = nts.uk.pr.view.qmm019.share.model.IStatementLayout;
    import getLayoutPatternText = nts.uk.pr.view.qmm019.share.model.getLayoutPatternText;

    export class ScreenModel {

        cloneList: KnockoutObservableArray<shareModel.BoxModel>;
        isClone: KnockoutObservable<number>;

        statementLayoutList: KnockoutObservableArray<IStatementLayout>;
        statementLayoutCodeSelected: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        layoutPatternText: KnockoutObservable<string>;
        statementCode: KnockoutObservable<string>;
        statementName: KnockoutObservable<string>;
        startDate: KnockoutObservable<number>;
        startDateJp: KnockoutObservable<string>;

        constructor() {
            let self = this;

            self.cloneList = ko.observableArray(shareModel.getSpecCreateAtr());
            self.isClone = ko.observable(shareModel.SpecCreateAtr.NEW);

            self.statementLayoutList = ko.observableArray([]);
            self.statementLayoutCodeSelected = ko.observable(null);

            self.isEnable = ko.observable(false);
            self.layoutPatternText = ko.observable("");
            self.statementCode = ko.observable(null);
            self.statementName = ko.observable(null);
            self.startDate = ko.observable(null);
            self.startDateJp = ko.observable(null);

            self.isClone.subscribe(value => {
                self.isEnable(value == shareModel.SpecCreateAtr.COPY);
            });

            self.statementLayoutCodeSelected.subscribe(value => {
                let statementLayout: IStatementLayout = _.find(self.statementLayoutList(), (item: IStatementLayout) => {
                    return item.statementCode == value;
                });

                self.layoutPatternText(getLayoutPatternText(statementLayout.history[0].layoutPattern));
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

            service.getAllStatementLayoutAndLastHist().done(function(data: Array<IStatementLayout>) {
                self.statementLayoutList(data);

                if(data.length > 0) {
                    self.statementLayoutCodeSelected(data[0].statementCode);
                } else {
                    //TODO
                }

            });

            dfd.resolve();
            return dfd.promise();
        }

        decide() {


            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }
    }

    class StatementLayoutCommand {
        isClone: number;
        histIdClone: string;
        statementCode: string;
        statementName: string;
        startDate: number;

        constructor(isClone: number, histIdClone: string, statementCode: string,
                    statementName: string, startDate: number) {
            this.isClone = isClone;
            this.histIdClone = histIdClone;
            this.statementCode = statementCode;
            this.statementName = statementName;
            this.startDate = startDate;
        }
    }
}