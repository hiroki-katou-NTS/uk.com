module nts.uk.at.view.ksu001.s.sa {
    import setShare = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            // SetShare
            lstEmp: KnockoutObservable<any> = ko.observable(nts.uk.ui.windows.getShared('KSU001S'));
            itemsSwap: KnockoutObservableArray<ItemModel>;
            //     columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeListSwap: KnockoutObservableArray<any>;
            test: KnockoutObservableArray<any>;
            
            //Swap List
            listEmployeeSwap: KnockoutObservableArray<any> = ko.observableArray([]);
            columnsLeftSwap: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn> = ko.observableArray([
                { headerText: nts.uk.resource.getText('KSU001_4043'), key: 'code', width: 0 },
                { headerText: nts.uk.resource.getText('KSU001_4043'), key: 'name', width: 140 }
            ]);
            columnsRightSwap: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn> = ko.observableArray([
                { headerText: nts.uk.resource.getText('KSU001_4044'), key: 'code', width: 0 },
                { headerText: nts.uk.resource.getText('KSU001_4044'), key: 'name', width: 140 }
            ]);
            selectedEmployeeSwap: KnockoutObservableArray<any> = ko.observableArray([]);
            constructor() {
                var self = this;
                this.itemsSwap = ko.observableArray([]);
                this.currentCodeListSwap = ko.observableArray([]);
                this.test = ko.observableArray([]);
                //Swap List
                self.selectedEmployeeSwap = ko.observableArray([]);
                self.listEmployeeSwap = ko.observableArray([
                    new ItemModel(0, "スケジュールチーム", nts.uk.resource.getText('KSU001_4048')),
                    new ItemModel(1, "ランク", nts.uk.resource.getText('KSU001_4049')),
                    new ItemModel(2, "免許区分", nts.uk.resource.getText('KSU001_4050')),
                    new ItemModel(3, "職位", nts.uk.resource.getText('Com_Jobtitle')),
                    new ItemModel(4, "分類", nts.uk.resource.getText('Com_Class'))
                ]);
            }
            
            remove() {
                this.itemsSwap.shift();
            }
            
            openDialog() {
                let self = this;
                let request: any = {};
                request.lstEmp = self.lstEmp();
                request.date = nts.uk.ui.windows.getShared('KSU001S').date;
                let paramToB = [];
                _.forEach(self.selectedEmployeeSwap(), function(value) {
                    let sortType = 0;
                    switch (value.name) {
                        case "ランク":
                            sortType = 1;
                            break;
                        case "免許区分":
                            sortType = 2;
                            break;
                        case "職位":
                            sortType = 3;
                            break;
                        case "分類":
                            sortType = 4;
                            break;
                    }
                    paramToB.push({
                        sortOrder: 0,
                        sortType: sortType,
                        priority: self.selectedEmployeeSwap().findIndex(x => x.code === value.code) + 1
                    });
                });

                request.selectedEmployeeSwap = paramToB;
                nts.uk.ui.windows.setShared('KSU001SB', request);
                nts.uk.ui.windows.sub.modal("/view/ksu/001/s/b/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {

                });
            }

            cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
            }
            
            save(): any {
                let self = this;
                let lstOrderListDto = _.map(self.selectedEmployeeSwap(), function(item) {
                    return {
                        sortOrder: 0,
                        sortType: parseInt(item.name)
                    };
                });
                let param = {
                    lstOrderListDto: lstOrderListDto
                }
                console.log(param)
                service.save(param).done(function(data: any) {
                    console.log("done: " + data);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                service.getData().done(function(data: any) {
                    _.forEach(data.lstOrderList, function(item) {
                        // Remove from left source
                        let removeItem = self.listEmployeeSwap.remove(function(leftItem) {
                            return leftItem.name == item.sortName;
                        })[0];

                        // Add to right source
                        self.selectedEmployeeSwap.push(removeItem);
                    });
                    dfd.resolve();
                });
                dfd = $.Deferred();
                return dfd.promise();
            }
        }
    }
}
class ItemModel {
    code: number;
    name: string;
    displayName: string;
    constructor(code: number, name: string, displayName: string) {
        this.code = code;
        this.name = name;
        this.displayName = displayName;
    }
}