/// <reference path="../reference.ts"/>

interface JQuery {
    ntsPopup(args: any): JQuery;
    ntsError(action: string, param?: any): any;
    ntsListBox(action: string, param?: any): any;
    ntsGridList(action: string, param?: any): any;
    ntsTreeView(action: string, param?: any): any;
    ntsGridListFeature(feature: string, action: string, ...params: any[]): any;
    ntsWizard(action: string, param?: any): any;
    ntsUserGuide(action?: string, param?: any): any;
    ntsSideBar(action?: string, param?: any): any;
    ntsEditor(action?: string, param?: any): any;
    setupSearchScroll(controlType: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {
    module ntsFileUpload {
        export interface FileUploadOption {
            stereoType: string;
            onSuccess?(): any;
            onFail?(): any;
        }

        $.fn.ntsFileUpload = function(option: FileUploadOption) {
            let dfd = $.Deferred();
            let file: JQuery;
            if ($(this).find("input[type='file']").length == 0) {
                file = $(this)[0].files;
            } else {
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
                } else {
                    dfd.reject({ message: "please select file", messageId: "-1" });
                    return dfd.promise();
                }
            } else {
                dfd.reject({ messageId: "0", message: "can not find control" });
            }
            return dfd.promise();
        }
    }

    module ntsError {
        var DATA_HAS_ERROR = 'hasError';

        $.fn.ntsError = function(action: string, message: any): any {
            var $control = $(this);
            if (action === DATA_HAS_ERROR) {
                return _.some($control, c => hasError($(c)));
            } else {
                $control.each(function(index) {
                    var $item = $(this);
                    $item = processErrorOnItem($item, message, action);
                });
                return $control;
            }

        }

        //function for set and clear error
        function processErrorOnItem($control: JQuery, message: any, action: string) {
            switch (action) {
                case 'set':
                    return setError($control, message);
                case 'clear':
                    return clearErrors($control);
            }
        }

        function setError($control: JQuery, message: any) {
            $control.data(DATA_HAS_ERROR, true);
            ui.errors.add({
                location: $control.data('name') || "",
                message: message,
                $control: $control
            });
            $control.parent().addClass('error');
            return $control;
        }

        function clearErrors($control: JQuery) {
            $control.data(DATA_HAS_ERROR, false);
            ui.errors.removeByElement($control);
            $control.parent().removeClass('error');
            return $control;
        }

        function hasError($control: JQuery) {
            return $control.data(DATA_HAS_ERROR) === true;
        }
    }

    module ntsPopup {
        let DATA_INSTANCE_NAME = 'nts-popup-panel';

        $.fn.ntsPopup = function() {
            if (arguments.length === 1) {
                var p: any = arguments[0];
                if (_.isPlainObject(p)) {
                    return init.apply(this, arguments);
                }
            }

            if (typeof arguments[0] === 'string') {
                return handleMethod.apply(this, arguments);
            }
        }

        function init(param): JQuery {
            var popup = new NtsPopupPanel($(this), param.position);
            var dismissible = param.dismissible === false;
            _.defer(function() {
                if (!dismissible) {
                    $(window).mousedown(function(e) {
                        if ($(e.target).closest(popup.$panel).length === 0) {
                            popup.hide();
                        }
                    });
                }
            });

            return popup.$panel;
        }

        function handleMethod() {
            var methodName: string = arguments[0];
            var popup: NtsPopupPanel = $(this).data(DATA_INSTANCE_NAME);

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

        class NtsPopupPanel {

            $panel: JQuery;
            position: any;

            constructor($panel: JQuery, position: any) {

                this.position = position;
                var parent = $panel.parent();
                this.$panel = $panel
                    .data(DATA_INSTANCE_NAME, this)
                    .addClass('popup-panel')
                    .appendTo(parent);
                this.$panel.css("z-index", 100);
            }

            show() {
                this.$panel
                    .css({
                        visibility: 'hidden',
                        display: 'block'
                    })
                    .position(this.position)
                    .css({
                        visibility: 'visible'
                    });
            }

            hide() {
                this.$panel.css({
                    display: 'none'
                });
            }

            destroy() {
                this.$panel = null;
            }

            toggle() {
                var isDisplaying = this.$panel.css("display");
                if (isDisplaying === 'none') {
                    this.show();
                } else {
                    this.hide();
                }
            }
        }
    }

    module ntsGridList {

        let OUTSIDE_AUTO_SCROLL_SPEED = {
            RATIO: 0.2,
            MAX: 30
        };

        $.fn.ntsGridList = function(action: string, param?: any): any {

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

        $.fn.ntsGridListFeature = function(feature: string, action: string, ...params: any[]): any {

            var $grid = $(this);

            switch (feature) {
                case 'switch':
                    switch (action) {
                        case 'setValue':
                            return setSwitchValue($grid, params);
                    }
            }
        };

        function setSwitchValue($grid: JQuery, ...params: any[]): any {
            let rowId: any = params[0][0];
            let columnKey: string = params[0][1];
            let selectedValue: any = params[0][2];
            let $row = $($grid.igGrid("rowById", rowId));
            let $parent = $row.find(".ntsControl");
            let currentSelect = $parent.attr('data-value');
            if (selectedValue !== currentSelect) {
                let rowKey = $row.attr("data-id");
                $parent.find(".nts-switch-button").removeClass("selected");
                let element = _.find($parent.find(".nts-switch-button"), function(e) {
                    return selectedValue.toString() === $(e).attr('data-value').toString();
                });
                if (element !== undefined) {
                    $(element).addClass('selected');
                    $parent.attr('data-value', selectedValue);
                    $grid.igGridUpdating("setCellValue", rowKey, columnKey, selectedValue);
                    $grid.igGrid("commit");
                    if ($grid.igGrid("hasVerticalScrollbar")) {
                        let current = $grid.ntsGridList("getSelected");
                        if(current !== undefined){
                            $grid.igGrid("virtualScrollTo", (typeof current === 'object' ? current.index : current[0].index) + 1);        
                        }
                    }
                }
            }
        }

        function getSelected($grid: JQuery): any {
            if ($grid.igGridSelection('option', 'multipleSelection')) {
                var selectedRows: Array<any> = $grid.igGridSelection('selectedRows');
                if (selectedRows)
                    return _.map(selectedRows, convertSelected);
                return [];
            } else {
                var selectedRow: any = $grid.igGridSelection('selectedRow');
                if (selectedRow)
                    return convertSelected(selectedRow);
                return undefined;
            }
        }

        function convertSelected(igGridSelectedRow: any) {
            return {
                id: igGridSelectedRow.id,
                index: igGridSelectedRow.index
            };
        }

        function setSelected($grid: JQuery, selectedId: any) {
            deselectAll($grid);

            if ($grid.igGridSelection('option', 'multipleSelection')) {
                (<Array<string>>selectedId).forEach(id => $grid.igGridSelection('selectRowById', id));
            } else {
                $grid.igGridSelection('selectRowById', selectedId);
            }
        }

        function deselectAll($grid: JQuery) {
            $grid.igGridSelection('clearSelection');
        }

        function setupDeleteButton($grid: JQuery, param) {
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
                    if (deleteField === true && primaryKey !== null && !util.isNullOrUndefined(row[primaryKey])) {
                        return result[0].outerHTML;
                    } else {
                        return result.attr("disabled", "disabled")[0].outerHTML;
                    }
                }
            });
            $grid.igGrid("option", "columns", currentColumns);

            $grid.on("click", ".delete-button", function() {
                var key = $(this).attr("data-value");
                var primaryKey = $grid.igGrid("option", "primaryKey");
                var source = _.cloneDeep($grid.igGrid("option", "dataSource"));
                _.remove(source, function(current) {
                    return _.isEqual(current[primaryKey].toString(), key.toString());
                });
                if (!util.isNullOrUndefined(param.sourceTarget) && typeof param.sourceTarget === "function") {
                    param.sourceTarget(source);
                } else {
                    $grid.igGrid("option", "dataSource", source);
                    $grid.igGrid("dataBind");
                }
                itemDeletedEvent.detail["target"] = key;
                document.getElementById($grid.attr('id')).dispatchEvent(itemDeletedEvent);
            });

        }

        function setupSelecting($grid: JQuery) {
            setupDragging($grid);
            setupSelectingEvents($grid);

            return $grid;
        }

        function unsetupSelecting($grid: JQuery) {
            unsetupDragging($grid);
            unsetupSelectingEvents($grid);

            return $grid;
        }

        function setupDragging($grid: JQuery) {
            var dragSelectRange = [];

            // used to auto scrolling when dragged above/below grid)
            var mousePos: { x: number, y: number, rowIndex: number } = null;


            $grid.bind('mousedown', function(e) {

                // グリッド内がマウスダウンされていない場合は処理なしで終了
                var $container = $grid.closest('.ui-iggrid-scrolldiv');
                if ($(e.target).closest('.ui-iggrid-table').length === 0) {
                    return;
                }

                // current grid size
                var gridVerticalRange = new util.Range(
                    $container.offset().top,
                    $container.offset().top + $container.height());

                mousePos = {
                    x: e.pageX,
                    y: e.pageY,
                    rowIndex: ig.grid.getRowIndexFrom($(e.target))
                };

                // set position to start dragging
                dragSelectRange.push(mousePos.rowIndex);

                var $scroller = $('#' + $grid.attr('id') + '_scrollContainer');

                // auto scroll while mouse is outside grid
                var timerAutoScroll = setInterval(() => {
                    var distance = gridVerticalRange.distanceFrom(mousePos.y);
                    if (distance === 0) {
                        return;
                    }

                    var delta = Math.min(distance * OUTSIDE_AUTO_SCROLL_SPEED.RATIO, OUTSIDE_AUTO_SCROLL_SPEED.MAX);
                    var currentScrolls = $scroller.scrollTop();
                    $grid.igGrid('virtualScrollTo', (currentScrolls + delta) + 'px');
                }, 20);

                // handle mousemove on window while dragging (unhandle when mouseup)
                $(window).bind('mousemove.NtsGridListDragging', function(e) {

                    var newPointedRowIndex = ig.grid.getRowIndexFrom($(e.target));

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
                $(window).one('mouseup', function(e) {
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
                } else if (dragSelectRange[0] > mousePos.rowIndex) {
                    for (var j = dragSelectRange[0]; j >= mousePos.rowIndex; j--) {
                        $grid.igGridSelection('selectRow', j);
                        newDragSelectRange.push(j);
                    }
                }

                dragSelectRange = newDragSelectRange;
                $grid.data("selectUpdated", true);
            }
        }

        function setupSelectingEvents($grid: JQuery) {
            $grid.bind('iggridselectioncellselectionchanging', () => {
            });
            $grid.bind('iggridselectionrowselectionchanged', () => {
                $grid.triggerHandler('selectionchanged');
            });

            //            $grid.on('mouseup', () => {
            //                $grid.triggerHandler('selectionchanged');
            //            });
        }

        function unsetupDragging($grid: JQuery) {

            $grid.unbind('mousedown');
        }

        function unsetupSelectingEvents($grid: JQuery) {
            $grid.unbind('iggridselectionrowselectionchanged');

            //            $grid.off('mouseup');
        }
    }

    module ntsTreeView {

        let OUTSIDE_AUTO_SCROLL_SPEED = {
            RATIO: 0.2,
            MAX: 30
        };

        $.fn.ntsTreeView = function(action: string, param?: any): any {

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

        function getSelected($tree: JQuery): any {
            if ($tree.igTreeGridSelection('option', 'multipleSelection')) {
                var selectedRows: Array<any> = $tree.igTreeGridSelection('selectedRows');
                if (selectedRows)
                    return _.map(selectedRows, convertSelected);
                return [];
            } else {
                var selectedRow: any = $tree.igTreeGridSelection('selectedRow');
                if (selectedRow)
                    return convertSelected(selectedRow);
                return undefined;
            }
        }

        function convertSelected(selectedRow: any) {
            return {
                id: selectedRow.id,
                index: selectedRow.index
            };
        }

        function setSelected($tree: JQuery, selectedId: any) {
            deselectAll($tree);

            if ($tree.igTreeGridSelection('option', 'multipleSelection')) {
                (<Array<string>>selectedId).forEach(id => $tree.igTreeGridSelection('selectRowById', id));
            } else {
                $tree.igTreeGridSelection('selectRowById', selectedId);
            }
        }

        function deselectAll($grid: JQuery) {
            $grid.igTreeGridSelection('clearSelection');
        }
    }

    module ntsListBox {
        $.fn.ntsListBox = function(action: string): any {

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

        function selectAll($list: JQuery) {
            let $grid = $list.find(".ntsListBox");
            let options = $grid.igGrid("option", "dataSource");
            let primaryKey = $grid.igGrid("option", "primaryKey");
            _.forEach(options, function (option, idx){
                $grid.igGridSelection("selectRowById", option[primaryKey]);        
            });
            $grid.triggerHandler('selectionchanged');
        }

        function deselectAll($list: JQuery) {
            let $grid = $list.find(".ntsListBox");
            $grid.igGridSelection('clearSelection');
            $grid.triggerHandler('selectionchanged');
        }
    }

    module ntsEditor {
        $.fn.ntsEditor = function(action: string): any {

            var $editor = $(this);

            switch (action) {
                case 'validate':
                    validate($editor);
                default:
                    break;
            }
        };

        function validate($editor: JQuery) {
            var validateEvent = new CustomEvent("validate", {

            });
            $editor.each(function(index) {
                var $input = $(this);
                document.getElementById($input.attr('id')).dispatchEvent(validateEvent);
            });
            //            document.getElementById($editor.attr('id')).dispatchEvent(validateEvent);
            //            $editor.trigger("validate");
        }
    }

    module ntsWizard {
        $.fn.ntsWizard = function(action: string, index?: number): any {
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
            };
        }

        function begin(wizard: JQuery): JQuery {
            wizard.setStep(0);
            return wizard;
        }

        function end(wizard: JQuery): JQuery {
            wizard.setStep(wizard.data("length") - 1);
            return wizard;
        }

        function goto(wizard: JQuery, index: number): JQuery {
            wizard.setStep(index);
            return wizard;
        }

        function prev(wizard: JQuery): JQuery {
            wizard.steps("previous");
            return wizard;
        }

        function next(wizard: JQuery): JQuery {
            wizard.steps("next");
            return wizard;
        }

        function getCurrentStep(wizard: JQuery): number {
            return wizard.steps("getCurrentIndex");
        }

    }

    module ntsUserGuide {

        $.fn.ntsUserGuide = function(action?: string): any {
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
            };
        }

        function init(controls: JQuery): JQuery {
            controls.each(function() {
                // UserGuide container
                let $control = $(this);
                $control.remove();
                if (!$control.hasClass("ntsUserGuide"))
                    $control.addClass("ntsUserGuide");
                $($control).appendTo($("body")).show();
                let target = $control.data('target');
                let direction = $control.data('direction');

                // Userguide Information Box
                $control.children().each(function() {
                    let $box = $(this);
                    let boxDirection = $box.data("direction");
                    $box.addClass("userguide-box caret-" + getReveseDirection(boxDirection) + " caret-overlay");
                });

                // Userguide Overlay
                let $overlay = $("<div class='userguide-overlay'></div>")
                    .addClass("overlay-" + direction)
                    .appendTo($control);
                $control.hide();

            });

            // Hiding when click outside
            $("html").on("mouseup keypress", { controls: controls }, hideBinding);

            return controls;
        }

        function destroy(controls: JQuery) {
            controls.each(function() {
                $(this).remove();
            });

            // Unbind Hiding when click outside
            $("html").off("mouseup keypress", hideBinding);
            return controls;
        }

        function hideBinding(e): JQuery {
            e.data.controls.each(function() {
                $(this).hide();
            });
            return e.data.controls;
        }

        function show(controls: JQuery): JQuery {
            controls.each(function() {
                let $control = $(this);
                $control.show();

                let target = $control.data('target');
                let direction = $control.data('direction');
                $control.find(".userguide-overlay").each(function(index, elem) {
                    calcOverlayPosition($(elem), target, direction)
                });
                $control.children().each(function() {
                    let $box = $(this);
                    let boxTarget = $box.data("target");
                    let boxDirection = $box.data("direction");
                    let boxMargin = ($box.data("margin")) ? $box.data("margin") : "20";
                    calcBoxPosition($box, boxTarget, boxDirection, boxMargin);
                });
            });
            return controls;
        }


        function hide(controls: JQuery): JQuery {
            controls.each(function() {
                $(this).hide();
            });
            return controls;
        }

        function toggle(controls: JQuery): JQuery {
            if (isShow(controls))
                hide(controls);
            else
                show(controls);
            return controls;
        }

        function isShow(controls: JQuery): boolean {
            let result = true;
            controls.each(function() {
                if (!$(this).is(":visible"))
                    result = false;
            });
            return result;
        }

        function calcOverlayPosition(overlay: JQuery, target: string, direction: string): JQuery {
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

        function calcBoxPosition(box: JQuery, target: string, direction: string, margin: string): JQuery {
            let operation = "+";
            if (direction === "left" || direction === "top")
                operation = "-";
            return box.position({
                my: getReveseDirection(direction) + operation + margin,
                at: direction,
                of: target,
                collision: "none"
            });
        }

        function getReveseDirection(direction: string): string {
            if (direction === "left")
                return "right";
            else if (direction === "right")
                return "left";
            else if (direction === "top")
                return "bottom";
            else if (direction === "bottom")
                return "top";
        }
    }

    module ntsSearchBox {
        $.fn.setupSearchScroll = function(controlType: string, virtualization?: boolean) {
            var $control = this;
            if (controlType.toLowerCase() == 'iggrid') return setupIgGridScroll($control, virtualization);
            if (controlType.toLowerCase() == 'igtreegrid') return setupTreeGridScroll($control, virtualization);
            if (controlType.toLowerCase() == 'igtree') return setupIgTreeScroll($control);
            return this;
        }
        function setupIgGridScroll($control: JQuery, virtualization?: boolean) {
            var $grid = $control;
            if (virtualization) {
                $grid.on("selectChange", function() {
                    var row = null;
                    var selectedRows = $grid.igGrid("selectedRows");
                    if (selectedRows) {
                        row = selectedRows[0];
                    } else {
                        row = $grid.igGrid("selectedRow");
                    }
                    if (row) $grid.igGrid("virtualScrollTo", row.index);
                });
            } else {
                $grid.on("selectChange", function() {
                    var row = null;
                    var selectedRows = $grid.igGrid("selectedRows");
                    if (selectedRows) {
                        row = selectedRows[0];
                    } else {
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

        function setupTreeGridScroll($control: JQuery, virtualization?: boolean) {
            var $treegrid = $control;
            var id = $treegrid.attr('id');
            $treegrid.on("selectChange", function() {
                var row = null;
                var selectedRows = $treegrid.igTreeGridSelection("selectedRows");
                if (selectedRows) {
                    row = selectedRows[0];
                } else {
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

        function setupIgTreeScroll($control: JQuery) {
            //implement later if needed
            return $control;
        }
    }

    module ntsSideBar {

        $.fn.ntsSideBar = function(action?: string, index?: number): any {
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
            };
        }

        function init(control: JQuery): JQuery {
            $("html").addClass("sidebar-html");
            control.find("div[role=tabpanel]").hide();
            control.on("click", "#sidebar-area .navigator a", function(e) {
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

        function active(control: JQuery, index: number): JQuery {
            control.find("#sidebar-area .navigator a").removeClass("active");
            control.find("#sidebar-area .navigator a").eq(index).addClass("active");
            control.find("div[role=tabpanel]").hide();
            $(control.find("#sidebar-area .navigator a").eq(index).attr("href")).show();
            return control;
        }

        function enable(control: JQuery, index: number): JQuery {
            control.find("#sidebar-area .navigator a").eq(index).removeAttr("disabled");
            return control;

        }

        function disable(control: JQuery, index: number): JQuery {
            control.find("#sidebar-area .navigator a").eq(index).attr("disabled", "disabled");
            return control;
        }

        function show(control: JQuery, index: number): JQuery {
            control.find("#sidebar-area .navigator a").eq(index).show();
            return control;
        }

        function hide(control: JQuery, index: number): JQuery {
            var current = getCurrent(control);
            if (current === index) {
                active(control, 0);
            }
            control.find("#sidebar-area .navigator a").eq(index).hide();
            return control;
        }

        function getCurrent(control: JQuery): number {
            let index = 0;
            index = control.find("#sidebar-area .navigator a.active").closest("li").index();
            return index;
        }

    }

    export module ntsGrid {
        $.fn.ntsGrid = function(options: any) {
            var self = this;
            
            if (typeof options === "string") {
                ntsAction($(self), options, [].slice.call(arguments).slice(1));
                return;
            }
            if (options.ntsControls === undefined) {
                $(this).igGrid(options);
                return;
            }
            validation.scanValidators($(self), options.columns); 
            
            var columns = _.map(options.columns, function(column: any) {
                if (column.ntsControl === undefined) return column;
                var controlDef = _.find(options.ntsControls, function(ctl: any) {
                    return ctl.name === column.ntsControl;
                });

                var $self = $(self);
                column.formatter = function(value, rowObj) {
                    var rowId = rowObj[$self.igGrid("option", "primaryKey")];
                    var update = (val) => {
                        if ($self.data("igGrid") !== null) {
                            $self.igGridUpdating("setCellValue", rowId, column.key, column.dataType !== 'string' ? val : val.toString());
                            if (options.autoCommit === undefined || options.autoCommit === false) {
                                var updatedRow = $self.igGrid("rowById", rowId, false);
                                $self.igGrid("commit");
                                if (updatedRow !== undefined) $self.igGrid("virtualScrollTo", $(updatedRow).data("row-idx"));
                            }
                        }
                    };
                    var deleteRow = () => {
                        if ($self.data("igGrid") !== null) $self.data("igGridUpdating").deleteRow(rowId);
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
                    setTimeout(function() {
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
            updating.addFeature(options);
            // Copy&Paste
            copyPaste.ifOn($(self), options);
            options.autoCommit = true;
            
            events.Handler.pull($(self)).filter().onCellUpdate().onDirectEnter().onSpacePress();
            $(this).igGrid(options);
        };
        
        function isFeatureEnable(features: any, name: string) {
            return _.find(features, function(feature: any) {
                return feature.name === name;
            }) !== undefined;
        }
        
        module feature {
            export let UPDATING = "Updating";
            export let SELECTION = "Selection";
            export let COPY_PASTE = "CopyPaste";
            export let CELL_EDIT = "CellEdit";
            export function replaceBy(options: any, featureName: string, newFeature: any) {
                let replaceId: number;
                _.forEach(options.features, function(feature: any, id: number) {
                    if (feature.name === featureName) {
                        replaceId = id;
                        return false;
                    }
                });
                options.features.splice(replaceId, 1, newFeature);
            }
        }
        
        module updating {
            
            export function addFeature(options: any) {
                let updateFeature = createUpdateOptions(options); 
                if (!isFeatureEnable(options.features, feature.UPDATING)) {
                    options.features.push(updateFeature);
                } else {
                    feature.replaceBy(options, feature.UPDATING, createUpdateOptions(options));
                }
            }
            
            function createUpdateOptions(options: any) {
                let updateFeature: any = { name: feature.UPDATING, enableAddRow: false, enableDeleteRow: false, editMode: 'none' };
                if (isFeatureEnable(options.ntsFeatures, feature.CELL_EDIT)) {
                    updateFeature.editMode = "cell";
                    updateFeature.editCellStarting = startEditCell;
                }
                return updateFeature;
            }
            
            export function containsNtsControl($target: any) {
                let td = $target;
                if (!$target.prev().is("td")) td = $target.closest("td");
                return td.find("div[class*='nts-grid-control']").length > 0;
            } 
            
            function startEditCell(evt: any, ui: any) {
                if (containsNtsControl($(evt.currentTarget)) || utils.isEnterKey(evt) || utils.isTabKey(evt)) {
                    let selectedCell = selection.getSelectedCell($(evt.target));
                    if (util.isNullOrUndefined(selectedCell)) return;
                    $(evt.target).igGridSelection("selectCell", selectedCell.rowIndex, selectedCell.index);
                    return false;
                }
//                $(ui.editor).on("keyup", cellKeyUp);
                return true; 
            }
            
            function finishEditCell(evt: any, ui: any) {
                let validators: any =  $(evt.target).data(validation.VALIDATORS);
                let fieldValidator = validators[ui.columnKey];
                if (util.isNullOrUndefined(fieldValidator)) return;
                let result = fieldValidator.probe(ui.value);
                if (!result.isValid) $(ui.editor).ntsError("set", result.errorMessage);
            }
            
            export function triggerCellUpdate(evt: any, cell: any) {
                var grid = evt.currentTarget;
                if ($(grid).igGridUpdating("isEditing")) return;
                if (utils.isAlphaNumeric(evt)) {
                    startEdit(evt, cell);
                }
                if (utils.isDeleteKey(evt)) {
                    $(grid).one(events.Handler.GRID_EDIT_CELL_STARTED, function(evt: any, ui: any) {
                        $(ui.editor).find("input").val("");
                    });
                    startEdit(evt, cell);
                }
            }
            
            function startEdit(evt: any, cell: any) {
                $(evt.currentTarget).igGridUpdating("startEdit", cell.id, cell.index);
                if ($(cell.element).text().trim() !== "") evt.preventDefault();
                evt.stopImmediatePropagation();
            }
        }
        
        module selection {
            
            export function addFeature(options: any) {
                let selection = { name: feature.SELECTION, mode: "cell", cellSelectionChanged: selectCellChange };
                if (!isFeatureEnable(options.features, feature.SELECTION)) {
                    options.features.push(selection);
                } else {
                    feature.replaceBy(options, feature.SELECTION, selection);
                }
            }
            
            export function selectPrev($grid: JQuery) {
                var selectedCell: any = getSelectedCell($grid);
                if (util.isNullOrUndefined(selectedCell)) return;
                let columns = getVisibleColumns($grid);
                if (selectedCell.index > 0) selectCell($grid, selectedCell.rowIndex, selectedCell.index - 1);
                else if (selectedCell.index === 0 && selectedCell.rowIndex > 0) {
                    selectCell($grid, selectedCell.rowIndex - 1, columns.length - 1);
                }
            }
            
            export function selectFollow($grid: JQuery, enterDirection?: string) {
                var enter = enterDirection || "right";
                if (enter === "right") selectNext($grid);
                else selectBelow($grid);
            }
            
            function selectNext($grid: JQuery) {
                var selectedCell: any = getSelectedCell($grid);
                if (util.isNullOrUndefined(selectedCell)) return;
                let columns = getVisibleColumns($grid);
                let dataSource = $grid.igGrid("option", "dataSource");
                if (selectedCell.index < columns.length - 1) selectCell($grid, selectedCell.rowIndex, selectedCell.index + 1);
                else if (selectedCell.index === columns.length - 1 && selectedCell.rowIndex < dataSource.length - 1) {
                    selectCell($grid, selectedCell.rowIndex + 1, 0);
                } 
            }
            
            function selectBelow($grid: JQuery) {
                var selectedCell: any = getSelectedCell($grid);
                if (util.isNullOrUndefined(selectedCell)) return;
                let dataSource = $grid.igGrid("option", "dataSource");
                if (selectedCell.rowIndex < dataSource.length - 1) {
                    selectCell($grid, selectedCell.rowIndex + 1, selectedCell.index);
                }
            }
            
            function getVisibleColumns($grid: JQuery) {
                return _.filter($grid.igGrid("option", "columns"), function(column: any) {
                    return column.hidden !== true;
                });
            }
            
            export function getSelectedCell($grid: JQuery) {
                return $grid.igGridSelection("selectedCell") || $grid.data("ntsSelectedCell");
            }
            
            export function selectCell($grid: JQuery, rowIndex: number, columnIndex: number) {
                $grid.igGridSelection("selectCell", rowIndex, columnIndex);
                
                // TODO: Focus nts common controls if exists.
                let selectedCell: any = getSelectedCell($grid);
                let ntsCombo = $(selectedCell.element).find(".nts-combo-container"); 
                if (ntsCombo.length > 0) {
                    ntsCombo.find("input").select();
                }
            }
            
            function selectCellChange(evt: any, ui: any) {
                if (util.isNullOrUndefined(ui.cell)) return;
                $(evt.target).data("ntsSelectedCell", ui.cell);
            }
            
            export function onCellNavigate(evt: any, enterDirection?: string) {
                var grid = evt.currentTarget;
                // Tab key
                if (evt.keyCode === 9) {
                    if ($(grid).igGridUpdating("isEditing"))
                        $(grid).igGridUpdating("endEdit");
                    
                    if (evt.shiftKey) {
                        selection.selectPrev($(grid));
                    } else {
                        selection.selectFollow($(grid));
                    }
                    evt.preventDefault();
                    return;
                }
                 
                // Enter key
                if (evt.keyCode === 13) {
                    if (evt.shiftKey) {
                        selection.selectPrev($(grid));
                    } else {
                        selection.selectFollow($(grid), enterDirection);
                    }
                    return;
                }
            }
            
            export class Direction {
                to: string;
                bind(evt: any) {
                    onCellNavigate(evt, this.to);
                }
            }
        }

        function ntsAction($grid: JQuery, method: string, params: Array<any>) {
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
                case "directEnter":
                    var direction: selection.Direction = $grid.data("enter");
                    direction.to = params[0];
                    break;
            }
        }

        function updateRow($grid: JQuery, rowId: any, object: any, autoCommit: boolean) {
            $grid.data("igGridUpdating").updateRow(rowId, object);
            if (!autoCommit) {
                var updatedRow = $grid.igGrid("rowById", rowId, false);
                $grid.igGrid("commit");
                if (updatedRow !== undefined) $grid.igGrid("virtualScrollTo", $(updatedRow).data("row-idx"));
            }
        }

        function disableNtsControlAt($grid: JQuery, rowId: any, columnKey: any, controlType: string) {
            var cellContainer = $grid.igGrid("cellById", rowId, columnKey);
            var control = getControl(controlType);
            control.disable($(cellContainer));
        }

        function enableNtsControlAt($grid: JQuery, rowId: any, columnKey: any, controlType: string) {
            var cellContainer = $grid.igGrid("cellById", rowId, columnKey);
            var control = getControl(controlType);
            control.enable($(cellContainer));
        }

        function getControl(name: string): NtsControlBase {
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

        abstract class NtsControlBase {
            readOnly: boolean = false;
            abstract containerClass(): string;
            abstract draw(data: any): JQuery;
            abstract enable($container: JQuery): void;
            abstract disable($container: JQuery): void;
        }

        class CheckBox extends NtsControlBase {
            containerClass(): string {
                return "nts-checkbox-container";
            }

            draw(data: any): JQuery {
                var checkBoxText: string;
                var setChecked = data.update;
                var initValue = data.initValue;
                var $wrapper = $("<div/>").addClass(this.containerClass()).data("enable", data.enable);
                $wrapper.addClass("ntsControl").on("click", (e) => {
                    if ($wrapper.data("readonly") === true) e.preventDefault();
                });

                var text = data.controlDef.options[data.controlDef.optionsText];
                if (text) {
                    checkBoxText = text;
                } else {
                    checkBoxText = $wrapper.text();
                    $wrapper.text('');
                }
                var $checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                var $checkBox = $('<input type="checkbox">').on("change", function() {
                    setChecked($(this).is(":checked"));
                }).appendTo($checkBoxLabel);
                var $box = $("<span class='box'></span>").appendTo($checkBoxLabel);
                if (checkBoxText && checkBoxText.length > 0)
                    var label = $("<span class='label'></span>").text(checkBoxText).appendTo($checkBoxLabel);
                $checkBoxLabel.appendTo($wrapper);

                var checked = initValue !== undefined ? initValue : true;
                $wrapper.data("readonly", this.readOnly);
                var $checkBox = $wrapper.find("input[type='checkbox']");

                if (checked === true) $checkBox.attr("checked", "checked");
                else $checkBox.removeAttr("checked");
                if (data.enable === true) $checkBox.removeAttr("disabled");
                else $checkBox.attr("disabled", "disabled");
                return $wrapper;
            }

            disable($container: JQuery): void {
                var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                $wrapper.find("input[type='checkbox']").attr("disabled", "disabled");
            }

            enable($container: JQuery): void {
                var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                $wrapper.find("input[type='checkbox']").removeAttr("disabled");
            }
        }

        class SwitchButtons extends NtsControlBase {
            containerClass(): string {
                return "nts-switch-container";
            }

            draw(data: any): JQuery {
                var selectedCssClass = 'selected';
                var options = data.controlDef.options;
                var optionsValue = data.controlDef.optionsValue;
                var optionsText = data.controlDef.optionsText;
                var selectedValue = data.initValue;
                var container = $("<div/>").addClass(this.containerClass()).data("enable", data.enable);

                _.forEach(options, function(opt) {
                    var value = opt[optionsValue];
                    var text = opt[optionsText];

                    var btn = $('<button>').text(text)
                        .addClass('nts-switch-button')
                        .attr('data-swbtn', value)
                        .on('click', function() {
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
            }

            enable($container: JQuery): void {
                var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                $('button', $wrapper).prop("disabled", false);
            }

            disable($container: JQuery): void {
                var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                $('button', $wrapper).prop("disabled", true);
            }

        }

        class ComboBox extends NtsControlBase {
            containerClass(): string {
                return "nts-combo-container";
            }

            draw(data: any): JQuery {
                // Default values.
                var distanceColumns = '     ';
                // Character used fill to the columns.
                var fillCharacter = ' ';
                var maxWidthCharacter = 15;
                var container = $("<div/>").addClass(this.containerClass()).data("enable", data.enable);
                var columns: Array<any> = data.controlDef.columns;

                // Set attribute for multi column.
                var itemTemplate: string = undefined;
                var haveColumn = columns && columns.length > 0;
                if (haveColumn) {
                    itemTemplate = '<div class="nts-combo-item">';
                    _.forEach(columns, function(item, i) {
                        // Set item template.
                        itemTemplate += '<div class="nts-column nts-combo-column-' + i + '">${' + item.prop + '}</div>';
                    });
                    itemTemplate += '</div>';
                }

                data.controlDef.options = data.controlDef.options.map((option) => {
                    var newOptionText: string = '';
                    if (haveColumn) {
                        _.forEach(columns, function(item, i) {
                            var prop: string = option[item.prop];
                            var length: number = item.length;

                            if (i === columns.length - 1) {
                                newOptionText += prop;
                            } else {
                                newOptionText += text.padRight(prop, fillCharacter, length) + distanceColumns;
                            }
                        });

                    } else {
                        newOptionText = option[data.controlDef.optionsText];
                    }
                    option['nts-combo-label'] = newOptionText;
                    return option;
                });

                var comboMode: string = data.controlDef.editable ? 'editable' : 'dropdown';
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
                    selectionChanged: function(evt: any, ui: any) {
                        if (ui.items.length > 0) {
                            data.update(ui.items[0].data[data.controlDef.optionsValue]);
                        }
                    }
                });

                // Set width for multi columns.
                if (haveColumn) {
                    var totalWidth = 0;
                    var $dropDownOptions = $(container.igCombo("dropDown"));
                    _.forEach(columns, function(item, i) {
                        var charLength: number = item.length;
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
            }

            enable($container: JQuery): void {
                var $wrapper = $container.find("." + this.containerClass());
                $wrapper.data("enable", true);
                $wrapper.igCombo("option", "disabled", false);
            }
            disable($container: JQuery): void {
                var $wrapper = $container.find("." + this.containerClass());
                $wrapper.data("enable", false);
                $wrapper.igCombo("option", "disabled", true);
            }
        }

        class Button extends NtsControlBase {
            containerClass(): string {
                return "nts-button-container";
            }

            draw(data: any): JQuery {
                var $container = $("<div/>").addClass(this.containerClass());
                var $button = $("<button/>").addClass("ntsButton").appendTo($container).text(data.controlDef.text)
                    .data("enable", data.enable).on("click", data.controlDef.click);
                $button.prop("disabled", !data.enable);
                return $container;
            }

            enable($container: JQuery): void {
                var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                $wrapper.find(".ntsButton").prop("disabled", false);
            }
            disable($container: JQuery): void {
                var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                $wrapper.find(".ntsButton").prop("disabled", true);
            }
        }

        class DeleteButton extends Button {
            draw(data: any): JQuery {
                var btn = super.draw(data);
                btn.off("click", data.controlDef.click);
                btn.on("click", data.deleteRow);
                return btn;
            }
        }
        
        module copyPaste {
            enum PasteMode {
                NEW,
                UPDATE
            }
            
            export class Processor {
                $grid: JQuery;
                options: any;
                pasteInMode: PasteMode = PasteMode.UPDATE;
            
                constructor(options: any) {
                    this.options = options;
                }
                static addFeatures(options: any) {
                    selection.addFeature(options);
                    return new Processor(options);
                }
                
                chainEvents($grid: JQuery) {
                    var self = this;
                    self.$grid = $grid;
                    events.Handler.pull(self.$grid).focusInWith(self).ctrlCxpWith(self);
                }
                
                copyHandler() {
                    var selectedCell: any = selection.getSelectedCell(this.$grid);
                    var content = selectedCell.element.text();
                    $("#copyHelper").val(content).select();
                    document.execCommand("copy");
                    return selectedCell;
                }
                
                cutHandler() {
                    var selectedCell = this.copyHandler();
                    var row = this.$grid.igGrid("rowAt", selectedCell.rowIndex);
                    var updatedRowData = {};
                    updatedRowData[selectedCell.columnKey] = "";
                    this.$grid.igGridUpdating("updateRow", parseInt($(row).data("id")), updatedRowData);
                }
                
                pasteHandler(evt: any) {
                    var cbData;
                    if (window.clipboardData) {
                        window.event.returnValue = false;
                        cbData = window.clipboardData.getData("text");
                    } else {
                        cbData = evt.originalEvent.clipboardData.getData("text/plain");
                    }
                    
                    cbData = this.process(cbData);
                    this.pasteInMode === PasteMode.UPDATE ? this.updateWith(cbData) : this.addNew(cbData);
                }
                
                private process(data: string) {
                    var dataRows = _.map(data.split("\n"), function(row) {
                        return row.split("\t");
                    });
                    
                    var rowsCount = dataRows.length;
                    if ((dataRows[rowsCount - 1].length === 1 && dataRows[rowsCount - 1][0] === "")
                        || dataRows.length === 1 && dataRows[0].length === 1 
                            && (dataRows[0][0] === "" || dataRows[0][0] === "\r")) {
                        dataRows.pop();
                    }
                    return dataRows;
                }
                
                private updateWith(data: any) {
                    var self = this;
                    if (this.$grid.data("igGridSelection") === undefined || this.$grid.data("igGridUpdating") === undefined) return;
                    var selectedCell: any = selection.getSelectedCell(this.$grid);
                    if (selectedCell === undefined) return;
                    selectedCell.element.focus();
                    
                    var visibleColumns = this.getVisibleColumns();
                    var columnIndex = selectedCell.index;
                    var rowIndex = selectedCell.rowIndex;
                    if (!this.pasteable(columnIndex + data[0].length - visibleColumns.length)) return;
                    
                    _.forEach(data, function(row: any, idx: number) {
                        var gridRow = self.$grid.igGrid("rowAt", rowIndex + idx);
                        var rowData = {};
                        for (var i = 0; i < row.length; i++) {
                            var columnKey = visibleColumns[columnIndex + i].key;
                            if (visibleColumns[columnIndex + i].dataType === "number") {
                                rowData[columnKey] = parseInt(row[i]);
                            } else {
                                rowData[columnKey] = row[i];
                            }
                        }
                        self.$grid.igGridUpdating("updateRow", parseInt($(gridRow).data("id")), rowData);    
                    });
                }
                
                private addNew(data: any) {
                    var self = this;
                    var visibleColumns = this.getVisibleColumns();
                    if (!this.pasteable(data[0].length - visibleColumns.length)) return;
                    
                    _.forEach(data, function(row: any, idx: number) {
                        var rowData = {};
                        for (var i = 0; i < visibleColumns.length; i++) {
                            var columnKey = visibleColumns[i].key;
                            if (visibleColumns[i].dataType === "number") {
                                rowData[columnKey] = parseInt(row[i]);
                            } else {
                                rowData[columnKey] = row[i];
                            }
                        }
                        self.$grid.igGridUpdating("addRow", rowData);
                    });
                }
                
                private getVisibleColumns() {
                    return _.filter(this.$grid.igGrid("option", "columns"), function(column: any) {
                        return column.hidden !== true;
                    });
                }
                
                private pasteable(excessColumns) {
                    if (excessColumns > 0) {
                        nts.uk.ui.dialog.alert("Copied table structure doesn't match.");
                        return false;
                    }
                    return true; 
                } 
            }
            
            export function ifOn($grid: JQuery, options: any) {
                if (options.ntsFeatures === undefined) return;
                _.forEach(options.ntsFeatures, function(f: any) {
                    if (f.name === feature.COPY_PASTE) {
                        Processor.addFeatures(options).chainEvents($grid);
                        return false;
                    }    
                });
            }   
        }
        
        module events {
            export class Handler {
                static KEY_DOWN: string = "keydown";
                static FOCUS_IN: string = "focusin";
                static GRID_EDIT_CELL_STARTED: string = "iggridupdatingeditcellstarted";
                $grid: JQuery;
                
                constructor($grid: JQuery) {
                    this.$grid = $grid; 
                }
                
                static pull($grid): Handler {
                    return new Handler($grid);
                }
                
                onDirectEnter() {
                    // Enter direction
                    var direction: selection.Direction = new selection.Direction();
                    this.$grid.on(Handler.KEY_DOWN, $.proxy(direction.bind, direction));
                    this.$grid.data("enter", direction);
                    return this;
                }
                
                onCellUpdate() {
                    var self = this;
                    this.$grid.on(Handler.KEY_DOWN, function(evt: any) {
                        if (evt.ctrlKey) return;
                        updating.triggerCellUpdate(evt, selection.getSelectedCell(self.$grid));
                    });
                    return this;
                }
                
                onSpacePress() {
                    var self = this;
                    self.$grid.on(Handler.KEY_DOWN, function(evt: any) {
                        if (!utils.isSpaceKey(evt)) return;
                        var selectedCell: any = selection.getSelectedCell(self.$grid);
                        if (util.isNullOrUndefined(selectedCell)) return;
                        var checkbox = $(selectedCell.element).find(".nts-checkbox-container");
                        if (checkbox.length > 0) {
                            checkbox.find("input[type='checkbox']").click();
                        }
                    });
                    return this;
                }
                
                focusInWith(processor: copyPaste.Processor) {
                    this.$grid.on(Handler.FOCUS_IN, function(evt: any) {
                        if ($("#pasteHelper").length > 0 && $("#copyHelper").length > 0) return;
                        var pasteArea = $("<textarea id='pasteHelper'/>").css({ "opacity": 0, "overflow": "hidden" })
                                            .on("paste", $.proxy(processor.pasteHandler, processor));
                        var copyArea = $("<textarea id='copyHelper'/>").css({ "opacity": 0, "overflow": "hidden" });
                        $("<div/>").css({ "position": "fixed", "top": -10000, "left": -10000 })
                                    .appendTo($(document.body)).append(pasteArea).append(copyArea);
                    });
                    return this;
                }
                
                ctrlCxpWith(processor: copyPaste.Processor) {
                    this.$grid.on(Handler.KEY_DOWN, function(evt: any) {
                        if (evt.ctrlKey && utils.isPasteKey(evt)) {
                            $("#pasteHelper").focus();
                        } else if (evt.ctrlKey && utils.isCopyKey(evt)) {
                            processor.copyHandler();
                        } else if (evt.ctrlKey && utils.isCutKey(evt)) {
                            processor.cutHandler();   
                        }
                    });
                    return this;
                }
                
                filter() {
                    var self = this;
                    this.$grid.on(Handler.KEY_DOWN, function(evt: any) {
                        if (utils.isAlphaNumeric(evt)) {
                            var cell = selection.getSelectedCell(self.$grid);
                            if (cell === undefined || updating.containsNtsControl($(evt.target)))  
                                evt.stopImmediatePropagation();
                            return;
                        }
                    });
                    return this;
                }
            }
        }
        
        module validation {
            export let VALIDATORS: string = "ntsValidators"; 
            export class ColumnFieldValidator {
                name: string;
                primitiveValue: string;
                options: any;
                constructor(name: string, primitiveValue: string, options: any) {
                    this.name = name;
                    this.primitiveValue = primitiveValue;
                    this.options = options;
                }
                
                probe(value: string) {
                    let constraint = nts.uk.ui.validation.getConstraint(this.primitiveValue);
                    switch (constraint.valueType) {
                        case "String":
                            return new nts.uk.ui.validation.StringValidator(this.name, this.primitiveValue, this.options)
                                    .validate(value, this.options);
                        case "Integer":
                        case "Decimal":
                        case "HalfInt":
                            return new nts.uk.ui.validation.NumberValidator(this.name, this.primitiveValue, this.options)
                                    .validate(value);
                        case "Time":
                            this.options.mode = "time";
                            return new nts.uk.ui.validation.TimeValidator(this.name, this.primitiveValue, this.options)
                                    .validate(value);
                        case "Clock":
                            this.options.outputFormat = "time";
                            return new nts.uk.ui.validation.TimeValidator(this.name, this.primitiveValue, this.options)
                                    .validate(value);
                    }
                }
            }
            
            function getValidators(columnsDef: any) : { [columnKey: string]: ColumnFieldValidator } {
                var validators: any = {};
                _.forEach(columnsDef, function(def: any) {
                    if (def.constraint === undefined) return;
                    validators[def.key] = new ColumnFieldValidator(def.headerText, def.constraint.primitiveValue, def.constraint); 
                });
                return validators;
            }
            
            export function scanValidators($grid: JQuery, columnsDef: any) {
                $grid.data(VALIDATORS, getValidators(columnsDef));
            }
        }
        
        module utils {
            
            export function isArrowKey(evt: any) {
                return evt.keyCode >= 37 && evt.keyCode <= 40;
            }
            export function isAlphaNumeric(evt: any) {
                return evt.keyCode >= 48 && evt.keyCode <= 90;
            }
            export function isTabKey(evt: any) {
                return evt.keyCode === 9;
            }
            export function isEnterKey(evt: any) {
                return evt.keyCode === 13;
            }
            export function isSpaceKey(evt: any) {
                return evt.keyCode === 32;
            }
            export function isDeleteKey(evt: any) {
                return evt.keyCode === 46;
            }
            export function isPasteKey(evt: any) {
                return evt.keyCode === 86;
            }
            export function isCopyKey(evt: any) {
                return evt.keyCode === 67;
            }
            export function isCutKey(evt: any) {
                return evt.keyCode === 88;
            }
        }
    }
}

