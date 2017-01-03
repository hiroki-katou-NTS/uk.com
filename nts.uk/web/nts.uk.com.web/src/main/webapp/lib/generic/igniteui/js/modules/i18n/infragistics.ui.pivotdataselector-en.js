/*!@license
* Infragistics.Web.ClientUI Pivot Data Selector localization resources 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/

/*global jQuery */
(function ($) {
    $.ig = $.ig || {};

    if (!$.ig.PivotDataSelector) {
        $.ig.PivotDataSelector = {};

        $.extend($.ig.PivotDataSelector, {
            locale: {
                invalidBaseElement: " is not supported as base element. Use DIV instead.",
                catalog: "Catalog",
                cube: "Cube",
                measureGroup: "Measure Group",
                measureGroupAll: "(All)",
                rows: "Rows",
                columns: "Columns",
                measures: "Measures",
                filters: "Filters",
                deferUpdate: "Defer Update",
                updateLayout: "Update Layout",
                selectAll: "Select All"
            }
        });
    }
})(jQuery);