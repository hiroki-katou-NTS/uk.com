var qmm020;
(function (qmm020) {
    var m;
    (function (m) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.listItemSelected = ko.observable('');
                    this.listItemDataSources = ko.observableArray([]);
                    this.listItemColumns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 50 },
                        { headerText: '名称', prop: 'name', width: 175 },
                        { headerText: '有効期間', prop: 'time', width: 175 }
                    ]);
                    var self = this, currentBaseYM = parseInt(nts.uk.ui.windows.getShared('M_BASEYM')) || 190701;
                    if (!!currentBaseYM) {
                        m.service.getData(currentBaseYM)
                            .done(function (resp) {
                            if (resp.length == 2) {
                                var _items_1 = [];
                                _.forEach(resp[0], function (item, i) {
                                    _items_1.push(new ItemModel({ code: item.stmtCode, name: resp[1][i], time: item.startYm + '~' + item.endYm }));
                                });
                                self.listItemDataSources(_items_1);
                            }
                        }).fail(function (res) {
                            alert(res);
                        });
                    }
                }
                //event when click to 選択 Button
                ScreenModel.prototype.selectStmtCode = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('M_RETURN', self.listItemSelected());
                    self.closeDialog();
                };
                //event when close dialog
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ;
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(param) {
                    this.code = param.code;
                    this.name = param.name;
                    this.time = param.time;
                }
                return ItemModel;
            }());
        })(viewmodel = m.viewmodel || (m.viewmodel = {}));
    })(m = qmm020.m || (qmm020.m = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.m.viewmodel.js.map