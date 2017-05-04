module ccg030.a.viewmodel {
    export class ScreenModel {
        // Grid list
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<string>;
        toppagepartcode: any;
        toppagepartname: any;
        width: any;
        height: any;
        fileName: KnockoutObservable<string>;
        enableDeleteMenu: KnockoutObservable<boolean>;
        enableDeleteFile: KnockoutObservable<boolean>;
        enableDownload: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.fileName = ko.observable("未設定");
            self.enableDeleteMenu = ko.observable(true);
            self.enableDeleteFile = ko.observable(true);
            self.enableDownload = ko.observable(true);
            //Grid list
            self.items = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0));
            }
            self.columns = ko.observableArray([
                { headerText: '既定', key: 'default', width: 50 },
                { headerText: 'コード', key: 'code', width: 80　},
                { headerText: '名称', key: 'name', width: 230　}                
            ]);
            self.currentCode = ko.observable("");
            //editor  Toppage part Code, Toppage part Name
            self.toppagepartcode = {
                value: ko.observable(''),
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    textmode: "text",
                    placeholder: "",
                    width: "30px",
                    textalign: "left"
                })),
                enableCode: ko.observable(true),
            };
            self.toppagepartname = {
                value: ko.observable(''),
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    textmode: "text",
                    placeholder: "",
                    width: "100px",
                    textalign: "left"
                })),
            };
            //横のサイズ
            self.width = {
                value: ko.observable(''),
            };
            //縦のサイズ
            self.height = {
                value: ko.observable(''),
            };

        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        // Open ccg030 B Dialog
        open030B_Dialog() {
            nts.uk.ui.windows.sub.modal("/view/ccg/030/b/index.xhtml", { title: 'プレビュー', dialogClass: "no-close" }).onClosed();
        }
        closeDialog(): any {
            nts.uk.ui.windows.close();
        }
        //新規ボタンをクリックする時
        createNewFlowMenu(){
            var self = this;
            self.toppagepartcode.value("");
            self.toppagepartcode.enableCode(true);
            self.toppagepartname.value("");
            self.currentCode("");
            self.fileName("未設定");
            self.enableDeleteFile(false);
            self.enableDeleteMenu(false);
            self.enableDownload(false);
            self.width.value("");
            self.height.value("");
        }
        
        //削除ボタンを押す時
        deleteNewFlowMenu(){
            
        }
        
        //登録ボタンを押す時
        registryFlowMenu(){
            //新規の時    
        }
    }
    
    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        deletable: boolean;
        switchValue: boolean;
        constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
        }
    }
}