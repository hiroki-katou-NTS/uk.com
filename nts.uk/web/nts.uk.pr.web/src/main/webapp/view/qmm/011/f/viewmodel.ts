module nts.uk.pr.view.qmm011.f {
    import option = nts.uk.ui.option;
    import HistoryUnemployeeInsuranceRate = nts.uk.pr.view.qmm011.a.service.model.HistoryUnemployeeInsuranceRate;
    import BusinessTypeEnum = nts.uk.pr.view.qmm011.a.service.model.BusinessTypeEnum;
    import InsuBizRateItem = nts.uk.pr.view.qmm011.a.service.model.InsuBizRateItem;
    import RoundingMethod = nts.uk.pr.view.qmm011.a.service.model.RoundingMethod;
    import TypeHistory = nts.uk.pr.view.qmm011.a.service.model.TypeHistory;
    import AccidentInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.AccidentInsuranceRateModel;
    import HistoryUnemployeeInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.HistoryUnemployeeInsuranceRateModel;
    import HistoryAccidentInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.HistoryAccidentInsuranceRateModel;
    export module viewmodel {
        export class ScreenModel {
            fsel001: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;
            textEditorOption: KnockoutObservable<any>;
            historyId: KnockoutObservable<string>;
            historyStart: KnockoutObservable<string>;
            historyEnd: KnockoutObservable<string>;
            typeHistory: number;

            constructor() {
                var self = this;
                self.fsel001 = ko.observableArray([
                    new BoxModel(1, '最新の履歴（N）から引き継ぐ'),
                    new BoxModel(2, '初めから作成する')
                ]);
                self.selectedId = ko.observable(1);
                self.enable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                var historyId = nts.uk.ui.windows.getShared("historyId");
                var lsthistoryValue = nts.uk.ui.windows.getShared("lsthistoryValue");
                self.typeHistory = nts.uk.ui.windows.getShared("type");
                if (self.typeHistory == TypeHistory.HistoryUnemployee) {
                    for (var index = 0; index < lsthistoryValue.length; index++) {
                        if (lsthistoryValue[index].historyId === historyId) {
                            self.historyStart = ko.observable(new HistoryUnemployeeInsuranceRateModel(lsthistoryValue[index]).getViewStartMonth(lsthistoryValue[index]));
                            self.historyEnd = ko.observable(new HistoryUnemployeeInsuranceRateModel(lsthistoryValue[index]).getViewEndMonth(lsthistoryValue[index]));
                        }
                    }
                } else {
                    for (var index = 0; index < lsthistoryValue.length; index++) {
                        if (lsthistoryValue[index].historyId === historyId) {
                            self.historyStart = ko.observable(new HistoryAccidentInsuranceRateModel(lsthistoryValue[index]).getViewStartMonth(lsthistoryValue[index]));
                            self.historyEnd = ko.observable(new HistoryAccidentInsuranceRateModel(lsthistoryValue[index]).getViewEndMonth(lsthistoryValue[index]));
                        }
                    }
                }
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