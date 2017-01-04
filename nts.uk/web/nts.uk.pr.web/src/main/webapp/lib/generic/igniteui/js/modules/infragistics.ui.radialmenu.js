/*!@license
* Infragistics.Web.ClientUI RadialMenu 16.1.20161.2145
*
* Copyright (c) 2013-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
*     jquery-1.8.3.js
*     jquery.ui.core.js
*     jquery.ui.widget.js
*     infragistics.util.js
*     infragistics.dv.simple.core.js
*     infragistics.radialmenu_core.js
*/

/*global jQuery */
if (typeof (jQuery) === "undefined") {
    throw new Error("jQuery is undefined");
}

(function ($) {
    // igRadialMenu definition
    $.widget('ui.igRadialMenu', {
        css: {
            /* Get the class applied to main element, shown when the radialMenu is opened in a non HTML5 compatible browser. */
            unsupportedBrowserClass: "ui-html5-non-html5-supported-message ui-helper-clearfix ui-html5-non-html5",
            /* Get the class applied to main element: ui-radialMenu */
            radialMenu: "ui-radialmenu",
            /* Class applied to the tooltip element: ui-radialmenu-tooltip ui-corner-all */
            tooltip: "ui-radialmenu-tooltip ui-corner-all",
    },

        events: {
            	/* cancel="false" Invoked when the IsOpen property is changed to false.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	*/
	closed: null,
	/* cancel="false" Invoked when the IsOpen property is changed to true.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	*/
	opened: null

        },
        options: {
            /* type="array"
            Gets or sets the items in the menu.
            */
            items:[
                {
                    /* type="button|coloritem|colorwell|list|numericitem|numericgauge"
                    Gets or sets a value indicating what type of item is being provided.
                    button type="string" 
                    coloritem type="string" 
                    colorwell type="string" 
                    list type="string" 
                    numericitem type="string" 
                    numericgauge type="string" 
                    */
                    type: "button",

                    /* type="string"
                    Gets or sets the unique name of the item within the menu.
                    */
                    name: null,

                    /* type="string"
                    Gets or sets the name of the child item that represents the most recently interacted with item. Note other item properties may be set to "{RecentItem}" to have them automatically set to values of the associated recent child item.
                    */
                    recentItemName: null,

                    /* type="number" Returns or sets the value of the numeric item.
                    */
                    value: NaN,

                    /* type="object" Returns or sets the value while the user is interacting with the element.
                    */
                    pendingValue: NaN,

                    	/* type="bool" Returns or sets a boolean indicating if the children should be rotated to align with the location of this element.
	*/
	autoRotateChildren: true,
	/* type="string" Returns or sets the brush used for the arc displayed within the tool when checked.
	*/
	checkedHighlightBrush: null,
	/* type="string" Returns or sets the foreground for the inner area of the item.
	*/
	foreground: null,
	/* type="string" Returns or sets the brush used for the arc displayed within the tool when hot tracked.
	*/
	highlightBrush: null,
	/* type="string" Returns or sets the background of the inner area of the menu item.
	*/
	innerAreaFill: null,
	/* type="string" Returns or sets the brush for the background of the inner area of the menu item that is under the pointer.
	*/
	innerAreaHotTrackFill: null,
	/* type="string" Returns or sets the brush for the default border of the inner area for the menu item that is under the pointer.
	*/
	innerAreaHotTrackStroke: null,
	/* type="string" Returns or sets the brush for the default border of the inner area for the menu item.
	*/
	innerAreaStroke: null,
	/* type="number" Returns or sets the thickness of the border for the inner area for the menu item.
	*/
	innerAreaStrokeThickness: 1,
	/* type="bool" Returns or sets a boolean indicating whether the item is enabled.
	*/
	isEnabled: true,
	/* type="bool" Returns or sets a boolean indicating if a tooltip may be displayed for the item.
	*/
	isToolTipEnabled: true,
	/* type="string" Returns or sets the brush for the background of the button within the outer ring for a menu item that is under the pointer.
	*/
	outerRingButtonHotTrackFill: null,
	/* type="string" Returns or sets the foreground of the buttons in the outer ring of the menu that is under the pointer.
	*/
	outerRingButtonHotTrackForeground: null,
	/* type="string" Returns or sets the brush for the default border of the button within the outer ring for a menu item that is under the pointer.
	*/
	outerRingButtonHotTrackStroke: null,
	/* type="string" Returns or sets the default background of the button within the outer ring for a menu item.
	*/
	outerRingButtonFill: null,
	/* type="string" Returns or sets the brush for the foreground of the buttons in the outer ring of the menu.
	*/
	outerRingButtonForeground: null,
	/* type="string" Returns or sets the brush for the default border of the button within the outer ring for a menu item.
	*/
	outerRingButtonStroke: null,
	/* type="number" Returns or sets the width of the outline of a button in the outer ring of the menu.
	*/
	outerRingButtonStrokeThickness: 1,
	/* type="object" Returns or sets the tooltip to be displayed for the radial menu item.
	*/
	toolTip: null,
	/* type="number" Returns or sets the wedge at which the item should be positioned.
	*/
	wedgeIndex: -1,
	/* type="number" Returns or sets the number of wedges that the item should occupy.
	*/
	wedgeSpan: 1,
	/* type="bool" Returns or sets a boolean indicating if the RecentItem property is updated when a child item is clicked.
	*/
	autoUpdateRecentItem: null,
	/* type="asChildren|asSiblingsWhenChecked|none" Returns or sets an enumeration indicating where the child items are displayed.
	asChildren type="string" The Items are displayed within a separate level that is accessed by clicking on the button in the outer ring of the xamRadialMenu for the parent.
	asSiblingsWhenChecked type="string" The items are displayed as siblings of the parent as long as the IsChecked is set to true.
	none type="string" The child items are not displayed.
	*/
	childItemPlacement: "asChildren",
	/* type="none|checkBox|radioButton|radioButtonAllowAllUp" Returns or sets a value indicating how the IsChecked property may be changed.
	none type="string" The item is not checkable
	checkBox type="string" The item is checkable and may be independantly checked or unchecked without affecting other items.
	radioButton type="string" The item is checkable. Only 1 item from the items with the same GroupName may be checked at a time and the checked item may not be unchecked.
	radioButtonAllowAllUp type="string" The item is checkable. Only 1 item from the items with the same GroupName may be checked at a time and the checked item may not be checked allowing all items to be unchecked.
	*/
	checkBehavior: "none",
	/* type="bool" Returns or sets a boolean indicating if the item is displayed as checked.
	*/
	isChecked: false,
	/* type="string" Returns or sets the name used to identify which RadioButton type items will be grouped together when determining the item to uncheck when the item is checked.
	*/
	groupName: null,
	/* type="object" Returns or sets the header of the menu item.
	*/
	header: null,
	/* type="string" Returns or sets the uri of the image for the item.
	*/
	iconUri: null,
	/* type="object" Returns or sets the color that the item represents.
	Note: When the Color property is set, several of the brush properties are changed.
	*/
	color: null,
	/* type="string" Returns or sets the brush used to render the line that represents the PendingValue
	*/
	pendingValueNeedleBrush: null,
	/* type="bool" Returns or sets a boolean indicating whether space should be left before the first tickmark.
	*/
	reserveFirstSlice: false,
	/* type="number" Returns or sets the amount that the PendingValue should be adjusted when incrementing or decrementing the value.
	*/
	smallIncrement: 1,
	/* type="string" Returns or sets the brush used to render the tick marks.
	*/
	tickBrush: null,
	/* type="object" Returns or sets the values of the ticks.
	*/
	ticks: null,
	/* type="object" Returns or sets the starting color for the track.
	*/
	trackStartColor: null,
	/* type="object" Returns or sets the ending color for the track.
	*/
	trackEndColor: null,
	/* type="string" Returns or sets the brush used to represent the Value
	*/
	valueNeedleBrush: null
,

                    	/* cancel="false" Invoked when one navigates back to the item after viewing the child items.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	Use ui.item to obtain reference to the item.
	*/
	closed: null,
	/* cancel="false" Invoked when one navigates to the view the child items.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	Use ui.item to obtain reference to the item.
	*/
	opened: null,
	/* cancel="false" Occurs when the IsChecked is changed to true.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	Use ui.item to obtain reference to the item.
	*/
	checked: null,
	/* cancel="false" Occurs when the item area is clicked.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	Use ui.item to obtain reference to the item.
	*/
	click: null,
	/* cancel="false" Occurs when the IsChecked is changed to false.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	Use ui.item to obtain reference to the item.
	*/
	unchecked: null,
	/* cancel="false" Event invoked when the Color property is changed.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	Use ui.oldValue to obtain the previous value.
	Use ui.newValue to obtain the new value.
	Use ui.item to obtain reference to the item.
	*/
	colorChanged: null,
	/* cancel="false" Occurs when the item area of a descendant color well is clicked.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	Use ui.item to obtain reference to the item.
	*/
	colorWellClick: null,
	/* cancel="false" Event invoked when the Value property is changed.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	Use ui.oldValue to obtain the previous value.
	Use ui.newValue to obtain the new value.
	Use ui.item to obtain reference to the item.
	*/
	valueChanged: null,
	/* cancel="false" Event invoked when the PendingValue property is changed.
	Function takes a first argument ui.
	Use ui.owner to obtain reference to menu widget.
	Use ui.oldValue to obtain the previous value.
	Use ui.newValue to obtain the new value.
	Use ui.item to obtain reference to the item.
	*/
	pendingValueChanged: null

                }
            ],

            /* type="string"
            Gets or sets the name of the item within the menu whose children are currently displayed.
            */
            currentOpenMenuItemName: null,

            	/* type="number" Returns or sets the width of the center button content.
	*/
	centerButtonContentWidth: 28,
	/* type="number" Returns or sets the height of the center button content.
	*/
	centerButtonContentHeight: 28,
	/* type="string" Returns or sets the background of the center button of the menu when the IsOpen property is false.
	*/
	centerButtonClosedFill: null,
	/* type="string" Returns or sets the brush used for the outline of the ring of the center button when the IsOpen property is false.
	*/
	centerButtonClosedStroke: null,
	/* type="string" Returns or sets the background of the center button of the menu when the IsOpen property is true.
	*/
	centerButtonFill: null,
	/* type="string" Returns or sets the background of the center button of the menu when under the pointer.
	*/
	centerButtonHotTrackFill: null,
	/* type="string" Returns or sets the brush used for the outline of the ring of the center button when under the pointer.
	*/
	centerButtonHotTrackStroke: null,
	/* type="string" Returns or sets the brush used for the outline of the ring of the center button when the IsOpen is true.
	*/
	centerButtonStroke: null,
	/* type="number" Returns or sets the width of the outline of the inner rings of the menu.
	*/
	centerButtonStrokeThickness: 0,
	/* type="string" The font for the control
	*/
	font: null,
	/* type="bool" Returns or sets a boolean indicating whether the items of the menu are currently displayed. When closed, only the center button is rendered.
	*/
	isOpen: false,
	/* type="string" Returns or sets the brush for the backing of the radial menu.
	*/
	menuBackground: null,
	/* type="number" Returns or sets the duration of the animation performed when the IsOpen property is changed.
	*/
	menuItemOpenCloseAnimationDuration: 250,
	/* type="object" Returns or sets the easing function applied to the animation that occurs when the IsOpen property is changed.
	*/
	menuItemOpenCloseAnimationEasingFunction: null,
	/* type="number" Returns or sets the duration of the animation performed when the IsOpen property is changed.
	*/
	menuOpenCloseAnimationDuration: 250,
	/* type="object" Returns or sets the easing function applied to the animation that occurs when the IsOpen property is changed.
	*/
	menuOpenCloseAnimationEasingFunction: null,
	/* type="number" Returns or sets the minimum number of wedges displayed by the menu.
	*/
	minWedgeCount: 8,
	/* type="string" Returns or sets the background of the outer ring of the menu.
	*/
	outerRingFill: null,
	/* type="number" Returns or sets the thickness of the outer ring of the menu.
	*/
	outerRingThickness: 26,
	/* type="string" Returns or sets the brush used for the outline of the outer ring.
	*/
	outerRingStroke: null,
	/* type="number" Returns or sets the width of the outline of the outer ring of the menu.
	*/
	outerRingStrokeThickness: 0,
	/* type="number" Returns or sets the starting angle of the items in degrees.
	*/
	rotationInDegrees: -90,
	/* type="number" Returns or sets the starting angle of the items expressed as the percentage of the width of a single wedge/slice.
	*/
	rotationAsPercentageOfWedge: -0.5,
	/* type="number" Returns or sets the amount of padding around each wedge in degrees.
	*/
	wedgePaddingInDegrees: 0

        },

        _setOption: function (key, value, checkPrev) {
            var radialMenu = this._radialMenu, o = this.options;
            if (checkPrev && o[key] === value) {
                return;
            }
            $.Widget.prototype._setOption.apply(this, arguments);

            if (this._set_option(radialMenu, key, value)) {
                return this;
            }

            return this;
        },

        _set_generated_option: function (radialMenu, key, value) {
            	switch (key) {
		case "font":
			radialMenu.font(value);
			return true;
	}

        },

        _set_option: function (radialMenu, key, value) {
            var self = this;
            var currentKey;

            switch (key) {
                case "width":
                    this._setSize(radialMenu, "width", value);
                    return true;
                case "height":
                    this._setSize(radialMenu, "height", value);
                    return true;
                case "items":
                    radialMenu.items().clear();
                    this._itemKeys = {};

                    $.each(value, function (i, val) {
                        self._addItem(radialMenu, val);
                    });

                    if (this.options.hasOwnProperty("currentOpenMenuItemName")) {
                        this._set_option(radialMenu, "currentOpenMenuItemName", this.options.currentOpenMenuItemName);
                    }
                    return true;
                case "currentOpenMenuItemName":
                    // if we haven't gotten the items yet then do it after that
                    if (radialMenu.items().count() > 0) {
                        if (value && this._itemKeys.hasOwnProperty(value)) {
                            radialMenu.currentOpenMenuItem(this._itemKeys[value]);
                        } else {
                            radialMenu.currentOpenMenuItem(null);
                        }
                    }
                    return true;
                case "menuItemOpenCloseAnimationEasingFunction":
                case "menuOpenCloseAnimationEasingFunction":
                    value = $.ig.util.getEasingFunction(value);
                    radialMenu[key](value);
                    return true;
                default:
                    // if we have an auto-generated handler then let it handle it
                    if (this._set_generated_option(radialMenu, key, value))
                        return true;

                    // otherwise use the dp
                    return this._setProperty(radialMenu, key, value, true);
            }
        },

        itemOption: function (itemKey, key, value) {
            /* gets or sets the value of a property for the item created with the specified key
                paramType="string" The name of the item
                paramType="string" The name of the property/option
                paramType="object" The new value for the property or undefined to obtain the current value
                returnType="object" The value of the property if the value parameter is undefined otherwise a boolean indicating if the property was changed. 
            */
            var item = this._itemKeys[itemKey];

            if (item === undefined)
                throw new Error("Specified itemKey is invalid"); // TODO localize

            var dataItem = item._dataItem;

            if (arguments.length === 1) {
                return dataItem; // TODO should we extend/copy it?
            }

            if (typeof key === "string") {
                if (value === undefined) {
                    // return the current option value
                    var value = dataItem[key];

                    // if the property hasn't been set then get the default value
                    if (value === undefined)
                        value = $.ui.igRadialMenu.prototype.options.items[0][key];

                    return value; // TODO should we extend/copy it?
                } else {
                    if (key == "items")
                        throw new Error("Not supported"); // TODO should we support replacing the children?

                    // update the option value
                    dataItem[key] = value;
                    this._setItemOption(item, key, value, false);

                    if (key == "recentItemName") {
                        var child = this._itemKeys[key];
                        this._setItemOption(item, "recentItem", child === undefined ? null : child._dataItem);
                    }

                }
            } else {
                throw new Error("Not supported"); // TODO should we support replacing the item?
            }

            return true;
        },

        _addItem : function(parent, item) {
            var mi;

            switch (item.type) {
                case "coloritem":
                    mi = new $.ig.RadialMenuColorItem();
                    break;
                case "colorwell":
                    mi = new $.ig.RadialMenuColorWell();
                    break;
                case "numericitem":
                    mi = new $.ig.RadialMenuNumericItem();
                    break;
                case "numericgauge":
                    mi = new $.ig.RadialMenuNumericGauge();
                    break;
                case "list":
                case "button":
                default:
                    mi = new $.ig.RadialMenuItem();
                    break;
            }

            mi._dataItem = item;
            mi.__setOptionCount__ = 0;

            if (item.name) {
                this._itemKeys[item.name] = mi;
            }

            for (var key in item) {
                var value = item[key];
                switch (key) {
                    case "items":
                        var count = value.length;

                        for (var i = 0; i < count; i++) {
                            this._addItem(mi, value[i]);
                        }
                        break;
                    default:
                        this._setItemOption(mi, key, value);
                        break;
                }
            }

            if (mi.recentItemName) {
                var child = this._itemKeys[mi.recentItemName];
                this._setItemOption(mi, "recentItem", child === undefined ? null : child._dataItem);
            }

            parent.items().add(mi);

            // we hook the property changed so we can update options values
            mi.propertyChanged = $.ig.Delegate.prototype.combine(mi.propertyChanged, jQuery.proxy(this._onRadialMenuItemPropChanged, this));
        },

        _setItemOption: function (item, key, value, mustBeDp) {
            if (!key in item)
                return false;

            switch (key) {
                case "closed":
                case "opened":
                case "checked":
                case "click":
                case "unchecked":
                case "colorWellClick":
                    var proxy = jQuery.proxy(this._onRadialMenuItemEvent, { self: this, eventName: key });

                    if (item[key])
                        item[key] = $.ig.Delegate.prototype.remove(item[key], proxy);

                    if (value)
                        item[key] = $.ig.Delegate.prototype.combine(item[key], proxy);
                    break;
                case "colorChanged":
                case "valueChanged":
                case "pendingValueChanged":
                    var proxy = jQuery.proxy(this._onRadialMenuItemValueEvent, { self: this, eventName: key });

                    if (item[key])
                        item[key] = $.ig.Delegate.prototype.remove(item[key], proxy);

                    if (value)
                        item[key] = $.ig.Delegate.prototype.combine(item[key], proxy);
                    break;
                case "recentItemName":
                    // this property may have been set before the items so wait
                    item.recentItemName = value;
                    break;
                default:
                    if (value === "{RecentItem}") {
                        var dp = item[key + "Property"];

                        if (dp === undefined)
                            return false;

                        var bExp = new $.ig.BindingExpression(item, "recentItemContainer." + key, item, dp);
                        if (item.bindingExpressions === undefined)
                            item.bindingExpressions = {};
                        item.bindingExpressions[key] = bExp;
                    } else if (key in item) {
                        item.__setOptionCount__++;
                        this._setProperty(item, key, value);
                        item.__setOptionCount__--;
                    }
            }

            return true;
        },

        _setProperty: function(item, key, value, mustBeDp) {
            if (value !== null) {
                var dp = item[key + "Property"];

                if (dp !== undefined) {
                    var propType = dp.propertyType();
                    if (value === undefined) {
                        item.clearValue(dp);
                        return true;
                    } else  if ($.ig.Brush.prototype.$type.equals(propType)) {
                        value = $.ig.Brush.prototype.create(value);
                    } else if ($.ig.DataTemplate.prototype.$type.equals(propType)) {
                        var $tempTemplate = new $.ig.DataTemplate();
                        if (value.render) {
                            $tempTemplate.render(value.render);
                            if (value.measure) {
                                $tempTemplate.measure(value.measure);
                            }
                        } else {
                            $tempTemplate.render(value);
                        }
                        value = $tempTemplate;
                    } else {
                        var uType = $.ig.Nullable.prototype.getUnderlyingType(propType);

                        if (uType !== null)
                            propType = uType;

                        if (typeof value === 'string' && propType !== String) {
                            if (value.length === 0) {
                                return false;
                            } else if ($.ig.util.canAssign($.ig.Enum.prototype.$type, propType)) {
                                value = $.ig.Enum.prototype.parse(propType, value, true).$value();
                            } else if ($.ig.Color.prototype.$type.equals(propType)) {
                                $clr = new $.ig.Color();
                                $clr.colorString(value);
                                value = $clr;
                            } else if ($.ig.Number.prototype.$type.equals(propType)) {
                                value = parseFloat(value);
                            } else if ($.ig.Boolean.prototype.$type.equals(propType)) {
                                value = value.toLowerCase() == "true";
                            } else if ($.ig.DoubleCollection.prototype.$type.equals(propType)) {
                                value = value.split(',');
                                for (var i = 0; i < value.length; i++) {
                                    value[i] = parseFloat(value[i]);
                                }
                            }
                        }

                        if (uType !== null) {
                            value = $.ig.util.toNullable(uType, value);
                        }
                    }

                } else if (mustBeDp) {
                    return false;
                }
            }

            item[key](value);
            return true;
        },

        exportVisualData: function () {
            /* exports visual data from the radial menu to aid in unit testing */
            if (this._radialMenu)
                return this._radialMenu.exportVisualData();
        },

        _creationOptions: null,
        _radialMenu: null,
        _itemKeys: null,
        _itemPropertyChangeHandlers: null,
        _menuPropertyChangeHandlers: null,

        _createWidget: function (options, element, widget) {
            this._creationOptions = options;

            $.Widget.prototype._createWidget.apply(this, [options, element]);
        },

        _create: function () {
            var key, v, size, radialMenu, width, height,
            i = -1,
            self = this,
            elem = self.element,
            style = elem[0].style,
            o = this._creationOptions;

            // variable which holds initial attributes/styles of target widget, which are used to
            // restore target element on destroy
            self._old_state = {
                // extended widget may add other attributes to that member and they will be processed within destroy
                style: { position: style.position, width: style.width, height: style.height, visibility: style.visibility },
                css: elem[0].className,
                elems: elem.find("*")
            };
            if (!$.ig.util._isCanvasSupported()) {
                $.ig.util._renderUnsupportedBrowser(this);
                return;
            }
            radialMenu = this._createMenu();
            self._radialMenu = radialMenu;
            self._itemKeys = {};
            self._itemPropertyChangeHandlers = {};
            self._itemPropertyChangeHandlers[$.ig.RadialMenuColorItemBase.prototype.colorProperty.name()] = function (item) { item._dataItem.color = item.color().colorString(); };
            self._itemPropertyChangeHandlers[$.ig.RadialMenuItem.prototype.isCheckedProperty.name()] = function (item) { item._dataItem.isChecked = item.isChecked(); };
            self._itemPropertyChangeHandlers[$.ig.RadialMenuNumericItem.prototype.valueProperty.name()] = 
            self._itemPropertyChangeHandlers[$.ig.RadialMenuNumericGauge.prototype.valueProperty.name()] = function (item) {  
                    var value = item.value();
                    item._dataItem.value = value != null && typeof value == 'object' ? value.value() : value;
                };
            self._itemPropertyChangeHandlers[$.ig.RadialMenuNumericGauge.prototype.pendingValueProperty.name()] = function (item) {  
                    var value = item.pendingValue();
                    item._dataItem.pendingValue = value != null && typeof value == 'object' ? value.value() : value;
                };
            self._itemPropertyChangeHandlers[$.ig.RadialMenuItem.prototype.recentItemProperty.name()] = function (item) {
                    var recentItem = item.recentItem();
                    item._dataItem.recentItemName = recentItem == null ? null : recentItem.name;
                };

            self._menuPropertyChangeHandlers = {};
            self._menuPropertyChangeHandlers[$.ig.XamRadialMenu.prototype.isOpenProperty.name()] = function (radialMenu) { this.options.isOpen = radialMenu.isOpen(); };
            self._menuPropertyChangeHandlers[$.ig.XamRadialMenu.prototype.currentOpenMenuItemProperty.name()] = function (radialMenu) {
                    var item = radialMenu.currentOpenMenuItem();
                    this.options.currentOpenMenuItemName = item != null && item.name ? item.name : null;
                };

            // we hook the property changed so we can update options values
            radialMenu.propertyChanged = $.ig.Delegate.prototype.combine(radialMenu.propertyChanged, jQuery.proxy(this._onRadialMenuPropChanged, this));

            			radialMenu.closed = $.ig.Delegate.prototype.combine(radialMenu.closed, jQuery.proxy(this._fireRadialMenu_closed, this));
			radialMenu.opened = $.ig.Delegate.prototype.combine(radialMenu.opened, jQuery.proxy(this._fireRadialMenu_opened, this));


            if (o.hasOwnProperty("width"))
                elem[0].style.width = o["width"];
            if (o.hasOwnProperty("height"))
                elem[0].style.height = o["height"];

            radialMenu.provideContainer(elem[0]);
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
                if (v && v.indexOf("%") > 0) {
                    self._setSize(radialMenu, size = key, v);
                }
            }
            //_setSize should be called at least once: support for initially invisible container of char
            if (!size) {
                self._setSize(radialMenu, "width");
            }

            if (self.css && self.css.radialMenu) {
                elem.addClass(self.css.radialMenu);
            }

            radialMenu.onInitialized();
        },
        _createMenu: function () {
            return new $.ig.XamRadialMenu();
        },

        _onRadialMenuItemEvent: function (item, evt) {
            var self = this.self;
            var dataItem = item._dataItem;
            var func = dataItem[this.eventName];
            var opts = {};
            opts.owner = self;
            opts.item = dataItem;
            func.call(self.element[0], opts);
        },

        _onRadialMenuItemValueEvent: function (item, evt) {

            if (item.__setOptionCount__ > 0)
                return;

            var self = this.self;
            var dataItem = item._dataItem;
            var opts = {};
            opts.owner = self;
            opts.item = dataItem;

            switch (this.eventName) {
                case "valueChanged":
                case "pendingValueChanged":
                    opts.oldValue = evt.oldValue();
                    opts.newValue = evt.newValue();

                    if (isNaN(opts.oldValue) && isNaN(opts.newValue))
                        return;

                    break;
                case "colorChanged":
                    opts.oldValue = evt.oldValue();
                    opts.newValue = evt.newValue();

                    if (opts.oldValue)
                        opts.oldValue = opts.oldValue.colorString();

                    if (opts.newValue)
                        opts.newValue = opts.newValue.colorString();
                    break;
            }

            if (opts.newValue == opts.oldValue)
                return;

            var func = dataItem[this.eventName];
            func.call(self.element[0], opts);
        },

        _onRadialMenuItemPropChanged: function (item, evt) {
            var handler = this._itemPropertyChangeHandlers[evt._propertyName];

            if (handler)
                handler.call(this, item);
        },

        _onRadialMenuPropChanged: function(radialMenu, evt) {
            var handler = this._menuPropertyChangeHandlers[evt._propertyName];

            if (handler)
                handler.call(this, radialMenu);
        },

        _fireRadialMenu_closed : function(radialMenu, evt) {
            var opts = {};
            opts.owner = this;
            this._trigger("closed", null, opts);
        },

        _fireRadialMenu_opened: function (radialMenu, evt) {
            var opts = {};
            opts.owner = this;
            this._trigger("opened", null, opts);
        },

        // sets width and height options.
        // params:
        // chart-reference to chart object
        // key-name of option/property (width or height only)
        // value-value of option
        _setSize: function (radialMenu, key, val) {
            $.ig.util.setSize(this.element, key, val, radialMenu, this._getNotifyResizeName());
        },

        _getNotifyResizeName: function () {
            return "containerResized";
        },

        flush: function () {
            /* Forces any pending deferred work to render on the radial menu before continuing */
            if (this._radialMenu && this._radialMenu.flush)
                this._radialMenu.flush();
        },

        destroy: function () {
            /* Destroys the widget. */
            var key, style,
				radialMenu = this._radialMenu,
				old = this._old_state,
				elem = this.element;
            if (!old) {
                return;
            }
            // remove children created by radialMenu
            elem.find("*").not(old.elems).remove();
            // restore className modified by radialMenu
            if (this.css.radialMenu) {
                elem.removeClass(this.css.radialMenu);
            }
            // restore css style attributes modified by radialMenu
            old = old.style;
            style = elem[0].style;
            for (key in old) {
                if (old.hasOwnProperty(key)) {
                    if (style[key] !== old[key]) {
                        style[key] = old[key];
                    }
                }
            }
            if (radialMenu) {
                this._setSize(radialMenu);
            }
            $.Widget.prototype.destroy.apply(this, arguments);
            if (radialMenu && radialMenu.destroy) {
                radialMenu.destroy();
            }
            delete this._radialMenu;
            delete this._old_state;
        },

        styleUpdated: function () {
            /* Notify the radial menu that style information used for rendering the menu may have been updated. */
            if (this._radialMenu) {
                this._radialMenu.styleUpdated();
            }
        }
    });
    $.extend($.ui.igRadialMenu, { version: '16.1.20161.2145' });
}(jQuery));
