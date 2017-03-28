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
                             * For one demension view.
                             */
                            var OneDemensionViewModel = (function (_super) {
                                __extends(OneDemensionViewModel, _super);
                                function OneDemensionViewModel(history) {
                                    _super.call(this, 'history/onedemension.xhtml', history);
                                    this.igGridDataSource = ko.observableArray([]);
                                    if (history.valueItems && history.valueItems.length > 0) {
                                        var element = history.elements[0];
                                        _.map(element.itemList, function (item) {
                                            var vm = new ItemViewModel(a.viewmodel.getElementTypeByValue(element.type), item);
                                            // Filter value.
                                            vm.amount(_.filter(history.valueItems, function (vi) {
                                                return vi.element1Id == item.uuid;
                                            })[0].amount);
                                        });
                                    }
                                    // Init grid.
                                    this.initIgGrid();
                                }
                                /**
                                 * Init ig grid.
                                 */
                                OneDemensionViewModel.prototype.initIgGrid = function () {
                                    var self = this;
                                    // IgGrid
                                    self.igGrid = ko.observable({
                                        dataSource: self.igGridDataSource,
                                        width: '60%',
                                        primaryKey: 'uuid',
                                        height: '250px',
                                        features: [
                                            {
                                                name: 'Updating',
                                                editMode: 'cell',
                                                enableAddRow: false,
                                                excelNavigatorMode: false,
                                                enableDeleteRow: false,
                                                columnSettings: [
                                                    {
                                                        columnKey: 'uuid',
                                                        readOnly: true
                                                    },
                                                    {
                                                        columnKey: 'name',
                                                        readOnly: true
                                                    },
                                                    {
                                                        columnKey: 'amount',
                                                        readOnly: false
                                                    }
                                                ],
                                            }
                                        ],
                                        autoCommit: true,
                                        columns: [
                                            { headerText: 'Element Name', dataType: 'string', key: 'uuid', hidden: true },
                                            { headerText: self.history.elements[0].demensionName, dataType: 'string', key: 'name', width: '50%' },
                                            { headerText: '値', dataType: 'number', key: 'amount', width: '50%', columnCssClass: "halign-right" }
                                        ]
                                    });
                                };
                                /**
                                 * On refresh element.
                                 */
                                OneDemensionViewModel.prototype.onRefreshElement = function () {
                                    var self = this;
                                    // First element.
                                    var element = self.elementSettings[0];
                                    var itemVmList = _.map(element.itemList, function (item) {
                                        return new ItemViewModel(a.viewmodel.getElementTypeByValue(element.type), item);
                                    });
                                    // Update source
                                    self.igGridDataSource(itemVmList);
                                };
                                /**
                                 * Get setting cell item.
                                 */
                                OneDemensionViewModel.prototype.getCellItem = function () {
                                    return _.map(this.igGridDataSource(), function (item) {
                                        var dto = {};
                                        dto.element1Id = item.uuid;
                                        dto.amount = item.amount();
                                        return dto;
                                    });
                                };
                                /**
                                 * Paste data from excel.
                                 */
                                OneDemensionViewModel.prototype.pasteFromExcel = function () {
                                    // Do parsing.
                                    return;
                                };
                                return OneDemensionViewModel;
                            }(history_1.base.BaseHistoryViewModel));
                            history_1.OneDemensionViewModel = OneDemensionViewModel;
                            /**
                             * Item view model.
                             */
                            var ItemViewModel = (function () {
                                /**
                                 * Constructor.
                                 */
                                function ItemViewModel(type, item) {
                                    var self = this;
                                    self.uuid = item.uuid;
                                    if (type.isRangeMode) {
                                        self.name = item.startVal + '～' + item.endVal;
                                    }
                                    else {
                                        self.name = 'Code';
                                    }
                                    self.amount = ko.observable(0);
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
