var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm016;
                (function (qmm016) {
                    var a;
                    (function (a) {
                        var history;
                        (function (history_1) {
                            /**
                             * For two demension view.
                             */
                            var ThreeDemensionViewModel = (function (_super) {
                                __extends(ThreeDemensionViewModel, _super);
                                function ThreeDemensionViewModel(history) {
                                    _super.call(this, 'history/threedemension.xhtml', history);
                                    var self = this;
                                    self.igGridDataSource = ko.observableArray([]);
                                    self.element3Name = ko.observable('');
                                    self.element3Items = ko.observableArray([]);
                                    self.selectedElement3ItemId = ko.observable(undefined);
                                    self.datasourceMap = [];
                                    // On change.
                                    self.selectedElement3ItemId.subscribe(function (id) {
                                        if (id && self.datasourceMap[id]) {
                                            self.initIgGrid(self.datasourceMap[id]);
                                        }
                                    });
                                }
                                /**
                                 * On load init ig grid.
                                 */
                                ThreeDemensionViewModel.prototype.onLoad = function () {
                                    var self = this;
                                    // Build first data source.
                                    var history = self.history;
                                    if (history.valueItems && history.valueItems.length > 0) {
                                        var element1 = history.elements[0];
                                        var element2 = history.elements[1];
                                        var element3 = history.elements[2];
                                        element3.itemList.forEach(function (item3) {
                                            var itemVmList = _.map(element1.itemList, function (item) {
                                                var vm = new ItemViewModel(element1.type, item);
                                                var valueItemMap = [];
                                                _.filter(history.valueItems, function (vi) { return vi.element1Id == item.uuid && vi.element3Id == item3.uuid; })
                                                    .forEach(function (vi) { valueItemMap[vi.element2Id] = vi; });
                                                element2.itemList.forEach(function (item2) {
                                                    vm[item2.uuid] = ko.observable(valueItemMap[item2.uuid].amount);
                                                });
                                                return vm;
                                            });
                                            self.datasourceMap[item3.uuid] = itemVmList;
                                        });
                                        // Build element 3 information.
                                        self.buildElement3Infomation();
                                    }
                                    // Ret.
                                    return $.Deferred().resolve().promise();
                                };
                                /**
                                 * On refresh element.
                                 */
                                ThreeDemensionViewModel.prototype.onRefreshElement = function () {
                                    var self = this;
                                    // Build elements 3 data source.
                                    self.datasourceMap = [];
                                    self.elementSettings[2].itemList.forEach(function (item3) {
                                        // Update data source.
                                        var firstEl = self.elementSettings[0];
                                        var secondEl = self.elementSettings[1];
                                        var dataSource = [];
                                        firstEl.itemList.forEach(function (firstItem) {
                                            var vm = new ItemViewModel(firstEl.type, firstItem);
                                            secondEl.itemList.forEach(function (secondItem) {
                                                vm[secondItem.uuid] = ko.observable(0);
                                            });
                                            dataSource.push(vm);
                                        });
                                        self.datasourceMap[item3.uuid] = dataSource;
                                    });
                                    // Build elements 3 item info.
                                    self.buildElement3Infomation();
                                };
                                /**
                                 * Get setting cell item.
                                 */
                                ThreeDemensionViewModel.prototype.getCellItem = function () {
                                    var self = this;
                                    var firstEl = self.elementSettings[0];
                                    var secondEl = self.elementSettings[1];
                                    var thirdEl = self.elementSettings[2];
                                    var result = new Array();
                                    thirdEl.itemList.forEach(function (item3) {
                                        if (self.datasourceMap[item3.uuid]) {
                                            self.datasourceMap[item3.uuid].forEach(function (vm) {
                                                secondEl.itemList.forEach(function (item2) {
                                                    var dto = {};
                                                    dto.element1Id = vm['uuid']();
                                                    dto.element2Id = item2.uuid;
                                                    dto.element3Id = item3.uuid;
                                                    dto.amount = vm[item2.uuid]();
                                                    result.push(dto);
                                                });
                                            });
                                        }
                                    });
                                    return result;
                                };
                                /**
                                 * Paste data from excel.
                                 */
                                ThreeDemensionViewModel.prototype.pasteFromExcel = function () {
                                    // Do parsing.
                                    return;
                                };
                                /**
                                 * Build element 3 information.
                                 */
                                ThreeDemensionViewModel.prototype.buildElement3Infomation = function () {
                                    var self = this;
                                    // Build elements 3 item info.
                                    self.element3Items(_.map(self.elementSettings[2].itemList, function (item) {
                                        item.name = item.displayName;
                                        return item;
                                    }));
                                    self.selectedElement3ItemId(self.elementSettings[2].itemList[0].uuid);
                                    self.element3Name(self.elementSettings[2].demensionName);
                                };
                                /**
                                * Init ig grid.
                                */
                                ThreeDemensionViewModel.prototype.initIgGrid = function (data) {
                                    var self = this;
                                    ko.cleanNode($('#dataTable').get(0));
                                    // Regenerate columns and columsn settings.
                                    var columns = [];
                                    var columnSettings = [];
                                    // Fixed part.
                                    columns.push({ headerText: 'UUID', dataType: 'string', key: 'uuid', width: '100px', hidden: true });
                                    columns.push({ headerText: self.elementSettings[0].demensionName, dataType: 'string', key: 'name', width: '100px', columnCssClass: "bgIgCol" });
                                    columnSettings.push({ columnKey: 'uuid', readOnly: true });
                                    columnSettings.push({ columnKey: 'name', readOnly: true });
                                    // Dynamic part.
                                    var secondDemensionElements = self.elementSettings[1];
                                    var mergeColumn = { headerText: secondDemensionElements.demensionName, group: [] };
                                    _.forEach(secondDemensionElements.itemList, function (item) {
                                        var colName = item.displayName;
                                        mergeColumn.group.push({ headerText: colName, dataType: 'number', key: item.uuid, width: '100px', columnCssClass: "halign-right" });
                                        columnSettings.push({ columnKey: item.uuid, readOnly: false });
                                    });
                                    columns.push(mergeColumn);
                                    // Set data soruce.
                                    self.igGridDataSource(data);
                                    // IgGrid
                                    self.igGrid = ko.observable({
                                        dataSource: self.igGridDataSource,
                                        width: '700px',
                                        primaryKey: 'uuid',
                                        height: '250px',
                                        features: [
                                            {
                                                name: 'MultiColumnHeaders'
                                            },
                                            {
                                                name: 'ColumnFixing',
                                                fixingDirection: 'left',
                                                columnSettings: [
                                                    {
                                                        columnKey: 'name',
                                                        isFixed: true
                                                    }
                                                ]
                                            },
                                            {
                                                name: 'Updating',
                                                editMode: 'row',
                                                enableAddRow: false,
                                                excelNavigatorMode: false,
                                                enableDeleteRow: false,
                                                columnSettings: columnSettings,
                                            }
                                        ],
                                        autoCommit: true,
                                        columns: columns
                                    });
                                    // Bind.
                                    if (mergeColumn.group.length > 0) {
                                        ko.applyBindingsToNode($('#dataTable').get(0), { igGrid: self.igGrid });
                                    }
                                };
                                return ThreeDemensionViewModel;
                            }(history_1.base.BaseHistoryViewModel));
                            history_1.ThreeDemensionViewModel = ThreeDemensionViewModel;
                            /**
                             * Item view model.
                             */
                            var ItemViewModel = (function () {
                                /**
                                 * Constructor.
                                 */
                                function ItemViewModel(type, item) {
                                    var self = this;
                                    self['uuid'] = ko.observable(item.uuid);
                                    self['name'] = ko.observable(item.displayName);
                                }
                                return ItemViewModel;
                            }());
                        })(history = a.history || (a.history = {}));
                    })(a = qmm016.a || (qmm016.a = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm016.a.history.threedemension.js.map