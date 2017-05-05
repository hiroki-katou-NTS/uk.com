module nts.uk.pr.view.qpp021.h {

    import option = nts.uk.ui.option;
    import EmploymentDto = service.model.EmploymentDto;

    export module viewmodel {
        export class ScreenModel {

            igGrid: any;
            igGridDataSource: KnockoutObservableArray<CommentPersonModel>;

            constructor() {

            }

            //start page
            startPage(): JQueryPromise<this> {
                var self = this;
                var dfd = $.Deferred<this>();
                service.findAllEmployee().done(data => {
                    var itemarr: CommentPersonModel[];
                    itemarr = [];
                    data.forEach(employee => {
                        var item: CommentPersonModel;
                        item = new CommentPersonModel();
                        item.setupData(employee);
                        itemarr.push(item);
                    });
                    self.igGridDataSource = ko.observableArray(itemarr);
                    self.initIgGrid();
                    dfd.resolve(self);
                }).fail(function() {

                });

                return dfd.promise();
            }


            initIgGrid(): void {
                var self = this;

                // IgGrid
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
            }
        }

        class CommentPersonModel {
            empCd: string;
            empName: string;
            commentInit: string;
            commentMonth: string;
            groupCalTypeText: string;

            setupData(dto: EmploymentDto) {
                this.empCd = dto.employmentCode;
                this.empName = dto.employmentName;
                this.commentInit = '';
                this.commentMonth = '';
            }
        }
    }
}