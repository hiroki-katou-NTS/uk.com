module nts.uk.at.view.kwr001.c {
    export module viewmodel {
        export class ScreenModel {
            data: KnockoutObservable<number>;
            
            // list
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;
            
            C3_2_value: KnockoutObservable<string>;
            C3_3_value: KnockoutObservable<string>;
            
            // switch button
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            
            currentCodeListSwap: KnockoutObservableArray<any>;
            test: KnockoutObservableArray<any>;

            
            constructor() {
                var self = this;
                self.data = ko.observable(1);
                
                self.items = ko.observableArray([]);
                var str = ['a0', 'b0', 'c0', 'd0'];
                for(var j = 0; j < 4; j++) {
                    for(var i = 1; i < 51; i++) {    
                        var code = i < 10 ? str[j] + '0' + i : str[j] + i;         
                        self.items.push(new ItemModel(code,code));
                    } 
                }
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("#KWR001_52"), prop: 'code', width: 70 },
                    { headerText: nts.uk.resource.getText("#KWR001_53"), prop: 'name', width: 180 }
                ]);
                self.currentCode = ko.observable();
                self.currentCodeList = ko.observableArray([]);
                self.C3_2_value = ko.observable("");
                self.C3_3_value = ko.observable("");
                
                self.roundingRules = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("#KWR001_58") },
                    { code: '2', name: nts.uk.resource.getText("#KWR001_59") }
                ]);
                self.selectedRuleCode = ko.observable(1);
                self.currentCodeListSwap = ko.observableArray([]);
                self.test = ko.observableArray([]);
            }
            
            remove(){
                var self = this;
                self.items.shift();            
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