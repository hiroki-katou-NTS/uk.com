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
                invalidBaseElement: " はベース要素としてサポートされていません。代わりに DIV を使用してください。",
                catalog: "カタログ",
                cube: "キューブ",
                measureGroup: "メジャー グループ",
                measureGroupAll: "(すべて)",
                rows: "行",
                columns: "列",
                measures: "メジャー",
                filters: "フィルター",
                deferUpdate: "更新を遅延する",
                updateLayout: "レイアウトの更新",
                selectAll: "すべて選択"
            }
        });
    }
})(jQuery);

/*!@license
* Infragistics.Web.ClientUI Pivot Data Selector 16.1.20161.2145
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
*	infragistics.templating.js
*	infragistics.ui.shared.js
*   infragistics.ui.scroll.js
*	infragistics.ui.combo.js
*	infragistics.ui.tree.js
*	infragistics.ui.pivot.shared.js
*/
/*global jQuery*/

(function ($) {
    var _droppable = $.ui.droppable.prototype.widgetFullName || $.ui.droppable.prototype.widgetName;

    $.widget("ui.igPivotDataSelector", {
        css: {
            /* Class applied to the the data selector element. */
            dataSelector: "ui-igpivotdataselector",
            /* Class applied to the root container for the data selector. */
            dataSelectorRoot: "ui-igpivotdataselector-root",
            /* Class applied to the catalog combo. */
            catalog: "ui-igpivotdataselector-catalog",
            /* Class applied to the cube combo. */
            cube: "ui-igpivotdataselector-cube",
            /* Class applied to the measure group combo. */
            measureGroup: "ui-igpivotdataselector-measuregroup",
            /* Classes applied to the metadata tree. */
            metadata: "ui-igpivotdataselector-metadata ui-widget-content",
            /* Classes applied to the elements representing metadata items - in the metadata tree and the drop areas. */
            metadataItem: "ui-igpivot-metadataitem ui-widget ui-corner-all ui-state-default",
            /* Class applied to the table with the drop areas. */
            dropAreasTable: "ui-igpivotdataselector-dropareas",
            /* Classes applied to the drop areas. */
            dropArea: "ui-igpivot-droparea ui-widget-content",
            /* Class applied to the drop areas, which can accept a drop. */
            activeDropArea: "active",
            /* Classes applied to the filters drop area icon. */
            filtersIcon: "ui-icon ui-icon-pivot-filters",
            /* Classes applied to the columns drop area icon. */
            columnsIcon: "ui-icon ui-icon-pivot-columns",
            /* Classes applied to the rows drop area icon. */
            rowsIcon: "ui-icon ui-icon-pivot-rows",
            /* Classes applied to the measures drop area icon. */
            measuresIcon: "ui-icon ui-icon-pivot-measures",
            /* Class applied to the update layout button. */
            updateLayout: "ui-igpivotdataselector-updatelayout",
            /* Class applied to the valid drop element. */
            dropIndicator: "ui-state-highlight",
            /* Class applied to the invalid drop element. */
            invalidDropIndicator: "ui-state-error",
            /* Classes applied to the insert item indicator in the drop areas. */
            insertItem: "ui-igpivot-insertitem ui-state-highlight ui-corner-all",
            /* Classes applied to the metadata item drop down element. */
            metadataItemDropDown: "ui-igpivot-metadatadropdown ui-widget ui-widget-content",
            /* Classes applied to the filter icon in the metadata items. */
            filterIcon: "ui-icon ui-icon-pivot-smallfilter",
            /* Classes applied to the filters drop down element. */
            filterDropDown: "ui-igpivot-filterdropdown ui-widget ui-widget-content",
            /* Class applied to the tree containing the filter members. */
            filterMembers: "ui-igpivot-filtermembers"
            ///* Class applied to the overlay element, which is over the data selector when a filter drop down is open. */
            //filterDropDownOverlay: "ui-igpivot-overlay"
        },
        options: {
            /* type="string|number|null"
				string The widget width can be set in pixels (px) and percentage (%). The recommended width is 250px.
				number The widget width can be set as a number.
				null type="object" will stretch to fit data, if no other widths are defined.
			*/
            width: 250,
            /* type="string|number|null" This is the total height of the grid, including all UI elements - scroll container with data rows, header, footer, filter row -  (if any), etc.  
				string The widget height can be set in pixels (px) and percentage (%).
				number The widget height can be set as a number.
				null type="object" will stretch vertically to fit data, if no other heights are defined.
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
                        /* type="function" A callback to be invoked right before the request is send to the server. Extends beforeSend callback of jQuery.ajax’s options object.
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
                                            Based on the item parameter the function should return a value that will form the $.ig.Member’s name and caption.
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
            /* type="bool" Setting deferUpdate to true will not apply changes to the data source until the update method is called or the update layout button is clicked.
            */
            deferUpdate: false,
            /* type="object" Settings for the drag and drop functionality of the igPivotDataSelector.
            */
            dragAndDropSettings: {
                /* type="parent|selector|element" Which element the draggable helper should be appended to while dragging.
                */
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
            /* type="function" A function that will be called to determine if an item can be moved in or dropped on an area of the data selector.
                paramType="string" The location where the item will be moved - igPivotGrid, igPivotDataSelector, filters, rows, columns or measures.
                paramType="string" The type of the item - Hierarchy, Measure or MeasureList.
                paramType="string" The unique name of the item.
                returnType="bool"  The function must return true if the item should be accepted.
			*/
            customMoveValidation: null
        },
        events: {
            /* cancel="false" Fired after the data selector is rendered. Changing the data source instance will re-render the data selector.
                Function takes arguments evt and ui.
                Use ui.owner to get a reference to the data selector.
            */
            dataSelectorRendered: null,
            /* cancel="false" Fired after the data source has initialized.
                Function takes arguments evt and ui.
                Use ui.owner to get a reference to the data selector.
                Use ui.dataSource to get a reference to the data source.
                Use ui.error to see if an error has occured during initialization.
                Use ui.metadataTreeRoot to get a reference to the root of the data source metatadata root item.
            */
            dataSourceInitialized: null,
            /* cancel="false" Fired after the data source has updated.
                Function takes arguments evt and ui.
                Use ui.owner to get a reference to the data selector.
                Use ui.dataSource to get a reference to the data source.
                Use ui.error to see if an error has occured during update.
                Use ui.result to get the result of the update operation.
            */
            dataSourceUpdated: null,
            /* cancel="false" Fired when the defer update checkbox changes.
                Function takes arguments evt and ui.
                Use ui.owner to get a reference to the data selector.
                Use ui.deferUpdate to get the defer update value.
            */
            deferUpdateChanged: null,
            /* cancel="true" Fired on drag start. Return false to cancel the drag.
				Use ui.metadata	to get a reference to the data.
				Use ui.helper to get a reference to the helper.
				Use ui.offset to get a reference to the offset.
				Use ui.originalPosition to get a reference to the original position of the draggable element.
				Use ui.position to get a reference to the current position of the draggable element.
            */
            dragStart: null,
            /* cancel="true" Fired on drag. Return false to cancel the dragging.
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
                Use ui.draggedElement for a reference to the dragged element.
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
        _deferUpdate: false,
        _create: function () {
            var $this = this,
                elementName = this.element[0].nodeName.toUpperCase();
            if (elementName !== "DIV") {
                throw new Error(elementName + $.ig.PivotDataSelector.locale.invalidBaseElement);
            }

            this.element.addClass(this.css.dataSelector);

            // Create funcitons, which will act as handlers to the
            // collectionChanged events of the data source.
            // These handlers can be detached from the events in a safe way
            // (unlike if anonymous functions were used).
            this._onFiltersChanged = function (collection, collectionChangedArgs) {
                var dropArea = $this.element.find(".ui-igpivot-droparea[data-role=filters]");
                $this._onDataSourceCollectionChanged(collection, collectionChangedArgs, dropArea, $this.options.disableFiltersDropArea);
            };
            this._onRowAxisChanged = function (collection, collectionChangedArgs) {
                var dropArea = $this.element.find(".ui-igpivot-droparea[data-role=rows]");
                $this._onDataSourceCollectionChanged(collection, collectionChangedArgs, dropArea, $this.options.disableRowsDropArea);
            };
            this._onColumnAxisChanged = function (collection, collectionChangedArgs) {
                var dropArea = $this.element.find(".ui-igpivot-droparea[data-role=columns]");
                $this._onDataSourceCollectionChanged(collection, collectionChangedArgs, dropArea, $this.options.disableColumnsDropArea);
            };
            this._onMeasuresChanged = function (collection, collectionChangedArgs) {
                var dropArea = $this.element.find(".ui-igpivot-droparea[data-role=measures]");
                $this._onDataSourceCollectionChanged(collection, collectionChangedArgs, dropArea, $this.options.disableMeasuresDropArea);
            };
            this._setDataSource();

            this._makeDroppable(this.element);
        },
        _setOption: function (key, value) {
            var dropArea, droppable;

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
                this._deferUpdate = value;
                this.element.find(".ui-igpivotdataselector-deferupdate").attr("checked", value);
                this.element.find(".ui-igpivotdataselector-updatelayout").igButton(value ? "enable" : "disable");
                break;
            case "width":
                $.Widget.prototype._setOption.apply(this, arguments);
                this.element.width(value);
                this.element.find(".ui-igpivotdataselector-catalog, .ui-igpivotdataselector-cube, .ui-igpivotdataselector-measuregroup")
                    .igCombo("option", "width", this.element.children(".ui-igpivotdataselector-root").width());
                break;
            case "height":
                $.Widget.prototype._setOption.apply(this, arguments);
                this.element.height(value);
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
            case "disableRowsDropArea":
                $.Widget.prototype._setOption.apply(this, arguments);
                dropArea = this.element.find(".ui-igpivot-droparea[data-role=rows]");
                if (value) {
                    droppable = dropArea.data(_droppable);
                    if (droppable) {
                        droppable.destroy();
                    }
                } else /*if (!$.ig.util.isTouchDevice()) TFS 202229 & 205976 – allowing drag drop on touch devices */{
                    dropArea.droppable(this._createDropAreaOptions());
                }
                this._fillDropArea(dropArea, this._ds.rowAxis(), this.options.disableRowsDropArea);
                break;
            case "disableColumnsDropArea":
                $.Widget.prototype._setOption.apply(this, arguments);
                dropArea = this.element.find(".ui-igpivot-droparea[data-role=columns]");
                if (value) {
                    droppable = dropArea.data(_droppable);
                    if (droppable) {
                        droppable.destroy();
                    }
                } else /*if (!$.ig.util.isTouchDevice()) TFS 202229 & 205976 – allowing drag drop on touch devices */ {
                    dropArea.droppable(this._createDropAreaOptions());
                }
                this._fillDropArea(dropArea, this._ds.columnAxis(), this.options.disableColumnsDropArea);
                break;
            case "disableMeasuresDropArea":
                $.Widget.prototype._setOption.apply(this, arguments);
                dropArea = this.element.find(".ui-igpivot-droparea[data-role=measures]");
                if (value) {
                    droppable = dropArea.data(_droppable);
                    if (droppable) {
                        droppable.destroy();
                    }
                } else /*if (!$.ig.util.isTouchDevice()) TFS 202229 & 205976 – allowing drag drop on touch devices */{
                    dropArea.droppable(this._createDropAreaOptions());
                }
                this._fillDropArea(dropArea, this._ds.measures(), this.options.disableMeasuresDropArea);
                break;
            case "disableFiltersDropArea":
                $.Widget.prototype._setOption.apply(this, arguments);
                dropArea = this.element.find(".ui-igpivot-droparea[data-role=filters]");
                if (value) {
                    droppable = dropArea.data(_droppable);
                    if (droppable) {
                        droppable.destroy();
                    }
                } else /*if (!$.ig.util.isTouchDevice()) TFS 202229 & 205976 – allowing drag drop on touch devices */{
                    dropArea.droppable(this._createDropAreaOptions());
                }
                this._fillDropArea(dropArea, this._ds.filters(), this.options.disableFiltersDropArea);
                break;
            }
        },
        _initUI: function () {
            var $this = this, dataSource = this._ds,
                rootDiv, comboOptions, droppableOptions, dropAreasTable, tableRow, tableHeader,
                tableColumn, dropArea, deferUpdateCheck, deferUpdateLabel;

            if (this.options.width) {
                this.element.width(this.options.width);
            }
            if (this.options.height) {
                this.element.height(this.options.height);
            }

            rootDiv = $("<div class='ui-widget " + this.css.dataSelectorRoot + "'></div>").appendTo(this.element);

            if (this._isInstance(dataSource, "OlapXmlaDataSource")) {
                // Add catalog, cube and measure group combos
                // if the data source supports these properties.
                // Flat data does not support them.
                comboOptions = {
                    textKey: "_caption",
                    valueKey: "_name",
                    mode: "dropdown",
                    enableClearButton: false,
                    width: rootDiv.width()
                };
                // Catalog
                $("<input class='" + this.css.catalog + "' />").appendTo(rootDiv)
                    .igCombo($.extend({
                        placeHolder: $.ig.PivotDataSelector.locale.catalog,
                        selectionChanged: function (evt, ui) {
                            $this._onCatalogSelected(ui.items[0].data.caption());
                        }
                    }, comboOptions));
                // Cube
                $("<input class='" + this.css.cube + "' />").appendTo(rootDiv)
                    .igCombo($.extend({
                        placeHolder: $.ig.PivotDataSelector.locale.cube,
                        selectionChanged: function (evt, ui) {
                            $this._onCubeSelected(ui.items[0].data.caption());
                        }
                    }, comboOptions));
                // Measure Group
                $("<input class='" + this.css.measureGroup + "' />").appendTo(rootDiv)
                    .igCombo($.extend({
                        placeHolder: $.ig.PivotDataSelector.locale.measureGroup,
                        selectionChanged: function (evt, ui) {
                            $this._onMeasureGroupSelected(ui.items[0].data.caption());
                        }
                    }, comboOptions));
            }

            // Metadata
            $("<div class='" + this.css.metadata + "'></div>").appendTo(rootDiv).addClass().igTree({
                initialExpandDepth: 0,
                bindings: {
                    nodeContentTemplate: "<span class='ui-igpivot-metadataitem' data-name='${name}' data-type='${type}'><span class='${image}'/>${caption}</span>",
                    childDataProperty: "children"
                },
                rendered: function(evt, ui) {
                    ui.owner.element.removeAttr('data-scroll');
                }
            });

            // Drop Areas
            droppableOptions = this._createDropAreaOptions();

            dropAreasTable = $("<table class='" + this.css.dropAreasTable + "'></table>").appendTo(rootDiv);

            tableRow = $("<tr></tr>").appendTo(dropAreasTable);
            tableHeader = $("<th></th>").appendTo(tableRow);
            $("<span class='" + this.css.filtersIcon  + "'></span>").appendTo(tableHeader);
            $("<span></span>").text($.ig.PivotDataSelector.locale.filters).appendTo(tableHeader);
            tableHeader = $("<th></th>").appendTo(tableRow);
            $("<span class='" + this.css.columnsIcon + "'></span>").appendTo(tableHeader);
            $("<span></span>").text($.ig.PivotDataSelector.locale.columns).appendTo(tableHeader);

            tableRow = $("<tr></tr>").appendTo(dropAreasTable);
            tableColumn = $("<td></td>").appendTo(tableRow);
            dropArea = $("<ul class='" + this.css.dropArea + "' data-role='filters'></ul>").appendTo(tableColumn);
            if (!this.options.disableFiltersDropArea /*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                dropArea.droppable(droppableOptions);
            }
            tableColumn = $("<td></td>").appendTo(tableRow);
            dropArea = $("<ul class='" + this.css.dropArea + "' data-role='columns'></ul>").appendTo(tableColumn);
            if (!this.options.disableColumnsDropArea /*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                dropArea.droppable(droppableOptions);
            }

            tableRow = $("<tr></tr>").appendTo(dropAreasTable);
            tableHeader = $("<th></th>").appendTo(tableRow);
            $("<span class='" + this.css.rowsIcon + "'></span>").appendTo(tableHeader);
            $("<span></span>").text($.ig.PivotDataSelector.locale.rows).appendTo(tableHeader);
            tableHeader = $("<th></th>").appendTo(tableRow);
            $("<span class='" + this.css.measuresIcon + "'></span>").appendTo(tableHeader);
            $("<span></span>").text($.ig.PivotDataSelector.locale.measures).appendTo(tableHeader);

            tableRow = $("<tr></tr>").appendTo(dropAreasTable);
            tableColumn = $("<td></td>").appendTo(tableRow);
            dropArea = $("<ul class='" + this.css.dropArea + "' data-role='rows'></ul>").appendTo(tableColumn);
            if (!this.options.disableRowsDropArea /*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                dropArea.droppable(droppableOptions);
            }
            tableColumn = $("<td></td>").appendTo(tableRow);
            dropArea = $("<ul class='" + this.css.dropArea + "' data-role='measures'></ul>").appendTo(tableColumn);
            if (!this.options.disableMeasuresDropArea /*&& !$.ig.util.isTouchDevice() TFS 202229 & 205976 – allowing drag drop on touch devices */) {
                dropArea.droppable(droppableOptions);
            }

            if ($.ig.util.isTouchDevice()) {
                dropAreasTable.find(".ui-igpivot-droparea").igScroll();
            }

            // Defer Update
            this._deferUpdate = this.options.deferUpdate;
            deferUpdateLabel = $("<label></label>").appendTo(rootDiv).text($.ig.PivotDataSelector.locale.deferUpdate);
            deferUpdateCheck = $("<input class='ui-igpivotdataselector-deferupdate' type='checkbox' />").prependTo(deferUpdateLabel).change(function (event) {
                $this._deferUpdate = $(event.target).is(":checked");
                if ($this._deferUpdate) {
                    $this.element.find(".ui-igpivotdataselector-updatelayout").igButton("enable");
                } else {
                    $this.element.find(".ui-igpivotdataselector-updatelayout").igButton("disable");
                    // Trigger an update after defer update is unchecked.
                    $this._updateDataSource();
                }
                $this._triggerDeferUpdateChanged($this._deferUpdate);
            });
            $("<button class='" + this.css.updateLayout + "'></button>").attr("title", $.ig.PivotDataSelector.locale.updateLayout)
                .appendTo(rootDiv).igButton({
                    text: false,
                    icons: {
                        primary: "ui-icon-refresh"
                    }
                }).igButton(this._deferUpdate ? "enable" : "disable").click(function () {
                    $this._updateDataSource(true);
                });

            this._triggerDataSelectorRendered();
        },
        _clearUI: function () {
            this.element.find(".ui-igpivotdataselector-catalog, .ui-igpivotdataselector-cube, .ui-igpivotdataselector-measuregroup").igCombo("destroy");
            this.element.find(".ui-igpivotdataselector-metadata").igTree("destroy");
            this.element.find(".ui-igpivot-droparea .ui-igpivot-metadataitem.ui-draggable").draggable("destroy").remove();
            this.element.empty();
        },
        _setDataSource: function () {
            var $this = this, dataSource;

            this._ds = dataSource = this._createDataSource(
                this.options.dataSource,
                this.options.dataSourceOptions
            );

            this._initUI();

            if (!dataSource) {
                return;
            }

            this.timestamp = new Date().getTime();
            $(dataSource).bind("initialized.dataselector" + this.timestamp, function (evt, args) {
                $this._onDataSourceInitialized(evt, args);
            });
            $(dataSource).bind("updated.dataselector" + this.timestamp, function (evt, args) {
                $this._onDataSourceUpdated(evt, args);
            });

            if (dataSource.isInitialized()) {
                if (this._isInstance(dataSource, "OlapXmlaDataSource")) {
                    this._onCatalogSelected(this._getItemName(dataSource.catalog()), true);
                } else {
                    this._fillMetadata(dataSource.metadataTree());
                    // this._updateDataSource();
                }

                dataSource.bindCollectionChanged({
                    filters: this._onFiltersChanged,
                    rowAxis: this._onRowAxisChanged,
                    columnAxis: this._onColumnAxisChanged,
                    measures: this._onMeasuresChanged
                });
            } else {
                dataSource.initialize();
            }
        },
        _clearDataSource: function () {
            if (this._ds) {
                $(this._ds).unbind("updated.dataselector");

                this._ds.unbindCollectionChanged({
                    filters: this._onFiltersChanged,
                    rowAxis: this._onRowAxisChanged,
                    columnAxis: this._onColumnAxisChanged,
                    measures: this._onMeasuresChanged
                });
            }

            // Clear the filter members cache.
            this._filterMembersCache = [];

            this._clearUI();
        },
        _onDataSourceInitialized: function (evt, evtArgs) {
            var dataSource = this._ds,
                args = $.extend({
                    owner: this,
                    dataSource: dataSource
                }, evtArgs);

            dataSource.bindCollectionChanged({
                filters: this._onFiltersChanged,
                rowAxis: this._onRowAxisChanged,
                columnAxis: this._onColumnAxisChanged,
                measures: this._onMeasuresChanged
            });

            this._triggerDataSourceInitialized(evt, args);

            if (!evtArgs.error) {
                if (this._isInstance(dataSource, "OlapXmlaDataSource")) {
                    this._onCatalogSelected(this._getItemName(dataSource.catalog()), true);
                } else {
                    this._fillMetadata(dataSource.metadataTree());
                    // this._updateDataSource();
                }
            }
        },
        _updateDataSource: function (deferUpdateOverride) {
            var dataSource = this._ds,
                shouldUpdate = deferUpdateOverride || this._deferUpdate === false;

            if (shouldUpdate && dataSource.cube() !== null) {
                dataSource.update();
            }
        },
        _onDataSourceUpdated: function (evt, evtArgs) {
            var dataSource = this._ds,
                args = $.extend({
                    owner: this,
                    dataSource: dataSource
                }, evtArgs);

            this._triggerDataSourceUpdated(evt, args);
        },
        _getItemName: function (item) {
            return item && item.name();
        },
        _fillCombo: function (comboSelector, items, selectedItem) {
        	var t = typeof selectedItem;

            this.element.find(comboSelector).igCombo("option", "dataSource", items);

            if (t === "number" && selectedItem !== null) {
                this.element.find(comboSelector).igCombo("index", selectedItem);
            } else if (t === "object" && selectedItem !== null && $.isFunction(selectedItem.name)) {
                this.element.find(comboSelector).igCombo("value", selectedItem.name());
            } 
        },
        _clearCombo: function (comboSelector) {
            this.element.find(comboSelector)
                .igCombo("deselectAll")
                .igCombo("option", "dataSource", null);
        },
        _fillMetadata: function (metadata) {
            var $this = this,
                parseMetadata = function (m) {
                    var metadataItem = {}, hasItem = true, item, imgClass, children, i;
                    switch (m.type()) {
                    case $.ig.OlapMetadataTreeItemType.prototype.cube:
                        imgClass = "cube";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.dimension:
                        imgClass = "dimension";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.group:
                        imgClass = "folder";
                        hasItem = false;
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.userDefinedHierarchy:
                        imgClass = "hierarchymultiple";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.systemEnabledHierarchy:
                        imgClass = "hierarchysingle";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.parentChildHierarchy:
                        imgClass = "hierarchydirect";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.measure:
                        imgClass = "measure";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.level1:
                        imgClass = "level1";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.level2:
                        imgClass = "level2";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.level3:
                        imgClass = "level3";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.level4:
                        imgClass = "level4";
                        break;
                    case $.ig.OlapMetadataTreeItemType.prototype.level5:
                        imgClass = "level5";
                        break;
					case $.ig.OlapMetadataTreeItemType.prototype.kpiRoot:
						case $.ig.OlapMetadataTreeItemType.prototype.kpi:
							imgClass = "kpi";
							break;
						case $.ig.OlapMetadataTreeItemType.prototype.kpiValue:
							imgClass = "kpi value";
							break;
						case $.ig.OlapMetadataTreeItemType.prototype.kpiGoal:
							imgClass = "kpi goal";
							break;
						case $.ig.OlapMetadataTreeItemType.prototype.kpiStatus:
							imgClass = "kpi status";
							break;
						case $.ig.OlapMetadataTreeItemType.prototype.kpiTrend:
							imgClass = "kpi trend";
							break;
					case $.ig.OlapMetadataTreeItemType.prototype.kpiWeight:
						imgClass = "kpi weight";
						break;
                    default:
                        imgClass = "folder";
                        break;
                    }

                    metadataItem.caption = m.caption();
                    metadataItem.image = imgClass;
                    if (hasItem) {
                        item = m.item();
                        metadataItem.name = item.uniqueName();
                        metadataItem.type = item.getType().typeName();
                    }

                    children = m.children();
                    if (children) {
                        metadataItem.children = [];
                        for (i = 0; i < children.length; i++) {
                            metadataItem.children[i] = parseMetadata(children[i]);
                        }
                    }

                    return metadataItem;
                },
                parsedMetadata = metadata === null ? [] : [parseMetadata(metadata)],
                dragAndDropSettings = this.options.dragAndDropSettings,
				tree,
				items;

            tree = this.element.find(".ui-igpivotdataselector-metadata").igTree("option", "dataSource", parsedMetadata);
			items = tree.find(".ui-igpivot-metadataitem[data-type='Kpi'],.ui-igpivot-metadataitem[data-type='Measure'],.ui-igpivot-metadataitem[data-type='Dimension'],.ui-igpivot-metadataitem[data-type='Hierarchy'],.ui-igpivot-metadataitem[data-type='KpiMeasure']");
            /*if (!$.ig.util.isTouchDevice()) TFS 202229 & 205976 – allowing drag drop on touch devices */
			{
                items.draggable({
                    appendTo: dragAndDropSettings.appendTo,
                    containment: dragAndDropSettings.containment,
                    zIndex: dragAndDropSettings.zIndex,
                    cursorAt: this._const.dragCursorAt,
                    revert: false,
                    helper: function () {
                        var markup;
                        markup = $($this._const.dragHelperMarkup.replace("{0}", $(this).text()));
                        markup.addClass($this.css.invalidDropIndicator)
                            .find("span")
                            .addClass("ui-icon  ui-icon-cancel");
                        return markup;
                    },
                    start: function (event, ui) { return $this._triggerDragStart(event, ui, tree.igTree("nodeDataFor", $(this).attr("data-path"))); },
                    drag: function (event, ui) { return $this._triggerDrag(event, ui, tree.igTree("nodeDataFor", $(this).attr("data-path"))); },
                    over: function (event, ui) { $this._onDraggableOver(event, ui); },
                    out: function (event, ui) { $this._onDraggableOut(event, ui); },
                    stop: function (event, ui) { $this._triggerDragStop(event, ui); }
                });
            }
            items.click(function (event) {
                var item = tree.igTree("nodeDataFor", $(this).closest("li").attr("data-path")),
                    name = item.name,
                    type = $.ig[item.type].prototype.getType(),
                    metadataItem;
                metadataItem = $this._ds.getCoreElement(
                    function (el) { return el.uniqueName() === name; },
                    type
                );

                if (metadataItem) {
                    $this._createMetadataItemDropDown(event, this, metadataItem);
                }
            });

            this._fillDropArea(".ui-igpivot-droparea[data-role=filters]", this._ds.filters(), this.options.disableFiltersDropArea);
            this._fillDropArea(".ui-igpivot-droparea[data-role=rows]", this._ds.rowAxis(), this.options.disableRowsDropArea);
            this._fillDropArea(".ui-igpivot-droparea[data-role=columns]", this._ds.columnAxis(), this.options.disableColumnsDropArea);
            this._fillDropArea(".ui-igpivot-droparea[data-role=measures]", this._ds.measures(), this.options.disableMeasuresDropArea);
        },
        _fillDropArea: function (dropArea, items, isDisabled) {
            var i, length;
            dropArea = dropArea.jquery ? dropArea : this.element.find(dropArea);
            dropArea.empty();
            for (i = 0, length = items.length; i < length; i++) {
                this._createMetadataElement(items[i], isDisabled, "appendTo", dropArea);
            }
        },
        _clearMetadata: function () {
            this.element.find(".ui-igpivotdataselector-metadata").igTree("option", "dataSource", []);
        },
        _onCatalogSelected: function (catalog, isInit) {
            var $this = this,
                dataSource = this._ds,
                callback = function () {
                    $this._fillCombo(".ui-igpivotdataselector-catalog", dataSource.catalogs(), dataSource.catalog());
                    $this._fillCombo(".ui-igpivotdataselector-cube", dataSource.cubes(), dataSource.cube());
                    $this._onCubeSelected($this._getItemName(dataSource.cube()), isInit);
                };

            this._clearCombo(".ui-igpivotdataselector-cube");

            if (catalog === null) {
                callback();
            } else {
                dataSource.setCatalog(catalog).done(callback);
            }
        },
        _onCubeSelected: function (cube, isInit) {
            var $this = this,
                dataSource = this._ds,
                callback = function () {
                    var measureGroup = dataSource.cube() && (dataSource.measureGroup() || 0),
                        measureGroups = dataSource.measureGroups().slice();

                    // Add the (All) measure group.
                    measureGroups.splice(0, 0, { _caption: $.ig.PivotDataSelector.locale.measureGroupAll, _name: $.ig.PivotDataSelector.locale.measureGroupAll });

                    $this._fillCombo(".ui-igpivotdataselector-measuregroup", measureGroups, measureGroup);
                    $this._onMeasureGroupSelected($this._getItemName(dataSource.measureGroup()), isInit);
                };

            this._clearCombo(".ui-igpivotdataselector-measuregroup");
            if (cube === null) {
                callback();
            } else {
                dataSource.setCube(cube).done(callback);
            }
        },
        _onMeasureGroupSelected: function (measureGroup, isInit) {
            var $this = this,
                dataSource = this._ds,
                callback = function () {
                    $this._fillMetadata(dataSource.metadataTree());
                    if (!isInit) {
                        $this._updateDataSource();
                    }
                };

            this._clearMetadata();

            if (measureGroup === null) {
                callback();
            } else {
                dataSource.setMeasureGroup(measureGroup).done(callback);
            }
        },
        _shouldAppendToTarget: function (target, dragged) {
            // Calculate the top of the dragged element and the drop target.
            return target.offset().top + target.height() / 2 < dragged.offset.top + this._const.dragCursorAt.top;
        },
        update: function () {
            /* Updates the data source.
            */
            this._ds.update();
        },
        destroy: function () {
            /* destroy is part of the jQuery UI widget API and does the following:
                1. Remove custom CSS classes that were added.
                2. Unwrap any wrapping elements such as scrolling divs and other containers.
                3. Unbind all events that were bound.
            */
            this._clearDataSource();
            this.element.removeClass(this.css.dataSelector);
            $(this._ds).unbind('updated.dataselector' + this.timestamp);
            $(this._ds).unbind('initialized.dataselector' + this.timestamp);
            $.Widget.prototype.destroy.apply(this);
            return this;
        },
        _triggerDataSelectorRendered: function () {
            var args = {
                owner: this
            };
            this._trigger("dataSelectorRendered", null, args);
        },
        _triggerDeferUpdateChanged: function (deferUpdate) {
            var args = {
                owner: this,
                deferUpdate: deferUpdate
            };
            this._trigger("deferUpdateChanged", null, args);
        }
    });
    $.extend(true, $.ui.igPivotDataSelector.prototype, $.ig.Pivot._pivotShared);
    $.extend($.ui.igPivotDataSelector, { version: "16.1.20161.2145" });
}(jQuery));


