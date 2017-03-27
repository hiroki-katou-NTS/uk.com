var cmm015;
(function (cmm015) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getItemsByCategory: "pr/proto/item/findall/bycategory/{0}",
                getItem: "pr/proto/item/find/{categoryAtr}/{itemCode}",
                getLayoutMasterDetail: "pr/proto/layout/findlayoutdetail/{0}/{1}/{2}/{3}"
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
            function getLayoutMasterDetail(stmtCode, startYm, categoryAtr, itemCd) {
                var dfd = $.Deferred();
                var objectItem = { stmtCode: stmtCode, startYm: startYm, categoryAtr: categoryAtr, itemCd: itemCd };
                var _path = nts.uk.text.format(paths.getLayoutMasterDetail, stmtCode, startYm, categoryAtr, itemCd);
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getLayoutMasterDetail = getLayoutMasterDetail;
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
        })(service = a.service || (a.service = {}));
    })(a = cmm015.a || (cmm015.a = {}));
})(cmm015 || (cmm015 = {}));
//# sourceMappingURL=service.js.map