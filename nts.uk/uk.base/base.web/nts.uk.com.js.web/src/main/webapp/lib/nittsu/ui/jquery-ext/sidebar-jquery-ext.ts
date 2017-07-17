/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsSideBar(action?: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

    module ntsSideBar {
    	export interface SideBarSetting {
    		active?: number;
    		beforeActivate?: (event, ui) => void,
    		activate?: (event, ui) => void,
    	};
    	export interface SideBarEventInfo {
			oldIndex: number,
			newIndex: number,
			oldTab: JQuery,
			newTab: JQuery
    	}
    	
    	var defaultOption: SideBarSetting = {
			active: 0,
			beforeActivate: function(event, info) {},
			activate: function(event, info) {},
    	};
    	
        $.fn.ntsSideBar = function(action?: string, option?: any): any {
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
            };
        }

        function init(control: JQuery, option: any): JQuery {
            $("html").addClass("sidebar-html");
            var settings: SideBarSetting = $.extend({}, defaultOption, option);
            control.find("div[role=tabpanel]").hide();
            control.off("click", "#sidebar-area .navigator a");
            control.on("click", "#sidebar-area .navigator a", function(event) {
            	event.preventDefault();
            	var info: SideBarEventInfo = {
        			oldIndex: getCurrent(control),
        			newIndex: $(this).closest("li").index(),
        			oldTab: control.find("#sidebar-area .navigator a.active").closest("li"),
        			newTab: $(this).closest("li")
            	};
                settings.beforeActivate.call(this, event, info);
                if ($(this).attr("disabled") !== "true" &&
                    $(this).attr("disabled") !== "disabled" &&
                    $(this).attr("href") !== undefined) {
                    active(control, $(this).closest("li").index());
                }
                settings.activate.call(this, event, info);
            });
            active(control, settings.active);
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