var qmm003;
(function (qmm003) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.filteredData = ko.observableArray([]);
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    this.currentNode = ko.observable(null);
                    var self = this;
                    self.init();
                    self.singleSelectedCode.subscribe(function (newValue) {
                        if (newValue.length > 2) {
                            self.currentNode(self.findByCode(self.residentalTaxList(), newValue));
                            console.log(self.currentNode());
                        }
                    });
                }
                ScreenModel.prototype.findByCode = function (items, newValue) {
                    var self = this;
                    var _node;
                    _.find(items, function (_obj) {
                        if (!_node) {
                            if (_obj.resiTaxCode == newValue) {
                                _node = _obj;
                            }
                        }
                    });
                    return _node;
                };
                ;
                ScreenModel.prototype.clickButton = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('currentNode', self.currentNode(), true);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.cancelButton = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable(nts.uk.ui.windows.getShared("singleSelectedCode"));
                };
                //11.初期データ取得処理 11. Initial data acquisition processing
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    (qmm003.b.service.getResidentialTax()).done(function (data) {
                        if (data.length > 0) {
                            self.residentalTaxList(data);
                            console.log(data);
                            (qmm003.b.service.getRegionPrefecture()).done(function (locationData) {
                                self.japanLocation = locationData;
                                self.itemPrefecture(self.precfecture);
                                console.log(self.itemPrefecture());
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
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qmm003.b || (qmm003.b = {}));
})(qmm003 || (qmm003 = {}));
;
//# sourceMappingURL=qmm003.b.vm.js.map