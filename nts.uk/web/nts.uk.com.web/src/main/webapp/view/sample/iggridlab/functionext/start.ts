module nts.uk.ui.gridlist {
    
    __viewContext.ready(function () {
    
        class ScreenModel {
            
            items = (function () {
                var list = [];
                for (var i = 0; i < 500; i++) {
                    list.push(new GridItem(i));
                }
                return list;
            })();
        }
        
        class GridItem {
            id: number;
            flag: boolean;
            ruleCode: number;
            phone: string;
            address1: string;
            address2: string;
            combo: string;
            constructor(index: number) {
                this.id = index;
                this.flag = index % 2 == 0;
                this.ruleCode = index % 3 + 1;
                this.phone = "0123456789";
                this.address1 = "Hanoi, Vietnam";
                this.address2 = "愛知県日本";
                this.combo = String(index % 3 + 1);
            }
        }
        
        var model = new ScreenModel();
        
        class ItemModel {
            code: string;
            name: string;
    
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        var comboItems = [ new ItemModel('1', '基本給'),
                            new ItemModel('2', '役職手当'),
                            new ItemModel('3', '基本給2') ];
        var comboColumns = [{ prop: 'code', length: 4 },
                            { prop: 'name', length: 8 }];
        $("#grid2").ntsGrid({ 
                            width: '1100px',
                            height: '400px',
                            dataSource: model.items,
                            primaryKey: 'id',
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            enter: 'right',
                            columns: [
                                { headerText: 'ID', key: 'id', dataType: 'number', width: '50px' },
                                { headerText: 'FLAG', key: 'flag', dataType: 'boolean', width: '190px', ntsControl: 'Checkbox' },
                                { headerText: 'RULECODE', key: 'ruleCode', dataType: 'number', width: '100px' },
                                { headerText: 'Phone', key: 'phone', dataType: 'string', width: '140px' },
                                { headerText: 'Address1', key: 'address1', dataType: 'string', width: '150px' },
                                { headerText: 'Address2', key: 'address2', dataType: 'string', width: '150px' },
                                { headerText: 'Combobox', key: 'combo', dataType: 'string', width: '230px', ntsControl: 'Combobox' },
                                { headerText: 'Delete', key: 'delete', dataType: 'string', width: '90px', unbound: true, ntsControl: 'DeleteButton' }
                            ], 
                            features: [{ name: 'Sorting', type: 'local' }],
                            ntsFeatures: [{ name: 'CopyPaste' },
                                            { name: 'CellEdit' }]
                            ntsControls: [{ name: 'Checkbox', options: { value: 1, text: 'Custom Check' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                                            { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, editable: true, controlType: 'ComboBox', enable: true },
                                            { name: 'DeleteButton', text: 'Delete', controlType: 'DeleteButton', enable: true }]
                            });
        $("#run").on("click", function() {
            var source = $("#grid2").igGrid("option", "dataSource");
            alert(source[1].flag);
        });
        $("#update-row").on("click", function() {
            $("#grid2").ntsGrid("updateRow", 0, { flag: false, ruleCode: '2', combo: '3' });
        });
        $("#enable-ctrl").on("click", function() {
            $("#grid2").ntsGrid("enableNtsControlAt", 1, "combo", "ComboBox");
        });
        $("#disable-ctrl").on("click", function() {
            $("#grid2").ntsGrid("disableNtsControlAt", 1, "combo", "ComboBox");
        });
        this.bind(model);
    });
}