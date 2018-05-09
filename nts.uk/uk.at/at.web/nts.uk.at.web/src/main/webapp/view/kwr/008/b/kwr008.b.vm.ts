module nts.uk.at.view.kwr008.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.at.view.kwr008.share.model;
    
    export class ScreenModel {
        //enum mode
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);

        //enum value output format
        valueOutputFormat: KnockoutObservableArray<any> = ko.observableArray([]);

        //B5_1
        //excessTime: KnockoutObservable<boolean> = ko.observable(false);

        //B2_2
        listStandardImportSetting: KnockoutObservableArray<model.OutputSettingCodeDto> = ko.observableArray([]);
        selectedCode: KnockoutObservable<any> = ko.observable('');
        currentSetOutputSettingCode :KnockoutObservable<SetOutputSettingCode>
                = ko.observable(new SetOutputSettingCode(null));
        //B3_2 B3_3
        //inputSettingCode: KnockoutObservable<string> = ko.observable('');
        //inputProjectName: KnockoutObservable<string> = ko.observable('');

        //B5_3
        itemRadio: KnockoutObservableArray<any> = ko.observableArray([]);
        //selectedItemRadio: KnockoutObservable<number> = ko.observable(0);

        //B4
        outputItem: KnockoutObservableArray<any> = ko.observableArray([]);
        

        constructor() {
            let self = this;

            //B5_3
            self.itemRadio = ko.observableArray([
                new model.ItemModel(0, getText('KWR008_37')),
                new model.ItemModel(1, getText('KWR008_38')),
                new model.ItemModel(2, getText('KWR008_39'))
            ]);
            
            //table fixed
            $('#fixed-table').ntsFixedTable({ height: 304, width: 900 });
             //event select change
            self.selectedCode.subscribe((code) => {
                nts.uk.ui.errors.clearAll()
                self.outputItem.removeAll();
                if (code) {
                    service.getListItemOutput(code).done(r => {
                        if (r && r.length > 0) {
                            for(var i = 0; i < r.length; i++) {
                                self.outputItem.push(new OutputItemData(i+1, r[i].cd, r[i].useClass, r[i].headingName, r[i].valOutFormat, ''));
                            }
                        }
                    });
                    self.updateMode(code);
                } else {
                    self.registerMode();
                }
            });
        }

        public startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            
            block.invisible();

            //fill data B2_2
            service.getOutItemSettingCode().done((data) => {
                for (let i = 0, count = data.length; i < count; i++) {
                    self.listStandardImportSetting.push(new SetOutputSettingCode(data[i]));
                }
                self.listStandardImportSetting_Sort();
                self.checkListItemOutput();
                service.getValueOutputFormat().done(data => {
                    for (let i = 0, count = data.length; i < count; i++) {
                        self.valueOutputFormat.push(new model.ItemModel(data[i].value, data[i].localizedName));
                    }
                }).always(function() {
                    dfd.resolve(self);
                    block.clear();
                });
            }).always(function() {
                dfd.resolve(self);
                block.clear();
            });


            
            return dfd.promise();
        }

        listStandardImportSetting_Sort() {
            let self = this;
            self.listStandardImportSetting.sort((a, b) => {
                return (a.cd === b.cd) ? 0 : (a.cd < b.cd) ? -1 : 1;
            });
        }
        //Open dialog KDW007
        openKDW007(idx) {
            let self = this;
            nts.uk.ui.block.invisible();
            let index = _.findIndex(self.outputItem(), (x) => {return x.index() === idx(); });
            if (index == -1) {
                nts.uk.ui.block.clear();
                return;
            }
            
            //let lstOpeItems: _.map(self.listOperationCds, ((item) => { return item.code; }));
            self.getListItemByAtr(self.outputItem()[index].valueOutputFormat()).done((lstItem) => {
                let lstItemCode = lstItem.map((item) => { return item.attendanceItemId; });
                let lstAddItems = _.filter(self.outputItem()[index].listOperationSetting(), (item) => {
                        return item.operation();
                    }).map((item) => { return item.cd; });
                let lstSubItems = _.filter(self.outputItem()[index].listOperationSetting(), (item) => {
                        return !item.operation();
                    }).map((item) => { return item.cd; });
                let param = {
                            lstAllItems: lstItemCode,
                            lstAddItems: lstAddItems,
                            lstSubItems: lstSubItems
                        };
                nts.uk.ui.windows.setShared("KDW007Params", param);
                nts.uk.ui.windows.sub.modal("at", "/view/kdw/007/c/index.xhtml").onClosed(() => {
                    let resultData = nts.uk.ui.windows.getShared("KDW007CResults");
                    if (!resultData) {
                        nts.uk.ui.block.clear();
                        return;
                    }
                    let operationName = "";

                    self.outputItem()[index].listOperationSetting.removeAll();
                    if (resultData.lstAddItems && resultData.lstAddItems.length > 0) {
                        //add
                        service.getAttendanceItemByCodes(resultData.lstAddItems).done((lstItems) => {
                            _.forEach(lstItems, (item) => {
                                self.outputItem()[index].listOperationSetting.push(new OperationCondition(item.attendanceItemId, false, item.attendanceItemName));
                                if (operationName) {
                                    operationName = operationName + " + " + item.attendanceItemName;
                                } else {
                                    operationName = item.attendanceItemName;
                                }
                            });
                            
                            //sub
                            if (resultData.lstSubItems && resultData.lstSubItems.length > 0) {
                                service.getAttendanceItemByCodes(resultData.lstSubItems).done((lstItems) => {
                                    _.forEach(lstItems, (item) => {
                                        self.outputItem()[index].listOperationSetting.push(new OperationCondition(item.attendanceItemId, false, item.attendanceItemName));
                                        if (operationName) {
                                            operationName = operationName + " - " + item.attendanceItemName;
                                        } else {
                                            operationName = item.attendanceItemName;
                                        }
                                    });
                                });
                                self.outputItem()[index].outputTargetItem(operationName);
                            } else {
                                self.outputItem()[index].listSubCds([]);
                            }
                        });
                        nts.uk.ui.block.clear();
                    } else {
                        self.outputItem()[index].listAddCds([]);
                        //sub
                            if (lstSubItems && lstSubItems.length > 0) {
                                service.getAttendanceItemByCodes(lstSubItems).done((lstItems) => {
                                    _.forEach(lstItems, (item) => {
                                        self.outputItem()[index].listOperationSetting.push(new OperationCondition(item.attendanceItemId, false, item.attendanceItemName));
                                        if (operationName) {
                                            operationName = operationName + " - " + item.attendanceItemName;
                                        } else {
                                            operationName = item.attendanceItemName;
                                        }
                                    });
                                });
                                self.outputItem()[index].outputTargetItem(operationName);
                            } else {
                                self.outputItem()[index].listSubCds([]);
                            }
                        nts.uk.ui.block.clear();
                    }
                    self.outputItem()[index].outputTargetItem(operationName);
                    nts.uk.ui.block.clear();
                });
             });
        }
        // get data for kdw007
        getListItemByAtr(valueOutputFormat) {
            let self = this;
            let dfd = $.Deferred<any>();
            if (valueOutputFormat === 0) {
                //With type 回数 - Times
                service.getAttendanceItemByAtr(2).done((lstAtdItem) => {
                    service.getOptItemByAtr(1).done((lstOptItem) => {
                        for (let i = 0; i < lstOptItem.length; i++) {
                            lstAtdItem.push(lstOptItem[i]);
                        }
                        dfd.resolve(lstAtdItem);
                    });
                });
            } else if (valueOutputFormat === 1) {
                //With type 時間 - Time
                service.getAttendanceItemByAtr(5).done((lstAtdItem) => {
                    service.getOptItemByAtr(0).done((lstOptItem) => {
                        for (let i = 0; i < lstOptItem.length; i++) {
                            lstAtdItem.push(lstOptItem[i]);
                        }
                        dfd.resolve(lstAtdItem);
                    });
                });
            } else if (valueOutputFormat === 2) {
                //With type 時刻 - TimeWithDay
                service.getAttendanceItemByAtr(6).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (valueOutputFormat === 3) {
                //With type 金額 - AmountMoney
                service.getAttendanceItemByAtr(3).done((lstAtdItem) => {
                    service.getOptItemByAtr(2).done((lstOptItem) => {
                        for (let i = 0; i < lstOptItem.length; i++) {
                            lstAtdItem.push(lstOptItem[i]);
                        }
                        dfd.resolve(lstAtdItem);
                    });
                });
            }
            return dfd.promise();
        }

        checkListItemOutput() {
            var self = this;

            if (self.listStandardImportSetting().length == 0) {
                self.isNewMode(true);
                self.registerMode();
            } else {
                self.isNewMode(false);
                if (!self.selectedCode()) {
                    self.selectedCode(self.listStandardImportSetting()[0].cd());
                }
            }
        }

        //mode update
        updateMode(code: string) {
            let self = this;
            if (code) {
                let selectedIndex = _.findIndex(self.listStandardImportSetting(), (obj) => { return obj.cd() == code; });
    
                self.isNewMode(false);
                if (selectedIndex > -1) {
                    self.currentSetOutputSettingCode(self.listStandardImportSetting()[selectedIndex]);
                    $('#B3_3').focus();
                }else {
                    self.selectedCode('');
                }
            } 
            for (var i = self.outputItem().length; i < 10; i++) {
                self.outputItem.push(new OutputItemData(i+1, '', false, '', 0, ''));
            }
        }

        //mode register
        registerMode() {
            let self = this;

            self.isNewMode(true);
            $("#B3_2").focus();
            $('#listStandardImportSetting').ntsGridList('deselectAll');
            self.currentSetOutputSettingCode(new SetOutputSettingCode(null));
            for (var i = 1; i <= 10; i++) {
                self.outputItem.push(new OutputItemData(i, '', false, '', 0, ''));
            }
        }

        //do register
        doRegister() {
            let self = this;
            block.invisible();
            let itemOut : any = _.filter(self.outputItem(), v=>{return v.headingName().trim();});
            
            if(!itemOut || itemOut.length == 0){
                alertError({messageId:"Msg_880"});
                block.clear();
                return;
            }
            for(var i = 0; i < itemOut.length; i++ ) {
                // item Rule 36 - do not checking
                if (itemOut.index == 0) {
                    continue;
                }
                if (itemOut.listOperationSetting.lenth == 0) {
                    alertError({messageId:"Msg_881"});
                    block.clear();
                    return;
                } else if (itemOut.listOperationSetting.lenth > 50) {
                    alertError({messageId:"Msg_882"});
                    block.clear();
                    return;
                }
            }
            $('.nts-input').trigger("validate");
            if(nts.uk.ui.errors.hasError()) {
                block.clear();
                return;
            }
            
            self.currentSetOutputSettingCode().buildListItemOutput(itemOut);
            let data : model.OutputSettingCodeDto = ko.toJS(self.currentSetOutputSettingCode);
            
            if (self.screenMode() == model.SCREEN_MODE.NEW) {
                service.registerOutputItemSetting(data).done(() => {
                    info({ messageId: 'Msg_15' }).then(() => {
                        self.listStandardImportSetting.push(self.currentSetOutputSettingCode);
                        self.listStandardImportSetting_Sort();
                        self.selectedCode(self.currentSetOutputSettingCode().cd());
                    });
                }).fail(err=>{
                    $('#B3_2').ntsError('set', err);
                }).always(function() {
                    block.clear();
                });
            } else {
                service.updateOutputItemSetting(data).done(() => {
                    let selectedIndex = _.findIndex(self.listStandardImportSetting(), (obj) => { return obj.cd == self.selectedCode(); });
                    if (selectedIndex > -1) {
                        //self.listStandardImportSetting.replace(self.listStandardImportSetting()[selectedIndex], data);
                        self.listStandardImportSetting()[selectedIndex](self.currentSetOutputSettingCode);
                    }
                    info({ messageId: 'Msg_15' });
                }).fail(err=>{
                    $('#B3_2').ntsError('set', err);
                }).always(function() {
                    block.clear();
                });
            }
        }

        //do delete
        doDelete() {
            var self = this;

            confirm({ messageId: 'Msg_18' }).ifYes(() => {

                let selectedIndex = _.findIndex(self.listStandardImportSetting(), (obj) => { return obj.cd() == self.selectedCode(); });

                let data = ko.toJS(self.listStandardImportSetting()[selectedIndex]);
                // send request remove item
                service.deleteOutputItemSetting(data).done(() => {
                    info({ messageId: 'Msg_16' });
                    self.listStandardImportSetting.splice(selectedIndex, 1);
                    if (self.listStandardImportSetting().length == 0) {
                        self.selectedCode('');
                    } else {
                        if (selectedIndex >= self.listStandardImportSetting().length) {
                            self.selectedCode(self.listStandardImportSetting()[self.listStandardImportSetting().length].cd());
                        } else {
                            self.selectedCode(self.listStandardImportSetting()[selectedIndex].cd());
                        }
                    }
                });

            });
        }

        //cancel register
        doCancel() {
            let self = this;
            setShared("KWR008_B_Result", self.selectedCode());
            nts.uk.ui.windows.close();
        }


    }

    export class OperationCondition {
        cd: KnockoutObservable<string>= ko.observable('');
        operation: KnockoutObservable<boolean>= ko.observable(true); //true: '+'; false: '-'
        name: KnockoutObservable<string>= ko.observable('');
        constructor(cd: string, operation: boolean, name: string) {
            let self = this;
            self.cd(cd || '');
            self.operation(operation || true);
            self.name(name || '');
        }
    }
    export class OutputItemData {
        index: KnockoutObservable<number>= ko.observable(1);
        cd: KnockoutObservable<number>= ko.observable(0);
        useClassification: KnockoutObservable<boolean>= ko.observable(false);
        headingName: KnockoutObservable<string>= ko.observable('');
        valueOutputFormat: KnockoutObservable<number>= ko.observable(0);
        outputTargetItem: KnockoutObservable<string>= ko.observable('');
        listOperationSetting: KnockoutObservableArray<OperationCondition> = ko.observableArray([]);
        constructor(index: number, cd: number, useClassification: boolean, headingName: string, valueOutputFormat: number, outputTargetItem: string) {
            let self = this;
            self.index(index || 1);
            self.cd(cd || 0);
            self.useClassification(useClassification || false);
            self.headingName(headingName || '');
            self.valueOutputFormat(valueOutputFormat || 0);
            self.outputTargetItem(outputTargetItem || '');
        }
        
        buildListOperationSetting(listOperation: Array<any>) {
            let self = this;
            if (listOperation && listOperation.length > 0) {
                for(var i = 0; i < listOperation.length; i++) {
                    self.listOperationSetting.push(new  OperationCondition(
                        listOperation[i].cd, 
                        listOperation[i].operation,
                        listOperation[i].name));
                }
            } else {
                self.listOperationSetting([]);
            }
        }
    }
    
    export class SetOutputSettingCode {
        cd: KnockoutObservable<string>= ko.observable('');
        displayCode: string;
        name: KnockoutObservable<string>= ko.observable('');
        displayName: string;
        outNumExceedTime36Agr: KnockoutObservable<boolean> = ko.observable(false);
        displayFormat: KnockoutObservable<number> = ko.observable(0);
        listItemOutput : KnockoutObservableArray<OutputItemData> = ko.observableArray([]);
        constructor(param) {
            let self = this;
            self.cd(param ? param.cd || '' : '');
            self.displayCode = self.cd();
            self.name(param ? param.name || '' : '');
            self.displayName = self.name();
            self.outNumExceedTime36Agr(param ? param.outNumExceedTime36Agr || false : false);
            self.displayFormat(param ? param.displayFormat || 0 : 0);
            
        }
        buildListItemOutput(listItemOutput: Array<any>) {
            let self = this;
            if (listItemOutput && listItemOutput.length > 0) {
                for(var i = 0; i < listItemOutput.length; i++) {
                    var outputItemData = new OutputItemData(
                        i++,
                        listItemOutput[i].cd, 
                        listItemOutput[i].useClassification,
                        listItemOutput[i].headingName,
                        listItemOutput[i].valueOutputFormat,
                        listItemOutput[i].outputTargetItem);
                    if (listItemOutput[i].listOperationSetting) {
                        outputItemData.buildListOperationSetting(listItemOutput[i].listOperationSetting);
                    }
                    self.listItemOutput.push(outputItemData)
                }
            } else {
                self.listItemOutput([]);
            }
        }
    }
}
