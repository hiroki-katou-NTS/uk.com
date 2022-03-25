module nts.uk.at.view.kdw007.c.viewmodel {

    export class ScreenModel {
        lstAllItems: KnockoutObservableArray<any> = ko.observableArray([]);
        lstAddSubItems: KnockoutObservableArray<any> = ko.observableArray([]);
        allListColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KDW007_30"), prop: 'code', width: 50, formatter: _.escape },
            { headerText: nts.uk.resource.getText("KDW007_31"), prop: 'name', width: 190 , formatter: function (name, record) {
                    return "<label class='limited-label'> " + name + " </label>";       
                } },
        ]);
        addSubListColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KDW007_39"), prop: 'operator', width: 50 , formatter: _.escape },
            { headerText: nts.uk.resource.getText("KDW007_30"), prop: 'code', width: 50 , formatter: _.escape },
            { headerText: nts.uk.resource.getText("KDW007_31"), prop: 'name', width: 190 , 
                formatter: function (name, record) {
                    return "<label class='limited-label'> " + name + " </label>";       
                }    
        },
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
                        self.handleListResult(param.lstAllItems,lstItems, 0);
                        dfdLstAll.resolve();
                    });
                } else {
                    service.getDailyAttendanceItemByCodes(param.lstAllItems).done((lstItems) => {
                        self.handleListResult(param.lstAllItems,lstItems, 0);
                        dfdLstAll.resolve();
                    });
                }
            } else {
                dfdLstAll.resolve();
            }
            if (param.lstAddItems.length > 0) {
                if (param.attr == ATTR.MONTHLY) {
                    service.getMonthlyAttendanceItemByCodes(param.lstAddItems).done((lstItems) => {
                        self.handleListResult(param.lstAddItems,lstItems, 1);
                        dfdLstAdd.resolve();
                    });
                } else {
                    service.getDailyAttendanceItemByCodes(param.lstAddItems).done((lstItems) => {
                        self.handleListResult(param.lstAddItems,lstItems, 1);
                        dfdLstAdd.resolve();
                    });
                }
            } else {
                dfdLstAdd.resolve();
            }
            if (param.lstSubItems.length > 0) {
                if (param.attr == ATTR.MONTHLY) {
                    service.getMonthlyAttendanceItemByCodes(param.lstSubItems).done((lstItems) => {
                        self.handleListResult(param.lstSubItems,lstItems, 2);
                        dfdLstSub.resolve();
                    });
                } else {
                    service.getDailyAttendanceItemByCodes(param.lstSubItems).done((lstItems) => {
                        self.handleListResult(param.lstSubItems,lstItems, 2);
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
        
        handleListResult(lstItemId: Array<any>,lstItems: Array<any>, listType: number) {
            let self = this,
            listItems = [];
			for(let i = 0;i<lstItemId.length;i++){
				let checkExist = false;
				let operator = "";
                if (listType == 1) operator = "+";
                if (listType == 2) operator = "-"
	            _.forEach(lstItems, (item) => {
					if(lstItemId[i] == item.attendanceItemId ){
		                listItems.push(new ItemValue(item.attendanceItemId, operator, item.attendanceItemDisplayNumber, item.attendanceItemName));
						checkExist = true;
						return;
					}
	            });
				if(checkExist == false){
					listItems.push(new ItemValue(lstItemId[i], operator, null, nts.uk.resource.getText("KDW007_113")));
				}
			}
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
                    self.lstAddSubItems.push(new ItemValue(targetItem.id, "+", targetItem.code, targetItem.name));
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
                    self.lstAddSubItems.push(new ItemValue(targetItem.id, "-", targetItem.code, targetItem.name));
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
                    self.lstAllItems.push(new ItemValue(targetItem.id, "", targetItem.code, targetItem.name));
                    self.lstAddSubItems.remove((itemBase) => { return itemBase.code === parseInt(item) });
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
    
    class ItemValue {
        id: number;
        operator: string;
        code: number;// aka display number
        name: string; 
        
        constructor(id: number, operator: string, displayNumber: number, name: string) {
            this.id = id;
            this.operator = operator;
            this.code = displayNumber;
            this.name = name;
        }
    }

}
