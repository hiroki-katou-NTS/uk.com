var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsGridList;
                (function (ntsGridList) {
                    var OUTSIDE_AUTO_SCROLL_SPEED = {
                        RATIO: 0.2,
                        MAX: 30
                    };
                    $.fn.ntsGridListFeature = function (feature, action) {
                        var params = [];
                        for (var _i = 2; _i < arguments.length; _i++) {
                            params[_i - 2] = arguments[_i];
                        }
                        var $grid = $(this);
                        switch (feature) {
                            case 'switch':
                                switch (action) {
                                    case 'setValue':
                                        return setSwitchValue($grid, params);
                                }
                        }
                    };
                    function setSwitchValue($grid) {
                        var params = [];
                        for (var _i = 1; _i < arguments.length; _i++) {
                            params[_i - 1] = arguments[_i];
                        }
                        var rowId = params[0][0];
                        var columnKey = params[0][1];
                        var selectedValue = params[0][2];
                        var $row = $($grid.igGrid("rowById", rowId));
                        var $parent = $row.find(".ntsControl");
                        var currentSelect = $parent.attr('data-value');
                        if (selectedValue !== currentSelect) {
                            var rowKey = $row.attr("data-id");
                            $parent.find(".nts-switch-button").removeClass("selected");
                            var element = _.find($parent.find(".nts-switch-button"), function (e) {
                                return selectedValue.toString() === $(e).attr('data-value').toString();
                            });
                            if (element !== undefined) {
                                var scrollTop_1 = $("#" + $grid.attr("id") + "_scrollContainer").scrollTop();
                                $(element).addClass('selected');
                                $parent.attr('data-value', selectedValue);
                                $grid.igGridUpdating("setCellValue", rowKey, columnKey, selectedValue);
                                $grid.igGrid("commit");
                                $grid.trigger("switchvaluechanged", { columnKey: columnKey, rowKey: rowKey, value: parseInt(selectedValue) });
                                if ($grid.igGrid("hasVerticalScrollbar")) {
                                    if (!nts.uk.util.isNullOrUndefined(scrollTop_1) && scrollTop_1 !== 0) {
                                        setTimeout(function () {
                                            $("#" + $grid.attr("id") + "_scrollContainer").scrollTop(scrollTop_1);
                                        }, 10);
                                    }
                                }
                            }
                        }
                    }
                    $.fn.ntsGridList = function (action, param) {
                        var $grid = $(this);
                        switch (action) {
                            case 'setupSelecting':
                                return setupSelecting($grid);
                            case 'unsetupSelecting':
                                return unsetupSelecting($grid);
                            case 'getSelected':
                                return getSelected($grid);
                            case 'setSelected':
                                return setSelected($grid, param);
                            case 'deselectAll':
                                return deselectAll($grid);
                            case 'setupDeleteButton':
                                return setupDeleteButton($grid, param);
                            case 'setupScrollWhenBinding':
                                return setupScrollWhenBinding($grid);
                        }
                    };
                    function setupScrollWhenBinding($grid) {
                        var gridId = "#" + $grid.attr("id");
                        $(document).delegate(gridId, "iggriddatarendered", function (evt, ui) {
                            var oldSelected = getSelectRow($grid);
                            if (!nts.uk.util.isNullOrEmpty(oldSelected)) {
                                _.defer(function () {
                                    var selected = getSelectRow($grid);
                                    if (!nts.uk.util.isNullOrEmpty(selected)) {
                                        selected = oldSelected;
                                    }
                                    if ($grid.data('igGrid')) {
                                        var $scrollContainer_1 = $grid.igGrid("scrollContainer");
                                        _.defer(function () {
                                            if ($scrollContainer_1.length > 0) {
                                                var firstRowOffset = $($("#single-list").igGrid("rowAt", 0)).offset().top;
                                                var selectRowOffset = $($("#single-list").igGrid("rowAt", index)).offset().top;
                                                $scrollContainer_1.scrollTop(selectRowOffset - firstRowOffset);
                                            }
                                            else if (selected && oldSelected) {
                                                var index = $(selected["element"]).attr("data-row-idx");
                                                $grid.igGrid("virtualScrollTo", nts.uk.util.isNullOrEmpty(index) ? oldSelected.index : parseInt(index));
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                    function getSelectRow($grid) {
                        var row = null;
                        if ($grid.data("igGrid")) {
                            var selectedRows = $grid.igGrid("selectedRows");
                            if (selectedRows) {
                                row = selectedRows[0];
                            }
                            else {
                                row = $grid.igGrid("selectedRow");
                            }
                        }
                        return row;
                    }
                    function getSelected($grid) {
                        if ($grid.igGridSelection('option', 'multipleSelection')) {
                            var selectedRows = $grid.igGridSelection('selectedRows');
                            if (selectedRows)
                                return _.map(selectedRows, convertSelected);
                            return [];
                        }
                        else {
                            var selectedRow = $grid.igGridSelection('selectedRow');
                            if (selectedRow)
                                return convertSelected(selectedRow);
                            return undefined;
                        }
                    }
                    function convertSelected(igGridSelectedRow) {
                        return {
                            id: igGridSelectedRow.id,
                            index: igGridSelectedRow.index
                        };
                    }
                    function setSelected($grid, selectedId) {
                        deselectAll($grid);
                        if ($grid.igGridSelection('option', 'multipleSelection')) {
                            selectedId.forEach(function (id) { return $grid.igGridSelection('selectRowById', id); });
                        }
                        else {
                            $grid.igGridSelection('selectRowById', selectedId);
                        }
                    }
                    function deselectAll($grid) {
                        $grid.igGridSelection('clearSelection');
                    }
                    function setupDeleteButton($grid, param) {
                        var itemDeletedEvent = new CustomEvent("itemDeleted", {
                            detail: {},
                        });
                        var currentColumns = $grid.igGrid("option", "columns");
                        currentColumns.push({
                            dataType: "bool", columnCssClass: "delete-column", headerText: "test", key: param.deleteField,
                            width: 60, formatter: function createButton(deleteField, row) {
                                var primaryKey = $grid.igGrid("option", "primaryKey");
                                var result = $('<button tabindex="-1" class="small delete-button">Delete</button>');
                                result.attr("data-value", row[primaryKey]);
                                if (deleteField === true && primaryKey !== null && !uk.util.isNullOrUndefined(row[primaryKey]) && $grid.data("enable") !== false) {
                                    return result[0].outerHTML;
                                }
                                else {
                                    return result.attr("disabled", "disabled")[0].outerHTML;
                                }
                            }
                        });
                        $grid.igGrid("option", "columns", currentColumns);
                        $grid.on("click", ".delete-button", function () {
                            var key = $(this).attr("data-value");
                            var primaryKey = $grid.igGrid("option", "primaryKey");
                            var source = _.cloneDeep($grid.igGrid("option", "dataSource"));
                            _.remove(source, function (current) {
                                return _.isEqual(current[primaryKey].toString(), key.toString());
                            });
                            if (!uk.util.isNullOrUndefined(param.sourceTarget) && typeof param.sourceTarget === "function") {
                                param.sourceTarget(source);
                            }
                            else {
                                $grid.igGrid("option", "dataSource", source);
                                $grid.igGrid("dataBind");
                            }
                            itemDeletedEvent.detail["target"] = key;
                            document.getElementById($grid.attr('id')).dispatchEvent(itemDeletedEvent);
                        });
                    }
                    function setupSelecting($grid) {
                        setupDragging($grid);
                        setupSelectingEvents($grid);
                        return $grid;
                    }
                    function unsetupSelecting($grid) {
                        unsetupDragging($grid);
                        unsetupSelectingEvents($grid);
                        return $grid;
                    }
                    function setupDragging($grid) {
                        var dragSelectRange = [];
                        var mousePos = null;
                        $grid.bind('pointerdown', function (e) {
                            var $container = $grid.closest('.ui-iggrid-scrolldiv');
                            if ($(e.target).closest('.ui-iggrid-table').length === 0) {
                                return;
                            }
                            var gridVerticalRange = new uk.util.Range($container.offset().top, $container.offset().top + $container.height());
                            mousePos = {
                                x: e.pageX,
                                y: e.pageY,
                                rowIndex: ui_1.ig.grid.getRowIndexFrom($(e.target))
                            };
                            dragSelectRange.push(mousePos.rowIndex);
                            var $scroller = $('#' + $grid.attr('id') + '_scrollContainer');
                            var timerAutoScroll = setInterval(function () {
                                var distance = gridVerticalRange.distanceFrom(mousePos.y);
                                if (distance === 0) {
                                    return;
                                }
                                var delta = Math.min(distance * OUTSIDE_AUTO_SCROLL_SPEED.RATIO, OUTSIDE_AUTO_SCROLL_SPEED.MAX);
                                var currentScrolls = $scroller.scrollTop();
                                $grid.igGrid('virtualScrollTo', (currentScrolls + delta) + 'px');
                            }, 20);
                            $(window).bind('pointermove.NtsGridListDragging', function (e) {
                                var newPointedRowIndex = ui_1.ig.grid.getRowIndexFrom($(e.target));
                                if (mousePos.rowIndex === newPointedRowIndex) {
                                    return;
                                }
                                mousePos = {
                                    x: e.pageX,
                                    y: e.pageY,
                                    rowIndex: newPointedRowIndex
                                };
                                if (dragSelectRange.length === 1 && !e.ctrlKey) {
                                    $grid.igGridSelection('clearSelection');
                                }
                                updateSelections();
                            });
                            $(window).one('pointerup', function (e) {
                                mousePos = null;
                                dragSelectRange = [];
                                $(window).unbind('pointermove.NtsGridListDragging');
                                if ($grid.data("selectUpdated") === true) {
                                    $grid.triggerHandler('selectionchanged');
                                }
                                clearInterval(timerAutoScroll);
                                $grid.data("selectUpdated", false);
                            });
                        });
                        function updateSelections() {
                            if (isNaN(mousePos.rowIndex)) {
                                return;
                            }
                            for (var i = 0, i_len = dragSelectRange.length; i < i_len; i++) {
                                $grid.igGridSelection('deselectRow', dragSelectRange[i]);
                            }
                            var newDragSelectRange = [];
                            if (dragSelectRange[0] <= mousePos.rowIndex) {
                                for (var j = dragSelectRange[0]; j <= mousePos.rowIndex; j++) {
                                    $grid.igGridSelection('selectRow', j);
                                    newDragSelectRange.push(j);
                                }
                            }
                            else if (dragSelectRange[0] > mousePos.rowIndex) {
                                for (var j = dragSelectRange[0]; j >= mousePos.rowIndex; j--) {
                                    $grid.igGridSelection('selectRow', j);
                                    newDragSelectRange.push(j);
                                }
                            }
                            dragSelectRange = newDragSelectRange;
                            $grid.data("selectUpdated", true);
                        }
                    }
                    function setupSelectingEvents($grid) {
                        $grid.bind('iggridselectioncellselectionchanging', function () {
                        });
                        $grid.bind('iggridselectionrowselectionchanged', function () {
                            $grid.triggerHandler('selectionchanged');
                        });
                    }
                    function unsetupDragging($grid) {
                        $grid.unbind('pointerdown');
                    }
                    function unsetupSelectingEvents($grid) {
                        $grid.unbind('iggridselectionrowselectionchanged');
                    }
                })(ntsGridList || (ntsGridList = {}));
            })(jqueryExtentions = ui_1.jqueryExtentions || (ui_1.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=gridlist-jquery-ext.js.map