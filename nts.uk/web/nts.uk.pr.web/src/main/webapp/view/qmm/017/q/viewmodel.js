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
                    var q;
                    (function (q) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.items = ko.observableArray([]);
                                    for (var i = 1; i < 4; i++) {
                                        self.items.push(new ItemModel('00' + i, 0));
                                    }
                                    self.currentCodeList = ko.observableArray([]);
                                    self.bindGridListItem();
                                }
                                ScreenModel.prototype.bindGridListItem = function () {
                                    var self = this;
                                    $("#q_lst_001").igGrid({
                                        primaryKey: "code",
                                        columns: [
                                            { headerText: "計算式の項目名", key: "code", dataType: "string", width: '150px' },
                                            { headerText: "値", key: "value", dataType: "number", width: '150px' },
                                        ],
                                        dataSource: self.items(),
                                        height: "200px",
                                        width: "330px",
                                        features: [
                                            {
                                                name: "Updating",
                                                enableAddRow: false,
                                                editMode: "row",
                                                enableDeleteRow: false,
                                                columnSettings: [
                                                    { columnKey: "code", editorOptions: { type: "string", disabled: true } },
                                                ],
                                                editCellEnding: function (evt, ui) {
                                                    var foundItem = _.find(self.items(), function (item) { return item.code == ui.rowID; });
                                                    (foundItem.code !== ui.value) ? foundItem.value = ui.value : foundItem.code = ui.value;
                                                }
                                            }]
                                    });
                                    $("[aria-describedby='q_lst_001_code']").css({ "backgroundColor": "#CFF1A5" });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = q.viewmodel || (q.viewmodel = {}));
                        var ItemModel = (function () {
                            function ItemModel(code, value) {
                                this.code = code;
                                this.value = value;
                            }
                            return ItemModel;
                        }());
                    })(q = qmm017.q || (qmm017.q = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
