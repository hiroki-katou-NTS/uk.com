// TreeGrid Node
module qpp014.d.viewmodel {
    export class ScreenModel {
        //switch
        roundingRules_D_SEL_001: KnockoutObservableArray<any>;
        selectedRuleCode_D_SEL_001: any;
        //gridview
        items_D_LST_001: any;
        columns_D_LST_001: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_D_LST_001: KnockoutObservable<any>;
        texteditor: any;
        constructor() {
            let self = this;
            //switch
            //SEL_001
            self.roundingRules_D_SEL_001 = ko.observableArray([
                { code: '1', name: '�l�̌ܓ�' },
                { code: '2', name: '�؂�グ' }
            ]);
            self.selectedRuleCode_D_SEL_001 = ko.observable(1);
            //LST_001
            self.items_D_LST_001 = [];
            for (let i = 1; i < 100; i++) {
                self.items_D_LST_001.push({ 'code': '00' + i, 'name': '基本給', 'description': 'description' + i });
            }
            
            $("#D_LST_001").igGrid({
                dataSource: self.items_D_LST_001,
                primaryKey: 'code',
                width: '785px',
                height: "380px",
                autoCommit: false,
                features: [
                    {
                        name: "Selection"
                    },
                    {
                        name: "RowSelectors",
                        enableRowNumbering: true
                    },
                    {
                        name: 'MultiColumnHeaders'
                    }
                ],
                autoGenerateColumns: false,
                columns: [
                    { headerText: 'コード', key: 'code', dataType: 'string', width: '25%' },
                    { headerText: '名称', key: 'code', dataType: 'string', width: '25%' },
                    {
                        headerText: '振込元設定', width: '50%',
                        group: [
                            { headerText: "支払1", key: "code", dataType: "string", width: "19%" },
                            { headerText: "支払2", key: "name", dataType: "string", width: "19%" },
                            { headerText: "支払3", key: "description", dataType: "string", width: "19%" },
                            { headerText: "支払4", key: "description", dataType: "string", width: "19%" },
                            { headerText: "支払5", key: "description", dataType: "string", width: "24%" }
                        ]
                    }
                ]
            });
            //Delegate
            $(document).delegate("#D_LST_001", "iggridcellclick", function (evt, ui) {
                //return cell html element in the DOM
                ui.cellElement;
                //return row index
                console.log(ui.rowIndex);
                //return row key
                console.log(ui.rowKey);
                //return col index of the DOM element
                ui.colIndex;
                //return col key
                ui.colKey;
                //return reference to igGrid
                ui.owner;
            });    
            self.currentCode_D_LST_001 = ko.observable();
            self.texteditor = {
                value: ko.observable('')
            };
        }
    }

};
