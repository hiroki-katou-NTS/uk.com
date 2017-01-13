var qmm018;
(function (qmm018) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
            };
            function getPaymentDateProcessingList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getPaymentDateProcessingList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getPaymentDateProcessingList = getPaymentDateProcessingList;
            function getItemList() {
                var items = ko.observableArray([
                    new ItemModel('004', 'name4'),
                    new ItemModel('005', 'name5'),
                    new ItemModel('006', 'name6')
                ]);
                ;
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getPaymentDateProcessingList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise(items);
            }
            service.getItemList = getItemList;
        })(service = b.service || (b.service = {}));
    })(b = qmm018.b || (qmm018.b = {}));
})(qmm018 || (qmm018 = {}));
var ItemModel = (function () {
    function ItemModel(code, name) {
        this.code = code;
        this.name = name;
    }
    return ItemModel;
}());
