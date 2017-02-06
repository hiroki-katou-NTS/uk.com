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
                                return nts.uk.request.ajax(paths.createUnitPriceHistory, unitPriceHistory);
                            }
                            service.create = create;
                            function update(unitPriceHistory) {
                                return nts.uk.request.ajax(paths.updateUnitPriceHistory, unitPriceHistory);
                            }
                            service.update = update;
                            function remove(id, version) {
                                var request = { id: id, version: version };
                                return nts.uk.request.ajax(paths.removeUnitPriceHistory, request);
                            }
                            service.remove = remove;
                            function collectData(unitPriceHistoryModel) {
                                var dto = new service.model.UnitPriceHistoryDto();
                                dto.id = unitPriceHistoryModel.id;
                                dto.version = unitPriceHistoryModel.version;
                                dto.unitPriceCode = unitPriceHistoryModel.unitPriceCode();
                                dto.unitPriceName = unitPriceHistoryModel.unitPriceName();
                                dto.startMonth = unitPriceHistoryModel.startMonth();
                                dto.endMonth = unitPriceHistoryModel.endMonth();
                                dto.budget = unitPriceHistoryModel.budget();
                                dto.fixPaySettingType = unitPriceHistoryModel.fixPaySettingType();
                                dto.fixPayAtr = unitPriceHistoryModel.fixPayAtr();
                                dto.fixPayAtrMonthly = unitPriceHistoryModel.fixPayAtrMonthly();
                                dto.fixPayAtrDayMonth = unitPriceHistoryModel.fixPayAtrDayMonth();
                                dto.fixPayAtrDaily = unitPriceHistoryModel.fixPayAtrDaily();
                                dto.fixPayAtrHourly = unitPriceHistoryModel.fixPayAtrHourly();
                                dto.memo = unitPriceHistoryModel.memo();
                                return dto;
                            }
                            service.collectData = collectData;
                            var model;
                            (function (model) {
                                var UnitPriceHistoryDto = (function () {
                                    function UnitPriceHistoryDto() {
                                    }
                                    return UnitPriceHistoryDto;
                                }());
                                model.UnitPriceHistoryDto = UnitPriceHistoryDto;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
