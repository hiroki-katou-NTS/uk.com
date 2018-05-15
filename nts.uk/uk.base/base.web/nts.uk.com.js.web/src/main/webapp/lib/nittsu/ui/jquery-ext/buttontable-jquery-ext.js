var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var isNull = nts.uk.util.isNullOrUndefined;
                var isEmpty = nts.uk.util.isNullOrEmpty;
                var ntsButtonTable;
                (function (ntsButtonTable) {
                    $.fn.ntsButtonTable = function (method, option, option2, option3) {
                        var $element = $(this);
                        var builder;
                        switch (method) {
                            case "init": {
                                builder = new TableBuildingConstructor($element, option);
                                builder.startBuildTable();
                                break;
                            }
                            case "dataSource": {
                                builder = $element.data("builder");
                                if (isNull(option) || !$.isArray(option)) {
                                    return builder.getDataSource();
                                }
                                builder.setDataSource(option);
                                builder.drawTable();
                                break;
                            }
                            case "column": {
                                builder = $element.data("builder");
                                if (isNull(option)) {
                                    return builder.column;
                                }
                                if (option !== builder.column) {
                                    builder.setColumn(option);
                                    builder.startBuildTable();
                                }
                                break;
                            }
                            case "row": {
                                builder = $element.data("builder");
                                if (isNull(option)) {
                                    return builder.row;
                                }
                                if (option !== builder.row) {
                                    builder.setRow(option);
                                    builder.drawTable();
                                }
                                break;
                            }
                            case "cellAt": {
                                builder = $element.data("builder");
                                var tbody = builder.container.find("tbody");
                                var rowAt = tbody.find("tr:nth-child(" + (option + 1) + ")");
                                var cellAt = rowAt.find("td:nth-child(" + (option2 + 1) + ")");
                                return {
                                    element: cellAt,
                                    data: cellAt.data("cell-data"),
                                    rowIdx: option,
                                    columnIdx: option2,
                                };
                            }
                            case "setCellValue": {
                                builder = $element.data("builder");
                                var tbody = builder.container.find("tbody");
                                var rowAt = tbody.find("tr:nth-child(" + (option + 1) + ")");
                                var cellAt = rowAt.find("td:nth-child(" + (option2 + 1) + ")");
                                builder.setCellValue(cellAt.find("button"), option3);
                                break;
                            }
                            case "getSelectedCells": {
                                builder = $element.data("builder");
                                var selectedButton = builder.container.find(".ntsButtonCellSelected");
                                return _.map(selectedButton, function (c) {
                                    var button = $(c);
                                    var cell = button.parent();
                                    var rowIdx = parseInt(cell.attr("row-idx"));
                                    var columnIdx = parseInt(cell.attr("column-idx"));
                                    return {
                                        element: cell,
                                        data: cell.data("cell-data"),
                                        rowIdx: rowIdx,
                                        columnIdx: columnIdx,
                                    };
                                });
                            }
                            case "setSelectedCell": {
                                builder = $element.data("builder");
                                var tbody = builder.container.find("tbody");
                                var rowAt = tbody.find("tr:nth-child(" + (option + 1) + ")");
                                var cellAt = rowAt.find("td:nth-child(" + (option2 + 1) + ")");
                                cellAt.find("button").trigger("cellselecting");
                                break;
                            }
                            case "clearSelectedCellAt": {
                                builder = $element.data("builder");
                                var tbody = builder.container.find("tbody");
                                var rowAt = tbody.find("tr:nth-child(" + (option + 1) + ")");
                                var cellAt = rowAt.find("td:nth-child(" + (option2 + 1) + ")");
                                cellAt.find("button").trigger("cellselecting");
                                break;
                            }
                            case "clearAllSelectedCells": {
                                builder = $element.data("builder");
                                this.container.find(".ntsButtonCellSelected").trigger("cellselecting");
                                ;
                                break;
                            }
                            case "getDataCells": {
                                builder = $element.data("builder");
                                var dataButton = builder.container.find(".ntsButtonCellData");
                                return _.map(dataButton, function (c) {
                                    var button = $(c);
                                    var cell = button.parent();
                                    var rowIdx = parseInt(cell.attr("row-idx"));
                                    var columnIdx = parseInt(cell.attr("column-idx"));
                                    return {
                                        element: cell,
                                        data: cell.data("cell-data"),
                                        rowIdx: rowIdx,
                                        columnIdx: columnIdx,
                                    };
                                });
                            }
                            default:
                                break;
                        }
                        $element.data("builder", builder);
                        return;
                    };
                    var TableBuildingConstructor = (function () {
                        function TableBuildingConstructor(container, option) {
                            this.container = container;
                            this.mode = option.mode;
                            this.clickOnAction = option.click;
                            this.row = option.row;
                            this.column = option.column;
                            this.originalSource = _.cloneDeep(option.source);
                            this.source = this.changeSource(option.source);
                            this.id = nts.uk.util.randomId();
                            this.width = option.width;
                            this.disableMenuOnDataNotSet = option.disableMenuOnDataNotSet;
                            this.cloneContextMenu(option.contextMenu);
                        }
                        TableBuildingConstructor.prototype.changeSource = function (origin) {
                            var result = [];
                            for (var rI = 0; rI < this.row; rI++) {
                                result[rI] = [];
                                for (var cI = 0; cI < this.column; cI++) {
                                    var cell = origin[(rI * this.column) + cI];
                                    result[rI][cI] = !isNull(cell) ? _.cloneDeep(cell) : undefined;
                                }
                            }
                            return result;
                        };
                        TableBuildingConstructor.prototype.setDataSource = function (source) {
                            this.originalSource = _.cloneDeep(source);
                            this.source = this.changeSource(source);
                        };
                        TableBuildingConstructor.prototype.getDataSource = function () {
                            return _.cloneDeep(this.originalSource);
                        };
                        TableBuildingConstructor.prototype.setColumn = function (columnSize) {
                            this.column = columnSize;
                        };
                        TableBuildingConstructor.prototype.setRow = function (rowSize) {
                            this.row = rowSize;
                        };
                        TableBuildingConstructor.prototype.cloneContextMenu = function (contextMenu) {
                            var self = this;
                            var menu = _.map(contextMenu, function (m) {
                                var action = function () {
                                    var element = self.container.data("context-opening");
                                    m.action(element, element.parent().data("cell-data")).done(function (result) {
                                        element.trigger("contextmenufinished", result);
                                    });
                                };
                                return new nts.uk.ui.contextmenu.ContextMenuItem(m.id, m.text, action, m.style);
                            });
                            this.contextMenu = new nts.uk.ui.contextmenu.ContextMenu(".menu" + this.id, menu);
                        };
                        TableBuildingConstructor.prototype.startBuildTable = function () {
                            var self = this;
                            self.container.empty();
                            var table = $("<table>", { "class": "ntsButtonTable ntsTable", id: this.id });
                            var tbody = $("<tbody>", { "class": "data-area" });
                            var colgroup = $("<colgroup>", { "class": "col-definition" });
                            for (var i = 0; i < this.column; i++) {
                                var col = $("<col>", { width: isNull(self.width) ? 100 : (self.width / self.column) });
                                col.appendTo(colgroup);
                            }
                            colgroup.appendTo(table);
                            tbody.appendTo(table);
                            table.appendTo(this.container);
                            this.drawTable();
                        };
                        TableBuildingConstructor.prototype.drawTable = function () {
                            var tbody = this.container.find("tbody");
                            tbody.empty();
                            for (var i = 0; i < this.row; i++) {
                                this.buildRow(tbody, i, this.id + "-row-" + i, this.source[i]);
                            }
                        };
                        TableBuildingConstructor.prototype.buildRow = function (container, dataIdx, id, rowData) {
                            var row = $("<tr>", { "class": "ntsRow ntsButtonTableRow", id: id, attr: { "data-idx": dataIdx, "data-id": id } });
                            for (var i = 0; i < this.column; i++) {
                                var idx = dataIdx * this.column + i;
                                this.buildCell(row, dataIdx, idx, id + "-cell-" + idx, isNull(rowData) || isNull(rowData[i]) ? {} : rowData[i], i);
                            }
                            row.appendTo(container);
                        };
                        TableBuildingConstructor.prototype.buildCell = function (container, rowIdx, dataIdx, id, data, columnIdx) {
                            var self = this;
                            var cell = $("<td>", { "class": "ntsCell ntsButtonTableCell", id: id, attr: { "row-idx": rowIdx, "data-idx": dataIdx, "data-id": id, "column-idx": columnIdx } });
                            var contextClass = "menu" + this.id;
                            var button = $("<button>", { "class": "ntsButtonCell ntsButtonTableButton " + contextClass, attr: { "data-idx": dataIdx, "data-id": id } });
                            button.text(isEmpty(data.text) ? "+" : data.text);
                            button.width(isNull(self.width) ? 90 : (self.width / self.column - 10));
                            if (!isEmpty(data.text)) {
                                button.addClass("ntsButtonCellData");
                                button.attr("title", data.tooltip);
                                button.data("empty-cell", false);
                                cell.data("cell-data", _.cloneDeep(data));
                            }
                            else {
                                button.data("empty-cell", true);
                            }
                            button.click(function (evt, ui) {
                                var c = $(this);
                                if (self.mode === "master") {
                                    if (_.isFunction(self.clickOnAction)) {
                                        self.clickOnAction(evt, c.parent().data("cell-data")).done(function (result) {
                                            self.setCellValue(c, result);
                                        });
                                    }
                                }
                                else {
                                    c.trigger("cellselecting");
                                }
                            });
                            button.bind("cellselecting", function (evt, data) {
                                var c = $(this);
                                if (!c.data("empty-cell")) {
                                    if (c.hasClass("ntsButtonCellSelected")) {
                                        c.removeClass("ntsButtonCellSelected");
                                        self.container.trigger("cellselectedchanging", { column: -1, row: -1, data: c.parent().data("cell-data") });
                                    }
                                    else {
                                        self.container.find(".ntsButtonCellSelected").removeClass("ntsButtonCellSelected");
                                        c.addClass("ntsButtonCellSelected");
                                        var oCell = c.parent();
                                        self.container.trigger("cellselectedchanging", { column: parseInt(oCell.attr("column-idx")), row: parseInt(oCell.attr("row-idx")), data: oCell.data("cell-data") });
                                    }
                                }
                                else {
                                    var oldSelected = self.container.find(".ntsButtonCellSelected");
                                    if (!nts.uk.util.isNullOrEmpty(oldSelected)) {
                                        var oCell = oldSelected.parent();
                                        self.container.trigger("cellselectedchanging", { column: parseInt(oCell.attr("column-idx")), row: parseInt(oCell.attr("row-idx")), data: oCell.data("cell-data") });
                                    }
                                    else {
                                        self.container.trigger("cellselectedchanging", { column: -1, row: -1, data: null });
                                    }
                                }
                            });
                            button.contextmenu(function () {
                                var c = $(this);
                                var enable = c.data("empty-cell");
                                if (self.mode === "master") {
                                    self.contextMenu.setEnable(!enable);
                                    if (enable) {
                                        return false;
                                    }
                                }
                                else {
                                    if (!isEmpty(self.disableMenuOnDataNotSet)) {
                                        _.forEach(self.disableMenuOnDataNotSet, function (target) {
                                            self.contextMenu.setEnableItem(!enable, target);
                                        });
                                    }
                                }
                                self.container.data("context-opening", button);
                            });
                            button.bind("contextmenufinished", function (evt, result) {
                                var c = $(this);
                                self.setCellValue(c, result);
                            });
                            button.appendTo(cell);
                            cell.appendTo(container);
                        };
                        TableBuildingConstructor.prototype.setCellValue = function (button, data) {
                            var cell = button.parent();
                            if (!isNull(data) && !isEmpty(data.text)) {
                                cell.data("cell-data", _.cloneDeep(data));
                                button.text(data.text);
                                button.attr("title", data.tooltip);
                                button.addClass("ntsButtonCellData");
                                button.data("empty-cell", false);
                            }
                            else {
                                cell.data("cell-data", null);
                                button.text("+");
                                button.removeAttr("title");
                                button.removeClass("ntsButtonCellData");
                                button.data("empty-cell", true);
                                data = {};
                            }
                            var rowIdx = parseInt(cell.attr("row-idx"));
                            var columnIdx = parseInt(cell.attr("column-idx"));
                            if (nts.uk.util.isNullOrUndefined(this.source[rowIdx])) {
                                this.source[rowIdx] = [];
                            }
                            this.source[rowIdx][columnIdx] = data;
                            this.updateOriginalSource();
                        };
                        TableBuildingConstructor.prototype.updateOriginalSource = function () {
                            this.originalSource = this.toFlatSource();
                            this.container.trigger("sourcechanging", { source: this.cloneSource() });
                        };
                        TableBuildingConstructor.prototype.toFlatSource = function () {
                            var result = [];
                            _.forEach(this.source, function (row) {
                                _.forEach(row, function (cell) {
                                    result.push(_.cloneDeep(cell));
                                });
                            });
                            return result;
                        };
                        TableBuildingConstructor.prototype.cloneSource = function () {
                            return this.getDataSource();
                        };
                        return TableBuildingConstructor;
                    }());
                    var TableButtonEntity = (function () {
                        function TableButtonEntity(rowId, columnId, viewText, tooltipText) {
                            this.rowId = rowId;
                            this.columnId = columnId;
                            this.viewText = viewText;
                            this.tooltipText = tooltipText;
                        }
                        TableButtonEntity.prototype.setRowId = function (rowId) {
                            this.rowId = rowId;
                        };
                        TableButtonEntity.prototype.setColumnId = function (columnId) {
                            this.columnId = columnId;
                        };
                        TableButtonEntity.prototype.setViewText = function (viewText) {
                            this.viewText = viewText;
                        };
                        TableButtonEntity.prototype.setTooltipText = function (tooltipText) {
                            this.tooltipText = tooltipText;
                        };
                        TableButtonEntity.prototype.getRowId = function () {
                            return this.rowId;
                        };
                        TableButtonEntity.prototype.getColumnId = function () {
                            return this.columnId;
                        };
                        TableButtonEntity.prototype.getViewText = function (rowId) {
                            return this.viewText;
                        };
                        TableButtonEntity.prototype.getTooltipText = function () {
                            return this.tooltipText;
                        };
                        return TableButtonEntity;
                    }());
                    ntsButtonTable.TableButtonEntity = TableButtonEntity;
                })(ntsButtonTable || (ntsButtonTable = {}));
            })(jqueryExtentions = ui_1.jqueryExtentions || (ui_1.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=buttontable-jquery-ext.js.map