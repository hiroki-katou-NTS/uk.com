var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                $.fn.exposeVertically = function ($target) {
                    var $scroll = $(this);
                    var currentViewTopPosition = $scroll.scrollTop();
                    var currentViewBottomPosition = currentViewTopPosition + $scroll.height();
                    var targetTopPosition = $target.position().top + currentViewTopPosition;
                    var targetBottomPosition = targetTopPosition + $target.outerHeight();
                    if (currentViewTopPosition <= targetTopPosition && targetBottomPosition <= currentViewBottomPosition) {
                        return;
                    }
                    if (targetTopPosition <= currentViewTopPosition) {
                        var gap = currentViewTopPosition - targetTopPosition;
                        $scroll.scrollTop(currentViewTopPosition - gap);
                        return;
                    }
                    if (currentViewBottomPosition <= targetBottomPosition) {
                        var gap = targetBottomPosition - currentViewBottomPosition;
                        $scroll.scrollTop(currentViewTopPosition + gap);
                        return;
                    }
                };
                $.fn.onkey = function (command, keyCode, handler) {
                    var $element = $(this);
                    $element.on("key" + command, function (e) {
                        if (e.keyCode === keyCode) {
                            return handler(e);
                        }
                    });
                    return $element;
                };
                $.fn.dialogPositionControl = function () {
                    var $dialog = $(this);
                    $dialog.dialog("option", "position", {
                        my: "center",
                        at: "center",
                        of: window,
                        collision: "none"
                    });
                    var $container = $dialog.closest(".ui-dialog");
                    var offsetContentsArea = window.parent.$("#header").height();
                    var offsetDialog = $container.offset();
                    if (offsetDialog.top < offsetContentsArea) {
                        offsetDialog.top = offsetContentsArea;
                    }
                    if (offsetDialog.left < 0) {
                        offsetDialog.left = 0;
                    }
                    $container.offset(offsetDialog);
                    $dialog.dialog({ dragStop: function (event, ui) {
                            var offsetDialog = $container.offset();
                            if (offsetDialog.top < offsetContentsArea) {
                                offsetDialog.top = offsetContentsArea;
                                $container.offset(offsetDialog);
                                return false;
                            }
                            $dialog.data("stopdrop", offsetDialog);
                        } });
                    return $dialog;
                };
                $.fn.exposeOnTabPanel = function () {
                    var $target = $(this);
                    var $tabPanel = $target.closest(".ui-tabs-panel");
                    if ($tabPanel.length === 0) {
                        return $target;
                    }
                    var tabId = $tabPanel.attr("id");
                    var $tabsContainer = $tabPanel.closest(".ui-tabs");
                    $tabsContainer.exposeOnTabPanel();
                    $tabsContainer.trigger("change-tab", tabId);
                    return $target;
                };
                $.fn.ctState = function (name, method, value) {
                    var $this = $(this);
                    var dataName = {
                        selected: "ctstate-selected",
                        required: "ctstate-required",
                        name: "ctstate-name"
                    }[name];
                    switch (method) {
                        case "set":
                            return $this.data(dataName, value);
                        case "get":
                            return $this.data(dataName);
                    }
                };
                $.fn.tooltipWhenReadonly = function () {
                    var $this = $(this);
                    var border = 2;
                    $this.mouseenter(function (e) {
                        if (!$this.prop("readonly") || !$this.isOverflowContent(border)) {
                            return;
                        }
                        $this.showTextContentAsTooltip(function () { return $this.val(); });
                    });
                };
                $.fn.isOverflowContent = function (border) {
                    var $this = $(this);
                    return $this.prop("offsetWidth") - border < $this.prop("scrollWidth");
                };
                $.fn.showTextContentAsTooltip = function (textContentGetter) {
                    var $this = $(this);
                    var $view = $('<div />').addClass('limited-label-view')
                        .text(textContentGetter())
                        .appendTo('body')
                        .position({
                        my: 'left top',
                        at: 'left bottom',
                        of: $this,
                        collision: 'flip'
                    });
                    $this.bind('mouseleave.limitedlabel', function () {
                        $this.unbind('mouseleave.limitedlabel');
                        $view.remove();
                    });
                };
            })(jqueryExtentions = ui_1.jqueryExtentions || (ui_1.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=jquery-ext.js.map