module nts.uk.at.view.kmk006.e {

    import UsageSettingDto = nts.uk.at.view.kmk006.a.service.model.UsageSettingDto;
    import UsageSettingModel = nts.uk.at.view.kmk006.a.viewmodel.UsageSettingModel;
    import UnitAutoCalSettingDto = nts.uk.at.view.kmk006.a.service.model.UnitAutoCalSettingDto;
    import UnitAutoCalSettingModel = nts.uk.at.view.kmk006.a.viewmodel.UnitAutoCalSettingModel;


    export module viewmodel {

        export class ScreenModel {
            usageSettingModel: UsageSettingModel;
            unitAtutoCalSettingModel: UnitAutoCalSettingModel;

            constructor() {
                var self = this;
                self.usageSettingModel = new UsageSettingModel();
                self.unitAtutoCalSettingModel = new UnitAutoCalSettingModel();
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<void> {
                var self = this;

                var dfd = $.Deferred();

                nts.uk.ui.block.invisible();
                nts.uk.at.view.kmk006.a.service.getEnumUnitAutoCal().done(function(data) {
                    self.unitAtutoCalSettingModel.updateData(data);
                    dfd.resolve();
                    dfd.resolve();
                }).always(function() {
                    nts.uk.ui.block.clear();
                })
                //                nts.uk.at.view.kmk006.a.service.findCompanySettingEstimate().done(function(data) {
                //                    self.usageSettingModel.updateData(data);
                //                    dfd.resolve();
                //                    dfd.resolve();
                //                }).always(function() {
                //                    nts.uk.ui.block.clear();
                //                });

                return dfd.promise();
            }


            /**
             * call service save button action click
             */
            private saveUsageSettingModel(): void {
                var self = this;

                nts.uk.ui.block.invisible();

                service.saveCompanySettingEstimate(self.usageSettingModel.toDto()).done(function() {
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

    }
}