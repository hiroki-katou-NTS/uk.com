module nts.uk.at.view.kdw001.f {
    export module viewmodel {
        export class ScreenModel {
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            //table
            itemList: KnockoutObservableArray<any>;
            constructor() {
                let self = this;
                //
                self.enable = ko.observable(true);
                self.required = ko.observable(true);
                self.dateValue = ko.observable({});
                self.startDateString = ko.observable('');
                self.endDateString = ko.observable('');
                //table
                self.itemList  = ko.observableArray([]);
                for (let i = 1; i <= 15; i++) {
                    self.itemList.push({column1: i, 
                         column2 :"者社員コード"+i,
                         column3 :"者名"+i,
                         column4 :"対象締め期間"+i,
                         column5 :"したメニュー"+i,
                         column6 :"結果"+i});
                }
                
                self.startDateString.subscribe(function(value){
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();        
                });
                
                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;   
                    self.dateValue.valueHasMutated();      
                });
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }//end start page
        }//end screenModel
    }//end viewmodel    
}//end module
    