
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
 *	infragistics.ui.grid.paging.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igTreeGridPaging widget. The widget is pluggable to the element where the treegrid is instantiated and the actual igTreeGrid object doesn't know about this.
		the paging widget just attaches its functionality to the treegrid
		igTreeGridPaging is extending igGrid Paging
	*/
	$.widget("ui.igTreeGridPaging", $.ui.igGridPaging, {
		css: {
			/* classes applied to the paging context row(<TR>) */
			contextRow: "ui-igtreegrid-contextrow",
			/* classes applied to the element showing context row icon */
			contextRowIcon: "ui-igtreegrid-contextrow-icon ui-icon ui-icon-bookmark",
			/* classes applied to the element showing context row content */
			contextRowContent: "ui-igtreegrid-contextrow-content",
			/* classes applied to the element that holds context row container(holding context row SPAN and context row icon SPAN) */
			contextRowTextContainer: "ui-igtreegrid-contextrow-container"
		},
		/*type="number" defines minimum count of visible data records in the data source so loading message to be shown in the context row(in case of option "contextRowMode" is not set to "none"). Loading message is visible while retrieving/rendering information about ancestors of the first record in the page. If the dataSource has a few records and this option is set to a small value - e.g. 10, then go to page could cause flickering of the context row.*/
		rowsToShowLoadingMessage: 300000,
		events: {
			/* cancel="true" Event fired before rendering context row content.
			Return false in order to cancel this event.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igTreeGridPaging.
			Use ui.dataRow to get reference to the first data row. Null if there are no records.
			Use ui.currentPageIndex to get current page index.
			Use ui.contextRowMode to get the current context row mode.
			*/
			contextRowRendering: "contextRowRendering",
			/* Event fired context row content is rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igTreeGridPaging.
			Use ui.dataRow to get reference to the first data row. Null if there are no records.
			Use ui.currentPageIndex to get current page index.
			Use ui.parentRows to get array of the parent rows(of the ui.dataRow). If the dataRow is null(for instance filter is applied and no records are shown) then it is empty array. Otherwise it contains all ancestors ordered by level(including the current dataRow) - the first item is root level, the last array item is the current ui.dataRow
			Use ui.contextRowMode to get the current context row mode.
			*/
			contextRowRendered: "contextRowRendered"
		},
		options: {
			/* type="rootLevelOnly|allLevels" Sets gets paging mode.
			rootLevelOnly type="string" Only pages records at the root of the tree grid are displayed.
			allLevels type="string" includes all visible records in paging.*/
			mode: "rootLevelOnly",
			/* type="none|parent|breadcrumb" When data flows to the next page there are a couple of different modes that can help communicate the context of a leaf level row. When mode option is "rootLevelOnly" then the context row always shows the value of the contextRowRootText option.
			none type="string" Does not render the contextual row
			parent type="string" Renders a read-only representation of the immediate parent row
			breadcrumb type="string" Renders a read-only breadcrumb trail representing the full path through all ancestors
			*/
			contextRowMode: "none",
			/* type="string" Sets/gets the text message shown while loading content of the context row(while processing breadcrumb/immediate parent row). It is set via $.html(). If set to null loading message is not shown.*/
			contextRowLoadingText: $.ig.TreeGridPaging.locale.contextRowLoadingText,
			/* type="string" Sets/gets the content of the context row when the first record in the page is root(hasn't ancestors) record. It is set via $.html()*/
			contextRowRootText: $.ig.TreeGridPaging.locale.contextRowRootText,
			/* type="string" Sets/gets the column key of ancestor to be shown in the breadcrumb trail. It is used only when contextRowMode is breadcrumb */
			breadcrumbKey: null,
			/* type="string" Sets/gets (it is set via  $.html()) delimiter between ancestors in the breadcrumb trail. It is used only when contextRowMode is breadcrumb */
			breadcrumbDelimiter: " &gt; ",
			/* type="function|string" Reference to the (or name of )function, called before rendering context row content(rendering loading message/bread crumb/parent row). The function takes 4 arguments- dataRow, $textArea- jQuery representation of the text area of the context row(when mode is loading/breadcrumb then it is <span /> otherwise <tr />), array of parent rows and context mode - "loading"|"breadcrumb"|"parent". When the function returns string it is used as html set in $textArea. If the function does not return result or returns false/empty string then rendering of the content of the context row should be handled by the developer(similar to canceling rendering of context row content). */
			renderContextRowFunc: null
		},
		_create: function () {
			this.element.data(
				$.ui.igGridPaging.prototype.widgetName,
				this.element.data($.ui.igTreeGridPaging.prototype.widgetName)
			);
			$.ui.igGridPaging.prototype._create.apply(this, arguments);
		},
		/* M.H. 26 June 2015 Fix for bug 201835: When filtering and paging are enabled for TreeGrid and filter and after that change the page pager label is not correct */
		_getDSLocalRecordsCount: function () {
			/* override _getDataSourceRecordsCount(from grid.paging) */
			if (this.grid.dataSource._filter && this.options.mode === "allLevels") {
				return this.grid.dataSource.totalLocalRecordsCount();
			}
			return $.ui.igGridPaging.prototype._getDSLocalRecordsCount.apply(this, arguments);
		},
		destroy: function () {
			/*destroys the igTreeGridPaging feature by removing all elements in the pager area, unbinding events, and resetting data to discard data filtering on paging*/
			$.ui.igGridPaging.prototype.destroy.apply(this, arguments);
			this.element.removeData($.ui.igGridPaging.prototype.widgetName);
		},
		_dataRendered: function () {
			var $tr;
			$.ui.igGridPaging.prototype._dataRendered.apply(this, arguments);
			switch (this.options.contextRowMode) {
				case "breadcrumb":
				case "parent":
					this._renderContextRow(this.options.contextRowMode);
					break;
					/*case "none": */
				default://none
					$tr = this.getContextRow();
					if ($tr.length) {
						$tr.remove();
						this.grid._initializeHeights();
					}
					break;
			}
		},
		_renderContextRow: function (mode) {
			var grid = this.grid, dv = grid.dataSource.flatDataView(), dataRow, noCancel, eArgs, self = this;
			if (dv && dv[ 0 ]) {
				dataRow = dv[ 0 ];
			}
			eArgs = {
				owner: this,
				dataRow: dataRow,
				currentPageIndex: this.options.currentPageIndex,
				contextRowMode: mode
			};
			noCancel = this._trigger(this.events.contextRowRendering, null, eArgs);
			if (noCancel) {
				this._renderLoading(dataRow);
				setTimeout(function () {
					if (mode === "breadcrumb") {
						self._renderBreadcrumb(dataRow);
					} else if (mode === "parent") {
						self._renderParentRow(dataRow);
					}
				}, 10);
			}
		},
		getContextRow: function () {
			/* Get jQuery representation of of the context row. It is rendered in the header. If there isn't such element - creates it.
			returnType="object" jQuery representation of the context row
			*/
			var $thead = this.grid.headersTable().children("thead"),
				$row = this.grid.headersTable().find(">thead>tr[data-treegrid-contextrow]");
			if ($row.length) {
				return $row;
			}
			$row = $('<tr data-treegrid-contextrow="true" data-skip="true"></tr>').appendTo($thead);
			$row.addClass(this.css.contextRow);
			return $row;
		},
		getContextRowTextArea: function () {
			/* Get jQuery representation of element that holds text area of the context row. If there isn't such element - creates it.
			returnType="object" jQuery representation of the context row text area
			*/
			var $thead = this.grid.headersTable().children("thead"), $tr, $td, $container,
				$colspan, colspan = 0;
			$container = $thead.find("[data-treegrid-contextrow-content]");
			if ($container.length) {
				return $container;
			}
			$tr = this.getContextRow();
			$tr.empty();
			/* find colspan */
			$colspan = $thead.closest("table").children("colgroup");
			$colspan.find("col").each(function () {
				var $col = $(this), cs;
				cs = parseInt($col.attr("colspan"), 10);
				if (isNaN(cs)) {
					cs = 1;
				}
				colspan += cs;
			});
			$td = $("<td></td>")
					.attr("colspan", colspan)
					.appendTo($tr);
			$container = $('<div class="' + this.css.contextRowTextContainer +
								'" data-treegrid-contextrow-container="1">' +
							'<span class="' + this.css.contextRowIcon + '"></span>' +
							'<span class="' + this.css.contextRowContent + '" data-treegrid-contextrow-content></span>' +
						"</div>").appendTo($td);
			return this.getContextRowTextArea();
		},
		_callRenderContextRowFunc: function (dataRow, $textArea, parents, mode) {
			/* call renderContextRowFunc(if set as function reference or name of the function) */
			/* if returned result is string then it is used as html set in $textArea */
			var func = this.options.renderContextRowFunc, f;
			if ($.type(func) === "function") {
				f = func;
			} else if (window[ func ] && typeof window[ func ] === "function") {
				f = window[ func ];
			}
			if (f) {
				/* M.H. 4 June 2015 Fix for bug 194075: renderContextRowFunc option does not work properly */
				return f(dataRow, $textArea, parents, mode);
			}
		},
		/* renders loading message - it is used dataRow as argument for renderContextRowFunc */
		_renderLoading: function (dataRow) {
			/* M.H. 4 June 2015 Fix for bug 193468: When you collapse or expand a row the context row rerenders.
			* loading message should be shown when getting breadcrumb/parent record takes more time(over 250-300ms) - otherwise it will be shown flickering.(fix for bug 193468)
			* So possible solution is to put _renderLoading in setTimeout/setInterval but in this case
			* after calling _renderBreadcrumb/_renderParentRow the function in setTimeout/setInterval is not called(JavaScript is single threaded, so none of setTimeout/setInterval can execute in parallel) */
			var self = this, $textArea, o = this.options, grid = this.grid, html;
			/* if we call getContextRowTextArea here there is flickering */
			if (o.renderContextRowFunc) {
				/* M.H. 5 June 2015 Fix for bug 193468: When you collapse or expand a row the context row rerenders. */
				$textArea = self.getContextRowTextArea();
				html = self._callRenderContextRowFunc(dataRow, $textArea, null, "loading");
				if (!html) {
					return;
				}
			}
			if (grid.dataSource._flatVisibleData &&
					grid.dataSource._flatVisibleData.length >= this.rowsToShowLoadingMessage) {
				if (!html) {
					/* M.H. 5 June 2015 Fix for bug 193468: When you collapse or expand a row the context row rerenders. */
					if (!$textArea) {
						$textArea = self.getContextRowTextArea();
					}
					$textArea.html(o.contextRowLoadingText);
				}
				self.grid._initializeHeights();
			}
		},
		_renderRootRecord: function () {
			/* renders context row at ROOT level(first record in the page hasn't ancestors) - with text taken from option contextRowRootText */
			this.getContextRowTextArea()
				.html(this.options.contextRowRootText);
		},
		_renderBreadcrumb: function (dataRow) {
			var i, $text, ds = this.grid.dataSource, parents, parentsLen, row,
				o = this.options, eArgs,
				breadcrumbKey = o.breadcrumbKey,
				html = "";
			parents = ds.getParentRowsForRow(dataRow);
			$text = this.getContextRowTextArea();
			if (o.renderContextRowFunc) {
				html = this._callRenderContextRowFunc(dataRow, $text, parents, "breadcrumb");
				if (!html) {
					return;
				}
			}
			/* if renderContextRowFunc returns result set it as html value of the text area. */
			if (html) {
				$text.html(html);
			} else {
				if ($.type(parents) === "array") {
					parentsLen = parents.length;
					if (breadcrumbKey === null || breadcrumbKey === undefined) {
						breadcrumbKey = this.grid.options.primaryKey;
					}
					/* root */
					if (parentsLen <= 1) {
						this._renderRootRecord();
					} else {
						for (i = 0; i < parentsLen; i++) {
							row = parents[ i ].row;
							if (row[ breadcrumbKey ] === undefined) {
								continue;
							}
							if (i) {
								html += o.breadcrumbDelimiter;
							}
							html += row[ breadcrumbKey ];
						}
						$text.html(html);
					}
				} else {
					this._renderRootRecord();
				}
			}
			eArgs = {
				owner: this,
				dataRow: dataRow,
				currentPageIndex: this.options.currentPageIndex,
				parentRows: parents,
				contextRowMode: "breadcrumb"
			};
			this.grid._initializeHeights();
			this._trigger(this.events.contextRowRendered, null, eArgs);
		},
		_renderParentRow: function (dataRow) {
			var parentsLen, $span, eArgs, html, parents, lastRec,
				$tr = this.getContextRow(),
				ds = this.grid.dataSource,
				o = this.options;
			parents = ds.getParentRowsForRow(dataRow);
			if (o.renderContextRowFunc) {
				html = this._callRenderContextRowFunc(dataRow, $tr, parents, "parent");
				if (!html) {
					return;
				}
			}
			/* if renderContextRowFunc returns result sets it as html */
			if (html) {
				$tr.html(html);
			} else {
				if ($.type(parents) === "array" || !parents.length) {
					parentsLen = parents.length;
					/* root */
					if (parentsLen === 1) {
						this._renderRootRecord();
					} else {
						lastRec = parents[ parentsLen - 2 ];
						/*M.K. Fix for bug 216412: The paging context row has an expansion indicator rendered in it */
						lastRec = $.extend(true, {}, parents[ parentsLen - 2 ]);
						if (lastRec && lastRec.row) {
							lastRec.row.childData = null;
							lastRec.row[ this.grid.options.dataSourceSettings.propertyDataLevel ] = 0;
							html = this.grid._renderRecord(lastRec.row);
							html = html.substr(html.indexOf(">") + 1);
							html = html.substr(0, html.lastIndexOf("</tr"));
							$tr.html(html);
							/* get that holds expand cell indicator and replace its class attribute */
							$span = $tr.find("[data-expandcell-indicator],[data-shift-container]");
							if ($span.length) {
								$span.attr("class", this.css.contextRowIcon);
							}
						}
					}
				} else {
					this._renderRootRecord();
				}
			}
			eArgs = {
				owner: this,
				dataRow: dataRow,
				currentPageIndex: this.options.currentPageIndex,
				parentRows: parents,
				contextRowMode: "parent"
			};
			this.grid._initializeHeights();
			this._trigger(this.events.contextRowRendered, null, eArgs);
		},
		_rowAdded: function (row) {
			// P.Zh. 9 May 2016 Fix for bug 219040: When autoCommit is false and paging is enabled adding a child throws an error.
			if (this.options.type === "remote" ||
					this.options.mode !== "allLevels" ||
					!this.grid.options.autoCommit) {
				return;
			}
			var id = this.grid._normalizedKey(row.attr("data-id")),
				index = this.grid._recordIndexInFlatView(id),
				pageSize = this.options.pageSize,
				targetPage;
			targetPage = Math.floor(index / pageSize);
			this.pageIndex(targetPage);
		},
		_injectGrid: function () {
			var ds, i;
			$.ui.igGridPaging.prototype._injectGrid.apply(this, arguments);
			ds = this.grid.dataSource;
			if (ds && ds.settings && ds.settings.treeDS) {
				ds.settings.treeDS.paging.mode = this.options.mode;
				ds.settings.treeDS.paging.contextRowMode = this.options.contextRowMode;
				/* when paging is remote and mode is allLevels we should send information for the changed(from the initial) expansion states
				* so the paging to be re-rendered
				* for each row toggling paging should be re-rendered(similar to the local paging) */
				if (this.options.type === "remote" && this.options.mode === "allLevels") {
					ds.settings.treeDS.persistExpansionStates = true;
				}
			}
			/* M.H. 9 June 2015 Fix for bug 201017: The treegrid does not throw error when contexRowMode and column fixing are enabled. */
			if (this.options.contextRowMode !== "none") {
				for (i = 0; i < this.grid.options.features.length; i++) {
					if (this.grid.options.features[ i ].name === "ColumnFixing") {
						throw new Error($.ig.TreeGridPaging.locale.columnFixingWithContextRowNotSupported);
					}
				}
			}
		}
	});
	$.extend($.ui.igTreeGridPaging, { version: "16.1.20161.2145" });
}(jQuery));



