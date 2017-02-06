//module cmm013.a.viewmodel {
//    import option = nts.uk.ui.option;
//    export class ScreenModel {
//        selectLayoutAtr: KnockoutObservable<string>;
//        itemList: KnockoutObservableArray<ItemModel>;
//        isEnable: KnockoutObservable<boolean>;
//        selectStmtCode: KnockoutObservable<string>;
//        selectStmtName: KnockoutObservable<string>;
//     
//        constructor() {
//            var self = this;
//            self.selectLayoutAtr = ko.observable("3");
//            self.itemList = ko.observableArray([]);
//            self.isEnable = ko.observable(true);
//            self.selectStmtCode = ko.observable(null);
//            self.selectStmtName = ko.observable(null);
//            
//        }
//
//        start(): JQueryPromise<any> {
//            var self = this;
//            var dfd = $.Deferred<any>();
//            //list data
//            self.buildItemList();
//            $('#LST_001').on('selectionChanging', function(event) {
//                console.log('Selecting value:' + (<any>event.originalEvent).detail);
//            })
//            $('#LST_001').on('selectionChanged', function(event: any) {
//                console.log('Selected value:' + (<any>event.originalEvent).detail)
//            })
//            //fill data to dialog
//
//
//            dfd.resolve();
//            // Return.
//            return dfd.promise();
//        }
//
//        buildItemList(): any {
//            var self = this;
//            self.itemList.removeAll();
//            self.itemList.push(new ItemModel('0', 'レーザープリンタ', 'Ａ４', '縦向き', '1人', '最大　30行ｘ9別まで設定可能', ''));
//            self.itemList.push(new ItemModel('1', 'レーザープリンタ', 'Ａ４', '縦向き', '最大2人', '最大　17行ｘ9別まで設定可能', ''));
//            self.itemList.push(new ItemModel('2', 'レーザープリンタ', 'Ａ４', '縦向き', '最大3人', '最大　10行ｘ9別まで設定可能', ''));
//            self.itemList.push(new ItemModel('3', 'レーザープリンタ', 'Ａ４', '横向き', '最大2人', '最大　10行ｘ9別まで設定可能', ''));
//            self.itemList.push(new ItemModel('4', 'レーザー（圧着式）', 'Ａ４', '縦向き', '1人', '最大　17行ｘ9別まで設定可能', '圧着式：　Ｚ折り'));
//            self.itemList.push(new ItemModel('5', 'レーザー（圧着式）', 'Ａ４', '横向き', '1人', '支給、控除、勤怠各52項目', '圧着式：　はがき'));
//            self.itemList.push(new ItemModel('6', 'ドットプリンタ', '連続用紙', '―', '1人', '支給、控除、勤怠各27項目', ''));
//        }
//
//    }
//
//    /**
// * Class Item model.
// */
//    export class ItemModel {
//        stt: string;
//        printType: string;
//        paperType: string;
//        direction: string;
//        numberPeople: string;
//        numberDisplayItems: string;
//        reference: string;
//
//
//        constructor(stt: string, printType: string, paperType: string, direction: string, numberPeople: string, numberDisplayItems: string, reference: string) {
//            this.stt = stt;
//            this.printType = printType;
//            this.paperType = paperType;
//            this.direction = direction;
//            this.numberPeople = numberPeople;
//            this.numberDisplayItems = numberDisplayItems;
//            this.reference = reference;
//        }
//    }
//
//
//}

module cmm013.a.viewmodel {

