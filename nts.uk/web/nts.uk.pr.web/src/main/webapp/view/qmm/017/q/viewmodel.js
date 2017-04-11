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
                                function ScreenModel(data) {
                                    var self = this;
                                    self.items = ko.observableArray([]);
                                    self.currentCodeList = ko.observableArray([]);
                                    self.formulaContent = data.formulaContent;
                                    self.buildListItemModel(data.itemsBag);
                                    self.bindGridListItem();
                                }
                                ScreenModel.prototype.isDuplicated = function (itemName) {
                                    var self = this;
                                    var foundItem = _.find(self.items(), function (item) {
                                        return item.name == itemName;
                                    });
                                    return foundItem !== undefined;
                                };
                                ScreenModel.prototype.buildListItemModel = function (itemsBag) {
                                    var self = this;
                                    _.forEach(itemsBag, function (item) {
                                        if (item.name.indexOf('関数') === -1 && self.formulaContent.indexOf(item.name) !== -1 && !self.isDuplicated(item.name)) {
                                            self.items.push(new ItemModel(item.name, 0));
                                        }
                                    });
                                };
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
                                ScreenModel.prototype.calculationTrial = function () {
                                    var self = this;
                                    var replacedValue = '';
                                    _.forEach(self.items(), function (item) {
                                        replacedValue = self.formulaContent.replace(item.code, item.value);
                                    });
                                    console.log(replacedValue);
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
