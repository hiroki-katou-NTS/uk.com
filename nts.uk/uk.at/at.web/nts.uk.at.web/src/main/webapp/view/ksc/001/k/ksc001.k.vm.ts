module nts.uk.at.view.ksc001.k {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                 let self = this;
                let dfd = $.Deferred();
                 $("#fixed-table").ntsFixedTable({height: 230});
                dfd.resolve();
                return dfd.promise();
            }
             /**
             * close dialog 
             */
            closeDialog(): void {
                let self = this;
                nts.uk.ui.windows.close();   
            }
           
        }
    }
}