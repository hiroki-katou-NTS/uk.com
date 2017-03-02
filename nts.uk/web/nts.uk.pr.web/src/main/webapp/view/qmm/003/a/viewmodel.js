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
                    this.test = ko.observableArray([]);
                    this.count = 0;
                    this.japanLocation = [];
                    this.residentalTaxList = ko.observableArray([]);
                    var self = this;
                    self.init();
                    console.log(self.test());
                    console.log(self.items());
                    console.log(self.filteredData());
                    self.selectedCodes = ko.observableArray([]);
                    self.singleSelectedCode.subscribe(function (newChange) {
                        self.findByCode(self.filteredData(), newChange);
                        console.log(self.currentNode());
                    });
                }
                ScreenModel.prototype.findByCode = function (items, newValue) {
                    var self = this;
                    var node;
                    _.find(items, function (obj) {
                        if (!node) {
                            if (obj.code == newValue) {
                                node.code = obj.code;
                                node.name = obj.name;
                                node.childs = obj.childs;
                                self.currentNode = ko.mapping.fromJS(node);
                                console.log(self.currentNode());
                            }
                        }
                    });
                    return node;
                };
                ;
                ScreenModel.prototype.resetData = function () {
                    var self = this;
                    self.editMode = false;
                    self.currentNode(new Node("", "", []));
                    self.singleSelectedCode("");
                    self.selectedCode("");
                    self.labelSubsub("");
                };
                ScreenModel.prototype.search = function () {
                    var inputSearch = $("#search").find("input.ntsSearchBox").val();
                    if (inputSearch == "") {
                        $('#search').ntsError('set', 'inputSearch が入力されていません。');
                    }
                    else {
                        $('#search').ntsError('clear');
                    }
                    // errror search
                    var error;
                    _.find(this.filteredData2(), function (obj) {
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
                    self.itemList = ko.observableArray([
                        new Node('1', '青森市', []),
                        new Node('2', '秋田市', []),
                        new Node('3', '山形市', []),
                        new Node('4', '福島市', []),
                        new Node('5', '水戸市', []),
                        new Node('6', '宇都宮市', []),
                        new Node('7', '川越市', []),
                        new Node('8', '熊谷市', []),
                        new Node('9', '浦和市', [])
                    ]);
                    // data of treegrid
                    self.items = ko.observableArray([]);
                    self.mode = ko.observable(null);
                    var node = new Node("022012", "青森市", []);
                    self.currentNode = ko.observable(ko.mapping.fromJS(node));
                    self.isEnable = ko.observable(true);
                    self.isEditable = ko.observable(true);
                    self.selectedCode = ko.observable("1");
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
                        //             self.editMode = false;
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
                                self.buildResidentalTaxTree();
                                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.test(), "childs"));
                                self.items(self.test());
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
                                    _.each(self.test(), function (obj) {
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
                                        self.test.push(new Node(objRegion.regionCode, objRegion.regionName, [new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
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
