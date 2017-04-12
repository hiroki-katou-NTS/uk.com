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
                            var CertificateViewModel = (function (_super) {
                                __extends(CertificateViewModel, _super);
                                function CertificateViewModel(history) {
                                    _super.call(this, 'history/certificate.xhtml', history);
                                    var self = this;
                                    self.igGridDataSource = ko.observableArray([]);
                                    self.certToGroupMap = [];
                                }
                                /**
                                 * On load processing.
                                 */
                                CertificateViewModel.prototype.onLoad = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    // Load certificate info.
                                    qmm016.service.instance.loadCertificate().done(function (res) {
                                        self.certificateGroupModel = res;
                                        _.forEach(self.certificateGroupModel.certifyGroups, function (group) {
                                            _.forEach(group.certifyItems, function (cert) {
                                                self.certToGroupMap[cert.code] = group;
                                            });
                                        });
                                        // Already have data.
                                        var vmList = new Array();
                                        if (self.history.valueItems && self.history.valueItems.length > 0) {
                                            // Map uuid to item.
                                            var codeToItemMap = [];
                                            var uuidToValueMap = [];
                                            _.forEach(self.elementSettings[0].itemList, function (item) {
                                                codeToItemMap[item.referenceCode] = item;
                                            });
                                            _.forEach(self.history.valueItems, function (vi) {
                                                uuidToValueMap[vi.element1Id] = vi;
                                            });
                                            // Map values item. 
                                            _.forEach(self.certificateGroupModel.certifyGroups, function (group) {
                                                _.forEach(group.certifyItems, function (cert) {
                                                    var item = codeToItemMap[cert.code];
                                                    var value = uuidToValueMap[item.uuid];
                                                    var vm = new ItemViewModel(group, item);
                                                    vm.amount(value.amount);
                                                    vmList.push(vm);
                                                });
                                            });
                                        }
                                        // Init ig girid.
                                        dfd.resolve();
                                        self.initIgGrid(vmList);
                                    });
                                    // Ret.
                                    return dfd.promise();
                                };
                                /**
                                 * Init ig grid.
                                 */
                                CertificateViewModel.prototype.initIgGrid = function (data) {
                                    var self = this;
                                    // Clean node.
                                    ko.cleanNode($('#dataTable').get(0));
                                    // IgGrid
                                    self.igGridDataSource(data);
                                    self.igGrid = ko.observable({
                                        dataSource: self.igGridDataSource,
                                        width: '100%',
                                        primaryKey: 'uuid',
                                        height: '250px',
                                        features: [
                                            {
                                                name: 'Updating',
                                                editMode: 'row',
                                                enableAddRow: false,
                                                excelNavigatorMode: false,
                                                enableDeleteRow: false,
                                                columnSettings: [
                                                    {
                                                        columnKey: 'uuid',
                                                        readOnly: true
                                                    },
                                                    {
                                                        columnKey: 'groupName',
                                                        readOnly: true
                                                    },
                                                    {
                                                        columnKey: 'name',
                                                        readOnly: true
                                                    },
                                                    {
                                                        columnKey: 'amount',
                                                        readOnly: false,
                                                        editorProvider: new $.ig.NtsNumberEditor(),
                                                        editorOptions: {
                                                            constraint: 'WtValue',
                                                            option: {},
                                                            required: true
                                                        }
                                                    },
                                                    {
                                                        columnKey: 'groupCalTypeText',
                                                        readOnly: true
                                                    }
                                                ],
                                            } /*
                                            {
                                                name: 'CellMerging',
                                                initialState: 'merged',
                                                cellsMerged: (evt: any, ui: any) => {
                                                    $('.halign-right', ui.row).removeClass('ui-iggrid-mergedcellstop ui-iggrid-mergedcell');
                                                }
                                                
                                            }*/
                                        ],
                                        autoCommit: true,
                                        columns: [
                                            { headerText: 'UUID', dataType: 'string', key: 'uuid', hidden: true, columnCssClass: 'bgIgCol' },
                                            { headerText: 'グルップ', dataType: 'string', key: 'groupName', width: '20%', columnCssClass: 'bgIgCol' },
                                            { headerText: '資格名称', dataType: 'string', key: 'name', width: '20%', columnCssClass: 'bgIgCol' },
                                            { headerText: '値', dataType: 'number', key: 'amount', width: '20%', columnCssClass: 'halign-right' },
                                            { headerText: '同一グループ内で複数の資格を取得している場合の支給方法', dataType: 'string', key: 'groupCalTypeText', width: '20%' }
                                        ]
                                    });
                                    // Bind.
                                    if (data && data.length > 0) {
                                        ko.applyBindingsToNode($('#dataTable').get(0), { igGrid: self.igGrid });
                                    }
                                };
                                /**
                                 * On refresh element.
                                 */
                                CertificateViewModel.prototype.onRefreshElement = function () {
                                    var self = this;
                                    // Map uuid to item.
                                    var codeToItemMap = [];
                                    _.forEach(self.elementSettings[0].itemList, function (item) {
                                        codeToItemMap[item.referenceCode] = item;
                                    });
                                    // Map values item.
                                    var vmList = new Array();
                                    _.forEach(self.certificateGroupModel.certifyGroups, function (group) {
                                        _.forEach(group.certifyItems, function (cert) {
                                            var item = codeToItemMap[cert.code];
                                            var vm = new ItemViewModel(group, item);
                                            vmList.push(vm);
                                        });
                                    });
                                    // Update source
                                    self.initIgGrid(vmList);
                                };
                                /**
                                 * Get setting cell item.
                                 */
                                CertificateViewModel.prototype.getCellItem = function () {
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
                                CertificateViewModel.prototype.pasteFromExcel = function () {
                                    // Do parsing.
                                    return;
                                };
                                return CertificateViewModel;
                            }(history_1.base.BaseHistoryViewModel));
                            history_1.CertificateViewModel = CertificateViewModel;
                            /**
                             * Item view model.
                             */
                            var ItemViewModel = (function () {
                                /**
                                 * Constructor.
                                 */
                                function ItemViewModel(group, item) {
                                    var self = this;
                                    self.uuid = item.uuid;
                                    self.groupName = group.name;
                                    self.name = item.displayName;
                                    self.amount = ko.observable(0);
                                    self.groupCalTypeText = group.multiApplySet == 0 ? 'BiggestMethod' : 'TotalMethod';
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
