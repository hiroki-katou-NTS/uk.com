var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm007;
                (function (qmm007) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenBaseModel = view.base.simplehistory.viewmodel.ScreenBaseModel;
                            var ScreenModel = (function (_super) {
                                __extends(ScreenModel, _super);
                                function ScreenModel() {
                                    _super.call(this, {
                                        functionName: '会社一律金額',
                                        service: qmm007.service.instance,
                                        removeMasterOnLastHistoryRemove: true
                                    });
                                    var self = this;
                                    self.isLoading = ko.observable(true);
                                    self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel(self.getDefaultUnitPriceHistory()));
                                    self.dirtyChecker = new nts.uk.ui.DirtyChecker(self.unitPriceHistoryModel);
                                    self.switchButtonDataSource = ko.observableArray([
                                        { code: ApplySetting.APPLY, name: '対象' },
                                        { code: ApplySetting.NOTAPPLY, name: '対象外' }
                                    ]);
                                    self.isContractSettingEnabled = ko.computed(function () {
                                        return self.unitPriceHistoryModel().fixPaySettingType() == SettingType.CONTRACT;
                                    });
                                    self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                        textmode: "text",
                                        placeholder: "",
                                        textalign: "left"
                                    }));
                                }
                                ScreenModel.prototype.onSave = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if (self.isNewMode()) {
                                        qmm007.service.instance.create(ko.toJS(self.unitPriceHistoryModel())).done(function (res) {
                                            dfd.resolve(res.uuid);
                                        }).fail(function (res) {
                                            self.setMessages(res.messageId);
                                        });
                                    }
                                    else {
                                        qmm007.service.instance.update(ko.toJS(self.unitPriceHistoryModel())).done(function (res) {
                                            dfd.resolve(self.unitPriceHistoryModel().id);
                                        });
                                    }
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.onSelectHistory = function (id) {
                                    var _this = this;
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.isLoading(true);
                                    qmm007.service.instance.findHistoryByUuid(id).done(function (dto) {
                                        self.setUnitPriceHistoryModel(dto);
                                        self.dirtyChecker.reset();
                                        self.isLoading(false);
                                        nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(_this.unitPriceHistoryModel()));
                                        self.clearError();
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.onRegistNew = function () {
                                    var self = this;
                                    self.clearError();
                                    self.clearInput();
                                };
                                ScreenModel.prototype.isDirty = function () {
                                    var self = this;
                                    return self.dirtyChecker.isDirty();
                                };
                                ScreenModel.prototype.setMessages = function (messageId) {
                                    var self = this;
                                    switch (messageId) {
                                        case 'ER001':
                                            if (!self.unitPriceHistoryModel().unitPriceCode()) {
                                                $('#inpCode').ntsError('set', '＊が入力されていません。');
                                            }
                                            if (!self.unitPriceHistoryModel().unitPriceName()) {
                                                $('#inpName').ntsError('set', '＊が入力されていません。');
                                            }
                                            if (!self.unitPriceHistoryModel().budget()) {
                                                $('#inpBudget').ntsError('set', '＊が入力されていません。');
                                            }
                                            break;
                                        case 'ER005':
                                            $('#inpCode').ntsError('set', '入力した＊は既に存在しています。\r\n ＊を確認してください。');
                                            break;
                                        case 'ER011':
                                            $('#inpStartMonth').ntsError('set', '対象データがありません。');
                                            break;
                                        default:
                                            break;
                                    }
                                };
                                ScreenModel.prototype.setUnitPriceHistoryModel = function (dto) {
                                    var model = this.unitPriceHistoryModel();
                                    model.id = dto.id;
                                    model.unitPriceCode(dto.unitPriceCode);
                                    model.unitPriceName(dto.unitPriceName);
                                    model.startMonth(dto.startMonth);
                                    model.endMonth(dto.endMonth);
                                    model.budget(dto.budget);
                                    model.fixPaySettingType(dto.fixPaySettingType);
                                    model.fixPayAtr(dto.fixPayAtr);
                                    model.fixPayAtrMonthly(dto.fixPayAtrMonthly);
                                    model.fixPayAtrDayMonth(dto.fixPayAtrDayMonth);
                                    model.fixPayAtrDaily(dto.fixPayAtrDaily);
                                    model.fixPayAtrHourly(dto.fixPayAtrHourly);
                                    model.memo(dto.memo);
                                };
                                ScreenModel.prototype.clearError = function () {
                                    $('.save-error').ntsError('clear');
                                };
                                ScreenModel.prototype.clearInput = function () {
                                    var self = this;
                                    self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                                };
                                ScreenModel.prototype.getDefaultUnitPriceHistory = function () {
                                    var defaultHist = {};
                                    defaultHist.id = '';
                                    defaultHist.unitPriceCode = '';
                                    defaultHist.unitPriceName = '';
                                    defaultHist.startMonth = parseInt(nts.uk.time.formatDate(new Date(), 'yyyyMM'));
                                    defaultHist.endMonth = 999912;
                                    defaultHist.budget = 0;
                                    defaultHist.fixPaySettingType = SettingType.COMPANY;
                                    defaultHist.fixPayAtr = ApplySetting.APPLY;
                                    defaultHist.fixPayAtrMonthly = ApplySetting.APPLY;
                                    defaultHist.fixPayAtrDayMonth = ApplySetting.APPLY;
                                    defaultHist.fixPayAtrDaily = ApplySetting.APPLY;
                                    defaultHist.fixPayAtrHourly = ApplySetting.APPLY;
                                    defaultHist.memo = '';
                                    return defaultHist;
                                };
                                return ScreenModel;
                            }(ScreenBaseModel));
                            viewmodel.ScreenModel = ScreenModel;
                            var UnitPriceHistoryModel = (function () {
                                function UnitPriceHistoryModel(historyDto) {
                                    this.id = historyDto.id;
                                    this.unitPriceCode = ko.observable(historyDto.unitPriceCode);
                                    this.unitPriceName = ko.observable(historyDto.unitPriceName);
                                    this.startMonth = ko.observable(historyDto.startMonth);
                                    this.endMonth = ko.observable(historyDto.endMonth);
                                    this.budget = ko.observable(historyDto.budget);
                                    this.fixPaySettingType = ko.observable(historyDto.fixPaySettingType);
                                    this.fixPayAtr = ko.observable(historyDto.fixPayAtr);
                                    this.fixPayAtrMonthly = ko.observable(historyDto.fixPayAtrMonthly);
                                    this.fixPayAtrDayMonth = ko.observable(historyDto.fixPayAtrDayMonth);
                                    this.fixPayAtrDaily = ko.observable(historyDto.fixPayAtrDaily);
                                    this.fixPayAtrHourly = ko.observable(historyDto.fixPayAtrHourly);
                                    this.memo = ko.observable(historyDto.memo);
                                }
                                return UnitPriceHistoryModel;
                            }());
                            viewmodel.UnitPriceHistoryModel = UnitPriceHistoryModel;
                            var SettingType = (function () {
                                function SettingType() {
                                }
                                SettingType.COMPANY = 'Company';
                                SettingType.CONTRACT = 'Contract';
                                return SettingType;
                            }());
                            viewmodel.SettingType = SettingType;
                            var ApplySetting = (function () {
                                function ApplySetting() {
                                }
                                ApplySetting.APPLY = 'Apply';
                                ApplySetting.NOTAPPLY = 'NotApply';
                                return ApplySetting;
                            }());
                            viewmodel.ApplySetting = ApplySetting;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm007.a.vm.js.map