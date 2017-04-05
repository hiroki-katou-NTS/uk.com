var qpp011;
(function (qpp011) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                find: "pr/core/rule/law/tax/residential/output/find",
                add: "pr/core/rule/law/tax/residential/output/add",
                update: "pr/core/rule/law/tax/residential/output/update",
                findAllResidential: "pr/core/residential/findallresidential",
                findAllLinebank: "basic/system/bank/linebank/findAll",
                findLinebank: "basic/system/bank/linebank/find",
                getlistLocation: "pr/core/residential/getlistLocation",
                currentProcessingNo: "pr/proto/paymentdatemaster/processing/findbylogin",
                findallRegalDoc: "pr/core/rule/law/tax/residential/output/findallRegalDoc",
                saveAsPdf: "screen/pr/qpp011/saveAsPdf"
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
            function findallRegalDoc() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findallRegalDoc)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findallRegalDoc = findallRegalDoc;
            function getCurrentProcessingNo() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.currentProcessingNo)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getCurrentProcessingNo = getCurrentProcessingNo;
            function findLinebank(lineBankCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.findLinebank + "/" + lineBankCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findLinebank = findLinebank;
            function findAllResidential() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findAllResidential)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findAllResidential = findAllResidential;
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
            function findAllLinebank() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.findAllLinebank)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findAllLinebank = findAllLinebank;
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
            function saveAsPdf(command) {
                return nts.uk.request.exportFile(paths.saveAsPdf, command);
            }
            service.saveAsPdf = saveAsPdf;
            var model;
            (function (model) {
                var residentialTax = (function () {
                    function residentialTax(resimentTaxCode, yearMonth, taxBonusMoney, taxOverDueMoney, taxDemandChargeMoyney, address, dueDate, headcount, retirementBonusAmout, cityTaxMoney, prefectureTaxMoney) {
                        this.resimentTaxCode = resimentTaxCode;
                        this.yearMonth = yearMonth;
                        this.taxBonusMoney = taxBonusMoney;
                        this.taxOverDueMoney = taxOverDueMoney;
                        this.taxDemandChargeMoyney = taxDemandChargeMoyney;
                        this.address = address;
                        this.dueDate = dueDate;
                        this.headcount = headcount;
                        this.retirementBonusAmout = retirementBonusAmout;
                        this.cityTaxMoney = cityTaxMoney;
                        this.prefectureTaxMoney = prefectureTaxMoney;
                    }
                    return residentialTax;
                }());
                model.residentialTax = residentialTax;
            })(model = service.model || (service.model = {}));
        })(service = b.service || (b.service = {}));
    })(b = qpp011.b || (qpp011.b = {}));
})(qpp011 || (qpp011 = {}));
