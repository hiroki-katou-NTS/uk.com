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
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllCertification = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findAllCertification("CCD1").done(function (data) {
                                        self.lstCertification = ko.observableArray(data);
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllCertifyGroup = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findAllCertifyGroup("CCD1").done(function (data) {
                                        self.lstCertifyGroup = ko.observableArray(data);
                                        self.findCertifyGroup(data[0].code).done(function (data) {
                                            dfd.resolve(self);
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findCertifyGroup = function (code) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findCertifyGroup(code).done(function (data) {
                                        self.certifyGroupModel = ko.observable(new CertifyGroupModel(data));
                                        self.selectCodeLstLstCertifyGroup = ko.observable(code);
                                        l.service.findAllCertification("CCD1").done(function (data) {
                                            self.certifyGroupModel().setLstCertification(data);
                                            dfd.resolve(self);
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.resetValueCertifyGroup = function () {
                                    var self = this;
                                    self.certifyGroupModel().code('');
                                    self.certifyGroupModel().name('');
                                    self.certifyGroupModel().certifies([]);
                                    self.certifyGroupModel().multiApplySet(MultipleTargetSetting.BigestMethod);
                                    self.typeAction(TypeActionCertifyGroup.add);
                                };
                                ScreenModel.prototype.saveCertifyGroup = function () {
                                    var self = this;
                                    if (self.typeAction() == TypeActionCertifyGroup.add) {
                                        l.service.addCertifyGroup(self.convertDataModel());
                                    }
                                    else {
                                        l.service.updateCertifyGroup(self.certifyGroupModel());
                                    }
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
                                    var lstCertifies;
                                    lstCertifies = [];
                                    for (var _i = 0, _a = certifyGroupDto.certifies; _i < _a.length; _i++) {
                                        var itemCertifies = _a[_i];
                                        lstCertifies.push(itemCertifies.code);
                                    }
                                    this.certifies = ko.observableArray(lstCertifies);
                                    this.columnsCertification = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 60 },
                                        { headerText: '名称', prop: 'name', width: 180 }
                                    ]);
                                    this.selectionMultipleTargetSetting = ko.observableArray([new MultipleTargetSettingDto(MultipleTargetSetting.BigestMethod, "BigestMethod"),
                                        new MultipleTargetSettingDto(MultipleTargetSetting.TotalMethod, "TotalMethod")
                                    ]);
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
