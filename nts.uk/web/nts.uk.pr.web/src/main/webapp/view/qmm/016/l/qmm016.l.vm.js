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
                            class ScreenModel {
                                constructor() {
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
                                    self.messageList = ko.observableArray([
                                        { messageId: "ER001", message: "＊が入力されていません。" },
                                        { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n ＊を確認してください。" },
                                        { messageId: "AL001", message: "変更された内容が登録されていません。\r\n よろしいですか。" },
                                        { messageId: "ER010", message: "対象データがありません。" },
                                        { messageId: "AL002", message: "データを削除します。\r\n よろしいですか？。" }
                                    ]);
                                    self.showDelete = ko.observable(true);
                                    self.certifyGroupModel = ko.observable(new CertifyGroupModel(new CertifyGroupDto()));
                                    self.dirty = new nts.uk.ui.DirtyChecker(self.certifyGroupModel);
                                }
                                //start page init data begin load page
                                startPage() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllCertifyGroup().done(function () {
                                        self.findAllCertification().done(data => {
                                            dfd.resolve(data);
                                        });
                                    });
                                    return dfd.promise();
                                }
                                // find all Certification connection service
                                findAllCertification() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findAllCertification().done(data => {
                                        self.lstCertification = data;
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                }
                                //find all CertifyGroup connection service
                                findAllCertifyGroup() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findAllCertifyGroup().done(data => {
                                        if (data != null && data.length > 0) {
                                            self.lstCertifyGroup = ko.observableArray(data);
                                            self.selectCodeLstLstCertifyGroup(data[0].code);
                                            self.selectCodeLstLstCertifyGroup.subscribe(function (selectionCodeLstLstCertifyGroup) {
                                                self.showchangeCertifyGroup(selectionCodeLstLstCertifyGroup);
                                            });
                                            self.detailCertifyGroup(data[0].code);
                                            dfd.resolve(self);
                                        }
                                        else {
                                            self.newmodeEmptyData();
                                            self.lstCertifyGroup = ko.observableArray([]);
                                            dfd.resolve(self);
                                        }
                                    });
                                    return dfd.promise();
                                }
                                //detail CertifyGroup by code
                                findCertifyGroup(code) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findCertifyGroup(code).done(data => {
                                        self.certifyGroupModel(new CertifyGroupModel(data));
                                        l.service.findAllCertification().done(data => {
                                            self.certifyGroupModel().lstCertification(data);
                                            self.lstCertification = data;
                                            dfd.resolve(self);
                                        });
                                    });
                                    return dfd.promise();
                                }
                                detailCertifyGroup(code) {
                                    if (code && code != '') {
                                        var self = this;
                                        l.service.findCertifyGroup(code).done(data => {
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
                                            self.showDelete(true);
                                            l.service.findAllCertification().done(dataCertification => {
                                                self.certifyGroupModel().setLstCertification(dataCertification);
                                                self.dirty.reset();
                                            }).fail(function () {
                                                self.dirty.reset();
                                            });
                                        });
                                    }
                                }
                                //show CertifyGroup (change event)
                                showchangeCertifyGroup(selectionCodeLstLstCertifyGroup) {
                                    var self = this;
                                    self.detailCertifyGroup(selectionCodeLstLstCertifyGroup);
                                }
                                //reset value => begin add button
                                resetValueCertifyGroup() {
                                    var self = this;
                                    if (self.dirty.isDirty()) {
                                        nts.uk.ui.dialog.confirm(self.messageList()[2].message).ifYes(function () {
                                            self.onResetValueCertifyGroup();
                                        }).ifNo(function () {
                                            //No action
                                        });
                                    }
                                    else {
                                        self.onResetValueCertifyGroup();
                                    }
                                }
                                //reset value => begin add button
                                onResetValueCertifyGroup() {
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
                                    self.showDelete(false);
                                    l.service.findAllCertification().done(data => {
                                        self.certifyGroupModel().lstCertification(data);
                                        self.dirty.reset();
                                    });
                                }
                                saveCertifyGroup() {
                                    var self = this;
                                    self.clearErrorSave();
                                    self.validateData();
                                    if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                                        return;
                                    }
                                    if (self.typeAction() == TypeActionCertifyGroup.add) {
                                        l.service.addCertifyGroup(self.convertDataModel()).done(function () {
                                            self.reloadDataByAction(self.certifyGroupModel().code());
                                        }).fail(function (error) {
                                            self.showMessageSave(error.message);
                                        });
                                    }
                                    else {
                                        l.service.updateCertifyGroup(self.convertDataModel()).done(function () {
                                            self.reloadDataByAction(self.certifyGroupModel().code());
                                            self.clearErrorSave();
                                        }).fail(function (error) {
                                            self.showMessageSave(error.message);
                                        });
                                    }
                                }
                                //reload action
                                reloadDataByAction(code) {
                                    var self = this;
                                    l.service.findAllCertifyGroup().done(data => {
                                        self.lstCertifyGroup(data);
                                        if (code && code != '') {
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
                                }
                                //new mode empty data
                                newmodeEmptyData() {
                                    var self = this;
                                    l.service.findAllCertification().done(data => {
                                        self.lstCertification = data;
                                        self.onResetValueCertifyGroup();
                                        self.isEmpty(true);
                                        self.certifyGroupModel().setReadOnly(false);
                                        self.showDelete(false);
                                    });
                                }
                                deleteCertifyGroup() {
                                    var self = this;
                                    nts.uk.ui.dialog.confirm(self.messageList()[4].message).ifYes(function () {
                                        var certifyGroupDeleteDto = new CertifyGroupDeleteDto();
                                        certifyGroupDeleteDto.groupCode = self.certifyGroupModel().code();
                                        certifyGroupDeleteDto.version = 12;
                                        l.service.deleteCertifyGroup(certifyGroupDeleteDto).done(function () {
                                            self.reloadDataByAction('');
                                        });
                                    }).ifNo(function () {
                                        self.reloadDataByAction(self.selectCodeLstLstCertifyGroup());
                                    }).ifCancel(function () {
                                        self.reloadDataByAction(self.selectCodeLstLstCertifyGroup());
                                    }).then(function () {
                                        self.reloadDataByAction(self.selectCodeLstLstCertifyGroup());
                                    });
                                }
                                closeCertifyGroup() {
                                    nts.uk.ui.windows.close();
                                }
                                //convert data model => Dto
                                convertDataModel() {
                                    var self = this;
                                    var certifyGroupDto = new CertifyGroupDto();
                                    certifyGroupDto.code = self.certifyGroupModel().code();
                                    certifyGroupDto.name = self.certifyGroupModel().name();
                                    certifyGroupDto.multiApplySet = self.certifyGroupModel().multiApplySet();
                                    certifyGroupDto.certifies = self.certifyGroupModel().certifies();
                                    return certifyGroupDto;
                                }
                                //show message by connection service => respone error
                                showMessageSave(messageId) {
                                    var self = this;
                                    if (messageId == self.messageList()[0]) {
                                        if (!self.certifyGroupModel().code()) {
                                            $('#inp_code').ntsError('set', self.messageList()[0].message);
                                        }
                                        if (!self.certifyGroupModel().name()) {
                                            $('#inp_name').ntsError('set', self.messageList()[0].message);
                                        }
                                    }
                                    if (messageId == self.messageList()[1]) {
                                        $('#btn_saveCertifyGroup').ntsError('set', self.messageList()[1].message);
                                    }
                                }
                                //clear error view 
                                clearErrorSave() {
                                    $('.save-error').ntsError('clear');
                                    $('#btn_saveCertifyGroup').ntsError('clear');
                                }
                                //validate client
                                validateData() {
                                    $("#inp_code").ntsEditor("validate");
                                    $("#inp_name").ntsEditor("validate");
                                }
                            }
                            viewmodel.ScreenModel = ScreenModel;
                            class CertifyGroupModel {
                                constructor(certifyGroupDto) {
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
                                setLstCertification(lstCertification) {
                                    this.lstCertification(lstCertification);
                                }
                                setReadOnly(readonly) {
                                    this.isReadOnly(readonly);
                                    this.isEnable(!readonly);
                                }
                            }
                            viewmodel.CertifyGroupModel = CertifyGroupModel;
                        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
                    })(l = qmm016.l || (qmm016.l = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
