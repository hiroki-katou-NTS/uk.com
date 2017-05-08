module kdl021.viewmodel {
    export class ScreenModel {
        //parameter
        isMulti: boolean;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            self.isMulti = true;
            self.items = ko.observableArray([]);
            var str = ['a0', 'b0'];
            for (var i = 1; i <= 15; i++) {
                let code = padZero(i.toString());
                let name = "残業時間" + i;
                this.items.push(new ItemModel(code, name));
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 70 },
                { headerText: '名称', prop: 'name', width: 230 }
            ]);
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray([]);
        }
    }
    //Format Code : Pad 0 to Code
    function padZero(code: string) {
        var length: number = code.length;
        var codeFm: string = "00000";
        return codeFm.substr(0, 5 - length) + code;
    }
    //
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}