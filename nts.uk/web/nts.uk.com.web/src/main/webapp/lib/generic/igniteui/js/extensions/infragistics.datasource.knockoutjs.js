/*!@license
* Infragistics.Web.ClientUI igDataSource KnockoutJS extension 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
*	jquery-1.9.1.js
*	ig.util.js
*	ig.dataSource.js
*/

// works locally only.
// the Knockout data source should be able to
// what about mapping from columns to schema ?
/*global ko, jQuery*/
(function ($) {

	$.ig.KnockoutDataSource = $.ig.KnockoutDataSource || $.ig.DataSource.extend({
		init: function (options) {
			// subscribe to notifications, we always assume the DataSource is an observableArray.
			// think of ways to avoid serialization/deserialization of the data & any duplicate data
			if (options.observableDataSource === null || options.observableDataSource === undefined) {
				//if (ko.isObservable(options.dataSource)) {
				//	this.kods = options.dataSource();
				//} else {
				this.kods = options.dataSource;
				options.dataSource = ko.toJS(options.dataSource);
			} else {
				this.kods = options.observableDataSource;
				/* Note : this means additional processing !!!
				the problem here is to determine if any changes have been done to
				the child data source after the hierarchical grid has been already instantiated */
				options.dataSource = ko.toJS(options.observableDataSource);
			}
			this._super(options);
			return this;
		},
		/* overwrite setting the cell value (and committing the transaction , respectively */
		setCellValue: function (rowId, colId, val, autoCommit) {
			//if (autoCommit) {
			//	this._setCellValue(rowId, colId, val);
			//}
			return this._super(rowId, colId, val, autoCommit);
		},
		_setCellValue: function (rowId, colId, val) {
			var cellData, pk = this.settings.primaryKey, ds = this.kods, i;
			/* commit to the KO observable
			check if we have a primary key defined
			note that if autoCommit is false, changes will be updated to the KO data source only when the
			grid's data source commit its changes */
			ds = ko.isObservable(ds) ? ds() : ds;
			if (this.settings.responseDataKey) {
				if (ko.isObservable(ds[ this.settings.responseDataKey ])) {
					ds = ds[ this.settings.responseDataKey ]();
				} else if (ds[ this.settings.responseDataKey ]) {
					ds = ds[ this.settings.responseDataKey ];
				}
			}
			if (pk) {
				// we need to find it (that is  - map the primary key value to the row index
				for (i = 0; i < ds.length; i++) {
					cellData = ko.isObservable(ds[ i ][ pk ]) ? ds[ i ][ pk ]() : ds[ i ][ pk ];
					if (String(cellData) === String(rowId)) {
						if (ko.isObservable(ds[ i ][ colId ])) {
							ds[ i ][ colId ](val);
						} else {
							ds[ i ][ colId ] = val;
						}
						break;
					}
				}
			} else {
				ds[ rowId ][ colId ](val);
			}
		},
		deleteRow: function (rowId, autoCommit) {
			//if (autoCommit) {
			//	this._deleteRow(rowId);
			//}
			if (autoCommit) {
				this._ownUpdate = !this._ownUpdate;
			}
			return this._super(rowId, autoCommit);
		},
		_deleteRow: function (rowId) {
			// delete from the KO observable
			var pk = this.settings.primaryKey, ds = this.kods;
			if (this.settings.responseDataKey && ds[ this.settings.responseDataKey ]) {
				ds = ds[ this.settings.responseDataKey ];
			}
			/* L.A. 28 January 2013 - Fixing bug #131746
			Removing items directly form data source doesn't work when igGrid is bound to knockout data source */
			if (ko.isObservable(ds)) {
				// we only need to do this if the knockout datasource is an observable array
				if (!this._koUpdate) {
					ds.remove(function (item) {
						return String((ko.isObservable(item[ pk ]) ? item[ pk ]() : item[ pk ])) === String(rowId);
					});
				} else {
					this._koUpdate = false;
				}
			} else {
				this._super(rowId);
			}
		},
		addRow: function (rowId, rowObject, autoCommit) {
			// add to the KO observable array
			//if (autoCommit) {
			//	this._addRow(rowId, rowObject);
			//}
			if (autoCommit) {
				this._ownUpdate = !this._ownUpdate;
			}
			return this._super(rowId, rowObject, autoCommit);
		},
		_addRow: function (rowObject, rowId, origDs) {
			var ds = this.kods, prop, koObject = jQuery.extend({}, rowObject);
			if (this.settings.responseDataKey && ds[ this.settings.responseDataKey ]) {
				ds = ds[ this.settings.responseDataKey ];
			}
			/* L.A. 28 January 2013 - Fixing bug #131746
			Removing items directly form data source doesn't work when igGrid is bound to knockout data source */
			if (ko.isObservable(ds)) {
				// we only need to do this if the knockout datasource is an observable array
				if (!this._koUpdate) {
					// when adding the new row we need to know which columns are observable
					// TODO
					// L.A. 28 January 2013 - Fixing bug #131549 AddRow is not working with KO
					// L.A. 11 February 2013 - Fixing bug #132644 Knockout - addrow issues with multiple grids
					for (prop in koObject) {
						if (koObject.hasOwnProperty(prop) && !ko.isObservable(prop)) {
							koObject[ prop ] = ko.observable(koObject[ prop ]);
						}
					}
					this._super(rowObject, rowId, origDs);
					ds.push(koObject);
				} else {
					this._koUpdate = false;
					this._super(rowObject, rowId, origDs);
				}
			} else {
				ds.push(rowObject);
				this._super(rowObject, rowId, origDs);
			}
		},
		updateRow: function (rowId, rowObject, autoCommit) {
			//if (autoCommit) {
			//	this._updateRow(rowId, rowObject);
			//}
			if (autoCommit) {
				this._ownUpdate = !this._ownUpdate;
			}
			return this._super(rowId, rowObject, autoCommit);
		},
		_updateRow: function (rowId, rowObject) {
			var cellData, pk = this.settings.primaryKey, ds = this.kods, prop, i;
			if (this.settings.responseDataKey && ds[ this.settings.responseDataKey ]) {
				ds = ds[ this.settings.responseDataKey ];
			}
			ds = ko.isObservable(ds) ? ds() : ds;
			if (pk) {
				// we need to find it (that is  - map the primary key value to the row index
				for (i = 0; i < ds.length; i++) {
					cellData = ko.isObservable(ds[ i ][ pk ]) ? ds[ i ][ pk ]() : ds[ i ][ pk ];
					/* L.A. 08 February 2013 - Fixing bug #132521 - Knockout - internal after external updates fails */
					if (String(cellData) === String(rowId)) {
						for (prop in rowObject) {
							if (rowObject.hasOwnProperty(prop)) {
								if (ko.isObservable(ds[ i ][ prop ])) {
									ds[ i ][ prop ](rowObject[ prop ]);
								} else {
									ds[ i ][ prop ] = rowObject[ prop ];
								}
							}
						}
						break;
					}
				}
			} else {
				for (prop in rowObject) {
					if (rowObject.hasOwnProperty(prop)) {
						if (ko.isObservable(ds[ rowId ][ prop ])) {
							ds[ rowId ][ prop ](rowObject[ prop ]);
						} else {
							ds[ rowId ][ prop ] = rowObject[ prop ];
						}
					}
				}
			}
		},
		_commitTransaction: function (t) {
			// commit, then remove from the transaction log
			var i, prop, rec, origRec, origDs = this.settings.localSchemaTransform ? this._origDs : null;
			if (origDs === this._data) {
				origDs = null;
			}
			this._ownUpdate = true;
			if (this.settings.primaryKey === null) {
				rec = this._data[ parseInt(t.rowId, 10) ];
				if (origDs) {
					origRec = origDs[ parseInt(t.rowId, 10) ];
				}
			} else {
				rec = this.findRecordByKey(t.rowId);
				if (origDs) {
					origRec = this.findRecordByKey(t.rowId, origDs);
				}
			}
			if (t.type === "cell") {
				rec[ t.col ] = t.value;
				if (origRec) {
					origRec[ t.col ] = t.value;
				}
				/* also update the knockout data source */
				this._setCellValue(t.rowId, t.col, t.value);
			} else if (t.type === "row") {
				if ($.type(t.row) === "array") {
					for (i = 0; i < t.row.length; i++) {
						rec[ i ] = t.row[ i ];
						if (origRec) {
							origRec[ i ] = t.row[ i ];
						}
					}
				} else if (rec) {
					for (prop in t.row) {
						if (t.row.hasOwnProperty(prop)) {
							rec[ prop ] = t.row[ prop ];
							if (origRec) {
								origRec[ prop ] = t.row[ prop ];
							}
						}
					}
					/* also update the row */
					this._updateRow(t.rowId, t.row);
				}
			} else if (t.type === "deleterow") {
				if (this.settings.primaryKey === null) {
					this.removeRecordByIndex(parseInt(t.rowId, 10), origDs);
				} else {
					this.removeRecordByKey(t.rowId, origDs);
				}
				/* also update the KO data source */
				this._deleteRow(t.rowId);
			} else if (t.type === "newrow") {
				//this._addRow(t.row, -1);
				// also update the KO data source
				this._addRow(t.row, t.rowId, origDs);
			} else if (t.type === "insertrow") {
				this._addRow(t.row, t.rowIndex, origDs);
			}
			/* finally remove from the log, since the transaction is already committed and shouldn't be pending */
			this._removeTransactionByTransactionId(t.tid);
		},
		dataAt: function (path, keyspath) {
			// search the data. the data source must be indexed to speed up this access.
			var data = this.kods, paths = path.split("/"),
				kp = keyspath.split("/"), k, i, tmp, j, cd = null,
				ckey = this.settings.primaryKey, ckeyval = "",
				ckeys = [], ckeyvals = [], match = false;
			data = ko.isObservable(data) ? data() : data;
			for (i = 0; i < paths.length; i++) {
				ckey = paths[ i ].split(":")[ 0 ];
				ckeyval = paths[ i ].split(":")[ 1 ];
				/* NOTE: composite keys are keys whose values are separated by a comma. if ckey or ckeyval contain commas,
				then the key is assumed to be composite and will be split based on "," */
				if (paths[ i ] !== "") {
					for (j = 0; data && j < data.length; j++) {
						if (ckey && ckey.indexOf(",") !== -1) {
							ckeys = ckey.split(",");
							ckeyvals = ckeyval.split(",");
							for (k = 0; k < ckeys.length; k++) {
								tmp = data[ j ][ ckeys[ k ] ];
								tmp = ko.isObservable(tmp) ? tmp() : tmp;
								if (!tmp.charAt && ckeyvals[ k ].charAt) {
									ckeyvals[ k ] = parseInt(ckeyvals[ k ], 10);
								}
								match = (tmp === ckeyvals[ k ]);
								if (!match) {
									break;
								}
							}
						} else {
							tmp = data[ j ][ ckey ];
							tmp = ko.isObservable(tmp) ? tmp() : tmp;
							if (tmp !== undefined && !tmp.charAt && ckeyval.charAt) {
								ckeyval = parseInt(ckeyval, 10);
							}
							match = (tmp === ckeyval);
						}
						/* special case when we have responseDataKey / search fields defined for every children data instance */
						if (match) {
							// get the children
							cd = data[ j ][ kp[ i ] ];
							if (paths.length > 1 && i < paths.length - 1 &&
								this.settings.responseDataKey &&
								$.type(cd) !== "array" &&
								cd[ this.settings.responseDataKey ]) {
								/* L.A. 24 January 2013 - Fixing bug #131448 */
								if (ko.isObservable(cd[ this.settings.responseDataKey ])) {
									cd = cd[ this.settings.responseDataKey ]();
								} else {
									cd = cd[ this.settings.responseDataKey ];
								}
							}
							break;
						}
					}
					data = ko.isObservable(cd) ? cd() : cd;
				}
			}
			return cd;
		}
	});
}(jQuery));
