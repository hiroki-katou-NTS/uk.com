var qmm019;
(function (qmm019) {
    var f;
    (function (f) {
        var service;
        (function (service) {
            var paths = {
                getItemsByCategory: "pr/proto/item/findall/bycategory/{0}",
                getItem: "pr/proto/item/find/{categoryAtr}/{itemCode}"
            };
            function getItemsByCategory(categoryAtr) {
                var dfd = $.Deferred();
                var objectItem = { categoryAtr: categoryAtr };
                var _path = nts.uk.text.format(paths.getItemsByCategory, categoryAtr);
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getItemsByCategory = getItemsByCategory;
            function getItem(categoryAtr, itemCode) {
                var dfd = $.Deferred();
                var objectItem = { categoryAtr: categoryAtr, itemCode: itemCode };
                var _path = nts.uk.text.format(paths.getItem, categoryAtr, itemCode);
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getItem = getItem;
            var model;
            (function (model) {
                var ItemDetailModel = (function () {
                    function ItemDetailModel() {
                    }
                    return ItemDetailModel;
                }());
                model.ItemDetailModel = ItemDetailModel;
            })(model = service.model || (service.model = {}));
        })(service = f.service || (f.service = {}));
    })(f = qmm019.f || (qmm019.f = {}));
})(qmm019 || (qmm019 = {}));
