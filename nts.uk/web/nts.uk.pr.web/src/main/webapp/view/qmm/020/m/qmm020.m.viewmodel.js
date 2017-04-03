var qmm020;
(function (qmm020) {
    var m;
    (function (m) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.histItem = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 50 },
                        { headerText: '名称', prop: 'name', width: 150 },
                        { headerText: '有効期間', prop: 'time', width: 150 }
                    ]);
                    self.currentCode = ko.observable();
                    self.currentCodeList = ko.observable('');
                    self.start();
                }
                ScreenModel.prototype.selectStmtCode = function () {
                    var self = this;
                    var stmtCode = self.currentCodeList();
                    nts.uk.ui.windows.setShared('stmtCodeSelected', stmtCode);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ;
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var currentBaseYM = parseInt(nts.uk.ui.windows.getShared('valMDialog'));
                    m.service.getAllAllotLayoutHist(currentBaseYM).done(function (layoutHistory) {
                        if (layoutHistory.length > 0) {
                            console.log(layoutHistory);
                            var _histItems_1 = [];
                            _.forEach(layoutHistory, function (layoutHist, i) {
                                _histItems_1.push(new HistModel(layoutHist.startYm, layoutHist.endYm, layoutHist.stmtCode));
                            });
                            var _items_1 = [];
                            _.forEach(_histItems_1, function (layoutSelect, i) {
                                if (_histItems_1.length > 0) {
                                    m.service.getAllotLayoutName(layoutSelect.stmtCode).done(function (stmtName) {
                                        _items_1.push(new ItemModel(layoutSelect.stmtCode, stmtName, layoutSelect.startYm + '~' + layoutSelect.endYm));
                                        self.items(_items_1);
                                    }).fail(function (res) {
                                        alert(res);
                                    });
                                }
                            });
                            console.log(_items_1);
                        }
                        else {
                            dfd.resolve();
                        }
                    }).fail(function (res) {
                        alert(res);
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name, time) {
                    this.code = code;
                    this.name = name;
                    this.time = time;
                }
                return ItemModel;
            }());
            var HistModel = (function () {
                function HistModel(startYm, endYm, stmtCode) {
                    this.startYm = startYm;
                    this.endYm = endYm;
                    this.stmtCode = stmtCode;
                }
                return HistModel;
            }());
        })(viewmodel = m.viewmodel || (m.viewmodel = {}));
    })(m = qmm020.m || (qmm020.m = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.m.viewmodel.js.map