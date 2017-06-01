var qpp011;
(function (qpp011) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                find: "pr/core/rule/law/tax/residential/output/find",
                add: "pr/core/rule/law/tax/residential/output/add",
                update: "pr/core/rule/law/tax/residential/output/update",
                findallresidential: "pr/core/residential/findallresidential",
                getlistLocation: "pr/core/residential/getlistLocation",
            };
            /**
             * Get list payment date processing.
             */
            function findresidentialTax(resimentTaxCode, yearMonth) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.find + "/" + resimentTaxCode + "/" + yearMonth)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findresidentialTax = findresidentialTax;
            function getlistLocation() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getlistLocation)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getlistLocation = getlistLocation;
            function findAllResidential() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findallresidential)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findAllResidential = findAllResidential;
            function add(residentialTax) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.add, residentialTax)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.add = add;
            function update(residentialTax) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.update, residentialTax)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.update = update;
            var model;
            (function (model) {
                var residentialTax = (function () {
                    function residentialTax(resimentTaxCode, yearMonth, taxPayRollMoney, taxBonusMoney, taxOverDueMoney, taxDemandChargeMoyney, address, dueDate, headcount, retirementBonusAmout, cityTaxMoney, prefectureTaxMoney) {
                        this.resimentTaxCode = ko.observable(resimentTaxCode);
                        this.yearMonth = ko.observable(yearMonth);
                        this.taxPayRollMoney = ko.observable(taxPayRollMoney);
                        this.taxBonusMoney = ko.observable(taxBonusMoney);
                        this.taxOverDueMoney = ko.observable(taxOverDueMoney);
                        this.taxDemandChargeMoyney = ko.observable(taxDemandChargeMoyney);
                        this.address = ko.observable(address);
                        this.dueDate = ko.observable(dueDate);
                        this.headcount = ko.observable(headcount);
                        this.retirementBonusAmout = ko.observable(retirementBonusAmout);
                        this.cityTaxMoney = ko.observable(cityTaxMoney);
                        this.prefectureTaxMoney = ko.observable(prefectureTaxMoney);
                    }
                    return residentialTax;
                }());
                model.residentialTax = residentialTax;
            })(model = service.model || (service.model = {}));
        })(service = d.service || (d.service = {}));
    })(d = qpp011.d || (qpp011.d = {}));
})(qpp011 || (qpp011 = {}));
//# sourceMappingURL=qpp011.d.service.js.map