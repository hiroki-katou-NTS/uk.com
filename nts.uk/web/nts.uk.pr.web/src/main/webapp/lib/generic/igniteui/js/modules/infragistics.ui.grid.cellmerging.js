/*!@license
 * Infragistics.Web.ClientUI Grid Merged Cells 16.1.20161.2145
 *
 * Copyright (c) 2011-2016 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 * Depends on:
 *	jquery-1.9.1.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	infragistics.ui.grid.framework.js
 *	infragistics.ui.shared.js
 *	infragistics.dataSource.js
 *	infragistics.util.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igGridCellMerging widget.
		The widget is pluggable to the element where the grid is instantiated and the actual igGrid object doesn't know about this.
		The merged cells widget just attaches its functionality to the grid.
		The widget is used for applying specific "merged" styles to the grid cells when their value is repeated multiple times
		in subsequent cells. Usually used after sorting for making groups of values more distinguishable for the end-user.
	*/
	"use strict";
	$.widget("ui.igGridCellMerging", {
		css: {
			/* Classes applied to the top cell of a merged group */
			mergedCellsTop: "ui-iggrid-mergedcellstop",
			/* Classes applied to the bottom cell of a merged group */
			mergedCellsBottom: "ui-iggrid-mergedcellsbottom",
			/* Classes applied to every cell of a merged group */
			mergedCell: "ui-iggrid-mergedcell"
		},
		options: {
			/* type="regular|merged" controls the initial state
			regular type="string" the grid won't be initialized with cells merged
			merged type="string" the grid will be initialized with cells merged
			*/
			initialState: "regular",
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		events: {
			/* cancel="true" Event fired before a new merged cells group is created.
			Function takes arguments evt and ui.
			Use ui.row to get reference to the row the merged group starts in.
			Use ui.rowIndex to get the index of the row the merged group starts in.
			Use ui.rowKey to get the key of the row the merged group starts in.
			Use ui.owner to get reference to igGridCellMerging.
			Use ui.grid to get reference to the igGrid the igGridCellMerging are initialized for.
			Use ui.value to get the cells value which is repeated and caused the merged group to be created.
			*/
			cellsMerging: "cellsMerging",
			/*
			Use ui.row to get reference to the row the merged group starts in.
			Use ui.rowIndex to get the index of the row the merged group starts in.
			Use ui.rowKey to get the key of the row the merged group starts in.
			Use ui.owner to get reference to igGridCellMerging.
			Use ui.grid to get reference to the igGrid the igGridCellMerging are initialized for.
			Use ui.value to get the cells value which is repeated and caused the merged group to be created.
			Use ui.count to get the total count of cells that were merged.
			*/
			cellsMerged: "cellsMerged"
		},
		_create: function () {
			this._sortingRequested = false;
			this._v = false;
		},
		destroy: function () {
			this._removePaint();
			this._unregisterEvents();
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		_gridSorting: function () {
			this._sortingRequested = true;
		},
		_gridSorted: function () {
			this._sortingRequested = false;
			this._notInitial = true;
		},
		_gridRendered: function () {
			var key, index, sExp = this.grid.dataSource.settings.sorting.expressions, i, j;
			if (this.options.initialState === "merged" &&
				this._sortingRequested === false &&
				!this._notInitial) {
				for (j = 0; j < this.grid._visibleColumns().length; j++) {
					this._paintMergedCells(j, this.grid._visibleColumns()[ j ].key);
				}
			} else {
				for (i = 0; i < sExp.length; i++) {
					key = sExp[ i ].fieldName;
					for (j = 0; j < this.grid._visibleColumns().length; j++) {
						if (this.grid._visibleColumns()[ j ].key === key) {
							index = j;
							break;
						}
					}
					if (typeof (this.grid._startColIndex) === "number") {
						index -= this.grid._startColIndex;
					}
					if (index !== null && index !== undefined && key) {
						this._paintMergedCells(index, key);
					}
				}
			}
		},
		_rrn: function () {
			this._gridRendered();
		},
		_rcn: function () {
			this._gridRendered();
		},
		_columnsCollectionModified: function () {
			this._gridRendered();
		},
		_paintMergedCells: function (index, key) {
			var cells,			// all cells to be painted
				prvCell,		// the cell from the previous pass
				curCell,		// the cell from the current pass
				prvCellTxt,		// the text of the prvCell
				curCellTxt,		// the text of the curCell
				first = true,	// marks the firt pass
				str = false,	// marks group being constructed through passes
				hasFixedCols = this.grid.hasFixedColumns(), // check whether there are fixed columns
				count = 0,		// keeps count of merged cells in group to use in events
				i,				// cell iterator
				args,			// event arguments
				cval,			// current repeating value
				noCancel;		// event cancelled
			if (hasFixedCols) {
				// M.H. 28 Feb 2014 Fix for bug #165654: When fixed column is hidden cell merging is applied to the wrong column.
				if (key) {
					index = this.grid.getVisibleIndexByKey(key, true) + 1;// include non-data columns(if row selectors are enabled)
				} else {
					index = (index + 1 + this._getSystemFixedColumnsCount());
				}
				if (this.grid.isFixedColumn(key)) {
					// M.H. 23 Aug 2013 Fix for bug #150006: When row selectors are enabled and you sort a fixed column the cell merging is applied to the wrong column.
					cells = this.grid.container()
						.find("#" + this.grid.id() + "_fixed tbody tr>td:nth-child(" + index + ")");
				} else {
					cells = this.grid.container()
						.find("#" + this.grid.id() + " tbody tr>td:nth-child(" + index + ")");
				}
			} else {
				cells = this.grid.container()
					.find("#" + this.grid.id() + " tbody tr>td:nth-child(" +
					(index + 1 + this._getSystemColumnsCount()) +
					")");
			}
			/* the function will add fictive cells to the start and end of the cell array
			representing the data of the cell above and below the visible arrea of a hierarchical grid */
			this._addVirtualBorderCells(cells, key);
			prvCell = cells.eq(0);
			for (i = 1; i < cells.length; i++) {
				curCell = cells.eq(i);
				prvCellTxt = this._getComparableCellText(prvCell);
				curCellTxt = this._getComparableCellText(curCell);
				if (prvCellTxt === curCellTxt && prvCellTxt !== cval) {
					/* a new merged group is being created */
					if (str === false) {
						args = this._getEventArgsForCell(prvCell);
						noCancel = this._trigger(this.events.cellsMerging, this, args);
						/*	canceling the event cause the whole group to not be grouped */
						if (noCancel !== true) {
							cval = prvCellTxt;
							prvCell = curCell;
							continue;
						}
					}
					/* clearing cancelled values */
					cval = null;
					str = true;
					if (first === true) {
						if (!cells.eq(i - 1)[ 0 ].fictive) {
							cells.eq(i - 1).addClass(this.css.mergedCellsTop);
						}
						first = false;
						count++;
					}
					if (!curCell[ 0 ].fictive) {
						curCell.addClass(this.css.mergedCell);
					}
					count++;
				} else {
					/* if we had a merged cells group before add the merged
					cells bottom class and throw merged event */
					if (str === true) {
						args.count = count;
						count = 0;
						this._trigger(this.events.cellsMerged, this, args);
						/* this will never be fictive so we don't need to check */
						cells.eq(i - 1).addClass(this.css.mergedCellsBottom);
						str = false;
					}
					first = true;
				}
				prvCell = curCell;
			}
			if (str === true) {
				args.count = count;
				this._trigger(this.events.cellsMerged, this, args);
				if (!cells.eq(cells.length - 1)[ 0 ].fictive) {
					cells.eq(cells.length - 1).addClass(this.css.mergedCellsBottom);
				}
			}
		},
		_addVirtualBorderCells: function (list, key) {
			var ds = this.grid.dataSource.dataView();
			if (this._v === true) {
				/* check if there is a row above the displayed ones */
				if (this.grid._startRowIndex > 0) {
					list.splice(0, 0,
						{ txt: String(ds[ this.grid._startRowIndex - 1 ][ key ]), fictive: true }
						);
				}
				/* check if there is a row below the displayed ones */
				if (this.grid._startRowIndex + this.grid._virtualRowCount <
					this.grid.dataSource.dataView().length) {
					list.splice(list.length, 0,
						{
							txt: String(ds[ this.grid._startRowIndex + this.grid._virtualRowCount ][ key ]),
							fictive: true
						}
					);
				}
			}
		},
		_getSystemFixedColumnsCount: function () {
			var firstRow = this.grid.fixedContainer()
				.find("tbody>tr:not([data-container='true'],[data-grouprow='true']):first");
			return firstRow.children("[data-parent='true'],[data-skip='true'],th").length;
		},
		_getSystemColumnsCount: function () {
			var firstRow = this.grid.container()
				.find("#" + this.grid.id() +
				" tbody>tr:not([data-container='true'],[data-grouprow='true']):first");
			return firstRow.children("[data-parent='true'],[data-skip='true'],th").length;
		},
		_getEventArgsForCell: function (cell) {
			var args, row, rKey, rIdx, val;
			if (cell[ 0 ].fictive) {
				// fictive cell
				row = null;
				rKey = null;
				rIdx = this.grid._startRowIndex ? this.grid._startRowIndex - 1 : -1;
				val = cell[ 0 ].txt;
			} else {
				row = cell.closest("tr");
				rKey = row.attr("data-id");
				rIdx = this._getVisibleRowIndex(row);
				if (rKey === "" || rKey === null || rKey === undefined) {
					rKey = rIdx;
				}
				val = cell.html();
			}
			args = {
				owner: this,
				row: row,
				rowIndex: rIdx,
				rowKey: rKey,
				grid: this.grid,
				value: val
			};
			return args;
		},
		_getComparableCellText: function (cell) {
			var text = cell[ 0 ].fictive ? cell[ 0 ].txt : cell.html().replace(/^&nbsp;$/, ""); // removing empty cell's &nbsp; markup
			if (this.grid.dataSource.settings.sorting.caseSensitive === false) {
				text = text.toLowerCase();
			}
			return text;
		},
		_getVisibleRowIndex: function (row) {
			return row.closest("tbody")
				.children("tr:not([data-container='true'],[data-grouprow='true'])")
				.index(row) + (this.grid._startRowIndex || 0);
		},
		_removePaint: function () {
			var dataRows = this.grid.element
				.children("tbody")
				.children("tr:not([data-container='true'],[data-grouprow='true'])"),
				cells, i;
			cells = dataRows.children("td." + this.css.mergedCellsTop + ",td." + this.css.mergedCell);
			for (i = 0; i < cells.length; i++) {
				$(cells[ i ])
					.removeClass(this.css.mergedCellsTop)
					.removeClass(this.css.mergedCell)
					.removeClass(this.css.mergedCellsBottom);
			}
		},
		_createHandlers: function () {
			this._sortingInitiatedHandler = $.proxy(this._gridSorting, this);
			this._sortingHandler = $.proxy(this._gridSorted, this);
			this._virtualRowsHandler = $.proxy(this._rrn, this);
			this._virtualColumnsHandler = $.proxy(this._rcn, this);
			this._columnsCollectionModifiedHandler = $.proxy(this._columnsCollectionModified, this);
		},
		_registerEvents: function () {
			this.grid.element.bind({
				"iggridsortingcolumnsorting": this._sortingInitiatedHandler,
				"iggridsortingcolumnsorted": this._sortingHandler,
				"iggridvirtualrecordsrender": this._virtualRowsHandler,
				"iggridvirtualhorizontalscroll": this._virtualColumnsHandler,
				"iggridcolumnscollectionmodified": this._columnsCollectionModifiedHandler
			});
		},
		_unregisterEvents: function () {
			var tbl = this.grid.element;
			tbl.unbind("iggridsortingcolumnsorting", this._sortingInitiatedHandler);
			tbl.unbind("iggridsortingcolumnsorted", this._sortingHandler);
			tbl.unbind("iggridvirtualrecordsrender", this._virtualRowsHandler);
			tbl.unbind("iggridvirtualhorizontalscroll", this._virtualColumnsHandler);
			tbl.unbind("iggridcolumnscollectionmodified", this._columnsCollectionModifiedHandler);
		},
		_injectGrid: function (gridInstance) {
			this.grid = gridInstance;
			/* merged cells need to know if the grid is virtual */
			this._v = this.grid.options.virtualization === true ||
				this.grid.options.rowVirtualization === true;
			this._createHandlers();
			this._unregisterEvents();
			this._registerEvents();
		}
	});
	$.extend($.ui.igGridCellMerging, { version: "16.1.20161.2145" });
}(jQuery));
