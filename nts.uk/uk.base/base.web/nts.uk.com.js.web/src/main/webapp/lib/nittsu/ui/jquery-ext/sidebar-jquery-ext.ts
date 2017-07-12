/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsSideBar(action?: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

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
}