var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.firstSelectedCode = ko.observable("");
                    this.enableInp002 = ko.observable(true);
                    this.editMode = true; // true là mode thêm mới, false là mode sửa 
                    this.filteredData = ko.observableArray([]);
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    this.residentalTaxList = ko.observableArray([]);
                    this.currentResidential = ko.observable(null);
                    this.resiTaxReportCode = ko.observable("");
                    this.resiTaxCode = ko.observable("");
                    this.resiTaxAutonomy = ko.observable("");
                    this.prefectureCode = ko.observable("");
                    this.previousCurrentCode = null; //lưu giá trị của currentCode trước khi nó bị thay đổi
                    this.hasFocus = ko.observable(true);
                    var self = this;
                    self.init();
                    self.singleSelectedCode.subscribe(function (newValue) {
                        if (newValue.length === 1) {
                            var index = void 0;
                            index = _.findIndex(self.items(), function (obj) {
                                return obj.code === newValue;
                            });
                            self.singleSelectedCode(self.items()[index].childs[0].childs[0].code);
                            return;
                        }
                        if (newValue.length === 2) {
                            var array = [];
                            array = self.findIndex(self.items(), newValue);
                            self.singleSelectedCode(self.items()[array[0]].childs[array[1]].childs[0].code);
                            return;
                        }
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
                // tìm index để khi chọn root thì ra hiển thị ra thằng đầu tiên của 1 thằng root
                ScreenModel.prototype.findIndex = function (items, newValue) {
                    var index;
                    var count = -1;
                    var array = [];
                    _.each(items, function (obj) {
                        count++;
                        index = _.findIndex(obj.childs, function (obj1) {
                            return obj1.code === newValue;
                        });
                        if (index > -1) {
                            array.push(count, index);
                        }
                    });
                    return array;
                };
                ScreenModel.prototype.processWhenCurrentCodeChange = function (newValue) {
                    var self = this;
                    a.service.getResidentialTaxDetail(newValue).done(function (data) {
                        if (data) {
                            if ($('.nts-editor').ntsError("hasError")) {
                                $('.save-error').ntsError('clear');
                            }
                            if (!self.enableBTN009()) {
                                self.enableBTN009(true);
                            }
                            self.enableInp002(false);
                            self.currentResidential(ko.mapping.fromJS(data));
                            self.previousCurrentCode = newValue;
                            self.dirtyObject.reset();
                        }
                        else {
                            self.resetData();
                        }
                    });
                };
                // create array prefecture from japan location
                ScreenModel.prototype.buildPrefectureArray = function () {
                    var self = this;
                    _.map(self.japanLocation, function (region) {
                        _.each(region.prefectures, function (objPrefecture) {
                            return self.precfecture.push(new RedensitalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, []));
                        });
                    });
                };
                // reset Data
                ScreenModel.prototype.resetData = function () {
                    var self = this;
                    self.editMode = false;
                    self.enableBTN007(true);
                    self.enableBTN009(true);
                    self.enableInp002(true);
                    var node = new a.service.model.ResidentialTax();
                    node.companyCode = '';
                    node.resiTaxCode = '';
                    node.resiTaxAutonomy = '';
                    node.prefectureCode = '1';
                    node.resiTaxReportCode = '';
                    node.registeredName = '';
                    node.companyAccountNo = '';
                    node.companySpecifiedNo = '';
                    node.cordinatePostalCode = '';
                    node.cordinatePostOffice = '';
                    node.memo = '';
                    self.currentResidential(ko.mapping.fromJS(node));
                    self.previousCurrentCode = '';
                    self.singleSelectedCode("");
                    self.hasFocus(true);
                    self.dirtyObject.reset();
                };
                // init menu
                ScreenModel.prototype.init = function () {
                    var self = this;
                    // data of treegrid
                    self.items = ko.observableArray([]);
                    self.mode = ko.observable(null);
                    self.isEnable = ko.observable(true);
                    self.enableBTN007 = ko.observable(true);
                    self.enableBTN009 = ko.observable(null);
                    self.isEditable = ko.observable(true);
                    self.singleSelectedCode = ko.observable("");
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
                    self.currentResidential = ko.observable(ko.mapping.fromJS(objResi));
                };
                //BTN007
                ScreenModel.prototype.openBDialog = function () {
                    var self = this;
                    var currentResidential;
                    nts.uk.ui.windows.setShared('singleSelectedCode', self.singleSelectedCode, true);
                    nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function () {
                        currentResidential = nts.uk.ui.windows.getShared("currentResidential");
                        self.editMode = false;
                        self.resiTaxCode(currentResidential.resiTaxCode);
                        self.resiTaxAutonomy(currentResidential.resiTaxAutonomy);
                        self.prefectureCode(currentResidential.prefectureCode);
                    });
                };
                //BTN009
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    var currentResidential;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/c/index.xhtml", { title: '住民税納付先の登録＞住民税報告先一覧', dialogClass: "no-close" }).onClosed(function () {
                        currentResidential = nts.uk.ui.windows.getShared('currentResidential');
                        self.resiTaxReportCode(currentResidential.resiTaxCode);
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
                    objResidential = (ko.toJS(self.currentResidential()));
                    var resiTaxCodes = [];
                    resiTaxCodes.push(objResidential.resiTaxCode);
                    qmm003.a.service.deleteResidential(resiTaxCodes).done(function (data) {
                        self.items([]);
                        self.nodeRegionPrefectures([]);
                        self.reload(self.firstSelectedCode());
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
                                self.enableBTN007(false);
                                self.enableBTN009(true);
                                self.enableInp002(false);
                            });
                        }
                        else {
                            //self.resetData();
                            (qmm003.a.service.getRegionPrefecture()).done(function (locationData) {
                                self.japanLocation = locationData;
                                self.buildPrefectureArray();
                                self.itemPrefecture(self.precfecture);
                            });
                            self.mode(false); // false, new mode
                            self.enableBTN007(true);
                            self.enableBTN009(true);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.reload = function (currentSelectedCode) {
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
                                self.singleSelectedCode(currentSelectedCode);
                                self.enableBTN007(false);
                                self.enableBTN009(true);
                                self.enableInp002(false);
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
                            self.enableBTN009(true);
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
                                        var child_1 = [];
                                        self.nodeRegionPrefectures.push(new RedensitalTaxNode(objRegion.regionCode, objRegion.regionName, [new RedensitalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new RedensitalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                                    }
                                }
                            });
                        });
                    });
                    var node;
                    node = self.nodeRegionPrefectures()[0].childs;
                    var node1;
                    node1 = (node[0].childs);
                    self.firstSelectedCode(self.nodeRegionPrefectures()[0].childs[0].childs[0].code);
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
                    var node = new RedensitalTaxNode('', '', []);
                    if (!self.validateData()) {
                        return;
                    }
                    objResi = ko.toJS(self.currentResidential());
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm003.a || (qmm003.a = {}));
})(qmm003 || (qmm003 = {}));
;
//# sourceMappingURL=qmm003.a.vm.js.map