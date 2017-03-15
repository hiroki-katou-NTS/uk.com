module cmm013.c.viewmodel { 

    export class ScreenModel {
        label_002: KnockoutObservable<Labels>;
        inp_003: KnockoutObservable<string>;
        historyId: KnockoutObservable<string>;
        startDateLast: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        res: KnockoutObservableArray<string>;
        endDateUpdate: KnockoutObservable<string>;
        historyIdLast: KnockoutObservable<string>;
        length: KnockoutObservable<number>;
        startDateAddNew: KnockoutObservable<string>;
        listbox: KnockoutObservableArray<model.ListHistoryDto>;
         selectedCode: KnockoutObservable<any>;
        checkDelete: KnockoutObservable<any>;


        startDateUpdate: KnockoutObservable<string>;
        historyIdUpdate: KnockoutObservable<string>;
        startDateUpdateNew: KnockoutObservable<string>;
        startDatePre: KnockoutObservable<string>;
        jobHistory: KnockoutObservable<model.ListHistoryDto>;

        constructor() {
            var self = this;
            self.label_002 = ko.observable(new Labels()); 
            self.inp_003 = ko.observable(null);
            self.historyId = ko.observable(null);
            self.startDateLast = ko.observable('');
            self.endDateUpdate = ko.observable('');
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true); 
            
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

        }
       
       
        
        
         addHist(){
            var self = this;
            var dfd = $.Deferred<any>();

           if(self.listbox() ===undefined || self.listbox()==null|| self.listbox().length ==0){
                var jobHistNew = new model.ListHistoryDto('1',self.startDateAddNew(),'','');
            }else{
                var jobHistNew = new model.ListHistoryDto('0',self.startDateAddNew(),'','');
            }
            service.addJobHist(jobHistNew).done(function() {
                nts.uk.ui.windows.setShared('startNew','', true);
                self.getAllHist();
                }).fail(function(res) {
                    alert('fail');
                })
        }
      
        add(){
            var self = this;
            if(self.inp_003() <= self.startDateLast()){
                alert("");    
                return;
            }
            else {
                if(self.startDateLast()!= null) {
                    var check = self.selectedId();
                }else{
                    var check = 2;
                }
                nts.uk.ui.windows.setShared('startNew',self.inp_003());
                nts.uk.ui.windows.setShared('copy_c',check);
                nts.uk.ui.windows.close();
            }
        }
        
         startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.historyId(nts.uk.ui.windows.getShared('Id_13'));
            self.startDateLast(nts.uk.ui.windows.getShared('startLast'));
            self.endDateUpdate(nts.uk.ui.windows.getShared('endUpdate'));

            if(self.startDateLast() !='' && self.startDateLast()!= null){
                self.itemList = ko.observableArray([
                    new BoxModel(1, '最新の履歴（'+self.startDateLast()+'）から引き継ぐ  '),
                    new BoxModel(2, '全員参照不可')
                ]);
            }
            else {
                self.itemList = ko.observableArray([
                    new BoxModel(1, '全員参照不可')
                ]);
            }                

            dfd.resolve();
            return dfd.promise();                    
        }
        
         getAllHist(): any{
            var self = this;
            var dfd = $.Deferred<any>();
            self.selectedCode('');
            self.listbox([]);
            service.getAllHistory().done(function(history_arr: Array<model.ListHistoryDto>){
                if(history_arr === undefined || history_arr.length === 0)
                    return;
                self.listbox(history_arr);
                _.forEach(history_arr, function(strHistory){
                    self.listbox.push(strHistory);
                })
                var historyFirst = _.first(history_arr);
                var historyLast= _.last(history_arr);
                self.checkDelete(historyLast.startDate);
                self.selectedCode(historyFirst.startDate);
                self.startDateUpdate(historyFirst.startDate);
                self.endDateUpdate(historyFirst.endDate);
                self.historyIdUpdate(historyFirst.historyId);
                self.startDateLast(historyFirst.startDate);
                self.historyIdLast(historyFirst.historyId);
                dfd.resolve(history_arr);
            })   
            return dfd.promise();    
        }
        
        
    }

     export class Labels{
        constraint: string =  'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
    
        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.enable = ko.observable(true);
        }    
    }

    export class BoxModel{
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
