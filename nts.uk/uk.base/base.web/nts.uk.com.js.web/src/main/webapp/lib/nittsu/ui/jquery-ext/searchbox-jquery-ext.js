var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsSearchBox;
                (function (ntsSearchBox) {
                    $.fn.setupSearchScroll = function (controlType, virtualization) {
                        var $control = this;
                        if (controlType.toLowerCase() == 'iggrid')
                            return setupIgGridScroll($control, virtualization);
                        if (controlType.toLowerCase() == 'igtreegrid')
                            return setupTreeGridScroll($control, virtualization);
                        if (controlType.toLowerCase() == 'igtree')
                            return setupIgTreeScroll($control);
                        return this;
                    };
                    function setupIgGridScroll($control, virtualization) {
                        var $grid = $control;
                        if (virtualization) {
                            $grid.on("selectChange", function () {
                                var row = null;
                                var selectedRows = $grid.igGrid("selectedRows");
                                if (selectedRows) {
                                    row = selectedRows[0];
                                }
                                else {
                                    row = $grid.igGrid("selectedRow");
                                }
                                if (row) {
                                    ui.ig.grid.virtual.expose(row, $grid);
                                }
                            });
                        }
                        else {
                            $grid.on("selectChange", function () {
                                var row = null;
                                var selectedRows = $grid.igGrid("selectedRows");
                                if (selectedRows) {
                                    row = selectedRows[0];
                                }
                                else {
                                    row = $grid.igGrid("selectedRow");
                                }
                                if (row) {
                                    ui.ig.grid.expose(row, $grid);
                                }
                            });
                        }
                        return $grid;
                    }
                    function getSelectRowIndex($grid, selectedValue) {
                        var dataSource = $grid.igGrid("option", "dataSource");
                        var primaryKey = $grid.igGrid("option", "primaryKey");
                        return _.findIndex(dataSource, function (s) { return s[primaryKey].toString() === selectedValue.toString(); });
                    }
                    function setupTreeGridScroll($control, virtualization) {
                        var $treegrid = $control;
                        var id = $treegrid.attr('id');
                        $treegrid.on("selectChange", function () {
                            var row = null;
                            var selectedRows = $treegrid.igTreeGridSelection("selectedRows");
                            if (selectedRows) {
                                row = selectedRows[0];
                            }
                            else {
                                row = $treegrid.igTreeGridSelection("selectedRow");
                            }
                            if (row) {
                                ui.ig.tree.grid.expandTo(row.id, $treegrid);
                            }
                        });
                        return $treegrid;
                    }
                    function setupIgTreeScroll($control) {
                        var id = $control.attr('id');
                        $control.on("selectChange", function () {
                            var selectedRows = $control.ntsTreeDrag("getSelected");
                            if ($.isArray(selectedRows)) {
                                selectedRows = selectedRows[0];
                            }
                            if (!nts.uk.util.isNullOrUndefined(selectedRows)) {
                                $control.igTree("expandToNode", selectedRows.element);
                                var index = _.findIndex($control.find("li"), function (e) {
                                    return $(e).is(selectedRows.element);
                                });
                                if (index >= 0) {
                                    var scrollTo_1 = index * 29;
                                    var scrollTop = $control.scrollTop();
                                    var height = $control.height();
                                    if (scrollTo_1 < scrollTop || scrollTo_1 > scrollTop + height - 28) {
                                        $control.scrollTop(scrollTo_1);
                                    }
                                }
                            }
                        });
                        return $control;
                    }
                })(ntsSearchBox || (ntsSearchBox = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=searchbox-jquery-ext.js.map