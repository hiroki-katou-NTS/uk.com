module ccg030.a.viewmodel {
    export class ScreenModel {
        // Grid list
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        toppagepartcode: any;
        toppagepartname: any;
        width: any;
        height: any;
        constructor() {
            var self = this;
            //Grid list
            self.items = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0));
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100, hidden: true },
                { headerText: '名称', key: 'name', width: 150, hidden: true },
                { headerText: '説明', key: 'description', width: 150 },
                { headerText: '説明1', key: 'other1', width: 150 }
            ]);
            self.currentCodeList = ko.observableArray([]);
            //editor  Toppage part Code, Toppage part Name
            self.toppagepartcode = {
                value: ko.observable(''),
                constraint: 'TopPagePartCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    textmode: "text",
                    placeholder: "",
                    width: "30px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.toppagepartname = {
                value: ko.observable(''),
                constraint: 'TopPagePartName',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    textmode: "text",
                    placeholder: "",
                    width: "100px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.width = {
                value: ko.observable(''),
                constraint: 'width',
                required: ko.observable(true),
            };
            self.height = {
                value: ko.observable(''),
                constraint: 'height',
                required: ko.observable(true),
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