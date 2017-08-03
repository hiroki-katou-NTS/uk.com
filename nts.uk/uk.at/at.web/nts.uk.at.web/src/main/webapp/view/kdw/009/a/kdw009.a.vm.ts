module nts.uk.at.view.kdw009.a.viewmodel {
    
    export class ScreenModel {
        lstErrorAlarm: KnockoutObservableArray<any>;
        gridListColumns: KnockoutObservableArray<any>;
        code: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<any>;
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDW007_6"), key: 'code', width: 45 },
                { headerText: nts.uk.resource.getText("KDW007_7"), key: 'name', width: 280 }
            ]);
            self.lstErrorAlarm = ko.observableArray([]);
            self.code = ko.observable();
            self.selectedCode = ko.observable();
        }

        /** get data number "value" in list **/
        getListStandardMenu(value) {
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getAll().done(
                (lstData) => {
                let sortedData = _.orderBy(lstData, ['code'], ['asc']);
                self.lstErrorAlarm(sortedData);
//                self.selectedCode(sortedData[0].code);
                    dfd.resolve();
                })   
            
                              
            
            
            return dfd.promise();
        }  
        
        /** update data when click button register **/
        register() {
           
        }    

        /** close Dialog **/
        closeDialog() {   
            
        }
        
        
    }

}




