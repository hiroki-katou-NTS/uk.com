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

            constructor() {
                var self = this;
                self.healthInsuranceRateModel = ko.observable(new HealthInsuranceRateModel());
                self.listAvgEarnLevelMasterSetting = ko.observableArray([]);
                self.listHealthInsuranceAvgearn = ko.observableArray([]);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                commonService.getAvgEarnLevelMasterSettingList().done(data => {
                    self.listAvgEarnLevelMasterSetting(data);
                    service.findHealthInsuranceRate('a').done(xx => {
                        self.healthInsuranceRateModel().officeCode(xx.officeCode);
                        self.healthInsuranceRateModel().officeName(xx.officeName);
                    });
                    service.findHealthInsuranceAvgEarn('a').done(zz => {
                        self.listHealthInsuranceAvgearn(zz);
                    });
                    dfd.resolve();
                    //self.loadListHealthInsuranceAvgEarn().done(() => {
                    //});
                });
                return dfd.promise();
            }

            private collectData(): Array<HealthInsuranceAvgEarnDto> {
                var self = this;
                var data = [];
                self.listHealthInsuranceAvgearn().forEach(item => {
                    data.push(ko.toJS(item));
                });
                console.log(data);
                return data;
            }

            private save() {
                var self = this;
                //service.save(this.collectData());
            }

            private loadHealthInsuranceAvgEarn(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findHealthInsuranceAvgEarn('a').done(data => {
                    data.forEach(item => {

                    });
                    self.listHealthInsuranceAvgearn(data);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            private closeDialog() {
                nts.uk.ui.windows.close();
            }
        }

        export class HealthInsuranceRateModel {
            companyCode: KnockoutObservable<string>;
            officeCode: KnockoutObservable<string>;
            officeName: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            autoCalculate: KnockoutObservable<boolean>;
            maxAmount: KnockoutObservable<number>;
            constructor() {
                this.companyCode = ko.observable('');
                this.officeCode = ko.observable('');
                this.officeName = ko.observable('');
                this.startMonth = ko.observable('');
                this.endMonth = ko.observable('');
                this.autoCalculate = ko.observable(false);
                this.maxAmount = ko.observable(0);
            }
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