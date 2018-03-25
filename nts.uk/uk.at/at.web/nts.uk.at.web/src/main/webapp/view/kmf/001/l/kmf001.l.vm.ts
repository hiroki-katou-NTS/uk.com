module nts.uk.pr.view.kmf001.l {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        
        export class ScreenModel {
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
                
                self.nursingSetting().selectedManageNursing.subscribe(function(v) {
                    if(v == 0){
                        // 子の看護
                        $('#nursing-month').ntsError('clear');
                        $('#nursing-day').ntsError('clear');
                        $('#nursing-number-leave-day').ntsError('clear');
                        $('#nursing-number-person').ntsError('clear');
                        $('#work-type-code-nursing').ntsError('clear');
                    }
                });
                
                self.childNursingSetting().selectedManageNursing.subscribe(function(v) {
                    if(v == 0){
                         // 介護
                        $('#child-nursing-month').ntsError('clear');
                        $('#child-nursing-day').ntsError('clear');
                        $('#child-nursing-number-leave-day').ntsError('clear');
                        $('#child-nursing-number-person').ntsError('clear');
                        $('#work-type-code-child-nursing').ntsError('clear');
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
                let command = self.toJsObject();
                
                nts.uk.ui.block.grayout();
                
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
                service.findSetting().done(function(res: any) {
                    if (res) {
                        self.initUI(res);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
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
                    $('#nursing-month').ntsEditor('validate');
                    $('#nursing-day').ntsEditor('validate');
                    $('#nursing-number-leave-day').ntsEditor('validate');
                    $('#nursing-number-person').ntsEditor('validate');
                    
                    if (!self.nursingSetting().workTypeCodes() || self.nursingSetting().workTypeCodes().length == 0) {
                        $('#work-type-code-nursing').ntsError('set', {messageId:"Msg_152"});
                    }
                }
                if (self.childNursingSetting().enableNursing()) {
                    $('#child-nursing-month').ntsEditor('validate');
                    $('#child-nursing-day').ntsEditor('validate');
                    $('#child-nursing-number-leave-day').ntsEditor('validate');
                    $('#child-nursing-number-person').ntsEditor('validate');
                    
                    if (!self.childNursingSetting().workTypeCodes()
                            || self.childNursingSetting().workTypeCodes().length == 0) {
                        $('#work-type-code-child-nursing').ntsError('set', {messageId:"Msg_152"});
                    }
                }
                if ($('.nts-input').ntsError('hasError')) {
                    return false;
                }
                return true;
            }
            
            private clearError(): void {
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
                $('#work-type-code-child-nursing').ntsError('clear');
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
                object.workTypeCodes = ob().workTypeCodes();
                
                return object;
            }
            
            private convertModel(ob : KnockoutObservable<NursingSettingModel>, object: any) {
                ob().selectedManageNursing(object.manageType);
                ob().monthDay(object.startMonthDay);
                ob().nursingNumberLeaveDay(object.nursingNumberLeaveDay);
                ob().nursingNumberPerson(object.nursingNumberPerson);
                ob().workTypeCodes(object.workTypeCodes);
                ob().typeCode(object.workType);
            }
            
        }
        
        export class NursingSettingModel {
            
            selectedManageNursing: KnockoutObservable<number>;
            enableNursing: KnockoutObservable<boolean>;
            monthDay: KnockoutObservable<number>;
            nursingNumberLeaveDay: KnockoutObservable<number>;
            nursingNumberPerson: KnockoutObservable<number>;
            workTypeCodes: KnockoutObservableArray<string>;
            typeCode: KnockoutObservable<string>;
            
            parent: ScreenModel;
            
            constructor(parent: ScreenModel) {
                let self = this;
                self.parent = parent;
                self.selectedManageNursing = ko.observable(1);
                self.enableNursing = ko.computed(function() {
                    return self.selectedManageNursing() == 1;
                }, self);
                self.monthDay = ko.observable(null);
                self.nursingNumberLeaveDay = ko.observable(null);
                self.nursingNumberPerson = ko.observable(null);
                self.workTypeCodes = ko.observableArray([]);
                self.typeCode = ko.observable('');
                

            }
            
            private openDialog() {
                let self = this;
                service.findWorkTypeCodes().done(function(res) {
                    
                    nts.uk.ui.windows.setShared('KDL002_Multiple', true);
                    nts.uk.ui.windows.setShared('KDL002_AllItemObj', res);
                    nts.uk.ui.windows.setShared('KDL002_SelectedItemId', self.workTypeCodes());
                    nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml').onClosed(() => {
                        let data = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                        if (!data) {
                            return;
                        }
                        self.workTypeCodes(data.map(item => item.code));
                        self.typeCode(data.map(item => item.code + '.' + item.name).join("、"));
                        if (self.parent.nursingSetting().workTypeCodes()
                                && self.parent.nursingSetting().workTypeCodes().length > 0) {
                            $('#work-type-code-nursing').ntsError('clear');
                        }
                        if (self.parent.childNursingSetting().workTypeCodes()
                                && self.parent.childNursingSetting().workTypeCodes().length > 0) {
                            $('#work-type-code-child-nursing').ntsError('clear');
                        }
                    });
                });
            }
        }
    }
}