module nts.uk.pr.view.qmm008.h {
    export module viewmodel {
        import commonService = nts.uk.pr.view.qmm008._0.common.service;
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;
        import HealthInsuranceAvgEarnDto = service.model.HealthInsuranceAvgEarnDto;
        import HealthInsuranceAvgEarnValue = service.model.HealthInsuranceAvgEarnValue;
        import PensionRateItemModel = nts.uk.pr.view.qmm008.a.viewmodel.PensionRateItemModel;

        export class ScreenModel {
            listAvgEarnLevelMasterSetting: Array<AvgEarnLevelMasterSettingDto>;
            listHealthInsuranceAvgearn: KnockoutObservableArray<HealthInsuranceAvgEarnModel>;
            healthInsuranceRateModel: HealthInsuranceRateModel;
            rateItems: Array<PensionRateItemModel>;

            constructor(dataOfSelectedOffice, healthModel) {
                var self = this;
                self.healthInsuranceRateModel = new HealthInsuranceRateModel();
                self.healthInsuranceRateModel.officeCode = dataOfSelectedOffice.code;
                self.healthInsuranceRateModel.officeName = dataOfSelectedOffice.name;
                self.listAvgEarnLevelMasterSetting = [];
                self.listHealthInsuranceAvgearn = ko.observableArray<HealthInsuranceAvgEarnModel>([]);
                self.rateItems = healthModel.rateItems();
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
                service.findHealthInsuranceAvgEarn('id').done(res => {
                    //self.listHealthInsuranceAvgearn(res);
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
            private save() {
                var self = this;
                service.updateHealthInsuranceAvgearn(this.collectData());
            }

            /**
             * Close dialog.
             */
            private closeDialog() {
                nts.uk.ui.windows.close();
            }
        }

        export class HealthInsuranceRateModel {
            officeCode: string;
            officeName: string;
            startMonth: string;
            endMonth: string;
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
            general: KnockoutObservable<number>;
            nursing: KnockoutObservable<number>;
            basic: KnockoutObservable<number>;
            specific: KnockoutObservable<number>;
            constructor(general: number, nursing: number, basic: number, specific: number) {
                this.general = ko.observable(general);
                this.nursing = ko.observable(nursing);
                this.basic = ko.observable(basic);
                this.specific = ko.observable(specific);
            }
        }

    }
}