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
                            class ScreenModel extends ScreenBaseModel {
                                constructor() {
                                    super({
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
                                    // Setting type
                                    self.isContractSettingEnabled = ko.computed(() => {
                                        return self.unitPriceHistoryModel().fixPaySettingType() == SettingType.CONTRACT;
                                    });
                                    // Nts text editor options
                                    self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                        textmode: "text",
                                        placeholder: "",
                                        textalign: "left"
                                    }));
                                }
                                /**
                                 * Override
                                 * Create or Update UnitPriceHistory.
                                 */
                                onSave() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    // Clear errors.
                                    self.clearErrors();
                                    // Validate.
                                    self.validate();
                                    // Return if has error.
                                    if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                                        dfd.reject();
                                        return dfd.promise();
                                    }
                                    if (self.isNewMode()) {
                                        qmm007.service.instance.create(ko.toJS(self.unitPriceHistoryModel())).done(res => {
                                            dfd.resolve(res.uuid);
                                            self.dirtyChecker.reset();
                                        }).fail(res => {
                                            dfd.reject();
                                            self.setMessages(res.messageId);
                                        });
                                    }
                                    else {
                                        qmm007.service.instance.update(ko.toJS(self.unitPriceHistoryModel())).done((res) => {
                                            dfd.resolve(self.unitPriceHistoryModel().id);
                                            self.dirtyChecker.reset();
                                        }).fail(res => {
                                            dfd.reject();
                                            self.setMessages(res.messageId);
                                        });
                                    }
                                    return dfd.promise();
                                }
                                /**
                                 * Override
                                 * Load UnitPriceHistory detail.
                                 */
                                onSelectHistory(id) {
                                    var self = this;
                                    self.isLoading(true);
                                    qmm007.service.instance.findHistoryByUuid(id).done(dto => {
                                        self.setUnitPriceHistoryModel(dto);
                                        self.dirtyChecker.reset();
                                        self.isLoading(false);
                                    });
                                }
                                /**
                                 * Override
                                 * Clear all input and switch to new mode.
                                 */
                                onRegistNew() {
                                    var self = this;
                                    self.clearInput();
                                    self.dirtyChecker.reset();
                                }
                                // Override
                                isDirty() {
                                    var self = this;
                                    return self.dirtyChecker.isDirty();
                                }
                                // Override
                                clearErrors() {
                                    $('#inpCode').ntsError('clear');
                                    $('#inpName').ntsError('clear');
                                    $('#inpStartMonth').ntsError('clear');
                                    $('#inpBudget').ntsError('clear');
                                }
                                setMessages(messageId) {
                                    var self = this;
                                    switch (messageId) {
                                        case 'ER005':
                                            $('#inpCode').ntsError('set', '入力した＊は既に存在しています。\r\n ＊を確認してください。');
                                            break;
                                        case 'ER011':
                                            $('#inpStartMonth').ntsError('set', '対象データがありません。');
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                /**
                                 * Set the UnitPriceHistoryModel
                                 */
                                setUnitPriceHistoryModel(dto) {
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
                                }
                                validate() {
                                    $('#inpCode').ntsEditor('validate');
                                    $('#inpName').ntsEditor('validate');
                                    $('#inpStartMonth').ntsEditor('validate');
                                    $('#inpBudget').ntsEditor('validate');
                                }
                                clearInput() {
                                    var self = this;
                                    self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                                }
                                /**
                                 * Get default history
                                 */
                                getDefaultUnitPriceHistory() {
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
                                }
                            }
                            viewmodel.ScreenModel = ScreenModel;
                            class UnitPriceHistoryModel {
                                constructor(historyDto) {
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
                            }
                            viewmodel.UnitPriceHistoryModel = UnitPriceHistoryModel;
                            class SettingType {
                            }
                            SettingType.COMPANY = 'Company';
                            SettingType.CONTRACT = 'Contract';
                            viewmodel.SettingType = SettingType;
                            class ApplySetting {
                            }
                            ApplySetting.APPLY = 'Apply';
                            ApplySetting.NOTAPPLY = 'NotApply';
                            viewmodel.ApplySetting = ApplySetting;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
