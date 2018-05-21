module nts.uk.com.view.kwr002.e {

    export module viewmodel {

        export class ScreenModel {

            processingCode: KnockoutObservable<string>;
            processingName: KnockoutObservable<string>;
            dailyMonthly: KnockoutObservable<string>;
            columnIndex: KnockoutObservable<string>;
            topBottom: KnockoutObservable<string>;
            attendanceRecordName: KnockoutObservable<string>;
            currentCodeList: KnockoutObservableArray<string>;
            columns: KnockoutObservableArray<any>;
            gridItems: KnockoutObservableArray<model.GridItem>;
            selectionTypeList: KnockoutObservableArray<model.SelectionType>;
            selectionTypeValue: KnockoutObservable<number>;
            selectedGridItems: KnockoutObservableArray<model.SelectedItem>;

            constructor() {
                let self = this;

                self.processingCode = ko.observable('a');
                self.processingName = ko.observable('a');
                self.dailyMonthly = ko.observable('a');
                self.columnIndex = ko.observable('a');
                self.topBottom = ko.observable('a');
                self.attendanceRecordName = ko.observable('a');
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
                self.selectedGridItems = ko.observableArray([]);

            }

            start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.findAttndRecByScreen(16).done(function(attData: Array<model.AttendanceRecordItemDto>) {
                    if (attData.length > 0) {
                        var itemList: Array<model.GridItem> = [];
                        attData.forEach(item => {
                            itemList.push(new model.GridItem(false, item.attendanceItemId, item.attendanceItemName));
                        });
                        self.gridItems(itemList);
                    }
                    dfd.resolve();
                });
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

                }
                else {
                    _.forEach(selectedItems, function(item: model.GridItem) {
                        self.selectedGridItems.push(new model.SelectedItem(model.Action.SUBTRACTION, item.code, item.name));
                    });
                }
            }
        }
    }
}