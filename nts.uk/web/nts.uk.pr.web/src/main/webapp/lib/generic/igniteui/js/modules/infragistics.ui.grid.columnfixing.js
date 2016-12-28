/*!@license
 * Infragistics.Web.ClientUI Grid Multi Headers 16.1.20161.2145
 *
 * Copyright (c) 2011-2016 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 *	jquery-1.9.1.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	infragistics.ui.grid.framework.js
 *	infragistics.ui.shared.js
 *	infragistics.dataSource.js
 *	infragistics.util.js
 */

/*global jQuery, MSApp */
/*jshint -W018 */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {
	var _aNull = function (val) { return val === null || val === undefined; };

	$.widget("ui.igGridColumnFixing", {
		/*
		igGridColumnFixing widget
		The widget is pluggable to the element where the grid is instantiated and the actual igGrid object doesn't know about it.
		*/
		/* property showing whether it should be rendered in feature chooser */
		renderInFeatureChooser: true,
		events: {
			/* cancel="true" Event which is fired when column fixing operation is initiated
			Function takes arguments evt and args.
			use args.columnIdentifier to get columnKey or columnIndex
			use args.isGroupHeader to get whether header cell has children(use this argument ONLY when multi-column-headers are enabled)
			use args.owner to get a reference to the widget
			*/
			columnFixing: "columnFixing",
			/* Event which is fired when column fixing operation is finished
			Function takes arguments evt and args.
			use args.columnIdentifier to get columnKey or columnIndex
			use args.isGroupHeader to get whether header cell has children(use this argument ONLY when multi-column-headers are enabled)
			use args.owner to get a reference to the widget
			*/
			columnFixed: "columnFixed",
			/* cancel="true" Event which is fired when column unfixing operation is initiated
			Function takes arguments evt and args.
			use args.columnIdentifier to get columnKey or columnIndex
			use args.isGroupHeader to get whether header cell has children(use this argument ONLY when multi-column-headers are enabled)
			use args.owner to get a reference to the widget
			*/
			columnUnfixing: "columnUnfixing",
			/* Event which is fired when column unfixing operation is done
			Function takes arguments evt and args.
			use args.columnIdentifier to get columnKey or columnIndex
			use args.isGroupHeader to get whether header cell has children(use this argument ONLY when multi-column-headers are enabled)
			use args.owner to get a reference to the widget
			*/
			columnUnfixed: "columnUnfixed",
			/* Event which is fired when column fixing operation has failed - e.g. sum of the width of the fixed columns container and width of the column to be fixed exceeds the grid width
			Function takes arguments evt and args.
			use args.columnIdentifier to get columnKey or columnIndex
			use args.isGroupHeader to get whether header cell has children(use this argument ONLY when multi-column-headers are enabled)
			use args.errorMessage to get error message describing the reason fixing has failed
			use args.owner to get a reference to the grid widget
			*/
			columnFixingRefused: "columnFixingRefused",
			/* Event which is fired when column unfixing operation has failed - e.g.: there is only one fixed visible column(and tries to unfix it) and at least one fixed hidden column
			Function takes arguments evt and args.
			use args.columnIdentifier to get columnKey or columnIndex
			use args.isGroupHeader to get whether header cell has children(use this argument ONLY when multi-column-headers are enabled)
			use args.errorMessage to get error message describing the reason unfixing has failed
			use args.owner to get a reference to the grid widget
			*/
			columnUnfixingRefused: "columnUnfixingRefused"
		},
		css: {
			/* Classes applied to the main fixed container */
			fixedContainer: "ui-iggrid-fixedcontainer",
			/* classes applied to the container div of header button(which holds button for fixing/unfixing) */
			headerButtonIconContainer: "ui-iggrid-fixcolumn-headerbuttoncontainer",
			/* Classes applied to the left side container */
			leftFixedContainer: "ui-iggrid-fixedcontainer-left",
			/* Classes applied right side fixed container */
			rightFixedContainer: "ui-iggrid-fixedcontainer-right",
			/* Classes applied to header cell button for fixing column */
			headerButtonIcon: "ui-icon ui-corner-all ui-icon-pin-w",
			/* Classes applied to header cell button for fixing column */
			headerButtonIconHover: "",//ui-state-hover
			/* Classes applied to header cell button for unfixing column */
			headerButtonUnfixIcon: "ui-icon ui-corner-all ui-icon-pin-s",
			/* Classes applied in feature chooser icon when column is not fixed */
			featureChooserIconClassFixed: "ui-icon ui-iggrid-icon-unfix",
			/* Classes applied in feature chooser icon when column is fixed */
			featureChooserIconClassUnfixed: "ui-icon ui-iggrid-icon-fix",
			/* Classes applied in unfixed table when fixing direction is left */
			unfixedTableLeft: "ui-iggrid-unfixed-table-left",
			/* Classes applied in unfixed table when fixing direction is right */
			unfixedTableRight: "ui-iggrid-unfixed-table-right"
		},
		internalErrors: {
			none: $.ig.ColumnFixing.locale.internalErrors.none,
			notValidIdentifier: $.ig.ColumnFixing.locale.internalErrors.notValidIdentifier,
			fixingRefused: $.ig.ColumnFixing.locale.internalErrors.fixingRefused,
			fixingRefusedMinVisibleAreaWidth:
				$.ig.ColumnFixing.locale.internalErrors.fixingRefusedMinVisibleAreaWidth,
			alreadyHidden: $.ig.ColumnFixing.locale.internalErrors.alreadyHidden,
			alreadyUnfixed: $.ig.ColumnFixing.locale.internalErrors.alreadyUnfixed,
			alreadyFixed: $.ig.ColumnFixing.locale.internalErrors.alreadyFixed,
			unfixingRefused: $.ig.ColumnFixing.locale.internalErrors.unfixingRefused,
			targetNotFound: $.ig.ColumnFixing.locale.internalErrors.targetNotFound
		},
		options: {
			/* type="string" Specifies altering text on column fixing header icon when column is not fixed */
			headerFixButtonText: $.ig.ColumnFixing.locale.headerFixButtonText,
			/* type="string" Specifies altering text on column fixing header icon when column is fixed */
			headerUnfixButtonText: $.ig.ColumnFixing.locale.headerUnfixButtonText,
			/* type="bool" option to show column fixing buttons in header cells/feature chooser */
			showFixButtons: true,
			/* type="bool" option enable syncing heights of rows between fixed/unfixed rows */
			syncRowHeights: true,
			/* type="number" option to configure scroll delta when scrolling with mouse wheel or keyboard in fixed columns area*/
			scrollDelta: 40,
			/* type="left|right" configure on which side to render fixed area
				left type="string" fixed column are rendered on the left side of the main grid.
				right type="string" fixed column are rendered on the right side of the main grid.
			*/
			fixingDirection: "left",
			/* type="array" a list of column settings that specifies custom column fixing options on a per column basis */
			columnSettings: [
				{
					/* type="string" Specifies column key. Either key or index must be set in every column setting.*/
					columnKey: null,
					/* type="number" Specifies column index. Either key or index must be set in every column setting.*/
					columnIndex: null,
					/* type="bool" Specifies whether the column allows to be fixed or not.*/
					allowFixing: true,
					/* type="bool" Specifies whether the column to be fixed or not.*/
					isFixed: false
				}
			],
			/* type="string" Feature chooser text of the fixed column*/
			featureChooserTextFixedColumn: $.ig.ColumnFixing.locale.featureChooserTextFixedColumn,
			/* type="string" Feature chooser text of the unfixed column*/
			featureChooserTextUnfixedColumn: $.ig.ColumnFixing.locale.featureChooserTextUnfixedColumn,
			/* type="string|number" minimal visible area for unfixed columns. For instance if you fix a column(or columns) and the width of the fixed columns is such that the width of visible are of unfixed columns is less than this option then fixing will be canceled
				string The width can be set in pixels (px) and percentage (%).
				number The width can be set as a number.
			*/
			minimalVisibleAreaWidth: 30,
			/* type="bool" Specify initial fixing of non data columns(like specific rowSelectors columns on the left side of the grid) when fixingDirection is left*/
			fixNondataColumns: true,
			/* type="bool" If true then on column fixing when creating table rows all row attributes for the unfixed rows will be set in fixed rows too. Because of performance issue you can set this option to false */
			populateDataRowsAttributes: true
		},
		/* value to check when grid height is not set and fixed and unfixed area has different heights. When difference is more than this value then append blank div to fixed area so to compensate horizontal scrollbar */
		scrollContainerCheckValue: 2,
		_createWidget: function () {
			/* feature chooser data */
			this._fcData = {};
			this._tds = {};
			this._containers = {};
			this._colgroups = {};
			this._isInitFC = false;
			this._isFunctionsOverriden = false;
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		_unfixColumnInternal: function (colKey, target, after) {
			var noCancel, res = { error: true }, grid = this.grid,
				isGroupHeader = this.isGroupHeader(colKey);
			noCancel = this._trigger(this.events.columnUnfixing, null, {
				/*'columnIndex' : colInd, */
				"columnIdentifier": colKey,
				"isGroupHeader": isGroupHeader,
				owner: grid
			});
			if (noCancel) {
				res = this.unfixColumn(colKey, target, after);
				if (res.result === false) {
					this._trigger(this.events.columnUnfixingRefused, null, {
						"columnIdentifier": colKey,
						"isGroupHeader": isGroupHeader,
						"errorMessage": res.error,
						owner: grid
					});
				} else {
					this._trigger(this.events.columnUnfixed, null, {
						"columnIdentifier": colKey,
						"isGroupHeader": isGroupHeader,
						owner: grid
					});
				}
			}
			return res;
		},
		unfixColumn: function (colIdentifier, target, after) {
			/* Unfix column by specified column identifier - column key or column index
			paramType="number|string" An identifier of the column to be unfixed - column index or column key.
			paramType="string" optional="true" Key of the column where the unfixed column should move to.
			paramType="bool" optional="true" Specifies where the unfixed column should be moved after or before the target column. This parameter is disregarded if there is no target column specified.
			returnType="object" Object description: { result: indicates whether unfixing is successful, error: error message describing the reason unfixing has failed, col: reference to the column object(if column identifier is not valid - then its value is null) }
			*/
			if ($.type(arguments[ 1 ]) === "boolean") {// in previous version 2nd argument was isGroupHeader, 3rd - target, 4th - after
				target = arguments[ 2 ];
				after = arguments[ 3 ];
						}
			var col, res, children, isGroupHeader, colKey, mch,
				grid = this.grid, cols, aObj;
			isGroupHeader = this.isGroupHeader(colIdentifier);
			res = {
				error: this.internalErrors.none,
				result: true,
				col: null,
				isGroupHeader: isGroupHeader
			};
			/* get column object */
			if (!isGroupHeader) {
				col = this._getColById(colIdentifier);
				res.col = col;
				if (!col) {
					res.result = false;
					res.error = this.internalErrors.notValidIdentifier;
					return res;
				}
				res.col = col;
				colKey = col.key;
				children = [ col ];
				res.id = colKey;
			} else {
				colKey = colIdentifier;
				res.id = colKey;
				mch = grid._getMultiHeaderColumnById(colIdentifier);
				if (!mch) {
					res.result = false;
					res.error = this.internalErrors.notValidIdentifier;
					return res;
				}
				res.col = mch;
				children = mch.children;
			}
			res.children = children;
			res = this._couldPerformUnfixing(res, target);
			if (!res.result) {
					return res;
				}
			/* find target column - when unfixing column target should be specified */
			if (!target) {
				/* get unfixed columns */
				cols = grid._isMultiColumnGrid ? grid._oldCols : grid.options.columns;
				$.each(cols, function (i, c) {
					if (c.fixed !== true && !(!!c.hidden)) {
						target = c.key || c.identifier;
						return false;
					}
				});
				after = false;
			}
			this._performDomColumnMove(res, target, after, false);
			aObj = this._rearrangeColumns(colKey, target, after, false);
			/* remove non-data columns only if there are non-data cols in fixed area and option "fixNondataColumns" is false */
			grid._hasFixedColumns = !!grid._fixedColumns.length || grid.hasFixedDataSkippedColumns();
			this._refreshDOMOnUnfixing(res);
			grid._onFixedColumnsChanged({
				isToFix: false,
				id: colIdentifier,
				colWidth: res.colWidth,
				children: res.children,
				isGroupHeader: isGroupHeader,
				/* indexes used to move column objects */
				start: aObj.from.dIndex,
				length: aObj.from.children.length,
				at: aObj.at.dIndex
			});
			if (grid._fixedColumns.length === 0 &&
				(this._getDataSkippedColumnsLength(true) && !this.options.fixNondataColumns)) {
				this.unfixNonDataColumns();
			}
			return res;
		},
		checkAndSyncHeights: function () {
			/*Check whether heights of fixed and unfixed tables are equal - if not sync them. Similar check is made for heights of table rows.
			*/
			this.syncHeights(true);
		},
		syncHeights: function (check, clearRowsHeigths) {
			/*If argument 'check' is set to true  - check whether heights of fixed and unfixed tables are equal - if not sync them. Similar check is made for heights of table rows(if argument clearRowsHeigths is set to true clear rows heights before syncing them).
			paramType="bool" optional="true" if set to true - check whether heights of fixed/unfixed tables are equal and if not - then sync heights. If this argument is set to false - check is not done - sync rows and table heights
			paramType="bool" optional="true" clear rows heigths for all visible rows
			*/
			if (!this.grid.hasFixedColumns()) {
				return;
			}
			if (!this._containers || !this._containers.body) {
				this._populateContainers();
			}
			var c = this._containers,
				hTbl = c.header || c.body,
				fTbl = c.footer || c.body;
			this._checkAndSyncHeightsForTables(hTbl.fixedTable, hTbl.unfixedTable, "thead",
												check, clearRowsHeigths);
			this._checkAndSyncHeightsForTables(c.body.fixedTable, c.body.unfixedTable, "tbody",
												check, clearRowsHeigths);
			this._checkAndSyncHeightsForTables(fTbl.fixedTable, fTbl.unfixedTable, "tfoot",
												check, clearRowsHeigths);
		},
		_checkAndSyncHeightsForTables: function ($fTbl, $ufTbl, selector, check, clearRowsHeiths) {
			/* check whether height of fixed and unfixed tables are equal and also whether offset of last <TR> of fixed and unfixed tables are equal - if not sync rows heights of fixed and unfixed table */
			/* use selector - to get THEAD/TBODY/TFOOT and its child TRs - performance optimization */
			selector = selector || "tbody";
			var h, fH, ufH, uftblH,
				$fTrs = $fTbl.children(selector).children("tr"),
				$ufTrs = $ufTbl.children(selector).children("tr"),
				$fLastTr = $fTrs.last(),
				$ufLastTr = $ufTrs.last();
			uftblH = $ufTbl[ 0 ].style.height;
			$fTbl.height("");
			$ufTbl.height("");
			if (clearRowsHeiths) {
				this._removeRowsHeights($fTrs, true);
				this._removeRowsHeights($ufTrs, true);
			}
			if (!check || ($fLastTr.length > 0 &&
				(Math.abs($fTbl.outerHeight() - $ufTbl.outerHeight()) > 1 ||
				Math.abs($fLastTr.offset().top - $ufLastTr.offset().top) > 1))) {
				this.syncRowsHeights($fTrs, $ufTrs);
				fH = $fTbl.height();
				ufH = $ufTbl.height();
				h = ufH;
				if (fH > ufH) {
					h = fH;
				}
				$fTbl.height(h);
				$ufTbl.height(h);
			} else if (uftblH) {
				$ufTbl[ 0 ].style.height = uftblH;
				$fTbl[ 0 ].style.height = uftblH;
			}
		},
		_syncTableHeights: function () {
			$("#" + this.grid.id() + "_fixed").height(this.grid.element.height());
		},
		_getKeyByVisibleIndex: function (index, isFixed) {
			var col;
			isFixed = !!isFixed;
			index -= this._getDataSkippedColumnsLength(isFixed);
			col = this.grid._visibleColumns(isFixed)[ index ];
			return col ? col.key : undefined;
		},
		_setOption: function (key, value) {
			if (value === this.options[ key ]) {
				return;
			}
			$.Widget.prototype._setOption.apply(this, arguments);
			if (key === "minimalVisibleAreaWidth") {
				this.grid._visibleAreaWidth(value);
			}
		},
		_gridSetOption: function (key, value) {
			/* check whether value is properly set when there are fixed columns - e.g. setting width of the grid - check whether the new value is OK(as checking option minimalVisibleAreaWidth) */
			var w = value, grid = this.grid, fcw/*fixed container width*/;
			if (!grid.hasFixedColumns()) {
				return;
			}
			if (key === "width") {
				if (typeof value === "string" && value.indexOf("%") > 0) {
					grid.container().css("width", value);
					w = grid.container().outerWidth();
				} else {
					w = parseInt(value, 10);
				}
				fcw = grid.fixedContainer().outerWidth();
				if (fcw === null) {
					fcw = grid.fixedBodyContainer().outerWidth();
				}
				if (w - fcw < parseInt(this.options.minimalVisibleAreaWidth, 10)) {
					throw new Error($.ig.ColumnFixing.locale.setOptionGridWidthException);
				}
			}
		},
		_gridWidthChanged: function () {
			/*called from _fireInternalEvent - called when grid width is changed via _setOption */
			/* M.H. 7 Jun 2016 Fix for bug 220337: Resizing grid in pixels causes white line to appear in the unfixed area when fixing direction is right and virtualization is enabled */
			var grid = this.grid;
			if (!grid.hasFixedColumns()) {
				return;
			}
			if (this._isVirtualGrid()) {
				this._recalcTableWidths();
				if (this.options.fixingDirection === "right") {
					this._adjustVirtWidthOnFixingRight(false);
				}
			}
		},
		/* M.H. 21 Aug 2013 Fix for bug #149676: When row selectors are enabled and you fix and unfix a column and then fix a column whose data is spanned on two rows the fixed and unfixed areas are misaligned. */
		isGroupHeader: function (colKey) {
			if (this.grid._isMultiColumnGrid && !this.grid.columnByKey(colKey)) {
				return true;
						}
			return false;
		},
		_fixColumnInternal: function (colKey, target, after) {
			var noCancel, res = { error: true }, grid = this.grid,
				isGroupHeader = this.isGroupHeader(colKey);
			noCancel = this._trigger(this.events.columnFixing, null, {
				/*'columnIndex' : colInd, */
				"columnIdentifier": colKey,
				"isGroupHeader": isGroupHeader,
				owner: grid
					});
			if (noCancel) {
				res = this.fixColumn(colKey, target, after);
				if (res.error === this.internalErrors.fixingRefused || res.result === false) {
					this._trigger(this.events.columnFixingRefused, null, {
						"columnIdentifier": colKey,
						"isGroupHeader": isGroupHeader,
						"errorMessage": res.error,
						owner: grid
					});
				} else {
					this._trigger(this.events.columnFixed, null, {
						"columnIdentifier": colKey,
						"isGroupHeader": isGroupHeader,
						owner: grid
					});
					}
				}
			return res;
		},
		_isVirtualGrid: function () {
			var gridOptions = this.grid.options;
			return (gridOptions.virtualization === true || gridOptions.rowVirtualization === true);
		},
		_isContinuousVirtualization: function () {
			return this._isVirtualGrid() && this.grid.options.virtualizationMode === "continuous";
		},
		_getColById: function (colId) {
			if ($.type(colId) === "string") {
				return this.grid.columnByKey(colId);
					}
			return this.grid.options.columns[ colId ];
		},
		/******** Update containers width ********/
		_updateWidths: function (colWidth) {
			var $fArea, $ufArea, grid = this.grid,
				v = this._isVirtualGrid(),
				$fCntnr = this._mainFixedCntnr, w;
			/* M.H. 28 Apr 2016 Fix for bug 218722: Error saying fixedTable is undefined occurs when showHeader is false and ColumnFixing is enabled. */
			if (grid.options.fixedHeaders && grid.options.showHeader) {
				$fArea = this._containers.header.fixedTable;
				$ufArea = this._containers.header.unfixedTable;
				this._updateTblWidths($fArea, $ufArea, colWidth);
			}
			$fArea = this._containers.body.fixedTable;
			$ufArea = grid.element;
			/* M.H. 9 Jun 2016 Fix for bug 220512: Incorrect width of virtual grid in case of grid width is set in % and there is initially fixed column */
			if (this._isVirtualGrid() && grid._gridHasWidthInPercent()) {
				$ufArea.width("");
			}
			this._updateTblWidths($fArea, $ufArea, colWidth);
			if (grid.options.fixedFooters && this._containers.footer) {
				$fArea = this._containers.footer.fixedTable;
				$ufArea = this._containers.footer.unfixedTable;
				this._updateTblWidths($fArea, $ufArea, colWidth);
			}
			if (v) {
				this._updateVirtualDOMWidth(colWidth);
				return;
			}
			/* We use CSS border 'border-right/border-left' as border line(differentiate fixed and unfixed area). We use "box-sizing: border-box;" for mainFixedContainer so to be applied fixed container should have Width set */
			w = parseFloat($fCntnr[ 0 ].style.width || 0);
			$fCntnr[ 0 ].style.width = (w + colWidth) + "px";
		},
		_calcTblWidth: function ($tbl, delta) {
			var styleWidth = $tbl[ 0 ].style.width, w;
			if ($.type(styleWidth) === "string" && styleWidth.indexOf("%") > -1) {
				return styleWidth;
			}
			w = parseInt(styleWidth, 10) || 0;
			if (w === 0) {
				$tbl.find(">colgroup>col").each(function (ind, col) {
					w += parseInt(col.style.width, 10) || 0;
				});
				}
			return (w + delta) + "px";
		},
		_updateTblWidths: function ($fixedTbl, $unfixedTbl, colWidth) {
			var fW, ufW, fTbl = $fixedTbl[ 0 ], ufTbl = $unfixedTbl[ 0 ];
			ufW = this._calcTblWidth($unfixedTbl, -colWidth);
			fW = this._calcTblWidth($fixedTbl, colWidth);
			fTbl.style.width = fW;
			/* M.H. 9 May 2016 Fix for bug 218851: Hiding when having a fixed column, autofitLastColumn: false and the columns are smaller than the grid width autofits the headers */
			ufTbl.style.width = ufW;
			if (this._isVirtualGrid()) {
				$fixedTbl.closest("div")[ 0 ].style.width = fW;
			}
		},
		_updateVirtualDOMWidth: function (colWidth) {
			var grid = this.grid, w, $col, $cntnr,
				widthInPerc = grid._gridHasWidthInPercent(),
				$displCntnr = grid._vdisplaycontainer(),
				$virtTbl = grid._virtualcontainer(),// table that holds virtual containers - have 2 TDs - first is for tables, second for scrollbar;
				$virtColgroup = $virtTbl.children("colgroup");
			/* update col width for fixed container */
			$col = $virtColgroup.children("col[data-fixed-col]");
			w = parseInt($col.attr("width"), 10) || 0;
			$col.attr("width", w + colWidth + "px");
			/*NOTE: "_createVirtualGrid" in grid framework - when browser is IE - add 1px to the width of the virtual scrollbar container */
			/* we should remove/add this 1 px when fixing/unfixing. Otherwise containers will be misaligned(unfixed containers will position below fixed) */
			if (!widthInPerc) {
				/*if ($.ig.util.isIE) //$virtTbl.css('display', 'inline-block'); */
				$col = $virtColgroup.children("col:not([data-fixed-col])").first();
				w = parseInt($col.attr("width"), 10) || 0;
				$col.attr("width", (w - colWidth) + "px");
				if (grid.options.showHeader) {
					$cntnr = $("#" + grid.id() + "_headers_v");
					$cntnr.width($cntnr.width() - colWidth);
			}
				if (grid.options.showFooter) {
					$cntnr = grid._fixedfooters();
					$cntnr.width($cntnr.width() - colWidth);
			}
				/*$virtTbl.width(parseFloat($virtTbl[0].style.width) - colWidth); */
				$displCntnr.width($displCntnr.width() - colWidth);
			}
		},
		/******** //Update containers width ********/
		_recalcTableWidths: function () {
			var c = this._containers;
			if (c.header) {
				c.header.unfixedTable.width("");
			}
			if (c.footer) {
				c.footer.unfixedTable.width("");
			}
			/* M.H. 6 Jun 2016 Fix for bug 220191: When rowselectors are enabled and grid width is greater than sum of column widths and autofitLastColumn is true horizontal scrollbar is shown */
			this.grid.element.width("");
			this._updateWidths(0);
		},
		_swapElements: function (area) {
			var swap = area.from;
			area.from = area.to;
			area.to = swap;
			return area;
		},
		_analyzeFixingObjects: function (colObj, target) {
			var grid = this.grid, tObj,
				movedCol  = { start: -1, length: colObj.children.length },
				targetCol = { start: -1, length: 1 },
				vIndFrom, // visible index FROM - including data-skip(non-data) columns
				vIndTo;// visible index TO - including data-skip(non-data) columns;
			vIndFrom = this.grid.getVisibleIndexByKey(colObj.children[ 0 ].key, true);
			if ($.type(target) === "string") { // it is passed key
				tObj = grid._analyzeColumnByKey(target);
			}
			if (_aNull(target)) {
				vIndTo = -1;
			} else {
				targetCol.length = tObj.children.length;
				vIndTo = grid.getVisibleIndexByKey(tObj.children[ 0 ].key, true);
			}
			movedCol.start = vIndFrom;
			targetCol.start = vIndTo;
			return {
				movedCol: movedCol,
				targetCol: targetCol
			};
		},
		_performDomColumnMove: function (col, target, after, isToFix) {
			/* column - object with props {col: column object, visibleIndex: vInd, children: [array]} */
			/* targetCol - column Identifier of column near which should be fixed/unfixed column - NOTE- if isToFix is TRUE and targetCol is null fix at first position(fix for the first time) */
			var grid = this.grid, oCols, aObj, nW,
				afc = this._analyzeFixingObjects(col, target), // analyzed
				movedCol = afc.movedCol,
				targetCol = afc.targetCol;
			movedCol.width = col.colWidth;
			movedCol.id = col.id;
			if (grid._isMultiColumnGrid) {
				oCols = jQuery.extend(true, [], grid._oldCols);
				aObj = grid._analyzeColumnMovingObjects(col.id, target, after, isToFix);
				grid._rearrangeArray(oCols, // modified array
									aObj.from.dMCHIndex, // start
									1, // length
									aObj.at.dMCHIndex); // at
				movedCol.oCols = oCols;
				/* M.H. 1 Mar 2016 Fix for bug 215072: Hiding a column in MultiColumn Header and fixing this MultiColumn Header causes the grid to not render properly */
				if (movedCol.length > 1) {
					nW = 0;
					movedCol.length = $.grep(aObj.from.children, function (col) {
						var v = !col.hidden;
						nW += v ? parseFloat(col.width) : 0;
						return v;
					}).length;
					movedCol.width = nW;
				}
			}
			this._performDomColumnMoveByIndexes(movedCol, targetCol, after, isToFix);
		},
		_performDomColumnMoveByIndexes: function (movedCol,
										targetCol,
										after,
										isToFix) {
			/* column - object with props {col: column object, visibleIndex: vInd, children: [array]} */
			/* targetCol - column Identifier of column near which should be fixed/unfixed column - NOTE- if isToFix is TRUE and targetCol is null fix at first position(fix for the first time) */
			var grid = this.grid, area = {}, $fTbl, $ufTbl,
				w = isToFix ? movedCol.width : -movedCol.width;
			this._updateWidths(w);
			/** perform DOM move for THEAD elements **/
			$fTbl = grid.fixedHeadersTable();
			$ufTbl = grid.headersTable();
			area.from = $fTbl;
			area.to   = $ufTbl;
			if (isToFix) {
				area = this._swapElements(area);
				}
			if (grid.options.fixedHeaders === true) {
				grid._moveCols({
									from: area.from.children("colgroup"),
									to: area.to.children("colgroup"),
									ignoreDataSkip: true
		},
								movedCol, targetCol, after);
			}
			if (grid._isMultiColumnGrid) {
				if (!movedCol.nonData) {
					this._fixUnfixMCHColumn(movedCol.id,
										{
											fixedThead: $fTbl.children("thead"),
											unfixedThead: $ufTbl.children("thead"),
											isToFix: isToFix
										}, movedCol.oCols);
			} else {
					/* fix non-data column */
					grid._moveColumnInBodyFooter({
													trsFrom: area.from.find("thead").children("tr[data-mch-level=" + grid._maxLevel + "]"),
													trsTo: area.to.find("thead").children("tr[data-mch-level=" + grid._maxLevel + "]"),
													ignoreDataSkip: true
												},
												movedCol, targetCol, after);
			}
				}
			grid._moveColumnInBodyFooter({
											trsFrom: area.from.find("thead").children("tr:not([data-mch-level])"),
											trsTo: area.to.find("thead").children("tr:not([data-mch-level])"),
											ignoreDataSkip: true
		},
										movedCol, targetCol, after);
			/** // perform DOM move for THEAD elements **/

			/** perform DOM move for TBODY elements **/
			area.from = grid.fixedBodyContainer().children("table");
			area.to = grid.element;
			if (isToFix) {
				area = this._swapElements(area);
			}
			grid._moveColumnInBodyFooter({
											trsFrom: area.from.children("tbody").children("tr"),
											trsTo: area.to.children("tbody").children("tr"),
											ignoreDataSkip: true
		},
										movedCol, targetCol, after);
			grid._moveCols({
								from: area.from.children("colgroup"),
								to: area.to.children("colgroup"),
								ignoreDataSkip: true
							},
							movedCol, targetCol, after);
			/** // perform DOM move for TBODY elements **/

			/** perform DOM move for TFOOT elements **/
			/* the footers */
			area.from = grid.fixedFootersTable();
			area.to = grid.footersTable();
			if (isToFix) {
				area = this._swapElements(area);
			}
			if (grid.options.fixedFooters === true) {
				grid._moveCols({
									from: area.from.children("colgroup"),
									to: area.to.children("colgroup"),
									ignoreDataSkip: true
								},
								movedCol, targetCol, after);
				}
			grid._moveColumnInBodyFooter({
											trsFrom: area.from.children("tfoot").children("tr"),
											trsTo: area.to.children("tfoot").children("tr"),
											ignoreDataSkip: true
		},
								movedCol, targetCol, after);
			/** // perform DOM move for TFOOT elements **/
			this._updateHScrollbarWidth(w);
		},
		/*
		_getColumnWidth: function (col, $th) {
			var w, grid = this.grid, $tbl, $col;
			$th = $th || this._getTH(col.key);
			w = ($th.length === 1) ? $th.outerWidth() : 0;
			if (!w) {
				$tbl = col.fixed ? grid.fixedBodyContainer().children("table") :
										this.element;
				$col = $tbl.find('>colgroup>col').eq(grid.getVisibleIndexByKey(col.key));
				w = $col.length ? parseInt($col.width(), 10) : 0;
				w = w || col.width;
			}
			return w;
		},
		*/
		_getColumnWidth: function (col, $th) {
			/* col - column object */
			/* $th - optional - jQuery represantation of <TH /> */
			var w;
			if (col.width) {
				w = parseInt(col.width, 10);
			}
			if (!w) {
				$th = $th || this._getTH(col.key);
				if ($th.length === 1) {
					w = $th.outerWidth();
				}
			}
			return w;
		},
		_updateGridWidth: function () {
			/* grid._gridInnerWidth should be updated so grid._updateHScrollbarVisibility to work properly */
			var grid = this.grid;
			grid._gridContentWidth = grid.element.width();
			if (!this._isVirtualGrid()) {
				grid._gridInnerWidth = grid.scrollContainer().width();
				if (grid.options.height) {
					grid._gridInnerWidth += grid._scrollbarWidth();
			}
			} else {
				grid._gridInnerWidth = //this.element.width() + this._scrollbarWidth();
					grid._vdisplaycontainer().width() + grid._scrollbarWidth();
			}
		},
		_refreshDOMOnUnfixing: function (colObj) {
			var grid = this.grid, fDir = this.options.fixingDirection;
			this._populateContainers();
			if ($.ig.util.isIE10) {
				grid._hscrollbar().width("");
			}
			this._updateGridWidth();
			grid._columnMovingResets();
			grid._hscrollbarcontent().scrollLeft(0);
			if (!colObj.nonData) {
				this._changeStyleHeaderButton(colObj.id, false, colObj.isGroupHeader);
			}
			/* M.H. 22 Jul 2013 Fix for bug #147064: When column is resized and the content of the cells is distributed on two rows the rows from both areas are misaligned. */
			/* when width is changed gridContentWidth is not taken properly on unfixing columns */
			if (this._isVirtualGrid()) {
				grid._scrollContainer().scrollTop(0);
				this._updateHScrollbarWidth();
				grid._resetVirtualDom();
				/* M.H. 20 Aug 2014 Fix for bug #178399: When virtualization mode is "continuous" and you unfix column the records are scrolled to the top but the scrollbar is not updated. */
				if (this._isContinuousVirtualization()) {
					grid._setScrollContainerScrollTop(0);
				}
				if (fDir === "right") {
					this._adjustVirtWidthOnFixingRight(false);
				}
			} else {
				this._containers.body.unfixedContainer.scrollTop(0);
				/* M.H. 10 Sep 2014 Fix for bug #180223: When you call commit the fixed area shrinks. */
			}
			/* M.H. 23 Aug 2013 Fix for bug #150279: When fixingDirection is "right" and row selectors are enabled unfixing columns breaks the grid. */
			if (grid._fixedColumns.length === 0 &&
				(!this._getDataSkippedColumnsLength(true) || fDir === "right")) {
				this._removeFixedContainer();
			}
			if (this.options.syncRowHeights) {
				/* M.H. 13 Dec 2013 Fix for bug #159796: Fixing the first column causes misalignment between rows in fixed/unfixed area(in the new theme) */
				this.checkAndSyncHeights();
			}
			if (grid._gridHasWidthInPercent()) {
				this._setMinWidthForGridContainer();
			}
			grid._updateVerticalScrollbarCellPadding();
		},
		_refreshDOMOnFixing: function (id, isInit) {
			var grid = this.grid, fDir = this.options.fixingDirection,
				v = this._isVirtualGrid();
			this._updateGridWidth();
			if (isInit) {
				/* M.H. 12 Dec 2013 Fix for bug #159796: Fixing the first column causes misalignment between rows in fixed/unfixed area(in the new theme) */
				if (this._containers && this._containers.body) {
					if (fDir === "left") {
						this._containers.body.unfixedTable.addClass(this.css.unfixedTableLeft);
					} else {
						this._containers.body.unfixedTable.addClass(this.css.unfixedTableRight);
					}
				}
			}
			grid._columnMovingResets();
			if (!_aNull(id)) {
				this._changeStyleHeaderButton(id, true);
			}
			this._containers.body.unfixedContainer.scrollTop(0);
			if (v) {
				grid._scrollContainer().scrollTop(0);
				/* M.H. 19 Feb 2015 Fix for bug #187756: When row selectors and fixed virtualization are enabled fixing a column moves the horizontal scrollbar downwards. */
				this._updateHScrollbarWidth();
				grid._resetVirtualDom();
				if (isInit) {
					this._attachVirtualizationEvents();
				}
				/* M.H. 20 Aug 2014 Fix for bug #178399: When virtualization mode is "continuous" and you unfix column the records are scrolled to the top but the scrollbar is not updated. */
				if (this._isContinuousVirtualization()) { // continuous virtualization
					this._containers.body
						.fixedContainer
						.scrollTop(this._containers.body.unfixedContainer.scrollTop());
				}
				if (!grid._outerHScrollbar) {
					grid._hscrollbarcontent().css("overflow-y", "hidden");
				}
				if (fDir === "right") {
					this._adjustVirtWidthOnFixingRight(true);
				}
			}
			if (grid._gridHasWidthInPercent()) {
				this._setMinWidthForGridContainer();
				/* M.H. 9 May 2016 Fix for bug 218851: Hiding when having a fixed column, autofitLastColumn: false and the columns are smaller than the grid width autofits the headers */
				/* updateHScrollbarVisibility is called before property 'fixed' of column object to be updated so contentWidth is not properly calculated in case of width is in % */
				if (isInit && v) {
					grid._updateHScrollbarVisibility();
				}
			}
			/* M.H. 18 Sep 2013 Fix for bug #151251: The space under the row selectors is empty when fixNondataColumns is true. */
			this._checkAndRenderHScrlbarCntnr();
			/* when the dom has changed we need to throw an event to notify other features to update dom related parameters */
			grid._hscrollbarcontent().scrollLeft(0);
			if (grid._initialized) {
				grid._updateVerticalScrollbarCellPadding();
			}
			/* M.H. 20 Aug 2014 Fix for bug #176479: The fixed and unfixed rows are misaligned if you fix the last column and the column before it has value that spans on two or more lines. */
			if (this.options.syncRowHeights) {
				this.checkAndSyncHeights();
			}
		},
		_setMinWidthForGridContainer: function (w) {
			var $fc = this._mainFixedCntnr || this._containers.body.fixedContainer,
				$gc = this.grid.container();
			if (this.grid.hasFixedColumns()) {
				w = w || parseFloat($fc[ 0 ].style.width) || $fc.outerWidth();
				w += parseFloat(this.options.minimalVisibleAreaWidth);
				$gc.css("min-width", w + "px");
			} else {
				$gc.css("min-width", "");
			}
		},
		/**** CHECK FIXING/UNFIXING ALLOWED ****/
		_couldPerformUnfixing: function (colObj, target) {
			var grid = this.grid, mchLevel, $th, tCol;
			/* check if column is not fixed(e.g. called from API) */
			if (!!colObj.col.fixed === false) {
				colObj.result = false;
				colObj.error = this.internalErrors.alreadyUnfixed;
				return colObj;
				}
			/* try to unfix hidden column */
			if (colObj.col.hidden) {
				colObj.result = false;
				colObj.error = this.internalErrors.alreadyHidden;
				return colObj;
			}
			/* it should not be allowed to UNfix a column when target key is not properly specified - there isn't a column with such key OR this column is already fixed */
			/* M.H. 29 Feb 2016 Fix for bug 214757: fixColumn/unfixColumn API should throw an error if the target column key is not in the same area where the specified column should go */
			if (!_aNull(target)) {
				tCol = grid.columnByKey(target);
				if (!tCol) {
					colObj.result = false;
					colObj.error = this.internalErrors.targetNotFound.replace("{key}", target);
					return colObj;
				}
				if (tCol.fixed) {
					colObj.result = false;
					colObj.error = this.internalErrors.unfixingRefused;
					return colObj;
				}
			}
			$th = this._getTH(colObj.id);
			/* it is possible to fix/unfix a column which is only on highest MCH level(it is not possible to fix/unfix columns which children of MCH) */
			if (grid._isMultiColumnGrid) {
				mchLevel = $th.closest("tr").attr("data-mch-level");
				if (mchLevel && parseInt(mchLevel, 10) !== grid._maxLevel) {
					colObj.result = false;
					colObj.error = this.internalErrors.ActionNotAllowedForChildHeaderColumn;
					return colObj;
				}
			}
			colObj.colWidth = this._getColumnWidth(colObj.col, $th);
			/* in case there is only 1 visible column in fixed area and we try to unfix */
			if (!this.checkUnfixingAllowed(colObj.children)) {
				colObj.result = false;
				colObj.error = this.internalErrors.unfixingRefused;
				return colObj;
			}
			return colObj;
		},
		_couldPerformFixing: function (colObj, target) {
			/* target is optional - key of the target column */
			var grid = this.grid, gridWidth, mchLevel, widthFixedCntr, $th, colWidth, tCol;
			if (colObj.col.fixed) {
				colObj.result = false;
				colObj.error = this.internalErrors.alreadyFixed;
				return colObj;
			}
			if (colObj.col.hidden ||
				(!grid._initialized && $.inArray(colObj.col, grid._initialHiddenColumns) > -1)) {// in case when there are initially fixed columns AND initially hidden columns
				colObj.result = false;
				colObj.error = this.internalErrors.alreadyHidden;
				return colObj;
			}
			/* it should not be allowed to fix a column when target(column key) is not properly specified - if there isn't a column with target key OR this column is not fixed */
			/* M.H. 29 Feb 2016 Fix for bug 214757: fixColumn/unfixColumn API should throw an error if the target column key is not in the same area where the specified column should go */
			if (!_aNull(target)) {
				tCol = grid.columnByKey(target) ||
					this.grid._getMultiHeaderColumnById(target);
				if (!tCol) {
					colObj.result = false;
					colObj.error = this.internalErrors.targetNotFound.replace("{key}", target);
					return colObj;
				}
				if (!tCol.fixed) {
					colObj.result = false;
					colObj.error = this.internalErrors.fixingRefused;
					return colObj;
				}
			}
			gridWidth = grid.container().outerWidth();
			$th = this._getTH(colObj.id);
			if (grid._isMultiColumnGrid) {
				mchLevel = $th.closest("tr").attr("data-mch-level");
				if (mchLevel && parseInt(mchLevel, 10) !== grid._maxLevel) {
					colObj.result = false;
					colObj.error = this.internalErrors.ActionNotAllowedForChildHeaderColumn;
					return colObj;
				}
			}
			/* it should not be allowed fixing when there is only one visible column in fixed area and we try to fix this column */
			if (!this.checkFixingAllowed(colObj.children, colObj.isGroupHeader)) {
				colObj.result = false;
				colObj.error = this.internalErrors.fixingRefused;
				return colObj;
			}
			/* if last column has automatically increased width (because 1 or more of the last columns are hidden) then fixing it
			the width of this column should be rolled back to its original width - we should get the width not of the DOM element but the original Width
			then we should rerender colgroups because fixing is just detaching the col from unfixed area and attaching to the fixed area
			- check minimalVisibleAreaWidth - when there are fixed columns(including data-skip columns) - get main fixed container width */
			/* M.H. 27 Feb 2015 Fix for bug #189710: When renderExpansionIndicatorColumn: true it is possible to fix a wide enough column to make the fixed area wider than the treegrid */
			if (grid.hasFixedColumns()) {
				widthFixedCntr = this._containers.body.fixedContainer.outerWidth();
					}
			if (isNaN(widthFixedCntr)) {
				widthFixedCntr = 0;
				}
			colWidth = this._getColumnWidth(colObj.col, $th);
			colObj.colWidth = colWidth;
			/* check whether we could fix a column - if the sum of width of new fixed area and width of the column exceeds the width of the grid then do not allow column fixing */
			/* M.H. 22 Feb 2013 Fix for bug #133421: Columns and headers are misaligned on fixing a column when grid and its columns doesn't have width set */
			if (colWidth + widthFixedCntr + grid._scrollbarWidth() >=
					gridWidth - parseInt(this.options.minimalVisibleAreaWidth, 10)) {
				colObj.result = false;
				colObj.error = this.internalErrors.fixingRefusedMinVisibleAreaWidth;
				return colObj;
			}
			return colObj;
		},
		checkFixingAllowed: function (columns) {
			/* Check whether fixing is allowed for the passed argument - columns. It should not be allowed if there is only one visible column in unfixed area and there are hidden unfixed columns
			paramType="array" array of columns - could be column indexes, column keys, column object or mixed
			returnType="bool" returns whether it is allowed fixing for the specified columns
			*/
			var i, visibleColumnsCount = 0;
			for (i = 0; i < columns.length; i++) {
				if (!columns[ i ].hidden) {
					visibleColumnsCount++;
				}
			}
			if (this.grid._visibleColumns(false).length - visibleColumnsCount < 1) {
				return false;
			}
			return this._isFixingUnfixingAllowed(columns, true);
		},
		checkUnfixingAllowed: function (columns) {
			/* Check whether unfixing is allowed for the passed argument - columns. It should not be allowed if there is only one visible column in fixed area and there are hidden fixed columns
			paramType="array" array of columns - could be column indexes, column keys, column object or mixed
			returnType="bool" returns whether it is allowed unfixing for the specified columns
			*/
			return this._isFixingUnfixingAllowed(columns, false);
		},
		_isFixingUnfixingAllowed: function (columns, isToFix) {
			/* columns - array of columns - it should be columns(including hidden in case of MCH scenario) you want to fix/unfix
			 isToFix - boolean argument - check whether for the passed columns it is allowed to fix, if false then it is checked it is possible to unfix columns
			 Check whether fixing/unfixing is allowed
			 It should NOT be allowed if there is ONLY one visible column(and hidden unfixed columns) in unfixed area and trying to fix it or ONLY one visible fixed column(and there are other fixed hidden columns) and trying to unfix it */
			var i, columnsLength = columns.length, j, col, columnsInArea = [],
				oCols = this.grid.options.columns, oColsLength = oCols.length;
			/* first get all columns in area - e.g. if columns should be fixed get all UNfixed columns(in this area we found columns) */
			for (i = 0; i < oColsLength; i++) {
				if ((!isToFix && oCols[ i ].fixed) || (isToFix && !oCols[ i ].fixed)) {
					for (j = 0; j < columnsLength; j++) {
						col = columns[ j ];
						if (col.key === oCols[ i ].key) {
							break;
						}
					}
					if (j === columnsLength) {
						columnsInArea.push(oCols[ i ]);
					}
				}
			}
			if (columnsInArea.length === 0) {
				return true;
			}
			for (i = 0; i < columnsInArea.length; i++) {
				if (!columnsInArea[ i ].hidden) {
					return true;
				}
			}
			return false;
		},
		/**** //CHECK FIXING/UNFIXING ALLOWED ****/
		_rearrangeColumns: function (colKey, targetKey, after, isToFix) {
			var grid = this.grid, targetColInfo, tInd,
				aObj = grid._analyzeColumnMovingObjects(colKey, targetKey, after, isToFix);
			/*_rearrangeArray: function (array, start, length, at) { */
			grid._rearrangeArray(grid.options.columns, // modified array
									aObj.from.dIndex, // start
									aObj.from.children.length, // length
									aObj.at.dIndex); // at
			if (aObj.at.dMCHIndex > -1) {
				grid._rearrangeArray(grid._oldCols, // modified array
									aObj.from.dMCHIndex, // start
									1, // length
									aObj.at.dMCHIndex); // at
			}
			this._setFixingStateByCol(aObj.from.colMCHObj || aObj.from.colObj, isToFix);
			if (isToFix) {
				targetColInfo = !_aNull(targetKey) ? grid._getColumnInfo(targetKey, grid._fixedColumns) : null;
				tInd = !_aNull(targetColInfo) ? targetColInfo.index : grid._fixedColumns.length;
				grid._fixedColumns.insertRange(tInd, aObj.from.children);
			} else {
				/* remove from fixedColumns */
				grid._fixedColumns.splice(
					grid._getColumnInfo(aObj.from.children[ 0 ].key, grid._fixedColumns).index,
					aObj.from.children.length);
			}
			/*we should reset collections that are already cached - like grid._visibleColumnsArray, grid._virtualDom */
			grid._columnMovingResets();
			return aObj;
		},
		fixColumn: function (colIdentifier, target, after) {
			/* Fix column by specified column identifier - column index or column key
			paramType="number|string" An identifier of the column to be fixed - column index or column key.
			paramType="string" optional="true" Key of the column where the fixed column should move to.
			paramType="bool" optional="true" Specifies where the fixed column should be moved after or before the target column. This parameter is disregarded if there is no target column specified.
			returnType="object" Object description: { result: indicates whether fixing is successful, error: error message describing the reason fixing has failed, col: reference to the column object(if column identifier is not valid - then its value is null) }
			*/
			if ($.type(arguments[ 1 ]) === "boolean") {// in previous version 2nd argument was isGroupHeader, 3rd - target, 4th - after
				target = arguments[ 2 ];
				after = arguments[ 3 ];
			}
			var col, res, children, isGroupHeader, colKey, aObj,
				grid = this.grid, mch, fixedCols = grid._fixedColumns,
				isInit = (fixedCols.length === 0);
			/* result object - having properties like result - boolean; col - col object, error. Used also in client side event columnFixingRefused */
			isGroupHeader = this.isGroupHeader(colIdentifier);
			res = {
				error: this.internalErrors.none,
				result: true,
				col: null,
				isInit: isInit,
				isGroupHeader: isGroupHeader
			};
			/* get column object */
			if (!isGroupHeader) {
				col = this._getColById(colIdentifier);
				res.col = col;
				if (!col) {
					res.result = false;
					res.error = this.internalErrors.notValidIdentifier;
					return res;
				}
				res.col = col;
				colKey = col.key;
				children = [ col ];
				res.id = colKey;
				/* M.H. 18 Apr 2016 Fix for bug 216301: Horizontal scrollbar disappears when resizing a column, fixing and unfixing it after that with virtualization */
				delete col.oWidth;
			} else {
				colKey = colIdentifier;
				res.id = colKey;
				mch = this.grid._getMultiHeaderColumnById(colIdentifier);
				if (!mch) {
					res.result = false;
					res.error = this.internalErrors.notValidIdentifier;
					return res;
				}
				res.col = mch;
				children = mch.children;
			}
			res.children = children;
			res = this._couldPerformFixing(res, target);
			if (!res.result) {
				return res;
			}
			after = $.type(after) === "boolean" ? after : false;
			if (isInit && !this.element.data("fixingApplied")) {
				this._renderMainFixedContainer();
				this.fixNonDataColumns();
			}
			if (!this._containers || !this._containers.body) {
				this._populateContainers();
			}
			this._performDomColumnMove(res, target, after, true);
			aObj = this._rearrangeColumns(colKey, target, after, true);
			grid._hasFixedColumns = true;
			this._refreshDOMOnFixing(res.id, res.isInit);
			grid._onFixedColumnsChanged({
				isToFix: true,
				id: colIdentifier,
				colWidth: res.colWidth,
				children: res.children,
				isGroupHeader: isGroupHeader,
				isInit: isInit,
				/* indexes used to move column objects */
				start: aObj.from.dIndex,
				length: aObj.from.children.length,
				at: aObj.at.dIndex
			});
			return res;
		},
		_attachVirtualizationEvents: function () {
			var grid = this.grid;
			grid.fixedBodyContainer().bind({
				mouseenter: function () {
					grid._isMouseOverVirtualTable = true;
				},
				mouseleave: function () {
					grid._isMouseOverVirtualTable = false;
				}
			});
		},
		/******** Non-data columns specific functions ********/
		fixNonDataColumns: function () {
			/* Fix non-data columns(like column of row selectors) if any - when fixing direction is left. If already fixed nothing is done
			*/
			var grid = this.grid,
				cols = this._getDataSkippedCols(),
				w = this._getDataSkippedWidth(false, cols),
				len = cols.length; // data-skipped columns in unfixed area
			/* we should fix data skipped columns if any - for now supported scenario is ONLY if fixing direction is LEFT */
			if (this.options.fixingDirection === "left" && len) {
				if (!$("#" + grid.id() + "_mainFixedContainer").length &&
					!this.element.data("fixingApplied")) {
					this._renderMainFixedContainer();
				}
				grid._hasFixedDataSkippedColumns = true;
				this._performDomColumnMoveByIndexes({ start: 0, length: len, width: w, nonData: true },
													{ start: -1, length: 0 },
													false,
													true);
				this._refreshDOMOnFixing(null, true);
				if (this.options.syncRowHeights) {
					this.checkAndSyncHeights();
			}
				/* M.H. 8 Sep 2014 Fix for bug #178635: The scrollbar shrinks when you fix a column. */
				if (this._isVirtualGrid()) {
					this.grid._hscrollbarcontent().css("overflow-y", "hidden");
				}
				this._checkAndRenderHScrlbarCntnr();
				grid._hasFixedColumns = true;
				/*trigger internal(for the grid) event */
				this.grid._onFixedColumnsChanged({
					isToFix: true,
					nonData: true,
					isInit: true,
					length: len
				});
			}
		},
		fixDataSkippedColumns: function () {
		    /* *** IMPORTANT DEPRECATED ***
			This function is deprecated - use function fixNonDataColumns.
			*/
			/* deprecated!!! We support it because of backward compatibility */
			this.fixNonDataColumns();
		},
		unfixNonDataColumns: function () {
			/* Unfix data skipped columns(like non-data columns in row-selectors feature) if any - when fixingDirection is left
			*/
			var w, cols = this._getDataSkippedCols(true),
				len = cols.length; // data-skipped columns in unfixed area
			/* we should fix data skipped columns if any - for now supported scenario is ONLY if fixing direction is LEFT */
			if (this.options.fixingDirection === "left" && len) {
				w = this._getDataSkippedWidth(true, cols);
				this._performDomColumnMoveByIndexes({ start: 0, length: len, width: w, nonData: true },
													{ start: 0, length: 0 },
													false,
													false);
				this.grid._hasFixedColumns = !!this.grid._fixedColumns.length;// if it is called and there are fixed DATA columns
				this.grid._hasFixedDataSkippedColumns = false;
				this._refreshDOMOnUnfixing({ nonData: true, width: w });//if in fixed area there are only non-data columns - grid._hasFixedColumns should be set to false - so mainfixed container to be removed
				/*trigger events */
				this.grid._onFixedColumnsChanged({
					isToFix: false,
					nonData: true,
					/* indexes used to move column objects */
					length: len
				});
			}
		},
		unfixDataSkippedColumns: function () {
		    /* *** IMPORTANT DEPRECATED ***
			This function is deprecated - use function unfixNonDataColumns.
			*/
			this.unfixNonDataColumns();
		},
		_getDataSkippedWidth: function (isFixed, cols) {
			var w = 0;
			cols = cols || this._getDataSkippedCols(isFixed);
			cols.each(function (i, c) {
				w += parseFloat(c.style.width);
			});
			return w;
		},
		_getDataSkippedCols: function (isFixed) {
			if (!this._containers || !this._containers.body) {
				this._populateContainers();
			}
			var $table = isFixed ?	this._containers.body.fixedTable :
									this._containers.body.unfixedTable;
			return $table.find("colgroup col[data-skip]");
		},
		_getDataSkippedColumnsLength: function (isFixed) {
			return this._getDataSkippedCols(isFixed).length;
		},
		/******** //Non-data columns specific functions ********/
		unfixAllColumns: function () {
			/* Unfix all columns (if any)
			*/
			if (!this.grid.hasFixedColumns()) {
				return;
			}
			/* M.H. 6 Jun 2013 Fix for bug #142858: The grid's multi column headers are shuffled when you fix a few groups and then you call unfixAllColumns. */
			var i, self = this, colsToUnfix = []; //array of keys of fixed column
			/* M.H. 5 Mar 2013 Fix for bug #134548: UnfixAllColumns method reverts the order of columns. */
			if (this.grid._isMultiColumnGrid) {
				/* get first table row from headears table(FIXED) - it is with fixed table top lever(root) cells and get IDs - column keys, data-mch-id */
				this.grid.fixedHeadersTable().children("thead")
								.children("tr[data-mch-level]:nth-child(1)")
								.children("th:not([data-skip])")
								.each(function (index, th) {
									var $th = $(th), id;
									id = $th.attr("data-mch-id");
									id = id || $th.attr("id").replace(self.grid.id() + "_", "");
									colsToUnfix.push(id);
								});
			} else {
				colsToUnfix = $.map(this.grid._fixedColumns, function (c) { return c.key; });
			}
			if (colsToUnfix.length > 0) {
				for (i = colsToUnfix.length - 1; i >= 0; i--) {
					this.unfixColumn(colsToUnfix[ i ]);
				}
			}
		},
		/******** render main fixed container and its internal containers and tables ********/
		_removeFixedContainer: function () {
			this.element.data("fixingApplied", false);
			var grid = this.grid, scrollContainer, v = this._isVirtualGrid();
			if (v) {
				this._removeFixedContainerVirtualization();
				}
			if (!this._containers) {
				this._populateContainers();
			}
			scrollContainer = this._containers.body.unfixedContainer;
			scrollContainer.unbind(".columnFixing");
			/* M.H. 9 May 2016 Fix for bug 218834: Scrolling is not possible with mouse wheel after unfixing a right fixed column */
			this.element.unbind(".columnFixing");
			/* M.H. 12 Dec 2013 Fix for bug #159796: Fixing the first column causes misalignment between rows in fixed/unfixed area(in the new theme) */
			if (this._containers && this._containers.body) {
				this._containers.body.unfixedTable
					.removeClass(this.css.unfixedTableLeft)
					.removeClass(this.css.unfixedTableRight);
				if (!v) {
					this._containers.body.unfixedTable.css("height", "");
			}
			}
			/* M.H. 26 Feb 2013 Fix for bug #134003: When the fixingDirection is "right" mouse wheel scroll does not work when you hover the unfixed area. */
			if (!_aNull(this._DOMMouseScroll)) {
				scrollContainer.unbind({
					DOMMouseScroll: this._DOMMouseScroll
				});
				this._DOMMouseScroll = null;
			}
			grid.fixedContainer().remove();
			if (this.options.fixingDirection === "right" && !v) {
				scrollContainer.css({ "overflow-y": "auto" });
				}
			if (!grid._outerHScrollbar) {
				/* M.H. 21 Feb 2013 Fix for bug #133521: When you fix and unfix a column the width of the columns changes and scrolling the grid to the right misaligns it. */
				grid._hscrollbar().css({
					width: "100%",
					left: 0
				});
				}
			/* M.H. 24 Feb 2013 Fix for bug #133421: Columns and headers are misaligned on fixing a column when grid and its columns doesn't have width set */
			if (grid.options.width === null && grid.container()[ 0 ].style.width === "") {
				grid.container().css("width", "");
				}
			if (grid.options.enableHoverStyles) {
				this._dettachHoverEvents();
			}
			$("#" + grid.id() + "_floatCompensate").remove();
		},
		_removeFixedContainerVirtualization: function () {
			var $virtCntnr = this.grid._virtualcontainer();
			$virtCntnr.children("colgroup").children("col[data-fixed-col]").remove();
			$virtCntnr.children("tbody").children("tr")
				.children("td[data-fixed-container]")
				.remove();
		},
		_getRowsHtml: function ($unfixedArea, type) {
			var html = "", attrs, i, j, len, tr, a, h, av, an,
				synch = this.options.syncRowHeights,
				trs = $unfixedArea.children("tr");
			/* get all rows without getting internal content - <TD> and <TH> */
			this._heights = this._heights || {};
			this._heights[ type ] = [];
			len = trs.length;
			for (i = 0; i < len; i++) {
				tr = trs[ i ];
				attrs = tr.attributes;
				html += "<tr";
				for (j = 0; j < attrs.length; j++) {
					a = attrs[ j ];
					av = a.value;
					an = a.name;
					if (an === "id" || an === "ID") {
						av += "_fixed";
			}
					html += " " + an + "=\"" + av + "\"";
				}
				if (synch) {
					h = $.ig.util.isIE ? tr.getBoundingClientRect().height :
						tr.offsetHeight;
					this._heights[ type ].push({ h: h, tr: tr });
					html += " height=\"" + h + "px\""; // sync rowsHeights if option is enabled
			}
				html += "></tr>";
			}
			return html;
		},
		_renderContainerInVirtGrid: function ($tr, $table, type) {
			var $cntnr,
				$td = $("<td data-fixed-container=\"" + type + "\" style=\"border-width: 0px;\"></td>"),
				cssClass = this.options.fixingDirection === "left" ?
								this.css.leftFixedContainer :
								this.css.rightFixedContainer;
			if (this.options.fixingDirection === "left") {
				$td.prependTo($tr);
			} else {
				$tr.children("td:first").removeAttr("colspan");
				if (type === "body") {
					$td.insertBefore($tr.children("td:last"));
				} else {
					$td.attr("colspan", 2);
					$td.appendTo($tr);
				}
			}
			$cntnr = this._renderContainer($td, $table, type);
			$cntnr.addClass(cssClass);//this.css.fixedContainer + ' ' +
			this._containers[ type ].fixedContainer.width(0);
			this._containers[ type ].fixedTable.width(0);
			return $cntnr;
		},
		_renderContainer: function ($mainFixedCntr, $table, type) {
			var fixedContainerId, $fixedContainer, $fixedTable, html;
			switch (type) {
				case "header":
					fixedContainerId = this.grid.id() + "_fixedHeaderContainer";
					html = "<thead></thead>";
					break;
				case "footer":
					fixedContainerId = this.grid.id() + "_fixedFooterContainer";
					html = "<tfoot role=\"rowgroup\"></tfoot>";
					break;
				default: //case "body":
					fixedContainerId = this.grid.id() + "_fixedBodyContainer";
					html = "<tbody>" + this._getRowsHtml($table.children("tbody"), type) + "</tbody>";
					break;
			}
			$fixedContainer = $("<div id=\"" + fixedContainerId +
									"\" data-fixed-container=\"true\"></div>")
							.appendTo($mainFixedCntr);
			if (type === "body") {
				$fixedContainer.attr("data-scroll", "true");
			}
			$fixedTable = $("<table id=\"" + $table.attr("id") + "_fixed\"" +
									" class=\"" + $table.attr("class") + "\" " +
									" style=\"table-layout:fixed;\" " +
									" border=0 cellpadding=0 cellspacing=0><colgroup />" +
								html +
							"</table>");
			$fixedTable.appendTo($fixedContainer);
			this._containers[ type ] = this._containers[ type ] || {};
			this._containers[ type ].fixedContainer = $fixedContainer;
			this._containers[ type ].fixedTable = $fixedTable;
			this._containers[ type ].unfixedContainer = $table.closest("div");
			this._containers[ type ].unfixedTable = $table;
				$fixedContainer
					.css({
						"overflow": "hidden",
						"position": "relative"
					});
			if (type === "header") {
				this._containers.header
					.fixedContainer
					.addClass(this._containers.header.unfixedContainer.attr("class"));
				}
				return $fixedContainer;
		},
		_scrollTopFixedContainer: function (direction) {
			var fC = this._scrollContainers.fCntnr,
				ufC = this._scrollContainers.ufCntnr,
				scrollTop = fC.scrollTop();
			direction = direction || 0;
			scrollTop -= direction * this.options.scrollDelta;
			scrollTop = scrollTop < 0 ? 0 : scrollTop;
			fC.scrollTop(scrollTop);
			ufC.scrollTop(scrollTop);
		},
		_syncScrollTopContainers: function ($cntnr, $cntnrToScroll) {
			$cntnrToScroll.scrollTop($cntnr.scrollTop());
		},
		_bindEvents: function () {
			/* binds scrolling events - sync scrolling between fixed/unfixed containers */
			var grid = this.grid, self = this,
				fc = this._scrollContainers.fCntnr,
				ufc = this._scrollContainers.ufCntnr;
			if (grid.options.enableHoverStyles) {
				this._attachHoverEvents();
			}
			if (this._isContinuousVirtualization()) {
				ufc.bind({
					scroll: function () {
						self._syncScrollTopContainers($(this), fc);
					}
				});
			}
			/* sync scrollTop of fixed and unfixed containers when tabbing throught cells/rows in fixed container(or using keyboard navigation in row/cell selection) */
			fc.delegate("tr, td", {
				focus: function () {
					/* M.H. 8 Sep 2016 Fix for bug 224242: Fixed and unfixed area rows get misaligned when selecting cell in fixed area in IE11. */
					if ($.ig.util.isIE) {
						// IN IE when TR is focused - first event focusin is fired and after that container is scrolled
						setTimeout(function () {
							self._syncScrollTopContainers(fc, ufc);
						}, 0);
					} else {
						self._syncScrollTopContainers(fc, ufc);
					}
				}
			});
			if (this._isVirtualGrid()) {
				return;
			}
			this._onMouseWheelHandler = function (event) {
				var evt, direction, w, d, st;
				evt = event.originalEvent;
				w = evt.wheelDelta;
				d = evt.detail;
				if (d) {
					if (w) {
						direction = w / d / 40 * d > 0 ? 1 : -1; /* Opera*/
					} else {
						direction = -d / 3;/* Firefox; */
					}
				} else {
					direction = w / 120;// IE/Safari/Chrome
				}
				st = fc.scrollTop();
				self._scrollTopFixedContainer(direction);
				/* M.H. 4 Mar 2013 Fix for bug #134225: The page can't be scrolled vertically with mouse wheel when you hover the fixed column. */
				/* M.H. 21 Mar 2015 Fix for bug 190105: Browser’s vertical scrollbar does not move by mouse wheel if MultiColumnHeaders and ColumnFixing are enabled. */
				if (grid.options.height === null ||
					st === fc.scrollTop()) {
					return true;
				}
				return false;
			};
			/* M.H. 26 Feb 2013 Fix for bug #134003: When the fixingDirection is "right" mouse wheel scroll does not work when you hover the unfixed area. */
			this._DOMMouseScroll = function (event) { // Firefox
				var dir = -1, delta;
				delta = -event.originalEvent.detail / 3;// determine if we are scrolling up or down
				if (delta > 0) { // scroll up
					dir = 1;
				}  // else default => scroll down
				self._scrollTopFixedContainer(dir);
				if (grid.options.height === null) {
					return true;
				}
				event.preventDefault();
			};
			/* M.H. 4 Jul 2014 Fix for bug #173032: Desynchronization between rows in the fixed and unfixed area on touch devices */
			if (grid.element.igScroll !== undefined &&
				($.ig.util.isTouch || window.PointerEvent || window.navigator.msPointerEnabled)) {
				/* instantiate igScroll for the fixed area */
				fc.igScroll({
					scrolled: function () {
						self._scrollTopFixedContainer(0);
						/*self._DOMMouseScroll(e); */
					},
					stopped: function () {
						self._scrollTopFixedContainer(0);
					}
				});
				/* M.H. 17 Jul 2014 Fix for bug #175880: Cannot scroll the fixed area on Internet Explorer under touch devices */
				fc.css("-ms-touch-action", "none");
			}
			/* M.H. 12 Apr 2016 Fix for bug 217368: Scrolling speed of the unfixed part of the grid is very slow on Google Chrome 49 on Windows 7. */
			if (this.options.fixingDirection === "right") {
				/* M.H. 12 May 2016 Fix for bug 219197: When column fixing, with direction right and cell selection is enabled(and there is at least one fixed column) trying to navigate from the first cell(from the first record of the unfixed area) as pressing key UP - grid is not scrolled */
				this._bindEventsToContainers(fc, ufc);
				/* M.H. 12 Apr 2016 Fix for bug 217368: Scrolling speed of the unfixed part of the grid is very slow on Google Chrome 49 on Windows 7. */
				this.element.bind({
					"mousewheel.columnFixing": this._onMouseWheelHandler,
					"DOMMouseScroll.columnFixing": this._DOMMouseScroll
				});
				ufc.bind({
					"scroll.columnFixing": function () {
						self._syncScrollTopContainers($(this), fc);
					}
				});
			} else {
				this._bindEventsToContainers(ufc, fc);
			}
		},
		_bindEventsToContainers: function ($scrollable, $hiddenScroll) {
			/* $scrollable - DIV container with overflow-y:auto (if fixing direction is left - this is unfixed container)
			$hiddenScroll - DIV container with overflow-y:hidden(if fixing direction is left - this is fixed container) */
			var self = this,
				selection = this.grid.element.data("igGridSelection");
			$scrollable.bind({
				"scroll.columnFixing": function () {
					self._syncScrollTopContainers($(this), $hiddenScroll);
				}
			});
			/* M.H. 27 Apr 2016 Fix for bug 217439: When column fixing and cell selection is enabled(and there is at least one fixed column) trying to navigate from the first cell(from the first record) as pressing key UP - grid is not scrolled to the selected cell in IE */
			if ($.ig.util.isIE) {
				$hiddenScroll.bind({
					"scroll.columnFixing": function () {
						self._syncScrollTopContainers($(this), $scrollable);
					}
				});
				$scrollable.bind({
					"mousewheel.columnFixing": function (e) {
						/* M.H. 9 Sep 2016 Fix for bug 224700: Parent element that has scroll bar and contains a grid with ColumnFixing is prevented from scrolling when wheeling unfixed area of the grid. */
						/*e.preventDefault();*/
						self._onMouseWheelHandler(e);
					}
				});
			}
			/* M.H. 26 Feb 2013 Fix for bug #134003: When the fixingDirection is "right" mouse wheel scroll does not work when you hover the unfixed area. */
			$hiddenScroll.bind({
				"mousewheel.columnFixing": this._onMouseWheelHandler,
				/* M.H. 22 Feb 2013 Fix for bug #133776: The mouse wheel scroll does not work when you hover the fixed area in Firefox. */
				"DOMMouseScroll.columnFixing": this._DOMMouseScroll,
				"keydown.columnFixing": function (e) {
					if (!selection) {
						if (e.keyCode === $.ui.keyCode.UP) {
							self._scrollTopFixedContainer(1);
						} else if (e.keyCode === $.ui.keyCode.DOWN) {
							self._scrollTopFixedContainer(-1);
						}
					}
				}
			});
		},
		_renderMainFixedContainerInVirtGrid: function () {
			this._populateContainers();
			var grid = this.grid, $col, $pScrlbar,
				html, $colgroup, $hscrlbar = grid._vhorizontalcontainer(), $td,
				$vContainer = grid._virtualcontainer(),
				fixedBodyContainer, scrollContainer, scrollContainerHeight;
			$colgroup = $vContainer.children("colgroup:first");
			scrollContainer = this._containers.body.unfixedContainer;
			/* render containers */
			/* first we should render body container */
			$col = $("<col />").attr("data-fixed-col", this.options.fixingDirection);
			if (this.options.fixingDirection === "left") {
				$col.prependTo($colgroup);
			} else {
				$col.insertBefore($colgroup.children("col:last"));
			}
			/* render body container */
			fixedBodyContainer = this._renderContainerInVirtGrid(
								this._containers.body.unfixedContainer.closest("tr"),
								this._containers.body.unfixedTable,
								"body");
			fixedBodyContainer
				.addClass(grid.css.gridVirtualScrollDivClass)
				.addClass(grid.css.gridScrollDivClass);

			if ($.ig.util.isIE) {
				fixedBodyContainer.children("table").height(scrollContainer.children("table").height());
				}
			scrollContainerHeight = scrollContainer.height();
			fixedBodyContainer.height(scrollContainerHeight);
			/* M.H. 10 Feb 2016 Fix for bug 143586: CellClick event doesn't fire for fixed columns. */
			fixedBodyContainer.bind(grid._mouseClickEventHandlers);
			if (grid.options.showHeader) {
				this._renderContainerInVirtGrid(this._containers.header.unfixedContainer.closest("tr"),
																this._containers.header.unfixedTable,
																"header");
				/* M.H. 22 Feb 2013 Fix for bug #133539: When the grid has vertical scrollbar and caption fixing a column makes the headers misaligned. */
				grid._renderFixedCaption();
				/* render rows */
				/* header <TR> */
				html = this._getRowsHtml(this._containers.header.unfixedTable.children("thead"),
									"header");
				this._containers.header.fixedTable.children("thead")
					.html(html);
			}
			if (grid.options.showFooter && this._containers.footer) {
				this._renderContainerInVirtGrid(this._containers.footer.unfixedContainer.closest("tr"),
																this._containers.footer.unfixedTable,
																"footer");
				if (grid.options.fixedFooters) {
					this._containers.footer.fixedContainer.bind(grid._mouseClickEventHandlers);
				}
				/*NOTE: most-consuming for rendering is TRs of <tbody />- I have moved it in _renderContainer - performance optimization!!! */
				html = this._getRowsHtml(grid.footersTable().children("tfoot"),
									"footer");
				grid.fixedFootersTable().children("tfoot")
					.html(html);
			}
			/* only for data container */
			grid._fixedTable = fixedBodyContainer.children("table");
			if (!$hscrlbar.length) {
				$hscrlbar = grid._hscrollbar();
			}
			$pScrlbar = $hscrlbar.parent();
			if ($pScrlbar.is("td")) {
				$td = $("<td data-fixed-container=\"scrollbar\"></td>");
				if (this.options.fixingDirection === "left") {
					$td.insertBefore($hscrlbar.closest("td"));
				} else {
					$td.attr("colspan", 2)
						.insertAfter($hscrlbar.closest("td").removeAttr("colspan"));
				}
			}
			this._scrollContainers = {
				fCntnr: fixedBodyContainer,
				ufCntnr: scrollContainer
			};
			this._bindEvents();
		},
		_renderMainFixedContainer: function () {
			this.element.data("fixingApplied", true);
			if (this._isVirtualGrid()) {
				return this._renderMainFixedContainerInVirtGrid();
			}
			var grid = this.grid, gridId = grid.id(), html,
				fDir = this.options.fixingDirection, scrCntnrTbl,
				fixedBodyTable, fixedHeaderContainer, fixedFooterContainer, scrollContainer,
				$mainFixedContainer, fixedBodyContainer,
				mainFixedContainerId = grid.id() + "_mainFixedContainer",
				scrollContainerHeight;
			/* M.H. 28 Feb 2013 Fix for bug #134435: When height is set and column is fixed changing the page changes grid's height and the horizontal scrollbar is not on the correct place */
			$mainFixedContainer = $("<div id=\"" + mainFixedContainerId +
										"\" data-fixed-container=\"true\"></div>");
			this._mainFixedCntnr = $mainFixedContainer;
			$mainFixedContainer
				.css({
					"width": "0px",
					"overflow": "hidden",
					"position": "relative"
			});
			scrollContainer = grid.scrollContainer();
			if (scrollContainer.length) {
				scrCntnrTbl = scrollContainer.children("table");
			} else {// in case of grid width AND grid height are not set(OR set in %)
				scrollContainer = grid.element;
				scrCntnrTbl = grid.element;
			}
			$mainFixedContainer
				.addClass(this.css.fixedContainer);
			$mainFixedContainer.insertBefore(grid.options.fixedHeaders &&
												grid.options.showHeader ?
																grid.headersTable().closest("div") :
																scrollContainer);
			if (fDir === "left") {
				$mainFixedContainer.css({ "float": "left", "left": 0 });
				$mainFixedContainer.addClass(this.css.leftFixedContainer);
			} else {
				$mainFixedContainer.css({ "float": "right", "right": 0 });
				$mainFixedContainer.addClass(this.css.rightFixedContainer);
			}
			$mainFixedContainer.attr("data-fixing-direction", fDir);
			$mainFixedContainer.bind(this.grid._mouseClickEventHandlers);
			/* render containers */
			/* first we should render body container */
			/* render body container */
			fixedBodyContainer = this._renderContainer($mainFixedContainer, scrCntnrTbl, "body");
			/* M.H. 10 Sep 2014 Fix for bug #179865: When you navigate the cell selection with arrow keys from fixed to unfixed area the selected cell goes to  the next row in virtual grid. */
			fixedBodyContainer.attr("data-fixing-direction", fDir);
			if (grid.options.height !== null && $.ig.util.isIE) {
				fixedBodyContainer.find("table")
					.height(scrCntnrTbl.height());
				}
			/* M.H. 26 Feb 2013 Fix for bug #133862: When height is not defined and column is fixed filtering a column makes the footer too big and the grid keeps its original height. */
			if (grid.options.height !== null) {
				scrollContainerHeight = scrollContainer.height();
				/* M.H. 7 Mar 2013 Fix for bug #135253: When width and height are set to the grid, widths to the columns are not set and there is initially fixed column the fixed area has bigger height than the unfixed. */
				if (this.grid._hscrollbar().is(":visible")) {
					scrollContainerHeight += this.grid._hscrollbar().outerHeight();
				}
			}
			fixedBodyContainer.height(scrollContainerHeight);
			/*fixedBodyContainer.appendTo($mainFixedContainer); */
			fixedBodyTable = fixedBodyContainer.find("table");
			if (grid.options.showHeader) {
				if (grid.options.fixedHeaders && grid.options.height !== null) {
					fixedHeaderContainer = this._renderContainer($mainFixedContainer,
																grid.headersTable(),
																"header");
					fixedHeaderContainer.prependTo($mainFixedContainer);
					/* M.H. 22 Feb 2013 Fix for bug #133539: When the grid has vertical scrollbar and caption fixing a column makes the headers misaligned. */
					grid._renderFixedCaption();
					} else {
					/* M.H. 4 Mar 2015 Fix for bug #189443: When columns are auto generated and heigth is not set fixing a column and data binding a grid causes a misalignment */
					/* order of TBODY/TFOOT/THEAD should be the same in both fixed/unfixed tables */
					if (grid.element.find("thead").next().is("tbody")) {
						$("<thead />").insertBefore(fixedBodyContainer.find("tbody"));
					} else {
						if (grid.element.find("thead").prev().is("colgroup")) {
							$("<thead />").insertAfter(fixedBodyContainer.find("colgroup"));
						} else {
							$("<thead />").insertAfter(fixedBodyContainer.find("tbody"));
					}
				}
			}
				}
			if (grid.options.showFooter) {
				if (grid.options.fixedFooters && grid.options.height !== null) {
					fixedFooterContainer = this._renderContainer($mainFixedContainer,
																grid.footersTable(),
																"footer");
					/* M.H. 11 Jun 2013 Fix for bug #143596: When the width of a fixed column is smaller than the width of the text in summary cell this text goes in 2 rows and it makes the summaries misaligned. */
					this._containers.footer
						.fixedTable.css("whiteSpace",
										this._containers.footer.unfixedTable.css("whiteSpace"));
					fixedFooterContainer.appendTo($mainFixedContainer);
				} else {
					/*M.K. 28 May 2015: Move TFOOT rendering after the TBODY (in order to support ARIA). */
					$("<tfoot role=\"rowgroup\" />").insertAfter(fixedBodyContainer.find("tbody"));
				}
			}
			/* render rows */
			/* header <TR> */
			html = this._getRowsHtml(grid.headersTable().children("thead"),
								"header");
			grid.fixedHeadersTable().children("thead")
				.html(html);
			/*NOTE: most-consuming for rendering is TRs of <tbody />- I have moved it in _renderContainer - performance optimization!!! */
			html = this._getRowsHtml(grid.footersTable().children("tfoot"),
								"footer");
			grid.fixedFootersTable().children("tfoot")
				.html(html);
			if (this.options.syncRowHeights) {
				this._setRowHeights("header");
				this._setRowHeights("body");
				this._setRowHeights("footer");
			}
			/* only for data container */
			grid._fixedTable = fixedBodyTable;
			if (fDir === "right") {
				fixedBodyContainer.css({ "overflow-y": "auto" });
				scrollContainer.css({ "overflow-y": "hidden" });
				this.grid._hscrollbarcontent().css({
					"overflow": "",
					"overflow-y": "hidden"
				});
			}
			this._scrollContainers = {
				fCntnr: fixedBodyContainer,
				ufCntnr: scrollContainer
			};
			this._bindEvents();
			/* compensate float of main fixed container */
			$("<div style=\"clear:both\" id=\"" + gridId + "_floatCompensate\"></div>")
				.insertAfter(grid.container());
		},
		/******** //render main fixed container and its internal containers and tables ********/
		_setRowHeights: function (type) {
			var i, heights = this._heights[ type ], heightsLength = heights.length;

			for (i = 0; i < heightsLength; i++) {
				heights[ i ].tr.style.height = heights[ i ].h + "px";
			}
		},
		_syncRowStyles: function () {
			/* sync styles between fixed and unfixed table body rows */
			/* M.H. 12 Jun 2013 Fix for bug #144399: Deleting a row and fixing a column breaks the zebra style of the records. */
			var i, fRow, ufRow,
				container = this._containers.body,
				$unfixedTable = container.unfixedTable,
				$fixedTable = container.fixedTable,
				fixedRows = $fixedTable.children("tbody").children("tr"),
				unfixedRows = $unfixedTable.children("tbody").children("tr"),
				rLen = fixedRows.length;
			for (i = 0; i < rLen; i++) {
				fRow = fixedRows[ i ];
				ufRow = unfixedRows[ i ];
				fRow.setAttribute("style", ufRow.getAttribute("style"));
				fRow.setAttribute("class", ufRow.getAttribute("class"));
			}
		},
		_populateContainers: function () {
			var gridId = this.grid.id(),
				grid = this.grid,
				self = this,
				virtualization = this._isVirtualGrid(),
				$unfixedHeaders = this.grid.container().find("#" + gridId + "_headers"),
				$unfixedFooters = this.grid.container().find("#" + gridId + "_footer_container"),
				$fixedBodyContainer = this.grid.container().find("#" + gridId + "_fixedBodyContainer"),
				functionPopulateContainers, scrollContainer;
			if (virtualization) {
				scrollContainer = this.grid._vdisplaycontainer();
			} else {
				scrollContainer = this.grid.scrollContainer();
			}
			if (scrollContainer.length === 0) {
				scrollContainer = this.grid.element;
			}
			this._containers = {};
			functionPopulateContainers = function ($unfixedContainer, $fixedContainer, type) {
				var $unfixedTable = $unfixedContainer.find("table"),
					$fixedTable = grid.container().find("#" + $unfixedTable.attr("id") + "_fixed");
				/* M.H. 21 May 2013 Fix for bug #141231: Column fixing breaks columns header with one normal header and other with multiple headers. */
				if ($unfixedTable.length === 0) {
					$unfixedTable = $unfixedContainer;
					$fixedTable = grid.container().find("#" + $unfixedTable.attr("id") + "_fixed");
				}
				self._containers[ type ] = {
					fixedContainer: $fixedContainer,
					unfixedContainer: $unfixedContainer,
					fixedTable: $fixedTable,
					unfixedTable: $unfixedTable
				};
			};
			if ($unfixedHeaders.length > 0) {
				functionPopulateContainers($unfixedHeaders.parent("div"),
									grid.fixedHeaderContainer(), "header");
			}
			functionPopulateContainers(scrollContainer, $fixedBodyContainer, "body");
			if ($unfixedFooters.length > 0 && $unfixedFooters[ 0 ].nodeName !== "TFOOT") {
				/*$fixedFooterContainer = $fixedBodyContainer; */
				functionPopulateContainers($unfixedFooters, grid.fixedFooterContainer(), "footer");
			}
		},
		/* render single header column - it could be MultiColumn or at level 0, -renders all its children */
		_fixUnfixMCHColumn: function (colId, fixingParams, oCols) {
			var i, grid = this.grid, col,
				fixedThead = fixingParams.fixedThead,
				unfixedThead = fixingParams.unfixedThead,
				isToFix = fixingParams.isToFix,
				area = isToFix ? fixedThead : unfixedThead,
				mchInstance = grid.element.data("igGridMultiColumnHeaders");
			oCols = oCols || grid._oldCols;
			if (!mchInstance) {
				return;
			}
			for (i = 0; i < oCols.length; i++) {
				if (oCols[ i ].identifier === colId || oCols[ i ].key === colId) {
					col = oCols[ i ];
					/*col.fixed = isToFix; */
					break;
				}
			}
			if (i === oCols.length) {
				return;
			}
			mchInstance._rows = {};
			mchInstance._analyzeRowspanRows(oCols, 0);
			this._fixUnfixMCHColumnRecursive([ col ], mchInstance._rows, area, isToFix);
		},
		_fixUnfixMCHColumnRecursive: function (cols, rows, area, isToFix) {
			var i, grid = this.grid, $th, id, domLevel, cells, $ths = $(), ind,
				tCell, after = isToFix, col, $targetTh, $tr;
			for (i = 0; i < cols.length; i++) {
				col = cols[ i ];
				if (col.level === 0) {
					id = !i ? col.key : id;
					$th = grid.container().find("#" + this.grid.id() + "_" + col.key);
				} else {
					id = !i ? col.identifier : id;
					$th = this._getTH(col.identifier);
				}
				if (_aNull(domLevel)) {
					domLevel = parseInt($th.closest("tr").attr("data-mch-level"), 10);
				}
				$th.detach();
				$ths = $ths.add($th);// ths for TR
			}
			cells = rows[ domLevel ];//$.grep(rows[domLevel], function (c) { return !!c.fixed === isToFix; }); // cells per row for fixed/unfixed area
			if (cells) {
				$tr = area.find("tr[data-mch-level=" + domLevel + "]");
				if (!$tr.find(">th:not([data-skip])").length ||
					!cells.length) {
					$ths.appendTo($tr);
				} else {
					for (i = 0; i < cells.length; i++) {
						if (cells[ i ].identifier === id || cells[ i ].key === id) {
							ind = i;
							if (isToFix) {
								tCell = i > 0 ? cells[ i - 1 ] : null;
							} else {
								for (i += cols.length; i < cells.length; i++) {
									if (!cells[ i ].fixed) {
										tCell = cells[ i ];
										break;
									}
								}
							}
							for (i = ind; i < ind + cols.length; i++) {
								cells[ i ].fixed = isToFix;
							}
							break;
						}
					}
					if (tCell) {
						$targetTh = this._getTH(_aNull(tCell.key) ? tCell.identifier : tCell.key);
						if (after) {
							$ths.insertAfter($targetTh);
						} else {
							$ths.insertBefore($targetTh);
						}
					} else {
						if (after) {
							$ths.appendTo($tr);
						} else {
							$ths.prependTo($tr);
						}
					}
				}
			}
			for (i = 0; i < cols.length; i++) {
				if (!cols[ i ].group) {
					continue;
				}
				this._fixUnfixMCHColumnRecursive(cols[ i ].group,
												rows,
												area,
												isToFix);
				cols[ i ].fixed = isToFix;
			}
		},
		_dettachHoverEvents: function () {
			this._populateContainers();
			var container = this._containers.body,
				$unfixedTable = container.unfixedTable,
				$fixedTable = container.fixedTable;
			$fixedTable.undelegate("tbody", ".hoverColumnFixing");
			$unfixedTable.undelegate("tbody", ".hoverColumnFixing");
		},
		_attachHoverEvents: function () {
			this._populateContainers();
			var container = this._containers.body,
				$unfixedTable = container.unfixedTable,
				$fixedTable = container.fixedTable;
			this._mouseOverHandler = $.proxy(this._mouseOver, this);
			this._mouseLeaveHandler = $.proxy(this._mouseLeave, this);
			/*this.grid._registerAdditionalEvents(); */
			$fixedTable.delegate("tbody", {
				"mousemove.hoverColumnFixing": this._mouseOverHandler,
				"mouseleave.hoverColumnFixing": this._mouseLeaveHandler
			});
			$unfixedTable.delegate("tbody", {
				"mousemove.hoverColumnFixing": this._mouseOverHandler,
				"mouseleave.hoverColumnFixing": this._mouseLeaveHandler
			});
		},
		_mouseOver: function (event) {
			var css = "ui-state-hover", grid = this.grid,
				$tr = $(event.target).closest("tr");
			if (grid._isFixedElement($tr)) {
				grid._mousemoveTr(grid.element
										.find("tbody tr:nth-child(" + ($tr.index() + 1) + ")")[ 0 ],
								event);
			}
			if (this._hoverTr) {
				this._hoverTr.find("td,th").removeClass(css);
			}
			this._hoverTr = grid.fixedTable()
								.find("tbody")
								.children("tr:nth-child(" + ($tr.index() + 1) + ")");
			this._hoverTr.children("td,th").addClass(css);
		},
		_mouseLeave: function (event) {
			var css = "ui-state-hover", $tr = $(event.target).closest("tr"), grid = this.grid;
			if (grid._isFixedElement($tr)) {
				grid._mouseleaveTr(grid.element
										.find("tbody")
										.children("tr:nth-child(" + ($tr.index() + 1) + ")")[ 0 ],
									event);
			}
			if (this._hoverTr) {
				this._hoverTr.children("td,th").removeClass(css);
			}
		},
		_checkAndRenderHScrlbarCntnr: function () {
			/* test if grid has horizontal scrollbar and renders <DIV> in fixed container with height equal to the height of the horizontal scrollbar in unfixed area. In this way when scroll to the bottom rows of the fixed/unfixed areas are synced */
			var grid = this.grid,
				scroller = grid._hscrollbarcontent(),
				hscrollbar = grid._hscrollbar(),
				fixedControllerScrollerId = grid.id() + "_fixedContainerScroller",
				$fixedScroller = grid.container().find("#" + fixedControllerScrollerId);
			if ($fixedScroller.length === 0 &&//grid.options.height &&
					(this._isContinuousVirtualization() ||
						$.ig.util.hasHorizontalScroll(grid.scrollContainer()) || // it is possible to be rendered (browser)horizontal scrollbar in grid container - e.g. height is not specified
						(scroller.length === 1 && hscrollbar.is(":visible"))
					)
				) {
				/* M.H. 7 Mar 2013 Fix for bug #135253: When width and height are set to the grid, widths to the columns are not set and there is initially fixed column the fixed area has bigger height than the unfixed. */
				$("<div style=\"height:" +
						(hscrollbar.height() || $.ig.util.getScrollHeight()) +
						"px\" id=\"" + fixedControllerScrollerId + "\"></div>")
					.appendTo(this._containers.body.fixedContainer);
			} else if ($fixedScroller.length === 1 && hscrollbar.length === 1) {
				if (hscrollbar.is(":visible")) {
					$fixedScroller.show();
				} else {
					$fixedScroller.hide();
				}
			}
		},
		syncRowsHeights: function ($trs, $anotherRows) {
			/* Syncs rows heights between $rows and $anotherRows
			paramType="array" An array of rows object of the first(fixed/unfixed) container.
			paramType="array" An array of rows of the first(fixed/unfixed) container.
			*/
			var i, len = $trs.length, hToSync, h, heights = [];
			for (i = 0; i < len; i++) {
				h = $.ig.util.isIE ?
						$trs[ i ].getBoundingClientRect().height :
						$trs[ i ].offsetHeight;
				hToSync = $.ig.util.isIE ?
							$anotherRows[ i ].getBoundingClientRect().height :
							$anotherRows[ i ].offsetHeight;
				hToSync = (h > hToSync) ? h : hToSync;
				heights.push(hToSync);
			}
			for (i = 0; i < len; i++) {
				$trs[ i ].style.height = heights[ i ] + "px";
				$anotherRows[ i ].style.height = heights[ i ] + "px";
			}
		},
		_checkSyncTablesHeights: function () {
			if (!this._containers || !this._containers.body) {
				this._populateContainers();
			}
			var h, fH, ufH, $fixedTable = this._containers.body.fixedTable,
				$unfixedTable = this._containers.body.unfixedTable,
				$fTRs = $fixedTable.children("tbody").children("tr"),
				$ufTRs = $unfixedTable.children("tbody").children("tr"),
				$lastTr = $ufTRs.last();
			if ($lastTr.length > 0 &&
				(Math.abs($fixedTable.outerHeight() - $unfixedTable.outerHeight()) > 1 ||
				Math.abs($lastTr.offset().top - $fTRs.last().offset().top) > 1)) {
				this.syncRowsHeights($fTRs, $ufTRs);
				fH = $fixedTable.height();
				ufH = $unfixedTable.height();
				h = fH > ufH ? fH : ufH;
				$fixedTable.height(h);
				$unfixedTable.height(h);
			}
		},
		_syncContainerHeights: function () {
			/* sync heights between fixed and unfixed container(especially for table body) */
			var $fixedTable, $unfixedTable,
				containers = this._containers;

			if (containers && containers.body) {
				$fixedTable = containers.body.fixedTable;
				$unfixedTable = containers.body.unfixedTable;
				if ($fixedTable.height() !== $unfixedTable.height()) {
					$fixedTable.height($unfixedTable.height());
				}
			}
		},
		_adjustVirtWidthOnFixingRight: function (isToFix) {
			if (this.options.fixingDirection !== "right") {
				return;
			}
			var grid = this.grid, w, $tblHeaders, stw,
				$colgroup = grid._virtualcontainer().children("colgroup"),
				$colFixed = $colgroup.children("col[data-fixed-col]"),
				$colUnfixed = $colgroup.children("col:not(:last):not([data-fixed-col])");
			if ($colUnfixed.length) {
				w = $colUnfixed[ 0 ].width;
				this._containers.header.unfixedContainer.width(w);
				this._containers.footer.unfixedContainer.width(w);
			}
			if (grid.options.showHeader && $colFixed.length) {
				this._containers.header
					.fixedContainer
					.width(this._containers.header.fixedContainer.width() +
											grid._scrollbarWidth() + "px");
			}
			if (!isToFix && !grid._fixedColumns.length) {
				$tblHeaders = $("#" + grid.id() + "_headers_v");
				/* M.H. 31 May 2016 Fix for bug 219548: Fixing and unfixing the last column with fixingDirection right and grid width set in percents shrinks the header row. */
				stw = $tblHeaders[ 0 ].style.width;
				if (stw && stw.indexOf("%") === -1) {
					$tblHeaders.width($tblHeaders.width() + grid._scrollbarWidth() + "px");
				}
				$tblHeaders.closest("td").attr("colspan", 2);
			}
			/* M.H. 8 Mar 2016 Fix for bug 215545: Unfixing a column when having fixing direction right and continuous virtualization results in misaligned vertical scrollbar */
			grid._vhorizontalcontainer().css("overflow-y", "hidden");
			this._updateHScrollbarTblWidthsInFixedVirtRight();
		},
		_updateFixedColgroupVirt: function (colWidthDelta) {
			var w, $colFixed, $colUnfixed, $colgroup;
			$colgroup = this.grid._virtualcontainer().children("colgroup");
			$colFixed = $colgroup.children("col[data-fixed-col]");
			$colUnfixed = $colgroup.children("col:not(:last):not([data-fixed-col])");
			w = parseInt($colFixed.attr("width"), 10);
			$colFixed.attr("width", w + colWidthDelta);
			w = parseInt($colUnfixed.attr("width"), 10);
			$colUnfixed.attr("width", w - colWidthDelta);
		},
		getWidthOfFixedColumns: function (fCols, excludeNonDataColumns, includeHidden) {
			/* Calculates width of the fixed columns.
			paramType="array" optional="true" Array of grid columns. If not set then it is taken fixed columns of the grid.
			paramType="bool" optional="true" If set to true do not calculate the width of non-data fixed columns(like row-selectors)
			paramType="bool" optional="true" If set to true calculates width of the hidden fixed columns(as getting their initial width)
			returnType="number" Returns total width of the fixed columns
			*/
			var $fTable, w = 0, i;
			if (!this._containers || !this._containers.body) {
				this._populateContainers();
			}
			$fTable = this._containers.body.fixedTable;
			/* get width of data-skip fixed columns */
			if (excludeNonDataColumns) {
				$fTable.children("colgroup").children("col[data-skip]")
					.each(function () {
						w += parseInt($(this).css("width"), 10);
					});
			}
			fCols = fCols || this.grid._fixedColumns;
			if (!fCols || !fCols.length) {
				return w;
			}
			for (i = 0; i < fCols.length; i++) {
				if (!includeHidden && fCols[ i ].hidden) {
					continue;
				}
				w += parseFloat(fCols[ i ].width);
			}
			return w;
		},
		_updateHScrollbarWidthVirt: function () {
			var grid = this.grid, $hScrlbar,
				scrollbarWidth = grid._scrollbarWidth(),
				$hScrollerContainer = grid._vhorizontalcontainer(),
				fixedTableWidth = parseInt(this._containers.body.fixedTable[ 0 ].style.width, 10),
				fdirLeft = this.options.fixingDirection === "left",
				gridWidth = parseInt(grid.options.width, 10);
			if (grid._outerHScrollbar) {
				$hScrlbar = grid._hscrollbar();
				if (this.options.fixingDirection === "left") {
					$hScrlbar.css("margin-left", fixedTableWidth);
				} else {
					$hScrlbar.css("margin-right",
								fixedTableWidth + scrollbarWidth);
				}
				/*fW = parseFloat(grid._virtualcontainer().children('colgroup').children('col[data-fixed-col]').css('width')); */
				if (grid._gridHasWidthInPercent()) {
					grid._updateGridContentWidth();
					grid._updateVirtualHorizontalScrollbar();
				}
				return;
			}
			if (this._isContinuousVirtualization()) {
				/*all columns are unfixed */
				if (fixedTableWidth === 0) {
					$hScrollerContainer.css({
						position: ""
						/*left: '' */
					});
				} else if (fdirLeft) {
					$hScrollerContainer.css({
						/*left: fixedTableWidth + 'px', */
						position: "relative"
					});
				}
				/* M.H. 8 Mar 2016 Fix for bug 215545: Unfixing a column when having fixing direction right and continuous virtualization results in misaligned vertical scrollbar */
				if (!fdirLeft) {
					gridWidth -= scrollbarWidth;
				}
				$hScrollerContainer.width((gridWidth - fixedTableWidth) + "px");
				grid._updateVirtualHorizontalScrollbar();
			}
		},
		_updateHScrollbarWidth: function (delta) {
			if (this._isVirtualGrid()) {
				return this._updateHScrollbarWidthVirt(delta);
			}
			var scrollContainer, grid = this.grid, $hScrollerContainer, scW,
				fdirLeft = this.options.fixingDirection === "left",
				oTableWidth = this._containers.body.unfixedTable.outerWidth();
			scrollContainer = this._containers.body.unfixedContainer;
			if (oTableWidth > 0) {
				/* M.H. 27 Feb 2013 Fix for bug #133875: The records in fixed column are not synchronized with the unfixed columns when the grid is vertically scrolled. */
				$hScrollerContainer = grid._hscrollbar();
				/* M.H. 19 August 2015 Fix for bug 204865: Horizontal scrollbar is not displayed if columns are fixed and the grid is inside a tile of TileManager. */
				if (scrollContainer.is(":visible")) {
					scW = scrollContainer.width();
				} else {
					scW = parseInt(grid.options.width, 10) - this.getWidthOfFixedColumns();
				}
				/* M.H. 18 Aug 2014 Fix for bug #178044: Changing the grid width at runtime breaks the scrollbars of ColumnFixing */
				$hScrollerContainer.css({
					/*width:  scW + 'px', */
					left: 0
				});
				/* M.H. 11 Apr 2016 Fix for bug 216610: It is not possible to scroll using the swipe action on iPad when there are fixed columns */
				if ($.ig.util.isTouch && this.element.igScroll !== undefined) {
					$hScrollerContainer.width(scW);
				}
				/* M.H. 21 Feb 2013 Fix for bug #133521: When you fix and unfix a column the width of the columns changes and scrolling the grid to the right misaligns it. */
				grid._hscrollbarinner().css({
					width: (grid._hasVerticalScrollbar && grid.options.fixedHeaders && fdirLeft ?
										oTableWidth - grid._scrollbarWidth() :
										oTableWidth) + "px",
					left: 0
				});
			}
		},
		_headerRendered: function (sender, args) {
			/*prevent handling of other grids' events in the case of hierarchical grid */
			if (args.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			if (this.options.showFixButtons === false) {
				return;
			}
			var i, j, cs, columnKey, ths, children,
				isFixed = false,
				allowFixing = true,
				grid = this.grid,
				self = this,
				cols = grid.options.columns,
				colsLength = cols.length;

			if (grid._isMultiColumnGrid) {
				ths = grid.headersTable()
					.children("thead")
					.children("tr[data-mch-level=" + grid._maxLevel + "]").children("th");
				ths.each(function () {
					var $th = $(this);
					if ($th.attr("data-mch-id")) {
						/* M.H. 10 Dec 2013 Fix for bug #158504: ColumnFixing combined with MultiColumnHeaders, the main columns can be fixed/unfixed with allowFixing set to false */
						columnKey = $th.attr("data-mch-id");
						allowFixing = true;
						/* M.H. 6 Feb 2014 Fix for bug #159858: ColumnFixing combined with MultiColumnHeaders, the main column fixing/unfixing options are overriden by the default options of the nested columns */
						cs = self._getColumnSettingByKey(columnKey);
						if (cs && cs.allowFixing === false) {
							return true;// continue
						}
						for (j = 0; j < grid._oldCols.length; j++) {
							if (grid._oldCols[ j ].identifier === columnKey) {
								children = grid._oldCols[ j ].children;
								for (i = 0; i < children.length; i++) {
									cs = self._getColumnSettingByKey(children[ i ].key, i);
									if (cs && cs.allowFixing === false) {
										allowFixing = false;
										break;
									}
								}
								break;
							}
						}
						if (!allowFixing) {
							return true;
						}
						self._renderHeaderCellButton(columnKey, isFixed, true, $th);
					} else {
						if ($th.attr("data-skip")) {
							return true;
						}
						columnKey = $th.attr("id").replace(grid.id() + "_", "");
						/* M.H. 10 Dec 2013 Fix for bug #158504: ColumnFixing combined with MultiColumnHeaders, the main columns can be fixed/unfixed with allowFixing set to false */
						cs = self._getColumnSettingByKey(columnKey);
						if (cs && cs.allowFixing === false) {
							return true;
						}
						if (self._fcData[ columnKey ] !== true) {
							self._renderHeaderCellButton(columnKey, isFixed, false, $th);
						}
					}
				});
			} else {
				for (i = 0; i < colsLength; i++) {
					columnKey = cols[ i ].key;
					cs = this._getColumnSettingByKey(columnKey, i);
					isFixed = false;
					if (cs !== null) {
						/* M.H. 4 Mar 2013 Fix for bug #134230: ColumnSettings do not work with columnIndex. */
						if (cs.allowFixing === false) {
							continue;
						}
						if (cs.isFixed === true) {
							isFixed = true;
						}
					}
					if (this._fcData[ columnKey ] !== true ) {
						this._renderHeaderCellButton(columnKey, isFixed);
					}
				}
			}
		},
		_headerRendering: function () {
			var i;
			for (i = 0; i < this.grid.options.features.length; i++) {
				if (this.grid.options.features[ i ].name === "Hiding") {
					this._hiding = this.grid.element.data("igGridHiding");
					break;
				}
			}
		},
		_columnsMoved: function (e, ui) {
			var start = ui.start, len = ui.len, idx = ui.index;
			if (!ui.isFixed) {
				return;
			}
			this.grid._rearrangeArray(this.grid._fixedColumns, start, len, idx);
		},
		_getColumnSettingByKey: function (key, colIndex) {
			var i, cs = this.options.columnSettings, csLength = cs.length, res = null;

			for (i = 0; i < csLength; i++) {
				if (cs[ i ].columnKey !== null && cs[ i ].columnKey !== undefined) {
					if (cs[ i ].columnKey === key) {
						res = cs[ i ];
						break;
					}
				} else if (cs[ i ].columnIndex !== null && cs[ i ].columnIndex !== undefined) {
					if (cs[ i ].columnIndex === colIndex) {
						res = cs[ i ];
						break;
					}
				}
			}
			return res;
		},
		_id: function () {
			var i, res = this.grid.id(), argumentsLength = arguments.length;

			if (argumentsLength === 0) {
				return null;
			}

			for (i = 0; i < argumentsLength; i++) {
				res += "_" + arguments[ i ];
			}

			return res;
		},
		_renderHeaderCellButton: function (columnKey, isFixed, isGroupHeader, $th) {
			/* M.H. 25 Feb 2013 Fix for bug #133865: The filtering dropdown can't be opened for fixed column. */
			var self = this,
				css = self.css,
				buttonId,
				gridId = this.grid.id(),
				$button,
				$divHeaderButtonContainer,
				$columnFixingHeaderIconContainer;

			if ($th === undefined) {
				$th = this.grid.container().find("#" + gridId + "_" + columnKey);
			}
			buttonId = this._id("header_cell", "fixing", columnKey);
			if ($th.length === 0) {
				return;
			}
			this.grid._enableHeaderCellFeature($th);
			$columnFixingHeaderIconContainer = $th.find(".ui-iggrid-indicatorcontainer");
			if ($columnFixingHeaderIconContainer.length === 0) {
				$columnFixingHeaderIconContainer = $("<div class=\"ui-iggrid-indicatorcontainer\"></div>")
													.appendTo($th);
			}
			$button = this.grid.container().find("#" + buttonId);
			if ($button.length === 0) {
				/* M.H. 31 Oct. 2011 Fix for bug 94362 */
				$button = $("<a></a>")
					.attr("href", "#")
					/* M.H. 27 Feb 2013 Fix for bug #134339: The destroy method of columnFixing does not work. */
					.attr("data-fixing-indicator", "true")
					.attr("id", buttonId);
				/* M.H. 31 Oct. 2011 Fix for bug 94362 */
				/* M.H. 22 Feb 2013 Fix for bug #133719: When you fix and unfix columns some extra divs are added in the container of the fixing icon. */
				$divHeaderButtonContainer = $columnFixingHeaderIconContainer
											.find(".ui-iggrid-fixcolumn-headerbuttoncontainer");
				if ($divHeaderButtonContainer.length === 0) {
					$divHeaderButtonContainer = $("<div></div>")
												.addClass(css.headerButtonIconContainer)
												.appendTo($columnFixingHeaderIconContainer);
				}
				$button.appendTo($divHeaderButtonContainer);
				$("<span></span>")
					.appendTo($button);
				$button.bind({
					mousedown: function () {
						$(this).trigger("mouseout");
					},
					click: function (event) {
						event.preventDefault();
						event.stopPropagation();
						if ($button.attr("data-fixed") === "true") {
							self._unfixColumnInternal(columnKey);
						} else {
							self._fixColumnInternal(columnKey);
						}
					}
				});
			}
			this._changeStyleHeaderButton(columnKey, isFixed);
		},
		_getTH: function (id) {
			return this.grid.container()
				.find("th[data-mch-id=\"" +
							id + "\"],th[id=\"" +
							this.grid.id() + "_" + id + "\"]");
		},
		_changeStyleHeaderButton: function (columnKey, isFixed) {
			var css = this.css, fc,
				attrVal = "true", title = this.options.headerFixButtonText,
				$button = this.grid.container().find("#" + this._id("header_cell", "fixing", columnKey)),
				$span;
			$span = $button.find("span");
			if (isFixed) {
				$span.removeClass(css.headerButtonIcon);
				$span.addClass(css.headerButtonUnfixIcon);
				title = this.options.headerUnfixButtonText;
			} else {
				attrVal = "false";
				$span.removeClass(css.headerButtonUnfixIcon);
				$span.addClass(css.headerButtonIcon);
			}
			$button.attr("data-fixed", attrVal).attr("title", title);
			/* M.H. 8 Jan 2014 Fix for bug #161032: The fixing icon in the feature chooser does not change when you fix a column with API method. */
			fc = this.grid.element.data("igGridFeatureChooser");
			if (fc) {
				fc._setSelectedState("ColumnFixing", columnKey, isFixed, false);
			}
		},
		_dataRendering: function (event, ui) {
			if (ui === undefined) {
				return;
			}
			if (this.grid.id() !== ui.owner.id()) {
				return;
			}
			if (this.grid.options.height === null  && this.grid.hasFixedColumns() &&
					$.ig.util.isIE && $.ig.util.browserVersion >= 9) {
				$("#" + this.grid.id() + "_fixed").height("");
			}
		},
		_dataRendered: function (event, ui) {
			if (ui === undefined || this.grid.id() !== ui.owner.id()) {
				return;
			}
			var grid = this.grid;
			if (grid.hasFixedColumns()) {
				if (!grid._initialized) {
					grid._updateVerticalScrollbarCellPadding();
				}
				if (this.options.syncRowHeights) {
					this.checkAndSyncHeights();
					if (this._isVirtualGrid() &&
						grid._virtualcontainer().outerHeight() > parseInt(grid.options.height)) {
						grid._initializeHeights();
					}
				}
			}
		},
		_gridContainersRendered: function () {//grid containers rendered on INIT - called before DOM rendered
			/* apply initial column fixing */
			var i, j, cs = this.options.columnSettings, csLength = cs.length,
				columnKeys = [], hasColumnKey, hasColumnIndex, res,
				cols = this.grid.options.columns, col,
				countHidden = 0,
				colsLength = cols.length;
			for (i = 0; i < csLength; i++) {
				if (cs[ i ].isFixed !== true) {
					continue;
				}
				hasColumnKey = (cs[ i ].columnKey !== null && cs[ i ].columnKey !== undefined);
				hasColumnIndex = (cs[ i ].columnIndex !== null && cs[ i ].columnIndex !== undefined);
				if (!hasColumnKey) {
					if (!hasColumnIndex) {
						continue;
					}
					/* M.H. 4 Mar 2013 Fix for bug #134230: ColumnSettings do not work with columnIndex. */
					if (cs[ i ].columnIndex >= 0 && cs[ i ].columnIndex < colsLength) {
						columnKeys.push(cols[ cs[ i ].columnIndex ].key);
						if (cols[ cs[ i ].columnIndex ].hidden) {
							countHidden++;
					}
					}
				} else {
					columnKeys.push(cs[ i ].columnKey);
					col = this.grid.columnByKey(cs[ i ].columnKey);
					if (col && col.hidden) {
						countHidden++;
					}
				}
			}
			if (countHidden === columnKeys.length && countHidden > 0) {
				this._trigger(this.events.columnFixingRefused, null, {
					"columnIdentifier": columnKeys,
					owner: this.grid
				});
				return;
			}
			/* M.H. 4 Mar 2013 Fix for bug #134230: ColumnSettings do not work with columnIndex. */
			for (j = 0; j < columnKeys.length; j++) {
				res = this.fixColumn(columnKeys[ j ]);
				if (!res.result) {
					throw new Error(
						$.ig.util.stringFormat($.ig.ColumnFixing.locale.initialFixingNotApplied,
							columnKeys[ j ], res.error));
				}
			}
			if (this.options.fixNondataColumns) {
				this.fixDataSkippedColumns();
			}
		},
		_detachEvents: function () {
			if (this._headerRenderedHandler) {
				this.grid.element.unbind("iggridheaderrendered", this._headerRenderedHandler);
			}
			if (this._headerCellRenderedHandler) {
				this.grid.element.unbind("iggridheadercellrendered", this._headerCellRenderedHandler);
			}
			if (this._headerRenderingHandler) {
				this.grid.element.unbind("iggridheaderrendering", this._headerRenderingHandler);
			}
			if (this._columnsMovedHandler) {
				this.grid.element.unbind("iggrid_columnsmoved", this._columnsMovedHandler);
			}
			/* M.H. 7 Mar 2014 Fix for bug #147490: The "Search Result" span is relocated when column is fixed and you filter the grid in Chrome. */
			if (this._gridHeightChangingHandler) {
				this.grid.element.unbind("iggrid_heightchanging", this._gridHeightChangingHandler);
			}
			if (this._dataRenderingHandler) {
				this.grid.element.unbind("iggriddatarendering", this._dataRenderingHandler);
			}
			if (this._dataRenderedHandler) {
				this.grid.element.unbind("iggriddatarendered", this._dataRenderedHandler);
			}
			if (this._virtualrecordsrenderHandler) {
				this.grid.element.unbind("iggridvirtualrecordsrender", this._virtualrecordsrenderHandler);
			}
			if (this._gridContainerHeightHandler) {
				this.grid.element.unbind("iggrid_heightchanged", this._gridContainerHeightHandler);
			}
			if (this._gridContainersRenderedHandler) {
				this.grid.element.unbind("iggrid_gridcontainersrendered", this._gridContainersRenderedHandler);
			}
		/* M.H. 6 Jun 2016 Fix for bug 220191: When rowselectors are enabled and grid width is greater than sum of column widths and autofitLastColumn is true horizontal scrollbar is shown */
			if (this._lastColumnWidthAutoAdjustedHandler) {
				this.grid.element.unbind("iggrid_lastcolumnwidthautoadjusted",
						this._lastColumnWidthAutoAdjustedHandler);
			}
		},
		destroy: function () {
			/* destroys the columnfixing widget */
			var fc;
			if (this.grid._fixedColumns && this.grid._fixedColumns.length > 0) {
				this.unfixAllColumns();
			}
			if (this.grid.hasFixedDataSkippedColumns()) {
				this.unfixNonDataColumns();
			}
			this.grid._hasFixedDataSkippedColumns = false;
			this.element.data("fixingApplied", false);
			/* M.H. 27 Feb 2013 Fix for bug #134339: The destroy method of columnFixing does not work. */
			this.grid.headersTable()
				.find("thead > tr > th")
				.not("[data-skip=true]")
				.each(function () {
					var th = $(this);
					th.find("a[data-fixing-indicator=true]").parent().remove();
				});
			this._detachEvents();
			if (this._gridRenderRowHandler !== undefined) {
				this.grid._renderRow = this._gridRenderRowHandler;
			}
			if (this._gridRenderRecordsForTableHandler) {
				this.grid._renderRecordsForTable = this._gridRenderRecordsForTableHandler;
			}
			if (this._gridRenderNewRowHandler) {
				this.grid.renderNewRow = this._gridRenderNewRowHandler;
			}
			if (this._gridDetachColumnHandler) {
				this.grid._detachColumn = this._gridDetachColumnHandler;
			}
			if (this._gridAttachColumnHandler) {
				this.grid._attachColumn = this._gridAttachColumnHandler;
			}
			if (this._gridRerenderColgroupsHandler) {
				this.grid._rerenderColgroups = this._gridRerenderColgroupsHandler;
			}
			if (this._gridRenderColgroupHandler) {
				this.grid._renderColgroup = this._gridRenderColgroupHandler;
			}
			if (this._gridUpdatePaddingHandler) {
				this.grid._updateVScrollbarCellPaddingHelper = this._gridUpdatePaddingHandler;
			}
			fc = this.grid.element.data("igGridFeatureChooser");
			if (fc && this.renderInFeatureChooser) {
				fc._removeFeature("ColumnFixing");
			}
			this._unregisterSetOptionCallback();
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		_headerCellRendered: function (event, ui) {
			/* check for which cells should be rendered in feature chooser
			*/
			/*prevent handling of other grids' events in the case of hierarchical grid */
			if (ui.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			if (this._isInitFC !== true) {
				this._initFC();
			}
		},
		_setFixedBodyCntnrHeight: function (scrollContainer, fixedBodyContainer) {
			if (!this._containers || !this._containers.body) {
				this._populateContainers();
			}
			var grid = this.grid, hScrlbar, scrollContainerHeight, wperc, v;
			fixedBodyContainer = fixedBodyContainer || this._containers.body.fixedContainer;
			scrollContainer = scrollContainer || this._containers.body.unfixedContainer;
			/* M.H. 26 Feb 2013 Fix for bug #133862: When height is not defined and column is fixed filtering a column makes the footer too big and the grid keeps its original height. */
			if (grid.options.height !== null) {
				/*$mainFixedContainer.css('max-height', grid.options.height); */
				scrollContainerHeight = scrollContainer.height();
				hScrlbar = grid._hscrollbar();
				wperc = grid._gridHasWidthInPercent();
				v = this._isVirtualGrid();
				/* M.H. 7 Mar 2013 Fix for bug #135253: When width and height are set to the grid, widths to the columns are not set and there is initially fixed column the fixed area has bigger height than the unfixed. */
				/* M.H. 3 Jun 2016 Fix for bug 220151: Horizontal scroll container is not at proper position when there is initially fixed columns and grid width is set in % */
				if (hScrlbar.is(":visible") &&
					(!v ||
						(!wperc && v && this._isContinuousVirtualization()))) { // it is not valid for fixed virtualization
					scrollContainerHeight += hScrlbar.outerHeight();
				}
			}
			fixedBodyContainer.height(scrollContainerHeight);
		},
		_summariesHeightsAdjusting: function () {
			/* internal event triggered by summaries */
			if (!this.grid._initialized) {
				return;
			}
			var $fTbl, $ufTbl, grid = this.grid;
			if (!this._containers || !this._containers.footer) {
				this._populateContainers();
			}
			if (grid.options.showFooter && grid.options.fixedFooters) {
				$fTbl = this._containers.footer.fixedTable;
				$ufTbl = this._containers.footer.unfixedTable;
				this._syncHeightsForTables($fTbl, $ufTbl);
			}
		},
		_heightChanged: function () {
			var grid = this.grid;
			/* M.H. 20 August 2015 Fix for bug 204979: Grid with column fixing, horizontal and vertical scrollbars is throwing an error */
			if (!grid.hasFixedColumns()) {
				return;
			}
			this._syncTableHeights();
			this._setFixedBodyCntnrHeight();
			/* M.H. 19 August 2015 Fix for bug 204865: Horizontal scrollbar is not displayed if columns are fixed and the grid is inside a tile of TileManager. */
			this._checkAndRenderHScrlbarCntnr();
			if (this.options.syncRowHeights) {
				this.checkAndSyncHeights();
			}
		},
		/* M.H. 6 Jun 2016 Fix for bug 220191: When rowselectors are enabled and grid width is greater than sum of column widths and autofitLastColumn is true horizontal scrollbar is shown */
		_lastColumnWidthAutoAdjusted: function (e, args) {
			if (this.grid.hasFixedColumns()) {
				/* M.H. 7 Jun 2016 Fix for bug 220336: There is blank space for horizontal scrollbar when there are fixed columns and fixing direction is right */
				if (this.options.fixingDirection === "right" && !this.grid._initialized) {
					this.grid._hscrollbarcontent().css("overflow-y", "hidden");
					this._updateHScrollbarWidth();
				}
				if (args && args.specialColumnsWidth) {
					this._recalcTableWidths();
					this._updateHScrollbarWidth();
					this._updateGridWidth();
					this.grid._updateHScrollbarVisibility();
				}
			}
		},
			/* M.H. 7 Mar 2014 Fix for bug #147490: The "Search Result" span is relocated when column is fixed and you filter the grid in Chrome. */
		_gridHeightChanging: function (e, arg) {
			if (!this._containers.body) {
				return;
			}
			if (this._isVirtualGrid()) {
				return;
			}
			var scrollContainerHeight, diff,
				pc = this.grid._prevContainerHeight,
				$fixedBodyContainer = this.grid.fixedBodyContainer();
			if ($fixedBodyContainer.length === 0) {
				return;
			}
			scrollContainerHeight = arg.ch - arg.h;//this._containers.body.unfixedContainer.outerHeight();
			if (this.grid._hscrollbar().is(":visible")) {
				scrollContainerHeight += this.grid._hscrollbar().outerHeight();
			}
				/*if ($fixedBodyContainer.outerHeight() !== parseInt(scrollContainerHeight)) { */
				/* M.H. 21 May 2013 Fix for bug #141227: Scrollbar is in wrong place when Grid's height set to 100%. */
				$fixedBodyContainer.height(scrollContainerHeight);
				diff = Math.abs(pc - this.grid.container().height());
				$fixedBodyContainer.height(scrollContainerHeight - diff);
				/* M.H. 23 Aug 2013 Fix for bug #147490: The "Search Result" span is relocated when column is fixed and you filter the grid in Chrome. */
				/*this._fixResultContainer(); */
				},
			_initFC: function () {
				/* instantiate feature chooser */
				/* check whether feature chooser is defined */
				var i, fc,
					isMCH = this.grid._isMultiColumnGrid,
					o = this.options,
					cols = this.grid.options.columns,
					colsLength = cols.length,
					cs,
					columnKey;

			this._isInitFC = true;
				/* instantiate igGridFeatureChooser = if it is defined */
				fc = this.grid.element.data("igGridFeatureChooser");
				/* M.H. 30 Mar 2015 Fix for bug 186411: Fix button should not be shown in the feature chooser when showFixButtons is false */
				if (fc !== null && fc !== undefined &&
					this.renderInFeatureChooser && o.showFixButtons) {
					if (isMCH) {
					cols = this.grid._oldCols;
					colsLength = cols.length;
				}
				for (i = 0; i < colsLength; i++) {
					columnKey = cols[ i ].key;
					this._fcData[ columnKey ] = false;
					/* M.H. 11 Jun 2013 Fix for bug #144394: When filtering is enabled the fixing icon is rendered next to feature chooser. */
					if (isMCH && cols[ i ].level !== 0) {
						continue;
				}
					cs = this._getColumnSettingByKey(columnKey, i);
					if (cs && cs.allowFixing === false) {
						continue;
				}
					if (fc._shouldRenderInFeatureChooser(columnKey) === true) {
						fc._renderInFeatureChooser(columnKey,
							{
									name: "ColumnFixing",
									text: o.featureChooserTextUnfixedColumn,
									textHide: o.featureChooserTextFixedColumn,
									iconClass: this.css.featureChooserIconClassFixed,
									iconClassOff: this.css.featureChooserIconClassUnfixed,
								/*secondaryIconClass: this.css.featureChooserIconClassUnfixed, */
								/* M.H. 10 Jun 2013 Fix for bug #140584: When the column is initially fixed column the text in the feature chooser is not correct. */
									isSelected: (cs && cs.isFixed === true),
									method: $.proxy(this._togglefromfc, this),
									updateOnClickAll: false,
								order: 3, // order in group
									groupName: "toggle",
								groupOrder: 1,
									type: "toggle",
									state: "hide"
						}
							);
						/*fc._setSelectedState('Summaries', columnKey, false, false); */
						this._fcData[ columnKey ] = true;
				}
				}
			}
			},
			_togglefromfc: function (event, columnKey) {
				var i, cols = this.grid.options.columns, colsLength = cols.length, col, ret;

			for (i = 0; i < colsLength; i++) {
				col = cols[ i ];
				if (col.key === columnKey) {
					if (col.fixed === true) {
						ret = this._unfixColumnInternal(columnKey);
					} else {
						ret = this._fixColumnInternal(columnKey);
				}
					break;
}
			}
				/* M.H. 10 Jun 2013 Fix for bug #144214: The "Fix Column" option changes even when column fixing is refused. */
				return (ret.error === undefined);
				},
			_columnMap: function () {
				var i, j, isMCH = this.grid._isMultiColumnGrid, cs, columnKey,
					elem, result = [], cols = this.grid.options.columns,
					colsLength = cols.length;

			if (this.options.showFixButtons === false) {
				return false;
			}
			for (i = 0; i < colsLength; i++) {
				columnKey = cols[ i ].key;
				elem = { columnKey: columnKey, enabled: true };
				cs = this._getColumnSettingByKey(columnKey, i);
				if (cs && cs.allowFixing === false) {
					elem.enabled = false;
				} else if (isMCH) {
					for (j = 0; j < this.grid._oldCols.length; j++) {
						if (this.grid._oldCols[ j ].key === columnKey) {
							break;
					}
				}
					if (j === this.grid._oldCols.length) {
						elem.enabled = false;
				}
			}
			result.push(elem);
			}
			return result;
			},
			/* Overriden functions */
			_cleanupTBody: function () {
				/* fired from grid framework(via fireInternalEvent when cleaning TBody of unfixed table) - see bug 215748 */
				/* M.H. 11 Mar 2016 Fix for bug 215748: Grid with ColumFixing, virtualization:continuous and Filtering throws the script error "Uncaught TypeError: Cannot read property 'offsetHeight' of undefined"  when filtering results is zero. */
				if (this.grid.hasFixedColumns()) {
					if (!this._containers || !this._containers.body) {
					this._populateContainers();
				}
				this._containers.body.fixedTable.children("tbody").empty();
			}
			},
			_renderRow: function (rec, tr, rowId) {
				/* overriding grid _renderRow */
				if (!this.grid.hasFixedColumns()) {
					return this._gridRenderRowHandler(rec, tr, rowId);
			}
			var i, col, content, $fixedRow, $unfixedRow, fixedCells, unfixedCells,
				counterFixed = 0, counterUnfixed = 0, $td,
				$tr = $(tr), grid = this.grid,
				cols = grid.options.columns, colsLength = cols.length,
				isFixedRow = this.grid._isFixedElement($tr);

			if (isFixedRow) {
				$fixedRow = $tr;
				$unfixedRow = grid.element.find("tbody tr:nth-child(" + ($tr.index() + 1) + ")");
} else {
	$unfixedRow = $tr;
	$fixedRow = this.grid.fixedTable().find("tbody tr:nth-child(" + ($tr.index() + 1) + ")");
			}
			fixedCells = $fixedRow.find(">td:not([data-skip])");
			unfixedCells = $unfixedRow.find(">td:not([data-skip])");
				/* render fixed records */
				for (i = 0; i < colsLength; i++) {
					col = cols[ i ];
					if (col.hidden === true) {
						continue;
				}
				if (col.fixed === true) {
					$td = fixedCells.eq(counterFixed++);
				} else {
					$td = unfixedCells.eq(counterUnfixed++);
				}
				if (col.template && col.template.length) {
					content = grid._renderTemplatedCell(rec, col);
					if (content.indexOf("<td") === 0) {
						$td.html($(content).html());
					} else {
					$td.html(content);
				}
				} else {
					$td.html(String(grid._renderCell(rec[ col.key ], col, rec)));
				}
			}
				return tr;
				},
			_renderRecordsForTable: function (start, end/*, table, tbody, isFixed*/) {
				var $fTable;
				if (this.grid.hasFixedColumns()) {
					$fTable = this._containers.body.fixedTable;
					if ($fTable.length) {
					this._gridRenderRecordsForTableHandler.call(this.grid,
							start, end, $fTable, $fTable.children("tbody"), true);
				}
			}
			this._gridRenderRecordsForTableHandler.apply(this.grid, arguments);
			if ($fTable &&
					this.options.syncRowHeights && this._applySyncRowHeights !== false) {
				this._syncHeightsForTables($fTable, this.grid.element);
			}
			},
			_renderNewRow: function (rec) {//, key
				var grid = this.grid,
					tbody = this.grid.element.children("tbody"),
					index = tbody.children("[data-container!=\"true\"]").length,
					virt = (grid.options.virtualization === true || grid.options.rowVirtualization === true);

			this._gridRenderNewRowHandler.apply(this.grid, arguments);
			if (!virt) {
				if (grid.hasFixedColumns()) {
					MSApp.execUnsafeLocalFunction(function () {
						grid.fixedTable().children("tbody").append(grid._renderRecord(rec, index, true));
			});
			}
			}
			},
			_updateVScrollbarCellPaddingHelper: function (paddingIncrement, skipHeaderFooters) {
				if (!this.grid.hasFixedColumns()) {
					this._gridUpdatePaddingHandler(paddingIncrement, skipHeaderFooters);
					return;
			}
			var grid = this.grid, gridOpts = grid.options, fTable, hTable, container,
				hasFixedHeaders = gridOpts.showHeader &&
									gridOpts.fixedHeaders === true &&
									gridOpts.height !== null,
				hasFixedFooters = gridOpts.showFooter &&
									gridOpts.fixedFooters === true &&
									gridOpts.height !== null;
				if (hasFixedHeaders && !skipHeaderFooters) {
					hTable = grid.headersTable();
										/* remove cell padding for fixed thead cells(not only for the last) */
					grid._removeHeaderCellPadding(grid.fixedHeadersTable(), true);
			}
			if (hasFixedFooters && !skipHeaderFooters) {
					fTable = grid.footersTable();
				/* remove cell padding for fixed tfoot cells(not only for the last) */
					grid._removeCellPadding(grid.fixedFootersTable(), "tfoot", "td", true);
			}
				/* remove cell padding for fixed tbody cells(not only for the last) */
				grid._removeCellPadding(grid.fixedBodyContainer(), "tbody", "td", true);
				container = this.element;
			if (hasFixedHeaders && !skipHeaderFooters) {
				grid._increaseLastHeaderCellVScrollbarPadding(hTable, paddingIncrement);
			}
			if (hasFixedFooters && !skipHeaderFooters) {
				grid._increaseLastCellVScrollbarPadding(fTable, "tfoot", "td", paddingIncrement);
			}
			grid._increaseLastCellVScrollbarPadding(container, "tbody", "td", paddingIncrement);
			},
			/* // Override functions */
			_syncHeightsForTables: function ($fixedTable, $unfixedTable, resetHeights) {
				var h, fH, ufH;
				/* M.H. 14 Jun 2013 Fix for bug #144693: When you fix a column and then filter a column the records increase their height. */
				/* in IE9+ there is misalignment between fixed and unfixed records because rendering engine of IE does not render properly records when height is epxlicitly set of table rows */
				/* to fix this we should set height of fixed/unfixed table */
				/* when filtering and there is only one row to show then row gets the height of the whole fixed area when height is explicitly set for the table */
				resetHeights = resetHeights ||
								($unfixedTable.length && $unfixedTable[ 0 ].style.height !== "");
				$fixedTable.css("height", "");
				$unfixedTable.css("height", "");
				if (this.options.syncRowHeights) {
					this.syncRowsHeights($fixedTable.children("tbody").children("tr"),
										$unfixedTable.children("tbody").children("tr"));
			}
			if (resetHeights) {
				fH = $fixedTable.height();
				ufH = $unfixedTable.height();
				h = fH > ufH ? fH : ufH;
				$fixedTable.height(h);
				$unfixedTable.height(h);
			}
			},
			/* M.H. 5 Mar 2013 Fix for bug #134687: Sorted style is removed the first time you fix a sorted column. */
			_detachColumn: function (col) {
				if (!this.grid.hasFixedColumns()) {
					return this._gridDetachColumnHandler(col);
			}
var pos, fixed, headerCells, grid = this.grid, $tbl, footerCells;
	fixed = col.fixed;
	col.hidden = false;
	grid._visibleColumnsArray = undefined;
				/* we need to get total position of header cells(no matter fixed/unfixed) - position according to this._headerCells(in case of MCH) */
			pos = grid.getVisibleIndexByKey(col.key);
				col.hidden = true;
				grid._visibleColumnsArray = undefined;
				grid._initializeDetachedContainers();
				/* detach thead cells */
				if (grid._isMultiColumnGrid) {
					grid._hideMultiHeaderCells(grid._headerCells, col.key);
			}
	$tbl = fixed ? grid.fixedHeadersTable() : grid.headersTable();
	headerCells = grid._isMultiColumnGrid ?
							$tbl.children("thead").children("tr:not([data-mch-level])").not("[data-skip=true]") :
							$tbl.children("thead").children("tr").not("[data-skip=true]");
					grid._detachCells(
						headerCells,
						function (row) {
						return row.filter(":not([data-new-row],[data-add-row])")
							.children("th, td").not("[data-skip=true]");
					},
				pos,
						grid._detachedHeaderCells,
						col.key
						);
				/* detach tfoot cells */
				$tbl = fixed ? grid.fixedFootersTable() : grid.footersTable();
				footerCells = $tbl.children("tfoot").children("tr");
					grid._detachCells(
				footerCells,
							function (row) {
					return row.children("td").not("[data-skip=true]");
					},
				pos,
					grid._detachedFooterCells,
					col.key
					);
					},
			_attachColumn: function (col) {
				if (!this.grid.hasFixedColumns()) {
					this._gridAttachColumnHandler(col);
					return;
			}
var headerCells, footerCells, fixed = col.fixed, $tbl,
	grid = this.grid, pos = grid.getVisibleIndexByKey(col.key);
grid._initializeDetachedContainers();
if (grid._isMultiColumnGrid) {
	grid._showMultiHeaderCells(col.key);
			}
				/* attach thead cells */
				$tbl = fixed ? grid.fixedHeadersTable() : grid.headersTable();
				headerCells = grid._isMultiColumnGrid ?
										$tbl.children("thead").children("tr:not([data-mch-level])").not("[data-skip=true]") :
										$tbl.children("thead").children("tr").not("[data-skip=true]");
					grid._attachCells(
						headerCells,
						function (row) { return row.children("th, td").not("[data-skip=true]"); },
					pos,
						grid._detachedHeaderCells,
						col.key
					);
				/* attach tfoot cells */
				$tbl = fixed ? grid.fixedFootersTable() : grid.footersTable();
				footerCells = $tbl.children("tfoot").children("tr");
				grid._attachCells(
					footerCells,
					function (row) { return row.children("td").not("[data-skip=true]"); },
					pos,
					grid._detachedFooterCells,
					col.key
				);
				},
			_rerenderColgroups: function () {
				var grid = this.grid, c = this._containers,
					fc = grid.hasFixedColumns();
				if (fc) {
					if (this._isVirtualGrid()) {
					c.body.fixedTable.children("colgroup").remove();
					c.header.fixedTable.children("colgroup").remove();
					c.footer.fixedTable.children("colgroup").remove();
				} else {
					grid.fixedContainer().find("colgroup").remove();
					}
			}
			this._gridRerenderColgroupsHandler.apply(this.grid, arguments);
			},
			_renderColgroup: function (table, isHeader, isFooter, autofitLastColumn) {
				var fc = this.grid.hasFixedColumns();
				if (fc) {
					this._gridRenderColgroupHandler(document.getElementById(table.id + "_fixed"),
												isHeader,
												isFooter,
												false,
												{ fixed: true });
			}
			this._gridRenderColgroupHandler(table, isHeader, isFooter, autofitLastColumn);
			},
			_resizeEl: function ($e, delta) {
				var w = this._calcTblWidth($e, delta);
				$e[ 0 ].style.width = w;
				return w;
				},
			_containerResized: function (isFixed, deltaWidth) {
				/* when grid container is resized and width is in percentage then we should sync header heights and row heights between fixed and unfixed area */
				if (!this.grid.hasFixedColumns()) {
					return;
			}
			var grid = this.grid, w, v = this._isVirtualGrid();
			if (!this._containers || !this._containers.body) {
				this._populateContainers();
			}

			if (isFixed) {
				deltaWidth = -deltaWidth || 0;
				w = this._resizeEl(this._containers.body.fixedTable, deltaWidth);
				if (grid.options.fixedHeaders && grid.options.showHeader) {
					w = this._resizeEl(this._containers.header.fixedTable, deltaWidth);
}
if (grid.options.fixedFooters && grid.options.showFooter) {
					w = this._resizeEl(this._containers.footer.fixedTable, deltaWidth);
}
if (v) {
					this._resizeEl(this._containers.body.fixedContainer, deltaWidth);
					if (grid.options.fixedHeaders && grid.options.showHeader) {
						w = this._resizeEl(this._containers.header.fixedContainer, deltaWidth);
}
					if (grid.options.fixedFooters && grid.options.showFooter) {
						w = this._resizeEl(this._containers.footer.fixedContainer, deltaWidth);
}
					this._updateVirtualDOMWidth(deltaWidth);
} else {
					this._mainFixedCntnr[ 0 ].style.width = w;
}
this._updateHScrollbarWidth();
this._updateGridWidth();
	/* M.H. 14 Mar 2016 Fix for bug 215862: Resizing fixed columns doesn't show horizontal scrollbar */
	grid._updateHScrollbarVisibility();
			}
			if (grid._gridHasWidthInPercent()) {
				this._setMinWidthForGridContainer();
			}
				/* if offset top of the last row is different between fixed and unfixed area we should sync heights of table rows(in fixed/unfixed tables) */
				/* we do not check offset of each table row and do not sync heights of all table rows because this could cause performance issue */
				/* M.H. 6 Jan 2014 Fix for bug #160621: By setting a column as fixed, Row height does not auto resize when resizing the column. */
				/*if (Math.abs(this._containers.body.fixedTable.outerHeight() - this._containers.body.unfixedTable.outerHeight()) > 1) { */
				if (this.options.syncRowHeights) {
					this.checkAndSyncHeights();
			}
			},
			_checkGridSupportedFeatures: function () {
				/* Throw an exception for unsupported integration scenarios */
				if (this.grid.options._isHierarchicalGrid) {
					throw new Error($.ig.ColumnFixing.locale.hierarchicalGridNotSupported);
			}
			var i, featureName,
				gridOptions = this.grid.options, cols = gridOptions.columns,
				dW = gridOptions.defaultColumnWidth,
				features = gridOptions.features, featuresLength = features.length;
				/* M.H. 13 Apr 2016 Fix for bug 217845: It should be thrown exception when ColumnFixing is enabled and width of the grid is not set */
				if (gridOptions.width === null || gridOptions.width === "") {
					throw new Error($.ig.ColumnFixing.locale.noGridWidthNotSupported);
			}
				/* M.H. 3 Dec 2014 Fix for bug #185566: ColumnFixing should throw a JavaScript exception that it doesn't support igGrid.width set in % */
				/* if defaultColumnWidth is set we should not check for each of the column widths */
				if (dW) {
					if ($.type(dW) === "string" && dW.indexOf("%") > 0) {
					throw new Error($.ig.ColumnFixing.locale.defaultColumnWidthInPercentageNotSupported);
				}
				} else {
					$.each(cols, function (ind, col) {
					var w = col.width;
					if (!w || ($.type(w) === "string" && w.indexOf("%") > 0)) {
						throw new Error(
							$.ig.ColumnFixing.locale.columnsWidthShouldBeSetInPixels.replace("{key}",
							col.key));
					}
				});
			}
				/*M.K. 1/9/2015 187174: Errors should be thrown when the grid is initialized with unsupported configurations */
				$.each(cols, function (ind, col) {
					if (col.unbound) {
					throw new Error($.ig.ColumnFixing.locale.unboundColumnsNotSupported);
				}
			});
				/* M.H. 8 Dec 2014 Fix for bug #186237: Row virtualization is not working with Column Fixing */
				if (gridOptions.columnVirtualization === true) {
					throw new Error($.ig.ColumnFixing.locale.columnVirtualizationNotSupported);
			}
				/* M.H. 29 Feb 2016 Fix for bug 214757: fixColumn/unfixColumn API should throw an error if the target column key is not in the same area where the specified column should go */
				if (gridOptions.width === null || gridOptions.width === "") {
					throw new Error($.ig.ColumnFixing.locale.noGridWidthNotSupported);
			}
				/*
				if ($.type(gridOptions.height) === "string" && gridOptions.height.indexOf("%") > 0) {
					throw new Error($.ig.ColumnFixing.locale.gridHeightInPercentageNotSupported);
				}*/
				/* when option virtualization is TRUE and virtualization mode is 'continuous' then it is assumed that there is ONLY rowVirtualization enabled */
				if (gridOptions.virtualization &&
					gridOptions.virtualizationMode !== "continuous") {
					throw new Error($.ig.ColumnFixing.locale.virtualizationNotSupported);
			}
				/* There is only one feature defined - ColumnFixing - in the features collection */
				if (featuresLength === 1) {
					return;
			}
			for (i = 0; i < featuresLength; i++) {
				featureName = features[ i ].name;
				if (!featureName) {
					continue;
			}
			featureName = featureName.toLowerCase();
			switch (featureName) {
					case "groupby":
						throw new Error($.ig.ColumnFixing.locale.groupByNotSupported);
						/*case "columnmoving": */
						/*	throw new Error($.ig.ColumnFixing.locale.columnMovingNotSupported); */
					case "responsive":
						throw new Error($.ig.ColumnFixing.locale.responsiveNotSupported);
			}
			}
			},
			/* Hiding support */
			_updateWidthsOnHiding: function (width, fixed) {
				/* Update widths of containers and tables on showing/hiding columns
				Update widths of FIXED (Header/footer/data) tables AND containers
				When virtualization is enabled it should be updated width of the UNFIXED containers as well(otherwise width of the virtual container(taken via grid._virtualcontainer) will not be equal to grid.options.width)
				*/
			var $bodyTbl, $headerTbl, $footerTbl, funcUpdateTbl,
				$bodyContainer, $headerContainer, $footerContainer;
			this._populateContainers();
			$bodyTbl = fixed ? this._containers.body.fixedTable : this._containers.body.unfixedTable;
			$bodyContainer = fixed ?
						this._containers.body.fixedContainer :
						this._containers.body.unfixedContainer;
			if (this._containers.header) {
				$headerTbl = fixed ? this._containers.header.fixedTable : this._containers.header.unfixedTable;
				$headerContainer = fixed ?
								this._containers.header.fixedContainer :
								this._containers.header.unfixedContainer;
			}
			if (this._containers.footer) {
				$footerTbl = fixed ?
								this._containers.footer.fixedTable :
								this._containers.footer.unfixedTable;
				$footerContainer = fixed ?
									this._containers.footer.fixedContainer :
									this._containers.footer.unfixedContainer;
			}
			funcUpdateTbl = function (nW, $tbl) {
				if (!$tbl || !$tbl.length) {
				return;
			}
				/* M.H. 6 Mar 2015 Fix for bug 188873: When continuous virtualization is enabled and you hide a column from the fixed and unfixed areas cells and headers are misaligned. */
				/* if grid is virtual and width is not set explicitly or it is in percentage - we should not change its width */
				var w = $tbl[ 0 ].style.width;
				if ($.type(w) === "string" &&
						(w.indexOf("%") !== -1 || w === "")) {
					return;
			}
			w = parseInt($tbl[ 0 ].style.width, 10);
			if (isNaN(w)) {
					w = $tbl.outerWidth();
			}
				w += nW;
				/* NOTE: set width through style.width (if using $.width() the border-width of fixed container is taken into account and actual width of the container is not accurate) */
				$tbl[ 0 ].style.width = w + "px";
			};
				/* update width of tables */
				funcUpdateTbl(width, $bodyTbl);
				funcUpdateTbl(width, $headerTbl);
				funcUpdateTbl(width, $footerTbl);
				if (fixed && this._mainFixedCntnr) {
					this._mainFixedCntnr.css("width",
						(parseFloat(this._mainFixedCntnr[ 0 ].style.width) + width) + "px");
			}
			if (this._isVirtualGrid()) {
				if (fixed) {
					this._updateFixedColgroupVirt(width);
				/* update virtual containers width - if fixed column is hidden then UNFIXED col in colgroup and UNFIXED containers should be updated */
					if (!this.grid._gridHasWidthInPercent()) {
						funcUpdateTbl(-width, this._containers.body.unfixedContainer);
						if (this.grid.options.showHeader) {
							funcUpdateTbl(-width, this._containers.header.unfixedContainer);
					}
						if (this.grid.options.showFooter) {
							funcUpdateTbl(-width, this._containers.footer.unfixedContainer);
					}
			}
					this._updateHScrollbarWidthVirt();
			}
			funcUpdateTbl(width, $bodyContainer);
			funcUpdateTbl(width, $headerContainer);
			funcUpdateTbl(width, $footerContainer);
			}
			},
			_hidingFinishing: function (args) {
				/* this function is called from _fireInternalEvent(grid's framework function _setHiddenColumns)
				it should be updated widths of fixed containers on hidingFinishing(NOT in hidingFinished event) because after hidingFinishing it is called (re)renderColgroup(and other functions that calculates widths) to calculate properly widths of unfixed container
				*/
			if (!this.grid.hasFixedColumns()) {
				return;
			}
			var i, grid = this.grid, cols = args.columns, w, col;
			for (i = 0; i < cols.length; i++) {
				col = cols[ i ];
			if (col.fixed) {
				w = parseInt(col.width, 10);
					this._updateWidthsOnHiding(args.hidden ? -w : w, true);
			}
			}
			this._populateContainers();
			grid._columnMovingResets();
			},
			_hidingFinished: function (args) {
				/* this function is called from _fireInternalEvent(grid's framework function _setHiddenColumns).
				It is possible to be hidden column which has CELLS which span on 2 or more rows - so it should be synced rows heights
				*/
			if (!this.grid.hasFixedColumns()) {
				return;
			}
			var $trs, virt = this._isVirtualGrid(), c = this._containers;
			this._populateContainers();
				/* in case of rowVirtualization and initially hidden columns + initially fixed columns - width of unfixed header table is not properly set because it is calculated width of all columns(including hidden ones). NOTE: initial fixing is applied before initial hiding */
				if (virt) {
					/*this.grid._initialHiddenColumns && */
					/*this.grid._initialHiddenColumns.length */
					/* calculate width of header/footer tables as summarizing width of each visible column - first clear widths of tables and call _updateWidths */
					/* M.H. 26 Feb 2016 Fix for bug 188873: When continuous virtualization is enabled and you hide a column from the fixed and unfixed areas data cells and header cells are misaligned. */
					this._recalcTableWidths();
					/* M.H. 12 Mar 2016 Fix for bug 215774: Fixing a column and hiding another after that causes the feature chooser button of righmost column to be not visible and the fixed column area is smaller */
					if (this.options.fixingDirection === "right") {
					this._adjustVirtWidthOnFixingRight(false);
				}
			}
				/* sync heights */
				if (this.options.syncRowHeights) {
					if (!args.hidden) {
					$trs = c.body.fixedTable.children("tbody").find(">tr");
					$trs = $trs.add(c.body.unfixedTable.children("tbody").find(">tr"));
					$trs.each(function (ind, tr) {
						tr.style.height = "";
				});
				}
				if (virt && !this._isContinuousVirtualization()) { // in case of fixed virtualization
					if (this.options.syncRowHeights) {
						this.syncRowsHeights(this.grid.fixedTable().children("tbody").children("tr"),
									this.grid.element.children("tbody").children("tr"));
				}
				} else {
					this._syncHeightsForTables(this.grid.fixedTable(), this.grid.element);
				}
				if (this.grid.options.showFooter) {
					/* M.H. 8 June 2015 Fix for bug 194635: When column fixing and summaries are enabled the fixed and unfixed area don't have the same height. */
					if (c.footer) {
						this.syncRowsHeights(c.footer.fixedTable.children("tfoot").children("tr"),
								c.footer.unfixedTable.children("tfoot").children("tr"));
				}
				}
			}
		if (this.grid._gridHasWidthInPercent()) {
			this._setMinWidthForGridContainer();
			}
				/*this._checkAndRenderHScrlbarCntnr(); */
				/* M.H. 19 Feb 2014 Fix for bug #164298: When a column is fixed and other column is hidden showing this column makes some of the rows higher. */
				this._applySyncRowHeights = true;
				},
			/* //Hiding support */
			/* Updating support*/
			_syncRowsOnAddEdit: function (rowId) {
				/* if there aren't fixed columns OR option syncRowHeights is false - return */
				if (!this.options.syncRowHeights || !this.grid.hasFixedColumns()) {
					return;
			}
			var h,
				c = this._containers,
				$fRow = this.grid.rowById(rowId, true),
				$ufRow = this.grid.rowById(rowId);
			if (!$fRow || !$ufRow) {
				return;
			}
		if (!c || !c.body) {
			this._populateContainers();
			}
				/* in case when 2 rows have synced heights and after updating one of the rows is shrunk */
				/* do not change table heights when virtualizationMode is fixed */
				/* M.H. 25 Feb 2016 Fix for bug 214746: Row adding stops working when fixed virtualization and column fixing are enabled */
				h = c.body.fixedTable.height();
				c.body.fixedTable.height("");
				c.body.unfixedTable.height("");
				$fRow.height("");
				$ufRow.height("");
				this.syncRowsHeights($fRow, $ufRow);
				c.body.fixedTable.height(h);
				c.body.unfixedTable.height(h);
				/* M.H. 5 June 2015 Fix for bug 187746: When RowSelectors and ColumnFixing are enabled adding a new row makes the rows misaligned. */
				this.checkAndSyncHeights();
				},
			_internalRowDeleted: function () {
				if (!this.options.syncRowHeights || !this.grid.hasFixedColumns()) {
					return;
			}
			var c = this._containers;
			if (!c || !c.body) {
				this._populateContainers();
			}
			if (!this._isVirtualGrid()) {
				c.body.fixedTable.height("");
				c.body.unfixedTable.height("");
			}
			this.checkAndSyncHeights();
			},
			_internalRowAdded: function (args) {
				if (!this.grid.hasFixedColumns()) {
					return;
				}
				this._syncRowsOnAddEdit(args.row.attr("data-id"));
				},
			_internalCellUpdated: function (args) {
				if (!this.grid.hasFixedColumns()) {
					return;
				}
				/* sync rows heights - if there are fixed columns */
				this._syncRowsOnAddEdit(args.rowID);
				},
			_internalRowUpdated: function (args) {
				if (!this.grid.hasFixedColumns()) {
					return;
				}
				this._syncRowsOnAddEdit(args.rowID);
			},
			_removeRowsHeights: function ($rows, removeAttr) {
				var i, len = $rows.length;
				for (i = 0; i < len; i++) {
					$rows[ i ].style.height = "";
					if (removeAttr) {
						$rows[ i ].removeAttribute("height");
					}
				}
			},
			/* //Updating support*/
			_virtualrecordsrender: function () {
				var grid = this.grid, h, $fixedTable, $unfixedTable, fc, ufc, $fRows, $ufRows,
					cvirt = this._isContinuousVirtualization();
				if (!grid.hasFixedColumns() || !this.options.syncRowHeights) {
					return;
			}
			$fixedTable = this._containers.body.fixedTable;
			$unfixedTable = this._containers.body.unfixedTable;
			h = $unfixedTable[ 0 ].style.height;
				/* M.H. 29 Sep 2014 Fix for bug #179959: When there is a fixed column, fixed virtualization is enabled and rows have different heights scrolling the grid changes the height of the scroll container. */
					$fixedTable.height("");
					$unfixedTable.height("");
				if (cvirt) {
					this._checkSyncTablesHeights();
					if (!$.ig.util.isIE) {
					fc = this._scrollContainers.fCntnr;
					ufc = this._scrollContainers.ufCntnr;
					fc.scrollTop(ufc.scrollTop());
					}
					return;
			}
			$ufRows = $unfixedTable.children("tbody").children("tr");
			$fRows = $fixedTable.children("tbody").children("tr");
			this._removeRowsHeights($ufRows);
			this._removeRowsHeights($fRows);
			this.syncRowsHeights($ufRows, $fRows);
				/* M.H. 29 Sep 2014 Fix for bug #179959: When there is a fixed column, fixed virtualization is enabled and rows have different heights scrolling the grid changes the height of the scroll container. */
					$fixedTable.height(h);
					$unfixedTable.height(h);

},
			_setFixingStateByCol: function (col, isFixed) {
				var i, len;
				col.fixed = isFixed;
				if (col.group) { // group header
					len = col.group.length;
					for (i = 0; i < len; i++) {
					this._setFixingStateByCol(col.group[ i ], isFixed);
				}
			}
			},
			_unregisterSetOptionCallback: function () {
				var callbacks = this.grid._setOptionCallbacks, i, len = callbacks.length;
				for (i = 0; i < len; i++) {
					if (callbacks[ i ].type === "ColumnFixing") {
					$.ig.removeFromArray(callbacks, i);
						break;
				}
			}
			},
			_registerSetOptionCallback: function () {
				var callbacks = this.grid._setOptionCallbacks, i, len = callbacks.length;
				for (i = 0; i < len; i++) {
					if (callbacks[ i ].type === "ColumnFixing") {
						break;
				}
			}
			if (i === len) {
				callbacks.push({
						type: "ColumnFixing",
						func: $.proxy(this._gridSetOption, this)
			});
			}
			},
			_updateHScrollbarTblWidthsInFixedVirtRight: function () {
				var $hScrlBar, $hScrlBarInner;
				$hScrlBarInner = this.grid._getHScrollContainerInner();
				/*$hScrlBarInner.width($hScrlBarInner.width() + this.grid._scrollbarWidth()); */
				$hScrlBar = $hScrlBarInner.parent();
				if (!this.grid._gridHasWidthInPercent) {
					$hScrlBar.width(this._containers.body.unfixedContainer.width());
			}
			$hScrlBarInner.width(this._containers.body.unfixedTable.width());
			$hScrlBar.css("overflow-y", "hidden");
				/* width of headers/footers table are not correct */
				if (this._containers.header) {
					this._containers.header.unfixedTable.css("width", "");
					this._containers.header.unfixedTable.css("width",
															this._calcTblWidth(this._containers.header.unfixedTable, 0));
			}
			if (this._containers.footer) {
				this._containers.footer.unfixedTable.css("width", "");
				this._containers.footer.unfixedTable.css("width",
														this._calcTblWidth(this._containers.footer.unfixedTable, 0));
			}
			this._updateGridWidth();
			this.grid._updateHScrollbarVisibility();
			},
			/* M.H. 8 Sep 2016 Fix for bug 223810: UI in fixed columns is not updated after rollback is called with updateUI=true */
			_rollbackApplied: function () {
				if (!this._containers || !this._containers.body) {
					this._populateContainers();
				}
				this._containers.body.fixedTable.height("");
				this._containers.body.unfixedTable.height("");
				if (this.options.syncRowHeights) {
					this.checkAndSyncHeights();
				}
			},
			_gridRendered: function () {
				/* M.H. 8 Mar 2016 Fix for bug 215545: Unfixing a column when having fixing direction right and continuous virtualization results in misaligned vertical scrollbar */
				if (this.options.fixingDirection === "right" &&
					this._isVirtualGrid() &&
					this.grid.hasFixedColumns()) {
					this._updateHScrollbarTblWidthsInFixedVirtRight();
			}
			},
			_injectGrid: function (gridInstance, isRebind) {
				this.grid = gridInstance;
				if (isRebind === true) {
					return;
				}
				this.grid._fixedColumns = this.grid._fixedColumns || [];
				this.grid._fixingDirection = this.options.fixingDirection;
				/* M.H. 9 Sep 2013 Fix for bug #144030: When no width and height are set, and columnFixing is enabled, no descriptive error is thrown */
				this._checkGridSupportedFeatures();
				this._registerSetOptionCallback();

				/* M.H. 28 May 2014 Fix for bug #172200: Memory leak when using the features of the grid */
				this._detachEvents();
				this._headerCellRenderedHandler = $.proxy(this._headerCellRendered, this);
				this.grid.element.bind("iggridheadercellrendered", this._headerCellRenderedHandler);
				this._headerRenderedHandler = $.proxy(this._headerRendered, this);
				this.grid.element.bind("iggridheaderrendered", this._headerRenderedHandler);
				this._headerRenderingHandler = $.proxy(this._headerRendering, this);
				this.grid.element.bind("iggridheaderrendering", this._headerRenderingHandler);
				this._columnsMovedHandler = $.proxy(this._columnsMoved, this);
				this.grid.element.bind("iggrid_columnsmoved", this._columnsMovedHandler);
				if (this._isVirtualGrid() && this.options.syncRowHeights) {
					this._virtualrecordsrenderHandler = $.proxy(this._virtualrecordsrender, this);
					this.grid.element.bind("iggridvirtualrecordsrender", this._virtualrecordsrenderHandler);
			}
				/* we should re-render main fixed container when grid changes its height */
				/*if (this.grid.options.height !== null) { */
				this._gridContainerHeightHandler = $.proxy(this._heightChanged, this);
				this.grid.element.bind("iggrid_heightchanged", this._gridContainerHeightHandler);
				/* M.H. 7 Mar 2014 Fix for bug #147490: The "Search Result" span is relocated when column is fixed and you filter the grid in Chrome. */
				this._gridHeightChangingHandler = $.proxy(this._gridHeightChanging, this);
				this.grid.element.bind("iggrid_heightchanging", this._gridHeightChangingHandler);
				/* used to apply initial column fixing */
				this._gridContainersRenderedHandler = $.proxy(this._gridContainersRendered, this);
				this.grid.element.bind("iggrid_gridcontainersrendered", this._gridContainersRenderedHandler);
				if (this.grid._columns === undefined || this.grid._columns === null) {
					this.grid._columns = this.grid.options.columns.clone();
			}
				/* M.H. 24 Jul 2013 Fix for bug #143633: If you call dataBind method while you have fixed columns the grid becomes misaligned after you scroll it horizontally. */
				/* we should not reset fixedColumns array - for instance on rebind(in this case isRebind is true) we should preserve _fixedColumns array */
				if (this.grid._fixedColumns === undefined) {
					this.grid._fixedColumns = [];
			}
				/* M.H. 18 Sep 2013 Fix for bug #152468: The fixed and unfixed areas are misalinged when there are cells with text spanned on two rows and the grid doesn't have height in IE9. */
				if (this.grid.options.height === null &&
						$.ig.util.isIE && $.ig.util.browserVersion >= 9) {
					this._dataRenderingHandler = $.proxy(this._dataRendering, this);
					this.grid.element.bind("iggriddatarendering", this._dataRenderingHandler);
			}
			this._dataRenderedHandler = $.proxy(this._dataRendered, this);
			this.grid.element.bind("iggriddatarendered", this._dataRenderedHandler);
			this._lastColumnWidthAutoAdjustedHandler = $.proxy(this._lastColumnWidthAutoAdjusted, this);
			this.grid.element.bind("iggrid_lastcolumnwidthautoadjusted",
									this._lastColumnWidthAutoAdjustedHandler);
			/* override functions */
			if (!this._isFunctionsOverriden) {
				this.grid._visibleAreaWidth(this.options.minimalVisibleAreaWidth);
				this._gridRenderRowHandler = $.proxy(this.grid._renderRow, this.grid);
				this._renderRowHandler = $.proxy(this._renderRow, this);
				this.grid._renderRow = this._renderRowHandler;
				this._gridRenderRecordsForTableHandler = $.proxy(this.grid._renderRecordsForTable, this.grid);
				this._renderRecordsForTableHandler = $.proxy(this._renderRecordsForTable, this);
				this.grid._renderRecordsForTable = this._renderRecordsForTableHandler;
				this._gridRenderNewRowHandler = $.proxy(this.grid.renderNewRow, this.grid);
				this._renderNewRowHandler = $.proxy(this._renderNewRow, this);
				this.grid.renderNewRow = this._renderNewRowHandler;
				/*override detach/attach cells */
				this._gridDetachColumnHandler = $.proxy(this.grid._detachColumn, this.grid);
				this._detachColumnHandler = $.proxy(this._detachColumn, this);
				this.grid._detachColumn = this._detachColumnHandler;
				this._gridAttachColumnHandler = $.proxy(this.grid._attachColumn, this.grid);
				this._attachColumnHandler = $.proxy(this._attachColumn, this);
				this.grid._attachColumn = this._attachColumnHandler;
				this._gridRerenderColgroupsHandler = $.proxy(this.grid._rerenderColgroups, this.grid);
				this._rerenderColgroupsHndlr = $.proxy(this._rerenderColgroups, this);
				this.grid._rerenderColgroups = this._rerenderColgroupsHndlr;
				this._gridRenderColgroupHandler = $.proxy(this.grid._renderColgroup, this.grid);
				this._renderColgroupHandler = $.proxy(this._renderColgroup, this);
				this.grid._renderColgroup = this._renderColgroupHandler;
				this._gridUpdatePaddingHandler = $.proxy(this.grid._updateVScrollbarCellPaddingHelper,
															this.grid);
				this._updateVScrollbarCellPaddingHelperHandler =
					$.proxy(this._updateVScrollbarCellPaddingHelper, this);
				this.grid._updateVScrollbarCellPaddingHelper = this._updateVScrollbarCellPaddingHelperHandler;
				this._isFunctionsOverriden = true;
			}
		}
	});
	$.extend($.ui.igGridColumnFixing, { version: "16.1.20161.2145" });
}(jQuery));
/*jshint +W018 */
