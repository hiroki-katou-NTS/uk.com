module nts.uk.at.view.kmk007.c.viewmodel {
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservable<any>;
        oldDataItems: KnockoutObservableArray<ItemModel>;
        newDataItems: KnockoutObservableArray<WorkTypeDispOrder>;
        selectedCodes: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            
            self.items = ko.observableArray([]);
            self.oldDataItems = ko.observableArray([]);
            self.newDataItems = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KMK007_58"), prop: 'code', width: 50 },
                { headerText: nts.uk.resource.getText("KMK007_59"), prop: 'name', width: 180, formatter: _.escape }
            ]);
            
            self.selectedCodes = ko.observableArray([]);
            
            var data = nts.uk.ui.windows.getShared("KMK007_WORK_TYPES");
            
            for(var i = 0; i < data.length; i++){
                self.oldDataItems.push(new ItemModel(data[i].workTypeCode, data[i].name, i));
            }
            
            self.items = ko.observableArray(self.oldDataItems());
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        
        /**
         * Save new order items to db
         */
        decide() {
            var self = this;
            
            nts.uk.ui.block.grayout();
            
            var newData = self.items();
            
            for(var i = 0; i < newData.length; i++){
                self.newDataItems.push(new WorkTypeDispOrder({workTypeCode: newData[i].code, dispOrder: i}));
            }
            
            service.order(self.newDataItems()).done(function(data){
                nts.uk.ui.windows.close();
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);      
            }).then(function() {
                nts.uk.ui.block.clear();    
            });
        }
        
        /**
         * Re-order items on list
         */
        initialize() {
            var self = this;

            dialog.confirm({ messageId: "Msg_414" }).ifYes(() => {
                nts.uk.ui.block.invisible();
                service.initializeOrder().done(data => {
                    self.oldDataItems([]);
                    for (let i = 0; i < data.length; i++) {
                        self.oldDataItems.push(new ItemModel(data[i].workTypeCode, data[i].name, i));
                    }
                    self.items([]);
                    self.items(self.oldDataItems());
                    self.selectedCodes.removeAll();
                    dialog.info({ messageId: "Msg_394" });
                }).always(() => {
                    nts.uk.ui.block.clear();
                })
            });
        }

        /**
         * Close the popup
         */
        cancel() {
            nts.uk.ui.windows.close();
        }
    }
    
    class ItemModel {
        code: string;
        name: string;
        order: number;
        constructor(code: string, name: string, order: number) {
            this.code = code;
            this.name = name;
            this.order = order;
        }
    }
    
    export interface IWorkTypeDispOrder {
        workTypeCode: string,
        dispOrder: number
    }
    
    class WorkTypeDispOrder {
        workTypeCode: string;
        dispOrder: number;
        constructor(param: IWorkTypeDispOrder) {
            this.workTypeCode = param.workTypeCode;
            this.dispOrder = param.dispOrder;
        }
    }
}