
module nts.uk.ui.jqueryExtentions {
    
    module ntsTreeGrid {
        
        $.fn.ntsTreeGrid = function(options: any) {
            let ROW_HEIGHT = 24;
            let HEADER_HEIGHT = 24;
            let self = this;
            let $treegrid = $(self);
            
            if (typeof options === "string") {
                return delegateMethod($treegrid, options, arguments[1]);    
            }
            
            let dataSource: Array<any> = options.dataSource;
            let optionsValue = options.primaryKey !== undefined ? options.primaryKey : options.optionsValue;
            let optionsText = options.primaryText !== undefined ? options.primaryText : options.optionsText;

            let optionsChild = options.childDataKey !== undefined ? options.childDataKey : options.optionsChild;
            let extColumns: Array<any> = options.columns !== undefined ? options.columns : options.extColumns;
            let initialExpandDepth: number = options.initialExpandDepth;
            let selectedValues: Array<any> = options.selectedValues;
            let singleValue = options.value;
            let rows = options.rows;
            let virtualization = !util.isNullOrUndefined(options.virtualization) ? options.virtualization : false;
            let virtualizationMode = !util.isNullOrUndefined(options.virtualizationMode) ? options.virtualizationMode : "";
            let multiple = !_.isNil(options.multiple) ? options.multiple : false;
            
            // Default.
            let showCheckBox = options.showCheckBox !== undefined ? options.showCheckBox : true;

            let enable = options.enable !== undefined ? options.enable : true;

            let height = options.height !== undefined ? options.height : 0; 
            let width = options.width !== undefined ? options.width : 0;

            let displayColumns;
            if (extColumns !== undefined && extColumns !== null) {
                displayColumns = extColumns;
            } else {
                displayColumns = [
                    { headerText: "コード", key: optionsValue, dataType: "string", hidden: true },
                    { headerText: "コード／名称", key: optionsText, dataType: "string" }
                ];
            }
            
            let tabIndex = nts.uk.util.isNullOrEmpty($treegrid.attr("tabindex")) ? "0" : $treegrid.attr("tabindex");
            $treegrid.attr("tabindex", "-1");
            
            let features = [];
            features.push({
                name: "Selection",
                multipleSelection: multiple,
                activation: true,
                rowSelectionChanged: function(evt: any, ui: any) {
//                    let selectedRows: Array<any> = ui.selectedRows;
//                    if (options.multiple) {
//                        selectRows($treegrid, _.map(selectedRows, function(row) {
//                            return row.id;
//                        }));
//                    } else {
//                        selectRows($treegrid, selectedRows.length <= 0 ? undefined : ui.row.id);
//                    }
                }
            });
            
            features.push({
                name: "RowSelectors",
                enableCheckBoxes: showCheckBox,
                rowSelectorColumnWidth: showCheckBox ? 25 : 0,
                enableRowNumbering: false,
                checkBoxMode: "biState"
            })
            features.push({ name: "Resizing" });
            
            if (!nts.uk.util.isNullOrEmpty(rows)) {
                height = rows * ROW_HEIGHT + HEADER_HEIGHT;   
                
                let colSettings = [];
                _.forEach(displayColumns, function (c) {
                    if (c["hidden"] === undefined || c["hidden"] === false) {
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

            $treegrid.data("expand", new koExtentions.ExpandNodeHolder());
            $treegrid.data("autoExpanding", false);
            
            // Init ig grid.
            $treegrid.igTreeGrid({
                width: width,
                height: height,
                indentation: "12px",
                dataSource: _.cloneDeep(dataSource),
                primaryKey: optionsValue,
                columns: displayColumns,
                childDataKey: optionsChild,
                initialExpandDepth: nts.uk.util.isNullOrUndefined(initialExpandDepth) ? 10 : initialExpandDepth,
                tabIndex: -1,
                features: features,
                virtualization: virtualization,
                virtualizationMode: virtualizationMode,
                rowExpanded: function (evt, ui) {
                    if (!$treegrid.data("autoExpanding")) {
                        let holder: koExtentions.ExpandNodeHolder = $treegrid.data("expand");
                        holder.addNode(ui["dataRecord"][optionsValue]);
                        $treegrid.data("expand", holder);
                    }
                }, rowCollapsed: function (evt, ui) {
                    if (!$treegrid.data("autoExpanding")) {
                        let holder: koExtentions.ExpandNodeHolder = $treegrid.data("expand");
                        holder.removeNodeAndChilds(ui["dataRecord"], optionsValue, optionsChild);
                        $treegrid.data("expand", holder);
                    }
                }, rowsRendered: function(evt, ui) {
                    $treegrid.data("autoExpanding", true);
//                    let holder: koExtentions.ExpandNodeHolder = $treegrid.data("expand");
//                    _.forEach(holder.nodes, function(node: any){
//                        $treegrid.igTreeGrid("expandRow", node); 
//                    });
                    
                    $treegrid.data("autoExpanding", false);   
                }
            });
            
            let treeGridId = $treegrid.attr('id');
            $treegrid.closest('.ui-igtreegrid').addClass('nts-treegridview').attr("tabindex", tabIndex);
            
            $treegrid.on('selectionchanged', () => {
//                if (options.multiple) { 
//                    let selected: Array<any> = $treegrid.ntsTreeView('getSelected');
//                    if (!nts.uk.util.isNullOrEmpty(selected)) {
//                        selectRows($treegrid, _.map(selected, s => s.id));
//                    } else {
//                        selectRows($treegrid);
//                    }
//                } else {
//                    let selected = $treegrid.ntsTreeView('getSelected');
//                    if (!nts.uk.util.isNullOrEmpty(selected)) {
//                        selectRows($treegrid, selected.id);
//                    } else {
//                        selectRows($treegrid);
//                    }
//                }
            });
            
            $treegrid.setupSearchScroll("igTreeGrid");
        };
        
        function selectRows($treegrid: JQuery, selectedValue: any) {
            let dataSource = $treegrid.igTreeGrid('option', 'dataSource');
            let multiple = !_.isNil(selectedValue) && selectedValue.constructor === Array;

            if (!selectedValue) {
                $treegrid.igTreeGridSelection("clearSelection");
            } else {
                let uiSR =  $treegrid.ntsTreeView('getSelected');
                if (multiple) {
                    let olds = _.map(uiSR as Array<any>, function(row: any) {
                        return row.id;
                    });
                    
                    if (_.isEqual(selectedValue.sort(), olds.sort())) {
                        return;
                    }
                    
                    $treegrid.igTreeGridSelection("clearSelection");
                    selectedValue.forEach(function(val) {
                        $treegrid.igTreeGridSelection("selectRowById", val);
                    })
                } else {
                    if (!_.isNil(uiSR) && uiSR.id === selectedValue) {
                        return;
                    }
                    $treegrid.igTreeGridSelection("clearSelection");
                    $treegrid.igTreeGridSelection("selectRowById", selectedValue);
                    
                    ui.ig.tree.grid.expandTo(selectedValue, $treegrid);
                }
            }
        }
        
        function delegateMethod($grid: JQuery, action: string, param: any) {
            switch(action) {
                case "getDataSource":
                    return $grid.igTreeGrid("option", "dataSource");
                case "setDataSource":
                    return setDataSource($grid, param);
                case "getSelected":
                    return $grid.ntsTreeView("getSelected");
                case "setSelected":
                    return selectRows($grid, param);
            }
        }
        
        function setDataSource($grid: JQuery, sources: any) {
            if (_.isNil(sources)) return;
            $grid.igTreeGrid("option", "dataSource", sources);
        }
    }
}