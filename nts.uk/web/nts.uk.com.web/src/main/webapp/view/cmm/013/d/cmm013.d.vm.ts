module cmm013.d.viewmodel { 

    export class ScreenModel {
        inp_003: KnockoutObservable<string>;
        inp_003_enable: KnockoutObservable<boolean>;
        startDateUpdate: KnockoutObservable<string>;
        endDateUpdate: KnockoutObservable<string>;
        startDateNew: KnockoutObservable<string>;
        //D_SEL_001
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        cDelete: KnockoutObservable<number>;
        sDateLast: KnockoutObservable<string>;
        histIdUpdate: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.inp_003 = ko.observable(null);
            self.inp_003_enable = ko.observable(true);
            self.startDateNew = ko.observable('');
            self.startDateUpdate = ko.observable('');
            self.endDateUpdate = ko.observable('');
            //D_SEL_001
            self.enable = ko.observable(true); 
            self.cDelete = ko.observable(0);
            self.sDateLast = ko.observable('');
            self.histIdUpdate = ko.observable('');
        }
          closeDialog(){
             nts.uk.ui.windows.close();   
        }
        startPage(): JQueryPromise<any>{
            var self = this;
            var dfd = $.Deferred();
            self.cDelete(nts.uk.ui.windows.getShared('CMM013_delete'));
            self.startDateUpdate(nts.uk.ui.windows.getShared('CMM013_startDateUpdate'));
            self.endDateUpdate(nts.uk.ui.windows.getShared('CMM013_endDateUpdate'));
            self.sDateLast(nts.uk.ui.windows.getShared('CMM013_sDateLast'));
            self.histIdUpdate(nts.uk.ui.windows.getShared('CMM013_historyIdUpdate'));
            if(self.cDelete()==1){//option delete
                self.itemList = ko.observableArray([
                    new BoxModel(1, '履歴を削除する '),
                    new BoxModel(2, '履歴を修正する')
                ]);
                self.selectedId = ko.observable(2);
                self.selectedId.subscribe((function(codeChanged){
                    if(codeChanged==1){
                        self.inp_003_enable(false);  
                    }
                    else{
                        self.inp_003_enable(true);    
                    }  
                }));
            }
            if(self.cDelete()==2){//not option delete
                self.itemList = ko.observableArray([
                    new BoxModel(1, '履歴を修正する'),
                    new BoxModel(2, '履歴を修正する')
                ]);
                self.selectedId = ko.observable(1);
            }
            self.inp_003(self.startDateUpdate());
            dfd.resolve();
            return dfd.promise();      
        }
   
        decision(){
            var self = this;
            if(self.selectedId()==2 && self.cDelete()==1){//check input
                if(self.inp_003() >= self.endDateUpdate()||self.inp_003()<=self.startDateUpdate()){
                    alert("nhap lai start Date");    
                    return;
                }
            }
            var dfd = $.Deferred<any>();
            if(self.selectedId()==1 && self.cDelete()==1){//delete   
                var jobHist = new service.model.JobHistDto(self.startDateUpdate(), '', self.endDateUpdate(), self.histIdUpdate());
                    //delete jobHist 1
                var checkDelete ='1';
                var checkUpdate = '0';
            }else{//update
                checkDelete = '0';
                var jobHist = new service.model.JobHistDto(self.startDateUpdate(), self.inp_003(), self.endDateUpdate(), self.histIdUpdate());
                if (self.startDateUpdate()==self.sDateLast()) {
                    //update jHist last
                    checkUpdate = '2';
                } else {
                   checkUpdate = '1';
                }
            }
            var updateHandler = new service.model.UpdateHandler(jobHist, checkUpdate, checkDelete);
            service.updateJobHist(updateHandler).done(function() {
                alert('update thanh cong');
                nts.uk.ui.windows.setShared('cmm013D_updateFinish',true, true);
                nts.uk.ui.windows.close();
            }).fail(function(res) {
                dfd.reject(res);
            })
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
}
