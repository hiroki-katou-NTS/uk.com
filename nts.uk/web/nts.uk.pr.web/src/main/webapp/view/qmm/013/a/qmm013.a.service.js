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
                var path = display ? paths.getPersonalUnitPriceList : paths.getPersonalUnitPriceListNoneDisplay;
                return nts.uk.request.ajax(path);
            }
            service.getPersonalUnitPriceList = getPersonalUnitPriceList;
            /**
             * get data from database P_UNITPRICE
             */
            function getPersonalUnitPrice(code) {
                return nts.uk.request.ajax(paths.getPersonalUnitPrice + "" + code);
            }
            service.getPersonalUnitPrice = getPersonalUnitPrice;
            /**
             * add data in database P_UNITPRICE
             */
            function addPersonalUnitPrice(isCreated, data) {
                var path = isCreated ? paths.addPersonalUnitPrice : paths.updatePersonalUnitPrice;
                return nts.uk.request.ajax(path, data);
            }
            service.addPersonalUnitPrice = addPersonalUnitPrice;
            /**
             * remove data in database P_UNITPRICE
             */
            function removePersonalUnitPrice(data) {
                return nts.uk.request.ajax(paths.removePersonalUnitPrice, data);
            }
            service.removePersonalUnitPrice = removePersonalUnitPrice;
        })(service = a.service || (a.service = {}));
    })(a = qmm013.a || (qmm013.a = {}));
})(qmm013 || (qmm013 = {}));
//# sourceMappingURL=qmm013.a.service.js.map