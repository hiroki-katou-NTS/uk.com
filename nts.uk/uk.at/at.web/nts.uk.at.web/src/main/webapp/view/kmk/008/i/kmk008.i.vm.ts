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
                        self.selectedClosingDateAtr( self.operationSetting().selectedClosingDateAtr);
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }

            insertAndUpdate() {
                let self = this;
                nts.uk.ui.block.invisible();
                new service.Service().getData().done(data => {
                    if (data && data.agreementOperationSettingDetailDto) {
                        new service.Service().updateData(new OperationSettingModelUpdate(self.operationSetting()));
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    } else {
                        new service.Service().insertData(new OperationSettingModelUpdate(self.operationSetting()));
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    }
                });
                self.closeDialog();
            }

            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }

        export class OperationSettingModel {
            startingMonth: any;
            numberTimesOverLimitType: any;
            closingDateType: any;
            closingDateAtr: any;
            yearlyWorkTableAtr: any;
            alarmListAtr: any;
            selectedStartingMonth: KnockoutObservable<string>;
            selectedClosingDateType: KnockoutObservable<string>;
            selectedClosingDateAtr: number;
            selectedNumberTimesOverLimitType: KnockoutObservable<number>;
            selectedAlarmListAtr: KnockoutObservable<string>;
            selectedYearlyWorkTableAtr: KnockoutObservable<string>;
            constructor(data: any) {
                let self = this;
                if (data) {
                    self.startingMonth = data.startingMonthEnum;
                    self.numberTimesOverLimitType = data.numberTimesOverLimitTypeEnum;                   
                    self.closingDateType = data.closingDateTypeEnum;
                    self.closingDateAtr = data.closingDateAtrEnum;
                    self.yearlyWorkTableAtr = data.yearlyWorkTableAtrEnum;
                    self.alarmListAtr = data.alarmListAtrEnum;
                    
                    self.selectedStartingMonth = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.startingMonth : "1");
                    self.selectedClosingDateType = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.closingDateType : "1");
                    self.selectedClosingDateAtr = data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.closingDateAtr : 0;
                    self.selectedNumberTimesOverLimitType = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.numberTimesOverLimitType : 6);
                    self.selectedAlarmListAtr = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.alarmListAtr : "0");
                    self.selectedYearlyWorkTableAtr = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.yearlyWorkTableAtr : "0");
                } else {
                    self.startingMonth = new Array();
                    self.closingDateType = new Array();
                    self.closingDateAtr = new Array();
                    self.numberTimesOverLimitType = new Array();
                    self.alarmListAtr = new Array();
                    self.yearlyWorkTableAtr = new Array();
                    self.selectedStartingMonth = ko.observable("1");
                    self.selectedClosingDateType = ko.observable("1");
                    self.selectedClosingDateAtr = 0;
                    self.selectedNumberTimesOverLimitType = ko.observable(6);
                    self.selectedAlarmListAtr = ko.observable("0");
                    self.selectedYearlyWorkTableAtr = ko.observable("0");
                }
            }
        }

        export class OperationSettingModelUpdate {
            startingMonth: number;
            closingDateType: number;
            closingDateAtr: number;
            numberTimesOverLimitType: number;
            alarmListAtr: number;
            yearlyWorkTableAtr: number;
            constructor(data: OperationSettingModel) {
                let self = this;
                self.startingMonth = Number(data.selectedStartingMonth());
                self.closingDateType = Number(data.selectedClosingDateType());
                self.closingDateAtr = Number(data.selectedClosingDateAtr);
                self.numberTimesOverLimitType = Number(data.selectedNumberTimesOverLimitType());
                self.alarmListAtr = Number(data.selectedAlarmListAtr());
                self.yearlyWorkTableAtr = Number(data.selectedYearlyWorkTableAtr());
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
