module nts.uk.com.view.cmf001.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        systemTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSystemTypes());
        systemType: KnockoutObservable<number>;
        screenMode: KnockoutObservable<number>;
        radioItemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(1, getText('CMF001_56')),
            new model.ItemModel(0, getText('CMF001_57'))
        ]);
        acceptModes: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(0, getText('CMF001_66')),
            new model.ItemModel(1, getText('CMF001_67')),
            new model.ItemModel(2, getText('CMF001_68'))
        ]);
        
        listStandardImportSetting: KnockoutObservableArray<model.StandardAcceptanceConditionSetting>;
        selectedStandardImportSettingCode: KnockoutObservable<string>;
        selectedStandardImportSetting: KnockoutObservable<model.StandardAcceptanceConditionSetting>;
        constructor() {
            var self = this;
            self.systemType = ko.observable(0);
            self.screenMode = ko.observable(model.SCREEN_MODE.NEW);
            self.listStandardImportSetting = ko.observableArray([]);
            self.selectedStandardImportSettingCode = ko.observable('');
            self.selectedStandardImportSetting = ko.observable(new model.StandardAcceptanceConditionSetting('', '', 0, 0, 0, 0));
            self.selectedStandardImportSettingCode.subscribe(data => {
                if (data) {
                    let item = _.find(self.listStandardImportSetting(), x => {return x.dispConditionSettingCode == data;});
                    self.selectedStandardImportSetting(item);
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                }
            });
            
            self.systemType.subscribe((data) => {
                self.listStandardImportSetting([
                    new model.StandardAcceptanceConditionSetting('0'+data+'1', 'Import Setting '+data+'1', 2, 0, 0, 1),
                    new model.StandardAcceptanceConditionSetting('0'+data+'2', 'Import Setting '+data+'2', 1, 1, 0, 1), 
                    new model.StandardAcceptanceConditionSetting('0'+data+'3', 'Import Setting '+data+'3', null, 2, 0, 1),
                    new model.StandardAcceptanceConditionSetting('0'+data+'4', 'Import Setting '+data+'4', 1, 3, 1, 2),
                    new model.StandardAcceptanceConditionSetting('0'+data+'5', 'Import Setting '+data+'5', 0, 0, 1, 3)
                ]);
            });
        }
        
        private openCMF001d() {
            let self = this;
            nts.uk.request.jump("/view/cmf/001/d/index.xhtml", {
                systemType: self.systemType(),
                conditionSetting: ko.toJS(self.selectedStandardImportSetting)
            });
        }
        
        private openCMF001m() {
            let self = this;
            setShared('CMF001mParams', {
                activation: model.M_ACTIVATION.Duplicate_Standard,
                systemType: self.systemType(),
                conditionCode: self.selectedStandardImportSetting().conditionSettingCode(),
                conditionName: self.selectedStandardImportSetting().conditionSettingName()
            }, true);
            
            modal("/view/cmf/001/m/index.xhtml").onClosed(function() {
                var output = getShared('CMF001mOutput');
                if (output) {
                    self.selectedStandardImportSetting().conditionSettingCode(output.code);
                    self.selectedStandardImportSetting().conditionSettingName(output.name);
                }
            });
        }
        
        startPage(code?: string): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            self.listStandardImportSetting.removeAll();

//            service.getAllData(self.selectedCategory()).done(function(data: Array<any>) {
//                if (data && data.length) {
                    self.systemType.valueHasMutated();
                    if (code) 
                        self.selectedStandardImportSettingCode(code);
                    else 
                        self.selectedStandardImportSettingCode(self.listStandardImportSetting()[0].dispConditionSettingCode);
//                } else {
//                    self.createNewAlarmCheckCondition();
//                }
                dfd.resolve();
//            }).fail(function(error) {
//                alertError(error);
//                dfd.reject();
//            }).always(() => {
                block.clear();
//            });
            return dfd.promise();
        }

        private createNewCondition() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.selectedStandardImportSettingCode('');
            self.selectedStandardImportSetting(new model.StandardAcceptanceConditionSetting('', '', 0, 0, 0, 0));
            self.screenMode(model.SCREEN_MODE.NEW);
        }

        registerCondition() {
            let self = this;
            let data = new model.StandardAcceptanceConditionSetting(self.selectedStandardImportSetting().conditionSettingCode(), self.selectedStandardImportSetting().conditionSettingName(), self.selectedStandardImportSetting().deleteExistData(), self.selectedStandardImportSetting().acceptMode(), self.selectedStandardImportSetting().csvDataItemLineNumber(), self.selectedStandardImportSetting().csvDataStartLine());
            let command: any = ko.toJS(data);
//            $("#A3_4").trigger("validate");
//            $("#check-condition-table .nts-editor.nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                self.listStandardImportSetting.push(self.selectedStandardImportSetting());
                self.selectedStandardImportSettingCode(self.selectedStandardImportSetting().conditionSettingCode());
//                service.registerData(command).done(function() {
//                    self.startPage(data.code()).done(() => {
                        info({ messageId: "Msg_15" }).then(() => {
                            if (self.screenMode() == model.SCREEN_MODE.UPDATE) {
//                                $("#A3_4").focus();
                            } else {
//                                $("#A3_2").focus();
                            }
                        });
//                    });
//                }).fail(error => {
//                    alertError(error);
//                }).always(() => {
                    block.clear();
//                });
            }
        }

        deleteCondition() {
            let self = this, data = self.selectedStandardImportSetting();
            nts.uk.ui.errors.clearAll();
            let command: any = ko.toJS(data);

            confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                let indexItemDelete = _.findIndex(self.listStandardImportSetting(), (item: model.StandardAcceptanceConditionSetting) => { return item.conditionSettingCode() == data.conditionSettingCode(); });
                self.listStandardImportSetting.remove(function(item) { return item.conditionSettingCode() == data.conditionSettingCode(); });
//                service.deleteData(command).done(function() {
//                    self.startPage().done(() => {
                        if (self.listStandardImportSetting().length == 0) {
                            self.createNewCondition();
                        } else {
                            if (indexItemDelete == self.listStandardImportSetting().length) {
                                self.selectedStandardImportSettingCode(self.listStandardImportSetting()[indexItemDelete - 1].conditionSettingCode());
                            } else {
                                self.selectedStandardImportSettingCode(self.listStandardImportSetting()[indexItemDelete].conditionSettingCode());
                            }
                        }
                        info({ messageId: "Msg_16" }).then(() => {
                            if (self.screenMode() == model.SCREEN_MODE.UPDATE) {
//                                $("#A3_4").focus();
                            } else {
//                                $("#A3_2").focus();
                            }
                        });
//                    });
//                }).fail(error => {
//                    alertError(error);
//                }).always(() => {
                    block.clear();
//                });

            }).ifNo(() => {
            });
        }
    }
}