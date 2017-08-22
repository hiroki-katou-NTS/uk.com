module nts.uk.at.view.ksm001.f {

    import UsageSettingDto = service.model.UsageSettingDto;
    import Enum = service.model.Enum;

    export module viewmodel {

        export class ScreenModel {
            detail: KnockoutObservable<UsageSettingModel>;
            useClsEnums: Array<Enum>;

            constructor() {
                var self = this;
                self.detail = ko.observable(new UsageSettingModel(0, 0));
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                
                var dfd = $.Deferred();

                nts.uk.ui.block.invisible();

                self.loadUseClsEnum().done(function() {
                    self.loadUsageSetting.done(function(dataRes: UsageSettingDto) {
                        dfd.resolve();
                    })
                });

                dfd.resolve(self);
                
                return dfd.promise();
            }
            
            // load setting
            private loadUsageSetting(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                nts.uk.ui.block.grayout();

                // get setting
               service.getUsageSetting().done(function(dataRes: UsageSettingDto) {

                    if (dataRes === undefined || dataRes.length == 0) {
                        // Set default val
                        self.detail().empSet(self.useClsEnums[0].value);
                        self.detail().perSet(self.useClsEnums[0].value);
                    } else {
                        self.detail().empSet(dataRes.employmentSetting);
                        self.detail().perSet(dataRes.personalSetting);
                    }

                    nts.uk.ui.block.clear();

                    dfd.resolve();
                })

                return dfd.promise();
            }
            
            // load enum
            private loadUseClsEnum(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                nts.uk.ui.block.grayout();

                // get setting
               service.getUseClsEnum().done(function(dataRes: Array<Enum>) {
                    self.useClsEnums = dataRes;

                    nts.uk.ui.block.clear();

                    dfd.resolve();
                });

                return dfd.promise();
            }

            public save(): void {
                var self = this;

                nts.uk.ui.block.invisible();

                service.saveUsageSetting(self.detail().toDto()).done(function() {

                }).fail(function(error) {

                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * Event on click cancel button.
             */
            public cancel(): void {
                nts.uk.ui.windows.close();
            }

        }

        export class UsageSettingModel {
            empSet: KnockoutObservable<number>;
            perSet: KnockoutObservable<number>;

            constructor(employmentSetting: number, personalSetting: number) {
                this.empSet = ko.observable(employmentSetting);
                this.perSet = ko.observable(personalSetting);
            }

            public toDto(): UsageSettingDto {
                return new UsageSettingDto(this.empSet(), this.perSet());
            }
        }
    }
}