module nts.uk.pr.view.qmm011.f {
    import option = nts.uk.ui.option;
    import TypeHistory = nts.uk.pr.view.qmm011.a.service.model.TypeHistory;
    import AccidentInsuranceRateDeleteDto = service.model.AccidentInsuranceRateDeleteDto;
    import UnemployeeInsuranceDeleteDto = service.model.UnemployeeInsuranceDeleteDto;
    export module viewmodel {
        export class ScreenModel {
            selectModel: KnockoutObservableArray<BoxModel>;
            selectedId: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;
            textEditorOption: KnockoutObservable<any>;
            historyId: KnockoutObservable<string>;
            historyStart: KnockoutObservable<number>;
            historyEnd: KnockoutObservable<number>;
            typeHistory: KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.selectModel = ko.observableArray<BoxModel>([
                    new BoxModel(1, '履歴を削除する'),//delete
                    new BoxModel(2, '履歴を修正する')//update
                ]);
                self.selectedId = ko.observable(2);
                self.enable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.typeHistory = ko.observable(nts.uk.ui.windows.getShared("type"));
                self.historyStart = ko.observable(nts.uk.ui.windows.getShared("historyStart"));
                self.historyId = ko.observable(nts.uk.ui.windows.getShared("historyId"));
                self.historyEnd = ko.observable(nts.uk.ui.windows.getShared("historyEnd"));
            }
            fwupdateHistoryInfoUnemployeeInsurance() {
                var self = this;
                var unemployeeInsuranceDeleteDto: UnemployeeInsuranceDeleteDto;
                unemployeeInsuranceDeleteDto = new UnemployeeInsuranceDeleteDto();
                unemployeeInsuranceDeleteDto.code = self.historyId();
                unemployeeInsuranceDeleteDto.version = 11;
                var updateHistoryInfoModel: UpdateHistoryInfoModel;
                updateHistoryInfoModel = new UpdateHistoryInfoModel();
                updateHistoryInfoModel.typeUpdate = self.selectedId();
                updateHistoryInfoModel.historyId = self.historyId();
                updateHistoryInfoModel.historyStart = self.historyStart();
                updateHistoryInfoModel.historyEnd = self.historyEnd();
                if (self.selectedId() == 1) {
                    //delete
                    service.deleteUnemployeeInsurance(unemployeeInsuranceDeleteDto).done(data => {
                        nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                        nts.uk.ui.windows.close();
                    }).fail(function(error) {
                        nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                        nts.uk.ui.windows.close();
                    })
                } else {
                    nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                    nts.uk.ui.windows.close();
                }

            }
            fwupdateHistoryInfoAccidentInsurance() {
                var self = this;
                //delete
                var accidentInsuranceRateDeleteDto: AccidentInsuranceRateDeleteDto;
                accidentInsuranceRateDeleteDto = new AccidentInsuranceRateDeleteDto();
                accidentInsuranceRateDeleteDto.code = self.historyId();
                accidentInsuranceRateDeleteDto.version = 11;
                var updateHistoryInfoModel: UpdateHistoryInfoModel;
                updateHistoryInfoModel = new UpdateHistoryInfoModel();
                updateHistoryInfoModel.typeUpdate = self.selectedId();
                updateHistoryInfoModel.historyId = self.historyId();
                updateHistoryInfoModel.historyStart = self.historyStart();
                updateHistoryInfoModel.historyEnd = self.historyEnd();
                if (self.selectedId() == 1) {
                    service.deleteAccidentInsuranceRate(accidentInsuranceRateDeleteDto).done(data => {
                        nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                        nts.uk.ui.windows.close();
                    }).fail(function(error) {
                        nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                        nts.uk.ui.windows.close();
                    })
                } else {
                    nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                    nts.uk.ui.windows.close();
                }
            }
            fwupdateHistoryInfo() {
                var self = this;
                if (self.typeHistory() == TypeHistory.HistoryUnemployee) {
                    self.fwupdateHistoryInfoUnemployeeInsurance();
                } else {
                    self.fwupdateHistoryInfoAccidentInsurance();
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
        export class UpdateHistoryInfoModel {
            typeUpdate: number;//1 delete, 2 update
            historyId: string;
            historyStart: number;
            historyEnd: number;
        }

    }
}