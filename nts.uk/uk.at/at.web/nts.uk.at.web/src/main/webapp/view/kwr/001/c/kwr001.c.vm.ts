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
            enableCodeC3_2: KnockoutObservable<boolean>;
            
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
                self.enableCodeC3_2 = ko.observable(false);
                
                self.currentCodeList.subscribe(function(value) {
                    
                    let codeChoose = _.find(self.outputItemList(), function(o: any) { 
                        return value == o.code; 
                    });
                    if (!_.isUndefined(codeChoose) && !_.isNull(codeChoose)) {
                        self.C3_2_value(codeChoose.code);
                        self.C3_3_value(codeChoose.name);
                        self.getOutputItemDailyWorkSchedule(_.find(self.allMainDom(), function(o: any) {
                                                                return codeChoose.code == o.itemCode;             
                                                            }));
                        self.enableBtnDel(true);
                        self.enableCodeC3_2(false);
                    } else {
                        self.C3_2_value('');
                        self.C3_3_value('');
                        self.getOutputItemDailyWorkSchedule([]);
                        self.enableBtnDel(false);
                        self.enableCodeC3_2(true);
                    }                    
                })
                
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
            
            /*
             * set data to C7_2, C7_8 
            */
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
                // refresh data for C7_2
                self.items(temp2);
                // refresh data for C7_8
                self.currentCodeListSwap(temp1);
                
                if (!_.isEmpty(data)) {
                    self.checkedRemarksInput(self.convertNumToBool(data.lstRemarkContent[0].usedClassification));
                    self.checkedMasterUnregistered(self.convertNumToBool(data.lstRemarkContent[1].usedClassification));
                    self.checkedEngraving(self.convertNumToBool(data.lstRemarkContent[2].usedClassification));
                    self.checkedImprintingOrderNotCorrect(self.convertNumToBool(data.lstRemarkContent[3].usedClassification));
                    self.checkedLeavingEarly(self.convertNumToBool(data.lstRemarkContent[4].usedClassification));
                    self.checkedDoubleEngraved(self.convertNumToBool(data.lstRemarkContent[5].usedClassification));
                    self.checkedAcknowledgment(self.convertNumToBool(data.lstRemarkContent[6].usedClassification));
                    self.checkedManualInput(self.convertNumToBool(data.lstRemarkContent[7].usedClassification));
                    self.checkedNotCalculated(self.convertNumToBool(data.lstRemarkContent[8].usedClassification));
                    self.checkedExceedByApplication(self.convertNumToBool(data.lstRemarkContent[9].usedClassification));    
                } else {
                    self.setRemarksContentDefault();
                }
                
            }
            
            public startPage(): JQueryPromise<void>  {
                var dfd = $.Deferred<void>();
                let self = this;
                self.getDataService().done(function(){
                    if (_.isUndefined(nts.uk.ui.windows.getShared('KWR001_C'))) {
                        self.currentCodeList(null);
                    } else {
                        self.currentCodeList(nts.uk.ui.windows.getShared('KWR001_C'));
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }
            
            /*
             *  get data from server
            */
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
                        arrCodeName.push({code: value.itemCode+"", name: value.itemName});
                    });
                    self.outputItemList(arrCodeName);
                    self.items(data.dailyAttendanceItem);
                    dfd.resolve();
                })
                
                return dfd.promise();
            }

            /*
             *  open screen D
            */
            openScreenD () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_D', self.currentCodeList(), true);
                nts.uk.ui.windows.sub.modal('/view/kwr/001/d/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_D');
                });
            }
            
            /*
             *  save data to server
            */
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
                command.newMode = (_.isUndefined(self.currentCodeList()) || _.isNull(self.currentCodeList())) ? true : false;
                service.save(command).done(function() { 
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.getDataService().done(function(){
                        dfd.resolve();
                    })
                    
                }).fail(function(err) {
                    nts.uk.ui.dialog.error(err);
                    dfd.reject();
                })
                
                return dfd.promise();
            }
            
            private newMode(): void {
                let self = this;
                self.currentCodeList('');
                self.C3_2_value('');
                self.C3_3_value('');
                self.getOutputItemDailyWorkSchedule([]);
                self.enableBtnDel(false);
            }
            
            private convertBoolToNum(value: boolean): number {
                if (value) {
                    return 1;
                }
                return 0;
            }
            
            private convertNumToBool(value: number): boolean {
                if (value == 1) {
                    return true;
                }
                return false;
            }
            
            // return to screen A
            closeScreenC(): void {
                nts.uk.ui.windows.close();
            }
            
            /*
             *  set default data
            */
            private setRemarksContentDefault(): void {
                let self = this;
                self.checkedRemarksInput(false);
                self.checkedMasterUnregistered(false);
                self.checkedEngraving(false);
                self.checkedImprintingOrderNotCorrect(false);
                self.checkedLeavingEarly(false);
                self.checkedDoubleEngraved(false);
                self.checkedAcknowledgment(false);
                self.checkedManualInput(false);
                self.checkedNotCalculated(false);
                self.checkedExceedByApplication(false);
            }
            
            /*
             *  remove data       
            */
            private removeData(): void {
                let self = this;
                service.remove(self.currentCodeList()).done(function() {
                    let indexCurrentCode = _.findIndex(self.outputItemList(), function(value, index) {
                        return self.currentCodeList() == value.code;
                    })
                    
                    // self.currentCodeList only have 1 element in list
                    if (self.outputItemList().length == 1) {
                        self.currentCodeList(null);
                    } 
                    // when current code was selected is last element in list self.currentCodeList
                    else if (indexCurrentCode == (self.outputItemList().length - 1)) {
                        self.currentCodeList(self.outputItemList()[indexCurrentCode-1].code);
                    } 
                    // when current code was selected in place middle in list self.currentCodeList
                    else {
                        self.currentCodeList(self.outputItemList()[indexCurrentCode+1].code);
                    }
                    self.getDataService().done(function(){
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    })
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