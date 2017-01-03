/*!@license
 * Infragistics.Web.ClientUI Grid AppendRowsOnDemand 16.1.20161.2145
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
 *	infragistics.util.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {
	$.widget("ui.igGridAppendRowsOnDemand", {
		options: {
			/* type="remote|local"
			remote type="string" request data from the remote endpoint
			local type="string" loading data on the client-side
			*/
			type: null,
			/* type="number" default number of records per chunk */
			chunkSize: 25,
			/* type="string" the property in the response that will hold the total number of records in the data source */
			recordCountKey: null,
			/* type="string" denotes the name of the encoded URL parameter that will state what is the currently requested chunk size */
			chunkSizeUrlKey: null,
			/* type="string" denotes the name of the encoded URL parameter that will state what is the currently requested chunk index */
			chunkIndexUrlKey: null,
			/* type="number" initial chunk index position */
			defaultChunkIndex: 0,
			/* type="number" current chunk index position */
			currentChunkIndex: 0,
			/* type="auto|button" denotes the append rows on demand request method
			auto type="string" new record will be appended to the grid while the user scrolls the scrollbar
			button type="string" a button will be rendered at the bottom of the grid. The user should press it to load more rows
			*/
			loadTrigger: "auto",
			/* type="string" Specifies caption text for the "load more data" button. */
			loadMoreDataButtonText: $.ig.GridAppendRowsOnDemand.locale.loadMoreDataButtonText
		},
		events: {
			/* cancel="true" Event fired before the rows are requested from the remote endpoint.
			Return false in order to cancel requesting of rows.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridAppendRowsOnDemand.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.chunkIndex to get next chunk index.
			Use ui.chunkSize to get chunk size.
			*/
			rowsRequesting: "rowsRequesting",
			/* Event fired after the requested rows are returned from the remote endpoint, but before grid data rebinds
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridAppendRowsOnDemand.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.chunkIndex to get current chunk index.
			Use ui.chunkSize to get chunk size.
			Use ui.rows to get requested rows.
			*/
			rowsRequested: "rowsRequested"
		},
		_loadingIndicator: null,
		_persistLocalSorting: true,
		_persistLocalFiltering: true,
		_recalcLocalSummaries: true,
		_keepCurrentChunkIndex: true,
		_callDataRendered: true,
		_initialProbeForChunkIndex: true,
		/* every grid feature widget should implement this */
		_injectGrid: function (gridInstance) {
			this.grid = gridInstance;
			this._checkNotSupportedScenarios();
			this.options.currentChunkIndex = this.options.defaultChunkIndex;
			if (this.options.type === null) {
				// infer type
				this.options.type = this.grid._inferOpType();
			}
			/* delegate to the data source all paging options */
			this.grid.dataSource.settings.paging.type = this.options.type || "remote";
			/* delegate to the data source all paging options */
			this._defaultChunkSize = parseInt(this.options.chunkSize, 10) *
				(this.options.defaultChunkIndex + 1);
			this.grid.dataSource.settings.paging.pageSize = this._defaultChunkSize;

			if (this.options.chunkSizeUrlKey !== null && this.options.chunkIndexUrlKey) {
				this.grid.dataSource.settings.paging.pageSizeUrlKey = this.options.chunkSizeUrlKey;
				this.grid.dataSource.settings.paging.pageIndexUrlKey = this.options.chunkIndexUrlKey;
			}
			if (this.options.recordCountKey !== null) {
				this.grid.dataSource.settings.responseTotalRecCountKey = this.options.recordCountKey;
			}
			this.grid.dataSource.settings.paging.enabled = true;
			if (this.options.loadTrigger === "auto") {
				this._verticalScrollHandler = $.proxy(this._probeForNextChunk, this);
			}
			this._appendRecordsHandler = $.proxy(this._appendRecords, this);
			/* M.P.: 21 Oct 2014. Fix for bug: 183285 - Filter condition isn’t respected when type set to local.*/
			this._columnSortingHandler = $.proxy(this._columnSorting, this);
			this._syncCurrentChunkIndexHandler = $.proxy(this._syncCurrentChunkIndex, this);
			/* M.P.: 21 Oct 2014. Fix for bug: 183285 - Filter condition isn’t respected when type set to local.*/
			this.grid.element.bind("iggridsortinginternalcolumnsorting", this._columnSortingHandler);
			/* M.P.: 07 Nov 2014. Fix for bug: 184694 - Changing the filter cause the AppendRowsOnDemand to stop functioning.*/
			this.grid.element.bind("iggriduisoftdirty iggriduidirty", this._syncCurrentChunkIndexHandler);
		},
		_dataRendered: function () {
			var buttonId, container;
			if (!this._callDataRendered) {
				return;
			}
			/* MP: 25 Nov 2014 bug 185524 - Re-apply summaries when it's local and AROD is remote */
			this._keepCurrentChunkIndex = false;
			this.grid.scrollContainer().css("background-color", "white");
			this.grid.dataSource.settings.paging.pageSize = this.options.chunkSize;
			this._originalDataSourceCallback = this.grid.dataSource.settings.callback;
			this._initLoadingIndicator();
			if (this.options.loadTrigger === "auto") {
				this.grid.scrollContainer().unbind("scroll", this._verticalScrollHandler);
				this.grid.scrollContainer().bind("scroll", this._verticalScrollHandler);
				if (this._initialProbeForChunkIndex || (this.options.type === "local")) {
					this._probeForNextChunk();
				}
			}
			this._requestPending = false;
			this._triggerEvents = true;
			if (this.options.loadTrigger === "button") {
				if (!this._buttonRow) {
					buttonId = this.grid.id() + "_loadMoreButton";
					container = this.grid.options.height ?
						this.grid.scrollContainer() : this.grid.container();
					this._buttonRow =
						container.append("<div class='ui-iggrid-loadmorebutton'><input type='button' id='" +
						buttonId + "'></input></div>");
					this.grid.container().find("#" + buttonId).igButton({
						labelText: this.options.loadMoreDataButtonText,
						click: $.proxy(this._nextChunk, this),
						width: "100%"
					});
				}
			}

			if (this._loadingIndicator) {
				this._hideLoading();
			}
		},
		_checkNotSupportedScenarios: function () {
			/* Throw an exception for unsupported integration scenarios */
			if (this.options.loadTrigger === "auto" && !this.grid.options.height) {
				throw new Error($.ig.GridAppendRowsOnDemand.locale.appendRowsOnDemandRequiresHeight);
			}

			if (this.grid.options.virtualization || this.grid.options.rowVirtualization ||
				this.grid.options.columnVirtualization) {
				throw new Error($.ig.GridAppendRowsOnDemand.locale.virtualizationNotSupported);
			}

			var i, featureName,
			features = this.grid.options.features, featuresLength = features.length;
			if (featuresLength === 1) {
				return;
			}
			for (i = 0; i < featuresLength; i++) {
				featureName = features[ i ].name;
				if (!featureName) {
					continue;
				}
				featureName = featureName.toLowerCase();
				switch (featureName) {
					case "groupby":
						throw new Error($.ig.GridAppendRowsOnDemand.locale.groupByNotSupported);
					case "paging":
						throw new Error($.ig.GridAppendRowsOnDemand.locale.pagingNotSupported);
					case "cellmerging":
						throw new Error($.ig.GridAppendRowsOnDemand.locale.cellMergingNotSupported);
				}
			}
		},
		_setOption: function (key) {
			/* handle new settings and update options hash */
			$.Widget.prototype._setOption.apply(this, arguments);
			/* options that are supported:
			 start by throwing an error for the options that aren't supported */
			if (key === "defaultChunkIndex") {
				throw new Error($.ig.Grid.locale.optionChangeNotSupported.replace("{optionName}", key));
			}
			if (key === "currentChunkIndex" || key === "chunkSize") {
				this._keepCurrentChunkIndex = true;
				this.grid.dataSource.settings.paging.pageSize =
					(this.options.currentChunkIndex + 1) * this.options.chunkSize;
				this.grid.dataSource.dataBind();
			}
		},
		_initLoadingIndicator: function () {
			/* attach loading indicator widget*/
			this._loadingIndicator = this.grid.container().igLoading().data("igLoading").indicator();
		},
		_nextChunk: function () {
			var noCancel = true;

			if (this.options.currentChunkIndex >= (this.grid.dataSource.pageCount() - 1)) {
				return;
			}
			if (this._triggerEvents) {
			noCancel = this._trigger(this.events.rowsRequesting, null,
				{
					owner: this,
					chunkIndex: this.options.currentChunkIndex + 1,
					chunkSize: this.options.chunkSize
				});
			}
			if (noCancel) {
				this._showLoading();
				this.grid.dataSource.settings.paging.pageSize = this.options.chunkSize;
				this.grid.dataSource.settings.paging.pageIndex = this.options.currentChunkIndex;
				this.grid.dataSource.settings.paging.appendPage = true;
				this._originalDataSourceCallback = this.grid.dataSource.settings.callback;
				this.grid.dataSource.settings.callback = this._appendRecordsHandler;
				this._requestPending = true;
				this.grid.dataSource.nextPage();
			}
		},
		_showLoading: function () {
			this._loadingIndicator.show();
		},
		_hideLoading: function () {
			this._loadingIndicator.hide();
		},
		destroy: function () {
			/* destroys the append rows on demand widget */
			var buttonId = this.grid.id() + "_loadMoreButton",
				container = this.grid.options.height ? this.grid.scrollContainer() : this.grid.container(),
				button = container.find("div.ui-iggrid-loadmorebutton");
			this.grid.container().find("#" + buttonId).igButton("destroy");
			if (button) {
				button.remove();
			}
			this.grid.element.unbind("iggridsortinginternalcolumnsorting", this._columnSortingHandler);
			this.grid.element.unbind("iggriduisoftdirty iggriduidirty", this._syncCurrentChunkIndexHandler);
			this.grid.scrollContainer().unbind("scroll", this._verticalScrollHandler);
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		_appendRecords: function (success, errmsg) {
			var i, currentPage = [  ], noCancelError, sorting = this.grid.element.data("igGridSorting"),
				filtering = this.grid.element.data("igGridFiltering"),
				summaries = this.grid.element.data("igGridSummaries");

			if (success === true) {
				currentPage = this.grid.dataSource.dataView()
					.slice(this.grid.dataSource.settings.paging.pageIndex *
					this.grid.dataSource.settings.paging.pageSize);
			}
			this._requestPending = false;
			this.grid.dataSource.settings.paging.pageIndex = 0;
			this.grid.dataSource.settings.paging.appendPage = false;
			this.grid.dataSource.settings.callback = this._originalDataSourceCallback;
			for (i = 0; i < currentPage.length; i++) {
				this.grid.renderNewRow(currentPage[ i ]);
			}

			/* re-apply sorting styles */
			if (sorting) {
				this._keepCurrentChunkIndex = true;
				/* MP: 176770 - Re-apply sorting when it's local and AROD is remote */
				if ((this.options.type === "remote") &&
					(this.grid.dataSource.settings.sorting.type === "local")) {
					if (!this._persistLocalSorting) {
						this.grid.dataSource.settings.sorting.expressions = [  ];
					} else {
						if (this.grid.dataSource.settings.sorting.expressions.length > 0) {
							this._callDataRendered = false;
							sorting.sortMultiple();
							this._callDataRendered = true;
						}
					}
				} else {
					sorting._dataRendered();
				}
				this._keepCurrentChunkIndex = false;
			}
			if (filtering) {
				this._keepCurrentChunkIndex = true;
				/* MP: 176770 - Re-apply filtering when it's local and AROD is remote */
				if ((this.options.type === "remote") &&
					(this.grid.dataSource.settings.filtering.type === "local")) {
					if (!this._persistLocalFiltering) {
						this.grid.dataSource.settings.filtering.expressions = [  ];
					}
					this._callDataRendered = false;

					filtering.filter(this.grid.dataSource.settings.filtering.expressions, true);
					this._callDataRendered = true;
				}
				this._keepCurrentChunkIndex = false;
			}

			if (summaries) {
				/* MP: 176770 - Re-apply summaries when it's local and AROD is remote */
				if ((this.options.type === "remote") &&
					(this.grid.dataSource.settings.summaries.type === "local")) {
					if (this._recalcLocalSummaries) {
						summaries.calculateSummaries();
					}
				}
			}

			this._hideLoading();
			if (success === false) {
				/* check if there is an event requestError defined */
				noCancelError = this._trigger(this.grid.events.requestError, null,
					{ owner: this, message: errmsg });
				/* if the handler returns false or doesn't return anything, the error will be propagated to teh grid and an Error object will be returned */
				if (noCancelError) {
					throw new Error(errmsg);
				}
			}
			this.options.currentChunkIndex++;
			this._keepCurrentChunkIndex = false;
			if (this._triggerEvents) {
				this._trigger(this.events.rowsRequested, null,
					{
						owner: this,
						chunkIndex: this.options.currentChunkIndex,
						chunkSize: this.options.chunkSize,
						rows: currentPage });
			}
			this._triggerEvents = true;
			if (this._initialProbeForChunkIndex || (this.options.type === "local")) {
				this._probeForNextChunk();
			}
		},
		_probeForNextChunk: function () {
			/* M.P.: Only in auto mode we need to load more data so that it shows the scrollbar */
			if (this.options.loadTrigger !== "auto") {
				return;
			}
			var delta = (this.grid.scrollContainer().scrollTop() +
				this.grid.scrollContainer().height()) / $(this.grid.element).height();
			if ((delta >= 1) && !this._requestPending) {
				this._nextChunk();
			} else {
				this._initialProbeForChunkIndex = false;
			}
		},
		/* M.P.: 21 Oct 2014. Fix for bug: 183285 - Filter condition isn’t respected when type set to local. */
		_columnSorting: function () {
			this.grid.dataSource.settings.paging.pageSize =
				this.options.chunkSize * (this.options.currentChunkIndex + 1);
			this._keepCurrentChunkIndex = true;
		},
		_syncCurrentChunkIndex: function () {
			if ((this.options.type === "remote") &&
				(this.grid.dataSource.settings.filtering.type === "local")) {
				return;
			}
			if (!this._keepCurrentChunkIndex) {
				if (this.grid.dataSource.settings.paging.pageIndex !== this.options.currentChunkIndex) {
					this.options.currentChunkIndex = this.grid.dataSource.settings.paging.pageIndex;
				}
				this._keepCurrentChunkIndex = true;
			}
		},
		nextChunk: function () {
			/*Loads the next chunk of data.*/
			this._triggerEvents = false;
			this._nextChunk();
		}
	});
	$.extend($.ui.igGridAppendRowsOnDemand, { version: "16.1.20161.2145" });
}(jQuery));
