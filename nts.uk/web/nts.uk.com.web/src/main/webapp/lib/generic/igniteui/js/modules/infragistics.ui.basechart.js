/*!@license
* Infragistics.Web.ClientUI FunnelChart 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends:
*     jquery-1.9.1.js
*     jquery.ui.core.js
*     jquery.ui.widget.js
*     infragistics.templating.js (Optional)
*     infragistics.util.js
*/

/*global jQuery */
if (typeof (jQuery) === "undefined") {
	throw new Error("jQuery is undefined");
}

(function ($) {

	// igBaseChart definition
	$.widget("ui.igBaseChart", {
		css: {
			/* Get the class applied to the tooltip element. */
			tooltip: "ui-widget-content ui-corner-all",
		    /* Get the class applied to main element, shown when the chart is opened in a non HTML5 compatible browser. */

			//unsupportedBrowserClass: "ui-chart-non-html5-supported-message ui-helper-clearfix ui-chart-non-html5"
			unsupportedBrowserClass:
                "ui-html5-non-html5-supported-message ui-helper-clearfix ui-html5-non-html5"
		},
		events: {
			/* cancel="true" Event which is raised before data binding.
				Return false in order to cancel data binding.
				Function takes first argument null and second argument ui.
				Use ui.owner to obtain reference to chart widget.
				Use ui.dataSource to obtain reference to instance of $.ig.DataSource.
			*/
			dataBinding: null,
			/* cancel="false" Event which is raised after data binding.
				Function takes first argument null and second argument ui.
				Use ui.owner to obtain reference to chart widget.
				Use ui.data to obtain reference to array actual data which is displayed by chart.
				Use ui.dataSource to obtain reference to instance of $.ig.DataSource.
			*/
			dataBound: null,
			/* cancel="true" Event which is raised before tooltip is updated.
				Return false in order to cancel updating and hide tooltip.
				Function takes first argument null and second argument ui.
				Use ui.owner to obtain reference to chart widget.
				Use ui.text to obtain html of tooltip. Value of that member can be modified. If modified value is null or empty string, then current content of tooltip keeps old value.
				Use ui.item to obtain reference to item. Value of that member can be modified or replaced by custom item.
				Use ui.x to obtain left position of tooltip in pixels relative to widget. Value of that member can be modified.
				Use ui.y to obtain top position of tooltip in pixels relative to widget. Value of that member can be modified.
				Use ui.element to obtain reference to jquery object which represents tooltip. Value of that member can be replaced by custom element.
			*/
			updateTooltip: null,
			/* cancel="true" Event which is raised before tooltip is hidden.
				Return false in order to cancel hiding and keep tooltip visible.
				Function takes first argument null and second argument ui.
				Use ui.owner to obtain reference to chart widget.
				Use ui.item to obtain reference to item.
				Use ui.element to obtain reference to jquery object which represents tooltip or value of ui.element from last updateTooltip event. Value of that member can be replaced by custom element.
			*/
			hideTooltip: null
		},
		options: {
			/* type="number" The width of the chart. */
			width: null,
			/* type="number" The height of the chart. */
			height: null,
			/* type="string" Gets sets template for tooltip associated with chart item.
				Example: "Value: $(ValueMemberPathInDataSource)"
			*/
			tooltipTemplate: null,
			/* type="number" Gets sets maximum number of displayed records in chart. */
			maxRecCount: null,
			/* type="object" Gets sets a valid data source.
				That can be instance of array or primitives, array of objects, instance of $.ig.DataSource, or any other data accepted by $.ig.DataSource.
				Note: if it is set to string and "dataSourceType" option is not set, then $.ig.JSONPDataSource is used.
			*/
			dataSource: null,
			/* type="string" Gets sets data source type (such as "json", "xml", etc). Please refer to the documentation of $.ig.DataSource and its type property */
			dataSourceType: null,
			/* type="string" Gets sets url which is used for sending JSON on request for remote data. */
			dataSourceUrl: null,
			/* type="string" see $.ig.DataSource. property in the response specifying the total number of records on the server. */
			responseTotalRecCountKey: null,
			/* type="string" see $.ig.DataSource. This is basically the property in the responses where data records are held, if the response is wrapped. */
			responseDataKey: null
		},

		// it should be extended by widget
		// handle dataSource, width and height options
		// base takes care about dataSource, width and height options and it also sets this._chart and provides container for chart
		// note: if(!this._chart){...} can be used as a flag that dataSource and other options were not initialized yet: suspend events, actions, etc.
		_create: function () {
			var key, v, size, chart,
				i = -1,
				self = this,
				elem = self.element,
				style = elem[ 0 ].style,
				o = self.options;

			// variable which holds initial attributes/styles of target widget, which are used to
			// restore target element on destroy
			self._oldState = {

				// extended widget may add other attributes to that member and they will be processed within destroy
				style: { position: style.position, width: style.width, height: style.height },
				elems: elem.find("*")
			};
			if (!$.ig.util._isCanvasSupported()) {
				$.ig.util._renderUnsupportedBrowser(this);
				return;
			}
			chart = self._createChart();
			self.dataBind();
			while (i++ < 1) {
				key = i === 0 ? "width" : "height";
				if (o[ key ]) {
					size = key;
                } else {
					v = elem[ 0 ].style[ key ];
					if (v && v.indexOf("%") > 0) {
						self._setSize(chart, size = key, v);
					}
				}
			}

			// _setSize should be called at least once: support for initially invisible container of char
			if (!size) {
				self._setSize(chart, "width");
			}
			this._beforeInitialOptions(chart, elem);
            this._setInitialOptions(chart);
			if (self.css.chart) {
				elem.addClass(self.css.chart);
			}
			self._chart = chart;
			self._dataChange();
			this._provideContainer(chart, elem);
		},

		_beforeInitialOptions: function () {

		},

		_provideContainer: function (chart, elem) {
			chart.provideContainer(elem[ 0 ]);
		},

		_setInitialOptions: function (chart) {
			var o = this.options, self = this, v;

			for (var key in o) {
				if (o.hasOwnProperty(key)) {
					v = o[ key ];
					if (v !== null) {
						/*jshint -W106*/
						self._set_option(chart, key, v); //jscs:ignore requireCamelCaseOrUpperCaseIdentifiers
						/*jshint +W106*/
					}
				}
			}
		},

		_fireTooltip: function (text, item, x, y) {
			var arg,
				t = this._ty;
			if (!text) {

				// custom tooltip element
				t = this._tte || t;
				if (t && t.css("display") !== "none" && this._trigger(
                    "hideTooltip", null, arg = { owner: this, element: t, item: this._tti })) {
					t = arg.element || t;
					t.css("display", "none");
				}
				return;
			}
			if (!t) {
				t = this._ty = $("<div style='position:absolute;display:none;white-space:nowrap;'></div>").
					addClass(this.css.tooltip).appendTo(this.element);
			}
			text = $.ig.tmpl ? $.ig.tmpl(text, item) : text;
			x = this._trigger("updateTooltip", null,
                arg = { owner: this, element: t, text: text, item: item, x: x, y: y });

			// custom tooltip element
			this._tte = t = arg.element || t;

			// last tooltip item
			this._tti = arg.item;
			if (!x) {
				t.css("display", "none");
			} else {
				t.css({ display: "block", left: arg.x + "px", top: arg.y + "px" });
				if (arg.text) {
					t.html(arg.text);
				}
			}
		},
		findIndexOfItem: function (item) {
			/* Find index of item within actual data used by chart.
				paramType="object" The reference to item.
				returnType="number" Returns -1 or index of item.
			*/
			var ds = item ? this.getData() : null, i = ds ? ds.length : 0;
			while (i-- > 0) {
				if (item === ds[ i ]) {
					break;
				}
			}
			return i;
		},
		getDataItem: function (index) {
			/* Get item within actual data used by chart. That is similar to this.getData()[ index ].
				paramType="index" Index of data item.
				returnType="object" Returns null or reference to data item.
			*/
			var ds = this.getData();
			return ds && ds.length > index && index >= 0 ? ds[ index ] : null;
		},
		getData: function () {
			/* Get reference of actual data used by chart.
				returnType="array" Returns null or reference to data.
			*/
			return this._chart ? this._chart.itemsSource() : null;
		},
		addItem: function (item) {
			/* adds a new item to the data source and notifies the chart.
				paramType="object" The item that we want to add to the data source.
				returnType="object" Returns a reference to this chart.
			*/
			if (this._dataSource) {
				this._dataEvt(1, true);
				this._dataSource.addRow(null, item, true);
			}
			return this;
		},
		insertItem: function (item, index) {
			/* inserts a new item to the data source and notifies the chart.
				paramType="object" the new item that we want to insert in the data source.
				paramType="number" The index in the data source where the new item will be inserted.
				returnType="object" Returns a reference to this chart.
			*/
			if (this._dataSource) {
				this._dataEvt(2, true);
				this._dataSource.insertRow(null, item, index, true);
			}
			return this;
		},
		removeItem: function (index) {
			/* deletes an item from the data source and notifies the chart.
				paramType="number" The index in the data source from where the item will be been removed.
				returnType="object" Returns a reference to this chart.
			*/
			if (this._dataSource) {
				this._dataEvt(-1, true);
				this._dataSource.deleteRow(index, true);
			}
			return this;
		},
		setItem: function (index, item) {
			/* updates an item in the data source and notifies the chart.
				paramType="number" The index of the item in the data source that we want to change.
				paramType="object" The new item object that will be set in the data source.
				returnType="object" Returns a reference to this chart.
			*/
			if (this._dataSource) {
				this._dataEvt(0, true);
				this._dataSource.updateRow(index, item, true);
			}
			return this;
		},
		notifySetItem: function (dataSource, index, newItem, oldItem) {
			/* Notifies the chart that an item has been set in an associated data source.
				paramType="object" optional="false" The data source in which the change happened.
				paramType="number" optional="false" The index in the items source that has been changed.
				paramType="object" optional="false" the new item that has been set in the collection.
				paramType="object" optional="false" the old item that has been overwritten in the collection.
				returnType="object" Returns a reference to this chart. */
			if (this._chart) {
				this._chart.notifySetItem(dataSource, index, oldItem, newItem);
				this._dataEvt(0);
			}
			return this;
		},
		notifyClearItems: function (dataSource) {
			/* Notifies the chart that the items have been cleared from an associated data source.
				It's not necessary to notify more than one target of a change if they share the same items source.
				paramType="object" optional="false" The data source in which the change happened.
				returnType="object" Returns a reference to this chart. */
			if (this._chart) {
				this._chart.notifyClearItems(dataSource);
				this._dataEvt(-1);
			}
			return this;
		},
		notifyInsertItem: function (dataSource, index, newItem) {
			/* Notifies the target axis or series that an item has been inserted at the specified index in its data source.
				It's not necessary to notify more than one target of a change if they share the same items source.
				paramType="object" optional="false" The data source in which the change happened.
				paramType="number" optional="false" The index in the items source where the new item has been inserted.
				paramType="object" optional="false" the new item that has been set in the collection.
				returnType="object" Returns a reference to this chart. */
			if (this._chart) {
				this._chart.notifyInsertItem(dataSource, index, newItem);
				this._dataEvt(2);
			}
			return this;
		},
		notifyRemoveItem: function (dataSource, index, oldItem) {
			/* Notifies the target axis or series that an item has been removed from the specified index in its data source.
				It's not necessary to notify more than one target of a change if they share the same items source.
				paramType="object" optional="false" The data source in which the change happened.
				paramType="number" optional="false" The index in the items source from where the old item has been removed.
				paramType="object" optional="false" the old item that has been removed from the collection.
				returnType="object" Returns a reference to this chart. */
			if (this._chart) {
				this._chart.notifyRemoveItem(dataSource, index, oldItem);
				this._dataEvt(-1);
			}
			return this;
		},

		// notification about data changes
		// extended class can use it as notification to trigger adjustments related to data changes: fix selection
		// params:
		// act: -1: remove item, 0-set item, 1-add item, 2-insert item, 3-new data
		// before: 1/true-before data was applied to chart, undefined/null/false-after data applied
		_dataEvt: function () {
		},
		_itemAdded: function (item, dataSource, dataSourceOwnerName) {
			var owner = this._getDataSourceOwner(dataSourceOwnerName);

			if (owner) {
				owner.notifyInsertItem(dataSource, dataSource.dataView().length - 1, item.row);
				this._dataEvt(1);
			}
		},
		_itemInserted: function (item, dataSource, dataSourceOwnerName) {
			var owner = this._getDataSourceOwner(dataSourceOwnerName);

			if (owner) {
				owner.notifyInsertItem(dataSource, item.rowIndex, item.row);
				this._dataEvt(2);
			}
		},
		_itemUpdated: function (item, dataSource, dataSourceOwnerName) {
			var owner = this._getDataSourceOwner(dataSourceOwnerName);

			if (owner) {
				owner.notifySetItem(dataSource, item.rowIndex, item.oldRow, item.newRow);
				this._dataEvt(0);
			}
		},
		_itemRemoved: function (item, dataSource, dataSourceOwnerName) {
			var owner = this._getDataSourceOwner(dataSourceOwnerName);

			if (owner) {
				owner.notifyRemoveItem(dataSource, item.rowIndex, item.row);
				this._dataEvt(-1);
			}
		},

		// it can return string: name of option, which holds column key for main data.
		// that is used when dataSource is array of primitives: [1.25, 34, 2, 5].
		// value returned by that method will build array of objects with desired key.
		// for example, if main data is defined by option "valueMemberPath", which value is equals
		//  to "val", then dataSource will be converted to [{val:1.25}, {val:34}, {val:2}, {val:5}]
		// if value of that option is missing, then "x" is used [{x:1.25}, {x:34}, {x:2}, {x:5}]
		_getValueKeyName: function () {
			return null;
		},

		// it can return array of field names.
		// that is used to optimize/reduce json when dataSourceUrl is set: only data with keys will be passed to client.
		// Example, if chart gets from data fields with options "valueMemberPath" and "labelMemberPath", which have values "Value1", "Label1",
		// then ["Value1", "Label1"] or [this.options.valueMemberPath, this.options.labelMemberPath] can be returned
		_getRemoteDataKeys: function () {
			return null;
		},

		// it can return string: name of chart-repaint method.
		// that is used by width/height option in order to trigger repaint of chart.
		// when repaint is required, then this._chart[this._getNotifyResizeName()]() will be called.
		_getNotifyResizeName: function () {
			return null;
		},

		// it should return new instance of chart, for example, new $.ig.XamFunnelChart()
		_createChart: function () {
			return null;
		},

		// it should be extended by widget.
		// base takes care about dataSource, width and height options and returns true for them.
		// base checks for existence of "set-key" method in chart and if it is missing, then true is returned.
		// base checks if new value is the same as "get-key" method and return true for that.
		// if base returns true, then widget should return true too.
		// extended widget should return true when chart or widget processed that property.
		// params:
		// chart-reference to chart object
		// key-name of option/property
		// value-value of option
		/*jshint -W106*/
		_set_option: function (chart, key, value) {//jscs:ignore requireCamelCaseOrUpperCaseIdentifiers
		/*jshint +W106*/
			if (!key) {
				return true;
			}
			if (key.indexOf("dataSource") >= 0 || key.indexOf("response") >= 0) {

				// _create() method calls dataBind before initialization
				if (this._chart) {
					this.dataBind();
				}
				return true;
			}
			if (key === "width" || key === "height") {
				this._setSize(chart, key, value);
				return true;
			}
			if (key === "maxRecCount") {
				if (this._chart) {
					this._dataChange();
				}
				return true;
			}
			if (key === "tooltipTemplate" && chart.toolTip) {
				chart.toolTip(value);
				return true;
			}
			if (!chart || !chart[ key ] || chart[ key ]() === value) {
				return true;
			}
		},

		// sets width and height options.
		// params:
		// chart-reference to chart object
		// key-name of option/property (width or height only)
		// value-value of option
		_setSize: function (chart, key, val) {
			$.ig.util.setSize(this.element, key, val, chart, this._getNotifyResizeName());
		},

		_getDataSourceOwner: function () {
			return this._chart;
		},

		_dataChange: function (noFire, dataSourceOwnerName) {
		    var owner;

			// Bug 135111: failure in remote dataSource
			if (!this._getDataSourceOwner) {
				return;
			}
			if (dataSourceOwnerName) {
				owner = this._getDataSourceOwner(dataSourceOwnerName);
				this._dataChangeInternal(owner, noFire);
			} else {
				this._dataChangeInternal(this._chart, noFire);
			}
		},

		// it is called when data is ready.
		// params:
		// if(noFire=="no"), then that is request to suppress firing dataBound event
		_dataChangeInternal: function (owner, noFire) {
			var data, len,
				max = this.options.maxRecCount,
				ds = this._dataSource,
				chart = owner;

			if (!ds || !chart || !chart.itemsSource) {
				return;
			}
			data = ds.dataView();
			len = data ? data.length : 0;
			if (!len && !this._dataLen) {
				return;
			}
			this._dataLen = len;
			if (len && max && max < len) {
				noFire = [];
				while (max-- > 0) {
					noFire[ max ] = data[ max ];
				}
				data = noFire;
			}
			chart.itemsSource(data);
			if (noFire !== "no") {
				this._trigger("dataBound", null, { owner: this, dataSource: ds, data: data });
			}
			this._dataEvt(3);
		},
		chart: function () {
			/* Get reference to chart object.
				returnType="object" Returns reference to chart.
			*/
			return this._chart;
		},
		dataBind: function () {
			this._dataBindInternal(this.options, null);
		},
		_dataBindInternal: function (options, dataSourceOwnerName) {
			/* Process new value of dataSource and trigger data binding. */
			var field, ds0, dataOptions, setting, bound,
				o = options,
				url = o.dataSourceUrl,
				key = o.responseDataKey,
				type = o.dataSourceType,
				valKeyName = this._getValueKeyName(),
				valKey = valKeyName ? o[ valKeyName ] : null,
				ds = o.dataSource,
				dsStr = typeof ds === "string",

				// assume key is something like "key1.key2.key3" and ds={key1:{key2:{key3:[actualData]}}}
				keys = key ? key.split(".") : null,
				len = keys ? keys.length - 1 : -1,
				i = -1;

			// check for something like http...netflix.com...
			if (dsStr && !type) {
				ds = new $.ig.JSONPDataSource({ dataSource: ds });
			}
			ds0 = ds;

			// find actualData within ds={key1:{key2:{key3:[actualData]}}}
			while (ds0 && i++ < len) {
				ds0 = ds0[ keys[ i ] ];
			}
			if (!ds0) {
				ds0 = ds;
				keys = null;
			}

			// first record/row
			field = ds0 ? ds0[ 0 ] : null;

			// convert simple array to array of objects
			if (typeof field === "string" || typeof field === "number" || (field && field.getTime)) {
				i = ds0.length;
				field = ds0;
				ds0 = [];
				valKey = valKey || "x";
				if (valKeyName) {
					o[ valKeyName ] = valKey;
				}
				while (i-- > 0) {
					ds0[ i ] = {};
					ds0[ i ][ valKey ] = field[ i ];
				}

				// process ds={key1:{key2:{key3:[actualData]}}}
				if (keys) {
					field = ds;
					i = -1;

					// find container of last/container object of real data within ds (key2)
					while (++i < len) {
						field = field[ keys[ i ] ];
					}

					// update actualData within ds={key1:{key2:{key3:[actualData]}}}
					field[ keys[ len ] ] = ds0;
				} else {
					ds = ds0;
				}
			}
		    /* fix regression after removing schema-logic on 04/09/2013 */

			// if valKey is undefined, then pickup first column
			if (ds0 && !valKey && valKeyName) {
				for (valKey in ds0[ 0 ]) {
					if (ds0[ 0 ].hasOwnProperty(valKey)) {
						o[ valKeyName ] = valKey;
						break;
					}
				}
			}
			dataOptions = {
			    callback: this._dataChange,

				// note: IE7/8 may fail properly to perform extend(...) with "this". Set callee later.
				//callee: this,
				dataSource: ds,
				type: type || undefined,
				responseDataKey: key,
				responseTotalRecCountKey: o.responseTotalRecCountKey,
				rowAdded: this._itemAdded,
				rowDeleted: this._itemRemoved,
				rowUpdated: this._itemUpdated,
				rowInserted: this._itemInserted
			};

			if (dataSourceOwnerName) {
				dataOptions.callback = function (nofire) { this._dataChange(nofire, dataSourceOwnerName); };
				dataOptions.rowAdded = function (item, dataSource) {
				    this._itemAdded(item, dataSource, dataSourceOwnerName);
				};
				dataOptions.rowDeleted = function (item, dataSource) {
				    this._itemRemoved(item, dataSource, dataSourceOwnerName);
				};
				dataOptions.rowUpdated = function (item, dataSource) {
				    this._itemUpdated(item, dataSource, dataSourceOwnerName);
				};
				dataOptions.rowInserted = function (item, dataSource) {
				    this._itemInserted(item, dataSource, dataSourceOwnerName);
				};
			}

			// configure data source
			if (ds && typeof ds._xmlToArray === "function" && typeof ds._encodePkParams === "function") {
				bound = ds._data && ds._data.length;
				dataOptions.dataSource = ds.settings.dataSource;
				ds.settings = $.extend(true, {}, ds.settings, dataOptions);

				// set callee after extend()
				ds.settings.callee = this;
			} else {
				ds = new $.ig.DataSource(dataOptions);
				ds.settings.callee = this;
			}

			// raise client event
			if (!bound && !this._trigger("dataBinding", null, { owner: this, dataSource: ds })) {
				return;
			}
			this._dataSource = ds;
			if (bound) {
				this._dataChange("no", dataSourceOwnerName);
			} else {
				ds.dataBind();
			}

			// configure request of remote filtering
			if (url && !this._urlBind) {
				setting = ds.settings;
				setting.dataSource = url;
				setting.type = "remoteUrl";
				ds._runtimeType = ds.analyzeDataSource();
				keys = this._getRemoteDataKeys();
				len = keys ? keys.length : 0;
				if (len > 0) {
					key = null;
					while (len-- > 0) {
						i = keys[ len ];
						if (i) {
							key = key ? key + "," + i : i;
						}
					}
					if (key) {
					    setting.urlParamsEncoded = $.proxy(function (data, params) {

							// set flag used by Mvc DataSourceActionAttribute
							if (params && params.filteringParams) {
								params.filteringParams.keys = key;
							}
						}, this);
					}
				}
				this._urlBind = 1;
				if (!o.dataSource) {
					ds.dataBind();
				}
			}
		},
		destroy: function () {
			/* Destroys widget. */
			var key, style,
				chart = this._chart,
				old = this._oldState,
				elem = this.element;
			if (!old) {
				return;
			}

			// remove children created by chart
			elem.find("*").not(old.elems).remove();

			// restore className modified by chart
			if (this.css.chart) {
				elem.removeClass(this.css.chart);
			}

			// restore css style attributes modified by chart
			old = old.style;
			style = elem[ 0 ].style;
			for (key in old) {
				if (old.hasOwnProperty(key)) {
					if (style[ key ] !== old[ key ]) {
						style[ key ] = old[ key ];
					}
				}
			}
			if (chart) {
				this._setSize(chart);
			}
			$.Widget.prototype.destroy.apply(this, arguments);
			if (chart && chart.destroy) {
				chart.destroy();
			}
			delete this._chart;
			delete this._oldState;
		}
	});
	$.extend($.ui.igBaseChart, { version: "16.1.20161.2145" });
}(jQuery));
