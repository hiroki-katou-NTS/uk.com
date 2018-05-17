module nts.uk.at.view.kwr001.c {
    
    import service = nts.uk.at.view.kwr001.c.service;
    
    export module viewmodel {
        export class ScreenModel {
            data: KnockoutObservable<number>;
            
            // list
            items: KnockoutObservableArray<ItemModel>;
            outputItemList: KnockoutObservableArray<ItemModel>;
            currentCodeList: KnockoutObservable<any>;
            columns: KnockoutObservableArray<any>;
            
            C3_2_value: KnockoutObservable<string>;
            C3_3_value: KnockoutObservable<string>;
            
            // switch button
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            
            currentCodeListSwap: KnockoutObservableArray<ItemModel>;
            test: KnockoutObservableArray<any>;

            checkedRemarksInput: KnockoutObservable<boolean>;
            checkedMasterUnregistered: KnockoutObservable<boolean>;
            checkedEngraving: KnockoutObservable<boolean>;
            checkedImprintingOrderNotCorrect: KnockoutObservable<boolean>;
            checkedLeavingEarly: KnockoutObservable<boolean>;
            checkedDoubleEngraved: KnockoutObservable<boolean>;
            checkedAcknowledgment: KnockoutObservable<boolean>;
            checkedManualInput: KnockoutObservable<boolean>;
            checkedNotCalculated: KnockoutObservable<boolean>;
            checkedExceedByApplication: KnockoutObservable<boolean>;
            
            // variable global store data from service 
            allMainDom: KnockoutObservable<any>;
            outputItemPossibleLst: KnockoutObservableArray<ItemModel>;
            // variable global store data from service 
            
            enableBtnDel: KnockoutObservable<boolean>;
            
            constructor() {
                var self = this;
                self.allMainDom = ko.observable();
                self.outputItemPossibleLst = ko.observableArray([]);
                
                self.items = ko.observableArray([]);
                self.outputItemList = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR001_52"), prop: 'code', width: 70 },
                    { headerText: nts.uk.resource.getText("KWR001_53"), prop: 'name', width: 180 }
                ]);
                self.currentCodeList = ko.observable();
                self.C3_2_value = ko.observable("");
                self.C3_3_value = ko.observable("");
                
                self.roundingRules = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("KWR001_58") },
                    { code: '2', name: nts.uk.resource.getText("KWR001_59") }
                ]);
                self.selectedRuleCode = ko.observable(1);
                self.currentCodeListSwap = ko.observableArray([]);
                self.test = ko.observableArray([]);
                
                self.currentCodeListSwap.subscribe(function(value) {})
                
                self.enableBtnDel = ko.observable(false);
                
                self.currentCodeList.subscribe(function(value) {
                    
                    let codeChoose = _.find(self.outputItemList(), function(o: any) { 
                        return value == o.code; 
                    });
                    if (!_.isUndefined(codeChoose)) {
                        self.C3_2_value(codeChoose.code);
                        self.C3_3_value(codeChoose.name);
                        self.getOutputItemDailyWorkSchedule(_.find(self.allMainDom(), function(o: any) {
                                                                return codeChoose.code == o.itemCode;             
                                                            }));
                        self.enableBtnDel(true);
                    } else {
                        self.C3_2_value('');
                        self.C3_3_value('');
                        self.enableBtnDel(false);
                        // TODO - hoangdd: lam them truong hop chua co code duoc chon
                    }                    
                })
                
