// TreeGrid Node
module qpp014.k {
    export class ScreenModel {
        //DatePicker
        //K_INP_001
        date_K_INP_001: KnockoutObservable<Date>;
        //K_INP_002
        date_K_INP_002: KnockoutObservable<Date>;
        //GridView
        //K_LST_001
        items_K_LST_001: any;
        data_K_LST_001: any;
        currentCode_K_LST_001: KnockoutObservable<any>;
        constructor() {
            let self = this;
            //DatePicker
            //K_INP_001
            self.date_K_INP_001 = ko.observable(new Date('2016/12/01'));
            //K_INP_002
            self.date_K_INP_002 = ko.observable(new Date('2016/12/01'));
            //gridview
            self.items_K_LST_001 = [];
            for (let i = 1; i < 100; i++) {
                self.items_K_LST_001.push({ 'code': '00' + i, 'name': '基本給', 'description': 'description' + i });
            }
            self.currentCode_K_LST_001 = ko.observable();
            $("#K_LST_001").igGrid({
                dataSource: self.items_K_LST_001,
                primaryKey: 'code',
                width: '100%',
                autoCommit: false,
                features: [
                    {
                        name: 'Paging',
                        type: "local",
                        pageSize: 12
                    },
                    {
                        name: 'Updating',
                        enableAddRow: false,
                        enableDeleteRow: false,
                        editMode: 'none'
                    },
                    {
                        name: "Selection"
                    }

                ],
                autoGenerateColumns: false,
                columns: [
                    { headerText: '', key: 'Delete', dataType: 'string', width: '7%', formatter: makeK_BTN_002 },
                    { headerText: 'function', key: 'Folder', dataType: 'string', width: '7%', formatter: makeK_BTN_003 },
                    { headerText: 'code', key: 'code', dataType: 'string', width: '25%' },
                    { headerText: 'name', key: 'name', dataType: 'string', width: '25%' },
                    { headerText: 'description', key: 'description', dataType: 'string', width: '25%' }
                ]
            });
            function makeK_BTN_002(value, record) {
                if (record.code === '005')
                    return '';
                return '<button class="small K_BTN_002" onclick="deleteRow(\'' + record.code + '\')"   value="Delete row" >Delete</button>';
            }
            function makeK_BTN_003(value, record) {
                if (record.code === '004')
                    return '';
                return '<button  onclick="deleteRow(\'' + record.code + '\')" class="K_BTN_003" value="delete" ></button>';
            }
        }
    }

    export class ItemModel_K_LST_001 {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
};
