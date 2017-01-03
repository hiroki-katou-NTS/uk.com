/*!@license
* Infragistics.Web.ClientUI igGrid KnockoutJS extension 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
*	jquery-1.9.1.js
*	ig.util.js
*	ig.dataSource.js
*	ig.ui.grid.framework.js
*/

/*global jQuery, ko*/
(function ($) {
	ko.bindingHandlers.igGrid = {
		init: function (element, valueAccessor) {
			var grid = $(element), gridObj = grid.data("igGrid"), binding, options, key;
			if (grid.attr("data-create") !== "false") {
				binding = ko.utils.unwrapObservable(valueAccessor());
				/* instantiate the iggrid */
				options = {};
				options.dataSource = new $.ig.KnockoutDataSource({ dataSource: binding.dataSource });
				/* add the other options which are defined */
				for (key in binding) {
					if (binding.hasOwnProperty(key) && key !== "dataSource") {
						options[ key ] = binding[ key ];
					}
				}
			} else {
				binding = options = gridObj.options;
			}
			grid.igGridKnockoutBridge();
			if (grid.attr("data-create") !== "false") {
				grid.igGrid(options);
			} else {
				// just apply the initial bindings
				grid.igGridKnockoutBridge("rebindCells");
			}
			return { controlsDescendantBindings: true };
		},
		update: function (element, valueAccessor) {
			// we need to apply the bindings to the respective node. when this is called, it means some observable got updated
			$(element).igGridKnockoutBridge("recordsUpdated", valueAccessor);
		}
	};
	ko.visitPropertiesOrArrayEntries = function (rootObject, visitorCallback) {
		var i, propertyName;
		if (rootObject instanceof Array) {
			for (i = 0; i < rootObject.length; i++) {
				visitorCallback(i);
			}
		} else {
			for (propertyName in rootObject) {
				if (rootObject.hasOwnProperty(propertyName)) {
					visitorCallback(propertyName);
				}
			}
		}
	};
	ko.bindingHandlers.igHierarchicalGrid = {
		init: function (element, valueAccessor) {
			var grid = $(element), binding, options, ds, childgridhandler, key;
			/* we don"t want to "unrap" the observable at once. because this will unwrap the whole hierarchy */
			binding = ko.utils.unwrapObservable(valueAccessor());
			options = {};
			ds = ko.isObservable(binding.dataSource) ? binding.dataSource() : binding.dataSource;
			if (ds instanceof $.ig.DataSource) {
				ds = ds.data();
			}
			options.dataSource = new $.ig.KnockoutDataSource({ dataSource: binding.dataSource });
			for (key in binding) {
				if (binding.hasOwnProperty(key) && key !== "dataSource") {
					options[ key ] = binding[ key ];
				}
			}
			grid.igGridKnockoutBridge();
			grid.igHierarchicalGrid(options);
			grid.data("observableDataSource", options.dataSource);
			childgridhandler = function (event, args) {
				var kods, opts = args.options, observableDs;
				/* if we are unwrapping everything at once we still need to handle this event in order to find the ko observable
				create a KO data source here and find the path to the child KO observable */
				/* L.A. 24 January 2013 - Fixing bug #131448 */
				if (args.element.data("igGrid")) {
					return;
				}
				kods = args.owner.element.data("observableDataSource");
				/* find the child data, basically we need at knockoutjs-aware dataAt() implementation */
				observableDs = kods.dataAt(args.id, args.path);
				opts.dataSource = new $.ig.KnockoutDataSource({
					primaryKey: opts.primaryKey,
					dataSource: opts.dataSource,
					observableDataSource: observableDs,
					responseDataKey: opts.responseDataKey
				});
				args.element.igGridKnockoutBridge();
				args.element.igGrid(opts);
				opts = args.element.data("igGrid").options;
				args.element.attr("data-create", false);
				ko.applyBindingsToNode(args.element[ 0 ], { igGrid: {} }, observableDs);
			};
			/* bind to the childgridcreating event */
			grid.on("igchildgridcreating", "table", childgridhandler);
			/* attach the bindings */
			return { "controlsDescendantBindings": true };
		},
		update: ko.bindingHandlers.igGrid.update
	};
	ko.bindingHandlers.igCell = {
		update: function (element, valueAccessor, allBindingsAccessor, viewModel) {
			var cell = $(element), grid = cell.closest(".ui-iggrid-table");
			if (grid.attr("id").endsWith("_fixed")) {
				grid = grid.closest(".ui-iggrid").find("[data-bind]:first");
			}
			grid.igGridKnockoutBridge("cellUpdated",
				cell,
				typeof valueAccessor === "function" ? valueAccessor() : valueAccessor,
				viewModel !== null && ko.isObservable(viewModel.$data) ? viewModel.$data() : viewModel
			);
		}
	};

	$.widget("ui.igGridKnockoutBridge", {
		options: {
			parent: null
		},
		_create: function () {
			if (!this.options.parent) {
				this.options.parent = this.element.data("igGrid");
			}
			this._createHandlers();
			this._bridgeGrid(this.element);
		},
		destroy: function () {
			/* destroys the igGridKnockoutBridge widget */
			var handler = this._rebindCellsHandler;
			this.options.parent.renderNewRow = this._renderNewRowDefault;
			if (this._grb) {
				this._grb._renderNewRow = this._renderNewRowGroupBy;
			}
			this.element.unbind({
				iggridrendering: this._gridRenderingHandler,
				iggridrendered: this._gridRenderedHandler,
				iggriddatarendered: handler,
				iggridvirtualrecordsrender: handler,
				iggridcolumnscollectionmodified: handler
				/* iggriduisoftdirty: handler,
				iggridfilteringdatafiltered: handler,
				iggridpagingpageindexchanged: handler */
			});
			$.Widget.prototype.destroy.apply(this, arguments);
			return this;
		},
		/* render new record override */
		renderNewRow: function (rec, key) {
			this._renderNewRowDefault.apply(this.options.parent, [ rec, key ]);
			this._rebindCells(key);
		},
		_renderNewRowGroupByKo: function (rec, key) {
			this._renderNewRowGroupBy.apply(this._grb, [ rec, key ]);
			this._rebindCells(key);
		},
		rebindCells: function (rowId) {
			this._rebindCells(rowId);
		},
		/* event handlers */
		_gridRendering: function (evt, ui) {
			if (this.options.parent && this.options.parent.id() !== ui.owner.id()) {
				return;
			}
			this.options.parent = ui.owner;
			if (this.renderNewRow !== ui.owner.renderNewRow) {
				this._renderNewRowDefault = ui.owner.renderNewRow;
				ui.owner.renderNewRow = $.proxy(this.renderNewRow, this);
			}
		},
		_gridRendered: function (evt, ui) {
			// groupby has its own renderNewRow which is called by Updating and we need to cover that scenario too
			var grb = $(ui.owner.element).data("igGridGroupBy") ||
				$("#" + ui.owner.id()).data("igGridGroupBy");
			if (grb && this._renderNewRowGroupByKo !== grb._renderNewRow) {
				this._renderNewRowGroupBy = grb._renderNewRow;
				grb._renderNewRow = $.proxy(this._renderNewRowGroupByKo, this);
				this._grb = grb;
			}
		},
		_rebindCells: function (rowId) {
			var ds,
			/* L.A. 06 November 2012 - Fixing bug #110571
			Grid displaying wrong page data when updated from Knockout */
			/* L.A. 01 February 2013 - Fixing bug #131807 - Knockout - js error when using hierarchical grid and paging */
				owner = this.options.parent,
				gridElement = this.options.parent.element,
				pageIndex = gridElement.data("igGridPaging") ? gridElement.data("igGridPaging").pageIndex() : 0,
				pageSize = gridElement.data("igGridPaging") ? gridElement.data("igGridPaging").pageSize() : 0,
				startRowIndex = (pageIndex * pageSize),
				opts = owner.options,
				dataIdSelector = $.type(rowId) === "number" || $.type(rowId) === "string" ?
					"[data-id='" + rowId + "']" : "",
				bindingFunction;
			if (ko.isObservable(owner.dataSource.kods)) {
				ds = owner.dataSource.kods();
			} else if (ko.isObservable(owner.dataSource.kods[ owner.options.responseDataKey ])) {
				ds = owner.dataSource.kods[ owner.options.responseDataKey ]();
			} else {
				ds = owner.dataSource.dataSource();
			}
			/* attach the bindings
			grid.attr("data-bound", grid ? false : true); */
			if (!opts.rowVirtualization && !opts.virtualization) {
				// L.A. 06 November 2012 - Fixing bug #110571
				// Grid displaying wrong page data when updated from Knockout
				owner._startRowIndex = startRowIndex;
			}

			if (gridElement.hasClass("ui-iggrid-responsive-vertical")) {
				bindingFunction = this._bindingFunctionResponsiveVerticalColumns(ds, owner, opts);
			} else {
				bindingFunction = this._bindingFunctionDefault(ds, owner, opts);
			}

			owner.element.children("tbody")
				.children("tr[data-grouprow!=true][data-container!=true]" + dataIdSelector)
				.filter(function () {
					return this.style.visibility !== "hidden";
				})
				.map(bindingFunction);
		},
		_bindingFunctionDefault: function (ds, owner, opts) {
			return function (i) {
				var trId = $(this).attr("data-id"),
					id, k, l, fl, cells, fcells, dataVal,
					rec, cell;
				for (i = 0; i < ds.length; i++) {
					id = ds[ i ][ owner.options.primaryKey ];
					if (ko.isObservable(id)) {
						id = id();
					}
					if (String(id) === String(trId)) {
						rec = ds[ i ];
						break;
					}
				}
				if (rec) {
					cells = $(this)
						.children("td[data-parent!=true][data-parent!=false][data-skip!=true]");
					fcells = $(this)
						.closest(".ui-iggrid")
						.find(".ui-iggrid-fixedcontainer tbody>tr[data-id='" + trId + "']")
						.children("td[data-parent!=true][data-parent!=false][data-skip!=true]");
					l = 0;
					fl = 0;
					for (k = 0; k < opts.columns.length; k++) {
						if (opts.columns[ k ].hidden) {
							continue;
						}
						if (opts.columns[ k ].unbound) {
							l++;
							continue;
						}
						dataVal = rec[ opts.columns[ k ].key ];
						if (opts.columns[ k ].fixed) {
							cell = fcells[ fl++ ];
						} else {
							cell = cells[ l++ ];
						}
						if (ko.isObservable(dataVal)) {
							/* with KO 3.0 we need to apply the bindgins differently */
							if (ko.hasOwnProperty("applyBindingAccessorsToNode")) {
								ko.applyBindingAccessorsToNode(cell, {
									igCell: { value: opts.columns[ k ] }
								}, { $data: dataVal });
							} else {
								ko.applyBindingsToNode(cell, {
									igCell: { value: opts.columns[ k ] }
								}, dataVal);
							}
						}
					}
				} else {
					$(this).remove();
				}
			};
		},
		_bindingFunctionResponsiveVerticalColumns: function (ds, owner, opts) {
			return function (i) {
				var trId = $(this).attr("data-id"),
					id, k, cells, dataVal,
					rec, cell, colkey;
				for (i = 0; i < ds.length; i++) {
					id = ds[ i ][ owner.options.primaryKey ];
					if (ko.isObservable(id)) {
						id = id();
					}
					if (String(id) === String(trId)) {
						rec = ds[ i ];
						break;
					}
				}
				if (rec) {
					cells = $(this)
						.children("td[data-parent!=true][data-parent!=false][data-skip!=true]");
					for (k = 0; k < opts.columns.length; k++) {
						colkey = $(this).data("col-key");
						if (opts.columns[ k ].key !== colkey) {
							continue;
						}
						dataVal = rec[ colkey ];
						if (ko.isObservable(dataVal)) {
							cell = cells[ 1 ];
							/* with KO 3.0 we need to apply the bindgins differently */
							if (ko.hasOwnProperty("applyBindingAccessorsToNode")) {
								ko.applyBindingAccessorsToNode(cell, {
									igCell: { value: opts.columns[ k ] }
								}, { $data: dataVal });
							} else {
								ko.applyBindingsToNode(cell, {
									igCell: { value: opts.columns[ k ] }
								}, dataVal);
							}
							break;
						}
					}
				} else {
					$(this).remove();
				}
			};
		},
		/* updates from KO handlers */
		recordsUpdated: function (valueAccessor) {
			var grid = this.options.parent, newds, oldds,
			updating = $(this.options.parent.element).data("igGridUpdating") ||
			$("#" + grid.id()).data("igGridUpdating"),
			i, key, changes, c, autoCommit = grid.options.autoCommit;
			newds = valueAccessor().dataSource;
			if (!newds) {
				if (grid.options.responseDataKey) {
					newds = grid.dataSource.kods[ grid.options.responseDataKey ];
				} else {
					newds = grid.dataSource.kods;
				}
			} else {
				grid.dataSource.kods = newds;
			}
			/* oldds will be the previous knockout observable state (before the last update) */
			oldds = grid.dataSource._knockoutState;
			/* we store the new knockout observable state to be used in future updates */
			grid.dataSource._knockoutState = $.extend([], newds());
			if (!oldds) {
				return;
			}
			if (updating === null || updating === undefined) {
				throw new Error("two-way adding and deleting of rows with " +
					"KnockoutJS requires the Updating feature to be defined");
			}
			if (typeof newds === "function" && !grid.dataSource._ownUpdate) {
				// we only try to modify the grid's dataSource if the update is not coming from grid's own widgets
				// compareArrays will give us the differences between the previous and current states of the observable on per record basis
				changes = ko.utils.compareArrays(oldds, newds());
				for (i = 0; i < changes.length; i++) {
					c = changes[ i ];
					/* the interesting statuses for us are "deleted" and "added" */
					if (c.status === "deleted") {
						grid.dataSource._koUpdate = true;
						key = c.value[ grid.options.primaryKey ];
						key = ko.isObservable(key) ? key() : key;
						/* updates coming from the observable should always be considered "comitted" */
						grid.options.autoCommit = true;
						updating.deleteRow(key);
						grid.options.autoCommit = autoCommit;
					} else if (c.status === "added") {
						key = c.value[ grid.options.primaryKey ];
						key = ko.isObservable(key) ? key() : key;
						grid.dataSource._koUpdate = true;
						/* updates coming from the observable should always be considered "comitted" */
						grid.options.autoCommit = true;
						updating.addRow(ko.toJS(c.value));
						grid.options.autoCommit = autoCommit;
					}
				}
			}
			grid.dataSource._ownUpdate = false;
		},
		cellUpdated: function (cell, valueAccessor, viewModel) {
			var grid = this.options.parent,
				key, tmpldata, newFormattedVal, tr, rec, keyVal, col, editor, curCol,
				origDs, origRec;
			key = valueAccessor.value.key;
			/*first we'll update the grid's dataSource and obtain the record
			get the closest row */
			tr = cell.closest("tr");
			col = grid.columnByKey(grid.options.primaryKey);
			if (tr.attr("data-id") !== null && tr.attr("data-id") !== undefined) {
				// we have a primary key defined
				if (col.dataType === "number" || col.dataType === undefined) {
					keyVal = parseInt(tr.attr("data-id"), 10);
					/* L.A. 08 February 2013 - 132519 - Knockout - repeated external update is causing js errors */
					if (key === grid.options.primaryKey) {
						viewModel = parseInt(viewModel, 10);
					}
				} else {
					keyVal = tr.attr("data-id"); // we are dealing with string primary keys
				}
				origDs = grid.dataSource.settings.localSchemaTransform ? grid.dataSource._origDs : null;
				rec = grid.dataSource.findRecordByKey(keyVal);
				if (origDs && origDs !== grid.dataSource._data) {
					origRec = grid.dataSource.findRecordByKey(keyVal, origDs);
				}
				/* L.A. 28 January 2013 - Fixing bug #131555 KO - TypeError: can"t convert null to object */
				if (rec === null) {
					rec = grid.dataSource.findRecordByKey(viewModel);
					if (origDs && origDs !== grid.dataSource._data) {
						origRec = grid.dataSource.findRecordByKey(keyVal, origDs);
					}
				}
				/* L.A. 08 February 2013 - 132519 - Knockout - repeated external update is causing js errors */
				if (key === grid.options.primaryKey) {
					tr.attr("data-id", viewModel);
					tr.data().id = viewModel;
				}
				rec[ key ] = viewModel;
				if (origRec) {
					origRec[ key ] = viewModel;
				}
			} else {
				throw new Error("Updating the data source requires a primary key to be defined");
			}
			/* we can now use the record to pass for templating/formatting that relies on other props */
			curCol = grid.columnByKey(key);
			if (curCol && curCol.template && curCol.template.length > 0) {
				tmpldata = origRec || {};
				if (!tmpldata.hasOwnProperty(key)) {
					tmpldata[ key ] = viewModel;
				}
				newFormattedVal = grid._renderTemplatedCell(tmpldata, valueAccessor.value);
				if (newFormattedVal && newFormattedVal.startsWith("<td")) {
					newFormattedVal = $(newFormattedVal).html();
				}
			} else {
				newFormattedVal = grid._renderCell(viewModel, valueAccessor.value, rec);
			}
			/* detach editors so they are not destoyed when cell's html is replaced */
			editor = cell.children(".ui-iggrid-editor").detach();
			/* updatecell */
			cell.html(newFormattedVal);
			/* prepend to cell afterwards */
			if (editor.length > 0) {
				cell.prepend(editor);
			}
			grid.dataSource._ownUpdate = false;
		},
		/* private */
		_bridgeGrid: function (grid) {
			/* binds and/or rewires grid functions to support updates */
			var handler = this._rebindCellsHandler;
			grid.bind({
				iggridrendering: this._gridRenderingHandler,
				iggridrendered: this._gridRenderedHandler,
				iggriddatarendered: handler,
				iggridvirtualrecordsrender: handler,
				iggridcolumnscollectionmodified: handler,
				iggridcolumnfixingcolumnfixed: handler
				/*iggriduisoftdirty: handler,
				iggridfilteringdatafiltered: handler,
				iggridpagingpageindexchanged: handler */
			});
		},
		_createHandlers: function () {
			this._rebindCellsHandler = $.proxy(this._rebindCells, this);
			this._gridRenderingHandler = $.proxy(this._gridRendering, this);
			this._gridRenderedHandler = $.proxy(this._gridRendered, this);
		}
	});
}(jQuery));
