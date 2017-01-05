/*!@license
* Infragistics.Web.ClientUI Tree Grid localization resources 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/

/*global jQuery */
(function ($) {
	$.ig = $.ig || {};

	if (!$.ig.TreeGrid) {
		$.ig.TreeGrid = {};

		$.extend($.ig.TreeGrid, {
			locale: {
				fixedVirtualizationNotSupported: 'RowVirtualization に異なる virtualizationMode 設定を使用してください。virtualizationMode を "continuous" に設定してください。'
			}
		});

		$.ig.TreeGridPaging = $.ig.TreeGridPaging || {};

		$.extend($.ig.TreeGridPaging, {
			locale: {
				contextRowLoadingText: "読み込んでいます",
				contextRowRootText: "ルート",
				columnFixingWithContextRowNotSupported: "ColumnFixing に異なる contextRowMode 設定を使用してください。contextRowMode で ColumnFixing を有効にするために none に設定してください。"
			}
		});

		$.ig.TreeGridFiltering = $.ig.TreeGridFiltering || {};

		$.extend($.ig.TreeGridFiltering, {
			locale: {
				filterSummaryInPagerTemplate: "一致するレコード ${currentPageMatches} / ${totalMatches}"
			}
		});

		$.ig.TreeGridRowSelectors = $.ig.TreeGridRowSelectors || {};

		$.extend($.ig.TreeGridRowSelectors, {
			locale: {
			    multipleSelectionWithTriStateCheckboxesNotSupported: "複数セクションに他の checkBoxMode 設定を使用してください。checkBoxMode の複数セクションを有効にするために biState に設定してください。"
			}
		});
    }
})(jQuery);


/*!@license
 * Infragistics.Web.ClientUI Tree Grid 16.1.20161.2145
 *
 * Copyright (c) 2011-2016 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 *	jquery-1.9.1.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	infragistics.dataSource.js
 *	infragistics.ui.shared.js
 *	infragistics.util.js
 *	infragistics.ui.grid.framework.js
 */

