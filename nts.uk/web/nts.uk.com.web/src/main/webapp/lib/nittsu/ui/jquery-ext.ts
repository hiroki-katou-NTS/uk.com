interface JQuery {
    ntsPopup(args: any): JQuery;
    ntsError(action: string, param?: any): any;
    ntsListBox(action: string, param?: any): any;
    ntsGridList(action: string, param?: any): any;
    ntsWizard(action: string, param?: any): any;
    ntsUserGuide(action?: string, param?: any): any;
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

        // this code was provided by Infragistics support
        function setupDragging($grid: JQuery) {
            var dragSelectRange = [];

            $grid.on('mousedown', function(e) {
                // グリッド内がマウスダウンされていない場合は処理なしで終了
                if ($(e.target).closest('.ui-iggrid-table').length === 0) {
                    return;
                }

                // ドラッグ開始位置を設定する
                var rowIndex = nts.uk.ui.ig.grid.getRowIndexFrom($(e.target));
                dragSelectRange.push(rowIndex);
                
                $(window).one('mouseup', function(e) {
                // ドラッグを終了する
                    dragSelectRange = [];
                });
            });

            $grid.on('mousemove', function(e) {
                // ドラッグ開始位置が設定されていない場合は処理なしで終了
                if (dragSelectRange.length === 0) {
                    return;
                }

                // 無駄な処理をさせないためにドラッグ終了位置が同じかどうかをチェックする
                var rowIndex = nts.uk.ui.ig.grid.getRowIndexFrom($(e.target));
                if (rowIndex === dragSelectRange[dragSelectRange.length - 1]) {
                    return;
                }

                // 新たにドラッグ選択を開始する場合、Ctrlキー押下されていない場合は以前の選択行を全てクリアする
                if (dragSelectRange.length === 1 && !e.ctrlKey) {
                    $grid.igGridSelection('clearSelection');
                }

                // 以前のドラッグ範囲の選択を一旦解除する
                for (var i = 0, i_len = dragSelectRange.length; i < i_len; i++) {
                    // http://jp.igniteui.com/help/api/2016.2/ui.iggridselection#methods:deselectRow
                    $grid.igGridSelection('deselectRow', dragSelectRange[i]);
                }

                var newDragSelectRange = [];

                if (dragSelectRange[0] <= rowIndex) {
                    for (var j = dragSelectRange[0]; j <= rowIndex; j++) {
                        // http://jp.igniteui.com/help/api/2016.2/ui.iggridselection#methods:selectRow
                        $grid.igGridSelection('selectRow', j);
                        newDragSelectRange.push(j);
                    }
                } else if (dragSelectRange[0] > rowIndex) {
                    for (var j = dragSelectRange[0]; j >= rowIndex; j--) {
                        $grid.igGridSelection('selectRow', j);
                        newDragSelectRange.push(j);
                    }
                }

                dragSelectRange = newDragSelectRange;
            });
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

    module userGuide {
        
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
}