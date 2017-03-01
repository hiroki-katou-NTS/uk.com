var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.editMode = true; // true là mode thêm mới, false là mode sửa 
                    this.testNode = [];
                    this.test = ko.observableArray([]);
                    this.count = 0;
                    this.japanLocation = [];
                    this.residentalTaxList = ko.observableArray([]);
                    var self = this;
                    self.currentNode = ko.observable(null);
                    self.init();
                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                    self.filteredData1 = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                    self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                    self.removeData(self.filteredData2());
                    self.selectedCodes = ko.observableArray([]);
                    self.singleSelectedCode.subscribe(function (newValue) {
                        console.log(newValue);
                        self.Value(newValue);
                        if (self.editMode) {
                            var count = 0;
                            self.curentNode(self.findByCode(self.filteredData2(), newValue, count));
                            self.nameBySelectedCode(self.findByName(self.filteredData2()));
                            self.selectedCode(self.nameBySelectedCode().code);
                            var co_1 = 0, co1_1 = 0;
                            _.each(self.filteredData2(), function (obj) {
                                if (obj.code != self.curentNode().code) {
                                    co_1++;
                                }
                                else {
                                    if (co_1 < ((_.size(self.filteredData2())) - 1)) {
                                        co1_1 = co_1 + 1;
                                    }
                                    else {
                                        co1_1 = co_1;
                                    }
                                }
                            });
                            self.labelSubsub(self.filteredData2()[co1_1]);
                            if (self.labelSubsub() == null) {
                                self.labelSubsub(new Node("11", "22", []));
                            }
                        }
                        else {
                            self.editMode = true;
                        }
                    });
                }
                ScreenModel.prototype.findByCode = function (items, newValue, count) {
                    var self = this;
                    var node;
                    _.find(items, function (obj) {
                        if (!node) {
                            if (obj.code == newValue) {
                                node = obj;
                                count = count + 1;
                            }
                        }
                    });
                    return node;
                };
                ;
                ScreenModel.prototype.findByName = function (items) {
                    var self = this;
                    var node;
                    _.find(items, function (obj) {
                        if (!node) {
                            if (obj.name == self.curentNode().name) {
                                node = obj;
                            }
                        }
                    });
                    return node;
                };
                ;
                ScreenModel.prototype.removeNodeByCode = function (items) {
                    var self = this;
                    _.remove(items, function (obj) {
                        if (obj.code == self.Value()) {
                            return obj.code == self.Value();
                        }
                        else {
                            return self.removeNodeByCode(obj.childs);
                        }
                    });
                };
                ;
                // remove data: return array of subsub tree
                ScreenModel.prototype.removeData = function (items) {
                    _.remove(items, function (obj) {
                        return _.size(obj.code) < 3;
                    });
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    self.removeNodeByCode(self.items());
                    self.item1s(self.items());
                    self.items([]);
                    self.items(self.item1s());
                };
                ScreenModel.prototype.Confirm = function () {
                    var self = this;
                    nts.uk.ui.dialog.confirm("Do you want to delete node \"?")
                        .ifYes(function () {
                        self.deleteData();
                    });
                };
                ScreenModel.prototype.resetData = function () {
                    var self = this;
                    self.editMode = false;
                    self.curentNode(new Node("", "", []));
                    self.singleSelectedCode("");
                    self.selectedCode("");
                    self.labelSubsub("");
                    //            self.items([]);
                    //            self.items(self.item1s());
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
                    self.currentCode = ko.observable(null);
                    self.item1s = ko.observable(new Node('', '', []));
                    self.isEnable = ko.observable(true);
                    self.isEditable = ko.observable(true);
                    self.nameBySelectedCode = ko.observable(null);
                    self.Value = ko.observable(null);
                    self.singleSelectedCode = ko.observable("022012");
                    self.curentNode = ko.observable(new Node('022012', '青森市', []));
                    self.labelSubsub = ko.observable(new Node('052019', '秋田市', []));
                    self.selectedCode = ko.observable("1");
                    //self.testNode = ko.observable([]);
                };
                ScreenModel.prototype.openBDialog = function () {
                    var self = this;
                    var singleSelectedCode;
                    var curentNode;
                    nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function () {
                        singleSelectedCode = nts.uk.ui.windows.getShared("singleSelectedCode");
                        curentNode = nts.uk.ui.windows.getShared("curentNode");
                        self.editMode = false;
                        self.singleSelectedCode(singleSelectedCode);
                        self.curentNode(curentNode);
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
