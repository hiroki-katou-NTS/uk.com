/*!@license
 * Infragistics.Web.ClientUI Grid Column Resizing 16.1.20161.2145
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
 *	infragistics.ui.grid.shared.js
 *	infragistics.ui.grid.featurechooser.js
 */

/*global jQuery, document */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igGridResizing summary
	*/
	$.widget("ui.igGridResizing", {
		options: {
			/* type="bool" Resize the column to the size of the longest currently visible cell value. */
			allowDoubleClickToResize: true,
			/* type="bool" Specifies whether the resizing should be deferred until the user finishes resizing or applied immediately. */
			deferredResizing: false,
			/* type="array" A list of column settings that specifies resizing options on a per column basis. */
			columnSettings: [
				{
					/* type="string" Column key. this is a required property in every column setting if columnIndex is not set. */
					columnKey: null,
					/* type="number" Column index. Can be used in place of column key. the preferred way of populating a column setting is to always use the column keys as identifiers. */
					columnIndex: null,
					/* type="bool" Enables disables resizing for the column. */
					allowResizing: true,
					/* type="string|number" Minimum column width in pixels or percents. */
					minimumWidth: 20,
					/* type="string|number" Maximum column width in pixels or percents. */
					maximumWidth: null
				}
			],
			/* type="int" The width in pixels of the resizing handle which is position at the right side of each resizeable column header. */
			handleThreshold: 5,
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		css: {
			/* Class applied to the resize line that is visible during resizing */
			"columnResizeLine": "ui-iggrid-resize-line",
			/* Class applied to the resizing handle and the body to change the cursor */
			"resizingHandleCursor": "ui-iggrid-resizing-handle-cursor",
			/* Class applied to the resizing handle */
			"resizingHandle": "ui-iggrid-resizing-handle"
		},
		events: {
			/* cancel="true" event fired before a resizing operation is executed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridResizing widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnIndex to get the resized column index.
			Use ui.columnKey to get the resized column key.
			Use ui.desiredWidth to get the desired width(before min/max coercion) for the resized column.
			*/
			columnResizing: "columnResizing",
			/* event fired(only when columnFixing is enabled) when trying to resize column in fixed area so total width of unfixed area to be less than minimalVisibleAreaWidth(option defined in columnFixing)
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridResizing widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnIndex to get the resized column index.
			Use ui.columnKey to get the resized column key.
			Use ui.desiredWidth to get the desired width(before min/max coercion) for the resized column.
			*/
			columnResizingRefused: "columnResizingRefused",
			/* event fired after the resizing has been executed and results are rendered
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridResizing widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnIndex to get the resized column index.
			Use ui.columnKey to get the resized column key.
			Use ui.originalWidth to get the original column width.
			Use ui.newWidth to get the final column width after resizing.
			*/
			columnResized: "columnResized"
		},
		_createWidget: function () {
			/* !Strip dummy objects from options, because they are defined for documentation purposes only! */

			this.options.columnSettings = [];
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		_setOption: function (key) {
			throw new Error($.ig.Grid.locale.optionChangeNotSupported.replace("{optionName}", key));

			/* handle new settings and update options hash */
			/* $.Widget.prototype._setOption.apply(this, arguments); */
		},
		destroy: function () {
			/* destroys the resizing widget */

			this._clearResizingHandles();

			/* unbind element events */
			this.grid.element.unbind(".resizing");

			$.Widget.prototype.destroy.call(this);
			return this;
		},
		resize: function (column, width) {
			/* resizes a column to a specified width in pixels, percents or auto if no width is specified.
			paramType="number|string" An identifier for the column. If a number is provided it will be used as a columnIndex else if a strings is provided it will be used as a columnKey.
			paramType="number|string" optional="true" Width of the column in pixels or percents. If no width or "*" is specified the column will be auto-sized to the width of the data in it (including header and footer cells).
			*/
			var columnIndex, gridWidth;

			if (typeof column === "number") {
				columnIndex = column;
			} else {
				$.each(this.grid._visibleColumns(), function (index, col) {
					if (col.key === column) {
						columnIndex = index;
					}
				});
				/* M.H. 20 Nov 2014 Fix for bug #185316: Uncaught TypeError is thrown when a hidden column is resized programmatically */
				if (columnIndex === undefined) {
					throw new Error($.ig.GridResizing.locale.noSuchVisibleColumn);
				}
			}

			if (width === undefined || width === null || width === "*") {
				this._autoResize(columnIndex, false, null);
			} else {
				if ($.type(width) === "string" && width.indexOf("%") > 0) {
					gridWidth = this.grid.element.width();
					width = (parseInt(width, 10) * gridWidth) / 100;
				}
				this._resizeColumn(columnIndex, width, false, null);
			}
		},
		_headerRendered: function () {
			if (this.grid.element.igGridColumnFixing !== undefined) {
				this._columnFixing = this.grid.element.data("igGridColumnFixing");
			}
			this._renderResizingHandles();
		},
		_columnsCollectionModified: function () {
			this._renderResizingHandles();
		},
		_columnsMoved: function (e, args) {
			var ths, grid = this.grid,
				len = args.len,
				startIndex = args.start,
				endIndex = args.index,
				ind,
				after = (endIndex - startIndex) > 0,
				$th;
			this._populateMultiColumnHeadersLevel0();
			/* M.H. 28 Aug 2014 Fix for bug #177217: Resizing column issue after first show the column and after that move it somewhere
			we want to resize only those two th which have changed its places. When MCH we re-set places of last cells(in case of MCH) of moved (MCH)columns */
			/* this._resetResizingHandlesPosition(); */
			if (after) {
				endIndex -= len;
			}
			if (grid._isMultiColumnGrid) {
				ths = $(grid._headerCells);
			} else {
				/* M.H. 14 Feb 2013 Fix for bug #132840 */
				if (grid.options.virtualization === true || grid.options.rowVirtualization === true) {
					ths = grid.headersTable().find("> thead > tr").eq(0).children("th").not("[data-skip=true]");
				} else {
					ths = grid.headersTable().find("> thead > tr[data-header-row]").eq(0)
							.children("th").not("[data-skip=true]");
				}
			}
			function funcCalibrateResizingHandle (ind) {
				if (ths && ths[ ind ]) {
					$th = $(ths[ ind ]);
					$th.find("span[data-nonpaddedindicator=\"right\"]")
						.css("margin-right", -parseInt($th.css("padding-right"), 10) + "px");
				}
			}
			if (after) {
				ind = endIndex - 1;
			} else {
				ind = startIndex + len - 1;
			}
			funcCalibrateResizingHandle(ind);
			funcCalibrateResizingHandle(endIndex + len - 1);/* new end index */
		},
		/* M.H. 19 Feb 2016 Fix for bug 214498: A column shown by igGridHiding"s showMultiColumns() method is not resizable. */
		_hiddenColumnIndicatorsRendered: function (ths) {
			/* M.H. 4 Apr 2016 Fix for bug 217213: When using MultiColumnHeaders, Hiding and Resizing, can"t resize the second from the last column after showing the last column. */
			if (this.grid._isMultiColumnGrid && ths.length) {
				/* in MCH scenario ths is array of html elemnts - <TH> only data cells (wrapped as jQuery object). It should be applied proper margin right on each THs so take thead and take all THs(not only data header cells) */
				ths = ths[ 0 ].closest("thead").find(">tr>th").not("[data-skip=true]");
			}
			ths.find("span[data-nonpaddedindicator=\"right\"]")
				.each(function (ind, span) {
				var $span = $(span), $th = $span.closest("th");
				$span.css("margin-right", -parseInt($th.css("padding-right"), 10) + "px");
			});
		},
		_fixedColumnsChanged: function () {
			this._renderResizingHandles();
		},
		_renderResizingHandles: function () {
			var self = this, i, ths, visibleColumns = this.grid._visibleColumns(),
				thsMultiHeader, gridId = this.grid.element.attr("id"),
				hasFixedColumns = this.grid.hasFixedColumns();

			this._clearResizingHandles();
			/* A.Y. bug 91602. If all columns are hidden no resizing handles should be rendered. */
			if (visibleColumns.length === 0) {
				return;
			}
			if (this.grid._isMultiColumnGrid) {
				thsMultiHeader = this.grid.headersTable().find("> thead > tr th")
					.not("[data-skip=true]").not("[data-isheadercell=true]");
				if (hasFixedColumns) {
					$.each(this.grid.fixedHeadersTable().find("> thead > tr th")
						.not("[data-skip=true]").not("[data-isheadercell=true]"),
						function (index, th) {
							thsMultiHeader.push($(th));
						});
				}
				this._populateMultiColumnHeadersLevel0();
				thsMultiHeader.each(function () {
					/* M.H. 8 June 2015 Fix for bug 194610: Multi column headers cannot be made unresizible */
					var a, th = $(this), mchId = th.data("mchId"), i, cs = self._oColumnSettings;
					for (i = 0; i < cs.length; i++) {
						if (cs[ i ].columnKey === mchId && cs[ i ].allowResizing === false) {
							/* continue for function $.each - do not render resizing handle for the MCH */
							return true;
						}
					}
					a = self._renderResizingHandle(th);
					self._bindMultipleResizingHandle(th, a.find("span"));
				});
				ths = $(this.grid._headerCells);/* this.grid.headersTable().find("> thead > tr th[data-isheadercell=true]").not("[data-skip=true]"); */
			} else {
				/* M.H. 14 Feb 2013 Fix for bug #132840 */
				if (this.grid.options.virtualization === true || this.grid.options.rowVirtualization === true) {
					if (hasFixedColumns) {
						ths = $();
						for (i = 0; i < visibleColumns.length; i++) {
							ths.push($("#" + gridId + "_" + visibleColumns[ i ].key));
						}
					} else {
						ths = this.grid.headersTable().find("> thead > tr").eq(0)
							.children("th").not("[data-skip=true]");
					}
				} else {
					if (hasFixedColumns) {
						ths = $();
						for (i = 0; i < visibleColumns.length; i++) {
							ths.push($("#" + gridId + "_" + visibleColumns[ i ].key));
						}
					} else {
						ths = this.grid.headersTable().find("> thead > tr[data-header-row]").eq(0)
							.children("th").not("[data-skip=true]");
					}
				}
			}
			ths.each(function (index) {
				var a, cs, th = $(this),
					col = visibleColumns[ index ];

				if (col === null || col === undefined) {
					return true;
				}
				cs = self._findColumnSettingsByKey(col.key);
				if (cs.allowResizing) {
					a = self._renderResizingHandle(th);
					self._bindResizingHandle(th, a.find("span"), col);
				}
			});
		},
		_renderResizingHandle: function (th) {
			var div, a;

			if (!this._resizingHandles) {
				this._resizingHandles = [];
			}
			div = $("<div data-resizinghandle=\"true\" />")
				.css("position", "relative")
				.css("width", "100%")
				.css("height", "0px")
				.css("top", "0px")
				.css("left", "0px")
				.prependTo(th);
			/* M.H. 22 July 2015 Fix for bug 203246: When Resizing is enabled the grid table header has an <a> element which is focusable */
			a = $("<a />")
				/* A.Y. bug 82895. Required to prevent the sorting tooltip from appearing when resizing. */
				.attr("title", "")
				.prependTo(div);

			if ($.ig.util.isIE9) {
				/* A.Y. required to fix bug 92472. */

				/* since we have not called preventDefault on IE9(look at mouseWrapper in ig.ui.shared)
				the browser will show the default cursor for a link when we mousedown on the <a/>
				so we need to change it to our resizing cursor */
				a.addClass(this.css.resizingHandleCursor);
			}

			$("<span data-nonpaddedindicator=\"right\"></span>")
				/* Adding title to the span as well otherwise it will not be visible in IE(look at bug 89113). */
				.attr("title", "")
				.css("position", "absolute")
				.css("margin-right", -parseInt(th.css("padding-right"), 10) + "px")
				.css("right", "0px")
				.css("width", this.options.handleThreshold + "px")
				.addClass(this.css.resizingHandleCursor)
				.addClass(this.css.resizingHandle)
				.appendTo(a);

			this._resizingHandles.push(div);

			return a;
		},

		_populateMultiColumnHeadersLevel0: function () {
			var i, j, self = this, cols = this.grid._oldCols || [], colsLength = cols.length,
				ths = $(this.grid._headerCells), level0 = [],
				level0Length, visibleColumns = this.grid._visibleColumns();

			for (i = 0; i < colsLength; i++) {
				if (cols[ i ].level === 0) {
					level0.push(cols[ i ]);
				} else {
					for (j = 0; j < cols[ i ].children.length; j++) {
						level0.push(cols[ i ].children[ j ]);
					}
				}
			}
			level0Length = level0.length;

			ths.each(function (index) {
				var th = $(this),
					col = visibleColumns[ index ],
					cs;

				if (col === null || col === undefined) {
					return true;
				}
				cs = self._findColumnSettingsByKey(col.key);

				if (cs.allowResizing) {
					for (i = 0; i < level0.length; i++) {
						if (level0[ i ].key === col.key) {
							break;
						}
					}
					if (i < level0Length) {
						level0[ i ].allowResizing = true;
						level0[ i ].visibleIndex = index;
						level0[ i ].settings = cs;
					}
					th.col = level0[ i ];
				}
			});
		},
		_bindMultipleResizingHandle: function ($th, button) {
			var self = this, id = $th.attr("data-mch-id"), column;
			/* M.H. 4 July 2012 Fix for bug #116254 */
			column = this.grid._getMultiHeaderColumnById(id);
			button
				.mouseWrapper({
					distance: 5,
					start: function () {
						return self._startResizing($th);
					},
					drag: function (event) {
						return self._doResizingMultiColumnHeader(event.originalEvent, $th, column);
					},
					stop: function (event) {
						return self._stopResizingMiltiColumnHeader(event.originalEvent, $th, column);
					}
				})
				.bind("dblclick.resizing", function (event) {
					self._handleMouseMultiColumnHeaderDbClick(event, $th, column);
				})
				.bind("click.resizing", function (event) {
					event.preventDefault();
					event.stopPropagation();
				});
		},

		_bindResizingHandle: function (th, button, column) {
			var self = this;

			button
				.mouseWrapper({
					distance: 5,
					start: function () {
						return self._startResizing(th);
					},
					drag: function (event) {
						return self._doResizing(event.originalEvent, th, column);
					},
					stop: function (event) {
						return self._stopResizing(event.originalEvent, th, column);
					}
				})
				.bind("dblclick.resizing", function (event) {
					self._handleMouseDbClick(event, th, column);
				})
				.bind("click.resizing", function (event) {
					event.preventDefault();
					event.stopPropagation();
				});
		},
		_clearResizingHandles: function () {
			var i;

			if (this._resizingHandles) {
				for (i = 0; i < this._resizingHandles.length; i++) {
					this._resizingHandles[ i ].remove();
				}
				this._resizingHandles = [];
			}
		},
		_handleMouseDbClick: function (event, th, column) {
			if (!this.options.allowDoubleClickToResize) {
				return;
			}

			this._autoResize($.inArray(column, this.grid._visibleColumns()), true, event);
		},
		_handleMouseMultiColumnHeaderDbClick: function (event, $th, column) {
			var i, children = column.children,
				childrenLength = children.length;

			if (!this.options.allowDoubleClickToResize) {
				return;
			}

			for (i = 0; i < childrenLength; i++) {
				if (children[ i ].allowResizing === true) {
					this._autoResize(children[ i ].visibleIndex, true, event);
				}
			}
		},
		_autoResize: function (columnIndex, fireEvents, event) {
			var maxWidth = this.grid.calculateAutoFitColumnWidth(columnIndex);
			if (maxWidth > -1) {
				return this._resizeColumn(columnIndex, maxWidth, fireEvents, event);
			}
		},
		_cancelHoveringEffects: function (cancel) {
			var topmostGrid = this.grid.element.closest(".ui-iggrid-root").data("igGrid");

			if (topmostGrid === undefined || topmostGrid === null) {
				topmostGrid = this.grid;
			}

			topmostGrid._cancelHoveringEffects = cancel;
		},
		_startResizing: function (th) {
			var body = $(document.body), resizeLineTop, heightOffset;

			this._resizing = true;
			this._cancelHoveringEffects(true);

			/* A.Y. bug 81452. remove focus from the activeElement to trigger dropdowns to close */
			/* M.H. 19 Jul 2013 Fix for bug #147135: When resizing a column with an overlapping window, the window which contains the grid is sometimes set behind the other window. */
			if (!$.ig.util.isIE10) {
				$(document.activeElement).blur();
			} else {
				body.focus();
			}

			/* change the cursor while resizing */
			body.addClass(this.css.resizingHandleCursor);

			if (this.grid._isMultiColumnGrid) {
				heightOffset = th.offset().top - this._gridContainerPositioningOffset().top;
			}

			resizeLineTop = th.offset().top + this._gridContainerPositioningOffset().top;
			/* A.Y. bug 84856. */
			/* if we don't have fixed headers it is posible for the grid */
			/* to be scrolled vertically so that part of the header is hidden */
			if (this.grid.options.height !== null &&
					this.grid.options.showHeader === true &&
					this.grid.options.fixedHeaders === false) {
				resizeLineTop += this.grid.scrollContainer().scrollTop();
			}

			/* create the resizeLine */
			this._resizeLine = $("<div></div>")
				.addClass(this.css.columnResizeLine)
				/* the resize line's height should not be taken into account when */
				/* autoadjusting height as it is now in the grid container */
				.data("efh", "1")
				.css({
					"height": this._calculateGridResizableHeight(heightOffset),
					"top": resizeLineTop,
					"visibility": "hidden"
				})
				.appendTo(this.grid.container());

			/* M.K. 5/7/2015 Fix for bug 193339:Guiding line does not display when resizing columns if deferredResizing and paging are enabled. (IE8 only).*/
			/* IE8 doesn't repaint the element. This is a hack for forcing repaint in IE8. */
			if ($.ig.util.isIE8) {
				this.grid.container().attr("tabIndex", this.grid.container().attr("tabIndex"));
			}

			return true;
		},

		_doResizingMultiColumnHeader: function (event, $th, column) {
			var i, width, resizeCellRange,
				resizeResult,
				/* columnIndex = $.inArray(column, this.grid._visibleColumns()), */
				range, vI,
				offsetLeft,
				offsetHeight,
				children = column.children,
				childrenLength = children.length,
				child = null,
				cellToResize;
			width = event.pageX - $th.offset().left;
			if (width < 0) {
				width = 0;
			}
			offsetHeight = $th.offset().top - this.grid.headersTable().offset().top;
			if (!this.options.deferredResizing) {
				for (i = childrenLength - 1; i >= 0; i--) {
					if (children[ i ].allowResizing && !children[ i ].hidden) {
						child = children[ i ];
						child.resized = !child.resized;
						if (child.resized) {
							break;
						}
						/* if (child.isResized = ) */
					}
				}
				if (child !== null && !child.hidden) {
					cellToResize = $("#" + this.grid.element[ 0 ].id + "_" + child.key);
					vI = $.inArray(child, this.grid._visibleColumns());
					/* width = event.pageX - cellToResize.offset().left; */
					width = cellToResize[ 0 ].offsetWidth - $th[ 0 ].offsetWidth + width;
					resizeResult = this._resizeColumn(vI, width, true, event, vI);
				}
				if (!resizeResult) {
					return true;
				}
				if (!resizeResult.canceled) {
					/* update the line height as it may have changed due to the resizing */
					this._resizeLine.css("height", this._calculateGridResizableHeight(offsetHeight));

					/* update resizeLine position to match header right */
					this._resizeLine.css({
						"left":
							$th.offset().left +
							$th.outerWidth() +
							this._gridContainerPositioningOffset().left,
						"visibility": "visible"
					});
				}
			} else {
				offsetLeft = $th.offset().left;
				range = { min: offsetLeft, max: offsetLeft };
				for (i = 0; i < childrenLength; i++) {
					child = children[ i ];
					if (child.hidden) {
						continue;
					}

					if (child.allowResizing) {
						resizeCellRange = this._getRange(this.options.columnSettings[ child.visibleIndex ]);
						range.min += resizeCellRange.min;
						if (resizeCellRange.max === Infinity) {
							range.max = Infinity;
						} else if (range.max !== Infinity) {
							range.max += resizeCellRange.max;
						}
					} else {
						range.min += $("#" + this.grid.element[ 0 ].id + "_" + child.key)[ 0 ].offsetWidth;
					}
				}

				this._resizeLine.css("height", this._calculateGridResizableHeight(offsetHeight));
				this._resizeLine.css({
					"left":
						this._coerceRange(range, event.pageX) +
						this._gridContainerPositioningOffset().left,
					"visibility": "visible"
				});
			}

			return true;
		},

		_doResizing: function (event, th, column) {
			var width,
				resizeResult,
				columnIndex = $.inArray(column, this.grid._visibleColumns()),
				range,
				offsetLeft,
				offsetHeight;
			if (!this.options.deferredResizing) {
				width = event.pageX - th.offset().left;

				if (width < 0) {
					width = 0;
				}

				if (this.grid._isMultiColumnGrid) {
					offsetHeight = th.offset().top - this.grid.headersTable().offset().top;
				}

				resizeResult = this._resizeColumn(columnIndex, width, true, event);
				if (!resizeResult.canceled) {
					/* update the line height as it may have changed due to the resizing */
					this._resizeLine.css("height", this._calculateGridResizableHeight(offsetHeight));

					/* update resizeLine position to match header right */
					this._resizeLine.css({
						"left":
							th.offset().left +
							th.outerWidth() +
							this._gridContainerPositioningOffset().left,
						"visibility": "visible"
					});
				}
			} else {
				/* update resizeLine position to the postion of the mouse after range coercion */
				range = this._getRange(this.options.columnSettings[ columnIndex ]);
				offsetLeft = th.offset().left;
				range.min += offsetLeft;
				range.max += offsetLeft;

				this._resizeLine.css({
					"left":
						this._coerceRange(range, event.pageX) +
						this._gridContainerPositioningOffset().left,
					"visibility": "visible"
				});
			}

			return true;
		},
		_stopResizingMiltiColumnHeader: function (event, th, column) {
			var i, width, cs, columnKey,
				self = this,
				children,
				childrenLength,
				childrenLengthAllowResizing = 0,
				childrenToResize;

			if (this.options.deferredResizing) {
				/* M.H. 8 Nov 2012 Fix for bug #126554 */
				width = event.pageX - th.offset().left;
				children = column.children;
				childrenLength = children.length;
				childrenToResize = [];
				for (i = 0; i < childrenLength; i++) {
					columnKey = children[ i ].key;
					/* M.H. 4 Apr 2013 Fix for bug #138710: When deferredResizing = true on resizing root group of multiColumnHeaders in order to increase its size it gets smaller */
					cs = this._findColumnSettingsByKey(columnKey);
					if (children[ i ].allowResizing === false || (cs && cs.allowResizing === false)) {
						width -= $("#" + this.grid.element[ 0 ].id + "_" + columnKey)[ 0 ].offsetWidth;
						continue;
					}
					childrenToResize.push(children[ i ]);
					childrenLengthAllowResizing++;
				}
				if (childrenLengthAllowResizing > 0) {
					/* M.H. 1 Nov 2012 Fix for bug #120722 */
					this._resizeMCHDeffered(width, childrenToResize);
				}
			}
			/* remove the resizing cursor */
			$("body").removeClass(this.css.resizingHandleCursor);
			/* M.H. 4 Mar 2014 Fix for bug #165697: Control automatically sorts the records while resizing the column using mouse */
			setTimeout(function () {
				self._resizing = false;
			}, 0);
			this._cancelHoveringEffects(false);
			this._resizeLine.remove();
			this._resizeLine = undefined;

			return true;
		},
		/* M.H. 1 Nov 2012 Fix for bug #120722 */
		_resizeMCHDeffered: function (width, columns) {
			/* we set average width for each MCH cell - but for some of them (usually which has maximumWidth or minimumWidth) cells are not resized to the averageWidth */
			/* that's why we run again _resizeMCHDeffered to set the difference of these columns */
			var i, column, columnIndex, columnsLength = columns.length,
				visibleColumns = [], gridVC = this.grid._visibleColumns(),
				resizeInfo, newWidth = width, newColumnsToResize = [], avgWidth;

			if (columnsLength === 0) {
				return;
			}
			/* M.H. 4 Apr 2013 Fix for bug #138710: When deferredResizing = true on resizing root group of multiColumnHeaders in order to increase its size it gets smaller */
			/* First we need to get only visible and columns collection are visible so we can resize only them */
			for (i = 0; i < columnsLength; i++) {
				columnIndex = $.inArray(columns[ i ], gridVC);
				if (columnIndex === -1 || this.options.columnSettings[ columnIndex ].allowResizing === false) {
					continue;
				}
				visibleColumns.push({ column: column, columnIndex: columnIndex });
			}
			columnsLength = visibleColumns.length;
			if (columnsLength > 0) {
				/* M.H. 4 Apr 2013 Fix for bug #132976: Can"t resize parent column when child column is hidden and "deferredResizing" is true */
				avgWidth = parseInt((width / columnsLength), 10);
				for (i = 0; i < columnsLength; i++) {
					column = visibleColumns[ i ].column;
					columnIndex = visibleColumns[ i ].columnIndex;
					/* M.H. 15 Feb 2013 Fix for bug #132976 */
					resizeInfo = this._resizeColumn(columnIndex, avgWidth, true);
					if (resizeInfo.newWidth !== avgWidth) {
						newWidth -= resizeInfo.newWidth;
					} else {
						newColumnsToResize.push(column);
					}
				}
			}
			/* tollerance */
			if (newWidth > 5 && newWidth !== width && newColumnsToResize.length > 0) {
				this._resizeMCHDeffered(newWidth, newColumnsToResize);
			}
		},

		_stopResizing: function (event, th, column) {
			var width, self = this,
				columnIndex = $.inArray(column, this.grid._visibleColumns());

			if (this.options.deferredResizing) {
				width = event.pageX - th.offset().left;
				width = this._coerceRange(this._getRange(this.options.columnSettings[ columnIndex ]), width);

				this._resizeColumn(columnIndex, width, true, event);
			}
			/* remove the resizing cursor */
			$("body").removeClass(this.css.resizingHandleCursor);
			/* M.H. 4 Mar 2014 Fix for bug #165697: Control automatically sorts the records while resizing the column using mouse */
			setTimeout(function () {
				self._resizing = false;
			}, 0);
			this._cancelHoveringEffects(false);
			this._resizeLine.remove();
			this._resizeLine = undefined;

			return true;
		},
		_resizeColumn: function (columnIndex, width, fireEvents, originalEvent, startIndex) {
			var gridWidth, minimalVisibleAreaWidth = 0, widthFixedContainer,
				visibleColumns = this.grid._visibleColumns(),
				columnKey = visibleColumns[ columnIndex ].key,
				typeW, hasWidthPx, w, tmpW,
				visibleIndex = columnIndex,
				headersTable = this.grid.options.showHeader ? this.grid.headersTable() :
															this.grid.element,
				cs, isFixed, headers, headerWidth, headerColumns,
				columnSettings = this.options.columnSettings,
				hasFixedCols = this.grid.hasFixedColumns(),
				columnsLength, headerColStyleWidth, hasPercentageWidth,
				actualColumnStyleWidths = [],
				actualColumnWidths = [],
				requiredColumnPercentageWidths = [],
				newColumnStyleWidths = [],
				i,
				widthToDistribute,
				shrinkColumns,
				widthDistributed,
				widthUsed,
				coercedWidth,
				widthPerColumn,
				range,
				totalWidth,
				readyColumns,
				readyColumnsCount,
				finalPixelWidth,
				allColumnsHaveWidth,
				noCancel,
				containerWidth,
				isResized = true;
			/* in case of resizing column that has been autoadjusted(it has been last visible column and its "original" width is cached) */
			delete visibleColumns[ columnIndex ].oWidth;
			if (hasFixedCols) {
				visibleIndex = this.grid.getVisibleIndexByKey(columnKey);
				isFixed = visibleColumns[ columnIndex ].fixed === true;
				if (isFixed) {
					headersTable = this.grid.fixedHeadersTable();
				}
			}
			if (this.grid._isMultiColumnGrid) {
				headers = $(this.grid._headerCells);
				/* M.H. 4 Dec 2013 Fix for bug #157949: Resizing on child band when showHeaders is false throws a js error */
				headerWidth = (headers.length > 0) ? $(headers[ columnIndex ])[ 0 ].offsetWidth : headerWidth;
			} else {
				/* M.H. 14 Feb 2013 Fix for bug #132840 */
				if (this.grid.options.virtualization === true || this.grid.options.rowVirtualization === true) {
					headers = headersTable.find("> thead > tr").first().children("th").not("[data-skip=true]");
				} else {
					headers = headersTable.find("> thead > tr[data-header-row]").first()
						.children("th").not("[data-skip=true]");
				}
				/* M.H. 13 Jun 2016 Fix for bug 220563: Exception is thrown when resizing column with API resize method and showHeaders option is set to false */
				if (!this.grid.options.showHeader) {
					// in case of width is in percentage
					headers = headersTable
						.find("tbody>tr:not([data-container='true'],[data-grouprow='true']):first")
						.children("td").not("[data-skip=true]");
				}
				/* M.H. 4 Dec 2013 Fix for bug #157949: Resizing on child band when showHeaders is false throws a js error */
				headerWidth = (headers.length > 0) ? headers.get(visibleIndex).offsetWidth : headerWidth;
			}
			headerColumns = headersTable.find("> colgroup > col").not("[data-skip=true]");
			columnSettings = this.options.columnSettings;
			columnsLength = headerColumns.length;
			headerColStyleWidth = headerColumns[ visibleIndex ].style.width;
			hasPercentageWidth = /%$/.test(headerColStyleWidth);
			/* M.H. 19 Nov 2014 Fix for bug #185367: When user extends a column by draging, other columns are shrunk and collapsed regardress of the minimumWidth settings. */
			if (headerColStyleWidth === "") {
				hasPercentageWidth = true;
				headerColumns.each(function (ind, col) {
					if (col.style.width !== "") {
						hasPercentageWidth = false;
						return false;
					}
				});
			}
			if (fireEvents) {
				noCancel = this._trigger(this.events.columnResizing, originalEvent,
					{
						owner: this,
						columnIndex: columnIndex,
						columnKey: columnKey,
						desiredWidth: width
					});

				if (!noCancel) {
					return {
						canceled: true,
						originalWidth: width,
						newWidth: width
					};
				}
			}
			/* M.H. 11 Feb 2014 Fix for bug #164074: Resizing column settings are not taken into account for a column if there is an initially hidden column with a lower column index than the configured column */
			if (columnKey !== undefined) {
				cs = this._findColumnSettingsByKey(columnKey);
			} else {
				cs = columnSettings[ columnIndex ];
			}
			range = this._getRange(cs);
			/*coerce width within min/max */
			width = this._coerceRange(range, width);

			/*make sure width is an integer */
			width = Math.floor(width);
			if (width === range.min || width === range.max) {
				isResized = false;
			}
			/* check whether unfixed area has reached its min visible width */
			if (isFixed) {
				widthToDistribute = headerWidth - width;
				gridWidth = parseInt(this.grid.options.width, 10);
				if (isNaN(gridWidth) || this.grid._gridHasWidthInPercent()) {
					gridWidth = this.grid.container().outerWidth();
				}
				if (widthToDistribute < 0) {
						minimalVisibleAreaWidth = this._columnFixing.options.minimalVisibleAreaWidth;
					if (this._columnFixing._isVirtualGrid()) {
						widthFixedContainer = this.grid._virtualcontainer()
							.find("colgroup:first>col[data-fixed-col]").width();
					} else {
						widthFixedContainer = this.grid.fixedContainer().outerWidth();
					}
					if (widthFixedContainer - widthToDistribute >
							gridWidth - this.grid._scrollbarWidth() - minimalVisibleAreaWidth) {
						if (fireEvents) {
							this._trigger(this.events.columnResizingRefused, originalEvent,
							{
								owner: this,
								columnIndex: columnIndex,
								columnKey: columnKey,
								desiredWidth: width
							});
						}
						return {
							canceled: true,
							originalWidth: width,
							newWidth: width
						};
					}
				}
			}
			/* M.H. 21 Mar 2013 Fix for bug #129578: Chrome: When columns are set in percent values and resizing is executed some of the columns are resized incorrectly */
			/* M.H. 21 Mar 2013 Fix for bug #129578: Chrome: When columns are set in percent values and resizing is executed some of the columns are resized incorrectly */
			if ($.ig.util.isWebKit && hasPercentageWidth) {
				totalWidth = headersTable[ 0 ].offsetWidth;
				for (i = 0; i < columnsLength; i++) {
					/* M.H. 19 Nov 2014 Fix for bug #185367: When user extends a column by draging, other columns are shrunk and collapsed regardress of the minimumWidth settings. */
					w = headerColumns[ i ].style.width;
					if (w === "") {
						actualColumnWidths[ i ] = parseFloat(this.grid._isMultiColumnGrid ?
															headers[ i ][ 0 ].offsetWidth :
															headers[ i ].offsetWidth);
					} else {
						actualColumnWidths[ i ] = w.indexOf("%") > 0 ?
							(parseFloat(w) / 100) * totalWidth :
							parseFloat(w);
					}
				}
			} else if (headers.length > 0) {
				/* M.H. 4 Dec 2013 Fix for bug #157949: Resizing on child band when showHeaders is false throws a js error */
				for (i = 0; i < columnsLength; i++) {
					actualColumnStyleWidths[ i ] = headerColumns[ i ].style.width;
					actualColumnWidths[ i ] = this.grid._isMultiColumnGrid ?
						headers[ i ][ 0 ].offsetWidth :
						headers[ i ].offsetWidth;
				}
			}
			if (hasPercentageWidth) {
				/* negative if the resized column grows, positive if it shrinks */
				widthToDistribute = headerWidth - width;
				shrinkColumns = widthToDistribute < 0;

				readyColumns = [];
				readyColumnsCount = 0;
				widthDistributed = 0;
				if (startIndex === undefined || startIndex === null) {
					startIndex = 0;
				}

				/* distrubiting the width until it reaches close to 0 or all columns reach their min/max */
				while (readyColumnsCount < columnsLength - 1 - startIndex &&
						((shrinkColumns && widthToDistribute < -0.05) ||
						(!shrinkColumns && widthToDistribute > 0.05))) {
					/* calculate how much each column should receive */
					widthPerColumn = widthToDistribute / (columnsLength - 1);

					for (i = startIndex; i < columnsLength; i++) {
						/* ignore the resizedColumn and ready columns */
						if (i !== columnIndex && !readyColumns[ i ]) {

							/* taking into account the fact that due to rounding we may have less to distribute then the average per column */
							if (shrinkColumns) {
								widthUsed = Math.max(widthPerColumn, widthToDistribute);
							} else {
								widthUsed = Math.min(widthPerColumn, widthToDistribute);
							}

							actualColumnWidths[ i ] += widthUsed;

							range = this._getRange(columnSettings[ i ]);
							coercedWidth = this._coerceRange(range, actualColumnWidths[ i ]);

							/* as we are dealing with floating point values we should chech for equility within an Epsilon region */
							if (Math.abs(actualColumnWidths[ i ] - coercedWidth) > 0.000005) {
								/* update the widthUsed as we didn't take as much as we wanted */
								widthUsed -= actualColumnWidths[ i ] - coercedWidth;

								actualColumnWidths[ i ] = coercedWidth;

								readyColumns[ i ] = true;
								readyColumnsCount++;
							}

							widthDistributed += widthUsed;
							widthToDistribute -= widthUsed;
						}
					}
				}

				/* remove the distributed width from the size of the resized column */
				/* this will be its final width */
				actualColumnWidths[ columnIndex ] -= widthDistributed;

				totalWidth = 0;
				for (i = 0; i < columnsLength; i++) {
					totalWidth += actualColumnWidths[ i ];
				}

				for (i = 0; i < columnsLength; i++) {
					requiredColumnPercentageWidths[ i ] = 100 * actualColumnWidths[ i ] / totalWidth;
				}

				/* save the desired widths for each column */
				for (i = 0; i < columnsLength; i++) {
					newColumnStyleWidths[ i ] = requiredColumnPercentageWidths[ i ] + "%";
					visibleColumns[ i ].width = newColumnStyleWidths[ i ];
				}

				finalPixelWidth = actualColumnWidths[ columnIndex ];

				this._applyToEachGridCOL(function (index, col) {
					col.css("width", newColumnStyleWidths[ index ]);
				}, isFixed);
			} else {
				allColumnsHaveWidth = true;
				for (i = 0; i < columnsLength; i++) {
					if (i === visibleIndex) {
						/* set the width of the resized column */
						newColumnStyleWidths[ i ] = width + "px";
						finalPixelWidth = width;
						/* M.H. 21 Nov 2014 Fix for bug #185417: Value type of column width changes after resize method */
						typeW = $.type(visibleColumns[ columnIndex ].width);
						hasWidthPx = (typeW === "string" && visibleColumns[ columnIndex ].width.indexOf("px") > 0);
						if (typeW === "number") {
							visibleColumns[ columnIndex ].width = width;
						} else if (typeW === "string") {
							if (!hasWidthPx) {
								visibleColumns[ columnIndex ].width = String(width);
							} else {
								visibleColumns[ columnIndex ].width = newColumnStyleWidths[ i ];
							}
						} else {
							visibleColumns[ columnIndex ].width = newColumnStyleWidths[ i ];
						}
					} else {
						newColumnStyleWidths[ i ] = actualColumnStyleWidths[ i ];
						allColumnsHaveWidth =
							allColumnsHaveWidth && parseInt(actualColumnStyleWidths[ i ], 10) > 0;
					}
				}

				this._applyToEachGridCOL(function (index, col) {
					col.css("width", newColumnStyleWidths[ index ]);
				}, isFixed);

				containerWidth = this.grid._calculateContainerWidth(false);
				if (allColumnsHaveWidth) {
					if (this.grid.options.width && parseInt(this.grid.options.width, 10) > 0) {
						if (!isFixed) {
							this.grid._updateGridContentWidth();
						}
						$("#" + this.grid.element[ 0 ].id + "_horizontalScrollContainer")
							.children("div")
							.css("width", containerWidth);
						/* M.H. 6 Aug 2013 Fix for bug #145572: When Resizing with continuous rowVirtualization no horizontal scrollbar appears when needed */
						if (this.grid.options.virtualization === true ||
								this.grid.options.rowVirtualization === true) {
							this.grid._oldScrollLeft = $("#" + this.grid.id() +
								"_horizontalScrollContainer").scrollLeft();
						}
						/* M.H. 17 Nov 2015 Fix for bug 209550: Resizing one column affect other column widths if virtualization is enaled */
						if (this.grid.options.rowVirtualization || this.grid.options.virtualization === true) {

							tmpW = containerWidth - this.grid._scrollbarWidth();

							$("#" + this.grid.element[ 0 ].id + "_headers")
								.css("width", containerWidth)
								.css("max-width", containerWidth);
							this.grid.element
								.css("width", tmpW)
								.css("max-width", tmpW);
							$("#" + this.grid.id() + "_footers")
								.css("width", containerWidth)
								.css("max-width", containerWidth);
						}
					} else {
						/* M.H. 7 Jun 2013 Fix for bug #144153: Resizing is not working properly when there are fixed column(s) and width is not set */
						if (hasFixedCols && !isFixed) {
								this.grid._updateGridContentWidth();
							}
						/* M.H. 19 Sep 2013 Fix for bug #152604: Resizing the grid columns cause javascript error */
						this.grid._setContainerWidth($("#" + this.grid.id() + "_container"));

						if (this.grid.options.rowVirtualization || this.grid.options.virtualization === true) {
							/* A.Y. bug 91641. When we have rowVirtualization we need to adjust */
							/* the displayContainer and the virtualContainer first columns as well. */
							tmpW = containerWidth + this.grid._scrollbarWidth();
							/* I.I. bug fix for 106920 */
							$("#" + this.grid.element[ 0 ].id + "_headers_v")
								.css("width", tmpW)
								.css("max-width", tmpW);

							$("#" + this.grid.element[ 0 ].id + "_displayContainer")
								.css("width", containerWidth)
								.css("max-width", containerWidth);
							$("#" + this.grid.element[ 0 ].id + "_virtualContainer > colgroup > col")
								.first()
								.attr("width", containerWidth);
								/* M.H. 15 Aug 2013 Fix for bug #149242: Misalignment when resizing grid with summaries and virtualization */
							$("#" + this.grid.id() + "_footer_container")
								.css("width", tmpW)
								.css("max-width", tmpW);
						}
					}
				}
			}
			/* M.H. 26 Jun 2013 Fix for bug #143357: The fixed area becomes misaligned when you resize it enough to increase its header's height. */
			if (hasFixedCols) {
				/* M.H. 22 Jul 2013 Fix for bug #147064: When column is resized and the content of the cells is distributed on two rows the rows from both areas are misaligned. */
				this._columnFixing._containerResized(isFixed, widthToDistribute);
			}
			if (fireEvents) {
				this._trigger(this.events.columnResized, originalEvent,
					{
						owner: this,
						columnIndex: columnIndex,
						columnKey: columnKey,
						originalWidth: headerWidth,
						newWidth: finalPixelWidth
					});
			}

			return {
				canceled: false,
				originalWidth: headerWidth,
				newWidth: finalPixelWidth,
				isResized: isResized
			};
		},
		_applyToEachGridCOL: function (appliedFunction, isFixed) {
			var headersTable, footersTable;
			if (this.grid.options.showHeader) {
				if (isFixed) {
					headersTable = this.grid.fixedHeadersTable();
				} else {
					headersTable = this.grid.headersTable();
				}
				headersTable.find("> colgroup > col").not("[data-skip=true]").each(function (i) {
					appliedFunction(i, $(this));
				});
			}

			/* update the data table if it is different from the headers table or we are not showing headers */
			if ((this.grid.options.fixedHeaders === true && this.grid.options.height !== null) ||
					this.grid.options.showHeader === false) {
				if (isFixed) {
					$("#" + this.grid.id() + "_fixed").find("> colgroup > col")
							.not("[data-skip=true]").each(function (i) {
						appliedFunction(i, $(this));
					});
				} else {
					this.grid.element.find("> colgroup > col").not("[data-skip=true]").each(function (i) {
						appliedFunction(i, $(this));
					});
				}
			}

			/* update the footers table if we have one */
			if (this.grid.options.fixedFooters === true && this.grid.options.height !== null) {
				if (isFixed) {
					footersTable = this.grid.fixedFootersTable();
				} else {
					footersTable = this.grid.footersTable();
				}
				footersTable.find("> colgroup > col").not("[data-skip=true]").each(function (i) {
					appliedFunction(i, $(this));
				});
			}
		},
		_getRange: function (column) {
			var min = column.minimumWidth,
				max = column.maximumWidth,
			    gridWidth;
			if ($.type(min) === "string" && min.indexOf("%") > 0) {
			    gridWidth = this.grid.element.width();
			    min = (parseInt(min, 10) * gridWidth) / 100;
			}
			if ($.type(max) === "string" && max.indexOf("%") > 0) {
			    gridWidth = this.grid.element.width();
			    max = (parseInt(max, 10) * gridWidth) / 100;
			}
			min = isNaN(min) ? 0 : min;
			min = Math.max(0, min);
			max = isNaN(max) ? Infinity : max;
			return { "min": min, "max": max };
		},
		_coerceRange: function (range, value) {
			value = Math.max(range.min, value);
			value = Math.min(range.max, value);
			return value;
		},
		_gridContainerPositioningOffset: function () {
            /* This method calculates how much we should add to a position */
			/* relative to the document(jQuery.offset()) to express it as top/left values for an */
			/* absolutely positioned element within the grid container(this.grid.container()). */
			/* We need this to fix bug #94177 which requires the resizeLine to be absolutely */
			/* positioned within the grid container instead of the document body. */
			var gridContainer = this.grid.container(),
				containerPosition = gridContainer.css("position"),
				gridContainerOffsetParent = gridContainer.offsetParent(),
				gridContainerPosition = gridContainer.position(),
				gridContainerOffset = gridContainer.offset(),
				offsetParentScrollTop,
				offsetParentScrollLeft;
			/* M.H. 16 Jul 2014 Fix for bug #175900: Resizing columns in a virtual grid positions the seperation line offset to the left and above of the column border */
			if (containerPosition === "relative" || containerPosition === "absolute") {
				return {
					top: -gridContainerOffset.top,
					left: -gridContainerOffset.left
				};
			}
			/* A.Y. bug #99092. Since we are positioning the resize line relatively if the offset parent  */
			/* is the "body" the scrollTop/Left should be ignored and indeed in IE/FF/Opera the  */
			/* scrollTop/Left is always 0 but in WebKit browsers the body is the scrollable element. */
			if (gridContainerOffsetParent.is("body")) {
				offsetParentScrollTop = 0;
				offsetParentScrollLeft = 0;
			} else {
				offsetParentScrollTop = gridContainerOffsetParent.scrollTop();
				offsetParentScrollLeft = gridContainerOffsetParent.scrollLeft();
			}
			return {
				top:
					offsetParentScrollTop +
					gridContainerPosition.top -
					gridContainerOffset.top,
				left:
					offsetParentScrollLeft +
					gridContainerPosition.left -
					gridContainerOffset.left
			};
		},
		_calculateGridResizableHeight: function (heightOffset) {
			/* L.A. 23 April 2012 Fixing bug #99471 When grid has row virtualization resize line height is not correct */
			var height, caption, headersTable, footersTable, scrollerContainer,
				hasVirtualization = this.grid.options.virtualization === true ||
				this.grid.options.rowVirtualization === true ||
				this.grid.options.columnVirtualization === true,
				hasWidthOrHeight = this.grid.options.height !== null ||
				this.grid.options.width !== null;
			if (hasVirtualization) {
				height = $("#" + this.grid.element[ 0 ].id + "_displayContainer").height();
			} else if (hasWidthOrHeight) {
				height = this.grid.scrollContainer().height();
			} else {
				height = this.grid.element.height();
			}
			if (hasVirtualization || hasWidthOrHeight) {
				headersTable = this.grid.headersTable();
				footersTable = this.grid.footersTable();
				if (this.grid.options.fixedHeaders === true && this.grid.options.showHeader === true) {
					if (headersTable.length !== 0 && this.grid.element[ 0 ].id !== headersTable[ 0 ].id) {
						height += headersTable.height();
					}
					/* in this case the caption is in the headersTable */
					caption = headersTable.children("#" + this.grid.element[ 0 ].id + "_caption");
					if (caption.length !== 0) {
						/* A.Y. bug 98449. FF has a bug and does not include the height of the caption when it calculates the table height. */
						if (!$.ig.util.isFF) {
							height -= caption.outerHeight(true);
						}
					}
				}
				/* L.A. 08 May 2012 Fixing bug #111034 When in grid fixedHeaders is false and column resizing and summaries are enabled, the resize line does not appear correctly */
				scrollerContainer = $("#" + this.element[ 0 ].id + "_hscroller_container");
				if (scrollerContainer.is(":visible")) {
					height += scrollerContainer.height();
				}
				if (this.grid.options.fixedFooters === true &&
						this.grid.options.showFooter === true &&
						footersTable.length !== 0 &&
						this.grid.element[ 0 ].id !== footersTable[ 0 ].id) {
					height += footersTable.height();
				}
			}
			if (heightOffset) {
				height -= heightOffset;
			}
			return height;
		},
		_findColumnSettingsByKey: function (key, settings) {
			var i;
			if (!settings) {
				settings = this.options.columnSettings;
			}
			for (i = 0; i < settings.length; i++) {
				if (settings[ i ].columnKey === key) {
					return settings[ i ];
				}
			}
		},
		_initDefaultSettings: function () {
			var settings = [], key, cs = this.options.columnSettings, i, j, mch, s;

			/* create default setting for each grid column */
			if (this.grid.options.columns && this.grid.options.columns.length > 0) {
				for (i = 0; i < this.grid.options.columns.length; i++) {
					settings[ i ] =
						{
							"columnIndex": i,
							"columnKey": this.grid.options.columns[ i ].key,
							"allowResizing": true,
							"minimumWidth": 20
						};
				}
			}

			for (i = 0; i < cs.length; i++) {
				/* find the corresponding column settings */
				for (j = 0; j < settings.length; j++) {
					if (settings[ j ].columnKey === cs[ i ].columnKey ||
							settings[ j ].columnIndex === cs[ i ].columnIndex) {
						/* foundByKey = settings[ j ].columnKey === cs[ i ].columnKey; */
						break;
					}
				}
				/* not found, continue */
				if (j === settings.length) {
					/* in MCH scenario when in columnSettings it is set allowResizing for group column - we should set allowResizing for children columns */
					/* M.H. 8 June 2015 Fix for bug 194610: Multi column headers cannot be made unresizible */
					if (this.grid._isMultiColumnGrid && cs[ i ].allowResizing === false) {
						mch = this.grid._getMultiHeaderColumnById(cs[ i ].columnKey);
						if (mch && mch.children) {
							mch.allowResizing = false;
							for (j = 0; j < mch.children.length; j++) {
								s = this._findColumnSettingsByKey(mch.children[ j ].key, settings);
								if (s) {
									s.allowResizing = false;
								}
								mch.children[ j ].allowResizing = false;
							}
						}
					}
					continue;
				}

				/* copy properties */
				for (key in cs[ i ]) {
					if (cs[ i ].hasOwnProperty(key) && key !== "columnIndex" && key !== "columnKey") {
						settings[ j ][ key ] = cs[ i ][ key ];
					}
				}
			}
			/* cache original columnSettings(in case of MCH to get allowResizing property for MCH column) */
			this._oColumnSettings = this.options.columnSettings;
			this.options.columnSettings = settings;
		},
		_injectGrid: function (gridInstance) {
			this.grid = gridInstance;

			/*M.K. 1/9/2015 187174:Errors should be thrown when the grid is initialized with unsupported configurations */
			this._checkGridNotSupportedFeatures();

			/* M.H. 2 Sep 2013 Fix for bug #150449: After rebinding the grid many times on interval the gird becomes unresponsive. */
			this.grid.element.unbind(".resizing");
			this._initDefaultSettings();

			/* register for headerRendered	 */
			this.grid.element.bind("iggridheaderrendered.resizing", $.proxy(this._headerRendered, this));

			/* register for columnsCollectionModified */
			this.grid.element.bind("iggridcolumnscollectionmodified.resizing",
				$.proxy(this._columnsCollectionModified, this));

			/* register for columnsMoved */
			this.grid.element.bind("iggrid_columnsmoved.resizing", $.proxy(this._columnsMoved, this));
		},
		_checkGridNotSupportedFeatures: function () {
			/* Throw an exception for unsupported integration scenarios */
			var gridOptions = this.grid.options;
			if ((gridOptions.virtualization === true ||
					gridOptions.columnVirtualization === true) &&
					gridOptions.virtualizationMode === "fixed") {
				/* The column resizing feature does not work when fixed virtualization is enabled. */
				throw new Error($.ig.GridResizing.locale.resizingAndFixedVirtualizationNotSupported);
			}
		}
	});
	$.extend($.ui.igGridResizing, { version: "16.1.20161.2145" });
}(jQuery));
