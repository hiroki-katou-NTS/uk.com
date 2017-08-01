module nts.uk.at.view.kdw008.c {
    export module viewmodel {
        export class ScreenModel {
            idList:KnockoutObservable<string>;
            itemsSwap: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            testSingle: KnockoutObservable<any>;

            constructor() {
                var self = this;
                this.idList = ko.observable('');
                this.itemsSwap = ko.observableArray([]);

                var array = [];
                for (var i = 0; i < 13; i++) {
                    var x = {
                        id: "test"+i,
                        code: "test"+i,
                        name: '基本給'
                    };
                    array.push(new ItemModel(x));
                }
                this.itemsSwap(array);

                this.columns = ko.observableArray([
                    { headerText: '', key: 'code', width: 100 },
                    { headerText: '', key: 'name', width: 150 }
                ]);
                this.testSingle = ko.observable(null);
            }

            findAll(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                var idList = this.idList();
                service.findAll(idList).done(function(data) {
                    //Convert list Object from server to list UnitPrice view model
                    let items = _.map(data, item => {
                        return new ItemModel(item);
                    });
                    self.itemsSwap(items);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            update(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                var idList = this.idList();
                var itemsSwap = this.itemsSwap();
                service.update(new UpdateData(idList, itemsSwap)).done(function(data) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_3" });
                    dfd.resolve();
                });
                return dfd.promise();
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
//                $.when(self.findAll()).done(function() {
//                    dfd.resolve();
//                });
                dfd.resolve();
                return dfd.promise();
            }
        }

        export class UpdateData{
            idList: string;
            itemsSwap: ItemModel[];
            constructor(idList:string, itemsSwap: ItemModel[]){
                this.idList = idList;
                this.itemsSwap = itemsSwap;
            }
        }
        
        export class ItemModel {
            id: string;
            code: string;
            name: string;
            constructor(x:ItemModelInterface) {
                if(x){
                    this.id = x.id;
                    this.code = x.code;
                    this.name = x.name;
                }else{
                    this.id ='';
                    this.code = '';
                    this.name = '';
                }    
            }
        }
        
        export interface ItemModelInterface{
            id: string;
            code: string;
            name: string;
        }
    }
}
