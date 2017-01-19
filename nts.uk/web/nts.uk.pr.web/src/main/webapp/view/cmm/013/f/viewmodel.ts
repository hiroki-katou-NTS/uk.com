module cmm013.f.viewmodel {
    import option = nts.uk.ui.option;

    export class ScreenModel {
        textSearch: any;
        employmentCode: KnockoutObservable<string>;
        employmentName: KnockoutObservable<string>;
        textEditorOption: KnockoutObservable<any>;
        items: KnockoutObservableArray<viewmodel.ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            self.employmentCode = ko.observable("");
            self.employmentName = ko.observable("");
            self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
            self.textSearch = {
                valueSearch: ko.observable(""),
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "繧ｳ繝ｼ繝峨・蜷咲ｧｰ縺ｧ讀懃ｴ｢繝ｻ繝ｻ繝ｻ",
                    width: "75%",
                    textalign: "left"
                }))
            }
            self.items = ko.observableArray([
                new ItemModel('001', '蝓ｺ譛ｬ邨ｦ'),
                new ItemModel('150', '蠖ｹ閨ｷ謇句ｽ'),
                new ItemModel('ABC', '蝓ｺ12譛ｬ邨ｦ')
            ]);
            self.columns = ko.observableArray([
                { headerText: '繧ｳ繝ｼ繝・', prop: 'code', width: 100 },
                { headerText: '蜷咲ｧｰ', prop: 'name', width: 150 }
            ]);
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray([]);


        }
        selectSomeItems() {
            this.currentCode('150');
            this.currentCodeList.removeAll();
            this.currentCodeList.push('001');
            this.currentCodeList.push('ABC');
        }
        
        
        deselectAll() {
            this.currentCode(null);
            this.currentCodeList.removeAll();
        }
    }

    export class ItemCloseDate {
        closeDateCode: number;
        closeDatename: string;
        constructor(closeDateCode: number, closeDatename: string) {
            this.closeDateCode = closeDateCode;
            this.closeDatename = closeDatename;
        }
    }

    export class ItemProcessingDate {
        processingDateCode: number;
        processingDatename: string;
        constructor(processingDateCode: number, processingDatename: string) {
            this.processingDateCode = processingDateCode;
            this.processingDatename = processingDatename;
        }
    }
    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}
