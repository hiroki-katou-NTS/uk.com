module nts.uk.pr.view.qmm008.i {
    export module viewmodel {
        import commonService = nts.uk.pr.view.qmm008._0.common.service;
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;
        import FunRateItemModel = nts.uk.pr.view.qmm008.c.viewmodel.FunRateItemModel;
        import PensionRateRoundingModel = nts.uk.pr.view.qmm008.c.viewmodel.PensionRateRoundingModel;
        import PensionRateModelFromScreenA = nts.uk.pr.view.qmm008.c.viewmodel.PensionRateModel;

        export class ScreenModel {
            listAvgEarnLevelMasterSetting: Array<AvgEarnLevelMasterSettingDto>;
            listPensionAvgearnModel: KnockoutObservableArray<PensionAvgearnModel>;
            numberEditorCommonOption: KnockoutObservable<nts.uk.ui.option.NumberEditorOption>;

            // expand table var
            leftShow: KnockoutObservable<boolean>;
            rightShow: KnockoutObservable<boolean>;
            leftBtnText: KnockoutComputed<string>;
            rightBtnText: KnockoutComputed<string>;

            pensionRateModel: PensionRateModel;

            constructor(officeName: string, pensionModel: PensionRateModelFromScreenA) {
                var self = this;
                self.listAvgEarnLevelMasterSetting = [];
                self.listPensionAvgearnModel = ko.observableArray<PensionAvgearnModel>([]);
                self.pensionRateModel = new PensionRateModel(
                    pensionModel.historyId,
                    pensionModel.officeCode(),
                    officeName,
                    pensionModel.startMonth(),
                    pensionModel.endMonth(),
                    pensionModel.autoCalculate(),
                    pensionModel.fundRateItems(),
                    pensionModel.roundingMethods(),
                    pensionModel.childContributionRate());
                self.leftShow = ko.observable(true);
                self.rightShow = ko.observable(true);
                self.leftBtnText = ko.computed(function() { if (self.leftShow()) return "—"; return "+"; });
                self.rightBtnText = ko.computed(function() { if (self.rightShow()) return "—"; return "+"; });

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
                var dfd = $.Deferred<any>();
                self.loadAvgEarnLevelMasterSetting().done(() =>
                    self.loadPensionAvgearn().done(() =>
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
             * Load PensionAvgEarn.
             */
            private loadPensionAvgearn(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findPensionAvgearn(self.pensionRateModel.historyId).done(res => {
                    res.forEach(item => {
                        self.listPensionAvgearnModel.push(new PensionAvgearnModel(
                            item.historyId,
                            item.levelCode,
                            /**new PensionAvgearnValueModel(
                                item.companyFund.maleAmount,
                                item.companyFund.femaleAmount,
                                item.companyFund.unknownAmount),*/
                            new PensionAvgearnValueModel(
                                item.companyFundExemption.maleAmount,
                                item.companyFundExemption.femaleAmount,
                                item.companyFundExemption.unknownAmount),
                            new PensionAvgearnValueModel(
                                item.companyPension.maleAmount,
                                item.companyPension.femaleAmount,
                                item.companyPension.unknownAmount),
                            /**new PensionAvgearnValueModel(
                                item.personalFund.maleAmount,
                                item.personalFund.femaleAmount,
                                item.personalFund.unknownAmount),*/
                            new PensionAvgearnValueModel(
                                item.personalFundExemption.maleAmount,
                                item.personalFundExemption.femaleAmount,
                                item.personalFundExemption.unknownAmount),
                            new PensionAvgearnValueModel(
                                item.personalPension.maleAmount,
                                item.personalPension.femaleAmount,
                                item.personalPension.unknownAmount),
                            item.childContributionAmount));
                    });
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Collect data from input.
             */
            private collectData(): any {
                var self = this;
                var data: any = [];
                self.listPensionAvgearnModel().forEach(item => {
                    data.push(ko.toJS(item));
                });
                return data;
            }

            /**
             * Call service to save pensionAvgearn.
             */
            private save(): void {
                var self = this;
                service.updatePensionAvgearn(self.collectData(), self.pensionRateModel.officeCode).done(() =>
                    self.closeDialog());
            }

            /**
             * Button toggle Pension welfare pension.
             */
            private leftToggle(): void {
                this.leftShow(!this.leftShow());
            }

            /**
             * Button toggle right.
             */
            private rightToggle(): void {
                this.rightShow(!this.rightShow());
            }

            /**
             * ReCalculate the listPensionAvgearnModel
             */
            private reCalculate(): void {
                var self = this;
                // Clear current listPensionAvgearnModel
                self.listPensionAvgearnModel.removeAll();

                // Recalculate listPensionAvgearnModel
                self.listAvgEarnLevelMasterSetting.forEach(item => {
                    self.listPensionAvgearnModel.push(self.calculatePensionAvgearn(item));
                });
            }

            /**
             * Calculate the PensionAvgearn
             */
            private calculatePensionAvgearn(levelMasterSetting: AvgEarnLevelMasterSettingDto): PensionAvgearnModel {
                var self = this;
                var model = self.pensionRateModel;
                var rateItems: FunRateItemModel = self.pensionRateModel.fundRateItems;
                var roundingMethods: PensionRateRoundingModel = self.pensionRateModel.roundingMethods;
                var personalRounding = self.convertToRounding(roundingMethods.pensionSalaryPersonalComboBoxSelectedCode());
                var companyRounding = self.convertToRounding(roundingMethods.pensionSalaryCompanyComboBoxSelectedCode()); 
                var rate = levelMasterSetting.avgEarn / 1000;
                var autoCalculate = self.pensionRateModel.autoCalculate;
                if(autoCalculate == AutoCalculate.Auto){
                    return new PensionAvgearnModel(
                        model.historyId,
                        levelMasterSetting.code,
                        new PensionAvgearnValueModel(
                            self.rounding(companyRounding, rateItems.salaryCompanySonExemption() * rate),
                            self.rounding(companyRounding, rateItems.salaryCompanyDaughterExemption() * rate),
                            self.rounding(companyRounding, rateItems.salaryCompanyUnknownExemption() * rate)),
                        new PensionAvgearnValueModel(
                            self.rounding(companyRounding, rateItems.salaryCompanySonBurden() * rate),
                            self.rounding(companyRounding, rateItems.salaryCompanyDaughterBurden() * rate),
                            self.rounding(companyRounding, rateItems.salaryCompanyUnknownBurden() * rate)),
                        new PensionAvgearnValueModel(
                            self.rounding(personalRounding, rateItems.salaryPersonalSonExemption() * rate),
                            self.rounding(personalRounding, rateItems.salaryPersonalDaughterExemption() * rate),
                            self.rounding(personalRounding, rateItems.salaryPersonalUnknownExemption() * rate)),
                        new PensionAvgearnValueModel(
                            self.rounding(personalRounding, rateItems.salaryPersonalSonBurden() * rate),
                            self.rounding(personalRounding, rateItems.salaryPersonalDaughterBurden() * rate),
                            self.rounding(personalRounding, rateItems.salaryPersonalUnknownBurden() * rate)),
                        model.childContributionRate() * rate
                    );
                }
                else {
                    return new PensionAvgearnModel(
                        model.historyId,
                        levelMasterSetting.code,
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        model.childContributionRate() * rate
                    );
                }
            }
            
            // rounding 
            private rounding(roudingMethod: string,roundValue: number){
                var self = this;
                var backupValue = roundValue;
                switch(roudingMethod){
                    case Rounding.ROUNDUP: return Math.ceil(backupValue);
                    case Rounding.TRUNCATION: return Math.floor(backupValue);
                    case Rounding.ROUNDDOWN:
                        if ((backupValue * 10) % 10 > 5)
                            return Math.ceil(backupValue);
                        else
                            return Math.floor(backupValue);
                    case Rounding.DOWN4_UP5: return self.roudingDownUp(backupValue, 4);
                    case Rounding.DOWN5_UP6: return self.roudingDownUp(backupValue, 5);
                }
            }
            private roudingDownUp(value: number, down: number) {
                var newVal = Math.round(value * 10) / 10;
                if ((newVal * 10) % 10 > down)
                    return Math.ceil(value);
                else
                    return Math.floor(value);
            }
            
              //value to string rounding
            public convertToRounding(stringValue: string) {
                switch (stringValue) {
                    case "0": return Rounding.ROUNDUP;
                    case "1": return Rounding.TRUNCATION;
                    case "2": return Rounding.DOWN4_UP5;
                    case "3": return Rounding.ROUNDDOWN;
                    case "4": return Rounding.DOWN5_UP6;
                    default: return Rounding.ROUNDUP;
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
         * PensionRate Model
         */
        export class PensionRateModel {
            historyId: string;
            officeCode: string;
            officeName: string;
            startMonth: string;
            endMonth: string;
            autoCalculate: number;
            fundRateItems: FunRateItemModel;
            roundingMethods: PensionRateRoundingModel
            childContributionRate: KnockoutObservable<number>;
            constructor(
                historyId: string,
                officeCode: string,
                officeName: string,
                startMonth: string,
                endMonth: string,
                autoCalculate: number,
                rateItems: FunRateItemModel,
                roundingMethods: PensionRateRoundingModel,
                childContributionRate: number) {
                this.historyId = historyId;
                this.officeCode = officeCode;
                this.officeName = officeName;
                this.startMonth = startMonth;
                this.endMonth = endMonth;
                this.autoCalculate = autoCalculate;
                this.fundRateItems = rateItems;
                this.roundingMethods = roundingMethods;
                this.childContributionRate = ko.observable(childContributionRate);
            }
        }

        /**
         * PensionAvgearn Model
         */
        export class PensionAvgearnModel {
            historyId: string;
            levelCode: number;
            //companyFund: PensionAvgearnValueModel;
            companyFundExemption: PensionAvgearnValueModel;
            companyPension: PensionAvgearnValueModel;
            //personalFund: PensionAvgearnValueModel;
            personalFundExemption: PensionAvgearnValueModel;
            personalPension: PensionAvgearnValueModel;
            childContributionAmount: KnockoutObservable<number>;
            constructor(
                historyId: string,
                levelCode: number,
                //companyFund: PensionAvgearnValueModel,
                companyFundExemption: PensionAvgearnValueModel,
                companyPension: PensionAvgearnValueModel,
                //personalFund: PensionAvgearnValueModel,
                personalFundExemption: PensionAvgearnValueModel,
                personalPension: PensionAvgearnValueModel,
                childContributionAmount: number) {
                this.historyId = historyId;
                this.levelCode = levelCode;
                //this.companyFund = companyFund;
                this.companyFundExemption = companyFundExemption;
                this.companyPension = companyPension;
                //this.personalFund = personalFund;
                this.personalFundExemption = personalFundExemption;
                this.personalPension = personalPension;
                this.childContributionAmount = ko.observable(childContributionAmount);
            }
        }

        /**
         * PensionAvgearnValue Model
         */
        export class PensionAvgearnValueModel {
            maleAmount: KnockoutObservable<number>;
            femaleAmount: KnockoutObservable<number>;
            unknownAmount: KnockoutObservable<number>;
            constructor(maleAmount: number, femaleAmount: number, unknownAmount: number) {
                this.maleAmount = ko.observable(maleAmount);
                this.femaleAmount = ko.observable(femaleAmount);
                this.unknownAmount = ko.observable(unknownAmount);
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