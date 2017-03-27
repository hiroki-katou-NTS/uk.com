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
                            var OneDemensionViewModel = (function (_super) {
                                __extends(OneDemensionViewModel, _super);
                                function OneDemensionViewModel(history) {
                                    _super.call(this, 'history/onedemension.xhtml', history);
                                    if (history.cellItems && history.cellItems.length > 0) {
                                    }
                                    this.initIgGrid();
                                }
                                OneDemensionViewModel.prototype.initIgGrid = function () {
                                    var self = this;
                                    self.igGridDataSource = ko.observableArray([]);
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
                                            { headerText: 'Element Name', dataType: 'string', key: 'name', width: '50%' },
                                            { headerText: '値', dataType: 'number', key: 'amount', width: '50%', columnCssClass: "halign-right" }
                                        ]
                                    });
                                };
                                OneDemensionViewModel.prototype.onRefreshElement = function () {
                                    var self = this;
                                    var element = self.elementSettings[0];
                                    var itemVmList = _.map(element.itemList, function (item) {
                                        return new ItemViewModel(item);
                                    });
                                    self.igGridDataSource(itemVmList);
                                };
                                OneDemensionViewModel.prototype.getCellItem = function () {
                                    return null;
                                };
                                OneDemensionViewModel.prototype.pasteFromExcel = function () {
                                    return;
                                };
                                return OneDemensionViewModel;
                            }(history_1.base.BaseHistoryViewModel));
                            history_1.OneDemensionViewModel = OneDemensionViewModel;
                            var ItemViewModel = (function () {
                                function ItemViewModel(item) {
                                    var self = this;
                                    self.uuid = item.uuid;
                                    self.name = 'Item Name (load...)';
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
//# sourceMappingURL=qmm016.a.history.onedemension.js.map