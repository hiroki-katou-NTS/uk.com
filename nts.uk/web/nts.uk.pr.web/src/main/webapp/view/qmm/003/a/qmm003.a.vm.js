var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.firstSelectedCode = ko.observable("");
                    this.isUpdate = ko.observable(true);
                    this.editMode = null; // true là mode thêm mới, false là mode update 
                    this.filteredData = ko.observableArray([]);
                    this.nodeRegionPrefectures = ko.observableArray([]);
                    this.japanLocation = [];
                    this.precfecture = [];
                    this.itemPrefecture = ko.observableArray([]);
                    //companyCode != 0000
                    this.residentalTaxList = ko.observableArray([]);
                    //companyCode = 0000
                    this.residentalTaxList000 = ko.observableArray([]);
                    this.currentResidential = ko.observable(null);
                    this.report = ko.observable('');
                    this.previousCurrentCode = null; //lưu giá trị của currentCode trước khi nó bị thay đổi
                    this.hasFocus = ko.observable(true);
                    this.numberOfResidentialTax = ko.observable('');
                    var self = this;
                    self.init();
                    self.singleSelectedCode.subscribe(function (newValue) {
                        if (newValue.length === 1) {
                            var index = void 0;
                            index = _.findIndex(self.redensitalTaxNodeList(), function (obj) {
                                return obj.code === newValue;
                            });
                            self.processWhenCurrentCodeChange(self.redensitalTaxNodeList()[index].childs[0].childs[0].code);
                            return;
                        }
                        if (newValue.length === 2) {
                            var prefecture = [];
                            prefecture = self.findIndex(self.redensitalTaxNodeList(), newValue);
                            self.processWhenCurrentCodeChange(self.redensitalTaxNodeList()[prefecture[0]].childs[prefecture[1]].childs[0].code);
                            return;
                        }
                        if (!nts.uk.text.isNullOrEmpty(newValue) && (self.singleSelectedCode() !== self.previousCurrentCode) && self.editMode) {
                            if (self.dirtyObject.isDirty()) {
                                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                                    self.processWhenCurrentCodeChange(newValue);
                                }).ifCancel(function () {
                                    if (nts.uk.text.isNullOrEmpty(self.previousCurrentCode)) {
                                        self.singleSelectedCode('');
                                        self.isUpdate(true);
                                    }
                                    else {
                                        self.redensitalTaxNodeList([]);
                                        self.nodeRegionPrefectures([]);
                                        self.start(self.previousCurrentCode);
                                    }
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
                    var prefectureArray = [];
                    _.each(items, function (obj) {
                        count++;
                        index = _.findIndex(obj.childs, function (obj1) {
                            return obj1.code === newValue;
                        });
                        if (index > -1) {
                            prefectureArray.push(count, index);
                        }
                    });
                    return prefectureArray;
                };
                // get Data khi currentCode thay đổi
                ScreenModel.prototype.processWhenCurrentCodeChange = function (newValue) {
                    var self = this;
                    a.service.getResidentialTaxDetail(newValue).done(function (data) {
                        if (data) {
                            if (data.resiTaxReportCode) {
                                var residential_1;
                                _.each(self.residentalTaxList000(), function (objResi) {
                                    if (objResi.resiTaxCode === data.resiTaxReportCode) {
                                        residential_1 = objResi;
                                    }
                                });
                                console.log(residential_1);
                                data.resiTaxReportCode = residential_1.resiTaxCode + " " + residential_1.resiTaxAutonomy;
                            }
                            if ($('.nts-editor').ntsError("hasError")) {
                                $('.save-error').ntsError('clear');
                            }
                            self.isUpdate(false);
                            self.currentResidential(ko.mapping.fromJS(data));
                            self.previousCurrentCode = newValue;
                            self.dirtyObject.reset();
                        }
                        else {
                            self.resetData();
                        }
                    });
                };
                //  set selectedcode by prefectureCode
                ScreenModel.prototype.findNodeByResiTax = function (items, code) {
                    var self = this;
                    var node;
                    node = _.find(items, function (objPrefecture) {
                        return objPrefecture.resiTaxCode = code;
                    });
                    return node;
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
                    self.enableBTN009(true);
                    self.isUpdate(true);
                    var node;
                    node = new ResidentialTax(({
                        resiTaxCode: '',
                        resiTaxAutonomy: '',
                        prefectureCode: '',
                        registeredName: '',
                        companyAccountNo: '',
                        companySpecifiedNo: ''
                    }));
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
                    self.redensitalTaxNodeList = ko.observableArray([]);
                    self.enableBTN009 = ko.observable(null);
                    self.singleSelectedCode = ko.observable("");
                    var objResi = new ResidentialTax(({
                        resiTaxCode: '',
                        resiTaxAutonomy: '',
                        prefectureCode: '',
                        registeredName: '',
                        companyAccountNo: '',
                        companySpecifiedNo: ''
                    }));
                    self.currentResidential = ko.observable(ko.mapping.fromJS(objResi));
                };
                // khi click btn007 mo ra man hinh B
                ScreenModel.prototype.openBDialog = function () {
                    var self = this;
                    var currentNode;
                    nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function () {
                        currentNode = nts.uk.ui.windows.getShared("currentNode");
                        self.editMode = false;
                        self.resiTaxCode(currentNode.resiTaxCode);
                        self.resiTaxAutonomy(currentNode.resiTaxAutonomy);
                        self.prefectureCode(currentNode.prefectureCode);
                    });
                };
                // khi click btn009 mo ra man hinh C 
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    var currentResidential;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/c/index.xhtml", { title: '住民税納付先の登録＞住民税報告先一覧', dialogClass: "no-close" }).onClosed(function () {
                        currentResidential = nts.uk.ui.windows.getShared('currentResidential');
                        self.resiTaxReportCode(currentResidential.resiTaxCode + " " + currentResidential.resiTaxAutonomy);
                    });
                };
                // khi click vào btn006 mở ra màn hình D
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    var items;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/d/index.xhtml", { title: '住民税納付先の登録　＞　一括削除', dialogClass: "no-close" }).onClosed(function () {
                        items = nts.uk.ui.windows.getShared('items');
                        self.redensitalTaxNodeList([]);
                        self.nodeRegionPrefectures([]);
                        self.start(undefined);
                    });
                };
                // xóa một đối tượng 
                ScreenModel.prototype.deleteAResidential = function () {
                    var self = this;
                    var objResidential;
                    objResidential = (ko.toJS(self.currentResidential()));
                    var resiTaxCodes = [];
                    resiTaxCodes.push(objResidential.resiTaxCode);
                    qmm003.a.service.deleteResidential(resiTaxCodes).done(function (data) {
                        self.redensitalTaxNodeList([]);
                        self.nodeRegionPrefectures([]);
                        self.reload(self.firstSelectedCode());
                    });
                };
                // khi click btn004 mở ra màn hình E - chức năng hợp nhất nơi đăng kí thuế cư trú
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
                    a.service.getResidentialTaxCCD().done(function (data) {
                        self.residentalTaxList000(data);
                    });
                    (qmm003.a.service.getResidentialTax()).done(function (data) {
                        if (data.length > 0) {
                            self.editMode = true; // true, update mode 
                            self.residentalTaxList(data);
                            console.log(data);
                            self.numberOfResidentialTax(" 【登録件数】" + data.length + "  件");
                            self.getJapanLocation(currentSelectedCode);
                        }
                        else {
                            self.settingNewMode();
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                // Khi xóa dữ liệu xong rồi  mà list bên trái  = [] 
                //thì sẽ gọi hàm reload ra để reset các giá trị ở màn hình bên phải
                ScreenModel.prototype.reload = function (currentSelectedCode) {
                    var dfd = $.Deferred();
                    var self = this;
                    (qmm003.a.service.getResidentialTax()).done(function (data) {
                        if (data.length > 0) {
                            self.editMode = true; // true, update mode 
                            self.residentalTaxList(data);
                            self.numberOfResidentialTax(" 【登録件数】" + data.length + "  件");
                            self.getJapanLocation(currentSelectedCode);
                        }
                        else {
                            self.resetData();
                            self.numberOfResidentialTax(" 【登録件数】" + data.length + "  件");
                            self.settingNewMode();
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                // lấy dữ liệu japan location
                ScreenModel.prototype.getJapanLocation = function (currentSelectedCode) {
                    var self = this;
                    (qmm003.a.service.getRegionPrefecture()).done(function (locationData) {
                        self.japanLocation = locationData;
                        self.buildPrefectureArray();
                        self.itemPrefecture(self.precfecture);
                        self.buildResidentalTaxTree();
                        var node = [];
                        node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                        self.filteredData(node);
                        self.redensitalTaxNodeList(self.nodeRegionPrefectures());
                        self.dirtyObject = new nts.uk.ui.DirtyChecker(self.currentResidential);
                        if (currentSelectedCode === undefined) {
                            self.singleSelectedCode(self.firstSelectedCode());
                        }
                        else {
                            self.singleSelectedCode(currentSelectedCode);
                        }
                        self.enableBTN009(true);
                        self.isUpdate(false);
                    });
                };
                // setting chế độ thêm mới (trường hợp get List data = 0)
                ScreenModel.prototype.settingNewMode = function () {
                    var self = this;
                    (qmm003.a.service.getRegionPrefecture()).done(function (locationData) {
                        self.japanLocation = locationData;
                        self.buildPrefectureArray();
                        self.itemPrefecture(self.precfecture);
                    });
                    self.numberOfResidentialTax(" 【登録件数】 0  件");
                    self.editMode = false; // false, new mode
                    self.isUpdate(true);
                    self.enableBTN009(true);
                };
                // hàm xây dựng tree
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
                // add va update doi tuong residentialTax
                ScreenModel.prototype.clickRegister = function () {
                    var self = this;
                    var objResi = new a.service.model.ResidentialTaxDetailDto();
                    var node = new RedensitalTaxNode('', '', []);
                    if (!self.validateData()) {
                        return;
                    }
                    objResi = ko.toJS(self.currentResidential());
                    objResi.resiTaxReportCode = objResi.resiTaxReportCode.substring(0, 6);
                    if (!self.editMode) {
                        qmm003.a.service.addResidential(objResi).done(function () {
                            self.redensitalTaxNodeList([]);
                            self.nodeRegionPrefectures([]);
                            self.start(objResi.resiTaxCode);
                        });
                    }
                    else {
                        qmm003.a.service.updateData(objResi).done(function () {
                            self.redensitalTaxNodeList([]);
                            self.nodeRegionPrefectures([]);
                            self.start(objResi.resiTaxCode);
                        });
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            // class xay dung tree grid
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
            //insert an update  ResidentialTax Object
            var ResidentialTax = (function () {
                function ResidentialTax(param) {
                    this.resiTaxCode = param.resiTaxCode;
                    this.resiTaxAutonomy = param.resiTaxAutonomy;
                    this.prefectureCode = param.prefectureCode;
                    this.resiTaxReportCode = param.resiTaxReportCode;
                    this.registeredName = param.registeredName;
                    this.companyAccountNo = param.companyAccountNo;
                    this.companySpecifiedNo = param.companySpecifiedNo;
                    this.cordinatePostalCode = param.cordinatePostalCode;
                    this.cordinatePostOffice = param.cordinatePostOffice;
                    this.memo = param.memo;
                }
                return ResidentialTax;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm003.a || (qmm003.a = {}));
})(qmm003 || (qmm003 = {}));
;
//# sourceMappingURL=qmm003.a.vm.js.map