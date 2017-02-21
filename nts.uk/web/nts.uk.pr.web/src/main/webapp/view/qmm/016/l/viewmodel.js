var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm016;
                (function (qmm016) {
                    var l;
                    (function (l) {
                        var option = nts.uk.ui.option;
                        var MultipleTargetSettingDto = l.service.model.MultipleTargetSettingDto;
                        var MultipleTargetSetting = l.service.model.MultipleTargetSetting;
                        var CertifyGroupDto = l.service.model.CertifyGroupDto;
                        var TypeActionCertifyGroup = l.service.model.TypeActionCertifyGroup;
                        var CertifyGroupDeleteDto = l.service.model.CertifyGroupDeleteDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.columnsLstCertifyGroup = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 120 },
                                        { headerText: '名称', prop: 'name', width: 120 }
                                    ]);
                                    self.currentCode = ko.observable();
                                    self.currentCodeList = ko.observableArray([]);
                                    self.textSearch = {
                                        valueSearch: ko.observable(""),
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "コード・名称で検索・・・",
                                            width: "270",
                                            textalign: "left"
                                        }))
                                    };
                                    self.enableButton = ko.observable(true);
                                    self.selectedMultipleTargetSetting = ko.observable(MultipleTargetSetting.BigestMethod);
                                    self.selectCodeLstLstCertifyGroup = ko.observable('');
                                    self.selectLstCodeLstCertification = ko.observableArray([]);
                                    self.lstCertificationInfo = ko.observableArray([]);
                                    self.selectLstCodeLstCertificationInfo = ko.observableArray([]);
                                    self.typeAction = ko.observable(TypeActionCertifyGroup.update);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllCertifyGroup().done(function (data) {
                                        self.findAllCertification().done(function (data) {
                                            dfd.resolve(self);
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllCertification = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findAllCertification().done(function (data) {
                                        self.lstCertification = data;
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllCertifyGroup = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findAllCertifyGroup().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            self.lstCertifyGroup = ko.observableArray(data);
                                            self.selectCodeLstLstCertifyGroup = ko.observable(data[0].code);
                                            self.selectCodeLstLstCertifyGroup.subscribe(function (selectionCodeLstLstCertifyGroup) {
                                                self.showchangeCertifyGroup(selectionCodeLstLstCertifyGroup);
                                            });
                                            self.findCertifyGroup(data[0].code).done(function (data) {
                                                dfd.resolve(self);
                                            });
                                        }
                                        else {
                                            self.newmodeEmptyData();
                                            self.lstCertifyGroup = ko.observableArray([]);
                                            dfd.resolve(self);
                                        }
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findCertifyGroup = function (code) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findCertifyGroup(code).done(function (data) {
                                        self.certifyGroupModel = ko.observable(new CertifyGroupModel(data));
                                        l.service.findAllCertification().done(function (data) {
                                            self.certifyGroupModel().setLstCertification(data);
                                            dfd.resolve(self);
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.detailCertifyGroup = function (code) {
                                    if (code != null && code != undefined && code != '') {
                                        var self = this;
                                        l.service.findCertifyGroup(code).done(function (data) {
                                            self.certifyGroupModel().code(data.code);
                                            self.certifyGroupModel().name(data.name);
                                            self.certifyGroupModel().multiApplySet(data.multiApplySet);
                                            self.certifyGroupModel().certifies(data.certifies);
                                            self.certifyGroupModel().lstCertification(self.clearCertifyGroupFind(self.lstCertification, data.certifies));
                                            self.typeAction(TypeActionCertifyGroup.update);
                                        });
                                    }
                                };
                                ScreenModel.prototype.clearCertifyGroupFind = function (datafull, dataClear) {
                                    var i = -1;
                                    var dataSetup = [];
                                    for (var _i = 0, datafull_1 = datafull; _i < datafull_1.length; _i++) {
                                        var itemOfDataFull = datafull_1[_i];
                                        i++;
                                        var exitClear = 1;
                                        for (var _a = 0, dataClear_1 = dataClear; _a < dataClear_1.length; _a++) {
                                            var itemOfDataClear = dataClear_1[_a];
                                            if (itemOfDataFull.code === itemOfDataClear.code) {
                                                exitClear = 0;
                                                break;
                                            }
                                        }
                                        if (exitClear == 1) {
                                            dataSetup.push(itemOfDataFull);
                                        }
                                    }
                                    return dataSetup;
                                };
                                ScreenModel.prototype.showchangeCertifyGroup = function (selectionCodeLstLstCertifyGroup) {
                                    var self = this;
                                    self.detailCertifyGroup(selectionCodeLstLstCertifyGroup);
                                };
                                ScreenModel.prototype.resetValueCertifyGroup = function () {
                                    var self = this;
                                    if (self.certifyGroupModel == null || self.certifyGroupModel == undefined) {
                                        self.certifyGroupModel = ko.observable(new CertifyGroupModel(new CertifyGroupDto()));
                                    }
                                    self.certifyGroupModel().code('');
                                    self.certifyGroupModel().name('');
                                    self.certifyGroupModel().multiApplySet(MultipleTargetSetting.BigestMethod);
                                    self.typeAction(TypeActionCertifyGroup.add);
                                    self.lstCertificationInfo([]);
                                    self.selectCodeLstLstCertifyGroup('');
                                    self.certifyGroupModel().certifies([]);
                                    self.certifyGroupModel().lstCertification(self.lstCertification);
                                };
                                ScreenModel.prototype.saveCertifyGroup = function () {
                                    var self = this;
                                    if (self.typeAction() == TypeActionCertifyGroup.add) {
                                        l.service.addCertifyGroup(self.convertDataModel()).done(function (data) {
                                            self.reloadDataByAction(self.certifyGroupModel().code());
                                        });
                                    }
                                    else {
                                        l.service.updateCertifyGroup(self.convertDataModel()).done(function (data) {
                                            self.reloadDataByAction(self.certifyGroupModel().code());
                                        });
                                    }
                                };
                                ScreenModel.prototype.reloadDataByAction = function (code) {
                                    var self = this;
                                    l.service.findAllCertifyGroup().done(function (data) {
                                        self.lstCertifyGroup(data);
                                        if (code != null && code != undefined && code != '') {
                                            self.selectCodeLstLstCertifyGroup(code);
                                            self.detailCertifyGroup(code);
                                        }
                                        else {
                                            self.selectCodeLstLstCertifyGroup(data[0].code);
                                            self.detailCertifyGroup(data[0].code);
                                        }
                                    });
                                };
                                ScreenModel.prototype.newmodeEmptyData = function () {
                                    var self = this;
                                    self.findAllCertification().done(function (data) {
                                        console.log();
                                        self.resetValueCertifyGroup();
                                    });
                                };
                                ScreenModel.prototype.deleteCertifyGroup = function () {
                                    var self = this;
                                    var certifyGroupDeleteDto = new CertifyGroupDeleteDto();
                                    certifyGroupDeleteDto.groupCode = self.certifyGroupModel().code();
                                    certifyGroupDeleteDto.version = 12;
                                    l.service.deleteCertifyGroup(certifyGroupDeleteDto).done(function (data) {
                                        self.reloadDataByAction('');
                                    });
                                };
                                ScreenModel.prototype.closeCertifyGroup = function () {
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.convertDataModel = function () {
                                    var self = this;
                                    var certifyGroupDto = new CertifyGroupDto();
                                    certifyGroupDto.code = self.certifyGroupModel().code();
                                    certifyGroupDto.name = self.certifyGroupModel().name();
                                    certifyGroupDto.multiApplySet = self.certifyGroupModel().multiApplySet();
                                    certifyGroupDto.certifies = self.certifyGroupModel().certifies();
                                    return certifyGroupDto;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var CertifyGroupModel = (function () {
                                function CertifyGroupModel(certifyGroupDto) {
                                    this.code = ko.observable(certifyGroupDto.code);
                                    this.name = ko.observable(certifyGroupDto.name);
                                    this.multiApplySet = ko.observable(certifyGroupDto.multiApplySet);
                                    this.columnsCertification = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 60 },
                                        { headerText: '名称', prop: 'name', width: 180 }
                                    ]);
                                    this.selectionMultipleTargetSetting = ko.observableArray([new MultipleTargetSettingDto(MultipleTargetSetting.BigestMethod, "BigestMethod"),
                                        new MultipleTargetSettingDto(MultipleTargetSetting.TotalMethod, "TotalMethod")
                                    ]);
                                    this.certifies = ko.observableArray([]);
                                    this.lstCertification = ko.observableArray([]);
                                }
                                CertifyGroupModel.prototype.setLstCertification = function (lstCertification) {
                                    this.lstCertification = ko.observableArray(lstCertification);
                                };
                                return CertifyGroupModel;
                            }());
                            viewmodel.CertifyGroupModel = CertifyGroupModel;
                        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
                    })(l = qmm016.l || (qmm016.l = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
