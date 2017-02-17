module nts.uk.pr.view.qmm008.i {
    export module viewmodel {
        import commonService = nts.uk.pr.view.qmm008._0.common.service;
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;
        import PensionRateItemModel = nts.uk.pr.view.qmm008.a.viewmodel.PensionRateItemModel;
        import PensionRateModel = nts.uk.pr.view.qmm008.a.viewmodel.PensionRateModel;
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;

        export class ScreenModel {

            leftShow: KnockoutObservable<boolean>;
            rightShow: KnockoutObservable<boolean>;
            leftBtnText: KnockoutComputed<string>;
            rightBtnText: KnockoutComputed<string>;
            listAvgEarnLevelMasterSetting: Array<AvgEarnLevelMasterSettingDto>;
            listPensionAvgearnModel: KnockoutObservableArray<PensionAvgearnModel>;
            listPensionRateItemModel: Array<PensionRateItemModel>;

            constructor(dataOfSelectedOffice: InsuranceOfficeItemDto, pensionModel: PensionRateModel) {
                var self = this;
                self.listAvgEarnLevelMasterSetting = [];
                self.listPensionAvgearnModel = ko.observableArray<PensionAvgearnModel>([]);
                self.listPensionRateItemModel = pensionModel.rateItems;
                self.leftShow = ko.observable(true);
                self.rightShow = ko.observable(true);
                self.leftBtnText = ko.computed(function() { if (self.leftShow()) return "—"; return "+"; });
                self.rightBtnText = ko.computed(function() { if (self.rightShow()) return "—"; return "+"; });
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
                service.findPensionAvgearn('id').done(res => {
                    res.forEach(item => {
                        self.listPensionAvgearnModel.push(new PensionAvgearnModel(
                            item.historyId,
                            item.levelCode,
                            new PensionAvgearnValueModel(
                                item.companyFund.maleAmount,
                                item.companyFund.femaleAmount,
                                item.companyFund.unknownAmount),
                            new PensionAvgearnValueModel(
                                item.companyFundExemption.maleAmount,
                                item.companyFundExemption.femaleAmount,
                                item.companyFundExemption.unknownAmount),
                            new PensionAvgearnValueModel(
                                item.companyPension.maleAmount,
                                item.companyPension.femaleAmount,
                                item.companyPension.unknownAmount),
                            new PensionAvgearnValueModel(
                                item.personalFund.maleAmount,
                                item.personalFund.femaleAmount,
                                item.personalFund.unknownAmount),
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
                service.updatePensionAvgearn(this.collectData());
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
             * Close dialog.
             */
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }

        export class PensionAvgearnModel {
            historyId: string;
            levelCode: number;
            companyFund: PensionAvgearnValueModel;
            companyFundExemption: PensionAvgearnValueModel;
            companyPension: PensionAvgearnValueModel;
            personalFund: PensionAvgearnValueModel;
            personalFundExemption: PensionAvgearnValueModel;
            personalPension: PensionAvgearnValueModel;
            childContributionAmount: KnockoutObservable<number>;
            constructor(
                historyId: string,
                levelCode: number,
                companyFund: PensionAvgearnValueModel,
                companyFundExemption: PensionAvgearnValueModel,
                companyPension: PensionAvgearnValueModel,
                personalFund: PensionAvgearnValueModel,
                personalFundExemption: PensionAvgearnValueModel,
                personalPension: PensionAvgearnValueModel,
                childContributionAmount: number) {
                this.historyId = historyId;
                this.levelCode = levelCode;
                this.companyFund = companyFund;
                this.companyFundExemption = companyFundExemption;
                this.companyPension = companyPension;
                this.personalFund = personalFund;
                this.personalFundExemption = personalFundExemption;
                this.personalPension = personalPension;
                this.childContributionAmount = ko.observable(childContributionAmount);
            }
        }

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