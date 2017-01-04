/*!@license
* Infragistics.Web.ClientUI Pivot Grid localization resources 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/

/*global jQuery */
(function ($) {
    $.ig = $.ig || {};

    if (!$.ig.PivotGrid) {
        $.ig.PivotGrid = {};

        $.extend($.ig.PivotGrid, {
            locale: {
                filtersHeader: "フィルター フィールドをここにドロップ",
                measuresHeader: "データ項目をここにドロップ",
                rowsHeader: "行フィールドをここにドロップ",
                columnsHeader: "列フィールドをここにドロップ",
                disabledFiltersHeader: "フィルター フィールド",
                disabledMeasuresHeader: "データ項目",
                disabledRowsHeader: "行フィールド",
                disabledColumnsHeader: "列フィールド",
                noSuchAxis: "指定した軸はありません。"
            }
        });
    }
})(jQuery);

/*!@license
* Infragistics.Web.ClientUI Pivot Grid 16.1.20161.2145
*
* Copyright (c) 2011-2012 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on: 
*	jquery-1.9.1.js
*	jquery.ui.core.js
*	jquery.ui.widget.js
*	jquery.ui.mouse.js
*	jquery.ui.draggable.js
*	jquery.ui.droppable.js
*	infragistics.util.js
*	infragistics.datasource.js
*	infragistics.olapxmladatasource.js
*	infragistics.olapflatdatasource.js
*	infragistics.ui.shared.js
*   infragistics.ui.scroll.js
*	infragistics.ui.tree.js
*	infragistics.ui.grid.framework.js
*	infragistics.ui.grid.multicolumnheaders.js
*	infragistics.ui.pivot.shared.js
*/
/*global jQuery */
(function ($) {
    function Matrix() {
        this[0] = [];
    }
    Matrix.prototype = [];
    Matrix.prototype.set = function (row, column, value) {
        if (this.length < row + 1) {
            this.setRowCount(row + 1);
        }
        if (this[0].length < column + 1) {
            this.setColumnCount(column + 1);
        }
        this[row][column] = value;
    };
    Matrix.prototype.setRowCount = function (rowCount) {
        var i, oldRowCount = this.length;
        this.length = rowCount;
        for (i = oldRowCount; i < rowCount; i++) {
            this[i] = [];
            this[i].length = this[0].length;
        }
    };
    Matrix.prototype.setColumnCount = function (columnCount) {
        var i;
        for (i = 0; i < this.length; i++) {
            this[i].length = columnCount;
        }
    };

    var _aNull = function (val) { return val === null || val === undefined; },
        _igGrid = $.ui.igGrid.prototype.widgetFullName || $.ui.igGrid.prototype.widgetName;

    $.widget("ui.igPivotGrid", {
        css: {
            /* Class applied to the container of the pivot grid. */
            pivotGrid: "ui-igpivotgrid",
            /* Class applied to the header cells in the pivot grid. */
            pivotGridHeader: "ui-igpivotgrid-header",
            /* Classes applied to the elements, which expand the pivot headers. */
            expandButton: "ui-iggrid-headerbutton ui-icon ui-icon-plus",
            /* Classes applied to the elements, which collapse the pivot headers. */
            collapseButton: "ui-iggrid-headerbutton ui-iggrid-headerbuttonexpanded ui-icon ui-icon-minus",
            /* Classes applied to the indicator for ascending value sorting. */
            rowsAscending: "ui-iggrid-sortindicator ui-icon ui-icon-triangle-1-n",
            /* Classes applied to the indicator for descending value sorting. */
            rowsDescending: "ui-iggrid-sortindicator ui-icon ui-icon-triangle-1-s",
            /* Classes applied to the indicator for ascending header sorting in the rows. */
            headerRowsAscending: "ui-iggrid-sortindicator ui-icon ui-icon-triangle-1-n",
            /* Classes applied to the indicator for descending header sorting in the rows. */
            headerRowsDescending: "ui-iggrid-sortindicator ui-icon ui-icon-triangle-1-s",
            /* Classes applied to the indicator for ascending header sorting in the columns. */
            headerColumnsAscending: "ui-iggrid-sortindicator ui-icon ui-icon-triangle-1-n",
            /* Classes applied to the indicator for descending header sorting in the columns. */
            headerColumnsDescending: "ui-iggrid-sortindicator ui-icon ui-icon-triangle-1-s",
            /* Classes applied to the overlay, which shows when the pivot grid is loading. */
            blockArea: "ui-igpivotgrid-blockarea",
            /* Classes applied to the elements representing metadata items - in the metadata tree and the drop areas. */
            metadataItem: "ui-igpivot-metadataitem ui-widget ui-corner-all ui-state-default",
            /* Classes applied to the drop area headers. */
            dropAreaHeader: "ui-igpivot-dropareaheader ui-iggrid-header ui-widget-header",
            /* Class applied to the scroll buttons added when the hierarchies and the measures overflow the drop area. */
            scrollButton: "ui-iggrid-headerbutton",
            /* Classes applied to the scroll left button. */
            scrollLeft: "ui-icon ui-icon-triangle-1-w",
            /* Classes applied to the scroll right button. */
            scrollRight: "ui-icon ui-icon-triangle-1-e",
            /* Class applied to the drop areas. */
            dropArea: "ui-igpivot-droparea",
            /* Class applied to the drop areas, which are over the column and row headers and the data cells. */
            overlayDropArea: "ui-igpivot-overlaydroparea",
            /* Class applied to the drop areas, which can accept a drop. */
            activeDropArea: "active",
            /* Classes applied to the valid drop element. */
            dropIndicator: "ui-state-highlight",
            /* Classes applied to the invalid drop element. */
            invalidDropIndicator: "ui-state-error",
            /* Classes applied to the insert item indicator in the drop areas. */
            insertItem: "ui-igpivot-insertitem ui-state-highlight ui-corner-all",
            /* Classes applied to the metadata item drop down element. */
            metadataItemDropDown: "ui-igpivot-metadatadropdown ui-widget ui-widget-content",
            /* Classes applied to the filter icon in the metadata items. */
            filterIcon: "ui-icon ui-icon-pivot-smallfilter",
            /* Class applied to the filters drop down element. */
            filterDropDown: "ui-igpivot-filterdropdown ui-widget ui-widget-content",
            /* Class applied to the tree containing the filter members. */
            filterMembers: "ui-igpivot-filtermembers"
            ///* Class applied to the overlay element, which is over the pivot grid when a filter drop down is open. */
            //filterDropDownOverlay: "ui-igpivot-overlay"
        },
        options: {
            /* type="string|number|null"
            string The widget width can be set in pixels (px) and percentage (%).
            number The widget width can be set as a number
            null type="object" Will stretch to fit the data, if no other widths are defined.
            */
            width: null,
            /* type="string|number|null" This is the total height of the grid.
            string The widget height can be set in pixels (px) and percentage (%).
            number The widget height can be set as a number
            null type="object" Will stretch vertically to fit data, if no other heights are defined
            */
            height: null,
            /* type="object" An instance of $.ig.OlapXmlaDataSource or $.ig.OlapFlatDataSource.
            */
            dataSource: null,
            /* type="object" An object that will be used to create an instance of $.ig.OlapXmlaDataSource or $.ig.OlapFlatDataSource.
            The provided value must contain an object with settings for one of the data source types - xmlaOptions or flatDataOptions.
            */
            dataSourceOptions: {
                /* type="object" Settings for creating an instance of $.ig.OlapXmlaDataSource.
                */
                xmlaOptions: {
                    /* type="string" optional="false" The URL of the XMLA server.
                    */
                    serverUrl: null,
                    /* type="string" The catalog name.
                    */
                    catalog: null,
                    /* type="string" The name of the cube in the data source.
                    */
                    cube: null,
                    /* type="string" The name of the measure group in the data source.
                    */
                    measureGroup: null,
                    /* type="object" An object containing information about how the request to the XMLA server should be processed.
                    */
                    requestOptions: {
                        /* type="bool" The value is applied to XmlHttpRequest.withCredentials if supported by the user agent.
                        Setting this property to true will allow IE8/IE9 to make authenticated cross-origin requests to tusted domains through XmlHttpRequest instead of XDomainRequest
                        and will prompt the user for credentials.
                        */
                        withCredentials: false,
                        /* type="function" A callback to be invoked right before the request is send to the server. Extends beforeSend callback of jQuery.ajax's options object.
                        */
                        beforeSend: null
                    },
                    /* type="bool" Enables/disables caching of the XMLA result object.
                    */
                    enableResultCache: true,
                    /* type="object" Additional properties sent with every discover request.
                    The object is treated as a key/value store where each property name is used as the key and the property value as the value.
                    */
                    discoverProperties: null,
                    /* type="object" Additional properties sent with every execute request.
                    The object is treated as a key/value store where each property name is used as the key and the property value as the value.
                    */
                    executeProperties: null,
                    /* type="object" optional="true" a javascript object containing information about how the request to the xmla server should be processed 
                    */
                    mdxSettings: {
                        /* type="bool" optional="true" a value indicating whether a NON EMPTY clause is present on ROWS axis. Default value is true 
                        */
                        nonEmptyOnRows: true,
                        /* type="bool" optional="true" a value indicating whether a NON EMPTY clause is present on COLUMNS axis. Default value is true 
                        */
                        nonEmptyOnColumns: true,
                        /* type="bool" optional="true" a value indicating whether a members' set expressions on ROWS axis should be wrapped with AddCalculatedMembers MDX method. Default value is true 
                        */
                        addCalculatedMembersOnRows: true,
                        /* type="bool" optional="true" a value indicating whether a members' set expressions on COLUMNS axis should be wrapped with AddCalculatedMembers MDX method. Default value is true 
                        */
                        addCalculatedMembersOnColumns: true,
                        /* type="array" optional="true" a string array with the names of intrinsic non-context sensitive member properties applied on ROWS axis. By defult CHILDREN_CARDINALITY and PARENT_UNIQUE_NAME properties are always added to DIMENSION PROPERTIES 
                        */
                        dimensionPropertiesOnRows: [],
                        /* type="array" optional="true" a string array with the names of intrinsic non-context sensitive member properties applied on COLUMNS axis. By defult CHILDREN_CARDINALITY and PARENT_UNIQUE_NAME properties are always added to DIMENSION PROPERTIES
                        */
                        dimensionPropertiesOnColumns: []
                    }
                },
                /* type="object" Settings for creating an instance of $.ig.OlapFlatDataSource.
                */
                flatDataOptions: {
                    /* type="object" Specifies any valid data source accepted by $.ig.DataSource, or an instance of an $.ig.DataSource itself.
                    */
                    dataSource: null,
                    /* type="string" Specifies a remote URL accepted by $.ig.DataSource in order to request data from it.
                    */
                    dataSourceUrl: null,
                    /* type="string" Explicitly set data source type (such as "json"). Please refer to the documentation of $.ig.DataSource and its type property. 
                    */
                    dataSourceType: null,
                    /* type="string" see $.ig.DataSource. 
                    string Specifies the name of the property in which data records are held if the response is wrapped. 
                    null Option is ignored.
                    */
                    responseDataKey: null,
                    /* type="string" 
                    string Explicitly set data source type (such as "json"). Please refer to the documentation of $.ig.DataSource and its type property.
                    null Option is ignored.
                    */
                    responseDataType: null,
                    /* type="object" optional="false" An object containing processing instructions for the $.ig.DataSource data.
                    */
                    metadata: {
                        /* type="object" optional="false" Metadata used for the creation of the cube.
                        */
                        cube: {
                            /* type="string" optional="false" A unique name for the cube.
                            */
                            name: null,
                            /* type="string" A caption for the cube.
                            */
                            caption: null,
                            /* type="object" An object providing information about the measures' root node.
                            */
                            measuresDimension: {
                                /* type="string" A unique name for the measures dimension.
                                The default value is "Measures". This name is used to create the names of dimensions using the following pattern:
                                [<measuresDimensionMetadata.name>].[<measureMetadata.name>]
                                */
                                name: null,
                                /* type="string" A caption for the measures dimension.
                                The default value is "Measures".
                                */
                                caption: null,
                                /* type="array" An array of measure metadata objects.
                                */
                                measures: [{
                                    /* type="string" optional="false" A unique name for the measure.
                                    */
                                    name: null,
                                    /* type="string" A caption for the measure.
                                    */
                                    caption: null,
                                    /* type="function" optional="false" An aggregator function called when each cell is evaluated.
                                    Returns a value for the cell. If the returned value is null, no cell will be created in for the data source result.
                                    */
                                    aggregator: null,
                                    /* type="string" The path used when displaying the measure in the user interface. Nested folders are indicated by a backslash (\).
                                    */
                                    displayFolder: null
                                }]
                            },
                            /* type="array" An array of dimension metadata objects.
                            */
                            dimensions: [{
                                /* type="string" optional="false" A unique name for the dimension.
                                */
                                name: null,
                                /* type="string" A caption for the dimension.
                                */
                                caption: null,
                                /* type="array" An array of hierarchy metadata objects.
                                */
                                hierarchies: [{
                                    /* type="string" optional="false" A name for the hierarchy.
                                    The unique name of the hierarchy is formed using the following pattern:
                                    [<parentDimension.name>].[<hierarchyMetadata.name>]
                                    */
                                    name: null,
                                    /* type="string" A caption for the hierarchy.
                                    */
                                    caption: null,
                                    /* type="string" The path to be used when displaying the hierarchy in the user interface. 
                                    Nested folders are indicated by a backslash (\). 
                                    The folder hierarchy will appear under parent dimension node.
                                    */
                                    displayFolder: null,
                                    /* type="array" An array of level metadata objects.
                                    */
                                    levels: [{
                                        /* type="string" optional="false" A name for the level.
                                        The unique name of the level is formed using the following pattern:
                                        {<hierarchy.uniqueName>}.[<levelMetadata.name>]
                                        */
                                        name: null,
                                        /* type="string" A caption for the level.
                                        */
                                        caption: null,
                                        /* type="function" A function called for each item of the data source array when level members are created.
                                        Based on the item parameter the function should return a value that will form the $.ig.Member's name and caption.
                                        */
                                        memberProvider: null
                                    }]
                                }]
                            }]
                        }
                    }
                },
                /* type="string" A list of measure names separated by comma (,). These will be the measures of the data source.
                */
                measures: null,
                /* type="string" A list of hierarchy names separated by comma (,). These will be hierarchies in the filters of the data source.
                */
                filters: null,
                /* type="string" A list of hierarchy names separated by comma (,). These will be the hierarchies in the rows of the data source.
                */
                rows: null,
                /* type="string" A list of hierarchy names separated by comma (,). These will be the hierarchies in the columns of the data source.
                */
                columns: null
            },
            /* type="bool" Setting deferUpdate to true will not apply changes to the data source until the updateGrid method is called.
            */
            deferUpdate: false,
            /* type="bool" A boolean value indicating whether a parent in the columns is in front of its children.
            If set to true, the query set sorts members in a level in their natural order - child members immediately follow their parent members.
            If set to false the query set sorts the members in a level using a post-natural order. In other words, child members precede their parents.
            */
            isParentInFrontForColumns: false,
            /* type="bool" A boolean value indicating whether a parent in the rows is in front of its children.
            If set to true, the query set sorts members in a level in their natural order - child members immediately follow their parent members.
            If set to false the query set sorts the members in a level using a post-natural order. In other words, child members precede their parents.
            */
            isParentInFrontForRows: true,
            /* type="bool" A boolean value indicating whether the column headers should be arranged for compact header layout i.e. each hierarchy is in a single row.
            */
            compactColumnHeaders: false,
            /* type="bool" A boolean value indicating whether the row headers should be arranged for compact header layout i.e. each hierarchy is in a single column.
            */
            compactRowHeaders: true,
            /* type="standard|superCompact|tree" A value indicating whether the layout that row headers should be arranged.
            standard Each hierarchy in the rows is displayed in a separate column. The child members of a member in the rows are displayed on its right.
            superCompact Each hierarchy in the rows is displayed in a separate column. The child members of a member in the rows are displayed on above or below it (Depending on the isParentInFrontForRows setting).
            tree All hierarchies in the rows are displayed in a tree-like structure in a single column (The column's width is dependent on the defaultRowHEaderWidth, which can be set to "null" to enable the built-in auto-sizing functionality).
            */
            rowHeadersLayout: null,
            /* type="number" The indentation for every level column when the compactColumnHeaders is set to true.
            */
            compactColumnHeaderIndentation: 30, // default indentation for every level when columns are rendered in a "tree"-view structure in a compact way
            /* type="number" The indentation for every level row when the rowHeadersLayout is set to 'superCompact'.
            */
            compactRowHeaderIndentation: 20, // default indentation for every level when rows are rendered in a "tree"-view structure in a compact way
            /* type="number" Use it when you set rowHeadersLayout to "tree". This property will set a margin between the level's caption and the next level's (underlined text) caption.
            */
            rowHeaderLinkGroupIndentation: 5,
            /* type="number" The indentation for the neighboring hierarchy's level row when the rowHeaderLayout is set to 'tree'.
            */
            treeRowHeaderIndentation: 10,
            /* type="number" Specifies the width of the row headers.
            */
            defaultRowHeaderWidth: 200,
            /* type="bool" Enables sorting of the value cells in columns.
            */
            allowSorting: false,
            /* type="ascending|descending" Specifies the default sort direction for the rows.
            */
            firstSortDirection: "ascending",
            /* type="bool" Enables sorting of the header cells in rows.
            */
            allowHeaderRowsSorting: false,
            /* type="bool" Enables sorting of the header cells in columns.
            */
            allowHeaderColumnsSorting: false,
            /* type="array" An array of level sort direction items, which predefine the sorted header cells.
            */
            levelSortDirections: [{
                /* type="string" Specifies the unique name of the level, which will be sorted.
                */
                levelUniqueName: null,
                /* type="ascending|descending" optional="true" Specifies the sort direction. If no direction is specified, the level is going to be sorted in the direction specified by the firstLevelSortDirection option.
                */
                sortDirection: null,
                // Not supported in 13.1
                /* type="system|alphabetical" optional="true" Specifies what type of sorting will be applied to the header cells. If no behavior is specified, the level is going to be sorted with the behavior specified in the defaultLevelSortBehavior option.
                                    system type="string" Sorts the headers by a specified sort key.
                                    alphabetical type="string" Sorts alphabetically the header captions.
                */
                sortBehavior: null
            }],
            // Not supported in 13.1
            /* type="system|alphabetical" Specifies the default sort behavior for the levels if no sort behavior is specified in an item from the levelSortDirections option.
                                system type="string" Sorts the headers by a specified sort key.
                                alphabetical type="string" Sorts alphabetically the header captions.
            */
            defaultLevelSortBehavior: "alphabetical",
            /* type="ascending|descending" Specifies the default sort direction for the levels if no sort direction is specified in an item from the levelSortDirections option.
            */
            firstLevelSortDirection: "ascending",
            /* type="object" Options specific to the igGrid that will render the pivot grid view.
            */
            gridOptions: {
                /* type="string|number" Default column width that will be set for all columns.
                string The default column width can be set in pixels (px).
                number The default column width can be set as a number.
                */
                defaultColumnWidth: null,
                /* type="bool" Headers will be fixed if this option is set to true, and only the grid data will be scrollable.
                */
                fixedHeaders: true,
                /* type="string" Caption text that will be shown above the pivot grid header.
                */
                caption: null,
                /* type="object" A list of grid features definitions. The supported features are Resizing and Tooltips. Each feature goes with its separate options that are documented for the feature accordingly. 
                */
                features: [],
                /* type="number" Initial tabIndex attribute that will be set on the container element.
                */
                tabIndex: 0,
                /* type="bool" Enables/disables rendering of alternating row styles (odd and even rows receive different styling). Note that if a custom jQuery template is set, this has no effect and CSS for the row should be adjusted manually in the template contents.
                */
                alternateRowStyles: true,
                /* type="bool"  Enables/disables rendering of ui-state-hover classes when the mouse is over a record. This can be useful in templating scenarios, for example, where we don't want to apply hover styling to templated content.
                */
                enableHoverStyles: false
            },
            /* type="object" Settings for the drag and drop functionality of the igPivotGrid.
            */
            dragAndDropSettings: {
                /* type="parent|selector|element" Which element the draggable helper should be appended to while dragging. */
                appendTo: "body",
                /* type="boolean|selector|element|string|array" Specifies the containment for the drag helper. The area inside of which the 
                helper is contained would be scrollable while dragging.
                */
                containment: false,
                /* type="number" Specifies z-index that would be set for the drag helper. 
                */
                zIndex: 10
            },
            /* type="jQuery|element|selector" Specifies the parent for the drop downs.
            */
            dropDownParent: "body",
            /* type="bool" Disable the drag and drop for the rows drop area and the ability to use filtering and remove items from it.
            */
            disableRowsDropArea: false,
            /* type="bool" Disable the drag and drop for the columns drop area and the ability to use filtering and remove items from it.
            */
            disableColumnsDropArea: false,
            /* type="bool" Disable the drag and drop for the measures drop area and the ability to use filtering and remove items from it.
            */
            disableMeasuresDropArea: false,
            /* type="bool" Disable the drag and drop for the filters drop area and the ability to use filtering and remove items from it.
            */
            disableFiltersDropArea: false,
            /* type="bool" Hide the rows drop area.
            */
            hideRowsDropArea: false,
            /* type="bool" Hide the columns drop area.
            */
            hideColumnsDropArea: false,
            /* type="bool" Hide the measures drop area.
            */
            hideMeasuresDropArea: false,
            /* type="bool" Hide the filters drop area.
            */
            hideFiltersDropArea: false,
            /* type="function" A function that will be called to determine if an item can be moved in or dropped on an area of the pivot grid.
            paramType="string" The location where the item will be moved - igPivotGrid, igPivotDataSelector, filters, rows, columns or measures.
            paramType="string" The type of the item - Hierarchy, Measure or MeasureList.
            paramType="string" The unique name of the item.
            returnType="bool"  The function must return true if the item should be accepted.
            */
            customMoveValidation: null
        },
        events: {
            /* cancel="false" Fired after the data source has initialized.
            Function takes arguments evt and ui.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.dataSource to get a reference to the data source.
            Use ui.error to see if an error has occured during initialization.
            Use ui.metadataTreeRoot to get a reference to the root of the data source metatadata root item.
            */
            dataSourceInitialized: null,
            /* cancel="false" Fired after the data source has updated.
            Function takes arguments evt and ui.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.dataSource to get a reference to the data source.
            Use ui.error to see if an error has occured during update.
            Use ui.result to get the result of the update operation.
            */
            dataSourceUpdated: null,
            /* cancel="false" Event fired after the headers have been rendered.
            Function takes arguments evt and ui.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.grid to get a reference to the igGrid widget, which holds the headers.
            Use ui.table to get a reference to the headers table DOM element.
            */
            pivotGridHeadersRendered: null,
            /* cancel="false" Event fired after the whole grid widget has been rendered (including headers, footers, etc.).
            Function takes arguments evt and ui.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.grid to get reference to the igGrid widget, which represents the data.
            */
            pivotGridRendered: null,
            /* cancel="true" Fired before the expand of the tuple member.
            Function takes arguments evt and ui. Return false to cancel the expanding.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.dataSource to get a reference to the data source.
            Use ui.axisName to get the name of axis, which holds the member and the tuple.
            Use ui.tupleIndex to get the index of the tuple in the axis.
            Use ui.memberIndex to get the index of the member in the tuple.
            */
            tupleMemberExpanding: null,
            /* cancel="false" Fired after the expand of the tuple member.
            Function takes arguments evt and ui.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.dataSource to get a reference to the data source.
            Use ui.axisName to get the name of axis, which holds the member and the tuple.
            Use ui.tupleIndex to get the index of the tuple in the axis.
            Use ui.memberIndex to get the index of the member in the tuple.
            */
            tupleMemberExpanded: null,
            /* cancel="true" Fired before the collapse of the tuple member.
            Function takes arguments evt and ui. Return false to cancel the collapsing.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.dataSource to get a reference to the data source.
            Use ui.axisName to get the name of axis, which holds the member and the tuple.
            Use ui.tupleIndex to get the index of the tuple in the axis.
            Use ui.memberIndex to get the index of the member in the tuple.
            */
            tupleMemberCollapsing: null,
            /* cancel="false" Fired after the collapse of the tuple member.
            Function takes arguments evt and ui.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.dataSource to get a reference to the data source.
            Use ui.axisName to get the name of axis, which holds the member and the tuple.
            Use ui.tupleIndex to get the index of the tuple in the axis.
            Use ui.memberIndex to get the index of the member in the tuple.
            */
            tupleMemberCollapsed: null,
            /* cancel="true" Fired before the sorting of the columns.
            Function takes arguments evt and ui. Return false to cancel the sorting.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.sortDirections to get an array of the tuple indices and sort directions that will be used.
            */
            sorting: null,
            /* cancel="false" Fired after the sorting of the columns.
            Function takes arguments evt and ui.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.sortDirections to get an array of the tuple indices and sort directions that were passed to the table view.
            Use ui.appliedSortDirections to get an array of the tuple indices and sort directions that were actually applied to the table view.
            */
            sorted: null,
            /* cancel="true" Fired before the sorting of the headers.
            Function takes arguments evt and ui. Return false to cancel the sorting.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.levelSortDirections to get an array of the level names and sort directions that will be used.
            */
            headersSorting: null,
            /* cancel="false" Fired after the sorting of the headers.
            Function takes arguments evt and ui.
            Use ui.owner to get a reference to the pivot grid.
            Use ui.levelSortDirections to get an array of the level names and sort directions that were used.
            Use ui.appliedLevelSortDirections to get an array of the level names and sort directions that were actually applied to the table view.
            */
            headersSorted: null,
            /* cancel="true" Fired on drag start. Return false to cancel the dragging.
            Use ui.metadata	to get a reference to the data.
            Use ui.helper to get a reference to the helper.
            Use ui.offset to get a reference to the offset.
            Use ui.originalPosition to get a reference to the original position of the draggable element.
            Use ui.position to get a reference to the current position of the draggable element.
            */
            dragStart: null,
            /* cancel="true" Fired on drag. Return false to cancel the drag.
            Use ui.metadata	to get a reference to the data.
            Use ui.helper to get a reference to the helper.
            Use ui.offset to get a reference to the offset.
            Use ui.originalPosition to get a reference to the original position of the draggable element.
            Use ui.position to get a reference to the current position of the draggable element.
            */
            drag: null,
            /* cancel="false" Fired on drag stop.
            Use ui.helper to get a reference to the helper.
            Use ui.offset to get a reference to the offset.
            Use ui.originalPosition to get a reference to the original position of the draggable element.
            Use ui.position to get a reference to the current position of the draggable element.
            */
            dragStop: null,
            /* cancel="true" Fired before a metadata item drop. Return false to cancel the drop.
            Use ui.targetElement for a reference to the drop target.
            Use ui.draggedElement for a reference to the metadata item element.
            Use ui.metadata	to get a reference to the data.
            Use ui.metadataIndex to get the index at which the metadata will be inserted.
            Use ui.helper to get a reference to the helper.
            Use ui.offset to get a reference to the offset.
            Use ui.position to get a reference to the current position of the draggable element.
            */
            metadataDropping: null,
            /* cancel="false" Fired after a metadata item drop.
            Use ui.targetElement for a reference to the drop target.
            Use ui.draggedElement for a reference to the dragged element.
            Use ui.metadata	to get a reference to the data.
            Use ui.metadataIndex to get the index at which the metadata is inserted.
            Use ui.helper to get a reference to the helper.
            Use ui.offset to get a reference to the offset.
            Use ui.position to get a reference to the current position of the draggable element.
            */
            metadataDropped: null,
            /* cancel="true" Fired before a metadata item is removed when the user clicks the close icon. Return false to cancel the removing.
            Use ui.targetElement for a reference to the dragged element.
            Use ui.metadata	to get a reference to the data.
            */
            metadataRemoving: null,
            /* cancel="false" Fired after a metadata item is removed when the user clicks the close icon.
            Use ui.metadata	to get a reference to the data.
            */
            metadataRemoved: null,
            /* cancel="true" Fired before the filter members drop down opens. Return false to cancel the opening.
            Use ui.hierarchy for a reference to the hierarchy.
            */
            filterDropDownOpening: null,
            /* cancel="false" Fired after the filter members drop down opens.
            Use ui.hierarchy for a reference to the hierarchy.
            Use ui.dropDownElement for a reference to the drop down.
            */
            filterDropDownOpened: null,
            /* cancel="false" Fired after the filter members are loaded.
            Use ui.parent to get the parent node or the igTree instance in the initial load.
            Use ui.rootFilterMembers for a collection with the root filter members (deprecated).
            Use ui.filterMembers for a collection with the newly loaded filter members.
            */
            filterMembersLoaded: null,
            /* cancel="true" Fired after the OK button in the filter members drop down is clicked. Return false to cancel the applying of the filters.
            Use ui.hierarchy for a reference to the hierarchy.
            Use ui.filterMembers for a collection with the selected filter members. If all filter members are selected the collection will be empty.
            Use ui.dropDownElement for a reference to the drop down.
            */
            filterDropDownOk: null,
            /* cancel="true" Fired before the filter members drop down closes. Return false to cancel the closing.
            Use ui.hierarchy for a reference to the hierarchy.
            Use ui.dropDownElement for a reference to the drop down.
            */
            filterDropDownClosing: null,
            /* cancel="false" Fired after the filter members drop down closes.
            Use ui.hierarchy for a reference to the hierarchy.
            */
            filterDropDownClosed: null
        },
        _const: {
            emptyCell: {
                headerText: "&nbsp;",
                key: "empty_cell",
                axisName: "empty_axis",
                memberIndex: 0,
                // Setting tuple index to 0 will enable sorting.
                tupleIndex: 0
            },
            hoverScrollOffset: 5,
            hoverScrollInterval: 50
        },
        _headerScrollOffsets: {},
        _gridScrollOffsets: {
            top: 0,
            left: 0
        },
        _create: function () {
            var $this = this;

            this._onFiltersChanged = function (collection, collectionChangedArgs) {
                var dropArea = $("#" + $this.element.attr("id") + "_filters .ui-igpivot-droparea");
                $this._onDataSourceCollectionChanged(collection, collectionChangedArgs, dropArea, $this.options.disableFiltersDropArea);
				$this._updateDropArea(dropArea, $this._ds.filters(), $this.options.disableFiltersDropArea, $this.options.disableFiltersDropArea ?
						$.ig.PivotGrid.locale.disabledFiltersHeader :
						$.ig.PivotGrid.locale.filtersHeader);
                // Flag for future use.
                // $this._filtersChanged = true;
            };
            this._onRowAxisChanged = function (collection, collectionChangedArgs) {
                var dropArea = $("#" + $this.element.attr("id") + "_rows .ui-igpivot-droparea");
                $this._onDataSourceCollectionChanged(collection, collectionChangedArgs, dropArea, $this.options.disableRowsDropArea);
				$this._updateDropArea(dropArea, $this._ds.rowAxis(), $this.options.disableRowsDropArea, $this.options.disableRowsDropArea ?
						$.ig.PivotGrid.locale.disabledRowsHeader :
						$.ig.PivotGrid.locale.rowsHeader);
                // Flag for future use.
                // $this._rowsChanged = true;
            };
            this._onColumnAxisChanged = function (collection, collectionChangedArgs) {
                var dropArea = $("#" + $this.element.attr("id") + "_columns .ui-igpivot-droparea");
                $this._onDataSourceCollectionChanged(collection, collectionChangedArgs, dropArea, $this.options.disableColumnsDropArea);
				$this._updateDropArea(dropArea, $this._ds.columnAxis(), $this.options.disableColumnsDropArea, $this.options.disableColumnsDropArea ?
						$.ig.PivotGrid.locale.disabledColumnsHeader :
						$.ig.PivotGrid.locale.columnsHeader);
                $this._columnsChanged = true;
            };
            this._onMeasuresChanged = function (collection, collectionChangedArgs) {
                var dropArea = $("#" + $this.element.attr("id") + "_measures .ui-igpivot-droparea");
                $this._onDataSourceCollectionChanged(collection, collectionChangedArgs, dropArea, $this.options.disableMeasuresDropArea);
				$this._updateDropArea(dropArea, $this._ds.measures(), $this.options.disableMeasuresDropArea, $this.options.disableMeasuresDropArea ?
						$.ig.PivotGrid.locale.disabledMeasuresHeader :
						$.ig.PivotGrid.locale.measuresHeader);
                // Flag for future use.
                // $this._measuresChanged = true;
            };

            this.element.addClass(this.css.pivotGrid);

            this._setDataSource();
        },
        _setOption: function (key, value) {
            var grid;

            switch (key) {
                case "dataSource":
                    this._clearDataSource();
                    this.options.dataSourceOptions = null;
                    this.options.dataSource = this._createDataSource(value, null);
                    this._setDataSource();
                    break;
                case "dataSourceOptions":
                    this._clearDataSource();
                    this.options.dataSourceOptions = value;
                    this.options.dataSource = this._createDataSource(null, value);
                    this._setDataSource();
                    break;
                case "deferUpdate":
                    $.Widget.prototype._setOption.apply(this, arguments);
                    if (value === false) {
                        this.updateGrid();
                    }
                    break;
                case "width":
                case "height":
                    $.Widget.prototype._setOption.apply(this, arguments);
                    grid = this.grid();
                    if (grid) {
                        grid.option(key, value);
                    }
                    break;
                case "levelSortDirections":
                case "isParentInFrontForColumns":
                case "isParentInFrontForRows":
                case "compactColumnHeaders":
                case "compactRowHeaders":
                case "rowHeadersLayout":
                    $.Widget.prototype._setOption.apply(this, arguments);
                    this._createTableView(this._ds);
                    this._onGridUpdated();
                    break;
                case "dragAndDropSettings":
                    $.Widget.prototype._setOption.apply(this, arguments);
                    this.element.find(":ui-draggable").each(function (ind, el) {
                        var draggable = $(el);
                        draggable.draggable("option", "appendTo", value.appendTo);
                        draggable.draggable("option", "containment", value.containment);
                        draggable.draggable("option", "zIndex", value.zIndex);
                    });
                    break;
                default:
                    $.Widget.prototype._setOption.apply(this, arguments);
                    this._onGridUpdated();
                    break;
            }
        },
        _setDataSource: function () {
            var $this = this, dataSource;

            this._renderEmptyGrid();

            this._ds = dataSource = this._createDataSource(
                this.options.dataSource,
                this.options.dataSourceOptions
            );

            if (!dataSource) {
                return;
            }

            if (this.options.rowHeadersLayout === "tree" && 
                dataSource.dataSource().measureListLocation() === 0 &&
                dataSource.dataSource().sourceOptions().measures() && 
                dataSource.dataSource().sourceOptions().measures().contains(",")) {
                this._ds.setMeasureListIndex(0);
            }

            this.timestamp = new Date().getTime();
            $(dataSource).bind("initialized.pivotgrid" + this.timestamp, function (evt, evtArgs) {
                var args = $.extend({
                    owner: $this,
                    dataSource: $this._ds
                }, evtArgs);

                $this._triggerDataSourceInitialized(evt, args);
                $this._onGridUpdated();
            });
            $(dataSource).bind("updated.pivotgrid" + this.timestamp, function (evt, evtArgs) {
                var args = $.extend({
                    owner: $this,
                    dataSource: $this._ds
                }, evtArgs);

                $this._triggerDataSourceUpdated(evt, args);
                $this._onGridUpdated();
            });

            if (dataSource.isInitialized()) {
                dataSource.bindCollectionChanged({
                    filters: this._onFiltersChanged,
                    rowAxis: this._onRowAxisChanged,
                    columnAxis: this._onColumnAxisChanged,
                    measures: this._onMeasuresChanged
                });

                this._onGridUpdated();
            } else {
                this._updateGrid(true).always(function () {
                    dataSource.bindCollectionChanged({
                        filters: $this._onFiltersChanged,
                        rowAxis: $this._onRowAxisChanged,
                        columnAxis: $this._onColumnAxisChanged,
                        measures: $this._onMeasuresChanged
                    });
                });
            }
        },
        _updateDropArea: function (dropArea, items, isDisabled, emptyMessage) {
            if (!this._ds.isUpdating()/*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                dropArea.empty().css({
                    "display": "",
                    "overflow": "",
                    "margin": "",
                    "padding": ""
                }).siblings().remove();
                this._fillDropArea(dropArea, items, isDisabled, emptyMessage);
                this._measureDropArea(dropArea);
            }
        },
        _clearDataSource: function () {
            var grid = this.grid();

            if (this._ds) {
                $(this._ds).unbind("updated.pivotgrid");

                this._ds.unbindCollectionChanged({
                    filters: this._onFiltersChanged,
                    rowAxis: this._onRowAxisChanged,
                    columnAxis: this._onColumnAxisChanged,
                    measures: this._onMeasuresChanged
                });
            }

            if (grid) {
                grid.destroy();
                this.element.empty();
            }
        },
        _createTableView: function (dataSource) {
            var viewSettings, hasColumns, hasRows, rowHeadersLayout,
                map, appliedColumnSortDirections, appliedLevelSortDirections, userLevelSortDirections, mergedLevelSortDirections;

            // Prepare the column sort directions.
            // Clear the sorting if the hierarchies in the columns have changed.
            appliedColumnSortDirections = this._columnsChanged ? [] : this.appliedColumnSortDirections();

            // Prepare the level sort directions.
            map = function (array) {
                var i, m = {};
                if ($.isArray(array)) {
                    for (i = 0; i < array.length; i++) {
                        if (!_aNull(array[i].levelUniqueName)) {
                            m[array[i].levelUniqueName] = array[i];
                        }
                    }
                }
                return m;
            };
            appliedLevelSortDirections = map(this.appliedLevelSortDirections());
            userLevelSortDirections = map(this.options.levelSortDirections);
            // Merge the level sort directions from the UI to the ones that
            // are specified in the view settings.
            mergedLevelSortDirections = $.extend(userLevelSortDirections, appliedLevelSortDirections);
            mergedLevelSortDirections = $.map(mergedLevelSortDirections, function (sd) {
                return sd;
            });

            rowHeadersLayout = this._resolveRowHeadersLayout();

            viewSettings = {
                compactRowHeaderIndentation: this.options.compactRowHeaderIndentation,
                isParentInFrontForColumns: this.options.isParentInFrontForColumns,
                treeRowHeaderIndentation: this.options.treeRowHeaderIndentation,
                isParentInFrontForRows: this.options.isParentInFrontForRows,
                compactColumnHeaders: this.options.compactColumnHeaders,
                rowHeadersLayout: rowHeadersLayout
            };    
            
            hasColumns = dataSource.columnAxis().length > 0;
            hasRows = dataSource.rowAxis().length > 0;

            // We use the OlapTableView in order to populate an $.ig.DataSource.
            this._tableView = new $.ig.OlapTableView(dataSource.result(), hasColumns, hasRows, viewSettings);
            this._tableView.columnSortDirections(appliedColumnSortDirections);
            this._tableView.levelSortDirections(mergedLevelSortDirections);
            this._tableView.initialize();

            this._columnsChanged = false;
        },
        
        _resolveRowHeadersLayout: function() {
            if (this.options.rowHeadersLayout !== undefined && this.options.rowHeadersLayout !== null) {
                return this.options.rowHeadersLayout;
            }
            
            if (this.options.compactRowHeaders === true) {
                return "superCompact";
            }

            return "standard";
        },
        _configureOptions: function (dataSource) {
            var $this = this, cols, dataCols, data, settings, gridDataSource, options;

            this._rowsMatrix = new Matrix();
            this._fillMatrixWithHeaderCells(this._rowsMatrix, this._tableView.rowHeaders());
            this._maxMemberIndex = dataSource.columnAxis().length - 1;

            cols = this._generateAllGridColumns(this._tableView.columnHeaders());
            // rowHeaders = this._generateRowHeaders(tableView.rowHeaders());
            dataCols = this._generateDataColumns(this._tableView.columnHeaders());

            data = this._transformGridData(this._tableView.resultCells(), dataCols, dataCols.length, this._rowsMatrix.length);
            settings = { dataSource: data };
            gridDataSource = new $.ig.DataSource(settings);
            options = {
                // dataSource: gridDataSource,
                // columns: dataCols,
                // columns: cols, // multi-column headers; 
                // features: [{ 
                //      name: "MultiColumnHeaders" // multi-column headers is a requirement of the pivot grid 
                // }]
                headerRendered: function (event, ui) {
                    if ($this._ds) {
                        $this._onGridHeadersRendered(event, ui);
                    }
                },
                dataRendered: function (event, ui) {
                    if ($this._ds) {
                        $this._onDataRendered(event, ui);
                    }
                }
            };

            // Deep merge with the options.gridOptions.
            $.extend(true, options, this.options.gridOptions);

            // The data source and the columns are set now (i.e. later, after the merge)
            // in order to ensure we are overwriting anything that the developer has set.
            // The pivot grid doesn't allow any initial columns or data sources
            // to be set in its options.gridOptions.
            options.dataSource = gridDataSource;
            options.columns = cols;
            // Disable the options, which are not suppoerted by igPivotGrid.
            options.autoGenerateColumns = false;
            options.virtualization = false;
            options.fixedFooters = false;
            options.autoFormat = false;
            options.renderCheckBoxes = false;
            options.autoFitLastColumn = false;
            options.autoAdjustHeight = true;
            // For width and height use igPivotGrid's options.
            options.width = this.options.width;
            options.height = this.options.height;

            // Merge the features. the PivotGrid requires multi-column headers
            $.merge(options.features, [{ name: "MultiColumnHeaders"}]);

            return options;
        },
        _addSortingMarkup: function (sortingMarkupSettings) {
            var sortMap = sortingMarkupSettings.sortMap, stringTemplate = sortingMarkupSettings.stringTemplate, sortDirection,
                replaceString = sortingMarkupSettings.replaceString, markup = sortingMarkupSettings.markup;

            if (sortingMarkupSettings.condition) {
                sortDirection = sortMap[sortingMarkupSettings.axisName];
                if (sortDirection) {
                    sortDirection = sortDirection[sortingMarkupSettings.hierarchyOffset];
                    if (sortDirection) {
                        sortDirection = sortDirection[sortingMarkupSettings.levelNumber];
                    }
                }

                if (sortDirection === "ascending") {
                    markup = markup.replace(stringTemplate, replaceString + "='ascending'");
                    markup += sortingMarkupSettings.ascIcon;
                } else if (sortDirection === "descending") {
                    markup = markup.replace(stringTemplate, replaceString + "='descending'");
                    markup += sortingMarkupSettings.descIcon;
                } else if (this.options.allowHeaderRowsSorting) {
                    markup = markup.replace(stringTemplate, replaceString + "=''");
                }
            } else {
                markup = markup.replace(stringTemplate, "");
            }

            return markup;
        },
        _fillMatrixWithHeaderCells: function (matrix, headerCells) {
            var expandIcon, expandItemIcon, collapseIcon, collapseItemIcon, sortHeaderRowsAscendingIcon, sortHeaderRowsDescendingIcon,
                itemSortHeaderRowsAscendingIcon, itemSortHeaderRowsDescendingIcon, headerCell, levelSortDirectionsMap, headerMarkup, text, rowSpan, columnSpan,
                rowIndex, columnIndex, hierarchy, endNodeIndent, headerCellIndent, headerCellLinkIndent, axisName, tupleIndex, memberIndex, css, applyIndent,
                isExpandable, isItemExpandable, isExpanded, isItemExpanded, isTreeLayout, expandButtonMarkup, itemExpandButtonMarkup, indent, i, linkText,
                dataSource = this._ds, $this = this, expandedItems, sortingMarkupSettings;

            if (!headerCells || headerCells.length === 0) {
                // Render an empty header cell.
                matrix.set(0, 0, "<th class='ui-iggrid-header ui-widget-header'></th>");
                return;
            }

            expandIcon = "<span data-expand='true' class='" + this.css.expandButton + "' style='margin-left: $$margin$$px;'></span>";
            collapseIcon = "<span data-expand='false' class='" + this.css.collapseButton + "' style='margin-left: $$margin$$px;'></span>";
            expandItemIcon = "<span data-item-expand='true' class='" + this.css.expandButton + "' style='margin-left: $$margin$$px;'></span>";
            collapseItemIcon = "<span data-item-expand='false' class='" + this.css.collapseButton + "' style='margin-left: $$margin$$px;'></span>";
            sortHeaderRowsAscendingIcon = "<span data-sortheader='asc' class='" + this.css.headerRowsAscending + "'></span>";
            sortHeaderRowsDescendingIcon = "<span data-sortheader='desc' class='" + this.css.headerRowsDescending + "'></span>";
            itemSortHeaderRowsAscendingIcon = "<span data-item-sortheader='asc' class='" + this.css.headerRowsAscending + "'></span>";
            itemSortHeaderRowsDescendingIcon = "<span data-item-sortheader='desc' class='" + this.css.headerRowsDescending + "'></span>";

            isTreeLayout = this.options.rowHeadersLayout === "tree";
            if (isTreeLayout) {
                expandedItems = {};
                for (i = 0; i < headerCells.length; i++) {
                    if (headerCells[i].isItemExpanded()) {
                        expandedItems[i] = headerCells[i].levelNumber();
                    }
                }
            }

            levelSortDirectionsMap = this._tableView.appliedSortDirectionsMap();
            
            applyIndent = !(this.options.rowHeadersLayout === "standard" || (this.options.rowHeadersLayout === null && this.options.compactRowHeaders === false));
            for (i = 0; i < headerCells.length; i++) {
                headerCell = headerCells[i];

                text = headerCell.caption();
                rowSpan = headerCell.rowSpan();
                columnSpan = headerCell.columnSpan();
                rowIndex = headerCell.rowIndex();
                columnIndex = headerCell.columnIndex();
                axisName = headerCell.axisName();
                tupleIndex = headerCell.tupleIndex();
                memberIndex = headerCell.memberIndex();
                isExpandable = headerCell.isExpandable();
                isExpanded = headerCell.isExpanded();
                headerCellIndent = isTreeLayout ? headerCell.indent() : this.options.compactRowHeaderIndentation * headerCell.levelNumber();
                
                if (this.options.rowHeaderLinkGroupIndentation) {
                    headerCellLinkIndent = this.options.rowHeaderLinkGroupIndentation;
                }

                if (isTreeLayout) {
                    isItemExpandable = headerCell.isItemExpandable();
                    isItemExpanded = headerCell.isItemExpanded();    
                }
                
                css = "ui-iggrid-header ui-widget-header " + this.css.pivotGridHeader;

                headerMarkup = "<th $$data-sortheader$$ $$data-item-sortheader$$ " +
                    "class='" + css + "' " +
                    "title='" + text + "' " +
                    "data-skip='true' " + // A.T. important for features 
                    "rowspan='" + rowSpan + "' " +
                    "colspan='" + columnSpan + "' " +
                    "data-axis='" + axisName + "' " +
                    "data-tuple='" + tupleIndex + "' " +
                    "data-member='" + memberIndex + "'>";

                if (isItemExpandable) {
                    indent = applyIndent ? headerCell.indent() : 0;
                    itemExpandButtonMarkup = isItemExpanded ? collapseItemIcon : expandItemIcon;
                    itemExpandButtonMarkup = itemExpandButtonMarkup.replace("$$margin$$", indent);
                    headerMarkup += itemExpandButtonMarkup;
                }

                if (isExpandable) {
                    if (isTreeLayout) {
                        headerMarkup += "<span class='ui-iggrid-headertext'>" + text + "</span>";

                        sortingMarkupSettings = {
                            condition: isItemExpanded,
                            sortMap: levelSortDirectionsMap,
                            axisName: headerCell.axisName(),
                            hierarchyOffset: headerCell.hierarchyNumber() + 1,
                            levelNumber: headerCell.levelNumber() - expandedItems[i],
                            replaceString: "data-sortheader",
                            stringTemplate: "$$data-sortheader$$",
                            markup: headerMarkup,
                            ascIcon: sortHeaderRowsAscendingIcon,
                            descIcon: sortHeaderRowsDescendingIcon
                        };

                        headerMarkup = $this._addSortingMarkup(sortingMarkupSettings);

                        expandButtonMarkup = isExpanded ? collapseIcon : expandIcon;
                        expandButtonMarkup = expandButtonMarkup.replace("$$margin$$", headerCellLinkIndent);
                        headerMarkup += expandButtonMarkup;

                    	/*jshint -W083*/
                        hierarchy = dataSource.rowAxis()[headerCell.memberIndex()];
                        linkText = dataSource.getCoreElement(function(element) { 
                            return element.hierarchyUniqueName() === hierarchy.uniqueName() &&
                                    element.depth() === (headerCell.levelNumber() + 1); 
                        }, $.ig.Level.prototype.getType());
                    	/*jshint +W083*/

                        if (linkText) {
                            headerMarkup += "<span class='ui-igpivot-rowheaderlink'>" + linkText.caption() + "</span>";
                        }

                        sortingMarkupSettings = {
                            condition: isExpanded,
                            sortMap: levelSortDirectionsMap,
                            axisName: headerCell.axisName(),
                            hierarchyOffset: headerCell.hierarchyNumber(),
                            levelNumber: headerCell.levelNumber(),
                            replaceString: "data-item-sortheader",
                            stringTemplate: "$$data-item-sortheader$$",
                            markup: headerMarkup,
                            ascIcon: itemSortHeaderRowsAscendingIcon,
                            descIcon: itemSortHeaderRowsDescendingIcon
                        };

                        headerMarkup = $this._addSortingMarkup(sortingMarkupSettings);
                    } else {
                        indent = applyIndent ? headerCellIndent : 0;
                        expandButtonMarkup = isExpanded ? collapseIcon : expandIcon;
                        expandButtonMarkup = expandButtonMarkup.replace("$$margin$$", indent);

                        headerMarkup += expandButtonMarkup;
                        headerMarkup += "<span class='ui-iggrid-headertext' style='display:inline;'>" + text + "</span>";

                        sortingMarkupSettings = {
                            condition: isExpanded,
                            sortMap: levelSortDirectionsMap,
                            axisName: headerCell.axisName(),
                            hierarchyOffset: headerCell.hierarchyNumber(),
                            levelNumber: headerCell.levelNumber(),
                            replaceString: "data-sortheader",
                            stringTemplate: "$$data-sortheader$$",
                            markup: headerMarkup,
                            ascIcon: sortHeaderRowsAscendingIcon,
                            descIcon: sortHeaderRowsDescendingIcon
                        };

                        headerMarkup = $this._addSortingMarkup(sortingMarkupSettings);
                    }
                } else if (!isTreeLayout || (isTreeLayout && itemExpandButtonMarkup && itemExpandButtonMarkup.contains("$$margin$$"))) {
                    indent = applyIndent ? headerCellIndent : 0;
                    text = "<span class='ui-iggrid-headertext' style='margin-left:" + indent + "px;'>" + text + "</span>";
                    headerMarkup += text;
                } else {
                    headerMarkup += "<span class='ui-iggrid-headertext'>" + text + "</span>";
                }

                if (isTreeLayout && ((isExpandable && !isItemExpandable) || (!isExpandable && !isItemExpandable))) {
                    indent = applyIndent ? headerCellIndent : 0;
                    endNodeIndent = $(".ui-iggrid-headerbutton.ui-icon").eq(0).width() + indent;
                    headerMarkup = headerMarkup.replace("class='ui-iggrid-headertext'", "class='ui-iggrid-headertext' style='margin-left:" + endNodeIndent + "px;'");
                }

                sortingMarkupSettings = {
                    condition: isTreeLayout && isItemExpanded && headerMarkup.contains("$$data-item-sortheader$$"),
                    sortMap: levelSortDirectionsMap,
                    axisName: headerCell.axisName(),
                    hierarchyOffset: headerCell.hierarchyNumber() + 1,
                    levelNumber: headerCell.levelNumber() - 1 > 0 ? headerCell.levelNumber() - 1 : 0,
                    replaceString: "data-sortheader",
                    stringTemplate: "$$data-item-sortheader$$",
                    markup: headerMarkup,
                    ascIcon: sortHeaderRowsAscendingIcon,
                    descIcon: sortHeaderRowsDescendingIcon
                };

                headerMarkup = $this._addSortingMarkup(sortingMarkupSettings);

                sortingMarkupSettings = {
                    condition: isTreeLayout && isItemExpanded && headerMarkup.contains("$$data-item-sortheader$$"),
                    sortMap: levelSortDirectionsMap,
                    axisName: headerCell.axisName(),
                    hierarchyOffset: headerCell.hierarchyNumber() + 1,
                    levelNumber: headerCell.levelNumber() - 1 > 0 ? headerCell.levelNumber() - 1 : 0,
                    replaceString: "data-item-sortheader",
                    stringTemplate: "$$data-item-sortheader$$",
                    markup: headerMarkup,
                    ascIcon: itemSortHeaderRowsAscendingIcon,
                    descIcon: itemSortHeaderRowsDescendingIcon
                };

                headerMarkup = $this._addSortingMarkup(sortingMarkupSettings);

                headerMarkup += "</th>";

                matrix.set(rowIndex + rowSpan - 1, columnIndex + columnSpan - 1, undefined);
                matrix.set(rowIndex, columnIndex, headerMarkup);
            }
        },
        _generateAllGridColumns: function (headers) {
            // A.T. the headers collection contains a list of all headers
            // some of which are multicolumn headers
            // so whenever we reach a column with a larger 
            var cols, levels = [], i;
            if (!headers || headers.length === 0) {
                // Render an empty header cell.
                return [this._const.emptyCell];
            }

            for (i = 0; i < headers.length; i++) {
                if (!levels[headers[i].rowIndex()]) {
                    levels[headers[i].rowIndex()] = [];
                }
                levels[headers[i].rowIndex()].push(headers[i]);
            }
            this._maxLevel = levels.length;
            cols = this._processHeadersTree(levels);
            // this._processMultiColumnHeadersRecursive(cols, headers, 0, headers.length);
            return cols;
        },
        _generateDataColumns: function (headers) {
            var cols = [], i;
            if (!headers || headers.length === 0) {
                // Render an empty header cell.
                return [this._const.emptyCell];
            }

            for (i = 0; i < headers.length; i++) {
                if (headers[i].memberIndex() !== this._maxMemberIndex) {
                    // A.T. skip headers who aren't on the last level - that's the case when we have more than 1 hierarchies for the columns
                    continue;
                }
                if (headers[i].columnSpan() === 1 && (headers[i].isExpandable() === false || !headers[i].isExpanded())) {
                    cols.push({
                        headerText: headers[i].caption(),
                        key:
                            headers[i].axisName() + "_" +
                            headers[i].tupleIndex() + "_" +
                            headers[i].memberIndex() + "_" +
                            headers[i].isExpandable()
                    });
                } else if (this.options.compactColumnHeaders) {
                    cols.push({
                        headerText: headers[i].caption(),
                        key:
                            headers[i].axisName() + "_" +
                            headers[i].tupleIndex() + "_" +
                            headers[i].memberIndex() + "_" +
                            headers[i].isExpandable()
                    });
                }
            }
            return cols;
        },
        _transformGridData: function (cells, columns, columnsCount, maxRowCount) {
            // PP TFS #130374
            // pass maxRowCount as it's calculated for rowsMatrix
            var data = [], cellOrdinal, i, colIndex;
            if (!cells || cells.length === 0) {
                // PP TFS #130374
                // we still can have rows even there we have no cells generated
                if (maxRowCount > 0) {
                    for (i = 0; i < maxRowCount; i++) {
                        data.push({});
                    }
                    return data;
                }

                // Render an empty header cell.
                return [{}];
            }

            for (i = 0; i < maxRowCount; i++) {
                data.push({});
            }

            // Set the data
            for (i = 0; i < cells.length; i++) {
                cellOrdinal = cells[i].cellOrdinal();
                colIndex = cellOrdinal % columnsCount;
                // data[Math.floor(cellOrdinal / columnsCount)][columns[colIndex].key] = cells[i].value();
                // A.T. should we use formatted value or the data value, in  the data source?
                data[Math.floor(cellOrdinal / columnsCount)][columns[colIndex].key] = cells[i].formattedValue();
            }
            return data;
        },
        _processHeadersTree: function (tree) {
            var cols = [], i, j, parent, parents;
            for (i = 0; tree.length >= 1 && i < tree[0].length; i++) {
                // Push root items.
                this._createHeader(cols, tree[0][i]);
            }
            // Fill all children items, by finding their parent .
            for (i = 1; i < tree.length; i++) {
                // Process levels.
                // Insert the tree[i] items into their respective places in cols.
                parents = this._parentsForLevel(i, cols);
                for (j = 0; j < tree[i].length; j++) { // process individual header cells 
                    // Find the item's parent.
                    parent = this._findParent(parents, tree[i][j]);
                    if (parent) {
                        this._createHeader(parent, tree[i][j]);
                    } else {
                        // Go one level up.
                        if (i - 1 >= 1) {
                            this._findAndCreateUp(i - 1, i, cols, tree, tree[i][j]);
                        }
                    }
                }
            }
            return cols;
        },
        _findAndCreateUp: function (level, itemsLevel, cols, tree, item) {
            var parents, j, currentItem, parent;

            // Insert the tree[i] items into their respective places in cols.
            parents = this._parentsForLevel(level, cols);

            for (j = 0; j < tree[itemsLevel].length; j++) {
                // Process individual header cells. 
                // Find the item's parent 
                // itemsLevel - level is optional - this is the rowSpan of the potential parent,
                // in case it's not 1
                currentItem = tree[itemsLevel][j];
                if (currentItem.axisName() === item.axisName() && currentItem.columnIndex() === item.columnIndex() &&
                        currentItem.memberIndex() === item.memberIndex() && currentItem.tupleIndex() === item.tupleIndex() &&
                        currentItem.caption() === item.caption()) {
                    parent = this._findParent(parents, currentItem, itemsLevel - level + 1);
                    if (parent) {
                        this._createHeader(parent, currentItem);
                    } else {
                        // Go one level up
                        if (level - 1 >= 1) {
                            this._findAndCreateUp(level - 1, itemsLevel, cols, tree, item);
                        }
                    }
                }
            }
        },
        _parentsForLevel: function (level, root) {
            var parents = [], i;
            for (i = 0; i < root.length; i++) {
                this._parentsForLevelRecursive(parents, root[i], 1, level);
            }
            return parents;
        },
        _parentsForLevelRecursive: function (parents, currentParent, currentLevel, level) {
            var extraLevel = currentParent.rowspan > 1 ? currentParent.rowspan - 1 : 0, i;
            if (currentLevel === level) {
                // for (i = 0; currentParent.group && i < currentParent.group.length; i++) {
                //  if (currentParent.group[i]) {
                parents.push(currentParent);
                //      parents.push(currentParent);
                //  }
                // }
            } else if (currentLevel < level && currentParent.group !== null && currentParent.group !== undefined) {
                for (i = 0; i < currentParent.group.length; i++) {
                    this._parentsForLevelRecursive(parents, currentParent.group[i], currentLevel + 1 + extraLevel, level);
                }
            }
        },
        _createHeader: function (parent, col) {
            var column = {
                headerText: col.caption(),
                key: col.axisName() + "_" + col.tupleIndex() + "_" + col.memberIndex() + "_" + col.isExpandable(),
                rowspan: col.rowSpan(),
                colSpan: col.columnSpan(),
                colIndex: col.columnIndex(),
                axisName: col.axisName(),
                tupleIndex: col.tupleIndex(),
                memberIndex: col.memberIndex(),
                expandable: col.isExpandable(),
                expanded: col.isExpanded(),
                hierarchyNumber: col.hierarchyNumber(),
                levelNumber: col.levelNumber()
            };
            // There is a case where a header can have colSpan of 1, but still have children.
            if (!this.options.compactColumnHeaders && (col.columnSpan() > 1 || (col.columnSpan() === 1 && col.isExpanded() && col.isExpandable()))) {
                // multi
                column.group = [];
            }
            parent.push(column);
        },
        _findParent: function (cols, header, rowSpan) {
            // Process cols recursively, until the level maches, and the header also matches the parent.
            // Traverse cols.
            var i;
            if (!cols || cols.length === undefined || cols.length === null) {
                return null;
            }
            for (i = 0; i < cols.length; i++) {
                if (rowSpan === undefined || (rowSpan !== undefined && cols[i].rowspan === rowSpan)) {
                    // if (cols[i].colSpan  >= header.columnSpan() && header.columnIndex() >= cols[i].colIndex) {
                    if (cols[i].colSpan + cols[i].colIndex >= header.columnSpan() + header.columnIndex() && cols[i].colIndex <= header.columnIndex()) {
                        // return cols[i];
                        if (!cols[i].group) {
                            cols[i].group = [];
                        }
                        return cols[i].group;
                    }
                }
            }
            return null;
        },
        grid: function () {
            /* Returns the igGrid instance used to render the OLAP data.
            returnType="object"
            */
            return this.element.data(_igGrid);
        },
        updateGrid: function () {
            /* Triggers an update on the data source and the igPivotGrid.  
            */
            this._updateGrid(false, true);
        },
        _updateGrid: function (init, deferUpdateOverride) {
            var dataSource = this._ds, container,
                shouldUpdate = deferUpdateOverride || this.options.deferUpdate === false,
                promise;

            if (!init && !shouldUpdate) {
                return;
            }

            // Show a progress indicator - that's the case when the grid has already been created
            // and we've started expanding/collapsing.
            if (this.element.data(_igGrid)) {
                container = this.grid().container();
                $("#" + this.grid().element.attr("id") + "_columns_overlay").css("display", "none");
                $("#" + this.grid().element.attr("id") + "_measures_overlay").css("display", "none");
                container.css("position", "relative");
                // Create the block area.
                $("<div class='" + this.css.blockArea + "'></div>")
                    .appendTo(container)
                // Set the "excludeFromHeight" attribute.
                // See infragistics.ui.grid.framework.js.
                    .data("efh", "1");
            }

            if (init) {
                promise = dataSource.initialize();
            } else {
                promise = dataSource.update();
            }

            return promise;
        },
        _onGridUpdated: function () {
            var dataSource = this._ds, options;

            this._createTableView(dataSource);
            options = this._configureOptions(dataSource);
            this._renderGrid(options);
        },
        _renderEmptyGrid: function () {
            var options;
            options = $.extend(true, {}, this.options.gridOptions);
            options.width = this.options.width;
            options.height = this.options.height;
            this.element.igGrid(options);
        },
        _renderGrid: function (options) {
            var id = this.element.attr("id"), grid, container;

            grid = this.grid();
            if (grid) {
                this._gridScrollOffsets.top = grid.scrollContainer().scrollTop();
                this._gridScrollOffsets.left = $("#" + id + "_hscroller").scrollLeft();
                // Clear the widget. 
                this.element.igGrid("destroy");
                this.element.empty();
            }

            // Reinitialize the widget.
            this.element.igGrid(options);

            // Make the Grid container droppable.
            container = this.grid().container();
            container.addClass(this.css.pivotGrid);
            this._makeDroppable(container);
        },
        _onGridHeadersRendered: function (event, ui) {
            // Iterate through all column headers.
            // var headers = sender.owner.options.columns, table = sender.table;
            var $this = this, headers = ui.owner._oldCols, table = ui.table, id = this.element.attr("id"),
                appliedColumnSortDirectionsMap,
                first, firstParent, rowsCellColSpan, rowsCellRowSpan, columnsCellColSpan, filtersCellColSpan, row,
                droppableOptions, dropArea, header, dataSource = this._ds,
                extraColsCount, colgroup, col, i, length, sortableHeaders;

            table.addClass(this.css.pivotGrid);

            // We need to render the extra THs for every row.
            // For that reason we need the row's string before it's passed to the grid for rendering
            // Override the renderRecord functionality.
            ui.owner._renderRecord = function (data, rowIndex) {
                return $this._renderPivotRecord.call(this, $this._rowsMatrix, data, rowIndex);
            };

            if (headers === null || headers === undefined) {
                headers = ui.owner.options.columns;
            }

            // Prepare the column sort directions map.
            appliedColumnSortDirectionsMap = {};
            $.each(this._tableView.appliedColumnSortDirections(), function (ind, csd) {
                appliedColumnSortDirectionsMap[csd.tupleIndex] = csd.sortDirection;
            });

            // Traverse the headers.
            this._processHeaderMarkupRecursive(
                this.grid().element[0].id,
                headers,
                appliedColumnSortDirectionsMap,
                this._tableView.appliedSortDirectionsMap()
            );

            // Prepare for the extra cells.
            first = table.find("tr").first();
            firstParent = first.parent();
            // Calculate the row and column spans.
            rowsCellColSpan = this._rowsMatrix.length > 0 ? this._rowsMatrix[0].length : 1;
            rowsCellRowSpan = this._maxLevel;
            columnsCellColSpan = ui.owner.options.columns.length;
            filtersCellColSpan = rowsCellColSpan + columnsCellColSpan;
            if (table.find(".ui-igpivot-droparea[data-role=rows], .ui-igpivot-droparea[data-role=columns], .ui-igpivot-droparea[data-role=measures], .ui-igpivot-droparea[data-role=filters]").length === 0) {
                droppableOptions = this._createDropAreaOptions();
                // Rows
                if (!this.options.hideRowsDropArea) {
                    header = $("<th id='" + id + "_rows' rowSpan='" + rowsCellRowSpan + "' colSpan='" + rowsCellColSpan + "' data-skip='true' class='" + this.css.dropAreaHeader + "'></th>").prependTo(first);

                    // N.A. - Fix for bug #130370.
                    // The following line sets the height of the header explicitly
                    // so all its children with height 100% will resize to fit the
                    // available height.
                    header.height(header.height());

                    dropArea = $("<ul data-role='rows' class='" + this.css.dropArea + "'></ul>").appendTo(header);
                    if (!this.options.disableRowsDropArea/*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                        dropArea.droppable(droppableOptions);
                    }
                } else {
                    $("<th rowSpan='" + rowsCellRowSpan + "' colSpan='" + rowsCellColSpan + "' data-skip='true' class='ui-iggrid-header ui-widget-header'></th>").prependTo(first);
                }

                if (!this.options.hideColumnsDropArea || !this.options.hideMeasuresDropArea) {
                    row = $("<tr></tr>").prependTo(firstParent);
                }
                // Columns
                if (!this.options.hideColumnsDropArea) {
                    header = $("<th id='" + id + "_columns' rowSpan='1' colSpan='" + columnsCellColSpan + "' data-skip='true' class='" + this.css.dropAreaHeader + "'></th>").prependTo(row);
                    dropArea = $("<ul data-role='columns' class='" + this.css.dropArea + "'></ul>").appendTo(header);
                    if (!this.options.disableColumnsDropArea/*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                        dropArea.droppable(droppableOptions);
                        if ($("#" + id + "_columns_overlay").length < 1) {
                            $("<div id='" + id + "_columns_overlay' data-role='columns' class='" + this.css.overlayDropArea + " ui-igpivot-droparea ui-droppable'></div>")
                                .appendTo(this.element)
                                .droppable(droppableOptions);
                        }
                    }
                } else if (!this.options.hideMeasuresDropArea) {
                    $("<th rowSpan='1' colSpan='" + columnsCellColSpan + "' data-skip='true' class='ui-iggrid-header ui-widget-header'></th>").prependTo(row);
                }
                // Measures
                if (!this.options.hideMeasuresDropArea) {
                    header = $("<th id='" + id + "_measures' rowSpan='1' colSpan='" + rowsCellColSpan + "' data-skip='true' class='" + this.css.dropAreaHeader + "'></th>").prependTo(row);
                    dropArea = $("<ul data-role='measures' class='" + this.css.dropArea + "'></ul>").appendTo(header);
                    if (!this.options.disableMeasuresDropArea/*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                        dropArea.droppable(droppableOptions);
                    }
                } else if (!this.options.hideColumnsDropArea) {
                    $("<th rowSpan='1' colSpan='" + rowsCellColSpan + "' data-skip='true' class='ui-iggrid-header ui-widget-header'></th>").prependTo(row);
                }

                // Filters
                if (!this.options.hideFiltersDropArea) {
                    row = $("<tr></tr>").prependTo(firstParent);
                    header = $("<th id='" + id + "_filters' rowSpan='1' colSpan='" + filtersCellColSpan + "' data-skip='true' class='" + this.css.dropAreaHeader + "'></th>").prependTo(row);
                    dropArea = $("<ul data-role='filters' class='" + this.css.dropArea + "'></ul>").appendTo(header);
                    if (!this.options.disableFiltersDropArea/*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                        dropArea.droppable(droppableOptions);
                    }
                }
            } else {
                // Rows
                $("#" + id + "_rows").attr("rowSpan", rowsCellRowSpan).attr("colSpan", rowsCellColSpan);
                // Columns
                $("#" + id + "_columns").attr("rowSpan", 1).attr("colSpan", columnsCellColSpan);
                // Measures
                $("#" + id + "_measures").attr("rowSpan", 1).attr("colSpan", rowsCellColSpan);
                // Filters
                $("#" + id + "_fiters").attr("rowSpan", 1).attr("colSpan", filtersCellColSpan);
            }

            // Find the COLGROUPs
            colgroup = table.find("colgroup");
            // If the grid is rendered using one table element, there will be no COLGROUP element.
            if (colgroup.length === 0) {
                // Add COL elements if the grid was rendered without them.

                // Determine the number of COL elements that need to be added.
                if (this.grid()._isMultiColumnGrid) {
                    // If the grid has multi column headers (there is an actual hierarchy in the headers),
                    // count all leaf headers - they have the attribute 'data-isheadercell'.
                    length = table.find("th[data-isheadercell]").length;
                } else {
                    // Even if the grid has the multi column headers feature enabled,
                    // if the specified columns don't have an actual hierarchy,
                    // the grid will have _isMultiColumnHeader set to false and
                    // there will be no leaf headers with 'data-isheadercell'.
                    // In this case the headers are a flat list.
                    length = headers.length;
                }

                // Create the COLGROUP and fill it with COL elements.
                colgroup = "<colgroup>";
                for (i = 0; i < length; i++) {
                    colgroup += "<col></col>";
                }
                colgroup += "</colgroup>";
                // Add the COLGROUP to the headers table.
                colgroup = $(colgroup).prependTo(table);
            }
            // Add extra col elements.
            // Set the extra COL elements to the grid's table, based on the value of rowsMatrix.length
            extraColsCount = this._rowsMatrix.length > 0 ? this._rowsMatrix[0].length : 1;
            for (i = 0; i < extraColsCount; i++) {
                // PP TFS 142541; 142542
                // because of bug in jQuery 2.0.0 keep setting attribute value separate from tag creation
                col = $("<col></col>").prependTo(colgroup).attr('data-skip', 'true');
            }
			// M.H. 24 Sep 2015 Fix for bug 207137: igPivotGrid resizing is blinking when height is not set
			if (extraColsCount) {
				colgroup.attr('data-cols-injected', 'true');
			}
            // Moved this event binding to the _onDataRendered function - bug 138842.
            //table.find("span[data-expand]").bind("click", function (evt) {
            //    $this._onToggleTupleMember(evt);
            //    return false;
            //});
            if (this.options.allowSorting) {
                sortableHeaders = table.find(".ui-igpivotgrid-header[data-sort]");
                sortableHeaders.children(".ui-iggrid-headertext,.ui-iggrid-sortindicator").bind("click", function (evt) {
                    $this._onSort(evt);
                });
                sortableHeaders.filter("[data-axis='empty_axis']").bind("click", function (evt) {
                    $this._onSort(evt);
                });
            }
            if (this.options.allowHeaderColumnsSorting) {
                table.find(".ui-igpivotgrid-header[data-sortheader]").children(".ui-iggrid-headertext,.ui-iggrid-sortindicator").bind("click", function (evt) {
                    $this._onSortHeader(evt);
                });
            }

            // Fill the drop areas with metadata elements.
            if (!this.options.hideFiltersDropArea) {
                this._fillDropArea("#" + id + "_filters .ui-igpivot-droparea", dataSource.filters(),
                    this.options.disableFiltersDropArea, this.options.disableFiltersDropArea ?
                        $.ig.PivotGrid.locale.disabledFiltersHeader :
                        $.ig.PivotGrid.locale.filtersHeader);
            }
            if (!this.options.hideRowsDropArea) {
                this._fillDropArea("#" + id + "_rows .ui-igpivot-droparea", dataSource.rowAxis(),
                    this.options.disableRowsDropArea, this.options.disableRowsDropArea ?
                        $.ig.PivotGrid.locale.disabledRowsHeader :
                        $.ig.PivotGrid.locale.rowsHeader);
            }
            if (!this.options.hideColumnsDropArea) {
                this._fillDropArea("#" + id + "_columns .ui-igpivot-droparea", dataSource.columnAxis(),
                    this.options.disableColumnsDropArea, this.options.disableColumnsDropArea ?
                        $.ig.PivotGrid.locale.disabledColumnsHeader :
                        $.ig.PivotGrid.locale.columnsHeader);
            }
            if (!this.options.hideMeasuresDropArea) {
                this._fillDropArea("#" + id + "_measures .ui-igpivot-droparea", dataSource.measures(),
                    this.options.disableMeasuresDropArea, this.options.disableMeasuresDropArea ?
                        $.ig.PivotGrid.locale.disabledMeasuresHeader :
                        $.ig.PivotGrid.locale.measuresHeader);
            }

            this._measureHeaders(headers);

            // Attach to events in the headers table
            table.find("thead span.ui-iggrid-headerbutton.ui-icon").bind("click", function (evt) {
                $this._onToggleTupleMember(evt);
                return false;
            });
            
            this._triggerPivotGridHeadersRendered(event, ui);
        },
        _renderPivotRecord: function (matrix, data, rowIndex) {
            // Generate a TR and append it to the table.
            var i, key = this.options.primaryKey, /*ar = this.options.accessibilityRendering, */temp,
                grid = this, dstr = "", cols = this.options.columns, noVisibleColumns, str = "";

            // Take into account any offset.
            // A.T. TODO: refactor this so that the pivot grid doesn't know about paging.
            // There are visualization (misalignment issues with this, so commenting for now)
            // rowIndex += grid.dataSource.pageIndex() * grid.dataSource.pageSize();

            dstr += "<tr";
            if (rowIndex % 2 !== 0 && this.options.alternateRowStyles) {
                dstr += " class='" + grid.css.recordAltClass + "'";
            }
			/*jshint -W106*/
            if (!_aNull(key)) {
                dstr += " data-id='" + this._kval_from_key(key, data) + "'";
            } else if (!_aNull(data.ig_pk)) {
                dstr += " data-id='" + data.ig_pk + "'";
            }
        	/*jshint +W106*/
            // Data index to which the row is bound.
            if (this.options.virtualization && this.options.virtualizationMode === "continuous") {
                dstr += " data-row-idx='" + rowIndex + "'";
            }
            //if (ar) {
			dstr += " role='row'>";
            // } else {
                // dstr += ">";
            // }
            // var str = "", matrix = $this.element.data("igPivotGrid")._rowsMatrix;
            for (i = 0; i < matrix[rowIndex].length; i++) {
                if (matrix[rowIndex][i]) {
                    str += matrix[rowIndex][i];
                }
            }
            if (str !== "") {
                dstr += str;
            }
            noVisibleColumns = true;
            $(cols).each(function (colIndex) {
                if (cols[colIndex].hidden || cols[colIndex].fixed === true) {
                    return;
                }
                noVisibleColumns = false;
                //if (ar) {
				dstr += '<td role="gridcell" aria-describedby="' + grid.id() + '_' + this.key + '" tabindex="' + grid.options.tabIndex + '"';
                // } else {
                    // dstr += '<td';
                // }
                if (cols[colIndex].template && cols[colIndex].template.length) {
                    temp = grid._renderTemplatedCell(data, this);
                    if (temp.indexOf("<td") === 0) {
                        dstr += temp.substring(3);
                    } else {
                        dstr += '>' + temp;
                    }
                    dstr = grid._editCellStyle(dstr, data, this.key);
                } else {
                	dstr += grid._addCellStyle(data, this.key ? this.key : colIndex, cols[colIndex]) + '>' +
						grid._renderCell(data[this.key ? this.key : colIndex], this, data);
                }
                dstr += '</td>';
            });
            if (noVisibleColumns) {
                dstr += "<td role='gridcell'></td>";
            }
            dstr += "</tr>";
            return dstr;
        },
        _processHeaderMarkupRecursive: function (tableId, headers, columnSortDirectionsMap, levelSortDirectionsMap) {
            var i, headerCell, expanded, expandable, sortDirection,
                expandIcon, collapseIcon,
                allowSorting, allowHeaderColumnsSorting,
                sortRowsAscendingIcon, sortRowsDescendingIcon,
                sortHeaderColumnsAscendingIcon, sortHeaderColumnsDescendingIcon;

            allowSorting = this.options.allowSorting;
            allowHeaderColumnsSorting = this.options.allowHeaderColumnsSorting;

            expandIcon = "<span data-expand='true' class='" + this.css.expandButton + "'/>";
            collapseIcon = "<span data-expand='false' class='" + this.css.collapseButton + "'/>";
            sortRowsAscendingIcon = "<span data-sort='asc' class='" + this.css.rowsAscending + "'/>";
            sortRowsDescendingIcon = "<span data-sort='desc' class='" + this.css.rowsDescending + "'/>";
            sortHeaderColumnsAscendingIcon = "<span data-sortheader='asc' class='" + this.css.headerColumnsAscending + "'></span>";
            sortHeaderColumnsDescendingIcon = "<span data-sortheader='desc' class='" + this.css.headerColumnsDescending + "'></span>";

            for (i = 0; i < headers.length; i++) {
                headerCell = $("#" + tableId + "_" + headers[i].key);
                headerCell
                    .addClass(this.css.pivotGridHeader)
                    .attr("data-axis", headers[i].axisName)
                    .attr("data-member", headers[i].memberIndex)
                    .attr("data-tuple", headers[i].tupleIndex);

                if (headers[i].axisName !== this._const.emptyCell.axisName) {
                    headerCell.attr("title", headers[i].headerText);
                }

                // if (headerCell.find("span[data-expand], span[data-sortheader]").length > 0) {
                //     headerCell.find("span[data-expand], span[data-sortheader]").remove();
                // }

                if (this.options.compactColumnHeaders && headers[i].levelNumber > 0) {
                    headerCell.css("padding-top", this.options.compactColumnHeaderIndentation * headers[i].levelNumber);
                }

                expandable = headers[i].expandable;
                expanded = headers[i].expanded;

                if (expandable && !expanded) {
                    $(expandIcon).insertBefore(headerCell.children(".ui-iggrid-headertext"));
                } else if (expandable && expanded) {
                    $(collapseIcon).insertBefore(headerCell.children(".ui-iggrid-headertext"));

                    sortDirection = levelSortDirectionsMap[headers[i].axisName];
                    if (sortDirection) {
                        sortDirection = sortDirection[headers[i].hierarchyNumber];
                        if (sortDirection) {
                            sortDirection = sortDirection[headers[i].levelNumber];
                        }
                    }
                    if (sortDirection === "ascending") {
                        headerCell.attr("data-sortheader", "ascending");
                        $(sortHeaderColumnsAscendingIcon).appendTo(headerCell);
                    } else if (sortDirection === "descending") {
                        headerCell.attr("data-sortheader", "descending");
                        $(sortHeaderColumnsDescendingIcon).appendTo(headerCell);
                    } else if (allowHeaderColumnsSorting) {
                        headerCell.attr("data-sortheader", "");
                    }
                }

                if (!expandable || (expandable && !expanded)) {
                    sortDirection = columnSortDirectionsMap[headers[i].tupleIndex];
                    if (sortDirection === "ascending") {
                        headerCell.attr("data-sort", "ascending");
                        $(sortRowsAscendingIcon).appendTo(headerCell);
                    } else if (sortDirection === "descending") {
                        headerCell.attr("data-sort", "descending");
                        $(sortRowsDescendingIcon).appendTo(headerCell);
                    } else if (allowSorting) {
                        headerCell.attr("data-sort", "");
                    }
                }

                // Process the header's children.
                if (headers[i].group) {
                    this._processHeaderMarkupRecursive(tableId, headers[i].group, columnSortDirectionsMap, levelSortDirectionsMap);
                }
            }
        },
        _fillDropArea: function (dropAreaSelector, items, isDisabled, emptyMessage) {
            var dropArea = $(dropAreaSelector), i, length;

            // Remove old items.
            dropArea.empty();

            if (items.length > 0) {
                // Fill the drop are with items.
                for (i = 0, length = items.length; i < length; i++) {
                    this._createMetadataElement(items[i], isDisabled, "appendTo", dropArea);
                }
            } else {
                dropArea.text(emptyMessage);
            }
        },
        _measureHeaders: function (headers) {
            var $this = this, id = this.element.attr("id"), grid = this.grid(), table = grid.headersTable(),
                extraColsCount, extraColsWidth, maxWidth, header, measureTh, colsWidth;

            extraColsCount = this._rowsMatrix.length > 0 ? this._rowsMatrix[0].length : 1;

            if (this.options.defaultRowHeaderWidth) {
                // Set the user's row header width to all extra columns.
                table.find("col:lt(" + extraColsCount + ")").width(this.options.defaultRowHeaderWidth);
                // Measure all drop areas and see if they need scrolls.
                if (!$.ig.util.isTouchDevice()) {
                    table.find(".ui-igpivot-droparea[data-role=rows], .ui-igpivot-droparea[data-role=columns], .ui-igpivot-droparea[data-role=measures], .ui-igpivot-droparea[data-role=filters]")
                        .each(function () { $this._measureDropArea($(this)); });
                } else {
                    table.find(".ui-igpivot-droparea[data-role=rows], .ui-igpivot-droparea[data-role=columns], .ui-igpivot-droparea[data-role=measures], .ui-igpivot-droparea[data-role=filters]").each(function (ind, el) {
                        // Wrap the drop area with a DIV and make it an igScroll.
                        $(el).wrap($("<div style='overflow: hidden;'></div>")).parent("div").igScroll();
                    });
                }
                extraColsWidth = this.options.defaultRowHeaderWidth * extraColsCount;
            } else {
                // Find the greater width between the filters and rows drop areas.
                maxWidth = 0;
                table.find("#" + id + "_measures, #" + id + "_rows").each(function (ind, el) {
                    var clone, parent;
                    clone = $(el).clone(false);
                    clone.children(".ui-igpivot-droparea").append("<li class='" + $this.css.insertItem + "'></li>");
                    parent = $("<div class='ui-widget ui-widget-header ui-iggrid ui-igpivotgrid'></div>").append(clone).css({
                        "position": "absolute",
                        "visibility": "hidden",
                        "height": "auto",
                        "width": "auto",
                        "overflow": "visible",
                        "text-overflow": "clip",
                        "left": "-1000px",
                        "top": "-1000px"
                    }).appendTo(document.body);
                    maxWidth = Math.max(maxWidth, parent.outerWidth(true));
                    parent.remove();
                });
                // Distribute the width between the extra columns.
                table.find("col:lt(" + extraColsCount + ")").width(Math.ceil(maxWidth / extraColsCount));
                extraColsWidth = maxWidth;
            }

            // Measure the grid's headers if the grid is rendered using several tables.
            if (grid.options.defaultColumnWidth) {
                table.find("col:gt(" + extraColsCount + ")").width(grid.options.defaultColumnWidth);
            } else {
                // Get a header from the grid's table and use it to create an element,
                // which will be used to measure the leaf headers.
                header = table.find("th:not(.ui-igpivot-dropareaheader):first");
                // Prepare the measuring element.
                measureTh = $("<th class='" + header.attr("class") + "' style='position:absolute;visibility:hidden;height:auto;width:auto;overflow:visible;text-overflow:clip;left:-1000px;top:-1000px;'></th>")
                    .css({
                        "font-family": header.css("fontFamily"),
                        "font-size": header.css("fontSize"),
                        "font-size-adjust": header.css("fontSizeAdjust"),
                        "font-stretch": header.css("fontStretch"),
                        "font-style": header.css("fontStyle"),
                        "font-variant": header.css("fontVariant"),
                        "font-weight": header.css("fontWeight")
                    }).appendTo(document.body);
                // Traverse and measure the headers.
                colsWidth = this._measureHeadersRecursive(
                    this.grid().element[0].id,
                    headers,
                // Reuse the measure element for performance reasons.
                    measureTh,
                    extraColsCount
                );
                // Remove the measuring element from the DOM.
                measureTh.remove();
            }

            if (table.find("col").length - extraColsCount === 1 || (extraColsWidth + colsWidth) < table.width()) {
                // Set the width of the last column to auto so it can stretch to fill the remaining space.
                table.find("col:last").width("auto");
            }
        },
        _measureHeadersRecursive: function (tableId, headers, measureTh, extraColsCount) {
            var i, headerCell, totalWidth, width;
            totalWidth = 0;
            // Enumerate all headers.
            for (i = 0; i < headers.length; i++) {
                if (headers[i].axisName === this._const.emptyCell.axisName) {
                    // N.A. - Fix for bug #135880.
                    break;
                }

                headerCell = $("#" + tableId + "_" + headers[i].key);
                // Process the header's children.
                if (headers[i].group) {
                    // Accumulate the widths of the child headers.
                    totalWidth += this._measureHeadersRecursive(tableId, headers[i].group, measureTh, extraColsCount);
                } else {
                    // This header has no children - measure its desired width.
                    // N.A. - Add 10 to prevent ellipsis when the sort indicator appears. Not sure why the ellipsis appears.
                    width = measureTh.html(headerCell[0].innerHTML)[0].offsetWidth + 10;
                    totalWidth += width;
                    // Assign the desired width of the header to its corresponding col element.
                    this.grid().headersTable()
                        .find("col:eq(" + (headerCell.data("columnIndex") + extraColsCount) + ")")
                        .width(width);
                    // Assign width to igGrid's column so that resizing works properly.
                    // If there is no width set, a horizontal scroll will not appear when
                    // the user resizes a column.
                    headers[i].width = width;
                }
            }
            return totalWidth;
        },
        _measureDropArea: function (dropArea) {
            var $this = this, id, parent, parentClone, parentContainer, parentWidth,
                left, minLeft, maxLeft, scrollLeft, scrollRight,
                scrollToLeft, timer,
                mouseover, mouseout;

            parent = dropArea.parent("th");
            id = parent.attr("id");

            // N.A. - Fix for bug #134655.
            parentClone = $(parent).clone(false);
            parentClone.children(".ui-igpivot-droparea").append("<li class='" + $this.css.insertItem + "'></li>");
            parentContainer = $("<div class='ui-widget ui-widget-header ui-iggrid ui-igpivotgrid'></div>").append(parentClone).css({
                "position": "absolute",
                "visibility": "hidden",
                "height": "auto",
                "width": "auto",
                "overflow": "visible",
                "text-overflow": "clip",
                "left": "-1000px",
                "top": "-1000px"
            }).appendTo(document.body);

            // Determine if the drop are has items, which are not visible.
            parentWidth = parent.width();
            if (parentContainer.find(".ui-igpivot-droparea").outerWidth(true) > parentWidth) {
                dropArea.css({
                    "display": "inline-block",
                    "overflow": "hidden",
                    "margin": 0,
                    "padding": 0
                });
                // Add the left scroll button.
                scrollLeft = $("<span class='" + this.css.scrollButton + "'><a class='" + this.css.scrollLeft + "' /></span>")
                    .insertBefore(dropArea);
                // Add the right scroll button.
                scrollRight = $("<span class='" + this.css.scrollButton + "'><a class='" + this.css.scrollRight + "' /></span>")
                    .insertAfter(dropArea);
                
                if(0 === parentWidth) {
                    dropArea.width("auto");
                } else {
                    dropArea.width(parentWidth - scrollLeft.outerWidth(true) - scrollRight.outerWidth(true));
                }

                // Prepare the scroll bounds.
                left = this._headerScrollOffsets[id] || 0;
                minLeft = 0;
                maxLeft = dropArea[0].scrollWidth;
                // Prepare a scroll function, which will be called to move the drop area.
                scrollToLeft = function () {
                    // Normalize left offset for the scrolling.
                    if (left < minLeft) {
                        left = minLeft;
                    }
                    if (left > maxLeft) {
                        left = maxLeft;
                    }
                    // Move the drop area.
                    // dropArea.css("left", left);
                    dropArea.scrollLeft(left);
                    $this._headerScrollOffsets[id] = left;
                };
                scrollToLeft();
                // Handle the mouseover events of the scroll buttons.
                // setInterval is used to call the scrollToLeft function and perform smooth scrolling.
                mouseover = this._getEvent("mouseover");
                mouseout = this._getEvent("mouseout");
                scrollLeft.bind(mouseover, function () {
                    timer = setInterval(function () {
                        left -= $this._const.hoverScrollOffset;
                        scrollToLeft();
                    }, $this._const.hoverScrollInterval);
                }).bind(mouseout, function () { clearInterval(timer); });
                scrollRight.bind(mouseover, function () {
                    timer = setInterval(function () {
                        left += $this._const.hoverScrollOffset;
                        scrollToLeft();
                    }, $this._const.hoverScrollInterval);
                }).bind(mouseout, function () { clearInterval(timer); });
            } else {
                // Remove the cached scroll offset for this drop area.
                delete this._headerScrollOffsets[id];
            }

            parentContainer.remove();
        },
        _getKPIs: function (dataSource) {
            var measures, kpi, kpiObject, KPIs = [], i;

            measures =  dataSource.measures();
            for (i = measures.length - 1; i >= 0; i--) {
                kpiObject = {};
            	/*jshint -W083*/
                if("KpiMeasure" === measures[i].getType().typeName()) {
                    kpi = dataSource.getCoreElements(function(el) {
                        return el.uniqueName() === measures[i].uniqueName();
                    }, $.ig.KpiMeasure.prototype.getType());
                }
            	/*jshint +W083*/
                if (undefined !== kpi && 1 === kpi.length) {
                    if (null !== kpi[0].graphic()) {
                        kpiObject.name = kpi[0].caption();
                        kpiObject.graphic = kpi[0].graphic();
                        KPIs.push(kpiObject);
                    }
                }
            }

            return KPIs;
        },
        _getKPICells: function (KPIs, dataSource) {
            var headers, allColumns, columnIndexes, rowCells, allRows, i, j, k, m,
            rowspan, colspan, colspanValue, colspanOffset, rows;

            if (0 !== KPIs.length) {
                if (1 === dataSource.measures().length) {
                    KPIs[0].cells = $("." + this.css.pivotGrid + " td");
                } else {
                    for (m = KPIs.length - 1; m >= 0; m--) {
                        KPIs[m].cells = $();
                        headers = $();
                        headers = headers.add("th.ui-igpivotgrid-header[title='" + KPIs[m].name + "']");

                        switch (dataSource.getMeasureListLocation()) {
                            case "rows":
                                for (k = 0; k < headers.length; k++) {
                                    rowspan = parseInt(headers.eq(k).attr("rowspan"), 10);
                                    rows = headers.eq(k).parent();

                                    for (i = 0; i < rowspan; i++) {
                                        for (j = 0; j < rows.find('td').length; j++) {
                                            KPIs[m].cells = KPIs[m].cells.add(rows.find('td').eq(j)[0]);
                                        }

                                        rows = rows.next();
                                    }
                                }
                                
                                break;
                            case "columns":
                                colspanOffset = 0;
                                columnIndexes = [];
                                allColumns = headers.parent().find("th[data-skip!=true]");
                                
                                for (i = 0, j = 0; i < allColumns.length; i++) {
                                    colspanValue = parseInt(allColumns.eq(i).attr("colspan"), 10);
                                    colspan = isNaN(colspanValue) ? 1 : colspanValue;

                                    if (allColumns.eq(i)[0] === headers.eq(j)[0]) {
                                        for (k = 0; k < colspan; k++) {
                                            columnIndexes.push(i + k + colspanOffset);
                                        }
                                        
                                        j++;
                                    }

                                    colspanOffset += colspan > 1 ? colspan - 1 : 0;
                                }

                                allRows = $("." + this.css.pivotGrid + " tbody tr");
                                rowCells = allRows.find('td');
                                for (i = 0; i < allRows.length; i++) {
                                    for (j = 0, k = 0; j < rowCells.length; j++) {
                                        if (j === columnIndexes[k]) {
                                            KPIs[m].cells = KPIs[m].cells.add(allRows.eq(i).find('td').eq(j)[0]);
                                            k++;
                                        }
                                    }
                                }

                                break;
                        }
                    }
                }

                return KPIs;
            }

            return [];
        },
        _addKPIImages: function (KPIs) {
            var i, j, imgString, currentCell, val, x, y;

            for (i = 0; i < KPIs.length; i++) {
                for (j = 0; j < KPIs[i].cells.length; j++) {
                    currentCell = KPIs[i].cells.eq(j);
                    //Cylinder, Faces, Gauge, None, ReversedGauge, ReversedStatusArrow, RoadSigns, Shapes
                    //StandartdArrow, StatusArrow, Thermometer, ThreeStateImages, TrafficLight, VarianceArrow
                    if (!isNaN(currentCell.text())) {
                        imgString = "<div class='ui-igpivotgrid-kpis'";
                        imgString += " title='" + currentCell.text() + "'";
                        imgString += " style='background-position: ";

                        val = parseFloat(currentCell.text());
                        if (0 < val) {
                            x = 0;
                        } else if (0 > val) {
                            x = 32;
                        } else {
                            x = 16;
                        }

                        switch (KPIs[i].graphic.toLowerCase()) {
                            case "road signs":
                                y = 0;
                                break;
                            case "traffic light":
                                y = 16;
                                break;
                            case "variance arrow":
                                y = 32;
                                break;
                            case "standart arrow":
                                y = 48;
                                break;
                            case "status arrow - ascending":
                                y = 64;
                                break;
                            case "status arrow - descending":
                                y = 80;
                                break;
                            case "faces":
                                y = 96;
                                break;
                            case "shapes":
                                y = 112;
                                break;
                            case "cylinder":
                                y = 128;
                                break;
                            case "gauge - ascending":
                                y = 144;
                                break;
                            case "gauge - descending":
                                y = 160;
                                break;
                            case "thermometer":
                                y = 176;
                                break;
                        }

                        imgString += "-" + x + "px -" + y + "px";
                        imgString += "'></div>";
                        KPIs[i].cells.eq(j)[0].innerHTML = imgString;
                    }
                }
            }
        },
        _adjustRowHeadersWidth: function (grid) {
            var maxWidth = 0, childrenWidth, headers, headerChildren, rowsDropAreaWidth, row;

            if (this.options.height !== null) {
                headers = grid.element.find('th');
            } else {
                headers = grid.headersTable().find("tbody th");
            }
            
            headers.each(function() {
                childrenWidth = parseInt($(this).css("border-right-width").replace("px", ""), 10);

                headerChildren = $(this).children();
                headerChildren.each(function() {
                    childrenWidth += parseInt($(this).css("margin-left").replace("px", ""), 10);
                    childrenWidth += $(this).width();
                });

                maxWidth = maxWidth < childrenWidth ? childrenWidth : maxWidth;
            });

            grid.headersTable().find("col").eq(0).width(maxWidth + headers.outerWidth() - headers.width());

            //Adjust the width neccessary to display the row's dimensions
            row = "#" + this.element[0].id + "_rows";
            rowsDropAreaWidth = $(row).width() - (2 * $(row + " span").width());
            $(row + " ul").width(rowsDropAreaWidth);
        },
        _onDataRendered: function (event, ui) {
            var $this = this, id = this.element.attr("id"), table = ui.owner.element, grid = this.grid(),
                overlayDropAreaOptions = this._createDropAreaOptions(), dataSource = this._ds, headersCols,
                colgroup, i, j, colAttributes, KPIs, headerCell, sortableItems, headerCells, headerItemCells;

            // TFS 188134 - making the tree layout's row header auto-sizing respect the defaultRowHeaderWidth property
            if ($this.options.rowHeadersLayout === "tree" && !$this.options.defaultRowHeaderWidth && this._ds.rowAxis().length !== 0) {
                $this._adjustRowHeadersWidth(grid);
            }

            if ("OlapXmlaDataSource" === dataSource.getType().typeName()) {
                KPIs = this._getKPIs(dataSource);
                KPIs = this._getKPICells(KPIs, dataSource);

                this._addKPIImages(KPIs, dataSource);
            }
            
            table.addClass(this.css.pivotGrid);

            // Attach to events in the data table.
            table.find("tbody th > span[data-expand]").bind("click", function (evt) {
                $this._onToggleTupleMember(evt);
                return false;
            });

            table.find("tbody th > span[data-item-expand]").bind("click", function (evt) {
                $this._onToggleTupleMember(evt);
                return false;
            });

            // table.find("tbody th a.ui-igpivot-rowheaderlink").bind("click", function (evt) {
            //     evt.preventDefault();
            //     $this._onToggleTupleMember(evt);
            //     return false;
            // });

            if (this.options.allowHeaderRowsSorting) {
                if (this.options.rowHeadersLayout === "tree") {
                    headerCell = "tbody th.ui-igpivotgrid-header[data-item-sortheader],tbody th.ui-igpivotgrid-header[data-sortheader]";
                    sortableItems = ".ui-iggrid-sortindicator";

                    headerItemCells = table.find(headerCell).find("span[data-item-expand]");
                    for (i = 0; i < headerItemCells.length; i++) {
                        if ($(headerItemCells[i]).data().itemExpand === false) {
                            sortableItems += ",.ui-iggrid-headertext:eq(" + i + ")";
                        }
                    }

                    headerCells = table.find(headerCell).find("span[data-expand]");
                    for (i = 0; i < headerCells.length; i++) {
                        if ($(headerCells[i]).data().expand === false) {
                            sortableItems += ",.ui-igpivot-rowheaderlink:eq(" + i + ")";
                        }
                    }
                } else {
                    headerCell = "tbody th.ui-igpivotgrid-header[data-sortheader]";
                    sortableItems = ".ui-iggrid-headertext,.ui-iggrid-sortindicator";
                }

                table.find(headerCell).children(sortableItems).bind("click", function (evt) {
                    $this._onSortHeader(evt);
                });    
            }

            // fixedHeaders will be true if the grid is rendered
            // using two table elements.
            if (grid.options.fixedHeaders) {
                // Find the COLGROUPs
                // MS TFS 143250
                headersCols = grid.headersTable().find("colgroup").find('col');
                colgroup = table.find("colgroup");
                
                colgroup.empty(); 
                for(i = 0; i < headersCols.length; i++) {
                    colgroup.append($("<col></col>"));
                    colAttributes = headersCols.eq(i)[0].attributes;
                    for(j = 0; j < colAttributes.length; j++) {
                        colgroup.find('col').eq(i).attr(colAttributes.item(j).name, colAttributes.item(j).value);
                    }
                }

                // Update the horizontal scrollbar for the grid.
                grid._setGridContentWidth(grid.headersTable().width());
                // Scroll to the previous positions.
                grid.scrollContainer().scrollTop(this._gridScrollOffsets.top);
                $("#" + id + "_hscroller").scrollLeft(this._gridScrollOffsets.left);
            }

            if (!this.options.disableRowsDropArea/*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                if ($("#" + id + "_rows_overlay").length < 1) {
                    $("<div id='" + id + "_rows_overlay' data-role='rows' class='" + this.css.overlayDropArea + " ui-igpivot-droparea ui-droppable'></div>")
                    .appendTo(this.element)
                    .droppable(overlayDropAreaOptions);
                }
            }
            if (!this.options.disableMeasuresDropArea/*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                if ($("#" + id + "_measures_overlay").length < 1) {
                    $("<div id='" + id + "_measures_overlay' data-role='measures' class='" + this.css.overlayDropArea + " ui-igpivot-droparea ui-droppable'></div>")
                    .appendTo(this.element)
                    .droppable(overlayDropAreaOptions);
                }
            }

            this._positionOverlayDropAreas();
            this._triggerPivotGridRendered(event, ui);
        },
        _positionOverlayDropAreas: function () {
            var id = this.element.attr("id"),
                headersTable = this.grid().headersTable().find("thead"),
                dataTable = this.grid().element.find("tbody"),
                firstColumnHeaderPosition,
                firstRowHeaderPosition,
                columnHeadersHeight,
                rowHeadersWidth,
                measuresArea,
                container,
                containerParent,
                containerPosition,
                columnsOverlayTop,
                columnsOverlayLeft,
                columnHeadersWidth,
                measuresHeadersWidth,
                dataTableHeight,
                rowsOverlayTop,
                rowsOverlayLeft,
                containerId,
                rowHeight,
                ht,
                firstRow,
                rowsProps,
                columnsProps,
                measuresProps,
                absoluteOffset;

            // Get the position of the first column header.
            firstColumnHeaderPosition = headersTable.find("th[data-axis]:first").position();

            // Calculate the height of the column header rows.
            columnHeadersHeight = 0;
            if ($.ig.util.isFF) {
                columnHeadersHeight = headersTable.find("tr[data-header-row], tr[data-mch-level]")[0].scrollHeight;
            } else {
                headersTable.find("tr[data-header-row], tr[data-mch-level]").each(function (ind, el) {
                    columnHeadersHeight += el.scrollHeight;
                });
            }

            // Position the columns overlay drop area.          
            if ((this.options.height !== null) || (this.options.width !== null)) {
                containerId = this.element.attr('id');
                container = $("#" + containerId + "_container").length === 1 ? $("#" + containerId + "_container") : $("#" + containerId + "_table_container");
                containerParent = container.parent();
                containerPosition = container.position();
            }

            if (this.options.height !== null) {
                firstRowHeaderPosition = {
                    "top": this.grid().headersTable().height() + containerPosition.top,
                    "left": containerPosition.left
                };

            } else if (this.options.width !== null) {
                rowHeight = this.grid().headersTable().find("tr").eq(0).height();
                if (this.grid().headersTable().find("tr[data-mch-level]").length === 0) {
                    firstRowHeaderPosition = {
                        "top": (3 * rowHeight) + containerPosition.top,
                        "left": containerPosition.left
                    };
                } else {
                    firstRowHeaderPosition = {
                        "top": (2 * rowHeight) + (this.grid().headersTable().find("tr[data-mch-level]").length * this.grid().headersTable().find("tr[data-mch-level]").eq(0).height()) + containerPosition.top,
                        "left": containerPosition.left
                    };
                }
            }

            if ((this.options.height !== null) || (this.options.width !== null)) {
                columnsOverlayTop = firstColumnHeaderPosition.top + containerPosition.top + (2 * parseInt(containerParent.css("paddingTop")[0].replace("px", ""), 10));
                columnsOverlayLeft = firstColumnHeaderPosition.left + containerPosition.left;
                rowsOverlayTop = firstRowHeaderPosition.top + (2 * parseInt(containerParent.css("paddingTop")[0].replace("px", ""), 10));
                rowsOverlayLeft = firstRowHeaderPosition.left;

            } else {
                firstRowHeaderPosition = this.grid().headersTable().find("tbody tr:first").position();
                columnsOverlayTop = firstColumnHeaderPosition.top + headersTable.parent().parent().position().top; 
                columnsOverlayLeft = firstColumnHeaderPosition.left + headersTable.parent().parent().position().left;
                rowsOverlayTop = firstRowHeaderPosition.top + headersTable.parent().parent().position().top;
                rowsOverlayLeft = firstRowHeaderPosition.left + headersTable.parent().parent().position().left;
            }
 
            if (this.options.hideRowsDropArea && this.options.hideColumnsDropArea &&
                this.options.hideMeasuresDropArea && this.options.hideFiltersDropArea) {
                firstRow = headersTable.find("tr").eq(0).find("th");
            } else {
                firstRow = headersTable.find("tr").eq(1).find("th");
            }

            ht = firstRow.eq(1);
            if (headersTable[0].scrollWidth <= this.element.width()) {
                columnHeadersWidth = ht.width() + (2 * (parseInt(ht.css("paddingLeft")[0].substr(0), 10) + parseInt(ht.css("borderLeftWidth")[0].substr(0), 10)));
            } else {
                columnHeadersWidth = ht.width() + (2 * (parseInt(ht.css("paddingLeft")[0].substr(0), 10) + parseInt(ht.css("borderLeftWidth")[0].substr(0), 10))) - (headersTable[0].scrollWidth - this.element.width());
            }

            // Get the width of the row headers.
            measuresArea = firstRow.eq(0);
            dataTableHeight = this.element.height() - rowsOverlayTop > dataTable[0].scrollHeight ? dataTable[0].scrollHeight : this.element.height() - headersTable.height();
            rowHeadersWidth = measuresArea.width() + (2 * (parseInt(measuresArea.css("paddingLeft")[0].replace("px", ""), 10) + parseInt(measuresArea.css("borderLeftWidth")[0].replace("px", ""), 10)));
            if (headersTable[0].scrollWidth <= this.element.width()) {
                measuresHeadersWidth = dataTable[0].scrollWidth - rowHeadersWidth;
            } else {
                measuresHeadersWidth = dataTable[0].scrollWidth - rowHeadersWidth - (headersTable[0].scrollWidth - this.element.width());
            }

            absoluteOffset = this._calculateOffset(this.element);
            rowsProps = {
                "top": rowsOverlayTop + absoluteOffset.top,
                "left": absoluteOffset.left,
                "width": rowHeadersWidth,
                "height": dataTableHeight
            };

            columnsProps = {
                "top": columnsOverlayTop + absoluteOffset.top,
                "left": rowHeadersWidth + absoluteOffset.left,
                "width": columnHeadersWidth,
                "height": columnHeadersHeight
            };

            measuresProps = {
                "top": rowsOverlayTop + absoluteOffset.top,
                "left": rowHeadersWidth + absoluteOffset.left,
                "width": measuresHeadersWidth,
                "height": dataTableHeight
            };

            $("#" + id + "_rows_overlay").css(rowsProps);
            $("#" + id + "_columns_overlay").css(columnsProps);
            $("#" + id + "_measures_overlay").css(measuresProps);

            headersTable.parent().parent().on(
                "scroll",
                { 
                    table: headersTable, 
                    id: id,
                    rowHeadersWidth: rowHeadersWidth,
                    columnHeadersWidth: columnHeadersWidth,
                    rowsOverlayLeft: rowsOverlayLeft,
                    measuresHeadersWidth: measuresHeadersWidth,
                    columnsOverlayLeft: columnsOverlayLeft
                },
                this._handlePivotGridScrolling
            );
        },
        _calculateOffset: function(jQueryObj) {
            var el = jQueryObj, offset = { top: 0, left: el.offset().left };

            while (el[0].tagName && el.scrollParent()[0].firstChild.name !== "html") {
                offset.top += el.scrollParent().scrollTop();
                offset.left += el.scrollParent().scrollLeft();

                el = el.scrollParent();
            }

            return offset;
        },
        _handlePivotGridScrolling: function(evt) {
            var scrollTable = evt.data.table.parent().parent(), 
                id = evt.data.id, 
                offset = scrollTable.scrollLeft(),
                rowHeadersWidth = evt.data.rowHeadersWidth,
                columnHeadersWidth = evt.data.columnHeadersWidth,
                rowsOverlayLeft = evt.data.rowsOverlayLeft,
                measuresHeadersWidth = evt.data.measuresHeadersWidth,
                columnsOverlayLeft = evt.data.columnsOverlayLeft;

            $("#" + id + "_columns_overlay").css({
                "left": columnsOverlayLeft - (rowHeadersWidth > offset ? offset : rowHeadersWidth),
                "width": rowHeadersWidth > offset ? columnHeadersWidth + offset : scrollTable.width()
            });

            $("#" + id + "_rows_overlay").css({
                "width": rowHeadersWidth - offset
            });

            $("#" + id + "_measures_overlay").css({
                "left": rowsOverlayLeft + rowHeadersWidth - (rowHeadersWidth > offset ? offset : rowHeadersWidth),
                "width": rowHeadersWidth > offset ? measuresHeadersWidth + offset : scrollTable.width()
            });
        },
        _updateDataSource: function (deferUpdateOverride) {
            this._updateGrid(false, deferUpdateOverride);
        },
        _shouldAppendToTarget: function (target, dragged) {
            // Calculate the top of the dragged element and the drop target.
            return target.offset().left + target.width() / 2 < dragged.offset.left + this._const.dragCursorAt.left;
        },
        _onToggleTupleMember: function (evt) {
            var $this = this, target = $(evt.target), header = target.closest("th"),
                axisName, memberIndex, tupleIndex, expand,
                noCancel;

            // N.A. - Fix for bug #130737 - Control hangs on double click over expand/collapse button
            if (!(this._ds.dataSource().measures().inner().count() > 1 && this.options.rowHeadersLayout === "tree" && this._ds.dataSource().measureListLocation() === 0) && 
                (this._performsToggle || this._ds.isModified())) {
                return;
            }

            axisName = header.attr("data-axis");
            memberIndex = parseInt(header.attr("data-member"), 10);
            tupleIndex = parseInt(header.attr("data-tuple"), 10);
            expand = target.attr("data-expand") === "true";

            if ($this.options.rowHeadersLayout === "tree" && target.attr("data-item-expand")) {
                expand = target.attr("data-item-expand") === "true";
                memberIndex += 1;
            }

            if (expand) {
                noCancel = this._triggerTupleMemberExpanding(evt, axisName, memberIndex, tupleIndex);
                if (noCancel) {
                    this._ds.expandTupleMember(axisName, tupleIndex, memberIndex);
                    // Set a flag for the toggle operation.
                    this._performsToggle = true;
                    this._updateGrid(false, true).done(function () {
                        $this._triggerTupleMemberExpanded(null, axisName, memberIndex, tupleIndex);
                    }).always(function () {
                        // Reset the flag.
                        $this._performsToggle = false;
                    });
                }
            } else {
                noCancel = this._triggerTupleMemberCollapsing(evt, axisName, memberIndex, tupleIndex);
                if (noCancel) {
                    this._ds.collapseTupleMember(axisName, tupleIndex, memberIndex);
                    // Set a flag for the toggle operation.
                    this._performsToggle = true;
                    this._updateGrid(false, true).done(function () {
                        $this._triggerTupleMemberCollapsed(evt, axisName, memberIndex, tupleIndex);
                    }).always(function () {
                        // Reset the flag.
                        $this._performsToggle = false;
                    });
                }
            }
        },
        _onSort: function (evt) {
            var $this = this, header = $(evt.target).closest("th"), axisName, tupleIndex, tupleIdentifier,
                axis, axes, tuple, memberNames,
                columnSortDirections, columnSortDirection, levelSortDirections,
                options, i, noCancel, en;

            if (this._ds.isModified()) {
                return;
            }

            axisName = header.attr("data-axis");
            tupleIndex = parseInt(header.attr("data-tuple"), 10);

            if (axisName !== this._const.emptyCell.axisName) {
                axes = this._ds.result().axes().__inner; //toArray();
                for (i = 0; i < axes.length; i++) {
                    if (axisName === axes[i].name()) {
                        axis = axes[i];
                        break;
                    }
                }
                // Return if no such axis exists.
                if (!axis) {
                    return;
                }
                // Get the member names in the tuple.
                tuple = axis.tuples().item(tupleIndex);
                memberNames = [];

                en = tuple.members().getEnumerator();
                while (en.moveNext()) {
                    memberNames.push(en.current().uniqueName());
                }

                // Join the names for easier comparison.
                tupleIdentifier = memberNames.join("|");
            } else {
                memberNames = null;
                tupleIdentifier = "";
            }

            columnSortDirections = this._tableView.columnSortDirections();
            for (i = 0; i < columnSortDirections.length; i++) {
                // Find existing sort directions.
                if ((columnSortDirections[i].memberNames && columnSortDirections[i].memberNames.join("|") === tupleIdentifier) ||
						(columnSortDirections[i].tupleIndex === tupleIndex)) {
                    columnSortDirection = columnSortDirections[i];
                    break;
                }
            }

            if (!evt.ctrlKey) {
                columnSortDirections = [];
            }

            if (columnSortDirection) {
                // Alternate the existing level sort direction.
				columnSortDirection.sortDirection = columnSortDirection.sortDirection === "ascending" ?
					"descending" : "ascending";
                if (!evt.ctrlKey) {
                    columnSortDirections.push(columnSortDirection);
                }
            } else {
                // Create a new column sort direction.
                columnSortDirection = {
                    memberNames: memberNames,
                    tupleIndex: tupleIndex,
                    sortDirection: this.options.firstSortDirection
                };
                columnSortDirections.push(columnSortDirection);
            }

            noCancel = this._triggerSorting(evt, columnSortDirections);
            if (noCancel) {
                // Remove all level sort directions, which are in the row axis.
                levelSortDirections = $.grep(this._tableView.levelSortDirections(), function (sortDirection) {
                    var level, hierarchyName;

                    level = $this._ds.getCoreElement(function (l) {
                        return l.uniqueName() === sortDirection.levelUniqueName;
                    }, $.ig.Level.prototype.getType());

                    if (level) {
                        hierarchyName = level.hierarchyUniqueName();
                        return $.grep($this._ds.rowAxis(), function (h) { return h.uniqueName() === hierarchyName; }).length === 0;
                    }

                    return false;
                });
                this._tableView.levelSortDirections(levelSortDirections);

                this._tableView.columnSortDirections(columnSortDirections);
                this._tableView.initialize();

                options = this._configureOptions(this._ds);
                this._renderGrid(options);

                this._triggerSorted(evt, columnSortDirections, this._tableView.appliedColumnSortDirections());
            }
        },
        _onSortHeader: function (evt) {
            var header = $(evt.target).closest("th"), axisName, tupleIndex, memberIndex,
                axis, axes, tuple, member, level,
                levelName, hierarchyName, levelSortDirections, levelSortDirection, options, i,
                noCancel;

            if (this._ds.isModified()) {
                return;
            }

            axisName = header.attr("data-axis");
            tupleIndex = parseInt(header.attr("data-tuple"), 10);
            memberIndex = parseInt(header.attr("data-member"), 10);

            if (!header.hasClass("ui-iggrid-multiheader-cell") && this.options.rowHeadersLayout === "tree" &&
                ($(evt.target).hasClass("ui-iggrid-headertext") || $(evt.target).attr("data-sortheader"))) {
                memberIndex += 1;
            }

            axes = this._ds.result().axes().__inner; //toArray();
            for (i = 0; i < axes.length; i++) {
                if (axisName === axes[i].name()) {
                    axis = axes[i];
                    break;
                }
            }
            // Return if no such axis exists.
            if (!axis) {
                return;
            }

            tuple = axis.tuples().item(tupleIndex);
            member = tuple.members().item(memberIndex);
            level = this._ds.getCoreElement(function (l) {
                return l.hierarchyUniqueName() === member.hierarchyUniqueName() && l.depth() === member.depth() + 1;
            }, $.ig.Level.prototype.getType());
            // Return if no such level exists.
            if (!level) {
                return;
            }

            levelName = level.uniqueName();

            levelSortDirections = this._tableView.appliedLevelSortDirections();
            for (i = 0; i < levelSortDirections.length; i++) {
                if (levelSortDirections[i].levelUniqueName === levelName) {
                    levelSortDirection = levelSortDirections[i];
                    break;
                }
            }
            if (levelSortDirection) {
                // Alternate the existing level sort direction.
				levelSortDirection.sortDirection = levelSortDirection.sortDirection === "ascending" ?
					"descending" : "ascending";
            } else {
                // Create a new level sort direction.
                levelSortDirection = {
                    levelUniqueName: levelName,
                    // Not supported in 13.1
                    sortBehavior: this.options.defaultLevelSortBehavior,
                    sortDirection: this.options.firstLevelSortDirection
                };
                levelSortDirections.push(levelSortDirection);
            }

            noCancel = this._triggerHeadersSorting(evt, levelSortDirections);
            if (noCancel) {
                // If the hierarchy of the level is on the row axis,
                // clear all column sort directions.
                hierarchyName = level.hierarchyUniqueName();
                if ($.grep(this._ds.rowAxis(), function (h) { return h.uniqueName() === hierarchyName; }).length > 0) {
                    this._tableView.columnSortDirections([]);
                }

                this._tableView.levelSortDirections(levelSortDirections);
                this._tableView.initialize();

                options = this._configureOptions(this._ds);
                this._renderGrid(options);

                this._triggerHeadersSorted(evt, levelSortDirections, this._tableView.appliedLevelSortDirections());
            }
        },
        expandTupleMember: function (tupleLocation, tupleIndex, memberIndex, shouldUpdate) {
            /* Expands a member from the data source and returns true if the expand succeeds. If the data source has a pending update, the method will return false. Note that igPivotGrid to will display the expanded result after the data source is updated.
            type="string" The name of the parent axis - 'columnAxis' or 'rowAxis'.
            type="number" The index of the containing tuple. This index should correspond to the position of the tuple in the original unsorted result of the data source.
            type="number" The index of the member in the tuple. This index should correspond to the position of the member in the original unsorted result of the data source.
            type="bool" optional="true" A flag indicating whether the data source should be updated after the expand.
            returnType="bool"
            */
            var dataSource = this._ds, hasColumns, hasRows, axisName;
            hasColumns = dataSource.columnAxis().length > 0;
            hasRows = dataSource.rowAxis().length > 0;

            if (this._ds.isModified()) {
                return false;
            }

            if (tupleLocation === "columnAxis" && hasColumns) {
                if (hasColumns) {
                    axisName = "Axis0";
                }
            } else if (tupleLocation === "rowAxis" && hasRows) {
                if (hasColumns) {
                    axisName = "Axis1";
                } else {
                    axisName = "Axis0";
                }
            }

            if (!axisName) {
                throw new Error($.ig.PivotGrid.locale.noSuchAxis);
            }

            this._ds.expandTupleMember(axisName, tupleIndex, memberIndex);
            if (shouldUpdate) {
                this._updateGrid(null, true);
            }
        },
        collapseTupleMember: function (tupleLocation, tupleIndex, memberIndex, shouldUpdate) {
            /* Collapses a member from the data source and returns true if the collapse succeeds. If the data source has a pending update, the method will return false. Note that igPivotGrid to will display the expanded result after the data source is updated.
            type="string" The name of the parent axis - 'columnAxis' or 'rowAxis'.
            type="number" The index of the containing tuple. This index should correspond to the position of the tuple in the original unsorted result of the data source.
            type="number" The index of the member in the tuple. This index should correspond to the position of the member in the original unsorted result of the data source.
            type="bool" optional="true" A flag indicating whether the data source should be updated after the expand.
            returnType="bool"
            */
            var dataSource = this._ds, hasColumns, hasRows, axisName;
            hasColumns = dataSource.columnAxis().length > 0;
            hasRows = dataSource.rowAxis().length > 0;

            if (this._ds.isModified()) {
                return false;
            }

            if (tupleLocation === "columnAxis" && hasColumns) {
                if (hasColumns) {
                    axisName = "Axis0";
                }
            } else if (tupleLocation === "rowAxis" && hasRows) {
                if (hasColumns) {
                    axisName = "Axis1";
                } else {
                    axisName = "Axis0";
                }
            }

            if (!axisName) {
                throw new Error($.ig.PivotGrid.locale.noSuchAxis);
            }

            this._ds.collapseTupleMember(axisName, tupleIndex, memberIndex);
            if (shouldUpdate) {
                this._updateGrid();
            }
        },
        appliedColumnSortDirections: function () {
            /* Returns an array with the applied sort directions on the igPivotGrid's columns. The returned array contains objects with the following properties:
            memberNames: The names of the members in the tuple.
            tupleIndex: The index of the tuple on the column axis in the original unsorted result.
            sortDirection: The direction of the sort - ascending or descending.
            returnType="array"
            */
            return this._tableView ? this._tableView.appliedColumnSortDirections() : [];
        },
        appliedLevelSortDirections: function () {
            /* Returns an array with the applied level sort direction items, which were used for the sorting of the header cells. The returned array contains objects with the following properties:
            levelUniqueName: Specifies the unique name of the level, which was sorted.
            sortDirection: The direction of the header sort - ascending or descending.
            returnType="array"
            */
            return this._tableView ? this._tableView.appliedLevelSortDirections() : [];
        },
        destroy: function () {
            /* destroy is part of the jQuery UI widget API and does the following:
            1. Remove custom CSS classes that were added.
            2. Unwrap any wrapping elements such as scrolling divs and other containers.
            3. Unbind all events that were bound.
            */
            var grid = this.grid();
            if (grid) {
                grid.destroy();
            }
            $("#" + this.element.attr("id") + "_container_block").remove();
            this.element.removeClass(this.css.pivotGrid);
            $(this._ds).unbind('initialized.pivotgrid' + this.timestamp);
            $(this._ds).unbind('updated.pivotgrid' + this.timestamp);
            $.Widget.prototype.destroy.call(this);
        },
        _triggerPivotGridHeadersRendered: function (evt, ui) {
            var args = {
                owner: this,
                grid: ui.owner,
                table: ui.table
            };

            this._trigger("pivotGridHeadersRendered", evt, args);
        },
        _triggerPivotGridRendered: function (evt, ui) {
            var args = {
                owner: this,
                grid: ui.owner
            };

            this._trigger("pivotGridRendered", evt, args);
        },
        _triggerTupleMemberExpanding: function (evt, axisName, memberIndex, tupleIndex) {
            var args = {
                owner: this,
                dataSource: this._ds,
                axisName: axisName,
                memberIndex: memberIndex,
                tupleIndex: tupleIndex
            };

            return this._trigger("tupleMemberExpanding", evt, args);
        },
        _triggerTupleMemberExpanded: function (evt, axisName, memberIndex, tupleIndex) {
            var args = {
                owner: this,
                dataSource: this._ds,
                axisName: axisName,
                memberIndex: memberIndex,
                tupleIndex: tupleIndex
            };

            this._trigger("tupleMemberExpanded", evt, args);
        },
        _triggerTupleMemberCollapsing: function (evt, axisName, memberIndex, tupleIndex) {
            var args = {
                owner: this,
                dataSource: this._ds,
                axisName: axisName,
                memberIndex: memberIndex,
                tupleIndex: tupleIndex
            };

            return this._trigger("tupleMemberCollapsing", evt, args);
        },
        _triggerTupleMemberCollapsed: function (evt, axisName, memberIndex, tupleIndex) {
            var args = {
                owner: this,
                dataSource: this._ds,
                axisName: axisName,
                memberIndex: memberIndex,
                tupleIndex: tupleIndex
            };

            this._trigger("tupleMemberCollapsed", evt, args);
        },
        _triggerSorting: function (evt, columnSortDirections) {
            var args = {
                owner: this,
                sortDirections: columnSortDirections
            };

            return this._trigger("sorting", evt, args);
        },
        _triggerSorted: function (evt, columnSortDirections, appliedColumnSortDirections) {
            var args = {
                owner: this,
                sortDirections: columnSortDirections,
                appliedSortDirections: appliedColumnSortDirections
            };

            this._trigger("sorted", evt, args);
        },
        _triggerHeadersSorting: function (evt, levelSortDirections) {
            var args = {
                owner: this,
                levelSortDirections: levelSortDirections
            };

            return this._trigger("headersSorting", evt, args);
        },
        _triggerHeadersSorted: function (evt, levelSortDirections, appliedLevelSortDirections) {
            var args = {
                owner: this,
                levelSortDirections: levelSortDirections,
                appliedLevelSortDirections: appliedLevelSortDirections
            };

            this._trigger("headersSorted", evt, args);
        }
        /* _processMultiColumnHeadersRecursive: function (parent, headers, start, end) {
        var i, multiHeaderGroup = [], multiHeader;
        for (i = start; i < end; i++) {
        if (headers[i].columnSpan() > 1) {
        // multiHeaderGroup = [];
        // currentHeader = headers[i];
        multiHeader = {
        headerText: headers[i].caption(),
        // rowSpan: headers[i].rowSpan(),
        // colSpan: headers[i].columnSpan(),
        group: multiHeaderGroup,
        key: headers[i].axisName() + "_" + headers[i].tupleIndex() + "_" + headers[i].memberIndex(),
        expandable: headers[i].isExpandable(),
        expanded: headers[i].isExpanded(),
        axisName: headers[i].axisName(),
        tupleIndex: headers[i].tupleIndex(),
        memberIndex: headers[i].memberIndex()
        };
        parent.push(multiHeader);
        // parent = multiHeaderGroup;
        this._processMultiColumnHeadersRecursive(multiHeaderGroup, headers, ++i, i + headers[i - 1].columnSpan());
        i += headers[i - 1].columnSpan() - 1;
        } else {
        parent.push({
        headerText: headers[i].caption(),
        key: headers[i].axisName() + "_" + headers[i].tupleIndex() + "_" + headers[i].memberIndex(),
        expandable: headers[i].isExpandable(),
        expanded: headers[i].isExpanded(),
        axisName: headers[i].axisName(),
        tupleIndex: headers[i].tupleIndex(),
        memberIndex: headers[i].memberIndex()
        });
        }
        }
        } */
    });
    $.extend(true, $.ui.igPivotGrid.prototype, $.ig.Pivot._pivotShared);
    $.extend($.ui.igPivotGrid, { version: "16.1.20161.2145" });
} (jQuery));


