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
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    h.service.findAllEmployee().done(function (data) {
                                        var itemarr;
                                        itemarr = [];
                                        data.forEach(function (employee) {
                                            var item;
                                            item = new CommentPersonModel();
                                            item.setupData(employee);
                                            itemarr.push(item);
                                        });
                                        self.igGridDataSource = ko.observableArray(itemarr);
                                        self.initIgGrid();
                                        dfd.resolve(self);
                                    }).fail(function () {
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.initIgGrid = function () {
                                    var self = this;
                                    self.igGrid = ko.observable({
                                        dataSource: self.igGridDataSource,
                                        width: '100%',
                                        primaryKey: 'empCd',
                                        height: '750px',
                                        features: [
                                            {
                                                name: 'Updating',
                                                editMode: 'row',
                                                enableAddRow: false,
                                                excelNavigatorMode: false,
                                                enableDeleteRow: false,
                                                columnSettings: [
                                                    {
                                                        columnKey: 'empCd',
                                                        readOnly: true
                                                    },
                                                    {
                                                        columnKey: 'empName',
                                                        readOnly: true
                                                    },
                                                    {
                                                        columnKey: 'commentMonth',
                                                        readOnly: false
                                                    },
                                                    {
                                                        columnKey: 'commentInit',
                                                        readOnly: false
                                                    },
                                                    {
                                                        columnKey: 'groupCalTypeText',
                                                        readOnly: true
                                                    }
                                                ]
                                            }
                                        ],
                                        autoCommit: true,
                                        columns: [
                                            { headerText: 'コード', dataType: 'string', key: 'empCd', width: '10%', columnCssClass: "bgIgCol" },
                                            { headerText: '名称', dataType: 'string', key: 'empName', width: '10%', columnCssClass: "bgIgCol" },
                                            { headerText: '今月の給与明細書に印刷する連絡事項', dataType: 'string', key: 'commentMonth', width: '40%', columnCssClass: "halign-right" },
                                            { headerText: '毎月の給与明細書に印刷する連絡事項', dataType: 'string', key: 'commentInit', width: '40%', columnCssClass: "halign-right" }
                                        ]
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var CommentPersonModel = (function () {
                                function CommentPersonModel() {
                                }
                                CommentPersonModel.prototype.setupData = function (dto) {
                                    this.empCd = dto.employmentCode;
                                    this.empName = dto.employmentName;
                                    this.commentInit = '';
                                    this.commentMonth = '';
                                };
                                return CommentPersonModel;
                            }());
                        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
                    })(h = qpp021.h || (qpp021.h = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.h.vm.js.map