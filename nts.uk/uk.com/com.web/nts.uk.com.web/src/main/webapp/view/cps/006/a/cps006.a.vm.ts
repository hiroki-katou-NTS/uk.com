module nts.uk.com.view.cps006.a.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        categoryList: KnockoutObservableArray<any> = ko.observableArray([]);
        itemList: KnockoutObservableArray<any> = ko.observableArray([]);
        currentCategoryId: KnockoutObservable<any> = ko.observable("");
        currentItemId : KnockoutObservable<any> = ko.observable("");
        colums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'id', key: 'id', width: 100, hidden: true },
            { headerText: text('CPS006_6'), key: 'categoryName', width: 220 },
            { headerText: text('CPS006_7'), key: 'isAbolition', width: 50 }
        ]);
        itemColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'id', key: 'id', width: 100, hidden: true },
            { headerText: text('CPS006_16'), key: 'itemName', width: 250 },
            { headerText: text('CPS006_17'), key: 'isAbolition', width: 50 }
        ]);

        constructor() {
            let self = this;
            self.start();
            self.currentCategoryId.subscribe(function(value){
                service.getAllPerInfoItemDefByCtgId(value).done(function(data: Array<any>){
                    if(data.length > 0){
                      self.itemList(data);  
                    };  
                })
                
            })
        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            
            service.getAllCategory().done(function(data) {
                if (data.length > 0) {
                    self.categoryList(data);
                    self.currentCategoryId(self.categoryList()[0].id);
                }
                dfd.resolve();
            });
            
            return dfd.promise();
        }

    }
    
    

}