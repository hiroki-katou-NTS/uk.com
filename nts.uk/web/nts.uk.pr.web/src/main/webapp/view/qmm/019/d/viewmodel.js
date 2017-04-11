var qmm019;
(function (qmm019) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.selectLayoutAtr = ko.observable("3");
                    self.itemList = ko.observableArray([]);
                    self.isEnable = ko.observable(true);
                    self.layouts = ko.observableArray([]);
                    self.historys = ko.observableArray([]);
                    self.selectStmtCode = ko.observable(null);
                    self.selectStmtName = ko.observable(null);
                    self.selectStartYm = ko.observable(null);
                    self.layoutSelect = ko.observable(nts.uk.ui.windows.getShared('stmtCode'));
                    self.valueSel001 = ko.observable("");
                    self.createlayout = ko.observable(null);
                    self.startYmHis = ko.observable(null);
                    console.log(option);
                    self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
                    self.itemsRadio = ko.observableArray([
                        { value: 1, text: ko.observable('最新の履歴（' + self.selectStartYm() + '）から引き継ぐ') },
                        { value: 2, text: ko.observable('初めから作成する') }
                    ]);
                    self.isRadioCheck = ko.observable(1);
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.buildItemList();
                    $('#LST_001').on('selectionChanging', function (event) {
                        console.log('Selecting value:' + event.originalEvent.detail);
                    });
                    $('#LST_001').on('selectionChanged', function (event) {
                        console.log('Selected value:' + event.originalEvent.detail);
                    });
                    d.service.getLayoutWithMaxStartYm().done(function (layout) {
                        self.layouts(layout);
                        d.service.getHistoryWithMaxStart().done(function (layoutHistory) {
                            if (layoutHistory.length > 0) {
                                self.historys(layoutHistory);
                                console.log(layoutHistory);
                            }
                        });
                        self.startDialog();
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.buildItemList = function () {
                    var self = this;
                    self.itemList.removeAll();
                    self.itemList.push(new ItemModel('0', 'レーザープリンタ', 'Ａ４', '縦向き', '1人', '最大　30行ｘ9別まで設定可能', ''));
                    self.itemList.push(new ItemModel('1', 'レーザープリンタ', 'Ａ４', '縦向き', '最大2人', '最大　17行ｘ9別まで設定可能', ''));
                    self.itemList.push(new ItemModel('2', 'レーザープリンタ', 'Ａ４', '縦向き', '最大3人', '最大　10行ｘ9別まで設定可能', ''));
                    self.itemList.push(new ItemModel('3', 'レーザープリンタ', 'Ａ４', '横向き', '最大2人', '最大　10行ｘ9別まで設定可能', ''));
                    self.itemList.push(new ItemModel('4', 'レーザー（圧着式）', 'Ａ４', '縦向き', '1人', '最大　17行ｘ9別まで設定可能', '圧着式：　Ｚ折り'));
                    self.itemList.push(new ItemModel('5', 'レーザー（圧着式）', 'Ａ４', '横向き', '1人', '支給、控除、勤怠各52項目', '圧着式：　はがき'));
                    self.itemList.push(new ItemModel('6', 'ドットプリンタ', '連続用紙', '―', '1人', '支給、控除、勤怠各27項目', ''));
                };
                ScreenModel.prototype.startDialog = function () {
                    var self = this;
                    _.forEach(self.layouts(), function (layout) {
                        var stmtCode = layout.stmtCode;
                        if (stmtCode == self.layoutSelect()) {
                            self.selectStmtCode(stmtCode);
                            self.selectStmtName(layout.stmtName);
                            self.selectStartYm(nts.uk.time.formatYearMonth(layout.startYm));
                            self.itemsRadio()[0].text('最新の履歴（' + self.selectStartYm() + '）から引き継ぐ');
                            self.startYmHis(layout.startYm);
                            return false;
                        }
                    });
                };
                ScreenModel.prototype.createHistoryLayout = function () {
                    var self = this;
                    var inputYm = $('#INP_001').val();
                    if (!nts.uk.time.parseYearMonth(inputYm).success) {
                        alert(nts.uk.time.parseYearMonth(inputYm).msg);
                        return false;
                    }
                    var selectYm = self.startYmHis();
                    inputYm = inputYm.replace('/', '');
                    if (+inputYm < +selectYm
                        || +inputYm == +selectYm) {
                        alert('履歴の期間が正しくありません。');
                        return false;
                    }
                    else {
                        self.createData();
                        if (self.isRadioCheck() === 1) {
                            self.createlayout().checkContinue = true;
                        }
                        else {
                            self.createlayout().checkContinue = false;
                        }
                        d.service.createLayoutHistory(self.createlayout()).done(function () {
                            nts.uk.ui.windows.close();
                        }).fail(function (res) {
                            alert(res);
                            nts.uk.ui.windows.close();
                        });
                    }
                };
                ScreenModel.prototype.createData = function () {
                    var self = this;
                    var start = +$('#INP_001').val().replace('/', '');
                    self.createlayout({
                        checkContinue: false,
                        stmtCode: self.selectStmtCode(),
                        startYm: start,
                        endYm: start,
                        startPrevious: self.startYmHis(),
                        layoutAtr: 3,
                        stmtName: ""
                    });
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(stt, printType, paperType, direction, numberPeople, numberDisplayItems, reference) {
                    this.stt = stt;
                    this.printType = printType;
                    this.paperType = paperType;
                    this.direction = direction;
                    this.numberPeople = numberPeople;
                    this.numberDisplayItems = numberDisplayItems;
                    this.reference = reference;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            var ItemCombobox = (function () {
                function ItemCombobox(layoutCode, layoutName) {
                    this.layoutCode = layoutCode;
                    this.layoutName = layoutName;
                }
                return ItemCombobox;
            }());
            viewmodel.ItemCombobox = ItemCombobox;
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm019.d || (qmm019.d = {}));
})(qmm019 || (qmm019 = {}));
//# sourceMappingURL=viewmodel.js.map