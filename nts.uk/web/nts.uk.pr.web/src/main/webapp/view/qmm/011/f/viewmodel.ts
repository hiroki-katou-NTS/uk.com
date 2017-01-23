module nts.uk.pr.view.qmm011.f {
    import option = nts.uk.ui.option;
    import TypeHistory = nts.uk.pr.view.qmm011.a.service.model.TypeHistory;
    import AccidentInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.AccidentInsuranceRateModel;
    import HistoryInfoDto = nts.uk.pr.view.qmm011.d.service.model.HistoryInfoDto;
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
                var historyStart = nts.uk.ui.windows.getShared("historyStart");
                var historyEnd = nts.uk.ui.windows.getShared("historyEnd");
                self.typeHistory = nts.uk.ui.windows.getShared("type");
                self.historyStart = ko.observable(historyStart);
                self.historyEnd = ko.observable(historyEnd);
            }
            updateHistoryInfoUnemployeeInsurance(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                var historyInfo: HistoryInfoDto;
                historyInfo = new HistoryInfoDto("historyId001", "companyCode001", null, self.historyStart(), "9999/12", true);
                service.updateHistoryInfoUnemployeeInsurance(historyInfo).done(data => {
                    /*  self.lstHistoryUnemployeeInsuranceRate = ko.observableArray<HistoryUnemployeeInsuranceRateDto>(data);
                      self.selectionHistoryUnemployeeInsuranceRate = ko.observable(data[0].historyId);
                      self.historyUnemployeeInsuranceRateStart = ko.observable(data[0].startMonthRage);
                      self.historyUnemployeeInsuranceRateEnd = ko.observable(data[0].endMonthRage);
                      self.selectionHistoryUnemployeeInsuranceRate.subscribe(function(selectionHistoryUnemployeeInsuranceRate: string) {
                          self.showchangeHistoryUnemployeeInsurance(selectionHistoryUnemployeeInsuranceRate);
                      });
                      self.detailHistoryUnemployeeInsuranceRate(data[0].historyId).done(data => {
                          dfd.resolve(self);
                      });
                    */
                });
                return dfd.promise();
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