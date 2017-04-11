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
                            var TwoDemensionViewModel = (function (_super) {
                                __extends(TwoDemensionViewModel, _super);
                                function TwoDemensionViewModel(history) {
                                    _super.call(this, 'history/twodemension.xhtml', history);
                                    this.igGridDataSource = ko.observableArray([]);
                                }
                                TwoDemensionViewModel.prototype.onLoad = function () {
                                    var self = this;
                                    var history = self.history;
                                    if (history.valueItems && history.valueItems.length > 0) {
                                        var element = history.elements[0];
                                        var secondElement = history.elements[1];
                                        var itemVmList = _.map(element.itemList, function (item) {
                                            var vm = new ItemViewModel(element.type, item);
                                            var valueItemMap = [];
                                            _.filter(history.valueItems, function (vi) { return vi.element1Id == item.uuid; })
                                                .forEach(function (vi) { valueItemMap[vi.element2Id] = vi; });
                                            secondElement.itemList.forEach(function (item2) {
                                                vm[item2.uuid] = ko.observable(valueItemMap[item2.uuid].amount);
                                            });
                                            return vm;
                                        });
                                    }
                                    self.initIgGrid(itemVmList);
                                    return $.Deferred().resolve().promise();
                                };
                                TwoDemensionViewModel.prototype.onRefreshElement = function () {
                                    var self = this;
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
                                    self.initIgGrid(dataSource);
                                };
                                TwoDemensionViewModel.prototype.getCellItem = function () {
                                    var self = this;
                                    var firstEl = self.elementSettings[0];
                                    var secondEl = self.elementSettings[1];
                                    var result = new Array();
                                    self.igGridDataSource().forEach(function (data) {
                                        secondEl.itemList.forEach(function (item) {
                                            var dto = {};
                                            dto.element1Id = data['uuid']();
                                            dto.element2Id = item.uuid;
                                            dto.amount = data[item.uuid]();
                                            result.push(dto);
                                        });
                                    });
                                    return result;
                                };
                                TwoDemensionViewModel.prototype.pasteFromExcel = function () {
                                    return;
                                };
                                TwoDemensionViewModel.prototype.initIgGrid = function (data) {
                                    var self = this;
                                    ko.cleanNode($('#dataTable').get(0));
                                    var columns = [];
                                    var columnSettings = [];
                                    columns.push({ headerText: 'UUID', dataType: 'string', key: 'uuid', width: '100px', hidden: true });
                                    columns.push({ headerText: self.elementSettings[0].demensionName, dataType: 'string', key: 'name', width: '100px', columnCssClass: "bgIgCol" });
                                    columnSettings.push({ columnKey: 'uuid', readOnly: true });
                                    columnSettings.push({ columnKey: 'name', readOnly: true });
                                    var secondDemensionElements = self.elementSettings[1];
                                    var mergeColumn = { headerText: secondDemensionElements.demensionName, group: [] };
                                    _.forEach(secondDemensionElements.itemList, function (item) {
                                        var colName = item.displayName;
                                        mergeColumn.group.push({ headerText: colName, dataType: 'number', key: item.uuid, width: '100px', columnCssClass: "halign-right" });
                                        columnSettings.push({
                                            columnKey: item.uuid,
                                            readOnly: false,
                                            editorProvider: new $.ig.NtsNumberEditor(),
                                            editorOptions: {
                                                constraint: 'WtValue',
                                                option: {},
                                                required: true
                                            }
                                        });
                                    });
                                    columns.push(mergeColumn);
                                    self.igGridDataSource(data);
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
                                    if (mergeColumn.group.length > 0) {
                                        ko.applyBindingsToNode($('#dataTable').get(0), { igGrid: self.igGrid });
                                    }
                                };
                                return TwoDemensionViewModel;
                            }(history_1.base.BaseHistoryViewModel));
                            history_1.TwoDemensionViewModel = TwoDemensionViewModel;
                            var ItemViewModel = (function () {
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
