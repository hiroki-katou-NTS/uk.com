var qmm003;
(function (qmm003) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.filteredData = ko.observableArray([]);
                    this.currentResidential = (null);
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    var self = this;
                    self.init();
                    self.singleSelectedCode.subscribe(function (newValue) {
                        if (newValue.length === 1) {
                            var index = void 0;
                            index = _.findIndex(self.items(), function (obj) {
                                return obj.code === newValue;
                            });
                            self.singleSelectedCode(self.items()[index].childs[0].childs[0].code);
                            return;
                        }
                        if (newValue.length === 2) {
                            var array = [];
                            array = self.findIndex(self.items(), newValue);
                            self.singleSelectedCode(self.items()[array[0]].childs[array[1]].childs[0].code);
                            return;
                        }
                        if (newValue.length > 2) {
                            self.processWhenCurrentCodeChange(newValue);
                        }
                    });
                }
                ScreenModel.prototype.processWhenCurrentCodeChange = function (newValue) {
                    var self = this;
                    c.service.getResidentialTaxDetail(newValue).done(function (data) {
                        if (data) {
                            self.currentResidential = ((data));
                            ;
                        }
                        else {
                            return;
                        }
                    });
                };
                ScreenModel.prototype.clickButton = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('currentResidential', self.currentResidential, true);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.cancelButton = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable("");
                };
                //11.初期データ取得処理 11. Initial data acquisition processing
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    (qmm003.c.service.getResidentialTax()).done(function (data) {
                        if (data.length > 0) {
                            self.residentalTaxList(data);
                            (qmm003.c.service.getRegionPrefecture()).done(function (locationData) {
                                self.japanLocation = locationData;
                                self.buildResidentalTaxTree();
                                var node = [];
                                node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                                self.filteredData(node);
                                self.items(self.nodeRegionPrefectures());
                            });
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                // tìm index để khi chọn root thì ra hiển thị ra thằng đầu tiên của 1 thằng root
                ScreenModel.prototype.findIndex = function (items, newValue) {
                    var index;
                    var count = -1;
                    var array = [];
                    _.each(items, function (obj) {
                        count++;
                        index = _.findIndex(obj.childs, function (obj1) {
                            return obj1.code === newValue;
                        });
                        if (index > -1) {
                            array.push(count, index);
                        }
                    });
                    return array;
                };
                ScreenModel.prototype.buildResidentalTaxTree = function () {
                    var self = this;
                    var child = [];
                    var i = 0;
                    _.each(self.residentalTaxList(), function (objResi) {
                        _.each(self.japanLocation, function (objRegion) {
                            var isChild = false;
                            var isPrefecture = false;
                            _.each(objRegion.prefectures, function (objPrefecture) {
                                if (objPrefecture.prefectureCode === objResi.prefectureCode) {
                                    _.each(self.nodeRegionPrefectures(), function (obj) {
                                        if (obj.code === objRegion.regionCode) {
                                            _.each(obj.childs, function (objChild) {
                                                if (objChild.code === objPrefecture.prefectureCode) {
                                                    objChild.childs.push(new RedensitalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, []));
                                                    isPrefecture = true;
                                                }
                                            });
                                            if (isPrefecture === false) {
                                                obj.childs.push(new RedensitalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new RedensitalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])]));
                                            }
                                            isChild = true;
                                        }
                                    });
                                    if (isChild === false) {
                                        var chi = [];
                                        self.nodeRegionPrefectures.push(new RedensitalTaxNode(objRegion.regionCode, objRegion.regionName, [new RedensitalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new RedensitalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                                    }
                                }
                            });
                        });
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var RedensitalTaxNode = (function () {
                function RedensitalTaxNode(code, name, childs) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.childs = childs;
                }
                return RedensitalTaxNode;
            }());
            viewmodel.RedensitalTaxNode = RedensitalTaxNode;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm003.c || (qmm003.c = {}));
})(qmm003 || (qmm003 = {}));
;
//# sourceMappingURL=qmm003.c.vm.js.map