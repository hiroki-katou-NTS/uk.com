module nts.uk.pr.view.qmm008.d {
    export module viewmodel {
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;
        export class ScreenModel {
            listAvgEarnLevelMasterSetting: KnockoutObservableArray<any>;
            listHealthInsuranceAvgearn: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.listAvgEarnLevelMasterSetting = ko.observableArray([
                    { code: 1, healthLevel: 1, pensionLevel: 1, avgEarn: 58000, salMin: 0, salMax: 63000 },
                    { code: 2, healthLevel: 2, pensionLevel: 1, avgEarn: 68000, salMin: 63000, salMax: 73000 }
                ]);
                self.listHealthInsuranceAvgearn = ko.observableArray([
                    { historyId: 1, levelCode: 1, companyAvg: { general: 123, nursing: 345, basic: 567, specific: 678 }, personalAvg: { general: 123, nursing: 345, basic: 567, specific: 678 } },
                    { historyId: 2, levelCode: 2, companyAvg: { general: 444, nursing: 222, basic: 111, specific: 333 }, personalAvg: { general: 222, nursing: 444, basic: 555, specific: 666 } }
                ]);

            }

            closeDialog() {
                nts.uk.ui.windows.close();
            }
        }

        export class HealthInsuranceAvgEarnValue {
            general: number;
            nursing: number;
            basic: number;
            specific: number;
        }
        export class HealthInsuranceAvgEarn {
            historyId: string;
            levelCode: number;
            companyAvg: HealthInsuranceAvgEarnValue;
            personalAvg: HealthInsuranceAvgEarnValue;
        }
    }
}