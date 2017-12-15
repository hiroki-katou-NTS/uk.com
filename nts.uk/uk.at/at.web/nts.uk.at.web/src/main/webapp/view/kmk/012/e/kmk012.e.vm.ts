module nts.uk.at.view.kmk012.e {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            startDate:number;
            
            
            items = (function() {
                var list = [];
                for (var i = 0; i < 500; i++) {
                    list.push(new GridItem(i));
                }
                return list;
            })();

            constructor() {
                let self = this;
                self.startDate = getShared("startDate");
            }

            closeWindowns(): void {
                nts.uk.ui.windows.close();
            }
            
            startPage(): JQueryPromise<any> {
                var model = new ScreenModel();

                var comboItems = [new ItemModel('1', '基本給'),
                    new ItemModel('2', '役職手当'),
                    new ItemModel('3', '基本給2')];
                var comboColumns = [{ prop: 'code', length: 4 },
                    { prop: 'name', length: 8 }];
                $("#grid2").ntsGrid({
                    width: '390px',
                    height: '380px',
                    dataSource: model.items,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', hidden: true },
                        { headerText: getText('KMK012_38'), key: 'text1', dataType: 'string', width: '150px' },
                        { headerText: getText('KMK012_39'), key: 'combo', dataType: 'string', width: '180px', ntsControl: 'Combobox' }
                    ],
                    features: [{ name: 'Sorting', type: 'local' }],
                    
                    ntsControls: [
                        { name: 'Checkbox', options: { value: 1, text: 'Custom Check' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                        {
                            name: 'SwitchButtons', options: [{ value: '1', text: 'Option 1' }, { value: '2', text: 'Option 2' }, { value: '3', text: 'Option 3' }],
                            optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true
                        },
                        { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'Button', text: 'Open', click: function() { alert("Button!!"); }, controlType: 'Button' },
                        { name: 'DeleteButton', text: 'Delete', controlType: 'DeleteButton', enable: true }]
                });
            }

        }
        //Grid list data model
        export class GridItem {
            id: number;
            combo: string;
            text1: string;
            constructor(index: number) {
                this.id = index;
                this.combo = String(index % 3 + 1);
                this.text1 = "TEXT";
            }
        }
        //Combo data model
        export class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

    }
}