var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.editMode = true; // true là mode thêm mới, false là mode sửa 
                    this.filteredData = ko.observableArray([]);
                    this.testNode = [];
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    this.currentResidential = ko.observable(null);
                    var self = this;
                    self.init();
                    self.selectedCodes = ko.observableArray([]);
                    self.singleSelectedCode.subscribe(function (newChange) {
                        if (self.editMode) {
                            var currentNode = void 0;
                            currentNode = self.findByCode(self.filteredData(), newChange);
                            self.currentNode(ko.mapping.fromJS(currentNode));
                            self.findPrefectureByResiTax(newChange);
                            self.currentResi(self.findResidentialByCode(self.residentalTaxList(), newChange));
                            self.currentResidential(ko.mapping.fromJS(self.currentResi()));
                        }
                        else {
                            self.editMode = true;
                        }
                    });
                }
                ScreenModel.prototype.findByCode = function (items, newValue) {
                    var self = this;
                    var node;
                    _.find(items, function (obj) {
                        if (!node) {
                            if (obj.code == newValue) {
                                node = obj;
                                $(document).ready(function (data) {
                                    $("#A_INP_002").attr('disabled', 'true');
                                    $("#A_INP_002").attr('readonly', 'true');
                                });
                            }
                        }
                    });
                    return node;
                };
                ;
                ScreenModel.prototype.findResidentialByCode = function (items, newValue) {
                    var self = this;
                    var objResi;
                    _.find(items, function (obj) {
                        if (!objResi) {
                            if (obj.resiTaxCode == newValue) {
                                objResi = obj;
                            }
                        }
                    });
                    return objResi;
                };
                ;
                ScreenModel.prototype.findByName = function (items, name) {
                    var self = this;
                    var node;
                    _.find(items, function (obj) {
                        if (!node) {
                            if (obj.name === name) {
                                node = obj;
                            }
                        }
                    });
                    return node;
                };
                ScreenModel.prototype.findPrefectureByResiTax = function (code) {
                    var self = this;
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
                ScreenModel.prototype.buildPrefectureArray = function () {
                    var self = this;
                    _.map(self.japanLocation, function (region) {
                        _.each(region.prefectures, function (objPrefecture) {
                            return self.precfecture.push(new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, []));
                        });
                    });
                };
                ScreenModel.prototype.resetData = function () {
                    var self = this;
                    self.editMode = false;
                    self.currentNode(ko.mapping.fromJS(new Node('', '', [])));
                    self.singleSelectedCode("");
                    self.selectedCode("");
                };
                ScreenModel.prototype.search = function () {
                    var inputSearch = $("#search").find("input.ntsSearchBox").val();
                    if (inputSearch === '') {
                        $('#search').ntsError('set', 'inputSearch が入力されていません。');
                    }
                    else {
                        $('#search').ntsError('clear');
                    }
                    // errror search
                    var error;
                    _.find(this.filteredData(), function (obj) {
                        if (obj.code !== inputSearch) {
                            error = true;
                        }
                    });
                    if (error = true) {
                        $('#search').ntsError('set', '対象データがありません。');
                    }
                    else {
                        $('#search').ntsError('clear');
                    }
                };
                ScreenModel.prototype.init = function () {
                    var self = this;
                    // 11.初期データ取得処理 11. Initial data acquisition processing [住民税納付先マスタ.SEL-1] 
                    // data of treegrid
                    self.items = ko.observableArray([]);
                    self.mode = ko.observable(null);
                    self.currentNode = ko.observable(ko.mapping.fromJS(new Node("022012", "青森市", [])));
                    self.isEnable = ko.observable(true);
                    self.isEditable = ko.observable(true);
                    self.selectedCode = ko.observable("11");
                    self.singleSelectedCode = ko.observable('022012');
                    var objResi = new a.service.model.ResidentialTax();
                    objResi.companyCode = '0000';
                    objResi.resiTaxCode = '062017';
                    objResi.resiTaxAutonomy = '宇都宮市';
                    objResi.prefectureCode = '42';
                    objResi.resiTaxReportCode = '062014';
                    objResi.registeredName = 'aaa';
                    objResi.companyAccountNo = 'b';
                    objResi.companySpecifiedNo = 'cccccc';
                    objResi.cordinatePostalCode = '11111';
                    objResi.cordinatePostOffice = 'bbbbb';
                    objResi.memo = 'sssssssssssssssss';
                    self.currentResi = ko.observable(objResi);
                };
                ScreenModel.prototype.openBDialog = function () {
                    var self = this;
                    var singleSelectedCode;
                    var currentNode;
                    nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function () {
                        singleSelectedCode = nts.uk.ui.windows.getShared("singleSelectedCode");
                        currentNode = nts.uk.ui.windows.getShared("currentNode");
                        self.editMode = false;
                        self.singleSelectedCode(singleSelectedCode);
                        self.currentNode(currentNode);
                    });
                };
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    var labelSubsub;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/c/index.xhtml", { title: '住民税納付先の登録＞住民税報告先一覧', dialogClass: "no-close" }).onClosed(function () {
                        labelSubsub = nts.uk.ui.windows.getShared('labelSubsub');
                        self.labelSubsub(labelSubsub);
                        console.log(labelSubsub);
                    });
                };
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    var items;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/d/index.xhtml", { title: '住民税納付先の登録　＞　一括削除', dialogClass: "no-close" }).onClosed(function () {
                        items = nts.uk.ui.windows.getShared('items');
                        self.items([]);
                        self.items(items);
                        console.log(items);
                        console.log(self.items());
                    });
                };
                ScreenModel.prototype.openEDialog = function () {
                    var self = this;
                    var labelSubsub;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/e/index.xhtml", { title: '住民税納付先の登録＞納付先の統合', dialogClass: "no-close" }).onClosed(function () {
                        labelSubsub = nts.uk.ui.windows.getShared('labelSubsub');
                        self.labelSubsub(labelSubsub);
                        console.log(labelSubsub);
                    });
                };
                //11.初期データ取得処理 11. Initial data acquisition processing
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    (qmm003.a.service.getResidentialTax()).done(function (data) {
                        if (data.length > 0) {
                            self.residentalTaxList(data);
                            (qmm003.a.service.getRegionPrefecture()).done(function (locationData) {
                                self.japanLocation = locationData;
                                self.buildPrefectureArray();
                                self.itemPrefecture(self.precfecture);
                                console.log(self.itemPrefecture());
                                self.buildResidentalTaxTree();
                                var node = [];
                                node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                                self.filteredData(node);
                                self.items(self.nodeRegionPrefectures());
                            });
                            self.mode(true); // true, update mode 
                        }
                        else {
                            self.mode(false); // false, new mode
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm003.a || (qmm003.a = {}));
})(qmm003 || (qmm003 = {}));
;
