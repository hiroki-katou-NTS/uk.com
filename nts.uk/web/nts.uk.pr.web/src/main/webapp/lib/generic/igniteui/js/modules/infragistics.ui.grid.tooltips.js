/*!@license
 * Infragistics.Web.ClientUI Grid Tooltips 16.1.20161.2145
 *
 * Copyright (c) 2011-2016 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 *	jquery-1.9.1.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	infragistics.ui.popover.js
 *	infragistics.ui.grid.framework.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igGridTooltips widget.
		The widget is pluggable to the element where the grid is instantiated and the actual igGrid object doesn't know about this.
		The tooltips widget just attaches its functionality to the grid.
	*/
	"use strict";
	$.widget("ui.igGridTooltips", {
		options: {
			/* type="always|never|overflow" determines the tooltip visibility option
			always type="string" tooltips always show for hovered elements
			never type="string" tooltips do not show automatically
			overflow type="string" tooltips show only when the underlying data overflows its container
			*/
			visibility: "overflow",
			/* type="tooltip|popover" controls the tooltip's style
			tooltip type="string" The tooltip will be positioned according to the mouse cursor. Will render the tooltip content as plain text.
			popover type="string" The tooltip will be positioned according to the target element with an arrow pointing the element. This style is more suitable for touch-supported environments. Will render the tooltip content as HTML.
			*/
			style: "tooltip",
			/* type="integer" The time in milliseconds after which tooltip will show when
			mouse cursor is hovered over a cell. */
			showDelay: 500,
			/* Type="integer" The time in milliseconds after which tooltip hides when mouse
			cursor gets outside of the cell.
			*/
			hideDelay: 300,
			/* a list of custom column settings that specify custom tooltip settings for a specific column (whether tooltips are enabled / disabled) */
			columnSettings: [
				{
					/* type="string" Either key or index must be set in every column setting.*/
					columnKey: null,
					/* type="number" Either key or index must be set in every column setting. */
					columnIndex: -1,
					/* type="bool" Enables / disables tooltips on the specified column. By default tooltips are displayed for each column. Note: This option is mandatory. */
					allowTooltips: true,
					/* type="number" Specifies the maximum width (in pixels) of the tooltip when shown for the specified column. If unset the width of the column will be used instead. */
					maxWidth: null
				}
			],
			/* sets the time tooltip fades in and out when showing/hiding */
			fadeTimespan: 150,
			/* sets the left position of the tooltip relative to the mouse cursor */
			cursorLeftOffset: 10,
			/* sets the top position of the tooltip relative to the mouse cursor */
			cursorTopOffset: 15,
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		css: {
			/* Classes applied to the tooltip widget */
			"tooltip": "ui-iggrid-tooltip",
			/* Classes applied to the tooltip container */
			"tooltipContent": "ui-iggrid-tooltip-content"
		},
		events: {
			/* cancel="true" event fired when the mouse has hovered on an element long enough to display a tooltip
			use args.owner to get a reference to the widget
			use args.tooltip to get or set the string to be displayed
			use args.value to get the value of the cell the tooltip is displayed for
			use args.element to get a reference to the cell the tooltip is displayed for
			use args.index to get the row index of the cell the tooltip is displayed for
			use args.columnKey to get the column key of the cell the tooltip is displayed for
			use args.columnIndex to get the column index of the cell the tooltip is displayed for
			*/
			tooltipShowing: "tooltipShowing",
			/* event fired after a tooltip is shown
			use args.owner to get a reference to the widget
			use args.tooltip to get the string displayed in the tooltip
			use args.value to get the value of the cell the tooltip is displayed for
			use args.element to get a reference to the cell the tooltip is displayed for
			use args.index to get the row index of the cell the tooltip is displayed for
			use args.columnKey to get the column key of the cell the tooltip is displayed for
			use args.columnIndex to get the column index of the cell the tooltip is displayed for
			*/
			tooltipShown: "tooltipShown",
			/* cancel="true" event fired when the mouse has left an element and the tooltip is about to hide
			use args.owner to get a reference to the widget
			use args.tooltip to get the string displayed in the tooltip
			use args.value to get the value of the cell the tooltip is displayed for
			use args.element to get a reference to the cell the tooltip is displayed for
			use args.index to get the row index of the cell the tooltip is displayed for
			use args.columnKey to get the column key of the cell the tooltip is displayed for
			use args.columnIndex to get the column index of the cell the tooltip is displayed for
			*/
			tooltipHiding: "tooltipHiding",
			/* event fired after a tooltip is hidden
			use args.owner to get a reference to the widget
			use args.tooltip to get the string displayed in the tooltip
			use args.value to get the value of the cell the tooltip was displayed for
			use args.element to get a reference to the cell the tooltip was displayed for
			use args.index to get the row index of the cell the tooltip was displayed for
			use args.columnKey to get the column key of the cell the tooltip was displayed for
			use args.columnIndex to get the column index of the cell the tooltip was displayed for
			*/
			tooltipHidden: "tooltipHidden"
		},
		_createWidget: function () {
			this.visible = false;
			this._canFadeIn = true;
			this._canFadeOut = true;
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		destroy: function () {
			/*
				Destroys the tooltip widget.
			*/
			this.grid.element.unbind(".tooltip");
			if (this.tooltip && this._registered) {
				if (this.tooltip.data("users") === 1) {
					this.tooltip.remove();
				} else {
					this.tooltip.data("users", this.tooltip.data("users") - 1);
					this.tooltip.children("[id$='_content']")
						.unbind("pointerenter." + this.grid.id())
						.unbind("MSPointerEnter." + this.grid.id())
						.unbind("pointerleave." + this.grid.id())
						.unbind("MSPointerLeave." + this.grid.id())
						.unbind("mouseenter." + this.grid.id())
						.unbind("mouseleave." + this.grid.id());
				}
			}
			if (this.ruler && this._registered) {
				if (this.ruler.data("users") === 1) {
					this.ruler.remove();
				} else {
					this.ruler.data("users", this.ruler.data("users") - 1);
				}
			}
			this._registered = false;
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		id: function () {
			/*  returns the ID of the parent div element bounding the ruler and the tooltip container
				returnType="string"
			*/
			return this.tooltip[ 0 ].id;
		},
		_injectGrid: function (gridInstance, isRebind) {
			if (isRebind === true) {
				return;
			}
			this.grid = gridInstance;
			this._createHandlers();
			this._unregisterGridEvents();
			this._registerGridEvents();
		},
		_createTooltip: function () {
			var existingTooltip = this.grid._rootContainer().children("[id$='_tooltips']"),
				tooltipContent, closeButton = $.ig.util.isTouch;
			if (existingTooltip.length > 0) {
				this.tooltip = existingTooltip;
				this.tooltip.data("users", this.tooltip.data("users") + 1);
			} else {
				if (this.options.style === "tooltip") {
					this.tooltip = $("<div></div>")
						.attr("id", this.grid.id() + "_tooltips")
						.attr("role", "tooltip")
						.addClass("ui-tooltip")
						.addClass("ui-widget")
						.addClass("ui-corner-all")
						.addClass("ui-widget-content")
						.addClass(this.css.tooltip)
						.css({
							"position": "absolute",
							"display": "none",
							"white-space": "normal",
							/* S.S. September 28, 2011 Bug #86596 Long words need to be broken
							down so they don"t overlap the container. */
							"word-wrap": "break-word"
						});
					/* create content container */
					$("<div></div>")
						.attr("id", this.id() + "_content")
						.addClass("ui-tooltip-content")
						.addClass(this.css.tooltipContent)
						.appendTo(this.tooltip);
				} else if (this.options.style === "popover") {
					this.tooltip = $("<div><div>")
						.attr("id", this.grid.id() + "_tooltips")
						.igPopover({
							animationDuration: this.options.fadeTimespan,
							maxHeight: "auto",
							headerTemplate: {
								closeButton: closeButton
							}
						});
				}
				this.tooltip.data("users", 1);
			}
			/* bind content events */
			if (this.options.style === "tooltip") {
				tooltipContent = this.tooltip.children("[id$='_content']");
				if (window.navigator.msPointerEnabled || window.navigator.pointerEnabled) {
					tooltipContent
						.bind("MSPointerEnter." + this.grid.id(), $.proxy(this._tooltipMouseEnter, this))
						.bind("pointerenter." + this.grid.id(), $.proxy(this._tooltipMouseEnter, this))
						.bind("MSPointerLeave." + this.grid.id(), $.proxy(this._tooltipMouseLeave, this))
						.bind("pointerleave." + this.grid.id(), $.proxy(this._tooltipMouseLeave, this));
				} else {
					tooltipContent
						.bind("mouseenter." + this.grid.id(), $.proxy(this._tooltipMouseEnter, this))
						.bind("mouseleave." + this.grid.id(), $.proxy(this._tooltipMouseLeave, this));
				}
			}
			this.tooltip.appendTo(this.grid._rootContainer());
		},
		_createRuler: function () {
			var existingRuler = this.grid._rootContainer().children("[id$='_ruler']");
			if (existingRuler.length > 0) {
				this.ruler = existingRuler;
				this.ruler.data("users", this.ruler.data("users") + 1);
			} else {
				this.ruler = $("<div></div>")
					.attr("id", this.id() + "_ruler")
					.css({
						"visibility": "hidden",
						"position": "absolute"
					})
					.prependTo(this.grid._rootContainer());
				this.ruler.data("users", 1);
			}
		},
		_displayOverflow: function (element) {
			var ruler = this.ruler, cellTextHeight, dcell = element[ 0 ];
			ruler.text(element.text());
			ruler.css({
				"font-family": element.css("font-family"),
				"font-size": element.css("font-size"),
				"font-size-adjust": element.css("font-size-adjust"),
				"font-stretch": element.css("font-stretch"),
				"font-style": element.css("font-style"),
				"font-variant": element.css("font-variant"),
				"font-weight": element.css("font-weight"),
				"border-left": element.css("border-left"),
				"border-right": element.css("border-right"),
				"padding-left": element.css("padding-left"),
				"padding-top": element.css("padding-top"),
				"padding-right": element.css("padding-right"),
				"padding-bottom": element.css("padding-bottom"),
				"overflow": element.css("overflow"),
				"white-space": element.css("white-space"),
				"word-break": element.css("word-break")
			});
			ruler.width(element.width());
			cellTextHeight = ruler.height();
			ruler.css({
				"overflow": "visible",
				"white-space": "normal",
				"word-break": "break-all"
			});
			/* if the ruler's height is larger the cell cannot fit it's contents */
			return dcell.offsetWidth < dcell.scrollWidth ||
				dcell.scrollWidth > dcell.clientWidth ||
				cellTextHeight < ruler.height();
		},
		_gridHeaderRendering: function (event, ui) {
			if (ui.owner.id() !== this.grid.id()) {
				return;
			}
			this._createTooltip();
			this._createRuler();
			this._registered = true;
		},
		_dataRendered: function (event, ui) {
			var $tbody, $tbodyFixed;
			if (ui !== undefined) {
				if ($(ui.owner.element).attr("id") !== this.grid.id()) {
					return;
				}
			}
			if (!this.tooltip) {
				this._createTooltip();
				this._createRuler();
				this._registered = true;
			}
			$tbody = this.grid.element.children("tbody");
			this._registerMouseEvents($tbody);
			if (this.grid.hasFixedColumns()) {
				$tbodyFixed = this.grid.fixedTable().find("tbody");
				this._registerMouseEvents($tbodyFixed);
			}
			/* S.S. September 16, 2011, We should save our thead so we can use it to get column indexes. */
			if (this.grid.options.fixedHeaders === true) {
				this._$thead = this.grid.headersTable().find("thead");
			} else {
				this._$thead = this.grid.container().find("#" + this.grid.id() + " thead");
			}
		},
		_rowsRendered: function (event, ui) {
			var tbody;
			if (ui !== undefined) {
				if ($(ui.owner.element).attr("id") !== this.grid.id()) {
					return;
				}
			}
			tbody = this.grid.element.children("tbody");
			this._registerMouseEvents(tbody);
			if (this.grid.hasFixedColumns()) {
				tbody = this.grid.fixedTable().find("tbody");
				this._registerMouseEvents(tbody);
			}
		},
		_registerMouseEvents: function (tbody) {
			// S.S. August 9, 2011, Bug #83695, #83697, Binding is done on tbody instead of the whole table.
			// This prevents tooltips to be displayed for filter rows and fixes some issues with column headers.
			var binding;
			if (window.navigator.msPointerEnabled || window.navigator.pointerEnabled) {
				binding = {
					"MSPointerLeave.tooltip": this._gridMouseLeaveHandler,
					"pointerleave.tooltip": this._gridMouseLeaveHandler,
					"MSPointerMove.tooltip": this._gridMouseMoveHandler,
					"pointermove.tooltip": this._gridMouseMoveHandler
				};
				tbody.unbind(binding).bind(binding);
			} else {
				binding = {
					"mouseleave.tooltip": this._gridMouseLeaveHandler,
					"mousemove.tooltip": this._gridMouseMoveHandler
				};
				tbody.unbind(binding).bind(binding);
			}
		},
		_gridMouseLeave: function () {
			clearTimeout(this.timeoutShowing);
			clearTimeout(this.timeoutHiding);
			/* S.S. August 9, 2011 Bug #83691 Hiding should not be called if the tooltip is not visible because
			it throws an unnecessary tooltipHiding event. */
			if (this.tooltip.css("display") !== "none") {
				this._hideTooltip(true);
			}
			this._currentlyHovered = null;
		},
		_gridMouseMove: function (event) {
			var element = $(event.target).closest("td,th"), elementData,
				hidePrevious = true, shouldShow, hasFixedCols = this.grid.hasFixedColumns(),
				isFixedElement = this.grid._isFixedElement(element);
			/* if the parent table isn't the one the widget is initialized for we should return */
			/* M.H. 7 Mar 2013 Fix for bug #135335: Tooltips throws error for child layouts */
			/* M.H. 5 Jul 2013 Fix for bug #145825: ToolTip does not display when a column(s) is fixed.*/
			if ((element.closest("table").attr("id") !== this.grid.id() && !isFixedElement) ||
				(hasFixedCols && isFixedElement &&
				element.closest("table").attr("id") !== this.grid.id() + "_fixed")) {
				return;
			}
			this.mouseX = event.pageX !== undefined ? event.pageX : event.originalEvent.pageX;
			this.mouseY = event.pageY !== undefined ? event.pageY : event.originalEvent.pageY;
			/* if we hover on the same element or the element is unknown we should return */
			if (element[ 0 ] === this._currentlyHovered || element.length === 0) {
				return;
			}
			elementData = this._getElementValue(element);
			if (elementData === undefined) {
				hidePrevious = true;
			}
			/* if we return on an element a tooltip is currently displayed for we should keep this tooltip */
			if (element[ 0 ] === this._currentlyDisplayed && this.visible === true) {
				hidePrevious = false;
				clearTimeout(this.timeoutHiding);
				return;
			}
			this._currentlyHovered = element[ 0 ];
			this._previouslyHoveredData = this._currentlyHoveredData;
			this._currentlyHoveredData = elementData;
			/* At this point the widget should clear a previously executed tooltip show timeout. */
			clearTimeout(this.timeoutShowing);
			/* S.S. September 28, 2011 Bug #88214 If the mouse hovers the delete button (Updating widget)
			the old tooltip should hide and no new tooltip should be shown. */
			shouldShow = this._shouldShowForTarget(element) && elementData !== undefined &&
				elementData !== "" && $(event.target).attr("unselectable") === undefined;
			/* Hides the tooltip displayed for the previously hovered item. */
			if (this.visible === true && hidePrevious === true) {
				this._hideTooltip(!shouldShow);
			}
			/* Checks if a tooltip should be displayed for the currently hovered element based on widget options. */
			if (shouldShow === true) {
				this._currentlyDisplayed = this._currentlyHovered;
				this._showTooltip();
			}
		},
		_tooltipMouseEnter: function () {
			if (this.timeoutHiding !== null && this.timeoutHiding !== undefined) {
				clearTimeout(this.timeoutHiding);
			}
		},
		_tooltipMouseLeave: function () {
			this._hideTooltip();
		},
		_getColumnFixingInstance: function () {
			if (!this._columnFixing && this.grid.element.data("igGridColumnFixing")) {
				this._columnFixing = this.grid.element.data("igGridColumnFixing");
			}
			return this._columnFixing;
		},
		_getDataView: function () {
			return this.grid.dataSource.dataView();
		},
		_getRowIndex: function (element, row) {
			return element.closest("tbody")
				.children("tr:not([data-container='true'],[data-grouprow='true'])")
				.index(row);
		},
		_getElementValue: function (element) {
			var col, val, tlog, i, tid, row = element.closest("tr"), key,
				hasFixedCols = this.grid.hasFixedColumns(), hskip = 0, cf, rec,
				sri = this.grid._startRowIndex || 0;
			if (this.grid.options.virtualization || this.grid.options.columnVirtualization) {
				hskip = this.grid._startColIndex || 0;
			}
			/* column fixing integration */
			if (hasFixedCols) {
				cf = this._getColumnFixingInstance();
				/* if element has attribute [data-skip] OR is th(row selector column) then it should not be shown tooltip for this element(it si non data element) */
				if (element.is("th") || element.attr("data-skip")) {
					col = -1;
				} else {
					col = element.index();
					key = cf._getKeyByVisibleIndex(col, this.grid._isFixedElement(element));
				}
			} else {
				if (this._$thead.length > 0) {
					col = element.index() - this._$thead.children(":first")
						.children("th[data-skip='true']").length + hskip;
				} else {
					col = element.index() - element.parent()
						.find("td[data-parent='true'],td[data-skip='true'],th").length + hskip;
				}
			}
			if (col < 0) {
				return val;
			}
			if (!key) {
				key = this.grid._visibleColumns()[ col ].key;
			}
			this._pRowIdx = this._rowIdx;
			this._rowIdx = this._getRowIndex(element, row);
			/* if there is row virtualization we need to take care about the starting index */
			if (this.grid.options.virtualization || this.grid.options.rowVirtualization) {
				this._rowIdx += sri;
			}
			/* attempting to get data from the transaction log */
			tlog = this.grid.dataSource.pendingTransactions();
			tid = parseInt(element.parent().attr("data-id"), 10);
			/* loop should start from the end to get the latest change first */
			for (i = tlog.length - 1; i >= 0; i--) {
				if (tlog[ i ].type === "deleterow") {
					continue;
				}
				if (tlog[ i ].type === "cell") {
					// match cell
					if (tlog[ i ].rowId === tid && tlog[ i ].col === key) {
						rec = $.extend(true, {}, this.grid.dataSource.findRecordByKey(tlog[ i ].rowId));
						rec[ key ] = tlog[ i ].value;
						val = this.grid.dataSource.getCellValue(key, rec);
						break;
					}
				} else {
					// match row
					if (tlog[ i ].rowId === tid) {
						val = this.grid.dataSource.getCellValue(key, tlog[ i ].row);
						break;
					}
				}
			}
			/* if failed the data should be taken from the data source */
			/* S.S. October 21, 2011 A cell content can be deleted therefore the transaction log will have null or "" for
			its value. We should fall back to dataView only if val is still undefined. */
			if (val === undefined && this._getDataView()[ this._rowIdx ] !== undefined) {
				//val = this._getDataView()[this._rowIdx][key];
				val = this.grid.dataSource.getCellValue(key, this._getDataView()[ this._rowIdx ]);
			}
			/* L.A. 11 September 2012 Fixing bug #120485 The control still converts the hours to the local time zone
			even when EnableUTCDates property is set */
			if ($.type(val) === "date") {
				if (this.grid.options.enableUTCDates === true) {
					val = $.ig.formatter(val, "date", "dateTime", true, this.grid.options.enableUTCDates);
				}
			}
			return val;
		},
		_shouldShowForTarget: function (element) {
			var shouldShow = true, dataIdx, hasFixedColumns = this.grid.hasFixedColumns(), col;
			switch (this.options.visibility) {
				case "always":
					break;
				case "never":
					shouldShow = false;
					break;
				case "overflow":
					shouldShow = this._displayOverflow(element);
					break;
			}
			if (shouldShow === false) {
				return false;
			}
			dataIdx = element.parent().children("td:not([data-parent],[data-skip='true'])").index(element);
			this._pColumnIdx = this._columnIdx;
			/* M.H. 15 Feb 2013 Fix for bug #132983 */
			if (this.grid._isMultiColumnGrid) {
				this._columnIdx = dataIdx;
			} else {
				if (hasFixedColumns && element.closest("[data-fixed-container]").length === 1) {
					this._columnIdx = this.grid.fixedHeadersTable()
						.find("thead th[id]").not("[data-skip='true']").eq(dataIdx).data("columnIndex");
				} else {
					this._columnIdx = this._$thead.find("th[id]")
						.not("[data-skip='true']").eq(dataIdx).data("columnIndex");
				}
			}
			if (this._columnIdx === undefined || this._columnIdx === null) {
				this._columnIdx = dataIdx;
			}
			if (this._columnIdx < 0 || this._columnIdx >= this.grid.options.columns.length) {
				return false;
			}
			this._pColumnKey = this._columnKey;
			/* M.H. 20 June 2014 Fix for bug #174010: When the grid has MultiColumn headers and a column is fixed columnKey property of tooltipShowing event is wrong. */
			if (hasFixedColumns) {
				col = this.grid.getColumnByTD(element);
				if (col) {
					this._columnKey = col.column.key;
				}
			} else {
				/* M.H. 15 Feb 2013 Fix for bug #132983 */
				if (this.grid._isMultiColumnGrid) {
					this._columnKey = this.grid._visibleColumns()[ dataIdx ].key;
				} else {
					this._columnKey = this.grid.options.columns[ this._columnIdx ].key;
				}
			}
			return shouldShow && this._shouldShowForColumn();
		},
		_shouldShowForColumn: function () {
			var i, colSettings = this.options.columnSettings;
			for (i = 0; i < colSettings.length; i++) {
				if (colSettings[ i ].columnKey === this._columnKey ||
					colSettings[ i ].columnIndex === this._columnIdx) {
					return colSettings[ i ].allowTooltips !== undefined ?
						colSettings[ i ].allowTooltips : true;
				}
			}
			return true;
		},
		_hideTooltip: function (current) {
			var self = this, args, fn;
			args = {
				owner: this,
				tooltip: current === true ? String(this._currentlyHoveredData) :
					String(this._previouslyHoveredData),
				value: current === true ? this._currentlyHoveredData : this._previouslyHoveredData,
				element: this._currentlyDisplayed,
				columnKey: current === true ? this._columnKey : this._pColumnKey,
				index: current === true ? this._rowIdx : this._pRowIdx,
				columnIndex: current === true ? this._columnIdx : this._pColumnIdx
			};
			fn = function () {
				var noCancel = self._trigger(self.events.tooltipHiding, self, args);
				if (noCancel === true) {
					self._hideTooltipNoDelay();
					self._trigger(self.events.tooltipHidden, self, args);
				}
			};
			if ($.ig.util.isTouch) {
				fn();
			} else {
				clearTimeout(this.timeoutHiding);
				this.timeoutHiding = setTimeout(fn, this.options.hideDelay);
			}
		},
		_showTooltip: function () {
			var self = this, args, fn;
			args = {
				owner: this,
				tooltip: String(this._currentlyHoveredData),
				value: this._currentlyHoveredData,
				element: this._currentlyDisplayed,
				columnKey: this._columnKey,
				index: this._rowIdx,
				columnIndex: this._columnIdx
			};
			fn = function () {
				var noCancel = self._trigger(self.events.tooltipShowing, self, args);
				if (noCancel === true) {
					self._showTooltipNoDelay(args.tooltip);
					self._trigger(self.events.tooltipShown, self, args);
				}
			};
			if ($.ig.util.isTouch) {
				fn();
			} else {
				this.timeoutShowing = setTimeout(fn, this.options.showDelay);
			}
		},
		_hideTooltipNoDelay: function () {
			if (this.options.style === "tooltip") {
				if (this._canFadeOut === true) {
					this._canFadeOut = false;
					this.tooltip.fadeOut(this.options.fadeTimespan, $.proxy(this._fadeOutEnd, this));
				}
			} else if (this.options.style === "popover") {
				this.tooltip.igPopover("hide");
			}
			this.visible = false;
		},
		_showTooltipNoDelay: function (value) {
			var tooltip;
			if (this._currentlyDisplayed === null) {
				return;
			}
			clearTimeout(this.timeoutHiding);
			tooltip = $("#" + this.id());
			if (this.options.style === "tooltip") {
				this._updateTooltip(value);
				if (this._canFadeIn === true) {
					this._canFadeIn = false;
					tooltip.fadeIn(this.options.fadeTimespan, $.proxy(this._fadeInEnd, this));
				}
			} else if (this.options.style === "popover") {
				tooltip.igPopover("show", $(this._currentlyDisplayed), value);
			}
			this.visible = true;
		},
		_fadeOutEnd: function () {
			this._canFadeOut = true;
		},
		_fadeInEnd: function () {
			this._canFadeIn = true;
		},
		_updateTooltip: function (value) {
			var tooltipContent = this.tooltip.children("[id$='_content']");
			tooltipContent.text(value);
			tooltipContent.parent().css("max-width",
				this._getMaxWidth(this._columnKey, this._columnIdx, this._currentlyDisplayed));
			this._positionTooltip(tooltipContent.parent());
		},
		_getMaxWidth: function (colKey, colIdx, td) {
			var colSettings = this.options.columnSettings, i;
			for (i = 0; i < colSettings.length; i++) {
				if (colSettings[ i ].columnKey === colKey || colSettings[ i ].columnIndex === colIdx) {
					if (colSettings[ i ].maxWidth !== null && colSettings[ i ].maxWidth !== undefined) {
						return colSettings[ i ].maxWidth;
					}
					break;
				}
			}
			return $(td).width();
		},
		_positionTooltip: function (tooltip) {
			var win = $(window),
				left, top, toffset,
				tfullw = tooltip.outerWidth(),
				tfullh = tooltip.outerHeight();
			toffset = $.ig.util.getRelativeOffset(tooltip);
			left = this.mouseX - toffset.left + this.options.cursorLeftOffset;
			top = this.mouseY - toffset.top + this.options.cursorTopOffset;
			/* repositions the tooltip if it would otherwise overflow the browser window to the right */
			if (left + tfullw + toffset.left > win.width() + win.scrollLeft()) {
				left = win.width() - toffset.left - tfullw + win.scrollLeft();
			}
			/* repositions the tooltip if it would otherwise overflow the browser window to the bottom */
			if (top + tfullh + toffset.top > win.height() + win.scrollTop()) {
				top = win.height() - toffset.top - tfullh + win.scrollTop();
			}
			tooltip.css({
				"top": top,
				"left": left
			});
		},
		_fixedColumnsChanged: function (fObj) {
			/* the method is called when some column is fixed/unfixed */
			var $tbodyFixed;
			if (fObj.isToFix && fObj.isInit) {
				$tbodyFixed = this.grid.fixedTable().find("tbody");
				this._registerMouseEvents($tbodyFixed);
			}
		},
		_createHandlers: function () {
			this._rowsRenderedHandler = $.proxy(this._rowsRendered, this);
			this._gridMouseLeaveHandler = $.proxy(this._gridMouseLeave, this);
			this._gridMouseMoveHandler = $.proxy(this._gridMouseMove, this);
			this._gridHeaderRenderingHandler = $.proxy(this._gridHeaderRendering, this);
		},
		_registerGridEvents: function () {
			this.grid.element.bind("iggridrowsrendered.tooltip", this._rowsRenderedHandler);
			this.grid.element.bind("iggridheaderrendering.tooltip", this._gridHeaderRenderingHandler);
		},
		_unregisterGridEvents: function () {
			this.grid.element.unbind("iggridrendered.tooltip", this._dataRenderedHandler);
			this.grid.element.unbind("iggridheaderrendering.tooltip", this._gridHeaderRenderingHandler);
		}
	});
	$.extend($.ui.igGridTooltips, { version: "16.1.20161.2145" });
}(jQuery));
