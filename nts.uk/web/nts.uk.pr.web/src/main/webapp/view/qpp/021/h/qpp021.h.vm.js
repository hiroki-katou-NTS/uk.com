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
                        var option = nts.uk.ui.option;
                        var EmpCommentDto = h.service.model.EmpCommentDto;
                        var ContactItemsSettingDto = h.service.model.ContactItemsSettingDto;
                        var ContactItemsSettingFindDto = h.service.model.ContactItemsSettingFindDto;
                        var EmpCommentFindDto = h.service.model.EmpCommentFindDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.initialCpComment = ko.observable('');
                                    self.monthCpComment = ko.observable('');
                                    this.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    h.service.findAllEmployee().done(function (data) {
                                        var dto;
                                        dto = new ContactItemsSettingFindDto();
                                        dto.processingNo = 1;
                                        dto.processingYm = 201705;
                                        var empCommentFinds;
                                        empCommentFinds = [];
                                        data.forEach(function (item) {
                                            var empComment;
                                            empComment = new EmpCommentFindDto();
                                            empComment.employeeCode = item.employmentCode;
                                            empComment.employeeName = item.employmentName;
                                            empCommentFinds.push(empComment);
                                        });
                                        dto.empCommentFinds = empCommentFinds;
                                        h.service.findContactItemSettings(dto).done(function (output) {
                                            var itemarr;
                                            itemarr = [];
                                            output.empCommentDtos.forEach(function (employee) {
                                                var item;
                                                item = new CommentPersonModel();
                                                item.setupData(employee);
                                                itemarr.push(item);
                                            });
                                            self.initialCpComment(output.initialCpComment);
                                            self.monthCpComment(output.monthCpComment);
                                            self.igGridDataSource = ko.observableArray(itemarr);
                                            self.initIgGrid();
                                            dfd.resolve(self);
                                        });
                                    }).fail(function () {
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.saveContactItemsSetting = function () {
                                    var self = this;
                                    h.service.saveContactItemSettings(self.collectData()).done(function () {
                                    }).fail(function () {
                                    });
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var dto;
                                    dto = new ContactItemsSettingDto();
                                    dto.processingNo = 1;
                                    dto.processingYm = 201705;
                                    dto.initialCpComment = self.initialCpComment();
                                    dto.monthCpComment = self.monthCpComment();
                                    var empCommentDtos;
                                    empCommentDtos = [];
                                    for (var _i = 0, _a = self.igGridDataSource(); _i < _a.length; _i++) {
                                        var item = _a[_i];
                                        var empCommentDto;
                                        empCommentDto = new EmpCommentDto();
                                        empCommentDto.empCd = item.empCd;
                                        empCommentDto.employeeName = item.empName;
                                        empCommentDto.initialComment = item.initialComment;
                                        empCommentDto.monthlyComment = item.monthlyComment;
                                        empCommentDtos.push(empCommentDto);
                                    }
                                    dto.empCommentDtos = empCommentDtos;
                                    return dto;
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
                                                        columnKey: 'monthlyComment',
                                                        readOnly: false
                                                    },
                                                    {
                                                        columnKey: 'initialComment',
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
                                            { headerText: '今月の給与明細書に印刷する連絡事項', dataType: 'string', key: 'monthlyComment', width: '40%', columnCssClass: "halign-right" },
                                            { headerText: '毎月の給与明細書に印刷する連絡事項', dataType: 'string', key: 'initialComment', width: '40%', columnCssClass: "halign-right" }
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
                                    this.empCd = dto.empCd;
                                    this.empName = dto.employeeName;
                                    this.monthlyComment = dto.monthlyComment;
                                    this.initialComment = dto.initialComment;
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