/*global jQuery */
/*jshint -W106 */
(function ($) {
	var _aNull = function (val) { return val === null || val === undefined; };
	$.widget("ui.igTreeGrid", $.ui.igGrid, {
		css: {
			/* class applied to root DOM element container for the grid */
			containerClasses: "ui-igtreegrid",
			/* classes applied to the expander span element, when the row is expanded */
			expandCellExpanded: "ui-icon ui-igtreegrid-expansion-indicator ui-icon-minus",
			/* classes applied to the expansion indicator SPAN when the row is collapsed */
			expandCellCollapsed: "ui-icon ui-igtreegrid-expansion-indicator ui-icon-plus",
			/* class applied to the expansion indicator's TD element of the when renderExpansionIndicatorColumn is set to true */
			dataSkipCell: "ui-igtreegrid-non-data-column",
			/* class applied to the TD element that contains the expansion indicator */
			expandColumn: "ui-igtreegrid-expansion-indicator-cell",
			/* class applied to data cell of the expansion indicator container */
			dataColumnExpandContainer: "ui-igtreegrid-expansion-indicator-container",
			/*class applied to the expansion indicator container if the expansion column is rendered in the grid. */
			expandColumnContainer: "ui-igtreegrid-expansion-column-container",
			/* class applied to the element (in the non-data-cell) holding expansion indicator column - applied only when renderExpansionIndicatorColumn is false. */
			expandContainer: "ui-igtreegrid-expandcell",
			/* class applied to the expansion indicator column's TH element if renderExpansionIndicatorColumn is set to true */
			expandHeaderCell:
				"ui-igtreegrid-expansion-indicator-header-cell ui-iggrid-header ui-widget-header",
			/* classes applied to the data row. For instance if a row is part of the root level (at index 0) then the class of ui-igtreegrid-rowlevel0 is applied to the row. The numbered suffix in the class name changes to reflect the grid row's index. */
			rowLevel: "ui-igtreegrid-rowlevel"
		},
		options: {
			/* type="string" Specifies the indentation (in pixels or percent) for a tree grid row. Nested indentation is achieved by calculating the level times the indentation value. Ex: '10px' or '5%'. Default is 30. */
			indentation: 30,
			/* type="number" if initial indentation level is set then it is used to be calculated width of the data skip column(usually used when remote load on demand is enabled)*/
			initialIndentationLevel: -1,
			/* type="bool" Specifies if rows(that have child rows) will have an expander image that will allow end users to expand and collapse them. This option can be set only at initialization. */
			showExpansionIndicator: true,
			/* type="string" Specifies the expansion indicator tooltip text. */
			expandTooltipText: $.ig.Grid.locale.expandTooltip,
			/* type="string" Specifies the collapse indicator tooltip text.*/
			collapseTooltipText: $.ig.Grid.locale.collapseTooltip,
			/* type="string" Unique identifier used in a self-referencing flat data source. Used with primaryKey to create a relationship among flat data sources. */
			foreignKey: null,
			/* type="number" Specifies the depth down to which the tree grid would be expanded upon initial render. To expand all rows set value to -1. Default is -1. */
			initialExpandDepth: -1,
			/* type="number"      Specifies the foreign key value in the data source to treat as the root level once the grid is data bound. Defaults to -1 (which includes the entire data source) */
			foreignKeyRootValue: -1,
			/* type="bool" Specify whether to render non-data column which contains expander indicators */
			renderExpansionIndicatorColumn: false,
			/* type="string|object" a reference or name of a javascript function which changes first data cell - renders indentation according to databound level */
			renderFirstDataCellFunction: null,
			/* type="string" Property name of the array of child data in a hierarchical data source.*/
			childDataKey: "childData",
			/* type="string|object" a reference or name of a javascript function which renders expand indicators(called ONLY IF option renderExpansionIndicatorColumn is true) */
			renderExpansionCellFunction: null,
			/*Specifies to the tree grid if data is loaded on demand from a remote server. Default is false. */
			enableRemoteLoadOnDemand: false,
			/* type="object" Options object to configure data source-specific settings*/
			dataSourceSettings: {
				/* The name of the property that keeps track of the expansion state of a data item. Defaults to __ig_options.expanded.*/
				propertyExpanded: "__ig_options.expanded",
				/* The name of the property that keeps track of the level in the hierarchy.Defaults to __ig_options.dataLevel.*/
				propertyDataLevel: "__ig_options.dataLevel",
				/* type="bool" if set to TRUE it is expected that the source of data is normalized and transformed(has set dataLevel and expansion state). The source of data is used as flatDataView. Usually used when the paging is remote and paging mode is allLevels, or features are remote(and the processing of the returned result should be made on the server)*/
				initialFlatDataView: false
			}
		},
		events: {
			/* cancel="true" Fired when a row is about to be expanded.
				use args.owner to access the instance of the igTreeGrid
				use args.row to access the row element (as a wrapped jQuery object) that is about to be expanded
				use args.fixedRow to access the row element (as a jQuery wrapped object) in a fixed column that is about to expanded. If there are no fixed columns then this property returns undefined.
				use args.dataLevel to access the level in the hierarchy associated with the row
			*/
			rowExpanding: "rowExpanding",
			/* Fired when a row is expanded.
				use args.owner to access the instance of the igTreeGrid
				use args.row to access the row element (as a wrapped jQuery object) that is about to be expanded
				use args.fixedRow to access the row element (as a jQuery wrapped object) in a fixed column that is about to expanded. If there are no fixed columns then this property returns undefined.
				use args.dataLevel to access the level in the hierarchy associated with the row
				use args.dataRecord to access the source data record
			*/
			rowExpanded: "rowExpanded",
			/* cancel="true" Fired when a row is about to be collapsed.
				use args.owner to access the instance of the igTreeGrid
				use args.row to access the row element (as a wrapped jQuery object) that is about to be expanded
				use args.fixedRow to access the row element (as a jQuery wrapped object) in a fixed column that is about to expanded. If there are no fixed columns then this property returns undefined.
				use args.dataLevel to access the level in the hierarchy associated with the row
			*/
			rowCollapsing: "rowCollapsing",
			/* Fired after a row is collapsed
				use args.owner to access the instance of the igTreeGrid
				use args.row to access the row element (as a wrapped jQuery object) that is about to be expanded
				use args.fixedRow to access the row element (as a jQuery wrapped object) in a fixed column that is about to expanded. If there are no fixed columns then this property returns undefined.
				use args.dataLevel to access the level in the hierarchy associated with the row
				use args.dataRecord to access the source data record
			*/
			rowCollapsed: "rowCollapsed"
		},
		/* type="bool" if false data source is flat with self-referencing data. */
		_isHierarchicalDataSource: true,
		_create: function () {
			this._checkForUnsoppertedScenarios();
			var func = this.options.renderFirstDataCellFunction;
			if (func && $.type(func) !== "function") {
				if (window[ func ] && typeof window[ func ] === "function") {
					func = window[ func ];
				}
			}
			if (func && $.type(func) === "function") {
				this._renderFirstDataCellHandler = func;
			} else {
				this._renderFirstDataCellHandler = $.proxy(this._renderFirstDataCell, this);
			}
			func = this.options.renderExpansionCellFunction;
			if (func && $.type(func) !== "function") {
				if (window[ func ] && typeof window[ func ] === "function") {
					func = window[ func ];
				}
			}
			if (func && $.type(func) === "function") {
				this._renderExpandCellHandler = func;
			} else {
				this._renderExpandCellHandler = $.proxy(this._renderExpandCell, this);
			}
			this._overrideFunctions();
			this._attachEvents();
			this.element.data(
				$.ui.igGrid.prototype.widgetName,
				this.element.data($.ui.igTreeGrid.prototype.widgetName)
			);
			$.ui.igGrid.prototype._create.apply(this, arguments);
			this.element.attr("role", "treegrid");
		},
		_checkForUnsoppertedScenarios: function () {
			if (this._rowVirtualizationEnabled() &&
				this.options.virtualizationMode === "fixed") {
				throw new Error($.ig.TreeGrid.locale.fixedVirtualizationNotSupported);
			}
		},
		_wrapElementDiv: function () {
			$.ui.igGrid.prototype._wrapElementDiv.apply(this, arguments);
			this.element.data($.ui.igTreeGrid.prototype.widgetName, this);
			/* when igGrid is instantiated on DIV then this.element is referencing inner data table - binding should be applied to the NEW this.element */
			this._overrideFunctions();
		},
		_removeOverridenFunction: function () {
			if (!this._functionsOverriden) {
				return;
			}
			var f, funcs = this._functionsOverriden;
			for (f in funcs) {
				if (funcs.hasOwnProperty(f)) {
					this.element[ f ] = funcs[ f ];
				}
			}
			delete this._functionsOverriden;
		},
		_overrideFunctions: function () {
			/* in grid features for binding to events it is used iggrid as widgetEventName - e.g. this.elememnt.bind('iggridheaderrendered')
			now for binding to client events it should be used igTreeGrid - e.g. this.element.bind('igtreegridheaderrendered').
			to not re-write each igGridFeaure we will re-write this.element.bind and this.element.unbind */
			this._overrideFunction("bind");
			this._overrideFunction("unbind");
			/* M.H. 7 Sep 2015 Fix for bug 205868: EnableAddRow option does not work in TreeGrid Updating */
			this._overrideFunction("on");
			this._overrideFunction("off");
		},
		_overrideFunction: function (functionName) {
			/* igGrid features are binding to the grid events using igGrid event namespace but in case of igTreeGrid the event name space is igTreeGrid
			for now a workaround is to override function bind(of this.element) - better solution
			TO DO: USE event namespace when binding to grid events in igGrid features */
			var e = this.element, func;
			if ($.type(e[ functionName ]) !== "function") {
				return;
			}
			if (!this._functionsOverriden) {
				this._functionsOverriden = {};
			}
			func = e[ functionName ];
			if (!this._functionsOverriden[ functionName ]) {
				this._functionsOverriden[ functionName ] = func;
			}
			e[ functionName ] = function (name, arg1, arg2) {
				var strIgGrid = "iggrid", argsLen = arguments.length, evtName, oEvtName;
				if ($.type(name) === "string" &&
						name.indexOf(strIgGrid) === 0 && name.length > strIgGrid.length &&
						(argsLen === 2 || argsLen === 3)) {
					name = "igtreegrid" + name.substr(strIgGrid.length);
					if (argsLen === 2) {
						return func.call(e, name, arg1);
					}
					return func.call(e, name, arg1, arg2);
				} else if ($.type(name) === "object") {
					/* in case of $.on/$.off rewrite */
					for (evtName in name) {
						if (name.hasOwnProperty(evtName)) {
							if ($.type(evtName) === "string" &&
								evtName.indexOf(strIgGrid) === 0) {
								oEvtName = evtName;
								evtName = "igtreegrid" + evtName.substr(strIgGrid.length);
								name[ evtName ] = name[ oEvtName ];
							}
						}
					}
				}
				return func.apply(e, arguments);
			};
		},
		_updateParentRowAfterDelete: function ($pRow, dataLevel) {
			/* $row - previous(to the deleted row) TR in DOM
			dataLevel of the row that is deleted
			check whether parent row has children and all of them are deleted - remove toggle button */
			var dl, found, rowId, children,
				ds = this.dataSource,
				primaryKeyCol, rec,
				identation = this.dataSource.getDataBoundDepth();
			if (isNaN(dataLevel) || dataLevel <= 0) {
				return;
			}
			/* get parent row from previous row */
			while ($pRow.length === 1) {
				dl = parseInt($pRow.attr("aria-level"), 10);
				if (isNaN(dl)) {
					break;
				}
				if (dl < dataLevel) {
					found = true;
					break;
				}
				$pRow = $pRow.prev("tr");
			}
			/* detect whether parentRow has children */
			if (found) {
				rowId = $pRow.attr("data-id");
				primaryKeyCol = this.columnByKey(this.options.primaryKey);
				if (primaryKeyCol.dataType === "number" || primaryKeyCol.dataType === "numeric") {
					rec = ds.findRecordByKey(parseInt(rowId, 10));
				} else {
					rec = ds.findRecordByKey(rowId);
				}
				if (rec) {
					children = rec[ this.options.childDataKey ];
					if (!children || children.length === 0) {
						if (this.hasFixedColumns()) {
							/* M.H. 9 Mar 2016 Fix for bug 215553: When there"s an initially fixed column and a root level row is deleted an error is thrown */
							$pRow = this.container().find("tr[data-id=\"" + rowId + "\"]");
						}
						$pRow.find("span[data-expandcell-indicator]").empty();
						$pRow.find("td[data-expand-cell]").removeAttr("data-expand-cell");
					}
				}
			}
			this._rerenderDataSkipColumn(identation);
		},
		rollback: function (rowId, updateUI) {
			/* Clears the transaction log (delegates to igDataSource). Note that this does not update the UI. In case the UI must be updated, set the second parameter "updateUI" to true, which will trigger a call to dataBind() to re-render the contents.
				paramType="string|number" optional="true" If specified, will only rollback the transactions with that row id.
				paramType="bool" optional="true" Whether to update the UI or not.
				returnType="array" The transactions that have been rolled back.
			*/
			var transactions = $.ui.igGrid.prototype.rollback.apply(this, arguments),
				transaction, i, tridx, tr, prevTr, dataLevel;
			if (updateUI === true) {
				if (rowId !== null && rowId !== undefined) {
					if (transactions === null || transactions === undefined || transactions.length === 0) {
						/* if no transactions are returned by the data source we shouldn't do anything */
						return;
					}
					i = transactions.length;
					while (i-- > 0) {
						transaction = transactions[ i ];
						tr = this.element.find("tr[data-id='" + transaction.rowId + "']");
						if (transaction.type === "insertnode") {
							/* the row found needs to be removed */
							tridx = this.element
										.children("tbody")
										.children("tr:not([data-container],[data-grouprow])")
										.index(tr);
							prevTr = tr.prev("tr");
							dataLevel = parseInt(tr.attr("aria-level"), 10);
							tr.remove();
							this._reapplyZebraStyle(tridx);
							this._updateParentRowAfterDelete(prevTr, dataLevel);
						}
					}
				} else {
					this.dataBind();
				}
			}
			return transactions;
		},
		dataBind: function () {
			/* causes the treegrid to data bind to the data source (local or remote) , and re-render all of the data */
			if (!this._initialized) {
				this._renderExtraHeaderCellHandler = $.proxy(this._renderExtraHeaderCells, this);
				this._renderExtraFooterCellHandler = $.proxy(this._renderExtraFooterCells, this);
				this._headerInitCallbacks.push({ type: "TreeGrid", func: this._renderExtraHeaderCellHandler });
				this._footerInitCallbacks.push({ type: "TreeGrid", func: this._renderExtraFooterCellHandler });
			}
			$.ui.igGrid.prototype.dataBind.apply(this, arguments);
		},
		_generateDataSourceOptions: function () {
			var o = this.options, ds = o.dataSource, instanceOfDs, tds,
				opts = $.ui.igGrid.prototype._generateDataSourceOptions.apply(this, arguments);

			opts.treeDS = {
				childDataKey: o.childDataKey,
				initialExpandDepth: o.initialExpandDepth,
				foreignKey: o.foreignKey,
				foreignKeyRootValue: o.foreignKeyRootValue
			};
			opts.treeDS = $.extend(opts.treeDS, o.dataSourceSettings);
			opts.treeDS.enableRemoteLoadOnDemand = o.enableRemoteLoadOnDemand;
			opts.treeDS.dataSourceUrl = o.dataSourceUrl;
			/* M.K. 2/13/2015 Fix for bug 189157: When only DataSourceUrl is set via the MVC wrapper Load On Demand requests are incorrect */
			if (o.dataSourceUrl === null && typeof(ds) === "string") {
				opts.treeDS.dataSourceUrl = ds;
			}

			instanceOfDs = ds &&
					typeof ds._xmlToArray === "function" &&
					typeof ds._encodePkParams === "function";
			if (instanceOfDs) {
				/* instance of tree hierarchical datasource */
				if (ds._isHierarchicalDataSource !== undefined) {
					ds.settings.treeDS = ds.settings.treeDS || {};
					ds.settings.treeDS = $.extend(ds.settings.treeDS, opts.treeDS);
				} else {
					if ($.type(ds.settings.dataSource) === "array" ||
							$.type(ds.settings.dataSource) === "object") {
						tds = ds.settings.dataSource;
					} else if ($.type(ds.settings.dataSource) !== "string") {
						tds = ds.data();
					} else {
						tds = [];
					}
					ds.settings.dataSource = null;
					ds.settings.data = null;
					if (opts && opts.dataSource) {
						opts.dataSource = null;
					}
					opts = $.extend(true, {}, ds.settings, opts);
					opts.dataSource = tds;
					tds = null;
					o.dataSource = new $.ig.TreeHierarchicalDataSource(opts);
				}
			} else {
				opts.dataSource = ds;
			}
			if (o.dataSourceType !== null) {
				opts.type = o.dataSourceType;
			}
			return opts;
		},
		_getDataColumns: function (cols) {
			var i, j, res = [], colsLength = cols.length, dCols;

			for (i = 0; i < colsLength; i++) {
				if (cols[ i ].group !== undefined && cols[ i ].group !== null) {
					dCols = this._getDataColumns(this._getDataColumns(cols[ i ].group));
					for (j = 0; j < dCols.length; j++) {
						res.push(dCols[ j ]);
					}
				} else {
					res.push(cols[ i ]);
				}
			}
			return res;
		},
		_generateDataSourceSchema: function () {
			var schema, i, j, rec, prop, cols = this._getDataColumns(this.options.columns),
				k, c, ds = this.options.dataSource;
			/* generate top level schema */
			if (cols.length > 0 && !this.options.autoGenerateColumns) {
				/* if autoGenerateColumns is true, fields for all columns in the data source must be specified */
				schema = {};
				schema.fields = [];
				j = 0;
				for (i = 0; i < cols.length; i++) {
					/* M.H. 4 Sep 2012 Fix for bug #120414 */
					if (cols[ i ].unbound === true) {
						continue;
					}
					schema.fields[ j ] = {};
					schema.fields[ j ].name = cols[ i ].key;
					schema.fields[ j ].type = cols[ i ].dataType;
					schema.fields[ j ].mapper = cols[ i ].mapper;
					j++;
				}
				schema.searchField = this.options.responseDataKey;
			} else if (this.options.autoGenerateColumns) {
				schema = {};
				schema.fields = [];
				if (this.options.dataSource && this.options.dataSource.length &&
						this.options.dataSource.length > 0 &&
						$.type(this.options.dataSource) === "array") {
					rec = this.options.dataSource[ 0 ];
					for (prop in rec) {
						if (rec.hasOwnProperty(prop)) {
							/* if the column isn't already defined in the columns collection */
							for (k = 0; k < cols; k++) {
								if (cols[ k ].key === prop) {
									c = cols[ k ];
									break;
								}
							}
							if (_aNull(c)) {
								schema.fields.push({ name: prop, type: $.ig.getColType(rec[ prop ]) });
							} else {
								/* M.H. 4 Sep 2012 Fix for bug #120414 */
								if (c.unbound === true) {
									continue;
								}
								schema.fields.push({ name: prop, type: c.dataType });
							}
							/* count++; */
						}
					}
				}
			}
			this._trigger(
				this.events.schemaGenerated,
				null,
				{ owner: this, schema: schema, dataSource: ds }
			);
			return schema;
		},
		_createDataSource: function (dataOptions) {
			var ds = this.options.dataSource, currentDataSource;
			if (!ds ||
				typeof ds._xmlToArray !== "function" ||
				typeof ds._encodePkParams !== "function") {
				currentDataSource = new $.ig.TreeHierarchicalDataSource(dataOptions);
			} else {
				currentDataSource = $.ui.igGrid.prototype._createDataSource.apply(this, arguments);
				}
				return currentDataSource;
		},
		_containersRendered: function () {
			if (this.options.renderExpansionIndicatorColumn) {
				this._addDataSkipColumn();
			}
		},
		_renderData: function () {
			var i = this._initialized;
			$.ui.igGrid.prototype._renderData.apply(this, arguments);
			/* P.Zh. 12 Mar 2016 Fix for bug #215826: When autoGenerateColumns is true clicking the expander indicator of a row enters in edit mode. */
			if (!i) {
				this._bindEvtsToExpIndicators();
			}
		},
		_bindEvtsToExpIndicators: function () {
			/* bind events to expansion indicators - for toggle rows, keyboard handling */
			var $cont = this.container(), selector;
			if (!this.options.renderExpansionIndicatorColumn) {
				selector = "tbody>tr>td>span[data-expandcell-indicator]";
			} else {
				selector = "td[data-expand-cell=1]";
			}
			$cont.off(".toggleTreegrid");
			$cont.on({
				"mouseup.toggleTreegrid": $.proxy(this._onMouseUpExpander, this),
				"keydown.toggleTreegrid": $.proxy(this._onKeyDownExpander, this),
				"click.toggleTreegrid": function (e) {
					e.stopPropagation();
				}
			}, selector);
		},
		/* Virtualization support */
		/* continuous support */
		_getTotalRowCount: function () {
			return this._getDataView().length;
		},
		_getDataView: function () {
			return this.dataSource.flatDataView();
		},
		_renderVirtualRecordsContinuous: function (from) {
			/* when expand/collapse rows we need to re-build virtual rows, re-calculate average row height.
			But when using _renderVirtualRecordsContinuous of the igGrid framework the scrolling position is reset to 0.
			for this specific case we do not reset scrolling position and renders virtual rows from the passed argument*/
			var to, $scrollContainer, scrllToBttm = false, $ae, $cell, $row, rowId, cellInd, $tr;
			if (_aNull(from)) {
				$.ui.igGrid.prototype._renderVirtualRecordsContinuous.apply(this, arguments);
			} else {
				this._totalRowCount = this._getTotalRowCount();
				if (from > this._totalRowCount) {
					return $.ui.igGrid.prototype._renderVirtualRecordsContinuous.apply(this, arguments);
				}
				this._virtualRowCount = this._determineVirtualRowCount();
				if (this._virtualRowCount > this._totalRowCount) {
					this._virtualRowCount = this._totalRowCount;
				}
				to = from + parseInt(this._virtualRowCount, 0);
				if (to > this._totalRowCount) {
					to = this._totalRowCount - 1;
					from = to - this._virtualRowCount;
					scrllToBttm = true;
					if (from < 0) {
						from = 0;
					}
				}

				$ae = $(document.activeElement);
				$cell = $ae.closest("td");
				$row = $ae.closest("tr");
				cellInd = $cell.index();
				rowId = $row.attr("id");

				this._renderRecords(from, to);
				this._avgRowHeight = this._calculateAvgRowHeight();
				this._setScrollContainerHeight(this._totalRowCount * this._avgRowHeight);
				/* M.H. 17 July 2015 Fix for bug 201854: Collapsing the last row does not scroll to the correct view in the treegrid when there is virtualization enabled */
				if (scrllToBttm) {
					$scrollContainer = this._scrollContainer();
					$scrollContainer[ 0 ].scrollTop = this._totalRowCount * this._avgRowHeight + 1;
				}
				if (rowId) {
					/* M.H. 1 Mar 2016 Fix for bug 214904: Exception is thrown when moving column from Unfixed to Fixed to area and afterwards trying to collapse */
					$tr = $("#" + rowId.replace(/(:|\.|\[|\]|,|\/)/g, "\\$1"));
					if ($tr.length) {
						if ($ae.is("tr")) {
							$ae = $tr;
							$tr.focus();
						} else if ($ae.is("td")) {
							$ae = $tr.children("td:nth-child(" + (cellInd + 1) + ")");
							$ae.focus();
						} else if ($ae.attr("data-expand-button") !== undefined) {
							$ae = $tr.find("[data-expand-button]");
							$ae.focus();
			}
						this._fireInternalEvent("_virtualRecordsRendered",
								{
									row: $tr,
									activeElement: $ae,
									cellInd: cellInd
								}
						);
					}
				}
			}
		},
		/* //Virtualization support */
		_rerenderDataSkipColumn: function (/*indentation*/) {
			/* rerender data-skip column with the specified indentation - use only if option renderExpansionIndicatorColumn is TRUE*/
			var $cntnr = this.container();
			/* $cntnr.find("colgroup").find("col[data-treegrid-col]").remove(); */
			$cntnr.find("thead").find("th").find("[data-treegrid-th]").remove();
			this._rerenderColgroups();
			if (this.options.width === null) {
				this._updateContainersWidthOnGridWidthNull();
			} else {
				/* M.H. 12 Mar 2016 Fix for bug 215729: When there's no width set and renderExpansionIndicatorColumn is true after adding multiple child rows the last column stops being visible */
				this._adjustLastColumnWidth(true);
			}
		},
		_addDataSkipColumn: function (dbd) {
			/* if argument dbd is not set take it from option initialIndentationLevel or from databounddepth of the datasource */
			if (!this.options.renderExpansionIndicatorColumn) {
				return;
			}
			var optInd = this.options.indentation, indent,
				$thDataSkip, $gridColgroup, $headersTbl, $footersTbl, cf;
			if (_aNull(dbd)) {
				dbd = this.options.initialIndentationLevel > 0 ?
							this.options.initialIndentationLevel :
							this.dataSource.getDataBoundDepth();
			}
			dbd = dbd || 1;
			if (dbd >= 0) {
				indent = parseInt(optInd, 10) * dbd;
				if (optInd && optInd.indexOf &&
						optInd.indexOf("%") >= 0) {
					/* M.H. 13 Nov 2014 Fix for bug #185115: Indentaion option for the treegrid does not work with percentages */
					indent = indent + "%";
				}
				/* when we have columnFixing we should detect where dataSkip column should be rendered - in fixed or unfixed part
				for now when fixing direction is left then this should be rendered on the fixed part */
				cf = (this.hasFixedColumns() && this.fixingDirection() === "left");
				if (cf) {
					$gridColgroup = this.fixedBodyContainer().find("colgroup:first");
				} else {
					$gridColgroup = this.element.find("colgroup:first");
				}
				this._addColHelper($gridColgroup, indent);
				if (cf) {
					$headersTbl = this.fixedHeadersTable();
				} else {
					$headersTbl = this.headersTable();
				}
				/* in case of fixed header headers */
				if ($headersTbl.attr("id") !== this.element.attr("id")) {
					this._addColHelper($headersTbl.find("colgroup:first"), indent);
				}
				if (cf) {
					$footersTbl = this.fixedFootersTable();
				} else {
					$footersTbl = this.footersTable();
				}
				/* in case of fixed footers */
				if ($footersTbl.attr("id") !== this.element.attr("id")) {
					this._addColHelper($footersTbl.find("colgroup"), indent);
				}
				if (!$headersTbl.find("> thead th[data-treegrid-th]").length) {
					/* D.K 21 Aug 2015 Fix for bug #205006: Checkboxes are rendered incorrectly when renderExpansionIndicatorColumn is enabled */
					if (!$headersTbl.find("> thead tr:nth-child(1) th.ui-iggrid-rowselector-header").length) {
						$thDataSkip = $("<th></th>").prependTo($headersTbl.find("> thead tr:nth-child(1)"));
					} else {
						$thDataSkip = $("<th></th>")
									.insertAfter($headersTbl.find("> thead tr:nth-child(1) th.ui-iggrid-rowselector-header"));
					}
					$thDataSkip
						.addClass(this.css.expandHeaderCell)
						.attr("data-skip", true)
						.attr("data-treegrid-th", true);
					if (this._isMultiColumnGrid) {
						$thDataSkip.attr("rowspan", this._maxLevel + 1);
					}
				}
			}
		},
		_addColHelper: function ($colgroup, width) {
			var $col = $colgroup.find("col[data-treegrid-col]");
			if ($col.length === 0) {
				$col = $("<col />").prependTo($colgroup)
							.attr("data-skip", "true")
							.attr("data-treegrid-col", "true");
			}
			if (width) {
				$col.width(width);
			}
		},
		_renderHeader: function () {
			$.ui.igGrid.prototype._renderHeader.apply(this, arguments);
			this.container().addClass(this.css.containerClasses);
		},
		_getHtmlForDataView: function (ds, isFixed, start, end) {
			/* get html markup for specified dataSource(if not specified - get flat data view) - used in toggle functions */
			var i, dsLen, html = "";
			ds = ds || this._getDataView();
			dsLen = ds.length;
			if (start === undefined) { /* no params => render all */
				start = 0;
				end = dsLen - 1;
			}
			if (start !== undefined && end === undefined) {
				end = start;
				start = 0;
			}
			start = start < 0 ? 0 : start;
			end = end > dsLen - 1 ? dsLen - 1 : end;
			for (i = start; i <= end; i++) {
				html += this._renderRecord(ds[ i ], i, isFixed);
			}
			return html;
		},
		_renderRecord: function (data, rowIndex, isFixed) {
			return this._renderRecordInternal(data, rowIndex, isFixed);
		},
		_renderRecordInternal: function (data, rowIndex, isFixed, rowData) {
			/* rowData is an object with properties dataBoundDepth, dataParent, hasExpandCell, expand */
			var html = "", markup, isContainerOnTheLeft, idxStart, children, hasChildren,
				o = this.options,
				key = this.options.primaryKey,
				dataBoundDepth, cssClass,
				id = $(this.element).attr("id"), owns = "";
			if (!rowData) {
				children = data[ o.childDataKey ];
				hasChildren = (children &&
									(children === true ||
										($.type(children) === "array" &&
											(o.enableRemoteLoadOnDemand || children.length > 0)))
								);
				rowData = {
					dataBoundDepth: data[ o.dataSourceSettings.propertyDataLevel ],
					parentCollapsed: false,
					hasExpandCell: hasChildren,
					children: children,
					expand: data[ o.dataSourceSettings.propertyExpanded ]
				};
			}
			dataBoundDepth = rowData.dataBoundDepth;
			cssClass = this.css.rowLevel + dataBoundDepth;
			html += "<tr";
			if (rowIndex % 2 !== 0 && this.options.alternateRowStyles) {
				cssClass += " " + this.css.recordAltClass;
			}
			if (this._transformCssCallback) {
				cssClass = this._transformCssCallback(cssClass, data);
			}
			html += " class=\"" + cssClass + "\" data-row-idx=\"" + rowIndex +
				"\" aria-level=\"" + dataBoundDepth + "\"";
			/*jshint -W106*/
			if (!_aNull(key)) {
				/*jscs:disable*/
				html += " data-id=\"" + this._kval_from_key(key, data) +
					"\" id=\"" + id + "_" + this._kval_from_key(key, data) + "\"";
			} else if (!_aNull(data.ig_pk)) {
				html += " data-id=\"" + data.ig_pk + "\" id=\"" + id + "_" + data.ig_pk + "\"";
				/*jscs:enable*/
			}

			if (rowData.parentCollapsed && dataBoundDepth > 0) {
				html += " style=\"display: none;\"";
			}
			if (rowData.hasExpandCell) {
				$(rowData.children).each(function () {
					owns += id + "_" + this[ key ] + " ";
				});
				owns = owns.trimEnd();
				if (owns !== "") {
					html += " aria-owns=\"" + owns;
				}
				html += "\" aria-expanded=\"" + rowData.expand + "\"";
			}
			html += " role=\"row\" tabIndex=\"" + this.options.tabIndex + "\">";
			isContainerOnTheLeft = this._isDataContainerOnTheLeft(isFixed);
			if (this._shouldRenderDataSkipColumn(isFixed)) {
				/* D.K. 1 Sep 2015 Fix for bug #205335 - The expander cell is not selected when you change the page and return back to the previous page. */
				if (this._selection && this._selection instanceof $.ig.SelectedRowsCollection) {
					html += this._editCellStyle(this._renderExpandCellHandler(rowData), data, key);
				} else {
					html += this._renderExpandCellHandler(rowData);
				}
			}
			markup = $.ui.igGrid.prototype._renderRecord.call(this, data, rowIndex, isFixed);
			/* M.H. 7 Nov 2014 Fix for bug #184802: When there are fixed columns calling dataBind for the treegrid renders them in fixed area as well */
			if (isContainerOnTheLeft) {
				html += this._renderFirstDataCellHandler(markup, rowData);
			} else {
				idxStart = markup.indexOf("<td");
				html += markup.substring(idxStart);
			}
			return html;
		},
		_shouldRenderDataSkipColumn: function (isFixed) {
			var fdLeft;
			if (!this.options.renderExpansionIndicatorColumn) {
				return false;
			}
			if (!this.hasFixedColumns()) {
				return true;
			}
			fdLeft = (this.fixingDirection() === "left");
			/* when there are ONLY non-data columns fixed AND fixing direction is LEFT */
			if (!isFixed && fdLeft && this._isFixedNonDataColumnsOnly()) {
				return false;
			}
			return this._isDataContainerOnTheLeft(isFixed);
		},
		_renderFirstDataCell: function (markup, rowData) {
			var newTDSmarkup = "", idxStart, TDSmarkup,
				tdContentFirstInd, otherTDSmarkup, classIdx;
			idxStart = markup.indexOf("<td");
			/* M.H. 25 Nov 2014 Fix for bug #185609: When renderExpansionIndicatorColumn, ColumnFixing and Paging are enabled then going to the second page desyncronize the row heights
			 in case of columnfixing(and no fixed columns but expand column is rendered - which should be fixed by default) then markup does not contain TDs rendered and we should return empty string */
			if (idxStart === -1) {
				return "";
			}
			TDSmarkup = markup.substring(idxStart);
			tdContentFirstInd = TDSmarkup.indexOf(">", 2);
			otherTDSmarkup = TDSmarkup.substring(tdContentFirstInd + 1);/* find first td */
			newTDSmarkup = TDSmarkup.substring(0, tdContentFirstInd);
			if (this.options.renderExpansionIndicatorColumn) {
				newTDSmarkup += " data-cell-shift-container=\"1\">";
			} else {
				/* M.H. 9 Dec 2015 Fix for bug 210374: When sorted column from the treegrid is moved to at first place, its cells does not contain ui-igtreegrid-expansion-indicator-cell class */
				classIdx = newTDSmarkup.indexOf("class=\"");
				if (classIdx > -1) {
					newTDSmarkup = newTDSmarkup.substring(0, classIdx + 7) +
						this.css.expandColumn + " " + newTDSmarkup.substr(classIdx + 7);
				} else {
					newTDSmarkup += " class=\"" + this.css.expandColumn + "\"";
				}
				newTDSmarkup += " data-expand-cell=\"1\">";
			}
			newTDSmarkup += this._renderExpandCellContainer(rowData);
			newTDSmarkup += otherTDSmarkup;
			return newTDSmarkup;
		},
		_renderExpandCellContainer: function (rowData) {
			var span = "", margin, dataBoundDepth = rowData.dataBoundDepth;/* find first td */

			margin = dataBoundDepth > 0 ? parseInt(this.options.indentation, 10) * dataBoundDepth : 0;
			if (this.options.renderExpansionIndicatorColumn) {
				span = "<span class=\"" + this.css.dataColumnExpandContainer +
					"\" data-shift-container=\"1\" style=\"display: inline-block; margin-left:" +
					margin + "px;\"></span>";
			} else {
				span = this._renderExpandCellContainerHelper(rowData);
			}
			return span;
		},
		_renderExpandCellContainerHelper: function (rowData) {
			var span = "", css, title, margin,
				dataBoundDepth = rowData.dataBoundDepth,
				cssEC = this.css.expandContainer,
				renderExpandButton = rowData.hasExpandCell;/* find first td */
			if (this.options.renderExpansionIndicatorColumn) {
				cssEC = this.css.expandColumnContainer;
			}
			margin = dataBoundDepth > 0 ? parseInt(this.options.indentation, 10) * dataBoundDepth : 0;
			/* M.H. 13 Nov 2014 Fix for bug #185115: Indentaion option for the treegrid does not work with percentages */
			if ($.type(this.options.indentation) === "string" &&
					this.options.indentation.indexOf("%") > 0) {
				margin += "%";
			} else {
				margin += "px";
			}
			span = "<span data-expandcell-indicator=\"1\" class=\"" +
				cssEC + "\" style=\"padding-left:" + margin + ";\">";
			if (renderExpandButton && this.options.showExpansionIndicator) {
				if (rowData.expand) {
					css = this.css.expandCellExpanded;
					title = this.options.collapseTooltipText;
				} else {
					css = this.css.expandCellCollapsed;
					title = this.options.expandTooltipText;
				}
				span += "<span data-expand-button class=\"" + css + "\" title=\"" + title +
					"\" tabIndex=\"" + this.options.tabIndex + "\"></span>";
			}
			span += "</span>";
			return span;
		},
		_renderExpandCell: function (rowData) {
			if (!rowData.hasExpandCell) {
				return this._renderDataSkipCell();
			}
			var html, css;
			css = (this.css.expandColumn + " " + this.css.dataSkipCell).trim();
			html = "<td class=\"" + css + "\" data-expand-cell=\"1\" data-skip=\"true\" tabIndex=\"" +
				this.options.tabIndex + "\">";
			if (this.options.showExpansionIndicator) {
				html += this._renderExpandCellContainerHelper(rowData);
			}
			return html + "</td>";
		},
		_renderDataSkipCell: function () {
			return "<td class=\"" + this.css.dataSkipCell + "\" data-skip=\"true\" tabIndex=\"" +
				this.options.tabIndex + "\"></td>";
		},
		_rerenderColgroups: function () {
			$.ui.igGrid.prototype._rerenderColgroups.apply(this, arguments);
			this._addDataSkipColumn();
		},
		/* M.H. 24 Jan 2015 Fix for bug #187687: When columns are auto generated extra columns are rendered in treegrid */
		_columnsGenerated: function () {
			/*  when autogeneratecolumns is TRUE we want to exclude columns like childDataKey, expanded property, etc. */
			var cols = this.options.columns,
				key, i,
				sDS = this.options.dataSourceSettings,
				arrSkipColumns = [ this.options.childDataKey, sDS.propertyExpanded, sDS.propertyDataLevel ];
			for (i = 0; i < cols.length; i++) {
				key = cols[ i ].key;
				if ($.inArray(key, arrSkipColumns) !== -1) {
					$.ig.removeFromArray(cols, i);
					i--;
				}
			}
		},
		_rowVirtualizationEnabled: function () {
			/* check whether grid has row virtualization enabled
			returnType="bool"
			*/
			return this.options.rowVirtualization || this.options.virtualization;
		},
		_rerenderGridRowsOnToggle: function () {
			/* returns whether it should be re-rendered grid rows on expand/collapse. E.g. grid should be re-rendered when pagingOnRootRecordsOnly is false(and paging is enabled) OR row virtualization is enabled
			returnType="bool"
			*/
			var virtualizationEnabled = this._rowVirtualizationEnabled(),
				dsSettings = this.dataSource.settings;
			return (dsSettings.paging.enabled && dsSettings.treeDS.paging.mode !== "rootLevelOnly") ||
					virtualizationEnabled;
		},
		_onDataRecordToggled: function (rec, expand, res, args) {
			/* result is not successful */
			if (!res || !args) {
				return;
			}
			/* if there isn't paging or paging is enabled but pagingOnRootRecordsOnly is TRUE then it should be rendered only expanded/collapsed rows
			1. Set data-state attribute
			2. Refresh flatDataView
			3. Render new DOM ONLY IF expand and children are not populated */
			var flatData, html, level, dataIdx, diff, triggerEvents, $row, callAsync = false,
				$firstRow, rowObj, $fRow, $ufRow, $curRow, offsetTop, customCallback, $this = this,
				fRows, ufRows,
				eArgs, $scrollContainer,
				owner = this, id = $(this.element).attr("id"),
				ariaOwns = "";

			$row = args.row;
			triggerEvents = args.triggerEvents;
			customCallback = args.customCallback;
			rowObj = this._getRows($row);
			$fRow = rowObj.fixedRow;
			$ufRow = rowObj.unfixedRow;
			level = parseInt($row.attr("aria-level"), 10);
			eArgs = {
				owner: this,
				row: $ufRow,
				fixedRow: $fRow,
				dataLevel: level,
				dataRecord: rec
			};
			/* M.K.2/19/2015 Fix for bug 189178: Loading indicator does not show when loading data on demand */
			this._loadingIndicator.hide();
			if (!this._rerenderGridRowsOnToggle()) {
				if (expand && !$row.attr("data-populated")) {
					if (rec) {
						this._toggleRowSuccessors($ufRow);
						$ufRow.add($fRow).attr("data-populated", "1");
						flatData = this.dataSource.getFlatDataForRecord(rec, level + 1);
						/* generate owner-id */
						$(rec[ this.options.childDataKey ]).each(function () {
							ariaOwns += id + "_" + this[ owner.options.primaryKey ] + " ";
						});
						ariaOwns = ariaOwns.trimEnd();
						if (flatData && flatData.flatVisibleData) {
							html = this._getHtmlForDataView(flatData.flatVisibleData, false);
							ufRows = $(html).insertAfter($ufRow);
							$ufRow.attr("aria-owns", ariaOwns);
							if ($fRow) {
								html = this._getHtmlForDataView(flatData.flatVisibleData, true);
								fRows = $(html).insertAfter($fRow);
								$fRow.attr("aria-owns", ariaOwns);
							}
							this._trigger("_buildDOMOnToggle", this, { fRows: fRows, ufRows: ufRows });
							}
						}
				} else {
					this._toggleRowSuccessors($row);
				}
			} else {
				if (this._rowVirtualizationEnabled() && this.options.virtualizationMode === "continuous") {
					$scrollContainer = this._scrollContainer();
					if ($row.length) {
						/* dataIdx = $row.attr("data-row-idx"); */
						$firstRow = $row.closest("tbody").children("tr[data-row-idx]:first");
						/* M.H. 2 Jun 2016 Fix for bug 220081: Treegrid with remote load on demand and virtualization renders empty chunk when rapidly expanding rows */
						if (!$firstRow.length) {
							return;
						}
						dataIdx = $firstRow.attr("data-row-idx");
						offsetTop = $row.offset().top;
						this._renderVirtualRecordsContinuous(parseInt(dataIdx, 10));
						/* this._avgRowHeight = null; */
						this._updateVirtualScrollContainer();
						/* M.H. 25 Feb 2015 Fix for bug #189482: After clicking the expanding button no cells can be selected in Chrome. */
						this._trigger("virtualrecordsrender", null, { owner: this, dom: this._virtualDom });
						if (!expand) {
							$curRow = $("#" + this.id() + " > tbody > tr[data-row-idx=" +
								$row.attr("data-row-idx") + "]");
							/* if you try to collapse some of the last rows diff will be not 0 */
							diff = ($curRow.offset().top - offsetTop);
							if (Math.abs(diff) > 1) {
								callAsync = true;
								setTimeout(function () {
									$scrollContainer[ 0 ].scrollTop = $scrollContainer.scrollTop() + diff;
								}, 50);
							}
						}
					} else {
						callAsync = true;
						this._updateVirtualScrollContainer();
						/* this._onVirtualVerticalScroll(); */
					}
				}
			}
			if (callAsync) {
				setTimeout(function () {
					$this._callDataToggledEventsAndCallbacks(customCallback, expand, eArgs, triggerEvents);
				}, 55);
			} else {
				this._callDataToggledEventsAndCallbacks(customCallback, expand, eArgs, triggerEvents);
			}
		},
		/* M.H. 8 Apr 2016 Fix for bug 217501: If the height of the rows of the next chunk is bigger than the previous, you scroll the grid with virtualScrollTo method and collapse a row scrolling the grid throws an error. */
		_avgRowHeightChanged: function (/*e, args*/) {
			var top = this._getScrollContainerScrollTop(),
				h = this._getScrollContainerHeight();
			/* changing avgRowHeight - causes changing height of virtual container - it should be reset scrollTop position of virtual container in this case */
			this._updateVirtualScrollContainer();
			this._correctVirtVertScrollTop(top, h);
		},
		_callDataToggledEventsAndCallbacks: function (customCallback, expand, eArgs, triggerEvents) {
			var hasHeight = this.options.height && parseInt(this.options.height, 10) > 0,
				isVirt = this.options.virtualization === true ||
					this.options.columnVirtualization === true ||
					this.options.rowVirtualization === true;
			if (customCallback) {
				$.ig.util.invokeCallback(customCallback, [
					this,
					{
						unfixedRow: eArgs.row,
						fixedRow: eArgs.fixedRow
					},
					eArgs.dataRecord,
					expand ]);
			}
			/* M.H. 8 Sep 2015 Fix for bug 206038: Headers and cells become misaligned when expanding a tree grid row causes a scrollbar to show
			when virtualization is enabled vertical scrollbar is always rendered,
			if height is not set vertical scrollbar is not rendered */
			if (hasHeight && !isVirt &&
				this._hasVerticalScrollbar !== this.hasVerticalScrollbar()) {
				/* calling _adjustLastColumnWidth updates padding of the right cell AND updates value of this._hasVerticalScrollbar */
				this._adjustLastColumnWidth();
			}
			if (expand) {
				this._fireInternalEvent("_rowExpanded", eArgs);
			} else {
				this._fireInternalEvent("_rowCollapsed", eArgs);
			}
			if (triggerEvents) {
				if (expand) {
					this._trigger(this.events.rowExpanded, null, eArgs);
				} else {
					this._trigger(this.events.rowCollapsed, null, eArgs);
				}
			}
		},
		toggleRow: function (row, callback) {
			/* Toggle row by specified row or row identifier
				paramType="object" jQuery table row object or a row id.
				paramType="function" optional="true" Specifies a custom function to be called when row is expanded/collapsed. The callback has 4 arguments- a reference to the current context(this), object that holds 2 properties(unfixedRow - DOM representation of the unfixed row, fixedRow - DOM representation of the fixed row, if there is no fixed columns it is undefined), reference to the dataRecord, expand - specifies whether row is expanded
			*/
			var $row, state, expand, ds = this.dataSource;

			if (row instanceof jQuery) {
				$row = row;
			} else {
				$row = this.element.find("tr[data-id=" + row + "]");
			}

			state = $row.attr("aria-expanded");
			if (!$row.length) {
				expand = !ds.getExpandStateById(row);
			} else {
				expand = (state === "false");
			}
			this._expandCollapseRow(row, expand, false, callback);
		},
		expandRow: function (row, callback) {
			/* Expands a parent row by specified row or row identifier
				paramType="object" jQuery table row object or a row id.
				paramType="function" optional="true" Specifies a custom function to be called when row is expanded/collapsed. The callback has 4 arguments- a reference to the current context(this), object that holds 2 properties(unfixedRow - DOM representation of the unfixed row, fixedRow - DOM representation of the fixed row, if there is no fixed columns it is undefined), reference to the dataRecord, expand - specifies whether row is expanded
			*/
			this._expandCollapseRow(row, true, false, callback);
		},
		collapseRow: function (row, callback) {
			/* Collapses a parent row by specified row or row identifier
				paramType="object" jQuery table row object, raw DOM row object or a row id.
				paramType="function" optional="true" Specifies a custom function to be called when row is expanded/collapsed. The callback has 4 arguments- a reference to the current context(this), object that holds 2 properties(unfixedRow - DOM representation of the unfixed row, fixedRow - DOM representation of the fixed row, if there is no fixed columns it is undefined), reference to the dataRecord, expand - specifies whether row is expanded
			*/
			this._expandCollapseRow(row, false, false, callback);
		},
		_onMouseUpExpander: function (event) {
		/* M.H. 30 May 2016 Fix for bug 219952: Expanding action is not consistent when there is selection and it applies activation to the expansion indicator cell when there is virtualization */
			var $et = $(event.target);
			if ($et.attr("data-expandcell-indicator") !== undefined) {
				$et.find("[data-expand-button]").focus();
				}
			this._toggleRow($et.closest("tr"));
		},
		_onKeyDownExpander: function (event) {
			if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
				this._toggleRow($(event.target).closest("tr"));
				event.preventDefault();
				event.stopPropagation();
			}
		},
		_toggleRow: function ($row) {
			if (!$row.length) {
				return;
			}
			var state = $row.attr("aria-expanded"), expand;
			if (state === undefined) {
				return;
			}
			expand = (state === "false" || state === "0");
			this._expandCollapseRow($row, expand, true);
		},
		_expandCollapseRow: function (row, expand, triggerEvents, callback) {
			/* expand or collapse row
				When virtualization is enabled it is possible $row to be with length 0 but rowId to be set - in case we want to toggle a row that is not in rendered chunks - in this case we should update the expand state in the datasource(for this data record) and render virtual rows
				paramType="object" jquery jQuery table row object that should be collapsed/expanded
				paramType="bool" specify whether the row should be expanded/collapsed. If true the row should be expanded. If it is already expanded it should return no result
				paramType="bool" optional="true" If true client events should be fired
				paramType="function" optional="true" If specified it is called once the row is expanded/collapsed
			*/
			var primaryKeyCol, noCancel = true, func, args, callbackArgs, rowId, $row, me = this,
				rows, $fRow, $ufRow, isExpanded = expand;
			if (row instanceof jQuery) {
				$row = row;
				if (!_aNull(this.options.primaryKey)) {
					primaryKeyCol = this.columnByKey(this.options.primaryKey);
					rowId = $row.attr("data-id");
					if (primaryKeyCol &&
							(primaryKeyCol.dataType === "number" || primaryKeyCol.dataType === "numeric")) {
						rowId = parseInt(rowId, 10);
					}
				} else {
					rowId = $row.index();
				}
			} else {
				rowId = row;
				$row = this.element.find("tr[data-id=" + row + "]");
			}
			/* we should not allow to apply expand/collapse for a row that is already expanded/collapsed */
			if ($row.length === 1 && $row.attr("aria-expanded") === isExpanded.toString()) {
				return;
			}
			func = $.proxy(this._onDataRecordToggled, this);
			callbackArgs = {
				callback: func,
				args: {
					row: $row,
					triggerEvents: triggerEvents
				}
			};
			if (callback) {
				callbackArgs.args.customCallback = callback;
			}
			rows = this._getRows($row);
			$fRow = rows.fixedRow;
			$ufRow = rows.unfixedRow;
			args = {
				owner: this,
				row: $ufRow,
				fixedRow: $fRow,
				dataLevel: parseInt($row.attr("aria-level"), 10)
			};
			if (triggerEvents) {
				if (expand) {
					noCancel = this._trigger(this.events.rowExpanding, null, args);
				} else {
					noCancel = this._trigger(this.events.rowCollapsing, null, args);
				}
			}
			if (noCancel) {
				/* M.K.2/19/2015 Fix for bug 189178: Loading indicator does not show when loading data on demand */
				this._loadingIndicator.show();
				setTimeout(function () {
					/*if (!_aNull(me.options.primaryKey)) {
					D.K. Fix for bug #205275 - Losing focus when trying to expand/collapse using keyboard navigation when using paging */
					var $tr, $ae = $(document.activeElement),
						$cell = $ae.closest("td"),
						cellInd = $cell.index();
						me.dataSource.setExpandedStateByPrimaryKey(rowId, expand, callbackArgs);
					if (me._rerenderGridRowsOnToggle()) {
						$tr = me.element.find("[data-id=\"" + rowId + "\"]");
						if ($tr.length) {
							if ($ae.is("tr")) {
								$ae = $tr;
								$tr.focus();
							}
							if ($ae.is("td")) {
								$ae = $tr.children("td:nth-child(" + (cellInd + 1) + ")");
								$ae.focus();
							} else if ($ae.attr("data-expand-button") !== undefined) {
								$ae = $tr.find("[data-expand-button]");
								$ae.focus();
							}
						}
					}
					/*} else {
						me.dataSource.setExpandedStateByRowIndex(rowId, expand, callbackArgs);
					} */
				}, 0);
			}
		},
		_toggleRowSuccessors: function ($row) {
			var $nextRow = $row, foundUpperLevel = false, dL, $fRow, $ufRow = $row,
				levelCollapsed = null, $container,
				dataBoundDepth = parseInt($row.attr("aria-level"), 10),
				isExpanded = $row.attr("aria-expanded"),
				styleDisplay = "",
				expanded = (isExpanded === "true");
			if (expanded) {
				styleDisplay = "none";
			}
			if (this.hasFixedColumns()) {
				if (this._isFixedElement($row)) {
					$fRow = $row;
					if (this._rowVirtualizationEnabled()) {
						$container = this._vdisplaycontainer();
					} else {
						$container = this.scrollContainer();
					}
					$ufRow = $container.find("tbody > tr").eq($row.index());
				} else {
					$fRow = this.fixedBodyContainer().find("tbody > tr").eq($row.index());
				}
			}
			/* update */
			while ($nextRow.length === 1 && !foundUpperLevel) {
				$nextRow = $nextRow.next("tr[aria-level]");
				dL = parseInt($nextRow.attr("aria-level"), 10);
				if (isNaN(dL) || dL <= dataBoundDepth) {
					foundUpperLevel = true;
					break;
				}
				/* we should show records but if some of the children is collapsed we should not show its children */
				if (!expanded) {
					if (levelCollapsed !== null) {
						if (dL <= levelCollapsed) {
							levelCollapsed = null;
						} else {
							/* we should not */
							continue;
						}
					}
					if ($nextRow.attr("aria-expanded") === "false") {
						levelCollapsed = dL;
					}
				}
				this._showHideRow($nextRow, styleDisplay);
			}
			if (expanded) {
				/* we should hide next rows */
				$ufRow.add($fRow)
					.attr("data-populated", "1")
					.attr("aria-expanded", false).find("[data-expand-button]")
					.attr("title", this.options.expandTooltipText)
					.removeClass(this.css.expandCellExpanded).addClass(this.css.expandCellCollapsed);
			} else {
				$ufRow.add($fRow).attr("aria-expanded", true).find("[data-expand-button]")
					.attr("title", this.options.collapseTooltipText)
					.removeClass(this.css.expandCellCollapsed).addClass(this.css.expandCellExpanded);
			}
		},
		_showHideRow: function ($row, styleDisplay) {
			var rows, $ufRow = $row, $fRow,
				fixedColumns = this.hasFixedColumns();
			if (fixedColumns) {
				rows = this._getRows($row);
				$fRow = rows.fixedRow;
				$ufRow = rows.unfixedRow;
			}
			$ufRow.css("display", styleDisplay);
			if ($fRow) {
				$fRow.css("display", styleDisplay);
			}
			if (styleDisplay === "") {
				this._trigger("_rowShown", this, { fRow: $fRow, ufRow: $ufRow });
			} else {
				this._trigger("_rowHidden", this, { fRow: $fRow, ufRow: $ufRow });
			}
		},
		_renderExtraHeaderCells: function (row, colgroup, prepend) {
			if (!this.options.renderExpansionIndicatorColumn) {
				return;
			}
			/* its how many grouped columns we have, the cell is only one
			also mark the extra cell with a data-skip attribute so that features can do their calculations based on this */
			if (prepend === true) {
				$("<td></td>").prependTo(row).css("border-width", 0).attr("data-skip", true);
				if (colgroup) {
					$("<col />").prependTo(colgroup).attr("data-skip", true)
						.css("width", this.options.indentation);
				}
			} else {
				$("<td></td>").appendTo(row).css("border-width", 0).attr("data-skip", true);
				if (colgroup) {
					$("<col />").appendTo(colgroup).attr("data-skip", true)
						.css("width", this.options.indentation);
				}
			}
		},
		/* integration with ColumnFixing */
		_isFixedNonDataColumnsOnly: function () {
			/* returns whether ONLY non data columns are fixed */
			if (this.hasFixedColumns() &&
					(!this._fixedColumns || !this._fixedColumns.length)) {
				return true;
			}
			return false;
		},
		_isDataContainerOnTheLeft: function (isFixed) {
			/* when there is at least one fixed column - rendering of non-data column and expansion indicators should be rendered only on the left container */
			var fdLeft = (this.fixingDirection() === "left");
			if (!this.hasFixedColumns()) {
				return true;
			}
			/* in case data-skip column is fixed */
			if ($.type(this._fixedColumns) === "array" &&
					!this._fixedColumns.length) {
				return true;
			}
			if (isFixed) {
				return fdLeft;
			}
			return !fdLeft;
		},
		_getRows: function ($row) {
			/* if $row is fixed gets the unfixed row() */
			var index, $fRow, $ufRow = $row;
			if (this.hasFixedColumns()) {
				index = $row.index();
				if (this._isFixedElement($row)) {
					$fRow = $row;
					$ufRow = $(this.rowAt(index));
				} else {
					$fRow = $(this.fixedRowAt(index));
				}
			}
			return { fixedRow: $fRow, unfixedRow: $ufRow };
		},
		/* //integration with ColumnFixing */
		_renderRow: function (rec, tr) {
			var funcCallbak;
			funcCallbak = function (rec, tr) {
				return $.ui.igGrid.prototype._renderRow.call(this, rec, tr);
			};
			return this._persistExpansionIndicator(rec, tr, funcCallbak, this);
		},
		renderNewChild: function (rec, parentId) {
			/* Adds a new row (TR) to the grid as a child of a specific row, by taking a data row object. Assumes the record will have the primary key.
				paramType="object" The data row JavaScript object.
				paramType="string" optional="true" Identifier/key of the targeted parent row. If missing, then the new row is rendered to the bottom of the grid.
			*/
			var tbody = this.element.children("tbody"),
				tbodyFixed = this.fixedBodyContainer().find("tbody"),
				virt = this.options.virtualization === true || this.options.rowVirtualization === true,
				prevRow,
				dlprop = this.options.dataSourceSettings.propertyDataLevel,
				dl,
				parent,
				rowData,
				fixing = this.hasFixedColumns(),
				unfixedRow,
				fixedRow,
				dataId,
				index = this._recordIndexInFlatView(rec[ this.options.primaryKey ]);
			if (parentId === undefined || parentId === null) {
				this.renderNewRow(rec);
			} else {
				if (virt) {
					this._renderVirtualRecordsContinuous();
					this._startRowIndex = 0;
					this.virtualScrollTo(index);
				} else {
					parent = this.dataSource.findRecordByKey(parentId);
					if (parent === null) {
						/* P.Zh. 22 Feb 2016 Fix for bug #214523: renderNewChild does not work correctly when you call it for rendering a subchild */
						throw new Error($.ig.Grid.locale.recordNotFound.replace("{id}", parentId));
					}
					dl = parent[ dlprop ] + 1;
					prevRow = this.rowById(parentId);
					while (prevRow.nextAll(":not(.ui-iggrid-addrow)").first().length > 0) {
						if (parseInt(prevRow.nextAll(":not(.ui-iggrid-addrow)")
							.first().attr("aria-level"), 10) >= dl) {
							prevRow = prevRow.nextAll(":not(.ui-iggrid-addrow)").first();
						} else {
							break;
						}
					}
					prevRow = prevRow ? prevRow : this.rowById(parentId);
					rowData = {
						dataBoundDepth: dl
					};
					dataId = prevRow.attr("data-id");
					unfixedRow = this._renderRecordInternal(rec, prevRow.index() + 1, false, rowData);
					if (fixing) {
						fixedRow = this._renderRecordInternal(rec, prevRow.index() + 1, true, rowData);
					}
					if (prevRow.length > 0) {
						$(prevRow).after(unfixedRow);
						if (fixing) {
							tbodyFixed.find("tr[data-id=" + dataId + "]").after(fixedRow);
						}
					} else {
						tbody.append(unfixedRow);
						if (fixing) {
							tbodyFixed.append(fixedRow);
						}
					}
				}
			}
		},
		_recordIndexInFlatView: function (rowId) {
			var dv = this.dataSource.flatDataView(),
				pk = this.options.primaryKey,
				index;
			for (var i = 0; i < dv.length; i++) {
				if (dv[ i ][ pk ] === rowId) {
					index = i;
					break;
				}
			}
			return index;
		},
		_persistExpansionIndicator: function (rec, tr, funcCallback, funcCallee) {
			var $td, trRes = tr, $tr = $(tr), $span,
				renderEC = this.options.renderExpansionIndicatorColumn;
			if (renderEC) {
				$span = $tr.find("span[data-shift-container]");
				$td = $span.closest("td");
			} else {
				$span = $tr.find("[data-expandcell-indicator]");
				$td = $span.closest("td");
				$span.detach();
			}
			if (funcCallback && funcCallee) {
				trRes = funcCallback.call(funcCallee, rec, tr);
				$tr = $(trRes);
			}
			if ($span.length > 0) {
				$span.prependTo($td);
			}
			return trRes;
		},
		_detachEvents: function () {
			if (this._columnsGeneratedHandler) {
				this.element.unbind("igtreegrid_columnsgenerated", this._columnsGeneratedHandler);
			}
			if (this._containersRenderedHandler) {
				this.element.unbind("iggrid_gridcontainersrendered", this._containersRenderedHandler);
			}
			/* M.H. 8 Apr 2016 Fix for bug 217501: If the height of the rows of the next chunk is bigger than the previous, you scroll the grid with virtualScrollTo method and collapse a row scrolling the grid throws an error. */
			if (this._avgRowHeightChangedHandler) {
				this.element.unbind("iggridavgrowheightchanged", this._avgRowHeightChangedHandler);
			}
			$.ui.igGrid.prototype._detachEvents.apply(this, arguments);
		},
		_attachEvents: function () {
			/* column fixing events */
			/* M.H. 24 Jan 2015 Fix for bug #187687: When columns are auto generated extra columns are rendered in treegrid */
			this._columnsGeneratedHandler = $.proxy(this._columnsGenerated, this);
			this.element.bind("igtreegrid_columnsgenerated", this._columnsGeneratedHandler);

			this._containersRenderedHandler = $.proxy(this._containersRendered, this);
			this.element.bind("iggrid_gridcontainersrendered", this._containersRenderedHandler);
			/* M.H. 8 Apr 2016 Fix for bug 217501: If the height of the rows of the next chunk is bigger than the previous, you scroll the grid with virtualScrollTo method and collapse a row scrolling the grid throws an error. */
			if (this.options.virtualization || this.options.rowVirtualization) {
				this._avgRowHeightChangedHandler = $.proxy(this._avgRowHeightChanged, this);
				this.element.bind("iggridavgrowheightchanged", this._avgRowHeightChangedHandler);
			}
		},
		_initFeature: function (featureObject) {
			if (!featureObject) {
				return;
			}
			/* construct widget name */
			if (featureObject.name === undefined) {
				return;
			}
			var widgetTreeGrid = "igTreeGrid" + featureObject.name;
			/* check whether there is igTreeGrid version of the feature */
			if ($.type(this.element[ widgetTreeGrid ]) === "function") {
				if (this.element.data(widgetTreeGrid)) {
					this.element[ widgetTreeGrid ]("destroy");
				}
				/* instantiate widget */
				this.element[ widgetTreeGrid ](featureObject);
				this.element.data(widgetTreeGrid)._injectGrid(this);
			} else {
				return $.ui.igGrid.prototype._initFeature.apply(this, arguments);
			}
		},
		_destroyFeatures: function () {
			var i, features = this.options.features, e = this.element;
			for (i = 0; i < features.length; i++) {
				/* Don't call destroy on non existing widget (or already destroyed one) */
				if (e.data("igTreeGrid" + features[ i ].name)) {
					e[ "igTreeGrid" + features[ i ].name ]("destroy");
				} else if (e.data("igGrid" + features[ i ].name)) {
					e[ "igGrid" + features[ i ].name ]("destroy");
				}
			}
		},
		destroy: function () {
			/* Destroys igTreeGrid
			returnType="object" Returns reference to this igTreeGrid.
			*/
			this._detachEvents();
			$.ui.igGrid.prototype.destroy.apply(this, arguments);
			this.element.removeData($.ui.igGrid.prototype.widgetName);
			this._removeOverridenFunction();
			return this;
		}
	});
	$.extend($.ui.igTreeGrid, { version: "16.1.20161.2145" });
}(jQuery));



