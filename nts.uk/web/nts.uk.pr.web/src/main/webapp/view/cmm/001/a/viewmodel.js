var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var cmm001;
                (function (cmm001) {
                    var a;
                    (function (a) {
                        var ScreenModel = (function () {
                            function ScreenModel() {
                                var self = this;
                                self.init();
                                self.currentCode.subscribe(function (newValue) {
                                    self.getObj(self.items(), newValue);
                                });
                            }
                            ScreenModel.prototype.getObj = function (items, newValue) {
                                var self = this;
                                var node;
                                _.find(items, function (obj) {
                                    if (!node) {
                                        if (obj.code == newValue) {
                                            node = obj;
                                            self.currentNode(node);
                                            console.log(self.currentNode());
                                        }
                                    }
                                });
                            };
                            ScreenModel.prototype.resetData = function () {
                                var self = this;
                                self.currentCode('');
                                self.currentNode(new Company("", "", ""));
                            };
                            ScreenModel.prototype.init = function () {
                                var self = this;
                                self.items = ko.observableArray([
                                    new Company('01', '日通システム株式会社', '<i class="icon icon-close"></i>'),
                                    new Company('02', '有限会社日通ベトナム', ''),
                                    new Company('03', 'UKシステム株式会社', ''),
                                    new Company('99', '○○株式会社', ''),
                                    new Company('', '', ''),
                                    new Company('', '', ''),
                                    new Company('', '', ''),
                                    new Company('', '', ''),
                                    new Company('', '', ''),
                                    new Company('', '', ''),
                                    new Company('', '', ''),
                                    new Company('', '', '')
                                ]);
                                self.columns2 = ko.observableArray([
                                    { headerText: '会社コード', prop: 'code', width: 80 },
                                    { headerText: '名称', prop: 'name', width: 200 },
                                    { headerText: '廃止', prop: 'description', width: 50 }
                                ]);
                                self.currentCode = ko.observable("01");
                                self.currentCodeList = ko.observableArray(null);
                                self.currentNode = ko.observable(new Company("11", "22", "33"));
                                //tabpanel
                                self.tabs = ko.observableArray([
                                    { id: 'tab-1', title: '会社基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                    { id: 'tab-2', title: '会社所在地・連絡先', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                    { id: 'tab-3', title: 'システム設定', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                                ]);
                                self.selectedTab = ko.observable('tab-1');
                                //SWITCH
                                self.roundingRules = ko.observableArray([
                                    { code: '1', name: '区別する' },
                                    { code: '2', name: '区別しない' }
                                ]);
                                self.selectedRuleCode = ko.observable(2);
                                self.roundingRule1s = ko.observableArray([
                                    { code: '1', name: '利用する' },
                                    { code: '2', name: '利用しない' }
                                ]);
                                self.selectedRuleCode1 = ko.observable(1);
                                //COMBOX 
                                self.itemList = ko.observableArray([
                                    new Company('1', '1月', ''),
                                    new Company('2', '2月', ''),
                                    new Company('3', '3月', ''),
                                    new Company('4', '4月', ''),
                                    new Company('5', '5月', ''),
                                    new Company('6', '6月', ''),
                                    new Company('7', '7月', ''),
                                    new Company('8', '8月', ''),
                                    new Company('9', '9月', ''),
                                    new Company('10', '10月', '')
                                ]);
                                self.itemName = ko.observable('');
                                self.currentCodeCombox = ko.observable(4);
                                self.selectedCode = ko.observable('4');
                                self.isEnable = ko.observable(true);
                                self.isEditable = ko.observable(true);
                            };
                            return ScreenModel;
                        }());
                        a.ScreenModel = ScreenModel;
                        var Company = (function () {
                            function Company(code, name, description) {
                                this.code = code;
                                this.name = name;
                                this.description = description;
                            }
                            return Company;
                        }());
                        a.Company = Company;
                    })(a = cmm001.a || (cmm001.a = {}));
                })(cmm001 = view.cmm001 || (view.cmm001 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
;
