/*!@license
 * Infragistics.Web.ClientUI Sparkline 16.1.20161.2145
 *
 * Copyright (c) 2011-2016 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 *	jquery-1.4.4.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	infragistics.util.js
 *	infragistics.ui.basechart.js
 */

/*global jQuery */
if (typeof (jQuery) !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {
	var _aNull = function (v, nan) {
		return v === null || v === undefined || (nan && typeof v === 'number' && isNaN(v));
	};
    // igSparkline definition
	$.widget('ui.igSparkline', $.ui.igBaseChart, {
		css: {
			/* Get the class applied to main element: ui-sparkline ui-corner-all ui-widget-content */
			chart: "ui-sparkline ui-corner-all ui-widget-content",
			/* Get the class applied to the tooltip element: ui-sparkline-tooltip ui-widget-content ui-corner-all */
			tooltip: "ui-sparkline-tooltip ui-widget-content ui-corner-all"
		},
		options: {
			/* type="string|number" The width of the sparkline. It can be set as a number in pixels, string (px) or percentage (%).
				string The widget width can be set in pixels (px) and percentage (%).
				number The widget width can be set as a number
			*/
			width: null,
			/* type="string|number" The height of the sparkline. It can be set as a number in pixels, string (px) or percentage (%).
				string The widget height can be set in pixels (px) and percentage (%).
				number The widget height can be set as a number
			*/
			height: null,
				/* type="string" Gets or sets the sparkline brush.
	*/
	brush: null,
	/* type="string" Gets or sets the negative brush of the sparkline.
	*/
	negativeBrush: null,
	/* type="string" Gets or sets the marker brush of the sparkline.
	*/
	markerBrush: null,
	/* type="string" Gets or sets the negative marker brush of the sparkline.
	*/
	negativeMarkerBrush: null,
	/* type="string" Gets or sets the first marker brush of the sparkline.
	*/
	firstMarkerBrush: null,
	/* type="string" Gets or sets the last marker brush of the sparkline.
	*/
	lastMarkerBrush: null,
	/* type="string" Gets or sets the high marker brush of the sparkline.
	*/
	highMarkerBrush: null,
	/* type="string" Gets or sets the low marker brush of the sparkline.
	*/
	lowMarkerBrush: null,
	/* type="string" Gets or sets the trendline brush of the sparkline.
	*/
	trendLineBrush: null,
	/* type="string" Gets or sets the horizontal axis line brush of the sparkline.
	*/
	horizontalAxisBrush: null,
	/* type="string" Gets or sets the vertical axis line brush of the sparkline.
	*/
	verticalAxisBrush: null,
	/* type="string" Gets or sets the normal range brush of the sparkline.
	*/
	normalRangeFill: null,
	/* type="visible|collapsed" Gets or sets the display state of the horizontal axis.
	visible type="string" 
	collapsed type="string" 
	*/
	horizontalAxisVisibility: "collapsed",
	/* type="visible|collapsed" Gets or sets the display state of the vertical axis.
	visible type="string" 
	collapsed type="string" 
	*/
	verticalAxisVisibility: "collapsed",
	/* type="visible|collapsed" Gets or sets the marker visibility of the sparkline.
	visible type="string" 
	collapsed type="string" 
	*/
	markerVisibility: "collapsed",
	/* type="visible|collapsed" Gets or sets the negative marker visibility of the sparkline.
	visible type="string" 
	collapsed type="string" 
	*/
	negativeMarkerVisibility: "collapsed",
	/* type="visible|collapsed" Gets or sets the first marker visibility of the sparkline.
	visible type="string" 
	collapsed type="string" 
	*/
	firstMarkerVisibility: "collapsed",
	/* type="visible|collapsed" Gets or sets the last marker visibility of the sparkline.
	visible type="string" 
	collapsed type="string" 
	*/
	lastMarkerVisibility: "collapsed",
	/* type="visible|collapsed" Gets or sets the low marker visibility of the sparkline.
	visible type="string" 
	collapsed type="string" 
	*/
	lowMarkerVisibility: "collapsed",
	/* type="visible|collapsed" Gets or sets the high marker visibility of the sparkline.
	visible type="string" 
	collapsed type="string" 
	*/
	highMarkerVisibility: "collapsed",
	/* type="visible|collapsed" Gets or sets the normal range visibility of the sparkline.
	visible type="string" 
	collapsed type="string" 
	*/
	normalRangeVisibility: "collapsed",
	/* type="bool" Gets or sets the position of the normal range on the sparkline.
	*/
	displayNormalRangeInFront: true,
	/* type="number" Gets or sets the marker size of the sparkline.
	*/
	markerSize: -1,
	/* type="number" Gets or sets the first marker size of the sparkline.
	*/
	firstMarkerSize: -1,
	/* type="number" Gets or sets the last marker size of the sparkline.
	*/
	lastMarkerSize: -1,
	/* type="number" Gets or sets the high marker size of the sparkline.
	*/
	highMarkerSize: -1,
	/* type="number" Gets or sets the low marker size of the sparkline.
	*/
	lowMarkerSize: -1,
	/* type="number" Gets or sets the negative marker size of the sparkline.
	*/
	negativeMarkerSize: -1,
	/* type="number" Gets or sets the line thickness of the sparkline.
	*/
	lineThickness: -1,
	/* type="string" Gets or sets the string path to the value column.
	*/
	valueMemberPath: null,
	/* type="string" String identifier of a column or property name to get labels from on each item in the data source.  These labels will be retrieved from the first and last item, and displayed by the horizontal axis.
	*/
	labelMemberPath: null,
	/* type="none|linearFit|quadraticFit|cubicFit|quarticFit|quinticFit|logarithmicFit|exponentialFit|powerLawFit|simpleAverage|exponentialAverage|modifiedAverage|cumulativeAverage|weightedAverage" Gets or sets the type of trendline used by the sparkline.
	none type="string" 
	linearFit type="string" 
	quadraticFit type="string" 
	cubicFit type="string" 
	quarticFit type="string" 
	quinticFit type="string" 
	logarithmicFit type="string" 
	exponentialFit type="string" 
	powerLawFit type="string" 
	simpleAverage type="string" 
	exponentialAverage type="string" 
	modifiedAverage type="string" 
	cumulativeAverage type="string" 
	weightedAverage type="string" 
	*/
	trendLineType: "none",
	/* type="number" Gets or sets the trendline period used by the sparkline.
	*/
	trendLinePeriod: 7,
	/* type="number" Gets or sets the thickness of the sparkline's trendline.
	*/
	trendLineThickness: -1,
	/* type="number" Gets or sets the minimum value of the normal range.
	*/
	normalRangeMinimum: 0,
	/* type="number" Gets or sets the maximum value of the normal range.
	*/
	normalRangeMaximum: 0,
	/* type="line|area|column|winLoss" Gets or sets the display type of the sparkline.
	line type="string" Display the sparkline as a line.
	area type="string" Display the sparkline as a filled polygon.
	column type="string" Display the sparkline as a set of columns.
	winLoss type="string" Display the sparkline as a set of columns on a boolean scale.
	*/
	displayType: "line",
	/* type="linearInterpolate|dontPlot" Gets or sets the way null values are interpreted.
	linearInterpolate type="string" 
	dontPlot type="string" 
	*/
	unknownValuePlotting: "dontPlot",
	/* type="object" The value or content to display on the vertical axis.
	This can be set to a formatted string, such as "{0:n}", or it can be set to a DataTemplate.
	*/
	verticalAxisLabel: null,
	/* type="object" The value or content to display on the horizontal axis.
	This can be set to a formatted string, such as "{0}", or it can be set to a DataTemplate.
	*/
	horizontalAxisLabel: null,
	/* type="object" Sets or gets a function which takes an object that produces a formatted label for displaying in the chart.
	*/
	formatLabel: null

		},
		events: {
			/* cancel="true" Event which is raised before data binding.
				Function takes first argument null and second argument ui.
				Use ui.owner to obtain reference to igSparkline.
				Use ui.dataSource to obtain reference to instance of $.ig.DataSource.
			*/
			dataBinding: null,
			/* cancel="false" Event which is raised after data binding.
				Function takes first argument null and second argument ui.
				Use ui.owner to obtain reference to igSparkline.
				Use ui.data to obtain reference to array actual data which is displayed by chart.
				Use ui.dataSource to obtain reference to instance of $.ig.DataSource.
			*/
			dataBound: null,
			

		},
		_create: function () {
			$.ui.igBaseChart.prototype._create.apply(this);
			var sparkline = this._chart;
			
		},
		_set_option: function (sparkline, key, value) {
			if ($.ui.igBaseChart.prototype._set_option.apply(this, arguments)) {
				return true;
			}
				switch (key) {
		case "brush":
			if (value == null) {
				sparkline.brush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.brush($tempBrush);
			}
			return true;
		case "negativeBrush":
			if (value == null) {
				sparkline.negativeBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.negativeBrush($tempBrush);
			}
			return true;
		case "markerBrush":
			if (value == null) {
				sparkline.markerBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.markerBrush($tempBrush);
			}
			return true;
		case "negativeMarkerBrush":
			if (value == null) {
				sparkline.negativeMarkerBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.negativeMarkerBrush($tempBrush);
			}
			return true;
		case "firstMarkerBrush":
			if (value == null) {
				sparkline.firstMarkerBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.firstMarkerBrush($tempBrush);
			}
			return true;
		case "lastMarkerBrush":
			if (value == null) {
				sparkline.lastMarkerBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.lastMarkerBrush($tempBrush);
			}
			return true;
		case "highMarkerBrush":
			if (value == null) {
				sparkline.highMarkerBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.highMarkerBrush($tempBrush);
			}
			return true;
		case "lowMarkerBrush":
			if (value == null) {
				sparkline.lowMarkerBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.lowMarkerBrush($tempBrush);
			}
			return true;
		case "trendLineBrush":
			if (value == null) {
				sparkline.trendLineBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.trendLineBrush($tempBrush);
			}
			return true;
		case "horizontalAxisBrush":
			if (value == null) {
				sparkline.horizontalAxisBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.horizontalAxisBrush($tempBrush);
			}
			return true;
		case "verticalAxisBrush":
			if (value == null) {
				sparkline.verticalAxisBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.verticalAxisBrush($tempBrush);
			}
			return true;
		case "normalRangeFill":
			if (value == null) {
				sparkline.normalRangeFill(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				sparkline.normalRangeFill($tempBrush);
			}
			return true;
		case "horizontalAxisVisibility":
			switch(value) {
				case "visible":
					sparkline.horizontalAxisVisibility(0);
					break;
				case "collapsed":
					sparkline.horizontalAxisVisibility(1);
					break;
			}
			return true;
		case "verticalAxisVisibility":
			switch(value) {
				case "visible":
					sparkline.verticalAxisVisibility(0);
					break;
				case "collapsed":
					sparkline.verticalAxisVisibility(1);
					break;
			}
			return true;
		case "markerVisibility":
			switch(value) {
				case "visible":
					sparkline.markerVisibility(0);
					break;
				case "collapsed":
					sparkline.markerVisibility(1);
					break;
			}
			return true;
		case "negativeMarkerVisibility":
			switch(value) {
				case "visible":
					sparkline.negativeMarkerVisibility(0);
					break;
				case "collapsed":
					sparkline.negativeMarkerVisibility(1);
					break;
			}
			return true;
		case "firstMarkerVisibility":
			switch(value) {
				case "visible":
					sparkline.firstMarkerVisibility(0);
					break;
				case "collapsed":
					sparkline.firstMarkerVisibility(1);
					break;
			}
			return true;
		case "lastMarkerVisibility":
			switch(value) {
				case "visible":
					sparkline.lastMarkerVisibility(0);
					break;
				case "collapsed":
					sparkline.lastMarkerVisibility(1);
					break;
			}
			return true;
		case "lowMarkerVisibility":
			switch(value) {
				case "visible":
					sparkline.lowMarkerVisibility(0);
					break;
				case "collapsed":
					sparkline.lowMarkerVisibility(1);
					break;
			}
			return true;
		case "highMarkerVisibility":
			switch(value) {
				case "visible":
					sparkline.highMarkerVisibility(0);
					break;
				case "collapsed":
					sparkline.highMarkerVisibility(1);
					break;
			}
			return true;
		case "normalRangeVisibility":
			switch(value) {
				case "visible":
					sparkline.normalRangeVisibility(0);
					break;
				case "collapsed":
					sparkline.normalRangeVisibility(1);
					break;
			}
			return true;
		case "displayNormalRangeInFront":
			sparkline.displayNormalRangeInFront(value);
			return true;
		case "markerSize":
			sparkline.markerSize(value);
			return true;
		case "firstMarkerSize":
			sparkline.firstMarkerSize(value);
			return true;
		case "lastMarkerSize":
			sparkline.lastMarkerSize(value);
			return true;
		case "highMarkerSize":
			sparkline.highMarkerSize(value);
			return true;
		case "lowMarkerSize":
			sparkline.lowMarkerSize(value);
			return true;
		case "negativeMarkerSize":
			sparkline.negativeMarkerSize(value);
			return true;
		case "lineThickness":
			sparkline.lineThickness(value);
			return true;
		case "valueMemberPath":
			sparkline.valueMemberPath(value);
			return true;
		case "labelMemberPath":
			sparkline.labelMemberPath(value);
			return true;
		case "trendLineType":
			switch(value) {
				case "none":
					sparkline.trendLineType(0);
					break;
				case "linearFit":
					sparkline.trendLineType(1);
					break;
				case "quadraticFit":
					sparkline.trendLineType(2);
					break;
				case "cubicFit":
					sparkline.trendLineType(3);
					break;
				case "quarticFit":
					sparkline.trendLineType(4);
					break;
				case "quinticFit":
					sparkline.trendLineType(5);
					break;
				case "logarithmicFit":
					sparkline.trendLineType(6);
					break;
				case "exponentialFit":
					sparkline.trendLineType(7);
					break;
				case "powerLawFit":
					sparkline.trendLineType(8);
					break;
				case "simpleAverage":
					sparkline.trendLineType(9);
					break;
				case "exponentialAverage":
					sparkline.trendLineType(10);
					break;
				case "modifiedAverage":
					sparkline.trendLineType(11);
					break;
				case "cumulativeAverage":
					sparkline.trendLineType(12);
					break;
				case "weightedAverage":
					sparkline.trendLineType(13);
					break;
			}
			return true;
		case "trendLinePeriod":
			sparkline.trendLinePeriod(value);
			return true;
		case "trendLineThickness":
			sparkline.trendLineThickness(value);
			return true;
		case "normalRangeMinimum":
			sparkline.normalRangeMinimum(value);
			return true;
		case "normalRangeMaximum":
			sparkline.normalRangeMaximum(value);
			return true;
		case "displayType":
			switch(value) {
				case "line":
					sparkline.displayType(0);
					break;
				case "area":
					sparkline.displayType(1);
					break;
				case "column":
					sparkline.displayType(2);
					break;
				case "winLoss":
					sparkline.displayType(3);
					break;
			}
			return true;
		case "unknownValuePlotting":
			switch(value) {
				case "linearInterpolate":
					sparkline.unknownValuePlotting(0);
					break;
				case "dontPlot":
					sparkline.unknownValuePlotting(1);
					break;
			}
			return true;
		case "verticalAxisLabel":
			sparkline.verticalAxisLabel(value);
			return true;
		case "horizontalAxisLabel":
			sparkline.horizontalAxisLabel(value);
			return true;
		case "formatLabel":
			sparkline.formatLabel(value);
			return true;
	}

		},
		_setOption: function (key, val) {
			var chart = this._chart, o = this.options;
			if (o[key] === val) {
				return this;
			}
			$.Widget.prototype._setOption.apply(this, arguments);
			this._set_option(chart, key, val);
			return this;
		},
		// used by dataSource of igBaseChart
		_getValueKeyName: function () {
			return "valueMemberPath";
		},
		// used by dataSourceUrl of igBaseChart
		_getRemoteDataKeys: function () {
			return [this.options.valueMemberPath, this.options.labelMemberPath];
		},
		// required by _setSize of igBaseChart
		_getNotifyResizeName: function () {
			return "notifyResized";
		},
		// required by _create of igBaseChart
		_createChart: function () {
			return new $.ig.XamSparkline();
		},
		_sparkline : function () {
			return this._chart;
		},
		destroy: function () {
			$.ui.igBaseChart.prototype.destroy.apply(this);
		}
	});
	$.extend($.ui.igSparkline, {version: '16.1.20161.2145'});
}(jQuery));



