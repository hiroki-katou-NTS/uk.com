
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
 *	infragistics.ui.grid.framework.js
 *	infragistics.ui.editors.js
 *	infragistics.ui.shared.js
 *	infragistics.dataSource.js
 *	infragistics.util.js
 *	infragistics.ui.treegrid.js
 *	infragistics.ui.grid.rowselectors.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igTreeGridRowSelectors are extending igGrid RowSelectors
	*/
	"use strict";
	$.widget("ui.igTreeGridRowSelectors", $.ui.igGridRowSelectors, {
		options: {
			/* type="sequential|hierarchical" determines row numbering format.
			sequential type="string" Defines numbering format to be the index of the visible records.
			hierarchical type="string" Defines numbering format to be concatenation of the parent and children indexes.
			*/
			rowSelectorNumberingMode: "sequential",
			/* type="biState|triState" Gets the type of checkboxes rendered in the row selector. Can be set only at initialization.
				biState type="string" Checkboxes are rendered and support two states(checked and unchecked). Checkboxes do not cascade down or up in this mode.
				triState type="string" Checkboxes are rendered and support three states(checked, partial and unchecked). Checkboxes cascade up and down in this mode.
			*/
			checkBoxMode: "biState"
		},
		css: {
			/* Classes applied to the row selectors treegrid parent cells if they should be partially or fully selected. Only when checkBoxMode is triState */
			rowSelectorChecked: "ui-state-checked",
			/* Classes defining the partially checked state of the checkbox. Used for tri-state mode */
			checkBoxP: "ui-icon ui-icon-check-p ui-igcheckbox-normal-on",
			/* Classes defining the fully checked state of the checkbox. Used for tri-state mode*/
			checkBoxF: "ui-icon ui-icon-check-f ui-igcheckbox-normal-on"
		},
		_create: function () {
			this.element.data(
				$.ui.igGridRowSelectors.prototype.widgetName,
				this.element.data($.ui.igTreeGridRowSelectors.prototype.widgetName)
			);
			$.ui.igGridRowSelectors.prototype._create.apply(this, arguments);
			/* when rowSelectorNumberingMode  === "hierarchical" */
			/* used for current indexes of each data level */
			this._cIndexes = {};
			/* save current path when rowSelectorNumberingMode  === "hierarchical" */
			this._path = "";
			/* flag to show that the path is build on expanding rows */
			this._pathBuild = false;
			/* associative array pKey: rowSelectorNumber when rowSelectorNumberingMode  === "hierarchical" */
			this._includedParentRecordNumbers = {};
			/* associative array pKey: rowSelectorNumber when rowSelectorNumberingMode  === "sequential" */
			this._recordNumbers = {};
			/* associative array for the checkbox states of the rows {pKey: 0|1} 0 - partially checked, 1 - fully checked */
			this._checkboxStates = {};
		},
		destroy: function () {
			$.ui.igGridRowSelectors.prototype.destroy.apply(this, arguments);
			this.element.removeData($.ui.igGridRowSelectors.prototype.widgetName);
		},
		_recordsRendering: function() {
			$.ui.igGridRowSelectors.prototype._recordsRendering.apply(this, arguments);
			if (this.options.rowSelectorNumberingMode  === "sequential") {
				if (this._cIdx === this.options.rowNumberingSeed) {
					this._numberRecords(this.grid.dataSource.data());
				}
				return;
			}
			/* only for rowSelectorNumberingMode  === "hierarchical" */
			if (!this.grid._rerenderGridRowsOnToggle()) {
				this._cIndexes = {};
				this._pathBuild = false;
			} else {
				/* number them only if they are not numbered before */
				if ($.isEmptyObject(this._includedParentRecordNumbers)) {
					this._numberRecordsWithParents(this.grid.dataSource.data(), "");
				}
			}
		},
		_rsExpandCollapseRow: function (row, expand) {
			if (expand) {
				this._cIndexes = {};
				this._path = (row instanceof jQuery ?
					row.find("span.ui-iggrid-rowselector-row-number").html() :
					/* when the row is the id */
					this.element
						.find("[data-id='" + row + "']")
						.find("span.ui-iggrid-rowselector-row-number").html()) + ".";
				this._pathBuild = true;
			}
			$.ui.igTreeGrid.prototype._expandCollapseRow.apply(this.grid, arguments);
		},
		_redirectFunctions: function () {
			if (this._functionsRedirected === false) {
				this._expandCollapseRowHandler = $.proxy(this._rsExpandCollapseRow, this);
				if (this.options.rowSelectorNumberingMode  !== "sequential") {
					/* override expandCollapseRow only when we need to include parents in rowselectors numbers */
					this.grid._expandCollapseRow = this._expandCollapseRowHandler;
				}
				if (this.options.checkBoxMode !== "biState") {
					/* override _select, _deselect, _handleCheck, _handleHeaderCheck only in tri state mode to decouple checkboxes from selection */
					this._select = $.proxy(this._selectHandler, this);
					this._deselect = $.proxy(this._deselectHandler, this);
					this._handleCheck = $.proxy(this._handleTriStateCheck, this);
					this._handleHeaderCheck = $.proxy(this._handleTriStateHeaderCheck, this);
				}
			}
			$.ui.igGridRowSelectors.prototype._redirectFunctions.apply(this, arguments);
		},
		_injectGrid: function () {
			var i;
			$.ui.igGridRowSelectors.prototype._injectGrid.apply(this, arguments);
			for (i = 0; i < this.grid.options.features.length; i++) {
				if (this.grid.options.features[ i ].name === "Selection") {
					if (this.grid.options.features[ i ].multipleSelection &&
						this.options.enableCheckBoxes &&
						this.options.checkBoxMode === "triState") {
						throw new Error(
							$.ig.TreeGridRowSelectors.locale.multipleSelectionWithTriStateCheckboxesNotSupported
						);
					}
					break;
				}
			}
		},
		_shouldRenderHeaderCheckBoxes: function() {
			var o = this.options;
			return (o.enableCheckBoxes === true && o.checkBoxMode === "triState") ||
				(o.enableCheckBoxes === true && o.checkBoxMode === "biState" && this._ms);
		},
		_updateHeader: function () {
			var dvl, sl, checkboxes, check = true, i;
			if (this.grid.element.data("igGridPaging")) {
				checkboxes = this._allCheckboxes();
				if (checkboxes.length === 0) {
					check = false;
				} else {
					for (i = 0; i < checkboxes.length; i++) {
						if (checkboxes.eq(i).attr("data-chk") === "off") {
							check = false;
							break;
						}
					}
				}
			} else {
				dvl = this.grid.dataSource._totalRecordsCount;
				sl = this.grid._selection ? this.grid._selection.selectionLength() : 0;
				if (this.options.checkBoxMode === "triState") {
					check = this._areAllRecordsChecked(sl, dvl);
				} else {
					check = sl === dvl && this._isFirstRowSelected();
				}
			}
			this._alterCheckbox(this._headerCheckbox(), check);
		},
		_rowExpanded: function (ui) {
			if (this.options.rowSelectorNumberingMode  === "sequential") {
				this._reapplyNumbering(ui);
			}
			this._unregisterCellEvents();
			this._registerCellEvents();
			if (this.options.enableCheckBoxes === true) {
				this._registerCheckBoxEvents();
				if (this._ms) {
					this._updateHeader();
				}
			}
		},
		_rowCollapsed: function (ui) {
			if (this.options.rowSelectorNumberingMode  === "sequential") {
				this._reapplyNumbering(ui);
			}
			/*  not needed */
			/*if (this.options.enableCheckBoxes === true && this._ms) {
				this._updateHeader();
			} */
		},
		_buildPath: function (rowData) {
			var dataLevel = this.grid.options.dataSourceSettings.propertyDataLevel,
				dbDepth = rowData[ dataLevel ],
				startingIndex;
			if (!this._pathBuild) {
				this._path = "";
				while (dbDepth > 0) {
					startingIndex = dbDepth === 1 && !this.grid._rerenderGridRowsOnToggle() ?
						this._getStartingIndexForPage() :
						0;
					this._path = this._cIndexes[ dbDepth - 1 ] +
						this.options.rowNumberingSeed +
						startingIndex +
						"." +
						this._path;
					dbDepth--;
				}
			}
		},
		_getCurrentNumber: function (rowData) {
			var pKey = this.grid.options.primaryKey,
				dataLevel = this.grid.options.dataSourceSettings.propertyDataLevel,
				expanded = this.grid.options.dataSourceSettings.propertyExpanded,
				seed, startingIndex;
			if (this.options.rowSelectorNumberingMode  === "sequential" &&
				!$.isEmptyObject(this._recordNumbers)) {
				return "<span class='ui-iggrid-rowselector-row-number'>" +
					/* When expanding and value is not calculated yet undefined value
					*  will extend the height of records if width of the row selector is small. */
					(this._recordNumbers[ rowData[ pKey ] ] || "") +
					"</span>";
			}
			/* if object is not empty use their indexes */
			if (!$.isEmptyObject(this._includedParentRecordNumbers)) {
				return "<span class='ui-iggrid-rowselector-row-number'>" +
					this._includedParentRecordNumbers[ rowData[ pKey ] ] +
					"</span>";
			}
			if (!this._cIndexes[ rowData[ dataLevel ] ]) {
				this._cIndexes[ rowData[ dataLevel ] ] = 0;
			}
			if (rowData[ expanded ]) {
				this._cIndexes[ rowData[ dataLevel ] + 1 ] = 0;
			}
			seed = ++this._cIndexes[ rowData[ dataLevel ] ];
			startingIndex = this.grid._rerenderGridRowsOnToggle() ?
				0 :
				this._getStartingIndexForPage();
			this._buildPath(rowData);
			return "<span class='ui-iggrid-rowselector-row-number'>" +
				(this._path === "" ?
				seed + this.options.rowNumberingSeed + startingIndex :
				this._path + seed) +
				"</span>";
		},
		_getRowSelectorCellMarkup: function (template, selected, rowData) {
			if (!rowData) {
				return "";
			}
			var markup = "",
				o = this.options,
				go = this.grid.options,
				pKey = go.primaryKey,
				rowId = rowData[ pKey ],
				checkStateDefined = this._checkboxStates[ rowId ] !== undefined &&
					this._checkboxStates[ rowId ] !== null;
			markup +=  "<th role='rowheader' tabindex='" + go.tabIndex + "' class='" + this.css.rowSelector;

			if (selected) {
				markup += " " + this.css.rowSelectorSelected;
			}
			if (checkStateDefined) {
				markup += " " + this.css.rowSelectorChecked;
			}

			if (template) {
				markup += " " + this._sTmpl;
			}
			markup += "'><span class='ui-icon ui-icon-triangle-1-e' style='margin-left: -5px'></span>";
			if (o.enableRowNumbering) {
				markup += template === true ? this._nTmpl : this._getCurrentNumber(rowData);
			}
			if (o.enableCheckBoxes) {
				/* in tri state we could have selected and not checked rows */
				markup += this._getTriStateCheckBox(rowData, selected && o.checkBoxMode !== "triState" ||
													checkStateDefined);
			}
			markup += "</th>";
			return markup;
		},
		_getTriStateCheckBox: function (rowData, checked) {
			var markup = "",
				dataChk = "off",
				checkBoxState = this.css.checkBoxOff,
				pKey = this.grid.options.primaryKey;
			if (checked) {
				dataChk = "on";
				/* is rowData[pKey] partially checked */
				checkBoxState = this._checkboxStates[ rowData[ pKey ] ] === 0 ?
					this.css.checkBoxP :
					this.css.checkBoxF;
			}
			markup += "<span name='" + "chk" + "' ";
			markup += "data-chk='" + dataChk + "' ";
			markup += "data-role='checkbox' class='" + this.css.checkBox + " '";
			markup += "tabindex='" + this.grid.options.tabIndex + " '";
			if (this.options.showCheckBoxesOnFocus === true && this._checkBoxesShown === false) {
				markup += " style='visibility: hidden;'";
			}
			markup += "><span class='" + checkBoxState + "'>";
			markup += "</span></span>";
			return markup;
		},
		_numberRecordsWithParents: function (data, currentPath) {
			var pKey = this.grid.options.primaryKey,
				childDataKey = this.grid.options.childDataKey,
				counter = 1, cPath,
				rs = this, dataLen, i, dataRow;
			if ($.type(data) === "array") {
				dataLen = data.length;
				for (i = 0; i < dataLen; i++, counter++) {
					dataRow = data[ i ];
					cPath = (currentPath === "" ? "" : (currentPath + ".")) + counter.toString();
					if (rs._includedParentRecordNumbers === undefined ||
						rs._includedParentRecordNumbers === null) {
						rs._includedParentRecordNumbers = {};
					}
					rs._includedParentRecordNumbers[ dataRow[ pKey ] ] = cPath;
					if (dataRow[ childDataKey ] !== undefined && dataRow[ childDataKey ] !== null) {
						rs._numberRecordsWithParents(dataRow[ childDataKey ], cPath);
					}
				}
			}
		},
		_numberRecords: function (data) {
			var pKey = this.grid.options.primaryKey,
			childDataKey = this.grid.options.childDataKey,
			expanded = this.grid.options.dataSourceSettings.propertyExpanded,
			dataLen, i, dataRow;
			if ($.type(data) === "array") {
				dataLen = data.length;
				for (i = 0; i < dataLen; i++) {
					dataRow = data[ i ];
					if (this._recordNumbers === undefined || this._recordNumbers === 0) {
						this._cIdx = this.options.rowNumberingSeed;
						this._recordNumbers = {};
					}
					this._recordNumbers[ dataRow[ pKey ] ] = ++this._cIdx;
					if (dataRow[ expanded ] && dataRow[ childDataKey ] !== undefined &&
						dataRow[ childDataKey ] !== null) {
						this._numberRecords(dataRow[ childDataKey ]);
					}
				}
			}
		},
		/* only for rowSelectorNumberingMode  === "sequential" */
		_reapplyNumbering: function (ui) {
			var nextRowId,
				nextRow = ui.fixedRow || ui.row,
				rsSelector = "span.ui-iggrid-rowselector-row-number";
			/* reset counter */
			this._cIdx = this.options.rowNumberingSeed;
			this._numberRecords(this.grid.dataSource.data());
			/* reapply new numbering to row selectors starting from the toggled row */
			while (nextRow.length === 1) {
				nextRowId = nextRow.attr("data-id");
				nextRow.find(rsSelector).text(this._recordNumbers[ nextRowId ]);
				nextRow = nextRow.next("tr[aria-level]");
			}
		},
		_selectHandler: function (info) {
			var res;
			info.element = info.element || this._getRowById(info.id);
			res = this._rowSelectorFromSelection(info);
			if (!res) {
				return;
			}
			res.rowSelector.addClass(this.css.rowSelectorSelected);
		},
		_deselectHandler: function (info) {
			var res, shouldDeselect;
			info.element = info.element || this._getRowById(info.id);
			res = this._rowSelectorFromSelection(info);
			if (!res) {
				return;
			}
			shouldDeselect = this.grid._selection instanceof $.ig.SelectedRowsCollection ||
				!this.grid._selection.atLeastOneSelected(
					this.grid._fixPKValue(info.element.parent().attr("data-id"))
				);
			if (shouldDeselect) {
				res.rowSelector.removeClass(this.css.rowSelectorSelected);
			}
		},
		_changeParentsCheckState: function (rowId, toCheck) {
			var grid = this.grid,
				pKey = grid.options.primaryKey,
				action = $.proxy(toCheck ? this._checkParent : this._uncheckParent, this),
				parents, res, i, element, parentRow;
			parents = grid.dataSource.getParentRowsForRow(rowId);
			for (i = parents.length - 1; i >= 0; i--) {
				parentRow = parents[ i ].row;
				element = this._getRowById(parentRow[ pKey ]);
				res = this._rowSelectorFromSelection({ "element": element });
				action(parentRow, res);
			}
		},
		_checkParent: function (parentRow, res) {
			var pKey = this.grid.options.primaryKey,
				childDataKey = this.grid.options.childDataKey,
				parentRowId = parentRow[ pKey ];
			if (this._areAllChildrenChecked(parentRow[ childDataKey ])) {
				/* fully checked */
				this._checkboxStates[ parentRowId ] = 1;
				res.checkbox.parent().addClass(this.css.rowSelectorChecked);
			} else {
				/* partially checked */
				this._checkboxStates[ parentRowId ] = 0;
				res.checkbox.parent().addClass(this.css.rowSelectorChecked);
			}
			this._alterTriStateCheckbox(res.checkbox, this._checkboxStates[ parentRowId ]);
		},
		_uncheckParent: function (parentRow, res) {
			var pKey = this.grid.options.primaryKey,
				childDataKey = this.grid.options.childDataKey,
				parentRowId = parentRow[ pKey ];
			if (this._hasCheckedChildren(parentRow[ childDataKey ])) {
				/* partially checked */
				this._checkboxStates[ parentRowId ] = 0;
				this._alterTriStateCheckbox(res.checkbox, 0);
			} else {
				/* unchecked */
				delete this._checkboxStates[ parentRowId ];
				res.checkbox.parent().removeClass(this.css.rowSelectorChecked);
				this._alterTriStateCheckbox(res.checkbox);
			}
		},
		/* toCheck - bool, true is checked state, false is unchecked state */
		_changeChildrenCheckState: function(rowId, toCheck) {
			var grid = this.grid,
				children = grid.dataSource.getChildrenByKey(rowId, grid.dataSource._data) || [],
				pKey = grid.options.primaryKey,
				childDataKey = grid.options.childDataKey,
				i, childId, child, element, res;
			for (i = 0; i < children.length; i++) {
				child = children[ i ];
				childId = child[ pKey ];
				element = this._getRowById(childId);
				res = this._rowSelectorFromSelection({ "element": element });
				if (toCheck) {
					this._checkboxStates[ childId ] = 1;
					res.checkbox.parent().addClass(this.css.rowSelectorChecked);
					this._alterTriStateCheckbox(res.checkbox, 1);
				} else {
					delete this._checkboxStates[ childId ];
					res.checkbox.parent().removeClass(this.css.rowSelectorChecked);
					this._alterTriStateCheckbox(res.checkbox);
				}
				/*this._alterTriStateCheckbox(res.checkbox, 1);*/
				if ($.type(child[ childDataKey ]) === "array") {
					this._changeChildrenCheckState(childId, toCheck);
				}
			}
		},
		_areAllChildrenChecked: function (children) {
			var i, childrenLen = children ? children.length : 0,
				pKey = this.grid.options.primaryKey,
				childId, state;
			for (i = 0; i < childrenLen; i++) {
				childId = children[ i ][ pKey ];
				/* 0 - partial, 1 - fully checked */
				state = this._checkboxStates[ childId ];
				/* if all of the immediate children are fully checked we can be sure to fully check the parent */
				if (state === null || state === undefined || state === 0) {
					return false;
				}
			}
			return true;
		},
		_hasCheckedChildren: function (children) {
			var i, childrenLen = children ? children.length : 0,
				pKey = this.grid.options.primaryKey,
				childId, state;
			for (i = 0; i < childrenLen; i++) {
				/* should make a check for is checked probably */
				childId = children[ i ][ pKey ];
				/* 0 - partial, 1 - fully checked */
				state = this._checkboxStates[ childId ];
				if (state !== undefined && state !== null) {
					return true;
				}
			}
			return false;
		},
		_rrn: function () {
			var rs = this._allRowSelectorCells(), cb = this._allCheckboxes(), cbx, i = 0, self = this,
				sri = this.grid._startRowIndex || 0, row, rowId, state;
			if (this.grid.options.virtualization === true &&
				 this.grid.options.virtualizationMode === "continuous") {
				this._unregisterCellEvents();
				this._registerCellEvents();
			}
			if (!this._skipRefresh) {
				rs.removeClass(this.css.rowSelectorSelected);
				cb.map(function () {
					self._alterCheckbox($(this), false);
				});
				for (i = 0; i < rs.length; i++) {
					row = rs.eq(i).closest("tr");
					rowId = this.grid._fixPKValue(row.attr("data-id"));
					/* 0 - partial, 1 - fully checked */
					state = this._checkboxStates[ rowId ];
					if (rowId === null || rowId === undefined) {
						rowId = i + sri;
					}
					if (this.grid._selection.selection[ rowId ] !== undefined ||
						state !== undefined && state !== null) {
						if (this.grid._selection.selection[ rowId ]) {
							rs.eq(i).addClass(this.css.rowSelectorSelected);
						}
						/*if (state === 0) {
							rs.eq(i).addClass(this.css.rowSelectorPartiallySelected);
						}*/
						cbx = cb.eq(i);
						if (cbx.length === 1) {
							if (this.options.checkBoxMode !== "triState" && (state === undefined || state === null)) {
								this._alterCheckbox(cbx, true);
							} else {
								this._alterTriStateCheckbox(cbx, state);
							}
						}
					}
				}
			}
		},
		_alterTriStateCheckbox: function (checkbox, checkState) {
			/* checkState = 1 - true | 0 - mixed | undefined,null - false */
			var inner = checkbox.children().first();
			if (checkbox.length > 0 && inner.length > 0) {
				if (checkState === 0) {
					checkbox.attr("data-chk", "mixed");
					inner
						.removeClass(this.css.checkBoxOff)
						.removeClass(this.css.checkBoxF)
						.addClass(this.css.checkBoxP);
				} else if (checkState === 1) {
					checkbox.attr("data-chk", "on");
					inner
						.removeClass(this.css.checkBoxOff)
						.removeClass(this.css.checkBoxP)
						.addClass(this.css.checkBoxF);
				} else {
					checkbox.attr("data-chk", "off");
					inner
						.removeClass(this.css.checkBoxP)
						.removeClass(this.css.checkBoxF)
						.addClass(this.css.checkBoxOff);
				}
			}
		},
		_clearSelection: function () {
			var rsCells, self = this;
			if (this.grid.hasFixedColumns() && this.grid.fixingDirection() === "left") {
				rsCells = this.grid.fixedBodyContainer()
							.find("tbody")
							.find("th.ui-iggrid-selectedcell")
							.removeClass(this.css.rowSelectorSelected);
			} else {
				rsCells = this.grid.element
							.children("tbody")
							.find("th.ui-iggrid-selectedcell")
							.removeClass(this.css.rowSelectorSelected);
			}
			if (this.options.enableCheckBoxes && this.options.checkBoxMode !== "triState") {
				$.each(rsCells, function () {
					self._alterCheckbox($(this).children("span:last"), false);
				});
				this._alterCheckbox(this._headerCheckbox(), false);
			}
		},
		_areAllRecordsChecked: function(sl, dvl) {
			var size = 0;
			for (var key in this._checkboxStates) {
				/* if there is a partially checked parent in _checkboxStates return false */
				if (this._checkboxStates.hasOwnProperty(key)) {
					/* if partially checked */
					if (this._checkboxStates[ key ] === 0) {
						return false;
					}
					/* if fully checked increase size */
					if (this._checkboxStates[ key ] === 1) {
						size++;
					}
				}
			}
			/*_checkboxParentStates has only parents which are checked but not selected
			* _checkboxStates has rows which are checked or partially checked
			* full checkboxParentStates should be recordNumber */
			return size === dvl;
		},
		_getDataView: function () {
			return this.grid.dataSource.flatDataView();
		},
		_getAllData: function () {
			if (this._flatData) {
				return this._flatData;
			} else {
				this._flatData = this.grid.dataSource.generateFlatData().flatData;
				return this._flatData;
			}
		},
		_dataRendered: function() {
			this._unregisterCellEvents();
			this._registerCellEvents();
			if (this.options.enableCheckBoxes === true) {
				this._unregisterCheckBoxEvents();
				this._registerCheckBoxEvents();
			}
		},
		_handleTriStateCheck: function (checkbox) {
			var row, rowId, upd, sel, offset;
			if (!checkbox) {
				return;
			}
			upd = this.grid.element.data("igGridUpdating");
			sel = this._getSelectionInstance();
			if (sel && sel._suspend) {
				if (upd) {
					if (upd.findInvalid()) {
						return;
					}
					upd._endEdit(null, true);
				} else {
					return;
				}
			}
			offset = this._v ? this.grid._startRowIndex : 0;
			row = checkbox.parent().parent();
			if (this.grid.hasFixedColumns()) {
				if (this.grid.fixedBodyContainer().attr("data-fixing-direction") === "left") {
					row = row.add(this.grid.element.find("tbody > tr").eq(row.index()));
				} else {
					row = row.add(this.grid.fixedBodyContainer().find("tbody > tr").eq(row.index()));
				}
			}
			rowId = this.grid._fixPKValue(row.attr("data-id"));
			if (rowId === null || rowId === undefined) {
				rowId = row.closest("tbody").children("tr:not([data-container])").index(row) + offset;
			}
			this.changeCheckStateById(rowId, checkbox.attr("data-chk") === "off");
			/*if (checkbox.attr("data-chk") === "off") {
				checkbox.parent().addClass("ui-state-checked");
				this._alterCheckbox(checkbox, true);
				this._changeChildrenCheckState(rowId, true);
				this._changeParentsCheckState(rowId, true);
			} else {
				checkbox.parent().removeClass("ui-state-checked");
				this._alterCheckbox(checkbox, false);
				this._changeChildrenCheckState(rowId, false);
				this._changeParentsCheckState(rowId, false);
			} */
			if (!this._suspendHeader) {
				this._updateHeader();
			}
		},
		_handleTriStateHeaderCheck: function (checkbox) {
			var dv = this._getDataView(),
				elem = this.grid.element,
				toCheck = checkbox.attr("data-chk") === "off",
				templateData, c, all;
			this._changeCheckStateForRows(dv, toCheck);
			this._alterCheckbox(checkbox, toCheck);
			if (this.options.enableSelectAllForPaging && elem.data("igGridPaging")) {
				c = this.checkedRows().length;
				all = this._getAllData().length;
				templateData = [{
					checked: c,
					unchecked: all - c,
					allCheckedRecords: c,
					/* totalRecordsCount: this.grid.dataSource.totalLocalRecordsCount() */
					totalRecordsCount: all
				}];
				this._renderOverlay(templateData, toCheck);
			}
		},
		_selectAllFromOverlay: function () {
			if (this.options.checkBoxMode === "biState") {
				/* use selecting mechanism in grid selection */
				this._changeCheckStateForAllRecords(this._getSelectionInstance(), true);
			} else {
				this._changeCheckStateForRows(this._getAllData(), true);
			}
		},
		_deselectAllFromOverlay: function () {
			if (this.options.checkBoxMode === "biState") {
				/*use deselecting mechanism in grid selection */
				this._changeCheckStateForAllRecords(this._getSelectionInstance(), false);
			} else {
				this._changeCheckStateForRows(this._getAllData(), false);
			}
		},
		_getRowById: function (identifier) {
			var urow, frow = $();
			if (this.grid.hasFixedColumns()) {
				frow = this.grid.rowById(identifier, true);
			}
			urow = this.grid.rowById(identifier, false);
			return urow instanceof jQuery ? urow.add(frow) : $();
		},
		_changeCheckStateForRows: function (dv, toCheck) {
			var i, res, element, rowId,
				pKey = this.grid.options.primaryKey;
			for (i = 0; i < dv.length; i++) {
				rowId = dv[ i ][ pKey ];
				element = this._getRowById(rowId);
				res = this._rowSelectorFromSelection({ "element": element });
				this._alterCheckbox(res.checkbox, toCheck);
				this._changeChildrenCheckState(rowId, toCheck);
				this._changeParentsCheckState(rowId, toCheck);
			}
		},
		_changeTriStateById: function (rowId, toCheck) {
			var element = this._getRowById(rowId),
				res = this._rowSelectorFromSelection({ "element": element }),
				checkbox = res.checkbox;
			if (toCheck) {
				checkbox.parent().addClass("ui-state-checked");
			} else {
				checkbox.parent().removeClass("ui-state-checked");
			}
			this._alterCheckbox(checkbox, toCheck);
			this._changeChildrenCheckState(rowId, toCheck);
			this._changeParentsCheckState(rowId, toCheck);
		},
		changeCheckStateById: function (rowId, toCheck) {
			/* change the check state of a row by row id
				paramType="number|string" Row Id
				paramType="bool" The new check state of the checkbox - true for checked or false for unchecked
			*/
			if (this.options.checkBoxMode === "biState") {
				if (toCheck) {
					this._selection.selectRowById(rowId);
					return;
				}
				this._selection.deselectRowById(rowId);
				return;
			}
			this._changeTriStateById(rowId, toCheck);
		},
		changeCheckState: function (index, toCheck) {
			/* changes the check state of a row by index to specified value
				paramType="number" Row index
				paramType="bool" The new check state of the checkbox - true for checked or false for unchecked
			*/
			var pKey = this.grid.options.primaryKey,
				rowId = this._getDataView()[ index ][ pKey ];
			this.changeCheckStateById(rowId, toCheck);
		},
		toggleCheckStateById: function (rowId) {
			/* toggles the check state of a row by row id
				paramType="number|string" Row Id
			*/
			var element = this._getRowById(rowId),
				res = this._rowSelectorFromSelection({ "element": element }),
				checkbox = res.checkbox;
			if (checkbox.attr("data-chk") === "off") {
				this.changeCheckStateById(rowId, true);
			} else {
				this.changeCheckStateById(rowId, false);
			}
		},
		toggleCheckState: function (index) {
			/* toggles the check state of a row by index
				paramType="number" Row index
			*/
			var pKey = this.grid.options.primaryKey,
				rowId = this._getDataView()[ index ][ pKey ];
			this.toggleCheckStateById(rowId);
		},
		_getIndexForRow: function (row) {
			return row.closest("tbody").children("tr:not([data-container])").index(row) +
				/* add virtualization start */
				(this.grid._startRowIndex || 0);
		},
		_rowObject: function (rowId) {
			var rowInfo = {};
			rowInfo.element = this._getRowById(rowId);
			rowInfo.index = this._getIndexForRow(rowInfo.element);
			return $.extend(true, {}, rowInfo, { id: rowId });
		},
		_checkForRequireSelectionWithCheckboxes: function () {
			return this.options.requireSelection === false &&
				this.options.enableCheckBoxes === true &&
				this.options.checkBoxMode === "biState";
		},
		checkedRows: function () {
			/* Gets an array of all the checked rows. Every object from the array has the following format { element: , id: , index: }
				returnType="array"
			*/
			var checkStateKey, rows = [];
			if (this.options.checkBoxMode === "biState") {
				if (this._ms) {
					return this._selection.selectedRows();
				}
				return this._selection.selectedRow() ? [ this._selection.selectedRow() ] : [];
			}
			for (checkStateKey in this._checkboxStates) {
				if (this._checkboxStates.hasOwnProperty(checkStateKey) &&
					this._checkboxStates[ checkStateKey ] === 1) {
					rows.push(this._rowObject(this.grid._fixPKValue(checkStateKey)));
				}
			}
			return rows;
		},
		partiallyCheckedRows: function () {
			/* Gets an array of all the partially checked rows. Every object from the array has the following format { element: , id: , index: }
				returnType="array"
			*/
			var checkStateKey, rows = [];
			for (checkStateKey in this._checkboxStates) {
				if (this._checkboxStates.hasOwnProperty(checkStateKey) &&
					this._checkboxStates[ checkStateKey ] === 0) {
					rows.push(this._rowObject(this.grid._fixPKValue(checkStateKey)));
				}
			}
			return rows;
		},
		uncheckedRows: function () {
			/* Gets an array of all the unchecked rows. Every object from the array has the following format { element: , id: , index: }
				returnType="array"
			*/
			/*var dv = this._getDataView(),*/
			var dv = this._getAllData(),
				rows = [], i, rowId,
				pKey = this.grid.options.primaryKey,
				checkedRows = (this.options.checkBoxMode === "biState" ?
					this.grid._selection.selection :
					this._checkboxStates);
			for (i = 0; i < dv.length; i++) {
				rowId = dv[ i ][ pKey ];
				if (checkedRows[ rowId ] === undefined || checkedRows[ rowId ] === null) {
					rows.push(this._rowObject(this.grid._fixPKValue(rowId)));
				}
			}
			return rows;
		},
		checkStateById: function (rowId) {
			/* Returns the check state of the row by id.
				returnType="string"
			*/
			if (this.options.checkBoxMode === "biState") {
				return (this.grid._selection.selection[ rowId ] ?
					"on" :
					"off");
			}
			if (this._checkboxStates[ rowId ] === undefined || this._checkboxStates[ rowId ] === null) {
				return "off";
			}
			if (this._checkboxStates[ rowId ] === 1) {
				return "on";
			}
			if (this._checkboxStates[ rowId ] === 0) {
				return "partial";
			}
		}
	});
	$.extend($.ui.igTreeGridRowSelectors, { version: "16.1.20161.2145" });
}(jQuery));



