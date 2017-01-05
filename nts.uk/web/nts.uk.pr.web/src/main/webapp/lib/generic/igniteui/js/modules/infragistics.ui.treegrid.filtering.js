
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
 *	infragistics.ui.grid.filtering.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	var _aNull = function (val) { return val === null || val === undefined; };
	/*
		igTreeGridFiltering widget. The widget is pluggable to the element where the treegrid is instantiated and the actual igTreeGrid object doesn"t know about this
		the filtering widget just attaches its functionality to the treegrid
		igTreeGridFiltering is extending igGrid Filtering
	*/
	$.widget("ui.igTreeGridFiltering", $.ui.igGridFiltering, {
		css: {
			/* classes applied to the row that matches filtering condition */
			recordMatchFiltering: "ig-igtreegrid-filter-matching-row",
			/*classes applied to the cell that matches the filtering condition */
			cellMatchFiltering: "ig-igtreegrid-filter-matching-cell",
			/* classes applied to the row that does not match filtering condition */
			recordNotMatchFiltering: "ui-igtreegrid-record-not-matchfiltering"
		},
		options: {
				/* type="string" the property in the response that will hold the total number of records in the data source */
				recordCountKey: null,
				/* type="number" specifies from which data bound level to be applied filtering - 0 is the first level */
				fromLevel: 0,
				/* type="number" specifies to which data bound level to be applied filtering - if -1 filtering should be applied to the last data bound level*/
				toLevel: -1,
				/* type="showWithAncestors|showWithAncestorsAndDescendants" If displayMode is showWithAncestorsAndDescendants, show all records that match filtering conditions and their child records, even if child records don"t match filtering conditions. If displayMode is showWithAncestors show only those records that match filtering conditions and do not show child records(if any) that don"t match filtering conditions */
				displayMode: "showWithAncestors",
				/* type="string" Specifies the name of a boolean property in the dataRecord object that indicates whether the dataRow matches the filtering conditions.
				When filtering a boolean flag  with the specified name is added on each data record object with a value of true if it matches the condition or false if it doesn"t.
				This is used mainly for internal purposes.*/
				matchFiltering: "__matchFiltering",
				/* type="string" Template that is used when filtering is applied and paging is enabled and user goes to another page. It takes precedence over the pagerRecordsLabelTemplate(option from igTreeGridPaging). If it is set to null then it is taken option from igTreeGridPaging.
				Supported options:
				${currentPageMatches} (filtering)
				${totalMatches} (filtering)
				${startRecord} (paging)
				${endRecord} (paging)
				${recordCount} (paging)
				*/
				filterSummaryInPagerTemplate: $.ig.TreeGridFiltering.locale.filterSummaryInPagerTemplate
		},
		_create: function () {
			this.element.data(
				$.ui.igGridFiltering.prototype.widgetName,
				this.element.data($.ui.igTreeGridFiltering.prototype.widgetName)
			);
			$.ui.igGridFiltering.prototype._create.apply(this, arguments);
		},
		_getFilterSummaryPagerTemplate: function () {
			var template = this.options.filterSummaryInPagerTemplate,
				matchesCount, countMatchesPerPage = 0;
			if (!template) {
				return null;
			}
			/* calculate currentPageMatches */
			if (template.indexOf("${currentPageMatches}") > -1) {
				countMatchesPerPage = this.grid.dataSource.getFilteredRecordsCountFromDataView();
				template = template.replace("${currentPageMatches}", countMatchesPerPage);
			}
			/* get totalMatches */
			if (template.indexOf("${totalMatches}") > -1) {
				matchesCount = this.getFilteringMatchesCount();
				template = template.replace("${totalMatches}", matchesCount);
			}
			return template;
		},
		_transformCss: function (cssClass, dataRow) {
			var matchFiltering, grid = this.grid, ds = grid.dataSource;
			if (this._gridTransformCssCallback) {
				cssClass = this._gridTransformCssCallback.apply(grid, arguments);
			}
			if (this._filteringApplied()) {
				matchFiltering = ds.settings.treeDS.filtering.matchFiltering;
				if (cssClass !== "") {
					cssClass += " ";
				}
				if (!_aNull(matchFiltering)) {
					if (dataRow[ matchFiltering ]) {
						cssClass += this.css.recordMatchFiltering;
					} else {
						cssClass += this.css.recordNotMatchFiltering;
					}
				}
			}
			return cssClass;
		},
		_filteringApplied: function () {
			/* returns whether filtering is applied */
			var ds = this.grid.dataSource, expr = ds.settings.filtering.expressions;
			if (this.options.type === "local") {
				return ds._filter;
			}
			return expr && expr.length;
		},
		getFilteringMatchesCount: function () {
			/* returns the count of data records that match filtering conditions
			returnType="number" count of filtered records
			*/
			var o = this.options, ds = this.grid.dataSource, matches;
			if (o.type === "local" || (o.type === "remote" && ds.hasTotalRecordsCount() === false)) {
				if (ds._filter) {
					/* matches = this.grid.dataSource.dataView().length; // this should be used when applyToAllData = false */
					matches = ds.getFilteredRecordsCount();
				} else {
					matches = ds.flatDataView().length;
				}
				/* we need that when, say, both paging and filtering are enabled, and both are remote */
			} else {
				matches = ds.getFilteringMatchRecordsCount();
			}
			return matches;
		},
		destroy: function () {
			$.ui.igGridFiltering.prototype.destroy.apply(this, arguments);
			this.element.removeData($.ui.igGridFiltering.prototype.widgetName);
		},
		_injectGrid: function (gridInstance, isRebind) {
			var ds, o = this.options;
			$.ui.igGridFiltering.prototype._injectGrid.apply(this, arguments);
			if (!isRebind) {
				if (this.grid._transformCssCallback) {
					this._gridTransformCssCallback = this.grid._transformCssCallback;
				}
				this.grid._transformCssCallback = $.proxy(this._transformCss, this);
			}
			ds = this.grid.dataSource;
			if (ds && ds.settings && ds.settings.treeDS) {
				ds.settings.filtering.enabled = true;
				ds.settings.treeDS.filtering.fromLevel = o.fromLevel;
				ds.settings.treeDS.filtering.toLevel = o.toLevel;
				ds.settings.treeDS.filtering.displayMode = o.displayMode;
				ds.settings.treeDS.filtering.matchFiltering = o.matchFiltering;
			}
			if (o.recordCountKey !== null) {
				ds.settings.responseTotalRecCountKey = o.recordCountKey;
			}
		}
	});
	$.extend($.ui.igTreeGridFiltering, { version: "16.1.20161.2145" });
}(jQuery));



