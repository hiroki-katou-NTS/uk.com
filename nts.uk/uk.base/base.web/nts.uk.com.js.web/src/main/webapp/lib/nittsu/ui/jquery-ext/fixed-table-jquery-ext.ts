/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsFixedTable(action?: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

    module ntsFixedTable {

        $.fn.ntsFixedTable = ntsFixedTable;

        function ntsFixedTable(action?: string, options?: any): any {
            var $controls = $(this);
            if (typeof arguments[0] !== 'string') {
                return ntsFixedTable.apply($controls, _.concat("init", action));
            }

            if (action === "init") {
                return init($controls, options);
            }
            else {
                return $controls;
            };
        }

        function init(controls: JQuery, options?: any): JQuery {
            controls.each(function() {
                let $originTable = $(this);
                $originTable.addClass("fixed-table");
                let $colgroup = $originTable.find("colgroup");
                let $thead = $originTable.find("thead");

                let width = 0;
                $colgroup.find("col").each(function() {
                    width += Number($(this).attr("width").replace(/px/gi, ''));
                });
                width++;

                let setting = $.extend({ height: "auto" }, options);

                let $container = $("<div class='nts-fixed-table cf'/>");
                $originTable.after($container);

                let $headerContainer = $("<div class='nts-fixed-header-container ui-iggrid'/>").width(width);
                let $headerTable = $("<table class='fixed-table'></table>");
                $headerTable.append($colgroup.clone()).append($thead);
                $headerContainer.append($headerTable);
                $headerContainer.appendTo($container);

                $originTable.addClass("nts-fixed-body-table");
                let $bodyContainer = $("<div class='nts-fixed-body-container ui-iggrid'/>");
                let $bodyWrapper = $("<div class='nts-fixed-body-wrapper'/>");
                let bodyHeight: any = "auto";
                if (setting.height !== "auto") {
                    bodyHeight = Number(setting.height.toString().replace(/px/mi)) - $headerTable.find("thead").outerHeight();
                }
                $bodyWrapper.width(width).height(bodyHeight);
                $bodyWrapper.append($originTable);
                $bodyContainer.append($bodyWrapper);
                $container.append($bodyContainer);
            });
            return controls;
        }
    }
}