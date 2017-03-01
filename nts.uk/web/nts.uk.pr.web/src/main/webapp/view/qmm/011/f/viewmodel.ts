module nts.uk.pr.view.qmm011.f {
    import option = nts.uk.ui.option;
    import TypeHistory = nts.uk.pr.view.qmm011.a.service.model.TypeHistory;
    import HistoryUnemployeeInsuranceDto = nts.uk.pr.view.qmm011.a.service.model.HistoryUnemployeeInsuranceDto;
    import HistoryAccidentInsuranceDto = nts.uk.pr.view.qmm011.a.service.model.HistoryAccidentInsuranceDto;
    export module viewmodel {
        export class ScreenModel {
            fsel001: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;
            textEditorOption: KnockoutObservable<any>;
            historyId: KnockoutObservable<string>;
            historyStart: KnockoutObservable<string>;
            historyEnd: KnockoutObservable<string>;
            typeHistory: KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.fsel001 = ko.observableArray([
                    new BoxModel(1, '最新の履歴（N）から引き継ぐ'),
                    new BoxModel(2, '初めから作成する')
                ]);
                self.selectedId = ko.observable(1);
                self.enable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.typeHistory = ko.observable(nts.uk.ui.windows.getShared("type"));
                self.historyStart = ko.observable(nts.uk.ui.windows.getShared("historyStart"));
                self.historyId = ko.observable(nts.uk.ui.windows.getShared("historyId"));
                self.historyEnd = ko.observable(nts.uk.ui.windows.getShared("historyEnd"));
            }
            fwupdateHistoryInfoUnemployeeInsurance() {
                var self = this;
                var historyInfo: HistoryUnemployeeInsuranceDto;
                historyInfo = new HistoryUnemployeeInsuranceDto(self.historyId(), self.historyStart(), self.historyEnd());
                nts.uk.ui.windows.setShared("updateHistoryUnemployeeInsuranceDto", historyInfo);
            }
            fwupdateHistoryInfoAccidentInsurance() {
                var self = this;
                var historyInfo: HistoryAccidentInsuranceDto;
                historyInfo = new HistoryAccidentInsuranceDto(self.historyId(), self.historyStart(), self.historyEnd());
                nts.uk.ui.windows.setShared("updateHistoryAccidentInsuranceDto", historyInfo);
            }
            fwupdateHistoryInfo() {
                var self = this;
                if (self.typeHistory() == TypeHistory.HistoryUnemployee) {
                    self.fwupdateHistoryInfoUnemployeeInsurance();
                } else {
                    self.fwupdateHistoryInfoAccidentInsurance();
                }
                nts.uk.ui.windows.close();
            }
        }

        export class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }

    }
}