/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * TreeGrid binding handler
     */
    class NtsTreeGridViewBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let ROW_HEIGHT = 24;
            let HEADER_HEIGHT = 24;
            // Get data.
            var data = valueAccessor();
            var options: Array<any> = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
            var optionsValue = ko.unwrap(data.primaryKey !== undefined ? data.primaryKey : data.optionsValue);
            var optionsText = ko.unwrap(data.primaryText !== undefined ? data.primaryText : data.optionsText);

            var optionsChild = ko.unwrap(data.childDataKey !== undefined ? data.childDataKey : data.optionsChild);
            var extColumns: Array<any> = ko.unwrap(data.columns !== undefined ? data.columns : data.extColumns);
            let initialExpandDepth: number = ko.unwrap(data.initialExpandDepth);
            var selectedValues: Array<any> = ko.unwrap(data.selectedValues);
            var singleValue = ko.unwrap(data.value);
            let rows = ko.unwrap(data.rows);
            
            // Default.
            var showCheckBox = data.showCheckBox !== undefined ? ko.unwrap(data.showCheckBox) : true;

            var enable = data.enable !== undefined ? ko.unwrap(data.enable) : true;

            var height = ko.unwrap(data.height !== undefined ? data.height : 0); 
            var width = ko.unwrap(data.width !== undefined ? data.width : 0);

            if (extColumns !== undefined && extColumns !== null) {
                var displayColumns = extColumns;
            } else {
                var displayColumns: Array<any> = [
                    { headerText: "コード", key: optionsValue, dataType: "string", hidden: true },
                    { headerText: "コード／名称", key: optionsText, dataType: "string" }
                ];
            }
            
            var $treegrid = $(element);
            let tabIndex = nts.uk.util.isNullOrEmpty($treegrid.attr("tabindex")) ? "0" : $treegrid.attr("tabindex");
            $treegrid.attr("tabindex", "-1");
            
            let features = [];
            features.push({
                name: "Selection",
                multipleSelection: true,
                activation: true,
                rowSelectionChanged: function(evt: any, ui: any) {
                    var selectedRows: Array<any> = ui.selectedRows;
                    if (ko.unwrap(data.multiple)) {
                        if (ko.isObservable(data.selectedValues)) {
                            data.selectedValues(_.map(selectedRows, function(row) {
                                return row.id;
                            }));
                        }
                    } else {
                        if (ko.isObservable(data.value)) {
                            data.value(selectedRows.length <= 0 ? undefined : ui.row.id);
                            data.value.valueHasMutated(); 
                        }
                    }
                }
            });
            features.push({
                name: "RowSelectors",
                enableCheckBoxes: showCheckBox,
                checkBoxMode: "biState"
            })
            features.push({ name: "Resizing" });
            
            if(!nts.uk.util.isNullOrEmpty(rows)){
                height = rows * ROW_HEIGHT + HEADER_HEIGHT;   
                
                let colSettings = [];
                _.forEach(displayColumns, function (c){
                    if(c["hidden"] === undefined || c["hidden"] === false){
                        colSettings.push({ columnKey: c["key"], allowTooltips: true }); 
                        if (nts.uk.util.isNullOrEmpty(c["columnCssClass"])) { 
                            c["columnCssClass"] = "text-limited";             
                        } else {
                            c["columnCssClass"] += " text-limited";
                        }
                    }      
                });
                
                features.push({
                    name: "Tooltips",
                    columnSettings: colSettings,
                    visibility: "overflow",
                    showDelay: 200,
                    hideDelay: 200
                });
                
                $treegrid.addClass("row-limited");
            }

            // Init ig grid.
            $treegrid.igTreeGrid({
                width: width,
                height: height,
                dataSource: _.cloneDeep(options),
                primaryKey: optionsValue,
                columns: displayColumns,
                childDataKey: optionsChild,
                initialExpandDepth: nts.uk.util.isNullOrUndefined(initialExpandDepth) ? 10 : initialExpandDepth,
                tabIndex: -1,
                features: features
            });
            var treeGridId = $treegrid.attr('id');
            $treegrid.closest('.ui-igtreegrid').addClass('nts-treegridview').attr("tabindex", tabIndex);
            
            $treegrid.bind('selectionchanged', () => {
                if (data.multiple) {
                    let selected: Array<any> = $treegrid.ntsTreeView('getSelected');
                    if (!nts.uk.util.isNullOrEmpty(selected)) {
                        data.selectedValues(_.map(selected, s => s.id));
                    } else {
                        data.selectedValues([]);
                    }
                } else {
                    let selected = $treegrid.ntsTreeView('getSelected');
                    if (!nts.uk.util.isNullOrEmpty(selected)) {
                        data.value(selected.id);
                    } else {
                        data.value('');
                    }
                }
            });
            
            $treegrid.setupSearchScroll("igTreeGrid");
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var options: Array<any> = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
            var selectedValues: Array<any> = ko.unwrap(data.selectedValues);
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
            } else {
                // Compare value.
                var olds = _.map($(element).igTreeGridSelection("selectedRow") as Array<any>, function(row: any) {
                    return row.id;
                });
                // Not change, do nothing.
                if (multiple) {
                    if (_.isEqual(selectedValues.sort(), olds.sort())) {
                        return;
                    }
                    // Update.
                    $(element).igTreeGridSelection("clearSelection");
                    selectedValues.forEach(function(val) {
                        $(element).igTreeGridSelection("selectRowById", val);
                    })
                } else {
                    if (olds.length > 1 && olds[0] === singleValue) {
                        return;
                    }
                    $(element).igTreeGridSelection("clearSelection");
                    $(element).igTreeGridSelection("selectRowById", singleValue);
                }
            }
        }
    }
    
    ko.bindingHandlers['ntsTreeGridView'] = new NtsTreeGridViewBindingHandler();
}