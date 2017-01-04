
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
 *	infragistics.ui.grid.updating.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igTreeGridUpdating widget. The widget is pluggable to the element where the treegrid is instantiated and the actual igTreeGrid object doesn"t know about this
		The treegrid updating widget just attaches its functionality to the treegrid
		igTreeGridUpdating is extending the igGridUpdating
	*/
	$.widget("ui.igTreeGridUpdating", $.ui.igGridUpdating, {
		options: {
			/* type="bool" Specifies whether to enable or disable adding children to rows. */
			enableAddChild: true,
			/* type="string" Specifies the add child tooltip text. */
			addChildTooltip: null,
			/* type="string" Specifies the label of the add child button in touch environment. */
			addChildButtonLabel: null
		},
		css: {
			/* Classes applied to the container of Done and Cancel editing buttons. Default value is "ui-iggrid-addrowicon ui-icon ui-icon-circle-plus" */
			addChildIcon: "ui-iggrid-addrowicon ui-icon ui-icon-circle-plus"
		},
		_startAddChildFor: function (rowId, raiseEvents) {
			var self = this, row = this.grid.rowById(rowId);
			if (row.attr("aria-expanded") === "false") {
				this.grid.expandRow(row, function () {
					/* Refresh the instance of the row in case Paging is enabled. */
					row = self.grid.rowById(rowId);
					/* P.Zh. 23 Feb 2016 Fix for bug #214542: Some Updating events do not fire */
					self._addEditableChildRow(row, raiseEvents);
				});
				return true;
			}
			return this._addEditableChildRow(row, raiseEvents);
		},
		_addEditableChildRow: function (row, raiseEvents) {
			var newRow = $(this.grid._renderRecord({}, null)),
				fixedRow, attr, fixDir, cellToFocus, cfInst,
				hasFixedColumns = this.grid.hasFixedColumns(),
				rowId = this.grid._normalizedKey(row.attr("data-id"));
			attr = {
				"data-new-row": true,
				"data-child-row": true
			};
			/* P.Zh. 23 Feb 2016 Fix for bug #214544: rowID event argument is NaN when adding a child row*/
			newRow.removeAttr("data-id");
			newRow = newRow.attr(attr).addClass(this.css.addRow).data("parent-id", rowId);
			if (hasFixedColumns) {
				fixedRow = $(this.grid._renderRecord({}, null, true)).removeAttr("data-id");
				fixedRow.attr(attr).addClass(this.css.addRow).data("parent-id", rowId);
				fixDir = this.grid.fixingDirection();
			}
			row.after(newRow);
			if (hasFixedColumns) {
				this.grid.rowById(rowId, true).after(fixedRow);
				if (fixDir === "left" &&
					fixedRow.find("td").not("[data-skip=\"true\"]").length > 0) {
					cellToFocus = fixedRow.find("td").not("[data-skip=\"true\"]").eq(0);
				}
				/* M.H. 25 Feb 2016 Fix for bug 214765: If only the expansion column is fixed _renderRecord method renders a fixed row without height. */
				cfInst = this.grid.element.data("igTreeGridColumnFixing");
				if (cfInst && cfInst.options.syncRowHeights) {
					cfInst.syncRowsHeights(fixedRow, newRow);
				}
			}
			cellToFocus = cellToFocus || newRow.find("td").not("[data-skip=\"true\"]").eq(0);
			this._startEditForRow(null, !raiseEvents, null, null, cellToFocus);
			return true;
		},
		_renderAddChildButton: function () {
			var addChildButton,
				addChildSelector = "#" + this.grid.id() + "_updating_add_child_hover",
				addChildContainer,
				ubodySelector = "#" + this.grid.id() + ">tbody",
				fbodySelector = "#" + this.grid.id() + "_fixed>tbody",
				pe = window.navigator.msPointerEnabled || window.navigator.pointerEnabled,
				addChildTitle = this.options.addChildTooltip ?
					this.options.addChildTooltip : $.ig.GridUpdating.locale.addChildTooltip;
			if ($(addChildSelector).length) {
				return;
			}
			/* button for adding a child to a row */
			addChildButton = $("<span></span>")
				.css("position", "absolute")
				.addClass(this.css.deleteButton);
			addChildButton.attr({
				id: addChildSelector.substring(1),
				unselectable: "on",
				title: addChildTitle
			});
			addChildButton.hide();
			$("<span></span>")
				/* .css("display", "inline-block") */
				.addClass(this.css.addChildIcon)
				.attr("unselectable", "on")
				.appendTo(addChildButton);
			addChildContainer = this._addElementToScrollContainer(addChildButton);
			this.grid.container().on({
				"mouseenter.addchildbutton": this._buttonHandlers.mouseEnter,
				"mouseleave.addchildbutton": this._buttonHandlers.mouseLeave,
				"click.addchildbutton": this._buttonHandlers.addChildClick,
				/* P.Zh. 26 Feb 2016 Fix for bug #214855: When adding a child row in IE an additional space appears below it */
				/* "pointerup.addchildbutton": this._buttonHandlers.addChildClick, */
				"keyup.addchildbutton": this._buttonHandlers.addChildClick
			}, addChildSelector);
			/* bind tbody events */
			if (pe) {
				/* in IE filter the pointer events and accept hoever only
				when the pointerType is mouse */
				this.grid.container().on({
					"pointerenter.deletebutton": this._handlers.pointerEnter,
					"mspointerenter.deletebutton": this._handlers.pointerEnter
				}, ubodySelector + ">tr," + fbodySelector + ">tr");
			} else {
				this.grid.container().on({
					"mouseenter.addchildbutton": this._handlers.mouseEnter
				}, ubodySelector + ">tr," + fbodySelector + ">tr");
			}
			if (this._renderTouchUI) {
				this.grid.container().on({
					"touchstart.addchildbutton": this._handlers.touchStart,
					"touchend.addchildbutton": this._handlers.touchEnd
				}, ubodySelector + ">tr," + fbodySelector + ">tr");
			}
			if ( addChildContainer[ 0 ] === this.grid.container()[ 0 ] ) {
				this.grid.container().bind("mouseleave.addchildbutton", this._handlers.mouseLeave);
			} else {
				this.grid.container().on({
					"mouseleave.addchildbutton": this._handlers.mouseLeave
				}, "#" + addChildContainer.attr("id"));
			}
		},
		_removeAddChildButton: function () {
			var button = $("#" + this.grid.id() + "_updating_add_child_hover");
			if (button.length === 1) {
				button.remove();
				this.grid.container().off(".addchildbutton");
				this.grid.container().unbind(".addchildbutton");
			}
		},
		_toggleAddRow: function () {
			var isAddChild = this._isAddChild(), newRow;
			if (isAddChild) {
				newRow = this.grid.element.find("tr[data-child-row]");
				if (this.grid.hasFixedColumns()) {
					newRow = newRow.add(this.grid.fixedBodyContainer().find("tr[data-child-row]"));
				}
				if (newRow.length !== 0) {
					newRow.remove();
				}
			} else {
				this._super();
			}
		},
		_addChildButtonClick: function (evt) {
			var rowId = $(evt.target).closest(".ui-iggrid-deletebutton").data("button-for");
			if (this.options.editMode === "dialog") {
				this._startEditDialog(evt, false, null, true, null, null, rowId);
			} else {
				/* P.Zh. 23 Feb 2016 Fix for bug #214542: Some Updating events do not fire */
				if (this._startAddChildFor(rowId, true)) {
					this.hideAddChildButton();
				}
			}
		},
		_touchAddChildClick: function (evt) {
			var rowId = this._editingForRowId,
				target = $(evt.target).closest(".ui-iggrid-button");
			if (!target.length || target.hasClass(this.css.buttonDisabled)) {
				return;
			}
			if (this.isEditing()) {
				this._endEdit(evt, false, false);
				this._startAddChildFor(rowId);
			}
		},
		_touchAddChildKeyUp: function (evt) {
			var rowId = this._editingForRowId,
				target = $(evt.target).closest(".ui-iggrid-button");
			if (!target.length || target.hasClass(this.css.buttonDisabled) ||
				evt.keyCode !== $.ui.keyCode.ENTER) {
				return;
			}
			if (this.isEditing()) {
				this._endEdit(evt, false, false);
				this._startAddChildFor(rowId);
			}
		},
		/* touch support */
		_touchEnd: function (evt) {
			/* P.Zh. 1 Mar 2016 Fix for bug #214773: [Mobile] Swipe on the right does not work for showing "add child" button */
			var changedTouchX = evt.originalEvent.changedTouches && evt.originalEvent.changedTouches[ 0 ] ?
				evt.originalEvent.changedTouches[ 0 ].pageX : evt.originalEvent.clientX;
			this._storedScrollLeft -= this.grid.scrollContainer().scrollLeft() || 0;
			this._storedScrollWidth -= this.grid.scrollContainer().width() || 0;
			if (this.options.enableAddChild &&
					Math.abs(changedTouchX - this._firstTouchX) > parseInt(this.options.swipeDistance, 10) &&
					this._firstTouchRow &&
					this._storedScrollLeft === 0 &&
					this._storedScrollWidth === 0) {
				this.showAddChildButtonFor(this._firstTouchRow);
				if (this.options.enableDeleteRow) {
					this.showDeleteButtonFor(this._firstTouchRow);
				}
			}
			delete this._firstTouchX;
			delete this._firstTouchRow;
			delete this._storedScrollLeft;
			delete this._storedScrollWidth;
			delete this._firstTouchRow;
		},
		_pointerUp: function (evt) {
			/* P.Zh. 1 Mar 2016 Fix for bug #214773: [Mobile] Swipe on the right does not work
			for showing "add child" button on MS Surface */
			var oe = evt.originalEvent, changedTouchX;
			if (oe.pointerType !== "touch") {
				return;
			}
			changedTouchX = oe.pageX;
			this._storedScrollLeft -= this.grid.scrollContainer().scrollLeft() || 0;
			this._storedScrollWidth -= this.grid.scrollContainer().width() || 0;
			if (this.options.enableDeleteRow &&
					Math.abs(changedTouchX - this._firstTouchX) > parseInt(this.options.swipeDistance, 10) &&
					this._firstTouchRow &&
					this._storedScrollLeft === 0 &&
					this._storedScrollWidth === 0) {
				this.showAddChildButtonFor(this._firstTouchRow);
				if (this.options.enableDeleteRow) {
					this.showDeleteButtonFor(this._firstTouchRow);
				}
			}
			delete this._firstTouchX;
			delete this._firstTouchRow;
			delete this._storedScrollLeft;
			delete this._storedScrollWidth;
			delete this._firstTouchRow;
		},
		_rowMouseEnter: function (evt) {
			this._super(evt);
			var row = $(evt.target).closest("tr");
			if (!this.isEditing() && this._isEditableRow(row)) {
				/* P.Zh. 22 Feb 2016 Fix for bug #214483: A subchild of a just added child is added
				at the bottom of the grid when autoCommit is false */
				if (!row.hasClass("ui-iggrid-modifiedrecord") &&
					this.grid.dataSource
						.findRecordByKey(this.grid._normalizedKey(row.attr("data-id")))) {
					this.showAddChildButtonFor(row);
				} else {
					this.hideAddChildButton();
				}
			} else {
				this.hideAddChildButton();
			}
		},
		_containerMouseLeave: function () {
			this._super();
			this.hideAddChildButton();
		},
		_renderDoneCancelButtons: function () {
			var buttonContainer = this._super(),
				addChildButton,
				addChildSelector = "#" + this.grid.id() + "_updating_add_child_touch";
			if (this._renderTouchUI && this.options.enableAddChild) {
				addChildButton = $("<span />")
					.attr("id", this.grid.id() + "_updating_add_child_touch")
					.addClass(this.css.button)
					.addClass(this.css.doneButton)
					.attr({
						unselectable: "on",
						tabIndex: this._getNextTabIndex() + 1,
						title: this.options.addChildTooltip !== null ?
							this.options.addChildTooltip : $.ig.GridUpdating.locale.addChildTooltip
					});
				addChildButton.prependTo(buttonContainer);
				/* P.Zh. 1 Mar 2016 Fix for bug #214751: [Mobile] When adding a child row "Done" and
				"Cancel" buttons and the small icons of the buttons are not properly positioned */
				addChildButton.css({
					"float": "left",
					"position": "relative"
				});
				$("<span />")
					.css({
						display: "inline-block",
						left: 0
					})
					.addClass(this.css.addRowIcon)
					.attr("unselectable", "on")
					.appendTo(addChildButton);
				$("<span />")
					.css("display", "inline-block")
					.attr("unselectable", "on")
					.html(this.options.addChildButtonLabel !== null ?
						this.options.addChildButtonLabel
						: $.ig.GridUpdating.locale.addChildTooltip)
					.appendTo(addChildButton);
				this.grid.container().on({
					"mouseenter.donecancel": this._buttonHandlers.mouseEnter,
					"mouseleave.donecancel": this._buttonHandlers.mouseLeave,
					"focus.donecancel": this._buttonHandlers.focus,
					"blur.donecancel": this._buttonHandlers.blur
				}, addChildSelector);
				this.grid.container().on({
					"click.donecancel": this._buttonHandlers._touchAddChildClick,
					"keyup.donecancel": this._buttonHandlers._touchAddChildKeyUp
				}, addChildSelector);
			}
			return buttonContainer;
		},
		_updateTouchButtons: function (isAdding) {
			var addChildButton, isInDS;
			this._super(isAdding);
			if (this.options.enableAddChild) {
				isInDS = this.grid.findRecordByKey(this._editingForRowId) !== null;
				addChildButton = $("#" + this.grid.id() + "_updating_add_child_touch");
				if (isAdding || this._isAddChild() || !isInDS) {
					addChildButton.addClass(this.css.buttonDisabled);
					/* P.Zh. 1 Mar 2016 Fix for bug #214752: [Mobile] After adding a child row,
					the buttons "Add a child row" and "Delete row" are with different styles */
					if (addChildButton.hasClass("ui-iggrid-buttonhover")) {
						addChildButton.removeClass(this.css.buttonHover);
					}
				} else {
					addChildButton.removeClass(this.css.buttonDisabled);
				}
			}
		},
		_isAddChild: function () {
			return this.grid.element.find("tr[data-child-row]").length !== 0;
		},
		_removeChildrenFromUI: function ($row, dataLevel) {
			/* $row - next(to the deleted row) TR in DOM
			dataLevel of the row that is deleted
			remove all children rows that have aria-level greater than dataLevel */
			if (!$row || !$row.length) {
				return;
			}
			/* check whether row has children and remove them */
			var dl, $tmp, rowId, $fRow, $cntnr, fixedCols = this.grid.hasFixedColumns();
			if (isNaN(dataLevel)) {
				return;
			}
			$cntnr = this.grid.container();
			while ($row.length === 1) {
				dl = parseInt($row.attr("aria-level"), 10);
				if (isNaN(dl)) {
					break;
				}
				if (dl <= dataLevel) {
					break;
				}
				$tmp = $row;
				rowId = $row.attr("data-id");
				$row = $row.next("tr");
				if (fixedCols) {
					/* M.H. 9 Mar 2016 Fix for bug 215553: When there"s an initially fixed column
					and a root level row is deleted an error is thrown */
					$fRow = $cntnr.find("tr[data-id=\"" + rowId + "\"]");
					$fRow.remove();
				}
				$tmp.remove();
			}
		},
		_updateParentRowAfterAddChild: function (parentId) {
			var span, rowData, parentRow,
				expCol = this.grid.options.renderExpansionIndicatorColumn,
				fixed = this.grid.hasFixedColumns() && this.grid.fixingDirection() === "left";
			/* P.Zh. 24 Feb 2016 Fix for bug #214700: Adding child row while a column is fixed
			causes an error */
			parentRow = this.grid.rowById(parentId, fixed);
			if (parentRow.find("span.ui-igtreegrid-expansion-indicator").length > 0) {
				return;
			}
			rowData = {
				dataBoundDepth: parseInt(parentRow.attr("aria-level"), 10),
				hasExpandCell: true,
				expand: true
			};
			/* span = expCol ?
				$(this.grid._renderExpandCellContainer(rowData)) :
				$(this.grid._renderExpandCellContainerHelper(rowData)); */
			span = this.grid._renderExpandCellContainerHelper(rowData);
			if (expCol) {
				parentRow.find("td.ui-igtreegrid-non-data-column").attr("data-expand-cell", 1)
					.append(span);
			} else {
				parentRow.find("span[data-expandcell-indicator]")
					.append($(span).find("span.ui-igtreegrid-expansion-indicator"));
			}
			parentRow.attr("aria-expanded", true);
		},
		_generatePrimaryKeyValue: function (e, col) {
			var value, ds = this.grid.dataSource;
			if (col) {
				/* M.H. 13 Nov 2014 Fix for bug #185114: The generation of the primary key value
				when adding new row is not correct for the treegrid */
				value = Math.max(this._recCount || 1,
								ds._totalRecordsCount || 1,
								ds._data.length);
				if (this.element.find("tr[data-id=" + value + "]").length) {
					value++;
				}
				this._recCount = value;
				col.value = value;
			}
		},
		_attachEvents: function () {
			/* M.H. 14 Nov 2014 Fix for bug #185126: When autoCommit:true deleting all childs of a row
			does not remove the expand indicator for the root row of the treegrid */
			this._generatePrimaryKeyValueHandler = $.proxy(this._generatePrimaryKeyValue, this);
			this.element
				.bind(
						"igtreegridupdatinggenerateprimarykeyvalue",
						this._generatePrimaryKeyValueHandler
					);
		},
		_detachEvents: function () {
			if (this._generatePrimaryKeyValueHandler) {
				this.element
					.unbind(
						"igtreegridupdatinggenerateprimarykeyvalue",
						this._generatePrimaryKeyValueHandler
					);
				delete this._generatePrimaryKeyValueHandler;
			}
		},
		/* Public API */
		addChild: function (values, parentId) {
			/* Adds a new child to a specific row. It also creates a transaction and updates the UI.
			paramType="object" Pairs of values in the format { column1Key: value1, column2Key: value2, ... } .
			paramType="object" The ID of the targeted row.
			*/
			var settings = this.options.columnSettings,
				i = settings ? settings.length : 0,
				defVals = {}, key, val, rec = this.grid.findRecordByKey(parentId);
			if (rec === null) {
				throw new Error($.ig.Grid.locale.recordNotFound.replace("{id}", parentId));
			}
			while (i-- > 0) {
				key = settings[ i ].columnKey;
				val = settings[ i ].defaultValue;
				if (val !== undefined && key !== undefined && key !== null) {
					defVals[ key ] = val;
				}
			}
			this._addRow(null, $.extend(defVals, values), defVals, true, parentId);
		},
		startAddChildFor: function (parentId, raiseEvents) {
			/* Starts editing for adding a new child for specific row.
			paramType="object" The ID of the targeted row.
			paramType="object" optional="true" Specifies whether or not updating events should be raised for this operation.
			*/
			var rec = this.grid.findRecordByKey(parentId);
			if (rec === null) {
				throw new Error($.ig.Grid.locale.recordNotFound.replace("{id}", parentId));
			}
			this._startAddChildFor(parentId, raiseEvents);
		},
		showAddChildButtonFor: function (row) {
			/* Shows the "Add Child" button for specific row.
			paramType="object" A jQuery object of the targeted row.
			*/
			var acb = $("#" + this.grid.id() + "_updating_add_child_hover"),
				go = this.grid.options, v,
				hasHeight = go.height !== null && go.height !== undefined,
				sbw = this.grid.hasVerticalScrollbar() === true ? this.grid._scrollbarWidth() : 0,
				left, top, sc, offset = 0, scrContainer, dbw = 0, rowId = this._getRowId(row);
			if (acb.length) {
				if (this.options.enableDeleteRow) {
					dbw += $("#" + this.grid.id() + "_updating_deletehover").outerWidth() + 5; /* indentation from delete button */
				}
				acb.show();
				sc = acb.parent();
				if (!hasHeight) {
					/* when the button and the row"s table are in different containers
					we need to add position top of the row"s container */
					v = go.virtualization || go.rowVirtualization || go.columnVirtualization;
					scrContainer = v ?
						this.grid._vdisplaycontainer()
						: this.grid.scrollContainer();
					if (scrContainer.length) {
						offset = scrContainer.position().top;
					}
				}
				left = sc.outerWidth() - acb.outerWidth() - sbw - dbw + sc.scrollLeft() - 5; /* indentation from right edge */
				top = row.position().top +
					row.outerHeight() / 2 -
					acb.outerHeight() / 2 +
					sc.scrollTop() +
					offset;
				acb.css({
					top: top,
					left: left
				});
				/* save the context of the button */
				acb.data("button-for", rowId);
			}
		},
		hideAddChildButton: function () {
			/* Hides the "Add Child" button.
			*/
			var acb = $("#" + this.grid.id() + "_updating_add_child_hover");
			if (acb.length) {
				acb.hide();
			}
		},
		/* Override base functions*/
		_create: function () {
			this.element.data(
				$.ui.igGridUpdating.prototype.widgetName,
				this.element.data($.ui.igTreeGridUpdating.prototype.widgetName)
			);
			$.ui.igGridUpdating.prototype._create.apply(this, arguments);
		},
		_addRow: function (evt, addObj, defVals, suppress, parentId) {
			var arg, key, t, row, index;
			if (parentId !== undefined && parentId !== null) {
				addObj = $.extend({}, defVals, addObj);
				arg = {
					owner: this,
					values: addObj,
					oldValues: defVals,
					addChild: true,
					parentID: parentId
				};
				if (!suppress) {
					if (!this._trigger(this.events.rowAdding, evt, arg)) {
						return false;
					}
				}
				key = addObj[ this.grid.options.primaryKey ];
				index = this.grid.dataSource.getChildrenByKey(parentId) ?
					this.grid.dataSource.getChildrenByKey(parentId).length :
					0;
				t = this.grid.dataSource
					.insertRow(key, addObj, index, this.grid.options.autoCommit, parentId);
				row = this._updateUIForTransaction(t);
				this._notifyRowAdded(row);
				/* increase the inner counter for the auto-generated PK */
				if ($.type(this._pkVal) === "number") {
					this._pkVal++;
				}
				if (!suppress) {
					this._trigger(this.events.rowAdded, evt, arg);
				}
			} else {
				this._super(evt, addObj, defVals, suppress, parentId);
			}
		},
		_startEditForCell: function (evt, suppress, rowId, columnKey, element, focus, isAdding, value) {
			this.hideAddChildButton();
			return this._super(evt, suppress, rowId, columnKey, element, focus, isAdding, value);
		},
		_rebindEditTriggers: function () {
			this._super();
			/* for add child */
			if (this.options.enableAddChild) {
				this._renderAddChildButton();
			}
		},
		_createHandlers: function () {
			this._super();
			this._buttonHandlers.addChildClick = $.proxy(this._addChildButtonClick, this);
			this._buttonHandlers._touchAddChildClick = $.proxy(this._touchAddChildClick, this);
			this._buttonHandlers._touchAddChildKeyUp = $.proxy(this._touchAddChildKeyUp, this);
		},
		_deleteRow: function (evt, rowId, suppress) {
			/* override base function _deleteRow */
			var res, $prevRow, $nextRow, $row = this.grid.rowById(rowId), dataLevel;
			if ($row && $row.length) {
				$nextRow = $row.next("tr");
				$prevRow = $row.prev("tr");
				dataLevel = parseInt($row.attr("aria-level"), 10);
			}
			res = this._super(evt, rowId, suppress);
			if (res && !isNaN(dataLevel)) {
				/* update UI - as remove child rows(if any) from DOM*/
				this._removeChildrenFromUI($nextRow, dataLevel);
				/* update parent (if exists)row - when deleting its last child - it should be removed its plus icon */
				this.grid._updateParentRowAfterDelete($prevRow, dataLevel);
			}
			return res;
		},
		_updateUIForTransaction: function (t, row) {
			var go = this.grid.options,
				autoCommit = go.autoCommit,
				v = go.virtualization || go.rowVirtualization || go.columnVirtualization,
				newRow, index;
			if (t.type === "insertnode") {
				/* if the row to be rendered is already found do nothing */
				if (this.grid.rowById(t.rowId).length) {
					return;
				}
				this._updateUnboundValuesForRow(t.row);
				if (v && !autoCommit) {
					return;
				}
				this.grid.renderNewChild(t.row, t.parentRowId);
				this._updateParentRowAfterAddChild(t.parentRowId);
				this.grid._rerenderDataSkipColumn(this.grid.dataSource.getDataBoundDepth());
				/* this.grid._updateGridContentWidth(); */
				newRow = this.grid.rowById(t.rowId);
				if (!autoCommit && newRow.length) {
					this._combineRowElements(newRow).addClass(this.grid.css.modifiedRecord);
				}
				index = newRow ? newRow.index() - 1 : 0;
				this.grid._reapplyZebraStyle(index);
			} else {
				if (t.type === "deleterow") {
					this.grid._rerenderColgroups();
				}
				newRow = this._super(t, row);
				if (this.grid.options.width === null && t.type === "deleterow") {
					this.grid._updateContainersWidthOnGridWidthNull();
				}
			}
			return newRow;
		},
		_setOption: function (key, value) {
			var button;
			$.Widget.prototype._setOption.apply(this, arguments);
			switch (key) {
				case "enableAddChild":
					if (value === true) {
						this._renderAddChildButton();
					} else {
						this._removeAddChildButton();
					}
					break;
				case "addChildTooltip":
					button = $("#" + this.grid.id() + "_updating_add_child_hover");
					if (button.length === 1) {
						button.attr("title", value);
					}
					break;
				default:
					this._super(key, value);
					break;
			}
		},
		destroy: function () {
			this._detachEvents();
			this._removeAddChildButton();
			$.ui.igGridUpdating.prototype.destroy.apply(this, arguments);
			this.element.removeData($.ui.igGridUpdating.prototype.widgetName);
		},
		_injectGrid: function (grid, isRebind) {
			$.ui.igGridUpdating.prototype._injectGrid.apply(this, arguments);
			if (!isRebind) {
				this._detachEvents();
				this._attachEvents();
			}
		}
		/* //Override base functions */
	});
	$.extend($.ui.igTreeGridUpdating, { version: "16.1.20161.2145" });
}(jQuery));



