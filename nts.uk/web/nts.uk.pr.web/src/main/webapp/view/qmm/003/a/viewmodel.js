var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.firstSelectedCode = ko.observable("");
                    this.editMode = true; // true là mode thêm mới, false là mode sửa 
                    this.filteredData = ko.observableArray([]);
                    this.testNode = [];
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    this.residentialReportCode = ko.observable("");
                    var self = this;
                    self.init();
                    //self.selectedCodes = ko.observableArray([]);
                    self.singleSelectedCode.subscribe(function (newChange) {
                        if (self.editMode) {
                            var currentNode = void 0;
                            currentNode = self.findByCode(self.filteredData(), newChange);
                            self.currentNode(ko.mapping.fromJS(currentNode));
                            self.findPrefectureByResiTax(newChange);
                            self.currentResi(ko.mapping.fromJS(self.findResidentialByCode(self.residentalTaxList(), newChange)));
                            self.residentialReportCode(self.currentResi().resiTaxReportCode);
                            self.currentResidential(ko.mapping.fromJS(self.currentResi()));
                            console.log(self.currentResidential());
                            console.log(self.currentResi());
                        }
                        else {
                            self.editMode = true;
                        }
                    });
                    self.selectedCode.subscribe(function (newChange) {
                        console.log(newChange);
                    });
                }
                // find Node By code (singleSelectedCode)
                ScreenModel.prototype.findByCode = function (items, newValue) {
                    var self = this;
                    var node;
                    _.find(items, function (obj) {
                        if (!node) {
                            if (obj.code == newValue) {
                                node = obj;
                                $(document).ready(function (data) {
                                    $("#A_INP_002").attr('disabled', 'true');
                                });
                            }
                        }
                    });
                    return node;
                };
                ;
                // find  Node by resiTaxCode
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
                // find Node by name
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
                //  set selectedcode by prefectureCode
                ScreenModel.prototype.findPrefectureByResiTax = function (code) {
                    var self = this;
                    _.each(self.items(), function (objRegion) {
                        _.each(objRegion.childs, function (objPrefecture) {
                            _.each(objPrefecture.childs, function (obj) {
                                if (obj.code === code) {
                                    self.selectedCode(objPrefecture.code);
                                    console.log(self.selectedCode());
                                }
                            });
                        });
                    });
                };
                // create array prefecture from japan location
                ScreenModel.prototype.buildPrefectureArray = function () {
                    var self = this;
                    //(qmm003.b.service.getResidentialTax()).done(function(data: Array<qmm003.b.service.model.ResidentialTax>) {
                    _.map(self.japanLocation, function (region) {
                        _.each(region.prefectures, function (objPrefecture) {
                            return self.precfecture.push(new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, []));
                        });
                    });
                    //  });
                };
                // reset Data
                ScreenModel.prototype.resetData = function () {
                    var self = this;
                    self.editMode = false;
                    self.enableBTN007(true);
                    self.currentNode(ko.mapping.fromJS(new Node('', '', [])));
                    var node = new a.service.model.ResidentialTax();
                    node.companyCode = '';
                    node.resiTaxCode = '';
                    node.resiTaxAutonomy = '';
                    node.prefectureCode = '';
                    node.resiTaxReportCode = '';
                    node.registeredName = '';
                    node.companyAccountNo = '';
                    node.companySpecifiedNo = '';
                    node.cordinatePostalCode = '';
                    node.cordinatePostOffice = '';
                    node.memo = '';
                    self.currentResi(node);
                    self.currentResidential(ko.mapping.fromJS(self.currentResi()));
                    self.singleSelectedCode("");
                    self.selectedCode("");
                };
                // init menu
                ScreenModel.prototype.init = function () {
                    var self = this;
                    // data of treegrid
                    self.items = ko.observableArray([]);
                    self.mode = ko.observable(null);
                    self.currentNode = ko.observable(ko.mapping.fromJS(new Node('022012', '青森市', [])));
                    self.isEnable = ko.observable(true);
                    //self.enableINP002 = ko.observable(null);
                    self.enableBTN007 = ko.observable(null);
                    self.enableBTN009 = ko.observable(null);
                    self.isEditable = ko.observable(true);
                    self.singleSelectedCode = ko.observable("");
                    self.selectedCode = ko.observable("11");
                    var objResi = new a.service.model.ResidentialTax();
                    objResi.companyCode = 'a';
                    objResi.resiTaxCode = 'a';
                    objResi.resiTaxAutonomy = 'a';
                    objResi.prefectureCode = '42';
                    objResi.resiTaxReportCode = '062014';
                    objResi.registeredName = 'aaa';
                    objResi.companyAccountNo = 'b';
                    objResi.companySpecifiedNo = 'cccccc';
                    objResi.cordinatePostalCode = '11111';
                    objResi.cordinatePostOffice = 'bbbbb';
                    objResi.memo = 'sssssssssssssssss';
                    self.currentResi = ko.observable((objResi));
                    self.currentResidential = ko.observable(ko.mapping.fromJS(self.currentResi()));
                };
                //BTN007
                ScreenModel.prototype.openBDialog = function () {
                    var self = this;
                    var singleSelectedCode;
                    var currentNode;
                    var selectedCode;
                    nts.uk.ui.windows.setShared('singleSelectedCode', self.singleSelectedCode(), true);
                    nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function () {
                        singleSelectedCode = nts.uk.ui.windows.getShared("singleSelectedCode");
                        currentNode = nts.uk.ui.windows.getShared("currentNode");
                        selectedCode = nts.uk.ui.windows.getShared("selectedCode");
                        console.log(currentNode);
                        self.editMode = false;
                        self.singleSelectedCode(singleSelectedCode);
                        self.currentNode(ko.mapping.fromJS(currentNode));
                        self.selectedCode(selectedCode);
                    });
                };
                //BTN009
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    var currentNode;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/c/index.xhtml", { title: '住民税納付先の登録＞住民税報告先一覧', dialogClass: "no-close" }).onClosed(function () {
                        currentNode = nts.uk.ui.windows.getShared('currentNode');
                        self.residentialReportCode(currentNode.nodeText);
                    });
                };
                //BTN006
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    var items;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/d/index.xhtml", { title: '住民税納付先の登録　＞　一括削除', dialogClass: "no-close" }).onClosed(function () {
                        items = nts.uk.ui.windows.getShared('items');
                        console.log(items);
                        self.items([]);
                        self.nodeRegionPrefectures([]);
                        self.start(undefined);
                    });
                };
                //BTN005
                ScreenModel.prototype.deleteAResidential = function () {
                    var self = this;
                    var objResidential;
                    //objResidential = ko.toJS
                    objResidential = (ko.toJS(self.currentResidential()));
                    var resiTaxCodes = [];
                    resiTaxCodes.push(objResidential.resiTaxCode);
                    qmm003.a.service.deleteResidential(resiTaxCodes).done(function (data) {
                        console.log(data);
                        self.items([]);
                        self.nodeRegionPrefectures([]);
                        self.start(undefined);
                    });
                };
                ScreenModel.prototype.openEDialog = function () {
                    var self = this;
                    var labelSubsub;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/e/index.xhtml", { title: '住民税納付先の登録＞納付先の統合', dialogClass: "no-close" }).onClosed(function () {
                        labelSubsub = nts.uk.ui.windows.getShared('labelSubsub');
                    });
                };
                //11.初期データ取得処理 11. Initial data acquisition processing
                ScreenModel.prototype.start = function (currentSelectedCode) {
                    var dfd = $.Deferred();
                    var self = this;
                    (qmm003.a.service.getResidentialTax()).done(function (data) {
                        if (data.length > 0) {
                            self.mode(true); // true, update mode 
                            self.residentalTaxList(data);
                            (qmm003.a.service.getRegionPrefecture()).done(function (locationData) {
                                self.japanLocation = locationData;
                                self.buildPrefectureArray();
                                self.itemPrefecture(self.precfecture);
                                self.buildResidentalTaxTree();
                                var node = [];
                                node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                                self.filteredData(node);
                                self.items(self.nodeRegionPrefectures());
                                if (currentSelectedCode === undefined) {
                                    self.singleSelectedCode(self.firstSelectedCode());
                                }
                                else {
                                    self.singleSelectedCode(currentSelectedCode);
                                }
                                self.enableBTN007(false);
                                self.enableBTN009(true);
                            });
                        }
                        else {
                            self.resetData();
                            (qmm003.a.service.getRegionPrefecture()).done(function (locationData) {
                                self.japanLocation = locationData;
                                self.buildPrefectureArray();
                                self.itemPrefecture(self.precfecture);
                            });
                            self.mode(false); // false, new mode
                            self.enableBTN007(true);
                            self.enableBTN009(false);
                            console.log(self.mode());
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
                    var node;
                    node = self.nodeRegionPrefectures()[0].childs;
                    var node1;
                    node1 = (node[0].childs);
                    console.log(node1[0]);
                    self.firstSelectedCode(node1[0].code);
                };
                ScreenModel.prototype.ClickRegister = function () {
                    var self = this;
                    var objResi = new a.service.model.ResidentialTax();
                    var node = new Node('', '', []);
                    node = ko.toJS(self.currentNode());
                    objResi = ko.toJS(self.currentResidential());
                    objResi.resiTaxCode = node.code;
                    objResi.resiTaxAutonomy = node.name;
                    objResi.prefectureCode = self.selectedCode();
                    objResi.resiTaxReportCode = self.residentialReportCode().substring(0, 6);
                    if (!self.mode()) {
                        qmm003.a.service.addResidential(objResi).done(function () {
                            self.items([]);
                            self.nodeRegionPrefectures([]);
                            self.start(objResi.resiTaxCode);
                            // self.singleSelectedCode(objResi.resiTaxCode);
                        });
                    }
                    else {
                        qmm003.a.service.updateData(objResi).done(function () {
                            self.items([]);
                            self.nodeRegionPrefectures([]);
                            self.start(objResi.resiTaxCode);
                            // self.singleSelectedCode(objResi.resiTaxCode);
                        });
                    }
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
