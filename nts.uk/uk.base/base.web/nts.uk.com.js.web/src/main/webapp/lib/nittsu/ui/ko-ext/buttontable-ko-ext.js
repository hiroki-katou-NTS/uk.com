var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsTableButtonBindingHandler = (function () {
                    function NtsTableButtonBindingHandler() {
                    }
                    NtsTableButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var source = ko.unwrap(data.source);
                        var mode = (data.mode !== undefined) ? ko.unwrap(data.mode) : "normal";
                        var row = (data.row !== undefined) ? ko.unwrap(data.row) : 1;
                        var column = (data.column !== undefined) ? ko.unwrap(data.column) : 1;
                        var contextMenu = (data.contextMenu !== undefined) ? ko.unwrap(data.contextMenu) : [];
                        var disableMenuOnDataNotSet = (data.disableMenuOnDataNotSet !== undefined) ? ko.unwrap(data.disableMenuOnDataNotSet) : [];
                        var selectedCell = ko.unwrap(data.selectedCell);
                        var width = (data.width !== undefined) ? ko.unwrap(data.width) : 400;
                        var clickAction = data.click;
                        var selectedCells = ko.unwrap(data.selectedCells);
                        $(element).ntsButtonTable("init", {
                            mode: mode,
                            click: clickAction,
                            row: row,
                            column: column,
                            source: source,
                            width: width,
                            disableMenuOnDataNotSet: disableMenuOnDataNotSet,
                            contextMenu: contextMenu
                        });
                        $(element).bind("cellselectedchanging", function (evt, value) {
                            if (!nts.uk.util.isNullOrUndefined(data.selectedCell)) {
                                $(element).data("o-selected", _.cloneDeep(value));
                                data.selectedCell(value);
                            }
                        });
                        $(element).bind("sourcechanging", function (evt, value) {
                            if (!nts.uk.util.isNullOrUndefined(data.source)) {
                                data.source(value.source);
                            }
                        });
                    };
                    NtsTableButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var source = ko.unwrap(data.source);
                        var row = (data.row !== undefined) ? ko.unwrap(data.row) : 1;
                        var column = (data.column !== undefined) ? ko.unwrap(data.column) : 1;
                        var selectedCell = ko.unwrap(data.selectedCell);
                        var container = $(element);
                        var oldSource = container.ntsButtonTable("dataSource");
                        if (!_.isEqual(oldSource, source)) {
                            container.ntsButtonTable("dataSource", source);
                        }
                        container.ntsButtonTable("row", row);
                        container.ntsButtonTable("column", column);
                        if (!nts.uk.util.isNullOrUndefined(selectedCell) && !nts.uk.util.isNullOrUndefined(selectedCell.column)
                            && !nts.uk.util.isNullOrUndefined(selectedCell.row) && !_.isEqual(container.data("o-selected"), selectedCell)) {
                            container.ntsButtonTable("setSelectedCell", selectedCell.row, selectedCell.column);
                        }
                        container.data("o-selected", _.cloneDeep(selectedCell));
                    };
                    return NtsTableButtonBindingHandler;
                }());
                ko.bindingHandlers['ntsTableButton'] = new NtsTableButtonBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=buttontable-ko-ext.js.map