//                        self.items(self.outputItemPossibleLst());
//                        self.items.removeAll(self.currentCodeListSwap());
                
                self.checkedRemarksInput = ko.observable(false);
                self.checkedMasterUnregistered = ko.observable(false);
                self.checkedEngraving = ko.observable(false);
                self.checkedImprintingOrderNotCorrect = ko.observable(false);
                self.checkedLeavingEarly = ko.observable(false);
                self.checkedDoubleEngraved = ko.observable(false);
                self.checkedAcknowledgment = ko.observable(false);
                self.checkedManualInput = ko.observable(false);
                self.checkedNotCalculated = ko.observable(false);
                self.checkedExceedByApplication = ko.observable(false);
            }
            
            private getOutputItemDailyWorkSchedule(data: any): void {
                let self = this;
                
                // variable temporary
                let temp2: any[] = [];
                let temp1: any[] = [];
                self.items.removeAll();
                self.currentCodeListSwap.removeAll();
                _.forEach(data.lstDisplayedAttendance, function(value, index) {
                    temp1.push({code: value.attendanceDisplay+"", name: value.attendanceName});    
                })
                _.forEach(self.outputItemPossibleLst(), function(value) {
                    temp2.push(value);
                })
                self.items(temp2);
                self.currentCodeListSwap(temp1);
                
            }
            
            public startPage(): JQueryPromise<void>  {
                var dfd = $.Deferred<void>();
                let self = this;
                self.getDataService().done(function(){
                    if (_.isUndefined(nts.uk.ui.windows.getShared('KWR001_C'))) {
                        self.currentCodeList();
                    } else {
                        self.currentCodeList(nts.uk.ui.windows.getShared('KWR001_C'));   
                    }
                })
                dfd.resolve();
                return dfd.promise();
            }
            
            private getDataService(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                service.getDataStartPage().done(function(data: any) {
                    // variable global store data from service 
                    self.allMainDom(data.outputItemDailyWorkSchedule);
                    
                    // variable temporary 
                    let temp: any[] = [];
                    _.forEach(data.dailyAttendanceItem, function(value) {
                        temp.push(value);
                    })
                    self.outputItemPossibleLst(temp);
                    
                    let arrCodeName: ItemModel[] = [];
                    _.forEach(data.outputItemDailyWorkSchedule, function(value, index) {
                        arrCodeName.push({code: value.itemCode, name: value.itemName});
                    });
                    self.outputItemList(arrCodeName);
                    self.items(data.dailyAttendanceItem);
                    // todo - hoangdd
                    
                    dfd.resolve();
                })
                
                return dfd.promise();
            }

            openScreenD () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_D', 1, true);
                nts.uk.ui.windows.sub.modal('/view/kwr/001/d/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_D');
                });
            }
            
            private saveData(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                let command: any = {};
                command.itemCode = self.C3_2_value();
                command.itemName = self.C3_3_value();
                command.lstDisplayedAttendance = [];
                _.forEach(self.currentCodeListSwap(), function(value, index) {
                    command.lstDisplayedAttendance.push({sortBy: index, itemToDisplay: value.code});
                });
                command.lstRemarkContent = [];
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedRemarksInput()), printitem: 0});
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedMasterUnregistered()), printitem: 1});
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedEngraving()), printitem: 2});
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedImprintingOrderNotCorrect()), printitem: 3});
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedLeavingEarly()), printitem: 4});
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedDoubleEngraved()), printitem: 5});
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedAcknowledgment()), printitem: 6});
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedManualInput()), printitem: 7});
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedNotCalculated()), printitem: 8});
                command.lstRemarkContent.push({usedClassification: self.convertBoolToNum(self.checkedExceedByApplication()), printitem: 9});
                command.workTypeNameDisplay = self.selectedRuleCode();
                command.newMode = _.isUndefined(self.currentCodeList()) ? true : false;
                service.save(command).done(function() { 
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    dfd.resolve();
                })
                
                return dfd.promise();
            }
            
            private convertBoolToNum(value: boolean): number {
                if (value) {
                    return 1;
                }
                return 0;
            }
            
            // return to screen A
            closeScreenC(): void {
                nts.uk.ui.windows.close();
            }
            
            private removeData(): void {
                let self = this;
                service.remove(self.currentCodeList()).done(function() { 
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                })
            }
        }
        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;  
            }
        } 
    }
}