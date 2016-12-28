/*!@license
	* Infragistics.Web.ClientUI igDataChart KnockoutJS extension 16.1.20161.2145
	*
	* Copyright (c) 2011-2016 Infragistics Inc.
	*
	* http://www.infragistics.com/
	*
	* Depends on:
	*  jquery-1.9.1.js
	*	jquery.ui.core.js
	*	jquery.ui.widget.js
	*	infragistics.util.js
	*	infragistics.ui.chart.js
	*	infragistics.dv.core.js
	*	infragistics.chart_piechart.js
	*	infragistics.chart_categorychart.js
	*	infragistics.chart_financialchart.js
	*	infragistics.chart_polarchart.js
	*	infragistics.chart_radialchart.js
	*	infragistics.chart_rangecategorychart.js
	*	infragistics.chart_scatterchart.js
	*	infragistics.dvcommonwidget.js
	*	infragistics.dataSource.js
	*	infragistics.util.js
	*/

/*global ko, jQuery*/
(function ($) {
	ko.bindingHandlers.igDataChart = {
		init: function (element, valueAccessor) {
		    var options = $.extend(true, {}, ko.utils.unwrapObservable(valueAccessor())), ds,
                i, addHandler, deleteHandler, rootItem, current;
			addHandler = function(data, index) {
				var ds = options.dataSource, rootElem, current;
				rootElem = $(element).find("#rootElem");
				current = $("<li><ul></ul></li>");

				//find where to append the child
				if (index === 0) {
					rootElem.prepend(current);
				} else if (index === ds.length - 1) {
					rootElem.append(current);
				} else {

					//Find the n-th li and append the current before it
					$($(rootElem).children()[ index ]).before(current);
				}

                //#176416 T.P. 23th July 2014. When observable array with non-observable items is used for ds, the chart doesn't get notified about item adding.
				$(element).igDataChart("notifyInsertItem", options.dataSource, index, data);
				ko.applyBindingsToNode(current[ 0 ], { igDataChartRecord: { value: ds[ index ],
                    chartInstance: $(element), dataSourceInstance: ds } }, ds[ i ]);
			};
			deleteHandler = function (data, index) {
				var ds = options.dataSource, rootElem;
				rootElem = $(element).find("#rootElem");
				if (index === 0) {
					$(rootElem).children().first().remove();
				} else if (index === ds.length) {
					$(rootElem).children().last().remove();
				} else {

					//Find the n-th li and remove it
					$($(rootElem).children()[ index ]).remove();
				}
				$(element).igDataChart("notifyRemoveItem", options.dataSource, index, data);
			};
			if (ko.isObservable(options.dataSource)) {
				ds = options.dataSource();
				options.dataSource.subscribeArrayChanged(addHandler, deleteHandler);
				options.dataSource = ko.utils.unwrapObservable(options.dataSource);
			} else {
				ds = options.dataSource;
			}

			//The expectFunctions option should be set to true so if a data member is observable the igDataChart can extract it's value.
			if (options.expectFunctions !== true) {
				options.expectFunctions = true;
			}
			$(element).igDataChart(options);
			rootItem = $("<ul id='rootElem'></ul>");
			$("<div style='display: none !important'></div>").appendTo(element).append(rootItem);

			//Initialize the list of used dataMembers, so only these memeber will include custom handler.
			for (i = 0; i < ds.length ; i++ ) {
				current = $("<li><ul></ul></li>");
				rootItem.append(current);
				ko.applyBindingsToNode(current[ 0 ], { igDataChartRecord: { value: ds[ i ],
                    chartInstance: $(element), dataSourceInstance: ds } }, ds[ i ]);
			}

			//A callback to the node that releases the view model when the node is removed using ko.removeNode
			ko.utils.domNodeDisposal.addDisposeCallback(element, function () {
				$(element).igDataChart("destroy");
			});
		}
	};
	ko.bindingHandlers.igDataChartRecord = {
		init: function (element, valueAccessor) {
			var options = valueAccessor(), key, currItem,
			record = options.value;
			for (key in record) {
				if (record.hasOwnProperty(key) && ko.isObservable(record[ key ])) {
					currItem = $("<li class='property'></li>");
					$(element).find("ul").append(currItem);
					ko.applyBindingsToNode(currItem[ 0 ], {
					igDataChartItem: {
						value: record[ key ],
						chartInstance: valueAccessor().chartInstance,
						dataSourceInstance: valueAccessor().dataSourceInstance } },
					record[ key ]);
				}
			}
		}
	};
	ko.bindingHandlers.igDataChartItem = {
		update: function (element, valueAccessor) {
		    var chart, ds, item, index;

			//Get the index on which the change has appeard
		    index = $(element).parent().parent().index();

			ds = valueAccessor().dataSourceInstance;

			//Get the html element on which the igDataChart was initialized
			chart = valueAccessor().chartInstance;

			//Extract the updated item
			item = ds[ index ];

			//Notify the chart for a change appeared into an observable field.
			$(chart).igDataChart("notifySetItem", ds, index, item, item);
		}
	};

	//This helper function exposes functionality to listen for a changes into an observable array - adding and deleting. It accepts two arguments which serve as add/delete handler functions. Currently both handlers receive as arguments - the changed item(added/deleted) and the index on which the change has appeared. Due to need of performance the array is cached only on the initialization and we are just keeping it up to date according to the changes. Keep in mind this function can’t be used for a subscription to the changes of the item properties, it's only for a changes of the array itself.
	ko.observableArray.fn.subscribeArrayChanged = function(addCallback, deleteCallback) {
		var previousValue = null;
		this.subscribe(function (_previousValue) {

			//This check guarantees the previousValue is copied only on the initialization and we keep it up to date into the afterChange substription
		    if (previousValue === null) {

				//Cache the array so we have the previous value we can compare against into the subscribe callback
				previousValue = _previousValue.slice(0);
			}
		}, undefined, "beforeChange");
		this.subscribe(function(latestValue) {
			var delta = ko.utils.compareArrays(previousValue, latestValue, true), i;
			for (i = 0; i < delta.length; i++) {
				switch (delta[ i ].status) {
					case "retained":
						break;
					case "deleted":
						if (deleteCallback) {
						    deleteCallback(delta[ i ].value, delta[ i ].index);

							//Keep the previouValue (cached array) updated. Removes the item at the specific index
							previousValue.splice(delta[ i ].index, 1);
						}
						break;
					case "added":
						if (addCallback) {
						    addCallback(latestValue[ i ], delta[ i ].index);

							//Keep the previouValue (cached array) updated. Adds the item at the specific index
							previousValue.splice(delta[ i ].index, 0, latestValue[ i ]);
						}
						break;
				}
			}
		});
	};
}(jQuery));
