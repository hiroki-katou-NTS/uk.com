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
            numberEditorCommonOption: KnockoutObservable<nts.uk.ui.option.NumberEditorOption>;

            constructor(dataOfSelectedOffice: InsuranceOfficeItemDto, healthModel: HealthInsuranceRateModelofScreenA) {
                var self = this;
                self.healthInsuranceRateModel = new HealthInsuranceRateModel(
                    dataOfSelectedOffice.code,
                    dataOfSelectedOffice.name,
                    healthModel.historyId,
                    healthModel.startMonth(),
                    healthModel.endMonth(),
                    healthModel.rateItems());

                self.listAvgEarnLevelMasterSetting = [];
                self.listHealthInsuranceAvgearn = ko.observableArray<HealthInsuranceAvgEarnModel>([]);

                // Common NtsNumberEditor Option
                self.numberEditorCommonOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3
                }));
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
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
                var dfd = $.Deferred<void>();
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
                var dfd = $.Deferred<void>();
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
                service.updateHealthInsuranceAvgearn(self.collectData(), self.healthInsuranceRateModel.officeCode).done(() =>
                    self.closeDialog());
            }

            /**
             * ReCalculate the healthInsuranceAvgearn list
             */
            private reCalculate(): void {
                var self = this;
                // Clear current listHealthInsuranceAvgearn
                self.listHealthInsuranceAvgearn.removeAll();
                // Recalculate listHealthInsuranceAvgearn
                self.listAvgEarnLevelMasterSetting.forEach(item => {
                    self.listHealthInsuranceAvgearn.push(self.calculateHealthInsuranceAvgEarnModel(item));
                });
            }

            /**
             * Calculate the healthInsuranceAvgearn
             */
            private calculateHealthInsuranceAvgEarnModel(levelMasterSetting: AvgEarnLevelMasterSettingDto): HealthInsuranceAvgEarnModel {
                var self = this;
                var historyId = self.healthInsuranceRateModel.historyId;
                var rateItems: HealthInsuranceRateItemModel = self.healthInsuranceRateModel.rateItems;
                var rate = levelMasterSetting.avgEarn / 1000;

                return new HealthInsuranceAvgEarnModel(
                    historyId,
                    levelMasterSetting.code,
                    new HealthInsuranceAvgEarnValueModel(
                        rateItems.healthSalaryCompanyGeneral() * rate,
                        rateItems.healthSalaryCompanyNursing() * rate,
                        rateItems.healthSalaryCompanyBasic() * rate,
                        rateItems.healthSalaryCompanySpecific() * rate
                    ),
                    new HealthInsuranceAvgEarnValueModel(
                        rateItems.healthSalaryPersonalGeneral() * rate,
                        rateItems.healthSalaryPersonalNursing() * rate,
                        rateItems.healthSalaryPersonalBasic() * rate,
                        rateItems.healthSalaryPersonalSpecific() * rate
                    )
                );
            }

            /**
             * Close dialog.
             */
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }

        /**
         * HealthInsuranceRate Model
         */
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

        /**
         * HealthInsuranceAvgEarn Model
         */
        export class HealthInsuranceAvgEarnModel {
            historyId: string;
            levelCode: number;
            companyAvg: HealthInsuranceAvgEarnValueModel;
            personalAvg: HealthInsuranceAvgEarnValueModel;
            constructor(historyId: string, levelCode: number, personalAvg: HealthInsuranceAvgEarnValueModel, companyAvg: HealthInsuranceAvgEarnValueModel) {
                this.historyId = historyId;
                this.levelCode = levelCode;
                this.companyAvg = companyAvg;
                this.personalAvg = personalAvg;
            }
        }

        /**
         * HealthInsuranceAvgEarnValue Model
         */
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