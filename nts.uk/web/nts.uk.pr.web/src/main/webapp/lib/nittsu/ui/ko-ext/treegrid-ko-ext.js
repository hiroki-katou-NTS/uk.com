/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * TreeGrid binding handler
                 */
                var NtsTreeGridViewBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function NtsTreeGridViewBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsTreeGridViewBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var optionsValue = ko.unwrap(data.primaryKey !== undefined ? data.primaryKey : data.optionsValue);
                        var optionsText = ko.unwrap(data.primaryText !== undefined ? data.primaryText : data.optionsText);
                        var optionsChild = ko.unwrap(data.childDataKey !== undefined ? data.childDataKey : data.optionsChild);
                        var extColumns = ko.unwrap(data.columns !== undefined ? data.columns : data.extColumns);
                        // Default.
                        var showCheckBox = data.showCheckBox !== undefined ? ko.unwrap(data.showCheckBox) : true;
                        var enable = data.enable !== undefined ? ko.unwrap(data.enable) : true;
                        var height = ko.unwrap(data.height !== undefined ? data.height : '100%');
                        var width = ko.unwrap(data.width !== undefined ? data.width : '100%');
                        if (extColumns !== undefined && extColumns !== null) {
                            var displayColumns = extColumns;
                        }
                        else {
                            var displayColumns = [
                                { headerText: "コード", key: optionsValue, dataType: "string", hidden: true },
                                { headerText: "コード／名称", key: optionsText, dataType: "string" }
                            ];
                        }
                        // Init ig grid.
                        var $treegrid = $(element);
                        $(element).igTreeGrid({
                            width: width,
                            height: height,
                            dataSource: _.cloneDeep(options),
                            primaryKey: optionsValue,
                            columns: displayColumns,
                            childDataKey: optionsChild,
                            initialExpandDepth: 10,
                            features: [
                                {
                                    name: "Selection",
                                    multipleSelection: true,
                                    activation: true,
                                    rowSelectionChanged: function (evt, ui) {
                                        var selectedRows = ui.selectedRows;
                                        if (ko.unwrap(data.multiple)) {
                                            if (ko.isObservable(data.selectedValues)) {
                                                data.selectedValues(_.map(selectedRows, function (row) {
                                                    return row.id;
                                                }));
                                            }
                                        }
                                        else {
                                            if (ko.isObservable(data.value)) {
                                                data.value(selectedRows.length <= 0 ? undefined : selectedRows[0].id);
                                            }
                                        }
                                    }
                                },
                                {
                                    name: "RowSelectors",
                                    enableCheckBoxes: showCheckBox,
                                    checkBoxMode: "biState"
                                }]
                        });
                        var treeGridId = $treegrid.attr('id');
                        $(element).closest('.ui-igtreegrid').addClass('nts-treegridview');
                        $treegrid.setupSearchScroll("igTreeGrid");
                    };
                    /**
                     * Update
                     */
                    NtsTreeGridViewBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var selectedValues = ko.unwrap(data.selectedValues);
                        var singleValue = ko.unwrap(data.value);
                        // Update datasource.
                        var originalSource = $(element).igTreeGrid('option', 'dataSource');
                        if (!_.isEqual(originalSource, options)) {
                            $(element).igTreeGrid("option", "dataSource", _.cloneDeep(options));
                            $(element).igTreeGrid("dataBind");
                        }
                        // Set multiple data source.
                        var multiple = data.multiple != undefined ? ko.unwrap(data.multiple) : true;
                        if ($(element).igTreeGridSelection("option", "multipleSelection") !== multiple) {
                            $(element).igTreeGridSelection("option", "multipleSelection", multiple);
                        }
                        // Set show checkbox.
                        var showCheckBox = ko.unwrap(data.showCheckBox != undefined ? data.showCheckBox : true);
                        if ($(element).igTreeGridRowSelectors("option", "enableCheckBoxes") !== showCheckBox) {
                            $(element).igTreeGridRowSelectors("option", "enableCheckBoxes", showCheckBox);
                        }
                        // Clear selection.
                        if ((selectedValues === null || selectedValues === undefined) && (singleValue === null || singleValue === undefined)) {
                            $(element).igTreeGridSelection("clearSelection");
                        }
                        else {
                            // Compare value.
                            var olds = _.map($(element).igTreeGridSelection("selectedRow"), function (row) {
                                return row.id;
                            });
                            // Not change, do nothing.
                            if (multiple) {
                                if (_.isEqual(selectedValues.sort(), olds.sort())) {
                                    return;
                                }
                                // Update.
                                $(element).igTreeGridSelection("clearSelection");
                                selectedValues.forEach(function (val) {
                                    $(element).igTreeGridSelection("selectRowById", val);
                                });
                            }
                            else {
                                if (olds.length > 1 && olds[0] === singleValue) {
                                    return;
                                }
                                $(element).igTreeGridSelection("clearSelection");
                                $(element).igTreeGridSelection("selectRowById", singleValue);
                            }
                        }
                    };
                    return NtsTreeGridViewBindingHandler;
                }());
                ko.bindingHandlers['ntsTreeGridView'] = new NtsTreeGridViewBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=treegrid-ko-ext.js.map