    export class ScreenModel {
        listbox: KnockoutObservable<ListBoxx>;
        searchboxx: KnockoutObservable<GridLists>;
        label_002: KnockoutObservable<Label_002>;
        label_003: KnockoutObservable<Label_003>;
        label_004: KnockoutObservable<Label_004>;
        label_005: KnockoutObservable<Label_005>;
        label_006: KnockoutObservable<Label_006>;
        hasCellphone: KnockoutObservable<boolean>;
        selectbox: KnockoutObservable<SelectBox>;
        selectbox_2: KnockoutObservable<SelectBox_2>;
        inp_002: KnockoutObservable<Input_002>;
        inp_003: KnockoutObservable<Input_003>;
        inp_004: KnockoutObservable<Input_004>;
        inp_005: KnockoutObservable<Input_005>;
        switchbt_1: KnockoutObservable<SwitchButton_1>;
        switchbt_2: KnockoutObservable<SwitchButton_2>;
        switchbt_3: KnockoutObservable<SwitchButton_3>;
        switchbt_4: KnockoutObservable<SwitchButton_4>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '蝓ｺ譛ｬ諠・ｱ', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) ,
                { id: 'tab-2', title: '險育ｮ怜ｼ上・險ｭ螳・, content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) } 
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.listbox = ko.observable(new ListBoxx());
            self.searchboxx = ko.observable(new GridLists());
            self.label_002 = ko.observable(new Label_002());
            self.label_003 = ko.observable(new Label_003());
            self.label_004 = ko.observable(new Label_004());
            self.label_005 = ko.observable(new Label_005());
            self.label_006 = ko.observable(new Label_006());
            self.selectbox = ko.observable(new SelectBox());
            self.selectbox_2 = ko.observable(new SelectBox_2());
            self.hasCellphone = ko.observable(false);
            self.switchbt_1 = ko.observable(new SwitchButton_1());
            self.switchbt_2 = ko.observable(new SwitchButton_2());
            self.switchbt_3 = ko.observable(new SwitchButton_3());
            self.switchbt_4 = ko.observable(new SwitchButton_4());
            self.inp_002 = ko.observable(new Input_002());
            self.inp_003 = ko.observable(new Input_003());
            self.inp_004 = ko.observable(new Input_004());
            self.inp_005 = ko.observable(new Input_005());
        }
    }

    export class ListBoxx {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;


        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel('2015/10/01 ~ 9999/12/31'),
                new ItemModel('2015/10/01 ~ 9999/12/31'),
                new ItemModel('2015/10/01 ~ 9999/12/31'),
                new ItemModel('2015/10/01 ~ 9999/12/31'),
                new ItemModel('2015/10/01 ~ 9999/12/31'),
                new ItemModel('2015/10/01 ~ 9999/12/31'),
                new ItemModel('2015/10/01 ~ 9999/12/31'),
                new ItemModel('2015/10/01 ~ 9999/12/31'),
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable(null)
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);


            $('#list-box').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#list-box').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
        }
    }

    export class ItemModel {
        code: string;
        constructor(code: string) {
            this.code = code;
        }
    }



    class GridLists {
        dataSource: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;

        constructor() {
            this.dataSource = ko.observableArray([
                new ItemModel_GridList('001', '蝓ｺ譛ｬ邨ｦ', "description"),
                new ItemModel_GridList('002', '蝓ｺ譛ｬ邨ｦ', "description"),
                new ItemModel_GridList('003', '蝓ｺ12譛ｬ', "description"),
                new ItemModel_GridList('004', '蝓ｺ12譛ｬ', "description"),
                new ItemModel_GridList('005', '蝓ｺ12譛ｬ', "description"),
                new ItemModel_GridList('006', '蝓ｺ12譛ｬ', "description"),
                new ItemModel_GridList('007', '蝓ｺ12譛ｬ', "description"),
                new ItemModel_GridList('008', '蝓ｺ12譛ｬ', "description"),
                new ItemModel_GridList('009', '蝓ｺ12譛ｬ', "description"),
                new ItemModel_GridList('010', '蝓ｺ12譛ｬ', "description")

            ]);
            this.columns = ko.observableArray([
                { headerText: '繧ｳ繝ｼ繝・, prop: 'code', width: 50 },
                { headerText: '蜷咲ｧｰ', prop: 'name', width: 80 },
                { headerText: '隱ｬ譏・, prop: 'description', width: 80 }
            ]);
           
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
        }
    }
    

    class ItemModel_GridList {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }

    class Label_002 {
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;


        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);

        }
    }

    class Label_003 {
        constraint: string = 'LayoutName';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;


        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
        }
    }

    class SelectBox {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, 'box 1'),
                new BoxModel(2, 'box 2'),
                new BoxModel(3, 'box 3')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class SwitchButton_1 {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '蝗帶昏莠泌・' },
                { code: '2', name: '蛻・ｊ荳翫￡' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }

    class SwitchButton_2 {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '蝗帶昏莠泌・' },
                { code: '2', name: '蛻・ｊ荳翫￡' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }

    class SwitchButton_3 {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '蝗帶昏莠泌・' },
                { code: '2', name: '蛻・ｊ荳翫￡' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }

    class SwitchButton_4 {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '蝗帶昏莠泌・' },
                { code: '2', name: '蛻・ｊ荳翫￡' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }

    class Input_002 {
        texteditor: any;

        constructor() {
            var self = this;
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    width: "80px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(false),
                readonly: ko.observable(false)
            };
        }
    }

    class Input_003 {
        texteditor: any;

        constructor() {
            var self = this;
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder text",
                    width: "160px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
    }

    class Label_004 {
        constraint: string = 'LayoutName';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;


        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
        }
    }

    class SelectBox_2 {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel_2(1, 'box 1'),
                new BoxModel_2(2, 'box 2'),
                new BoxModel_2(3, 'box 3'),
                new BoxModel_2(4, 'box 4')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
        }
    }

    class BoxModel_2 {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class Input_004 {
        texteditor: any;

        constructor() {
            var self = this;
            self.texteditor = {
                value: ko.observable('1'),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "30px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
    }

    class Label_005 {
        constraint: string = 'LayoutName';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;


        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.enable = ko.observable(true);
        }
    }

    class Label_006 {
        constraint: string = 'LayoutName';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;


        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.enable = ko.observable(true);
        }
    }

    class Input_005 {
        multilineeditor: any;
        constructor() {
            var self = this;
            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "Placeholder for text editor",
                    width: "380",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
    }

}