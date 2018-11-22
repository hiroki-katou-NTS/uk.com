module nts.uk.pr.view.qmm017.g.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm017.g.service;
    import model = nts.uk.pr.view.qmm017.share.model;
    export class ScreenModel {
        calculationFormulaList: KnockoutObservableArray<any> = ko.observableArray([]);
        trialCalculationResult: KnockoutObservable<number> = ko.observable(null);
        formulaContent: KnockoutObservable<string> = ko.observable(null);
        constructor() {
            let self = this;
            self.initData();
            $('#G1_2').ntsFixedTable({height: 178});
        }

        initData () {
            let self = this;
            let calculationFormulaData : Array<any> = [
                {
                    formulaItem: '支給@AAA',
                    trialCalculationValue: 1500
                },
                {
                    formulaItem: '控除@BBB',
                    trialCalculationValue: 60
                },
                {
                    formulaItem: '勤怠@ CCC',
                    trialCalculationValue: 1.25
                },
                {
                    formulaItem: '支給@DDD',
                    trialCalculationValue: 100
                },
                {
                    formulaItem: '支給@AAA',
                    trialCalculationValue: 1500
                },
                {
                    formulaItem: '控除@BBB',
                    trialCalculationValue: 60
                },
                {
                    formulaItem: '勤怠@ CCC',
                    trialCalculationValue: 1.25
                },
                {
                    formulaItem: '支給@DDD',
                    trialCalculationValue: 100
                }
            ]
            self.calculationFormulaList(calculationFormulaData);
            self.trialCalculationResult(1.125);
        }

        startPage () : JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            dfd.resolve();
            block.clear();
            return dfd.promise();
        }
        closeDialog () {
            nts.uk.ui.windows.close();
        }
    }
}


