module qmm010.a.viewmodel {
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
                    placeholder: "コード・名称で検索・・・",
                    width: "75%",
                    textalign: "left"
                }))
            }
            self.items = ko.observableArray([
                new ItemModel('001', '基本給'),
                new ItemModel('150', '役職手当'),
                new ItemModel('ABC', '基12本ghj給')
            ]);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 }
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
