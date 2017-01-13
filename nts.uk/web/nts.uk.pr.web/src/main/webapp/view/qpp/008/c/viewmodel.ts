module qpp008.c.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        /*GridList*/
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentItem: KnockoutObservable<any>;
        nameValue: KnockoutObservable<string>;
        codeValue: KnockoutObservable<any>;
        /*TextEditer*/
        enableC_INP_002: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);

            /*gridList*/
            self.items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            for (var j = 0; j < 4; j++) {
                for (var i = 1; i < 51; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    this.items.push(new ItemModel(code, code, code, code));
                }
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 230 },

            ]);
            //get event when hover on table by subcribe
            self.currentCode = ko.observable();
            self.currentItem = ko.observable(ko.mapping.fromJS(_.first(self.items())));
            self.currentCode.subscribe(function(codeChanged) {
                self.currentItem(ko.mapping.fromJS(self.getItem(codeChanged)));
            });
            /*TextEditer*/
            self.enableC_INP_002 = ko.observable(false);
        }

        getItem(codeNew): ItemModel {
            let self = this;
            let currentItem: ItemModel = _.find(self.items(), function(item) {
                return item.code === codeNew;
            });

            return currentItem;
        }

        addItem() {
            this.items.push(new ItemModel('999', '基本給', "description 1", "other1"));
        }

        removeItem() {
            this.items.shift();
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getPaymentDateProcessingList().done(function(data) {
                // self.paymentDateProcessingList(data);
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }
    }
    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        constructor(code: string, name: string, description: string, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
        }
    }
}

