// TreeGrid Node
module qpp014.d.viewmodel {
    export class ScreenModel {
        d_SEL_001_selectedCode: KnockoutObservable<any>;
        d_SEL_002_selectedCode: KnockoutObservable<any>;
        d_INP_001: any;
        d_LST_001_items: KnockoutObservableArray<any>;
        d_LST_001_itemSelected: KnockoutObservable<any>;
        
        constructor() {
            let self = this;

            self.d_SEL_001_selectedCode = ko.observable(1);
            self.d_SEL_002_selectedCode = ko.observable(1);
            self.d_INP_001 = {
                value: ko.observable('')
            };
            self.d_LST_001_items = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.d_LST_001_items.push(({ code: '00' + i, name: ('基本給'), description: ('description' + i) }));
            }
            //LST 001
            /*
            $("#D_LST_001").igGrid({
                dataSource: self.d_LST_001_items,
                primaryKey: 'code',
                width: '740px',
                height: '290px',
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
                    { headerText: 'コード', key: 'code', dataType: 'string', width: '17%' },
                    { headerText: '名称', key: 'code', dataType: 'string', width: '17%' },
                    {
                        headerText: '振込元設定', width: '70%',
                        group: [
                            { headerText: "支払1", key: "code", dataType: "string", width: "13%" },
                            { headerText: "支払2", key: "name", dataType: "string", width: "13%" },
                            { headerText: "支払3", key: "description", dataType: "string", width: "13%" },
                            { headerText: "支払4", key: "description", dataType: "string", width: "13%" },
                            { headerText: "支払5", key: "description", dataType: "string", width: "14%" }
                        ]
                    }
                ]
            });
            */
            self.d_LST_001_itemSelected = ko.observable(0);
        }
    }

};
