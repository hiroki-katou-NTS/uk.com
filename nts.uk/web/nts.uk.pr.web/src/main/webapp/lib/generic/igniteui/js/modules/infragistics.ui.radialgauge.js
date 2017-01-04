/*!@license
* Infragistics.Web.ClientUI RadialGauge 16.1.20161.2145
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
*     infragistics.gauge_radialgauge.js
*/

/*global jQuery */
if (typeof (jQuery) === "undefined") {
       throw new Error("jQuery is undefined");
}

(function ($) {
    // igRadialGauge definition
       $.widget('ui.igRadialGauge', {
        css: {
           /* Get the class applied to main element, shown when the radialGauge is opened in a non HTML5 compatible browser. */
           unsupportedBrowserClass: "ui-html5-non-html5-supported-message ui-helper-clearfix ui-html5-non-html5",
			/* Get the class applied to main element: ui-radialGauge ui-corner-all ui-widget-content */
           radialGauge: "ui-radialgauge"
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
			  ranges:[
						{
							/* type="string"
							Gets or sets the name of the range.
							*/
							name: null,
							/* type="double"
							Gets or sets the starting value of the range.
							*/
    		    			startValue: 0, 
							/* type="double"
							Gets or sets the ending value of the range.
							*/
    		    			endValue: 0,
							/* type="double"
							Gets or sets the starting value of the outer extent of the range.
							*/
    		    			outerStartExtent: 0,
							/* type="double"
							Gets or sets the ending value of the outer extent of the range.
							*/
    		    			outerEndExtent: 0,
							/* type="double"
							Gets or sets the starting value of the inner extent of the range.
							*/
    		    			innerStartExtent: 0,
							/* type="double"
							Gets or sets the ending value of the inner extent of the range.
							*/
    		    			innerEndExtent: 0,							
							/* type="string"
							Gets or sets the brush for the entire range.
							*/
    		    			brush: null,
							/* type="string"
							Gets or sets the brush for the outline of the range.
							*/
    		    			outline: null,
							/* type="double"
							Gets or sets the thickness of the range outline.
							*/
    		    			strokeThickness: 0,
							/* type="bool"
							Gets or sets the flag used to determine if the range should be removed. If set to true, the range (if existing) is removed.
							*/					
							remove: false
						}
					 ],				
              		/* type="object" Gets or sets a collection of brushes to be used as the palette for gauge ranges.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	rangeBrushes: null,
	/* type="object" Gets or sets a collection of brushes to be used as the palette for gauge outlines.
	The value provided should be an array of css color strings or JavaScript objects defining gradients. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
	*/
	rangeOutlines: null,
	/* type="number" Gets or sets the minimum value of the scale.
	*/
	minimumValue: 0,
	/* type="number" Gets or sets the maximum value of the scale.
	*/
	maximumValue: 100,
	/* type="number" Gets or sets the interval to use for the scale.
	*/
	interval: 0,
	/* type="number" Gets or sets the x position of the center of the gauge with the value ranging from 0 to 1.
	*/
	centerX: 0.5,
	/* type="number" Gets or sets the y position of the center of the gauge with the value ranging from 0 to 1.
	*/
	centerY: 0.5,
	/* type="number" Gets or sets the value at which to point the needle of the gauge.
	*/
	value: 0,
	/* type="number" Gets or sets the start angle for the scale in degrees.
	*/
	scaleStartAngle: 135,
	/* type="number" Gets or sets the end angle for the scale in degrees.
	*/
	scaleEndAngle: 45,
	/* type="counterclockwise|clockwise" Gets or sets the direction in which the scale sweeps around the center from the start angle to end angle.
	counterclockwise type="string" 
	clockwise type="string" 
	*/
	scaleSweepDirection: "clockwise",
	/* type="number" Gets or sets the number of milliseconds over which changes to the gauge should be animated.
	*/
	transitionDuration: 0,
	/* type="object" Gets or sets the easing function used to morph the current series.
	*/
	transitionEasingFunction: null,
	/* type="string" Gets or sets the brush to use when rendering the fill of the needle.
	*/
	needleBrush: null,
	/* type="string" Gets or sets the brush to use when rendering the outline of the needle.
	*/
	needleOutline: null,
	/* type="number" Gets or sets the extent (from -1 to 1) at which to start rendering the needle, measured from the center of the gauge. 
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	needleStartExtent: 0,
	/* type="number" Gets or sets the extent (from -1 to 1) at which to end rendering the needle, measured from the center of the gauge.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	needleEndExtent: 0,
	/* type="none|rectangle|triangle|needle|trapezoid|rectangleWithBulb|triangleWithBulb|needleWithBulb|trapezoidWithBulb" Gets or sets the shape to use when rendering the needle from a number of options.
	none type="string" 
	rectangle type="string" 
	triangle type="string" 
	needle type="string" 
	trapezoid type="string" 
	rectangleWithBulb type="string" 
	triangleWithBulb type="string" 
	needleWithBulb type="string" 
	trapezoidWithBulb type="string" 
	*/
	needleShape: "needle",
	/* type="number" Gets or sets the width of the needle at its point using a value from (0 to 1). Note: Only some needle shapes respect this property.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	needleStartWidthRatio: 0,
	/* type="number" Gets or sets the width of the needle at its point using a value from (0 to 1). Note: Only some needle shapes respect this property.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	needleEndWidthRatio: 0,
	/* type="number" Gets or sets the width of the needle at its feature which is closest to the base (e.g. a bulb) with a value from 0 to 1. Note: Only some needle shapes respect this property.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	needleBaseFeatureWidthRatio: 0,
	/* type="number" Gets or sets the extent of the feature which is closest to the base (e.g. a bulb) with a value from -1 to 1. Note: Only some needle shapes respect this property.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	needleBaseFeatureExtent: 0,
	/* type="number" Gets or sets the width of the needle at its feature which is closest to the point (e.g. the tapering point of a needle) with a value from 0 to 1. Note: Only some needle shapes respect this property.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	needlePointFeatureWidthRatio: 0,
	/* type="number" Gets or sets the extent of the feature which is closest to the point (e.g. the tapering point of a needle) with a value from -1 to 1. Note: Only some needle shapes respect this property.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	needlePointFeatureExtent: 0,
	/* type="number" Gets or sets the width of the cap of the needle with a value from 0 to 1. Note: Will only take effect if you have a cap set on the needle.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	needlePivotWidthRatio: 0,
	/* type="number" Gets or sets the width of the inner cutout section of the needle cap with a value from 0 to 1. Note: Will only take effect if you have a cap set on the needle that has a cutout section.
	*/
	needlePivotInnerWidthRatio: 0,
	/* type="none|circle|circleWithHole|circleOverlay|circleOverlayWithHole|circleUnderlay|circleUnderlayWithHole" Gets or sets the shape to use for the needle cap.
	none type="string" 
	circle type="string" 
	circleWithHole type="string" 
	circleOverlay type="string" 
	circleOverlayWithHole type="string" 
	circleUnderlay type="string" 
	circleUnderlayWithHole type="string" 
	*/
	needlePivotShape: "circleOverlay",
	/* type="number" Gets or sets the position at which to start rendering the scale, measured from the center of the gauge as a value from 0 to 1.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	scaleStartExtent: 0.5,
	/* type="string" Gets or sets the brush to use for filling the needle cap. Note: this only applies to certain cap shapes.
	*/
	needlePivotBrush: null,
	/* type="string" Gets or sets the brush to use for the outlines of the needle cap.
	*/
	needlePivotOutline: null,
	/* type="number" Gets or sets the stroke thickness of the needle outline.
	*/
	needleStrokeThickness: 1,
	/* type="number" Gets or sets the stroke thickness to use for the outline of the needle cap.
	*/
	needlePivotStrokeThickness: 1,
	/* type="number" Gets or sets the position at which to stop rendering the scale as a value from 0 to 1 measured from the center of the gauge.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	scaleEndExtent: 0.57,
	/* type="number" Gets or sets the position at which to put the labels as a value from 0 to 1, measured form the center of the gauge.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	labelExtent: 0.65,
	/* type="number" Gets or sets the interval to use for rendering labels. This defaults to be the same interval as the tickmarks on the scale.
	*/
	labelInterval: 0,
	/* type="number" Gets or sets the position at which to start rendering the major tickmarks as a value from 0 to 1, measured from the center of the gauge.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	tickStartExtent: 0.5,
	/* type="number" Gets or sets the position at which to stop rendering the major tickmarks as a value from 0 to 1, measured from the center of the gauge.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	tickEndExtent: 0.57,
	/* type="number" Gets or sets the stroke thickness to use when rendering ticks.
	*/
	tickStrokeThickness: 3,
	/* type="string" Gets or sets the brush to use for the major tickmarks.
	*/
	tickBrush: null,
	/* type="string" Gets or sets the brush to use for the label font.
	*/
	fontBrush: null,
	/* type="number" Gets or sets the position at which to start rendering the minor tickmarks as a value from 0 to 1, measured from the center of the gauge.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	minorTickStartExtent: 0.54,
	/* type="number" Gets or sets the position at which to stop rendering the minor tickmarks as a value from 0 to 1, measured from the center of the gauge.
	Values further from zero than 1 can be used to make this extend further than the normal radius of the gauge.
	*/
	minorTickEndExtent: 0.57,
	/* type="number" Gets or sets the stroke thickness to use when rendering minor ticks.
	*/
	minorTickStrokeThickness: 2,
	/* type="string" Gets or sets the brush to use for the minor tickmarks.
	*/
	minorTickBrush: null,
	/* type="number" Gets or sets the number of minor tickmarks to place between major tickmarks.
	*/
	minorTickCount: 3,
	/* type="string" Gets or sets the brush to use to fill the background of the scale.
	*/
	scaleBrush: null,
	/* type="string" Gets or sets the brush to use to fill the backing of the gauge.
	*/
	backingBrush: null,
	/* type="string" Gets or sets the brush to use for the outline of the backing.
	*/
	backingOutline: null,
	/* type="number" Gets or sets the stroke thickness of the backing outline.
	*/
	backingStrokeThickness: 12,
	/* type="number" Gets or sets the outer extent of the gauge backing.
	*/
	backingOuterExtent: 0.82,
	/* type="number" Gets or sets the over sweep angle to apply to the backing if it is displaying fitted (in degrees). Must be greater or equal to 0.
	*/
	backingOversweep: 3,
	/* type="number" Gets or sets the extra degrees of sweep to apply to the scale background. Must be greater or equal to 0.
	*/
	scaleOversweep: 2.8,
	/* type="auto|circular|fitted" Gets or sets the over or shape to use for the excess fill area for the scale.
	auto type="string" 
	circular type="string" 
	fitted type="string" 
	*/
	scaleOversweepShape: "auto",
	/* type="number" Gets or sets the corner rounding radius to use for the fitted scale backings.
	*/
	backingCornerRadius: 4,
	/* type="number" Gets or sets the inner extent of the gauge backing.
	*/
	backingInnerExtent: 0.12,
	/* type="circular|fitted" Gets or sets the type of shape to use for the backing of the gauge.
	circular type="string" 
	fitted type="string" 
	*/
	backingShape: "circular",
	/* type="number" Gets or sets the multiplying factor to apply to the normal radius of the gauge. 
	The radius of the gauge is defined by the minimum of the width and height of the control divided by 2.0. 
	This introduces a multiplicative factor to that value.
	*/
	radiusMultiplier: 1,
	/* type="omitLast|omitFirst|omitNeither|omitBoth" Gets or sets the strategy to use for omitting labels if the first and last label have the same value.
	omitLast type="string" 
	omitFirst type="string" 
	omitNeither type="string" 
	omitBoth type="string" 
	*/
	duplicateLabelOmissionStrategy: "omitLast",
	/* type="bool" Gets or sets whether needle dragging is enabled or not.
	*/
	isNeedleDraggingEnabled: false,
	/* type="bool" Gets or sets whether the needle is constrained within the minimum and maximum value range during dragging.
	*/
	isNeedleDraggingConstrained: true,
	font: null,
	/* type="number" Gets the transition progress of the animation when the control is animating.
	*/
	transitionProgress: 0

			},
           				
				_rangesColl: {},
			   _setOption: function (key, value, checkPrev) {
                     var radialGauge = this._radialGauge, o = this.options;
                     if (checkPrev && o[key] === value) {
                           return;
                     }
                     $.Widget.prototype._setOption.apply(this, arguments);

					 if (this._set_option(radialGauge, key, value)) {
						return this;
					 }

					this._set_generated_option(radialGauge, key, value);
					 
					return this;
				},

				_set_generated_option: function (radialGauge, key, value) {
							switch (key) {
		case "rangeBrushes":
			var isRGB = true, val = value ? value[0] : null;
			if (typeof val == "string" && val == "HSV" || val =="RGB") { if (value[0] == "HSV") { isRGB = false; } value = value.slice(1); }
var $tempBrushCollection = new $.ig.BrushCollection()
			for (var i = 0; value && i < value.length; i++) {
				var $tempBrush = $.ig.Brush.prototype.create(value[i]);
				$tempBrushCollection.add($tempBrush);
			}
			radialGauge.rangeBrushes($tempBrushCollection);
			return true;
		case "rangeOutlines":
			var isRGB = true, val = value ? value[0] : null;
			if (typeof val == "string" && val == "HSV" || val =="RGB") { if (value[0] == "HSV") { isRGB = false; } value = value.slice(1); }
var $tempBrushCollection = new $.ig.BrushCollection()
			for (var i = 0; value && i < value.length; i++) {
				var $tempBrush = $.ig.Brush.prototype.create(value[i]);
				$tempBrushCollection.add($tempBrush);
			}
			radialGauge.rangeOutlines($tempBrushCollection);
			return true;
		case "minimumValue":
			radialGauge.minimumValue(value);
			return true;
		case "maximumValue":
			radialGauge.maximumValue(value);
			return true;
		case "interval":
			radialGauge.interval(value);
			return true;
		case "centerX":
			radialGauge.centerX(value);
			return true;
		case "centerY":
			radialGauge.centerY(value);
			return true;
		case "value":
			radialGauge.value(value);
			return true;
		case "scaleStartAngle":
			radialGauge.scaleStartAngle(value);
			return true;
		case "scaleEndAngle":
			radialGauge.scaleEndAngle(value);
			return true;
		case "scaleSweepDirection":
			switch(value) {
				case "counterclockwise":
					radialGauge.scaleSweepDirection(0);
					break;
				case "clockwise":
					radialGauge.scaleSweepDirection(1);
					break;
			}
			return true;
		case "transitionDuration":
			radialGauge.transitionDuration(value);
			return true;
		case "needleBrush":
			if (value == null) {
				radialGauge.needleBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.needleBrush($tempBrush);
			}
			return true;
		case "needleOutline":
			if (value == null) {
				radialGauge.needleOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.needleOutline($tempBrush);
			}
			return true;
		case "needleStartExtent":
			radialGauge.needleStartExtent(value);
			return true;
		case "needleEndExtent":
			radialGauge.needleEndExtent(value);
			return true;
		case "needleShape":
			switch(value) {
				case "none":
					radialGauge.needleShape(0);
					break;
				case "rectangle":
					radialGauge.needleShape(1);
					break;
				case "triangle":
					radialGauge.needleShape(2);
					break;
				case "needle":
					radialGauge.needleShape(3);
					break;
				case "trapezoid":
					radialGauge.needleShape(4);
					break;
				case "rectangleWithBulb":
					radialGauge.needleShape(5);
					break;
				case "triangleWithBulb":
					radialGauge.needleShape(6);
					break;
				case "needleWithBulb":
					radialGauge.needleShape(7);
					break;
				case "trapezoidWithBulb":
					radialGauge.needleShape(8);
					break;
			}
			return true;
		case "needleStartWidthRatio":
			radialGauge.needleStartWidthRatio(value);
			return true;
		case "needleEndWidthRatio":
			radialGauge.needleEndWidthRatio(value);
			return true;
		case "needleBaseFeatureWidthRatio":
			radialGauge.needleBaseFeatureWidthRatio(value);
			return true;
		case "needleBaseFeatureExtent":
			radialGauge.needleBaseFeatureExtent(value);
			return true;
		case "needlePointFeatureWidthRatio":
			radialGauge.needlePointFeatureWidthRatio(value);
			return true;
		case "needlePointFeatureExtent":
			radialGauge.needlePointFeatureExtent(value);
			return true;
		case "needlePivotWidthRatio":
			radialGauge.needlePivotWidthRatio(value);
			return true;
		case "needlePivotInnerWidthRatio":
			radialGauge.needlePivotInnerWidthRatio(value);
			return true;
		case "needlePivotShape":
			switch(value) {
				case "none":
					radialGauge.needlePivotShape(0);
					break;
				case "circle":
					radialGauge.needlePivotShape(1);
					break;
				case "circleWithHole":
					radialGauge.needlePivotShape(2);
					break;
				case "circleOverlay":
					radialGauge.needlePivotShape(3);
					break;
				case "circleOverlayWithHole":
					radialGauge.needlePivotShape(4);
					break;
				case "circleUnderlay":
					radialGauge.needlePivotShape(5);
					break;
				case "circleUnderlayWithHole":
					radialGauge.needlePivotShape(6);
					break;
			}
			return true;
		case "scaleStartExtent":
			radialGauge.scaleStartExtent(value);
			return true;
		case "needlePivotBrush":
			if (value == null) {
				radialGauge.needlePivotBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.needlePivotBrush($tempBrush);
			}
			return true;
		case "needlePivotOutline":
			if (value == null) {
				radialGauge.needlePivotOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.needlePivotOutline($tempBrush);
			}
			return true;
		case "needleStrokeThickness":
			radialGauge.needleStrokeThickness(value);
			return true;
		case "needlePivotStrokeThickness":
			radialGauge.needlePivotStrokeThickness(value);
			return true;
		case "scaleEndExtent":
			radialGauge.scaleEndExtent(value);
			return true;
		case "labelExtent":
			radialGauge.labelExtent(value);
			return true;
		case "labelInterval":
			radialGauge.labelInterval(value);
			return true;
		case "tickStartExtent":
			radialGauge.tickStartExtent(value);
			return true;
		case "tickEndExtent":
			radialGauge.tickEndExtent(value);
			return true;
		case "tickStrokeThickness":
			radialGauge.tickStrokeThickness(value);
			return true;
		case "tickBrush":
			if (value == null) {
				radialGauge.tickBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.tickBrush($tempBrush);
			}
			return true;
		case "fontBrush":
			if (value == null) {
				radialGauge.fontBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.fontBrush($tempBrush);
			}
			return true;
		case "minorTickStartExtent":
			radialGauge.minorTickStartExtent(value);
			return true;
		case "minorTickEndExtent":
			radialGauge.minorTickEndExtent(value);
			return true;
		case "minorTickStrokeThickness":
			radialGauge.minorTickStrokeThickness(value);
			return true;
		case "minorTickBrush":
			if (value == null) {
				radialGauge.minorTickBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.minorTickBrush($tempBrush);
			}
			return true;
		case "minorTickCount":
			radialGauge.minorTickCount(value);
			return true;
		case "scaleBrush":
			if (value == null) {
				radialGauge.scaleBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.scaleBrush($tempBrush);
			}
			return true;
		case "backingBrush":
			if (value == null) {
				radialGauge.backingBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.backingBrush($tempBrush);
			}
			return true;
		case "backingOutline":
			if (value == null) {
				radialGauge.backingOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				radialGauge.backingOutline($tempBrush);
			}
			return true;
		case "backingStrokeThickness":
			radialGauge.backingStrokeThickness(value);
			return true;
		case "backingOuterExtent":
			radialGauge.backingOuterExtent(value);
			return true;
		case "backingOversweep":
			radialGauge.backingOversweep(value);
			return true;
		case "scaleOversweep":
			radialGauge.scaleOversweep(value);
			return true;
		case "scaleOversweepShape":
			switch(value) {
				case "auto":
					radialGauge.scaleOversweepShape(0);
					break;
				case "circular":
					radialGauge.scaleOversweepShape(1);
					break;
				case "fitted":
					radialGauge.scaleOversweepShape(2);
					break;
			}
			return true;
		case "backingCornerRadius":
			radialGauge.backingCornerRadius(value);
			return true;
		case "backingInnerExtent":
			radialGauge.backingInnerExtent(value);
			return true;
		case "backingShape":
			switch(value) {
				case "circular":
					radialGauge.backingShape(0);
					break;
				case "fitted":
					radialGauge.backingShape(1);
					break;
			}
			return true;
		case "radiusMultiplier":
			radialGauge.radiusMultiplier(value);
			return true;
		case "duplicateLabelOmissionStrategy":
			switch(value) {
				case "omitLast":
					radialGauge.duplicateLabelOmissionStrategy(0);
					break;
				case "omitFirst":
					radialGauge.duplicateLabelOmissionStrategy(1);
					break;
				case "omitNeither":
					radialGauge.duplicateLabelOmissionStrategy(2);
					break;
				case "omitBoth":
					radialGauge.duplicateLabelOmissionStrategy(3);
					break;
			}
			return true;
		case "isNeedleDraggingEnabled":
			radialGauge.isNeedleDraggingEnabled(value);
			return true;
		case "isNeedleDraggingConstrained":
			radialGauge.isNeedleDraggingConstrained(value);
			return true;
		case "font":
			radialGauge.font(value);
			return true;
		case "transitionProgress":
			radialGauge.transitionProgress(value);
			return true;
	}

				},

				_set_option: function (radialGauge, key, value) {
					var currentKey;

					switch (key) {
					    case "formatLabel":
					    case "alignLabel":
					        radialGauge.refresh();
					        return true;

						case "ranges":							
							var count = value.length;
							for(i = 0; i < count;i++)  
							{
								if (!value[i].name) {
									throw new Error("Range name is missing for range: " + i);
								}

								if (this._rangesColl.hasOwnProperty(value[i].name)) 
								{	
									//Removes range if value[i].remove == true else updates the range
									this._removeRange(radialGauge, value[i]);			
									this._updateRange(radialGauge, value[i]);	
								}
								else
									this._addRange(radialGauge, value[i]);
							}

							return true;
					    case "width":
					        this._setSize(radialGauge, "width", value);
					        return true;
					    case "height":
					        this._setSize(radialGauge, "height", value);
					        return true;
					    case "transitionEasingFunction":
					        radialGauge.transitionEasingFunction($.ig.util.getEasingFunction(value));
					        return true;
					}
              },

			getRangeNames: function () {
				/* Returns a string containing the names of all the ranges delimited with a \n symbol.
					returnType="string"
				*/
					var rangeNames = "";
					for(var key in this._rangesColl)
					{
						rangeNames += key + "\n";
					}
					return rangeNames;
			},
			
			addRange: function (value) {
				/* Adds a new range to the radial gauge. */
					this._addRange(this._radialGauge, value);
			},

			  _addRange: function(radialGauge, value) {
			      if (!value || value.remove == true || !this._rangesColl || !radialGauge) return;

					if (!this._rangesColl.hasOwnProperty(value.name)) 
					{
						var range = new $.ig.XamRadialGaugeRange();
						for (currentKey in value) {
							if (value.hasOwnProperty(currentKey)) {
								this._setRangeOption(range, currentKey, value[currentKey]);
							}
						}
						this._rangesColl[value.name] = range;
						if(radialGauge.ranges())
						    radialGauge.ranges().add(range);
					}
			  },

			  removeRange: function (value) {
			      /* Removes a specified range. */
					this._removeRange(this._radialGauge, value);
			  },

			  _removeRange: function(radialGauge, value) {
			      if (!value || !this._rangesColl || !radialGauge) return;

					if (this._rangesColl.hasOwnProperty(value.name)) 
					{
						var range = this._rangesColl[value.name];
						if(range && value.remove == true)
						{						
							delete this._rangesColl[value.name];
							if(radialGauge.ranges() && radialGauge.ranges().contains(range))
								radialGauge.ranges().remove(range);
						}
					}
			  },

			  updateRange: function (value) {
			      /* Updates the range. */
					this._updateRange(this._radialGauge, value);
			  },

			  _updateRange: function(radialGauge, value) {
			      if (!value || !this._rangesColl || !radialGauge) return;

					if (this._rangesColl.hasOwnProperty(value.name)) {
						var range = this._rangesColl[value.name];
						if(range && !value.remove){						
							for (currentKey in value) {
								if (value.hasOwnProperty(currentKey)) {
									this._setRangeOption(range, currentKey, value[currentKey]);
								}
							}
							this._rangesColl[value.name] = range;								
						}
					}
			  },

			  clearRanges: function () {
			      /* Clears the ranges in the radial gauge. */
			      if (!this._radialGauge || !this._radialGauge.ranges()) return;

			      this._radialGauge.ranges().clear();
			      this._rangesColl = {};
			  },

			  _creationOptions: null,
			  _radialGauge: null,
			  _createWidget: function (options, element, widget) {
					this._creationOptions = options;
					
					$.Widget.prototype._createWidget.apply(this, [options, element]);
			  },

              _create: function () {
				var key, v, size, radialGauge, width, height,
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
				radialGauge = this._createGauge();
				self._radialGauge = radialGauge;
				
							radialGauge.formatLabel = $.ig.Delegate.prototype.combine(radialGauge.formatLabel, jQuery.proxy(this._fireRadialGauge_formatLabel, this));
			radialGauge.alignLabel = $.ig.Delegate.prototype.combine(radialGauge.alignLabel, jQuery.proxy(this._fireRadialGauge_alignLabel, this));
			radialGauge.valueChanged = $.ig.Delegate.prototype.combine(radialGauge.valueChanged, jQuery.proxy(this._fireRadialGauge_valueChanged, this));

								
				if (o.hasOwnProperty("width"))
				    elem[0].style.width = o["width"];
				if (o.hasOwnProperty("height"))
				    elem[0].style.height = o["height"];

				radialGauge.provideContainer(elem[0]);
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
						self._setSize(radialGauge, size = key, v);
					}					
				}
				//_setSize should be called at least once: support for initially invisible container of char
				if (!size) {
					self._setSize(radialGauge, "width");
				}
				
				if (self.css && self.css.radialGauge) {
				    elem.addClass(self.css.radialGauge);
				}			
		},
		_createGauge: function () {
		    this._rangesColl = {};
		    return new $.ig.XamRadialGauge();		    
		},
		
		_fireRadialGauge_formatLabel: function (radialGauge, evt) {
			var opts = {};
			opts.actualMinimumValue = evt.actualMinimumValue;
			opts.actualMaximumValue = evt.actualMaximumValue;
			opts.startAngle = evt.startAngle;
			opts.endAngle = evt.endAngle;
			opts.angle = evt.angle;
			opts.value = evt.value;
			opts.label = evt.label;
			opts.owner = this;

			this._trigger("formatLabel", null, opts);

			evt.value = opts.value;
			evt.label = opts.label;
		},

		_fireRadialGauge_alignLabel: function (radialGauge, evt) {
			var opts = {};
			opts.actualMinimumValue = evt.actualMinimumValue;
			opts.actualMaximumValue = evt.actualMaximumValue;
			opts.startAngle = evt.startAngle;
			opts.endAngle = evt.endAngle;
			opts.angle = evt.angle;
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

		_fireRadialGauge_valueChanged: function (radialGauge, evt) {
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
		_setSize: function (radialGauge, key, val) {
			$.ig.util.setSize(this.element, key, val, radialGauge, this._getNotifyResizeName());
		},

		_getNotifyResizeName: function () {
			return "containerResized";
		},
		
		_setRangeOption: function(range, key, value) {
			switch (key) {
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
				case "outerStartExtent":
					range.outerStartExtent(value);
					break;
				case "outerEndExtent":
					range.outerEndExtent(value);
					break;
				case "innerStartExtent":
					range.innerStartExtent(value);
					break;
				case "innerEndExtent":
					range.innerEndExtent(value);
					break;
				case "strokeThickness":
					range.strokeThickness(value);
					break;
			}
		},
		
		scaleValue: function (value) {
		    /* Scales a value on the gauge's main scale to an angle around the center point of the gauge, in radians. */
			if(this._radialGauge)
				return this._radialGauge.scaleValue(value);
		},

		unscaleValue: function(value) {
		    /* Unscales a value from an angle in radians to the represented value along the main scale of the gauge. */
		    if (this._radialGauge)
				return this._radialGauge.unscaleValue(value);
		},

		getValueForPoint: function (x, y) {
			/* Gets the value for the main scale of the gauge for a given point within the bounds of the gauge. 
				returnType="number" The value for the main scale of the gauge.
			*/
		    if (this._radialGauge) {
		        var point = $.ig.APIFactory.prototype.createPoint(x, y);
				return this._radialGauge.getValueForPoint(point);
			}
		},

		getPointForValue: function (value, extent) {
		    /* Gets the point on the gauge for a given scale value and extent. */
		    if (this._radialGauge) {
		        var point = this._radialGauge.getPointForValue(value, extent);
		        return {
		            x: point.__x,
		            y: point.__y
		        };
		    }
		},

		needleContainsPoint: function(x, y) {
		    /* Returns true if the main gauge needle bounding box contains the point provided, otherwise false. */
		    if (this._radialGauge) {
		        var point = $.ig.APIFactory.prototype.createPoint(x, y);
				return this._radialGauge.needleContainsPoint(point);
			}
		},

		exportVisualData: function () {
		    /* Exports the visual data for the radial gauge. */
		    if (this._radialGauge)
		        return this._radialGauge.exportVisualData();
		},

		flush: function () {
		    /* Flushes the gauge. */
		    if (this._radialGauge && this._radialGauge.view())
		        this._radialGauge.view().flush();
		},

        destroy: function () {
            /* Destroys widget. */
            var key, style,
				radialGauge = this._radialGauge,
				old = this._old_state,
				elem = this.element;
            if (!old) {
                return;
            }
            // remove children created by radialGauge
            elem.find("*").not(old.elems).remove();
            // restore className modified by radialGauge
            if (this.css.radialGauge) {
                elem.removeClass(this.css.radialGauge);
            }
            // restore css style attributes modified by radialGauge
            old = old.style;
            style = elem[0].style;
            for (key in old) {
                if (old.hasOwnProperty(key)) {
                    if (style[key] !== old[key]) {
                        style[key] = old[key];
                    }
                }
            }            
            if (radialGauge) {
                this._setSize(radialGauge);
            }
            $.Widget.prototype.destroy.apply(this, arguments);
            if (radialGauge && radialGauge.destroy) {
                radialGauge.destroy();
            }
            delete this._radialGauge;
            delete this._old_state;
        },

        styleUpdated: function () {
            /* Returns true if the style was updated for the radial gauge. */
            if (this._radialGauge) {
                this._radialGauge.styleUpdated();
            }
        }
       });
       $.extend($.ui.igRadialGauge, {version: '16.1.20161.2145'});
}(jQuery));
