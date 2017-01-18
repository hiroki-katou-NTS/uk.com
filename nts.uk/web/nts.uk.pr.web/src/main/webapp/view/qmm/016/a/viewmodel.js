var qmm016;
(function (qmm016) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var WageTableItem = (function () {
                function WageTableItem(code, name, histories) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.histories = histories;
                }
                return WageTableItem;
            }());
            viewmodel.WageTableItem = WageTableItem;
            var WageTableHistoryItem = (function () {
                function WageTableHistoryItem(code, startMonth, endMonth) {
                    var self = this;
                    self.code = code;
                    self.startMonth = startMonth;
                    self.endMonth = endMonth;
                    self.nodeText = self.startMonth + ' ~ ' + self.endMonth;
                }
                return WageTableHistoryItem;
            }());
            viewmodel.WageTableHistoryItem = WageTableHistoryItem;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.wageTableList = ko.observableArray([new WageTableItem('0001', 'あああああ', []),
                        new WageTableItem('0002', '明細書 B', []),
                        new WageTableItem('0003', '資格手当 テーブル', [new WageTableHistoryItem('1', '2015/04', '2016/03')])]);
                    self.tabs = ko.observableArray([
                        { id: 'tab-1', title: '基本情報', content: '#tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-2', title: '賃金テーブルの情報', content: '#tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                    ]);
                    self.generalTableTypes = ko.observableArray([
                        { code: '0', name: '一次元' },
                        { code: '1', name: '二次元' },
                        { code: '2', name: '三次元' }
                    ]);
                    self.specialTableTypes = ko.observableArray([
                        { code: '3', name: '資格' },
                        { code: '4', name: '精皆勤手当' }
                    ]);
                    self.selectedCode = ko.observable('0001');
                    self.selectedTab = ko.observable('tab-1');
                    self.code = ko.observable('');
                    self.name = ko.observable('');
                    self.startMonth = ko.observable('');
                    self.endMonth = ko.observable('9999/12');
                    self.monthRange = ko.computed(function () { return self.startMonth() + " ~ " + self.endMonth(); });
                    self.selectedDimensionName = ko.computed(function () { return '一次元'; });
                    self.memo = ko.observable('');
                    self.selectedDimensionType = ko.observable(0);
                }
                ScreenModel.prototype.goToB = function () {
                    nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { dialogClass: 'no-close', height: 380, width: 400 }).setTitle('å±¥æ­´ã�®è¿½åŠ ');
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm016.a || (qmm016.a = {}));
})(qmm016 || (qmm016 = {}));
