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
                                    self.selectedCode = ko.observable(null);
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
                                    self.itemListCombo = ko.observableArray([
                                        new ItemModel('基本給1', '基本給'),
                                        new ItemModel('基本給2', '役職手当'),
                                        new ItemModel('0003', '基本給')
                                    ]);
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
                                    self.roundingRules = ko.observableArray([
                                        { code: '1', name: '四捨五入' },
                                        { code: '2', name: '切り上げ' },
                                        { code: '3', name: '切り捨て' }
                                    ]);
                                    self.selectedRuleCode = ko.observable();
                                    self.roundingRules2 = ko.observableArray([
                                        { code2: '1', name2: '四捨五入' },
                                        { code2: '2', name2: '切り上げ' },
                                        { code2: '3', name2: '切り捨て' }
                                    ]);
                                    self.selectedRuleCode2 = ko.observable();
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
