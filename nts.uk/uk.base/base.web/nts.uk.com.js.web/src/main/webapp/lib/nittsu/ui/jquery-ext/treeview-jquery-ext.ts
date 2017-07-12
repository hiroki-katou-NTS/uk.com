/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsTreeView(action: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

    module ntsTreeView {

        let OUTSIDE_AUTO_SCROLL_SPEED = {
            RATIO: 0.2,
            MAX: 30
        };

        $.fn.ntsTreeView = function(action: string, param?: any): any {

            var $tree = $(this);

            switch (action) {
                case 'getSelected':
                    return getSelected($tree);
                case 'setSelected':
                    return setSelected($tree, param);
                case 'deselectAll':
                    return deselectAll($tree);
            }
        };

        function getSelected($tree: JQuery): any {
            if ($tree.igTreeGridSelection('option', 'multipleSelection')) {
                var selectedRows: Array<any> = $tree.igTreeGridSelection('selectedRows');
                if (selectedRows)
                    return _.map(selectedRows, convertSelected);
                return [];
            } else {
                var selectedRow: any = $tree.igTreeGridSelection('selectedRow');
                if (selectedRow)
                    return convertSelected(selectedRow);
                return undefined;
            }
        }

        function convertSelected(selectedRow: any) {
            return {
                id: selectedRow.id,
                index: selectedRow.index
            };
        }

        function setSelected($tree: JQuery, selectedId: any) {
            deselectAll($tree);

            if ($tree.igTreeGridSelection('option', 'multipleSelection')) {
                (<Array<string>>selectedId).forEach(id => $tree.igTreeGridSelection('selectRowById', id));
            } else {
                $tree.igTreeGridSelection('selectRowById', selectedId);
            }
        }

        function deselectAll($grid: JQuery) {
            $grid.igTreeGridSelection('clearSelection');
        }
    }
}