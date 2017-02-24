module nts.uk.pr.view.qmm008.i {
    export module viewmodel {
        import commonService = nts.uk.pr.view.qmm008._0.common.service;
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;
        import FunRateItemModel = nts.uk.pr.view.qmm008.a.viewmodel.FunRateItemModel;
        import PensionRateModelFromScreenA = nts.uk.pr.view.qmm008.a.viewmodel.PensionRateModel;
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;

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

            constructor(dataOfSelectedOffice: InsuranceOfficeItemDto, pensionModel: PensionRateModelFromScreenA) {
                var self = this;
                self.listAvgEarnLevelMasterSetting = [];
                self.listPensionAvgearnModel = ko.observableArray<PensionAvgearnModel>([]);
                self.pensionRateModel = new PensionRateModel(
                    pensionModel.historyId,
                    dataOfSelectedOffice.code,
                    dataOfSelectedOffice.name,
                    pensionModel.startMonth(),
                    pensionModel.endMonth(),
                    pensionModel.fundRateItems(),
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
                service.findPensionAvgearn('11').done(res => { //fixed id
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
                var data = [];
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
                var rate = levelMasterSetting.avgEarn / 1000;

                return new PensionAvgearnModel(
                    '11', // fake id.
                    levelMasterSetting.code,
                    new PensionAvgearnValueModel(
                        rateItems.salaryCompanySonExemption() * rate,
                        rateItems.salaryCompanyDaughterExemption() * rate,
                        rateItems.salaryCompanyUnknownExemption() * rate),
                    new PensionAvgearnValueModel(
                        rateItems.salaryCompanySonBurden() * rate,
                        rateItems.salaryCompanyDaughterBurden() * rate,
                        rateItems.salaryCompanyUnknownBurden() * rate),
                    new PensionAvgearnValueModel(
                        rateItems.salaryPersonalSonExemption() * rate,
                        rateItems.salaryPersonalDaughterExemption() * rate,
                        rateItems.salaryPersonalUnknownExemption() * rate),
                    new PensionAvgearnValueModel(
                        rateItems.salaryPersonalSonBurden() * rate,
                        rateItems.salaryPersonalDaughterBurden() * rate,
                        rateItems.salaryPersonalUnknownBurden() * rate),
                    model.childContributionRate() * rate
                );
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
            fundRateItems: FunRateItemModel;
            childContributionRate: KnockoutObservable<number>;
            constructor(
                historyId: string,
                officeCode: string,
                officeName: string,
                startMonth: string,
                endMonth: string,
                rateItems: FunRateItemModel,
                childContributionRate: number) {
                this.historyId = historyId;
                this.officeCode = officeCode;
                this.officeName = officeName;
                this.startMonth = startMonth;
                this.endMonth = endMonth;
                this.fundRateItems = rateItems;
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
    }
}