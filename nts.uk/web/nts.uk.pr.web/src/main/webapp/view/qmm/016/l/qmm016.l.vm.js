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
                                        { headerText: 'コード', key: 'code', width: 120 },
                                        { headerText: '名称', key: 'name', width: 120 }
                                    ]);
                                    self.selectedMultipleTargetSetting = ko.observable(MultipleTargetSetting.BigestMethod);
                                    self.selectCodeLstLstCertifyGroup = ko.observable('');
                                    self.selectLstCodeLstCertification = ko.observableArray([]);
                                    self.typeAction = ko.observable(TypeActionCertifyGroup.update);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    self.isEmpty = ko.observable(true);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllCertifyGroup().done(function () {
                                        self.findAllCertification().done(function (data) {
                                            dfd.resolve(data);
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
                                            self.selectCodeLstLstCertifyGroup(data[0].code);
                                            self.selectCodeLstLstCertifyGroup.subscribe(function (selectionCodeLstLstCertifyGroup) {
                                                self.showchangeCertifyGroup(selectionCodeLstLstCertifyGroup);
                                            });
                                            self.findCertifyGroup(data[0].code).done(function (res) {
                                                dfd.resolve(res);
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
                                            self.certifyGroupModel().lstCertification(data);
                                            self.lstCertification = data;
                                            dfd.resolve(self);
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.detailCertifyGroup = function (code) {
                                    if (code != null && code != undefined && code != '') {
                                        var self = this;
                                        l.service.findCertifyGroup(code).done(function (data) {
                                            if (self.isEmpty()) {
                                                self.selectCodeLstLstCertifyGroup(code);
                                                self.selectCodeLstLstCertifyGroup.subscribe(function (selectionCodeLstLstCertifyGroup) {
                                                    self.showchangeCertifyGroup(selectionCodeLstLstCertifyGroup);
                                                });
                                                self.isEmpty(false);
                                            }
                                            self.certifyGroupModel().code(data.code);
                                            self.certifyGroupModel().name(data.name);
                                            self.certifyGroupModel().multiApplySet(data.multiApplySet);
                                            self.certifyGroupModel().certifies(data.certifies);
                                            self.typeAction(TypeActionCertifyGroup.update);
                                            self.certifyGroupModel().setReadOnly(true);
                                            l.service.findAllCertification().done(function (dataCertification) {
                                                self.certifyGroupModel().setLstCertification(dataCertification);
                                            });
                                        });
                                    }
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
                                    self.selectCodeLstLstCertifyGroup('');
                                    self.certifyGroupModel().setReadOnly(false);
                                    self.certifyGroupModel().certifies([]);
                                    l.service.findAllCertification().done(function (data) {
                                        self.certifyGroupModel().lstCertification(data);
                                    });
                                };
                                ScreenModel.prototype.saveCertifyGroup = function () {
                                    var self = this;
                                    if (self.typeAction() == TypeActionCertifyGroup.add) {
                                        l.service.addCertifyGroup(self.convertDataModel()).done(function () {
                                            self.reloadDataByAction(self.certifyGroupModel().code());
                                        }).fail(function (error) {
                                            self.showMessageSave(error.message);
                                            self.reloadDataByAction('');
                                        });
                                    }
                                    else {
                                        l.service.updateCertifyGroup(self.convertDataModel()).done(function () {
                                            self.reloadDataByAction(self.certifyGroupModel().code());
                                            self.clearErrorSave();
                                        }).fail(function (error) {
                                            self.showMessageSave(error.message);
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
                                            if (data != null && data.length > 0) {
                                                self.selectCodeLstLstCertifyGroup(data[0].code);
                                                self.detailCertifyGroup(data[0].code);
                                            }
                                            else {
                                                self.newmodeEmptyData();
                                                self.lstCertifyGroup = ko.observableArray([]);
                                            }
                                        }
                                    });
                                };
                                ScreenModel.prototype.newmodeEmptyData = function () {
                                    var self = this;
                                    l.service.findAllCertification().done(function (data) {
                                        self.lstCertification = data;
                                        self.resetValueCertifyGroup();
                                        self.isEmpty(true);
                                        self.certifyGroupModel().setReadOnly(false);
                                    });
                                };
                                ScreenModel.prototype.deleteCertifyGroup = function () {
                                    var self = this;
                                    nts.uk.ui.dialog.confirm("Do you delete Item?").ifYes(function () {
                                        var certifyGroupDeleteDto = new CertifyGroupDeleteDto();
                                        certifyGroupDeleteDto.groupCode = self.certifyGroupModel().code();
                                        certifyGroupDeleteDto.version = 12;
                                        l.service.deleteCertifyGroup(certifyGroupDeleteDto).done(function (data) {
                                            self.reloadDataByAction('');
                                        });
                                    }).ifNo(function () {
                                        self.reloadDataByAction(self.selectCodeLstLstCertifyGroup());
                                    }).ifCancel(function () {
                                        self.reloadDataByAction(self.selectCodeLstLstCertifyGroup());
                                    }).then(function () {
                                        self.reloadDataByAction(self.selectCodeLstLstCertifyGroup());
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
                                ScreenModel.prototype.showMessageSave = function (message) {
                                    $('#btn_saveCertifyGroup').ntsError('set', message);
                                    nts.uk.ui.dialog.alert(message);
                                };
                                ScreenModel.prototype.clearErrorSave = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    $('#btn_saveCertifyGroup').ntsError('clear');
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
                                        { headerText: 'コード', key: 'code', width: 60 },
                                        { headerText: '名称', key: 'name', width: 180 }
                                    ]);
                                    this.selectionMultipleTargetSetting = ko.observableArray([new MultipleTargetSettingDto(MultipleTargetSetting.BigestMethod, "一番高い手当を1つだけ支給する"),
                                        new MultipleTargetSettingDto(MultipleTargetSetting.TotalMethod, "複数該当した金額を加算する")
                                    ]);
                                    this.certifies = ko.observableArray(certifyGroupDto.certifies);
                                    this.lstCertification = ko.observableArray([]);
                                    this.isReadOnly = ko.observable(true);
                                    this.isEnable = ko.observable(false);
                                }
                                CertifyGroupModel.prototype.setLstCertification = function (lstCertification) {
                                    this.lstCertification(lstCertification);
                                };
                                CertifyGroupModel.prototype.setReadOnly = function (readonly) {
                                    this.isReadOnly(readonly);
                                    this.isEnable(!readonly);
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
//# sourceMappingURL=qmm016.l.vm.js.map