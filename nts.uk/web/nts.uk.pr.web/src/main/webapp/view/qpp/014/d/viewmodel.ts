// TreeGrid Node
module qpp014.d {
    export class ScreenModel {
        //switch
        roundingRules_D_SEL_001: KnockoutObservableArray<any>;
        selectedRuleCode_D_SEL_001: any;
        //gridview
        items_D_LST_001: any;
        columns_D_LST_001: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_D_LST_001: KnockoutObservable<any>;
        constructor() {
            let self = this;
            //switch
            //SEL_001
            self.roundingRules_D_SEL_001 = ko.observableArray([
                { code: '1', name: 'éléÃå‹ì¸' },
                { code: '2', name: 'êÿÇËè„Ç∞' }
            ]);
            self.selectedRuleCode_D_SEL_001 = ko.observable(1);
            //LST_001
            self.items_D_LST_001 = [];
            for (let i = 1; i < 100; i++) {
                self.items_D_LST_001.push({ 'code': '00' + i, 'name': 'Âü∫Êú¨Áµ¶', 'description': 'description' + i });
            }
            $("#D_LST_001").igGrid({
                dataSource: self.items_D_LST_001,
                primaryKey: 'code',
                width: '800px',
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
                    { headerText: 'col1', key: 'code', dataType: 'string', width: '25%' },
                    { headerText: 'col2', key: 'code', dataType: 'string', width: '25%' },
                    {
                        headerText: 'Group1', width: '50%',
                        group: [
                            { headerText: "SubCol1", key: "code", dataType: "string", width: "20%" },
                            { headerText: "SubCol2", key: "name", dataType: "string", width: "20%" },
                            { headerText: "SubCol3", key: "description", dataType: "string", width: "20%" },
                            { headerText: "SubCol4", key: "description", dataType: "string", width: "20%" },
                            { headerText: "SubCol5", key: "description", dataType: "string", width: "20%" }
                        ]
                    }
                ]
            });
            self.currentCode_D_LST_001 = ko.observable();
        }
    }

};
