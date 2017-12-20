module nts.uk.at.view.kmk006.e {

    import UnitAutoCalSettingDto = service.model.UnitAutoCalSettingDto;

    export module viewmodel {

        export class ScreenModel {
            unitAtutoCalSettingModel: UnitAutoCalSettingModel;

            constructor() {
                var self = this;
                self.unitAtutoCalSettingModel = new UnitAutoCalSettingModel();
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<void> {
                var self = this;

                var dfd = $.Deferred<any>();
                

                nts.uk.ui.block.invisible();
                service.getUseUnitAutoCal().done(function(data) {
                    // Initial settings.
                    self.loadUseUnitAutoCalSettingModel();
                    dfd.resolve();
                }).always(function() {
                    nts.uk.ui.block.clear();
                })

                return dfd.promise();
            }

            /**
           * call service load UseUnitAutoCalSettingModel
           */
            private loadUseUnitAutoCalSettingModel(): void {
                var self = this;
                service.getUseUnitAutoCal().done(function(data) {
                    self.unitAtutoCalSettingModel.updateData(data);
                }).fail(res => {
                    nts.uk.ui.dialog.alertError(res);
                });
            }

            /**
             * call service save button action click
             */
            private saveUnitAutoCalSettingModel(): void {
                var self = this;

                nts.uk.ui.block.invisible();

                service.saveUnitAutoCal(self.unitAtutoCalSettingModel.toDto()).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // close windows
                        nts.uk.ui.windows.close();
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(function() {
                   
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * event on click cancel button.
             */
            private cancelSaveUsageSettingModel(): void {
                nts.uk.ui.windows.close();
            }

        }

        export class UnitAutoCalSettingModel {
            useJobSet: KnockoutObservable<boolean>;
            useWkpSet: KnockoutObservable<boolean>;
            useJobwkpSet: KnockoutObservable<boolean>;
            constructor() {
                this.useWkpSet = ko.observable(true);
                this.useJobSet = ko.observable(true);
                this.useJobwkpSet = ko.observable(false);
            }

            updateData(dto: UnitAutoCalSettingDto) {
                this.useWkpSet(dto.useWkpSet);
                this.useJobSet(dto.useJobSet);
                this.useJobwkpSet(dto.useJobwkpSet);
            }

            toDto(): UnitAutoCalSettingDto {
                var dto: UnitAutoCalSettingDto = {
                    useWkpSet: this.useWkpSet(),
                    useJobSet: this.useJobSet(),
                    useJobwkpSet: this.useJobwkpSet()
                };
                return dto;
            }

            resetData() {
                this.useWkpSet(true);
                this.useJobSet(true);
                this.useJobwkpSet(false);
            }
        }

    }
}