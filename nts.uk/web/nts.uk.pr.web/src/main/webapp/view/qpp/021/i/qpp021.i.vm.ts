module qpp021.i.viewmodel {
    export class ScreenModel {

        listContactPersonalSetting: KnockoutObservableArray<ContactPersonalSettingModel>;
        processingNo: number;
        processingYm: number;
        igGrid: any;
        selected: KnockoutObservable<any>;

        constructor() {
            this.listContactPersonalSetting = ko.observableArray<ContactPersonalSettingModel>([]);
            this.selected = ko.observable();
            this.processingNo = nts.uk.ui.windows.getShared("processingNo");
            this.processingYm = nts.uk.ui.windows.getShared("processingYm");
            let self = this;
            this.selected.subscribe(val => {
                let selectedIndex = self.listContactPersonalSetting().findIndex(item => {
                    return item.employeeCode == val;
                });
                $('#table-contact-setting').igGridSelection('selectRow', selectedIndex);
            });
        }

        /**
         * Start page.
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.loadListContactPersonalSetting().done(() => {
                self.initIgGrid();
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * Event when click save button.
         */
        public onSaveBtnClicked(): void {
            let self = this;
            // Validate.
            if ($('.nts-input').ntsError('hasError')) {
                return;
            }
            service.save(ko.toJS(self.listContactPersonalSetting)).fail(res => {
                nts.uk.ui.dialog.alert(res.message);
            });
        }

        /**
         * Event when click close button.
         */
        public onCloseBtnClicked(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * Load list contact personal setting.
         */
        private loadListContactPersonalSetting(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            $.when(service.findAllEmp(), service.findAllSetting(this.processingNo, this.processingYm))
                .done((listEmp: Array<service.EmployeeDto>, listSetting: Array<service.ContactPersonalSettingDto>) => {
                    self.listContactPersonalSetting(self.convertToModel(listEmp, listSetting));
                    dfd.resolve();
                });
            return dfd.promise();
        }

        /**
         * Convert dto to model.
         */
        private convertToModel(listEmp: Array<service.EmployeeDto>, listSetting: Array<service.ContactPersonalSettingDto>): Array<ContactPersonalSettingModel> {
            let self = this;
            let listModel: Array<ContactPersonalSettingModel> = [];
            let filteredList = listEmp.filter(emp => emp.processingNo === self.processingNo);
            filteredList.forEach((emp: service.EmployeeDto) => {
                let setting: service.ContactPersonalSettingDto = listSetting.find((setting: service.ContactPersonalSettingDto) => {
                    return emp.employmentCode === setting.employeeId
                });
                let model = new ContactPersonalSettingModel();
                model.processingNo = emp.processingNo;
                model.processingYm = self.processingYm;
                model.employeeCode = emp.employmentCode;
                model.employeeName = emp.employmentName;
                if (setting) {
                    model.comment = setting.comment;
                }
                listModel.push(model);
            });
            return listModel;
        }

        /**
         * Initialize igGrid.
         */
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
                                editorProvider: new (<any>$.ig).NtsTextEditor(),
                                editorOptions: {
                                    constraint: 'ReportComment',
                                    option: {},
                                    required: false
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