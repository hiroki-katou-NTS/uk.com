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
 *	jquery.ui.mouse.js
 *	jquery.ui.draggable.js
 *	jquery.ui.resizable.js
 *	infragistics.ui.grid.framework.js
 *	infragistics.ui.grid.shared.js
 *	infragistics.ui.grid.featurechooser.js
 *	infragistics.ui.editors.js
 *	infragistics.ui.shared.js
 *	infragistics.dataSource.js
 *	infragistics.templating.js
 *	infragistics.util.js
 */

/*jscs:disable*/
/*global jQuery, toStaticHTML */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {

	/*
		igGridFiltering widget. The widget is pluggable to the element where the grid is instantiated and the actual igGrid object doesn't know about this
		the filtering widget just attaches its functionality to the grid
		it supports filtering dropdowns with various predefined filtering expressions based on the column type
		the widget also implements has full keyboard support with the TAB , SPACE/ENTER keys.
	*/
	$.widget("ui.igGridFiltering", {
		/* property showing whether it should be rendered in feature chooser */
		renderInFeatureChooser: true,
		options: {
			/* type="bool" Enables or disables the filtering case sensitivity. Works only for local filtering.If true, it enables filtering case sensitivity. If false, it disables filtering case sensitivity.
			*/
			caseSensitive: false,
			/*dropDownAnimations: true,*/
			/* type="bool" Enable/disable footer visibility with summary info about the filter.
			When false, the filter summary row (in the footer) will only be visible when paging is enabled (or some other feature that renders a footer).
			When true, the filter summary row will only be visible when a filter is applied i.e. it's not visible by default.
			*/
			filterSummaryAlwaysVisible: true,
			/* type="bool" Render in feature chooser. Feature chooser is dialog which lists all the enabled features (like Sorting, Filtering, Hiding etc.) of igGrid. */
			renderFC: true,
			/* type="string" Summary template that will appear in the bottom left corner of the footer. Has the format '${matches} matching records' */
			filterSummaryTemplate: $.ig.GridFiltering.locale.filterSummaryTemplate,
			/*
				type="linear|none" type of animations for the column filter dropdowns
				linear type="string"
				none type="string"
			*/
			filterDropDownAnimations: "linear",
			/* type="number" animation duration in milliseconds for the filter dropdown animations */
			filterDropDownAnimationDuration: 500,
			/* type="string|number" width of the column filter dropdowns
				string The width in pixels (0px)
				number The width as a number (0)
			*/
			filterDropDownWidth: 0,
			/* type="object" Height of the column filter dropdowns
				string The height of the column filter dropdowns in pixels (0px).
				number The height of the column filter dropdowns as a number (0).
			*/
			filterDropDownHeight: 0,
			/* type="string" URL key name that specifies how the filtering expressions will be encoded for remote requests, e.g. &filter('col') = startsWith. Default is OData */
			filterExprUrlKey: null,
			/* type="true|false" Enable/disable filter icons visibility.
				true type="bool" all predefined filters in the filter dropdowns will have icons rendered in front of the text
				false type="bool"
			*/
			filterDropDownItemIcons: true,
			/* type="array" a list of column settings that specifies custom filtering options on a per column basis */
			columnSettings: [
				{
					/* type="string" Specifies column key. Either key or index must be set in every column setting.*/
					columnKey: null,
					/* type="number" Specifies column index. Either key or index must be set in every column setting.*/
					columnIndex: null,
					/* type="bool" Enables/disables filtering for the column.*/
					allowFiltering: true,
					/* type="empty|notEmpty|null|notNull|equals|doesNotEqual|startsWith|contains|doesNotContain|endsWith|greaterThan|lessThan|greaterThanOrEqualTo|lessThanOrEqualTo|true|false|on|notOn|before|after|today|yesterday|thisMonth|lastMonth|nextMonth|thisYear|nextYear|lastYear" default filtering condition for the column
					empty type="string"
					notEmpty type="string"
					null type="string"
					notNull type="string"
					equals type="string"
					doesNotEqual type="string"
					startsWith type="string"
					contains type="string"
					doesNotContain type="string"
					endsWith type="string"
					greaterThan type="string"
					lessThan type="string"
					greaterThanOrEqualTo type="string"
					lessThanOrEqualTo type="string"
					true type="bool"
					false type="bool"
					on type="string"
					notOn type="string"
					before type="string"
					after type="string"
					today type="string"
					yesterday type="string"
					thisMonth type="string"
					lastMonth type="string"
					nextMonth type="string"
					thisYear type="string"
					nextYear type="string"
					lastYear type="string"
					*/
					condition: null,
					/* type="array" An array of strings that determine which conditions to display for this column
					*/
					conditionList: [],
					/* type="object" Initial filtering expressions - if set they will be applied on initialization
					*/
					defaultExpressions: [],
					/* type="object" An object used to specify custom filtering conditions as objects for this column
					labelText type="string" The label as it will appear in the column's condition dropdown
					expressionText type="string" The text to display in the editor when requireExpr is false
					requireExpr type="bool" If this condition requires the user to input a filtering expression
					filterImgIcon type="string" Class applied to the dropdown item when in simple mode
					filterFunc type="function" The custom comparing filter function. Signature: function (value, expression, dataType, ignoreCase, preciseDateFormat)
					*/
					customConditions: null
				}
			],
			/* type="remote|local" Type of filtering. Delegates all filtering functionality to the $.ig.DataSource.
			remote type="string"
			local type="string"
			 */
			type: null,
			/* type="number" Time in milliseconds for which widget will wait for keystrokes before sending filtering request.*/
			filterDelay: 500,
			/* type="simple|advanced" Default is 'simple' for non-virtualized grids, and 'advanced' when virtualization is enabled
				simple type="string" renders just a filter row
				advanced type="string" allows to configure multiple filters from a dialog - Excel style
			*/
			mode: null,
			/* type="bool" defines whether to show/hide editors in advanced mode. If false, no editors will be rendered in the advanced mode
			*/
			advancedModeEditorsVisible: false,
			/* type="left|right" location of the advanced filtering button when advancedModeEditorsVisible is false (i.e. when the button is rendered in the header)
			left type="string"
			right type="string"
			*/
			advancedModeHeaderButtonLocation: "left",
			/* showEditorsOnClick: false, // when true, editors will not be rendered for every column, and one shared editor will be reused */
			/* type="string|number" default filter dialog width (used for Advanced filtering)
				string The dialog window width in pixels (370px).
				number The dialog window width as a number (370).
			*/
			filterDialogWidth: 430,
			/* type="string|number" default filter dialog height (used for Advanced filtering)
				string The dialog window height in pixels (350px).
				number The dialog window height as a number (350).
			*/
			filterDialogHeight: "",
			/* type="string|number" Width of the filtering condition dropdowns in the advanced filter dialog
				string The filtering condition dropdowns width in pixels (80px).
				number The filtering condition dropdowns width as a number (80).
			*/
			filterDialogFilterDropDownDefaultWidth: 80,
			/* type="string|number" width of the filtering expression input boxes in the advanced filter dialog
				string The filtering expression input boxes width in pixels (80px).
				number The filtering expression input boxes width as a number (80).
			*/
			filterDialogExprInputDefaultWidth: 130,
			/* type="string|number" Width of the column chooser dropdowns in the advanced filter dialog
				string The column chooser dropdowns width in pixels (80px).
				number The column chooser dropdowns width as a number (80).
			*/
			filterDialogColumnDropDownDefaultWidth: null,
			/* showFilterDialogClearAllButton: true, // if false, doesn't render the "Clear ALL" button link in the advanced filtering dialog */
			/* type="bool" Enable/disable filter button visibility. If false,no filter dropdown buttons will be rendered and predefined list of filters will not be rendered for the columns
			*/
			renderFilterButton: true,
			/* type="left|right" the filtering button for filter dropdowns can be rendered either on the left of the filter editor or on the right
			left type="string"
			right type="string"
			*/
			filterButtonLocation: "left",
			/* list of configurable and localized null texts that will be used for the filter editors */
			nullTexts: {
				/* type="string" */
				"startsWith": $.ig.GridFiltering.locale.startsWithNullText,
				/* type="string" */
				"endsWith": $.ig.GridFiltering.locale.endsWithNullText,
				/* type="string" */
				"contains": $.ig.GridFiltering.locale.containsNullText,
				/* type="string" */
				"doesNotContain": $.ig.GridFiltering.locale.doesNotContainNullText,
				/* type="string" */
				"equals": $.ig.GridFiltering.locale.equalsNullText,
				/* type="string" */
				"doesNotEqual": $.ig.GridFiltering.locale.doesNotEqualNullText,
				/* type="string" */
				"greaterThan": $.ig.GridFiltering.locale.greaterThanNullText,
				/* type="string" */
				"lessThan": $.ig.GridFiltering.locale.lessThanNullText,
				/* type="string" */
				"greaterThanOrEqualTo": $.ig.GridFiltering.locale.greaterThanOrEqualToNullText,
				/* type="string" */
				"lessThanOrEqualTo": $.ig.GridFiltering.locale.lessThanOrEqualToNullText,
				/* type="string" */
				"on": $.ig.GridFiltering.locale.onNullText,
				/* type="string" */
				"notOn": $.ig.GridFiltering.locale.notOnNullText,
				/* type="string" */
				"after": $.ig.GridFiltering.locale.afterNullText,
				/* type="string" */
				"before": $.ig.GridFiltering.locale.beforeNullText,
				/* type="string" */
				"thisMonth": $.ig.GridFiltering.locale.thisMonthLabel,
				/* type="string" */
				"lastMonth": $.ig.GridFiltering.locale.lastMonthLabel,
				/* type="string" */
				"nextMonth": $.ig.GridFiltering.locale.nextMonthLabel,
				/* type="string" */
				"thisYear": $.ig.GridFiltering.locale.thisYearLabel,
				/* type="string" */
				"lastYear": $.ig.GridFiltering.locale.lastYearLabel,
				/* type="string" */
				"nextYear": $.ig.GridFiltering.locale.nextYearLabel,
				/* type="string" */
				"empty": $.ig.GridFiltering.locale.emptyNullText,
				/* type="string" */
				"notEmpty": $.ig.GridFiltering.locale.notEmptyNullText,
				/* type="string" */
				"null": $.ig.GridFiltering.locale.nullNullText,
				/* type="string" */
				"notNull": $.ig.GridFiltering.locale.notNullNullText
			},
			/* A list of configurable and localized labels that are used for the predefined filtering conditions in the filter dropdowns. */
			labels: {
				/* type="string" */
				noFilter: $.ig.GridFiltering.locale.noFilterLabel,
				/* type="string" */
				clear: $.ig.GridFiltering.locale.clearLabel,
				/* type="string" */
				startsWith: $.ig.GridFiltering.locale.startsWithLabel,
				/* type="string" */
				endsWith: $.ig.GridFiltering.locale.endsWithLabel,
				/* type="string" */
				contains: $.ig.GridFiltering.locale.containsLabel,
				/* type="string" */
				doesNotContain: $.ig.GridFiltering.locale.doesNotContainLabel,
				/* type="string" */
				equals: $.ig.GridFiltering.locale.equalsLabel,
				/* type="string" */
				doesNotEqual: $.ig.GridFiltering.locale.doesNotEqualLabel,
				/* type="string" */
				greaterThan: $.ig.GridFiltering.locale.greaterThanLabel,
				/* type="string" */
				lessThan: $.ig.GridFiltering.locale.lessThanLabel,
				/* type="string" */
				greaterThanOrEqualTo: $.ig.GridFiltering.locale.greaterThanOrEqualToLabel,
				/* type="string" */
				lessThanOrEqualTo: $.ig.GridFiltering.locale.lessThanOrEqualToLabel,
				/* type="string" */
				trueLabel: $.ig.GridFiltering.locale.trueLabel,
				/* type="string" */
				falseLabel: $.ig.GridFiltering.locale.falseLabel,
				/* type="string" */
				after: $.ig.GridFiltering.locale.afterLabel,
				/* type="string" */
				before: $.ig.GridFiltering.locale.beforeLabel,
				/* type="string" */
				today: $.ig.GridFiltering.locale.todayLabel,
				/* type="string" */
				yesterday: $.ig.GridFiltering.locale.yesterdayLabel,
				/* type="string" */
				thisMonth: $.ig.GridFiltering.locale.thisMonthLabel,
				/* type="string" */
				lastMonth: $.ig.GridFiltering.locale.lastMonthLabel,
				/* type="string" */
				nextMonth: $.ig.GridFiltering.locale.nextMonthLabel,
				/* type="string" */
				thisYear: $.ig.GridFiltering.locale.thisYearLabel,
				/* type="string" */
				lastYear: $.ig.GridFiltering.locale.lastYearLabel,
				/* type="string" */
				nextYear: $.ig.GridFiltering.locale.nextYearLabel,
				/* type="string" */
				on: $.ig.GridFiltering.locale.onLabel,
				/* type="string" */
				notOn: $.ig.GridFiltering.locale.notOnLabel,
				/* type="string" */
				advancedButtonLabel: $.ig.GridFiltering.locale.advancedButtonLabel,
				/* type="string" */
				filterDialogCaptionLabel: $.ig.GridFiltering.locale.filterDialogCaptionLabel,
				/* type="string" */
				filterDialogConditionLabel1: $.ig.GridFiltering.locale.filterDialogConditionLabel1,
				/* type="string" */
				filterDialogConditionLabel2: $.ig.GridFiltering.locale.filterDialogConditionLabel2,
				/* type="string" */
				filterDialogOkLabel: $.ig.GridFiltering.locale.filterDialogOkLabel,
				/* type="string" */
				filterDialogCancelLabel: $.ig.GridFiltering.locale.filterDialogCancelLabel,
				/* type="string" */
				filterDialogAnyLabel: $.ig.GridFiltering.locale.filterDialogAnyLabel,
				/* type="string" */
				filterDialogAllLabel: $.ig.GridFiltering.locale.filterDialogAllLabel,
				/* type="string" */
				filterDialogAddLabel: $.ig.GridFiltering.locale.filterDialogAddLabel,
				/* type="string" */
				filterDialogErrorLabel: $.ig.GridFiltering.locale.filterDialogErrorLabel,
				/* type="string" */
				filterSummaryTitleLabel: $.ig.GridFiltering.locale.filterSummaryTitleLabel,
				/* type="string" */
				filterDialogClearAllLabel: $.ig.GridFiltering.locale.filterDialogClearAllLabel,
				/* type="string" */
				empty: $.ig.GridFiltering.locale.emptyNullText,
				/* type="string" */
				notEmpty: $.ig.GridFiltering.locale.notEmptyNullText,
				/* type="string" */
				nullLabel: $.ig.GridFiltering.locale.nullNullText,
				/* type="string" */
				notNull: $.ig.GridFiltering.locale.notNullNullText,
				/* type="string" */
				"true": $.ig.GridFiltering.locale.trueLabel,
				/* type="string" */
				"false": $.ig.GridFiltering.locale.falseLabel
			},
			/* type="string" custom tooltip template for the filter button, when a filter is applied */
			tooltipTemplate: $.ig.GridFiltering.locale.tooltipTemplate,
			/* type="string" Custom template for add condition area in the filter dialog.
				The default template is "<div><span>${label1}</span><div><select></select></div><span>${label2}</span></div>"
			*/
			filterDialogAddConditionTemplate: null,
			/* type="string" Custom template for options in dropdown in add condition area in the filter dialog.
				The default template is "<option value='${value}'>${text}</option>"
			*/
			filterDialogAddConditionDropDownTemplate: null,
			/* type="string" Custom template for filter dialog.
				Each DOM element which is used for selecting filter conditions/columns/filter expressions has "data-*" attribute.
				E.g.: DOM element used for selecting column has attribute "data-af-col", for selecting filter condition - "data-af-cond", for filter expression- "data-af-expr".
				NOTE: The template is supported only with <tr />.
				The default template is "<tr data-af-row><td><input data-af-col/></td><td><select data-af-cond></select></td><td><input data-af-expr /> </td><td><span data-af-rmv></span></td></tr>"
			*/
			filterDialogFilterTemplate: null,

			// filterDialogFilterColumnsListTemplate: "<option value='${columnKey}'>${columnKey}</option>",
			/* type="string" Custom template for options in condition list in filter dialog
				The default template is "<option value='${condition}'>${text}</option>"
			*/
			filterDialogFilterConditionTemplate: null,
			/* type="string|number" add button width - in the advanced filter dialog
				string The dialog Add button width in pixels (100px).
				number The dialog Add button width as a number (100).
			*/
			filterDialogAddButtonWidth: 100,
			/* type="string|number" Width of the Ok and Cancel buttons in the advanced filtering dialogs
				string The advanced filter dialog Ok and Cancel buttons width in pixels (120px).
				number The advanced filter dialog Ok and Cancel buttons width as a number (120).
			*/
			filterDialogOkCancelButtonWidth: 120,
			/* type="number" Maximum number of filter rows in the advanced filtering dialog. if this number is exceeded, an error message will be rendered */
			filterDialogMaxFilterCount: 5,
			/* type="string" Controls containment behavior.
				owner type="string" The filter dialog will be draggable only in the grid area
				window type="string" The filter dialog will be draggable in the whole window area
			*/
			filterDialogContainment: "owner",
			/* type="bool" Enable/disable empty condition visibility in the filter. If true, shows empty and not empty filtering conditions in the dropdowns
			*/
			showEmptyConditions: false,
			/* type="bool" Enable/disable visibility of null and not null filtering conditions in the dropdowns. If true, shows null and not null filtering conditions in the dropdowns
			*/
			showNullConditions: false,
			/* type="string" Feature chooser text when filter is shown and filter mode is simple*/
			featureChooserText: $.ig.GridFiltering.locale.featureChooserText,
			/* type="string" Feature chooser text when filter is hidden and filter mode is simple*/
			featureChooserTextHide: $.ig.GridFiltering.locale.featureChooserTextHide,
			/* type="string" Feature chooser text when filter mode is advanced*/
			featureChooserTextAdvancedFilter: $.ig.GridFiltering.locale.featureChooserTextAdvancedFilter,
			/* type="bool" Enables / disables filtering persistence between states*/
			persist: true,
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		css: {
			/* Classes applied to the filter row TR in the headers table */
			"filterRow": "ui-iggrid-filterrow ui-widget", // A.T. 15 Feb 2011 - removing ui-widget-content as it messes up hover and focus styles for buttons
			/* Classes applied to every filter cell TH */
			"filterCell": "ui-iggrid-filtercell",
			/* Classes applied to every filter editor element (igEditor) */
			"filterCellEditor": "ui-iggrid-filtereditor",
			/* Classes applied to the UL filter dropdown list */
			"filterDropDownList": "ui-menu ui-widget ui-widget-content ui-iggrid-filterddlist ui-corner-all",
			/* Classes applied to the DIV which wraps the dropdown UL */
			"filterDropDown": "ui-iggrid-filterdd",
			/* Classes applied to the element that holds the text in every filter list item (LI) */
			"filterDropDownListItemTextContainer": "ui-iggrid-filterddlistitemcontainer",
			/* Classes applied to each filter dropdown list item  (LI) */
			"filterDropDownListItem": "ui-iggrid-filterddlistitem",
			/* Class applied to the list item that holds the Advanced button, if options are configured such that editors are shown when mode = "advanced" */
			"filterDropDownListItemAdvanced": "ui-iggrid-filterddlistitemadvanced",
			/* Classes applied to the list item when filtering icons are visible for it */
			"filterDropDownListItemWithIcons": "ui-iggrid-filterddlistitemicons ui-state-default",
			/* Classes applied to the "clear" filter list item */
			"filterDropDownListItemClear": "ui-iggrid-filterddlistitemclear",
			/* Classes applied to the list item when it is hovered */
			"filterDropDownListItemHover": "ui-iggrid-filterddlistitemhover ui-state-hover",
			/* Classes applied to the list item when it is selected */
			"filterDropDownListItemActive": "ui-iggrid-filterddlistitemactive ui-state-active",
			"filterDateCell": "",
			"filterTextCell": "",
			"filterNumberCell": "",
			"filterBoolCell": "",
			/* Classes applied to every filtering dropdown button */
			"filterButton": "ui-iggrid-filterbutton ui-corner-all ui-icon ui-icon-triangle-1-s",
			/* Classes applied to the button when mode = advanced. This also applies to the button when it's rendered in the header (which is the default behavior). */
			"filterButtonAdvanced": "ui-iggrid-filterbutton ui-iggrid-filterbuttonadvanced ui-icon ui-icon-search", // icon similar to Excel's filtering
			/* Classes applied to the advanced filtering button when it is rendered on the right */
			"filterButtonAdvancedRight": "ui-iggrid-filterbuttonright ui-iggrid-filterbuttonadvanced ui-icon ui-icon-search",
			/* Classes applied to the filter button when it is hovered */
			"filterButtonHover": "ui-iggrid-filterbuttonhover ui-state-hover",
			/* Classes applied to the filter button when it is selected */
			"filterButtonActive": "ui-iggrid-filterbuttonactive ui-state-active",
			/* Classes applied to the filter button when it has focus but is not selected. */
			"filterButtonFocus": "ui-iggrid-filterbuttonfocus ui-state-focus",
			/* Classes applied to the filtering button when it is disabled  */
			"filterButtonDisabled": "ui-iggrid-filterbuttondisabled ui-state-disabled",
			/* Classes applied to the filter button when a date filter is defined for the column */
			"filterButtonDate": "ui-iggrid-filterbuttondate",
			/* Classes applied to the filter button when a string filter is applied for the column (default) */
			"filterButtonString": "ui-iggrid-filterbuttonstring",
			/* Classes applied to the filter button when a number filter is applied for the column (default) */
			"filterButtonNumber": "ui-iggrid-filterbuttonnumber",
			/* Classes applied to the filter button when a boolean filter is applied for the column (default) */
			"filterButtonBoolean": "ui-iggrid-filterbuttonbool",
			/* Classes applied on the advanced button when it is hovered */
			"filterButtonAdvancedHover": "ui-iggrid-filterbuttonadvancedhover ui-state-hover",
			/* Classes applied on the advanced button when it is selected */
			"filterButtonAdvancedActive": "ui-iggrid-filterbuttonadvancedactive ui-state-active",
			/* Classes applied on the advanced button when it has focus */
			"filterButtonAdvancedFocus": "ui-iggrid-filterbuttonadvancedfocus ui-state-focus",
			/* Classes applied on the advanced button when it is disabled */
			"filterButtonAdvancedDisabled": "ui-iggrid-filterbuttonadvanceddisabled ui-state-disabled",
			/* Classes applied to every filter dropdown list item's image icon area */
			"filterItemIcon": "ui-iggrid-filtericon",
			/* Classes applied to the item icon's container element */
			"filterItemIconContainer": "ui-iggrid-filtericoncontainer",
			/* Classes applied to the item icon's span when the item holds a startsWith condition */
			"filterItemIconStartsWith": "ui-iggrid-filtericonstartswith",
			/* Classes applied to the item icon's span when the item holds an endsWith condition */
			"filterItemIconEndsWith": "ui-iggrid-filtericonendswith",
			/* Classes applied to the item icon's span when the item holds a contains condition */
			"filterItemIconContains": "ui-iggrid-filtericoncontains",
			/* Classes applied to the item icon's span when the item holds a contains condition */
			"filterItemIconEquals": "ui-iggrid-filtericonequals",
			/* Classes applied to the item icon's span when the item holds a doesNotEqual condition */
			"filterItemIconDoesNotEqual": "ui-iggrid-filtericondoesnotequal",
			/* Classes applied to the item icon's span when the item holds a doesNotContain condition */
			"filterItemIconDoesNotContain": "ui-iggrid-filtericondoesnotcontain",
			/* Classes applied to the item icon's span when the item holds a greaterThan condition */
			"filterItemIconGreaterThan": "ui-iggrid-filtericongreaterthan",
			/* Classes applied to the item icon's span when the item holds a lessThan condition */
			"filterItemIconLessThan": "ui-iggrid-filtericonlessthan",
			/* Classes applied to the item icon's span when the item holds a greaterThanOrEqualTo condition */
			"filterItemIconGreaterThanOrEqualTo": "ui-iggrid-filtericongreaterthanorequalto",
			/* Classes applied to the item icon's span when the item holds a lessThanOrEqualTo condition */
			"filterItemIconLessThanOrEqualTo": "ui-iggrid-filtericonlessthanorequalto",
			/* Classes applied to the item icon's span when the item holds a true condition */
			"filterItemIconTrue": "ui-iggrid-filtericontrue",
			/* Classes applied to the item icon's span when the item holds a false condition */
			"filterItemIconFalse": "ui-iggrid-filtericonfalse",
			/* Classes applied to the item icon's span when the item holds an after condition */
			"filterItemIconAfter": "ui-iggrid-filtericonafter",
			/* Classes applied to the item icon's span when the item holds a before condition */
			"filterItemIconBefore": "ui-iggrid-filtericonbefore",
			/* Classes applied to the item icon's span when the item holds a today condition */
			"filterItemIconToday": "ui-iggrid-filtericontoday",
			/* Classes applied to the item icon's span when the item holds a yesterday condition */
			"filterItemIconYesterday": "ui-iggrid-filtericonyesterday",
			/* Classes applied to the item icon's span when the item holds a thisMonth condition */
			"filterItemIconThisMonth": "ui-iggrid-filtericonthismonth",
			/* Classes applied to the item icon's span when the item holds a lastMonth condition */
			"filterItemIconLastMonth": "ui-iggrid-filtericonlastmonth",
			/* Classes applied to the item icon's span when the item holds a nextMonth condition */
			"filterItemIconNextMonth": "ui-iggrid-filtericonnextmonth",
			/* Classes applied to the item icon's span when the item holds a thisYear condition */
			"filterItemIconThisYear": "ui-iggrid-filtericonthisyear",
			/* Classes applied to the item icon's span when the item holds a lastYear condition */
			"filterItemIconLastYear": "ui-iggrid-filtericonlastyear",
			/* Classes applied to the item icon's span when the item holds a nextYear condition */
			"filterItemIconNextYear": "ui-iggrid-filtericonnextyear",
			/* Classes applied to the item icon's span when the item holds an on condition */
			"filterItemIconOn": "ui-iggrid-filtericonon",
			/* Classes applied to the item icon's span when the item holds a notOn condition */
			"filterItemIconNotOn": "ui-iggrid-filtericonnoton",
			/* Classes applied to the item icon's span when the item holds a clear condition */
			"filterItemIconClear": "ui-iggrid-filtericonclear",
			/* Classes applied to the filtering block area, when the advanced filter dialog is opened and the area behind it is grayed out (that's the block area) */
			"blockArea": "ui-widget-overlay ui-iggrid-blockarea",
			/* Classes applied to the filter dialog element */
			"filterDialog": "ui-dialog ui-draggable ui-resizable ui-iggrid-dialog ui-widget ui-widget-content ui-corner-all",
			/* Classes applied to the filter dialog header caption area */
			"filterDialogHeaderCaption": "ui-dialog-titlebar ui-iggrid-filterdialogcaption ui-widget-header ui-corner-all ui-helper-reset ui-helper-clearfix",
			/* Class applied to the filter dialog header caption title */
			"filterDialogHeaderCaptionTitle": "ui-dialog-title",
			/* Classes applied to the filter dialog add condition area */
			"filterDialogAddCondition": "ui-iggrid-filterdialogaddcondition",
			/* Classes applied to the filter dialog add condition SELECT dropdown. */
			"filterDialogAddConditionDropDown": "ui-iggrid-filterdialogaddconditionlist",
			/* Classes applied to the filter dialog add button */
			"filterDialogAddButton": "ui-iggrid-filterdialogaddbuttoncontainer ui-helper-reset",
			/* Classes applied to the filter dialog OK and Cancel buttons. */
			"filterDialogOkCancelButton": "ui-dialog-buttonpane ui-widget-content ui-helper-clearfix ui-iggrid-filterdialogokcancelbuttoncontainer",
			/* Classes applied to the filter dialog filters table */
			"filterDialogFiltersTable": "ui-iggrid-filtertable ui-helper-reset",
			/* Classes applied to the "X" button used to remove filters from the filters table */
			"filterDialogFilterRemoveButton": "ui-icon ui-icon-closethick",
			/* Classes applied to the filter dialog "Clear All" button. */
			"filterDialogClearAllButton": "ui-iggrid-filterdialogclearall",
			/*Classes applied to the feature chooser icon when filter shows advanced dialog*/
			"featureChooserModalDialogIcon": "ui-icon ui-iggrid-icon-advanced-filter"
		},
		events: {
			/* cancel="true" Event fired before a filtering operation is executed (remote request or local).
			Return false in order to cancel filtering operation.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.columnIndex to get column index. Applicable only when filtering mode is "simple".
			Use ui.columnKey to get column key. Applicable only when filtering mode is "simple".
			Use ui.newExpressions to get filtering expressions. Filtering expressions could be changed in this event handler and after that data binding is applied. In this way the user could control filtering more easily before applying data-binding.
			*/
			dataFiltering: "dataFiltering",
			/* Event fired after the filtering has been executed and results are rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.columnIndex to get column index. Applicable only when filtering mode is "simple".
			Use ui.columnKey to get column key.Applicable only when filtering mode is "simple".
			Use ui.expressions to get filtered expressions.
			*/
			dataFiltered: "dataFiltered",
			/* cancel="true" Event fired before the filter dropdown is opened for a specific column.
			Return false in order to cancel dropdown opening.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dropDown to get reference to dropdown DOM element.
			*/
			dropDownOpening: "dropDownOpening",
			/* Event fired after the filter dropdown is opened for a specific column.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dropDown to get reference to dropdown DOM element.
			*/
			dropDownOpened: "dropDownOpened",
			/* cancel="true" Event fired before the filter dropdown starts closing.
			Return false in order to cancel dropdown closing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dropDown to get reference to dropdown DOM element.
			*/
			dropDownClosing: "dropDownClosing",
			/* Event fired after a filter column dropdown is completely closed.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dropDown to get reference to dropdown DOM element.
			*/
			dropDownClosed: "dropDownClosed",
			/* cancel="true" Event fired before the advanced filtering dialog is opened.
			Return false in order to cancel filter dialog opening.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dialog to get reference to filtering dialog DOM element.
			*/
			filterDialogOpening: "filterDialogOpening",
			/* Event fired after the advanced filter dialog is already opened.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dialog to get reference to filtering dialog DOM element.
			*/
			filterDialogOpened: "filterDialogOpened",
			/* Event fired every time the advanced filter dialog changes its position.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dialog to get reference to filtering dialog DOM element.
			Use ui.originalPosition to get the original position of the groupby dialog div as { top, left } object, relative to the page.
			Use ui.position to get the current position of the groupby dialog div as { top, left } object, relative to the page.
			*/
			filterDialogMoving: "filterDialogMoving",
			/* cancel="true" Event fired before a filter row is added to the advanced filter dialog.
			Return false in order to cancel filter adding to the advanced filtering dialog.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.filtersTableBody to get reference to filters table body DOM element.
			*/
			filterDialogFilterAdding: "filterDialogFilterAdding",
			/* Event fired after a filter row is added to the advanced filter dialog.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.filter to get reference to filters table row DOM element.
			*/
			filterDialogFilterAdded: "filterDialogFilterAdded",
			/* cancel="true" Event fired before the advanced filter dialog is closed.
			Return false in order to cancel filtering dialog closing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			*/
			filterDialogClosing: "filterDialogClosing",
			/* Event fired after the advanced filter dialog has been closed.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			*/
			filterDialogClosed: "filterDialogClosed",
			/* cancel="true" Event fired before the contents of the advanced filter dialog are rendered.
			Return false in order to cancel filtering dialog rendering.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dialogElement to get reference to filtering dialog DOM element.
			*/
			filterDialogContentsRendering: "filterDialogContentsRendering",
			/* Event fired after the contents of the advanced filter dialog are rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dialogElement to get reference to filtering dialog DOM element.
			*/
			filterDialogContentsRendered: "filterDialogContentsRendered",
			/* Event fired when the OK button in the advanced filter dialog is pressed.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridFiltering.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dialog to get reference to filtering dialog DOM element.	*/
			filterDialogFiltering: "filterDialogFiltering"
		},
		/*jscs:enable*/
		_createWidget: function () {
			/* !Strip dummy objects from options, because they are defined for documentation purposes only! */
			this.options.columnSettings = [];
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		_create: function () {
			this._editors = [];
			if (!$.fn.fadeToggle) {
				$.fn.fadeToggle = $.fn.toggle;
			}
			/* feature chooser data */
			this._fcData = {};
			/* flag for feature chooser whether summary columns are analyzed */
			this._isInitFC = false;
			/*	Object of custom developer defined conditions */
			this._dsTransformedCustomConditions = null;
			if (this.options.filterDropDownAnimations === "none") {
				this.options.filterDropDownAnimationDuration = 1;
				this.options.filterDropDownAnimations = "linear";
			}
		},
		_setOption: function (key, value) {
			/* handle new settings and update options hash */
			$.Widget.prototype._setOption.apply(this, arguments);
			/* options that are supported: filterDropDownWidth, filterDropDownHeight, filterDialogWidth, filterDialogHeight */
			/* start by throwing an error for the options that aren't supported: */
			if (key === "mode" || key === "renderFilterButton" ||
				key === "filterButtonLocation" || key === "type") {
				throw new Error($.ig.Grid.locale.optionChangeNotSupported.replace("{optionName}", key));
			}
			/* handle filterDropDownWidth */
			if (key === "filterDropDownWidth") {
				this.grid._rootContainer().find("div ul").parent().css("width", value);
				/* handle filterDropDownHeight */
			} else if (key === "filterDropDownHeight") {
				this.grid._rootContainer().find("div ul").parent().css("height", value);
				/* handle filterDialogWidth */
			} else if (key === "filterDialogWidth") {
				this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog")
					.css("width", value);
				/* handle filterDialogHeight */
			} else if (key === "filterDialogHeight") {
				this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog")
					.css("height", value);
				/* handle columnSettings */
			} else if (key === "columnSettings") {
				/* M.H. 1 Apr 2013 Fix for bug #130581: Rebinding igGrid after setting igGridFiltering.columnSetting at runtime causes an error */
				this._initDefaultSettings();
			}
		},
		destroy: function () {
			/* destroys the filtering widget - remove fitler row, unbinds events, returns the grid to its previous state. */
			var i, fc, cols = this.grid.options.columns,
				gridId = this.grid.id(),
				block = this.grid._rootContainer().find("#" + gridId + "_container_block"),
				dialog = this.grid._rootContainer().find("#" + gridId + "_container_dialog"),
				gridContainer = this.grid._rootContainer();
			/* remove the filter row, and it will take care of unbinding all events */
			this.grid._rootContainer().find("#" + gridContainer[ 0 ].id + " .ui-iggrid-filterrow").remove();
			/* also remove all filtering dropdowns */
			this.grid._rootContainer().find("#" + gridContainer[ 0 ].id + " .ui-iggrid-filterdd").remove();
			/* remove all filtering dropdowns */
			for (i = 0; i < cols.length; i++) {
				this.grid._rootContainer()
					.find("#" + gridId + "_dd_" + cols[ i ].key)
					.remove();
				this.grid._rootContainer()
					.find("#" + gridId + "_dd_" + cols[ i ].key + "_button")
					.remove();
			}
			/* and the advanced filter dialog and block area, if present (if Advanced filtering is enabled) */
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			block.remove();
			dialog.remove();
			gridContainer.unbind("keydown.focusChecker");
			this.grid.element.unbind("iggridheadercellrendered", this._headerCellRenderedHandler);
			this.grid.element.unbind("iggridheaderrendered", this._headerRenderedHandler);
			this.grid.element.unbind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			this.grid.element.unbind("iggridresizingcolumnresized", this._columnResizedHandler);
			this.grid.element.unbind("iggridheaderextracellsmodified", this._headerInitHandler);
			this.grid.element.unbind("iggrid_columnsmoved", this._columnsAlteredHandler);
			this.grid.element.unbind("iggriduidirty", this._onUIDirtyHandler);
			delete this._headerCellRenderedHandler;
			delete this._headerRenderedHandler;
			delete this._virtualHorizontalScrollHandler;
			delete this._columnResizedHandler;
			delete this._headerInitHandler;
			delete this._onUIDirtyHandler;
			delete this._columnsAlteredHandler;
			this._editors = null;
			this._fcData = null;
			this.options.columnSettings = null;
			this.options = null;
			if (this._loadingIndicator) {
				delete this._loadingIndicator;
			}
			if (this._filterInternal) {
				delete this._filterInternal;
			}
			if (this._toggleFilterRowHandler) {
				delete this._toggleFilterRowHandler;
			}
			/* M.H. 4 Oct 2013 Fix for bug #151434: When filtering is advanced and advancedModeEditorsVisible = true the button for advanced filtering dialog is missing from the feature chooser. */
			if (this._openFilterDialogFromFCHandler) {
				delete this._openFilterDialogFromFCHandler;
			}
			fc = this.grid.element.data("igGridFeatureChooser");
			if (fc && this.renderInFeatureChooser) {
				fc._removeFeature("Filtering");
				fc._removeFeature("AdvancedFiltering");
			}
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		_initLoadingIndicator: function () {
			/* attach loading indicator widget */
			this._loadingIndicator = this.grid._rootContainer().length > 0 ?
						this.grid._rootContainer().igLoading().data("igLoading") :
						this.grid.element.igLoading().data("igLoading").indicator();
		},
		/* if uiDirty event is fired, it means that all subscribed features will need to reset their state. For example page size change triggers onUIDirty */
		_onUIDirty: function (e, args) {
			/* M.H. 19 Aug 2014 Fix for bug #166879: Changing page size with remote paging does not persist remote sorting and filtering */
			if (this.options.persist && this.options.type === "remote") {
				return;
			}
			var i, cols = this.grid.options.columns, condition;
			/* A.T. 23 Aug 2011 - add additional checks so that events don't propagate across hierarchical grids */
			if (args.owner === this || args.owner.element[ 0 ].id !== this.element[ 0 ].id) {
				return;
			}
			/* reset all filtering states & UI */
			this._filterDialogClearAll();
			if (this._editors !== null && this._editors !== undefined) {
				for (i = 0; i < this._editors.length; i++) {
					if (this.options.columnSettings[ i ].allowFiltering !== false) {
						this._editors[ i ].value(null);
						/* M.H. 2 Apr 2013 Fix for bug #138427: The condition set in the columnSettings of filtering feature is returned back to default after dataBind. */
						condition = null;
						if (this.options.columnSettings[ i ].columnKey) {
							condition = this.options.nullTexts[
								this._findColumnSetting(this.options.columnSettings[ i ].columnKey)
									.condition ];//this.options.columnSettings[i].condition;
						}
						if (condition === null || condition === undefined) {
							condition = this.options.nullTexts[
									this._getDefaultCondition(this._getColType(cols[ i ].key)) ];
						}
						this._editors[ i ].element.igGridFilterEditor(
							"option",
							"placeHolder",
							condition
						);
					}
				}
			}
			/* clear selected items in the dropdowns: */
			for (i = 0; i < cols.length; i++) {
				this.grid._rootContainer()
					.find("#" + this.grid.id() + "_dd_" + cols[ i ].key)
					.find("li")
					.removeClass("ui-iggrid-filterddlistitemactive ui-state-active");
			}
			/* clear tooltips */
			$(".ui-iggrid-filterbutton", this.grid._rootContainer())
				.parent().attr("title",
						this.options.tooltipTemplate.replace("${condition}",
						this.options.labels.noFilter));
			/* last but not least clear the filter expressions */
			this.grid.dataSource.settings.filtering.expressions = [];
		},
		getFilteringMatchesCount: function () {
			/* returns the count of data records that match filtering conditions
			returnType="number" count of filtered records
			*/
			var o = this.options, ds = this.grid.dataSource, matches;
			if (o.type === "local" || (o.type === "remote" && ds.hasTotalRecordsCount() === false)) {
				if (ds._filter) {
					/*matches = this.grid.dataSource.dataView().length; // this should be used when applyToAllData = false */
					matches = ds._filteredData.length;
				} else {
					matches = ds._data.length;
				}
				/* we need that when, say, both paging and filtering are enabled, and both are remote */
			} else {
				matches = ds.totalRecordsCount();
			}
			return matches;
		},
		_dataRendered: function () {
			var w, fCntnr, defExpr, expr, matches = 0, grid = this.grid,
				summary = grid.container().find(".ui-iggrid-footer .ui-iggrid-results").eq(0),
				footer = summary.parent(), shouldInitHeights = false, isInitiallyFiltered = false,
				i, isFiltering = false, exprs, reInitHeights = false;
			if (!this._loadingIndicator) {
				this._initLoadingIndicator();
			}
			/* M.H. 6 Feb 2014 Fix for bug #160331: Hidden column is visible until the data is being fetched */
			if (this._hiddenCells && this._hiddenCells.length > 0) {
				for (i = 0; i < this._hiddenCells.length; i++) {
					this._hiddenCells[ i ].show();
				}
				this._hiddenCells = undefined;
			}
			/* M.H. 19 Jul 2013 Fix for bug #147233: cannot set default filtering expressions in ig.DataSource (they aren't taken into account , similar to sorting default expressions) */
			if (this._defaultExpressions && this._defaultExpressions.length > 0) {
				defExpr = this._defaultExpressions;
				this._defaultExpressions = [];
				this._updateFiltersUI(defExpr, false);
				isInitiallyFiltered = true;
				this._isFilteringRequest = true;
			}
			if (this._shouldFireDataFiltered) {
				exprs = this._getDataColumnFilteringExpressions(grid.dataSource.settings.filtering.expressions);
				this._shouldFireDataFiltered = false;
				this._trigger(this.events.dataFiltered, null, {
					columnKey: this._curColKey,
					columnIndex: this._curColIndex,
					owner: this, expressions: exprs
				});
			}
			/* M.H. 17 May 2013 Fix for bug #142492: When you filter a column and then clear the filter the height of the grid decreases. */
			if (!summary.is(":visible")) {
				shouldInitHeights = true;
			}
			/* change filter summary */
			if (this._isFilteringRequest === true) {
				if (this.options.filterSummaryAlwaysVisible === true && summary.length === 0) {
					/* we need to render a footer */
					footer = $("<div></div>")
								.appendTo(grid.container())
						.addClass("ui-widget ui-helper-clearfix ui-corner-bottom ui-widget-header ui-iggrid-footer");
					if (grid.hasFixedColumns()) {
						footer.css("clear", "both");
						/* M.H. 26 Feb 2016 Fix for bug 214934: When there's a fixed column and filtering is applied the grid height becomes smaller */
						/* Strange issue - height of the footer becomes over 14K pixels - ONLY in Chrome - probably caused by CSS class "ui-helper-clearfix" applied on footer */
						reInitHeights = ($.ig.util.isChrome && footer.height() > grid._rootContainer().height());
					}
					summary = $("<span></span>").appendTo(footer).addClass("ui-iggrid-results");
					/* M.H. 19 Jul 2013 Fix for bug #147233: cannot set default filtering expressions in ig.DataSource (they aren't taken into account , similar to sorting default expressions) */
					if (isInitiallyFiltered && grid.element.data("igGridPaging")) {
						footer.attr("id", grid.element[ 0 ].id + "_pager").addClass("ui-iggrid-pager");
						summary
							.attr("id", grid.element[ 0 ].id + "_pager_label")
							.addClass("ui-iggrid-pagerrecordslabel");
					}
					shouldInitHeights = true;
				}
				matches = this.getFilteringMatchesCount();
				/* M.H. 30 Jan 2014 Fix for bug #162407: Footer area remains after filter condition is removed */
				if (!summary.parent().is(":visible")) {
					summary.parent().show();
					shouldInitHeights = true;
				}
				summary
					.text(this.options.filterSummaryTemplate.replace("${matches}", matches))
					.attr("title", this.options.labels.filterSummaryTitleLabel);
				summary.show();
				isFiltering = true;
				this._isFilteringRequest = false;
			} else if (this._isFilteringRequest === false) {
				/* M.H. 30 Jan 2014 Fix for bug #162407: Footer area remains after filter condition is removed */
				/* if we have sorting + filtering in case we filter and then apply sorting twice we should check whether there are filtering expressions - and if there are we shouldn't hide footer/ footer info */
				expr = grid.dataSource.settings.filtering.expressions || [];
				if (summary.data("hideflag") !== false && expr.length === 0) {
					isFiltering = true;
					summary.hide();
					/* M.H. 30 Jan 2014 Fix for bug #162407: Footer area remains after filter condition is removed */
					/* we should be sure that we want to hide footer only if there aren't any other children(for instance paging is enabled and pager is rendered as a child) */
					/* for instance if there is paging + filtering + sorting - when we filter column after that clear filtering, only label should be hidden. When sort by column summary.data('hideflag') will be true but we shouldn't hide the whole footer if paging is rendered */
					if (summary.parent().children().length === 1) {
						summary.parent().hide();
					}
				} else {
					summary.data("hideflag", true);
				}
				/* M.H. 17 May 2013 Fix for bug #142492: When you filter a column and then clear the filter the height of the grid decreases. */
				shouldInitHeights = true;
			}
			this._loadingIndicator.hide();
			if (shouldInitHeights) {
				if (grid.hasFixedColumns()) {
					fCntnr = grid.fixedContainer()[ 0 ];
					if (fCntnr) {
						w = fCntnr.style.width;
						fCntnr.style.width = "";
					}
				}
				grid._initializeHeights();
				if (w) {
					fCntnr.style.width = w;
				}
				/* M.H. 26 Feb 2016 Fix for bug 214934: When there's a fixed column and filtering is applied the grid height becomes smaller */
				/* Dirty hack - this issue could be reproduced ONLY in Chrome - footer is too huge and height of the containers is not properly calculated - it should be re-calculated */
				if (reInitHeights) {
					grid._initializeHeights();
				}
				/* M.H. 14 May 2013 Fix for bug #135074: With continuous virtualization rows expand to fill the height of the grid. */
				/* M.H. 1 Sep 2013 Fix for bug #153659: When fixed virtualization rows expand to fill the height of the grid after the grid is filtered */
				/* M.H. 29 Jul 2014 Fix for bug #176755: When there a certain number of records in the grid with Virtualization enabled the rows display at an incorrect height. */
				if (isFiltering && (grid.options.virtualization || grid.options.rowVirtualization)) {
					grid._updateVirtualScrollContainer();
					/* we should reRender virtual records */
					grid._virtualDom = null;
					grid._renderVirtualRecords();
				}
			}
			this._setEditorsWidth();
		},
		_initFC: function () {
			/* check whether it should be rendered in Feature Chooser */
			var columnKey, i,
				fc = this.grid.element.data("igGridFeatureChooser"),
				o = this.options,
				cs = this.options.columnSettings,
				showAdvancedButton = (o.mode === "advanced" && o.advancedModeEditorsVisible === true),
				isAdvanced = (o.mode !== "simple" && o.advancedModeEditorsVisible === false);

			this._isInitFC = true;
			/* if renderFC is disabled or column key is not found return */
			if (o.renderFC === false) {
				return;
			}
			/* instantiate igGridFeatureChooser = if it is defined */
			if (fc !== null && fc !== undefined && this.renderInFeatureChooser) {
				/* M.H. 29 Oct 2012 Fix for bug #120642 */
				if (this._toggleFilterRowHandler === null || this._toggleFilterRowHandler === undefined) {
					this._toggleFilterRowHandler = $.proxy(this.toggleFilterRowByFeatureChooser, this);
				}
				/* M.H. 4 Oct 2013 Fix for bug #151434: When filtering is advanced and advancedModeEditorsVisible = true the button for advanced filtering dialog is missing from the feature chooser. */
				if (this._openFilterDialogFromFCHandler === null ||
						this._openFilterDialogFromFCHandler === undefined) {
					this._openFilterDialogFromFCHandler = $.proxy(this._openFilterDialogFromFC, this);
				}
				for (i = 0; i < cs.length; i++) {
					/* check for the specified column whether allowFiltering is enabled */
					columnKey = cs[ i ].columnKey;
					if (columnKey &&
							cs[ i ].allowFiltering === true &&
							fc._shouldRenderInFeatureChooser(columnKey) === true) {
						this._filterRowShown = true;
						this._fcData[ columnKey ] = true;
						/* M.H. 4 Oct 2013 Fix for bug #151434: When filtering is advanced and advancedModeEditorsVisible = true the button for advanced filtering dialog is missing from the feature chooser. */
						if (isAdvanced || showAdvancedButton) {
							fc._renderInFeatureChooser(columnKey, {
								name: "AdvancedFiltering",
								/* M.H. 13 Oct. 2011 Fix for bug #91007 */
								text: o.featureChooserTextAdvancedFilter,
								iconClass: this.css.featureChooserModalDialogIcon,
								isSelected: true,
								/* M.H. 29 Oct 2012 Fix for bug #120642 */
								method: this._openFilterDialogFromFCHandler,
								updateOnClickAll: true,
								groupName: "modaldialog",
								groupOrder: 3,
								order: 3
							});
						}
						if (!isAdvanced) {
							fc._renderInFeatureChooser(columnKey, {
								name: "Filtering",
								/* M.H. 13 Oct. 2011 Fix for bug #91007 */
								text: o.featureChooserText,
								textHide: o.featureChooserTextHide,
								iconClass: this.css.featureChooserModalDialogIcon,
								isSelected: true,
								/* M.H. 29 Oct 2012 Fix for bug #120642 */
								method: this._toggleFilterRowHandler,
								updateOnClickAll: true,
								groupName: "toggle",
								groupOrder: 1,
								order: 2,
								type: "toggle",
								state: "hide"
							});
						}
					}
				}
			}
		},
		_columnResized: function () {
			if (this._filterRowShown !== false) {
				this._setEditorsWidth();
			}
		},
		_setEditorsWidth: function () {
			if (this.options.mode !== "simple" && this.options.advancedModeEditorsVisible !== true) {
				return;
			}

			var cs = this.options.columnSettings,
				cols = this.grid._visibleColumns(),
				cells = this.grid.headersTable()
					.find("thead tr[data-role=filterrow]")
					.first()
					.find("td")
					.not("[data-skip=true]"),
				cellWidth,
				i,
				j,
				skipColumn;

			for (i = 0; i < cols.length; i++) {
				/* continue if filtering is disabled for that particular column */
				for (j = 0; j < cs.length; j++) {
					if (cs[ j ].columnKey === cols[ i ].key && cs[ j ].allowFiltering === false) {
						skipColumn = true;
						break;
					}
				}
				if (skipColumn) {
					skipColumn = false;
					continue;
				}

				if (!$.ig.util.isOpera &&
					((this.grid.options.height && this.grid.options.fixedHeaders === true) ||
						$.ig.util.isWebKit)) {
					/*A.T. 10 April 2011 - changing from outerWidth to innerWidth, so that the borders width is accounted for, and excluded from the calculations */
					/* if inner height is used, there is the border space remaining. if outer height is used, the editors /dropdowns are a bit cut on the right. */
					cellWidth = cells.eq(i).innerWidth();
				} else {
					cellWidth = cells.eq(i).width();
				}
				cellWidth -= cells.eq(i).data("buttonWidth");
				cells.eq(i)
					.children("span[data-filter-editor]").first()
					.igGridFilterEditor("option", "width", cellWidth);
			}
		},
		_headerRendered: function (sender) {
			// render the filter row
			var thead, filterrow, i = 0, cell, w, button, cols = this.grid.options.columns, thCell,
				id, j, cs = this.options.columnSettings, skipColumn = false, buttonCss,
				showAdvancedInHeader = false, indicatorContainer,
				cancelFunc, ci, buttonWidth, isMRL = !!this.grid._rlp,
				isSimpleMode = (this.options.mode === "simple" ||
								this.options.advancedModeEditorsVisible === true),
				/* M.H. 17 Oct 2013 Fix for bug #155162: When it is used simple filtering and jquery version 1.9.1+ js error is thrown */
				isToCheck = (this.grid._initialHiddenColumns &&
							this.grid._initialHiddenColumns.length > 0), cond;
			/*A.T. if the event is fired from another grid, return ! (hierarchical grid scenarios where events are bubbling */
			if (sender.target.id !== this.grid.element[ 0 ].id) {
				return;
			}
			/* 1. find the correct THEAD */
			thead = this.grid.container().find("thead");

			/*A.T. take into account hierarchy */
			/*
			if (this._hc === undefined) {
				this._hc = this.grid._rootContainer().find('.ui-iggrid-expandheadercell').length > 0;
			}
			*/
			/* add the filter row */
			/*V1 */
			if (this.options.mode === "simple" || this.options.advancedModeEditorsVisible === true) {
				filterrow = $("<tr></tr>")
							.appendTo(thead)
							.addClass(this.css.filterRow)
							.attr("data-role", "filterrow");
				/*if (this._hc === true) {
					$('<td></td>').appendTo(filterrow).css('border-width', 0);
				} */
				this.grid._headerInit(filterrow);
			} else {
				/*V2: A.T. 27 Jan 2011 */
				filterrow = thead.find("tr:first");
			}
			cancelFunc = function (e) { e.preventDefault(); e.stopPropagation(); };
			/* render the filter row cells */
			/*
			if (this._hc === true) {
				ci = i + 1;
			} else {
				ci = i;
			}
			*/
			/* M.H. 15 Oct 2013 Fix for bug #154835: When the grid has 100% width,  filtering is enabled and a column is hidden there is a white space on the right of the grid. */
			if (isToCheck) {
				this._hiddenCells = [];
			}
			/* find all cells marked with a data-skip attribute */
			ci = i + filterrow.find("[data-skip=true]").length;
			for (i = 0; i < cols.length; i++, ci++) {
				cond = this._findColumnSetting(cols[ i ].key).condition;
				this._findColumnSetting(cols[ i ].key).condition =
					cond ? cond : this._getDefaultCondition(this._getColType(cols[ i ].key));
				/* A.T. V1 */
				if (isSimpleMode === true) {
					cell = $("<td></td>").appendTo(filterrow).addClass(this.css.filterCell);
					/* M.H. 15 Oct 2013 Fix for bug #154835: When the grid has 100% width,  filtering is enabled and a column is hidden there is a white space on the right of the grid. */
					/* this bug is caused because td for filtering cell is rendered and the IE8 layout engine takes into account its width. That's why we should hide this cell */
					/* NOTE: later we should show this cell because if feature hiding is enabled and we show filtered cell then editors should be shown too */
					/* M.H. 6 Feb 2014 Fix for bug #160331: Hidden column is visible until the data is being fetched */
					if (isToCheck && this._hiddenCells.length < this.grid._initialHiddenColumns.length) {
						for (j = this.grid._initialHiddenColumns.length - 1; j >= 0; j--) {
							if (this.grid._initialHiddenColumns[ j ].key === cols[ i ].key) {
								cell.hide();
								this._hiddenCells.push(cell);
								break;
						}
					}
				}
					/* A.T. V2 27 Jan 2011 */
					cell.attr("aria-describedBy", this.grid.id() + "_" + cols[ i ].key);
				} else {
					/* M.H. 19 July 2012 Fix for bug #117306 */
					if (this.grid._isMultiColumnGrid) {
						cell  = $(this.grid._headerCells[ ci ]);
					} else {
						cell = isMRL ?	this.grid.container().find("#" + this.grid.id() + "_" + cols[ i ].key) :// in case of MultiRowLayout
										$(filterrow[ 0 ].cells[ ci ]);
				}
					/* Sorting and other features which modify the header */
					if (cell.children().first().is("a")) {
						cell.children().first().css("display", "inline");
						cell.css("cursor", "pointer");
				}
					/*cell.data("colName", cols[ i ].headerText); */
					cell.data("colName", cols[ i ].key);
					cell.attr("aria-describedBy", this.grid.id() + "_" + cols[ i ].key);
			}

				if (this.grid._isMultiColumnGrid !== true || isSimpleMode === true) {
					if (i === cols.length - 1 &&
						this.grid.options.height &&
						parseInt(this.grid.options.height, 10) > 0) {
						/* set last col width explicitly */
						cell.css("width", parseInt(cols[ i ].width, 10) + this.grid._scrollbarWidth());
					} else {
						cell.css("width", cols[ i ].width);
				}
			}
				/* continue if filtering is disabled for that particular column */
				for (j = 0; j < cs.length; j++) {
					if (cs[ j ].columnKey === cols[ i ].key && cs[ j ].allowFiltering === false) {
						if (this.options.advancedModeEditorsVisible === true || this.options.mode !== "advanced") {
							this._editors.push({});
					}
						skipColumn = true;
						break;
				}
			}
				if (skipColumn) {
					skipColumn = false;
					continue;
			}
				thCell = thead.children().first().find("th:nth-child(" + (ci + 1) + ")");
				/* M.H. 14 Mar 2013 Fix for bug #135924: Column headers are misalign in Safari when using filtering */
				/* M.H. 3 Apr 2013 Fix for bug #138576: DataSourceUrl with Filtering and RowSelectors throws error in Google Chrome */
				/* M.H. 8 Nov 2013 Fix for bug #157211: Column header misalignment when there are initially hidden columns under Android stock browser */
				if ($.ig.util.isWebKit && thCell.length > 0) {
					w = thCell[ 0 ].offsetWidth;
				} else if ((this.grid.options.height && this.grid.options.fixedHeaders === true) ||
						 $.ig.util.isWebKit) {
					/* render editor in cell */
					/* L.A. 24 April 2012 - Fixed bug #83009 Last column's cell in the filter row doesn't look correct in Opera */
					/* Removed opera condition. It seems that the latest Opera version is rendering the content correctly. */
					/*A.T. 10 April 2011 - changing from outerWidth to innerWidth, so that the borders width is accounted for, and excluded from the calculations */
					/* if inner height is used, there is the border space remaining. if outer height is used, the editors /dropdowns are a bit cut on the right. */
					w = thCell.innerWidth();
				} else {
					w = thCell.width();
			}
				/* M.H. 12 Sep 2013 Fix for bug #151972: Setting renderFilterButton to false does not hide the filter button. */
				showAdvancedInHeader = (this.options.advancedModeEditorsVisible === false &&
						this.options.mode === "advanced" &&
						this.options.renderFilterButton === true);
				if ((this.options.renderFilterButton === true && !showAdvancedInHeader) ||
						(showAdvancedInHeader && this._renderFCFor(cols[ i ].key) === false)) {
					id = this.grid.element.attr("id") + "_dd_" + cols[ i ].key;
					/* render the dropdown associated with that button */
					if (this.options.advancedModeEditorsVisible === true || this.options.mode !== "advanced") {
						this._renderDropDown(cols[ i ].dataType, id, cols[ i ].key);
				}
					buttonCss = this.options.mode === "advanced" ?
									this.css.filterButtonAdvanced :
									this.css.filterButton;
					if (showAdvancedInHeader && this.options.advancedModeHeaderButtonLocation === "right") {
						buttonCss = this.css.filterButtonAdvancedRight;
						button = $("<span></span>").appendTo(cell).addClass(buttonCss).data("colIndex", i);
					} else {
						button = $("<span></span>").prependTo(cell).addClass(buttonCss).data("colIndex", i);
				}
					/* M.H. 1 Nov 2013 Fix for bug #156411: Advanced filtering icon spans the header of the grid on two rows */
					if (this.options.mode === "advanced") {
						if (!cell.hasClass(this.grid.css.headerCellFeatureEnabledClass)) {
							cell.addClass(this.grid.css.headerCellFeatureEnabledClass);
					}
				}
					if (this.options.mode === "simple" ||
							(this.options.mode === "advanced" && this.options.advancedModeEditorsVisible === true)) {
						button.wrap("<a id=\"" + id + "_button\" href=\"#\" title=\"" +
							this.options.tooltipTemplate.replace("${condition}", this.options.labels.noFilter) +
								"\" ></a>");
					} else {
						button.wrap("<a id=\"" + id + "_button\" href=\"#\" title=\"" +
							this.options.tooltipTemplate.replace("${condition}", this.options.labels.noFilter) +
							"\" style=\"display:inline;\"></a>");
				}
					indicatorContainer = cell.find(".ui-iggrid-indicatorcontainer");

					if (indicatorContainer.length === 0) {
						indicatorContainer = $("<div></div>").appendTo(cell).addClass("ui-iggrid-indicatorcontainer");
				}

					indicatorContainer.append(button.parent());
					w = parseInt(w, 10);
					if (this.grid.options.height === null && $.ig.util.isFF) {
						buttonWidth = button.outerWidth(true);
					} else {
						buttonWidth = button.outerWidth(false);
				}
					w -= buttonWidth;
					cell.data("buttonWidth", buttonWidth);
			}
				if (this.options.renderFilterButton === true) {
					if (this.options.advancedModeEditorsVisible === true || this.options.mode !== "advanced") {
						button.parent().bind({
								mousedown: $.proxy(this._toggleDropDown, this),
								mouseup: cancelFunc,
								click: cancelFunc,
								blur: $.proxy(this._closeDropDown, this),
								keydown: $.proxy(this._toggleDropDownKeyboard, this),
								mouseover: $.proxy(this._hoverButton, this),
								mouseout: $.proxy(this._unhoverButton, this),
								focus: $.proxy(this._activateButton, this)
						});
						/* L.A. 08 May 2012 Fixing bug #106229 - Filtering dropdown item list does not close after you select item in IE7. */
						/* L.A. 17 October 2012 Fixing bug #120937 - Filter dropdown is not hiding in IE 9 compatibility mode when the grid is inside tab */
						/* Browser mode may be IE9, but rendering mode may be IE7 or IE8 */
						if (document.documentMode === 5 || document.documentMode === 7 || $.ig.util.isIE7) {
							button.parent().bind({
									focusout: $.proxy(this._closeDropDown, this)
							});
					}
					} else if (this._renderFCFor(cols[ i ].key) === false) {
						button.parent().bind({
								click: $.proxy(this._openFilterDialog, this),
								keydown: $.proxy(this._openFilterDialogFromKeyboard, this),
								focus: $.proxy(this._activateButton, this),
								blur: $.proxy(this._deactivateButton, this)
						});
				}
			}
				if (this.options.advancedModeEditorsVisible === true || this.options.mode !== "advanced") {
					this._createEditor(cell, w, i, cols[ i ]);
			}
			}
				/* if advanced filtering is enabled we will also render the advanced filtering dialog */
				/* NOTE that it is only a single dialog for the whole grid, not per column ! */
			if (this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog").length > 0) {
				this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog").remove();
			}
			this._renderFilterDialog();
		},
		_renderFCFor: function (columnKey) {
			/* returns whether it should be rendered in Feature Chooser for the specified columnKey */
			return !(this._fcData === null ||
						this._fcData === undefined ||
						this.options.renderFC === false ||
						this._fcData[ columnKey ] !== true
					);
		},
		_columnMap: function () {
			var o = this.options,
				i,
				elem,
				cs = o.columnSettings,
				csLength = cs.length,
				result = [];

			if (o.renderFC === false) {
				return false;
			}

			for (i = 0; i < csLength; i++) {
				elem = { columnKey: cs[ i ].columnKey, enabled: true };
				if (cs[ i ].allowFiltering === false) {
					elem.enabled = false;
				}
				result.push(elem);
			}

			return result;
		},
		_headerCellRendered: function () {
			if (this._isInitFC !== true) {
				this._initFC();
			}
		},
		/* M.H. 4 Oct 2013 Fix for bug #151434: When filtering is advanced and advancedModeEditorsVisible = true the button for advanced filtering dialog is missing from the feature chooser. */
		_openFilterDialogFromFC: function (event, columnKey) {
			this._openFilterDialog(event, columnKey);
		},
		/* M.H. 30 March 2012 Fix for bug #106217 */
		toggleFilterRowByFeatureChooser: function (event) {
			/* toggle filter row when mode is simple or advancedModeEditorsVisible is TRUE. Otherwise show/hide advanced dialog
				paramType="object" Represents click event object
				paramType="string" Column key
			*/
			var fc, $filterRow, o = this.options,
				isShown = this._filterRowShown,
				$thead = this.grid._rootContainer().find("thead"),
				isAdvanced = !(o.mode === "simple" || o.advancedModeEditorsVisible === true);

			if (isShown === null || isShown === undefined) {
				this._filterRowShown = true;
				isShown = this._filterRowShown;
			}
			if (isAdvanced === false || (isAdvanced && o.advancedModeEditorsVisible)) {
				$filterRow = $thead.find("tr[data-role=\"filterrow\"]");
				if (isShown === true) {
					$filterRow.hide();
					/* M.H. 14 Feb 2013 Fix for bug #132858 */
					if (document.documentMode === 7 || $.ig.util.isIE7) {
						$filterRow.children().hide();
					}
					isShown = false;
				} else {
					$filterRow.show();
					/* M.H. 14 Feb 2013 Fix for bug #132858 */
					if (document.documentMode === 7 || $.ig.util.isIE7) {
						$filterRow.children().show();
					}
					this._setEditorsWidth();
					isShown = true;
				}
				/* M.H. 2 Aug 2012 Fix for bug #118136 */
				/* not called by FeatureChooser */
				/* M.H. 9 Apr 2014 Fix for bug #169578: igGridFiltering.toggleFilterRowByFeatureChooser method doesn't update feature chooser labels. */
				if (event === null || event === undefined) {
					fc = this.grid.element.data("igGridFeatureChooser");
					if (fc) {
						fc._toggleSelectedItems("Filtering");
					}
				}
				/* M.H. 21 Jun 2013 Fix for bug #143614: When grid is hierarchical with continuous virtualization and feature chooser is enabled height of rows is unnecessarily increased */
				this._filterRowShown = isShown;
				this.grid._initializeHeights();
				/* M.H. 10 May 2012 Fix for bug #110952 */
				/* M.H. 30 Mar 2015 Fix for bug 190585: Ignite UI Tree grid - hide/show filter hides a column in the grid */
				if (this.grid.options.autofitLastColumn &&
					this.grid.options.height &&
					this.grid.hasVerticalScrollbar() !== this.grid._hasVerticalScrollbar) {
					/* if we pass TRUE as an argument(rerenderedColgroups ) of the function _adjustLastColumnWidth - in case there are data-skip columns width of the last column decreases(minus width of the last column width) */
					this.grid._adjustLastColumnWidth();
				}
			}
		},
		_getEditorVal: function (editor) {
			if (editor._editorInput.is(":focus") &&
				editor.element.data("editorType") !== "igDatePicker") {
				editor._processValueChanging(editor._editorInput.val());
			}
			return editor.value();
		},
		_getEditorNameByColType: function (colType) {
			if (colType === "date" || colType === "datetime") {
				return "igDatePicker";
			}
			if (colType === "number" || colType === "numeric") {
				return "igNumericEditor";
			}
			return "igTextEditor";
		},
		_createEditor: function (parent, w, colIndex, col) {
			var $editor, colType, colKey, options, edtrType, self = this,
				colSetting, isReadOnly = false, cond;
			colType = col.dataType;
			colKey = col.key;
			if (colType === "object") {
				colType = this._getColType(colKey);
			}
			edtrType = this._getEditorNameByColType(colType);
			colSetting = this._findColumnSetting(colKey);
			cond = colSetting.condition;
			$editor = $("<span />")
				.attr("data-filter-editor", true);
				/*.css("float", 'left').css("width", '5px'); */
			if (this.options.filterButtonLocation === "left") {
				$editor.appendTo(parent);
			} else {
				$editor.prependTo(parent);
			}
			if (colType === "bool" || colType === "boolean") {
				isReadOnly = true;
			}
			/*check if there's a default condition to be applied and if so if it requires entry */
			if (colSetting.defaultExpressions && colSetting.defaultExpressions.length > 0) {
				$(colSetting.defaultExpressions).each(function () {
					if (colSetting.customConditions && colSetting.customConditions.hasOwnProperty(this.cond)) {
						/*if it's a custom condition */
						cond = colKey + "_" + this.cond;
					} else {
						cond = this.cond;
			}
					isReadOnly = !self.requiresFilteringExpression(cond);
				});
			}
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			options = {
				width: w,
				suppressNotifications: true,
				readOnly: isReadOnly,
				textAlign: "left",
				/*button: isDatePicker ? 'dropdown' : null, */
				/* fix for bug #76266 */
				/*maxDecimals: 6, */
				/* M.H. 14 Sep 2015 Fix for bug 206312: The value of the editor is not removed when Clear Filter option is used for column of type number */
				revertIfNotValid: false,
				placeHolder: this.options.nullTexts[ cond ],
				allowNullValue: true,
				rendered: function (e, args) {
					var edtr = args.owner, $edtrCont = edtr.editorContainer(),
						$indCont = parent.find(".ui-iggrid-indicatorcontainer");
					if (self.options.filterButtonLocation === "left") {
						$indCont.prependTo($edtrCont);
						/* M.H. 14 Sep 2015 Fix for bug 206389: When the input of the editor is disabled the filter button is not tapable */
						/* the issue is caused by the DOM architecture of editors. Indicator container overflows disabled input - on touch device event is first triggered on disabled input */
						$edtrCont.on({
							mousedown: function (e) {
								if (edtr.options.readOnly) {
									self._toggleDropDown({ currentTarget: $indCont.find("a") });
									e.stopPropagation();
								}
							}
						});
					} else {
						$indCont.appendTo($edtrCont);
					}
				}
			};
			$editor.data("colIndex", colIndex).data("colKey", colKey);
			if (colType === "date" || colType === "datetime") {
				options = $.extend({},
					options,
					{
						enableUTCDates: this.options.type === "remote" ? true : this.grid.options.enableUTCDates,
						valueChanged: $.proxy(this._filter, this),
						revertIfNotValid: false
					}
					);
			/* M.H. 28 May 2014 Fix for bug #172388: Date filter editor doesn't take into account grid's column format */
				if (col.format) {
				options.dateInputFormat = col.format;
			}
			} else {
				options.textChanged = $.proxy(this._filter, this);
			}
			$editor.data("editorType", edtrType);
			$editor.igGridFilterEditor(options);
			this._editors.push($editor.data(edtrType));
			/* A.T. 29 Dec 2010 */
			$editor.find("input").addClass(this.css.filterCellEditor);
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			/*return editor; */
		},
		_fixedColumnsChanged: function (args) {
			var i, start = args.start, $edtr,
				len = args.length,
				at = args.at;
			this.grid._rearrangeArray(this.options.columnSettings, start, len, at);
			this.grid._rearrangeArray(this._editors, start, len, at);
				for (i = 0; i < this._editors.length; i++) {
				$edtr = $(this._editors[ i ].element);
				$edtr.data("colIndex", i);
				$edtr.closest("td").find("span.ui-iggrid-filterbutton").data("colIndex", i);
				}
		},
		_columnsAltered: function (event, args) {
			var i, cs, start = args.start, len = args.len, idx = args.index;
			for (i = 0; i < this.options.columnSettings.length; i++) {
				cs = this.options.columnSettings[ i ];
				if (this.grid._isColumnHidden(cs.columnKey) === true) {
					start += i <= start ? 1 : 0;
					len += i >= start && i < start + len ? 1 : 0;
					idx += i <= idx ? 1 : 0;
				}
			}
			this.grid._rearrangeArray(this.options.columnSettings, start, len, idx);
			this.grid._rearrangeArray(this._editors, start, len, idx);
			for (i = 0; i < this._editors.length; i++) {
				$(this._editors[ i ].element).data("colIndex", i);
				$(this._editors[ i ].element)
					.closest("td")
					.find("span.ui-iggrid-filterbutton")
					.data("colIndex", i);
			}
		},
		_findColumnSetting: function (key) {
			var i;
			for (i = 0; i < this.options.columnSettings.length; i++) {
				if (this.options.columnSettings[ i ].columnKey === key) {
					return this.options.columnSettings[ i ];
				}
			}
		},
		/* Accepts parameters: */
		/* expressions - a list of filtering expressions (objects) */
		/* updateUI - flag specifying whether the filter row UI should also be updated when applying the filter */
		filter: function (expressions, updateUI, addedFromAdvanced) {
			/* Applies filtering programmatically and updates the UI by default
				paramType="array" An array of filtering expressions, each one having the format {fieldName: , expr: , cond: , logic: } where  fieldName is the key of the column, expr is the actual expression string with which we would like to filter, logic is 'AND' or 'OR', and cond is one of the following strings: "equals", "doesNotEqual", "contains", "doesNotContain", "greaterThan", "lessThan", "greaterThanOrEqualTo", "lessThanOrEqualTo", "true", "false", "null", "notNull", "empty", "notEmpty", "startsWith", "endsWith", "today", "yesterday", "on", "notOn", "thisMonth", "lastMonth", "nextMonth", "before", "after", "thisYear", "lastYear", "nextYear". The difference between the empty and null filtering conditions is that empty includes null, NaN, and undefined, as well as the empty string.
				paramType="bool" optional="true" specifies whether the filter row should be also updated once the grid is filtered
				paramType="bool" excluded="true"
			*/
			var i, cond;
			this._loadingIndicator.show();
			if (expressions !== undefined && expressions.length > 0) {
				this._isFilteringRequest = true;
			} else {
				//clear filter condition if condition does not require expression
				for (i = 0; i < this.options.columnSettings.length; i++) {
					cond = this.options.columnSettings[ i ].condition;
					if (!this.requiresFilteringExpression(cond)) {
						this.options.columnSettings[ i ].condition = null;
					}
				}
			}
			if (!addedFromAdvanced) {
				this._filterDataSource(expressions, true);
				/* M.H. 5 Mar 2013 Fix for bug #120009: When filtering through the API method you are unable to apply multiple filters through separate calls. */
				this._updateFiltersUI([], false);
			} else {
				this._filterDataSource(expressions);
			}
			if (updateUI === undefined || updateUI === true) {
				this._updateFiltersUI(expressions === undefined ?
										this._generateExpressions() :
										expressions,
										addedFromAdvanced);
			}
		},
		_filter: function (ui, args) {
			clearTimeout(this._timeoutId);
			this._ui = ui;
			this._args = args;
			if (parseInt(this.options.filterDelay, 10) === 0) {
				this._filterInternal();
			} else {
				this._timeoutId = setTimeout($.proxy(this._filterInternal, this), this.options.filterDelay);
			}
		},
		/*jshint -W106 */
		_filterInternal: function (colIndex, colKey) {
			/* determine the column */
			var args = this._args, _colIndex, _colKey, noCancel, editor, exprs;
			if (args !== undefined) {
				_colIndex = $(args.owner.element).data("colIndex");
				_colKey = $(args.owner.element).data("colKey");
			} else {
				_colIndex = colIndex;
				_colKey = colKey;
			}
			/* M.H. 14 Jan 2014 Fix for bug #161067: Using the after filter condition will leave values from the selected date unfiltered. */
			/* we want first to generate expressions so we pass it as arguments to the dataFiltering event. In this way we will allow users to manipulate filtering expressions before databinding */
			exprs = this._generateExpressions(true);
			noCancel = this._trigger(this.events.dataFiltering, null,
									{
										columnKey: _colKey,
										columnIndex: _colIndex,
										owner: this, newExpressions: exprs
									});
			if (noCancel) {
				this._loadingIndicator.show();
				editor = this._editors ? this._editors[ _colIndex ] : null;
				/* mark the column so that we know it's not generated from the advanced filters */
				if (editor) {
					editor._addedFromAdvanced = false;
				}
				this._curColKey = _colKey;
				this._curColIndex = _colIndex;
				this._filterDataSource(exprs, false, true);
				/*this._trigger(this.events.dataFiltered, null, {columnKey: _colKey, columnIndex: _colIndex, owner: this, expressions: exprs}); */
			}
		},
		_filterDataSource: function (expressions, apiCall, updateUI) {
			/* updateUI is bool - it is possible expressions to be generated but we should update button title */
			var exprs, i, j, expr, col, button, groupBy,
				cols = this.grid.options.columns, found = false, defaultTooltip, noCancel; // i, exprs;
			defaultTooltip = this.options.tooltipTemplate
								.replace("${condition}", this.options.labels.noFilter);
			/* M.H. 27 Mar 2015 Fix for bug 190869: Using advanced filtering on unbound combobox-column causes endless loading */
			/* it should be verified for all columns (NOT only for unbound) - M.H. 3 Apr 2015 Fix for bug 162227: When column dataType is not defined filtering a column will throw a JavaScript error */
			/* when it is called from API(from filter) some of the expressions(for unbound columns) if it is possible to not have dataType(then they should be set) */
			if (apiCall) {
				if ($.type(expressions) === "array" && expressions.length) {
					for (i = 0; i < expressions.length; i++) {
						expr = expressions[ i ];
						if (expr.dataType) {
							continue;
						}
						col = this.grid.columnByKey(expr.fieldName);
						if (col) {
							if (col.dataType) {
								expr.dataType = col.dataType;
							} else {
								/* M.H. 19 Feb 2014 Fix for bug #162227: Handle Invalid Column Config in igGrid */
								expr.dataType = "string";
							}
						}
					}
				}
			}
			if (expressions !== undefined && !updateUI) {
				/* L.A. 09 May 2012 - reset all filtering states & UI on each new api call */
				if (apiCall === true) {
					/* M.H. 5 Mar 2013 Fix for bug #120009: When filtering through the API method you are unable to apply multiple filters through separate calls. */
					this.grid.dataSource.settings.filtering.expressions = expressions;
					/*ensure filter dialog specific API is called only when mode is advanced. */
					if (this.options.mode === "advanced") {
						this._filterDialogClearAll();
						for (i = 0; i < expressions.length; i++) {
							this._addFilterFromDialog(null, expressions[ i ]);
						}
					}
				} else {
					/* L.A. 24 September 2012 - fixing bug #122228 */
					/* Grid -> Advanced Filtering sample-> filter condition is not cleared */
					/* Different behavior needs to be applied. If the call is from the API */
					/* then the expressions should be agregated */
					/* Otherwise the call is made from the UI and the expressions needs */
					/* to be replaced */
					this.grid.dataSource.settings.filtering.expressions = expressions;
				}
			} else {
				if (expressions !== undefined && updateUI) {
					exprs = expressions;
				} else {
					exprs = this._generateExpressions(true);
				}
				/* update the tooltips, since we are doing simple filtering (expressions are not provided) */
				for (i = 0; i < cols.length; i++) {
					found = false;
					button = this.grid._rootContainer()
						.find("#" + this.grid.element[ 0 ].id + "_dd_" + cols[ i ].key + "_button");
					for (j = 0; j < exprs.length; j++) {
						if (cols[ i ].key === exprs[ j ].fieldName) {
							found = true;
							if ((exprs[ j ].expr === undefined ||
									exprs[ j ].expr === null || exprs[ j ].expr === "") &&
									this.requiresFilteringExpression(exprs[ j ].cond)) {
								button.attr("title", defaultTooltip);
							} else {
								/*button.attr('title', exprs[ j ].fieldName + ' ' + exprs[ j ].cond + ' ' + exprs[ j ].expr + ' '); */
								/*M.K. 1/19/2015 Fix for bug 187532: Filter icon shows tooltip content inconsistently */
								button.attr("title",
									this.options.tooltipTemplate
										.replace("${condition}", this.options.labels[ exprs[ j ].cond ]));
							}
							break;
						}
					}
					if (!found) {
						/* place the default no filter */
						button.attr("title", defaultTooltip);
					}
				}
				this.grid.dataSource.settings.filtering.expressions = exprs;
			}
			if (this.options.persist) {
				this._saveFilteringExpressions();
			}
			/* M.H. 23 Jan 2013 Fix for bug #130586 */
			if (this.grid._hasUnboundColumns) {
				this.grid._rebindUnboundColumns = true;
			}
			/* A.T. 13 April 2011 fix for bug #72284 */
			this.grid.element.trigger("iggriduisoftdirty", { owner: this });
			noCancel = this.grid._trigger(this.grid.events.dataBinding, null,
								{
									owner: this.grid,
									dataSource: this.grid.dataSource
								});
			if (noCancel) {
				exprs = this.grid.dataSource.settings.filtering.expressions;

				/* A.T. fix for bug #73509 */
				/* it's important that paging also resets its "current" page Index, otherwise the paging dropdown, etc. won't be refreshed. */
				/* L.A. 03 May 2012 Fixed bug #110881 */
				/* If filtering is done local (only the selected page is filtered) then the pageIndex should not be changed to 0 */
				if (this.options.type === "remote") {
					this.grid.dataSource.settings.paging.pageIndex = 0;
					this.grid._shouldResetPaging = true;
				} else if (this.grid.container().find(".ui-iggrid-footer .ui-iggrid-results").length > 0) {
					/* M.H. 8 Mar 2013 Fix for bug #135130: When paging is defined after filtering and on filtering the footer is pagerRecordsLabelTemplate istead of filterSummaryTemplate */
					this.grid.container()
						.find(".ui-iggrid-footer .ui-iggrid-results")
						.data("overrideLabel", exprs.length);
				}
				this._isFilteringRequest = true;
				this._preserveSorting();
				if (exprs.length === 0) {
					/* A.T. 4 April 2011 - Fix for bug #66210 */
					/*this._isFilteringRequest = false; */
					if (!apiCall) {
						this._shouldFireDataFiltered = true;
					}
					/* M.H. 15 Feb 2013 Fix for bug #132957 */
					if (this.options.type === "remote") {
						this._isFilteringRequest = false;
						this.grid.dataSource.dataBind();
					} else {
						this.grid.dataSource.settings.filtering.type = "local";
						this._isFilteringRequest = false;
						this.grid.dataSource.clearLocalFilter();
						/* M.H. 24 Jun 2013 Fix for bug #145179: Grouped rows are not correct while clearing the filtered data after ungrouping. */
						groupBy = this.grid.element.data("igGridGroupBy");
						if (groupBy && groupBy.options &&
								groupBy.options.groupedColumns &&
								groupBy.options.groupedColumns.length > 0) {
							if (this.grid.dataSource.settings.sorting.expressions.length > 0 &&
								groupBy.options.type === "local") {
								this.grid.dataSource.sort(this.grid.dataSource.settings.sorting.expressions);
							}
						}
						this.grid._renderData();
					}
				} else {
					if (this.options.type === "remote") {
						if (!apiCall) {
							this._shouldFireDataFiltered = true;
						}
						this.grid.dataSource.dataBind();
					} else {
						this.grid.dataSource.settings.filtering.type = "local";
						this.grid.dataSource.filter(exprs);
						/* M.H. 14 Oct 2013 Fix for bug #154649: Rows are grouped incorrectly when applying and removing a filter if a filter is applied by default through code */
						/* when there is initial filtering and then groupby is applied the dataSource is not sorted(if first groupBy is applied the whole dataSource is sorted and then filtering and groupby works properly) */
						/* and filtering again causes issues */
						groupBy = this.grid.element.data("igGridGroupBy");
						if (groupBy && groupBy.options &&
							groupBy.options.groupedColumns &&
							groupBy.options.groupedColumns.length > 0) {
							if (this.grid.dataSource.settings.sorting.expressions.length > 0 &&
								groupBy.options.type === "local" &&
								this.grid.dataSource._allDataSorted === false) {
								this.grid.dataSource.sort(this.grid.dataSource.settings.sorting.expressions);
							}
						}
						/* L.A. 07 January 2013 - Fixing bug #129527 dataBound event is fired twice on filtering */
						/* dataBound event is immediately fired in _renderData, therefore it should be commented here */
						/*this.grid._trigger(this.grid.events.dataBound, null, {owner: this.grid}); */
						this.grid._renderData();
						if (!apiCall) {
							this._trigger(this.events.dataFiltered, null,
											{
												columnKey: this._curColKey,
												columnIndex: this._curColIndex,
												owner: this, expressions: exprs
											});
						}
					}
				}
			}
		},
		_filterDataSourceClear: function (colKey, colIndex) {
			var i, exprs = this.grid.dataSource.settings.filtering.expressions,
				noCancel, noCancelFiltering,
				clearFiltering = false;
			for (i = 0; i < exprs.length; i++) {
				if (exprs[ i ].fieldName === colKey) {
					clearFiltering = true;
					/*A.T. 8 March 2012 - Fix for bug #104244 */
					/*exprs.remove(i); */
					$.ig.removeFromArray(exprs, i);
					break;
				}
			}
			/*M.H. Fix for bug 207107: When I clear filter with no filter applied the grid requests data from the server */
			/*When there are no expressions to clear there's no need to apply filtering. */
			if (!clearFiltering) {
				return;
			}
			this.grid.dataSource.settings.filtering.expressions = exprs;
			/* M.H. 25 Feb 2014 Fix for bug #165286: Filter expression is kept after dataBind when  "Clear Filter" condition is applied and persistence is enabled */
			if (this.options.persist) {
				this._saveFilteringExpressions();
			}
			/* trigger dirty so that any other features reset their UI and state */
			/*this.grid.element.trigger('iggriduidirty', {owner: this}); */
			/* A.T. 22 Nov 2011 - fix for bug #90396 */
			if (!this._curColKey || colKey) {
				this._curColKey = colKey;
				this._curColIndex = colIndex;
			}
			/* M.H. 14 Jan 2014 Fix for bug #161067: Using the after filter condition will leave values from the selected date unfiltered. */
			noCancelFiltering = this._trigger(this.events.dataFiltering, null,
													{
														columnKey: this._curColKey,
														columnIndex: this._curColIndex,
														owner: this, newExpressions: exprs
													});
			/*M.K. Fix for bug 207107: When I clear filter with no filter applied the grid requests data from the server */
			/*When there are no expressions to clear there's no need to apply filtering. */
			if (noCancelFiltering) {
				noCancel = this.grid._trigger(this.grid.events.dataBinding, null,
												{
													owner: this.grid,
													dataSource: this.grid.dataSource
												});

				if (noCancel) {
					this.grid.dataSource.settings.paging.pageIndex = 0;
					this._preserveSorting();
					this.grid._shouldResetPaging = true;
					/* M.H. 8 July 2015 Fix for bug 202230: When you clear filter the loading indicator is not rendered. */
					this._loadingIndicator.show();
					/* L.A. 24 October 2012 - Fixing bug #125473 Clearing the last remaining filtering expression causes a data rebinding (local filtering) */
					if (this.options.type === "remote") {
						if (exprs.length > 0) {
							this._shouldFireDataFiltered = true;
							this._isFilteringRequest = true;
						} else {
							/* M.H. 13 Feb 2013 Fix for bug #131548 */
							this._isFilteringRequest = false;
							this._shouldFireDataFiltered = true;
						}
						this.grid.dataSource.dataBind();
					} else {
						this.grid.dataSource.settings.filtering.type = "local";
						/* M.H. 8 Mar 2013 Fix for bug #135130: When paging is defined after filtering and on filtering the footer is pagerRecordsLabelTemplate istead of filterSummaryTemplate */
						if (this.grid.container().find(".ui-iggrid-footer .ui-iggrid-results").length > 0) {
							this.grid.container()
								.find(".ui-iggrid-footer .ui-iggrid-results")
								.data("overrideLabel", exprs.length);
						}
						if (exprs.length > 0) {
							this._isFilteringRequest = true;
							this.grid.dataSource.filter(exprs);
						} else {
							this._isFilteringRequest = false;
							this.grid.dataSource.clearLocalFilter();
						}
						this.grid._renderData();
						this._trigger(this.events.dataFiltered, null,
									{
										columnKey: this._curColKey,
										columnIndex: this._curColIndex,
										owner: this, expressions: exprs
									});
					}
				}
			}
		},
		_preserveSorting: function () {
			/* we need to be aware if there are groupedColumns or not. If there aren't we shouldn't do this. */
			/* A.T. Fix for bug #91599 */
			var hasGroupedCols = false, groupBy = this.grid.element.data("igGridGroupBy");
			if (groupBy && groupBy.options &&
				groupBy.options.groupedColumns &&
				groupBy.options.groupedColumns.length > 0) {
				hasGroupedCols = true;
			}
			if (this.grid.dataSource.settings.sorting.expressions.length > 0 && hasGroupedCols) {
				this.grid.dataSource.settings.sorting.defaultFields =
					this.grid.dataSource.settings.sorting.expressions;
			}
		},
		/* M.H. 11 Mar 2014 Fix for bug #166528: Sorting and Filtering does not persist its state, when they are applied to unbound column */
		_getDataColumnFilteringExpressions: function (fe) {
			/* return only those filtering expressions that are for data bound columns(for which property unbound is not equal to TRUE) */
			if (!this.grid._hasUnboundColumns) {
				return fe;
			}
			var grid = this.grid, newFE;
			newFE = $.grep(fe, function (s) {
				var col = grid.columnByKey(s.fieldName);
				return !col || col.unbound !== true;
			});
			return newFE;
		},
		_saveFilteringExpressions: function () {
			var fe = this._getDataColumnFilteringExpressions(
					this.grid.dataSource.settings.filtering.expressions);
			if (this.element.closest(".ui-iggrid-root").data("igGrid")) {
				this.grid._savePersistenceData(fe, "filtering", this.grid.element[ 0 ].id);
			}
			this._filteringExpressions = fe;
		},
		_preserveFiltering: function () {
			var fe = this._filteringExpressions ||
						this.grid._getPersistenceData("filtering", this.grid.element[ 0 ].id);
			if (fe) {
				/* M.H. 11 Mar 2014 Fix for bug #166528: Sorting and Filtering does not persist its state, when they are applied to unbound column */
				/* because the first time we rebind _onUIDirty is not called we use a hack for removing the value in editors */
				if (this.grid._hasUnboundColumns) {
					this._updateFiltersUI([], false);
				}
				this.grid.dataSource.settings.filtering.expressions = fe;
				this.grid.dataSource.settings.filtering.defaultFields = fe;
				/* we need to set _defaultExpressions - used to populate UI editors */
				this._defaultExpressions = fe;
			}
		},
		_updateFiltersUI: function (expressions) {
			var i, j, editor, filterList, items, condName, $li, exprLen = expressions.length;
			/* L.A. 10 October 2012 - Fixing bug #124163 */
			/* Filter method does not update the UI when empty array is used and updateUI=true */
			/* M.H. 20 May 2013 Fix for bug #141618: When advancedModeEditorsVisible option is true, deleting an expression from the advanced filtering dialog does not change the value from the filtering editor. */
			if (this._editors) {//expressions.length === 0 &&
				/* make sure we update (clear) the inputs */
				for (i = 0; i < this._editors.length; i++) {
					if (this.options.columnSettings[ i ].allowFiltering !== false) {
						this._editors[ i ].value(null);
						/*this._editors[i]._addedFromAdvanced = false; */
					}
				}
			}
			/* M.H. 21 Sep 2015 Fix for bug 206864: Calling filter with empty expressions array does not clear "Empty" and "NotEmpty" conditions with subsequent filtering operations */
			if (this.options.showEmptyConditions && !exprLen) {
				/* we should traverse all columnSetting and check whether there are filter conditions set to empty/notEmpty/null/notNull. */
				/* If so then dropdown selected conditions should be set to default values. If this is not done then bug 206864 will be reproduced - in generateExpressions it will be added these (empty)expressions as well. */
				for (i = 0; i < this.options.columnSettings.length; i++) {
					condName = this.options.columnSettings[ i ].condition;
					if (condName === "empty" || condName === "notEmpty" ||
						condName === "null" || condName === "notNull") {
						/* we need to reset to default filter condition in this case - to work properly _selectDropDownItem it is necessary to pass LI from the proper dropdown(no matter which LI) */
						$li = this.grid._rootContainer()
							.find("#" + this.grid.element[ 0 ].id + "_dd_" + this.options.columnSettings[ i ].columnKey)
							.find("ul").children().first();
						if ($li.length) {
							this._selectDropDownItem({ currentTarget: $li }, null);
						}
					}
				}
			}
			for (i = 0; i < exprLen; i++) {
				/*1.  update the selected item in the filter dropdown */
				filterList = this.grid._rootContainer()
					.find("#" + this.grid.element[ 0 ].id + "_dd_" + expressions[ i ].fieldName)
					.find("ul");
				items = filterList.children();
				/*	M.W.H. Fix for bug #204763: When custom filter is set as default its text is not in the editor. */
				condName = this._resolveConditionNameFromExpression(expressions[ i ]);
				for (j = 0; j < items.length; j++) {
					if ($(items[ j ]).data("cond") === condName) {
						 /* update selection */
						/* L.A. 29 October 2012 - Fixing bug #124166 - Filter client API method doesn't rebind the grid */
						this._selectDropDownItem({ currentTarget: items[ j ] }, null, expressions[ i ]);
						break;
					}
				}
				/*2. update the editor text (and value) */
				editor = this._editors[ this._columnIndexFromKey(expressions[ i ].fieldName) ];
				/* L.A. 06 November 2012 - Fixing bug #126543 */
				/* igGridFiltering.filter method throws an error when filtered column is configured with allowFiltering: false */
				if (editor !== undefined && editor !== null && editor.value) {
					if (this.requiresFilteringExpression(condName)) {
					editor.value(expressions[ i ].expr);
					} else {
						editor.value(this._editorValueForCondition(condName, editor));
					}
					editor._addedFromAdvanced = true;
				}
			}
			this._updateTooltips(expressions);
		},
		_updateTooltips: function (expressions) {
			var button, title, found, titleText, tempTooltipExpr,
				condName, conditionLabel, i, j, expressionFieldName,
				cols = this.grid.options.columns, colsLength = cols.length,
				isSimpleMode = (this.options.mode === "simple" ||
								this.options.advancedModeEditorsVisible === true);
			titleText = this.options.tooltipTemplate.replace("${condition}", this.options.labels.noFilter);
			title = "";
			for (i = 0; i < expressions.length; i++) {
				/* update the advanced filtering tooltips */
				/* 1. find the tooltip for the column */
				button = this.grid._rootContainer()
					.find("#" + this.grid.element[ 0 ].id + "_dd_" + expressions[ i ].fieldName + "_button");
				/* A.T. 1 Sept 2011 - fix for bug #85220 */
				if (!this.requiresFilteringExpression(expressions[ i ].cond) ||
					expressions[ i ].expr === undefined || expressions[ i ].expr === null) {
					tempTooltipExpr = "";
				} else {
					tempTooltipExpr = expressions[ i ].expr;
					/* M.H. 1 Apr 2013 Fix for bug #137908: Date filter value is one day behind the selected date */
					if (expressions[ i ].type === "date") {
						if ($.type(tempTooltipExpr) === "number") {
							tempTooltipExpr = new Date(tempTooltipExpr);
						}
						if (this.grid.options.enableUTCDates &&
								$.type(tempTooltipExpr) === "date" &&
								tempTooltipExpr.toUTCString) {
							tempTooltipExpr = tempTooltipExpr.toUTCString();
						}
					}
				}
				/* M.H. 2 Apr 2013 Fix for bug #137910: Column filter tooltip shows the column key instead of column header when using Advanced filtering */
				expressionFieldName = expressions[ i ].fieldName;
				for (j = 0; j < colsLength; j++) {
					if (cols[ j ].key === expressionFieldName) {
						expressionFieldName = cols[ j ].headerText;
						break;
					}
				}
				/*	M.W.H. Fix for bug #204760: When custom condition is set as default expression the title of the dropdown button is not correct. */
				/*	Check for custom conditions. */
				condName = this._resolveConditionNameFromExpression(expressions[ i ]);
				conditionLabel = this.options.labels[ condName ];
				/* M.H. 17 Sep 2013 Fix for bug #148955: Filtering tooltip is different when filter through UI and API */
				if (isSimpleMode) {
					button.attr("title", this.options.tooltipTemplate.replace("${condition}", conditionLabel));
					continue;
				}
				if (i === 0) {
					button.attr("title", expressionFieldName + " " + conditionLabel + " " + tempTooltipExpr + " ");
				} else {
					button.attr("title",
									title + expressions[ i ].logic + " " +
									expressionFieldName + " " + conditionLabel + " " + tempTooltipExpr + " ");
				}
				title = button.attr("title");
			}
			/* if some column doesn't have a filter applied, we should update its tooltip to match the "no filter" tooltip */
			for (i = 0; i < colsLength; i++) {
				found = false;
				for (j = 0; j < expressions.length; j++) {
					if (expressions[ j ].fieldName === cols[ i ].key) {
						found = true;
						break;
					}
				}
				if (!found) {
					/* update tooltip */
					this.grid._rootContainer()
						.find("#" + this.grid.element[ 0 ].id + "_dd_" + cols[ i ].key + "_button")
						.attr("title", titleText);
				}
			}
		},
		_resolveConditionNameFromExpression: function (expression) {
			var expressionFieldName,
				custConds = this._dsTransformedCustomConditions;

				expressionFieldName = expression.fieldName;
			/*	Check if the expression is a custom condition. */
			if (custConds && custConds[ expressionFieldName + "_" + expression.cond ]) {
				return expressionFieldName + "_" + expression.cond;
			} else {
				return expression.cond;
			}
		},
		_columnIndexFromKey: function (key) {
			var i;
			for (i = 0; i < this.grid.options.columns.length; i++) {
				if (this.grid.options.columns[ i ].key === key) {
					return i;
				}
			}
		},
		_generateExpressions: function (filterRowTrigger) {
			/* iterate over the columns and generate the current filtering expressions array */
			var exprs = [], cols = this.grid.options.columns, expr, i,
				currentCondition, requiresEntry, isExprAdded, dt,
				defExpr, precise, format, preciseInd;
			for (i = 0; i < cols.length; i++) {
				if (this._editors && this._editors[ i ] &&
					this._editors[ i ]._addedFromAdvanced && !filterRowTrigger) {
					continue;
				}
				/* if filtering is disabled for this column, exit - MH: for now exit if there aren't default expressions. */
				/* Otherwise get the first from the default expressions */
				/* M.H. 8 Apr 2015 Fix for bug 190504: Clearing a filter with the UI clears filters set with the defaultExpressions property in the column settings for columns without the filtering UI allowed */
				if (this.options.columnSettings[ i ].allowFiltering !== true) {
					defExpr = this.options.columnSettings[ i ].defaultExpressions;
					if (defExpr && defExpr.length) {
						expr = defExpr[ 0 ].expr;
						currentCondition = defExpr[ 0 ].cond;
					} else {
						continue;
					}
				} else {
					expr = this._getEditorVal(this._editors[ i ]);
					currentCondition = this.options.columnSettings[ i ].condition;
				}
				requiresEntry = this.requiresFilteringExpression(currentCondition);
				if (currentCondition === undefined || currentCondition === null || currentCondition === "") {
					continue;
				}
				if ((expr === undefined || expr === null ||  expr === "") && requiresEntry) {
					continue;
				} /* else if (!requiresEntry) {
					expr = null;
				}
				*/
				precise = "";
				dt = cols[ i ].dataType;
				/* M.H. 17 Jun 2013 Fix for bug #144579: Filtering by date when browser timezone is negative gives wrong results (date is one day behind) */
				if (dt === "date" && expr) {
					if (this.options.type === "remote" &&
						this.options.filterExprUrlKey !== null &&
						this.options.filterExprUrlKey !== undefined) {
						expr = Date.UTC(expr.getUTCFullYear(),
										expr.getUTCMonth(),
										expr.getUTCDate(),
										expr.getUTCHours(),
										expr.getUTCMinutes());
					} else {
						/* detect whether there is precise datetime format */
						format = cols[ i ].format;
						if (format && format.indexOf) {
						/* M.K. 12 Nov 2014 Fix for bug 185048:Local filtering does not work as expected */
						/*with date values when the date column's format contains "H" */
							preciseInd = format.toLowerCase().indexOf("h");
							if (preciseInd === -1) {
								preciseInd = format.indexOf("m");
							}
							if (preciseInd === -1) {
								preciseInd = format.indexOf("s");
							}
							if (preciseInd > -1) {
								precise = format.substr(preciseInd);
							}
						}
						if ($.type(expr) === "date") {
							/* M.H. 23 Feb 2016 Fix for bug 214690: Filter condition "Before" is not giving the correct results */
							expr = new Date(expr.valueOf());
							if (precise === "" && currentCondition === "after") {
								/* M.H. 14 Jan 2014 Fix for bug #161067: Using the after filter condition will leave values from the selected date unfiltered. */
								expr.setHours(23);
								expr.setMinutes(59);
								expr.setSeconds(59);
								expr.setMilliseconds(999);
							}
						}
					}
				}
				isExprAdded = false;
				/*A.T. 28 March 2011 - Fix for bug #69561 */
				if (dt !== "date" || (dt === "date" && expr !== null &&
						expr !== undefined && currentCondition !== "empty" &&
						currentCondition !== "notEmpty" &&
						currentCondition !== "null" && currentCondition !== "notNull") ||
						(dt === "date" && !this.requiresFilteringExpression(currentCondition))) {
					if (expr !== "" && (dt !== "bool") && !this.options.filterExprUrlKey) {
						isExprAdded = true;
						exprs.push({ fieldName: cols[ i ].key, cond: currentCondition, expr: expr });
					} else if ((dt === "bool" && expr !== "" && expr !== null) || (dt !== "bool" &&
							((this.options.filterExprUrlKey !== undefined &&
								this.options.filterExprUrlKey !== null) ||
									this.options.type === "local"))) {
						isExprAdded = true;
						exprs.push({ fieldName: cols[ i ].key, cond: currentCondition, expr: expr });
					} else if (dt === "bool" && (expr === "" || expr === null) &&
								(currentCondition === "null" || currentCondition === "notNull" ||
							currentCondition === "empty" || currentCondition === "notEmpty")) {
						isExprAdded = true;
						exprs.push({ fieldName: cols[ i ].key, cond: currentCondition, expr: expr });
					}
					/* M.H. 6 Feb 2014 Fix for bug #162227: Handle Invalid Column Config in igGrid */
					if (isExprAdded) {
						if (dt === undefined && $.type(expr) === "string") {
							exprs[ exprs.length - 1 ].dataType = "string";
						}
						/* M.H. 10 Sep 2012 Fix for bug #120759 */
						if (cols[ i ].unbound) {
							exprs[ exprs.length - 1 ].dataType = dt;
						}
						if (precise) {
							exprs[ exprs.length - 1 ].preciseDateFormat = precise;
						}
					}
				}
			}
			return exprs;
		},
		_getDefaultCondition: function (type) {
			var result = "equals";
			if (type === undefined || type === null || type === "string") {
				result = "contains";
			} else if (type === "number") {
				result = "equals";
			} else if (type === "date") {
				result = "on";
			} else if (type === "bool" || type === "boolean") {
				result = "true";
			} else if (type === "object") {
				result = "";
			}
			return result;
		},
		_initDefaultSettings: function () {
			// fill in default column settings, so that later on we can get the current sort state of every sortable column
			// iterate through columns
			var settings = [], key, de, cs = this.options.columnSettings,
				i, j, k, l, cols = this.grid.options.columns, allowFiltering, defExpr,
				isToCheckUnboundColumns = (this.grid._hasUnboundColumns === true &&
											this.options.type === "remote"),
				conditionIsValid, conds, custConds, currSettings;
			/* M.H. 19 Jul 2013 Fix for bug #147233: cannot set default filtering expressions in ig.DataSource (they aren't taken into account , similar to sorting default expressions)*/
			this._defaultExpressions = this.grid.dataSource.settings.filtering.expressions || [];
			/* initialize */
			if (cols && cols.length > 0) {
				for (i = 0; i < cols.length; i++) {
					allowFiltering = true;
					/* remote filtering should be forbidden for unbound columns */
					if (isToCheckUnboundColumns && this.grid.getUnboundColumnByKey(cols[ i ].key) !== null) {
						allowFiltering = false;
					}
					settings[ i ] = {
						"columnIndex": i,
						"columnKey": cols[ i ].key,
						"allowFiltering": allowFiltering
					};
				}
			}
			/* M.H. 24 Mar 2014 Fix for bug #165993: Sorting is not working with initial sorting, autogenerate column and no columns definition */
			/* we got defaultExpressions from columnSettings(only for those which have defined columnKey - NOTE: columnKey should be properly defined) */
			currSettings = $.extend(true, {}, settings, cs);
			if (settings.length === 0 &&
				this.grid.options.autoGenerateColumns &&
				cs.length > 0) {
				for (i = 0; i < cs.length; i++) {
					if (!cs[ i ].columnKey) {
						continue;
					}
					/*	M.W.H.: Get the column type from the schema since the columns are not auto-generated yet.*/
					conds = this._populateConditionsList(cs[ i ].columnKey,
						this._getColType(currSettings[ i ].columnKey));
					defExpr = cs[ i ].defaultExpressions;
					custConds = cs[ i ].customConditions;
					if (defExpr && defExpr.length > 0) {
						for (k = 0; k < defExpr.length; k++) {
							/*	Check if the condition for the defExpr is allowed. */
							conditionIsValid = false;

							for (l = 0; l < conds.length; ++l) {
								/*	M.W.H. Fix for bug 203365: When you set the custom condition is set as a default expression the grid throws an error. */
								for (key in custConds) {
									if (custConds.hasOwnProperty(key)) {
										if (conds[ l ].condition === cs[ i ].columnKey + "_" + defExpr[ k ].cond) {
											conditionIsValid = true;
											break;
										}
									}
								}
								if (conditionIsValid) {
									break;
								}

								if (conds[ l ].condition === defExpr[ k ].cond) {
									conditionIsValid = true;
									break;
								}
							}
							if (!conditionIsValid) {
								throw new Error($.ig.util.stringFormat(
									$.ig.GridFiltering.locale.defaultConditionContainsInvalidCondition,
									cs[ i ].columnKey));
							}
							de = {
								fieldName: cs[ i ].columnKey,
								expr: defExpr[ k ].expr,
								cond: defExpr[ k ].cond
							};
							if (defExpr[ k ].logic) {
								de.logic = defExpr[ k ].logic;
							}
							this._defaultExpressions.push(de);
						}
					}
				}
			} else {
				for (i = 0; i < cs.length; i++) {
					for (j = 0; j < settings.length; j++) {
						/* M.H. 11 August 2015 Fix for bug 204196: Calling dataBind twice after moving a column with a custom filtering combobox causes an error */
						if (settings[ j ].columnKey === cs[ i ].columnKey ||
							(cs[ i ].columnKey === undefined && settings[ j ].columnIndex === cs[ i ].columnIndex)) {
							break;
						}
					}
					if (j === settings.length) {
						continue;
					}
					conds = this._populateConditionsList(cs[ i ].columnKey,
						this._getColType(currSettings[ i ].columnKey));
					for (key in cs[ i ]) {
						if (cs[ i ].hasOwnProperty(key)) {
							if (key !== "columnKey" && key !== "columnIndex") {
								settings[ j ][ key ] = cs[ i ][ key ];
							}
							/* M.H. 19 Jul 2013 Fix for bug #147233: cannot set default filtering expressions in ig.DataSource (they aren't taken into account , similar to sorting default expressions) */
							if (key === "defaultExpressions" && cs[ i ][ key ]) {
								defExpr = cs[ i ][ key ];
								custConds = cs[ i ].customConditions;
								for (k = 0; k < defExpr.length; k++) {
									/*	Check if the condition for the defExpr is allowed. */
									conditionIsValid = false;
									for (l = 0; l < conds.length; ++l) {
										/*	M.W.H. Fix for bug 203365: When you set the custom condition is set as a default expression the grid throws an error. */
										for (key in custConds) {
											if (custConds.hasOwnProperty(key)) {
												if (conds[ l ].condition === cs[ i ].columnKey + "_" + defExpr[ k ].cond) {
													conditionIsValid = true;
													break;
												}
											}
										}
										if (conditionIsValid) {
											break;
										}
										if (conds[ l ].condition === defExpr[ k ].cond) {
											conditionIsValid = true;
											break;
										}
									}
									if (!conditionIsValid) {
										throw new Error($.ig.util.stringFormat(
												$.ig.GridFiltering.locale.defaultConditionContainsInvalidCondition,
												cs[ i ].columnKey));
									}
									de = {
										fieldName: cs[ i ].columnKey,
										expr: defExpr[ k ].expr,
										cond: defExpr[ k ].cond
									};
									if (defExpr[ k ].logic) {
										de.logic = defExpr[ k ].logic;
									}
									this._defaultExpressions.push(de);
								}
							}
						}
					}
				}
			}
			/* copy */
			this.options.columnSettings = settings;
			/* store default expressions */
			/* L.A. 03 December 2012 - Fixing bug #128401 - $.ig.DataSource.filtering.expressions are cleared by igGridFiltering */
			this.grid.dataSource.settings.filtering.expressions = this._defaultExpressions;// || this.grid.dataSource.settings.filtering.expressions;
			/* M.H. 19 Jul 2013 Fix for bug #147233: cannot set default filtering expressions in ig.DataSource (they aren't taken into account , similar to sorting default expressions) */
			this.grid.dataSource.settings.filtering.defaultFields = this._defaultExpressions;
		},
		_transformCustomConditionsForDataSource: function () {
			var i, cs = this.options.columnSettings, curCS,
				custConds, curCond, newCond, key, keyCond;
			for (i = 0; i < cs.length; ++i) {
				curCS = cs[ i ];
				custConds = curCS.customConditions;
				if (custConds) {
					this._dsTransformedCustomConditions = this._dsTransformedCustomConditions || {};
					for (key in custConds) {
						if (custConds.hasOwnProperty(key)) {
							curCond = custConds[ key ];
							keyCond = curCS.columnKey + "_" + key;
							newCond = {
								requireExpr: !!curCond.requireExpr,
								filterFunc: curCond.filterFunc,
								expressionText: curCond.expressionText || key
							};
							/*	M.W.H. Fix for bug 203332: When you select the custom condition its text is not displayed in the editor. */
							this.options.labels[ keyCond ] = this.options.labels[ keyCond ] || curCond.labelText;
							this.options.nullTexts[ keyCond ] = this.options.nullTexts[ keyCond ] || curCond.labelText;
							this._dsTransformedCustomConditions[ keyCond ] = newCond;
			}
				}
				}
			}
			this.grid.dataSource.settings.filtering.customConditions = this._dsTransformedCustomConditions;
		},
		_renderDropDown: function (type, id, colName) {
			var ul = this._renderDropDownElement(id, colName), i, item, obj, conds,
				cs = this.options.columnSettings, cols = this.grid.options.columns;
			conds = this._populateConditionsList(colName, this._getColType(colName));
			if (this.options.filterDropDownItemIcons !== true && conds.length > 0 ) {
				$("<a></a>").appendTo($("<li></li>")
							.appendTo(ul)
							.addClass(this.css.filterDropDownListItem)
							.addClass(this.css.filterDropDownListItemClear))
							.text(this.options.labels.clear)
							.addClass("ui-corner-all");
				for (i = 0; i < conds.length; ++i) {
					$("<a></a>")
						.appendTo($("<li></li>")
						.appendTo(ul)
						.addClass(this.css.filterDropDownListItem)
						.data("cond", conds[ i ].condition))
						.text(conds[ i ].text)
						.addClass("ui-corner-all");
				}
			} else if (this.options.filterDropDownItemIcons === true  && conds.length > 0) {
				obj = {
					itemClass: this.css.filterDropDownListItemWithIcons,
					imgContainerClass: this.css.filterItemIconContainer,
					textClass: this.css.filterDropDownListItemTextContainer
				};
				this._renderDropDownToList(obj, [{
					text: this.options.labels.clear,
					imgClass: this.css.filterItemIconClear
				}], ul);
				this._renderDropDownToList(obj, conds, ul);
				}
			/* if the mode is "Advanced", add the "Advanced" button */
			if (this.options.mode === "advanced") {
				this._renderAdvancedButton(ul);
			}
			/*A.T. 14 Feb 2011 - Fix for bug #65814 */
			/* set initially selected value, if any */
			for (i = 0; i < cs.length; i++) {
				if (cs[ i ].columnKey === colName &&
					cs[ i ].condition !== this._getDefaultCondition(this._getColType(cols[ i ].key))) {
					// find the item
					if (cs[ i ].condition === "null") {
						item = ul.find(":contains(\"" + this.options.labels.nullLabel + "\")");
					} else {
						item = ul.find(":contains(\"" + this.options.labels[ cs[ i ].condition ] + "\")");
					}
					item = item.children().first().closest("li");
					item.addClass(this.css.filterDropDownListItemActive)
						.parent()
						.data("selectedItem", $("li", item.parent()).index(item));
				}
			}
		},
		_renderAdvancedButton: function (ul) {
			var li;
			li = $("<li></li>")
				.appendTo(ul)
				.addClass(this.css.filterDropDownListItemAdvanced);
			/* render the igButton inside the LI */
			this._currentButton = $(toStaticHTML("<input type=\"button\"></input>"));
			this._currentButton
				.igButton({ labelText: this.options.labels.advancedButtonLabel })
				.bind({
				mousedown: $.proxy(this._openFilterDialog, this)
			});
			li.append(this._currentButton);
		},
		_openFilterDialogFromKeyboard: function (event) {
			if (event.keyCode === $.ui.keyCode.ENTER) {
				this._openFilterDialog(event);
			}
		},
		_checkModalDialogFocus: function () {
			/* if modal dialog is opened and the user starts tabbing through grid elements(inside grid container) - like grid data cells/ grid header cells/etc. focus first element inside modal dialog(fix for bug 186625) */
			/* M.H. 29 June 2015 Fix for bug 186625: When a Feature Chooser is used and one of the dialogs is displayed you can move focus back to the grid (using TAB) */
			var $dialog = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog"),
				$container = this.grid._rootContainer();
			$container.unbind("keydown.focusChecker");
			$container.bind("keydown.focusChecker", function (e) {
				var tabElems, target, gridContainer = $container[ 0 ];
				if (e.keyCode === $.ui.keyCode.TAB) {
					target = document.activeElement;
					if (!target || !gridContainer) {
						return;
					}
					if (target === gridContainer ||
							($.contains(gridContainer, target) &&
								!$.contains($dialog[ 0 ], target))) {
						tabElems = $(":tabbable", $dialog);
						tabElems.first().focus();
						return;
					}
				}
			});
		},
		_filterDialogOkClicked: function (event, ui) {
			var noCancel = this._trigger(this.events.filterDialogFiltering, null, {
				dialog: this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog"),
				owner: this
			});
			if (noCancel) {
				ui.toClose = true;
			}
		},
		_openFilterDialog: function (event, columnKey) {
			var dialog = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog");
			/* fire filterDialogOpening event */
			this._dialogCurrentColumn = $(event.target).closest("ul").data("colName");
			if (this._dialogCurrentColumn === undefined ||
				this._dialogCurrentColumn === null) {
				/* M.H. 30 March 2012 Fix for bug #106217 */
				if (columnKey !== null && columnKey !== undefined) {
					this._dialogCurrentColumn = columnKey;
				} else {
					/* in advanced mode when we don't render dropdowns there is no UL, so we take the col name from the closest TH which is the header's TH */
					this._dialogCurrentColumn = $(event.target).closest("th").data("colName");
				}
			}
			/* L.A. 10 May 2012 Fixing bug #110975 */
			if (this._dialogCurrentColumn === undefined ||
				this._dialogCurrentColumn === null) {
				this._dialogCurrentColumn = this.grid.options.columns[ 0 ];
			}
			dialog.igGridModalDialog("openModalDialog");
			event.preventDefault();
			event.stopPropagation();
		},
		/* M.H. 30 March 2012 Fix for bug #106217 */
		_filterDialogOpening: function (event) {
			/* show the dialog that has been created before */
			var maxZ, left, top, pos, h, w, bh, dh, dw, bw, addDefault = true,
				noCancel = true, block = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_block"),
				dialog = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog"),
				rOffset, i, expressions = this.grid.dataSource.settings.filtering.expressions;
			/* M.H. 29 June 2015 Fix for bug 186625: When a Feature Chooser is used and one of the dialogs is displayed you can move focus back to the grid (using TAB) */
			this._checkModalDialogFocus();
			/* M.H. 10 July 2012 Fix for bug #106407 */
			maxZ = $.ig.getMaxZIndex(this.grid._rootContainer().attr("id") + "_dialog");
			block.css("zIndex", maxZ + 1);
			dialog.css("zIndex", maxZ + 2);
			/* recalc containment */
			/* L.A. 13 June 2012 Fixing bug #114457 Advanced search filtering dialog cannot be dragged outside of the grid */
			if (this.options.filterDialogContainment === "owner") {
				dialog.draggable("option", "containment", this.grid._rootContainer());
			} else {
				/* M.H. 22 May 2014 Fix for bug #162225: Allow Users to Position the igGrid Advanced Filter Dialog Anywhere in Browser Window */
				dialog.draggable("option", "containment", "document");
			}
			/* M.H. 5 Mar 2013 Fix for bug #120009: When filtering through the API method you are unable to apply multiple filters through separate calls. */
			dialog.find(".ui-iggrid-filtertable tbody tr").remove();
			this._dialogCurrentType = this.grid.columnByKey(this._dialogCurrentColumn).dataType;
			/* M.H. 8 Mar 2013 Fix for bug #135184: Columns are added for filtering if you open the advanced filtering dialog and click Cancel */
			dialog.find(".ui-iggrd-filtertable tbody tr").remove();
			if (expressions && expressions.length > 0) {
				addDefault = true;
				for (i = 0; i < expressions.length; i++) {
					/* M.H. 8 Mar 2013 Fix for bug #135184: Columns are added for filtering if you open the advanced filtering dialog and click Cancel */
					if (!addDefault || expressions[ i ].fieldName === this._dialogCurrentColumn) {
						addDefault = false;
					}
					/*this._addFilterFromDialog(null, expressions[ i ]); */
					this._addFilterFromDialog(null, expressions[ i ]);
				}
				if (addDefault) {
					this._addFilterFromDialog();
				}
			} else {
				this._addFilterFromDialog();
			}
			noCancel = this._trigger(this.events.filterDialogOpening, null, { dialog: dialog, owner: this });
			if (noCancel) {
				/* M.H. 9 Nov 2012 Fix for bug #126471 */
				pos = $.ig.util.offset(this.grid._rootContainer());
				left = pos.left;
				top = pos.top;
				if (block.outerWidth() !== this.grid._rootContainer().outerWidth()) {
					block.css("width", this.grid._rootContainer().outerWidth());
				}
				if (block.outerHeight() !== this.grid._rootContainer().outerHeight()) {
					block.css("height", this.grid._rootContainer().outerHeight());
				}
				/* hide any error messages that are shown */
				this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog_error").hide();
				rOffset = $.ig.util.getRelativeOffset(block);
				block.css({ left: left - rOffset.left, top: top - rOffset.top }).fadeToggle();
				/* show the actual dialog */
				w = this.grid._rootContainer().outerWidth();
				h = this.grid._rootContainer().outerHeight();
				/* calculate browser height and width, and if the grid's w  & h exceed the browser ones, position the advanced filtering dialog */
				/* so that it can be seen on screen */
				bw = $(window).width();
				bh = $(window).height();
				if (w + left > bw) {
					w = w - (w + left - bw);
				}
				if (w <= 0) {
					w = this.grid._rootContainer().outerWidth();
				}
				if (h + top > bh) {
					h = h - (h + top - bh);
				}
				if (h <= 0) {
					h = this.grid._rootContainer().outerHeight();
				}
				dh = parseInt(this.options.filterDialogHeight, 10);
				dw = parseInt(this.options.filterDialogWidth, 10);
				if (isNaN(dh) || dh <= 0) {
					dh = dialog.outerHeight();
				}
				if (isNaN(dw) || dw <= 0) {
					dw = dialog.outerWidth();
				}
				/* L.A. 05 September 2012 Fixing bug #120390 */
				/* When the grid is shorter than the Advanced filtering dialog the pop up window hides its titlebar */
				top = top + parseInt(h / 2, 10) - dh / 2;
				left = left + parseInt(w / 2, 10) - dw / 2;
				top = top < 0 ? pos.top : top;
				left = left < 0 ? pos.left : left;
				rOffset = $.ig.util.getRelativeOffset(dialog);
				/* focus dialog so that the dropdown closes (on blur), and the advanced dialog can be closed with the ESCAPE key */
				dialog.focus();
				/* close dropdown */
				/* M.H. 4 Oct 2013 Fix for bug #151434: When filtering is advanced and advancedModeEditorsVisible = true the button for advanced filtering dialog is missing from the feature chooser. */
				if (!(this.options.mode === "advanced" && this.options.advancedModeEditorsVisible === false) &&
						this._currentTarget !== null && this._currentTarget !== undefined) {
					this._toggleDropDown({ currentTarget: this._currentTarget }, true);
				}
			} else {
				event.preventDefault();
				event.stopPropagation();
			}
		},
		_filterDialogOpened: function () {
			var dialog = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog");
			this._trigger(this.events.filterDialogOpened, null, { dialog: dialog, owner: this });
		},
		_closeFilterDialog: function (event) {
			var dialog = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog");
			dialog.igGridModalDialog("closeModalDialog", false);
			event.preventDefault();
		},
		_filterDialogClosing: function (evt, ui) {
			var noCancel = this._trigger(this.events.filterDialogClosing, null, { owner: this });
			if (!noCancel) {
				ui.toClose = false;
				evt.preventDefault();
				evt.stopPropagation();
			} else {
				ui.toClose = true;
				}
		},
		_filterDialogClosed: function (evt, ui) {
			this._trigger(this.events.filterDialogClosed, null, { owner: this });
			if (ui.accepted) {
				this._searchFilterDialog();
			}
		},
		_searchFilterDialog: function () {
			var expressions = [], rows, boolLogic, selectLogic, i, type, exprObj, $row,
				expr, noCancelFiltering = true, cond, col, $input, hrs, mins, sec, ms;

				/* construct filter expressions from table */
			rows = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog").find("[data-af-row]");
			if (!rows.length) {
				rows = this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog table tbody").children();
			}
				boolLogic = "and";
				selectLogic = this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog select:first");
				if (selectLogic.length > 0) {
					boolLogic =
						this.grid._rootContainer()
						.find("#" + this.grid._rootContainer().attr("id") +
						"_dialog select:first")[ 0 ].value === "all" ?
							"AND" : "OR";
				}
				for (i = 0; i < rows.length; i++) {
				$row = $(rows[ i ]);
					/* M.H. 3 June 2015 Fix for bug 192909: Advanced filtering mode in igGrid builds wrong filtering expression */
				$input = this._getDialogColSelByRow($row);
					if ($input.data("value") !== undefined) {
						col = this.grid.columnByKey($input.data("value"));
					} else {
						col = this.grid.columnByText($input[ 0 ].value);
					}
					if (!col) {
						continue;
					}
					type = col.dataType; // get the column type
					expr = this._getDialogExprSelByRow($row).igGridFilterEditor("value");
					/* M.H. 2 Mar 2016 Fix for bug 215135: Filtering by "Ends with" condition from the advanced filtering dialog  when there's no input throws an error */
					expr = (!expr && type === "string") ? "" : expr;
				cond = this._getDialogCondSelByRow($row)[ 0 ].value;
					/* M.H. 1 Apr 2013 Fix for bug #137908: Date filter value is one day behind the selected date */
					if ($.type(expr) === "date") {
						/* M.H. 14 Jan 2014 Fix for bug #161067: Using the after filter condition will leave values from the selected date unfiltered. */
						/* M.H. 14 Sep 2015 Fix for bug 206390: When conditions are changed on the same date valie, filtering results are incorret in advanced filtering mode */
						/* M.H. 18 Sep 2015 Fix for bug 206802: Uncaught TypeError: expr.setHours is not a function occurred when using advanced filter on datetime columns and set filter condition "After" */
						/* when col.format is set to DateTime(longTime etc) then we should not change dateTime object */
						/* TODO: implement filtering in DataSource when filtering is local and should be performed only for dates(exclude hours/mins, secs, milisecs) */
						if (this.options.type === "local" && (!col.format || col.format === "date")) {
							/* when filtering by date(if col.format is not set - it is by date) */
							if (cond === "after") {
								hrs = 23;
								mins = 59;
								sec = 59;
								ms = 999;
							} else {// if (cond === "before")
								/* if filtered by "after" and after that filtering cond is changed to On/Before/etc. hrs, mins, sec, ms are not changed */
								hrs = 0;
								mins = 0;
								sec = 0;
								ms = 0;
							}
						}
						if (this.grid.options.enableUTCDates) {
							if (hrs === undefined) {
								hrs = expr.getUTCHours();
								mins = expr.getUTCMinutes();
							}
							expr = Date.UTC(expr.getUTCFullYear(), expr.getUTCMonth(), expr.getUTCDate(), hrs, mins);
						} else if (hrs !== undefined) {
							expr.setHours(hrs);
							expr.setMinutes(mins);
							expr.setSeconds(sec);
							expr.setMilliseconds(ms);
						}
					}
					exprObj = {
						/* fire user-defined function to extract expression from row */
						fieldName: col.key,
						cond: cond,
						expr: expr,
						logic: boolLogic,
						type: type
					};
					/* M.H. 20 Mar 2015 Fix for bug 190869: Using advanced filtering on unbound combobox-column causes endless loading */
					if (col.unbound && type) {
						exprObj.dataType = type;
					} else if (type === undefined && $.type(expr) === "string") {
						/* M.H. 19 Feb 2014 Fix for bug #162227: Handle Invalid Column Config in igGrid */
						exprObj.dataType = "string";
					}
					expressions.push(exprObj);
				}
				/* when the editors are visible and the mode is advanced, make sure we are not ignoring the filter row (Bug #70099) */
				/*if (this.options.advancedModeEditorsVisible === true && this.options.mode === "advanced") {
					merge the two arrays of expressions
					$.merge(expressions, this._generateExpressions());
				} */
				this._currentAdvancedExpressions = expressions;
				/* there are no column key / index here, because advanced filtering may include many colum filters, it doesn't have to be just one. */
				/* M.H. 14 Jan 2014 Fix for bug #161067: Using the after filter condition will leave values from the selected date unfiltered. */
				/* passing filtering expressions to the dataFiltering event. In this way we will allow users easily to manipulate filtering expressions before databinding */
				noCancelFiltering = this._trigger(this.events.dataFiltering, null,
												{
													owner: this,
													newExpressions: expressions
												});
				if (noCancelFiltering) {
					this.filter(expressions, true, true); // the third parameter denotes that those filters have been added from the advanced window
				}
		},
		_filterDialogStartMove: function () {
			this._isFilterDialogMouseDown = true;
		},
		_filterDialogStopMove: function () {
			this._isFilterDialogMouseDown = false;
			this._dialogClientX = undefined;
			this._dialogClientY = undefined;
		},
		_filterDialogMove: function (e, ui) {
			$(e.target).find(".ui-igedit-fieldincontainer").igGridFilterEditor("hideDropDown");
			/*var dialog = $('#' + this.grid._rootContainer().attr('id') + '_dialog'), left, top, newLeft, newTop, noCancel = true; */
			this._trigger(this.events.filterDialogMoving, null, {
				/* M.H. 22 May 2014 Fix for bug #162225: Allow Users to Position the igGrid Advanced Filter Dialog Anywhere in Browser Window */
				dialog: e.target, owner: this, originalPosition: ui.originalPosition, position: ui.position
			});
		},
		_renderFilterDialog: function () {
			var dialog, condObj, dropDownData, condElement, condDropDown, closeButton, f = this,
				opts = this.options,
				captionsContainer, dialogContent,
				noCancel = true, addClearButtons, limit, container, containment,
				titleLabel = opts.labels.filterDialogCaptionLabel,
				searchButtonLabel = this.options.labels.filterDialogOkLabel,
				cancelButtonLabel = this.options.labels.filterDialogCancelLabel;
			container = this.grid._rootContainer();
			if (this.options.filterDialogContainment === "owner") {
				containment = this.grid._rootContainer();
			} else {
				containment = "window";
			}
			dialog = $("<div></div>")
				.appendTo(container)
				.attr("id", this.grid._rootContainer().attr("id") + "_dialog");
			dialog.igGridModalDialog({
				containment: containment,
				modalDialogCaptionText: titleLabel,
				modalDialogWidth: opts.filterDialogWidth,
				modalDialogHeight: opts.filterDialogHeight,
				buttonApplyText: searchButtonLabel,
				buttonCancelText: cancelButtonLabel,
				/*buttonApplyDisabled: true, */
				gridContainer: this.grid._rootContainer(),
				/*events */
				modalDialogOpening: $.proxy(this._filterDialogOpening, this),
				modalDialogOpened: $.proxy(this._filterDialogOpened, this),
				modalDialogMoving: $.proxy(this._filterDialogMove, this),
				modalDialogClosing: $.proxy(this._filterDialogClosing, this),
				modalDialogClosed: $.proxy(this._filterDialogClosed, this),
				buttonOKClick: $.proxy(this._filterDialogOkClicked, this),
				tabIndex: this._getNextTabIndex()
			});
			captionsContainer = dialog.igGridModalDialog("getCaptionButtonContainer");
			closeButton = $("<span></span>")
							.appendTo($("<a></a>")
							.appendTo(captionsContainer)
							.attr({
								"href": "#",
								"role": "button",
								"title": $.ig.GridFiltering.locale.filterDialogCloseLabel,
								"tabindex": this._getNextTabIndex()
							})
							.addClass("ui-dialog-titlebar-close ui-corner-all"))
							.bind({
					click: $.proxy(this._closeFilterDialog, this)
							})
							.addClass("ui-icon ui-icon-closethick");
			noCancel = this._trigger(this.events.filterDialogContentsRendering, null,
										{
											dialogElement: dialog,
											owner: this
										});
			if (noCancel) {
				dialogContent = dialog.igGridModalDialog("getContent");
				/* add condition dialog */
				condObj = {
					label1: this.options.labels.filterDialogConditionLabel1,
					label2: this.options.labels.filterDialogConditionLabel2
				};
				dropDownData = [
					{ text: this.options.labels.filterDialogAllLabel, value: "all" },
					{ text: this.options.labels.filterDialogAnyLabel, value: "any" }
				];
				if (this.options.filterDialogAddConditionTemplate &&
					typeof this.options.filterDialogAddConditionTemplate === "string") {
					condElement = $(this._fTmplWrappers[ this.grid.id() +
									"_filterDialogAddConditionTemplate" ](condObj));
				} else {
					/* K.D. July 15, 2015 Bug #201576 Adding title to the select. */
					condElement = $("<div><span>" + condObj.label1 +
									"</span><div><select tabindex='" +
									this._getNextTabIndex() +
									"' title='" +
									$.ig.GridFiltering.locale.filterDialogConditionDropDownLabel +
									"'></select></div><span>" + condObj.label2 + "</span></div>");
				}
				condElement.appendTo(dialogContent).addClass(this.css.filterDialogAddCondition);
				if (this.options.filterDialogAddConditionDropDownTemplate &&
					typeof this.options.filterDialogAddConditionDropDownTemplate === "string") {
					condDropDown = $(this._fTmplWrappers[ this.grid.id() +
							"_filterDialogAddConditionDropDownTemplate" ](dropDownData));
				} else {
					condDropDown = $("<option value='" + dropDownData[ 0 ].value + "'>" +
										dropDownData[ 0 ].text + "</option>" +
										"<option value='" + dropDownData[ 1 ].value + "'>" +
										dropDownData[ 1 ].text + "</option>"
					);
				}
				condDropDown.appendTo(condElement.find("div")
									.addClass(this.css.filterDialogAddConditionDropDown)
									.find("select"));
				addClearButtons = $("<div></div>").appendTo(dialogContent);
				/* add filters table */
				$(toStaticHTML("<input type=\"button\"></input>"))
							.attr("tabindex", this._getNextTabIndex())
							.appendTo($("<span></span>")
							.appendTo(addClearButtons)
							.addClass(this.css.filterDialogAddButton))
							.igButton({
					labelText: this.options.labels.filterDialogAddLabel,
					width: this.options.filterDialogAddButtonWidth,
					tabindex: this._getNextTabIndex()
				}).bind({
					click: $.proxy(this._addFilterFromDialog, this)
				});
				/* add Clear All button */
				/* if (this.options.showFilterDialogClearAllButton === true) { */
				$("<button />")
						.attr("tabindex", this._getNextTabIndex())
						.appendTo(addClearButtons)
						.css("float", "right")
						.igButton({
					labelText: this.options.labels.filterDialogClearAllLabel,
					click: $.proxy(this._filterDialogClearAll, this),
					css: {
						"buttonClasses": this.css.filterDialogClearAllButton,
						"buttonHoverClasses": "",
						"buttonActiveClasses": "", //when button is clicked
						"buttonFocusClasses": "", //when button get focus
						"buttonLabelClass": ""
					}
				});
				$("<label></label>")
					.appendTo(dialogContent)
					.attr("id", dialog.attr("id") + "_error")
					.hide()
					.text(this.options.labels.filterDialogErrorLabel)
					.addClass("ui-widget ui-state-error ui-igvalidator-label");
				$("<table><tbody></tbody></table>")
					.appendTo(dialogContent)
					.addClass(this.css.filterDialogFiltersTable);
				/*A.T. 14 Feb 2011 - Fix for #64483 */
				if (this._hc === true) {
					limit = this.grid._rootContainer()
						.find("#" + this.grid.element[ 0 ].id.split("_")[ 0 ] + "_container");
				} else {
					limit = this.grid._rootContainer();
				}
				dialog.bind({
					keydown: function (e) {
						var tabElems, first, last;
						if (e.keyCode === $.ui.keyCode.ESCAPE) {
							f._closeFilterDialog(e);
						}
						/* M.H. 29 June 2015 Fix for bug 186625: When a Feature Chooser is used and one of the dialogs is displayed you can move focus back to the grid (using TAB) */
						if (e.keyCode !== $.ui.keyCode.TAB) {
							return;
						}
						tabElems = $(":tabbable", dialog);
						first = tabElems.first();
						last = tabElems.last();
						if (e.target === last[ 0 ] && !e.shiftKey) {
							first.focus(1);
							return false;
						}
						if (e.target === first[ 0 ] && e.shiftKey) {
							last.focus(1);
							return false;
						}
					},
					drag: function () {
						dialog.find("input[data-af-col]").igTextEditor("hideDropDown");
					}
				});
				dialog.find(".ui-dialog-content").bind({
					scroll: function () {
						dialog.find("input[data-af-col]").igTextEditor("hideDropDown");
					}
				});
				this._trigger(this.events.filterDialogContentsRendered, null,
								{
									dialogElement: dialog,
									owner: this
								});
			}
		},
		_setResizableEnv: function () {
			var initH, e = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog"),
				rCont = e.find(">.ui-dialog-content"),
				hCont = e.find(">.ui-dialog-titlebar"),
				bCont = e.find(">.ui-dialog-buttonpane");
			if (!this._resizeCont) {
				this._resizeCont = rCont;
				initH = e.height() - hCont.outerHeight() -
					(rCont.outerHeight() - rCont.height()) - bCont.outerHeight();
				this._setFilterDialogContentHeight(initH);
			}
			this._resizeCont = rCont;
			/* M.H. 1 Dec 2014 Fix for bug #185815: Filter dialog window changes its height as its width changes */
			if (e[ 0 ].style.height === "") {
				e.css("height", e.height());
			}
			this._trigger(this.events.filterDialogOpened, null, { dialog: e, owner: this });
		},
		_resizeHandler: function (event, ui) {
			var u = ui.originalElement, heightDifference,
				e = this.grid._rootContainer().find("#" + this.grid._rootContainer().attr("id") + "_dialog"),
				rCont = e.find(">.ui-dialog-content"),
				hCont = e.find(">.ui-dialog-titlebar"),
				bCont = e.find(">.ui-dialog-buttonpane"),
				pad = rCont.outerHeight() - rCont.height();

			/*M.K. 4/2/2015 Fix for bug 186204: After resizing the Filter dialog window the second button in the dialog is not visible. */
			heightDifference = hCont.outerHeight(true) + bCont.outerHeight(true) + pad;
			this._setFilterDialogContentHeight(u.height() - heightDifference);
		},
		_setFilterDialogContentHeight: function (height) {
			this._resizeCont.css({
				height: height + "px"
			});
		},
		_changeFilterCondition: function (e) {
			/* if the condition does not require an entry, set the value in the third dropdown and disable it */
			var editor, condition, $target = $(e.target),
				$row = $target.closest("[data-af-row]");
			if (!$row.length) {
				$row = $target.closest("tr");
			}
			condition = e.target.value;
			editor = this._getDialogExprSelByRow($row);
			this._setFilterDialogInput(editor, condition);
		},
		_setFilterDialogInput: function (editor, condition) {
			var editorObject, requiresEntry = this.requiresFilteringExpression(condition);
			if (!requiresEntry) {
				editor.igGridFilterEditor("option", "readOnly", true);
				editorObject = editor.data(editor.data("editorType"));
				/* set correct input value if it doesn't require an entry */
				this._editorValueForCondition(condition, editorObject);
			} else {
				editor.igGridFilterEditor("option", "readOnly", false);
			}
		},
		/* Advanced filter dialog element selectors by row */
		_getDialogColSelByRow: function ($frow) {
			/* get column selector field(by default it is dropdown) */
			var $elem = $frow.find("[data-af-col]");
			if (!$elem.length) {
				$elem = $frow.find("td:first").find("input").first();
			}
			return $elem;
		},
		_getDialogCondSelByRow: function ($frow) {
			/* get filtering condition field(by default it is dropdown) */
			var $elem = $frow.find("[data-af-cond]");
			if (!$elem.length) {
				$elem = $frow.find("td:nth-child(2)").find("select").first();
			}
			return $elem;
		},
		_getDialogExprSelByRow: function ($frow) {
			/* get filtering expression field(by default it is textbox) */
			var $elem = $frow.find("[data-af-expr]");
			if (!$elem.length) {
				$elem = $frow.find("td:nth-child(3)").find("input").first();
			}
			return $elem;
		},
		_getDialogRmvBtnByRow: function ($frow) {
			/* get button which removes filtering row */
			var $elem = $frow.find("[data-af-rmv]");
			if (!$elem.length) {
				$elem = $frow.find("td:last").find("span");
			}
			return $elem;
		},
		_getNextTabIndex: function () {
			var gti = this.grid.options.tabIndex;
			return gti + 1;
		},
		/* //Advanced filter dialog element selectors by row */
		_addFilterFromDialog: function (e, expression) {
			var tableBody = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog table tbody"),
				cs = this.options.columnSettings, rows,
				filterObj = {}, filterRow, colKeys, i, noCancel = true, field, type, cnd,
				 defaultValue, condition, col, conds, shouldDisable = false, input,
				 editorWidth = this.options.filterDialogColumnDropDownDefaultWidth || 120;
			noCancel = this._trigger(this.events.filterDialogFilterAdding, null,
									{
										filtersTableBody: tableBody,
										owner: this
									});
			if (noCancel) {
				rows = this.grid._rootContainer().find("#" + this.grid._rootContainer().attr("id") + "_dialog")
							.find("[data-af-row]");
				if (!rows.length) {
					rows = tableBody.children();
				}
				if (rows.length >= this.options.filterDialogMaxFilterCount) {
					/* use the validation framework to validate the button? */
					this.grid._rootContainer().find("#" + this.grid._rootContainer()
							.attr("id") + "_dialog_error")
							.show();
					return;
				}
				/* L.A. 10 May 2012 Fixing bug #110975 */
				if (expression === undefined) {
					/* M.H. 30 May 2014 Fix for bug #172388: Date filter editor doesn't take into account grid's column format */
					col = this.grid.columnByKey(this._dialogCurrentColumn);
					field = col.headerText;
					if (this._dialogCurrentType === "object") {
						this._dialogCurrentType = this._getColType(this._dialogCurrentColumn);
					}
					type = this._dialogCurrentType;
				} else {
					col = this.grid.columnByKey(expression.fieldName);
					if (col !== null) {
						field = col.headerText;
						if (col.dataType === "object") {
							type = this._getColType(expression.fieldName);
						} else {
							type = col.dataType;
						}
					}
					/*	M.W.H. Fix for bug #204763: When custom filter is set as default its text is not in the editor. */
					condition = this._resolveConditionNameFromExpression(expression);
					defaultValue = expression.expr;
					/* M.H. 20 May 2013 Fix for bug #142681: When enableUTCDates is set to true and the Filtering is advanced after filtering a date column the value in the date picker is removed the second time you open the dialog. */
					if (type === "date" && $.type(defaultValue) === "number" && this.grid.options.enableUTCDates) {
						defaultValue = new Date(defaultValue);
					}
				}

				/* fire filter adding event */
				if (this.options.filterDialogFilterTemplate &&
					typeof this.options.filterDialogFilterTemplate === "string") {
					filterRow = $(this._fTmplWrappers[ this.grid.id() +
									"_filterDialogFilterTemplate" ](filterObj));
				} else {
					filterRow = $("<tr data-af-row><td><input data-af-col/></td>" +
								"<td><select data-af-cond tabindex='" +
								this._getNextTabIndex() + "' title='" +
								$.ig.GridFiltering.locale.filterDialogConditionDropDownLabel +
								"'></select></td><td><input data-af-expr /> " +
								"</td><td><span data-af-rmv></span></td></tr>");
				}
				filterRow.appendTo(tableBody);
				/*filterRow = $.tmpl(this.options.filterDialogFilterTemplate, filterObj).appendTo(tableBody).hide(); */
				/* instantiate editors and fill in values */
				/* 1. list of columns */
				colKeys = [];

				for (i = 0; i < cs.length; i++) {
					if (cs[ i ].columnKey === col.key &&
						cs[ i ].allowFiltering === false) {
						/*if current filtering column does not allow filtering, disable all editors for it. */
						shouldDisable = true;
					} else if (cs[ i ].allowFiltering === true) {
						colKeys.push(this.grid.columnByKey(cs[ i ].columnKey).headerText);
					}

				}
				/* M.H. 1 Dec 2014 Fix for bug #185893: Condition drop down widget is missing when using Safari and filtering */
				/* only on iPad2(Safari) select could not be found if igEditor is instantiated */
				cnd = this._getDialogCondSelByRow(filterRow);
				/* column selector */
				this._getDialogColSelByRow(filterRow).igTextEditor({
					listItems: colKeys,
					disabled: shouldDisable,
					button: "dropdown",
					isLimitedToListValues: true,
					allowNullValue: false,
					buttonType: "dropdown",
					width: editorWidth,
					valueChanged: $.proxy(this._polulateFilterConditionDropDown, this),
					dropDownAttachedToBody: true,
					/*value: this.grid.columnByKey(field).headerText */
					/* M.H. 3 June 2015 Fix for bug 192909: Advanced filtering mode in igGrid builds wrong filtering expression */
					value: field,
					tabIndex: this._getNextTabIndex(),
					validatorOptions: {
						notificationOptions:
							{
								appendTo: "#" + this.grid._rootContainer().attr("id") + "_dialog",
								maxWidth: editorWidth
							}
					}
				});
				/* 2. conditions for the respective column will be populated later dynamically, when the column is changed */
				conds = this._populateConditionsList(col.key, this._getColType(col.key));
				this._renderAdvancedConditionsDropDown(cnd, conds);
				/* M.H. 2 Apr 2013 Fix for bug #138403: The condition of the applied expression in the advanced dialog is not persisted after you open the dialog for the second time. */
				if (condition !== undefined && cnd.length > 0) {
					cnd[ 0 ].value = condition;
				}
				cnd.attr("disabled", shouldDisable);
				/* attach a handler to the conditions list for value change */
				cnd.bind("change", $.proxy(this._changeFilterCondition, this));
				this._getDialogCondSelByRow(filterRow)
					.css("width", this.options.filterDialogFilterDropDownDefaultWidth);
				input = this._getDialogExprSelByRow(filterRow);
				this._populateFilterDialogInput(this._getDialogExprSelByRow(filterRow),
													type,
													defaultValue, col, condition, shouldDisable);
				input.attr("disabled", shouldDisable);
				/* M.H. 4 Dec 2015 Fix for bug 210323: When applying a boolean filter "true" from dialog in igGrid filtering expression returns null */
				if (cnd.length && !this.requiresFilteringExpression(condition)) {
					this._changeFilterCondition({ target: cnd[ 0 ] });
				}
				/*3. remove button for the filter */
				this._getDialogRmvBtnByRow(filterRow)
						.attr("tabindex", this._getNextTabIndex())
						.addClass(this.css.filterDialogFilterRemoveButton)
						.bind({
					mousedown: $.proxy(this._removeFilterFromDialog, this)
				});
				/* fire filter added */
				this._trigger(this.events.filterDialogFilterAdded, null, {
					filter: filterRow,
					owner: this
				});
			}
		},
		_filterDialogClearAll: function (e) {
			/* events? */
			var tableBody = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog table tbody");
			tableBody.empty();
			/*A.T. 12 Feb 2011 - Fix for bug #64538 */
			/* clear validation error message if any */
			this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog_error").hide();
			if (e) {
				e.preventDefault();
			}
		},
		_polulateFilterConditionDropDown: function (e, args) {
			var selectedColKey = args.newValue, conditionListElem,
				cols = this.grid.options.columns, type,
				input, i, col, conds, $tr, $target = $(e.target);
			$tr = $target.closest("[data-af-row]");
			if (!$tr.length) {
				$tr = $target.closest("tr");
			}
			/* find the column, and populate the conditions based on the type */
			conditionListElem = this._getDialogCondSelByRow($tr);
			input = this._getDialogExprSelByRow($tr);
			/* M.H. 3 June 2015 Fix for bug 192909: Advanced filtering mode in igGrid builds wrong filtering expression */
			if ($(e.target).data("value") !== undefined) {
				col = this.grid.columnByKey($(e.target).data("value"));
				if (col) {
					type = col.dataType;
				}
			} else {
				for (i = 0; i < cols.length; i++) {
					if (cols[ i ].headerText === selectedColKey) {
						type = cols[ i ].dataType;
						col = cols[ i ];
						break;
					}
				}
			}

			//if type is object, check if we have a mapped value
			if (type === "object") {
				type = this._getColType(col.key);
			}
			/* no full match */
			if (type === undefined) {
				return;
			}
			conds = this._populateConditionsList(col.key, type);
			this._renderAdvancedConditionsDropDown(conditionListElem, conds);
			this._populateFilterDialogInput(input, type, undefined,
											col, conds[ 0 ].condition);
		},
		_populateFilterDialogInput: function (input, type, defaultValue, col, condition) {
			var edtrT = this._getEditorNameByColType(type),
				options = {
					suppressNotifications: true,
					tabIndex: this._getNextTabIndex(),
				buttonHidden: type !== "date",
				button: "dropdown",
				readOnly: type === "bool",
				maxDecimals: 12,
				textAlign: (type === "number") ? "right" : "left",
				width: this.options.filterDialogExprInputDefaultWidth,
				value: defaultValue,
				/*_utc: true */
				enableUTCDates: this.options.type === "remote" ?
									true :
									this.grid.options.enableUTCDates
			};
			/* M.H. 30 May 2014 Fix for bug #172388: Date filter editor doesn't take into account grid's column format */
			if (type === "date" && col && col.format) {
				options.dateInputFormat = col.format;
			}
			/* do not allow to create same igEditor for different elements. First time it is INPUT, but later it can be SPAN or INPUT */
			if (!input.is("input")) {
				input = input.find("INPUT").eq(0);
			}
			/* remove old editor and re-create new editor */
			if (input.data("editorType")) {
				input.igGridFilterEditor("destroy");
			}
			input.data("editorType", edtrT);
			input.igGridFilterEditor(options);
			/*	M.W.H. Fix for bug #204736: When the mode is advanced and requireExpr is false you are able to write in the editor the second time you open the filtering dialog. */
			if (condition) {
				this._setFilterDialogInput(input, condition);
			}
		},
		_populateConditionsList: function (selectedColKey, type) {
			var conditions = [], i, j, labels = this.options.labels,
				cs = this.options.columnSettings, css = this.css, validConditions = [],
				matchingCondition, custConds, curCond, newCond, key;
			if (type === "number") {
				validConditions.push({
					condition: "equals",
					text: labels.equals,
					imgClass: css.filterItemIconEquals
				});
				validConditions.push({
					condition: "doesNotEqual",
					text: labels.doesNotEqual,
					imgClass: css.filterItemIconDoesNotEqual
				});
				validConditions.push({
					condition: "greaterThan",
					text: labels.greaterThan,
					imgClass: css.filterItemIconGreaterThan
				});
				validConditions.push({
					condition: "lessThan",
					text: labels.lessThan,
					imgClass: css.filterItemIconLessThan
				});
				validConditions.push({
					condition: "greaterThanOrEqualTo",
					text: labels.greaterThanOrEqualTo,
					imgClass: css.filterItemIconGreaterThanOrEqualTo
				});
				validConditions.push({
					condition: "lessThanOrEqualTo",
					text: labels.lessThanOrEqualTo,
					imgClass: css.filterItemIconLessThanOrEqualTo
				});
			} else if (type === "bool" || type === "boolean") {
				validConditions.push({
					condition: "true",
					text: labels.trueLabel,
					imgClass: css.filterItemIconTrue
				});
				validConditions.push({
					condition: "false",
					text: labels.falseLabel,
					imgClass: css.filterItemIconFalse
				});
			} else if (type === "date") {
				validConditions.push({
					condition: "on", text: labels.on, imgClass: css.filterItemIconOn
				});
				validConditions.push({
					condition: "notOn", text: labels.notOn, imgClass: css.filterItemIconNotOn
				});
				validConditions.push({
					condition: "after", text: labels.after, imgClass: css.filterItemIconAfter
				});
				validConditions.push({
					condition: "before", text: labels.before, imgClass: css.filterItemIconBefore
				});
				validConditions.push({
					condition: "today", text: labels.today, imgClass: css.filterItemIconToday
				});
				validConditions.push({
					condition: "yesterday", text: labels.yesterday, imgClass: css.filterItemIconYesterday
				});
				validConditions.push({
					condition: "thisMonth", text: labels.thisMonth, imgClass: css.filterItemIconThisMonth
				});
				validConditions.push({
					condition: "lastMonth", text: labels.lastMonth, imgClass: css.filterItemIconLastMonth
				});
				validConditions.push({
					condition: "nextMonth", text: labels.nextMonth, imgClass: css.filterItemIconNextMonth
				});
				validConditions.push({
					condition: "thisYear", text: labels.thisYear, imgClass: css.filterItemIconThisYear
				});
				validConditions.push({
					condition: "lastYear", text: labels.lastYear, imgClass: css.filterItemIconLastYear
				});
				validConditions.push({
					condition: "nextYear", text: labels.nextYear, imgClass: css.filterItemIconNextYear
				});
			} else if ( type === "string") { // string
				validConditions.push({
					condition: "startsWith", text: labels.startsWith, imgClass: css.filterItemIconStartsWith
				});
				validConditions.push({
					condition: "endsWith", text: labels.endsWith, imgClass: css.filterItemIconEndsWith
				});
				validConditions.push({
					condition: "contains", text: labels.contains, imgClass: css.filterItemIconContains
				});
				validConditions.push({
					condition: "doesNotContain",
					text: labels.doesNotContain,
					imgClass: css.filterItemIconDoesNotContain
				});
				validConditions.push({
					condition: "equals", text: labels.equals, imgClass: css.filterItemIconEquals
				});
				validConditions.push({
					condition: "doesNotEqual", text: labels.doesNotEqual, imgClass: css.filterItemIconDoesNotEqual
				});
			}
			if (this.options.showEmptyConditions) {
				validConditions.push({
					condition: "empty", text: labels.empty, imgClass: css.filterItemIcon
				});
				validConditions.push({
					condition: "notEmpty", text: labels.notEmpty, imgClass: css.filterItemIcon
				});
			}
			if (this.options.showNullConditions) {
				validConditions.push({
					condition: "null", text: labels.nullLabel, imgClass: css.filterItemIcon
				});
				validConditions.push({
					condition: "notNull", text: labels.notNull, imgClass: css.filterItemIcon
				});
			}
			for (i = 0; i < cs.length; ++i) {
				if (cs[ i ].columnKey === selectedColKey) {
					cs = cs[ i ];
					break;
				}
			}
			/*	Include any custom conditions for this column. */
			custConds = cs.customConditions;
			for (key in custConds) {
				if (custConds.hasOwnProperty(key)) {
					curCond = custConds[ key ];
					newCond = {
						condition: cs.columnKey + "_" + key,	//	Transform custom conditions so the dataSource knows which one to use.
						text: curCond.labelText,
						imgClass: curCond.filterImgIcon
					};
					validConditions.push(newCond);
				}
			}

			if (cs.conditionList && cs.conditionList.length > 0) {
				/*	Loop through the supplied conditionList */
				for (i = 0; i < cs.conditionList.length; ++i) {
					matchingCondition = null;
					for (j = 0; j < validConditions.length; ++j) {
						if (validConditions[ j ].condition === cs.conditionList[ i ] ||
							validConditions[ j ].condition === cs.columnKey + "_" + cs.conditionList[ i ]) {
							matchingCondition = validConditions[ j ];
							break;
						}
					}
					if (!matchingCondition) {
						throw new Error($.ig.util.stringFormat(
							$.ig.GridFiltering.locale.conditionNotValidForColumnType,
							cs.conditionList[ i ], type));
					}
					conditions.push(matchingCondition);
				}
			} else {
				for (i = 0; i < validConditions.length; ++i) {
					conditions.push(validConditions[ i ]);
				}
			}

			return conditions;
		},
		_renderAdvancedConditionsDropDown: function (conditionListElem, conditions) {
			var i, condList = "";

			/* clear the list from existing conditions */
			conditionListElem.empty();
			/* reset selected value */
			if (this.options.filterDialogFilterConditionTemplate &&
				typeof this.options.filterDialogFilterConditionTemplate === "string") {
				condList = $(this._fTmplWrappers[ this.grid.id() +
								"_filterDialogFilterConditionTemplate" ](conditions));
			} else {
				for (i = 0; i < conditions.length; i++) {
					condList += "<option value='" + conditions[ i ].condition + "'>" +
								conditions[ i ].text + "</option>";
				}
				condList = $(condList);
			}
			condList.appendTo(conditionListElem);
			/*$.tmpl(this.options.filterDialogFilterConditionTemplate, conditions).appendTo(conditionList); */
			/* IE9 has a pretty obvious bug, we need this hack to handle that case */
			if ($.ig.util.isIE) {
				conditionListElem.parent().append(conditionListElem);
				conditionListElem.width(conditionListElem.width());
			}
		},
		_removeFilterFromDialog: function (e) {
			/* remove filter row */
			var filterR, $target = $(e.target),
				$tr = $target.closest("[data-af-row]");
			if (!$tr.length) {
				$target.closest("tr").remove();
				filterR = this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog").find("tr");
			} else {
				$tr.remove();
				filterR = this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog")
					.find("[data-af-row]");
			}
			/* hide the error message if it is shown */
			if (filterR.length < this.options.filterDialogMaxFilterCount) {
				this.grid._rootContainer()
					.find("#" + this.grid._rootContainer().attr("id") + "_dialog_error").hide();
			}
		},
		_renderDropDownToList: function (obj, list, ul) {
			var i, cond;
			/* the template is: "<li class='${itemClass}'><span class='${imgContainerClass}'><span class='${imgClass}'></span></span><span class='${textClass}'> ${text} </span></li>" */
			for (i = 0; i < list.length; i++) {
				cond = $("<li class='" + obj.itemClass + "'><span class='" + obj.imgContainerClass + "'>" +
					"<span class='" + this.css.filterItemIcon + " " + list[ i ].imgClass + "'></span></span>" +
					"<span class='" + obj.textClass + "'> " + list[ i ].text + "</span></li>");
				cond.appendTo(ul).data("cond", list[ i ].condition);
			}
		},
		/* append dropdowns as children of the grid container */
		_renderDropDownElement: function (id, colName) {
			var ul, container;
			ul = $("<ul></ul>").appendTo(this.grid._rootContainer()).addClass(this.css.filterDropDownList);
			container = $("<div></div>")
				.appendTo(this.grid._rootContainer())
				.attr("id", id)
				.addClass(this.css.filterDropDown)
				.css("overflow", "hidden")
				.css("position", "absolute")
				.hide();
			if (!$.ig.util.isIE) {
				container.css("overflow-y", "auto");
			}
			container.remove();
			if (this.options.filterDropDownWidth > 0) {
				ul.css("width", this.options.filterDropDownWidth);
			}
			if (this.options.filterDropDownHeight > 0) {
				container.css("height", this.options.filterDropDownHeight);
			}
			ul.data("colName", colName);
			ul.wrap(container);
			/* mark it with the "efh" attribute - "excludeFromHeight" */
			ul.parent().data("efh", "1");
			/* attach events */
			this.grid._rootContainer().find("#" + id).delegate("li", {
				mousedown: $.proxy(this._selectDropDownItem, this),
				mouseover: $.proxy(this._hoverDropDownItem, this),
				mouseout: $.proxy(this._unhoverDropDownItem, this)
			});
			return ul;
		},
		_hoverButton: function (event) {
			var $target  = $(event.currentTarget).find("span");
			if (this.options.mode === "advanced") {
				$target.addClass(this.css.filterButtonAdvancedHover);
			} else {
				$target.addClass(this.css.filterButtonHover);
			}
		},
		_unhoverButton: function (event) {
			var $target  = $(event.currentTarget).find("span");
			if (this.options.mode === "advanced") {
				$target.removeClass(this.css.filterButtonAdvancedHover);
			} else {
				$target.removeClass(this.css.filterButtonHover);
			}
		},
		_activateButton: function (event) {
			var $target = $(event.currentTarget).find("span");
			if (this._dontApplyStyles) {
				this._dontApplyStyles = false;
				return;
			}
			if (this.options.mode === "advanced") {
				$target.addClass(this.css.filterButtonAdvancedActive);
			} else {
				$target.addClass(this.css.filterButtonActive);
			}
		},
		_deactivateButton: function (event) {
			var target = $(event.target).find("span");
			target.removeClass(this.css.filterButtonAdvancedActive);
			target.removeClass(this.css.filterButtonActive);
		},
		_toggleDropDown: function (event, close, dontFocus) {
			var $target = $(event.currentTarget), id = $target.attr("id"), maxZ,
				dd, left, top, noCancel = true, button = $target.find("span"), rOffset,
				block = this.grid._rootContainer().find("#" + this.grid._rootContainer().attr("id") + "_block"),
				dialog = this.grid._rootContainer()
				.find("#" + this.grid._rootContainer().attr("id") + "_dialog");
			/* M.H. 19 Sep 2016 Fix for bug 225655: When renderFilterButton is false clicking on the input element of a boolen column throws an error */
			if (this._animating && id === this._animatingId || !id) {
				return;
			}
			/* L.A. 18 October 2012 - Fixing bug #123537 */
			/* IE 7: When jQuery dialog with igGrid in it is opened for the second time filter containers are shown behind dialog */
			/* Using // M.H. 10 July 2012 Fix for bug #106407 */
			maxZ = $.ig.getMaxZIndex(this.grid._rootContainer().attr("id") + "_dialog");
			block.css("zIndex", maxZ + 1);
			dialog.css("zIndex", maxZ + 2);
			/* find the dropdown */
			dd = this.grid._rootContainer().find("#" + id.substring(0, id.lastIndexOf("_button")));
			if (dd.find("ul > li").length === 0) {
				/*there are no conditions to show. */
				return;
			}
			/* M.H. 1 Nov 2012 Fix for bug #123537 */
			dd.css("zIndex", maxZ + 3);

			if (!(!dd.is(":visible") && close === true)) {
				this._animating = true;
				this._animatingId = id;
			}

			if (this._openingAnimation !== true) {
				this._dontFocus = dontFocus;
			} else {
				this._dontFocus = null;
			}
			this._isClosing = false;
			/* trigger dropdown opening / closing */
			if (dd.is(":visible")) {
				/* trigger closing */
				noCancel = this._trigger(this.events.dropDownClosing, null, {
					dropDown: dd, owner: this
				});
				this._isClosing = true;
			} else {
				/* trigger opening */
				if (!close) {
					noCancel = this._trigger(this.events.dropDownOpening, null, {
						dropDown: dd, owner: this
					});
				}
			}
			if (noCancel) {
				/* looks like this issue is fixed in FF4, therefore i am not going to */
				/*if (!$.ig.util.isWebKit && !$.ig.util.isIE) {
					leftScroll = $(document).scrollLeft();
					topScroll = $(document).scrollTop();
				}*/
				left = $.ig.util.offset(button).left;
				top = $.ig.util.offset(button).top + button.outerHeight();
				/* account for body padding */
				/*left = left + parseInt($('body').css('margin-left'), 10) + parseInt($('body').css('padding-left'), 10); */
				/*top = top + parseInt($('body').css('margin-top'), 10) + parseInt($('body').css('padding-top'), 10); */
				/* finally, show the dropdown, if it is hidden, or hide it alternatively */
				if (dd.offset().left !== left && dd.offset().top !== top) {
					rOffset = $.ig.util.getRelativeOffset(dd);
					left -= rOffset.left;
					top -= rOffset.top;
					dd.css("left", left).css("top", top);
				}
				this._currentTarget = $target;
				this._dd = dd;
				if (!this._isClosing) {
					this._opendd = dd;
				}
				if ($.ig.util.isIE) {
					dd.css("overflow-x", "hidden");
					dd.css("overflow-y", "hidden");
				}
				if (close !== undefined && close === true) {
					if (dd.is(":visible")) {
						if (this.options.filterDropDownAnimations !== "none") {
							dd.hide(this.options.filterDropDownAnimationDuration,
									$.proxy(this._animationEnd, this));
						} else {
							dd.hide();
							/* trigger closed event */
							this._trigger(this.events.dropDownClosed, null,
											{
												dropDown: dd,
												owner: this
											});
						}
					}
				} else {
					if (this.options.filterDropDownAnimations !== "none") {
						if (dd.is(":visible")) {
							this._closingTarget = this._currentTarget;
						} else {
							this._openingTarget = this._currentTarget;
							this._openingAnimation = true;
						}
						dd.toggle(this.options.filterDropDownAnimationDuration, $.proxy(this._animationEnd, this));
					} else {
						dd.toggle();
						/* trigger closed/opened events */
						if (this._isClosing) {
							this._trigger(this.events.dropDownClosed, null, { dropDown: dd, owner: this });
						} else {
							this._trigger(this.events.dropDownOpened, null, { dropDown: dd, owner: this });
						}
						/* we need to focus the button manually */
						if (dontFocus !== true) {
							this._dontApplyStyles = true;
							$target.focus();
						}
					}
				}
			}
			if ($.type(event.stopPropagation) === "function") {
				event.stopPropagation();
			}
		},
		_animationEnd: function () {
			/* M.H. 21 Mar 2015 Fix for bug 190834: Uncaught TypeError is thrown when grid is destroyed while filter dialog opens */
			if (this.options === null) {
				return;
			}
			if (this._isClosing === true) {
				this._trigger(this.events.dropDownClosed, null, { dropDown: this._dd, owner: this });
			} else {
				this._trigger(this.events.dropDownOpened, null, { dropDown: this._opendd, owner: this });
				this._openingAnimation = false;
			}
			if (this._dd && $.ig.util.isIE) {
				this._dd.css("overflow-y", "auto");
			}
			this._isClosing = null;
			/* we need to focus the button manually */
			if (this._dontFocus !== true) {
				this._dontApplyStyles = true;
				this._openingTarget.focus();
			}
			this._animating = false;
		},
		_toggleDropDownKeyboard: function (event) {
			var parent, selectedItem, $target = $(event.target), ddVisible;
			if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
				event.stopPropagation();
				event.preventDefault();
				parent = this.grid._rootContainer()
					.find("#" + $target.attr("id").substring(0, $target.attr("id")
					.lastIndexOf("_button")))
					.find("ul");
				if (parent.data("selectedItem") ||
					parent.data("selectedItem") === 0) {
					selectedItem = parent.find("li:nth-child(" + (parent.data("selectedItem") + 1) + ")");
					ddVisible = this.grid._rootContainer().find("#" + $target.attr("id")
									.substring(0, $target.attr("id").lastIndexOf("_button")))
									.is(":visible");
					if ($(selectedItem).hasClass("ui-iggrid-filterddlistitemadvanced") && ddVisible) {
						/* open advanced filter dialog */
						this._openFilterDialog(event);
					}
				}
				/*M.K. fix for bug 200936 :Using the keyboard to navigate filter dropdown options would instead trigger default editor behavior and hide the dropdown */
				/*Apply filtering only when Enter/Space is hit while an item from the dropdown is selected */
				if (selectedItem && ddVisible) {
					event.currentTarget = selectedItem;
					this._selectDropDownItem(event);
				} else {
					this._toggleDropDown(event);
				}
			} else if (event.keyCode === $.ui.keyCode.DOWN) {
				event.stopPropagation();
				event.preventDefault();
				this._selectDropDownItem(event, "next");
			} else if (event.keyCode === $.ui.keyCode.UP) {
				event.stopPropagation();
				event.preventDefault();
				this._selectDropDownItem(event, "prev");
			}
		},
		_closeDropDown: function (event) {
			var $target = $(event.currentTarget).find("span"),
				$targetButton = $(event.currentTarget), dd;
			if (this._openingTarget &&
				event.target.id === this._openingTarget.attr("id") &&
				this._openingAnimation === true) {
				return;
			}
			dd = this.grid._rootContainer().find("#" + $targetButton.attr("id")
						.substring(0, $targetButton.attr("id").lastIndexOf("_button")));
			if (dd && !dd.is(":visible")) {
				/* remove focus styles */
				$target.removeClass(this.css.filterButtonActive);
				return; // no need to close the dropdown if it has been already closed
			}
			this._toggleDropDown(event, true, true);
			if (this._dontApplyStyles) {
				this._dontApplyStyles = false;
				return;
			}
			if (this.options.mode === "advanced") {
				$target.removeClass(this.css.filterButtonAdvancedActive);
			} else {
				$target.removeClass(this.css.filterButtonActive);
			}
		},
		_getColType: function (colkey) {
			var type = this.grid.dataSource._getFieldTypeFromSchema(colkey);
			if (type === undefined) {
				type = this.grid.columnByKey(colkey).dataType;
			}
			return type;
		},
		_selectDropDownItem: function (event, nav, expr) {
			var $target = $(event.currentTarget), filterCondition, colIndex, sel, parent, requiresEntry,
				button, currentCond, targetParent, evtArgs, focT, type;
			if ($target.find("input").length > 0 && this.options.mode === "advanced") {
				return;
			}
			if (nav === "next" || nav === "prev") {
				parent = this.grid._rootContainer().find("#" + $target.attr("id")
							.substring(0, $target.attr("id").lastIndexOf("_button")))
						.find("ul");
			}
			if (nav === "next") {
				/* get the currently selected item */
				if (parent.data("selectedItem") === undefined) {
					$target = parent.find("li:first");
				} else {
					$target = parent.find("li:nth-child(" + (parent.data("selectedItem") + 1) + ")");
					if ($target.next().length === 0) {
					/* last, get the first */
						$target = parent.find("li:first");
					} else {
						$target = $target.next();
					}
				}
			}
			if (nav === "prev") {
				/* get the currently selected item */
				if (parent.data("selectedItem") === undefined) {
					$target = parent.find("li:last");
				} else {
					$target = parent.find("li:nth-child(" + (parent.data("selectedItem") + 1) + ")");
					if ($target.prev().length === 0) {
						/* last, get the first */
						$target = parent.find("li:last");
					} else {
						$target = $target.prev();
					}
				}
			}
			filterCondition = $target.data("cond");
			/* update tooltip */
			/* M.H. 23 Mar 2015 Fix for bug 190769: Filter condition icon tooltip is misleading */
			button = this.grid._rootContainer().find("#" + $target.closest("div").attr("id") + "_button");
			button.attr("title",
						this.options.tooltipTemplate
							.replace("${condition}", this.options.labels.noFilter));
			if ($target.data("cond") === undefined) {
				button.attr("title",
						this.options.tooltipTemplate
							.replace("${condition}", this.options.labels.noFilter));
			}
			colIndex = this.grid._rootContainer().find("#" + $target.closest("div").attr("id") + "_button")
							.find("span").data("colIndex");
			/* L.A. 29 October 2012 - Fixing bug #124166 - Filter client API method doesn't rebind the grid */
			if (!colIndex && expr) {
				$.each(this.options.columnSettings, function (index, col) {
					if (col.columnKey === expr.fieldName) {
						colIndex = index;
						return false;
					}
				});
			}
			currentCond = this.options.columnSettings[ parseInt(colIndex, 10) ].condition;
			/*M.K. Fix for bug #187533: Filter placeholder text disappears after filter is cleared */
			targetParent = $target.parent();
			sel = targetParent.data("selectedItem");
			type = this._getColType(this.grid.options.columns[ colIndex ].key);
			requiresEntry = this.requiresFilteringExpression(filterCondition);
			if (filterCondition !== undefined) {
				/* change the editor condition */
				this.options.columnSettings[ parseInt(colIndex, 10) ].condition = filterCondition;
				/* apply active state on target */
				if (sel !== undefined) {
					targetParent
						.find("li:nth-child(" + (sel + 1) + ")")
						.removeClass(this.css.filterDropDownListItemActive);
				}
				$target.addClass(this.css.filterDropDownListItemActive);
				targetParent.data("selectedItem", $("li", targetParent).index($target));
				/* reinitialize editor */
				this._editors[ colIndex ]
					.element.igGridFilterEditor("option", "placeHolder",
							this.options.nullTexts[ filterCondition ]);
			} else if (currentCond === "empty" ||
						currentCond === "notEmpty" ||
						currentCond === "null" ||
						currentCond === "notNull" ||
						!this.requiresFilteringExpression(currentCond)) {
				/* should not be null but the default condition ! */
				this.options.columnSettings[ parseInt(colIndex, 10) ].condition =
						this._getDefaultCondition(type);
				/*clear selection */
				if (sel !== undefined) {
					targetParent
						.find("li:nth-child(" + (sel + 1) + ")")
						.removeClass(this.css.filterDropDownListItemActive);
					targetParent.data("selectedItem", null);
					/*	M.W.H. Fix for bug #204724: When requireExpr is false and you clear a custom filter for column the text in the editor is not correct. */
					/*if (this.grid.options.columns[colIndex].dataType !== "bool") {
						this._editors[colIndex].element.igGridFilterEditor("option", "placeHolder", this._getDefaultCondition(this.grid.options.columns[colIndex].dataType));
					} else { */
					this._editors[ colIndex ]
						.element
						.igGridFilterEditor("option", "placeHolder",
							this.options.nullTexts[ this._getDefaultCondition(type) ]);
					/*}*/
				}
				/* M.H. 11 Mar 2013 Fix for bug #134207: null condition label is misleading after clearing a Null filter */
				/* M.H. 7 Nov 2013 Fix for bug #157107: Date column preset filter nullText is retained after the filter is cleared */
				if (currentCond === "null" ||
					this.grid.options.columns[ colIndex ].dataType === "date") {
					this._editors[ colIndex ]
						.element
						.igGridFilterEditor("option", "placeHolder",
							this.options.nullTexts[ this._getDefaultCondition(type) ]);
				}
			} else {
				/*Clear filter is selected */
				if (sel !== undefined) {
					targetParent
						.find("li:nth-child(" + (sel + 1) + ")")
						.removeClass(this.css.filterDropDownListItemActive);
				}
				$target.addClass(this.css.filterDropDownListItemActive);
				targetParent.data("selectedItem", $("li", targetParent).index($target));
			}
			if (nav === undefined ||
				event.keyCode === $.ui.keyCode.ENTER ||
				event.keyCode === $.ui.keyCode.SPACE) {
				/* we should always clear the filter after a new one has been selected */
				if ($target.hasClass("ui-iggrid-filterddlistitemclear") ||
					(this.options.filterDropDownItemIcons === true &&
						$target.find(".ui-iggrid-filtericonclear").length > 0)) {
					/* clear editor */
					/* if the default condition does not require an entry, set disabled to false on the editor */
					if (!requiresEntry || filterCondition === undefined) {
						this._editors[ colIndex ]
								.element
								.igGridFilterEditor(
									"option",
									"readOnly",
									this._getColType(this.grid.options.columns[ colIndex ].key) === "boolean");
					}
					this._editors[ colIndex ].value(null);
					this._filterDataSourceClear(this.grid.options.columns[ colIndex ].key, colIndex);
					/*close drop-down when Clear filter is selected. */
					this._toggleDropDown({ currentTarget: button }, true);
				} else {
					/* determine if the condition requires entry */
					/* M.H. 19 Jul 2013 Fix for bug #147233: cannot set default filtering expressions in ig.DataSource (they aren't taken into account , similar to sorting default expressions) */
					/* update filters UI updates also dropdowns - e.g. when filtering column is boolean then it causes infinite rebinding because _updateFiltersUI calls _selectDropDownItem which in these cases calls filtering again */
					if (!requiresEntry && !expr) {
						this._editors[ colIndex ].element.igGridFilterEditor("option", "readOnly", true);
						/* set correct input value if it doesn't require an entry */
						this._editorValueForCondition(filterCondition, this._editors[ colIndex ]);
						/* M.H. 25 Jun 2013 Fix for bug #143295: Wrong data in ui.columnKey and ui.columnIndex for dataFiltering and dataFiltered events */
						this._args = undefined;
						this._filterInternal(colIndex, this.grid.options.columns[ colIndex ].key);
						/*A.T. 14 Feb 2011 - Fix for bug #64465 */
					} else if (this._editors[ colIndex ].value() !== "" &&
								this._editors[ colIndex ].value() !== null) {
						/*M.K. 21 May 2015 Fix for bug 194293: Changing the filtering condition in simple mode should initiate a filtering */
						this._editors[ colIndex ].setFocus(true);
						evtArgs = {
							owner: this._editors[ colIndex ],
							text: this._editors[ colIndex ].displayValue()
						};
						this._filter(event, evtArgs);
					}
					if (requiresEntry) {
						this._editors[ colIndex ].element.igGridFilterEditor("option", "readOnly", false);
					if (nav === undefined && filterCondition !== undefined) {
						focT = $.ig.util.isTouchDevice() ? this.options.filterDropDownAnimationDuration : -1;
						this._editors[ colIndex ].setFocus(focT);
					}
				}
			}
			}
		},
		requiresFilteringExpression: function (filterCondition) {
			/* Check whether filterCondition requires or not filtering expression - e.g. if filterCondition is "lastMonth", "thisMonth", "null", "notNull", "true", "false", etc. then filtering expression is NOT required
			paramType="string" filtering condition - e.g. "true", "false",  "yesterday", "empty", "null", etc.
			returnType="bool" if false then filterCondition does not require filtering expression.
			*/
			var custConds = this._dsTransformedCustomConditions;
			if (this.grid.dataSource._isFilteringExprNotReq(filterCondition)) {
				return false;
			}
			if (custConds && custConds[ filterCondition ]) {
				return custConds[ filterCondition ].requireExpr;
			}
			if (filterCondition === undefined || filterCondition === null) {
				return false;
			}
			return true;
		},
		_editorValueForCondition: function (filterCondition, editor) {
			var date = new Date(), custConds = this._dsTransformedCustomConditions;
			if (filterCondition === "true") {
				editor.value(this.options.labels[ "true" ]);
			} else if (filterCondition === "false") {
				editor.value(this.options.labels[ "false" ]);
			} else if (filterCondition === "today") {
				editor.value(new Date());
			} else if (filterCondition === "yesterday") {
				if (this.grid.options.enableUTCDates) {
					editor.value(new Date(
										Date.UTC(date.getFullYear(),
												date.getMonth(),
												date.getDate() - 1,
												0, 0, 0, 0)));
				} else {
					editor.value(new Date(date.getFullYear(),
										date.getMonth(),
										date.getDate() - 1,
										0, 0, 0, 0));
				}
			/*
			} else if (filterCondition === "empty") {
				editor.value('empty');
			} else if (filterCondition === "notEmpty") {
				editor.value('notEmpty');
			} else if (filterCondition === "null") {
				editor.value('null');
			} else if (filterCondition === "notNull") {
				editor.value('notNull');
			*/
				/*	M.W.H. Fix for bug 203332: When you select the custom condition its text is not displayed in the editor. */
			} else if (custConds && custConds[ filterCondition ]) {
				editor.value(custConds[ filterCondition ].expressionText);
			} else {
				editor.value(null);
			}
		},
		_assignTemplates: function () {
			var self = this, opts = self.options, gId = this.grid.id();
			this._fTmplWrappers = {};
			this._fTmplWrappers[ gId + "_filterDialogFilterTemplate" ] = function (d) {
				return $.ig.tmpl(opts.filterDialogFilterTemplate, d);
			};
			this._fTmplWrappers[ gId + "_filterDialogAddConditionTemplate" ] = function (d) {
				return $.ig.tmpl(opts.filterDialogAddConditionTemplate, d);
			};
			this._fTmplWrappers[ gId + "_filterDialogAddConditionDropDownTemplate" ] = function (d) {
				return $.ig.tmpl(opts.filterDialogAddConditionDropDownTemplate, d);
			};
			this._fTmplWrappers[ gId + "_filterDialogFilterConditionTemplate" ] = function (d) {
				return $.ig.tmpl(opts.filterDialogFilterConditionTemplate, d);
			};
		if (String(this.grid.options.templatingEngine).toLowerCase() === "jsrender") {
				this._jsr = true;
				$.templates(gId + "_filterDialogFilterTemplate",
							opts.filterDialogFilterTemplate);
				$.templates(gId + "_filterDialogAddConditionTemplate",
							opts.filterDialogAddConditionTemplate);
				$.templates(gId + "_filterDialogAddConditionDropDownTemplate",
							opts.filterDialogAddConditionDropDownTemplate);
				$.templates(gId + "_filterDialogFilterConditionTemplate",
							opts.filterDialogFilterConditionTemplate);
				this._fTmplWrappers = $.render;
			}
		},
		_hoverDropDownItem: function (event) {
			if ($(event.currentTarget).find("input").length === 0) {
				$(event.currentTarget).addClass(this.css.filterDropDownListItemHover);
			}
		},
		_unhoverDropDownItem: function (event) {
			$(event.currentTarget).removeClass(this.css.filterDropDownListItemHover);
		},
		_virtualHorizontalScroll: function (event, args) {
			var start = args.startColIndex, i, headers = this.grid.headersTable().find("th"), columnKey;
			if (this._currentTarget) {
				this._toggleDropDown({ currentTarget: this._currentTarget }, true);
			}

			if (this.options.mode === "advanced" &&
				this.options.advancedModeEditorsVisible !== true) {
				for (i = 0; i < this.grid._virtualColumnCount; i++) {
					columnKey = this.grid.options.columns[ i + start ].key;
					$(headers[ i ]).data("colName", columnKey);
					if (this._fcData[ columnKey ] === true) {
						/* if filter button is shown in feature chooser do not update in indicator container */
						continue;
					}
					/* update button ID */
					$(headers[ i ])
						.find("a")
						.attr("id", this.grid.element[ 0 ].id + "_dd_" + columnKey + "_button");
				}
				this._updateTooltips(this._currentAdvancedExpressions || []);
			} else {
				throw new Error($.ig.GridFiltering.locale.virtualizationSimpleFilteringNotAllowed);
			}
		},
		_headerInit: function (owner, args) {
			var filterrow;
			/* clear all cells from the filter row that are marked with data-skip=true */
			/* L.A. 03 January 2012 - Fixing bug #130031 Filtering/Group By integration issue - when advancedModeEditorsVisible = TRUE */
			/* and the column is grouped, the filtering editors shift with 1 column to the left */
			if ((this.options.mode !== "simple" &&
					this.options.advancedModeEditorsVisible !== true) ||
				this.grid.element.attr("id") !== args.owner.element.attr("id")) {
				return;
			}
			/* M.H. 21 May 2014 Fix for bug #171794: Column Filtering is moving one column right with Row Selectors, Selection  and Column Fixing features enabled */
			/*M.K. 04 Feb 2015 Fix for bug 186482: When a parent column is grouped the filtering input gets smaller */
			filterrow = this.grid.headersTable().children("thead").find("[data-role=filterrow]");
			filterrow.find("[data-skip=true]").remove();
			this.grid._headerInit(filterrow, null, true);
		},
		_injectGrid: function (gridInstance) {
			var topmostGrid = null;
			this.grid = gridInstance;
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
			/* M.H. 19 Jul 2013 Fix for bug #147233: cannot set default filtering expressions in ig.DataSource (they aren't taken into account , similar to sorting default expressions) */
			if (this.options.type) {
				this.grid.dataSource.settings.filtering.type = this.options.type;
			} else {
				this.grid.dataSource.settings.filtering.type = "remote";
			}
			/* attach headerRendered handler */
			if (this.options.filterExprUrlKey) {
				this.grid.dataSource.settings.filtering.filterExprUrlKey = this.options.filterExprUrlKey;
			}
			this.grid.dataSource.settings.filtering.caseSensitive = this.options.caseSensitive;

			if (this._headerCellRenderedHandler !== null && this._headerCellRenderedHandler !== undefined) {
				this.grid.element.unbind("iggridheadercellrendered", this._headerCellRenderedHandler);
			}
			this._headerCellRenderedHandler = $.proxy(this._headerCellRendered, this);
			this.grid.element.bind("iggridheadercellrendered", this._headerCellRenderedHandler);

			if (this._headerRenderedHandler !== null && this._headerRenderedHandler !== undefined) {
				this.grid.element.unbind("iggridheaderrendered", this._headerRenderedHandler);
			}
			this._headerRenderedHandler = $.proxy(this._headerRendered, this);
			this.grid.element.bind("iggridheaderrendered", this._headerRenderedHandler);

			if (this._columnResizedHandler !== null && this._columnResizedHandler !== undefined) {
				this.grid.element.unbind("iggridresizingcolumnresized", this._columnResizedHandler);
			}
			this._columnResizedHandler = $.proxy(this._columnResized, this);
			this.grid.element.bind("iggridresizingcolumnresized", this._columnResizedHandler);

			if (this._columnsAlteredHandler !== null && this._columnsAlteredHandler !== undefined) {
				this.grid.element.unbind("iggrid_columnsmoved", this._columnsAlteredHandler);
			}
			this._columnsAlteredHandler = $.proxy(this._columnsAltered, this);
			this.grid.element.bind("iggrid_columnsmoved", this._columnsAlteredHandler);

			/*if (!isRebind) { */
			this._transformCustomConditionsForDataSource();
			this._initDefaultSettings();
			/*} */
			/* register for uiDirty */
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			if (this._onUIDirtyHandler !== null && this._onUIDirtyHandler !== undefined) {
				this.grid.element.unbind("iggriduidirty", this._onUIDirtyHandler);
			}
			this._onUIDirtyHandler = $.proxy(this._onUIDirty, this);
			this.grid.element.bind("iggriduidirty", this._onUIDirtyHandler);
			/* manage horizontal virtual scrolling -> columns need to be updated accordingly and */
			if (this._virtualHorizontalScrollHandler !== null &&
				this._virtualHorizontalScrollHandler !== undefined) {
				this.grid.element.unbind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			}
			this._virtualHorizontalScrollHandler = $.proxy(this._virtualHorizontalScroll, this);
			this.grid.element.bind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			if (((this.grid.options.virtualization === true && this.grid.options.width !== null) ||
						this.grid.options.columnVirtualization === true) &&
				this.options.mode === null) {
				this.options.mode = "advanced";
			} else if ((this.grid.options.virtualization === false ||
							(this.grid.options.virtualization === true &&
							!this.grid._isColumnVirtualizationEnabled())) &&
						(this.options.mode === null || this.options.mode === "simple")) {
				this.options.mode = "simple";
			} else if (this.options.mode === "simple" &&
						(this.grid.options.virtualization === true ||
						this.grid.options.columnVirtualization === true)) {
				throw new Error($.ig.GridFiltering.locale.virtualizationSimpleFilteringNotAllowed);
			}
			/* MultiRowLayout is supported ONLY when mode is advanced */
			if (this.grid._rlp && (this.options.mode === "simple" || !this.options.mode)) {
				throw new Error($.ig.GridFiltering.locale.multiRowLayoutSimpleFilteringNotAllowed);
			}
			if (this._headerInitHandler !== null && this._headerInitHandler !== undefined) {
				this.grid.element.unbind("iggridheaderextracellsmodified", this._headerInitHandler);
			}
			this._headerInitHandler = $.proxy(this._headerInit, this);
			this.grid.element.bind("iggridheaderextracellsmodified", this._headerInitHandler);
			if (this.grid.element.igGridFeatureChooser !== undefined) {
				this.grid.element.igGridFeatureChooser();
			} else {
				throw new Error($.ig.GridFiltering.locale.featureChooserNotReferenced);
			}
			this._assignTemplates();
			/* if persistance is enabled we need to set filtering expressions, default filtering expressions to datasource
				we can do using injectGrid because in grid framework DataBind the steps for setup datasource are:
				1. setupDataSource is called and DS filtering expressions are cleared
				2. UI Dirty is called
				3. Inject Grid is called - in this case filtering expressions are reset and it is set DS defaultEpxressions because if they are set then data source filter is called and filtering is applied
			*/
			if (this.options.persist) {
				this._preserveFiltering();
			}
		}
	});
	$.extend($.ui.igGridFiltering, { version: "16.1.20161.2145" });

	$.fn.extend({
		igGridFilterEditor: function () {
			var edtrT = this.data("editorType"),
				func = this[ edtrT ];
			if ($.type(func) !== "function") {
				return;
			}
			return func.apply(this, arguments);
		}
	});
}(jQuery));
