var qmm019;
(function (qmm019) {
    var g;
    (function (g) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
                    var self = this;
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observable("3");
                    self.layouts = ko.observableArray([]);
                    self.itemList = ko.observableArray([]);
                    self.comboboxList = ko.observableArray([]);
                    self.selectLayoutCode = ko.observable("");
                    self.layoutAtrStr = ko.observable(null);
                    self.selectStmtCode = ko.observable(null);
                    self.selectStmtName = ko.observable(null);
                    self.selectStartYm = ko.observable(null);
                    console.log(option);
                    self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
                    self.createlayout = ko.observable(null);
                    self.createNewSelect = ko.observable(null);
                    self.startYmCopy = ko.observable(null);
                }
                // start function
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
                    //combobox
                    g.service.getLayoutWithMaxStartYm().done(function (layout) {
                        self.layouts(layout);
                        self.buildCombobox();
                    });
                    //change combobox
                    self.selectLayoutCode.subscribe(function (newValue) {
                        self.selectLayoutCode(newValue);
                        self.createNewSelect(newValue);
                        self.buildComboboxChange(newValue);
                    });
                    $('#INP_001').focus();
                    dfd.resolve();
                    // Return.
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
                ScreenModel.prototype.buildCombobox = function () {
                    var self = this;
                    self.comboboxList.removeAll();
                    _.forEach(self.layouts(), function (layout) {
                        var stmtCode = layout.stmtCode;
                        self.comboboxList.push(new ItemCombobox(stmtCode, layout.stmtName));
                    });
                };
                ScreenModel.prototype.buildComboboxChange = function (layoutCd) {
                    var self = this;
                    _.forEach(self.layouts(), function (layout) {
                        if (layout.stmtCode == layoutCd) {
                            self.startYmCopy(layout.startYm);
                            self.selectStartYm(nts.uk.time.formatYearMonth(layout.startYm));
                            return false;
                        }
                    });
                };
                ScreenModel.prototype.createNewLayout = function () {
                    var self = this;
                    if (self.checkData()) {
                        self.createData();
                        if ($("#newCreate").is(":checked")) {
                            self.createlayout().checkCopy = false;
                        }
                        else {
                            if (self.layouts().length === 0) {
                                return false;
                            }
                            //印字行数　＞　最大印字行数　の場合
                            //メッセージ( ER030 )を表示する
                            self.createlayout().checkCopy = true;
                        }
                        g.service.createLayout(self.createlayout()).done(function () {
                            //alert('追加しました。');    
                            nts.uk.ui.windows.close();
                        }).fail(function (res) {
                            alert(res);
                        });
                    }
                };
                ScreenModel.prototype.checkData = function () {
                    var self = this;
                    //登録時チェック処理
                    var mess = "が入力されていません。";
                    //必須項目の未入力チェックを行う
                    if ($('#INP_001').val() == '') {
                        alert("コード" + mess);
                        $('#INP_001').focus();
                        return false;
                    }
                    if ($('#INP_002').val() == '') {
                        alert('名称' + mess);
                        $('#INP_002').focus();
                        return false;
                    }
                    if ($('INP_003').val() == '') {
                        alert('開始年月' + mess);
                        $('#INP_003').focus();
                        return false;
                    }
                    //
                    if (isNaN($('#INP_001').val())) {
                        alert('コードを確認してください。');
                        $('#INP_001').focus();
                        return false;
                    }
                    //check YM
                    var Ym = $('#INP_003').val();
                    if (!nts.uk.time.parseYearMonth(Ym).success) {
                        alert(nts.uk.time.parseYearMonth(Ym).msg);
                        return false;
                    }
                    //コードの重複チェックを行う
                    var isStorage = false;
                    var stmtCd = $('#INP_001').val().trim();
                    if (stmtCd.length < 2)
                        stmtCd = '0' + stmtCd;
                    _.forEach(self.layouts(), function (layout) {
                        if (stmtCd == layout.stmtCode) {
                            alert('入力した' + stmtCd + 'は既に存在しています。\r\n' + stmtCd + 'を確認してください。');
                            $('#INP_001').val(stmtCd);
                            $('#INP_001').focus();
                            isStorage = true;
                            return false;
                        }
                    });
                    if (isStorage) {
                        return false;
                    }
                    return true;
                };
                ScreenModel.prototype.createData = function () {
                    var self = this;
                    var stmtCd = $('#INP_001').val().trim();
                    if (stmtCd.length < 2)
                        stmtCd = '0' + stmtCd;
                    self.createlayout({
                        checkCopy: true,
                        stmtCodeCopied: self.createNewSelect(),
                        startYmCopied: +self.startYmCopy,
                        stmtCode: stmtCd,
                        startYm: +$('#INP_003').val().replace('/', ''),
                        layoutAtr: 3,
                        stmtName: $('#INP_002').val() + "",
                        endYm: 999912
                    });
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            /**
         * Class Item model.
         */
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
        })(viewmodel = g.viewmodel || (g.viewmodel = {}));
    })(g = qmm019.g || (qmm019.g = {}));
})(qmm019 || (qmm019 = {}));
