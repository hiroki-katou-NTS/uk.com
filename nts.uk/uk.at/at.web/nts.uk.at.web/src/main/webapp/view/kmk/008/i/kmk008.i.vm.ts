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

                new service.Service().getData().done(data => {
                    if (data) {
                        self.operationSetting(new OperationSettingModel(data));
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }

            insertAndUpdate() {
                let self = this;

                new service.Service().getData().done(data => {
                    if (data) {
                        new service.Service().updateData(new OperationSettingModelUpdate(self.operationSetting()));
                    } else {
                        new service.Service().insertData(new OperationSettingModelUpdate(self.operationSetting()));
                    }
                });
            }

            closeDialog(): void {
                nts.uk.ui.windows.close();
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
                    self.startingMonth = ko.observableArray(data.startingMonthEnum);
                    self.numberTimesOverLimitType = ko.observableArray(data.numberTimesOverLimitTypeEnum);
                    self.closingDateType = ko.observableArray(data.closingDateTypeEnum);
                    self.closingDateAtr = ko.observableArray(data.closingDateAtrEnum);
                    self.yearlyWorkTableAtr = ko.observableArray(data.yearlyWorkTableAtrEnum);
                    self.alarmListAtr = ko.observableArray(data.alarmListAtrEnum);

                    self.selectedStartingMonth = ko.observable(data.startingMonth);
                    self.selectedClosingDateType = ko.observable(data.closingDateType);
                    self.selectedClosingDateAtr = 0;
                    self.selectedNumberTimesOverLimitType = ko.observable(data.numberTimesOverLimitType);
                    self.selectedAlarmListAtr = ko.observable(data.alarmListAtr);
                    self.selectedYearlyWorkTableAtr = ko.observable(data.yearlyWorkTableAtr);
                } else {
                    self.startingMonth = ko.observableArray([]);
                    self.closingDateType = ko.observableArray([]);
                    self.closingDateAtr = ko.observableArray([]);
                    self.numberTimesOverLimitType = ko.observableArray([]);
                    self.alarmListAtr = ko.observableArray([]);
                    self.yearlyWorkTableAtr = ko.observableArray([]);
                    self.selectedStartingMonth = ko.observable("1");
                    self.selectedClosingDateType = ko.observable("1");
                    self.selectedClosingDateAtr = 0;
                    self.selectedNumberTimesOverLimitType = ko.observable("1");
                    self.selectedAlarmListAtr = ko.observable("1");
                    self.selectedYearlyWorkTableAtr = ko.observable("1");
                }
            }
        }

        export class OperationSettingModelUpdate {
            selectedStartingMonth: number;
            selectedClosingDateType: number;
            selectedClosingDateAtr: number;
            selectedNumberTimesOverLimitType: number;
            selectedAlarmListAtr: number;
            selectedYearlyWorkTableAtr: number;
            constructor(data: OperationSettingModel) {
                let self = this;
                self.selectedStartingMonth = Number(data.startingMonth);
                self.selectedClosingDateType = Number(data.selectedClosingDateType);
                self.selectedClosingDateAtr = Number(data.selectedClosingDateAtr);
                self.selectedNumberTimesOverLimitType = Number(data.numberTimesOverLimitType);
                self.selectedAlarmListAtr = Number(data.selectedAlarmListAtr);
                self.selectedYearlyWorkTableAtr = Number(data.selectedYearlyWorkTableAtr);
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
