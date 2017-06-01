var qmm003;
(function (qmm003) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.filteredData = ko.observableArray([]);
                    this.filteredData1 = ko.observableArray([]);
                    this.filteredData2 = ko.observableArray([]);
                    this.filteredData3 = ko.observableArray([]);
                    this.arrayNode = ko.observableArray([]);
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    this.currentResidential = ko.observable(null);
                    this.indexOfRoot = [];
                    this.indexOfPrefecture = [];
                    this.yes = null; // cancel or register
                    var self = this;
                    self.init();
                    self.selectedCode.subscribe(function (newValue) {
                        self.arrayNode(newValue);
                    });
                }
                ScreenModel.prototype.init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.selectedCode = ko.observableArray([]);
                };
                ScreenModel.prototype.clickButton = function () {
                    var self = this;
                    var resiTaxCodes = [];
                    var resiTax = [];
                    self.yes = true;
                    for (var i = 0; i < self.arrayNode().length; i++) {
                        resiTaxCodes.push(self.arrayNode()[i]);
                    }
                    nts.uk.ui.windows.setShared('yes', self.yes, true);
                    if (resiTaxCodes.length > 0) {
                        qmm003.d.service.deleteResidential(resiTaxCodes).done(function (data) {
                            self.items([]);
                            self.nodeRegionPrefectures([]);
                            nts.uk.ui.windows.close();
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert(res.message);
                        });
                    }
                    else {
                        nts.uk.ui.dialog.alert("住民税納付先コード が選択されていません。");
                    }
                };
                ScreenModel.prototype.cancelButton = function () {
                    this.yes = false;
                    nts.uk.ui.windows.setShared('items', this.items(), true);
                    nts.uk.ui.windows.setShared('yes', this.yes, true);
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
                                var node1 = [];
                                node1 = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                                var node2 = [];
                                node2 = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                                var node3 = [];
                                node3 = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                                self.filteredData(node);
                                self.filteredData1(node1);
                                self.filteredData2(node2);
                                self.filteredData3(node3);
                                self.items(self.nodeRegionPrefectures());
                            });
                        }
                        else {
                            nts.uk.ui.dialog.alert("対象データがありません。");
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