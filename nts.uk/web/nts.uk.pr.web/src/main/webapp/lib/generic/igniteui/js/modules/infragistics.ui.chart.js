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
 * infragistics.chart_piechart.js
 * infragistics.chart_categorychart.js
 * infragistics.chart_financialchart.js
 * infragistics.chart_polarchart.js
 * infragistics.chart_radialchart.js
 * infragistics.chart_rangecategorychart.js
 * infragistics.chart_scatterchart.js
 * infragistics.datasource.js
 * infragistics.dvcommonwidget.js
 */

/*global jQuery */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {

	// igChart definition
	$.widget("ui.igDataChart", {
		options: {
			/* type="string" The channel name to use to sync this chart with other charts. */
			syncChannel: null,
			/* type="bool" Whether the chart should be synchronized vertically */
			synchronizeVertically: true,
			/* type="bool" Whether the chart should be synchronized horizontally */
			syncrhonizeHorizontally: false,
			/* type="object"
			Gets or sets the cross hair point (in world coordinates)
			Either or both of the crosshair point's X and Y may be set to double.NaN, in which
			case the relevant crosshair line is hidden.
			*/
			crosshairPoint: {
				/* type="number" The x coordinate. */
				x: NaN,
				/* type="number" The y coordinate. */
				y: NaN
			},
			/* type="object"
			A rectangle representing the portion of the chart currently in view.
			A rectangle at X=0, Y=0 with a Height and Width of 1 implies the entire plotting area is in view.  A Height and Width of .5 would imply that the view is halfway zoomed in.
			The provided object should have numeric properties called left, top, width and height.
			*/
			windowRect: null,
			/* type="bool"
			Gets or sets the current Chart's horizontal zoomability.
			*/
			horizontalZoomable: false,
			/* type="bool"
			Gets or sets the current Chart's vertical zoomability.
			*/
			verticalZoomable: false,
			/* type="deferred|immediate"
			The response to user panning and zooming: whether to update the view immediately while the user action is happening, or to defer the update to after the user action is complete.  The user action will be an action such as a mouse drag which causes panning and/or zooming to occur.
			deferred type="string" Defer the view update until after the user action is complete.
			immediate type="string" Update the view immediately while the user action is happening.
			*/
			windowResponse: null,
			/* type="number"
			Sets or gets the minimum width that the window rect is allowed to reach before being clamped.
			Decrease this value if you want to allow for further zooming into the viewer.
			If this value is lowered too much it can cause graphical corruption due to floating point arithmetic inaccuracy.
			*/
			windowRectMinWidth: 0,
			/* type="visible|collapsed" The visibility of the OverviewPlusDetailPane.
			visible type="string" The overview pane should be visible.
			collapsed type="string" The overview pane should not be visible.
			*/
			overviewPlusDetailPaneVisibility: "collapsed",
			/* type="visible|collapsed" Gets or sets the current Chart's crosshair visibility override.
			visible type="string" Crosshair should be visible.
			collapsed type="string" Crosshair should not be visible.
			*/
			crosshairVisibility: "collapsed",
			/* type="string"
			Gets or sets the brush used as the background for the current Chart object's plot area.
			*/
			plotAreaBackground: null,
			/* type="none|dragZoom|dragPan" Gets or sets the DefaultInteraction property. The default interaction state defines the chart's response to mouse events.
			none type="string" User gesture will not change the state of the chart.
			dragZoom type="string" User gesture will start a drag rectangle to zoom the chart.
			dragPan type="string" User gesture will start a pan action to move the chart's window.
			*/
			defaultInteraction: "dragZoom",
			/* type="none|alt|control|shift|windows|apple" Gets or sets the current Chart's DragModifier property.
			none type="string" No modifier key is set.
			alt type="string" The modifier is set to alt key.
			control type="string" The modifier is set to control key.
			shift type="string" The modifier is set to shift key.
			*/
			dragModifier: "none",
			/* type="none|alt|control|shift|windows|apple" Gets or sets the current Chart's PanModifier property.
			none type="string" No modifier key is set.
			alt type="string" The modifier is set to alt key.
			control type="string" The modifier is set to control key.
			shift type="string" The modifier is set to shift key.
			*/
			panModifier: "shift",
			/* type="object"
			Gets or sets the preview rectangle.
			The preview rectangle may be set to Rect.Empty, in which case the visible preview
			strokePath is hidden.
			The provided object should have numeric properties called left, top, width and height.
			*/
			previewRect: null,
			/* type="number"
			A number between 0 and 1 determining the position of the horizontal scroll.
			This property is effectively a shortcut to the X position of the WindowRect property.
			*/
			windowPositionHorizontal: 0,
			/* type="number"
			A number between 0 and 1 determining the position of the vertical scroll.
			This property is effectively a shortcut to the Y position of the WindowRect property.
			*/
			windowPositionVertical: 0,
			/* type="number"
			A number between 0 and 1 determining the scale of the horizontal zoom.
			This property is effectively a shortcut to the Width of the WindowRect property.
			*/
			windowScaleHorizontal: 1,
			/* type="number"
			A number between 0 and 1 determining the scale of the vertical zoom.
			This property is effectively a shortcut to the Height of the WindowRect property.
			*/
			windowScaleVertical: 1,
			/* type="object"
			Gets or sets the template to use for circle markers on the chart.
			Defines the marker template used for
			series with a marker type of circle.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			circleMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for triangle markers on the chart.
			Defines the marker template used for
			series with a marker type of triangle.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			triangleMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for pyramid markers on the chart.
			Defines the marker template used for
			series with a marker type of pyramid.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			pyramidMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for square markers on the chart.
			Defines the marker template used for
			series with a marker type of square.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			squareMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for diamond markers on the chart.
			Defines the marker template used for
			series with a marker type of diamond.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			diamondMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for pentagon markers on the chart.
			Defines the marker template used for
			series with a marker type of pentagon.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			pentagonMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for hexagon markers on the chart.
			Defines the marker template used for
			series with a marker type of hexagon.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			hexagonMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for tetragram markers on the chart.
			Defines the marker template used for
			series with a marker type of tetragram.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			tetragramMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for pentragram markers on the chart.
			Defines the marker template used for
			series with a marker type of pentagram.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			pentagramMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for hexagram markers on the chart.
			Defines the marker template used for
			series with a marker type of hexagram.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			hexagramMarkerTemplate: null,
			/* type="number"
			Sets or gets the top margin to use around the chart content in the canvas.
			*/
			topMargin: 0,
			/* type="number"
			Sets or gets the left margin to use around the chart content in the canvas.
			*/
			leftMargin: 0,
			/* type="number"
			Sets or gets the right margin to use around the chart content in the canvas.
			*/
			rightMargin: 0,
			/* type="number"
			Sets or gets the bottom margin to use around the chart content in the canvas.
			*/
			bottomMargin: 0,
			/* type="number"
			Sets or gets the automatic width to add when automatically adding margins to the chart.
			*/
			autoMarginWidth: 20,
			/* type="number"
			Sets or gets the automatic height to add when automatically adding margins to the chart.
			*/
			autoMarginHeight: 0,
			/* type="bool"
			Gets or sets whether to use a square aspect ratio for the chart. This is locked to true for polar and radial charts.
			*/
			isSquare: false,
			/* type="none|beforeSeries|behindSeries" Gets or sets the GridMode property.
			none type="string" No chart grid.
			beforeSeries type="string" Chart grid should be rendered before or in front of the data series.
			behindSeries type="string" Chart grid should be rendered behind or in back of the data series.
			*/
			gridMode: "behindSeries",
			/* type="object"
			Gets or sets the Brushes property.
			The brushes property defines the palette from which automatically assigned series brushes are selected.
			The value provided should be an array of css color strings. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
			*/
			brushes: null,
			/* type="object"
			Gets or sets the MarkerBrushes property.
			The marker brushes property defines the palette from which automatically assigned marker brushes are selected.
			The value provided should be an array of css color strings. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
			*/
			markerBrushes: null,
			/* type="object"
			Gets or sets the Outlines property.
			The outlines property defines the palette from which automatically assigned series outlines are selected.
			The value provided should be an array of css color strings. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
			*/
			outlines: null,
			/* type="object"
			Gets or sets the MarkerOutlines property.
			The marker outlines property defines the palette from which automatically assigned marker outlines are selected.
			The value provided should be an array of css color strings. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
			*/
			markerOutlines: null,
			/* type="string|number|null" The width of the chart. It can be set as a number in pixels, string (px) or percentage (%). */
			width: null,
			/* type="string|number|null" The height of the chart. It can be set as a number in pixels, string (px) or percentage (%). */
			height: null,
			/* type="object"
				To set both dimensions of the chart simultaneously an object with a width and height property can be provided
			*/
			size: null,
			/* type="object" can be any valid data source accepted by $.ig.DataSource, or an instance of an $.ig.DataSource itself */
			dataSource: null,
			/* type="string" Specifies a remote URL accepted by $.ig.DataSource in order to request data from it */
			dataSourceUrl: null,
			/* type="string" Explicitly set data source type (such as "json"). Please refer to the documentation of $.ig.DataSource and its type property. */
			dataSourceType: null,
			/* type="string" see $.ig.DataSource. Specifies the name of the property in which data records are held if the response is wrapped. */
			responseDataKey: null,
			/* type="bool" Set to true in order to disable any interactions with the plot surface. */
			isSurfaceInteractionDisabled: false,
			/* type="bool" Set to true in order to override the default behavior in which series do not animate if an axis range changes */
			animateSeriesWhenAxisRangeChanges: false,
			/* type="string" the title to display for the component. */
			title: null,
			/* type="string" the subtitle to display for the component. */
			subtitle: null,
			/* type="string" the css font property to use for the title. */
			titleTextStyle: null,
			/* type="number" the top margin to use for the title. */
			titleTopMargin: 0,
			/* type="number" the left margin to use for the title. */
			titleLeftMargin: 0,
			/* type="number" the right margin to use for the title. */
			titleRightMargin: 0,
			/* type="number" the bottom margin to use for the title. */
			titleBottomMargin: 0,
			/* type="string" the css font property to use for the title. */
			subtitleTextStyle: null,
			/* type="number" the top margin to use for the subtitle. */
			subtitleTopMargin: 0,
			/* type="number" the left margin to use for the subtitle. */
			subtitleLeftMargin: 0,
			/* type="number" the right margin to use for the subtitle. */
			subtitleRightMargin: 0,
			/* type="number" the bottom margin to use for the subtitle. */
			subtitleBottomMargin: 0,
			/* type="object" the color to use for the title. */
			titleTextColor: "black",
			/* type="object" the color to use for the subtitle. */
			subtitleTextColor: "black",
			/* type="left|center|right" the horizontal alignment to use for the title.
				left type="string" left aligns the title.
				center type="string" center aligns the title.
				right type="string" right aligns the title.
			*/
			titleHorizontalAlignment: "center",
			/* type="left|center|right" the horizontal alignment to use for the subtitle.
				left type="string" left aligns the subtitle.
				center type="string" center aligns the subtitle.
				right type="string" right aligns the subtitle.
			*/
			subtitleHorizontalAlignment: "center",
			/* type="number" the length, in milliseconds of the highlighting transition. */
			highlightingTransitionDuration: 300,
		    /* type="bool" sets whether the series viewer should use cached tiles during zooms rather than the default live content. */
			useTiledZooming: false,
		    /* type="bool" sets whether the series viewer should prefer selecting higher resolution tiles over lower resolution tiles when performing tile zooming. Setting this to true will lower performance but increase quality. */
			preferHigherResolutionTiles: false,
		    /* type="number" Gets or sets the scaling value used by the main canvas rendering context to apply a scale transform to it.*/
            pixelScalingRatio: 1,
		    /* type="number" sets the maximum number of zoom tiles that the series viewer should cache while in tiled zooming mode. */
			zoomTileCacheSize: 30,
		    /* type="auto|computational|colorEncoded|mixed|mixedFavoringComputational" Gets or sets which type of hit testing the series should use.
				auto type="string" automatically decide the appropriate hit test mode for the series.
				computational type="string" use a computational based approach to determine which series has been hit. This uses loose bounding boxes, in some cases, and can range in time complexity between O(1) and O(log n) to find a hit. This decreases frame render time compared to color encoded.
				colorEncoded type="string" use a color encoded off screen buffer for hit testing. This can have extremely rare false positives where the wrong series is hit (this is due to our inability to disable anti-aliasing in our color buffer), but should always be O(1) time for determining a hit series. This increases frame render time, however. Consider using this if hit testing time is degrading performance.
                mixed type="string" let each series decide which hit testing mode to use individually based on their own hit test mode.
                mixedFavoringComputational type="string" let each series decide which hit testing mode to use individually based on their own hit test mode, but evaluate all computational hits before evaluating any color encoding hits.
				*/
			contentHitTestMode: "auto",
			/* type="object" can be any valid options accepted by $.ig.ChartLegend, or an instance of an $.ig.ChartLegend itself. */
			legend: {
				/* type="string" the name of the element to turn into a legend. */
				element: null,
				/* type="item|legend" Type of the legend.
					item type="string" Specify the legend as item legend. It displays a legend item for each pie in the igPieChart control.
					legend type="string" Specify the legend as legend. It is supported by all types of series in the igDataChart control.
				*/
				type: "legend",
				/* type="string|number|null" The width of the legend.
					string The widget width can be set in pixels (px) and percentage (%).
					number The widget width can be set as a number.
					null type="object" will stretch to fit data, if no other widths are defined.
				*/
				width: null,
				/* type="string|number|null" The height of the legend.
					string The widget height can be set in pixels (px) and percentage (%).
					number The widget height can be set as a number.
					null will stretch vertically to fit data, no other height are defined.
				*/
				height: null
			},
			/* type="array" an array of axis objects */
			axes: [{
				/* type="numericX|numericY|categoryX|categoryDateTimeX|categoryY|categoryAngle|numericAngle|numericRadius" Type of the axis.
					numericX type="string" Specify the axis as numeric X axis. Useful for displaying scatter, category and financial price series.
					numericY type="string" Specify the axis as numeric Y axis.Useful for displaying scatter, category and financial price series.
					categoryX type="string" Specify the axis as category X axis. Useful for displaying scatter, category and financial price series.
					categoryDateTimeX type="string" Specify the axis as category datetime X axis. Useful for displaying category and financial price series with date based data.
					categoryY type="string" Specify the axis as category Y axis. Useful for displaying scatter, category and financial price series.
					categoryAngle type="string" Specify the axis as category angle axis. Useful for displaying polar and radial categories.
					numericAngle type="string" Specify the axis as numeric angle axis. Useful for displaying polar and radial series.
					numericRadius type="string" Specify the axis as numeric radius axis. Useful for displaying polar and radial series.
				*/
				type: null,
				/* type="string" the unique identifier of the axis. */
				name: null,
				/* type="object" can be any valid data source accepted by $.ig.DataSource, or an instance of an $.ig.DataSource itself */
				dataSource: null,
				/* type="string" Specifies a remote URL accepted by $.ig.DataSource in order to request data from it */
				dataSourceUrl: null,
				/* type="string" Explicitly set data source type (such as "json"). Please refer to the documentation of $.ig.DataSource and its type property. */
				dataSourceType: null,
				/* type="string" see $.ig.DataSource. Specifies the name of the property in which data records are held if the response is wrapped. */
				responseDataKey: null,
				/* type="bool" Set to true in order to have an existing axis removed from the chart, by name */
				remove: false,
				/* type="outsideTop|outsideBottom|outsideLeft|outsideRight|insideTop|insideBottom|insideLeft|insideRight" Specifies the location to display the axis labels for this axis.
					outsideTop type="string" The labels should have an outside top position.
					outsideBottom type="string" The labels should have an outside bottom position.
					outsideLeft type="string" The labels should have an outside left position.
					outsideRight type="string" The labels should have an outside right position.
					insideTop type="string" The labels should have an inside top position.
					insideBottom type="string" The labels should have an inside bottom position.
					insideLeft type="string" The labels should have an inside left position.
					insideRight type="string" The labels should have an inside right position. */
				labelLocation: null,
				/* type="visible|collapsed" Specifies whether the labels are visible.
					visible type="string" The labels should be visisble for this axis.
					collapsed type="string" The labels should not be visible for this axis. */
				labelVisibility: "visible",
				/* type="number" Specifies the extent of the area dedicated to the labels for this axis. If unspecified, this value is auto-calculated. */
				labelExtent: null,
				/*type="number" Specifies the angle that labels on the axis should be rotated */
				labelAngle: 0,
				/*type="string" Overrides the style of the text used for the axis labels. */
				labelTextStyle: null,
				/*type="string" Overrides the color of the text used for the axis labels. */
				labelTextColor: null,
				/* type="object"
				Sets or gets a function which takes an object that produces a formatted label for displaying in the chart.
				*/
				formatLabel: null,
				/* type="string"
				Gets or sets the Stroke property.
				*/
				stroke: null,
				/* type="number"
				Gets or sets the StrokeThickness property.
				*/
				strokeThickness: 1,
				/* type="string"
				Gets or sets the Strip property.
				*/
				strip: null,
				/* type="string"
				Gets or sets the MajorStroke property.
				*/
				majorStroke: null,
				/* type="number"
				Gets or sets the MajorStrokeThickness property.
				*/
				majorStrokeThickness: 1,
				/* type="string"
				Gets or sets the MinorStroke property.
				*/
				minorStroke: null,
				/* type="number"
				Gets or sets the MinorStrokeThickness property.
				*/
				minorStrokeThickness: 1,
				/* type="bool"
				Gets or sets the IsInverted property.
				*/
				isInverted: false,
				/* type="string"
				Gets or sets the CrossingAxis property.
				*/
				crossingAxis: null,
				/* type="object"
				Gets or sets the CrossingValue property.
				*/
				crossingValue: null,
				/* type="object"
				Gets or sets the coercion methods to use when loading data from data sources.
				Should be specified before setting any member paths, if being used. Setting it later
				will not cause data to be reimported into the chart.
				*/
				coercionMethods: null,
				/* type="object"
				Gets or sets the axis label format string.
				*/
				label: null,
				/* type="number"
				Gets or sets the amount of space between adjacent categories for the current axis object.
				The gap is silently clamped to the range [0, 1] when used.
				*/
				gap: 0,
				/* type="number"
				Gets or sets the amount of overlap between adjacent categories for the current axis object.
				The overlap is silently clamped to the range [-1, 1] when used.
				*/
				overlap: 0,
				/* type="number"
				Indicates the angle in degress that the chart's 0th angle should be offset.
				*/
				startAngleOffset: 0,
				/* type="number"
				Gets or sets the frequency of displayed labels.
				The set value is a factor that determines which labels will be hidden. For example, an interval of 2 will display every other label.
				*/
				interval: 0,
				/* type="continuous|discrete" Gets or sets the axis display type. Continuous display type divides the axis into even intervals, where labels will not necessarily be aligned with data points. Discrete display type will not use a constant interval, but will align each label with its data point.
				continuous type="string" Points occur at even intervals, even where data is not present at a given point.
				discrete type="string" Points occur when data is present, possibly at uneven intervals.
				*/
				displayType: null,
			    /* type="bool"
				Gets or sets whether the data assigned to the date time axis should be considered pre-sorted by date/time.
				*/
                isDataPreSorted: false,
				/* type="number"
				Gets or sets the axis MinimumValue.
				*/
				minimumValue: 0,
				/* type="number"
				Gets or sets the axis MaximumValue.
				*/
				maximumValue: 0,
				/* type="string"
				Gets or sets the DateTime mapping property for the CategoryDateTimeXAxis.
				*/
				dateTimeMemberPath: null,
				/* type="number"
				Gets or sets the ReferenceValue property.
				*/
				referenceValue: 0,
				/* type="bool"
				Gets or sets the IsLogarithmic property.
				*/
				isLogarithmic: false,
				/* type="number"
				Gets or sets the LogarithmBase property.
				*/
				logarithmBase: 10,
				/* type="number"
				Defines the percentage of the maximum radius extent to use as the maximum radius. Should be
				a value between 0.0 and 1.0.
				*/
				radiusExtentScale: 0.75,
				/* type="number"
				Defines the percentage of the maximum radius extent to leave blank at the center of the chart. Should be
				a value between 0.0 and 1.0.
				*/
				innerRadiusExtentScale: 0,
				/* type="string" the title to display for the component. */
				title: null,
				/* type="string" the css font property to use for the title. */
				titleTextStyle: null,
				/* type="number" the margin to display around the title of the axis. */
				titleMargin: 0,
				/* type="left|center|right" the horizontal alignment to use for the title.
				left type="string" left aligns the title.
				center type="string" center aligns the title.
				right type="string" right aligns the title.
				*/
				titleHorizontalAlignment: "center",
				/* type="left|center|right" the vertical alignment to use for the title.
				top type="string" top aligns the title.
				center type="string" center aligns the title.
				bottom type="string" bottom aligns the title.
				*/
				titleVerticalAlignment:"center",
				/* type="auto|left|right|top|bottom" the position to use for the title.
				auto type="string" the title is positioned automatically.
				left type="string" the title is positioned on the left of the labels, if applicable.
				right type="string" the title is positioned on the right of the labels, if applicable.
				top type="string" the title is positioned on the top of the labels, if applicable.
				bottom type="string" the title is positioned on the bottom of the labels, if applicable.
				*/
				titlePosition: "auto",
				/* type="number" the top margin to use for the title. */
				titleTopMargin: 0,
				/* type="number" the left margin to use for the title. */
				titleLeftMargin: 0,
				/* type="number" the right margin to use for the title. */
				titleRightMargin: 0,
				/* type="number" the bottom margin to use for the title. */
				titleBottomMargin: 0,
				/* type="left|center|right" the horizontal alignment to use for the labels. Only applicable to vertical axes.
				left type="string" left aligns the labels.
				center type="string" center aligns the labels.
				right type="string" right aligns the labels.
				*/
				labelHorizontalAlignment: "right",
				/* type="top|center|bottom" the vertical alignment to use for the labels. Only applicable to horizontal axes.
				top type="string" top aligns the labels.
				center type="string" center aligns the labels.
				bottom type="string" bottom aligns the labels.
				*/
				labelVerticalAlignment: "top",
				/* type="number" the margin to use for the labels. */
				labelMargin: 0,
				/* type="number" the top margin to use for the labels. */
				labelTopMargin: 0,
				/* type="number" the left margin to use for the labels. */
				labelLeftMargin: 0,
				/* type="number" the right margin to use for the labels. */
				labelRightMargin: 0,
				/* type="number" the bottom margin to use for the labels. */
				labelBottomMargin: 0,
				/* type="bool" sets whether or not to show the first label on the axis. */
				showFirstLabel: true,
				/* type="number" the angle to use for the axis title. */
				titleAngle: 0,
				/* type="number" the length of the tickmarks to display for this axis. */
				tickLength: 0,
				/* type="number" the stroke thickness to use for the tickmarks. */
				tickStrokeThickness: 0.5,
				/* type="object" the color to use for the tickmarks. */
				tickStroke: "black",
				/* type="bool" Gets or sets whether the cateogory axis should use clustering display mode even if no series are present that would force clustering mode. */
				useClusteringMode: false,
			    /* type="bool" Gets or sets the whether to use more advanced heuristics when determining the initial number of labels to render, before resolving collisions, etc. */
				useEnhancedIntervalManagement: false,
			    /* type="number" Gets or sets the mininum desired characters to be displayed for horizontal axes when using advanced label heuristics. -1 will attempt to adjust the interval to precisely fit the horizontal labels. */
			    enhancedIntervalMinimumCharacters: 5
			}],
			/* type="array" an array of series objects */
			series: [{
				/* type="area|bar|column|line|rangeArea|rangeColumn|splineArea|spline|stepArea|stepLine|waterfall|financial|typicalPriceIndicator|polarArea|polarLine|polarScatter|radialColumn|radialLine|radialPie|scatter|scatterLine|bubble|absoluteVolumeOscillatorIndicator|averageTrueRangeIndicator|accumulationDistributionIndicator|averageDirectionalIndexIndicator" Type of the series.
					area type="string" Specify the series as Area series.
					bar type="string" Specify the series as Bar series.
					column type="string" Specify the series as Column series.
					line type="string" Specify the series as Line series.
					rangeArea type="string" Specify the series as Range Area series.
					rangeColumn type="string" Specify the series as Range Column series.
					splineArea type="string" Specify the series as Spline Area series.
					spline type="string" Specify the series as Spline series.
					stepArea type="string" Specify the series as Step Area series.
					stepLine type="string" Specify the series as Step Line series.
					waterfall type="string" Specify the series as Waterfall series.
					financial type="string" Specify the series as Financial series.
					typicalPriceIndicator type="string" Specify the series as Typical Price Indicator series.
					point type="string" Specify the series as Point series.
					polarSplineArea type="string" Specify the series as Polar Spline Area series.
					polarSpline type="string" Specify the series as Polar Spline series.
					polarArea type="string" Specify the series as Polar Area series.
					polarLine type="string" Specify the series as Polar Line series.
					polarScatter type="string" Specify the series as Polar Scatter series.
					radialColumn type="string" Specify the series as Radial Column series.
					radialLine type="string" Specify the series as Radial Line series.
					radialPie type="string" Specify the series as Radial Pie series.
					radialArea type="string" Specify the series as Radial Area series.
					scatter type="string" Specify the series as Scatter series.
					scatterLine type="string" Specify the series as Scatter Line series.
					scatterSpline type="string" Specify the series as Scatter Spline series.
					bubble type="string" Specify the series as Bubble series.
					absoluteVolumeOscillatorIndicator type="string" Specify the series as Absolute Volume Oscillator Indicator series.
					averageTrueRangeIndicator type="string" Specify the series as Average True Range Indicator series.
					accumulationDistributionIndicator type="string" Specify the series as Accumulation Distribution Indicator series
					averageDirectionalIndexIndicator type="string" Specify the series as Average Directional Index Indicator series.
					bollingerBandWidthIndicator type="string" Specify the series as Bollinger Band Width Indicator series.
					chaikinOscillatorIndicator type="string" Specify the series as Chaikin Oscillator Indicator series.
					chaikinVolatilityIndicator type="string" Specify the series as Chaikin Volitility Indicator series.
					commodityChannelIndexIndicator type="string" Specify the series as Commodity Channel Index Indicator series.
					detrendedPriceOscillatorIndicator type="string" Specify the series as Detrended Price Oscillator Indicator series.
					easeOfMovementIndicator type="string" Specify the series as Ease Of Movement Indicator series.
					fastStochasticOscillatorIndicator type="string" Specify the series as Fast Stochastic Oscillator Indicator series.
					forceIndexIndicator type="string" Specify the series as Force Index Indicator series.
					fullStochasticOscillatorIndicator type="string" Specify the series as Full Stochastic Oscillator Indicator series.
					marketFacilitationIndexIndicator type="string" Specify the series as Market Facilitation Index Indicator series.
					massIndexIndicator type="string" Specify the series as Mass Index Indicator series.
					medianPriceIndicator type="string" Specify the series as Median Price Indicator series.
					moneyFlowIndexIndicator type="string" Specify the series as Money Flow Index Indicator series.
					movingAverageConvergenceDivergenceIndicator type="string" Specify the series as Moving Average Convergence Divergence Indicator series.
					negativeVolumeIndexIndicator type="string" Specify the series as Negative Volume Index Indicator series.
					onBalanceVolumeIndicator type="string" Specify the series as On Balance Volume Indicator series.
					percentagePriceOscillatorIndicator type="string" Specify the series as Percentage Price Oscillator Indicator series.
					percentageVolumeOscillatorIndicator type="string" Specify the series as Percentage Volume Oscillator Indicator series.
					positiveVolumeIndexIndicator type="string" Specify the series as Positive Volume Index Indicator series.
					priceVolumeTrendIndictor type="string" Specify the series as Price Volume Trend Indictor series.
					rateOfChangeAndMomentumIndicator type="string" Specify the series as Rate Of Change And Momentum Indicator series.
					relativeStrengthIndexIndicator type="string" Specify the series as Relative Strength Index Indicator series.
					slowStochasticOscillatorIndicator type="string" Specify the series as Slow Stochastic Oscillator Indicator series.
					standardDeviationIndicator type="string" Specify the series as Standard Deviation Indicator series.
					stochRSIIndicator type="string" Specify the series as Stoch RSI Indicator series.
					trixIndicator type="string" Specify the series as Trix Indicator series.
					ultimateOscillatorIndicator type="string" Specify the series as Ultimate Oscillator Indicator series.
					weightedCloseIndicator type="string" Specify the series as Weighted Close Indicator series.
					williamsPercentRIndicator type="string" Specify the series as Williams Percent R Indicator series.
					bollingerBandsOverlay type="string" Specify the series as Bollinger Bands Overlay series.
					priceChannelOverlay type="string" Specify the series as Price Channel Overlay series.
					customIndicator type="string" Specify the series as Custom Indicator series.
					stackedBar type="string" Specify the series as Stacked Bar series.
					stacked100Bar type="string" Specify the series as Stacked 100 Bar series.
					stackedArea type="string" Specify the series as Stacked Area series.
					stacked100Area type="string" Specify the series as Stacked 100 Area series.
					stackedColumn type="string" Specify the series as Stacked Column series.
					stacked100Column type="string" Specify the series as Stacked 100 Column series.
					stackedLine type="string" Specify the series as Stacked Line series.
					stacked100Line type="string" Specify the series as Stacked 100 Line series.
					stackedSpline type="string" Specify the series as Stacked Spline series.
					stacked100Spline type="string" Specify the series as Stacked 100 Spline series.
					stackedSplineArea type="string" Specify the series as Stacked Spline Area series.
					stacked100SplineArea type="string" Specify the series as Stacked 100 Spline Area series.
					crosshairLayer type="string" Specify the series as a crosshair layer.
					categoryHighlightLayer type="string" Specify the series as a category highlight layer.
					categoryItemHighlightLayer type="string" Specify the series as a category item highlight layer.
					itemToolTipLayer type="string" Specify the series as an item tooltip layer.
					categoryToolTipLayer type="string" Specify the series as a category tooltip layer.
				*/
				type: null,
				/* type="string" the unique identifier of the series. */
				name: null,
				/* type="object" can be any valid data source accepted by $.ig.DataSource, or an instance of an $.ig.DataSource itself */
				dataSource: null,
				/* type="string" Specifies a remote URL accepted by $.ig.DataSource in order to request data from it */
				dataSourceUrl: null,
				/* type="string" Explicitly set data source type (such as "json"). Please refer to the documentation of $.ig.DataSource and its type property. */
				dataSourceType: null,
				/* type="string" see $.ig.DataSource. Specifies the name of the property in which data records are held if the response is wrapped. */
				responseDataKey: null,
				/* type="bool" Set to true in order to have an existing series removed from the chart, by name */
				remove: false,
				/* type="bool" Whether the chart should render a tooltip. */
				showTooltip: false,
				/* type="string" The name of template or the template itself that chart tooltip will use to render. */
				tooltipTemplate: null,
				/* type="object" can be any valid options accepted by $.ig.ChartLegend, or an instance of an $.ig.ChartLegend itself. */
				legend: {
					/* type="string" the name of the element to turn into a legend. */
					element: null,
					/* type="item|legend" Type of the legend.
						item type="string" Specify the legend as item legend. It displays a legend item for each pie in the igPieChart control.
						legend type="string" Specify the legend as legend. It is supported by all types of series in the igDataChart control.
					*/
					type: "legend",
					/* type="string|number|null" The width of the legend.
						string The widget width can be set in pixels (px) and percentage (%).
						number The widget width can be set as a number
						null type="object" will stretch to fit data, if no other widths are defined
					*/
					width: null,
					/* type="string|number|null" The height of the legend.
						string The widget height can be set in pixels (px) and percentage (%).
						number The widget height can be set as a number
						null will stretch vertically to fit data, no other height are defined
					*/
					height: null
				},
				/* type="visible|collapsed" Gets or sets the legend item visibility for the current series object.
				visible type="string" The legend item should be visible.
				collapsed type="string" The legend item should not be visible.
				*/
				legendItemVisibility: "visible",
				/* type="object"
				Gets or sets the LegendItemBadgeTemplate property.
				The legend item badge is created according to the LegendItemBadgeTemplate on-demand by
				the series object itself.
				The provided object should have properties called render and optionally measure.
				These are functions which will be called that will be called to handle the user specified custom rendering.
				measure will be passed an object that looks like this:
				{
					context: [either a DOM element or a CanvasContext2D depending on the particular template scenario],
					width: [if value is present, specifies the available width, user may set to desired width for content],
					height: [if value is present, specifies the available height, user may set to desired height for content],
					isConstant: [user should set to true if desired with and height will always be the same for this template],
					data: [if present, represents the contextual data for this template]
				}
				render will be passed an object that looks like this:
				{
					context: [either a DOM element or a CanvasContext2D depending on the particular template scenario],
					xPosition: [if present, specifies the x position at which to render the content],
					yPosition: [if present, specifies the y position at which to render the content],
					availableWidth: [if present, specifies the available width in which to render the content],
					availableHeight: [if present, specifies the available height in which to render the content],
					data: [if present, specifies the data that is in context for this content],
					isHitTestRender: [if true, indicates that this is a special render pass for hit testing, in which case the brushes from the data should be used]
				}
				*/
				legendItemBadgeTemplate: null,
				/* type="object"
				Gets or sets the LegendItemTemplate property.
				The legend item control content is created according to the LegendItemTemplate on-demand by
				the series object itself.
				The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
				*/
				legendItemTemplate: null,
				/* type="object"
				Gets or sets the DiscreteLegendItemTemplate property.
				The legend item control content is created according to the DiscreteLegendItemTemplate on-demand by
				the series object itself.
				The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
				*/
				discreteLegendItemTemplate: null,
				/* type="number"
				Gets or sets the duration of the current series's morph.
				*/
				transitionDuration: 0,
				/* type="object"
				Provides an easing function to use for the transitions. This should either be a well known name of an easing function (The only currently supported name is cubic), or it should be a function that takes a number and returns the output with the function applied. */
				transitionEasingFunction: null,
				/* type="number"
				Gets or sets the current series object's rendering resolution.
				*/
				resolution: 1,
				/* type="string"
				Gets or sets the Title property.
				The legend item control is created according to the Title on-demand by
				the series object itself.
				*/
				title: null,
				/* type="string"
				Gets or sets the brush to use for the series.
				*/
				brush: null,
				/* type="string"
				Gets or sets the brush to use for the outline of the series.
				Some series types, such as LineSeries, do not display outlines.  Therefore, this property does not affect some charts.
				*/
				outline: null,
				/* type="number"
				Gets or sets the width of the current series object's line thickness.
				*/
				thickness: 0,
				/* type="object"
				Gets or gets the coercion methods to use when loading data from data sources.
				Should be specified before setting any member paths, if being used. Setting it later
				will not cause data to be reimported into the chart.
				*/
				coercionMethods: null,
				/* type="unset|none|automatic|circle|triangle|pyramid|square|diamond|pentagon|hexagon|tetragram|pentagram|hexagram" Gets or sets the marker type for the current series object. If the MarkerTemplate property is set, the setting of the MarkerType property will be ignored.
				unset type="string" Marker hasn't been set.
				none type="string" No markerItems.
				automatic type="string" Automatic marker shape.
				circle type="string" Circle marker shape.
				triangle type="string" Flat-top triangle marker shape.
				pyramid type="string" Flat-base triangle marker shape.
				square type="string" Square marker shape.
				diamond type="string" Diamond marker shape.
				pentagon type="string" Pentagon marker shape.
				hexagon type="string" Hexagon marker shape.
				tetragram type="string" Four-pointed star marker shape.
				pentagram type="string" Five-pointed star marker shape.
				hexagram type="string" Six-pointed star marker shape.
				*/
				markerType: "none",
				/* type="object"
				Gets or sets the MarkerTemplate for the current series object.
				The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
				*/
				markerTemplate: null,
				/* type="string"
				Gets or sets the brush that specifies how the current series object's marker interiors are painted.
				*/
				markerBrush: null,
				/* type="string"
				Gets or sets the brush that specifies how the current series object's marker outlines are painted.
				*/
				markerOutline: null,
				/* type="string"
				Gets or sets the effective x-axis for the current object.
				*/
				xAxis: null,
				/* type="string"
				Gets or sets the effective y-axis for the current object.
				*/
				yAxis: null,
				/* type="string"
				Gets or sets the value mapping property for the current series object.
				*/
				xMemberPath: null,
				/* type="string"
				Gets or sets the value mapping property for the current series object.
				*/
				yMemberPath: null,
				/* type="none|linearFit|quadraticFit|cubicFit|quarticFit|quinticFit|logarithmicFit|exponentialFit|powerLawFit|simpleAverage|exponentialAverage|modifiedAverage|cumulativeAverage|weightedAverage" Gets or sets the trend type for the current scatter series.
				none type="string" No trendline should display.
				linearFit type="string" Linear fit.
				quadraticFit type="string" Quadratic polynomial fit.
				cubicFit type="string" Cubic polynomial fit.
				quarticFit type="string" Quartic polynomial fit.
				quinticFit type="string" Quintic polynomial fit.
				logarithmicFit type="string" Logarithmic fit.
				exponentialFit type="string" Exponential fit.
				powerLawFit type="string" Powerlaw fit.
				simpleAverage type="string" Simple moving average.
				exponentialAverage type="string" Exponential moving average.
				modifiedAverage type="string" Modified moving average.
				cumulativeAverage type="string" Cumulative moving average.
				weightedAverage type="string" Weighted moving average.
				*/
				trendLineType: "none",
				/* type="string"
				Gets or sets the brush to use to draw the trend line.
				*/
				trendLineBrush: null,
				/* type="number"
				Gets or sets the thickness of the current scatter series object's trend line.
				*/
				trendLineThickness: 1.5,
				/* type="number"
				Gets or sets the moving average period for the current scatter series object.
				The typical, and initial, value for trend line period is 7.
				*/
				trendLinePeriod: 7,
				/* type="number"
				Gets or sets the Z-Index of the trend line.  Values greater than 1000 will result in the trend line being rendered in front of the series data.
				*/
				trendLineZIndex: 1001,
				/* type="number"
				Gets or sets the maximum number of markerItems displayed by the current series.
				If more than the specified number of markerItems are visible, the series will automatically
				choose a representative set.
				*/
				maximumMarkers: 400,
				/* type="linearInterpolate|dontPlot" Determines how unknown values will be plotted on the chart. Null and Double.NaN are two examples of unknown values.
				linearInterpolate type="string" Plot the unknown value as the midpoint between surrounding known values using linear interpolation.
				dontPlot type="string" Do not plot the unknown value on the chart.
				*/
				unknownValuePlotting: "dontPlot",
				/* type="string"
				Gets or sets the radius mapping property for the current series object.
				*/
				radiusMemberPath: null,
				/* type="object"
				Gets or sets the radius size scale for the bubbles.
				*/
				radiusScale: null,
				/* type="string"
				Gets or sets the Label mapping property for the current series object.
				*/
				labelMemberPath: null,
				/* type="string"
				Gets or sets the fill mapping property for the current series object.
				*/
				fillMemberPath: null,
				/* type="object"
				Gets or sets the brush scale for the marker brush.
				*/
				fillScale: null,
				/* type="string"
				Gets the effective angle axis for the current series object.
				*/
				angleAxis: null,
				/* type="string"
				Gets the effective value axis for the current series object.
				*/
				valueAxis: null,
				/* type="bool"
				Gets or sets whether to clip the series to the bounds.
				Setting this to true can effect performance.
				*/
				clipSeriesToBounds: null,
				/* type="string"
				Gets or sets the item path that provides the values for the current series.
				*/
				valueMemberPath: null,
				/* type="number"
				Gets or sets the x-radius of the ellipse that is used to round the corners of the column.
				*/
				radiusX: 2.0,
				/* type="number"
				Gets or sets the y-radius of the ellipse that is used to round the corners of the column.
				*/
				radiusY: 2.0,
				/* type="number"
				Gets or sets the x-radius of the ellipse that is used to round the corners of the column.
				*/
				angleMemberPath: null,
				/* type="string"
				Gets the effective radius axis for the current series object.
				*/
				radiusAxis: null,
				/* type="bool"
				Gets or sets whether Cartesian Interpolation should be used rather than Archimedian
				spiral based interpolation.
				*/
				useCartesianInterpolation: true,
				/* type="string"
				Gets or sets the brush to use for negative portions of the series.
				*/
				negativeBrush: null,
				/* type="natural|clamped" Gets or sets the type of spline to be rendered.
				natural type="string" Calculates the spline using a natural spline calculation formula.
				clamped type="string" Calculated the spline using a clamped spline calculation formula.
				*/
				splineType: "natural",
				/* type="string"
				Gets or sets the value mapping property for the current series object.
				*/
				lowMemberPath: null,
				/* type="string"
				Gets or sets the value mapping property for the current series object.
				*/
				highMemberPath: null,
				/* type="string"
				Gets or sets the open mapping property for the current series object.
				*/
				openMemberPath: null,
				/* type="string"
				Gets or sets the close mapping property for the current series object.
				*/
				closeMemberPath: null,
				/* type="string"
				Gets or sets the volume mapping property for the current series object.
				*/
				volumeMemberPath: null,
				/* type="candlestick|OHLC" Gets or sets the display for the current FinancialIndicator object.
				candlestick type="string" Displays prices as a Japanese Candlestick.
				OHLC type="string" Displays prices as an OHLC bar.
				*/
				displayType: "candlestick",
				/* type="number"
				Gets or sets the number of values to hide at the beginning of the indicator.
				*/
				ignoreFirst: 0,
				/* type="number"
				Gets or sets the moving average period for the current AverageDirectionalIndexIndicator object.
				The typical, and initial, value for AverageDirectionalIndexIndicator periods is 14.
				*/
				period: 0,
				/* type="number"
				Gets or sets the short moving average period for the current AbsoluteVolumeOscillatorIndicator object.
				The typical, and initial, value for short AVO periods is 10.
				*/
				shortPeriod: 0,
				/* type="number"
				Gets or sets the short moving average period for the current AbsoluteVolumeOscillatorIndicator object.
				The typical, and initial, value for long AVO periods is 30.
				*/
				longPeriod: 0,
				/* type="none|omit|fade|omitAndShift|fadeAndShift" Gets or sets the MarkerCollisionAvoidance
				none type="string" No collision avoidance is attempted.
				omit type="string" Markers that collide will be omitted.
				fade type="string" Markers that collide will be faded in opacity.
				omitAndShift type="string" Markers that collide may be shifted or omitted.
				omitAndFade type="string" Markers that collide may be shifted or faded.
				*/
				markerCollisionAvoidance: "none",
				/* type="bool"
				Sets or Gets whether to increase marker fidelity for extreme data shapes that have lots of Y variation over short X intervals.
				*/
				useHighMarkerFidelity: false,
				/* type="bool"
				Gets or sets the whether to use use brute force mode.
				*/
				useBruteForce: false,
				/* type="bool"
				Gets or sets the whether to progressively load the data into the chart.
				*/
				progressiveLoad: true,
				/* type="bool"
				Gets or sets the whether the chart reacts to mouse move events.
				*/
				mouseOverEnabled: false,
				/* type="bool"
				Gets or sets the whether to use squares when halting a render traversal rather than the shape of the coalesced area.
				*/
				useSquareCutoffStyle: false,
				/* type="number"
				Gets or sets the density value that maps to the minimum heat color.
				*/
				heatMinimum: 0,
				/* type="number"
				Gets or sets the value that maps to the maximum heat color.
				*/
				heatMaximum: 50,
				/* type="object"
				Gets or sets the minimum heat color for the density scale.
				*/
				heatMinimumColor: "black",
				/* type="object"
				Gets or sets the maximum heat color for the density scale.
				*/
				heatMaximumColor: "red",
				/* type="array"
				Gets or sets the series for stacked charts. It should contain array of series objects. Each item in array should represent a series with type="stackedFragment" and it may have most options supported by top-level series object, such as xAxis, yAxis, valueMemberPath, etc.
				*/
				series: null,
				/* type="bool"
				Gets or sets whether drop shadow should be enabled for this series.
				*/
				isDropShadowEnabled: false,
				/* type="bool"
				Gets or sets whether drop shadow is applied to the whole series visual or to each of the individual shapes forming the series.
				*/
				useSingleShadow: true,
				/* type="object"
				Gets or sets the color to use for the drop shadow.
				*/
				shadowColor: "rgba(95,95,95,0.5)",
				/* type="number"
				Gets or sets the blur amount to use for the drop shadow.
				*/
				shadowBlur: 10,
				/* type="number"
				Gets or sets the x offset amount to use for the drop shadow.
				*/
				shadowOffsetX: 5,
				/* type="number"
				Gets or sets the y offset amount to use for the drop shadow.
				*/
				shadowOffsetY: 5,
				/* type="bool"
				Gets or sets if the series should play a transition in animation when the data source is assigned. Note: Transitions are not currently supported for stacked series.
				*/
				isTransitionInEnabled: false,
				/* type="auto|normal|valueScaled|indexScaled|random" Gets or sets the speed to transition in the series data points.
				auto type="string" A speed type is automatically selected.
				normal type="string" All speeds are normal, data points will arrive at the same time.
				valueScaled type="string" Data points will arrive later if their value is further from the start point.
				indexScaled type="string" Data points will arrive later if their index is further from the axis origin.
				random type="string" Data points will arrive at random times.
				*/
				transitionInSpeedType: "auto",
                /* type="auto|fromZero|sweepFromLeft|sweepFromRight|sweepFromTop|sweepFromBottom|sweepFromCenter|accordionFromLeft|accordionFromRight|accordionFromTop|accordionFromBottom|expand|sweepFromCategoryAxisMinimum|sweepFromCategoryAxisMaximum|sweepFromValueAxisMinimum|sweepFromValueAxisMaximum|accordionFromCategoryAxisMinimum|accordionFromCategoryAxisMaximum|accordionFromValueAxisMinimum|accordionFromValueAxisMaximum" Gets or sets the method to transition in the series. Note: Transitions are not currently supported for stacked series.
				auto type="string" Series transitions in an automatically chosen way.
				fromZero type="string" Series transitions in from the reference value of the value axis.
				sweepFromLeft type="string" Series sweeps in from the left.
				sweepFromRight type="string" Series sweeps in from the right.
				sweepFromTop type="string" Series sweeps in from the top.
				sweepFromBottom type="string" Series sweeps in from the bottom.
				sweepFromCenter type="string" Series sweeps in from the center.
				accordionFromLeft type="string" Series accordions in from the left.
				accordionFromRight type="string" Series accordions in from the right.
				accordionFromTop type="string" Series accordions in from the top.
				accordionFromBottom type="string" Series accordions in from the bottom.
				expand type="string" Series expands from the value midpoints.
				sweepFromCategoryAxisMinimum type="string" Series sweeps in from the category axis minimum.
				sweepFromCategoryAxisMaximum type="string" Series sweeps in from the category axis maximum.
				sweepFromValueAxisMinimum type="string" Series sweeps in from the value axis minimum.
				sweepFromValueAxisMaximum type="string" Series sweeps in from the value axis maximum.
				accordionFromCategoryAxisMinimum type="string" Series accordions in from the category axis minimum.
				accordionFromCategoryAxisMaximum type="string" Series accordions in from the category axis maximum.
				accordionFromValueAxisMinimum type="string" Series accordions in from the value axis minimum.
				accordionFromValueAxisMaximum type="string" Series accordions in from the value axis maximum.
				*/
				transitionInMode: "auto",
				/* type="number" Gets or sets the duration of the current series's transition in morph in milliseconds.
				*/
				transitionInDuration: 500,
				/* type="number" Gets or sets the corner radius to use for the series, if applicable.
				*/
				radius: 2,
				/* type="number" Gets or sets the opacity modifier to apply to the area fill shape of the series, if applicable.
				*/
				areaFillOpacity: 1.0,
				/* type="bool" Gets or sets whether the series should expect that its data source members need to be called as functions to get their values.
				*/
				expectFunctions: false,
				/* type="bool" Gets or sets whether the hover layer should use interpolation to position itself relative the closest values.
				*/
				useInterpolation: false,
				/* type="bool" Gets or sets whether the hover layer should skip unknown values when trying to find the closest values.
				*/
				skipUnknownValues: false,
				/* type="bool" Gets or sets whether the vertical crosshair portion of the layer should be visible.
				*/
				verticalLineVisibility: "visible",
				/* type="bool" Gets or sets whether the horizontal crosshair portion of the layer should be visible.
				*/
				horizontalLineVisibility: "visible",
				/* type="string" Gets or sets the name of the target series for the layer, if desired. Setting the target series will scope the layer to target just that series.
				*/
				targetSeries: null,
				/* type="string" Gets or sets the name of the target axis for the layer, if desired. Setting the target axis will scope the layer to target just that axis.
				*/
				targetAxis: null,
				/* type="bool" Gets or sets whether a custom category style is allowed. Setting this to true will case the assigningCategoryStyle event to get fired, if provided.
				*/
				isCustomCategoryStyleAllowed: false,
				/* type="bool" Gets or sets whether a custom category marker style is allowed. Setting this to true will case the assigningCategoryMarkerStyle event to get fired, if provided.
				*/
				isCustomCategoryMarkerStyleAllowed: false,
				/* type="bool" Gets or sets whether highlighting should be enabled for the series, if supported.
				*/
				isHighlightingEnabled: false,
				/* type="number" Gets or sets the width to use for the highlight region if highlighting items in a grid aligned series (line, spline, etc), with a banded shape.
				*/
				bandHighlightWidth: 10.0,
				/* type="auto|marker|shape" Gets or sets which type of highlight shape to use when highlighting items.
				auto type="string" use an automatic highlight type for this series.
				marker type="string" use a marker highlight type for this series.
				shape type="string" use a shape highlight type for this series.
				*/
				highlightType: "auto",
				/* type="auto|outsideStart|insideStart|insideEnd|outsideEnd" Gets or sets the Position to apply to the tooltip containers.
				auto type="string" use an automatic position for the category tooltips.
				outsideStart type="string" position the category tooltip at the outside start of the value axis.
				insideStart type="string" position the category tooltip at the inside start of the value axis.
				insideEnd type="string" position the category tooltip at the inside end of the value axis.
				outsideEnd type="string" position the category tooltip at the outside end of the value axis.
				*/
				tooltipPosition: "auto",
				/* type="object" Sets the position a cursor position to use instead of the current mouse cursor position for displaying the annotations in this layer.
				Should be an object with an x and a y property in world coordinates (ranging from 0 to 1)
				*/
				cursorPosition: null,
				/* type="bool" Sets if the presence of this layer should disable the default crosshair behavior of the chart, if present.
				*/
				isDefaultCrosshairDisabled: true,
				/* type="bool" Sets if the current layer should take up a brush/ordering index in the series collection to derive its color automatically.
				*/
				useIndex: false,
				/* type="bool" Sets if the current layer should have an entry in the legend of the chart. By default annotation layers are not present in the legend.
				*/
				useLegend: false,
			    /* type="bool" Sets whether the order of the fragment series should be reversed in the legend. Note: Reversing the legend order is only supported on stacked series.
                */
                reverseLegendOrder: false,
				/* type="auto|computational|colorEncoded" Gets or sets which type of hit testing the series should use.
				auto type="string" automatically decide the appropriate hit test mode for the series.
				computational type="string" use a computational based approach to determine whether the series has been hit. This uses loose bounding boxes, in some cases, and can range in time complexity between O(1) and O(log n) to find a hit. This decreases frame render time compared to color encoded.
				colorEncoded type="string" use a color encoded off screen buffer for hit testing. This can have extremely rare false positives where the wrong series is hit (this is due to our inability to disable anti-aliasing in our color buffer), but should always be O(1) time for determining a hit series. This increases frame render time, however. Consider using this if hit testing time is degrading performance.
				*/
				hitTestMode: "auto"
			}],/*BUILDREMOVESTHIS*/
			/* type="string" The swatch used to style this widget */
			theme: "c"
		},
		css: {
			/* Classes applied on a div element. */
			chart: "ui-corner-all ui-widget-content ui-chart-container",
			/* Classes applied on a div element, shown when the chart is opened in a non HTML5 compatible browser. */
			unsupportedBrowserClass:
            "ui-html5-non-html5-supported-message ui-helper-clearfix  ui-html5-non-html5",
			/* Classes applied to the tooltip div element. */
			tooltip: "ui-chart-tooltip ui-widget-content ui-corner-all",
			/* Class applying background-color and border-color to the n-th series in the chart. As many palettes can be defined as neccessary. */
			seriesPalettes: "ui-chart-palette-n",
			/* Class applying background-image to the fill of the n-th series in the chart. Only gradient colors are accepted. As many palettes can be defined as neccessary. */
			seriesFillPalettes: "ui-chart-fill-palette-n",
			/* Class applying background-image to the outline of the n-th series in the chart. Only gradient colors are accepted. As many palettes can be defined as neccessary. */
			seriesOutlinePalettes: "ui-chart-outline-palette-n",
			/* Class applying opacity value to the fill shape of all area-like series. */
			areaFillOpacity: "ui-chart-area-fill-opacity",
			/* Class applying border-color to all of the chart axis lines. */
			axis: "ui-chart-axis",
			/* Class applying border-color to the chart axes stroke lines. Transparent is treated as auto meaning that the chart decides which lines to be displayed based on the series types used in it. For horizontal category series (Column, Line, Area), only horizontal lines are displayed. For vertical category series (Bar), only vertical lines are displayed. For scatter, polar and radial series all gridlines are displayed. */
			axisStroke: "ui-chart-axis-stroke",
			/* Class applying border-color to the chart axes major lines. Transparent is treated as auto meaning that the chart decides which lines to be displayed based on the series types used in it. For horizontal category series (Column, Line, Area), only horizontal lines are displayed. For vertical category series (Bar), only vertical lines are displayed. For scatter, polar and radial series all gridlines are displayed. */
			axisMajorStroke: "ui-chart-axis-major-line",
			/* When the visibility CSS property is set to visible for this class, all grid lines in the chart will be snapped to the nearest pixel. */
			alignedGridLines: "ui-chart-aligned-gridlines",
			/* Class used for specifying width/height for the tick marks of the category axes. */
			categoryAxisTick: "ui-chart-category-axis-tick",
			/* Class applying font color, margin, and vertical alignment to the labels of horizontal axes. */
			horizontalAxisLabels: "ui-horizontal-axis-labels",
			/* Class applying font color, margin, and horizontal (text) alignment to the labels of vertical axes. */
			verticalAxisLabels: "ui-vertical-axis-labels",
			/* Class applying font color to the labels of angular axes. */
			angularAxisLabels: "ui-angular-axis-labels",
			/* Class applying font color, margin, and vertical alignment to the labels of radial axes. */
			radialAxisLabels: "ui-radial-axis-labels",
			/* Class applying font, color, and margin to the chart title. */
			title: "ui-chart-title",
			/* Class applying font, color, and margin to the chart subtitle. */
			subtitle: "ui-chart-subtitle",
			/* Class applying font, color, and margin to the title of horizontal chart axes. */
			horizontalAxisTitle: "ui-chart-horizontal-axis-title",
			/* Class applying font, color, and margin to the title of vertical chart axes. */
			verticalAxisTitle: "ui-chart-vertical-axis-title",
			/* Class setting all styling options to chart legends. */
			legendItemsList: "ui-chart-legend-items-list",
			/* Class setting all styling options to legend item text. */
			legendItemsText: "ui-chart-legend-item-text",
			/* Class setting all styling options to legend item icons. */
			legendItemsBadge: "ui-chart-legend-item-badge"
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
			/* cancel="false"
			Occurs when the cursors are moved over a series in this chart.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesCursorMouseMove: null,
			/* cancel="false"
			Occurs when the left mouse button is pressed while the mouse pointer is over an element of this chart.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseLeftButtonDown: null,
			/* cancel="false"
			Occurs when the left mouse button is released while the mouse pointer is over an element of this chart.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseLeftButtonUp: null,
			/* cancel="false"
			Occurs when the left mouse pointer moves while over an element of this chart.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseMove: null,
			/* cancel="false"
			Occurs when the left mouse pointer enters an element of this chart.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseEnter: null,
			/* cancel="false"
			Occurs when the left mouse pointer leaves an element of this chart.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseLeave: null,
			/* cancel="false"
			Occurs just after the current Chart's window rectangle is changed.
			Function takes arguments evt and ui.
			Use ui.chart to get reference to chart object.
			Use ui.newHeight to get new height value.
			Use ui.newLeft to get new left value.
			Use ui.newTop to get new top value.
			Use ui.newWidth to get new top value.
			Use ui.oldHeight to get old height value.
			Use ui.oldLeft to get old left value.
			Use ui.oldTop to get old top value.
			Use ui.oldWidth to get old top value.
			*/
			windowRectChanged: null,
			/* cancel="false"
			Occurs just after the current Chart's grid area rectangle is changed.
			The grid area may change as the result of the Chart being resized, or
			of an axis being added or changing size, possibly in another Chart.
			Function takes arguments evt and ui.
			Use ui.chart to get reference to chart object.
			Use ui.newHeight to get new height value.
			Use ui.newLeft to get new left value.
			Use ui.newTop to get new top value.
			Use ui.newWidth to get new top value.
			Use ui.oldHeight to get old height value.
			Use ui.oldLeft to get old left value.
			Use ui.oldTop to get old top value.
			Use ui.oldWidth to get old top value.
			*/
			gridAreaRectChanged: null,
			/* cancel="false"
			Raised when the chart's processing for an update has completed.
			Function takes arguments evt and ui.
			Use ui.chart to get reference to chart object.
			*/
			refreshCompleted: null,
			/* cancel="false"
			Event fired when the range of and axis on the chart changes.
			Function takes arguments evt and ui.
			Use ui.axis to get reference to current chart axis object.
			Use ui.chart to get reference to chart object.
			Use ui.newMaximumValue to get new maximum value.
			Use ui.newMinimumValue to get new minimum value.
			Use ui.oldMaximumValue to get old maximum value.
			Use ui.oldMinimumValue to get old minimum value.
			*/
			axisRangeChanged: null,
			/* cancel="false"
			Handle this event in order to specify which columns the Typical price calculation is based on.
			Function takes arguments evt and ui.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.count to get the number of positions that should be calculated from the start.
			Use ui.position to get the beginning position that should be calculated from.
			Use ui.supportingCalculations to get the supporting calculations to use in the calculation.
			Use ui.dataSource to get the data to use for the calculation.
			Use ui.basedOn to specify which columns changing will invalidate the series and cause it to be recalculated.
			*/
			typicalBasedOn: null,
			/* cancel="false"
			Event fired when the progressive loading state of the series has changed.
			Function takes arguments evt and ui.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.currentStatus to get current status.
			*/
			progressiveLoadStatusChanged: null,
			/* cancel="false"
			Event fired to allow you to override the style of items in a category or financial series. Only fires if you set allowCustomCategoryStyle to true for a series.
			Function takes arguments evt and ui.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.startIndex to get the start index for the current items.
			Use ui.endIndex to get the end index for the current items.
			Use ui.hasDateRange to tell if you should use the startDate and endDate to know the current items instead of startIndex/endIndex.
			Use ui.startDate if ui.hasDateRange is true.
			Use ui.endDate if ui.hasDateRange is true.
			Use ui.getItems to get all the items associated with the event (only if necessary).
			Use ui.fill to get or set the fill to use for the current item.
			Use ui.stroke to get or set the stroke to use for the current item.
			Use ui.opacity to get or set the opacity to use for the current item.
			Use ui.highlightingHandled to set if the default highlighting behavior should not run, given that you are handling it in this event.
			Use ui.maxAllSeriesHighlightingProgress to get the maximum highlighted progress across all series.
			Use ui.sumAllSeriesHighlightingProgress to get the sum of highlighting progtess across all series.
			Use ui.highlightingInfo.progress to tell what the highlighting progress is for the current item, if highlightingInfo is not null.
			Use ui.highlightingInfo.state to tell whether the current item is currently highlighting in or out, is static, if highlightingInfo is not null.
			*/
			assigningCategoryStyle: null,
			/* cancel="false"
			Event fired to allow you to override the style of markers for the items in a category or financial series. Only fires if you set allowCustomCategoryMarkerStyle to true for a series.
			Function takes arguments evt and ui.
			Use ui.chart to get reference to chart object.
			Use ui.series to get reference to current series object.
			Use ui.startIndex to get the start index for the current items.
			Use ui.endIndex to get the end index for the current items.
			Use ui.hasDateRange to tell if you should use the startDate and endDate to know the current items instead of startIndex/endIndex.
			Use ui.startDate if ui.hasDateRange is true.
			Use ui.endDate if ui.hasDateRange is true.
			Use ui.getItems to get all the items associated with the event (only if necessary).
			Use ui.fill to get or set the fill to use for the current item.
			Use ui.stroke to get or set the stroke to use for the current item.
			Use ui.opacity to get or set the opacity to use for the current item.
			Use ui.highlightingHandled to set if the default highlighting behavior should not run, given that you are handling it in this event.
			Use ui.maxAllSeriesHighlightingProgress to get the maximum highlighted progress across all series.
			Use ui.sumAllSeriesHighlightingProgress to get the sum of highlighting progtess across all series.
			Use ui.highlightingInfo.progress to tell what the highlighting progress is for the current item, if highlightingInfo is not null.
			Use ui.highlightingInfo.state to tell whether the current item is currently highlighting in or out, is static, if highlightingInfo is not null.
			*/
			assigningCategoryMarkerStyle: null
		},
		_createWidget: function (options, element) {
			this._fixCss();
			this.dvWidget = new $.ig.dvCommonWidget(this);
			this.dvWidget._createWidget(options, element, this);
		},
		_init: function () {

			//Once the widget instance is created, all other calls to the widget name where the first
			//parameter is not a string will call the _init() method; if options are passed, the /
			// setOption()  method will be called before the _init() method.
		},
		_create: function () {
			this._fixCss();
			this.dvWidget._create();
		},
		_fixCss: function () {
			if (this.css.chart.indexOf("{0}") > -1) {
				this.css.chart = this.css.chart.replace("{0}", this.options.theme);
				this.css.tooltip = this.css.tooltip.replace("{0}", this.options.theme);
			}
		},
		_setOption: function (key, value) {
			this.dvWidget._setOption(key, value);
		},
		option: function () {
		    return this.dvWidget.option.apply(this.dvWidget, arguments);
		},
		widget: function () {
			/* Returns the element holding the chart. */
			return this.element;
		},
		id: function () {
			/*  returns the ID of parent element holding the chart.
				returnType="string"
			*/
			return this.element[ 0 ].id;
		},
		exportImage: function (width, height) {
			/* Exports the chart to a PNG image.
				paramType="string|number" optional="true" The width of the image.
				paramType="string|number" optional="true" The height of the image.
				returnType="object" Returns a IMG DOM element. */
			return this.dvWidget._getImage(width, height, this);
		},
		destroy: function () {
			/* Destroys the widget. */
		    if (this._chart) {
		        if (this._chart.actualSyncLink) {
		            var oldLink = this._chart.actualSyncLink();
		            if (oldLink) {
		                $.ig.SyncLinkManager.prototype.instance().releaseLink(oldLink);
		            }
		            this._chart.actualSyncLink(new $.ig.SyncLink());
		        }

				this._chart.destroy();
				this._chart = null;
			}
			this.dvWidget._destroy(this);

			$.Widget.prototype.destroy.apply(this, arguments);
		},
		styleUpdated: function () {
			/* Notify the chart that styles it draws colors from may have been updated.
				returnType="object" Returns a reference to this igChart. */
			this._chart.styleUpdated();
			return this;
		},
		resetZoom: function () {
			/* Resets the zoom level of the chart to default.
				returnType="object" Returns a reference to this igChart. */
			this._chart.resetZoom();
			return this;
		},
		addItem: function (item, targetName) {
			/* adds a new item to the data source and notifies the chart.
				paramType="object" The item that we want to add to the data source.
				paramType="string" The name of the series or axis bound to the data source. This is required only when the data is bound to series or axis. If the data is bound to dataSource of igDataChart, the second parameter should not be set.
			*/
			var dataSourceId = targetName || this._containerSourceID;
			if (this.dataSources[ dataSourceId ]) {
				this.dataSources[ dataSourceId ].addRow(null, item, true);
			}
		},
		insertItem: function (item, index, targetName) {
			/* inserts a new item to the data source and notifies the chart.
				paramType="object" the new item that we want to insert in the data source.
				paramType="number" The index in the data source where the new item will be inserted.
				paramType="string" The name of the series or axis bound to the data source.
			*/
			var dataSourceId = targetName || this._containerSourceID;
			if (this.dataSources[ dataSourceId ]) {
				this.dataSources[ dataSourceId ].insertRow(null, item, index, true);
			}
		},
		removeItem: function (index, targetName) {
			/* deletes an item from the data source and notifies the chart.
				paramType="number" The index in the data source from where the item will be been removed.
				paramType="string" The name of the series or axis bound to the data source. This is required only when the data is bound to series or axis. If the data is bound to dataSource of igDataChart, the second parameter should not be set.
			*/
			var dataSourceId = targetName || this._containerSourceID;
			if (this.dataSources[ dataSourceId ]) {
				this.dataSources[ dataSourceId ].deleteRow(index, true);
			}
		},
		setItem: function (index, item, targetName) {
			/* updates an item in the data source and notifies the chart.
				paramType="number" The index of the item in the data source that we want to change.
				paramType="object" The new item object that will be set in the data source.
				paramType="string" The name of the series or axis bound to the data source.
			*/
			var dataSourceId = targetName || this._containerSourceID;
			if (this.dataSources[ dataSourceId ]) {
				this.dataSources[ dataSourceId ].updateRow(index, item, true);
			}
		},
		notifySetItem: function (dataSource, index, newItem, oldItem) {
			/* Notifies the chart that an item has been set in an associated data source.
				paramType="object" optional="false" The data source in which the change happened.
				paramType="number" optional="false" The index in the items source that has been changed.
				paramType="object" optional="false" the new item that has been set in the collection.
				paramType="object" optional="false" the old item that has been overwritten in the collection.
				returnType="object" Returns a reference to this chart. */
			this._chart.notifySetItem(dataSource, index, oldItem, newItem);
			return this;
		},
		notifyClearItems: function (dataSource) {
			/* Notifies the chart that the items have been cleared from an associated data source.
				It's not necessary to notify more than one target of a change if they share the same items source.
				paramType="object" optional="false" The data source in which the change happened.
				returnType="object" Returns a reference to this chart. */
			this._chart.notifyClearItems(dataSource);
			return this;
		},
		notifyInsertItem: function (dataSource, index, newItem) {
			/* Notifies the target axis or series that an item has been inserted at the specified index in its data source.
				It's not necessary to notify more than one target of a change if they share the same items source.
				paramType="object" optional="false" The data source in which the change happened.
				paramType="number" optional="false" The index in the items source where the new item has been inserted.
				paramType="object" optional="false" the new item that has been set in the collection.
				returnType="object" Returns a reference to this chart. */
			this._chart.notifyInsertItem(dataSource, index, newItem);
			return this;
		},
		notifyRemoveItem: function (dataSource, index, oldItem) {
			/* Notifies the target axis or series that an item has been removed from the specified index in its data source.
				It's not necessary to notify more than one target of a change if they share the same items source.
				paramType="object" optional="false" The data source in which the change happened.
				paramType="number" optional="false" The index in the items source from where the old item has been removed.
				paramType="object" optional="false" the old item that has been removed from the collection.
				returnType="object" Returns a reference to this chart. */
			this._chart.notifyRemoveItem(dataSource, index, oldItem);
			return this;
		},
		scrollIntoView: function (targetName, item) {
			/* Notifies the target axis or series that it should scroll the requested data item into view.
				paramType="string" optional="false" The name of the axis or series notify.
				paramType="object" optional="false" The data item to bring into view, if possible.
				returnType="object" Returns a reference to this chart. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.scrollIntoView) {
				target.scrollIntoView(item);
			}
			return this;
		},
		scaleValue: function (targetName, unscaledValue) {
			/* Notifies the target axis that it should scale the requested value into chart space from axis space.
				For example you can use this method if you want to find where value 50 of the x axis stands scaled to chart's width.
				paramType="string" optional="false" The name of the axis to notify.
				paramType="number" optional="false" The value in axis space to translate into chart space.
				returnType="number" Returns the scaled value. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.scaleValue) {
				return target.scaleValue(unscaledValue);
			}
			return 0.0;
		},
		unscaleValue: function (targetName, scaledValue) {
			/* Notifies the target axis that it should unscale the requested value into axis space from chart space.
				For example you can use this method if you want to find what is the value of x axis unscaled from 0 width of the chart.
				paramType="string" optional="false" The name of the axis to notify.
				paramType="number" optional="false" The value in chart space to translate into axis space.
				returnType="number" Returns the unscaled value. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.unscaleValue) {
				return target.unscaleValue(scaledValue);
			}
			return 0.0;
		},
		resetCachedEnhancedInterval: function (targetName) {
		    /* For the target axis, if using enhanced interval management and precise interval fitting, this will reset the cached maximum label width, and recalculate using the current labels.
				paramType="string" optional="false" The name of the axis to notify.
				returnType="object" Returns a reference to this chart. */
		    var target = this.dvWidget._getNotifyTarget(targetName);
		    if (target && target.resetCachedEnhancedInterval) {
		        return target.resetCachedEnhancedInterval();
		    }
		    return this;
		},
		notifyVisualPropertiesChanged: function (targetName) {
			/* Notifies the target series that something that affects its visual properties has changed and the visual output needs a repaint.
				paramType="string" optional="false" The name of the series to notify.
				returnType="object" Returns a reference to this chart. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.notifyVisualPropertiesChanged) {
				return target.notifyVisualPropertiesChanged();
			}
			return this;
		},
		flush: function () {
			/* Forces any pending deferred work to render on the chart before continuing */
			this._chart.flush();
		},
		exportVisualData: function () {
			/* exports visual data from the chart to aid in unit testing */
			return this._chart.exportVisualData();
		},
		getActualMinimumValue: function (targetName) {
			/* Gets the actual minimum value of the target numeric or date time axis
			paramType="string" optional="false" The name of the axis from which to get the minimum value. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.actualMinimumValue) {
				return target.actualMinimumValue();
			}
			return 0.0;
		},
		getActualMaximumValue: function (targetName) {
			/* Gets the actual maximum value of the target numeric or date time axis
			paramType="string" optional="false" The name of the axis from which to get the maximum value. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.actualMaximumValue) {
				return target.actualMaximumValue();
			}
			return 0.0;
		},
		print: function () {
			/* Creates a print preview page with the chart, hiding all other elements on the page. */
			this.dvWidget._print();
		},
		renderSeries: function (targetName, animate) {
			/* Indicates that a series should render, even though no option has been modified that would normally cause it to refresh.
				paramType="string" The name of the series to render.
				paramType="bool" Whether the change should be animated, if possible. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.renderSeries) {
				return target.renderSeries(animate);
			}
			return this;
		},
		getItemIndex: function (targetName, worldPoint) {
			/* Gets the item item index associated with the specified world position.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			returnType="number" Item index or -1 if no item is assocated with the specified world position. */
            var target = this.dvWidget._getNotifyTarget(targetName),
            p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
			if (target && target.getItemIndex) {
				return target.getItemIndex(p);
			}
		},
		getItem: function (targetName, worldPoint) {
			/* Gets the item that is the best match for the specified world coordinates.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			returnType="object" The item that is the best match. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
            p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
			if (target && target.getItem) {
				return target.getItem(p);
			}
		},
		getItemSpan: function (targetName) {
		    /* For a category plotted series, gets the current width of the items within the categories. This only returns a value if the items have some form of width (e.g. columns, bars, etc.) otherwise 0 is returned.
			paramType="string" The name of the series to target.
			returnType="number" The width of the items for this series within the categories. */
		    var target = this.dvWidget._getNotifyTarget(targetName);
		    if (target && target.getItemSpan) {
		        return target.getItemSpan();
		    }
		    return 0;
		},
		_flattenPoint: function(point) {
		    var retPoint = {
		        __x: point.__x,
		        __y: point.__y,
		        x: point.__x,
		        y: point.__y
		    };

		    return retPoint;
		},
		_flattenRect: function (rect) {
		    var retRect = {
		        left: rect.left(),
		        top: rect.top(),
		        width: rect.width(),
		        height: rect.height()
		    };

		    return retRect;
		},
		getSeriesValue: function (targetName, worldPoint,  useInterpolation, skipUnknowns) {
			/* If possible, will return the best available main value of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="number" The value of the series at the specified world coordinate, or double.NaN if unavailable. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
            p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
			if (target && target.getSeriesValue) {
				return target.getSeriesValue(p, useInterpolation, skipUnknowns);
			}
		},
		getSeriesValueBoundingBox: function (targetName, worldPoint) {
		    /* If possible, will return the best available value bounding box within the series that has the best value match for the world position provided.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			returnType="object" The bounding box in question, or empty if no valid position is available. This will be an object literal with top, left, width, and height properties. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
            p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
		    if (target && target.getSeriesValueBoundingBox) {
				return this.__flattenRect(
					target.getSeriesValueBoundingBox(p));
		    }
		},
		getSeriesValueFineGrainedBoundingBoxes: function (targetName, worldPoint) {
		    /* If possible, will return the best available value fine grained bounding boxes within the series that have the best value match for the world position provided.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			returnType="object" The fine grained bounding boxes in question, or empty if no valid position is available. This will be an object literal with top, left, width, and height properties. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
            p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
		    if (target && target.getSeriesValueFineGrainedBoundingBoxes) {
				return this.__flattenRect(
					target.getSeriesValueFineGrainedBoundingBoxes(p));
		    }
		},
		getSeriesValuePosition: function (targetName, worldPoint,  useInterpolation, skipUnknowns) {
			/* If possible, will return the best available main value position of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="object" The position of the best available main value for the series in series pixel space. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
			if (target && target.getSeriesValuePosition) {
			    return this._flattenPoint(target.getSeriesValuePosition(p, useInterpolation, skipUnknowns));
			}
		},
		getSeriesValuePositionFromSeriesPixel:
            function (targetName, seriesPoint, useInterpolation, skipUnknowns) {
			/* If possible, will return the best available main value position of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The series pixel position (in the form {x: [number], y: [number]} that represents a position within the pixel space of the series.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="object" The position (in the form {x: [number], y: [number]} ) of the best available main value for the series in series pixel space. */
                var target = this.dvWidget._getNotifyTarget(targetName),
                    p = $.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y);
			if (target && target.getSeriesValuePositionFromSeriesPixel) {
			    return this._flattenPoint(target.getSeriesValuePositionFromSeriesPixel(
                    p, useInterpolation, skipUnknowns));
			}
		},
		getSeriesValueFromSeriesPixel:
            function (targetName, seriesPoint, useInterpolation, skipUnknowns) {
			/* If possible, will return the best available main value of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The series pixel position (in the form {x: [number], y: [number]} ) that represents a position in the pixel space of the series.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="number" The value of the series at the specified world coordinate, or double.NaN if unavailable. */
                var target = this.dvWidget._getNotifyTarget(targetName),
                    p = $.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y);
			if (target && target.getSeriesValueFromSeriesPixel) {
				return target.getSeriesValueFromSeriesPixel(p, useInterpolation, skipUnknowns);
			}
		},
		getSeriesValueBoundingBoxFromSeriesPixel: function (targetName, seriesPoint) {
		    /* If possible, will return the best available value bounding box within the series that has the best value match for the given series pixel coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The series pixel position (in the form {x: [number], y: [number]} ) that represents a position in the pixel space of the series.
			returnType="object" The bounding box in question, or empty if no valid position is available. This will be an object literal with top, left, width, and height properties. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
            p = $.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y);
		    if (target && target.getSeriesValueBoundingBoxFromSeriesPixel) {
				return this.__flattenRect(
					target.getSeriesValueBoundingBoxFromSeriesPixel(p));
		    }
		},
		getSeriesValueFineGrainedBoundingBoxesFromSeriesPixel: function (targetName, worldPoint) {
		    /* If possible, will return the best available value fine grained bounding boxes within the series that have the best value match for series pixel position provided.
			paramType="string" The name of the series to target.
			paramType="object" The series pixel position (in the form {x: [number], y: [number]} ) that represents a position in the pixel space of the series.
			returnType="object" The fine grained bounding boxes in question, or empty if no valid position is available. This will be an object literal with top, left, width, and height properties. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
            p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
		    if (target && target.getSeriesValueFineGrainedBoundingBoxesFromSeriesPixel) {
				return this.__flattenRect(
					target.getSeriesValueFineGrainedBoundingBoxesFromSeriesPixel(
					p));
		    }
		},
		getSeriesHighValue: function (targetName, worldPoint,  useInterpolation, skipUnknowns) {
			/* If possible, will return the best available high value of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="number" The value of the series at the specified world coordinate, or double.NaN if unavailable. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
			if (target && target.getSeriesHighValue) {
				return target.getSeriesHighValue(p, useInterpolation, skipUnknowns);
			}
		},
		getSeriesHighValuePosition: function (targetName, worldPoint,  useInterpolation, skipUnknowns) {
			/* If possible, will return the best available high value position of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="object" The position of the best available high value for the series in series pixel space. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
			if (target && target.getSeriesHighValuePosition) {
			    return this._flattenPoint(target.getSeriesHighValuePosition(
                    p, useInterpolation, skipUnknowns));
			}
		},
		getSeriesHighValuePositionFromSeriesPixel:
            function (targetName, seriesPoint, useInterpolation, skipUnknowns) {
			/* If possible, will return the best available high value position of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The series pixel position (in the form {x: [number], y: [number]} that represents a position within the pixel space of the series.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="object" The position (in the form {x: [number], y: [number]} ) of the best available high value for the series in series pixel space. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y);
			if (target && target.getSeriesHighValuePositionFromSeriesPixel) {
			    return this._flattenPoint(target.getSeriesHighValuePositionFromSeriesPixel(
                    p, useInterpolation, skipUnknowns));
			}
		},
		getSeriesHighValueFromSeriesPixel:
            function (targetName, seriesPoint, useInterpolation, skipUnknowns) {
			/* If possible, will return the best available high value of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The series pixel position (in the form {x: [number], y: [number]} ) that represents a position in the pixel space of the series.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="number" The value of the series at the specified world coordinate, or double.NaN if unavailable. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y);
			if (target && target.getSeriesHighValueFromSeriesPixel) {
				return target.getSeriesHighValueFromSeriesPixel(p, useInterpolation, skipUnknowns);
			}
		},
		getSeriesLowValue: function (targetName, worldPoint,  useInterpolation, skipUnknowns) {
			/* If possible, will return the best available low value of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="number" The value of the series at the specified world coordinate, or double.NaN if unavailable. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
			if (target && target.getSeriesLowValue) {
				return target.getSeriesLowValue(p, useInterpolation, skipUnknowns);
			}
		},
		getSeriesLowValuePosition: function (targetName, worldPoint,  useInterpolation, skipUnknowns) {
			/* If possible, will return the best available low value position of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="object" The position of the best available low value for the series in series pixel space. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y);
			if (target && target.getSeriesLowValuePosition) {
			    return this._flattenPoint(target.getSeriesLowValuePosition(
                    p, useInterpolation, skipUnknowns));
			}
		},
		getSeriesLowValuePositionFromSeriesPixel:
            function (targetName, seriesPoint, useInterpolation, skipUnknowns) {
			/* If possible, will return the best available low value position of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The series pixel position (in the form {x: [number], y: [number]} that represents a position within the pixel space of the series.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="object" The position (in the form {x: [number], y: [number]} ) of the best available low value for the series in series pixel space. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y);
			if (target && target.getSeriesLowValuePositionFromSeriesPixel) {
			    return this._flattenPoint(target.getSeriesLowValuePositionFromSeriesPixel(
                    p, useInterpolation, skipUnknowns));
			}
		},
		getSeriesLowValueFromSeriesPixel:
            function (targetName, seriesPoint, useInterpolation, skipUnknowns) {
			/* If possible, will return the best available low value of the series for a given world coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The series pixel position (in the form {x: [number], y: [number]} ) that represents a position in the pixel space of the series.
			paramType="bool" If true, interpolation should be used to get in-between values, rather than only the actual values in the data set.
			paramType="bool" If true, unknown values should be skipped.
			returnType="number" The value of the series at the specified world coordinate, or double.NaN if unavailable. */
                var target = this.dvWidget._getNotifyTarget(targetName),
                    p = $.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y);
			if (target && target.getSeriesLowValueFromSeriesPixel) {
				return target.getSeriesLowValueFromSeriesPixel(p, useInterpolation, skipUnknowns);
			}
		},
		getItemIndexFromSeriesPixel: function (targetName, seriesPoint) {
			/* Gets the item item index associated with the specified series pixel coordinate.
			paramType="string" The name of the series to target.
			paramType="object" The world position (in the form {x: [number from 0 to 1], y: [number from 0 to 1]} ) that represents a position in the space of the axes.
			returnType="number" Item index or -1 if no item is assocated with the specified world position. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y);
			if (target && target.getItemIndexFromSeriesPixel) {
				return target.getItemIndexFromSeriesPixel(p);
			}
		},
		getItemFromSeriesPixel: function (targetName, seriesPoint) {
			/* Gets the item that is the best match for the specified world coordinates.
			paramType="string" The name of the series to target.
			paramType="object" The series pixel position (in the form {x: [number], y: [number]} ) that represents a position in the pixel space of the series.
			returnType="object" The item that is the best match. */
		    var target = this.dvWidget._getNotifyTarget(targetName),
                p = $.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y);
			if (target && target.getItemFromSeriesPixel) {
				return target.getItemFromSeriesPixel(p);
			}
		},
		getSeriesOffsetValue: function (targetName) {
			/* Gets the category offset for a series, if applicable.
			paramType="string" The name of the series to target.
			returnType="number" The pixel category offset. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.getOffsetValue) {
				return target.getOffsetValue();
			}
		},
		getSeriesCategoryWidth: function (targetName) {
			/* Gets the category width for a series, if applicable.
			paramType="string" The name of the series to target.
			returnType="number" The pixel width of the categories. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.getCategoryWidth) {
				return target.getCategoryWidth();
			}
		},
		replayTransitionIn: function (targetName) {
			/* Replays the transition in animation for a series, if applicable.
			paramType="string" The name of the series to target.
			returnType="object" Returns a reference to this chart. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.replayTransitionIn) {
				target.replayTransitionIn();
			}
			return this;
		},
		simulateHover: function (targetName, seriesPoint) {
			/* Simulates a hover interaction over a given point in the viewport of a series.
			paramType="string" The name of the series to target.
			paramType="object" The point at which to hover. Should have an x property with type number and a y property with type number.
			returnType="object" Returns a reference to this chart. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.simulateHover) {
			    target.simulateHover($.ig.APIFactory.prototype.createPoint(seriesPoint.x, seriesPoint.y));
			}
			return this;
		},
		moveCursorPoint: function (targetName, worldPoint) {
			/* Moves the cursor point of the target annotation layer to the desired world coordinates.
			paramType="string" The name of the series to target.
			paramType="object" The point to which to move the cursor. Should have an x property with type number and a y property with type number.
			returnType="object" Returns a reference to this chart. */
			var target = this.dvWidget._getNotifyTarget(targetName);
			if (target && target.moveCursorPoint) {
			    target.moveCursorPoint($.ig.APIFactory.prototype.createPoint(worldPoint.x, worldPoint.y));
			}
			return this;
		},
		startTiledZoomingIfNecessary: function () {
		    /* Manually starts a tiled zoom if one isn't already running.
			*/
		    this._chart.startTiledZoomingIfNecessary();
		    return this;
		},
		endTiledZoomingIfRunning: function () {
		    /* Manually ends a tiled zoom if one is running.
			*/
		    this._chart.endTiledZoomingIfRunning();
		    return this;
		},
		clearTileZoomCache: function () {
		    /* Clears the tile zoom tile cache so that new tiles will be generated. Only applies if the viewer is using a tile based zoom.
			*/
		    this._chart.clearTileZoomCache();
		    return this;
		}
	});
	$.extend($.ui.igDataChart, { version: "16.1.20161.2145" });

	$.widget("ui.igPieChart", {
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
			/* type="object" can be any valid data source accepted by $.ig.DataSource, or an instance of an $.ig.DataSource itself */
			dataSource: null,
			/* type="string" Specifies a remote URL accepted by $.ig.DataSource in order to request data from it */
			dataSourceUrl: null,
			/* type="string" Explicitly set data source type (such as "json"). Please refer to the documentation of $.ig.DataSource and its type property. */
			dataSourceType: null,
			/* type="string" see $.ig.DataSource. Specifies the name of the property in which data records are held if the response is wrapped. */
			responseDataKey: null,
			/* type="string"
			Gets or Sets the property name that contains the values.
			*/
			valueMemberPath: null,
			/* type="string"
			Gets or sets the property name that contains the labels.
			*/
			labelMemberPath: null,
			/* type="none|center|insideEnd|outsideEnd|bestFit"
			Gets or sets the position of chart labels.
			none type="string" No labels will be displayed.
			center type="string" Labels will be displayed in the center.
			insideEnd type="string" Labels will be displayed inside and by the edge of the container.
			outsideEnd type="string" Labels will be displayed outside the container.
			bestFit type="string" Labels will automatically decide their location.
			*/
			labelsPosition: "center",
			/* type="visible|collapsed"
			Gets or sets whether the leader lines are visible.
			visible type="string"
			collapsed type="string"
			*/
			leaderLineVisibility: "visible",
			/* type="straight|arc|spline"
			Gets or sets what type of leader lines will be used for the outside end labels.
			straight type="string"
			arc type="string"
			spline type="string"
			*/
			leaderLineType: "straight",
			/* type="number"
			Gets or sets the margin between a label and the end of its leader line.
			*/
			leaderLineMargin: 6.0,
			/* type="number"
			Gets or sets the threshold value that determines if slices are grouped into the Others slice.
			*/
			othersCategoryThreshold: 3,
			/* type="function"
			Gets or sets the function to use to transform a pie slice data context into a label for the slice. Function takes one argument of type object.
			Use context.item to get the item associated with the slice, if any.
			Use context.actualItemBrush to get the brush used to paint the slice.
			Use context.outline to get the outline brush used to paint the slice.
			Use context.itemLabel to get the label object that would be used for the slice.
			Use context.percentValue to see the percentage value that is associated with the slice.
			Use context.isOthersSlice to tell if the associated slice is the others slice.
			Should return a string value that should be used for the label.
			*/
			formatLabel: null,
			/* type="object"
			Gets or sets a style object that can be used to override the style settings on the others category slice.
			*/
			othersCategoryStyle: null,
			/* type="number|percent" Gets or sets whether to use numeric or percent-based threshold value.
			number type="string" Data value is compared directly to the value of OthersCategoryThreshold.
			percent type="string" Data value is compared to OthersCategoryThreshold as a percentage of the total.
			*/
			othersCategoryType: "percent",
			/* type="string"
			Gets or sets the label of the Others slice.
			*/
			othersCategoryText: "Others",
			/* type="number"
			Determines how much the exploded slice is offset from the center. Value between 0 and 1.
			*/
			explodedRadius: 0.2,
			/* type="number"
			Gets or sets the scaling factor of the chart's radius. Value between 0 and 1.
			*/
			radiusFactor: 0.9,
			/* type="bool"
			Gets or sets whether the slices can be selected.
			*/
			allowSliceSelection: true,
			/* type="bool"
			Gets or sets whether the slices can be exploded.
			*/
			allowSliceExplosion: true,
			/* type="array"
			Gets or sets the collection of exploded slice indices.
			Should be an array of integers that indicate the indexes of the slices to explode.
			*/
			explodedSlices: null,
		    /* type="array"
            Sets the collection of selected slice indices.
            Should be an array of integers that indicate the indexes of the slices to select.
            */
            selectedSlices: null,
			/* type="bool" Whether the chart should render a tooltip. */
			showTooltip: false,
			/* type="string" The name of template or the template itself that chart tooltip will use to render. */
			tooltipTemplate: null,
			/* type="object" can be any valid options accepted by $.ig.ChartLegend, or an instance of an $.ig.ChartLegend itself. */
			legend: {
				/* type="string" the name of the element to turn into a legend. */
				element: null,
				/* type="item|legend" Type of the legend.
					item type="string" Specify the legend as item legend. It displays a legend item for each pie in the igPieChart control.
					legend type="string" Specify the legend as legend. It is supported by all types of series in the igDataChart control.
				*/
				type: "item",
				/* type="number" The width of the legend. It can be set as a number in pixels, string (px) or percentage (%). */
				width: null,
				/* type="number" The height of the legend. It can be set as a number in pixels, string (px) or percentage (%). */
				height: null
			},
			/* type="number"
			Gets or sets the pixel amount, by which the labels are offset from the edge of the slices.
			*/
			labelExtent: 10,
			/* type="number"
			Gets or sets the starting angle of the chart.
			The default zero value is equivalent to 3 o'clock.
			*/
			startAngle: 0,
			/* type="counterclockwise|clockwise"
			Gets or sets the rotational direction of the chart.
			counterclockwise type="string"
			clockwise type="string"
			*/
			sweepDirection: "clockwise",
			/* type="object"
			Gets or sets the style used when a slice is selected.
			*/
			selectedStyle: null,
			/* type="object"
			Gets or sets the Brushes property.
			The brushes property defines the palette from which automatically assigned slice brushes are selected.
			The value provided should be an array of css color strings. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
			*/
			brushes: null,
			/* type="object"
			Gets or sets the Outlines property.
			The Outlines property defines the palette from which automatically assigned slice outlines are selected.
			The value provided should be an array of css color strings. Optionally the first element can be a string reading "RGB" or "HSV" to specify the interpolation mode of the collection.
			*/
			outlines: null,
			/* type="object"
			Gets or sets the LegendItemTemplate property.
			The legend item control content is created according to the LegendItemTemplate on-demand by
			the chart object itself.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			legendItemTemplate: null,
			/* type="object"
			Gets or sets the LegendItemBadgeTemplate property.
			The legend item badge is created according to the LegendItemBadgeTemplate on-demand by
			the chart object itself.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			legendItemBadgeTemplate: null,
			/* type="string" Overrides the style used for text in the pie chart.
			*/
			textStyle: null,/*BUILDREMOVESTHIS*/
			/* type="string" The swatch used to style this widget */
			theme: "c"

		},
		css: {
			/* Get or set the class applied on a div element. */
			chart: "ui-corner-all ui-widget-content ui-chart-piechart-container",
			/* Get or set the class applied on a div element, shown when the chart is opened in a non HTML5 compatible browser. */
			unsupportedBrowserClass:
                "ui-html5-non-html5-supported-message ui-helper-clearfix ui-html5-non-html5",
			/* Get or set the class applied to the tooltip div element. */
			tooltip: "ui-chart-tooltip ui-widget-content ui-corner-all"
		},
		events: {
			/* cancel="true" event fired when the mouse has hovered on an element long enough to display a tooltip
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current pie chart item.
			Use ui.chart to get reference to chart object.
			*/
			tooltipShowing: "tooltipShowing",
			/* event fired after a tooltip is shown
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current pie chart item.
			Use ui.chart to get reference to chart object.
			*/
			tooltipShown: "tooltipShown",
			/* cancel="true" event fired when the mouse has left an element and the tooltip is about to hide
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current pie chart item.
			Use ui.chart to get reference to chart object.
			*/
			tooltipHiding: "tooltipHiding",
			/* event fired after a tooltip is hidden
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current pie chart item.
			Use ui.chart to get reference to chart object.
			*/
			tooltipHidden: "tooltipHidden",
			/* event fired when the control is displayed on a non HTML5 compliant browser */
			browserNotSupported: "browserNotSupported",
			/* cancel="false"
			Raised when the slice is clicked.
			Function takes arguments evt and ui.
			Use ui.chart to get reference to chart object.
			Use ui.slice to get reference to slice object.
			Use ui.slice.item to get reference to current pie chart item.
			Use ui.slice.isExploded to get is the slice exploded.
			Use ui.slice.isSelected to get is the slice selected.
			*/
			sliceClick: null,
		    /* cancel="false"
            Raised when a slice's label is clicked.
            Function takes arguments evt and ui.
            Use ui.item to get reference to the slice object.
            Use ui.allowSliceClick to determine whether or not the label click should fire slice click event.
            */
			labelClick: null
		},
		_createWidget: function (options, element) {
			this._fixCss();
			this.dvWidget = new $.ig.dvCommonWidget(this);
			this.dvWidget._createWidget(options, element, this);
		},
		_create: function () {
			this._fixCss();
			this.dvWidget._create();
		},
		_fixCss: function () {
			if (this.css.chart.indexOf("{0}") > -1) {
				this.css.chart = this.css.chart.replace("{0}", this.options.theme);
				this.css.tooltip = this.css.tooltip.replace("{0}", this.options.theme);
			}
		},
		_setOption: function (key, value) {
			this.dvWidget._setOption(key, value);
		},
		option: function () {
		    return this.dvWidget.option.apply(this.dvWidget, arguments);
		},
		addItem: function (item) {
			/* adds a new item to the data source and notifies the chart.
				paramType="object" the new item that will be added to the data source.
			*/
			this.dataSources[ this.id() ].addRow(null, item, true);
		},
		insertItem: function (item, index) {
			/* inserts a new item to the data source and notifies the chart.
				paramType="object" the new item that will be inserted in the data source.
				paramType="number" The index in the data source where the new item will be inserted.
			*/
			this.dataSources[ this.id() ].insertRow(null, item, index, true);
		},
		removeItem: function (index) {
			/* deletes an item from the data source and notifies the chart.
				paramType="number" The index in the data source from where the item will be been removed.
			*/
			this.dataSources[ this.id() ].deleteRow(index, true);
		},
		setItem: function (index, item) {
			/* updates an item in the data source and notifies the chart.
				paramType="number" optional="false" The index in the data source that we want to change.
				paramType="object" the new item that we want to set in the data source.
			*/
			this.dataSources[ this.id() ].updateRow(index, item, true);
		},
		exportImage: function (width, height) {
			/* Exports the chart to a PNG image.
				paramType="string|number" optional="true" The width of the image.
				paramType="string|number" optional="true" The height of the image.
				returnType="object" Returns a IMG DOM element. */
			return this.dvWidget._getImage(width, height, this);
		},
		destroy: function () {
			/* Destroys the widget. */
			if (this._chart) {
				this._chart.destroy();
				this._chart = null;
			}
			this.dvWidget._destroy(this);

			$.Widget.prototype.destroy.call(this);
			return this;
		},
		id: function () {
			/*  returns the ID of parent element holding the chart.
				returnType="string"
			*/
			return this.element[ 0 ].id;
		},
		widget: function () {
			/* Returns the element holding the chart. */
			return this.element;
		},
		print: function () {
			/* Creates a print preview page with the chart, hiding all other elements on the page. */
			this.dvWidget._print();
		},
		exportVisualData: function () {
			/* exports visual data from the pie chart to aid in unit testing */
			return this._chart.exportVisualData();
		}
	});
	$.extend($.ui.igPieChart, { version: "16.1.20161.2145" });
	/*MOBILEHERE*/
}(jQuery));
