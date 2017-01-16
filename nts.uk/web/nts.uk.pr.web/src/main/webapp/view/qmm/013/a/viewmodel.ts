module qmm013.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentName: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        roundingRules: KnockoutObservableArray<any>;
        SEL_004: KnockoutObservableArray<any>;
        SEL_005_check: any;
        SEL_006_check: any;
        SEL_007_check: any;
        SEL_008_check: any;
        SEL_009_check: any;
        SEL_004_check: any;
        texteditor1: any;
        texteditor2: any;
        multilineeditor: any;

        constructor() {
            var self = this;
            this.items = ko.observableArray([
            new ItemModel('001', '基本給', "description 1"),
            new ItemModel('150', '役職手当', "description 2"),
            new ItemModel('ABC', '基本給', "description 3")
            ]);
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 },
                { headerText: '廃止', prop: 'abolition', width: 50 }
            ]);
            
            self.itemList = ko.observableArray([
            new BoxModel(1, '全員一律で指定する'),
            new BoxModel(2, '給与約束形態ごとに指定する')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
            
            
            self.roundingRules = ko.observableArray([
            { code: '1', name: '対象' },
            { code: '2', name: '対象外' }
            ]);
            
            
            self.SEL_004 = ko.observableArray([
            { code: '1', name: '時間単価' },
            { code: '2', name: '金額' },
            { code: '3', name: 'その他' },
            ]);
            self.SEL_005_check = ko.observable(1);
            self.SEL_006_check = ko.observable(1);
            self.SEL_007_check = ko.observable(1);
            self.SEL_008_check = ko.observable(1);
            self.SEL_009_check = ko.observable(1);
            self.SEL_004_check = ko.observable(1);
            
            this.currentCode = ko.observable();
            this.currentName = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            
            self.texteditor1 = {
                value: self.currentCode,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "45px",
                    textalign: "center"
                })),
            };

            self.texteditor2 = {
                value: self.currentName,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "180px",
                    textalign: "left"
                })),
            };
            
             self.multilineeditor = {
                value: ko.observable(''),
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    width: "450px",
                    textalign: "left"
                })),
            };
            }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            
            var dfd = $.Deferred();
            qmm013.a.service.getPaymentDateProcessingList().done(function() {
                dfd.resolve();
            }).fail(function() {
                
            });
            return dfd.promise();
        }
    }

    class ItemModel {
    code: string;
    name: string;
    abolition: string;
    
    constructor(code: string, name: string, abolition: string) {
        this.code = code;
        this.name = name;
        this.abolition = abolition;
    }
   }
   
    class BoxModel {
    id: number;
    name: string;
    constructor(id, name){
        var self = this;
        self.id = id;
        self.name = name;
    }
   }
}