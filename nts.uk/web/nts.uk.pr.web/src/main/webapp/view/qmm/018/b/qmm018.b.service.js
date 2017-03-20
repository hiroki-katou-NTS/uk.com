var qmm018;
(function (qmm018) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                itemSelect: "pr/core/item/findall/category/{0}"
            };
            function itemSelect(categoryAtr) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.itemSelect, categoryAtr))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.itemSelect = itemSelect;
        })(service = b.service || (b.service = {}));
    })(b = qmm018.b || (qmm018.b = {}));
})(qmm018 || (qmm018 = {}));
