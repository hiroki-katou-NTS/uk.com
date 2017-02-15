module nts.uk.pr.view.qmm008.i {
    export module viewmodel {
        import commonService = nts.uk.pr.view.qmm008._0.common.service;

        export class ScreenModel {

            leftShow: KnockoutObservable<boolean>;
            rightShow: KnockoutObservable<boolean>;
            leftBtnText: KnockoutComputed<string>;
            rightBtnText: KnockoutComputed<string>;
            listAvgEarnLevelMasterSetting: KnockoutObservableArray<any>;
            listPensionAvgearn: KnockoutObservableArray<any>;
            healthInsuranceRateModel: KnockoutObservable<any>;

            constructor() {
                var self = this;
                self.listAvgEarnLevelMasterSetting = ko.observableArray([]);
                self.listPensionAvgearn = ko.observableArray([]);
                self.healthInsuranceRateModel = ko.observable(new HealthInsuranceRateModel());
                self.leftShow = ko.observable(true);
                self.rightShow = ko.observable(true);
                self.leftBtnText = ko.computed(function() { if (self.leftShow()) return "—"; return "+"; });
                self.rightBtnText = ko.computed(function() { if (self.rightShow()) return "—"; return "+"; });
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.loadAvgEarnLevelMasterSetting().done(() =>
                    self.loadPensionRate().done(() =>
                        self.loadPensionAvgearn().done(() =>
                            dfd.resolve())));
                return dfd.promise();
            }

            /**
             * Load AvgEarnLevelMasterSetting list.
             */
            private loadAvgEarnLevelMasterSetting(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                commonService.getAvgEarnLevelMasterSettingList().done(res => {
                    self.listAvgEarnLevelMasterSetting(res);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Load PensionRate.
             */
            private loadPensionRate(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findPensionRate('id').done(res => {
                    self.healthInsuranceRateModel().officeCode = res.officeCode;
                    self.healthInsuranceRateModel().officeName = res.officeCode;
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Load PensionAvgEarn.
             */
            private loadPensionAvgearn(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findPensionAvgearn('id').done(res => {
                    self.listPensionAvgearn(res);
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
                self.listPensionAvgearn().forEach(item => {
                    data.push(ko.toJS(item));
                });
                return data;
            }

            /**
             * Call service to save pensionAvgearn.
             */
            private save() {
                var self = this;
                service.updatePensionAvgearn(this.collectData());
            }

            /**
             * Button toggle Pension welfare pension.
             */
            private leftToggle() {
                this.leftShow(!this.leftShow());
            }

            /**
             * Button toggle right.
             */
            private rightToggle() {
                this.rightShow(!this.rightShow());
            }

            /**
             * Close dialog.
             */
            private closeDialog() {
                nts.uk.ui.windows.close();
            }
        }

        export class HealthInsuranceRateModel {
            officeCode: string;
            officeName: string;
            startMonth: string;
            endMonth: string;
        }
    }
}