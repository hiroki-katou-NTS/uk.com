var qmm018;
(function (qmm018) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                getItemList: "pr/proto/item/findall/bycategory/0"
            };
            function getItemList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getItemList)
                    .done(function (res) {
                    console.log(res);
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getItemList = getItemList;
        })(service = b.service || (b.service = {}));
    })(b = qmm018.b || (qmm018.b = {}));
})(qmm018 || (qmm018 = {}));
