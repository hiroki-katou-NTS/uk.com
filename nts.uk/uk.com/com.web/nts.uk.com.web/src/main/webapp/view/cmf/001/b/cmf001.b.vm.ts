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
        deleteExistDataMethod: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getDeleteExistDataMethod());
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
            self.selectedStandardImportSetting = ko.observable(new model.StandardAcceptanceConditionSetting(0, '', '', 1));
            self.selectedStandardImportSettingCode.subscribe((data) => {
                if (data) {
                    block.invisible();
                    let d1 = service.getOneStdData(self.systemType(), data);
                    let d2 = service.getAllStdItemData(self.systemType(), data);
                    $.when( d1, d2 ).done(function ( result, rs ) {
                        if (result) {
                            let item = new model.StandardAcceptanceConditionSetting(
                                result.systemType, result.conditionSetCode, result.conditionSetName, 
                                result.deleteExistData, result.acceptMode, result.csvDataItemLineNumber, 
                                result.csvDataStartLine, result.characterCode, result.deleteExistDataMethod, result.categoryId);
                            self.selectedStandardImportSetting(item);
                            self.screenMode(model.SCREEN_MODE.UPDATE);
                            if (!nts.uk.util.isNullOrUndefined(self.transitData)) $("#B4_21").focus();
                            _.defer(() => {nts.uk.ui.errors.clearAll()});
                        }
                        if (rs && rs.length) {
                            self.selectedStandardImportSetting().alreadySetting(true);
                        }
                    }).fail(function(error) {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                } else {
                    self.createNewCondition();
                    setTimeout(() => {
                        nts.uk.ui.errors.clearAll();
                    }, 10);
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
                conditionCode: self.selectedStandardImportSetting().conditionSetCode()
            });
        }
        
        private openCMF001m() {
            let self = this;
            setShared('CMF001mParams', {
                activation: model.M_ACTIVATION.Duplicate_Standard,
                systemType: self.systemType(),
                conditionCode: self.selectedStandardImportSetting().conditionSetCode(),
                conditionName: self.selectedStandardImportSetting().conditionSetName()
            }, true);
            
            modal("/view/cmf/001/m/index.xhtml").onClosed(function() {
                let output = getShared('CMF001mOutput');
                if (output) {
                    block.invisible();
                    let isOverride = output.checked;
                    let desCode = output.code;
                    let desName = output.name;
                    
                    //process copy condition setting.
                    let copyParam : any = {systemType: self.selectedStandardImportSetting().systemType(),
                                            sourceCondSetCode: self.selectedStandardImportSetting().conditionSetCode(),
                                            destCondSetCode: desCode,
                                            destCondSetName: desName,
                                            override: isOverride};
                    service.copyStdData(copyParam).done(function(sourceCondSet: any){
                        //Reload grid condition setting
                        self.getAllData(desCode);
                    }).fail(function(error) {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });;
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
                        return new model.StandardAcceptanceConditionSetting(rs.systemType, rs.conditionSetCode, rs.conditionSetName, rs.deleteExistData);
                    });
//                    _rsList = _.sortBy(_rsList, ['code']);
                    if (code) {
                        if (code == self.selectedStandardImportSettingCode())
                            self.selectedStandardImportSettingCode.valueHasMutated();
                        else
                            self.selectedStandardImportSettingCode(code);
                    }
                    else {
                        if (self.init() && !nts.uk.util.isNullOrUndefined(self.transitData) && !nts.uk.util.isNullOrUndefined(self.transitData.conditionCode)) {
                            self.selectedStandardImportSettingCode(self.transitData.conditionCode);
                            self.init(false);
                        } else
                            self.selectedStandardImportSettingCode(_rsList[0].dispConditionSettingCode);
                    }
                    self.listStandardImportSetting(_rsList);
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
            self.selectedStandardImportSetting(new model.StandardAcceptanceConditionSetting(self.systemType(), '', '', 1));
            self.screenMode(model.SCREEN_MODE.NEW);
            $("#B4_3").focus();
        }

        registerCondition() {
            let self = this;
            let data = new model.StandardAcceptanceConditionSetting(
                self.selectedStandardImportSetting().systemType(), 
                self.selectedStandardImportSetting().conditionSetCode(), 
                self.selectedStandardImportSetting().conditionSetName(), 
                self.selectedStandardImportSetting().deleteExistData(), 
                self.selectedStandardImportSetting().acceptMode(), 
                self.selectedStandardImportSetting().csvDataItemLineNumber(), 
                self.selectedStandardImportSetting().csvDataStartLine(), 
                self.selectedStandardImportSetting().characterCode(), 
                self.selectedStandardImportSetting().deleteExistDataMethod(), 
                self.selectedStandardImportSetting().categoryId());
            if (data.deleteExistData() ==  model.NOT_USE_ATR.NOT_USE) {
                data.deleteExistDataMethod(null);
            } else {
                data.acceptMode(null);
            }
            data.action(self.screenMode());
            let command: any = ko.toJS(data);
            $(".nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                service.registerStdData(command).done(function() {
                    self.getAllData(data.conditionSetCode()).done(() => {
                        info({ messageId: "Msg_15" }).then(() => {
                            if (self.screenMode() != model.SCREEN_MODE.UPDATE) $("#B4_3").focus();
                            else $("#B3_4_container").focus();
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
            nts.uk.ui.errors.clearAll();
            let self = this, data = self.selectedStandardImportSetting();
            data.systemType(self.systemType());
            let command: any = ko.toJS(data);

            confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                let indexItemDelete = _.findIndex(self.listStandardImportSetting(), (item: model.StandardAcceptanceConditionSetting) => { return item.conditionSetCode() == data.conditionSetCode(); });
                self.listStandardImportSetting.remove(function(item) { return item.conditionSetCode() == data.conditionSetCode(); });
                service.deleteStdData(command).done(function() {
                    if (self.listStandardImportSetting().length == 0) {
                        self.selectedStandardImportSettingCode(null);
                    } else {
                        if (indexItemDelete == self.listStandardImportSetting().length) {
                            self.selectedStandardImportSettingCode(self.listStandardImportSetting()[indexItemDelete - 1].conditionSetCode());
                        } else {
                            self.selectedStandardImportSettingCode(self.listStandardImportSetting()[indexItemDelete].conditionSetCode());
                        }
                    }

                    self.getAllData(self.selectedStandardImportSettingCode()).done(() => {
                        info({ messageId: "Msg_16" }).then(() => {
                            if (self.screenMode() != model.SCREEN_MODE.UPDATE) {
                                $("#B4_3").focus();
                            } else {
                                $("#B3_4_container").focus();
                            }
                        });
                    });
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });

            }).ifNo(() => {
            });
        }
    }
}
