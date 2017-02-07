module nts.uk.pr.view.qmm008.h {
    export module viewmodel {
        import commonService = nts.uk.pr.view.qmm008._0.common.service;
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;
        import HealthInsuranceAvgEarnDto = service.model.HealthInsuranceAvgEarnDto;
        import HealthInsuranceAvgEarnValue = service.model.HealthInsuranceAvgEarnValue;

        export class ScreenModel {
            listAvgEarnLevelMasterSetting: KnockoutObservableArray<any>;
            listHealthInsuranceAvgearn: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.listAvgEarnLevelMasterSetting = ko.observableArray();
                self.listHealthInsuranceAvgearn = ko.observableArray([
                    { historyId: 1, levelCode: 1, companyAvg: { general: 123, nursing: 345, basic: 567, specific: 678 }, personalAvg: { general: 123, nursing: 345, basic: 567, specific: 678 } },
                    { historyId: 2, levelCode: 2, companyAvg: { general: 444, nursing: 222, basic: 111, specific: 333 }, personalAvg: { general: 222, nursing: 444, basic: 555, specific: 666 } }
                ]);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                commonService.getAvgEarnLevelMasterSettingList().done(data => {
                    self.listAvgEarnLevelMasterSetting(data);
                    service.findHealthInsuranceRate('a').done(data => { });
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