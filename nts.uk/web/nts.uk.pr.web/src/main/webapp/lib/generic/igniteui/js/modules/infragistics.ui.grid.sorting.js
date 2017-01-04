/*!@license
 * Infragistics.Web.ClientUI Grid Sorting 16.1.20161.2145
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

/*global jQuery */
/*jshint -W106 */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {
	// we will define a new widget for sorting. That kind of widget is completely independent one and doesn't subclass the grid
	// it subscribes to grid events such as BodyRendering/BodyRendered, and has the grid instance (igGrid) injected into it
	// for sorting the this.element[0] will be the TR hosting the <TH>s
	$.widget("ui.igGridSorting", {
		/* igGridSorting widget -  The widget is pluggable to the element where the grid is instantiated and the actual igGrid object doesn't know about this
			the sorting widget just attaches its functionality to the grid
			it supports single and multiple column sorting, with predefined sorting settings specified on a per column basis
			the sorted column headers have spefific appearance and icons applied based on the current sorting condition applied, also the column cells can receive special styling (configurable)
			the widget also implements has full keyboard support with the TAB , SPACE/ENTER keys.
		*/
		css: {
			/*jscs:disable*/
			/* classes applied to a sortable column header */
			"sortableColumnHeader": "ui-iggrid-sortableheader ui-state-default",
			/* classes appied to a sortable header when it's active (navigated with keyboard / clicked) */
			"sortableColumnHeaderActive": "ui-iggrid-sortableheaderactive ui-state-active",
			/* classes applied to a sortable column header when it is hovered */
			"sortableColumnHeaderHover": "ui-iggrid-sortableheaderhover ui-state-hover",
			/* classes applied to the sortable column header when it has focus */
			"sortableColumnHeaderFocus": "ui-iggrid-sortableheaderfocus ui-state-focus",
			/* classes applied to a column header when it's sorted ascending */
			"ascendingColumnHeader": "ui-iggrid-colheaderasc",
			/* classes applied to a column header when it's sorted descending */
			"descendingColumnHeader": "ui-iggrid-colheaderdesc",
			/* classes applied to a column's cells when it's sorted ascending */
			"ascendingColumn": "ui-iggrid-colasc ui-state-highlight",
			/* classes applied to a column's cells when it's sorted descending */
			"descendingColumn": "ui-iggrid-coldesc ui-state-highlight",
			/* classes applied to the sorting indicator SPAN rendered inside the column header */
			"sortIndicator": "ui-iggrid-colindicator",
			/* classes applied to the ascending sorting indicator in feature chooser */
			"fcSortIndicatorAscending": "ui-iggrid-featurechooser-li-iconcontainer ui-icon ui-iggrid-icon-sort-a-z",
			/* classes applied to the descending sorting indicator in feature chooser */
			"fcSortIndicatorDescending": "ui-iggrid-featurechooser-li-iconcontainer ui-icon ui-iggrid-icon-sort-z-a",
			/* classes applied to the sorting indicator span when it's in ascending state */
			"sortIndicatorAscending": "ui-iggrid-colindicator-asc ui-icon ui-icon-arrowthick-1-n",
			/* classes applied to the sorting indicator span when it's in descending state */
			"sortIndicatorDescending": "ui-iggrid-colindicator-desc ui-icon ui-icon-arrowthick-1-s",
			/* classes applied to container of sorted columns in multiple sorting dialog */
			"dialogSortedColumns": "ui-iggrid-sorting-dialog-sortedcolumns",
			/* classes applied to container of unsorted columns in multiple sorting dialog */
			"dialogUnsortedColumns": "ui-iggrid-sorting-dialog-unsortedcolumns",
			/* classes applied to sort button for each colum in multiple sorting dialog */
			"dialogUnsortedColumnsSortByButton": "ui-iggrid-sorting-dialog-unsortedcolumns-sortbybutton",
			/* classes applied to ascending/descending button in multiple sorting dialog */
			"dialogAsdDescButton": "ui-iggrid-sorting-dialog-ascdescbutton",
			/* classes applied to sort by button in multiple sorting dialog */
			"modalDialogSortByColumn": "ui-iggrid-sorting-dialog-sortbybutton",
			/* classes applied to list item for each sorted column in multiple sorting dialog */
			"dialogSortedColumnsItem": "ui-widget-content",
			/* classes applied to list item for each unsorted column in multiple sorting dialog */
			"dialogUnsortedColumnsItem": "ui-widget-content",
			/* classes applied to ascending button for sorted column in multiple sorting dialog */
			"dialogButtonAsc": "ui-button ui-corner-all ui-button-icon-only ig-sorting-indicator",
			/* classes applied to ascending button icon for sorted column
			in multiple sorting dialog */
			"dialogButtonAscIcon": "ui-button-icon-primary ui-icon ui-icon-arrowthick-1-n",
			/* classes applied to descending button icon for sorted column in multiple sorting dialog */
			"dialogButtonDesc": "ui-button ui-corner-all ui-button-icon-only ig-sorting-indicator",
			/* classes applied to descending button icon for sorted column in multiple sorting dialog */
			"dialogButtonDescIcon": "ui-button-icon-primary ui-icon ui-icon-arrowthick-1-s",
			/* classes applied to remove sorting button for sorted column in multiple sorting dialog */
			"dialogButtonUnsort": "ui-iggrid-sorting-dialog-sortbybuttons ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-igbutton ui-widget-content ui-igbutton-remove",
			/* classes applied to remove sorting button for sorted column in multiple sorting dialog */
			"dialogButtonUnsortContainer": "ui-button-icon-primary ui-icon ui-icon-circle-close",
			/* classes applied to container which holds sorted column name in multiple sorting dialog */
			"dialogSortedColumnTextContainer": "ui-iggrid-dialog-text",
			/* classes applied to container which holds unsorted column name in multiple sorting dialog */
			"dialogItemText": "ui-iggrid-dialog-text",
			/*			"dialogButtonSlide": "ui-iggrid-sorting-dialog-button-slide ui-button ui-widget ui-state-default ui-corner-bottom ui-button-icon-only",
						"dialogButtonSlideContainer": "ui-button-icon-primary ui-icon ui-icon-triangle-1-n",
						"dialogButtonSlideUp": "ui-icon-triangle-1-s",
						"dialogSlideArea": "ui-iggrid-multiplesorting-dialog-slide ui-widget-content",
						"dialogSlideAreaContainer": "ui-iggrid-multiplesorting-dialog-slide-button-container",*/
			/* classes applied to hovered buttons - e.g. unsort button in multiple sorting dialog */
			"dialogButtonsHover": "ui-state-hover",
			/* Classes applied to the feature chooser icon to show multiple sorting dialog */
			"featureChooserModalDialogIcon": "ui-icon ui-iggrid-icon-multiple-sorting"
			/*jscs:enable*/
		},
		options: {
			/* type="remote|local" Defines local or remote sorting.
			remote type="string"
			local type="string"
			*/
			type: null,
			/* type="bool" Enables or disables the case sensitivity of the sorting. Works only for local sorting */
			caseSensitive: false,
			/* type="bool" Enables/disables special styling for sorted columns. If false, sorted column cells will not have any special sort-related styling
			*/
			applySortedColumnCss: true,
			/* type="string" URL param name which specifies how sorting expressions will be encoded in the URL. Uses OData conventions. ex: ?sort(col1)=asc */
			sortUrlKey: null,
			/* type="string" URL param value for ascending type of sorting. Uses OData conventions. Example: ?sort(col1)=asc */
			sortUrlKeyAscValue: null,
			/* type="string" URL param value for descending type of sorting. Uses OData conventions */
			sortUrlKeyDescValue: null,
			/* type="single|multi" Defines single column sorting or multiple column sorting.
			single type="string"
			multi type="string" if enabled, previous sorted state for columns won't be cleared
			*/
			mode: "single",
			/* type="function" custom sort function(or name of the function as a string) accepting three parameters - the data to be sorted, an array of data source field definitions, and the direction to sort with (optional). The function should return the sorted data array */
			customSortFunction: null,
			/* type="ascending|descending" Specifies which direction to use on the first click / keydown, if the column hasn't been sorted before
			ascending type="string"
			descending type="string"
			*/
			firstSortDirection: "ascending",
			/* type="string" custom sorted column tooltip in jQuery templating format */
			sortedColumnTooltip: $.ig.GridSorting.locale.sortedColumnTooltipFormat,
			/* type="bool" Specifies whether sorting to be applied immediately when click sort/unsort columns in multiple sorting dialog. When it is false Apply button shows and sorting is applied when the button is clicked */
			modalDialogSortOnClick: false,
			/* type="string" Specifies sortby button text for each unsorted column in multiple sorting dialog */
			modalDialogSortByButtonText: $.ig.GridSorting.locale.modalDialogSortByButtonText,
			/* type="string" Specifies sortby button label for each unsorted column in multiple sorting dialog */
			modalDialogResetButtonLabel: $.ig.GridSorting.locale.modalDialogResetButton,
			/* type="string" Specifies caption for each descending sorted column in multiple sorting dialog */
			modalDialogCaptionButtonDesc: $.ig.GridSorting.locale.modalDialogCaptionButtonDesc,
			/* type="string" Specifies caption for each ascending sorted column in multiple sorting dialog */
			modalDialogCaptionButtonAsc: $.ig.GridSorting.locale.modalDialogCaptionButtonAsc,
			/* type="string" Specifies caption for unsort button in multiple sorting dialog */
			modalDialogCaptionButtonUnsort: $.ig.GridSorting.locale.modalDialogCaptionButtonUnsort,
			/*dialogButtonSlideCaption: $.ig.GridSorting.locale.modalDialogButtonSlideCaption,*/
			/* type="number" Specifies width of multiple sorting dialog */
			modalDialogWidth: 350,
			/* type="number|string" Specifies height of multiple sorting dialog
				string The widget height can be set in pixels (px) and percentage (%).
				number The widget height can be set as a number
			*/
			modalDialogHeight: "",
			/* type="number" Specifies time of milliseconds for animation duration to show/hide modal dialog */
			modalDialogAnimationDuration: 200,
			/* type="string" Specifies text in feature chooser */
			featureChooserText: $.ig.GridSorting.locale.featureChooserText,
			/* type="string" custom unsorted column tooltip in jQuery templating format */
			unsortedColumnTooltip: $.ig.GridSorting.locale.unsortedColumnTooltip,
			/* type="array" a list of custom column settings that specify custom sorting settings for a specific column (whether sorting is enabled / disabled, default sort direction, first sort direction, etc.) */
			columnSettings: [
				{
					/* type="string" column key. Either key or index must be set in every column setting */
					columnKey: null,
					/* type="number" column index. Either key or index must be set in every column setting */
					columnIndex: null,
					/* type="asc|desc" This will be the first sort direction when the column hasn't been sorted before
					asc type="string"
					desc type="string"
					*/
					firstSortDirection: null,
					/* type="asc|desc" The current (or default) sort direction. If this setting is specified, the column will be rendered sorted according to this option.
					asc type="string"
					desc type="string"
					*/
					currentSortDirection: null,
					/* type="bool" enables / disables sorting on the specified column. By default all columns are sortable */
					allowSorting: true,
					/* type="string|function" Reference to a function (string or function) used for custom comparison.
					string type="string" the name of the function as a string located in the global window object.
					function type="function" function which will be used for custom comparison.
					The function accepts the following arguments:
					val1 - the first value to compare
					val2 - the second value to compare
					recordsData (optional) - an object having three properties: fieldName - the name of the sorted field; record1 - first record to compare; record2 - second record to compare
					The function returns the following numeric value:
					0 - indicating that values are equal
					1 - indicating that val1 > val2
					-1 - indicating that val1 < val2
					*/
					compareFunc: null
				}
			 ],
			/* type="string" Specifies caption text for multiple sorting dialog */
			modalDialogCaptionText: $.ig.GridSorting.locale.modalDialogCaptionText,
			/* type="string" Specifies text of button which apply changes in modal dialog */
			modalDialogButtonApplyText: $.ig.GridSorting.locale.modalDialogButtonApplyText,
			/* type="string" Specifies text of button which cancel changes in modal dialog */
			modalDialogButtonCancelText: $.ig.GridSorting.locale.modalDialogButtonCancelText,
			/* type="string" Specifies the text shown in the feature chooser item for sorting in ascending order (displayed only on touch environment). */
			featureChooserSortAsc: $.ig.GridSorting.locale.featureChooserSortAsc,
			/* type="string" Specifies the text shown in the feature chooser item for sorting in descending order (displayed only on touch environment). */
			featureChooserSortDesc: $.ig.GridSorting.locale.featureChooserSortDesc,
			/* type="bool" Enables / disables sorting persistence between states*/
			persist: true,
			/* type="string" Controls containment behavior of multiple sorting dialog.
				owner type="string" The multi sorting dialog will be draggable only in the grid area
				window type="string" The multi sorting dialog will be draggable in the whole window area
			*/
			sortingDialogContainment: "owner",
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		renderInFeatureChooser: true,
		events: {
			/* cancel="true" Event fired before sorting is invoked for a certain column.
			Return false in order to cancel column sorting.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSorting.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.columnKey to get column key.
			Use ui.direction to get sorting direction.
			Use ui.newExpressions to get sorting expressions.
			*/
			columnSorting: "columnSorting",
			/* cancel="false" Event fired after the column has already been sorted and data - re-rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridSorting.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.columnKey to get column key.
			Use ui.direction to get sorting direction.
			Use ui.expressions to get sorted expressions.
			*/
			columnSorted: "columnSorted",
			/* cancel="true" event fired before the modal dialog is opened.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.columnChooserElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogOpening: "modalDialogOpening",
			/* event fired after the modal dialog is already opened.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogOpened: "modalDialogOpened",
			/* event fired every time the modal dialog changes its position.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			Use ui.originalPosition to get the original position of the modal dialog div as { top, left } object, relative to the page.
			Use ui.position to get the current position of the modal dialog div as { top, left } object, relative to the page.
			*/
			modalDialogMoving: "modalDialogMoving",
			/* cancel="true" event fired before the modal dialog is closed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogClosing: "modalDialogClosing",
			/* event fired after the modal dialog has been closed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogClosed: "modalDialogClosed",
			/* cancel="true" event fired before the contents of the modal dialog are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogContentsRendering: "modalDialogContentsRendering",
			/* event fired after the contents of the modal dialog are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogContentsRendered: "modalDialogContentsRendered",
			/* cancel="true" event fired when sorting of column is changed in modal dialog. Column should be sorted
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			Use ui.columnKey to get the column key
			Use ui.isAsc to get whether column should be ascending or not. If true it should be ascending
			*/
			modalDialogSortingChanged: "modalDialogSortingChanged",
			/* cancel="true" event fired when button to unsort column is clicked in modal dialog
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			Use ui.columnKey to get the column key
			*/
			modalDialogButtonUnsortClick: "modalDialogButtonUnsortClick",
			/* cancel="true" event fired when column(which is not sorted) is clicked to be sorted in modal dialog
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			Use ui.columnKey to get the column key
			*/
			modalDialogSortClick: "modalDialogSortClick",
			/* cancel="true" event fired when button Apply in modal dialog is clicked
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			Use ui.columnsToSort to get array of columns which should be sorted - array of objects of sort order - Asc/Desc and columnIdentifier
			*/
			modalDialogButtonApplyClick: "modalDialogButtonApplyClick",
			/* cancel="true" event fired when the button to reset sorting is clicked.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridSorting widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogButtonResetClick: "modalDialogButtonResetClick"
		},
		_createWidget: function () {
			/* !Strip dummy objects from options, because they are defined for documentation purposes only! */
			this.options.columnSettings = [  ];
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		_create: function () {
			var self = this;
			this._headers = [  ];
			this._clickHandler = function (event) {
				var th, id, scrLeft, $scrContainer;
				/* A.T. 12 Sept. Fix for bug #87042*/
				id = $(event.target).closest(".ui-iggrid").attr("id").replace("_container", "");
				if (self.grid.element.attr("id") !== id) {
					return;
				}
				th = $(event.currentTarget).closest("th");
				if (th.attr("data-skip") !== "true" &&
					(self.grid._isMultiColumnGrid !== true || th.attr("data-isheadercell") === "true")) {
					// M.H. 19 Nov 2014 Fix for bug #185373: Clicked header is highlighted even when the column is not allowed to sort by setting allowSorting to false
					if (!self._checkSortingAllowed(th)) {
						return;
					}
					/*th.find("a").focus();*/
					self._handleSort(event);
					/* sync the grid's scrollbar if the grid has width
					check if there is a scrollLeft
					 M.H. 21 August 2015 Fix for bug 205044: Headers and cells are misaligned after sorting if virtualization is enabled.*/
					scrLeft = $("#" + self.grid.id() + "_hscroller").scrollLeft();
					if (scrLeft > 0) {
						if (self.grid.options.fixedHeaders === true) {
							//set the new scrollLeft to the one of the header
							self.grid.headersTable().parent().scrollLeft(scrLeft);
						}
						if (self.grid.options.fixedFooters === true) {
							//set the new scrollLeft to the one of the header
							self.grid.footersTable().parent().scrollLeft(scrLeft);
						}
						$scrContainer = self.grid.scrollContainer();
						if (!$scrContainer.length) {
							$scrContainer = self.grid._vdisplaycontainer();
						}
						$scrContainer.scrollLeft(scrLeft);
					}
				}
			};
			this._dragStartHandler = function (event) {
				var id, noCancel = true;
				noCancel = self.grid._trigger("headercelldragcancel", event, {});
				if (noCancel) {
					id = $(event.target).closest(".ui-iggrid").attr("id").replace("_container", "");
					if (self.grid.element.attr("id") !== id) {
						return;
					}
					event.stopPropagation();
					event.preventDefault();
				} else {
					return true;
				}
				return false;
			};
			this._mouseOverHandler = function (event) {
				var id, $target = $(event.currentTarget), cs;
				id = $(event.target).closest(".ui-iggrid").attr("id").replace("_container", "");
				if (self.grid.element.attr("id") !== id) {
					return;
				}
				/* check if the column is sortable */
				cs = self._getColSettingFromElement(event.currentTarget);
				if (cs && cs.allowSorting !== false) {
					$target.addClass(self.css.sortableColumnHeaderHover);
				}
			};
			this._mouseOutHandler = function (event) {
				var id;
				id = $(event.target).closest(".ui-iggrid").attr("id").replace("_container", "");
				if (self.grid.element.attr("id") !== id) {
					return;
				}
				$(event.currentTarget).removeClass(self.css.sortableColumnHeaderHover);
			};
			/* bind events - we assume the <tr> holding the <th>s is already there*/
			$("#" + this.element[ 0 ].id).delegate("thead th", {
				click: this._clickHandler,
				/*A.T. 21 Jan 2011 - Fix for bug #63737 - Droping a column in the filter row area results in displaying "javascript:void(0)" in the filter area
				dragstart: this._dragStartHandler,*/
				mouseover: this._mouseOverHandler,
				mouseout: this._mouseOutHandler
			});
			$("#" + this.element[ 0 ].id + " thead th").bind("dragstart", this._dragStartHandler);
			$("#" + this.element[ 0 ].id + "_headers thead th").bind("dragstart", this._dragStartHandler);
			$(document).delegate("#" + this.element[ 0 ].id + "_headers thead th", {
				click: this._clickHandler,
				/*A.T. 21 Jan 2011 - Fix for bug #63737 - Droping a column in the filter row area results in displaying "javascript:void(0)" in the filter area
				dragstart: this._dragStartHandler,*/
				mouseover: this._mouseOverHandler,
				mouseout: this._mouseOutHandler
			});
			this._keyDownHandler = function (event) {
				self._handleSortKb(event);
			};
			this._focusHandler = function (event) {
				self._handleFocusKb(event);
			};
			this._blurHandler = function (event) {
				self._handleBlurKb(event);
			};
			/* M.H. 22 March 2012 Fix for bug #105757*/
			$("#" + this.element[ 0 ].id).delegate("thead th", {
				keydown: this._keyDownHandler,
				focus: this._focusHandler,
				blur: this._blurHandler
			});
			/* M.H. 22 March 2012 Fix for bug #105757*/
			$(document).delegate("#" + this.element[ 0 ].id + "_headers thead th", {
				keydown: this._keyDownHandler,
				focus: this._focusHandler,
				blur: this._blurHandler
			});
		},
		_setOption: function (key, value) {
			var i, col, featureChooserInstance,
				modalDialog = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog");
			/* M.H. 20 March 2012 Fix for bug #105270
			handle new settings and update options hash*/
			$.Widget.prototype._setOption.apply(this, arguments);
			switch (key) {
				case "type":
					/* start by throwing an error for the options that aren't supported: */
					throw new Error($.ig.Grid.locale.optionChangeNotSupported.replace("{optionName}", key));
				case "caseSensitive":
					/* reinitialize data source setting
					A.T. 12 Feb 2011 - Fix for #66143 - igSorting caseSensitive option doesn't change case sensitivity */
					this.grid.dataSource.settings.sorting.caseSensitive = this.options.caseSensitive;
					break;
				case "modalDialogSortOnClick":
					modalDialog.remove();
					this._renderMultipleSortingDialog();
					break;
				case "modalDialogAnimationDuration":
					modalDialog.igGridModalDialog("option", "animationDuration", value);
					break;
				case "modalDialogWidth":
					modalDialog.igGridModalDialog("option", "modalDialogWidth", value);
					break;
				case "modalDialogHeight":
					modalDialog.igGridModalDialog("option", "modalDialogHeight", value);
					break;
				case "modalDialogButtonApplyText":
					modalDialog.igGridModalDialog("option", "buttonApplyText", value);
					break;
				case "modalDialogButtonCancelText":
					modalDialog.igGridModalDialog("option", "buttonCancelText", value);
					break;
				case "modalDialogCaptionText":
					modalDialog.igGridModalDialog("option", "modalDialogCaptionText", value);
					break;
				case "featureChooserText":
					featureChooserInstance = this.grid.element.data("igGridFeatureChooser");
					if (featureChooserInstance) {
						for (i = 0; i < this.grid.options.columns.length; i++) {
							col = this.grid.options.columns[ i ].key;
							featureChooserInstance._setListItemText(col, "Sorting", value);
						}
					}
					break;
				default:
					break;
			}
		},
		_getColSettingFromElement: function (e) {
			var index, cs = this.options.columnSettings, $e = $(e);
			index = parseInt($e.data("columnIndex"), 10);
			return cs[ index ];
		},
		_handleSortKb: function (event) {

			if ($(event.target).attr("id") === undefined || $(event.target).attr("id") === null) {
				return;
			}
			var $el = $(event.target),
				key = $el.attr("id").replace(this.grid.element.attr("id") + "_", ""), id;
			id = $el.closest(".ui-iggrid").attr("id").replace("_container", "");
			if (this.grid.element.attr("id") !== id) {
				return;
			}
			if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
				// M.H. 19 Nov 2014 Fix for bug #185373: Clicked header is highlighted even when the column is not allowed to sort by setting allowSorting to false
				if (this._checkSortingAllowed($el)) {
					if (this._currentActiveHeader) {
						this._currentActiveHeader.removeClass(this.css.sortableColumnHeaderActive);
					}
					$el.addClass(this.css.sortableColumnHeaderActive);
					this._currentActiveHeader = $el;
					/* M.H. 22 March 2012 Fix for bug #105214*/
					if (event.shiftKey) {
						this.unsortColumn(key, $el);
					} else {
						/*A.T. 15 Feb 2011 - fix for bug #66140*/
						this.sortColumn(key, null, $el);
						/*A.T. 19 March 2012 - fix for bug #105209
						// if ($.ig.util.isIE) {
						// $el.focus();
						// }*/
					}
				}
				event.stopPropagation();
				event.preventDefault();
			}
		},
		_handleFocusKb: function (event) {
			/* M.H. 17 Mar 2014 Fix for bug #165697: Control automatically sorts the records while resizing the column using mouse*/
			if (this._resizing && this._resizing._resizing) {
				return;
			}
			var $target = $(event.target),
				id = $target.closest(".ui-iggrid").attr("id").replace("_container", "");
			/* M.H. 11 Jun 2013 Fix for bug #144395: When you fix a column, fixedHeaders is false and click "Add new row" clicking in the editor of the fixed column cancels the adding.
			 M.H. 19 Nov 2014 Fix for bug #185373: Clicked header is highlighted even when the column is not allowed to sort by setting allowSorting to false
			 D.K. 21 Mar 2016 Fix for bug #216360: Summaries icon gets white background when clicked */
			if (this.grid.element.attr("id") !== id ||
					$target.attr("data-fixing-indicator") !== undefined ||
					!this._checkSortingAllowed($target) ||
					!$target.is("th")) {
				return;
			}
			$target.addClass(this.css.sortableColumnHeaderFocus);
		},
		_handleBlurKb: function (event) {
			var id = $(event.target).closest(".ui-iggrid").attr("id").replace("_container", "");
			if (this.grid.element.attr("id") !== id) {
				return;
			}
			$(event.target).removeClass(this.css.sortableColumnHeaderFocus);
		},
		_handleSort: function (event) {
			/* M.H. 4 Mar 2014 Fix for bug #165697: Control automatically sorts the records while resizing the column using mouse*/
			if (this._resizing && this._resizing._resizing) {
				this._resizing._resizing = false;
				return;
			}
			var $currentTarget = $(event.currentTarget),
				$th = $currentTarget.closest("th"),
				key = $th.attr("id").replace(this.grid.element.attr("id") + "_", ""), isShiftClicked = false;
			if (this._currentActiveHeader) {
				/* Fix for bug 187244: Multiple sorting and column headers: If  we click below the header text to sort, the header remains highlighted after clicking on another header to sort.
				This is related to bug 187149 and 165592 */
				this._currentActiveHeader.removeClass(this.css.sortableColumnHeaderActive)
					.removeClass(this.css.sortableColumnHeaderFocus);
			}
			isShiftClicked = event.shiftKey;
			if (isShiftClicked) {
				//this._currentHeader = $th;
				this.unsortColumn(key, $th);
			} else {
				$currentTarget.addClass(this.css.sortableColumnHeaderActive);
				this._currentActiveHeader = $th;
				this.sortColumn(key, null, $currentTarget);
			}
			event.stopPropagation();
			event.preventDefault();
		},
		_checkSortingAllowed: function ($th) {
			$th = $th.is("th") ? $th : $th.closest("th");
			var id =  $th.attr("id") || "", colKey = id.replace(this.grid.id() + "_", ""),
			s = this._findColumnSetting(colKey);
			if (!s || s.allowSorting === false) {
				return false;
			}
			return true;
		},
		_initLoadingIndicator: function () {
			/* attach loading indicator widget*/
			this._loadingIndicator = this.grid.container().length > 0 ?
				this.grid.container().igLoading().data("igLoading").indicator() :
				this.grid.element.igLoading().data("igLoading").indicator();
		},
		_getHeaderCellByIdentifier: function (identifier) {
			var $headerCell, colKey;

			if ($.type(identifier) === "number") {
				colKey = this.grid.options.columns[ identifier ].key;
			} else {
				colKey = identifier;
			}
			$headerCell = $("#" + this.grid.element[ 0 ].id + "_" + colKey);
			return $headerCell;
		},
		sortColumn: function (index, direction, header) {
			/* sorts a grid column and updates the UI
				paramType="object" Column key (string) or index (number) - for multi-row grid only column key can be used. Specifies the column which we want to sort. If the mode is multiple, previous sorting states are not cleared.
				paramType="asc|desc" Specifies sorting direction (ascending or descending)
				paramType="object" excluded="true"
			*/
			var colKey, expr, noCancel = true, s, se, i, visualIndex, groupby, reset = true, newDir,
				apiCall = (header === null || header === undefined),
				addSE = false,
				isHidden = false,
				sortingNewExprs, curNewExpr, cs,
				compareFunc;
			/* M.H. 21 August 2015 Fix for bug 205045: Sorted column’s background color style disappears when a vertical scrollbar is moved if virtualization is enabled.*/
			this._activeSortedCol = null;
			if ($.type(index) === "number") {
				colKey = this.grid.options.columns[ index ].key;
				if (this.grid.options.columns[ index ].hidden) {
					isHidden = true;
				}
			} else {
				colKey = index;
				if (header === null || header === undefined) {
					header = $("#" + this.grid.element[ 0 ].id + "_" + colKey);
				}
				index = header.data("columnIndex");
				/* M.H. 22 Feb 2013 Fix for bug #132629: Sorting cannot be applied on a hidden column */
				if (index === undefined) {
					isHidden = true;
				}
			}

			/* M.H. 10 April 2012 Fix for bug #107671
			 M.K. 3/6/2015 Fix for bug 187249: Sorted column loses its styles after moving and hiding/showing the column.*/
			this._curColKey = colKey;
			expr = this.grid.dataSource.settings.sorting.expressions;
			s = this._findColumnSetting(colKey);
			if (this.grid.options.virtualizationMode === "fixed" &&
				(this.grid.options.virtualization === true || this.grid.options.columnVirtualization === true)
				) {
				visualIndex = index - this.grid._startColIndex;
			} else {
				visualIndex = index;
			}
			if (apiCall && this._currentActiveHeader) {
				this._currentActiveHeader.removeClass(this.css.sortableColumnHeaderActive);
			}
			if (header === null || header === undefined) {
				/* programmatic sorting
				A.T. 11 Oct - Fix for bug #91033*/
				visualIndex +=
					this.grid.headersTable().children("thead").children("tr")
					.children("th[ data-skip=true ]").length;
				/* M.H. 12 April 2012 Fix for bug #108488*/
				if (colKey) {
					header = $("#" + this.grid.element[ 0 ].id + "_" + colKey);
				} else {
					header =
						this.grid.headersTable().children("thead")
						.children("tr").children("th:nth-child(" + (visualIndex + 1) + ")");
				}
				apiCall = true;
			}
			if (s && s.allowSorting === false) {
				return;
			}
			/* M.H. 14 Apr 2015 Fix for bug 192560: When sorting is disabled hiding and showing a column applies the sorting style*/
			if (direction !== null && direction !== undefined) {
				newDir = direction;
			} else {
				if ((s.currentSortDirection !== undefined && s.currentSortDirection !== null &&
					!s.currentSortDirection.startsWith("asc") && !s.currentSortDirection.startsWith("desc")) ||
					s.currentSortDirection === undefined || s.currentSortDirection === null) {
					newDir = s.firstSortDirection === undefined ?
						this.options.firstSortDirection : s.firstSortDirection;
				} else {
					newDir = (s.currentSortDirection !== undefined && s.currentSortDirection !== null &&
						s.currentSortDirection.indexOf("asc") !== -1) ? "descending" : "ascending";
				}
			}
			/* M.P.: 21 Oct 2014. Fix for bug: 183285 - Filter condition isn’t respected when type set to local.
			 As the sorting requires persisting of page index we need a mechanism to internally handle an event*/
			this._trigger("internalcolumnsorting", null, { owner: this });

			/*	M.W.H: 4 Jun 2015. Fix for bug 200633 - igGridSorting.columnSorting event ui.newExpressions collection is not populated
				Pre-generate the expressions to provide to the columnSorting event. This also allows users to manipulate sorting expressions before databinding.
				check if we have group columns (GroupBy feature)*/
			for (i = 0; this.grid.options.features && i < this.grid.options.features.length; i++) {
				if (this.grid.options.features[ i ] && this.grid.options.features[ i ].name === "GroupBy") {
					/* try getting the feature*/
					groupby = this.grid.element.data("igGridGroupBy");
					if (groupby && groupby.options.groupedColumns && groupby.options.groupedColumns.length > 0) {
						reset = false;
					}
				}
			}
			/*check for custom sort func*/
			cs = this._findColumnSetting(colKey);
			compareFunc = null;
			if (cs && cs.compareFunc) {
				if ($.type(cs.compareFunc) === "function") {
					compareFunc = cs.compareFunc;
				} else if (typeof cs.compareFunc === "string" && window[ cs.compareFunc ] &&
					typeof window[ cs.compareFunc ] === "function") {
					compareFunc = window[ cs.compareFunc ];
				}
			}
			/* A.T. 10 Oct 2011 if we have Grouped columns, even though the sorting is single, we shouldn't clear all existing sorting expressions
			 because essentially GroupBy is using the same sorting expressions for the groups */
			curNewExpr = {
				fieldName: colKey,
				isSorting: true,
				dir: newDir.startsWith("asc") ? "asc" : "desc",
				compareFunc: compareFunc
			};

			if (this.options.mode === "single") {
				/* A.T. 10 Oct 2011 if we have Grouped columns, even though the sorting is single, we shouldn't clear all existing sorting expressions
				because essentially GroupBy is using the same sorting expressions for the groups*/
				if (reset) {
					sortingNewExprs = [{
						fieldName: colKey,
						isSorting: true,
						dir: newDir.startsWith("asc") ? "asc" : "desc",
						compareFunc: compareFunc }];
				} else {
					sortingNewExprs = [  ];
					/* replace the sorting expression which is for the current key
					 M.H. 21 May 2012 Fix for bug #112188
					 we should insert first groupby sorting expressions (those which are marked with flag groupBy) and then to push at the end of the array current sorting expression*/
					se = this.grid.dataSource.settings.sorting.expressions;
					addSE = true;
					/* M.H. 6 Jan 2014 Fix for bug #160660: When applying groupBy and sorting to the same column the generated  $orderby is set twice on the same column*/
					for (i = 0; i < se.length; i++) {
						if (se[ i ].isGroupBy === true) {
							if (se[ i ].fieldName === colKey) {
								addSE = false;
							}
							sortingNewExprs.push(se[ i ]);
						}
					}
					if (addSE) {
						sortingNewExprs.push({
							fieldName: colKey,
							isSorting: true,
							dir: newDir.startsWith("asc") ? "asc" : "desc",
							compareFunc: compareFunc });
					}
				}
			} else {
				sortingNewExprs = expr.slice();
				/* multi-column sorting*/
				for (i = 0; i < expr.length; i++) {
					if (sortingNewExprs[ i ].fieldName === colKey) {
						sortingNewExprs.splice(i, 1);
						break;
					}
				}
				sortingNewExprs = sortingNewExprs.concat([{
					fieldName: colKey,
					isSorting: true,
					dir: newDir.startsWith("asc") ? "asc" : "desc",
					compareFunc: compareFunc }]);
			}
			/* fire sorting event*/
			if (!apiCall) {
				noCancel = this._trigger(this.events.columnSorting, null,
					{ columnKey: colKey, direction: newDir, owner: this, newExpressions: sortingNewExprs });
			}
			if (noCancel) {
				/* M.H. 14 Apr 2015 Fix for bug 192560: When sorting is disabled hiding and showing a column applies the sorting style*/
				s.currentSortDirection = curNewExpr.dir.startsWith("asc") ? "ascending" : "descending";
				this._loadingIndicator.show();
				/* update title attributes*/
				header.attr("title", s.currentSortDirection.startsWith("asc") ?
					this.options.sortedColumnTooltip.replace("${direction}", $.ig.GridSorting.locale.ascending) :
					this.options.sortedColumnTooltip.replace("${direction}", $.ig.GridSorting.locale.descending));
				if (this.options.mode === "single") {
					/* M.H. 22 Feb 2013 Fix for bug #132629: Sorting cannot be applied on a hidden column */
					if (isHidden) {
						this._clearSortStates(header, -1);
					}
						/* clear all sort states except the one with specified colKey. If colKey is NOT specified will clear all*/
						for (i = 0; i < this.grid.options.columns.length; i++) {
							if (this.options.columnSettings[ i ] &&
								this.options.columnSettings[ i ].allowSorting !== false &&
								this.options.columnSettings[ i ].columnKey !== colKey) {
								delete this.options.columnSettings[ i ].currentSortDirection;
								/*jscs:disable*/
								delete this.options.columnSettings[ i ].userSet_currentSortDirection;
								/*jscs:enable*/
								this._clearSortStateByColKey(this.options.columnSettings[ i ].columnKey);
							}
						}
					/* M.H. 22 Feb 2013 Fix for bug #132629: Sorting cannot be applied on a hidden column*/
					if (isHidden) {
						s.currentSortDirection = direction || s.currentSortDirection;
					}
					}
				this.grid.dataSource.settings.sorting.expressions = sortingNewExprs;
				/* trigger DataBinding event on the grid */
				noCancel = this.grid._trigger(this.grid.events.dataBinding,
					null, { owner: this.grid, dataSource: this.grid.dataSource });
				/* M.H. 5 Sep 2013 Fix for bug #145014: When using remote paging and sorting  the values of the unbound columns set via the unboundValues option are not persisted.
				M.H. 15 Oct 2013 Fix for bug #154987: When the dataSource is remote, any action that rearranges records mess up the unbound values */
				if (this.grid._hasUnboundColumns && this.options.type === "remote") {
					this.grid._rebindUnboundColumns = true;
				}
				if (noCancel) {
					this._saveSortingExpressions();
					/* A.T. 13 April 2011 fix for bug #72284 */
					this.grid.element.trigger("iggriduisoftdirty", { owner: this });
					this._currentHeader = header;
					this._curColKey = colKey;
					if (this.options.type === "remote") {
						if (!apiCall) {
							this._shouldFireColumnSorted = true;
						}
						this.grid.dataSource.dataBind();
					} else {
						this.grid.dataSource.sort(
							this.grid.dataSource.settings.sorting.expressions,
							s.currentSortDirection);
						/* L.A. 07 August 2012 - Fixing bug #118388 The "databound" event fires twice when sorting a column
						 _renderData fires again databound, therefore this call is not needed.
						this.grid._trigger(this.grid.events.dataBound, null, {owner: this.grid});*/
						this.grid._renderData();
						if (!apiCall) {
							this._trigger(this.events.columnSorted, null,
								{ columnKey: colKey,
									direction: s.currentSortDirection,
									owner: this,
									expressions: this.grid.dataSource.settings.sorting.expressions });
						}
					}
					this._curSortDir = s.currentSortDirection;
				}
			}
		},
		sortMultiple: function () {
			/* sorts grid columns and updates the UI
			*/
			/* trigger DataBinding event on the grid*/
			var self = this, exprLen,
				id = self.grid.element[ 0 ].id,
				noCancel,
				expr = this.grid.dataSource.settings.sorting.expressions;

			noCancel = this.grid._trigger(this.grid.events.dataBinding, null,
				{ owner: this.grid, dataSource: this.grid.dataSource });
			if (noCancel) {
				/*M.K. 3/31/2015 Fix for bug 190905: Sorting through the sorting dialog doesn't apply sorting styles*/
				this._currentHeader = null;
				this._curColKey = null;
				if (this._currentActiveHeader) {
					/*M.K. If sorting is applied via the dialog, remove active header styles. They should apply only on user interactions: click or keyboard navigation.*/
					this._currentActiveHeader.removeClass(this.css.sortableColumnHeaderActive);
				}
				exprLen = expr && expr.length;
				if (!exprLen) {
					exprLen = 0;
				}
				$.each(this.grid.options.columns, function (index, column) {
					var i, cs, colKey = column.key,
						header = $("#" + id + "_" + colKey);
					for (i = 0; i < exprLen; i++) {
						if (expr[ i ].fieldName === colKey) {
							if (i === exprLen - 1) {
								/*M.K.Apply active styles for the last sorted column*/
								self._currentHeader = header;
								self._curColKey = colKey;
							}
							break;
						}
					}
					cs = self._findColumnSetting(colKey);
					if (cs) {
						if (i === exprLen) {
							/* remove sorting for the column*/
							cs.currentSortDirection = undefined;
							if (cs.allowSorting !== false) {
								/*clear sort states*/
								self._clearSortState(header, cs.columnIndex, colKey);
							}
						} else {
							cs.currentSortDirection = expr[ i ].dir;
							self._applySortStyles(header, colKey);
						}
					}
				});
				/* A.T. 13 April 2011 fix for bug #72284*/
				this.grid.element.trigger("iggriduisoftdirty", { owner: this });
				/* M.H. 18 July 2012 Fix for bug #105514*/
				if (this._isResetClick === true) {
					// M.H. 19 July 2012 Fix for bug #105514*/
					this._isResetClick = false;
				}
				/* M.H. 29 May 2014 Fix for bug #170808: Sorting is remain applied after data binding and clicking reset button in sorting dialog when persistence is enabled*/
				this._saveSortingExpressions();
				/*this._shouldFireColumnSorted = true;*/
				if (this.options.type === "remote") {
					this.grid.dataSource.dataBind();
				} else {
					this.grid.dataSource.sort(this.grid.dataSource.settings.sorting.expressions);
					/* M.H. 19 Sep 2013 Fix for bug #139696: igGridSorting duplicated dataBound event when sorting through the mutliple sorting dialog
					this.grid._trigger(this.grid.events.dataBound, null, {owner: this.grid});*/
					this.grid._renderData();
				}
				/* render Asc/Desc buttons*/
			}
		},
		clearSorting: function () {
			/* remove current sorting(for all sorted columns) and updates the UI  */
			var i, se = [  ], exprs = this.grid.dataSource.settings.sorting.expressions;
			for (i = 0; i < exprs.length; i++) {
				se.push(exprs[ i ].fieldName);
			}
			for (i = 0; i < se.length; i++) {
				this.unsortColumn(se[ i ], undefined);
			}
		},
		unsortColumn: function (index, header) {
			/* remove sorting for the grid column with the specified columnKey/columnIndex and updates the UI
				paramType="object" Column key (string) or index (number) - for multi-row grid only column key can be used. Specifies the column for which we want to remove sorting. If the mode is multiple, previous sorting states are not cleared.
				paramType="object" excluded="true" - if specified client events should be fired
			*/
			var colKey, expr, noCancel = true, s, i, exprLen, headerId, $th,
				apiCall = (header === null || header === undefined);
			/* M.H. 21 August 2015 Fix for bug 205045: Sorted column’s background color style disappears when a vertical scrollbar is moved if virtualization is enabled.*/
			this._activeSortedCol = null;
			if ($.type(index) === "number") {
				colKey = this.grid.options.columns[ index ].key;
			} else {
				colKey = index;
			}
			headerId = this.grid.id() + "_" + colKey;
			$th = header || $("#" + headerId);
			expr = this.grid.dataSource.settings.sorting.expressions;
			s = this._findColumnSetting(colKey);
			if (s === null || s === undefined) {
				return;
			}
			if (s && s.allowSorting === false) {
				return;
			}
			s.currentSortDirection = undefined;
			/* M.H. 10 Sep 2015 Fix for bug 206096: When moving sorted column and unsorting it after that the icon for sorted column stays.*/
			/*jscs:disable*/
			if (s.userSet_currentSortDirection) {
				delete s.userSet_currentSortDirection;
			}
			/*jscs:enable*/
			exprLen = expr.length;
			for (i = 0; i < expr.length; i++) {
				if (expr[ i ].fieldName === colKey) {
					expr.splice(i, 1);
					break;
				}
			}
			/* there is no found sorting expressions with the specified colKey*/
			if (i === exprLen) {
				return;
			}
			/* M.H. 18 April 2012 Fix for bug #109745*/
			this._curColKey = this._curColKey === colKey ? null : this._curColKey;
			this._currentHeader = (this._currentHeader && this._currentHeader.attr("id") === headerId) ?
										null : this._currentHeader;
			/* M.H. 16 March 2012 Fix for bug #105074 */
			this._applySortStyles($th, colKey);
			if (!apiCall &&
				!(this.grid.dataSource.settings.sorting.expressions.length === 0 &&
				this.options.type === "local")) {
				noCancel = this.grid._trigger(this.grid.events.dataBinding, null,
					{ owner: this.grid, dataSource: this.grid.dataSource });
			}
			if (noCancel) {
				this.grid.element.trigger("iggriduisoftdirty", { owner: this });
				$th.removeClass(this.css.sortableColumnHeaderFocus)
					.removeClass(this.css.sortableColumnHeaderActive);
				this._saveSortingExpressions();
				/* M.H. 10 April 2012 Fix for bug #107671 - it should not be set current index
				this._currentIndex = index;*/
				if (this.options.type === "remote") {
					/* M.H. 15 July 2015 Fix for bug 201728: When unsorting column the loading indicator does not show. */
					if (!this._loadingIndicator) {
						this._initLoadingIndicator();
					}
					this._loadingIndicator.show();
					if (!apiCall) {
						this._shouldFireColumnSorted = true;
					}
					this.grid.dataSource.dataBind();
				} else {
					if (this.grid.dataSource.settings.sorting.expressions.length === 0 && exprLen > 0) {
						this.grid.dataBind();
					} else {
						this.grid.dataSource.sort(this.grid.dataSource.settings.sorting.expressions);
						this.grid._renderData();
					}
					if (!apiCall) {
						this._trigger(this.events.columnSorted, null, {
							columnKey: colKey,
							direction: s.currentSortDirection,
							owner: this,
							expressions: this.grid.dataSource.settings.sorting.expressions
						});
					}
				}
				this._curSortDir = s.currentSortDirection;
			}
		},
		/* A.T. fix for bug #73496 */
		_excludeExpr: function (key) {
			/* M.H. 5 Mar 2014 Fix for bug #165360: Sorting persistance is not working for the child layouts of the grid*/
			if (this.options.persist && !this._couldPreserveData()) {
				return;
			}
			var expr = this.grid.dataSource.settings.sorting.expressions, i;
			for (i = 0; i < expr.length; i++) {
				if (expr[ i ].fieldName === key) {
					/* remove the entry from the array
					A.T. 8 March 2012 - Fix for bug #104244
					expr.remove(i);*/
					$.ig.removeFromArray(expr, i);
				}
			}
		},
		/* M.H. 2 April 2012 Fix for bug #106392
		 M.H. 10 April 2012 Fix for bug #107671 - add ignore selected style*/
		_applySortStyles: function (header, columnKey, ignoreActiveSelection) {
			// M.H. 5 April 2012 Fix for bug #106392
			var oldHeight = null, span, col, indicatorContainer,
				indexResolved, i, currentColumnSetting, $tbody,
				/* L.A. 21 September 2012 - fixing bug #121812
				 Grid - Incorrect Tooltip in Multiple Sorting and Hiding Events with Modal Window sample */
				toolTip = this.options.sortedColumnTooltip,
				isMrl = !!this.grid._rlp,// detect multi-row-layout template is enabled
				hasFixedColumns = this.grid.hasFixedColumns(),
				sortAsc = toolTip.replace("${direction}", $.ig.GridSorting.locale.ascending),
				sortDesc = toolTip.replace("${direction}", $.ig.GridSorting.locale.descending);

			/* if (this.grid.options.virtualizationMode === "fixed" && (this.grid.options.virtualization === true || this.grid.options.columnVirtualization === true)) {
				visualIndex = index - this.grid._startColIndex;
			} else {
				visualIndex = index;
			}
			M.H. 16 May 2013 Fix for bug 142351: Cell value is split into single character when a flat grid is shown after a hierarchical grid has been expanded*/
			if (this.grid.options.fixedHeaders) {
				oldHeight = this.grid.headersTable().outerHeight();
			}
			/* apply class to sorting indicator*/
			span = header.find(".ui-iggrid-colindicator");
			/* S.S. Bug #108027 April 9, 2012 sorting indicator span hasn't been added yet*/
			if (span.length === 0) {
				span = $("<span></span>").addClass(this.css.sortIndicator);
			}
			indicatorContainer = header.find(".ui-iggrid-indicatorcontainer");
			if (indicatorContainer.length === 0) {
				indicatorContainer = $("<div></div>").appendTo(header).addClass("ui-iggrid-indicatorcontainer");
			}
			indicatorContainer.append(span);
			indexResolved = this.grid.getVisibleIndexByKey(columnKey, true) + 1;
			/* A.T. fix failing unit tests
			M.H. 2 April 2012 Fix for bug #106392*/
			if (columnKey !== null && columnKey !== undefined) {
				for (i = 0; i < this.options.columnSettings.length; i++) {
					if (this.options.columnSettings[ i ].columnKey === columnKey) {
						currentColumnSetting = this.options.columnSettings[ i ];
						break;
					}
				}
			}
			if (hasFixedColumns && this.grid._isFixedElement(header)) {
				$tbody = $("#" + this.element[ 0 ].id + "_fixed").find("tbody");
			} else {
				if (this.grid.options.fixedHeaders !== true) {
					/* M.H. 2 Sep 2014 Fix for bug #175901: If the first column is sorted and also grouped the sort style will be applied to the groupby rows as well */
					$tbody = header.closest("thead").parent().find(">tbody");
				} else if (this.options.applySortedColumnCss !== false) {
					/* M.H. 2 Sep 2014 Fix for bug #175901: If the first column is sorted and also grouped the sort style will be applied to the groupby rows as well */
					$tbody = this.grid.element.find(">tbody");
				}
				}
			if ($tbody) {
				col = isMrl ?
					$tbody.find(">tr:not([data-grouprow])>td[aria-describedby='" +
					this.grid.id() + "_" + columnKey + "']") :
					$tbody.find(">tr:not([data-grouprow])>td:nth-child(" + indexResolved + ")");
			}
			/* M.H. 2 April 2012 Fix for bug #106392
			 do not apply sorting styles on a hidden column - indexResolved is not calculated properly
			 M.H. 5 April 2012 Fix for bug #106392
			 M.H. 21 August 2015 Fix for bug 205045: Sorted column’s background color style disappears when a vertical scrollbar is moved if virtualization is enabled.*/
			this._activeSortedCol = null;
			if (!!currentColumnSetting && !!currentColumnSetting.currentSortDirection
				/*&& (!columnOption || (columnOption && columnOption.hidden !== true))*/
				) {
				if (currentColumnSetting.currentSortDirection.indexOf("asc") !== -1) {
					span.removeClass(this.css.sortIndicatorDescending).addClass(this.css.sortIndicatorAscending);
					header.removeClass(this.css.descendingColumnHeader).addClass(this.css.ascendingColumnHeader);
					/* L.A. 21 September 2012 - fixing bug #121812
					Grid - Incorrect Tooltip in Multiple Sorting and Hiding Events with Modal Window sample*/
					header.attr("title", sortAsc);
					header.attr("aria-sort", "ascending");
					header.siblings("th").removeAttr("aria-sort");
					/* change the CSS class for the column */
					if (this.options.applySortedColumnCss !== false && ignoreActiveSelection !== true) {
						/* M.H. 21 August 2015 Fix for bug 205045: Sorted column’s background color style disappears when a vertical scrollbar is moved if virtualization is enabled.*/
						this._activeSortedCol = { key: currentColumnSetting.columnKey, asc: true };
						col.removeClass(this.css.descendingColumn).addClass(this.css.ascendingColumn);
					}
				} else {
					span.removeClass(this.css.sortIndicatorAscending).addClass(this.css.sortIndicatorDescending);
					header.removeClass(this.css.ascendingColumnHeader).addClass(this.css.descendingColumnHeader);
					/* L.A. 21 September 2012 - fixing bug #121812
					 Grid - Incorrect Tooltip in Multiple Sorting and Hiding Events with Modal Window sample*/
					header.attr("title", sortDesc);
					header.attr("aria-sort", "descending");
					header.siblings("th").removeAttr("aria-sort");
					/* change the css class for the column */
					if (this.options.applySortedColumnCss !== false && ignoreActiveSelection !== true) {
						/* M.H. 21 August 2015 Fix for bug 205045: Sorted column’s background color style disappears when a vertical scrollbar is moved if virtualization is enabled. */
						this._activeSortedCol = { key: currentColumnSetting.columnKey, asc: false };
						col.removeClass(this.css.ascendingColumn).addClass(this.css.descendingColumn);
					}
				}
			} else {
				/* L.A. 21 September 2012 - fixing bug #121812
				 Grid - Incorrect Tooltip in Multiple Sorting and Hiding Events with Modal Window sample */
				header.attr("title", this.options.unsortedColumnTooltip);
				header.removeAttr("aria-sort");
				/* M.H. 27 Feb 2014 Fix for bug #165592: When multi column headers are enabled and sorting can't be undone by Shift+Click in multiple mode.*/
				if (this.grid._isMultiColumnGrid === true || isMrl) {
					span = header.find(".ui-iggrid-colindicator");
				} else {
					span = header.parent("tr").find("th:nth-child(" + indexResolved + ") .ui-iggrid-colindicator");
				}
				/* M.H. 13 Mar 2014 Fix for bug #165592: When multi column headers are enabled and sorting can't be undone by Shift+Click in multiple mode.
				//M.K. Fix for bug 187149: Style for sorted column is not preserved after hiding and showing the column.*/
				span.removeClass(this.css.sortIndicatorAscending)
					.removeClass(this.css.sortIndicatorDescending)
					.removeClass(this.css.descendingColumnHeader);
				header.removeClass(this.css.ascendingColumnHeader)
					.removeClass(this.css.descendingColumnHeader);
				/*.removeClass(this.css.sortableColumnHeaderActive)
				//.removeClass(this.css.sortableColumnHeaderFocus)*/
			}
			/* M.H. 16 May 2013 Fix for bug 142351: Cell value is split into single character when a flat grid is shown after a hierarchical grid has been expanded*/
			if (oldHeight !== null && oldHeight !== this.grid.headersTable().outerHeight()) {
				this.grid._initializeHeights();
			}
		},
		/* will clear all sort states except the one specified as index. If none is specified will clear all*/
		_clearSortStates: function (header, index) {
			var i, cs = this.options.columnSettings,
				realIndex = index, isSingleMode = this.options.mode === "single";
			/* take into account all hidden columns with index below "index"*/
			for (i = 0; i < this.grid.options.columns.length; i++) {
				if (i < index && this.grid.options.columns[ i ].hidden) {
					realIndex--;
					/* hiddenCount++; */
				}
			}
			index = realIndex;
			for (i = 0; i < this.options.columnSettings.length; i++) {
				if (cs[ i ].allowSorting !== false) {
					if (index !== undefined && cs[ i ].columnIndex !== index) {
						/* cs[ i ].currentSortDirection = null;
						if (cs[ i ].currentSortDirection) {
							cs[ i ].currentSortDirection = cs[ i ].userSet_currentSortDirection;
						} */
						delete cs[ i ].currentSortDirection;
						/* M.H. 12 Apr 2016 Fix for bug 217765: Sorting indicator appears after the second column sorting and calling dataBind*/
						/*jscs:disable*/
						delete cs[ i ].userSet_currentSortDirection;
						/*jscs:enable*/
						/*this._clearSortState(header, i - hiddenCount < 0 ? 0 : i - hiddenCount);
						 M.H. 4 June 2012 Fix for bug #113187*/
						this._clearSortState(header, cs[ i ].columnIndex, cs[ i ].columnKey);
					}
					/* M.H. 19 July 2012 Fix for bug #113505*/
					if (this.grid._detachedHeaderCells && isSingleMode && cs[ i ].columnKey &&
						this.grid._detachedHeaderCells[ cs[ i ].columnKey ]) {
						if (cs[ i ].currentSortDirection !== undefined && cs[ i ].currentSortDirection !== null) {
							delete cs[ i ].currentSortDirection;
						}
						this._clearHeaderCellSortState(this.grid._detachedHeaderCells[ cs[ i ].columnKey ][ 0 ]);
					}
				}
			}
		},
		_clearSortState: function (header, i, columnKey) {
			if (columnKey !== undefined &&
				(this.grid.hasFixedColumns() || this.grid._rlp)) {
				this._clearSortStateByColKey(columnKey);
				return;
			}
			var th, ths, fixedColumnsLength, $thead;
			if (this.grid._isMultiColumnGrid === true) {
				/* M.H. 10 Aug 2012 Fix for bug #118779 */
				ths = this.grid._headerCells;/*headersTable().find("thead th[ data-isheadercell=true ]").not("[ data-skip=true ]"); */
				th = ths[ i ];
				if (th === undefined) {
					return;
				}
			} else {
				if (this.grid.hasFixedColumns()) {
					fixedColumnsLength = this.grid._fixedColumns.length;
					if (i < fixedColumnsLength) {
						$thead = this.grid.fixedHeadersTable().find("thead");
					} else {
						$thead = this.grid.headersTable().find("thead");
						i -= fixedColumnsLength;
					}
				} else {
					$thead = this.grid.headersTable().find("thead");
				}
				ths = $thead.find(">tr").first().find(">th").not("[data-skip=true]");
					th = ths.eq(i);
				}
			this._clearSortStateByVisibleIndex(i, th);
		},
		_clearSortStateByColKey: function (key) {
			if (key === undefined || key === null) {
				return;
			}
			var hk = this.grid.id() + "_" + key, $cells, csInd, i, $tbl, $th;
			for (i = 0; i < this.options.columnSettings.length; i++) {
				if (this.options.columnSettings[ i ].columnKey === key) {
					csInd = i;
					/* M.H. 21 Sep 2016 Fix for bug 225848: When hiding sorted column and sort another column - sort indicator of hidden column is not removed(when showing it) in case of sorting mode is "single" */
					if (this.options.mode === "single" &&
							this.grid._detachedHeaderCells &&
							this.grid._detachedHeaderCells[ key ]) {
						$th = this.grid._detachedHeaderCells[ key ][ 0 ];
					}
					$th = !$th || !$th.length ? $("#" + hk) : $th;
					break;
				}
			}
			if (this.options.applySortedColumnCss !== false) {
				$tbl = this.grid.isFixedColumn(key) ?
							$("#" + this.grid.id() + "_fixed") :
							this.grid.element;
				$cells = $tbl.find(">tbody>tr>td[aria-describedby='" + hk + "']");
			}
			this._clearSotrStateForCells($th, $cells, csInd);
		},
		_clearSortStateByVisibleIndex: function (i, th) {
			var col, $tbl;
			if (this.options.applySortedColumnCss !== false) {
				$tbl = this.grid._isFixedElement(th) ?
						$("#" + this.element[ 0 ].id + "_fixed") :
						this.grid.element;
				col = (this.grid.options.fixedHeaders !== true) ?
					th.closest("table").find(">tbody>tr>td:nth-child(" + (i + 1) + ")") :
					$tbl.find("tbody>tr>td:nth-child(" + (i + 1) + ")");
			}
			this._clearSotrStateForCells(th, col, i);
		},
		_clearSotrStateForCells: function ($th, $cells, csIndex) {
			this._clearHeaderCellSortState($th);
			if (csIndex !== undefined &&
				this.options.columnSettings.length > csIndex &&
				this.options.columnSettings[ csIndex ].allowSorting) {
				$th.attr("title", this.options.unsortedColumnTooltip);
				} else {
				$th.attr("title", "");
				}
			if ($cells) {
				// change the CSS class for the column
				$cells.removeClass(this.css.descendingColumn).removeClass(this.css.ascendingColumn);
			}
		},
		_clearHeaderCellSortState: function (spanHeader) {
			var span = spanHeader.find(".ui-iggrid-colindicator");
			if (span.hasClass("ui-iggrid-colindicator-desc") ||
				span.hasClass("ui-iggrid-colindicator-asc")) {
				span.removeClass(this.css.sortIndicatorDescending).removeClass(this.css.sortIndicatorAscending);
				/* we need to get the correct header, that corresponds to the span*/
				spanHeader.removeClass(this.css.ascendingColumnHeader)
					.removeClass(this.css.descendingColumnHeader).removeClass(this.css.sortableColumnHeaderFocus);
			}
		},
		_initDefaultSettings: function (suppressUsrOpts) {
			/* TODO: remove this argument - suppressUsrOpts(it is true when called from _columnsRearranged) - it is workaround  for fixing bug 206537: Moving a sorted column and unsorting a second column after that a sorted icon appears on the first column
			fill in default column settings, so that later on we can get the current sort state of every sortable column
			iterate through columns*/
			var settings = [  ], key, cs = this.options.columnSettings,
				i, j, k, colIndex = 0, defaultExpressions = [  ],
				defExpLength, isToSkipGroupBySortingExpr = false, dir,
				allowSorting, isToCheckUnboundColumns =
				(this.grid._hasUnboundColumns === true && this.options.type === "remote");
			/* M.H. 9 May 2012 Fix for bug #111339 - it is possible for instance to be set groupby with default settings and then sorting expressions will be overwritten */
			if (this.grid.dataSource.settings.sorting.expressions !== null &&
				this.grid.dataSource.settings.sorting.expressions !== undefined) {
				defaultExpressions = this.grid.dataSource.settings.sorting.expressions;
			}
			defExpLength = defaultExpressions.length;
			/* initialize */
			if (this.grid.options.columns && this.grid.options.columns.length > 0) {
				for (i = 0; i < this.grid.options.columns.length; i++) {
					allowSorting = true;
					if (isToCheckUnboundColumns &&
						this.grid.getUnboundColumnByKey(this.grid.options.columns[ i ].key) !== null) {
						allowSorting = false;
					}
					settings[ i ] = { "columnIndex": colIndex,
						"columnKey": this.grid.options.columns[ i ].key,
						"allowSorting": allowSorting };
					/* M.H. 19 May 2012 Fix for bug #112103
					column index should be set properly when hiding is set on init. Otherwise clearsortstates does not clear the sort state for the proper column */
					if (this.grid.options.columns[ i ].hidden !== true) {
						colIndex++;
					}
				}
			}
			for (i = 0; i < cs.length; i++) {
				for (key in cs[ i ]) {
					if (cs[ i ].hasOwnProperty(key) && key !== "columnKey" && key !== "columnIndex") {
						if (key === "userSet_currentSortDirection") {
							/* M.H. 10 Apr 2014 Fix for bug #169650: Missing headers in Sorting dialog after clicking reset button in multiple sorting dialog*/
							/*jscs:disable*/
							if (cs[ i ].userSet_currentSortDirection === "undefined" ||
								cs[ i ].userSet_currentSortDirection === undefined) {
								delete cs[ i ].currentSortDirection;
								/*delete cs[ i ][ "userSet_currentSortDirection" ];*/
							} else {
								cs[ i ].currentSortDirection = cs[ i ].userSet_currentSortDirection;
							}
							/*jscs:enable*/
						} else if (key === "userSet_allowSorting") {
							/*A.T. 22 Feb 2012 - Fix for #102444 - The column which "allowSorting" option is false came to be sortable after dataSource is set. */
							cs[ i ].allowSorting = cs[ i ][ key ];
							delete cs[ i ][ key ];
						}
					}
				}
			}
			/* M.H. 24 Mar 2014 Fix for bug #165993: Sorting is not working with initial sorting, autogenerate column and no columns definition
			 default expressions are applied ONLY IF column key is properly defined*/
			if (settings.length === 0 &&
				this.grid.options.autoGenerateColumns &&
				cs.length > 0) {
				for (i = 0; i < cs.length; i++) {
					/* M.H. 12 June 2015 Fix for bug 165993: Sorting is not working when it is initial, and the columns are autogenerated
					 add a new defaultExpression ONLY when currentSortDirection is defined
					 M.H. 12 June 2015 Fix for bug 201176: Column is incorrectly sorted if it has column settings defined and grid has autoGenerateColumns: true*/
					if (!cs[ i ].columnKey || typeof (cs[ i ].currentSortDirection) !== "string") {
						continue;
					}
					dir = (cs[ i ].currentSortDirection.startsWith("asc") ? "asc" : "desc");
					defaultExpressions.push({ fieldName: cs[ i ].columnKey, isSorting: true, dir: dir });
				}
			} else {
				for (i = 0; i < cs.length; i++) {
					for (j = 0; j < settings.length; j++) {
						/* M.H. 6 Jan 2014 Fix for bug #160381: When a column is hidden and you assign new data source to the grid the first column becomes sortable again.
						 in case when there are initially hidden columns, columnIndexes of settings and cs could be equal even if columnKeys are not equal(and are set)*/
						if (settings[ j ].columnKey !== null &&
							settings[ j ].columnKey !== undefined &&
							cs[ i ].columnKey !== null &&
							cs[ i ].columnKey !== undefined) {
							if (settings[ j ].columnKey === cs[ i ].columnKey) {
								break;
							}
						} else if (settings[ j ].columnIndex === cs[ i ].columnIndex) {
							break;
						}
					}
					if (j === settings.length) {
						continue;
					}
					for (key in cs[ i ]) {
						if (cs[ i ].hasOwnProperty(key) && key !== "columnKey" &&
							key !== "columnIndex" && !key.startsWith("userSet")) {
							settings[ j ][ key ] = cs[ i ][ key ];
							if (!suppressUsrOpts) {
								settings[ j ] [ "userSet_" + key ] = cs[ i ][ key ]; // we need to do that so after rebind the original user defined settings are restored
							}
							if (key === "currentSortDirection" && cs[ i ][ key ]) {
								if ($.type(settings[ j ].columnKey) !== "number") {
									/* M.H. 9 May 2012 Fix for bug #111339 - we should not include 2 objects with the same field names in defaultExpressions
									 M.H. 23 Aug 2012 Fix for bug #119413 - if there is sorting expression from group by ignore the sorting expression*/
									isToSkipGroupBySortingExpr = false;
									if (defExpLength > 0) {
										for (k = 0; k < defExpLength; k++) {
											if (defaultExpressions[ k ].fieldName === settings[ j ].columnKey) {
												/* M.H. 11 Jun 2012 Fix for bug #111339 - groupby sorting expressions are with higher priority than sorting expressions of sorting*/
												if (defaultExpressions[ k ].isGroupBy === true) {
													isToSkipGroupBySortingExpr = true;
												} else {
													defaultExpressions.splice(k, 1);
												}
												break;
											}
										}
									}
									if (isToSkipGroupBySortingExpr === true) {
										continue;
									}
									defaultExpressions.push({
										fieldName: settings[ j ].columnKey,
										isSorting: true, dir: cs[ i ][ key ].startsWith("asc") ? "asc" : "desc",
										compareFunc: cs[ i ].compareFunc });
								} else {
									defaultExpressions.push({
										fieldIndex: settings[ j ].columnKey,
										isSorting: true, dir: cs[ i ][ key ].startsWith("asc") ? "asc" : "desc",
										compareFunc: cs[ i ].compareFunc });
								}
							}
						}
					}
				}
			}
			for (i = 0; i < settings.length; i++) {
				if (!settings[ i ].hasOwnProperty("currentSortDirection")) {
					/*jscs:disable*/
					settings[ i ].userSet_currentSortDirection = "undefined";
					/*jscs:enable*/
				}
				/* M.K. Fix for bug 194383: Sorting styles are not applied when sorting from igDataSource */
				for (j = 0; j < this.grid.dataSource.settings.sorting.expressions.length; j++) {
					if (this.grid.dataSource.settings.sorting.expressions[ j ].fieldName ===
						settings[ i ].columnKey) {
						settings[ i ].currentSortDirection =
							this.grid.dataSource.settings.sorting.expressions[ j ].dir;
					}
				}
			}
			/* copy */
			this.options.columnSettings = settings;
			/* store default expressions*/
			this.grid.dataSource.settings.sorting.expressions = defaultExpressions;
			this.grid.dataSource.settings.sorting.defaultFields = defaultExpressions;
		},
		/* grid event handlers*/
		_headerCellRendered: function (event, ui) {
			/* decorate sorting logic, apply CSS classes, etc.
			 apply sortable CSS class*/
			var cs = this._findColumnSetting(ui.columnKey), i, col,
				initialSorting,
				featureChooserInstance = this.grid.element.data("igGridFeatureChooser");
			/* A.T. if the event is fired from another grid, return ! (hierarchical grid scenarios where events are bubbling*/
			if (event.target.id !== this.grid.element[ 0 ].id) {
				return;
			}
			if (ui.isMultiColumnHeader === true) {
				return;
			}
			if (ui.columnKey && cs) {
				this._headers.push({ header: ui.th, index: cs.columnIndex });
				if (cs.allowSorting !== false) {
					ui.th.addClass(this.css.sortableColumnHeader);
					/* apply title attributes */
					if (cs.currentSortDirection === undefined || cs.currentSortDirection === null) {
						ui.th.attr("title", this.options.unsortedColumnTooltip);
					} else {
						ui.th.attr("title", cs.currentSortDirection.startsWith("asc") ?
							this.options.sortedColumnTooltip.replace("${direction}",
							$.ig.GridSorting.locale.ascending) : this.options.sortedColumnTooltip.replace("${direction}",
							$.ig.GridSorting.locale.descending));
					}
					/* render span */
					$("<span></span>").appendTo(ui.th).addClass(this.css.sortIndicator);
					/* keyboard navigation
					 M.H. 11 Jul 2014 Fix for bug #175845: Sorting a column adds "#" to page's URL when you stop click event's propagation of the anchor element.
					$(ui.th).wrapInner("<a href="javascript:void(0);"></a>");
					 if the column has been initially sorted */
					if (cs.currentSortDirection !== undefined) {
						this._applySortStyles(ui.th, cs.columnKey);
					}
				}/* else {
				 M.H. 11 Jul 2014 Fix for bug #175845: Sorting a column adds "#" to page's URL when you stop click event's propagation of the anchor element.
				$(ui.th).wrapInner("<a href="javascript:void(0);"></a>");
				}*/
			}
			/* M.H. 21 March 2012 Fix for bug #105568*/
			if (!this._featureChooserInitialized && featureChooserInstance && this.renderInFeatureChooser) {
				this._featureChooserInitialized = true;
				if (this._featureChooserMenuTogglingHandler) {
					this.grid.element.unbind("iggridfeaturechoosermenutoggling",
						this._featureChooserMenuTogglingHandler);
				}
				this._featureChooserMenuTogglingHandler = $.proxy(this._featureChooserMenuToggling, this);
				this.grid.element.bind("iggridfeaturechoosermenutoggling",
					this._featureChooserMenuTogglingHandler);
				if (featureChooserInstance._isTouchDevice()) {
					for (i = 0; i < this.grid.options.columns.length; i++) {
						col = this.grid.options.columns[ i ];
						cs = this._getColumnSettingsByIndex(i);
						if (cs.allowSorting) {
							/* M.H. 16 March 2012 Fix for bug #105037 */
							if (featureChooserInstance &&
								featureChooserInstance._shouldRenderInFeatureChooser(col.key) === true) {
								initialSorting = cs.currentSortDirection;
								if (initialSorting === null) {
									initialSorting = false;
								}
								featureChooserInstance._renderInFeatureChooser(
									col.key,
									{
										name: "SortingAscending",
										/* M.H. 13 Oct. 2011 Fix for bug #91007*/
										text: this.options.featureChooserSortAsc,
										textHide: this.options.featureChooserSortAsc,
										iconClass: this.css.fcSortIndicatorAscending,
										iconClassOff: this.css.fcSortIndicatorAscending,
										isSelected: initialSorting &&
											typeof initialSorting === "string" ?
											initialSorting.toLowerCase().indexOf("asc") > -1 : false,
										/* M.H. 29 Oct 2012 Fix for bug #120642*/
										method: $.proxy(this._sortAscFromFeatureChooser, this),
										updateOnClickAll: false,
										groupName: "toggle",
										groupOrder: 1,
										order: 0,
										type: "toggle"
									}
								);
								featureChooserInstance._renderInFeatureChooser(
									col.key,
									{
										name: "SortingDescending",
										/* M.H. 13 Oct. 2011 Fix for bug #91007 */
										text: this.options.featureChooserSortDesc,
										textHide: this.options.featureChooserSortDesc,
										iconClass: this.css.fcSortIndicatorDescending,
										iconClassOff: this.css.fcSortIndicatorDescending,
										isSelected: initialSorting && typeof initialSorting === "string" ?
											initialSorting.toLowerCase().indexOf("desc") > -1 : false,
										/* M.H. 29 Oct 2012 Fix for bug #120642*/
										method: $.proxy(this._sortDescFromFeatureChooser, this),
										updateOnClickAll: false,
										groupName: "toggle",
										groupOrder: 1,
										order: 0,
										type: "toggle"
									}
								);
							}
						}
					}
				}
				if (this.options.mode !== "single") {
					for (i = 0; i < this.grid.options.columns.length; i++) {
						col = this.grid.options.columns[ i ];
						if (this._getColumnSettingsByIndex(i).allowSorting) {
							/* M.H. 16 March 2012 Fix for bug #105037 */
							if (featureChooserInstance._shouldRenderInFeatureChooser(col.key) === true) {
								featureChooserInstance._renderInFeatureChooser(
									col.key,
									{
										name: "MultipleSorting",
										text: this.options.featureChooserText,
										iconClass: this.css.featureChooserModalDialogIcon,
										method: $.proxy(this.openMultipleSortingDialog, this),
										groupName: "modaldialog",
										groupOrder: 3,
										order: 2
									}
								);
							}
						}
					}
				}
			}
		},
		_featureChooserMenuToggling: function (event, args) {
			var i, expr, isAsc, hasSorting = false,
				columnKey = args.columnKey,
				featureChooserInstance = this.grid.element.data("igGridFeatureChooser"),
				exprs = this.grid.dataSource.settings.sorting.expressions,
				exprsLength = exprs.length;

			if (!args.isVisible) {
				for (i = 0; i < exprsLength; i++) {
					expr = exprs[ i ];
					if (expr.fieldName === columnKey) {
						hasSorting = true;
						isAsc = false;
						if (expr.dir && expr.dir.toLowerCase().indexOf("asc") > -1) {
							isAsc = true;
						}
						featureChooserInstance._setSelectedState("SortingDescending", columnKey, !isAsc, false);
						featureChooserInstance._setSelectedState("SortingAscending", columnKey, isAsc, false);
					}
				}
				if (!hasSorting) {
					featureChooserInstance._setSelectedState("SortingDescending", columnKey, false, false);
					featureChooserInstance._setSelectedState("SortingAscending", columnKey, false, false);
				}
			}
		},
		_sortAscFromFeatureChooser: function (event, columnKey, isSelected) {
			var featureChooserInstance = this.grid.element.data("igGridFeatureChooser"),
				gridId = this.grid.id(), $th = $("#" + gridId + "_" + columnKey);
			if (!featureChooserInstance) {
				return;
			}
			featureChooserInstance._setSelectedState("SortingDescending", columnKey, false, false);
			if (isSelected) {
				/* M.H. 2 Mar 2015 Fix for bug #188438: igGridSorting events not firing when sorting from the FeatureChooser menu*/
				this.sortColumn(columnKey, "asc", $th);
			} else {
				/* M.H. 2 Mar 2015 Fix for bug #188438: igGridSorting events not firing when sorting from the FeatureChooser menu*/
				this.unsortColumn(columnKey, $th);
			}
		},
		_sortDescFromFeatureChooser: function (event, columnKey, isSelected) {
			var featureChooserInstance = this.grid.element.data("igGridFeatureChooser"),
				gridId = this.grid.id(), $th = $("#" + gridId + "_" + columnKey);
			featureChooserInstance._setSelectedState("SortingAscending", columnKey, false, false);
			if (!featureChooserInstance) {
				return;
			}
			if (isSelected) {
				/* M.H. 2 Mar 2015 Fix for bug #188438: igGridSorting events not firing when sorting from the FeatureChooser menu*/
				this.sortColumn(columnKey, "desc", $th);
			} else {
				/* M.H. 2 Mar 2015 Fix for bug #188438: igGridSorting events not firing when sorting from the FeatureChooser menu*/
				this.unsortColumn(columnKey, $th);
			}
		},
		_fixedColumnsChanged: function (args) {
			var i, grid = this.grid,
				stngs = this.options.columnSettings,
				start = args.start,
				len = args.length,
				at = args.at,
				fixedTable = grid.fixedHeadersTable(),
				isAttachedSortingHandlers = fixedTable.data("attachedSortingHandlers");
			if (isAttachedSortingHandlers !== true) {
				fixedTable.data("attachedSortingHandlers", true);
				fixedTable.delegate("thead th", {
					click: this._clickHandler,
					/*A.T. 21 Jan 2011 - Fix for bug #63737 - Droping a column in the filter row area results in displaying "javascript:void(0)" in the filter area
					dragstart: this._dragStartHandler,*/
					mouseover: this._mouseOverHandler,
					mouseout: this._mouseOutHandler
				});
			}
			grid._rearrangeArray(stngs, start, len, at);
			for (i = 0; i < stngs.length; i++) {
				stngs[ i ].columnIndex = i;
			}
		},
		_columnsRearranged: function () {
			this._initDefaultSettings(true);
		},
		_columnMap: function () {
			/* M.H. 26 March 2012 Fix for bug #105568 */
			var self = this, isMultiple = (self.options.mode !== "single");

			/* function used by the FeatureChooser */
			return $.map(this.grid.options.columns, function (col, index) {
				/*M.H. 14 Feb 2012 - Fix for #101696 */
				var allowSorting = false;
				if (isMultiple && self._getColumnSettingsByIndex(index).allowSorting) {
					allowSorting = true;
				}
				return { "columnKey": col.key, "enabled": allowSorting };
			});
		},
		_findColumnSetting: function (key) {
			var i;
			for (i = 0; i < this.options.columnSettings.length; i++) {
				if (this.options.columnSettings[ i ].columnKey === key) {
					return this.options.columnSettings[ i ];
				}
			}
		},
		_dataRendered: function () {
			var i, cs, expr, col, colKey, gridId = this.grid.element[ 0 ].id, ignoreActiveSelection;
			if (!this._loadingIndicator) {
				this._initLoadingIndicator();
			}
			/* apply sorting styles */
			expr = this._sortingExpressions;
			/* fire column sorted if rendering has been triggered by sorting*/
			if (this._shouldFireColumnSorted) {
				this._trigger(this.events.columnSorted, null,
					{ columnKey: this._curColKey,
						direction: this._curSortDir,
						owner: this,
						expressions: this.grid.dataSource.settings.sorting.expressions });
				this._shouldFireColumnSorted = false;
			}
			this._loadingIndicator.hide();
			if (this._curColKey !== undefined && this._curColKey !== null && this._currentHeader) {
				this._applySortStyles(this._currentHeader, this._curColKey);
			} else if (this.options.persist && this._restoreSorting && expr && expr.length > 0) {
				for (i = 0; i < expr.length; i++) {
					if (expr[ i ].isGroupBy) {
						continue;
					}
					colKey = expr[ i ].fieldName;
					cs = this._findColumnSetting(colKey);
					if (cs) {
						cs.currentSortDirection = expr[ i ].dir;
					}
					ignoreActiveSelection = true;
					col = $("#" + gridId + "_" + colKey);

					/*M.K. fix for bug 216337: Sorting style is lost if the grid is data bound and the page size or index is changed */
					this._curColKey = colKey;
					this._currentHeader = col;

					/*M.K. 02/10/2015 Fix for bug 187294:After sorting via multiple columns and re-databinding all previously sorted columns get the active style applied to their headers. */
					if (i === expr.length - 1) {
						col.addClass(this.css.sortableColumnHeaderActive);
						this._currentActiveHeader = col;
						ignoreActiveSelection = false;
						this._curColKey = colKey;
					}
					/*M.K. If the column is hidden getting its index from data('columnIndex') returns incorrect result. Now we get the index from the column settings.
					// M.H. 22 July 2015 Fix for bug 203204: Sorting styles are not applied correctly when rebinding igHierarchicalGrid */
					this._applySortStyles(col, colKey, ignoreActiveSelection);
				}
				this._restoreSorting = false;
			}
			if (this._hc === undefined) {
				this._hc = this.grid.container().find(".ui-iggrid-expandheadercell").length > 0;
			}
			/* if there is virtualization enabled, set the scrolltop to 0.
			A.T. 2 March - fix for bug #99940*/
			if ((this.grid.options.rowVirtualization || this.grid.options.virtualization) &&
				!this.grid._persistVirtualScrollTop) {
				$("#" + this.grid.element.attr("id") + "_scrollContainer").scrollTop(0);
			}
		},
		_onUIDirty: function (e, args) {
			/* M.H. 19 Aug 2014 Fix for bug #166879: Changing page size with remote paging does not persist remote sorting and filtering */
			if (this.options.persist && this.options.type === "remote") {
				return;
			}
			/* if sorting itself has triggered the event, return*/
			var i, j, exprs = this.grid.dataSource.settings.sorting.expressions,
				defaultExprs = this.grid.dataSource.settings.sorting.defaultFields,
				cs = this.options.columnSettings, skip = false;
			/* A.T. 23 Aug 2011 - add additional checks so that events don't propagate across hierarchical grids*/
			if (args.owner === this || args.owner.element[ 0 ].id !== this.element[ 0 ].id) {
				return;
			}
			this._curColKey = null;
			this._currentHeader = null;
			this._clearUi(true);
			/*A.T. Fix for bug #91651
			 clear data source sorting expressions*/
			for (i = 0; i < exprs.length; i++) {
				skip = false;
				for (j = 0; j < cs.length; j++) {
					/*jscs:disable*/
					if (cs[ j ].columnKey === exprs[ i ].fieldName && cs[ j ].userSet_currentSortDirection) {
						/*jscs:enable*/
						skip = true;
						break;
					}
				}
				if (!skip) {
					if (exprs.length > i) {
						$.ig.removeFromArray(exprs, i);
					}
					if (defaultExprs.length > i) {
						$.ig.removeFromArray(defaultExprs, i);
					}
				}
			}
		},
		_clearUi: function () {
			var i, header, csd, isMCH = this.grid._isMultiColumnGrid, persist = this.options.persist;
			for (i = 0; this._headers && i < this._headers.length; i++) {
				/* A.T. fix for bug 72281 - (Related to bug #67517)*/
				/*jscs:disable*/
				csd = this.options.columnSettings[ i ].userSet_currentSortDirection;
				/*jscs:enable*/
				/* M.H. 13 Mar 2014 Fix for bug #166980: When data bind sorting applies an extra sorting indicator when there are multi column headers*/
				if ((isMCH || persist) && this.options.columnSettings[ i ].columnKey) {
					header = $("#" + this.grid.element[ 0 ].id + "_" + this.options.columnSettings[ i ].columnKey);
				} else {
					header = this._headers[ i ].header;
				}
				if (csd === undefined || csd === null || csd === "undefined") {
					/*this._clearSortStates(this._headers[ i ].header, resetAll === true ? null : this._headers[ i ].index);*/
					this._clearSortState(header, i, this.options.columnSettings[ i ].columnKey);
					/*A.T. Fix for bug #73496
					 M.H. 26 March 2012 Fix for bug #105568*/
					if (this.options.mode !== "single") {
						delete this.options.columnSettings[ i ].currentSortDirection;
						/* exclude the expression from the expressions in the data source*/
						this._excludeExpr(this.options.columnSettings[ i ].columnKey);
					}
				} else {
					/* apply sort State*/
					this._applySortStyles(header, this.options.columnSettings[ i ].columnKey);
				}
				header.removeClass(this.css.sortableColumnHeaderActive)
					.removeClass(this.css.sortableColumnHeaderHover)
					.addClass(this.grid.css.headerClass);
				/* A.T. 3 May - fix for #102444*/
				if (this.options.columnSettings[ i ].allowSorting) {
					header.addClass(this.css.sortableColumnHeader);
				}
			}
		},
		_virtualHorizontalScroll: function (event, args) {
			/* get the current col index from args, and reinitialize the header cells */
			var start = args.startColIndex, end = args.endColIndex,
				i, j, cs = this.options.columnSettings, header,
				ths = this.grid.headersTable().find("thead > tr").first().find("th").not("[ data-skip=true ]"),
				visibleColumns = this.grid._visibleColumns();
			for (i = 0; i < ths.length; i++) {
				header = ths.eq(i);
				/* 1. clear && 2. apply styles
				 set correct key/values using data()*/
				this._clearSortState(header, i);
				header.removeClass(this.css.sortableColumnHeaderActive)
					.removeClass(this.css.sortableColumnHeaderHover)
					.addClass(this.grid.css.headerClass)
					.addClass(this.css.sortableColumnHeader);
			}
			for (i = start; i <= end; i++) {
				header = ths.eq(i - start);
				header.data("columnIndex", i);
				for (j = 0; j < cs.length; j++) {
					if (cs[ j ].currentSortDirection && cs[ j ].columnKey === visibleColumns[ i ].key) {
						this._applySortStyles(header, cs[ j ].columnKey);
						/* don't break cause it may be multi-col sorting*/
					}
				}
			}
		},
		_columnsCollectionModified: function (event, args) {
			/* M.H. 3 Dec 2013 Fix for bug #158002: Sorting and Summary styles are applied after sorting of parent grid*/
			if (args.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			/* we need to reset column settings" indices*/
			var i, j, found, colKey, cs, col;

			for (i = 0; i < this.options.columnSettings.length; i++) {
				/* find the column corresponding to this column setting*/
				j = 0;
				found = false;
				for (j = 0; j < this.grid._visibleColumns().length; j++) {
					if (this.grid._visibleColumns()[ j ].key === this.options.columnSettings[ i ].columnKey) {
						found = true;
						colKey = this.grid._visibleColumns()[ j ].key;
						break;
					}
				}
				if (found) {
					this.options.columnSettings[ i ].columnIndex = j;
					/* M.H. 28 March 2012 Fix for bug #106392*/
					cs = this._findColumnSetting(colKey);

					if (cs &&
							cs.currentSortDirection !== undefined &&
							this.options.applySortedColumnCss !== false) {
						/* M.H. 30 March 2012 Fix for bug #106392 */
						col = $("#" + this.grid.element[ 0 ].id + "_" + colKey);

						this._applySortStyles(col, colKey, true);
					}
				}
			}
			/* M.H. 10 April 2012 Fix for bug #107671
			M.K. Fix for bug 187149: Style for sorted column is not preserved after hiding and showing the column.
			Here the logic would skip the column with index 0. To prevent that I"ve added a check for this case.*/
			if (this._curColKey) {
				col = $("#" + this.grid.element[ 0 ].id + "_" + this._curColKey);
				this._applySortStyles(col, this._curColKey);
			}

			/* A.T. 10 Oct Fix for #91161*/
			$("#" + this.element[ 0 ].id + " thead th").removeClass(this.css.sortableColumnHeaderHover);
		},
		/* M.H. 28 May 2014 Fix for bug #172200: Memory leak when using the features of the grid */
		_detachEvents: function () {
			if (this._headerCellRenderedHandler) {
				this.grid.element.unbind("iggridheadercellrendered", this._headerCellRenderedHandler);
			}
			if (this._virtualHorizontalScrollHandler) {
				this.grid.element.unbind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			}
			if (this._uiDirtyHandler) {
				this.grid.element.unbind("iggriduidirty", this._uiDirtyHandler);
			}
			if (this._headerRenderedHandler) {
				this.grid.element.unbind("iggridheaderrendered", this._headerRenderedHandler);
			}
			if (this._columnsCollectionModifiedHandler) {
				/* M.H. 29 Oct 2012 Fix for bug #120642*/
				this.grid.element.unbind("iggridcolumnscollectionmodified",
					this._columnsCollectionModifiedHandler);
			}
			if (this._columnsRearangedHandler) {
				this.grid.element.unbind("iggrid_columnsmoved",
					this._columnsRearangedHandler);
			}
			/* M.H. 23 Sep 2013 Fix for bug #152875: igFeatureChooserPopover doesn't destroy correctly*/
			if (this._featureChooserMenuTogglingHandler) {
				this.grid.element.unbind("iggridfeaturechoosermenutoggling",
					this._featureChooserMenuTogglingHandler);
			}
		},
		destroy: function () {
			/* unbind events, removes added sorting elements, etc. */
			var i, a, fc, header, span, text,
				modalDialog = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog");
			if (!this.grid) {
				return;
			}
			$("#" + this.element[ 0 ].id + " thead th").unbind("dragstart", this._dragStartHandler);
			$("#" + this.element[ 0 ].id + "_headers thead th").unbind("dragstart", this._dragStartHandler);
			$(document).undelegate("#" + this.element[ 0 ].id + "_headers thead th", {
				click: this._clickHandler,
				mouseover: this._mouseOverHandler,
				mouseout: this._mouseOutHandler
			});
			$("#" + this.element[ 0 ].id).undelegate("thead th", {
				click: this._clickHandler,
				mouseover: this._mouseOverHandler,
				mouseout: this._mouseOutHandler
			});
			/* M.H. 29 Oct 2012 Fix for bug #120642*/
			$("#" + this.element[ 0 ].id).undelegate("thead th", {
				keydown: this._keyDownHandler,
				focus: this._focusHandler,
				blur: this._blurHandler
			});
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			$(document).undelegate("#" + this.element[ 0 ].id + "_headers thead th", {
				keydown: this._keyDownHandler,
				focus: this._focusHandler,
				blur: this._blurHandler
			});
			/* M.H. 28 May 2014 Fix for bug #172200: Memory leak when using the features of the grid*/
			this._detachEvents();
			delete this._blurHandler;
			delete this._clickHandler;
			delete this._headerCellRenderedHandler;
			delete this._focusHandler;
			delete this._keyDownHandler;
			delete this._mouseOutHandler;
			delete this._mouseOverHandler;
			delete this._dragStartHandler;
			delete this._uiDirtyHandler;
			delete this._virtualHorizontalScrollHandler;
			delete this._headerRenderedHandler;
			delete this._columnsRearangedHandler;
			/* remove sorted classes for columns */
			this.grid.element.find(".ui-iggrid-colasc").removeClass("ui-iggrid-colasc ui-state-highlight");
			this.grid.element.find(".ui-iggrid-coldesc").removeClass("ui-iggrid-coldesc ui-state-highlight");
			this._clearUi(true);
			/* clear styling and markup */
			for (i = 0; this._headers && i < this._headers.length; i++) {
				header = this._headers[ i ].header;
				header.removeClass("ui-iggrid-sortableheader ui-state-default" +
					" ui-state-active ui-state-hover ui-state-focus");
				header.attr("title", "");
				span = header.find("a span");
				text = span.text();
				/* span.remove();*/
				a = header.find("a:not([ th-remove-focus ])");
				$("<span>" + text + "</span>").appendTo(header).addClass("ui-iggrid-headertext");
				a.remove();
				/* delete header;*/
			}
			fc = this.grid.element.data("igGridFeatureChooser");
			if (fc && this.renderInFeatureChooser) {
				fc._removeFeature("SortingAscending");
				fc._removeFeature("SortingDescending");
				fc._removeFeature("MultipleSorting");
			}
			this._headers = null;
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			if (this._loadingIndicator) {
				delete this._loadingIndicator;
			}
			modalDialog.igGridModalDialog("getCaptionButtonContainer");
			modalDialog.remove();

			$.Widget.prototype.destroy.apply(this, arguments);
			return this;
		},
		_renderMultipleSortingDialog: function () {
			var $buttonOK, containment,
				self = this,
				o = this.options,
				modalDialogId = this.grid.element[ 0 ].id + "_multiplesorting_modalDialog",
				$captionButtonContainer,
				modalDialog;

			if (this.options.sortingDialogContainment === "owner") {
				containment = this.grid.container();
			} else {
				containment = "window";
			}
			$("#" + modalDialogId).remove();
			modalDialog = $("<div></div>")
				.appendTo(this.grid._rootContainer())
				.attr("id", modalDialogId);
			modalDialog.igGridModalDialog({
				containment: containment,
				buttonApplyText: o.modalDialogButtonApplyText,
				buttonCancelText: o.modalDialogButtonCancelText,
				renderFooterButtons: !o.modalDialogSortOnClick,
				modalDialogCaptionText: o.modalDialogCaptionText,
				modalDialogWidth: o.modalDialogWidth,
				modalDialogHeight: o.modalDialogHeight,
				animationDuration: o.modalDialogAnimationDuration,
				/* M.H. 8 May 2012 Fix for bug #110344 */
				gridContainer: this.grid.container(),
				modalDialogOpening: function (event, args) {
					/* M.H. 21 March 2012 Fix for bug #105460 */
					var noCancel = self._trigger(self.events.modalDialogOpening, null,
						{ modalDialogElement: modalDialog, owner: self });
					if (noCancel) {
						self._multiplesortingDialogOpening(event, args);
						self._trigger(self.events.modalDialogOpened, null,
							{ modalDialogElement: modalDialog, owner: self });
					}
					return noCancel;
				},
				modalDialogMoving: function (e, ui) {
					self._trigger(self.events.modalDialogMoving, null,
						{
							modalDialogElement: e.target,
							owner: self,
							originalPosition: ui.originalPosition,
							position: ui.position
						});
				},
				modalDialogClosing: function () {
					return self._trigger(self.events.modalDialogClosing, null,
						{ modalDialogElement: modalDialog, owner: self });
				},
				modalDialogClosed: function () {
					self._trigger(self.events.modalDialogClosed, null,
						{ modalDialogElement: modalDialog, owner: self });
				}
			});
			if (o.modalDialogSortOnClick) {
				/* show close button */
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
			} else {
				$captionButtonContainer = modalDialog.igGridModalDialog("getCaptionButtonContainer");
				$buttonOK = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog_footer_buttonok");
				$buttonOK.bind("igbuttonclick", function (e) {
					self._multiplesortingDialogButtonOKClick(e); e.preventDefault();
				});
			}
		},
		openMultipleSortingDialog: function () {
			/* Open multiple sorting dialog */
			var modalDialog = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog");
			modalDialog.igGridModalDialog("openModalDialog");
		},
		closeMultipleSortingDialog: function () {
			/* Close multiple sorting dialog */
			var modalDialog = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog");
			modalDialog.igGridModalDialog("closeModalDialog");
		},
		_multiplesortingDialogOpening: function () {
			this._tempExpr = this.grid.dataSource.settings.sorting.expressions.slice(0);
			this.renderMultipleSortingDialogContent(true);
		},
		renderMultipleSortingDialogContent: function (isToCallEvents) {
			/* Renders content of multiple sorting dialog - sorted and unsorted columns. */
			var $content, $sortedColumns, $unsortedColumns, i, j, noCancel = true,
				self = this,
				/* o = this.options,*/
				columns = this.grid.options.columns,
				css = this.css,
				se = this.grid.dataSource.settings.sorting.expressions,
				seLength = se.length,
				sortedColumns = [  ],
				modalDialog = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog");
			if (isToCallEvents) {
				noCancel = this._trigger(this.events.modalDialogContentsRendering, null,
					{ modalDialogElement: modalDialog, owner: this });
			}
			if (noCancel) {
				this.removeDialogClearButton();
				$content = modalDialog.igGridModalDialog("getContent");
				$content.empty();
				$sortedColumns = $("<div></div>")
					.attr("id", this.grid.element[ 0 ].id + "_multiplesorting_modalDialog_sortedcolumns")
					.addClass(css.dialogSortedColumns)
					.appendTo($content);
				$unsortedColumns = $("<div></div>")
					.attr("id", this.grid.element[ 0 ].id + "_multiplesorting_modalDialog_unsortedcolumns")
					.addClass(css.dialogUnsortedColumns)
					.appendTo($content);

				$("<ul></ul>")
					.addClass(css.dialogSortedColumnsList)
					.appendTo($sortedColumns);
				$("<ul></ul>")
					.addClass(css.dialogUnsortedColumnsList)
					.appendTo($unsortedColumns);

				$.each(columns, function (columnIndex, column) {
					var cs, columnIdentifier, direction;
					cs = self._getColumnSettingsByIndex(columnIndex);
					if (cs === null || cs === undefined || cs.allowSorting === false) {
						return true;
					}
					columnIdentifier = cs.columnKey || cs.columnIndex || column.key;
					for (i = 0; i < self._tempExpr.length; i++) {
						if (self._tempExpr[ i ].fieldName === columnIdentifier) {
							break;
						}
					}
					if (i !== self._tempExpr.length) {
						sortedColumns.push(
							{ column: column,
								columnIndex: cs.columnIndex,
								dir: self._tempExpr[ i ].dir,
								columnIdentifier: columnIdentifier });
					} else {
						/* M.H. 16 Sep 2013 Fix for bug #139357: Sorting dialog doesn't take into account the firstSortDirection option
						 M.H. 16 July 2015 Fix for bug 201744: Sorting firstSortDirection does not work  when sorting via the dialog*/
						direction = cs.currentSortDirection || cs.firstSortDirection ||
							self.options.firstSortDirection;
						self._renderDialogUnsortedColumn(column, cs.columnIndex, direction, columnIdentifier);
					}
				});
				/* order of sorting depends on order in sort expressions - that's why show columns in modal dialog according to order in sorting expressions */
				for (i = 0; i < seLength; i++) {
					for (j = 0; j < sortedColumns.length; j++) {
						if (se[ i ].fieldName === sortedColumns[ j ].columnIdentifier) {
							/*orderedSortedColumns.push(sortedColumns[ j ]);*/
							self._renderDialogSortedColumn(
								sortedColumns[ j ].column,
								sortedColumns[ j ].columnIndex,
								sortedColumns[ j ].dir,
								sortedColumns[ j ].columnIdentifier);
							break;
						}
					}
				}

				/*this._addSlideButtonSortingColumns();*/
				if (isToCallEvents) {
					this._trigger(this.events.modalDialogContentsRendered, null,
						{ modalDialogElement: modalDialog, owner: this });
				}
				/* M.H. 28 March 2012 Fix for bug #107039*/
				if (this._checkRenderButtonReset()) {
					this._renderDialogButtonClearAll();
				}
			}
		},
		_addSlideButtonSortingColumns: function () {
			var css = this.css,
				o = this.options,
				$sortedColumns = $("#" + this.grid.element[ 0 ].id +
				"_multiplesorting_modalDialog_sortedcolumns"),
				buttonId = this.grid.element[ 0 ].id +
				"_multiplesorting_modalDialog_slidebutton",
				$button,
				innerHTML;

			innerHTML = "<div class='" + css.dialogSlideArea + "'>" +
				"	<div class='" + css.dialogSlideAreaContainer + "'>" +
				"		<button id='" + buttonId + "' class='" + css.dialogButtonSlide +
				"' role='button' aria-disabled='false' title='' + o.dialogButtonSlideCaption + ''>" +
				"			<span class='" + css.dialogButtonSlideContainer + "'></span>" +
				"			<span class='ui-button-text'>" + o.dialogButtonSlideCaption + "</span>" +
				"		</button>" +
				"	</div>" +
				"</div>";
			$(innerHTML).insertAfter($sortedColumns);
			$button = $("#" + buttonId);
			$button.bind({
				click: function () {
					$sortedColumns.slideToggle("slow");
					$(this).find("span:eq(0)").toggleClass(css.dialogButtonSlideUp);
					return false;
				}
			});
		},
		_renderDialogSortedColumn: function (column, columnIndex, direction, columnIdentifier) {
			var self = this, $li, css = this.css, $buttonUnsort, liHTML,
				o = this.options,
				buttonAscDescId = this.grid.element[ 0 ].id + "_" + columnIdentifier +
				"_multiplesorting_modalDialog_sortedcolumns_buttonascdesc",
				buttonUnsortId = this.grid.element[ 0 ].id + "_" + columnIdentifier +
				"_multiplesorting_modalDialog_sortedcolumns_buttonunsort",
				$ul = $("#" + this.grid.element[ 0 ].id +
				"_multiplesorting_modalDialog_sortedcolumns ul");

			/* M.H. 12 Feb 2013 Fix for bug #130430 - Related with bug 122446
			 The cruel idea is to cause the IE8 to flush the dialog rendering by just reading the width value*/
			$ul.css("width");
			$li = $("<li  tabIndex='0'></li>")
				.attr("id", self.grid.element[ 0 ].id + "_" + columnIdentifier + "_multiplesorting_sorted_li")
				.addClass(css.dialogSortedColumnsItem);
			$li.appendTo($ul);
			/* this._insertColumnModalDialog($ul, $li, columnIndex);*/
			if (columnIndex !== null && columnIndex !== undefined) {
				$li.attr("li-order", columnIndex);
			}
			liHTML = "<span id='" + buttonAscDescId + "' class='" + css.dialogButtonAsc +
				"' role='button' title='" + o.modalDialogCaptionButtonDesc + "' >" +
				"	<span class='" + css.dialogButtonAscIcon + "'></span>" +
				"</span>" +
				"<span class='" + css.dialogSortedColumnTextContainer + "'>" + column.headerText + "</span>" +
				"<button id='" + buttonUnsortId + "' class='" + css.dialogButtonUnsort +
				"' role='button' title='" + o.modalDialogCaptionButtonUnsort + "' >" +
				"	<span class='" + css.dialogButtonUnsortContainer + "'></span>" +
				"	<span class='ui-button-text'>" + o.modalDialogCaptionButtonUnsort + "</span>" +
				"</button>";
			$li.bind({
				click: function (e) {
					self._curColKey = columnIdentifier;
					self._dialogButtonAscDescClick(columnIdentifier);
					/* M.H. 28 March 2012 Fix for bug #107039*/
					if (self._checkRenderButtonReset()) {
						self._renderDialogButtonClearAll();
					} else {
						self.removeDialogClearButton();
					}

					e.preventDefault();
					e.stopPropagation();
				}
			});
			$li.html(liHTML);
			$buttonUnsort = $("#" + buttonUnsortId);
			if (direction !== undefined) {
				if (direction.indexOf("asc") !== -1) {
					self._setDialogButtonAscDesc(true, columnIdentifier);
					self._setTempExpr(columnIdentifier, "asc");
				} else {
					self._setDialogButtonAscDesc(false, columnIdentifier);
					self._setTempExpr(columnIdentifier, "desc");
				}
			} else {
				self._setDialogButtonAscDesc(true, columnIdentifier);
				self._setTempExpr(columnIdentifier, "asc");
			}
			$buttonUnsort.bind({
				keydown: function (e) {
					if (e.keyCode === $.ui.keyCode.ENTER || e.keyCode === $.ui.keyCode.SPACE) {
						e.target.click();
						e.preventDefault();
						e.stopPropagation();
					}
				},
				click: function (e) {
					var noCancel, gridId = self.grid.element[ 0 ].id, $nextLi,
						modalDialog = $("#" + gridId + "_multiplesorting_modalDialog");

					noCancel = self._trigger(self.events.modalDialogButtonUnsortClick, e,
						{ modalDialogElement: modalDialog, owner: self, columnKey: columnIdentifier });
					if (noCancel) {
						$li.remove();
						if (o.modalDialogSortOnClick === true) {
							self.unsortColumn(columnIdentifier, $("#" + gridId + "_" + columnIdentifier));
						} else {
							self._setTempExpr(columnIdentifier);
						}
						$nextLi = self._renderDialogUnsortedColumn(column, columnIndex, direction, columnIdentifier);
						$nextLi.find(":focusable").first().focus(1);
						/* M.H. 28 March 2012 Fix for bug #107039 */
						if (self._checkRenderButtonReset()) {
							self._renderDialogButtonClearAll();
						} else {
							self.removeDialogClearButton();
						}
					}
					e.preventDefault();
					e.stopPropagation();
				},
				mouseover: function () {
					if (!$(this).hasClass(css.dialogButtonsHover)) {
						$(this).addClass(css.dialogButtonsHover);
					}
				},
				mouseout: function () {
					if ($(this).hasClass(css.dialogButtonsHover)) {
						$(this).removeClass(css.dialogButtonsHover);
					}
				}
			});
			return $li;
		},
		_dialogButtonAscDescClick: function (columnIdentifier) {
			var o = this.options,
				noCancel,
				modalDialog = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog"),
				$buttonAscDesc = $("#" + this.grid.element[ 0 ].id +
				"_" + columnIdentifier + "_multiplesorting_modalDialog_sortedcolumns_buttonascdesc"),
				isAsc = $buttonAscDesc.data("isAsc");

			noCancel = this._trigger(this.events.modalDialogSortingChanged, null,
				{ modalDialogElement: modalDialog, owner: this, columnKey: columnIdentifier, isAsc: !isAsc });
			if (noCancel) {
				if (isAsc === true) {
					if (o.modalDialogSortOnClick === true) {
						this.sortColumn(columnIdentifier,
							"descending", this._getHeaderCellByIdentifier(columnIdentifier));
					} else {
						this._setTempExpr(columnIdentifier, "desc");
					}
					this._setDialogButtonAscDesc(false, columnIdentifier);
				} else {
					if (o.modalDialogSortOnClick === true) {
						this.sortColumn(columnIdentifier, "ascending",
							this._getHeaderCellByIdentifier(columnIdentifier));
					} else {
						this._setTempExpr(columnIdentifier, "asc");
					}
					this._setDialogButtonAscDesc(true, columnIdentifier);
				}

			}
		},
		_setDialogButtonAscDesc: function (isAsc, columnIdentifier) {
			var css = this.css,
				buttonAscDescId = this.grid.element[ 0 ].id + "_" + columnIdentifier +
				"_multiplesorting_modalDialog_sortedcolumns_buttonascdesc",
				$buttonAscDesc = $("#" + buttonAscDescId),
				$spanIcon = $buttonAscDesc.find("span:eq(0)");

			$buttonAscDesc.data("isAsc", isAsc);
			/* M.H. 20 March 2012 Fix for bug #105078 */
			if (isAsc === true) {
				$buttonAscDesc
					.attr("title", this.options.modalDialogCaptionButtonDesc)
					.removeClass(css.dialogButtonDesc)
					.addClass(css.dialogButtonAsc);
				$spanIcon
					.removeClass(css.dialogButtonDescIcon)
					.addClass(css.dialogButtonAscIcon);
				this._setTempExpr(columnIdentifier, "asc");
			} else {
				$buttonAscDesc
					.attr("title", this.options.modalDialogCaptionButtonAsc)
					.removeClass(css.dialogButtonAsc)
					.addClass(css.dialogButtonDesc);
				$spanIcon
					.removeClass(css.dialogButtonAscIcon)
					.addClass(css.dialogButtonDescIcon);
				this._setTempExpr(columnIdentifier, "desc");
			}
		},
		_setTempExpr: function (colKey, dir) {
			var i, expr = this._tempExpr;

			for (i = 0; i < expr.length; i++) {
				if (expr[ i ].fieldName === colKey) {
					expr.splice(i, 1);
					if (dir === null || dir === undefined) {
						this._tempExpr = expr;
						return;
					}
				}
			}
			this._tempExpr = expr.concat([ { fieldName: colKey, isSorting: true, dir: dir } ]);
		},
		_renderDialogUnsortedColumn: function (column, columnIndex, direction, columnIdentifier) {
			var self = this, $li, $a,
				o = this.options,
				css = this.css,
				$ul = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog_unsortedcolumns ul");
			/* M.H. 12 Feb 2013 Fix for bug #130430 - Related with bug 122446
			 The cruel idea is to cause the IE8 to flush the dialog rendering by just reading the width value */
			$ul.css("width");
			$li = $("<li></li>")
				.attr("id", self.grid.element[ 0 ].id + "_" + columnIdentifier + "_multiplesorting_unsorted_li")
				.addClass(css.dialogUnsortedColumnsItem)
				.append("<span class='" + css.dialogUnsortedColumnsSortByButton +
				"'><a href='#'></a></span> <span class='" + css.dialogItemText + "'>" +
				column.headerText + "</span>");
			this._insertColumnModalDialog($ul, $li, columnIndex);
			if (columnIndex !== null && columnIndex !== undefined) {
				$li.attr("li-order", columnIndex);
			}
			$a = $li.find("a:first");
			$a.addClass(css.modalDialogSortByColumn);
			$a.html(o.modalDialogSortByButtonText);
			$li.bind({
				keydown: function (e) {
					if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
						$li.click();
						e.preventDefault();
						e.stopPropagation();
					}
				},
				click: function (e) {
					var noCancel,
						modalDialog = $("#" + self.grid.element[ 0 ].id + "_multiplesorting_modalDialog"),
						$nextLi;
					noCancel = self._trigger(self.events.modalDialogSortClick, e,
						{ modalDialogElement: modalDialog, owner: self, columnKey: columnIdentifier });

					if (noCancel) {
						$li.remove();
						$nextLi = self._renderDialogSortedColumn(column, columnIndex, direction, columnIdentifier);
						$nextLi.find(":focusable").first().focus(1);
						if (o.modalDialogSortOnClick) {
							/* M.H. 9 April 2012 Fix for bug #108488 */
							self.sortColumn(columnIdentifier, null, self._getHeaderCellByIdentifier(columnIdentifier));
							/*							self.grid.dataSource.settings.sorting.expressions = self._tempExpr;
														self.sortMultiple(); */
						} else {
							/* M.H. 28 March 2012 Fix for bug #107039 */
							if (self._checkRenderButtonReset()) {
								self._renderDialogButtonClearAll();
							} else {
								self.removeDialogClearButton();
							}
						}
					}
					e.preventDefault();
					e.stopPropagation();
				}
			});
			return $li;
		},
		_checkRenderButtonReset: function () {
			/* M.H. 28 March 2012 Fix for bug #107039 */
			var i, j, cs, columnKey,
				expr = this._tempExpr;
			/* M.H. 19 July 2012 Fix for bug #105514 */
			this._isResetClick = false;
			for (i = 0; i < this.options.columnSettings.length; i++) {
				cs = this.options.columnSettings[ i ];
				if (cs.allowSorting === false) {
					continue;
				}
				columnKey = cs.columnKey;

				for (j = 0; j < expr.length; j++) {
					if (expr[ j ].fieldName === columnKey) {
						/*jscs:disable*/
						if (cs.userSet_currentSortDirection !== expr[ j ].dir) {
							/*jscs:enable*/
							return true;
						}
						break;
					}
				}
				/* M.H. 10 Apr 2014 Fix for bug #169650: Missing headers in Sorting dialog after clicking reset button in multiple sorting dialog */
				/*jscs:disable*/
				if (j === expr.length && cs.userSet_currentSortDirection !== "undefined" &&
					cs.userSet_currentSortDirection !== undefined) {
					/*jscs:enable*/
					return true;
				}
			}
			/* M.H. 19 July 2012 Fix for bug #105514 */
			this._isResetClick = true;
			return false;
		},
		removeDialogClearButton: function () {
			/* Remove clear button for multiple sorting dialog */
			$("#" + this.grid.element[ 0 ].id + "_sorting_modalDialog_reset_button").remove();
		},
		_renderDialogButtonClearAll: function () {
			if (this.options.modalDialogSortOnClick === true) {
				return;
			}
			var self = this,
				o = this.options,
				resetButtonId = self.grid.element[ 0 ].id + "_sorting_modalDialog_reset_button",
				modalDialog = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog"),
				$captionButtonContainer,
				$resetButton;

			if ($("#" + resetButtonId).length === 0) {
				$captionButtonContainer = modalDialog.igGridModalDialog("getCaptionButtonContainer");
				$resetButton = $("<button></button>")
					.attr("id", resetButtonId)
					.appendTo($captionButtonContainer);

				$resetButton.igButton({
					labelText: o.modalDialogResetButtonLabel,
					click: function (e) {
						var noCancel, i, cs = self.options.columnSettings;
						noCancel = self._trigger(self.events.modalDialogButtonResetClick, e,
							{ modalDialogElement: modalDialog, owner: self });
						if (noCancel) {
							/* M.H. 21 March 2012 Fix for bug #105464
							 M.H. 28 March 2012 Fix for bug #107039 */
							self._tempExpr = [  ];
							for (i = 0; i < cs.length; i++) {
								/* M.H. 10 Apr 2014 Fix for bug #169650: Missing headers in Sorting dialog after clicking reset button in multiple sorting dialog */
								if (cs[ i ].allowSorting !== false &&
									/*jscs:disable*/
										cs[ i ].userSet_currentSortDirection !== "undefined" &&
										cs[ i ].userSet_currentSortDirection !== undefined) {
									self._tempExpr.push({
										fieldName: cs[ i ].columnKey,
										isSorting: true,
										dir: cs[ i ].userSet_currentSortDirection });
									/*jscs:enable*/
								}
							}
							self.renderMultipleSortingDialogContent(false);
							self.removeDialogClearButton();
							/* M.H. 19 July 2012 Fix for bug #105514 */
							self._isResetClick = true;
						}
					}
				});
			}
		},
		_multiplesortingDialogButtonOKClick: function () {
			var noCancel, modalDialog = $("#" + this.grid.element[ 0 ].id + "_multiplesorting_modalDialog");

			noCancel = this._trigger(this.events.modalDialogButtonApplyClick, null,
				{ modalDialogElement: modalDialog, owner: this, columnsToSort: this._tempExpr });
			if (noCancel) {
				this.grid.dataSource.settings.sorting.expressions = this._tempExpr;
				this.sortMultiple();
				modalDialog.igGridModalDialog("closeModalDialog");
			}
		},
		_insertColumnModalDialog: function ($ul, $li, pos) {
			var i, $currLi, currPos, listItems = $ul.find("li"), listItemsLength = listItems.length;
			if (listItemsLength === 0 || pos === null || pos === undefined) {
				$li.appendTo($ul);
				return;
			}
			for (i = 0; i < listItemsLength; i++) {
				$currLi = $(listItems[ i ]);
				currPos = $currLi.attr("li-order");
				if (currPos >= pos) {
					$li.insertBefore($currLi);
					break;
				}
			}
			if (i === listItemsLength) {
				$li.appendTo($ul);
			}
		},
		_getColumnSettings: function (index, key) {
			var i, foundByIndex = null;
			for (i = 0; i < this.options.columnSettings.length; i++) {
				if (this.options.columnSettings[ i ].columnKey === key) {
					foundByIndex = this.options.columnSettings[ i ];
					break;
				}

				if (this.options.columnSettings[ i ].columnIndex === index) {
					foundByIndex = this.options.columnSettings[ i ];
					break;
				}
			}
			return foundByIndex;
		},
		_getColumnSettingsByIndex: function (index) {
			var key = this.grid.options.columns[ index ].key;

			return this._getColumnSettings(index, key);
		},
		_headerRendered: function (event, ui) {
			/*prevent handling of other grids" events in the case of hierarchical grid */
			if (ui.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			/* M.H. 4 Mar 2014 Fix for bug #165697: Control automatically sorts the records while resizing the column using mouse */
			if (this.grid.element.data("igGridResizing")) {
				this._resizing = this.grid.element.data("igGridResizing");
			}
			if (!this._columnFixing && this.grid.element.data("igGridColumnFixing")) {
				this._columnFixing = this.grid.element.data("igGridColumnFixing");
			}
			/* M.H. 26 March 2012 Fix for bug #105568 */
			if (this.options.mode !== "single") {
				this._renderMultipleSortingDialog();
				/* this.openMultipleSortingDialog(); */
			}
		},
		/* M.H. 11 Mar 2014 Fix for bug #166528: Sorting and Filtering does not persist its state, when they are applied to unbound column */
		_getDataColumnSortingExpressions: function (se) {
			/* return only those sorting expressions that are for data bound columns(for which property unbound is not equal to TRUE) */
			if (!this.grid._hasUnboundColumns) {
				return se;
			}
			var grid = this.grid, newSE;
			newSE = $.grep(se, function (s) {
				var col = grid.columnByKey(s.fieldName);
				return !col || col.unbound !== true;
			});
			return newSE;
		},
		_saveSortingExpressions: function () {
			if (this.options.persist) {
				var se;
				/* M.H. 26 Feb 2014 Fix for bug #165287: Sorting arrow indicator appear after databinding when there are initial sorting settings and persist: true
				 we are using restoreSorting flag to not iterate through all colulmnsetting every time we save sorting expressions
				 only when
				 1. There aren't set sorting expressions
				 2. It is called for the first time we are sorting(after injectGrid is called)*/
				if (this._restoreSorting === undefined || this._restoreSorting) {
					/* we should delete current sort direction because we do not need to use it for persistence
					 in clearUI it is used and is called appliedSortStyles */
					$.each(this.options.columnSettings, function (i, setting) {
						/*jscs:disable*/
						delete setting.userSet_currentSortDirection;
						/*jscs:enable*/
					});
				}
				/* preseerve only data sorting expressions that are of data boundable columns(unbound <> TRUE) */
				se = this._getDataColumnSortingExpressions(this.grid.dataSource.settings.sorting.expressions);
				/* if (this.element.closest(".ui-iggrid-root").data("igGrid")) { */
				this.grid._savePersistenceData(se, "sorting", this.grid.element[ 0 ].id);
				/*}*/
				this._sortingExpressions = se;
				this._restoreSorting = false;
			}
		},
		_preserveSorting: function () {
			var se = this.grid._getPersistenceData("sorting", this.grid.element[ 0 ].id);
			if (se) {
				this._restoreSorting = true;
				if (se !== this._sortingExpressions) {
					this._sortingExpressions = se;
				}
				this.grid.dataSource.settings.sorting.expressions = se;
				this.grid.dataSource.settings.sorting.defaultFields = se;
			}
		},
		_applyActiveSortCellStyle: function (data, col) {
			/* apply active sort style(if sorting is applied) when scrolling grid AND rowVirtualization(columnVirtualization) is enabled
			 when loading new chunk active sort classes should be applied */

			/* M.K. Fix for bug 217283 : Sorting style isn't removed after changing datasource when virtualization = TRUE and persist = FALSE.
			check if the data source expressions have changed
			if there's no expression in the data source for the current column then we should not apply sort style for its cells */
			var expressionExists = false;
			$(this.grid.dataSource.settings.sorting.expressions).each(function () {
				if (this.fieldName === col) {
					expressionExists = true;
					return false;
				}
			});
			/* M.H. 21 August 2015 Fix for bug 205045: Sorted column’s background color style disappears when a vertical scrollbar is moved if virtualization is enabled. */
			if (this._activeSortedCol && expressionExists && col === this._activeSortedCol.key) {
				return this._activeSortedCol.asc ? this.css.ascendingColumn : this.css.descendingColumn;
			}
			return "";
		},
		_couldPreserveData: function () {
			/* M.H. 5 Mar 2014 Fix for bug #165360: Sorting persistance is not working for the child layouts of the grid
			 it is not working because in setupDataSource in grid framework it is reset dataSource but in hierarchical grid it is no reset grid sorting expressions for child layout datasources */
			return !this.options.persist ||
				this.grid.dataSource.settings.sorting.expressions !==
				this.grid._getPersistenceData("sorting", this.grid.element[ 0 ].id);
		},
		/* every grid feature widget should implement this */
		_injectGrid: function (gridInstance, isRebind) {
			var i, cs, col, restoreSortingExpr, topmostGrid;
			this.grid = gridInstance;
			/* M.H. 28 May 2014 Fix for bug #172200: Memory leak when using the features of the grid */
			this._detachEvents();
			if (this.options.type === null) {
				/* infer type */
				this.options.type = this.grid._inferOpType();
			}
			/* M.H. 21 Mar 2014 Fix for bug #167839: Remote filtering, sorting or group by are not persisted when applied to the child and root layout */
			if (this.options.persist && this.options.type === "remote") {
				topmostGrid = this.element.closest(".ui-iggrid-root").data("igGrid");
				if (topmostGrid && topmostGrid.element.attr("id") !== this.grid.element[ 0 ].id &&
					topmostGrid.options.initialDataBindDepth === -1) {
					this.options.persist = false;
				}
			}
			if (this.options.type) {
				this.grid.dataSource.settings.sorting.type = this.options.type;
			} else {
				this.grid.dataSource.settings.sorting.type = "remote";
			}
			/* A.T. 12 Feb 2011 - Fix for #66143 - igSorting caseSensitive option doesn't change case sensitivity */
			this.grid.dataSource.settings.sorting.caseSensitive = this.options.caseSensitive;
			this.grid.dataSource.settings.sorting.defaultFields =
				this.grid.dataSource.settings.sorting.expressions;
			this.grid.dataSource.settings.sorting.enabled = true;
			this.grid.dataSource.settings.sorting.sortUrlKey = this.options.sortUrlKey;
			this.grid.dataSource.settings.sorting.sortUrlAscValueKey = this.options.sortUrlKeyAscValue;
			this.grid.dataSource.settings.sorting.sortUrlDescValueKey = this.options.sortUrlKeyDescValue;
			if ($.type(this.options.customSortFunction) === "function") {
				this.grid.dataSource.settings.sorting.customFunc =
					this.options.customSortFunction;
			} else if (typeof this.options.customSortFunction === "string") {
				if (window[ this.options.customSortFunction ] &&
					typeof window[ this.options.customSortFunction ] === "function") {
					this.grid.dataSource.settings.sorting.customFunc = window[ this.options.customSortFunction ];
				}
			}
			this._headerCellRenderedHandler = $.proxy(this._headerCellRendered, this);
			this._columnsCollectionModifiedHandler = $.proxy(this._columnsCollectionModified, this);
			this._uiDirtyHandler = $.proxy(this._onUIDirty, this);
			this._headerRenderedHandler = $.proxy(this._headerRendered, this);
			this._columnsRearangedHandler = $.proxy(this._columnsRearranged, this);
			this.grid.element.bind("iggridheadercellrendered", this._headerCellRenderedHandler);
			this._virtualHorizontalScrollHandler = $.proxy(this._virtualHorizontalScroll, this);
			this.grid.element.bind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			/* register for uiDirty so that sorting states are cleared when dirty  event gets fired */
			this.grid.element.bind("iggriduidirty", this._uiDirtyHandler);
			this.grid.element.bind("iggridcolumnscollectionmodified",
				this._columnsCollectionModifiedHandler);
			this.grid.element.bind("iggridheaderrendered", this._headerRenderedHandler);
			this.grid.element.bind("iggrid_columnsmoved", this._columnsRearangedHandler);
			/*if (!isRebind) {
			 M.H. 11 Mar 2014 Fix for bug #166528: Sorting and Filtering does not persist its state, when they are applied to unbound column*/
			if (this.options.persist && this.grid._hasUnboundColumns) {
				cs = this.options.columnSettings;
				for (i = 0; i < cs.length; i++) {
					if (cs[ i ].columnKey && cs[ i ].currentSortDirection) {
						col = this.grid.columnByKey(cs[ i ].columnKey);
						if (col && col.unbound === true) {
							delete cs[ i ].currentSortDirection;
						}
					}
				}
			}
			this._initDefaultSettings();
			/*}
			 when we have multi sorting + groupBy AND groupby is defined before sorting in features definition then
			 sorting expressions is equal to dataSource sorting expressions and in ClearUI this.dataSource.sortingExpressions is cleared
			 M.H. 27 Feb 2014 Fix for bug #165268: The group does not retain after dataBind when GroupBy and multiple Sorting are enabled*/
			restoreSortingExpr = null;
			if (this.options.persist && this.grid.element.data("igGridGroupBy") &&
				this.options.mode !== "single") {
				if (this.grid.element.data("igGridGroupBy")._getSortingExpressions() ===
					this.grid.dataSource.settings.sorting.expressions) {
					restoreSortingExpr = this.grid.dataSource.settings.sorting.expressions.slice(0);
				}
			}
			this._clearUi();
			/* M.H. 27 Feb 2014 Fix for bug #165268: The group does not retain after dataBind when GroupBy and multiple Sorting are enabled*/
			if (restoreSortingExpr) {
				/* we need to reset dataSource sorting expressions and internal variable which holds this expression*/
				this.grid.dataSource.settings.sorting.expressions = restoreSortingExpr;
				this._saveSortingExpressions();
			}
			this._currentActiveHeader = null;
			this._currentHeader = null;
			this._curColKey = null;
			if (this.options.persist) {
				this._preserveSorting();
			}
			/* if row/column virtualization is enabled
			M.H. 8 Oct 2015 Fix for bug 206552: Collapsed cells don't get sorting style after sort and expand in igTreeGrid*/
			if (!isRebind && !this._cellStyleSubscriberAdded &&
				(this.grid.options.rowVirtualization || this.grid.options.virtualization)) {
				this._cellStyleSubscriberAdded = true;
				this.grid._cellStyleSubscribers.push($.proxy(this._applyActiveSortCellStyle, this));
			}
		}
	});
	$.extend($.ui.igGridSorting, { version: "16.1.20161.2145" });
}(jQuery));
/*jshint +W106 */
