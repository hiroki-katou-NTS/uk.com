module qmm018.b.service {
    var paths: any = {
        getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
    }
    
    export function getPaymentDateProcessingList(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getPaymentDateProcessingList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function getItemList(): JQueryPromise<Array<ItemModel>> {
        var items = ko.observableArray([
                        new ItemModel('004', 'name4'),
                        new ItemModel('005', 'name5'),
                        new ItemModel('006', 'name6')
                    ]);;
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getPaymentDateProcessingList)
            .done(function(res: Array<any>) {
                
                dfd.resolve(res);
                
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise(items);
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