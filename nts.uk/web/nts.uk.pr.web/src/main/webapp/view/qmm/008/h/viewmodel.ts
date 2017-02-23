module nts.uk.pr.view.qmm008.h {
    export module viewmodel {
        import commonService = nts.uk.pr.view.qmm008._0.common.service;
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;
        import HealthInsuranceAvgEarnDto = service.model.HealthInsuranceAvgEarnDto;
        import HealthInsuranceAvgEarnValue = service.model.HealthInsuranceAvgEarnValue;
        import HealthInsuranceRateItemModel = nts.uk.pr.view.qmm008.a.viewmodel.HealthInsuranceRateItemModel;
        import HealthInsuranceRateModelofScreenA = nts.uk.pr.view.qmm008.a.viewmodel.HealthInsuranceRateModel;
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;

        export class ScreenModel {
            listAvgEarnLevelMasterSetting: Array<AvgEarnLevelMasterSettingDto>;
            listHealthInsuranceAvgearn: KnockoutObservableArray<HealthInsuranceAvgEarnModel>;
            healthInsuranceRateModel: HealthInsuranceRateModel;

            constructor(dataOfSelectedOffice: InsuranceOfficeItemDto, healthModel: HealthInsuranceRateModelofScreenA) {
                var self = this;
                self.healthInsuranceRateModel = new HealthInsuranceRateModel(
                    dataOfSelectedOffice.code,
                    dataOfSelectedOffice.name,
                    healthModel.historyId, "fake-data", "fake-data",
                    healthModel.rateItems());

                self.listAvgEarnLevelMasterSetting = [];
                self.listHealthInsuranceAvgearn = ko.observableArray<HealthInsuranceAvgEarnModel>([]);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.loadAvgEarnLevelMasterSetting().done(() =>
                    self.loadHealthInsuranceAvgearn().done(() =>
                        dfd.resolve()));
                return dfd.promise();
            }

            /**
             * Load AvgEarnLevelMasterSetting list.
             */
            private loadAvgEarnLevelMasterSetting(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<any>();
                commonService.getAvgEarnLevelMasterSettingList().done(res => {
                    self.listAvgEarnLevelMasterSetting = res;
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Load HealthInsuranceAvgEarn.
             */
            private loadHealthInsuranceAvgearn(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findHealthInsuranceAvgEarn(self.healthInsuranceRateModel.historyId).done(res => {
                    res.forEach(item => {
                        self.listHealthInsuranceAvgearn.push(
                            new HealthInsuranceAvgEarnModel(
                                item.historyId,
                                item.levelCode,
                                new HealthInsuranceAvgEarnValueModel(
                                    item.personalAvg.healthGeneralMny,
                                    item.personalAvg.healthNursingMny,
                                    item.personalAvg.healthBasicMny,
                                    item.personalAvg.healthSpecificMny),
                                new HealthInsuranceAvgEarnValueModel(
                                    item.companyAvg.healthGeneralMny,
                                    item.companyAvg.healthNursingMny,
                                    item.companyAvg.healthBasicMny,
                                    item.companyAvg.healthSpecificMny)
                            )
                        );
                    });
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Collect data from input.
             */
            private collectData(): Array<HealthInsuranceAvgEarnDto> {
                var self = this;
                var data = [];
                self.listHealthInsuranceAvgearn().forEach(item => {
                    data.push(ko.toJS(item));
                });
                return data;
            }

            /**
             * Call service to save healthInsuaranceAvgearn.
             */
            private save(): void {
                var self = this;
                service.updateHealthInsuranceAvgearn(self.collectData()).done(() =>
                    self.closeDialog());
            }

            /**
             * ReCalculate the healthInsuranceAvgearn
             */
            private reCalculate(): void {
                var self = this;
                // clear current listHealthInsuranceAvgearn
                self.listHealthInsuranceAvgearn.removeAll();
                // recalculate listHealthInsuranceAvgearn
                self.listAvgEarnLevelMasterSetting.forEach(item => {
                    self.listHealthInsuranceAvgearn.push(new HealthInsuranceAvgEarnModel(
                        self.healthInsuranceRateModel.historyId,
                        item.code,
                        new HealthInsuranceAvgEarnValueModel(
                            self.healthInsuranceRateModel.rateItems.healthSalaryCompanyGeneral() * item.avgEarn,
                            self.healthInsuranceRateModel.rateItems.healthSalaryCompanyNursing() * item.avgEarn,
                            self.healthInsuranceRateModel.rateItems.healthSalaryCompanyBasic() * item.avgEarn,
                            self.healthInsuranceRateModel.rateItems.healthSalaryCompanySpecific() * item.avgEarn
                        ),
                        new HealthInsuranceAvgEarnValueModel(
                            self.healthInsuranceRateModel.rateItems.healthSalaryPersonalGeneral() * item.avgEarn,
                            self.healthInsuranceRateModel.rateItems.healthSalaryPersonalNursing() * item.avgEarn,
                            self.healthInsuranceRateModel.rateItems.healthSalaryPersonalBasic() * item.avgEarn,
                            self.healthInsuranceRateModel.rateItems.healthSalaryPersonalSpecific() * item.avgEarn
                        )
                    ));
                });
            }

            /**
             * Close dialog.
             */
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }

        export class HealthInsuranceRateModel {
            officeCode: string;
            officeName: string;
            historyId: string;
            startMonth: string;
            endMonth: string;
            rateItems: HealthInsuranceRateItemModel;
            constructor(officeCode: string, officeName: string, historyId: string, startMonth: string, endMonth: string, rateItems: HealthInsuranceRateItemModel) {
                this.officeCode = officeCode;
                this.officeName = officeName;
                this.historyId = historyId;
                this.startMonth = startMonth;
                this.endMonth = endMonth;
                this.rateItems = rateItems;
            }
        }
        export class HealthInsuranceAvgEarnModel {
            historyId: string;
            levelCode: number;
            companyAvg: HealthInsuranceAvgEarnValueModel;
            personalAvg: HealthInsuranceAvgEarnValueModel;
            constructor(historyId: string, levelCode: number, companyAvg: HealthInsuranceAvgEarnValueModel, personalAvg: HealthInsuranceAvgEarnValueModel) {
                this.historyId = historyId;
                this.levelCode = levelCode;
                this.companyAvg = companyAvg;
                this.personalAvg = personalAvg;
            }
        }
        export class HealthInsuranceAvgEarnValueModel {
            healthGeneralMny: KnockoutObservable<number>;
            healthNursingMny: KnockoutObservable<number>;
            healthBasicMny: KnockoutObservable<number>;
            healthSpecificMny: KnockoutObservable<number>;
            constructor(general: number, nursing: number, basic: number, specific: number) {
                this.healthGeneralMny = ko.observable(general);
                this.healthNursingMny = ko.observable(nursing);
                this.healthBasicMny = ko.observable(basic);
                this.healthSpecificMny = ko.observable(specific);
            }
        }

    }
}