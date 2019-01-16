module nts.uk.at.view.ksm005.a {

    
    export module viewmodel {

        export class ScreenModel {
            constructor() {
                var self = this;
//                self.showExportBtn();
            }
            
             // 締め期間確認 
            /**
             * open dialog D
             */
            public openMasterSettingDialog(): void {
                nts.uk.request.jump("/view/ksm/005/b/index.xhtml");
            }
    
            /**
             * open dialog Assignment setting (init screen c)
             */
            public openAssignmentSettingDialog(): void {
                nts.uk.request.jump("/view/ksm/005/c/index.xhtml");
            }
            
            /**
             * show export button by role
             */
            showExportBtn() {
                if (nts.uk.util.isNullOrUndefined(__viewContext.user.role.attendance)
                    && nts.uk.util.isNullOrUndefined(__viewContext.user.role.payroll)
                    && nts.uk.util.isNullOrUndefined(__viewContext.user.role.officeHelper)
                    && nts.uk.util.isNullOrUndefined(__viewContext.user.role.personnel)) {
                    $("#print-button_1").hide();
                } else {
                    $("#print-button_1").show();
                }
            }
            
             /**
             * closeDialog
             */
            public opencdl028Dialog() {
                var self = this;
                let params = {
                    date: moment(new Date()).toDate(),
                    mode: 3 //ALL
                };
    
                nts.uk.ui.windows.setShared("CDL028_INPUT", params);
    
                nts.uk.ui.windows.sub.modal("com", "/view/cdl/028/a/index.xhtml").onClosed(function() {
                    var params = nts.uk.ui.windows.getShared("CDL028_A_PARAMS");
                    if (params.status) {
                        self.exportExcel(params.mode,params.standardDate, params.startDateFiscalYear, params.endDateFiscalYear);
                     }
                });
            }                                                           
        
            /**
             * Print file excel
             */
            exportExcel(mode: string, baseDate: string, startDate: string, endDate: string) : void {
                var self = this;
                nts.uk.ui.block.grayout();
                service.saveAsExcel(mode, baseDate, startDate, endDate).done(function() {
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

        }
        

    }
}