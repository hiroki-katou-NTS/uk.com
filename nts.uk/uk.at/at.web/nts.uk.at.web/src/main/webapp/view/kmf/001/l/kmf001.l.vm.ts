module nts.uk.pr.view.kmf001.l {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        import message = nts.uk.resource.getMessage;
        
        export class ScreenModel {
            numberEditorOption: KnockoutObservable<any>;
            manageDistinctList: KnockoutObservableArray<EnumertionModel>;
            
            // 子の看護
            nursingSetting: KnockoutObservable<NursingSettingModel>;
            backupNursingSetting: KnockoutObservable<NursingSettingModel>;
            
            // 介護
            childNursingSetting: KnockoutObservable<NursingSettingModel>;
            backupChildNursingSetting: KnockoutObservable<NursingSettingModel>;
            
            nursingLeaveSpecialHolidayList: KnockoutObservableArray<ItemModel>;
            nursingLeaveWorkAbsenceList: KnockoutObservableArray<ItemModel>;
            
            constructor() {
                let self = this;
                self.numberEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    width: "60",
                    textalign: "center",
                    unitID: "DAYS"
                }));
                self.manageDistinctList = ko.observableArray([]);
                
                self.nursingSetting = ko.observable(new NursingSettingModel(self));
                self.backupNursingSetting = ko.observable(null);
                
                self.childNursingSetting = ko.observable(new NursingSettingModel(self));
                self.backupChildNursingSetting = ko.observable(null);
                
                self.nursingLeaveSpecialHolidayList = ko.observableArray([]);
                self.nursingLeaveWorkAbsenceList = ko.observableArray([]);
                
                self.nursingSetting().selectedManageNursing.subscribe(function(v) {
                    if(v == 0){
                        // 子の看護
                        $('#nursing-month').ntsError('clear');
                        $('#nursing-day').ntsError('clear');
                        $('#nursing-number-leave-day').ntsError('clear');
                        $('#nursing-number-person').ntsError('clear');
                    }
                });
                
                self.childNursingSetting().selectedManageNursing.subscribe(function(v) {
                    if(v == 0){
                         // 介護
                        $('#child-nursing-month').ntsError('clear');
                        $('#child-nursing-day').ntsError('clear');
                        $('#child-nursing-number-leave-day').ntsError('clear');
                        $('#child-nursing-number-person').ntsError('clear');
                    }
                });
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                $.when(self.loadManageDistinctEnums()).done(function() {
                    self.loadSetting().done(() => {
                        $("#manage-nursing").focus();
                    });
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private save(): void {
                let self = this;
                let dfd = $.Deferred();
                if (!self.validate()) {
                    return;
                }
                nts.uk.ui.block.invisible();
                let command = self.toJsObject();
                service.save(command).done(function() {
                    self.loadSetting().done(function() {
                        $("#manage-nursing").focus();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        dfd.resolve();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
            
            private loadSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                let itemNull = new ItemModel("0", "なし");
                service.findAllSpecialHoliday().done(function(allSpecialSetting: Array<any>) {
                    var specialSettingList: ItemModel[] = [];
                    specialSettingList.push(itemNull);
                    allSpecialSetting.forEach(specialHolidayItem => {
                        let item = new ItemModel(specialHolidayItem.specialHdFrameNo, specialHolidayItem.specialHdFrameName);
                        specialSettingList.push(item);
                    });
                    self.nursingLeaveSpecialHolidayList(specialSettingList);
                    
                    service.findAllAbsenceFrame().done(function(allAbsenceSetting: Array<any>) {
                        var absenceSettingList: ItemModel[] = [];
                        absenceSettingList.push(itemNull);
                        allAbsenceSetting.forEach(absenceFrameItem => {
                            let item = new ItemModel(absenceFrameItem.absenceFrameNo, absenceFrameItem.absenceFrameName);
                            absenceSettingList.push(item);
                        });
                        self.nursingLeaveWorkAbsenceList(absenceSettingList);
                        
                        service.findSetting().done(function(res: any) {
                            if (res) {
                                self.initUI(res);
                            }
                        dfd.resolve();
                        }).fail(function(res) {
                            nts.uk.ui.dialog.alertError(res.message);
                        });
                        
                    });
                    
                });
                
                return dfd.promise();
            }
            
            private toJsObject(): any {
                let self = this;
                let command: any = {};
                
                // 管理しない
                if (self.nursingSetting().selectedManageNursing() == 0 && self.backupNursingSetting()) {
                    self.backupNursingSetting().selectedManageNursing(self.nursingSetting().selectedManageNursing());
                    self.backupNursingSetting().nursingLeaveSpecialHoliday(self.nursingSetting().nursingLeaveSpecialHoliday());
                    self.backupNursingSetting().nursingLeaveWorkAbsence(self.nursingSetting().nursingLeaveWorkAbsence());
                    command.nursingSetting = self.convertObjectCmd(self.backupNursingSetting, 0);
                }
                // 管理する
                else {
                    command.nursingSetting = self.convertObjectCmd(self.nursingSetting, 0);
                }
                // 管理しない
                if (self.childNursingSetting().selectedManageNursing() == 0 && self.backupChildNursingSetting()) {
                    self.backupChildNursingSetting().selectedManageNursing(self.childNursingSetting().selectedManageNursing());
                    self.backupChildNursingSetting().nursingLeaveSpecialHoliday(self.childNursingSetting().nursingLeaveSpecialHoliday());
                    self.backupChildNursingSetting().nursingLeaveWorkAbsence(self.childNursingSetting().nursingLeaveWorkAbsence());
                    command.childNursingSetting = self.convertObjectCmd(self.backupChildNursingSetting, 1);
                }
                // 管理する
                else {
                    command.childNursingSetting = self.convertObjectCmd(self.childNursingSetting, 1);
                }
                return command;
            }
            
            private initUI(res: any): any {
                let self = this;
                if (res) {
                    // NURSING
                    self.convertModel(self.nursingSetting, res[0]);
                    self.backupNursingSetting(new NursingSettingModel());
                    self.convertModel(self.backupNursingSetting, res[0]);
                    
                    // CHILD NURSING
                    self.convertModel(self.childNursingSetting, res[1]);
                    self.backupChildNursingSetting(new NursingSettingModel());
                    self.convertModel(self.backupChildNursingSetting, res[1]);
                }
            }
            
            private validate(): boolean {
                let self = this;
                self.clearError();
                if (self.nursingSetting().enableNursing()) {
                    $('#nursing-month').ntsEditor('validate');
                    $('#nursing-day').ntsEditor('validate');
                    $('#nursing-number-leave-day').ntsEditor('validate');
                    $('#nursing-number-person').ntsEditor('validate');
                    
//                    if (!self.nursingSetting().workTypeCodes() || self.nursingSetting().workTypeCodes().length == 0) {
//                        $('#work-type-code-nursing').ntsError('set', {messageId:"Msg_152"});
//                    }
                }
                if (self.childNursingSetting().enableNursing()) {
                    $('#child-nursing-month').ntsEditor('validate');
                    $('#child-nursing-day').ntsEditor('validate');
                    $('#child-nursing-number-leave-day').ntsEditor('validate');
                    $('#child-nursing-number-person').ntsEditor('validate');
                    
//                    if (!self.childNursingSetting().workTypeCodes()
//                            || self.childNursingSetting().workTypeCodes().length == 0) {
//                        $('#work-type-code-child-nursing').ntsError('set', {messageId:"Msg_152"});
//                    }
                }
                if ($('.nts-input').ntsError('hasError')) {
                    return false;
                }
                
                if (self.nursingSetting().selectedManageNursing() == 1) {
                    if (self.nursingSetting().monthDay() == 0) {
                        $('#nursing-month').ntsEditor('validate');
                        $('#nursing-day').ntsEditor('validate');
                        return false;
                    }
                }

                if (self.childNursingSetting().selectedManageNursing() == 1) {
                    if (self.childNursingSetting().monthDay() == 0) {
                        $('#child-nursing-month').ntsEditor('validate');
                        $('#child-nursing-day').ntsEditor('validate');
                        return false;
                    }
                }

                if ((self.nursingSetting().selectedManageNursing() == 1 && self.nursingSetting().nursingLeaveSpecialHoliday() == 0 && self.nursingSetting().nursingLeaveWorkAbsence() == 0)
                    || (self.childNursingSetting().selectedManageNursing() == 1 && self.childNursingSetting().nursingLeaveSpecialHoliday() == 0 && self.childNursingSetting().nursingLeaveWorkAbsence() == 0)) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_1368", message: message("Msg_1368")});
                    return false;
                }
                if (self.nursingSetting().selectedManageNursing() == 1 && self.childNursingSetting().selectedManageNursing() == 1 ) {
                    if (self.nursingSetting().nursingLeaveSpecialHoliday() == self.childNursingSetting().nursingLeaveSpecialHoliday()
                        && self.nursingSetting().nursingLeaveSpecialHoliday() != 0) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_1366", message: message("Msg_1366")});
                        return false;
                    } else if (self.nursingSetting().nursingLeaveWorkAbsence() == self.childNursingSetting().nursingLeaveWorkAbsence()
                        && self.nursingSetting().nursingLeaveWorkAbsence() != 0) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_1367", message: message("Msg_1367")});
                        return false;
                    }
                }
                if (self.nursingSetting().selectedManageNursing() == 1) {
                    if (self.nursingSetting().nursingNumberLeaveDay() < 5) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_1369", messageParams: [5]});
                        return false;
                    }
                    if (self.nursingSetting().nursingNumberPerson() < 10) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_1369", messageParams: [10]});
                        return false;
                    }
                }
                if (self.childNursingSetting().selectedManageNursing() == 1) {
                    if (self.childNursingSetting().nursingNumberLeaveDay() < 5) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_1369", messageParams: [5]});
                        return false;
                    }
                    if (self.childNursingSetting().nursingNumberPerson() < 10) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_1369", messageParams: [10]});
                        return false;
                    }
                }
                
    
                return true;
            }
            
            public clearError(): void {
                // 子の看護
                $('#nursing-month').ntsError('clear');
                $('#nursing-day').ntsError('clear');
                $('#nursing-number-leave-day').ntsError('clear');
                $('#nursing-number-person').ntsError('clear');
                $('#work-type-code-nursing').ntsError('clear');
            
                // 介護
                $('#child-nursing-month').ntsError('clear');
                $('#child-nursing-day').ntsError('clear');
                $('#child-nursing-number-leave-day').ntsError('clear');
                $('#child-nursing-number-person').ntsError('clear');
            }
            
            // find enumeration ManageDistinct
            private loadManageDistinctEnums(): JQueryPromise<Array<EnumertionModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findManageDistinct().done(function(res: Array<EnumertionModel>) {
                    self.manageDistinctList(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }
            
            private convertObjectCmd(ob : KnockoutObservable<NursingSettingModel>, nursingCategory: number): any {
                let self = this;
                let object: any = {};
                
                object.manageType = ob().selectedManageNursing();
                object.nursingCategory = nursingCategory;
                object.startMonthDay = ob().monthDay();
                object.nursingNumberLeaveDay = ob().nursingNumberLeaveDay();
                object.nursingNumberPerson = ob().nursingNumberPerson();
                object.specialHolidayFrame = ob().nursingLeaveSpecialHoliday();
                object.absenceWork = ob().nursingLeaveWorkAbsence();
                
                return object;
            }
            
            private convertModel(ob : KnockoutObservable<NursingSettingModel>, object: any) {
                ob().selectedManageNursing(object.manageType);
                ob().monthDay(object.startMonthDay);
                ob().nursingNumberLeaveDay(object.nursingNumberLeaveDay);
                ob().nursingNumberPerson(object.nursingNumberPerson);
                ob().nursingLeaveSpecialHoliday(object.specialHolidayFrame);
                ob().nursingLeaveWorkAbsence(object.absenceWorkDay);
            }
            
        }
        
        export class NursingSettingModel {
            
            selectedManageNursing: KnockoutObservable<number>;
            enableNursing: KnockoutObservable<boolean>;
            monthDay: KnockoutObservable<number>;
            nursingNumberLeaveDay: KnockoutObservable<number>;
            nursingNumberPerson: KnockoutObservable<number>;
            
            nursingLeaveSpecialHoliday: KnockoutObservable<number>;
            nursingLeaveWorkAbsence: KnockoutObservable<number>;
            
            parent: ScreenModel;
            
            constructor(parent: ScreenModel) {
                let self = this;
                self.parent = parent;
                self.selectedManageNursing = ko.observable(1);
                self.enableNursing = ko.computed(function() {
                    return self.selectedManageNursing() == 1;
                }, self);
                self.monthDay = ko.observable(101);
                self.nursingNumberLeaveDay = ko.observable(0);
                self.nursingNumberPerson = ko.observable(0);
                self.nursingLeaveSpecialHoliday = ko.observable(0);
                self.nursingLeaveWorkAbsence = ko.observable(0);
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