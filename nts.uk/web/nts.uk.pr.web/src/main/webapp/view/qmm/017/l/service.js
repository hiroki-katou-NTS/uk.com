var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var l;
                    (function (l) {
                        var service;
                        (function (service) {
                            var paths = {
                                getListCompanyUnitPrice: "pr/proto/unitprice/findbymonth/",
                                getListPersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/find/all",
                                getListItemMaster: "pr/core/item/findall/category/"
                            };
                            function getListCompanyUnitPrice(baseDate) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax("pr", paths.getListCompanyUnitPrice + baseDate)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getListCompanyUnitPrice = getListCompanyUnitPrice;
                            function getListPersonalUnitPrice() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax("pr", paths.getListPersonalUnitPrice)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getListPersonalUnitPrice = getListPersonalUnitPrice;
                            function getListItemMaster(categoryAtr) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax("pr", paths.getListItemMaster + categoryAtr)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getListItemMaster = getListItemMaster;
                        })(service = l.service || (l.service = {}));
                        var model;
                        (function (model) {
                            var CompanyUnitPriceDto = (function () {
                                function CompanyUnitPriceDto() {
                                }
                                return CompanyUnitPriceDto;
                            }());
                            model.CompanyUnitPriceDto = CompanyUnitPriceDto;
                            var PersonalUnitPriceDto = (function () {
                                function PersonalUnitPriceDto() {
                                }
                                return PersonalUnitPriceDto;
                            }());
                            model.PersonalUnitPriceDto = PersonalUnitPriceDto;
                            var ItemMasterDto = (function () {
                                function ItemMasterDto() {
                                }
                                return ItemMasterDto;
                            }());
                            model.ItemMasterDto = ItemMasterDto;
                        })(model = l.model || (l.model = {}));
                    })(l = qmm017.l || (qmm017.l = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
