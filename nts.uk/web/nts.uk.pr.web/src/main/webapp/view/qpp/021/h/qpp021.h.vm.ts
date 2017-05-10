module nts.uk.pr.view.qpp021.h {

    import option = nts.uk.ui.option;
    import EmploymentDto = service.model.EmploymentDto;
    import EmpCommentDto = service.model.EmpCommentDto;
    import ContactItemsSettingDto = service.model.ContactItemsSettingDto;
    import ContactItemsSettingFindDto = service.model.ContactItemsSettingFindDto;
    import EmpCommentFindDto = service.model.EmpCommentFindDto;

    export module viewmodel {
        export class ScreenModel {

            igGrid: any;
            igGridDataSource: KnockoutObservable<Array<CommentPersonModel>>;
            initialCpComment: KnockoutObservable<string>;
            monthCpComment: KnockoutObservable<string>;
            textEditorOption: KnockoutObservable<any>;
            processingNo: number;
            processingYm: number;
            lbl_processingYm: string;

            constructor() {
                var self = this;
                self.initialCpComment = ko.observable('');
                self.monthCpComment = ko.observable('');
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                var dataProcessingNo = nts.uk.ui.windows.getShared("processingNo");
                var dataprocessingYm = nts.uk.ui.windows.getShared("processingYm");
                self.processingNo = dataProcessingNo;
                self.processingYm = dataprocessingYm;
                self.lbl_processingYm = nts.uk.time.formatYearMonth(self.processingYm);
            }

            //start page
            startPage(): JQueryPromise<this> {
                var self = this;
                var dfd = $.Deferred<this>();
                service.findAllEmployee().done(data => {

                    var dto: ContactItemsSettingFindDto;
                    dto = new ContactItemsSettingFindDto();
                    dto.processingNo = self.processingNo;
                    dto.processingYm = self.processingYm;

                    var empCommentFinds: EmpCommentFindDto[];
                    empCommentFinds = [];

                    data.forEach(item => {
                        var empComment: EmpCommentFindDto;
                        empComment = new EmpCommentFindDto();
                        empComment.employeeCode = item.employmentCode;
                        empComment.employeeName = item.employmentName;
                        empCommentFinds.push(empComment);
                    })
                    dto.empCommentFinds = empCommentFinds;
                    service.findContactItemSettings(dto).done(output => {
                        var itemarr: CommentPersonModel[];
                        itemarr = [];
                        output.empCommentDtos.forEach(employee => {
                            var item: CommentPersonModel;
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

                }).fail(function(error) {
                });
                return dfd.promise();
            }

            saveContactItemsSetting(): void {
                var self = this;
                if (self.validateData()) {
                    return;
                }
                service.saveContactItemSettings(self.collectData());
            }

            closeContactItemsSetting(): void {
                nts.uk.ui.windows.close();
            }


            collectData(): ContactItemsSettingDto {
                var self = this;
                var dto: ContactItemsSettingDto;
                dto = new ContactItemsSettingDto();
                dto.processingNo = self.processingNo;
                dto.processingYm = self.processingYm;
                dto.initialCpComment = self.initialCpComment();
                dto.monthCpComment = self.monthCpComment();
                var empCommentDtos: EmpCommentDto[];
                empCommentDtos = [];
                for (var item of self.igGridDataSource()) {
                    var empCommentDto: EmpCommentDto;
                    empCommentDto = new EmpCommentDto();
                    empCommentDto.empCd = item.empCd;
                    empCommentDto.employeeName = item.empName;
                    empCommentDto.initialComment = item.initialComment;
                    empCommentDto.monthlyComment = item.monthlyComment;
                    empCommentDtos.push(empCommentDto);
                }
                dto.empCommentDtos = empCommentDtos;
                return dto;
            }

            private validateData(): boolean {
                $("#inp_monthCpComment").ntsEditor("validate");
                $("#inp_initialCpComment").ntsEditor("validate");
                if ($('.nts-editor').ntsError("hasError")) {
                    return true;
                }
                return false;
            }

            initIgGrid(): void {
                var self = this;

                // IgGrid
                self.igGrid = ko.observable({
                    dataSource: self.igGridDataSource,
                    width: '100%',
                    primaryKey: 'empCd',
                    height: '500px',
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
                                    editorProvider: new (<any>$.ig).NtsTextEditor(),
                                    editorOptions: {
                                        constraint: 'ReportComment',
                                        option: {
                                        },
                                        required: false
                                    },
                                    columnKey: 'monthlyComment',
                                    readOnly: false
                                },
                                {
                                    columnKey: 'initialComment',
                                    editorProvider: new (<any>$.ig).NtsTextEditor(),
                                    editorOptions: {
                                        constraint: 'ReportComment',
                                        option: {
                                        },
                                        required: false
                                    },
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
                        {
                            headerText: 'コード', dataType: 'string', key: 'empCd', width: '10%', columnCssClass: "bgIgCol",
                            headerCssClass: "labelHeader"
                        },
                        {
                            headerText: '名称', dataType: 'string', key: 'empName', width: '10%', columnCssClass: "bgIgCol",
                            headerCssClass: "labelHeader"
                        },
                        {
                            headerText: '今月の給与明細書に印刷する連絡事項', dataType: 'string', key: 'monthlyComment', width: '40%',
                            columnCssClass: "halign-left", headerCssClass: "labelHeader"
                        },
                        {
                            headerText: '毎月の給与明細書に印刷する連絡事項', dataType: 'string', key: 'initialComment', width: '40%',
                            columnCssClass: "halign-left", headerCssClass: "labelHeader"
                        }
                    ]
                });
            }
        }

        class CommentPersonModel {
            empCd: string;
            empName: string;
            initialComment: string;
            monthlyComment: string;
            groupCalTypeText: string;

            setupData(dto: EmpCommentDto) {
                this.empCd = dto.empCd;
                this.empName = dto.employeeName;
                this.monthlyComment = dto.monthlyComment;
                this.initialComment = dto.initialComment;
            }
        }
    }
}