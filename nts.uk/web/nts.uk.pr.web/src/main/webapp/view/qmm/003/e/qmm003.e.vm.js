var qmm003;
(function (qmm003) {
    var e;
    (function (e) {
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
                    this.selectedCode = ko.observable("");
                    var self = this;
                    self.init();
                    self.singleSelectedCode.subscribe(function (newValue) {
                        self.currentNode(self.findByCode(self.filteredData(), newValue));
                        console.log(self.currentNode());
                        self.findPrefectureByResiTax(newValue);
                        console.log(self.selectedCode());
                    });
                }
                ScreenModel.prototype.findByCode = function (items, newValue) {
                    var self = this;
                    var _node;
                    _.find(items, function (_obj) {
                        if (!_node) {
                            if (_obj.code == newValue) {
                                _node = _obj;
                            }
                        }
                    });
                    return _node;
                };
                ;
                ScreenModel.prototype.clickButton = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('singleSelectedCode', self.singleSelectedCode(), true);
                    nts.uk.ui.windows.setShared('selectedCode', self.selectedCode(), true);
                    nts.uk.ui.windows.setShared('currentNode', self.currentNode(), true);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.cancelButton = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.item1s = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable("");
                    self.singleSelectedCode1 = ko.observable("");
                    self.currentNode = ko.observable((new Node("", "", [])));
                };
                //11.初期データ取得処理 11. Initial data acquisition processing
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    e.service.getResidentalTaxListByReportCode('029999').done(function (data) {
                        console.log(data);
                    });
                    (qmm003.e.service.getResidentialTax()).done(function (data) {
                        if (data.length > 0) {
                            self.residentalTaxList(data);
                            (qmm003.e.service.getRegionPrefecture()).done(function (locationData) {
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
                            var cout = false;
                            var coutPre = false;
                            _.each(objRegion.prefectures, function (objPrefecture) {
                                if (objPrefecture.prefectureCode === objResi.prefectureCode) {
                                    _.each(self.nodeRegionPrefectures(), function (obj) {
                                        if (obj.code === objRegion.regionCode) {
                                            _.each(obj.childs, function (objChild) {
                                                if (objChild.code === objPrefecture.prefectureCode) {
                                                    objChild.childs.push(new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, []));
                                                    coutPre = true;
                                                }
                                            });
                                            if (coutPre === false) {
                                                obj.childs.push(new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])]));
                                            }
                                            cout = true;
                                        }
                                    });
                                    if (cout === false) {
                                        var chi = [];
                                        self.nodeRegionPrefectures.push(new Node(objRegion.regionCode, objRegion.regionName, [new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                                    }
                                }
                            });
                        });
                    });
                };
                ScreenModel.prototype.findPrefectureByResiTax = function (code) {
                    var self = this;
                    var node;
                    _.each(self.items(), function (objRegion) {
                        _.each(objRegion.childs, function (objPrefecture) {
                            _.each(objPrefecture.childs, function (obj) {
                                if (obj.code === code) {
                                    self.selectedCode(objPrefecture.code);
                                }
                            });
                        });
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Node = (function () {
                function Node(code, name, childs) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.childs = childs;
                }
                return Node;
            }());
            viewmodel.Node = Node;
        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
    })(e = qmm003.e || (qmm003.e = {}));
})(qmm003 || (qmm003 = {}));
;
//# sourceMappingURL=qmm003.e.vm.js.map