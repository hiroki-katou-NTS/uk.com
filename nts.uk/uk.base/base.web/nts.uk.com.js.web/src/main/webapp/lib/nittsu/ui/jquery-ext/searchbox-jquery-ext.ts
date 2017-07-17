/// <reference path="../../reference.ts"/>

interface JQuery {
    setupSearchScroll(controlType: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

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
//                    if (row) $grid.igGrid("virtualScrollTo", getSelectRowIndex($grid, row.id));
                    if (row) {
                        let index = $(row.element).attr("data-row-idx");
                        $grid.igGrid("virtualScrollTo", index === undefined ? getSelectRowIndex($grid, row.id) : parseInt(index));
                    }
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
        
        function getSelectRowIndex($grid: JQuery, selectedValue): number {
            let dataSource = $grid.igGrid("option", "dataSource");
            let primaryKey = $grid.igGrid("option", "primaryKey");
            return _.findIndex(dataSource, s => s[primaryKey].toString() === selectedValue.toString());        
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
}