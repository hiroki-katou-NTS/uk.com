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
    import errors = nts.uk.ui.errors;

    export class ScreenModel {
        //enum mode
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);

        //enum value output format
        valOutFormat: KnockoutObservableArray<any> = ko.observableArray([]);

        //年間勤務表印刷形式
        listSheetPrintingForm: KnockoutObservableArray<any> = ko.observableArray([
            new model.ItemModel(0, getText('KWR008_53')),
            new model.ItemModel(1, getText('KWR008_54'))
        ]);

        //B2_2
        listStandardImportSetting: KnockoutObservableArray<SetOutputItemOfAnnualWorkSchDto> = ko.observableArray([]);
        selectedLayoutId: KnockoutObservable<string> = ko.observable('');
        currentSetOutputSettingCode: KnockoutObservable<SetOutputItemOfAnnualWorkSchDto> = ko.observable(new SetOutputItemOfAnnualWorkSchDto(null));

        //B5_3
        itemRadio: KnockoutObservableArray<any> = ko.observableArray([]);
        //Rule 36.
        rule36CalculationName: string;

        //B4
        outputItem: KnockoutObservableArray<ItemsOutputToBookTable> = ko.observableArray([]);

        isCheckedAll: KnockoutObservable<boolean> = ko.observable(false);

        selectedPrintForm: KnockoutObservable<number> = ko.observable(null);
        
        rule36CalculationAverageName: string;
        
        contentSelectionOutput: KnockoutObservableArray<ItemEnum> = ko.observableArray([]);

        attendanceItem: share.model.AttendanceItemDto[] = [];

        constructor() {
            let self = this;

            //B5_3
            self.itemRadio = ko.observableArray([
                new model.ItemModel(1, getText('KWR008_38')),
                new model.ItemModel(2, getText('KWR008_39'))
            ]);
            self.rule36CalculationName = getText('KWR008_71');
            self.rule36CalculationAverageName = getText('KWR008_70');
            for (var i = 1; i <= 11; i++) {
                self.outputItem.push(new ItemsOutputToBookTable(
                    i,
                    null,
                    false,
                    '',
                    0,
                ));
            }
            //event select change
            self.selectedLayoutId.subscribe((settingId) => {
                _.defer(() => { errors.clearAll() });
                errors.clearAll();
                block.invisible();
                if (settingId) {
                    // TODO
                } else {
                    block.clear();
                    self.registerMode();
                }
            });

            self.selectedPrintForm.subscribe(data => {
                if (data == 0) {
                    self.outputItem()[0].useClass(false);
                    self.outputItem()[1].useClass(false);
                } else if (data == 1) {
                    self.outputItem()[0].useClass(true);
                    self.outputItem()[1].useClass(true);
                }
            });

            self.isCheckedAll = ko.computed({
                read: function() {
                    let itemOut: any = _.filter(self.outputItem(), (x) => { return !x.useClass(); });
                    if (itemOut && itemOut.length > 0) {
                        return false;
                    } else {
                        return true;
                    }
                },
                write: function(val) {
                    ko.utils.arrayForEach(self.outputItem(), function(item) {
                        item.useClass(val);
                        if ((item.sortBy() == 1 || item.sortBy() == 2) && self.selectedPrintForm() == 0) {
                            item.useClass(false);
                        }
                    });
                },
                owner: this
            });

            $('#table-output-items').ntsFixedTable({ height: 350, width: 1200 });
        }

        public startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            block.invisible();
            //fill data B2_2
            let KWR008BParam = nts.uk.ui.windows.getShared("KWR008_B_Param");
            let selectionType = share.model.SelectionClassification.STANDARD;
            if (KWR008BParam) {
                self.selectedPrintForm(KWR008BParam.printFormat);
                selectionType = KWR008BParam.selectionType;
            }

            $.when(service.getValueOutputFormat()
                 , service.findAllBySettingType(selectionType, this.selectedPrintForm())
                 , self.getAtdItemsByDisplayFormat(self.selectedPrintForm()))
            .done((data1: Array<model.EnumConstantDto>, data2: Array<model.SetOutputItemOfAnnualWorkSchDto>) => {
                // get list value output format
                let listValOutFormat = [];
                for (let i of data1) {
                    listValOutFormat.push(new model.ItemModel(i.value + '', i.localizedName));
                }
                self.valOutFormat(listValOutFormat);

                var dataSorted = _.sortBy(data2, ['cd']);
                for (let i = 0, count = data2.length; i < count; i++) {
                    self.listStandardImportSetting.push(new SetOutputItemOfAnnualWorkSchDto(dataSorted[i]));
                }
            }).always(function() {
                dfd.resolve(self);
                //get parameter from B
                
                if (KWR008BParam && KWR008BParam.selectedCd) {
                    self.selectedLayoutId(KWR008BParam.selectedCd);
                    self.updateMode(KWR008BParam.selectedCd);
                } else { //case no param
                    self.checkListItemOutput();
                }
                block.clear();
            });

            if (KWR008BParam.printFormat === 1) {
                self.listStandardImportSetting.push(new SetOutputItemOfAnnualWorkSchDto({
                    cd: '02',
                    name: 'Fake data 36'
                }));
            } else {
                self.listStandardImportSetting.push(new SetOutputItemOfAnnualWorkSchDto({
                    cd: '01',
                    name: 'Fake data'
                }));
            }
            
            return dfd.promise();
        }

        checkEnable36(data: ItemsOutputToBookTable) {
            let self = this;
            if ((data.sortBy() == 1 || data.sortBy() == 2) && self.selectedPrintForm() == 0)
                return false;
            return true;
        }

        listStandardImportSetting_Sort() {
            let self = this;
            self.listStandardImportSetting.sort((a, b) => {
                return (+a.cd() === +b.cd()) ? 0 : (+a.cd() < +b.cd()) ? -1 : 1;
            });
        }

        buildOutputItemByOper(lstItems: Array<any>, outputItem: SetOutputItemOfAnnualWorkSchDto, isAdd: boolean) {
            let self = this;
            let operationName = "";
            // TODO
            // if (calculationItem.itemsForAdd()) {
            //     _.forEach(calculationItem.itemsForAdd(), (addItem) => {
            //         const attendanceItems: model.AttendanceItemDto[] = self.attendanceItem.filter((item) => item.attendanceItemId === addItem);
            //         const targetItem = attendanceItems.length > 0 ? attendanceItems[0] : null;
            //         if (targetItem) {
            //             if (operationName) {
            //                 operationName = operationName + " + " + targetItem.attendanceItemName;
            //             } else {
            //                 operationName = targetItem.attendanceItemName;
            //             }
            //         }
            //     });
            // }

            // if (calculationItem.itemsForSubtract()) {
            //     _.forEach(calculationItem.itemsForSubtract(), (addItem) => {
            //         const attendanceItems: model.AttendanceItemDto[] = self.attendanceItem.filter((item) => item.attendanceItemId === addItem);
            //         const targetItem = attendanceItems.length > 0 ? attendanceItems[0] : null;
            //         if (targetItem) {
            //             if (operationName) {
            //                 operationName = operationName + " - " + targetItem.attendanceItemName;
            //             } else {
            //                 operationName = targetItem.attendanceItemName;
            //             }
            //         }
            //     });
            // }

            return operationName;
        }

        //check list output when start page
        checkListItemOutput() {
            var self = this;

            if (self.listStandardImportSetting().length == 0) {
                self.registerMode();
            } else {
                if (!self.selectedLayoutId()) {
                    self.selectedLayoutId(self.listStandardImportSetting()[0].layoutId);
                    self.updateMode(self.listStandardImportSetting()[0].layoutId);
                }
            }
        }
        //mode update
        updateMode(code: string) {
            let self = this;


        }

        //mode register
        registerMode() {
            let self = this;

            self.isNewMode(true);
            self.currentSetOutputSettingCode(new SetOutputItemOfAnnualWorkSchDto(null));
            // self.selectedPrintForm(0);
            self.selectedLayoutId('');
            for (var i = 0; i < self.outputItem().length; i++) {
                // TODO
            }

            $("#B3_2").focus();
        }

        //do register or update
        doRegister() {
            let self = this;
            block.invisible();
            let itemOutByName: any = _.filter(self.outputItem(), v => { return v.headingName().trim(); });
            if (!self.isValidate(itemOutByName)) {
                alertError({ messageId: "Msg_880" });
                block.clear();
                return;
            }

            for (var i = 0; i < itemOutByName.length; i++) {
                // item Rule 36 - do not checking
                if (itemOutByName[i].item36AgreementTime()) {
                    continue;
                }
                if (itemOutByName[i].listOperationSetting().length == 0) {
                    alertError({ messageId: "Msg_881" });
                    block.clear();
                    return;
                } else if (itemOutByName[i].listOperationSetting().length > 50) {
                    alertError({ messageId: "Msg_882" });
                    block.clear();
                    return;
                }
            }
            $('.nts-input').trigger("validate");
            if (errors.hasError()) {
                block.clear();
                return;
            }

            //insert item 36
            if (itemOutByName[0].listOperationSetting().length == 0) {
                itemOutByName[0].listOperationSetting.push(
                    new CalculationFormulaOfItem(
                        202,    //attendanceItemId
                        1,      // operation
                        getText('KWR008_71')
                    )
                );
            }
            if (itemOutByName[1].listOperationSetting().length == 0) {
                itemOutByName[1].listOperationSetting.push(
                    new CalculationFormulaOfItem(
                        202,    //attendanceItemId
                        1,      // operation
                        getText('KWR008_70')
                    )
                );
            }
            self.currentSetOutputSettingCode().printForm(self.selectedPrintForm());
            self.currentSetOutputSettingCode().buildListItemOutput(ko.toJS(itemOutByName));
            let data: model.SetOutputItemOfAnnualWorkSchDto = ko.toJS(self.currentSetOutputSettingCode);

            if (self.isNewMode()) { //register
                service.registerOutputItemSetting(data).done(() => {
                   
                }).fail(err => {
                    alertError({ messageId: err.messageId });
                }).always(function() {
                    block.clear();
                });
            } else { //update
                service.updateOutputItemSetting(data).done(() => {
                    
                    info({ messageId: 'Msg_15' }).then(() => {
                        self.selectedLayoutId(data.layoutId);
                        $('#B3_3').focus();
                    });
                }).fail(err => {
                    console.log(err);
                }).always(function() {
                    block.clear();
                });
            }
        }

        isValidate(itemOut: ItemsOutputToBookTable[]) {
            let itemOutUseClass: any = _.filter(itemOut, (v: ItemsOutputToBookTable) => { return v.useClass(); });
            return itemOutUseClass.length > 0;
        }

        //do delete
        doDelete() {
            const vm = this;
            confirm({ messageId: 'Msg_18' }).ifYes(() => {

                let selectedIndex = _.findIndex(vm.listStandardImportSetting(), (obj) => { return obj.layoutId == vm.selectedLayoutId(); });

                let data = ko.toJS(vm.listStandardImportSetting()[selectedIndex]);
                vm.listStandardImportSetting.remove((item: any) => { return item.settingId() == data.settingId; });
                // send request remove item
                service.deleteOutputItemSetting(data).done(() => {
                    info({ messageId: 'Msg_16' }).then(() => {
                        if (vm.listStandardImportSetting().length == 0) {
                            vm.selectedLayoutId('');
                        } else {
                            if (selectedIndex >= vm.listStandardImportSetting().length) {
                                vm.selectedLayoutId(vm.listStandardImportSetting()[vm.listStandardImportSetting().length - 1].layoutId);
                            } else {
                                vm.selectedLayoutId(vm.listStandardImportSetting()[selectedIndex].layoutId);
                            }
                        }
                    });
                });

            });
        }

        //cancel register
        doCancel() {
            let self = this;
            setShared("KWR008_B_Result", { selectedCd: self.selectedLayoutId() });
            nts.uk.ui.windows.close();
        }

        openCopyDialogC() {
            const self = this;
            const param = {
                selectCode: self.currentSetOutputSettingCode().cd(),
                selectName: self.currentSetOutputSettingCode().name()
            };
            nts.uk.ui.windows.setShared("KWR008CParam", param);
            nts.uk.ui.windows.sub.modal("at", "/view/kwr/008/c/index.xhtml").onClosed(() => {
               // TODO
            });
        }

        private getAtdItemsByDisplayFormat(displayFormat: number) {
            const self = this;
            const dfd = $.Deferred();
            service.getAtdItemsByDisplayFormat(displayFormat).done((result) => {
                self.attendanceItem = result;
                dfd.resolve();
            });
            return dfd.promise();
        }

        openKDL048Dialog(dataNode: ItemsOutputToBookTable) {
            const vm = this;
            let attendanceItemTransfer: share.model.AttendanceItemShare = new share.model.AttendanceItemShare();
            // タイトル行.出力項目コード = B3_2
            attendanceItemTransfer.titleLine.layoutCode = vm.currentSetOutputSettingCode().cd();
            // タイトル行.出力項目名 = B3_3
            attendanceItemTransfer.titleLine.layoutName = vm.currentSetOutputSettingCode().name();
            attendanceItemTransfer.titleLine.displayFlag = true;
            
            // 項目名行.名称 = B4_14
            attendanceItemTransfer.itemNameLine.name = dataNode.headingName();
            // 属性.選択済み = B4_16
            attendanceItemTransfer.attribute.selected = Number(dataNode.valOutFormat());
            attendanceItemTransfer.itemNameLine.displayFlag = true;
            attendanceItemTransfer.itemNameLine.displayInputCategory = 2;
            attendanceItemTransfer.columnIndex = 0;
            attendanceItemTransfer.exportAtr = 0;
            attendanceItemTransfer.attribute.attributeList = _.map(vm.valOutFormat(), (item) => new model.AttendaceType(item.code, item.name));

            let selectedLst: any[] = [];
            if (!!dataNode.calculationExpression()) {
                // TODO filter lst add and substract
                // selectedLst = _.map(dataNode.outputTargetItem().itemsForAdd(), (item) => new share.model.SelectedTimeItem({
                //     itemId: item.toString(),
                //     operator: '+'
                // }));
                // selectedLst.push(_.map(dataNode.outputTargetItem().itemsForSubtract(), (item) => new share.model.SelectedTimeItem({
                //     itemId: item.toString(),
                //     operator: '-'
                // })));
            }
            attendanceItemTransfer.selectedTimeList = selectedLst;
            let atdCanUsed = _.map(vm.attendanceItem, (mapItem: share.model.AttendanceItemDto) => new share.model.AtdItemKDL048Model({
                                    id: mapItem.attendanceItemId,
                                    name: mapItem.attendanceItemName,
                                    attendanceAtr: mapItem.attendanceAtr,
                                    indicatesNumber: mapItem.displayNumbers
                                }));
            attendanceItemTransfer.diligenceProjectList = atdCanUsed;
            setShared('attendanceItem', attendanceItemTransfer, true);

            nts.uk.ui.windows.sub.modal("at", "/view/kdl/048/index.xhtml").onClosed(() => {
                // TODO
             });
        }
    }

    export class CalculationFormulaOfItem {
        /** 勤怠項目. */
        attendanceItemId: KnockoutObservable<number> = ko.observable(null);
        /** オペレーション. */
        operation: KnockoutObservable<number> = ko.observable(0); //0: '-'; 1: '+'
        /** Attendance name */
        name: KnockoutObservable<string> = ko.observable('');
        constructor(attendanceItemId: number, operation: number, name: string) {
            let self = this;
            self.attendanceItemId(attendanceItemId || null);
            self.operation(operation || 0);
            self.name(name || '');
        }
    }

    export class ItemsOutputToBookTable {
        /** 並び順. */
        sortBy: KnockoutObservable<number> = ko.observable(1);
        /** コード. */
        itemOutCd: KnockoutObservable<string> = ko.observable('');
        /** 使用区分 */
        useClass: KnockoutObservable<boolean> = ko.observable(false);
        /** 見出し名称. */
        headingName: KnockoutObservable<string> = ko.observable('');
        /** 値の出力形式 */
        valOutFormat: KnockoutObservable<number> = ko.observable(0);
        /** 出力対象項目 */
        listOperationSetting: KnockoutObservableArray<CalculationFormulaOfItem> = ko.observableArray([]);
        /**  */
        calculationExpression: KnockoutObservable<string> = ko.observable('');
        constructor(sortBy: number
                  , itemOutCd: string
                  , useClass: boolean
                  , headingName: string
                  , valOutFormat: number) {
            let self = this;
            self.valOutFormat.subscribe((data) => {
                self.buildOutputTargetItem([]);
                if (self.sortBy() > 1) {
                    self.calculationExpression('');
                }
            });
            self.sortBy(sortBy || 1);
            self.headingName(headingName);
            self.useClass(useClass);
            self.itemOutCd(itemOutCd || '');
            self.valOutFormat(valOutFormat || 0);
        }

        updateData(sortBy: number
                 , itemOutCd: string
                 , useClass: boolean
                 , headingName: string
                 , valOutFormat: number
                 , listOperationSetting: CalculationFormulaOfItem[]) {
            let self = this;
            self.sortBy(sortBy || 1);
            self.itemOutCd(itemOutCd || '');
            self.useClass(useClass || false);
            self.headingName(headingName || '');
            self.valOutFormat(valOutFormat || 0);
            self.listOperationSetting(listOperationSetting ? listOperationSetting : []);
        }

        buildOutputTargetItem(listOperationSetting: any[]) {
            let self = this;
            if (listOperationSetting && listOperationSetting.length > 0) {
                for (var i = 0; i < listOperationSetting.length; i++) {
                    self.listOperationSetting.push(new CalculationFormulaOfItem(
                        listOperationSetting[i].attendanceItemId,
                        listOperationSetting[i].operation,
                        listOperationSetting[i].name));
                }
            } else {
                self.listOperationSetting([]);
            }
        }
    }

    export class SetOutputItemOfAnnualWorkSchDto {
        layoutId: string;
        settingType: number;
        cd: KnockoutObservable<string> = ko.observable('');
        displayCode: string;
        name: KnockoutObservable<string> = ko.observable('');
        displayName: string;
        outNumExceedTime36Agr: KnockoutObservable<boolean> = ko.observable(false);
        monthsInTotalDisplay: KnockoutObservable<number> = ko.observable(0);
        listItemsOutput: KnockoutObservableArray<ItemsOutputToBookTable> = ko.observableArray([]);
        printForm: KnockoutObservable<number> = ko.observable(0);
        
        totalAverageDisplay: KnockoutObservable<string> = ko.observable('1');
        multiMonthDisplay: KnockoutObservable<boolean> = ko.observable(false);

        constructor(param: any) {
            let self = this;
            if (param) {
                self.layoutId = param.layoutId || '';
                self.settingType = param.settingType || 0;
                self.cd(param.cd || '');
                self.displayCode = self.cd();
                self.name(param.name || '');
                self.displayName = self.name();
                self.outNumExceedTime36Agr(param.outNumExceedTime36Agr || false);
                self.monthsInTotalDisplay(param.monthsInTotalDisplay || false);
                self.printForm(param.printForm || 0);
                self.printForm.subscribe(data => {
                    self.printForm(data);
                });
                self.totalAverageDisplay(param ? param.totalAverageDisplay || '1' : '1');
                self.multiMonthDisplay(param ? param.multiMonthDisplay : false);
            }
        }

        buildListItemOutput(listItemOutput: Array<any>) {
            let self = this;
            if (listItemOutput && listItemOutput.length > 0) {
                self.listItemsOutput([]);
                for (var i = 0; i < listItemOutput.length; i++) {
                    var outputItemData = new ItemsOutputToBookTable(
                        i + 1,
                        listItemOutput[i].cd,
                        listItemOutput[i].useClass,
                        listItemOutput[i].headingName,
                        listItemOutput[i].valOutFormat);
                    if (listItemOutput[i].outputTargetItem) {
                        outputItemData.buildOutputTargetItem(listItemOutput[i].outputTargetItem);
                    }
                    self.listItemsOutput.push(outputItemData);
                }
            } else {
                self.listItemsOutput([]);
            }
        }
    }

    enum ATTR {
        DAILY = 0,
        MONTHLY = 1
    }

    enum MonthlyAttendanceItemAtr {
        /** The time. */
        TIME = 1,

        /** The number. */
        NUMBER = 2,

        /** The days. */
        DAYS = 3,

        /** The amount. */
        AMOUNT = 4
    }
    
    class ItemEnum {
        value: string;
        name: string;
        constructor(value: string, name: string) {
            this.value = value;
            this.name = name;
        }
    }
}