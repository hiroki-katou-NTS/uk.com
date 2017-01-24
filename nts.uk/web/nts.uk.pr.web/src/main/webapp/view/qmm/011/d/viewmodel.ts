module nts.uk.pr.view.qmm011.d {
    import option = nts.uk.ui.option;
    import HistoryInfoDto = service.model.HistoryInfoDto;
    import TypeHistory = nts.uk.pr.view.qmm011.a.service.model.TypeHistory;
    export module viewmodel {
        export class ScreenModel {
            dsel001: KnockoutObservableArray<any>;
            enable: KnockoutObservable<boolean>;
            textEditorOption: KnockoutObservable<any>;
            historyId: KnockoutObservable<string>;
            historyStart: KnockoutObservable<string>;
            historyTakeover: KnockoutObservable<boolean>;
            historyEnd: KnockoutObservable<string>;
            selectedId: KnockoutObservable<number>;
            typeHistory: KnockoutObservable<number>;

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
                self.typeHistory = ko.observable(nts.uk.ui.windows.getShared("type"));
            }
            addHistoryInfo() {
                var self = this;
                if (self.typeHistory() == TypeHistory.HistoryUnemployee) {
                    self.addHistoryInfoUnemployeeInsurance();
                } else {
                    self.addHistoryInfoAccidentInsurance();
                }
            }
            addHistoryInfoUnemployeeInsurance() {
                var self = this;
                var dfd = $.Deferred<any>();
                var historyInfo: HistoryInfoDto;
                historyInfo = new HistoryInfoDto("historyId001", "companyCode001", null, self.historyStart(), "9999/12", true);
                service.addHistoryInfoUnemployeeInsurance(historyInfo).done(data => {
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
            }

            addHistoryInfoAccidentInsurance() {
                var self = this;
                var dfd = $.Deferred<any>();
                var historyInfo: HistoryInfoDto;
                historyInfo = new HistoryInfoDto("historyId001", "companyCode001", null, self.historyStart(), "9999/12", true);
                service.addHistoryInfoAccidentInsurance(historyInfo).done(data => {
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