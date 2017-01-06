/*!@license
* Infragistics.Web.ClientUI Pivot Shared localization resources 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/

/*global jQuery */
(function ($) {
    $.ig = $.ig || {};

    if (!$.ig.PivotShared) {
        $.ig.PivotShared = {};

        $.extend($.ig.PivotShared, {
            locale: {
                invalidDataSource: "渡されたデータ ソースが null 値またはサポートされていません。",
                measureList: "メジャー",
                ok: "OK",
                cancel: "キャンセル",
                addToMeasures: "メジャーに追加",
                addToFilters: "フィルターに追加",
                addToColumns: "列に追加",
                addToRows: "行に追加"
            }
        });
    }
})(jQuery);

/*!@license
* Infragistics.Web.ClientUI Pivot Shared 16.1.20161.2145
*
* Copyright (c) 2011-2012 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on: 
*   jquery-1.9.1.js
*   jquery.ui.core.js
*   jquery.ui.widget.js
*   jquery.ui.mouse.js
*   jquery.ui.draggable.js
*   jquery.ui.droppable.js
*   infragistics.util.js
*   infragistics.datasource.js
*   infragistics.olapxmladatasource.js
*   infragistics.olapflatdatasource.js
*   infragistics.templating.js
*   infragistics.ui.shared.js
*   infragistics.ui.scroll.js
*   infragistics.ui.tree.js
*/
/*global jQuery */
(function ($) {
    var _aNull = function (val) { return val === null || val === undefined; },
        _draggable = $.ui.draggable.prototype.widgetFullName || $.ui.draggable.prototype.widgetName,
        _tree = $.ui.igTree.prototype.widgetFullName || $.ui.igTree.prototype.widgetName;

    $.ig = $.ig || {};
    $.ig.Pivot = $.ig.Pivot || {};
    $.ig.Pivot._pivotShared = $.ig.Pivot._pivotShared || {
        _isInstance: function (object, typeName) {
            return (typeName !== undefined && !_aNull(object) && object.$type && object.$type.name === typeName);
        },
        _const: {
            index: 0,
            dragCursorAt: {
                top: -10,
                left: 10
            },
            dragHelperMarkup: "<div class='ui-widget ui-corner-all ui-igpivot-draghelper'><p><span></span><strong>{0}</strong></p></div>",
            touchEvents: {
                // TFS 201975 - IE does not have touch events and also we want to support touch & mouse interactions at the same time  
                mousedown: "touchstart mousedown",
                mouseover: "mouseover",
                mouseout: "mouseover"
            },
            // N.A. - Fix for bug #134471 - Drag drop in pivot is not working correctly with IE8
            // Detect IE.
            ie: !!/msie [\w.]+/.exec(navigator.userAgent.toLowerCase())
        },
        _insertIndex: 0,
        _showLastLevelExpanders: true,
        _createDataSource: function (dataSource, dataSourceOptions) {
            var ds = null, dsOptions;
            if (!_aNull(dataSource) && (this._isInstance(dataSource, "OlapXmlaDataSource") || this._isInstance(dataSource, "OlapFlatDataSource"))) {
                ds = dataSource;
            } else if (!_aNull(dataSourceOptions)) {
                dsOptions = $.extend({}, dataSourceOptions);
                delete dsOptions.xmlaOptions;
                delete dsOptions.flatDataOptions;
                if (!_aNull(dataSourceOptions.xmlaOptions) && !_aNull(dataSourceOptions.xmlaOptions.serverUrl)) {
                    dsOptions = $.extend(dsOptions, dataSourceOptions.xmlaOptions);
                    ds = new $.ig.OlapXmlaDataSource(dsOptions);
                } else if (!_aNull(dataSourceOptions.flatDataOptions) && (!_aNull(dataSourceOptions.flatDataOptions.dataSource) || !_aNull(dataSourceOptions.flatDataOptions.dataSourceUrl))) {
                    dsOptions = $.extend(dsOptions, dataSourceOptions.flatDataOptions);
                    ds = new $.ig.OlapFlatDataSource(dsOptions);
                }
            }

            //if (_aNull(ds)) {
            //    throw new Error($.ig.PivotShared.locale.invalidDataSource);
            //}

            return ds;
        },
        _addingKpiMetadataItems: function (dataSource, name) {
                var kpi, kpiMeasure, kpiValue, kpiGoal, kpiStatus, kpiTrend, kpiWeight, item;

                kpi = dataSource.getCoreElement(
                    function (el) { return el.uniqueName() === name; },
                    $.ig.Kpi.prototype.getType()
                );

                item = [];

                kpiValue = kpi.kpiValue();
                kpiGoal = kpi.kpiGoal();
                kpiStatus = kpi.kpiStatus();
                kpiTrend = kpi.kpiTrend();
                kpiWeight = kpi.kpiWeight();
                
                if (null !== kpiValue && "" !== kpiValue) {
                    kpiMeasure = dataSource.getCoreElement(
                        function (el) { return el.uniqueName() === kpiValue; },
                        $.ig.Measure.prototype.getType()
                    );

                    if(null === kpiMeasure) {
                        kpiMeasure = dataSource.getCoreElement(
                            function (el) { return el.uniqueName() === kpiValue; },
                            $.ig.KpiMeasure.prototype.getType()
                        );                        
                    }

                    item.push(kpiMeasure);
                }

                if (null !== kpiGoal && "" !== kpiGoal) {
                    kpiMeasure = dataSource.getCoreElement(
                        function (el) { return el.uniqueName() === kpiGoal; },
                        $.ig.Measure.prototype.getType()
                    );

                    if(null === kpiMeasure) {
                        kpiMeasure = dataSource.getCoreElement(
                            function (el) { return el.uniqueName() === kpiGoal; },
                            $.ig.KpiMeasure.prototype.getType()
                        );                        
                    }

                    item.push(kpiMeasure);
                }

                if (null !== kpiStatus && "" !== kpiStatus) {
                    kpiMeasure = dataSource.getCoreElement(
                        function (el) { return el.uniqueName() === kpiStatus; },
                        $.ig.KpiMeasure.prototype.getType()
                    );

                    item.push(kpiMeasure);
                }

                if (null !== kpiTrend && "" !== kpiTrend) {
                    kpiMeasure = dataSource.getCoreElement(
                        function (el) { return el.uniqueName() === kpiTrend; },
                        $.ig.KpiMeasure.prototype.getType()
                    );

                    item.push(kpiMeasure);
                }

                if (null !== kpiWeight && "" !== kpiWeight) {
                    kpiMeasure = dataSource.getCoreElement(
                        function (el) { return el.uniqueName() === kpiWeight; },
                        $.ig.KpiMeasure.prototype.getType()
                    );

                    item.push(kpiMeasure);
                }

                return item;
        },
        _getEvent: function (event) {
            if ($.ig.util.isTouchDevice()) {
                return this._const.touchEvents[event];
            }
            return event;
        },
        _makeDroppable: function (droppable) {
            // Make the current widget droppable,
            // so it deletes metadata items that are dropped on it.
            var $this = this;
            //if ($.ig.util.isTouchDevice()) TFS 202229 & 205976 – allowing drag drop on touch devices
			{
                droppable.droppable({
                    tolerance: "pointer",
                    accept: function (draggable) {
                        // N.A. - Fix for bug #134999.
                        // If the dragged element has class ui-igpivot-dragover it will not be accepted -
                        // this class it toggled when the dragged element moves over a drop area.

                        // Note that if the dragged element is a tree node, the droppable should not accept it.
                        // The droppable should only accept metadata items in order to execute a remove action
                        // on them.
                        return draggable.hasClass("ui-igpivot-dragover") === false &&
                            draggable.closest("li.ui-igtree-node").length === 0 &&
                            (draggable.hasClass("ui-igpivot-metadataitem") ||
                                draggable.find(".ui-igpivot-metadataitem:first").length > 0);
                    },
                    over: function (event, ui) {
                        var isValid = true, markup = $(ui.helper.html()), element = ui.draggable, typeName, name;

                        if (!element.hasClass("ui-igpivot-metadataitem")) {
                            element = element.find(".ui-igpivot-metadataitem:first");
                        }
                        if (element.length > 0) {
                            typeName = element.attr("data-type");
                            name = element.attr("data-name");
                        } else {
                            return false;
                        }

                        // N.A. - Fix related to bugs #132380 and #132383.
                        // The user's validation will now be executed upon over and drop.
                        if ($.isFunction($this.options.customMoveValidation)) {
                            isValid = $this.options.customMoveValidation.call($this.element, $this.widgetName, typeName, name);
                        }
                        if (ui.helper.hasClass("ui-igpivot-draghelper")) {
                            if (isValid) {
                                markup.find("span").removeClass("ui-icon-plus ui-icon-cancel").addClass("ui-icon-close").siblings("strong");
                                ui.helper
                                    .removeClass($this.css.dropIndicator)
                                    .addClass($this.css.invalidDropIndicator)
                                    .html(markup);
                            } else {
                                markup.find("span").removeClass("ui-icon-plus ui-icon-close").addClass("ui-icon-cancel").siblings("strong");
                                ui.helper
                                    .removeClass($this.css.dropIndicator)
                                    .addClass($this.css.invalidDropIndicator)
                                    .html(markup);
                            }
                        }
                    },
                    out: function (event, ui) {
                        var markup = $(ui.helper.html());
                        if (ui.helper.hasClass("ui-igpivot-draghelper")) {
                            markup.find("span").removeClass("ui-icon-close ui-icon-plus").addClass("ui-icon-cancel").siblings("strong");
                            ui.helper
                                .removeClass($this.css.dropIndicator)
                                .addClass($this.css.invalidDropIndicator)
                                .html(markup);
                        }
                    },
                    drop: function (event, ui) {
                        var element = ui.draggable, isValid = true, typeName, type, name, item, noCancel, location, dataRole;

                        // Detach from the draggable events in a safe way.
                        ui.draggable.unbind("." + $this.widgetName);

                        if (!element.hasClass("ui-igpivot-metadataitem")) {
                            element = element.find(".ui-igpivot-metadataitem:first");
                        }
                        if (element.length > 0) {
                            typeName = element.attr("data-type");
                            name = element.attr("data-name");
                        } else {
                            return false;
                        }

                        // N.A. - Fix related to bugs #132380 and #132383.
                        // The user's validation will now be executed upon over and drop.
                        if ($.isFunction($this.options.customMoveValidation)) {
                            dataRole = $(this).attr("data-role");
                            location = dataRole === undefined ? $this.widgetName : dataRole;
                            isValid = $this.options.customMoveValidation.call($this.element, location, typeName, name);
                        }
                        if (!isValid) {
                            return false;
                        }

                        switch (typeName) {
                            case $.ig.Dimension.prototype.getType().typeName():
                                type = $.ig.Dimension.prototype.getType();
                                break;
                            case $.ig.Hierarchy.prototype.getType().typeName():
                                type = $.ig.Hierarchy.prototype.getType();
                                break;
                            case $.ig.Measure.prototype.getType().typeName():
                                type = $.ig.Measure.prototype.getType();
                                break;
                            case $.ig.MeasureList.prototype.getType().typeName():
                                type = $.ig.MeasureList.prototype.getType();
                                break;
                            case $.ig.Kpi.prototype.getType().typeName():
                                type = $.ig.Kpi.prototype.getType();
                                break; 
                            case $.ig.KpiMeasure.prototype.getType().typeName():
                                type = $.ig.KpiMeasure.prototype.getType();
                                break;
                            default:
                                return false;
                        }
                        item = $this._ds.getCoreElement(
                            function (el) { return el.uniqueName() === name; },
                            type
                        );

                        if (item) {
                            noCancel = $this._triggerMetadataRemoving(event, element, item);
                            if (noCancel) {
                                // Directly call the remove function
                                // rather than checking each collection -
                                // it's faster.
                                $this._ds.removeFilterItem(item);
                                $this._ds.removeRowItem(item);
                                $this._ds.removeColumnItem(item);
                                $this._ds.removeMeasureItem(item);
                                $this._triggerMetadataRemoved(event, item);
                                $this._updateDataSource();
                                return true;
                            }
                        }
                        return false;
                    }
                });
            }
        },
        _createDropAreaOptions: function () {
            var $this = this,
                dropAreaOptions = {
                    // Make the droppable greedy, so ti won't interfere with the droppable beneath it.
                    greedy: true,
                    tolerance: "pointer",
                    activeClass: this.css.activeDropArea,
                    accept: function (draggable) { return $this._accept($(this), draggable); },
                    over: function (event, ui) { $this._onDraggableOver(event, ui); },
                    out: function (event, ui) { $this._onDraggableOut(event, ui); },
                    drop: function (event, ui) {
                        var element = ui.draggable, type, name;
                        if (!element.hasClass("ui-igpivot-metadataitem")) {
                            element = element.find(".ui-igpivot-metadataitem:first");
                        }
                        if (element.length > 0) {
                            type = element.attr("data-type");
                            name = element.attr("data-name");

                            if(!name) {
                                name = "null";
                            }

                            return $this._onDrop(event, ui, $(this), element, type, name);
                        }
                        return false;
                    }
                };

            return dropAreaOptions;
        },
        _onDataSourceCollectionChanged: function (collection, collectionChangedArgs, dropArea, isDisabled) {
            var action, items, i, length, name, filter, startingIndex, previousItem, destroyDraggable;

            action = collectionChangedArgs.action();
            switch (action) {
                case $.ig.NotifyCollectionChangedAction.prototype.add:
                    items = collectionChangedArgs.newItems().__inner; //toArray();
                    startingIndex = collectionChangedArgs.newStartingIndex();
                    if (startingIndex === 0) {
                        this._createMetadataElement(items[0], isDisabled, "prependTo", dropArea);
                    } else {
                        previousItem = dropArea.find(".ui-igpivot-metadataitem")[startingIndex - 1];
                        this._createMetadataElement(items[0], isDisabled, "insertAfter", previousItem);
                    }
                    break;
                case $.ig.NotifyCollectionChangedAction.prototype.remove:
                    items = collectionChangedArgs.oldItems().__inner; //toArray();
                    filter = function (ind, itemElement) {
                        return $(itemElement).attr("data-name") === name;
                    };
                    destroyDraggable = function (ind, el) {
                        var draggable = $(el).data(_draggable);
                        if (draggable) {
                            draggable.destroy();
                        }
                    };
                    for (i = 0, length = items.length; i < length; i++) {
                        if (this._isInstance(items[i], "MeasureList")) {
                            dropArea
                            .find(".ui-igpivot-metadataitem[data-type=" + $.ig.MeasureList.prototype.getType().typeName() + "]")
                            .each(destroyDraggable)
                            .remove();
                        } else {
                            name = items[i].uniqueName();
                            dropArea
                            .find(".ui-igpivot-metadataitem")
                            .filter(filter)
                            .each(destroyDraggable)
                            .remove();
                        }
                    }
                    break;
                case $.ig.NotifyCollectionChangedAction.prototype.reset:
                    destroyDraggable = function (ind, el) {
                        var draggable = $(el).data(_draggable);
                        if (draggable) {
                            draggable.destroy();
                        }
                    };
                    dropArea
                    .find(".ui-igpivot-metadataitem")
                    .each(destroyDraggable)
                    .remove();
                    break;
            }
        },
        _createMetadataElement: function (item, isDisabled, appendFunc, target) {
            var $this = this, dragAndDropSettings = this.options.dragAndDropSettings,
                metadataElement, metadataElementMarkup;

            metadataElementMarkup = "<li ";
            if (this._isInstance(item, "MeasureList") && item.caption() === null) {
                item.caption($.ig.PivotShared.locale.measureList);
            } else {
                metadataElementMarkup += "data-name='" + item.uniqueName() + "' ";
            }
            metadataElementMarkup += "title='" + item.caption() + "' data-type='" + item.getType().typeName() + "'>";
            if (this._isInstance(item, "Hierarchy") && !isDisabled) {
                metadataElementMarkup += "<span style='display:block; float:left;' class='ui-icon " + this.css.filterIcon + "'></span>";
            }
			
			if(this.widget()[0].className.indexOf("ui-igpivotdataselector ui-droppable") !== -1 && target !== undefined) {
				metadataElementMarkup += "<div style='float:left; display:block; width: calc(100%";
				if ("Hierarchy" === item.getType().typeName()) {
					metadataElementMarkup += " - 28px";
				} else {
					metadataElementMarkup += " - 16px";
				}
				
				metadataElementMarkup += ");overflow-x:hidden !important; text-overflow:ellipsis !important;'>";
					metadataElementMarkup += "<span data-role='caption'>";
					metadataElementMarkup += item.caption();
					metadataElementMarkup += "</span>";
				metadataElementMarkup += "</div>";
			} 
			else {
				metadataElementMarkup += "<span data-role='caption'>" + item.caption() + "</span>";
			}
			
            if (!isDisabled) {
                metadataElementMarkup += "<span class='ui-icon ui-icon-close'></span>";
            }
            metadataElementMarkup += "</li>";

            metadataElement = $(metadataElementMarkup).addClass(this.css.metadataItem);
            // Append the element to the DOM before binding to events - IE8 issues.
            metadataElement[appendFunc](target);

            if (!isDisabled) {
                metadataElement.find("span.ui-icon-pivot-smallfilter").click(function (event) {
                    $this._createFilterDropDown(event, this, item);
                    return false;
                });
                metadataElement.find("span.ui-icon-close").click(function (event) {
                    var noCancel = $this._triggerMetadataRemoving(event, metadataElement, item);
                    if (noCancel) {
                        // Directly call the remove function
                        // rather than checking each collection -
                        // it's faster.
                        $this._ds.removeFilterItem(item);
                        $this._ds.removeRowItem(item);
                        $this._ds.removeColumnItem(item);
                        $this._ds.removeMeasureItem(item);
                        $this._triggerMetadataRemoved(event, item);
                        $this._updateDataSource();
                        return false;
                    }
                    return false;
                });
                //if (!$.ig.util.isTouchDevice()) TFS 202229 & 205976 – allowing drag drop on touch devices
				{
                    metadataElement.draggable({
                        appendTo: dragAndDropSettings.appendTo,
                        containment: dragAndDropSettings.containment,
                        opacity: dragAndDropSettings.dragOpacity,
                        zIndex: dragAndDropSettings.zIndex,
                        cursorAt: this._const.dragCursorAt,
                        revert: false,
                        cancel: ".ui-icon",
                        helper: function (event) {
                            var target = $(event.target).closest(".ui-igpivot-metadataitem").find("span[data-role='caption']"),
                                markup = $($this._const.dragHelperMarkup.replace("{0}", target.text()));
                            markup.addClass($this.css.invalidDropIndicator)
                                .find("span")
                                .addClass("ui-icon");
                            return markup;
                        },
                        start: function (event, ui) { return $this._triggerDragStart(event, ui, item); },
                        drag: function (event, ui) { return $this._triggerDrag(event, ui, item); },
                        over: function (event, ui) { $this._onDraggableOver(event, ui); },
                        out: function (event, ui) { $this._onDraggableOut(event, ui); },
                        stop: function (event, ui) { $this._triggerDragStop(event, ui); }
                    });
                }
                metadataElement.click(function (event) {
                    $this._createMetadataItemDropDown(event, this, item);
                });
            }

            return metadataElement;
        },
        _accept: function (targetElement, draggable) {
            var target, typeName, isValid = false, isMeasureDimension, dimension, dataSource = this._ds;
            if (!draggable.hasClass("ui-igpivot-metadataitem")) {
                draggable = draggable.find(".ui-igpivot-metadataitem:first");
            }
            typeName = draggable.attr("data-type");
            target = targetElement.attr("data-role");
            $(".ui-igpivot-overlaydroparea").css("display", "block");

            dimension = dataSource.getCoreElement(function (el) {
                return el.dimensionType() === $.ig.DimensionType.prototype.measure; 
            }, $.ig.Dimension.prototype.getType());

            isMeasureDimension = draggable.text() === dimension.name() || draggable.text() === dimension.caption();

            if (typeName) {
                switch (target) {
                    case "rows":
                    case "columns":
                        isValid = typeName === $.ig.Hierarchy.prototype.getType().typeName() ||
                        (typeName === $.ig.Dimension.prototype.getType().typeName() && !isMeasureDimension)||
                        typeName === $.ig.MeasureList.prototype.getType().typeName();
                        break;
                    case "filters":
                        isValid = typeName === $.ig.Hierarchy.prototype.getType().typeName() ||
                        (typeName === $.ig.Dimension.prototype.getType().typeName() && !isMeasureDimension);
                        break;
                    case "measures":
                        isValid = (typeName === $.ig.Measure.prototype.getType().typeName() ||
                            typeName === $.ig.Kpi.prototype.getType().typeName() ||     
                            typeName === $.ig.KpiMeasure.prototype.getType().typeName() ||                       
                            isMeasureDimension) &&
                            "MeasureList" !== draggable.attr("data-type");
                        break;
                }

                // N.A. - Fix related to bugs #132380 and #132383.
                // The user's validation will now be executed upon over and drop.
                //if (isValid && $.isFunction(this.options.customMoveValidation)) {
                //    isValid = this.options.customMoveValidation.call(targetElement, draggable);
                //}
            }

            return isValid;
        },
        _onDraggableOver: function (event, ui) {
            var $this = this, isValid = true, markup = $(ui.helper.html()), element = ui.draggable, typeName, name;

            // N.A. - Fix for bug #134999.
            // Add a special class to the dragged element so it won't be accepted
            // by the underlying drop area, which perdorms a remove.
            ui.draggable.addClass("ui-igpivot-dragover");
            $(".ui-igpivot-overlaydroparea").css("display", "none");

            if (!element.hasClass("ui-igpivot-metadataitem")) {
                element = element.find(".ui-igpivot-metadataitem:first");
            }
            if (element.length > 0) {
                typeName = element.attr("data-type");
                name = element.attr("data-name");
            } else {
                return false;
            }

            // N.A. - Fix related to bugs #132380 and #132383.
            // The user's validation will now be executed upon over and drop.
            if ($.isFunction(this.options.customMoveValidation)) {
                isValid = this.options.customMoveValidation.call(this.element, $(event.target).attr("data-role"), typeName, name);
            }
            if (ui.helper.hasClass("ui-igpivot-draghelper")) {
                if (isValid) {
                    markup.find("span").removeClass("ui-icon-cancel ui-icon-close").addClass("ui-icon-plus").siblings("strong");
                    ui.helper
                        .removeClass(this.css.invalidDropIndicator)
                        .addClass(this.css.dropIndicator)
                        .html(markup);
                } else {
                    markup.find("span").removeClass("ui-icon-plus ui-icon-close").addClass("ui-icon-cancel").siblings("strong");
                    ui.helper
                        .removeClass(this.css.dropIndicator)
                        .addClass(this.css.invalidDropIndicator)
                        .html(markup);
                }
            }
            if (!isValid) {
                return false;
            }

            // Attach to the events of the draggable.
            ui.draggable
                .bind("drag." + this.widgetName, function (event1, ui1) {
                    $this._onDraggableDrag(event1, ui1);
                });
        },
        _onDraggableDrag: function (event, ui) {
            var target = $(event.originalEvent.target),
                insertItem = "<li class='" + this.css.insertItem + "'></li>";

            if (target.hasClass("ui-igpivot-insertitem")) {
                // N.A. - Fix for bug #134471 - Drag drop in pivot is not working correctly with IE8
                // If the browser is IE8, continue.
                // For some reason if this method returns, IE8's event reports that
                // no mouse button is pressed and jQuery UI triggers mouse up.
                if (!this._const.ie || (this._const.ie && document.documentMode !== 8)) {
                    return;
                }
            }

            // Remove the old insert line indicator.
            $(document).find(".ui-igpivot-insertitem").remove();

            // Check if the target is an element from the cotnent
            // of a metadata element.
            if (target.is("span")) {
                // Locate its parent metadata element.
                target = target.closest(".ui-igpivot-metadataitem");
            }
            // Do not add the line indicator for elements comming from outside the drop areas
            if (!(target.parent().hasClass("ui-igpivot-droparea") || target.hasClass("ui-igpivot-droparea"))) {
                return;
            }
            if (target.is(".ui-igpivot-metadataitem")) {
                if (this._shouldAppendToTarget(target, ui)) {
                    // The dragged element will be inserted after the target.
                    this._insertIndex = target.index() + 1;
                    $(insertItem).insertAfter(target);
                } else {
                    if (target.index() === 0) {
                        // The dragged element will be inserted at the beginning.
                        this._insertIndex = 0;
                        $(insertItem).insertBefore(target);
                    } else {
                        // The dragged element will be inserted before the target.
                        this._insertIndex = target.index();
                        $(insertItem).insertBefore(target);
                    }
                }
            } else if (target.is(".ui-igpivot-droparea")) {
                target = target.find(".ui-igpivot-metadataitem:last");
                // The dragged element will be inserted after the last 
                // metadata element.
                this._insertIndex = target.index() + 1;
                $(insertItem).insertAfter(target);
            }
        },
        _onDraggableOut: function (event, ui) {
            var markup = $(ui.helper.html()), invalidIcon;

            // N.A. - Fix for bug #134999.
            // Remove the special class.
            ui.draggable.removeClass("ui-igpivot-dragover");

            // If the dragged element is a tree node, the helper will display the cancel icon.
            invalidIcon = ui.draggable.closest("li.ui-igtree-node").length === 0 ? "ui-icon-close" : "ui-icon-cancel";

            if (ui.helper.hasClass("ui-igpivot-draghelper")) {
                markup.find("span").removeClass("ui-icon-plus").addClass(invalidIcon).siblings("strong");
                ui.helper
                    .removeClass(this.css.dropIndicator)
                    .addClass(this.css.invalidDropIndicator)
                    .html(markup);
            }

            // Detach from the draggable events in a safe way.
            ui.draggable.unbind("drag." + this.widgetName);

            // Remove the old insert line indicator.
            $(document).find(".ui-igpivot-insertitem").remove();

            // Reset the drag helper.
            this._insertIndex = 0;
        },
        _getDefaultHierarchy: function (dimensionName, name, dataSource, typeName) {
			var i, firstHierarchy, returnValue;
            dimensionName = name.substr(1, name.length-2);
            if (null !== dataSource.getDimension(name) && 
                $.ig.DimensionType.prototype.measure === dataSource.getDimension(name).dimensionType()){
                for (i = dataSource.metadataTree().children().length - 1; i >= 0; i--) {
                    if (name === dataSource.metadataTree().children()[i].item().uniqueName()) {
                        firstHierarchy = dataSource.metadataTree().children()[i];
                        while (null !== firstHierarchy.children()) {
                            firstHierarchy = firstHierarchy.children()[0];
                        }

                        name = firstHierarchy.item().uniqueName();
                        returnValue = dataSource.getMeasure(name);
                    }
                }
            } else if (typeName === $.ig.Dimension.prototype.getType().typeName()) {
                for (i = dataSource.metadataTree().children().length - 1; i >= 0; i--) {
                    if (dimensionName === dataSource.metadataTree().children()[i].item().name() ||
                        dimensionName === dataSource.metadataTree().children()[i].item().name().replace(' ', '')) {
                        if (typeof dataSource.metadataTree().children()[i].item().defaultHierarchy === 'function' &&
                            dataSource.metadataTree().children()[i].item().defaultHierarchy()) {
                            name = dataSource.metadataTree().children()[i].item().defaultHierarchy();
                        }
                        else if (null !== dataSource.metadataTree().children()[i].children()[0].item()) {
                            name = dataSource.metadataTree().children()[i].children()[0].item().uniqueName();
                        } else {
                            firstHierarchy = dataSource.metadataTree().children()[i].children()[0];
                            while(null === firstHierarchy.item()) {
                                firstHierarchy = firstHierarchy.children()[0];
                            }

                            name = firstHierarchy.item().uniqueName();
                        }
                        break;
                    }
                }

                returnValue = dataSource.getHierarchy(name);
            } else {
                returnValue = null;
            }
			
			return returnValue;
        },
        _addDroppedMeasure: function (item, dataSource, targetIndex) {
        	var i;
            if (item instanceof Array) {
                for (i = 0; i < item.length; i++) {
                    dataSource.addMeasureItem(item[i]);
                }
            } else if (item instanceof $.ig.Kpi) {
                item = this._addingKpiMetadataItems(dataSource, item.name());

                for(i = 0; i< item.length; i++) {
                    dataSource.removeMeasureItem(item[i]);
                    dataSource.addMeasureItem(item[i]);
                }
            } else {
                dataSource.insertMeasureItem(targetIndex, item);
            }
        },
        _onDrop: function (event, ui, targetElement, draggedElement, typeName, name) {
            var dataSource = this._ds, isValid = true, targetRole = $(targetElement).attr("data-role"), targetIndex = this._insertIndex,
                item, type, sourceRole, sourceIndex, filterMembers, i, isTreeLayout,
                noCancel, dimensionName;

            // Detach from the draggable events in a safe way.
            ui.draggable.unbind("." + this.widgetName);
            // If the drop is canceled, the insert line indicator
            // will not be removed in the onDraggableOut method.
            // Remove the old insert line indicator.
            $(document).find(".ui-igpivot-insertitem").remove();

            // N.A. - Fix related to bugs #132380 and #132383.
            // The user's validation will now be executed upon over and drop.
            if ($.isFunction(this.options.customMoveValidation)) {
                isValid = this.options.customMoveValidation.call(this.element, $(event.target).attr("data-role"), typeName, name);
            }
            if (!isValid) {
                return false;
            }

            switch (typeName) {
                case $.ig.Dimension.prototype.getType().typeName():
                    type = $.ig.Dimension.prototype.getType();
                    break;
                case $.ig.Hierarchy.prototype.getType().typeName():
                    type = $.ig.Hierarchy.prototype.getType();
                    break;
                case $.ig.Measure.prototype.getType().typeName():
                    type = $.ig.Measure.prototype.getType();
                    break;
                case $.ig.Kpi.prototype.getType().typeName():
                    type = $.ig.Kpi.prototype.getType();
                    break;
                case $.ig.KpiMeasure.prototype.getType().typeName():
                    type = $.ig.KpiMeasure.prototype.getType();
                    break;
                case $.ig.MeasureList.prototype.getType().typeName():
                    type = $.ig.MeasureList.prototype.getType();
                    break;
                default:
                    return false;
            }
            item = this._getDefaultHierarchy(dimensionName, name, dataSource, typeName);
            if (null === item) {
                item = dataSource.getCoreElement(
                    function (el) { return el.uniqueName() === name; },
                    type
                );
            }

            if (!item) {
                return false;
            }

            noCancel = this._triggerMetadataDropping(event, ui, targetElement, draggedElement, item, targetIndex);
            if (noCancel) {
                if ((sourceIndex = $.inArray(item, dataSource.filters())) > -1) {
                    sourceRole = "filters";
                } else if ((sourceIndex = $.inArray(item, dataSource.rowAxis())) > -1) {
                    sourceRole = "rows";
                } else if ((sourceIndex = $.inArray(item, dataSource.columnAxis())) > -1) {
                    sourceRole = "columns";
                } else if ((sourceIndex = $.inArray(item, dataSource.measures())) > -1 || item instanceof Array) {
                    sourceRole = "measures";
                } else {
                    sourceRole = null;
                }

                if (sourceRole !== null && sourceRole === targetRole && sourceIndex < targetIndex) {
                    targetIndex--;
                }

                isTreeLayout = false;
                $(".ui-igpivotgrid").each(function() {
                    if ($(this).data("igPivotGrid") && $(this).igPivotGrid("option", "rowHeadersLayout") === "tree") {
                        isTreeLayout = true;
                        return false;
                    }
                });
                // Create the new item element and update the data source.
                if (this._isInstance(item, "MeasureList")) {
                    // The drop are role should be 'rows' or 'columns'
                    // Move from rows to columns and from columns to rows.
                    dataSource.setMeasureListLocation(targetRole);
                    dataSource.setMeasureListIndex(isTreeLayout && targetRole === "rows" ? 0 : targetIndex);
                } else if (isTreeLayout && this._isInstance(item, "Measure") && dataSource.measures().length > 0 && dataSource.dataSource().measureListLocation() === 0) {
                    dataSource.setMeasureListIndex(0);

                    this._addDroppedMeasure(item, dataSource, targetIndex);
                } else {
                    if (this._isInstance(item, "Hierarchy")) {
                        // N.A. - Fix for bug #129528.
                        // Cache the filter bembers before the hierarchy is removed.
                        filterMembers = dataSource.getFilterMemberNames(name);
                    }

                    // TFS 188480 – adding a condition to check if there are any axes at all
                    if (isTreeLayout && (typeof dataSource.rowAxis().item(0) !== "undefined") && (typeof dataSource.rowAxis().item(0).measures === "function") && (targetIndex === 0)) {
                        targetIndex = 1;
                    }

                    switch (sourceRole) {
                        case "filters":
                            dataSource.removeFilterItem(item);
                            break;
                        case "rows":
                            dataSource.removeRowItem(item);
                            break;
                        case "columns":
                            dataSource.removeColumnItem(item);
                            break;
                        case "measures":
                            if (item instanceof Array) {
                                for (i = 0; i < item.length; i++) {
                                    dataSource.removeMeasureItem(item[i]);
                                }
                            } else {
                            dataSource.removeMeasureItem(item);
                            }
                            
                            break;
                    }

                    switch (targetRole) {
                        case "filters":
                            dataSource.insertFilterItem(targetIndex, item);
                            break;
                        case "rows":
                            dataSource.insertRowItem(targetIndex, item);
                            break;
                        case "columns":
                            dataSource.insertColumnItem(targetIndex, item);
                            break;
                        case "measures":
                            this._addDroppedMeasure(item, dataSource, targetIndex);
                            break;
                    }

                    if (this._isInstance(item, "Hierarchy")) {
                        // N.A. - Fix for bug #129528.
                        // Add the cached filter members.
                        for (i = 0; i < filterMembers.length; i++) {
                            dataSource.addFilterMember(name, filterMembers[i]);
                        }
                    }
                }

                // N.A. - Fix for bugs #132343 and #130174.
                // The bug is related to ticket #4239 for jQuery UI, which is fixed
                // for draggable, but not for its plugins.
                // Ticket: http://bugs.jqueryui.com/ticket/4239
                if (this.widgetName === "igPivotGrid" && ui.draggable.data(_draggable)) {
                    // Remove all "stop" handlers.
                    delete ui.draggable.data(_draggable).plugins.stop;
                }

                // Update the data source.
                this._updateDataSource();

                this._triggerMetadataDropped(event, ui, targetElement, draggedElement, item, targetIndex);
                return true;
            }

            return false;
        },
        _createMetadataItemDropDown: function (event, targetElement, metadataItem) {
            var $this = this, options = this.options, dataSource = this._ds, closestDropArea, dropDownParent, dropDownElement, menu,
                addMeasureList, addHierarchy, items, item, customValidation, dimensionName, defaultHierarchy, defaultHierarchyElement, i;

            // Determine if the metadata item is in a drop area.
            closestDropArea = $(targetElement).closest(".ui-igpivot-droparea").attr("data-role");

            //TFS 208105 - check if MeasureList metadataitem is clicked
            if ((closestDropArea === "columns" || closestDropArea === "rows") && metadataItem._caption === "Measures") {
                return;
            }

            if ($.isFunction(this.options.customMoveValidation)) {
                customValidation = function (location) {
                    return $this.options.customMoveValidation.call($this.element, location, metadataItem.getType().name, metadataItem.uniqueName() || undefined);
                };
            } else {
                customValidation = function () {
                    return true;
                };
            }

            dropDownParent = $(this.options.dropDownParent).first();

            dropDownElement = $("<div class='" + this.css.metadataItemDropDown + "'></div>");
            dropDownElement.data("efh", "1");
            dropDownElement
                .appendTo(dropDownParent)
                .bind(this._getEvent("mousedown"), function (event1) {
                    // Prevent the drop down from closing.
                    event1.stopPropagation();
                });

            // Add buttons to the drop down depending on the type and the
            // location of the metadata item.
            menu = $("<ul class='ui-widget'></ul>").appendTo(dropDownElement);

            defaultHierarchy = this._getDefaultHierarchy(dimensionName, metadataItem.uniqueName(), dataSource, metadataItem.getType().typeName());
            if (defaultHierarchy) {
                metadataItem = defaultHierarchy;
            }

            defaultHierarchyElement = $("li[data-name='" + metadataItem.uniqueName() + "']");
            if (0 < defaultHierarchyElement.length) {
                closestDropArea = defaultHierarchyElement.parent().attr("data-role");
            }

            if (this._isInstance(metadataItem, "Measure") || this._isInstance(metadataItem, "Kpi")) {
                if (!options.disableMeasuresDropArea && closestDropArea !== "measures" && customValidation("measures")) {
                    $("<li><span class='ui-icon ui-icon-pivot-measures'></span>" + $.ig.PivotShared.locale.addToMeasures + "</li>").appendTo(menu).click(function () {
                        if ($this._isInstance(metadataItem, "Kpi")) {
                            item = $this._addingKpiMetadataItems($this._ds, $(targetElement).attr('data-name'));

                            for(i = 0; i< item.length; i++) {
                                $this._ds.removeMeasureItem(item[i]);
                                $this._ds.addMeasureItem(item[i]);
                            }

                            dropDownElement.remove();
                            $this._updateDataSource();    
                        } else {
                        $this._ds.removeMeasureItem(metadataItem);
                        $this._ds.addMeasureItem(metadataItem);
                        dropDownElement.remove();
                        $this._updateDataSource();
                        }
                        
                    });
                }
            } else if (this._isInstance(metadataItem, "MeasureList")) {
                addMeasureList = function (location, index) {
                    $this._ds.setMeasureListLocation(location);
                    $this._ds.setMeasureListIndex(index);
                    dropDownElement.remove();
                    $this._updateDataSource();
                };
                if (!options.disableColumnsDropArea && closestDropArea !== "columns" && customValidation("columns")) {
                    $("<li><span class='ui-icon ui-icon-pivot-clumns'></span>" + $.ig.PivotShared.locale.addToColumns + "</li>").appendTo(menu).click(function () {
                        addMeasureList("columns", $this._ds.columnAxis().length);
                    });
                }
                if (!options.disableRowsDropArea && closestDropArea !== "rows" && customValidation("rows")) {
                    $("<li><span class='ui-icon ui-icon-pivot-rows'></span>" + $.ig.PivotShared.locale.addToRows + "</li>").appendTo(menu).click(function () {
                        addMeasureList("rows", $this._ds.rowAxis().length);
                    });
                }
            } else {
                addHierarchy = function (addMethod) {
                    var i, name = metadataItem.uniqueName(),
                    // N.A. - Fix for bug #129528.
                    // Cache the filter bembers before the hierarchy is removed.
                        filterMembers = $this._ds.getFilterMemberNames(name);

                    $this._ds.removeFilterItem(metadataItem);
                    $this._ds.removeColumnItem(metadataItem);
                    $this._ds.removeRowItem(metadataItem);

                    $this._ds[addMethod](metadataItem);

                    // N.A. - Fix for bug #129528.
                    // Add the cached filter members.
                    for (i = 0; i < filterMembers.length; i++) {
                        $this._ds.addFilterMember(name, filterMembers[i]);
                    }

                    dropDownElement.remove();
                    $this._updateDataSource();
                };

                if (!options.disableFiltersDropArea && closestDropArea !== "filters" && customValidation("filters")) {
                    $("<li><span class='ui-icon ui-icon-pivot-filters'></span>" + $.ig.PivotShared.locale.addToFilters + "</li>").appendTo(menu).click(function () {
                        addHierarchy("addFilterItem");
                    });
                }
                if (!options.disableColumnsDropArea && closestDropArea !== "columns" && customValidation("columns")) {
                    $("<li><span class='ui-icon ui-icon-pivot-columns'></span>" + $.ig.PivotShared.locale.addToColumns + "</li>").appendTo(menu).click(function () {
                        addHierarchy("addColumnItem");
                    });
                }
                if (!options.disableRowsDropArea && closestDropArea !== "rows" && customValidation("rows")) {
                    $("<li><span class='ui-icon ui-icon-pivot-rows'></span>" + $.ig.PivotShared.locale.addToRows + "</li>").appendTo(menu).click(function () {
                        addHierarchy("addRowItem");
                    });
                }
            }

            items = dropDownElement.find("li");
            if (items.length === 0) {
                dropDownElement.remove();
                return;
            }

            dropDownElement
                .css("position", "absolute")
                .position({
                    of: targetElement,
                    my: "left top",
                    at: "left bottom"
                });

            items
                .bind(this._getEvent("mouseover"), function () { $(this).addClass("ui-state-hover"); })
                .bind(this._getEvent("mouseout"), function () { $(this).removeClass("ui-state-hover"); });

            $(document).bind(this._getEvent("mousedown") + "." + this.widgetName, function () {
                dropDownElement.remove();
                $(document).unbind("." + $this.widgetName);
            });
        },
        _createFilterDropDown: function (event, targetElement, hierarchy) {
            var $this = this, hierarchyName, hierarchyFilterView, dropDownParent, dropDownElement,
                filterMembersTree, buttonContainer, removeFilterDropDown, noCancel;

            noCancel = this._triggerFilterDropDownOpening(event, hierarchy);
            if (noCancel) {
                hierarchyName = hierarchy.uniqueName();
                // Create an instance of the HierarchyFilterView
                hierarchyFilterView = new $.ig.HierarchyFilterView(hierarchy);

                dropDownParent = $(this.options.dropDownParent).first();

                dropDownElement = $("<div class='" + this.css.filterDropDown + "'></div>");
                dropDownElement.data("efh", "1");
                dropDownElement
                    .css("position", "absolute")
                    .attr("data-hierarchy", hierarchyName)
                    .appendTo(dropDownParent)
                    .position({
                        of: targetElement,
                        my: "left top",
                        at: "left bottom"
                    }).bind(this._getEvent("mousedown"), function (event1) {
                        // Prevent the drop down from closing.
                        event1.stopPropagation();
                    });
                // Create the element for the tree.
                filterMembersTree = $("<div class='" + this.css.filterMembers + "'></div>").appendTo(dropDownElement);
                // Append the container for the OK and Cancel buttons.
                buttonContainer = $("<div class='ui-igpivot-filterdropdown-buttoncontainer'></div>").appendTo(dropDownElement);
                // Prepare a function, which will remove the drop down.
                removeFilterDropDown = function (event1) { $this._removeFilterDropDown(event1, dropDownElement, hierarchy); };
                // OK button.
                $("<button></button>")
                    .attr("data-role", "ok")
                    .text($.ig.PivotShared.locale.ok)
                    .appendTo(buttonContainer).igButton().igButton("disable") // Disable the OK button.
                    .click(function (event1) {
                        $this._onFilterOk(event1, dropDownElement, hierarchyFilterView, hierarchy);
                    });
                // Cancel button.
                $("<button></button>")
                    .attr("data-role", "cancel")
                    .text($.ig.PivotShared.locale.cancel)
                    .appendTo(buttonContainer).igButton()
                    .click(removeFilterDropDown);

                $(document).bind(this._getEvent("mousedown") + "." + this.widgetName, removeFilterDropDown);

                this._loadFilterMembers(hierarchyFilterView, hierarchy, removeFilterDropDown);
                this._triggerFilterDropDownOpened(event, dropDownElement, hierarchy);
            }
        },
        _loadFilterMembers: function (hierarchyFilterView, hierarchy, removeFilterDropDown) {
            var $this = this, dataSource, hierarchyName, rootFilterMembers, filterMembers, filterMember, member, members, maxLevel, levels, levelMembers, rootLevel, i;

            dataSource = this._ds;
            hierarchyName = hierarchy.uniqueName();
            filterMembers = dataSource.getFilterMemberNames(hierarchyName);

            if (filterMembers.length > 0) {
                members = [];
                maxLevel = 0;
                // Get the member objects from the data source.
                for (i = 0; i < filterMembers.length; i++) {
                    member = dataSource.tryGetMember(filterMembers[i]);
                    if (member) {
                        members.push(member);
                        if (member.levelDepth() > maxLevel) {
                            // Find the deepest level that has filter members.
                            maxLevel = member.levelDepth();
                        }
                    }
                }
                // Get all levels above the deepest level.
                levels = dataSource.getCoreElements(function (el) {
                    return el.hierarchyUniqueName() === hierarchyName && el.depth() <= maxLevel;
                }, $.ig.Level.prototype.getType());
                // Enumerate the levels and add their members to the filter view.
                for (i = 0; i < levels.length; i++) {
                    levelMembers = dataSource.tryGetMembersForLevel(levels[i].uniqueName());
                    hierarchyFilterView.addFiltersForMembers(levelMembers);
                }
                // Get the root level members from the filter view and uncheck them.
                rootFilterMembers = hierarchyFilterView.getRootFilterMembers();
                rootFilterMembers = rootFilterMembers ? rootFilterMembers.__inner : [];
                for (i = 0; i < rootFilterMembers.length; i++) {
                    rootFilterMembers[i].isSelected($.ig.util.toNullable($.ig.Boolean.prototype.$type, false));
                }
                // Check the filter members.
                for (i = 0; i < members.length; i++) {
                    filterMember = hierarchyFilterView.tryGetFilterMember(members[i].uniqueName());
                    if (filterMember) {
                        filterMember.isSelected($.ig.util.toNullable($.ig.Boolean.prototype.$type, true));
                    }
                }
            }

            // Get the root level.
            rootLevel = dataSource.getCoreElement(function (el) {
                return el.depth() === 0 && el.hierarchyUniqueName() === hierarchyName;
            }, $.ig.Level.prototype.getType());
            // Initiate a load of the root filter members.
            this._ds.getMembersOfLevel(rootLevel.uniqueName())
                .done(function (m) { $this._onFilterMembersLoaded(hierarchyFilterView, m, hierarchy); })
                .fail(removeFilterDropDown);
        },
        _onFilterMembersLoaded: function (hierarchyFilterView, members, hierarchy) {
            var hierarchyName, dropDownElement, filterMembersTree;

            hierarchyName = hierarchy.uniqueName();
            // Add the members in the filter view.
            hierarchyFilterView.addFiltersForMembers(members);
            // Parse the filter members to objects suitable for the igTree.

            dropDownElement = $(".ui-igpivot-filterdropdown").filter(function () {
                // The hierarchy name contains characters, which cannot be
                // used in the selector [data-hierarchy=...].
                return $(this).attr("data-hierarchy") === hierarchyName;
            });
            // Make sure that the drop down exists.
            // If the user has closed it before the data has loaded
            // the drop down will not exist.
            if (dropDownElement.length > 0) {
                filterMembersTree = $(dropDownElement[0]).find(".ui-igpivot-filtermembers");
                // Initialize the tree.
                this._initTree(filterMembersTree, hierarchyFilterView);
            }
        },
        _getScrollBarWidth: function () {
            var el = $('<div style="width: 100px; height: 100px; position: absolute; top: -10000px; left: -10000px; overflow: scroll"></div>').appendTo($(document.body)), scrollWidth;
            scrollWidth = el[0].offsetWidth - el[0].clientWidth;
            el.remove();
            return scrollWidth;
        },
        _getElementSize: function (element) {
            var el = $('<div style="width: 5000px; height: 5000px; position: absolute; top: -10000px; left: -10000px;"></div>').appendTo($(document.body)), result, position, float;
            position = element.css('position');
            float = element.css('float');
            element.css({ 'position': 'relative', 'float': 'left' });
            element.appendTo(el);
            result = [element.width(), element.height()];
            element.css('position', position);
            element.css('float', float);
            element.detach();
            el.remove();
            return result;
        },
        _arrangeDropDown: function (onExpand) {
            var $this = this, fdd, fm, fmHeight, bcHeight,
                    ddTop, bTop, bHeight, bBottom, fmBottom, fmMaxHeight,
                    ddLeft, bLeft, bWidth, bRight, fmRight, fddMaxWidth,
                    windowHeight, windowInnerHeight,
                    parentOffset, elementSize, fddWidth, scrollTop, dropDownOffsetBottom;

            dropDownOffsetBottom = 30;
            // PP 4/2/2013 set max-width for '.ui-igpivot-filterdropdown' and 
            // calculate heigh and max-height for '.ui-igpivot-filterdropdown .ui-igpivot-filtermembers'
            fdd = $('.ui-igpivot-filterdropdown');
            fm = $('.ui-igpivot-filterdropdown .ui-igpivot-filtermembers');

            // calculate max-height
            fmHeight = fm.height();
            bcHeight = $('.ui-igpivot-filterdropdown-buttoncontainer').innerHeight();

            ddTop = parseInt(fdd.css('top').replace('px', ''), 10);
            bTop = $(document).scrollTop();

            windowHeight = $(window).height();
            windowInnerHeight = window.innerHeight;
            bHeight = windowHeight > windowInnerHeight ? windowHeight : windowInnerHeight;
            bBottom = bTop + bHeight;
            fmBottom = bBottom - bcHeight - dropDownOffsetBottom;
            fmMaxHeight = Math.floor(fmBottom - ddTop);

            // calculate max-width
            ddLeft = parseInt(fdd.css('left').replace('px', ''), 10);
            bLeft = $('body').css('left');
            if (bLeft === 'auto') {
                bLeft = 0;
            } else {
                bLeft = parseInt(bLeft.replace('px', ''), 10);
            }

            bWidth = $('body').width();
            bRight = bLeft + bWidth;
            fmRight = bRight;
            fddMaxWidth = Math.floor(fmRight - ddLeft);

            // set max-width and max-height
            fm.css('max-height', fmMaxHeight);
            fdd.css('max-width', fddMaxWidth);

            // ensure the parent drop down div has enough width space to host its tree content
            parentOffset = parseInt(fdd.css('padding-left').replace('px', ''), 10) +
                        parseInt(fdd.css('padding-right').replace('px', ''), 10) +
                        parseInt(fdd.css('border-left-width').replace('px', ''), 10) +
                        parseInt(fdd.css('border-right-width').replace('px', ''), 10);

            // measure the actual tree size
            scrollTop = fm[0].scrollTop;
            fm.detach();
            elementSize = $this._getElementSize(fm);
            fdd.prepend(fm);
            fm[0].scrollTop = scrollTop;

            // ensure we have the width to meet at least min-width value
            fddWidth = elementSize[0] + $this._getScrollBarWidth() + parseInt(fm.css('padding-right').replace('px', ''), 10) + parentOffset;
            fddWidth = Math.max(fddWidth, parseInt(fdd.css('min-width').replace('px', ''), 10));
            fdd.css('width', fddWidth);

            // the vertical scroll bar has appeared
            if (fmHeight > fmMaxHeight || onExpand === false) {
                if (fddWidth <= fddMaxWidth) {
                    fm.css('overflow-x', 'hidden');
                } else {
                    fm.css('overflow-x', 'auto');
                }
            }
        },
        _initTree: function (filterMembersTree, hierarchyFilterView) {
            var $this = this, dataSource = this._ds, checkChildNodes, parsedFilterMembers;

            $(filterMembersTree)
                .siblings(".ui-igpivot-filterdropdown-buttoncontainer")
                .children(".ui-igbutton[data-role=ok]")
                .igButton("enable");

            checkChildNodes = function (nodeElement, filterMembers) {
                var isSelected, state, i, filterMember;
                for (i = 0; i < filterMembers.length; i++) {
                    filterMember = filterMembers[i].filterMember;

                    isSelected = filterMember.isSelected();
                    if (isSelected.hasValue()) {
                        if (isSelected.value()) {
                            state = "on";
                        } else {
                            state = "off";
                        }
                    } else {
                        state = "partial";
                    }

                    $(nodeElement).find(".ui-igtree-node[data-value='" + filterMembers[i].uniqueName + "']")
                        .children("[data-role=checkbox]").attr("data-chk", state).children("span")
                        .removeClass("ui-state-disabled ui-igcheckbox-normal-on ui-igcheckbox-normal-partial ui-igcheckbox-normal-off")
                        .addClass("ui-igcheckbox-normal-" + state + (state === "partial" ? " ui-igcheckbox-normal-on ui-state-disabled" : ""));
                }
            };

            parsedFilterMembers = this._parseFilterMembers(hierarchyFilterView.getRootFilterMembers());
            // Create the filters tree.
            filterMembersTree.igTree({
                dataSource: parsedFilterMembers,
                loadOnDemand: true,
                checkboxMode: "triState",
                bindings: {
                    textKey: "caption",
                    valueKey: "uniqueName",
                    childDataProperty: "children"
                },

                nodeCollapsed: function () {
                    $this._arrangeDropDown(false);
                },

                nodeCheckstateChanged: function (event, ui) {
                    var okButton, isSelected;

                    okButton = $(this)
                        .siblings(".ui-igpivot-filterdropdown-buttoncontainer")
                        .children(".ui-igbutton[data-role=ok]");
                    if (ui.newCheckedNodes && ui.newCheckedNodes.length > 0) {
                        okButton.igButton("enable");
                    } else {
                        okButton.igButton("disable");
                    }

                    isSelected = $(ui.node.element).children("[data-role=checkbox]").attr("data-chk");
                    isSelected = isSelected === "partial" ? null : (isSelected === "on" ? true : false);
                    ui.node.data.filterMember.isSelected($.ig.util.toNullable($.ig.Boolean.prototype.$type, isSelected));
                    //ui.node.data.filterMember.isSelected(isSelected);
                },

                nodeExpanded: function(ect, ui) {
					$this._arrangeDropDown(true);
                    var i, collection, element;

                    if (!$.ig.Pivot._pivotShared._showLastLevelExpanders) {

                        element = ui.node.element;
                        collection = ui.node.data.children;

                        if (collection !== null && 0 < collection.length) {
                            for (i = 0; i < collection.length; i++) {
                                if (null !== collection[i].children && 0 === collection[i].children.length) {
                                    element
                                    .find(".ui-igtree-node")
                                    .eq(i)
                                    .find('span:first')
                                    .css('display', 'none');
                                }
                            }
                        }
                    }
                }
            })
            .css('overflow-x', 'hidden')
            .data(_tree)._executeUrlRequest = function (node) {
                var $$this = this, nodeData = this.nodeDataFor(node.attr("data-path")), ul, indicator, levels;

                ul = node.children("ul");
                // Create loading indicator space
                $("<li style='width:20px; height:20px;' data-role='loading'>&nbsp;</li>").appendTo(ul);
                ul.show();
                // K.D. December 19th, 2011 Bug #98217 Adding flag pointing that the loading indicator is instantiated by the tree
                indicator = ul.children("li").igLoading({ includeVerticalOffset: true, cssClass: "ui-igpivotgrid-tree-blockarea" }).data("igLoading").indicator();
                indicator.show();

                this._populatingNode = {
                    ul: ul,
                    node: node,
                    indicator: indicator
                };

                levels = dataSource.getCoreElements(function(el) {
                        return el.hierarchyUniqueName() === nodeData.member.hierarchyUniqueName();
                }, $.ig.Level.prototype.$type);
                
                $.ig.Pivot._pivotShared._showLastLevelExpanders = true;
                dataSource.getMembersOfMember(nodeData.member.uniqueName()).done(function (members) {
                    var parsedFilterMembers1;

                    if (members.item(0) && levels.length - 1 === members.item(0).levelDepth()) {
                        $.ig.Pivot._pivotShared._showLastLevelExpanders = false;
                    }

                    hierarchyFilterView.addFiltersForMembers(members);
                    parsedFilterMembers1 = $this._parseFilterMembers(nodeData.filterMember.children());
                    $$this._populateNodeData(true, "", {
                        // Create a dummy object with a data method to simulate igDataSource.
                        data: function () { return parsedFilterMembers1; }
                    });
                    // Update or remove the expansion idicator of the parent node.
                    $$this._updateParentState(node);
                    checkChildNodes(node, nodeData.children);
                    $this._triggerFilterMembersLoaded(node, parsedFilterMembers, parsedFilterMembers1);
                });
            };
            // Check the root nodes.
            checkChildNodes(filterMembersTree, parsedFilterMembers);

            this._triggerFilterMembersLoaded(filterMembersTree, parsedFilterMembers, parsedFilterMembers);
        },
        _parseFilterMembers: function (filterMembers) {
            var parsedFilterMembers, parsedFilterMember, filterMember, member, i;

            if (filterMembers === null) {
                return [];
            }

            filterMembers = filterMembers.__inner; //toArray();
            parsedFilterMembers = [];
            for (i = 0; i < filterMembers.length; i++) {
                filterMember = filterMembers[i];
                member = filterMember.member();

                parsedFilterMember = {};
                parsedFilterMember.filterMember = filterMember;
                parsedFilterMember.member = member;
                parsedFilterMember.caption = member.caption();
                parsedFilterMember.uniqueName = member.uniqueName();
                parsedFilterMember.children = [];

                parsedFilterMembers.push(parsedFilterMember);
            }

            return parsedFilterMembers;
        },
        _onFilterOk: function (event, dropDownElement, hierarchyFilterView, hierarchy) {
            var dataSource = this._ds, hierarchyName = hierarchy.uniqueName(),
                filterMembers, checkedFilterMembers, i, noCancel;

            filterMembers = hierarchyFilterView.getSelectedFilterItems().__inner; //toArray();

            checkedFilterMembers = [];
            for (i = 0; i < filterMembers.length; i++) {
                checkedFilterMembers.push(filterMembers[i].member().uniqueName());
            }

            noCancel = this._triggerFilterDropDownOk(event, dropDownElement, hierarchy, checkedFilterMembers);
            if (noCancel) {
                dataSource.removeAllFilterMembers(hierarchyName);
                for (i = 0; i < checkedFilterMembers.length; i++) {
                    dataSource.addFilterMember(hierarchyName, checkedFilterMembers[i]);
                }

                this._updateDataSource();
                this._removeFilterDropDown(event, dropDownElement, hierarchy);
            }
        },
        _removeFilterDropDown: function (event, dropDownElement, hierarchy) {
            var noCancel;

            noCancel = this._triggerFilterDropDownClosing(event, dropDownElement, hierarchy);
            if (noCancel) {
                dropDownElement.find(".ui-igtree").igTree("destroy");
                dropDownElement.find(".ui-button").igButton("destroy");
                dropDownElement.remove();
                // Detach from the document events in a safe way.
                $(document).unbind("." + this.widgetName);

                // Remove the overlay.
                // $("." + this.css.filterDropDownOverlay).remove();

                this._triggerFilterDropDownClosed(event, hierarchy);
            }
        },
        _triggerDataSourceInitialized: function (evt, args) {
            this._trigger("dataSourceInitialized", evt, args);
        },
        _triggerDataSourceUpdated: function (evt, args) {
            this._trigger("dataSourceUpdated", evt, args);
        },
        _triggerDragStart: function (event, ui, item) {
            var args = {
                helper: ui.helper,
                offset: ui.offset,
                originalPosition: ui.originalPosition,
                position: ui.position,
                metadata: item
            };

            // Make the overlay drop areas available only while dragging
            $(".ui-igpivot-overlaydroparea").css("display", "block");
            return this._trigger("dragStart", event, args);
        },
        _triggerDrag: function (event, ui, item) {
            var args = {
                helper: ui.helper,
                offset: ui.offset,
                originalPosition: ui.originalPosition,
                position: ui.position,
                metadata: item
            };

            return this._trigger("drag", event, args);
        },
        _triggerDragStop: function (event, ui) {
            $(".ui-igpivot-overlaydroparea").css("display", "none");
            this._trigger("dragStop", event, ui);
        },
        _triggerMetadataDropping: function (event, ui, targetElement, item, itemIndex) {
            var args = {
                helper: ui.helper,
                offset: ui.offset,
                position: ui.position,
                targetElement: targetElement,
                metadata: item,
                metadataIndex: itemIndex
            };

            return this._trigger("metadataDropping", event, args);
        },
        _triggerMetadataDropped: function (event, ui, targetElement, draggedElement, item, itemIndex) {

            var args = {
                helper: ui.helper,
                offset: ui.offset,
                position: ui.position,
                targetElement: targetElement,
                draggedElement: draggedElement,
                metadata: item,
                metadataIndex: itemIndex
            };

            this._trigger("metadataDropped", event, args);
        },
        _triggerMetadataRemoving: function (event, targetElement, item) {
            var args = {
                targetElement: targetElement,
                metadata: item
            };

            return this._trigger("metadataRemoving", event, args);
        },
        _triggerMetadataRemoved: function (event, item) {
            var args = {
                metadata: item
            };

            this._trigger("metadataRemoved", event, args);
        },
        _triggerFilterDropDownOpening: function (event, hierarchy) {
            var args = {
                hierarchy: hierarchy
            };

            return this._trigger("filterDropDownOpening", event, args);
        },
        _triggerFilterDropDownOpened: function (event, dropDownElement, hierarchy) {
            var args = {
                dropDownElement: dropDownElement,
                hierarchy: hierarchy
            };

            this._trigger("filterDropDownOpened", event, args);
        },
        _triggerFilterMembersLoaded: function (parent, rootFilterMembers, filterMembers) {
            var args = {
                parent: parent,
                rootFilterMembers: rootFilterMembers,
                filterMembers: filterMembers
            };

            this._trigger("filterMembersLoaded", null, args);
        },
        _triggerFilterDropDownOk: function (event, dropDownElement, hierarchy, filterMembers) {
            var args = {
                dropDownElement: dropDownElement,
                hierarchy: hierarchy,
                filterMembers: filterMembers
            };

            return this._trigger("filterDropDownOk", event, args);
        },
        _triggerFilterDropDownClosing: function (event, dropDownElement, hierarchy) {
            var args = {
                dropDownElement: dropDownElement,
                hierarchy: hierarchy
            };

            return this._trigger("filterDropDownClosing", event, args);
        },
        _triggerFilterDropDownClosed: function (event, hierarchy) {
            var args = {
                hierarchy: hierarchy
            };

            this._trigger("filterDropDownClosed", event, args);
        }
    };

    $.widget("ui.igOlapDataSourceWidget", {
        _create: function () {
            this._ds = $.ig.Pivot._pivotShared._createDataSource(null, this.options.dataSourceOptions);
        },
        dataSource: function () {
            /* Returns the associated data source of the OLAP data source widget. The data source will be an instance of $.ig.OlapXmlaDataSource or $.ig.OlapFlatDataSource, or null.
            returnType="object"
            */
            return this._ds;
        }
    });
} (jQuery));



