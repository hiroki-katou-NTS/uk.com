module nts.uk.com.view.ccg025.a {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            
            constructor() {
            }

            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }//end start page

            
            
        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        

    }//end module model

}//end module