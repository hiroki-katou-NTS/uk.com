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
                    function setupSelecting($grid) {
                        setupDragging($grid);
                        setupSelectingEvents($grid);
                        return $grid;
                    }
                    // this code was provided by Infragistics support
                    function setupDragging($grid) {
                        var dragSelectRange = [];
                        $grid.on('mousedown', function (e) {
                            // グリッド内がマウスダウンされていない場合は処理なしで終了
                            if ($(e.target).closest('.ui-iggrid-table').length === 0) {
                                return;
                            }
                            // ドラッグ開始位置を設定する
                            var rowIndex = nts.uk.ui.ig.grid.getRowIndexFrom($(e.target));
                            dragSelectRange.push(rowIndex);
                            $(window).one('mouseup', function (e) {
                                // ドラッグを終了する
                                dragSelectRange = [];
                            });
                        });
                        $grid.on('mousemove', function (e) {
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
                            }
                            else if (dragSelectRange[0] > rowIndex) {
                                for (var j = dragSelectRange[0]; j >= rowIndex; j--) {
                                    $grid.igGridSelection('selectRow', j);
                                    newDragSelectRange.push(j);
                                }
                            }
                            dragSelectRange = newDragSelectRange;
                        });
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
                                break;
                            case 'validate':
                                return validate($grid);
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
                        $list.data('value', '');
                        $list.find('.nts-list-box > li').removeClass("ui-selected");
                        $list.find('.nts-list-box > li > div').removeClass("ui-selected");
                        $list.trigger("selectionChange");
                    }
                    function validate($list) {
                        var required = $list.data('required');
                        var $currentListBox = $list.find('.nts-list-box');
                        if (required) {
                            var itemsSelected = $list.data('value');
                            if (itemsSelected === undefined || itemsSelected === null || itemsSelected.length == 0) {
                                $currentListBox.ntsError('set', 'at least 1 item selection required');
                                return false;
                            }
                            else {
                                $currentListBox.ntsError('clear');
                                return true;
                            }
                        }
                    }
                })(ntsListBox || (ntsListBox = {}));
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
                        return controls;
                    }
                    function destroy(controls) {
                        controls.each(function () {
                            $(this).remove();
                        });
                        return controls;
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
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
