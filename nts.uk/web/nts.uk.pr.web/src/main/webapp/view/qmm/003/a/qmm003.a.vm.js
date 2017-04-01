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
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    this.residentialReportCode = ko.observable("");
                    this.previousCurrentCode = null; //lưu giá trị của currentCode trước khi nó bị thay đổi
                    this.hasFocus = ko.observable(true);
                    var self = this;
                    self.init();
                    self.singleSelectedCode.subscribe(function (newValue) {
                        if (!nts.uk.text.isNullOrEmpty(newValue) && (self.singleSelectedCode() !== self.previousCurrentCode) && self.editMode) {
                            if (self.dirtyObject.isDirty()) {
                                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                                    self.processWhenCurrentCodeChange(newValue);
                                }).ifCancel(function () {
                                    self.items([]);
                                    self.nodeRegionPrefectures([]);
                                    self.start(self.previousCurrentCode);
                                });
                            }
                            else {
                                self.processWhenCurrentCodeChange(newValue);
                            }
                        }
                        else {
                            self.editMode = true;
                        }
                    });
                }
                ScreenModel.prototype.processWhenCurrentCodeChange = function (newValue) {
                    var self = this;
                    if (!self.enableBTN009()) {
                        self.enableBTN009(true);
                    }
                    var currentNode;
                    if ($('.nts-editor').ntsError("hasError")) {
                        $('.save-error').ntsError('clear');
                    }
                    $(document).ready(function (data) {
                        $("#A_INP_002").attr('disabled', 'true');
                    });
                    a.service.getResidentialTaxDetail(newValue).done(function (data) {
                        console.log(data);
                    });
                    currentNode = self.findByCode(self.filteredData(), newValue);
                    if (currentNode) {
                        self.currentNode(ko.mapping.fromJS(currentNode));
                        self.findPrefectureByResiTax(newValue);
                        self.currentResi(ko.mapping.fromJS(self.findResidentialByCode(self.residentalTaxList(), newValue)));
                        self.residentialReportCode(self.currentResi().resiTaxReportCode);
                        self.currentResidential(ko.mapping.fromJS(self.currentResi()));
                        self.previousCurrentCode = newValue;
                        self.dirtyObject.reset();
                    }
                    else {
                        self.resetData();
                    }
                };
                // find Node By code (singleSelectedCode)
                ScreenModel.prototype.findByCode = function (items, newValue) {
                    var self = this;
                    var node;
                    node = _.find(items, function (obj) {
                        return obj.code == newValue;
                    });
                    return node;
                };
                ;
                // find  Node by resiTaxCode
                ScreenModel.prototype.findResidentialByCode = function (items, newValue) {
                    var self = this;
                    var objResi;
                    objResi = _.find(items, function (obj) {
                        return (obj.resiTaxCode == newValue);
                    });
                    return objResi;
                };
                ;
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
                    _.map(self.japanLocation, function (region) {
                        _.each(region.prefectures, function (objPrefecture) {
                            return self.precfecture.push(new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, []));
                        });
                    });
                };
                // reset Data
                ScreenModel.prototype.resetData = function () {
                    var self = this;
                    self.editMode = false;
                    self.enableBTN007(true);
                    self.enableBTN009(false);
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
                    self.residentialReportCode("");
                    self.currentResi(node);
                    self.currentResidential(ko.mapping.fromJS(self.currentResi()));
                    self.previousCurrentCode = '';
                    self.singleSelectedCode("");
                    self.hasFocus(true);
                    self.dirtyObject.reset();
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
                    self.enableBTN007 = ko.observable(true);
                    self.enableBTN009 = ko.observable(null);
                    self.isEditable = ko.observable(true);
                    self.singleSelectedCode = ko.observable("");
                    self.selectedCode = ko.observable("");
                    var objResi = new a.service.model.ResidentialTax();
                    objResi.companyCode = '';
                    objResi.resiTaxCode = '';
                    objResi.resiTaxAutonomy = '';
                    objResi.prefectureCode = '';
                    objResi.resiTaxReportCode = '';
                    objResi.registeredName = '';
                    objResi.companyAccountNo = '';
                    objResi.companySpecifiedNo = '';
                    objResi.cordinatePostalCode = '';
                    objResi.cordinatePostOffice = '';
                    objResi.memo = '';
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
                                self.dirtyObject = new nts.uk.ui.DirtyChecker(self.currentResidential);
                                if (currentSelectedCode === undefined) {
                                    self.singleSelectedCode(self.firstSelectedCode());
                                }
                                else {
                                    self.singleSelectedCode(currentSelectedCode);
                                }
                                self.enableBTN007(true);
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
                    self.firstSelectedCode(node1[0].code);
                };
                ScreenModel.prototype.validateData = function () {
                    $(".nts-editor").ntsEditor("validate");
                    $("#A_INP_002").ntsEditor("validate");
                    $("#A_INP_003").ntsEditor("validate");
                    $("#A_INP_004").ntsEditor("validate");
                    $("#A_INP_005").ntsEditor("validate");
                    $("#A_INP_006").ntsEditor("validate");
                    $("#A_INP_007").ntsEditor("validate");
                    $("#A_INP_008").ntsEditor("validate");
                    $("#A_SEL_001").ntsEditor("validate");
                    var errorA = false;
                    errorA = $("#A_INP_002").ntsError('hasError') || $("#A_INP_003").ntsError('hasError')
                        || $("#A_INP_004").ntsError('hasError') || $("#A_INP_005").ntsError('hasError')
                        || $("#A_INP_006").ntsError('hasError') || $("#A_INP_007").ntsError('hasError')
                        || $("#A_INP_008").ntsError('hasError') || $("#A_SEL_001").ntsError('hasError');
                    if ($(".nts-editor").ntsError('hasError') || errorA) {
                        return false;
                    }
                    return true;
                };
                ScreenModel.prototype.clickRegister = function () {
                    var self = this;
                    var objResi = new a.service.model.ResidentialTax();
                    var node = new Node('', '', []);
                    if (!self.validateData()) {
                        return;
                    }
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
                        });
                    }
                    else {
                        qmm003.a.service.updateData(objResi).done(function () {
                            self.items([]);
                            self.nodeRegionPrefectures([]);
                            self.start(objResi.resiTaxCode);
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
//# sourceMappingURL=qmm003.a.vm.js.map