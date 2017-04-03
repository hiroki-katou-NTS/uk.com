var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
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
                            width: 30, formatter: function createButton(deleteField, row) {
                                var primaryKey = $grid.igGrid("option", "primaryKey");
                                var result = $('<input class="delete-button" value="delete" type="button"/>');
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
                    function setupDragging($grid) {
                        var dragSelectRange = [];
                        var mousePos = null;
                        $grid.bind('mousedown', function (e) {
                            var $container = $grid.closest('.ui-iggrid-scrolldiv');
                            if ($(e.target).closest('.ui-iggrid-table').length === 0) {
                                return;
                            }
                            var gridVerticalRange = new uk.util.Range($container.offset().top, $container.offset().top + $container.height());
                            mousePos = {
                                x: e.pageX,
                                y: e.pageY,
                                rowIndex: ui.ig.grid.getRowIndexFrom($(e.target))
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
                            $(window).bind('mousemove.NtsGridListDragging', function (e) {
                                var newPointedRowIndex = ui.ig.grid.getRowIndexFrom($(e.target));
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
                            $(window).one('mouseup', function (e) {
                                mousePos = null;
                                dragSelectRange = [];
                                $(window).unbind('mousemove.NtsGridListDragging');
                                clearInterval(timerAutoScroll);
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
                        }
                    }
                    function setupSelectingEvents($grid) {
                        $grid.bind('iggridselectioncellselectionchanging', function () {
                        });
                        $grid.bind('iggridselectionrowselectionchanged', function () {
                            $grid.triggerHandler('selectionchanged');
                        });
                        $grid.on('mouseup', function () {
                            $grid.triggerHandler('selectionchanged');
                        });
                    }
                })(ntsGridList || (ntsGridList = {}));
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
                        $list.find('.nts-list-box > li').addClass("ui-selected");
                        $list.find("li").attr("clicked", "");
                        $list.find('.nts-list-box').data("ui-selectable")._mouseStop(null);
                    }
                    function deselectAll($list) {
                        var selectListBoxContainer = $list.find('.nts-list-box');
                        selectListBoxContainer.data('value', '');
                        $list.find('.nts-list-box > li').removeClass("ui-selected");
                        $list.find('.nts-list-box > li > div').removeClass("ui-selected");
                        $list.trigger("selectionChange");
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
                            var $control = $(this);
                            $control.remove();
                            if (!$control.hasClass("ntsUserGuide"))
                                $control.addClass("ntsUserGuide");
                            $($control).appendTo($("body")).show();
                            var target = $control.data('target');
                            var direction = $control.data('direction');
                            $control.children().each(function () {
                                var $box = $(this);
                                var boxDirection = $box.data("direction");
                                $box.addClass("userguide-box caret-" + getReveseDirection(boxDirection) + " caret-overlay");
                            });
                            var $overlay = $("<div class='userguide-overlay'></div>")
                                .addClass("overlay-" + direction)
                                .appendTo($control);
                            $control.hide();
                        });
                        $("html").on("mouseup keypress", { controls: controls }, hideBinding);
                        return controls;
                    }
                    function destroy(controls) {
                        controls.each(function () {
                            $(this).remove();
                        });
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
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
