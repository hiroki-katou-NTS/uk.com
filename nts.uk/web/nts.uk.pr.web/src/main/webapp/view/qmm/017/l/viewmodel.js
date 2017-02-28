var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var l;
                    (function (l) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.enaleInp4 = ko.observable(false);
                                    self.itemList = ko.observableArray([
                                        new ItemModel('基本給2', '基本給'),
                                        new ItemModel('基本給1', '役職手当'),
                                        new ItemModel('基本給3', '基本給')
                                    ]);
                                    self.itemName = ko.observable('');
                                    self.currentCode = ko.observable(3);
                                    self.isEnable = ko.observable(true);
                                    self.selectedCodes = ko.observableArray([]);
                                    $('#l_lst_001').on('selectionChanging', function (event) {
                                        console.log('Selecting value:' + event.originalEvent.detail);
                                    });
                                    $('#l_lst_001').on('selectionChanged', function (event) {
                                        console.log('Selected value:' + event.originalEvent.detail);
                                    });
                                    self.tabs = ko.observableArray([
                                        { id: 'tab-1', title: '計算式１', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-2', title: '計算式2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-3', title: '計算式3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                                    ]);
                                    self.selectedTab = ko.observable('tab-2');
                                    self.itemListComboSel002 = ko.observableArray([
                                        new ItemModel('0', '固定値'),
                                        new ItemModel('1', '会社単価'),
                                        new ItemModel('2', '個人単価'),
                                        new ItemModel('3', '支給項目'),
                                        new ItemModel('4', '控除項目')
                                    ]);
                                    self.selectedCodeSel002 = ko.observable(0);
                                    self.itemListComboSel003 = ko.observableArray([
                                        new ItemModel('0', '固定値'),
                                        new ItemModel('1', '基準日数'),
                                        new ItemModel('2', '要勤務日数'),
                                        new ItemModel('3', '出勤日数'),
                                        new ItemModel('4', '出勤日数+有給使用数'),
                                        new ItemModel('5', '基準日数*基準時間'),
                                        new ItemModel('6', '要勤務日数*基準時間'),
                                        new ItemModel('7', '出勤日数*基準時間'),
                                        new ItemModel('8', '（出勤日数+有給使用数）*基準時間'),
                                        new ItemModel('9', '出勤時間')
                                    ]);
                                    self.selectedCodeSel003 = ko.observable(0);
                                    self.itemListComboSel005 = ko.observableArray([
                                        new ItemModel('0', '固定値'),
                                        new ItemModel('1', '基準日数'),
                                        new ItemModel('2', '要勤務日数'),
                                        new ItemModel('3', '出勤日数'),
                                        new ItemModel('4', '有給使用数'),
                                        new ItemModel('5', '欠勤日数'),
                                        new ItemModel('6', '出勤日数+有給使用数'),
                                        new ItemModel('7', '出勤時間'),
                                        new ItemModel('8', '遅早回数'),
                                        new ItemModel('9', '遅早時間'),
                                        new ItemModel('10', '遅刻回数'),
                                        new ItemModel('11', '遅刻時間'),
                                        new ItemModel('12', '早退回数'),
                                        new ItemModel('13', '早退時間'),
                                        new ItemModel('14', '勤怠項目')
                                    ]);
                                    self.selectedCodeSel005 = ko.observable(0);
                                    self.itemListComboSel006 = ko.observableArray([
                                        new ItemModel('0', '調整しない'),
                                        new ItemModel('1', 'プラス調整'),
                                        new ItemModel('2', 'マイナス調整')
                                    ]);
                                    self.selectedCodeSel006 = ko.observable(0);
                                    self.itemListComboSel007 = ko.observableArray([
                                        new ItemModel('0', '切上げ'),
                                        new ItemModel('1', '切捨て'),
                                        new ItemModel('2', '一捨二入'),
                                        new ItemModel('3', '二捨三入'),
                                        new ItemModel('4', '三捨四入'),
                                        new ItemModel('5', '四捨五入'),
                                        new ItemModel('6', '五捨六入'),
                                        new ItemModel('7', '六捨七入'),
                                        new ItemModel('8', '七捨八入'),
                                        new ItemModel('9', '八捨九入')
                                    ]);
                                    self.selectedCodeSel007 = ko.observable(0);
                                    self.itemNameCombo = ko.observable('');
                                    self.currentCodeCombo = ko.observable(3);
                                    self.selectedCodeCombo = ko.observable('0003');
                                    self.currencyeditor = {
                                        value: ko.observable(),
                                        constraint: '',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                                            grouplength: 3,
                                            decimallength: 2,
                                            currencyformat: "JPY",
                                            currencyposition: 'right'
                                        })),
                                        required: ko.observable(false),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.currencyeditor2 = {
                                        value: ko.observable(),
                                        constraint: '',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                                            grouplength: 3,
                                            decimallength: 2,
                                            currencyformat: "JPY",
                                            currencyposition: 'right'
                                        })),
                                        required: ko.observable(false),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.roundingRulesSel001 = ko.observableArray([
                                        { code: '0', name: '計算式１' },
                                        { code: '1', name: '計算式２' },
                                        { code: '2', name: '計算式３' }
                                    ]);
                                    self.selectedRuleCodeSel001 = ko.observable(0);
                                    self.roundingRulesSel004 = ko.observableArray([
                                        { code: '0', name: '四捨五入' },
                                        { code: '1', name: '切り上げ' },
                                        { code: '2', name: '切り捨て' }
                                    ]);
                                    self.selectedRuleCodeSel004 = ko.observable(0);
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var ItemModel = (function () {
                                function ItemModel(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return ItemModel;
                            }());
                        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
                    })(l = qmm017.l || (qmm017.l = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
