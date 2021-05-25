module nts.uk.at.view.kmk004.e {

    import UsageUnitSettingDto = service.model.UsageUnitSettingDto;

    export module viewmodel {

        export class ScreenModel {
            companySetting: KnockoutObservable<boolean>;
            checkValueCompanySetting: KnockoutObservable<number>;
            usageUnitSettingModel: UsageUnitSettingModel;

            constructor() {
                let self = this;
                self.usageUnitSettingModel = new UsageUnitSettingModel();
                self.companySetting = ko.observable(true);
                self.checkValueCompanySetting = ko.observable(1);

                self.companySetting.subscribe(function(val: boolean){
                   if(val){
                        self.usageUnitSettingModel.employment(val);    
                        self.checkValueCompanySetting(1);
                        $('#radio-group-display-setting').attr('tabindex', '2');
                   } else {
                       self.checkValueCompanySetting(3);
                       self.usageUnitSettingModel.workPlace(false);
                       self.usageUnitSettingModel.employment(false);
                       $('#radio-group-display-setting').attr('tabindex', '-1');
                   }
                });
                self.checkValueCompanySetting.subscribe(function(val: number){
                    if(val == 1){
                        self.usageUnitSettingModel.employment(true);    
                        self.usageUnitSettingModel.workPlace(false);    
                    }
                    if(val == 2){
                        self.usageUnitSettingModel.workPlace(true);    
                        self.usageUnitSettingModel.employment(false);    
                    }
                });
            }

            /**
             * Update viewmodel.
             */
            private updateView() {
                var self = this;
                self.companySetting(self.usageUnitSettingModel.employment() || self.usageUnitSettingModel.workPlace());
                if (self.usageUnitSettingModel.employment()) {
                    self.checkValueCompanySetting(1);
                }
                if (self.usageUnitSettingModel.workPlace()) {
                    self.checkValueCompanySetting(2);
                }
            }

            /**
             * Event on start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                nts.uk.ui.block.grayout();
                service.findUsageUnitSetting().done(function(data) {
                    self.usageUnitSettingModel.updateData(data);
                    self.updateView();
                    dfd.resolve();
                }).always(() => nts.uk.ui.block.clear());
                return dfd.promise();
            }

            /**
             * Collect data from viewmodel.
             */
            private collectData(): UsageUnitSettingDto{
                var self = this;
                var dto: UsageUnitSettingDto = new UsageUnitSettingDto();
                dto.employee = self.usageUnitSettingModel.employee(); 
                dto.workPlace = self.usageUnitSettingModel.workPlace(); 
                dto.employment = self.usageUnitSettingModel.employment();
                return dto; 
            }

            /**
             * Save usage unit setting.
             */
            public save(): void {
                var self = this;
                nts.uk.ui.block.grayout();
                service.saveUsageUnitSetting(self.collectData()).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        nts.uk.ui.windows.close();
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error).then(function() {
                        nts.uk.ui.windows.close();
                    });
                }).always(() => nts.uk.ui.block.clear());
            }

            /**
             * Event on click cancel button.
             */
            public cancel(): void {
                nts.uk.ui.windows.close();
            }
        }

        /**
         * The class UsageUnitSettingModel
         */
        export class UsageUnitSettingModel {

            /** The employee. */
            employee: KnockoutObservable<boolean>;

            /** The work place. */
            workPlace: KnockoutObservable<boolean>;

            /** The employment. */
            employment: KnockoutObservable<boolean>;

            constructor() {
                this.employee = ko.observable(true);
                this.workPlace = ko.observable(true);
                this.employment = ko.observable(true);
            }

            /**
             * Update viewmodel data from dto.
             */
            updateData(dto: UsageUnitSettingDto) {
                this.employee(dto.employee);
                this.workPlace(dto.workPlace);
                this.employment(dto.employment);
            }
        }
    }
}