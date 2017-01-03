/*!@license
* Infragistics.Web.ClientUI FunnelChart 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
*     jquery-1.4.4.js
*     jquery.ui.core.js
*     jquery.ui.widget.js
*     infragistics.util.js
*     infragistics.ui.basechart.js
*/

/*global jQuery */
if (typeof (jQuery) !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {
	var _aNull = function (v, nan) {
		return v === null || v === undefined || (nan && typeof v === 'number' && isNaN(v));
	};
	// igFunnelChart definition
	$.widget("ui.igFunnelChart", $.ui.igBaseChart, {
		css: {
			/* Get the class applied to main element: ui-funnel ui-corner-all ui-widget-content */
			chart: "ui-funnel ui-corner-all ui-widget-content",
			/* Get the class applied to the tooltip element: ui-funnel-tooltip ui-widget-content ui-corner-all */
			tooltip: "ui-funnel-tooltip ui-widget-content ui-corner-all"
		},
		options: {
			/* type="string"
				Gets or sets values for upper and lower bezier points. That option has effect only when useBezierCurve is enabled.
				Value should provide 4 numeric values in range from 0 to 1 separated by space character.
				The first number defines upper-x position, second: upper-y, third: lower-x, fourth: lower-y.
				The null or invalid value will reset internal default, which is "0.5 0 0.5 1".
				Example:
				bezierPoints: "0.4 0.1 0.6 1"
			*/
			bezierPoints: null,
			/* type="object"
				Gets or sets the Legend for the chart.
				The value of that option can be a string with the id of existing html element. That element should be empty and it will be used to create igChartLegend widget.
				If element was not found, then new DIV element is created and inserted into parent after chart element.
				Value of that option can be an object with member "element", which contains the id of legend-element.
				In this case, that object also may contain options supported by the igChartLegend widget, such as "width", "height" and events, such as "legendItemMouseLeftButtonDown", "legendItemMouseEnter", etc.
				Examples:
				legend: {}
				legend: "idOfDiv"
				legend: { element: "idOfDiv", width: 300 }
				legend: { legendItemMouseLeftButtonDown: function (evt, ui) { ui.chart.toggleSelection(ui.item); } }
			*/
			legend: null,
				/* type="string" Gets or sets the value member path for the funnel chart.
	*/
	valueMemberPath: null,
	/* type="object" Gets or sets the Brushes property.
	The brushes property defines the palette from which automatically assigned brushes are selected.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	brushes: null,
	/* type="object" Gets or sets the Outlines property.
	The Outlines property defines the palette from which automatically assigned Outlines are selected.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	outlines: null,
	/* type="number" Gets or sets the percentage (from near 0 to 1) of space the bottom edge of the funnel should take.
	*/
	bottomEdgeWidth: 0.35,
	/* type="string" Gets or sets the InnerLabel mapping property for the current series object.
	*/
	innerLabelMemberPath: null,
	/* type="string" Gets or sets the OuterLabel mapping property for the current series object.
	*/
	outerLabelMemberPath: null,
	/* type="visible|collapsed" Gets or sets whether the inner labels are visible.
	visible type="string" 
	collapsed type="string" 
	*/
	innerLabelVisibility: null,
	/* type="visible|collapsed" Gets or sets whether the outer labels are visible.
	visible type="string" 
	collapsed type="string" 
	*/
	outerLabelVisibility: null,
	/* type="left|right" Gets or sets which side of the chart the outer labels should appear.
	left type="string" The labels will be displayed to the left of the chart.
	right type="string" The labels will be displayed to the right of the chart.
	*/
	outerLabelAlignment: null,
	/* type="uniform|weighted" Gets or sets the how the heights of the funnel slices should be configured.
	uniform type="string" The slice heights should be uniform.
	weighted type="string" The slice heights should be weighted by value.
	*/
	funnelSliceDisplay: null,
	/* type="object" Gets or sets the formatter function for inner labels. Function should return string and it takes 3 parameters: 1st-value of item to format, 2nd-index of item within data, 3rd-reference to igFunnelChart.
	*/
	formatInnerLabel: null,
	/* type="object" Gets or sets the formatter function for outer labels. Function should return string and it takes 3 parameters: 1st-value of item to format, 2nd-index of item within data, 3rd-reference to igFunnelChart.
	*/
	formatOuterLabel: null,
	/* type="number" Gets or sets how long the animations should take to run.
	*/
	transitionDuration: 0,
	/* type="bool" Gets or sets if the funnel should be rendered inverted.
	*/
	isInverted: false,
	/* type="bool" Gets or sets whether to use a Bezier curve to define the funnel.
	*/
	useBezierCurve: false,
	/* type="bool" Gets or sets whether to allow slices to be selected.
	*/
	allowSliceSelection: false,
	/* type="bool" Gets or sets whether to use the unselected style on unselected slices.
	*/
	useUnselectedStyle: false,
	/* type="object" Gets or sets the style to use for selected slices.
	That can be used to set stroke (outline color), fill (background color) and opacity.
	*/
	selectedSliceStyle: null,
	/* type="object" Gets or sets the style to use for unselected slices.
	That can be used to set stroke (outline color), fill (background color) and opacity.
	*/
	unselectedSliceStyle: null,
	/* type="object" Gets or sets the LegendItemBadgeTemplate to use for the legend items.
	The provided object should have properties called render and optionally measure. See definition for option: circleMarkerTemplate 
	*/
	legendItemBadgeTemplate: null,
	/* type="bool" Gets or sets whether to use the outer labels to identify the legend items.
	*/
	useOuterLabelsForLegend: false,
	/* type="string" Gets or sets the text style for inner labels
	*/
	textStyle: null,
	/* type="string" Gets or sets the text style for outer labels
	*/
	outerLabelTextStyle: null,
	/* type="number" Gets or sets the thickness of outline around slices.
	*/
	outlineThickness: -1

		},
		events: {
				/* cancel="false" Event which is raised when a slice is clicked.
	Function takes first argument null and second argument ui.
	Use ui.owner to obtain reference to igFunnelChart.
	Use ui.index to obtain index of clicked slice.
	Use ui.item to obtain reference to clicked slice item.
	Use ui.selected to check if slice became selected.
	*/
	sliceClicked: null

		},
		_create: function () {
			$.ui.igBaseChart.prototype._create.apply(this);
			this._defStyle("selected");
			this._defStyle("unselected");
			var funnelChart = this._chart;
			if (funnelChart) {
							funnelChart.sliceClicked = $.ig.Delegate.prototype.combine(funnelChart.sliceClicked, jQuery.proxy(this._fireFunnelChart_sliceClicked, this));

			}
		},
		selectedSliceItems: function (selection) {
			/* Gets array of selected slice items.
				paramType="array" optional="true" Array or selected slice items.
				returnType="array|object" If parameter is undefined, then array of selected items is returned. Otherwise, it returns reference to igFunnelChart.
			*/
			var i, v, sel = this._chart;
			if (selection === undefined) {
				sel = this.selectedSliceIndexes();
				i = sel ? sel.length : 0;
				while (i-- > 0) {
					v = sel[i] = this.getDataItem(sel[i]);
					if (v === null) {
						sel.splice(i, 1);
					}
				}
				return sel;
			}
			i = selection ? selection.length : 0;
			sel = [];
			while (i-- > 0) {
				v = this.findIndexOfItem(selection[i]);
				if (v >= 0) {
					sel[sel.length] = v;
				}
			}
			this.selectedSliceIndexes(sel);
			return this;
		},
		selectedSliceIndexes: function (selection) {
			/* Gets sets array of indexes of selected slices.
				paramType="array" optional="true" Array or selected slice indexes.
				returnType="array|object" If parameter is undefined, then array of selected indexes is returned. Otherwise, it returns reference to igFunnelChart.
			*/
			var i, v, sel = this._chart;
			if (sel && selection !== undefined && selection !== true) {
				sel.selectedIndexes(selection);
				return this;
			}
			sel = sel ? sel.selectedIndexes() : null;
			if (selection) {
				return sel;
			}
			i = sel ? sel.length : 0;
			selection = [];
			while (i-- > 0) {
				v = selection[i] = sel[i];
				if (v === null || isNaN(v) || v < 0) {
					selection.splice(i, 1);
				}
			}
			return selection;
		},
		isSelected: function (slice) {
			/* Checks if slice is selected.
				paramType="number|object" Index of slice or reference to slice-data-item.
				returnType="bool" Returns true if slice is selected.
			*/
			var sel = this.selectedSliceIndexes(true);
			return !(slice === null || !sel || !sel.contains((typeof slice === "number") ? slice : this.findIndexOfItem(slice)));
		},
		toggleSelection: function (slice) {
			/* Toggles selected state of slice.
				paramType="number|object" Index of slice or reference to slice-data-item.
				returnType="object" Returns reference to igFunnelChart.
			*/
			if (typeof slice !== "number") {
				slice = this.findIndexOfItem(slice);
			}
			if (slice >= 0 && this._chart) {
				this._chart.toggleSelection(slice);
			}
			return this;
		},
		_fireFunnelChart_sliceClicked: function (chart, evt) {
			var i = evt.index();
			this._trigger("sliceClicked", null, { owner: this, index: i, item: evt.item(), selected: this.isSelected(i) });
		},
		// used by dataSource of igBaseChart
		_getValueKeyName: function () {
			return "valueMemberPath";
		},
		// used by dataSourceUrl of igBaseChart
		_getRemoteDataKeys: function () {
			var o = this.options;
			return [o.valueMemberPath, o.innerLabelMemberPath, o.outerLabelMemberPath];
		},
		// required by _setSize of igBaseChart
		_getNotifyResizeName: function () {
			return "notifyResized";
		},
		// required by _create of igBaseChart
		_createChart: function () {
			return new $.ig.XamFunnelChart();
		},
		_setLegend: function (chart, value) {
			var legend = this._legend;
			if (legend && legend.data("igChartLegend") !== undefined) {
				legend.igChartLegend("destroy");
				// request to remove dynamic legend
				if (legend[0]._remove) {
					legend.remove();
				}
				delete this._legend;
				chart.legend(null);
			}
			if (!value) {
				return;
			}
			if (typeof value === "string") {
				value = { element: value };
			}
			legend = value.element;
			if (legend) {
				legend = $("#" + legend);
			}
			value.owner = this;
			value.type = "item";
			// check if application provided element for legend
			if (!legend || legend.length !== 1) {
				legend = $("<div />").insertAfter(this.element);
				// request to remove dynamic legend
				legend[0]._remove = true;
			}
			this._legend = legend;
			chart.legend(legend.igChartLegend(value).data("igChartLegend").legend);
		},
		_set_option: function (funnelChart, key, value) {
			if (key === "legend") {
				this._setLegend(funnelChart, value);
				return true;
			}
			if (key === "bezierPoints") {
				var i = -1, len = 0;
				if (typeof value === "string") {
					value = value.split(" ");
					len = value.length;
					while (++i < len) {
						if (isNaN(value[i] = parseFloat(value[i]))) {
							len = 0;
						}
					}
				}
				if (len < 2) {
					len = 4;
					value = [0.5, 0, 0.5, 1];
				}
				funnelChart.upperBezierControlPoint({ __x: value[0], __y: value[1] });
				if (len > 3) {
					funnelChart.lowerBezierControlPoint({ __x: value[2], __y: value[3] });
				}
				return true;
			}
			if ($.ui.igBaseChart.prototype._set_option.apply(this, arguments)) {
				return true;
			}
				switch (key) {
		case "valueMemberPath":
			funnelChart.valueMemberPath(value);
			return true;
		case "brushes":
			var isRGB = true, val = value ? value[0] : null;
			if (typeof val == "string" && val == "HSV" || val =="RGB") { if (value[0] == "HSV") { isRGB = false; } value = value.slice(1); }
var $tempBrushCollection = new $.ig.BrushCollection()
			for (var i = 0; value && i < value.length; i++) {
				var $tempBrush = $.ig.Brush.prototype.create(value[i]);
				$tempBrushCollection.add($tempBrush);
			}
			funnelChart.brushes($tempBrushCollection);
			return true;
		case "outlines":
			var isRGB = true, val = value ? value[0] : null;
			if (typeof val == "string" && val == "HSV" || val =="RGB") { if (value[0] == "HSV") { isRGB = false; } value = value.slice(1); }
var $tempBrushCollection = new $.ig.BrushCollection()
			for (var i = 0; value && i < value.length; i++) {
				var $tempBrush = $.ig.Brush.prototype.create(value[i]);
				$tempBrushCollection.add($tempBrush);
			}
			funnelChart.outlines($tempBrushCollection);
			return true;
		case "bottomEdgeWidth":
			funnelChart.bottomEdgeWidth(value);
			return true;
		case "innerLabelMemberPath":
			funnelChart.innerLabelMemberPath(value);
			return true;
		case "outerLabelMemberPath":
			funnelChart.outerLabelMemberPath(value);
			return true;
		case "innerLabelVisibility":
			switch(value) {
				case "visible":
					funnelChart.innerLabelVisibility(0);
					break;
				case "collapsed":
					funnelChart.innerLabelVisibility(1);
					break;
			}
			return true;
		case "outerLabelVisibility":
			switch(value) {
				case "visible":
					funnelChart.outerLabelVisibility(0);
					break;
				case "collapsed":
					funnelChart.outerLabelVisibility(1);
					break;
			}
			return true;
		case "outerLabelAlignment":
			switch(value) {
				case "left":
					funnelChart.outerLabelAlignment(0);
					break;
				case "right":
					funnelChart.outerLabelAlignment(1);
					break;
			}
			return true;
		case "funnelSliceDisplay":
			switch(value) {
				case "uniform":
					funnelChart.funnelSliceDisplay(0);
					break;
				case "weighted":
					funnelChart.funnelSliceDisplay(1);
					break;
			}
			return true;
		case "formatInnerLabel":
			funnelChart.formatInnerLabel(value);
			return true;
		case "formatOuterLabel":
			funnelChart.formatOuterLabel(value);
			return true;
		case "transitionDuration":
			funnelChart.transitionDuration(value);
			return true;
		case "isInverted":
			funnelChart.isInverted(value);
			return true;
		case "useBezierCurve":
			funnelChart.useBezierCurve(value);
			return true;
		case "allowSliceSelection":
			funnelChart.allowSliceSelection(value);
			return true;
		case "useUnselectedStyle":
			funnelChart.useUnselectedStyle(value);
			return true;
		case "selectedSliceStyle":
			funnelChart.selectedSliceStyle(value);
			return true;
		case "unselectedSliceStyle":
			funnelChart.unselectedSliceStyle(value);
			return true;
		case "legendItemBadgeTemplate":
			var $tempTemplate = new $.ig.DataTemplate();
			if (value.render) {
				$tempTemplate.render(value.render);
				if (value.measure) {
					$tempTemplate.measure(value.measure);
				}
			} else {
				$tempTemplate.render(value);
			}
			funnelChart.legendItemBadgeTemplate($tempTemplate);
			return true;
		case "useOuterLabelsForLegend":
			funnelChart.useOuterLabelsForLegend(value);
			return true;
		case "textStyle":
			funnelChart.textStyle(value);
			return true;
		case "outerLabelTextStyle":
			funnelChart.outerLabelTextStyle(value);
			return true;
		case "outlineThickness":
			funnelChart.outlineThickness(value);
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
		// synchronize selection with modified data
		// act: -1: remove item, 0-set item, 1-add item, 2-insert item, 3-new data
		// before: 1/true-before data was applied to chart, undefined/null/false-after data applied
		_dataEvt: function (act, before) {
			if (act === 0 || act === 1) {
				return;
			}
			var sel, i, old = this._oldSel;
			// save old selected items
			if (before) {
				old = this.selectedSliceItems();
				this._oldSel = old && old.length ? old : null;
				return;
			}
			delete this._oldSel;
			sel = this.selectedSliceIndexes();
			if (!old && (!sel || !sel.length)) {
				return;
			}
			// restore old selected items
			i = old ? old.length : 0;
			while (i-- > 0) {
				old[i] = this.findIndexOfItem(old[i]);
				if (old[i] < 0) {
					// fix required
					sel = null;
					old.splice(i, 1);
				} else if (sel && sel[i] !== old[i]) {
					// fix required
					sel = null;
				}
			}
			if (!sel) {
				this.selectedSliceIndexes(old || null);
			}
		},
		_defStyle: function (sel) {
			var v, bk0, b0, style = {}, name = sel + "SliceStyle",
				span = this.element.find("SPAN");
			if (span.length !== 1 || this.options[name]) {
				return;
			}
			bk0 = span.css("background-color");
			b0 = span.css("border-top-color");
			sel = "ui-funnel-slice-" + sel;
			span.addClass(sel);
			v = span.css("background-color");
			if (v && v !== "transparent" && v !== bk0) {
				style.fill = v;
			}
			v = span.css("border-top-color");
			if (v && v !== b0) {
				style.stroke = v;
			}
			v = parseFloat(span.css("opacity"));
			if (!isNaN(v) && v > 0 && v < 1) {
				style.opacity = v;
			}
			span.removeClass(sel);
			this._set_option(this._chart, name, style);
		},
		exportVisualData: function () {
		    return this._chart.exportVisualData();
		},
		destroy: function () {
			/* Destroys widget. */
			if (this._chart) {
				this._setLegend(this._chart);
			}
			$.ui.igBaseChart.prototype.destroy.apply(this);
		}
	});
	$.extend($.ui.igFunnelChart, {version: '16.1.20161.2145'});
}(jQuery));
