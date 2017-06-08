module nts.uk.pr.view.kmf001.l {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        
        export class ScreenModel {
            textEditorOption: KnockoutObservable<any>;
            numberEditorOption: KnockoutObservable<any>;
            manageDistinctList: KnockoutObservableArray<EnumertionModel>;
            
            // 子の看護
            nursingSetting: KnockoutObservable<NursingSettingModel>;
            backupNursingSetting: KnockoutObservable<NursingSettingModel>;
            
            // 介護
            childNursingSetting: KnockoutObservable<NursingSettingModel>;
            backupChildNursingSetting: KnockoutObservable<NursingSettingModel>;
            
            constructor() {
                let self = this;
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textalign: "center"
                }));
                self.numberEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    width: "40px",
                    textalign: "center",
                }));
                self.manageDistinctList = ko.observableArray([]);
                
                self.nursingSetting = ko.observable(new NursingSettingModel());
                self.backupNursingSetting = ko.observable(null);
                
                self.childNursingSetting = ko.observable(new NursingSettingModel());
                self.backupChildNursingSetting = ko.observable(null);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                $.when(self.loadManageDistinctEnums()).done(function() {
                    self.loadSetting();
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
                let command = self.toJsObject();
                service.save(command).done(function() {
                    self.loadSetting().done(function() {
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
                        dfd.resolve();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
            }
            
            private loadSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findSetting().done(function(res: any) {
                    if (res) {
                        self.initUI(res);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            private backSetupVacation(): void {
                nts.uk.request.jump("/view/kmf/001/a/index.xhtml", {});
            }
            
            private toJsObject(): any {
                let self = this;
                let command: any = {};
                
                // 管理しない
                if (self.nursingSetting().selectedManageNursing() == 0 && self.backupNursingSetting()) {
                    self.backupNursingSetting().selectedManageNursing(self.nursingSetting().selectedManageNursing());
                    command.nursingSetting = self.convertObjectCmd(self.backupNursingSetting, 0);
                }
                // 管理する
                else {
                    command.nursingSetting = self.convertObjectCmd(self.nursingSetting, 0);
                }
                // 管理しない
                if (self.childNursingSetting().selectedManageNursing() == 0 && self.backupChildNursingSetting()) {
                    self.backupChildNursingSetting().selectedManageNursing(self.childNursingSetting().selectedManageNursing());
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
                    $('#nursing-month').ntsError('validate');
                    $('#nursing-day').ntsError('validate');
                    $('#nursing-number-leave-day').ntsError('validate');
                    $('#nursing-number-person').ntsError('validate');
                    
                    if (!self.nursingSetting().workTypeCodes() || self.nursingSetting().workTypeCodes().length == 0) {
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_152'));
                        return false;
                    }
                }
                if (self.childNursingSetting().enableNursing()) {
                    $('#child-nursing-month').ntsError('validate');
                    $('#child-nursing-day').ntsError('validate');
                    $('#child-nursing-number-leave-day').ntsError('validate');
                    $('#child-nursing-number-person').ntsError('validate');
                    
                    if (!self.childNursingSetting().workTypeCodes()
                            || self.childNursingSetting().workTypeCodes().length == 0) {
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_152'));
                        return false;
                    }
                }
                if ($('.nts-input').ntsError('hasError')) {
                    return false;
                }
                return true;
            }
            
            private clearError(): void {
                // 子の看護
                $('#nursing-month').ntsError('validate');
                $('#nursing-day').ntsError('validate');
                $('#nursing-number-leave-day').ntsError('validate');
                $('#nursing-number-person').ntsError('validate');
            
                // 介護
                $('#child-nursing-month').ntsError('validate');
                $('#child-nursing-day').ntsError('validate');
                $('#child-nursing-number-leave-day').ntsError('validate');
                $('#child-nursing-number-person').ntsError('validate');
            }
            
            // find enumeration ManageDistinct
            private loadManageDistinctEnums(): JQueryPromise<Array<EnumertionModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findManageDistinct().done(function(res: Array<EnumertionModel>) {
                    self.manageDistinctList(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            private convertObjectCmd(ob : KnockoutObservable<NursingSettingModel>, nursingCategory: number): any {
                let self = this;
                let object: any = {};
                
                object.manageType = ob().selectedManageNursing();
                object.nursingCategory = nursingCategory;
                object.startMonthDay = self.convertMonthDay(ob());
                object.nursingNumberLeaveDay = ob().nursingNumberLeaveDay();
                object.nursingNumberPerson = ob().nursingNumberPerson();
                object.workTypeCodes = ob().workTypeCodes();
                
                return object;
            }
            
            private convertModel(ob : KnockoutObservable<NursingSettingModel>, object: any) {
                ob().selectedManageNursing(object.manageType);
                ob().nursingMonth(parseInt(object.startMonthDay/100));
                ob().nursingDay(object.startMonthDay - ob().nursingMonth() * 100);
                ob().nursingNumberLeaveDay(object.nursingNumberLeaveDay);
                ob().nursingNumberPerson(object.nursingNumberPerson);
                ob().workTypeCodes(object.workTypeCodes);
            }
            
            private convertMonthDay(setting : KnockoutObservable<NursingSettingModel>): number {
                if (!setting.nursingMonth() || !setting.nursingDay()) {
                    return null;
                }
                return parseInt(setting.nursingMonth()) * 100 + parseInt(setting.nursingDay());
            }
        }
        
        export class NursingSettingModel {
            
            selectedManageNursing: KnockoutObservable<number>;
            enableNursing: KnockoutObservable<boolean>;
            nursingMonth: KnockoutObservable<number>;
            nursingDay: KnockoutObservable<number>;
            nursingNumberLeaveDay: KnockoutObservable<number>;
            nursingNumberPerson: KnockoutObservable<number>;
            workTypeCodes: KnockoutObservableArray<string>;
            typeCode: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.selectedManageNursing = ko.observable(1);
                self.enableNursing = ko.computed(function() {
                    return self.selectedManageNursing() == 1;
                }, self);
                self.nursingMonth = ko.observable(null);
                self.nursingDay = ko.observable(null);
                self.nursingNumberLeaveDay = ko.observable(null);
                self.nursingNumberPerson = ko.observable(null);
                self.workTypeCodes = ko.observableArray(["001", "002", "003", "004"]);
                self.typeCode = ko.computed(function() {
                    return self.workTypeCodes().join(", ");
                }, self);
            }
        }
    }
}