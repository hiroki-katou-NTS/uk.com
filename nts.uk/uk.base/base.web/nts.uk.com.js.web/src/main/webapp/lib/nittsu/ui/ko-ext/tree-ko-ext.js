var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsTreeDragAndDropBindingHandler = (function () {
                    function NtsTreeDragAndDropBindingHandler() {
                    }
                    NtsTreeDragAndDropBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var ROW_HEIGHT = 30;
                        var HEADER_HEIGHT = 24;
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var optionsValue = ko.unwrap(data.primaryKey !== undefined ? data.primaryKey : data.optionsValue);
                        var optionsText = ko.unwrap(data.primaryText !== undefined ? data.primaryText : data.optionsText);
                        var optionsChild = ko.unwrap(data.childDataKey !== undefined ? data.childDataKey : data.optionsChild);
                        var multiple = data.multiple != undefined ? ko.unwrap(data.multiple) : false;
                        var selectedValues = ko.unwrap(data.selectedValues);
                        var singleValue = ko.unwrap(data.value);
                        var rows = ko.unwrap(data.rows);
                        var selectOnParent = data.selectOnParent != undefined ? ko.unwrap(data.selectOnParent) : false;
                        var allowOtherTreeNode = data.receiveOtherTreeNode !== undefined ? ko.unwrap(data.receiveOtherTreeNode) : false;
                        var enable = data.enable !== undefined ? ko.unwrap(data.enable) : true;
                        var height = ko.unwrap(data.height !== undefined ? data.height : 0);
                        var width = ko.unwrap(data.width !== undefined ? data.width : 0);
                        var maxDeepLeaf = ko.unwrap(data.maxDeepLeaf !== undefined ? data.maxDeepLeaf : 10);
                        var maxChildInNode = ko.unwrap(data.maxChildInNode !== undefined ? data.maxChildInNode : 999);
                        var customValidate = data.customValidate;
                        if (!nts.uk.util.isNullOrEmpty(rows)) {
                            height = rows * ROW_HEIGHT;
                        }
                        var $tree = $(element);
                        $tree.igTree({
                            width: width,
                            height: height,
                            dataSource: _.cloneDeep(options),
                            initialExpandDepth: 0,
                            tabIndex: -1,
                            checkboxMode: !multiple ? "off" : selectOnParent ? "triState" : "biState",
                            singleBranchExpand: false,
                            pathSeparator: '_',
                            bindings: {
                                textKey: optionsText,
                                valueKey: optionsValue,
                                childDataProperty: optionsChild
                            },
                            dragAndDrop: true,
                            dragAndDropSettings: {
                                allowDrop: allowOtherTreeNode,
                                customDropValidation: function (element) {
                                    var dragInfor = $tree.data("dragInfor");
                                    var mousePosition = $tree.data("mousePosition");
                                    var droppableNode = $(this);
                                    if (droppableNode.prop("tagName").toLowerCase() !== "li") {
                                        droppableNode = droppableNode.closest("li");
                                    }
                                    var isOutTarget = mousePosition.top < droppableNode.offset().top
                                        || mousePosition.top > droppableNode.offset().top + droppableNode.height();
                                    var dragParent = $tree.igTree("parentNode", element);
                                    var targetParent = $tree.igTree("parentNode", droppableNode);
                                    var targetNode = $tree.igTree("nodeFromElement", droppableNode);
                                    if (!isOutTarget) {
                                        if (!nts.uk.util.isNullOrEmpty(targetNode.path)) {
                                            var targetDeep = (targetNode.path.match(/_/g) || []).length;
                                            if (targetDeep + 1 >= maxDeepLeaf) {
                                                return false;
                                            }
                                        }
                                        else {
                                            return false;
                                        }
                                        var targetNodeChildren = $tree.igTree("children", droppableNode);
                                        if (targetNodeChildren.length >= maxChildInNode) {
                                            return false;
                                        }
                                    }
                                    else if (targetParent !== null && !targetParent.is(dragParent)) {
                                        targetNode = $tree.igTree("nodeFromElement", targetParent);
                                        if (!nts.uk.util.isNullOrEmpty(targetNode.path)) {
                                            var targetDeep = (targetNode.path.match(/_/g) || []).length;
                                            if (targetDeep + 1 >= maxDeepLeaf) {
                                                return false;
                                            }
                                        }
                                        else {
                                            return false;
                                        }
                                        var targetNodeChildren = $tree.igTree("children", targetParent);
                                        if (targetNodeChildren.length >= maxChildInNode) {
                                            return false;
                                        }
                                    }
                                    var customValidateResult = nts.uk.util.isNullOrUndefined(customValidate) ? true : customValidate();
                                    if (customValidateResult === false) {
                                        return false;
                                    }
                                    return true;
                                }
                            },
                            dragStart: function (evt, ui) {
                                $tree.data("dragInfor", {
                                    helper: ui.helper,
                                    targetNodePath: ui.path,
                                    mousePosition: ui.position
                                });
                            },
                            selectionChanged: function (evt, ui) {
                                if (ko.unwrap(data.multiple)) {
                                    if (ko.isObservable(data.selectedValues)) {
                                        var selectedNodes = $tree.igTree("checkedNodes");
                                        var checkedNodes = _.map(selectedNodes, function (s) {
                                            return s.data[optionsValue];
                                        });
                                        if (ui.selectedNodes.length > 0) {
                                            checkedNodes.push(ui.selectedNodes[0].data[optionsValue]);
                                        }
                                        data.selectedValues(_.uniq(checkedNodes));
                                    }
                                }
                                else {
                                    if (ko.isObservable(data.value)) {
                                        var selectedRows = ui.selectedNodes;
                                        data.value(selectedRows.length <= 0 ? undefined : selectedRows[0].data[optionsValue]);
                                    }
                                }
                            },
                            nodeCheckstateChanged: function (evt, ui) {
                                if (ko.isObservable(data.selectedValues)) {
                                    if (ko.isObservable(data.selectedValues)) {
                                        var selectedNodes = $tree.igTree("checkedNodes");
                                        data.selectedValues(_.map(selectedNodes, function (s) {
                                            return s.data[optionsValue];
                                        }));
                                    }
                                }
                            }
                        });
                        $tree.mousemove(function (event) {
                            var pageCoords = { top: event.pageY, left: event.pageX };
                            $tree.data("mousePosition", pageCoords);
                        });
                        $tree.setupSearchScroll("igTree");
                    };
                    NtsTreeDragAndDropBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var selectedValues = ko.unwrap(data.selectedValues);
                        var singleValue = ko.unwrap(data.value);
                        var $tree = $(element);
                        var multiple = data.multiple != undefined ? ko.unwrap(data.multiple) : false;
                        var originalSource = $tree.igTree('option', 'dataSource').__ds;
                        if (!_.isEqual(originalSource, options)) {
                            $tree.igTree("option", "dataSource", _.cloneDeep(options));
                            $tree.igTree("dataBind");
                        }
                        if (nts.uk.util.isNullOrEmpty(selectedValues) && nts.uk.util.isNullOrUndefined(singleValue)) {
                            $tree.ntsTreeDrag("deselectAll");
                            $tree.find("a").removeClass("ui-state-active");
                        }
                        else {
                            if (multiple) {
                                $tree.find("a").removeClass("ui-state-active");
                                selectedValues.forEach(function (val) {
                                    var $node = $tree.igTree("nodesByValue", val);
                                    $node.find("a:first").addClass("ui-state-active");
                                    var $checkbox = $node.find("span[data-role=checkbox]:first").find(".ui-icon-check");
                                    if ($tree.igTree("checkState", $node) === "off") {
                                        $tree.igTree("toggleCheckstate", $node);
                                    }
                                });
                            }
                            else {
                                $tree.igTree("select", $tree.igTree("nodesByValue", singleValue));
                            }
                        }
                    };
                    return NtsTreeDragAndDropBindingHandler;
                }());
                ko.bindingHandlers['ntsTreeDragAndDrop'] = new NtsTreeDragAndDropBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=tree-ko-ext.js.map