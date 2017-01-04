/*!@license
* Infragistics.Web.ClientUI Doughnut Chart localization resources 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/

/*global jQuery */
(function ($) {
    $.ig = $.ig || {};

    if (!$.ig.igDoughnutChart) {
        $.ig.igDoughnutChart = {};

        $.extend($.ig.igDoughnutChart, {
            locale: {
                invalidBaseElement: " はベース要素としてサポートされていません。代わりに DIV を使用してください。"
            }
        });
    }
})(jQuery);

/*!@license
* Infragistics.Web.ClientUI DoughnutChart 16.1.20161.2145
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
*	infragistics.datasource.js
*	infragistics.dv.core.js
*	infragistics.dvcommonwidget.js
*	infragistics.templating.js
*	infragistics.ui.chartlegend.js
*	infragistics.ui.basechart.js
*	infragistics.chart_piechart.js
*/

/*global jQuery */
if (typeof (jQuery) !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {
	var _aNull = function (v, nan) {
		return v === null || v === undefined || (nan && typeof v === 'number' && isNaN(v));
	};
	// igDoughnutChart definition
	$.widget("ui.igDoughnutChart", $.ui.igBaseChart, {
		css: {
			/* Get the class applied to main element: ui-doughnut ui-corner-all ui-widget-content */
			chart: "ui-doughnut ui-corner-all ui-widget-content",
			/* Get the class applied to the tooltip element: ui-doughnut-tooltip ui-widget-content ui-corner-all */
			tooltip: "ui-doughnut-tooltip ui-widget-content ui-corner-all"
		},
		options: {
			/* type="string|number" The width of the chart. It can be set as a number in pixels, string (px) or percentage (%).
				string The widget width can be set in pixels (px) and percentage (%).
				number The widget width can be set as a number
			*/
			width: null,
			/* type="string|number" The height of the chart. It can be set as a number in pixels, string (px) or percentage (%).
				string The widget height can be set in pixels (px) and percentage (%).
				number The widget height can be set as a number
			*/
			height: null,
		    /* type="array" An array of series objects. */
			series: [{

				/* type="flat|hierarchical" Gets or sets the current series type.
				flat type="string" Series has flat 1-dimensional data.
				collapsed type="string" Series has hierarchical data.
				*/
			    type: "flat",
			    /* type="bool" Whether the series should render a tooltip. */
			    showTooltip: false,
			    /* type="string" The name of template or the template itself that chart tooltip will use to render. */
			    tooltipTemplate: null,
					/* type="object" Gets or sets the data source for the chart.
	*/
	itemsSource: null,
	/* type="string" Gets or Sets the property name that contains the values.
	*/
	valueMemberPath: "null",
	/* type="string" Gets or sets the property name that contains the labels.
	*/
	labelMemberPath: "null",
	/* type="string" Gets or sets the property name that contains the legend labels.
	*/
	legendLabelMemberPath: "null",
	/* type="none|center|insideEnd|outsideEnd|bestFit" Gets or sets the position of chart labels.
	none type="string" No labels will be displayed.
	center type="string" Labels will be displayed in the center.
	insideEnd type="string" Labels will be displayed inside and by the edge of the container.
	outsideEnd type="string" Labels will be displayed outside the container.
	bestFit type="string" Labels will automatically decide their location.
	*/
	labelsPosition: "center",
	/* type="visible|collapsed" Gets or sets whether the leader lines are visible.
	visible type="string" 
	collapsed type="string" 
	*/
	leaderLineVisibility: "visible",
	/* type="object" Gets or sets the style for the leader lines.
	*/
	leaderLineStyle: null,
	/* type="straight|arc|spline" Gets or sets what type of leader lines will be used for the outside end labels.
	straight type="string" A straight line is drawn between the slice and its label.
	arc type="string" A curved line is drawn between the slice and its label. The line follows makes a natural turn from the slice to the label.
	spline type="string" A curved line is drawn between the slice and its label. The line starts radially from the slice and then turns to the label.
	*/
	leaderLineType: "straight",
	/* type="number" Gets or sets the margin between a label and its leader line. The default is 6 pixels.
	*/
	leaderLineMargin: 6,
	/* type="number" Gets or sets the threshold value that determines if slices are grouped into the Others slice.
	*/
	othersCategoryThreshold: 3,
	/* type="number|percent" Gets or sets whether to use numeric or percent-based threshold value.
	number type="string" Data value is compared directly to the value of OthersCategoryThreshold.
	percent type="string" Data value is compared to OthersCategoryThreshold as a percentage of the total.
	*/
	othersCategoryType: "percent",
	/* type="string" Gets or sets the label of the Others slice.
	*/
	othersCategoryText: "Others",
	/* type="object" Gets or sets the legend used for the current chart.
	*/
	legend: null,
	/* type="object" Sets or gets a function which takes an object that produces a formatted label for displaying in the chart.
	*/
	formatLabel: null,
	/* type="object" Sets or gets a function which takes an object that produces a formatted label for displaying in the chart's legend.
	*/
	formatLegendLabel: null,
	/* type="number" Gets or sets the pixel amount, by which the labels are offset from the edge of the slices.
	*/
	labelExtent: 10,
	/* type="number" Gets or sets the starting angle of the chart.
	The default zero value is equivalent to 3 o'clock.
	*/
	startAngle: 0,
	/* type="object" Gets or sets the style used when a slice is selected.
	*/
	selectedStyle: null,
	/* type="object" Gets or sets the Brushes property.
	The brushes property defines the palette from which automatically assigned slice brushes are selected.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	brushes: null,
	/* type="object" Gets or sets the Outlines property.
	The Outlines property defines the palette from which automatically assigned slice outlines are selected.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	outlines: null,
	/* type="object" Gets or sets whether all surface interactions with the plot area should be disabled.
	*/
	isSurfaceInteractionDisabled: null,
	/* type="number" Gets or sets the scaling factor of the chart's radius. Value between 0 and 1.
	*/
	radiusFactor: 0.9
	
			}],

				/* type="bool" Gets or sets whether the slices can be selected.
	*/
	allowSliceSelection: true,
	/* type="object" Gets or sets whether all surface interactions with the plot area should be disabled.
	*/
	isSurfaceInteractionDisabled: false,
	/* type="bool" Gets or sets whether the slices can be exploded.
	*/
	allowSliceExplosion: true,
	/* type="number" Gets or sets the inner extent of the doughnut chart. It is percent from the outer ring's radius.
	*/
	innerExtent: 40,
	/* type="object" Gets or sets the style used when a slice is selected.
	*/
	selectedStyle: null

		},
		events: {
			/* cancel="true" event fired when the mouse has hovered on a series and the tooltip is about to show 
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			*/
			tooltipShowing: "tooltipShowing",
			/* event fired after a tooltip is shown
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			*/
			tooltipShown: "tooltipShown",
			/* cancel="true" event fired when the mouse has left a series and the tooltip is about to hide
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			*/
			tooltipHiding: "tooltipHiding",
			/* event fired after a tooltip is hidden 
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			*/
			tooltipHidden: "tooltipHidden",
			/* event fired when the control is displayed on a non HTML5 compliant browser */
			browserNotSupported: "browserNotSupported",
				/* cancel="false" Raised when the slice is clicked.
	*/
	sliceClick: null,
	/* cancel="false" Raised when the dimensions (center point or radius) of the doughnut hole change.
	*/
	holeDimensionsChanged: null

		},
		_create: function () {
		    var elementName = this.element[0].nodeName.toUpperCase();
		    if (elementName !== "DIV") {
		        throw new Error(elementName + $.ig.igDoughnutChart.locale.invalidBaseElement);
		    }
		    if (this.css.chart) {
		        this.element.addClass(this.css.chart);
		    }
			$.ui.igBaseChart.prototype._create.apply(this);
			this._defStyle("selected");
			this._defStyle("unselected");
			var doughnutChart = this._chart;
			if (doughnutChart) {
							doughnutChart.sliceClick = $.ig.Delegate.prototype.combine(doughnutChart.sliceClick, jQuery.proxy(this._fireDoughnutChart_sliceClick, this));
			doughnutChart.holeDimensionsChanged = $.ig.Delegate.prototype.combine(doughnutChart.holeDimensionsChanged, jQuery.proxy(this._fireDoughnutChart_holeDimensionsChanged, this));

			}
		},

		_creationOptions: null,

		_createWidget: function (options, element) {
			 this._creationOptions = options;
			 this._tooltipTemplate = {};
			 this._seriesOpt = {};
			 this._seriesColl = {};
			 this._tooltip = {};
			 this._legends = {};
			 $.Widget.prototype._createWidget.apply(this, [options, element]);
		},
		_beforeInitialOptions: function (chart, elem) {
			chart.provideContainer(elem[0]);
		},

		_provideContainer: function (chart, elem) {
		},

		_setInitialOptions: function (chart) {
			var o = this._creationOptions, self = this;
			
			for (key in o) {
				if (o.hasOwnProperty(key)) {
					v = o[key];
					if (v !== null) {
						self._set_option(chart, key, v);
					}
				}
			}			
		},
		
		_getDataSourceOwner: function (dataSourceOwnerName) {
			return this._seriesColl[dataSourceOwnerName];
		},

		_fireDoughnutChart_holeDimensionsChanged: function (chart, evt)
		{
			var e = {};
			e.center = { x: evt.center().__x, y: evt.center().__y };
			e.radius = evt.radius();

			e.doughnut = this.options;

			this._trigger("holeDimensionsChanged", null, e);
		},
		_fireDoughnutChart_sliceClick: function (chart, evt) {
			var e = {}, slice = evt.slice();
			
			if (slice) {
				e.slice = {};
				e.slice.item = slice.dataContext();
				e.slice.isSelected = slice.isSelected();
				e.slice.isExploded = slice.isExploded();
				e.doughnut = this.options;
			}

			this._trigger("sliceClick", null, e);

		    if (e.slice.isSelected != slice.isSelected()) {
		        slice.isSelected(e.slice.isSelected);
		    }
		    
		    if (e.slice.isExploded != slice.isExploded()) {
		        slice.isExploded(e.slice.isExploded);
		    }
		    
		},
		// required by dataSource of igBaseChart
		_getValueKeyName: function () {
			return "valueMemberPath";
		},
		// required by _setSize of igBaseChart
		_getNotifyResizeName: function () {
			return "notifyResized";
		},
		// required by _createChart of igBaseChart
		_createChart: function () {
			return new $.ig.XamDoughnutChart();
		},

        _setLegend: function (item, value, chart) {			
			var legend;
			if (value) {
				value.owner = this;
				if (value.type === undefined) {
					value.type = "item";
				}
				var legend = value.element;
				if (legend) {
					legend = $("#" + legend);
				}

				// check if application provided element for legend
				if (!legend || legend.length !== 1) {
					var widgetId = chart.getContainerID();
					legend = $("<div id=\"" + widgetId + "_" + item.name() + "_legend\" />").insertAfter(this.element);					
					// request to remove dynamic legend
					legend[0]._remove = true;
				}
				if (item.legend() === null) {
					//check if the widget is created already
					if (legend.data("igChartLegend") || (value.data && value.data("igChartLegend"))) {
						legend = (!legend) ? value.data("igChartLegend") : legend.data("igChartLegend");
						legend.options.owner = this;
						legend._owner = this;
						if (!legend._ownerCount) {
							legend._ownerCount = 1;
						}
						else {
							legend._ownerCount += 1
						}
					} else {
						legend = legend.igChartLegend(value).data("igChartLegend");
						if (!legend._ownerCount) {
							legend._ownerCount = 1;
						}
						else {
							legend._ownerCount += 1
						}
					}
					item.legend(legend.legend);
				} else {
					$('#' + item.legend().name()).igChartLegend(value);
					item.legend()._ownerCount += 1;
				}
			} else {
				if (item.legend() !== null && item.legend().name().length > 0 && $('#' + item.legend().name()).length > 0) {
					legend = $('#' + item.legend().name()).data().igChartLegend;
					legend._ownerCount -= 1;
					item.legend(null);
					if (legend._ownerCount == 0) {
						var legendElement = legend.element;
						legend.destroy();						
						if (legendElement[0]._remove) {
							legendElement.remove();
						}
					}
				}
			}
        },
		_htmlCheckExpr: /^[^<]*(<[\w\W]+>)[^>]*$/,
		_addTooltip: function (chart, series, clss) {		
            var widgetId = chart.getContainerID();
			
			if (this._tooltip[series.name()] === undefined) {
                this._tooltip[series.name()] = $("<div id=\"" + widgetId + "_" + series.name() + "_tooltip\" class=\"" + clss + "\"></div>");
				this._bindTooltipEvents(this, this._tooltip[series.name()]);
				series.toolTip(this._tooltip[series.name()]);
            }
        },

        _removeTooltip: function (series) {
            if (this._tooltip[series.name()] !== undefined) {
                this._removeTooltipEvents(this._tooltip[series.name()]);
                delete this._tooltip[series.name()];
                series.toolTip(null);
            }
        },

        _bindTooltipEvents: function (chart, tooltip) {
            tooltip.updateToolTip = $.ig.Delegate.prototype.combine(tooltip.updateToolTip, jQuery.proxy(this._fireToolTip_updateToolTip, this));
            tooltip.hideToolTip = $.ig.Delegate.prototype.combine(tooltip.hideToolTip, jQuery.proxy(this._fireToolTip_hideToolTip, this));
        },
		
		_removeTooltipEvents: function (tooltip) {
            delete tooltip.updateToolTip;
            delete tooltip.hideToolTip;
        },

		_fireToolTip_hideToolTip: function (args) {
            var e = {}, noCancel;
            e = this._getChartEvt(args);
            e.tempId = e.series.name;
            e.element = null;
            if (e.series !== null) {
                e.element = this._tooltip[e.tempId];
            }

            noCancel = this._trigger(this.events.tooltipHiding, null, e);
            if (noCancel) {
                $.each(this._tooltip, function (i, tip) {
                    tip.hide();
                });
                this._trigger(this.events.tooltipHidden, null, e);
            }
        },
		_fireToolTip_updateToolTip: function (args) {
            var e = {}, noCancel, template;
            e = this._getChartEvt(args);
            e.tempId = e.series.name;
            e.element = null;
            if (e.series !== null) {
                e.element = this._tooltip[e.tempId];
            }

            noCancel = this._trigger(this.events.tooltipShowing, null, e);

            if (e === null) {
                noCancel = false;
            }
            if (noCancel) {
                if (e.series !== null && this._tooltipTemplate[e.tempId] !== undefined) {
                    template = this._tooltipTemplate[e.tempId];
                }

                if (template) {
                    this._tooltip[e.tempId].children().remove();
                    if (e.item === null) {
                        noCancel = false;
                    }

                    if (noCancel) {
                        this._tooltip[e.tempId].html($.ig.tmpl(template, e));
                    }
                }

                if (noCancel) {
                    this._tooltip[e.tempId].show();
                    this._trigger(this.events.tooltipShown, null, e);
                }
            }
            else {
                $.each(this._tooltip, function (i, tip) {
                    tip.hide();
                });
            }
        },
		 _getChartEvt: function (evtArgs) {
            var e = {}, seriesOpt = this._getSeriesOpt(evtArgs), pos, widget = this, intSeries = null;

            e.doughnut = widget.options;
            e.series = seriesOpt;
            e.item = evtArgs.item();

            if (evtArgs.series && evtArgs.series() !== null) {
                intSeries = evtArgs.series();
            }
            
            if (evtArgs.originalEvent && evtArgs.originalEvent() !== null && evtArgs.originalEvent().position && evtArgs.originalEvent().position() !== null) {
                pos = evtArgs.originalEvent().position();
                e.positionX = pos.__x;
                e.positionY = pos.__y;
            }
            return e;
        },
		_getSeriesOpt: function (evtArgs) {
				var widget = this;
								
				if (widget._seriesOpt[evtArgs.series().name()]) {
					return widget._seriesOpt[evtArgs.series().name()];
				}
				
				return widget.options;
        },
		_setSeriesOption: function (ringSeriesBase, key, value, chart) {			
			
			if (key === "legend") {
				this._setLegend(ringSeriesBase, value, chart);
				return true;
			}
			else if(key === "showTooltip")
			{
                if (value === true) {
					//widget.css.tooltip 
                    this._addTooltip(chart, ringSeriesBase, this.css.tooltip);
                }
                if (value === false) {
                    this._removeTooltip(ringSeriesBase, chart);
                }
				return;
            }
            else if(key === "tooltipTemplate" && ringSeriesBase.toolTip)
			{	
											
                if ($.ig.tmpl) {
                    if (this._htmlCheckExpr.test(value)) {
                        templ = value;
                    } else {
                        if ($('#' + value).length > 0) {
                            templ = $('#' + value).text();
                        } else if ($(value).length > 0) {
                            templ = $(value).text();
                        } else {
                            templ = value;
                        }
                    }
                    this._tooltipTemplate[ringSeriesBase.name()] = templ;
                }
				return;
             }
			else if( key === "legendItemBadgeTemplate" || key === "legendItemTemplate")
			{
                this._setDataTemplate(ringSeriesBase, key, value);
                return;
			} else if (key === "isSurfaceInteractionDisabled") {
			    ringSeriesBase.isSurfaceInteractionDisabled($.ig.util.toNullable($.ig.Boolean.prototype.$type, value));
			    return true;
			}
			else if ("othersCategoryText" == key && null == value) {
			    value = String.empty();
			}
			else if ("childrenMemberPath" == key && "RingSeriesCollection" == chart.series().getType().name) {
			    return;
			}
				switch (key) {
		case "itemsSource":
			ringSeriesBase.itemsSource(value);
			return true;
		case "valueMemberPath":
			ringSeriesBase.valueMemberPath(value);
			return true;
		case "labelMemberPath":
			ringSeriesBase.labelMemberPath(value);
			return true;
		case "legendLabelMemberPath":
			ringSeriesBase.legendLabelMemberPath(value);
			return true;
		case "labelsPosition":
			switch(value) {
				case "none":
					ringSeriesBase.labelsPosition(0);
					break;
				case "center":
					ringSeriesBase.labelsPosition(1);
					break;
				case "insideEnd":
					ringSeriesBase.labelsPosition(2);
					break;
				case "outsideEnd":
					ringSeriesBase.labelsPosition(3);
					break;
				case "bestFit":
					ringSeriesBase.labelsPosition(4);
					break;
			}
			return true;
		case "leaderLineVisibility":
			switch(value) {
				case "visible":
					ringSeriesBase.leaderLineVisibility(0);
					break;
				case "collapsed":
					ringSeriesBase.leaderLineVisibility(1);
					break;
			}
			return true;
		case "leaderLineStyle":
			ringSeriesBase.leaderLineStyle(value);
			return true;
		case "leaderLineType":
			switch(value) {
				case "straight":
					ringSeriesBase.leaderLineType(0);
					break;
				case "arc":
					ringSeriesBase.leaderLineType(1);
					break;
				case "spline":
					ringSeriesBase.leaderLineType(2);
					break;
			}
			return true;
		case "leaderLineMargin":
			ringSeriesBase.leaderLineMargin(value);
			return true;
		case "othersCategoryThreshold":
			ringSeriesBase.othersCategoryThreshold(value);
			return true;
		case "othersCategoryType":
			switch(value) {
				case "number":
					ringSeriesBase.othersCategoryType(0);
					break;
				case "percent":
					ringSeriesBase.othersCategoryType(1);
					break;
			}
			return true;
		case "othersCategoryText":
			ringSeriesBase.othersCategoryText(value);
			return true;
		case "formatLabel":
			ringSeriesBase.formatLabel(value);
			return true;
		case "formatLegendLabel":
			ringSeriesBase.formatLegendLabel(value);
			return true;
		case "labelExtent":
			ringSeriesBase.labelExtent(value);
			return true;
		case "startAngle":
			ringSeriesBase.startAngle(value);
			return true;
		case "selectedStyle":
			ringSeriesBase.selectedStyle(value);
			return true;
		case "brushes":
			var isRGB = true, val = value ? value[0] : null;
			if (typeof val == "string" && val == "HSV" || val =="RGB") { if (value[0] == "HSV") { isRGB = false; } value = value.slice(1); }
var $tempBrushCollection = new $.ig.BrushCollection()
			for (var i = 0; value && i < value.length; i++) {
				var $tempBrush = $.ig.Brush.prototype.create(value[i]);
				$tempBrushCollection.add($tempBrush);
			}
			ringSeriesBase.brushes($tempBrushCollection);
			return true;
		case "outlines":
			var isRGB = true, val = value ? value[0] : null;
			if (typeof val == "string" && val == "HSV" || val =="RGB") { if (value[0] == "HSV") { isRGB = false; } value = value.slice(1); }
var $tempBrushCollection = new $.ig.BrushCollection()
			for (var i = 0; value && i < value.length; i++) {
				var $tempBrush = $.ig.Brush.prototype.create(value[i]);
				$tempBrushCollection.add($tempBrush);
			}
			ringSeriesBase.outlines($tempBrushCollection);
			return true;
		case "isSurfaceInteractionDisabled":
			ringSeriesBase.isSurfaceInteractionDisabled(value);
			return true;
		case "radiusFactor":
			ringSeriesBase.radiusFactor(value);
			return true;
	}

		},

		_set_option: function (doughnutChart, key, value) {
			if (key === "legend") {				
				this._setLegend(doughnutChart, value, doughnutChart);		
				return true;
			}
			else if( key === "legendItemBadgeTemplate" || key === "legendItemTemplate")
			{
                this._setDataTemplate(doughnutChart, key, value);
                return;
			}
			if ($.ui.igBaseChart.prototype._set_option.apply(this, arguments)) {
				return true;
			}
			
			if (key === "series") {
				this._iterationFlag = !this._iterationFlag;		
				var count = value.length;
				for(i = 0; i < count;i++)  
				{
					if (!value[i].name) {
						throw new Error("Series name is missing for series: " + i);
					}

					if (this._seriesColl.hasOwnProperty(value[i].name)) 
					{	
						//Removes series if value[i].remove == true elseupdates the series
						if (value[i].remove) {
							this._removeSeries(doughnutChart, value[i]);			
						}
						this._updateSeries(doughnutChart, value[i]);	
					}
					else
						this._addSeries(doughnutChart, value[i]);
				}

				return true;
			} else if (key === "isSurfaceInteractionDisabled") {
			    doughnutChart.isSurfaceInteractionDisabled($.ig.util.toNullable($.ig.Boolean.prototype.$type, value));
			    return true;
			}
			
				switch (key) {
		case "allowSliceSelection":
			doughnutChart.allowSliceSelection(value);
			return true;
		case "isSurfaceInteractionDisabled":
			doughnutChart.isSurfaceInteractionDisabled(value);
			return true;
		case "allowSliceExplosion":
			doughnutChart.allowSliceExplosion(value);
			return true;
		case "innerExtent":
			doughnutChart.innerExtent(value);
			return true;
		case "selectedStyle":
			doughnutChart.selectedStyle(value);
			return true;
	}
			
		},
		_setDataTemplate: function (series, key, value) {
            var tempTemplate = new $.ig.DataTemplate(), requireThis = false;
            if (!value) {
                if (series[key]) {
                    series[key](null);
                }
                return;
            }

            if (value.requireThis) {
                requireThis = true;
            }

            if (value.render) {
                if (requireThis) {
                    tempTemplate.render($.proxy(value.render, value));
                } else {
                    tempTemplate.render(value.render);
                }
            } else {
                tempTemplate.render(value);
            }
            if (value.measure) {
                if (requireThis) {
                    tempTemplate.measure($.proxy(value.measure, value));
                } else {
                    tempTemplate.measure(value.measure);
                }
            }
            if (value.passStarting) {
                if (requireThis) {
                    tempTemplate.passStarting($.proxy(value.passStarting, value));
                } else {
                    tempTemplate.passStarting(value.passStarting);
                }
            }
            if (value.passCompleted) {
                if (requireThis) {
                    tempTemplate.passCompleted($.proxy(value.passCompleted, value));
                } else {
                    tempTemplate.passCompleted(value.passCompleted);
                }
            }
            if (series[key]) {
                series[key](tempTemplate);
            }
        },

		addSeries: function (seriesObj) {
		    /* Adds a new series to the doughnut chart.
				paramType="object" The series object to be added.
			*/
		    this._addSeries(this._chart, seriesObj);
		},
		
		_addSeries: function(chart, value) {
			var series;

			if(!value || value.remove == true || !this._seriesColl) return;					

			if (!this._seriesColl.hasOwnProperty(value.name)) 
			{
				if (!value.type || value.type === "flat") {
					series = new $.ig.RingSeries();
				} else {
					series = new $.ig.HierarchicalRingSeries();
				}

				series.name(value.name);
				series.chart(chart);

				this._seriesOpt[value.name] = {};
				for (currentKey in value) {
					if (value.hasOwnProperty(currentKey)) {
						this._setSeriesOption(series, currentKey, value[currentKey], chart);
						this._seriesOpt[value.name][currentKey] = value[currentKey];
					}
				}

				this._seriesColl[value.name] = series;								

				if (value.dataSource) {
					this._dataBindInternal(value, value.name);
				}
			    
				chart.series().add(series);
				if (series.selectedStyle() == null) {
				    series.selectedStyleResolved(chart.selectedStyle());
				}
			}
		},

		removeSeries: function (seriesObj) {
		    /* Removes the specified series from the doughnut chart.
				paramType="object" The series object identifying the series to be removed.
			*/
		    this._removeSeries(this._chart, seriesObj);
		},

		_removeSeries: function(chart, value) {
			if(!value || !this._seriesColl) return;

			if (this._seriesColl.hasOwnProperty(value.name)) 
			{
				var series = this._seriesColl[value.name];
				if(series)
				{						
					delete this._seriesColl[value.name];
					if(chart.series().contains(series))
					{
						this._setLegend(series);
						chart.series().remove(series);
						this._removeTooltip(series);
					}
				}
				if (this._seriesOpt[value.name])
					delete this._seriesOpt[value.name];
				if (this._tooltipTemplate[value.name])
					delete this._tooltipTemplate[value.name];
			}
		},

		updateSeries: function (value) {
		    /* Updates the series with the specified name with the specified new property values.
				paramType="object" The series object identifying the series to be updated.
			*/
			this._updateSeries(this._chart, value);			
		},

		_updateSeries: function(chart, value) {
			if(!value || !this._seriesColl) return;
			
			if (this._seriesColl.hasOwnProperty(value.name)) {
				var series = this._seriesColl[value.name];
				if(series && !value.remove){						
					for (currentKey in value) {
						if (value.hasOwnProperty(currentKey)) {
							this._setSeriesOption(series, currentKey, value[currentKey], chart);
							this._seriesOpt[value.name][currentKey] = value[currentKey];
						}
					}
					this._seriesColl[value.name] = series;								
					
					if (value.dataSource) {
						this._dataBindInternal(value, value.name);
					}
				}
			}		
		},
		getCenterCoordinates: function () {
		    /* Returns the center of the doughnut chart.
			    returnType="object" a JavaScript object specifying x and y coordinates of the point.
			*/
			if (this._chart)
			{
				var center = this._chart.getCenterCoordinates();
				return { x: center.__x, y: center.__y };
			}
			return {x: 0, y: 0};
		},
		getHoleRadius: function () {
		    /* Returns the radius of the chart's hole.
			    returnType="number"
			*/
			if (this._chart)
				return this._chart.getHoleRadius();
			return 0;
		},
		exportVisualData: function () {
		    /* Returns information about how the doughnut chart is rendered.
			    returnType="object" a JavaScript object containing the visual data.
			*/
		    if (this._chart) {
		        return this._chart.exportVisualData();
		    }

		    return null;
		},
		flush: function () {
		    /* Causes all of the series that have pending changes e.g. by changed property values to be rendered immediately. */
		    if (this.chart()) {
		        this.chart().flush();
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

		_defStyle: function (sel) {
			var v, bk0, b0, style = {}, name = sel + "SliceStyle",
				span = this.element.find("SPAN");
			if (span.length !== 1 || this.options[name]) {
				return;
			}
			bk0 = span.css("background-color");
			b0 = span.css("border-top-color");
			sel = "ui-doughnut-slice-" + sel;
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
		destroy: function () {
			/* Destroys the widget. */
			for (var seriesName in this._seriesColl){
				var series = this._seriesColl[seriesName];
				this._setLegend(series);				
				this._removeTooltip(series);
				delete this._seriesColl[seriesName];
				delete this._seriesOpt[seriesName];
				delete this._tooltipTemplate[seriesName];
			}
			delete this._tooltipTemplate;
			delete this._seriesOpt;
			delete this._seriesColl;
			delete this._tooltip;
			delete this._legends;
		    this.chart().destroy();
			
			$.ui.igBaseChart.prototype.destroy.apply(this);
		}
	});
	$.extend($.ui.igDoughnutChart, {version: '16.1.20161.2145'});
}(jQuery));



