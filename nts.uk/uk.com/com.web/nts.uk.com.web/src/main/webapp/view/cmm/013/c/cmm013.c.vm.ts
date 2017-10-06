module nts.uk.com.view.cmm013.c {

    export module viewmodel {
       
        export class ScreenModel {
            
            columns: KnockoutObservableArray<NtsGridListColumn>;
            
            items: KnockoutObservableArray<ItemModel>;        
            currentCode: KnockoutObservable<any>;
            
            constructor() {
                let _self = this;
                
                _self.items = ko.observableArray([]);
                _self.currentCode = ko.observable();
                
                _self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("CMM013_23"), key: 'code', width: 75},
                    { headerText: nts.uk.resource.getText("CMM013_24"), key: 'name', width: 175}
                ]); 
            }
            
            startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
               
                service.findAllSequenceMaster()
                    .done((data: any) => {
                        console.log(data);
                        dfd.resolve();
                    })
                    .fail((res: any) => {
                    
                    });
                
                return dfd.promise();
            }
            
            /**
             * close
             */
            public close() {
                nts.uk.ui.windows.close();
            }
        }
    }    
}