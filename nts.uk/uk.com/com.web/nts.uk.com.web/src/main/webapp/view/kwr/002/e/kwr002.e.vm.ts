module nts.uk.com.view.kwr002.e {

    export module viewmodel {

        export class ScreenModel {

            layoutCode: KnockoutObservable<string>;
            layoutName: KnockoutObservable<string>;
            dailyMonthly: KnockoutObservable<string>;
            columnIndex: KnockoutObservable<string>;
            position: KnockoutObservable<string>;
            attendanceRecordName: KnockoutObservable<string>;
            currentCodeList: KnockoutObservableArray<string>;
            columns: KnockoutObservableArray<any>;
            gridItems: KnockoutObservableArray<model.GridItem>;
            selectionTypeList: KnockoutObservableArray<model.SelectionType>;
            selectionTypeValue: KnockoutObservable<number>;
            selectedGridItems: KnockoutObservableArray<model.SelectedItem>;
            formulaContent: KnockoutObservable<string>;
            attendanceItem: KnockoutObservable<any>;

            constructor() {
                let self = this;

                self.layoutCode = ko.observable('');
                self.layoutName = ko.observable('');
                self.dailyMonthly = ko.observable('');
                self.columnIndex = ko.observable('');
                self.position = ko.observable('');
                self.attendanceRecordName = ko.observable('');
                self.currentCodeList = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KWR002_176'), key: 'code' },
                    { headerText: nts.uk.resource.getText('KWR002_177'), key: 'name' }
                ]); 
                self.gridItems = ko.observableArray([]);
                self.selectionTypeList = ko.observableArray([
                    new model.SelectionType(16, nts.uk.resource.getMessage("Msg_1209", [])),
                    new model.SelectionType(17, nts.uk.resource.getMessage("Msg_1210", [])),
                    new model.SelectionType(18, nts.uk.resource.getMessage("Msg_1211", [])),
                ]);
                self.selectionTypeValue = ko.observable(0);
                self.selectionTypeValue.subscribe(function(codeChange) {
                    self.gridItems([]);
                    self.currentCodeList([]);
                    self.selectedGridItems([]);
                    self.start(codeChange);
                });
                self.selectedGridItems = ko.observableArray([]);
                self.selectedGridItems.subscribe(function(changeList) {
                    var tempContent: string = '';
                    changeList.forEach(function(item) {
                        tempContent += item.action + item.name;
                    });
                    self.formulaContent(tempContent);
                });
                self.formulaContent = ko.observable('');
                self.attendanceItem = ko.observable(null);

            }

            start(value: number): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.findAttndRecByScreen(value).done(function(attData: Array<model.AttendanceRecordItemDto>) {
                    if (attData.length > 0) {
                        var itemList: Array<model.GridItem> = [];
                        attData.forEach(item => {
                            itemList.push(new model.GridItem(item.attendanceItemId, item.attendanceItemName));
                        });
                        self.gridItems(itemList);
                    }
                    dfd.resolve();
                });
                var attendanceItem = nts.uk.ui.windows.getShared('attendanceItem');
                self.attendanceItem(attendanceItem);
                self.layoutCode(attendanceItem.layoutCode);
                self.layoutName(attendanceItem.layoutName);
                // process display
                if (attendanceItem.exportAtr == 1) {
                    self.dailyMonthly('日次');
                }
                else {
                    self.dailyMonthly('月次');
                }
                self.columnIndex(attendanceItem.columnIndex);
                // process display
                if (attendanceItem.position == 1) {
                    self.position('上');
                }
                else {
                    self.position('下');
                }
                self.attendanceRecordName(attendanceItem.attendanceItemName);
                return dfd.promise();
            }

            writeItemWithAdd() {
                var self = this;
                var selectedItems: Array<model.GridItem> = [];
                _.forEach(self.currentCodeList(), function(item) {
                    var selectedItem = _.find(self.gridItems(), function(e) { return e.code == item; });
                    selectedItems.push(selectedItem);
                });
                if (selectedItems.length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1140' });
                    return;
                }
                else {
                    _.forEach(selectedItems, function(item: model.GridItem) {
                        self.selectedGridItems.push(new model.SelectedItem(model.Action.ADDITION, item.code, item.name));
                    });
                }
            }

            writeItemWithSubtract() {
                var self = this;
                var selectedItems: Array<model.GridItem> = [];
                _.forEach(self.currentCodeList(), function(item) {
                    var selectedItem = _.find(self.gridItems(), function(e) { return e.code == item; });
                    selectedItems.push(selectedItem);
                });
                if (selectedItems.length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1140' });
                    return;
                }
                else {
                    _.forEach(selectedItems, function(item: model.GridItem) {
                        self.selectedGridItems.push(new model.SelectedItem(model.Action.SUBTRACTION, item.code, item.name));
                    });
                }
            }
            
            deleteItem(data) {
                var self = this;
                var seletedItems = self.selectedGridItems();
                var item: model.SelectedItem;
                for (var i = 0; i<seletedItems.length; i++) {
                    item = seletedItems[i];
                    if (item == data) {
                        var index = seletedItems.indexOf(item);
                        if (index > -1) {
                            seletedItems.splice(index, 1);
                            break;
                        }
                    }
                }
                self.selectedGridItems(seletedItems);
            }

            decide() {
                var self = this;
                var outputItems: Array<model.SelectedItem>;
                outputItems = self.selectedGridItems();

                if (outputItems.length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1141' });
                    return;
                }
                else {
                    var outputTimeIDs: Array<{ id: string, action: string }> = [];
                    outputItems.forEach(function(item) {
                        outputTimeIDs.push({id: item.code, action: item.action});
                    });
                    nts.uk.ui.windows.setShared('attendanceItemSetting', {
                        attendanceItemName: self.attendanceRecordName,
                        layoutCode: self.attendanceItem().layoutCode,
                        layoutName: self.attendanceItem().layoutName,
                        columnIndex: self.attendanceItem().columnIndex,
                        position: self.attendanceItem().position,
                        exportAtr: self.attendanceItem().exportAtr,
                        attendanceId: outputTimeIDs,
                        attribute: 16
                    }, true);
                }
                nts.uk.ui.windows.close();
            }

            cancel() {
                nts.uk.ui.windows.close();
            }
        }
    }
}