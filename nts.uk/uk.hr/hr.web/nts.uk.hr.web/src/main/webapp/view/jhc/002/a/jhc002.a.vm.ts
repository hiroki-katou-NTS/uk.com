module nts.uk.hr.view.jhc002.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {

        dateValue: KnockoutObservable<any>;
        items: KnockoutObservableArray<GridItem>;
        
         masterId: KnockoutObservable<string>;
        histList: KnockoutObservableArray<any>;
        selectedHistId: KnockoutObservable<any>;
        constructor() {
            var self = this;
            var comboItems = [new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給2')];
            var comboColumns = [{ prop: 'code', length: 1 },
                { prop: 'name', length: 4 }];
            let keys = [];

            self.items = ko.observableArray([]);
            
            // history component
            
            self.height = ko.observable("200px");
            self.labelDistance = ko.observable("30px");
            self.screenMode = ko.observable(1);
            self.masterId = ko.observable("a2316878-a3a5-4362-917e-ad71d956e6c2");
            self.histList = ko.observableArray([]);
            self.selectedHistId = ko.observable();
            self.pathGet = ko.observable(`/bs/employee/workplace/hist/${self.masterId()}`);
            self.pathAdd = ko.observable(`bs/employee/workplace/history/save`);
            self.pathUpdate = ko.observable(`bs/employee/workplace/history/save`);
            self.pathDelete = ko.observable(`bs/employee/workplace/history/remove`);
            self.getQueryResult = (res) => {
                return _.map(res.workplaceHistory, h => {
                    return { histId: h.historyId, startDate: h.startDate, endDate: h.endDate, displayText: `${h.startDate} ～ ${h.endDate}` };
                });
            };
            self.getSelectedStartDate = () => {
                let selectedHist = _.find(self.histList(), h => h.histId === self.selectedHistId());
                if (selectedHist) return selectedHist.startDate;
            };
            self.commandAdd = (masterId, histId, startDate, endDate) => {
                return {
                    isAddMode: true,
                    workplaceId: masterId,
                    workplaceHistory: {
                        historyId: '',
                        period: {
                            startDate: startDate,
                            endDate: new Date(endDate)
                        }
                    }
                }
            };
            self.commandUpdate = (masterId, histId, startDate, endDate) => {
                return {
                    isAddMode: false,
                    workplaceId: masterId,
                    workplaceHistory: {
                        historyId: histId,
                        period: {
                            startDate: startDate,
                            endDate: new Date(endDate)
                        }
                    }
                }
            };
            self.commandDelete = (masterId, histId) => {
                return {
                    workplaceId: masterId,
                    historyId: histId
                };
            };
            self.delVisible = ko.observable(true);
            self.delChecked = ko.observable();
            self.afterRender = () => {
                alert("Load!"); 
            };
            self.afterAdd = () => {
                alert("Added");
            };
            self.afterUpdate = () => {
                alert("Updated");
            };
            self.afterDelete = () => {
                alert("Deleted");
            };
            

        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            var list = [];
            for (var i = 0; i < 5; i++) {
                list.push(new GridItem(i));
            }
            self.items(list);
            var comboItems = [new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給2')];
            var comboColumns = [{ prop: 'code', length: 1 },
                { prop: 'name', length: 4 }];

            $("#grid2").ntsGrid({
                width: '600px',
                height: '400px',
                dataSource:  self.items(),
                primaryKey: 'id',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                preventEditInError: false,
                hidePrimaryKey: true,
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'number', width: '60px', ntsControl: 'Label', hidden: false },
                    { headerText: 'Image', key: 'flexImage', dataType: 'string', width: '200px',  ntsControl: 'Label', template: '<span> aaaaaaa</span> <input type=\'button\' value=\'Test\' data-id=\'${id}\' onclick=\'buttonClickHandler()\'/>' },
                    { headerText: 'Combobox1', key: 'combo1', dataType: 'string', width: '130px', ntsControl: 'Combobox' },
                    { headerText: 'Combobox2', key: 'combo2', dataType: 'string', width: '130px', ntsControl: 'Combobox' },
                    { headerText: 'Combobox3', key: 'combo3', dataType: 'string', width: '130px', ntsControl: 'Combobox' },
                    { headerText: 'Combobox4', key: 'combo4', dataType: 'string', width: '130px', ntsControl: 'Combobox' },
                    { headerText: 'Combobox5', key: 'combo5', dataType: 'string', width: '130px', ntsControl: 'Combobox' },
                    { headerText: 'Combobox6', key: 'combo6', dataType: 'string', width: '130px', ntsControl: 'Combobox' },
                    { headerText: 'Combobox7', key: 'combo7', dataType: 'string', width: '130px', ntsControl: 'Combobox' },
                    { headerText: 'Combobox8', key: 'combo8', dataType: 'string', width: '130px', ntsControl: 'Combobox' },
                    { headerText: 'Combobox9', key: 'combo9', dataType: 'string', width: '130px', ntsControl: 'Combobox' },
                    { headerText: 'Combobox10', key: 'combo10', dataType: 'string', width: '130px', ntsControl: 'Combobox' }
                ],
                features: [
                    { name: 'MultiColumnHeaders' },
                    {
                        name: 'ColumnFixing', fixingDirection: 'left',
                        showFixButtons: false,
                        columnSettings: [
                            { columnKey: 'id', isFixed: true },
                            { columnKey: 'flexImage', isFixed: true }
                        ]
                    }
                ],
                ntsFeatures: [{ name: 'CopyPaste' },
                    {
                        name: "Sheet",
                        initialDisplay: "sheet1",
                        sheets: [
                            { name: "sheet1", text: "Sheet 1", columns: ["combo1", "combo2", "combo3", "combo4", "combo5", "combo6", "combo7", "combo8", "combo9", "combo10", "flexImage"] }
                        ]
                    },
                    { name: "CellEdit" },
                    { name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: []
                    }
                ],
                ntsControls: [
                    { name: 'Combobox', width: '70px', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, editable: false, displayMode: 'codeName', controlType: 'ComboBox', enable: true, spaceSize: 'small' }
                ]
            });


            dfd.resolve();
            return dfd.promise();
        }
    }

    class GridItem {
        id: number;
        combo1: string;
        combo2: string;
        combo3: string;
        combo4: string;
        combo5: string;
        combo6: string;
        combo7: string;
        combo8: string;
        combo9: string;
        combo10: string;
        flexImage: string;
        constructor(index: number) {
            this.id = index;
            this.combo1 = String(index % 3 + 1);
            this.combo2 = String(index % 3 + 1);
            this.combo3 = String(index % 3 + 1);
            this.combo4 = String(index % 3 + 1);
            this.combo5 = String(index % 3 + 1);
            this.combo6 = String(index % 3 + 1);
            this.combo7 = String(index % 3 + 1);
            this.combo8 = String(index % 3 + 1);
            this.combo9 = String(index % 3 + 1);
            this.combo10 = String(index % 3 + 1);
            this.flexImage = "aaaa";
        }
    }


    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}

