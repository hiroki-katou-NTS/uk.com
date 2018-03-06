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
        systemTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
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
        transitData: any;
        init: KnockoutObservable<boolean> = ko.observable(true);
        constructor(data?: any) {
            var self = this;
            if (data) self.transitData = data;
            self.systemType = ko.observable(-1);
            self.screenMode = ko.observable(model.SCREEN_MODE.NEW);
            self.listStandardImportSetting = ko.observableArray([]);
            self.selectedStandardImportSettingCode = ko.observable('');
            self.selectedStandardImportSetting = ko.observable(new model.StandardAcceptanceConditionSetting('', '', 1, -1, 0, 0));
            self.selectedStandardImportSettingCode.subscribe((data) => {
                nts.uk.ui.errors.clearAll();
                if (data) {
                    block.invisible();
                    service.getOneStdData(self.systemType(), data).done((result) => {
                        if (result) {
                            let item = new model.StandardAcceptanceConditionSetting(result.conditionSettingCode, result.conditionSettingName, result.deleteExistData, result.acceptMode, result.csvDataItemLineNumber, result.csvDataStartLine);
                            self.selectedStandardImportSetting(item);
                            self.screenMode(model.SCREEN_MODE.UPDATE);
                            service.getAllStdItemData(self.systemType(), data).done((rs) => {
                                if (rs && rs.length) {
                                    self.selectedStandardImportSetting().alreadySetting(true);
                                }
                            });
                        }
                    }).fail(function(error) {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
            });
            
            self.systemType.subscribe((data) => {
                nts.uk.ui.errors.clearAll();
                self.getAllData();
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
        
        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getSysTypes().done(function(data: Array<any>) {
                if (data && data.length) {
                    let _rsList: Array<model.ItemModel> = _.map(data, rs => {
                        return new model.ItemModel(rs.type, rs.name);
                    });
                    _rsList = _.sortBy(_rsList, ['code']);
                    self.systemTypes(_rsList);
                    if (self.init() && !nts.uk.util.isNullOrUndefined(self.transitData) && !nts.uk.util.isNullOrUndefined(self.transitData.sysType))
                        self.systemType(self.transitData.sysType);
                    else
                        self.systemType(self.systemTypes()[0].code);
                } else {
                    nts.uk.request.jump("/view/cmf/001/a/index.xhtml");
                }
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        getAllData(code?: string): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            self.listStandardImportSetting.removeAll();

            service.getAllStdData(self.systemType()).done(function(data: Array<any>) {
                if (data && data.length) {
                    let _rsList: Array<model.StandardAcceptanceConditionSetting> = _.map(data, rs => {
                        return new model.StandardAcceptanceConditionSetting(rs.conditionSettingCode, rs.conditionSettingName, rs.deleteExistData, rs.acceptMode, rs.csvDataItemLineNumber, rs.csvDataStartLine);
                    });
//                    _rsList = _.sortBy(_rsList, ['code']);
                    self.listStandardImportSetting(_rsList);
                    if (code) 
                        self.selectedStandardImportSettingCode(code);
                    else {
                        if (self.init() && !nts.uk.util.isNullOrUndefined(self.transitData) && !nts.uk.util.isNullOrUndefined(self.transitData.conditionCode)) {
                            self.selectedStandardImportSettingCode(self.transitData.conditionCode);
                            self.init(false);
                        } else
                            self.selectedStandardImportSettingCode(self.listStandardImportSetting()[0].dispConditionSettingCode);
                    }
                } else {
                    self.createNewCondition();
                }
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        private createNewCondition() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.selectedStandardImportSettingCode('');
            self.selectedStandardImportSetting(new model.StandardAcceptanceConditionSetting('', '', 1, -1, 0, 0));
            self.screenMode(model.SCREEN_MODE.NEW);
        }

        registerCondition() {
            let self = this;
            let data = new model.StandardAcceptanceConditionSetting(self.selectedStandardImportSetting().conditionSettingCode(), self.selectedStandardImportSetting().conditionSettingName(), self.selectedStandardImportSetting().deleteExistData(), self.selectedStandardImportSetting().acceptMode(), self.selectedStandardImportSetting().csvDataItemLineNumber(), self.selectedStandardImportSetting().csvDataStartLine());
            let command: any = ko.toJS(data);
            $(".nts-input").trigger("validate");
//            $("#check-condition-table .nts-editor.nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
//                self.listStandardImportSetting.push(self.selectedStandardImportSetting());
//                self.selectedStandardImportSettingCode(self.selectedStandardImportSetting().conditionSettingCode());
                service.registerStdData(command).done(function() {
                    self.getAllData(data.conditionSettingCode()).done(() => {
                        info({ messageId: "Msg_15" }).then(() => {
                            if (self.screenMode() == model.SCREEN_MODE.UPDATE) {
//                                $("#A3_4").focus();
                            } else {
//                                $("#A3_2").focus();
                            }
                        });
                    });
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
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
//                    self.getAllData().done(() => {
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