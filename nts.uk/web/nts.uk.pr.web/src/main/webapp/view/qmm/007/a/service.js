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
                            var UnitPriceHistoryNode = (function () {
                                function UnitPriceHistoryNode(code, name, monthRange, isChild, childs) {
                                    var self = this;
                                    self.code = code;
                                    self.name = name;
                                    self.monthRange = monthRange;
                                    self.nodeText = self.code + ' ' + self.name;
                                    self.isChild = isChild;
                                    self.childs = childs;
                                    if (self.isChild == true) {
                                        self.nodeText = self.monthRange;
                                    }
                                }
                                return UnitPriceHistoryNode;
                            }());
                            service.UnitPriceHistoryNode = UnitPriceHistoryNode;
                            var paths = {};
                            var mockData = [new UnitPriceHistoryNode('001', 'ガソリン単価', '2016/04 ~ 9999/12', false, [new UnitPriceHistoryNode('0011', 'ガソリン単価', '2016/04 ~ 9999/12', true), new UnitPriceHistoryNode('0012', 'ガソリン単価', '2015/04 ~ 2016/03', true)]),
                                new UnitPriceHistoryNode('002', '宿直単価', '2016/04 ~ 9999/12', false, [new UnitPriceHistoryNode('0021', '宿直単価', '2016/04 ~ 9999/12', true), new UnitPriceHistoryNode('0022', '宿直単価', '2015/04 ~ 2016/03', true)])];
                            function getUnitPriceHistoryList() {
                                var dfd = $.Deferred();
                                dfd.resolve(mockData);
                                return dfd.promise();
                            }
                            service.getUnitPriceHistoryList = getUnitPriceHistoryList;
                            /**
                            * Model namespace.
                            */
                            var model;
                            (function (model) {
                                var UnitPriceDto = (function () {
                                    function UnitPriceDto() {
                                    }
                                    return UnitPriceDto;
                                }());
                                model.UnitPriceDto = UnitPriceDto;
                                var UnitPriceHistoryDto = (function () {
                                    function UnitPriceHistoryDto() {
                                    }
                                    return UnitPriceHistoryDto;
                                }());
                                model.UnitPriceHistoryDto = UnitPriceHistoryDto;
                                var DateTimeDto = (function () {
                                    function DateTimeDto() {
                                    }
                                    return DateTimeDto;
                                }());
                                model.DateTimeDto = DateTimeDto;
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
