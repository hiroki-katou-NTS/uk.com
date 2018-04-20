module nts.uk.at.view.kwr001.c {
    export module viewmodel {
        export class ScreenModel {
            data: KnockoutObservable<number>;
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            
            constructor() {
                var self = this;
                self.data = ko.observable(1);
                
                this.items = ko.observableArray([]);
                var str = ['a0', 'b0', 'c0', 'd0'];
                for(var j = 0; j < 4; j++) {
                    for(var i = 1; i < 51; i++) {    
                        var code = i < 10 ? str[j] + '0' + i : str[j] + i;         
                        this.items.push(new ItemModel(code,code));
                    } 
                }
                this.columns = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 100 },
                    { headerText: '名称', prop: 'name', width: 230 }
                ]);
                this.currentCode = ko.observable();
                this.currentCodeList = ko.observableArray([]);
            }
            
            public startPage(): JQueryPromise<void>  {
                var dfd = $.Deferred<void>();
                var self = this;
                var data = nts.uk.ui.windows.getShared('KWR001_C');
                dfd.resolve();
                return dfd.promise();
            }
            openScreenD () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_D', self.data(), true);
                nts.uk.ui.windows.sub.modal('/view/kwr/001/d/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_D');
                });
            }
            
            saveData(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                
                dfd.resolve();
                return dfd.promise();
            }
        }
        
        class Node {
            code: string;
            name: string;
            nodeText: string;
            childs: any;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;         
            }
        } 
        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;  
            }
        } 
    }
}