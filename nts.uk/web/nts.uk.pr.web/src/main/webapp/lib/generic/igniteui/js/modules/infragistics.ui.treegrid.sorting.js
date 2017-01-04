
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
 *	infragistics.ui.grid.sorting.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igTreeGridSorting widget. The widget is pluggable to the element where the treegrid is instantiated and the actual igTreeGrid object doesn't know about this
		the filtering widget just attaches its functionality to the treegrid
		igTreeGridSorting is extending igGrid Sorting
	*/
	$.widget("ui.igTreeGridSorting", $.ui.igGridSorting, {
		css: {},
		options: {
			/* type="number" specifies from which data bound level to be applied sorting - 0 is the first level */
			fromLevel: 0,
			/* type="number" specifies to which data bound level to be applied sorting - if -1 sorting should be applied to the last data bound level */
			toLevel: -1
		},
		_create: function () {
			this.element.data(
				$.ui.igGridSorting.prototype.widgetName,
				this.element.data($.ui.igTreeGridSorting.prototype.widgetName)
			);
			$.ui.igGridSorting.prototype._create.apply(this, arguments);
		},
		isColumnSorted: function (columnKey) {
			/* returns whether a column with the specified columnKey is sorted(taken from the data source sorting expressions)
				paramType="string" Column key (string)
				returnType="bool"
			*/
			var i, se = this.grid.dataSource.settings.sorting.expressions;
			if (!se || !columnKey || !se.length) {
				return false;
			}
			for (i = 0; i < se.length; i++) {
				if (se[ i ].fieldName === columnKey) {
					return se[ i ].isSorting;
				}
			}
			return false;
		},
		destroy: function () {
			$.ui.igGridSorting.prototype.destroy.apply(this, arguments);
			this.element.removeData($.ui.igGridSorting.prototype.widgetName);
		},
		_injectGrid: function (gridInstance, isRebind) {
			var ds;
			$.ui.igGridSorting.prototype._injectGrid.apply(this, arguments);
			ds = this.grid.dataSource;
			if (ds && ds.settings && ds.settings.treeDS) {
				ds.settings.treeDS.sorting.fromLevel = this.options.fromLevel;
				ds.settings.treeDS.sorting.toLevel = this.options.toLevel;
			}
			/* M.H. 8 Oct 2015 Fix for bug 206552: Collapsed cells don't get sorting style after sort and expand in igTreeGrid */
			if (!isRebind && !this._cellStyleSubscriberAdded) {
				this._cellStyleSubscriberAdded = true;
				this.grid._cellStyleSubscribers.push($.proxy(this._applyActiveSortCellStyle, this));
			}
		}
	});
	$.extend($.ui.igTreeGridSorting, { version: "16.1.20161.2145" });
}(jQuery));



