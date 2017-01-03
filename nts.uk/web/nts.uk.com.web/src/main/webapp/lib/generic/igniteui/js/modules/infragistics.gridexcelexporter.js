/// <reference path="infragistics.excel.js" />
/*!@license
 * Infragistics.Web.ClientUI Widget 16.1.20161.2145
 *
 * Copyright (c) 2011-2016 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 *
 * Depends on:
 *  jquery-1.9.1.js
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 *  FileSaver.js
 *  infragistics.ui.grid.framework.js
 *  infragistics.ui.grid.columnfixing.js
 *  infragistics.ui.grid.filtering.js
 *  infragistics.ui.grid.hiding.js
 *  infragistics.ui.grid.paging.js
 *  infragistics.ui.grid.sorting.js
 *  infragistics.ui.grid.summaries.js
 *  infragistics.excel.js
 */

/*global jQuery, Class, saveAs */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {

	$.ig = $.ig || {};

	$.ig.GridExcelExporter = $.ig.GridExcelExporter || Class.extend({
		/* The Infragistics Grid Excel exporter client-side component is implemented as a class, and exports the igGrid control with the columnfixing, filtering, hiding, paging, sorting, and summaries features
		*/
		settings: {
			/* type="string" Specifies the name of the excel file that will be generated. */
			fileName: "document",
			/* List of export settings which can be used with Grid Excel exporter */
			gridFeatureOptions: {
				/* type="none|applied" Indicates whether sorting will be applied in the exported table. This is set_ to none by default, but will change to applied if sorting feature is defined in the igGrid.
					none type="string" No sorting will be applied in the excel document.
					applied type="string" Sorting will be applied in the excel document.
				*/
				sorting: "none",
				/* type="currentPage|allRows" Indicates whether the rows on the current page or entire data will exported.
					currentPage type="string" Only current page will be exported to the excel document.
					allRows type="string" All pages will be exported to the excel document.
				*/
				paging: "allRows",
				/* type="none|applied|visibleColumnsOnly" Indicates whether hidden columns will be removed from the exported table. This is set to none by default, but will change to applied if hiding feature is defined in the igGrid.
					none type="string" All hidden columns will be exported to the excel document.
					applied type="string" Hidden columns will be exported as hidden in the excel document.
					visibleColumnsOnly type="string" Only visible columns will be exported.
				*/
				hiding: "none",
				/* type="none|applied|filteredRowsOnly" Indicates whether filtering will be applied in the exported table. this is set to none by default, but will change to applied if filtering feature is defined in the igGrid.
					none type="string" No filtering will be applied in the excel document.
					applied type="string" Filtering will be applied in the excel document.
					filteredRowsOnly type="string" Filtering will be exported in the excel document.
				*/
				filtering: "none",
				/* type="none|applied" Indicates whether fixed columns will be applied in the exported table. This is set to none by default, but will change to applied if column fixing feature is defined in the igGrid.
					none type="string" No column fixing will be applied in the excel document.
					applied type="string" Column fixing will be applied in the excel document.
				*/
				columnfixing: "none",
				/* type="none|applied" Indicates whether summaries will be added in the exported table. This is set to none by default, but will change to applied if summaries feature is defined in the igGrid.
					none type="string" No summaries will be exported to the excel document.
					applied type="string" Summaries will be exported to the excel document.
				*/
				summaries: "none"
			},
			/* type="string" Specifies the name of workbook where the igGrid will be exported. */
			worksheetName: "Sheet1",
			/* type="array" List of strings containing the keys for the worksheet columns which will not be applied any filtering*/
			skipFilteringOn: [],
			/* type="array" List of strings containing the keys for the columns that will not be exported*/
			columnsToSkip: [],
			/* type="string" Specifies the excel table style region.
				 You can set the following table style
				 TableStyleMedium[1-28]
				 TableStyleLight[1-21]
				 TableStyleDark[1-11]
			 */
			tableStyle: "TableStyleMedium1",
			/* type="none|applied" Indicates whether excel table styles will be the same as grid styles. This is set to applied by default. Custom grid themes are not supported.
				none type="string" The styles from the grid are not applied to the table region.
				applied type="string" The styles from the grid are applied to the table region.
			*/
			gridStyling: "applied",
			/* type="allRows|expandedRows" Indicates whether all sublevel data will be exported, or only data under expanded rows.
				allRows type="string" All sublevel data will be exported.
				expandedRows type="string" Only data under expanded rows will be exported.
			*/
			dataExportMode: "allRows"
		},

		/* Callback for the exporter events */
		callbacks: {
			/* cancel="true" Callback fired when the exporting has started.
			Function takes arguments sender and args.
			Use args.grid to get reference to igGrid widget.
			*/
			exportStarting: null,
			/* cancel="true" Callback fired when cell exporting has begin.
			Function takes arguments sender and args.
			Use args.columnKey to get the igGrid column key of the cell.
			Use args.columnIndex to get the igGrid column index of the cell.
			Use args.cellValue to get or set the igGrid cell value.
			Use args.rowId to get key or index of row.
			Use args.xlRow to get reference to the worksheet row.
			Use args.grid to get reference to the igGrid widget.
			*/
			cellExporting: null,
			/* Callback fired when cell exporting has end.
			Function takes arguments sender and args.
			Use args.columnKey to get the igGrid column key of the cell.
			Use args.columnIndex to get the igGrid column index of the cell.
			Use args.cellValue to get the igGrid cell value.
			Use args.rowId to get key or index of row.
			Use args.xlRow to get reference to the worksheet row.
			Use args.grid to get reference to the igGrid widget.
			*/
			cellExported: null,
			/* cancel="true" Callback fired when header cell exporting has begin.
			Function takes arguments sender and args.
			Use args.headerText to get or set the igGrid column key of the header cell.
			Use args.columnKey to get the igGrid column key of the header cell.
			Use args.columnIndex to get the igGrid column index of the header cell.
			*/
			headerCellExporting: null,
			/* Callback fired when header cell exporting has end.
			Function takes arguments sender and args.
			Use args.headerText to get the igGrid column key of the header cell.
			Use args.columnKey to get the igGrid column key of the header cell.
			Use args.columnIndex to get the igGrid column index of the header cell.
			*/
			headerCellExported: null,
			/* cancel="true" Callback fired when row exporting has begin.
			Function takes arguments sender and args.
			Use args.rowId to get key or index of row.
			Use args.element to get row TR element.
			Use args.xlRow to get reference to the worksheet row.
			Use args.grid to get reference to the igGrid widget.
			Note: When exporting an igHierarchicalGrid this callback is available only for the root grid rows.
			*/
			rowExporting: null,
			/* cancel="true" Callback fired when row exporting has ended.
			Function takes arguments sender and args.
			Use args.rowId to get key or index of row.
			Use args.element to get row TR element.
			Use args.xlRow to get reference to the worksheet row.
			Use args.grid to get reference to the igGrid widget.
			Note: When exporting an igHierarchicalGrid this callback is available only for the root grid rows.
			*/
			rowExported: null,
			/* cancel="true" Callback fired when summary exporting has begun.
			Function takes arguments sender and args.
			Use args.headerText to get the igGrid column header text.
			Use args.columnKey to get the igGrid column key.
			Use args.columnIndex to get the igGrid column index.
			Use args.summary to get a reference to the summary object.
			Use args.xlRowIndex to get reference to worksheet row index.
			*/
			summaryExporting: null,
			/* Callback fired when cell exporting has end.
			Function takes arguments sender and args.
			Use args.headerText to get the igGrid column header text.
			Use args.columnKey to get the igGrid column key.
			Use args.columnIndex to get the igGrid column index.
			Use args.summary to get a reference to the summary object.
			Use args.xlRowIndex to get the worksheet row index.
			*/
			summaryExported: null,
			/* cancel="true" Callback fired when export is ending, but the document is not saved.
			Function takes arguments sender and args.
			Use args.grid to get reference to the igGrid widget.
			Use args.workbook to get reference to the excel workbook.
			Use args.worksheet to get reference to the excel worksheet.
			*/
			exportEnding: null,
			/* Callback fired when exporting is successful.
			Use data to get the reference of saved object.
			*/
			success: null,
			/* Callback fired when exporting is failed.
			Use error to get the reference of error object.
			*/
			error: null
		},
		init: function () {
			this.settings = $.extend({}, this.settings);
			this.callbacks = $.extend({}, this.callbacks);
		},

		exportGrid: function (grid, userSettings, userCallbacks) {
			/* Exports the provided igGrid to Excel document.
				paramType="object" Grid to be exported.
				paramType="object" Settings for exporting the grid.
				paramType="object" Callbacks for the events.
			*/

			this._setGridReferences(grid);
			this._setSettings(userSettings);
			this._setCallbacks(userCallbacks);

			if (!this._handleEventCallback(this.callbacks.exportStarting, { grid: this._gridWidget })) {
				return;
			}

			var self = this;

			setTimeout(function () {
				self._createExcelWorkbookWithWorksheet();

				if (self._columnsToExport.length > 0) {
					self._getGridStyles();
					self._exportHeaders();
					self._setDataToExport();
					self._createExcelTableRegion();
					self._exportDataSegment();

					// This should be true when row exporting is canceled
					// so the table size should be resized also.
					if (self._shouldResizeTableRegion) {
						self._resizeTableRegion();
					}

					self._exportFeatures();
					self._calculateColumnsSize();
					self._styleChildGridHeaders();
				}

				var noCancel =
                    self._handleEventCallback(self.callbacks.exportEnding,
                        { grid: self._gridWidget, workbook: self._workbook,
                            worksheet: self._worksheet });

				if (!noCancel) {
					return;
				}

				self._saveWorkbook();
			}, 0);
		},

		_setGridReferences: function (grid) {

			if (grid.data().igHierarchicalGrid !== undefined) {
				this._gridType = "igHierarchicalGrid";
				this._grid = grid.data()[ this._gridType ];

				// D.G. 13 October 2015 fix 207480
			    // H.A. 03 December 2015 fix 210482
			    // Z.K. May 4th, 2016 Fixing Bug #214917 - When responseDataKey for both parent and child definitions is set to empty string "" _dataToExport is undefined
				if (this._grid.options.responseDataKey !== null &&
                    this._grid.options.responseDataKey !== undefined &&
                    this._grid.options.responseDataKey !== "") {
					this._gridData = this._grid.options.dataSource[ this._grid.options.responseDataKey ];
				} else {
					this._gridData = this._grid.options.dataSource;
				}

			    // H.A. 03 December 2015 fix 210482
			    // Z.K. May 4th, 2016 Fixing Bug #214917 - When responseDataKey for both parent and child definitions is set to empty string "" _dataToExport is undefined
				if (this._grid.options.columnLayouts[ 0 ].responseDataKey !== null &&
                    this._grid.options.columnLayouts[ 0 ].responseDataKey !== undefined &&
                    this._grid.options.columnLayouts[ 0 ].responseDataKey !== "") {
					this._gridData =
                    this._grid.options.
                        dataSource[ this._grid.options.columnLayouts[ 0 ].responseDataKey ];
				}
				this._dataRows = grid.data("igGrid").rows().siblings("[role='row']");
			} else {

				if (grid.data().igTreeGrid !== undefined) {
					this._gridType = "igTreeGrid";
				} else {
					this._gridType = "igGrid";
				}

				this._grid = grid.data()[ this._gridType ];
				this._gridData = this._grid.dataSource.data();
				this._dataRows = this._grid.rows().siblings("[role='row']");
			}

			this._gridWidget = grid;
			this._allColumnLayouts = [];
		},

		_setSettings: function (userSettings) {
			this._enableFeaturesAsInGrid();
			this._setUserSettings(userSettings);
			this._setColumnsToExport(this._grid.options);
			if (this._grid.options.columnLayouts !== undefined) {
				this._getAllColumnLayouts(this._grid.options.columnLayouts[ 0 ]);
			}

		},

		_enableFeaturesAsInGrid: function () {
			var gridFeatureOption;
			for (gridFeatureOption in this.settings.gridFeatureOptions) {
				if (!this._getFeature(gridFeatureOption)) {
					continue;
				}

				if (gridFeatureOption === "paging") {
					this.settings.gridFeatureOptions[ gridFeatureOption ] = "allRows";
				} else {
					this.settings.gridFeatureOptions[ gridFeatureOption ] = "applied";
				}
			}
		},

		_setUserSettings: function (userSettings) {
			this.settings = $.extend(this.settings, userSettings);
		},

	    // A.K. August 8th, 2016 Fixing Bug #223981 - igGridExcelExporter exports child grids' hidden columns always, even if hiding: "visibleColumnsOnly"
		_setColumnsToExport: function (columnsObj, indent) {
		    if (columnsObj !== undefined) {

		        if (indent !== undefined && indent > 0) {
		            this._childColumnsToExport = this._getVisibleColumns(columnsObj.columns);
		        } else {
		            this._columnsToExport = this._getVisibleColumns(columnsObj.columns);
		        }
		    }
		},

		_getVisibleColumns: function (columns) {
		    var i,
                visibleColumns = [],
                columnsToSkip = this.settings.columnsToSkip;

		    if (columns.length === 0 && this._gridType === "igHierarchicalGrid") {
		        columns = this._grid.rootWidget().options.columns;
		    }

		    for (i = 0; i < columns.length; i++) {
		        var gridColumn = columns[ i ];

		        if (gridColumn.hidden &&
                    this.settings.gridFeatureOptions.hiding === "visibleColumnsOnly") {
		            continue;
		        }

		        if (columnsToSkip.indexOf(gridColumn.key) < 0) {
		            visibleColumns.push(gridColumn);
		        }
		    }

		    return visibleColumns;
		},

		_setCallbacks: function (userCallbacks) {
			this.callbacks = $.extend(this.callbacks, userCallbacks);
		},

		_createExcelWorkbookWithWorksheet: function () {
			this._xlRowIndex = 0;
			this._workbook = new $.ig.excel.Workbook($.ig.excel.WorkbookFormat.excel2007);
			this._worksheet = this._workbook.worksheets().add(this.settings.worksheetName);
		},

		//Getting the grid styles for headers and rows (back & fore colors) for the different themes

		_getGridStyles: function () {

			// H.A. 04 January 2016 Fix #211764
			var headersTable,
				gridTable = this._gridWidget.find("#" + this._gridWidget.attr("id") + "_table");

			if (gridTable.length === 0) {
				gridTable = this._gridWidget;
			}

			headersTable = gridTable.find("thead");

			if (this.settings.gridStyling === "applied") {

				if (headersTable.length === 0) {

					// those hardcoded values correspond to the default style applied
					this._headersBackColor = "rgb(136, 136, 136)";
					this._headersForeColor = "rgb(255, 255, 255)";
					this._altRowColor = "rgb(240, 240, 240)";

				} else {

					// we create a copy header tr in order to modify it if the default one is affected by sorting styles applied
					var $stylesTR = $("<tr>").attr({
						"class": "ui-ig-altrecord ui-iggrid-altrecord"
					}).appendTo(gridTable).css("display", "none");

					// H.A. 26 November 2015 Fix #210111
					var $th = $(headersTable.find("th[role='columnheader']")[ 0 ]).clone().css("display", "none");
					$stylesTR.append($th);

					this._headersBackColor = $th.removeClass("ui-state-active").css("background-color");
					$th.remove();

					this._headersForeColor = headersTable.find(".ui-iggrid-headertext").css("color");

					if ($stylesTR.css("background-color").indexOf("rgba") > -1) {
						var backTopColor =
                            $stylesTR.css("background-color").
                                replace("rgba(", "").replace(")", "").split(", ");
						var backBotColor =
                            gridTable.css("background-color").
                                replace("rgb(", "").replace(")", "").split(", ");
						this._altRowColor =
                            this._RGBAtoRGB(backTopColor[ 0 ], backTopColor[ 1 ],
                                backTopColor[ 2 ], backTopColor[ 3 ], backBotColor[ 0 ],
                                backBotColor[ 1 ], backBotColor[ 2 ]);
					} else {
						this._altRowColor = $stylesTR.css("background-color");
					}

					$stylesTR.remove();

				}

				this._notAltRowColor = gridTable.css("background-color");
				this._rowForeColor = gridTable.css("color");

				this._getCellFill();
			}
		},

		//Function to convert RGBA to RGB colors from the grid
		_RGBAtoRGB: function (r, g, b, a, r2, g2, b2) {
			var r3 = a * r + (1 - a) * r2,
				g3 = a * g + (1 - a) * g2,
				b3 = a * b + (1 - a) * b2;
			return "rgb(" + r3 + "," + g3 + "," + b3 + ")";
		},

		// Bug when data has a response key
		_setDataToExport: function () {
			var paging = this.settings.gridFeatureOptions.paging,
				filtering = this.settings.gridFeatureOptions.filtering;
			this._dataToExport = [];

			if (paging === "currentPage" && this._getFeature("Paging")) {

				// H.A. 24 November 2015 fix 210114
				this._dataToExport = this._gridWidget.data("igGrid").dataSource.dataView();
			} else if (filtering !== "filteredRowsOnly") {
				this._dataToExport = this._gridData;
			} else {
				this._dataToExport = this._gridWidget.data("igGrid").dataSource._filteredData;
				if (this._dataToExport === undefined) {
					this._dataToExport = this._gridData;
				}
			}
		},

		_createExcelTableRegion: function () {
            // Z.K. counter?
			var i, regionRowsLength,
				counter = 0;

			regionRowsLength = this._dataToExport.length;

			for (i = 0; i < this._columnsToExport.length; i++) {
				if (this._columnsToExport[ i ].hidden) {
					counter++;
				}
			}

			this._worksheetRegion =
                new $.ig.excel.WorksheetRegion(this._worksheet, 0, 0,
                    regionRowsLength, this._columnsToExport.length - 1);
			this._worksheetTable = this._worksheetRegion.formatAsTable(true);
			this._worksheetTable.style(this._workbook.standardTableStyles(this.settings.tableStyle));
			this._woorksheetTableColumns = this._worksheetTable.columns();
		},

		_exportHeaders: function () {
			var columnIndex, gridColumn, noCancel,
				args = {},
				xlRow = this._worksheet.rows(this._xlRowIndex);

			this._headerFill = $.ig.excel.CellFill.createSolidFill(this._headersBackColor);

			xlRow.cellFormat().font().bold(1);

			for (columnIndex = 0; columnIndex < this._columnsToExport.length; columnIndex++) {
				gridColumn = this._columnsToExport[ columnIndex ];
				args = {
					headerText: gridColumn.headerText,
					columnKey: gridColumn.key,
					columnIndex: columnIndex, xlRow: xlRow
				};
				noCancel = this._handleEventCallback(this.callbacks.headerCellExporting, args);

				if (!noCancel) {
					return;
				}

				if (this.settings.gridStyling === "applied") {
					xlRow.getCellFormat(columnIndex).fill(this._headerFill);
					xlRow.getCellFormat(columnIndex).font().
                        colorInfo(new $.ig.excel.WorkbookColorInfo(this._headersForeColor));
				}

				this._exportCell(xlRow, gridColumn, columnIndex, args.headerText);
				this._handleEventCallback(this.callbacks.headerCellExported, args);
			}

			this._xlRowIndex++;
			this._worksheet.displayOptions().frozenPaneSettings().frozenRows(this._xlRowIndex);
			this._worksheet.displayOptions().panesAreFrozen(true);
		},

		_exportDataSegment: function () {
			var xlRow, dataRow, rowIndex, noCancel,
				args = {};
			this._exportedProps = [];
			this._subGridHeaderRowsIndices = [];
			this._colsIndices = [];

			var wasRowExportingCancelled = false;

			for (rowIndex = 0; rowIndex < this._dataToExport.length; rowIndex++) {
				xlRow = this._worksheet.rows(this._xlRowIndex);
				dataRow = this._dataToExport[ rowIndex ];

				args = {
					element: this._dataRows[ rowIndex ],
					rowId: this._getGridRowId(dataRow, rowIndex),
					xlRow: xlRow,
					grid: this._gridWidget
				};
				noCancel = this._handleEventCallback(this.callbacks.rowExporting, args);

				if (!noCancel) {
					wasRowExportingCancelled = true;
					continue;
				}

				this._exportDataSegmentRow(xlRow, dataRow, rowIndex);

				this._handleEventCallback(this.callbacks.rowExported, args);
				this._xlRowIndex++;

				// H.A. 24 November 2015 fix 210140
				if (this._gridType === "igHierarchicalGrid" && args.element !== undefined) {
					if (!this._grid.expanded(args.element) && this.settings.dataExportMode === "expandedRows") {
						continue;
					}
				}

				this._goThroughChildren(dataRow, xlRow, rowIndex, 1, args);
				this._exportedProps = [];
			}

			if (wasRowExportingCancelled) {
				this._recalculateTableEnd();
				this._shouldResizeTableRegion = true;
			}
		},

		_exportDataSegmentRow: function (xlRow, dataRow, rowIndex, indent, dataRowProp) {
			var gridColumn, columnLayout, dataCell, columnIndex, subgridColumnsNumber, y, prop,
				layoutColumns = [];

			if (indent === undefined) {
				layoutColumns = this._columnsToExport;
			} else {

				for (y = 0; y < this._allColumnLayouts.length; y++) {
				    if (this._isEqualColumnLayoutsAndDataRowProp(dataRowProp, this._allColumnLayouts[ y ])) {
						columnLayout = this._allColumnLayouts[ y ];
						if (columnLayout.columns !== undefined) {
						    this._setColumnsToExport(columnLayout, indent);
						    layoutColumns = this._childColumnsToExport;
						} else {
						    layoutColumns = undefined;
						}
					}
				}
			}

			// will be true only for igHierarchicalGrid
			if (indent !== undefined && this._gridType === "igHierarchicalGrid") {
				if (layoutColumns !== undefined && layoutColumns.length > 0) {
					for (columnIndex = 0; columnIndex < layoutColumns.length; columnIndex++) {
						gridColumn = layoutColumns[ columnIndex ];

						if (this._colsIndices.indexOf(columnIndex) === -1) {
							this._colsIndices.push(columnIndex);
						}

						dataCell = dataRow[ gridColumn.key ];
						this._exportCell(xlRow, gridColumn, columnIndex, dataCell, rowIndex, indent);
					}
				} else {
					subgridColumnsNumber = 0;
					for (prop in dataRow) {
						if (prop !== "__metadata" && prop !== "ig_pk") {
							if (typeof (dataRow[ prop ]) === "object") {
								continue;
							}
							subgridColumnsNumber++;
							if (this._colsIndices.indexOf(subgridColumnsNumber - 1) === -1) {
                                this._colsIndices.push(subgridColumnsNumber - 1);
                            }
							dataCell = dataRow[ prop ];
							this._exportCell(xlRow, gridColumn, subgridColumnsNumber - 1, dataCell, rowIndex, indent);
						}
					}
				}
			} else {

				for (columnIndex = 0; columnIndex < this._columnsToExport.length; columnIndex++) {
					gridColumn = this._columnsToExport[ columnIndex ];
					dataCell = dataRow[ gridColumn.key ];

					if (this.settings.gridStyling === "applied") {
						xlRow.getCellFormat(columnIndex).fill(this._cellFill[ xlRow.index() % 2 ]);
						xlRow.getCellFormat(columnIndex).font().
                            colorInfo(new $.ig.excel.WorkbookColorInfo(this._rowForeColor));
					}

					// when column is unbound and there is formula defined, then we already have the value of the cell in dataCell
					if (gridColumn.unbound && gridColumn.formula === undefined && dataCell === undefined) {
						// H.A. 09 May 2016 Fix #219038 - trying to export igHierarchicalGrid with unbound column results in an error
						dataCell =
                            this._gridWidget.data("igGrid").
                                getCellText(this._getGridRowId(dataRow, rowIndex), gridColumn.key);
					}

					// exporting complex objects will call the formatter or mapper function if available
					// if there is no formatter or mapper in grid, then the cell will display "[object object]" and dataCell will also return "[object object]"
					if (gridColumn.formatter !== undefined) {
						dataCell = gridColumn.formatter(dataCell);
					}

					// H.A. 03 June 2016 Fix #220186 - trying to export igHierarchicalGrid with unbound column results in an error
					if (gridColumn.mapper !== undefined) {
						dataCell = gridColumn.mapper(dataRow);
					}

					this._exportCell(xlRow, gridColumn, columnIndex, dataCell, rowIndex, indent);
				}
			}

		},

		_goThroughChildren: function (dataRow, xlRow, rowIndex, indent,
            args, parentDataRowProp, childRowIndex) {
			var responseDataKey, dataRowObj, shouldContinue, prop, i, y;

			//export child grid headers
			if (this._dataToExport.indexOf(dataRow) === -1 && this._gridType === "igHierarchicalGrid") {
				this._exportChildGridHeaders(dataRow, xlRow, indent, parentDataRowProp);
			}

			shouldContinue = true;

			// H.A. 24 November 2015 fix 210140
			if (args.element === undefined && this.settings.dataExportMode === "expandedRows") {
				shouldContinue = false;
			} else {
				if (this._gridType === "igHierarchicalGrid" &&
                    childRowIndex !== undefined &&
                    this.settings.dataExportMode !== "allRows") {
					if (!this._grid.expanded($(args.element).siblings("[data-container='true']").find("tr").
                        siblings("[role='row']")[ childRowIndex ]) &&
                        this.settings.dataExportMode === "expandedRows") {
						shouldContinue = false;
					}
				}
			}

			if (shouldContinue) {
				for (prop in dataRow) {
					if (dataRow[ prop ] !== null && dataRow.hasOwnProperty(prop) &&
                        typeof dataRow[ prop ] === "object" &&
                        prop !== "__metadata" && prop !== "ig_pk") {

						for (y = 0; y < this._allColumnLayouts.length; y++) {
						    if (this._isEqualColumnLayoutsAndDataRowProp(prop, this._allColumnLayouts[ y ])) {
								responseDataKey = this._allColumnLayouts[ y ].responseDataKey;
							}
						}

						if (responseDataKey !== undefined) {
							dataRowObj = dataRow[ prop ][ responseDataKey ];
						} else {
							dataRowObj = dataRow[ prop ];
						}

						if (dataRowObj !== undefined) {
							for (i = 0; i < dataRowObj.length; i++) {

								xlRow = this._worksheet.rows(this._xlRowIndex);
								xlRow.outlineLevel(indent);

								this._exportDataSegmentRow(xlRow, dataRowObj[ i ], rowIndex, indent, prop);
								this._handleEventCallback(this.callbacks.rowExported, args);

								this._xlRowIndex++;

								xlRow = this._worksheet.rows(this._xlRowIndex);

								this._goThroughChildren(dataRowObj[ i ], xlRow, rowIndex, indent + 1, args, prop, i);

								this._recalculateTableEnd();
								this._shouldResizeTableRegion = true;
							}
						}

					}
				}
			}

		},

		_getAllColumnLayouts: function (obj) {
			this._allColumnLayouts.push(obj);

			if (obj.columnLayouts !== undefined && obj.columnLayouts.length) {
				this._getAllColumnLayouts(obj.columnLayouts[ 0 ]);
			}

		},

		_exportChildGridHeaders: function (dataRow, xlRow, indent, parentDataRowProp) {
			var columnLayout, gridColumn, i, y, columnIndex, prop,
				subGridColumnIndex = 0,
				layoutColumns = [],
				keysCounter = 0,
				counter = 0;
			this._columnLayoutKeys = [];

			for (i = 0; i < this._allColumnLayouts.length; i++) {
			    if (this._isEqualColumnLayoutsAndDataRowProp(parentDataRowProp,
                    this._allColumnLayouts[ i ])) {
					columnLayout = this._allColumnLayouts[ i ];
				}
			}

		    // Z.K. May 4th, 2016 Fixing Bug #214917 - When responseDataKey for both parent and child definitions is set to empty string "" _dataToExport is undefined
			if (columnLayout !== undefined && columnLayout.columns !== undefined) {
			    this._setColumnsToExport(columnLayout, indent);
			    layoutColumns = this._childColumnsToExport;

				for (columnIndex = 0; columnIndex < layoutColumns.length; columnIndex++) {
					gridColumn = layoutColumns[ columnIndex ];

					if (this._exportedProps.indexOf(gridColumn.key) === -1) {

						if (counter === 0) {
							this._worksheet.rows().insert(xlRow.index() - 1, 1);
							xlRow = this._worksheet.rows(xlRow.index() - 2);
							xlRow.outlineLevel(indent - 1);
						}

						xlRow.cells(0).cellFormat().indent((indent - 1) * 2);
						xlRow.cells(columnIndex).cellFormat().alignment($.ig.excel.HorizontalCellAlignment.left);
						xlRow.cells(columnIndex).cellFormat().font().bold(1);

						// H.A fix bug #222576 - Column key instead of headerText is exported for child band headers.
						xlRow.setCellValue(columnIndex, gridColumn.headerText);

						this._exportedProps.push(gridColumn.key);
						if (this._subGridHeaderRowsIndices.indexOf(xlRow.index()) === -1) {
							this._subGridHeaderRowsIndices.push(xlRow.index());
						}

						if (counter === 0) {
							this._xlRowIndex++;
						}

						counter++;
					}
				}

			} else {

				for (prop in dataRow) {

					for (y = 0; y < this._allColumnLayouts.length; y++) {
					    if (this._isEqualColumnLayoutsAndDataRowProp(prop, this._allColumnLayouts[ y ])) {
							keysCounter++;
						}
					}

					if (keysCounter > 0) {
						continue;
					}

					if (this._exportedProps.indexOf(prop) === -1 && prop !== "ig_pk") {

						if (counter === 0) {
							this._worksheet.rows().insert(xlRow.index() - 1, 1);
							xlRow = this._worksheet.rows(xlRow.index() - 2);
							xlRow.outlineLevel(indent - 1);
						}

						subGridColumnIndex++;

						xlRow.cells(0).cellFormat().indent((indent - 1) * 2);
						xlRow.cells(subGridColumnIndex - 1).
                            cellFormat().
                            alignment($.ig.excel.HorizontalCellAlignment.left);
						xlRow.cells(subGridColumnIndex - 1).cellFormat().font().bold(1);

						xlRow.setCellValue(subGridColumnIndex - 1, prop);

						this._exportedProps.push(prop);
						if (this._subGridHeaderRowsIndices.indexOf(xlRow.index()) === -1) {
							this._subGridHeaderRowsIndices.push(xlRow.index());
						}

						if (counter === 0) {
							this._xlRowIndex++;
						}

						counter++;
					}
				}

			}
		},

	    // A.K. August 8th, 2016 Fixing Bug #223981 - igGridExcelExporter exports child grids' hidden columns always, even if hiding: "visibleColumnsOnly"
        _isEqualColumnLayoutsAndDataRowProp: function (dataRowProp, obj) {
			if (dataRowProp === obj.childrenDataProperty) {
				return true;
			} else if (dataRowProp === obj.key) {
				return true;
			} else {
				return false;
			}
		},

		_exportCell: function (xlRow, gridColumn, columnIndex, cellValue, rowIndex, indent) {
			var noCancel,
				args = {};

			// for hierarchical grid only args.CellValue will be available
			if (gridColumn) {
				args = {
					columnKey: gridColumn.key,
					columnIndex: columnIndex,
					xlRow: xlRow,
					cellValue: cellValue,
					grid: this._gridWidget
				};

				if (rowIndex !== undefined) {
					args.rowId = this._getGridRowId(this._dataToExport[ rowIndex ], rowIndex);
				}
			} else {
				args = {
					cellValue: cellValue,
					xlRow: xlRow,
					grid: this._gridWidget
				};
			}

			noCancel = this._handleEventCallback(this.callbacks.cellExporting, args);

			if (!noCancel) {
				return;
			}

			if (indent !== undefined && columnIndex === 0) {
				xlRow.cells(columnIndex).cellFormat().indent(indent * 2);
				xlRow.cells(columnIndex).cellFormat().alignment($.ig.excel.HorizontalCellAlignment.left);
				xlRow.setCellValue(columnIndex, args.cellValue);
			} else {
				xlRow.setCellValue(columnIndex, args.cellValue);
			}

			this._handleEventCallback(this.callbacks.cellExported, args);
		},

		_getGridRowId: function (dataRow, rowIndex) {
			var rowId;

			if (this._grid.options.primaryKey !== null) {
				rowId = dataRow[ this._grid.options.primaryKey ];
			} else {
				rowId = rowIndex;
			}

			return rowId;
		},

		_recalculateTableEnd: function (cols) {
			var xlRowIndex = this._xlRowIndex - 1;
			cols = cols ? cols : this._worksheetTable.columns().count();
			this._tableRegionEndCellReference =
                $.ig.excel.WorksheetCell.getCellAddressString(
                    this._worksheet.rows().item(xlRowIndex),
                    cols - 1, $.ig.excel.CellReferenceMode.a1, false).toString();
		},

		_resizeTableRegion: function () {
			var tableRegionStartCellReference =
                $.ig.excel.WorksheetCell.getCellAddressString(
                    this._worksheet.rows().item(0), 0,
                    $.ig.excel.CellReferenceMode.a1, false).toString();

			if (tableRegionStartCellReference.
                    substring(tableRegionStartCellReference.lastIndexOf("$") + 1) ===
                this._tableRegionEndCellReference.
                    substring(this._tableRegionEndCellReference.lastIndexOf("$") + 1)) {
				var nextRowIndex =
                    parseInt(this._tableRegionEndCellReference.
                        substring(this._tableRegionEndCellReference.lastIndexOf("$") + 1)) + 1;
				this._tableRegionEndCellReference =
                    this._tableRegionEndCellReference.
                        substring(0, this._tableRegionEndCellReference.lastIndexOf("$") + 1) +
                        nextRowIndex.toString();
			}

			this._worksheetTable.resize(
                tableRegionStartCellReference +
                ":" +
                this._tableRegionEndCellReference);
		},

		_exportFeatures: function () {
			var featureName, i,
				features = this.settings.gridFeatureOptions;

			for (i = 0; i < this._grid.options.features.length; i++) {
				featureName = this._grid.options.features[ i ].name;

				if (features[ featureName.toLowerCase() ] === undefined) {
					continue;
				}

				// Paging is handled inside exportDataSegment, Hiding is implemented in _calculateColumnsSize
				if (featureName !== "Paging" && featureName !== "Hiding") {
					if (features[ featureName.toLowerCase() ] !== "none") {
						this[ "_export" + featureName ]();
					}
				}
			}
		},

		// The following method gets the feature by case insensitive name
		_getFeature: function (name) {
			var featureIndex,
				features = this._grid.options.features;

			for (featureIndex = 0; featureIndex < features.length; featureIndex++) {
				var feature = features[ featureIndex ];

				if (feature.name.toLowerCase() === name.toLowerCase()) {
					return feature;
				}
			}

			return null;
		},

		_exportSorting: function () {
			var columnToSort, sortDirection, i, j,
				orderedSortConditions = this._createSortingConditions(),

				 // H.A. 25 November 2015 Fix #210105
				sortExpressions = this._gridWidget.data("igGrid").dataSource.settings.sorting.expressions;

			for (i = 0; i < sortExpressions.length; i++) {

				for (j = 0; j < this._columnsToExport.length; j++) {

					if (sortExpressions[ i ].fieldName === this._columnsToExport[ j ].key) {
						columnToSort = this._worksheetTable.columns().item(j);
						sortDirection = sortExpressions[ i ].dir;

						if (columnToSort !== undefined && sortDirection !== undefined) {
							this._worksheetTable.sortSettings().sortConditions().
                                add(columnToSort, orderedSortConditions[ sortDirection ]);
						}
					}
				}
			}
		},

		_createSortingConditions: function () {
			var orderedSortConditions = {
				"asc": new $.ig.excel.OrderedSortCondition($.ig.excel.SortDirection.ascending),
				"desc": new $.ig.excel.OrderedSortCondition($.ig.excel.SortDirection.descending)
			};

			return orderedSortConditions;
		},

		_exportFiltering: function () {
			if (this.settings.gridFeatureOptions.filtering === "filteredRowsOnly") {
				return;
			}

			var columnToFilter, gridColumn, filtCond, filtExpr, dayStart, dayEnd, i, j,
				filteringConditions = this._createFilteringConditions(),

				// H.A. 25 November 2015 Fix #210105 - Export igHierarchicalGrid with Sorting or Paging results in error
				filtExpressions = this._gridWidget.data("igGrid").dataSource.settings.filtering.expressions;

			if (filtExpressions.length > 0) {
				for (i = 0; i < filtExpressions.length; i++) {

					for (j = 0; j < this._columnsToExport.length; j++) {
						// H.A. 14 June 2016 Fix #220461 - Error "infragistics.excel.js:25 Uncaught Invalid Enum Argument" is thrown when custom filtering is applied
						if (this._columnsToExport[ j ].key === filtExpressions[ i ].fieldName &&
                            this.settings.skipFilteringOn.
                                indexOf(filtExpressions[ i ].fieldName) < 0) {
							gridColumn = this._columnsToExport[ j ];
							columnToFilter = this._worksheetTable.columns().item(j);

							filtCond = filtExpressions[ i ].cond;
							filtExpr = filtExpressions[ i ].expr;

							if (filtCond === "false" || filtCond === "true") {
								filtExpr = filtCond;
							}

							switch (filtCond) {
								case "on":
									columnToFilter.applyFixedValuesFilter(
                                        true,
                                        $.ig.excel.CalendarType.gregorian,
                                        [ new $.ig.excel.FixedDateGroup(
                                            $.ig.excel.FixedDateGroupType.day, filtExpr) ]);
									break;
								case "notOn":
									dayStart = new Date(filtExpr.setHours(0));
									dayEnd = new Date(filtExpr.setHours(24));
									columnToFilter.applyCustomFilter(
                                        new $.ig.excel.CustomFilterCondition(
                                            filteringConditions.before, dayStart),
                                            new $.ig.excel.CustomFilterCondition(
                                                filteringConditions.after, dayEnd),
                                                $.ig.excel.ConditionalOperator.or);
									break;
								case "after":
									columnToFilter.applyCustomFilter(
                                        new $.ig.excel.CustomFilterCondition(
                                            filteringConditions[ filtCond ], filtExpr));
									break;
								case "before":
									columnToFilter.applyCustomFilter(
                                        new $.ig.excel.CustomFilterCondition(
                                            filteringConditions[ filtCond ], filtExpr));
									break;
								case "today":
									columnToFilter.applyRelativeDateRangeFilter(
                                        $.ig.excel.RelativeDateRangeOffset.current,
                                        $.ig.excel.RelativeDateRangeDuration.day);
									break;
								case "yesterday":
									columnToFilter.applyRelativeDateRangeFilter(
                                        $.ig.excel.RelativeDateRangeOffset.previous,
                                        $.ig.excel.RelativeDateRangeDuration.day);
									break;
								case "thisMonth":
									columnToFilter.applyRelativeDateRangeFilter(
                                        $.ig.excel.RelativeDateRangeOffset.current,
                                        $.ig.excel.RelativeDateRangeDuration.month);
									break;
								case "lastMonth":
									columnToFilter.applyRelativeDateRangeFilter(
                                        $.ig.excel.RelativeDateRangeOffset.previous,
                                        $.ig.excel.RelativeDateRangeDuration.month);
									break;
								case "nextMonth":
									columnToFilter.applyRelativeDateRangeFilter(
                                        $.ig.excel.RelativeDateRangeOffset.next,
                                        $.ig.excel.RelativeDateRangeDuration.month);
									break;
								case "thisYear":
									columnToFilter.applyRelativeDateRangeFilter(
                                        $.ig.excel.RelativeDateRangeOffset.current,
                                        $.ig.excel.RelativeDateRangeDuration.year);
									break;
								case "lastYear":
									columnToFilter.applyRelativeDateRangeFilter(
                                        $.ig.excel.RelativeDateRangeOffset.previous,
                                        $.ig.excel.RelativeDateRangeDuration.year);
									break;
								case "nextYear":
									columnToFilter.applyRelativeDateRangeFilter(
                                        $.ig.excel.RelativeDateRangeOffset.next,
                                        $.ig.excel.RelativeDateRangeDuration.year);
									break;
								case "null":
									if (gridColumn.dataType === "number" ||
                                        gridColumn.dataType === "date" ||
                                        gridColumn.dataType === undefined) {
										columnToFilter.applyCustomFilter(
                                            new $.ig.excel.CustomFilterCondition(
                                                $.ig.excel.ExcelComparisonOperator.equals, ""));
									} else {
										columnToFilter.applyCustomFilter(
                                            new $.ig.excel.CustomFilterCondition(
                                                $.ig.excel.ExcelComparisonOperator.notEqual, "*"));
									}
									break;
								case "notNull":
									if (gridColumn.dataType === "number" ||
                                        gridColumn.dataType === "date" ||
                                        gridColumn.dataType === undefined) {
										columnToFilter.applyCustomFilter(
                                            new $.ig.excel.CustomFilterCondition(
                                                $.ig.excel.ExcelComparisonOperator.notEqual, ""));
									} else {
										columnToFilter.applyCustomFilter(
                                            new $.ig.excel.CustomFilterCondition(
                                                $.ig.excel.ExcelComparisonOperator.equals, "*"));
									}
									break;
								case "empty":
									columnToFilter.applyCustomFilter(
                                        new $.ig.excel.CustomFilterCondition(
                                            $.ig.excel.ExcelComparisonOperator.equals, ""));
									break;
								case "notEmpty":
									columnToFilter.applyCustomFilter(
                                        new $.ig.excel.CustomFilterCondition(
                                            $.ig.excel.ExcelComparisonOperator.notEqual, ""));
									break;
								default:

									// H.A. 14 June 2016 Fix #220461 - Error "infragistics.excel.js:25 Uncaught Invalid Enum Argument" is thrown when custom filtering is applied
									// this behavior is a limitation, the fix throws a custom exception with enough information
									// to prompt the user to use the column
									if (filteringConditions[ filtCond ] !== undefined) {
										columnToFilter.applyCustomFilter(
                                            new $.ig.excel.CustomFilterCondition(
                                                filteringConditions[ filtCond ], filtExpr));
									} else {
										throw "Custom filtering conditions cannot be applied to the exported worksheet. " +
                                            "Please use the skipFilteringOn property to " +
                                                "define columns that filtering will skip";
									}
							}

						}
					}
				}
			}

		},

		_createFilteringConditions: function () {
			var filteringConditions = {
				"equals": $.ig.excel.ExcelComparisonOperator.equals,
				"doesNotEqual": $.ig.excel.ExcelComparisonOperator.notEqual,
				"greaterThan": $.ig.excel.ExcelComparisonOperator.greaterThan,
				"greaterThanOrEqualTo": $.ig.excel.ExcelComparisonOperator.greaterThanOrEqual,
				"lessThan": $.ig.excel.ExcelComparisonOperator.lessThan,
				"lessThanOrEqualTo": $.ig.excel.ExcelComparisonOperator.lessThanOrEqual,
				"null": $.ig.excel.ExcelComparisonOperator.equals,
				"notNull": $.ig.excel.ExcelComparisonOperator.notEqual,
				"empty": $.ig.excel.ExcelComparisonOperator.equals,
				"notEmpty": $.ig.excel.ExcelComparisonOperator.notEqual,
				"startsWith": $.ig.excel.ExcelComparisonOperator.beginsWith,
				"endsWith": $.ig.excel.ExcelComparisonOperator.endsWith,
				"contains": $.ig.excel.ExcelComparisonOperator.contains,
				"doesNotContain": $.ig.excel.ExcelComparisonOperator.doesNotContain,
				"after": $.ig.excel.ExcelComparisonOperator.greaterThan,
				"before": $.ig.excel.ExcelComparisonOperator.lessThan,
				"notOn": $.ig.excel.ExcelComparisonOperator.notEqual,
				"false": $.ig.excel.ExcelComparisonOperator.equals,
				"true": $.ig.excel.ExcelComparisonOperator.equals
			};
			return filteringConditions;
		},

		_exportSummaries: function () {
			var i, columnIndex, column, summaryIndex, summary, summaryType,
                summariesForColumn, dataStartRowIndex,
                formatString, xlRow, columnReference, noCancel,
				args = {},
				argumentSeparator =
                    this._getSummariesArgumentSeparator(
                        $.ig.CultureInfo.prototype.
                        currentCulture().numberFormat().numberDecimalSeparator()),
				dataEndRowIndex = this._xlRowIndex - 1,
				summaries = this._gridWidget.igGridSummaries("summaryCollection"),
				worksheet = this._worksheet;

			if (this._xlRowIndex === 1) {
				return;
			}

			for (i = 0; i < this._columnsToExport.length; i++) {

				columnIndex = i;
				column = this._columnsToExport[ columnIndex ];

				//To ensure potential summaries for undefined columns are not exported
				if (summaries [ column.key ] === undefined) {
					continue;
				}

				summariesForColumn = summaries[ column.key ];
				dataStartRowIndex = 1;

				// To get the range string in the form of "A1:A45", we can construct a WorksheetRegion instance
				// and then get its string representation in the A1 reference mode, without the sheet name, and
				// with relative row and column identifiers. This is the range to which each summary in the
				// current column will apply.
				columnReference = new $.ig.excel.WorksheetRegion(this._worksheet, dataStartRowIndex,
                    columnIndex, dataEndRowIndex, columnIndex)
					.toString($.ig.excel.CellReferenceMode.a1, false, true, true);

				// Export each summary cell applied to the current column
				for (summaryIndex = 0; summaryIndex < summariesForColumn.length; summaryIndex++) {
					summary = summariesForColumn[ summaryIndex ];
					switch (summary.type.toLowerCase()) {
						case "avg":
							summaryType = 101;
							formatString = '"' + $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelAvg + ' = "0.00';
							break;
						case "min":
							summaryType = 105;
							formatString = '"' + $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelMin + ' = "0.00';
							break;
						case "max":
							summaryType = 104;
							formatString = '"' + $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelMax + ' = "0.00';
							break;
						case "count":
							summaryType = 103;
							formatString = '"' +
                                $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelCount +
                                ' = "0.00';
							break;
						case "sum":
							summaryType = 109;
							formatString = '"' + $.ig.GridSummaries.locale.defaultSummaryRowDisplayLabelSum + ' = "0.00';
							break;
					}

					args = {
						headerText: this._grid.options.columns[ columnIndex ].headerText,
						columnKey: this._grid.options.columns[ columnIndex ].key,
						columnIndex: columnIndex,
						summary: summariesForColumn[ summaryIndex ],
						xlRowIndex: this._xlRowIndex
					};
					noCancel = this._handleEventCallback(this.callbacks.summaryExporting, args);

					if (!noCancel) {
						return;
					}

					if (summaryType) {
						xlRow = worksheet.rows(this._xlRowIndex + summaryIndex);
						xlRow.applyCellFormula(columnIndex,
                            "=SUBTOTAL(" + summaryType + argumentSeparator + columnReference + ")");
						xlRow.getCellFormat(columnIndex).formatString(formatString);

						this._handleEventCallback(this.callbacks.summaryExported, args);
					}
				}
			}
		},

		_getSummariesArgumentSeparator: function (decimalSeparator) {
			if (decimalSeparator === ",") {
				return ";";
			}

			return ",";
		},

		_exportColumnFixing: function () {
			var i,
				numberOfFrozenCols = 0;

			for (i = 0; i < this._columnsToExport.length; i++) {
				if (this._columnsToExport[ i ].fixed) {
					numberOfFrozenCols++;
				}
			}

			this._worksheet.displayOptions().frozenPaneSettings().frozenColumns(numberOfFrozenCols);
		},

		_calculateColumnsSize: function () {

			// H.A. 04 January 2016 Fix #211639 - Columns in excel file are collapsed when ExcelExporter exports grid which has no data records.
			var i, col, colWidth, column, colElemId,
				gridTable = this._gridWidget.find("#" + this._gridWidget.attr("id") + "_table"),
				gridFeatureOptions = this.settings.gridFeatureOptions;

			if (gridTable.length === 0) {
				gridTable = this._gridWidget;
			}

			for (i = 0; i < this._columnsToExport.length; i++) {
				column = this._columnsToExport[ i ];

				if ((this._getFeature("Hiding") && gridFeatureOptions.hiding === "none") ||
					(column.fixed && !column.hidden)) {
					col = this._worksheet.columns().item(i);
					colWidth = column.width;

					if (colWidth && !column.hidden) {
						if (!isNaN(colWidth)) {
							col.setWidth(colWidth, $.ig.excel.WorksheetColumnWidthUnit.pixel);
						} else if (isNaN(colWidth)) {
							var widthSize = colWidth.match(/\d+/)[ 0 ];
							if (colWidth.contains("px")) {
								col.setWidth(widthSize, $.ig.excel.WorksheetColumnWidthUnit.pixel);
							} else {
								col.setWidth((widthSize / 100) * this._gridWidget.width(),
                                    $.ig.excel.WorksheetColumnWidthUnit.pixel);
							}
						}
					}
				} else {
					col = this._worksheet.columns().item(i);
					colElemId = (gridTable.attr("id") + "_" + column.key);

					// H.A. 04 January 2016 Fix #211639
					colWidth = gridTable.find('[aria-describedby="' + colElemId + '"]').length > 0 ?
                        gridTable.find('[aria-describedby="' + colElemId + '"]').width() :
                        this._gridWidget.igGrid("headersTable").
                            find('[id="' + colElemId + '"]').width();
					col.setWidth(colWidth, $.ig.excel.WorksheetColumnWidthUnit.pixel);
				}
			}
		},

		_getCellFill: function () {
			this._cellFill = [];

			this._cellFill.push($.ig.excel.CellFill.createSolidFill(this._altRowColor));
			this._cellFill.push($.ig.excel.CellFill.createSolidFill(this._notAltRowColor));

			return this._cellFill;
		},

		_styleChildGridHeaders: function () {
			var i, y, tableEndColumn, lastRow;

			if (this._gridType === "igHierarchicalGrid" &&
                this._colsIndices &&
                this._colsIndices.length > 0) {

				// H.A. 07 December 2015 fix 210768
				tableEndColumn = this._colsIndices.length > this._columnsToExport.length ?
                    this._colsIndices.length :
                    this._columnsToExport.length;
				lastRow = this._worksheetRegion.lastRow() + 1;
				this._recalculateTableEnd(tableEndColumn);
				this._resizeTableRegion();

				if (this.settings.gridStyling === "applied") {
					for (i = 1; i < lastRow; i++) {
						for (y = 0; y < tableEndColumn; y++) {
							if (this._subGridHeaderRowsIndices.indexOf(i) !== -1) {
								this._worksheet.rows(
                                    this._subGridHeaderRowsIndices[
                                        this._subGridHeaderRowsIndices.indexOf(i) ]).
                                        getCellFormat(y).fill(this._headerFill);
								this._worksheet.rows(
                                    this._subGridHeaderRowsIndices[
                                        this._subGridHeaderRowsIndices.indexOf(i) ]).
                                            getCellFormat(y).
                                            font().
                                            colorInfo(new $.ig.excel.WorkbookColorInfo(
                                                this._headersForeColor));
							} else {
								this._worksheet.rows(i).getCellFormat(y).fill(this._cellFill[ i % 2 ]);
								this._worksheet.rows(i).getCellFormat(y).font().colorInfo(
									new $.ig.excel.WorkbookColorInfo(this._rowForeColor));
							}
						}
					}
				}
			}
		},

		_saveWorkbook: function () {
			var self = this;

			setTimeout(function () {

				// Finally, save the workbook and create a link so the .xlsx document can be downloaded
				self._workbook.save({ type: "blob" }, function (data) {
					saveAs(data, self.settings.fileName + ".xlsx");
					self._handleEventCallback(self.callbacks.success, data);
				}, function (err) {
					self._handleEventCallback(self.callbacks.error, err);
				});
			}, 1);
		},

		_handleEventCallback: function (callback, args) {
			if (!$.isFunction(callback)) {
				return true;
			}

			var noCancel = callback(this, args);

			if (noCancel === false) {
				return false;
			}

			return true;
		}
	});

	$.ig.GridExcelExporter.exportGrid = function (grid, userSettings, userCallbacks) {
		/* Exports the provided igGrid to Excel document.
			paramType="object" Grid to be exported.
			paramType="object" Settings for exporting the grid.
			paramType="object" Callbacks for the events.
		*/
		var exp = new $.ig.GridExcelExporter();
		exp.exportGrid(grid, userSettings, userCallbacks);
		exp = null;
	};
}(jQuery));
