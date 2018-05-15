var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var errorMementos = {};
                var currentTabIndex = undefined;
                var ntsSideBar;
                (function (ntsSideBar) {
                    ;
                    var defaultOption = {
                        active: 0,
                        beforeActivate: function (event, info) { },
                        activate: function (event, info) { },
                    };
                    $.fn.ntsSideBar = function (action, option) {
                        var $control = $(this);
                        if (nts.uk.util.isNullOrUndefined(action) || action === "init") {
                            return init($control, option);
                        }
                        else if (action === "active") {
                            return active($control, option);
                        }
                        else if (action === "enable") {
                            return enable($control, option);
                        }
                        else if (action === "disable") {
                            return disable($control, option);
                        }
                        else if (action === "show") {
                            return show($control, option);
                        }
                        else if (action === "hide") {
                            return hide($control, option);
                        }
                        else if (action === "getCurrent") {
                            return getCurrent($control);
                        }
                        else {
                            return $control;
                        }
                        ;
                    };
                    function init(control, option) {
                        $("html").addClass("sidebar-html");
                        control.find(".sidebar-content > div[role=tabpanel]").addClass("disappear");
                        var settings = $.extend({}, defaultOption, option);
                        control.off("click.sideBarClick", "#sidebar-area .navigator a");
                        control.on("click.sideBarClick", "#sidebar-area .navigator a", function (event) {
                            event.preventDefault();
                            var info = {
                                oldIndex: getCurrent(control),
                                newIndex: $(this).closest("li").index(),
                                oldTab: control.find("#sidebar-area .navigator a.active").closest("li"),
                                newTab: $(this).closest("li")
                            };
                            if ($(this).attr("disabled") !== "true" && $(this).attr("disabled") !== "disabled") {
                                settings.beforeActivate.call(this, event, info);
                                if ($(this).attr("href") !== undefined)
                                    active(control, $(this).closest("li").index());
                                settings.activate.call(this, event, info);
                            }
                        });
                        active(control, settings.active, true);
                        return control;
                    }
                    function active(control, index, isInit) {
                        if (isInit === void 0) { isInit = false; }
                        control.find("#sidebar-area .navigator a").removeClass("active");
                        control.find("#sidebar-area .navigator a").eq(index).addClass("active");
                        control.find(".sidebar-content > div[role=tabpanel]").addClass("disappear");
                        var $displayPanel = $(control.find("#sidebar-area .navigator a").eq(index).attr("href"));
                        if ($displayPanel.length > 0) {
                            if (!isInit) {
                                if (currentTabIndex !== undefined) {
                                    errorMementos[currentTabIndex] = ui.errors.errorsViewModel().stashMemento();
                                }
                                if (errorMementos[index] !== undefined) {
                                    ui.errors.errorsViewModel().restoreFrom(errorMementos[index]);
                                }
                            }
                            currentTabIndex = index;
                            $displayPanel.removeClass("disappear");
                            setErrorPosition($displayPanel);
                        }
                        return control;
                    }
                    function setErrorPosition($displayPanel) {
                        setTimeout(function () {
                            if ($displayPanel.find(".sidebar-content-header").length > 0) {
                                $('#func-notifier-errors').addClass("show-immediately");
                                $('#func-notifier-errors').position({ my: 'left+145 top+44', at: 'left top', of: $displayPanel.find(".sidebar-content-header") });
                                $('#func-notifier-errors').removeClass("show-immediately");
                            }
                            else {
                                setErrorPosition($(".sidebar-content"));
                            }
                        }, 10);
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
                        return control.find("#sidebar-area .navigator a.active").closest("li").index();
                    }
                })(ntsSideBar || (ntsSideBar = {}));
            })(jqueryExtentions = ui_1.jqueryExtentions || (ui_1.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=sidebar-jquery-ext.js.map