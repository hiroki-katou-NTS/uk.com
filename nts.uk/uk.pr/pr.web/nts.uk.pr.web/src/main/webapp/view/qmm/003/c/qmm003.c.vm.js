var qmm003;
(function (qmm003) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.filteredData = ko.observableArray([]);
                    this.currentNode = null;
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    this.yes = null;
                    var self = this;
                    self.init();
                    self.singleSelectedCode.subscribe(function (newValue) {
                        if (newValue.length > 2) {
                            self.currentNode = _.find(self.residentalTaxList(), function (obj) {
                                return obj.resiTaxCode = newValue;
                            });
                        }
                    });
                }
                ScreenModel.prototype.clickButton = function () {
                    var self = this;
                    self.yes = true;
                    nts.uk.ui.windows.setShared('yes', self.yes, true);
                    if (self.currentNode) {
                        nts.uk.ui.windows.setShared('currentNode', self.currentNode, true);
                        nts.uk.ui.windows.close();
                    }
                    else {
                        nts.uk.ui.dialog.alert("住民税納付先コード が選択されていません。");
                    }
                };
                ScreenModel.prototype.cancelButton = function () {
                    var self = this;
                    self.yes = false;
                    nts.uk.ui.windows.setShared('yes', self.yes, true);
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
                            console.log(data);
                            (qmm003.c.service.getRegionPrefecture()).done(function (locationData) {
                                self.japanLocation = locationData;
                                self.buildResidentalTaxTree();
                                var node = [];
                                node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                                self.filteredData(node);
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
                                                    objChild.childs.push(new ResidentalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, []));
                                                    isPrefecture = true;
                                                }
                                            });
                                            if (isPrefecture === false) {
                                                obj.childs.push(new ResidentalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new ResidentalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])]));
                                            }
                                            isChild = true;
                                        }
                                    });
                                    if (isChild === false) {
                                        var chi = [];
                                        self.nodeRegionPrefectures.push(new ResidentalTaxNode(objRegion.regionCode, objRegion.regionName, [new ResidentalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new ResidentalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                                    }
                                }
                            });
                        });
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ResidentalTaxNode = (function () {
                function ResidentalTaxNode(code, name, childs) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.childs = childs;
                }
                return ResidentalTaxNode;
            }());
            viewmodel.ResidentalTaxNode = ResidentalTaxNode;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm003.c || (qmm003.c = {}));
})(qmm003 || (qmm003 = {}));
;
//# sourceMappingURL=qmm003.c.vm.js.map