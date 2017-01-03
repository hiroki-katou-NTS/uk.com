
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
 *	infragistics.ui.treegrid.js
 *	infragistics.util.js
 *	infragistics.ui.grid.framework.js
 *	infragistics.ui.grid.selection.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igTreeGridSelection widget. The widget is pluggable to the element where the treegrid is instantiated and the actual igTreeGrid object doesn't know about this
		the filtering widget just attaches its functionality to the treegrid
		igTreeGridSelection is extending igGrid Selection
	*/
	$.widget("ui.igTreeGridSelection", $.ui.igGridSelection, {
		css: {},
		options: {},
		_create: function () {
			this.element.data(
				$.ui.igGridSelection.prototype.widgetName,
				this.element.data($.ui.igTreeGridSelection.prototype.widgetName)
			);
			$.ui.igGridSelection.prototype._create.apply(this, arguments);
		},
		_getDataView: function () {
			return this.grid.dataSource.flatDataView();
		},
		destroy: function () {
			$.ui.igGridSelection.prototype.destroy.apply(this, arguments);
			this.element.removeData($.ui.igGridSelection.prototype.widgetName);
		},
		_keyDown: function (event) {
			var target;
			if (this.options.mode === "row") {
				if (this._keyDownRowMode(event)) {
					return;
				}
			} else if (this.options.mode === "cell") {
				if (this._keyDownCellMode(event)) {
					return;
				}
			}
			target = event && event.target ? $(event.target) : null;
			/* expanding end collapsing rows should not apply selection */
			if (!target.is("[data-expand-button]") /*&& !target.is("td.ui-igtreegrid-non-data-column")*/) {
				$.ui.igGridSelection.prototype._keyDown.apply(this, arguments);
			}
		},
		_keyDownRowMode: function (event) {
			var tr = $(event.target), keyCode = event.keyCode, state;
			if (keyCode === $.ui.keyCode.LEFT ||
							keyCode === $.ui.keyCode.RIGHT) {
				/* D.K. Fix for bug #205136 - Left and right arrow keys are not collapsing and expanding the treegrid when there is column fixing */
				/* D.K. 3 Sep 2015 Fix for bug #205329 - When focus is on a cell without expanding/collapsing option keyboard navigation can be used */
				if (!tr.is("tr")) {
					return;
				}
				state = tr.attr("aria-expanded");
				if (state === "undefined" || state === undefined) {
					return;
				}
				/* collapse on RIGHT arrow, collapse on left arrow */
				this.grid._expandCollapseRow(tr, keyCode === $.ui.keyCode.RIGHT, true);
				return true;
			} else if (keyCode === $.ui.keyCode.HOME) {
				/* go to first row */
				if (this._navigateRow(event, "top")) {
					event.preventDefault();
					return true;
				}
			} else if (keyCode === $.ui.keyCode.END) {
				/* go to first row */
				if (this._navigateRow(event, "bottom")) {
					event.preventDefault();
					return true;
				}
			}
			if (tr.is("td.ui-igtreegrid-non-data-column") &&
				(keyCode === $.ui.keyCode.SPACE ||
				keyCode === $.ui.keyCode.ENTER)) {
				return true;
			}
		},
		_keyDownCellMode: function (event) {
			var keyCode = event.keyCode, updating;
			/* toggle cell (if expander) */
			if (keyCode === $.ui.keyCode.ENTER) {
				updating = this.grid.element.data("igGridUpdating");
				/* if updating is enabled - row should not be toggled when ENTER is pressed() */
				if (!updating && this._expandCollapseRowByCell()) {
					event.preventDefault();
					event.stopPropagation();
					return true;
				}
			} else if (event.altKey &&
					(keyCode === $.ui.keyCode.UP || keyCode === $.ui.keyCode.DOWN)) {
				if (this._expandCollapseRowByCell(null, keyCode === $.ui.keyCode.DOWN)) {
					event.preventDefault();
					return true;
				}
			} else if (keyCode === $.ui.keyCode.HOME) {
				if (event.ctrlKey) {
					if (this._navigateCell(event, "topLeft")) {
						event.preventDefault();
						return true;
					}
				}
				/* Move to leftmost cell in row */
				if (this._navigateCell(event, "left")) {
					event.preventDefault();
					return true;
				}
			} else if (keyCode === $.ui.keyCode.END) {
				if (event.ctrlKey) {
					if (this._navigateCell(event, "bottomRight")) {
						event.preventDefault();
						return true;
					}
				}
				/* Move to leftmost cell in row */
				if (this._navigateCell(event, "right")) {
					event.preventDefault();
					return true;
				}
			} else if (keyCode === $.ui.keyCode.SPACE && this.grid.options.renderExpansionIndicatorColumn) {
				/* Toggle the row when there is expansion indicator column */
				if (this._expandCollapseRowByCell()) {
					event.preventDefault();
					event.stopPropagation();
					return true;
				}
			}
		},
		/* Navigation helper functions */
		/* Cell navigation helper functions */
		_navigateCell: function (event, dir) {
			var $cell = this._getActiveCell(), funcNav, $nextActiveCell, self = this, $tr;
			if (!$cell || !$cell.length) {
				return;
			}
			$tr = $cell.closest("tr");
			if (dir === "left") {
				$nextActiveCell = $tr.children("td:not([data-skip]):visible").first();
			} else if (dir === "right") {
				$nextActiveCell = $tr.children("td:not([data-skip]):visible").last();
			}
			if ($nextActiveCell) {
				this._storedActiveIndex = null;
				this._navigateOwn($nextActiveCell, this._selection.activeElement,
											event.keyCode, false, event.shiftKey);
				return true;
			}
			funcNav = function ($tbody, e) { /*, direction */
				var $tr, $nextEl;
				/* because Control key is pressed it should be removed old(if any) selection */
				if (e.ctrlKey) {
					this.clearSelection();
				}
				if (dir === "topLeft") {
					$tr = $tbody
						.children("tr:visible:not([data-skip])").first();
					$nextEl = $tr.children("td:visible:not([data-skip])").first();
				} else {
					/* bottom right */
					$tr = $tbody
						.children("tr:visible:not([data-skip])").last();
					$nextEl = $tr.children("td:visible:not([data-skip])").last();
				}
				self._storedActiveIndex = null;
				self._navigateOwn($nextEl, this._selection.activeElement,
										e.keyCode, false, e.shiftKey);
			};
			return this._navigateTo(event, dir, funcNav);
		},
		/* //Cell navigation helper functions */
		/* Row navigation helper functions */
		_navigateRow: function (event, dir) {
			var funcNav = function ($tbody, ev, direction) {
				var $nextEl, prevActiveElement = this._selection.activeElement;
				if (direction === "top") {
					$nextEl = $tbody
						.children("tr:visible:not([data-skip])").first();
				} else {
					/* bottom right */
					$nextEl = $tbody
						.children("tr:visible:not([data-skip])").last();
				}
				/* we should allow multiple selection of rows */
				if (ev.shiftKey && this.options.multipleSelection) {
					this._shiftSelectChange($nextEl);
				} else {
					this._navigateOwn($nextEl, prevActiveElement,
												ev.keyCode, false, ev.shiftKey);
				}
			};
			return this._navigateTo(event, dir, funcNav);
		},
		/* //Row navigation helper functions */
		_navigateTo: function (event, dir, funcNavigate) {
			/* helper function - if grid is not virtual execute funcNavigate - otherwise detect whether to scroll to TOP or BOTTOM and after scroll is done execute funcNavigate */
			var $scrollContainer, go = this.grid.options, self = this, scrTop;
			if (go.virtualization === true && go.virtualizationMode === "continuous") {
				$scrollContainer = this.grid._scrollContainer();
				if (dir === "topLeft" || dir === "top") {
					scrTop = 0;
				} else {
					/* should go to bottom */
					scrTop = $scrollContainer.children(":first-child").height();
				}
				$scrollContainer.scrollTop(scrTop);
				if (self._loadingIndicator === undefined) {
					self._initLoadingIndicator();
				}
				self._loadingIndicator.show();
				setTimeout(function () {
					if (self._loadingIndicator) {
						self._loadingIndicator.hide();
					}
					funcNavigate.call(self, self.grid.element.children("tbody"), event, dir);
				}, 300);
			} else {
				funcNavigate.call(self, this.grid.element.children("tbody"), event, dir);
			}
			return true;
		},
		/* //Navigation helper functions */
		_getActiveCell: function () {
			var activeEl = this._selection.activeElement;
			if (!activeEl) {
				return;
			}
			return this._getCellByIdentifier(activeEl);
		},
		_expandCollapseRowByCell: function ($cell, expand) {
			/* if cell is not set then try to get cell from activeElement
			*  if expand not set then try to toggle
			*  return TRUE if cell is data expander cell */
			var $tr;
			if (!$cell) {
				$cell = this._getActiveCell();
				if (!$cell) {
					return;
				}
			}
			if ($cell.length && $cell.attr("data-expand-cell") &&
					$cell.find("[data-expand-button]").length) {
				$tr = $cell.closest("tr");
				if (expand === undefined) {
					this.grid._toggleRow($tr);
				} else {
					this.grid._expandCollapseRow($tr, expand, true);
				}
				return true;
			}
		},
		_initLoadingIndicator: function () {
			/* attach loading indicator widget */
			this._loadingIndicator = this.grid.container().length > 0 ?
									 this.grid.container().igLoading().data("igLoading") :
									 this.grid.element.igLoading().data("igLoading").indicator();
		},
		_mouseUp: function (event) {
			var target = event && event.target ? $(event.target) : null;
			/* expanding end collapsing rows should not apply selection */
			if (!target.is("[data-expand-button]") &&
				(!target.is("td[data-expand-cell]") || !this.grid.options.renderExpansionIndicatorColumn)) {
				$.ui.igGridSelection.prototype._mouseUp.apply(this, arguments);
			}
		},
		_virtualRecordsRendered: function (info) {
			var $ae = info.activeElement;
			if ($ae.is("tr") || $ae.is("td")) {
				this._selection.activate(this._identifierForTarget($ae), $ae, true);
			}
		},
		selectRowById: function (id) {
			/* selects a row by row id
				paramType="number|string" Row Id
			*/
			if (this.options.mode === "cell") {
				return;
			}
			if (!this._selection.isSelected(id, this.grid)) {
				if (this._selection.settings.owner !== this.grid) {
					this._selection.changeOwner(this.grid);
				}
				this._selection.select(id, true, { element: this._getRowsByIdentifier(id), id: id }, true);
			}
		},
		_select: function (info) {
			var element = info.element || this._selection.elementFromIdentifier(info.id);
			/*apply selection styles to the expansion indicator column when the mode of the selection is row */
			if (element.is("tr") && this.grid.options.renderExpansionIndicatorColumn) {
				element.children("td.ui-igtreegrid-non-data-column").addClass(this.css.selectedCell);
			}
			$.ui.igGridSelection.prototype._select.apply(this, arguments);
		},
		_preventDefault: function (event) {
			var target = $(event.target);
			if (target.is("td") &&
				(!target.is("td[data-expand-cell]") || !this.grid.options.renderExpansionIndicatorColumn)) {
				event.preventDefault();
			} else if (target.attr("data-expandcell-indicator")) {
				/* M.H. 30 May 2016 Fix for bug 219952: Expanding action is not consistent when there is selection and it applies activation to the expansion indicator cell when there is virtualization */
				event.preventDefault();
				event.stopPropagation();
			}
		}
	});
	$.extend($.ui.igTreeGridSelection, { version: "16.1.20161.2145" });
}(jQuery));



