var qmm018;
(function (qmm018) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                qcamt_Item_SEL_3: "pr/proto/item/findall/bycategory/{0}"
            };
            function qcamt_Item_SEL_3(categoryAtr) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_SEL_3, categoryAtr))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qcamt_Item_SEL_3 = qcamt_Item_SEL_3;
        })(service = b.service || (b.service = {}));
    })(b = qmm018.b || (qmm018.b = {}));
})(qmm018 || (qmm018 = {}));
