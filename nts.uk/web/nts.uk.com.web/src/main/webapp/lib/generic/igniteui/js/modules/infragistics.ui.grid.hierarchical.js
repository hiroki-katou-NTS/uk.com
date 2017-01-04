/*!@license
 * Infragistics.Web.ClientUI Hierarchical Grid 16.1.20161.2145
 *
 * Copyright (c) 2011-2016 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 *	jquery-1.9.1.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	infragistics.dataSource.js
 *	infragistics.ui.shared.js
 *	infragistics.util.js
 *	infragistics.ui.grid.framework.js
 */

/*global jQuery */ /*jshint -W106*/
(function ($) {

	$.widget("ui.igHierarchicalGrid", {
		// check the parent path. the path has the following format PrimaryKeyID/ChildKeyID1/childKeyID2[band_name]. for example an empty key gives the root grid
		// a key of "1[0]" specifies the first child band of the second root row, a key of "1[0]/2[0]" specifies the first child band of the third child row of the
		// first band of the second parent row.
		// a key can also have the format "1[0]/3" which gives the third data row element of the first child band of the second root row.
		css: {
			/* classes that are applied to the expander column TD */
			expandColumn: "ui-iggrid-expandcolumn",
			/* classes applied when the TD in which the expand/collapse indicator is located, is currently in expand state */
			expandColumnExpanded: "ui-iggrid-expandcolumn-expanded",
			/* classes applied to the expand button SPAN, when it's collapsed */
			expandButton: "ui-iggrid-expandbutton ui-icon-plus",
			/* classes applied to a collapsed expand button span, when it's hovered */
			expandButtonHover: "ui-iggrid-expandbuttonhover ui-icon-plus ui-state-hover",
			/* classes applied to an expanded expand button span, when it's hovered */
			expandButtonExpandedHover: "ui-iggrid-expandbuttonexpandedhover ui-icon-plus ui-state-hover",
			/* classes applied to the expand button SPAN, when it's expanded */
			expandButtonExpanded: "ui-iggrid-expandbutton ui-iggrid-expandbuttonexpanded ui-icon-minus",
			/* classes applied to the expand button SPAN, when it's clicked */
			expandButtonClick: "ui-iggrid-expandbuttonclick ui-icon-plus",
			/* classes applied to the child grid TD container when it is expanded */
			childGridArea: "ui-iggrid-childarea",
			/* classes applied to the child grid TD container when it's collapsed */
			childGridAreaCollapsed: "ui-iggrid-childarea-collapsed",
			/* classes applied to the special header cell  TH which is for the expand column */
			expandHeaderCell: "ui-iggrid-expandheadercell ui-iggrid-header ui-widget-header",
			/* classes applied to the SPAN holding the expand header cell icon */
			expandHeaderCellTriangle: "ui-icon ui-icon-triangle-1-se",
			/* classes applied to the root grid's table element */
			root: "ui-iggrid-root",
			/* achieves a "dotted-line" effect in between two child grids, excluding first and last ones */
			childGridSeparator: "ui-iggrid-childgridseparator"
		},
		/* the options listed here are only specific to the hierarchical grid. All other options that are exposed by the flat grid, will be copied automatically
		such as width, height, fixedHeaders, etc. */
		options: {
			// specific hierarchical grid options go here. everything else gets merged with the flat grid CSS (meaning root level)
			// even though this widget *does not extend* the flat grid
			/* type="number" Only the first level will be data-bound initially. Also serves as "render" depth, meaning that depending on this prop, the grid will */
			initialDataBindDepth: -1,
			/* type="number" No levels will be automatically expanded when the widget is instantiated for the first time */
			initialExpandDepth: -1,
			/* type="bool" If true, encodes all requests using OData conventions and the $expand syntax */
			odata: false,
			/* type="bool" If true, load on demand will be achieved using REST compliant resource access with appropriate url-s for
				the ajax GET calls. */
			rest: false,
			/* type="number" Specifies the limit on the number of levels to bind to */
			maxDataBindDepth: -1,
			/* type="string" Specifies the default property in the response where children will be located */
			defaultChildrenDataProperty: "children",
			/* type="bool" if true, will autogenerate all layouts assuming default values for "childrenDataProperty"
				When autoGenerateLayouts is enabled, it will automatically generate all columns recursively. (all columns on all levels)
			*/
			autoGenerateLayouts: false,
			/* type="bool" applies a linear animation - either expanding or collapsing depending on the parent row state */
			expandCollapseAnimations: true,
			/* type="number" Specifies the expand column width */
			expandColWidth: 30,
			/* type="string" Specifies the delimiter for constructing paths , for hierarchical lookup of data */
			pathSeparator: "/",
			/* type="number" The row expanding/collapsing animation duration in ms. */
			animationDuration: 500,
			/* type="string" Specifies the default tooltip applied to an expand column cell, that is currently collapsed */
			expandTooltip: $.ig.Grid.locale.expandTooltip,
			/* type="string" Specifies the default tooltip applied to an expand column cell, that is currently expanded */
			collapseTooltip: $.ig.Grid.locale.collapseTooltip,
			/* type="array" An array of column objects */
			columns: [
			],
			/* type="array" List of columnLayout objects that specify the structure of the child grids. All options that are applicable to a flat grid are also applicable here */
			columnLayouts: [
				{
					/* type="string" Specifies the columnLayout key. This is the property that holds the data records for the current column layout. */
					key: null,
					/* type="string" Specifies the primaryKey of the columnLayout. This also serves as the column key for the current column layout. */
					primaryKey: null,
					/* type="string" specifies the foreignKey of the columnLayout. This is also the column key of the parent grid. */
					foreignKey: null
				}
			]
			/* all options for the flat grid are also applicable here */
		},
		events: {
			/* cancel="true" Event which is fired when a hierarchical row is about to be expanded
				use args.owner to access the hierarchical grid object
				use args.parentrow to access the row element for the row that's about to be expanded
			*/
			rowExpanding: "rowExpanding",
			/* Event which is fired after a hierarchical row has been expanded
				use args.owner to access the hierarchical grid object
				use args.parentrow to access the row element for the row that was expanded
			*/
			rowExpanded: "rowExpanded",
			/* cancel="true" Event which is fired when a hierarchical row is about to be collapsed
				use args.owner to access the hierarchical grid object
				use args.parentrow to access the row element for the row that's about to be collapsed
			*/
			rowCollapsing: "rowCollapsing",
			/* Event which is fired when a hierarchical row has been collapsed
				use args.owner to access the hierarchical grid object
				use args.parentrow to access the row element for the row that was collapsd
			*/
			rowCollapsed: "rowCollapsed",
			/* cancel="true" Event which is fired when children are about to be populated (Load on demand)
				use args.owner to access the hierarchical grid object
				use args.parentrow to access the row element for the row that's about to be populated
				use args.id to get the data ID of the row
			*/
			childrenPopulating: "childrenPopulating",
			/* Event which is fired when children have been populated (Load on demand)
				use args.owner to access the hierarchical grid object
				use args.parentrow to access the row element for the row that was populated
				use args.id to get the data ID of the row
			*/
			childrenPopulated: "childrenPopulated",
			/*  Event fired when child grid is rendered
				use args.owner to access the hierarchical grid object
				use args.parentrow to access the row element for the row that's about to be populated
				use args.childgrid to get reference to the child grid
			*/
			childGridRendered: "childGridRendered",
			/*
				event fired before a child grid is going to be created, allows the developer to override the child grid creation
				cancel="true"
			*/
			childGridCreating: "igchildgridcreating",
			/*
				event fired after a child grid is created
				cancel="false"
			*/
			childGridCreated: "igchildgridcreated"
		},
		_setOption: function (key, value) {
			// handle new settings and update options hash
			// delegate to the flat root grid
			if (key === "dataSource") {
				this.options.dataSource = value;
				this.dataBind();
			} else if (key === "initialDataBindDepth" ||
					key === "initialExpandDepth" ||
					key === "expandColWidth") {
				throw new Error($.ig.Grid.locale.optionChangeNotSupported.replace("{optionName}", key));
			}
			if (key === "odata" || key === "defaultChildrenDataProperty" ||
					key === "expandTooltip" || key === "collapseTooltip" ||
					key === "autoGenerateLayouts" || key === "expandCollapseAnimations" ||
					key === "animationDuration") {
				$.Widget.prototype._setOption.apply(this, arguments);
			} else if (this.rootWidget() && key !== "dataSource") {
				this.rootWidget()._setOption(key, value);
			}
		},
		_create: function () {
			// bind to the headerRendered event for the root grid, as well as dataRendering
			this._regevents(this.element, this);
			/* the options need to be merged with the flat grid's *default* options. all child layouts inherit the default flat grid's options
			we need to ensure we strip all dummy column definitions, before the widget is created */
			$.ui.igGrid.prototype.options.columns = [];
			$.ui.igGrid.prototype.options.features = [];
			this.options = $.extend(true, {}, $.ui.igGrid.prototype.options, this.options);
			if (this.tmpDataSource !== null && this.tmpDataSource !== undefined) {
				this.options.dataSource = this.tmpDataSource;
				this._originalOptions.dataSource = this.tmpDataSource;
			}

			//hGrid supports continous virtualiztion only
			// M.H. 16 Sep 2014 Fix for bug #180978: JavaScript error should be thrown when there is igHierarchicalGrid and fixed virtualization
			if (this.options.virtualization === true || this.options.rowVirtualization === true) {
				this.options.virtualizationMode = "continuous";
			}

			// we need to data-bind the hierarchical data source, and then create recursively child grids where appropriate,
			// and bind the child data to them
			this._eventQueue = [];
			this._animationQueue = [];
			this.dataBind();
			/* multicolumn headers could be set only for some of the column layouts */
			this.rootWidget()._isMultiColumnGrid = this._checkIsMultiColumnHeader(this.options);
		},
		_createWidget: function (options) {
			/* !Strip dummy objects from options, because they are defined for documentation purposes only! */
			this.options.columns = [];
			this.options.features = [];
			this.options.columnLayouts = [];
			if (options.dataSource &&
					($.type(options.dataSource) === "array" || $.type(options.dataSource) === "object")) {
				this.tmpDataSource = options.dataSource;
				options.dataSource = null;
				/* later we would need to restore the data source to the original external options object */
				this._originalOptions = options;
			}
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		dataBind: function () {
			/* Data binds the hierarchical grid. No child grids will be created or rendered by default, unless there is initialExpandDepth >= 0 set.
			*/
			var hds, hdsoptions, rootds, opts;
			/* this._parseLayouts(); */
			/* auto generate layouts */
			if (this.options.autoGenerateLayouts) {
				this._generateLayouts();
			}
			hdsoptions = this._hdsoptions();
			hds = new $.ig.HierarchicalDataSource(hdsoptions);
			/* do not data-bind the hierarchical data source. Instead "ask" it to generate options for the respective sources
			such as the root one */
			/* hds.dataBind(); */
			this._hds = hds;
			/* create the root level grid
			two ways, pass the root ds instance, or the dynamically generated options object */
			rootds = this._hds.root();
			this._tmpds = this.options.dataSource;
			this.options.dataSource = null;
			opts = $.extend(true, {}, this.options);
			this.options.dataSource = this._tmpds;
			this._tmpds = null;
			opts.dataSource = rootds;
			if ($.type(hdsoptions.dataSource) === "string") {
				opts.dataSourceUrl = hdsoptions.dataSource;
			}
			if (!this._columnsGeneratedHandler) {
				this._columnsGeneratedHandler = $.proxy(this._columnsGenerated, this);
			} else {
				this.element.unbind("iggridcolumnsgenerated.hierarchicalgrid", this._columnsGeneratedHandler);
			}
			this.element.bind("iggridcolumnsgenerated.hierarchicalgrid", this._columnsGeneratedHandler);
			/* bind to the databound event so that we can auto generate the layouts if that option is enabled */
			if (this.options.autoGenerateLayouts === true) {
				if (!this._databoundHandler) {
					this._databoundHandler = $.proxy(this._layouts, this);
				} else {
					this.element.unbind("iggridcolumnsgenerated.hierarchicalgrid", this._databoundHandler);
				}
				this.element.bind("iggriddatarendering.hierarchicalgrid", this._databoundHandler);
			}
			/* used for initialExpandDepth, in order to start expanding and creating child grids once the root one has been rendered */
			/*this._renderedHandler = $.proxy(this._handleBatchExpandRender, this);
			this.element.bind('iggriddatarendered', this._renderedHandler);
			} */
			/* mark the element with a special class specifying that it's the hierarchical grid root */
			this.element.addClass(this.css.root);
			this.element.attr("data-level", 0);
			/* M.H. 14 June 2012 Fix for bug #114327 */
			opts._isHierarchicalGrid = true;
			if (this._root) {
				this._root.igGrid("option", "dataSource", opts.dataSource);
			} else {
				if (this.options.autoGenerateLayouts === true) {
					opts._recurseSchema = true;
				}
				this._root = this.element;
				this.element.igGrid(opts);
			}
			/* init loading indicator */
			if (this.rootWidget().container().data("igLoading")) {
				this.indicator = this.rootWidget().container().data("igLoading").indicator();
			} else {
				this.indicator = this.rootWidget().container().igLoading().data("igLoading").indicator();
			}
			this._regToggleEvent(this.element, this);
		},
		/* auto generation of layouts */
		_generateLayouts: function () {
			var rec, i, ds = this.options.dataSource;
			if (($.type(ds) !== "array" && $.type(ds) !== "object") || ds.length === 0) {
				return;
			}
			if ($.type(ds) === "object" && this.options.responseDataKey) {
				ds = ds[ this.options.responseDataKey ] || ds;
			}
			for (i = 0; i < ds.length; i++) {
				rec = ds[ i ];
				this._generateLayout(rec, this.options);
			}
		},
		_generateLayout: function (record, options) {
			var name, layout, tmpLayout, i, rec, hasRespKey = false;
			for (name in record) {
				if (record.hasOwnProperty(name) && ($.type(record[ name ]) === "array" ||
					($.type(record[ name ]) === "object" && options.responseDataKey &&
						$.type(record[ name ][ options.responseDataKey ]) === "array"))) {
					rec = record[ name ];
					if (options.responseDataKey && $.type(rec) === "object") {
						if (rec[ options.responseDataKey ] && $.type(rec[ options.responseDataKey ]) === "array") {
							rec = rec[ options.responseDataKey ];
							hasRespKey = true;
						} else {
							continue;
						}
					}
					layout = { key: name, childrenDataProperty: name, columnLayouts: [] };
					if (hasRespKey) {
						layout.responseDataKey = options.responseDataKey;
					}
					/* check if the layout exists. Needs to be recursive. */
					tmpLayout = this._layoutExistsRecursive(this.options, name);
					if (!tmpLayout) {
						if (options.columnLayouts === undefined || options.columnLayouts === null) {
							options.columnLayouts = [];
						}
						options.columnLayouts.push(layout);
					} else {
						layout = tmpLayout;
					}
					if (rec.length > 0) {
						for (i = 0; i < rec.length; i++) {
							this._generateLayout(rec[ i ], layout);
						}
					}
				}
			}
		},
		_layoutExistsRecursive: function (options, key) {
			var i, tmpLayout = null;
			tmpLayout = options.key === key ? options : tmpLayout;
			if (!tmpLayout) {
				for (i = 0; options.columnLayouts && i < options.columnLayouts.length; i++) {
					tmpLayout = this._layoutExistsRecursive(options.columnLayouts[ i ], key);
					if (tmpLayout) {
						break;
					}
				}
			}
			return tmpLayout;
		},
		root: function () {
			/* returns the element of the root grid (igGrid)
				returnType="object" jquery wrapped element of the root grid
			*/
			return this._root;
		},
		rootWidget: function () {
			/* returns the widget object of the root grid (igGrid)
				returnType="object" widget object of the root igGrid (not igHierarchicalGrid)
			*/
			return this.root().data("igGrid");
		},
		allChildrenWidgets: function () {
			/* returns a flat list of all child grid widgets (not elements) - recursive
				returnType="object"
			*/
			return this.root().find(".ui-iggrid-table").map(function () {
				return $(this).data("igGrid");
			});
		},
		allChildren: function () {
			/* returns a flat list of all child grid elements (recursive)
				returnType="object"
			*/
			return this.root().find(".ui-iggrid-table");
		},
		toggle: function (element, callback) {
			/* expands or collapses (toggles) a parent row
				Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
				paramType="dom" accepts a dom element, or a jquery wrapped dom element that should be a TR and should specify a parent row
				paramType="function" optional="true" Specifies a custom function to be called when parent row is toggled(optional). Takes 2 arguments - first is hierarchical grid object, second is the row element that was toggled
			*/
			this._toggleInternal({ target: $(element).find(".ui-iggrid-expandcolumn") }, true, callback);
		},
		expand: function (id, callback) {
			/* expands (toggles) a parent row
				Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
				paramType="dom" accepts a dom element, or a jquery wrapped dom element that should be a TR and should specify a parent row
				paramType="function" optional="true" Specifies a custom function to be called when parent row is expanded(optional). Takes 2 arguments first is hierarchical grid object, second is the row element that was expanded
			*/
			/* if there is load on demand we need to use toggleInternal */
			if (!this.populated(id)) {
				this._toggleInternal({ target: $(id).find(".ui-iggrid-expandcolumn") }, true, callback);
			} else {
				this._expand(id, true, callback);
			}
		},
		_expand: function (id, suppressEvents, callback) {
			var tr = $(id), c, noCancel = true, handler, rootgrid;
			/* M.H. 16 Oct 2014 Fix for bug #182885: When continuous virtualization is enabled and all levels are expanded teh scrollbar cannot reach the bottom */
			if (this.expanded(tr)) {
				return;
			}
			if (!suppressEvents) {
				noCancel = this._trigger(this.events.rowExpanding, null, { owner: this, parentrow: tr });
			}
			/* M.H. 16 Oct 2014 Fix for bug #182885: When continuous virtualization is enabled and all levels are expanded teh scrollbar cannot reach the bottom */
			if (this.expanded(tr)) {
				return;
			}
			rootgrid = this._rootgrid || this.element.closest(".ui-iggrid-root").data("igHierarchicalGrid");
			if (noCancel) {
				// M.H. 4 Dec 2013 Fix for bug #159045: Cannot expand row which was previously canceled from exapnsion on rowExpanding event
				if (this.options.expandCollapseAnimations) {
					if (rootgrid._animationQueue[ tr.attr("data-id") + "_" + tr.index() ] === true) {
						return; // animation in progress
					}
					rootgrid._animationQueue[ tr.attr("data-id") + "_" + tr.index() ] = true;
				}
				c = tr.find(".ui-iggrid-expandcolumn");
				tr.next().css("display", "").css("visibility", "visible");
				if (this.options.expandCollapseAnimations) {
					if (!suppressEvents) {
						handler = this._expandend;
					} else {
						handler = this._expandendnoevents;
					}
					tr.next().find("> td > div").slideDown(this.options.animationDuration, function (ctx) {
						handler.apply(this, [ ctx, callback ]);
					});
				} else {
					tr.next().find("> td > div").show();
					c.addClass(this.css.expandColumnExpanded).find(".ui-iggrid-expandbutton")
						.removeClass(this.css.expandButton).addClass(this.css.expandButtonExpanded)
						.attr("title", this.options.collapseTooltip);
				}
				tr.attr("state", "e");
				tr.attr("aria-expanded", true);
				tr.next().children().first().removeClass(this.css.childGridAreaCollapsed)
					.addClass(this.css.childGridArea);
				if (!this.options.expandCollapseAnimations) {
					this._rowExpanded({ owner: this, parentrow: tr });
					if (!suppressEvents) {
						this._trigger(this.events.rowExpanded, null, { owner: this, parentrow: tr });
					} else if (callback) {
						$.ig.util.invokeCallback(callback, [ this, tr ]);
					}
				}
			}
		},
		expanded: function (element) {
			/* checks if a parent row is expanded or not
				paramType="dom" accepts a dom element, or a jquery wrapped dom element that should be a TR and should specify a parent row
				returnType="bool" specifies if the row is currently expanded or collapsed
			*/
			var $e = element.length !== undefined ? element : $(element);
			if ($e === null || $e === undefined) {
				return false;
			}
			if ($e.attr("state") !== "e" || $e.attr("state") === undefined) {
				return false;
			}
			return true;
		},
		_expandendnoevents: function (ctx, callback) {
			var expcell, expcellspan, rootgrid, $this = ctx ? $(ctx) : $(this),
				prow = $this.closest("tr[data-container]").prev();

			rootgrid = this._rootgrid || $this.closest(".ui-iggrid-root").data("igHierarchicalGrid");
			expcell = $this.closest("[data-container=true]").prev().find(".ui-iggrid-expandcolumn");
			expcellspan = expcell.find(".ui-iggrid-expandbutton");
			expcell.addClass(rootgrid.css.expandColumnExpanded);
			expcellspan.removeClass(rootgrid.css.expandButton)
				.addClass(rootgrid.css.expandButtonExpanded).attr("title", rootgrid.options.collapseTooltip);
			rootgrid._rowExpanded({ owner: rootgrid, parentrow: prow });
			if (rootgrid._animationQueue[ prow.attr("data-id") + "_" + prow.index() ] === true) {
				delete rootgrid._animationQueue[ prow.attr("data-id") + "_" + prow.index() ];
			}
			if (callback) {
				$.ig.util.invokeCallback(callback, [ rootgrid, prow ]);
			}
		},
		_expandend: function () {
			// child td
			var $this = $(this), rootgrid, prow = $this.closest("tr[data-container]").prev(),
				parentGrid = prow.closest(".ui-iggrid-table").data("igGrid");
			rootgrid = this._rootgrid || $this.closest(".ui-iggrid-root").data("igHierarchicalGrid");
			rootgrid._expandendnoevents(this);
			/* L.A. 30 October 2012 - Fixing bug #122750 - When a hierarchical child grid has a vertical scrollbar,
			expanding a row in it causes its header cells to be misaligned with the records*/
			parentGrid._adjustLastColumnWidth(false);
			rootgrid._trigger(rootgrid.events.rowExpanded, null, { owner: rootgrid, parentrow: prow });
		},
		_collapseendnoevents: function (ctx, callback) {
			var expcell, expcellspan, rootgrid, $this = ctx ? $(ctx) : $(this),
				ctd = $this.closest(".ui-iggrid-childarea"),
				prow = $this.closest("tr[data-container]").prev();

			rootgrid = this._rootgrid || $this.closest(".ui-iggrid-root").data("igHierarchicalGrid");
			ctd.removeClass(rootgrid.css.childGridArea).addClass(rootgrid.css.childGridAreaCollapsed);
			ctd.closest("tr").css("display", "none").css("visibility", "hidden");
			expcell = $this.closest("[data-container=true]").prev().find(".ui-iggrid-expandcolumn");
			expcellspan = expcell.find(".ui-iggrid-expandbutton");
			expcell.removeClass(rootgrid.css.expandColumnExpanded);
			expcellspan.removeClass(rootgrid.css.expandButtonExpanded)
				.addClass(rootgrid.css.expandButton).attr("title", rootgrid.options.expandTooltip);
			if (rootgrid._animationQueue[ prow.attr("data-id") + "_" + prow.index() ] === true) {
				delete rootgrid._animationQueue[ prow.attr("data-id") + "_" + prow.index() ];
			}
			if (callback) {
				$.ig.util.invokeCallback(callback, [ rootgrid, prow ]);
			}
		},
		_collapseend: function () {
			var $this = $(this), rootgrid, prow = $this.closest("tr[data-container]").prev(),
				parentGrid = prow.closest(".ui-iggrid-table").data("igGrid");
			rootgrid = this._rootgrid || $this.closest(".ui-iggrid-root").data("igHierarchicalGrid");
			rootgrid._collapseendnoevents(this);
			/* L.A. 30 October 2012 - Fixing bug #122750 - When a hierarchical child grid has a vertical scrollbar,
			expanding a row in it causes its header cells to be misaligned with the records */
			parentGrid._adjustLastColumnWidth(false);
			rootgrid._trigger(rootgrid.events.rowCollapsed, null, { owner: rootgrid, parentrow: prow });
		},
		collapse: function (id, callback) {
			/* collapses a parent row
				Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
				paramType="dom" accepts a dom element, or a jquery wrapped dom element that should be a TR and should specify a parent row
				paramType="function" optional="true" Specifies a custom function to be called when parent row is expanded(optional). Takes 2 arguments - first is hierarchical grid object, second is the row element that was collapsed
			*/
			this._collapse(id, true, callback);
		},
		_collapse: function (id, suppressEvents, callback) {
			var tr = $(id), c, noCancel = true, handler, rootgrid;
			/* M.H. 16 Oct 2014 Fix for bug #182885: When continuous virtualization is enabled and all levels are expanded teh scrollbar cannot reach the bottom */
			if (this.collapsed(tr)) {
				return;
			}
			if (!suppressEvents) {
				noCancel = this._trigger(this.events.rowCollapsing, null, { owner: this, parentrow: tr });
			}
			rootgrid = this._rootgrid || this.element.closest(".ui-iggrid-root").data("igHierarchicalGrid");
			if (noCancel) {
				// M.H. 4 Dec 2013 Fix for bug #159045: Cannot expand row which was previously canceled from exapnsion on rowExpanding event
				if (this.options.expandCollapseAnimations) {
					if (rootgrid._animationQueue[ tr.attr("data-id") + "_" + tr.index() ] === true) {
						return; // animation in progress
					}
					rootgrid._animationQueue[ tr.attr("data-id") + "_" + tr.index() ] = true;
				}
				c = tr.find(".ui-iggrid-expandcolumn");
				if (this.options.expandCollapseAnimations) {
					if (!suppressEvents) {
						handler = this._collapseend;
					} else {
						handler = this._collapseendnoevents;
					}
					this._rowCollapsed({ owner: this, parentrow: tr });
					tr.next().find("> td > div").slideUp(this.options.animationDuration, function (ctx) {
						handler.apply(this, [ ctx, callback ]);
					});
				} else {
					this._rowCollapsed({ owner: this, parentrow: tr });
					tr.next().find("> td > div").hide();
					tr.next().children().first().removeClass(this.css.childGridArea)
						.addClass(this.css.childGridAreaCollapsed);
					tr.next().css("display", "none").css("visibility", "hidden");
					c.find(".ui-iggrid-expandbutton").removeClass(this.css.expandButtonExpanded)
						.addClass(this.css.expandButton).attr("title", this.options.expandTooltip);
					if (!suppressEvents) {
						this._trigger(this.events.rowCollapsed, null, { owner: this, parentrow: tr });
					} else if (callback) {
						$.ig.util.invokeCallback(callback, [ this, tr ]);
					}
				}
				tr.attr("state", "c");
				tr.attr("aria-expanded", false);
			}
		},
		collapsed: function (element) {
			/* checks if a parent row is currently collapsed
				paramType="dom" accepts a dom element, or a jquery wrapped dom element that should be a TR and should specify a parent row
				returnType="bool" returns a boolean value indicating if the parent row specified by the "element" is currently collapsed or expanded
			*/
			var $e = element.length !== undefined ? element : $(element);
			if ($e === null || $e === undefined) {
				return true;
			}
			if ($e.attr("state") === "c" || $e.attr("state") === undefined) {
				return true;
			}
			return false;
		},
		/*
		// this may be not possible to implement due to the architecture of the grid
		// assuming if there are features, they need to encode the url, and this only happens
		// once the grid is created after a data source url is assigned to it
		populate: function (element, suppressEvents) {

		},
		*/
		populated: function (element) {
			/* checks if a parent row is populated with data
				paramType="dom" accepts a dom element, or a jquery wrapped dom element that should be a TR and should specify a parent row
				returnType="bool" returns a boolean value indicating if the parent row's child grids are populated and created
			*/
			var $e = element.length !== undefined ? element : $(element);
			if ($e === null || $e === undefined) {
				return false;
			}
			if ($e.attr("c") === "true") {
				return true;
			}
			return false;
		},
		_mouseOver: function (event) {
			$(event.target).addClass(this.css.expandButtonHover);
		},
		_mouseOut: function (event) {
			$(event.target).removeClass(this.css.expandButtonHover).addClass(this.css.expandButton);
		},
		_toggleInternalWithDelay: function (event) {
			var self = this;
			/*Fixing bug #130025 */
			/* valueChanged in the igEditor doesn't fire when you modify cell when initalDataBindDepth is set to 2 */
			setTimeout(function () { self._toggleInternal(event); }, 0);
			/* M.H. 20 June 2014 Fix for bug #174049: igHierarchicalGrid LoadOnDemand is not working for the second child layout */
			event.stopImmediatePropagation();
		},
		_toggleInternal: function (event, suppressEvents, callback) {
			var created = $(event.target).closest("tr").attr("c"), tr = $(event.target).closest("tr"),
				p = tr.find(".ui-iggrid-expandcolumn"), hgrid = this, isExpand,
				expandTout = this.options.virtualization === true ? 50 : 0;
			isExpand = $(event.target).hasClass("ui-iggrid-expandbutton") ||
				$(event.target).children(".ui-iggrid-expandbutton").length > 0;
			/* start loading indicator if the child grids aren't created yet */
			if (!created && this.options.initialDataBindDepth === -1 && isExpand) { // if there is load on demand, we don't need explicit async, since it's async anyway.
				// we want the indicator to be positioned below the parent row
				// M.H. 1 Nov 2013 Fix for bug #156657: New loading indicator isn't well positioned when used for hiearchical grid expand row scenario
				//				x = tr.offset().left;
				//				y = p.offset().top;
				//				this.indicator._indicator.css('left', x + tr.width() / 2).css('top', y + p.height() / 3 - 4);
				// M.H. 6 Nov 2013 Fix for bug #156657: New loading indicator isn't well positioned when used for hiearchical grid expand row scenario
				this.indicator.refreshPos();
				/* hide the expand indicator for a while
				if the loading indicator is shown on chrome and safari, text selection appears randomly on all child layouts. Couldn't find a way
				to resolve this any other way */
				if (!$.ig.util.isWebKit) {
					p.find(".ui-iggrid-expandbutton").css("visibility", "hidden");
					this.indicator.show(false);
				} else {
					// M.H. 19 Nov 2013 Fix for bug #156657: New loading indicator isn't well positioned when used for hiearchical grid expand row scenario
					this.indicator.show(false);
				}
				/* Async, don't block the UI */
				/* L.A. 05 November 2012 - Fixing bug #113718
				When continuous virtualization is in use, the loading indicator is not displayed while expanding a row */
				setTimeout(function () {
					hgrid._toggleInternalAsync(event, suppressEvents, callback);
				}, expandTout);
			} else {
				// L.A. 03 January 2012 - Fixing bug #130025
				// valueChanged in the igEditor doesn't fire when you modify cell when initalDataBindDepth is set to 2
				// M.H. 18 June 2014 Fix for bug #172395: [Hierarchical Grid] 'initialExpandDepth' property is not working
				// MH: We should not call _toggleInternalAsync using setTimeout - when initialExpandDepth is gth 1 is not working
				//setTimeout(function () {
				hgrid._toggleInternalAsync(event, suppressEvents, callback);
				/*}, expandTout); */
			}
			/*A.T. 20 Feb. 2012 Fix for bug #100588 */
			if (!suppressEvents) {
				// M.H. 23 Sep 2014 Fix for bug #181404: An error is thrown in the samples browser when a row is expanded in the hierarchical grid:Member not found.
				if (event.originalEvent &&
						typeof event.originalEvent.cancelBubble === "unknown") {
					event.originalEvent = {};
				}
				event.stopImmediatePropagation();
			}
		},
		_toggleInternalAsync: function (event, suppressEvents, callback) {
			// obtain the row id
			var $td = $(event.target), parentrow = $td.closest("tr"), rowid = "", keyspath = "",
				keyspathvar = "", pgridp, o, level = -1, sp = false, curpath = 0, restSettingsSet,
				childds, created = parentrow.attr("c"), childrow, childgrids = [], ds, opts = [],
				id, k, isHierarchical, tmpkey, pki, dsobj, dsString, key, pgInstance,
				cs, popts, i, h, parentLevel = 0, tmpgrid, state = parentrow.attr("state"),
				animatec, noCancel = true, noCancelChild = true, pid, instanceOfDs, eArgs,
				mainUrl, queryStr; // parent options

			if (($td.is("td") && $td.attr("data-parent") !== "true") ||
					$td.closest("td").attr("data-parent") !== "true") {
				return;
			}
			if (state === undefined) {
				state = "c";
			}
			restSettingsSet = this.options.restSettings.update.url !== null ||
				this.options.restSettings.update.template !== null ||
				this.options.restSettings.create.url !== null ||
				this.options.restSettings.create.template !== null ||
				this.options.restSettings.remove.url !== null ||
				this.options.restSettings.remove.template !== null;
			if (!created && state === "c") {
				parentrow.after("<tr></tr>");
				childrow = parentrow.next();
				childrow.attr("data-container", true);
				/* childrow.hide(); */
				popts = parentrow.closest("table").data("igGrid").options;
				opts = this._optsFor(popts);
				/* cs = popts.columns.length + 1; */
				if (popts.childrenDataProperty === undefined) {
					if (popts.key) {
						keyspath = popts.key;
					}
				} else {
					keyspath += popts.childrenDataProperty;
				}
				/* call populate if necessary (remote) */
				pgridp = parentrow.closest(".ui-iggrid-childarea").parent().prev();
				while (pgridp.length > 0) {
					if (curpath !== 0) {
						rowid += this.options.pathSeparator;
					}
					o = pgridp.closest(".ui-iggrid-table").data("igGrid").options;
					/* k = o.foreignKey;
					if (!k) { */
					k = o.primaryKey;
					/* } */
					rowid += k + ":" + pgridp.attr("data-id");
					if (o.childrenDataProperty === undefined) {
						if (o.key) {
							keyspath += this.options.pathSeparator + o.key;
						}
					} else {
						keyspath += this.options.pathSeparator + o.childrenDataProperty;
					}
					pgridp = pgridp.closest(".ui-iggrid-childarea").parent().prev();
					curpath++;
				}
				/* reverse keyspath and rowid */
				keyspath = keyspath.split(this.options.pathSeparator)
					.reverse().join(this.options.pathSeparator);
				rowid = rowid.split(this.options.pathSeparator).reverse().join(this.options.pathSeparator);
				/*k = parentrow.closest('.ui-iggrid-table').data('igGrid').options.foreignKey; */
				/*if (!k) { */
				k = parentrow.closest("table").data("igGrid").options.primaryKey;
				/*} */
				if (rowid === "") {
					rowid = k + ":" + parentrow.attr("data-id");
				} else {
					rowid += this.options.pathSeparator + k + ":" + parentrow.attr("data-id");
				}
				cs = parentrow.find("td,th").length;
				animatec = $("<div></div>").appendTo($("<td></td>")
					.appendTo(childrow).addClass(this.css.childGridArea).attr("colspan", cs));
				/* create a table for each child grid */
				for (i = 0; i < opts.length; i++) {
					keyspathvar = "";
					childgrids.push($("<table></table>").appendTo(animatec).attr("data-childgrid", true));
					/* check if we need to add a separator div, to achieve the "dotted line" effect as shown in wireframes */
					if (i < opts.length - 1) {
						$("<div></div>").appendTo(animatec).addClass(this.css.childGridSeparator);
					}
					/* set the ID of the child grid element */
					/* important - append layout name! */
					/* M.H. 29 May 2014 Fix for bug #170463: A JavaScript exception is thrown when igHierarchicalGrid.childrenDataProperty is used instead of igHierarchicalGrid.key */
					key = (opts[ i ].key !== undefined) ? opts[ i ].key : opts[ i ].childrenDataProperty;
					childgrids[ i ].attr("id", this.root()[ 0 ].id + "_" +
						parentrow.attr("data-id") + "_" + key + "_child");
					/* in the case of composite keys, we need to replace the comma character (",") with a dash ("-") or similar
					otherwise it's not compilant and the generated id won't work with jQuery selectors */
					parentLevel = parseInt(parentrow.closest("table").attr("data-level"), 10);
					childgrids[ i ].attr("id", childgrids[ i ].attr("id")
						.replace(",", "-")).attr("data-level", parentLevel + 1);
					/* find the data, construct the options, create the container, init the grid
					first check if the child is already created (via attr. ?)
					create a new <tr> below the parent one which will hold the child data, with one td with colspan colcount + 1 (the expandable column)*/
					if (keyspath === "") {
						keyspathvar = opts[ i ].childrenDataProperty === undefined ?
							opts[ i ].key :
							opts[ i ].childrenDataProperty;
					} else {
						keyspathvar = this.options.pathSeparator +
							(opts[ i ].childrenDataProperty === undefined ?
								opts[ i ].key :
								opts[ i ].childrenDataProperty
							);
					}
					childds = this._hds.dataAt(rowid, keyspath + keyspathvar);
					/* if ($.type(childds) !== "array") { */
					if ($.type(childds) !== "array" && !opts[ i ].responseDataKey && childds !== undefined) {
						childds = [ childds ];
					} else if (childds === null || childds === undefined) {
						childds = [];
					}
					/* register the child grid
					init hierarchical events, if this grid has layouts defined, otherwise init as purely a FLAT grid ! */
					isHierarchical = false;
					/* here we shouldn't check for primary / foreign keys. it's irrelevant as to whether the grid is hierarchical or not.
					what's important is whether it has layouts defined.
					its another story if this layout has matching records or not
					that determines whether the row will have an expansion indicator or not. */
					if (opts[ i ].columnLayouts && opts[ i ].columnLayouts.length > 0) {
						isHierarchical = true;
					}
					if (isHierarchical) {
						this._regevents(childgrids[ i ], this);
					}
					/* A.T. load on demand support (always remote ! when data source is remote. )
					1. check level index, if it surpasses initialDataBindDepth populate data
					through the data source, by also showing the loading indicator
					level => count the number of separator characters */
					level = rowid.split(this.options.pathSeparator).length;
					if (level > this.options.initialDataBindDepth && this.options.initialDataBindDepth !== -1) {
						sp = true;
					}
					/* 2. mark the parent record as already populated */
					ds = this._hdsoptions().dataSource;
					eArgs = {
						owner: this,
						options: opts[ i ],
						element: childgrids[ i ],
						id: rowid,
						path: keyspath + keyspathvar
					};
					if (!sp) {
						// pass layout / level info
						opts[ i ].dataSource = childds;
						/* L.A. 15 January 2013 - Fixing bug #130730 */
						/* When the dataSource of the HGrid is URL the remote features in child layouts do not use the correct Url for their requests. */
						if ($.type(ds) === "string" && this.options.rest !== true && !opts[ i ].dataSourceUrl) {
							opts[ i ].dataSourceUrl = ds;
						}
						if (opts[ i ].dataSourceUrl && $.type(opts[ i ].dataSourceUrl) === "string") {
							if (opts[ i ].dataSourceUrl.indexOf("?") !== -1) {
								opts[ i ].dataSourceUrl += "&" + this._hds._encodeUrlPath(rowid, opts[ i ].key); //rowid + "/[" + opts[ i ].key + "]";
							} else {
								opts[ i ].dataSourceUrl += "?" + this._hds._encodeUrlPath(rowid, opts[ i ].key);
							}
						}
						/* we need to copy the ig_pk col, if present (that's given there are no primary keys defined).
						in that case the grid is generating the primary keys itself, so that auto generation of layouts
						works fine */
						if (this.options.autoGenerateLayouts === true) {
							opts[ i ]._recurseSchema = true;
						}
						childgrids[ i ].trigger(this.events.childGridCreating, eArgs);
						noCancelChild = childgrids[ i ].attr("data-create");
						if (noCancelChild !== "false") {
							if ((this.options.rest || this.options.odata) && restSettingsSet === true) {
								this._inheritRestSettings(null, parentrow.attr("data-id"),
									keyspathvar.toLowerCase(), popts, opts[ i ]);
							}
							/* M.H. 10 Dec 2012 Fix for bug #126991 */
							if (isHierarchical) {
								opts[ i ]._isHierarchicalGrid = true;
							}
							pgInstance = parentrow.closest(".ui-iggrid-table").data("igGrid");
							if (pgInstance) {
								opts[ i ].foreignKeyValue = pgInstance._fixPKValue(parentrow.data("id"));
							} else {
								opts[ i ].foreignKeyValue = parentrow.data("id");
							}
							/* M.H. 21 Apr 2015 Fix for bug 192978: Event igChildCreating/igChildCreated are not fired when initialDataBindDepth is 0 */
							h = $.proxy(this._childRendered, this);
							childgrids[ i ].bind("iggridrendered.hierarchicalgrid", h);
							childgrids[ i ].igGrid(opts[ i ]);
							if (isHierarchical) {
								this._regToggleEvent(childgrids[ i ], this);
							}
							childgrids[ i ].trigger(this.events.childGridCreated, {
								owner: this,
								element: childgrids[ i ]
							});
						}
						if (opts[ i ].primaryKey === null || opts[ i ].primaryKey === undefined) {
							if (opts[ i ].dataSource &&
									typeof opts[ i ].dataSource._xmlToArray === "function" &&
									typeof opts[ i ].dataSource._encodePkParams === "function") {
								throw new Error($.ig.Grid.locale.noPrimaryKey);
							}
							tmpgrid = childgrids[ i ].data("igGrid");
							tmpkey = tmpgrid.dataSource.schema().schema.searchField;
							if (tmpkey !== null) {
								for (pki = 0; pki < opts[ i ].dataSource[ tmpkey ].length; pki++) {
									/* jscs:disable */
									opts[ i ].dataSource[ tmpkey ][ pki ].ig_pk =
										tmpgrid.dataSource.data()[ pki ].ig_pk;
									/* jscs:enable */
								}
							} else {
								for (pki = 0; pki < opts[ i ].dataSource.length; pki++) {
									if (opts[ i ].dataSource[ pki ]) {
										/* jscs:disable */
										opts[ i ].dataSource[ pki ].ig_pk =
											tmpgrid.dataSource.data()[ pki ].ig_pk;
										/* jscs:enable */
									}
								}
							}
						}
					} else {
						instanceOfDs = ds &&
								typeof ds._xmlToArray === "function" &&
								typeof ds._encodePkParams === "function";
						/* can child layouts have dataSource themselves ? i.e. sth like mashup data source? */
						if (this.options.odata) {
							dsobj = instanceOfDs ? ds.settings.dataSource : ds;
							dsString = (popts.dataSource &&
									typeof popts.dataSource._xmlToArray === "function" &&
									typeof popts.dataSource._encodePkParams === "function") ?
										popts.dataSource.settings.dataSource :
										popts.dataSource;
							/* childds = this._hds.dataAt(rowid, keyspath + keyspathvar); */
							childds = parentrow.closest("table").data("igGrid").dataSource.data();
							tmpkey = parentrow.attr("data-id");
							k = parentrow.closest("table").data("igGrid").options.primaryKey;
							/* find the primary column type */
							if (parentrow.closest("table").data("igGrid").columnByKey(k).dataType === "number") {
								tmpkey = parseInt(tmpkey, 10);
							}
							for (pki = 0; pki < childds.length; pki++) {
								if (childds[ pki ][ parentrow.closest("table")
										.data("igGrid").options.primaryKey ] === tmpkey) {
									childds = childds[ pki ][ opts[ i ].key ];
									break;
								}
							}
							if (!childds) {
								// with webapi implementation we need to create the uri similarly to how rest is done
								if (dsString.substr(dsString.length - 1) === "/") {
									dsString = dsString.substring(0, dsString.length - 1);
								}
								pid = typeof tmpkey === "string" ? "'" + tmpkey + "'" : tmpkey;
								if (keyspathvar.charAt(0) !== "/") {
									keyspathvar = "/" + keyspathvar;
								}
								if ($.type(dsobj) === "string" && dsobj !== dsString) {
									dsString = dsobj.substring(0, dsobj.lastIndexOf("/")) +
										dsString.substring(dsString.lastIndexOf("/"));
								}

								//if there is a query string the pid should be appended to the main url not to the end of the url
								if (dsString.indexOf("?") !== -1) {
									mainUrl = dsString.substring(0, dsString.indexOf("?"));
									queryStr = dsString.substring(dsString.indexOf("?"), dsString.length);
									opts[ i ].dataSource = mainUrl + "(" + pid + ")" + queryStr;
								} else {
									opts[ i ].dataSource = dsString + "(" + pid + ")";
								}
							} else {
								// the OData spec specifies that all deferred content which hasn't been initially retrieved using $expand
								// should be contained in a __deferred object.
								if (childds.__deferred && childds.__deferred.uri) {
									opts[ i ].dataSource = childds.__deferred.uri;
									/* we need to handle the special case when JSONP protocol is used. For that a callback=? is necessary in the URL
									if the original data source contains this fragment, we need to ensure we add it for all dynamically constructed URLs
									for child data sources. The OData spec doesn't say anything about $expand and __deferred syntax having callback
									i.e. it's not inherited from parent URLs.*/
									if (dsobj.indexOf("$callback=?") !== -1) {
										opts[ i ].dataSource += "?$callback=?";
									}
									/* copy $format */
									if (dsobj.indexOf("$format=") !== -1) {
										if (dsobj.indexOf("$format=json") !== -1) {
											opts[ i ].dataSource += "&$format=json";
										} else {
											opts[ i ].dataSource += "&$format=atom";
										}
									}
								}
							}
							if (restSettingsSet === true) {
								this._inheritRestSettings(null, parentrow.attr("data-id"),
									keyspathvar.toLowerCase(), popts, opts[ i ]);
							}
						} else if (this.options.rest === true) {
							if (ds.substr(ds.length - 1) !== "/") {
								ds += "/";
							}
							pid = parentrow.attr("data-id");
							opts[ i ].dataSource = ds + pid + "/" + keyspathvar.toLowerCase();
							if (restSettingsSet === true) {
								this._inheritRestSettings(opts[ i ].dataSource, pid,
									keyspathvar.toLowerCase(), popts, opts[ i ]);
							}
						} else if (instanceOfDs) {
							opts[ i ].dataSource = ds.settings.dataSource +
								this._hds._encodeUrlPath(rowid, opts[ i ].key);
						} else {
							//check for  the existence of dataSourceUrl (MVC scenario)
							/* L.A. 28 August 2012 Fixing bug 119708
							When LoadOnDemand = TRUE and the root grid is assigned a DataSourceURL (and no initial source data),
							expanding a root grid row causes JS errors due to incorrect child layout binding.
							The problem is caused because dataSourceUrl is not used anymore. dataSource is used instead.
							*/
							if (opts[ i ].dataSource) {
								if (opts[ i ].dataSource.indexOf("?") !== -1) {
									opts[ i ].dataSource += "&" + this._hds._encodeUrlPath(rowid, opts[ i ].key);
								} else {
									opts[ i ].dataSource += "?" + this._hds._encodeUrlPath(rowid, opts[ i ].key);
								}
								opts[ i ].dataSourceType = "remoteUrl";
							} else {
								// 10 Oct. 2011 - Fix for bug #91254
								if ($.type(ds) !== "string") {
									// it is possible ds to be array - check bug 210760
									if ($.type(opts[ i ].dataSource) === "string") {
										opts[ i ].dataSource += "?" + this._hds._encodeUrlPath(rowid, opts[ i ].key);
										opts[ i ].dataSourceType = "remoteUrl";
									}
								} else {
									opts[ i ].dataSource = ds + "?" + this._hds._encodeUrlPath(rowid, opts[ i ].key);
									/* this._hds.populate(rowid, $.proxy(this._populateComplete, this), params); */
								}
							}
							/* M.H. 17 May 2013 Fix for bug #142453: Remote inhereted operations for third layout throw js error (GroupBy, Paging and Sorting) */
							if ($.type(opts[ i ].dataSource) === "string") {
								opts[ i ].dataSourceUrl = opts[ i ].dataSource;
							}
						}
					}
				}
				if (sp) {
					// fire children populating
					noCancel = this._trigger(this.events.childrenPopulating, null, {
						owner: this, parentrow: parentrow, id: rowid
					});
					if (noCancel) {
						// create the grids
						for (i = 0; i < opts.length; i++) {
							// we need to handle the children populated events, and for this reason we need to handle the gridRendered events and
							// fire a single children populated after the last gridRendered is called
							id = parentrow.attr("data-id");
							if (!this._eventQueue[ "id" + id ] || !this._eventQueue[ "id" + id ].length) {
								this._eventQueue[ "id" + id ] = [];
							}
							h =  $.proxy(this._childRendered, this);
							this._eventQueue[ "id" + id ].push({
								grid: childgrids[ i ],
								handler: h, parentrow: parentrow,
								id: rowid
							});
							childgrids[ i ].bind("iggridrendered", h);
							if (this.options.autoGenerateLayouts === true) {
								opts[ i ]._recurseSchema = true;
							}
							/* M.H. 10 Dec 2012 Fix for bug #126991 */
							if (isHierarchical) {
								opts[ i ]._isHierarchicalGrid = true;
							}
							pgInstance = parentrow.closest(".ui-iggrid-table").data("igGrid");
							if (pgInstance) {
								opts[ i ].foreignKeyValue = pgInstance._fixPKValue(parentrow.data("id"));
							} else {
								opts[ i ].foreignKeyValue = parentrow.data("id");
							}
							/*M.K. Fix for bug 215707: eventArgs are not correctly set for layouts when there are more than one layout in the ighierarachicalgrid */
							eArgs = {
								owner: this,
								options: opts[ i ],
								element: childgrids[ i ],
								id: rowid,
								path: keyspath + keyspathvar
							};
							/* M.H. 21 Apr 2015 Fix for bug 192978: Event igChildCreating/igChildCreated are not fired when initialDataBindDepth is 0 */
							childgrids[ i ].trigger(this.events.childGridCreating, eArgs);
							noCancelChild = childgrids[ i ].attr("data-create");
							if (noCancelChild !== "false") {
								childgrids[ i ].igGrid(opts[ i ]);
								childgrids[ i ].trigger(this.events.childGridCreated, {
									owner: this,
									element: childgrids[ i ]
								});
								if (isHierarchical) {
									this._regToggleEvent(childgrids[ i ], this);
								}
							}
						}
					}
				}
				animatec.attr("id", "ac_" + parentrow.attr("data-id"));
				parentrow.attr("c", true);
				parentrow.next().hide();
				animatec.hide();
				this._expand(parentrow, suppressEvents, callback);
			} else {
				// just toggle the visibility
				if (state === "e") {
					// collapse
					this._collapse(parentrow, suppressEvents, callback);
				} else if (state === "c") {
					// expand
					this._expand(parentrow, suppressEvents, callback);
				}
			}
			if (this.indicator) {
				this.indicator.hide();
			}
			parentrow.find(".ui-iggrid-expandbutton").css("visibility", "visible");
		},
		/* parse the hierarchical layout definitions */
		_layouts: function () {
			// traverse the DS and generate the layouts.
			// does autoGenerateLayouts make any sense at all ?
			// traverses the existing data source and auto generates the layouts
			this.element.unbind("iggriddatarendering", this._databoundHandler);
		},
		_columnsGenerated: function (event, args) {
			// this is necessary cause when columns start to be generated, we want to fill the root layouts.
			// potential optimization: if columns get generated for one child grid, when another child grid from the same layout is expanded,
			// we don't need to generate its own columns - we can put the existing ones in its own options when they are passed to .igGrid(..)
			var layoutKey = args.key, layout;
			if (!layoutKey) {
				return;
			}
			/* search the layout, and if it exists and doesn't have columns, add args.owner.options.columns to it */
			layout = this._findLayout(this.rootWidget().options.columnLayouts, layoutKey);
			if (layout.columns && layout.columns.length && layout.columns.length > 0) {
				return;
			}
			layout.columns = args.owner.options.columns;
		},
		_findLayout: function (layouts, key) {
			var i, layout;
			for (i = 0; layouts && i < layouts.length; i++) {
				if (layouts[ i ].key === key) {
					layout = layouts[ i ];
					break;
				}
				if (!layout && layouts[ i ].columnLayouts) {
					layout = this._findLayout(layouts[ i ].columnLayouts, key);
				}
			}
			return layout;
		},
		_hdsoptions: function () {
			var opts, schema, instanceOfHds;
			opts = {
				responseDataKey: this.options.responseDataKey,
				responseTotalRecCountKey: this.options.responseTotalRecCountKey,
				dataSource: this.options.dataSource,
				primaryKey: this.options.primaryKey,
				localSchemaTransform: this.options.localSchemaTransform,
				autoCommit: this.options.autoCommit,
				odata: this.options.odata,
				serializeTransactionLog: this.options.serializeTransactionLog,
				updateUrl: this.options.updateUrl,
				restSettings: this.options.restSettings,
				initialDataBindDepth: this.options.initialDataBindDepth
			};
			if (this.options.dataSourceType !== null) {
				opts.type = this.options.dataSourceType;
			}
			schema = this._hschema();
			instanceOfHds = this.options.dataSource &&
							typeof this.options.dataSource._encodeHierarchicalUrlParams === "function" &&
							this.options.dataSource.settings && this.options.dataSource.settings.hasOwnProperty &&
							this.options.dataSource.settings.hasOwnProperty("initialDataBindDepth");
			if ((instanceOfHds && this.options.dataSource.settings.schema === null) || !instanceOfHds) {
				opts = $.extend(opts, { schema: schema });
			}
			return opts;
		},
		/* M.H. 20 Sep 2012 Fix for bug #121981 */
		_getDataColumns: function (cols) {
			var i, j, res = [], colsLength = cols.length, dCols;

			for (i = 0; i < colsLength; i++) {
				if (cols[ i ].group !== undefined && cols[ i ].group !== null) {
					dCols = this._getDataColumns(this._getDataColumns(cols[ i ].group));
					for (j = 0; j < dCols.length; j++) {
						res.push(dCols[ j ]);
					}
				} else {
					res.push(cols[ i ]);
				}
			}
			return res;
		},
		_hschema: function () {
			// M.H. 20 Sep 2012 Fix for bug #121981
			var schema, i, j, rec, prop, cols = this._getDataColumns(this.options.columns),
				encodedLayouts = [], k, c;
			/* generate top level schema */
			if (cols.length > 0 && !this.options.autoGenerateColumns) {
				// if autoGenerateColumns is true, fields for all columns in the data source must be specified
				schema = {};
				schema.fields = [];
				j = 0;
				for (i = 0; i < cols.length; i++) {
					// M.H. 4 Sep 2012 Fix for bug #120414
					if (cols[ i ].unbound === true) {
						continue;
					}
					schema.fields[ j ] = {};
					schema.fields[ j ].name = cols[ i ].key;
					schema.fields[ j ].type = cols[ i ].dataType;
					j++;
				}
				schema.searchField = this.options.responseDataKey;
			} else if (this.options.autoGenerateColumns) {
				schema = {};
				schema.fields = [];
				if (this.options.dataSource && this.options.dataSource.length &&
						this.options.dataSource.length > 0 &&
						$.type(this.options.dataSource) === "array") {
					rec = this.options.dataSource[ 0 ];
					for (prop in rec) {
						if (rec.hasOwnProperty(prop)) {
							// if the column isn't already defined in the columns collection
							for (k = 0; k < cols; k++) {
								if (cols[ k ].key === prop) {
									c = cols[ k ];
									break;
								}
							}
							if (c === null || c === undefined) {
								schema.fields.push({ name: prop, type: $.ig.getColType(rec[ prop ]) });
							} else {
								// M.H. 4 Sep 2012 Fix for bug #120414
								if (c.unbound === true) {
									continue;
								}
								schema.fields.push({ name: prop, type: c.dataType });
							}
							/* count++; */
						}
					}
				}
			}
			/* for (i = 0; i < this.options.columns.length; i++) { */
			if (this.options.columnLayouts && this.options.columnLayouts.length > 0) {
				for (j = 0; j < this.options.columnLayouts.length; j++) {
					schema.fields.push({
						name: this.options.columnLayouts[ j ].childrenDataProperty === undefined ?
							this.options.columnLayouts[ j ].key :
							this.options.columnLayouts[ j ].childrenDataProperty
					});
				}
			}
			/*}
			encode layouts. Layouts are encoded as part of the settings of the schema of the
			hierarchical data source. They are stored in a hash (normal JS array, which it always a hash)
			where the hey is the "path" of primary keys, such as /Code:<childrenDataProperty>:ID/<level 2 definition ..>
			which means parent col Code => connected to ID as the primary key, and children are stored in the
			<childrenDataProperty. so that we can have multiple layout configurations bound to the same data
			with different settings
			second option is the current layout "path" which is recursively accumulated */
			this._parseLayouts(encodedLayouts, "", this.options);
			schema.layouts = encodedLayouts;
			return schema;
		},
		_parseLayouts: function (layouts, cpath, options) {
			var j, layout, p, cdp = null;
			if (options.columnLayouts && options.columnLayouts.length !== undefined &&
				options.columnLayouts.length > 0) {
				for (j = 0; j < options.columnLayouts.length; j++) {
					layout = options.columnLayouts[ j ];
					cdp = layout.childrenDataProperty === undefined ? layout.key : layout.childrenDataProperty;
					if (!cdp) {
						cdp = this.options.defaultChildrenDataProperty;
					}
					p = cpath + this.options.pathSeparator + cdp + ":" + layout.primaryKey;
					layouts[ p ] = layout;
					this._parseLayouts(layouts, p, layout);
				}
			}
		},
		_batchExpand: function () {
			var crows = [], tmpanim, hgrid;
			hgrid = this.element.closest(".ui-iggrid-root").data("igHierarchicalGrid");
			if (!hgrid) {
				hgrid = this.element.data("igHierarchicalGrid");
			}
			tmpanim = hgrid.options.expandCollapseAnimations;
			hgrid.options.expandCollapseAnimations = false;
			crows = this.element.find("tr[data-id]");
			/* get all rows
			now call off the hierarchical grid object */
			hgrid._batchExpandLevel(crows);
			hgrid.options.expandCollapseAnimations = tmpanim;
		},
		_batchExpandLevel: function (rows) {
			var i;
			for (i = 0; i < rows.length; i++) {
				this.toggle(rows[ i ]);
			}
		},
		_renderExtraHeaderCell: function (row, colgroup, prepend) {
			if (prepend === true) {
				$("<td></td>").prependTo(row).css("border-width", 0).attr("data-skip", true);
			} else {
				$("<td></td>").appendTo(row).css("border-width", 0).attr("data-skip", true);
			}
		},
		_renderExtraFooterCell: function (row, colgroup, prepend, cssClass) {
			if (prepend === true) {
				$("<td></td>").addClass(cssClass).prependTo(row).attr("data-skip", true);
			} else {
				$("<td></td>").addClass(cssClass).appendTo(row).attr("data-skip", true);
			}
		},
		/* M.H. 10 May 2012 Fix for bug #108221 */
		_headerrenderedinternal: function (sender, args) {
			// M.H. 9 Oct 2012 Fix for bug #124072
			if (args.owner.element[ 0 ].id !== sender.currentTarget.id &&
					args.owner.element[ 0 ].id !== sender.currentTarget.id + "_table") {
				return;
			}
			var self = this, flatRenderColgroup = args.owner._renderColgroup, tmpid, $thDataSkip;
			args.owner._renderRecord = $.proxy(this._hierarchicalRenderRecord, args.owner);

			args.owner._getFirstVisibleTR = $.proxy(this._hierarchicalGetFirstVisibleTR, args.owner);
			args.owner._getLastVisibleTR = $.proxy(this._hierarchicalGetLastVisibleTR, args.owner);

			args.owner._renderRecordInArray = $.proxy(this._hierarchicalRenderRecordInArray, args.owner);
			args.owner._renderColgroup = function () {
				self._hierarchicalRenderColgroup
					.apply(args.owner, $.merge([ flatRenderColgroup, self ], arguments));
			};
			args.owner._headerInitCallbacks.push({
				type: "HierarchicalGrid",
				func: $.proxy(this._renderExtraHeaderCell, this)
			});
			args.owner._footerInitCallbacks.push({
				type: "HierarchicalGrid",
				func: $.proxy(this._renderExtraFooterCell, this)
			});
			args.owner._trigger("headerextracellsmodified", null, { owner: args.owner });
			/*args.owner._registerAdditionalEvents = $.proxy(this._registerAdditionalEvents, args.owner); */
			/* insert extra <col> and extra <th> in front */
			/* M.H. 13 Nov 2012 Fix for bug #126991 */
			if (args.table.attr("id") !== args.owner.element.attr("id") &&
					$(args.table).find("colgroup col[data-expander]").length === 0) {
				$("<col></col>").attr("data-skip", "true").attr("data-expander", true)
					.prependTo(args.table.find("colgroup")).width(this.options.expandColWidth);
			}

			$thDataSkip = $("<th></th>");
			$("<span></span>").appendTo($thDataSkip.prependTo(args.table.find("thead tr:first"))
				.addClass(this.css.expandHeaderCell)
				.attr("data-skip", true)).addClass(this.css.expandHeaderCellTriangle);
			if (args.owner._isMultiColumnGrid) {
				$thDataSkip.attr("rowspan", args.owner._maxLevel + 1);
			}
			/* A.T. 4 April 2012 - This isn't necessary any more, because we are doing this generically in grid.framework => dataRender => any col which has data-skip
			increase the container grid width, if there is no grid width defined, but there are column widths defined */
			/*if (args.owner.options.width === null && args.owner.options.columns.length > 0 && args.owner.options.columns[0].width !== null) {
				args.owner.container().width(parseInt(args.owner.container().width(), 10) + parseInt(this.options.expandColWidth, 10));
			} */
			/* M.H. 5 Nov 2012 Fix for bug #126444 */
			if (args.owner._isWrapped) {
				tmpid = args.owner.element.attr("id")
					.substring(0, args.owner.element.attr("id").indexOf("_table"));
				/* M.H. 10 May 2012 Fix for bug #108221 */
				args.owner.container().find("#" + tmpid)
					.unbind("iggridheaderrenderedinternal", args.owner.element.data("hr"));
				args.owner.container().find("#" + tmpid).removeData("hr");
			} else {
				// M.H. 10 May 2012 Fix for bug #108221
				args.owner.element.unbind("iggridheaderrenderedinternal", args.owner.element.data("hr"));
				args.owner.element.removeData("hr");
			}
		},
		_checkIsMultiColumnHeader: function (lobj) {
			var i, cols = lobj.columns, colsLength;
			/* M.H. 31 July 2012 Fix for bug #115599 */
			if (cols) {
				colsLength = cols.length;
				for (i = 0; i < colsLength; i++) {
					if (cols[ i ].group !== undefined && cols[ i ].group !== null) {
						return true;
					}
				}
			}
			/* M.H. 16 May 2014 Fix for bug #171779: Cannot sort parent grid columns if child grid has multi column headers and the parent grid does not */
			/*if (columnLayouts) {
				columnLayoutsLength = columnLayouts.length;
				for (i = 0; i < columnLayoutsLength; i++) {
					if (this._checkIsMultiColumnHeader(columnLayouts[ i ]) === true) {
						return true;
					}
				}
			} */

			return false;
		},
		_hierarchicalRenderRecord: function (data, rowIndex) {
			// generate a Tr and append it to the table
			var i, /*ar = this.options.accessibilityRendering, */grid = this, lod, hg, childprop, dstr = "",
				val, markup = "", layouts, hasChildren, noVisibleColumns, layout, temp;
			if (rowIndex % 2 !== 0 && this.options.alternateRowStyles) {
				dstr += "<tr class='" + grid.css.recordAltClass + "'";
			} else {
				dstr += "<tr";
			}

			/* data index to which the row is bound */
			dstr += " data-row-idx='" + rowIndex + "'";

			if (!this._hg) {
				hg = this.element.closest(".ui-iggrid-root").data("igHierarchicalGrid");
				if (!hg) {
					hg = this.element.hasClass(".ui-iggrid-root") ? this.element : undefined;
				}
				this._hg = hg;
			} else {
				hg = this._hg;
			}
			/* render row ID, check if the grid has a primary key defined. It is also mandatory to check if the grid has any parents, and append existing "paths" */
			if (this.options.foreignKey) {
				// handle composite keys; they are always separated by comma in the value of foreignKey
				/* jscs:disable */
				dstr += " data-id-fk='" + this._kval_from_key(this.options.foreignKey, data) + "'";
				/* jscs:enable */
			} else {
				if (this.options.primaryKey === null || this.options.primaryKey === undefined) {
					// generate a primary key and put it in the data source in another column called ig_pk
					this.options.primaryKey = "ig_pk";
					val = hg._getVal(data);
					dstr += " data-id-fk='" + val + "'";
				} else if (this.options.primaryKey === "ig_pk") {
					val = hg._getVal(data);
					dstr += " data-id-fk='" + val + "'";
				} else {
					// generate record foreign key ID
					// handle composite keys
					/* jscs:disable */
					dstr += " data-id-fk='" + this._kval_from_key(this.options.primaryKey, data) + "'";
					/* jscs:enable */
				}
			}
			if (this.options.primaryKey === null || this.options.primaryKey === undefined) {
				//throw new Error($.ig.Grid.locale.noPrimaryKey);
				this.options.primaryKey = "ig_pk";
				val = hg._getVal(data);
				dstr += " data-id='" + val + "'";
			} else if (this.options.primaryKey === "ig_pk") {
				val = hg._getVal(data);
				dstr += " data-id='" + val + "'";
			} else {
				// generate record ID
				/* jscs:disable */
				dstr += " data-id='" + this._kval_from_key(this.options.primaryKey, data) + "'";
				/* jscs:enable */
			}
			/*if (ar) { */
			dstr += " role='row' aria-expanded='false' tabindex='" + this.options.tabIndex + "'>";
			/* } else {
				dstr += '>';
			} */
			/* add hierarchical td
			check if there is child data, and load on demand is not enabled
			first we need to get the layout's childrenDataProperties so that we aren't
			checking all properties which are objects (arrays) */
			layouts = this.options.columnLayouts;
			hasChildren = false;
			lod = hg ? hg.options.initialDataBindDepth > -1 : false;
			if (!lod) {
				for (i = 0; layouts && i < layouts.length; i++) {
					if (data[ layouts[ i ].key ]) {
						childprop = layouts[ i ].key;
						layout = layouts[ i ];
					} else {
						childprop = layouts[ i ].childrenDataProperty;
					}
					/* if (!layout) {
						layout = this.options;
					}*/
					childprop = childprop || this.options.defaultChildrenDataProperty;
					if (childprop) {
						if (layout && layout.responseDataKey) {
							if (data[ childprop ] && data[ childprop ][ layout.responseDataKey ] &&
									data[ childprop ][ layout.responseDataKey ].length !== undefined &&
									(data[ childprop ][ layout.responseDataKey ].length > 0 ||
									this._shouldAlwaysRenderChildIndicator)) {
								hasChildren = true;
								break;
							}
						} else {
							// M.H. 29 May 2014 Fix for bug #170463: A JavaScript exception is thrown when igHierarchicalGrid.childrenDataProperty is used instead of igHierarchicalGrid.key
							// make sure the case where the children also have response key is also considered: || $.type(data[ childprop ]) === "object"
							if (data[ childprop ]) {
								if (data[ childprop ].length !== undefined &&
									(data[ childprop ].length > 0 ||
									this._shouldAlwaysRenderChildIndicator)) {
									hasChildren = true;
									break;
								}
								/* M.H. 29 May 2014 Fix for bug #170463: A JavaScript exception is thrown when igHierarchicalGrid.childrenDataProperty is used instead of igHierarchicalGrid.key */
								if (this.options.responseDataKey && data[ childprop ][ this.options.responseDataKey ] &&
									data[ childprop ][ this.options.responseDataKey ].length !== undefined &&
									(data[ childprop ][ this.options.responseDataKey ].length > 0 ||
									this._shouldAlwaysRenderChildIndicator)) {
									hasChildren = true;
									break;
								}
							}
						}
					}
				}
			} else {
				hasChildren = true;
			}
			if (hasChildren) {
				markup = "<span class='ui-iggrid-expandbuttoncontainer'>" +
					"<span class='ui-iggrid-expandbutton ui-icon ui-icon-plus' title='" +
					hg.options.expandTooltip + "'></span></span>";
			}
			dstr += "<td tabindex='0' class='ui-iggrid-expandcolumn" + grid._addCellStyle(data, -1)
				.replace(/class=\"/, "").replace(/\"/, "") +
				"' data-parent='" + hasChildren + "'>" + markup + "</td>";
			noVisibleColumns = true;
			$(this.options.columns).each(function (colIndex) {
				var col = grid.options.columns[ colIndex ];
				if (col.hidden) {
					return;
				}
				noVisibleColumns = false;
				/*if (ar) { */
				dstr += "<td role='gridcell'  aria-readonly=" + !!this.readOnly +
					" aria-describedby='" + grid.id() + "_" + this.key +
					"' tabindex='" + grid.options.tabIndex + "'";
				/* } else {
					dstr += '<td';
				} */
				if (data[ this.key ] === undefined) {
					if (this.template && this.template.length) {
						// M.H. 2 Sep 2013 Fix for bug #151051: Template is not working for the unbound column in the root layout in igHierarchicalGrid
						if (col.unbound) {
							temp = grid._renderTemplatedCell(data, this);
						} else {
							temp = grid._renderTemplatedCell(data[ colIndex ], this);
						}
						if (temp.indexOf("<td") === 0) {
							dstr += temp.substring(3);
						} else {
							dstr += ">" + temp;
						}
						dstr = grid._editCellStyle(dstr, data, colIndex);
					} else {
						dstr += grid._addCellStyle(data, colIndex, col) + ">" +
							grid._renderCell(data[ colIndex ], this, data);
					}
					dstr += "</td>";
				} else {
					if (this.template && this.template.length) {
						temp = grid._renderTemplatedCell(data, this);
						if (temp.indexOf("<td") === 0) {
							dstr += temp.substring(3);
						} else {
							dstr += ">" + temp;
						}
						dstr = grid._editCellStyle(dstr, data, this.key);
					} else {
						dstr += grid._addCellStyle(data, this.key, col) +
							">" + grid._renderCell(data[ this.key ], this, data);
					}
					dstr += "</td>";
				}
			});
			if (noVisibleColumns) {
				dstr += "<td role='gridcell'></td>";
			}
			dstr += "</tr>";
			return dstr;
		},
		_getVal: function (data) {
			var val;
			/* jscs:disable */
			if (data.ig_pk) {
				val = data.ig_pk;
				/* jscs:enable */
			} else {
				val = $.ig.util.getCheckSumForObject(data);//$.ig.uid();
				/* jscs:disable */
				data.ig_pk = val;
				/* jscs:enable */
			}
			return val;
		},
		_hierarchicalRenderRecordInArray: function (darr, tbody, data, rowIndex) {
			// generate a Tr and append it to the table
			var i, /*ar = this.options.accessibilityRendering, */grid = this, lod, hg, childprop, layout,
				val, responseDataKey, markup = "", layouts, hasChildren,
				noVisibleColumns, appendBehavior = false, temp,
				tdIndex;
			if (darr === null) {
				darr = [];
				appendBehavior = true;
			}
			if (rowIndex % 2 !== 0 && this.options.alternateRowStyles) {
				darr.push("<tr class='" + grid.css.recordAltClass + "'");
			} else {
				darr.push("<tr");
			}
			/* render row ID, check if the grid has a primary key defined. It is also mandatory to check if the grid has any parents, and append existing "paths" */
			if (this.options.foreignKey) {
				// handle composite keys; they are always separated by comma in the value of foreignKey
				/* jscs:disable */
				darr.push(" data-id-fk='" + this._kval_from_key(this.options.foreignKey, data) + "'");
				/* jscs:enable */
			} else {
				if (this.options.primaryKey === null || this.options.primaryKey === undefined) {
					//throw new Error($.ig.Grid.locale.noPrimaryKey);
					// generate a primary key and put it in the data source in another column called ig_pk
					this.options.primaryKey = "ig_pk";
					/* refactor in separate func */
					val = $.ig.util.getCheckSumForObject(data);//$.ig.uid();
					darr.push(" data-id-fk='" + val + "'");
					/* jscs:disable */
					data.ig_pk = val;
					/* jscs:enable */
				} else if (this.options.primaryKey === "ig_pk") {
					val = $.ig.util.getCheckSumForObject(data);//$.ig.uid();
					darr.push(" data-id-fk='" + val + "'");
					/* jscs:disable */
					data.ig_pk = val;
					/* jscs:enable */
				} else {
					// generate record foreign key ID
					// handle composite keys
					/* jscs:disable */
					darr.push(" data-id-fk='" + this._kval_from_key(this.options.primaryKey, data) + "'");
					/* jscs:enable */
				}
			}
			if (this.options.primaryKey === null || this.options.primaryKey === undefined) {
				//throw new Error($.ig.Grid.locale.noPrimaryKey);
				this.options.primaryKey = "ig_pk";
				val = $.ig.util.getCheckSumForObject(data);//$.ig.uid();
				darr.push(" data-id='" + val + "'");
				/* jscs:disable */
				data.ig_pk = val;
				/* jscs:enable */
			} else if (this.options.primaryKey === "ig_pk") {
				val = $.ig.util.getCheckSumForObject(data);//$.ig.uid();
				darr.push(" data-id='" + val + "'");
				/* jscs:disable */
				data.ig_pk = val;
				/* jscs:enable */
			} else {
				// generate record ID
				/* jscs:disable */
				darr.push(" data-id='" + this._kval_from_key(this.options.primaryKey, data) + "'");
				/* jscs:enable */
			}
			/*if (ar) { */
			darr.push(" role='row'>");
			/* } else {
				darr.push('>');
			} */
			/* add hierarchical td
			check if there is child data, and load on demand is not enabled
			first we need to get the layout's childrenDataProperties so that we aren't
			checking all properties which are objects (arrays) */
			layouts = this.options.columnLayouts;
			hasChildren = false;
			/* optimize */
			hg = this.element.closest(".ui-iggrid-root").data("igHierarchicalGrid");
			if (!hg) {
				hg = this.element.hasClass(".ui-iggrid-root") ? this.element : undefined;
			}
			lod = hg ? hg.options.initialDataBindDepth > -1 : false;
			if (!lod) {
				for (i = 0; layouts && i < layouts.length; i++) {
					layout = layouts[ i ];
					if (data[ layout.key ]) {
						childprop = layout.key;
					} else {
						childprop = layout.childrenDataProperty;
					}
					childprop = childprop || this.options.defaultChildrenDataProperty;
					/* try to get responseDataKey from the corresponding layout. If it is not set take it from the options */
					/* M.H. 15 Aug 2014 Fix for bug #177493: When grouping a column in a hierarchical grid the child grids are lost if their responseDataKey doesn't match the responseDataKey of the parent grid */
					responseDataKey = (layout && layout.responseDataKey) || this.options.responseDataKey;
					if (childprop && data[ childprop ]) {
						// make sure the case where the children also have response key is also considered
						if (responseDataKey && data[ childprop ][ responseDataKey ] &&
								data[ childprop ][ responseDataKey ].length !== undefined &&
								data[ childprop ][ responseDataKey ].length > 0) {
							hasChildren = true;
							break;
						}
						if (data[ childprop ].length !== undefined && data[ childprop ].length > 0) {
							hasChildren = true;
							break;
						}
					}
				}
			} else {
				hasChildren = true;
			}
			if (hasChildren) {
				markup = "<span class='ui-iggrid-expandbuttoncontainer'>" +
					"<span class='ui-iggrid-expandbutton ui-icon ui-icon-plus' title='" +
					hg.options.expandTooltip + "'></span></span>";
			}
			darr.push("<td tabindex='0' class='ui-iggrid-expandcolumn" + grid._addCellStyle(data, -1) +
				"' data-parent='" + hasChildren + "'>" + markup + "</td>");
			noVisibleColumns = true;
			$(this.options.columns).each(function (colIndex) {
				if (grid.options.columns[ colIndex ].hidden) {
					return;
				}
				noVisibleColumns = false;
				/*if (ar) { */
				darr.push("<td role='gridcell' aria-readonly=" + !!this.readOnly +
					" aria-describedby='" + grid.id() + "_" + this.key +
					"' tabindex='" + grid.options.tabIndex + "'");
				/* } else {
					darr.push('<td');
				} */
				if (data[ this.key ] === undefined) {
					if (this.template && this.template.length) {
						temp = grid._renderTemplatedCell(data, this);
						if (temp.indexOf("<td") === 0) {
							tdIndex = darr.length - 1;
							darr[ tdIndex ] = temp.replace("<td", darr[ tdIndex ]);
						} else {
							darr.push(">" + temp);
							tdIndex = darr.length - 2;
						}
						darr[ tdIndex ] = grid._editCellStyle(darr[ tdIndex ], data, colIndex);
					} else {
						darr.push(grid._addCellStyle(data, colIndex,
							grid.options.columns[ colIndex ]) + ">" + grid._renderCell(data[ colIndex ],
							this, data));
					}
					darr.push("</td>");
				} else {
					if (this.template && this.template.length) {
						temp = grid._renderTemplatedCell(data, this);
						if (temp.indexOf("<td") === 0) {
							tdIndex = darr.length - 1;
							darr[ tdIndex ] = temp.replace("<td", darr[ tdIndex ]);
						} else {
							darr.push(">" + temp);
							tdIndex = darr.length - 2;
						}
						darr[ tdIndex ] = grid._editCellStyle(darr[ tdIndex ], data, this.key);
					} else {
						darr.push(grid._addCellStyle(data, this.key,
							grid.options.columns[ colIndex ]) + ">" + grid._renderCell(data[ this.key ],
							this, data));
					}
					darr.push("</td>");
				}
			});
			if (noVisibleColumns) {
				darr.push("<td role='gridcell'></td>");
			}
			darr.push("</tr>");
			if (appendBehavior) {
				tbody.append(darr.join(""));
			}
		},
		_hierarchicalRenderColgroup: function (flatRenderColgroup, hierarchicalGrid, table,
				isHeader, isFooter, autofitLastColumn) {
			var $colgroup;
			flatRenderColgroup.apply(this, [ table, isHeader, isFooter, autofitLastColumn ]);
			$colgroup = $(table).find(">colgroup");
			if ($colgroup.find(">col[data-expander]").length > 0) {
				return;
			}
			$("<col></col>")
				.prependTo($colgroup)
				.css("width", hierarchicalGrid.options.expandColWidth)
				.attr("data-skip", "true")
				.attr("data-expander", true);
		},
		_schemaGenerated: function (event, args) {
			// check the initialDataBindDepth of the root grid, if it is greater than -1 (load on demand), do nothing
			// since we have load on demand and we don't care (on the client), if parent rows have children or not, at this very moment
			var hg, i, rec, schema = args.schema, ds = args.dataSource, $this = args.owner, prop;
			hg = $this.element.closest(".ui-iggrid-root").data("igHierarchicalGrid");
			if (!hg) {
				hg = $this.element.hasClass(".ui-iggrid-root") ? $this.element : undefined;
			}
			if (!hg || hg.options.initialDataBindDepth > -1) {
				return;
			}
			/* add fields which are complex objects (or arrays) */
			for (i = 0; ds && ds.length && $.type(ds) === "array" && i < ds.length; i++) {
				rec = ds[ i ];
				for (prop in rec) {
					if (rec.hasOwnProperty(prop)) {
						if (!$this._fieldExists(prop, schema) &&
							($.type(rec[ prop ]) === "array" ||
							$.type(rec[ prop ]) === "object")) {
							schema.fields.push({ name: prop, type: $.ig.getColType(rec[ prop ]) });
						}
					}
				}
			}
		},
		_childRendered: function (sender, args) {
			// traverse the event queue specified by args.owner.element[ id ] and check if all
			// events have been fired, then fire the populated event
			// MH: if remote load on demand then this._eventQueue[ pid ] will be undefined and we just need to trigger childGridRendered, unbind iggridrendered
			// {grid: childgrids[ i ], handler: h, parentrow: parentrow, id: rowid}
			var ptr = args.owner.element.closest("tr").prev(),
				pid = "id" + ptr.attr("data-id"),
				e = this._eventQueue[ pid ], i, fire = true;
			/* M.H. 21 Apr 2015 Fix for bug 192978: Event igChildCreating/igChildCreated are not fired when initialDataBindDepth is 0 */
			this._trigger(this.events.childGridRendered, null, {
				owner: this,
				parentrow: ptr,
				childgrid: args.owner
			});
			/* it is fired when Load on demand is enabled and this._eventQueue is empty */
			if (!e) {
				args.owner.element.unbind("iggridrendered.hierarchicalgrid");
				return;
			}
			for (i = 0; e.length !== undefined && i < e.length; i++) {
				if (e[ i ].grid.attr("id") === args.owner.element.attr("id")) {
					// we found the child grid which was just rendered
					e[ i ].done = true;
					/* unbind */
					e[ i ].grid.unbind("iggridrendered", e[ i ].handler);
					break;
				}
			}
			/* check if all grids are rendered, and if so, fire the populated event */
			for (i = 0; e.length !== undefined && i < e.length; i++) {
				if (!e[ i ].done) {
					fire = false;
					break;
				}
			}
			if (fire) {
				// fire childrenPopulated event
				this._trigger(this.events.childrenPopulated, null, {
					owner: this,
					parentrow: ptr,
					id: e[ 0 ].id
				});
				/* clear queue */
				delete this._eventQueue[ pid ];
			}
		},
		/*
		_registerAdditionalEvents: function () {
			var css = 'ui-state-hover';
			// hovering (Hot Tracking) and unhovering
			$('#' + this.element[ 0 ].id + ' th.ui-state-default').hover(
				function () { $(this).addClass('ui-state-hover'); },
				function () { $(this).removeClass('ui-state-hover'); }
			);
			this.element.bind(this._hovEvts = {
				mousemove: function (e) {
					var par, tag, tr = e.target;
					while (tr) {
						par = tr.parentNode;
						if ((tag = tr.nodeName) === 'TR' && par.nodeName === 'TBODY') {
							break;
						}
						tr = (tag === 'TABLE') ? null : par;
					}
					if (_hovTR !== tr) {
						// A.T. crc = child row container. don't hover such rows.
						if (_hovTR && $(_hovTR).attr('crc') !== "true") {
							$('td', _hovTR).removeClass(css);
						}
						if (tr && $(tr).attr('crc') !== "true") {
							$('td', tr).addClass(css);
						}
						_hovTR = tr;
					}
				},
				mouseleave: function (e) {
					if (_hovTR && $(_hovTR).attr('crc') !== "true") {
						$('td', _hovTR).removeClass(css);
						_hovTR = null;
					}
				}
			});
		},
		*/
		/* returns child options given parent options */
		_optsFor: function (popts) {
			var i, j, o = [], layouts;
			if (popts.columnLayouts && popts.columnLayouts.length > 0 &&
				$.type(popts.columnLayouts) === "array") {
				layouts = popts.columnLayouts;
				for (j = 0; j < layouts.length; j++) {
					// handle features inheritance. Parameters passed - parent options and child options
					this._inherit(popts, layouts[ j ]);
					o.push($.extend(true, {}, $.ui.igGrid.prototype.options, layouts[ j ]));
				}
			}
			/* L.A. 15 January 2013 - Fixing bug #130730 */
			/* When the dataSource of the HGrid is URL the remote features in child layouts do not use the correct Url for their requests. */
			for (i = 0; i < o.length; i++) {
				if (!o[ i ].dataSource) {
					o[ i ].dataSourceUrl = popts.dataSourceUrl;
				} else {
					// L.A. 15 February 2013 - Combined fix for bugs 128891, 130642, 132970
					if (String(popts.initialDataBindDepth) === "-1" || popts.initialDataBindDepth === undefined) {
						o[ i ].dataSourceUrl = o[ i ].dataSource;
					}
				}
			}

			return o;
		},
		_inherit: function (parent, child) {
			// traverse all parent features
			var i, f, j, nowrite;
			if (parent.dataSourceType) {
				child.dataSourceType = parent.dataSourceType;
			}
			if (!parent.features || parent.features.length === undefined || parent.features.length === 0) {
				return;
			}
			if (!child.features || child.features.length === undefined) {
				child.features = [];
			}
			for (i = 0; i < parent.features.length; i++) {
				if (parent.features[ i ].inherit === true) {
					// check if there is a feature with the same name (id) defined on the child layout
					f = null;
					for (j = 0; j < child.features.length; j++) {
						if (child.features[ j ].name === parent.features[ i ].name) {
							f = child.features[ j ];
							break;
						}
					}
					if (f === null || f === undefined) {
						// just copy everything, excluding columnSettings.
						// columnSettings cannot be inherited because of difference of columns !
						child.features.push($.extend(true, {}, parent.features[ i ]));
						child.features[ child.features.length - 1 ].columnSettings = [];
					} else {
						// merge the options with the child ones taking priority (meaning they override the parent ones)
						nowrite = false;
						if (child.features[ j ].columnSettings && child.features[ j ].columnSettings.length > 0) {
							nowrite = true;
						}
						child.features[ j ] = $.extend(true, {}, parent.features[ i ], f);
						if (!nowrite) {
							child.features[ j ].columnSettings = [];
						}
					}
				}
			}
		},
		/* decorates the flat grid with hierarchical functionality */
		_regevents: function (e, context) {
			// M.H. 10 May 2012 Fix for bug #108221
			var headerRenderedInternal, datarendered, schemaGenerated;
			/* M.H. 10 May 2012 Fix for bug #108221 */
			headerRenderedInternal = $.proxy(context._headerrenderedinternal, context);
			datarendered = $.proxy(this._handleBatchExpandRender, context);
			schemaGenerated = $.proxy(this._schemaGenerated, context);
			/* M.H. 10 May 2012 Fix for bug #108221 - bind to this event. It is called even when showHeader is false */
			e.bind("iggridheaderrenderedinternal.hierarchicalgrid", headerRenderedInternal);
			e.bind("iggriddatarendered.hierarchicalgrid", datarendered);
			e.bind("iggridschemagenerated.hierarchicalgrid", schemaGenerated);
			/* A.T. watch out for live()'s perf. AND SELECTORS. */
			/* M.H. 5 Nov 2012 Fix for bug #126444 */
			/* M.H. 10 May 2012 Fix for bug #108221 */
			e.data("hr", headerRenderedInternal);
		},
		_regToggleEvent: function (e, context) {
			var toggle;
			/* M.H. 18 June 2014 Fix for bug #172395: [Hierarchical Grid] 'initialExpandDepth' property is not working */
			/* this is applied as a fix for bug 130025 */
			toggle = $.proxy(context._toggleInternalWithDelay, context);
			/* A.T. 31 Jan 2012 - fix for bug # */
			e.undelegate(".ui-iggrid-expandcolumn > .ui-iggrid-expandbuttoncontainer", "mousedown");
			e.delegate(".ui-iggrid-expandcolumn > .ui-iggrid-expandbuttoncontainer", "mousedown", toggle);
		},
		commit: function () {
			/* Commits pending transactions to the client data source for main and all child grids.
			*/
			var elem = this.element;
			elem.find(".ui-iggrid-table").each(function () {
				$(this).igGrid("commit");
			});
			elem.igGrid("commit");
		},
		rollback: function (rebind) {
			/* Clears the transaction log (delegates to igDataSource). Note that this does not update the UI. In case the UI must be updated, set the second parameter "updateUI" to true, which will trigger a call to dataBind() to re-render the contents.
				paramType="bool" optional="true" Whether to perform a rebind.
			*/
			var elem = this.element;
			elem.find(".ui-iggrid-table").each(function () {
				$(this).igGrid("rollback", null, rebind);
			});
			elem.igGrid("rollback", null, rebind);
		},
		_addToLog: function (elem, taLog, child) {
			var grid = elem.data("igGrid"),
				ds = grid ? grid.dataSource : null,
				gridLog = ds ? ds._accumulatedTransactionLog : null,
				i = gridLog ? gridLog.length : 0;
			if (!i) {
				return;
			}
			this._dsLog = this._dsLog || [];
			this._dsLog.push(ds);
			while (i-- > 0) {
				taLog.push($.extend(true, child ? { layoutKey: grid.options.key } : {}, gridLog[ i ]));
			}
		},
		saveChanges: function (success, error) {
			/* posts to the settings.updateUrl using $.ajax, by serializing the changes as url params
			paramType="function" Specifies a custom function to be called when AJAX request to the updateUrl option succeeds(optional)
			paramType="function" Specifies a custom function to be called when AJAX request to the updateUrl option fails(optional)
			*/
			var self = this,
				opts,
				elem = self.element,
				url = self.options.updateUrl,
				taLog = [];
			if ((this.options.rest || this.options.odata) && this.options.restSettings) {
				this._saveChangesForEachGrid();
				return;
			}
			if (!url || !elem) {
				return;
			}
			delete self._dsLog;
			self._addToLog(elem, taLog);
			elem.find(".ui-iggrid-table").each(function () {
				self._addToLog($(this), taLog, true);
			});
			/* M.H. 17 Feb 2014 Fix for bug #153639: Add successCallback and errorCallback params to the igDataSource.saveChanges API */
			opts = {
				type: "POST",
				url: url,
				data: { "ig_transactions": JSON.stringify(taLog) },
				success: function (data, textStatus, jqXHR) {
					var logs = self._dsLog,
						i = logs ? logs.length : 0;
					while (i-- > 0) {
						logs[ i ]._saveChangesSuccess(data, textStatus, jqXHR);
					}
					if (success) {
						success(data, textStatus, jqXHR);
					}
				},
				error: function (jqXHR, textStatus, errorThrown) {
					var logs = self._dsLog,
						i = logs ? logs.length : 0;
					while (i-- > 0) {
						logs[ i ]._saveChangesError(jqXHR, textStatus, errorThrown);
					}
					if (error) {
						error(jqXHR, textStatus, errorThrown);
					}
				}
			};
			/* post to the Url using $.ajax, by serializing the changes as url params */
			$.ajax(opts);
		},
		_saveChangesForEachGrid: function (success, error) {
			// will call each of the open layouts save changes instead of one full transaction log
			this.rootWidget().saveChanges(success, error);
			$.each(this.allChildrenWidgets(), function () {
				this.saveChanges(success, error);
			});
		},
		/* when there is virtualization enabled, we need to handle the expansion columns in a special way */
		_handleBatchExpandRender: function (sender, args) {
			var level = 0, grid = args.owner, keyspath, ds, hdsData, o, k, curpath, rowid, popts, pgridp,
				gridId = grid.element[ 0 ].id, tId = sender.currentTarget.id;
			if (gridId !== tId && gridId !== tId + "_table") {// && (!sender.target || sender.target.id !== gridId)
				return;
			}
			/* render first cells for every data rows
			implementation for initialExpandDepth. The prerequisite is that the initialDataBindDepth
			should always exceed the value of initialExpandDepth
			check level */
			level = parseInt(grid.element.attr("data-level"), 10);
			if (isNaN(level)) {
				level = 0;
				grid.element.attr("data-level", level);
			}
			/* M.H. 27 Mar 2013 Fix for bug #133320: When remote paging and remote filtering are enabled if you filter a column from a child layout and the result is from another page expanding this result makes the grid to throw a js error. */
			/* we should check for each databind and set the proper databind */
			if (level > 0 &&
					this.options.initialDataBindDepth === -1 &&
					this._hds.settings.type === "json" &&
					$.type(grid.options.dataSource) === "string") {
				keyspath = "";
				curpath = 0;
				rowid = "";
				popts = grid.options;
				if (popts.childrenDataProperty === undefined) {
					if (popts.key) {
						keyspath = popts.key;
					}
				} else {
					keyspath += popts.childrenDataProperty;
				}

				pgridp = grid.element;
				pgridp = pgridp.closest(".ui-iggrid-childarea").parent().prev();

				while (pgridp.length > 0) {
					if (curpath !== 0) {
						rowid += this.options.pathSeparator;
					}
					o = pgridp.closest(".ui-iggrid-table").data("igGrid").options;
					/*k = o.foreignKey;
					if (!k) { */
					k = o.primaryKey;
					/*} */
					rowid += k + ":" + pgridp.attr("data-id");
					if (o.childrenDataProperty === undefined) {
						if (o.key) {
							keyspath += this.options.pathSeparator + o.key;
						}
					} else {
						keyspath += this.options.pathSeparator + o.childrenDataProperty;
					}
					pgridp = pgridp.closest(".ui-iggrid-childarea").parent().prev();
					curpath++;
				}

				keyspath = keyspath.split(this.options.pathSeparator).reverse()
					.join(this.options.pathSeparator);
				rowid = rowid.split(this.options.pathSeparator).reverse().join(this.options.pathSeparator);
				hdsData = this._hds.dataAt(rowid, keyspath);
				ds = grid.dataSource;
				if (hdsData[ popts.responseDataKey ]) {
					hdsData[ popts.responseDataKey ] = ds._dataView;
					hdsData.Metadata = ds.metadata();
				}
			}
			if (level > this.options.initialExpandDepth) {
				return;
			}
			if ((this.options.initialDataBindDepth === -1 ||
					this.options.initialDataBindDepth >= this.options.initialExpandDepth) &&
					this.options.initialExpandDepth !== -1) {
				// non blocking UI
				setTimeout($.proxy(this._batchExpand, args.owner), 1);
				/* this._batchExpand(); */
			}
			/* unbind later (at destroy) */
			/* this.element.unbind('iggriddatarendered', this._renderedHandler); */
		},
		_inheritRestSettings: function (path, pid, layout, popts, copts) {
			var crest = copts.restSettings, prest = popts.restSettings, rs, ptmpl, fullpath;
			/* path, parent id, layout name, parent options, child options */
			if (!path) {
				// if we don't have path we need to get it from the parent restSettings and inherit from there
				if (prest.update.template || prest.remove.template) {
					// get the path from the template with higher priority
					path = prest.update.template || prest.remove.template;
					path = path.replace("${id}", pid);
				} else {
					// get the path from the urls
					path = prest.create.url || prest.update.url || prest.remove.url;
					if (path.substr(path.length - 1) !== "/") {
						path += "/";
					}
					path += pid;
				}
				/* e.g [/employees/5] or [/employees(5)] */
				if (layout.substr(0, 1) === "/") {
					layout = layout.substr(1);
				}
				if (path.substr(path.length - 1) !== "/") {
					path += "/";
				}
				/* e.g [/employees/5/] or [/employees(5)/] */
				fullpath = path + layout;
			} else {
				fullpath = path;
			}
			/* urls are easy - they should just equal the generated path + the layout */
			rs = {
				update: {
					url: fullpath
				},
				create: {
					url: fullpath
				},
				remove: {
					url: fullpath
				}
			};
			/* inheriting the templates is trickier */
			if (crest.create.template && prest.create.template) {
				ptmpl = prest.create.template.replace("${id}", pid);
				crest.create.template = ptmpl + "/" + crest.create.template;
			}
			if (crest.remove.template && prest.remove.template) {
				ptmpl = prest.remove.template.replace("${id}", pid);
				crest.remove.template = ptmpl + "/" + crest.remove.template;
			}
			if (crest.update.template && prest.update.template) {
				ptmpl = prest.update.template.replace("${id}", pid);
				crest.update.template = ptmpl + "/" + crest.update.template;
			}
			/* apply the generated settings */
			crest = $.extend(true, crest, rs);
		},
		/* continuous virtualization: gets called each time a row gets expanded to increase the scroller height */
		_rowExpanded: function (args) {
			var rowExpansion, rowHeight, parentGrid, currentHeight, rootGrid;

			rowExpansion = args.parentrow.next();
			if (rowExpansion.length === 0 || !rowExpansion.is("tr[data-container='true']")) {
				return;
			}
			rowHeight = rowExpansion.outerHeight();
			/* I.I. bug fix for 105446 */
			parentGrid = args.parentrow.closest(".ui-iggrid-table").data("igGrid");
			currentHeight = parentGrid._getScrollContainerHeight();
			parentGrid._setScrollContainerHeight(currentHeight + rowHeight);
			/* M.H. 16 Oct 2014 Fix for bug #182885: When continuous virtualization is enabled and all levels are expanded teh scrollbar cannot reach the bottom */
			rootGrid = this.element.closest(".ui-iggrid-root").data("igGrid");
			if (rootGrid.options.rowVirtualization &&
				!parentGrid.options.height &&
				rootGrid.options.height) {
				currentHeight = rootGrid._getScrollContainerHeight();
				rootGrid._setScrollContainerHeight(currentHeight + rowHeight);
			}
		},
		/* continuous virtualization: gets called each time a row gets collapsed to decrease the scroller height */
		_rowCollapsed: function (args) {
			var rowExpansion = args.parentrow.next(), rowHeight, parentGrid, currentHeight, rootGrid;

			if (rowExpansion.length === 0 || !rowExpansion.is("tr[data-container='true']")) {
				return;
			}
			/* rowHeight is already collaped, should be handled partly in the colapsing event */
			rowHeight = rowExpansion.outerHeight();
			/* I.I. bug fix for 105446 */
			parentGrid = args.parentrow.closest(".ui-iggrid-table").data("igGrid");
			currentHeight = parentGrid._getScrollContainerHeight();
			parentGrid._setScrollContainerHeight(currentHeight - rowHeight);
			/* M.H. 16 Oct 2014 Fix for bug #182885: When continuous virtualization is enabled and all levels are expanded teh scrollbar cannot reach the bottom */
			rootGrid = this.element.closest(".ui-iggrid-root").data("igGrid");
			if (rootGrid.options.rowVirtualization &&
				!parentGrid.options.height &&
				rootGrid.options.height) {
				currentHeight = rootGrid._getScrollContainerHeight();
				rootGrid._setScrollContainerHeight(currentHeight - rowHeight);
			}
		},
		/*continuous virtualization: overrides _getFirstVisibleTR of grid.gramework */
		/*returns the first TR in the grid which is partly or fully visible */
		_hierarchicalGetFirstVisibleTR: function (visibleArea) {
			var tableId = "#" + this.element[ 0 ].id, firstVisibleTR;

			firstVisibleTR = $(tableId + " > tbody > tr:not([data-container='true'])").filter(function () {
				return this.offsetTop + $(this).height() > visibleArea.top;
			}).first();

			return firstVisibleTR;
		},
		/*continuous virtualization: overrides _getLastVisibleTR of grid.gramework */
		/*returns the last TR in the grid which is partly or fully visible */
		_hierarchicalGetLastVisibleTR: function (visibleArea) {
			var tableId = "#" + this.element[ 0 ].id, lastVisibleTR;

			lastVisibleTR = $(tableId + " > tbody > tr:not([data-container='true'])").filter(function () {
				return this.offsetTop < visibleArea.bottom;
			}).last();
			return lastVisibleTR;
		},
		_clearVariablesAndEvents: function () {
			// remove internal variables and unbind events
			this.tmpDataSource = null;
			this._rootgrid = null;
			/* M.H. 23 Feb 2016 Fix for bug 214694: iggriddatarendered event is not unbound when destroying igHierarachicalGrid */
			this.element.unbind(".hierarchicalgrid");
		},
		destroy: function () {
			/* destroys the hierarchical grid by recursively destroying all child grids */
			this._clearVariablesAndEvents();
			this.rootWidget().destroy();
			$.Widget.prototype.destroy.call(this);
		}
	});
	$.extend($.ui.igHierarchicalGrid, { version: "16.1.20161.2145" });
}(jQuery));
