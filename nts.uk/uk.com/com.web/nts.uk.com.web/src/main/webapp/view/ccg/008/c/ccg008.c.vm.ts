module nts.uk.com.view.ccg008.c {
    export module viewmodel {
        export class ScreenModel {
            index: number;
            dataItems: KnockoutObservableArray<model.Node>;
            selectedCode: KnockoutObservable<string>;
            columns: KnockoutObservableArray<any>;
            itemSelected: KnockoutObservable<model.TopPageSelfSet>;
            constructor() {
                var self = this;
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG008_8"), prop: 'code', width: 70 }
            ]);
                self.dataItems = ko.observableArray([]);
                self.itemSelected = ko.observable(null);
                self.selectedCode = ko.observable(null);
            }
            
            start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.getSelectMyTopPage().done(function(lst: Array<model.Node>) {
                    if(lst===null|| lst===undefined || lst.length==0){
                        self.dataItems([]);
                        self.selectedCode();
                    }else{
                        var items = _.map(res, function(item: any) {
                        return new model.Node(item.code, item.name);
                    });
                    self.dataItems(items);
                    service.getTopPageSelfSet().done(function(topPageSelfSet: model.TopPageSelfSet){
                        let itemSeletced = new model.TopPageSelfSet(topPageSelfSet.code,topPageSelfSet.division);
                        self.itemSelected(itemSeletced)
                        self.selectedCode(topPageSelfSet.code);
                    })
                    dfd.resolve();
                    }
                dfd.resolve();
                }).fail(function(err){
                    nts.uk.ui.dialog.alert(err);
                });
                
                return dfd.promise();
            }
                
            register(): void {
                var self = this;
                let data = new model.TopPageSelfSet(self.singleSelectedCode(),self.itemSelected().division);
                service.save(data).done(function(res) {
                }).fail(function(err){
                    nts.uk.ui.dialog.alert(err);
                });
            }
            
            closeDialog() {
                nts.uk.ui.windows.close();   
            }  
        }  
    }
    export module model {
        export class Node {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                var self = this;
                self.code = code;
                self.name = name;
            }
        }
        export class TopPageSelfSet{
            code: string;
            division: number;
            constructor(code: string,division: number){
                this.code = code;
                this.division = division;    
            }
        }   
    }
}