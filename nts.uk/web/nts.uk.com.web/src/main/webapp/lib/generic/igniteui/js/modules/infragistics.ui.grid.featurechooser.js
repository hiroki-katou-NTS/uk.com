/*!@license
 * Infragistics.Web.ClientUI Grid Feature Chooser 16.1.20161.2145
 *
 * Copyright (c) 2011-2016 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 *	jquery-1.9.1.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	jquery.ui.mouse.js
 *	jquery.ui.draggable.js
 *	jquery.ui.resizable.js
 *	infragistics.ui.popover.js
 *	infragistics.ui.grid.framework.js
 *	infragistics.ui.grid.shared.js
 *	infragistics.ui.shared.js
 *	infragistics.dataSource.js
 *	infragistics.util.js
 */

/*global jQuery, window*/
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}

(function ($) {

	$.widget("ui.igGridFeatureChooserPopover", $.ui.igPopover, {
		options: {
			gridId: "",
			targetButton: null,
			closeOnBlur: true,
			containment: null
		},
		_create: function () {
			$.extend($.ui.igGridFeatureChooserPopover.prototype.options, $.ui.igPopover.prototype.options);
			$.extend($.ui.igGridFeatureChooserPopover.prototype.css, $.ui.igPopover.prototype.css);
			$.ui.igPopover.prototype._create.apply(this);
		},
		isShown: function () {
			return this.container().is(":visible");
		},
		_setFCElementFocus: function (focus, $target) {
			if ($target === undefined || $target === null) {
				$target = this.options.targetButton;
			}
			this.options.targetButton.data("onFocus", focus);
			if (focus) {
				$target.focus();
			}
		},
		registerElements: function (elements) {
			var events, $targetButton = this.options.targetButton,
			self = this;

			events = {
				focus: function () {
					$targetButton.data("onFocus", true);
				},
				blur: function () {
					$targetButton.data("onFocus", false);
					if (self._timeoutId) {
						clearTimeout(self._timeoutId);
					}
					self._timeoutId = setTimeout(function () {
						if (!$targetButton.data("onFocus") && self.isShown()) {
							self._closePopover();
						}
					}, 1);
				}
			};
			elements.bind(events);
		},
		_createWidget: function () {
			/*this.options.defaultMaxWidth = null; */
			var self = this,
			$targetButton;

			this._attachedToBody = true;
			this.options.content = "";
			$.Widget.prototype._createWidget.apply(this, arguments);
			$.ui.igPopover.prototype._createWidget.apply(this, arguments);
			this._detachEventsFromTarget();
			$targetButton = this.options.targetButton;
			if (this.options.closeOnBlur) {
				$targetButton.attr("tabindex", "0");
				this._eventsFC = {
					"iggridfeaturechooserpopovershown": function () {
						self._setFCElementFocus(true);
					},
					"iggridfeaturechooserpopoverhidden": function () {
						self._setFCElementFocus(false);
						/* M.H. 26 Sep 2013 Fix for bug #150389: Feature chooser is not properly shown when browser window is scrolled left */
						self.popover.width("");
						self.popover.css({ "left": "" });
					},
					"mousedown": function () {
						setTimeout(function () {
							self._setFCElementFocus(true);
						}, 1);
					},
					"touchstart": function () {
						setTimeout(function () {
							self._setFCElementFocus(true);
						}, 1);
					}
				};
				this.element.bind(this._eventsFC);
				this.registerElements($targetButton);
			}
		},
		/* overwrite _removeOriginalTitle - fix for bug 201576: Click on FC target - title attribute is set to ""(and on close FC target title is NOT restored) */
		_removeOriginalTitle: function () {
		},
		_positionPopover: function () {
			var $popover,
				availableWidth,
				mw = this.options.maxWidth,
				$containment = this.options.containment,
				$window = $(window);
			/* M.H. 26 Sep 2013 Fix for bug #150389: Feature chooser is not properly shown when browser window is scrolled left */
			if (mw && $.type(mw) === "string") {
				mw = parseInt(mw, 10);
			}
			$popover = this.popover;
			/* M.H. 3 Oct 2013 Fix for bug #153973: When grid's width is 300px the feature chooser is not rendered correctly. */
			if ($popover) {
				/* M.H. 8 Nov 2013 Fix for bug #153482: Feature chooser is not properly rendered when widow is resized */
				if ($containment.offset().left >= $window.scrollLeft()) {
					availableWidth = $window.width() + $window.scrollLeft() - $containment.offset().left;
				} else {
					availableWidth = $window.width();
				}
				if (mw && $.type(mw) === "number" && mw < availableWidth) {
					availableWidth = mw;
				}
				/* M.H. 9 Oct 2014 Fix for bug #182398: Column Chooser icon always comes under other features and there is a large blank space on the right side */
				this.popover.css("max-width", availableWidth);
			}
			$.ui.igPopover.prototype._positionPopover.apply(this, arguments);
		},
		destroy: function () {
			var $targetButton;

			if (this._eventsFCTargetButton) {
				$targetButton = this.options.targetButton;
				$targetButton.unbind(this._eventsFCTargetButton);
			}
			if (this._eventsFC) {
				this.element.unbind(this._eventsFC);
			}
			$.ui.igPopover.prototype.destroy.apply(this, arguments);
		}
	});
	$.extend($.ui.igGridFeatureChooserPopover, { version: "16.1.20161.2145" });
	$.ig = $.ig || {};
	$.ig.GridFeatureChooserSections = $.ig.GridFeatureChooserSections || {
		click: "click",
		toggle: "toggle",
		modalDialog: "modalDialog"
	};

	$.widget("ui.igGridFeatureChooser", {
		/* igGridSummaries widget -  The widget is pluggable to the element where the grid is instantiated and the actual igGrid object doesn"t know about this
		*/
		/* the instance of the grid to which this feature is going to attach its functionality */
		css: {
			featureChooserDropDown: "",
			headerButtonIcon: "ui-iggrid-featurechooserbutton ui-icon ui-icon-gear",
			headerButtonIconMouseOver: "ui-iggrid-header-icon-mouseover",
			headerButtonIconSelected: "ui-iggrid-header-icon-selected",
			listClass: "ui-iggrid-featurechooser-list ui-menu ui-widget ui-widget-content ui-corner-all",
			listItemClass: "ui-iggrid-featurechooserddlistitemicons ui-state-default",
			listItemContainer: "ui-iggrid-featurechooser-li-container",
			listItemIconContainer: "ui-iggrid-featurechooser-li-iconcontainer",
			itemSecondaryIconContainer: "ui-iggrid-featurechooser-secondaryicon-container",
			separator: "ui-iggrid-featurechooser-separator",
			listItemText: "ui-iggrid-featurechooserddlistitemtext",
			itemSelected: "item-selected",
			dropDownListItemHover: "ui-iggrid-featurechooser-listitem-hover ui-state-active ui-state-hover",
			dropDownButtonClasses: "ui-igbutton",
			dropDownButtonHoverClasses: "",
			dropDownButtonActiveClasses: "", // when button is clicked
			dropDownButtonFocusClasses: "", // when button get focus
			dropDownButtonLabelClass: "",
			containerDelimiter: "ui-iggrid-featurechooser-container-delimiter",
			containerSection: "ui-iggrid-featurechooser-container-section",
			itemNoIcon: "ui-iggrid-featurechooserbutton ui-icon ui-icon-close",
			submenu: "ui-iggrid-featurechooser-submenu ui-widget-content ui-corner-all",
			submenuIcon: "ui-iggrid-featurechooser-submenuicon ui-icon ui-icon-triangle-1-s"
		},
		options: {
			dropDownWidth: null,
			animationDuration: 400
		},
		events: {
			featureChooserRendering: "featureChooserRendering",
			featureChooserRendered: "featureChooserRendered",
			featureChooserDropDownOpening: "featureChooserDropDownOpening",
			featureChooserDropDownOpened: "featureChooserDropDownOpened",
			menuToggling: "menuToggling",
			featureToggling: "featureToggling",
			featureToggled: "featureToggled"
		},
		_createWidget: function () {
			/* !Strip dummy objects from options, because they are defined for documentation purposes only! */
			/*this.options.columnSettings = [  ]; */
			$.Widget.prototype._createWidget.apply(this, arguments);
		},

		_create: function () {
			var i;
			this.analyzedData = {};
			this.data = {};
			this._features = [  ];
			this._isFeaturesAnalyzed = false;
			this.isInitialized = false;
			this.grid = this.element.data("igGrid");
			this.gridId = this.element[ 0 ].id;
			this.grid._internalFeatures = this.grid._internalFeatures || [ ];
			/* register FeatureChooser in array _internalFeatures*/
			for (i = 0; i < this.grid._internalFeatures.length; i++) {
				if (this.grid._internalFeatures[ i ].name === "FeatureChooser") {
					break;
				}
			}
			if (i === this.grid._internalFeatures.length) {
				this.grid._internalFeatures.push({
					name: "FeatureChooser"
				});
			}
			this._analyzeGridFeaturesOptions();
			this._countRenderedFeatures = 0;
			/* M.H. 19 Dec 2013 Fix for bug 158542: Memory Leak in IE8 when calling dataBind */
			if (this._headerRenderedHandler) {
				this.grid.element.unbind("iggridheaderrendered", this._headerRenderedHandler);
			}
			this._headerRenderedHandler = $.proxy(this._headerRendered, this);
			this.grid.element.bind("iggridheaderrendered", this._headerRenderedHandler);
			/* M.H. 19 Dec 2013 Fix for bug 158542: Memory Leak in IE8 when calling dataBind */
			if (this._gridDestroyedHandler) {
				this.grid.element.unbind("igcontroldestroyed", this._gridDestroyedHandler);
			}
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			this._gridDestroyedHandler = $.proxy(this.destroy, this);
			this.grid.element.bind("igcontroldestroyed", this._gridDestroyedHandler);
			if (this._virtualHorizontalScrollHandler) {
				this.grid.element.unbind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			}
			this._virtualHorizontalScrollHandler = $.proxy(this._virtualHorizontalScroll, this);
			this.grid.element.bind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);

		},
		_analyzeGridFeaturesOptions: function () {
			/* analyze features and push those which are defined in grid element */
			var i,
			features = this.grid.options.features,
			featuresLength = features.length;

			for (i = 0; i < featuresLength; i++) {
				this._features.push(features[ i ].name);
			}
		},
		shouldShowFeatureIcon: function (key) {
			if (this.analyzedData[ key ] !== undefined && this.analyzedData[ key ] !== null) {
				return (this.analyzedData[ key ].length <= 1);
			}

			return true;
		},
		_setOption: function () {
			/* handle new settings and update options hash */
			$.Widget.prototype._setOption.apply(this, arguments);
		},
		_id: function (name, suffix) {
			var res = this.gridId + "_" + name;
			if (suffix !== undefined && suffix !== null && $.type(suffix) === "string") {
				res += "_" + suffix;
			}
			return res;
		},
		_isMetroIE: function () {
			return (!$.ig.util.isIEOld && $.ig.util.isIE && (window.hasOwnProperty("ontouchstart") ||
				navigator.MaxTouchPoints > 0 || navigator.msMaxTouchPoints > 0));
		},
		_isTouchDevice: function () {
			var fcIconDisplay;
			if (this._isTouch === undefined) {
				fcIconDisplay = this.grid.options.featureChooserIconDisplay;
				if (fcIconDisplay === "none") {
					this._isTouch = true;
				} else if (fcIconDisplay === "always") {
					this._isTouch = false;
				} else {
					this._isTouch = $.ig.util.isTouch || this._isMetroIE();
				}
			}
			return this._isTouch;
		},
		_callFeatureMethod: function (feature, isSelected, columnKey, event) {
			/* if callback feature function returns false(for instance in column fixing it is cancelled) then it should return false and feature chooser should not be updated */
			var method,
			returnType = true,
			methodType = $.type(feature.method),
			featureData = this.grid.element.data("igGrid" + feature.name);

			if (methodType === "string") {
				if (featureData === undefined || featureData === null) {
					return false;
				}
				method = featureData[ feature.method ];
			} else if (methodType === "function") {
				method = feature.method;
			}

			if (method === undefined || method === null) {
				return false;
			}

			/* the event should be called from button - e.g. in filtering it tries to get column cell -  */
			/* which is parent of the target element */
			/* M.H. 10 Jun 2013 Fix for bug #144214: The "Fix Column" option changes even when column fixing is refused. */
			if ($.type(feature.method) === "string") {
				returnType = featureData[ feature.method ](event, columnKey, isSelected, feature.methodData);
			} else {
				returnType = feature.method(event, columnKey, isSelected, feature.methodData);
			}
			return returnType;
		},
		_getFeatureByKeyName: function (columnKey, featureName) {
			var i,
				feature = null,
				columnData = this.data[ columnKey ],
				columnDataLength;

			if (!columnData) {
				return feature;
			}
			columnDataLength = columnData.length;
			for (i = 0; i < columnDataLength; i++) {
				if (columnData[ i ].name === featureName) {
					feature = columnData[ i ];
					break;
				}
			}

			return feature;
		},
		_clickFeature: function (event) {
			var i, j,
				$target = ($(event.target).data("data") !== undefined) ?
				$(event.target) : $(event.currentTarget),
				d = $target.data("data"),
				self = this,
				isSelected = null,
				type = d.type,
				columnKey = d.columnKey,
				featureName = d.featureName,
				columnData = this.data[ columnKey ],
				columnDataLength = columnData.length,
				feature = null;
			feature = this._getFeatureByKeyName(columnKey, featureName);
			if (feature === null || feature === undefined) {
				return;
			}
			/* M.H. 23 Jul 2013 Fix for bug #144214: The "Fix Column" option changes even when column fixing is refused. */
			if (type === "toggle") {
				isSelected = !feature.isSelected;
			}
			if (this._trigger(
					this.events.featureToggling,
					null,
					{
				target: $target,
				columnKey: columnKey,
				isToSelect: isSelected,
				featureName: "igGrid" + feature.name
			}
				) === false) {
				return;
			}
			if (this._callFeatureMethod(feature, isSelected, columnKey, event) === false) {
				return;
			}
			/* when updateOnClickAll is true AND isOnBottom is <> true then */
			/* update the state of this feature in the other feature chooser dropdowns */
			if (type === "toggle") {
				/* M.H. 23 Jul 2013 Fix for bug #144214: The "Fix Column" option changes even when column fixing is refused. */
				feature.isSelected = isSelected;
				self._setSelectedItem(
					columnKey,
					isSelected,
					$("#" + self._id("featurechooser_dd_li_" + columnKey, featureName))
				);

				if (feature.updateOnClickAll === true) {
					$.each(self.data, function (index, value) {
						if (index !== columnKey) {
							for (j = 0; j < value.length; j++) {
								if (value[ j ].name === featureName) {
									break;
								}
							}
							if (j === value.length) {
								return true;
							}

							/* update isSelected property for other columns specified feature */
							columnData = self.data[ index ];
							if (columnData === null || columnData === undefined) {
								return true;
							}
							columnDataLength = columnData.length;

							for (i = 0; i < columnDataLength; i++) {
								if (columnData[ i ].name === featureName) {
									columnData[ i ].isSelected = isSelected;
									break;
								}
							}

							self._setSelectedItem(
								index,
								isSelected,
								$("#" + self._id("featurechooser_dd_li_" + index, value[ j ].name))
							);
						}
					});
				}
			}
		},
		_setListItemText: function (columnKey, featureName, text) {
			/* M.H. 20 March 2012 Fix for bug #105270 */
			$("#" + this._id("featurechooser_dd_li_" + columnKey, featureName) +
				" span.ui-iggrid-featurechooserddlistitemtext").text(text);
		},
		_analyzeFeatures: function () {
			if (this._isFeaturesAnalyzed === true) {
				return;
			}

			var i, k,
			cs = this.grid.options.columns,
			csLength = cs.length,
			featureName,
			featuresLength = this._features.length,
			features = this._features,
			newFeatures = [  ],
			columnsFeature,
			columnsFeatureLength,
			featureInstance,
			columnKey;

			for (i = 0; i < featuresLength; i++) {
				featureName = features[ i ];
				featureInstance = this.grid.element.data("igGrid" + featureName);

				if (featureInstance === undefined || featureInstance === null) {
					continue;
				}

				if (featureInstance.renderInFeatureChooser !== true) {
					continue;
				}

				if (featureInstance._columnMap === undefined || featureInstance._columnMap === null) {
					continue;
				}

				columnsFeature = featureInstance._columnMap();
				if (columnsFeature === false || columnsFeature === null || columnsFeature === undefined) {
					/* there is no column settings for specified feature */
					continue;
				}

				columnsFeatureLength = columnsFeature.length;
				if (columnsFeatureLength === 0) {
					/* feature should be shown in all columns */
					for (k = 0; k < csLength; k++) {
						columnKey = cs[ k ].key;
						if (this.analyzedData[ columnKey ] === undefined || this.analyzedData[ columnKey ] === null) {
							this.analyzedData[ columnKey ] = [  ];
						}

						if (this._isFeatureExistByColumnKey(featureName, columnKey) === true) {
							continue;
						}
						this.analyzedData[ columnKey ].push(
						{
							name: featureName,
							method: null,
							text: null,
							iconClass: null,
							isSelected: false,
							columnCell: null,
							isActive: false
						}
					);
					}
				} else {
					for (k = 0; k < columnsFeatureLength; k++) {
						columnKey = columnsFeature[ k ].columnKey;
						if (this.analyzedData[ columnKey ] === undefined ||
								this.analyzedData[ columnKey ] === null) {
							this.analyzedData[ columnKey ] = [  ];
						}

						if (columnsFeature[ k ].enabled !== true ||
							this._isFeatureExistByColumnKey(featureName, columnKey) === true) {
							continue;
						}

						this.analyzedData[ columnKey ].push(
							{
								name: featureName,
								method: null,
								text: null,
								iconClass: null,
								isSelected: false,
								columnCell: null,
								isActive: false
							}
						);
					}
				}
				newFeatures.push({ name: featureName, instance: featureInstance });
			}

			this._features = newFeatures;
			this._isFeaturesAnalyzed = true;
		},
		_isFeatureExistByColumnKey: function (featureName, columnKey) {
			var aData = this.analyzedData[ columnKey ], ret = false;

			if (aData === null || aData === undefined) {
				return true;
			}

			$.each(aData, function (index, value) {
				if (value.name === featureName) {
					ret = true;
					return false;
				}
			});

			return ret;
		},
		_shouldRenderInFeatureChooser: function (columnKey) {
			/* it is called only for the first time when _shouldRenderInFeatureChooser */
			this._analyzeFeatures();

			if (this.analyzedData[ columnKey ] !== undefined && this.analyzedData[ columnKey ] !== null) {
				return this.analyzedData[ columnKey ].length > 1;
			}
			return false;
		},
		_headerRendered: function (sender, args) {
			/*prevent handling of other grids" events in the case of hierarchical grid */
			if (args.owner.element.attr("id") !== this.grid.element.attr("id")) {
				return;
			}
			if (sender.target.id !== this.grid.element[ 0 ].id) {
				return;
			}
			this._renderFCForAllColumns();
		},
		_renderFCForAllColumns: function () {
			/* render feature chooser for all columns */
			var self = this;
			$.each(this.data, function (columnKey, elem) {
				self._initFC(columnKey, elem);
			});
		},
		_touchStart: function (e, columnKey) {
			/* M.H. 10 Sep 2013 Fix for bug #151643: When you tap on a hiding indicator feature chooser opens and then hiding dropdown on a mobile device. */
			if ($(e.target).attr("data-skip-event")) {
				return e;
			}
			this.toggleDropDown(columnKey);
			e.preventDefault();
			e.stopPropagation();
		},
		_initFC: function (columnKey, elem) {
			var self = this,
				thead = this.grid.container().find("thead"),
				$columnCell,
				$theadCell,
				buttonId,
				cancelFunc,
				$button,
				$headerContainer;

			$columnCell = this.grid.container().find("#" + self.grid.element[ 0 ].id + "_" + columnKey);
			buttonId = self._id("featureChooser_headerButton", columnKey);
			if (!$columnCell.length) {
				return;
			}
			$columnCell.find("[ data-fc-button ]").remove();
			this.grid.container().find("#" + buttonId).remove();
			cancelFunc = function (e) { e.preventDefault(); e.stopPropagation(); };
			/* Cancel rendering is fired only when features are not rendered in feature chooser.  */
			/* Otherwise this means some features are rendered and then rendering of feature chooser is canceled */
			if (elem.isCancelledRendering !== false &&
					self._trigger(self.events.featureChooserRendering,
					null,
					{ owner: self.grid, columnKey: columnKey, columnCell: $columnCell }) === false) {
				elem.isCancelledRendering = true;
				return;
			}
			elem.isCancelledRendering = false;
			if (this._isTouchDevice()) {
				/* M.H. 9 Sep 2013 Fix for bug #151657: Tapping on fixed column's header does not open the feature chooser on Ipad. */
				/* this is a hack for MS Surface devices - we should use bind instead of live */
				/* M.H. 16 Sep 2013 Fix for bug #152244: Grid is not shown and js error is thrown when it is used 1.7.2+ jQuery version and FeatureChooser should be shown */
				$columnCell.bind({
					click: function (e) {
						self._touchStart(e, columnKey);
					}
				});
			} else {
				$theadCell = thead.find("#" + self.grid.element[ 0 ].id + "_" + columnKey);
				this.grid._enableHeaderCellFeature($theadCell);
				$headerContainer = $theadCell.find(".ui-iggrid-indicatorcontainer");
				/* it is not set container for feature chooser in the header */

				if ($headerContainer.length === 0) {
					$headerContainer = $("<div></div>")
					.appendTo($theadCell)
					.addClass("ui-iggrid-indicatorcontainer");
				}
				/* M.H. 16 June 2014 Fix for bug #173692: Feature chooser and sorting indicator icons are not ordered  correctly, when initial sorting is applied */
				$button = $("<span></span>").prependTo($headerContainer).addClass(self.css.headerButtonIcon);
				/* K.D. July 15, 2015 Bug #201576 Adding role and title to the feature chooser button. */
				$button.wrap("<a id='" + buttonId + "' role='button' tabindex='" + self.grid.options.tabIndex +
					"' data-fc-button='" + columnKey + "' href='#' title='" +
					$.ig.Grid.locale.featureChooserTooltip + "'></a>");
				/* M.H. 22 March 2012 Fix for bug #105757 */
				$button.parent()
					.attr("th-remove-focus", "")
					.bind({
						/* M.H. 22 March 2012 Fix for bug #105783 */
						keydown: function (event) {
							var $item, $nextPrevItem, $dialog = $("#" + self._id("featureChooser_dd", columnKey));

							if (event.keyCode === $.ui.keyCode.ENTER || event.keyCode === $.ui.keyCode.SPACE) {
								$item = $dialog.find("ul li.ui-iggrid-featurechooser-listitem-hover");
								if ($dialog.is(":visible") && $item.length > 0) {
									if ($item.find("*[ role='button' ]").length) {
										event.target = $item.find("*[ role='button' ]:eq(0)");
									} else {
										event.target = $item[ 0 ];
									}
									self._clickFeature(event);
								}
								self.toggleDropDown(columnKey);
								cancelFunc(event);
							} else if (event.keyCode === $.ui.keyCode.DOWN || event.keyCode === $.ui.keyCode.UP) {
								if ($dialog.is(":visible")) {
									$item = $dialog.find("ul li.ui-iggrid-featurechooser-listitem-hover");
									if ($item.length > 1) {
										$.each($item, function (index, value) {
											self._removeCssSelectionListItem($(value));
										});
									}
									$nextPrevItem = ((event.keyCode === $.ui.keyCode.DOWN) ? $item.next() : $item.prev());
									if ($nextPrevItem.attr("data-fc-separator")) {
										$nextPrevItem = ((event.keyCode === $.ui.keyCode.DOWN) ?
											$nextPrevItem.next() : $nextPrevItem.prev());
									}
									if ($item.length === 0) {
										self._addCssSelectionListItem($dialog.find("ul li:eq(0)"));
									} else if ($nextPrevItem.length > 0 && $nextPrevItem.is("li")) {
										self._removeCssSelectionListItem($item);
										self._addCssSelectionListItem($nextPrevItem);
									}
									cancelFunc(event);
								}
							}
						},
						mousedown: function (event) {
							self.toggleDropDown(columnKey);
							cancelFunc(event);
						},
						mouseover: function () {
							if ($button.hasClass(self.css.headerButtonIconSelected) === false) {
								$button.addClass(self.css.headerButtonIconMouseOver);
							}
						},
						mouseout: function () {
							$button.removeClass(self.css.headerButtonIconMouseOver);
						},
						mouseup: cancelFunc,
						click: cancelFunc
					});
				/* M.H. 20 Feb 2013 Fix for bug #132371: Hierarchical grid - can not hide a column on a child layout */
				/* we should remove dropdown containers - we should clear inner data with grid reference otherwise in child layout it is save the "old" reference */
				this.grid.container().find("#" + this._id("featureChooser_dd", columnKey)).remove();
				}
			self._trigger(
				self.events.featureChooserRendered,
				null,
				{ owner: self.grid, columnKey: columnKey, columnCell: $columnCell }
			);
		},
		_renderInFeatureChooser: function (columnKey, data) {
			/* render icon of the feature widget ONLY if it the column key exists in the analyzed data with keys */
			var i,
			columnData = this.analyzedData[ columnKey ],
			columnDataLength;

			if (columnData === undefined || columnData === null) {
				/* question: throw exception or just return */
				return;
			}

			columnDataLength = columnData.length;
			if (columnData.isCancelledRendering === true) {
				return;
			}

			for (i = 0; i < columnDataLength; i++) {
				if (columnData[ i ].name.toLowerCase() === data.name.toLowerCase()) {
					this.analyzedData[ columnKey ][ i ] = data;
					break;
				}
			}

			if (i === columnDataLength) {
				this.analyzedData[ columnKey ].push(data);
			}
			if (this.data[ columnKey ] === null || this.data[ columnKey ] === undefined) {
				this.data[ columnKey ] = [  ];
			}
			if ($.type(this.data[ columnKey ].order) !== "number") {
				this.data[ columnKey ].order = 0;
			}

			/* check whether for this columnKey it is already registered this feature */
			/* example when autoGenerateColumns is true - _renderInFeatureChooser method is called twice for each feature */
			for (i = 0; i < this.data[ columnKey ].length; i++) {
				if (this.data[ columnKey ][ i ].name === data.name && data.name !== undefined) {
					return;
				}
			}
			this.data[ columnKey ].push(data);
		},
		_renderMenu: function (columnKey) {
			var i, data, self = this,
			listItems,
			popoverInstance,
			$targetButton,
			$headerCell = $("#" + this.grid.id() + "_" + columnKey),
			dropDownId = this._id("featureChooser_dd", columnKey),
			$dropDown = $("#" + dropDownId),
			rootContainer = this.grid._rootContainer();
			if ($dropDown.length > 0) {
				return;
			}
			/* check whether dropdown container is initialized and if not - initialize */
			$dropDown = $("<div tabindex='0'></div>")
			.attr("id", dropDownId)
			/*.css({"position": "absolute", "display": "none"}) */
			.appendTo(rootContainer);
			$targetButton = $("#" + this._id("featureChooser_headerButton", columnKey)).find("span");
			if ($targetButton.length === 0) {
				$targetButton = $headerCell;
			}
			/* M.H. 25 Sep 2013 Fix for bug #153407: FC is positioned according to the header cell but the arrow is positioned next to the gearbox */
			$dropDown.igGridFeatureChooserPopover({
				/* T.G. 15 Jan 2014 Fix for bug 161249 - Target option is not needed because the user expects it to be the element on which the popover is initialized */
				/* target: $targetButton, */
				position: "auto",
				targetButton: $targetButton,
				maxWidth: this.grid.container().width(),
				direction: "bottom",
				maxHeight: null,
				containment: rootContainer,
				appendTo: rootContainer
			});
			popoverInstance = $dropDown.data("igGridFeatureChooserPopover");
			/*popoverInstance.container().attr("tabindex", "0"); */
			$dropDown.bind("iggridfeaturechooserpopovershown", function () {
				self._visiblePopover = dropDownId;
				/*popoverInstance.container().focus().data("onFocus", true); */
			});
			$dropDown.bind("iggridfeaturechooserpopoverhiding", function () {
				if (self._activeSubmenuId) {
					$("#" + self._activeSubmenuId).hide();
					self._activeSubmenuId = null;
				}
				/*$dropDown.find("[ data-submenu ]").hide(); */
				/*popoverInstance.container().focus().data("onFocus", true); */
			});
			data = this.data[ columnKey ];

			if (data) {
				for (i = 0; i < data.length; i++) {
					this._renderDropDownItem(columnKey, data[ i ]);
				}
				listItems = popoverInstance.container().find("li[ data-fc-order ]");
				$dropDown.igGridFeatureChooserPopover("registerElements", listItems);
				$targetButton.bind({
					keydown: function (e) {
						if (e.keyCode === $.ui.keyCode.ESCAPE) {
							popoverInstance._closePopover();
							/* T.B. 19 August 2015 Fix for bug 204068: Grid's Feature Chooser does not stop propagation */
							e.stopPropagation();
						} else if (e.keyCode === $.ui.keyCode.TAB &&
							!e.shiftKey &&
							popoverInstance.isShown()) {
								popoverInstance.popover.find("li[ data-fc-order ]:first").focus();
								e.stopPropagation();
								e.preventDefault();
						}
					}
				});
				listItems.bind({
					keydown: function (e) {
						var $elem, keyCode = e.keyCode;

						switch (keyCode) {
							case $.ui.keyCode.ESCAPE:
								$targetButton.data("onFocus", false).focus();
								popoverInstance._closePopover();
								break;
							case $.ui.keyCode.TAB:
								$elem = $(this);
								if (!e.shiftKey) {
									if ($elem.is(":last-child")) {
										$elem.closest("ul").find("li:first-child").focus();
										e.preventDefault();
									}
									/*e.stopPropagation(); */
								} else {
									if ($elem.is(":first-child")) {
										$elem.closest("ul").find("li:last-child").focus();
										e.preventDefault();
									}
								}
								break;
							case $.ui.keyCode.RIGHT:
								$elem = $(this);
								$elem = $elem.nextAll("li[ data-fc-order ]").eq(0);
								if ($elem.length === 0) {
									$elem = $(this).closest("ul").find("li:first-child");
								}
								$elem.focus();
								e.preventDefault();
								break;
							case $.ui.keyCode.LEFT:
								$elem = $(this);
								$elem = $elem.prevAll("li[ data-fc-order ]").eq(0);
								if ($elem.length === 0) {
									$elem = $(this).closest("ul").find("li:last-child");
								}
								$elem.focus();
								e.preventDefault();
								break;
						}
					}
				});
			}
			$dropDown.bind({
				keydown: function (event) {
					if (event.keyCode === $.ui.keyCode.ESCAPE && popoverInstance.container().is(":visible")) {
						self.toggleDropDown(columnKey);
						popoverInstance.container().blur();
					}
				}
			});
			/*this._clearSections($dropDown); */
		},
		_removeFeature: function (featureName, submenu) {
			var self = this, cols = this.grid.options.columns;

			$.each(cols, function (index, col) {
				self._removeDropDownItem(col.key, featureName);
				if (submenu) {
					$("#" + self._id("featurechooser_submenu_" + col.key, featureName)).remove();
				}
			});
		},
		_removeDropDownItem: function (columnKey, featureName) {
			var index = -1, data, $dropDown = $("#" + this._id("featureChooser_dd", columnKey)),
				$dropDownList = $("#" + this._id("featurechooser_dd_list", columnKey)),
				$item = $("#" + this._id("featurechooser_dd_li_" + columnKey, featureName));

			if ($dropDown.length === 0) {
				data = this.data[ columnKey ];
				if (data) {
					$.each(data, function (i, dataItem) {
						if (dataItem.name === featureName) {
							index = i;
							return false;
						}
					});
					if (index > -1) {
						data.splice(index, 1);
						if (data.length === 0) {
							this._removeFC(columnKey);
						}
					}
				}
			} else if ($item.length > 0) {
				$item.remove();
				if ($dropDownList.find("li:not([ data-fc-separator ])").length === 0) {
					this._removeFC(columnKey);
				} else {
					this._removeSeparatorItem(columnKey);
				}
			}
		},
		_removeFC: function (columnKey) {
			var $dropDown = $("#" + this._id("featureChooser_dd", columnKey));

			$dropDown.igGridFeatureChooserPopover("destroy");
			$dropDown.remove();
			$("#" + this._id("featureChooser_headerButton", columnKey)).remove();
		},
		_removeSeparatorItem: function (columnKey) {
			var listLiSeparator = $("#" + this._id("featurechooser_dd_list", columnKey))
				.find("[ data-fc-separator ]");

			listLiSeparator.each(function () {
				var $li = $(this),
					$prevLi = $li.prev(),
					$nextLi = $li.next();
				if ($prevLi.length === 0 || $prevLi.attr("data-fc-separator") !== undefined ||
					$nextLi.length === 0 || $nextLi.attr("data-fc-separator") !== undefined) {
					$li.remove();
					return false;
				}
			});
		},
		_renderDropDownItem: function (columnKey, data) {
			var dropDownId = this._id("featureChooser_dd", columnKey),
				$dropDownContainer = $("#" + dropDownId).igGridFeatureChooserPopover("container"),
				$li, listId, $list,
				funcClickOnFeature,
				$iconContainer, $span,
				cssClassIcon = "",
				self = this,
				isSelected = data.isSelected,
				featureName = data.name,
				innerData,
				liId = this._id("featurechooser_dd_li_" + columnKey, featureName),
				groupName = data.groupName,
				groupOrder = data.groupOrder,
				listItemsGroup,// items which belongs to specific group (or does not belong to any group)
				insertElementObj,
				labelText;

			/* M.H. 10 Oct. 2011 Fix for bug #89870 */
			if ($("#" + liId).length > 0) {
				return;
			}
			if (groupName === undefined || groupName === null) {
				groupName = "click";
			}
			listId = this._id("featurechooser_dd_list", columnKey);
			$list = $("#" + listId);
			if ($list.length === 0) {
				$dropDownContainer.html("");
				$list = $("<ul></ul>")
					.attr("id", listId)
					.addClass(this.css.listClass)
					.appendTo($dropDownContainer);
			}
			/* for instance default behavior for buttons is to be rendered on bottom of the list */
			if (isSelected === undefined || isSelected === null) {
				isSelected = false;
			}
			$li = $("<li tabindex='0'></li>")
				.addClass(this.css.listItemClass)
				.attr("id", liId)
				.attr("data-fc-order", data.order);
			if (groupName !== undefined && groupName !== null) {
				$li.attr("data-fc-groupname", groupName);
			}
			if (groupOrder !== undefined && groupOrder !== null) {
				$li.attr("data-fc-grouporder", groupOrder);
			}
			innerData = {
				columnKey: columnKey,
				featureName: featureName,
				updateOnClickAll: data.updateOnClickAll,
				iconClass: data.iconClass,
				iconClassOff: data.iconClassOff,
				groupName: groupName,
				groupOrder: groupOrder,
				type: data.type,
				textHide: data.textHide,
				state: data.state,
				text: data.text
			};
			$li.data("data", innerData);
			funcClickOnFeature = function (event) {
				var submenuId = self._id("featurechooser_submenu_" + columnKey, featureName),
					e = event,
					$submenu,
					keyCode = event.keyCode;

				if (data.type !== "dropdown") {
					if (!keyCode || keyCode === $.ui.keyCode.ENTER || keyCode === $.ui.keyCode.SPACE) {
						if (event.target === undefined) {
							e.target = event.srcElement;
						}
						self._clickFeature(event);
						self.hideDropDown(columnKey);
					}
				} else {
					/* M.H. 23 Oct 2013 Fix for bug #155672: The Column Moving submenu in Feature Chooser does not open in IE8. */
					if (self._visiblePopover) {
						if (!keyCode) {
							self._toggleSubmenu(columnKey, featureName, data);
							setTimeout(function () {
								$("#" + self._visiblePopover).data("igGridFeatureChooserPopover")._setFCElementFocus(true);
							}, 5);
						} else {
							if (keyCode === $.ui.keyCode.DOWN) {
								$submenu = $("#" + submenuId);
								if ($submenu.is(":visible")) {
									$submenu.find("[ data-fc-item ]:first").focus();
								} else {
									self._toggleSubmenu(columnKey, featureName, data);
								}
							} else if (keyCode === $.ui.keyCode.ENTER || keyCode === $.ui.keyCode.SPACE) {
								self._toggleSubmenu(columnKey, featureName, data);
							} else if (keyCode === $.ui.keyCode.UP) {
								$submenu = $("#" + submenuId);
								if ($submenu.is(":visible")) {
									self._toggleSubmenu(columnKey, featureName, data);
								}
							}
						}
					}
					/* M.H. 26 Sep 2013 Fix for bug #153462: Clicking on "Move To" icon in feature chooser causes FC to hide */
					/*event.stopPropagation(); */
					if (event.preventDefault) {
						event.preventDefault();
					}
				}
			};
			$li.bind({
				keydown: funcClickOnFeature,
				mousedown: funcClickOnFeature
			});
			if (data.iconClass !== null && data.iconClass !== undefined && data.iconClass !== "") {
				if (data.iconClassOff && data.isSelected === false) {
					cssClassIcon = data.iconClassOff;
				} else {
					cssClassIcon = data.iconClass;
				}
			}
			$iconContainer = $("<div></div>").addClass(this.css.listItemContainer).appendTo($li);
			$span = $("<span></span>").addClass(this.css.listItemIconContainer).appendTo($iconContainer);
			if (cssClassIcon !== "") {
				$span.addClass(cssClassIcon);
			} else {
				$span.addClass(this.css.itemNoIcon);
			}
			labelText = data.text;
			$li.attr("title", labelText);
			$("<span></span>").text(data.text).addClass(this.css.listItemText).appendTo($iconContainer);
			/* order by groups */
			listItemsGroup = $("#" + listId + " li[ data-fc-groupName = " + groupName + " ]");
			if (listItemsGroup.length === 0) {
				/* we should add such group */
				/* first we should find its position - where to insert */
				listItemsGroup = $("#" + listId + " li[ data-fc-groupName ]");
				insertElementObj = this._getInsertElement(listItemsGroup, groupOrder, "data-fc-grouporder");
				if (insertElementObj.item !== null) {
					$list = listItemsGroup;
				}
				this._insertElement(insertElementObj, $list, $li, groupName);
			} else {
				/* we should find the correct position of element inside the group */
				insertElementObj = this._getInsertElement(listItemsGroup, data.order, "data-fc-order");
				this._insertElement(insertElementObj, listItemsGroup, $li, groupName);
			}
			if (data.type === "toggle") {
				this._setSelectedItem(columnKey, data.isSelected, $li);
			}
			if (data.type === "dropdown") {
				$("<span class='" + this.css.submenuIcon +
					"' data-submenu-arrow='true'></span>").appendTo($iconContainer);
				self._renderSubmenu(columnKey, featureName, data);
			}
		},
		_renderSubmenu: function (columnKey, featureName, data) {
			var submenuId = this._id("featurechooser_submenu_" + columnKey, featureName),
			$submenu;

			if ($("#" + submenuId).length > 0) {
				$("#" + submenuId).remove();
			}
			if (this._submenus === null || this._submenus === undefined) {
				this._submenus = [  ];
			}
			this._submenus.push(submenuId);
			$submenu = $("<div id='" + submenuId +
				"' style='position:absolute' class='" + this.css.submenu + "'></div>")
				.appendTo(this.grid._rootContainer());
			$submenu.data("buttonId", this._id("featurechooser_dd_li_" + columnKey, featureName));
			$submenu.hide();
			if (data.methodRenderSubmenu) {
				data.methodRenderSubmenu(columnKey, $submenu);
				$("#" + this._id("featureChooser_dd", columnKey))
					.igGridFeatureChooserPopover("registerElements", $submenu.find("[ data-fc-item ]"));
			}
		},
		_toggleSubmenu: function (columnKey, featureName, data) {
			var rOffset, $li = $("#" + this._id("featurechooser_dd_li_" + columnKey, featureName)),
				$innerDiv = $li.find("div:nth-child(1)"),
				targetWidth = $innerDiv.outerWidth(),
				targetLeft = $innerDiv.offset().left,
				submenuId = this._id("featurechooser_submenu_" + columnKey, featureName),
				$submenu = $("#" + submenuId),
				left = targetLeft - (Math.abs($submenu.outerWidth() - targetWidth) / 2),
				top = $innerDiv.offset().top + $innerDiv.outerHeight();

			/* we should position submenu */
			rOffset = $.ig.util.getRelativeOffset($submenu);
			/* we should position submenu */
			$submenu.css({
				"left": left - rOffset.left,
				"top": top - rOffset.top
			});
			if (data.methodToggleSubmenu) {
				data.methodToggleSubmenu(columnKey, !$submenu.is(":visible"), $submenu);
			}
			if (this._activeSubmenuId && this._activeSubmenuId !== submenuId) {
				$("#" + this._activeSubmenuId).hide();
			}
			this._activeSubmenuId = submenuId;
			/* show/hide submenu */
			$submenu.toggle(
				"slide",
				{
					duration: 150,
					direction: "up"
				}
			);
		},
		_getSeparatorItem: function () {
			return $("<li data-fc-separator='1' class='" + this.css.separator + "'></li>");
		},
		_insertElement: function (insertElementObj, $list, $li, groupName) {
			/* insert element $li at the correct position in $list */
			/* insertElementObj - the element before/after the element $li should be inserted - if null insert it at the end of $list */
			/* groupName - name of the Group of $li */
			var $insertElement = insertElementObj.item,
				insertElementPosition = insertElementObj.position,
				$prevElement;

			if ($insertElement === null) {
				$li.appendTo($list);

				/* check for separator UP */
				$prevElement = $li.prev();
				if ($prevElement !== undefined &&
						$prevElement.length > 0 &&
						$prevElement.attr("data-fc-separator") !== "1" &&
						$prevElement.attr("data-fc-groupname") !== undefined &&
						$prevElement.attr("data-fc-groupname") !== groupName) {
					/* add separator */
					this._getSeparatorItem().insertBefore($li);
				}
			} else {
				if (insertElementPosition === "after") {
					$li.insertAfter($insertElement);
					/* check for separator UP */
					if ($insertElement !== undefined &&
							$insertElement.length > 0 &&
							$insertElement.attr("data-fc-separator") !== "1" &&
							$insertElement.attr("data-fc-groupname") !== undefined &&
							$insertElement.attr("data-fc-groupname") !== groupName) {
						/* add separator */
						this._getSeparatorItem().insertBefore($li);
					}
				} else {
					$li.insertBefore($insertElement);
					$prevElement = $li.prev();
					/* check for separator UP */
					if ($prevElement !== undefined &&
							$prevElement.length > 0 &&
							$prevElement.attr("data-fc-separator") !== "1" &&
							$prevElement.attr("data-fc-groupname") !== undefined &&
							$prevElement.attr("data-fc-groupname") !== groupName) {
						/* add separator */
						this._getSeparatorItem().insertBefore($li);
					} else if ($insertElement !== undefined &&
							$insertElement.length > 0 &&
							$insertElement.attr("data-fc-separator") !== "1" &&
							$insertElement.attr("data-fc-groupname") !== undefined &&
							$insertElement.attr("data-fc-groupname") !== groupName) {
						/* add separator */
						this._getSeparatorItem().insertAfter($li);
					}

				}
			}
		},
		_getInsertElement: function (listItems, targetOrder, attr) {
			/* return from list of items object which is of type {item: $item, position: pos} */
			/* where $item is jQuery wrapped DOM element or NULL */
			/* if $item is null insert at the beginning of the list */
			/* if $item is NOT null then if pos is: */
			/* "before" insert element before $item */
			/* "after" insert element after $item */
			var $item = null, itemOrder, at, i;

			for (i = 0; i < listItems.length; i++) {
				$item = $(listItems[ i ]);
				at = $item.attr(attr);

				if (at === undefined) {
					continue;
				}
				itemOrder = parseInt(at, 10);
				if (itemOrder > targetOrder) {
					return { item: $item, position: "before" };
				}
			}

			return { item: $item, position: "after" };
		},
		_setSelectedState: function (featureName, columnKey, isSelected, executeCallback) {
			var data = this.data[ columnKey ], $listItem, self = this;

			if (data === undefined || data === null) {
				return false;
			}
			$.each(data, function (index, value) {
				if (value.name.toLowerCase() === featureName.toLowerCase()) {
					$listItem = $("#" + self._id("featurechooser_dd_li_" + columnKey, value.name));
					self._setSelectedItem(columnKey, isSelected, $listItem);
					self.data[ columnKey ][ index ].isSelected = !isSelected;
					if (executeCallback === true) {
						self._callFeatureMethod(value, isSelected, columnKey, null);
					}
					value.isSelected = isSelected;
					return false;
				}
			});
		},
		_setSelectedItem: function (columnKey, isSelected, $listItem) {
			if ($listItem === null || $listItem === undefined || $listItem.length === 0) {
				return;
			}
			var innerData = $listItem.data("data"),
				textShow,
				textHide,
				labelText,
				$label = null;

			if (innerData !== undefined && innerData !== null) {
				textShow = innerData.text;

				textHide = innerData.textHide;
				$label = $listItem.find("span.ui-iggrid-featurechooserddlistitemtext");
			}
			$listItem.attr("data-fc-selected", isSelected);
			if (isSelected === true) {
				$listItem.addClass(this.css.itemSelected);
				labelText = textShow;
				if ($label !== null && textShow !== null && textShow !== undefined) {
					$label.text(labelText);
				}
				if (innerData.iconClassOff) {
					$listItem.find("span.ui-iggrid-featurechooser-li-iconcontainer")
						.removeClass(innerData.iconClassOff)
						.addClass(innerData.iconClass);
				}
			} else {
				$listItem.removeClass(this.css.itemSelected);
				labelText = textHide;
				if ($label !== null && textHide !== null && textHide !== undefined) {
					$label.text(labelText);
				}
				if (innerData.iconClassOff) {
					$listItem.find("span.ui-iggrid-featurechooser-li-iconcontainer")
						.removeClass(innerData.iconClass)
						.addClass(innerData.iconClassOff);
				}
			}
			$listItem.attr("title", labelText);
		},
		/* M.H. 2 Aug 2012 Fix for bug #118136 */
		_toggleSelectedItems: function (featureName, selected) {
			var i, $li, columnKey, cols = this.grid.options.columns,
				colsLength = cols.length, isSelected, feature;
			if (selected !== undefined) {
				isSelected = selected;
			}
			for (i = 0; i < colsLength; i++) {
				columnKey = cols[ i ].key;
				/* M.H. 9 Apr 2014 Fix for bug #169578: igGridFiltering.toggleFilterRowByFeatureChooser method doesn"t update feature chooser labels. */
				/*if dropdown is not rendered then update the data(it is used when rendering dropdown items) */
				if ($("#" + this._id("featureChooser_dd", columnKey)).length === 0) {
					feature = this._getFeatureByKeyName(columnKey, featureName);
					if (feature === null || feature === undefined) {
						continue;
					}
					/* M.H. 23 Jul 2013 Fix for bug #144214: The "Fix Column" option changes even when column fixing is refused. */
					if (isSelected === undefined) {
						isSelected = feature.isSelected;
					}
				} else {
					$li = $("#" + this._id("featurechooser_dd_li_" + columnKey, featureName));
					if ($li.data("data") === null || $li.data("data") === undefined) {
						continue;
					}
					if (selected === undefined) {
						isSelected = $li.attr("data-fc-selected") === "true";
					}
				}
				this._setSelectedState(featureName, columnKey, !isSelected);
				/*this._setSelectedItem(columnKey, !($li.attr("data-fc-selected") === "true"), $li); */
			}
		},
		showDropDown: function (columnKey) {
			/* Show feature chooser dialog by the specified column key
			paramType="string" Key of the column that should be shown.
			*/
			var $targetButton, dropDownId = this._id("featureChooser_dd", columnKey),
				$dropDown = this.getDropDownByColumnKey(columnKey), fcp;
			/* M.H. 3 Oct 2013 Fix for bug #153904: When you open feature chooser and move column by dragging the feature chooser does not close. */
			this.grid._focusedElement = $("#" +
				this._id("featureChooser_headerButton", columnKey)).find("span");
			$targetButton = $("#" + this._id("featureChooser_headerButton", columnKey)).find("span");
			if ($targetButton.length === 0) {
				$targetButton = $("#" + this.grid.id() + "_" + columnKey);
			}
			if (this._visiblePopover && dropDownId !== this._visiblePopover) {
				fcp = $("#" + this._visiblePopover).data("igGridFeatureChooserPopover");
				if (fcp) {
					fcp._closePopover();
				}
				setTimeout(function () {
					$dropDown.data("igGridFeatureChooserPopover")._openPopover($targetButton, false);
				}, 100);
			} else {
				$dropDown.data("igGridFeatureChooserPopover")._openPopover($targetButton, false);
			}
		},
		hideDropDown: function (columnKey) {
			/* Hide feature chooser dialog by column key
			paramType="string" Key of the column that should be hidden.
			*/
			var $dropDown = this.getDropDownByColumnKey(columnKey);
			$dropDown.igGridFeatureChooserPopover("hide");
			/* M.H. 3 Oct 2013 Fix for bug #153904: When you open feature chooser and move column by dragging the feature chooser does not close. */
			this.grid._focusedElement = null;
		},
		getDropDownByColumnKey: function (columnKey) {
			/* Get jQuery representation of the div holding dropdown for the specified columnKey
			paramType="string" Key of the column
			*/
			var dropDownId = this._id("featureChooser_dd", columnKey),
				$dropDown = $("#" + dropDownId);
			if ($dropDown.length === 0) {
				this._renderMenu(columnKey);
				$dropDown = $("#" + dropDownId);
			}
			return $dropDown;
		},
		toggleDropDown: function (columnKey) {
			/* Show/hide feature chooser dialog by column key
			paramType="string" Key of the column that should be shown/hidden.
			*/
			var $dropDown = this.getDropDownByColumnKey(columnKey),
				isVisible = $dropDown.igGridFeatureChooserPopover("isShown");
			this._trigger(this.events.menuToggling, null,
				{ isVisible: isVisible, columnKey: columnKey, owner: this });
			if (isVisible) {
				this.hideDropDown(columnKey);
			} else {
				this.showDropDown(columnKey);
			}
		},
		_mouseOverDropDownItem: function (event) {
			this._addCssSelectionListItem($(event.currentTarget));
		},
		_mouseOutDropDownItem: function (event) {
			this._removeCssSelectionListItem($(event.currentTarget));
		},
		_addCssSelectionListItem: function ($listItem) {
			$listItem.addClass(this.css.dropDownListItemHover);
		},
		_removeCssSelectionListItem: function ($listItem) {
			$listItem.removeClass(this.css.dropDownListItemHover);
		},
		_virtualHorizontalScroll: function () {
			this._renderFCForAllColumns();
		},
		destroy: function (e, args) {
			var i, l, self = this, sbm = this._submenus, grid;
			/* M.H. 20 Mar 2015 Fix for bug 190598: FeatureChooser retains incorrect state for child grids after the parent is re-rendered on touch device */
			/* it is called destroy of feature chooser of the parent grid */
			if (args && args.owner) {
				grid = args.owner;
				if (grid && grid.id() !== this.gridId) {
					return;
				}
			}
			this.data = null;
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			$.each(this.grid.options.columns, function (ind, column) {
				var $dd = $("#" + self._id("featureChooser_dd", column.key));

				if ($dd.length > 0) {
					$dd.igGridFeatureChooserPopover("destroy");
					$dd.remove();
				}
			});
			if (sbm) {
				l = sbm.length;
				for (i = 0; i < l; i++) {
					$("#" + sbm[ i ]).remove();
				}
			}
			if (this._headerRenderedHandler) {
				this.grid.element.unbind("iggridheaderrendered", this._headerRenderedHandler);
			}
			if (this._gridDestroyedHandler) {
				this.grid.element.unbind("igcontroldestroyed", this._gridDestroyedHandler);
			}
			if (this._gridRenderedHandler) {
				this.grid.element.unbind("iggridrendered", this._gridRenderedHandler);
			}
			if (this._virtualHorizontalScrollHandler) {
				this.grid.element.unbind("iggridvirtualhorizontalscroll", this._virtualHorizontalScrollHandler);
			}
			delete this._gridRenderedHandler;
			delete this._headerRenderedHandler;
			delete this._gridDestroyedHandler;
			delete this._virtualHorizontalScrollHandler;
			$.Widget.prototype.destroy.apply(this, arguments);
			return this;
		}
	});
	$.extend($.ui.igGridFeatureChooser, { version: "16.1.20161.2145" });
}(jQuery));

