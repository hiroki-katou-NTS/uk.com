//module nts.uk.pr.view.qmm003.e {
//    export class ScreenModel {
//        index: number;
//        items: any;
//        singleSelectedCode: KnockoutObservable<any>;
//        headers: any;
//        test: any;
//        curentNode: any;
//        editMode: boolean = true; // true là mode thêm mới, false là mode sửa 
//        filteredData: any;
//        filteredData1: any;
//        selectedCodes: any;
//        constructor() {
//            let self = this;
//            self.items = ko.observableArray(
//                [
//                    new Node('1', '東北', [
//                        new Node('11', '青森県', [
//                            new Node('022012', '青森市', []),
//                            new Node('052019', '秋田市', [])
//                        ]),
//                        new Node('12', '東北', [
//                            new Node('062014', '山形市', [])
//                        ]),
//                        new Node('13', '福島県', [
//                            new Node('062015', '福島市', [])
//                        ])
//                    ]),
//                    new Node('2', '北海道', []),
//                    new Node('3', '東海', []),
//                    new Node('4', '関東', [
//                        new Node('41', '茨城県', [
//                            new Node('062016', '水戸市', []),
//                        ]),
//                        new Node('42', '栃木県', [
//                            new Node('062017', '宇都宮市', [])
//                        ]),
//                        new Node('43', '埼玉県', [
//                            new Node('062019', '川越市', []),
//                            new Node('062020', '熊谷市', []),
//                            new Node('062022', '浦和市', []),
//                        ])
//                    ]),
//                    new Node('5', '東海', [])
//                ]);
//
//            self.singleSelectedCode = ko.observable("11");
//            self.curentNode = ko.observable(new Node("", "", []));
//            self.index = 0;
//            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
//            self.filteredData1 = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
//            self.selectedCodes = ko.observableArray([]);
//            //Init();
//            self.singleSelectedCode.subscribe(function(newValue) {
//            });
//
//        }
//        clickButton(): any {
//            nts.uk.ui.windows.close();
//
//        };
//        cancelButton(): void {
//            nts.uk.ui.windows.close();
//        };
//        register(): void {
//
//            let inputSearch1 = $("#E_SCH_001").find("input.ntsSearchBox").val();
//            let inputSearch2 = $("#E_SCH_002").find("input.ntsSearchBox").val();
//            let error: boolean;
//            let error1: boolean;
//            _.find(this.filteredData(), function(obj: Node) {
//                if (obj.code !== inputSearch1) {
//                    error = true;
//                }
//            })
//            _.find(this.filteredData1(), function(obj: Node) {
//                if (obj.code !== inputSearch2) {
//                    error1 = true;
//                }
//            })
//            //09.住民税納付先の統合_検索時エラーチェック処理  E_SCH_0002 9. Integration of inhabitant tax payment destination _ Error check processing at search time 
//            if (inputSearch1 === "") {
//                $('#E_SCH_001').ntsError('set', 'inputSearch E_INP_001 が入力されていません。');
//            } else {
//                $('#E_SCH_001').ntsError('clear');
//            }
//            if (error === true) {
//                $('#E_SCH_001').ntsError('set', 'inputSearch 対象データがありません。');
//            } else {
//                $('#E_SCH_001').ntsError('clear');
//            }
//            //10.住民税納付先の統合_検索時エラーチェック処理 E_SCH_0002  10. Integration of inhabitant tax payment destination _ Error check processing at search time 
//            if (inputSearch2 === "") {
//                $('#E_SCH_002').ntsError('set', 'inputSearch E_INP_002 が入力されていません。');
//            } else {
//                $('#E_SCH_002').ntsError('clear');
//            }
//            if (error1 === true) {
//                $('#E_SCH_002').ntsError('set', 'inputSearch E_INP_002 対象データがありません。');
//            } else {
//                $('#E_SCH_002').ntsError('clear');
//            }
//
//
//        }
//    }
//    export class Node {
//        code: string;
//        name: string;
//        nodeText: string;
//        custom: string;
//        childs: any;
//        constructor(code: string, name: string, childs: Array<Node>) {
//            let self = this;
//            self.code = code;
//            self.name = name;
//            self.nodeText = self.code + ' ' + self.name;
//            self.childs = childs;
//
//        }
//    }
//}
var qmm003;
(function (qmm003) {
    var e;
    (function (e) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.filteredData = ko.observableArray([]);
                    this.testNode = [];
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
                    self.singleSelectedCode = ko.observable(nts.uk.ui.windows.getShared("singleSelectedCode"));
                    self.currentNode = ko.observable((new Node("", "", [])));
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
