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
                                        removeMasterOnLastHistoryRemove: true });
                                    var self = this;
                                    self.isLoading = ko.observable(true);
                                    self.isSelected = ko.observable(false);
                                    self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel(self.getDefaultUnitPriceHistory()));
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
                                        });
                                    }
                                    else {
                                        qmm007.service.instance.update(ko.toJS(self.unitPriceHistoryModel())).done(function (res) {
                                            dfd.resolve(self.unitPriceHistoryModel().id);
                                        });
                                    }
                                    return dfd.promise();
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
                                ScreenModel.prototype.onSelectHistory = function (id) {
                                    var _this = this;
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.isLoading(true);
                                    qmm007.service.instance.findHistoryByUuid(id).done(function (dto) {
                                        self.setUnitPriceHistoryModel(dto);
                                        self.isLoading(false);
                                        nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(_this.unitPriceHistoryModel()));
                                        $('.save-error').ntsError('clear');
                                        self.isSelected(true);
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.onRegistNew = function () {
                                    var self = this;
                                    $('.save-error').ntsError('clear');
                                    self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                                    self.isSelected(false);
                                };
                                ScreenModel.prototype.getDefaultUnitPriceHistory = function () {
                                    var defaultHist = {};
                                    defaultHist.id = '';
                                    defaultHist.unitPriceCode = '';
                                    defaultHist.unitPriceName = '';
                                    defaultHist.startMonth = nts.uk.time.parseTime(new Date()).toValue();
                                    ;
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