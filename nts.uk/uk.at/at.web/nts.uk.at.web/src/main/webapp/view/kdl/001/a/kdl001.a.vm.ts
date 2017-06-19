module nts.uk.at.view.kdl001.a {
    export module viewmodel {
        export class ScreenModel {
            multiSelectMode: KnockoutObservable<boolean>; 
            rootList: Array<WorkTimeSet>;
            selectAbleItemList: KnockoutObservableArray<WorkTimeSet>;
            selectAbleCodeList: KnockoutObservableArray<string>;
            selectedCodeList: KnockoutObservableArray<string>;
            selectedCode: KnockoutObservable<string>;
            searchOption: KnockoutObservable<number>;
            startTimeOption: KnockoutObservable<number>;
            startTime: KnockoutObservable<number>;
            endTimeOption: KnockoutObservable<number>;
            endTime: KnockoutObservable<number>;
            constructor() {
                var self = this;
                self.multiSelectMode = nts.uk.ui.windows.getShared('kml001multiSelectMode');
                self.selectAbleCodeList = ko.observableArray(<Array<string>>nts.uk.ui.windows.getShared('kml001selectAbleCodeList'));
                self.selectedCodeList = ko.observableArray(<Array<string>>nts.uk.ui.windows.getShared('kml001selectedCodeList'));
                self.selectedCode = ko.observable(null);
                self.searchOption = ko.observable(0); 
                self.startTimeOption = ko.observable(1);
                self.startTime = ko.observable('');
                self.endTimeOption = ko.observable(1); 
                self.endTime = ko.observable('');  
                self.selectAbleItemList = ko.observableArray([]);
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                kdl001.a.service.findByCodeList(self.selectAbleCodeList())
                    .done(function(data) {
                        self.rootList = data;
                        self.selectAbleItemList(_.clone(self.rootList));
                        if(!nts.uk.util.isNullOrEmpty(self.selectAbleItemList())){
                            if(nts.uk.util.isNullOrEmpty(self.selectedCodeList())) {
                                self.selectedCodeList([_.first(self.selectAbleItemList()).code]);
                            }
                            self.selectedCode(_.first(self.selectAbleItemList()).code); 
                        } else {
                            self.selectedCodeList([]);
                            self.selectedCode(null);  
                        }
                        nts.uk.ui.block.clear();
                        dfd.resolve(); 
                    })
                    .fail(function(res) { 
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function(){nts.uk.ui.block.clear();});
                    });
                return dfd.promise();
            }
            
            search(){
                nts.uk.ui.block.invisible();
                var self = this;
                let command = {
                    codelist: _.map(self.rootList, function(item){ return item.code}),
                    startAtr: self.startTimeOption(),
                    startTime: nts.uk.util.isNullOrEmpty(self.startTime())?-1:self.startTime(),
                    endAtr: self.endTimeOption(),
                    endTime: nts.uk.util.isNullOrEmpty(self.endTime())?-1:self.endTime()
                }
                kdl001.a.service.findByTime(command)
                    .done(function(data) {
                        self.selectAbleItemList(data);
                        if(!nts.uk.util.isNullOrEmpty(self.selectAbleItemList())){
                            self.selectedCodeList([_.first(self.selectAbleItemList()).code]);
                            self.selectedCode(_.first(self.selectAbleItemList()).code);    
                        } else {
                            self.selectedCodeList([]);
                            self.selectedCode(null);  
                        }
                        nts.uk.ui.block.clear();
                    })
                    .fail(function(res) { 
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function(){nts.uk.ui.block.clear();});
                    });
            }
                
            returnData(){
                var self = this;
                self.startTimeOption(1);
                self.startTime(0);
                self.endTimeOption(1); 
                self.endTime(0); 
                self.selectAbleItemList(_.clone(self.rootList));   
                if(self.selectAbleItemList().length!=0) {
                    self.selectedCodeList([_.first(self.selectAbleItemList()).code]);
                    self.selectedCode(_.first(self.selectAbleItemList()).code);   
                } else {
                    self.selectedCodeList([]);
                    self.selectedCode(null);    
                }
                $("#inputStartTime").focus();
            }
            
            submitAndCloseDialog(){
                nts.uk.ui.block.invisible();
                var self = this;
                if(!self.multiSelectMode) 
                    self.selectedCodeList(nts.uk.util.isNullOrUndefined(self.selectedCode())?[]:[self.selectedCode()]);
                if(self.selectedCodeList().length==0){
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_29" }).then(function(){nts.uk.ui.block.clear();});
                } else {
                    nts.uk.ui.windows.setShared('kml001selectedCodeList', self.selectedCodeList());   
                    nts.uk.ui.block.clear();
                    nts.uk.ui.windows.close(); 
                }
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