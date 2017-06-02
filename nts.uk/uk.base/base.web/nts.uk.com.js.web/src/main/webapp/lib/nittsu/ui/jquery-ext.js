/// <reference path="../reference.ts"/>
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsFileUpload;
                (function (ntsFileUpload) {
                    $.fn.ntsFileUpload = function (option) {
                        var dfd = $.Deferred();
                        var file;
                        if ($(this).find("input[type='file']").length == 0) {
                            file = $(this)[0].files;
                        }
                        else {
                            file = $(this).find("input[type='file']")[0].files;
                        }
                        if (file) {
                            var formData = new FormData();
                            formData.append("stereotype", option.stereoType);
                            // HTML file input, chosen by user
                            formData.append("userfile", file[0]);
                            formData.append("filename", file[0].name);
                            if (file[0]) {
                                return nts.uk.request.uploadFile(formData, option);
                            }
                            else {
                                dfd.reject({ message: "please select file", messageId: "-1" });
                                return dfd.promise();
                            }
                        }
                        else {
                            dfd.reject({ messageId: "0", message: "can not find control" });
                        }
                        return dfd.promise();
                    };
                })(ntsFileUpload || (ntsFileUpload = {}));
                var ntsError;
                (function (ntsError) {
                    var DATA_HAS_ERROR = 'hasError';
                    $.fn.ntsError = function (action, message) {
                        var $control = $(this);
                        if (action === DATA_HAS_ERROR) {
                            return _.some($control, function (c) { return hasError($(c)); });
                        }
                        else {
                            $control.each(function (index) {
                                var $item = $(this);
                                $item = processErrorOnItem($item, message, action);
                            });
                            return $control;
                        }
                    };
                    //function for set and clear error
                    function processErrorOnItem($control, message, action) {
                        switch (action) {
                            case 'set':
                                return setError($control, message);
                            case 'clear':
                                return clearErrors($control);
                        }
                    }
                    function setError($control, message) {
                        $control.data(DATA_HAS_ERROR, true);
                        ui.errors.add({
                            location: $control.data('name') || "",
                            message: message,
                            $control: $control
                        });
                        $control.parent().addClass('error');
                        return $control;
                    }
                    function clearErrors($control) {
                        $control.data(DATA_HAS_ERROR, false);
                        ui.errors.removeByElement($control);
                        $control.parent().removeClass('error');
                        return $control;
                    }
                    function hasError($control) {
                        return $control.data(DATA_HAS_ERROR) === true;
                    }
                })(ntsError || (ntsError = {}));
                var ntsPopup;
                (function (ntsPopup) {
                    var DATA_INSTANCE_NAME = 'nts-popup-panel';
                    $.fn.ntsPopup = function () {
                        if (arguments.length === 1) {
                            var p = arguments[0];
                            if (_.isPlainObject(p)) {
                                return init.apply(this, arguments);
                            }
                        }
                        if (typeof arguments[0] === 'string') {
                            return handleMethod.apply(this, arguments);
                        }
                    };
                    function init(param) {
                        var popup = new NtsPopupPanel($(this), param.position);
                        var dismissible = param.dismissible === false;
                        _.defer(function () {
                            if (!dismissible) {
                                $(window).mousedown(function (e) {
                                    if ($(e.target).closest(popup.$panel).length === 0) {
                                        popup.hide();
                                    }
                                });
                            }
                        });
                        return popup.$panel;
                    }
                    function handleMethod() {
                        var methodName = arguments[0];
                        var popup = $(this).data(DATA_INSTANCE_NAME);
                        switch (methodName) {
                            case 'show':
                                popup.show();
                                break;
                            case 'hide':
                                popup.hide();
                                break;
                            case 'destroy':
                                popup.hide();
                                popup.destroy();
                                break;
                            case 'toggle':
                                popup.toggle();
                                break;
                        }
                    }
                    var NtsPopupPanel = (function () {
                        function NtsPopupPanel($panel, position) {
                            this.position = position;
                            var parent = $panel.parent();
                            this.$panel = $panel
                                .data(DATA_INSTANCE_NAME, this)
                                .addClass('popup-panel')
                                .appendTo(parent);
                            this.$panel.css("z-index", 100);
                        }
                        NtsPopupPanel.prototype.show = function () {
                            this.$panel
                                .css({
                                visibility: 'hidden',
                                display: 'block'
                            })
                                .position(this.position)
                                .css({
                                visibility: 'visible'
                            });
                        };
                        NtsPopupPanel.prototype.hide = function () {
                            this.$panel.css({
                                display: 'none'
                            });
                        };
                        NtsPopupPanel.prototype.destroy = function () {
                            this.$panel = null;
                        };
                        NtsPopupPanel.prototype.toggle = function () {
                            var isDisplaying = this.$panel.css("display");
                            if (isDisplaying === 'none') {
                                this.show();
                            }
                            else {
                                this.hide();
                            }
                        };
                        return NtsPopupPanel;
                    }());
                })(ntsPopup || (ntsPopup = {}));
                var ntsGridList;
                (function (ntsGridList) {
                    var OUTSIDE_AUTO_SCROLL_SPEED = {
                        RATIO: 0.2,
                        MAX: 30
                    };
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
                        }
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
                                $(element).addClass('selected');
                                $parent.attr('data-value', selectedValue);
                                $grid.igGridUpdating("setCellValue", rowKey, columnKey, selectedValue);
                                $grid.igGrid("commit");
                                if ($grid.igGrid("hasVerticalScrollbar")) {
                                    var current = $grid.ntsGridList("getSelected");
                                    if (current !== undefined) {
                                        $grid.igGrid("virtualScrollTo", (typeof current === 'object' ? current.index : current[0].index) + 1);
                                    }
                                }
                            }
                        }
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
                                var result = $('<button class="small delete-button">Delete</button>');
                                result.attr("data-value", row[primaryKey]);
                                if (deleteField === true && primaryKey !== null && !uk.util.isNullOrUndefined(row[primaryKey])) {
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
                        // used to auto scrolling when dragged above/below grid)
                        var mousePos = null;
                        $grid.bind('mousedown', function (e) {
                            // グリッド内がマウスダウンされていない場合は処理なしで終了
                            var $container = $grid.closest('.ui-iggrid-scrolldiv');
                            if ($(e.target).closest('.ui-iggrid-table').length === 0) {
                                return;
                            }
                            // current grid size
                            var gridVerticalRange = new uk.util.Range($container.offset().top, $container.offset().top + $container.height());
                            mousePos = {
                                x: e.pageX,
                                y: e.pageY,
                                rowIndex: ui_1.ig.grid.getRowIndexFrom($(e.target))
                            };
                            // set position to start dragging
                            dragSelectRange.push(mousePos.rowIndex);
                            var $scroller = $('#' + $grid.attr('id') + '_scrollContainer');
                            // auto scroll while mouse is outside grid
                            var timerAutoScroll = setInterval(function () {
                                var distance = gridVerticalRange.distanceFrom(mousePos.y);
                                if (distance === 0) {
                                    return;
                                }
                                var delta = Math.min(distance * OUTSIDE_AUTO_SCROLL_SPEED.RATIO, OUTSIDE_AUTO_SCROLL_SPEED.MAX);
                                var currentScrolls = $scroller.scrollTop();
                                $grid.igGrid('virtualScrollTo', (currentScrolls + delta) + 'px');
                            }, 20);
                            // handle mousemove on window while dragging (unhandle when mouseup)
                            $(window).bind('mousemove.NtsGridListDragging', function (e) {
                                var newPointedRowIndex = ui_1.ig.grid.getRowIndexFrom($(e.target));
                                // selected range is not changed
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
                            // stop dragging
                            $(window).one('mouseup', function (e) {
                                mousePos = null;
                                dragSelectRange = [];
                                $(window).unbind('mousemove.NtsGridListDragging');
                                if ($grid.data("selectUpdated") === true) {
                                    $grid.triggerHandler('selectionchanged');
                                }
                                //$grid.triggerHandler('selectionchanged');  
                                clearInterval(timerAutoScroll);
                                $grid.data("selectUpdated", false);
                            });
                        });
                        function updateSelections() {
                            // rowIndex is NaN when mouse is outside grid
                            if (isNaN(mousePos.rowIndex)) {
                                return;
                            }
                            // 以前のドラッグ範囲の選択を一旦解除する
                            // TODO: probably this code has problem of perfomance when select many rows
                            // should process only "differences" instead of "all"
                            for (var i = 0, i_len = dragSelectRange.length; i < i_len; i++) {
                                // http://jp.igniteui.com/help/api/2016.2/ui.iggridselection#methods:deselectRow
                                $grid.igGridSelection('deselectRow', dragSelectRange[i]);
                            }
                            var newDragSelectRange = [];
                            if (dragSelectRange[0] <= mousePos.rowIndex) {
                                for (var j = dragSelectRange[0]; j <= mousePos.rowIndex; j++) {
                                    // http://jp.igniteui.com/help/api/2016.2/ui.iggridselection#methods:selectRow
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
                        //            $grid.on('mouseup', () => {
                        //                $grid.triggerHandler('selectionchanged');
                        //            });
                    }
                    function unsetupDragging($grid) {
                        $grid.unbind('mousedown');
                    }
                    function unsetupSelectingEvents($grid) {
                        $grid.unbind('iggridselectionrowselectionchanged');
                        //            $grid.off('mouseup');
                    }
                })(ntsGridList || (ntsGridList = {}));
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
                            selectedId.forEach(function (id) { return $tree.igTreeGridSelection('selectRowById', id); });
                        }
                        else {
                            $tree.igTreeGridSelection('selectRowById', selectedId);
                        }
                    }
                    function deselectAll($grid) {
                        $grid.igTreeGridSelection('clearSelection');
                    }
                })(ntsTreeView || (ntsTreeView = {}));
                var ntsListBox;
                (function (ntsListBox) {
                    $.fn.ntsListBox = function (action) {
                        var $grid = $(this);
                        switch (action) {
                            case 'deselectAll':
                                deselectAll($grid);
                                break;
                            case 'selectAll':
                                selectAll($grid);
                            default:
                                break;
                        }
                    };
                    function selectAll($list) {
                        var $grid = $list.find(".ntsListBox");
                        var options = $grid.igGrid("option", "dataSource");
                        var primaryKey = $grid.igGrid("option", "primaryKey");
                        _.forEach(options, function (option, idx) {
                            $grid.igGridSelection("selectRowById", option[primaryKey]);
                        });
                        $grid.triggerHandler('selectionchanged');
                    }
                    function deselectAll($list) {
                        var $grid = $list.find(".ntsListBox");
                        $grid.igGridSelection('clearSelection');
                        $grid.triggerHandler('selectionchanged');
                    }
                })(ntsListBox || (ntsListBox = {}));
                var ntsEditor;
                (function (ntsEditor) {
                    $.fn.ntsEditor = function (action) {
                        var $editor = $(this);
                        switch (action) {
                            case 'validate':
                                validate($editor);
                            default:
                                break;
                        }
                    };
                    function validate($editor) {
                        var validateEvent = new CustomEvent("validate", {});
                        $editor.each(function (index) {
                            var $input = $(this);
                            document.getElementById($input.attr('id')).dispatchEvent(validateEvent);
                        });
                        //            document.getElementById($editor.attr('id')).dispatchEvent(validateEvent);
                        //            $editor.trigger("validate");
                    }
                })(ntsEditor || (ntsEditor = {}));
                var ntsWizard;
                (function (ntsWizard) {
                    $.fn.ntsWizard = function (action, index) {
                        var $wizard = $(this);
                        if (action === "begin") {
                            return begin($wizard);
                        }
                        else if (action === "end") {
                            return end($wizard);
                        }
                        else if (action === "goto") {
                            return goto($wizard, index);
                        }
                        else if (action === "prev") {
                            return prev($wizard);
                        }
                        else if (action === "next") {
                            return next($wizard);
                        }
                        else if (action === "getCurrentStep") {
                            return getCurrentStep($wizard);
                        }
                        else {
                            return $wizard;
                        }
                        ;
                    };
                    function begin(wizard) {
                        wizard.setStep(0);
                        return wizard;
                    }
                    function end(wizard) {
                        wizard.setStep(wizard.data("length") - 1);
                        return wizard;
                    }
                    function goto(wizard, index) {
                        wizard.setStep(index);
                        return wizard;
                    }
                    function prev(wizard) {
                        wizard.steps("previous");
                        return wizard;
                    }
                    function next(wizard) {
                        wizard.steps("next");
                        return wizard;
                    }
                    function getCurrentStep(wizard) {
                        return wizard.steps("getCurrentIndex");
                    }
                })(ntsWizard || (ntsWizard = {}));
                var ntsUserGuide;
                (function (ntsUserGuide) {
                    $.fn.ntsUserGuide = function (action) {
                        var $controls = $(this);
                        if (nts.uk.util.isNullOrUndefined(action) || action === "init") {
                            return init($controls);
                        }
                        else if (action === "destroy") {
                            return destroy($controls);
                        }
                        else if (action === "show") {
                            return show($controls);
                        }
                        else if (action === "hide") {
                            return hide($controls);
                        }
                        else if (action === "toggle") {
                            return toggle($controls);
                        }
                        else if (action === "isShow") {
                            return isShow($controls);
                        }
                        else {
                            return $controls;
                        }
                        ;
                    };
                    function init(controls) {
                        controls.each(function () {
                            // UserGuide container
                            var $control = $(this);
                            $control.remove();
                            if (!$control.hasClass("ntsUserGuide"))
                                $control.addClass("ntsUserGuide");
                            $($control).appendTo($("body")).show();
                            var target = $control.data('target');
                            var direction = $control.data('direction');
                            // Userguide Information Box
                            $control.children().each(function () {
                                var $box = $(this);
                                var boxDirection = $box.data("direction");
                                $box.addClass("userguide-box caret-" + getReveseDirection(boxDirection) + " caret-overlay");
                            });
                            // Userguide Overlay
                            var $overlay = $("<div class='userguide-overlay'></div>")
                                .addClass("overlay-" + direction)
                                .appendTo($control);
                            $control.hide();
                        });
                        // Hiding when click outside
                        $("html").on("mouseup keypress", { controls: controls }, hideBinding);
                        return controls;
                    }
                    function destroy(controls) {
                        controls.each(function () {
                            $(this).remove();
                        });
                        // Unbind Hiding when click outside
                        $("html").off("mouseup keypress", hideBinding);
                        return controls;
                    }
                    function hideBinding(e) {
                        e.data.controls.each(function () {
                            $(this).hide();
                        });
                        return e.data.controls;
                    }
                    function show(controls) {
                        controls.each(function () {
                            var $control = $(this);
                            $control.show();
                            var target = $control.data('target');
                            var direction = $control.data('direction');
                            $control.find(".userguide-overlay").each(function (index, elem) {
                                calcOverlayPosition($(elem), target, direction);
                            });
                            $control.children().each(function () {
                                var $box = $(this);
                                var boxTarget = $box.data("target");
                                var boxDirection = $box.data("direction");
                                var boxMargin = ($box.data("margin")) ? $box.data("margin") : "20";
                                calcBoxPosition($box, boxTarget, boxDirection, boxMargin);
                            });
                        });
                        return controls;
                    }
                    function hide(controls) {
                        controls.each(function () {
                            $(this).hide();
                        });
                        return controls;
                    }
                    function toggle(controls) {
                        if (isShow(controls))
                            hide(controls);
                        else
                            show(controls);
                        return controls;
                    }
                    function isShow(controls) {
                        var result = true;
                        controls.each(function () {
                            if (!$(this).is(":visible"))
                                result = false;
                        });
                        return result;
                    }
                    function calcOverlayPosition(overlay, target, direction) {
                        if (direction === "left")
                            return overlay.css("right", "auto")
                                .css("width", $(target).offset().left);
                        else if (direction === "right")
                            return overlay.css("left", $(target).offset().left + $(target).outerWidth());
                        else if (direction === "top")
                            return overlay.css("position", "absolute")
                                .css("bottom", "auto")
                                .css("height", $(target).offset().top);
                        else if (direction === "bottom")
                            return overlay.css("position", "absolute")
                                .css("top", $(target).offset().top + $(target).outerHeight())
                                .css("height", $("body").height() - $(target).offset().top);
                    }
                    function calcBoxPosition(box, target, direction, margin) {
                        var operation = "+";
                        if (direction === "left" || direction === "top")
                            operation = "-";
                        return box.position({
                            my: getReveseDirection(direction) + operation + margin,
                            at: direction,
                            of: target,
                            collision: "none"
                        });
                    }
                    function getReveseDirection(direction) {
                        if (direction === "left")
                            return "right";
                        else if (direction === "right")
                            return "left";
                        else if (direction === "top")
                            return "bottom";
                        else if (direction === "bottom")
                            return "top";
                    }
                })(ntsUserGuide || (ntsUserGuide = {}));
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
                                if (row)
                                    $grid.igGrid("virtualScrollTo", row.index);
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
                                    var index = row.index;
                                    var height = row.element[0].scrollHeight;
                                    var gridId = $grid.attr('id');
                                    $("#" + gridId + "_scrollContainer").scrollTop(index * height);
                                }
                            });
                        }
                        return $grid;
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
                                var index = row.index;
                                var height = row.element[0].scrollHeight;
                                $("#" + id + "_scroll").scrollTop(index * height);
                            }
                        });
                        return $treegrid;
                    }
                    function setupIgTreeScroll($control) {
                        //implement later if needed
                        return $control;
                    }
                })(ntsSearchBox || (ntsSearchBox = {}));
                var ntsSideBar;
                (function (ntsSideBar) {
                    $.fn.ntsSideBar = function (action, index) {
                        var $control = $(this);
                        if (nts.uk.util.isNullOrUndefined(action) || action === "init") {
                            return init($control);
                        }
                        else if (action === "active") {
                            return active($control, index);
                        }
                        else if (action === "enable") {
                            return enable($control, index);
                        }
                        else if (action === "disable") {
                            return disable($control, index);
                        }
                        else if (action === "show") {
                            return show($control, index);
                        }
                        else if (action === "hide") {
                            return hide($control, index);
                        }
                        else if (action === "getCurrent") {
                            return getCurrent($control);
                        }
                        else {
                            return $control;
                        }
                        ;
                    };
                    function init(control) {
                        $("html").addClass("sidebar-html");
                        control.find("div[role=tabpanel]").hide();
                        control.on("click", "#sidebar-area .navigator a", function (e) {
                            e.preventDefault();
                            if ($(this).attr("disabled") !== "true" &&
                                $(this).attr("disabled") !== "disabled" &&
                                $(this).attr("href") !== undefined) {
                                active(control, $(this).closest("li").index());
                            }
                        });
                        control.find("#sidebar-area .navigator a.active").trigger('click');
                        return control;
                    }
                    function active(control, index) {
                        control.find("#sidebar-area .navigator a").removeClass("active");
                        control.find("#sidebar-area .navigator a").eq(index).addClass("active");
                        control.find("div[role=tabpanel]").hide();
                        $(control.find("#sidebar-area .navigator a").eq(index).attr("href")).show();
                        return control;
                    }
                    function enable(control, index) {
                        control.find("#sidebar-area .navigator a").eq(index).removeAttr("disabled");
                        return control;
                    }
                    function disable(control, index) {
                        control.find("#sidebar-area .navigator a").eq(index).attr("disabled", "disabled");
                        return control;
                    }
                    function show(control, index) {
                        control.find("#sidebar-area .navigator a").eq(index).show();
                        return control;
                    }
                    function hide(control, index) {
                        var current = getCurrent(control);
                        if (current === index) {
                            active(control, 0);
                        }
                        control.find("#sidebar-area .navigator a").eq(index).hide();
                        return control;
                    }
                    function getCurrent(control) {
                        var index = 0;
                        index = control.find("#sidebar-area .navigator a.active").closest("li").index();
                        return index;
                    }
                })(ntsSideBar || (ntsSideBar = {}));
                var ntsGrid;
                (function (ntsGrid) {
                    $.fn.ntsGrid = function (options) {
                        var self = this;
                        if (typeof options === "string") {
                            ntsAction($(self), options, [].slice.call(arguments).slice(1));
                            return;
                        }
                        if (options.ntsControls === undefined) {
                            $(this).igGrid(options);
                            return;
                        }
                        var columns = _.map(options.columns, function (column) {
                            if (column.ntsControl === undefined)
                                return column;
                            var controlDef = _.find(options.ntsControls, function (ctl) {
                                return ctl.name === column.ntsControl;
                            });
                            var $self = $(self);
                            column.formatter = function (value, rowObj) {
                                var rowId = rowObj[$self.igGrid("option", "primaryKey")];
                                var update = function (val) {
                                    if ($self.data("igGrid") !== null) {
                                        $self.igGridUpdating("setCellValue", rowId, column.key, column.dataType !== 'string' ? val : val.toString());
                                        if (options.autoCommit === undefined || options.autoCommit === false) {
                                            var updatedRow = $self.igGrid("rowById", rowId, false);
                                            $self.igGrid("commit");
                                            if (updatedRow !== undefined)
                                                $self.igGrid("virtualScrollTo", $(updatedRow).data("row-idx"));
                                        }
                                    }
                                };
                                var deleteRow = function () {
                                    if ($self.data("igGrid") !== null)
                                        $self.data("igGridUpdating").deleteRow(rowId);
                                };
                                var ntsControl = getControl(controlDef.controlType);
                                var cell = $self.igGrid("cellById", rowId, column.key);
                                var isEnable = $(cell).find("." + ntsControl.containerClass()).data("enable");
                                isEnable = isEnable !== undefined ? isEnable : controlDef.enable === undefined ? true : controlDef.enable;
                                var data = {
                                    controlDef: controlDef,
                                    update: update,
                                    deleteRow: deleteRow,
                                    initValue: value,
                                    enable: isEnable
                                };
                                var controlCls = "nts-grid-control-" + column.key + "-" + rowId;
                                var $container = $("<div/>").append($("<div/>").addClass(controlCls));
                                var $_self = $self;
                                setTimeout(function () {
                                    var $self = $_self;
                                    var $gridCell = $self.igGrid("cellById", rowObj[$self.igGrid("option", "primaryKey")], column.key);
                                    if ($($gridCell.children()[0]).children().length === 0)
                                        $("." + controlCls).append(ntsControl.draw(data));
                                }, 0);
                                return $container.html();
                            };
                            return column;
                        });
                        options.columns = columns;
                        if (_.find(options.features, function (feature) {
                            return feature.name === "Updating";
                        }) === undefined) {
                            options.features.push({ name: 'Updating', enableAddRow: false, enableDeleteRow: false, editMode: 'none' });
                        }
                        options.autoCommit = true;
                        $(this).igGrid(options);
                    };
                    function ntsAction($grid, method, params) {
                        switch (method) {
                            case "updateRow":
                                var autoCommit = $grid.data("igGrid") !== null && $grid.igGrid("option", "autoCommit") ? true : false;
                                updateRow($grid, params[0], params[1], autoCommit);
                                break;
                            case "enableNtsControlAt":
                                enableNtsControlAt($grid, params[0], params[1], params[2]);
                                break;
                            case "disableNtsControlAt":
                                disableNtsControlAt($grid, params[0], params[1], params[2]);
                                break;
                        }
                    }
                    function updateRow($grid, rowId, object, autoCommit) {
                        $grid.data("igGridUpdating").updateRow(rowId, object);
                        if (!autoCommit) {
                            var updatedRow = $grid.igGrid("rowById", rowId, false);
                            $grid.igGrid("commit");
                            if (updatedRow !== undefined)
                                $grid.igGrid("virtualScrollTo", $(updatedRow).data("row-idx"));
                        }
                    }
                    function disableNtsControlAt($grid, rowId, columnKey, controlType) {
                        var cellContainer = $grid.igGrid("cellById", rowId, columnKey);
                        var control = getControl(controlType);
                        control.disable($(cellContainer));
                    }
                    function enableNtsControlAt($grid, rowId, columnKey, controlType) {
                        var cellContainer = $grid.igGrid("cellById", rowId, columnKey);
                        var control = getControl(controlType);
                        control.enable($(cellContainer));
                    }
                    function getControl(name) {
                        switch (name) {
                            case 'CheckBox':
                                return new CheckBox();
                            case 'SwitchButtons':
                                return new SwitchButtons();
                            case 'ComboBox':
                                return new ComboBox();
                            case 'Button':
                                return new Button();
                            case 'DeleteButton':
                                return new DeleteButton();
                        }
                    }
                    var NtsControlBase = (function () {
                        function NtsControlBase() {
                            this.readOnly = false;
                        }
                        return NtsControlBase;
                    }());
                    var CheckBox = (function (_super) {
                        __extends(CheckBox, _super);
                        function CheckBox() {
                            _super.apply(this, arguments);
                        }
                        CheckBox.prototype.containerClass = function () {
                            return "nts-checkbox-container";
                        };
                        CheckBox.prototype.draw = function (data) {
                            var checkBoxText;
                            var setChecked = data.update;
                            var initValue = data.initValue;
                            var $wrapper = $("<div/>").addClass(this.containerClass()).data("enable", data.enable);
                            $wrapper.addClass("ntsControl").on("click", function (e) {
                                if ($wrapper.data("readonly") === true)
                                    e.preventDefault();
                            });
                            var text = data.controlDef.options[data.controlDef.optionsText];
                            if (text) {
                                checkBoxText = text;
                            }
                            else {
                                checkBoxText = $wrapper.text();
                                $wrapper.text('');
                            }
                            var $checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                            var $checkBox = $('<input type="checkbox">').on("change", function () {
                                setChecked($(this).is(":checked"));
                            }).appendTo($checkBoxLabel);
                            var $box = $("<span class='box'></span>").appendTo($checkBoxLabel);
                            if (checkBoxText && checkBoxText.length > 0)
                                var label = $("<span class='label'></span>").text(checkBoxText).appendTo($checkBoxLabel);
                            $checkBoxLabel.appendTo($wrapper);
                            var checked = initValue !== undefined ? initValue : true;
                            $wrapper.data("readonly", this.readOnly);
                            var $checkBox = $wrapper.find("input[type='checkbox']");
                            if (checked === true)
                                $checkBox.attr("checked", "checked");
                            else
                                $checkBox.removeAttr("checked");
                            if (data.enable === true)
                                $checkBox.removeAttr("disabled");
                            else
                                $checkBox.attr("disabled", "disabled");
                            return $wrapper;
                        };
                        CheckBox.prototype.disable = function ($container) {
                            var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                            $wrapper.find("input[type='checkbox']").attr("disabled", "disabled");
                        };
                        CheckBox.prototype.enable = function ($container) {
                            var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                            $wrapper.find("input[type='checkbox']").removeAttr("disabled");
                        };
                        return CheckBox;
                    }(NtsControlBase));
                    var SwitchButtons = (function (_super) {
                        __extends(SwitchButtons, _super);
                        function SwitchButtons() {
                            _super.apply(this, arguments);
                        }
                        SwitchButtons.prototype.containerClass = function () {
                            return "nts-switch-container";
                        };
                        SwitchButtons.prototype.draw = function (data) {
                            var selectedCssClass = 'selected';
                            var options = data.controlDef.options;
                            var optionsValue = data.controlDef.optionsValue;
                            var optionsText = data.controlDef.optionsText;
                            var selectedValue = data.initValue;
                            var container = $("<div/>").addClass(this.containerClass()).data("enable", data.enable);
                            _.forEach(options, function (opt) {
                                var value = opt[optionsValue];
                                var text = opt[optionsText];
                                var btn = $('<button>').text(text)
                                    .addClass('nts-switch-button')
                                    .attr('data-swbtn', value)
                                    .on('click', function () {
                                    var selectedValue = $(this).data('swbtn');
                                    $('button', container).removeClass(selectedCssClass);
                                    $(this).addClass(selectedCssClass);
                                    data.update(selectedValue);
                                });
                                if (value === selectedValue) {
                                    btn.addClass(selectedCssClass);
                                }
                                container.append(btn);
                            });
                            (data.enable === true) ? $('button', container).prop("disabled", false)
                                : $('button', container).prop("disabled", true);
                            return container;
                        };
                        SwitchButtons.prototype.enable = function ($container) {
                            var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                            $('button', $wrapper).prop("disabled", false);
                        };
                        SwitchButtons.prototype.disable = function ($container) {
                            var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                            $('button', $wrapper).prop("disabled", true);
                        };
                        return SwitchButtons;
                    }(NtsControlBase));
                    var ComboBox = (function (_super) {
                        __extends(ComboBox, _super);
                        function ComboBox() {
                            _super.apply(this, arguments);
                        }
                        ComboBox.prototype.containerClass = function () {
                            return "nts-combo-container";
                        };
                        ComboBox.prototype.draw = function (data) {
                            // Default values.
                            var distanceColumns = '     ';
                            // Character used fill to the columns.
                            var fillCharacter = ' ';
                            var maxWidthCharacter = 15;
                            var container = $("<div/>").addClass(this.containerClass()).data("enable", data.enable);
                            var columns = data.controlDef.columns;
                            // Set attribute for multi column.
                            var itemTemplate = undefined;
                            var haveColumn = columns && columns.length > 0;
                            if (haveColumn) {
                                itemTemplate = '<div class="nts-combo-item">';
                                _.forEach(columns, function (item, i) {
                                    // Set item template.
                                    itemTemplate += '<div class="nts-column nts-combo-column-' + i + '">${' + item.prop + '}</div>';
                                });
                                itemTemplate += '</div>';
                            }
                            data.controlDef.options = data.controlDef.options.map(function (option) {
                                var newOptionText = '';
                                if (haveColumn) {
                                    _.forEach(columns, function (item, i) {
                                        var prop = option[item.prop];
                                        var length = item.length;
                                        if (i === columns.length - 1) {
                                            newOptionText += prop;
                                        }
                                        else {
                                            newOptionText += uk.text.padRight(prop, fillCharacter, length) + distanceColumns;
                                        }
                                    });
                                }
                                else {
                                    newOptionText = option[data.controlDef.optionsText];
                                }
                                option['nts-combo-label'] = newOptionText;
                                return option;
                            });
                            var comboMode = data.editable ? 'editable' : 'dropdown';
                            container.igCombo({
                                dataSource: data.controlDef.options,
                                valueKey: data.controlDef.optionsValue,
                                textKey: 'nts-combo-label',
                                mode: comboMode,
                                disabled: !data.enable,
                                placeHolder: '',
                                enableClearButton: false,
                                initialSelectedItems: [
                                    { value: data.initValue }
                                ],
                                itemTemplate: itemTemplate,
                                selectionChanged: function (evt, ui) {
                                    if (ui.items.length > 0) {
                                        data.update(ui.items[0].data[data.controlDef.optionsValue]);
                                    }
                                }
                            });
                            // Set width for multi columns.
                            if (haveColumn) {
                                var totalWidth = 0;
                                var $dropDownOptions = $(container.igCombo("dropDown"));
                                _.forEach(columns, function (item, i) {
                                    var charLength = item.length;
                                    var width = charLength * maxWidthCharacter + 10;
                                    $dropDownOptions.find('.nts-combo-column-' + i).width(width);
                                    if (i !== columns.length - 1) {
                                        $dropDownOptions.find('.nts-combo-column-' + i).css({ 'float': 'left' });
                                    }
                                    totalWidth += width + 10;
                                });
                                $dropDownOptions.find('.nts-combo-item').css({ 'min-width': totalWidth });
                                container.css({ 'min-width': totalWidth });
                            }
                            container.data("columns", columns);
                            container.data("comboMode", comboMode);
                            return container;
                        };
                        ComboBox.prototype.enable = function ($container) {
                            var $wrapper = $container.find("." + this.containerClass());
                            $wrapper.data("enable", true);
                            $wrapper.igCombo("option", "disabled", false);
                        };
                        ComboBox.prototype.disable = function ($container) {
                            var $wrapper = $container.find("." + this.containerClass());
                            $wrapper.data("enable", false);
                            $wrapper.igCombo("option", "disabled", true);
                        };
                        return ComboBox;
                    }(NtsControlBase));
                    var Button = (function (_super) {
                        __extends(Button, _super);
                        function Button() {
                            _super.apply(this, arguments);
                        }
                        Button.prototype.containerClass = function () {
                            return "nts-button-container";
                        };
                        Button.prototype.draw = function (data) {
                            var $container = $("<div/>").addClass(this.containerClass());
                            var $button = $("<button/>").addClass("ntsButton").appendTo($container).text(data.controlDef.text)
                                .data("enable", data.enable).on("click", data.controlDef.click);
                            $button.prop("disabled", !data.enable);
                            return $container;
                        };
                        Button.prototype.enable = function ($container) {
                            var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                            $wrapper.find(".ntsButton").prop("disabled", false);
                        };
                        Button.prototype.disable = function ($container) {
                            var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                            $wrapper.find(".ntsButton").prop("disabled", true);
                        };
                        return Button;
                    }(NtsControlBase));
                    var DeleteButton = (function (_super) {
                        __extends(DeleteButton, _super);
                        function DeleteButton() {
                            _super.apply(this, arguments);
                        }
                        DeleteButton.prototype.draw = function (data) {
                            var btn = _super.prototype.draw.call(this, data);
                            btn.off("click", data.controlDef.click);
                            btn.on("click", data.deleteRow);
                            return btn;
                        };
                        return DeleteButton;
                    }(Button));
                })(ntsGrid = jqueryExtentions.ntsGrid || (jqueryExtentions.ntsGrid = {}));
            })(jqueryExtentions = ui_1.jqueryExtentions || (ui_1.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=jquery-ext.js.map