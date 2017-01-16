module nts.uk.pr.view.qmm011.d {
    import option = nts.uk.ui.option;
    import HistoryUnemployeeInsuranceRate = nts.uk.pr.view.qmm011.a.service.model.HistoryUnemployeeInsuranceRate;
    import BusinessTypeEnum = nts.uk.pr.view.qmm011.a.service.model.BusinessTypeEnum;
    import InsuBizRateItem = nts.uk.pr.view.qmm011.a.service.model.InsuBizRateItem;
    import RoundingMethod = nts.uk.pr.view.qmm011.a.service.model.RoundingMethod;
    import AccidentInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.AccidentInsuranceRateModel;
    import HistoryUnemployeeInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.HistoryUnemployeeInsuranceRateModel;
    export module viewmodel {
        export class ScreenModel {
            dsel001: KnockoutObservableArray<any>;
            enable: KnockoutObservable<boolean>;
            textEditorOption: KnockoutObservable<any>;
            historyId: KnockoutObservable<string>;
            historyStart: KnockoutObservable<string>;
            historyEnd: KnockoutObservable<string>;
            selectedId: KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.dsel001 = ko.observableArray([
                    new BoxModel(1, '最新の履歴（N）から引き継ぐ'),
                    new BoxModel(2, '初めから作成する')
                ]);
                self.enable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.historyStart = ko.observable('');
                self.historyEnd = ko.observable('9999/12');
                self.selectedId = ko.observable(1);
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