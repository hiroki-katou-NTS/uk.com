/*!@license
 * Infragistics.Web.ClientUI Grid Selection (and Keyboard navigation) 16.1.20161.2145
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
/*global jQuery, Class*/
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {
	/* Grid selection jQuery UI widget */
	"use strict";
	$.widget("ui.igGridSelection", {
		css: {
			/* classes applied to a cell once it's selected */
			selectedCell: "ui-iggrid-selectedcell ui-state-active",
			/* classes applied to a row once it's selected */
			selectedRow: "ui-iggrid-selectedrow ui-state-active",
			/* classes applied to the currently active cell, if any (mode = "cell") */
			activeCell: "ui-iggrid-activecell ui-state-focus",
			/* classes applied to the currently active row, if any (mode = "row") */
			activeRow: "ui-iggrid-activerow ui-state-focus"
		},
		options: {
			/* type="bool" Enables / Disables multiple selection of cells and rows - depending on the mode */
			multipleSelection: false,
			/* type="bool" Enables / disables selection via dragging with the mouse - only applicable for cell selection */
			mouseDragSelect: true,
			/* type="row|cell" Defines type of the selection.
			row type="string" Defines row selection mode.
			cell type="string" Defines cell selection mode.
			*/
			mode: "row",
			/* type="bool" Enables / disables activation of rows and cells. Activation implies ability to perform navigating through cells and rows via the keyboard, and selecting rows and cells using CTRL / SHIFT - in the way cells/rows are selected in Ms Excel */
			activation: true,
			/* type="bool" if wrapAround is enabled and selection is on the first or last row or cell, then when the end user tries to go beyond that, the first/last row or cell will be selected */
			wrapAround: true,
			/* if true will basically skip going into child grids with down / up / right / left arrow keys, when in the context of hierarchical grid */
			skipChildren: true,
			/* type="bool" if true multiple selection of cells is done as if CTRL is being held. the option is disregarded if mode is set to row. this option is useful for enabling multiple discountinued selection on touch environments. */
			multipleCellSelectOnClick: false,
			/* type="bool" Enables / disables selection via continuous touch event - only applicable for cell selection and touch-supported environments */
			touchDragSelect: true,
			/* type="bool" Enables / disables selection persistance between states. */
			persist: true,
			/* type="bool" Enables / disables the ability to ctrl drag multiple selection windows when selection mode is 'cell' */
			allowMultipleRangeSelection: true
		},
		events: {
			/* cancel="true" Event fired before row(s) are about to be selected (cancellable).
			Return false in order to cancel selection changing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSelection.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.row to get reference to row object.
			Use ui.row.element to get reference to row DOM element.
			Use ui.row.index to get row index.
			Use ui.row.id to get the row id if primary key is defined or persistence is enabled.
			Use ui.selectedRows to get reference to rows object array.
			Use ui.startIndex to get the start index for a range row selection.
			Use ui.endIndex to get the end index for a range row selection.
			*/
			rowSelectionChanging: "rowSelectionChanging",
			/* Event fired after row(s) are selected.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSelection.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.row to get reference to row object.
			Use ui.row.element to get reference to row DOM element.
			Use ui.row.index to get row index.
			Use ui.row.id to get the row id if primary key is defined or persistence is enabled.
			Use ui.selectedRows to get reference to rows object array.
			*/
			rowSelectionChanged: "rowSelectionChanged",
			/* cancel="true" Event fired before cell(s) are about to be selected (cancellable).
			Return false in order to cancel cell selection changing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSelection.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.cell to get reference to cell object.
			Use ui.cell.element to get reference to cell DOM element.
			Use ui.cell.columnKey to get reference to column key.
			Use ui.cell.rowId to get the row id if primary key is defined or persistence is enabled.
			Use ui.cell.index to get column index.
			Use ui.cell.row to get reference to row DOM element.
			Use ui.cell.rowIndex to get row index.
			Use ui.selectedCells to get reference to selected cells object array.
			Use ui.firstColumnIndex to get the column index for the first cell in a range selection.
			Use ui.firstRowIndex to get the row index for the first cell in a range selection.
			Use ui.lastColumnIndex to get the column index for the last cell in a range selection.
			Use ui.lastRowIndex to get the row index for the last cell in a range selection.
			*/
			cellSelectionChanging: "cellSelectionChanging",
			/* cancel="true" Event fired after cell(s) are selected.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSelection.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.cell to get reference to cell object.
			Use ui.cell.element to get reference to cell DOM element.
			Use ui.cell.columnKey to get reference to column key.
			Use ui.cell.rowId to get the row id if primary key is defined or persistence is enabled.
			Use ui.cell.index to get column index.
			Use ui.cell.row to get reference to row DOM element.
			Use ui.cell.rowIndex to get row index.
			Use ui.selectedCells to get reference to selected cells object array.
			*/
			cellSelectionChanged: "cellSelectionChanged",
			/* cancel="true" Event fired before a cell becomes active (focus style applied) (cancellable).
			Return false in order to cancel active cell changing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSelection.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.cell to get reference to cell object.
			Use ui.cell.element to get reference to cell DOM element.
			Use ui.cell.columnKey to get column key.
			Use ui.cell.rowId to get the row id if primary key is defined or persistence is enabled.
			Use ui.cell.index to get column index.
			Use ui.cell.row to get reference to row DOM element.
			Use ui.cell.rowIndex to get row index.
			*/
			activeCellChanging: "activeCellChanging",
			/* Event fired after a cell becomes active (focus style applied).
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSelection.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.cell to get reference to cell object.
			Use ui.cell.element to get reference to cell DOM element.
			Use ui.cell.columnKey to get column key.
			Use ui.cell.rowId to get the row id if primary key is defined or persistence is enabled.
			Use ui.cell.index to get column index.
			Use ui.cell.row to get reference to row DOM element.
			Use ui.cell.rowIndex to get row index.
			*/
			activeCellChanged: "activeCellChanged",
			/* cancel="true" Event fired before a row becomes active (focus style applied) (cancellable).
			Return false in order to cancel active row changing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSelection.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.row to get reference to row object.
			Use ui.row.element to get reference to active row DOM element.
			Use ui.row.index to get active row index.
			Use ui.row.id to get the active row id if primary key is defined or persistence is enabled.
			*/
			activeRowChanging: "activeRowChanging",
			/* Event fired after a row becomes active (focus style applied).
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSelection.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.row to get reference to row object.
			Use ui.row.element to get reference to active row DOM element.
			Use ui.row.index to get active row index.
			Use ui.row.id to get the active row id if primary key is defined or persistence is enabled.
			*/
			activeRowChanged: "activeRowChanged"
		},
		_create: function () {
			this._isMouseDown = false;
			this._pkProp = null;
			/* special cells props */
			this._rsCellKey = "##ROWSEL##";
			this._gbExpandCellKey = "##GBXCEL##";
			this._gbSummaryCellKey = "##GBSCEL##";
			this._gbEmptyCellKey = "##GBECEL##";
			this._hgCellKey = "##HIEGRD##";
			this._tgExpandKey = "##TGXCEL##";
			this.element.attr("aria-multiselectable", this.options.multipleSelection);
		},
		destroy: function () {
			// Destroys the selection widget.
			this.grid.element.unbind({
				"iggriddatarendering": this._dataRenderingHandler,
				"iggridvirtualrendering": this._virtualRecordsRenderingHandler,
				"igtreegridvirtualrendering": this._virtualRecordsRenderingHandler,
				"iggridvirtualrecordsrender": this._virtualRecordsRendererHandler,
				"igtreegridvirtualrecordsrender": this._virtualRecordsRendererHandler,
				"iggridcolumnscollectionmodified": this._columnsCollectionModifiedHandler
			});
			this.clearSelection();
			this._unregisterEvents();
			this._selection.removeSubscriber(this._subId, this.grid.id());
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		_setOption: function (key) {
			// handle new settings and update options hash
			$.Widget.prototype._setOption.apply(this, arguments);
			/* throw an error for the options that cannot be changed after the widget has been created */
			if (key === "mode") {
				throw new Error($.ig.Grid.locale.optionChangeNotSupported.replace("{optionName}", key));
			}
		},
		/* grid event handlers */
		_dataRendering: function (event, ui) {
			var tbody;
			if (ui.owner.id() !== this.grid.id()) {
				return;
			}
			tbody = this.grid.element.find("tbody").add(this.grid.fixedBodyContainer().find("tbody"));
			if (this._tbodyBinding) {
				tbody.unbind(this._tbodyBinding);
			}
			this._pkProp = this.grid.options.primaryKey;
			if (this.options.persist && !this.grid.options.primaryKey) {
				/* add checksum code for each record in the data source */
				this._createUidForData();
				this._pkProp = "ig_pk";
			}
		},
		_dataRendered: function () {
			this._unregisterEvents();
			this._registerEvents();
			if (this._hc === undefined) {
				this._hc = this.grid.container().closest(".ui-iggrid-root").length > 0;
				if (this._hc === false) {
					this._hc = this.grid.element.hasClass("ui-iggrid-root");
				}
			}
			this.grid._startRowIndex = this.grid._startRowIndex || 0;
			this._defaultAttributes();
			if (this.options.persist) {
				this._paintActive();
				this._renderAttributes();
				this._selection.invalid = true;
			} else {
				this.clearSelection();
			}
		},
		_fixedColumnsChanged: function (args) {
			/* M.H. 1 Mar 2013 Fix for bug #134495: If a row is selected fixing
			a column removes the selected and active style from the cell. */
			if (args.isToFix && args.isInit) {
					/* register events */
					this._unregisterEvents();
					this._registerEvents();
					this._selection.invalid = true;
				}
		},
		_virtualRecordsRendering: function () {
			//store the active(focused element) and reapply the focus after the new virtual chunk is rendered
			this._storedActiveElement = this.activeCell() || this.activeRow();
			if (this._storedActiveElement) {
				this._storedActiveElement.element.blur();
			}
		},
		_virtualRecordsRender: function (evt, ui) {
			var ae;
			if (ui.owner.id() !== this.grid.id()) {
				return;
			}
			/* D.K. 18 Aug 2015 Fix for bug #203636: Selected rows object contains incorrect information */
			this._selection.invalid = true;
			/* unbind the grid's own keydown handler when selection is enabled */
			$(document).unbind("keydown." + this.grid.id());
			if (this.grid.options.virtualization === true &&
				this.grid.options.virtualizationMode === "continuous") {
				/* D.K. 20 Aug 2015 Fix for bug #203626 - Focus is lost when trying
				to expand/collapse using keyboard navigation (with virtualization) */
				/*if ($.ig.util.isIE) {
					this.grid.container().focus();
				} */
				this._unregisterEvents();
				this._registerEvents();
			}
			this._paintActive();
			this._renderAttributes();
			if (this.options.mode === "cell") {
				ae = this.activeCell();
			} else {
				ae = this.activeRow();
			}
			if (ae && ae.element instanceof jQuery) {
				ae.element.focus();
			} else if (this._storedActiveElement) {
				if (this.options.mode === "cell") {
					this._getCellByIdentifier(this._storedActiveElement).focus();
				} else {
					this._getRowsByIdentifier(this._storedActiveElement.id).focus();
				}
			}
		},
		_columnsCollectionModified: function (evt, ui) {
			if (ui.owner.id() === this.grid.id()) {
				this._unregisterEvents();
				this._registerEvents();
				this._selection.invalid = true;
				this._paintActive();
			}
		},
		_hidingFinished: function () {
			if (this.options.persist) {
				this._paintActive();
				this._selection.invalid = true;
			}
		},
		_rowDeleted: function (rowId) {
			var cellsForRow, colKey, cellId;
			/* always invalidate indexes (a random row is removed) */
			this._selection.invalid = true;
			if (this.options.mode === "cell") {
				/* for cell we have some non-standard checks to perform
				therefore we'll work with the collection directly
				change to collection API if the checks are later required elsewhere */
				if (this._selection.settings.owner === this.grid) {
					if (this._selection.activeElement && this._selection.activeElement.id === rowId) {
						this._selection.deactivate();
					}
					cellsForRow = this._selection.selection[ rowId ];
					for (colKey in cellsForRow) {
						if (cellsForRow.hasOwnProperty(colKey)) {
							cellId = {
								id: rowId,
								columnKey: colKey
							};
							this._selection.deselect(cellId, null, false);
						}
					}
				}
			} else {
				/* remove activation of that row */
				if (this._selection.isActive(rowId, this.grid)) {
					this._selection.deactivate();
				}
				/* remove selection of that row */
				if (this._selection.isSelected(rowId, this.grid)) {
					this._selection.deselect(rowId, null, false);
				}
			}
		},
		/* mouse event handlers */
		_mouseDown: function (event) {
			var target, targetGrid, gOpts = this.grid.options,
				re = new RegExp("^" + this.grid.id() + "(_fixed)?$"),
				hasVirtualization =
					gOpts.virtualization || gOpts.rowVirtualization || gOpts.columnVirtualization,
				scrollCont = hasVirtualization ?
					this.grid._vdisplaycontainer() : this.grid.scrollContainer();
			/* check if Updating has suspended mouseDown handling
			this will be done on mouseup for since Updating always hanldes
			events last meaning mousedown that ends edit won't select */
			/*if (event && this._suspend) {
				return;
			} */
			/* check if we are clicking the left or the right mouse button.
			Cancel selection on the right mouse button */
			if (event && event.which !== 1) {
				return;
			}
			target = event && event.target ? $(event.target) : null;
			/* we'll compare the target with the mouseup one to detect possible issues */
			this._mouseDownTarget = target;
			this._mouseDownScroll = scrollCont.length ? scrollCont.scrollTop() : 0;
			this._prevDragTar = target[ 0 ];
			this._previousRangeCache = event.ctrlKey || event.metaKey ? [] : null;
			if (this.options.allowMultipleRangeSelection && this.options.mode === "cell") {
				this._selection._lockSelection();
			}
			targetGrid = target ? target.closest(".ui-iggrid-table") : null;
			/* check if the target is not the scrollbar as that breaks the drag select in Chrome */
			if (target && target.hasClass("ui-iggrid-scrolldiv")) {
				return;
			}
			/* ensure we are handling it in the correct grid */
			if (targetGrid && targetGrid.length > 0 && !targetGrid.attr("id").match(re)) {
				return;
			}
			this._isMouseDown = true;
			if (this.options.mouseDragSelect === false ||
				this.options.multipleSelection === false ||
				this.options.mode === "row") {
				return;
			}
			this._firstDragCell = target;
		},
		_mouseMove: function (event) {
			var target = event && event.target ? $(event.target) : null, fdc = this._firstDragCell, re;
			if (event && this._suspend) {
				return;
			}
			if (this.options.mode !== "cell" ||
				!this.options.mouseDragSelect ||
				!this._isMouseDown) {
				return;
			}
			target = target.closest("td,th");
			if (fdc && fdc.length > 0) {
				/* we have a firstDragCell */
				re = new RegExp("^" + this.grid.id() + "(_fixed)?$");
				/* uncomment first condition if dragging over the same cell causes issues */
				if (target[ 0 ] !== this._prevDragTar && // and the new cell is not the same cell
					/* and the new cell and the firstDragCell have the
					same parent table or tables of the same fixed grid */
					target.closest(".ui-iggrid-table").attr("id").match(re) &&
					fdc.closest(".ui-iggrid-table").attr("id").match(re) &&
					/* and the new cell is a data cell (a smarter check is performed later) */
					!(target.is("th") || target.attr("data-skip"))) {
					this._selection.rangeSelectStart = this._identifierForTarget(fdc);
					this._dragSelect = true;
					this._prevDragTar = target[ 0 ];
					this._shiftSelectChange(target, false);
					this._storedActiveIndex = target.closest("td").index();
				}
			}
		},
		_mouseUp: function (event) {
			// get the target, ensure we are toggling the drag flags, send to select handler
			var gOpts = this.grid.options,
				hasVirtualization =
					gOpts.virtualization || gOpts.rowVirtualization || gOpts.columnVirtualization,
				scrollCont = hasVirtualization ?
					this.grid._vdisplaycontainer() : this.grid.scrollContainer(),
				target = event && event.target ? $(event.target) : null,
				targetGrid = target ? target.closest(".ui-iggrid-table") : null,
				re = new RegExp("^" + this.grid.id() + "(_fixed)?$"),
				shouldCancel, self = this, ctrl;
			/* ensure we are handling it in the correct grid */
			if (targetGrid && targetGrid.length > 0 && !targetGrid.attr("id").match(re)) {
				return;
			}
			this._isMouseDown = false;
			/* when drag selecting the last selection will always be done on move so we can exit */
			if (!target || this._dragSelect || this._suspend || !this._mouseDownTarget ||
				this._mouseDownTarget[ 0 ] !== target[ 0 ] || event.which > 1 ||
				($.ig.util.isTouch &&
				(scrollCont.length ? scrollCont.scrollTop() : 0) !== this._mouseDownScroll)) {
				this._dragSelect = false;
				this._prevDragTar = null;
				this._firstDragCell = null;
				this._mouseDownTarget = null;
				return;
			}
			if (target.closest(".ui-iggrid-rowselector-class").length === 1 ||
				target.hasClass("ui-iggrid-childarea") ||
				target.parent().hasClass("ui-iggrid-childarea") ||
				target.hasClass("ui-widget-header")) {
				/* clicked on element that cannot be selected (headers, childgrid areas, etc.) */
				return;
			}
			if (this._hc) {
				target.parents("tr[data-container]").each(function () {
					if ($(this).closest(".ui-iggrid-table").attr("id") === self.grid.element.attr("id")) {
						shouldCancel = true;
						return false;
					}
				});
				if (shouldCancel) {
					return;
				}
			}
			if (event.shiftKey && this.options.multipleSelection) {
				/* shift selection will have different event args and event handling
				will be done once for the whole collection. Therefore we need a seperate
				function to handle it without convoluting the other scenarios */
				this._shiftSelectChange(target, true);
			} else {
				ctrl = (event.ctrlKey || event.metaKey) || (this.options.mode === "cell" &&
					this.options.multipleCellSelectOnClick);
				this._singleSelectChange(target, ctrl);
			}
		},
		/* touch events handlers */
		_touchStart: function (event) {
			event.target = $(document.elementFromPoint(
				event.originalEvent.touches[ 0 ].clientX, event.originalEvent.touches[ 0 ].clientY)
			);
			event.which = 1;
			if (event.target.hasClass("ui-iggrid-selectedcell")) {
				this._canDrag = true;
			} else {
				this._canDrag = false;
			}
			this._mouseDown(event);
		},
		_touchEnd: function (event) {
			event.target = $(document.elementFromPoint(
				event.originalEvent.changedTouches[ 0 ].clientX,
				event.originalEvent.changedTouches[ 0 ].clientY)
			);
			this._canDrag = false;
			this._mouseUp(event);
		},
		_touchMove: function (event) {
			// if touch drag select requirements are not met we'll let the browser execute default behavior
			if (!this._isMouseDown || !this._canDrag) {
				return true;
			}
			event.preventDefault();
			event.target = $(document.elementFromPoint(
				event.originalEvent.touches[ 0 ].clientX,
				event.originalEvent.touches[ 0 ].clientY)
			);
			this._mouseMove(event);
		},
		/* pointer event handlers - we'll try to use only these in supported Browsers (IE10+) */
		_pointerDown: function (event) {
			this.grid.element.css("-ms-touch-action", "none");
			this._mouseDown(event);
		},
		_pointerMove: function (event) {
			this._mouseMove(event);
		},
		_pointerUp: function (event) {
			this.grid.element.css("-ms-touch-action", "auto");
			this._mouseUp(event);
		},
		/* key event handlers */
		_keyDown: function (event) {
			var target, targetGrid, re = new RegExp("^" + this.grid.id() + "(_fixed)?$"), el;
			/* disabled if updating is currently in progress */
			if (event && this._suspend) {
				return;
			}
			/* keydown should not modify the owner and should not be handled by other grids */
			if (this.grid !== this._selection.settings.owner) {
				return;
			}
			/* disabled if mouse key is pressed */
			if (this._isMouseDown === true) {
				return;
			}
			/* keyboard navigation depends on activation */
			if (this.options.activation !== true) {
				return;
			}
			/* accepted keys ENTER, UP, LEFT, RIGHT, DOWN, SPACE */
			if (event.keyCode !== $.ui.keyCode.ENTER && event.keyCode !== $.ui.keyCode.SPACE &&
				event.keyCode !== $.ui.keyCode.UP && event.keyCode !== $.ui.keyCode.DOWN &&
				event.keyCode !== $.ui.keyCode.LEFT && event.keyCode !== $.ui.keyCode.RIGHT) {
				return;
			}
			/* decide if prevent default is required */
			target = event && event.target ? $(event.target) : null;
			targetGrid = target ? target.closest(".ui-iggrid-table") : null;
			/* ensure we are handling it in the correct grid */
			if (targetGrid && targetGrid.length > 0 && !targetGrid.attr("id").match(re)) {
				return;
			}
			/* Following checks are only needed when the keydown handler is on the container
			if ($.ig.util.isIE) {
				ae = document.activeElement ? $(document.activeElement) : $();
				aeId = ae.attr("id");
				// M.H. 30 Oct 2014 Fix for bug #184167: It is not possible to use keyboard navigation with row selectors when there are fixed columns - a JS error is thrown
				if (ae.length &&
					aeId !== this.grid.id() &&
					aeId !== this.grid.container().attr("id") &&
					!String(ae.closest("tbody").parent().attr("id")).match(re) &&
					!ae.is("td")) {
					return;
				}
			} else {
				if (event.target.id !== this.grid.container().attr("id") &&
					event.target.id !== this.grid.container().find("[id$='_headers_v'] a:first").attr("id")) {
					return;
				}
			}*/
			/* if no element is active we'll select and activate the first element */
			if (this._selection.activeElement === null || this._selection.activeElement === undefined) {
				/* M.H. 21 Mar 2015 Fix for bug 190835: Uncaught TypeError is thrown when a right or
				left arrow key is pressed immediately after filtering. */
				/* Here we have an active element that's not of the type of the setting */
				if (this.options.mode === "cell") {
					el = target.children("td:not([data-skip='true']):first");
					if (!el.length) {
						return;
					}
					this._singleSelectChange(el, false, false);
				} else {
					el = target.closest("tr");
					if (!el.length) {
						return;
					}
					this._selection.activate(this._identifierForRow(el), el);
					this._selection.toggle();
				}
				/* prevent scrolling */
				if (event.keyCode === $.ui.keyCode.SPACE) {
					event.preventDefault();
				}
				return;
			}
			event.preventDefault();
			/* space and enter should toggle the selection on the active element */
			if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
				if (this._suppressKey(event.keyCode)) {
					return;
				}
				if (!this._isDataElement(this._selection.activeElement)) {
					/* non-data elements may have specific actions attached on keydown SPACE/ENTER */
					this._handleToggle(event.ctrlKey || event.metaKey);
				} else {
					/* toggle the active element */
					this._selection.toggle();
				}
				return;
			}
			this._navigate(event.keyCode, event.ctrlKey || event.metaKey, event.shiftKey);
		},
		/* select start event handler */
		_selectStart: function (event) {
			var re = new RegExp("^" + this.grid.id() + "(_fixed)?$"),
				target = event.target ? $(event.target) : null,
				targetGrid = target ? target.closest(".ui-iggrid-table") : $();
			if (targetGrid.length > 0 && !targetGrid.attr("id").match(re)) {
				return;
			}
			if (event && this._suspend) {
				return;
			}
			if (this.options.mouseDragSelect === false || this.options.multipleSelection === false) {
				return false;
			}
			event.preventDefault();
		},
		_selectStartContainer: function (event) {
			// prevent start select when selecting through fixed container elements
			if (event.target && $(event.target).hasClass("ui-iggrid-fixedcontainer")) {
				event.preventDefault();
			}
		},
		_tbodyFocusIn: function (event) {
			var target = event && event.target ? $(event.target) : null,
				targetGrid = target ? target.closest(".ui-iggrid-table") : null,
				re = new RegExp("^" + this.grid.id() + "(_fixed)?$"), reo;
			if (this._selection.settings.owner) {
				reo = new RegExp("^" + this._selection.settings.owner.id() + "(_fixed)?$");
			}
			/* ensure we are handling it in the correct grid */
			if (targetGrid && targetGrid.length > 0) {
				if (!targetGrid.attr("id").match(re)) {
					return;
				}
				if (reo && !targetGrid.attr("id").match(reo)) {
					this._selection.changeOwner(this.grid);
				}
			}
			if (this.options.mode === "row") {
				/* should activate the row if row selection */
				if (target.is("tr")) {
					this._selection.activate(this._identifierForRow(target), target);
				}
			} else {
				if (target.is("td")) {
					/* D.K. 16 Sep 2015 Fix bug 206414 - Cannot move Up/Left/Right
					from a cell with expand/collapse button in the Expansion Column
					in cell mode. Moving down results in wrong selected cell. */
					this._selection.activate(this._identifierForTarget(target), target);
					if (!target.attr("data-gbsummary")) {
						this._storedActiveIndex = target.index();
					}
				}
			}
		},
		_tbodyFocusOut: function (event) {
			var target = event && event.target ? $(event.target) : null,
				targetGrid = target ? target.closest(".ui-iggrid-table") : null,
				re = new RegExp("^" + this.grid.id() + "(_fixed)?$"),
				id;
			/* ensure we are handling it in the correct grid */
			if (targetGrid && targetGrid.length > 0 && !targetGrid.attr("id").match(re)) {
				return;
			}
			if (this.options.mode === "row") {
				/* should activate the row if row selection */
				if (target.is("tr")) {
					id = this._identifierForRow(target);
					if (this._selection.isActive(id)) {
						this._selection.deactivate();
					}
				}
			} else {
				if (target.is("td")) {
					/* D.K. 16 Sep 2015 Fix bug 206414 - Cannot move Up/Left/Right
					from a cell with expand/collapse button in the Expansion Column
					in cell mode. Moving down results in wrong selected cell. */
					id = this._identifierForTarget(target);
					if (this._selection.isActive(id)) {
						this._selection.deactivate();
					}
				}
			}
		},
		_preventDefault: function (event) {
			if ($(event.target).is("td")) {
				event.preventDefault();
			}
		},
		/* document mouseup handler */
		_releaseMouse: function () {
			// ensures drag select stops if mouseup is done outside the grid
			this._dragSelect = false;
			this._firstDragCell = null;
			this._isMouseDown = false;
		},
		/* cell style restoration */
		_applyCellStyle: function (data, col) {
			var identifier = {
				id: data[ this._pkProp ],
				columnKey: col
			};
			return this._selection.isSelected(identifier, this.grid) ? this.css.selectedCell : "";
		},
		_applyRowStyle: function (data) {
			return this._selection.isSelected(data[ this._pkProp ], this.grid) ? this.css.selectedCell : "";
		},
		_paintActive: function () {
			if (this._selection.activeElement && this._selection.settings.owner === this.grid) {
				if (this.options.mode === "row") {
					this._getRowsByIdentifier(this._selection.activeElement).addClass(this.css.activeRow);
				} else {
					this._getCellByIdentifier(this._selection.activeElement).addClass(this.css.activeCell);
				}
			}
		},
		_renderAttributes: function () {
			if (this._selection.selectionLength()) {
				if (this.options.mode === "row") {
					this.grid.element.children("tbody").find("td.ui-iggrid-selectedcell")
						.closest("tr").attr("aria-selected", true);
					this.grid.fixedBodyContainer().find("tbody")
						.find("td.ui-iggrid-selectedcell").closest("tr").attr("aria-selected", true);
				} else {
					this.grid.element.children("tbody")
						.find("td.ui-iggrid-selectedcell").attr("aria-selected", true);
					this.grid.fixedBodyContainer()
						.find("tbody").find("td.ui-iggrid-selectedcell").attr("aria-selected", true);
				}
			} else {
				/* D.K. 24 Aug 2015 Fix for bug 202758 - After scrolling a grid
				with continuous virtualization and selection ARIA attribute
				aria-selected='false' is not applied to the records */
				if (this.options.mode === "row") {
					this.grid.element.children("tbody").find("td:not('.ui-iggrid-selectedcell')")
						.closest("tr").attr("aria-selected", false);
					this.grid.fixedBodyContainer().find("tbody")
						.find("td:not('.ui-iggrid-selectedcell')").closest("tr").attr("aria-selected", false);
				} else {
					this.grid.element.children("tbody")
						.find("td:not('.ui-iggrid-selectedcell')").attr("aria-selected", false);
					this.grid.fixedBodyContainer().find("tbody")
						.find("td:not('.ui-iggrid-selectedcell')").attr("aria-selected", false);
				}
			}
		},
		_defaultAttributes: function () {
			if (this.options.mode === "row") {
				this.grid.element.children("tbody").children("tr").attr("aria-selected", false);
				this.grid.fixedBodyContainer().find("tbody").children("tr").attr("aria-selected", false);
			} else {
				this.grid.element.children("tbody").find("td").attr("aria-selected", false);
				this.grid.fixedBodyContainer().find("tbody").find("td").attr("aria-selected", false);
			}
		},
		/* public API */
		clearSelection: function () {
			/* clears all selected cells, selected rows, active cell and active row. Also updates the UI accordingly
			*/
			this._selection.cleanAll(this.grid);
		},
		selectCell: function (row, col, isFixed) {
			/* selects a cell by row/col
				paramType="number" Row index
				paramType="number" Column index
				paramType="bool" optional="true" If the cell is part of the fixed or unfixed area of the grid.
			*/
			var rowElement, cellId, cellElement, record;
			if (this.options.mode === "row") {
				return;
			}
			if (this._isOutOfView(row, col)) {
				record = this._getDataView()[ row ];
				if (!record) {
					return;
				}
				if (this.grid._visibleColumns().length < col || col < 0) {
					return;
				}
				cellId = {
					id: this._pkProp ? row[ this._pkProp ] : row,
					columnKey: this.grid._visibleColumns()[ col ].key
				};
				cellElement = $();
			} else {
				row -= this.grid._startRowIndex || 0;
				col -= this.grid._startColIndex || 0;
				rowElement = this._getRowByIndex(row, isFixed);
				cellId = this._identifierForCell(this._getCellByIndex(col, rowElement));
				cellElement = this._getCellByIdentifier(cellId);
			}
			if (this._selection.settings.owner !== this.grid) {
				this._selection.changeOwner(this.grid);
			}
			if (this.options.activation) {
				this._selection.activate(cellId, cellElement);
			}
			if (!this._selection.isSelected(cellId, this.grid)) {
				this._selection.select(cellId, true, { element: cellElement }, true);
			}
		},
		selectCellById: function (id, colKey) {
			/* selects a cell by row id/column key
				paramType="number|string" Row Id
				paramType="string" Column key
			*/
			var identifier = { id: id, columnKey: colKey };
			if (this.options.mode === "row") {
				return;
			}
			if (!this._selection.isSelected(identifier, this.grid)) {
				if (this._selection.settings.owner !== this.grid) {
					this._selection.changeOwner(this.grid);
				}
				this._selection.select(identifier, true,
					{ element: this._getCellByIdentifier(identifier) }, true);
			}
		},
		deselectCell: function (row, col, isFixed) {
			/* deselects a cell by row/col
				paramType="number" Row index
				paramType="number" Column index
				paramType="bool" optional="true" If the cell is part of the fixed or unfixed area of the grid.
			*/
			var rowElement, cellId, cellElement;
			row -= this.grid._startRowIndex || 0;
			col -= this.grid._startColIndex || 0;
			rowElement = this._getRowByIndex(row, isFixed);
			cellId = this._identifierForCell(this._getCellByIndex(col, rowElement));
			cellElement = this._getCellByIdentifier(cellId);
			if (this.options.mode === "row") {
				return;
			}
			if (this._selection.isSelected(cellId, this.grid)) {
				this._selection.deselect(cellId, { element: cellElement }, true);
			}
		},
		deselectCellById: function (id, colKey) {
			/* deselects a cell by row id/column key
				paramType="number|string" Row Id
				paramType="string" Column key
			*/
			var identifier = { id: id, columnKey: colKey };
			if (this.options.mode === "row") {
				return;
			}
			if (this._selection.isSelected(identifier, this.grid)) {
				this._selection.deselect(identifier, { element: this._getCellByIdentifier(identifier) }, true);
			}
		},
		selectRow: function (index) {
			/* selects a row by index
				paramType="number" Row index
			*/
			var row, rowId;
			if (this.options.mode === "cell") {
				return;
			}
			if (this._isOutOfView(index)) {
				row = this._getDataView()[ index ];
				if (!row) {
					return;
				}
				rowId = this._pkProp ? row[ this._pkProp ] : index;
				row = $();
			} else {
				index -= this.grid._startRowIndex || 0;
				row = this._getRowByIndex(index, false).add(this._getRowByIndex(index, true));
				rowId = this._identifierForRow(row.eq(0));
			}
			if (this._selection.settings.owner !== this.grid) {
				this._selection.changeOwner(this.grid);
			}
			if (this.options.activation) {
				this._selection.activate(rowId, row);
			}
			if (!this._selection.isSelected(rowId, this.grid)) {
				this._selection.select(rowId, true, { element: row }, true);
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
				this._selection.select(id, true, { element: this._getRowsByIdentifier(id) }, true);
			}
		},
		deselectRow: function (index) {
			/* deselects a row by index
				paramType="number" Row index
			*/
			var row, rowId;
			index -= this.grid._startRowIndex || 0;
			row = this._getRowByIndex(index, false).add(this._getRowByIndex(index, true));
			rowId = this._identifierForRow(row.eq(0));
			if (this.options.mode === "cell") {
				return;
			}
			if (this._selection.isSelected(rowId, this.grid)) {
				this._selection.deselect(rowId, { element: row }, true);
			}
		},
		deselectRowById: function (id) {
			/* deselects a row by row id
				paramType="number|string" Row Id
			*/
			if (this.options.mode === "cell") {
				return;
			}
			if (this._selection.isSelected(id, this.grid)) {
				this._selection.deselect(id, { element: this._getRowsByIdentifier(id) }, true);
			}
		},
		selectedCells: function () {
			/* returns an array of selected cells in arbitrary order where every objects has the format { element: , row: , index: , rowIndex: , columnKey: } .
				If multiple selection is disabled the function will return null.
				returnType="array"
			*/
			if (this._selection.settings.owner !== this.grid) {
				return [];
			}
			return this.options.multipleSelection ? this._selection.selectedCells() : null;
		},
		selectedRows: function () {
			/* returns an array of selected rows in arbitrary order where every object has the format { element: , index: } .
				If multiple selection is disabled the function will return null.
				returnType="array"
			*/
			if (this._selection.settings.owner !== this.grid) {
				return [];
			}
			return this.options.multipleSelection ? this._selection.selectedRows() : null;
		},
		selectedCell: function () {
			/* returns the currently selected cell that has the format { element: , row: , index: , rowIndex: , columnKey: }, if any.
				If multiple selection is enabled the function will return null.
				returnType="object"
			*/
			var selectedCells;
			if (this._selection.settings.owner !== this.grid) {
				return null;
			}
			selectedCells = this._selection.selectedCells();
			return this.options.multipleSelection ?
				null : (selectedCells.length === 1 ? selectedCells[ 0 ] : null);
		},
		selectedRow: function () {
			/* returns the currently selected row that has the format { element: , index: }, if any.
				If multiple selection is enabled the function will return null.
				returnType="object"
			*/
			var selectedRows;
			if (this._selection.settings.owner !== this.grid) {
				return null;
			}
			selectedRows = this._selection.selectedRows();
			return this.options.multipleSelection ?
				null : (selectedRows.length === 1 ? selectedRows[ 0 ] : null);
		},
		activeCell: function () {
			/* returns the currently active (focused) cell that has the format { element: , row: , index: , rowIndex: , columnKey: }, if any.
				returnType="object"
			*/
			if (this.options.mode === "row") {
				return null;
			}
			if (this._selection.settings.owner !== this.grid) {
				return null;
			}
			return this._selection.activeCell();
		},
		activeRow: function () {
			/* returns the currently active (focused) row that has the format { element: , index: }, if any.
				returnType="object"
			*/
			if (this.options.mode === "cell") {
				return null;
			}
			if (this._selection.settings.owner !== this.grid) {
				return null;
			}
			return this._selection.activeRow();
		},
		/* private methods */
		_singleSelectChange: function (target, ctrlKey) {
			var tId, tIdx, oldT = target;
			/* get correct element type */
			if (this.options.mode === "row") {
				target = target.closest("tr");
				target.addClass("initially-focused");
				/* we need to add the row from the other grid if available */
				if (this.grid.hasFixedColumns()) {
					tIdx = target.index();
					target = target.add(this._getRowByIndex(tIdx, !this.grid._isFixedElement(target)));
				}
			} else {
				target = target.closest("td");
			}
			/* hgrid check if owner is the same */
			if (this._selection.settings.owner !== this.grid) {
				this._selection.changeOwner(this.grid);
			}
			/* each element must have an identifier. these identifiers
			can be built from the rowId/colKey or row index in rowIds absence */
			tId = this._identifierForTarget(target);
			/* D.K. 28 Aug 2015 Fix for bug #200623 - activeCellChanging fires
			again when you click a checkbox in a already active cell. */
			if (this.options.activation && (oldT.is("tr") || oldT.is("td"))) {
				this._selection.activate(tId, target);
			}
			if (this._canBeSelected(tId)) {
				this._selection.select(tId, ctrlKey, { element: target });
			}
		},
		_shiftSelectChange: function (target) {
			var tId,
				rangeStats,
				keep = this.options.mode === "cell" && this.options.allowMultipleRangeSelection,
				v = this.grid.options.virtualization ||
					this.grid.options.rowVirtualization ||
					this.grid.options.columnVirtualization;
			/* get correct element type */
			target = this.options.mode === "row" ? target.closest("tr") : target.closest("td");
			/* hgrid check if owner is the same */
			if (this._selection.settings.owner !== this.grid) {
				this._selection.changeOwner(this.grid);
			}
			/* each element must have an identifier. these identifiers
			can be built from the rowId/colKey or row index in rowIds absence */
			tId = this._identifierForTarget(target);
			if (!this._canBeSelected(tId)) {
				return;
			}
			if (this._selection.rangeSelectStart === null ||
				this._selection.rangeSelectStart === undefined) {
				this._selection.rangeSelectStart = tId;
			}
			if (v) {
				rangeStats = this._getRecordRange(this._selection.rangeSelectStart, tId);
			} else {
				rangeStats = this._getElementRange(this._selection.rangeSelectStart, tId);
			}
			/* returns an array of { id: element } pairs to get applied to the selection */
			if (!rangeStats.range || !rangeStats.range.length) {
				return;
			}
			if (keep) {
				this._selection.rangeSelect(
					rangeStats.range, false, this._previousRangeCache, rangeStats, false
				);
				this._previousRangeCache = rangeStats.range;
			} else {
				this._selection.rangeSelect(rangeStats.range, false, null, rangeStats, false);
			}
		},
		_navigate: function (code, ctrlKey, shiftKey, prevElementOverride) {
			var prevActiveElement = prevElementOverride || this._selection.activeElement,
				nextActiveElementParent, childSelection,
				nextActiveElement = this._getNextActiveElement(
					code, !shiftKey && this.options.wrapAround, prevActiveElement
				);
			if (!nextActiveElement || nextActiveElement.length === 0) {
				/* usually happens when wrap around is disabled
				and we've reached the edge rows of the grid */
				return;
			}
			nextActiveElementParent = nextActiveElement.closest(".ui-iggrid-table");
			/* M.H. 30 Oct 2014 Fix for bug #184167: It is not possible
			to use keyboard navigation with row selectors when there are
			fixed columns - a JS error is thrown */
			if (nextActiveElementParent.attr("id") !== this.grid.id() &&
				nextActiveElement.closest(".ui-iggrid").attr("id") !== this.grid.container().attr("id")) {
				/* navigation have gone into a child grid and that grid's selection needs to take over */
				childSelection = nextActiveElementParent.data("igGridSelection");
				/* change owner */
				this._selection.changeOwner(childSelection.grid);
				/* focus container */
				nextActiveElementParent.closest(".ui-iggrid").focus();
				/* pass handle to child Selection */
				childSelection._storedActiveIndex = null;
				childSelection._navigateOwn(nextActiveElement, prevActiveElement, code, ctrlKey, shiftKey);
			} else {
				this._navigateOwn(nextActiveElement, prevActiveElement, code, ctrlKey, shiftKey);
			}
		},
		_navigateOwn: function (nextActiveElement, prevActiveElement, code, ctrlKey, shiftKey) {
			var nextId = this._identifierForTarget(nextActiveElement);
			if (nextActiveElement.is("tr") && this.grid.hasFixedColumns()) {
				nextActiveElement.addClass("initially-focused");
				/* add back the other row (we'll always only have the fixed one) */
				nextActiveElement = nextActiveElement.add(
					this._getRowByIndex(nextActiveElement.index(),
					!this.grid._isFixedElement(nextActiveElement))
				);
			}
			if (!nextActiveElement.is("tr") && nextId.columnKey.startsWith("##") && shiftKey) {
				/* skip non-data cells with shift selection */
				this._navigate(code, ctrlKey, shiftKey, nextId);
				return;
			}
			this._selection.activate(nextId, nextActiveElement);
			if (ctrlKey) {
				/* with ctrl we are just navigating and not selecting, the selection remains the same */
				this._setScroll(code, nextActiveElement);
				return;
			}
			if (shiftKey) {
				if (this.options.mode === "row" || !this.options.multipleSelection) {
					if (this._selection.isSelected(this._selection.activeElement, this.grid)) {
						this._selection.deselect(prevActiveElement);
					} else {
						this._selection.toggle(nextActiveElement);
					}
				} else {
					/* with cell shift navigate should do a range select */
					this._shiftSelectChange(nextActiveElement, false);
				}
			} else {
				if (nextId.columnKey && nextId.columnKey.startsWith("##")) {
					/* non-data elements should only be activated */
					if (this._selection.isSelected(prevActiveElement)) {
						this._selection.clearSelection();
					}
				} else {
					this._selection.select(nextId, false, { element: nextActiveElement });
				}
			}
			this._setScroll(code, nextActiveElement);
		},
		_handleToggle: function (ctrlKey) {
			var element = this._selection.elementFromIdentifier(this._selection.activeElement),
				row, cell;
			if (element.is("th")) {
				// toggle the first data cell for the row
				row = this._getRowsByIdentifier(this._selection.activeElement.id);
				if (this.grid.hasFixedColumns() && this.grid._isFixedElement(element)) {
					// first cell for fixed row
					cell = this._firstDataCellForRow(row.first());
				} else {
					// first cell for unfixed row
					cell = this._firstDataCellForRow(row.last());
				}
				this._singleSelectChange(cell, ctrlKey);
			} else if (element.attr("data-parent")) {
				element.closest(".ui-iggrid-root").igHierarchicalGrid("toggle", element.closest("tr"));
			} else if (element.hasClass("ui-igtreegrid-expansion-indicator-cell") &&
				element.attr("data-expand-cell")) {
				element.closest(".ui-iggrid-table").igTreeGrid("toggleRow", element.closest("tr"));
			}
		},
		_getNextActiveElement: function (code, wrapAround, override) {
			var currentActiveElement = override instanceof jQuery ?
				override : this._selection.elementFromIdentifier(override || this._selection.activeElement),
				currentRow = currentActiveElement.closest("tr"),
				currentCellIndex = currentActiveElement.index(),
				hasFixedCols = this.grid.hasFixedColumns(),
				isFixedElement = hasFixedCols ? this.grid._isFixedElement(currentActiveElement) : false,
				fixingDir = this.grid.fixingDirection(),
				nextElement, nextElementIndex, nextRow, wrapped,
				go = this.grid.options,
				vVirt = go.virtualization || go.rowVirtualization;
			if (code === $.ui.keyCode.UP) {
				nextRow = this._getPrevRow(currentRow, wrapAround, isFixedElement, false, vVirt);
				if (this.options.mode === "row" || !nextRow) {
					return nextRow;
				}
				/* check if the row has the same amount of cells and if not select the right most */
				nextElementIndex = currentCellIndex >= nextRow.children().length ?
					nextRow.children().length - 1 : this._storedActiveIndex || currentCellIndex;
				nextElement = nextRow.children().eq(nextElementIndex);
			} else if (code === $.ui.keyCode.DOWN) {
				nextRow = this._getNextRow(currentRow, wrapAround, isFixedElement, false, vVirt);
				if (this.options.mode === "row" || !nextRow) {
					return nextRow;
				}
				/* check if the row has the same amount of cells and if not select the stored one */
				nextElementIndex = currentCellIndex >= nextRow.children().length ?
					nextRow.children().length - 1 : this._storedActiveIndex || currentCellIndex;
				nextElement = nextRow.children().eq(nextElementIndex);
			} else if (code === $.ui.keyCode.LEFT && this.options.mode === "cell") {
				if (currentActiveElement.is(":first-child")) {
					/* either we'll wrap to prev row or we'll move to the leftside grid */
					if (hasFixedCols && !isFixedElement && fixingDir === "left") {
						nextRow = this._getRowByIndex(currentRow.index(), true);
					} else if (hasFixedCols && isFixedElement && fixingDir === "right") {
						nextRow = this._getRowByIndex(currentRow.index(), false);
					} else {
						nextRow = this._getPrevRow(currentRow, wrapAround, isFixedElement, hasFixedCols, vVirt);
					}
					wrapped = true;
				} else {
					nextRow = currentRow;
				}
				if (!nextRow) {
					return null;
				}
				nextElementIndex = wrapped ? nextRow.children().length - 1 : currentCellIndex - 1;
				nextElement = nextRow.children().eq(nextElementIndex);
				this._revertH = wrapped;
			} else if (code === $.ui.keyCode.RIGHT && this.options.mode === "cell") {
				if (currentActiveElement.is(":last-child")) {
					// either we'll wrap to next row or we'll move to the rightside grid
					if (hasFixedCols && isFixedElement && fixingDir === "left") {
						nextRow = this._getRowByIndex(currentRow.index(), false);
					} else if (hasFixedCols && !isFixedElement && fixingDir === "right") {
						nextRow = this._getRowByIndex(currentRow.index(), true);
					} else {
						nextRow = this._getNextRow(currentRow, wrapAround, isFixedElement, hasFixedCols, vVirt);
					}
					wrapped = true;
				} else {
					nextRow = currentRow;
				}
				if (!nextRow) {
					return null;
				}
				nextElementIndex = wrapped ? 0 : currentCellIndex + 1;
				nextElement = nextRow.children().eq(nextElementIndex);
				this._revertH = wrapped;
			}
			if (nextElement && !nextElement.attr("data-gbsummary")) {
				this._storedActiveIndex = nextElementIndex;
			}
			return nextElement;
		},
		_getNextRow: function (currentRow, wrapAround, isFixed, swap, virt) {
			var hierRowSkip = this.options.skipChildren ? "[data-container=true]" : "", nextRow, cIdx;
			if (virt) {
				cIdx = currentRow.index();
				if (cIdx >= this.grid._virtualRowCount - 1) {
					this._scrollVmanual(true);
					return null;
				}
			}
			/* get the row bellow, account for non-data rows, hierarchical rows, etc */
			if (currentRow.first().parent()
				.children((hierRowSkip.length > 0 ? ":not(" + hierRowSkip + ")" : "") +
				":visible:last")[ 0 ] === currentRow[ 0 ]) {
				if (wrapAround) {
					nextRow = this._firstRow(null, swap ? !isFixed : isFixed, hierRowSkip);
				} else {
					return;
				}
				this._revertV = true;
			} else {
				/* :visible selector is not working in IE11 - returns false
				for visible fixed records. If we get width of the element before
				calling the selector it works as expected */
				if (isFixed && $.ig.util.isIE) {
					currentRow.width();
				}
				if (swap) {
					nextRow = this._getRowByIndex(currentRow.index() + 1, !isFixed);
				} else {
					nextRow = currentRow.nextAll("tr" + (hierRowSkip.length > 0 ?
						":not(" + hierRowSkip + ")" : "") + ":visible").first();
				}
				this._revertV = false;
			}
			if (nextRow.attr("data-container")) {
				/* focus-pocus - get the first row of the inner-most
				child grid inside the following hierarchy (revise) */
				nextRow = this._firstRow(nextRow, swap ? !isFixed : isFixed, hierRowSkip);
			}
			return nextRow;
		},
		_getPrevRow: function (currentRow, wrapAround, isFixed, swap, virt) {
			var hierRowSkip = this.options.skipChildren ? "[data-container=true]" : "", nextRow, cIdx;
			if (virt) {
				cIdx = currentRow.index();
				if (cIdx <= 0) {
					this._scrollVmanual(false);
					return null;
				}
			}
			/* get the row above, account for non-data rows, hierarchical rows, etc */
			if (currentRow.is(":first-child")) {
				if (wrapAround) {
					nextRow = this._lastRow(null, swap ? !isFixed : isFixed, hierRowSkip);
				} else {
					return;
				}
				this._revertV = true;
			} else {
				/* :visible selector is not working in IE11 - returns false
				for visible fixed records. If we get width of the element
				before calling the selector it works as expected */
				if (isFixed && $.ig.util.isIE) {
					currentRow.width();
				}
				if (swap) {
					nextRow = this._getRowByIndex(currentRow.index() - 1, !isFixed);
				} else {
					nextRow = currentRow.prevAll("tr" + (hierRowSkip.length > 0 ?
						":not(" + hierRowSkip + ")" : "") + ":visible").first();
				}
				this._revertV = false;
			}
			if (nextRow.attr("data-container")) {
				/* get the last row of the inner-most child grid inside the following hierarchy */
				nextRow = this._lastRow(nextRow, swap ? !isFixed : isFixed, hierRowSkip);
			}
			return nextRow;
		},
		_lastRow: function (container, fixed, rowSkip) {
			var body;
			if (container) {
				/* get the last row of the inner-most child grid */
				body = container.find("tbody");
			} else if (fixed) {
				body = this.grid.fixedBodyContainer().find("tbody");
			} else {
				body = this.grid.element.children("tbody");
			}
			return body.children("tr" + (rowSkip.length > 0 ?
				":not(" + rowSkip + ")" : "") + ":visible").last();
		},
		_firstRow: function (container, fixed, rowSkip) {
			var body;
			if (container) {
				/* get the last row of the inner-most child grid */
				body = container.find("tbody");
			} else if (fixed) {
				body = this.grid.fixedBodyContainer().find("tbody");
			} else {
				body = this.grid.element.children("tbody");
			}
			return body.children("tr" + (rowSkip.length > 0 ? ":not(" + rowSkip + ")" : "")).first();
		},
		_createUidForData: function () {
			var i = 0, ds = this.grid.dataSource ? this.grid.dataSource._data : [];
			for (i = 0; i < ds.length; i++) {
				/*jshint -W106*/
				/*jscs:disable*/
				if (!ds[i].ig_pk) {
					ds[i].ig_pk = $.ig.util.getCheckSumForObject(ds[i]);
				}
				/*jscs:enable*/
				/*jshint +W106*/
			}
		},
		_initSelectionCollection: function () {
			var rootGrid, opts;
			/* the selection collection will have only one instance hooked to the root grid */
			rootGrid = this.grid.element.closest(".ui-iggrid-root");
			if (rootGrid.length > 0) {
				rootGrid = rootGrid.data("igGrid");
			} else {
				rootGrid = this.grid;
			}
			opts = {
				multipleSelection: this.options.multipleSelection,
				byIndex: this._pkProp === null,
				owner: this.grid
			};
			if (this.options.mode === "row") {
				rootGrid._selectionCollection = rootGrid._selectionCollection ||
					new $.ig.SelectedRowsCollection(opts);
			} else {
				rootGrid._selectionCollection = rootGrid._selectionCollection ||
					new $.ig.SelectedCellsCollection(opts);
			}
			/* store a local reference for easy access */
			this._selection = rootGrid._selectionCollection;
			this.grid._selection = this._selection;
			/* add the current widget's subscription */
			if (!this._subId) {
				this._subId = this._selection.addSubscriber(this, this.grid.id());
			}
		},
		_setScroll: function (code, nextActiveElement) {
			var scrollHDir;
			/* When cells / rows can be focused, we'll leave the browser to handle the vertical
			scroll position. We only need to update the horizontal one for better ux and the vertical
			when there is continuous virtualization because the browser won't update the virtual scrollbar */
			var scrollVDir = this._revertV ?
				(code === $.ui.keyCode.DOWN || code === $.ui.keyCode.RIGHT ? "up" : "down") :
				(code === $.ui.keyCode.DOWN || code === $.ui.keyCode.RIGHT ? "down" : "up");
			this._setScrollTop(this.grid.element.parent(), nextActiveElement.closest("tr"),
				scrollVDir, nextActiveElement.closest("tr").index());
			if (this.options.mode === "cell" &&
				(code === $.ui.keyCode.LEFT || code === $.ui.keyCode.RIGHT)) {
				scrollHDir = this._revertH ? (code === $.ui.keyCode.LEFT ? "right" : "left") :
					(code === $.ui.keyCode.LEFT ? "left" : "right");
				this._setScrollLeft(
					this.grid._hscrollbarcontent(), nextActiveElement, scrollHDir, nextActiveElement.index()
				);
			}
		},
		_setScrollTop: function (parent, child, direction, index) {
			var parentOffset = parent.offset(), childOffset = child.offset(), childh, isDown, isUp, v, c;
			if (!child || child.length === 0) {
				return;
			}
			v = (this.grid.options.virtualization || this.grid.options.rowVirtualization);
			c = v && this.grid.options.virtualizationMode === "continuous";
			childh = v && !c ? parseInt(this.grid.options.avgRowHeight, 10) : child.outerHeight();
			if (!v || c) {
				isDown = childOffset.top + childh + this.grid._scrollbarWidth() >
					parentOffset.top + $(parent).outerHeight();
				isUp = childOffset.top - childh / 2 <= parentOffset.top || (c && index < 0);
			}
			if (index === 0 && (!v || c)) {
				parent[ 0 ].scrollTop = 0;
			} else if (direction === "down") {
				if (isDown) {
					if (c) {
						// continuous virtualization
						this.grid._onVirtualVerticalScroll({}, childh, direction);
					}
				}
			} else {
				if (isUp) {
					if (c) {
						// continuous virtualization
						this.grid._onVirtualVerticalScroll({}, childh, direction);
					}
				}
			}
		},
		_setScrollLeft: function (parent, child, direction, index) {
			var parentOffset = parent.offset(), sc, childOffset = child.offset(), fullLeft;
			if (!child || !child.length || !parent.length) {
				return;
			}
			sc = this.grid.scrollContainer();
			fullLeft = childOffset.left + (sc.length > 0 ? sc.scrollLeft() : 0);
			if (index === 0) {
				parent[ 0 ].scrollLeft = 0;
				this.grid._synchronizeHScroll();
			} else if (direction === "right") {
				if (fullLeft + child.outerWidth() > parentOffset.left + parent.outerWidth()) {
					parent[ 0 ].scrollLeft = parent[ 0 ].scrollLeft + fullLeft -
						(parentOffset.left + parent.outerWidth()) + child.outerWidth();
					this.grid._synchronizeHScroll();
				}
			} else {
				if (childOffset.left < parentOffset.left) {
					parent[ 0 ].scrollLeft = parent[ 0 ].scrollLeft - child.outerWidth();
					this.grid._synchronizeHScroll();
				}
			}
		},
		_scrollVmanual: function (down) {
			var sc = $("#" + this.grid.element[ 0 ].id + "_scrollContainer"),
				h = parseInt(this.grid.options.avgRowHeight, 10);
			this.grid._ignoreScroll = true;
			if (down) {
				sc.scrollTop(sc.scrollTop() + h);
			} else {
				sc.scrollTop(sc.scrollTop() - h);
			}
			this.grid._onVirtualVerticalScroll();
			this.grid._ignoreScroll = false;
		},
		_getRecordRange: function (start, end, data) {
			var startRecord, endRecord, startRecordIndex, endRecordIndex, startPropIndex, endPropIndex,
				range = [], recordRange, propRange, i, j, dv = data ? data : this._getDataView(),
				srId, erId, vcols = this.grid._visibleColumns();
			if (this.options.mode === "row") {
				srId = start;
				erId = end;
			} else {
				srId = start.id;
				erId = end.id;
			}
			/* technically shift selection allows us to select rows which are out of view when virtualization is enabled
			this means that the level of this function disallows us from using rows or cells directly. It should mark
			a range from the data view that then pass it to the collection which will notify select for each element
			allowing for those that are in view to get selected. */
			if (this._pkProp) {
				for (i = 0; i < dv.length; i++) {
					if (dv[ i ][ this._pkProp ] === srId) {
						startRecord = dv[ i ];
						startRecordIndex = i;
					}
					if (dv[ i ][ this._pkProp ] === erId) {
						endRecord = dv[ i ];
						endRecordIndex = i;
					}
					if (startRecord && endRecord) {
						break;
					}
				}
			} else {
				startRecord = srId >= 0 && srId < dv.length ? dv[ srId ] : null;
				startRecordIndex = srId;
				endRecord = erId >= 0 && erId < dv.length ? dv[ erId ] : null;
				endRecordIndex = erId;
			}
			if (!startRecord || !endRecord) {
				return;
			}
			if (startRecordIndex > endRecordIndex) {
				recordRange = dv.slice(endRecordIndex, startRecordIndex + 1);
			} else {
				recordRange = dv.slice(startRecordIndex, endRecordIndex + 1);
			}
			startRecordIndex = Math.min(startRecordIndex, endRecordIndex);
			if (this.options.mode === "row") {
				/* build the range object to be used in the collection optimally */
				if (!this._pkProp) {
					for (i = 0; i < recordRange.length; i++) {
						range.push({ id: i + startRecordIndex });
					}
				} else {
					for (i = 0; i < recordRange.length; i++) {
						range.push({ id: recordRange[ i ][ this._pkProp ] });
					}
				}
				return {
					startIndex: startRecordIndex,
					endIndex: endRecordIndex,
					active: end,
					element: this._getRowsByIdentifier(end),
					range: range
				};
			}
			/* cell */
			for (i = 0; i < vcols.length; i++) {
				if (vcols[ i ].key === start.columnKey) {
					startPropIndex = i;
				}
				if (vcols[ i ].key === end.columnKey) {
					endPropIndex = i;
				}
				if (startPropIndex !== undefined && endPropIndex !== undefined) {
					break;
				}
			}
			if (startPropIndex === undefined || endPropIndex === undefined) {
				return;
			}
			if (startPropIndex > endPropIndex) {
				propRange = vcols.slice(endPropIndex, startPropIndex + 1);
			} else {
				propRange = vcols.slice(startPropIndex, endPropIndex + 1);
			}
			if (!this._pkProp) {
				for (i = 0; i < recordRange.length; i++) {
					for (j = 0; j < propRange.length; j++) {
						range.push({
							id: {
								id: i + startRecordIndex,
								columnKey: propRange[ j ].key
							}
						});
					}
				}
			} else {
				for (i = 0; i < recordRange.length; i++) {
					for (j = 0; j < propRange.length; j++) {
						range.push({
							id: {
								id: recordRange[ i ][ this._pkProp ],
								columnKey: propRange[ j ].key
							}
						});
					}
				}
			}
			return {
				firstRowIndex: startRecordIndex,
				lastRowIndex: endRecordIndex,
				firstColumnIndex: startPropIndex,
				lastColumnIndex: endPropIndex,
				active: end,
				element: this._getCellByIdentifier(end),
				range: range
			};
		},
		_getElementRange: function (start, end) {
			var startElement, endElement, startRowIndex, endRowIndex, range = [],
				i, reverse, rowRange, fixedRowRange, cellRange, rowIndex, isStartFixed, isEndFixed;
			startElement = this._selection.elementFromIdentifier(start);
			endElement = this._selection.elementFromIdentifier(end);
			startRowIndex = this._getIndexForRow(startElement.closest("tr"));
			endRowIndex = this._getIndexForRow(endElement.closest("tr"));
			reverse = startRowIndex > endRowIndex;
			if (reverse) {	// swap
				startRowIndex = startRowIndex + endRowIndex;
				endRowIndex = startRowIndex - endRowIndex;
				startRowIndex = startRowIndex - endRowIndex;
			}
			rowRange = this.grid.element.find("tbody").children("tr:not([data-container]):visible")
				.slice(startRowIndex, endRowIndex + 1);
			if (this.grid.hasFixedColumns()) {
				fixedRowRange = this.grid.fixedBodyContainer().find("tbody")
					.children("tr:not([data-container]):visible")
					.slice(startRowIndex, endRowIndex + 1);
			}
			if (this.options.mode === "row") {
				for (i = 0; i < rowRange.length; i++) {
					rowIndex = i + startRowIndex;
					range.push({
						id: this._pkProp ? this._identifierForRow(rowRange.eq(i)) : rowIndex,
						index: rowIndex,
						element: rowRange.eq(i).add(fixedRowRange ? fixedRowRange.eq(i) : null)
					});
				}
				return {	// return a full information object to pass on to event handlers
					startIndex: startRowIndex,
					endIndex: endRowIndex,
					active: end,
					element: endElement,
					range: range
				};
			}
			if (this.grid.hasFixedColumns()) {
				isStartFixed = this.grid._isFixedElement(startElement);
				isEndFixed = this.grid._isFixedElement(endElement);
				if (isStartFixed && isEndFixed) {
					cellRange = this._getCellRangeFor(
						fixedRowRange, startElement, endElement, startRowIndex
					);
				} else if (isStartFixed !== isEndFixed) {
					cellRange = this._getCrossCellRange(
						rowRange, fixedRowRange, startElement, endElement, startRowIndex
					);
				} else {
					cellRange = this._getCellRangeFor(rowRange, startElement, endElement, startRowIndex);
				}
			} else {
				cellRange = this._getCellRangeFor(rowRange, startElement, endElement, startRowIndex);
			}
			return $.extend(cellRange, {
				firstRowIndex: startRowIndex,
				lastRowIndex: endRowIndex,
				active: end,
				element: endElement
			});
		},
		_getCrossCellRange: function (rowRange, fixedRowRange, startElement, endElement, startRowIndex) {
			// called when the start and end elements are in different grids (fixing scenario)
			var fixingDir = this.grid.fixedBodyContainer().attr("data-fixing-direction"),
				startFixedElement, endFixedElement, startUnfixedElement, endUnfixedElement,
				startFixedRow, endFixedRow, startUnfixedRow, endUnfixedRow,
				unfixedRange, fixedRange;
			if (this.grid._isFixedElement(startElement)) {
				// setup
				startFixedRow = startElement.parent();
				startFixedElement = startElement;
				endUnfixedRow = endElement.parent();
				endUnfixedElement = endElement;
				endFixedRow = this._getRowByIndex(endUnfixedRow.index(), true);
				startUnfixedRow = this._getRowByIndex(startFixedRow.index(), false);
				if (fixingDir === "left") {
					// calc the ranges
					endFixedElement = endFixedRow.children("td:not([data-skip=true])").last();
					startUnfixedElement = startUnfixedRow.children("td:not([data-skip=true])").first();
				} else {
					endFixedElement = endFixedRow.children("td:not([data-skip=true])").first();
					startUnfixedElement = startUnfixedRow.children("td:not([data-skip=true])").last();
				}
			} else {
				// setup
				startUnfixedRow = startElement.parent();
				startUnfixedElement = startElement;
				endFixedRow = endElement.parent();
				endFixedElement = endElement;
				endUnfixedRow = this._getRowByIndex(endFixedRow.index(), false);
				startFixedRow = this._getRowByIndex(startUnfixedRow.index(), true);
				if (fixingDir === "left") {
					// calc the ranges
					endUnfixedElement = endUnfixedRow.children("td:not([data-skip=true])").first();
					startFixedElement = startFixedRow.children("td:not([data-skip=true])").last();
				} else {
					endUnfixedElement = endUnfixedRow.children("td:not([data-skip=true])").last();
					startFixedElement = startFixedRow.children("td:not([data-skip=true])").first();
				}
			}
			fixedRange = this._getCellRangeFor(
				fixedRowRange,
				startFixedElement,
				endFixedElement,
				startRowIndex
			);
			unfixedRange = this._getCellRangeFor(
				rowRange,
				startUnfixedElement,
				endUnfixedElement,
				startRowIndex
			);
			return { // return a full information object to pass on to event handlersrange;
				range: fixedRange.range.concat(unfixedRange.range),
				firstColumnIndex: fixedRange.startCellIndex,
				lastColumnIndex: fixedRange.endCellIndex
			};
		},
		_getCellRangeFor: function (rowRange, startElement, endElement, startRowIndex) {
			var startCellIndex, endCellIndex, reverse, crLength, cellRange, range = [],
				row, rowIndex, cell, cellIndex, i, startElementIndex;
			startCellIndex = startElement.parent().children("td").index(startElement);
			endCellIndex = endElement.parent().children("td").index(endElement);
			reverse = startCellIndex > endCellIndex;
			if (reverse) {	// swap
				startCellIndex = startCellIndex + endCellIndex;
				endCellIndex = startCellIndex - endCellIndex;
				startCellIndex = startCellIndex - endCellIndex;
			}
			crLength = endCellIndex - startCellIndex + 1;
			/* we will select the cells directly in this case
			only works without fixing rework - split based on start and end element's place */
			cellRange = rowRange.find("td:nth-of-type(n+" +
				(startCellIndex + 1) + "):nth-of-type(-n+" +
				(endCellIndex + 1) + ")");
			if (!cellRange.length) {
				return { range: range };
			}
			/* initial helpers */
			row = cellRange.eq(0).parent();
			rowIndex = startRowIndex;
			startElementIndex = startElement.index();
			for (i = 0; i < cellRange.length; i++) {
				cell = cellRange.eq(i);
				if (cell.parent()[ 0 ] !== row[ 0 ]) {
					row = cell.parent();
					rowIndex = startRowIndex + Math.floor(i / crLength);
				}
				cellIndex = i % crLength + startElementIndex;
				range.push({
					id: this._identifierForCell(cell),
					element: cell,
					rowIndex: rowIndex,
					index: cellIndex
				});
			}
			return {	// return a full information object to pass on to event handlersrange;
				firstColumnIndex: startCellIndex,
				lastColumnIndex: endCellIndex,
				range: range
			};
		},
		_isOutOfView: function (rowIdx, colIdx) {
			var rv = this.grid.options.rowVirtualization || this.grid.options.virtualization,
				cv = this.grid.options.columnVirtualization || this.grid.options.virtualization,
				sri = this.grid._startRowIndex || 0, sci = this.grid._startColIndex || 0,
				vrc = this.grid._virtualRowCount, vcc = this.grid._virtualColumnCount;
			if (colIdx === null || colIdx === undefined) {
				return rv ? rowIdx < sri || rowIdx >= sri + vrc : false;
			}
			return rv || cv ?
				(rowIdx < sri || rowIdx >= sri + vrc) || (colIdx < sci || colIdx >= sci + vcc) : false;
		},
		_isDataElement: function (identifier) {
			var type = $.type(identifier);
			return type === "number" || type === "string" || !identifier.columnKey.startsWith("##");
		},
		_suppressKey: function (keyCode) {
			// checks if another feature uses the key and the default
			// behavior for this key should be suppressed
			var updating = this.grid.element.data("igGridUpdating"), key;
			switch (keyCode) {
				case $.ui.keyCode.ENTER:
					key = "enter";
					break;
				case $.ui.keyCode.SPACE:
					key = "space";
					break;
			}
			if (updating) {
				return updating.options.startEditTriggers.indexOf(key) >= 0;
			}
			return false;
		},
		_firstDataCellForRow: function (row) {
			return row.children("td:not([data-skip='true'],td[data-parent]):first");
		},
		_canBeSelected: function (identifier) {
			return (!identifier.columnKey) ||										// for row
				(identifier.columnKey && !identifier.columnKey.startsWith("##"));	// for cell
		},
		/* translate element to ids */
		_identifierForTarget: function (target) {
			if (target.is("tr")) {
				return this._identifierForRow(target);
			}
			if (target.hasClass("ui-iggrid-nongrouprowemptycell")) {
				return this._identifierForDataSkipCell(target, this._gbEmptyCellKey);
			}
			if (target.hasClass("ui-iggrid-expandcolumn")) {
				if (target.attr("data-parent")) {
					return this._identifierForDataSkipCell(target, this._hgCellKey);
				}
				return this._identifierForDataSkipCell(target, this._gbExpandCellKey);
			}
			if (target.attr("data-gbsummary")) {
				return this._identifierForDataSkipCell(target, this._gbSummaryCellKey);
			}
			if (target.hasClass("ui-igtreegrid-non-data-column") && target.attr("data-skip")) {
				return this._identifierForDataSkipCell(target, this._tgExpandKey);
			}
			if (target.is("td")) {
				return this._identifierForCell(target);
			}
			if (target.is("th")) {
				return this._identifierForDataSkipCell(target, this._rsCellKey);
			}
			return null;
		},
		_identifierForCell: function (cell) {
			var row = cell.parent(), id = this.grid._fixPKValue(row.attr("data-id"));
			if (id === null || id === undefined) {
				id = this._getIndexForRow(row);
			}
			return { id: id, columnKey: this._getColKeyForCell(cell) };
		},
		_identifierForRow: function (row) {
			var id = this.grid._fixPKValue(row.attr("data-id"));
			if (id !== null && id !== undefined) {
				return id;
			}
			return this._getIndexForRow(row);
		},
		_identifierForDataSkipCell: function (cell, key) {
			var row = cell.parent(), id = this.grid._fixPKValue(row.attr("data-id"));
			if (id === null || id === undefined) {
				id = this._getIndexForRow(row);
			}
			return { id: id, columnKey: key };
		},
		/* translate ids to elements */
		_getRowsByIdentifier: function (identifier) {
			// gets the rows for the identifier (both fixed and unfixed)
			var urow, frow = $();
			if (!this._pkProp) {
				// the row id is index - we'll get the rows based on the indexing conventions for the options
				if (this.grid.hasFixedColumns()) {
					frow = this._getRowByIndex(identifier, true);
				}
				urow = this._getRowByIndex(identifier, false);
			} else {
				if (this.grid.hasFixedColumns()) {
					frow = this.grid.rowById(identifier, true);
				}
				urow = this.grid.rowById(identifier, false);
			}
			return urow instanceof jQuery ? urow.add(frow) : $();
		},
		_getCellByIdentifier: function (identifier) {
			var cellRow, isFixed, skippedCells, colIndex, i, cols, sci = this.grid._startColIndex || 0;
			if (identifier.columnKey && identifier.columnKey.startsWith("##")) {
				return this._getNonDataCellByIdentifier(identifier);
			}
			if (!this._pkProp) {
				isFixed = this.grid.isFixedColumn(identifier.columnKey);
				/* the row id is index - we'll get the rows based on the indexing conventions for the options */
				cellRow = this._getRowByIndex(identifier.id, isFixed);
				/* get the visible cols */
				cols = this.grid._visibleColumns(isFixed);
				skippedCells = cellRow.children("th,[data-skip=true],[data-parent]").length;
				for (i = 0; i < cols.length; i++) {
					if (cols[ i ].key === identifier.columnKey) {
						colIndex = i;
						break;
					}
				}
				return cellRow.children("td:nth-child(" + (colIndex + 1 + skippedCells - sci) + ")");
			}
			return this.grid.cellById(identifier.id, identifier.columnKey) || $();
		},
		_getNonDataCellByIdentifier: function (identifier) {
			var cellRow;
			if (!this._pkProp) {
				cellRow = this._getRowByIndex(identifier.id, this.grid.hasFixedColumns());
			} else {
				cellRow = this._getRowsByIdentifier(identifier.id);
			}
			switch (identifier.columnKey) {
				case this._rsCellKey:
					return cellRow.children("th:first");
				case this._gbEmptyCellKey:
					return cellRow.children(".ui-iggrid-nongrouprowemptycell:first");
				case this._gbExpandCellKey:
					return cellRow.children().first();
				case this._gbSummaryCellKey:
					return cellRow.children().last();
				case this._hgCellKey:
					return cellRow.children("td[data-parent='true']");
				case this._tgExpandKey:
					return cellRow.children(".ui-igtreegrid-non-data-column:first");
				default:
					return $();
			}
		},
		_getRecordByIdentifier: function (identifier) {
			// returns both the record and its index
			var dv = this._getDataView(), i, record = null, index = -1;
			if (this._pkProp) {
				for (i = 0; i < dv.length; i++) {
					if (dv[ i ][ this._pkProp ] === identifier) {
						record = dv[ i ];
						index = i;
						break;
					}
				}
			} else {
				if (identifier >= 0 && identifier < dv.length) {
					record = dv[ identifier ];
					index = identifier;
				}
			}
			return {
				record: record,
				index: index
			};
		},
		/* translation helpers */
		_getRowByIndex: function (index, fixed) {
			var tbody = fixed ?
				this.grid.fixedBodyContainer().find("tbody") : this.grid.element.find("tbody");
			return tbody.children("tr:not([data-container])").eq(index);
		},
		_getCellByIndex: function (index, row) {
			return row.children("td").eq(index);
		},
		_getIndexForRow: function (row) {
			return row.closest("tbody").children("tr:not([data-container])").index(row) +
				/* add virtualization start */
				(this.grid._startRowIndex || 0);
		},
		_getColKeyForCell: function (cell) {
			var col = this.grid.getColumnByTD(cell);
			return col ? col.column.key : null;
		},
		_getColIndexByKey: function (key) {
			var i, columns = this.grid._visibleColumns();
			for (i = 0; i < columns.length; i++) {
				if (columns[ i ].key === key) {
					return i;
				}
			}
			return -1;
		},
		_getDataView: function () {
			return this.grid.dataSource.dataView();
		},
		/* event callbacks */
		_onRowSelectionChanging: function (a) {
			var args = {
				owner: this,
				selectedRows: this._selection.selectedRows()
			};
			if (a.element && a.element.length) {
				args.row = {
					element: a.element,
					index: a.index !== undefined ? a.index : this._getIndexForRow(a.element)
				};
				if (this._pkProp) {
					args.row.id = a.id || this._identifierForRow(a.element);
				}
			} else {
				args.row = {
					element: $(),
					index: -1
				};
				if (this._pkProp) {
					args.row.id = a.id;
				}
			}
			if (a.startIndex !== undefined) {
				args.startIndex = a.startIndex;
				args.endIndex = a.endIndex;
			}
			return this._trigger(this.events.rowSelectionChanging, null, args);
		},
		_onRowSelectionChanged: function (a) {
			var args = {
				owner: this,
				selectedRows: this._selection.selectedRows()
			};
			if (a.element && a.element.length) {
				args.row = {
					element: a.element,
					index: a.index !== undefined ? a.index : this._getIndexForRow(a.element)
				};
				if (this._pkProp) {
					args.row.id = a.id || this._identifierForRow(a.element);
				}
			} else {
				args.row = {
					element: $(),
					index: -1
				};
				if (this._pkProp) {
					args.row.id = a.id;
				}
			}
			return this._trigger(this.events.rowSelectionChanged, null, args);
		},
		_onCellSelectionChanging: function (a) {
			var args = {
				owner: this,
				selectedCells: this._selection.selectedCells()
			};
			if (a.element && a.element.length) {
				args.cell = {
					element: a.element,
					row: a.row || a.element.closest("tr"),
					columnKey: a.id ? a.id.columnKey : this._getColKeyForCell(a.element)
				};
				args.cell.index = a.index !== undefined ?
					a.index : this._getColIndexByKey(args.cell.columnKey);
				args.cell.rowIndex = a.rowIndex !== undefined ?
					a.rowIndex : this._getIndexForRow(args.cell.row);
				if (this._pkProp) {
					args.cell.rowId = a.id ? a.id.id : this._identifierForRow(args.cell.row);
				}
			} else {
				args.cell = {
					element: $(),
					row: $(),
					columnKey: a.id.columnKey
				};
				args.cell.index = -1;
				args.cell.rowIndex = -1;
				if (this._pkProp) {
					args.cell.rowId = a.id ? a.id.id : null;
				}
			}
			if (a.firstColumnIndex !== undefined) {
				args.firstColumnIndex = a.firstColumnIndex;
				args.firstRowIndex = a.firstRowIndex;
				args.lastColumnIndex = a.lastColumnIndex;
				args.lastRowIndex = a.lastRowIndex;
			}
			return this._trigger(this.events.cellSelectionChanging, null, args);
		},
		_onCellSelectionChanged: function (a) {
			var args = {
				owner: this,
				selectedCells: this._selection.selectedCells()
			};
			if (a.element && a.element.length) {
				args.cell = {
					element: a.element,
					row: a.row || a.element.closest("tr"),
					columnKey: a.id ? a.id.columnKey : this._getColKeyForCell(a.element)
				};
				args.cell.index = a.index !== undefined ?
					a.index : this._getColIndexByKey(args.cell.columnKey);
				args.cell.rowIndex = a.rowIndex !== undefined ?
					a.rowIndex : this._getIndexForRow(args.cell.row);
				if (this._pkProp) {
					args.cell.rowId = a.rowId || this._identifierForRow(args.cell.row);
				}
			} else {
				args.cell = {
					element: $(),
					row: $(),
					columnKey: a.id.columnKey
				};
				args.cell.index = -1;
				args.cell.rowIndex = -1;
				if (this._pkProp) {
					args.cell.rowId = a.id ? a.id.id : null;
				}
			}
			return this._trigger(this.events.cellSelectionChanged, null, args);
		},
		_onRowActivationChanging: function (a) {
			var args = {
				owner: this,
				row: a ? {
					element: a.element,
					index: this._getIndexForRow(a.element)
				} : null
			};
			if (this._pkProp && a) {
				args.row.id = a.id;
			}
			return this._trigger(this.events.activeRowChanging, null, args);
		},
		_onRowActivationChanged: function (a) {
			var args = {
				owner: this,
				row: a ? {
					element: a.element,
					index: this._getIndexForRow(a.element)
				} : null
			};
			if (this._pkProp && a) {
				args.row.id = a.id;
			}
			return this._trigger(this.events.activeRowChanged, null, args);
		},
		_onCellActivationChanging: function (a) {
			var args = {
				owner: this,
				cell: a ? {
					element: a.element,
					row: a.element.closest("tr")
				} : null
			};
			if (a) {
				args.cell.index = this._getColIndexByKey(a.id.columnKey);
				args.cell.rowIndex = this._getIndexForRow(args.cell.row);
				args.cell.columnKey = a.id.columnKey;
				if (this._pkProp) {
					args.cell.rowId = a.id.id;
				}
			}
			return this._trigger(this.events.activeCellChanging, null, args);
		},
		_onCellActivationChanged: function (a) {
			var args = {
				owner: this,
				cell: a ? {
					element: a.element,
					row: a.element.closest("tr")
				} : null
			};
			if (a) {
				args.cell.index = this._getColIndexByKey(a.id.columnKey);
				args.cell.rowIndex = this._getIndexForRow(args.cell.row);
				args.cell.columnKey = a.id.columnKey;
				if (this._pkProp) {
					args.cell.rowId = a.id.id;
				}
			}
			return this._trigger(this.events.activeCellChanged, null, args);
		},
		/* collection callbacks */
		_select: function (info) {
			// should apply selected classes to the element
			var element = info.element || this._selection.elementFromIdentifier(info.id);
			if (element.is("tr")) {
				element.children("td").not("[data-skip=true]").addClass(this.css.selectedCell);
			} else {
				element.addClass(this.css.selectedCell);
			}
			element.attr("aria-selected", true);
		},
		_deselect: function (info) {
			var element = info.element || this._selection.elementFromIdentifier(info.id);
			if (element.is("tr")) {
				element.children("td").removeClass(this.css.selectedCell);
			} else {
				element.removeClass(this.css.selectedCell);
			}
			element.attr("aria-selected", false);
		},
		_resizeGridWidth: function (decrease) {
			if ((decrease && this._gridWidthDecreased) ||
				(!decrease && !this._gridWidthDecreased)) {
				return;
			}
			var grid = this.grid, diff,
				padding = parseInt(grid.element.find("tbody tr td:last-child").css(grid._padding), 10),
				$col = grid.element.find("colgroup:first>col:last");
			this._scrollbarWidth = this._scrollbarWidth || grid._scrollbarWidth();
			diff = this._scrollbarWidth;
			this._gridWidthDecreased = false;
			if (decrease) {
				diff = -diff;
				this._gridWidthDecreased = true;
			}
			grid.element.find("tbody tr td:last-child").css({
				"padding-right": padding + diff,
				"border-right": diff > 0 ? "1px" : "0px"
			});
			grid.element.css("width", grid.element.width() + diff);
			$col.width($col.width() + diff);
		},
		_isScrollbarOverLastCells: function () {
			if (this._isScrollbarOverLastCellsCache === undefined ||
				this._isScrollbarOverLastCellsCache === null) {
				var opts = this.grid.options,
					hasFixedHeaders = opts.showHeader && opts.fixedHeaders === true && opts.height !== null,
					hasVirtualization = opts.virtualization === true || opts.columnVirtualization === true ||
						opts.rowVirtualization === true,
					lastColInPixels = (this.grid._lastColPixelWidth !== undefined &&
						this.grid._lastColPixelWidth !== null),
					hasHorizontalScrollbar = this.grid._hscrollbar().length > 0;
				this._isScrollbarOverLastCellsCache = this.grid._hasVerticalScrollbar &&
					hasFixedHeaders && !hasVirtualization &&
					hasHorizontalScrollbar && lastColInPixels;
			}
			return this._isScrollbarOverLastCellsCache;
		},
		_activate: function (element) {
			var initiallyFocused, me = this;
			/* should apply activated classes to the element */
			if (element.is("tr")) {
				element.addClass(this.css.activeRow);
				initiallyFocused = element.filter(".initially-focused");
				if (initiallyFocused.length) {
					initiallyFocused.focus();
					initiallyFocused.removeClass("initially-focused");
					return;
				}
			} else {
				element.addClass(this.css.activeCell);
			}
			if (this.options.mode === "cell" &&
				$.ig.util.isIE && element.is(":last-child") &&
				this._isScrollbarOverLastCells()) {
				//this is needed because the last cell has part under the scrollbar and IE is trying to scroll the cell into view
				//which causes flickering(it is easy to repr navigating through the last cells)
				//execute ONLY if grid has width in px and last col has width in px and the grid has vertical scrollbar and virtualization is disabled
				this._resizeGridWidth(true);
				element.focus();
				if (this._timeoutResizeGrid) {
					clearTimeout(this._timeoutResizeGrid);
					this._timeoutResizeGrid = null;
				}
				this._timeoutResizeGrid = setTimeout(function () {
					me._resizeGridWidth(false);
				}, 100);
			} else {
				element.focus();
			}
		},
		_deactivate: function () {
			// should remove activated classes from the element
			if (this.grid.hasFixedColumns()) {
				if (this.options.mode === "row") {
					this.grid.fixedBodyContainer()
						.find("tbody")
						.find("tr.ui-iggrid-activerow")
						.removeClass(this.css.activeRow);
				} else {
					this.grid.fixedBodyContainer()
						.find("tbody")
						.find("td.ui-iggrid-activecell")
						.removeClass(this.css.activeCell);
				}
			}
			if (this.options.mode === "row") {
				this.grid.element
					.find("tbody")
					.find("tr.ui-iggrid-activerow")
					.removeClass(this.css.activeRow);
			} else {
				this.grid.element
					.find("tbody")
					.find("td.ui-iggrid-activecell")
					.removeClass(this.css.activeCell);
			}
		},
		_clearSelection: function () {
			// should select all selected elements and remove the selected classes
			this.grid.fixedBodyContainer()
				.find("tbody").find("td.ui-iggrid-selectedcell")
				.removeClass(this.css.selectedCell).attr("aria-selected", false);
			this.grid.fixedBodyContainer()
				.find("tbody").find("tr[aria-selected]")
				.attr("aria-selected", false);
			this.grid.element.children("tbody")
				.find("td.ui-iggrid-selectedcell")
				.removeClass(this.css.selectedCell).attr("aria-selected", false);
			this.grid.element.children("tbody")
				.find("tr[aria-selected]")
				.attr("aria-selected", false);
		},
		/* event management */
		_createHandlers: function () {
			/* grid event handlers */
			this._dataRenderingHandler = $.proxy(this._dataRendering, this);
			this._virtualRecordsRenderingHandler = $.proxy(this._virtualRecordsRendering, this);
			this._virtualRecordsRendererHandler = $.proxy(this._virtualRecordsRender, this);
			this._columnsCollectionModifiedHandler = $.proxy(this._columnsCollectionModified, this);
			/* mouse event handlers */
			this._mouseDownHandler = $.proxy(this._mouseDown, this);
			this._mouseMoveHandler = $.proxy(this._mouseMove, this);
			this._mouseUpHandler = $.proxy(this._mouseUp, this);
			this._pointerDownHandler = $.proxy(this._pointerDown, this);
			this._pointerMoveHandler = $.proxy(this._pointerMove, this);
			this._pointerUpHandler = $.proxy(this._pointerUp, this);
			this._selectStartHandler = $.proxy(this._selectStart, this);
			this._selectStartContainerHandler = $.proxy(this._selectStartContainer, this);
			this._releaseMouseHandler = $.proxy(this._releaseMouse, this);
			/* touch event handlers */
			this._touchStartHandler = $.proxy(this._touchStart, this);
			this._touchEndHandler = $.proxy(this._touchEnd, this);
			this._touchMoveHandler = $.proxy(this._touchMove, this);
			/* misc event handlers */
			this._keyDownHandler = $.proxy(this._keyDown, this);
			this._containerFocusHandler = $.proxy(this._containerFocus, this);
			this._focusInHandler = $.proxy(this._tbodyFocusIn, this);
			this._focusOutHandler = $.proxy(this._tbodyFocusOut, this);
			this._preventDefaultHandler = $.proxy(this._preventDefault, this);
		},
		_unregisterEvents: function () {
			var gOpts = this.grid.options,
				tbody = this.grid.element.find("tbody")
				.add(this.grid.fixedBodyContainer().find("tbody")),
				hasVirtualization = gOpts.virtualization ||
				gOpts.rowVirtualization ||
				gOpts.columnVirtualization,
				scrollCont = hasVirtualization ?
				this.grid._vdisplaycontainer() : this.grid.scrollContainer();
			if (this._tbodyBinding) {
				tbody.unbind(this._tbodyBinding);
			}
			$(document).unbind("mouseup." + this.grid.id() + "_selection");
			scrollCont.unbind("scroll", this._releaseMouseHandler);
			/* focusing tbody elements makes possible to bind to the tbodies reducing
			the possibility for event handling errors
			this.grid.container().unbind("keydown", this._keyDownHandler); */
			this.grid.container().unbind("selectstart", this._selectStartContainerHandler);
			/* this.grid.element.unbind("iggriduidirty", this._uiDirtyHandler); */
			this.grid.element.removeClass("ui-iggrid-canceltextselection");
			if (this.options.mode === "row") {
				this.grid.element.find("tbody td").unbind("mousedown", this._preventDefaultHandler);
			}
		},
		_registerEvents: function () {
			var gOpts = this.grid.options,
				tbody = this.grid.element.find("tbody")
				.add(this.grid.fixedBodyContainer().find("tbody")),
				hasVirtualization = gOpts.virtualization ||
				gOpts.rowVirtualization ||
				gOpts.columnVirtualization,
				scrollCont = hasVirtualization ?
				this.grid._vdisplaycontainer() : this.grid.scrollContainer();
			this._tbodyBinding = this._tbodyBinding || this._generateTbodyBinding();
			tbody.unbind(this._tbodyBinding).bind(this._tbodyBinding);
			/* ensure mouse release is detected outside the grid */
			$(document).bind("mouseup." + this.grid.id() + "_selection", this._releaseMouseHandler);
			scrollCont.bind("scroll", this._releaseMouseHandler);
			/* focusing tbody elements makes possible to bind to the tbodies reducing
			the possibility for event handling errors
			this.grid.container().bind("keydown", this._keyDownHandler); */
			this.grid.container().bind("selectstart", this._selectStartContainerHandler);
			if (this.options.multipleSelection === true) {
				this.grid.element.addClass("ui-iggrid-canceltextselection");
			}
			/* prevent default behavior for tds, because they have tabindexes,
			but should not be focused in row selection */
			if (this.options.mode === "row") {
				this.grid.element.find("tbody td").bind("mousedown", this._preventDefaultHandler);
			}
		},
		_generateTbodyBinding: function () {
			var binding = {};
			binding.selectstart = this._selectStartHandler;
			binding.focusin = this._focusInHandler;
			binding.focusout = this._focusOutHandler;
			binding.keydown = this._keyDownHandler;
			if ($.ig.util.isTouch) {
				// ios/android touch or touch monitor in chrome/ff
				binding.touchstart = this._touchStartHandler;
				binding.touchend = this._touchEndHandler;
				binding.touchmove = this._touchMoveHandler;
			}
			if (window.navigator.msPointerEnabled || window.navigator.pointerEnabled) {
				// ie10/ie11 any device
				binding.MSPointerDown = this._pointerDownHandler;
				binding.MSPointerMove = this._pointerMoveHandler;
				binding.MSPointerUp = this._pointerUpHandler;
				binding.pointerdown = this._pointerDownHandler;
				binding.pointermove = this._pointerMoveHandler;
				binding.pointerup = this._pointerUpHandler;
			} else {
				binding.mousedown = this._mouseDownHandler;
				binding.mousemove = this._mouseMoveHandler;
				binding.mouseup = this._mouseUpHandler;
			}
			return binding;
		},
		/* every grid feature widget should implement this */
		_injectGrid: function (gridInstance, isRebind) {
			var i;
			this.grid = gridInstance;
			/* selection is always inheritable ! */
			for (i = 0; i < this.grid.options.features.length; i++) {
				if (this.grid.options.features[ i ].name === "Selection") {
					this.grid.options.features[ i ].inherit = true;
				}
				/* when checkboxes are enabled for RowSelectors we need to use row selection */
				if (this.grid.options.features[ i ].name === "RowSelectors") {
					if (this.grid.options.features[ i ].enableCheckBoxes === true &&
						this.grid.options.features[ i ].checkBoxMode !== "triState") {
						this.options.mode = "row";
					}
				}
			}
			this._initSelectionCollection(isRebind);
			if (!isRebind) {
				this._createHandlers();
			}
			/* we need to bind to data rendering as we we need the
			TBODY to be created before binding to the mouse/touch events */
			this.grid.element
				.unbind("iggriddatarendering", this._dataRenderingHandler)
				.bind("iggriddatarendering", this._dataRenderingHandler);
			this.grid.element
				.unbind("iggridvirtualrendering", this._virtualRecordsRenderingHandler)
				.bind("iggridvirtualrendering", this._virtualRecordsRenderingHandler);
			this.grid.element
				.unbind("igtreegridvirtualrendering", this._virtualRecordsRenderingHandler)
				.bind("igtreegridvirtualrendering", this._virtualRecordsRenderingHandler);
			this.grid.element
				.unbind("iggridvirtualrecordsrender", this._virtualRecordsRendererHandler)
				.bind("iggridvirtualrecordsrender", this._virtualRecordsRendererHandler);
			this.grid.element
				.unbind("igtreegridvirtualrecordsrender", this._virtualRecordsRendererHandler)
				.bind("igtreegridvirtualrecordsrender", this._virtualRecordsRendererHandler);
			this.grid.element
				.unbind("iggridcolumnscollectionmodified", this._columnsCollectionModifiedHandler)
				.bind("iggridcolumnscollectionmodified", this._columnsCollectionModifiedHandler);
			/* persistence
			we'll add the subscribers regardless of the option to cover
			non-data-view-changing state persistence like when showing a column */
			if (/*this.options.persist && */!isRebind) {
				this.grid._cellStyleSubscribers.push(this.options.mode === "row" ?
					$.proxy(this._applyRowStyle, this) : $.proxy(this._applyCellStyle, this));
			}
		}
	});
	$.extend($.ui.igGridSelection, { version: "16.1.20161.2145" });

	$.ig.SelectionCollection = $.ig.SelectionCollection || Class.extend({
		settings: {
			multipleSelection: false,
			subscribers: {},
			owner: null
		},
		/* public */
		init: function (options) {
			if (options) {
				this.settings = $.extend(true, {}, $.ig.SelectionCollection.prototype.settings, options);
			}
			this.selection = {};
			return this;
		},
		addSubscriber: function (subscriber, owner) {
			var suid = $.ig.uid();
			if (!this.settings.subscribers[ owner ]) {
				this.settings.subscribers[ owner ] = {};
			}
			this.settings.subscribers[ owner ][ suid ] = subscriber;
			return suid;
		},
		removeSubscriber: function (subscriberId, owner) {
			delete this.settings.subscribers[ owner ][ subscriberId ];
		},
		changeOwner: function (newOwner) {
			this.cleanAll();
			this.settings.owner = newOwner;
		},
		/* M.P.: 183193 - The igniteui.d.ts file is not compiling */
		/* jshint unused:false */
		isSelected: function (identifier, forOwner) {
			return false;
		},
		isActive: function (identifier, forOwner) {
			return false;
		},
		elementFromIdentifier: function (identifier) {
			return $();
		},
		/* jshint unused:true */
		toggle: function (element) {
			// toggles selection for the active element
			// optionally add the element if available to speed up processing
			if (this.activeElement === null) {
				return;
			}
			this.select(this.activeElement, true, {
				element: element || this.elementFromIdentifier(this.activeElement)
			});
		},
		activate: function (identifier, element, suppress) {
			var info = { element: element, id: identifier };
			/* a new element is always activated */
			if (!this.isActive(identifier)) {
				// call for widgets firing activation changing
				if (this._requestTrigger("ActivationChanging", suppress, info)) {
					// removing activated styles is faster if the active element is selected by class
					// therefore we don't really need to pass it around
					this._notify("deactivate");
					this.activeElement = identifier;
					this._notify("activate", element);
					this._requestTrigger("ActivationChanged", suppress, info);
				}
			}
		},
		deactivate: function (suppress) {
			if (this._requestTrigger("ActivationChanging", suppress, null)) {
				this._notify("deactivate");
				this.activeElement = null;
				this._requestTrigger("ActivationChanged", suppress, null);
			}
		},
		select: function (identifier, add, info, suppress) {
			// make sure to use what's sent optimally
			info = info || {};
			info.element = info.element || this.elementFromIdentifier(identifier);
			info.id = identifier;
			/* check if selection needs to change */
			if (this.isSelected(identifier)) {
				if (add) {
					// we'll toggle the selection in this case
					if (this._requestTrigger("SelectionChanging", suppress, info)) {
						this._markUnselected(identifier);
						this._notify("deselect", info);
						this._requestTrigger("SelectionChanged", suppress, info);
					}
				} else {
					if (this.onlyOneSelected()) {
						// the selection will not be changed and we shouldn't do anything
						return;
					}
					/* clear all but this */
					if (this._requestTrigger("SelectionChanging", suppress, info)) {
						this.clearSelection();
						this._markSelected(identifier, info);
						this._notify("select", info);
						this._requestTrigger("SelectionChanged", suppress, info);
					}
				}
			} else {
				if (this._requestTrigger("SelectionChanging", suppress, info)) {
					if (!add || !this.settings.multipleSelection) {
						// we need to clear the rest of the selection
						this.clearSelection();
					}
					/* we'll just add the new selected element to the collection */
					this._markSelected(identifier, info);
					this._notify("select", info);
					this._requestTrigger("SelectionChanged", suppress, info);
				}
			}
			/* finally change the range select starting point */
			this.rangeSelectStart = identifier;
		},
		rangeSelect: function (range, add, prevRange, info, suppress) {
			var i;
			/* first we need to see if the selection change will be cancelled */
			if (!this._requestTrigger("SelectionChanging", suppress, info)) {
				return;
			}
			/* prevRange holds the previous range for this drag or shift
			if it is specified we only want to clear that specific range
			because there is multi-range selection enabled and other selected cells
			should be preserved. Additionally we need to take care of "locked" for deselection
			cells to ensure intersecting selection always works as "or" */
			if (!prevRange || !this._rangeUnlock) {
				if (!add) {
					this.clearSelection();
				}
			} else {
				this._rangeUnlock(prevRange);
			}
			for (i = 0; i < range.length; i++) {
				if (!this.isSelected(range[ i ].id)) {
					this._markSelected(range[ i ].id, range[ i ]);
					this._notify("select", range[ i ]);
				}
			}
			this.activate(info.active, info.element || this.elementFromIdentifier(info.active));
			this._requestTrigger("SelectionChanged", suppress, info);
		},
		rangeDeselect: function (range, info, suppress) {
			// deselects an array of identifiers
			var i;
			if (!this._requestTrigger("SelectionChanging", suppress, info)) {
				return;
			}
			for (i = 0; i < range.length; i++) {
				this._markUnselected(range[ i ].id);
				this._notify("deselect", range[ i ]);
			}
			this._requestTrigger("SelectionChanged", suppress, info);
		},
		deselect: function (identifier, info, suppress) {
			info = info || {};
			info.element = info.element || this.elementFromIdentifier(identifier);
			info.id = identifier;
			this.select(identifier, true, info, suppress);
		},
		deselectAll: function (suppress) {
			// deselects all elements
			if (!this._requestTrigger("SelectionChanging", suppress, {})) {
				return;
			}
			this.clearSelection();
			this._requestTrigger("SelectionChanged", suppress, {});
		},
		clearSelection: function (forOwner) {
			if (!forOwner || forOwner === this.settings.owner) {
				this.selection = {};
				this._notify("clearSelection");
			}
		},
		cleanAll: function (forOwner) {
			if (!forOwner || forOwner === this.settings.owner) {
				this.deactivate(true);
				this.clearSelection();
			}
		},
		onlyOneSelected: function () {
			return false;
		},
		selectedCells: function () {
			return [];
		},
		selectedRows: function () {
			return [];
		},
		selectionLength: function () {
			return Object.keys(this.selection).length;
		},
		/* private */
		_requestTrigger: function (type, suppress, args) {
			var subs = this.settings.subscribers[ this.settings.owner.id() ],
				func = "_on" + this._selectionMode() + type, key, result = true;
			if (suppress) {
				return true;
			}
			for (key in subs) {
				if (subs.hasOwnProperty(key) && typeof subs[ key ][ func ] === "function") {
					result = result && subs[ key ][ func ](args);
				}
			}
			return result;
		},
		_notify: function (message, info) {
			var subs = this.settings.subscribers[ this.settings.owner.id() ],
				func = "_" + message, key;
			for (key in subs) {
				if (subs.hasOwnProperty(key) && typeof subs[ key ][ func ] === "function") {
					subs[ key ][ func ](info);
				}
			}
		}
	});

	$.ig.SelectedRowsCollection = $.ig.SelectedRowsCollection || $.ig.SelectionCollection.extend({
		/* override */
		isSelected: function (identifier, forOwner) {
			if (forOwner && this.settings.owner !== forOwner) {
				return false;
			}
			return this.selection[ identifier ] !== undefined;
		},
		isActive: function (identifier, forOwner) {
			if (forOwner && this.settings.owner !== forOwner) {
				return false;
			}
			return this.activeElement === identifier;
		},
		selectedRows: function () {
			var rows = this._rowscache || [], rowKey;
			for (rowKey in this.selection) {
				if (this.selection.hasOwnProperty(rowKey)) {
					rows.push(this._selectedRowObject(this.settings.owner._fixPKValue(rowKey)));
				}
			}
			this.invalid = false;
			return rows;
		},
		activeRow: function () {
			var row;
			if (this.activeElement !== null && this.activeElement !== undefined) {
				row = this.elementFromIdentifier(this.activeElement);
				return {
					id: this.activeElement,
					index: this.elementPosition(this.activeElement, row),
					element: row
				};
			}
			return null;
		},
		elementFromIdentifier: function (identifier) {
			return this.settings.owner.element.data("igGridSelection")._getRowsByIdentifier(identifier);
		},
		elementPosition: function (identifier, element) {
			var ownSelection = this.settings.owner.element.data("igGridSelection");
			if (element && element.length > 0) {
				return ownSelection._getIndexForRow(element);
			}
			return ownSelection._getRecordByIdentifier(identifier).index;
		},
		onlyOneSelected: function () {
			return Object.keys(this.selection).length === 1;
		},
		/* private */
		_markSelected: function (identifier, info) {
			this.selection[ identifier ] = info || true;
		},
		_markUnselected: function (identifier) {
			delete this.selection[ identifier ];
		},
		_selectedRowObject: function (identifier) {
			// assume the identifier is always valid
			var rowInfo = this.selection[ identifier ];
			if ($.type(rowInfo) !== "object") {
				rowInfo = {};
				this.selection[ identifier ] = rowInfo;
			}
			if (this.invalid) {
				rowInfo.element = this.elementFromIdentifier(identifier);
				rowInfo.index = this.elementPosition(identifier, rowInfo.element);
			} else {
				rowInfo.element = rowInfo.element || this.elementFromIdentifier(identifier);
				rowInfo.index = rowInfo.index !== undefined && rowInfo.index !== null ?
					rowInfo.index : this.elementPosition(identifier, rowInfo.element);
			}
			return $.extend(true, {}, rowInfo, { id: identifier });
		},
		_selectionMode: function () {
			return "Row";
		}
	});

	$.ig.SelectedCellsCollection = $.ig.SelectedCellsCollection || $.ig.SelectionCollection.extend({
		/* override */
		isSelected: function (identifier, forOwner) {
			if (forOwner && this.settings.owner !== forOwner) {
				return false;
			}
			return this.selection[ identifier.id ] !== undefined &&
				this.selection[ identifier.id ][ identifier.columnKey ] !== undefined;
		},
		atLeastOneSelected: function (rowId, forOwner) {
			if (forOwner && this.settings.owner !== forOwner) {
				return false;
			}
			return Object.keys(this.selection[ rowId ]).length > 0;
		},
		isActive: function (identifier, forOwner) {
			if (forOwner && this.settings.owner !== forOwner) {
				return false;
			}
			return this.activeElement &&
				this.activeElement.id === identifier.id &&
				this.activeElement.columnKey === identifier.columnKey;
		},
		selectedCells: function () {
			var cells = [], sid, rowKey, columnKey;
			for (rowKey in this.selection) {
				if (this.selection.hasOwnProperty(rowKey)) {
					for (columnKey in this.selection[ rowKey ]) {
						if (this.selection[ rowKey ].hasOwnProperty(columnKey)) {
							sid = { id: this.settings.owner._fixPKValue(rowKey), columnKey: columnKey };
							cells.push(this._selectedCellObject(sid));
						}
					}
				}
			}
			this.invalid = false;
			return cells;
		},
		activeCell: function () {
			var cell, pos;
			if (this.activeElement) {
				cell = this.elementFromIdentifier(this.activeElement);
				pos = this.elementPosition(this.activeElement, cell);
				return {
					row: cell.parent(),
					id: this.activeElement.id,
					columnKey: this.activeElement.columnKey,
					rowIndex: pos.x,
					index: pos.y,
					element: cell
				};
			}
			return null;
		},
		elementFromIdentifier: function (identifier) {
			return this.settings.owner.element.data("igGridSelection")._getCellByIdentifier(identifier);
		},
		elementPosition: function (identifier, element) {
			var ownSelection = this.settings.owner.element.data("igGridSelection");
			if (element && element.length > 0) {
				return {
					x: ownSelection._getIndexForRow(element.parent()),
					y: element.index()
				};
			}
			return {
				x: ownSelection._getRecordByIdentifier(identifier.id).index,
				y: ownSelection._getColIndexByKey(identifier.columnKey)
			};
		},
		onlyOneSelected: function () {
			var rKeys = Object.keys(this.selection);
			if (rKeys.length === 1) {
				return Object.keys(this.selection[ rKeys[ 0 ] ]).length === 1;
			}
			return false;
		},
		/* private */
		_lockSelection: function () {
			// for each currently selected cell,
			// apply a flag to prevent its deselection
			// on range change
			var rowId, cellId;
			for (rowId in this.selection) {
				if (this.selection.hasOwnProperty(rowId)) {
					for (cellId in this.selection[ rowId ]) {
						if (this.selection[ rowId ].hasOwnProperty(cellId)) {
							if ($.type(this.selection[ rowId ][ cellId ] === "object")) {
								this.selection[ rowId ][ cellId ].locked = true;
							} else {
								this.selection[ rowId ][ cellId ] = { locked: true };
							}
						}
					}
				}
			}
		},
		_markSelected: function (identifier, info) {
			this.selection[ identifier.id ] = this.selection[ identifier.id ] || {};
			this.selection[ identifier.id ][ identifier.columnKey ] = info || true;
		},
		_markUnselected: function (identifier) {
			if (!this.selection[ identifier.id ]) {
				return;
			}
			delete this.selection[ identifier.id ][ identifier.columnKey ];
		},
		_unlockSelected: function (identifier) {
			var c;
			if (!this.selection[ identifier.id ]) {
				return false;
			}
			c = this.selection[ identifier.id ][ identifier.columnKey ];
			if ($.type(c) === "object") {
				if (!c.locked) {
					delete this.selection[ identifier.id ][ identifier.columnKey ];
					return true;
				}
				return false;
			}
			delete this.selection[ identifier.id ][ identifier.columnKey ];
			return true;
		},
		_rangeUnlock: function (range) {
			// deselects an array of identifiers
			var i;
			for (i = 0; i < range.length; i++) {
				if (this._unlockSelected(range[ i ].id)) {
					this._notify("deselect", range[ i ]);
				}
			}
		},
		_selectedCellObject: function (identifier) {
			// assume the identifier is always valid
			var cellInfo = this.selection[ identifier.id ][ identifier.columnKey ], pos;
			if ($.type(cellInfo) !== "object") {
				cellInfo = {};
				this.selection[ identifier.id ][ identifier.columnKey ] = cellInfo;
			}
			if (this.invalid) {
				// get info anew
				cellInfo.element = this.elementFromIdentifier(identifier);
				cellInfo.row = cellInfo.element.parent();
				pos = this.elementPosition(identifier, cellInfo.element);
				cellInfo.rowIndex = pos.x;
				cellInfo.index = pos.y;
			} else {
				cellInfo.element = cellInfo.element || this.elementFromIdentifier(identifier);
				cellInfo.row = cellInfo.row || cellInfo.element.parent();
				if (!(cellInfo.rowIndex !== undefined && cellInfo.rowIndex !== null &&
					cellInfo.index !== undefined && cellInfo.index !== null)) {
					pos = this.elementPosition(identifier, cellInfo.element);
					cellInfo.rowIndex = pos.x;
					cellInfo.index = pos.y;
				}
			}
			return $.extend(true, {}, cellInfo, { id: identifier.id, columnKey: identifier.columnKey });
		},
		_selectionMode: function () {
			return "Cell";
		}
	});
}(jQuery));
