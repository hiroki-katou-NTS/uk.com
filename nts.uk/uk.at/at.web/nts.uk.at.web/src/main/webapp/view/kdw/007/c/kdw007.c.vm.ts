module nts.uk.at.view.kdw007.c.viewmodel {

    export class ScreenModel {
        lstAllItems: KnockoutObservableArray<any> = ko.observableArray([]);
        lstAddSubItems: KnockoutObservableArray<any> = ko.observableArray([
        ]);
        allListColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KDW007_30"), prop: 'code', width: 50 },
            { headerText: nts.uk.resource.getText("KDW007_31"), prop: 'name', width: 180 },
        ]);
        addSubListColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KDW007_39"), prop: 'operator', width: 50 },
            { headerText: nts.uk.resource.getText("KDW007_30"), prop: 'code', width: 50 },
            { headerText: nts.uk.resource.getText("KDW007_31"), prop: 'name', width: 180 },
        ]);
        selectedAllList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedAddSubList: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor(param) {
            let self = this;
            service.getAttendanceItemByCodes(param.lstAllItems).done((lstItems) => {
                _.forEach(lstItems, (item) => {
                    self.lstAllItems.push({ code: item.attendanceItemId, name: item.attendanceItemName });
                });
                self.sortGridList();
            });
            service.getAttendanceItemByCodes(param.lstAddItems).done((lstItems) => {
                _.forEach(lstItems, (item) => {
                    self.lstAddSubItems.push({ code: item.attendanceItemId, name: item.attendanceItemName, operator: '+' });
                });
                self.sortGridList();
            });
            service.getAttendanceItemByCodes(param.lstSubItems).done((lstItems) => {
                _.forEach(lstItems, (item) => {
                    self.lstAddSubItems.push({ code: item.attendanceItemId, name: item.attendanceItemName, operator: '-' });
                });
                self.sortGridList();
            });
        }

        removeDupplicateItems() {
            let self = this;
            _.forEach(self.lstAddSubItems(), (addSubItems) => {
                self.lstAllItems.remove((itemDel) => { return itemDel.code === addSubItems.code });
            });
        }

        setAdditionItems() {
            let self = this;
            _.forEach(self.selectedAllList(), (item) => {
                let targetItem = _.find(self.lstAllItems(), (baseItem) => {
                    return baseItem.code === item;
                });
                if (targetItem) {
                    self.lstAddSubItems.push(
                        { code: targetItem.code, name: targetItem.name, operator: '+' }
                    );
                    self.lstAllItems.remove((itemBase) => { return itemBase.code === item });
                }
            });
            self.selectedAllList([]);
            self.sortGridList();
        }

        setSubItems() {
            let self = this;
            _.forEach(self.selectedAllList(), (item) => {
                let targetItem = _.find(self.lstAllItems(), (baseItem) => {
                    return baseItem.code === item;
                });
                if (targetItem) {
                    self.lstAddSubItems.push(
                        { code: targetItem.code, name: targetItem.name, operator: '-' }
                    );
                    self.lstAllItems.remove((itemBase) => { return itemBase.code === item });
                }
            });
            self.selectedAllList([]);
            self.sortGridList();
        }

        setBaseItems() {
            let self = this;
            _.forEach(self.selectedAddSubList(), (item) => {
                let targetItem = _.find(self.lstAddSubItems(), (baseItem) => {
                    return baseItem.code === item;
                });
                if (targetItem) {
                    self.lstAllItems.push(
                        { code: targetItem.code, name: targetItem.name }
                    );
                    self.lstAddSubItems.remove((itemBase) => { return itemBase.code === item });
                }
            });
            self.selectedAddSubList([]);
            self.sortGridList();
        }

        sortGridList() {
            let self = this;
            let lstAllItemsSorted = _.orderBy(self.lstAllItems(), ['code'], ['asc']);
            self.lstAllItems(lstAllItemsSorted);
            let lstAddSubItemsSorted = _.orderBy(self.lstAddSubItems(), ['operator', 'code'], ['asc', 'asc']);
            self.lstAddSubItems(lstAddSubItemsSorted);
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        returnData() {
            let self = this;
            let resultData = {
                lstAddItems: _.filter(self.lstAddSubItems(), (item) => {
                    return item.operator === '+';
                }).map((item) => { return item.code; }),
                lstSubItems: _.filter(self.lstAddSubItems(), (item) => {
                    return item.operator === '-';
                }).map((item) => { return item.code; })
            };
            nts.uk.ui.windows.setShared("KDW007CResults", resultData);
            nts.uk.ui.windows.close();
        }
    }

}