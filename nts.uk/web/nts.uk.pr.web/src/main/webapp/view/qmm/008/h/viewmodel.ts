module nts.uk.pr.view.qmm008.h {
    export module viewmodel {
        import commonService = nts.uk.pr.view.qmm008._0.common.service;
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;
        import HealthInsuranceAvgEarnDto = service.model.HealthInsuranceAvgEarnDto;
        import HealthInsuranceAvgEarnValue = service.model.HealthInsuranceAvgEarnValue;

        export class ScreenModel {
            listAvgEarnLevelMasterSetting: KnockoutObservableArray<any>;
            listHealthInsuranceAvgearn: KnockoutObservableArray<any>;
            healthInsuranceRateModel: KnockoutObservable<HealthInsuranceRateModel>;
            rateItems: KnockoutObservableArray<any>;
            roundingMethods: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.healthInsuranceRateModel = ko.observable(new HealthInsuranceRateModel());
                self.listAvgEarnLevelMasterSetting = ko.observableArray([]);
                self.listHealthInsuranceAvgearn = ko.observableArray([]);
                self.rateItems = ko.observableArray([]);
                self.roundingMethods = ko.observableArray([]);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.loadAvgEarnLevelMasterSetting().done(() =>
                    self.loadHealthInsuranceRate().done(() =>
                        self.loadHealthInsuranceAvgearn().done(() =>
                            dfd.resolve())));
                return dfd.promise();
            }

            /**
             * Load AvgEarnLevelMasterSetting list.
             */
            private loadAvgEarnLevelMasterSetting(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                commonService.getAvgEarnLevelMasterSettingList().done(res => {
                    self.listAvgEarnLevelMasterSetting(res);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Load healthInsuranceRate.
             */
            private loadHealthInsuranceRate(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findHealthInsuranceRate('id').done(res => {
                    self.healthInsuranceRateModel().officeCode = res.officeCode;
                    self.healthInsuranceRateModel().officeName = res.officeName;
                    self.rateItems(res.ratesItem);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Load HealthInsuranceAvgEarn.
             */
            private loadHealthInsuranceAvgearn(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findHealthInsuranceAvgEarn('id').done(res => {
                    self.listHealthInsuranceAvgearn(res);
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
        }
        export class HealthInsuranceAvgEarnValueModel {
            general: KnockoutObservable<number>;
            nursing: KnockoutObservable<number>;
            basic: KnockoutObservable<number>;
            specific: KnockoutObservable<number>;
        }

    }
}