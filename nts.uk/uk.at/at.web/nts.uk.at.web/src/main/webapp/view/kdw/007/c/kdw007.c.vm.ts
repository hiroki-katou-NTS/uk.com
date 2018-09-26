module nts.uk.at.view.kdw007.c.viewmodel {

    export class ScreenModel {
        lstAllItems: KnockoutObservableArray<any> = ko.observableArray([]);
        lstAddSubItems: KnockoutObservableArray<any> = ko.observableArray([]);
        allListColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KDW007_30"), prop: 'code', width: 50, formatter: _.escape },
            { headerText: nts.uk.resource.getText("KDW007_31"), prop: 'name', width: 180 , formatter: _.escape },
        ]);
        addSubListColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KDW007_39"), prop: 'operator', width: 50 , formatter: _.escape },
            { headerText: nts.uk.resource.getText("KDW007_30"), prop: 'code', width: 50 , formatter: _.escape },
            { headerText: nts.uk.resource.getText("KDW007_31"), prop: 'name', width: 180 , formatter: _.escape },
        ]);
        selectedAllList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedAddSubList: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor(param) {
            let self = this;
            nts.uk.ui.block.grayout();
            self.initData(param).done(()=>{
                self.removeDupplicateItems();
                nts.uk.ui.block.clear();
            });
        }
        
        initData(param){
            let self = this;
            let dfdAll = $.Deferred();
            let dfdLstAll = $.Deferred();
            let dfdLstAdd = $.Deferred();
            let dfdLstSub = $.Deferred();
            if (param.lstAllItems.length > 0) {
                if (param.attr == ATTR.MONTHLY) {
                    service.getMonthlyAttendanceItemByCodes(param.lstAllItems).done((lstItems) => {
                        self.handleListResult(lstItems, 0);
                        dfdLstAll.resolve();
                    });
                } else {
                    service.getDailyAttendanceItemByCodes(param.lstAllItems).done((lstItems) => {
                        self.handleListResult(lstItems, 0);
                        dfdLstAll.resolve();
                    });
                }
            } else {
                dfdLstAll.resolve();
            }
            if (param.lstAddItems.length > 0) {
                if (param.attr == ATTR.MONTHLY) {
                    service.getMonthlyAttendanceItemByCodes(param.lstAddItems).done((lstItems) => {
                        self.handleListResult(lstItems, 1);
                        dfdLstAdd.resolve();
                    });
                } else {
                    service.getDailyAttendanceItemByCodes(param.lstAddItems).done((lstItems) => {
                        self.handleListResult(lstItems, 1);
                        dfdLstAdd.resolve();
                    });
                }
            } else {
                dfdLstAdd.resolve();
            }
            if (param.lstSubItems.length > 0) {
                if (param.attr == ATTR.MONTHLY) {
                    service.getMonthlyAttendanceItemByCodes(param.lstSubItems).done((lstItems) => {
                        self.handleListResult(lstItems, 2);
                        dfdLstSub.resolve();
                    });
                } else {
                    service.getDailyAttendanceItemByCodes(param.lstSubItems).done((lstItems) => {
                        self.handleListResult(lstItems, 2);
                        dfdLstSub.resolve();
                    });
                }
            } else {
                dfdLstSub.resolve();
            }
            $.when(dfdLstAll, dfdLstAdd, dfdLstSub).done(()=>{
                dfdAll.resolve();
            });
            return dfdAll;
        }
        
        handleListResult(lstItems: Array<any>, listType: number) {
            let self = this,
            listItems = [];
            _.forEach(lstItems, (item) => {
                listItems.push({ id: item.attendanceItemId, code: item.attendanceItemDisplayNumber, name: item.attendanceItemName, operator: listType == 1 ? '+' : '-', displayOrder: item.attendanceItemDisplayNumber });
            });
            if (listType == 0) { // list All items
                self.lstAllItems(listItems);
            } else { // list Add or Sub items
                ko.utils.arrayPushAll(self.lstAddSubItems, listItems);
            }
            self.sortGridList();
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
                    return baseItem.code === parseInt(item);
                });
                if (targetItem) {
                    self.lstAddSubItems.push(
                        { id: targetItem.id, code: targetItem.code, name: targetItem.name, operator: '+' }
                    );
                    self.lstAllItems.remove((itemBase) => { return itemBase.code === parseInt(item) });
                }
            });
            self.selectedAllList([]);
            self.sortGridList();
        }

        setSubItems() {
            let self = this;
            _.forEach(self.selectedAllList(), (item) => {
                let targetItem = _.find(self.lstAllItems(), (baseItem) => {
                    return baseItem.code === parseInt(item);
                });
                if (targetItem) {
                    self.lstAddSubItems.push(
                        { id: targetItem.id, code: targetItem.code, name: targetItem.name, operator: '-' }
                    );
                    self.lstAllItems.remove((itemBase) => { return itemBase.code === parseInt(item) });
                }
            });
            self.selectedAllList([]);
            self.sortGridList();
        }

        setBaseItems() {
            let self = this;
            _.forEach(self.selectedAddSubList(), (item) => {
                let targetItem = _.find(self.lstAddSubItems(), (baseItem) => {
                    return baseItem.code === parseInt(item);
                });
                if (targetItem) {
                    self.lstAllItems.push(
                        { code: targetItem.code, name: targetItem.name }
                    );
                    self.lstAddSubItems.remove((itemBase) => { return itemBase.code === parseInt(item) });
                }
            });
            self.selectedAddSubList([]);
            self.sortGridList();
        }

        sortGridList() {
            let self = this;
            let lstAllItemsSorted = _.orderBy(self.lstAllItems(), ['displayOrder'], ['asc']);
            self.lstAllItems(lstAllItemsSorted);
            let lstAddSubItemsSorted = _.orderBy(self.lstAddSubItems(), ['operator', 'displayOrder'], ['asc', 'asc']);
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
                }).map((item) => { return item.id; }),
                lstSubItems: _.filter(self.lstAddSubItems(), (item) => {
                    return item.operator === '-';
                }).map((item) => { return item.id; })
            };
            nts.uk.ui.windows.setShared("KDW007CResults", resultData);
            nts.uk.ui.windows.close();
        }
    }
    
    enum ATTR {
        DAILY = 0,
        MONTHLY = 1
    }

}
