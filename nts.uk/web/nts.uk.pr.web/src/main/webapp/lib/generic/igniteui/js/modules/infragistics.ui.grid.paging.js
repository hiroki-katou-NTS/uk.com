/*!@license
 * Infragistics.Web.ClientUI Grid Paging 16.1.20161.2145
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
 */

/*jscs:disable*/
/*global jQuery, toStaticHTML */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {

	/* we will define a new widget for paging. That kind of widget is completely independent one and doesn't subclass the grid */
	/* it subscribes to grid events such as BodyRendered, and has the grid instance (igGrid) injected into it */
	/* for paging */
	/*
		igGridPaging widget -  The widget is pluggable to the element where the grid is instantiated and the actual igGrid object doesn't know about this
		the paging widget just attaches its functionality to the grid
		the paging widget renders most of its UI in the grid footer. Optionally there can be a dropdown to choose the page size, that can be placed above the header
		keyboard navigation inside the pager is supported with TAB & SPACE/ENTER keys
	*/
	$.widget("ui.igGridPaging", {
		css: {
			/*  classes applied to the pager area */
			"pagerClass": "ui-widget ui-iggrid-pager ui-helper-clearfix ui-corner-bottom ui-widget-header ui-iggrid-footer",
			/* classes applied to a page link that can be clicked with the mouse */
			"pageLink": "ui-iggrid-pagelink ui-helper-reset",
			/* classes applied to a page container element (list item) */
			"page": "ui-iggrid-page ui-state-default ui-corner-all",
			/* classes applied to a page list item when it's hovered */
			"pageHover": "ui-iggrid-page-hover ui-state-hover",
			/* classes applied to the UL list that holds all pages */
			"pageList": "ui-helper-reset ui-iggrid-pagelist ui-iggrid-paging-item",
			/* classes applied to the anchor of the current page item */
			"pageLinkCurrent": "ui-iggrid-pagelinkcurrent",
			/* clases applied to the current page (corresponding to the current page index) */
			"pageCurrent": "ui-iggrid-pagecurrent ui-state-active ui-corner-all",
			/* classes applied to the current page that has focus (when keyboard anvigation is used) */
			"pageFocused": "ui-iggrid-pagefocused ui-state-focus",
			/* classes applied to the area where the next page button and label are rendered */
			"nextPage": "ui-iggrid-nextpage ui-iggrid-paging-item ui-state-default",
			/* classes applied to the area where the prev page button and label are rendered */
			"prevPage": "ui-iggrid-prevpage ui-iggrid-paging-item ui-state-default",
			/* classes applied to the area where the first page button and label are rendered */
			"firstPage": "ui-iggrid-firstpage ui-iggrid-paging-item ui-state-default ui-corner-left",
			/* classes applied to the area where the last page button and label are rendered */
			"lastPage": "ui-iggrid-lastpage ui-iggrid-paging-item ui-state-default ui-corner-right",
			/* classes applied to the next page's label (span) */
			"nextPageLabel": "ui-iggrid-nextpagelabel",
			/* classes applied to the prev page's label (span) */
			"prevPageLabel": "ui-iggrid-prevpagelabel",
			/* classes applied to the first page's label (span) */
			"firstPageLabel": "ui-iggrid-firstpagelabel",
			/* classes applied to the last page's label (span) */
			"lastPageLabel": "ui-iggrid-lastpagelabel",
			/* classes applied to the next page's label (span) when it's disabled */
			"nextPageLabelDisabled": "ui-iggrid-nextpagelabeldisabled ui-state-disabled",
			/* classes applied to the prev page's label (span) when it's disabled */
			"prevPageLabelDisabled": "ui-iggrid-prevpagelabeldisabled ui-state-disabled",
			/* classes applied to the first page's label (span) when it's disabled */
			"firstPageLabelDisabled": "ui-iggrid-firstpagelabeldisabled ui-state-disabled",
			/* classes applied to the last page's label (span) when it's disabled */
			"lastPageLabelDisabled": "ui-iggrid-lastpagelabeldisabled ui-state-disabled",
			/* classes applied to the next page area that holds the span for the icon */
			"nextPageImage": "ui-iggrid-pageimg ui-iggrid-nextpageimg ui-icon ui-icon-triangle-1-e",
			/* classes applied to the prev page area that holds the span for the icon */
			"prevPageImage": "ui-iggrid-pageimg ui-iggrid-prevpageimg ui-icon ui-icon-triangle-1-w",
			/* classes applied to the first page area that holds the span for the icon */
			"firstPageImage": "ui-iggrid-pageimg ui-iggrid-firstpageimg ui-icon ui-icon-arrowstop-1-w",
			/* classes applied to the last page area that holds the span for the icon */
			"lastPageImage": "ui-iggrid-pageimg ui-iggrid-lastpageimg ui-icon ui-icon-arrowstop-1-e",
			/* classes applied to the next page area that holds the span for the icon when it is disabled */
			"nextPageImageDisabled": "ui-iggrid-pageimg ui-iggrid-nextpageimgdisabled ui-icon ui-state-disabled ui-icon-triangle-1-e",
			/* classes applied to the prev page area that holds the span for the icon when it is disabled */
			"prevPageImageDisabled": "ui-iggrid-pageimg ui-iggrid-prevpageimgdisabled ui-icon ui-state-disabled ui-icon-triangle-1-w",
			/* classes applied to the first page area that holds the span for the icon when it is disabled */
			"firstPageImageDisabled": "ui-iggrid-pageimg ui-iggrid-firstpageimgdisabled ui-icon ui-state-disabled ui-icon-arrowstop-1-w",
			/* classes applied to the last page area that holds the span for the icon when it is disabled */
			"lastPageImageDisabled": "ui-iggrid-pageimg ui-iggrid-lastpageimgdisabled ui-icon ui-state-disabled ui-icon-arrowstop-1-e",
			/* classes applied to the label showing how many records are rendered of some total number */
			"pagerRecordsLabel": "ui-iggrid-pagerrecordslabel ui-iggrid-results",
			/* classes applied to the editor dropdown label from where page size can be changed */
			"pageSizeLabel": "ui-iggrid-pagesizelabel",
			/* classes applied to the editor dropdown from where page size can be changed */
			"pageSizeDropDown": "ui-iggrid-pagesizedropdown",
			/* classes applied to the element that holds the page size dropdown */
			"pageSizeDropDownContainer": "ui-helper-clearfix ui-iggrid-pagesizedropdowncontainer",
			/* classes applied to the container of the page size dropdown editor, when it is rendered above the header */
			"pageSizeDropDownContainerAbove": "ui-widget ui-helper-clearfix ui-iggrid-pagesizedropdowncontainerabove ui-iggrid-toolbar ui-widget-header and ui-corner-top",
			/* classes applied to the element holding the editor dropdown from where the page index can be changed */
			"pageDropDownContainer": "ui-iggrid-pagedropdowncontainer",
			/* classes applied to the spans that are before and after the dropdown editor from where the page index can be changed */
			"pageDropDownLabels": "ui-iggrid-pagedropdownlabels",
			/* classes applied to the editor dropdown from where the page index can be changed */
			"pageDropDown": "ui-iggrid-pagedropdown",
			/* classes applied to the area on the right of the footer where first, last, prev, next buttons as well as page links and page index dropdown are rendered */
			"pagerRightAreaContainer": "ui-iggrid-paging",
			/* classes applied around the label showing the currently rendered record indices out of some total value */
			"pagingResults": "ui-iggrid-results"
		},
		options: {
			/* type="number" default number of records per page */
			pageSize: 25,
			/* type="string" the property in the response that will hold the total number of records in the data source */
			recordCountKey: null,
			/* type="string" denotes the name of the encoded URL parameter that will state what is the currently requested page size */
			pageSizeUrlKey: null,
			/* type="string" denotes the name of the encoded URL parameter that will state what is the currently requested page index */
			pageIndexUrlKey: null,
			/* type="number" current page index that's bound and rendered in the UI */
			currentPageIndex: 0,
			/* type="remote|local"
			remote type="string" request
			local type="string" paging on the client-side
			*/
			type: null,
			/* type="bool" if false, a dropdown allowing to change the page size will not be rendered in the UI */
			showPageSizeDropDown: true,
			/* type="string" label rendered in front of the page size dropdown */
			pageSizeDropDownLabel: $.ig.GridPaging.locale.pageSizeDropDownLabel,
			/* type="string" Trailing label for the page size dropdown */
			pageSizeDropDownTrailingLabel: $.ig.GridPaging.locale.pageSizeDropDownTrailingLabel,
			/* type="above|inpager" Page size dropdown location. Can be rendered above the grid header or inside the pager, next to the page links.
			above type="string" it will be rendered above the grid header
			inpager type="string" it will be rendered next to page links
			*/
			pageSizeDropDownLocation: "above",
			/* type="bool" option specifying whether to show summary label for the currently rendered records or not */
			showPagerRecordsLabel: true,
			/* type="string" custom pager records label template - in jQuery templating style and syntax */
			pagerRecordsLabelTemplate: $.ig.GridPaging.locale.pagerRecordsLabelTemplate,
			/* type="string" localized text for the next page label */
			nextPageLabelText: $.ig.GridPaging.locale.nextPageLabelText,
			/* type="string" localized text for the prev page label */
			prevPageLabelText: $.ig.GridPaging.locale.prevPageLabelText,
			/* type="string" localized text for the first page label */
			firstPageLabelText: $.ig.GridPaging.locale.firstPageLabelText,
			/* type="string" localized text for the last page label */
			lastPageLabelText: $.ig.GridPaging.locale.lastPageLabelText,
			/* type="bool" option specifying whether to render the first and last page buttons */
			showFirstLastPages: true,
			/* type="bool" option specifying whether to render the previous and next page buttons */
			showPrevNextPages: true,
			/* type="string" leading label for the dropdown from where the page index can be switched */
			currentPageDropDownLeadingLabel: $.ig.GridPaging.locale.currentPageDropDownLeadingLabel,
			/* type="string" localized trailing label for the dropdown from where the page index can be switched */
			currentPageDropDownTrailingLabel: $.ig.GridPaging.locale.currentPageDropDownTrailingLabel,
			/* type="string" custom localized tooltip for the page index dropdown */
			currentPageDropDownTooltip: $.ig.GridPaging.locale.currentPageDropDownTooltip,
			/* type="string" custom localized tooltip for the page size dropdown */
			pageSizeDropDownTooltip: $.ig.GridPaging.locale.pageSizeDropDownTooltip,
			/* type="string" custom localized tooltip for the pager records label */
			pagerRecordsLabelTooltip: $.ig.GridPaging.locale.pagerRecordsLabelTooltip,
			/* type="string" custom localized tooltip for the prev. page button */
			prevPageTooltip: $.ig.GridPaging.locale.prevPageTooltip,
			/* type="string" custom localized tooltip for the next. page button */
			nextPageTooltip: $.ig.GridPaging.locale.nextPageTooltip,
			/* type="string" custom localized tooltip for the first. page button */
			firstPageTooltip: $.ig.GridPaging.locale.firstPageTooltip,
			/* type="string" custom localized tooltip for the last. page button */
			lastPageTooltip: $.ig.GridPaging.locale.lastPageTooltip,
			/* type="string" custom localized format string for tooltips of buttons that directly jump to a particular page. The format string follows the jQuery templating style and syntax. See also the pageCountLimit option.  */
			pageTooltipFormat: $.ig.GridPaging.locale.pageTooltipFormat,
			/* type="array" Default: [5, 10, 20, 25, 50, 75, 100]. contents of the page size dropdown indicating what preconfigured page sizes are available to the end user */
			pageSizeList: [],
			/* type="number" sets gets the number of pages which if exceeded a drop down list of page indices is displayed. If the number of pages is less than or equal to this option then page buttons are displayed. */
			pageCountLimit: 10,
			/* type="number" number of pages that are constantly visible. For the invisible pages, prev and next buttons are used */
			visiblePageCount: 5,
			/* type="number" dropdown width for page size and page index dropdowns */
			defaultDropDownWidth: 70,
			/* type="number" Time in milliseconds for which page dropdown will wait for keystrokes before changing the page. */
			delayOnPageChanged: 350,
			/* type="bool" Enables / disables paging persistence between states*/
			persist: true,
			/* type="bool" Enables/disables feature inheritance for the child layouts. NOTE: It only applies for igHierarchicalGrid. */
			inherit: false
		},
		events: {
			/* cancel="true" Event fired before the page index is changed.
			Return false in order to cancel page index changing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridPaging.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.currentPageIndex to get current page index.
			Use ui.newPageIndex to get new page index.
			*/
			pageIndexChanging: "pageIndexChanging",
			/* Event fired after the page index is changed , but before grid data rebinds
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridPaging.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.pageIndex to get current page index.
			*/
			pageIndexChanged: "pageIndexChanged",
			/* cancel="true" Event fired when the page size is about to be changed from the page size dropdown.
			Return false in order to cancel page size changing.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridPaging.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.currentPageSize to get current page size.
			Use ui.newPageSize to get new page size.
			*/
			pageSizeChanging: "pageSizeChanging",
			/* Event fired after the page size is changed from the page size dropdown.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridPaging.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.pageSize to get current page size.
			*/
			pageSizeChanged: "pageSizeChanged",
			/* cancel="true" Event fired before the pager footer is rendered (the whole area below the grid records).
			Return false in order to cancel pager footer rendering.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridPaging.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dataSource to get reference to grid's data source.
			*/
			pagerRendering: "pagerRendering",
			/* Event fired after the pager footer is rendered
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGridPaging.
			Use ui.owner.grid to get reference to igGrid.
			Use ui.dataSource to get reference to grid's data source.
			*/
			pagerRendered: "pagerRendered"
		},
		/* count of visible items shown in pager dropdown */
		dropDownVisibleItemsCount: 10,
		_loadingIndicator: null,
		_isPaging: false,
		/*jscs:enable*/
		_create: function () {
			/* M.H. 2 Dec 2014 Fix for bug #185831: When persistence for paging is disabled, databinding does not cause the grid to go on the currentPageIndex */
			this._oPageIndex = this.options.currentPageIndex;
			this._oPageSize = this.options.pageSize;
		},
		_setOption: function (key, value) {
			var items, label, pager;
			pager = this._pager();
			/* handle new settings and update options hash */
			$.Widget.prototype._setOption.apply(this, arguments);
			/* options that are supported: pageSize, currentPageIndex, showFirstLastPages, showPrevNextPages, pageSizeList, */
			/* nextPageLabelText, prevPageLabelText, firstPageLabelText, lastPageLabelText */
			/* start by throwing an error for the options that aren't supported */
			if (key === "type" ||
				key === "showPageSizeDropDown" ||
				key === "pageSizeDropDownLocation" ||
				key === "showPagerRecordsLabel" ||
				key === "visiblePageCount") {
				throw new Error($.ig.Grid.locale.optionChangeNotSupported.replace("{optionName}", key));
			}
			if (key === "pageSize") {
				this.pageSize(value);
			} else if (key === "currentPageIndex") {
				this.pageIndex(value);
			} else if (key === "showFirstLastPages") {
				if (value === true) {
					pager.find(".ui-iggrid-firstpage").show();
					pager.find(".ui-iggrid-lastpage").show();
				} else {
					pager.find(".ui-iggrid-firstpage").hide();
					pager.find(".ui-iggrid-lastpage").hide();
				}
			} else if (key === "showPrevNextPages") {
				if (value === true) {
					pager.find(".ui-iggrid-prevpage").show();
					pager.find(".ui-iggrid-nextpage").show();
				} else {
					pager.find(".ui-iggrid-prevpage").hide();
					pager.find(".ui-iggrid-nextpage").hide();
				}
			} else if (key === "pageSizeList") {
				if ($.type(value) === "string") {
					items = value.split(",");
				} else {
					items = value;
				}
				if (this._pageSizeDD) {
					this._pageSizeDD.igNumericEditor("option", "listItems", items);
				}
			} else if (key === "nextPageLabelText") {
				label = pager.find(".ui-iggrid-nextpagelabel");
				label = label.length === 0 ? pager.find(".ui-iggrid-nextpagelabeldisabled") : label;
				label.text(value);
			} else if (key === "prevPageLabelText") {
				label = pager.find(".ui-iggrid-prevpagelabel");
				label = label.length === 0 ? pager.find(".ui-iggrid-prevpagelabeldisabled") : label;
				label.text(value);
			} else if (key === "firstPageLabelText") {
				label = pager.find(".ui-iggrid-firstpagelabel");
				label = label.length === 0 ? pager.find(".ui-iggrid-firstpagelabeldisabled") : label;
				label.text(value);
			} else if (key === "lastPageLabelText") {
				label = pager.find(".ui-iggrid-lastpagelabel");
				label = label.length === 0 ? pager.find(".ui-iggrid-nextpagelabeldisabled") : label;
				label.text(value);
			}
		},
		/* Accepts parameters: */
		/* index - page index to switch to */
		pageIndex: function (index) {
			/* gets /sets the current page index, delegates data binding and paging to $.ig.DataSource
				paramType="number" optional="true" the page index to go to
				returnType="number" optional="true" if no param is specified, returns the current page index
			*/
			if (index !== null && index !== undefined) {
				/* M.H. 1 Dec 2014 Fix for bug #185759: Uncaught TypeError is thrown when an invalid page index is assigned */
				if (index < 0 || index >= this.grid.dataSource.pageCount()) {
					throw new Error($.ig.GridPaging.locale.invalidPageIndex);
				}
				this._overrideLabel = true;
				this.options.currentPageIndex = index;
			}
			/* fire partial dirty event - currently used only in Selection - cause we just need to call clearSelection without clearing any visual states or reset the whole UI */
			/* we cannot use UI Dirty cause if we trigger this event here it means behaviors such as *Sorting* (not Selection!) will reset their whole state, and we want to keep the */
			/* data sorted (for example) while paging locally */
			/* L.A. 12 June 2012 - Fixing bug #114318 */
			/* If index is not set then the pageIndex is called as GET index, so it's not necessary to */
			/* to call the UIDIRTY function which removes clears the selection */
			if (index !== undefined && index !== null) {
				this.grid.element.trigger("iggriduisoftdirty", { owner: this });
			}
			/* M.H. 20 Sep 2013 Fix for bug #145014: When using remote paging and sorting  the values of the unbound columns set via the unboundValues option are not persisted. */
			/* M.H. 15 Oct 2013 Fix for bug #154987: When the dataSource is remote, any action that rearranges records mess up the unbound values */
			if (this.grid._hasUnboundColumns && this.options.type === "remote") {
				this.grid._rebindUnboundColumns = true;
			}
			/* M.H. 2 Dec 2014 Fix for bug #185895: Paging does not persist the state of the children in hierarchical grid */
			this._savePagingData();
			return this.grid.dataSource.pageIndex(index);
		},
		/* Accepts parameters: */
		/* size - page size */
		pageSize: function (size) {
			/* gets / sets the page Size. if no parameter is specified, just returns the current page size
				paramType="number" optional="true" the page size
				returnType="number" optional="true" if no param is specified, returns the current page size
			*/
			var noCancel = true;
			if (size) {
				this.grid.dataSource.settings.paging.pageIndex = 0;
				this.options.currentPageIndex = 0;
				/* trigger DataBinding / DataBound */
				noCancel = this.grid._trigger(this.grid.events.dataBinding, null,
						{
							owner: this.grid,
							dataSource: this.grid.dataSource
						});
				if (noCancel) {
					this._showLoading();
					/* M.H. 20 Sep 2013 Fix for bug #145014: When using remote paging and sorting  the values of the unbound columns set via the unboundValues option are not persisted. */
					/* M.H. 15 Oct 2013 Fix for bug #154987: When the dataSource is remote, any action that rearranges records mess up the unbound values */
					if (this.grid._hasUnboundColumns && this.options.type === "remote") {
						this.grid._rebindUnboundColumns = true;
					}
					/* M.K. 21 May 2015 Fix for bug 194116: Pager label is not getting refreshed when page size changes after filtering */
					this._overrideLabel = true;
					/* M.H. 13 Feb 2014 Fix for bug #164277: Paging shows not existing chunk of data when you change the page size, change the page and data bind after that. */
					this.options.pageSize = size;
					/* M.H. 2 Dec 2014 Fix for bug #185895: Paging does not persist the state of the children in hierarchical grid */
					this._savePagingData();
					this.grid.dataSource.pageSize(size);
				}
				/*A.T. 14 Feb 2011 - fix for bug #65975 */
				/* update page size dropdown */
				if (this._pageSizeDD) {
					this._pageSizeDD.igNumericEditor("value", size);
				}
			} else {
				return this.grid.dataSource.pageSize();
			}
		},
		_savePagingData: function () {
			if (!this.options.persist) {
				return;
			}
			/* we need to save paging data in case of hierarchical grid(for child grids) */
			/* for flat grid and root grid persistence works out-of-the-box */
			var pagingData;
			if (this.element.closest(".ui-iggrid-root").data("igGrid")) {
				pagingData = {
					pageIndex: this.options.currentPageIndex,
					pageSize: this.options.pageSize
				};
				this.grid._savePersistenceData(pagingData, "paging", this.grid.element[ 0 ].id);
			}
		},
		_loadPagingData: function () {
			var pagingData;
			if (this.element.closest(".ui-iggrid-root").data("igGrid")) {
				pagingData = this.grid._getPersistenceData("paging", this.grid.element[ 0 ].id);
				if (!pagingData) {
					return;
				}
				this.grid.dataSource.settings.paging.pageIndex = pagingData.pageIndex;
				this.options.currentPageIndex = pagingData.pageIndex;
				this.options.pageSize = pagingData.pageSize;
			}
		},
		_initLoadingIndicator: function () {
			/* attach loading indicator widget */
			this._loadingIndicator = this.grid.container().igLoading().data("igLoading").indicator();
		},
		_nextPage: function (event) {
			var noCancel = true, noCancelBinding = true;
			noCancel = this._trigger(this.events.pageIndexChanging, null, {
				newPageIndex: this.options.currentPageIndex + 1,
				currentPageIndex: this.options.currentPageIndex,
				owner: this
			});
			if (noCancel) {
				if (this.options.currentPageIndex >= this.grid.dataSource.pageCount() - 1) {
					return;
				}
				this.options.currentPageIndex = this.options.currentPageIndex + 1;
				noCancelBinding = this.grid._trigger(this.grid.events.dataBinding, null,
					{
						owner: this.grid,
						dataSource: this.grid.dataSource
					});
				this._shouldFirePageIndexChanged = true;
				if (noCancelBinding) {
					this._showLoading();
					this._overrideLabel = true;
					this.grid.element.trigger("iggriduisoftdirty", { owner: this });
					/* M.H. 20 Sep 2013 Fix for bug #145014: When using remote paging and sorting  the values of the unbound columns set via the unboundValues option are not persisted. */
					/* M.H. 15 Oct 2013 Fix for bug #154987: When the dataSource is remote, any action that rearranges records mess up the unbound values */
					if (this.grid._hasUnboundColumns && this.options.type === "remote") {
						this.grid._rebindUnboundColumns = true;
					}
					/* M.H. 2 Dec 2014 Fix for bug #185895: Paging does not persist the state of the children in hierarchical grid */
					this._savePagingData();
					this.grid.dataSource.nextPage();
				}
				/*this._trigger(this.events.pageIndexChanged, null, {pageIndex: this.options.currentPageIndex, owner: this}); */
			}
			if (event) {
				event.stopPropagation();
				event.preventDefault();
			}
		},
		_prevPage: function (event) {
			var noCancel = true, noCancelBinding = true;
			noCancel = this._trigger(this.events.pageIndexChanging, null,
				{
					newPageIndex: this.options.currentPageIndex - 1,
					currentPageIndex: this.options.currentPageIndex,
					owner: this
				});
			if (noCancel) {
				if (this.options.currentPageIndex === 0) {
					return;
				}
				this.options.currentPageIndex = this.options.currentPageIndex - 1;
				noCancelBinding = this.grid._trigger(this.grid.events.dataBinding, null,
					{
						owner: this.grid,
						dataSource: this.grid.dataSource
					});
				this._shouldFirePageIndexChanged = true;
				if (noCancelBinding) {
					this._showLoading();
					this._overrideLabel = true;
					this.grid.element.trigger("iggriduisoftdirty", { owner: this });
					/* M.H. 20 Sep 2013 Fix for bug #145014: When using remote paging and sorting  the values of the unbound columns set via the unboundValues option are not persisted. */
					/* M.H. 15 Oct 2013 Fix for bug #154987: When the dataSource is remote, any action that rearranges records mess up the unbound values */
					if (this.grid._hasUnboundColumns && this.options.type === "remote") {
						this.grid._rebindUnboundColumns = true;
					}
					this._savePagingData();
					this.grid.dataSource.prevPage();
				}
				/*this._trigger(this.events.pageIndexChanged, null, {pageIndex: this.options.currentPageIndex, owner: this}); */
			}
			if (event) {
				event.stopPropagation();
				event.preventDefault();
			}
		},
		_firstPage: function (event) {
			var noCancel = true, noCancelBinding = true;
			noCancel = this._trigger(this.events.pageIndexChanging, null,
								{
									newPageIndex: 0,
									currentPageIndex: this.options.currentPageIndex,
									owner: this
								});
			if (noCancel) {
				noCancelBinding = this.grid._trigger(this.grid.events.dataBinding, null,
								{
									owner: this.grid,
									dataSource: this.grid.dataSource
								});
				this._shouldFirePageIndexChanged = true;
				if (noCancelBinding) {
					this._showLoading();
					this.pageIndex(0);
				}
				/*this._trigger(this.events.pageIndexChanged, null, {pageIndex: 0, owner: this}); */
			}
			if (event) {
				event.stopPropagation();
				event.preventDefault();
			}
		},
		_lastPage: function (event) {
			var noCancel = true, noCancelBinding = true;
			noCancel = this._trigger(this.events.pageIndexChanging, null,
									{
										newPageIndex: this.grid.dataSource.pageCount() - 1,
										currentPageIndex: this.options.currentPageIndex,
										owner: this
									});
			if (noCancel) {
				noCancelBinding = this.grid._trigger(this.grid.events.dataBinding, null,
					{
						owner: this.grid,
						dataSource: this.grid.dataSource
					});
				this._shouldFirePageIndexChanged = true;
				if (noCancelBinding) {
					this._showLoading();
					this.pageIndex(this.grid.dataSource.pageCount() - 1);
				}
				/*this._trigger(this.events.pageIndexChanged, null, {pageIndex: this.grid.dataSource.pageCount() - 1, owner: this}); */
			}
			if (event) {
				event.stopPropagation();
				event.preventDefault();
			}
		},
		_showLoading: function () {
			this._loadingIndicator.show();
		},
		_hideLoading: function () {
			this._loadingIndicator.hide();
		},
		_bindEvents: function () {
			var paging = this, grid = this.grid, noCancel = true, id, noCancelBinding = true;
			id = "#" + grid.element[ 0 ].id + "_pager";
			/* M.H. 7 Nov 2013 Fix for bug #156757: Clicking a button the change the page index does not work if you dont click on the text */
			$(id + " li").bind({
				mousedown: function (event) {
					/* page changed */
					var $eventTarget = $(event.target),
						newIndex = $eventTarget.data("pageIndex");

					if (newIndex === undefined) {
						newIndex = $eventTarget.parent().data("pageIndex");
					}
					if ($.type(newIndex) === "number" && newIndex !== paging.options.currentPageIndex) {
						/* fire PageIndexChanging */
						noCancel = paging._trigger(paging.events.pageIndexChanging, event,
								{
									newPageIndex: newIndex,
									currentPageIndex: paging.options.currentPageIndex,
									owner: paging
								});
						paging._shouldFirePageIndexChanged = true;
						if (noCancel) {
							noCancelBinding = paging.grid._trigger(paging.grid.events.dataBinding, null,
								{
									owner: paging.grid,
									dataSource: paging.grid.dataSource
								});
							if (noCancelBinding) {
								paging._showLoading(grid.element.children("tbody"));
								paging.pageIndex(newIndex);
							}
							/* decide exactly when should the pageIndexChanged event fire */
							/*paging._trigger(paging.events.pageIndexChanged, event, {pageIndex: paging.options.currentPageIndex, owner: paging}); */
						}
					}
				}
			});
			/* hover styles */
			$(id + " li, " + id + " div.ui-iggrid-paging-item").bind({
				mouseover: function (event) {
					if (!$(event.currentTarget).find("span").first().hasClass("ui-state-disabled")) {
						$(event.currentTarget).addClass(paging.css.pageHover);
					}
				},
				mouseout: function (event) {
					$(event.currentTarget).removeClass(paging.css.pageHover);
				}
			});
		},
		_fixedColumnsChanged: function (args) {
			/* M.H. 1 Feb 2016 Fix for bug 212827: igGrid gets much smaller in height than is expected when it is resized to be small in window resize event. */
			if (args.isInit) {
				this._pager().css("clear", "both");
			}
		},
		_gridRendered: function (gridContainer) {
			var self = this;
			/* L.A. 19 February 2013 - Fixing bug #124252 */
			/* When the height is fixed and pageSizeDropDownLocation is inpager the footer and the next element in DOM are overlapped */
			if (this.options.showPageSizeDropDown === true &&
				this.options.pageSizeDropDownLocation === "above" &&
				!this._pageSizeDropDownRendered) {
				/* the second arg means prepend not append if it is true */
				self._renderPageSizeDropDown(gridContainer, true);
			}
			if (this.grid.options.autoAdjustHeight) {
				this.grid._initializeHeights();
				this.grid._adjustLastColumnWidth();
			}
			/* remove rounded corners from the caption, if any */
			/* M.H. 10 June 2014 Fix for bug #173280: In Metro theme when caption is enabled the headers, caption and add new row do not extend across whole grid in IE 8+. */
			if (this.grid.options.caption !== null &&
				!($.ig.util.isIE && $.ig.util.browserVersion >= 8)) {
				this.grid._caption().removeClass("ui-corner-top");
			}
		},
		_rowDeleted: function () {
			/* M.H. 9 Sep 2013 Fix for bug #151489: Unable to delete rows when autoCommit is true and paging set to remote. */
			if (this.options.type === "remote") {
				return;
			}
			/* M.H. 3 Jul 2013 Fix for bug #146146: The rows displayed on a page become less than the page size and a page might become unavailable when deleting rows */
			/* when row is deleted we should apply again paging so rows to be shown properly - for instance to be shown those from previous page if not last page - zebra style will be lost */
			var currentPageIndex = this.options.currentPageIndex,
				pageCount = this.grid.dataSource.pageCount();
			if (currentPageIndex > 0 && currentPageIndex + 1 > pageCount) {
				currentPageIndex--;
			}
			this.pageIndex(currentPageIndex);
		},
		_rowAdded: function () {
			if (this.options.type === "remote") {
				return;
			}
			var currentPageIndex = this.options.currentPageIndex,
				pageCount = this.grid.dataSource.pageCount();
			if (currentPageIndex < pageCount - 1 && this.grid.options.autoCommit) {
				this.pageIndex(pageCount - 1);
			} else {
				this._dataRendered();
			}
		},
		_gridCommit: function () {
			if (this.options.type === "remote") {
				return;
			}
			this.pageIndex(this.options.currentPageIndex);
			return true;
		},
		_plabel: function () {
			return this._pager().find(".ui-iggrid-pagerrecordslabel");
		},
		_pager: function () {
			return this.grid.container().find(".ui-iggrid-pager");
		},
		/* M.H. 26 June 2015 Fix for bug 201835: When filtering and paging are enabled for TreeGrid and filter and after that change the page pager label is not correct */
		_getDSLocalRecordsCount: function () {
			/* get datasource local records count */
			if (this.grid.dataSource._filter) {
				return this.grid.dataSource._filteredData.length;
			}
			return this.grid.dataSource.totalLocalRecordsCount();
		},
		_dataRendered: function () {
			var id = this.grid.element[ 0 ].id, filtering, tmpl, kbrdNavEvents, edtrOpts,
				i, html, pager = null, pageList, pagesArray, dropDownContainer, pageCount,
				startRecord = 0, endRecord = 0, recordsCount = 0, localRecordsCount = 0, noCancel = true,
				startPageIndex = 0, endPageIndex = 0,
				/* construct pager div */
				pagerHtml = toStaticHTML("<div id=\"" + id + "_pager\"></div>"),
				pageLinkHtml = toStaticHTML("<li class=\"${pageClass}\" tabIndex=\"0\">" +
											"<a class=\"${pageLinkClass}\" href=\"javascript:void(0);\" " +
												"tabIndex=\"-1\">${page}</a></li>"),
				template = this.options.pagerRecordsLabelTemplate,
				self = this,
				pagerRight = null,
				vpc = this.options.visiblePageCount,
				val = 0,
				recordsLabel = null;
			this._deleteOld();
			/*M.K. 2/26/2015 Fix for bug 188752: When cancelling pagerRendering event, changing the pageSize will result in a null exception */
			this._initLoadingIndicator();
			noCancel = this._trigger(this.events.pagerRendering, null,
					{
						dataSource: this.grid.dataSource,
						owner: this
					});
			if (noCancel) {
				if (this.grid._shouldResetPaging) {
					this.options.currentPageIndex = 0;
					this.grid._shouldResetPaging = false;
				}
				/* empty everything but the summary label */
				if (this._plabel().length > 0) {
					this._pager().find(".ui-iggrid-paging").remove();
				} else {
					this._pager().empty();
				}
				if (this.grid.dataSource.pageSizeDirty()) {
					this.options.currentPageIndex = this.grid.dataSource.pageIndex();
					this.grid.dataSource.pageSizeDirty(false);
				}
				/* check if we have virtualization enabled or not */
				if (this._pager().length === 0) {
					id = this.grid.element[ 0 ].id;
					pager = $(pagerHtml).appendTo(this.grid.container());
				} else {
					pager = this._pager();
				}
				/* M.H. 1 Feb 2016 Fix for bug 212827: igGrid gets much smaller in height than is expected when it is resized to be small in window resize event. */
				if (this.grid.hasFixedColumns()) {
					pager.css("clear", "both");
				}
				/* apply pager class */
				pager.addClass(this.css.pagerClass);
				if (this.options.showPagerRecordsLabel) {
					/* calculate startRecord and endRecord */
					recordsCount = this.grid.dataSource.totalRecordsCount() > 0 ?
										this.grid.dataSource.totalRecordsCount() :
										this.grid.dataSource.totalLocalRecordsCount();
					startRecord = this.options.currentPageIndex === 0 ? 1 :
									this.options.currentPageIndex * this.pageSize() + 1;

					/* special case when there is filtering (Bug #67998) */
					localRecordsCount = this._getDSLocalRecordsCount();
					if (this.grid.dataSource._filter) {
						recordsCount = localRecordsCount;
						filtering = this.grid.element.data("igTreeGridFiltering");
						if (filtering) {
							tmpl = filtering._getFilterSummaryPagerTemplate();
							/* if tmpl is null then use default pagerRecordsLabelTemplate */
							if (tmpl !== undefined && tmpl !== null) {
								template = tmpl;
							}
						}
					}
					/* M.H. 11 August 2015 Fix for bug 203649: Total record count in pager is incorrect after update datasource */
					if (startRecord > recordsCount) {
						startRecord = this.grid.dataSource.pageIndex() * this.pageSize() + 1;
						this.options.currentPageIndex = this.grid.dataSource.pageIndex();
					}
					endRecord = this.options.currentPageIndex === 0 && this.pageSize() <= recordsCount ?
							this.pageSize() : startRecord + this.pageSize() > recordsCount ?
												recordsCount :
												(startRecord - 1) + this.pageSize();

					if (this.grid.dataSource.totalLocalRecordsCount() === 0) {
						/* nothing to show */
						startRecord = 0;
						endRecord = 0;
					}
					/* check if dataView has less records than page size */
					if (endRecord > localRecordsCount && this.options.type === "local") {
						endRecord = localRecordsCount;
					}
					/* render label */
					/* M.H. 8 Mar 2013 Fix for bug #135130: When paging is defined after filtering and on filtering the footer is pagerRecordsLabelTemplate istead of filterSummaryTemplate */
					if (this.grid.container()
								.find(".ui-iggrid-footer .ui-iggrid-results")
								.data("overrideLabel") === 0 ||
											this._overrideLabel ||
											!this.grid.dataSource._filter) {
						this._plabel().remove();
						template = template
										.replace("${startRecord}", startRecord)
										.replace("${endRecord}", endRecord)
										.replace("${recordCount}", recordsCount);
						recordsLabel = $("<span>" + template + "</span>")
											.appendTo(pager)
											.attr("id", pager[ 0 ].id + "_label")
											.addClass(this.css.pagerRecordsLabel)
											.attr("title", this.options.pagerRecordsLabelTooltip)
											.show();
					} else if (this._plabel().length === 0) {
						recordsLabel = $("<span></span>")
											.appendTo(pager)
											.attr("id", pager[ 0 ].id + "_label")
											.addClass(this.css.pagerRecordsLabel)
											.attr("title", this.options.pagerRecordsLabelTooltip)
											.show();
					}
					if (recordsLabel) {
						recordsLabel.data("hideflag", false);
					}
					this._plabel().show();
					this._overrideLabel = false;
				}
				/* get page count from the data source and render a span for each page index */
				pageCount = this.grid.dataSource.pageCount();
				/* A.T. render the container for first page, prev page, the page links, next page and last page */
				pagerRight = $("<div></div>").appendTo(pager).addClass(this.css.pagerRightAreaContainer);
				if (this.options.showFirstLastPages === true) {
					this._renderFirstPage(pagerRight);
				}
				if (this.options.showPrevNextPages === true) {
					this._renderPrevPage(pagerRight);
				}
				/* if the pageCountLimit is exceeded, switch to dropdown rendering automatically ! */
				if (this.grid.dataSource.pageCount() <= this.options.pageCountLimit) {
					pageList = $("<ul></ul>").appendTo(pagerRight).addClass(this.css.pageList);
					/* M.H. 7 Nov 2013 Fix for bug #157047: Loading indicator does not disappear when changing page using keyboard navigation */
					if (!this._pagerEvents) {
						this._pagerEvents = {
							keydown: function (event) {
								if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
									/*M.K. 12/17/2014 Fix for bug 186560: Keyboard interaction with the pager doesn't throw paging events */
									var noCancel = true, noCancelBinding = true,
										newIndex = $(event.currentTarget).closest("li").data("pageIndex");
									if (newIndex !== self.options.currentPageIndex) {
										noCancel = self._trigger(self.events.pageIndexChanging, null,
																	{
																		newPageIndex: newIndex,
																		currentPageIndex: self.options.currentPageIndex,
																		owner: self
																	});
										self._shouldFirePageIndexChanged = true;
										if (noCancel) {
											noCancelBinding = self.grid._trigger(self.grid.events.dataBinding, null,
																	{
																		owner: self.grid,
																		dataSource: self.grid.dataSource
																	});
											if (noCancelBinding) {
												/* L.A. 23 April 2012 Fixed bug #88118 The loading indicator is not displayed when keyboard navigation is used on the page index buttons (remote paging) */
												self._showLoading();
												self.pageIndex(newIndex);
											}
										}
									}
									event.stopPropagation();
									event.preventDefault();
								}
							},
							focus: function (event) {
								$(event.currentTarget).closest("li").addClass(self.css.pageFocused);
							},
							blur: function (event) {
								$(event.currentTarget).closest("li").removeClass(self.css.pageFocused);
							}
						};
					} else {
						this._pager().undelegate("li", this._pagerEvents);
					}
					this._pager().delegate("li", this._pagerEvents);
					val = Math.floor(parseInt(vpc, 10) / 2);
					/* we will render the number of page links specified by visiblePageCount */
					startPageIndex = this.options.currentPageIndex - val;
					if (startPageIndex < 0) {
						startPageIndex = 0;
					}
					endPageIndex = this.options.currentPageIndex + val;
					/* compensate the indices, so that we always get the same count */
					if (this.options.currentPageIndex - startPageIndex < val) {
						endPageIndex += val - (this.options.currentPageIndex - startPageIndex);
					}
					if (this.grid.dataSource.pageCount() - (this.options.currentPageIndex + 1) < val &&
															vpc % 2 !== 0) {
						startPageIndex = startPageIndex -
							(val - (this.grid.dataSource.pageCount() -
									(this.options.currentPageIndex + 1)));
					}
					if (endPageIndex >= this.grid.dataSource.pageCount()) {
						endPageIndex = this.grid.dataSource.pageCount() - 1;
					}
					if (startPageIndex < 0) {
						startPageIndex = 0;
					}
					if (vpc % 2 === 0 && endPageIndex - startPageIndex < vpc - 1) {
						startPageIndex -= (vpc - 1) - (endPageIndex - startPageIndex);
					}
					if (startPageIndex < 0) {
						startPageIndex = 0;
					}
					for (i = startPageIndex; i <= endPageIndex && i - startPageIndex < vpc; i++) {
						html = pageLinkHtml
							.replace("${page}", (i + 1))
							.replace("${pageLinkClass}", i === this.options.currentPageIndex ?
													this.css.pageLinkCurrent : this.css.pageLink);
						html = html
							.replace("${pageClass}", i === this.options.currentPageIndex ?
													this.css.pageCurrent : this.css.page);
						$(html)
							.appendTo(pageList)
							.attr("title", this.options.pageTooltipFormat.replace("${index}", i + 1))
							.data("pageIndex", i);
					}
				} else {
					/* render dropdown */
					pagesArray = [];
					for (i = 1; i <= pageCount; i++) {
						pagesArray.push(i + this._empty());
					}
					/* create the igEditor */
					dropDownContainer = $("<div></div>")
									.appendTo(pagerRight)
									.addClass(this.css.pageDropDownContainer)
									.attr("title", this.options.currentPageDropDownTooltip);
					$("<span></span>")
						.appendTo(dropDownContainer)
						.text(this.options.currentPageDropDownLeadingLabel)
						.addClass(this.css.pageDropDownLabels);
					/* render igEditor now */
					kbrdNavEvents = this._getEditorKeyboardNavigation();
					edtrOpts = {
						listItems: pagesArray,
						dropDownAttachedToBody: true,
						visibleItemsCount: this.dropDownVisibleItemsCount,
						/*dropDownOnReadOnly: true, */
						suppressNotifications: true,
						isLimitedToListValues: true,
						width: this.options.defaultDropDownWidth,
						allowNullValue: false,
						buttonType: "dropdown",
						/* drop down should be opened when editor is focused */
						value: this.options.currentPageIndex + 1,
						minValue: 1,
						maxValue: pagesArray[ pagesArray.length - 1 ],
						dropDownListOpened: $.proxy(this._onSelectDropDownOpened, this),
						/*keyup: $.proxy(this._onSelectDropDownPageIndex, this) */
						textChanged: $.proxy(this._onSelectDropDownPageIndex, this)
					};
					edtrOpts = $.extend(edtrOpts, kbrdNavEvents);
					this._curPageDD = $("<span />").appendTo(dropDownContainer)
						.addClass(this.css.pageDropDownContainer).igNumericEditor(edtrOpts);
					this._curPageDD.igNumericEditor("selectedListIndex", this.options.currentPageIndex);
					/* render trailing label */
					$("<span></span>")
						.appendTo(dropDownContainer)
						.text(this.options.currentPageDropDownTrailingLabel.replace("${count}",
											this.grid.dataSource.pageCount()))
						.addClass(this.css.pageDropDownLabels);
				}
				if (this.options.showPrevNextPages === true) {
					this._renderNextPage(pagerRight);
				}
				if (this.options.showFirstLastPages === true) {
					this._renderLastPage(pagerRight);
				}
				if (this.options.showPageSizeDropDown && this.options.pageSizeDropDownLocation === "inpager") {
					this._renderPageSizeDropDown(pagerRight);
				}
				this._bindEvents(pager);
				/* hide loading message */
				this._hideLoading(this.grid.element.children("tbody"));
				pager.show();
				/* M.H. 8 May 2013 Fix for bug #138949: Inconsistent event sequence between local and remote pageSizeChanged event */
				if (this._shouldFirePageSizeChanged) {
					this._shouldFirePageSizeChanged = false;
					this._trigger(this.events.pageSizeChanged, null, { pageSize: this.pageSize(), owner: this });
				}
				/* fire pager rendered event */
				this._trigger(this.events.pagerRendered, null,
						{
							dataSource: this.grid.dataSource,
							owner: this
						});
				if (this._shouldFirePageIndexChanged) {
					this._shouldFirePageIndexChanged = false;
					this._trigger(this.events.pageIndexChanged, null,
						{
							pageIndex: this.options.currentPageIndex,
							owner: this
						});
				}
			}
		},
		_getEditorKeyboardNavigation: function () {
			return {
				focus: function (e, args) {
					var edtr = args.owner;
					if (edtr._dropDownList && !edtr._dropDownList.is(":visible")) {
						edtr.showDropDown();
					}
				},
				keydown: function (e, args) {
					var edtr = args.owner, $activeItem;
					/* when editor is focused and dropdown is opened - implement UP/DOWN/Enter - change default behavior of keyboard navigation of the editor */
					if (edtr._dropDownList && edtr._dropDownList.is(":visible")) {
						if (e.keyCode === $.ui.keyCode.DOWN) {// activate next page
							edtr._hoverNextDropDownListItem();
							e.preventDefault();// cancel event - prevent default behavior
						} else if (e.keyCode === $.ui.keyCode.UP) {// activate prev page
							edtr._hoverPreviousDropDownListItem();
							e.preventDefault();
						} else if (e.keyCode === $.ui.keyCode.ENTER) {// select active page
							$activeItem = edtr._dropDownList
											.children(".ui-igedit-listitem")
											.filter("[data-active=\"true\"]");
							if ($activeItem.length) {
								edtr._triggerListItemClick($activeItem);
							}
							e.preventDefault();
						}
					}
				}
			};
		},
		_onSelectDropDownOpened: function (event, args) {
			/* when dropdown is opened- scroll to the selected item */
			var dd = args.owner,
				$ddCont = dd.dropDownContainer(),
				$selItem = dd.getSelectedListItem();
			if (!$selItem.length) {
				return;
			}
			$ddCont.scrollTop($selItem.position().top -
					((this.dropDownVisibleItemsCount - 1) * $selItem.outerHeight()));
		},
		_onSelectDropDownPageIndex: function (event, args) {
			var self = this;
			/* M.H. 18 Jul 2013 Fix for bug #146362: Unable to type more than one digit in the paging dropdown input and go to page with number several digits long */
			if (this._timeoutId !== undefined) {
				clearTimeout(this._timeoutId);
			}
			/*M.K. 4/2/2015 Fix for bug 190905: delayOnPageChanged incorrectly applies when explicitly choosing a page from the paging dropdown */
			if (parseInt(this.options.delayOnPageChanged, 10) === 0 ||
				(event && event.originalEvent && event.originalEvent.type !== "keypress")) {
				this._dropDownPageIndex(event, args);
			} else {
				this._timeoutId = setTimeout(function () {
					self._dropDownPageIndex(event, args);
				}, this.options.delayOnPageChanged);
			}
		},
		_dropDownPageIndex: function (event, args) {
			var noCancel = true, noCancelBinding = true, parsedString, editor = args.owner,
				val = args.text, pageInd = parseInt(val, 10),
				mapping = {
					"１": "1",
					"２": "2",
					"３": "3",
					"４": "4",
					"５": "5",
					"６": "6",
					"７": "7",
					"８": "8",
					"９": "9",
					"０": "0"
				};
			if (val === "") {
				return;
			}
			if (isNaN(pageInd)) {
				parsedString = val;
				$.each(mapping, function (jpVal, engVal) {
					parsedString = parsedString.replace(new RegExp(jpVal, "g"), engVal);
				});
				pageInd = parseInt(parsedString, 10);
				if (isNaN(pageInd)) {
					/* retrieve previous page index */
					editor.value(this.options.currentPageIndex + 1);
					return;
				}
			}
			/* not valid value - retrieve to previous value and return */
			if (pageInd <= 0 || pageInd > this.grid.dataSource.pageCount()) {
				editor.hideDropDown();
				editor.value(this.options.currentPageIndex + 1);
				return;
			}
			if (pageInd - 1 === this.options.currentPageIndex) {
				editor.hideDropDown();
				return;
			}
			noCancel = this._trigger(this.events.pageIndexChanging, null,
											{
												newPageIndex: pageInd - 1,
												currentPageIndex: this.options.currentPageIndex,
												owner: this
											});
			if (noCancel) {
				noCancelBinding = this.grid._trigger(this.grid.events.dataBinding, null,
														{
															owner: this.grid,
															dataSource: this.grid.dataSource
														});
				if (noCancelBinding) {
					editor.selectedListIndex(pageInd - 1);
					this._shouldFirePageIndexChanged = true;
					if (!isNaN(pageInd)) {
						/*M.K. Fix for bug 186430: When user enters Japanese character to the Page-Editor which is displaying current page number., NaN is displayed there. */
						this._showLoading();
						this.pageIndex(pageInd - 1);
					}
				}
			}
		},
		_renderPrevPage: function (pager) {
			/* we render one div, which has one span for the prev image, and one span for the prev text */
			var prev, self = this, imgspan;
			prev = $("<div></div>")
				.appendTo(pager)
				.addClass(this.css.prevPage)
				.attr("title", this.options.prevPageTooltip);
			if (this.options.currentPageIndex === 0) {
				$("<span></span>").appendTo(prev).addClass(this.css.prevPageImageDisabled);
				$("<span></span>")
					.appendTo(prev)
					.addClass(this.css.prevPageLabelDisabled)
					.append(this.options.prevPageLabelText);
			} else {
				prev.bind("mousedown", $.proxy(this._prevPage, this));
				imgspan = $("<span></span>").appendTo(prev).addClass(this.css.prevPageImage);
				$("<span></span>")
					.appendTo(prev)
					.addClass(this.css.prevPageLabel)
					.append(this.options.prevPageLabelText);
				imgspan.wrap(toStaticHTML("<a href=\"javascript:void(0);\" tabIndex=\"-1\"></a>"));
				prev.attr("tabIndex", "0").bind({
					keydown: function (event) {
						if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
							self._prevPage();
							event.stopPropagation();
							event.preventDefault();
						}
					},
					focus: function (event) {
						$(event.currentTarget)
							.closest("div")
							.removeClass("ui-state-default")
							.addClass("ui-state-focus");
					},
					blur: function (event) {
						$(event.currentTarget)
							.closest("div")
							.removeClass("ui-state-focus")
							.addClass("ui-state-default");
					}
				});
			}
		},
		_renderNextPage: function (pager) {
			/* we render one div, which has one span for the next image, and one span for the next text */
			var next, self = this, imgspan;
			next = $("<div></div>")
						.appendTo(pager)
						.addClass(this.css.nextPage)
						.attr("title", this.options.nextPageTooltip);
			if (this.options.currentPageIndex === this.grid.dataSource.pageCount() - 1) {
				$("<span></span>")
					.appendTo(next)
					.addClass(this.css.nextPageLabelDisabled)
					.append(this.options.nextPageLabelText);
				$("<span></span>").appendTo(next).addClass(this.css.nextPageImageDisabled);
			} else {
				next.bind("mousedown", $.proxy(this._nextPage, this));
				$("<span></span>")
					.appendTo(next)
					.addClass(this.css.nextPageLabel)
					.append(this.options.nextPageLabelText);
				imgspan = $("<span></span>").appendTo(next).addClass(this.css.nextPageImage);
				imgspan.wrap(toStaticHTML("<a href=\"javascript:void(0);\" tabIndex=\"-1\"></a>"));
				next.attr("tabIndex", "0").bind({
					keydown: function (event) {
						if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
							self._nextPage();
							event.stopPropagation();
							event.preventDefault();
						}
					},
					focus: function (event) {
						$(event.currentTarget)
							.closest("div")
							.removeClass("ui-state-default")
							.addClass("ui-state-focus");
					},
					blur: function (event) {
						$(event.currentTarget)
							.closest("div")
							.removeClass("ui-state-focus")
							.addClass("ui-state-default");
					}
				});
			}
		},
		_renderFirstPage: function (pager) {
			var first, self = this, imgspan;
			first = $("<div></div>")
						.appendTo(pager)
						.addClass(this.css.firstPage)
						.attr("title", this.options.firstPageTooltip);
			if (this.options.currentPageIndex === 0) {
				$("<span></span>").appendTo(first).addClass(this.css.firstPageImageDisabled);
				$("<span></span>")
					.appendTo(first)
					.addClass(this.css.firstPageLabelDisabled)
					.append(this.options.firstPageLabelText);
			} else {
				first.bind("mousedown", $.proxy(this._firstPage, this));
				imgspan = $("<span></span>").appendTo(first).addClass(this.css.firstPageImage);
				$("<span></span>")
					.appendTo(first)
					.addClass(this.css.firstPageLabel)
					.append(this.options.firstPageLabelText);
				imgspan.wrap(toStaticHTML("<a href=\"javascript:void(0);\" tabIndex=\"-1\"></a>"));
				first.attr("tabIndex", "0").bind({
					keydown: function (event) {
						if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
							self._firstPage();
							event.stopPropagation();
							event.preventDefault();
						}
					},
					focus: function (event) {
						$(event.currentTarget)
							.closest("div")
							.removeClass("ui-state-default")
							.addClass("ui-state-focus");
					},
					blur: function (event) {
						$(event.currentTarget)
							.closest("div")
							.removeClass("ui-state-focus")
							.addClass("ui-state-default");
					}
				});
			}
		},
		_renderLastPage: function (pager) {
			/* we render one div, which has one span for the next image, and one span for the next text */
			var last, self = this, imgspan;
			last = $("<div></div>")
					.appendTo(pager)
					.addClass(this.css.lastPage)
					.attr("title", this.options.lastPageTooltip);
			if (this.options.currentPageIndex === this.grid.dataSource.pageCount() - 1) {
				$("<span></span>")
					.appendTo(last)
					.addClass(this.css.lastPageLabelDisabled)
					.append(this.options.lastPageLabelText);
				$("<span></span>").appendTo(last).addClass(this.css.lastPageImageDisabled);
			} else {
				last.bind("mousedown", $.proxy(this._lastPage, this));
				$("<span></span>")
					.appendTo(last)
					.addClass(this.css.lastPageLabel)
					.append(this.options.lastPageLabelText);
				imgspan = $("<span></span>").appendTo(last).addClass(this.css.lastPageImage);
				imgspan.wrap(toStaticHTML("<a href=\"javascript:void(0);\" tabIndex=\"-1\"></a>"));
				last.attr("tabIndex", "0").bind({
					keydown: function (event) {
						if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
							self._lastPage();
							event.stopPropagation();
							event.preventDefault();
						}
					},
					focus: function (event) {
						$(event.currentTarget)
							.closest("div")
							.removeClass("ui-state-default")
							.addClass("ui-state-focus");
					},
					blur: function (event) {
						$(event.currentTarget)
							.closest("div")
							.removeClass("ui-state-focus")
							.addClass("ui-state-default");
					}
				});
			}
		},
		_renderPageSizeDropDown: function (parent, prepend) {
			/* render label here */
			var i, ps, editorId = this.grid.id() + "_editor",
				cClass, items, self = this, edtrOpts, kbrdNavEvents;
			cClass = this.options.pageSizeDropDownLocation === "above" ?
							this.css.pageSizeDropDownContainerAbove :
							this.css.pageSizeDropDownContainer;
			if (prepend) {
				parent = $("<div></div>").prependTo(parent).addClass(cClass);
			} else {
				parent = $("<div></div>").appendTo(parent).addClass(cClass);
			}
			parent.attr("title", this.options.pageSizeDropDownTooltip);
			parent = $("<div></div>").appendTo(parent).addClass(this.css.pagingResults);
			if (prepend) {
				$("<span>" + this.options.pageSizeDropDownTrailingLabel + "</span>")
					.prependTo(parent)
					.addClass(this.css.pageSizeLabel);
				$("<span></span>").prependTo(parent).attr("id", editorId);//
				$("<span>" + this.options.pageSizeDropDownLabel + "</span>")
					.prependTo(parent)
					.addClass(this.css.pageSizeLabel);
			} else {
				$("<span>" + this.options.pageSizeDropDownLabel + "</span>")
					.appendTo(parent)
					.addClass(this.css.pageSizeLabel);
				$("<span></span>").appendTo(parent).attr("id", editorId).addClass(this.css.pageSizeDropDown);
				$("<span>" + this.options.pageSizeDropDownTrailingLabel + "</span>")
					.appendTo(parent)
					.addClass(this.css.pageSizeLabel);
			}
			items = (this.options.pageSizeList === null ||
					this.options.pageSizeList.length === 0) ?
												[ 5, 10, 20, 25, 50, 75, 100 ] :
												this.options.pageSizeList;
			if ($.type(items) === "string") {
				items = items.split(",");
			}
			ps = this.pageSize();
			kbrdNavEvents = this._getEditorKeyboardNavigation();
			edtrOpts = {
				width: this.options.defaultDropDownWidth,
				dropDownAttachedToBody: true,
				suppressNotifications: true,
				buttonType: "dropdown",
				/*isLimitedToListValues: true, */
				dropDownOnReadOnly: true,
				listItems: items,
				allowNullValue: false,
				readOnly: true,
				textAlign: "left",
				value: ps,
				valueChanged: function (e, args) {
					/* M.H. 11 Sep 2015 Fix for bug 206191: PageSizeDropDown does not work when pageSizeDropDownLocation is "inpager" */
					/* When pageSizeDropDownLocation is inpager - it is called _dataRendered which removes all DOM elements in pager container. This causes to be called Destroy of the editor(while the event valueChanged is processed from the editor which causes JS error to be thrown) */
					/* as workaround: call changePageSize async - first valueChanged to be executed and processed from the editor - after that Destroy is called */
					if (self.options.pageSizeDropDownLocation === "inpager") {
						setTimeout(function () {
							self._changePageSize(e, args);
						}, 0);
					} else {
						self._changePageSize(e, args);
					}
				},
				rendered: function (e, args) {
					var edtr = args.owner;
					/* set css class of outer DIV container - because the editor is instantiated on span */
					edtr.editorContainer().addClass(self.css.pageSizeDropDown);
					edtr.field().removeClass("ui-state-disabled");
					edtr.editorContainer().removeClass("ui-state-disabled");
				}
			};
			edtrOpts = $.extend(edtrOpts, kbrdNavEvents);
			this._pageSizeDD = this.grid.container().find("#" + editorId)
				.igNumericEditor(edtrOpts);
			for (i = 0; i < items.length; i++) {
				if (items[ i ] === ps) {
					this._pageSizeDD.igNumericEditor("selectedListIndex", i);
					break;
				}
			}
			/* M.H. 26 Jul 2013 Fix for bug #146248: When enabled with various other features and the width of the columns causes a horizontal scrollbar, the column headers do not align with the columns */
			/* it is an edge case - the issue is caused by getting innerHTML of the input in igEdidor source. So one possible solution is just to force the browser to re-render header */
			if ($.ig.util.isIE8 === true &&
				this.grid.options._isHierarchicalGrid &&
				this.grid.options.fixedHeaders) {
				$("<col></col>").appendTo(this.grid.headersTable().find("colgroup")).remove();
				/*colgroup.find("col:last").detach().appendTo(colgroup); */
			}
			this._pageSizeDropDownRendered = true;
			/* S.S. September 21, 2012 - An event after rendering the dropdown container for the paging widget is needed by the Column Moving */
			/* to recalculate the header cell positions. */
			this._trigger("pagingdropdownrendered");
		},
		_changePageSize: function (event, args) {
			/* M.H. 20 Nov 2012 Fix for bug #127328 */
			var noCancel = true, size = args.owner.value();
			noCancel = this._trigger(this.events.pageSizeChanging, null,
									{
										currentPageSize: this.pageSize(),
										newPageSize: size,
										owner: this
									});
			if (noCancel) {
				/*A.T. 29 Nov 2011 - Fix for #91994 - moving the dirty event trigger before the page size change */
				/* that's necessary because if everything is bound remotely, the filtering/sorting/etc params can be encoded */
				/* in advance, before dirty is triggered, which will result in the filtering or sorting being persisted */
				/* trigger UI dirty on the grid */
				/* M.H. 12 Dec 2013 Fix for bug #159725: Filter option Tooltip displays as "No filter applied" after Page size is changed */
				if (this.options.type === "remote") {
					this.grid.element.trigger("iggriduidirty", { owner: this });
				} else {
					this.grid.element.trigger("iggriduisoftdirty", { owner: this });
				}
				this.pageSize(size);
				/* M.H. 8 May 2013 Fix for bug #138949: Inconsistent event sequence between local and remote pageSizeChanged event */
				if (this.options.type === "remote") {
					this._shouldFirePageSizeChanged = true;
				} else {
					this._trigger(this.events.pageSizeChanged, null, { pageSize: size, owner: this });
				}
			} else {
				/* M.H. 13 May 2013 Fix for bug #141665: Canceling the pageSizeChanging and pageIndexChanging events does not revert the state of the pager drop downs. */
				args.owner.value(this.pageSize());
				/*args.owner.value(size); */
			}
		},
		_deleteOld: function (destroy) {
			if (this._curPageDD) {
				this._curPageDD.igNumericEditor("destroy");
				delete this._curPageDD;
			}
			/* L.A. 21 January 2013 - Fixing bug #131005 */
			/* Exception is thrown in the filtering module when jQuery 1.9.1. is used */
			if (destroy && this._pageSizeDD && this._pageSizeDD.closest("body").length > 0) {
				this._pageSizeDD.igNumericEditor("destroy");
				delete this._pageSizeDD;
			}
		},
		destroy: function () {
			/*destroys the igGridPaging feature by removing all elements in the pager area, unbinding events, and resetting data to discard data filtering on paging*/
			var pager = this._pager();
			/* rebind the grid by setting pageSize to 0 */
			this.pageSize(0);
			this._deleteOld(true);
			/* unbind events and remove elements */
			if (this.options.showPageSizeDropDown === true &&
				this.options.pageSizeDropDownLocation === "above") {
				$("#" + this.grid.container()[ 0 ].id + " .ui-iggrid-pagesizedropdowncontainerabove").remove();
			}
			/* jQuery's remove is recursive and should take care of unregistering all events and removing all child elements */
			pager.remove();
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			if (this._loadingIndicator) {
				delete this._loadingIndicator;
			}
			$.Widget.prototype.destroy.call(this);
			return this;
		},
		/* every grid feature widget should implement this */
		_injectGrid: function (gridInstance, isRebind) {
			this.grid = gridInstance;
			if (this.options.type === null) {
				/* infer type */
				this.options.type = this.grid._inferOpType();
			}
			/* M.H. 9 Dec 2014 Fix for bug #186166: Dataview is not persisting for children when remote paging is enabled */
			/*if (this.options.type === 'remote') {
				topmostGrid = this.element.closest('.ui-iggrid-root').data('igGrid');
				if (topmostGrid && topmostGrid.element.attr('id') !== this.grid.element[0].id &&
					topmostGrid.options.initialDataBindDepth === -1) {
					this.options.persist = false;
				}
			}*/
			if (!this.options.persist) {
				if (isRebind) {
					this.options.currentPageIndex = this._oPageIndex;
					this.options.pageSize = this._oPageSize;
				}
			} else {
				/* M.H. 2 Dec 2014 Fix for bug #185895: Paging does not persist the state of the children in hierarchical grid */
				this._loadPagingData();
			}
			/* delegate to the data source all paging options */
			this.grid.dataSource.settings.paging.type = this.options.type || "remote";
			/*if (!isRebind) { */
			this.grid.dataSource.settings.paging.pageIndex = this.options.currentPageIndex;
			/* M.H. 21 Oct 2014 Fix for bug #181395: When filtering is applied selected page is not persisted. */
			if (this.options.type === "local") {
				this.grid.dataSource.persistedPageIndex(this.grid.dataSource.settings.paging.pageIndex);
			}
			/*} else {
				this.grid.dataSource.settings.paging.pageIndex = 0;
				this.options.currentPageIndex = 0;
			}*/
			this.grid.dataSource.settings.paging.pageSize = parseInt(this.options.pageSize, 10);
			if (this.options.pageSizeUrlKey !== null && this.options.pageIndexUrlKey) {
				this.grid.dataSource.settings.paging.pageSizeUrlKey = this.options.pageSizeUrlKey;
				this.grid.dataSource.settings.paging.pageIndexUrlKey = this.options.pageIndexUrlKey;
			}
			if (this.options.recordCountKey !== null) {
				this.grid.dataSource.settings.responseTotalRecCountKey = this.options.recordCountKey;
			}
			this.grid.dataSource.settings.paging.enabled = true;
			/*A.T 13 April 2011 - fix for bug #72431 */
			if (this._pageSizeDD) {
				this._pageSizeDD.igNumericEditor("option", "value", this.options.pageSize);
			}
		},
		_empty: function () {
			return "";
		}
	});
	$.extend($.ui.igGridPaging, { version: "16.1.20161.2145" });
}(jQuery));
