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
        deleteChecker: KnockoutObservable<number>;
        startDateLast: KnockoutObservable<string>;
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
            self.deleteChecker = ko.observable(0);
            self.startDateLast = ko.observable('');
            self.histIdUpdate = ko.observable('');
        }
        
        startPage(): JQueryPromise<any>{
            var self = this;
            var dfd = $.Deferred();
            self.deleteChecker(nts.uk.ui.windows.getShared('delete'));
            self.startDateUpdate(nts.uk.ui.windows.getShared('startUpdate'));
            self.endDateUpdate(nts.uk.ui.windows.getShared('endUpdate'));
            self.startDateLast(nts.uk.ui.windows.getShared('startDateLast'));
            self.histIdUpdate(nts.uk.ui.windows.getShared('Id_13Update'));
            if(self.deleteChecker()==1){//option delete
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
            if(self.deleteChecker()==2){//not option delete
                self.itemList = ko.observableArray([
                    new BoxModel(1, '履歴を修正する')
                ]);
                self.selectedId = ko.observable(1);
            }
            self.inp_003(self.startDateUpdate());
            dfd.resolve();
            return dfd.promise();      
        }

         closeDialog(): any{
            nts.uk.ui.windows.close();   
        }
        positionHis(){
            let self = this;
            if(self.selectedId()==2 && self.deleteChecker()==1){
                if(self.inp_003() >= self.endDateUpdate()||self.inp_003()<=self.startDateUpdate()){
                    alert("Re Input");    
                    return;
                }
            }
            let dfd = $.Deferred<any>();
            if(self.selectedId()==1 && self.deleteChecker()==1){   
                let jobHist = new model.ListHistoryDto(self.startDateUpdate(), '', self.endDateUpdate(), self.histIdUpdate());
                    
                var checkDelete ='1';
                var checkUpdate = '0';
            }else{
                checkDelete = '0';
                var jobHist = new model.ListHistoryDto(self.startDateUpdate(), self.inp_003(), self.endDateUpdate(), self.histIdUpdate());
                if (self.startDateUpdate()==self.startDateLast()) {
                    
                    checkUpdate = '2';
                } else {
                   checkUpdate = '1';
                }
            }
            let afterUpdate = new model.AfUpdate(jobHist, checkUpdate, checkDelete);
            service.updateHist(afterUpdate).done(function() {
                alert('update thanh cong');
                nts.uk.ui.windows.setShared('Finish',true, true);
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
        
    export module model {
        export class AfUpdate{
            jHist: ListHistoryDto;
            checkUpdate: string;
            checkDelete: string;
            constructor(jHist: ListHistoryDto, checkUpdate: string, checkDelete: string){
                this.jHist = jHist;
                this.checkUpdate = checkUpdate;
                this.checkDelete = checkDelete;
            }    
        }
        export class ListHistoryDto{
            companyCode: string;
            startDate: string;
            endDate: string;
            historyId: string;
            constructor(companyCode: string, startDate: string, endDate: string, historyId: string){
                this.companyCode = companyCode;
                this.startDate = startDate;
                this.endDate = endDate;
                this.historyId = historyId;
            }    
        }
    }
}
