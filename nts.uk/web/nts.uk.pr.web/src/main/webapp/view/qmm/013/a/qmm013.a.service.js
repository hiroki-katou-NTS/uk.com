var qmm013;
(function (qmm013) {
    var a;
    (function (a) {
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
            /**
             * get data from database base-on property 'display' true/false
             */
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
            /**
             * get data from database P_UNITPRICE
             */
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
            /**
             * add data in database P_UNITPRICE
             */
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
            /**
             * remove data in database P_UNITPRICE
             */
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
        })(service = a.service || (a.service = {}));
    })(a = qmm013.a || (qmm013.a = {}));
})(qmm013 || (qmm013 = {}));
