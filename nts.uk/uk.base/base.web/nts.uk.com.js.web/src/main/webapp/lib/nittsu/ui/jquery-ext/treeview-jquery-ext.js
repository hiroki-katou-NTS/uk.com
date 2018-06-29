var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsTreeView;
                (function (ntsTreeView) {
                    var OUTSIDE_AUTO_SCROLL_SPEED = {
                        RATIO: 0.2,
                        MAX: 30
                    };
                    $.fn.ntsTreeView = function (action, param) {
                        var $tree = $(this);
                        switch (action) {
                            case 'getSelected':
                                return getSelected($tree);
                            case 'setSelected':
                                return setSelected($tree, param);
                            case 'deselectAll':
                                return deselectAll($tree);
                            case 'virtualScrollTo':
                                return virtualScroll($tree, param);
                        }
                    };
                    function getSelected($tree) {
                        if ($tree.igTreeGridSelection('option', 'multipleSelection')) {
                            var selectedRows = $tree.igTreeGridSelection('selectedRows');
                            if (selectedRows)
                                return _.map(selectedRows, convertSelected);
                            return [];
                        }
                        else {
                            var selectedRow = $tree.igTreeGridSelection('selectedRow');
                            if (selectedRow)
                                return convertSelected(selectedRow);
                            return undefined;
                        }
                    }
                    function convertSelected(selectedRow) {
                        return {
                            id: selectedRow.id,
                            index: selectedRow.index
                        };
                    }
                    function setSelected($tree, selectedId) {
                        deselectAll($tree);
                        if ($tree.igTreeGridSelection('option', 'multipleSelection')) {
                            selectedId.forEach(function (id) {
                                $tree.igTreeGridSelection('selectRowById', id);
                                virtualScroll($tree, id);
                            });
                        }
                        else {
                            $tree.igTreeGridSelection('selectRowById', selectedId);
                            virtualScroll($tree, id);
                        }
                    }
                    function virtualScroll($tree, id) {
                        var virtualization = $tree.igTreeGrid("option", "virtualization");
                        if (virtualization) {
                            var pk = $tree.igTreeGrid("option", "primaryKey");
                            var childKey = $tree.igTreeGrid("option", "childDataKey");
                            var ds = $tree.igTreeGrid("option", "dataSource");
                            var res = findIndex(ds, id, pk, childKey, 0);
                            if (res.found) {
                                $tree.igTreeGrid("virtualScrollTo", res.index);
                            }
                        }
                    }
                    function findIndex(dataSource, id, pk, childKey, cIndex) {
                        var found = false;
                        _.forEach(dataSource, function (d) {
                            if (d[pk] !== id && d[childKey]) {
                                cIndex++;
                                var res = findIndex(d[childKey], id, pk, childKey, cIndex);
                                if (res.found) {
                                    found = true;
                                    cIndex = res.index;
                                    return false;
                                }
                                cIndex = res.index;
                            }
                            else if (d[pk] === id) {
                                found = true;
                                return false;
                            }
                        });
                        return { index: cIndex, found: found };
                    }
                    function deselectAll($grid) {
                        $grid.igTreeGridSelection('clearSelection');
                    }
                })(ntsTreeView || (ntsTreeView = {}));
                var ntsTreeDrag;
                (function (ntsTreeDrag) {
                    $.fn.ntsTreeDrag = function (action, param) {
                        var $tree = $(this);
                        switch (action) {
                            case 'getSelected':
                                return getSelected($tree);
                            case 'setSelected':
                                return setSelected($tree, param);
                            case 'deselectAll':
                                return deselectAll($tree);
                            case 'isMulti':
                                return isMultiple($tree);
                        }
                    };
                    function isMultiple($tree) {
                        var isMulti = $tree.igTree("option", "checkboxMode") !== "off";
                        return isMulti;
                    }
                    function getSelected($tree) {
                        var isMulti = isMultiple($tree);
                        if (isMulti) {
                            var values = $tree.igTree("checkedNodes");
                            _.forEach(values, function (e) {
                                return e["id"] = e.data[e.binding.valueKey];
                            });
                            return values;
                        }
                        else {
                            var value = $tree.igTree("selectedNode");
                            value["id"] = value.data[value.binding.valueKey];
                            return value;
                        }
                    }
                    function setSelected($tree, selectedId) {
                        deselectAll($tree);
                        var isMulti = isMultiple($tree);
                        if (isMulti) {
                            if (!$.isArray(selectedId)) {
                                selectedId = [selectedId];
                            }
                            selectedId.forEach(function (id) {
                                var $node = $tree.igTree("nodesByValue", id);
                                $tree.igTree("toggleCheckstate", $node);
                            });
                        }
                        else {
                            var $node = $tree.igTree("nodesByValue", selectedId);
                            $tree.igTree("select", $node);
                        }
                    }
                    function deselectAll($tree) {
                        _.forEach($tree.igTree("checkedNodes"), function (node) {
                            $tree.igTree("toggleCheckstate", node.element);
                        });
                    }
                })(ntsTreeDrag || (ntsTreeDrag = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=treeview-jquery-ext.js.map