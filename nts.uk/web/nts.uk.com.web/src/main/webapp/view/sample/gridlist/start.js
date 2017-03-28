var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var gridlist;
            (function (gridlist) {
                __viewContext.ready(function () {
                    var ScreenModel = (function () {
                        function ScreenModel() {
                            this.count = 100;
                            this.items = ko.observableArray([]);
                            for (var i = 1; i < 100; i++) {
                                this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0, "other" + i));
                            }
                            this.columns = ko.observableArray([
                                { headerText: 'コード', key: 'code', width: 100, hidden: true },
                                { headerText: '名称', key: 'name', width: 150, hidden: true },
                                { headerText: '説明', key: 'description', width: 150 },
                                { headerText: '説明1', key: 'other1', width: 150 },
                                { headerText: '説明2', key: 'other2', width: 150 }
                            ]);
                            this.columns2 = ko.observableArray([
                                { headerText: 'コード', key: 'code', width: 100 },
                                { headerText: '名称', key: 'name', width: 150 },
                                { headerText: '説明', key: 'description', width: 150 },
                                { headerText: '説明1', key: 'other1', width: 150 },
                                { headerText: '説明2', key: 'other2', width: 150 }
                            ]);
                            this.currentCode = ko.observable();
                            this.currentCodeList = ko.observableArray([]);
                            $("#multi-list").on('itemDeleted', (function (e) {
                                alert("Item is deleted in multi grid is " + e["detail"]["target"]);
                            }));
                            $("#single-list").on('itemDeleted', (function (e) {
                                alert("Item is deleted in single grid is " + e["detail"]["target"]);
                            }));
                        }
                        ScreenModel.prototype.selectSomeItems = function () {
                            this.currentCode('0010');
                            this.currentCodeList.removeAll();
                            this.currentCodeList.push('001');
                            this.currentCodeList.push('002');
                        };
                        ScreenModel.prototype.deselectAll = function () {
                            this.currentCode(null);
                            this.currentCodeList.removeAll();
                        };
                        ScreenModel.prototype.addItem = function () {
                            this.items.push(new ItemModel(this.count.toString(), '基本給', "description " + this.count, true, "other " + this.count));
                            this.count++;
                        };
                        ScreenModel.prototype.removeItem = function () {
                            this.items.shift();
                        };
                        ScreenModel.prototype.addDeleteButton = function () {
                            $("#multi-list").ntsGridList("setupDeleteButton", { deleteField: "deletable", sourceTarget: this.items });
                        };
                        return ScreenModel;
                    }());
                    var ItemModel = (function () {
                        function ItemModel(code, name, description, deletable, other1, other2) {
                            this.code = code;
                            this.name = name;
                            this.description = description;
                            this.other1 = other1;
                            this.other2 = other2 || other1;
                            this.deletable = deletable;
                        }
                        return ItemModel;
                    }());
                    this.bind(new ScreenModel());
                });
            })(gridlist = ui.gridlist || (ui.gridlist = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=start.js.map