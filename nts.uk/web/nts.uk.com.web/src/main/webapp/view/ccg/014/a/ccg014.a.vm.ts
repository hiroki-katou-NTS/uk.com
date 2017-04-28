module ccg014.a.viewmodel {
    export class ScreenModel {
        // Grid list
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        // text editor1
        texteditor1: any;
        simpleValue1: KnockoutObservable<string>;
        // text editor2
        texteditor2: any;
        simpleValue2: KnockoutObservable<string>;
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
            // text editor
            self.texteditor1 = {
            value: ko.observable(''),
            constraint: 'ResidenceCode',
            option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                textmode: "text",
                placeholder: "Placeholder for text editor",
                width: "100px",
                textalign: "left"
            })),
            required: ko.observable(true),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        };
            self.texteditor2 = {
            value: ko.observable(''),
            constraint: 'ResidenceCode',
            option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                textmode: "text",
                placeholder: "Placeholder for text editor",
                width: "100px",
                textalign: "left"
            })),
            required: ko.observable(true),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        };
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
         }
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