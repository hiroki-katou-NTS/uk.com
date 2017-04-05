module nts.uk.pr.view.qmm017.k {
    export module viewmodel {
        export class ScreenModel {
            data: any;
            startYm: KnockoutObservable<any>;
            endYm: KnockoutObservable<any>;
            selectedMode: KnockoutObservable<number>;

            constructor(param) {
                var self = this;
                self.data = param;
                self.startYm = ko.observable(param.startYm);
                self.endYm = ko.observable(param.endYm);
                self.selectedMode = ko.observable(1);
            }


            actionHandler() {
                var self = this;
                if (self.selectedMode() == 0) {
                    let command = {
                        formulaCode: self.data.formulaCode,
                        historyId: self.data.historyId,
                        startDate: self.startYm(),
                        difficultyAtr: self.data.difficultyAtr
                    }
                    service.removeFormulaHistory(command)
                        .done(function() {
                            nts.uk.ui.windows.close();
                        })
                        .fail(function(res) {
                            alert(res);
                        });
                } else {
                    let command = {
                        formulaCode: self.data.formulaCode,
                        historyId: self.data.historyId,
                        startDate: self.startYm(),
                    }
                    service.updateFormulaHistory(command)
                        .done(function() {
                            nts.uk.ui.windows.close();
                        })
                        .fail(function(res) {
                            alert(res);
                        });    
                }
            }
        }

    }

}