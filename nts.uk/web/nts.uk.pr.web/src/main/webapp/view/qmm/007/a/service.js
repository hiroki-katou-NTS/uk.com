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
                                getUnitPriceHistoryList: "pr/proto/unitprice/findall"
                            };
                            function convertToTreeList() {
                                var mockData = [];
                                var parentNodes = [];
                                mockData.map(function (item) {
                                    var s = item.unitPriceCode;
                                    if (s in parentNodes)
                                        parentNodes[s] = item;
                                    else
                                        parentNodes[s] = item;
                                });
                                parentNodes.map(function (item) { new model.UnitPriceHistoryNode(item.id, item.unitPriceCode, item.unitPriceName, item.monthRange, false); });
                                var childNodes = mockData.map(function (item) { return new model.UnitPriceHistoryNode(item.id, item.unitPriceCode, item.unitPriceName, item.monthRange, true); });
                                console.log(mockData);
                                console.log(parentNodes);
                                console.log(childNodes);
                                var merged = childNodes.concat(parentNodes);
                                console.log(merged);
                            }
                            function getUnitPriceHistoryList() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.getUnitPriceHistoryList)
                                    .done(function (res) {
                                    dfd.resolve(null);
                                    var unitPriceHistoryList = res;
                                    console.log(unitPriceHistoryList);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getUnitPriceHistoryList = getUnitPriceHistoryList;
                            function create(unitPriceHistory) {
                                var data = { unitPriceHistory: unitPriceHistory };
                                return null;
                            }
                            service.create = create;
                            function update(unitPriceHistory) {
                                var data = { unitPriceHistory: unitPriceHistory };
                                return null;
                            }
                            service.update = update;
                            function remove(id) {
                                return null;
                            }
                            service.remove = remove;
                            /**
                            * Model namespace.
                            */
                            var model;
                            (function (model) {
                                var UnitPriceHistoryDto = (function () {
                                    function UnitPriceHistoryDto(id, unitPriceCode, unitPriceName, startMonth, endMonth, budget, fixPaySettingType, fixPayAtr, fixPayAtrMonthly, fixPayAtrDayMonth, fixPayAtrDaily, fixPayAtrHourly, memo) {
                                        this.id = id;
                                        this.unitPriceCode = unitPriceCode;
                                        this.unitPriceName = unitPriceName;
                                        this.startMonth = startMonth;
                                        this.endMonth = endMonth;
                                        this.budget = budget;
                                        this.fixPaySettingType = fixPaySettingType;
                                        this.fixPayAtr = fixPayAtr;
                                        this.fixPayAtrMonthly = fixPayAtrMonthly;
                                        this.fixPayAtrDayMonth = fixPayAtrDayMonth;
                                        this.fixPayAtrDaily = fixPayAtrDaily;
                                        this.fixPayAtrHourly = fixPayAtrHourly;
                                    }
                                    return UnitPriceHistoryDto;
                                }());
                                model.UnitPriceHistoryDto = UnitPriceHistoryDto;
                                var UnitPriceHistoryNode = (function () {
                                    function UnitPriceHistoryNode(id, code, name, monthRange, isChild, childs) {
                                        var self = this;
                                        self.id = id;
                                        self.code = code;
                                        self.name = name;
                                        self.monthRange = monthRange;
                                        self.isChild = isChild;
                                        self.nodeText = self.code + ' ' + self.name;
                                        self.childs = childs;
                                        if (self.isChild == true) {
                                            self.nodeText = self.monthRange.startMonth + self.monthRange.endMonth;
                                        }
                                    }
                                    return UnitPriceHistoryNode;
                                }());
                                model.UnitPriceHistoryNode = UnitPriceHistoryNode;
                                var MonthRange = (function () {
                                    function MonthRange(startMonth, endMonth) {
                                        this.startMonth = startMonth;
                                        this.endMonth = endMonth;
                                    }
                                    return MonthRange;
                                }());
                                model.MonthRange = MonthRange;
                                (function (SettingType) {
                                    SettingType[SettingType["Company"] = 0] = "Company";
                                    SettingType[SettingType["Contract"] = 1] = "Contract";
                                })(model.SettingType || (model.SettingType = {}));
                                var SettingType = model.SettingType;
                                (function (ApplySetting) {
                                    ApplySetting[ApplySetting["Apply"] = 1] = "Apply";
                                    ApplySetting[ApplySetting["NotApply"] = 0] = "NotApply";
                                })(model.ApplySetting || (model.ApplySetting = {}));
                                var ApplySetting = model.ApplySetting;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
