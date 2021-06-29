module nts.uk.at.view.kmk011.a {
    import serviceScreenH = nts.uk.at.view.kmk011.h.service;
    import setShared = nts.uk.ui.windows.setShared;

    export module viewmodel {

        const path = {
          getOtsukaOption: "at/record/divergencetime/setting/getOtsukaOption",
        };

        export class ScreenModel {
            otsukaOption: KnockoutObservable<boolean> = ko.observable(false);

            constructor() {
                var self = this; 
                nts.uk.request.ajax("at", path.getOtsukaOption).then((result: boolean) => self.otsukaOption(result));
            }

            /**
             * open DivergenceTimeSetting (init screen b)
             */
            public openDivergenceTimeSetting(): void {
                nts.uk.request.jump("/view/kmk/011/b/index.xhtml");
            }

            /**
             * open Creating divergence reference time page (init screen d)
             */
            public openCreatingDivergenceRefTimePage(): void {
                nts.uk.request.jump("/view/kmk/011/d/index.xhtml");
            }

            /**
             * open dialog H
             */
            public openUsageUnitSettingDialog(): void {
                serviceScreenH.find().done((value) => {
                    setShared("selectWorkTypeCheck", value.workTypeUseSet);
                    nts.uk.ui.windows.sub.modal("/view/kmk/011/h/index.xhtml").onClosed(function() { });
                })

            }
            
           public opencdl028Dialog() {
            var self = this;
            let params = {
            //    date: moment(new Date()).toDate(),
                mode: 1 //basedate
            };
            nts.uk.ui.windows.setShared("CDL028_INPUT", params);
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/028/a/index.xhtml").onClosed(function() {
                var params = nts.uk.ui.windows.getShared("CDL028_A_PARAMS");
                if (!nts.uk.util.isNullOrUndefined(params) && !nts.uk.util.isNullOrUndefined(params.standardDate)) {                 
                    self.exportExcel(params.standardDate);
                 }
            });

        } 

            public exportExcel(param): void {
                var self = this;
                nts.uk.ui.block.grayout();

                serviceScreenH.saveAsExcel(param).done(function() {
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
            
             showExportBtn() {
                    if (nts.uk.util.isNullOrUndefined(__viewContext.user.role.attendance)
                            && nts.uk.util.isNullOrUndefined(__viewContext.user.role.payroll)
                            && nts.uk.util.isNullOrUndefined(__viewContext.user.role.officeHelper)
                            && nts.uk.util.isNullOrUndefined(__viewContext.user.role.personnel)) {
                    $("#print-button").hide();
                    } else {
                    $("#print-button").show();
                    }
                    }

        }

    }
}