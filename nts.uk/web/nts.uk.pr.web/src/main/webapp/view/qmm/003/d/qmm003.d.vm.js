var qmm003;
(function (qmm003) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.filteredData = ko.observableArray([]);
                    this.arrayNode = ko.observableArray([]);
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    this.currentResidential = ko.observable(null);
                    this.indexOfRoot = [];
                    this.indexOfPrefecture = [];
                    var self = this;
                    self.init();
                    self.selectedCode.subscribe(function (newValue) {
                        self.arrayNode(newValue);
                    });
                }
                // tìm index để khi chọn root thì ra hiển thị ra thằng đầu tiên của 1 thằng root
                ScreenModel.prototype.findIndex = function (items, newValue) {
                    var indexOfValue1;
                    if (newValue.length === 1) {
                        indexOfValue1 = _.findIndex(items, function (obj) {
                            return obj.code === newValue;
                        });
                        this.indexOfRoot.push(indexOfValue1);
                    }
                    else if (newValue.length === 2) {
                        var index_1;
                        var count_1 = -1;
                        var array_1 = [];
                        _.each(items, function (obj) {
                            count_1++;
                            index_1 = _.findIndex(obj.childs, function (obj1) {
                                return obj1.code === newValue;
                            });
                            if (index_1 > -1) {
                                array_1.push(count_1, index_1);
                            }
                        });
                        this.indexOfPrefecture = array_1;
                        return array_1;
                    }
                };
                ScreenModel.prototype.init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.selectedCode = ko.observableArray([]);
                };
                ScreenModel.prototype.clickButton = function () {
                    var self = this;
                    var resiTaxCodes = [];
                    var resiTax = [];
                    console.log(self.arrayNode());
                    for (var i = 0; i < self.arrayNode().length; i++) {
                        resiTaxCodes.push(self.arrayNode()[i]);
                        this.findIndex(this.items(), self.arrayNode()[i]);
                    }
                    console.log(this.indexOfRoot);
                    for (var i = 0; i < (self.indexOfRoot.length); i++) {
                        _.each(self.items()[i], function (obj) {
                            console.log(obj.childs);
                            _.each(obj.childs, function (obj1) {
                                console.log(obj1);
                            });
                        });
                    }
                    console.log(this.indexOfPrefecture);
                    console.log(resiTaxCodes);
                    qmm003.d.service.deleteResidential(resiTaxCodes).done(function (data) {
                        console.log(data);
                        self.items([]);
                        self.nodeRegionPrefectures([]);
                    });
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.cancelButton = function () {
                    nts.uk.ui.windows.close();
                };
                //11.初期データ取得処理 11. Initial data acquisition processing
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    (qmm003.d.service.getResidentialTax()).done(function (data) {
                        if (data.length > 0) {
                            self.residentalTaxList(data);
                            (qmm003.d.service.getRegionPrefecture()).done(function (locationData) {
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
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm003.d || (qmm003.d = {}));
})(qmm003 || (qmm003 = {}));
//# sourceMappingURL=qmm003.d.vm.js.map