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
                        var MultipleTargetSettingDto = l.service.model.MultipleTargetSettingDto;
                        var MultipleTargetSetting = l.service.model.MultipleTargetSetting;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.columnsCertification = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 60 },
                                        { headerText: '名称', prop: 'name', width: 180 }
                                    ]);
                                    self.columnsLstCertifyGroup = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 120 },
                                        { headerText: '名称', prop: 'name', width: 120 }
                                    ]);
                                    self.currentCode = ko.observable();
                                    self.currentCodeList = ko.observableArray([]);
                                    self.selectionMultipleTargetSetting = ko.observableArray([new MultipleTargetSettingDto(MultipleTargetSetting.BigestMethod, "BigestMethod"),
                                        new MultipleTargetSettingDto(MultipleTargetSetting.TotalMethod, "TotalMethod")
                                    ]);
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
                                }
                                ScreenModel.prototype.readFromSocialTnsuranceOffice = function () {
                                    var self = this;
                                    self.enableButton(false);
                                    nts.uk.ui.windows.sub.modal("/view/qmm/010/b/index.xhtml", { height: 800, width: 500, title: "社会保険事業所から読み込み" }).onClosed(function () {
                                        self.enableButton(true);
                                    });
                                };
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllCertification().done(function (data) {
                                        self.findAllCertifyGroup().done(function (data) {
                                            dfd.resolve(self);
                                        });
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
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.lefttorightCertification = function () {
                                    var self = this;
                                    var selectLstCodeLstCertification;
                                    var lstCertification;
                                    var lstCertificationInfo;
                                    lstCertification = self.lstCertification();
                                    lstCertificationInfo = self.lstCertificationInfo();
                                    selectLstCodeLstCertification = self.selectLstCodeLstCertification();
                                    if (selectLstCodeLstCertification != null && selectLstCodeLstCertification != undefined) {
                                        for (var _i = 0, selectLstCodeLstCertification_1 = selectLstCodeLstCertification; _i < selectLstCodeLstCertification_1.length; _i++) {
                                            var itemSelectLstCodeLstCertification = selectLstCodeLstCertification_1[_i];
                                            var indexDelete = 0;
                                            for (var _a = 0, lstCertification_1 = lstCertification; _a < lstCertification_1.length; _a++) {
                                                var itemDelete = lstCertification_1[_a];
                                                indexDelete++;
                                                if (itemDelete.code === itemSelectLstCodeLstCertification) {
                                                    lstCertificationInfo.push(itemDelete);
                                                    indexDelete--;
                                                    break;
                                                }
                                            }
                                            lstCertification.splice(indexDelete, 1);
                                        }
                                        self.lstCertification(lstCertification);
                                        self.lstCertificationInfo(lstCertificationInfo);
                                    }
                                };
                                ScreenModel.prototype.righttoleftCertification = function () {
                                    var self = this;
                                    var selectLstCodeLstCertificationInfo;
                                    var lstCertification;
                                    var lstCertificationInfo;
                                    lstCertification = self.lstCertification();
                                    lstCertificationInfo = self.lstCertificationInfo();
                                    selectLstCodeLstCertificationInfo = self.selectLstCodeLstCertificationInfo();
                                    if (selectLstCodeLstCertificationInfo != null && selectLstCodeLstCertificationInfo != undefined) {
                                        for (var _i = 0, selectLstCodeLstCertificationInfo_1 = selectLstCodeLstCertificationInfo; _i < selectLstCodeLstCertificationInfo_1.length; _i++) {
                                            var itemSelectLstCodeLstCertification = selectLstCodeLstCertificationInfo_1[_i];
                                            var indexDelete = 0;
                                            for (var _a = 0, lstCertificationInfo_1 = lstCertificationInfo; _a < lstCertificationInfo_1.length; _a++) {
                                                var itemDelete = lstCertificationInfo_1[_a];
                                                indexDelete++;
                                                if (itemDelete.code === itemSelectLstCodeLstCertification) {
                                                    lstCertification.push(itemDelete);
                                                    indexDelete--;
                                                    break;
                                                }
                                            }
                                            lstCertificationInfo.splice(indexDelete, 1);
                                        }
                                        self.lstCertification(lstCertification);
                                        self.lstCertificationInfo(lstCertificationInfo);
                                        self.selectLstCodeLstCertificationInfo([]);
                                        self.selectLstCodeLstCertification([]);
                                    }
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
                    })(l = qmm016.l || (qmm016.l = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
