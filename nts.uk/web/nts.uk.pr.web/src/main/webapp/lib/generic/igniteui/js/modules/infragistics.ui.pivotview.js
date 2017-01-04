/*!@license
* Infragistics.Web.ClientUI Pivot View 16.1.20161.2145
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
*	infragistics.ui.scroll.js
*	infragistics.ui.splitter.js
*	infragistics.ui.tree.js
*	infragistics.ui.grid.framework.js
*	infragistics.ui.grid.multicolumnheaders.js
*	infragistics.ui.pivot.shared.js
*	infragistics.ui.pivotdataselector.js
*	infragistics.ui.pivotgrid.js
*/
/*global jQuery */
(function ($) {
	var _igPivotGrid = $.ui.igPivotGrid.prototype.widgetFullName || $.ui.igPivotGrid.prototype.widgetName,
		_igPivotDataSelector = $.ui.igPivotDataSelector.prototype.widgetFullName || $.ui.igPivotDataSelector.prototype.widgetName,
		_igSplitter = $.ui.igSplitter.prototype.widgetFullName || $.ui.igSplitter.prototype.widgetName;

	$.widget("ui.igPivotView", {
		options: {
			/* type="string|number|null"
				string The widget width can be set in pixels (px) and percentage (%).
				number The widget width can be set as a number.
				null type="object" will stretch to fit the parent, if no other widths are defined.
			*/
			width: null,
			/* type="string|number|null"
				string The widget height can be set in pixels (px) and percentage (%).
				number The widget height can be set as a number.
				null type="object" will stretch vertically to fit the parent, if no other heights are defined.
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
			/* type="object" Configuration settings that will be assigned to the igPivotGrid widget.
			*/
			pivotGridOptions: {
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
				/* type="bool" A boolean value indicating wheter the column headers should be arranged for compact header layout – each hieararchy is in a single row.
				*/
				compactColumnHeaders: false,
			    /* type="standard|compact|tree" A value indicating wheter the layout that row headers should be arranged. For compact header layout – each hieararchy is in a single column.
                */
				rowHeadersLayout: "compact",
				/* type="number" The indentation for every level column when the compactColumnHeaders is set to true.
				*/
				compactColumnHeaderIndentation: 30, // default indentation for every level when columns are rendered in a "tree"-view structure in a compact way
			    /* type="number" The indentation for every level row when the rowHeadersLayout is set to 'compact'.
				*/
				compactRowHeaderIndentation: 20, // default indentation for every level when rows are rendered in a "tree"-view structure in a compact way
				/* typle="number" Specifies the width of the row headers.
				*/
				defaultRowHeaderWidth: 200,
				/* type="bool" Enables sorting of the value cells in columns.
				*/
				allowSorting: false,
				/* type="ascending|descending" Spefies the default sort direction for the rows.
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
					/* type="ascending|descending" optional="true" Specifies the sort direction. If no direction is specified,
										the level is going to be sorted in the direction specified by the firstLevelSortDirection option.
					*/
					sortDirection: null
				}],
				/* type="ascending|descending" Spefies the default sort direction for the levels if no sort direction is specified in an item from the levelSortDirections option.
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
				/* type="object" Settings for the drag and drop functionality of the igPivotDataSelector.
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
			/* type="object" Configuration settings that will be assigned to the igPivotDataSelector widget.
			*/
			dataSelectorOptions: {
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
				/* type="function" A function that will be called to determine if an item can be moved in or dropped on an area of the data selector.
					paramType="string" The location where the item will be moved - igPivotGrid, igPivotDataSelector, filters, rows, columns or measures.
					paramType="string" The type of the item - Hierarchy, Measure or MeasureList.
					paramType="string" The unique name of the item.
					returnType="bool"  The function must return true if the item should be accepted.
				*/
				customMoveValidation: null
			},
			/* type="object" Configuration settings for the panel containing the igPivotGrid.
			*/
			pivotGridPanel: {
				/* type="bool" Determines if the panel containing the igPivotGrid will be resizable.
				*/
				resizable: true,
				/* type="bool" Determines if the panel containing the igPivotGrid will be collapsible.
				*/
				collapsible: false,
				/* type="bool" Determines if the panel containing the igPivotGrid will initially collapsed.
				*/
				collapsed: false,
				/* type="string|number|null" Determines the size of the igPivotGrid panel.
					string The panel size can be set in pixels (px).
					number The size can be set as a number.
					null type="object" will automatically size the panel.
				*/
				size: null
			},
			/* type="object" Configuration settings for the panel containing the igPivotDataSelector.
			*/
			dataSelectorPanel: {
				/* type="left|right" Determines the position of the data selector panel inside the igPivotView widget.
				*/
				location: "right",
				/* type="bool" Determines if the panel containing the igPivotDataSelector will be resizable.
				*/
				resizable: true,
				/* type="bool" Determines if the panel containing the igPivotDataSelector will be collapsible.
				*/
				collapsible: true,
				/* type="bool" Determines if the panel containing the igPivotDataSelector will initially collapsed.
				*/
				collapsed: false,
				/* type="string|number|null" Determines the size of the igPivotDataSelector  panel. The recommended value is 250px.
					string The panel size can be set in pixels (px).
					number The size can be set as a number.
					null type="object" will automatically size the panel.
				*/
				size: 250
			}
		},
		_create: function () {
			var $this = this, elementName, splitter, panel, pivotGrid, dataSelector,
				panels, dataSelectorLocation, dataSource;

			elementName = this.element[0].nodeName.toUpperCase();
			if (elementName !== "DIV") {
				throw new Error(elementName + $.ig.PivotDataSelector.locale.invalidBaseElement);
			}

			this.element.addClass("ui-igpivotview");

			if (this.options.width) {
				this.element.width(this.options.width);
			}

			if (this.options.height) {
				this.element.height(this.options.height);
			}

			dataSelectorLocation = this.options.dataSelectorPanel.location;

			// Create the layout for the PivotView.
			splitter = $("<div></div>").appendTo(this.element);
			// Append the panel for the PivotGrid.
			panel = $("<div></div>").appendTo(splitter);
			// Add the placeholder for the PivotGrid.
			pivotGrid = $("<table></table>").attr("id", this.element.attr("id") + "_pivotGrid").appendTo(panel);
			// Depending on the DataSelector location, prepend or append the DataSelector panel.
			panel = $("<div></div>")[dataSelectorLocation === "right" ? "appendTo" : "prependTo"](splitter);
			// Add the placeholder for the DataSelector.
			dataSelector = $("<div></div>").attr("id", this.element.attr("id") + "_dataSelector").appendTo(panel);

			panels = [this.options.pivotGridPanel, this.options.dataSelectorPanel];
			if (dataSelectorLocation !== "right") {
				panels.reverse();
			}

			// Prepare the data source.
			dataSource = $.ig.Pivot._pivotShared._createDataSource(
				this.options.dataSource,
				this.options.dataSourceOptions
			);

			// N.A. - Fix for bug #134667.
			// First render the data selector and then the splitter to
			// ensure that the splitter's panels will contain dom elements
			// when it calculates the panels.
			// 1. Create the data selector.
			dataSelector.igPivotDataSelector($.extend(true, this.options.dataSelectorOptions, {
				dataSource: dataSource
				//width: (dataSelectorLocation === "right" ? splitter.igSplitter("secondPanel") : splitter.igSplitter("firstPanel")).width(),
				//height: splitter.igSplitter("option", "height")
			}));
			// 2. Create the splitter.
			splitter.igSplitter({
				width: this.options.width || this.element.width(),
				height: this.options.height || this.element.height(),
				panels: panels
			}).bind("igsplitterresizeended igsplitterexpanded igsplittercollapsed", function () {
				$this._setSize();
			}).bind("igsplitterresizestarted", function () {
				$(".ui-igpivot-metadatadropdown,.ui-igpivot-filterdropdown").remove();
			});

			if ($.ui.igSplitter.prototype.css.verticalPanel) {
				$($.ui.igSplitter.prototype.css.verticalPanel.replace(/ui-/g, ".ui-").replace(/ /g, "")).css("overflow", "hidden");
			}

			// 3. Create the pivot grid.
			pivotGrid.igPivotGrid($.extend(true, this.options.pivotGridOptions, {
				dataSource: dataSource,
				width: (dataSelectorLocation === "right" ? splitter.igSplitter("firstPanel") : splitter.igSplitter("secondPanel")).width(),
				height: splitter.igSplitter("option", "height")
			}));

			// Refresh the layout of the splitter panels.
			splitter.igSplitter("refreshLayout");
			// Set the sizes of the pivot grid and the data selector.
			this._setSize();

			// Bind the defer update of the data selector to the pivot grid.
			dataSelector.bind("igpivotdataselectordeferupdatechanged", function (evt, ui) {
				pivotGrid.igPivotGrid("option", "deferUpdate", ui.deferUpdate);
			});
		},
		_setOption: function (key, value) {
			switch (key) {
			case "dataSource":
				this.options.dataSourceOptions = null;
				this.options.dataSource = $.ig.Pivot._pivotShared._createDataSource(value, null);
				this.dataSelector().option("dataSource", this.options.dataSource);
				this.pivotGrid().option("dataSource", this.options.dataSource);
				break;
			case "dataSourceOptions":
				this.options.dataSourceOptions = value;
				this.options.dataSource = $.ig.Pivot._pivotShared._createDataSource(null, value);
				this.dataSelector().option("dataSource", this.options.dataSource);
				this.pivotGrid().option("dataSource", this.options.dataSource);
				break;
			case "width":
			case "height":
				$.Widget.prototype._setOption.call(this, arguments);
				// Currently igSplitter does not support resizing.
				this.splitter().element[key](value);
				this._setSize();
				break;
			}
		},
		_setSize: function () {
			// Resize the PivotGrid when the splitter is resized.
			var splitter = this.splitter(), pivotGrid = this.pivotGrid(), dataSelector = this.dataSelector(),
				height = this.element.height(),
				dataSelectorLocation, pivotGridPanelWidth, dataSelectorPanelWidth;

			dataSelectorLocation = this.options.dataSelectorPanel.location;
			if (dataSelectorLocation === "right") {
				pivotGridPanelWidth = splitter.firstPanel().width();
				dataSelectorPanelWidth = splitter.secondPanel().width();
			} else {
				pivotGridPanelWidth = splitter.secondPanel().width();
				dataSelectorPanelWidth = splitter.firstPanel().width();
			}

			// Resize the grid.
			pivotGrid.option("width", pivotGridPanelWidth);
			pivotGrid.option("height", height);

			// Resize the data selector.
			dataSelector.option("width", dataSelectorPanelWidth);
			dataSelector.option("height", height);
		},
		pivotGrid: function () {
			/* Returns the igPivotGrid instance of the pivot view.
				returnType="object"
			*/
			try {
				return this.element.find(":ui-igPivotGrid").data(_igPivotGrid);
			} catch(e) {
				if (e === "Syntax error, unrecognized expression: Syntax error, unrecognized expression: ui-igPivotGrid" || 
					e.message === "Syntax error, unrecognized expression: ui-igPivotGrid") {
					return this.element.find(":ui-igpivotgrid").data(_igPivotGrid);
				}

				throw e;
			}
			
		},
		dataSelector: function () {
			/* Returns the igPivotDataSelector instance of the pivot view.
				returnType="object"
			*/
			try {
				return this.element.find(":ui-igPivotDataSelector").data(_igPivotDataSelector);    
			} catch(e) {
				if (e === "Syntax error, unrecognized expression: Syntax error, unrecognized expression: ui-igPivotDataSelector" || 
					e.message === "Syntax error, unrecognized expression: ui-igPivotDataSelector") {
					return this.element.find(":ui-igpivotdataselector").data(_igPivotDataSelector);
				}
					
				throw e;
			}
			
		},
		splitter: function () {
			/* Returns the igSplitter instance used to separate the pivot grid and the data selector.
				returnType="object"
			*/
			try {
				return this.element.find(":ui-igSplitter").data(_igSplitter);
			} catch(e) {
				if (e === "Syntax error, unrecognized expression: Syntax error, unrecognized expression: ui-igSplitter" || 
					e.message === "Syntax error, unrecognized expression: ui-igSplitter") {
					return this.element.find(":ui-igsplitter").data(_igSplitter);
				}
				
				throw e;
			}
			
		},
		destroy: function () {
			/* destroy is part of the jQuery UI widget API and does the following:
				1. Remove custom CSS classes that were added.
				2. Unwrap any wrapping elements such as scrolling divs and other containers.
				3. Unbind all events that were bound.
			*/
			this.element.removeClass("ui-igpivotview");
			try {
				this.element.find(":ui-igPivotGrid").igPivotGrid("destroy");
				this.element.find(":ui-igPivotDataSelector").igPivotDataSelector("destroy");
				this.element.find(":ui-igSplitter").igSplitter("destroy").remove();
				$.Widget.prototype.destroy.call(this);
			} catch(e) {
				if (typeof e === "string") { 
					if (e.indexOf("unrecognized expression: ui-ig") !== -1) { 
						this.element.find(":ui-igpivotgrid").igPivotGrid("destroy");
						this.element.find(":ui-igpivotdataselector").igPivotDataSelector("destroy");
						this.element.find(":ui-igsplitter").igSplitter("destroy").remove();

						$.Widget.prototype.destroy.call(this);
						return;
					}
				} else {
					if (e.message.indexOf("unrecognized expression: ui-ig") !== -1) { 
						this.element.find(":ui-igpivotgrid").igPivotGrid("destroy");
						this.element.find(":ui-igpivotdataselector").igPivotDataSelector("destroy");
						this.element.find(":ui-igsplitter").igSplitter("destroy").remove();

						$.Widget.prototype.destroy.call(this);
						return;
					}
				}

				throw e;
			}
		}
	});
	$.extend($.ui.igPivotView, { version: "16.1.20161.2145" });
}(jQuery));