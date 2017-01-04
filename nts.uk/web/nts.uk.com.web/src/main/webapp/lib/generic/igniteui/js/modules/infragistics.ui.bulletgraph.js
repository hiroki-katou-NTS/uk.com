/*!@license
* Infragistics.Web.ClientUI BulletGraph 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
*     jquery-1.8.3.js
*     jquery.ui.core.js
*     jquery.ui.widget.js
*     infragistics.util.js
*     infragistics.dv.simple.core.js
*     infragistics.gauge_bulletgraph.js
*/

/*global jQuery */
if (typeof (jQuery) === "undefined") {
    throw new Error("jQuery is undefined");
}

(function ($) {
    // igBulletGraph definition
    $.widget('ui.igBulletGraph', {
        css: {
            /* Class applied to main element, shown when the bulletGraph is opened in a non HTML5 compatible browser. */
            unsupportedBrowserClass: "ui-html5-non-html5-supported-message ui-helper-clearfix ui-html5-non-html5",
            /* Class applied to main element: ui-bulletGraph ui-corner-all ui-widget-content */
            bulletGraph: "ui-bulletgraph",
            /* Class applied to the tooltip element: ui-bulletgraph-tooltip ui-corner-all */
            tooltip: "ui-bulletgraph-tooltip ui-corner-all",
            /* Class applying background-color and border-color to the value element */
            bulletGraphValue: "ui-bulletgraph-value",
            /* Class applying background-color and border-color to the target value element */
            bulletGraphTargetValue: "ui-bulletgraph-targetvalue",
            /* Class applying background-color and border-color to the backing element */
            bulletGraphBacking: "ui-bulletgraph-backing",
            /* Class applying background-color to the tick elements */
            bulletGraphTick: "ui-bulletgraph-tick",
            /* Class applying background-color to the minor tick elements */
            bulletGraphMinorTick: "ui-bulletgraph-minortick",
            /* Class applying background-color to the text elements */
            bulletGraphLabel: "ui-bulletgraph-label",
            /* Class applying background-color and border-color to the n-th range of the bullet graph. As many palettes can be defined as neccessary. */
            bulletGraphPalette: "ui-bulletgraph-range-palette-n",
            /* Class applying background-image to the fill of the n-th range of the bullet graph. Only gradient colors are accepted. As many palettes can be defined as neccessary. */
            bulletGraphFillPalette: "ui-bulletgraph-range-fill-palette-n",
            /* Class applying background-image to the outline of the n-th range of the bullet graph. Only gradient colors are accepted. As many palettes can be defined as neccessary. */
            bulletGraphOutlinePalette: "ui-bulletgraph-range-outline-palette-n"
        },

        events: {
            	formatLabel: null,
	alignLabel: null

        },
        options: {
            /* type="string|number" The width of the gauge. It can be set as a number in pixels, string (px) or percentage (%).
                string The widget width can be set in pixels (px) and percentage (%).
                number The widget width can be set as a number
            */
            width: null,
            /* type="string|number" The height of the gauge. It can be set as a number in pixels, string (px) or percentage (%).
                string The widget height can be set in pixels (px) and percentage (%).
                number The widget height can be set as a number
            */
            height: null,
            /* type="array"
            Gets or sets the scale ranges to render on the bullet graph.
            */
            ranges: [
                      {
                          	/* type="string" Gets or sets the name of the range.
	*/
	name: null,
	/* type="string" Gets or sets the brush to use to fill the range.
	*/
	brush: null,
	/* type="string" Gets or sets the outline to use when rendering the range.
	*/
	outline: null,
	/* type="number" Gets or sets the value at which the range starts along the scale.
	*/
	startValue: 0,
	/* type="number" Gets or sets the value at which the range ends along the scale.
	*/
	endValue: 0,
	/* type="number" Gets or sets the distance measured from the front/bottom of the bullet graph (from 0 to 1) at which to start rendering the inner edge of the range.
	Values further from zero than 1 can be used to make this extend further than the normal width/height of the bullet graph.
	*/
	innerStartExtent: 0,
	/* type="number" Gets or sets the distance measured from the front/bottom of the bullet graph (from 0 to 1) at which to end rendering the inner edge of the range.
	Values further from zero than 1 can be used to make this extend further than the normal width/height of the bullet graph.
	*/
	innerEndExtent: 0,
	/* type="number" Gets or sets the distance measured from the front/bottom of the bullet graph (from 0 to 1) at which to start rendering the outer edge of the range.
	Values further from zero than 1 can be used to make this extend further than the normal width/height of the bullet graph.
	*/
	outerStartExtent: 0,
	/* type="number" Gets or sets the distance measured from the front/bottom of the bullet graph (from 0 to 1) at which to end rendering the outer edge of the range.
	Values further from zero than 1 can be used to make this extend further than the normal width/height of the bullet graph.
	*/
	outerEndExtent: 0,
	strokeThickness: 1

                      }
            ],
            /* type="string"
            Gets or sets the ranges' tooltip template.
            */
            rangeToolTipTemplate: null,
            /* type="string"
            Gets or sets the value's tooltip template.
            */
            valueToolTipTemplate: null,
            /* type="string"
            Gets or sets the target value's tooltip template.
            */
            targetValueToolTipTemplate: null,
            	/* type="horizontal|vertical" Gets or sets the orientation of the scale.
	horizontal type="string" 
	vertical type="string" 
	*/
	orientation: "horizontal",
	/* type="object" Gets or sets a collection of brushes to be used as the palette for bullet graph ranges.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	rangeBrushes: null,
	/* type="object" Gets or sets a collection of brushes to be used as the palette for bullet graph outlines.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	rangeOutlines: null,
	/* type="number" Gets or sets the minimum value of the scale.
	*/
	minimumValue: 0,
	/* type="number" Gets or sets the maximum value of the scale.
	*/
	maximumValue: 100,
	/* type="number" Gets or sets the value indicated by the target value bar.
	*/
	targetValue: 0,
	/* type="string" Gets or sets the name used for the target value. The name is displayed in the default target value tooltip.
	*/
	targetValueName: null,
	/* type="number" Gets or sets the value at which the bar ends.
	*/
	value: 0,
	/* type="string" Gets or sets the name used for actual value.
	*/
	valueName: null,
	/* type="number" Gets or sets the position at which to start rendering the ranges, measured from the front/bottom of the control as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	rangeInnerExtent: 0.2,
	/* type="number" Gets or sets the position at which to stop rendering the range as a value from 0 to 1 measured from the front/bottom of the bullet graph.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	rangeOuterExtent: 0.95,
	/* type="number" Gets or sets the position at which to start rendering the actual value geometries, measured from the front/bottom of the bullet graph as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	valueInnerExtent: 0.5,
	/* type="number" Gets or sets the position at which to stop rendering the actual value geometries as a value from 0 to 1 measured from the front/bottom of the bullet graph.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	valueOuterExtent: 0.65,
	/* type="number" Gets or sets the interval to use for the scale.
	*/
	interval: 0,
	/* type="number" A value to start adding tickmarks, added to the scale's MinimumValue.
	*/
	ticksPostInitial: 0,
	/* type="number" A value to stop adding tickmarks, subtracted from the scale's MaximumValue.
	*/
	ticksPreTerminal: 0,
	/* type="number" Gets or sets the interval to use for rendering labels. This defaults to be the same interval as the tickmarks on the scale.
	*/
	labelInterval: 0,
	/* type="number" Gets or sets the position at which to put the labels as a value from 0 to 1, measured from the bottom of the scale.
	Values further from zero than 1 can be used to hide the labels of the bullet graph.
	*/
	labelExtent: 0,
	/* type="number" A value to start adding labels, added to the scale's MinimumValue.
	*/
	labelsPostInitial: 0,
	/* type="number" A value to stop adding labels, subtracted from the scale's MaximumValue.
	*/
	labelsPreTerminal: 0,
	/* type="number" Gets or sets the number of minor tickmarks to place between major tickmarks.
	*/
	minorTickCount: 3,
	/* type="number" Gets or sets the position at which to start rendering the major tickmarks as a value from 0 to 1, measured from the front/bottom of the bullet graph.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	tickStartExtent: 0.1,
	/* type="number" Gets or sets the position at which to stop rendering the major tickmarks as a value from 0 to 1, measured from the front/bottom of the bullet graph.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	tickEndExtent: 0.2,
	/* type="number" Gets or sets the stroke thickness to use when rendering ticks.
	*/
	tickStrokeThickness: 2,
	/* type="string" Gets or sets the brush to use for the major tickmarks.
	*/
	tickBrush: null,
	/* type="string" Gets or sets the brush to use for the label font.
	*/
	fontBrush: null,
	/* type="string" Gets or sets the brush to use for the actual value element.
	*/
	valueBrush: null,
	/* type="string" Gets or sets the brush to use for the outline of actual value element.
	*/
	valueOutline: null,
	/* type="number" Gets or sets the stroke thickness to use when rendering single actual value element.
	*/
	valueStrokeThickness: 1,
	/* type="number" Gets or sets the position at which to start rendering the minor tickmarks as a value from 0 to 1, measured from the front/bottom of the bullet graph.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	minorTickStartExtent: 0.06,
	/* type="number" Gets or sets the position at which to stop rendering the minor tickmarks as a value from 0 to 1, measured from the front/bottom of the bullet graph.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	minorTickEndExtent: 0.2,
	/* type="number" Gets or sets the stroke thickness to use when rendering minor ticks.
	*/
	minorTickStrokeThickness: 1,
	/* type="string" Gets or sets the brush to use for the minor tickmarks.
	*/
	minorTickBrush: null,
	/* type="bool" Gets or sets a value indicating whether the scale is inverted.
	When the scale is inverted the direction in which the scale values increase is right to left.
	*/
	isScaleInverted: false,
	/* type="string" Gets or sets the brush to use to fill the backing of the bullet graph.
	*/
	backingBrush: null,
	/* type="string" Gets or sets the brush to use for the outline of the backing.
	*/
	backingOutline: null,
	/* type="number" Gets or sets the stroke thickness of the backing outline.
	*/
	backingStrokeThickness: 2,
	/* type="number" Gets or sets the inner extent of the bullet graph backing.
	*/
	backingInnerExtent: 0,
	/* type="number" Gets or sets the outer extent of the bullet graph backing.
	*/
	backingOuterExtent: 1,
	/* type="number" Gets or sets the position at which to start rendering the scale, measured from the front/bottom of the bullet graph as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	scaleStartExtent: 0.05,
	/* type="number" Gets or sets the position at which to stop rendering the scale as a value from 0 to 1, measured from the front/bottom of the bullet graph.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	scaleEndExtent: 0.95,
	/* type="string" Gets or sets the brush to use when rendering the fill of the comparative marker.
	*/
	targetValueBrush: null,
	/* type="number" Get or sets the breadth of the target value element.
	*/
	targetValueBreadth: 3,
	/* type="number" Gets or sets the position at which to start rendering the target value, measured from the front/bottom of the control as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	targetValueInnerExtent: 0.3,
	/* type="number" Gets or sets the position at which to start rendering the target value, measured from the front/bottom of the control as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal size of the bullet graph.
	*/
	targetValueOuterExtent: 0.85,
	/* type="string" Gets or sets the brush to use when rendering the outline of the target value.
	*/
	targetValueOutline: null,
	/* type="number" Gets or sets the stroke thickness of the outline of the target value bar.
	*/
	targetValueStrokeThickness: 1,
	/* type="number" Gets or sets the number of milliseconds over which changes to the bullet graph should be animated.
	*/
	transitionDuration: 0,
	/* type="number" Gets or sets the time in milliseconds that tooltip appearance is delayed with.
	*/
	showToolTipTimeout: 0,
	/* type="bool" Gets or sets a value indicating whether tooltips are enabled.
	*/
	showToolTip: false,
	/* type="string" Gets or sets the font.
	*/
	font: null

        },
        
        _setOption: function (key, value, checkPrev) {
            var bulletGraph = this._bulletGraph, o = this.options;
            if (checkPrev && o[key] === value) {
                return;
            }
            $.Widget.prototype._setOption.apply(this, arguments);

            if (this._set_option(bulletGraph, key, value)) {
                return this;
            }

            this._set_generated_option(bulletGraph, key, value);

            return this;
        },

        _set_generated_option: function (bulletGraph, key, value) {
            	switch (key) {
		case "orientation":
			switch(value) {
				case "horizontal":
					bulletGraph.orientation(0);
					break;
				case "vertical":
					bulletGraph.orientation(1);
					break;
			}
			return true;
		case "rangeBrushes":
			var isRGB = true, val = value ? value[0] : null;
			if (typeof val == "string" && val == "HSV" || val =="RGB") { if (value[0] == "HSV") { isRGB = false; } value = value.slice(1); }
var $tempBrushCollection = new $.ig.BrushCollection()
			for (var i = 0; value && i < value.length; i++) {
				var $tempBrush = $.ig.Brush.prototype.create(value[i]);
				$tempBrushCollection.add($tempBrush);
			}
			bulletGraph.rangeBrushes($tempBrushCollection);
			return true;
		case "rangeOutlines":
			var isRGB = true, val = value ? value[0] : null;
			if (typeof val == "string" && val == "HSV" || val =="RGB") { if (value[0] == "HSV") { isRGB = false; } value = value.slice(1); }
var $tempBrushCollection = new $.ig.BrushCollection()
			for (var i = 0; value && i < value.length; i++) {
				var $tempBrush = $.ig.Brush.prototype.create(value[i]);
				$tempBrushCollection.add($tempBrush);
			}
			bulletGraph.rangeOutlines($tempBrushCollection);
			return true;
		case "minimumValue":
			bulletGraph.minimumValue(value);
			return true;
		case "maximumValue":
			bulletGraph.maximumValue(value);
			return true;
		case "targetValue":
			bulletGraph.targetValue(value);
			return true;
		case "targetValueName":
			bulletGraph.targetValueName(value);
			return true;
		case "value":
			bulletGraph.value(value);
			return true;
		case "valueName":
			bulletGraph.valueName(value);
			return true;
		case "rangeInnerExtent":
			bulletGraph.rangeInnerExtent(value);
			return true;
		case "rangeOuterExtent":
			bulletGraph.rangeOuterExtent(value);
			return true;
		case "valueInnerExtent":
			bulletGraph.valueInnerExtent(value);
			return true;
		case "valueOuterExtent":
			bulletGraph.valueOuterExtent(value);
			return true;
		case "interval":
			bulletGraph.interval(value);
			return true;
		case "ticksPostInitial":
			bulletGraph.ticksPostInitial(value);
			return true;
		case "ticksPreTerminal":
			bulletGraph.ticksPreTerminal(value);
			return true;
		case "labelInterval":
			bulletGraph.labelInterval(value);
			return true;
		case "labelExtent":
			bulletGraph.labelExtent(value);
			return true;
		case "labelsPostInitial":
			bulletGraph.labelsPostInitial(value);
			return true;
		case "labelsPreTerminal":
			bulletGraph.labelsPreTerminal(value);
			return true;
		case "minorTickCount":
			bulletGraph.minorTickCount(value);
			return true;
		case "tickStartExtent":
			bulletGraph.tickStartExtent(value);
			return true;
		case "tickEndExtent":
			bulletGraph.tickEndExtent(value);
			return true;
		case "tickStrokeThickness":
			bulletGraph.tickStrokeThickness(value);
			return true;
		case "tickBrush":
			if (value == null) {
				bulletGraph.tickBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				bulletGraph.tickBrush($tempBrush);
			}
			return true;
		case "fontBrush":
			if (value == null) {
				bulletGraph.fontBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				bulletGraph.fontBrush($tempBrush);
			}
			return true;
		case "valueBrush":
			if (value == null) {
				bulletGraph.valueBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				bulletGraph.valueBrush($tempBrush);
			}
			return true;
		case "valueOutline":
			if (value == null) {
				bulletGraph.valueOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				bulletGraph.valueOutline($tempBrush);
			}
			return true;
		case "valueStrokeThickness":
			bulletGraph.valueStrokeThickness(value);
			return true;
		case "minorTickStartExtent":
			bulletGraph.minorTickStartExtent(value);
			return true;
		case "minorTickEndExtent":
			bulletGraph.minorTickEndExtent(value);
			return true;
		case "minorTickStrokeThickness":
			bulletGraph.minorTickStrokeThickness(value);
			return true;
		case "minorTickBrush":
			if (value == null) {
				bulletGraph.minorTickBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				bulletGraph.minorTickBrush($tempBrush);
			}
			return true;
		case "isScaleInverted":
			bulletGraph.isScaleInverted(value);
			return true;
		case "backingBrush":
			if (value == null) {
				bulletGraph.backingBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				bulletGraph.backingBrush($tempBrush);
			}
			return true;
		case "backingOutline":
			if (value == null) {
				bulletGraph.backingOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				bulletGraph.backingOutline($tempBrush);
			}
			return true;
		case "backingStrokeThickness":
			bulletGraph.backingStrokeThickness(value);
			return true;
		case "backingInnerExtent":
			bulletGraph.backingInnerExtent(value);
			return true;
		case "backingOuterExtent":
			bulletGraph.backingOuterExtent(value);
			return true;
		case "scaleStartExtent":
			bulletGraph.scaleStartExtent(value);
			return true;
		case "scaleEndExtent":
			bulletGraph.scaleEndExtent(value);
			return true;
		case "targetValueBrush":
			if (value == null) {
				bulletGraph.targetValueBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				bulletGraph.targetValueBrush($tempBrush);
			}
			return true;
		case "targetValueBreadth":
			bulletGraph.targetValueBreadth(value);
			return true;
		case "targetValueInnerExtent":
			bulletGraph.targetValueInnerExtent(value);
			return true;
		case "targetValueOuterExtent":
			bulletGraph.targetValueOuterExtent(value);
			return true;
		case "targetValueOutline":
			if (value == null) {
				bulletGraph.targetValueOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				bulletGraph.targetValueOutline($tempBrush);
			}
			return true;
		case "targetValueStrokeThickness":
			bulletGraph.targetValueStrokeThickness(value);
			return true;
		case "transitionDuration":
			bulletGraph.transitionDuration(value);
			return true;
		case "showToolTipTimeout":
			bulletGraph.showToolTipTimeout(value);
			return true;
		case "showToolTip":
			bulletGraph.showToolTip(value);
			return true;
		case "font":
			bulletGraph.font(value);
			return true;
	}

        },

        _set_option: function (bulletGraph, key, value) {
            var currentKey, templ;

            switch (key) {
                case "formatLabel":
                case "alignLabel":
                    bulletGraph.refresh();
                    return true;

                case "ranges":
                    var count = value.length;
                    for (i = 0; i < count; i++) {
                        if (!value[i].name) {
                            throw new Error("Range name is missing for range: " + i);
                        }

                        if (this._rangesColl.hasOwnProperty(value[i].name)) {
                            //Removes range if value[i].remove == true else updates the range
                            this._removeCollValue(bulletGraph.ranges(), this._rangesColl, value[i]);
                            this._updateCollValue(bulletGraph.ranges(), this._rangesColl, value[i], this._setRangeOption);
                        }
                        else
                            this._addCollValue(bulletGraph.ranges(), this._rangesColl, value[i], this._setRangeOption, function () {
                                return new $.ig.XamLinearGraphRange();
                            });
                    }

                    return true;
                case "width":
                    this._setSize(bulletGraph, "width", value);
                    return true;
                case "height":
                    this._setSize(bulletGraph, "height", value);
                    return true;
                case "rangeToolTipTemplate":
                    this._tooltipTemplates['range'] = this._resolveTemplate(value);
                    return true;
                case "valueToolTipTemplate":
                    this._tooltipTemplates['value'] = this._resolveTemplate(value);
                    return true;
                case "targetValueToolTipTemplate":
                    this._tooltipTemplates['targetvalue'] = this._resolveTemplate(value);
                    return true;
                case "showToolTip":
                    if (value === true) {
                        var tooltip = $("<div class='" + this.css.tooltip + "' style='white-space: nowrap; z-index: 10000;'></div>");
                        this._addTooltip(bulletGraph, tooltip, 'range');
                        //this._addTooltip(bulletGraph, tooltip, 'value');
                        //this._addTooltip(bulletGraph, tooltip, 'targetvalue');
                    }
                    if (value === false) {
                        this._removeTooltip(bulletGraph);
                    }

                    bulletGraph.showToolTip(value);
                    return true;
            }
        },

        _resolveTemplate: function(value){
            var templ;
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
                
                return templ;
            }
        },

        _htmlCheckExpr: /^[^<]*(<[\w\W]+>)[^>]*$/,
        //_tooltipCheckExpre: //,        
        _addTooltip: function (bulletGraph, tooltip, name) {
            this._removeTooltipEvents(bulletGraph, tooltip);
            this._bindTooltipEvents(bulletGraph, tooltip);
            bulletGraph.toolTip(tooltip);
        },

        _removeTooltip: function (bulletGraph, name) {
            this._removeTooltipEvents(bulletGraph, bulletGraph.toolTip());
            bulletGraph.toolTip(null);
        },

        _bindTooltipEvents: function (chart, tooltip) {
            tooltip.updateToolTip = $.ig.Delegate.prototype.combine(tooltip.updateToolTip, jQuery.proxy(this._fireToolTip_updateToolTip, this));
            tooltip.hideToolTip = $.ig.Delegate.prototype.combine(tooltip.hideToolTip, jQuery.proxy(this._fireToolTip_hideToolTip, this));
        },

        _removeTooltipEvents: function (chart, tooltip) {
            delete tooltip.updateToolTip;
            delete tooltip.hideToolTip;
        },

        _resolveTemplateContext: function (args, name) {
            var e = {};
            e.itemName = args.itemName();
            e.itemBrush = args.brush().fill();
            e.outline = args.outline().fill();
            e.thickness = args.thickness();
            e.label = args.label();
            switch (name) {
                case 'range':
                    e.item = {
                        name: args.item().name(),
                        startValue: args.item().startValue(),
                        endValue: args.item().endValue()
                    };
                    break;
                case 'value':
                    e.item = {
                        name: e.itemName,
                        value: args.item()
                    };
                    break;
                case 'targetvalue':
                    e.item = {
                        name: e.itemName,
                        value: args.item()
                    };
                    break;
            }

            return e;
        },

        _fireToolTip_updateToolTip: function (args, name) {
            var e, noCancel = true, template;
            template = this._tooltipTemplates[name];
            if (template === undefined) {
                template = this._tooltipDefaultTemplates[name];
                this._tooltipTemplates[name] = template;
            }

            this._bulletGraph.toolTip().children().remove();
            e = this._resolveTemplateContext(args, name);
            if (e.item === null) {
                noCancel = false;
            }

            if (noCancel) {
                var templ = $.ig.tmpl(template, e);
                this._bulletGraph.toolTip().html(templ);
            }
        },

        getRangeNames: function () {
            /* Returns a string containing the names of all the ranges delimited with a \n symbol. */
            var rangeNames = "";
            for (var key in this._rangesColl) {
                rangeNames += key + "\n";
            }
            return rangeNames;
        },

        addRange: function (value) {
            /* Adds a new range to the bullet graph.
				paramType="object" The range object to be added.
			*/
            this._addCollValue(this._bulletGraph.ranges(), this._rangesColl, value, this._setRangeOption, function () {
                return new $.ig.XamLinearGraphRange();
            });
        },

        _addCollValue: function (target, source, value, setItemOptionCallback, createItemCallback) {
            if (!value || value.remove == true || !source) return;

            if (!source.hasOwnProperty(value.name)) {
                var range = createItemCallback();
                for (currentKey in value) {
                    if (value.hasOwnProperty(currentKey)) {
                        setItemOptionCallback(range, currentKey, value[currentKey]);
                    }
                }
                source[value.name] = range;
                target.add(range);
            }
        },

        removeRange: function (value) {
            /* Removes a range from the bullet graph.
				paramType="object" A JS object with properties set as follows: name: nameOfTheRangeToRemove, remove: true
			*/
            this._removeCollValue(this._bulletGraph.ranges(), this._rangesColl, value);
        },

        _removeCollValue: function (target, source, value) {
            if (!value || !source) return;

            if (source.hasOwnProperty(value.name)) {
                var range = source[value.name];
                if (range && value.remove == true) {
                    delete source[value.name];
                    if (target.contains(range))
                        target.remove(range);
                }
            }
        },

        updateRange: function (value) {
            /* Updates the specified range of the bullet graph.
				paramType="object" The range object to be updated.
			*/
            this._updateCollValue(this._bulletGraph.ranges(), this._rangesColl, value, this._setRangeOption);
        },

        _updateCollValue: function (target, source, value, setItemOptionCallBack) {
            if (!value || !source) return;

            if (source.hasOwnProperty(value.name)) {
                var range = source[value.name];
                if (range && !value.remove) {
                    for (currentKey in value) {
                        if (value.hasOwnProperty(currentKey)) {
                            setItemOptionCallBack(range, currentKey, value[currentKey]);
                        }
                    }
                    source[value.name] = range;
                }
            }
        },

        _creationOptions: null,
        _bulletGraph: null,
        _bulletGraphId: null,
        _createWidget: function (options, element, widget) {
            this._creationOptions = options;

            $.Widget.prototype._createWidget.apply(this, [options, element]);
        },

        _create: function () {
            var key, v, size, bulletGraph, width, height,
            i = -1,
            self = this,
            elem = self.element,           
            style = elem[0].style,
            o = this._creationOptions;

            // variable which holds initial attributes/styles of target widget, which are used to
            // restore target element on destroy
            self._old_state = {
                // extended widget may add other attributes to that member and they will be processed within destroy
                style: { position: style.position, width: style.width, height: style.height },
                css: elem[0].className,
                elems: elem.find("*")
            };
            if (!$.ig.util._isCanvasSupported()) {
                $.ig.util._renderUnsupportedBrowser(this);
                return;
            }

            bulletGraph = this._createBulletGraph();

            self._bulletGraph = bulletGraph;            

            			bulletGraph.formatLabel = $.ig.Delegate.prototype.combine(bulletGraph.formatLabel, jQuery.proxy(this._fireBulletGraph_formatLabel, this));
			bulletGraph.alignLabel = $.ig.Delegate.prototype.combine(bulletGraph.alignLabel, jQuery.proxy(this._fireBulletGraph_alignLabel, this));

            this._bulletGraphId = Date.now();
            this._tooltipTemplates = {};
            this._rangesColl = {};
            this._tooltipDefaultTemplates = {};
            this._tooltipDefaultTemplates['range'] = "<div class='ui-bulletgraph-range-tooltip' style='border-color: ${itemBrush};'><span>${label}</span></div>";
            this._tooltipDefaultTemplates['value'] = "<div class='ui-bulletgraph-value-tooltip' style='border-color: ${itemBrush};'><span>${label}</span></div>";
            this._tooltipDefaultTemplates['targetvalue'] = "<div class='ui-bulletgraph-targetvalue-tooltip' style='border-color: ${itemBrush};'><span>${label}</span></div>";

            if (o.hasOwnProperty("width"))
                elem[0].style.width = o["width"];
            if (o.hasOwnProperty("height"))
                elem[0].style.height = o["height"];

            bulletGraph.provideContainer(elem[0]);
            elem.mousemove(function (eventData) {
                var point = $.ig.APIFactory.prototype.createPoint(eventData.pageX, eventData.pageY);
               
                bulletGraph.onMouseOver(point, true);
            });

            elem.mouseleave(function () {
                bulletGraph.onMouseLeave();
            });

            for (key in o) {
                if (o.hasOwnProperty(key)) {
                    v = o[key];
                    if (v !== null) {
                        this._setOption(key, v, false);
                    }
                }
            }

            while (i++ < 1) {
                key = i === 0 ? "width" : "height";
                if (o[key]) {
                    size = key;
                    v = o[key];
                } else {
                    v = elem[0].style[key];
                }
                if (v && typeof(v) === "string" && v.indexOf("%") > 0) {
                    self._setSize(bulletGraph, size = key, v);
                }
            }
            //_setSize should be called at least once: support for initially invisible container of char
            if (!size) {
                self._setSize(bulletGraph, "width");
            }

            if (self.css && self.css.bulletGraph) {
                elem.addClass(self.css.bulletGraph);
            }
        },
        _createBulletGraph: function () {
            return new $.ig.XamBulletGraph();
            //return new $.ig.BulletGraph();

        },

        _fireBulletGraph_formatLabel: function (bulletGraph, evt) {
            var opts = {};
            opts.actualMinimumValue = evt.actualMinimumValue;
            opts.actualMaximumValue = evt.actualMaximumValue;            
            opts.value = evt.value;
            opts.label = evt.label;
            opts.owner = this;

            var event = this.options["formatLabel"];
            if (event !== undefined)
                this._trigger("formatLabel", null, opts);
            else
                opts.label = (Math.round(opts.value * 100) / 100);
          
            evt.value = opts.value;
            evt.label = opts.label;
        },

        _fireBulletGraph_alignLabel: function (bulletGraph, evt) {
            var opts = {};
            opts.actualMinimumValue = evt.actualMinimumValue;
            opts.actualMaximumValue = evt.actualMaximumValue;        
            opts.value = evt.value;
            opts.label = evt.label;
            opts.width = evt.width;
            opts.height = evt.height;
            opts.offsetX = evt.offsetX;
            opts.offsetY = evt.offsetY;
            opts.owner = this;

            this._trigger("alignLabel", null, opts);

            evt.value = opts.value;
            evt.label = opts.label;
            evt.offsetX = opts.offsetX;
            evt.offsetY = opts.offsetY;
            evt.width = opts.width;
            evt.height = opts.height;
        },

        // sets width and height options.
        // params:
        // chart-reference to chart object
        // key-name of option/property (width or height only)
        // value-value of option
        _setSize: function (bulletGraph, key, val) {
            $.ig.util.setSize(this.element, key, val, bulletGraph, this._getNotifyResizeName());
        },

        _getNotifyResizeName: function () {
            return "containerResized";
        },

        _setRangeOption: function (range, key, value) {
            switch (key) {
                case "name":
                    range.name(value);
                    break;
                case "brush":
                    range.brush($.ig.Brush.prototype.create(value));
                    break;
                case "outline":
                    range.outline($.ig.Brush.prototype.create(value));
                    break;
                case "startValue":
                    range.startValue(value);
                    break;
                case "endValue":
                    range.endValue(value);
                    break;
                case "innerStartExtent":
                    range.innerStartExtent(value);
                    break;
                case "innerEndExtent":
                    range.innerEndExtent(value);
                    break;
                case "outerStartExtent":
                    range.outerStartExtent(value);
                    break;
                case "outerEndExtent":
                    range.outerEndExtent(value);
                    break;
                case "strokeThickness":
                    range.strokeThickness(value);
                    break;
            }
        },
        
        exportVisualData: function () {
            /* Returns information about how the bullet graph is rendered.
			    returnType="object" a JavaScript object containing the visual data.
			*/
            if (this._bulletGraph)
                return this._bulletGraph.exportVisualData();
        },

        flush: function () {
            /* Causes all pending changes of the bullet graph e.g. by changed property values to be rendered immediately. */
            if (this._bulletGraph && this._bulletGraph.view())
                this._bulletGraph.view().flush();
        },

        destroy: function () {
            /* Destroys widget. */
            var key, style,
				bulletGraph = this._bulletGraph,
				old = this._old_state,
				elem = this.element;
            if (!old) {
                return;
            }
            // remove children created by bulletGraph
            elem.find("*").not(old.elems).remove();
            // restore className modified by bulletGraph
            if (this.css.bulletGraph) {
                elem.removeClass(this.css.bulletGraph);
            }
            // restore css style attributes modified by bulletGraph
            old = old.style;
            style = elem[0].style;
            for (key in old) {
                if (old.hasOwnProperty(key)) {
                    if (style[key] !== old[key]) {
                        style[key] = old[key];
                    }
                }
            }
            if (bulletGraph) {
                this._setSize(bulletGraph);
            }
            elem.off("mousemove");
            elem.off("mouseleave");

            $.Widget.prototype.destroy.apply(this, arguments);
            if (bulletGraph && bulletGraph.destroy) {
                bulletGraph.destroy();
            }
            delete this._bulletGraph;
            delete this._old_state;
        },

        styleUpdated: function () {
            /* Re-polls the css styles for the widget. Use this method when the css styles have been modified. */
            if (this._bulletGraph) {
                this._bulletGraph.styleUpdated();
            }
        }
    });
    $.extend($.ui.igBulletGraph, { version: '16.1.20161.2145' });
}(jQuery));
