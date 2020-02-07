/*!@license
 * Infragistics.Web.ClientUI CategoryChart 19.1.20191.172
 *
 * Copyright (c) 2011-2019 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 * jquery.js
 * jquery-ui.js
 * infragistics.util.js
 * infragistics.util.jquery.js
 * infragistics.datasource.js
 * infragistics.templating.js
 * infragistics.ext_core.js
 * infragistics.ext_collections.js
 * infragistics.ext_ui.js
 * infragistics.dv_core.js
 * infragistics.dv_datasource.js
 * infragistics.dv_dataseriesadapter.js
 * infragistics.dv_geometry.js
 * infragistics.dv_jquerydom.js
 * infragistics.datachart_core.js
 * infragistics.datachart_categorycore.js
 * infragistics.dvcommonwidget.js
 * infragistics.datachart_category.js
 * infragistics.categorychart.js
 * infragistics.ui.widget.js
 * infragistics.ui.basechart.js
 * infragistics.ui.categorychart.js
 */
(function(factory){if(typeof define==="function"&&define.amd){define(["jquery","jquery-ui","./infragistics.util","./infragistics.util.jquery","./infragistics.datasource","./infragistics.templating","./infragistics.dv_dataseriesadapter","./infragistics.dv_jquerydom","./infragistics.categorychart","./infragistics.dvcommonwidget","./infragistics.ui.basechart"],factory)}else{factory(jQuery)}})(function($){var _aNull=function(v,nan){return v===null||v===undefined||nan&&typeof v==="number"&&isNaN(v)};$.widget("ui.igCategoryChart",$.ui.igBaseChart,{css:{chart:"ui-category ui-corner-all ui-widget-content ui-categorychart-container",tooltip:"ui-category-tooltip ui-widget-content ui-corner-all"},options:{tooltipTemplate:null,tooltipTemplates:null,pixelScalingRatio:NaN,titleLeftMargin:0,titleRightMargin:0,titleTopMargin:0,titleBottomMargin:0,subtitleLeftMargin:0,subtitleTopMargin:0,subtitleRightMargin:0,subtitleBottomMargin:0,subtitleTextColor:null,titleTextColor:null,leftMargin:NaN,topMargin:NaN,rightMargin:NaN,bottomMargin:NaN,transitionDuration:0,transitionEasingFunction:null,createWrappedTooltip:null,widget:null,subtitleTextStyle:null,titleTextStyle:null,itemsSource:null,includedProperties:null,excludedProperties:null,brushes:null,outlines:null,legend:null,isHorizontalZoomEnabled:true,isVerticalZoomEnabled:true,isSeriesHighlightingEnabled:false,windowRect:null,title:null,subtitle:null,titleAlignment:"center",subtitleAlignment:"center",unknownValuePlotting:null,resolution:1,thickness:1,markerTypes:null,markerBrushes:null,markerOutlines:null,markerMaxCount:400,animateSeriesWhenAxisRangeChanges:false,trendLineBrushes:null,trendLineType:null,trendLineThickness:1.5,alignsGridLinesToPixels:true,trendLinePeriod:7,toolTipType:null,crosshairsDisplayMode:null,crosshairsSnapToData:false,crosshairsAnnotationEnabled:false,finalValueAnnotationsVisible:false,calloutsVisible:false,calloutStyleUpdatingEventEnabled:false,calloutsItemsSource:null,calloutsXMemberPath:null,calloutsYMemberPath:null,calloutsLabelMemberPath:null,calloutsContentMemberPath:null,xAxisFormatLabel:null,yAxisFormatLabel:null,xAxisLabelLeftMargin:0,xAxisLabelTopMargin:5,xAxisLabelRightMargin:0,xAxisLabelBottomMargin:5,yAxisLabelLeftMargin:5,yAxisLabelTopMargin:0,yAxisLabelRightMargin:5,yAxisLabelBottomMargin:0,xAxisLabelTextColor:null,yAxisLabelTextColor:null,xAxisTitleMargin:0,yAxisTitleMargin:0,xAxisTitleLeftMargin:NaN,yAxisTitleLeftMargin:NaN,xAxisTitleTopMargin:NaN,yAxisTitleTopMargin:NaN,xAxisTitleRightMargin:NaN,yAxisTitleRightMargin:NaN,xAxisTitleBottomMargin:NaN,yAxisTitleBottomMargin:NaN,xAxisTitleTextColor:null,yAxisTitleTextColor:null,xAxisLabelTextStyle:null,yAxisLabelTextStyle:null,xAxisTitleTextStyle:null,yAxisTitleTextStyle:null,xAxisLabel:null,yAxisLabel:null,xAxisMajorStroke:null,yAxisMajorStroke:null,xAxisMajorStrokeThickness:1,yAxisMajorStrokeThickness:1,xAxisMinorStrokeThickness:1,yAxisMinorStrokeThickness:0,xAxisStrip:null,yAxisStrip:null,xAxisStroke:null,yAxisStroke:null,xAxisStrokeThickness:1,yAxisStrokeThickness:1,xAxisTickLength:5,yAxisTickLength:0,xAxisTickStroke:null,yAxisTickStroke:null,xAxisTickStrokeThickness:2,yAxisTickStrokeThickness:2,xAxisTitle:null,yAxisTitle:null,xAxisMinorStroke:null,yAxisMinorStroke:null,xAxisLabelAngle:0,yAxisLabelAngle:0,xAxisExtent:NaN,yAxisExtent:NaN,xAxisTitleAngle:0,yAxisTitleAngle:-90,xAxisInverted:false,yAxisInverted:false,xAxisTitleAlignment:"center",yAxisTitleAlignment:"center",xAxisLabelHorizontalAlignment:"center",yAxisLabelHorizontalAlignment:"right",xAxisLabelVerticalAlignment:"top",yAxisLabelVerticalAlignment:"center",xAxisLabelVisibility:null,yAxisLabelVisibility:null,yAxisLabelLocation:"outsideLeft",transitionInDuration:1e3,transitionInEasingFunction:null,chartType:null,markerCollisionAvoidance:"omit",isTransitionInEnabled:false,transitionInMode:"auto",transitionInSpeedType:"auto",xAxisInterval:0,xAxisMinorInterval:0,xAxisGap:0,xAxisOverlap:0,yAxisInterval:NaN,yAxisIsLogarithmic:false,yAxisLogarithmBase:10,yAxisMinimumValue:NaN,yAxisMaximumValue:NaN,yAxisMinorInterval:0,negativeBrushes:null,negativeOutlines:null,yAxisAbbreviateLargeNumbers:true,isCategoryHighlightingEnabled:false,isItemHighlightingEnabled:false},events:{propertyChanged:null,seriesAdded:null,seriesRemoved:null,seriesPointerEnter:null,seriesPointerLeave:null,seriesPointerMove:null,seriesPointerDown:null,seriesPointerUp:null,calloutStyleUpdating:null},_create:function(){this._tooltip={};this._tooltipTemplates={};this._seriesOpt={};$.ui.igBaseChart.prototype._create.apply(this);this._getChartEvt=$.proxy($.ig.dvCommonWidget.prototype._getChartEvt,this);this._widget=this.widget;this.widget=this;var categoryChart=this._chart;if(categoryChart){categoryChart.propertyChanged=$.ig.Delegate.prototype.combine(categoryChart.propertyChanged,$.proxy(this._fireCategoryChart_propertyChanged,this));categoryChart.seriesAdded=$.ig.Delegate.prototype.combine(categoryChart.seriesAdded,$.proxy(this._fireCategoryChart_seriesAdded,this));categoryChart.seriesRemoved=$.ig.Delegate.prototype.combine(categoryChart.seriesRemoved,$.proxy(this._fireCategoryChart_seriesRemoved,this));categoryChart.seriesPointerEnter=$.ig.Delegate.prototype.combine(categoryChart.seriesPointerEnter,$.proxy(this._fireCategoryChart_seriesPointerEnter,this));categoryChart.seriesPointerLeave=$.ig.Delegate.prototype.combine(categoryChart.seriesPointerLeave,$.proxy(this._fireCategoryChart_seriesPointerLeave,this));categoryChart.seriesPointerMove=$.ig.Delegate.prototype.combine(categoryChart.seriesPointerMove,$.proxy(this._fireCategoryChart_seriesPointerMove,this));categoryChart.seriesPointerDown=$.ig.Delegate.prototype.combine(categoryChart.seriesPointerDown,$.proxy(this._fireCategoryChart_seriesPointerDown,this));categoryChart.seriesPointerUp=$.ig.Delegate.prototype.combine(categoryChart.seriesPointerUp,$.proxy(this._fireCategoryChart_seriesPointerUp,this));categoryChart.calloutStyleUpdating=$.ig.Delegate.prototype.combine(categoryChart.calloutStyleUpdating,$.proxy(this._fireCategoryChart_calloutStyleUpdating,this))}},_maskSeriesOptions:function(seriesOpt,options){for(var i=0;i<seriesOpt;i++){if(seriesOpt[options[i]]!==undefined){delete seriesOpt[options[i]]}}},_fireCategoryChart_seriesAdded:function(chart,args){var seriesOpt=null;var beforeOpt=null;var opt={};var series=args.series();if(args.series()!==null){seriesOpt=$.ig.dvCommonWidget.prototype._flattenCommonSeriesOptions(series);beforeOpt=$.ig.dvCommonWidget.prototype._flattenCommonSeriesOptions(series);opt.series=seriesOpt}this._trigger("seriesAdded",null,opt);var mustSet=false;for(var p in seriesOpt){if(seriesOpt.hasOwnProperty(p)){if(beforeOpt[p]===undefined){mustSet=true;continue}if(seriesOpt[p]===beforeOpt[p]){delete seriesOpt[p]}else{mustSet=true}}}this._maskSeriesOptions(seriesOpt,["legend","showTooltip","tooltipTemplate","tileImagery","targetSeries","targetAxis","xAxis","yAxis","series","name"]);$.each(seriesOpt,function(key,value){if(!$.ig.dvCommonWidget.prototype._setCoreSeriesOption(series,key,value)){$.ig.dvCommonWidget.prototype._seriesSetOption(series,key,value)}})},_fireCategoryChart_seriesRemoved:function(chart,args){var seriesOpt=null;var opt={};if(args.series()!==null){seriesOpt=$.ig.dvCommonWidget.prototype._flattenCommonSeriesOptions(args.series());opt.series=seriesOpt}this._trigger("seriesRemoved",null,opt)},_setLegend:function(chart,value){var legend=this._legend;if(legend&&legend.data("igChartLegend")!==undefined){legend.igChartLegend("destroy");if(legend[0]._remove){legend.remove()}delete this._legend;chart.legend(null)}if(!value){return}if(typeof value==="string"){value={element:value}}legend=value.element;if(legend){legend=$("#"+legend)}value.owner=this;if(!legend||legend.length!==1){legend=$("<div />").insertAfter(this.element);legend[0]._remove=true}this._legend=legend;chart.legend(legend.igChartLegend(value).data("igChartLegend").legend)},_set_option:function(categoryChart,key,value){var result=false;switch(key){case"propertyChanged":case"seriesAdded":case"seriesRemoved":case"seriesPointerEnter":case"seriesPointerLeave":case"seriesPointerMove":case"seriesPointerDown":case"seriesPointerUp":case"calloutStyleUpdating":break;default:result=$.ui.igBaseChart.prototype._set_option.apply(this,arguments);break}switch(key){case"yAxisMinimumValue":case"yAxisMaximumValue":case"xAxisExtent":case"yAxisExtent":categoryChart[key](value);return true;case"legend":this._setLegend(categoryChart,value);return true;case"tooltipTemplate":result=false;break;case"markerTypes":var markerTypes=new $.ig.MarkerTypeCollection;for(i=0;i<value.length;i++){var markerType=$.ig.util.getValue($.ig.Enum.prototype.parse($.ig.MarkerType.prototype.$type,value[i],true));markerTypes.add(markerType)}categoryChart.markerTypes(markerTypes);return true;case"transitionEasingFunction":value=$.ig.util.getEasingFunction(value);break}if(result){return true}switch(key){case"tooltipTemplate":categoryChart.tooltipTemplate(value);return true;case"tooltipTemplates":categoryChart.tooltipTemplates(value);return true;case"pixelScalingRatio":categoryChart.pixelScalingRatio(value);return true;case"titleLeftMargin":categoryChart.titleLeftMargin(value);return true;case"titleRightMargin":categoryChart.titleRightMargin(value);return true;case"titleTopMargin":categoryChart.titleTopMargin(value);return true;case"titleBottomMargin":categoryChart.titleBottomMargin(value);return true;case"subtitleLeftMargin":categoryChart.subtitleLeftMargin(value);return true;case"subtitleTopMargin":categoryChart.subtitleTopMargin(value);return true;case"subtitleRightMargin":categoryChart.subtitleRightMargin(value);return true;case"subtitleBottomMargin":categoryChart.subtitleBottomMargin(value);return true;case"subtitleTextColor":if(value==null){categoryChart.subtitleTextColor(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.subtitleTextColor($tempBrush)}return true;case"titleTextColor":if(value==null){categoryChart.titleTextColor(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.titleTextColor($tempBrush)}return true;case"leftMargin":categoryChart.leftMargin(value);return true;case"topMargin":categoryChart.topMargin(value);return true;case"rightMargin":categoryChart.rightMargin(value);return true;case"bottomMargin":categoryChart.bottomMargin(value);return true;case"transitionDuration":categoryChart.transitionDuration(value);return true;case"transitionEasingFunction":categoryChart.transitionEasingFunction(value);return true;case"createWrappedTooltip":categoryChart.createWrappedTooltip(value);return true;case"widget":categoryChart.widget(value);return true;case"subtitleTextStyle":categoryChart.subtitleTextStyle(value);return true;case"titleTextStyle":categoryChart.titleTextStyle(value);return true;case"itemsSource":categoryChart.itemsSource(value);return true;case"includedProperties":categoryChart.includedProperties(value);return true;case"excludedProperties":categoryChart.excludedProperties(value);return true;case"brushes":var isRGB=true,val=value?value[0]:null;if(typeof val=="string"&&val=="HSV"||val=="RGB"){if(value[0]=="HSV"){isRGB=false}value=value.slice(1)}var $tempBrushCollection=new $.ig.BrushCollection;for(var i=0;value&&i<value.length;i++){var $tempBrush=$.ig.Brush.prototype.create(value[i]);$tempBrushCollection.add($tempBrush)}categoryChart.brushes($tempBrushCollection);return true;case"outlines":var isRGB=true,val=value?value[0]:null;if(typeof val=="string"&&val=="HSV"||val=="RGB"){if(value[0]=="HSV"){isRGB=false}value=value.slice(1)}var $tempBrushCollection=new $.ig.BrushCollection;for(var i=0;value&&i<value.length;i++){var $tempBrush=$.ig.Brush.prototype.create(value[i]);$tempBrushCollection.add($tempBrush)}categoryChart.outlines($tempBrushCollection);return true;case"legend":categoryChart.legend(value);return true;case"isHorizontalZoomEnabled":categoryChart.isHorizontalZoomEnabled(value);return true;case"isVerticalZoomEnabled":categoryChart.isVerticalZoomEnabled(value);return true;case"isSeriesHighlightingEnabled":categoryChart.isSeriesHighlightingEnabled(value);return true;case"windowRect":categoryChart.windowRect(new $.ig.Rect(0,value.left,value.top,value.width,value.height));return true;case"title":categoryChart.title(value);return true;case"subtitle":categoryChart.subtitle(value);return true;case"titleAlignment":switch(value){case"left":categoryChart.titleAlignment(0);break;case"center":categoryChart.titleAlignment(1);break;case"right":categoryChart.titleAlignment(2);break;case"stretch":categoryChart.titleAlignment(3);break}return true;case"subtitleAlignment":switch(value){case"left":categoryChart.subtitleAlignment(0);break;case"center":categoryChart.subtitleAlignment(1);break;case"right":categoryChart.subtitleAlignment(2);break;case"stretch":categoryChart.subtitleAlignment(3);break}return true;case"unknownValuePlotting":switch(value){case"linearInterpolate":categoryChart.unknownValuePlotting(0);break;case"dontPlot":categoryChart.unknownValuePlotting(1);break}return true;case"resolution":categoryChart.resolution(value);return true;case"thickness":categoryChart.thickness(value);return true;case"markerTypes":categoryChart.markerTypes(value);return true;case"markerBrushes":var isRGB=true,val=value?value[0]:null;if(typeof val=="string"&&val=="HSV"||val=="RGB"){if(value[0]=="HSV"){isRGB=false}value=value.slice(1)}var $tempBrushCollection=new $.ig.BrushCollection;for(var i=0;value&&i<value.length;i++){var $tempBrush=$.ig.Brush.prototype.create(value[i]);$tempBrushCollection.add($tempBrush)}categoryChart.markerBrushes($tempBrushCollection);return true;case"markerOutlines":var isRGB=true,val=value?value[0]:null;if(typeof val=="string"&&val=="HSV"||val=="RGB"){if(value[0]=="HSV"){isRGB=false}value=value.slice(1)}var $tempBrushCollection=new $.ig.BrushCollection;for(var i=0;value&&i<value.length;i++){var $tempBrush=$.ig.Brush.prototype.create(value[i]);$tempBrushCollection.add($tempBrush)}categoryChart.markerOutlines($tempBrushCollection);return true;case"markerMaxCount":categoryChart.markerMaxCount(value);return true;case"animateSeriesWhenAxisRangeChanges":categoryChart.animateSeriesWhenAxisRangeChanges(value);return true;case"trendLineBrushes":var isRGB=true,val=value?value[0]:null;if(typeof val=="string"&&val=="HSV"||val=="RGB"){if(value[0]=="HSV"){isRGB=false}value=value.slice(1)}var $tempBrushCollection=new $.ig.BrushCollection;for(var i=0;value&&i<value.length;i++){var $tempBrush=$.ig.Brush.prototype.create(value[i]);$tempBrushCollection.add($tempBrush)}categoryChart.trendLineBrushes($tempBrushCollection);return true;case"trendLineType":switch(value){case"none":categoryChart.trendLineType(0);break;case"linearFit":categoryChart.trendLineType(1);break;case"quadraticFit":categoryChart.trendLineType(2);break;case"cubicFit":categoryChart.trendLineType(3);break;case"quarticFit":categoryChart.trendLineType(4);break;case"quinticFit":categoryChart.trendLineType(5);break;case"logarithmicFit":categoryChart.trendLineType(6);break;case"exponentialFit":categoryChart.trendLineType(7);break;case"powerLawFit":categoryChart.trendLineType(8);break;case"simpleAverage":categoryChart.trendLineType(9);break;case"exponentialAverage":categoryChart.trendLineType(10);break;case"modifiedAverage":categoryChart.trendLineType(11);break;case"cumulativeAverage":categoryChart.trendLineType(12);break;case"weightedAverage":categoryChart.trendLineType(13);break}return true;case"trendLineThickness":categoryChart.trendLineThickness(value);return true;case"alignsGridLinesToPixels":categoryChart.alignsGridLinesToPixels(value);return true;case"trendLinePeriod":categoryChart.trendLinePeriod(value);return true;case"toolTipType":switch(value){case"default":categoryChart.toolTipType(0);break;case"item":categoryChart.toolTipType(1);break;case"category":categoryChart.toolTipType(2);break;case"none":categoryChart.toolTipType(3);break}return true;case"crosshairsDisplayMode":switch(value){case"default":categoryChart.crosshairsDisplayMode(0);break;case"none":categoryChart.crosshairsDisplayMode(1);break;case"horizontal":categoryChart.crosshairsDisplayMode(2);break;case"vertical":categoryChart.crosshairsDisplayMode(3);break;case"both":categoryChart.crosshairsDisplayMode(4);break}return true;case"crosshairsSnapToData":categoryChart.crosshairsSnapToData(value);return true;case"crosshairsAnnotationEnabled":categoryChart.crosshairsAnnotationEnabled(value);return true;case"finalValueAnnotationsVisible":categoryChart.finalValueAnnotationsVisible(value);return true;case"calloutsVisible":categoryChart.calloutsVisible(value);return true;case"calloutStyleUpdatingEventEnabled":categoryChart.calloutStyleUpdatingEventEnabled(value);return true;case"calloutsItemsSource":categoryChart.calloutsItemsSource(value);return true;case"calloutsXMemberPath":categoryChart.calloutsXMemberPath(value);return true;case"calloutsYMemberPath":categoryChart.calloutsYMemberPath(value);return true;case"calloutsLabelMemberPath":categoryChart.calloutsLabelMemberPath(value);return true;case"calloutsContentMemberPath":categoryChart.calloutsContentMemberPath(value);return true;case"xAxisFormatLabel":categoryChart.xAxisFormatLabel(value);return true;case"yAxisFormatLabel":categoryChart.yAxisFormatLabel(value);return true;case"xAxisLabelLeftMargin":categoryChart.xAxisLabelLeftMargin(value);return true;case"xAxisLabelTopMargin":categoryChart.xAxisLabelTopMargin(value);return true;case"xAxisLabelRightMargin":categoryChart.xAxisLabelRightMargin(value);return true;case"xAxisLabelBottomMargin":categoryChart.xAxisLabelBottomMargin(value);return true;case"yAxisLabelLeftMargin":categoryChart.yAxisLabelLeftMargin(value);return true;case"yAxisLabelTopMargin":categoryChart.yAxisLabelTopMargin(value);return true;case"yAxisLabelRightMargin":categoryChart.yAxisLabelRightMargin(value);return true;case"yAxisLabelBottomMargin":categoryChart.yAxisLabelBottomMargin(value);return true;case"xAxisLabelTextColor":if(value==null){categoryChart.xAxisLabelTextColor(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.xAxisLabelTextColor($tempBrush)}return true;case"yAxisLabelTextColor":if(value==null){categoryChart.yAxisLabelTextColor(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.yAxisLabelTextColor($tempBrush)}return true;case"xAxisTitleMargin":categoryChart.xAxisTitleMargin(value);return true;case"yAxisTitleMargin":categoryChart.yAxisTitleMargin(value);return true;case"xAxisTitleLeftMargin":categoryChart.xAxisTitleLeftMargin(value);return true;case"yAxisTitleLeftMargin":categoryChart.yAxisTitleLeftMargin(value);return true;case"xAxisTitleTopMargin":categoryChart.xAxisTitleTopMargin(value);return true;case"yAxisTitleTopMargin":categoryChart.yAxisTitleTopMargin(value);return true;case"xAxisTitleRightMargin":categoryChart.xAxisTitleRightMargin(value);return true;case"yAxisTitleRightMargin":categoryChart.yAxisTitleRightMargin(value);return true;case"xAxisTitleBottomMargin":categoryChart.xAxisTitleBottomMargin(value);return true;case"yAxisTitleBottomMargin":categoryChart.yAxisTitleBottomMargin(value);return true;case"xAxisTitleTextColor":if(value==null){categoryChart.xAxisTitleTextColor(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.xAxisTitleTextColor($tempBrush)}return true;case"yAxisTitleTextColor":if(value==null){categoryChart.yAxisTitleTextColor(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.yAxisTitleTextColor($tempBrush)}return true;case"xAxisLabelTextStyle":categoryChart.xAxisLabelTextStyle(value);return true;case"yAxisLabelTextStyle":categoryChart.yAxisLabelTextStyle(value);return true;case"xAxisTitleTextStyle":categoryChart.xAxisTitleTextStyle(value);return true;case"yAxisTitleTextStyle":categoryChart.yAxisTitleTextStyle(value);return true;case"xAxisLabel":categoryChart.xAxisLabel(value);return true;case"yAxisLabel":categoryChart.yAxisLabel(value);return true;case"xAxisMajorStroke":if(value==null){categoryChart.xAxisMajorStroke(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.xAxisMajorStroke($tempBrush)}return true;case"yAxisMajorStroke":if(value==null){categoryChart.yAxisMajorStroke(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.yAxisMajorStroke($tempBrush)}return true;case"xAxisMajorStrokeThickness":categoryChart.xAxisMajorStrokeThickness(value);return true;case"yAxisMajorStrokeThickness":categoryChart.yAxisMajorStrokeThickness(value);return true;case"xAxisMinorStrokeThickness":categoryChart.xAxisMinorStrokeThickness(value);return true;case"yAxisMinorStrokeThickness":categoryChart.yAxisMinorStrokeThickness(value);return true;case"xAxisStrip":if(value==null){categoryChart.xAxisStrip(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.xAxisStrip($tempBrush)}return true;case"yAxisStrip":if(value==null){categoryChart.yAxisStrip(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.yAxisStrip($tempBrush)}return true;case"xAxisStroke":if(value==null){categoryChart.xAxisStroke(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.xAxisStroke($tempBrush)}return true;case"yAxisStroke":if(value==null){categoryChart.yAxisStroke(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.yAxisStroke($tempBrush)}return true;case"xAxisStrokeThickness":categoryChart.xAxisStrokeThickness(value);return true;case"yAxisStrokeThickness":categoryChart.yAxisStrokeThickness(value);return true;case"xAxisTickLength":categoryChart.xAxisTickLength(value);return true;case"yAxisTickLength":categoryChart.yAxisTickLength(value);return true;case"xAxisTickStroke":if(value==null){categoryChart.xAxisTickStroke(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.xAxisTickStroke($tempBrush)}return true;case"yAxisTickStroke":if(value==null){categoryChart.yAxisTickStroke(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.yAxisTickStroke($tempBrush)}return true;case"xAxisTickStrokeThickness":categoryChart.xAxisTickStrokeThickness(value);return true;case"yAxisTickStrokeThickness":categoryChart.yAxisTickStrokeThickness(value);return true;case"xAxisTitle":categoryChart.xAxisTitle(value);return true;case"yAxisTitle":categoryChart.yAxisTitle(value);return true;case"xAxisMinorStroke":if(value==null){categoryChart.xAxisMinorStroke(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.xAxisMinorStroke($tempBrush)}return true;case"yAxisMinorStroke":if(value==null){categoryChart.yAxisMinorStroke(null)}else{var $tempBrush=$.ig.Brush.prototype.create(value);categoryChart.yAxisMinorStroke($tempBrush)}return true;case"xAxisLabelAngle":categoryChart.xAxisLabelAngle(value);return true;case"yAxisLabelAngle":categoryChart.yAxisLabelAngle(value);return true;case"xAxisExtent":categoryChart.xAxisExtent(value);return true;case"yAxisExtent":categoryChart.yAxisExtent(value);return true;case"xAxisTitleAngle":categoryChart.xAxisTitleAngle(value);return true;case"yAxisTitleAngle":categoryChart.yAxisTitleAngle(value);return true;case"xAxisInverted":categoryChart.xAxisInverted(value);return true;case"yAxisInverted":categoryChart.yAxisInverted(value);return true;case"xAxisTitleAlignment":switch(value){case"left":categoryChart.xAxisTitleAlignment(0);break;case"center":categoryChart.xAxisTitleAlignment(1);break;case"right":categoryChart.xAxisTitleAlignment(2);break;case"stretch":categoryChart.xAxisTitleAlignment(3);break}return true;case"yAxisTitleAlignment":switch(value){case"top":categoryChart.yAxisTitleAlignment(0);break;case"center":categoryChart.yAxisTitleAlignment(1);break;case"bottom":categoryChart.yAxisTitleAlignment(2);break;case"stretch":categoryChart.yAxisTitleAlignment(3);break}return true;case"xAxisLabelHorizontalAlignment":switch(value){case"left":categoryChart.xAxisLabelHorizontalAlignment(0);break;case"center":categoryChart.xAxisLabelHorizontalAlignment(1);break;case"right":categoryChart.xAxisLabelHorizontalAlignment(2);break;case"stretch":categoryChart.xAxisLabelHorizontalAlignment(3);break}return true;case"yAxisLabelHorizontalAlignment":switch(value){case"left":categoryChart.yAxisLabelHorizontalAlignment(0);break;case"center":categoryChart.yAxisLabelHorizontalAlignment(1);break;case"right":categoryChart.yAxisLabelHorizontalAlignment(2);break;case"stretch":categoryChart.yAxisLabelHorizontalAlignment(3);break}return true;case"xAxisLabelVerticalAlignment":switch(value){case"top":categoryChart.xAxisLabelVerticalAlignment(0);break;case"center":categoryChart.xAxisLabelVerticalAlignment(1);break;case"bottom":categoryChart.xAxisLabelVerticalAlignment(2);break;case"stretch":categoryChart.xAxisLabelVerticalAlignment(3);break}return true;case"yAxisLabelVerticalAlignment":switch(value){case"top":categoryChart.yAxisLabelVerticalAlignment(0);break;case"center":categoryChart.yAxisLabelVerticalAlignment(1);break;case"bottom":categoryChart.yAxisLabelVerticalAlignment(2);break;case"stretch":categoryChart.yAxisLabelVerticalAlignment(3);break}return true;case"xAxisLabelVisibility":switch(value){case"visible":categoryChart.xAxisLabelVisibility(0);break;case"collapsed":categoryChart.xAxisLabelVisibility(1);break}return true;case"yAxisLabelVisibility":switch(value){case"visible":categoryChart.yAxisLabelVisibility(0);break;case"collapsed":categoryChart.yAxisLabelVisibility(1);break}return true;case"yAxisLabelLocation":switch(value){case"outsideTop":categoryChart.yAxisLabelLocation(0);break;case"outsideBottom":categoryChart.yAxisLabelLocation(1);break;case"outsideLeft":categoryChart.yAxisLabelLocation(2);break;case"outsideRight":categoryChart.yAxisLabelLocation(3);break;case"insideTop":categoryChart.yAxisLabelLocation(4);break;case"insideBottom":categoryChart.yAxisLabelLocation(5);break;case"insideLeft":categoryChart.yAxisLabelLocation(6);break;case"insideRight":categoryChart.yAxisLabelLocation(7);break}return true;case"transitionInDuration":categoryChart.transitionInDuration(value);return true;case"transitionInEasingFunction":categoryChart.transitionInEasingFunction(value);return true;case"chartType":switch(value){case"line":categoryChart.chartType(0);break;case"area":categoryChart.chartType(1);break;case"column":categoryChart.chartType(2);break;case"point":categoryChart.chartType(3);break;case"stepLine":categoryChart.chartType(4);break;case"stepArea":categoryChart.chartType(5);break;case"spline":categoryChart.chartType(6);break;case"splineArea":categoryChart.chartType(7);break;case"waterfall":categoryChart.chartType(8);break;case"auto":categoryChart.chartType(9);break}return true;case"markerCollisionAvoidance":switch(value){case"none":categoryChart.markerCollisionAvoidance(0);break;case"omit":categoryChart.markerCollisionAvoidance(1);break}return true;case"isTransitionInEnabled":categoryChart.isTransitionInEnabled(value);return true;case"transitionInMode":switch(value){case"auto":categoryChart.transitionInMode(0);break;case"fromZero":categoryChart.transitionInMode(1);break;case"sweepFromLeft":categoryChart.transitionInMode(2);break;case"sweepFromRight":categoryChart.transitionInMode(3);break;case"sweepFromTop":categoryChart.transitionInMode(4);break;case"sweepFromBottom":categoryChart.transitionInMode(5);break;case"sweepFromCenter":categoryChart.transitionInMode(6);break;case"accordionFromLeft":categoryChart.transitionInMode(7);break;case"accordionFromRight":categoryChart.transitionInMode(8);break;case"accordionFromTop":categoryChart.transitionInMode(9);break;case"accordionFromBottom":categoryChart.transitionInMode(10);break;case"expand":categoryChart.transitionInMode(11);break;case"sweepFromCategoryAxisMinimum":categoryChart.transitionInMode(12);break;case"sweepFromCategoryAxisMaximum":categoryChart.transitionInMode(13);break;case"sweepFromValueAxisMinimum":categoryChart.transitionInMode(14);break;case"sweepFromValueAxisMaximum":categoryChart.transitionInMode(15);break;case"accordionFromCategoryAxisMinimum":categoryChart.transitionInMode(16);break;case"accordionFromCategoryAxisMaximum":categoryChart.transitionInMode(17);break;case"accordionFromValueAxisMinimum":categoryChart.transitionInMode(18);break;case"accordionFromValueAxisMaximum":categoryChart.transitionInMode(19);break}return true;case"transitionInSpeedType":switch(value){case"auto":categoryChart.transitionInSpeedType(0);break;case"normal":categoryChart.transitionInSpeedType(1);break;case"valueScaled":categoryChart.transitionInSpeedType(2);break;case"indexScaled":categoryChart.transitionInSpeedType(3);break;case"random":categoryChart.transitionInSpeedType(4);break}return true;case"xAxisInterval":categoryChart.xAxisInterval(value);return true;case"xAxisMinorInterval":categoryChart.xAxisMinorInterval(value);return true;case"xAxisGap":categoryChart.xAxisGap(value);return true;case"xAxisOverlap":categoryChart.xAxisOverlap(value);return true;case"yAxisInterval":categoryChart.yAxisInterval(value);return true;case"yAxisIsLogarithmic":categoryChart.yAxisIsLogarithmic(value);return true;case"yAxisLogarithmBase":categoryChart.yAxisLogarithmBase(value);return true;case"yAxisMinimumValue":categoryChart.yAxisMinimumValue(value);return true;case"yAxisMaximumValue":categoryChart.yAxisMaximumValue(value);return true;case"yAxisMinorInterval":categoryChart.yAxisMinorInterval(value);return true;case"negativeBrushes":var isRGB=true,val=value?value[0]:null;if(typeof val=="string"&&val=="HSV"||val=="RGB"){if(value[0]=="HSV"){isRGB=false}value=value.slice(1)}var $tempBrushCollection=new $.ig.BrushCollection;for(var i=0;value&&i<value.length;i++){var $tempBrush=$.ig.Brush.prototype.create(value[i]);$tempBrushCollection.add($tempBrush)}categoryChart.negativeBrushes($tempBrushCollection);return true;case"negativeOutlines":var isRGB=true,val=value?value[0]:null;if(typeof val=="string"&&val=="HSV"||val=="RGB"){if(value[0]=="HSV"){isRGB=false}value=value.slice(1)}var $tempBrushCollection=new $.ig.BrushCollection;for(var i=0;value&&i<value.length;i++){var $tempBrush=$.ig.Brush.prototype.create(value[i]);$tempBrushCollection.add($tempBrush)}categoryChart.negativeOutlines($tempBrushCollection);return true;case"yAxisAbbreviateLargeNumbers":categoryChart.yAxisAbbreviateLargeNumbers(value);return true;case"isCategoryHighlightingEnabled":categoryChart.isCategoryHighlightingEnabled(value);return true;case"isItemHighlightingEnabled":categoryChart.isItemHighlightingEnabled(value);return true}},_setOption:function(key,val){var chart=this._chart,o=this.options;if(o[key]===val){return this}$.Widget.prototype._setOption.apply(this,arguments);this._set_option(chart,key,val);return this},_getValueKeyName:function(){return"valueMemberPath"},_getRemoteDataKeys:function(){var o=this.options;return[o.valueMemberPath]},_getNotifyResizeName:function(){return"notifyResized"},_createChart:function(){var result=new $.ig.CategoryChart;result.createWrappedTooltip(function(ele){var jEle=$(ele);var wrap=new $.ig.JQueryDomWrapper(jEle[0],jEle);return[wrap,jEle]});result.widget(this);return result},destroy:function(){this.widget=this._widget;$.ui.igBaseChart.prototype.destroy.apply(this)},_getWidgetName:function(){return""},id:function(){return this.element.length>0?this.element[0].id:""},_beforeInitialOptions:function(chart,elem){var elemWrapper=new $.ig.JQueryDomWrapper(elem[0],elem);var renderer=new $.ig.JQueryDomRenderer(elemWrapper,$.proxy(this._getLocaleValue,this));if(this.css.chart){elem.addClass(this.css.chart)}chart.provideContainer(renderer)},_provideContainer:function(chart,elem){},exportVisualData:function(){return this._chart.exportVisualData()},_getSeriesOpt:function(evtArgs){var result=$.extend(true,{},evtArgs._series);if(typeof result.name=="function")result.name=result.name();return result}});$.extend($.ui.igCategoryChart,{version:"19.1.20191.172"});return $.ui.igCategoryChart});