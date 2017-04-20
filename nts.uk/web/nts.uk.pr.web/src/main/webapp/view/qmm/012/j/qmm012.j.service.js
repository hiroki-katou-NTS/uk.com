var qmm012;
(function (qmm012) {
    var j;
    (function (j) {
        var service;
        (function (service) {
            var paths = {
                findAllItemMasterByCategory: "pr/core/item/findall/category",
                updateNameItemMaster: "pr/core/item/updateName"
            };
            function findAllItemMasterByCategory(ctgAtr) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findAllItemMasterByCategory + "/" + ctgAtr)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res.message);
                });
                return dfd.promise();
            }
            service.findAllItemMasterByCategory = findAllItemMasterByCategory;
            function updateNameItemMaster(itemList) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateNameItemMaster, itemList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res.message);
                });
                return dfd.promise();
            }
            service.updateNameItemMaster = updateNameItemMaster;
        })(service = j.service || (j.service = {}));
    })(j = qmm012.j || (qmm012.j = {}));
})(qmm012 || (qmm012 = {}));
