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
 * infragistics.geographicmap_core.js
 * infragistics.datasource.js
 * infragistics.dvcommonwidget.js
 */

/*global jQuery, Class*/
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {

    // igMap definition
	$.widget("ui.igMap", {
		options: {
			/* type="string|number" The width of the map. It can be set as a number in pixels, string (px) or percentage (%).
				string The widget width can be set in pixels (px) and percentage (%).
				number The widget width can be set as a number
			*/
			width: null,
			/* type="string|number" The height of the map. It can be set as a number in pixels, string (px) or percentage (%).
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
			/* type="number"
			Sets or gets the automatic width to add when automatically adding margins to the map.
			*/
			autoMarginWidth: 20,
			/* type="number"
			Sets or gets the automatic height to add when automatically adding margins to the map.
			*/
			autoMarginHeight: 0,
			/* type="visible|collapsed" Gets or sets the current Map's crosshair visibility override.
			visible type="string" Crosshair should be visible.
			collapsed type="string" Crosshair should not be visible.
			*/
			crosshairVisibility: "collapsed",
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
			/* type="string"
			Gets or sets the brush used as the background for the current Map object's plot area.
			*/
			plotAreaBackground: null,
			/* type="none|dragZoom|dragPan" Gets or sets the DefaultInteraction property. The default interaction state defines the map's response to mouse events.
			none type="string" User gesture will not change the state of the map.
			dragZoom type="string" User gesture will start a drag rectangle to zoom the map.
			dragPan type="string" User gesture will start a pan action to move the map's window.
			*/
			defaultInteraction: "dragPan",
			/* type="none|alt|control|shift|windows|apple" Gets or sets the current Map's DragModifier property.
			none type="string" No modifier key is set.
			alt type="string" The modifier is set to alt key.
			control type="string" The modifier is set to control key.
			shift type="string" The modifier is set to shift key.
			*/
			dragModifier: "none",
			/* type="none|alt|control|shift|windows|apple" Gets or sets the current Map's PanModifier property.
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
			/* type="object"
			A rectangle representing the portion of the map currently in view.
			A rectangle at X=0, Y=0 with a Height and Width of 1 implies the entire plotting area is in view.  A Height and Width of .5 would imply that the view is halfway zoomed in.
			The provided object should have numeric properties called left, top, width and height.
			*/
			windowRect: null,
			/* type="bool"
			Gets or sets the current Map's zoomability.
			*/
			zoomable: true,
		    /* type="number"
			Gets or sets the current Map's zoom scale.
			*/
			windowScale: 1,
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
			/* type="object"
			Gets or sets the template to use for circle markers on the map.
			Defines the marker template used for
			series with a marker type of circle.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			circleMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for triangle markers on the map.
			Defines the marker template used for
			series with a marker type of triangle.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			triangleMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for pyramid markers on the map.
			Defines the marker template used for
			series with a marker type of pyramid.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			pyramidMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for square markers on the map.
			Defines the marker template used for
			series with a marker type of square.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			squareMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for diamond markers on the map.
			Defines the marker template used for
			series with a marker type of diamond.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			diamondMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for pentagon markers on the map.
			Defines the marker template used for
			series with a marker type of pentagon.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			pentagonMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for hexagon markers on the map.
			Defines the marker template used for
			series with a marker type of hexagon.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			hexagonMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for tetragram markers on the map.
			Defines the marker template used for
			series with a marker type of tetragram.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			tetragramMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for pentragram markers on the map.
			Defines the marker template used for
			series with a marker type of pentagram.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			pentagramMarkerTemplate: null,
			/* type="object"
			Gets or sets the template to use for hexagram markers on the map.
			Defines the marker template used for
			series with a marker type of hexagram.
			The provided object should have properties called render and optionally measure. See definition for option: legendItemBadgeTemplate
			*/
			hexagramMarkerTemplate: null,
			/* type="string"
			The background image uri use in the overview detail pane.
			*/
			overviewPlusDetailPaneBackgroundImageUri: null,
		    /* type="bool" sets whether the series viewer should use cached tiles during zooms rather than the default live content. */
			useTiledZooming: false,
		    /* type="bool" sets whether the series viewer should prefer selecting higher resolution tiles over lower resolution tiles when performing tile zooming. Setting this to true will lower performance but increase quality. */
			preferHigherResolutionTiles: false,
		    /* type="number" sets the maximum number of zoom tiles that the series viewer should cache while in tiled zooming mode. */
			zoomTileCacheSize: 30,
			/* type="object" background content object*/
			backgroundContent: {
				/* type="openStreet|cloudMade|bing" Type of the background content for the map.
					openStreet type="string" Specify the background content to display OpenStreetMap geographic data. Set as default.
					cloudMade type="string"  Specify the background content to display CloudMade geographic data.
					bing type="string"  Specify the background content to BingMaps geographic data.
				*/
				type: "openStreet",
				/* type="string" Gets or sets the key. */
				key: null,
				/* type="string" Gets or sets the parameter. */
				parameter: null,
				/* type="string" Gets or sets the map tile image uri. This is a bing maps specific option */
				tilePath: null,
				/* type="string" Gets or sets the type of the imagery. */
				imagerySet: "AerialWithLabels",
				/* type="string" Gets or sets the bing maps url */
				bingUrl: "http://dev.virtualearth.net/REST/v1/Imagery/Metadata/"
			},
			/* type="array" an array of series objects */
			series: [{
			    /* type="geographicShape|geographicPolyLine|geographicScatterArea|geographicSymbolSeries|geographicContourLine|geographicHighDensityScatter|geographicProportionalSymbol" Type of the series.
					geographicSymbolSeries type="string" Specify the series as geographic Symbol Series series.
					geographicPolyLine type="string" Specify the series as geographic Polyline series.
					geographicScatterArea type="string" Specify the series as geographic Scatter Area series.
					geographicShape type="string" Specify the series as geographic Shape series.
					geographicContourLine type="string" Specify the series as geographic Contour Line series.
					geographicHighDensityScatter type="string" Specify the series as geographic High Density Scatter series.
					geographicProportionalSymbol type="string" Specify the series as geographic Proportional Symbol series.
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
                /* type="bool" Set to true in order to have an existing series removed from the map, by name */
                remove: false,
				/* type="bool" Whether the map should render a tooltip. */
                showTooltip: false,

				//	legendItemVisibility: "visible",
				/* type="string"
					The triangulated file source URI or an instance of $.ig.ShapeDataSource.
				*/
				shapeDataSource: null,
				/* type="string"
					string The database source URI.
				*/
				databaseSource: null,
				/* type="string"
				 The triangulated file source URI or an instance of $.ig.TriangulationDataSource.
				*/
				triangulationDataSource: null,
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
				Some series types, such as LineSeries, do not display outlines.
				*/
				outline: null,
				/* type="number"
				Gets or sets the width of the current series object's line thickness.
				*/
				thickness: 0,
				/* type="object"
				The source of triangulation data.
				This property is optional.  If it is left as null, the triangulation will be created based on the items in the data source.  Triangulation is a demanding operation, so the runtime performance will be better when specifying a TriangulationSource, especially when a large number of data items are present.
				*/
				trianglesSource: null,
				/* type="string"
				The name of the property of the TrianglesSource items which, for each triangle, contains the index of the first vertex point in the data source.
				*/
				triangleVertexMemberPath1: null,
				/* type="string"
				The name of the property of the TrianglesSource items which, for each triangle, contains the index of the second vertex point in the data source.
				*/
				triangleVertexMemberPath2: null,
				/* type="string"
				The name of the property of the TrianglesSource items which, for each triangle, contains the index of the third vertex point in the data source.
				*/
				triangleVertexMemberPath3: null,
				/* type="object"
				The ColorScale used to resolve the color values of points in the series.
				*/
				colorScale: null,
				/* type="string"
				The name of the property on each data item containing a numeric value which can be converted to a color by the ColorScale.
				*/
				colorMemberPath: null,
				/* type="number"
				The minimum scale at which this series becomes visible.
				The default value for this property is 1.0, which means the series will always be visible.  At a VisibleFromScale setting of 0.0, the series will never be visible.  At a VisibleFromScale setting of 0.5, the series will be visible as long as the map is zoomed in to at least 200%.
				*/
				visibleFromScale: 0,
				/* type="string"
				The name of the property of data source items which contains the longitude coordinate of the symbol.
				*/
				longitudeMemberPath: null,
				/* type="string"
				The name of the property of data source items which contains the latitude coordinate of the symbol.
				*/
				latitudeMemberPath: null,
				/* type="unset|none|automatic|circle|triangle|pyramid|square|diamond|pentagon|hexagon|tetragram|pentagram|hexagram" Gets or sets the marker type for the current series object.
				If the MarkerTemplate property is set, the setting of the MarkerType property will be ignored.
				unset type="string"
				none type="string"
				automatic type="string"
				circle type="string"
				triangle type="string"
				pyramid type="string"
				square type="string"
				diamond type="string"
				pentagon type="string"
				hexagon type="string"
				tetragram type="string"
				pentagram type="string"
				hexagram type="string"
				*/
				markerType: "none",
				/* type="object"
				Gets or sets the MarkerTemplate for the current series object.
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
				markerTemplate: null,
				/* type="string"
				The name of the property on data source items which, for each shape, contains a list of points to be converted to a polygon.
				To be consistent with the Shapefile technical description, it is expected that each list of points is defined as an IEnumerable of IEnumerable of Point, or in other words, a list of lists of points.
				*/
				shapeMemberPath: null,
				/* type="object"
				The StyleSelector which is used to select a style for each Shape.
				*/
				shapeStyleSelector: null,
				/* type="object"
				The default style to apply to all Shapes in the series.
				*/
				shapeStyle: null,
				/* type="string"
				Gets or sets the brush that specifies how the current series object's marker interiors are painted.
				*/
				markerBrush: null,
				/* type="string"
				Gets or sets the brush that specifies how the current series object's marker outlines are painted.
				*/
				markerOutline: null,
                /* type="none|omit|fade|omitAndShift|fadeAndShift" Gets or sets the MarkerCollisionAvoidance
                none type="string" No collision avoidance is attempted.
                omit type="string" Markers that collide will be omitted.
                fade type="string" Markers that collide will be faded in opacity.
                omitAndShift type="string" Markers that collide may be shifted or omitted.
                omitAndFade type="string" Markers that collide may be shifted or faded.
                */
                markerCollisionAvoidance: "none",
				/* type="object"
				The ValueBrushScale to use when determining Brushes for each Shape, based on the values found in FillMemberPath.
				*/
				fillScale: null,
				/* type="string"
				The name of the property on data source items which contains a numeric value to convert to a Brush using the FillScale.
				*/
				fillMemberPath: null,
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
				/* type="bool"
				Gets or sets whether to clip the series to the bounds.
				Setting this to true can affect performance.
				*/
				clipSeriesToBounds: null,
				/* type="string"
				Gets or sets the item path that provides the values for the current series.
				*/
				valueMemberPath: null,
				/* type="linearInterpolate|dontPlot" Determines how unknown values will be plotted on the map. Null and Double.NaN are two examples of unknown values.
				linearInterpolate type="string" Plot the unknown value as the midpoint between surrounding known values using linear interpolation.
				dontPlot type="string" Do not plot the unknown value on the map.
				*/
				unknownValuePlotting: "dontPlot",
				/* type="number"
				Gets or sets the x-radius of the ellipse that is used to round the corners of the column.
				*/
				angleMemberPath: null,
				/* type="bool"
				Gets or sets whether Cartesian Interpolation should be used rather than Archimedian
				spiral based interpolation.
				*/
				useCartesianInterpolation: true,
				/* type="number"
				Gets or sets the Stiffness property.
				*/
				stiffness: 0.5,
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
				Gets or sets the value mapping property for the current series object.
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
				/* type="object"
				Gets or sets the ContourValueResolver used to determine the numeric values of contours.
				*/
				valueResolver: null,
				/* type="number"
				Gets or sets the resolution at which to filter out shapes in the series.
				For example, if the shapeFilterResolution is set to 3, then elements with a bounding rectangle smaller than 3 X 3 pixels will be filtered out.
				*/
				shapeFilterResolution: 2,
				/* type="bool"
				Gets or sets the whether to use use brute force mode.
				*/
				useBruteForce: false,
				/* type="bool"
				Gets or sets the whether to progressively load the data into the map.
				*/
				progressiveLoad: true,
				/* type="bool"
				Gets or sets the whether the map reacts to mouse move events.
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
				heatMaximum: 50
			}],/*BUILDREMOVESTHIS*/
			/* type="string" The swatch used to style this widget */
			theme: "c"
        },
		css: {
			/* Get or set the class applied on a div element. */
			map: "ui-corner-all ui-widget-content",
			/* Get or set the class applied on a div element, shown when the map is opened in a non HTML5 compatible browser. */
			unsupportedBrowserClass: "ui-chart-non-html5-supported-message ui-helper-clearfix",
            /* Get or set the class applied to the tooltip div element. */
            tooltip: "ui-chart-tooltip ui-widget-content ui-corner-all"
		},
		events: {
			/* cancel="true" event fired when the mouse has hovered on a series and the tooltip is about to show
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			*/
			tooltipShowing: "tooltipShowing",
			/* event fired after a tooltip is shown
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			*/
			tooltipShown: "tooltipShown",
			/* cancel="true" event fired when the mouse has left a series and the tooltip is about to hide
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			*/
			tooltipHiding: "tooltipHiding",
			/* event fired after a tooltip is hidden
			Function takes arguments evt and ui.
			Use ui.element to get reference to tooltip DOM element.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			*/
			tooltipHidden: "tooltipHidden",
			/* event fired when the control is displayed on a non HTML5 compliant browser */
			browserNotSupported: "browserNotSupported",
			/* cancel="false"
			Occurs when the cursors are moved over a series in this map.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesCursorMouseMove: null,
			/* cancel="false"
			Occurs when the left mouse button is pressed while the mouse pointer is over an element of this map.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseLeftButtonDown: null,
			/* cancel="false"
			Occurs when the left mouse button is released while the mouse pointer is over an element of this map.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseLeftButtonUp: null,
			/* cancel="false"
			Occurs when the left mouse pointer moves while over an element of this map.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseMove: null,
			/* cancel="false"
			Occurs when the left mouse pointer enters an element of this map.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseEnter: null,
			/* cancel="false"
			Occurs when the left mouse pointer leaves an element of this map.
			Function takes arguments evt and ui.
			Use ui.item to get reference to current series item object.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.actualItemBrush to get item brush.
			Use ui.actualSeriesBrush to get series brush.
			Use ui.positionX to get mouse X position.
			Use ui.positionY to get mouse Y position.
			*/
			seriesMouseLeave: null,
			/* cancel="false"
			Occurs just after the current Map's window rectangle is changed.
			Function takes arguments evt and ui.
			Use ui.map to get reference to map object.
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
			Occurs just after the current Map's grid area rectangle is changed.
			The grid area may change as the result of the Map being resized.
			Function takes arguments evt and ui.
			Use ui.map to get reference to map object.
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
			Raised when the map's processing for an update has completed.
			Function takes arguments evt and ui.
			Use ui.map to get reference to map object.
			*/
			refreshCompleted: null,
			/* cancel="false"
			Event fired when the status of an ongoing Triangulation has changed.
			Function takes arguments evt and ui.
			Use ui.map to get reference to map object.
			Use ui.series to get reference to current series object.
			Use ui.currentStatus to get current status.
			*/
			triangulationStatusChanged: null
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
			if (this.css.map.indexOf("{0}") > -1) {
				this.css.map = this.css.map.replace("{0}", this.options.theme);
				this.css.tooltip = this.css.tooltip.replace("{0}", this.options.theme);
			}
		},
		_setOption: function (key, value) {
			this.dvWidget._setOption(key, value);
		},
		option: function () {
		    return this.dvWidget.option.apply(this.dvWidget, arguments);
		},
        destroy: function () {
			/* Destroys the widget. */
            if (this._chart) {
                this._chart.destroy();
            }
            this.dvWidget._destroy(this);

			$.Widget.prototype.destroy.apply(this, arguments);
		},
		id: function () {
			/*  returns the ID of parent element holding the map.
				returnType="string"
			*/
			return this.element[ 0 ].id;
		},
        exportImage: function (width, height) {
			/* Exports the map to a PNG image.
                paramType="string|number" optional="true" The width of the image.
                paramType="string|number" optional="true" The height of the image.
                returnType="object" Returns a IMG DOM element. */
			return this.dvWidget._getImage(width, height, this);
		},
        styleUpdated: function () {
            /* Notify the map that styles it draws colors from may have been updated.
                returnType="object" Returns a reference to this igMap. */
            this._chart.styleUpdated();
            return this;
        },
        resetZoom: function () {
            /* Resets the zoom level of the map to default.
                returnType="object" Returns a reference to this igMap. */
            this._chart.resetZoom();
            return this;
        },
		addItem: function (item, targetName) {
			/* adds a new item to the data source and notifies the map.
				paramType="object" The item that we want to add to the data source.
				paramType="string" The name of the series bound to the data source.
			*/
			var dataSourceId = targetName || this._containerSourceID;
			if (this.dataSources[ dataSourceId ]) {
				this.dataSources[ dataSourceId ].addRow(null, item, true);
			}
		},
		insertItem: function (item, index, targetName) {
			/* inserts a new item to the data source and notifies the map.
				paramType="object" the new item that we want to insert in the data source.
				paramType="number" The index in the data source where the new item will be inserted.
				paramType="string" The name of the series bound to the data source.
			*/
			var dataSourceId = targetName || this._containerSourceID;
			if (this.dataSources[ dataSourceId ]) {
				this.dataSources[ dataSourceId ].insertRow(null, item, index, true);
			}
		},
		removeItem: function (index, targetName) {
			/* deletes an item from the data source and notifies the map.
				paramType="number" The index in the data source from where the item will be been removed.
				paramType="string" The name of the series bound to the data source.
			*/
			var dataSourceId = targetName || this._containerSourceID;
			if (this.dataSources[ dataSourceId ]) {
				this.dataSources[ dataSourceId ].deleteRow(index, true);
			}
		},
		setItem: function (index, item, targetName) {
			/* updates an item in the data source and notifies the map.
				paramType="number" The index of the item in the data source that we want to change.
				paramType="object" The new item object that will be set in the data source.
				paramType="string" The name of the series bound to the data source.
			*/
			var dataSourceId = targetName || this._containerSourceID;
			if (this.dataSources[ dataSourceId ]) {
				this.dataSources[ dataSourceId ].updateRow(index, item, true);
			}
		},
        notifySetItem: function (dataSource, index, newItem, oldItem) {
            /* Notifies the the map that an item has been set in an associated data source.
                paramType="object" optional="false" The data source in which the change happened.
                paramType="number" optional="false" The index in the items source that has been changed.
                paramType="object" optional="false" the new item that has been set in the collection.
                paramType="object" optional="false" the old item that has been overwritten in the collection.
                returnType="object" Returns a reference to this igMap. */
            this._chart.notifySetItem(dataSource, index, oldItem, newItem);
            return this;
        },
        notifyClearItems: function (dataSource) {
            /* Notifies the the map that the items have been cleared from an associated data source.
                It's not necessary to notify more than one target of a change if they share the same items source.
                paramType="object" optional="false" The data source in which the change happened.
                returnType="object" Returns a reference to this igMap. */
            this._chart.notifyClearItems(dataSource);
            return this;
        },
        notifyInsertItem: function (dataSource, index, newItem) {
            /* Notifies the the target series that an item has been inserted at the specified index in its data source.
                It's not necessary to notify more than one target of a change if they share the same items source.
                paramType="object" optional="false" The data source in which the change happened.
                paramType="number" optional="false" The index in the items source where the new item has been inserted.
                paramType="object" optional="false" the new item that has been set in the collection.
                returnType="object" Returns a reference to this igMap. */
            this._chart.notifyInsertItem(dataSource, index, newItem);
            return this;
        },
        notifyRemoveItem: function (dataSource, index, oldItem) {
            /* Notifies the the target series that an item has been removed from the specified index in its data source.
                It's not necessary to notify more than one target of a change if they share the same items source.
                paramType="object" optional="false" The data source in which the change happened.
                paramType="number" optional="false" The index in the items source from where the old item has been removed.
                paramType="object" optional="false" the old item that has been removed from the collection.
                returnType="object" Returns a reference to this igMap. */
            this._chart.notifyRemoveItem(dataSource, index, oldItem);
            return this;
        },
        scrollIntoView: function (targetName, item) {
            /* Notifies the target series or axis that it should scroll the requested data item into view.
                paramType="string" optional="false" The name of the series or axis notify.
                paramType="object" optional="false" The data item to bring into view, if possible.
                returnType="object" Returns a reference to this igMap. */
            var target = this.dvWidget._getNotifyTarget(targetName);
            if (target && target.scrollIntoView) {
                target.scrollIntoView(item);
            }
            return this;
        },
        scaleValue: function (targetName, unscaledValue) {
            /* Either xAxis or yAxis (longitude or latitude) that it should scale the requested value into map space from axis space.
				For example you can use this method if you want to find where longitude 50 stands scaled to map's width.
                paramType="string" optional="false" Either xAxis or yAxis to notify.
                paramType="number" optional="false" The value in axis space to translate into map space.
                returnType="number" Returns the scaled value. */
            var target = this.dvWidget._getNotifyTarget(targetName);
            if (target && target.scaleValue) {
                return target.scaleValue(unscaledValue);
            }
            return 0.0;
        },
        unscaleValue: function (targetName, scaledValue) {
            /* Either xAxis or yAxis (longitude or latitude) that it should unscale the requested value into axis space from map space.
				For example you can use this method if you want to find what is the longitude unscaled from 0 width of the map.
                paramType="string" optional="false" Either xAxis or yAxis to notify.
                paramType="number" optional="false" The value in map space to translate into axis space.
                returnType="number" Returns the unscaled value. */
            var target = this.dvWidget._getNotifyTarget(targetName);
            if (target && target.unscaleValue) {
                return target.unscaleValue(scaledValue);
            }
            return 0.0;
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
		    /* Clears the tile zoom tile cache so that new tiles will be generated. Only applies if the viewer is using a tile based zoom..
			*/
		    this._chart.clearTileZoomCache();
		    return this;
		},
        flush: function () {
            /* Forces any pending deferred work to render on the map before continuing */
            this._chart.flush();
        },
        exportVisualData: function () {
            /* exports visual data from the map to aid in unit testing */
            return this._chart.exportVisualData();
        },
        getActualMinimumValue: function (targetName) {
            /* Gets the actual minimum value of the target xAxis or yAxis */
            var target = this.dvWidget._getNotifyTarget(targetName);
            if (target && target.actualMinimumValue) {
                return target.actualMinimumValue();
            }
            return 0.0;
        },
        getActualMaximumValue: function (targetName) {
            /* Gets the actual maximum value of the target xAxis or yAxis */
            var target = this.dvWidget._getNotifyTarget(targetName);
            if (target && target.actualMaximumValue) {
                return target.actualMaximumValue();
            }
            return 0.0;
        },
        notifyContainerResized: function () {
			/* Notifies the map that the container was resized */
            this._chart.notifyContainerResized();
        },
		zoomToGeographic: function (rect) {
			/*  Zoom in to the geographic region specified, when possible (may need to wait fior map to be initialized).
                paramType="object" The geographic area rectangle.
                returnType="object" Returns a reference to this igMap. */
			var theRect;
			if (rect === undefined) {
                rect = this._chart.getGeographicFromZoom();
            } else {
			    theRect = $.ig.APIFactory.prototype.createRect(rect.left, rect.top, rect.width, rect.height);
                rect = theRect;
            }
            this._chart.zoomToGeographic(rect);

			return this;
		},
        getGeographicFromZoom: function (rect) {
			/*  Given the current plot area of the control and a geographic region, get the WindowRect that would encompass that geographic region.
                paramType="object" The geographic area rectangle.
                returnType="object" The window zoom area. */
            var res, ret, theRect;
            if (rect === undefined) {
                rect = this._chart.windowRect();
            } else {
                theRect = $.ig.APIFactory.prototype.createRect(
                    rect.left, rect.top, rect.width, rect.height);
                rect = theRect;
            }
            res = this._chart.getGeographicFromZoom(rect);
            ret = {};
            ret.top = res.top();
            ret.left = res.left();
            ret.width = res.width();
            ret.height = res.height();
            return ret;
        },
        getZoomFromGeographic: function (rect) {
			/*  Given the current plot area of the control and a geographic region, get the WindowRect that would encompass that geographic region.
                paramType="object" The geographic area rectangle.
                returnType="object" The window zoom area. */
            var res, ret, theRect;
            if (rect === undefined) {
                rect = this._chart.windowRect();
            } else {
                theRect = $.ig.APIFactory.prototype.createRect(
                    rect.left, rect.top, rect.width, rect.height);
                rect = theRect;
            }
            res = this._chart.getZoomRectFromGeoRect(rect);
            ret = {};
            ret.top = res.top();
            ret.left = res.left();
            ret.width = res.width();
            ret.height = res.height();
            return ret;
        },
		print: function () {
			/* Creates a print preview page with the map, hiding all other elements on the page. */
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
		_initConverter: function (options, callback) {
		    var id = options.name || this._containerSourceID, convDataSource =
                options.triangulationDataSource || options.shapeDataSource;
            if (this.dataSources === undefined) {
                this.dataSources = {};
            }

            if (convDataSource instanceof $.ig.TriangulationDataSource ||
                convDataSource instanceof $.ig.ShapeDataSource) {
                this.dataSources[ id ] = convDataSource.converter();
                if (convDataSource.isBound()) {
                    callback.call(this.dvWidget);
                } else {
					convDataSource.settings.id = id;
                    convDataSource.settings.callee = this.dvWidget;
                    convDataSource.settings.callback = callback;
                }
            } else {
                if (options.triangulationDataSource) {
                    this.dataSources[ id ] = new $.ig.TriangulationDataSource(
                        {
                            id: id, source: options.triangulationDataSource,
                            callback: callback, callee: this.dvWidget
                        }).dataBind().converter();
                } else {
                    this.dataSources[ id ] = new $.ig.ShapeDataSource(
                        {
                            id: id, shapefileSource: options.shapeDataSource,
                            databaseSource: options.databaseSource,
                            callback: callback, callee: this.dvWidget
                        }).dataBind().converter();
                }
            }
            this.dataSources[ id ].id = id;
        }
    });
	$.extend($.ui.igMap, { version: "16.1.20161.2145" });

	$.ig.ShapeDataSource = $.ig.ShapeDataSource || Class.extend({

        settings: {
			/* type="string" The unique identifier. */
			id: null,
            /* type="string" The Uri of the .shp portion of the Shapefile. */
            shapefileSource: null,
            /* type="string" The Uri of the .dbf portion of the Shapefile. */
            databaseSource: null,
            /* type="function" callback function to call when data binding is complete. */
            callback: null,
            /* type="object" object on which to invoke the callback function. */
            callee: null,
			/* type="function" callback function to call to allow shape records to be transformed.
				paramType="object" the shape record to be transformed. */
			transformRecord: null,
			/* type="function" callback function to call to allow points in the shape records to be transformed.
				paramType="object" the point to be transformed in place. The object will look like { x: value, y: value2 } */
			transformPoint: null,
			/* type="function" callback function to call to allow the bounds of the shape data source to be transformed.
				paramType="object" the bounds of the shape datasource to be transformed in place. The object will look like { top: value, left: value, width: value, height: value } */
			transformBounds: null,
			/* type="function" callback function to call when the import process has been completed
				paramType="object" the ShapeDataSource instance */
			importCompleted: null
        },

        init: function (options) {
            this._uris = {};
            this._isImported = false;
            this.settings = $.extend(true, {}, $.ig.ShapeDataSource.prototype.settings, options);
            this._converter = new $.ig.ShapefileConverter();
            this._converter.importCompleted = jQuery.proxy(this._importCompleted, this);
            return this;
        },

        _importCompleted: function () {
			var i, j, k, bounds, currRecord, subShape, point, flatPoint;
            this._isImported = true;

			if (this._converter !== null &&	(typeof (this.settings.transformBounds) === "function")) {
				bounds = {
					left: this._converter.worldRect().left(),
					top: this._converter.worldRect().top(),
					width: this._converter.worldRect().width(),
					height: this._converter.worldRect().height()
				};

				this.settings.transformBounds(bounds);

				this._converter.worldRect($.ig.APIFactory.prototype.createRect(
                    bounds.left, bounds.top, bounds.width, bounds.height));
				this._converter.bounds = bounds;
			}

			if (this._converter !== null && (typeof (this.settings.transformRecord) ===
                "function" || typeof (this.settings.transformPoint) === "function")) {
				for (i = 0; i < this._converter.records().count(); i++) {
					currRecord = this._converter.records().item(i);

					if (typeof (this.settings.transformPoint) === "function") {
						for (j = 0; j < currRecord.points.count(); j++) {
							subShape = currRecord.points.item(j);
							for (k = 0; k < subShape.count(); k++) {
								point = subShape.item(k);
								flatPoint = { x: point.x(), y: point.y() };
								this.settings.transformPoint(flatPoint);

								point.x(flatPoint.x);
								point.y(flatPoint.y);
							}
						}
					}

					if (typeof (this.settings.transformRecord) === "function") {
						this.settings.transformRecord(currRecord);
					}
				}
			}

			if (typeof (this.settings.importCompleted) === "function") {
				this.settings.importCompleted(this);
			}

            if (typeof (this.settings.callback) === "function") {
                this.settings.callback.call(this);
            }
        },

        dataBind: function () {
		/* loads to the current data source */
            if (this.settings.shapefileSource) {
                this._uris.shapefileSource = new $.ig.Uri();
                this._uris.shapefileSource.value(this.settings.shapefileSource);
                this._converter.shapefileSource(this._uris.shapefileSource);
            }
            if (this.settings.databaseSource) {
                this._uris.databaseSource = new $.ig.Uri();
                this._uris.databaseSource.value(this.settings.databaseSource);
                this._converter.databaseSource(this._uris.databaseSource);
            }
            return this;
        },

        isBound: function () {
		/* returns true if data is loaded
			returnType="bool"
		*/
            return this._isImported;
        },

        converter: function () {
		/* returns the current converter instance
			returnType="object"
		*/
            return this._converter;
        }
    });

    $.ig.TriangulationDataSource = $.ig.TriangulationDataSource || Class.extend({

        settings: {
			/* type="string" The unique identifier. */
			id: null,
            /* type="string" A Uri specifying the location of the Itf file. */
            source: null,
            /* type="string" The TriangulationSource which is typically created after importing the Itf from the Source Uri.*/
            triangulationSource: null,
            /* type="function" callback function to call when data binding is complete */
            callback: null,
            /* type="object" object on which to invoke the callback function */
            callee: null
        },

        init: function (options) {
            this._uris = {};
            this._isImported = false;
            this.settings = $.extend(true, {},
                $.ig.TriangulationDataSource.prototype.settings, options);
            this._converter = new $.ig.ItfConverter();
            this._converter.importCompleted = jQuery.proxy(this._importCompleted, this);
            return this;
        },

        _importCompleted: function () {
            this._isImported = true;
            if (typeof (this.settings.callback) === "function") {
                this.settings.callback.call(this);
            }
        },

        dataBind: function () {
		/* loads to the current data source */
            if (this.settings.source) {
                this._uris.source = new $.ig.Uri();
                this._uris.source.value(this.settings.source);
                this._converter.source(this._uris.source);
            }
            if (this.settings.triangulationSource) {
                this._uris.triangulationSource = new $.ig.Uri();
                this._uris.triangulationSource.value(this.settings.triangulationSource);
                this._converter.source(this._uris.triangulationSource);
            }
            return this;
        },

        isBound: function () {
		/* returns true if data is loaded
			returnType="bool"
		*/
            return this._isImported;
        },

        converter: function () {
		/* returns the current converter instance
			returnType="object"
		*/
            return this._converter;
        }
    });

	/*MOBILEHERE*/
}(jQuery));
