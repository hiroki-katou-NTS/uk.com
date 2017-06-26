module nts.uk.at.view.kmk008.i {
    export module viewmodel {

        export class ScreenModel {
            operationSetting: KnockoutObservable<OperationSettingModel>;
            selectedClosingDateAtr: KnockoutObservable<number>;
            isEnableClosingDateType: KnockoutObservable<boolean>;
            isEnableTargetSetting: KnockoutObservable<boolean>;
            constructor() {
                let self = this;
                self._init();
                self.selectedClosingDateAtr.subscribe(function(newValue) {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    self.operationSetting().selectedClosingDateAtr = newValue
                    if (newValue == 0) {
                        self.isEnableClosingDateType(false);
                        self.isEnableTargetSetting(false)
                        return;
                    }
                    self.isEnableClosingDateType(true);
                    self.isEnableTargetSetting(true);
                });

            }
            _init(): void {
                let self = this;
                self.operationSetting = ko.observable(new OperationSettingModel(null));
                self.selectedClosingDateAtr = ko.observable(0);
                //init enable
                self.isEnableClosingDateType = ko.observable(false);
                self.isEnableTargetSetting = ko.observable(false);
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
        }

        export class OperationSettingModel {
            startingMonth: KnockoutObservableArray<any>;
            numberTimesOverLimitType: KnockoutObservableArray<any>;
            closingDateType: KnockoutObservableArray<any>;
            closingDateAtr: KnockoutObservableArray<any>;
            yearlyWorkTableAtr: KnockoutObservableArray<any>;
            alarmListAtr: KnockoutObservableArray<any>;
            selectedStartingMonth: KnockoutObservable<string>;
            selectedClosingDateType: KnockoutObservable<string>;
            selectedClosingDateAtr: number;
            selectedNumberTimesOverLimitType: KnockoutObservable<string>;
            selectedAlarmListAtr: KnockoutObservable<string>;
            selectedYearlyWorkTableAtr: KnockoutObservable<string>;
            constructor(data: any) {
                let self = this;
                if (data) {
                    self.startingMonth = ko.observableArray(data.startingMonth);
                    self.numberTimesOverLimitType = ko.observableArray(data.numberTimesOverLimitType);
                    self.closingDateType = ko.observableArray(data.closingDateType);
                    self.closingDateAtr = ko.observableArray(data.closingDateAtr);
                    self.yearlyWorkTableAtr = ko.observableArray(data.yearlyWorkTableAtr);
                    self.alarmListAtr = ko.observableArray(data.alarmListAtr);

                    self.selectedStartingMonth = ko.observableArray(data.selectedStartingMonth);
                    self.selectedClosingDateType = ko.observableArray(data.selectedClosingDateType);
                    self.selectedClosingDateAtr = 0;
                    self.selectedNumberTimesOverLimitType = ko.observableArray(data.selectedNumberTimesOverLimitType);
                    self.selectedAlarmListAtr = ko.observableArray(data.selectedAlarmListAtr);
                    self.selectedYearlyWorkTableAtr = ko.observableArray(data.selectedYearlyWorkTableAtr);
                } else {
                    self.startingMonth = ko.observableArray([
                        new ComboBoxModel("1", nts.uk.resource.getText("KMK008_32")),
                        new ComboBoxModel("2", nts.uk.resource.getText("KMK008_32")),
                    ]);
                    self.closingDateType = ko.observableArray([
                        new ComboBoxModel("1", nts.uk.resource.getText("KMK008_33")),
                        new ComboBoxModel("2", nts.uk.resource.getText("KMK008_33")),
                    ]);
                    self.closingDateAtr = ko.observableArray([
                        new RadioModel(0, "勤怠の締め日と同じ"),
                        new RadioModel(1, "締め日を指定"),
                    ]);
                    self.numberTimesOverLimitType = ko.observableArray([
                        new ComboBoxModel("1", nts.uk.resource.getText("KMK008_34")),
                        new ComboBoxModel("2", nts.uk.resource.getText("KMK008_34")),
                    ]);
                    self.alarmListAtr = ko.observableArray([
                        new ComboBoxModel("1", nts.uk.resource.getText("KMK008_35")),
                        new ComboBoxModel("2", nts.uk.resource.getText("KMK008_34")),
                    ]);
                    self.yearlyWorkTableAtr = ko.observableArray([
                        new ComboBoxModel("1", nts.uk.resource.getText("KMK008_35")),
                        new ComboBoxModel("2", nts.uk.resource.getText("KMK008_34")),
                    ]);
                    self.selectedStartingMonth = ko.observable("1");
                    self.selectedClosingDateType = ko.observable("1");
                    self.selectedClosingDateAtr = 0;
                    self.selectedNumberTimesOverLimitType = ko.observable("1");
                    self.selectedAlarmListAtr = ko.observable("1");
                    self.selectedYearlyWorkTableAtr = ko.observable("1");
                }
            }
        }

        export class ComboBoxModel {
            value: string;
            localizedName: string;
            label: string;
            constructor(value: string, localizedName: string) {
                this.value = value;
                this.localizedName = localizedName;
            }
        }

        class RadioModel {
            value: number;
            localizedName: string;
            constructor(value, localizedName) {
                let self = this;
                self.value = value;
                self.localizedName = localizedName;
            }
        }

    }
}
