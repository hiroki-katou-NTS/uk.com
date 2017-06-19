module ccg018.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        itemList: KnockoutObservableArray<ItemModel1>;
        selectedCode1: KnockoutObservable<string>;
        selectedCode2: KnockoutObservable<string>;
        employeeCode: KnockoutObservable<string>;
        employeeName: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.items.push(new ItemModel('A00000' + i, '基本給' + i, "役職手当 " + i, "基本給 " + i));
            }
            self.currentCode = ko.observable(self.items()[0].code);
            self.employeeCode = ko.observable(self.items()[0].code);
            self.employeeName = ko.observable(self.items()[0].name);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 80 },
                { headerText: '名称', key: 'name', width: 80 },
                { headerText: '説明', key: 'description', width: 120 },
                { headerText: '説明1', key: 'other1', width: 120 },
            ]);

            self.itemList = ko.observableArray([
                new ItemModel1('1', '基本給'),
                new ItemModel1('2', '役職手当'),
                new ItemModel1('3', '基本給')
            ]);
            self.selectedCode1 = ko.observable('1');
            self.selectedCode2 = ko.observable('3');
            self.currentCode.subscribe(function(codeChange: any) {
                self.employeeCode(codeChange);
                self.employeeName(_.find(self.items(), ['code', codeChange]).name);
            });
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        openDialogC() {
            nts.uk.ui.windows.sub.modal("/view/ccg/018/c/index.xhtml", { dialogClass: "no-close" });
        }
    }

    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        constructor(code: string, name: string, description: string, other1: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
        }
    }

    class ItemModel1 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}