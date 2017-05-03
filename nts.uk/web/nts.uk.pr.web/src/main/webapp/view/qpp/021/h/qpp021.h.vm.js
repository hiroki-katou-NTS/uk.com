var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp021;
                (function (qpp021) {
                    var h;
                    (function (h) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    var item;
                                    item = new ItemViewModel();
                                    item.uuid = '12321323223';
                                    item.groupName = 'NAME';
                                    item.name = 'name';
                                    item.amount = ko.observable(6);
                                    item.groupCalTypeText = '323ds2f3d';
                                    var itemarr;
                                    itemarr = [];
                                    itemarr.push(item);
                                    self.igGridDataSource = ko.observableArray(itemarr);
                                    self.initIgGrid();
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve(self);
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.initIgGrid = function () {
                                    var self = this;
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
                                                        columnKey: 'name',
                                                        readOnly: true
                                                    },
                                                    {
                                                        columnKey: 'amount',
                                                        editorOptions: {
                                                            constraint: 'WtValue',
                                                            option: {},
                                                            required: true
                                                        },
                                                        readOnly: false
                                                    }
                                                ],
                                            }
                                        ],
                                        autoCommit: true,
                                        columns: [
                                            { headerText: 'Element Name', dataType: 'string', key: 'uuid', hidden: true },
                                            { headerText: 'コード', dataType: 'string', key: 'code', width: '10%', columnCssClass: "bgIgCol" },
                                            { headerText: '名称', dataType: 'string', key: 'name', width: '10%', columnCssClass: "bgIgCol" },
                                            { headerText: '今月の給与明細書に印刷する連絡事項', dataType: 'text', key: 'amount', width: '40%', columnCssClass: "halign-right" },
                                            { headerText: '毎月の給与明細書に印刷する連絡事項', dataType: 'text', key: 'amount', width: '40%', columnCssClass: "halign-right" }
                                        ]
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var ItemViewModel = (function () {
                                function ItemViewModel() {
                                }
                                return ItemViewModel;
                            }());
                        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
                    })(h = qpp021.h || (qpp021.h = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.h.vm.js.map