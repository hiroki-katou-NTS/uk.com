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
                            var ScreenModel = (function (_super) {
                                __extends(ScreenModel, _super);
                                function ScreenModel() {
                                    _super.call(this, 'test', qmm016.service.instance);
                                    var self = this;
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
                                return ScreenModel;
                            }(view.base.simplehistory.viewmodel.ScreenBaseModel));
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm016.a || (qmm016.a = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map