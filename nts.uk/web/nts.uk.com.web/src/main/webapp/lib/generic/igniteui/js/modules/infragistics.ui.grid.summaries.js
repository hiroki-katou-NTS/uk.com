/*!@license
 * Infragistics.Web.ClientUI Grid Column Summaries 16.1.20161.2145
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
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {

	$.widget("ui.igGridSummaries", {
		/*
		igGridSummaries widget
		The widget is pluggable to the element where the grid is instantiated and the actual igGrid object doesn't know about it.
		The summaries widget just attaches its functionality to the grid.
		It supports summaries dropdown with default predefined summary functions like AVG, SUM, COUNT, etc and custom functions
		Predefined summary functions are shown according to develop options and type of the column.
		*/
		/* the instance of the grid to which this feature is going to attach its functionality */
		grid: null,
		css: {
			/*jscs:disable*/
			/* classes applied to dropdown */
			columnSummariesDropdown: "ui-iggrid-summaries-dialog",
			/* classes applied to DOM element in the summary cell which contains text for summaries result */
			footerRowTextContainer: "ui-iggrid-summaries-footer-text-container",
			/* classes applied to DOM element which contains text for icon in the summary cell */
			footerRowIconContainer: "ui-corner-all ui-iggrid-summaries-footer-icon-container",
			/* classes applied to the container of OK/Cancel button in dropdown */
			footerDropdownButtonsContainer: "ui-iggrid-summaries-footer-dialog-buttons-container",
			/* classes applied to footer button icon(which show/hide dropdown) */
			footerButtonIcon: "ui-icon ui-icon-calculator ui-iggrid-icon-summaries",
			/* classes applied to footer button icon when hovered (button show/hide dropdown) */
			footerButtonIconHover: "ui-iggrid-filterbuttonhover ui-state-hover",
			/* classes applied to footer button icon when selected(the dropdown is opened) */
			footerButtonIconActive: "ui-iggrid-filterbuttonactive ui-state-active",
			/* classes for icon to be showin in the feature chooser */
			featureChooserIconClass: "ui-icon ui-iggrid-icon-summaries",
			/* classes applied to container which holds all checkboxes in dropdown */
			dropDownListContainer: "ui-iggrid-summaries-dropdown-listcontainer ui-widget",
			/* classes applied to list item in the dropdown */
			dropDownListItem: "ui-iggrid-summaries-dialog-listitem",
			/* classes applied to the container div of header button(which shows/hides summaries) */
			headerButtonContainer: "ui-iggrid-summaries-headerbuttoncontainer ui-corner-all",
			/* classes applied to the header button(which shows/hides summaries) */
			headerButtonIcon: "ui-iggrid-summaries-header-icon ui-corner-all ui-icon ui-icon-calculator ui-icon-summaries",
			/* classes applied to the header button when hovered(which shows/hides summaries) */
			headerButtonIconHover: "ui-iggrid-summaries-header-icon-hover ui-state-hover",
			/* classes applied to footer row cell */
			footerRowCell: "ui-widget-footer ui-state-default",
			/* classes applied to footer row cell when it is hovered */
			footerRowCellHover: "ui-state-hover",
			/* classes applied to the checkbox container */
			checkbox: "ui-state-default ui-corner-all ui-igcheckbox-normal",
			/* classes defining the unchecked state of the checkbox */
			checkboxOff: "ui-icon ui-icon-check ui-igcheckbox-normal-off",
			/* classes defining the checked state of the checkbox */
			checkboxOn: "ui-icon ui-icon-check ui-igcheckbox-normal-on",
			/* class defining the hover state style of the checkbox */
			checkboxHovered: "ui-state-hover",
			/* classes applied to DOM element when showSummariesButton is false */
			footerRowIconContainerEmpty: "ui-iggrid-summaries-footer-icon-container-empty",
			/* classes applied to DOM element in the summary cell which contains text for summaries result when showSummariesButton is false */
			footerRowTextContainerEmpty: "ui-iggrid-summaries-footer-text-container-empty",
			/* class for IE7 */
			"baseClassIE7": "ui-ie7"
			/*jscs:enable*/
		},
		defaultDecimalDisplay: 2,
		/* A list of default summary methods
		   For now these are: Count, Min, Max, Sum, Avg and custom.
		   Each of them has:
				text - default text shown in dropdown near the checkbox
				type - unique string identifier for summary method name
				allowed - for which data type columns this summary method could be applied
				order - the order shown in dropdown/summary footer.
					*order of custom methods should be greater or equal than default custom summary method
				type="int" The number of digits after the decimal point.
		*/
		/* M.H. 13 Oct. 2011 Fix for bug 91008 */
		defaultSummaryMethods: [
			{
				"rowDisplayLabel": $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelCount,
				"type": "count", "allowed": "all", "active": true,
				"order": 0,
				decimalDisplay: this.defaultDecimalDisplay
			},
			{
				"rowDisplayLabel": $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelMin,
				"type": "min", "allowed": [ "number", "date", "numeric" ],
				"active": true,
				"order": 1,
				decimalDisplay: this.defaultDecimalDisplay
			},
			{
				"rowDisplayLabel": $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelMax,
				"type": "max",
				"allowed": [ "number", "date", "numeric" ],
				"active": true,
				"order": 2,
				decimalDisplay: this.defaultDecimalDisplay
			},
			{
				"rowDisplayLabel": $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelSum,
				"type": "sum",
				"allowed": [ "number", "numeric" ],
				"active": true,
				"order": 3,
				decimalDisplay: this.defaultDecimalDisplay
			},
			{
				"rowDisplayLabel": $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelAvg,
				"type": "avg",
				"allowed": [ "number", "numeric" ],
				"active": true,
				"order": 4,
				decimalDisplay: this.defaultDecimalDisplay
			},
			{
				"rowDisplayLabel": $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelCustom,
				"type": "custom",
				"allowed": "all",
				"active": true,
				"summaryCalculator": null,
				"order": 5,
				decimalDisplay: this.defaultDecimalDisplay
			}
		],
		renderInFeatureChooser: true,
		options: {
			/* type="remote|local" type of summaries calculating.
			remote type="string" when it is remote summaries calculations are made on the server
			local type="string"	When it is local calculations are made on the client
			*/
			type: null,
			/* type="string" Text of the button OK in the summaries dropdown */
			dialogButtonOKText: $.ig.GridSummaries.locale.dialogButtonOKText,
			/* type="string" Text of the button Cancel in the summaries dropdown */
			dialogButtonCancelText: $.ig.GridSummaries.locale.dialogButtonCancelText,
			/* type="okcancelbuttons|onselect" Specifies when calculations are made.
			onselect type="string" summaries are updated when checkbox is checked/unchecked
			okcancelbuttons type="string" summaries are updated only when OK button is clicked
			*/
			calculateRenderMode: "okcancelbuttons",//okcancelbuttons|onselect
			/* type="string" Get or set text that is shown in the feature chooser dropdown when summaries are hidden*/
			featureChooserText: $.ig.GridSummaries.locale.featureChooserText,
			/* type="string" Get or set text that is shown in the feauture chooser dropdown when summaries are shown*/
			featureChooserTextHide: $.ig.GridSummaries.locale.featureChooserTextHide,
			/* type="true|false|auto" Specifies how compact the summaries are rendered.
			When true indicates that the summaries may be rendered compactly, even mixing different summaries on the same line.
			False ensures that each summary type is occupying a separate line.
			Auto will use True if the maximum number of visible summaries is one or less and False otherwise.
			*/
			compactRenderingMode: true,
			/* M.H. 18 Oct. 2011 Fix for bug 92454 */
			/* type="number" The number of digits after the decimal point. If gridFormatter is True then this option is ignored. */
			defaultDecimalDisplay: 2,
			/* type="bool" Show header button icon in the header cell. On click - shows/hides summaries. If false then summary icon should not be shown in the feature chooser(if shown). */
			showSummariesButton: true,
			/* type="string" Result key by which we get data from the result returned by remote data source.*/
			summariesResponseKey: "summaries",
			/* type="string" Set key in GET Request for summaries - used only when type is remote */
			summaryExprUrlKey: "summaries",
			/* type="function" Function reference - it is called when data is retrieved from the data source */
			callee: null,
			/* type="number" Height of the dropdown in pixels */
			dropDownHeight: "",
			/* type="number" Width of the dropdown in pixels */
			dropDownWidth: "",
			/* type="bool" Show/hide footer button(on click show/hide dropdown) */
			showDropDownButton: true,
			/* type="priortofilteringandpaging|afterfilteringbeforepaging|afterfilteringandpaging" Determines when the summary values are calculated when type is local
			priortofilteringandpaging type="string" summaries are calculated prior to filtering and paging
			afterfilteringbeforepaging type="string" summaries are calculated after filtering and before paging
			afterfilteringandpaging type="string" summaries are calculated after filtering and paging
			*/
			summaryExecution: "afterfilteringbeforepaging",
			/* type="int" Dropdown animation duration */
			dropDownDialogAnimationDuration: 400,
			/* type="string" Empty text template to be shown for empty cells */
			emptyCellText: $.ig.GridSummaries.locale.emptyCellText,
			/* type="string" Tooltip text for header cell button */
			summariesHeaderButtonTooltip: $.ig.GridSummaries.locale.summariesHeaderButtonTooltip,
			/* type="string" Result template for summary result(shown in table cell)*/
			resultTemplate: "{0} = {1}",
			/* type="bool" if this property is enabled and in summaryOperand isGridFormatter  is undefined then use grid formatting for cells */
			isGridFormatter: true,
			/* type="string|object" a reference or name of a javascript function - renders summary cell in grid footer.(if not set it is used default render function) It takes 3 parameters - methodName(summary method), columnKey, data(summary data which should be rendered) */
			renderSummaryCellFunc: null,
			/* type="array" a list of column settings that specifies custom summaries options per column basis */
			columnSettings: [
				{
					/* type="bool" Enables disables summaries for the column */
					allowSummaries: true,
					/* type="string" Column key. This is a required property in every column setting if columnIndex is not set */
					columnKey: null,
					/* type="number" Column index. Can be used in place of column key. The preferred way of populating a column setting is to always use the column keys as identifiers */
					columnIndex: -1,
					/* type="array" Check defaultSummaryOperands */
					summaryOperands: [
						{
							/* type="string" Text of the summary method which is shown in summary cell */
							rowDisplayLabel: "",
							/* type="count|min|max|sum|avg|custom" Set type of summary operand
							count type="string" calculate count of result rows for the specified column
							min type="string" calculate min of result rows for the specified column
							max type="string" calculate max of result rows for the specified column
							sum type="string" calculate sum of result rows for the specified column
							avg type="string" calculate average of result rows for the specified column
							custom type="string" calculate custom function (specified by summaryCalculator property) of result rows for the specified column
							*/
							type: "custom",
							/* type="bool" If it is false the summary operand will be shown in dropdown but it will not be made calculation */
							active: true,
							/* type="string" Name of the custom summary function which should be executed when type is custom */
							summaryCalculator: null,
							/* type="number" Specifies the order of elements in dropdown. It is recommended to set order of custom operands and to be greater or equal to 5 */
							order: 5,
							/* type="number" Specifies the number of digits after the decimal point for result of the current summary operand. This property will be ignored when isGridFormatter is true */
							decimalDisplay: 2,
							/* type="bool" Specifies whether to be used grid formatter for result for the current summary operand. */
							isGridFormatter: undefined
						}
					]
				}
			],
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		events: {
			/* cancel="true" Event fired before drop down is opened for a specific column summary
			Return false in order to cancel opening the drop down.
			Function takes arguments evt and ui.
			Use ui.columnKey to get column key of the column where drop down is opening.
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			dropDownOpening: "dropDownOpening",
			/* Event fired after the summaries dropdown is opened for a specific column
			Function takes arguments evt and ui.
			Use ui.columnKey to get column key of the column where drop down is shown.
			Use ui.owner in order to access the igGridSummaries widget object.
			 */
			dropDownOpened: "dropDownOpened",
			/* cancel="true" Event fired before the dropdown for a summary column starts closing
			Return false in order to cancel closing the drop down.
			Function takes arguments evt and ui.
			Use ui.columnKey to get column key of the column where drop down is closing.
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			dropDownClosing: "dropDownClosing",
			/* Event fired after the dropdown for a summary column is closed
			Function takes arguments evt and ui.
			Use ui.columnKey to get column key of the column where drop down is closing.
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			dropDownClosed: "dropDownClosed",
			/* cancel="true" Event fired before summaries calculations are made
			Return false in order to cancel calculation of summaries.
			Function takes arguments evt and ui.
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			summariesCalculating: "summariesCalculating",
			/* Event fired after summaries calculation are completely made
			Function takes arguments evt and ui.
			Use ui.data to get data for calculated summaries
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			summariesCalculated: "summariesCalculated",
			/* Event fired when user selects/deselects summary method from checkbox
			Function takes arguments evt and ui.
			Use ui.columnKey to get column key of the column where it is changed selection of summary method
			Use ui.isSelected to get whether method is selected or not
			Use ui.methodName to get summary method name
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			summariesMethodSelectionChanged: "summariesMethodSelectionChanged",
			/* cancel="true" Event is fired bofore summary rows start toggling
			Return false in order to cancel showing/hiding of summaries.
			Function takes arguments evt and ui.
			Use ui.isToShow to get whether summaries are shown or not.
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			summariesToggling: "summariesToggling",
			/* Event is fired after summary rows are toggled
			Function takes arguments evt and ui.
			Use ui.isToShow to get whether summaries are shown or not.
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			summariesToggled: "summariesToggled",
			/* Event is fired when OK button is clicked in drop down
			Function takes arguments evt and ui.
			Use ui.columnKey to get column key for which OK button is clicked.
			Use ui.eventData to get data about which summary methods are(not) selected. Format of event data is {type: "min", active: false};
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			dropDownOKClicked: "dropDownOKClicked",
			/* Event is fired when Cancel button is clicked in drop down.
			Function takes arguments evt and ui.
			Use ui.columnKey to get column key for which Cancel button is clicked.
			Use ui.owner in order to access the igGridSummaries widget object.
			*/
			dropDownCancelClicked: "dropDownCancelClicked"
		},
		_createWidget: function () {
			/* !Strip dummy objects from options, because they are defined for documentation purposes only! */
			this.options.columnSettings = [  ];
			this._summariesRows = [  ];
			this._data = {}; // {columnKey: "", res: [ {type:"min", result: 2} ]}
			this._currentOpenedColumnKey = null;
			this._isRenderedSummariesRows = false;
			this._footerInitialized = false;
			this._currentRowShownIcons = null;
			/* array which is used when analyze custom summary methods of type "custom" - set them with type custom1, custom2, etc. */
			this._customInitMethods = [  ];
			/* feature chooser data */
			this._fcData = {};
			this._isInitFC = false;
			this._isSummariesRowsHidden = false;
			this._disallowedSummaries = [  ];
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		_create: function () {
			/* M.H. 29 Sept. 2011 Fix for bug #88718 - first it should be called parent method and then re-initialized options */
			var compactRenderingMode = this.options.compactRenderingMode;
			this._setRenderSummaryCellFunction(this.options.renderSummaryCellFunc);
			/* check whether compact rendering mode option is string */
			if (compactRenderingMode === "true") {
				compactRenderingMode = true;
			} else if (compactRenderingMode === "false") {
				compactRenderingMode = false;
			}
			this.options.compactRenderingMode = compactRenderingMode;
		},
		_setRenderSummaryCellFunction: function (func) {
			if (func && $.type(func) !== "function") {
				if (window[ func ] && typeof window[ func ] === "function") {
					func = window[ func ];
				}
			}
			if (func && $.type(func) === "function") {
				this._renderCellHandler = func;
			} else {
				this._renderCellHandler = $.proxy(this._renderCell, this);
			}
		},
		_initColumnSettings: function () {
			var settings = [  ], i, j, k, l,
				cs = $.extend(true, [  ], this.options.columnSettings),
				csetting,
				columnSettingsLength = cs.length,
				gridColumnsLength = (this.grid.options.columns) ? this.grid.options.columns.length : 0;

			this.newColumnSettings = [  ];
			this._disallowedSummaries = [  ];
			for (i = 0; i < gridColumnsLength; i++) {
				settings.push({
					"columnIndex": i,
					"columnKey": this.grid.options.columns[ i ].key,
					"dataType": this.grid.options.columns[ i ].dataType
				});
			}
			if (columnSettingsLength > 0) {
				/* First it should be initialized _customInitMethods array with those custom methods which are of type "customN" where N is digit - 1,2,3, ... */
				/* in this way we know all custom types and we will be able to enumerate correctly summaries with type "custom" and to set their correct type if exists */
				/* M.H. 17 Oct. 2011 Fix for bug 89444 */
				for (i = 0; i < columnSettingsLength; i++) {
					csetting = cs[ i ];
					if (csetting.summaryOperands === null || csetting.summaryOperands === undefined) {
						continue;
					}
					if ((csetting.columnKey !== null && csetting.columnKey !== undefined) ||
							(csetting.columnIndex !== null && csetting.columnIndex !== undefined)) {
						for (j = 0; j < gridColumnsLength; j++) {
							for (l = 0; l < csetting.summaryOperands.length; l++) {
								/* M.H. 18 Oct. 2011 Fix for bug 92460 */
								/* M.H. 12 Oct 2012 Fix for bug #124080 */
								if (csetting.summaryOperands[ l ].active === null ||
									csetting.summaryOperands[ l ].active === undefined) {
									csetting.summaryOperands[ l ].active = true;
								}
								if (csetting.summaryOperands[ l ].type.startsWith("custom") === true) {
									if (csetting.summaryOperands[ l ].type !== "custom") {
										for (k = 0; k < this._customInitMethods.length; k++) {
											if (csetting.summaryOperands[ l ].summaryCalculator ===
												this._customInitMethods[ k ].summaryCalculator) {
												break;
											}
										}
										/* add new custom type */
										if (this._customInitMethods.length === k) {
											this._customInitMethods.push({
												summaryCalculator: csetting.summaryOperands[ l ].summaryCalculator,
												type: csetting.summaryOperands[ l ].type,
												index: csetting.summaryOperands[ l ].type.replace("custom", "")
											});
										}
									}
								}
							}
						}
					}
				}
				/* check columnSettings if key/index is defined */
				for (i = 0; i < columnSettingsLength; i++) {
					csetting = cs[ i ];
					if ((csetting.columnKey !== null && csetting.columnKey !== undefined) ||
							(csetting.columnIndex !== null && csetting.columnIndex !== undefined)) {
						for (j = 0; j < gridColumnsLength; j++) {
							if (
								(settings[ j ].columnIndex === csetting.columnIndex &&
									csetting.columnIndex !== undefined && csetting.columnIndex !== null) ||
									(settings[ j ].columnKey === csetting.columnKey &&
										csetting.columnKey !== undefined && csetting.columnKey !== null)
							) {
								csetting.dataType = settings[ j ].dataType;
								csetting.columnIndex = settings[ j ].columnIndex;
								csetting.columnKey = settings[ j ].columnKey;
								csetting.summaryOperands = this._initAllowedSummaryMethods(csetting);

								if (csetting.allowSummaries !== false) {
									this.newColumnSettings.push(csetting);
								} else {
									this._disallowedSummaries[ settings[ j ].columnKey ] = true;
								}
								break;
							}
						}
					}
				}
				for (i = 0; i < gridColumnsLength; i++) {
					for (j = 0; j < this.newColumnSettings.length; j++) {
						if (this.newColumnSettings[ j ].columnKey === settings[ i ].columnKey) {
							break;
						}
					}
					if (j === this.newColumnSettings.length &&
						this._disallowedSummaries[ settings[ i ].columnKey ] !== true) {
						this.newColumnSettings.push({
							dataType: settings[ i ].dataType,
							columnIndex: i,
							columnKey: settings[ i ].columnKey,
							summaryOperands: this._initAllowedSummaryMethods(settings[ i ], false)
						});
					}
				}
			} else {
				for (i = 0; i < gridColumnsLength; i++) {
					this.newColumnSettings.push({
						dataType: settings[ i ].dataType,
						columnIndex: i,
						columnKey: settings[ i ].columnKey,
						summaryOperands: this._initAllowedSummaryMethods(settings[ i ])
					});
				}
			}
			this.columnSettings = this.newColumnSettings;
		},
		_initAllowedSummaryMethods: function (column, isNotActive) {
			var i, j, k, z, c,
				lastCustomSummaryOperandIndex,
				defaultSummaryMethodsLength = this.defaultSummaryMethods.length,
				columnDataType,
				methods = [  ],
				allowedMethods = [  ],
				methodName,
				methodsLength,
				isActive,
				defaultMethodName;

			if (column === null || column === undefined) {
				return;
			}
			columnDataType = column.dataType;
			if (columnDataType === "object") {
				columnDataType = this.grid.dataSource._getFieldTypeFromSchema(column.columnKey);
			}
			if (column !== null && column !== undefined && column.summaryOperands !== undefined &&
				column.summaryOperands !== null) {
				for (i = 0; i < column.summaryOperands.length; i++) {
					if (column.summaryOperands[ i ].type === undefined ||
						column.summaryOperands[ i ].type === null) {
						return;
					}
					methodName = column.summaryOperands[ i ].type.toLowerCase();
					if (this._isAllowedMethodName(methodName) === true) {
						if (isNotActive === true) {
							column.summaryOperands[ i ].active = false;
						}
						/* M.H. 17 Oct. 2011 Fix for bug 89444 */
						/* when type is "custom" we should check all summary operands which are of type "custom1", "custom2", "custom3", etc. */
						/* it is checked by summaryCalculator method. If it does not exist set new value which will be the biggest N from "customN" plus 1 to "custom(N+1)"  */
						/* if till now it is defined custom1, custom2 then the new custom type will be custom3 */
						/* NOTE: we have preliminarily in array _customInitMethods all summary methods that are of type custom1, custom2, etc. from columnSettings */
						/* here we check only those which are of type "custom" */
						if (column.summaryOperands[ i ].type === "custom") {
							if (column.summaryOperands[ i ].summaryCalculator === null ||
									column.summaryOperands[ i ].summaryCalculator === undefined) {
								continue;
							}
							lastCustomSummaryOperandIndex = 1;
							for (k = 0; k < this._customInitMethods.length; k++) {
								if (column.summaryOperands[ i ].summaryCalculator ===
									this._customInitMethods[ k ].summaryCalculator) {
									column.summaryOperands[ i ].type = this._customInitMethods[ k ].type;
									break;
								}
								if (this._customInitMethods[ k ].index > lastCustomSummaryOperandIndex) {
									lastCustomSummaryOperandIndex = this._customInitMethods[ k ].index;
								}
							}
							/* add new custom type */
							if (this._customInitMethods.length === k) {
								lastCustomSummaryOperandIndex++;
								column.summaryOperands[ i ].type = "custom" + lastCustomSummaryOperandIndex;
								this._customInitMethods.push({
									summaryCalculator: column.summaryOperands[ i ].summaryCalculator,
									type: "custom" + lastCustomSummaryOperandIndex,
									index: lastCustomSummaryOperandIndex
								});
							}
						}
						methods.push(column.summaryOperands[ i ]);
					}
				}
			} else {
				for (i = 0; i < defaultSummaryMethodsLength; i++) {
					c = this.defaultSummaryMethods[ i ];
					/* type should be custom1, custom2, etc.  */
					/* if it is ONLY "custom" then we should check each custom type and if they are equal by summaryOperand to make them equal as types */
					if (c.type.startsWith("custom")) {
						continue;
					}
					isActive = c.active;
					if (isNotActive === true) {
						isActive = false;
					}
					methods.push(
						{
							"rowDisplayLabel": c.rowDisplayLabel,
							"type": c.type,
							"allowed": c.allowed,
							"active": isActive,
							"order": c.order,
							"decimalDisplay": c.decimalDisplay,
							"isGridFormatter": c.isGridFormatter
						}
					);
				}
			}
			methodsLength = methods.length;
			/* check which methods are appropriate for the column data type */
			for (i = 0; i < methodsLength; i++) {
				for (j = 0; j < defaultSummaryMethodsLength; j++) {
					defaultMethodName = this.defaultSummaryMethods[ j ].type.toUpperCase();
					if (defaultMethodName === methods[ i ].type.toUpperCase() ||
							methods[ i ].type.startsWith("custom")) {
						/* check order - if it is lower than custom method */
						/* check for NOT custom methods order - it could be only those from default */
						if (methods[ i ].type.toLowerCase().startsWith("custom")) {
							if (methods[ i ].order < this.defaultSummaryMethods[ j ].order) {
								methods[ i ].order = this.defaultSummaryMethods[ j ].order;
							}
						} else if (methods[ i ].order !== this.defaultSummaryMethods[ j ].order) {
							methods[ i ].order = this.defaultSummaryMethods[ j ].order;
						}
						/* M.H. 19 Oct 2012 Fix for bug #124080 */
						if (methods[ i ].rowDisplayLabel === null || methods[ i ].rowDisplayLabel === undefined) {
							methods[ i ].rowDisplayLabel = this.defaultSummaryMethods[ j ].rowDisplayLabel;
						}
						if (this.defaultSummaryMethods[ j ].allowed === "all") {
							allowedMethods.push(methods[ i ]);
						} else {
							for (z = 0; z < this.defaultSummaryMethods[ j ].allowed.length; z++) {
								if (this.defaultSummaryMethods[ j ].allowed[ z ] === columnDataType) {
									allowedMethods.push(methods[ i ]);
									break;
								}
							}
						}
						break;
					}
				}
			}
			return allowedMethods;
		},
		_setOption: function (key, value) {
			/* handle new settings and update options hash */
			var i, o = this.options,
				columns, columnsLength,
				cs, csLength,
				$colCell,
				columnKey,
				gridId = this.grid.element[ 0 ].id;

			switch (key) {
			case "dialogButtonOKText":
				if (o.dialogButtonOKText !== value) {
					columns = this.grid.options.columns;
					columnsLength = columns.length;
					for (i = 0; i < columnsLength; i++) {
						this.grid._rootContainer()
							.find("#" + this._id("summaries", "dialog_button_ok", columns[ i ].key))
							.igButton("option", "labelText", value);
					}
				}
				break;
			case "dialogButtonCancelText":
				if (o.dialogButtonCancelText !== value) {
					columns = this.grid.options.columns;
					columnsLength = columns.length;
					for (i = 0; i < columnsLength; i++) {
						this.grid._rootContainer()
							.find("#" + this._id("summaries", "dialog_button_cancel", columns[ i ].key))
							.igButton("option", "labelText", value);
					}
				}
				break;
			case "calculateRenderMode":
				if (o.calculateRenderMode !== value) {
					o.calculateRenderMode = value;
					columns = this.grid.options.columns;
					columnsLength = columns.length;
					for (i = 0; i < columnsLength; i++) {
						this._renderDropDown(columns[ i ].key);
					}
				}
				break;
			case "compactRenderingMode":
				if (o.compactRenderingMode !== value) {
					o.compactRenderingMode = value;

					this._clearAllRows();
					this.calculateSummaries();
				}
				break;
			case "showSummariesButton":
				if (o.showSummariesButton !== value) {
					o.showSummariesButton = value;
					cs = this.columnSettings;
					csLength = cs.length;

					for (i = 0; i < csLength; i++) {
						columnKey = cs[ i ].columnKey;

						if (value === true) {
							$colCell = this.grid._rootContainer().find("#" + gridId + "_" + columnKey);
							if (this._fcData !== null && this._fcData !== undefined &&
									$colCell.length > 0 && this._fcData[ columnKey ] !== true) {
								this._renderHeaderCellButton($colCell, columnKey);
							}
						} else {
							this.grid._rootContainer()
								.find("#" + this._id("summaries", "header_column_icon", columnKey))
								.remove();
						}
					}
				}
				break;
			case "disabled":
				if (o.disabled !== value) {
					o.disabled = value;
					if (value === true) {
						this._clearAllRows();
					} else {
						this.calculateSummaries();
					}
				}
				break;
			case "type":
				if (o.type !== value) {
					o.type = value;
					this._setDataSourceColumnSettingsOptions();
				}
				break;
			case "summaryExprUrlKey":
				if (o.summaryExprUrlKey !== value) {
					o.summaryExprUrlKey = value;
					this._setDataSourceColumnSettingsOptions();
				}
				break;
			case "dropDownHeight":
				if (o.dropDownHeight !== value) {
					o.dropDownHeight = value;
					columns = this.grid.options.columns;
					columnsLength = columns.length;
					for (i = 0; i < columnsLength; i++) {
						this.grid._rootContainer()
							.find("#" + this._id("summaries", "methods_dialog", columns[ i ].key))
							.css("height", value);
					}
				}
				break;
			case "dropDownWidth":
				if (o.dropDownWidth !== value) {
					o.dropDownWidth = value;
					columns = this.grid.options.columns;
					columnsLength = columns.length;
					for (i = 0; i < columnsLength; i++) {
						this.grid._rootContainer()
							.find("#" + this._id("summaries", "methods_dialog", columns[ i ].key))
							.css("width", value);
					}
				}
				break;
			case "showDropDownButton":
				if (o.showDropDownButton !== value) {
					o.showDropDownButton = value;
					if (value === true) {
						this._renderFooterIcons();
					} else {
						this._clearFooterIcons();
					}
				}
				break;
			case "emptyCellText":
				if (o.emptyCellText !== value) {
					o.emptyCellText = value;
					this._clearAllRows();
					this.calculateSummaries();
				}
				break;
			case "resultTemplate":
				if (o.resultTemplate !== value) {
					o.resultTemplate = value;
					this._clearAllRows();
					this.calculateSummaries();
				}
				break;
			case "isGridFormatter":
				if (o.isGridFormatter !== value) {
					o.isGridFormatter = value;
					this._clearAllRows();
					this.calculateSummaries();
				}
				break;
			case "defaultDecimalDisplay":
				if (o.defaultDecimalDisplay !== value) {
					o.defaultDecimalDisplay = value;
					this._clearAllRows();
					this.calculateSummaries();
				}
				break;
			case "columnSettings":
				if (o.columnSettings !== value) {
					o.columnSettings = value;
					/* M.H. 3 Nov. 2011 Fix for bug 95290 */
					this.columnSettings = [  ];
					this.newColumnSettings = [  ];
					/* data should be reinitialized */
					this._data = {};

					this._clearAllRows();
					this._initColumnSettings();

					columns = this.grid.options.columns;
					columnsLength = columns.length;
					this.grid.dataSource.dataBind();
					for (i = 0; i < columnsLength; i++) {
						this._renderDropDown(columns[ i ].key);
					}
				}
				break;
				case "renderSummaryCellFunc":
					if (o.renderSummaryCellFunc !== value) {
						this._setRenderSummaryCellFunction(value);
					}
					break;
			}
			$.Widget.prototype._setOption.apply(this, arguments);
		},
		/* M.H. 29 Oct 2012 Fix for bug #120642 */
		_removeHeaderColumnIcons: function () {
			var i, cs = this.columnSettings, csLength = cs.length;

			for (i = 0; i < csLength; i++) {
				this.grid._rootContainer()
					.find("#" + this._id("summaries", "header_column_icon", cs[ i ].columnKey))
					.remove();
			}
		},
		destroy: function () {
			var i, columns, columnsLength, fc;
			/*destroys the igGridSummaries feature by removing all elements in the footer area, unbinding events*/
			/* M.H. 26 Jan 2012 - fix for bug #91738 */
			this._detachEvents();

			this._clearAllRows();
			this.newColumnSettings = null;
			this.options.columnSettings = null;
			this.options = null;
			this._summariesRows.length = 0;
			this._summariesRows = null;
			if (this._activeDialog) {
				this._activeDialog.remove();
				this._activeDialog = null;
			}
			this._data = null;
			this._currentOpenedColumnKey = null;
			this._isRenderedSummariesRows = null;
			this._footerInitialized = null;
			this._currentRowShownIcons = null;
			this._customInitMethods = null;
			this._fcData = null;
			this._isInitFC = null;
			this._isSummariesRowsHidden = null;
			this._disallowedSummaries = null;
			this._activeDialog = null;
			this.defaultSummaryMethods = null;
			/* remove summaries dialogs */
			/* M.H. 3 Oct 2013 Fix for bug #153955: [ igGrid Summaries ]Destroying Grid Summaries does not remove dropdown elements from DOM */
			columns = this.grid.options.columns;
			columnsLength = columns.length;
			for (i = 0; i < columnsLength; i++) {
				this.grid._rootContainer()
					.find("#" + this._id("summaries", "methods_dialog", columns[ i ].key))
					.remove();
			}
			fc = this.grid.element.data("igGridFeatureChooser");
			if (fc && this.renderInFeatureChooser) {
				fc._removeFeature("Summaries");
			}
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		_detachEvents: function () {
			/* M.H. 28 May 2014 Fix for bug #172200: Memory leak when using the features of the grid */
			this.grid.footersTable().find("tr:first td").unbind(".summaries");
			/* M.H. 26 Jan 2012 - fix for bug #91738 */
			if (this._headerRenderedHandler) {
				this.grid.element.unbind("iggridheaderrendered", this._headerRenderedHandler);
			}
			if (this._footerRenderedHandler) {
				this.grid.element.unbind("iggridfooterrendered", this._footerRenderedHandler);
			}
			if (this._headerCellRenderedHandler) {
				this.grid.element.unbind("iggridheadercellrendered", this._headerCellRenderedHandler);
			}
			if (this._headerFooterInitHandler) {
				this.grid.element.unbind("iggridheaderextracellsmodified", this._headerFooterInitHandler);
			}
			if (this._columnsCollectionModifiedHandler) {
				this.grid.element.unbind("iggridcolumnscollectionmodified",
					this._columnsCollectionModifiedHandler);
			}
			if (this._virtualHorizontalScrollHandler) {
				this.grid.element.unbind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			}
		},
		_headerCellRendered: function (event, ui) {
			/* check for which cells should be rendered in feature chooser
			*/
			/*prevent handling of other grids' events in the case of hierarchical grid */
			if (ui.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}

			if (this._isInitFC !== true) {
				this._initFC();
			}
		},
		_initFC: function () {
			 /* instantiate feature chooser */
			/* check whether feature chooser is defined */
			var i, fc,
				o = this.options,
				cs = this.columnSettings,
				csLength = cs.length,
				columnKey;
			this._isInitFC = true;
			/* instantiate igGridFeatureChooser = if it is defined */
			fc = this.grid.element.data("igGridFeatureChooser");
			if (fc !== null &&
				fc !== undefined &&
				this.renderInFeatureChooser &&
				o.showSummariesButton) {
				for (i = 0; i < csLength; i++) {
					if (cs[ i ].allowSummaries === false) {
						continue;
					}
					columnKey = cs[ i ].columnKey;
					if (fc._shouldRenderInFeatureChooser(columnKey) === true) {
						fc._renderInFeatureChooser(columnKey,
							{
								name: "Summaries",
								text: o.featureChooserText,
								textHide: o.featureChooserTextHide,
								iconClass: this.css.featureChooserIconClass,
								iconClassOff: this.css.featureChooserIconClass,
								/*secondaryIconClass: this.css.featureChooserIconClass, */
								isSelected: true,
								method: $.proxy(this._togglefromfc, this),
								updateOnClickAll: true,
								order: 1, // order in group
								groupName: "toggle",
								groupOrder: 1,
								type: "toggle",
								state: "hide"
							}
							);
						/*fc._setSelectedState("Summaries", columnKey, false, false); */
						this._fcData[ columnKey ] = true;
					}
				}
			}
		},
		_footerRendered: function (sender, args) {
			/*prevent handling of other grids" events in the case of hierarchical grid */
			if (args.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			this._initColumnSettings();
			var i,
				cs = this.columnSettings,
				csLength = cs.length,
				columnKey;
			for (i = 0; i < csLength; i++) {

				if (cs[ i ].allowSummaries === false) {
					continue;
				}
				columnKey = cs[ i ].columnKey;
				/* M.H. 27 Sep 2011 - fix for bug #84759 */
				/* initializing of summary dialogs should be in header rendered NOT in headercellrendered */
				/* because when showHeaders is false then headercellrendered is not called */
				this._renderDropDown(columnKey);
			}
		},
		_headerRendered: function (sender, args) {
			/*prevent handling of other grids" events in the case of hierarchical grid */
			if (args.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			var i,
				cs = this.columnSettings,
				csLength = cs.length,
				$colCell,
				columnKey,
				gridId = this.grid.element[ 0 ].id;

			for (i = 0; i < csLength; i++) {
				if (cs[ i ].allowSummaries === false) {
					continue;
				}
				columnKey = cs[ i ].columnKey;
				/* M.H. 27 Sep 2011 - fix for bug #84759 */
				/* initializing of summary dialogs should be in header rendered NOT in headercellrendered */
				/* because when showHeaders is false then headercellrendered is not called */
				/*$colCell = $("#" + gridId + "_" + columnKey); */
				$colCell = this.grid.headersTable().find("#" + gridId + "_" + columnKey);
				if ($colCell.length > 0 && this._fcData[ columnKey ] !== true &&
					this.options.showSummariesButton !== false) {
					this._renderHeaderCellButton($colCell, columnKey);
				}
			}
		},
		_columnMap: function () {
			var o = this.options,
				cs = this.newColumnSettings,
				i,
				csLength = cs.length,
				elem,
				result = [  ];

			if (o.showSummariesButton === false) {
				return false;
			}

			for (i = 0; i < csLength; i++) {
				elem = { columnKey: cs[ i ].columnKey, enabled: true };
				if (cs[ i ].allowSummaries === false) {
					elem.enabled = false;
				}
				result.push(elem);
			}
			return result;
		},
		_dataRendered: function () {
			var grid = this.grid;
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			if (this.options.disabled !== true && this._isSummariesRowsHidden === false) {
				this.calculateSummaries();
				/* M.H. 11 Mar 2016 Fix for bug 215731: Cannot scroll to the last row when fixed virtualization and Summaries are enabled */
				if (!grid._initialized &&
					this._isVirtualization() &&
					grid.options.virtualizationMode === "fixed") {
					grid._resetVirtualDom();
					grid._renderVirtualRecordsFixed();
				}
			}
		},
		_updatingApplied: function () {
			/* re-calculate summaries when updating is applied - like row/cell updated, row deleted/ row added */
			/* M.H. 15 Mar 2013 Fix for bug #136429: Updating Values does not fire updating of summary */
			/* M.H. 21 Jun 2013 Fix for bug #144973: Summaries are shown after updating when autoCommit=true */
			if (this.grid.options.autoCommit && this._isRenderedSummariesRows) {
				this.calculateSummaries();
			}
		},
		_rowDeleted: function () {
			/* internal callback */
			this._updatingApplied();
		},
		_rowAdded: function () {
			this._updatingApplied();
		},
		_internalRowUpdated: function () {
			this._updatingApplied();
		},
		_internalCellUpdated: function () {
			this._updatingApplied();
		},
		isSummariesRowsHidden: function () {
			/* Returns whether summaries rows are hidden */
			return this._isSummariesRowsHidden;
		},
		calculateSummaries: function () {
			/* Calculate summaries */
			var h = this.grid.footersTable().outerHeight();
			this._trigger(this.events.rowsInitialize, null, {});
			this._calculateInternalSummaries();
			if (this.options.showDropDownButton === true) {
				this._renderFooterIcons();
			}
			this._adjustHeights(h);
		},
		_adjustHeights: function (h) {
			var grid = this.grid, gridHeight = grid.options.height;
			if (gridHeight === null || gridHeight === undefined) {
				return;
			}
			grid._fireInternalEvent("_summariesHeightsAdjusting");
			if (grid.hasFixedColumns()) {
				this._syncFixedUnfixedRowHeights();
			}
			if (!h || h !== this.grid.footersTable().outerHeight()) {
				grid._initializeHeights();
				if (this._isVirtualization()) {
					var svst = this.grid._persistVirtualScrollTop;
					this.grid._persistVirtualScrollTop = true;
					this.grid._saveFirstVisibleTRIndex();
					/* we should reRender virtual records */
					grid._resetVirtualDom();
					grid._renderVirtualRecords();
					this.grid._persistVirtualScrollTop = svst;
				}
			}
			if (grid.hasVerticalScrollbar() !== grid._hasVerticalScrollbar) {
				grid._adjustLastColumnWidth(false);
			}
		},
		_removeFooterRows: function () {
			/* remove all footer rows which are in _summariesRow array and clears the array */
			var i,
				sr = this._summariesRows,
				hasFixedColumns = this.grid.hasFixedColumns(),
				srLength = sr.length;
			for (i = 0; i < srLength; i++) {
				this.grid._rootContainer()
					.find("#" + this._id("summaries", "footer_row", sr[ i ].type)).remove();
				if (hasFixedColumns) {
					this.grid._rootContainer()
						.find("#" + this._id("summaries", "footer_row", sr[ i ].type, "fixed"))
						.remove();
				}
			}
			this._isRenderedSummariesRows = false;
			this._summariesRows = [  ];
		},
		_calculateSelectedSummaries: function (columnKey) {
			/* analyze whether checkboxes are selected/unselected for the column(with the specified columnKey) and calculates(if there is such need) summaries and/or refresh summaries(if there is such need) */
			/* if checkboxes are only unchecked for the specified column - do not recalculate summaries - just refreshes UI */
			var i, k, methods,
				hasFixedColumns = this.grid.hasFixedColumns(),
				res = [  ],
				csLength,
				$checkbox,
				methodsLength,
				summariesRowsLength,
				isToCalculate = false,
				data, newMax, max,
				cs = null,
				isChecked,
				j,
				compactRenderingMode = this.options.compactRenderingMode,
				selectedColumnSettingsIndex,
				columnSettingsLength = this.columnSettings.length,
				isToRerender = false,
				isToRemoveRow = false,
				methodNameToRemove,
				unactiveMethods = [  ],
				unactiveMethodsLength = 0,
				activeMethods = [  ],
				isToRemoveFirstRow = false,
				elemDataCalculating,
				rowToRemoveMethodName = "";
			if (columnKey === undefined || columnKey === null) {
				return;
			}
			for (j = 0; j < columnSettingsLength; j++) {
				/* M.H. 12 Oct. 2011 Fix for bug #91580 */
				if (this.columnSettings[ j ].columnKey === columnKey && this._isVisibleColumn(columnKey)) {
					cs = this.columnSettings[ j ];
					selectedColumnSettingsIndex = j;
					break;
				}
			}
			if (cs === null || cs === undefined) {
				return;
			}
			methods = cs.summaryOperands;
			methodsLength = methods.length;
			/* check each of the rendered methods - if there are changes - re-render */
			/* analyze each of the checkboxes whether are only unchecked or it should be made summaries calculation */
			/* if there is no changes in checkboxes then isToRerender is FALSE, otherwise it is TRUE and should be refreshed summaries */
			/* if it should be made summaries calculation then isToCalculate is set to TRUE */
			/* save the state of active and not active methods in 2 arrays */
			for (i = 0; i < methodsLength; i++) {
				$checkbox = this.grid._rootContainer().find("#" + this._id("summaries_footer_checkbox",
					methods[ i ].type.toLowerCase(), columnKey));
				isChecked = ($checkbox.attr("data-chk").toLowerCase() === "on");
/*				if (isChecked === true) { */
/*					countCheckedSettings++; */
/*				} */
				if (isChecked !== methods[ i ].active) {
					isToRerender = true;
					/* if there are options that are checked */
					this.newColumnSettings[ selectedColumnSettingsIndex ].summaryOperands[ i ].active = isChecked;
					if (isChecked === true) {
						isToCalculate = true;
						activeMethods.push(
								this.newColumnSettings[ selectedColumnSettingsIndex ].summaryOperands[ i ]
							);
					} else {
						unactiveMethods.push(methods[ i ]);
					}
				}
			}
			this.columnSettings[ selectedColumnSettingsIndex ].summaryOperands =
				this.newColumnSettings[ selectedColumnSettingsIndex ].summaryOperands;
			this.isToCalculateInRemoteMode = false;
			/* M.H. 17 April 2012 Fix for bug #109058 */
			if (this.options.compactRenderingMode === "auto") {
				if (this._summariesRows.length === 1 &&
						this._analyzeAutoCompactRenderingMode() === false) {
					/* when compact rendering mode is AUTO and count of summary rows is 1 but should be shown more than 1 row */
					this.grid._rootContainer()
						.find("#" + this._summariesRows[ 0 ].id)
						.remove();
					if (hasFixedColumns) {
						this.grid._rootContainer()
							.find("#" + this._summariesRows[ 0 ].id + "_fixed")
							.remove();
					}
					this._summariesRows = [  ];
					this.calculateSummaries();
					return;
				}
				if (this._summariesRows.length > 1 &&
						this._analyzeAutoCompactRenderingMode() === true) {
					/* when compact rendering mode is AUTO and count of summary rows is more than 1 but should be shown ONLY 1 row */
					for (i = 0; i < this._summariesRows.length; i++) {
						this.grid._rootContainer()
							.find("#" + this._summariesRows[ i ].id)
							.remove();
						if (hasFixedColumns) {
							this.grid._rootContainer()
								.find("#" + this._summariesRows[ i ].id + "_fixed")
								.remove();
						}
					}
					this._summariesRows = [  ];
					this.calculateSummaries();
					return;
				}
			}
			if (isToCalculate === true) {
				/* if some new methods are added then recalculate */
				/* remove Footer Rows */
				if ((compactRenderingMode === false ||
						(compactRenderingMode === "auto" && this._analyzeAutoCompactRenderingMode() === false)) &&
							this._summariesRows.length === 1) {
					/* check whether it should be removed first row - it should be removed if no info is selected */
					rowToRemoveMethodName = this._summariesRows[ 0 ].type.toLowerCase();
					isToRemoveFirstRow = true;
					for (j = 0; j < this.columnSettings.length; j++) {
						/* M.H. 12 Oct. 2011 Fix for bug #91580 */
						if (!this._isVisibleColumn(this.columnSettings[ j ].columnKey)) {
							continue;
						}
						for (k = 0; k < this.columnSettings[ j ].summaryOperands.length; k++) {
							if (this.columnSettings[ j ].summaryOperands[ k ].type.toLowerCase() ===
								rowToRemoveMethodName && this.columnSettings[ j ].summaryOperands[ k ].active !== false) {
								isToRemoveFirstRow = false;
								break;
							}
						}
						if (isToRemoveFirstRow === false) {
							break;
						}
					}
				}
				/* if type is remote - rebind - otherwise - recalculate */
				if (this.options.type === "remote") {
					if (this.options.calculateRenderMode === "onselect") {
						this.isToCalculateInRemoteMode = true;
						elemDataCalculating = {
							type: activeMethods[ 0 ].type,
							result: "calculating",
							rowDisplayLabel: activeMethods[ 0 ].rowDisplayLabel,
							order: activeMethods[ 0 ].order,
							decimalDisplay: activeMethods[ 0 ].decimalDisplay,
							/* M.H. 27 Sept. 2011 Fix for bug #88109 - add dataType */
							dataType: this.newColumnSettings[ selectedColumnSettingsIndex ].dataType
						};

						if (this._data[ columnKey ] === null || this._data[ columnKey ] === undefined) {
							this._data[ columnKey ] = [  ];
						}
						this._data[ columnKey ].push(elemDataCalculating);

						this._renderFooterColumnInfo(columnKey,
										this._data[ columnKey ]
							);
					} else {
						this._setDataSourceColumnSettingsOptions();
						this.grid.dataSource.dataBind();
					}
				} else {
					/*
					if (this.options.calculateDataView === true) {
						// get summaries only for current DataView - e.g. if paging is enabled only for current page
						data = ds.dataView();
					} else if (ds._filteredData !== undefined && ds._filteredData !== null) {
						data = ds._filteredData;
					} else {
						data = ds.data();
					}*/
					data = this._dataSummaries();
					/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
					if (this._trigger(this.events.summariesCalculating, null, { owner: this }) !== false) {
						res = this.calculateSummaryColumn(
							columnKey,
							activeMethods,
							data,
							this.newColumnSettings[ selectedColumnSettingsIndex ].dataType
							);
						if (compactRenderingMode === false ||
								(compactRenderingMode === "auto" && this._analyzeAutoCompactRenderingMode() === false)) {
							this._renderFooterColumnInfo(columnKey, res);
						}
						/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
						this._trigger(this.events.summariesCalculated, null, { data: data, owner: this });
					}
				}
			}
			unactiveMethodsLength = unactiveMethods.length;
			if (compactRenderingMode === true ||
					(compactRenderingMode === "auto" && this._analyzeAutoCompactRenderingMode() === true)) {
				cs = this.newColumnSettings;
				csLength = cs.length;
				max = 0;
				newMax = 0;
				if (compactRenderingMode === "auto") {
					unactiveMethods = [  ];
					for (i = 0; i < methodsLength; i++) {
						$checkbox = this.grid._rootContainer().find("#" +
							this._id("summaries_footer_checkbox", methods[ i ].type.toLowerCase(), columnKey));
						isChecked = ($checkbox.attr("data-chk").toLowerCase() === "on");
						if (isChecked === false) {
							unactiveMethods.push(methods[ i ]);
						}
					}
					unactiveMethodsLength = unactiveMethods.length;
				}
				for (i = 0; i < unactiveMethodsLength; i++) {
					this._removeDataResult(columnKey, unactiveMethods[ i ].type.toLowerCase());
				}
				if (unactiveMethodsLength > activeMethods.length) {
					/* remove empty rows */
					for (i = 0; i < csLength; i++) {
						/* M.H. 12 Oct. 2011 Fix for bug #91580 */
						if (!this._isVisibleColumn(cs[ i ].columnKey)) {
							continue;
						}
						newMax = 0;
						for (j = 0; j < cs[ i ].summaryOperands.length; j++) {
							if (cs[ i ].summaryOperands[ j ].active === true) {
								newMax++;
							}
						}

						if (newMax > max) {
							max = newMax;
						}
					}
					summariesRowsLength = this._summariesRows.length;

					if (max > summariesRowsLength) {
						return;
					}
					for (i = 0; i < max; i++) {
						this.grid._rootContainer().find("#" + this._id("summaries", "footer_row_text_container",
									this._summariesRows[ i ].type.toLowerCase(),
									columnKey))
							.html(this.options.emptyCellText);
					}
					for (i = max; i < summariesRowsLength; i++) {
						this.grid._rootContainer().find("#" + this._summariesRows[ i ].id).remove();
						if (hasFixedColumns) {
							this.grid._rootContainer()
								.find("#" + this._summariesRows[ i ].id + "_fixed")
								.remove();
						}
					}
					for (i = max; i < summariesRowsLength; i++) {
						this._summariesRows.pop();
					}
					if (hasFixedColumns && this.grid.options.height === null &&
						$.ig.util.isIE && $.ig.util.browserVersion >= 9) {
						this._getColumnFixingInstance()._syncTableHeights();
					}
				} else if (activeMethods.length > unactiveMethodsLength) {
					this._initCompactModeSummariesRows();
				}
				this._renderFooterColumnInfo(columnKey, this._data[ columnKey ]);

				if (this._summariesRows.length === 0) {
					this._insertSummariesRows(this.defaultSummaryMethods[ 0 ].type.toLowerCase(),
						this.defaultSummaryMethods[ 0 ].order);
					this._renderFooterIcons();
				}
				this._adjustHeights();
			}
			if (isToRerender &&
					(compactRenderingMode === false ||
					(compactRenderingMode === "auto" && this._analyzeAutoCompactRenderingMode() === false))
					) {
				/* if it should be removed members do not recalculate only remove cells */
				for (i = 0; i < unactiveMethodsLength; i++) {
					methodNameToRemove = unactiveMethods[ i ].type.toLowerCase();
					this.grid._rootContainer()
						.find("#" + this._id("summaries", "footer_row_text_container", methodNameToRemove, columnKey))
						.html(this.options.emptyCellText);
					this._removeDataResult(columnKey, methodNameToRemove);
					/* M.H. 12 Oct. 2011 Fix for bug #91580 */
					if (this._summariesRows.length <= 1) {
						continue;
					}
					/* check is to remove row  */
					/* it should be removed only when summaries rows are more than 1  */
					/* for each cell of this row it is not shown info */
					isToRemoveRow = true;
					for (j = 0; j < this.columnSettings.length; j++) {
						if (!this._isVisibleColumn(this.columnSettings[ j ].columnKey)) {
							continue;
						}
						for (k = 0; k < this.columnSettings[ j ].summaryOperands.length; k++) {
							if (this.columnSettings[ j ].summaryOperands[ k ].type.toLowerCase() ===
								methodNameToRemove && this.columnSettings[ j ].summaryOperands[ k ].active !== false) {
								isToRemoveRow = false;
								break;
							}
						}
						if (isToRemoveRow === false) {
							break;
						}
					}
					if (isToRemoveRow === true && this._summariesRows.length > 1) {
						this._removeRowsDataByType(methodNameToRemove);
						this.grid._rootContainer()
							.find("#" + this._id("summaries", "footer_row", methodNameToRemove))
							.remove();
						if (hasFixedColumns) {
							this.grid._rootContainer()
								.find("#" + this._id("summaries", "footer_row", methodNameToRemove, "fixed"))
								.remove();
						}
					}
				}
				if (this._summariesRows.length >= 1 && isToRemoveFirstRow === true) {
					for (i = 0; i < activeMethods.length; i++) {
						if (activeMethods[ i ].type.toLowerCase() === rowToRemoveMethodName) {
							break;
						}
					}
					if (i === activeMethods.length) {
						this._removeRowsDataByType(rowToRemoveMethodName);
						this.grid._rootContainer()
							.find("#" + this._id("summaries", "footer_row", rowToRemoveMethodName))
							.remove();
						if (hasFixedColumns) {
							this.grid._rootContainer()
								.find("#" + this._id("summaries", "footer_row", rowToRemoveMethodName, "fixed"))
								.remove();
						}
					}
				}
				if (this._summariesRows.length > 0 && this.grid._rootContainer().find("#" +
					this._id("summaries", "footer_row_button_dd",
					this._summariesRows[ 0 ].type, columnKey)).length === 0) {
					this._renderFooterIcons();
				}
				this._adjustHeights();
			}
		},
		_analyzeAutoCompactRenderingMode: function () {
			/* when compact rendering mode is Auto analyze whether to render as compact or not  */
			/* when for each of the columns is selected only 1(or less) methods then render as compact and returns TRUE otherwise returns FALSE */
			var i, j, methods,
				cs,
				methodsLength,
				activeMethodsCount,
				columnSettingsLength = this.columnSettings.length;
			for (j = 0; j < columnSettingsLength; j++) {
				cs = this.columnSettings[ j ];
				methods = cs.summaryOperands;
				methodsLength = methods.length;
				/* check each of the rendered methods - if there are changes - re-render */
				activeMethodsCount = 0;
				for (i = 0; i < methodsLength; i++) {
					if (methods[ i ].active === true && ++activeMethodsCount > 1) {
						return false;
					}
				}
			}
			return true;
		},
		_removeRowsDataByType: function (methodNameToRemove) {
			if (methodNameToRemove === undefined || methodNameToRemove === null) {
				return;
			}
			var hasFixedColumns = this.grid.hasFixedColumns(), self = this;
			this._summariesRows = $.grep(this._summariesRows, function (n) {
				if (n.type === methodNameToRemove) {
					$(n).remove();
					if (hasFixedColumns) {
						self.grid.container().find("#" + n.id + "_fixed").remove();
					}
				}
				return (n.type !== methodNameToRemove);
			});
		},
		_syncFixedUnfixedRowHeights: function () {
			var $tfoot = this.grid.footersTable().find("tfoot"),
				$fixedTFoot = this.grid.fixedFootersTable().find("tfoot"),
				summariesRows = $tfoot.find("tr[ data-role ]"),
				fixedSummariesRows = $fixedTFoot.find("tr[ data-role ]"),
				$fixedRow;
			$.each(summariesRows, function (index, r) {
				var h, fixedRowHeight, rowHeight, $row = $(r);

				$fixedRow = fixedSummariesRows.eq(index);
				if ($fixedRow.length === 0) {
					return true;
				}
				fixedRowHeight = $fixedRow.height();
				rowHeight = $row.height();
				if (fixedRowHeight > rowHeight) {
					h = fixedRowHeight;
				} else {
					h = rowHeight;
				}
				$row.height(h);
				$fixedRow.height(h);
			});
		},
		_clearFooterIcons: function () {
			var i, currentRow = this._currentRowShownIcons,
				columns,
				columnsLength;

			if (currentRow === null || currentRow === undefined) {
				return;
			}

			columns = this.grid.options.columns;
			columnsLength = columns.length;
			for (i = 0; i < columnsLength; i++) {
				this.grid._rootContainer().find("#" + this._id("summaries", "footer_row_button_dd",
					currentRow.type, columns[ i ].key)).remove();
			}
		},
		clearAllFooterIcons: function () {
			/* Remove all summaries dropdown buttons. */
			var i, j,
				rows = this._summariesRows,
				rowsLength = rows.length,
				columns = this.grid.options.columns,
				columnsLength = columns.length;

			for (i = 0; i < rowsLength; i++) {
				for (j = 0; j < columnsLength; j++) {
					this.grid._rootContainer().find("#" + this._id("summaries", "footer_row_button_dd",
						rows[ i ].type, columns[ j ].key)).remove();
				}
			}
		},
		_clearAllRows: function () {
			/* remove all rows as markup AND remove internal rows Data */
			var i,
				summaryRowsLength = this._summariesRows.length,
				hasFixedColumns = this.grid.hasFixedColumns(),
				$row;

			for (i = 0; i < summaryRowsLength; i++) {
				$row = this.grid._rootContainer().find("#" + this._summariesRows[ i ].id);
				$row.remove();
				if (hasFixedColumns) {
					this.grid._rootContainer().find("#" + this._summariesRows[ i ].id + "_fixed").remove();
				}
			}
			this._summariesRows.length = 0;
		},
		_removeUnusedFooterRows: function (oldRows) {
			var i, j,
				sr = this._summariesRows,
				summariesRowsLength = this._summariesRows.length,
				hasFixedColumns = this.grid.hasFixedColumns(),
				oldRowsLength = oldRows.length;
			/* if count of summaries rows is 0 then remove old rows */
			if (summariesRowsLength === 0) {
				for (i = 0; i < oldRowsLength; i++) {
					this.grid._rootContainer().find("#" + oldRows.id).remove();
				}
				return;
			}
			/* remove those old rows that are not contained in summaries rows */
			for (i = 0; i < oldRowsLength; i++) {
				for (j = 0; j < summariesRowsLength; j++) {
					if (sr[ j ].id === oldRows[ i ].id) {
						continue;
					}
				}
				if (j === summariesRowsLength) {
					this.grid._rootContainer().find("#" + oldRows[ i ].id).remove();
					if (hasFixedColumns) {
						this.grid._rootContainer()
							.find("#" + oldRows[ i ].id + "_fixed")
							.remove();
					}
				}
			}
		},
		_renderFooterIcons: function (startInd, endInd) {
			var i,
				columnKey,
				sr = this._summariesRows,
				columnSetting,
				isToRemoveRow = true,
				gridColumns = this.grid.options.columns,
				gridColumnsLength = gridColumns.length,
				methodName;

			if (startInd === null || startInd === undefined) {
				startInd = 0;
			}
			if (endInd === null || endInd === undefined) {
				endInd = gridColumnsLength;
			}
			if (sr.length === 0) {
				return;
			}
			this._clearFooterIcons();
			methodName = sr[ 0 ].type;
			this._currentRowShownIcons = sr[ 0 ];
			for (i = startInd; i < endInd; i++) {
				columnKey = gridColumns[ i ].key;
				if (this._disallowedSummaries[ columnKey ] === true) {
					continue;
				}
				/* check is to render icon - if there is no summary methods do not render */
				columnSetting = this._getColumnSettingByKey(columnKey);
				if (columnSetting === null ||
						columnSetting === undefined ||
						columnSetting.length === 0 ||
						columnSetting.allowSummaries === false) {
					continue;
				}
				isToRemoveRow = false;
				this._renderFooterIconMarkup(methodName, columnKey);
				/*
				$button = $("<span></span>")
					.appendTo($("#" + this._id("summaries", "footer_row_icon_container", methodName, columnKey)))
					.addClass(self.css.footerButtonIcon);//self.css.footerButtonIcon
				$button.wrap("<a id="" +
					this._id("summaries", "footer_row_button_dd", methodName, columnKey) + "" href="#" title="" + "" ></a>");
				$button.data("columnKey", columnKey);
				$button.parent().bind({
					mousedown: $.proxy(self._onMouseDownFooterIcon, self),
					click: $.proxy(self._onClickFooterIcon, self),
					mouseover: $.proxy(self._onMouseOverFooterIcon, self),
					mouseout: $.proxy(self._onMouseOutFooterIcon, self)
				});
				*/
			}
			if (isToRemoveRow === true) {
				this._removeRowsDataByType(methodName);
			}
			/* M.H. 14 Oct. 2011 Fix for bug #88116 */
			/*
			if (this._documentClickHandler === null || this._documentClickHandler === undefined) {
				documentClickHandler = $.proxy(self._documentClick, self);
				self._documentClickHandler = documentClickHandler;
				$(document).bind("mousedown", documentClickHandler);
			}
			*/
		},
		_renderFooterIconMarkup: function (methodName, columnKey) {
			var self = this, $td, $button;
			$button = $("<span></span>")
				.appendTo(this.grid._rootContainer()
				.find("#" + this._id("summaries", "footer_row_icon_container", methodName, columnKey)))
				.addClass(self.css.footerButtonIcon);//self.css.footerButtonIcon
			$button.wrap("<a id='" +
				this._id("summaries", "footer_row_button_dd", methodName, columnKey) +
				"' href='#' title='' tabindex='" + this.grid.options.tabIndex + "'></a>");
			$button.data("columnKey", columnKey);
			/* M.H. 15 June 2015 Fix for bug 201033: When column virtualization is enabled opening the summaries via the summaries button throwns an error */
			$td = $button.closest("td");
			$td.data("columnKey", columnKey);
			/* M.H. 11 Mar 2016 Fix for bug 215730: There is a memory leak when calling igGridSummaries.calculateSummaries continuously */
			$td.unbind(".summaries");
			$td.bind({
				"mousedown.summaries": function (e) { self.toggleDropDown(columnKey, e); e.stopPropagation(); }
			});
			$button.parent().bind({
				mousedown: $.proxy(self._onMouseDownFooterIcon, self),
				click: $.proxy(self._onClickFooterIcon, self),
				mouseover: $.proxy(self._onMouseOverFooterIcon, self),
				mouseout: $.proxy(self._onMouseOutFooterIcon, self),
				focus: $.proxy(self._onMouseOverFooterIcon, self),
				blur: $.proxy(self._onMouseOutFooterIcon, self),
				keydown: function (event) {
					if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
						event.stopPropagation();
						event.preventDefault();
						self.toggleDropDown(null, event);
					}
				}
			});
		},
		_onMouseOverFooterIcon: function (e) {
			var $button = $(e.target);

			if ($button.closest("div").hasClass(this.css.footerButtonIconActive) === false) {
				$button.closest("div").addClass(this.css.footerButtonIconHover);
			}
		},
		_onMouseOutFooterIcon: function (e) {
			var $button = $(e.target);
			if ($button.closest("div").hasClass(this.css.footerButtonIconActive) === false) {
				$button.closest("div").removeClass(this.css.footerButtonIconHover);
			}
		},
		_onClickFooterIcon: function (e) {
			e.preventDefault();
		},
		_onMouseDownFooterIcon: function (event) {
/*			event.preventDefault(); */
/*			event.stopPropagation(); */
			this.toggleDropDown(null, event);//, true
		},
		_isFirstRowEmpty: function () {
			/*
			Check whether first footer summaries row is empty
			*/
			var i,
				res = true,
				columnKey,
				sr = this._summariesRows,
				data = null,
				row,
				gridColumns = this.grid.options.columns,
				gridColumnsLength = gridColumns.length;

			if (sr.length === 0) {
				/* there is no first row */
				return false;
			}
			row = sr[ 0 ];
			for (i = 0; i < gridColumnsLength; i++) {
				columnKey = gridColumns[ i ].key;
				data = this._getDataByKeyMethodName(columnKey, row.type);
				if (data !== null) {
					return false;
				}
			}
			return res;
		},
		toggleDropDown: function (columnKey, event) {//, explicitClose
			/* Toggle drop down
			paramType="string" toggle drop down for the column with the specified key
			paramType="object" event object. Its data should contain current columnKey, isAnimating, buttonId*/
			/*paramType="bool" If true and option calculateRenderMode is okcancelbuttons then rollbacks checkboxes state before showing the active dropdown */
			var $target, $dialog, top, left, self, isVisible, rOffset, $footersTable, $td;
			if (!event) {
				/* M.H. 2 Jul 2014 Fix for bug #174640: When click summries cell to show menu in fixed part js error is thrown */
				if (this.grid.isFixedColumn(columnKey)) {
					$footersTable = this.grid.fixedFootersTable();
				} else {
					$footersTable = this.grid.footersTable();
				}
				$target = $footersTable
					.find("tfoot tr:first-child td[ data-key=" + columnKey + " ] a>span");
			} else {
				/* M.H. 15 June 2015 Fix for bug 201033: When column virtualization is enabled opening the summaries via the summaries button throwns an error */
				$td = $(event.target).closest("td");
				columnKey = $td.data("columnKey");
				$target = $td.find("a>span");
				if (!$target.length) {
					return;
				}
			}
			if (columnKey === null || columnKey === undefined) {
				/* tries to get span */
				$target = $target.find("span");
				columnKey = $target.data("columnKey");
			}
			$dialog = this.grid._rootContainer()
				.find("#" + this._id("summaries", "methods_dialog", columnKey));
			top = $.ig.util.offset($target).top;
			left = $.ig.util.offset($target).left;
			self = this;
			isVisible = ($dialog.is(":visible") === true);
			/* if $dialog is not found OR animating is in progress AND active dialog is the same as $dialog */
			if ($dialog.length === 0 ||
					$dialog.data("isAnimating") === true
					) {
				return;
			}
			/* M.H. 31 Oct 2013 Fix for bug #156216: Summaries dialog appears over the summaries button in jquery 1.8.1 and above */
			top -= $target.height() - 2;
			if ($target.is("span") === true) {
				/* M.H. 18 Oct. 2011 Fix for bug 92709 - save id of the button element */
				$dialog.data("buttonId", $target.parent().attr("id"));
			}
			if (!isVisible) {
				rOffset = $.ig.util.getRelativeOffset($dialog);
				$dialog.css({ "top": top - $dialog.height() - rOffset.top, "left": left - rOffset.left });
			}
			if (self._activeDialog !== null &&
					self._activeDialog !== undefined &&
					$dialog !== self._activeDialog &&
					columnKey !== self._activeDialog.data("columnKey")) {
				self.showHideDialog(self._activeDialog);//, true
			}

			if (isVisible === true) {
				self._currentOpenedColumnKey = null;
			} else {
				self._currentOpenedColumnKey = columnKey;
			}
			/* M.H. 10 Jan 2014 Fix for bug #160290: Reversing change from summaries dropdown does not work when clicking over summaries footer button or hitting escape key */
			self.showHideDialog($dialog);//, explicitClose
		},
		showHideDialog: function ($dialog) {//, explicitClose
			/* Show/Hide dialog
				paramType="object" jQuery object representation of dropdown div element*/
			/*paramType="bool" If true and option and option calculateRenderMode is okcancelbuttons then rollbacks checkboxes state before showing the active dropdown */
			if ($dialog === null || $dialog === undefined) {
				return;
			}
			var self = this,
				e = this.events,
				/* M.H. 18 Oct. 2011 Fix for bug 92709 */
				$button = this.grid._rootContainer().find("#" + $dialog.data("buttonId")),
				columnKey = $dialog.data("columnKey"),
				animationDuration = self.options.dropDownDialogAnimationDuration;
			if ($dialog.data("isAnimating") === true && self._activeDialog === $dialog) {
				return;
			}
			if ($dialog.is(":visible") === true) {
				/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
				if (this._trigger(e.dropDownClosing, null, { columnKey: columnKey, owner: this }) === false) {
					return;
				}
				if ($button !== null && $button !== undefined) {
					$button.closest("div").removeClass(self.css.footerButtonIconActive);
				}
			} else {
				/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
				if (this._trigger(e.dropDownOpening, null, { columnKey: columnKey, owner: this }) === false) {
					return;
				}
				if ($button !== null && $button !== undefined) {
					$button.closest("div").removeClass(self.css.footerButtonIconHover);
					$button.closest("div").addClass(self.css.footerButtonIconActive);
					/* M.H. 13 Jan 2014 Fix for bug #160382: Summaries hidden by the user get visible after an igGrid.dataBind method was called. */
					this._rollbackCheckboxState(columnKey);
				}
			}
			/* M.H. 14 Oct. 2011 Fix for bug 88116 */
			if (animationDuration <= 0) {
				animationDuration = 1;
			}
			$dialog.data("isAnimating", true);
			$dialog.toggle(
				"slide",
				{
					duration: animationDuration,
					direction: "down"
				},
				function () {
					$dialog.data("isAnimating", false);
					if ($dialog.is(":visible") === true) {
						$dialog.focus();
						/* M.H. 3 Oct 2013 Fix for bug #153904: When you open feature chooser and move column by dragging the feature chooser does not close. */
						self.grid._focusedElement = $dialog;
						self._activeDialog = $dialog;
						/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
						self._trigger(e.dropDownOpened, null, { columnKey: columnKey, owner: this });
					} else {
						/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
						self._trigger(e.dropDownClosed, null, { columnKey: columnKey, owner: this });
						/* M.H. 3 Oct 2013 Fix for bug #153904: When you open feature chooser and move column by dragging the feature chooser does not close. */
						self.grid._focusedElement = null;
						/* on close try to calculate for the selected column summaries */
						if (self.options.type === "remote" &&
								self.options.calculateRenderMode === "onselect" &&
								self.isToCalculateInRemoteMode === true) {
							/* recalculate when the drop down is closed(if there is such need) */
							self.isToCalculateInRemoteMode = false;
							self._setDataSourceColumnSettingsOptions();
							self.grid.dataSource.dataBind();
						}
						/*self._activeDialog = null; */
						/*$(document).unbind("mousedown", documentClickHandler); */
						/* M.H. 31 Oct. 2011 Fix for bug 94362 */
						if (self._activeDialog !== null &&
								self._activeDialog !== undefined &&
								(self._activeDialog.data("columnKey") === columnKey)) {
							self._activeDialog = null;
						}
					}
				}
			);
		},
		/* M.H. 14 Oct. 2011 Fix for bug 88116 */
		/*
		_documentClick: function (e) {
			var self = this,
				clickX = e.pageX,
				clickY = e.pageY,
				$dialog = this._activeDialog,
				dialogWidth,
				dialogHeight,
				dialogTop,
				dialogLeft;
			return;
			if ($dialog === null || $dialog === undefined) {
				return;
			}
			if ($dialog.is(":visible") === false) {
				this._activeDialog = null;
				return;
			}

			dialogWidth = $dialog.width();
			dialogHeight = $dialog.height();
			dialogTop = parseInt($dialog.css("top"), 10);
			dialogLeft = parseInt($dialog.css("left"), 10);

			if (clickX < dialogLeft || clickX > dialogLeft + dialogWidth
					|| clickY < dialogTop || clickY > dialogTop + dialogHeight) {
				self._closeDropDown();
			}
		},
		*/
		_closeDropDown: function () {
			var $dialog = this._activeDialog;
			this.showHideDialog($dialog);//, true
		},
		_setDataSourceColumnSettingsOptions: function () {
			/* it should be initialized/refreshed newColumneSettings before call this method */
			var o = this.options;

			this.grid.dataSource.settings.summaries.columnSettings = this.newColumnSettings;
			this.grid.dataSource.settings.summaries.type = o.type;
			this.grid.dataSource.settings.summaries.summaryExecution = o.summaryExecution;
			this.grid.dataSource.settings.summaries.summaryExprUrlKey = o.summaryExprUrlKey;
			this.grid.dataSource.settings.summaries.summariesResponseKey = o.summariesResponseKey;
			/* L.A. 15 January 2013 - Fixing bug #130642, 128891 */
			/* The hierarchical grid bound to remote data throws a js error when you expand a row. */
			if (this.grid.dataSource.type && this.grid.dataSource.type() === "remoteUrl" &&
					this.grid._dataOptions && $.type(this.grid._dataOptions.dataSource) === "string") {
				this.grid.dataSource.settings.dataSource = this.grid._dataOptions.dataSource;
			}
		},
		_getColumnSettingByKey: function (columnKey) {
			var i, len = this.columnSettings.length;

			if (columnKey === null || columnKey === undefined) {
				return null;
			}
			for (i = 0; i <  len; i++) {
				if (this.columnSettings[ i ].columnKey === columnKey) {
					return this.columnSettings[ i ];
				}
			}
			return null;
		},
		/* summary columns specific code */
		_renderHeaderCellButton: function (columnCell, columnKey) {
			var summaryHeaderIconContainer,
				self = this,
				css = self.css,
				buttonId = this._id("summaries", "header_column_icon", columnKey),
				$button,
				$divHeaderButtonContainer;

			summaryHeaderIconContainer = columnCell.find(".ui-iggrid-indicatorcontainer");
			this.grid._enableHeaderCellFeature(columnCell);
			if (summaryHeaderIconContainer.length === 0) {
				summaryHeaderIconContainer = $("<div></div>").appendTo(columnCell);
				summaryHeaderIconContainer.addClass("ui-iggrid-indicatorcontainer");
			}
			this.grid._rootContainer().find("#" + buttonId).remove();
			/* M.H. 31 Oct. 2011 Fix for bug 94362 */
			$button = $("<a></a>").attr({
						"href": "#",
						"id": buttonId,
						"tabindex": this.grid.options.tabIndex,
						"title": this.options.summariesHeaderButtonTooltip
					});
			/* M.H. 31 Oct. 2011 Fix for bug 94362 */
			$divHeaderButtonContainer = summaryHeaderIconContainer
				.find(".ui-iggrid-summaries-headerbuttoncontainer");
			if ($divHeaderButtonContainer.length === 0) {
				$divHeaderButtonContainer =
					$("<div></div>")
						.addClass(css.headerButtonContainer)
						.appendTo(summaryHeaderIconContainer);
			}
			$button.appendTo($divHeaderButtonContainer);
			$("<span></span>")
				.addClass(css.headerButtonIcon)
				.appendTo($button);
			if ($.ig.util.isIE7 === true) {
				$button.parent().addClass(this.css.baseClassIE7);
			}
			$button.hover(
				function () {$(this).parent().addClass(css.headerButtonIconHover); },
				function () {$(this).parent().removeClass(css.headerButtonIconHover); }
			);
			$button.bind({
				click: function (event) {
					event.preventDefault();
					event.stopPropagation();
					self._internalToggleSummariesRow(null, event);
				}
			});
		},
		_togglefromfc: function (event, columnKey, isSelected) {
			this._internalToggleSummariesRow(isSelected, event);
		},
		_internalToggleSummariesRow: function (isToShow, event) {
			if (isToShow === null || isToShow === undefined) {
				isToShow = !this._isRenderedSummariesRows;
			}
			/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
			if (this._trigger(this.events.summariesToggling, event,
				{ isToShow: isToShow, owner: this }
				) === false) {
				return;
			}
			this.toggleSummariesRows(isToShow, true);
			/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
			this._trigger(this.events.summariesToggled, event, { isShown: isToShow, owner: this });
		},
		toggleSummariesRows: function (isToShow, isInternalCall) {
			/* Toggle summaries rows
			paramType="bool" Specifies whether to show or not summaries
			paramType="bool" Optional parameter.Specifies whether this function is called internally by the widget. */
			var fc, cs, csLength, i, $hScroller, hScrollerLeft, grid = this.grid;
			if (isToShow === true) {
				$hScroller = this.grid._rootContainer().find("#" + grid.id() + "_horizontalScrollContainer");
				/* M.H. 11 Sep 2013 Fix for bug #146740: Toggling the summaries will show wrong results when columnVirtualization = true */
				if ($hScroller.length > 0 &&
					(grid.options.columnVirtualization || grid._isColumnVirtualizationEnabled())) {
					hScrollerLeft =  $hScroller.scrollLeft();
					$hScroller.scrollLeft(0);
				}
				this.calculateSummaries();
				/* M.H. 11 Sep 2013 Fix for bug #146740: Toggling the summaries will show wrong results when columnVirtualization = true */
				if (hScrollerLeft) {
					setTimeout(function () {
						$hScroller.scrollLeft(hScrollerLeft);
					}, 0);
				}
				/* M.H. 3 Sept. 2015 Fix for bug #89580: When grid have fixed width and height and when hide and show summaries, columns are displaced towards to column's summaries */
				grid._synchronizeHScroll();
				this._isSummariesRowsHidden = false;
				/* M.H. 23 Jan 2012 Fix for bug #99938 */
				if (this._summariesRows.length === 0) {
					/* it should be shown summaries rows */
					cs = this.newColumnSettings;
					csLength = cs.length;
					this._insertSummariesRows(this.defaultSummaryMethods[ 0 ].type,
						this.defaultSummaryMethods[ 0 ].order);
					for (i = 0; i < csLength; i++) {
						if (!this._isVisibleColumn(cs[ i ].columnKey)) {
							continue;
						}
						this._renderFooterIconMarkup(this.defaultSummaryMethods[ 0 ].type, cs[ i ].columnKey);
					}
				}
			} else {
				this._removeFooterRows();
				this._isSummariesRowsHidden = true;
			}
			this._adjustHeights();
			if (isInternalCall === undefined || isInternalCall === false) {
				fc = grid.element.data("igGridFeatureChooser");
				if (fc !== null && fc !== undefined) {
					fc._toggleSelectedItems("Summaries", !isToShow);
				}
			}
			/* M.H. 10 Jul 2014 Fix for bug #175832: When a summary is spanned on two lines and there is a fixed column hiding and showing the Summaries makes the table misaligned. */
			if (grid.hasFixedColumns()) {
				if ($.ig.util.isIE && $.ig.util.browserVersion >= 9) {
					grid.element.data("igGridColumnFixing")._syncTableHeights();
				}
				this._syncFixedUnfixedRowHeights();
			}
		},
		_renderDropDown: function (columnKey) {
			var $dialog, $ul, i, j,
				$buttonOK, $buttonCancel,
				$div, minInd, swap, $li,
				id = this._id("summaries", "methods_dialog", columnKey), methodsLength,
				cs = this._getColumnSettingByKey(columnKey),
				css = this.css,
				checkboxId,
				allowedMethods,
				self = this,
				o = this.options,
				isChecked = false,
				cssClassCheckBox,
				onFocusLi,
				onMousedownLi,
				toggleState,
				onBlurLi,
				onKeydownLi,
				onMousedown,
				onMouseover,
				onMouseout,
				$checkbox,
				onClick;

			if (cs === null || cs === undefined) {
				return;
			}
			allowedMethods = cs.summaryOperands;
			if (allowedMethods === null || allowedMethods === undefined) {
				return;
			}
			/* remove div with specified id if exists */
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			/* M.H. 7 Nov 2012 Fix for bug #126637 */
			this.grid._rootContainer().find("#" + id).remove();
			$dialog = $("<div></div>").appendTo(this.grid._rootContainer()).attr("id", id)
				.css("position", "absolute")
				.css("width", this.options.dropDownWidth)
				.css("height", this.options.dropDownHeight)
				.addClass(this.css.columnSummariesDropdown)
				/* M.H. 14 Oct. 2011 Fix for bug 88116 */
				.attr("tabindex", this.grid.options.tabIndex)
				.hide();
			$dialog.data("methods", allowedMethods);
			$ul = $("<ul></ul>").addClass(css.dropDownListContainer);
			$ul.appendTo($dialog);
			/* render allowed methods */
			methodsLength = allowedMethods.length;
			for (i = 0; i < methodsLength; i++) {
				minInd = i;
				for (j = i; j < methodsLength; j++) {
					if (allowedMethods[ j ].order === null || allowedMethods[ j ].order === undefined) {
						allowedMethods[ j ].order = 0;
					}
					if (allowedMethods[ minInd ].order > allowedMethods[ j ].order) {
						minInd = j;
					}
				}
				if (minInd !== i) {
					/* swap elements */
					swap = allowedMethods[ minInd ];
					allowedMethods[ minInd ] = allowedMethods[ i ];
					allowedMethods[ i ] = swap;
				}
			}
			toggleState = function ($checkbox, event) {
				self.toggleCheckstate($checkbox);
				/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
				/* M.H. 18 Oct. 2011 Fix for bug #92828 - this event should be triggered not only for calculaterendermode="onselect" */
				self._trigger(
					self.events.summariesMethodSelectionChanged,
					event,
					{
						columnKey: columnKey,
						isSelected: ($checkbox.attr("data-chk").toLowerCase() === "on"),
						methodName: $checkbox.data("methodName"),
						owner: this
					}
				);
				if (o.calculateRenderMode === "onselect") {
					self._calculateSelectedSummaries(columnKey);
					if (self.grid.hasFixedColumns()) {
						self._syncFixedUnfixedRowHeights();
					}
				}
			};
			onClick = function (event) {
				toggleState($(event.currentTarget), event);
				/* M.H. 14 Oct. 2011 Fix for bug 88116 */
				if (self._activeDialog) {
					self._activeDialog.data("focussed", true);
					self._activeDialog.focus();
				}
				event.preventDefault();
				event.stopPropagation();
			};
			onMousedown = function (event) {
				/* M.H. 14 Oct. 2011 Fix for bug 88116 */
				if (self._activeDialog) {
					self._activeDialog.data("focussed", true);
					self._activeDialog.focus();
				}
				event.preventDefault();
				event.stopPropagation();
			};
			onMouseover = function (event) {
				$(event.currentTarget).addClass(css.checkboxHovered);
			};
			onMouseout = function (event) {
				$(event.currentTarget).removeClass(css.checkboxHovered);
			};
			$dialog.bind({
				blur: function () {
					/* M.H. 14 Oct. 2011 Fix for bug 88116 */
					$dialog.data("focussed", false);
					setTimeout(function () {
						/* when users clicks very fast two or more drop-down icons - only the last clicked should show its drop-down - other drop-down should be hidden */
						self._checkCloseDropDown($dialog);
					},
					10);
				},
				focus: function () {
					$dialog.data("focussed", true);
				},
				keydown: function (event) {
					if (event.keyCode === $.ui.keyCode.ESCAPE ||
							event.keyCode === $.ui.keyCode.SPACE ||
							event.keyCode === $.ui.keyCode.ENTER) {
						event.stopPropagation();
						event.preventDefault();
						self.toggleDropDown(null, event);//, (event.keyCode === $.ui.keyCode.ESCAPE)
						if (self._summariesRows.length > 0) {
							self.grid.container().find("#" + self._id("summaries", "footer_row_button_dd",
								self._summariesRows[ 0 ].type, columnKey))
								.focus();
						}
					}
				}
			});
			onFocusLi = function () {
				if (self._activeDialog) {
					self._activeDialog.data("focussed", true);
				}
			};
			onBlurLi = function () {
				if (self._activeDialog) {
					self._activeDialog.data("focussed", false);
				}
				setTimeout($.proxy(self._checkCloseDropDown, self), 300);
			};
			onKeydownLi = function (event) {
				var $liFocused = $(this),
					keyCode = event.keyCode;
				if (keyCode === $.ui.keyCode.ENTER || keyCode === $.ui.keyCode.SPACE) {
					/*$liFocused.mousedown(); */
					toggleState(self.grid.container().find("#" + $liFocused.data("checkboxId")), event);
					event.stopPropagation();
					event.preventDefault();
				} else if (keyCode === $.ui.keyCode.ESCAPE) {
					$dialog.blur();
				}
			};
			onMousedownLi = function (event) {
				toggleState(self.grid.container().find("#" + $(this).data("checkboxId")), event);
			};
			for (i = 0; i < methodsLength; i++) {
				checkboxId = this._id("summaries_footer_checkbox",
					allowedMethods[ i ].type.toLowerCase(), columnKey);
				if (allowedMethods[ i ].active === true || allowedMethods[ i ].active === 1) {
					isChecked = "on";
					cssClassCheckBox = css.checkboxOn;
				} else {
					isChecked = "off";
					cssClassCheckBox = css.checkboxOff;
				}
				$li = $("<li></li>")
					.data("checkboxId", checkboxId)
					.addClass(css.dropDownListItem)
					.appendTo($ul)
					.attr("tabindex", this.grid.options.tabIndex);
				$li.bind({
					focus: onFocusLi,
					blur: onBlurLi,
					keydown: onKeydownLi,
					mousedown: onMousedownLi
				});
				$("<span id='" + checkboxId + "' class='" + css.checkbox + "'></span>")
					.appendTo($li);
				$("<label></label>")
					.html(allowedMethods[ i ].rowDisplayLabel)
					.appendTo($li);
				$checkbox = this.grid._rootContainer().find("#" + checkboxId);
				$("<span class='" + cssClassCheckBox + "'></span>").appendTo($checkbox);
				$checkbox
					.attr("data-chk", isChecked)
					.attr("data-role", "checkbox");
				$checkbox.data("methodName", allowedMethods[ i ].type);
				$checkbox.bind({
					/*change: onChange, */
					"click": onClick,
					"mouseover": onMouseover,
					"mouseout": onMouseout,
					"mousedown": onMousedown
				});
			}
			$div = $("<div></div>")
				.addClass(this.css.footerDropdownButtonsContainer)
				.appendTo($dialog);
			if (o.calculateRenderMode === "okcancelbuttons") {
				$buttonOK = $("<a href='http://localhost/'></a>");
				$buttonOK
					.attr({
						"id": this._id("summaries", "dialog_button_ok", columnKey),
						"tabindex": this.grid.options.tabIndex
					})
					.appendTo($div);
				$buttonOK.igButton({
					/* M.H. 22 Nov. 2011 Fix for bug 84440 */
					link: { href: "#", title: "" },
					labelText: this.options.dialogButtonOKText,
					/* M.H. 22 Nov. 2011 Fix for bug 84440 */
					keydown: function (event) {
						if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
							self._clickOKButton(columnKey);
						}
						if (event.keyCode !== $.ui.keyCode.TAB) {
							event.stopPropagation();
							event.preventDefault();
						}
					},
					click: function (e) {
						self._clickOKButton(columnKey);
						/* M.H. 12 Oct. 2011 Fix for bug #89777 */
						e.preventDefault();
						e.stopPropagation();
					},
					mousedown: function (event) {
						event.preventDefault();
						event.stopPropagation();
					},
					/* M.H. 18 Oct. 2011 Fix for bug 88116 - on focus dropdown should NOT be closed */
					focus: function () {
						if (self._activeDialog) {
							self._activeDialog.data("focussed", true);
						}
					},
					blur: function () {
						/* M.H. 18 Oct. 2011 Fix for bug 88116 - on blur check whether dropdown should be closed */
						/* M.H. 20 Oct. 2011 Fix for bug 93136 */
						if (self._activeDialog) {
							self._activeDialog.data("focussed", false);
						}
						setTimeout($.proxy(self._checkCloseDropDown, self), 300);
					}
				});
				$buttonCancel = $("<a></a>");
				$buttonCancel.attr({
					"id": this._id("summaries", "dialog_button_cancel", columnKey),
					"tabindex": this.grid.options.tabIndex
				}).appendTo($div);
				$buttonCancel.igButton({
					/* M.H. 22 Nov. 2011 Fix for bug 84440 */
					link: { href: "#", title: "" },
					labelText: this.options.dialogButtonCancelText,
					click: function (e) {
						self._clickCancelButton(columnKey);
						/* M.H. 12 Oct. 2011 Fix for bug #89777 */
						e.preventDefault();
						e.stopPropagation();
					},
					/* M.H. 12 Oct. 2011 Fix for bug #89128 */
					mousedown: function (event) {
						event.preventDefault();
						event.stopPropagation();
					},
					/* M.H. 12 Oct. 2011 Fix for bug #91433   */
					focus: function () {
						/* M.H. 18 Oct. 2011 Fix for bug 88116 - on focus dropdown should NOT be closed */
						if (self._activeDialog) {
							self._activeDialog.data("focussed", true);
						}
					},
					blur: function () {
						/* M.H. 18 Oct. 2011 Fix for bug 88116 - on blur this element check whether dropdown should be closed */
						/* M.H. 20 Oct. 2011 Fix for bug 93136 */
						if (self._activeDialog) {
							self._activeDialog.data("focussed", false);
						}
						setTimeout($.proxy(self._checkCloseDropDown, self), 300);
					}
				});
			}
			$dialog.data("columnKey", columnKey);
		},
		_checkCloseDropDown: function ($dialog) {
			/* M.H. 14 Oct. 2011 Fix for bug 88116 */
			if (this._activeDialog === null) {
				return;
			}
			if (this._activeDialog.data("focussed") === false) {
				setTimeout($.proxy(this._closeDropDown, this), 10);
				return;
			}
			/* M.H. 15 June 2015 Fix for bug 201033: When column virtualization is enabled opening the summaries via the summaries button throwns an error */
			/* when the user clicks consecutively on table cell to show summaries drop-downs(the last shown should be visible and others should hide) */
			if ($dialog && $dialog.is(":visible") &&
				$dialog.attr("id") !== this._activeDialog.attr("id")) {
				this.showHideDialog($dialog);
			}
		},
		toggleCheckstate: function ($checkbox) {
			/* Toggles the checkstate of a checkbox if checkboxMode is not set to off, otherwise does nothing.
				paramType="object" optional="false" Specifies the jQuery object of the checkbox.
			*/
			var noCancel;
			noCancel = true;//this._triggerNodeCheckstateChanging(event, node);
			if (noCancel) {
				if ($checkbox.attr("data-chk") === "on") {
					this.selectCheckBox($checkbox, false);
				} else {
					this.selectCheckBox($checkbox, true);
				}
			}
		},
		selectCheckBox: function ($checkbox, isToSelect) {
			/* Select/Unselect specified checkbox
				paramType="object" Specifies the jQuery object for checkbox
				paramType="bool" Specify whether to select or not checkbox
			*/
			var css = this.css,
				$innerSpan = $checkbox.children("span");

			if (isToSelect === true) {
				$innerSpan.removeClass(css.checkboxOff).addClass(css.checkboxOn);
				$checkbox.attr("data-chk", "on");
			} else {
				$innerSpan.removeClass(css.checkboxOn).addClass(css.checkboxOff);
				$checkbox.attr("data-chk", "off");
			}
		},
		_clickOKButton: function (columnKey) {
			var i, j, cs,
				$checkbox,
				columnSettingsLength = this.columnSettings.length,
				methods,
				methodsLength,
				eventData = [  ], elem;

			if (columnKey === null || columnKey === undefined) {
				return;
			}
			for (j = 0; j < columnSettingsLength; j++) {
				if (this.columnSettings[ j ].columnKey === columnKey) {
					cs = this.columnSettings[ j ];
					break;
				}
			}
			if (cs === null || cs === undefined) {
				return;
			}
			methods = cs.summaryOperands;
			methodsLength = methods.length;
			/* check each of the rendered methods - if there are changes - re-render */
			for (i = 0; i < methodsLength; i++) {
				$checkbox = this.grid._rootContainer().find("#" + this._id("summaries_footer_checkbox",
					methods[ i ].type.toLowerCase(), columnKey));
				elem = { type: methods[ i ].type.toLowerCase(), active: false };
				if ($checkbox.attr("data-chk") !== undefined &&
						$checkbox.attr("data-chk").toLowerCase() === "on") {
					elem.active = true;
				}
				eventData.push(elem);
			}
			/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
			this._trigger(this.events.dropDownOKClicked, null, {
				columnKey: columnKey,
				eventData: eventData,
				owner: this
			});
			/* M.H. 13 Oct. 2011 Fix for bug 90350 */
			this.showHideDialog(
				this.grid._rootContainer().find("#" + this._id("summaries", "methods_dialog", columnKey))
				);
			this._calculateSelectedSummaries(columnKey);
			if (this.grid.hasFixedColumns()) {
				this._syncFixedUnfixedRowHeights();
			}
		},
		_clickCancelButton: function (columnKey) {
			/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
			this._trigger(this.events.dropDownCancelClicked, null, { columnKey: columnKey, owner: this });
			this.showHideDialog(
				this.grid._rootContainer().find("#" + this._id("summaries", "methods_dialog", columnKey))
				);//, true
		},
		_rollbackCheckboxState: function (columnKey) {
			var cs, i, j, methods, isChecked, methodsLength,
				columnSettingsLength = this.columnSettings.length,
				$checkbox;

			if (columnKey === undefined || columnKey === null) {
				return;
			}
			for (j = 0; j < columnSettingsLength; j++) {
				if (this.columnSettings[ j ].columnKey === columnKey) {
					cs = this.columnSettings[ j ];
					/*selectedColumnSettingsIndex = j; */
					break;
				}
			}
			if (cs === null || cs === undefined) {
				return;
			}
			methods = cs.summaryOperands;
			methodsLength = methods.length;
			/* check each of the rendered methods - if there are changes - re-render */
			for (i = 0; i < methodsLength; i++) {
				$checkbox = this.grid._rootContainer().find("#" + this._id("summaries_footer_checkbox",
					methods[ i ].type.toLowerCase(), columnKey));
				if ($checkbox.attr("data-chk") !== undefined) {
					isChecked = ($checkbox.attr("data-chk").toLowerCase() === "on");
					if (methods[ i ].active !== isChecked) {
						if (methods[ i ].active === true) {
							this.selectCheckBox($checkbox, true);
						} else {
							this.selectCheckBox($checkbox, false);
						}
					}
				}
			}
		},
		_initCompactModeSummariesRows: function () {
			/*
			Check how exactly rows should be rendered - if there is such need
			*/
			var i, j, k, cs = this.newColumnSettings,
				csLength = cs.length, columnMaxMethods = cs[ 0 ],
				cmm, cmmLength,
				countRowsToAdd,
				methodName,
				maxMethods = -1,
				summariesRowsLength,
				newMaxMethods;

			for (i = 0; i < csLength; i++) {
				/* M.H. 12 Oct. 2011 Fix for bug #91580 */
				if (!this._isVisibleColumn(cs[ i ].columnKey)) {
					continue;
				}
				newMaxMethods = 0;
				if (cs[ i ].summaryOperands === null || cs[ i ].summaryOperands === undefined) {
					continue;
				}
				for (j = 0; j < cs[ i ].summaryOperands.length; j++) {
					if (cs[ i ].summaryOperands[ j ].active === true) {
						newMaxMethods++;
					}
				}
				if (newMaxMethods > maxMethods) {
					maxMethods = newMaxMethods;
					columnMaxMethods = cs[ i ];
				}
			}
			if (columnMaxMethods === undefined || columnMaxMethods === null) {
				return;
			}
			cmm = columnMaxMethods.summaryOperands;
			cmmLength = cmm.length;
			summariesRowsLength = this._summariesRows.length;
			/* it should not be rendered because it is rendered */
			if (summariesRowsLength > maxMethods) {
				return;
			}
			countRowsToAdd = maxMethods - summariesRowsLength;
			if (countRowsToAdd === 0) {
				return;
			}
			k = 0;
			for (i = 0; i < cmmLength; i++) {
				methodName = cmm[ i ].type.toLowerCase();

				if (this.grid._rootContainer()
					.find("#" + this._id("summaries", "footer_row", methodName)).length === 0) {
					this._insertSummariesRows(methodName, null);
					if (++k === countRowsToAdd) {
						break;
					}
				}
			}
		},
		_calculateInternalSummaries: function () {
			var cs = this.newColumnSettings, csLength = cs.length, i,
				o = this.options, data, res,
				e = this.events;
			/* M.H. 12 Oct. 2011 Fix for bug #91580 */
			/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
			if (this._trigger(e.summariesCalculating, null, { owner: this }) === false) {
				return;
			}
			data = this._dataSummaries();
			if (o.callee !== null &&
					o.callee !== undefined &&
					$.type(o.callee) === "function") {
				data = o.callee(data);
			}
			if (data === null || data === undefined) {
				data = [  ];
			}
			if (o.compactRenderingMode === true ||
					(o.compactRenderingMode === "auto" &&
				this._analyzeAutoCompactRenderingMode() === true)) {
				this._initCompactModeSummariesRows();
			}
			for (i = 0; i < csLength; i++) {
				/* M.H. 12 Oct. 2011 Fix for bug #91580 */
				if (!this._isVisibleColumn(cs[ i ].columnKey)) {
					continue;
				}
				res = this.calculateSummaryColumn(cs[ i ].columnKey,
					cs[ i ].summaryOperands, data, cs[ i ].dataType);
				this._renderFooterColumnInfo(cs[ i ].columnKey, res);
			}
			this._isRenderedSummariesRows = true;
			/* M.H. 10 Oct. 2011 Fix for bug #90333 - add owner as event argument */
			this._trigger(e.summariesCalculated, null, { data: data, owner: this });
		},
		_isVisibleColumn: function (columnKey) {
			/* M.H. 12 Oct. 2011 Fix for bug #91580 - add function to check whether column is hidden */
			var cols = this.grid._visibleColumns(), ret = false;

			$.each(cols, function (index, value) {
				if (value.key === columnKey) {
					ret = true;
					return false;
				}
			});
			return ret;
		},
		calculateSummaryColumn: function (ck, columnMethods, data, dataType) {
			/*
			Summary calculate the whole data for the specified column key, columnMethods and dataType (used when datasource is remote and dataType is date)
			paramType="string" ColumnKey
			paramType="array" Array of column methods objects
			paramType="object" Object which represents result
			dataType="string" represents dataType for the current column
			*/
			var i, j, val,
				o = this.options,
				columnMethodsLength,
				tmpArr = [  ],
				rowsCount = 0,
				res = [  ],
				order,
				resultCalculated,
				resElement,
				methodName,
				gridDataSource,
				isDate;

			if (ck === null || ck === undefined) {
				/* M.H. 22 March 2012 Fix for bug #105616 */
				throw new Error($.ig.GridSummaries.locale.calculateSummaryColumnKeyNotSpecified);
			}
			if (columnMethods === undefined || columnMethods === null) {
				return;
			}
			columnMethodsLength = columnMethods.length;
			/* check if data source is local and type of getting information is local */
			if (o.type === "local") { // && ds._runtimeType !== "remoteUrl"
				rowsCount = data.length;
				isDate = (dataType === "date");
				if (isDate === true) {
					for (i = 0; i < rowsCount; i++) {
						if (data[ i ][ ck ] !== null && data[ i ][ ck ] !== undefined) {
							if (data[ i ][ ck ].getTime) {
								tmpArr.push(this.grid.dataSource.getCellValue(ck, data[ i ]).getTime());
								/*tmpArr.push(data[i][ck].getTime()); */
							} else {
								tmpArr.push(this.grid.dataSource.getCellValue(ck, data[ i ]));
								/*tmpArr.push(data[i][ck]); */
							}
						}
					}
				} else {
					for (i = 0; i < rowsCount; i++) {
						/* M.H. 14 Sep 2012 Fix for bug #120721 */
						if (data[ i ][ ck ] === undefined) {
							data[ i ][ ck ] = null;
						}
						/*tmpArr.push(data[i][ck]);*/
						tmpArr.push(this.grid.dataSource.getCellValue(ck, data[ i ]));
					}
				}
				for (i = 0; i < columnMethodsLength; i++) {
					methodName = columnMethods[ i ].type.toLowerCase();
					if (methodName.startsWith("custom")) {
						methodName = "custom";
					}
					if (columnMethods[ i ].active === true ||
						columnMethods[ i ].active === 1) {
						order = columnMethods[ i ].order;
						if (order === undefined) {
							order = null;
						}
						/* M.H. 27 Sept. 2011 Fix for bug #88793 - when date is null it should be tracked its count */
						if (methodName === "count") {
							resultCalculated = rowsCount;
						} else {
							resultCalculated =
								$.ig.calcSummaries(methodName, tmpArr, columnMethods[ i ].summaryCalculator, dataType);
						}
						resElement = {
							type: columnMethods[ i ].type,
							result: resultCalculated,
							rowDisplayLabel: columnMethods[ i ].rowDisplayLabel,
							order: order,
							decimalDisplay: columnMethods[ i ].decimalDisplay,
							isGridFormatter: columnMethods[ i ].isGridFormatter,
							dataType: dataType
						};
						res.push(resElement);
						this._addDataResult(ck, resElement);
					}
				}
			} else if (o.type === "remote") {
				if (data[ ck ] !== undefined && data[ ck ] !== null) {
					for (i = 0; i < columnMethodsLength; i++) {
						val = data[ ck ][ columnMethods[ i ].type ];
						if (columnMethods[ i ].active === true || columnMethods[ i ].active === 1) {
							order = columnMethods[ i ].order;
							if (order === undefined) {
								order = null;
							}
							if (val === null || val === undefined) {
								if (columnMethods[ i ].summaryCalculator !== undefined) {
									/* M.H. 16 Nov. 2011 Fix for bug 97886 - we should get data from dataSource, we need only the data for the specified by the columnKey fields */
									gridDataSource = this.grid.dataSource._data;
									rowsCount = gridDataSource.length;
									isDate = (dataType === "date");
									if (isDate === true) {
										for (j = 0; j < rowsCount; j++) {
											if (gridDataSource[ j ][ ck ] !== null && gridDataSource[ j ][ ck ] !== undefined) {
												tmpArr.push(this.grid.dataSource.getCellValue(ck, gridDataSource[ j ]).getTime());
											}
										}
									} else {
										for (j = 0; j < rowsCount; j++) {
											tmpArr.push(this.grid.dataSource.getCellValue(ck, gridDataSource[ j ]));
										}
									}
									val = $.ig.calcSummaries(columnMethods[ i ].type.toLowerCase(), tmpArr,
										columnMethods[ i ].summaryCalculator, dataType);
								} else {
									val = "";
								}
							}

							res.push({
								type: columnMethods[ i ].type,
								result: val,
								rowDisplayLabel: columnMethods[ i ].rowDisplayLabel,
								order: order,
								decimalDisplay: columnMethods[ i ].decimalDisplay,
								isGridFormatter: columnMethods[ i ].isGridFormatter,
								dataType: dataType
							});
						}
					}
				}
				this._data[ ck ] = res;
			}
			return res;
		},
		summaryCollection: function () {
			/*
			Return a JQUERY object which holds all summaries for all columns
			*/
			return this._data;
		},
		summariesFor: function (columnKey) {
			/*
			Return a JQUERY object which holds all summaries for column with the specified column key
			*/
			return this._data[ columnKey ];
		},
		_addDataResult: function (columnKey, resElement) {
			var d = this._data[ columnKey ],
				i,
				dLen;

			if (d === null || d === undefined) {
				this._data[ columnKey ] = [  ];
				this._data[ columnKey ].push(resElement);
				return;
			}
			dLen = d.length;
			for (i = 0; i < dLen; i++) {
				if (d[ i ].type.toLowerCase() === resElement.type.toLowerCase()) {
					d[ i ] = resElement;
					break;
				}
			}
			if (i === dLen) {
				this._data[ columnKey ].push(resElement);
			}
		},
		_removeDataResult: function (columnKey, methodName) {
			var d = this._data[ columnKey ];

			if (d === undefined || d === null) {
				return;
			}
			d = $.grep(d, function (n) {
				return (n.type.toLowerCase() !== methodName.toLowerCase());
			});
			this._data[ columnKey ] = d;
		},
		_getDataByKeyMethodName: function (columnKey, methodName) {
			var i, d = this._data[ columnKey ], dLength, res = null;

			methodName = methodName.toLowerCase();
			if (d === undefined || d === null) {
				return null;
			}

			dLength = d.length;

			for (i = 0; i < dLength; i++) {
				if (d[ i ].type.toLowerCase() === methodName) {
					return d[ i ];
				}
			}

			return res;
		},
		_renderFooterColumnInfo: function (columnKey, data) {
			/* render data for the column with specified columnKey */
			if (data === null || data === undefined) {
				return;
			}
			var i, j, methodName, result, decimalDisplay,
				footerRowId, $footerRow, order,
				compactRenderingMode = this.options.compactRenderingMode,
				dataLength = data.length,
				min, swapElement;

			if (compactRenderingMode === true ||
					((this.options.compactRenderingMode === "auto" &&
						this._analyzeAutoCompactRenderingMode() === true))
					) {
				/*if (dataLength < this._summariesRows.length) { */
				/* reorder data */
				if (dataLength > 1) {
					for (i = 0; i < dataLength; i++) {
						min = i;
						for (j = i + 1; j < dataLength; j++) {
							if (data[ j ].order < data[ min ].order) {
								swapElement = data[ j ];
								data[ j ] = data[ min ];
								data[ min ] = swapElement;
								min = j;
							}
						}
					}
				}
				for (i = 0; i < dataLength; i++) {
					if (this._summariesRows[ i ] === undefined ||
							this._summariesRows[ i ] === null ||
							data[ i ] === undefined ||
							data[ i ] === null) {
						continue;
					}
					result = data[ i ].result;
					decimalDisplay = data[ i ].decimalDisplay;
					if (decimalDisplay === undefined || decimalDisplay === null) {
						decimalDisplay = this.defaultDecimalDisplay;
					}
					if (decimalDisplay !== null &&
							decimalDisplay !== undefined &&
							this._isNumber(result) === true) {
						result = (parseFloat(result)).toFixed(decimalDisplay);
					}
					this._renderCellHandler(this._summariesRows[ i ].type,
						columnKey,
						data[ i ]);
				}
			} else if (compactRenderingMode === false ||
					(this.options.compactRenderingMode === "auto" &&
				this._analyzeAutoCompactRenderingMode() === false)) {
				for (i = 0; i < dataLength; i++) {
					methodName = data[ i ].type.toLowerCase();
					footerRowId = this._id("summaries", "footer_row", methodName);
					/*$footerRow = $("#" + footerRowId); */
					/* M.H. 21 Aug 2013 Fix for bug #149886: When you unselect a summary of a fixed column and then select it again it doesn't appear. */
					$footerRow = this.grid._rootContainer().find("#" + footerRowId);
					if ($footerRow.length === 0) {
						order = data[ i ].order;
						/* order footer row */
						if (order === undefined) {
							order = null;
						}
						this._addDataResult(columnKey, data[ 0 ]);
						this._insertSummariesRows(methodName, order);
					}
					/*
					if (this._isFirstRowEmpty() === true) {
						// remove first row if all cells of the first row are empty
						//$("#" + this._id("summaries", "footer_row", this._summariesRows[ 0 ].type)).remove();
						//this._removeRowsDataByType(this._summariesRows[ 0 ].type);
					}
					*/
					/* update value(s) for the cell */
					this._renderCellHandler(
						methodName,
						columnKey,
						data[ i ]
					);
				}
			}
		},
		_renderCell: function (methodName, columnKey, data) {
			var i, resultTemplate = this.options.resultTemplate, column,
				value = data.result, format, res, parseToDate = false,
				oColumnType, oColumnFormat,
				summaryOperand = data.type,
				rowDisplayLabel = data.rowDisplayLabel,
				isGridFormatter = data.isGridFormatter,
				dataType = data.dataType,
				defaultDecimalDisplay = this.options.defaultDecimalDisplay,
				decimalDisplay = data.decimalDisplay,
				isNumber = this._isNumber(value);
			if (isNumber && dataType === "bool") {
				isGridFormatter = false;
			}
			if (dataType === "date" && isNumber &&
				(summaryOperand === "min" || summaryOperand === "max")) {
				parseToDate = true;
				/* M.H. 18 Oct. 2011 Fix for bug 92467 */
				value = new Date(parseInt(value, 10));
			}
			/* M.H. 27 March 2012 Fix for bug #104054 */
			/* M.H. 6 Oct. 2011 Fix for bug #90225 */
			/*If isGridFormatter is true then numbers are formatted according to formatting applied in data grid cells.  */
			/*For numbers decimal display option is taken from columnSettings in summaries. If not set and it is taken from option format in grid columns definition. If it is not set then it is taken from option defaultDecimalDisplay in Summaries */
			if (isGridFormatter === true || (isGridFormatter === undefined &&
				this.options.isGridFormatter === true)) {
				column = this._getGridColumnFor(columnKey);
				if (column !== null) {
					oColumnFormat = column.format;
					oColumnType = column.dataType;
					/* M.H. 14 Oct. 2011 Fix for bug 91175 */
					if (isNumber && !parseToDate) {
						/* if decimalDisplay is explicitly set in summaries columnSetttings OR */
						/* there is no format in grid columns and defaultDecimalDisplay is set OR */
						/* format is set in the option grid column but it is not of type *.**(for instance it is of type currency) - Fixing bug #128404 Summaries Count value is formatted with currency format */
						if (this._isNumber(decimalDisplay) ||
								((oColumnFormat === undefined || oColumnFormat === null) &&
							this._isNumber(defaultDecimalDisplay)) ||
								(summaryOperand === "count" &&
									oColumnFormat &&
									oColumnFormat.indexOf &&
									oColumnFormat.indexOf(".") === -1 &&
									oColumnFormat !== "0")) {
							if ((oColumnFormat === undefined || oColumnFormat === null) ||
								decimalDisplay === undefined) {
								decimalDisplay = defaultDecimalDisplay;
							}
							if (decimalDisplay > 0) {
								format = "0.";
								for (i = 0; i < decimalDisplay; i++) {
									format += "0";
								}
							} else {
								format = "0";
							}
							column.format = format;
						}
					}
					if (summaryOperand === "count") {
						column.dataType = "number";
					}
					/* we set column dataType and format just to apply proper formatting and after that we re-set dataType and format for the column with the cached original values */
					value = this.grid._renderCell(value, column);
					column.format = oColumnFormat;
					column.dataType = oColumnType;
				}
			} else if (isNumber && !parseToDate) {
				/* if we do not format summary cell using grid formatter then */
				/* 1. if dataType is date we should parse it to Date */
				/* 2. we should use decimalDisplay option if datatype is not date (if parseToDate is true then we have already parsed it) */
				if (decimalDisplay === undefined || decimalDisplay === null) {
					decimalDisplay = defaultDecimalDisplay;
				}
				value = (parseFloat(value)).toFixed(decimalDisplay);
			}
			if (value === null || value === undefined) {
				value = "";
			}
			if (resultTemplate) {
				res = resultTemplate.replace("{0}", rowDisplayLabel).replace("{1}", value);
			} else {
				res = value;
			}
			/* M.H. 21 Aug 2013 Fix for bug #149886: When you unselect a summary of a fixed column and then select it again it doesn't appear. */
			this.grid._rootContainer().find("#" +
				this._id("summaries", "footer_row_text_container", methodName, columnKey))
				.html(res);
		},
		_getGridColumnFor: function (columnKey) {
			var i, gridColumnsLength = (this.grid.options.columns) ? this.grid.options.columns.length : 0;
			for (i = 0; i < gridColumnsLength; i++) {
				/* M.H. 12 Oct. 2011 Fix for bug #91580 */
				if (this.grid.options.columns[ i ].key === columnKey) {
					return this.grid.options.columns[ i ];
				}
			}
			return null;
		},
		_isNumber: function (n) {
			return !isNaN(parseFloat(n)) && isFinite(n);
		},
		_columnsCollectionModified: function (event, ui) {
			if (ui.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			/* M.H. 13 Oct. 2011 Fix for bug #91738 */
			if (this._isSummariesRowsHidden === true) {
				return;
			}
			/* M.H. 12 Oct. 2011 Fix for bug #91580 - remove data binding */
			this._clearAllRows();
			this.calculateSummaries();
		},
		_insertSummariesRows: function (methodName, order) {
			methodName = methodName.toLowerCase();
			var srLength, i, j,
				srOrder,
				hasFixed = this.grid.hasFixedColumns(),
				css = this.css,
				$grid = this.grid,
				/* M.H. 12 Oct. 2011 Fix for bug #91580 */
				gridColumns = $grid._visibleColumns(),
				gridColumnsLength = gridColumns.length,
				$tfoot = this._tfoot,
				$td, $tfootFixed,
				$footer = $grid._footer,
				footerRowIdFixed,
				$footerRowFixed,
				footerRowId = this._id("summaries", "footer_row", methodName),
				$footerRow = $("<tr></tr>").attr("id", footerRowId).attr("data-role", "summaryrow"),
				elem = { id: footerRowId, type: methodName, order: order },
				footerRowCellMousein = function () { $(this).addClass(css.footerRowCellHover); },
				footerRowCellMouseout = function () { $(this).removeClass(css.footerRowCellHover); };

			if (hasFixed === true) {
				footerRowIdFixed = this._id("summaries", "footer_row", methodName, "fixed");
				$footerRowFixed = $("<tr></tr>").attr("id", footerRowIdFixed).attr("data-role", "summaryrow");
				$tfootFixed = this.grid.fixedFootersTable().find("tfoot");
			}
			if ($tfoot === null || $tfoot === undefined) {
				if ($footer === undefined || $footer === null || $footer.length === 0) {
					/* init $footer */
					$grid._renderFooter();
					$footer = $grid._footer;
				}
				if ($footer === undefined || $footer === null || $footer.length === 0) {
					/* M.H. 22 March 2012 Fix for bug #105616 */
					/*throw new Error("Footer is not defined in _insertSummariesRows"); */
					return;
				}
				/* M.H. 11 Oct. 2011 Fix for bug #91147 */
				$footer.css("display", "");
				if (this._isVirtualization() === true) {
					/*$tfoot = $("#" + $grid.element[ 0 ].id + "_footers").find("tfoot"); */
					$tfoot = this.grid.footersTable().find("tfoot");
				} else if (this._isFixedFooters() === true) {
					$tfoot = $footer.find("tfoot");
				} else {
					$tfoot = $footer;
				}
				this._tfoot = $tfoot;
			}
			/* M.H. 10 Oct. 2011 Fix for bug #89870 */
			/* fix strange behavior - when it is in hierarchical grid and hide column, expand and close first layout */
			/* then show hidden column and expand again first layout when click to hide summaries it works */
			/* BUT when again clicks on summaries they are not shown, appendTo is not working */
			if ($tfoot.attr("id") !== "" && $tfoot.attr("id") !== null && $tfoot.attr("id") !== undefined) {
				$tfoot = this.grid._rootContainer().find("#" + $tfoot.attr("id"));
			}
			if (order === null) {
				elem.order = this._summariesRows.length;
				this._summariesRows.push(elem);
				$footerRow.appendTo($tfoot);
				if (hasFixed) {
					$footerRowFixed.appendTo($tfootFixed);
				}
			} else {
				/* insert at the correct position */
				srLength = this._summariesRows.length;
				if (srLength === 0) {
					this._summariesRows.push(elem);
					$footerRow.appendTo($tfoot);
					if (hasFixed) {
						$footerRowFixed.appendTo($tfootFixed);
					}
				} else {
					if (this.options.compactRenderingMode === true ||
							(this.options.compactRenderingMode === "auto" &&
								this._analyzeAutoCompactRenderingMode() === true)) {
						$footerRow.appendTo($tfoot);
						if (hasFixed) {
							$footerRowFixed.appendTo($tfootFixed);
						}
						this._summariesRows.push(elem);
					} else if (this.options.compactRenderingMode === false ||
							(this.options.compactRenderingMode === "auto" &&
								this._analyzeAutoCompactRenderingMode() === false)) {
						for (i = 0; i < srLength; i++) {
							srOrder = this._summariesRows[ i ].order;
							if (srOrder === undefined || srOrder === null) {
								continue;
							}
							if (order <= srOrder) {
								$footerRow
									.insertBefore(
									this.grid._rootContainer()
									.find("#" + this._id("summaries", "footer_row", this._summariesRows[ i ].type))
									);
								if (hasFixed) {
									$footerRowFixed
										.insertBefore(this.grid._rootContainer().find("#" +
										this._id("summaries", "footer_row", this._summariesRows[ i ].type, "fixed")));
								}
								this._summariesRows.splice(i, 0, elem);
								break;
							}
						}
						if (i === srLength) {
							this._summariesRows.push(elem);
							$footerRow.appendTo($tfoot);
							if (hasFixed) {
								$footerRowFixed.appendTo($tfootFixed);
							}
						}
					}
				}
			}
			/* M.H. 2 April 2012 Fix for bug #107567 */
			/* M.H. 4 April 2012 Fix for bug #108082 */
			if (this._isVirtualization() && this.grid.options.virtualizationMode === "fixed") {
				gridColumnsLength = this.grid._virtualColumnCount;
			}
			for (j = 0; j < gridColumnsLength; j++) {
				$td = $("<td></td>")
					.attr("tabIndex", this.grid.options.tabIndex)
					.attr("aria-describedby", this.grid.id() + "_" + gridColumns[ j ].key)
					.addClass(css.footerRowCell)
					.attr("data-key", gridColumns[ j ].key);
				if (gridColumns[ j ].fixed === true) {
					$td.appendTo($footerRowFixed);
					this.grid._rootContainer().find("#" + footerRowIdFixed + " td.ui-state-default").hover(
						footerRowCellMousein,
						footerRowCellMouseout
					);
				} else {
					$td.appendTo($footerRow);
					this.grid._rootContainer().find("#" + footerRowId + " td.ui-state-default").hover(
						footerRowCellMousein,
						footerRowCellMouseout
					);
				}
				if (j === gridColumnsLength - 1 && $grid.options.height &&
					parseInt($grid.options.height, 10) > 0) {
					/* set last col width explicitly */
					$td.css("width", parseInt(gridColumns[ j ].width, 10) + $grid._scrollbarWidth());
				} else {
					$td.css("width", gridColumns[ j ].width);
				}
				this._renderInnerCellMarkup($td, methodName, gridColumns[ j ].key);
				/*
				$textContainer = $("<div></div>")
					.attr("id", this._id("summaries", "footer_row_text_container", methodName, gridColumns[ j ].key))
					.addClass(this.css.footerRowTextContainer)
					.appendTo($td)
					.html(this.options.emptyCellText);
				$iconContainer = $("<div></div>")
					.attr("id", this._id("summaries", "footer_row_icon_container", methodName, gridColumns[ j ].key))
					.addClass(this.css.footerRowIconContainer)
					.insertBefore($textContainer);
				if ($.ig.util.isIE7 === true) {
					$iconContainer.addClass(this.css.baseClassIE7);
				}
				*/
			}
			/* M.H. 23 Aug 2013 Fix for bug #149880: When row selectors are enabled unchecking and checking a summary of a number column makes its Avg summary to render in the next column. */
			if (hasFixed && this.grid.hasFixedDataSkippedColumns() &&
				this.grid.fixingDirection() === "left") {
				this.grid._footerInit($footerRowFixed, null, true, css.footerRowCell);
			} else {
				this.grid._footerInit($footerRow, null, true, css.footerRowCell);
			}
		},
		_renderInnerCellMarkup: function ($td, methodName, columnKey) {
			var $textContainer, $iconContainer;

			$textContainer = $("<div></div>")
				.attr("id", this._id("summaries", "footer_row_text_container", methodName, columnKey))
				.addClass(this.css.footerRowTextContainer)
				.appendTo($td)
				.html(this.options.emptyCellText);
			$iconContainer = $("<div></div>")
				.attr("id", this._id("summaries", "footer_row_icon_container", methodName, columnKey))
				.addClass(this.css.footerRowIconContainer)
				.insertBefore($textContainer);
			if ($.ig.util.isIE7 === true) {
				$iconContainer.addClass(this.css.baseClassIE7);
			}
			/* M.H. 28 May 2012 Fix for bug #107927 */
			if (this.options.showDropDownButton === false) {
				$iconContainer.addClass(this.css.footerRowIconContainerEmpty);
				$textContainer.addClass(this.css.footerRowTextContainerEmpty);
			}
		},
		_virtualHorizontalScroll: function (event, args) {
			/*prevent handling of other grids' events in the case of hierarchical grid */
			/*
			if (args.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			*/
			if (this._isRenderedSummariesRows === false) {
				return;
			}
			/* get the current col index from args, and reinitialize the header cells  */
			var start = args.startColIndex, end = args.endColIndex, i, j, $row, $children, $td,
				sr = this._summariesRows, srLength = sr.length, columnSetting, columnKey,
				/* M.H. 12 Oct. 2011 Fix for bug #91580 */
				cols = this.grid._visibleColumns(), self = this;
			/* M.H. 2 April 2012 Fix for bug #107567 */
			end = start + this.grid._virtualColumnCount;
			if (srLength > 0) {
				$children = sr[ 0 ];
				$.each($children, function (index, element) {
					self.grid._rootContainer()
						.find("#" + self._id("summaries", "methods_dialog", $(element).attr("data-key"))).remove();
				});
			}
			for (i = 0; i < srLength; i++) {
				$row = this.grid._rootContainer().find("#" + sr[ i ].id);
				$children = $row.children();
				$children.html("");

				for (j = start; j <= end; j++) {
					$td = $row.find("td:nth-child(" + (j - start + 1) + ")");
					$td.html("");

					if (cols[ j ] === null || cols[ j ] === undefined) {
						continue;
					}
					this._renderInnerCellMarkup($td, sr[ i ].type, cols[ j ].key);
				}
			}
			/*this._renderFooterIcons(start, end); */
			for (i = start; i < end; i++) {
				if (cols[ i ] !== null && cols[ i ] !== undefined) {
					columnKey = cols[ i ].key;
					columnSetting = this._getColumnSettingByKey(columnKey);
					if (columnKey === undefined || columnKey === null) {
						continue;
					}
					this._renderFooterIconMarkup(sr[ 0 ].type, columnKey);
					this._renderDropDown(columnKey);

					if (columnSetting === null || columnSetting === undefined) {
						continue;
					}
					this._renderFooterColumnInfo(columnKey, this._data[ columnKey ]);
				}
			}
			if (this.grid.hasFixedColumns()) {
				this._syncFixedUnfixedRowHeights();
			}
		},
		/* Helper methods */
		_isFixedFooters: function () {
			return (this.grid.options.fixedFooters === true && this.grid.options.height !== null);
		},
		_isVirtualization: function () {
			var gridOptions = this.grid.options;
			/* M.H. 2 April 2012 Fix for bug #107566 */
			return (gridOptions.virtualization === true ||
					(gridOptions.rowVirtualization === true && gridOptions.height !== null) ||
					(gridOptions.columnVirtualization === true && gridOptions.width !== null));
		},
		_id: function () {
			var i, res = this.grid.element[ 0 ].id, argumentsLength = arguments.length;

			if (argumentsLength === 0) {
				return null;
			}
			for (i = 0; i < argumentsLength; i++) {
				res += "_" + arguments[ i ];
			}
			return res;
		},
		_getColumnFixingInstance: function () {
			return this.grid.element.data("igGridColumnFixing");
		},
		_isAllowedMethodName: function (methodName) {
			var defaultMethodName, isAllowed = false, len = this.defaultSummaryMethods.length, i;

			for (i = 0; i < len; i++) {
				defaultMethodName = this.defaultSummaryMethods[ i ].type;
				if (defaultMethodName === methodName ||
						defaultMethodName === methodName.substr(0, defaultMethodName.length)) {
					isAllowed = true;
					break;
				}
			}
			return isAllowed;
		},
		_footerInit: function (owner, args) {
			/*prevent handling of other grids" events in the case of hierarchical grid */
			if (args.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			var rows, i;
			/* clear all cells from the summary rows that are marked with data-skip=true */
			rows = this.grid.footersTable().find("[ data-role=summaryrow ]");
			/*colgroup = this.grid.footersTable().find("colgroup"); */
			rows.find("[ data-skip=true ]").remove();
			/*colgroup.find("[ data-skip=true ]").remove(); */
			for (i = 0; i < rows.length; i++) {
				/* M.H. 12 Oct. 2011 Fix for bug #91479 */
				/* M.H. 21 Dec. 2011 Fix for bug #98183 */
				this.grid._footerInit($(rows[ i ]), null, true, this.css.footerRowCell);
			}
		},
		_dataSummaries: function () {
			/* return data for summaries */
			var ds = this.grid.dataSource;
			/* M.H. 29 Sept. 2011 Fix for bug #89411 */
			return ds.dataSummaries();
			/*
			if (o.type === "local" && ds.settings.type === "remote") {
				// when summaries type is local but datasource type is remote then get Data from Datasource
				// because in dataSummaries method the data is not populated
				data = ds.data();
			} else {
				data = ds.dataSummaries();
			}

			return data;
			*/
		},
		_injectGrid: function (gridInstance) {
			this.grid = gridInstance;
			/*
			if (this._headerRenderedHandler !== null) {
				this.grid.element.unbind("iggridheaderrendered", this._headerRenderedHandler);
			}
			*/
			if (this.options.type === null) {
				/* infer type */
				this.options.type = this.grid._inferOpType();
			}
			/* M.H. 28 May 2014 Fix for bug #172200: Memory leak when using the features of the grid */
			this._detachEvents();
			this._headerRenderedHandler = $.proxy(this._headerRendered, this);
			this._footerRenderedHandler = $.proxy(this._footerRendered, this);
			this._headerCellRenderedHandler = $.proxy(this._headerCellRendered, this);
			this._headerFooterInitHandler = $.proxy(this._footerInit, this);
			/* M.H. 12 Oct. 2011 Fix for bug #91580 */
			this._columnsCollectionModifiedHandler = $.proxy(this._columnsCollectionModified, this);
			/* M.H. 15 Mar 2013 Fix for bug #136429: Updating Values does not fire updating of summary */
			this.grid.element.bind("iggridheaderrendered", this._headerRenderedHandler);
			this.grid.element.bind("iggridfooterrendered", this._footerRenderedHandler);
			this.grid.element.bind("iggridheadercellrendered", this._headerCellRenderedHandler);
			this.grid.element.bind("iggridheaderextracellsmodified", this._headerFooterInitHandler);
			/* M.H. 12 Oct. 2011 Fix for bug #91580 */
			this.grid.element.bind("iggridcolumnscollectionmodified",
				this._columnsCollectionModifiedHandler);
			this._virtualHorizontalScrollHandler = $.proxy(this._virtualHorizontalScroll, this);
			this.grid.element.bind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			this._initColumnSettings();
			this._setDataSourceColumnSettingsOptions();

			if (this.grid.element.igGridFeatureChooser !== undefined) {
				this.grid.element.igGridFeatureChooser();
			} else {
				throw new Error($.ig.GridSummaries.locale.featureChooserNotReferenced);
			}
		}
	});
	$.extend($.ui.igGridSummaries, { version: "16.1.20161.2145" });
}(jQuery));
