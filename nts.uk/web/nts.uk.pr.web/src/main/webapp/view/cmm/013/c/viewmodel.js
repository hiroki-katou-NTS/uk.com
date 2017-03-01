var cmm013;
(function (cmm013) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
                    var self = this;
                    self.selectLayoutAtr = ko.observable("3");
                    self.itemList = ko.observableArray([]);
                    self.isEnable = ko.observable(true);
                    self.layouts = ko.observableArray([]);
                    self.selectStmtCode = ko.observable(null);
                    self.selectStmtName = ko.observable(null);
                    self.selectStartYm = ko.observable(null);
                    self.layoutSelect = ko.observable(nts.uk.ui.windows.getShared('stmtCode'));
                    self.valueSel001 = ko.observable("");
                    self.createlayout = ko.observable(null);
                    self.startYmHis = ko.observable(null);
                    console.log(option);
                    self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
                    //---radio
                    self.itemsRadio = ko.observableArray([
                        { value: 1, text: ko.observable('最新の履歴（' + self.selectStartYm() + '）から引き継ぐ') },
                        { value: 2, text: ko.observable('初めから作成する') }
                    ]);
                    self.isRadioCheck = ko.observable(1);
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    //list data
                    $('#LST_001').on('selectionChanging', function (event) {
                        console.log('Selecting value:' + event.originalEvent.detail);
                    });
                    $('#LST_001').on('selectionChanged', function (event) {
                        console.log('Selected value:' + event.originalEvent.detail);
                    });
                    //fill data to dialog
                    service.getLayoutWithMaxStartYm().done(function (layout) {
                        self.layouts(layout);
                        self.startDialog();
                    });
                    dfd.resolve();
                    // Return.
                    return dfd.promise();
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
                    //check YM
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
                        service.createLayoutHistory(self.createlayout()).done(function () {
                            //alert("追加しました。"); 
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
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = cmm013.c || (cmm013.c = {}));
})(cmm013 || (cmm013 = {}));
