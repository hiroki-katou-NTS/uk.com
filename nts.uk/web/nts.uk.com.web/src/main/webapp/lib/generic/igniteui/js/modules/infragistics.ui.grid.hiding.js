/*!@license
 * Infragistics.Web.ClientUI Grid Column Hiding 16.1.20161.2145
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

/*jscs:disable*/
/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igGridHiding summary
	*/
	$.widget("ui.igGridHiding", {
		options: {
			/* type="array" A list of column settings that specifies hiding options on a per column basis. */
			columnSettings: [
				{
					/* type="string" Column key. this is a required property in every column setting if columnIndex is not set. */
					columnKey: null,
					/* type="number" Column index. Can be used in place of column key. the preferred way of populating a column setting is to always use the column keys as identifiers. */
					columnIndex: null,
					/* type="bool" Allows the column to be hidden. */
					allowHiding: true,
					/* type="bool" Sets the initial visibility of the column. */
					hidden: false
				}
			],
			/* type="int" The width in pixels of the hidden column indicator in the header. */
			hiddenColumnIndicatorHeaderWidth: 7,
			/* type="string" Controls containment behavior.
				owner type="string" The column chooser dialog will be draggable only in the grid area
				window type="string" The column chooser dialog will be draggable in the whole window area
			*/
			columnChooserContainment: "owner",
			/* type="string" The default column chooser width. */
			columnChooserWidth: 350,
			/* type="string" The default column chooser height. */
			columnChooserHeight: "",
			/* type="number" The duration of the dropdown animation in milliseconds. */
			dropDownAnimationDuration: 500,
			/* type="string" The caption of the column chooser dialog. */
			columnChooserCaptionText: $.ig.GridHiding.locale.columnChooserCaptionLabel,
			/* type="string" The text used in the drop down tools menu(Feature Chooser) to launch the column chooser dialog. */
			columnChooserDisplayText: $.ig.GridHiding.locale.columnChooserDisplayText,
			/* type="string" The text displayed in the tooltip of the hidden column indicator. */
			hiddenColumnIndicatorTooltipText: $.ig.GridHiding.locale.hiddenColumnIndicatorTooltipText,
			/* type="string" The text used in the drop down tools menu(Feature Chooser) to hide a column. */
			columnHideText: $.ig.GridHiding.locale.columnHideText,
			/* type="string" The text used in the column chooser to show column */
			columnChooserShowText: $.ig.GridHiding.locale.columnChooserShowText,
			/* type="string" The text used in the column chooser to hide column */
			columnChooserHideText: $.ig.GridHiding.locale.columnChooserHideText,
			/* type="bool" Specifies on click show/hide directly to be shown/hidden columns. If columnChooserHideOnClick is false then Apply and Cancel Buttons are shown on the bottom of modal dialog. Columns are Shown/Hidden after the Apply button is clicked */
			columnChooserHideOnClick: false,
			/* type="string" Text label for reset button. */
			columnChooserResetButtonLabel: $.ig.GridHiding.locale.columnChooserResetButtonLabel,
			/* type="number" Specifies time of milliseconds for animation duration to show/hide modal dialog */
			columnChooserAnimationDuration: 200,
			/* M.H. 9 March 2012 Fix for bug #103978 */
			/* type="string" Specifies text of button which apply changes in modal dialog */
			columnChooserButtonApplyText: $.ig.GridHiding.locale.columnChooserButtonApplyText,
			/* type="string" Specifies text of button which cancel changes in modal dialog */
			columnChooserButtonCancelText: $.ig.GridHiding.locale.columnChooserButtonCancelText,
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		css: {
			/* Classes applied to the column chooser element */
			"columnChooser": "ui-dialog ui-draggable ui-resizable ui-iggrid-dialog ui-widget ui-widget-content ui-corner-all",
			/* Classes applied to the column chooser dialog header caption area */
			"columnChooserHeaderCaption": "ui-dialog-titlebar ui-iggrid-columnchooser-caption ui-widget-header ui-corner-top ui-helper-reset ui-helper-clearfix",
			/* Classes applied to the column chooser dialog header caption title */
			"columnChooserHeaderCaptionTitle": "ui-dialog-title ui-iggrid-columnchooser-caption-title",
			/* Classes applied to the column chooser dialog content */
			"columnChooserContent": "ui-dialog-content ui-iggrid-columnchooser-content",
			/* Classes applied to the hiding header icon */
			"hidingHeaderIndicator": "ui-iggrid-hiding-indicator",
			/* Classes applied to the column chooser handlebar at the bottom */
			"columnChooserHandleBar": "ui-iggrid-columnchooser-handlebar",
			/* Classes applied to the hidden column indicator in the header */
			"hiddenColumnIndicator": "ui-iggrid-hiding-hiddencolumnindicator",
			/* Classes applied to the hidden column indicator in the header when it is selected(user has clicked and the dropdown is visible) */
			"hiddenColumnIndicatorSelected": "ui-iggrid-hiding-indicator-selected",
			/* Classes applied to the hidden column indicator in the header when the mouse is over it */
			"hiddenColumnIndicatorMouseOver": "ui-iggrid-hiding-indicator-mouseover",
			/* Classes applied to the hidden columns dropdown div */
			"hiddenColumnsDropDown": "ui-iggrid-hiding-dropdown-dialog ui-widget ui-widget-content ui-corner-all",
			/* Classes applied to the hidden columns dropdown ul */
			"hiddenColumnsDropDownList": "ui-iggrid-hiding-dropdown-list ui-menu",
			/* Classes applied to the hidden columns dropdown li*/
			"hiddenColumnsDropDownItem": "ui-iggrid-hiding-dropdown-ddlistitemicons ui-state-default",
			/* Classes applied to the hidden columns dropdown li when hovered */
			"hiddenColumnsDropDownItemHover": "ui-iggrid-hiding-dropdown-listitem-hover ui-state-active ui-state-hover",
			/* Classes applied to the hidden columns dropdown span inside the li */
			"hiddenColumnsDropDownItemText": "ui-iggrid-hiding-dropdown-ddlistitemtext",
			/* Classes applied to list(which shows all columns) in column chooser */
			"columnChooserListItems": "ui-iggrid-columnchooser-listitems",
			/* Classes applied to list item(which shows single column) in column chooser */
			"columnChooserItem": "ui-iggrid-columnchooser-item ui-widget-content",
			/* Classes applied to list item(which shows single column) in column chooser when column is hidden */
			"columnChooserItemHidden": "ui-iggrid-columnchooser-itemhidden",
			/* Classes applied to hide button in list item(which shows single column) in column chooser */
			"columnChooserHideButton": "ui-iggrid-columnchooser-hidebutton",
			/* Classes applied to show button in list item(which shows single column) in column chooser */
			"columnChooserItemText": "ui-iggrid-dialog-text",
			/* Classes applied to the feature chooser icon for hiding feature */
			"featureChooserHidingIcon": "ui-icon ui-iggrid-icon-hide",
			/* Classes applied to the feature chooser icon for column chooser feature */
			"featureChooserModalDialogIcon": "ui-icon ui-iggrid-icon-column-chooser"
		},
		events: {
			/* cancel="true" event fired before a hiding operation is executed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnIndex to get the hidden column index. Has a value only if the column's key is a number.
			Use ui.columnKey to get the hidden column key. Has a value only if the column's key is a string.
			*/
			columnHiding: "columnHiding",
			/* event fired when trying to hide all columns in fixed or unfixed area.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnKeys array of column keys. Has a value only if the column's key is a string.
			*/
			columnHidingRefused: "columnHidingRefused",
			/* event fired when trying to show column(s) in fixed area and total width of fixed are will be larger than whole grid width.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnKeys array of column keys. Has a value only if the column's key is a string.
			*/
			columnShowingRefused: "columnShowingRefused",
			/* cancel="true" event fired before a hiding operation is executed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnKeys to get array of the hidden column keys. In case when click on Reset button in column chooser then this is an array of column keys.
			*/
			multiColumnHiding: "multiColumnHiding",
			/* event fired after the hiding has been executed and results are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnIndex to get the hidden column index. Has a value only if the column's key is a number.
			Use ui.columnKey to get the hidden column key. Has a value only if the column's key is a string.
			*/
			columnHidden: "columnHidden",
			/* cancel="true" event fired before a showing operation is executed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnIndex to get the shown column index.
			Use ui.columnKey to get the shown column key.
			*/
			columnShowing: "columnShowing",
			/* event fired after the showing has been executed and results are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnIndex to get the shown column index.
			Use ui.columnKey to get the shown column key.
			*/
			columnShown: "columnShown",
			/* cancel="true" event fired before the column chooser is opened.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the Column Chooser element. This is a jQuery object.
			*/
			columnChooserOpening: "columnChooserOpening",
			/* event fired after the column chooser is already opened.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the Column Chooser element. This is a jQuery object.
			*/
			columnChooserOpened: "columnChooserOpened",
			/* event fired every time the column chooser changes its position.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the Column Chooser element. This is a jQuery object.
			Use ui.originalPosition to get the original position of the column chooser div as { top, left } object, relative to the page.
			Use ui.position to get the current position of the column chooser div as { top, left } object, relative to the page.
			*/
			columnChooserMoving: "columnChooserMoving",
			/* cancel="true" event fired before the column chooser is closed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the Column Chooser element. This is a jQuery object.
			*/
			columnChooserClosing: "columnChooserClosing",
			/* event fired after the column chooser has been closed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the Column Chooser element. This is a jQuery object.
			*/
			columnChooserClosed: "columnChooserClosed",
			/* cancel="true" event fired before the contents of the column chooser are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the Column Chooser element. This is a jQuery object.
			*/
			columnChooserContentsRendering: "columnChooserContentsRendering",
			/* event fired after the contents of the column chooser are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the Column Chooser element. This is a jQuery object.
			*/
			columnChooserContentsRendered: "columnChooserContentsRendered",
			/* event fired when button Apply in column chooser is clicked
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the Column Chooser element. This is a jQuery object.
			Use ui.columnsToShow to get array of columns identifiers which should be shown
			Use ui.columnsToHide to get array of columns identifiers which should be hidden
			*/
			columnChooserButtonApplyClick: "columnChooserButtonApplyClick",
			/* event fired when button Reset in column chooser is clicked
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridHiding widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the Column Chooser element. This is a jQuery object.
			*/
			columnChooserButtonResetClick: "columnChooserButtonResetClick"
		},
		renderInFeatureChooser: true,
		_headerHashDataKey: "igGrid-hiding-hash",
		/*jscs:enable*/
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
			/* destroys the hiding widget */
			var fc, self = this,
				modalDialog = this.grid.container().find("#" + this.grid.id() + "_hiding_modalDialog");
			this.grid.element.unbind(".hiding");
			this.grid.headersTable()
				.find("> thead > tr > th")
				.not("[data-skip=true]")
				.each(function () {
					var th = $(this);
					th.find("a[data-hiding-indicator=true]").remove();

					self._clearHiddenColumnIndicator(true, th);
					self._clearHiddenColumnIndicator(false, th);
				});
			/* remove dropdown elements */
			$("div[data-hiding-inddropdown='" + this.grid.id() + "']").remove();
			/* remove the column chooser element */
			this._detachEvents();
			modalDialog.igGridModalDialog("getCaptionButtonContainer");
			modalDialog.remove();
			fc = this.grid.element.data("igGridFeatureChooser");
			if (fc && this.renderInFeatureChooser) {
				fc._removeFeature("Hiding");
				fc._removeFeature("ColumnChooser");
			}
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		showColumnChooser: function () {
			/* Shows the Column Chooser dialog. If it is visible the method does nothing. */
			this._openColumnChooser(false);
		},
		hideColumnChooser: function () {
			/* Hides the Column Chooser dialog. If it is not visible the method does nothing. */
			this._closeColumnChooser(false);
		},
		showColumn: function (column, isMultiColumnHeader, callback) {
			/* Shows a hidden column. If the column is not hidden the method does nothing.
			Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
			paramType="number|string" An identifier for the column. If a number is provided it will be used as a column index else if a strings is provided it will be used as a column key.
			paramType="bool" optional="true" If it is true then the column is of type multicolumnheader. An identifier for the column should be of type string.
			paramType="function" optional="true" Specifies a custom function to be called when the column(s) is shown(optional)
			*/
			var i, colToShow, len, arrIds = [];
			if (isMultiColumnHeader === true) {
				colToShow = this.grid._getMultiHeaderColumnById(column);
				if (colToShow === null) {
					return;
				}
				len = colToShow.children.length;
				for (i = 0; i < len; i++) {
					arrIds.push(colToShow.children[ i ].key);
				}
				this.showMultiColumns(arrIds, callback);
				colToShow.hidden = false;
				colToShow.colspan = len;
			} else {
				this.grid.showColumn(column, callback);
			}
		},
		hideColumn: function (column, isMultiColumnHeader, callback) {
			/* Hides a visible column. If the column is hidden the method does nothing.
			Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
			paramType="number|string" An identifier for the column. If a number is provided it will be used as a column index else if a strings is provided it will be used as a column key.
			paramType="bool" optional="true" If it is true then the column is of type multicolumnheader. An identifier for the column should be of type string.
			paramType="function" optional="true" Specifies a custom function to be called when the column is hidden(optional)
			*/
			var i, colToHide, len, arrIds = [];
			if (isMultiColumnHeader === true) {
				colToHide = this.grid._getMultiHeaderColumnById(column);
				if (colToHide === null) {
					return;
				}
				len = colToHide.children.length;
				for (i = 0; i < len; i++) {
					arrIds.push(colToHide.children[ i ].key);
				}
				this.hideMultiColumns(arrIds, callback);
				colToHide.hidden = true;
				colToHide.colspan = 0;
			} else {
				this.grid.hideColumn(column, callback);
			}
		},
		hideMultiColumns: function (columns, callback) {
			/* Hides visible columns specified by the array. If the column is hidden the method does nothing.
			Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
			paramType="array" An array of identifiers for the columns. If a number is provided it will be used as a column index else if a strings is provided it will be used as a column key.
			paramType="function" optional="true" Specifies a custom function to be called when all columns are hidden(optional)
			*/
			this._setHiddenMultipleColumns([], columns, false, callback);
		},
		showMultiColumns: function (columns, callback) {
			/* Show visible columns specified by the array. If the column is shown the method does nothing.
			Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
			paramType="array" An array of identifiers for the columns. If a number is provided it will be used as a column index else if a strings is provided it will be used as a column key.
			paramType="function" optional="true" Specifies a custom function to be called when all columns are shown(optional)
			*/
			this._setHiddenMultipleColumns(columns, [], false, callback);
		},
		_showColumnFromUI: function (column) {
			var columnKey,
				columnIndex,
				noCancel, self = this;

			if (typeof column === "number") {
				columnIndex = column;
			} else {
				columnKey = column;
			}
			noCancel = this._trigger(this.events.columnShowing, null, {
				owner: this,
				columnKey: columnKey,
				columnIndex: columnIndex
			});
			if (!this.grid._isShowingAllowed([ column ])) {
				this._trigger(this.events.columnShowingRefused, null, {
					owner: this,
					columnKeys: [ columnKey ]
				});
				return false;
			}
			if (noCancel) {
				/* M.H. 16 June 2014 Fix for bug #173154: columnHidden/columnShown events are triggered before the column is actually detached/attached to the DOM */
				self.grid._loadingIndicator.show();
				setTimeout(function () {
					self.grid._setHidden(column, false);
					self._trigger(self.events.columnShown, null, {
						owner: self,
						columnKey: columnKey,
						columnIndex: columnIndex
					});
					self.grid._loadingIndicator.hide();
				}, 0);
			}
			return noCancel;
		},
		_setHiddenMultipleColumns: function (columnsToShow, columnsToHide, callEvents, callback) {
			/* first it should be processed columnsToShow then columnsToHide */
			/* if columnsToHide is processed first then it will not be hidden all of them, because it will not hide the column because it will be shown at least 1 column */
			var self = this,
				grid = self.grid,
				columnKey,
				columnIndex;
			grid._loadingIndicator.show();
			setTimeout(function () {
				var i, column, noCancel, cols = [], c;
				for (i = 0; i < columnsToShow.length; i++) {
					column = columnsToShow[ i ];
					if (typeof column === "number") {
						columnIndex = column;
					} else {
						columnKey = column;
					}
					noCancel = true;
					if (callEvents) {
						noCancel = self._trigger(self.events.columnShowing, null, {
							owner: self,
							columnKey: columnKey,
							columnIndex: columnIndex
						});
					}
					if (noCancel) {
						c = self.grid._setHidden(column, false);
						if (callEvents) {
							self._trigger(self.events.columnShown, null, {
								owner: self,
								columnKey: columnKey,
								columnIndex: columnIndex
							});
						}
						if (c) {
							cols.push(c);
						}
					}
				}
				for (i = 0; i < columnsToHide.length; i++) {
					column = columnsToHide[ i ];
					if (grid._visibleColumns().length === 1) {
						/* hiding the last column through the UI should be forbidden */
						break;
					}
					if (typeof column === "number") {
						columnIndex = column;
					} else {
						columnKey = column;
					}
					noCancel = true;
					if (callEvents) {
						noCancel = self._trigger(self.events.columnHiding, null, {
							owner: self,
							columnKey: columnKey,
							columnIndex: columnIndex
						});
					}
					if (noCancel) {
						c = grid._setHidden(column, true);
						if (callEvents) {
							self._trigger(self.events.columnHidden, null, {
								owner: self,
								columnKey: columnKey,
								columnIndex: columnIndex
							});
						}
						if (c) {
							cols.push(c);
						}
					}
				}
				/* M.H. 24 Nov 2015 Fix for bug 206738: Length of the data rows when hiding a multicolumn header in igGrid does not get updated in Mozilla Firefox */
				if (!grid._allColumnWidthsInPixels && $.ig.util.isFF) {
					grid._refreshUI();
				}
				grid._loadingIndicator.hide();
				if (callback) {
					$.ig.util.invokeCallback(callback, [ cols, columnsToShow.length === 0 ]);
				}
			}, 0);
		},
		_hideMultiColumnFromUI: function (id) {
			var i, self = this, grid = this.grid,
				column,
				children,
				columnKey,
				childrenLength,
				noCancel,
				visibleChildrenLength = 0,
				columnKeysToHide = [],
				columnKeysHidden = [];

			if (grid._visibleColumns().length === 1) {
				/* hiding the last column through the UI should be forbidden */
				return false;
			}
			/* M.H. 4 July 2012 Fix for bug #116254 - column indentifier should be string */
			column = grid._getMultiHeaderColumnById(id);
			if (column === null) {
				return;
			}
			children = column.children;
			childrenLength = children.length;
			for (i = 0; i < childrenLength; i++) {
				if (!children[ i ].hidden) {
					visibleChildrenLength++;
				}
			}
			if (grid._visibleColumns().length - visibleChildrenLength < 1) {
				/* hiding the last column through the UI should be forbidden */
				return false;
			}
			for (i = 0; i < childrenLength; i++) {
				columnKeysToHide.push(children[ i ].key);
			}
			if (!grid._isHidingAllowed(columnKeysToHide)) {
				this._trigger(this.events.columnHidingRefused, null, {
					owner: this,
					columnKeys: columnKeysToHide
				});
				return false;
			}
			noCancel = this._trigger(this.events.multiColumnHiding, null, {
				owner: this,
				columnKeys: columnKeysToHide
			});
			if (noCancel) {
				grid._loadingIndicator.show();
				setTimeout(function () {
					for (i = 0; i < childrenLength; i++) {
						columnKey = columnKeysToHide[ i ];
						noCancel = self._trigger(self.events.columnHiding, null, {
							owner: self,
							columnKey: columnKey,
							columnIndex: undefined
						});
						if (noCancel) {
							if (children[ i ].allowHiding && !children[ i ].hidden) { //
								grid._setHidden(columnKey, true);
								columnKeysHidden.push(columnKey);
								self._trigger(self.events.columnHidden, null, {
									owner: self,
									columnKey: columnKey,
									columnIndex: undefined
								});
							}
						}
					}
					/* M.H. 17 Sep 2015 Fix for bug 206738: Length of the data rows when hiding a multicolumn header in igGrid does not get updated in Mozilla Firefox */
					if (!grid._allColumnWidthsInPixels && $.ig.util.isFF) {
						grid._refreshUI();
					}
					column.hidden = true;
					column.colspan = 0;
					grid._loadingIndicator.hide();
					self._trigger(self.events.multiColumnHidden, null, {
						owner: self,
						columnKeys: columnKeysHidden
					});
				}, 0);
			}
		},
		_fixedColumnsChanged: function () {
			this._renderHiddenColumnIndicators();
		},
		_hideColumnFromUI: function (column) {
			var self = this,
				columnKey,
				columnIndex,
				noCancel;

			if (self.grid._visibleColumns().length === 1) {
				/* hiding the last column through the UI should be forbidden */
				return false;
			}
			if (typeof column === "number") {
				columnIndex = column;
			} else {
				columnKey = column;
			}
			if (!this.grid._isHidingAllowed([ column ])) {
				this._trigger(this.events.columnHidingRefused, null, {
					owner: this,
					columnKeys: [ columnKey ]
				});
				return;
			}
			noCancel = this._trigger(this.events.columnHiding, null, {
				owner: this,
				columnKey: columnKey,
				columnIndex: columnIndex
			});
			if (noCancel) {
				/* M.H. 16 June 2014 Fix for bug #173154: columnHidden/columnShown events are triggered before the column is actually detached/attached to the DOM */
				self.grid._loadingIndicator.show();
				setTimeout(function () {
					self.grid._setHidden(column, true);
					self._trigger(self.events.columnHidden, null, {
						owner: self,
						columnKey: columnKey,
						columnIndex: columnIndex
					});
					/* M.H. 15 Aug 2013 Fix for bug #149344: When the grid has multi-column headers, the columns do not have widths and the height is defined hiding a column misaligns the grid. */
					if (!self.grid._allColumnWidthsInPixels && $.ig.util.isFF) {
						self.grid._refreshUI();
					}
					self.grid._loadingIndicator.hide();
				}, 0);
			}
			return noCancel;
		},
		_headerCellRendered: function (event, ui) {
			/* prevent handling of other grids' events in the case of hierarchical grid */
			if (ui.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			if (ui.isMultiColumnHeader === true) {
				return;
			}
			var i,
				col,
				featureChooserInstance = this.grid.element.data("igGridFeatureChooser");
			if (!this._featureChooserInitialized && featureChooserInstance && this.renderInFeatureChooser) {
				this._featureChooserInitialized = true;
				this._hidingIconColumnKeys = [];
				for (i = 0; i < this.grid.options.columns.length; i++) {
					col = this.grid.options.columns[ i ];
					if (this._getColumnSettingsByIndex(i).allowHiding) {
						if (featureChooserInstance._shouldRenderInFeatureChooser(col.key) === true) {
							featureChooserInstance._renderInFeatureChooser(
								col.key,
								{
									name: "Hiding",
									text: this.options.columnHideText,
									iconClass: this.css.featureChooserHidingIcon,
									/* M.H. 10 Oct. 2011 Fix for bug #89761 - hiding should not be selectable - on click item should not change its state */
									isSelectable: false,
									isSelected: false, //if the column is hidden by default the feature chooser for this column will not be visible too
									method: $.proxy(this._featureChooserHide, this),
									updateOnClickAll: false,
									groupName: "click",
									groupOrder: 2,
									order: 2
								}
							);
							featureChooserInstance._renderInFeatureChooser(
								col.key,
								{
									name: "ColumnChooser",
									text: this.options.columnChooserDisplayText,
									iconClass: this.css.featureChooserModalDialogIcon,
									method: $.proxy(this._featureChooserColumnChooser, this),
									groupName: "modaldialog",
									groupOrder: 3,
									order: 1
								}
							);
						} else {
							this._hidingIconColumnKeys.push(col.key);
						}
					}
				}
			}
		},
		_featureChooserHide: function (event, columnKey) {
			this._closeColumnChooser(true);
			/* we can't show a column from here so isSelected doesn't matter */
			this._hideColumnFromUI(columnKey);
		},
		_featureChooserColumnChooser: function () {
			this._openColumnChooser(true);
		},
		_columnMap: function () {
			var self = this;
			/* function used by the FeatureChooser */
			return $.map(this.grid.options.columns, function (col, index) {
				return { "columnKey": col.key, "enabled": self._getColumnSettingsByIndex(index).allowHiding };
			});
		},
		_renderHidingColumnIcon: function (th, columnKey, isMultiColumnHeader) {
			var self = this, $a,
				indicatorContainer = th.find(".ui-iggrid-indicatorcontainer");
			if (indicatorContainer.length === 0) {
				indicatorContainer = $("<div></div>").addClass("ui-iggrid-indicatorcontainer").appendTo(th);
			}
			/* M.H. 9 Sep 2015 Fix for bug 202369: It is not possible to use hiding with keyboard navigation */
			$a = $("<a></a>")
					.css("display", "inline")
					.attr("data-hiding-indicator", "true")
					.attr("href", "#")
					.attr("title", $.ig.GridHiding.locale.hideColumnIconTooltip)
					.bind("keydown.hiding", function (event) {
						if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
							if (isMultiColumnHeader) {
								self._hideMultiColumnFromUI(th.attr("data-mch-id"));
							} else {
								self._hideColumnFromUI(columnKey);
							}
							event.preventDefault();
							event.stopPropagation();
						}
					}).appendTo(indicatorContainer);
			$("<span>&laquo;</span>")
				.addClass(this.css.hidingHeaderIndicator)
				.appendTo($a)
				/* .wrap($a) */
				.bind("click.hiding",
					function (event) {
						/*L.A. 19 March 2012. Fixed bug 105173 Showing a hidden column makes the header"s hover state stay forever */
						$(this).parent().mouseout();
						self._closeColumnChooser(true);
						if (isMultiColumnHeader) {
							self._hideMultiColumnFromUI(th.attr("data-mch-id"));
						} else {
							self._hideColumnFromUI(columnKey);
						}
						event.preventDefault();
						event.stopPropagation();
					}
				);
			/* M.H. 19 Sep 2013 Fix for bug #152681: The header text and feature icon are rendered on two rows in the header. */
			this.grid._enableHeaderCellFeature(th);
		},
		_populateMultiColumnHeadersLevel0: function () {
			var i, j, self = this, cols = this.grid._oldCols,
				colsLength = cols.length, ths = $(this.grid._headerCells),
				level0 = [], level0Length, visibleColumns = this.grid._visibleColumns();
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
				cs = self._getColumnSettingsByKey(col.key);
				if (cs.allowHiding) {
					for (i = 0; i < level0.length; i++) {
						if (level0[ i ].key === col.key) {
							break;
						}
					}
					if (i < level0Length) {
						level0[ i ].allowHiding = true;
						level0[ i ].visibleIndex = index;
						level0[ i ].settings = cs;
					}
					th.col = level0[ i ];
				}
			});
		},
		_renderHidingIcons: function () {
			var thsMultiHeader, self = this,
				ths;
			/* M.H. 11 June 2012 Fix for bug #113740 */
			if (self.grid._isMultiColumnGrid === true) {
				thsMultiHeader = this.grid.headersTable()
					.find("> thead > tr th")
					.not("[data-skip=true]").not("[data-isheadercell=true]");
				this._populateMultiColumnHeadersLevel0();
				/* M.H. 8 Dec 2014 Fix for bug #186134: After setting allowHiding to false on MultiColumnHeader the hide icon still appears visible. */
				thsMultiHeader.each(function () {
					var th = $(this), id = th.attr("id"), col, allowHiding = true;
					if (id) {
						id = id.replace(self.grid.id() + "_", "");
						if (id) {
							col = self._getColumnSettings(null, id);
							if (col && col.allowHiding === false) {
								allowHiding = false;
							}
						}
					}
					/* check if all children have allowHiding to false to set parent allowHiding to false */
					/*
					if (allowHiding) {
						children = self.grid._getMultiHeaderColumnById(th.attr("data-mch-id"));
						if (children) {
							children = children.children;
							if (children) {
								for (i = 0; i < children.length; i++) {
									col = self._getColumnSettings(null, children[ i ].key);
									if (col && col.allowHiding === false) {
										counter++;
									}
								}
								if (counter === children.length) {
									allowHiding = false;
								}
							}
						}
					}
					*/
					if (allowHiding) {
					self._renderHidingColumnIcon(th, null, true);
					}
				});
				/* M.H. 10 Aug 2012 Fix for bug #118771 */
				ths = $(this.grid._headerCells);//$($.grep(this.grid._headerCells, function (elem) {return $(elem).is(":visible");}));//this.grid.headersTable().find("> thead > tr th[data-isheadercell=true]").not("[data-skip=true]");
			} else {
				ths = this.grid.headersTable().find("> thead > tr > th").not("[data-skip=true]");
			}
			/* clear the old hiding icons */
			ths.find("a[data-hiding-indicator=true]").remove();
			ths.each(function (index) {
				var th = $(this),
					visibleColumns = self.grid._visibleColumns(),
					visibleIndex,
					columnKey;
				if (self.grid.options.virtualizationMode === "fixed" &&
					(self.grid.options.virtualization === true ||
					self.grid.options.columnVirtualization === true)) {
					visibleIndex = (self.grid._startColIndex || 0) + index;
				} else {
					visibleIndex = index;
				}
				columnKey = visibleColumns[ visibleIndex ].key;
				if ($.inArray(columnKey, self._hidingIconColumnKeys) !== -1) {
					self._renderHidingColumnIcon(th, columnKey);
				}
			});
		},
		_headerRendered: function (event, ui) {
			//prevent handling of other grids' events in the case of hierarchical grid
			if (ui.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			this._renderHidingIcons();
			this._renderColumnChooser();
		},
		_columnChooserButtonOKClick: function () {
			var self = this, changedColumns = self._columnsToHide,
				columnsToHide = [], columnsToShow = [], noCancel,
				modalDialog = self.grid.container().find("#" + this.grid.id() + "_hiding_modalDialog");
			$.each(this.grid.options.columns, function (columnIndex, column) {
				var cs, columnIdentifier, col;
				cs = self._getColumnSettingsByIndex(columnIndex);
				if (!cs.allowHiding) {
					return;
				}
				/* if we use _hideColumnFromUI and _showColumnFromUI this will allow us to hide all columns because hiding and showing columns from framework is made async and this.grid._visibleColumns() is not updated properly */
				columnIdentifier = cs.columnKey || cs.columnIndex || column.key;
				col = changedColumns[ columnIdentifier ];
				/* M.H. 26 March 2012 Fix for bug #106157 */
				if (col !== null && col !== undefined) {
					if (col) {
						/* M.H. 24 Sep 2012 Fix for bug #122410 */
						if (column.hidden === false) {
							columnsToHide.push(columnIdentifier);
						}
						/*self._hideColumnFromUI(columnIdentifier); */
					} else {
						/* M.H. 24 Sep 2012 Fix for bug #122410 */
						if (column.hidden === true) {
							columnsToShow.push(columnIdentifier);
						}
						/*self._showColumnFromUI(columnIdentifier); */
					}
				}
			});
			noCancel = this._trigger(this.events.columnChooserButtonApplyClick, null, {
				columnChooserElement: modalDialog,
				owner: this,
				columnsToHide: columnsToHide,
				columnsToShow: columnsToShow
			});
			if (!this.grid._isHidingAllowed(columnsToHide)) {
				this._trigger(this.events.columnHidingRefused, null, {
					owner: this, columnKeys: columnsToHide
				});
				return;
			}
			/* M.H. 25 Feb 2014 Fix for bug #165105: When the width of the fixed columns plus the width of the hidden fixed columns is bigger than grid"s defined width showing the hidden columns via the column chooser makes the fixed area as big as the grid itself. */
			if (!this.grid._isShowingAllowed(columnsToShow)) {
				this._trigger(this.events.columnShowingRefused, null, {
					owner: this, columnKeys: columnsToShow
				});
				return;
			}
			if (noCancel) {
				self._setHiddenMultipleColumns(columnsToShow, columnsToHide, true);
				self.grid.container()
					.find("#" + this.grid.id() + "_hiding_modalDialog")
					.igGridModalDialog("closeModalDialog", true);
			}
		},
		isToRenderButtonReset: function () {
			/* Gets whether the reset button in the column chooser dialog is to be rendered or not. */
			/* M.H. 29 March 2012 Fix for bug #107039 */
			var self = this,
				columnsToHide = self._columnsToHide,
				result = false;
			$.each(this.grid.options.columns, function (columnIndex, column) {
				var cs, columnIdentifier, col, isHidden;
				cs = self._getColumnSettingsByIndex(columnIndex);
				if (cs && !cs.allowHiding) {
					return true;
				}
				/* if we use _hideColumnFromUI and _showColumnFromUI this will allow us to hide all columns because hiding and showing columns from framework is made async and this.grid._visibleColumns() is not updated properly */
				columnIdentifier = cs.columnKey || cs.columnIndex || column.key;
				col = columnsToHide[ columnIdentifier ];
				/* M.H. 15 Sep 2012 Fix for bug #105514 */
				if (column._initiallyHidden === true) {
					isHidden = true;
				} else {
					isHidden = cs.hidden;
				}
				/* M.H. 26 March 2012 Fix for bug #106157 */
				if (col !== null && col !== undefined) {
					if (col !== isHidden) {
						result = true;
						return false;//break;
					}
				}
			});
			return result;
		},
		/* M.H. 3 April 2012 Fix for bug #106792 */
		_isVisibleColumnsNotAllowHiding: function () {
			var self = this, res = false;
			$.each(this.grid.options.columns, function (columnIndex, column) {
				var cs;
				cs = self._getColumnSettingsByIndex(columnIndex);
				if (cs && !cs.allowHiding && !column.hidden) {
					res = true;
					return false;
				}
			});
			return res;
		},
		_columnChooserOpening: function () {
			var $ul, noCancel, $content,
				self = this,
				o = this.options,
				css = this.css,
				countColumnsAllowHiding = 0,
				modalDialog = this.grid.container()
				.find("#" + this.grid.id() + "_hiding_modalDialog");

			noCancel = this._trigger(this.events.columnChooserOpening, null, {
				columnChooserElement: modalDialog, owner: this
			});
			if (noCancel) {
				noCancel = this._trigger(this.events.columnChooserContentsRendering, null, {
					columnChooserElement: modalDialog, owner: this
				});
				if (noCancel) {
					this.removeColumnChooserResetButton();
					$content = modalDialog.igGridModalDialog("getContent");
					$content.empty();
					$ul = $("<ul></ul>")
						.addClass(css.columnChooserListItems)
						.appendTo($content);
					this._columnsToHide = {};
					$.each(this.grid.options.columns, function (columnIndex, column) {
						var cs, columnIdentifier, $a, $li;
						cs = self._getColumnSettingsByIndex(columnIndex);
						if (!cs.allowHiding) {
							return;
						}
						countColumnsAllowHiding++;
						/* use the key or index that is in the column settings and if none is present use the column.key */
						/* by doing this we will trigger the event with the identifier that was used for matching the settings */
						columnIdentifier = cs.columnKey || cs.columnIndex || column.key;
						$li = $("<li></li>")
							.attr("id", self.grid.element[ 0 ].id + "_" + columnIdentifier + "_columnchooser_li")
							.addClass(css.columnChooserItem)
							.append("<span class='" + css.columnChooserHideButton +
									"'><a href='#'></a></span><span class='" + css.columnChooserItemText + "'>" +
									column.headerText + "</span>");
						$li.appendTo($ul);
						$a = $li.find("a:first");
						if (o.columnChooserHideOnClick === true) {
							$li
								.bind("click.hiding", function (event) {
									var isHidden;

									if ($a.attr("ishidden") === "1") {
										noCancel = self._showColumnFromUI(columnIdentifier);
										isHidden = false;
									} else {
										noCancel = self._hideColumnFromUI(columnIdentifier);
										isHidden = true;
									}
									if (noCancel) {
										self._renderColumnChooserHideButton($a, isHidden);
									}
									event.preventDefault();
									event.stopPropagation();
									return false;
								});
						} else {
							$li
								.bind("click.hiding", function (event) {
									var isHidden, key, c;
									if ($a.attr("ishidden") === "1") {
										isHidden = false;
									} else {
										isHidden = true;
									}
									/* we should check whether it is possible to hide column when it is only one visible column in fixed/unfixed area */
									if (isHidden && self.grid.hasFixedColumns()) {
										c = [];
										for (key in self._columnsToHide) {
											if (self._columnsToHide.hasOwnProperty(key) && self._columnsToHide[ key ]) {
												c.push(key);
											}
										}
										c.push(columnIdentifier);
										if (!self.grid._isHidingAllowed(c)) {
											return;
										}
									}
									/* M.H. 26 March 2012 Fix for bug #106089 */
									/* M.H. 3 April 2012 Fix for bug #106792 */
									if (isHidden &&
											!self._isVisibleColumnsNotAllowHiding() &&
											$ul.find("a[ishidden=1]").length === countColumnsAllowHiding - 1) {
										return;
									}
									self._columnsToHide[ columnIdentifier ] = isHidden;
									self._renderColumnChooserHideButton($a, isHidden);
									if (self.isToRenderButtonReset()) {
										/* M.H. 29 March 2012 Fix for bug #107039 */
										self.renderColumnChooserResetButton();
									} else {
										self.removeColumnChooserResetButton();
									}
									event.preventDefault();
									event.stopPropagation();
									return false;
								});
						}
						self._columnsToHide[ columnIdentifier ] = column.hidden;
						self._renderColumnChooserHideButton($a, column.hidden);
					});
					this._trigger(this.events.columnChooserContentsRendered, null, {
						columnChooserElement: modalDialog, owner: this
					});
					/* M.H. 29 March 2012 Fix for bug #107039 */
					if (o.columnChooserHideOnClick === false && self.isToRenderButtonReset()) {
						self.renderColumnChooserResetButton();
					}
				}
				this._trigger(this.events.columnChooserOpened, null, {
					columnChooserElement: modalDialog, owner: this
				});
			}
			return noCancel;
		},
		resetHidingColumnChooser: function () {
			/* Reset hidden/shown column to initial state of dialog(when it is opened) */
			var self = this;
			$.each(this.grid.options.columns, function (columnIndex, column) {
				var cs, columnIdentifier, isToHide;
				cs = self._getColumnSettingsByIndex(columnIndex);
				if (!cs) {
					return true;
				}
				if (!cs.allowHiding) {
					return true;
				}
				isToHide = cs.hidden;
				/* M.H. 15 Sep 2012 Fix for bug #105514 */
				if (column._initiallyHidden === true) {
					isToHide = true;
				}
				/* use the key or index that is in the column settings and if none is pressent use the column.key */
				/* by doing this we will trigger the event with the identificator that was used for matching the settings */
				columnIdentifier = cs.columnKey || cs.columnIndex || column.key;
				/* M.H. 29 March 2012 Fix for bug #107039 */
				self._columnsToHide[ columnIdentifier ] = isToHide;
				self._renderColumnChooserHideButton(
					self.grid.container()
					.find("#" + self.grid.id() + "_" + columnIdentifier + "_columnchooser_li a:first"),
					isToHide
				);
			});
			self.removeColumnChooserResetButton();
		},
		renderColumnChooserResetButton: function () {
			/* Renders the Reset button in the Column Chooser dialog. */

			/* M.H. 29 March 2012 Fix for bug #107039 */
			var o = this.options,
				self = this,
				resetButtonId = this.grid.id() + "_hiding_modalDialog_reset_button",
				modalDialog = this.grid.container().find("#" + this.grid.id() + "_hiding_modalDialog"),
				$captionButtonContainer,
				$resetButton;
			if (this.grid.container().find("#" + resetButtonId).length === 0) {
				$captionButtonContainer = modalDialog.igGridModalDialog("getCaptionButtonContainer");
				$resetButton = $("<button></button>")
					.attr("id", resetButtonId)
					.appendTo($captionButtonContainer);
				$resetButton.igButton({
					labelText: o.columnChooserResetButtonLabel,
					click: function (e) {
						self._trigger(self.events.columnChooserButtonResetClick, e, {
							columnChooserElement: modalDialog, owner: self
						});
						self.resetHidingColumnChooser();
					}
				});
			}
		},
		removeColumnChooserResetButton: function () {
			/* Remove Reset button in column chooser modal dialog*/
			this.grid.container().find("#" + this.grid.id() + "_hiding_modalDialog_reset_button").remove();
		},
		_renderColumnChooserHideButton: function ($a, isHidden) {
			var text, attrIsHidden, css = this.css, $li = $a.closest("li");
			if (isHidden === false) {
				attrIsHidden = "0";
				text = this.options.columnChooserHideText;
				$li.removeClass(css.columnChooserItemHidden);
			} else {
				attrIsHidden = "1";
				text = this.options.columnChooserShowText;
				$li.addClass(css.columnChooserItemHidden);
			}
			$a.attr("ishidden", attrIsHidden).text(text);
		},
		_renderColumnChooser: function () {
			/* M.H. 20 Feb 2013 Fix for bug #132371: Hierarchical grid - can not hide a column on a child layout */
			this.grid.container().find("#" + this.grid.id() + "_hiding_modalDialog").remove();
			var $buttonOK,
				self = this,
				o = this.options,
				$captionButtonContainer,
				containment = this.grid._rootContainer(),
				modalDialog;
			if (this.options.columnChooserContainment !== "owner") {
				containment = "window";
			}
			modalDialog = $("<div></div>")
					.appendTo(this.grid._rootContainer())
					.attr("id", this.grid.id() + "_hiding_modalDialog");
			modalDialog.igGridModalDialog({
				/* M.H. 9 March 2012 Fix for bug #103978 */
				/* M.H. 26 March 2012 Fix for bug #106204 */
				modalDialogWidth: o.columnChooserWidth,
				modalDialogHeight: o.columnChooserHeight,
				modalDialogCaptionText: o.columnChooserCaptionText,
				buttonApplyText: o.columnChooserButtonApplyText,
				buttonCancelText: o.columnChooserButtonCancelText,
				containment: containment,
				/* M.H. 8 May 2012 Fix for bug #110344 */
				gridContainer: this.grid.container(),
				renderFooterButtons: !o.columnChooserHideOnClick,
				animationDuration: o.columnChooserAnimationDuration,
				modalDialogOpening: function (event, args) {
					return self._columnChooserOpening(event, args);
				},
				modalDialogMoving: function (e, ui) {
					self._trigger(self.events.columnChooserMoving, null,
						{
							/* M.H. 29 March 2012 Fix for bug #107140 */
							columnChooserElement: $(e.target),
							owner: self,
							originalPosition: ui.originalPosition,
							position: ui.position
						});
				},
				modalDialogClosing: function () {
					return self._trigger(self.events.columnChooserClosing, null, {
						columnChooserElement: modalDialog, owner: self
					});
				},
				modalDialogClosed: function () {
					self._trigger(self.events.columnChooserClosed, null, {
						columnChooserElement: modalDialog, owner: self
					});
				}
			});
			if (!o.columnChooserHideOnClick) {
				$buttonOK = this.grid.container()
					.find("#" + this.grid.id() + "_hiding_modalDialog_footer_buttonok");
				$buttonOK
					.bind("igbuttonclick", function (e) {
					self._columnChooserButtonOKClick(e); e.preventDefault();
				});
			} else {
				$captionButtonContainer = modalDialog.igGridModalDialog("getCaptionButtonContainer");
				$("<span></span>")
					.bind("click.hiding", function (event) {
						modalDialog.igGridModalDialog("closeModalDialog", true);
						event.preventDefault();
						event.stopPropagation();
						return false;
					})
					.addClass("ui-icon ui-icon-closethick")
					.appendTo(
						$("<a></a>")
							.appendTo($captionButtonContainer)
							.attr("title", $.ig.GridHiding.locale.columnChooserCloseButtonTooltip)
							.attr("href", "#")
							.attr("role", "button")
							.addClass("ui-dialog-titlebar-close ui-corner-all")
					);
			}
		},
		_hidingFinished: function () {
			if (this.options.virtualization === true && this.options.virtualizationMode === "fixed") {
				return;
			}
			if (this.options.columnVirtualization === true) {
				return;
			}
			this._renderHiddenColumnIndicators();
		},
		_virtualHorizontalScroll: function () {
			this._renderHiddenColumnIndicators();
			this._renderHidingIcons();
		},
		_getIndicators: function (indicators, isFixed) {
			var i, col, columns = this.grid.options.columns, columnsLength = columns.length,
				visibleIndex = 0, fixed;

			for (i = 0; i < columnsLength; i++) {
				col = columns[ i ];
				fixed = (col.fixed === true);
				if (fixed !== isFixed) {
					continue;
				}
				if (col.hidden) {
					if (this._getColumnSettingsByIndex(i).allowHiding) {
						/* add an indicator for this group of hidden columns */
						if (indicators[ visibleIndex ] === undefined) {
							indicators[ visibleIndex ] = [];
						}
						indicators[ visibleIndex ].push(col.key);
					}
				} else {
					visibleIndex++;
				}
			}
			return visibleIndex;
		},
		_renderHiddenColumnIndicators: function () {
			var self = this, ths, visibleIndex = 0, indicators = [], oldDropDowns, fHCells, ufHCells,
				hasFixedColumns = this.grid.hasFixedColumns(), fixedIndicators = [];

			visibleIndex = this._getIndicators(indicators, false);
			if (hasFixedColumns) {
				this._getIndicators(fixedIndicators, true);
			}
			/* remove the old dropdowns with a setTimeout so that we give the animation time to finish */
			oldDropDowns = this.grid.container()
				.children("div[data-hiding-inddropdown='" + self.grid.id() + "']");
			if (oldDropDowns.length) {
				setTimeout(function () {
					oldDropDowns.remove();
				}, this.options.dropDownAnimationDuration * 2 + 1);
			}
			/* all columns are hidden */
			if (visibleIndex === 0) {
				this.grid.headersTable().find("> thead > tr > th[data-hiddenreplacement=true]")
					.each(function () {
						var a = self._renderHiddenColumnIndicator(true, $(this));
						self._bindDropDownToIndicator(a.find("span"), indicators[ 0 ]);
					});
			} else {
				ths = this.grid._isMultiColumnGrid ? $(this.grid._headerCells) :
									this.grid.headersTable().find("> thead > tr > th").not("[data-skip=true]");
				/* when there is at least one fixed column then first we render indicators for unfixed columns, then for the fixed one */
				if (hasFixedColumns) {
					if (this.grid._isMultiColumnGrid) {
						fHCells = $();
						ufHCells = $();
						$.each(this.grid._headerCells, function (index, cell) {
							if (self.grid._isFixedElement(cell)) {
								fHCells.push(cell);
							} else {
								ufHCells.push(cell);
							}
						});
						this._renderHiddenColumnIndicatorsHelper(ufHCells, indicators, false);
						this._renderHiddenColumnIndicatorsHelper(fHCells, fixedIndicators, true);
					} else {
						this._renderHiddenColumnIndicatorsHelper(ths, indicators, false);
						ths = this.grid.fixedHeadersTable().find("thead > tr > th").not("[data-skip=true]");
						this._renderHiddenColumnIndicatorsHelper(ths, fixedIndicators, true);
					}
				} else {
					this._renderHiddenColumnIndicatorsHelper(ths, indicators);
				}
			}
		},
		_renderHiddenColumnIndicatorsHelper: function (ths, indicators, isFixed) {
			var self = this;
			ths.each(function (index) {
				var th = $(this), div, visibleIndex;
				if ((self.grid.options.virtualization === true &&
					self.grid.options.virtualizationMode === "fixed") ||
					self.grid.options.columnVirtualization === true) {
					visibleIndex = self.grid._startColIndex + index;
				} else {
					visibleIndex = index;
				}
				self._clearHiddenColumnIndicator(true, th);
				self._clearHiddenColumnIndicator(false, th);
				if (indicators[ visibleIndex ] !== undefined) {
					div = self._renderHiddenColumnIndicator(true, th);
					self._bindDropDownToIndicator(div.find("span"), indicators[ visibleIndex ]);
				}
				/* the last th may have an indicator at its end if all columns on the right are hidden */
				if (visibleIndex === self.grid._visibleColumns(isFixed).length - 1) {
					if (indicators[ visibleIndex + 1 ] !== undefined) {
						div = self._renderHiddenColumnIndicator(false, th);
						self._bindDropDownToIndicator(div.find("span"), indicators[ visibleIndex + 1 ]);
					}
				}
			});
			/* M.H. 19 Feb 2016 Fix for bug 214498: A column shown by igGridHiding's showMultiColumns() method is not resizable. */
			this.grid._fireInternalEvent("_hiddenColumnIndicatorsRendered", ths);
		},
		_renderHiddenColumnIndicator: function (left, th) {
			var padding,
				margin,
				nonPaddedIndicatorsAttr,
				position,
				indicatorMarkup,
				spanMarkup,
				paddingValue,
				div,
				span;
			if (left) {
				padding = "padding-left";
				margin = "margin-left";
				position = "left";
				indicatorMarkup = "<div data-hiddencolindicator='left' />";
				spanMarkup = "<span data-nonpaddedindicator='left'></span>";
				nonPaddedIndicatorsAttr = "[data-nonpaddedindicator=left]";
			} else {
				padding = "padding-right";
				margin = "margin-right";
				position = "right";
				indicatorMarkup = "<div data-hiddencolindicator='right' />";
				spanMarkup = "<span data-nonpaddedindicator='right'></span>";
				nonPaddedIndicatorsAttr = "[data-nonpaddedindicator=right]";
			}
			/* increase padding */
			paddingValue = parseInt(th.css(padding), 10) + this.options.hiddenColumnIndicatorHeaderWidth;
			th.css(padding, paddingValue + "px");
			/* update the margin of all indicator that should not be affected by padding */
			th.find(nonPaddedIndicatorsAttr).css(margin, -paddingValue + "px");
			div = $(indicatorMarkup)
				.attr("tabindex", this.grid.options.tabIndex)
				.attr("title", this.options.hiddenColumnIndicatorTooltipText)
				.css("position", "relative")
				.css("width", "100%")
				.css("height", "0px")
				.css("top", "0px")
				.css("left", "0px")
				.prependTo(th);
			/* a = $("<a href="#" />") */
			/*	.attr("tabindex", "-1") */
			/*	//.attr("title", this.options.hiddenColumnIndicatorTooltipText) */
			/*	.prependTo(div); */
			span = $(spanMarkup)
				/* A.Y. bug #89113. */
				/* For some odd reason in IE we need to have title on the span as well. */
				.attr("title", this.options.hiddenColumnIndicatorTooltipText)
				/* M.H. 10 Sep 2013 Fix for bug #151643: When you tap on a hiding indicator feature chooser opens and then hiding dropdown on a mobile device. */
				.attr("data-skip-event", "true")
				.css("position", "absolute")
				.css(margin, -paddingValue + "px")
				.css(position, "0px")
				.css("width", this.options.hiddenColumnIndicatorHeaderWidth + "px")
				.addClass(this.css.hiddenColumnIndicator)
				.appendTo(div);
			/* A.Y. bug #107365. On IE7 there is a bug with overflow:hidden not working correctly */
			/* when we have abosulte positioned children. So we need to size the indicator to not overflow. */
			if ($.ig.util.isIE7) {
				span.css({
					"top": -parseInt(th.css("padding-top"), 10),
					"height": th.innerHeight()
				});
			}
			return div;
		},
		_clearHiddenColumnIndicator: function (left, th) {
			var padding, paddingValue, indicatorSelector, indicator;
			if (left) {
				padding = "padding-left";
				indicatorSelector = "div[data-hiddencolindicator=left]";
			} else {
				padding = "padding-right";
				indicatorSelector = "div[data-hiddencolindicator=right]";
			}
			indicator = th.find(indicatorSelector);
			if (indicator.length !== 0) {
				indicator.remove();
				/* decrease padding */
				paddingValue = parseInt(th.css(padding), 10);
				th.css(padding, (paddingValue - this.options.hiddenColumnIndicatorHeaderWidth) + "px");
			}
		},
		/* if element loses focus check whether  */
		_onBlurDDElement: function (e, button, dropDown) {
			var self = this;
			self._activeDD = null;
			clearTimeout(self._blurTimeout);
			self._blurTimeout = setTimeout(function () {
				if (!self._activeDD || self._activeDD !== dropDown) {
					self._toggleDropDown(button, dropDown, true);
				}
			}, 1);
		},
		/* if focus is on element/dropdown in active dropdown do not close */
		_onFocusDDElement: function (e, button, dropDown) {
			this._activeDD = dropDown;
		},
		_bindDropDownToIndicator: function (button, columnKeys) {
			var self = this,
				$hInd = button.closest("[data-hiddencolindicator]"),
				dropDown = this._renderDropDown(button, columnKeys);
			/* detect when to close dropdown(according to which element loses focus) */
			dropDown.find("ul,[tabindex],a").bind({
				blur: function (e) {
					self._onBlurDDElement(e, button, dropDown);
				},
				focus: function (e) {
					self._onFocusDDElement(e, button, dropDown);
				}
			});
			/* close dropdown indicator when focus is in dropdown(or inner element) and Esc is pressed */
			/* select column/open columnchooser when Enter/Spacebar are pressed */
			dropDown.bind({
				keydown: function (e) {
					var $li, keyCode = e.keyCode, $target = $(e.target);
					switch (keyCode) {
						case $.ui.keyCode.ESCAPE:
							self._toggleDropDown(button, dropDown, true);
							$hInd.focus();
							break;
						case $.ui.keyCode.ENTER:
						case $.ui.keyCode.SPACE:
							if ($target.is("li")) {
								$target.mousedown();
								e.preventDefault();// do not scroll if SPACEBAR is pressed
							}
							break;
						case $.ui.keyCode.TAB:
							$li = $target;
							if (!$li.is("li")) {
								$li = $target.closest("li");
							}
							if (!e.shiftKey) {
								if ($li.is(":last-child")) {
									$li.closest("ul").find("li:first-child").focus();
									e.preventDefault();
								}
							} else {
								if ($li.is(":first-child")) {
									$li.closest("ul").find("li:last-child").focus();
									e.preventDefault();
								}
							}
							break;
					}
				}
			});
			/* open dropdown when focus is on hiding column indicator and SPACEBAR or ENTER are pressed */
			$hInd.bind({
				keydown: function (e) {
					if (e.keyCode === $.ui.keyCode.ENTER || e.keyCode === $.ui.keyCode.SPACE) {
						self._toggleDropDown(button, dropDown, false);
						e.preventDefault();
						e.stopPropagation();
					}
				}
			});
			button.parent().bind({
				mousedown: function (e) {
					self._toggleDropDown(button, dropDown, false);
					e.preventDefault();
					e.stopPropagation();
				},
				mouseover: function () {
					if (button.attr("data-indicator-selected") !== "true") {
						button.addClass(self.css.hiddenColumnIndicatorMouseOver);
					}
				},
				mouseout: function () {
					button.removeClass(self.css.hiddenColumnIndicatorMouseOver);
				},
				mouseup: function (e) { e.preventDefault(); e.stopPropagation(); },
				click: function (e) { e.preventDefault(); e.stopPropagation(); }
			});
		},
		_renderDropDown: function (button, columnKeys) {
			var self = this, dropDown, list;
			dropDown = $("<div data-hiding-inddropdown='" + this.grid.id() + "'></div>")
				.css("position", "absolute")
				.css("display", "none")
				.addClass(this.css.hiddenColumnsDropDown)
				.appendTo(this.grid._rootContainer());
			list = $("<ul tabindex='0'></ul>")
				.addClass(this.css.hiddenColumnsDropDownList)
				.appendTo(dropDown);
			$.each(columnKeys, function (index, columnKey) {
				$("<li tabindex='0'></li>")
					.addClass(self.css.hiddenColumnsDropDownItem)
					.bind({
						mouseover: function (e) {
							$(e.currentTarget).addClass(self.css.hiddenColumnsDropDownItemHover);
						},
						mouseout: function (e) {
							$(e.currentTarget).removeClass(self.css.hiddenColumnsDropDownItemHover);
						},
						mousedown: function () {
							self._closeColumnChooser(true);
							self._showColumnFromUI(columnKey);
							self._toggleDropDown(button, dropDown, true);
						}
					})
					.append(
						$("<span></span>")
							.addClass(self.css.hiddenColumnsDropDownItemText)
							.text(self.grid.columnByKey(columnKey).headerText)
					)
					.appendTo(list);
			});
			$("<a tabindex='0'></a>")
				.appendTo($("<li></li>").appendTo(list))
				.igButton({
					labelText: this.options.columnChooserDisplayText,
					mousedown: function (event) {
						/* M.H. 26 March 2012 Fix for bug #106195 */
						if (event.target) {
							setTimeout(function () {$(event.target).removeClass("ui-state-active"); }, 0);
						}
						self._openColumnChooser(true);
						self._toggleDropDown(button, dropDown, true);
					},
					keydown: function (e) {
						if (e.keyCode === $.ui.keyCode.ENTER || e.keyCode === $.ui.keyCode.SPACE) {
							$(e.target).mousedown();
							e.preventDefault();
						}
					}
				});
			return dropDown;
		},
		_toggleDropDown: function (button, dropDown, isCalledFromBlur) {
			var th, rOffset,
				isLast,
				offset,
				left,
				isVisible = (dropDown.is(":visible") === true),
				self = this;
			if ((dropDown.data("isAnimating") === true) ||
					(isVisible === false && isCalledFromBlur === true)) {
				return;
			}
			if (!isVisible) {
				th = button.closest("th");
				isLast = button.closest("div").attr("data-hiddencolindicator") === "right";
				/* M.H. 9 Nov 2012 Fix for bug #115306 */
				offset = $.ig.util.offset(th);
				if (isLast) {
					left = offset.left + th.outerWidth() - dropDown.outerWidth();
					left = Math.max(0, left);
				} else {
					left = offset.left;
				}
				rOffset = $.ig.util.getRelativeOffset(dropDown);
				dropDown.css("top", offset.top + th.outerHeight() - rOffset.top);
				dropDown.css("left", left - rOffset.left);
			}
			dropDown.data("isAnimating", true);
			dropDown.toggle(this.options.dropDownAnimationDuration, function () {
				if (dropDown.is(":visible") === true) {
					/* M.H. 19 May 2012 Fix for bug #111585 */
					dropDown.find("ul").focus();
					button.addClass(self.css.hiddenColumnIndicatorSelected);
					button.attr("data-indicator-selected", "true");
				} else {
					button.removeClass(self.css.hiddenColumnIndicatorSelected);
					button.removeAttr("data-indicator-selected");
				}
				dropDown.data("isAnimating", false);
			});
		},
		_openColumnChooser: function () {
			var modalDialog = this.grid.container().find("#" + this.grid.id() + "_hiding_modalDialog");
			modalDialog.igGridModalDialog("openModalDialog");
		},
		_closeColumnChooser: function () {
			var modalDialog = this.grid.container().find("#" + this.grid.id() + "_hiding_modalDialog");
			modalDialog.igGridModalDialog("closeModalDialog");
		},
		_columnChooserMove: function (e, ui) {
			this._trigger(this.events.columnChooserMoving, null,
				{
					columnChooserElement: e.target,
					owner: this,
					originalPosition: ui.originalPosition,
					position: ui.position
				});
		},
		_getColumnSettings: function (index, key) {
			var i, foundByIndex;
			for (i = 0; i < this.options.columnSettings.length; i++) {
				if (this.options.columnSettings[ i ].columnKey === key) {
					return this._mergeColumnSettingsDefaults(this.options.columnSettings[ i ]);
				}
				if (this.options.columnSettings[ i ].columnIndex === index) {
					foundByIndex = this.options.columnSettings[ i ];
				}
			}
			/* fallback to the column settings found by index */
			return this._mergeColumnSettingsDefaults(foundByIndex);
		},
		_getColumnSettingsByIndex: function (index) {
			var key = this.grid.options.columns[ index ].key;
			return this._getColumnSettings(index, key);
		},
		_getColumnSettingsByKey: function (key) {
			var i;
			for (i = 0; i < this.grid.options.columns.length; i++) {
				if (this.grid.options.columns[ i ].key === key) {
					return this._getColumnSettings(i, key);
				}
			}
		},
		_mergeColumnSettingsDefaults: function (columnSettings) {
			var defaults = {
					"allowHiding": true,
					"hidden": false
				},
				copy = $.extend({}, columnSettings),
				key;
			for (key in defaults) {
				if (defaults.hasOwnProperty(key) &&
						typeof copy[ key ] !== "boolean") {
					copy[ key ] = defaults[ key ];
				}
			}
			return copy;
		},
		_columnsMoved: function () {
			var self = this;
			this.grid.headersTable()
				.find("> thead > tr > th")
				.not("[data-skip=true]")
				.each(function () {
					var th = $(this);
					self._clearHiddenColumnIndicator(true, th);
					self._clearHiddenColumnIndicator(false, th);
				});
			this._renderHiddenColumnIndicators();
		},
		_detachEvents: function () {
			if (this._headerCellRenderedHandler !== null &&
				this._headerCellRenderedHandler !== undefined) {
				this.grid.element.unbind("iggridheadercellrendered", this._headerCellRenderedHandler);
			}
			if (this._headerRenderedHandler !== null &&
				this._headerRenderedHandler !== undefined) {
				this.grid.element.unbind("iggridheaderrendered", this._headerRenderedHandler);
			}
			if (this._virtualHorizontalScrollHandler !== null &&
				this._virtualHorizontalScrollHandler !== undefined) {
				this.grid.element.unbind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			}
			if (this._columnsMovedHandler !== null && this._columnsMovedHandler !== undefined) {
				this.grid.element.unbind("iggrid_columnsmoved", this._columnsMovedHandler);
			}
		},
		_injectGrid: function (gridInstance) {
			var i, j, cs, col, children;
			this.grid = gridInstance;
			this._detachEvents();
			this._headerRenderedHandler = $.proxy(this._headerRendered, this);
			this._headerCellRenderedHandler = $.proxy(this._headerCellRendered, this);
			this._virtualHorizontalScrollHandler = $.proxy(this._virtualHorizontalScroll, this);
			this._columnsMovedHandler = $.proxy(this._columnsMoved, this);
			/* register for headerCellRendered	 */
			this.grid.element.bind("iggridheadercellrendered", this._headerCellRenderedHandler);
			/* register for headerRendered	 */
			this.grid.element.bind("iggridheaderrendered", this._headerRenderedHandler);
			this.grid.element.bind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			this.grid.element.bind("iggrid_columnsmoved", this._columnsMovedHandler);
			/* instantiate igGridFeatureChooser */
			if (this.grid.element.igGridFeatureChooser !== undefined) {
				this.grid.element.igGridFeatureChooser();
			} else {
				throw new Error($.ig.GridHiding.locale.featureChooserNotReferenced);
			}
			/* apply hidden column settings */
			for (i = 0; i < this.options.columnSettings.length; i++) {
				cs = this.options.columnSettings[ i ];
				if (typeof cs.hidden === "boolean") {
					if (typeof cs.columnKey  === "string") {
						col = this.grid.columnByKey(cs.columnKey);
					} else if (typeof cs.columnIndex === "number") {
						col = this.grid.options.columns[ cs.columnIndex ];
					}
					if (col !== undefined && col !== null) {
						/* M.H. 12 Nov 2012 Fix for bug #126924 */
						/* M.H. 12 Dec 2012 Fix for bug #129334 */
						/* M.H. 26 Mar 2013 Fix for bug #137831: If you rebind the grid after you show initially hidden column the cells of that column disappear and the other columns are shifted. */
						if (col.hidden === undefined ||
							(cs.hidden === true &&
							col.hidden !== col._initiallyHidden &&
							col._initiallyHidden !== true)) {
							col.hidden = cs.hidden;
						}
					} else if (this.grid._isMultiColumnGrid === true &&
								cs.columnKey !== null &&
								cs.columnKey !== undefined) {
						/* M.H. 12 Dec 2012 Fix for bug #129344 */
						col = this.grid._getMultiHeaderColumnById(cs.columnKey);
						if (col !== undefined && col !== null && col.children) {
							children = col.children;
							for (j = 0; j < children.length; j++) {
								/* M.H. 18 Sep 2015 Fix for bug 206806: Sorting and unsorting a column while having hidden column in multicolumn header unhides the hidden column */
								if (children[ j ].hidden === undefined ||
									(cs.hidden === true &&
										children[ j ].hidden !== children[ j ]._initiallyHidden &&
										children[ j ]._initiallyHidden !== true)) {
									children[ j ].hidden = cs.hidden;
								}
							}
						}
					}
				}
			}
		}
	});
	$.extend($.ui.igGridHiding, { version: "16.1.20161.2145" });
}(jQuery));
