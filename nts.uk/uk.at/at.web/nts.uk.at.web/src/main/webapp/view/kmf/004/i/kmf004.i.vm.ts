module nts.uk.at.view.kmf004.i.viewmodel {

    export class ScreenModel {
        
        constructor() {
            let self = this;
           

        }

        /** get data to list **/
        getData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            
                dfd.resolve();
           
            return dfd.promise();
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
          

                dfd.resolve();
            
            return dfd.promise();
        }

        /** update or insert data when click button register **/
        register() {
            let self = this;
        }
        //  new mode 

    }
}




