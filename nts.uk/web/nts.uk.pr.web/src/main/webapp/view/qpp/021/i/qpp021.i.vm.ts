module qpp021.i.viewmodel {
    export class ScreenModel {

        listContactPersonalSetting: KnockoutObservableArray<ContactPersonalSettingModel>;
        igGrid: any;
        selected: KnockoutObservable<any>;

        constructor() {
            this.listContactPersonalSetting = ko.observableArray<ContactPersonalSettingModel>([]);
            this.selected = ko.observable();
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.loadListContactPersonalSetting().done(() => {
                console.log(self.listContactPersonalSetting());
                self.initIgGrid();
                dfd.resolve();
            });
            return dfd.promise();
        }

        public onSaveBtnClicked(): void {
            let self = this;
            service.save(ko.toJS(self.listContactPersonalSetting));
        }

        public onCloseBtnClicked(): void {

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
                height: '350px',
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
                                readOnly: false
                            }
                        ]
                    }
                ],
                autoCommit: true,
                columns: [
                    { headerText: 'コード', dataType: 'string', key: 'employeeCode', width: '20%', columnCssClass: "bgIgCol" },
                    { headerText: '名称', dataType: 'string', key: 'employeeName', width: '20%', columnCssClass: "bgIgCol" },
                    { headerText: 'Comment', dataType: 'string', key: 'comment', width: '60%', columnCssClass: "halign-right" }
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