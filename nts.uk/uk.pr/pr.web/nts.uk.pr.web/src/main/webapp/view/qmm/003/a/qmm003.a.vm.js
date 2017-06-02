var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.firstSelectedCode = ko.observable("");
                    this.enableBTN005 = ko.observable(null);
                    this.enableBTN006 = ko.observable(null);
                    this.isNew = ko.observable(true); // mode update , new mode if true
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
                    this.previousCurrentCode = null; //lưu giá trị của currentCode trước khi nó bị thay đổi
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
                        if (!nts.uk.text.isNullOrEmpty(newValue) && (self.singleSelectedCode() !== self.previousCurrentCode)) {
                            if (self.dirtyObject.isDirty()) {
                                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                                    self.processWhenCurrentCodeChange(newValue);
                                }).ifCancel(function () {
                                    if (nts.uk.text.isNullOrEmpty(self.previousCurrentCode)) {
                                        self.singleSelectedCode('');
                                        self.isNew(true);
                                        self.enableBTN005(false);
                                    }
                                    else {
                                        self.singleSelectedCode(self.previousCurrentCode);
                                    }
                                });
                            }
                            else {
                                self.processWhenCurrentCodeChange(newValue);
                            }
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
                            if ($('.nts-editor').ntsError("hasError")) {
                                $('.save-error').ntsError('clear');
                            }
                            if (data.resiTaxReportCode) {
                                var residential_1;
                                _.each(self.residentalTaxList000(), function (objResi) {
                                    if (objResi.resiTaxCode === data.resiTaxReportCode) {
                                        residential_1 = objResi;
                                    }
                                });
                                if (!residential_1) {
                                    _.each(self.residentalTaxList(), function (objResi) {
                                        if (objResi.resiTaxCode === data.resiTaxReportCode) {
                                            residential_1 = objResi;
                                        }
                                    });
                                }
                                data.resiTaxReportCode = residential_1.resiTaxCode + " " + residential_1.resiTaxAutonomy;
                            }
                            self.isNew(false);
                            self.enableBTN005(true);
                            self.currentResidential().setData(data);
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
                    if ($('.nts-editor').ntsError("hasError")) {
                        $('.save-error').ntsError('clear');
                    }
                    self.currentResidential().enableBTN009(true);
                    self.isNew(true);
                    self.enableBTN005(false);
                    self.currentResidential().resiTaxCode('');
                    self.currentResidential().resiTaxAutonomy('');
                    self.currentResidential().resiTaxAutonomyKana('');
                    self.currentResidential().prefectureCode('');
                    self.currentResidential().resiTaxReportCode('');
                    self.currentResidential().registeredName('');
                    self.currentResidential().companyAccountNo('');
                    self.currentResidential().companySpecifiedNo('');
                    self.currentResidential().cordinatePostalCode('');
                    self.currentResidential().cordinatePostOffice('');
                    self.currentResidential().memo('');
                    self.previousCurrentCode = '';
                    self.singleSelectedCode("");
                    self.dirtyObject.reset();
                    $("#A_INP_002").focus();
                };
                // init menu
                ScreenModel.prototype.init = function () {
                    var self = this;
                    // data of treegrid
                    self.redensitalTaxNodeList = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable("");
                    self.currentResidential = ko.observable(new ResidentialTax({
                        resiTaxCode: '',
                        resiTaxAutonomy: '',
                        resiTaxAutonomyKana: '',
                        prefectureCode: '',
                        registeredName: '',
                        companyAccountNo: '',
                        companySpecifiedNo: ''
                    }));
                };
                // khi click vào btn006 mở ra màn hình D
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    var yes;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/d/index.xhtml", { title: '住民税納付先の登録　＞　一括削除', dialogClass: "no-close" }).onClosed(function () {
                        yes = nts.uk.ui.windows.getShared("yes");
                        if (yes) {
                            self.redensitalTaxNodeList([]);
                            self.nodeRegionPrefectures([]);
                            self.reload(undefined);
                        }
                    });
                };
                // xóa một đối tượng 
                ScreenModel.prototype.deleteAResidential = function () {
                    var self = this;
                    var objResidential;
                    objResidential = (ko.toJS(self.currentResidential()));
                    var resiTaxCodes = [];
                    resiTaxCodes.push(objResidential.resiTaxCode);
                    nts.uk.ui.dialog.confirm("データを削除します。\r\n よろしいですか？").ifYes(function () {
                        if (resiTaxCodes) {
                            qmm003.a.service.deleteResidential(resiTaxCodes).done(function (data) {
                                self.redensitalTaxNodeList([]);
                                self.nodeRegionPrefectures([]);
                                self.reload(self.firstSelectedCode());
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert(res.message);
                            });
                        }
                        else {
                            nts.uk.ui.dialog.alert("住民税納付先コード が選択されていません。す。");
                        }
                    });
                };
                // khi click btn004 mở ra màn hình E - chức năng hợp nhất nơi đăng kí thuế cư trú
                ScreenModel.prototype.openEDialog = function () {
                    var self = this;
                    var yes;
                    if (self.redensitalTaxNodeList().length > 0) {
                        nts.uk.ui.windows.sub.modeless("/view/qmm/003/e/index.xhtml", { title: '住民税納付先の登録＞納付先の統合', dialogClass: "no-close" }).onClosed(function () {
                            yes = nts.uk.ui.windows.getShared('yes');
                            if (yes) {
                                self.redensitalTaxNodeList([]);
                                self.nodeRegionPrefectures([]);
                                self.reload(undefined);
                            }
                        });
                    }
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
                            self.isNew(false);
                            self.enableBTN005(true);
                            self.enableBTN006(true);
                            self.residentalTaxList(data);
                            self.numberOfResidentialTax(" 【登録件数】" + data.length + "  件");
                            self.getJapanLocation(currentSelectedCode);
                        }
                        else {
                            self.settingNewMode();
                            self.enableBTN006(false);
                            self.currentResidential().enableBTN009(false);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
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
                            self.currentResidential().editMode = true; // true, update mode 
                            self.residentalTaxList(data);
                            self.numberOfResidentialTax(" 【登録件数】" + data.length + "  件");
                            self.getJapanLocation(currentSelectedCode);
                            self.isNew(false);
                            self.enableBTN005(true);
                            self.enableBTN006(true);
                        }
                        else {
                            self.resetData();
                            self.numberOfResidentialTax(" 【登録件数】" + data.length + "  件");
                            self.settingNewMode();
                            self.currentResidential().enableBTN009(false);
                            self.enableBTN006(false);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
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
                        self.currentResidential().enableBTN009(true);
                        self.isNew(false);
                        self.enableBTN005(true);
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
                    self.isNew(true);
                    self.enableBTN005(false);
                    $("#A_INP_002").focus();
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
                    if (!objResi.resiTaxReportCode) {
                        objResi.resiTaxReportCode = objResi.resiTaxReportCode;
                    }
                    else {
                        objResi.resiTaxReportCode = objResi.resiTaxReportCode.substring(0, 6);
                    }
                    if (self.isNew()) {
                        qmm003.a.service.addResidential(objResi).done(function () {
                            self.redensitalTaxNodeList([]);
                            self.nodeRegionPrefectures([]);
                            self.start(objResi.resiTaxCode);
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert(res.message);
                        });
                    }
                    else {
                        qmm003.a.service.updateData(objResi).done(function () {
                            self.redensitalTaxNodeList([]);
                            self.nodeRegionPrefectures([]);
                            self.start(objResi.resiTaxCode);
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert(res.message);
                        });
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            //insert an update  ResidentialTax Object
            var ResidentialTax = (function () {
                function ResidentialTax(param) {
                    this.editMode = null; // true là mode thêm mới, false là mode update 
                    this.enableBTN009 = ko.observable(null);
                    this.init(param);
                }
                ResidentialTax.prototype.setData = function (param) {
                    var self = this;
                    self.resiTaxCode(param.resiTaxCode);
                    self.resiTaxAutonomy(param.resiTaxAutonomy);
                    self.resiTaxAutonomyKana(param.resiTaxAutonomyKana);
                    self.prefectureCode(param.prefectureCode);
                    self.resiTaxReportCode(param.resiTaxReportCode);
                    self.registeredName(param.registeredName);
                    self.companyAccountNo(param.companyAccountNo);
                    self.companySpecifiedNo(param.companySpecifiedNo);
                    self.cordinatePostalCode(param.cordinatePostalCode);
                    self.cordinatePostOffice(param.cordinatePostOffice);
                    self.memo(param.memo);
                };
                ResidentialTax.prototype.init = function (param) {
                    this.resiTaxCode = ko.observable(param.resiTaxCode);
                    this.resiTaxAutonomy = ko.observable(param.resiTaxAutonomy);
                    this.resiTaxAutonomyKana = ko.observable(param.resiTaxAutonomyKana);
                    this.prefectureCode = ko.observable(param.prefectureCode);
                    this.resiTaxReportCode = ko.observable(param.resiTaxReportCode);
                    this.registeredName = ko.observable(param.registeredName);
                    this.companyAccountNo = ko.observable(param.companyAccountNo);
                    this.companySpecifiedNo = ko.observable(param.companySpecifiedNo);
                    this.cordinatePostalCode = ko.observable(param.cordinatePostalCode);
                    this.cordinatePostOffice = ko.observable(param.cordinatePostOffice);
                    this.memo = ko.observable(param.memo);
                };
                // khi click btn007 mo ra man hinh B
                ResidentialTax.prototype.openBDialog = function () {
                    var self = this;
                    var currentNode;
                    var yes;
                    nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function () {
                        currentNode = nts.uk.ui.windows.getShared("currentNode");
                        yes = nts.uk.ui.windows.getShared("yes");
                        if (yes) {
                            self.resiTaxCode(currentNode.resiTaxCode);
                            self.resiTaxAutonomy(currentNode.resiTaxAutonomy);
                            self.resiTaxAutonomyKana(currentNode.resiTaxAutonomyKana);
                            self.prefectureCode(currentNode.prefectureCode);
                            $('#A_INP_002').ntsError('clear');
                            $('#A_INP_003').ntsError('clear');
                        }
                    });
                };
                // khi click btn009 mo ra man hinh C 
                ResidentialTax.prototype.openCDialog = function () {
                    var self = this;
                    var currentNode;
                    var yes;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/c/index.xhtml", { title: '住民税納付先の登録＞住民税報告先一覧', dialogClass: "no-close" }).onClosed(function () {
                        currentNode = nts.uk.ui.windows.getShared('currentNode');
                        yes = nts.uk.ui.windows.getShared('yes');
                        if (yes) {
                            self.resiTaxReportCode(currentNode.resiTaxCode + " " + currentNode.resiTaxAutonomy);
                        }
                    });
                };
                return ResidentialTax;
            }());
            viewmodel.ResidentialTax = ResidentialTax;
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm003.a || (qmm003.a = {}));
})(qmm003 || (qmm003 = {}));
;
//# sourceMappingURL=qmm003.a.vm.js.map