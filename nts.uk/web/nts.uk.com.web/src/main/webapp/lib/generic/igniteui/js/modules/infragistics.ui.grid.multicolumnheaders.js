/*!@license
 * Infragistics.Web.ClientUI Grid Multi Headers 16.1.20161.2145
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
	$.widget("ui.igGridMultiColumnHeaders", {
		/*
		igGridMultiHeaders widget
		The widget is pluggable to the element where the grid is instantiated and the actual igGrid object doesn"t know about it.
		*/
		css: {
			/* classes applied to the multi-column header cell(group header cell which has children in the multi-column headers) */
			multiHeaderCell: "ui-iggrid-multiheader-cell"
		},
		options: {
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
        _createWidget: function () {
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
        _renderingMultiColumnHeader: function () {
			/* internal event multi column headers */
			this._renderHeaderColumns(this.grid._headerParent);
			this.grid._trigger(this.grid.events.headerRendered, null,
                { owner: this.grid, table: this.grid.headersTable() });
			this.grid._headerRenderCancel = false;
		},
		_analyzeRowspanRows: function (oldCols, level) {
			/* analyze rowspans for each cell */
			/* if header is at level 0 set its rowspan if it is rendered on row which is not on level 0 */
			/* if rowspan is set for some of the column cells through column options generates custom spans and sets the correct level at which columns should be rendered */
			var i, col, groups = [], ml = this.grid._maxLevel - level, rowspan;

			for (i = 0; i < oldCols.length; i++) {
				rowspan = 1;
				col = oldCols[ i ];
				col.rowspan = col.rowspan || col.rowSpan;
				/* M.H. 4 July 2012 Fix for bug #116205 */
				if ($.type(col.rowspan) === "string") {
					col.rowspan = parseInt(col.rowspan, 10);
				}
				if (col.rowspan > 0) {
					rowspan = col.rowspan;
				}
				if (col.group !== undefined && col.group !== null) {
					groups.push({ group: col.group, level: level + rowspan });
				} else {
					if (col.level === 0) {
						/* M.H. 4 July 2012 Fix for bug #116205 */
						if (col.rowspan === null || col.rowspan === undefined || isNaN(col.rowspan)) {
							if (ml + 1 - col.level > 0) {
								col.rowspan = ml + 1 - col.level;
							}
						}
					}
				}
				if (this._rows[ ml ] === undefined || this._rows[ ml ] === null) {
					this._rows[ ml ] = [];
				}
				this._rows[ ml ].push(col);
			}
			for (i = 0; i < groups.length; i++) {
				this._analyzeRowspanRows(groups[ i ].group, groups[ i ].level);
			}
		},
		_renderRow: function (headerContainer, row, level) {
			/* render each row */
			var col, i, $headerCell,
			$tr = $("<tr data-mch-level=\"" + level + "\" role=\"row\"></tr>").appendTo(headerContainer);
			for (i = 0; i < row.length; i++) {
				col = row[ i ];
				/* K.D. July 10, 2015 Removing the unnecessary parameters and just passing in the column object. */
				$headerCell = this._createHeaderColumnMarkup(col);
				$headerCell.appendTo($tr);
			}
		},
		_renderHeaderColumns: function ($container) {
			/* function is called when the internal event _renderingMultiColumnHeader is called */
			/* analyze multicolumn headers - set for each row the necessary header cells and renders header columns */
			var $th, i, j, k, oldCols, parents, key, headersTable, parentCol, mchElement,
				cols = this.grid.options.columns, mchChildren, isToHide,
				colsLength = cols.length,
				initHiddenCols = this.grid._initialHiddenColumns,
				gridId = this.grid.id(), $thead;

			this._arrayTargetRowspan = [];
			this._totalColumnsLength = $container.find("colgroup col").length;
			/* M.H. 23 Aug 2012 Fix for bug #118364 - if container has thead then empty and use it to show MCH - e.g.: when binding to table */
			$thead = $container.find("thead");
			if ($thead.length > 0) {
				$container = $thead.empty().attr("role", "rowgroup");
			} else {
				$container = $("<thead role=\"rowgroup\"></thead>").appendTo($container);
			}
			this._tr = {};
			oldCols = this.grid._oldCols;
			this._rows = {};
			/* analyze rowspans for reach row and sets for each row which are column columns */
			this._analyzeRowspanRows(oldCols, 0);
			/* when analyzed redner multicolumn headers */
			for (i = this.grid._maxLevel; i >= 0; i--) {
				if (this._rows[ i ] !== null && this._rows[ i ] !== undefined) {
					this._renderRow($container, this._rows[ i ], i);
				}
			}
			/* set data-index and populates headercells - internal array which persists order of column header cells at level 0 */
			for (i = 0; i < colsLength; i++) {
				$th = this.grid.container().find("#" + gridId + "_" + cols[ i ].key).data("columnIndex", i);
				$th.data("data-mch-order", i);
				this.grid._headerCells.push($th);
			}
			/* M.H. 20 July 2012 Fix for bug #117468 */
			if (initHiddenCols) {
                /* M.H. 27 Mar 2013 Fix for bug #117468: When the grid is bound to remote data and at least one column from (the first defined) column group is hidden by default, that column"s header cell is still visible while the grid is waiting for the data */
                headersTable = this.grid.headersTable().find("thead");
				for (i = 0; i < initHiddenCols.length; i++) {
                    /* M.H. 27 Mar 2013 Fix for bug #117468: When the grid is bound to remote data and at least one column from (the first defined) column group is hidden by default, that column"s header cell is still visible while the grid is waiting for the data */
                    key = initHiddenCols[ i ].key;
                    this.grid.container().find("#" + gridId + "_" + key).css("display", "none");
                    parents = this.grid._getParentsMultiHeader(key);
                    /* M.H. 3 Apr 2013 Fix for bug #138703: Initially hidden column hides all group of multi column headers */
                    for (j = 0; j < parents.length; j++) {
                        parentCol = parents[ j ];
                        if (parentCol.level > 0) {
                            mchElement = this.grid._getMultiHeaderColumnById(parentCol.identifier);
                            if (mchElement && mchElement.children) {
                                mchChildren = mchElement.children;
                                isToHide = true;
                                if (mchElement.hidden !== true) {
                                    for (k = 0; k < mchChildren.length; k++) {
                                        if (mchChildren[ k ].level === 0) {
											$th = this.grid.container()
												.find("#" + gridId + "_" + mchChildren[ k ].key);
                                        } else {
											$th = headersTable
												.find("th[data-mch-id=" + mchChildren[ k ].identifier + "]");
                                        }
                                        if (!$th.is(":visible")) {
                                            isToHide = false;
                                            break;
                                        }
                                    }
                                }
                                if (isToHide) {
									headersTable
										.find("th[data-mch-id=" + parentCol.identifier + "]")
										.css("display", "none");
                                }
                            }
                        }
                    }
				}
			}
		},
		/* K.D. July 10, 2015 Removing the unnecessary parameters and just passing in the column object. */
		_createHeaderColumnMarkup: function (col) {
			/* render each header column and fire headerCellRendered event */
			var grid = this.grid, id,
				isMultiColumnHeader = true,
				headerClass = grid.css.headerClass,
				customClass = col.headerCssClass || "",
				$th = $("<th></th>"),
				$headerCell,
				childIds = "",
				i,
				length;
			if (col.group) {
				length = col.group.length;
				for (i = 0; i < length; i++) {
					childIds += this.grid.element[ 0 ].id +
								"_" + col.group[ i ].key +
								((i === length - 1) ? "" : " ");
				}
			}
			/* K.D. July 10, 2015 Adding ARIA attributes */
			$headerCell = $th.append("<span class=\"" + grid.css.headerTextClass + "\">" +
							col.headerText + "</span>")
				.attr({
					"role": "columnheader",
					"aria-label": col.headerText,
					"tabIndex": grid.options.tabIndex
				}).addClass(headerClass).addClass(customClass);
			if (col.colspan > 1) {
				$th.attr("colspan", col.colspan);
			}
			if (col.rowspan > 1) {
				$headerCell.attr("rowspan", col.rowspan);
			}

			if (col.key) {
				$headerCell.attr("id", this.grid.element[ 0 ].id + "_" + col.key);
			}
			if (col.level === 0) {
				id = col.key;
				isMultiColumnHeader = false;
				$headerCell.attr("data-isheadercell", true);
			} else {
				id = col.identifier;
				$headerCell.addClass(this.css.multiHeaderCell);
				$headerCell.attr("data-mch-id", col.identifier);
				if (childIds) {
					$headerCell.attr("aria-owns", childIds);
				}
			}

			grid._trigger(grid.events.headerCellRendered, null,
				{ owner: grid, th: $headerCell, columnKey: id, isMultiColumnHeader: isMultiColumnHeader });

			return $headerCell;
		},
		getMultiColumnHeaders: function () {
			/* returns multicolumn headers array. if there aren"t multicolumn headers returns undefined
				returnType="array" array of columns
			*/
			return this.grid._oldCols;
		},
		destroy: function () {
			/* destroys the multicolumn widget */
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		_injectGrid: function (gridInstance) {
			this.grid = gridInstance;
			/*M.K. 1/13/2014 187174: Errors should be thrown when the grid is initialized with unsupported configurations */
			/*Multi-column headers feature is not supported with columnVirtualization */
			if ((this.grid.options.virtualizationMode !== "continuous") &&
				((this.grid.options.virtualization === true && this.grid.options.width) ||
					this.grid.options.columnVirtualization === true)) {
				throw new Error(
					$.ig.igGridMultiColumnHeaders.locale.multiColumnHeadersNotSupportedWithColumnVirtualization);
			}
        }
    });

    $.extend($.ui.igGridMultiHeaders, { version: "16.1.20161.2145" });
}(jQuery));
