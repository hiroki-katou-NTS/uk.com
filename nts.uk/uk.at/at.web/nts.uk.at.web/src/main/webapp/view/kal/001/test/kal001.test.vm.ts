module nts.uk.at.view.kal001.test {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
           
            constructor() {
                let self = this;
            }
            /**
             * functiton start pagea
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                dfd.resolve();

                return dfd.promise();
            }//end start page 

            openDialogB(){
				nts.uk.ui.windows.setShared("extractedAlarmData", {employeeIds: [__viewContext.user.employeeId], isTopPage: true});
                nts.uk.ui.windows.sub.modal("/view/kal/001/b/index.xhtml");    
            }

            openDialogC(){
                nts.uk.ui.windows.sub.modal("/view/kal/001/c/index.xhtml");    
            }


        }//end screenModel
    }//end viewmodel  

   

}//end module