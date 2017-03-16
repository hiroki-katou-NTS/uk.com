// TreeGrid Node
module qpp014.a.viewmodel {
    export class ScreenModel {
        //
        //viewmodelb = new qpp014.b.viewmodel.ScreenModel();

        //viewmodel A
        a_SEL_001_items: KnockoutObservableArray<ItemModel_A_SEL_001>;
        a_SEL_001_itemSelected: KnockoutObservable<any>;
        
        //viewmodel B
        b_stepList: Array<nts.uk.ui.NtsWizardStep>;
        b_stepSelected: KnockoutObservable<nts.uk.ui.NtsWizardStep>;

        //viewmodel D
        d_SEL_001_selectedCode: KnockoutObservable<any>;
        d_INP_001: any;
        d_LST_001_items: any;
        d_LST_001_itemSelected: KnockoutObservable<any>;
        
        //viewmodel G
        g_INP_001: KnockoutObservable<Date>;
        g_SEL_001_items: KnockoutObservableArray<ItemModel_G_SEL_001>;
        g_SEL_001_itemSelected: KnockoutObservable<any>;
        g_SEL_002_items: KnockoutObservableArray<ItemModel_G_SEL_002>;
        g_SEL_002_itemSelected: KnockoutObservable<any>;
        g_INP_002: any;
        g_SEL_003_itemSelected: KnockoutObservable<any>;
        
        //viewmodel H
        h_INP_001: KnockoutObservable<Date>;
        h_LST_001_items: KnockoutObservableArray<ItemModel_H_LST_001>;
        h_LST_001_itemsSelected: KnockoutObservable<any>;
        
        constructor() {
            $('.func-btn').css('display', 'none');
            $('#screenB').css('display', 'none');
            let self = this;


            //viewmodel A
            self.a_SEL_001_items = ko.observableArray([
                new ItemModel_A_SEL_001('基本給1', '基本給'),
                new ItemModel_A_SEL_001('基本給2', '役職手当'),
                new ItemModel_A_SEL_001('0003', '基本給')
            ]);
            self.a_SEL_001_itemSelected = ko.observable('0003');


            //viewmodel B
            self.b_stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' }
            ];
            self.b_stepSelected = ko.observable({ id: 'step-2', content: '.step-2' });


            //viewmodel D
            self.d_SEL_001_selectedCode = ko.observable(1);
            self.d_INP_001 = {
                value: ko.observable('')
            };
            self.d_LST_001_items = [];
            for (let i = 1; i < 100; i++) {
                self.d_LST_001_items.push({ 'code': '00' + i, 'name': '基本給', 'description': 'description' + i });
            }
                //LST 001
            $("#D_LST_001").igGrid({
                dataSource: self.d_LST_001_items,
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
            self.d_LST_001_itemSelected = ko.observable();
            
            //viewmodel G
            self.g_INP_001 = ko.observable(new Date('2016/12/01'));
            self.g_SEL_001_items = ko.observableArray([
                new ItemModel_G_SEL_001('��{��1', '��{��'),
                new ItemModel_G_SEL_001('��{��2', '��E�蓖'),
                new ItemModel_G_SEL_001('0003', '��{��')
            ]);
            self.g_SEL_001_itemSelected = ko.observable('0002');
            self.g_SEL_002_items = ko.observableArray([
                new ItemModel_G_SEL_002('��{��1', '��{��'),
                new ItemModel_G_SEL_002('��{��2', '��E�蓖'),
                new ItemModel_G_SEL_002('0003', '��{��')
            ]);
            self.g_SEL_002_itemSelected = ko.observable('0002');
            self.g_INP_002 = {
                value: ko.observable(12)
            };
            self.g_SEL_003_itemSelected = ko.observable('0002');
            
            //viewmodel H
            self.h_INP_001 = ko.observable(new Date('2016/12/01'));
            self.h_LST_001_items = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.h_LST_001_items.push(new ItemModel_H_LST_001('00' + i, '基本給', "description " + i));
            }
            self.h_LST_001_itemsSelected = ko.observable(); 
        }
        nextScreen() {
            $("#screenA").css("display", "none");
            $("#screenB").css("display", "");
            $("#screenB").ready(function() {
                $(".func-btn").css("display", "");
            });
        }
        backScreen() {
            $("#screenB").css("display", "none");
            $("#screenA").css("display", "");
            $(".func-btn").css("display", "none");
        }
    }
    export class ItemModel_A_SEL_001 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class ItemModel_G_SEL_001 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class ItemModel_G_SEL_002 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class ItemModel_H_LST_001 {
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
