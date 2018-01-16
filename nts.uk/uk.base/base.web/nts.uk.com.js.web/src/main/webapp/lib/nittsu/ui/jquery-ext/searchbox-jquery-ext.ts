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
                    if (row) {
                        ui.ig.grid.virtual.expose(row, $grid);
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
                        ui.ig.grid.expose(row, $grid);
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
                    ui.ig.tree.grid.expandTo(row.id, $treegrid);
                }
            });
            return $treegrid;
        }

        function setupIgTreeScroll($control: JQuery) {
            var id = $control.attr('id');
            $control.on("selectChange", function() {
                var selectedRows = $control.ntsTreeDrag("getSelected");   
                if ($.isArray(selectedRows)) {
                    selectedRows = selectedRows[0];
                } 
                if (!nts.uk.util.isNullOrUndefined(selectedRows)) {
                    $control.igTree("expandToNode", selectedRows.element);
                    let index = _.findIndex($control.find("li"), function(e){
                        return  $(e).is(selectedRows.element);     
                    });
                    if(index >= 0){
                        let scrollTo = index * 29;
                        let scrollTop = $control.scrollTop();
                        let height = $control.height();
                        if(scrollTo < scrollTop || scrollTo > scrollTop + height - 28){
                            $control.scrollTop(scrollTo);     
                        }       
                    }
                }
            });
            return $control;
        }
    }
}