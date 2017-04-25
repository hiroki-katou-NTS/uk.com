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
                var qmm016;
                (function (qmm016) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            function getElementTypeByValue(val) {
                                return _.filter(viewmodel.elementTypes, function (el) {
                                    return el.value == val;
                                })[0];
                            }
                            viewmodel.getElementTypeByValue = getElementTypeByValue;
                            var ScreenModel = (function (_super) {
                                __extends(ScreenModel, _super);
                                function ScreenModel() {
                                    _super.call(this, {
                                        functionName: '賃金テープル',
                                        service: a.service.instance,
                                        removeMasterOnLastHistoryRemove: true
                                    });
                                    this.demensionBullet = { 1: "①", 2: "②", 3: "③" };
                                    var self = this;
                                    self.head = ko.observable(new HeadViewModel());
                                    self.history = ko.observable(new HistoryViewModel());
                                    self.selectedTab = ko.observable('tab-1');
                                    self.tabs = ko.observableArray([
                                        {
                                            id: 'tab-1',
                                            title: '基本情報',
                                            content: '#tab-content-1',
                                            enable: ko.observable(true),
                                            visible: ko.observable(true)
                                        },
                                        {
                                            id: 'tab-2',
                                            title: '賃金テーブルの情報',
                                            content: '#tab-content-2',
                                            enable: ko.computed(function () {
                                                return !self.isNewMode();
                                            }),
                                            visible: ko.observable(true)
                                        }
                                    ]);
                                    self.generalTableTypes = ko.observableArray(a.model.normalDemension);
                                    self.specialTableTypes = ko.observableArray(a.model.specialDemension);
                                    self.valueItems = ko.observable([]);
                                    self.headDirtyChecker = new nts.uk.ui.DirtyChecker(self.head);
                                    self.settingDirtyChecker = new nts.uk.ui.DirtyChecker(self.history().elements);
                                    self.valuesDirtyChecker = new nts.uk.ui.DirtyChecker(self.valueItems);
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.instance.loadElementList().done(function (res) {
                                        viewmodel.elementTypes = res;
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.isDirty = function () {
                                    var self = this;
                                    self.valueItems(self.history().detailViewModel ? self.history().detailViewModel.getCellItem() : []);
                                    return self.headDirtyChecker.isDirty() ||
                                        self.settingDirtyChecker.isDirty() ||
                                        self.valuesDirtyChecker.isDirty();
                                };
                                ScreenModel.prototype.onSelectHistory = function (id) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.instance.loadHistoryByUuid(id).done(function (model) {
                                        $.when(self.head().resetBy(model.head), self.history().resetBy(model.head, model.history)).done(function () {
                                            self.valueItems(self.history().detailViewModel.getCellItem());
                                            self.headDirtyChecker.reset();
                                            self.settingDirtyChecker.reset();
                                            self.valuesDirtyChecker.reset();
                                        });
                                    });
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.validateData = function () {
                                    $("#inp_code").ntsEditor("validate");
                                    $("#inp_name").ntsEditor("validate");
                                    $("#inp_start_date").ntsEditor("validate");
                                    if ($('.nts-editor').ntsError("hasError")) {
                                        return true;
                                    }
                                    return false;
                                };
                                ScreenModel.prototype.clearErrorSave = function () {
                                    $('.save-error').ntsError('clear');
                                };
                                ScreenModel.prototype.onSave = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.clearErrorSave();
                                    if (self.validateData()) {
                                        return dfd.promise();
                                    }
                                    if (self.isNewMode()) {
                                        var wagetableDto = self.head().getWageTableDto();
                                        a.service.instance.initWageTable({
                                            wageTableHeadDto: wagetableDto,
                                            startMonth: self.history().startYearMonth()
                                        }).done(function (res) {
                                            dfd.resolve(res.uuid);
                                            self.valueItems(self.history().detailViewModel.getCellItem());
                                            self.headDirtyChecker.reset();
                                            self.settingDirtyChecker.reset();
                                            self.valuesDirtyChecker.reset();
                                        });
                                    }
                                    else {
                                        a.service.instance.updateHistory({
                                            code: self.head().code(),
                                            name: self.head().name(),
                                            memo: self.head().memo(),
                                            wtHistoryDto: self.history().getWageTableHistoryDto()
                                        }).done(function () {
                                            dfd.resolve(self.history().history.historyId);
                                            self.valueItems(self.history().detailViewModel.getCellItem());
                                            self.headDirtyChecker.reset();
                                            self.settingDirtyChecker.reset();
                                            self.valuesDirtyChecker.reset();
                                        });
                                    }
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.onRegistNew = function () {
                                    var self = this;
                                    self.selectedTab('tab-1');
                                    self.head().reset();
                                    self.headDirtyChecker.reset();
                                    self.settingDirtyChecker.reset();
                                    self.valuesDirtyChecker.reset();
                                };
                                ScreenModel.prototype.btnGroupSettingClick = function () {
                                    var self = this;
                                    var ntsDialogOptions = {
                                        title: '資格グループの設定',
                                        dialogClass: 'no-close'
                                    };
                                    nts.uk.ui.windows.sub.modal('/view/qmm/016/l/index.xhtml', ntsDialogOptions);
                                };
                                ScreenModel.prototype.btnInputFileDownload = function () {
                                    var self = this;
                                    nts.uk.request.exportFile('/screen/pr/qmm016/inputfile', {
                                        code: self.head().code(),
                                        name: self.head().name(),
                                        memo: self.head().memo(),
                                        wtHistoryDto: self.history().getWageTableHistoryDto()
                                    });
                                };
                                return ScreenModel;
                            }(view.base.simplehistory.viewmodel.ScreenBaseModel));
                            viewmodel.ScreenModel = ScreenModel;
                            var HeadViewModel = (function () {
                                function HeadViewModel() {
                                    var self = this;
                                    self.code = ko.observable(undefined);
                                    self.name = ko.observable(undefined);
                                    self.demensionSet = ko.observable(undefined);
                                    self.memo = ko.observable(undefined);
                                    self.demensionType = ko.computed(function () {
                                        return a.model.demensionMap[self.demensionSet()];
                                    });
                                    self.lblContent = ko.computed(function () {
                                        var contentMap = [
                                            '１つの要素でテーブルを作成します。',
                                            '２つの要素でテーブルを作成します。',
                                            '３つの要素でテーブルを作成します。',
                                            '資格手当用のテーブルを作成します。',
                                            '精皆勤手当て用のテーブルを作成します。'
                                        ];
                                        return contentMap[self.demensionSet()];
                                    });
                                    self.lblSampleImgLink = ko.computed(function () {
                                        var linkMap = [
                                            '１.png',
                                            '２.png',
                                            '３.png',
                                            '4.png',
                                            '5.png'
                                        ];
                                        return linkMap[self.demensionSet()];
                                    });
                                    self.demensionItemList = ko.observableArray([]);
                                    self.demensionSet.subscribe(function (val) {
                                        if (!self.isNewMode()) {
                                            return;
                                        }
                                        self.demensionItemList(self.getDemensionItemListByType(val));
                                    });
                                    self.isNewMode = ko.observable(true);
                                    self.reset();
                                }
                                HeadViewModel.prototype.reset = function () {
                                    var self = this;
                                    self.isNewMode(true);
                                    self.code('');
                                    self.name('');
                                    self.demensionSet(a.model.allDemension[0].code);
                                    self.demensionItemList(self.getDemensionItemListByType(self.demensionSet()));
                                    self.memo('');
                                };
                                HeadViewModel.prototype.getWageTableDto = function () {
                                    var self = this;
                                    var dto = {};
                                    dto.code = self.code();
                                    dto.name = self.name();
                                    dto.memo = self.memo();
                                    dto.mode = self.demensionSet();
                                    dto.elements = _.map(self.demensionItemList(), function (item) {
                                        var elementDto = {};
                                        elementDto.demensionName = item.elementName();
                                        elementDto.demensionNo = item.demensionNo();
                                        elementDto.type = item.elementType();
                                        elementDto.referenceCode = item.elementCode();
                                        return elementDto;
                                    });
                                    return dto;
                                };
                                HeadViewModel.prototype.getDemensionItemListByType = function (typeCode) {
                                    var newDemensionItemList = new Array();
                                    switch (typeCode) {
                                        case 0:
                                            newDemensionItemList.push(new DemensionItemViewModel(1));
                                            break;
                                        case 1:
                                            newDemensionItemList.push(new DemensionItemViewModel(1));
                                            newDemensionItemList.push(new DemensionItemViewModel(2));
                                            break;
                                        case 2:
                                            newDemensionItemList.push(new DemensionItemViewModel(1));
                                            newDemensionItemList.push(new DemensionItemViewModel(2));
                                            newDemensionItemList.push(new DemensionItemViewModel(3));
                                            break;
                                        case 3:
                                            {
                                                var cert = new DemensionItemViewModel(1);
                                                cert.elementType(6);
                                                cert.elementName('資格名称');
                                                newDemensionItemList.push(cert);
                                            }
                                            break;
                                        case 4:
                                            {
                                                var workDay = new DemensionItemViewModel(1);
                                                workDay.elementType(7);
                                                workDay.elementName('欠勤日数');
                                                var late = new DemensionItemViewModel(2);
                                                late.elementType(8);
                                                late.elementName('遅刻・早退回数');
                                                var level = new DemensionItemViewModel(3);
                                                level.elementType(9);
                                                level.elementName('レベル');
                                                newDemensionItemList.push(workDay);
                                                newDemensionItemList.push(late);
                                                newDemensionItemList.push(level);
                                            }
                                            break;
                                    }
                                    return newDemensionItemList;
                                };
                                HeadViewModel.prototype.resetBy = function (head) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.isNewMode(false);
                                    self.code(head.code);
                                    self.name(head.name);
                                    self.demensionSet(head.mode);
                                    self.demensionItemList(_.map(head.elements, function (item) {
                                        var itemViewModel = new DemensionItemViewModel(item.demensionNo);
                                        itemViewModel.resetBy(item);
                                        return itemViewModel;
                                    }));
                                    self.memo(head.memo);
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                HeadViewModel.prototype.onSelectDemensionBtnClick = function (demension) {
                                    var self = this;
                                    var dlgOptions = {
                                        selectedDemensionDto: _.map(self.demensionItemList(), function (item) {
                                            var dto = {};
                                            dto.type = item.elementType();
                                            dto.code = item.elementCode();
                                            return dto;
                                        }),
                                        onSelectItem: function (data) {
                                            demension.elementType(data.demension.type);
                                            demension.elementCode(data.demension.code);
                                            demension.elementName(data.demension.name);
                                        }
                                    };
                                    nts.uk.ui.windows.setShared('options', dlgOptions);
                                    var ntsDialogOptions = { title: '要素の選択', dialogClass: 'no-close' };
                                    nts.uk.ui.windows.sub.modal('/view/qmm/016/k/index.xhtml', ntsDialogOptions);
                                };
                                return HeadViewModel;
                            }());
                            viewmodel.HeadViewModel = HeadViewModel;
                            var DemensionItemViewModel = (function () {
                                function DemensionItemViewModel(demensionNo) {
                                    var self = this;
                                    self.demensionNo = ko.observable(demensionNo);
                                    self.elementType = ko.observable(0);
                                    self.elementCode = ko.observable('');
                                    self.elementName = ko.observable('');
                                }
                                DemensionItemViewModel.prototype.resetBy = function (element) {
                                    var self = this;
                                    self.elementType(element.type);
                                    self.elementCode(element.referenceCode);
                                    self.elementName(element.demensionName);
                                };
                                return DemensionItemViewModel;
                            }());
                            viewmodel.DemensionItemViewModel = DemensionItemViewModel;
                            var HistoryViewModel = (function () {
                                function HistoryViewModel() {
                                    var self = this;
                                    self.startYearMonth = ko.observable(parseInt(nts.uk.time.formatDate(new Date(), 'yyyyMM')));
                                    self.endYearMonth = ko.observable(99999);
                                    self.startYearMonthText = ko.computed(function () {
                                        return nts.uk.time.formatYearMonth(self.startYearMonth());
                                    });
                                    self.endYearMonthText = ko.computed(function () {
                                        return nts.uk.time.formatYearMonth(self.endYearMonth());
                                    });
                                    self.startYearMonthJpText = ko.computed(function () {
                                        return nts.uk.text.format('（{0}）', nts.uk.time.yearmonthInJapanEmpire(self.startYearMonth()).toString());
                                    });
                                    self.elements = ko.observableArray([]);
                                }
                                HistoryViewModel.prototype.resetBy = function (head, history) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.history = history;
                                    self.startYearMonth(history.startMonth);
                                    self.endYearMonth(history.endMonth);
                                    var elementSettingViewModel = _.map(history.elements, function (el) {
                                        return new HistoryElementSettingViewModel(head, el);
                                    });
                                    self.elements(elementSettingViewModel);
                                    if ($('#detailContainer').children().length > 0) {
                                        var element = $('#detailContainer').children().get(0);
                                        ko.cleanNode(element);
                                        $('#detailContainer').empty();
                                    }
                                    self.detailViewModel = this.getDetailViewModelByType(head.mode);
                                    $('#detailContainer').load(self.detailViewModel.htmlPath, function () {
                                        var element = $('#detailContainer').children().get(0);
                                        self.detailViewModel.onLoad().done(function () {
                                            ko.applyBindings(self.detailViewModel, element);
                                            dfd.resolve();
                                        });
                                    });
                                    return dfd.promise();
                                };
                                HistoryViewModel.prototype.generateItem = function () {
                                    var _this = this;
                                    var self = this;
                                    a.service.instance.genearetItemSetting({
                                        historyId: self.history.historyId,
                                        settings: self.getElementSettings()
                                    })
                                        .done(function (res) {
                                        if (res.length == null || !_this.validateElementSettingDto(res)) {
                                            nts.uk.ui.dialog.alert("Cann't generate items with the current setting. Please check again!");
                                        }
                                        self.history.elements = res;
                                        self.detailViewModel.refreshElementSettings(res);
                                    }).fail(function (error) {
                                        nts.uk.ui.dialog.alert("Cann't generate items with the current setting. Please check again!");
                                    });
                                };
                                HistoryViewModel.prototype.validateElementSettingDto = function (res) {
                                    if (res == null || res.length <= 0) {
                                        return false;
                                    }
                                    for (var i = 0; i < res.length; i++) {
                                        if (res[i].itemList == null || res[i].itemList.length <= 0) {
                                            return false;
                                        }
                                    }
                                    return true;
                                };
                                HistoryViewModel.prototype.getElementSettings = function () {
                                    var self = this;
                                    return _.map(self.elements(), function (el) {
                                        var dto = {};
                                        dto.type = el.elementType();
                                        dto.demensionNo = el.demensionNo();
                                        dto.upperLimit = el.upperLimit();
                                        dto.lowerLimit = el.lowerLimit();
                                        dto.interval = el.interval();
                                        return dto;
                                    });
                                };
                                HistoryViewModel.prototype.getWageTableHistoryDto = function () {
                                    var self = this;
                                    self.history.valueItems = self.detailViewModel.getCellItem();
                                    return self.history;
                                };
                                HistoryViewModel.prototype.getDetailViewModelByType = function (typeCode) {
                                    var self = this;
                                    switch (typeCode) {
                                        case 0:
                                            return new qmm016.a.history.OneDemensionViewModel(self.history);
                                        case 1:
                                            return new qmm016.a.history.TwoDemensionViewModel(self.history);
                                        case 2:
                                            return new qmm016.a.history.ThreeDemensionViewModel(self.history);
                                        case 3:
                                            return new qmm016.a.history.CertificateViewModel(self.history);
                                        case 4:
                                            return new qmm016.a.history.ThreeDemensionViewModel(self.history);
                                        default:
                                            return new qmm016.a.history.OneDemensionViewModel(self.history);
                                    }
                                };
                                HistoryViewModel.prototype.unapplyBindings = function ($node, remove) {
                                    $node.find("*").each(function () {
                                        $(this).unbind();
                                    });
                                    if (remove) {
                                        ko.removeNode($node[0]);
                                    }
                                    else {
                                        ko.cleanNode($node[0]);
                                    }
                                };
                                return HistoryViewModel;
                            }());
                            viewmodel.HistoryViewModel = HistoryViewModel;
                            var HistoryElementSettingViewModel = (function () {
                                function HistoryElementSettingViewModel(head, element) {
                                    var self = this;
                                    self.demensionNo = ko.observable(element.demensionNo);
                                    self.elementType = ko.observable(0);
                                    self.elementCode = ko.observable('');
                                    self.elementName = ko.observable('');
                                    self.lowerLimit = ko.observable(0);
                                    self.upperLimit = ko.observable(0);
                                    self.interval = ko.observable(0);
                                    self.resetBy(head, element);
                                }
                                HistoryElementSettingViewModel.prototype.resetBy = function (head, element) {
                                    var self = this;
                                    self.elementType(element.type);
                                    var elementDto = _.filter(head.elements, function (el) {
                                        return el.demensionNo == element.demensionNo;
                                    })[0];
                                    self.elementCode(elementDto.referenceCode);
                                    self.elementName(elementDto.demensionName);
                                    self.upperLimit(element.upperLimit);
                                    self.lowerLimit(element.lowerLimit);
                                    self.interval(element.interval);
                                    self.type = _.filter(viewmodel.elementTypes, function (type) {
                                        return type.value == self.elementType();
                                    })[0];
                                };
                                return HistoryElementSettingViewModel;
                            }());
                            viewmodel.HistoryElementSettingViewModel = HistoryElementSettingViewModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm016.a || (qmm016.a = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm016.a.vm.js.map