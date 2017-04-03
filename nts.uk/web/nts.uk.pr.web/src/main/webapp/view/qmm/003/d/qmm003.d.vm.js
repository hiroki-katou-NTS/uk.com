var qmm003;
(function (qmm003) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.filteredData = ko.observableArray([]);
                    this.testNode = [];
                    this.arrayNode = ko.observableArray([]);
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    var self = this;
                    self.init();
                    self.selectedCode.subscribe(function (newValue) {
                        self.arrayNode(newValue);
                        self.currentNode(self.findByCode(self.items(), self.arrayNode()[0]));
                        console.log(self.currentNode());
                    });
                }
                ScreenModel.prototype.findByCode = function (items, newValue) {
                    var self = this;
                    var node;
                    _.each(items, function (objRegion) {
                        _.each(objRegion.childs, function (objPrefecture) {
                            _.each(objPrefecture.childs, function (obj) {
                                if (obj.code === newValue) {
                                    node = obj;
                                }
                            });
                        });
                    });
                    return node;
                };
                ScreenModel.prototype.init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.selectedCode = ko.observableArray([]);
                    self.currentNode = ko.observable(new Node("", "", []));
                };
                ScreenModel.prototype.clickButton = function () {
                    var self = this;
                    var resiTaxCodes = [];
                    //            qmm003.d.service.getResidentialTaxByResiTaxCode(self.arrayNode()[0],self.arrayNode()[0]).done(function(data){
                    //                console.log(data);
                    //            
                    //            });
                    for (var i = 0; i < self.arrayNode().length; i++) {
                        resiTaxCodes.push(self.arrayNode()[i]);
                    }
                    console.log(resiTaxCodes);
                    qmm003.d.service.deleteResidential(resiTaxCodes).done(function (data) {
                        console.log(data);
                        self.items([]);
                        self.nodeRegionPrefectures([]);
                        //self.start();
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
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm003.d || (qmm003.d = {}));
})(qmm003 || (qmm003 = {}));
//# sourceMappingURL=qmm003.d.vm.js.map