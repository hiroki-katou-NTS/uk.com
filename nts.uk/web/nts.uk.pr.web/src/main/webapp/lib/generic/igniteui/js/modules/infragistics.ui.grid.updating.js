/*!@license
* Infragistics.Web.ClientUI Grid Editing 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
* jquery-1.9.1.js
* jquery.ui-1.9.0.js
* infragistics.ui.grid.framework.js
* infragistics.ui.grid.shared.js
* infragistics.ui.editors.js
* infragistics.ui.validator.js
* infragistics.ui.combo.js
* infragistics.ui.rating.js
* infragistics.ui.shared.js
* infragistics.dataSource.js
* infragistics.util.js
*/

/*global jQuery, window */
(function ($) {
	/*
	igGridUpdating is a widget based on jQuery UI that provides support for functionality related to data updates of igGrid.
	*/
	"use strict";
	$.widget("ui.igGridUpdating", {
		options: {
			/* type="array" Sets gets array of settings for each column. */
			columnSettings: [{
				/* type="string" Sets gets identifier for column. That value should correspond to the "key" of a column in the "columns" of igGrid. */
				columnKey: null,
				/* type="text|mask|date|datepicker|numeric|checkbox|currency|percent|combo|rating" Sets type of editor.
					text type="string" an igTextEditor will be created
					mask type="string" an igMaskEditor will be created
					date type="string" an igDateEditor will be created
					datepicker type="string" an igDatePicker will be created
					numeric type="string" an igNumericEditor will be created
					checkbox type="string" an igCheckboxEditor will be created
					currency type="string" an igCurrencyEditor will be created
					percent type="string" an igPercentEditor will be created
					combo type="string" the igCombo editor is created. Note: the css and js files used by ui.igCombo should be available.
					rating type="string" the igRating editor is created. Note: the css and js files used by ui.igRating should be available.
				*/
				editorType: null,
				/* type="object" Sets gets custom editor provider instance (it"s an entry point for implementing your custom editors).
					It should extend $.ig.EditorProviderDefault or it should have definitions of all its methods:
					$.ig.EditorProviderDefault = $.ig.EditorProviderDefault || $.ig.EditorProvider.extend({
						createEditor: function (updating, key, columnSetting, tabIndex, format, dataType, cellValue, element) {},
						getValue: function () {},
						setValue: function (val) {},
						setSize: function (width, height) {},
						setFocus: function () {},
						removeFromParent: function () {},
						destroy: function () {},
						validator: function () {},
						validate: function (noLabel) {},
						isValid: function () {}
					});
				*/
				editorProvider: null,
				/* type="object" Sets gets options supported by corresponding editor such as igEditor, igCombo, or custom editor defined by editorProvider option.
					In addition to specific editor options, it may contain the member "id", which will be set to the element of editor.
					That "id" can be usefull to link cascading igCombo editors.
				*/
				editorOptions: null,
				/* type="bool" Sets gets validation for required entry. */
				required: null,
				/* type="bool" Sets gets read only. If option is enabled, then editor is not used and cells in column are excluded from editing. */
				readOnly: null,
				/* type="bool" Enables disables validation of editor value.
				Value true: enable validation according to rules of igEditor.
				In case of numeric editors, the validation occurs for min/maxValue including range of values for dataMode, illegal number like "." or "-".
				In case of date editors, the validation occurs for min/maxValue and missing fields in "dateInputFormat".
				In case of mask editors, the validation occurs for not filled required positions in inputMask.
				If "editorOptions" enables "required", then validation for all types of editor has effect.
				Value false: do not enable validation. */
				validation: null,
				/* type="object" Sets gets default value in cell for add-new-row. That can be string, number, Date or boolean. */
				defaultValue: null
			}],
			/* type="row|cell|dialog|none|" Sets the edit mode.
				row type="string" editors for all cells in a row are displayed. The editor of the clicked cell receives focus. Optionally Done and Cancel buttons are displayed.
				cell type="string" an editor is displayed only for the clicked cell. The Done and Cancel buttons are not supported for this mode.
				dialog type="string" editors for all cells will be rendered as a popup dialog
			none type="string" editing of grid-cells is disabled
			*/
			editMode: "row",
			/* type="bool" Sets gets delete-row functionality.
			Value true: the "Delete" button is displayed on mouse-over a row and all selected rows are deleted by Delete-key.
			Value false: delete-row is disabled.
			*/
			enableDeleteRow: true,
			/* type="bool" Sets gets add-new-row functionality.
			Notes: If igGrid has primaryKey, then application should process generatePrimaryKeyValue event and provide value for a cell.
			It is also recommended to set readOnly:true for the column (within columnSettings) with the primaryKey or to use editorOptions:{readOnly:true}.
			By default the value of a cell with primary key is generated automatically and its value is equal to number of rows in grid plus 1.
			Value true: the "Add Row" button is displayed on header, click on that button shows editors in all columns and new row inserted at the end of editing.
			Value false: add-row functionality is disabled.
			*/
			enableAddRow: true,
			/* type="bool" Sets gets option to enable validation for all columns. */
			validation: false,
			/* type="string" Sets gets text for Done editing button. If that is not set, then $.ig.GridUpdating.locale.doneLabel is used. */
			doneLabel: null,
			/* type="string" Sets gets text for title of Done editing button. If that is not set, then $.ig.GridUpdating.locale.doneTooltip is used. */
			doneTooltip: null,
			/* type="string" Sets gets text for Cancel editing button. If that is not set, then $.ig.GridUpdating.locale.cancelLabel is used. */
			cancelLabel: null,
			/* type="string" Sets gets text for title of Cancel editing button. If that is not set, then $.ig.GridUpdating.locale.cancelTooltip is used. */
			cancelTooltip: null,
			/* type="string" Sets gets text for add-new-row button. If that is not set, then $.ig.GridUpdating.locale.addRowLabel is used. */
			addRowLabel: null,
			/* type="string" Sets gets text for title of add-new-row button. If that is not set, then $.ig.GridUpdating.locale.addRowTooltip is used. */
			addRowTooltip: null,
			/* type="string" Sets gets text for Delete row button. If that is not set, then $.ig.GridUpdating.locale.deleteRowLabel is used. */
			deleteRowLabel: null,
			/* type="string" Sets gets text for title of Delete row button. If that is not set, then $.ig.GridUpdating.locale.deleteRowTooltip is used. */
			deleteRowTooltip: null,
			/* type="bool" Sets gets visibility of the end-edit pop-up dialog with Done/Cancel buttons. */
			showDoneCancelButtons: true,
			/* type="bool" Sets gets ability to enable or disable exception, which is raised when grid has pending transaction and may fail to render data correctly. */
			enableDataDirtyException: true,
			/* type="string" Sets gets triggers for start edit mode.
			Possible values: "click", "dblclick", "F2", "enter" and their combinations separated by coma.
			Notes:
				The array of strings similar to ["dblclick", "f2"] is also supported.
			The keyboard triggers have effect only when "Selection" feature of grid is enabled.
				If the "dblclick" is included, then "click" has no effect.
			*/
			startEditTriggers: "click,F2,enter",
			/* type="bool" Enables horizontal move to the right on Enter while editing a cell.
			Value true: Pressing Enter will move the focus to the next edited cell on the right
			Value false: Pressing Enter will move the focus to the next edited cell on the row below
			*/
			horizontalMoveOnEnter: false,
			/* type="bool" Enables excel navigating style while editing a cell
			Value true: Arrows will not navigate inside the edited cell, but will exit the edit mode
			and move the focus to the nearest cell.
			Value false: Arrows will navigate the cursor inside the edited cell
			*/
			excelNavigationMode: false,
			/* type="function|string" Specifies a custom function to be called when AJAX request to the updateUrl option succeeds. Receives as argument the data returned by the server */
			saveChangesSuccessHandler: null,
			/* type="function|string" Specifies a custom function to be called when AJAX request to the updateUrl option fails. Receives three arguments - the jqXHR, string describing the type of error and an optional exception object */
			saveChangesErrorHandler: null,
			/* type="string|number" The swipe distance when on touch to trigger row delete button to appear. */
			swipeDistance: "100px",
			/* type="bool" Controls whether the widget will wrap around the grid when editing reaches a cell in one of the edges of the data view. */
			wrapAround: true,
			/* type="object" A list of options controlling the rendering behavior of the row edit dialog. If the edit mode is not 'dialog' these have no effect. */
			rowEditDialogOptions: {
				/* type="string" Specifies the caption of the dialog. If not set $.ig.GridUpdating.locale.rowEditDialogCaptionLabel is used. */
				captionLabel: null,
				/* type="owner|window" Controls the containment of the dialog's drag operation.
					owner type="string" The row edit dialog will be draggable only in the grid area
					window type="string" The row edit dialog will be draggable in the whole window area
				*/
				containment: "owner",
				/* type="string|number" Controls the default row edit dialog width
					string The dialog window width in pixels (370px).
					number The dialog window width as a number (370).
				*/
				width: "370px",
				/* type="string|number" Controls the default row edit dialog height
					string The dialog window height in pixels (350px).
					number The dialog window height as a number (350).
				*/
				height: "350px",
				/* type="number" Specifies the animation duration for the opening and closing operations. */
				animationDuration: 200,
				/* type="bool" Controls if editors should be rendered for read-only columns. If rendered, these editors will be disabled. */
				showReadonlyEditors: true,
				/* type="bool" Controls if editors should be rendered for hidden columns. */
				showEditorsForHiddenColumns: false,
				/* type="string|number|null" Controls the width of the column containing the column names in the default row edit dialog.
					string The width of the column in pixels (100px) or percents (20%).
					number The width of the column as a number (100) in pixels.
					null The width of the column will be left empty for the browser to size automatically.
				*/
				namesColumnWidth: "150px",
				/* type="string|number|null" Controls the width of the column containing the editors in the default row edit dialog.
					string The width of the column in pixels (100px) or percents (20%).
					number The width of the column as a number (100) in pixels.
					null The width of the column will be left empty for the browser to size automatically.
				*/
				editorsColumnWidth: null,
				/* type="bool" Controls the visibility of the done and cancel buttons for the dialog.
					If disabled the end-user will be able to stop editing only with the ENTER and ESC keys.
				*/
				showDoneCancelButtons: true,
				/* type="string|null" Specifies a template to be rendered against the currently edited record (or up-to-date key-value pairs in the case of not yet
					created records). It may contain an element decorated with the 'data-render-tmpl' attribute to specify where the control should render the
					editors template specified in the editorsTemplate option. For custom dialogs, the elements can be decorated with 'data-editor-for-<columnKey>'
					attributes where columnKey is the key of the column that editor or input will be used to edit.
					If both dialogTemplate and dialogTemplateSelector are specified, dialogTemplateSelector will be used.
					The default template is '<table><colgroup><col></col><col></col></colgroup><tbody data-render-tmpl></tbody></table>'.
				*/
				dialogTemplate: null,
				/* type="string|null" Specifies a selector to a template rendered against the currently edited record (or up-to-date key-value pairs in the case of not yet
					created records). It may contain an element decorated with the 'data-render-tmpl' attribute to specify where the control should render the
					editors template specified in the editorsTemplate option. For custom dialogs, the elements can be decorated with 'data-editor-for-<columnKey>'
					attributes where columnKey is the key of the column that editor or input will be used to edit.
					If both editorsTemplate and editorsTemplateSelector are specified, editorsTemplateSelector will be used.
					The default template is '<table><colgroup><col></col><col></col></colgroup><tbody data-render-tmpl></tbody></table>'.
				*/
				dialogTemplateSelector: null,
				/* type="string" Specifies a template to be executed for each column in the grid's column collection (or just the read-write columns if
					showReadonlyEditors is false). Decorate the element to be used as an editor with 'data-editor-for-${key}'. The ${key} template tag should
					be replaced with the chosen templating engine's syntax for rendering values. If any editors for columns are specified in the dialog markup
					they will be exluded from the data the template will be rendered for.
					This property is ignored if the dialog template does not include an element with the 'data-render-tmpl' attribute.
					If both editorsTemplate and editorsTemplateSelector are specified, editorsTemplateSelector will be used.
					The default template is "<tr><td>${headerText}</td><td><input data-editor-for-${key} /></td></tr>"
				*/
				editorsTemplate: null,
				/* type="string" Specifies a selector to a template to be executed for each column in the grid's column collection.
					Decorate the element to be used as an editor with 'data-editor-for-${key}'.
					The ${key} template tag should be replaced with the chosen templating engine's syntax for rendering values. If any editors for columns are
					specified in the dialog markup they will be exluded from the data the template will be rendered for.
					This property is ignored if the dialog markup does not include an element with the 'data-render-tmpl' attribute.
					If both editorsTemplate and editorsTemplateSelector are specified, editorsTemplateSelector will be used.
					The default template is "<tr><td>${headerText}</td><td><input data-editor-for-${key} /></td></tr>"
				*/
				editorsTemplateSelector: null
			},
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		events: {
			/* cancel="true" Event which is raised before start row editing.
			Return false in order to cancel editing and do not show editors in row.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.rowID to get key or index of row.
			Use ui.rowAdding to check if that event is raised while new-row-adding.
			*/
			editRowStarting: "editRowStarting",
			/* Event which is raised after start row editing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.rowID to get key or index of row.
			Use ui.rowAdding to check if that event is raised while new-row-adding.
			*/
			editRowStarted: "editRowStarted",
			/* cancel="true" Event which is raised before ending row editing.
			Return false in order to prevent the ending of the edit mode.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.rowID to get key or index of row.
			Use ui.update to check if value of any cell was modified and the data source will be updated. Can be set to false in order to prevent the update of the data source.
			Use ui.rowAdding to check if that event is raised while new-row-adding.
			Use ui.values[key] to get value of cell in column with the key. That is available only when ui.update is true.
			Use ui.oldValues[key] to get old value of cell in column with the key. That is available only when ui.update is true.
			*/
			editRowEnding: "editRowEnding",
			/* Event which is raised after end row editing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.rowID to get key or index of row.
			Use ui.update to check if value of any cell was modified and data source will be updated.
			Use ui.rowAdding to check if that event is raised while new-row-adding.
			Use ui.values[key] to get value of cell in column with the key. That is available only when ui.update is true.
			Use ui.oldValues[key] to get old value of cell in column with the key. That is available only when ui.update is true.
			*/
			editRowEnded: "editRowEnded",
			/* cancel="true" Event which is raised before start cell editing.
			Return false in order to cancel start editing and do not show editors.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.rowID to get key or index of row.
			Use ui.columnIndex to get index of column.
			Use ui.columnKey to get key of column.
			Use ui.editor to get reference to igEditor.
			Use ui.value to get or set value of editor.
			Use ui.rowAdding to check if that event is raised while new-row-adding.
			*/
			editCellStarting: "editCellStarting",
			/* Event which is raised after start cell editing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.rowID to get key or index of row.
			Use ui.columnIndex to get index of column.
			Use ui.columnKey to get key of column.
			Use ui.editor to get reference to igEditor.
			Use ui.value to get value of editor.
			Use ui.rowAdding to check if that event is raised while new-row-adding.
			*/
			editCellStarted: "editCellStarted",
			/* cancel="true" Event which is raised before ending cell editing.
			Return false in order to prevent the ending of the edit mode.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.rowID to get key or index of row.
			Use ui.columnIndex to get index of column.
			Use ui.columnKey to get key of column.
			Use ui.editor to get reference to igEditor.
			Use ui.value to get value of cell/editor. That value can be modified and it will be used to update data source.
			Use ui.oldValue to get old value.
			Use ui.update to check if value was modified and data source will be updated. Can be set to false in order to prevent the update of the data source.
			Use ui.rowAdding to check if that event is raised while new-row-adding.
			*/
			editCellEnding: "editCellEnding",
			/* Event which is raised after end cell editing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.rowID to get key or index of row.
			Use ui.columnIndex to get index of column.
			Use ui.columnKey to get key of column.
			Use ui.editor to get reference to igEditor.
			Use ui.value to get value of cell.
			Use ui.oldValue to get old value.
			Use ui.update to check if cell was modified and data source will be updated.
			Use ui.rowAdding to check if that event is raised while new-row-adding.
			*/
			editCellEnded: "editCellEnded",
			/* cancel="true" Event which is raised before adding new row.
			Return false in order to cancel adding new row to data source.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.values[key] to get new value of cell in column with the key.
			Use ui.oldValues[key] to get default value (before editing) of cell in column with the key.
			*/
			rowAdding: "rowAdding",
			/* Event which is raised after adding new row.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.values[key] to get new value of cell in column with the key.
			Use ui.oldValues[key] to get default value (before editing) of cell in column with the key.
			*/
			rowAdded: "rowAdded",
			/* cancel="true" Event which is raised before row deleting.
			Return false in order to cancel.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.element to get reference to jquery object which represents TR of row to delete.
			Use ui.rowID to get key or index of row to delete.
			*/
			rowDeleting: "rowDeleting",
			/* Event which is raised after row deleting.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.element to get reference to jquery object which represents TR of row to delete.
			Use ui.rowID to get key or index of row to delete.
			*/
			rowDeleted: "rowDeleted",
			/* cancel="true" Event which is raised when autoCommit of grid is not enabled and grid has pending transaction which may fail to be correctly rendered.
			Application should process that event and it may trigger commit of grid.
			Return false in order to prevent exception.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			*/
			dataDirty: "dataDirty",
			/* cancel="false" Event which is raised before adding new row to get value of cell for primaryKey column.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.value to set unique record-key-identifier for new row. It is prefilled with suggested value (defaultValue of column or number of rows in data source)
			*/
			generatePrimaryKeyValue: "generatePrimaryKeyValue",
			/* cancel="false" Event fired before the row edit dialog is opened.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.dialogElement to get reference to row edit dialog DOM element.
			*/
			rowEditDialogBeforeOpen: "rowEditDialogBeforeOpen",
			/* cancel="false" Event fired after the row edit dialog is opened.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.dialogElement to get reference to row edit dialog DOM element.
			*/
			rowEditDialogAfterOpen: "rowEditDialogAfterOpen",
			/* cancel="false" Event fired before the row edit dialog is closed.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.dialogElement to get reference to row edit dialog DOM element.
			*/
			rowEditDialogBeforeClose: "rowEditDialogBeforeClose",
			/* cancel="false" Event fired after the row edit dialog is closed.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.dialogElement to get reference to row edit dialog DOM element.
			*/
			rowEditDialogAfterClose: "rowEditDialogAfterClose",
			/* cancel="false" Event fired after the row edit dialog is rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridUpdating.
			Use ui.dialogElement to get reference to row edit dialog DOM element.
			*/
			rowEditDialogContentsRendered: "rowEditDialogContentsRendered"
		},
		css: {
			/*jscs:disable*/
			/* Classes applied to the container of Done and Cancel editing buttons. Default value is "ui-iggrid-buttoncontainer ui-widget-content" */
			buttonContainer: "ui-iggrid-buttoncontainer ui-widget-content ui-corner-all",
			/* Classes applied to the buttons. Default value is "ui-iggrid-button ui-state-default" */
			button: "ui-iggrid-button ui-state-default ui-corner-all",
			/* Classes applied to the Done button. Default value is "ui-iggrid-donebutton ui-priority-primary" */
			doneButton: "ui-iggrid-donebutton ui-priority-primary ui-corner-all",
			/* Class applied to the Cancel button. Default value is "ui-iggrid-cancelbutton" */
			cancelButton: "ui-iggrid-cancelbutton ui-corner-all",
			/* Classes applied to the Delete button. Default value is "ui-iggrid-deletebutton ui-state-default" */
			deleteButton: "ui-iggrid-deletebutton ui-state-default ui-corner-all",
			/* Classes applied to buttons in mouse-over state. Default value is "ui-iggrid-buttonhover ui-state-hover" */
			buttonHover: "ui-iggrid-buttonhover ui-state-hover",
			/* Classes applied to buttons in disabled state. Default value is "ui-iggrid-buttondisabled ui-state-disabled" */
			buttonDisabled: "ui-iggrid-buttondisabled ui-state-disabled",
			/* Classes applied to buttons in active/focus state. Default value is "ui-iggrid-buttonactive ui-state-active" */
			buttonActive: "ui-iggrid-buttonactive ui-state-active",
			/* Classes applied to the icon on Done button. Default value is "ui-iggrid-doneicon ui-icon ui-icon-check" */
			doneIcon: "ui-iggrid-doneicon ui-icon ui-icon-check",
			/* Classes applied to the icon on Done button. Default value is "ui-iggrid-cancelicon ui-icon ui-icon-cancel" */
			cancelIcon: "ui-iggrid-cancelicon ui-icon ui-icon-cancel",
			/* Class applied to Done and Cangel buttons when they have no text. Default value is "ui-iggrid-button-icon-only" */
			buttonIconOnly: "ui-iggrid-button-icon-only",
			/* Classes applied to the editing cells. Default value is "ui-iggrid-editingcell" */
			editingCell: "ui-iggrid-editingcell",
			/* Classes applied to the add-new-row button. Default value is "ui-iggrid-addrow ui-widget-header" */
			addRow: "ui-iggrid-addrow ui-widget-header",
			/* Classes applied to the add-new-row button in mouse-over state. Default value is "ui-iggrid-addrowhover ui-state-hover" */
			addRowHover: "ui-iggrid-addrowhover ui-state-hover",
			/* Classes applied to the add-new-row button in active/focus state. Default value is "ui-iggrid-addrowactive ui-state-active" */
			addRowActive: "ui-iggrid-addrowactive ui-state-active",
			/* Classes applied to the icon on add-new-row button. Default value is "ui-iggrid-addrowicon ui-icon ui-icon-circle-plus" */
			addRowIcon: "ui-iggrid-addrowicon ui-icon ui-icon-circle-plus",
			/* Classes applied to the icon on Delete button. Default value is "ui-iggrid-deleteicon ui-icon ui-icon-circle-close" */
			deleteIcon: "ui-iggrid-deleteicon ui-icon ui-icon-circle-close",
			/* Class applied to editors. Default value is "ui-iggrid-editor" */
			editor: "ui-iggrid-editor",
			/* Class applied to editors. */
			rowEditDialogHeaderCaption: "ui-dialog-titlebar ui-iggrid-filterdialogcaption ui-widget-header ui-corner-all ui-helper-reset ui-helper-clearfix",
			/* Class applied to editors. */
			rowEditDialogHeaderCaptionTitle: "ui-dialog-title",
			/* Classes applied to the close button of the row edit dialog */
			rowEditDialogCloseButton: "ui-icon ui-icon-close",
			/* Classes applied to the row edit dialog element */
			rowEditDialog: "ui-dialog ui-draggable ui-resizable ui-iggrid-dialog ui-widget ui-widget-content ui-corner-all",
			/* Classes applied to the filtering block area, when the advanced row edit dialog is opened and the area behind it is grayed out (that"s the block area) */
			blockArea: "ui-widget-overlay ui-iggrid-blockarea",
			/* Classes applied to the row edit dialog OK and Cancel buttons. */
			rowEditDialogButtonsContainer: "ui-dialog-buttonpane ui-widget-content ui-helper-clearfix",
			/* Classes applied to the default row edit dialog table */
			rowEditDialogTable: "ui-iggrid-filtertable ui-helper-reset"
			/*jscs:enable*/
		},
		/* public API */
		setCellValue: function (rowId, colKey, value) {
			/* Sets a cell value for the specified cell. It also creates a transaction and updates the UI.
			If the specified cell is currently in edit mode, the function will set the desired value in the cell's editor instead.
			paramType="number|string" The primary key of the row the cell is a child of.
			paramType="string" The column key of the cell.
			paramType="object" The new value for the cell.
			*/
			var provider, providerWrapper;
			if (rowId === null || rowId === undefined || $.type(colKey) !== "string") {
				return;
			}
			if (value === undefined) {
				throw new Error($.ig.GridUpdating.locale.undefinedCellValue);
			}
			if (!this._recOrPropFound(rowId, colKey)) {
				throw new Error($.ig.GridUpdating.locale.recordOrPropertyNotFoundException);
			}
			if (this.isEditing() && this._editingForRowId === rowId) {
				providerWrapper = this._providerForKey(colKey);
				if (this._originalValues.hasOwnProperty(colKey) && providerWrapper) {
					// the same cell is in edit mode - update the editor
					provider = providerWrapper.igEditorFilter("option", "provider");
					provider.setValue(value);
					this._editorTextChanged();
					return;
				} else {
					// we'll end edit here to ensure the rendering procedure invoked doesn't break the editing
					this.endEdit(false, false);
				}
			}
			this._updateCell(rowId, colKey, value);
		},
		updateRow: function (rowId, values) {
			/* Sets values for specified cells in a row. It also creates a transaction and updates the UI.
			If the specified row is currently in edit mode, the function will set the desired values in the row's editors instead.
			paramType="number|string" The primary key of the row to update.
			paramType="object" Pairs of values in the format { column1Key: value1, column2Key: value2, ... } .
			*/
			var key, provider, providerWrapper, shouldUpdate = false;
			if (rowId === null || rowId === undefined || $.type(values) !== "object") {
				return;
			}
			if (!this._recOrPropFound(rowId)) {
				throw new Error($.ig.GridUpdating.locale.recordOrPropertyNotFoundException);
			}
			if (this.isEditing() && this._editingForRowId === rowId) {
				/* the requested row is in edit mode or has a cell in edit mode */
				for (key in values) {
					providerWrapper = this._providerForKey(key);
					if (values.hasOwnProperty(key) &&
						this._originalValues.hasOwnProperty(key) &&
						providerWrapper) {
						provider = providerWrapper.igEditorFilter("option", "provider");
						provider.setValue(values[ key ]);
					} else {
						this.endEdit(false, false);
						shouldUpdate = true;
						break;
					}
				}
				this._editorTextChanged();
			} else {
				shouldUpdate = true;
			}
			if (shouldUpdate) {
				this._updateRow(rowId, values, null, null);
			}
		},
		addRow: function (values) {
			/* Adds a new row to the grid. It also creates a transaction and updates the UI.
			paramType="object" Pairs of values in the format { column1Key: value1, column2Key: value2, ... } .
			*/
			var settings = this.options.columnSettings,
				i = settings ? settings.length : 0,
				defVals = {}, key, val;
			while (i-- > 0) {
				key = settings[ i ].columnKey;
				val = settings[ i ].defaultValue;
				if (val !== undefined && key !== undefined && key !== null) {
					defVals[ key ] = val;
				}
			}
			this._addRow(null, $.extend(defVals, values), defVals, true);
		},
		deleteRow: function (rowId) {
			/* Deletes a row from the grid. It also creates a transaction and updates the UI.
			paramType="number|string" The primary key of the row to delete.
			*/
			if (rowId === null || rowId === undefined) {
				return;
			}
			if (!this._recOrPropFound(rowId)) {
				throw new Error($.ig.GridUpdating.locale.recordOrPropertyNotFoundException);
			}
			if (this.isEditing() && this._editingForRowId === rowId) {
				this.endEdit(false);
			}
			this._deleteRow(null, rowId, true);
		},
		startEdit: function (rowId, column, raiseEvents) {
			/* Starts editing for the row or cell specified.
			paramType="number|string" The row id.
			paramType="number|string" The column key or index.
			paramType="bool" optional="true" Specifies whether or not updating events should be raised for this operation.
			returnType="bool" Returns true if the operation succeeds.
			*/
			var columnIndex, columnKey, mode = this.options.editMode,
				visibleColumns = this.grid._visibleColumns(), cell, row;
			if (this.isEditing()) {
				throw new Error($.ig.GridUpdating.locale.editingInProgress);
			}
			if ($.type(column) === "string") {
				columnKey = column;
				columnIndex = this._getVisibleIndexForKey(columnKey);
			} else if ($.type(column) === "number" && column >= 0 && column < visibleColumns.length) {
				columnKey = visibleColumns[ column ].key;
				columnIndex = column;
			}
			if (!columnKey || columnIndex === undefined || columnIndex === null) {
				if (mode === "cell") {
					// for cell mode we need the columnKey and index to be defined and found
					// in the visible columns collection
					throw new Error($.ig.Grid.locale.columnNotFound.replace("{key}", columnKey));
				}
				columnKey = visibleColumns[ 0 ].key;
				columnIndex = 0;
			}
			/* check for editable */
			if (mode === "cell" && visibleColumns[ columnIndex ].readOnly) {
				return false;
			}
			/* at this point we can start editing for dialog */
			switch (mode) {
				case "dialog":
					return this._startEditDialog(null, !raiseEvents, rowId, false, columnKey, cell);
				case "row":
					row = this.grid.rowById(rowId);
					if (!row || !row.length) {
						throw new Error($.ig.GridUpdating.locale.rowOrColumnSpecifiedOutOfView);
					}
					/* calling startEditRow with element null means it'll properly focus the
					column specified by columnKey or the first rendered column (in column virtualization mode) */
					return this._startEditForRow(null, !raiseEvents, rowId, columnKey, row);
				case "cell":
					cell = this.grid.cellById(rowId, columnKey);
					if (!cell || !cell.length) {
						throw new Error($.ig.GridUpdating.locale.rowOrColumnSpecifiedOutOfView);
					}
					return this._startEditForCell(null, !raiseEvents, rowId, columnKey, cell, true, false);
			}
		},
		startAddRowEdit: function (raiseEvents) {
			/* Start for adding a new row.
			paramType="bool" optional="true" Specifies whether or not updating events should be raised for this operation.
			returnType="bool" Returns true if the operation succeeds.
			*/
			if (this.isEditing()) {
				throw new Error($.ig.GridUpdating.locale.editingInProgress);
			}
			if (!this.options.enableAddRow) {
				return false;
			}
			if (this.options.editMode === "dialog") {
				return this._startEditDialog(
					null, !raiseEvents, null, true, this.grid._visibleColumns()[ 0 ].key, null
				);
			}
			return this._startEditForRow(null, !raiseEvents, null, null,
				this.grid.headersTable().children("thead")
					.children("[data-add-row]").children(":not([data-skip='true'])").first());
		},
		endEdit: function (update, raiseEvents) {
			/* Ends the currently active edit mode.
			paramType="bool" optional="true" Specifies if the edit process should accept the current changes. Default is 'false'.
			paramType="bool" optional="true" Specifies whether or not updating events should be raised for this operation.
			returnType="bool" Returns false if the request fails and editing resumes.
			*/
			var dialog;
			if (!this.isEditing()) {
				return;
			}
			if (this.options.editMode === "dialog") {
				dialog = $("#" + this.grid.id() + "_updating_dialog_container");
				if (dialog && dialog.data("igGridModalDialog")) {
					dialog.igGridModalDialog("closeModalDialog", update, !!raiseEvents);
				}
			} else {
				return this._endEdit(null, !!update, !raiseEvents);
			}
		},
		findInvalid: function () {
			/* Find column-key which editor has invalid value.
			returnType="string" Return null or key of column which editor has invalid value and validation of column is enabled.
			*/
			var key, all = this._editors, provider;
			for (key in all) {
				if (all.hasOwnProperty(key) && all[ key ]) {
					provider = all[ key ].data("igEditorFilter").options.provider;
					provider.refreshValue();
					if (!all[ key ].data("igEditorFilter").options.provider.validate()) {
						return key;
					}
				}
			}
			return null;
		},
		isEditing: function () {
			/* Checks if the grid is in edit mode.
			returnType="bool" Returns true if the grid is in edit mode and false otherwise.
			*/
			return !!this._originalValues;
		},
		editorForKey: function (key) {
			/* Gets the editor for a column by the column key. That method can be used only after editor was already created.
			paramType="string" The key of the column.
			returnType="object" Returns a reference to the element the editor is initialized on or null.
			*/
			var provider;
			if (!this._editors || !this._editors[ key ]) {
				return null;
			}
			provider = this._editors[ key ];
			return provider.igEditorFilter("option", "provider").editor.element;
		},
		editorForCell: function (cell, create) {
			/* Gets the editor for a column by the cell it resides in. If allowed the function can create the editor if it has not been created yet.
			paramType="$" Reference to the jQuery-wrapped TD object of the grid that the editor belongs to.
			paramType="bool" optional="true" Requests to create the editor if it has not been created yet.
			returnType="object" Returns a reference to the element the editor is initialized on or null.
			*/
			var columnKey = this._getColumnKeyForCell(cell);
			if (!this._editors) {
				this._editors = {};
			}
			if (!this._editors[ columnKey ] && create) {
				this._editors[ columnKey ] = this._createEditor(cell, columnKey);
			}
			return this.editorForKey(columnKey);
		},
		destroy: function () {
			/* Destroys igGridUpdating
			returnType="object" Returns reference to this igGridUpdating.
			*/
			if (this.options.enableAddRow) {
				this._removeAddRow();
			}
			if (this.options.enableDeleteRow) {
				this._removeDeleteButton();
			}
			if (this.options.showDoneCancelButtons && this.options.editMode === "row") {
				this._removeDoneCancelButtons();
			}
			this._destroyAllEditors();
			this._unbindAllEvents();
			delete this._stopEditingHandler;
			delete this._gridHandlers;
			delete this._handlers;
			delete this._buttonHandlers;
			delete this._addNewRowHandlers;
			delete this._validationHandlers;
			delete this._dialogHandlers;
			delete this._editorCallbacks;
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		_setOption: function (key, value) {
			var opts = this.options, vt = $.type(value);
			/* users may get the columnSettings, do some changes and pass the array
			back to the grid - we would want for the change to be accepted*/
			if (opts[ key ] === value && vt !== "object" && vt !== "array") {
				return this;
			}
			/* add options that should throw exception */
			if (key === "editMode") {
				if (this.grid._isMultiRowGrid() && value !== "dialog") {
					throw new Error($.ig.GridUpdating.locale.multiRowGridNotSupportedWithCurrentEditMode);
				}
				this.endEdit(false, false);
				this._dialogInvalid = true;
				this._destroyAllEditors();
			}
			$.Widget.prototype._setOption.apply(this, arguments);
			switch (key) {
				case "rowEditDialogOptions":
					this._dialogInvalid = true;
					opts.rowEditDialogOptions = $.extend(opts.rowEditDialogOptions, value);
					break;
				case "enableDeleteRow":
					if (value === true) {
						this._renderDeleteButton();
					} else {
						this._removeDeleteButton();
					}
					break;
				case "enableAddRow":
					if (value === true && this.grid.options.showHeader) {
						this._updateAddNewRow();
					} else {
						this._removeAddRow();
					}
					this._rebindEditTriggers();
					break;
				case "startEditTriggers":
					this._analyzeEditTriggers();
					this._rebindEditTriggers();
					break;
				case "columnSettings":
					/* if new column settings are set we need to reset the editors */
					this.endEdit(false, false);
					this._destroyAllEditors();
					this._processReadOnly();
					this.grid._renderData();
					this._gridDirty = true;
					this._dialogInvalid = true;
					break;
			}
			if (key === "saveChangesSuccessHandler") {
				// M.H. 18 Feb 2014 Fix for bug #153639: Add successCallback and errorCallback params to the igDataSource.saveChanges API
				this._addDSSuccessHandler();
			} else if (key === "saveChangesErrorHandler") {
				// M.H. 18 Feb 2014 Fix for bug #153639: Add successCallback and errorCallback params to the igDataSource.saveChanges API
				this._addDSErrorHandler();
			}
			return this;
		},
		/* grid-related event handlers */
		_gridDataRendering: function (evt, ui) {
			var ds, l, noCancel;
			if (ui && ui.owner.id() !== this.grid.id()) {
				return;
			}
			ds = this.grid.dataSource;
			l = ds.data() ? ds.data().length : 0;
			if (!this.grid.options.showHeader) {
				this._processReadOnly();
			}
			if (this.isEditing()) {
				if (!this.endEdit(this.grid.options.autoCommit, true)) {
					this.endEdit(false, true);
				}
			}
			this._pkVal = Math.max(this._pkVal || 1, l + 1);
			if (ds.pendingTransactions().length) {
				// trigger data dirty if there are pending transactions
				if (this.options.enableDataDirtyException) {
					noCancel = this._trigger(this.events.dataDirty, null, { owner: this });
					if (noCancel) {
						throw new Error($.ig.GridUpdating.locale.dataDirtyException);
					}
				}
			}
			if (l && !this.grid.options.primaryKey) {
				throw new Error($.ig.Grid.locale.noPrimaryKeyDefined);
			}
			this.hideDeleteButton();
		},
		_gridRendered: function () {
			this._rebindEditTriggers();
		},
		_rowsRendered: function (evt, ui) {
			if (ui && ui.owner && ui.owner.id() !== this.grid.id()) {
				return;
			}
			this._paintModifiedCells();
		},
		_virtPreRender: function (evt, ui) {
			if (ui && ui.owner && ui.owner.id() !== this.grid.id()) {
				return;
			}
			if (this.isEditing() && !this._vscrolled) {
				// try to commit the changes
				if (!this._endEdit(evt, true, false, true)) {
					// if there are errors we still need to end edit
					this._endEdit(evt, false, false, true);
				}
			}
			delete this._vscrolled;
		},
		_virtPostRender: function (evt, ui) {
			if (ui && ui.owner && ui.owner.id() !== this.grid.id()) {
				return;
			}
			this.hideDeleteButton();
			this._paintModifiedCells();
		},
		_headerRendered: function (evt, ui) {
			// responsible for creating the add new row / should only be bound to when
			// options.enableAddRow is true
			if (ui.owner.id() !== this.grid.id()) {
				return;
			}
			this._updateAddNewRow();
		},
		_fixedColumnsChanged: function () {
			// M.H. 12 Feb 2016 Fix for bug 213842: Edit mode is not ended when fixing column in edit mode
			this._stopEditing();
			this._editorsContainerInvalid = true;
			if (this.options.enableAddRow) {
				this._updateAddNewRow();
			}
			if (this.options.enableDeleteRow) {
				this._removeDeleteButton();
				this._renderDeleteButton();
			}
			if (this.options.showDoneCancelButtons && this.options.editMode === "row") {
				this._removeDoneCancelButtons();
			}
		},
		_groupedColumnsChanged: function () {
			if (this.options.enableAddRow) {
				this._updateAddNewRow();
			}
		},
		_hidingFinishing: function () {
			if (this.isEditing()) {
				if (!this.endEdit(true, true)) {
					this.endEdit(false, true);
				}
			}
		},
		_columnsModified: function (evt, ui) {
			if (ui.owner.id() !== this.grid.id()) {
				return;
			}
			this._editorsContainerInvalid = true;
			if (this.options.enableAddRow) {
				this._updateAddNewRow();
			}
			this._paintModifiedCells();
		},
		_scroll: function () {
			if (this.isEditing()) {
				if (this._editingForRowId === null) {
					this._positionDoneCancelButtons(this.grid.headersTable().find("tr[data-new-row]"));
				} else if (this._editingForRowId !== undefined) {
					this._positionDoneCancelButtons(this.grid.rowById(this._editingForRowId));
				}
				this._hideValidatorMessages();
			}
		},
		/* event handlers on tds that should start editing */
		_mouseDown: function (evt) {
			var target = $(evt.target).closest("td"),
				targetContainer = target.closest(".ui-iggrid"),
				originalTarget = evt.originalEvent.target instanceof jQuery ?
				evt.originalEvent.target[ 0 ] : evt.originalEvent.target,
				targetGrid = target.closest(this.grid._isMultiRowGrid() ?
				".ui-iggrid-table-mrl" : ".ui-iggrid-table"),
				re = new RegExp("^" + this.grid.id() + "(_fixed)?$"),
				selection = this.grid.element.data("igGridSelection");
			if (evt.originalEvent && evt.originalEvent.type === "touchstart") {
				this._cevt = originalTarget;
			} else {
				if (this._cevt === originalTarget) {
					this._cevt = null;
					return;
				}
			}
			/* ensure we are handling it in the correct grid */
			if (targetContainer.length && targetContainer[ 0 ] === this.grid.container()[ 0 ]) {
				/* end editing in certain circumstances */
				if (this.isEditing()) {
					/* can be uncommented for blocking done/cancel buttons
					if (this.options.editMode === "cell" || !this.options.showDoneCancelButtons) {
					close editing if the end-user clicked somewhere else
					this will re-enable selection and allow for subsequent start edit which is
					at this point blocked indefinately because of the inability to select anything */
					if (!target.hasClass(this.css.editingCell) &&
						(this.options.editMode === "cell" ||
						this._getRowId(target.closest("tr")) !== this._editingForRowId)) {
						this._endEdit(evt, true, false, true);
					}
				}
				/* if the target is in the grid's header, we shouldn't wait for selection on it */
				if (this.grid.headersTable().children("thead").has(target).length ||
					this.grid.fixedHeadersTable().children("thead").has(target).length) {
					return;
				}
				if (targetGrid.length && targetGrid.attr("id").match(re)) {
					/* check for selection */
					if (selection && !target.hasClass("ui-iggrid-selectedcell")) {
						this._wait = true;
					} else {
						delete this._wait;
					}
				}
			}
		},
		_clickTrigger: function (evt) {
			var target = $(evt.target),
				targetCell = target.closest("td"),
				targetGrid = targetCell.closest(".ui-iggrid");
			if (targetGrid.length && targetGrid[ 0 ] === this.grid.container()[ 0 ] &&
				/* some elements inside cells shouldn't start editing */
				!target.is("a")) {
				if (this._wait) {
					delete this._wait;
					if (evt.type !== "dblclick") {
						return;
					}
				}
				this._startEditForElement(evt, targetCell);
			}
		},
		_keyDown: function (evt) {
			var target = $(evt.target),
				targetGrid = target.closest(".ui-iggrid"),
				sel, row;
			/* ensure we are handling it in the correct grid */
			if (!targetGrid.length || targetGrid[ 0 ] !== this.grid.container()[ 0 ]) {
				return;
			}
			if (target.is("td,tr")) {
				if ((evt.keyCode === $.ui.keyCode.ENTER && this._editTriggers.enter) ||
					(evt.keyCode === 113 && this._editTriggers.f2)) {
					if (this.options.editMode === "cell" && target.is("tr")) {
						target = this._getEditableCellsForRow(target).first();
					}
					this._startEditForElement(evt, target);
				} else if (evt.keyCode === $.ui.keyCode.DELETE &&
					this.options.enableDeleteRow && !this.isEditing()) {
					sel = this.grid.element.data("igGridSelection");
					if (sel && sel.options.mode === "row" && sel.options.multipleSelection) {
						this._deleteMultipleRows(evt);
					} else {
						row = target.closest("tr");
						if (this._isEditableRow(row)) {
							this._deleteRow(evt, this._getRowId(row), false);
						}
					}
				}
			}
		},
		/* touch support */
		_touchStart: function (evt) {
			/* store start/current point */
			this._firstTouchX = evt.originalEvent.touches && evt.originalEvent.touches[ 0 ] ?
				evt.originalEvent.touches[ 0 ].pageX : evt.originalEvent.clientX;
			/* store row of first touch */
			this._firstTouchRow = evt.originalEvent.touches ?
				$(evt.originalEvent.touches[ 0 ].target).closest("tr") :
				$(evt.originalEvent.target).closest("tr");
			/* store scroll position */
			this._storedScrollLeft = this.grid.scrollContainer().scrollLeft() || 0;
			this._storedScrollWidth = this.grid.scrollContainer().width() || 0;
		},
		_touchEnd: function (evt) {
			var changedTouchX = evt.originalEvent.changedTouches && evt.originalEvent.changedTouches[ 0 ] ?
				evt.originalEvent.changedTouches[ 0 ].pageX : evt.originalEvent.clientX;
			this._storedScrollLeft -= this.grid.scrollContainer().scrollLeft() || 0;
			this._storedScrollWidth -= this.grid.scrollContainer().width() || 0;
			if (this.options.enableDeleteRow &&
				Math.abs(changedTouchX - this._firstTouchX) > parseInt(this.options.swipeDistance, 10) &&
				this._firstTouchRow && this._storedScrollLeft === 0 && this._storedScrollWidth === 0 &&
				this._firstTouchRow
				.filter(":not([data-container],[data-grouprow],.ui-iggrid-deletedrecord)").length) {
				this.showDeleteButtonFor(this._firstTouchRow);
			}
			delete this._firstTouchX;
			delete this._firstTouchRow;
			delete this._storedScrollLeft;
			delete this._storedScrollWidth;
			delete this._firstTouchRow;
		},
		_pointerDown: function (evt) {
			var oe = evt.originalEvent;
			if (oe.pointerType !== "touch") {
				return;
			}
			this._firstTouchX = oe.pageX;
			this._firstTouchRow = $(oe.target).closest("tr");
			/* store scroll position */
			this._storedScrollLeft = this.grid.scrollContainer().scrollLeft() || 0;
			this._storedScrollWidth = this.grid.scrollContainer().width() || 0;
		},
		_pointerUp: function (evt) {
			var oe = evt.originalEvent, changedTouchX;
			if (oe.pointerType !== "touch") {
				return;
			}
			changedTouchX = oe.pageX;
			this._storedScrollLeft -= this.grid.scrollContainer().scrollLeft() || 0;
			this._storedScrollWidth -= this.grid.scrollContainer().width() || 0;
			if (this.options.enableDeleteRow &&
				Math.abs(changedTouchX - this._firstTouchX) > parseInt(this.options.swipeDistance, 10) &&
				this._firstTouchRow && this._storedScrollLeft === 0 && this._storedScrollWidth === 0 &&
				this._firstTouchRow
				.filter(":not([data-container],[data-grouprow],.ui-iggrid-deletedrecord)").length) {
				this.showDeleteButtonFor(this._firstTouchRow);
			}
			delete this._firstTouchX;
			delete this._firstTouchRow;
			delete this._storedScrollLeft;
			delete this._storedScrollWidth;
			delete this._firstTouchRow;
		},
		/* add row specific handlers */
		_addRowFocus: function (evt) {
			$(evt.target).closest("tr").addClass(this.css.addRowActive);
		},
		_addRowBlur: function (evt) {
			$(evt.target).closest("tr").removeClass(this.css.addRowActive);
		},
		/* delete-hover-related handlers */
		_rowMouseEnter: function (evt) {
			var row = $(evt.target).closest("tr");
			if (!this.isEditing() && this._isEditableRow(row)) {
				this.showDeleteButtonFor(row);
			} else {
				this.hideDeleteButton();
			}
		},
		_rowPointerEnter: function (evt) {
			if (evt.originalEvent.pointerType === "mouse") {
				this._rowMouseEnter(evt);
			}
		},
		_containerMouseLeave: function () {
			this.hideDeleteButton();
		},
		_containerPointerLeave: function (evt) {
			if (evt.originalEvent.pointerType !== "touch") {
				this.hideDeleteButton();
			}
		},
		/* event handlers for button elements - done, cancel, delete */
		_buttonMouseEnter: function (evt) {
			var button = $(evt.target).closest(".ui-iggrid-button,.ui-iggrid-deletebutton");
			if (!button.hasClass("ui-state-disabled")) {
				button.addClass(this.css.buttonHover);
			}
		},
		_buttonMouseLeave: function (evt) {
			var button = $(evt.target).closest(".ui-iggrid-button,.ui-iggrid-deletebutton");
			button.removeClass(this.css.buttonHover);
		},
		_buttonFocus: function (evt) {
			var button = $(evt.target).closest(".ui-iggrid-button,.ui-iggrid-deletebutton");
			if (!button.hasClass("ui-state-disabled")) {
				button.addClass(this.css.buttonActive);
			}
		},
		_buttonBlur: function (evt) {
			$(evt.target).removeClass(this.css.buttonActive);
		},
		_doneButtonClick: function (evt) {
			var target = $(evt.target).closest(".ui-iggrid-button");
			if (!target.length || target.hasClass(this.css.buttonDisabled)) {
				return;
			}
			this._endEdit(evt, true);
		},
		_doneButtonKeyUp: function (evt) {
			var target = $(evt.target).closest(".ui-iggrid-button");
			if (!target.length || target.hasClass(this.css.buttonDisabled) ||
				evt.keyCode !== $.ui.keyCode.ENTER) {
				return;
			}
			this._endEdit(evt, true);
		},
		_cancelButtonKeyUp: function (evt) {
			if (evt.keyCode === $.ui.keyCode.ENTER) {
				this._stopEditing();
			}
		},
		_deleteButtonClick: function (evt) {
			var rowId = $(evt.target).closest(".ui-iggrid-deletebutton").data("button-for");
			if (this._deleteRow(evt, rowId, false)) {
				this.hideDeleteButton();
			}
		},
		_touchDeleteButtonClick: function (evt) {
			var rowId = this._editingForRowId,
				target = $(evt.target).closest(".ui-iggrid-button");
			if (this.isEditing() && !target.hasClass(this.css.buttonDisabled)) {
				// should be always true
				this._endEdit(evt, false, false);
				this._deleteRow(evt, rowId, false);
			}
		},
		_touchDeleteButtonKeyUp: function (evt) {
			var rowId,
				target = $(evt.target).closest(".ui-iggrid-button");
			if (evt.keyCode === $.ui.keyCode.ENTER) {
				rowId = this._editingForRowId;
				if (this.isEditing() && !target.hasClass(this.css.buttonDisabled)) {
					// should be always true
					this._endEdit(evt, false, false);
					this._deleteRow(evt, rowId, false);
				}
			}
		},
		/* event handlers for dialog elements - done, keydown */
		_dialogCloseClick: function () {
			var dialog = $("#" + this.grid.id() + "_updating_dialog_container");
			if (dialog && dialog.data("igGridModalDialog")) {
				dialog.igGridModalDialog("closeModalDialog", false, true);
			}
		},
		_dialogDone: function (evt, ui) {
			ui.toClose = true;
		},
		_dialogCancel: function (evt, ui) {
			ui.toClose = true;
		},
		_dialogClosing: function (evt, ui) {
			var dialog = ui.modalDialog;
			if (ui.raiseEvents) {
				this._trigger(
					this.events.rowEditDialogBeforeClose, evt, { owner: this, dialogElement: dialog }
				);
			}
			return this._endEditDialog(evt, ui.accepted, ui.owner.getContent(), !ui.raiseEvents);
		},
		_dialogClosed: function (evt, ui) {
			if (ui.raiseEvents) {
				this._trigger(
					this.events.rowEditDialogAfterClose, evt, { owner: this, dialogElement: ui.modalDialog }
				);
			}
		},
		_dialogOpening: function (evt, ui) {
			var dialog = ui.modalDialog,
				opts = this.options.rowEditDialogOptions,
				rowId = this.grid._fixPKValue(dialog.attr("data-for-rowid")),
				isAdding = dialog.attr("data-isadding") === "true",
				content = dialog.igGridModalDialog("getContent"),
				templateParent,
				she = opts.showEditorsForHiddenColumns,
				cols = $.extend([], this.grid.options.columns),
				values = isAdding ? this._getDefaultValues() : this._getLatestValues(rowId),
				cache = content.find("[data-render-tmpl]").children(),
				invalid = !!this._editorsContainerInvalid,
				at = dialog.data("at"),
				rArgs;
			if (cache.length) {
				if (!invalid) {
					// detach the already rendered column template so we don't render it again
					cache.detach();
				} else {
					this._clearEditorsFromContainer(cache);
					cache.remove();
				}
			}
			/* always render the dialog content with the new values (template rendered against the record) */
			this._renderDialogContent(content, values);
			/* init editors on the record template, in this way the next init will only use cols that are not used here */
			this._initDialogEditors(content, cols, values);
			/* render template */
			templateParent = content.find("[data-render-tmpl]");
			if (templateParent.length && !templateParent.children().length) {
				if (!invalid) {
					cache.appendTo(templateParent);
				} else {
					this._renderDialogTemplate(templateParent, cols, !she);
					delete this._editorsContainerInvalid;
				}
			}
			this._initDialogEditors(content, cols, values);
			if (isAdding && !this._anyEditorInvalid()) {
				this._enableDoneButton();
			} else {
				this._disableDoneButton();
			}
			$.extend(this._originalValues, values, this._originalValues);
			rArgs = { owner: this, rowAdding: isAdding, rowID: rowId };
			if (at !== null && at !== undefined) {
				rArgs = $.extend({ parentID: at }, rArgs);
			}
			this._trigger(this.events.rowEditDialogAfterOpen, evt, { owner: this, dialogElement: dialog });
			this._trigger(this.events.editRowStarted, evt, rArgs);
		},
		_dialogOpened: function (evt, ui) {
			ui.shouldFocus = false;
			if (this._columnToFocus && this._columnToFocus.data &&
				this._columnToFocus.data("igEditorFilter")) {
				this._columnToFocus.igEditorFilter("setFocus");
			}
		},
		/* editor event handlers or callbacks */
		_editorErrorShowing: function (evt, ui) {
			var colKey = this._getEditorKey(ui.owner.element);
			/* do not show an error message if another editor already has one */
			if (this._errorShownFor && this._errorShownFor !== colKey) {
				return false;
			}
		},
		_editorErrorShown: function (evt, ui) {
			var colKey = this._getEditorKey(ui.owner.element);
			/* set the error holder */
			this._errorShownFor = colKey;
			this._disableDoneButton();
			/* focus and scroll to editor
			me._scrollTo(key); */
		},
		_editorErrorHidden: function () {
			delete this._errorShownFor;
			this._enableDoneButton();
		},
		_editorTextChanged: function () {
			// arguments: evt, ui, columnKey
			if (this._fromExitEditing) {
				delete this._fromExitEditing;
				return;
			}
			if (!this.findInvalid()) {
				this._enableDoneButton();
			} else {
				this._disableDoneButton();
			}
		},
		_editorKeyDown: function (evt, ui, columnKey) {
			var keyCode = evt.keyCode,
				rowId,
				editor = this._editors[ columnKey ],
				providerWrapper = editor.data("igEditorFilter"), provider,
				row = ui.owner.element.closest("tr"),
				isAdd = row.hasClass("ui-iggrid-addrow") || row.attr("data-new-row");
			if (!row.length) {
				return;
			}
			if (providerWrapper) {
				provider = providerWrapper.options.provider;
			}
			if (keyCode === $.ui.keyCode.ESCAPE) {
				// this should be the same for all edit modes
				if (this._revertValueForEditor(provider, columnKey)) {
					evt.stopPropagation();
					return;
				}
			}
			if (this.options.editMode === "dialog") {
				// don't navigate in the dialog
				return;
			}
			rowId = isAdd ? null : this._getRowId(row);
			this._navigateElement(evt, keyCode, row, rowId, columnKey, isAdd);
		},
		/* private methods */
		_stopEditing: function () {
			if (this.isEditing()) {
				this._endEdit(null, false);
			}
		},
		_paintModifiedCells: function () {
			// applies the modified styles for rows/cells of the specified tbody
			var transactions = this.grid.pendingTransactions(), tran, i;
			for (i = 0; i < transactions.length; i++) {
				tran = transactions[ i ];
				this._updateUIForTransaction(tran);
			}
		},
		_removeAddRow: function () {
			this.grid.fixedHeadersTable().children("thead")
				.children("tr[data-add-row='true'],tr[data-new-row='true']").remove();
			this.grid.headersTable().children("thead")
				.children("tr[data-add-row='true'],tr[data-new-row='true']").remove();
		},
		_updateAddNewRow: function () {
			// ensures add new row is rendered and updated - should be
			// called when the column collection is altered in some way that needs
			// the colspans recalculated / hidden tds modified, etc.
			if (this.grid.hasFixedColumns()) {
				this._updateAddNewRowForTable(true);
			}
			this._updateAddNewRowForTable(false);
		},
		_updateAddNewRowForTable: function (fixed) {
			var numOfCols, thead, i, j, addRow, newRow, vcols, ihc, layout;
			if (fixed) {
				thead = this.grid.fixedHeadersTable().children("thead");
			} else {
				thead = this.grid.headersTable().children("thead");
			}
			this._renderAddNewRow(thead);
			vcols = $.extend([], this.grid._visibleColumns(fixed));
			ihc = this.grid._initialHiddenColumns;
			if (ihc && ihc.length) {
				for (i = 0; i < ihc.length; i++) {
					for (j = 0; j < vcols.length; j++) {
						if (ihc[ i ].key === vcols[ j ].key) {
							$.ig.removeFromArray(vcols, j, j);
							break;
						}
					}
				}
			}
			/* in MRL grids the horizontal size is not equal to the number of columns */
			numOfCols = this.grid._isMultiRowGrid() ?
				this.grid._recordHorizontalSize() : vcols.length;
			addRow = thead.children("tr[data-add-row='true']");
			addRow.children("td:last").attr("colspan", numOfCols);
			layout = this.grid._isMultiRowGrid() ?
				this.grid._multiRowLayoutRenderingHelper(fixed) : this._generateDummyLayout(vcols);
			for (i = 0; i < layout.length; i++) {
				newRow = thead.children("tr[data-new-row='true']").eq(i).empty();
				for (j = 0; j < layout[ i ].length; j++) {
					$("<td></td>")
						.attr("aria-readonly", !!layout[ i ][ j ].col.readOnly)
						.attr("aria-describedby", this.grid.id() + "_" + layout[ i ][ j ].col.key)
						.attr("colspan", layout[ i ][ j ].cs || 1)
						.attr("rowspan", layout[ i ][ j ].rs || 1)
						.appendTo(newRow);
				}
			}
			/* finally get all the newrows from the layout */
			newRow = thead.children("tr[data-new-row='true']");
			if (fixed && this.grid.fixingDirection() === "left" ||
				(!fixed && (this.grid.fixingDirection() !== "left" || !this.grid.hasFixedColumns()))) {
				addRow.find("th[data-skip='true'],td[data-skip='true']").remove();
				newRow.find("th[data-skip='true'],td[data-skip='true']").remove();
				this.grid._headerInit(addRow, null, true);
				this.grid._headerInit(newRow, null, true);
			}
		},
		_toggleAddRow: function () {
			// toggles visibility between the add row and the new row containing the seperate cells
			var newRow = this.grid.headersTable().find("tr[data-new-row]"),
				addRow = this.grid.headersTable().find("tr[data-add-row]"),
				height;
			if (this.grid.hasFixedColumns()) {
				newRow = newRow.add(this.grid.fixedHeadersTable().find("tr[data-new-row]"));
				addRow = addRow.add(this.grid.fixedHeadersTable().find("tr[data-add-row]"));
			}
			if (newRow.eq(0).is(":visible")) {
				newRow.hide();
				addRow.show();
			} else {
				height = addRow.height();
				addRow.hide();
				newRow.show();
				newRow.css("height", height);
			}
		},
		_providerForKey: function (key) {
			if (!this._editors) {
				return null;
			}
			return this._editors[ key ] || null;
		},
		/* start edit proxy function from events */
		_startEditForElement: function (evt, target) {
			// only accepts tr or td as target
			// handles state checks and editable checks, handles end edit if necessery
			var mode = this.options.editMode,
				td = target.is("td") ? target : null,
				tr = target.is("tr") ? target : target.parent(),
				isAdding = tr.hasClass("ui-iggrid-addrow"),
				rowId = isAdding ? null : this._getRowId(tr),
				editEnded = true, col;
			if (mode === "dialog") {
				if (!td) {
					// possibly starting editing for row through keyup
					// get first cell for that row
					td = tr.children("td:not([data-skip='true'],[data-parent='true'])").first();
				}
				if (!this._isDataCell(td) || !this._isEditableRow(tr)) {
					return false;
				}
				this._startEditDialog(evt, false, rowId, isAdding, null, td);
			} else if (mode === "row" || tr.hasClass("ui-iggrid-addrow")) {
				if (!td) {
					// possibly starting editing for row through keyup
					// get first cell for that row
					td = tr.children("td:not([data-skip='true'],[data-parent='true'])").first();
				}
				if (!this._isDataCell(td) || !this._isEditableRow(tr)) {
					return false;
				}
				/* first handle the case when editing is already in progress */
				if (this.isEditing()) {
					if (this._editingForRowId === rowId) {
						// don't start editing for the same row
						return false;
					}
					/* commented for parity with 15.1
					if (this.options.showDoneCancelButtons && mode === "row") {
					when done/cancel buttons are enabled ending is controlled
					by them and the keyboard
					return false;
					} */
					editEnded = this._endEdit(evt, true, false);
				}
				return !editEnded ? false : this._startEditForRow(evt, false, rowId, null, td);
			} else if (mode === "cell") {
				if (!this._isEditableCell(td)) {
					return false;
				}
				/* first handle the case when editing is already in progress */
				if (this.isEditing()) {
					col = this._getColumnKeyForCell(td);
					if (rowId === this._editingForRowId && col && this._originalValues.hasOwnProperty(col)) {
						// don't start editing for the same cell
						return false;
					}
					editEnded = this._endEdit(evt, true, false);
				}
				return !editEnded ? false : this._startEditForCell(evt, false, rowId, col, td, true, isAdding);
			}
			return false;
		},
		/* start/end edit main functions */
		_startEditDialog: function (evt, suppress, rowId, adding, columnKey, element, at) {
			// dialog starts edit without the need for a row element
			// this means that for row adding we'll use another parameter
			var dialog = this._renderRowEditDialog(!!this._dialogInvalid), rArgs;
			rArgs = { owner: this, rowAdding: adding, rowID: rowId };
			if (at !== null && at !== undefined) {
				dialog.data("at", at);
				rArgs = $.extend({ parentID: at }, rArgs);
			}
			if (!suppress && !this._trigger(this.events.editRowStarting, evt, rArgs)) {
				return false;
			}
			dialog.attr({
				"data-for-rowid": rowId,
				"data-isadding": adding
			});
			if (!suppress) {
				this._trigger(this.events.rowEditDialogBeforeOpen, evt, { owner: this, dialogElement: dialog });
			}
			this._disableDoneButton();
			if (element && element.is("td") && !columnKey) {
				columnKey = this._getColumnKeyForCell(element);
			}
			this._columnToFocus = this._chooseColumnToFocus(adding, columnKey);
			dialog.igGridModalDialog("openModalDialog");
			return true;
		},
		_startEditForRow: function (evt, suppress, rowId, columnKey, element) {
			var rowElement, args, editModeStarted = false, colElements,
				pair, columnToFocus = null, i, allValues, cellValue, isAdding, at;
			rowElement = this._resolveRowElement(element, rowId);
			isAdding = rowElement.hasClass("ui-iggrid-addrow");
			at = this.grid._normalizedKey(rowElement.data("parent-id"));
			args = {
				owner: this,
				rowAdding: isAdding,
				rowID: rowId
			};
			if (at !== null && at !== undefined) {
				args = $.extend({ parentID: at }, args);
			}
			if (!suppress && !this._trigger(this.events.editRowStarting, evt, args)) {
				return;
			}
			if (this._gridDirty) {
				rowElement = this._resolveRowElement(isAdding ? element : null, rowId);
				if (!rowElement || !rowElement.length) {
					return;
				}
			}
			if (isAdding) {
				// we need the cell elements from the hidden split row
				rowElement = this._combineRowElements(rowElement);
				if (!rowElement.attr("data-new-row")) {
					rowElement = rowElement.siblings("[data-new-row]");
					this._toggleAddRow();
				}
			}
			if (element && element.is("td") && !columnKey) {
				columnKey = this._getColumnKeyForCell(element);
			}
			/* choose an editor to focus based on input and the state of the grid (virtualization, readonly cols, etc.) */
			columnToFocus = this._chooseColumnToFocus(isAdding, columnKey);
			/* for each cell start editing for that cell, also focus the one that's clicked
			make sure that only editors for cells that are rendered are used (column virtualization scenario) */
			colElements = this._getEditableElementsForRow(rowElement);
			/* all cells that are not rendered due to virtualization will have element: null */
			allValues = isAdding ? this._getDefaultValues() : this._getLatestValues(rowId);
			for (i = 0; i < colElements.length; i++) {
				pair = colElements[ i ];
				cellValue = allValues[ pair.colKey ] === undefined ? null : allValues[ pair.colKey ];
				/* at least one cell in a row should start editing to detect row editing */
				editModeStarted = this._startEditForCell(evt, suppress, rowId, pair.colKey, pair.element,
					pair.colKey === columnToFocus, isAdding, cellValue) || editModeStarted;
			}
			/* check if even one is actually edited */
			if (editModeStarted) {
				if (this.options.showDoneCancelButtons) {
					this._showDoneCancelButtons();
					this._positionDoneCancelButtons(rowElement);
					if (isAdding && !this._anyEditorInvalid()) {
						this._enableDoneButton();
					} else {
						this._disableDoneButton();
					}
					if (this._renderTouchUI) {
						this._updateTouchButtons(isAdding);
					}
				}
				if (!suppress) {
					this._trigger(this.events.editRowStarted, evt, args);
				}
				this._editingForRowId = isAdding ? null : rowId;
				$.extend(this._originalValues, allValues, this._originalValues);
				this._selectionToggle(rowElement);
				/* null is a magic value for row adding since we don't have rowid there */
			} else {
				rowElement.children().removeClass(this.css.editingCell);
				return false;
			}
			return true;
		},
		_startEditForCell: function (evt, suppress, rowId, columnKey, element, focus, isAdding, value) {
			var providerWrapper,
				provider,
				validator, args,
				editor, newValue,
				width, height;
			/* redundant checks to ensure proper use */
			columnKey = columnKey || this._getColumnKeyForCell(element);
			rowId = rowId === null ? this._getRowId(element.closest("tr")) : rowId;
			if (element) {
				// element may be null when starting row edit for virtual grids
				element.addClass(this.css.editingCell);
				editor = this.editorForCell(element, true);
				providerWrapper = this._providerForKey(columnKey);
				if (providerWrapper) {
					provider = providerWrapper.igEditorFilter("option", "provider");
				} else {
					return false;
				}
				height = element.outerHeight();
				width = element.outerWidth();
				width = this._isLastScrollableCell(element) ? width - this.grid._scrollbarWidth() : width;
			}
			if (value === undefined) {
				value = this._getLatestValues(rowId, columnKey);
				value = value === undefined ? null : value;
			}
			args = {
				owner: this,
				rowID: rowId,
				columnIndex: this.grid.getVisibleIndexByKey(columnKey),
				columnKey: columnKey,
				editor: editor,
				value: value,
				rowAdding: isAdding
			};
			if (!suppress && !this._trigger(this.events.editCellStarting, evt, args)) {
				// reset editor if available
				if (editor) {
					provider.setValue(null);
				}
				element.removeClass(this.css.editingCell);
				return false;
			}
			if (editor) {
				// P.Zh. 12 Mar 2016 Fix for bug #214414: Expansion indicator is visible in edit mode
				providerWrapper.css("z-index", 1);
				providerWrapper.prependTo(element);
				provider.setSize(Math.max(8, width), Math.max(10, height));
				validator = provider.validator();
				newValue = args.value;
				provider.setValue(newValue);
			}
			this._originalValues = this._originalValues || {};
			this._originalValues[ columnKey ] = editor && newValue === value ? provider.getValue() : value;
			this._trigger(this.events.editCellStarted, evt, args);
			if (focus && editor) {
				this._activateEditor(providerWrapper);
			}
			this.hideDeleteButton();
			this._editingForRowId = rowId;
			if (this.options.editMode === "cell" && !isAdding) {
				this._selectionToggle(element);
			}
			return true;
		},
		_endEditDialog: function (evt, shouldUpdate, dialogContent, suppress) {
			var newValues = {}, prevValues = this._originalValues, update, colElements, hasInvalid,
				add = dialogContent.parent().attr("data-isadding") === "true", noCancel, isChanged,
				rowId = this.grid._fixPKValue(dialogContent.parent().attr("data-for-rowid")),
				at = this.grid._fixPKValue(dialogContent.parent().data("at"));
			colElements = this._getEditedColumnElementsForDialog(dialogContent);
			if (shouldUpdate) {
				hasInvalid = this._handleInvalid(colElements, evt);
				if (hasInvalid) {
					// stop closing
					return false;
				}
				/* new values need to be obtained at this point */
				newValues = this._getNewValuesForRow(colElements);
				isChanged = this._compareValues(prevValues, newValues);
				if (add) {
					newValues = $.extend({}, prevValues, newValues);
				}
			}
			update = {
				update: shouldUpdate && (isChanged || add !== undefined)
			};
			/* throw -ing events */
			if (!suppress) {
				// handles both row and cell events
				noCancel = this._fireEndingEvents(
					evt, prevValues, newValues, colElements, rowId, add, update, at
				);
				if (!noCancel) {
					// the function will decide when to return false in which case editing should continue
					// in all other cases the shouldUpdate or newValues object will be modified
					return false;
				}
			}
			delete this._originalValues;
			delete this._editingForRowId;
			if (update.update && (this._compareValues(prevValues, newValues) || add)) {
				// don't update if user cancelled it from the events or if there is no change after reverting values, etc.
				if (add) {
					this._addRow(evt, newValues, prevValues, suppress, at);
				} else {
					this._updateRow(prevValues[ this.grid.options.primaryKey ], newValues, null, null);
				}
			}
			if (!suppress) {
				this._fireEndedEvents(evt, prevValues, newValues, colElements, rowId, add, update, at);
			}
			/* we don't go through the usual detach mechanism with the dialog
			so we need to hide the validation messages manually. */
			this._hideValidatorMessages();
			if (at !== null && at !== undefined) {
				dialogContent.parent().removeData("at");
			}
			return true;
		},
		_endEdit: function (evt, shouldUpdate, suppress, dontToggle) {
			// only responsible for cell/row mode
			var cell, row, rowId, colKey, colElements, hasInvalid, add,
				prevValues = this._originalValues, newValues = {},
				isChanged, noCancel = true, update, val, at, self = this;
			/* first find the cell/row being edited
			hierarchical grid makes the following harder */
			cell = this.grid.container().find("." + this.css.editingCell)
				.filter(function () {
					var clg = $(this).closest("table").attr("id");
					return clg === self.grid.element.attr("id") || clg === self.grid.headersTable().attr("id") ||
						clg === self.grid.fixedBodyContainer().children("table").attr("id") ||
						clg === self.grid.fixedHeadersTable().attr("id");
				}).first();
			row = cell.closest("tr");
			at = row.data("parent-id");
			rowId = this._getRowId(row);
			add = row.data("new-row");
			colElements = this._getEditedColumnElementsForRow(row);
			/* check for invalid messages and create update object if update is requested */
			if (shouldUpdate) {
				hasInvalid = this._handleInvalid(colElements, evt);
				if (hasInvalid) {
					// if invalid editors are present we will not update and not end edit
					return false;
				}
				/* new values need to be obtained at this point */
				newValues = this._getNewValuesForRow(colElements);
				isChanged = this._compareValues(prevValues, newValues);
				if (add) {
					newValues = $.extend({}, prevValues, newValues);
				}
			}
			update = {
				/* should be able to add a row with default values */
				update: shouldUpdate && (isChanged || add !== undefined)
			};
			/* throw -ing events */
			if (!suppress) {
				// handles both row and cell events
				noCancel = this._fireEndingEvents(
					evt, prevValues, newValues, colElements, rowId, add, update, this.grid._normalizedKey(at)
				);
				if (!noCancel) {
					// the function will decide when to return false in which case editing should continue
					// in all other cases the shouldUpdate or newValues object will be modified
					return false;
				}
			}
			/* at this point we know if the end edit is interrupted in any way and can start closing down editing */
			this._closeEditingForRow(colElements);
			delete this._originalValues;
			delete this._editingForRowId;
			if (add) {
				this._toggleAddRow();
			}
			/* choose the correct command */
			if (update.update && (this._compareValues(prevValues, newValues) || add)) {
				// don't update if user cancelled it from the events or if there is no change after reverting values, etc.
				if (add) {
					this._addRow(evt, newValues, prevValues, suppress, at);
				} else if (this.options.editMode === "cell") {
					colKey = colElements[ 0 ].colKey;
					cell = colElements[ 0 ].element;
					val = newValues[ colKey ];
					this._updateCell(rowId, colKey, val, cell);
				} else {
					this._updateRow(rowId, newValues, prevValues, row);
				}
			}
			if (!suppress) {
				this._fireEndedEvents(evt, prevValues, newValues, colElements, rowId, add, update);
			}
			this._hideDoneCancelButtons();
			this._selectionToggle(null, dontToggle);
			delete this._fromExitEditing;
			return true;
		},
		/* end edit private functions */
		_handleInvalid: function (colElements, evt) {
			var i, key, editor, provider, providerWrapper;
			for (i = 0; i < colElements.length; i++) {
				key = colElements[ i ].colKey;
				editor = this._providerForKey(key);
				providerWrapper = editor.data("igEditorFilter");
				/* this shouldn't be needed but e.g. DateEditor has different isValid with empty input
				between edit mode and non-edit mode so we'll exit edit mode for each editor before testing */
				providerWrapper.options.provider.refreshValue();
				this._fromExitEditing = true;
				providerWrapper.exitEditMode();
				provider = providerWrapper.options.provider;
				if (editor && editor.length && editor.is(":visible") &&
					(providerWrapper.hasInvalidMessage() ||
					!provider.requestValidate(evt) ||
					!provider.isValid())) {
					// if an invalid message is found or validation fails focus the editor and stop editing
					this._activateEditor(editor);
					this._disableDoneButton();
					return true;
				}
			}
			return false;
		},
		_fireEndingEvents: function (evt, prevValues, newValues, colElements, rowId, add, update, at) {
			var i, colKey, editor, rArg, cArg, mode = this.options.editMode, noCancel;
			if (mode !== "cell") {
				rArg = {
					owner: this,
					oldValues: prevValues,
					values: update.update ? newValues : prevValues,
					rowID: rowId,
					update: update.update, //if datasource will be updated
					rowAdding: add
				};
				if (at !== null && at !== undefined) {
					rArg = $.extend({ parentID: at }, rArg);
				}
				noCancel = this._trigger(this.events.editRowEnding, evt, rArg);
				/* the user can set update to false in order to stop editing of the data source
				editing will still be ended */
				if (update.update) {
					update.update = rArg.update;
				}
				if (!noCancel) {
					// if we cancel the editRowEnding we need to keep editing
					return false;
				}
			}
			/* dialog mode doesn't throw cell events */
			if (mode !== "dialog") {
				// now for every cell throw events
				for (i = 0; i < colElements.length; i++) {
					// for each cell we need to throw the cell event
					colKey = colElements[ i ].colKey;
					editor = this.editorForKey(colKey);
					cArg = {
						owner: this,
						rowID: rowId,
						columnIndex: this.grid.getVisibleIndexByKey(colKey),
						columnKey: colKey,
						editor: editor,
						value: newValues.hasOwnProperty(colKey) ? newValues[ colKey ] : prevValues[ colKey ],
						oldValue: prevValues[ colKey ],
						update: update.update && newValues[ colKey ] !== prevValues[ colKey ],
						rowAdding: add
					};
					noCancel = this._trigger(this.events.editCellEnding, evt, cArg);
					if (!noCancel && mode === "cell") {
						// canceling the -ing event only works in cell edit mode
						return false;
					}
					newValues[ colKey ] = cArg.value;
					/* if it would update the value but the user set it to false
					either revert it in the new values list or entirely cancel the update */
					if (mode === "cell" && !add) {
						update.update = cArg.update;
					} else {
						/* revert, later we'll have to check if there are still changes left and cancel
						the making of a transaction if there aren't any */
						if (!cArg.update) {
							newValues[ colKey ] = prevValues[ colKey ];
						}
					}
				}
			}
			return true;
		},
		_fireEndedEvents: function (evt, prevValues, newValues, colElements, rowId, add, update, at) {
			var i, colKey, editor, rArg, cArg, mode = this.options.editMode;
			if (mode !== "dialog") {
				for (i = 0; i < colElements.length; i++) {
					colKey = colElements[ i ].colKey;
					editor = this.editorForKey(colKey);
					cArg = {
						owner: this,
						rowID: rowId,
						columnIndex: this.grid.getVisibleIndexByKey(colKey),
						columnKey: colKey,
						editor: editor,
						value: newValues.hasOwnProperty(colKey) ? newValues[ colKey ] : prevValues[ colKey ],
						oldValue: prevValues[ colKey ],
						update: update.update && newValues[ colKey ] !== prevValues[ colKey ],
						rowAdding: add
					};
					this._trigger(this.events.editCellEnded, evt, cArg);
				}
			}
			if (mode !== "cell") {
				rArg = {
					owner: this,
					oldValues: prevValues,
					values: update.update ? newValues : prevValues,
					rowID: rowId,
					update: update.update,
					rowAdding: add
				};
				if (at !== null && at !== undefined) {
					rArg = $.extend({ parentID: at }, rArg);
				}
				this._trigger(this.events.editRowEnded, evt, rArg);
			}
		},
		_getNewValuesForRow: function (colElements) {
			var i, key, editor, provider, providerWrapper, newValues = {};
			for (i = 0; i < colElements.length; i++) {
				key = colElements[ i ].colKey;
				editor = this._providerForKey(key);
				providerWrapper = editor.data("igEditorFilter");
				provider = providerWrapper.options.provider;
				/* commenting out since post the exitEditMode this
				actually breaks date editors parsing - the value
				should be current anyway from the handleInvalid call
				provider.refreshValue(); */
				newValues[ key ] = provider.getValue();
			}
			return newValues;
		},
		_compareValues: function (prevValues, newValues) {
			var key, noChange = true;
			for (key in newValues) {
				if (newValues.hasOwnProperty(key)) {
					if ($.type(newValues[ key ]) === "date" && $.type(prevValues[ key ]) === "date") {
						noChange = noChange && newValues[ key ].getTime() === prevValues[ key ].getTime();
					} else {
						noChange = noChange && newValues[ key ] === prevValues[ key ];
					}
				}
			}
			return !noChange; // returns if changed
		},
		_closeEditingForRow: function (colElements) {
			var i, colKey, editor, provider, providerWrapper;
			for (i = 0; i < colElements.length; i++) {
				colKey = colElements[ i ].colKey;
				editor = this._providerForKey(colKey);
				providerWrapper = editor.data("igEditorFilter");
				provider = providerWrapper.options.provider;
				providerWrapper.remove();
				colElements[ i ].element.closest("td").removeClass(this.css.editingCell);
			}
		},
		/* editor-related private functions */
		_createEditor: function (cell, columnKey, element) {
			var vh = this._validationHandlers,
				colSetting = this._getColSettingsForCol(columnKey),
				column = this.grid.columnByKey(columnKey),
				provider = colSetting ? colSetting.editorProvider : null,
				editorOptions,
				validatorOptions,
				required,
				editorMargins,
				wrapper,
				elem,
				format = column.format;
			/* editor margins are only needed if the editor will reside in the cell */
			editorMargins = !element ? this._getEditorMargins(cell) : null;
			if (colSetting) {
				editorOptions = colSetting.editorOptions || {};
				validatorOptions = editorOptions.validatorOptions;
				required = colSetting.required;
				if (!validatorOptions && (colSetting.validation || required)) {
					validatorOptions = {};
				}
				if (required) {
					validatorOptions.required = true;
					editorOptions.required = true;
				}
			}
			if (!validatorOptions && this.options.validation) {
				validatorOptions = {};
				if (!editorOptions) {
					editorOptions = {};
				}
			}
			if (validatorOptions) {
				// setup validator if we have the validatorOptions object defined
				if (!this.grid.element.igValidator) {
					throw new Error($.ig.GridUpdating.locale.igValidatorException);
				}
				if (colSetting) {
					// if only validation is enabled we need to assign the validator options to the column settings
					colSetting.editorOptions = editorOptions;
				}
				editorOptions.validatorOptions = validatorOptions;
				validatorOptions.notificationOptions = validatorOptions.notificationOptions || {};
				if (!element) {
					// we'll only set popover when we don't have dialog
					validatorOptions.notificationOptions.mode = "popover";
					validatorOptions.notificationOptions.containment = this.grid.container();
					validatorOptions.notificationOptions.appendTo = this.grid.container();
				}
				if (validatorOptions.notificationOptions.mode === "popover" && element) {
					//if we have dialog and the notification mode is popover ensure that the dialog is the containment for the popover
					validatorOptions.notificationOptions.containment = $(element.context);
					validatorOptions.notificationOptions.appendTo = $(element.context);
				}
			}
			if (!provider) {
				provider = this._getProviderForKey(column, colSetting);
			}
			/* the editor element id is of the type <grid_id>_editorfor_<colKey> */
			elem = provider.createEditor(
				this._editorCallbacks,
				columnKey,
				colSetting ? colSetting.editorOptions || null : null,
				this._getNextTabIndex(),
				format,
				element
			);
			provider.attachErrorEvents(vh.errorShowing, vh.errorShown, vh.errorHidden);
			if (!element) {
				elem.addClass(this.css.editor)
					.css({
						marginLeft: editorMargins.x + "px",
						marginTop: editorMargins.y + "px"
					});
				elem.css("position", "absolute");
			}
			wrapper = elem.igEditorFilter({
				"provider": provider
			});
			return wrapper;
		},
		_getProviderForKey: function (column, setting) {
			var provider,
				dataType = column.dataType,
				format = column.format,
				editorType = setting ? setting.editorType : null,
				value, ds;
			if (editorType === "checkbox" || dataType === "bool") {
				provider = new $.ig.EditorProviderBoolean();
				/* use checkbox if the format of the column is checkbox or if the grid has
				rendercheckboxes enabled or if the user specified the editorType as checkbox */
				if (format === "checkbox" || (!format && this.grid.options.renderCheckboxes) ||
					editorType === "checkbox") {
					provider.renderFormat = "checkbox";
				} else {
					// drop down editor with true and false as values
					provider.renderFormat = "dropdown";
				}
			} else if (editorType === "combo" && dataType === "object") {
				provider = new $.ig.EditorProviderObjectCombo();
			} else if (editorType === "combo" && dataType !== "object") {
				provider = new $.ig.EditorProviderCombo();
			} else if (editorType === "rating") {
				provider = new $.ig.EditorProviderRating();
			} else if (editorType === "mask") {
				provider = new $.ig.EditorProviderMask();
				/*} else if (editorType === "html") {
				provider = new $.ig.EditorProviderHtml(); */
			} else if ((editorType || format) === "currency") {
				provider = new $.ig.EditorProviderCurrency();
			} else if ((editorType || format) === "percent") {
				provider = new $.ig.EditorProviderPercent();
			} else if (editorType === "numeric" || dataType === "number") {
				provider = new $.ig.EditorProviderNumeric();
			} else if (editorType === "text" || dataType === "string") {
				provider = new $.ig.EditorProviderText();
			} else if (editorType === "datepicker") {
				provider = new $.ig.EditorProviderDatePicker();
			} else if ((editorType || dataType) === "date") {
				provider = new $.ig.EditorProviderDate();
			} else {
				// determine the type by the value type of the first record's property value
				ds = this.grid.dataSource;
				if (ds && ds.data() && ds.data().length) {
					value = ds.data()[ 0 ][ column.key ];
					switch ($.type(value)) {
						case "number":
							return new $.ig.EditorProviderNumeric();
						case "string":
							return new $.ig.EditorProviderText();
						case "date":
							return new $.ig.EditorProviderDate();
						case "boolean":
							provider = new $.ig.EditorProviderBoolean();
							if (format === "checkbox" || (!format && this.grid.options.renderCheckboxes)) {
								provider.renderFormat = "checkbox";
							} else {
								// drop down editor with true and false as values
								provider.renderFormat = "dropdown";
							}
							return provider;
					}
				}
				throw new Error($.ig.GridUpdating.locale.editorTypeCannotBeDetermined + column.key);
			}
			return provider;
		},
		_getEditorKey: function (element) {
			var id = element.attr("id"), colKey, idx;
			if (id && id.length) {
				// the editor element id is of the type <grid_id>_editorfor_<colKey>
				idx = id.indexOf("_editorfor_");
				colKey = idx > 0 ? id.substring(idx + 10) : null;
			}
			if (!colKey) {
				colKey = this._getColumnKeyForCell(element.closest("td"));
			}
			return colKey;
		},
		_getEditorMargins: function (cell) {
			if (!cell || !cell.length) {
				return { x: 0, y: 0 };
			}
			/* measure attributes of editor class */
			if (!this._editorMargins) {
				var btw = parseInt(cell.css("borderTopWidth"), 10);
				var paddingTop = parseInt(cell.css("paddingTop"), 10);
				var blw = parseInt(cell.css("borderLeftWidth"), 10);
				var paddingLeft = parseInt(cell.css("paddingLeft"), 10);
				this._editorMargins = {
					x: -1 * (paddingLeft + blw),
					y: -1 * (paddingTop + btw)
				};
			}
			return this._editorMargins;
		},
		_getColSettingsForCol: function (colKey) {
			var i, settings = this.options.columnSettings;
			if (!settings) {
				return null;
			}
			for (i = 0; i < settings.length; i++) {
				if (settings[ i ].columnKey === colKey) {
					return settings[ i ];
				}
			}
		},
		_destroyAllEditors: function () {
			var key;
			for (key in this._editors) {
				if (this._editors.hasOwnProperty(key)) {
					if (this._editors[ key ].data("igEditorFilter")) {
						this._editors[ key ].igEditorFilter("destroy");
						this._editors[ key ].remove();
					}
				}
			}
			delete this._editors;
		},
		/* navigation functions */
		_navigateElement: function (evt, keyCode, row, rowId, columnKey, isAdd) {
			/* LEFT + RIGHT */
			if (keyCode === $.ui.keyCode.TAB ||
				(this.options.horizontalMoveOnEnter && keyCode === $.ui.keyCode.ENTER)) {
				if (evt.shiftKey) {
					return this._navigateLeft(evt, row, rowId, columnKey, isAdd);
				}
				return this._navigateRight(evt, row, rowId, columnKey, isAdd);
			}
			/* UP + DOWN OR COMMIT */
			if (keyCode === $.ui.keyCode.ENTER) {
				if (isAdd) {
					return this._endEdit(evt, true, false);
				}
				if (evt.shiftKey) {
					return this._navigateUp(evt, rowId, columnKey);
				}
				return this._navigateDown(evt, rowId, columnKey);
			}
			/* ESCAPE */
			if (keyCode === $.ui.keyCode.ESCAPE) {
				return this._stopEditing();
			}
			/* ARROW KEYS */
			if (this.options.excelNavigationMode) {
				switch (keyCode) {
					case $.ui.keyCode.LEFT:
						return this._navigateLeft(evt, row, rowId, columnKey, isAdd);
					case $.ui.keyCode.RIGHT:
						return this._navigateRight(evt, row, rowId, columnKey, isAdd);
					case $.ui.keyCode.DOWN:
						if (!isAdd) {
							return this._navigateDown(evt, rowId, columnKey);
						}
						break;
					case $.ui.keyCode.UP:
						if (!isAdd) {
							return this._navigateUp(evt, rowId, columnKey);
						}
						break;
				}
			}
		},
		_navigateLeft: function (evt, row, rowId, columnKey, isAdd) {
			// var cols = this.grid._visibleColumns(), nextCell, i, curRow;
			if (!isAdd && this.options.editMode === "cell") {
				return this._navigateLeftForCell(evt, rowId, columnKey);
			}
			return this._navigateLeftForRow(evt, row, rowId, columnKey, isAdd);
		},
		_navigateLeftForRow: function (evt, row, rowId, columnKey, isAdd) {
			var i, prevRow, prevCell, pairs = this._getEditedColumnElementsForRow(row), db,
				wrap = this.options.wrapAround &&
				!this.grid.options.virtualization &&
				!this.grid.options.rowVirtualization;
			for (i = 0; i < pairs.length; i++) {
				if (pairs[ i ].colKey === columnKey) {
					break;
				}
			}
			if (i === 0) {
				// first editable
				if (this.options.showDoneCancelButtons) {
					// focus the done/cancel container
					db = $("#" + this.grid.id() + "_updating_done");
					if (db.hasClass(this.css.buttonDisabled)) {
						db.siblings().first().focus();
					} else {
						db.focus();
					}
					return;
				}
				if (!this._endEdit(evt, true, false) || isAdd) {
					return;
				}
				row = this.grid.rowById(rowId);
				prevRow = this._nextEditableDataRow(row, "prev");
				if (!prevRow.length && wrap) {
					prevRow = this._lastEditableDataRow();
				}
				if (!prevRow.length) {
					return;
				}
				prevCell = this._getEditableCellsForRow(prevRow).last();
				this._startEditForRow(evt, false, this._getRowId(prevRow),
					this._getColumnKeyForCell(prevCell), prevCell);
				evt.preventDefault();
			} else if (this.grid.columnByKey(pairs[ i ].colKey).fixed !==
				this.grid.columnByKey(columnKey).fixed) {
				this._activateEditor(this._providerForKey(pairs[ i - 1 ].colKey));
			} else {
				this._scrollTo(pairs[ i - 1 ].element);
			}
		},
		_navigateLeftForCell: function (evt, rowId, columnKey) {
			// attempts to end edit for the current cell and finds the first element to the left of it
			// that is editable, starts edit for it (unless cancelled) in which case it stops
			var i, cols = this.grid._visibleColumns(), nextCell, curRow,
				wrap = this.options.wrapAround &&
				!this.grid.options.virtualization &&
				!this.grid.options.rowVirtualization;
			if (!this._endEdit(evt, true, false, true)) {
				return;
			}
			curRow = this.grid.rowById(rowId);
			for (i = 0; i < cols.length; i++) {
				if (cols[ i ].key === columnKey) {
					break;
				}
			}
			while (curRow.length) {
				rowId = this._getRowId(curRow);
				while (--i >= 0) {
					if (!cols[ i ].readOnly) {
						nextCell = this.grid.cellById(rowId, cols[ i ].key);
						this._startEditForCell(evt, false, rowId, cols[ i ].key, nextCell, true, false);
						evt.preventDefault();
						return;
					}
				}
				curRow = this._nextEditableDataRow(curRow, "prev");
				if (!curRow.length && wrap) {
					curRow = this._lastEditableDataRow();
				}
				/* reset i */
				i = cols.length;
			}
		},
		_navigateRight: function (evt, row, rowId, columnKey, isAdd) {
			// var cols = this.grid._visibleColumns(), nextCell, i, curRow;
			if (!isAdd && this.options.editMode === "cell") {
				return this._navigateRightForCell(evt, rowId, columnKey);
			}
			return this._navigateRightForRow(evt, row, rowId, columnKey, isAdd);
		},
		_navigateRightForCell: function (evt, rowId, columnKey) {
			// attempts to end edit for the current cell and finds the first element to the right of it
			// that is editable, starts edit for it (unless cancelled) in which case it stops
			var i, cols = this.grid._visibleColumns(), nextCell, curRow,
				wrap = this.options.wrapAround &&
				!this.grid.options.virtualization &&
				!this.grid.options.rowVirtualization;
			if (!this._endEdit(evt, true, false, true)) {
				return;
			}
			curRow = this.grid.rowById(rowId);
			for (i = 0; i < cols.length; i++) {
				if (cols[ i ].key === columnKey) {
					break;
				}
			}
			while (curRow.length) {
				rowId = this._getRowId(curRow);
				while (++i < cols.length) {
					if (!cols[ i ].readOnly) {
						nextCell = this.grid.cellById(rowId, cols[ i ].key);
						this._startEditForCell(evt, false, rowId, cols[ i ].key, nextCell, true, false);
						evt.preventDefault();
						return;
					}
				}
				curRow = this._nextEditableDataRow(curRow, "next");
				if (!curRow.length && wrap) {
					curRow = this._firstEditableDataRow();
				}
				/* reset i */
				i = -1;
			}
		},
		_navigateRightForRow: function (evt, row, rowId, columnKey, isAdd) {
			var i, nextRow, nextCell, pairs = this._getEditedColumnElementsForRow(row),
				wrap = this.options.wrapAround &&
				!this.grid.options.virtualization &&
				!this.grid.options.rowVirtualization;
			for (i = 0; i < pairs.length; i++) {
				if (pairs[ i ].colKey === columnKey) {
					break;
				}
			}
			if (i === pairs.length - 1) {
				// last editable
				if (this.options.showDoneCancelButtons) {
					return;
				}
				if (!this._endEdit(evt, true, false) || isAdd) {
					return;
				}
				row = this.grid.rowById(rowId);
				nextRow = this._nextEditableDataRow(row, "next");
				if (!nextRow.length && wrap) {
					nextRow = this._firstEditableDataRow();
				}
				if (!nextRow.length) {
					return;
				}
				nextCell = this._getEditableCellsForRow(nextRow).first();
				this._startEditForRow(evt, false, this._getRowId(nextRow),
					this._getColumnKeyForCell(nextCell), nextCell);
				evt.preventDefault();
			} else if (this.grid.columnByKey(pairs[ i ].colKey).fixed !==
				this.grid.columnByKey(columnKey).fixed) {
				/* need to activate */
				this._activateEditor(this._providerForKey(pairs[ i + 1 ].colKey));
			} else {
				this._scrollTo(pairs[ i + 1 ].element);
			}
		},
		_navigateUp: function (evt, rowId, columnKey) {
			// attempts to end edit for the current cell and finds the first element above it
			// that is editable, starts edit for it (unless cancelled) in which case it stops
			var nextCell, curRow, wrap;
			wrap = this.options.wrapAround &&
				!this.grid.options.virtualization &&
				!this.grid.options.rowVirtualization;
			evt.preventDefault();
			evt.stopPropagation();
			if (!this._endEdit(evt, true, false, true)) {
				return;
			}
			curRow = this._nextEditableDataRow(this.grid.rowById(rowId), "prev");
			if (!curRow.length && wrap) {
				curRow = this._lastEditableDataRow();
			}
			if (curRow.length) {
				rowId = this._getRowId(curRow);
				nextCell = this.grid.cellById(rowId, columnKey);
				if (this.options.editMode === "row") {
					this._startEditForRow(evt, false, rowId, columnKey, nextCell);
				} else {
					this._startEditForCell(evt, false, rowId, columnKey, nextCell, true, false);
				}
				evt.originalEvent.stopPropagation();
				evt.originalEvent.preventDefault();
			}
		},
		_navigateDown: function (evt, rowId, columnKey) {
			// attempts to end edit for the current cell and finds the first element below it
			// that is editable, starts edit for it (unless cancelled) in which case it stops
			var nextCell, curRow, wrap;
			wrap = this.options.wrapAround &&
				!this.grid.options.virtualization &&
				!this.grid.options.rowVirtualization;
			evt.preventDefault();
			evt.stopPropagation();
			if (!this._endEdit(evt, true, false, true)) {
				return;
			}
			curRow = this._nextEditableDataRow(this.grid.rowById(rowId), "next");
			if (!curRow.length && wrap) {
				curRow = this._firstEditableDataRow();
			}
			if (curRow.length) {
				rowId = this._getRowId(curRow);
				nextCell = this.grid.cellById(rowId, columnKey);
				if (this.options.editMode === "row") {
					this._startEditForRow(evt, false, rowId, columnKey, nextCell);
				} else {
					this._startEditForCell(evt, false, rowId, columnKey, nextCell, true, false);
				}
				evt.originalEvent.stopPropagation();
				evt.originalEvent.preventDefault();
			}
		},
		_nextEditableDataRow: function (row, command) {
			var go = this.grid.options, vVirt = go.virtualization || go.rowVirtualization, cIdx,
				query = ":not([data-container],[data-grouprow],.ui-iggrid-deletedrecord):visible:first";
			if (vVirt) {
				if (go.virtualizationMode === "fixed") {
					cIdx = row.index();
					if (cIdx >= this.grid._virtualRowCount - 1 && command === "next") {
						this._scrollVmanual(true);
						return this._lastEditableDataRow();
					}
					if (cIdx <= 0 && command === "prev") {
						this._scrollVmanual(false);
						return this._firstEditableDataRow();
					}
				}
			}
			/* editable rows are not deleted, not group rows and not
			hierarhical container rows and must be visible (treegrid) */
			row = row[ command + "All" ](query);
			if (vVirt && go.virtualizationMode === "continuous") {
				this._setScroll(command === "next" ? $.ui.keyCode.DOWN : $.ui.keyCode.UP, row);
			}
			return row;
		},
		_firstEditableDataRow: function () {
			return this.element.children("tbody")
				.children("tr:not([data-grouprow],.ui-iggrid-deletedrecord):visible:first");
		},
		_lastEditableDataRow: function () {
			return this.element.children("tbody")
				.children("tr:not([data-container],.ui-iggrid-deletedrecord):visible:last");
		},
		/* other */
		_updateUnboundValuesForRow: function (row) {
			// row could be primary key value or row object(that should be updated)
			// update unbound values for columns that have formula set and editModeUnboundValues is auto or undefined
			if (!this.grid._hasUnboundColumns) {
				return;
			}
			var i, uc = this.grid._unboundColumns, col, f, rec;
			if (!uc || !uc.length) {
				return;
			}
			if ($.type(row) === "object") {
				rec = row;
			}
			for (i = 0; i < uc.length; i++) {
				col = uc[ i ];
				if (col && col.editModeUnboundValues !== "manual" && col.formula) {
					if (col.formula) {
						f = this.grid._getUnboundColumnFormula(col);
						if (f) {
							if (!rec) {
								rec = this.grid.findRecordByKey(row);
							}
							if (!rec) {
								return;
							}
							rec[ col.key ] = f.apply(col, [ rec, this.grid.element ]);
						}
					}
				}
			}
		},
		_isEditableRow: function (row) {
			return row &&
				row.is("tr") &&
				!row.hasClass(this.grid.css.deletedRecord) &&
				!row.attr("data-grouprow") &&
				(row.attr("data-id") !== undefined || row.attr("data-add-row") !== undefined);
			/* can add is selected here for selection */
		},
		_isEditableCell: function (cell) {
			return this._isDataCell(cell) &&
				cell.attr("aria-readonly") !== "true" &&
				this._isEditableRow(cell.parent());
			/* can add is selected here for selection */
		},
		_isDataCell: function (cell) {
			return cell && cell.is("td") && !cell.attr("data-skip") && !cell.attr("data-parent");
		},
		_isLastScrollableCell: function (cell) {
			return cell && cell.is(":last-child") &&
				(parseInt(cell.css("padding-right"), 10) > 12 ||
				(this.grid._hscrollbar().is(":visible") && this.grid._hasVerticalScrollbar)) &&
				this.grid.scrollContainer() && this.grid.scrollContainer().has(cell).length;
		},
		_selectionToggle: function (element, dontToggle) {
			var sel = this.grid.element.data("igGridSelection");
			if (!sel) {
				return;
			}
			sel._suspend = !sel._suspend;
			if (element || dontToggle) {
				this._actElement = element;
			} else {
				this._actElement.focus();
			}
		},
		_showDoneCancelButtons: function () {
			var container = this._findElementInScrollContainer(".ui-iggrid-buttoncontainer:first");
			if (!container.length) {
				container = this._renderDoneCancelButtons();
			}
			container.show();
		},
		_hideDoneCancelButtons: function () {
			var container = this._findElementInScrollContainer(".ui-iggrid-buttoncontainer:first");
			if (container.length) {
				container.hide();
			}
		},
		_positionDoneCancelButtons: function (row) {
			var container = this._findElementInScrollContainer(".ui-iggrid-buttoncontainer:first"),
				sbw = this.grid._hasVerticalScrollbar === true ? this.grid._scrollbarWidth() : 0,
				left, top, sc, contHeight, scHeight, rowPos, go = this.grid.options, v,
				hasHeight = go.height !== null && go.height !== undefined, scrContainer;
			row = this._combineRowElements(row);
			sc = container.parent();
			left = sc.outerWidth() - container.outerWidth() - sbw + sc.scrollLeft();
			if (!sc.has(row).length && sc[ 0 ] !== this.grid.container()[ 0 ]) {
				// when positioning for the add row and there is height set
				// top should just be the scroll top of the scroll cont
				top = sc.scrollTop();
			} else {
				contHeight = container.outerHeight();
				scHeight = sc.outerHeight();
				rowPos = row.position();
				top = rowPos.top + row.outerHeight();
				if (top + contHeight > scHeight) {
					top = rowPos.top - contHeight;
				}
				if (!hasHeight) {
					v = go.virtualization || go.rowVirtualization || go.columnVirtualization;
					scrContainer = v ? this.grid._vdisplaycontainer() : this.grid.scrollContainer();
					if (scrContainer.length) {
						top += scrContainer.position().top;
					}
				}
				top += sc.scrollTop();
			}
			container.css({
				top: top,
				left: left
			});
		},
		_disableDoneButton: function () {
			if (this.options.editMode === "dialog") {
				$("#" + this.grid.id() + "_updating_dialog_container")
					.igGridModalDialog("option", "buttonApplyDisabled", true);
			} else {
				$("#" + this.grid.id() + "_updating_done")
					.addClass(this.css.buttonDisabled)
					.removeClass(this.css.buttonActive)
					.attr("tabIndex", -1);
			}
		},
		_enableDoneButton: function () {
			if (this.options.editMode === "dialog") {
				$("#" + this.grid.id() + "_updating_dialog_container")
					.igGridModalDialog("option", "buttonApplyDisabled", false);
			} else {
				$("#" + this.grid.id() + "_updating_done")
					.removeClass(this.css.buttonDisabled)
					.attr("tabIndex", this._getNextTabIndex() + 1);
			}
		},
		_updateTouchButtons: function (isAdding) {
			var button = $("#" + this.grid.id() + "_updating_delete_touch");
			if (button.length) {
				if (isAdding || !this.options.enableDeleteRow) {
					button.addClass(this.css.buttonDisabled);
				} else {
					button.removeClass(this.css.buttonDisabled);
				}
			}
		},
		showDeleteButtonFor: function (row) {
			/* Shows the delete button for specific row.
			paramType="object" A jQuery object of the targeted row.
			*/
			var db = $("#" + this.grid.id() + "_updating_deletehover"),
				go = this.grid.options, v, hasHeight = go.height !== null && go.height !== undefined,
				sbw = this.grid.hasVerticalScrollbar() === true ? this.grid._scrollbarWidth() : 0,
				left, top, sc, offset = 0, scrContainer, totalHeight,
				rowGrp = row.siblings("[data-id='" + row.attr("data-id") + "']").add(row);
			if (db.length) {
				db.show();
				sc = db.parent();
				if (!hasHeight) {
					// when the button and the row's table are in different containers
					// we need to add position top of the row's container
					v = go.virtualization || go.rowVirtualization || go.columnVirtualization;
					scrContainer = v ? this.grid._vdisplaycontainer() : this.grid.scrollContainer();
					if (scrContainer.length) {
						offset = scrContainer.position().top;
					}
				}
				totalHeight = rowGrp.last().position().top + rowGrp.last().outerHeight() -
					rowGrp.first().position().top;
				left = sc.outerWidth() - db.outerWidth() - sbw + sc.scrollLeft() - 5 /* indentation from right edge */;
				top = rowGrp.first().position().top + totalHeight / 2 - db.outerHeight() / 2 +
					sc.scrollTop() + offset;
				db.css({
					top: top,
					left: left
				});
				/* save the context of the button */
				db.data("button-for", this._getRowId(row));
			}
		},
		hideDeleteButton: function () {
			/* Hides the delete button.
			*/
			var db = $("#" + this.grid.id() + "_updating_deletehover");
			if (db.length) {
				db.hide();
			}
		},
		_getNextTabIndex: function () {
			var gti = this.grid.options.tabIndex;
			return gti + 1;
		},
		_getRowId: function (element) {
			return this.grid._fixPKValue(element.attr("data-id"));
		},
		_getEditableElementsForRow: function (row) {
			var result = [], i, cells = this._getEditableCellsForRow(row),
				editableCols = $.grep(this.grid._visibleColumns(), function (col) { return !col.readOnly; }),
				vcc = this.grid.hasFixedColumns() ?
					this.grid._visibleColumns().length :
					(this.grid._virtualColumnCount || this.grid._visibleColumns().length),
				vci = this.grid._startColIndex || 0;
			for (i = 0; i < editableCols.length; i++) {
				result.push({
					colKey: editableCols[ i ].key,
					element: i >= vci && i < vci + vcc ? cells.eq(i - vci) : null
				});
			}
			return result;
		},
		_getEditedColumnElementsForRow: function (row) {
			var result = [], cells, i;
			cells = this._getEditableCellsForRow(row).filter("." + this.css.editingCell);
			for (i = 0; i < cells.length; i++) {
				result.push({
					colKey: this._getColumnKeyForCell(cells.eq(i)),
					element: cells.eq(i)
				});
			}
			return result;
		},
		_getEditedColumnElementsForDialog: function (content) {
			var result = [], cols = this.grid.options.columns, i, key, element;
			for (i = 0; i < cols.length; i++) {
				key = cols[ i ].key;
				element = content.find("[data-editor-for-" + key.toLowerCase() + "]");
				if (element.length === 1) {
					result.push({ colKey: key, element: element });
				}
			}
			return result;
		},
		_getEditableCellsForRow: function (row) {
			return this._combineRowElements(row)
				.children("td[aria-readonly='false']:not([data-skip='true'],[data-parent])");
		},
		_combineRowElements: function (row) {
			var frow, urow, fixingDir = this.grid.fixingDirection(),
				type = row.attr("data-new-row") ? "[data-new-row]" : "[data-add-row]";
			/* returns a jquery object containing the fixed/unfixed counterpart of the provided row element */
			if (this.grid.hasFixedColumns()) {
				// we should add the row's fixed or unfixed portion too
				if (this.grid._isFixedElement(row)) {
					frow = row;
					urow = row.parent().is("thead") ?
						this.grid.headersTable().children("thead").children("tr" + type) :
						this._getRowByIndex(this._getIndexForRow(row), false);
				} else {
					urow = row;
					frow = row.parent().is("thead") ?
						this.grid.fixedHeadersTable().children("thead").children("tr" + type) :
						this._getRowByIndex(this._getIndexForRow(row), true);
				}
				return fixingDir === "left" ? $([ frow[ 0 ], urow[ 0 ] ]) : $([ urow[ 0 ], frow[ 0 ] ]);
			}
			return row;
		},
		_getVisibleIndexForKey: function (columnKey) {
			var vc = this.grid._visibleColumns(), i;
			for (i = 0; i < vc.length; i++) {
				if (vc[ i ].key === columnKey) {
					return i;
				}
			}
			return null;
		},
		_getRowByIndex: function (index, fixed) {
			var tbody = fixed ?
				this.grid.fixedBodyContainer().find("tbody") :
				this.grid.element.find("tbody");
			return tbody.children("tr:not([data-container])").eq(index - (this.grid._startRowIndex || 0));
		},
		_getIndexForRow: function (row) {
			return row.closest("tbody").children("tr:not([data-container])").index(row) +
				/* add virtualization start */
				(this.grid._startRowIndex || 0);
		},
		_getColumnKeyForCell: function (cell) {
			var col = this.grid.getColumnByTD(cell);
			return col ? col.column.key : null;
		},
		_analyzeEditTriggers: function () {
			var trg = this.options.startEditTriggers, key;
			this._editTriggers = {
				click: false,
				dblclick: false,
				f2: false,
				enter: false
			};
			for (key in this._editTriggers) {
				if (this._editTriggers.hasOwnProperty(key)) {
					if ($.type(trg) === "array") {
						this._editTriggers[ key ] = $.inArray(key, trg);
					} else {
						this._editTriggers[ key ] = trg.toLowerCase().indexOf(key) >= 0;
					}
				}
			}
		},
		_rebindEditTriggers: function () {
			var selector = "#" + this.grid.id() + ">tbody>tr>td,#" +
				this.grid.id() + "_fixed>tbody>tr>td", hId, scr;
			if (this.options.enableAddRow && this.grid.options.showHeader) {
				hId = this.grid.headersTable().attr("id");
				selector += ",#" + hId + ">thead>tr.ui-iggrid-addrow,#" +
					hId + "_fixed>thead>tr.ui-iggrid-addrow";
			}
			this.grid.container().off(".triggers");
			this.grid.container().on({
				"mousedown.triggers": this._handlers.mouseDown,
				"touchstart.triggers": this._handlers.mouseDown
			}, selector);
			if (this._editTriggers.dblclick) {
				this.grid.container().on({
					"dblclick.triggers": this._handlers.clickTrigger
				}, selector);
			} else if (this._editTriggers.click) {
				this.grid.container().on({
					"click.triggers": this._handlers.clickTrigger
				}, selector);
			}
			this.grid.container().on({
				"keydown.triggers": this._handlers.keyDown
			}, selector.replace(/>td/g, ""));
			/* for deleterow */
			if (this.options.enableDeleteRow) {
				this._renderDeleteButton();
			}
			/* bind to scroll */
			scr = this._addElementToScrollContainer($());
			if (scr && scr.length && scr[ 0 ] !== this.grid.container()[ 0 ]) {
				scr
					.unbind("scroll", this._handlers.scroll)
					.bind("scroll", this._handlers.scroll);
			}
		},
		_getLatestValues: function (id, columnKey) {
			// returns the latest values for the specified record or propery
			// if the record is not found in the data source or pending transactions
			// an exception should be thrown - possible dataSource/view mismatch or wrongly
			// executed startEdit by the user
			var original, record, transactions, i, notNew = false;
			original = this.grid.dataSource.findRecordByKey(id);
			record = jQuery.extend(true, {}, original);
			transactions = this.grid.dataSource.pendingTransactions();
			for (i = transactions.length - 1; i >= 0; i--) {
				if (transactions[ i ].rowId === id) {
					switch (transactions[ i ].type) {
						case "row":
						case "newrow":
						/* P.Zh. 23 Feb 2016 Fix for bug #214551: When autoCommit is
						false and a new child is added, column moving causes data loss */
						case "insertnode":
							jQuery.extend(true, record, transactions[ i ].row);
							break;
						case "cell":
							record[ transactions[ i ].col ] = transactions[ i ].value;
							break;
						case "deleterow":
							record = null;
							break;
					}
					notNew = true;
				}
			}
			if (original || notNew) {
				// return the latest record values
				return $.type(columnKey) === "string" ? record[ columnKey ] : record;
			}
			/* the record was not found in the data source or as part of a transaction
			therefore we need to throw an exception */
			throw new Error($.ig.GridUpdating.locale.recordOrPropertyNotFoundException);
		},
		_getDefaultValues: function (columnKey) {
			var defVals = this._defaultValues, allValues,
				settings = this.options.columnSettings,
				i, gpkArgs;
			if (!defVals) {
				defVals = {};
				for (i = 0; i < settings.length; i++) {
					if (settings[ i ].defaultValue !== undefined && $.type(settings[ i ].columnKey) === "string") {
						defVals[ settings[ i ].columnKey ] = settings[ i ].defaultValue;
					}
				}
				this._defaultValues = defVals;
			}
			allValues = jQuery.extend(true, {}, defVals);
			if ((!columnKey || columnKey === this.grid.options.primaryKey) &&
				!(defVals.hasOwnProperty(columnKey))) {
				// we need to generate the PK
				gpkArgs = {
					owner: this
				};
				if (this._getPKType() === "string") {
					gpkArgs.value = String(this._pkVal);
				} else {
					gpkArgs.value = this._pkVal;
				}
				this._trigger(this.events.generatePrimaryKeyValue, null, gpkArgs);
				allValues[ this.grid.options.primaryKey ] = gpkArgs.value;
			}
			return columnKey ? allValues[ columnKey ] : allValues;
		},
		_getPKType: function () {
			// evaluates the type of the pk and stores it
			var pk = this.grid.options.primaryKey, pkType,
				pkColumn, data = this.grid.dataSource._data;
			if (!this._pkt) {
				if (!pk) {
					throw new Error($.ig.GridUpdating.locale.noPrimaryKeyException);
				}
				pkColumn = this.grid.columnByKey(pk);
				if (pkColumn && pkColumn.dataType) {
					pkType = pkColumn.dataType;
				} else {
					pkType = data && data.length > 0 && data[ 0 ][ pk ] !== null && data[ 0 ][ pk ] !== undefined ?
						$.type(data[ 0 ][ pk ]) : null;
				}
				this._pkt = pkType;
			}
			return this._pkt;
		},
		_updateUIForTransaction: function (t, element) {
			var go = this.grid.options,
				autoCommit = go.autoCommit,
				v = go.virtualization || go.rowVirtualization || go.columnVirtualization,
				fv = go.virtualizationMode === "fixed",
				row, vals, pk, grp, idx, nextRow;
			switch (t.type) {
				case "newrow":
					/* if the row to be rendered is already found do nothing */
					if (this.grid.element.children("tbody")
						.children("tr." + this.grid.css.modifiedRecord + "[data-id='" + t.rowId + "']").length) {
						return;
					}
					grp = this.grid.element.children("tbody").children("tr.ui-iggrid-groupedrow").length > 0;
					/* M.H. 22 July 2015 Fix for bug 202165: Unbound column value does not
					update when updating one of the other values in the dependency */
					this._updateUnboundValuesForRow(t.row);
					if (v && !autoCommit) {
						break;
					}
					if (grp) {
						this.grid.element.data("igGridGroupBy")._renderNewRow(t.row, t.rowId);
					} else {
						this.grid.renderNewRow(t.row, t.rowId);
					}
					this.grid._updateGridContentWidth();
					row = this.grid.rowById(t.rowId);
					if (!autoCommit && row.length) {
						this._combineRowElements(row).addClass(this.grid.css.modifiedRecord);
					}
					break;
				case "cell":
					pk = autoCommit && t.col === go.primaryKey ? t.value : t.rowId;
					row = element ? element.parent() : this.grid.rowById(t.rowId);
					vals = this._getLatestValues(pk);
					vals[ t.col ] = t.value;
					/* M.H. 22 July 2015 Fix for bug 202165: Unbound column value does not
					update when updating one of the other values in the dependency */
					this._updateUnboundValuesForRow(vals);
					if (!row || !row.length) {
						break;
					}
					this.grid._renderRow(vals, row);
					if (!autoCommit) {
						this._combineRowElements(row).addClass(this.grid.css.modifiedRecord);
					} else {
						// change the row's data-id attribute
						this._combineRowElements(row).attr("data-id", pk).data("id", pk);
					}
					break;
				case "row":
					pk = autoCommit && t.row.hasOwnProperty(go.primaryKey) ? t.row[ go.primaryKey ] : t.rowId;
					row = element || this.grid.rowById(pk);
					vals = $.extend({}, this._getLatestValues(pk), t.row);
					/* M.H. 22 July 2015 Fix for bug 202165: Unbound column value does not
					update when updating one of the other values in the dependency */
					this._updateUnboundValuesForRow(vals);
					if (!row || !row.length) {
						break;
					}
					this.grid._renderRow(vals, row);
					if (!autoCommit) {
						this._combineRowElements(row).addClass(this.grid.css.modifiedRecord);
					} else {
						// change the row's data-id attribute
						this._combineRowElements(row).attr("data-id", pk).data("id", pk);
					}
					break;
				case "deleterow":
					row = element || this.grid.rowById(t.rowId);
					if (autoCommit && v) {
						this._deleteRowVirtualization(row, fv);
						this._notifyRowDeleted(t.rowId, row);
						break;
					}
					if (!row || !row.length) {
						break;
					}
					if (autoCommit) { // non-virtualization
						idx = row.index();
						row = this._combineRowElements(row);
						nextRow = row.next("tr[data-container='true']");
						if (nextRow.length === 1) {
							nextRow.remove();
						}
						row.remove();
						this.grid._reapplyZebraStyle(idx);
						this.grid._updateGridContentWidth();
						this._notifyRowDeleted(t.rowId, row);
					} else {
						this._combineRowElements(row).addClass(this.grid.css.deletedRecord);
					}
					break;
			}
			return row;
		},
		_processReadOnly: function () {
			var i, col, colSettings = this.options.columnSettings;
			if (colSettings) {
				for (i = 0; i < colSettings.length; i++) {
					col = this.grid.columnByKey(colSettings[ i ].columnKey);
					if (col && col.readOnly !== colSettings[ i ].readOnly) {
						col.readOnly = colSettings[ i ].readOnly;
					}
				}
			}
		},
		_isMultiLineText: function (key) {
			var cs = this._getColSettingsForCol(key);
			if (cs && cs.editorOptions) {
				return cs.editorOptions.textMode === "multiline";
			}
			return false;
		},
		_anyEditorInvalid: function () {
			var key, all = this._editors, provider;
			for (key in all) {
				if (all.hasOwnProperty(key) && all[ key ]) {
					provider = all[ key ].data("igEditorFilter").options.provider;
					provider.refreshValue();
					if (!all[ key ].data("igEditorFilter").options.provider.isValid()) {
						return true;
					}
				}
			}
			return false;
		},
		_revertValueForEditor: function (provider, columnKey) {
			var oval, nval, ac, hf;
			/* refresh the value first */
			provider.refreshValue();
			nval = provider.getValue();
			oval = this._originalValues[ columnKey ];
			hf = $.type(provider.editor.field) === "function";
			if (hf) {
				ac = provider.editor.field().attr("autocomplete");
				provider.editor.field().attr("autocomplete", "off");
			}
			if ($.type(nval) === "date" && $.type(oval) === "date") {
				if (nval.getTime() !== oval.getTime()) {
					provider.setValue(new Date(oval.getTime()), true);
					if (hf) {
						if (ac) {
							provider.editor.field().removeAttr("autocomplete");
						} else {
							provider.editor.field().attr("autocomplete", ac);
						}
					}
					return true;
				}
			} else if (oval !== nval) {
				provider.setValue(oval, true);
				if (hf) {
					if (ac) {
						provider.editor.field().removeAttr("autocomplete");
					} else {
						provider.editor.field().attr("autocomplete", ac);
					}
				}
				return true;
			}
			if (hf) {
				if (ac) {
					provider.editor.field().removeAttr("autocomplete");
				} else {
					provider.editor.field().attr("autocomplete", ac);
				}
			}
			return false;
		},
		_hideValidatorMessages: function () {
			var key, e = this._editors, wrapper, validator;
			for (key in e) {
				if (e.hasOwnProperty(key)) {
					wrapper = this._providerForKey(key).data("igEditorFilter");
					if (wrapper && wrapper.hasInvalidMessage()) {
						validator = wrapper.options.provider.validator();
						if (validator) {
							validator.hide();
						}
					}
				}
			}
		},
		_chooseColumnToFocus: function (isAdding, initialKey) {
			var defaultIndex, i, columnToFocus,
				visibleCols = this.grid._visibleColumns(),
				vcc = this.grid._virtualColumnCount || visibleCols.length,
				vci = this.grid._startColIndex || 0;
			defaultIndex = isAdding ? 0 : this._getVisibleIndexForKey(initialKey);
			defaultIndex = Math.min(Math.max(vci, defaultIndex), vci + vcc - 1);
			if (!initialKey) {
				defaultIndex--;
			}
			if (initialKey && !this.grid.columnByKey(initialKey).readOnly) {
				columnToFocus = initialKey;
			} else {
				i = defaultIndex + 1 < visibleCols.length ? defaultIndex + 1 : vci;
				while (visibleCols[ i ].key !== initialKey) {
					if (!visibleCols[ i ].readOnly) {
						columnToFocus = visibleCols[ i ].key;
						break;
					}
					if (++i === vci + vcc) {
						i = vci;
					}
				}
			}
			return columnToFocus;
		},
		_addDSSuccessHandler: function () {
			// remove the existing save changes success handler from the data source (if any) and after that adds saveChangesSuccessHandler
			var fS, grid = this.grid;
			if (this._addChangesSuccessHandler !== null && this._addChangesSuccessHandler !== undefined) {
				grid.dataSource._removeChangesSuccessHandler(this._addChangesSuccessHandler);
			}
			/* M.H. 17 Feb 2014 Fix for bug #153639: Add successCallback and
			errorCallback params to the igDataSource.saveChanges API */
			fS = this.options.saveChangesSuccessHandler;
			if (fS) {
				if ($.type(fS) === "string" && window[ fS ] && $.type(window[ fS ]) === "function") {
					fS = window[ fS ];
				}
			}
			if ($.type(fS) !== "function") {
				fS = null;
			}
			this._addChangesSuccessHandler = function (data) {
				// if there are deleted rows, then whole grid should be repainted, because of zebra styles
				if (grid.rows().parent().find("." + grid.css.deletedRecord).length) {
					grid._renderData();
				}
				grid.rows().removeClass(grid.css.modifiedRecord);
				if (fS) {
					fS(data);
				}
			};
			/* DAY 18 OCT 2011 90177- When call saveChanges the rows should not be marked as dirty */
			grid.dataSource._addChangesSuccessHandler(this._addChangesSuccessHandler);
		},
		_addDSErrorHandler: function () {
			// remove the existing save changes error handler from the data source (if any) and after that adds saveChangesErrorHandler if defined in options
			var fE;
			if (this._addChangesErrorHandler !== null && this._addChangesErrorHandler !== undefined) {
				this.grid.dataSource._removeChangesErrorHandler(this._addChangesErrorHandler);
			}
			/* M.H. 17 Feb 2014 Fix for bug #153639: Add successCallback and
			errorCallback params to the igDataSource.saveChanges API */
			if (this.options.saveChangesErrorHandler) {
				fE = this.options.saveChangesErrorHandler;
				if ($.type(fE) === "string" && window[ fE ] && $.type(window[ fE ]) === "function") {
					fE = window[ fE ];
				}
				if ($.type(fE) === "function") {
					this._addChangesErrorHandler = function (jqXHR, textStatus, errorThrown) {
						fE(jqXHR, textStatus, errorThrown);
					};
					this.grid.dataSource._addChangesErrorHandler(this._addChangesErrorHandler);
				}
			}
		},
		_resolveRowElement: function (element, rowId) {
			if (element) {
				if (element.is("tr")) {
					return element;
				}
				if (element.is("td")) {
					return element.parent();
				}
			}
			return this.grid.rowById(rowId);
		},
		/* virtualization handlers for updateUIForTransaction */
		_deleteRowVirtualization: function (row, fixed) {
			var svst;
			if (fixed) {
				if (this.grid._startRowIndex + this.grid._virtualRowCount === this.grid._totalRowCount) {
					this.grid._startRowIndex = Math.max(0, this.grid._startRowIndex - 1);
				}
				this.grid._totalRowCount--;
				if (this.grid._virtualRowCount > this.grid._totalRowCount) {
					// we need to call pre and post render manually as buildvirtualdom doesn't throw events
					this._virtPreRender(null, { owner: this.grid });
					this.grid._buildVirtualDom();
					this._virtPostRender(null, { owner: this.grid });
				} else {
					this.grid._renderVirtualRecords();
					this.grid._setScrollContainerHeight(
						this.grid._totalRowCount * parseInt(this.grid.options.avgRowHeight, 10)
					);
				}
			} else {
				svst = this.grid._persistVirtualScrollTop;
				this.grid._persistVirtualScrollTop = true;
				this.grid._saveFirstVisibleTRIndex();
				/* grid._renderVirtualRecordsContinuous();
				calling the wrapper function will still recognize the virt mode
				and call the one commented out, however also notifying features
				about the rerendering. */
				this.grid._renderVirtualRecords();
				this.grid._persistVirtualScrollTop = svst;
			}
		},
		_childrenWithAddRowEnabled: function (opts, inheritedRule) {
			var i, j, cl, clr;
			if (opts.columnLayouts) {
				for (i = 0; i < opts.columnLayouts.length; i++) {
					cl = opts.columnLayouts[ i ];
					if (inheritedRule !== null && inheritedRule !== undefined) {
						clr = inheritedRule;
					} else {
						clr = $.ui.igGridUpdating.prototype.options.enableAddRow;
					}
					if (cl.features && cl.features.length > 0) {
						for (j = 0; j < cl.features.length; j++) {
							if (cl.features[ j ].name === "Updating") {
								return cl.features[ j ].enableAddRow === null ||
									cl.features[ j ].enableAddRow === undefined ?
									clr : cl.features[ j ].enableAddRow;
							}
						}
					} else {
						return inheritedRule === true;
					}
				}
			}
			return false;
		},
		_recOrPropFound: function (rowId, colKey) {
			var pendingTransactions = this.grid.dataSource.pendingTransactions(),
				rec, i = pendingTransactions.length, found;
			rec = this.grid.dataSource.findRecordByKey(rowId);
			found = !!rec;
			if (colKey) {
				found = found && rec.hasOwnProperty(colKey);
			}
			if (!found) {
				while (--i >= 0) {
					if (pendingTransactions[ i ].rowId === rowId) {
						return colKey ? pendingTransactions[ i ].row.hasOwnProperty(colKey) : true;
					}
				}
				return false;
			}
			return true;
		},
		/* navigational tools */
		_activateEditor: function (editor) {
			// basically get offset of td, change scrolltop/scrollleft, (adjust for error message) and focus
			var cell = editor.closest("td");
			/* navigate to cell */
			if (cell.length && this.options.editMode !== "dialog") {
				this._scrollTo(cell);
			}
			/* focus editor */
			editor.igEditorFilter("setFocus");
			setTimeout(function () {
				if (editor.data("igEditorFilter")) {
					editor.igEditorFilter("setFocus");
				}
			}, 5);
		},
		_scrollTo: function (cell) {
			var scrollContainer = this.grid.scrollContainer(),
				/* need virtualization handling of vdisplaycontainer */
				cellPosition, ct, cl, paddingRight;
			if (scrollContainer.length && scrollContainer.has(cell).length) {
				/* we might need to adjust the scroll to make the whole cell visible */
				cellPosition = cell.position();
				paddingRight = this.grid.element
					.find("tbody > tr:first > :last-child").attr("data-vscr-padding-icrement");
				paddingRight = paddingRight ? parseInt(paddingRight, 10) : 0;
				ct = cellPosition.top + scrollContainer.scrollTop();
				cl = cellPosition.left + scrollContainer.scrollLeft();
				if (cellPosition.left < 0) {
					/* cell is left of the view */
					scrollContainer.scrollLeft(cl);
				} else if (cl + cell.outerWidth() + paddingRight >
					scrollContainer.scrollLeft() + scrollContainer.outerWidth()) {
					/* cell is right of the view */
					scrollContainer.scrollLeft(cl + cell.outerWidth() +
						paddingRight - scrollContainer.outerWidth());
				}
				if (cellPosition.top < 0) {
					/* cell is above the view */
					scrollContainer.scrollTop(ct);
				} else if (ct + cell.outerHeight() >
					scrollContainer.scrollTop() + scrollContainer.outerHeight()) {
					/* cell is below the view */
					scrollContainer.scrollTop(ct + cell.outerHeight() - scrollContainer.outerHeight());
				}
				/* ADD ERROR MESSAGE HANDLING
				recalc position so that it can be scrolled to with the error message in view */
			}
		},
		_setScroll: function (code, nextActiveElement) {
			// When cells / rows can be focused, we'll leave the browser to handle
			// the vertical scroll position. We only need to update the horizontal one for better ux
			// and the vertical when there is continuous virtualization because the browser won't update the virtual scrollbar
			var scrollVDir = code === $.ui.keyCode.DOWN || code === $.ui.keyCode.RIGHT ? "down" : "up";
			this._setScrollTop(this.grid.element.parent(), nextActiveElement.closest("tr"),
				scrollVDir, nextActiveElement.closest("tr").index() + (this.grid._startRowIndex || 0));
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
			this._vscrolled = true;
		},
		/* private command methods */
		_deleteRow: function (evt, rowId, suppress) {
			var arg, t, autoCommit = this.grid.options.autoCommit,
				row = this.grid.rowById(rowId);
			/* don't delete a row twice */
			if (row.hasClass(this.grid.css.deletedRecord)) {
				return;
			}
			arg = {
				owner: this,
				element: row,
				rowID: rowId
			};
			if (!suppress) {
				if (!this._trigger(this.events.rowDeleting, evt, arg)) {
					return false;
				}
			}
			t = this.grid.dataSource.deleteRow(rowId, autoCommit);
			row = this._updateUIForTransaction(t, row);
			if (!suppress) {
				this._trigger(this.events.rowDeleted, evt, arg);
			}
			return true;
		},
		_deleteMultipleRows: function (evt) {
			var selectedRows = this.grid.element.igGridSelection("selectedRows"), i;
			for (i = 0; i < selectedRows.length; i++) {
				if (selectedRows[ i ] && selectedRows[ i ].hasOwnProperty("id") &&
					!selectedRows[ i ].element.hasClass(this.grid.css.deletedRecord)) {
					this._deleteRow(evt, selectedRows[ i ].id, false);
				}
			}
		},
		_updateRow: function (rowId, updateObj, origObj, row) {
			var t, autoCommit = this.grid.options.autoCommit;
			origObj = origObj || this._getLatestValues(rowId);
			row = row || this.grid.rowById(rowId);
			/* update the data source */
			t = this.grid.dataSource.updateRow(rowId, $.extend({}, origObj, updateObj), autoCommit);
			/* update the grid */
			this._updateUIForTransaction(t, row);
			/* M.H. 21 August 2015 Fix for bug 204837:
			When updating a row the summaries don't get recalculated */
			this._notifyRowUpdated(rowId, row);
		},
		_updateCell: function (rowId, colKey, value, cell) {
			var t, autoCommit = this.grid.options.autoCommit;
			/* update the data source */
			t = this.grid.dataSource.setCellValue(rowId, colKey, value, autoCommit);
			/* update the grid */
			this._updateUIForTransaction(t, cell);
			/* M.H. 21 August 2015 Fix for bug 204837:
			When updating a row the summaries don't get recalculated */
			this._notifyCellUpdated(rowId, cell);
		},
		_addRow: function (evt, addObj, defVals, suppress) {
			var arg, key, t, row;
			addObj = $.extend({}, defVals, addObj);
			arg = {
				owner: this,
				values: addObj,
				oldValues: defVals
			};
			if (!suppress) {
				if (!this._trigger(this.events.rowAdding, evt, arg)) {
					return false;
				}
			}

			key = addObj[ this.grid.options.primaryKey ];
			if (key === undefined || key === null) {
				key = this._pkVal;
				addObj[ this.grid.options.primaryKey ] = key;
			}
			t = this.grid.dataSource.addRow(key, addObj, this.grid.options.autoCommit);
			row = this._updateUIForTransaction(t);
			this._notifyRowAdded(row);
			/* increase the inner counter for the auto-generated PK */
			if ($.type(this._pkVal) === "number") {
				this._pkVal++;
			}
			if (!suppress) {
				this._trigger(this.events.rowAdded, evt, arg);
			}
		},
		/* internal notifications */
		_notifyRowAdded: function (row) {
			// refactor internalrowadded
			var rs = this.grid.element.data("igGridRowSelectors"),
				pa = this.grid.element.data("igGridPaging"),
				su = this.grid.element.data("igGridSummaries");
			if (rs && typeof rs._rowAdded === "function") {
				rs._rowAdded(row);
			}
			if (pa && typeof pa._rowAdded === "function") {
				pa._rowAdded(row);
			}
			/* M.H. 21 August 2015 Fix for bug 204837:
			When updating a row the summaries don't get recalculated */
			if (su && typeof su._rowAdded === "function") {
				su._rowAdded(row);
			}
			this.grid._fireInternalEvent("_internalRowAdded", { row: row });
		},
		_notifyRowDeleted: function (rowId, element) {
			var se = this.grid.element.data("igGridSelection"),
				pa = this.grid.element.data("igGridPaging"),
				su = this.grid.element.data("igGridSummaries");
			if (se && typeof se._rowDeleted === "function") {
				se._rowDeleted(rowId, element);
			}
			if (su && typeof su._rowDeleted === "function") {
				su._rowDeleted(rowId, element);
			}
			if (pa && typeof pa._rowDeleted === "function") {
				pa._rowDeleted(rowId, element);
			}
			this.grid._fireInternalEvent("_internalRowDeleted", { rowID: rowId, row: element });
		},
		_notifyCellUpdated: function (rowId, cell) {
			this.grid._fireInternalEvent("_internalCellUpdated", { rowID: rowId, cell: cell });
		},
		_notifyRowUpdated: function (rowId, row) {
			this.grid._fireInternalEvent("_internalRowUpdated", { rowID: rowId, row: row });
		},
		/* rendering methods */
		_renderRowEditDialog: function (rerender) {
			var opts = this.options.rowEditDialogOptions,
				container, containment, captionsContainer, closeButton,
				dialogSelector = "#" + this.grid.id() + "_updating_dialog_container",
				titleLabel = opts.captionLabel !== null ?
					opts.captionLabel : $.ig.GridUpdating.locale.rowEditDialogCaptionLabel,
				doneButtonLabel = this.options.doneLabel !== null ?
					this.options.doneLabel : $.ig.GridUpdating.locale.doneLabel,
				doneButtonTitle = this.options.doneTooltip !== null ?
					this.options.doneTooltip : $.ig.GridUpdating.locale.doneTooltip,
				cancelButtonLabel = this.options.cancelLabel !== null ?
					this.options.cancelLabel : $.ig.GridUpdating.locale.cancelLabel,
				cancelButtonTitle = this.options.cancelTooltip !== null ?
					this.options.cancelTooltip : $.ig.GridUpdating.locale.cancelTooltip;
			container = $(dialogSelector);
			if (container.length) {
				if (!rerender) {
					return container;
				}
				container.igGridModalDialog("destroy");
				container.remove();
				/* clean the cached editors template render */
				this._destroyAllEditors();
			}
			if (opts.containment === "owner") {
				containment = this.grid._rootContainer();
			} else {
				containment = "window";
			}
			/* the dialog container */
			container = $("<div></div>")
				.appendTo(this.grid._rootContainer())
				.attr("id", dialogSelector.substring(1));
			container.igGridModalDialog({
				containment: containment,
				renderFooterButtons: opts.showDoneCancelButtons,
				modalDialogCaptionText: titleLabel,
				modalDialogWidth: opts.width,
				modalDialogHeight: opts.height,
				buttonApplyText: doneButtonLabel,
				buttonApplyTitle: doneButtonTitle,
				buttonCancelText: cancelButtonLabel,
				buttonCancelTitle: cancelButtonTitle,
				animationDuration: opts.animationDuration,
				buttonApplyDisabled: true,
				gridContainer: this.grid._rootContainer(),
				/* M.H. 19 Nov 2015 Fix for bug 209857: Modal dialog of Feature Chooser
				for Hiding column closes when pressing Enter */
				closeModalDialogOnEnter: true,
				/* events */
				modalDialogOpening: this._dialogHandlers.dialogOpening,
				modalDialogOpened: this._dialogHandlers.dialogOpened,
				modalDialogClosing: this._dialogHandlers.dialogClosing,
				modalDialogClosed: this._dialogHandlers.dialogClosed,
				buttonOKClick: this._dialogHandlers.dialogDone,
				buttonCancelClick: this._dialogHandlers.dialogCancel,
				tabIndex: this._getNextTabIndex()
			});
			captionsContainer = container.igGridModalDialog("getCaptionButtonContainer");
			closeButton = $("<button></button>")
				.attr("id", this.grid.id() + "_updating_dialog_closeButton")
				.attr("tabindex", this._getNextTabIndex())
				.appendTo(captionsContainer);
			closeButton.igButton({
				onlyIcons: true,
				icons: {
					primary: this.css.rowEditDialogCloseButton
				},
				width: "20px",
				height: "20px",
				click: this._dialogHandlers.dialogCloseClick
			});
			this._dialogInvalid = false;
			this._editorsContainerInvalid = true;
			this._trigger(
				this.events.rowEditDialogContentsRendered, null, { owner: this, dialogElement: container }
			);
			return container;
		},
		_renderDialogContent: function (content, values) {
			var opts = this.options.rowEditDialogOptions, colgroup, tmpl;
			this._clearEditorsFromContainer(content);
			content.empty();
			if (opts.dialogTemplateSelector) {
				tmpl = $(opts.dialogTemplateSelector);
				content.append($(this._jsr ?
					tmpl.render(values) :
					$.ig.tmpl(tmpl.html().fullTrim(), values)));
			} else if (opts.dialogTemplate) {
				tmpl = opts.dialogTemplate;
				content.append($(this._jsr ?
					$.render[ this.grid.id() + "_rowEditDialogTemplate" ](values) :
					$.ig.tmpl(tmpl, values)));
			} else {
				content.html(this._defaultDialogTemplate);
				/* we need to set col widts for the default dialog */
				content.find("table").addClass(this.css.rowEditDialogTable).css("table-layout", "fixed");
				colgroup = content.find("colgroup");
				colgroup.children(":first").css("width", opts.namesColumnWidth);
				colgroup.children(":last").css("width", opts.editorsColumnWidth);
				colgroup.parent().css("width", "100%");
			}
		},
		_renderDialogTemplate: function (tmplParent, cols, onlyVisible) {
			var opts = this.options.rowEditDialogOptions, tmpl, i, tr, e;
			cols = onlyVisible ? $.grep(cols, function (col) { return !col.hidden; }) : cols;
			if (!opts.showReadonlyEditors) {
				cols = $.grep(cols, function (col) { return !col.readOnly; });
			}
			/* we need to render the template */
			if (opts.editorsTemplateSelector) {
				tmpl = $(opts.editorsTemplateSelector);
				tmplParent.append($(this._jsr ?
					tmpl.render(cols) :
					$.ig.tmpl(tmpl.html().fullTrim(),
					cols)));
			} else if (opts.editorsTemplate) {
				tmpl = opts.editorsTemplate;
				tmplParent.append($(this._jsr ?
					$.render[ this.grid.id() + "_rowEditDialogEditorsTemplate" ](cols) :
					$.ig.tmpl(tmpl, cols)));
			} else {
				for (i = 0; i < cols.length; i++) {
					tr = $("<tr></tr>");
					$("<td>" + (cols[ i ].headerText || cols[ i ].key) + "</td>").appendTo(tr);
					$("<td></td>").appendTo(tr);
					e = this._isMultiLineText(cols[ i ].key) ? $("<textarea></textarea>") : $("<input />");
					e.attr("data-editor-for-" + cols[ i ].key, "").appendTo(tr.children(":last"));
					tr.appendTo(tmplParent);
				}
			}
		},
		_initDialogEditors: function (content, cols, values) {
			var i = 0, provider, providerWrapper, ro, key, element, val, settings;
			/* editors may not be yet initialized */
			this._editors = this._editors || {};
			/* search for cols */
			while (i < cols.length) {
				key = cols[ i ].key;
				ro = !!cols[ i ].readOnly;
				val = values[ key ] === undefined ? null : values[ key ];
				element = content.find("[data-editor-for-" + key.toLowerCase() + "]");
				if (element.length === 1) {
					// check if the editor filter
					//providerWrapper = element.data("igEditorFilter");
					providerWrapper = this._providerForKey(key);
					if (providerWrapper && providerWrapper.has(element).length) {
						// already init, only change value
						provider = providerWrapper.igEditorFilter("option", "provider");
						provider.setValue(val);
					} else {
						// we need to create the editor
						if (ro) {
							settings = this._getColSettingsForCol(key);
							if (!settings) {
								this.options.columnSettings.push({ columnKey: key, editorOptions: { readOnly: true } });
							} else {
								$.extend(true, settings, { editorOptions: { readOnly: true } });
							}
						}
						providerWrapper = this._createEditor(null, key, element);
						this._editors[ key ] = providerWrapper;
						provider = providerWrapper.igEditorFilter("option", "provider");
						provider.setValue(val);
					}
					if (this._columnToFocus === key) {
						this._columnToFocus = providerWrapper;
					}
					this._originalValues = this._originalValues || {};
					this._originalValues[ key ] = provider.getValue();
					$.ig.removeFromArray(cols, i, i);
				} else {
					i++;
				}
			}
		},
		_clearEditorsFromContainer: function (content) {
			var cols = this.grid.options.columns,
				i = -1, key, element, providerWrapper;
			while (++i < cols.length) {
				key = cols[ i ].key;
				element = content.find("[data-editor-for-" + key.toLowerCase() + "]");
				if (element.length) {
					providerWrapper = this._providerForKey(key).data("igEditorFilter");
					if (providerWrapper) {
						this._providerForKey(key).igEditorFilter("destroy");
						delete this._editors[ key ];
					}
				}
			}
		},
		_renderDoneCancelButtons: function () {
			var buttonContainer,
				buttonsParent,
				doneButton,
				cancelButton,
				deleteButton,
				doneButtonLabel = this.options.doneLabel !== null ?
					this.options.doneLabel : $.ig.GridUpdating.locale.doneLabel,
				doneButtonTitle = this.options.doneTooltip !== null ?
					this.options.doneTooltip : $.ig.GridUpdating.locale.doneTooltip,
				cancelButtonLabel = this.options.cancelLabel !== null ?
					this.options.cancelLabel : $.ig.GridUpdating.locale.cancelLabel,
				cancelButtonTitle = this.options.cancelTooltip !== null ?
					this.options.cancelTooltip : $.ig.GridUpdating.locale.cancelTooltip,
				doneSelector, cancelSelector, deleteSelector,
				v = this.grid.options.virtualization === true ||
					this.grid.options.columnVirtualization === true ||
					this.grid.options.rowVirtualization === true,
				sbw = this.grid.hasVerticalScrollbar() && !v ? this.grid._scrollbarWidth() : 0;
			buttonContainer = $("<div></div>")
				.css("position", "absolute")
				.attr("unselectable", "on")
				.addClass(this.css.buttonContainer);
			/* button container is either child of the scroll or the grid containers */
			buttonsParent = this._addElementToScrollContainer(buttonContainer);
			/* Done button */
			doneButton = $("<span></span")
				.attr("id", this.grid.id() + "_updating_done")
				.addClass(this.css.button)
				.addClass(this.css.doneButton)
				.attr({
					unselectable: "on",
					tabIndex: this._getNextTabIndex() + 1,
					title: doneButtonTitle
				})
				/* P.Zh. 1 Mar 2016 Fix for bug #214751: [Mobile] When adding a child row "Done" and
				"Cancel" buttons and the small icons of the buttons are not properly positioned */
				.css({
					"display": "inline-block",
					"position": "relative"
				})
				.appendTo(buttonContainer);
			$("<span></span>")
				.css("display", "inline-block")
				.addClass(this.css.doneIcon)
				.attr("unselectable", "on")
				.appendTo(doneButton);
			if (doneButtonLabel && doneButton.length) {
				// add the done button label
				$("<span></span>")
					.css("display", "inline-block")
					.attr("unselectable", "on")
					.html(doneButtonLabel)
					.appendTo(doneButton);
			} else {
				doneButton.addClass(this.css.buttonIconOnly);
			}
			/* Cancel button */
			cancelButton = $("<span></span>")
				.attr("id", this.grid.id() + "_updating_cancel")
				.addClass(this.css.button)
				.addClass(this.css.cancelButton)
				.attr({
					unselectable: "on",
					tabIndex: this._getNextTabIndex() + 1,
					title: cancelButtonTitle
				})
				/* P.Zh. 1 Mar 2016 Fix for bug #214751: [Mobile] When adding a child row "Done"
				and "Cancel" buttons and the small icons of the buttons are not properly positioned */
				.css({
					"display": "inline-block",
					"position": "relative"
				})
				.appendTo(buttonContainer);
			$("<span></span>")
				.css("display", "inline-block")
				.addClass(this.css.cancelIcon)
				.attr("unselectable", "on")
				.appendTo(cancelButton);
			if (cancelButtonLabel && cancelButtonLabel.length) {
				$("<span></span>")
					.css("display", "inline-block")
					.attr("unselectable", "on")
					.html(cancelButtonLabel)
					.appendTo(cancelButton);
			} else {
				cancelButton.addClass(this.css.buttonIconOnly);
			}
			/* add delete button here for touch environments
			touch delete */
			if (this._renderTouchUI && this.options.enableDeleteRow) {
				deleteButton = $("<span />")
					.attr("id", this.grid.id() + "_updating_delete_touch")
					.addClass(this.css.button)
					.addClass(this.css.doneButton)
					.attr({
						unselectable: "on",
						tabIndex: this._getNextTabIndex() + 1,
						title: this.options.deleteRowTooltip !== null ?
							this.options.deleteRowTooltip : $.ig.GridUpdating.locale.deleteRowTooltip
					})
					.prependTo(buttonContainer);
				$("<span />")
					.css({
						display: "inline-block",
						left: 0
					})
					.addClass(this.css.deleteIcon)
					.attr("unselectable", "on")
					.appendTo(deleteButton);
				$("<span />")
					.css("display", "inline-block")
					.attr("unselectable", "on")
					.html(this.options.deleteRowLabel !== null ?
						this.options.deleteRowLabel : $.ig.GridUpdating.locale.deleteRowLabel)
					.appendTo(deleteButton);
				/* P.Zh. 1 Mar 2016 Fix for bug #214751: [Mobile] When adding a child row "Done" and
				"Cancel" buttons and the small icons of the buttons are not properly positioned */
				deleteButton.css({
					"float": "left",
					"position": "relative"
				});
				doneButton.css("float", "right");
				cancelButton.css("float", "right");
				/* Subtract 2 pixels because of the borders. */
				buttonContainer.css("width", buttonsParent.outerWidth() - sbw - 2);
				deleteSelector = "#" + deleteButton.attr("id");
			}
			/* bind events */
			doneSelector = "#" + doneButton.attr("id");
			cancelSelector = "#" + cancelButton.attr("id");
			/* common handlers */
			this.grid.container().on({
				"mouseenter.donecancel": this._buttonHandlers.mouseEnter,
				"mouseleave.donecancel": this._buttonHandlers.mouseLeave,
				"focus.donecancel": this._buttonHandlers.focus,
				"blur.donecancel": this._buttonHandlers.blur
			}, doneSelector + "," + cancelSelector + (deleteSelector ? "," + deleteSelector : ""));
			/* specific handlers */
			this.grid.container().on({
				"click.donecancel": this._buttonHandlers.doneClick,
				"keyup.donecancel": this._buttonHandlers.doneKeyUp
			}, doneSelector);
			this.grid.container().on({
				"click.donecancel": this._buttonHandlers.cancelClick,
				"keyup.donecancel": this._buttonHandlers.cancelKeyUp
			}, cancelSelector);
			if (deleteSelector) {
				this.grid.container().on({
					"click.donecancel": this._buttonHandlers.touchDeleteClick,
					"keyup.donecancel": this._buttonHandlers.touchDeleteKeyUp
				}, deleteSelector);
			}
			return buttonContainer;
		},
		_removeDoneCancelButtons: function () {
			var container = this._findElementInScrollContainer(".ui-iggrid-buttoncontainer:first");
			this.grid.container().off(".donecancel");
			container.remove();
		},
		_renderDeleteButton: function () {
			var deleteButton,
				deleteParent,
				ubodySelector = "#" + this.grid.id() + ">tbody",
				fbodySelector = "#" + this.grid.id() + "_fixed>tbody",
				deleteSelector = "#" + this.grid.id() + "_updating_deletehover",
				deleteButtonTitle = this.options.deleteRowTooltip !== null ?
				this.options.deleteRowTooltip : $.ig.GridUpdating.locale.deleteRowTooltip,
				pe = window.navigator.pointerEnabled;
			/* deleteButtonLabel = this.options.deleteRowLabel !== null ?
			this.options.deleteRowLabel : $.ig.GridUpdating.locale.deleteRowLabel;
			prevent rendering of the same button */
			deleteButton = $(deleteSelector);
			if (deleteButton.length) {
				return;
			}
			/* button */
			deleteButton = $("<span></span>")
				.attr("id", deleteSelector.substring(1))
				.css("position", "absolute")
				.addClass(this.css.deleteButton)
				.attr({
					unselectable: "on",
					title: deleteButtonTitle
				});
			deleteButton.hide();
			/* icon */
			$("<span></span>")
				.css("display", "inline-block")
				.addClass(this.css.deleteIcon)
				.attr("unselectable", "on")
				.appendTo(deleteButton);
			/* label if specified */
			/*if (deleteButtonLabel && deleteButtonLabel.length) {
				$("<span></span>")
					.css("display", "inline-block")
					.attr("unselectable", "on")
					.html(deleteButtonLabel)
					.appendTo(deleteButton);
			}*/
			deleteParent = this._addElementToScrollContainer(deleteButton);
			/* bind the delete button related events */
			this.grid.container().on({
				"mouseenter.deletebutton": this._buttonHandlers.mouseEnter,
				"mouseleave.deletebutton": this._buttonHandlers.mouseLeave,
				"click.deletebutton": this._buttonHandlers.deleteClick,
				"keyup.deletebutton": this._buttonHandlers.deleteClick
			}, deleteSelector);
			/* bind tbody events */
			if (pe) {
				// in IE filter the pointer events and accept hoever only
				// when the pointerType is mouse
				this.grid.container().on({
					"pointerover.deletebutton": this._handlers.pointerEnter
				}, ubodySelector + ">tr," + fbodySelector + ">tr");
			} else {
				this.grid.container().on({
					"mouseenter.deletebutton": this._handlers.mouseEnter
				}, ubodySelector + ">tr," + fbodySelector + ">tr");
			}
			/* if (this._renderTouchUI) {
			touch-specific events to show the delete button on swipe */
			this.grid.container().on({
				"MSPointerDown.deletebutton": this._handlers.pointerDown,
				"pointerdown.deletebutton": this._handlers.pointerDown,
				"MSPointerUp.deletebutton": this._handlers.pointerUp,
				"pointerup.deletebutton": this._handlers.pointerUp,
				"touchstart.deletebutton": this._handlers.touchStart,
				"touchend.deletebutton": this._handlers.touchEnd
			}, ubodySelector + ">tr," + fbodySelector + ">tr");
			if (deleteParent[ 0 ] === this.grid.container()[ 0 ]) {
				if (pe) {
					this.grid.container().bind("pointerleave.deletebutton", this._handlers.pointerLeave);
				} else {
					this.grid.container().bind("mouseleave.deletebutton", this._handlers.mouseLeave);
				}
			} else {
				if (pe) {
					this.grid.container().on({
						"pointerleave.deletebutton": this._handlers.pointerLeave
					}, "#" + deleteParent.attr("id"));
				} else {
					this.grid.container().on({
						"mouseleave.deletebutton": this._handlers.mouseLeave
					}, "#" + deleteParent.attr("id"));
				}
			}
		},
		_removeDeleteButton: function () {
			$("#" + this.grid.id() + "_updating_deletehover").remove();
			this.grid.container().off(".deletebutton");
			this.grid.container().unbind(".deletebutton");
		},
		_renderAddNewRow: function (thead) {
			var addRowLabel = this.options.addRowLabel !== null ?
					this.options.addRowLabel : $.ig.GridUpdating.locale.addRowLabel,
				addRowTitle = this.options.addRowTooltip !== null ?
					this.options.addRowTooltip : $.ig.GridUpdating.locale.addRowTooltip,
				addTr, newTr, mainTd, i, recVerticalSize;
			addTr = thead.children("tr[data-add-row='true']");
			if (!addTr.length) {
				addTr = $("<tr></tr>")
					.addClass(this.css.addRow)
					.attr({
						"data-add-row": true,
						"tabIndex": this.grid.options.tabIndex
					})
					.appendTo(thead);
			}
			addTr.empty();
			mainTd = $("<td></td>")
				.attr("title", addRowTitle)
				.addClass(this.css.addRow)
				.appendTo(addTr);
			$("<span></span>")
				.css("display", "inline-block")
				.addClass(this.css.addRowIcon)
				.appendTo(mainTd);
			$("<span></span>")
				.text(addRowLabel)
				.css("outline", 0)
				.attr("tabIndex", 0)
				.appendTo(mainTd);
			/* render (hidden) cells */
			newTr = thead.children("tr[data-new-row='true']");
			if (!newTr.length) {
				recVerticalSize = this.grid._recordVerticalSize();
				for (i = 0; i < recVerticalSize; i++) {
					$("<tr></tr>")
					.attr("data-new-row", "true")
					.attr("data-role", "newrow")
					.appendTo(thead)
					.hide();
			}
			}
		},
		_generateDummyLayout: function (cols) {
			var i, layout = [ [ ] ];
			for (i = 0; i < cols.length; i++) {
				layout[ 0 ].push({
					col: cols[ i ],
					rs: 1,
					cs: 1
				});
			}
			return layout;
		},
		_addElementToScrollContainer: function (element) {
			// both the delete button and the done/cancel buttons container
			// need to be in the scrollcontainer if the grid has height
			// in virtual grids the container should be the display container
			var go = this.grid.options, container,
				hasHeight = go.height !== null && go.height !== undefined;
			if (!hasHeight) {
				container = this.grid.container();
				element.appendTo(container);
			} else if (this.grid.hasFixedColumns() && this.grid.fixingDirection() === "right") {
				container = this.grid.fixedBodyContainer();
				element.prependTo(container);
			} else {
				if (go.virtualization || go.rowVirtualization || go.columnVirtualization) {
					container = this.grid._vdisplaycontainer();
				} else {
					container = this.grid.scrollContainer();
				}
				element.prependTo(container);
			}
			return container;
		},
		_findElementInScrollContainer: function (selector) {
			var go = this.grid.options, container,
				hasHeight = go.height !== null && go.height !== undefined;
			if (!hasHeight) {
				container = this.grid.container();
			} else if (this.grid.hasFixedColumns() && this.grid.fixingDirection() === "right") {
				container = this.grid.fixedBodyContainer();
			} else {
				if (go.virtualization || go.rowVirtualization || go.columnVirtualization) {
					container = this.grid._vdisplaycontainer();
				} else {
					container = this.grid.scrollContainer();
				}
			}
			return container.children(selector);
		},
		/* binding */
		_createHandlers: function () {
			this._stopEditingHandler = this._stopEditingHandler || $.proxy(this._stopEditing, this);
			this._gridHandlers = this._gridHandlers || {
				stopEditing: this._stopEditingHandler,
				gridDataRendering: $.proxy(this._gridDataRendering, this),
				rowsRendered: $.proxy(this._rowsRendered, this),
				headerRendering: $.proxy(this._processReadOnly, this),
				headerRendered: $.proxy(this._headerRendered, this),
				rendered: $.proxy(this._headerRendered, this),
				virtualFrameChanging: $.proxy(this._virtPreRender, this),
				virtualFrameChanged: $.proxy(this._virtPostRender, this),
				columnsCollectionModified: $.proxy(this._columnsModified, this)
			};
			this._handlers = this._handlers || {
				mouseDown: $.proxy(this._mouseDown, this),
				clickTrigger: $.proxy(this._clickTrigger, this),
				keyDown: $.proxy(this._keyDown, this),
				focus: $.proxy(this._focus, this),
				blur: $.proxy(this._blur, this),
				touchStart: $.proxy(this._touchStart, this),
				touchEnd: $.proxy(this._touchEnd, this),
				pointerDown: $.proxy(this._pointerDown, this),
				pointerUp: $.proxy(this._pointerUp, this),
				mouseEnter: $.proxy(this._rowMouseEnter, this),
				pointerEnter: $.proxy(this._rowPointerEnter, this),
				mouseLeave: $.proxy(this._containerMouseLeave, this),
				pointerLeave: $.proxy(this._containerPointerLeave, this),
				scroll: $.proxy(this._scroll, this)
			};
			this._buttonHandlers = this._buttonHandlers || {
				/* common */
				mouseEnter: $.proxy(this._buttonMouseEnter, this),
				mouseLeave: $.proxy(this._buttonMouseLeave, this),
				focus: $.proxy(this._buttonFocus, this),
				blur: $.proxy(this._buttonBlur, this),
				/* specific */
				doneClick: $.proxy(this._doneButtonClick, this),
				doneKeyUp: $.proxy(this._doneButtonKeyUp, this),
				cancelClick: this._stopEditingHandler,
				cancelKeyUp: $.proxy(this._cancelButtonKeyUp, this),
				deleteClick: $.proxy(this._deleteButtonClick, this),
				touchDeleteClick: $.proxy(this._touchDeleteButtonClick, this),
				touchDeleteKeyUp: $.proxy(this._touchDeleteButtonKeyUp, this)
			};
			this._addNewRowHandlers = this._addNewRowHandlers || {
				focus: $.proxy(this._addRowFocus, this),
				blur: $.proxy(this._addRowBlur, this)
			};
			this._validationHandlers = this._validationHandlers || {
				errorShowing: $.proxy(this._editorErrorShowing, this),
				errorShown: $.proxy(this._editorErrorShown, this),
				errorHidden: $.proxy(this._editorErrorHidden, this)
			};
			this._dialogHandlers = this._dialogHandlers || {
				dialogCloseClick: $.proxy(this._dialogCloseClick, this),
				dialogOpening: $.proxy(this._dialogOpening, this),
				dialogOpened: $.proxy(this._dialogOpened, this),
				dialogClosing: $.proxy(this._dialogClosing, this),
				dialogClosed: $.proxy(this._dialogClosed, this),
				dialogDone: $.proxy(this._dialogDone, this),
				dialogCancel: $.proxy(this._dialogCancel, this)
			};
			this._editorCallbacks = this._editorCallbacks || {
				keyDown: $.proxy(this._editorKeyDown, this),
				textChanged: $.proxy(this._editorTextChanged, this)
			};
		},
		_unbindAllEvents: function () {
			this.grid.element.off({
				iggriduidirty: this._gridHandlers.stopEditing,
				iggridresizingcolumnresizing: this._gridHandlers.stopEditing,
				ighierarchicalgridrowexpanded: this._gridHandlers.stopEditing,
				/* should attach container events when the grid is rendered */
				iggridrendered: this._gridHandlers.gridRendered,
				/* should check for data dirty and PK existence on this event */
				iggriddatarendering: this._gridHandlers.gridDataRendering,
				/* should redraw modified styles on these events */
				iggridrowsrendered: this._gridHandlers.rowsRendered,
				/* iggriddatabinding: this._gridHandlers.gridRendering,
				iggridrowsrendering:	this._gridHandlers.gridRendering,
				instead of just stoping editing we'll try to preserve the edit state
				and exit - enter it on each rerender, so that we preserve the chance to edit
				iggridvirtualrendering: this._gridHandlers.stopEditing, */
				iggridvirtualrendering: this._gridHandlers.virtualFrameChanging,
				iggridvirtualrecordsrender: this._gridHandlers.virtualFrameChanged,
				iggridcolumnscollectionmodified: this._gridHandlers.columnsCollectionModified,
				iggridheaderrendering: this._gridHandlers.headerRendering
			});
			if (this.options.enableAddRow) {
				this.grid.element.off({
					iggridheaderrenderedinternal: this._gridHandlers.headerRendered,
					iggridrendered: this._gridHandlers.rendered
				});
			}
			this.grid.container().off({
				click: this._handlers.click
			});
		},
		_bindGridEvents: function () {
			// grid framework and other feature events
			this.grid.element.on({
				/* should stop editing on these events */
				iggriduidirty: this._gridHandlers.stopEditing,
				iggridresizingcolumnresizing: this._gridHandlers.stopEditing,
				ighierarchicalgridrowexpanded: this._gridHandlers.stopEditing,
				/* should check for data dirty and PK existence on this event */
				iggriddatarendering: this._gridHandlers.gridDataRendering,
				/* should redraw modified styles on these events */
				iggridrowsrendered: this._gridHandlers.rowsRendered,
				/* iggriddatabinding: this._gridHandlers.gridRendering,
				iggridrowsrendering: this._gridHandlers.gridRendering, */
				iggridvirtualrendering: this._gridHandlers.virtualFrameChanging,
				iggridvirtualrecordsrender: this._gridHandlers.virtualFrameChanged,
				iggridcolumnscollectionmodified: this._gridHandlers.columnsCollectionModified,
				iggridheaderrendering: this._gridHandlers.headerRendering
			});
			if (this.options.enableAddRow) {
				this.grid.element.on({
					iggridheaderrenderedinternal: this._gridHandlers.headerRendered,
					iggridrendered: this._gridHandlers.rendered
				});
			}
		},
		_injectGrid: function (gridInstance, isRebind) {
			var hg, cl;
			this.grid = gridInstance;

			/* M.H. 18 Feb 2014 Fix for bug #153639: Add successCallback
			and errorCallback params to the igDataSource.saveChanges API */
			this._addDSSuccessHandler();
			this._addDSErrorHandler();

			if (isRebind) {
				return;
			}
			this._dialogInvalid = true;
			this._defaultDialogTemplate =
				"<table><colgroup><col></col><col></col></colgroup><tbody data-render-tmpl></tbody></table>";
			this._editors = this._editors || {};
			/* check setup support */
			if (this.options.excelNavigationMode &&
				(this.options.editMode !== "cell" && this.options.editMode !== "row")) {
				throw new Error($.ig.GridUpdating.locale.excelNavigationNotSupportedWithCurrentEditMode);
			}
			if (this.grid._isMultiRowGrid() && this.options.editMode !== "dialog") {
				throw new Error($.ig.GridUpdating.locale.multiRowGridNotSupportedWithCurrentEditMode);
			}
			this._createHandlers();
			this._bindGridEvents();
			this._analyzeEditTriggers();
			if (this.grid.options._isHierarchicalGrid && this.grid._originalOptions) {
				// we'll flag the grid if enableAddRow is true for at least one child layout
				// this flag will be used to enable expanding of new rows added in this layout even
				// if they don"t contain any data in their child layout properties
				if (this.options.inherit) {
					this.grid._shouldAlwaysRenderChildIndicator =
						this._childrenWithAddRowEnabled(this.grid.options, this.options.enableAddRow);
				} else {
					this.grid._shouldAlwaysRenderChildIndicator =
						this._childrenWithAddRowEnabled(this.grid.options);
				}
				/* we also need to add layouts to the data schema for the child data sources
				in this way we will know which properties to add empty collections for when adding rows */
				if (!this.grid.dataSource.settings.schema.layouts) {
					hg = this.grid.element.closest(".ui-iggrid-root").data("igHierarchicalGrid");
					cl = [];
					hg._parseLayouts(cl, "", this.grid._originalOptions);
					this.grid.dataSource.settings.schema.layouts = cl;
				}
			}
			this._renderTouchUI = $.ig.util.isTouch;
			if (window.navigator.msPointerEnabled || window.navigator.pointerEnabled) {
				this.grid.element.css("-ms-touch-action", "none");
				this.grid.element.css("touch-action", "none");
			}
			/* set jsRender style templating */
			if (String(this.grid.options.templatingEngine).toLowerCase() === "jsrender") {
				this._jsr = true;
				if (this.options.rowEditDialogOptions.dialogTemplate &&
					typeof this.options.rowEditDialogOptions.dialogTemplate === "string") {
					$.templates(this.grid.id() + "_rowEditDialogTemplate",
						this.options.rowEditDialogOptions.dialogTemplate);
				}
				if (this.options.rowEditDialogOptions.editorsTemplate &&
					typeof this.options.rowEditDialogOptions.editorsTemplate === "string") {
					$.templates(this.grid.id() + "_rowEditDialogEditorsTemplate",
						this.options.rowEditDialogOptions.editorsTemplate);
				}
			}
		}
	});
	$.extend($.ui.igGridUpdating, { version: "16.1.20161.2145" });
}(jQuery));
