module nts.uk.pr.view.qmm008.h {
    export module viewmodel {
        import commonService = nts.uk.pr.view.qmm008._0.common.service;
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;
        import HealthInsuranceAvgEarnDto = service.model.HealthInsuranceAvgEarnDto;
        import HealthInsuranceAvgEarnValue = service.model.HealthInsuranceAvgEarnValue;
        import HealthInsuranceRateItemModel = nts.uk.pr.view.qmm008.b.viewmodel.HealthInsuranceRateItemModel;
        import HealthInsuranceRoundingModel = nts.uk.pr.view.qmm008.b.viewmodel.HealthInsuranceRoundingModel;
        import HealthInsuranceRateModelofScreenA = nts.uk.pr.view.qmm008.b.viewmodel.HealthInsuranceRateModel;
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.b.service.model.finder.InsuranceOfficeItemDto;

        export class ScreenModel {
            listAvgEarnLevelMasterSetting: Array<AvgEarnLevelMasterSettingDto>;
            listHealthInsuranceAvgearn: KnockoutObservableArray<HealthInsuranceAvgEarnModel>;
            healthInsuranceRateModel: HealthInsuranceRateModel;
            numberEditorCommonOption: KnockoutObservable<nts.uk.ui.option.NumberEditorOption>;
            errorList: KnockoutObservableArray<any>;
            dirty: nts.uk.ui.DirtyChecker;
            constructor(officeName: string, healthModel: HealthInsuranceRateModelofScreenA) {
                var self = this;
                self.healthInsuranceRateModel = new HealthInsuranceRateModel(
                    healthModel.officeCode(),
                    officeName,
                    healthModel.historyId,
                    healthModel.startMonth(),
                    healthModel.endMonth(),
                    healthModel.autoCalculate(),
                    healthModel.rateItems(),
                    healthModel.roundingMethods());

                self.listAvgEarnLevelMasterSetting = [];
                self.listHealthInsuranceAvgearn = ko.observableArray<HealthInsuranceAvgEarnModel>([]);

                // Common NtsNumberEditor Option
                self.numberEditorCommonOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3
                }));
                //dirty check
                self.dirty = new nts.uk.ui.DirtyChecker(ko.observable(''));
                self.errorList = ko.observableArray([
                    { messageId: "AL001", message: "変更された内容が登録されていません。\r\n よろしいですか。" },
                    { messageId: "AL002", message: "データを削除します。\r\nよろしいですか？" },
                ]);
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
                    self.dirty = new nts.uk.ui.DirtyChecker(self.listHealthInsuranceAvgearn);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Collect data from input.
             */
            private collectData(): Array<HealthInsuranceAvgEarnDto> {
                var self = this;
                var data: Array<HealthInsuranceAvgEarnDto> = [];
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
                // Return if has error.
                if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                    return;
                }
                service.updateHealthInsuranceAvgearn(self.collectData(), self.healthInsuranceRateModel.officeCode).done(() =>
                    self.closeDialog());
            }

            private clearError(): void {
                $('.has-error').ntsError('clear');
            }

            /**
             * ReCalculate the healthInsuranceAvgearn list
             */
            private reCalculate(): void {
                var self = this;
                self.clearError();
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
                var roundingMethods: HealthInsuranceRoundingModel = self.healthInsuranceRateModel.roundingMethods;
                var personalRounding = self.convertToRounding(roundingMethods.healthSalaryPersonalComboBoxSelectedCode());
                var companyRounding = self.convertToRounding(roundingMethods.healthSalaryCompanyComboBoxSelectedCode());
                var rate = levelMasterSetting.avgEarn / 1000;
                var autoCalculate = self.healthInsuranceRateModel.autoCalculate;
                if (autoCalculate == AutoCalculate.Auto) {
                    return new HealthInsuranceAvgEarnModel(
                        historyId,
                        levelMasterSetting.code,
                        new HealthInsuranceAvgEarnValueModel(
                            self.rounding(personalRounding, rateItems.healthSalaryPersonalGeneral() * rate,Number.One),
                            self.rounding(personalRounding, rateItems.healthSalaryPersonalNursing() * rate,Number.One),
                            self.rounding(personalRounding, rateItems.healthSalaryPersonalBasic() * rate,Number.Three),
                            self.rounding(personalRounding, rateItems.healthSalaryPersonalSpecific() * rate,Number.Three)
                        ),
                        new HealthInsuranceAvgEarnValueModel(
                            self.rounding(companyRounding, rateItems.healthSalaryCompanyGeneral() * rate,Number.One),
                            self.rounding(companyRounding, rateItems.healthSalaryCompanyNursing() * rate,Number.One),
                            self.rounding(companyRounding, rateItems.healthSalaryCompanyBasic() * rate,Number.Three),
                            self.rounding(companyRounding, rateItems.healthSalaryCompanySpecific() * rate,Number.Three)
                        )
                    );
                }
                else {
                    return new HealthInsuranceAvgEarnModel(
                        historyId,
                        levelMasterSetting.code,
                        new HealthInsuranceAvgEarnValueModel(Number.Zero,Number.Zero,Number.Zero,Number.Zero),
                        new HealthInsuranceAvgEarnValueModel(Number.Zero,Number.Zero,Number.Zero,Number.Zero)
                    );
                }
            }
            
            // rounding 
            private rounding(roudingMethod: string,roundValue: number,roundType: number): number{
                var self = this;
                var getLevel = Math.pow(10,roundType);
                var backupValue = roundValue*(getLevel/10);
                switch(roudingMethod){
                    case Rounding.ROUNDUP: return Math.ceil(backupValue)/(getLevel/10);
                    case Rounding.TRUNCATION: return Math.floor(backupValue)/(getLevel/10);
                    case Rounding.ROUNDDOWN:
                        if ((backupValue * getLevel) % 10 > 5)
                            return (Math.ceil(backupValue))/(getLevel/10);
                        else
                            return Math.floor(backupValue)/(getLevel/10);
                    case Rounding.DOWN4_UP5: return self.roudingDownUp(backupValue, 4)/(getLevel/10);
                    case Rounding.DOWN5_UP6: return self.roudingDownUp(backupValue, 5)/(getLevel/10);
                }
            }
            private roudingDownUp(value: number, down: number): number {
                var newVal = Math.round(value * 10) / 10;
                if ((newVal * 10) % 10 > down)
                    return Math.ceil(value);
                else
                    return Math.floor(value);
            }

              //value to string rounding
            public convertToRounding(stringValue: string) {
                switch (stringValue) {
                    case "0": return Rounding.TRUNCATION;
                    case "1": return Rounding.ROUNDUP;
                    case "2": return Rounding.DOWN4_UP5;
                    case "3": return Rounding.DOWN5_UP6;
                    case "4": return Rounding.ROUNDDOWN;
                    default: return Rounding.TRUNCATION;
                }
            }

            private closeDialogWithDirtyCheck(): void {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm(self.errorList()[0].message).ifYes(function() {
                        self.closeDialog();
                        self.dirty.reset();
                    }).ifCancel(function() {
                    });
                }
                else {
                    self.closeDialog();
                }
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
            autoCalculate: number;
            rateItems: HealthInsuranceRateItemModel;
            roundingMethods: HealthInsuranceRoundingModel;
            constructor(officeCode: string, officeName: string, historyId: string, startMonth: string, endMonth: string,autoCalculate: number,rateItems: HealthInsuranceRateItemModel,roundingMethods: HealthInsuranceRoundingModel) {
                this.officeCode = officeCode;
                this.officeName = officeName;
                this.historyId = historyId;
                this.startMonth = startMonth;
                this.endMonth = endMonth;
                this.autoCalculate = autoCalculate;
                this.rateItems = rateItems;
                this.roundingMethods = roundingMethods;
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
        export class Rounding {
            static ROUNDUP = 'RoundUp';
            static TRUNCATION = 'Truncation';
            static ROUNDDOWN = 'RoundDown';
            static DOWN5_UP6 = 'Down5_Up6';
            static DOWN4_UP5 = 'Down4_Up5'
        }
        export enum Number{
                Zero = 0,
                One = 1,
                Three = 3
        }
        export enum AutoCalculate{
                Auto = 0,
                Manual = 1
        }
    }
}