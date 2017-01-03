/*!@license
	* Infragistics.Web.ClientUI Chart 16.1.20161.2145
	*
	* Copyright (c) 2011-2016 Infragistics Inc.
	*
	* http://www.infragistics.com/
	*
	* Depends on:
	* jquery-1.9.1.js
	* jquery.ui.core.js
	* jquery.ui.widget.js
	* infragistics.util.js
	* infragistics.dv.core.js
	* infragistics.chart_funnelchart.js
	*/

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {
	/*
		igChartLegend is a widget based on jQuery UI that can be used to identify
		the name of a series or its elements represented in chart plot area.
	*/
	$.widget("ui.igChartLegend", {
		options: {
			/* type="item|legend|scale" Type of the legend.
				item type="string" Specify the legend as item legend. It displays a legend item for each pie in the igPieChart control.
				legend type="string" Specify the legend as legend. It is supported by all types of series in the igDataChart control.
				scale type="string" Specify the legend as a scale legend. It displays a color/size scale for the bubble series.
			*/
			type: "legend",
			/* type="string|number|null" The width of the legend. It can be set as a number in pixels, string (px) or percentage (%). */
			width: null,
			/* type="string|number|null" The height of the legend. It can be set as a number in pixels, string (px) or percentage (%). */
			height: null,/*BUILDREMOVESTHIS*/
			/* type="string" The swatch used to style this widget */
			theme: "c"
		},
		css: {
			/* Classes applied on the parent element. */
			legend: "ui-corner-all ui-widget-content ui-chart-legend",
			/* Class applied on the legend items table element. */
			legendItemsList: "ui-chart-legend-items-list",
			/* Class applied on the legend item tr element. */
			legendItem: "ui-chart-legend-item",
			/* Class applied on the legend item badge td element. */
			legendItemBadge: "ui-chart-legend-item-badge",
			/* Class applied on the legend item text td element. */
			legendItemText: "ui-chart-legend-item-text"
		},
		events: {
			/* cancel="false"
			Occurs when the left mouse button is pressed while the mouse pointer is over an element of this legend.
			Function takes arguments evt and ui.
			Use ui.legend to get reference to current legend object.
			Use ui.chart to get reference to chart owner object.
			Use ui.series to get reference to current series owner object.
			Use ui.actualItemBrush to get legend item brush.
			Use ui.actualSeriesBrush to get series owner brush.
			Use ui.item to get reference to chart item related to event.
			*/
			legendItemMouseLeftButtonDown: null,
			/* cancel="false"
			Occurs when the left mouse button is released while the mouse pointer is over an element of this legend.
			Function takes arguments evt and ui.
			Use ui.legend to get reference to current legend object.
			Use ui.chart to get reference to chart owner object.
			Use ui.series to get reference to current series owner object.
			Use ui.actualItemBrush to get legend item brush.
			Use ui.actualSeriesBrush to get series owner brush.
			Use ui.item to get reference to chart item related to event.
			*/
			legendItemMouseLeftButtonUp: null,
			/* cancel="false"
			Occurs when the left mouse pointer enters an element of this legend.
			Function takes arguments evt and ui.
			Use ui.legend to get reference to current legend object.
			Use ui.chart to get reference to chart owner object.
			Use ui.series to get reference to current series owner object.
			Use ui.actualItemBrush to get legend item brush.
			Use ui.actualSeriesBrush to get series owner brush.
			Use ui.item to get reference to chart item related to event.
			*/
			legendItemMouseEnter: null,
			/* cancel="false"
			Occurs when the left mouse pointer leaves an element of this legend.
			Function takes arguments evt and ui.
			Use ui.legend to get reference to current legend object.
			Use ui.chart to get reference to chart owner object.
			Use ui.series to get reference to current series owner object.
			Use ui.actualItemBrush to get legend item brush.
			Use ui.actualSeriesBrush to get series owner brush.
			Use ui.item to get reference to chart item related to event.
			*/
			legendItemMouseLeave: null
		},
		_create: function () {
			var legend, o = this.options, elem = this.element;
			if (o.type === "item") {
				legend = new $.ig.ItemLegend();
			} else if (o.type === "scale") {
			    legend = new $.ig.ScaleLegend();
			    if (!o.width) {
			        o.width = "100px";
			    }
			    if (!o.height) {
			        o.height = "150px";
			    }
			} else {
				legend = new $.ig.Legend();
			}
			if (o.owner) {
				this._owner = o.owner;
				o.owner = this._owner.options;
			}
			this.legend = legend;
			legend.name(this.id());
			legend.legendItemsListStyle(this.css.legendItemsList);
			legend.legendItemStyle(this.css.legendItem);
			legend.legendItemBadgeStyle(this.css.legendItemBadge);
			legend.legendItemTextStyle(this.css.legendItemText);
			if (o.width) {
				this._oldWidth = elem[ 0 ].style.width;
				elem.css("width", o.width);
			}
			if (o.height) {
				this._oldHeight = elem[ 0 ].style.width;
				elem.css("height", o.height);
			}
			this._cssLegend = this.css.legend.replace("{0}", o.theme);
			elem.addClass(this._cssLegend);
			this._bindLegendEvents(legend);
			legend.provideContainer(elem);
		},
		_bindLegendEvents: function (legend) {
			legend.legendItemMouseLeftButtonDown = $.ig.Delegate.prototype.combine(
				legend.legendItemMouseLeftButtonDown, jQuery.proxy(
				this._fireLegendItemMouseLeftButtonDown, this));
			legend.legendItemMouseLeftButtonUp = $.ig.Delegate.prototype.combine(
				legend.legendItemMouseLeftButtonUp, jQuery.proxy(this._fireLegendItemMouseLeftButtonUp, this));
			legend.legendItemMouseEnter = $.ig.Delegate.prototype.combine(
				legend.legendItemMouseEnter, jQuery.proxy(this._fireLegendItemMouseEnter, this));
			legend.legendItemMouseLeave = $.ig.Delegate.prototype.combine(
				legend.legendItemMouseLeave, jQuery.proxy(this._fireLegendItemMouseLeave, this));
		},
		_getLegendEvt: function (evtArgs) {
			var e, brush,
				series = evtArgs.series ? evtArgs.series() : null,
				owner = this._owner;
			if (!owner) {
				return null;
			}
			e = {
				legend: this.options,
				series: (series !== null && owner.dvWidget) ? owner.dvWidget._getSeriesOpt(evtArgs) : owner.options,
				chart: owner,
				item: evtArgs._item
			};
			e[ owner.dvWidget ? owner.dvWidget._getWidgetName() : owner.widgetName ] = owner.options;
			brush = (evtArgs.legendItem && evtArgs.legendItem() !==
				null && evtArgs.legendItem().content) ? evtArgs.legendItem().content() : null;
			brush = brush && brush.actualItemBrush ? brush.actualItemBrush() : null;

			if (brush === null) {
			    var intSeries = null;
			    if (evtArgs.series && evtArgs.series() !== null) {
			        intSeries = evtArgs.series();
			    }
			    if (intSeries !== null && intSeries.hostedSeries && intSeries.hostedSeries() !== null) {
			        intSeries = intSeries.hostedSeries();
			    }
			    if (intSeries !== null && intSeries.actualMarkerBrush &&
					intSeries.actualMarkerBrush() !== null) {
			        brush = intSeries.actualMarkerBrush();
			    }
			}

			e.actualItemBrush = brush ? this._getValueFromBrush(brush) : null;
			brush = (series && series.actualBrush) ? series.actualBrush() : null;
			e.actualSeriesBrush = brush ? this._getValueFromBrush(brush) : null;

			return e;
		},
		_getValueFromBrush: function (brush) {
		    var ret = {}, currStop, newStop;
		    if (brush._isGradient) {
		        ret.type = "linearGradient";
		        if (brush._useCustomDirection) {
		            ret.startPoint = {};
		            ret.startPoint.x = brush._startX;
		            ret.startPoint.y = brush._startY;
		            ret.endPoint = {};
		            ret.endPoint.x = brush._endX;
		            ret.endPoint.y = brush._endY;
		        }
		        if (brush._gradientStops) {
		            ret.colorStops = [];
		            for (var i = 0; i < brush._gradientStops.length; i++) {
		                currStop = brush._gradientStops[ i ];
		                newStop = {};

		                newStop.offset = currStop._offset;
		                newStop.color = currStop.__fill;
		                ret.colorStops.push(newStop);
		            }
		        }
		        return ret;
		    } else {
		        return brush.fill();
		    }
		},
		_fireLegendItemMouseLeftButtonDown: function (sender, evtArgs) {
			this._trigger("legendItemMouseLeftButtonDown", null, this._getLegendEvt(evtArgs));
		},
		_fireLegendItemMouseLeftButtonUp: function (sender, evtArgs) {
			this._trigger("legendItemMouseLeftButtonUp", null, this._getLegendEvt(evtArgs));
		},
		_fireLegendItemMouseEnter: function (sender, evtArgs) {
			this._trigger("legendItemMouseEnter", null, this._getLegendEvt(evtArgs));
		},

		_fireLegendItemMouseLeave: function (sender, evtArgs) {
			this._trigger("legendItemMouseLeave", null, this._getLegendEvt(evtArgs));
		},
		_getLegend: function () {
			return this.legend;
		},
		_setOption: function (key, value) {
			switch (key) {
				case "width":
					this.element.css("width", value);
					break;
				case "height":
					this.element.css("height", value);
					break;
			}
		},
		exportVisualData: function () {
		    if (this.legend === null) {
		        return null;
		    }
		    return this.legend.exportVisualData();
		},
		destroy: function () {
			/* Destroys the widget. */
			var elements, elem = this.element;
			$.Widget.prototype.destroy.call(this);
			if (this.legend) {
				this.legend = null;
			}
			if (elem) {
				elem.removeClass(this._cssLegend);
				if (this.options.width) {
					elem[ 0 ].style.width = this._oldWidth || "";
				}
				if (this.options.height) {
					elem[ 0 ].style.height = this._oldHeight || "";
				}
				if (elem.children("table").length > 0) {
					elements = elem.children("table").children("tr");
					$.each(elements, function (key, tr) {
						$(tr).unbind("mouseleave").unbind("mouseup").unbind("mousedown").unbind("mousemove");
					});
				}
				elem.empty();
			}
			return this;
		},
		widget: function () {
			/* Returns the element holding the legend. */
			return this.element;
		},
		id: function () {
			/*  returns the ID of the DOM element holding the legend.
				returnType="string"
			*/
			return this.element[ 0 ].id;
		}
	});
	$.extend($.ui.igChartLegend, { version: "16.1.20161.2145" });
}(jQuery));
