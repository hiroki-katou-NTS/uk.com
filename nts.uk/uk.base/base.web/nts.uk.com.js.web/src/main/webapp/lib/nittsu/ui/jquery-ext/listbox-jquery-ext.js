var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
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
                            default:
                                break;
                        }
                    };
                    function selectAll($list) {
                        var $grid = $list.find(".ntsListBox");
                        var options = $grid.igGrid("option", "dataSource");
                        var primaryKey = $grid.igGrid("option", "primaryKey");
                        _.forEach(options, function (option, idx) {
                            $grid.igGridSelection("selectRowById", option[primaryKey]);
                        });
                        $grid.triggerHandler('selectionchanged');
                    }
                    function deselectAll($list) {
                        var $grid = $list.find(".ntsListBox");
                        $grid.igGridSelection('clearSelection');
                        $grid.triggerHandler('selectionchanged');
                    }
                })(ntsListBox || (ntsListBox = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=listbox-jquery-ext.js.map