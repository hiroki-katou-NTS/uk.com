/*!@license
* Infragistics.Web.ClientUI LinearGauge 16.1.20161.2145
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
*     infragistics.gauge_lineargauge.js
*/

/*global jQuery */
if (typeof (jQuery) === "undefined") {
    throw new Error("jQuery is undefined");
}

(function ($) {
    // igLinearGauge definition
    $.widget('ui.igLinearGauge', {
        css: {
            /* Class applied to main element, shown when the linearGauge is opened in a non HTML5 compatible browser. */
            unsupportedBrowserClass: "ui-html5-non-html5-supported-message ui-helper-clearfix ui-html5-non-html5",
            /* Class applied to main element: ui-linearGauge ui-corner-all ui-widget-content */
            linearGauge: "ui-lineargauge",
            /* Class applied to the tooltip element: ui-lineargauge-tooltip ui-corner-all */
            tooltip: "ui-lineargauge-tooltip ui-corner-all",
            /* Class applying background-color and border-color to the needle element */
            linearGaugeNeedle: "ui-lineargauge-needle",
            /* Class applying background-color and border-color to the backing element */
            linearGaugeBacking: "ui-lineargauge-backing",
            /* Class applying background-color to the tick elements */
            linearGaugeTick: "ui-lineargauge-tick",
            /* Class applying background-color to the minor tick elements */
            linearGaugeMinorTick: "ui-lineargauge-minortick",
            /* Class applying background-color to the text elements */
            linearGaugeLabel: "ui-lineargauge-label",
            /* Class applying background-color and border-color to the n-th range of the linear gauge. As many palettes can be defined as neccessary. */
            linearGaugePalette: "ui-lineargauge-range-palette-n",
            /* Class applying background-image to the fill of the n-th range of the linear gauge. Only gradient colors are accepted. As many palettes can be defined as neccessary. */
            linearGaugeFillPalette: "ui-lineargauge-range-fill-palette-n",
            /* Class applying background-image to the outline of the n-th range of the linear gauge. Only gradient colors are accepted. As many palettes can be defined as neccessary. */
            linearGaugeOutlinePalette: "ui-lineargauge-range-outline-palette-n"
        },

        events: {
            	formatLabel: null,
	alignLabel: null,
	/* cancel="false" Occurs when the Value property changes.
	*/
	valueChanged: null

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
            Gets or sets the scale ranges to render on the linear gauge.
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
            Gets or sets the needle's tooltip template.
            */
            needleToolTipTemplate: null,             

            	/* type="horizontal|vertical" Gets or sets the orientation of the scale.
	horizontal type="string" 
	vertical type="string" 
	*/
	orientation: "horizontal",
	/* type="object" Gets or sets a collection of brushes to be used as the palette for linear gauge ranges.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	rangeBrushes: null,
	/* type="object" Gets or sets a collection of brushes to be used as the palette for linear gauge outlines.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	rangeOutlines: null,
	/* type="number" Gets or sets the minimum value of the scale.
	*/
	minimumValue: 0,
	/* type="number" Gets or sets the maximum value of the scale.
	*/
	maximumValue: 100,
	/* type="number" Gets or sets the value at which the needle is positioned.
	*/
	value: 0,
	/* type="custom|rectangle|triangle|needle|trapezoid" Gets or sets the shape to use when rendering the needle from a number of options.
	custom type="string" 
	rectangle type="string" 
	triangle type="string" 
	needle type="string" 
	trapezoid type="string" 
	*/
	needleShape: null,
	/* type="string" Gets or sets the name used for needle.
	*/
	needleName: null,
	/* type="number" Gets or sets the position at which to start rendering the ranges, measured from the front/bottom of the control as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	rangeInnerExtent: 0.05,
	/* type="number" Gets or sets the position at which to start rendering the scale, measured from the bottom/front (when orientation is horizontal/vertical) of the control as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	scaleInnerExtent: 0,
	/* type="number" Gets or sets the position at which to stop rendering the range as a value from 0 to 1 measured from the front/bottom of the linear gauge.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	rangeOuterExtent: 0.65,
	/* type="number" Gets or sets the position at which to stop rendering the scale as a value from 0 to 1 measured from the bottom/front (when orientation is horizontal/vertical) of the linear gauge.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	scaleOuterExtent: 0,
	/* type="number" Gets or sets the position at which to start rendering the needle geometry, measured from the front/bottom of the linear gauge as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	needleInnerExtent: 0,
	/* type="number" Gets or sets the position at which to stop rendering the needle geometry as a value from 0 to 1 measured from the front/bottom of the linear gauge.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	needleOuterExtent: 0,
	/* type="number" Gets or sets the width of the needle's inner base.
	*/
	needleInnerBaseWidth: 0,
	/* type="number" Gets or sets the width of the needle's outer base.
	*/
	needleOuterBaseWidth: 0,
	/* type="number" Gets or sets the width of the needle's inner point.
	*/
	needleInnerPointWidth: 0,
	/* type="number" Gets or sets the width of the needle's outer point.
	*/
	needleOuterPointWidth: 0,
	/* type="number" Gets or sets the extent of the needle's inner point.
	*/
	needleInnerPointExtent: 0,
	/* type="number" Gets or sets the extent of the needle's outer point.
	*/
	needleOuterPointExtent: 0,
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
	Values further from zero than 1 can be used to hide the labels of the linear gauge.
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
	/* type="number" Gets or sets the position at which to start rendering the major tickmarks as a value from 0 to 1, measured from the front/bottom of the linear gauge.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	tickStartExtent: 0.05,
	/* type="number" Gets or sets the position at which to stop rendering the major tickmarks as a value from 0 to 1, measured from the front/bottom of the linear gauge.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	tickEndExtent: 0.65,
	/* type="number" Gets or sets the stroke thickness to use when rendering ticks.
	*/
	tickStrokeThickness: 2,
	/* type="string" Gets or sets the brush to use for the major tickmarks.
	*/
	tickBrush: null,
	/* type="string" Gets or sets the brush to use for the label font.
	*/
	fontBrush: null,
	needleBreadth: 6,
	/* type="string" Gets or sets the brush to use for needle element.
	*/
	needleBrush: null,
	/* type="string" Gets or sets the brush to use for the outline of needle element.
	*/
	needleOutline: null,
	needleStrokeThickness: 1,
	/* type="number" Gets or sets the position at which to start rendering the minor tickmarks as a value from 0 to 1, measured from the front/bottom of the linear gauge.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	minorTickStartExtent: 0.05,
	/* type="number" Gets or sets the position at which to stop rendering the minor tickmarks as a value from 0 to 1, measured from the front/bottom of the linear gauge.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	minorTickEndExtent: 0.35,
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
	/* type="string" Gets or sets the brush to use to fill the backing of the linear gauge.
	*/
	backingBrush: null,
	/* type="string" Gets or sets the brush to use for the outline of the backing.
	*/
	backingOutline: null,
	/* type="number" Gets or sets the stroke thickness of the backing outline.
	*/
	backingStrokeThickness: 2,
	/* type="number" Gets or sets the inner extent of the linear gauge backing.
	*/
	backingInnerExtent: 0,
	/* type="number" Gets or sets the outer extent of the linear gauge backing.
	*/
	backingOuterExtent: 1,
	/* type="number" Gets or sets the position at which to start rendering the scale, measured from the front/bottom of the linear gauge as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	scaleStartExtent: 0.05,
	/* type="number" Gets or sets the position at which to stop rendering the scale as a value from 0 to 1, measured from the front/bottom of the linear gauge.
	Values further from zero than 1 can be used to make this extend further than the normal size of the linear gauge.
	*/
	scaleEndExtent: 0.95,
	/* type="string" Gets or sets the brush to use to fill the scale of the linear gauge.
	*/
	scaleBrush: null,
	/* type="string" Gets or sets the brush to use for the outline of the scale.
	*/
	scaleOutline: null,
	scaleStrokeThickness: 0,
	/* type="bool" Gets or sets whether needle dragging is enabled or not.
	*/
	isNeedleDraggingEnabled: false,
	/* type="number" Gets or sets the number of milliseconds over which changes to the linear gauge should be animated.
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
            var linearGauge = this._bulletGraph, o = this.options;
            if (checkPrev && o[key] === value) {
                return;
            }
            $.Widget.prototype._setOption.apply(this, arguments);

            if (this._set_option(linearGauge, key, value)) {
                return this;
            }

            this._set_generated_option(linearGauge, key, value);

            return this;
        },

        _set_generated_option: function (linearGauge, key, value) {
            	switch (key) {
		case "orientation":
			switch(value) {
				case "horizontal":
					linearGauge.orientation(0);
					break;
				case "vertical":
					linearGauge.orientation(1);
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
			linearGauge.rangeBrushes($tempBrushCollection);
			return true;
		case "rangeOutlines":
			var isRGB = true, val = value ? value[0] : null;
			if (typeof val == "string" && val == "HSV" || val =="RGB") { if (value[0] == "HSV") { isRGB = false; } value = value.slice(1); }
var $tempBrushCollection = new $.ig.BrushCollection()
			for (var i = 0; value && i < value.length; i++) {
				var $tempBrush = $.ig.Brush.prototype.create(value[i]);
				$tempBrushCollection.add($tempBrush);
			}
			linearGauge.rangeOutlines($tempBrushCollection);
			return true;
		case "minimumValue":
			linearGauge.minimumValue(value);
			return true;
		case "maximumValue":
			linearGauge.maximumValue(value);
			return true;
		case "value":
			linearGauge.value(value);
			return true;
		case "needleShape":
			switch(value) {
				case "custom":
					linearGauge.needleShape(0);
					break;
				case "rectangle":
					linearGauge.needleShape(1);
					break;
				case "triangle":
					linearGauge.needleShape(2);
					break;
				case "needle":
					linearGauge.needleShape(3);
					break;
				case "trapezoid":
					linearGauge.needleShape(4);
					break;
			}
			return true;
		case "needleName":
			linearGauge.needleName(value);
			return true;
		case "rangeInnerExtent":
			linearGauge.rangeInnerExtent(value);
			return true;
		case "scaleInnerExtent":
			linearGauge.scaleInnerExtent(value);
			return true;
		case "rangeOuterExtent":
			linearGauge.rangeOuterExtent(value);
			return true;
		case "scaleOuterExtent":
			linearGauge.scaleOuterExtent(value);
			return true;
		case "needleInnerExtent":
			linearGauge.needleInnerExtent(value);
			return true;
		case "needleOuterExtent":
			linearGauge.needleOuterExtent(value);
			return true;
		case "needleInnerBaseWidth":
			linearGauge.needleInnerBaseWidth(value);
			return true;
		case "needleOuterBaseWidth":
			linearGauge.needleOuterBaseWidth(value);
			return true;
		case "needleInnerPointWidth":
			linearGauge.needleInnerPointWidth(value);
			return true;
		case "needleOuterPointWidth":
			linearGauge.needleOuterPointWidth(value);
			return true;
		case "needleInnerPointExtent":
			linearGauge.needleInnerPointExtent(value);
			return true;
		case "needleOuterPointExtent":
			linearGauge.needleOuterPointExtent(value);
			return true;
		case "interval":
			linearGauge.interval(value);
			return true;
		case "ticksPostInitial":
			linearGauge.ticksPostInitial(value);
			return true;
		case "ticksPreTerminal":
			linearGauge.ticksPreTerminal(value);
			return true;
		case "labelInterval":
			linearGauge.labelInterval(value);
			return true;
		case "labelExtent":
			linearGauge.labelExtent(value);
			return true;
		case "labelsPostInitial":
			linearGauge.labelsPostInitial(value);
			return true;
		case "labelsPreTerminal":
			linearGauge.labelsPreTerminal(value);
			return true;
		case "minorTickCount":
			linearGauge.minorTickCount(value);
			return true;
		case "tickStartExtent":
			linearGauge.tickStartExtent(value);
			return true;
		case "tickEndExtent":
			linearGauge.tickEndExtent(value);
			return true;
		case "tickStrokeThickness":
			linearGauge.tickStrokeThickness(value);
			return true;
		case "tickBrush":
			if (value == null) {
				linearGauge.tickBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				linearGauge.tickBrush($tempBrush);
			}
			return true;
		case "fontBrush":
			if (value == null) {
				linearGauge.fontBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				linearGauge.fontBrush($tempBrush);
			}
			return true;
		case "needleBreadth":
			linearGauge.needleBreadth(value);
			return true;
		case "needleBrush":
			if (value == null) {
				linearGauge.needleBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				linearGauge.needleBrush($tempBrush);
			}
			return true;
		case "needleOutline":
			if (value == null) {
				linearGauge.needleOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				linearGauge.needleOutline($tempBrush);
			}
			return true;
		case "needleStrokeThickness":
			linearGauge.needleStrokeThickness(value);
			return true;
		case "minorTickStartExtent":
			linearGauge.minorTickStartExtent(value);
			return true;
		case "minorTickEndExtent":
			linearGauge.minorTickEndExtent(value);
			return true;
		case "minorTickStrokeThickness":
			linearGauge.minorTickStrokeThickness(value);
			return true;
		case "minorTickBrush":
			if (value == null) {
				linearGauge.minorTickBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				linearGauge.minorTickBrush($tempBrush);
			}
			return true;
		case "isScaleInverted":
			linearGauge.isScaleInverted(value);
			return true;
		case "backingBrush":
			if (value == null) {
				linearGauge.backingBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				linearGauge.backingBrush($tempBrush);
			}
			return true;
		case "backingOutline":
			if (value == null) {
				linearGauge.backingOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				linearGauge.backingOutline($tempBrush);
			}
			return true;
		case "backingStrokeThickness":
			linearGauge.backingStrokeThickness(value);
			return true;
		case "backingInnerExtent":
			linearGauge.backingInnerExtent(value);
			return true;
		case "backingOuterExtent":
			linearGauge.backingOuterExtent(value);
			return true;
		case "scaleStartExtent":
			linearGauge.scaleStartExtent(value);
			return true;
		case "scaleEndExtent":
			linearGauge.scaleEndExtent(value);
			return true;
		case "scaleBrush":
			if (value == null) {
				linearGauge.scaleBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				linearGauge.scaleBrush($tempBrush);
			}
			return true;
		case "scaleOutline":
			if (value == null) {
				linearGauge.scaleOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				linearGauge.scaleOutline($tempBrush);
			}
			return true;
		case "scaleStrokeThickness":
			linearGauge.scaleStrokeThickness(value);
			return true;
		case "isNeedleDraggingEnabled":
			linearGauge.isNeedleDraggingEnabled(value);
			return true;
		case "transitionDuration":
			linearGauge.transitionDuration(value);
			return true;
		case "showToolTipTimeout":
			linearGauge.showToolTipTimeout(value);
			return true;
		case "showToolTip":
			linearGauge.showToolTip(value);
			return true;
		case "font":
			linearGauge.font(value);
			return true;
	}

        },

        _set_option: function (linearGauge, key, value) {
            var currentKey, templ;

            switch (key) {
                case "formatLabel":
                case "alignLabel":
                    linearGauge.refresh();
                    return true;
                case "ranges":
                    var count = value.length;
                    for (i = 0; i < count; i++) {
                        if (!value[i].name) {
                            throw new Error("Range name is missing for range: " + i);
                        }

                        if (this._rangesColl.hasOwnProperty(value[i].name)) {
                            //Removes range if value[i].remove == true else updates the range
                            this._removeCollValue(linearGauge.ranges(), this._rangesColl, value[i]);
                            this._updateCollValue(linearGauge.ranges(), this._rangesColl, value[i], this._setRangeOption);
                        }
                        else
                            this._addCollValue(linearGauge.ranges(), this._rangesColl, value[i], this._setRangeOption, function () {
                                return new $.ig.XamLinearGraphRange();
                            });
                    }

                    return true;
                case "width":
                    this._setSize(linearGauge, "width", value);
                    return true;
                case "height":
                    this._setSize(linearGauge, "height", value);
                    return true;                
                case "rangeToolTipTemplate":
                    this._tooltipTemplates['range'] = this._resolveTemplate(value);
                    return true;
                case "actualValueTooltipTemplate":
                    this._tooltipTemplates['actualValue'] = this._resolveTemplate(value);
                    return true;
                case "needleToolTipTemplate":
                    this._tooltipTemplates['needle'] = this._resolveTemplate(value);
                    return true;
                case "showToolTip":
                    if (value === true) {
                        var tooltip = $("<div class='" + this.css.tooltip + "' style='white-space: nowrap; z-index: 10000;'></div>");
                        this._addTooltip(linearGauge, tooltip, 'range');
                        //this._addTooltip(linearGauge, tooltip, 'actualValue');
                        //this._addTooltip(linearGauge, tooltip, 'targetValue');
                    }
                    if (value === false) {
                        this._removeTooltip(linearGauge);
                    }

                    linearGauge.showToolTip(value);
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
        _addTooltip: function (linearGauge, tooltip, name) {
            this._removeTooltipEvents(linearGauge, tooltip);
            this._bindTooltipEvents(linearGauge, tooltip);
            linearGauge.toolTip(tooltip);
        },

        _removeTooltip: function (linearGauge, name) {
            this._removeTooltipEvents(linearGauge, linearGauge.toolTip());
            linearGauge.toolTip(null);
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
                case 'needle':
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
            /* Returns a string containing the names of all the ranges delimited with a \n symbol.
                    returnType="string"
            */
            var rangeNames = "";
            for (var key in this._rangesColl) {
                rangeNames += key + "\n";
            }
            return rangeNames;
        },

        addRange: function (value) {
            /* Adds a new range to the linear gauge.
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
            /* Removes a range from the linear gauge.
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
            /* Updates the specified range of the linear gauge.
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
            var key, v, size, linearGauge, width, height,
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

            linearGauge = this._createLinearGauge();

            self._bulletGraph = linearGauge;            

            			linearGauge.formatLabel = $.ig.Delegate.prototype.combine(linearGauge.formatLabel, jQuery.proxy(this._fireLinearGauge_formatLabel, this));
			linearGauge.alignLabel = $.ig.Delegate.prototype.combine(linearGauge.alignLabel, jQuery.proxy(this._fireLinearGauge_alignLabel, this));
			linearGauge.valueChanged = $.ig.Delegate.prototype.combine(linearGauge.valueChanged, jQuery.proxy(this._fireLinearGauge_valueChanged, this));

            this._bulletGraphId = Date.now();
            this._tooltipTemplates = {};
            this._rangesColl = {};
            this._needlesColl = {};
            this._tooltipDefaultTemplates = {};
            this._tooltipDefaultTemplates['range'] = "<div class='ui-lineargauge-range-tooltip' style='border-color: ${itemBrush};'><span>${label}</span></div>";
            this._tooltipDefaultTemplates['needle'] = "<div class='ui-lineargauge-needle-tooltip' style='border-color: ${itemBrush};'><span>${label}</span></div>";

            if (o.hasOwnProperty("width"))
                elem[0].style.width = o["width"];
            if (o.hasOwnProperty("height"))
                elem[0].style.height = o["height"];

            linearGauge.provideContainer(elem[0]);

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
                if (v && typeof (v) === "string" && v.indexOf("%") > 0) {
                    self._setSize(linearGauge, size = key, v);
                }
            }
            //_setSize should be called at least once: support for initially invisible container of char
            if (!size) {
                self._setSize(linearGauge, "width");
            }

            if (self.css && self.css.linearGauge) {
                elem.addClass(self.css.linearGauge);
            }
        },
        _createLinearGauge: function () {
            return new $.ig.XamLinearGauge();
            //return new $.ig.LinearGauge();

        },

        _fireLinearGauge_formatLabel: function (linearGauge, evt) {
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

        _fireLinearGauge_alignLabel: function (linearGauge, evt) {
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

        _fireLinearGauge_valueChanged: function (linearGauge, evt) {
            var opts = {
                oldValue: evt._oldValue,
                newValue: evt._newValue,
                owner: this
            };

            this.options.value = opts.newValue;
            this._trigger("valueChanged", null, opts);
        },

        // sets width and height options.
        // params:
        // chart-reference to chart object
        // key-name of option/property (width or height only)
        // value-value of option
        _setSize: function (linearGauge, key, val) {
            $.ig.util.setSize(this.element, key, val, linearGauge, this._getNotifyResizeName());
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
         
        getValueForPoint: function (x, y) {
            /* Gets the value for the main scale of the gauge for a given point within the bounds of the gauge. 
                returnType="number" The value for the main scale of the gauge.
            */
            if (this._bulletGraph) {
                var point = $.ig.APIFactory.prototype.createPoint(x, y);
                return this._bulletGraph.getValueForPoint(point);
            }
        },

        needleContainsPoint: function (x, y) {
            /* Returns true if the main gauge needle bounding box contains the point provided, otherwise false.
                paramType="number" The x coordinate of the point.
                paramType="number" The y coordinate of the point.
            */
            if (this._bulletGraph) {
                var point = $.ig.APIFactory.prototype.createPoint(x, y);
                return this._bulletGraph.needleContainsPoint(point);
            }
        },
        
        exportVisualData: function () {
            /* Returns information about how the linear gauge is rendered.
			    returnType="object" a JavaScript object containing the visual data.
			*/
            if (this._bulletGraph)
                return this._bulletGraph.exportVisualData();
        },

        flush: function () {
            /* Causes all pending changes of the linear gauge e.g. by changed property values to be rendered immediately. */
            if (this._bulletGraph && this._bulletGraph.view())
                this._bulletGraph.view().flush();
        },

        destroy: function () {
            /* Destroys widget. */
            var key, style,
				linearGauge = this._bulletGraph,
				old = this._old_state,
				elem = this.element;
            if (!old) {
                return;
            }
            // remove children created by linearGauge
            elem.find("*").not(old.elems).remove();
            // restore className modified by linearGauge
            if (this.css.linearGauge) {
                elem.removeClass(this.css.linearGauge);
            }
            // restore css style attributes modified by linearGauge
            old = old.style;
            style = elem[0].style;
            for (key in old) {
                if (old.hasOwnProperty(key)) {
                    if (style[key] !== old[key]) {
                        style[key] = old[key];
                    }
                }
            }
            if (linearGauge) {
                this._setSize(linearGauge);
            }

            $.Widget.prototype.destroy.apply(this, arguments);
            if (linearGauge && linearGauge.destroy) {
                linearGauge.destroy();
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
    $.extend($.ui.igLinearGauge, { version: '16.1.20161.2145' });
}(jQuery));
