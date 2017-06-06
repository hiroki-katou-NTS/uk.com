module nts.uk.pr.view.kmf001.l {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        
        export class ScreenModel {
            textEditorOption: KnockoutObservable<any>;
            manageDistinctList: KnockoutObservableArray<EnumertionModel>;
            
            // 子の看護
            nursingSetting: KnockoutObservable<NursingSettingModel>;
            
            // 介護
            childNursingSetting: KnockoutObservable<NursingSettingModel>;
            
            constructor() {
                let self = this;
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textalign: "center"
                }));
                self.manageDistinctList = ko.observableArray([]);
                
                self.nursingSetting = ko.observable(new NursingSettingModel(1, null, null, null, null,
                    ["KDL001", "KDL002", "KDL003", "KDL004"]));
                self.childNursingSetting = ko.observable(new NursingSettingModel(1, null, null, null, null,
                    ["KDL001", "KDL002", "KDL003", "KDL004"]));
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
                command.nursingSetting = self.nursingSetting();
                command.childNursingSetting = self.childNursingSetting();
                return command;
            }
            
            private initUI(res: any): any {
                let self = this;
                self.nursingSetting(res.nursingSetting);
                self.childNursingSetting(res.childNursingSetting);
            }
            
            private validate(): boolean {
                let self = this;
                self.clearError();
                if (self.nursingSetting().enableNursing()) {
                    $('#nursing-month').ntsError('validate');
                    $('#nursing-day').ntsError('validate');
                    $('#nursing-number-leave-day').ntsError('validate');
                    $('#nursing-number-person').ntsError('validate');
                }
                if (self.childNursingSetting().enableNursing()) {
                    $('#child-nursing-month').ntsError('validate');
                    $('#child-nursing-day').ntsError('validate');
                    $('#child-nursing-number-leave-day').ntsError('validate');
                    $('#child-nursing-number-person').ntsError('validate');
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
            
//            constructor() {
//                let self = this;
//                self.selectedManageNursing = ko.observable(1);
//                self.enableNursing = ko.computed(function() {
//                    return self.selectedManageNursing() == 1;
//                }, self);
//                self.nursingMonth = ko.observable(null);
//                self.nursingDay = ko.observable(null);
//                self.nursingNumberLeaveDay = ko.observable(null);
//                self.nursingNumberPerson = ko.observable(null);
//                self.workTypeCodes = ko.observableArray(["KDL001", "KDL002", "KDL003", "KDL004"]);
//                self.typeCode = ko.computed(function() {
//                    return self.workTypeCodes().join(", ");
//                }, self);
//            }
            
            constructor(manageNursing: number, nursingMonth: number, nursingDay: number, nursingNumberLeaveDay: number,
                    nursingNumberPerson: number, workTypeCodes: Array<string>) {
                let self = this;
                self.selectedManageNursing = ko.observable(manageNursing);
                self.enableNursing = ko.computed(function() {
                    return self.selectedManageNursing() == 1;
                }, self);
                self.nursingMonth = ko.observable(nursingMonth);
                self.nursingDay = ko.observable(nursingDay);
                self.nursingNumberLeaveDay = ko.observable(nursingNumberLeaveDay);
                self.nursingNumberPerson = ko.observable(nursingNumberPerson);
                self.workTypeCodes = ko.observableArray(workTypeCodes);
                self.typeCode = ko.computed(function() {
                    return self.workTypeCodes().join(", ");
                }, self);
            }
        }
    }
}