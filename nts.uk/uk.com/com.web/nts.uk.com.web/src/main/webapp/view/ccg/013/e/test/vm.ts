module nts.uk.sys.view.ccg013.e.test.viewmodel {
    export class ScreenModel {
        currentCode: KnockoutObservable<any>;
        items: KnockoutObservableArray<MenuBar>;
        columns: KnockoutObservableArray<any>;
        selectMenuBar: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
             var str = ['a0', 'b0', 'c0', 'd0'];
            for(var j = 0; j < 4; j++) {
                for(var i = 1; i < 51; i++) {    
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;         
                    this.items.push(new MenuBar(code,code));
                } 
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'codeMenuBar', width: 100 },
                { headerText: '名称', prop: 'nameMenuBar', width: 230 }
            ]);
            self.currentCode = ko.observable('');
            self.selectMenuBar = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

    }
    class MenuBar {
        codeMenuBar: string;
        nameMenuBar: string;
        
        constructor(codeMenuBar: string, nameMenuBar: string) {
            this.codeMenuBar = codeMenuBar;
            this.nameMenuBar = nameMenuBar;
        }
    }
}