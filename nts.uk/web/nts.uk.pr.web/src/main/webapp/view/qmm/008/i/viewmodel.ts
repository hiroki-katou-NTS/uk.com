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

            constructor() {
                var self = this;
                self.listAvgEarnLevelMasterSetting = ko.observableArray([]);
                self.listPensionAvgearn = ko.observableArray([]);
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
                commonService.getAvgEarnLevelMasterSettingList().done(data => {
                    self.listAvgEarnLevelMasterSetting(data);
                    /**
                     * service.findPensionAvgearn('a').done(zz => {
                        self.listPensionAvgearn(zz);
                    });
                     */
                    dfd.resolve();
                });
                return dfd.promise();
            }

            private collectData(): any {
                var self = this;
                var data = [];
                self.listPensionAvgearn().forEach(item => {
                    data.push(ko.toJS(item));
                });
                return data;
            }

            private save() {
                var self = this;
                service.updatePensionAvgearn(this.collectData());
            }

            private leftToggle() {
                this.leftShow(!this.leftShow());
            }
            private rightToggle() {
                this.rightShow(!this.rightShow());
            }

            private closeDialog() {
                nts.uk.ui.windows.close();
            }
        }
    }
}