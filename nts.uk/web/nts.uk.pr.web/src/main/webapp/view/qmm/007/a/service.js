var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm007;
                (function (qmm007) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                getUnitPriceHistoryList: "pr/proto/unitprice/findall",
                                getUnitPriceHistoryDetail: "pr/proto/unitprice/find",
                                createUnitPriceHistory: "pr/proto/unitprice/create",
                                updateUnitPriceHistory: "pr/proto/unitprice/update",
                                removeUnitPriceHistory: "pr/proto/unitprice/remove"
                            };
                            function getUnitPriceHistoryList() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.getUnitPriceHistoryList)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getUnitPriceHistoryList = getUnitPriceHistoryList;
                            function find(id) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.getUnitPriceHistoryDetail + "/" + id)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.find = find;
                            function create(unitPriceHistory) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.createUnitPriceHistory, unitPriceHistory)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.create = create;
                            function update(unitPriceHistory) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.updateUnitPriceHistory, unitPriceHistory)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.update = update;
                            function remove(histId, unitPriceCode) {
                                var command = { id: histId, unitPriceCode: unitPriceCode };
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.removeUnitPriceHistory, command)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.remove = remove;
                            var model;
                            (function (model) {
                                var UnitPriceHistoryDto = (function () {
                                    function UnitPriceHistoryDto() {
                                    }
                                    return UnitPriceHistoryDto;
                                }());
                                model.UnitPriceHistoryDto = UnitPriceHistoryDto;
                                var UnitPriceHistoryItemDto = (function () {
                                    function UnitPriceHistoryItemDto(id, startMonth, endMonth) {
                                        this.id = id;
                                        this.startMonth = startMonth;
                                        this.endMonth = endMonth;
                                    }
                                    return UnitPriceHistoryItemDto;
                                }());
                                model.UnitPriceHistoryItemDto = UnitPriceHistoryItemDto;
                                var UnitPriceItemDto = (function () {
                                    function UnitPriceItemDto(unitPriceCode, unitPriceName, histories) {
                                        this.unitPriceCode = unitPriceCode;
                                        this.unitPriceName = unitPriceName;
                                        this.histories = histories;
                                    }
                                    return UnitPriceItemDto;
                                }());
                                model.UnitPriceItemDto = UnitPriceItemDto;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
