module nts.uk.at.view.kdl001.a {
    export module viewmodel {
        export class ScreenModel {
            multiSelectMode: KnockoutObservable<boolean>; 
            selectAbleItemList: KnockoutObservableArray<WorkTimeSet>;
            selectAbleCodeList: KnockoutObservableArray<string>;
            selectedCodeList: KnockoutObservableArray<string>;
            selectedCode: KnockoutObservable<string>;
            searchOption: KnockoutObservable<number>;
            startTimeOption: KnockoutObservable<string>;
            startTime: KnockoutObservable<number>;
            endTimeOption: KnockoutObservable<string>;
            endTime: KnockoutObservable<number>;
            constructor() {
                var self = this;
                self.multiSelectMode = nts.uk.ui.windows.getShared('multiSelectMode');
                self.selectAbleCodeList = ko.observableArray(<Array<string>>nts.uk.ui.windows.getShared('selectAbleCodeList'));
                self.selectedCodeList = ko.observableArray(<Array<string>>nts.uk.ui.windows.getShared('selectedCodeList'));
                self.selectedCode = ko.observable(nts.uk.ui.windows.getShared('selectedCode'));
                self.searchOption = ko.observable(0); 
                self.startTimeOption = ko.observable('当日');
                self.startTime = ko.observable(0);
                self.endTimeOption = ko.observable('当日'); 
                self.endTime = ko.observable(0);  
                self.selectAbleItemList = ko.observableArray([]);
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findByCodeList(self.selectAbleCodeList())
                    .done(function(data) {
                        self.selectAbleItemList(data);
                        dfd.resolve(); 
                    })
                    .fail(function(res) { 
                    });
                return dfd.promise();
            }
            
            search(){
                var self = this;
                let inputString = self.startTimeOption().toString()+self.startTime().toString()+' ~ '+self.endTimeOption().toString()+self.endTime().toString();
                $('#day-list-tbl > tbody > tr').css('display','none');
                let allRow = $('#day-list-tbl > tbody > tr');
                for(let i=1;i<=allRow.length;i++){
                    let tr = $('#day-list-tbl > tbody > tr:nth-child('+i+')');
                    let col1=3; let col2=4;
                    if(self.multiSelectMode) { col1=4; col2=5; }
                    for(let j=col1;j<=col2;j++){
                        if(tr.find(":nth-child("+j+")").text().indexOf(inputString) > -1) {
                            tr.css('display','');
                            break;
                        }   
                    }    
                }        
            }
                
            returnData(){
                $('#day-list-tbl > tbody > tr').css('display','');        
            }
            
            submitAndCloseDialog(){
                var self = this;
                if(self.multiSelectMode){
                    nts.uk.ui.windows.setShared('selectedCodeList', self.selectedCodeList());    
                } else {
                    nts.uk.ui.windows.setShared('selectedCode', self.selectedCode());    
                }
                nts.uk.ui.windows.close();   
            }
            
            closeDialog(){
                nts.uk.ui.windows.close();    
            }
        }
        
        interface WorkTimeSet {
            code: string;
            name: string;
            workTime1: string;
            workTime2: string;
            workAtr: string;
            remark: string;
        }
    }
}