/*!@license
* Infragistics.Web.ClientUI Outlook GroupBy feature 16.1.20161.2145
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
*	jquery.ui.droppable.js
*	infragistics.ui.grid.framework.js
*	infragistics.ui.grid.shared.js
*	infragistics.ui.grid.featurechooser.js
*/

/*jscs:disable*/
/*global jQuery, MSApp*/
(function ($) {

	$.widget("ui.igGridGroupBy", {
		css: {
			/* Classes applied to the markup that's being dragged*/
			dragMarkup: "ui-iggrid-dragMarkup",
			/* classes applied to the group by area, where column headers can be dropped */
			groupByArea: "ui-iggrid-groupbyarea",
			/* classes applied to the text container in the group by area */
			groupByAreaText: "ui-iggrid-groupbyareatext",
			/* classes applied to every group row TR */
			groupedRow: "ui-iggrid-groupedrow",
			/* classes applied to the special group by cell rendered in the header (the small empty first cell) */
			expandHeaderCellGroupBy: "ui-iggrid-header ui-widget-header",
			/* classes applied to the group by expander span element, when the group row is expanded */
			groupByExpandCellExpanded: "ui-icon ui-iggrid-expandbutton ui-iggrid-expandbuttonexpanded ui-icon-minus",
			/* classes applied to the group by expander span element, when the group row is collapsed */
			groupByExpandCellCollapsed: "ui-icon ui-iggrid-expandbutton ui-icon-plus",
			/* class applied when we are dragging a label and we are over a groupby area, before it's dropped */
			groupByAreaDropHover: "ui-iggrid-groupbyareahover",
			/* classes applied to the groupBy expander TD cell */
			groupByExpandColumn: "ui-iggrid-expandcolumn",
			/* classes applied to every cell that's rendered in front of a data cell, which is not a grouped row. this is necessary so that data rows and grouped rows align well */
			nonGroupRowEmptyCell: "ui-iggrid-nongrouprowemptycell",
			/* classes applied to the LI which is rendered in the groupBy area, when a column header is dropped there */
			groupedColumnLabel: "ui-iggrid-groupedcolumnlabel ui-state-default",
			/* classes applied to the text in the group by label */
			groupedColumnLabelText: "ui-iggrid-groupedcolumnlabeltext",
			/* classses applied to the remove button that appears for every column label that's dropped in the groupBy area. The button appears on hover */
			groupByRemoveButton: "ui-icon ui-icon-circle-close ui-iggrid-groupbyremovebutton",
			/* classes applied to the right edge of the groupBy area bread-crumb labels, when there are no more labels to the right. so that it appears as triangle */
			groupedColumnLabelRightEdgeEnd: "ui-iggrid-groupbylabelrightedgeend",
			/* classes applied to the right edge of a groupBy column label, when it's in the middle of the bread-crumb */
			groupedColumnLabelRightEdge: "ui-iggrid-groupbylabelrightedge",
			/* classes apppled to the text container, which specifies the columnLayout name in front of the groupBy column label, in case the grid is hierarchical */
			groupedColumnLayoutLabel: "ui-iggrid-groupbylayoutlabel",
			/* classes which are applied to the extra rendered cell in the header */
			headerExtraCell: "ui-iggrid-expandheadercellgb",
			/* M.H. 17 Oct. 2011 Fix for bug 90815 - add css class for footer cell */
			/* classes which are applied to the extra rendered cell in the footer */
			footerExtraCell: "ui-widget-content ui-iggrid-footerextracell",
			/* classes which are applied to the feature chooser groupby item */
			featureChooserIconClass: "ui-icon ui-iggrid-icon-groupby",
			/* classes which are applied to container of grouped columns the groupby dialog */
			dialogGroupedColumns: "ui-iggrid-groupby-dialog-groupedcolumns",
			/* classes which are applied to container of columns which are not grouped the groupby dialog */
			dialogUnroupedColumns: "ui-iggrid-groupby-dialog-ungroupedcolumns",
			/* classes which are applied to each of grouped columns in the groupby dialog */
			dialogGroupedItem: "ui-widget-content",
			/* classes which are applied to each of columns(NOT grouped) in the groupby dialog */
			dialogUngroupedItem: "ui-widget-content",
			/* classes which are applied to groupby button of columns(NOT grouped) in the groupby dialog */
			dialogUngroupedColumnsGroupByButton: "ui-iggrid-dialog-groupby-button",
			/* classes which are applied to each column name container of columns(NOT grouped) in the groupby dialog */
			dialogUngroupedColumnsText: "ui-iggrid-dialog-text",
			/* classes which are applied to indicator for ascending sorted columns in the groupby dialog */
			dialogButtonAsc: "ui-button ui-corner-all ui-button-icon-only ig-sorting-indicator",
			/* classes which are applied to icon indicator for ascending sorted columns in the groupby dialog */
			dialogButtonAscIcon: "ui-button-icon-primary ui-icon ui-icon-arrowthick-1-n",
			/* classes which are applied to indicator for descending sorted columns in the groupby dialog */
			dialogButtonDesc: "ui-button ui-corner-all ui-button-icon-only ig-sorting-indicator",
			/* classes which are applied to icon indicator for descending sorted columns in the groupby dialog */
			dialogButtonDescIcon: "ui-button-icon-primary ui-icon ui-icon-arrowthick-1-s",
			/* classes which are applied to button which ungroup columns in the groupby dialog */
			dialogButtonUngroup: "ui-iggrid-dialog-groupedbuttons ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-igbutton ui-widget-content ui-igbutton-remove",
			/* classes which are applied to container which holds all columns(NOT grouped) in the groupby dialog */
			dialogButtonUngroupContainer: "ui-button-icon-primary ui-icon ui-icon-circle-close",
			/* classes which are applied to column name container for each grouped column in the groupby dialog */
			dialogGroupedColumnTextContainer: "ui-iggrid-dialog-text",
			/* classes which are applied to the list of grouped columns in the groupby dialog */
			dialogListGroupedColumns: "ui-iggrid-dialog-list-groupedcolumns",
			/* classes which are applied to the list of columns(NOT grouped) in the groupby dialog */
			dialogListUngroupedColumns: "ui-iggrid-dialog-list-ungroupedcolumns",
			/* classes which are applied to the layers dropdown button in the groupby dialog */
			dialogLayoutsDDButton: "ui-icon ui-icon-triangle-1-s ui-iggrid-dialog-layouts-dd-button",
			/* classes which are applied to the container which holds layouts tree in the groupby dialog */
			dropDownLayoutsContainer: "ui-iggrid-featurechooser-dropdown-dialog ui-widget ui-widget-content ui-corner-all",
			/* classes which are applied to the layouts dropdown field in the groupby dialog */
			dialogLayoutsDDField: "ui-iggrid-dialog-layouts-dd-field",
			/* classes which are applied to the container which holds layouts dropdown in the groupby dialog*/
			dialogLayoutsDD: "ui-iggrid-dialog-layouts-dd ui-widget-content ui-corner-all ig-combo-icon-container",
			/*			dialogButtonSlide: "ui-iggrid-sorting-dialog-button-slide ui-button ui-widget ui-state-default ui-corner-bottom ui-button-icon-only",
						dialogButtonSlideContainer: "ui-button-icon-primary ui-icon ui-icon-triangle-1-n",
						dialogButtonSlideUp: "ui-icon-triangle-1-s",
						dialogSlideArea: "ui-iggrid-multiplesorting-dialog-slide ui-widget-content",
						dialogSlideAreaContainer: "ui-iggrid-multiplesorting-dialog-slide-button-container", */
			/* classes which are applied to the container of layout tree in groupby dialog */
			modalDialogTreeContainer: "ui-iggrid-groupby-dialog-tree",
			/* classes which are applied to the layouts container in the groupby dialog */
			modalDialogLayoutsContainer: "ui-iggrid-groupby-dialog-layoutscontainer",
			/* classes which are applied to hover state of buttons */
			dialogButtonsHover: "ui-state-hover",
			/* classes which are added to the last empty cell before expander cell */
			groupByLastEmptyCell: "ui-iggrid-last-emptycell"
		},
		renderInFeatureChooser: false,
		options: {
			/* type="top|hidden|bottom" Sets the place in the grid where the groupBy area will be
			top type="string" the GroupBy area will be rendered above the grid headers
			hidden type="string" the GroupBy area will not be rendered
			bottom type="string" the GroupBy area will be rendered below the grid footer (and above the pager, if any)
			*/
			groupByAreaVisibility: "top",
			/* type="bool" Specifies if after grouping, the grouped rows will be initially expanded or collapsed. This option can be set only at initialization. */
			initialExpand: true,
			/* type="string" Text that will be shown in the GroupBy area when there are no grouped columns */
			emptyGroupByAreaContent: $.ig.GridGroupBy.locale.emptyGroupByAreaContent,
			/* type="string" Text of link(on click shows modal dialog with columns which could be group/ungroup by) that will be shown in the GroupBy area when there are no grouped columns */
			emptyGroupByAreaContentSelectColumns:
				$.ig.GridGroupBy.locale.emptyGroupByAreaContentSelectColumns,
			/* type="bool" Specifies if grouped rows will have an expander image that will allow end users to expand and collapse them. This option can be set only at initialization. */
			expansionIndicatorVisibility: true,
			/* type="number" By default, the column width for the header is taken. If this is specified it's used for all headers. */
			groupByLabelWidth: null,
			/* type="number" Specifies the opacity of the drag markup, while a column header is being dragged. The value must be between 0 and 1. */
			labelDragHelperOpacity: 0.5,
			/* type="number" specifies the indentation for a grouped row. If several columns are grouped, the total indentation will grow */
			indentation: 30,
			/* type="asc|desc" default sort order - asc or desc */
			defaultSortingDirection: "asc",
			/* type="object" Returns the list of currently grouped columns. The option is read-only and cannot be set at initialization or at runtime. */
			groupedColumns: [
				{
					/* type="string" key of the column that's grouped */
					key: null,
					/* type="asc|desc" sort order - ascending or descending */
					dir: "asc",
					/* type="string" key of the columnLayout, if the grid is hierarchical */
					layout: null,
					/* type="object" column object for the column that is grouped */
					col: null
				}
			],
			/* type="string" Specifies a key to get group by data from the response. This option can be set only at initialization. */
			resultResponseKey: null,
			/* type="string" Template for the grouped row's text, follows jQuery's templating guidelines. Variables available for the template are ${key}, ${val} and ${count}. */
			groupedRowTextTemplate: "${key}: ${val} (${count})",
			/* type="remote|local" Specifies whether the groupBy operation takes place locally on client-side or remotely on server-side.
			local type="string" Execute the groupBy operation locally on client-side.
			remote type="string" Execute the groupBy operation by a request to the server.
			*/
			type: null,
			/* type="string" URL param name which specifies how groupBy expressions will be encoded in the URL. The OData conventions for sorting params are used if the grid is bound to an OData service. ex: ?groupby(col1)=asc */
			groupByUrlKey: null,
			/* type="string" URL param value for ascending type of grouping. The OData conventions for sorting params are used if the grid is bound to an OData service. Example: ?groupBy(col1)=asc*/
			groupByUrlKeyAscValue: null,
			/* type="string" URL param value for descending type of grouping. The OData conventions for sorting params are used if the grid is bound to an OData service. Example: ?groupBy(col1)=desc */
			groupByUrlKeyDescValue: null,
			/* type="object" specifies the settings for GroupBy summaries */
			summarySettings: {
				/* type="bool" specifies if summaries are going to be rendered inline as part of the grouped row, or in separate dedicated rows
				 renderSummariesInGroupRow: true,
				 type="top|bottom" specifies the location of the summary row, relevant when renderSummariesInGroupRow = false
				/*summariesPosition: "top",*/
				/* type="string" specifies the delimiter for multiple summaries, in case they are rendered inline in the grouped row */
				multiSummaryDelimiter: ",",
				/* type="string" by default, two digits are shown after the decimal place */
				summaryFormat: "#.00"
			},
			/* type="object" Get sets settings for individual columns such as whether the grid is grouped by that column or grouping is allowed for that column. */
			columnSettings: [
				{
					/* type="bool" enables/disables a column to participate in grouping. By default all columns can be grouped */
					allowGrouping: true,
					/* type="bool" specifies if a column should be grouped by default */
					isGroupBy: false,
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
					compareFunc: null,
					/* type="function" specifies a custom group by function, which accepts the column setting, the first and the second value to compare and returns bool */
					groupComparerFunction: null,
					/* type="function" Reference to a function which will be used for formatting the cell values. The function should accept a value from the grouped column and return the new formatted value in the label of the row.*/
					groupLabelFormatter: null,
					/* type="asc|desc" specifies the sort order - ascending or descending */
					dir: "asc",
					/* type="object" A list of aggregation functions to calculate on the column values for each group. */
					summaries: [
						{
							/* type="avg|min|max|sum|count|custom" the summary function
							avg type="string" average
							min type="string" minimal
							max type="string" maximal
							sum type="string" sum
							count type="string" count
							custom type="string" custom summary
							*/
							summaryFunction: "avg",
							/* specifies the Summary text that will be shown before the value */
							text: null,
							/* specifies a custom summary function, which is called for each group, it should return the custom summary result. It takes as arguments object with properties: dataRecords - array of grouped(for the data view) data records, array - array of cell values for the specified column, key - key of the grouped column, allGroupData - array of data records for the group(for the whole data source - not only for the data view) */
							customSummary: null
						}
					]
				}
			],
			/* type="string" specifies the expand indicator tooltip for grouped rows */
			expandTooltip: $.ig.GridGroupBy.locale.expandTooltip,
			/* type="string" specifies the collapse indicator tooltip for grouped rows */
			collapseTooltip: $.ig.GridGroupBy.locale.collapseTooltip,
			/* type="string" specifies the tooltip for the remove button */
			removeButtonTooltip: $.ig.GridGroupBy.locale.removeButtonTooltip,
			/* type="bool" specifies whether on click in groupby dialog to be immediately grouped/ungrouped columns. When it is false then it is shown Apply button in the groupby dialog  */
			modalDialogGroupByOnClick: false,
			/* type="string" specifies the text of GroupBy button in the groupby dialog */
			modalDialogGroupByButtonText: $.ig.GridGroupBy.locale.modalDialogGroupByButtonText,
			/* type="string" Specifies caption for each descending sorted column in groupby dialog */
			modalDialogCaptionButtonDesc: $.ig.GridGroupBy.locale.modalDialogCaptionButtonDesc,
			/* type="string" Specifies caption for each ascending sorted column in groupby dialog */
			modalDialogCaptionButtonAsc: $.ig.GridGroupBy.locale.modalDialogCaptionButtonAsc,
			/* type="string" Specifies caption button ungroup in groupby dialog */
			modalDialogCaptionButtonUngroup: $.ig.GridGroupBy.locale.modalDialogCaptionButtonUngroup,
			/* type="string" Specifies caption text for groupby dialog */
			modalDialogCaptionText: $.ig.GridGroupBy.locale.modalDialogCaptionText,
			/* type="string" Specifies label for layouts dropdown in groupby dialog */
			modalDialogDropDownLabel: $.ig.GridGroupBy.locale.modalDialogDropDownLabel,
			/* type="string" Specifies name of the root layout which is shown layouts tree dialog */
			modalDialogRootLevelHierarchicalGrid:
				$.ig.GridGroupBy.locale.modalDialogRootLevelHierarchicalGrid,
			/* type="string" Specifies caption of layouts dropdown button in groupby dialog */
			modalDialogDropDownButtonCaption: $.ig.GridGroupBy.locale.modalDialogDropDownButtonCaption,
			/* type="string" Specifies label for clear all button in groupby dialog */
			modalDialogClearAllButtonLabel:
				$.ig.GridGroupBy.locale.modalDialogClearAllButtonLabel,
			/* type="string" Specifies caption for button which opens groupby dialog */
			emptyGroupByAreaContentSelectColumnsCaption:
				$.ig.GridGroupBy.locale.emptyGroupByAreaContentSelectColumnsCaption,
			/* type="number" Specifies width of layouts dropdown in groupby dialog */
			modalDialogDropDownWidth: 200,
			/* type="number" Specifies height of layouts dropdown in groupby dialog */
			modalDialogDropDownAreaWidth: null,
			/* type="number" Specifies time in milliseconds for animation duration to show/hide modal dialog */
			modalDialogAnimationDuration: 200,
			/* type="number" Specifies width of groupby dialog */
			modalDialogWidth: 400,
			/* type="number" Specifies height of groupby dialog */
			modalDialogHeight: "",
			/* M.H. 9 March 2012 Fix for bug #103978*/
			/* type="string" Specifies text of button which apply changes in modal dialog */
			modalDialogButtonApplyText: $.ig.GridGroupBy.locale.modalDialogButtonApplyText,
			/* type="string" Specifies text of button which cancel changes in modal dialog */
			modalDialogButtonCancelText: $.ig.GridGroupBy.locale.modalDialogButtonCancelText,
			/* type="bool" Format grouped column using the formatter set in igGrid.columns[ i ].formatter or igGrid format. */
			useGridColumnFormatter: true,
			/* type="bool" Enables / disables groupby persistence between states*/
			persist: true,
			/* type="string" Controls containment behavior for the groupby dialog.
				owner type="string" The groupby dialog will be draggable only in the grid area
				window type="string" The groupby dialog will be draggable in the whole window area
			*/
			groupByDialogContainment: "owner",
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		events: {
			/* cancel="true" Event which is fired when the grouped columns collection is about to change. This event is fired even when button OK is clicked from the modal dialog(after event modalDialogButtonApplyClick is fired)
			use args.owner in order to access the groupby widget object
			use args.owner.grid to access the grid widget option
			use args.groupedColumns to get a reference to the current groupedColumns.
			use args.newGroupedColumns to get an object of the new grouped columns that should be applied.(it is set ONLY if called from modal dialog)
			use args.key to get a reference to the current column"s key that"s being grouped(not set if called from modal dialog)
			use args.layout to get a reference to the current layout object, if any(not set if called from modal dialog)
			use args.grid to get a reference to the current child grid element, in case it's an hierarchical grid(not set if called from modal dialog)
			use args.triggeredBy to get which user interaction triggers the event - possible options are dragAndDrop|modalDialog|sortStateChanged|removeButton|regroup
			*/
			groupedColumnsChanging: "groupedColumnsChanging",
			/* event which is fired when the groupedColumns collection has changed. This event is fired also when group/ungroup from groupby modal dialog but key, layout and grid are not set
			use args.owner in order to access the groupby widget object
			use args.owner.grid to access the grid widget option
			use args.groupedColumns to get a reference to the current groupedColumns
			use args.key to get a reference to the current column"s key that"s being grouped
			use args.layout to get a reference to the current layout object, if any
			use args.grid to get a reference to the current child grid element, in case it's an hierarchical grid(not set if called from modal dialog)
			use args.triggeredBy to get which user interaction triggers the event - possible options are dragAndDrop|modalDialog|sortStateChanged|removeButton|regroup
			*/
			groupedColumnsChanged: "groupedColumnsChanged",
			/* cancel="false" Event fired every time the groupby dialog changes its position.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupBy widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the Column Chooser element. This is a jQuery object.
			Use ui.originalPosition to get the original position of the groupby dialog div as { top, left } object, relative to the page.
			Use ui.position to get the current position of the groupby dialog div as { top, left } object, relative to the page.
			*/
			modalDialogMoving: "modalDialogMoving",
			/* cancel="true" event fired before the modal dialog is closed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupBy widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogClosing: "modalDialogClosing",
			/* event fired after the modal dialog has been closed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupBy widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogClosed: "modalDialogClosed",
			/* cancel="true" event fired before the modal dialog is opened.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupBy widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogOpening: "modalDialogOpening",
			/* event fired after the modal dialog is already opened.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupBy widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogOpened: "modalDialogOpened",
			/* cancel="true" event fired before the contents of the modal dialog are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupby widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogContentsRendering: "modalDialogContentsRendering",
			/* event fired after the contents of the modal dialog are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupBy widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogContentsRendered: "modalDialogContentsRendered",
			/* cancel="true" event fired when the button is Apply is clicked.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupby widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			Use ui.groupedColumns to get the array of grouped columns
			Use ui.groupedColumnLayouts to get array of column layouts
			Use ui.sortingExpr to get array of sorted columns
			*/
			modalDialogButtonApplyClick: "modalDialogButtonApplyClick",
			/* cancel="true" event fired when reset button is clicked.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupby widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.modalDialogElement to get a reference to the modal dialog element. This is a jQuery object.
			*/
			modalDialogButtonResetClick: "modalDialogButtonResetClick",
			/* cancel="true" event fired when column in modal dialog is clicked to be grouped.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupby widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.key to get the key of the column to be grouped.
			Use ui.layout to get the layout of the columns
			*/
			modalDialogGroupingColumn: "modalDialogGroupingColumn",
			/* event fired when column in modal dialog is clicked to be grouped.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupby widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.key to get the key of the column to be grouped.
			Use args.groupedColumns to get a reference to the current groupedColumns
			Use ui.layout to get the layout of the columns
			*/
			modalDialogGroupColumn: "modalDialogGroupColumn",
			/* cancel="true" event fired when column in modal dialog is clicked to be ungrouped.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupby widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.key to get the key of the column to be grouped.
			Use ui.layout to get the layout of the columns
			*/
			modalDialogUngroupingColumn: "modalDialogUngroupingColumn",
			/* event fired when column in modal dialog is clicked to be ungrouped.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupby widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use args.groupedColumns to get a reference to the current groupedColumns
			Use ui.key to get the key of the column to be grouped.
			Use ui.layout to get the layout of the columns
			*/
			modalDialogUngroupColumn: "modalDialogUngroupColumn",
			/* cancel="true" event fired when column in modal dialog is clicked to be ungrouped.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridGroupby widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			Use ui.key to get the key of the column to be grouped.
			Use ui.layout to get the layout of the columns
			Use ui.isAsc to get whether column should be sorted ascending or descending
			*/
			modalDialogSortGroupedColumn: "modalDialogSortGroupedColumn"
		},
		/*jscs:enable*/
		_create: function () {
			/* A.T. 21 March 2012 - fix for #91992 */
			this._orderedGroupedCols = [  ];
		},
		_createWidget: function () {
			/* !Strip dummy objects from options, because they are defined for documentation purposes only! */
			this.options.columnSettings = [  ];
			this.options.groupedColumns = [  ];
			this._tempSortingExpr = [  ];
			this._tempGroupedColumns = [  ];
			/* M.H. 23 March 2012 Fix for bug #105946 */
			this._tempOrderedGroupedCols = [  ];
			this._tempOptions = {};
			this._currentTreeLayer = null;
			this._isInitFC = false;
			this._modalSelectedLayout = null;
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		_setOption: function (key, value) {
			var modalDialog = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog");
			$.Widget.prototype._setOption.apply(this, arguments);
			if (key === "groupByAreaVisibility" || key === "expansionIndicatorVisibility") {
				throw new Error($.ig.Grid.locale.optionChangeNotSupported.replace("{optionName}", key));
			}
			/* M.H. 14 March 2012 Fix for bug #104700 */
			switch (key) {
				case "modalDialogGroupByOnClick":
					this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog").remove();
					this._renderGroupByDialog();
					break;
				case "modalDialogCaptionText":
					modalDialog.igGridModalDialog("option", "modalDialogCaptionText", value);
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
				default:
					break;
			}
		},
		_headerRendered: function (event, args) {
			/* render the groupby area on top of the grid */
			var groupby = this, groupbyarea;
			if (args.owner.id() !== this.grid.id()) {
				return;
			}
			/*
			if (this._hierarchical !== true && this._hierarchical !== false) {
				if (this.grid.headersTable().find(".ui-iggrid-expandheadercell").length > 0) {
					this._hierarchical = true;
				} else {
					this._hierarchical = false;
				}
			}
			*/
			if (args.owner.element.attr("data-childgrid")) {
				return;
			}
			/* M.H. 31 May 2014 Fix for bug #172881: When groupByAreaVisibility ="hidden" the openGroupByDialog API method is not working */
			this._renderGroupByDialog();
			if (this.options.groupByAreaVisibility === "top") {
				/* groupbyarea = $("<div></div>").prependTo(args.table.parent()); */
				groupbyarea = $("<div></div>").prependTo(this.grid.container());
			} else if (this.options.groupByAreaVisibility === "bottom") {
				groupbyarea = $("<div></div>").appendTo(args.owner.container());
			} else if (this.options.groupByAreaVisibility === "hidden") {
				return; /* group area is not visible */
			}
			groupbyarea
				.attr("id", this.grid.element.attr("id") + "_groupbyarea")
				.addClass(this.css.groupByArea);
			this._setGroupAreaTextContent(
				$("<span></span>")
				.appendTo(groupbyarea)
				.addClass(this.css.groupByAreaText)
			);
			groupbyarea.droppable({
				accept: "th:not([ groupby-disabled ])",
				hoverClass: groupby.css.groupByAreaDropHover,
				drop: function (event, ui) {
					var accepted, isdescendant;
					isdescendant = ui.draggable.closest(".ui-iggrid-root").attr("id") === groupby.grid.id() ||
						ui.draggable.closest(".ui-iggrid").find(".ui-iggrid-root").length > 0;
					if (!groupby.grid.element.hasClass("ui-iggrid-root")) {
						isdescendant = true;
					}
					accepted = ui.draggable.hasClass("ui-iggrid-header") && isdescendant;
					if (accepted) {
						/* invoke group by */
						groupby._groupBy(event, ui.draggable);
					}
				}
			});
			/* M.H. 11 July 2012 Fix for bug #115984 */
			if (this.grid._isMultiColumnGrid) {
				groupbyarea.droppable("option", "tolerance", "touch");
			}
			/* this.openGroupByDialog(); */
		},
		_renderGroupByDialog: function () {
			var $buttonOK, containment,
				self = this,
				o = this.options,
				$captionButtonContainer,
				modalDialog;
			if (this.options.groupByDialogContainment === "owner") {
				containment = this.grid.container();
			} else {
				containment = "window";
			}
			modalDialog = $("<div></div>")
					.appendTo(this.grid._rootContainer())
					.attr("id", this.grid.id() + "_groupby_modalDialog");
			modalDialog.igGridModalDialog({
				renderFooterButtons: !o.modalDialogGroupByOnClick,
				modalDialogCaptionText: o.modalDialogCaptionText,
				modalDialogWidth: o.modalDialogWidth,
				modalDialogHeight: o.modalDialogHeight,
				animationDuration: o.modalDialogAnimationDuration,
				/* M.H. 9 March 2012 Fix for bug #103978 */
				buttonApplyText: o.modalDialogButtonApplyText,
				buttonCancelText: o.modalDialogButtonCancelText,
				/* M.H. 8 May 2012 Fix for bug #110344 */
				containment: containment,
				gridContainer: this.grid.container(),
				modalDialogOpening: function (event, args) {
					return self._groupByDialogOpening(event, args);
				},
				modalDialogOpened: function () {
					self._trigger(self.events.modalDialogOpened, null,
						{
							modalDialogElement: modalDialog,
							owner: self
						});
				},
				/* M.H. 13 March 2012 Fix for bug #104549 */
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
					/* M.H. 13 March 2012 Fix for bug #104549 */
					return self._trigger(self.events.modalDialogClosing, null,
						{ modalDialogElement: modalDialog, owner: self });
				},
				modalDialogClosed: function () {
					/* M.H. 13 March 2012 Fix for bug #104549 */
					self._trigger(self.events.modalDialogClosed, null,
						{ modalDialogElement: modalDialog, owner: self });
				}
			});
			if (o.modalDialogGroupByOnClick) {
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
				$buttonOK = this.grid.container()
					.find("#" + this.grid.id() + "_groupby_modalDialog_footer_buttonok");
				$buttonOK.bind("igbuttonclick", function (e) { self._groupByDialogButtonOKClick(e); });
			}
		},
		openGroupByDialog: function () {
			/* Open groupby modal dialog */
			var modalDialog = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog");
			modalDialog.igGridModalDialog("openModalDialog");
		},
		closeGroupByDialog: function () {
			/* Close groupby modal dialog */
			var modalDialog = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog");
			modalDialog.igGridModalDialog("closeModalDialog");
		},
		_groupByDialogOpening: function () {
			var noCancel,
				modalDialog = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog");
			noCancel = this._trigger(this.events.modalDialogOpening, null,
				{ modalDialogElement: modalDialog, owner: this });
			if (noCancel) {
				this.renderGroupByModalDialog();
			}
			return noCancel;
		},
		renderGroupByModalDialog: function () {
			/* Render groupby modal dialog and its content */
			var $content, i,
				self = this,
				o = this.options,
				css = this.css,
				modalDialog = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog"),
				noCancel,
				$groupedColumns,
				$ungroupedColumns;

			noCancel = this._trigger(this.events.modalDialogContentsRendering, null,
				{ modalDialogElement: modalDialog, owner: this });
			if (noCancel) {
				$content = modalDialog.igGridModalDialog("getContent");
				$content.empty();
				self._tempGroupedColumns = o.groupedColumns.slice(0);
				/* M.H. 23 March 2012 Fix for bug #105946 */
				self._tempOrderedGroupedCols = self._orderedGroupedCols.slice(0);
				/* M.H. 12 March 2012 Fix for bug #104507 */
				self._tempSortingExpr = self.grid.dataSource.settings.sorting.expressions.slice(0);
				self._tempOptions = { columnLayouts: [  ] };
				/* M.H. 20 March 2012 Fix for bug #105271 */
				if (this.grid.options.columnLayouts && this.grid.options.columnLayouts.length > 0) {
					for (i = 0; i < this.grid.options.columnLayouts.length; i++) {
						self._tempOptions.columnLayouts.push(
							$.extend(true, {}, this.grid.options.columnLayouts[ i ])
							);
					}
					this._renderLayoutsDropDown();
				}
				$groupedColumns = $("<div></div>")
					.attr("id", this.grid.id() + "_groupby_modalDialog_groupedcolumns")
					.addClass(css.dialogGroupedColumns)
					.appendTo($content);
				$ungroupedColumns = $("<div></div>")
					.attr("id", this.grid.id() + "_groupby_modalDialog_ungroupedcolumns")
					.addClass(css.dialogUnroupedColumns)
					.appendTo($content);

				$("<ul></ul>")
					.addClass(css.dialogListGroupedColumns)
					.appendTo($groupedColumns);
				$("<ul></ul>")
					.addClass(css.dialogListUngroupedColumns)
					.appendTo($ungroupedColumns);

				this._renderColumnsDialog(this.grid.options.columns);
				this._currentTreeLayer = null;
				if (this._isToRenderClearAllButton()) {
					this._renderDialogButtonClearAll();
				} else {
					/* M.H. 8 March 2012 Fix for bug #103675 */
					this.grid.container().find("#" + this.grid.id() + "_groupbydialog_reset_button").remove();
				}
				this._trigger(this.events.modalDialogContentsRendered, null,
					{ modalDialogElement: modalDialog, owner: this });
			}
			/* this._addSlideButtonGroupedColumns(); */
		},
		_addSlideButtonGroupedColumns: function () {
			var css = this.css,
				o = this.options,
				$groupedColumns = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog_groupedcolumns"),
				buttonId = this.grid.id() + "_groupbymodaldialot_slidebutton",
				$button,
				innerHTML;

			innerHTML = "<div class='" + css.dialogSlideArea + "'>" +
				"	<div class='" + css.dialogSlideAreaContainer + "'>" +
				"		<button id='" + buttonId + "' class='" + css.dialogButtonSlide +
				"' role='button' aria-disabled='false' title='" + o.dialogButtonSlideCaption + "'>" +
				"			<span class='" + css.dialogButtonSlideContainer + "'></span>" +
				"			<span class='ui-button-text'>" + o.dialogButtonSlideCaption + "</span>" +
				"		</button>" +
				"	</div>" +
				"</div>";
			$(innerHTML).insertAfter($groupedColumns);
			$button = this.grid.container().find("#" + buttonId);
			$button.bind({
				click: function () {
					$groupedColumns.slideToggle("slow");
					$(this).find("span:eq(0)").toggleClass(css.dialogButtonSlideUp);
					return false;
				}
			});
		},
		_renderLayoutsDropDown: function () {
			var o = this.options, fn, w, dW, $ddField,
				self = this,
				css = this.css,
				modalDialog = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog"),
				$content = modalDialog.igGridModalDialog("getContent"),
				cancelFunc,
				htmlLayouts,
				$buttonDD,
				$dropDown,
				$tree,
				dropDownId = this.grid.id() + "_groupby_modalDialog_layoutsDD",
				modalDialogDropDownAreaWidth = o.modalDialogDropDownAreaWidth || o.modalDialogDropDownWidth,
				columnLayoutsData = this.grid.options.columnLayouts.slice(0),
				$layoutsContainer;

			$layoutsContainer = $("<div></div>")
				.attr("id", this.grid.id() + "_groupby_modalDialog_layoutsContainer")
				.addClass(css.modalDialogLayoutsContainer)
				.appendTo($content);

			htmlLayouts = "<div style='float: left;'>" + o.modalDialogDropDownLabel + "</div>" +
				"<div class='" + css.dialogLayoutsDD + "'>" +
				"	<div id='" + this.grid.id() + "_groupby_modalDialog_layoutsDDField' class='" +
				css.dialogLayoutsDDField + "'>" + o.modalDialogRootLevelHierarchicalGrid + "</div>" +
				"	<span id='" + this.grid.id() +
				"_groupby_modalDialog_layoutsDDButton' style='float:right;'>^</span>" +
				"	<div style='clear: both;'></div>" +
				"</div>" +
				"<div style='clear: both;'></div>";

			$layoutsContainer.html(htmlLayouts);
			$buttonDD = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog_layoutsDDButton");
			$buttonDD.wrap("<a href='#' title='" + o.modalDialogDropDownButtonCaption + " '></a>");
			cancelFunc = function (e) { e.preventDefault(); e.stopPropagation(); };

			$buttonDD.closest("div").bind({
				mousedown: function (event) {
					self._toggleDropDown(event, false);
				},
				mouseup: cancelFunc,
				click: cancelFunc
			});

			$buttonDD.addClass(css.dialogLayoutsDDButton);
			if (this._checkIsGroupByInherit(this.grid.options) === false) {
				columnLayoutsData = this._getEnabledGroupByLayouts(columnLayoutsData);
			}
			if (columnLayoutsData.length > 0 || this._checkIsGroupByEnabled(this.grid.options)) {
				$dropDown = this.grid.container().find("#" + dropDownId);
				$dropDown.remove();

				/* check whether dropdown container is initialized and if not - initialize */
				$dropDown = $("<div></div>");
				$dropDown
					.attr("id", dropDownId).css({ "position": "absolute", "display": "none", "z-index": "1000" })
					.addClass(this.css.dropDownLayoutsContainer)
					.appendTo(this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog_content"))
					.bind({
						/* M.H. 26 Mar 2014 Fix for bug #160244: When clicking the scrollbar of the dropdown in GrouBy it closes before selecting an item from the tree */
						mousedown: function () {
							clearTimeout(self._dropDownTimeout);
							setTimeout(function () {
								$buttonDD.closest("a").focus();
							}, 10);
						}
					});
				if (o.modalDialogDropDownWidth) {
					$buttonDD.closest("div").width(o.modalDialogDropDownWidth);
				}
				if (modalDialogDropDownAreaWidth) {
					$dropDown.width(modalDialogDropDownAreaWidth);
				}
				/* M.H. 26 Mar 2014 Fix for bug #160241: Long text exceeds the size of the dropdown in GroupBy modal dialog */
				if (o.modalDialogDropDownWidth &&
					(!o.modalDialogDropDownWidth.indexOf || o.modalDialogDropDownWidth.indexOf("%") === -1)) {
					w = $buttonDD.closest("div").width() - $buttonDD.outerWidth();
					$ddField = this.grid.container()
						.find("#" + this.grid.id() + "_groupby_modalDialog_layoutsDDField");
					$ddField.width(w);
					dW = $ddField.outerWidth() - w;
					if (dW > 0) {
						w -= dW;
						$ddField.width(w - 2);
					}
				}
				$tree = $("<div></div>")
					.attr("id", this.grid.id() + "_groupby_modal_treeLayouts")
					.addClass(css.modalDialogTreeContainer);
				$tree.appendTo($dropDown);
				columnLayoutsData = [ { key: "Root", columnLayouts: columnLayoutsData, isRoot: true } ];

				$tree.igTree({
					dataSourceType: "json",
					dataSource: columnLayoutsData,
					initialExpandDepth: 1000000,
					bindings: {
						textKey: "key",
						childDataProperty: "columnLayouts"
					},
					nodeClick: function (event, args) {
						var data = args.node.data, keyLayout = data.key,
							$groupedUL = self.grid.container()
							.find("#" + self.grid.id() + "_groupby_modalDialog_groupedcolumns ul"),
							$ungroupedUL = self.grid.container()
							.find("#" + self.grid.id() + "_groupby_modalDialog_ungroupedcolumns ul");
						/* M.H. 13 March 2012 Fix for bug #104646 */
						if (data.dislabledGroupBy === true) {
							return;
						}
						$groupedUL.empty();
						$ungroupedUL.empty();
						self.grid.container()
							.find("#" + self.grid.id() + "_groupby_modalDialog_layoutsDDField").text(keyLayout);
						if (args.node.path.length === 1) {
							self._renderColumnsDialog(self.grid.options.columns);
							self._currentTreeLayer = null;
						} else {
							self._currentTreeLayer = keyLayout;
							self._renderColumnsDialog(data.columns, keyLayout);
						}
						self._toggleDropDown();
					}
				});

				/* M.H. 14 March 2012 Fix for bug #104647 */
				fn = function () {
					self._dropDownTimeout = setTimeout(function () {
						self.closeDropDown();
					}, 200);
				};
				$buttonDD.closest("a").bind({
					focus: function () {
						self._igTreeHasFocus = true;
						clearTimeout(self._dropDownTimeout);
					},
					blur: function () {
						self._igTreeHasFocus = false;
						fn();
					}
				});
				/* M.H. 14 March 2012 Fix for bug #104647 */
				$tree.find("a").bind({
					focus: function () {
						clearTimeout(self._dropDownTimeout);
					},
					blur: function () {
						fn();
					}
				});

				$tree.find("span.ui-igtree-expander.ui-icon").bind({
					click: function () {
						clearTimeout(self._dropDownTimeout);
						setTimeout(function () {
							$buttonDD.closest("a").focus();
						}, 10);
					}
				});
			}
		},
		openDropDown: function () {
			/* Open layouts dropdown */
			var $dropDown = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog_layoutsDD");
			if ($dropDown.is(":visible") === false) {
				this._toggleDropDown();
			}
		},
		closeDropDown: function () {
			/* Close layouts dropdown */
			var $dropDown = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog_layoutsDD");
			if ($dropDown.is(":visible") === true) {
				this._toggleDropDown();
			}
		},
		/* M.H. 13 March 2012 Fix for bug #104646 */
		_getEnabledGroupByLayouts: function (layouts) {
			/* get only those layouts which are groupby enabled(also inherit can be applied) */
			var resultLayouts = [  ], i, tempChildLayouts, layout;
			if (layouts) {
				for (i = 0; i < layouts.length; i++) {
					tempChildLayouts = null;
					/* M.H. 14 March 2012 Fix for bug #104823 */
					layout = $.extend(true, {}, layouts[ i ]);

					if (layout.columnLayouts && this._checkIsGroupByInherit(layout) === false) {
						tempChildLayouts = this._getEnabledGroupByLayouts(layout.columnLayouts);
						if (tempChildLayouts && tempChildLayouts.length > 0) {
							/* M.H. 15 March 2012 Fix for bug #104646 */
							layout.columnLayouts = tempChildLayouts;
						} else {
							/* M.H. 15 March 2012 Fix for bug #104646 */
							layout.columnLayouts = null;
						}
					}
					if (this._checkIsGroupByEnabled(layout)) {
						resultLayouts.push(layout);
					} else if (layout.columnLayouts !== null && layout.columnLayouts !== undefined) {
						layout.dislabledGroupBy = true;
						resultLayouts.push(layout);
					}
				}
			}

			return resultLayouts;
		},
		_checkIsGroupByInherit: function (layout) {
			var i;

			if (layout && layout.features) {
				for (i = 0; i < layout.features.length; i++) {
					if (layout.features[ i ].name === "GroupBy" && layout.features[ i ].inherit === true) {
						return true;
					}
				}
			}
			return false;
		},
		_checkIsGroupByEnabled: function (layout) {
			var i;

			if (layout && layout.features) {
				for (i = 0; i < layout.features.length; i++) {
					if (layout.features[ i ].name === "GroupBy") {
						return true;
					}
				}
			}
			return false;
		},
		_toggleDropDown: function () {
			/* show/hide feature chooser dialog */
			var $button = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog_layoutsDDButton").parent(),
				$modalDialogContent = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog_content"),
				$layoutsDDField = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog_layoutsDDField").parent(),
				left = 0,
				top = 0,
				$dropDown = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog_layoutsDD"),
				isVisible = ($dropDown.is(":visible") === true),
				self = this;
			if ($dropDown.data("isAnimating") === true) {
				return;
			}
			if (!isVisible) {
				/* M.H. 3 April 2012 Fix for bug #104645 */
				top = $layoutsDDField.offset().top -
					$modalDialogContent.offset().top + $layoutsDDField.height() + 1;
				left = $layoutsDDField.offset().left - $modalDialogContent.offset().left;
				$dropDown.css({ "top": top, "left": left });
			}
			$dropDown.data("isAnimating", true);
			$dropDown.slideToggle(
				400,
				function () {
					var $layoutsDD = self.grid.container()
						.find("#" + self.grid.id() + "_groupby_modalDialog_layoutsDD"),
						$tree = self.grid.container()
						.find("#" + self.grid.id() + "_groupby_modal_treeLayouts");
					if ($dropDown.is(":visible") === true) {
						$button.focus();
						self._activeDropdown = $dropDown;
						if (!self.options.modalDialogDropDownAreaWidth && !self.options.modalDialogDropDownWidth) {
							if ($layoutsDD.width() !== $tree.width()) {
								$layoutsDD.width($tree.width());
							}
						}
						/* $button.children("span:first").addClass(self.css.headerButtonIconSelected); */
					} else {
						self._activeDropdown = null;
						/* $button.children("span:first").removeClass(self.css.headerButtonIconSelected); */
					}
					$dropDown.data("isAnimating", false);
				}
			);
		},
		_groupByDialogButtonOKClick: function (e) {
			var noCancel, noCancelGroupBy, i, j, len, tempGroupedCol, copy, o = this.options, $hTable,
				modalDialog = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog"),
				isToCallGroupedColumnsChanged = false;
			/* M.H. 13 March 2012 Fix for bug #104524 */
			noCancel = this._trigger(this.events.modalDialogButtonApplyClick, null,
				{
					modalDialogElement: modalDialog,
					owner: this, groupedColumns: this._tempGroupedColumns,
					groupedColumnLayouts: this._tempOptions.columnLayouts,
					sortingExpr: this._tempSortingExpr
				});
			if (noCancel) {
				noCancelGroupBy = this._trigger(this.events.groupedColumnsChanging, e, {
					owner: this,
					groupedColumns: o.groupedColumns,
					newGroupedColumns: {
						columns: this._tempGroupedColumns,
						layouts: this._tempOptions.columnLayouts
					},
					triggeredBy: "modalDialog"
				});
				if (noCancelGroupBy) {
					/* M.H. 4 April 2012 Fix for bug #106390 */
					if (this._orderedGroupedCols.length !== this._tempOrderedGroupedCols.length) {
						isToCallGroupedColumnsChanged = true;
					} else {
						len = this._orderedGroupedCols.length;
						for (i = 0; i < len; i++) {
							tempGroupedCol = this._tempOrderedGroupedCols[ i ];
							for (j = 0; j < len; j++) {
								if (tempGroupedCol.key === this._orderedGroupedCols[ j ].key &&
										tempGroupedCol.layout === this._orderedGroupedCols[ j ].layout) {
									break;
								}
							}
							if (j === len) {
								isToCallGroupedColumnsChanged = true;
								break;
							}
						}
					}
					/* M.H. 23 March 2012 Fix for bug #105946
					/* M.H. 28 March 2012 Fix for bug #106980 */
					/*if (this._hierarchical) { */
					this._orderedGroupedCols = this._tempOrderedGroupedCols;
					/*} */
					if (o.groupedColumns) {
						o.groupedColumns = this._tempGroupedColumns;
					}

					if (this.grid.options.columnLayouts) {
						this.grid.options.columnLayouts = this._tempOptions.columnLayouts;
					}

					if (this.grid.dataSource.settings.sorting.expressions) {
						this.grid.dataSource.settings.sorting.expressions = this._tempSortingExpr;
					}
					/* M.H. 20 March 2012 Fix for bug #105273 */
					if (this._isToRenderClearAllButton() === false) {
						if ($(".ui-iggrid-groupbyarea", this.grid.container()).find("ul li").length === 0) {
							this._isgroup = null;
						} else {
							this._isgroup = false;
						}
					} else {
						this._isgroup = true;
					}
					$hTable = this.grid.headersTable();
					/* M.H. 15 March 2012 Fix for bug #104507 */
					if (o.groupedColumns.length === 0 &&
						$hTable.find("thead tr").children().first().hasClass("ui-iggrid-expandheadercellgb")) {
						/* M.H. 16 July 2015 Fix for bug 201937: When width is 100% and you group and ungroup columns in IE width of the grid is not properly calculated */
						if ($.ig.util.isIE) {
							this.grid.element.children("tbody").hide();
							$hTable.hide();
						}
						this.grid.element.find("colgroup").children().first().remove();
						if (this.grid.options.fixedHeaders) {
							$hTable.find("colgroup").children().first().remove();
						}
						/* M.H. 12 Oct. 2011 Fix for bug #90815 */
						if (this.grid.options.fixedFooters && this.grid.options.height !== null) {
							this.grid.footersTable().find("colgroup").children().first().remove();
						}
						/* L.A. 04 January 2013 - Fixing bug #129797 A column"s header cell is removed instead
						 of the group-by expander header cell when ungrouping is performed (either through the UI or programatically)
						 while a column is being grouped (remote GroupBy) */
						$hTable.find("thead tr").children("[ data-skip ]").first().remove();
					}
					/* M.H. 30 Oct 2012 Fix for bug #117396*/
					copy = this._orderedGroupedCols.slice(0);
					this._orderedGroupedCols = [  ];
					this._ensureLabelsOrder(copy, this._orderedGroupedCols, this.grid.options);
					this._rebind();
					if ($.ig.util.isIE && !$hTable.is(":visible")) {
						$hTable.show();
					}
					/* M.H. 29 May 2012 Fix for bug #112881 */
					if (this.grid.options.width !== null && this.grid.options.width !== undefined) {
						this.grid._updateGridContentWidth();
					}
					this.grid._trigger("headerExtraCellsModified", null, { owner: this });

					/* M.H. 4 April 2012 Fix for bug #106390
					 M.H. 6 April 2012 Fix for bug #108338*/
					if (isToCallGroupedColumnsChanged === true) {
						this.grid._onGroupedColumnsChanged(this._tempGroupedColumns);
						this._trigger(this.events.groupedColumnsChanged, e, {
							owner: this,
							groupedColumns: this._tempGroupedColumns,
							triggeredBy: "modalDialog"
							/*key: key,
							layout: layout,
							grid: cgrid */
						});
					}
				}
				e.preventDefault();
				e.stopPropagation();
				modalDialog.igGridModalDialog("closeModalDialog");
			}
		},
		_setSortingTempExpr: function (key, dir, layout) {
			var i, layouts,
				j, gc = this._tempGroupedColumns, gcTemp, sexpr, s = this._tempSortingExpr, settings, lobj;

			sexpr = { fieldName: key, dir: dir, layout: layout, isGroupBy: true };
			/* M.H. 19 March 2012 Fix for bug #104507 - remove sorting when dir is not specified(when it is "")*/
			if (dir === "") {
				for (j = 0; j < s.length; j++) {
					if (s[ j ].fieldName === key &&
						(s[ j ].layout === layout || (!s[ j ].layout && !layout))) {
						s.splice(j, 1);
						break;
					}
				}
				return;
			}
			if (gc.length === 0 && !layout) {
				/* if we are inside of changeSortState and the count is zero, get all grouped cols)*/
				gcTemp = this._tempGroupedColumns.slice(0);
				this._allGroupedCols(gcTemp);
				gc = gcTemp.slice(0);
			}
			for (i = 0; i < gc.length; i++) {
				if (gc[ i ].key === key) {
					gc[ i ].dir = dir;
					for (j = 0; j < s.length; j++) {
						if (s[ j ].fieldName === key) {
							s[ j ].dir = gc[ i ].dir;
							if (gc[ i ].layout) {
								s[ j ].layout = gc[ i ].layout;
							}
							break;
						}
					}
					if (j === s.length) {
						/* M.H. 17 May 2012 Fix for bug #111331*/
						this._addSortingExpressions(s, sexpr);
						/*s.push(sexpr); */
					}
					break;
				}
			}
			if (layout) {
				/* M.H. 28 March 2012 Fix for bug #106952*/
				for (i = 0; i < this._tempOrderedGroupedCols.length; i++) {
					if (this._tempOrderedGroupedCols[ i ].key === key &&
							((this._tempOrderedGroupedCols.layout && this._tempOrderedGroupedCols.layout === layout) ||
						!this._tempOrderedGroupedCols.layout)) {
						this._tempOrderedGroupedCols[ i ].dir = dir;
						break;
					}
				}

				for (j = 0; j < s.length; j++) {
					if (s[ j ].fieldName === key && s[ j ].layout === layout) {
						s[ j ].dir = dir;
						break;
					}
				}
				if (j === s.length) {
					/* M.H. 17 May 2012 Fix for bug #111331 */
					this._addSortingExpressions(s, sexpr);
					/* s.push(sexpr);*/
				}
				/* find the layout and change the settings */
				layouts = [  ];
				this._findLayout(layout, this._tempOptions, layouts);
				lobj = layouts[ 0 ];
				for (i = 0; lobj.features && i < lobj.features.length; i++) {
					if (lobj.features[ i ].name === "GroupBy") {
						/* find the col setting in order to change the sort direction */
						settings = lobj.features[ i ].columnSettings;
						for (j = 0; j < settings.length; j++) {
							if (settings[ j ].columnKey === key) {
								settings[ j ].dir = dir;
								break;
							}
						}
						break;
					}
				}
			}
		},
		_setGroupByTempExpr: function (key, isGroupBy, dir, layout) {
			var i, gexpr, expr, lobj, c, fcs, setting, layouts;

			if (layout) {
				layouts = [  ];
				this._findLayout(layout, this._tempOptions, layouts);
				lobj = layouts[ 0 ];
				/* lobj = $.extend(true, {}, layouts[ 0 ]); */
				c = this._getColumnByLayoutKey(key, lobj.columns);
			} else {
				lobj = this.grid.options;
				c = this.grid.columnByKey(key);
			}
			if (layout) {
				if (!lobj.features) {
					lobj.features = [  ];
					lobj.features.push({ name: "GroupBy", columnSettings: [  ] });
					fcs = lobj.features[ 0 ].columnSettings;
				} else {
					for (i = 0; i < lobj.features.length; i++) {
						if (lobj.features[ i ].name === "GroupBy") {
							fcs = lobj.features[ i ].columnSettings;
							if (!fcs) {
								lobj.features[ i ].columnSettings = [  ];
								fcs = lobj.features[ i ].columnSettings;
							}
							break;
						}
					}
				}
				for (i = 0; i < fcs.length; i++) {
					if (fcs[ i ].columnKey === key) {
						if (!isGroupBy) {
							fcs.splice(i, 1);
						} else {
							setting = fcs[ i ];
						}
						break;
					}
				}

				if (isGroupBy) {
					if (!setting) {
						fcs.push({ columnKey: key, isGroupBy: isGroupBy, dir: dir });
					} else {
						setting.isGroupBy = isGroupBy;
						setting.dir = dir;
					}
				}
			}
			/* if the layout is defined we need to store that layout in the data source, and set the group expression to the layout"s settings
			 where it belongs to*/
			gexpr = { key: key, dir: dir, layout: layout, col: c };

			if (!layout) {
				expr = this._tempGroupedColumns;

				for (i = 0; i < expr.length; i++) {
					if (expr[ i ].key === key) {
						expr.splice(i, 1);
						break;
					}
				}
				if (isGroupBy) {
					expr = expr.concat(gexpr);
				}
				this._tempGroupedColumns = expr;
			}
			if (isGroupBy) {
				this._tempOrderedGroupedCols.push(gexpr);
			} else {
				for (i = 0; i < this._tempOrderedGroupedCols.length; i++) {
					if (this._tempOrderedGroupedCols[ i ].key === key &&
							((this._tempOrderedGroupedCols.layout && this._tempOrderedGroupedCols.layout === layout) ||
						!this._tempOrderedGroupedCols.layout)) {
						$.ig.removeFromArray(this._tempOrderedGroupedCols, i);
						break;
					}
				}
			}
			/* M.H. 19 March 2012 Fix for bug #104507 */
			this._setSortingTempExpr(key, dir, layout);
		},
		/* M.H. 19 Sep 2013 Fix for bug #131441: checkColumnIsGrouped API doesn't work */
		_checkColumnIsGroupedInternal: function (key, layout, groupedColumns, options) {
			var isGrouped = false, i, j, layouts, lobj, fcs;

			if (!layout) {
				for (i = 0; i < groupedColumns.length; i++) {
					if (groupedColumns[ i ].key === key) {
						isGrouped = true;
						break;
					}
				}
			} else {
				layouts = [  ];
				this._findLayout(layout, options, layouts);
				lobj = layouts[ 0 ];
				if (lobj && lobj.features) {
					for (i = 0; i < lobj.features.length; i++) {
						if (lobj.features[ i ].name === "GroupBy") {
							fcs = lobj.features[ i ].columnSettings;
							if (fcs) {
								for (j = 0; j < fcs.length; j++) {
									if (fcs[ j ].columnKey === key) {
										if (fcs[ j ].isGroupBy) {
											isGrouped = true;
										}
										break;
									}
								}
							}
							break;
						}
					}
				}
			}
			return isGrouped;
		},
		checkColumnIsGrouped: function (key, layout) {
			/* Check whether column with specified key and layout is grouped
			paramType="string" key of the column
			paramType="string" layout name
			*/
			return this._checkColumnIsGroupedInternal(
				key,
				layout,
				this.options.groupedColumns,
				this.grid.options
				);
		},
		_getAllColumns: function (columns, columnsRes) {
			var i, columnsLength = columns.length;

			for (i = 0; i < columnsLength; i++) {
				if (columns[ i ].group) {
					this._getAllColumns(columns[ i ].group, columnsRes);
				} else {
					columnsRes.push(columns[ i ]);
				}
			}
		},
		_renderColumnsDialog: function (columns, layout) {
			var self = this, columnsRes = [  ];

			if (this.grid._isMultiColumnGrid) {
				this._getAllColumns(columns, columnsRes);
			} else {
				columnsRes = columns;
			}

			$.each(columnsRes, function (columnIndex, column) {
				var cs = self._findColumnSetting(column.key);
				/* M.H. 7 Sep 2012 Fix for bug #120471 - do not show unbound columns in column list of the advanced dialog
				 M.H. 12 Sep 2012 Fix for bug #121078 - Selecting a child grid where there"s at least one unbound column causes a JS error when GroupBy is remote and inherited
				 M.H. 10 Oct 2012 Fix for bug #123203 - Unbound column(s) are not listed in local group-by"s modal dialog */
				if (self.options.type === "remote" && (column.unboundDS === true || column.unbound === true)) {
					return true;
				}
				/* M.H. 21 March 2012 Fix for bug #105590 */
				if (!cs || cs.allowGrouping) {
					/* M.H. 19 Sep 2013 Fix for bug #131441: checkColumnIsGrouped API doesn"t work */
					if (self._checkColumnIsGroupedInternal(
						column.key, layout, self._tempGroupedColumns, self._tempOptions)) {
						self._renderDialogGroupedColumn(column, layout);
					} else {
						self._renderDialogUngroupedColumn(column, layout);
					}
				}
			});
		},
		_clearAllGroupedColumns: function () {
			this._tempGroupedColumns = [  ];
			this._clearAllGroupedColumnsLayout(this._tempOptions);
		},
		_clearAllGroupedColumnsLayout: function (layout) {
			var i, fcs;
			if (layout !== null && layout.features) {
				for (i = 0; i < layout.features.length; i++) {
					if (layout.features[ i ].name === "GroupBy") {
						fcs = layout.features[ i ].columnSettings;
						if (fcs) {
							layout.features[ i ].columnSettings = [  ];
						}
					}
				}
			}

			for (i = 0; layout.columnLayouts && i < layout.columnLayouts.length; i++) {
				this._clearAllGroupedColumnsLayout(layout.columnLayouts[ i ]);
			}
		},
		_isToRenderClearAllButton: function () {
			/* M.H. 8 March 2012 Fix for bug #103407 */
			if (this.options.modalDialogGroupByOnClick === true) {
				return true;
			}
			if (this._tempGroupedColumns.length > 0) {
				return true;
			}

			if (this._checkRenderLayoutsClearAll(this.grid.options) === true) {
				return true;
			}

			return false;
		},
		_checkRenderLayoutsClearAll: function (layout) {
			var i, j, fcs;
			if (layout !== null && layout.features) {
				for (i = 0; i < layout.features.length; i++) {
					if (layout.features[ i ].name === "GroupBy") {
						fcs = layout.features[ i ].columnSettings;
						if (fcs) {
							for (j = 0; j < fcs.length; j++) {
								if (fcs[ j ].isGroupBy) {
									return true;
								}
							}
						}
					}
				}
			}

			for (i = 0; layout.columnLayouts && i < layout.columnLayouts.length; i++) {
				if (this._checkRenderLayoutsClearAll(layout.columnLayouts[ i ]) === true) {
					return true;
				}
			}
			return false;
		},
		_renderDialogGroupedColumn: function (column, layout) {
			var i, j, s, $li, liHTML, $buttonUngroup,
				o = this.options,
				self = this,
				css = this.css,
				key = column.key,
				$ul = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog_groupedcolumns ul"),
				buttonAscDescId = this.grid.id() + "_" + key + "_groupbydialog_groupedcolumns_buttonascdesc",
				buttonUngroupId = this.grid.id() + "_" + key + "_groupbydialog_groupedcolumns_buttonungroup",
				dir = "asc",
				gc = null;
			/* L.A. 03 October 2012 - Fixing bug #122446 - Group by dialog doesn"t look right when you group a couple of columns in IE8.
			 The cruel idea is to cause the IE8 to flush the dialog rendering by just reading the width value */
			this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog").css("width");
			if (!layout) {
				for (i = 0; i < self._tempGroupedColumns.length; i++) {
					if (self._tempGroupedColumns[ i ].key === key) {
						gc = self._tempGroupedColumns[ i ];
						dir = gc.dir;
						break;
					}
				}
			} else {
				s = self._tempSortingExpr;
				for (j = 0; j < s.length; j++) {
					if (s[ j ].fieldName === key && s[ j ].layout === layout) {
						dir = s[ j ].dir;
						break;
					}
				}
			}
			$li = $("<li tabIndex='0'></li>")
				.attr("id", self.grid.id() + "_" + key + "_groupbydialog_grouped_li")
				.addClass(css.dialogGroupedItem);
			$li.appendTo($ul);
			/*this._insertColumnModalDialog($ul, $li, columnSetting.columnIndex); */
			liHTML = "<span id='" + buttonAscDescId + "' class='" +
				css.dialogButtonAsc + "' role='button' title='" + o.modalDialogCaptionButtonAsc + "'>" +
				"	<span class='" + css.dialogButtonAscIcon + "'></span>" +
				"</span>" +
				"<span class='" + css.dialogGroupedColumnTextContainer + "'>" +
				column.headerText + "</span>" +
				"<button id='" + buttonUngroupId + "' class='" + css.dialogButtonUngroup +
				"' role='button' title='" + o.modalDialogCaptionButtonUngroup + "' aria-label='" +
				o.modalDialogCaptionButtonUngroup + " " + column.headerText + "' >" +
				"	<span class='" + css.dialogButtonUngroupContainer + "'></span>" +
				"	<span class='ui-button-text'>" + o.modalDialogCaptionButtonUngroup + "</span>" +
				"</button>";
			$li.html(liHTML);
			$buttonUngroup = this.grid.container().find("#" + buttonUngroupId);
			self._setDialogButtonAscDesc((dir === "asc"), key);

			$li.bind({
				keydown: function (e) {
					if (e.keyCode === $.ui.keyCode.ENTER || e.keyCode === $.ui.keyCode.SPACE) {
						$li.click();
						e.preventDefault();
						e.stopPropagation();
					}
				},
				click: function () {
					self._dialogButtonAscDescClick(key, layout);
				}
			});
			$buttonUngroup.bind({
				keydown: function (e) {
					if (e.keyCode === $.ui.keyCode.ENTER || e.keyCode === $.ui.keyCode.SPACE) {
						e.target.click();
						e.preventDefault();
						e.stopPropagation();
					}
				},
				click: function (e) {
					var noCancel, $nextLi;
					/* M.H. 15 March 2012 Fix for bug #104449 */
					noCancel = self._trigger(self.events.modalDialogUngroupingColumn, e,
						{ key: key, layout: layout, owner: self });
					if (noCancel) {
						$li.remove();
						if (o.modalDialogGroupByOnClick === true) {
							self.ungroupByColumn(key, layout);
						} else {
							self._setGroupByTempExpr(key, false, "", layout);
						}
						$nextLi = self._renderDialogUngroupedColumn(column, layout);
						$nextLi.find(":focusable").first().focus(1);
						/* M.H. 19 March 2012 Fix for bug #105102 */
						if (self.grid.container().find("#" +
							self.grid.id() + "_groupby_modalDialog_groupedcolumns ul li").length === 0 &&
								self._isToRenderClearAllButton() === false) {
							self.grid.container().find("#" + self.grid.id() + "_groupbydialog_reset_button").remove();
						}
						/* M.H. 15 March 2012 Fix for bug #104449 */
						self._trigger(self.events.modalDialogUngroupColumn, e,
							{ key: key, layout: layout, owner: self, groupedColumns: self._tempGroupedColumns });
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
		_dialogButtonAscDescClick: function (key, layout) {
			var o = this.options,
				$buttonAscDesc = this.grid.container().find("#" + this.grid.id() + "_" +
				key + "_groupbydialog_groupedcolumns_buttonascdesc"),
				noCancel,
				modalDialog = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog"),
				isAsc = $buttonAscDesc.data("isAsc");
			/* M.H. 13 March 2012 Fix for bug #104705 */
			noCancel = this._trigger(this.events.modalDialogSortGroupedColumn, null,
				{ modalDialogElement: modalDialog, owner: this, key: key, isAsc: !isAsc, layout: layout });
			if (noCancel) {
				if (isAsc === true) {
					if (o.modalDialogGroupByOnClick === true) {
						this._setSortingTempExpr(key, "desc", layout);
						this.grid.dataSource.settings.sorting.expressions = this._tempSortingExpr;
						this._rebind();
					} else {
						this._setSortingTempExpr(key, "desc", layout);
					}
				} else {
					if (o.modalDialogGroupByOnClick === true) {
						this._setSortingTempExpr(key, "asc", layout);
						this.grid.dataSource.settings.sorting.expressions = this._tempSortingExpr;
						this._rebind();
					} else {
						this._setSortingTempExpr(key, "asc", layout);
					}
				}
				this._setDialogButtonAscDesc(!isAsc, key);
			}
		},
		_setDialogButtonAscDesc: function (isAsc, key) {
			var css = this.css,
				$buttonAscDesc = this.grid.container().find("#" + this.grid.id() + "_" +
				key + "_groupbydialog_groupedcolumns_buttonascdesc"),
				$spanIcon = $buttonAscDesc.find("span:eq(0)"),
				caption;
			if (isAsc === true) {
				$buttonAscDesc
					.removeClass(css.dialogButtonDesc)
					.addClass(css.dialogButtonAsc);
				$spanIcon
					.removeClass(css.dialogButtonDescIcon)
					.addClass(css.dialogButtonAscIcon);
				caption = this.options.modalDialogCaptionButtonAsc;
			} else {
				$buttonAscDesc
					.removeClass(css.dialogButtonAsc)
					.addClass(css.dialogButtonDesc);
				$spanIcon
					.removeClass(css.dialogButtonAscIcon)
					.addClass(css.dialogButtonDescIcon);
				caption = this.options.modalDialogCaptionButtonDesc;
			}
			/* M.H. 13 March 2012 Fix for bug #104509 */
			$buttonAscDesc.data("isAsc", isAsc).attr("title", caption);
		},
		_renderDialogButtonClearAll: function () {
			/* M.H. 8 March 2012 Fix for bug #103407 */
			if (this.options.modalDialogGroupByOnClick === true) {
				return;
			}
			var self = this,
				o = this.options,
				resetButtonId = self.grid.id() + "_groupbydialog_reset_button",
				modalDialog = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog"),
				$captionButtonContainer,
				$resetButton;
			if (this.grid.container().find("#" + resetButtonId).length === 0) {
				$captionButtonContainer = modalDialog.igGridModalDialog("getCaptionButtonContainer");
				$resetButton = $("<button></button>")
					.attr("id", resetButtonId)
					.appendTo($captionButtonContainer);
				$resetButton.igButton({
					labelText: o.modalDialogClearAllButtonLabel,
					click: function (e) {
						var noCancel, $groupedUL, $ungroupedUL, layouts = [  ];
						noCancel = self._trigger(self.events.modalDialogButtonResetClick, e,
							{ modalDialogElement: modalDialog, owner: self });
						if (noCancel) {
							self._clearAllGroupedColumns();
							/* M.H. 12 March 2012 Fix for bug #104507 */
							self._tempSortingExpr = self.grid.dataSource.settings.sorting.expressions.slice(0);
							$groupedUL = self.grid.container()
								.find("#" + self.grid.id() + "_groupby_modalDialog_groupedcolumns ul");
							$ungroupedUL = self.grid.container()
								.find("#" + self.grid.id() + "_groupby_modalDialog_ungroupedcolumns ul");
							$groupedUL.empty();
							$ungroupedUL.empty();
							/* M.H. 22 March 2012 Fix for bug #105360 */
							self._tempSortingExpr = [  ];
							/* M.H. 23 March 2012 Fix for bug #105946 */
							self._tempOrderedGroupedCols = [  ];

							if (!self._currentTreeLayer) {
								self._renderColumnsDialog(self.grid.options.columns);
							} else {
								self._findLayout(self._currentTreeLayer, self._tempOptions, layouts);
								if (layouts.length > 0) {
									self._renderColumnsDialog(layouts[ 0 ].columns, self._currentTreeLayer);
								}
							}
							$(this).remove();
						}
					}
				});
			}
		},
		_renderDialogUngroupedColumn: function (column, layout) {
			var self = this, $li, $a,
				key = column.key,
				o = this.options,
				css = this.css,
				$ul = this.grid.container()
				.find("#" + this.grid.id() + "_groupby_modalDialog_ungroupedcolumns ul");
			$li = $("<li></li>")
				.attr("id", self.grid.id() + "_" + key + "_groupbydialog_grouped_li")
				.addClass(css.dialogUngroupedItem)
				.append("<span class='" + css.dialogUngroupedColumnsGroupByButton +
				"'><a href='#'></a></span> <span class='" + css.dialogUngroupedColumnsText +
				"'>" + column.headerText + "</span>");
			/*this._insertColumnModalDialog($ul, $li, columnSetting.columnIndex); */
			$li.appendTo($ul);
			$a = $li.find("a:first");
			$a.addClass(css.modalDialogSortByColumn);
			$a.html(o.modalDialogGroupByButtonText);
			$a.attr("aria-label", o.modalDialogGroupByButtonText + " " + column.headerText);
			$li.bind({
				click: function (e) {
					var noCancel, $nextLi;
					/* M.H. 15 March 2012 Fix for bug #104449 */
					noCancel = self._trigger(self.events.modalDialogGroupingColumn,
						e, { key: key, layout: layout, owner: self });
					if (noCancel) {
						$li.remove();
						if (o.modalDialogGroupByOnClick) {
							self.groupByColumn(key, layout);
						} else {
							/* M.H. 11 Mar 2014 Fix for bug #162907: GroupBy does not have option to set sort direction */
							self._setGroupByTempExpr(key, true, o.defaultSortingDirection, layout);
						}
						self._renderDialogButtonClearAll();
						$nextLi = self._renderDialogGroupedColumn(column, layout);
						$nextLi.find(":focusable").first().focus(1);
						self._renderDialogButtonClearAll();
						/* M.H. 15 March 2012 Fix for bug #104449 */
						self._trigger(self.events.modalDialogGroupColumn, e,
							{ key: key, layout: layout, owner: self, groupedColumns: self._tempGroupedColumns });
					}
					e.preventDefault();
					e.stopPropagation();
				}
			});
			return $li;
		},
		_setGroupAreaTextContent: function ($area) {
			var text = this.options.emptyGroupByAreaContent,
				linkText = this.options.emptyGroupByAreaContentSelectColumns,
				linkTextCaption = this.options.emptyGroupByAreaContentSelectColumnsCaption, $a, self = this;

			/* modalDialog.igGridModalDialog("openModalDialog"); */
			text = text.replace("{0}", "<a href='#' id='" + this.grid.id() +
				"_link_selectcolumns' title='" + linkTextCaption + "'>" + linkText + "</a>");
			$area.html(text);
			$a = this.grid.container().find("#" + this.grid.id() + "_link_selectcolumns");
			$a.bind({
				click: function (e) {
					self.openGroupByDialog();
					e.preventDefault();
					e.stopPropagation();
				}
			});
		},
		_headerCellDragCancel: function () {
			/* we should always cancel the "cancelling" of the draggability of headers, done by features such as sorting */
			return false;
		},
		_headerCellRendered: function (event, args) {
			var groupby = this, cs, hg, hgTemp, isMultiColumnHeaderGrid = this.grid._isMultiColumnGrid;
			if (args.owner.id() !== this.grid.id()) {
				return;
			}
			/* M.H. 2 July 2012 Fix for bug #115940 */
			if (args.isMultiColumnHeader === true) {
				return;
			}
			/* M.H. 11 June 2012 Fix for bug #114220 */
			hg = groupby.grid.element.hasClass("ui-iggrid-root") ?
				groupby.grid.container() : groupby.grid.element.closest(".ui-iggrid-root");
			if (hg.length === 0) {
				hg = groupby.grid.container();
			} else {
				hgTemp = hg.closest(".ui-widget");
				if (hgTemp.length > 0) {
					hg = hgTemp;
				}
			}
			/* take all TH elements and attach draggable to them individually
			 take the column key and check if the column is allowed to participate in grouping */
			cs = this._findColumnSetting(args.columnKey);
			/* M.H. 22 June 2015 Fix for bug 190689: On Ie8 when the feature chooser is enabled the header spans for two rows- one with the text and another for the feature chooser icon
			 in IE8 width 100 causes the header spans for 2 rows*/
			if (!$.ig.util.isIE || $.ig.util.browserVersion >= 9) {
				args.th.find(".ui-iggrid-headertext").css("width", "100%");
			}
			if ((cs && cs.allowGrouping) || !cs) {
				args.th.draggable({
					containment: hg,
					appendTo: hg,
					distance: 5,
					revert: "invalid",
					scroll: false,
					/* M.H. 12 Oct. 2011 Fix for bug #91090 */
					cancel: "div.ui-iggrid-indicatorcontainer",
					helper: function (event) {
						var th, helperDOM;
						if ($(event.target).is("span")) {
							groupby._spanDragging = true;
						} else {
							groupby._spanDragging = false;
						}
						th = $(event.target).closest("th");
						/* M.H. 11 July 2012 Fix for bug #115984 */
						helperDOM = th.clone()
						/* A.Y. Bug 91597. Adding "overflow: hidden" so that hiding indicators
						are not visible when dragging a column header */
							.css("overflow", "hidden")
							.width(th.width())
							.addClass("ui-widget ui-iggrid")
							/* M.H. 16 May 2012 Fix for bug #111507
							 L.A. 31 July 2012 Fixing bug #115961
							When the grid has width bigger than the screen and when you drag a column that was outside the initially visible area
							it appears smaller while you drag it to the drop area. */
							.wrap($("<div class='" + groupby.css.dragMarkup + "'/>").width(th.outerWidth()));
						if (isMultiColumnHeaderGrid) {
							helperDOM.height(th.height());
						}
						return helperDOM.parent();
					},
					opacity: groupby.options.labelDragHelperOpacity,
					/*
					cursorAt: {
					bottom: -5,
					top: 5,
					},
					*/
					drag: function (event, ui) {
						var groupbyarea;
						groupby._isDragging = true;
						groupbyarea = $(".ui-iggrid-groupbyarea", hg);
						if (!groupbyarea.hasClass("ui-iggrid-groupbyareahover") && !groupby._movingEnabled) {
							/* change mouse cursor to red (stop) */
							if (groupby._spanDragging) {
								ui.helper.find("span").css("cursor", "no-drop");
							} else {
								ui.helper.css("cursor", "no-drop");
							}
						} else {
							if (groupby._spanDragging) {
								ui.helper.find("span").css("cursor", "move");
							} else {
								ui.helper.css("cursor", "move");
							}
						}
					},
					stop: function () {
						groupby._isDragging = false;
					}
				}).bind({
					mouseover: groupby._headerMouseOverHandler,
					mouseout: groupby._headerMouseOutHandler
				});
				/* if this is a child grid TH that we are dropping, we also need to encode
				 the layout key as a data-* attribute */
				if (args.owner.element.attr("data-childgrid")) {
					args.th
						.attr("data-layout", args.owner.options.key)
						.attr("data-grid-id", args.owner.element.attr("id"));
				}
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
		/* V1 - decorating on existing markup
		_dataRendered: function () {
		start = new Date().getTime();
		var grid = this.grid, groupby = this, count = 0, tmpCount = 0, tmpSum = 0, registeredTd,
		idval = null, indent = this.options.indentation, gc = this.options.groupedColumns,
		grow, expcell, textcell;
		TODO
		if (gc.length > 0) {
		registeredTd = false;
		for (i = 0; i < gc.length; i++) {
		tmpCount = 0;
		 we need to insert group by rows and indentation cells based on the group #
		 iterate through all rendered TRs
		if (!grid.headersTable().find("thead tr th").hasClass("ui-iggrid-expandheadercell")) {
		$("<th></th>").prependTo(grid.headersTable().find("thead tr")).addClass("ui-iggrid-expandheadercell ui-iggrid-header ui-widget-header"); //TODO
		$("<col></col>").prependTo(grid.headersTable().find("colgroup")).width(indent);
		} else if (indent > this.options.indentation) {
		grid.headersTable().find("colgroup col:nth-child(1)").width(indent);
		}
		rows = grid.rows();
		this._tbody.children().map(function (index, element) {
		for (j = 0; j < rows.length; j++) {
		var grow, expcell, textcell;
		 if it"s already a grouped row, return
		if ($(rows[ j ]).attr("data-grouprow")) {
		return;
		continue;
		}
		if (j < tmpCount || tmpCount >= grid.dataSource.dataView().length) {
		return;
		continue;
		}
		if (grid.dataSource.dataView()[ tmpCount ][ gc[ i ].fieldName ] !== idval) {
		idval = grid.dataSource.dataView()[ tmpCount ][ gc[ i ].fieldName ];
		 render new group row
		grow = $("<tr></tr>").insertBefore(rows[ j ]).addClass(grid.css.recordClass)
			.attr("data-grouprow", true).attr("data-state", "expanded").attr("data-glevel", i);
		rows[ j ].parentNode.insertBefore($("<tr></tr>").addClass(grid.css.recordClass)
		.attr("data-grouprow", true).attr("data-state", "expanded").attr("data-glevel", i)[ 0 ], rows[ j ]);
		grow = rows[ j ].previousSibling;
		expcell = $("<td class="ui-iggrid-expandcolumn"><span class="ui-iggrid-expandbutton ui-icon ui-icon-minus ui-iggrid-groupexpandcell"></span></td>").appendTo(grow);
		 set the margin left
		if (i > 0) {
		expcell.children().first().css("margin-left", parseInt(groupby.options.indentation, 10) * i);
		}
		textcell = $("<td></td>").appendTo(grow).attr("colspan", grid.options.columns.length);
		tmpCount = count;
		while (tmpCount < grid.dataSource.dataView().length &&
		grid.dataSource.dataView()[ tmpCount ][ gc[ i ].fieldName ] === idval) {
		tmpCount++;
		tmpSum++;
		 add an extra TD for every tr
		if (!registeredTd) {
			$("<td></td>").prependTo(this).addClass("ui-iggrid-nongrouprowemptycell");
		}
		}
		textcell.text(grid.columnByKey(gc[ i ].fieldName).headerText + ": " + idval + " (" + tmpSum + ")");
		tmpCount = 0;
		tmpSum = 0;
		tmpCount = 0;
		} else {
		if (!registeredTd) {
			$("<td></td>").prependTo(this).addClass("ui-iggrid-nongrouprowemptycell");
		}
		}
		 indentation
		$("<td></td>").prependTo(this);
		count++;
		});
		}
		 add extra TDs for every non-grouped row
		if (!registeredTd) {
		this._tbody.children().map(function () {
		rows = grid.rows();
		for (j = 0; j < rows.length; j++) {
		if ($(rows[ j ]).attr("data-grouprow") !== "true") {
		add extra td
		$("<td></td>").prependTo(rows[ j ]).addClass("ui-iggrid-nongrouprowemptycell");
		}
		});
		}
		}
		indent += indent;
		registeredTd = true;
		}
		finally show the data
		this._tbody.show();
		},
		*/
		/* intercepting renderRecords directly*/
		_renderRecords: function (ui, args) {
			if (args.owner.id() !== this.grid.id()) {
				return;
			}
			if (args.vrtWnd && args.vrtWnd.calledFromGroupBy) {
				return true;
			}
			if (this.options.groupedColumns.length > 0) {
				/* we are going to render the rows ourselves */
				this._renderRecordsInternal(args);
			} else {
				return true;
			}
			return false;
		},
		_recordsRendered: function (ui, args) {
			if (args.calledFromGroupBy) {
				return;
			}
			var currentScrollTop = $(window).scrollTop();
			if (!args.tbody.is(":visible")) {
				args.tbody.css("display", "");
			}
			if (this._scrolltop !== 0 && currentScrollTop !== 0 && this._scrolltop !== currentScrollTop) {
				$(window).scrollTop(this._scrolltop); /* restore the scrolltop */
			}
		},
		_renderRecordsInternal: function (args) {
			/* iterate all records in the data source instead of rendered table rows
			 IMPORTANT: the initial value of idval is really important ! */
			var grid = this.grid, idval = null, ds = grid.dataSource.dataView(), firstTime = true, counter,
				d = [  ], tbody = grid.element.children("tbody"), fh = false, html, i, tbodyHtml, $thDataSkip,
				noCancel = true, gr, groupedRowsLength, tr, countHiddenRows, startIndex = 0, endIndex = 0,
				newFirstVisibleRow, isScrolledToBottom, lastVisibleRow,
				avgRowHeight, dataSkipWidth = 0, scrollContainer;
			/* M.H. 8 Oct 2014 Fix for bug #182146: When Grouping in the hierarchical grid, rowsRendered is fired before rowsRendering */
			noCancel = grid._trigger(grid.events.rowsRendering, null, args);
			if (noCancel) {
				this._indent = this.options.indentation;
				if (grid.options.fixedHeaders === true) {
					fh = true;
				}
				/* S.S. January 21st, 2012 Bug #98209 Colspan calculation should be done on a colgroup level
				 because when showHeader is false for the grid there is no header row to calculate against
				 L.A. 27 March 2012 Fixed bug #106069, 104826, 98209.
				 I"ve combined the previous two fixes into one conditional */
				if (grid._isMultiColumnGrid) {
					/* M.H. 25 Sep 2015 Fix for bug 206738: Length of the data rows when hiding a multicolumn header in igGrid does not get updated in Mozilla Firefox */
					this._colspan = grid.container().find("#" + grid.id() +
						" colgroup:first>col:not([ data-hiding ])").length - 1;
				} else {
					if (grid.options.showHeader) {
						this._colspan = grid.container().find("#" + grid.id() +
							(fh === true ? "_headers" : "") + " thead>tr:first")
							.children("th:not(.ui-iggrid-expandheadercellgb)").length;
					} else {
						this._colspan = grid.container().find("#" + grid.id() + " colgroup:first>col").length - 1;
					}
				}
				/* this._colspan = $("#" + this.grid.id() + (fh === true ? "_headers" : "") + " thead>tr:first")
				.children("th:not(.ui-iggrid-expandheadercellgb)").length;*/
				this._processGroupsRecursive(0, ds, tbody, d, idval, 0, ds.length, firstTime);
				/* we need to insert group by rows and indentation cells based on the group #
				 iterate through all rendered TRs */
				if (!grid.headersTable().find("thead tr th").hasClass("ui-iggrid-expandheadercellgb")) {
					$thDataSkip = $("<th></th>").prependTo(grid.headersTable().find("thead tr:nth-child(1)"))
						.addClass(this.css.expandHeaderCellGroupBy)
						.addClass(this.css.headerExtraCell)
						.attr("data-skip", true);
					if (grid._isMultiColumnGrid) {
						$thDataSkip.attr("rowspan", grid._maxLevel + 1);
					}
				}
				tbody.show();

				html = d.join("");
				this._expandedRowCount = 0;
				if (grid.options.virtualization === true) {
					this._visibleGroupedRows = [  ];
					this._groupedRows = this._tokenizeTBody(html);

					if (this._groupedRows.length > 0) {
						this._groupedRows[ 0 ] = this._groupedRows[ 0 ].substr(0, 3) +
							" data-firstgrouprow='true' " + this._groupedRows[ 0 ].substr(3);
						this._groupedRows[ this._groupedRows.length - 1 ] =
							this._groupedRows[ this._groupedRows.length - 1 ].substr(0, 3) +
							" data-lastrow='true' " + this._groupedRows[ this._groupedRows.length - 1 ].substr(3);
						for (i = this._groupedRows.length - 1; i >= 0; i--) {
							if (this._groupedRows[ i ].indexOf("data-grouprow='true'") >= 0) {
								this._groupedRows[ i ] = this._groupedRows[ i ].substr(0, 3) +
									" data-lastgrouprow='true' " + this._groupedRows[ i ].substr(3);
								break;
							}
						}
						this._virtualRowCount =
							this._determineVirtualRowCount(this._groupedRows, grid._getDisplayContainerHeight());
						if (this._virtualRowCount > this._groupedRows.length) {
							this._virtualRowCount = this._groupedRows.length;
						}

						endIndex = this._virtualRowCount;
						/* M.K. Persist virtual scroll top */
						if (!grid._persistVirtualScrollTop || !grid._prevFirstVisibleTRIndex) {
							grid._scrollTo(0, true);
						}
						tbodyHtml = "";
						/* M.H. 29 Aug 2013 Fix for bug #149879: When using grouping and continuous virtualization the page hangs on scroll.
						 When initialExpand is false. We render only grouping rows and cache the other rows. We show them when expanding grouping rows */
						if (!this.options.initialExpand) {
							this._countGroupedRows = html.match(/(data-grouprow='true')/ig).length;

							this._cachedGroupedRows = this._groupedRows;
							this._visibleRowIndexes = [  ];
							gr = [  ];
							this._groups = {};
							counter = 0;
							groupedRowsLength = this._groupedRows.length;
							countHiddenRows = 0;
							for (i = 0; i < groupedRowsLength; i++) {
								tr = this._groupedRows[ i ];
								if (tr.indexOf("data-grouprow") > -1) {/* .match(/<tr.*style="display: none.*>/)*/
									counter++;
									countHiddenRows = 0;
									/*tr = tr.substr(0, 3) + " data-row-vidx="" + (counter - 1) + "" " + tr.substr(3); */
									tr = tr.replace(/data-row-idx="(\d)+"/i, "data-row-idx='" + (counter - 1) + "'");
									if (tr.indexOf("display:none") === -1) {
										this._visibleRowIndexes.push(counter - 1);
										this._visibleGroupedRows.push(tr);
									}
									gr.push(tr);
								} else {
									if (++countHiddenRows > this._virtualRowCount) {
										continue;
									}
									if (!this._groups[ counter - 1 ]) {
										this._groups[ counter - 1 ] = [  ];
									}
									this._groups[ counter - 1 ].push(tr);
								}
							}
							this._groupedRows = gr;
							gr = null;
							/*M.K. Virtual Row count MUST be calculated based ONLY the visible grouped rows */
							this._virtualRowCount =
								this._determineVirtualRowCount(this._visibleGroupedRows, grid._getDisplayContainerHeight());
							if (this._virtualRowCount > this._visibleGroupedRows.length) {
								this._virtualRowCount = this._visibleGroupedRows.length;
							}
							/*M.K. Fix for bug 190543: When grouping and row virtualization is enabled with initiaExpand:false the last group is not visible.*/
							if (this.grid._prevFirstVisibleTRIndex && this.grid._persistVirtualScrollTop)
							{
								startIndex = this._visibleRowIndexes.indexOf(this.grid._prevFirstVisibleTRIndex);
							}
							endIndex = startIndex + this._virtualRowCount;
							tbodyHtml += this._renderInitiallyCollapsedRows(startIndex, endIndex);
						} else {
							/*M.K. If we should persist virtual scroll top and _prevFirstVisibleTRIndex exists*/
							if (this.grid._prevFirstVisibleTRIndex && this.grid._persistVirtualScrollTop) {
								/*first loaded data row becomes the row which was first visble before*/
								startIndex = this.grid._prevFirstVisibleTRIndex;
								endIndex = startIndex + this._virtualRowCount;

								if (startIndex >= this._groupedRows.length) {
									endIndex = this._groupedRows.length;
									startIndex = this._groupedRows.length - this._virtualRowCount - 1;
								} else {
									endIndex =
										this._determineVirtualRowCount(this._groupedRows, grid._getDisplayContainerHeight()) +
										startIndex;
								}

								if (endIndex > this._groupedRows.length) {
									endIndex = this._groupedRows.length;
									startIndex = endIndex -
										this._determineVirtualRowCount(this._groupedRows, grid._getDisplayContainerHeight());
									/*firstVisibleTRIndex = this.dataSource.dataView().length - this._virtualRowCount - 1;*/
								}

								if (startIndex < 0) {
									startIndex = 0;
								}

							}

							for (i = startIndex; i < endIndex; i++) {
								tbodyHtml += this._groupedRows[ i ];
							}
						}
						/* K.D. September 4th, 2012 Bug #119685 Checking if MSApp is defined to recognize whether the control is running
						 in a WinJS environment*/
						if (window.MSApp === undefined) {
							tbody.html(tbodyHtml);
						} else {
							MSApp.execUnsafeLocalFunction(function () {
								tbody.html(tbodyHtml);
							});
						}

						/*M.K. recalculate and set the _totalRowCount and _avgRowHeight */
						grid._totalRowCount = grid._getTotalRowCount();
						grid._avgRowHeight = grid._calculateAvgRowHeight();
						/*I.I bug fix for 102462*/
						grid._buildVirtualDomForContinuousVirtualization();
					}

					/*M.K. Set the avarage row height only if it has drasticly changed. */
					avgRowHeight = grid._calculateAvgRowHeight();
					if (!grid._persistVirtualScrollTop || !this._avgRowHeight ||
						Math.abs(this._avgRowHeight - avgRowHeight) > 1) {
						this._avgRowHeight = avgRowHeight;
					}
					/*M.K. Persist scroll position */
					if (grid._persistVirtualScrollTop && grid._prevFirstVisibleTRIndex) {
						/* set scroll height */
						grid._setScrollContainerHeight(this._avgRowHeight * grid._getTotalRowCount());
						newFirstVisibleRow = tbody.find("tr[ data-row-idx='" + grid._prevFirstVisibleTRIndex + "' ]");

						isScrolledToBottom = grid._getScrollContainerScrollTop() + 5 >
							grid._scrollContainer()[ 0 ].scrollHeight - grid._scrollContainer().innerHeight();
						if (isScrolledToBottom) {
							/*scrolled to bottom. Must scroll container to last row. */
							lastVisibleRow = tbody.find("tr:visible:last");
							grid._setDisplayContainerScrollTop(lastVisibleRow[ 0 ].offsetTop);
						} else {
							if (newFirstVisibleRow.length > 0) {
								grid._setDisplayContainerScrollTop(newFirstVisibleRow[ 0 ].offsetTop);
							}
						}
					}
				} else {
					/* K.D. September 4th, 2012 Bug #119685 Checking if MSApp is defined to recognize whether the control is running
					 in a WinJS environment*/
					if (window.MSApp === undefined) {
						tbody.html(html);
					} else {
						MSApp.execUnsafeLocalFunction(function () {
							tbody.html(html);
						});
					}
					/*A.Y. when the grid has no width the container width should be recalculated
					to account for the extra cell that is added for the group by */
					if (grid.options.width === null) {
						grid._setContainerWidth(grid.container());
					} else {
						grid._updateGridContentWidth();
					}

					/*M.K. persist virtual scroll top */
					if (grid._persistVirtualScrollTop && grid._prevFirstVisibleTROffset) {
						grid.scrollContainer()[ 0 ].scrollTop = grid._prevFirstVisibleTROffset;
					}
				}
				scrollContainer = grid._virtualcontainer().length > 0 ?
					grid._virtualcontainer() : grid.scrollContainer();
				/* M.K. Fix for bug 186470: Horizontal scrollbar is not updated when grouping columns with continuous virtualization enabled */
				if (this.options.groupedColumns.length > 0 && scrollContainer.length > 0 &&
					grid.element.width() > scrollContainer.width() &&
					(grid.options.virtualization === true || grid.options.rowVirtualization === true)) {
					$(grid.element).find(">colgroup>col[ data-skip=true ]").each(function () {
						dataSkipWidth += parseInt(this.style.width, 10);
					});
					grid._setGridContentWidth(grid._calculateContainerWidth(false) + dataSkipWidth);
				}
				/* S.S. Bug #107178 After rendering all rows GroupBy should throw rowsRendered event
				 for the grid so other features can do rendering-specific actions*/
				this.grid._trigger(this.grid.events.rowsRendered, null, { owner: this.grid, tbody: tbody });
			}
		},
		/*overrides the _getTotalRowCount function of the grid.framework*/
		_getTotalRowCount: function () {
			if (this.grid.options.virtualization && this.options.groupedColumns.length > 0) {
				if (this.options.initialExpand && this._groupedRows) {
					return this._groupedRows.length;
				}
				if (!this.options.initialExpand && this._visibleGroupedRows) {
					return this._visibleGroupedRows.length;
			}
		}
			return this._getTotalRowCountHandler();
		},
		/*overrides the _calculateAvgRowHeight function of the grid.franework.*/
		_calculateAvgRowHeight: function () {
			var rowHeightSum = this.grid.container().find("#" + this.grid.id() + " > tbody").height();
			/* M.H. 25 Jul 2013 Fix for bug #146837: Vertical scroll bar does not display when control inside the Jquery UI tab*/
			if (rowHeightSum === 0) {
				rowHeightSum = parseInt(this.options.height, 10);
			}
			if (this.options.groupedColumns.length > 0) {
				return rowHeightSum / this._virtualRowCount;
			}
			return rowHeightSum / this.grid._virtualRowCount;
		},
		_processGroupsRecursive: function (i, ds, tbody, d, idval, start, end, firstTime) {
			var index, tmpSum = 0, j, tmpCount = start, tmpStart = start, k, arr, arrLength, cssClass,
				gcval = this.options.groupedColumns[ i ].key, cs;
			if (ds.length === 0) {
				return;
			}
			idval = this.grid.dataSource.getCellValue(gcval, ds[ tmpCount ]);
			/*ds[tmpCount][gcval];*/
			cs = this._settingFromKey(gcval);
			firstTime = true;
			/* M.H. 25 Sep 2013 Fix for bug #153365: GroupBy option "indentation" does not work with percentages*/
			if (!(this.options.indentation && this.options.indentation.indexOf &&
					this.options.indentation.indexOf("%") !== -1)) {
				this._indent = parseInt(this.options.indentation, 10) * (i + 1);
			}
			for (j = start; j < end; j++) {
				if (j < tmpCount || tmpCount >= end) {
					continue;
				}
				/* check if we don't have a custom group function defined for gcval */
				if (!this._cmp(cs, this.grid.dataSource.getCellValue(gcval, ds[ tmpCount ]), idval) ||
					firstTime) {
					firstTime = false;
					idval = this.grid.dataSource.getCellValue(gcval, ds[ tmpCount ]);
					if (cs.customGroupName) {
						index = this._renderGroupRow(d, i, gcval, idval, cs.customGroupName, cs, ds[ tmpCount ]);
					} else {
						index = this._renderGroupRow(d, i, gcval, idval, null, cs, ds[ tmpCount ]);
					}
					tmpStart = tmpCount;
					if (this.options.groupedColumns.length - 1 > i) {
						while (tmpCount < end &&
							this._cmp(cs, this.grid.dataSource.getCellValue(gcval, ds[ tmpCount ]), idval)) {
							tmpCount++;
							tmpSum++;
						}
						/* check if we don"t have the value in the data source*/
						this._rs(gcval, idval, index, tmpSum, tmpStart, tmpCount, ds, d);
						tmpSum = 0;
						this._processGroupsRecursive(i + 1, ds, tbody, d, idval, j, tmpCount, firstTime);
					} else {
						arr = [];
						/* check if we don't have a custom group function defined for gcval */
						while (tmpCount < end &&
							this._cmp(cs, this.grid.dataSource.getCellValue(gcval, ds[ tmpCount ]), idval)) {
							tmpCount++;
							tmpSum++;
							/* render normal row
							 make sure we are limiting the rendering to what"s in the dataView, as this may have
							 local client-side paging and filtering applied, even though the actual calculations will need to be done
							 on the whole local data
							if (tmpCount < dvmax) {*/
							this._renderRecord(d, tbody, ds[ tmpCount - 1 ],
								tmpCount - 1, this.options.initialExpand, arr);
							/*} */
						}
						/* M.H. 15 May 2012 Fix for bug #103502 - set for the last cell in group by rows specific class */
						arrLength = arr.length;
						for (k = 0; k < arrLength; k++) {
							cssClass = this.css.nonGroupRowEmptyCell;
							if (k === arrLength - 1) {
								cssClass += " " + this.css.groupByLastEmptyCell;
							}
							d[ arr[ k ] ] += "<td class='" + cssClass + "' data-skip='true'></td>";
						}
						this._rs(gcval, idval, index, tmpSum, tmpStart, tmpCount, ds, d);
						tmpSum = 0;
					}
				}
			}
		},
		_rs: function (gcval, idval, index, tmpSum, tmpStart, tmpCount, ds, d) {
			var subdata, res;
			res = this._calcRealSum(gcval, idval, tmpSum, ds);
			/* M.H. 6 Mar 2015 Fix for bug 189752: groupedRowTextTemplate template tokens are only evaluated once*/
			d[ index ] = d[ index ].replace(new RegExp("__sum__", "g"), res.sum);
			if (d[ index ].indexOf("$$value$$") !== -1) {
				subdata = ds.slice(tmpStart, tmpCount);
				d[ index ] = this._calcRealSummary(d[ index ], gcval, idval, subdata, res.allGroupData);
			}
		},
		_cmp: function (cs, val1, val2) {
			var res, o = this.grid.options, enableUTCDates = o.enableUTCDates,
				rowTemplate = (!o.rowTemplate || o.rowTemplate.length <= 0), format, key, col;
			if (cs.groupComparerFunction) {
				if (typeof (cs.groupComparerFunction) === "function") {
					res = cs.groupComparerFunction(cs, val1, val2);
				} else {
					res = window[ cs.groupComparerFunction ](cs, val1, val2);
				}
				return res;
			}
			/* L.A. 04 June 2012 New feature #112933 Default grouping for date columns*/
			if ($.type(val1) === "date" && $.type(val2) === "date") {
				key = cs.columnKey;
				if (key !== undefined && key !== null) {
					col = this.grid.columnByKey(key);
					if (col !== undefined && col !== null) {
						format = col.format;
					}
				}
				return $.ig.formatter(val1, "date", format, rowTemplate, enableUTCDates) ===
					$.ig.formatter(val2, "date", format, rowTemplate, enableUTCDates);
			}
			return val1 === val2;
		},
		_encodeSummaryParams: function (owner, params) {
			var cs = this.options.columnSettings, i, j, key;
			for (i = 0; i < cs.length; i++) {
				if (cs[ i ].summaries && cs[ i ].summaries.length > 0) {
					for (j = 0; j < cs[ i ].summaries.length; j++) {
						key = "gs(" + cs[ i ].columnKey + ")";
						if (params.extraParams[ key ]) {
							params.extraParams[ key ] = params.extraParams[ key ] +
								"," + cs[ i ].summaries[ j ].summaryFunction;
						} else {
							params.extraParams[ key ] = cs[ i ].summaries[ j ].summaryFunction;
						}
					}
				}
			}
		},
		_calcRealSummary: function (contents, gcval, idval, subdata, allGroupData) {
			var n, k, i, cs, j, mdata = this.grid.dataSource.metadata(this.options.resultResponseKey),
				o, str = contents, val, found = false, mul = 1, t, kVal;
			n = this.grid.dataSource.data();
			/* scenario 1. check if summaries aren"t coming from the server*/
			if (mdata && mdata !== null) {
				cs = this.options.columnSettings;
				for (i = 0; i < cs.length; i++) {
					for (j = 0; j < cs[ i ].summaries.length; j++) {
						/* get the summary from the metadata */
						o = mdata[ cs[ i ].columnKey + "_" + cs[ i ].summaries[ j ].summaryFunction ];
						/* find the object whose key is equal to idval*/
						for (k in o) {
							if (o.hasOwnProperty(k)) {
								/* we need to convert the metadata value based on the column"s type
								 bool and number are the ones that are tricky. */
								t = this.grid.columnByKey(gcval).dataType;
								if (t === "bool" && $.type(k) === "string") {
									kVal = k.toLowerCase() === "true" ? true : false;
								} else if (t === "number" && $.type(k) === "string") {
									kVal = mul * k;
								} else {
									kVal = k;
								}
								if ((idval !== null && !idval.length && (idval === kVal * mul)) || kVal === idval) {
									val = o[ k ];
									val = $.ig.formatter(val, "number", this.options.summarySettings.summaryFormat);
									/* ensure that we support mixed summaries - both remote (coming in metadata from the server), as well as local
									str = str.replace(cs[ i ].columnKey + cs[ i ].summaries[ j ].summaryFunction + "$$value$$", val);
									 custom summaries may only exist and be calculated on the client*/
									if (cs[ i ].summaries[ j ].customSummary === undefined ||
										cs[ i ].summaries[ j ].customSummary === null) {
										/*found = true;*/
										str = str.replace(cs[ i ].columnKey +
											cs[ i ].summaries[ j ].summaryFunction + "$$value$$", val);
									}
								}
							}
						}
					}
				}
				/* ensure that we support mixed summaries - both remote (coming in metadata from the server), as well as local
				if (found) {
					return str;
				}*/
			}
			if (!found || (this.grid.dataSource.dataView().length < n.length)) {
				/*
				setting = this._settingFromKey(gcval);
				for (k = 0; k < n.length; k++) {
				/if (n[ k ][ gcval ] === idval) {
				if (this._cmp(setting, n[ k ][ gcval ], idval)) {
				startIndex = k;
				break;
				}
				}
				for (k = startIndex; k < n.length; k++) {
				if (!this._cmp(setting, n[ k ][ gcval ], idval)) {
				break;
				}
				}
				*/
				/*subdata = n.slice(startIndex, k); */
				/* we still need to call this, cause there may be local summaries to consider. the difference to the previous implementation */
				/* is that we have already replaced th ones calculated on the server, so this is safe.  */
				return this._summaries(str, subdata, gcval, allGroupData);
			}
		},
		/* used when there is local or remote paging/filtering. We need to know the real values not the ones that are on the client*/
		_calcRealSum: function (gcval, idval, sum, ds) {
			var tmpSum = 0, k, n, setting, mul = 1, metadata, t, arrGrp = [  ],
				nVal, grpRecs, i, grpCols = this.options.groupedColumns;
			setting = this._settingFromKey(gcval);
			metadata = this.grid.dataSource.metadata(this.options.resultResponseKey);
			/* check if we don"t have the value in the data source*/
			if (metadata && metadata[ gcval ]) {
				k = metadata[ gcval ];
				for (n in k) {
					if (k.hasOwnProperty(n)) {
						/* we need to convert the metadata value based on the column"s type
						 bool and number are the ones that are tricky. */
						t = this.grid.columnByKey(gcval).dataType;
						if (t === "bool" && $.type(n) === "string") {
							nVal = n.toLowerCase() === "true" ? true : false;
						} else if (t === "number" && $.type(n) === "string") {
							nVal = mul * n;
						} else {
							nVal = n;
						}
						if (idval !== null && idval !== undefined && !idval.length && (idval === nVal * mul)) {
							tmpSum = k[ n ];
						} else if (nVal === idval) {
							tmpSum = k[ n ];
						}
					}
				}
			} else if (ds.length < this.grid.dataSource.data().length) {
				/* we have local paging or filtering, but still all info is present in the data source
				 find the unique values count, assuming the data source is already sorted*/
				tmpSum = 0;
				if (gcval === grpCols[ 0 ].key) {
					this._grpRecords = {};
				} else {
					this._grpRecords = this._grpRecords || {};
					for (i = 0; i < grpCols.length; i++) {
						if (grpCols[ i ].key === gcval && i > 0) {
							grpRecs = this._grpRecords[ grpCols[ i - 1 ].key ];
							break;
						}
					}
				}
				if (!grpRecs) {
					/* M.H. 14 Oct 2013 Fix for bug #154649: Rows are grouped incorrectly when applying and removing a filter if a filter is applied by default through code
					 if filtering is applied the groupby aggregate function is not properly calculated because it is taken from the dataSource not from the filtered data*/
					if (this.grid.dataSource._filter && this.grid.dataSource._filteredData &&
						this.options.type === "local") {
						n = this.grid.dataSource._filteredData;
					} else {
						n = this.grid.dataSource.data();
					}
				} else {
					n = grpRecs;
				}
				arrGrp = this.getGroupedData(n, gcval, idval, setting);
				tmpSum = arrGrp.length;
				this._grpRecords[ gcval ] = arrGrp;
			}
			return { sum: tmpSum > 0 ? tmpSum : sum, allGroupData: arrGrp };
		},
		getGroupedData: function (data, colKey, idval, setting) {
			/* Get grouped data by value for the specific column. NOTE: Before calling this function the data(that is passed as an argument) should be sorted by colKey.
			paramType="array" optional="false" data (sorted by colKey) that is used to get the records from
			paramType="string" optional="false" key of the column for which grouping will be applied
			paramType="string" optional="false" value of the column by which grouping will be applied
			paramType="object" optional="true" excluded="true" groupby column setting(taken from private function _settingFromKey).
			returnType="array" returns array of data records grouped by the column's value.
			*/
			var idx, k, len = data.length, arrGrp = [  ];
			setting = setting || this._settingFromKey(colKey);
			for (k = 0; k < len; k++) {
				if (this._cmp(setting, data[ k ][ colKey ], idval)) {
					idx = k;
					break;
				}
			}
			for (k = idx; k < len; k++) {
				if (this._cmp(setting, data[ k ][ colKey ], idval)) {
					arrGrp.push(data[ k ]);
				} else {
					break;
				}
			}
			return arrGrp;
		},
		_settingFromKey: function (key) {
			var cs, j;
			for (j = 0; j < this.options.columnSettings.length; j++) {
				if (this.options.columnSettings[ j ].columnKey === key) {
					cs = this.options.columnSettings[ j ];
				}
			}
			return cs;
		},
		_renderGroupRow: function (arr, indent, field, val, customName, colCustomSetting, record) {
			var expcell, textcell, col,
				margin = indent > 0 ? parseInt(this.options.indentation, 10) * indent : 0,
				cscol, htext, text, state, css, i, cs = this.options.columnSettings,
				shtml = "", tmpl, title, j, hide = false, grpRowId;
			if (this.options.initialExpand) {
				css = this.css.groupByExpandCellExpanded;
				title = this.options.collapseTooltip;
			} else {
				css = this.css.groupByExpandCellCollapsed;
				title = this.options.expandTooltip;
				if (indent > 0) {
					hide = true;
				}
			}
			if (this.options.expansionIndicatorVisibility) {
				/*L.A. 04 May 2012 - Fixing bug #Bug 107316 - The icon in the expander cell is misaligned to the left side of the cell*/
				expcell = "<td class='" + this.css.groupByExpandColumn + "' gbexpandcell='1'" +
					" tabindex='" + this.grid.options.tabIndex +
					"'><span class='ui-iggrid-expandbuttoncontainer-group-by' style='margin-left:" +
					margin + "px;'><span class='" + css + "' title='" + title + "'></span></span></td>";
			} else {
				expcell = "<td class='" + this.css.groupByExpandColumn + "' gbexpandcell='1'></td>";
			}
			/*text = this.grid.columnByKey(field).headerText + ": " + val + " (__sum__)";
			 default template:  "${key}: ${val} (${count})"*/
			tmpl = this.options.groupedRowTextTemplate;
			/* M.H. 15 May 2012 Fix for bug #111450*/
			col = this.grid.columnByKey(field);
			if (colCustomSetting && colCustomSetting.groupLabelFormatter) {
				if (typeof (colCustomSetting.groupLabelFormatter) === "function") {
					val = colCustomSetting.groupLabelFormatter(val);
				} else {
					val = window[ colCustomSetting.groupLabelFormatter ](val);
				}
			} else if (this.options.useGridColumnFormatter === true) {
				/* L.A. 17 October 2012 - Fixing bug #123215 The group rows of a grouped checkbox column are too large*/
				val = this.grid._renderCell(val, col, record, " ");
			}
			/* M.H. 6 Mar 2015 Fix for bug 189752: groupedRowTextTemplate template tokens are only evaluated once*/
			tmpl = tmpl.replace(new RegExp("\\$\\{key\\}", "g"), col.headerText);
			if (customName) {
				text = tmpl.replace(new RegExp("\\$\\{val\\}", "g"), customName)
							.replace(new RegExp("\\$\\{count\\}", "g"), "__sum__");
			} else {
				text = tmpl.replace(new RegExp("\\$\\{val\\}", "g"), val)
					.replace(new RegExp("\\$\\{count\\}", "g"), "__sum__");
			}
			if (this.options.initialExpand) {
				state = "expanded";
			} else {
				state = "collapsed";
			}
			/* we need to check if there are summaries, and if renderSummariesInGroupRow = true
			if (this.options.summarySettings.renderSummariesInGroupRow) {
			 create a template for the summaries*/
			for (i = 0; i < cs.length; i++) {
				/* M.H. 19 Apr 2016 Fix for bug 218306: Column key is displayed instead of column header text  in the grouping summary row when summaries are enabled*/
				if (cs[ i ].columnKey !== field) {
					cscol = this.grid.columnByKey(cs[ i ].columnKey);
					htext = cscol ? cscol.headerText : cs[ i ].columnKey;
				} else {
					htext = col.headerText;
				}
				/*if (cs[ i ].columnKey === field) {*/
				for (j = 0; j < cs[ i ].summaries.length; j++) {
					shtml += " " + htext + " " +
						(cs[ i ].summaries[ j ].text || cs[ i ].summaries[ j ].summaryFunction) + " " +
						cs[ i ].columnKey + cs[ i ].summaries[ j ].summaryFunction + "$$value$$";
					if (j !== cs[ i ].summaries.length - 1) {
						shtml += this.options.summarySettings.multiSummaryDelimiter;
					}
				}
				/*}*/
			}
			text += shtml;
			/*}
			else {
			for (i = 0; i < cs.length; i++) {
			for (j = 0; j < cs[ i ].summaries.length; j++) {
			TODO: implement summaries in a separate row (above or below)
			}
			}
			}
			*/
			textcell = "<td data-gbsummary='true' colspan='" + this._colspan +
				"' tabindex='" + this.grid.options.tabIndex + "'>" + text + "</td>";
			grpRowId = this.grid.options.primaryKey ||
				record.hasOwnProperty("ig_pk") ? $.ig.util.getCheckSumForObject({ text: text }) : null;
			arr.push("<tr class='" + this.grid.css.recordClass +
				" " + this.css.groupedRow + "' data-grouprow='true' data-state='" +
				state + "' data-glevel='" + indent + "'" + (hide ? " style='display:none;'" : "") +
				" aria-expanded='" + (state === "expanded" ? "true" : "false") + "'" +
				" aria-describedby='" + this.grid.id() + "_" + col.key + "'" +
				" title='" + $.ig.GridGroupBy.locale.summaryRowTitle + "'" +
				(grpRowId ? " data-id='" + grpRowId + "'" : "") +
							" tabindex='" + this.grid.options.tabIndex +
							"' >" + expcell + textcell + "</tr>");
			return arr.length - 1;
		},
		_renderRecord: function (d, tbody, data, rowIndex, expand, arr) {
			var i, j, dLen, lastIndex = d.length;
			/*if (this.grid.options.jQueryTemplating === false) {
			 K.D. April 17th, 2012 Bug #109497 Template and non-template rendering is now branched in _renderRecordsInArray*/
			this.grid._renderRecordInArray(d, tbody, data, rowIndex);
			dLen = d.length;
			/* take the last string from d, and add a TD in front of it (empty cell)
			if (!registeredTd) {*/
			for (i = lastIndex; i < dLen; i++) {
				if (d[ i ].indexOf && d[ i ].indexOf("<tr") !== -1) {
					/* initial expand or collapse*/
					if (!expand) {
						d[ i ] = d[ i ] + " style='display: none;'";
					}
					for (j = i; j < dLen; j++) {
						if (d[ j ].indexOf && d[ j ].indexOf("data-grouprow") !== -1) {
							break;
						}
						if (d[ j ].indexOf && d[ j ].indexOf(">") !== -1) {
							/* M.H. 15 May 2012 Fix for bug #103502*/
							arr.push(j);
							/*d[ j ] += "<td class="" + this.css.nonGroupRowEmptyCell + "" data-skip="true">1</td>";*/
							break;
						}
					}
					break;
				}
			}
			/*}
			 } else {
			 tmpl = this.grid._generateRowTemplate();
			 if (this.grid.options.alternateRowStyles) {
			 data.ig_zebraStyle = rowIndex;
			}
			 rec = $($.ig.tmpl(tmpl, data, this.grid._buildFormatters()));
			 $("<td class="" + this.css.nonGroupRowEmptyCell + "" data-skip="true"></td>").prependTo(rec);
			 d.push($("<div>").append(rec).html());
			 }*/
		},
		_renderNewRow: function (rec) {
			var tbody = this.grid.element.children("tbody"),
				dataRows = tbody.children("tr:not([ data-container='true' ],[ data-grouprow='true' ])"),
				index = dataRows.length,
				row,
				ind,
				i;
			row = $(this.grid._renderRecord(rec, index));
			ind = dataRows.last().find("td." + this.css.nonGroupRowEmptyCell)
				.removeClass(this.css.groupByLastEmptyCell)
				.length;
			for (i = 0; i < ind; i++) {
				$("<td></td>")
					.addClass(this.css.nonGroupRowEmptyCell)
					.addClass(this.css.groupByLastEmptyCell)
					.attr("data-skip", true)
					.prependTo(row);
			}
			/* K.D. October 8th, 2013 Bug #154311 Exception when adding new row into grouped table in WinJS*/
			MSApp.execUnsafeLocalFunction(function () {
				tbody.append(row);
			});
		},
		_summaries: function (contents, data, gcval, allGroupData) {
			var i, j, cs = this.options.columnSettings, s, ret = contents, arr, val, argsData;
			/* check what summaries need to be applied
			 the format is: columnKey_summaryFunction$$value$$
			 nothing to calculate*/
			if (contents && contents.indexOf && contents.indexOf("$$value$$") === -1) {
				return contents;
			}
			for (i = 0; i < cs.length; i++) {
				/*if (cs[ i ].columnKey === gcval) {*/
				s = cs[ i ].summaries;
				if (s.length > 0) {
					arr = this._arr(cs[ i ].columnKey, data);
					for (j = 0; j < s.length; j++) {
						if (s[ j ].summaryFunction === "custom") {
							argsData = {
								dataRecords: data,
								array: arr,
								key: gcval,
								allGroupData: allGroupData
							};
						} else {
							argsData = arr;
						}
						val = $.ig.calcSummaries(s[ j ].summaryFunction, argsData, s[ j ].customSummary);
						val = $.ig.formatter(val, "number", this.options.summarySettings.summaryFormat);
						ret = ret.replace(cs[ i ].columnKey + s[ j ].summaryFunction + "$$value$$", val);
					}
				}
				/*}*/
			}
			return ret;
		},
		_arr: function (key, data) {
			var arr = [  ], i;
			for (i = 0; i < data.length; i++) {
				arr.push(data[ i ][ key ]);
			}
			return arr;
		},
		/* dataEmpty follows dataRendering and is invoked when the tbody is emptied
		 this is necessary because jQuery automatically "shows" the elements that have been
		 emptied, which is not correct. */
		_dataEmpty: function (event, args) {
			var indent = 0, indentation = parseInt(this.options.indentation, 10),
				$cntnr;
			if (args.owner.id() !== this.grid.id()) {
				return;
			}
			if (this.options.groupedColumns.length > 0) {
				/*args.tbody.hide(); */
				indent += indentation * this.options.groupedColumns.length;
				/* M.H. 25 Sep 2013 Fix for bug #153365: GroupBy option "indentation" does not work with percentages */
				if (this.options.indentation && this.options.indentation.indexOf &&
						this.options.indentation.indexOf("%") >= 0) {
					indent = this.options.indentation;
				}
				/* M.H. 6 Apr 2016 Fix for bug 217266: [ IgGrid ] rowVirtualization property is not compatible with Hidden column*/
				this._addUpdataDataSkipCol(indent);
			}
			$cntnr = this.grid.container();
			if (this._isgroup !== null && this._isgroup !== undefined &&
				$cntnr.length && $cntnr[ 0 ].style.width && !this.grid.options.width) {
				if (this._isgroup === true) {
					/* M.H. 11 April 2012 Fix for bug #108399 */
					if (this.grid.options.virtualization !== true) {
						$cntnr.width(parseInt($cntnr.width(), 10) + indentation);
					}
					/*} else if (this.options.groupedColumns.length > 0) {*/
				} else {
					$cntnr.width(parseInt($cntnr.width(), 10) - indentation);
					indentation *= -1;
				}

				/*I.I. bug fix for 107222*/
				if (this.grid.options.virtualization === true &&
					this.grid.options.virtualizationMode === "continuous") {
					this.grid._setDisplayContainerWidth(this.grid._getDisplayContainerWidth() + indentation);
					this.grid._setVHeadersWidth(this.grid._getVHeadersWidth() + indentation);
				}

				this._isgroup = null;
			}
			this._tbody = args.tbody;
			if (!this._isgroup && this.groupByColumns().length > 0) {
				this._groupAreaList();
			}
			event.stopPropagation();
		},
		_checkGroupExists: function (key) {
			var exists = false, i;
			for (i = 0; i < this.options.groupedColumns.length; i++) {
				if (this.options.groupedColumns[ i ].key === key) {
					exists = true;
					break;
				}
			}
			return exists;
		},
		_groupBy: function (event, th) {
			/* fire groupedColumnsChanging*/
			var noCancel, key, layout, cgrid, fc, eArgs, exists = false;
			/* check for the data-layout attribute*/
			layout = th.attr("data-layout");
			if (layout) {
				/*I.I. bug fix for 108342 */
				/* find the layout object. layouts must have unique keys */
				cgrid = this.grid.container().find("#" + th.attr("data-grid-id")).data("igGrid");
				key = cgrid.options.columns[ th.data("columnIndex") ].key;
			} else {
				/* M.H. 13 Feb 2013 Fix for bug #131796 */
				if (th.data("columnIndex") === null || th.data("columnIndex") === undefined) {
					return;
				}
				key = this.grid.options.columns[ th.data("columnIndex") ].key;
				cgrid = this.grid;
			}

			fc = this.grid.element.data("igGridFeatureChooser");
			if (fc !== undefined && fc !== null) {
				fc._setSelectedState("GroupBy", key, true, false);
			}

			/* check if the groupedColumns collection doesn"t already contain that entry */
			if (!layout) {
				exists = this._checkGroupExists(key);
			}
			if (exists) {
				return;
			}
			eArgs = {
				owner: this,
				groupedColumns: this.options.groupedColumns,
				key: key,
				layout: layout,
				grid: cgrid,
				triggeredBy: "dragAndDrop"
			};
			noCancel = this._trigger(this.events.groupedColumnsChanging, event, eArgs);
			if (noCancel) {
				this.groupByColumn(key, layout);
				/* fire groupedColumnsChanged */
				this._trigger(this.events.groupedColumnsChanged, event, eArgs);
			}
		},
		groupByColumns: function () {
			/* Adds a column to the group by columns list, executes the group by operation and updates the view.
			returnType="object" returns the currently grouped columns collection
			*/
			/*A.T. 12 March 2012 - fix for bug #101236 */
			var gc = [  ];
			if (this._hierarchical) {
				/*this._allGroupedCols(gc); */
				/* we need to preserve the order in which columns were dragged */
				/*A.T. 21 March 2012 - bug #91992 - we want to preserve the original order of dragging to the groupby area or grouping programmatically */
				gc = this._orderedGroupedCols;
			} else {
				gc = this.options.groupedColumns;
			}
			return gc;
		},
		/*A.T. 28 March 2012 - fix for Bug #106918 */
		/* ensure order is correct both within the hierarchy as well as the layouts themselves */
		_ensureLabelsOrder: function (from, to, layout) {
			var i, key = layout ? layout.key : null;
			for (i = 0; i < from.length; i++) {
				if ((from[ i ].layout === key || (!from[ i ].layout && !key)) && !this._exists(to, from[ i ])) {
					to.push(from[ i ]);
				}
			}
			for (i = 0; layout.columnLayouts && i < layout.columnLayouts.length; i++) {
				this._ensureLabelsOrder(from, to, layout.columnLayouts[ i ]);
			}
		},
		_exists: function (arr, layout) {
			var i;
			for (i = 0; i < arr.length; i++) {
				if ((arr[ i ].layout === layout.layout || (!arr[ i ].layout && !layout.layout)) &&
					arr[ i ].key === layout.key) {
					return true;
				}
			}
			return false;
		},
		_findLayout: function (layout, opts, layouts) {
			var i;
			if (opts.key === layout) {
				/*return opts; */
				layouts.push(opts);
			} else if (!layout) {
				layouts.push(this.grid.options);
			} else {
				for (i = 0; opts.columnLayouts && i < opts.columnLayouts.length; i++) {
					this._findLayout(layout, opts.columnLayouts[ i ], layouts);
				}
			}
			/*return opts; */
		},
		_isGroupColumn: function (key, layout, gc) {
			/* check in collection gc - grouped columns whether there is such column with the specified key and layout */
			var i, gcLen = gc.length;
			for (i = 0; i < gcLen; i++) {
				if (gc[ i ].key === key && gc[ i ].layout === layout) {
					return true;
				}
			}
			return false;
		},
		_allGroupedCols: function (gc) {
			var i, j, key, layout, isToAdd;
			for (i = 0; i < this.options.groupedColumns.length; i++) {
				key = this.options.groupedColumns[ i ].key;
				layout = this.options.groupedColumns[ i ].layout;
				isToAdd = true;
				for (j = 0; j < gc.length; j++) {
					if (key === gc[ j ].key && layout === gc[ j ].layout) {
						this.options.groupedColumns[ i ] = gc[ j ];
						isToAdd = false;
						break;
					}
				}
				if (isToAdd) {
					gc.push(this.options.groupedColumns[ i ]);
				}
			}
			this._addGroupsRecursive(gc, this.grid.options.columnLayouts);
		},
		_addGroupsRecursive: function (gc, layouts) {
			var i, j, gb, col, k, persist;
			if (!layouts) {
				return;
			}
			for (i = 0; i < layouts.length; i++) {
				for (j = 0; layouts[ i ].features && j < layouts[ i ].features.length; j++) {
					if (layouts[ i ].features[ j ].name === "GroupBy") {
						gb = layouts[ i ].features[ j ];
						break;
					}
				}
				if (gb) {
					persist = (gb.persist || this.options.persist);
					for (j = 0; gb.columnSettings && gb.columnSettings.length &&
						j < gb.columnSettings.length; j++) {
						if (gb.columnSettings[ j ].isGroupBy) {
							/* M.H. 27 Feb 2014 Fix for bug #165349: Grouping a column in child layout with persitance enabled cause the grouping of the same column to be done twice */
							/* we should ensure that there isn"t added the same grouped column(with the same key and layout) twice in gc collection */
							if (persist && gb.columnSettings[ j ].columnKey) {
								if (this._isGroupColumn(gb.columnSettings[ j ].columnKey, layouts[ i ].key, gc)) {
									continue;
								}
							}
							for (k = 0; k < layouts[ i ].columns.length; k++) {
								if (layouts[ i ].columns[ k ].key === gb.columnSettings[ j ].columnKey ||
									gb.columnSettings[ j ].columnIndex === k) {
									col = layouts[ i ].columns[ k ];
									break;
								}
							}
							gc.push({
								key: gb.columnSettings[ j ].columnKey || col.key,
								layout: layouts[ i ].key,
								dir: gb.columnSettings[ j ].dir,
								col: col
							});
						}
					}
				}
				gb = null;
				this._addGroupsRecursive(gc, layouts[ i ].columnLayouts);
			}
		},
		_getColumnByLayoutKey: function (key, columns) {
			var c, i;

			if (this.grid._isMultiColumnGrid) {
				for (i = 0; i < columns.length; i++) {
					if (columns[ i ].key === key) {
						c = columns[ i ];
						break;
					}
					if (columns[ i ].group !== undefined && columns[ i ].group !== null) {
						c = this._getColumnByLayoutKey(key, columns[ i ].group);
						if (c !== undefined && c !== null) {
							break;
						}
					}
				}
			} else {
				for (i = 0; i < columns.length; i++) {
					if (columns[ i ].key === key) {
						c = columns[ i ];
						break;
					}
				}
			}
			return c;
		},
		groupByColumn: function (key, layout, sortingDirection) {
			/* Groups by a column
			paramType="string" optional="false" Column Key - group by the column with the specified key
			paramType="string" optional="true" layout is an optional parameter. if set it means the grouped column is not in the root level but is a child layout column
			paramType="asc|desc" optional="true" if not set it is taken from option defaultSortingDirection
			*/
			/* set sorting expressions for the key for the current grid"s data source */
			/* then call dataBind() */
			/* check if we haven"t already grouped by this col */
			var i, expr, gexpr, lobj, c, fcs, setting, layouts,
				exists = this._checkGroupExists(key), copy, dir = "asc";
			this.grid._onGroupedColumnsChanging(this.options.groupedColumns);
			if (sortingDirection !== undefined && sortingDirection !== null) {
				dir = sortingDirection;
			} else if (this.options.defaultSortingDirection !== undefined) {
				dir = this.options.defaultSortingDirection;
			}
			/* already grouped by that column */
			if (!layout && exists) {
				return;
			}
			if (layout) {
				layouts = [  ];
				this._findLayout(layout, this.grid.options, layouts);
				lobj = layouts[ 0 ];

				c = this._getColumnByLayoutKey(key, lobj.columns);
			} else {
				lobj = this.grid.options;
				c = this.grid.columnByKey(key);
			}
			if (layout) {
				if (!lobj.features) {
					lobj.features = [  ];
					lobj.features.push({ name: "GroupBy", columnSettings: [  ] });
					fcs = lobj.features[ 0 ].columnSettings;
				} else {
					for (i = 0; i < lobj.features.length; i++) {
						if (lobj.features[ i ].name === "GroupBy") {
							fcs = lobj.features[ i ].columnSettings;
							if (!fcs) {
								lobj.features[ i ].columnSettings = [  ];
								fcs = lobj.features[ i ].columnSettings;
							}
							break;
						}
					}
				}
				for (i = 0; i < fcs.length; i++) {
					if (fcs[ i ].columnKey === key) {
						if (fcs[ i ].isGroupBy) {
							return;
						}
						setting = fcs[ i ];
						break;
					}
				}
				if (!setting) {
					fcs.push({ columnKey: key, isGroupBy: true, dir: dir });
				} else {
					setting.isGroupBy = true;
				}
			}
			/* if the layout is defined we need to store that layout in the data source, and set the group expression to the layout"s settings  */
			/* where it belongs to */
			expr = { fieldName: key, dir: dir, layout: layout, isGroupBy: true };
			gexpr = { key: key, dir: dir, layout: layout, col: c };

			/*if (layout) { */
			/*	fcs.groupedColumns.push(gexpr); */
			/*	setting.isGroupBy = true; */
			/*	setting.dir = "asc"; */
			/*} else { */
			if (!layout) {
				this.options.groupedColumns.push(gexpr);
			}// else {
			/*A.T. 21 March 2012 - bug #91992 - we want to preserve the original order of dragging to the groupby area or grouping programmatically */
			this._orderedGroupedCols.push(gexpr);
			copy = this._orderedGroupedCols.slice(0);
			this._orderedGroupedCols = [  ];
			this._ensureLabelsOrder(copy, this._orderedGroupedCols, this.grid.options);
			/*} */
			/*this._refreshHeaderHandlers(); */
			/* firing this event tells all subscribers that they need to re-render their header extra cells */
			/* this could happen in the case when a column is grouped, and it adds extra cell to the header */
			/* therefore features such as filtering or sorting also need to add extra headers, etc.  */
			this.grid._trigger("headerExtraCellsModified", null, { owner: this });
			/*} */
			/* M.H. 17 May 2012 Fix for bug #111331 */
			/*this.grid.dataSource.settings.sorting.expressions.push(expr);unshift */
			this._addSortingExpressions(this.grid.dataSource.settings.sorting.expressions, expr);
			if (!layout) {
				this._isgroup = true;
			}
			this._rebind();
			this.grid._onGroupedColumnsChanged(this.options.groupedColumns);
		},
		_addSortingExpressions: function (se, expr) {
			/* insert expr at the first position of the se (sorting expressions) if there are not any other expressions with flag group by
			 otherwise if there are such expressions inserts after the last */
			var i, seLength = se.length, found = -1, cs,
				layout = expr.layout, key = expr.fieldName;

			for (i = 0; i < seLength; i++) {
				/* if there are from sorting expression */
				if (se[ i ].fieldName === key &&
					((!layout && !se[ i ].layout) || (layout && layout === se[ i ].layout))) {
					if (se[ i ].isSorting) {
						expr.isSorting = true;
					}
					se.splice(i, 1);
					break;
				}
			}
			seLength = se.length;
			for (i = 0; i < seLength; i++) {
				if (se[ i ].isGroupBy === true) {
					found = i;
				} else {
					break;
				}
			}
			/* M.H. 21 Apr 2016 Fix for bug 216651: Second grouping after custom grouping is applied is not correct */
			cs = this._findColumnSetting(key);
			if (cs && cs.compareFunc) {
				if ($.type(cs.compareFunc) === "function") {
					expr.compareFunc = cs.compareFunc;
				} else if (typeof cs.compareFunc === "string" &&
					typeof window[ cs.compareFunc ] === "function") {
					expr.compareFunc = window[ cs.compareFunc ];
				}
			}
			if (found === -1) {
				se.unshift(expr);
			} else {
				se.splice(found + 1, 0, expr);
			}
		},
		ungroupByColumn: function (key, layout) {
			/* Removes the specified column from the group by columns list, executes the group by operation and updates the view.
			paramType="string" optional="false" Column Key - ungroup by the column with the specified key
			paramType="string" optional="true" Layout is an optional parameter. If set it means the grouped column is not in the root level but is a child layout column.
			*/
			var i, fc, self = this,
				exprs = this.grid.dataSource.settings.sorting.expressions,
				lobj, layouts, exists = this._checkGroupExists(key), copy;
			if (!layout && !exists) {
				return;
			}
			this.grid._onGroupedColumnsChanging(this.options.groupedColumns);
			if (layout) {
				layouts = [  ];
				this._findLayout(layout, this.grid.options, layouts);
				lobj = layouts[ 0 ];
			} else {
				lobj = this.grid.options;
			}
			fc = this.grid.element.data("igGridFeatureChooser");
			if (fc !== undefined && fc !== null) {
				fc._setSelectedState("GroupBy", key, false, false);
			}
			if (!layout) {
				for (i = 0; i < this.options.groupedColumns.length; i++) {
					if (this.options.groupedColumns[ i ].key === key) {
						/*A.T. 8 March 2012 - Fix for bug #104244  */
						/*this.options.groupedColumns.remove(i); */
						$.ig.removeFromArray(this.options.groupedColumns, i);
						break;
					}
				}
			} //else {
			/* remove from the ordered expressions */
			for (i = 0; i < this._orderedGroupedCols.length; i++) {
				if (this._orderedGroupedCols[ i ].key === key &&
						((this._orderedGroupedCols[ i ].layout && this._orderedGroupedCols[ i ].layout === layout) ||
							(!this._orderedGroupedCols[ i ].layout && (layout === "" || layout === undefined)))) {
					$.ig.removeFromArray(this._orderedGroupedCols, i);
					break;
				}
			}
			/*} */
			for (i = 0; i < exprs.length; i++) {
				if (exprs[ i ].fieldName === key && (!layout || (layout && layout === exprs[ i ].layout))) {
					/*A.T. 8 March 2012 - Fix for bug #104244  */
					/*exprs.remove(i); */
					if (exprs[ i ].isSorting) {
						exprs[ i ].isGroupBy = undefined;
					} else {
						$.ig.removeFromArray(exprs, i);
					}
				}
			}
			copy = this._orderedGroupedCols.slice(0);
			this._orderedGroupedCols = [  ];
			this._ensureLabelsOrder(copy, this._orderedGroupedCols, this.grid.options);
			/*this._refreshHeaderHandlers(); */
			this.grid._trigger("headerExtraCellsModified", null, { owner: this });
			/* we also need to ensure that we clear the UI from any unnecessary cols and headers, if there are no more */
			/* grouped rows in groupedColumns */
			/*if (parseInt(this.grid.container().width(), 10) > 0 && !this.grid.options.width) { */
			/*	this.grid.container().width(parseInt(this.grid.container().width(), 10) - parseInt(this.options.indentation, 10)); */
			/*} */
			this.grid.element.children("tbody").hide();
			/* L.A. 29 October 2012 - Fixing bug #123405 - Ungrouping in sample cause shrinking of columns in IE8 */
			if (document.documentMode === 8) {
				this.grid.element.hide();
				this.grid.headersTable().hide();
			}
			if (!layout && this.options.groupedColumns.length === 0) {
				this.grid.element.find("colgroup").children().first().remove();
				if (this.grid.options.fixedHeaders) {
					this.grid.headersTable().find("colgroup").children().first().remove();
				}
				/* M.H. 12 Oct. 2011 Fix for bug #90815 */
				if (this.grid.options.fixedFooters && this.grid.options.height !== null) {
					this.grid.footersTable().find("colgroup").children().first().remove();
				}
				/* L.A. 04 January 2013 - Fixing bug #129797 A column"s header cell is removed instead  */
				/* of the group-by expander header cell when ungrouping is performed (either through the UI or programatically)   */
				/* while a column is being grouped (remote GroupBy) */
				this.grid.headersTable().find("thead tr").children("[ data-skip ]").first().remove();
			}
			if (!layout) {
				this._isgroup = false;
			}
			this._rebind();
			/* L.A. 29 October 2012 - Fixing bug #123405 - Ungrouping in sample cause shrinking of columns in IE8 */
			if (document.documentMode === 8) {
				this.grid.headersTable().show();
				this.grid.element.show();
			}
			/* decrease the grid width accordingly */
			if (this.options.groupedColumns.length === 0) {
				/* M.H. 23 Oct 2013 Fix for bug #155756: Group and ungroup when there is an unbound column causes the summaries of the unbound column to disapear in Firefox */
				/* when summaries are enabled then we should refresh hide and then show footers table in FF(version 22, 23, 24) so the layout engine to be forced to refresh UI and show properly columns */
				if (window.mozInnerScreenX !== undefined && self.grid.options.fixedFooters === true) {
					self.grid.footersTable().hide();
					setTimeout(function () {
						self.grid.footersTable().show();
					}, 0);
				}
				this._setGroupAreaTextContent(
					$(".ui-iggrid-groupbyarea .ui-iggrid-groupbyareatext",
					this.grid.container()
					));
				/* M.H. 29 May 2012 Fix for bug #112881 */
				/* M.H. 7 Aug 2012 Fix for bug #113954 */
				if (this.grid.options.width !== null && this.grid.options.width !== undefined) {
					this.grid._updateGridContentWidth();
				}
			}
			this.grid._onGroupedColumnsChanged(this.options.groupedColumns);
		},
		_clearSettings: function (layouts) {
			var i, j, cs, k;
			for (i = 0; i < layouts.length; i++) {
				if (layouts[ i ].features && layouts[ i ].features.length) {
					for (j = 0; j < layouts[ i ].features.length; j++) {
						if (layouts[ i ].features[ j ].name === "GroupBy") {
							cs = layouts[ i ].features[ j ].columnSettings;
							/* A.T. 2 April 2012 fix for #105052 */
							if (cs) {
								for (k = 0; k < cs.length; k++) {
									cs[ k ].isGroupBy = false;
								}
							}
						}
					}
				}
				if (layouts[ i ].columnLayouts) {
					this._clearSettings(layouts[ i ].columnLayouts);
				}
			}
		},
		ungroupAll: function () {
			/* Clears the group by columns list and updates the view. */
			var indent = 0, i, layouts = this.grid.options.columnLayouts,
				cols = this.groupByColumns(), hasRoot = false;
			/* do nothing if there"s nothing grouped */
			/*A.T. 14 March 2012 - Fix for #101231  */
			if (cols.length === 0) {
				/*if (this.options.groupedColumns.length === 0) { */
				return;
			}
			/* M.H. 15 Apr 2014 Fix for bug #170091: When Updating is enabled "Add new row" gets displaced after calling the groupByColumn API */
			this.grid._onGroupedColumnsChanging(this.options.groupedColumns);
			for (i = 1; i < this.options.groupedColumns.length; i++) {
				indent += parseInt(this.options.indentation, 10);
			}
			if (parseInt(this.grid.container().width(), 10) > 0 && !this.grid.options.width) {
				this.grid.container().width(parseInt(this.grid.container().width(), 10) - indent);
			}
			this.grid.dataSource.settings.sorting.expressions = [  ];
			this.options.groupedColumns = [  ];
			this._orderedGroupedCols = [  ];
			if (!this._hierarchical) {
				cols = [  ];
			} else {
				for (i = 0; i < cols.length; i++) {
					if (!cols[ i ].layout) {
						hasRoot = true;
						break;
					}
				}
				if (hasRoot) {
					cols = [  ];
				}
			}
			if (layouts) {
				/* we need to iterate over all column layouts and check the column settings as well */
				this._clearSettings(layouts);
			}
			/*A.T. 14 March 2012 - do the below only of both are 0, otherwise we will remove the columns cols */
			/* do this only if we are ungrouping ROOT (current) level columns, not child ones  */
			if (this.options.groupedColumns.length === 0 && cols.length === 0) {
				this.grid.element.find("colgroup").children().first().remove();
				if (this.grid.options.fixedHeaders) {
					this.grid.headersTable().find("colgroup").each(function () {
						/* M.H. 24 Sep 2012 Fix for bug #122404 */
						$(this).children("[ data-skip ]").first().remove();
					});
				}
				/* M.H. 12 Oct. 2011 Fix for bug #90815 */
				if (this.grid.options.fixedFooters) {
					this.grid.footersTable().find("colgroup").each(function () {
						/* M.H. 24 Sep 2012 Fix for bug #122404 */
						$(this).children("[ data-skip ]").first().remove();
					});
				}
				this.grid.headersTable().find("thead tr").each(function () {
					/* M.H. 24 Sep 2012 Fix for bug #122404 */
					$(this).children("[ data-skip ]").first().remove();
				});
				/* M.H. 1 Nov. 2011 Fix for bug #95004 */
				this.grid.footersTable().find("tfoot tr").each(function () {
					/* M.H. 24 Sep 2012 Fix for bug #122404 */
					$(this).children("[ data-skip ]").first().remove();
				});
			}
			this._isgroup = false;
			this._rebind();
			/* L.A. 07 January 2013 - Fixing bug #128970, 130443 */
			/* When the grid has width and height, every column has a width, you group by a column and then click "Clear Filter"  */
			/* option from filtering dropdown the columns change their original width.			 */
			/* Just adding similar fix from ungroupByColumn method */
			/* decrease the grid width accordingly */
			if (this.options.groupedColumns.length === 0) {
				/* M.H. 29 May 2012  Fix for bug #112881 */
				/* M.H. 7 Aug 2012 Fix for bug #113954 */
				if (this.grid.options.width !== null && this.grid.options.width !== undefined) {
					this.grid._updateGridContentWidth();
				}
			}
			/* M.H. 15 Apr 2014 Fix for bug #170091: When Updating is enabled "Add new row" gets displaced after calling the groupByColumn API */
			this.grid._onGroupedColumnsChanged([  ]);
		},
		_regroup: function () {
			var items = $(".ui-iggrid-groupbyarea", this.grid.container()).find("ul li"),
				groupby = this, i, copy, gbObj, eArgs, noCancel, sExprs = [  ], gbExprs = [  ];
			this.options.groupedColumns = [  ];
			this.grid.dataSource.settings.sorting.expressions = [  ];
			copy = this._orderedGroupedCols.slice(0);
			this._orderedGroupedCols = [  ];
			items.each(function () {
				var direction, k, layout, c, lobj, layouts = [  ], sExpr;
				k = $(this).attr("data-key");
				layout = $(this).attr("data-layout");
				direction = $(this).find(".ui-iggrid-asc").length > 0 ? "asc" : "desc";
				if (layout) {
					groupby._findLayout(layout, groupby.grid.options, layouts);
					lobj = layouts[ 0 ];
					for (i = 0; lobj && i < lobj.columns.length; i++) {
						if (lobj.columns[ i ].key === k) {
							c = lobj.columns[ i ];
							break;
						}
					}
				} else {
					c = groupby.grid.columnByKey(k);
				}
				/* M.H. 10 Nov 2014 Fix for bug #175903: Dragging a grouped column to position 0 is not possible */
				gbObj = { key: k, dir: direction, col: c, layout: layout };
				sExpr = { fieldName: k, dir: direction, isGroupBy: true };
				if (layout) {
					/* don"t do anything, the column is already marked as grouped */
					/*groupby.grid.dataSource.settings.sorting.expressions.push({ fieldName: k, dir: direction, layout: layout }); */
					/* M.H. 17 May 2012 Fix for bug #111331 */
					sExpr.layout = layout;
				}
				/* it should be fired first event groupedColumnsChanging and if it is not canceled then  */
				/* arrays of grouped columns(gbExprs) and sorted columns(sExprs) should update the internal collections of GroupBy */
				gbExprs.push(gbObj);
				sExprs.push(sExpr);
			});
			eArgs = {
				owner: this,
				groupedColumns: gbExprs,
				triggeredBy: "regroup"
			};
			noCancel = this._trigger(this.events.groupedColumnsChanging, null, eArgs);
			if (noCancel) {
				for (i = 0; i < gbExprs.length; i++) {
					groupby._addSortingExpressions(
						groupby.grid.dataSource.settings.sorting.expressions,
						sExprs[ i ]
						);
					groupby._orderedGroupedCols.push(gbExprs[ i ]);
					if (!gbExprs[ i ].layout) {
						groupby.options.groupedColumns.push(gbExprs[ i ]);
					}
				}
				groupby._ensureLabelsOrder(copy, groupby._orderedGroupedCols,
					groupby.grid.options); // ensure we don"t allow layouts to preceed higher level layouts or root ones
				this.grid._loadingIndicator.show();
				this._rebindDataSource();
				/* fire groupedColumnsChanged */
				this._trigger(this.events.groupedColumnsChanged, null, eArgs);
			}
		},
		_rebind: function () {
			/* get the scrolltop of the browser window */
			this._scrolltop = $(window).scrollTop();
			this.grid._loadingIndicator.show();
			this._saveSortingExpressions();
			this._rebindDataSource();
			/* add group by list */
			this._groupAreaList();
		},
		_rebindDataSource: function () {
			var ds = this.grid.dataSource;
			if (this.options.type === "remote") {
				ds.dataBind();
			} else {
				/* M.H. 15 March 2012 Fix for bug #104535 */
				/* L.A. 11 September 2012 Fixing bug #120131  */
				/* Ungrouping when Type = local makes request to the remote dataSource */
				/*M.K. 3/31/2015 Fix for bug 190697: When grouped columns are removed the data is sorted according to the last set of grouped column(s) */
				if (ds.settings.sorting.expressions.length === 0 ) {
					this.grid.dataBind();
				} else {
					ds.sort(ds.settings.sorting.expressions);
					this.grid._renderData();
				}
			}
		},
		_renderPartialRecords: function (parentrow) {
			if (parentrow.attr("data-loaded")) {
				return;
			}
			var dataRowId, rows, html = "", i, rowsLength, scrollerHeight;

			dataRowId = parseInt(parentrow.attr("data-row-idx"), 10);
			if (dataRowId >= 0) {
				rows = this._groups[ dataRowId ];
			}
			if (!rows) {
				return;
			}
			rowsLength = rows.length;
			for (i = 0; i < rowsLength; i++) {
				html += rows[ i ];
			}
			$(html).insertAfter(parentrow);
			scrollerHeight = this.grid._getScrollContainerHeight();
			this._addScrollHeight = parentrow.outerHeight() * rowsLength;
			this.grid._setScrollContainerHeight(scrollerHeight + this._addScrollHeight);
			parentrow.attr("data-loaded", true);
		},
		_onKeyDownGroup: function (event) {
			var $target;
			if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
				$target = $(event.target);
				this._toggleGroup(event);
				event.preventDefault();
				event.stopPropagation();
				$target.focus();
			}
		},
		_toggleGroup: function (event) {
			/* get the grouped row */
			/* find all trs between two grouped rows */
			/* change their visibility / display attributes */
			var parentrow = $(event.target).closest("tr"),
				grid, i, el, currentLevel,
				cindex, glevel = parentrow.attr("data-glevel"),
				grouprow, rowPath = [  ], level = glevel, style, owner = this;
			cindex = parentrow.index();
			if (this._hierarchical) {
				grid = parentrow.closest(".ui-iggrid-table").data("igGrid");
			} else {
				grid = this.grid;
			}
			/* L.A. Fixing bug #108470 Only the first grouped row is collapsed with initialExpand: false */
			/* All collapsed/expanded children will not preserve their states and their child states */
			/* L.A. 04 May 2012 - Fixing bug #107316 - The icon in the expander cell is misaligned to the left side of the cell */
			if (parentrow.attr("data-state") === "expanded") {
				parentrow
					.attr("data-state", "collapsed")
					.attr("aria-expanded", "false")
					.find(".ui-iggrid-expandbutton")
					.attr("title", this.options.expandTooltip)
					.removeClass(this.css.groupByExpandCellExpanded).addClass(this.css.groupByExpandCellCollapsed);
			} else {
				parentrow.attr("data-state", "expanded")
					.attr("aria-expanded", "true")
					.find(".ui-iggrid-expandbutton")
					.attr("title", this.options.collapseTooltip)
					.removeClass(this.css.groupByExpandCellCollapsed).addClass(this.css.groupByExpandCellExpanded);
				if (!this.options.initialExpand) {
					this._renderPartialRecords(parentrow);
				}
			}
			rowPath[ glevel ] = parentrow;
			grid.element.children("tbody").children().each(function (index) {
				if (index > cindex) {
					var $this = $(this);
					grouprow = $this.attr("data-grouprow");
					level = $this.attr("data-glevel");

					/*Exit loop */
					if (grouprow === "true" && level <= glevel) {
						return false;
					}

					/*Calculate real level. If the row is not a grouprow, increase the level by one */
					if (grouprow) {
						currentLevel = level;
					} else {
						currentLevel = parseInt(rowPath[ rowPath.length - 1 ].attr("data-glevel"), 10) + 1;
					}

					/*Detect visiblity by reading the whole row path */
					style = "";
					for (i = 0; i < currentLevel; i++) {
						if (rowPath[ i ] !== undefined && rowPath[ i ].attr("data-state") !== "expanded") {
							style = "none";
							break;
						}
					}
					$this.css("display", style);
					/*M.K. Track count of expanded rows */
					if (style !== "none") {
						owner._expandedRowCount++;
					} else {
						owner._expandedRowCount--;
					}

					/*Update row path */
					if (grouprow) {
						rowPath[ parseInt($this.attr("data-glevel"), 10) ] = $this;
					}
				}
			});
			/* M.K. Recalculate the height of the scroll container based on how many rows are expanded */
			if (this._expandedRowCount > 0) {
				this._virtualRowCount =
					this.grid._determineVirtualRowCount(this._groupedRows, this.grid._getDisplayContainerHeight());
				this._avgRowHeight = this.grid._calculateAvgRowHeight();

				this.grid._setScrollContainerHeight(this.grid._getScrollContainerHeight() +
					this._avgRowHeight * this._expandedRowCount);
			} else {
				this._virtualRowCount = this.grid._determineVirtualRowCount(
					this._groupedRows,
					this.grid._getDisplayContainerHeight()
					);
				this._avgRowHeight = this.grid._calculateAvgRowHeight();
				this.grid._setScrollContainerHeight(this._avgRowHeight * this.grid._getTotalRowCount());
			}
			/* Empty the array to ensure no memory leaks in IE */
			for (el in rowPath) {
				if (rowPath.hasOwnProperty(el)) {
					el = null;
					$.ig.removeFromArray(rowPath, el);
				}
			}
			/* M.H. 10 May 2012 Fix for bug #110952 */
			/* M.H. 29 May 2012 Fix for bug #112865 */
			this.grid._adjustLastColumnWidth(false);
			event.stopPropagation();
		},
		_groupAreaList: function () {
			var area = $(".ui-iggrid-groupbyarea", this.grid.container()),
				layout, allgrouped = [  ], dropFunc, mousedownFunc,
				list = area.find("ul"), i, groupby = this, css, key, item,
				itemEdge, helperFunc, overFunc, outFunc, text;
			/*this._allGroupedCols(allgrouped); */
			allgrouped = this.groupByColumns();
			if (list.length > 0) {
				list.empty();
			} else {
				/* A.T. 15 Sept 2011 - Fix for bug #87562. Removing float:left from the UL, because it increases the overall height of teh group by area, unnecessarily */
				/* list = $("<ul></ul>").appendTo(area).addClass("ui-iggrid-grouparealist").css("float", "left").sortable({ */
				list = $("<ul></ul>").appendTo(area).addClass("ui-iggrid-grouparealist");
			}
			helperFunc = function (event) {
				var li = null;
				if ($(event.target).is("li")) {
					li = $(event.target);
				} else {
					li = $(event.target).closest("li");
				}
				li = li.clone();
				li.find(".ui-iggrid-groupbylabelrightedgeend").remove();
				li.find(".ui-iggrid-groupbylabelrightedge").remove();
				li.find(".ui-iggrid-groupbyremovebutton").remove();
				li.attr("data-dragging", true);
				return li;
			};
			overFunc = function (event, ui) {
				var $li;
				if (!ui.draggable.hasClass("ui-iggrid-groupedcolumnlabel")) {
					return false;
				}
				if (ui.draggable.attr("data-key") === $(this).attr("data-key")) {
					return false;
				}
				if (groupby._thOver) {
					$li = ui
							.draggable
							.closest("ul")
							.find("li[ data-key=" + groupby._thOver + " ]");

					if ($li.length > 0 && $li.hasClass("ui-iggrid-groupbydroppableedge")) {
						$li.removeClass("ui-iggrid-groupbydroppableedge");
					}
				}
				groupby._thOver = $(event.target).attr("data-key");
				$(event.target).addClass("ui-iggrid-groupbydroppableedge");
			};
			outFunc = function (event, ui) {
				if (!ui.draggable.hasClass("ui-iggrid-groupedcolumnlabel")) {
					return false;
				}
				if (ui.draggable.attr("data-key") === $(this).attr("data-key")) {
					return false;
				}
				$(event.target).removeClass("ui-iggrid-groupbydroppableedge");
			};
			dropFunc = function (event, ui) {
				var parent, $dragged, $target;
				$(this).removeClass("ui-iggrid-groupbydroppableedge");
				if (!ui.draggable.hasClass("ui-iggrid-groupedcolumnlabel")) {
					return;
				}
				if (ui.draggable.attr("data-key") !== $(this).attr("data-key")) {
					/* regroup */
					/* reorder items, first find the original of the cloned one */
					parent = $(this).closest("ul");
					$target = $(this);
					$dragged = ui.draggable;
					if ($dragged.index() > $target.index()) {
						$dragged.insertBefore($target);
					} else {
						$dragged.insertAfter($target);
					}
					/*dragged = parent.find("li[ data-key=" + ui.draggable.attr("data-key") + " ]").first(); */
					/*dragged.insertAfter(this); */
					parent.find("li[ data-dragging=true ]").remove();
					parent.find("li").removeClass("ui-iggrid-groupbydroppableedge");
					groupby._regroup();
					groupby._groupAreaList();
					event.preventDefault();
					event.stopPropagation();
					return false;
				}

				return;
			};
			mousedownFunc = function (event) {
				event.preventDefault();
				event.stopPropagation();
				return false;
			};
			for (i = 0; i < allgrouped.length; i++) {
				key = allgrouped[ i ].key;
				layout = allgrouped[ i ].layout;
				text = allgrouped[ i ].col.headerText;
				if (allgrouped[ i ].dir === "asc") {
					css = "ui-iggrid-asc ui-icon ui-icon-arrowthick-1-n";
				} else {
					css = "ui-iggrid-desc ui-icon ui-icon-arrowthick-1-s";
				}
				/*w = allgrouped[ i ].col ? allgrouped[ i ].col.width : ""; */
				item = $("<li></li>").appendTo(list).addClass(this.css.groupedColumnLabel)
					.css("float", "left")
					.attr("data-key", key).attr("data-layout", layout || "")
					.bind({
						mouseup: $.proxy(this._changeSortState, this)
						/*mousedown: $.proxy(this._changeSortState, this) */
					}).draggable({
						containment: "body",
						appendTo: "parent",
						distance: 5,
						revert: "invalid",
						helper: helperFunc
						/*opacity: groupby.options.labelDragHelperOpacity */
					}).droppable({
						accept: "li",
						tolerance: "touch",
						/*activeClass: "ui-iggrid-groupbydroppableedge", */
						over: overFunc,
						out: outFunc,
						drop: dropFunc
					});
				if (this.options.groupByLabelWidth !== null) {
					item.width(this.options.groupByLabelWidth);
				}
				/* append triangle item (which is not a label). It should have the rightEdgeEnd class applied */
				itemEdge = $("<span></span>").appendTo(item).css({ "float": "right" })
					.addClass(this.css.groupedColumnLabel).addClass(this.css.groupedColumnLabelRightEdgeEnd);
				itemEdge.attr("data-marker", "edge");
				/*itemEdge.css("border-color", area.css("border-color")); */
				/* now get the previous item, if any, and change its class from "rightEdgeEnd" to "rightEdge" */
				item.prev().find("span[ data-marker ]")
					.removeClass(this.css.groupedColumnLabelRightEdgeEnd)
					.addClass(this.css.groupedColumnLabelRightEdge);
				/*$("<span></span>").appendTo($("<a></a>").appendTo(item).attr("href", "#").css("text-decoration", "none")).text(key); */
				if (layout) {
					$("<span></span>").appendTo(item)
						.text(text).addClass(this.css.groupedColumnLabelText);
					$("<span></span>").prependTo(item)
						.text(layout).addClass(this.css.groupedColumnLayoutLabel);
				} else {
					$("<span></span>").appendTo(item).text(text);
				}
				$("<span></span>").appendTo(item).addClass(css);
				/* M.H. 15 March 2012 Fix for bug #104811 */
				if (!$.ig.util.isTouch) {
					/* create remove button */
					$("<span></span>").appendTo(item).addClass("ui-iggrid-groupbyremovebutton")
						.attr("title", groupby.options.removeButtonTooltip).bind({
							mouseup: $.proxy(groupby._removeButtonClick, groupby),
							mousedown: mousedownFunc
							/*							mouseover: $.proxy(groupby._removeButtonHover, groupby), */
							/*							mouseout: $.proxy(groupby._removeButtonUnhover, groupby) */
						});
				}
				/* add a Li that won"t be a grouped one but will show an arrow */
				/* the "magic" value is required so that it leaves space for the icon plus the paddings/borders/margins */
				/* so that when we hover, the item doesn"t change its width dynamically */
				/* L.A. 05 October 2012 - Fixing bug #119933 - Groupby bar and show records per page style appear wrong in IE7, MVC4 */
				/* Specific patch for IE7 width defaults */
				if ($.ig.util.isIE7 && item.width() > 300) {
					item.width(260);
				} else {
					item.width(Math.ceil(item.width()) + 22);
				}
			}
			/* attach events */
			list.children().bind({
				"mouseover": $.proxy(this._showRemoveButton, this),
				"mouseout": $.proxy(this._hideRemoveButton, this)
			});
		},
		_changeSortState: function (event) {
			var li = $(event.target).closest("li"), key = li.attr("data-key"),
				i, layout = li.attr("data-layout"), layouts, eArgs,
				j, gc = this._orderedGroupedCols,
				s = this.grid.dataSource.settings.sorting.expressions, settings, lobj, noCancel;
			/*if (gc.length === 0 && !layout) { */
			/* if we are inside of changeSortState and the count is zero, get all grouped cols) */
			/*this._allGroupedCols(gc); */
			/* } */
			for (i = 0; i < gc.length; i++) {
				if (gc[ i ].key === key && ((layout && layout === gc[ i ].layout) || !layout)) {
					if (gc[ i ].dir === "asc") {
						gc[ i ].dir = "desc";
					} else {
						gc[ i ].dir = "asc";
					}
					for (j = 0; j < s.length; j++) {
						if (s[ j ].fieldName === key && ((layout && layout === s[ j ].layout) || !layout)) {
							s[ j ].dir = gc[ i ].dir;
							break;
						}
					}
					eArgs = { owner: this, groupedColumns: gc, key: gc[ i ].key, triggeredBy: "sortStateChanged" };
					noCancel = this._trigger(this.events.groupedColumnsChanging, event, eArgs);
					if (noCancel) {
						/* rebind */
						this._rebind();
						this._trigger(this.events.groupedColumnsChanged, event, eArgs);
					}
					/* this._groupAreaList(); */
					break;
				}
			}
			if (layout) {
				/* find the layout and change the settings */
				layouts = [  ];
				this._findLayout(layout, this.grid.options, layouts);
				lobj = layouts[ 0 ];
				for (i = 0; lobj.features && i < lobj.features.length; i++) {
					if (lobj.features[ i ].name === "GroupBy") {
						/* find the col setting in order to change the sort direction */
						settings = lobj.features[ i ].columnSettings;
						for (j = 0; j < settings.length; j++) {
							if (settings[ j ].columnKey === key) {
								settings[ j ].dir = settings[ j ].dir === "asc" ? "desc" : "asc";
								break;
							}
						}
						break;
					}
				}
				this._rebind();
			}
			/* li.removeClass("ui-state-highlight").find("[ data-marker ]").removeClass("ui-state-highlight"); */
		},
		_showRemoveButton: function (event) {
			var button = $(event.target).closest("li").find(".ui-iggrid-groupbyremovebutton"),
				key = $(event.target).closest("li").attr("data-key"),
				layout = $(event.target).closest("li").attr("data-layout");
			if (!key) {
				return;
			}
			/* position it and change its attribute */
			button.attr("data-key", key)
				.attr("data-layout", layout)
				.addClass("ui-icon ui-icon-circle-close");
			/* remove hovering for the items based on Lucia"s request */
			/*$(event.target).closest("li").addClass("ui-state-hover"); */
		},
		_hideRemoveButton: function (event) {
			var button = $(event.target).closest("li").find(".ui-iggrid-groupbyremovebutton");
			/*if (!button.hasClass("ui-state-hover")) { */
			button.removeClass("ui-icon ui-icon-circle-close");
			$(event.target).closest("li").removeClass("ui-state-hover");
			/*} */
		},
		_removeButtonClick: function (event) {
			var key = $(event.target).attr("data-key"), noCancel, layout, eArgs;//, area = $(".ui-iggrid-groupbyarea", this.grid.container())
			if (this._isDragging) {
				return;
			}
			eArgs = {
				owner: this,
				groupedColumns: this.options.groupedColumns,
				key: key,
				triggeredBy: "removeButton"
			};
			noCancel = this._trigger(this.events.groupedColumnsChanging, event, eArgs);
			if (noCancel) {
				layout = $(event.target).attr("data-layout");
				this.ungroupByColumn(key, layout);
				/* remove the actual list element */
				/* $(event.target).hide(); */
				/*area.find("ul li[ data-key="" + key + "" ][ data-layout="" + layout + "" ]").remove(); */
				this._trigger(this.events.groupedColumnsChanged, event, eArgs);
			}
			event.preventDefault();
			event.stopPropagation();
			return false;
		},
		destroy: function () {
			/*
			Destroys the group by feature object.
			*/
			var modalDialog = this.grid.container().find("#" + this.grid.id() + "_groupby_modalDialog");
			/* remove the groupby area */
			this.grid.container().find(".ui-iggrid-groupbyarea").remove();
			this._removeInitCallbacks();
			/* rebind the grid */
			/* remove any sorting expressions from the data source (this means that if a column has been sorted, that state will be lost when */
			/* GroupBy is destroyed */
			/* M.H. 13 Mar 2014 Fix for bug #166844: When the grouping is initial and you ungroup through modal dialog the grid remain grouped */
			if (this.grid._initialized) {
				this.ungroupAll();
			}
			/* M.H. 8 May 2012 Fix for bug #107895 */
			this._detachEvents(true);
			modalDialog.igGridModalDialog("getCaptionButtonContainer");
			modalDialog.remove();
			/* call base destroy */
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		_detachEvents: function (isDestroyDraggable) {
			var ths = this.grid.headersTable().find("th"), i;
			/* M.H. 8 May 2012 Fix for bug #107895 */
			/* when it is called from destroy it should be removed destroy functionallity. Otherwise when databind method of the grid is called then draggable should not be removed */
			if (isDestroyDraggable === true) {
				/* L.A. 20 February 2013 - jQuery 1.9 compatibility */
				/* Error: cannot call methods on draggable prior to initialization; attempted to call method "destroy" */
				for (i = 0; i < ths.length; ++i) {
					if ($(ths[ i ]).data().draggable) {
						$(ths[ i ]).draggable("destroy");
					}
				}
			}
			ths.unbind("mouseover", this._headerMouseOverHandler)
				.unbind("mouseout", this._headerMouseOutHandler);
			/* M.H. 26 April 2012 Fix for bug #105949 */
			/*this.grid.element.undelegate("td[ gbexpandcell=1 ]", "mousedown", this._toggleGroupHandler); */
			/* M.H. 26 Sep 2013 Fix for bug #153447: It is not possible to collapse a group row when columns are autogenerated and selection is enabled */
			$(document).undelegate("#" + this.grid.id() + "_container td[ gbexpandcell=1 ]",
				"mousedown", this._toggleGroupHandler);
			$(document).undelegate("#" + this.grid.id() + "_container td[ gbexpandcell=1 ]",
				"keydown", this._onKeyDownGroupHandler);
			/*this.grid.container().unbind("iggridheaderrendering", this._headerRenderingHandler); */
			/* M.H. 10 May 2012 Fix for bug #108221 */
			this.grid.element.unbind("iggridheaderrenderedinternal", this._headerRenderedHandler);
			this.grid.element.unbind("iggriddataempty", this._dataRenderingHandler);
			this.grid.element.unbind("iggrid_rowsrendering", this._rowsRenderingHandler);
			this.grid.element.unbind("iggridrowsrendered", this._rowsRenderedHandler);
			this.grid.element.unbind("iggridheadercellrendered", this._headerCellRenderedHandler);
			this.grid.element.unbind("iggridheadercelldragcancel", this._headerDragCancelHandler);
			this.grid.element.unbind("iggriduidirty", this._uiDirtyHandler);
			this.grid.element.unbind("iggriddatabound", this._dataBoundHandler);
			/* M.H. 12 Feb 2014 Fix for bug #164195: igGrid header/column misalignment when data source is changed at runtime, and grouping and hiding are applied */
			if (this._columnsCollectionModifiedHandler) {
				this.grid.element.unbind("iggridcolumnscollectionmodified",
					this._columnsCollectionModifiedHandler);
			}
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			if (isDestroyDraggable === true) {
				delete this._dataRenderingHandler;
				delete this._rowsRenderingHandler;
				delete this._rowsRenderedHandler;
				delete this._headerCellRenderedHandler;
				delete this._headerDragCancelHandler;
				delete this._uiDirtyHandler;
				delete this._columnsCollectionModifiedHandler;
			}
		},
		_initDefaultSettings: function () {
			var settings = [  ], key,
				cs = this.options.columnSettings, i, j, allowGroupBy,
				isToCheckUnboundColumns =
				(this.grid._hasUnboundColumns === true && this.options.type === "remote");
			this._orderedColumnSettings = [  ];
			/* initialize */
			if (this.grid.options.columns && this.grid.options.columns.length > 0) {
				for (i = 0; i < this.grid.options.columns.length; i++) {
					allowGroupBy = true;
					if (isToCheckUnboundColumns &&
						this.grid.getUnboundColumnByKey(this.grid.options.columns[ i ].key) !== null) {
						allowGroupBy = false;
					}
					settings[ i ] = {
						"columnIndex": i,
						"columnKey": this.grid.options.columns[ i ].key,
						"allowGrouping": allowGroupBy,
						"summaries": [  ],
						isGroupBy: false
					};
				}
			}
			/* M.H. 24 Mar 2014 Fix for bug #165993: Sorting is not working with initial sorting, autogenerate column and no columns definition */
			/* default expressions are applied ONLY IF column key is properly defined */
			if (settings.length === 0 &&
				this.grid.options.autoGenerateColumns &&
				cs.length > 0) {
				for (i = 0; i < cs.length; i++) {
					if (!cs[ i ].columnKey) {
						continue;
					}
					if (!cs[ i ].dir) {
						cs[ i ].dir = this.options.defaultSortingDirection || "asc";
					}
					this._orderedColumnSettings.push(cs[ i ]);
				}
			} else {
				for (i = 0; i < cs.length; i++) {
					for (j = 0; j < settings.length; j++) {
						if (settings[ j ].columnKey === cs[ i ].columnKey ||
							settings[ j ].columnIndex === cs[ i ].columnIndex) {
							break;
						}
					}
					if (j === settings.length) {
						continue;
					}

					for (key in cs[ i ]) {
						if (cs[ i ].hasOwnProperty(key) && key !== "columnKey" && key !== "columnIndex") {
							settings[ j ][ key ] = cs[ i ][ key ];
						}
					}
					/* M.H. 5 Mar 2014 Fix for bug #162907: GroupBy does not have option to set sort direction */
					if (settings[ j ].dir === undefined && this.options.defaultSortingDirection) {
						settings[ j ].dir = this.options.defaultSortingDirection;
					}
					this._orderedColumnSettings.push(settings[ j ]);
				}
			}
			/* copy */
			this.options.columnSettings = settings;
		},
		_renderExtraFooterCells: function (row, colgroup, prepend) {
			/* M.H. 12 Oct. 2011 Fix for bug #91479 */
			/* its how many grouped columns we have, the cell is only one */
			/* also mark the extra cell with a data-skip attribute so that features can do their  */
			/* calculations based on this */

			if (this.options.groupedColumns.length > 0) {
				if (prepend === true) {
					/* M.H. 17 Oct. 2011 Fix for bug 90815 - remove border-width and add css class */
					$("<td></td>").addClass(this.css.footerExtraCell).prependTo(row).attr("data-skip", true);
				} else {
					/* M.H. 17 Oct. 2011 Fix for bug 90815 */
					$("<td></td>").addClass(this.css.footerExtraCell).appendTo(row).attr("data-skip", true);
				}
			}
		},
		_renderExtraHeaderCells: function (row, colgroup, prepend) {
			/* its how many grouped columns we have, the cell is only one */
			/*for (i = 0; i < this.options.groupedColumns.length; i++) { */
			/* also mark the extra cell with a data-skip attribute so that features can do their  */
			/* calculations based on this */
			if (this.options.groupedColumns.length > 0) {
				if (prepend === true) {
					$("<td></td>").prependTo(row)
						.addClass(this.css.headerExtraCell).attr("data-skip", true);
					if (colgroup) {
						$("<col />").prependTo(colgroup)
							.attr("data-skip", true).css("width", this.options.indentation);
					}
				} else {
					$("<td></td>").appendTo(row)
						.addClass(this.css.headerExtraCell).attr("data-skip", true);
					if (colgroup) {
						$("<col />").appendTo(colgroup)
							.attr("data-skip", true).css("width", this.options.indentation);
					}
				}
			}
		},
		_addUpdataDataSkipCol: function (width) {
			/* add groupby data-skip col in colgroup for (data table, headers table, footers table) if necessary(grop a column for the first time) - and set its width. If there is such col in colgroup update its width */
			var colGroup, attrs,
				$htbl = this.grid.headersTable(),
				$ftbl = this.grid.footersTable();
			/* L.A. 27 April 2012 Fixed bug #110760 When in child layout autoGenerateColumns is true and group by a column it is not possible to resize layout columns */
			/* _registeredCol flag is saved now directly in the element */
			colGroup = this.grid.element.children("colgroup");
			if (colGroup.children("[ data-registeredCol=true ]").length === 0) {// it should be added groupby data-skip column
				/* M.H. 14 May 2013 Fix for bug 142258: Grouping column cause the last column to disappear and a wider expand button container in jquery 2.0.0 */
				attrs = { "data-skip": "true", "data-registeredCol": "true" };
				$("<col />").prependTo(colGroup).attr(attrs).width(width);
				if ($htbl.attr("id") !== this.grid.element.attr("id")) {
					/*A.Y. bug 93426. If we have a separate table for the data  */
					/*we need to add a col to the data table colgroup as well. */
					/* M.H. 14 May 2013 Fix for bug 142258: Grouping column cause the last column to disappear and a wider expand button container in jquery 2.0.0 */
					$("<col />").prependTo($htbl.children("colgroup")).attr(attrs).width(width);
				}
				if ($ftbl.attr("id") !== this.grid.element.attr("id")) {
					/* M.H. 14 May 2013 Fix for bug 142258: Grouping column cause the last column to disappear and a wider expand button container in jquery 2.0.0 */
					$("<col />").prependTo($ftbl.children("colgroup")).attr(attrs).width(width);
				}
			} else {// there is groupby data-skip col
				colGroup.children("col:nth-child(1)").width(width);
				$htbl.children("colgroup").children("col:nth-child(1)").width(width);
				$ftbl.children("colgroup").children("col:nth-child(1)").width(width);
			}
		},
		_columnsCollectionModified: function (event, args) {
			/* M.H. 4 April 2012 Fix for bug #107199 */
			if (args.owner.id() !== this.grid.id()) {
				return;
			}
			args.tbody = this.grid.element.children("tbody");
			if (this.options.groupedColumns.length > 0) {
				/* M.H. 6 Apr 2016 Fix for bug 217266: [ IgGrid ] rowVirtualization property is not compatible with Hidden column */
				this._addUpdataDataSkipCol(this._indent);
				if (this.grid.options.width === null) {
					/*A.Y. if grid has no width we need to adjust the container width to account for the special columns */
					/*it is done in the _setHiddenColumns function but the groupby column is not yet rendered */
					/*so we need to do it here again */
					this.grid._setContainerWidth(this.grid.container());
				} else {
					this.grid._updateGridContentWidth();
				}
			}
			this._dataEmpty(event, args);
		},
		_onUIDirty: function () {
			if (this.options.persist) {
				return;
			}
			/* reset groups */
			this.ungroupAll();
		},
		_saveSortingExpressions: function () {
			if (this.options.persist) {
				this.grid._savePersistenceData(this.grid.dataSource.settings.sorting.expressions, "sorting");
				/* M.H. 26 Feb 2014 Fix for bug #165340: Initially grouped columns are not persisted on data bind */
				this._groupedColumns = this._orderedGroupedCols;
				this._optionsGC = this.options.groupedColumns;
			}
		},
		_getSortingExpressions: function () {
			return this.grid._getPersistenceData("sorting");
		},
		_restoreGroupBy: function () {
			/* M.H. 26 Feb 2014 Fix for bug #165340: Initially grouped columns are not persisted on data bind */
			var se = this._getSortingExpressions(), col, layouts, lobj, i, key;
			if (this._groupedColumns) {
				this._orderedGroupedCols = this._groupedColumns;
			}
			if (this._optionsGC) {
				this.options.groupedColumns = this._optionsGC;
				/* M.H. 11 Mar 2014 Fix for bug #166528: Sorting and Filtering does not persist its state, when they are applied to unbound column */
				if (this.grid._hasUnboundColumns && this.options.type === "local") {
					for (i = 0; i < this._optionsGC.length; i++) {
						key = this._optionsGC[ i ].key;
						if (this._optionsGC[ i ].layout) {
							layouts = [  ];
							this._findLayout(this._optionsGC[ i ].layout, this.grid.options, layouts);
							lobj = layouts[ 0 ];

							col = this._getColumnByLayoutKey(key, lobj.columns);
						} else {
							col = this.grid.columnByKey(key);
						}
						if (col && col.unbound) {
							this.ungroupByColumn(key, this._optionsGC[ i ].layout);
						}
					}
				}
			}
			if (se) {
				this.grid.dataSource.settings.sorting.expressions = se;
				this.grid.dataSource.settings.sorting.defaultFields = se;
			}
		},
		_onDataBound: function () {
			if (this.options.groupedColumns.length > 0 && this.grid._isDataBoundCalled) {
				/* M.H. 9 Nov 2012 Fix for bug #126509 */
				this.grid._trigger("headerExtraCellsModified", null, { owner: this });
			}
		},
		_removeInitCallbacks: function () {
			var i;
			/* remove the header and footer init callbacks if they exist */
			for (i = 0; this.grid._headerInitCallbacks && i < this.grid._headerInitCallbacks.length; i++) {
				if (this.grid._headerInitCallbacks[ i ].type === "GroupBy") {
					/*A.T. 8 March 2012 - Fix for bug #104244  */
					/*this.grid._headerInitCallbacks.remove(i); */
					$.ig.removeFromArray(this.grid._headerInitCallbacks, i);
					break;
				}
			}
			for (i = 0; this.grid._footerInitCallbacks && i < this.grid._footerInitCallbacks.length; i++) {
				if (this.grid._footerInitCallbacks[ i ].type === "GroupBy") {
					/*A.T. 8 March 2012 - Fix for bug #104244  */
					/*this.grid._footerInitCallbacks.remove(i); */
					$.ig.removeFromArray(this.grid._footerInitCallbacks, i);
					break;
				}
			}
		},
		_addInitCallbacks: function () {
			this._removeInitCallbacks();
			/* we must check if those entries don"t exist yet (or remove them in destroy) */
			this.grid._headerInitCallbacks.push({
				type: "GroupBy",
				func: $.proxy(this._renderExtraHeaderCells, this)
			});
			/* M.H. 12 Oct. 2011 Fix for bug #91479 */
			this.grid._footerInitCallbacks.push({
				type: "GroupBy",
				func: $.proxy(this._renderExtraFooterCells, this)
			});
		},
		_injectGrid: function (gridInstance) {
			var i, gc, gcsort, s, root = null, orderedGrouped = null, layoutGrouped = null;
			this.grid = gridInstance;

			/*M.K. 1/9/2015 187174:Errors should be thrown when the grid is initialized with unsupported configurations */
			this._checkGridNotSupportedFeatures();

			if (this.options.type === null) {
				/* infer type */
				this.options.type = this.grid._inferOpType();
			}
			root = this.element.closest(".ui-iggrid-root");
			/* M.H. 8 Sep 2014 Fix for bug #167839: Remote filtering, sorting or group by are not persisted when applied to the child and root layout */
			if (this.options.persist && this.options.type === "remote" &&
				root.data("igGrid")) {
				this.options.persist = false;
			}
			if (root.is("div")) {
				root = root.find("#" + root.attr("id") + "_table");
			}
			root = root.data("igGridGroupBy");
			if (root) {
				orderedGrouped = root._orderedGroupedCols;
				if (orderedGrouped && orderedGrouped.length > 0 && this.grid.options.key) {
					layoutGrouped = [  ];
					for (i = 0; i < orderedGrouped.length; i++) {
						if (this.grid.options.key === orderedGrouped[ i ].layout) {
							layoutGrouped.push(orderedGrouped[ i ]);
						}
					}
				}
			}
			for (i = 0; i < this.grid.options.features.length; i++) {
				if (this.grid.options.features[ i ].name === "ColumnMoving") {
					this._movingEnabled = true;
					break;
				}
			}
			this._dataBoundHandler = $.proxy(this._onDataBound, this);
			/*this._headerRenderingHandler = $.proxy(this._headerRendering, this); */
			this._headerRenderedHandler = $.proxy(this._headerRendered, this);
			this._dataRenderingHandler = $.proxy(this._dataEmpty, this);
			this._toggleGroupHandler = $.proxy(this._toggleGroup, this);
			this._onKeyDownGroupHandler = $.proxy(this._onKeyDownGroup, this);
			this._rowsRenderingHandler = $.proxy(this._renderRecords, this);
			this._rowsRenderedHandler = $.proxy(this._recordsRendered, this);
			this._headerCellRenderedHandler = $.proxy(this._headerCellRendered, this);
			this._headerDragCancelHandler = $.proxy(this._headerCellDragCancel, this);

			if (this.grid.options.virtualization === true &&
				this.grid.options.virtualizationMode === "continuous") {
				this._rebuildVirtualRowsHandler = $.proxy(this._rebuildVirtualRows, this);
				this.grid.element.bind("iggridrebuildingvirtualrows", this._rebuildVirtualRowsHandler);

				this.grid._getTotalRowsCount = $.proxy(this._getTotalRowsCount, this);
			}
			/*M.K. overrides */
			/* override _getLastVisibleIndex function */
			this._getLastVisibleDataRecordIndexHandler =
				$.proxy(this.grid._getLastVisibleDataRecordIndex, this.grid);
			this.grid._getLastVisibleDataRecordIndex = $.proxy(this._getLastVisibleDataRecordIndex, this);

			/*override _calculateAvgRowHeight function */
			if (!this._calculateAvgRowHeightHandler) {
				this._calculateAvgRowHeightHandler = $.proxy(this.grid._calculateAvgRowHeight, this.grid);
				this.grid._calculateAvgRowHeight = $.proxy(this._calculateAvgRowHeight, this);
			}

			/* override _getTotalRowCount function */
			if (!this._getTotalRowCountHandler) {
				this._getTotalRowCountHandler = $.proxy(this.grid._getTotalRowCount, this.grid);
				this.grid._getTotalRowCount = $.proxy(this._getTotalRowCount, this);
			}

			/*A.T. 23 Nov Fix for bug #96602 The grouped row can"t be expanded when bind the data after init and initialExpand is set to false */
			this._detachEvents();
			/* M.H. 10 May 2012 Fix for bug #108221 */
			this.grid.element.bind("iggridheaderrenderedinternal", this._headerRenderedHandler);
			this.grid.element.bind("iggriddataempty", this._dataRenderingHandler);
			this.grid.element.bind("iggrid_rowsrendering", this._rowsRenderingHandler);
			this.grid.element.bind("iggridrowsrendered", this._rowsRenderedHandler);
			this.grid.element.bind("iggridheadercellrendered", this._headerCellRenderedHandler);
			this.grid.element.bind("iggriddatabound", this._dataBoundHandler);
			/* we need to cancel the "drag cancelling" done by features such as sorting, because GroupBy needs to be able to drag the headers */
			/* into the groupby area */
			this.grid.element.bind("iggridheadercelldragcancel", this._headerDragCancelHandler);

			this._headerMouseOverHandler = function (event) {
				$(event.currentTarget).addClass("ui-state-hover");
			};
			this._headerMouseOutHandler = function (event) {
				$(event.currentTarget).removeClass("ui-state-hover");
			};
			/* group by works on sorting */
			/* this.grid.dataSource.settings.sorting.sortUrlKey = "sort"; */
			/* this.grid.dataSource.settings.sorting.sortUrlAscValueKey = "asc"; */
			/* this.grid.dataSource.settings.sorting.sortUrlDescValueKey = "desc"; */
			/* A.T. 3 May - fix for bug 105720 */
			this.grid.dataSource.settings.sorting.sortUrlKey = this.options.groupByUrlKey;
			this.grid.dataSource.settings.sorting.sortUrlAscValueKey = this.options.groupByUrlKeyAscValue;
			this.grid.dataSource.settings.sorting.sortUrlDescValueKey = this.options.groupByUrlKeyDescValue;
			if (this.options.type) {
				this.grid.dataSource.settings.sorting.type = this.options.type;
			} else {
				this.grid.dataSource.settings.sorting.type = "remote";
			}
			this.grid.dataSource.settings.encodeExtraParams = $.proxy(this._encodeSummaryParams, this);
			if (!this.grid._initialized) {
				this._addInitCallbacks();
				this._initDefaultSettings();
				this.options.groupedColumns = [  ];
				if (!this.grid.options.key && (layoutGrouped === null || layoutGrouped.length === 0)) {
					for (i = 0; i < this._orderedColumnSettings.length; i++) {
						if (this._orderedColumnSettings[ i ].isGroupBy) {
							s = this._orderedColumnSettings[ i ];
							gc = { key: s.columnKey, dir: s.dir || "asc", col: this.grid.columnByKey(s.columnKey) };
							this.options.groupedColumns.push(gc);
							/* M.H. 17 May 2012 Fix for bug #111331 */
							gcsort = {
								fieldName: s.columnKey,
								dir: s.dir || "asc",
								layout: this.grid.options.key,
								isGroupBy: true
							};
							if (this.options.type === "local") {
								gcsort.layout = null;
								this.grid.dataSource.settings.sorting.defaultFields.push(gcsort);
							}
							/*this.grid.dataSource.settings.sorting.expressions.push(gcsort); */
							/* M.H. 17 May 2012 Fix for bug #111331 */
							this._addSortingExpressions(this.grid.dataSource.settings.sorting.expressions, gcsort);
						}
					}
				} else {
					for (i = 0; layoutGrouped !== null && i < layoutGrouped.length; i++) {
						this.options.groupedColumns.push(layoutGrouped[ i ]);
						gcsort = {
							fieldName: layoutGrouped[ i ].key,
							dir: layoutGrouped[ i ].dir || "asc",
							layout: this.grid.options.key,
							isGroupBy: true };
						if (this.options.type === "local") {
							gcsort.layout = null;
							this.grid.dataSource.settings.sorting.defaultFields.push(gcsort);
						}
						/*this.grid.dataSource.settings.sorting.expressions.push(gcsort); */
						/* M.H. 17 May 2012 Fix for bug #111331 */
						this._addSortingExpressions(this.grid.dataSource.settings.sorting.expressions, gcsort);
					}
				}
				this._saveSortingExpressions();
			}
			if (this.grid.element.closest(".ui-iggrid-root").length === 0 ||
				this.grid.element.hasClass("ui-iggrid-root") || this.grid._isWrapped) {
				/* M.H. 26 April 2012 Fix for bug #105949 - attach mousedown to TD */
				/*this.grid.element.delegate("td[ gbexpandcell=1 ]", "mousedown", this._toggleGroupHandler); */
				/* M.H. 26 Sep 2013 Fix for bug #153447: It is not possible to collapse a group row when columns are autogenerated and selection is enabled */
				$(document).delegate("#" + this.grid.id() + "_container td[ gbexpandcell=1 ]",
					"mousedown", this._toggleGroupHandler);
				$(document).delegate("#" + this.grid.id() + "_container td[ gbexpandcell=1 ]",
					"keydown", this._onKeyDownGroupHandler);
			}
			this._columnsCollectionModifiedHandler = $.proxy(this._columnsCollectionModified, this);
			this.grid.element.bind("iggridcolumnscollectionmodified",
				this._columnsCollectionModifiedHandler);
			this._uiDirtyHandler = $.proxy(this._onUIDirty, this);
			this.grid.element.bind("iggriduidirty", this._uiDirtyHandler);
			if (this.grid.element.igGridFeatureChooser !== undefined) {
				this.grid.element.igGridFeatureChooser();
			}
			/* M.H. 14 June 2012 Fix for bug #114327 */
			this._hierarchical = this.grid._isHierarchicalGrid;
			/*A.T. 21 March 2012 */
			if (this.options.persist) {
				/* M.H. 26 Feb 2014 Fix for bug #165340: Initially grouped columns are not persisted on data bind */
				this._restoreGroupBy();
				/*this._groupAreaList(); */
				/*this.grid._renderData(); */
			}
			this._allGroupedCols(this._orderedGroupedCols);
		},
		/*continuous virtualization: converts the pre-generated tbody to an array */
		/*each array element represents a TR; TRs can be grouping or not. */
		_tokenizeTBody: function (html) {
			var start = 0, trTokens = [  ], index = 0, begin, end, tr;
			while (true) {
				begin = html.indexOf("<tr", start);
				end = html.indexOf("</tr>", start);
				if (begin === -1 || end === -1) {
					break;
				}
				tr = html.substr(begin, end + 5 - begin);
				tr = tr.substr(0, 3) + " data-row-idx='" + (index++) + "' " + tr.substr(3);
				trTokens.push(tr);
				if (end + 5 < html.length - 1) {
					start = end + 5;
				} else {
					break;
				}
			}
			return trTokens;
		},
		/*continuous virtualization: overrides _determineVirtualRowCount of the grid.framework to determine the number of virtual rows */
		_determineVirtualRowCount: function (allRows, displayContainerHeight) {
			var div, rowNumber, rows, i, height, avgRowHeight, rowsPerPage, result;
			div = $("<div></div>)").appendTo("body")
				.css({ position: "absolute", top: -1800, left: -1800, visibility: "hidden" });
			rowNumber = allRows.length > 10 ? 10 : allRows.length;
			rows = "";
			for (i = 0; i < rowNumber; i++) {
				rows += allRows[ i ];
			}
			div.html(rows);
			height = div.height();
			avgRowHeight = height / rowNumber;
			rowsPerPage = displayContainerHeight / avgRowHeight;
			result = Math.ceil(rowsPerPage * 3);
			div.remove();
			return result;
		},
		/*continuous virtualization: overrides _getTotalRowsCount of the grid.framework to determine total row count */
		_getTotalRowsCount: function () {
			if (this.options.groupedColumns.length > 0 && this._groupedRows) {
				/* M.H. 4 Sep 2013 Fix for bug #151288: When you groupby a column in virtual grid you cannot scroll all the way down */
				if (!this.options.initialExpand && this._getTotalRowCountHandler) {
					return this._countGroupedRows;
				}
				return this._groupedRows.length;
			}

			return this.grid.dataSource.dataView().length;
		},
		/* gets called when rendering initially collapsed groups */
		_renderInitiallyCollapsedRows: function (startIndex, endIndex) {
			var i = 0, index = 0, tbodyHtml = "", tr;
			/*M.K. When the rows are initially collapsed the virtualRowCount */
			/*should be based on the visible groups. However we should render the non-visible sub-groups as well. */
			if (endIndex > this._visibleGroupedRows.length) {
				endIndex = this._visibleGroupedRows.length;
				startIndex = endIndex - this._virtualRowCount;
			}
			if (startIndex < 0) {
				startIndex = 0;
			}
			for (i = startIndex; i < endIndex; i++) {
				tbodyHtml += this._visibleGroupedRows[ i ];
				index = this._visibleRowIndexes[ i ] + 1;
				if (index > this._groupedRows.length - 1) {
					index = this._groupedRows.length - 1;
				}
				tr = this._groupedRows[ index ];
				while (tr !== null && tr !== undefined &&
					tr.indexOf("display:none") > -1 && tr.indexOf("data-grouprow") > -1) {
					/*get next hidden grouprow */
					tbodyHtml += tr;
					index++;
					tr = this._groupedRows[ index ];
				}
			}
			return tbodyHtml;
		},
		/*overrides _getLastVisibleDataRecordIndex of grid.framework */
		/*gets called when calculating whether a new chunk should be loaded in _tryScroll() function of grid.framework */
		_getLastVisibleDataRecordIndex: function () {
			if (this.options.groupedColumns.length > 0) {
				if (!this.options.initialExpand) {
					/*last visible index */
					return this._visibleRowIndexes[ this._visibleRowIndexes.length - 1 ];
				}
				return this._groupedRows.length;
			}

			return this.grid.dataSource.dataView().length;
		},
		/*continuous virtualization: overrides _rebuildVirtualRows of grid.framework */
		/*gets called each time when after scrolling current TRs are not sufficient to display the required data */
		_rebuildVirtualRows: function (event, args) {

			var scrollData, scrollResult, virtualScrollerY, deltaScroll, tableId, noCancel,
				visibleArea, tbody, expandedRowsHeight, trs, i,
				scrollerHeight, firstVisibleTR, firstVisibleTRRelativeOffset,
				startIndexToBeLoaded, dataRowIndexToBeFirst, endIndexToBeLoaded, hasOverflow, tr,
				lastVisibleTR, lastVisibleTRIndex, firstVisibleTRIndex, rowsHtml,
				newFirstVisibleRow, start, end, displayContainerScrollTop, avgRowHeight, maxScrollPos;

			/*I.I. bug fix for 107870 */
			if (args.owner.id() !== this.grid.id()) {
				return;
			}

			if (this.options.groupedColumns.length === 0) {
				return true;
			}
			scrollData = args.scrollData;
			scrollResult = args.scrollResult;
			virtualScrollerY = scrollData.virtualScrollerY;
			deltaScroll = scrollData.deltaScroll;
			tableId = "#" + this.element[ 0 ].id;
			visibleArea = scrollData.visibleArea;
			tbody = $(tableId + " > tbody");
			expandedRowsHeight = 0;
			tableId = "#" + this.element[ 0 ].id;
			trs = $(tableId + " > tbody > tr[ data-container='true' ]");
			for (i = 0; i < trs.length; i++) {
				expandedRowsHeight += $(trs[ i ]).outerHeight();
			}
			if (expandedRowsHeight > 0) {
				scrollerHeight = this.grid._getScrollContainerHeight();
				this.grid._setScrollContainerHeight(scrollerHeight - expandedRowsHeight);
			}
			/*next virtual page needed */
			if (deltaScroll > 0 && scrollResult.action === "SA_NEED_NEXT_PAGE") {
				/*get first visble TR */
				firstVisibleTR = this.grid._getFirstVisibleTR(visibleArea);
				/*get first visible TR offset relative to visible area */
				firstVisibleTRRelativeOffset = Math.abs(firstVisibleTR[ 0 ].offsetTop - visibleArea.top);
				/*first loaded data row becomes the row which was first visble before */
				startIndexToBeLoaded = parseInt(firstVisibleTR.attr("data-row-idx"), 0);
				dataRowIndexToBeFirst = startIndexToBeLoaded;
				endIndexToBeLoaded = parseInt(this._virtualRowCount, 0) + startIndexToBeLoaded - 1;
				hasOverflow = false;
				if (endIndexToBeLoaded > this._groupedRows.length - 1) {
					endIndexToBeLoaded = this._groupedRows.length - 1;
					startIndexToBeLoaded = endIndexToBeLoaded - this._virtualRowCount + 1;
					hasOverflow = true;
				}
				noCancel = this.grid._trigger(this.grid.events.rowsRendering, null,
					{ owner: this.grid, tbody: tbody, vrtWnd: {
						calledFromGroupBy: true,
						start: startIndexToBeLoaded,
						end: endIndexToBeLoaded
					}
				});
				if (noCancel) {
					tbody.empty();
					rowsHtml = "";
					if (this.options.initialExpand) {
						for (i = startIndexToBeLoaded; i <= endIndexToBeLoaded; i++) {
							rowsHtml += this._groupedRows[ i ];
						}
					} else {
						startIndexToBeLoaded =
							this._visibleRowIndexes.indexOf(parseInt(firstVisibleTR.attr("data-row-idx"), 0));
						endIndexToBeLoaded = startIndexToBeLoaded + this._virtualRowCount;
						rowsHtml = this._renderInitiallyCollapsedRows(startIndexToBeLoaded, endIndexToBeLoaded);
					}
					tbody.html(rowsHtml);
					if (hasOverflow) {
							tr = $(tableId + " > tbody > tr[ data-row-idx='" + dataRowIndexToBeFirst + "' ]");
							firstVisibleTRRelativeOffset = tr[ 0 ].offsetTop + firstVisibleTRRelativeOffset;
					}
					/*scroll to where last visible row was and add the new scrolling */
						this.grid._setDisplayContainerScrollTop(firstVisibleTRRelativeOffset + deltaScroll);
				}
			}
			/* M.H. 3 Dec 2014 Fix for bug #185933: Scrolling behaves strangely when grouping multiple columns with continuous rowVirtualization */
			if (deltaScroll < 0 && !this.options.initialExpand) {
				scrollResult.action = "SA_NEED_SOME_PAGE";
			}
			/*prev virtual page needed */
			if (deltaScroll < 0 && scrollResult.action === "SA_NEED_PREV_PAGE") {
				/*get last visible tr -> lastTR */
				lastVisibleTR = this.grid._getLastVisibleTR(visibleArea);
				lastVisibleTRIndex = parseInt(lastVisibleTR.attr("data-row-idx"), 0);
				firstVisibleTR = this.grid._getFirstVisibleTR(visibleArea);
				firstVisibleTRIndex = parseInt(firstVisibleTR.attr("data-row-idx"), 0);
				/*get its offset */
				/*lastVisibleTRRelativeOffset = visibleArea.bottom - lastVisibleTR[ 0 ].offsetTop; */
				/*get its data index */
				endIndexToBeLoaded = lastVisibleTRIndex;
				startIndexToBeLoaded = endIndexToBeLoaded - parseInt(this._virtualRowCount, 0) + 1;
				/*TODO: if start is < 0, recalc */
				if (startIndexToBeLoaded < 0) {
					startIndexToBeLoaded = 0;
					endIndexToBeLoaded = startIndexToBeLoaded + this._virtualRowCount - 1;
				}
				noCancel = this.grid._trigger(this.grid.events.rowsRendering, null,
					{ owner: this.grid, tbody: tbody, vrtWnd: {
						calledFromGroupBy: true,
						start: startIndexToBeLoaded,
						end: endIndexToBeLoaded
					} });
				if (noCancel) {
					/*rebind from dataIndex - this._virtualRowsCount to dataIndex */
					tbody.empty();
					rowsHtml = "";
					for (i = startIndexToBeLoaded; i <= endIndexToBeLoaded; i++) {
						rowsHtml += this._groupedRows[ i ];
					}
					tbody.html(rowsHtml);
					tableId = "#" + this.element[ 0 ].id;
					newFirstVisibleRow = $(tableId + " > tbody > tr[ data-row-idx='" +
						firstVisibleTRIndex + "' ]");
					this.grid._setDisplayContainerScrollTop(newFirstVisibleRow[ 0 ].offsetTop + deltaScroll);
				}
			}
			if (scrollResult.action === "SA_NEED_SOME_PAGE") {
				start = Math.floor(virtualScrollerY / this._avgRowHeight);
				end = start + this._virtualRowCount - 1;
				if (start < 0) {
					start = 0;
					end = start + this._virtualRowCount - 1;
				}
				if (end >= this._groupedRows.length - 1) {
					end = this._groupedRows.length - 1;
					start = end - this._virtualRowCount + 1;
				}
				noCancel = this.grid._trigger(this.grid.events.rowsRendering, null,
					{ owner: this.grid, tbody: tbody, vrtWnd: {
						calledFromGroupBy: true,
						start: start,
						end: end }
					});
				if (noCancel) {
					tbody.empty();
					rowsHtml = "";
					if (this.options.initialExpand) {
						for (i = start; i <= end; i++) {
							rowsHtml += this._groupedRows[ i ];
						}
					} else {
						rowsHtml = this._renderInitiallyCollapsedRows(start, end);
					}
					tbody.html(rowsHtml);
					displayContainerScrollTop = 0;
					maxScrollPos = this.grid._getScrollContainerHeight() - this.grid._getDisplayContainerHeight();
					if (maxScrollPos - 3 <= virtualScrollerY && virtualScrollerY <= maxScrollPos + 3) {
						displayContainerScrollTop = 10000;
					}
					if (!this.grid._persistVirtualScrollTop || displayContainerScrollTop !== 0) {
						this.grid._setDisplayContainerScrollTop(displayContainerScrollTop);
					}
				}
			}
			/* M.H. 3 Dec 2014 Fix for bug #185933: Scrolling behaves strangely when grouping multiple columns with continuous rowVirtualization */
			if (this._addScrollHeight && this.options.initialExpand) {
				scrollerHeight = this.grid._getScrollContainerHeight();
				this.grid._setScrollContainerHeight(scrollerHeight - this._addScrollHeight);
				this.grid.element.height("");
			}
			avgRowHeight = this.grid._calculateAvgRowHeight();
			if (avgRowHeight > this._avgRowHeight) {
				this._avgRowHeight = avgRowHeight;
			}
			/*M.K. If we have expanded rows and we"ve loaded a new chunk */
			/*the groups have reverted to their original state and we should clear _expandedRowCount */
			/* and apply the new _avgRowHeight */
			if (this._expandedRowCount > 0) {
				this._expandedRowCount = 0;
				this._avgRowHeight = avgRowHeight;
			}
			this.grid._setScrollContainerHeight(this._avgRowHeight * this.grid._getTotalRowCount());
			if (noCancel) {
				this.grid._trigger(this.grid.events.rowsRendered, null,
					{ owner: this.grid, tbody: tbody, calledFromGroupBy: true });
			}
			return false;
		},
		_checkGridNotSupportedFeatures: function () {
			/* Throw an exception for unsupported integration scenarios */
			var gridOptions = this.grid.options;
			if ((gridOptions.virtualization === true || gridOptions.rowVirtualization === true ||
				gridOptions.columnVirtualization === true) && gridOptions.virtualizationMode === "fixed") {
				/*Grouping not working with fixed virtualization */
				throw new Error($.ig.GridGroupBy.locale.fixedVirualizationNotSupported);
			}
		}
	});
	$.extend($.ui.igGridGroupBy, { version: "16.1.20161.2145" });
}(jQuery));
