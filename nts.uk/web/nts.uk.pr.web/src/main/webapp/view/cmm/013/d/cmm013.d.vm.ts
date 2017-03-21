module cmm013.d.viewmodel { 

    export class ScreenModel {
        inp_003: KnockoutObservable<string>;
        inp_003_enable: KnockoutObservable<boolean>;
        startDateUpdate: KnockoutObservable<string>;
        endDateUpdate: KnockoutObservable<string>;
        startDateNew: KnockoutObservable<string>;
        
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        
         res: KnockoutObservableArray<string>;
        startDateLast: KnockoutObservable<string>;
        historyIdLast: KnockoutObservable<string>;
        length: KnockoutObservable<number>;
        startDateAddNew: KnockoutObservable<string>;
        listbox: KnockoutObservableArray<model.ListHistoryDto>;
         selectedCode: KnockoutObservable<any>;
        checkDelete: KnockoutObservable<any>;


        historyIdUpdate: KnockoutObservable<string>;
        startDateUpdateNew: KnockoutObservable<string>;
        startDatePre: KnockoutObservable<string>;
        jobHistory: KnockoutObservable<model.ListHistoryDto>;
        constructor() {
            var self = this;
            self.inp_003 = ko.observable(null);
            self.inp_003_enable = ko.observable(true);
            self.startDateNew = ko.observable('');
            self.startDateUpdate = ko.observable('');
            self.endDateUpdate = ko.observable('');
            
              self.startDateLast = ko.observable(null);
            self.historyIdLast = ko.observable(null);
            self.length = ko.observable(0);
            self.startDateAddNew = ko.observable("");

            self.startDateUpdate = ko.observable(null);
            self.endDateUpdate = ko.observable(null);
            self.historyIdUpdate = ko.observable(null);
            self.startDateUpdateNew = ko.observable(null);
            self.startDatePre = ko.observable(null);
            self.jobHistory = ko.observable(null);
               self.selectedCode = ko.observable(null);
            self.checkDelete = ko.observable(null);
            self.listbox = ko.observableArray([]);
           
            self.itemList = ko.observableArray([
                new BoxModel(1, '履歴を削除する '),
                new BoxModel(2, '履歴を修正する')
            ]);
            self.selectedId = ko.observable(2);
            self.enable = ko.observable(true); 
            
            self.selectedId.subscribe((function(codeChanged){
                if(codeChanged==1){
                    self.inp_003_enable(false);  
                }
                else{
                    self.inp_003_enable(true);    
                }  
            }));
        }
        
      
      
          closeDialog(): any{
            nts.uk.ui.windows.close();   
        }
        
        startPage(): JQueryPromise<any>{
            var self = this;
            var dfd = $.Deferred();
            self.startDateUpdate(nts.uk.ui.windows.getShared('startUpdate'));
            self.endDateUpdate(nts.uk.ui.windows.getShared('endUpdate'));
            self.inp_003(self.startDateUpdate());
            dfd.resolve();
            return dfd.promise();      
        }
   
        positionHis(){
            var self = this;
//            if(self.selectedId() == 2){
//                if(self.inp_003() >= self.endDateUpdate()||self.inp_003()<=self.startDateUpdate()){
//                    // Update
//                    alert("ERRRR");    
//                    return;
//                }
//                else {
//                    nts.uk.ui.windows.setShared('startUpdateNew',self.inp_003());
//                    nts.uk.ui.windows.setShared('check_d',self.selectedId());
//                    nts.uk.ui.windows.close();
//                }
//            }else{
//                self.inp_003(null);
                nts.uk.ui.windows.setShared('startUpdateNew',self.inp_003());
                nts.uk.ui.windows.setShared('check_d',self.selectedId());
                nts.uk.ui.windows.close();     
//            }
        }
    }

    class BoxModel{
        id: number;
        name: string;
        constructor(id, name){
            var self = this;
            self.id = id;
            self.name = name;
        }    
    }
    
         export module model {
               export class historyDto{
           startDate: string;
           endDate: string;
           historyId: string; 
           constructor (startDate: string, endDate: string,historyId: string){
               this.startDate = startDate;
               this.endDate = endDate;
               this.historyId = historyId;
           }  
       }

 export class ListHistoryDto {
            companyCode: string;
            startDate: string;
            endDate: string;
            historyId: string;
            constructor( companyCode: string,startDate: string, endDate: string, historyId: string) {
                var self = this;
                self.companyCode = companyCode;
                self.startDate = startDate;
                self.endDate = endDate;
                self.historyId = historyId;
            }
        }


}
}
