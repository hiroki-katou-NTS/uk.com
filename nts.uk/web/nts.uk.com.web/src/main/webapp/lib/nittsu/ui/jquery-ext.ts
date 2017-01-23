interface JQuery {
    ntsPopup(args: any): JQuery;
    ntsError(action: string, param?: any): any;
    ntsListBox(action: string, param?: any): any;
    ntsGridList(action: string, param?: any): any;
    ntsWizard(action: string, param?: any): any;
    ntsUserGuide(action?: string, param?: any): any;
    setupSearchScroll(controlType: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

    module ntsError {
        var DATA_HAS_ERROR = 'hasError';

        $.fn.ntsError = function(action: string, message: string): any {
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
        function processErrorOnItem($control: JQuery, message: string, action: string) {
            switch (action) {
                case 'set':
                    return setError($control, message);

                case 'clear':
                    return clearErrors($control);
            }
        }
        function setError($control: JQuery, message: string) {
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
                        //console.log(dismissible);
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
                case 'getSelected':
                    return getSelected($grid);
                case 'setSelected':
                    return setSelected($grid, param);
                case 'deselectAll':
                    return deselectAll($grid);
            }
        };

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

        function setupSelecting($grid: JQuery) {
            setupDragging($grid);
            setupSelectingEvents($grid);

            return $grid;
        }

        function setupDragging($grid: JQuery) {
            var dragSelectRange = [];
            
            // used to auto scrolling when dragged above/below grid)
            var mousePos: { x: number, y: number, rowIndex: number } = null;
                
            var $container = $grid.closest('.ui-iggrid-scrolldiv');

            $grid.bind('mousedown', function(e) {
                // グリッド内がマウスダウンされていない場合は処理なしで終了
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
                    clearInterval(timerAutoScroll);
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
            }
        }

        function setupSelectingEvents($grid: JQuery) {
            $grid.bind('iggridselectioncellselectionchanging', () => {
            });
            $grid.bind('iggridselectionrowselectionchanged', () => {
                $grid.triggerHandler('selectionchanged');
            });

            $grid.on('mouseup', () => {
                $grid.triggerHandler('selectionchanged');
            });
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
                    break;
                case 'validate':
                    return validate($grid);
                default:
                    break;
            }
        };

        function selectAll($list: JQuery) {
            $list.find('.nts-list-box > li').addClass("ui-selected");
            $list.find("li").attr("clicked", "");
            $list.find('.nts-list-box').data("ui-selectable")._mouseStop(null);
        }

        function deselectAll($list: JQuery) {
            $list.data('value', '');
            $list.find('.nts-list-box > li').removeClass("ui-selected");
            $list.find('.nts-list-box > li > div').removeClass("ui-selected");
            $list.trigger("selectionChange");
        }

        function validate($list: JQuery) {
            var required = $list.data('required');
            var $currentListBox = $list.find('.nts-list-box');
            if (required) {
                var itemsSelected: any = $list.data('value');
                if (itemsSelected === undefined || itemsSelected === null || itemsSelected.length == 0) {
                    $currentListBox.ntsError('set', 'at least 1 item selection required');
                    return false;
                } else {
                    $currentListBox.ntsError('clear');
                    return true;
                }
            }
        }
    }
    
    module ntsWizard {
        $.fn.ntsWizard = function (action: string, index?: number): any {
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
        
        function begin (wizard: JQuery): JQuery {
            wizard.setStep(0);
            return wizard;
        }
        
        function end (wizard: JQuery): JQuery {
            wizard.setStep(wizard.data("length") - 1);
            return wizard;
        }
        
        function goto (wizard: JQuery, index: number): JQuery {
            wizard.setStep(index);
            return wizard;
        }
        
        function prev (wizard: JQuery): JQuery {
            wizard.steps("previous");
            return wizard;
        }
        
        function next (wizard: JQuery): JQuery {
            wizard.steps("next");
            return wizard;
        }
        
        function getCurrentStep (wizard: JQuery): number {
            return wizard.steps("getCurrentIndex");
        }
        
    }

    module ntsUserGuide {
        
        $.fn.ntsUserGuide = function (action?: string): any {
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
                $control.children().each(function(){
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
            return controls;
        }
        
        function destroy(controls: JQuery) {
            controls.each(function() {
                $(this).remove();
            });
            return controls;
        }
        
        function show(controls: JQuery): JQuery {
            controls.each(function() {
                let $control = $(this);
                $control.show();
                
                let target = $control.data('target');
                let direction = $control.data('direction');
                $control.find(".userguide-overlay").each(function(index, elem){
                    calcOverlayPosition($(elem), target, direction)
                });
                $control.children().each(function(){
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
        $.fn.setupSearchScroll = function(controlType: string, virtualization? : boolean) {
            var $control = this;
            if(controlType == 'igGrid') return setupIgGridScroll($control, virtualization);
            if(controlType == 'igTreeGrid') return setupTreeGridScroll($control);
            if(controlType == 'igTree') return setupIgTreeScroll($control);
            return this;
        }
        function setupIgGridScroll($control: JQuery, virtualization?: boolean) {
            var $grid = $control;
            console.log($grid);
            if(virtualization) {
                $grid.on("selectChange", function() {
                    var row = null;
                    var selectedRows = $grid.igGrid("selectedRows");
                    if(selectedRows) {
                        row = selectedRows[0];
                    } else {
                        row = $grid.igGrid("selectedRow"); 
                    }                
                    if(row) $grid.igGrid("virtualScrollTo", row.index);                
                });
            } else {
                $grid.on("selectChange", function() {
                    var row = null;
                    var selectedRows = $grid.igGrid("selectedRows");
                    if(selectedRows) {
                        row = selectedRows[0];
                    } else {
                        row = $grid.igGrid("selectedRow"); 
                    }                
                    if(row) {
                        var index = row.index;
                        var height = row.element[0].scrollHeight;
                        var gridId = $grid.attr('id');
                        $("#" + gridId + "_scrollContainer").scrollTop(index*height); 
                    }            
                }); 
            }
            return $grid;
        }
        function setupTreeGridScroll($control: JQuery) {
             
        }
        function setupIgTreeScroll($control: JQuery) {
             
        }
    }
}