var qmm013;
(function (qmm013) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                getPersonalUnitPriceList: "pr/core/rule/employment/unitprice/personal/find/all",
                getPersonalUnitPriceListNoneDisplay: "pr/core/rule/employment/unitprice/personal/find/all/nonedisplay",
                getPersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/find/",
                addPersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/add",
                updatePersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/update",
                removePersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/remove"
            };
            function getPersonalUnitPriceList(display) {
                var dfd = $.Deferred();
                var path = display ? paths.getPersonalUnitPriceList : paths.getPersonalUnitPriceListNoneDisplay;
                nts.uk.request.ajax(path)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getPersonalUnitPriceList = getPersonalUnitPriceList;
            function getPersonalUnitPrice(code) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getPersonalUnitPrice + "" + code)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getPersonalUnitPrice = getPersonalUnitPrice;
            function addPersonalUnitPrice(isCreated, data) {
                var dfd = $.Deferred();
                var path = isCreated ? paths.addPersonalUnitPrice : paths.updatePersonalUnitPrice;
                nts.uk.request.ajax(path, data)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addPersonalUnitPrice = addPersonalUnitPrice;
            function removePersonalUnitPrice(data) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.removePersonalUnitPrice, data)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.removePersonalUnitPrice = removePersonalUnitPrice;
        })(service = b.service || (b.service = {}));
    })(b = qmm013.b || (qmm013.b = {}));
})(qmm013 || (qmm013 = {}));
