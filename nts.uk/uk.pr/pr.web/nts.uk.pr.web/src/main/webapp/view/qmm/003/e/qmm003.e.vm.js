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
                    this.year = ko.observable(null);
                    this.yearJapan = ko.observable("");
                    this.yes = true;
                    var self = this;
                    self.init();
                    self.year.subscribe(function (value) {
                        self.yearJapan("(" + nts.uk.time.yearInJapanEmpire(self.year()).toString() + ")");
                    });
                }
                ScreenModel.prototype.clickButton = function () {
                    var self = this;
                    self.yes = true;
                    nts.uk.ui.windows.setShared('yes', self.yes, true);
                    if (self.resiTaxCodeLeft() && self.resiTaxCodeRight() && self.year()) {
                        e.service.updateAllReportCode(self.resiTaxCodeLeft(), self.resiTaxCodeRight(), self.year()).done(function (data) {
                            nts.uk.ui.windows.close();
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert(res.message);
                        });
                    }
                    else {
                        if (nts.uk.text.isNullOrEmpty(self.year())) {
                            //error 07, 01
                            nts.uk.ui.dialog.alert("対象年度  が入力されていません。");
                        }
                        else if (self.resiTaxCodeLeft().length === 0 || nts.uk.text.isNullOrEmpty(self.resiTaxCodeRight())) {
                            //error 07
                            nts.uk.ui.dialog.alert("住民税納付先コード が選択されていません。");
                        }
                    }
                };
                ScreenModel.prototype.cancelButton = function () {
                    this.yes = false;
                    nts.uk.ui.windows.setShared('yes', this.yes, true);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.init = function () {
                    var self = this;
                    self.treeLeft = ko.observableArray([]);
                    self.treeRight = ko.observableArray([]);
                    self.resiTaxCodeLeft = ko.observableArray([]);
                    self.resiTaxCodeRight = ko.observable("");
                };
                //11.初期データ取得処理 11. Initial data acquisition processing
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    (qmm003.e.service.getResidentialTax()).done(function (data) {
                        if (data.length > 0) {
                            self.residentalTaxList(data);
                            (qmm003.e.service.getRegionPrefecture()).done(function (locationData) {
                                self.japanLocation = locationData;
                                self.itemPrefecture(self.precfecture);
                                self.buildResidentalTaxTree();
                                var node = [];
                                node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                                self.filteredData(node);
                                self.treeLeft(self.nodeRegionPrefectures());
                                self.treeRight(self.nodeRegionPrefectures());
                            });
                        }
                        else {
                            nts.uk.ui.dialog.alert("対象データがありません。");
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
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
                                                    objChild.childs.push(new ResidentialTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, []));
                                                    isPrefecture = true;
                                                }
                                            });
                                            if (isPrefecture === false) {
                                                obj.childs.push(new ResidentialTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new ResidentialTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])]));
                                            }
                                            isChild = true;
                                        }
                                    });
                                    if (isChild === false) {
                                        var chi = [];
                                        self.nodeRegionPrefectures.push(new ResidentialTaxNode(objRegion.regionCode, objRegion.regionName, [new ResidentialTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new ResidentialTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                                    }
                                }
                            });
                        });
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ResidentialTaxNode = (function () {
                function ResidentialTaxNode(code, name, childs) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.childs = childs;
                }
                return ResidentialTaxNode;
            }());
            viewmodel.ResidentialTaxNode = ResidentialTaxNode;
        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
    })(e = qmm003.e || (qmm003.e = {}));
})(qmm003 || (qmm003 = {}));
;
//# sourceMappingURL=qmm003.e.vm.js.map