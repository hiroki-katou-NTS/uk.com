var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsTreeGridViewBindingHandler = (function () {
                    function NtsTreeGridViewBindingHandler() {
                    }
                    NtsTreeGridViewBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var ROW_HEIGHT = 24;
                        var HEADER_HEIGHT = 24;
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var optionsValue = ko.unwrap(data.primaryKey !== undefined ? data.primaryKey : data.optionsValue);
                        var optionsText = ko.unwrap(data.primaryText !== undefined ? data.primaryText : data.optionsText);
                        var optionsChild = ko.unwrap(data.childDataKey !== undefined ? data.childDataKey : data.optionsChild);
                        var extColumns = ko.unwrap(data.columns !== undefined ? data.columns : data.extColumns);
                        var initialExpandDepth = ko.unwrap(data.initialExpandDepth);
                        var selectedValues = ko.unwrap(data.selectedValues);
                        var singleValue = ko.unwrap(data.value);
                        var rows = ko.unwrap(data.rows);
                        var virtualization = ko.unwrap(!uk.util.isNullOrUndefined(data.virtualization) ? data.virtualization : false);
                        var virtualizationMode = ko.unwrap(!uk.util.isNullOrUndefined(data.virtualizationMode) ? data.virtualizationMode : "");
                        var showCheckBox = data.showCheckBox !== undefined ? ko.unwrap(data.showCheckBox) : true;
                        var enable = data.enable !== undefined ? ko.unwrap(data.enable) : true;
                        var height = ko.unwrap(data.height !== undefined ? data.height : 0);
                        var width = ko.unwrap(data.width !== undefined ? data.width : 0);
                        if (extColumns !== undefined && extColumns !== null) {
                            var displayColumns = extColumns;
                        }
                        else {
                            var displayColumns = [
                                { headerText: "コード", key: optionsValue, dataType: "string", hidden: true },
                                { headerText: "コード／名称", key: optionsText, dataType: "string" }
                            ];
                        }
                        var $treegrid = $(element);
                        var tabIndex = nts.uk.util.isNullOrEmpty($treegrid.attr("tabindex")) ? "0" : $treegrid.attr("tabindex");
                        $treegrid.attr("tabindex", "-1");
                        var features = [];
                        features.push({
                            name: "Selection",
                            multipleSelection: true,
                            activation: true,
                            rowSelectionChanged: function (evt, ui) {
                                var selectedRows = ui.selectedRows;
                                if (ko.unwrap(data.multiple)) {
                                    if (ko.isObservable(data.selectedValues)) {
                                        data.selectedValues(_.map(selectedRows, function (row) {
                                            return row.id;
                                        }));
                                    }
                                }
                                else {
                                    if (ko.isObservable(data.value)) {
                                        var valueX = selectedRows.length <= 0 ? undefined : ui.row.id;
                                        if (data.value() === valueX) {
                                            data.value.valueHasMutated();
                                        }
                                        else {
                                            data.value(selectedRows.length <= 0 ? undefined : ui.row.id);
                                        }
                                    }
                                }
                            }
                        });
                        features.push({
                            name: "RowSelectors",
                            enableCheckBoxes: showCheckBox,
                            rowSelectorColumnWidth: showCheckBox ? 25 : 0,
                            enableRowNumbering: false,
                            checkBoxMode: "biState"
                        });
                        features.push({ name: "Resizing" });
                        if (!nts.uk.util.isNullOrEmpty(rows)) {
                            height = rows * ROW_HEIGHT + HEADER_HEIGHT;
                            var colSettings_1 = [];
                            _.forEach(displayColumns, function (c) {
                                if (c["hidden"] === undefined || c["hidden"] === false) {
                                    colSettings_1.push({ columnKey: c["key"], allowTooltips: true });
                                    if (nts.uk.util.isNullOrEmpty(c["columnCssClass"])) {
                                        c["columnCssClass"] = "text-limited";
                                    }
                                    else {
                                        c["columnCssClass"] += " text-limited";
                                    }
                                }
                            });
                            features.push({
                                name: "Tooltips",
                                columnSettings: colSettings_1,
                                visibility: "overflow",
                                showDelay: 200,
                                hideDelay: 200
                            });
                            $treegrid.addClass("row-limited");
                        }
                        $treegrid.data("expand", new ExpandNodeHolder());
                        $treegrid.data("autoExpanding", false);
                        $treegrid.igTreeGrid({
                            width: width,
                            height: height,
                            indentation: "12px",
                            dataSource: _.cloneDeep(options),
                            primaryKey: optionsValue,
                            columns: displayColumns,
                            childDataKey: optionsChild,
                            initialExpandDepth: nts.uk.util.isNullOrUndefined(initialExpandDepth) ? 10 : initialExpandDepth,
                            tabIndex: -1,
                            features: features,
                            virtualization: virtualization,
                            virtualizationMode: virtualizationMode,
                            rowExpanded: function (evt, ui) {
                                if (!$treegrid.data("autoExpanding")) {
                                    var holder = $treegrid.data("expand");
                                    holder.addNode(ui["dataRecord"][optionsValue]);
                                    $treegrid.data("expand", holder);
                                }
                            }, rowCollapsed: function (evt, ui) {
                                if (!$treegrid.data("autoExpanding")) {
                                    var holder = $treegrid.data("expand");
                                    holder.removeNodeAndChilds(ui["dataRecord"], optionsValue, optionsChild);
                                    $treegrid.data("expand", holder);
                                }
                            }, rowsRendered: function (evt, ui) {
                                $treegrid.data("autoExpanding", true);
                                var holder = $treegrid.data("expand");
                                _.forEach(holder.nodes, function (node) {
                                    $treegrid.igTreeGrid("expandRow", node);
                                });
                                $treegrid.data("autoExpanding", false);
                            }
                        });
                        var treeGridId = $treegrid.attr('id');
                        $treegrid.closest('.ui-igtreegrid').addClass('nts-treegridview').attr("tabindex", tabIndex);
                        $treegrid.bind('selectionchanged', function () {
                            if (data.multiple) {
                                var selected = $treegrid.ntsTreeView('getSelected');
                                if (!nts.uk.util.isNullOrEmpty(selected)) {
                                    data.selectedValues(_.map(selected, function (s) { return s.id; }));
                                }
                                else {
                                    data.selectedValues([]);
                                }
                            }
                            else {
                                var selected = $treegrid.ntsTreeView('getSelected');
                                if (!nts.uk.util.isNullOrEmpty(selected)) {
                                    data.value(selected.id);
                                }
                                else {
                                    data.value('');
                                }
                            }
                        });
                        $treegrid.setupSearchScroll("igTreeGrid");
                    };
                    NtsTreeGridViewBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var selectedValues = ko.unwrap(data.selectedValues);
                        var singleValue = ko.unwrap(data.value);
                        var $treegrid = $(element);
                        var originalSource = $(element).igTreeGrid('option', 'dataSource');
                        if (!_.isEqual(originalSource, options)) {
                            $treegrid.igTreeGrid("option", "dataSource", _.cloneDeep(options));
                            $treegrid.igTreeGrid("dataBind");
                        }
                        var multiple = data.multiple != undefined ? ko.unwrap(data.multiple) : true;
                        if ($treegrid.igTreeGridSelection("option", "multipleSelection") !== multiple) {
                            $treegrid.igTreeGridSelection("option", "multipleSelection", multiple);
                        }
                        var showCheckBox = ko.unwrap(data.showCheckBox != undefined ? data.showCheckBox : true);
                        if ($treegrid.igTreeGridRowSelectors("option", "enableCheckBoxes") !== showCheckBox) {
                            $treegrid.igTreeGridRowSelectors("option", "enableCheckBoxes", showCheckBox);
                        }
                        if ((selectedValues === null || selectedValues === undefined) && (singleValue === null || singleValue === undefined)) {
                            $treegrid.igTreeGridSelection("clearSelection");
                        }
                        else {
                            var uiSR = $treegrid.ntsTreeView('getSelected');
                            if (multiple) {
                                var olds = _.map(uiSR, function (row) {
                                    return row.id;
                                });
                                if (_.isEqual(selectedValues.sort(), olds.sort())) {
                                    return;
                                }
                                $treegrid.igTreeGridSelection("clearSelection");
                                selectedValues.forEach(function (val) {
                                    $treegrid.igTreeGridSelection("selectRowById", val);
                                });
                            }
                            else {
                                if (uiSR !== undefined && uiSR.id === singleValue) {
                                    return;
                                }
                                $treegrid.igTreeGridSelection("clearSelection");
                                $treegrid.igTreeGridSelection("selectRowById", singleValue);
                                ui.ig.tree.grid.expandTo(singleValue, $treegrid);
                            }
                        }
                    };
                    return NtsTreeGridViewBindingHandler;
                }());
                var isEmpty = nts.uk.util.isNullOrEmpty;
                var ExpandNodeHolder = (function () {
                    function ExpandNodeHolder() {
                        this.nodes = [];
                    }
                    ExpandNodeHolder.prototype.isEmpty = function () {
                        return isEmpty(this.nodes);
                    };
                    ExpandNodeHolder.prototype.addNode = function (nodeId) {
                        this.nodes.push(nodeId);
                    };
                    ExpandNodeHolder.prototype.removeNodeAndChilds = function (nodeSource, nodeKey, nodeChildKey) {
                        var ids = Helper.getAllIdFromNodeSource(_.cloneDeep(nodeSource), nodeKey, nodeChildKey);
                        _.remove(this.nodes, function (node) {
                            return ids.indexOf(node) >= 0;
                        });
                    };
                    return ExpandNodeHolder;
                }());
                var Helper;
                (function (Helper) {
                    function getAllIdFromNodeSource(nodeSource, nodeKey, childKey) {
                        var ids = [nodeSource[nodeKey]];
                        var children = [].concat(nodeSource[childKey]);
                        while (!isEmpty(children)) {
                            var currentNode = children.shift();
                            ids.push(currentNode[nodeKey]);
                            if (!isEmpty(currentNode)) {
                                children = children.concat(currentNode[childKey]);
                            }
                        }
                        return ids;
                    }
                    Helper.getAllIdFromNodeSource = getAllIdFromNodeSource;
                    function flatTree(tree, childKey) {
                        var ids = [];
                        _.forEach(tree, function (nodeSource) {
                            ids.push(nodeSource);
                            var children = [].concat(nodeSource[childKey]);
                            while (!isEmpty(children)) {
                                var currentNode = children.shift();
                                ids.push(currentNode);
                                if (!isEmpty(currentNode)) {
                                    children = children.concat(currentNode[childKey]);
                                }
                            }
                        });
                        return ids;
                    }
                    Helper.flatTree = flatTree;
                    function getAllParentId(tree, id, nodeKey, childKey) {
                        var source = _.cloneDeep(tree.igTreeGrid("option", "dataSource"));
                        var parentIds = [];
                        _.forEach(source, function (node) {
                            var result = checkIfInBranch(node, id, nodeKey, childKey);
                            if (result.inThis) {
                                parentIds = [node[nodeKey]].concat(result.ids);
                                return false;
                            }
                        });
                        return parentIds;
                    }
                    Helper.getAllParentId = getAllParentId;
                    function checkIfInBranch(source, id, nodeKey, childKey) {
                        if (source[nodeKey] === id) {
                            return {
                                inThis: true,
                                ids: []
                            };
                        }
                        else {
                            var result_1 = {
                                inThis: false,
                                ids: []
                            };
                            _.forEach(source[childKey], function (node) {
                                result_1 = checkIfInBranch(node, id, nodeKey, childKey);
                                if (result_1.inThis) {
                                    result_1.ids = [node[nodeKey]].concat(result_1.ids);
                                    return false;
                                }
                            });
                            return result_1;
                        }
                    }
                })(Helper || (Helper = {}));
                var ExpandNode = (function () {
                    function ExpandNode(source, nodeKey, childKey, element, nodeLevel) {
                        this.nodeSource = source;
                        this.nodeLevel = nodeLevel;
                        this.element = element;
                        this.nodeKey = nodeKey;
                        this.childKey = childKey;
                    }
                    ExpandNode.prototype.getNode = function () {
                        return this.nodeSource;
                    };
                    return ExpandNode;
                }());
                ko.bindingHandlers['ntsTreeGridView'] = new NtsTreeGridViewBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=treegrid-ko-ext.js.map