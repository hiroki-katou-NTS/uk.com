var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var e;
                    (function (e) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.listOptions = ko.observableArray([new optionsModel(1, "Ã¦Å“â‚¬Ã¦â€“Â°Ã£ï¿½Â®Ã¥Â±Â¥Ã¦Â­Â´(2016/04)Ã£ï¿½â€¹Ã£â€šâ€°Ã¥Â¼â€¢Ã£ï¿½ï¿½Ã§Â¶â„¢Ã£ï¿½ï¿½"), new optionsModel(2, "Ã¥Ë†ï¿½Ã£â€šï¿½Ã£ï¿½â€¹Ã£â€šâ€°Ã¤Â½Å“Ã¦Ë†ï¿½Ã£ï¿½â„¢Ã£â€šâ€¹")]);
                                    self.selectedValue = ko.observable(new optionsModel(1, ""));
                                    self.modalValue = ko.observable("Goodbye world!");
                                    self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                                    self.dataSource = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                                        new Node('0003', 'Bangkok Thailand', []),
                                        new Node('0004', 'Tokyo Japan', []),
                                        new Node('0005', 'Jakarta Indonesia', []),
                                        new Node('0002', 'Seoul Korea', []),
                                        new Node('0006', 'Paris France', []),
                                        new Node('0007', 'United States', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])]),
                                        new Node('0010', 'Beijing China', []),
                                        new Node('0011', 'London United Kingdom', []),
                                        new Node('0012', '', [])]);
                                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "childs"));
                                    self.singleSelectedCode = ko.observable(null);
                                    self.selectedCodes = ko.observableArray([]);
                                    self.index = 0;
                                    self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
                                    self.tabs = ko.observableArray([
                                        { id: 'tab-1', title: 'åŸºæœ¬æƒ…å ±', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-2', title: 'ä¿�é™ºãƒžã‚¹ã‚¿ã�®æƒ…å ±', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                    ]);
                                    self.selectedTab = ko.observable('tab-1');
                                }
                                ScreenModel.prototype.CloseModalSubWindow = function () {
                                    nts.uk.ui.windows.setShared("addHistoryChildValue", this.modalValue(), this.isTransistReturnData());
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var optionsModel = (function () {
                                function optionsModel(id, name) {
                                    var self = this;
                                    self.id = id;
                                    self.name = name;
                                }
                                return optionsModel;
                            }());
                            viewmodel.optionsModel = optionsModel;
                            var Node = (function () {
                                function Node(code, name, childs) {
                                    var self = this;
                                    self.code = code;
                                    self.name = name;
                                    self.nodeText = self.code + ' ' + self.name;
                                    self.childs = childs;
                                    self.custom = 'Random' + new Date().getTime();
                                }
                                return Node;
                            }());
                            viewmodel.Node = Node;
                        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
                    })(e = qmm008.e || (qmm008.e = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
