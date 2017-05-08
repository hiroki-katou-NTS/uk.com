module qpp021.i.viewmodel {
    export class ScreenModel {

        listContactPersonalSetting: KnockoutObservableArray<ContactPersonalSettingModel>;
        igGrid: any;
        selected: KnockoutObservable<any>;
        dirtyChecker: nts.uk.ui.DirtyChecker;

        constructor() {
            this.listContactPersonalSetting = ko.observableArray<ContactPersonalSettingModel>([]);
            this.selected = ko.observable();
            let self = this;
            self.dirtyChecker = new nts.uk.ui.DirtyChecker(self.listContactPersonalSetting);
            this.selected.subscribe(val => {
                let selectedIndex = self.listContactPersonalSetting().findIndex(item => {
                    return item.employeeCode == val;
                });
                $('#table-contact-setting').igGridSelection('selectRow', selectedIndex);
            });
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.loadListContactPersonalSetting().done(() => {
                self.initIgGrid();
                dfd.resolve();
            });
            return dfd.promise();
        }

        public onSaveBtnClicked(): void {
            let self = this;
            // Validate.
            $('.ui-igedit-input').ntsEditor('validate');
            if ($('.ui-igedit-input').ntsError('hasError')) {
                return;
            }
            service.save(ko.toJS(self.listContactPersonalSetting)).done(() => {
                self.dirtyChecker.reset();
            }).fail(res => {
                nts.uk.ui.dialog.alert(res.message);
            });
        }

        public onCloseBtnClicked(): void {
            let self = this;
            if (self.dirtyChecker.isDirty()) {
                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function() {
                    nts.uk.ui.windows.close();
                });
            } else {
                nts.uk.ui.windows.close();
            }
        }

        private loadListContactPersonalSetting(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            $.when(service.findAllEmp(), service.findAllSetting())
                .done((listEmp: Array<service.EmployeeDto>, listSetting: Array<service.ContactPersonalSettingDto>) => {
                    self.listContactPersonalSetting(self.convertToModel(listEmp, listSetting));
                });
            dfd.resolve();
            return dfd.promise();
        }

        private convertToModel(listEmp: Array<service.EmployeeDto>, listSetting: Array<service.ContactPersonalSettingDto>): Array<ContactPersonalSettingModel> {
            let listModel: Array<ContactPersonalSettingModel> = [];
            listSetting.forEach((setting: service.ContactPersonalSettingDto) => {
                let emp: service.EmployeeDto = listEmp.find((emp: service.EmployeeDto) => {
                    return emp.employmentCode === setting.employeeId
                });
                if (emp) {
                    let model = new ContactPersonalSettingModel();
                    model.processingNo = emp.processingNo;
                    model.processingYm = 201704;
                    model.employeeCode = emp.employmentCode;
                    model.employeeName = emp.employmentName;
                    model.comment = setting.comment;
                    listModel.push(model);
                }
            });
            return listModel;
        }

        private initIgGrid(): void {
            var self = this;
            self.igGrid = ko.observable({
                dataSource: self.listContactPersonalSetting,
                width: '100%',
                primaryKey: 'employeeCode',
                height: '400px',
                features: [
                    {
                        name: 'Updating',
                        editMode: 'row',
                        enableAddRow: false,
                        excelNavigatorMode: false,
                        enableDeleteRow: false,
                        columnSettings: [
                            {
                                columnKey: 'employeeCode',
                                readOnly: true
                            },
                            {
                                columnKey: 'employeeName',
                                readOnly: true
                            },
                            {
                                columnKey: 'comment',
                                validation: true,
                                editorOptions: {
                                    validatorOptions: {
                                        text: {
                                            type: 'Any',
                                            length: 100
                                        }
                                    }
                                },
                                readOnly: false
                            }
                        ]
                    },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: false,
                        activation: false,
                    }
                ],
                autoCommit: true,
                columns: [
                    { headerText: 'コード', dataType: 'string', key: 'employeeCode', width: '20%', columnCssClass: "bgIgCol" },
                    { headerText: '名称', dataType: 'string', key: 'employeeName', width: '20%', columnCssClass: "bgIgCol" },
                    { headerText: '今月の給与明細に印刷する個人へのコメント', dataType: 'string', key: 'comment', width: '60%', columnCssClass: "halign-right" }
                ]
            });
        }

    }

    export class ContactPersonalSettingModel {
        processingNo: number;
        processingYm: number;
        employeeCode: string;
        employeeName: string;
        comment: string;
    }
}