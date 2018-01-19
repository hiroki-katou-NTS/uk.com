module nts.uk.at.view.kdw003.b {
    export module viewmodel {
        import getText = nts.uk.resource.getText;
        export class ScreenModel {
            lstError: KnockoutObservableArray<any> = ko.observableArray([]);

            constructor() {

            }

            closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            startPage(param, errorValidate): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getErrorRefer(param).done((data) => {
                    let i : number = data.length;
                    _.each(errorValidate, value =>{
                        data.push({ id : i++,date: value.date, employeeCode: value.employeeCode, employeeName: value.employeeName, message: value.message, itemName: value.itemName, errorCode:""});
                    });
                    self.lstError(_.orderBy(data, ['employeeCode', 'date'], ['asc', 'asc']).map((d) => { return new ErrorReferModel(d); }));
                    self.loadGrid();
                    dfd.resolve();
                });
                return dfd.promise();
            }

            loadGrid() {
                var self = this;
                $("#grid").igGrid({
                    primaryKey: "id",
                    height: 400,
                    dataSource: self.lstError(),
                    autoGenerateColumns: false,
                    alternateRowStyles: false,
                    dataSourceType: "json",
                    autoCommit: true,
                    columns: [
                        { key: "id", width: "130px", hidden: true, dataType: "number" },
                        { key: "employeeCode", width: "120px", headerText: getText('KDW003_32'), dataType: "string" },
                        { key: "employeeName", width: "150px", headerText: getText('KDW003_33'), dataType: "string" },
                        { key: "date", width: "130px", headerText: getText('KDW003_34'), dataType: "string" },
                        { key: "errorCode", width: "50px", headerText: "コード", dataType: "string" },
                        { key: "message", width: "300px", headerText: getText('KDW003_36'), dataType: "string" },
                        { key: "itemName", width: "170px", headerText: getText('KDW003_37'), dataType: "string" }
                    ],
                    features: [
                        {
                            name: "Updating",
                            showDoneCancelButtons: false,
                            enableAddRow: false,
                            enableDeleteRow: false,
                            editMode: 'cell',
                            columnSettings: [
                                { columnKey: "employeeCode", readOnly: true },
                                { columnKey: "employeeName", readOnly: true },
                                { columnKey: "date", readOnly: true },
                                { columnKey: "code", readOnly: true },
                                { columnKey: "message", readOnly: true },
                                { columnKey: "itemName", readOnly: true }
                            ]
                        },
                        {
                            name: "Selection",
                            mode: "row",
                            multipleSelection: false,
                            touchDragSelect: false,
                            multipleCellSelectOnClick: false
                        },
                        {
                            name: 'Paging',
                            type: "local",
                            pageSize: 15
                        }
                    ]
                });
            }
        }

        interface IErrorReferModel {
            id: string;
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            date: string;
            errorCode: string;
            message: string;
            itemId: number;
            itemName: string;
            boldAtr: boolean;
            messageColor: string;
        }

        class ErrorReferModel {
            id: string;
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            date: string;
            errorCode: string;
            message: string;
            itemId: number;
            itemName: string;
            boldAtr: boolean;
            messageColor: string;

            constructor(model: IErrorReferModel) {
                let self = this;
                self.id = model.id;
                self.employeeId = model.employeeId;
                self.employeeCode = model.employeeCode;
                self.employeeName = model.employeeName;
                if (moment(model.date, "YYYY/MM/DD").day() == 6) {
                    self.date = '<span style="color: #4F81BD">'+ moment(model.date, 'YYYY/MM/DD').format('YYYY/MM/DD(ddd)') +'</span>';
                } else if (moment(model.date, "YYYY/MM/DD").day() == 0) {
                    self.date = '<span style="color: #e51010">'+ moment(model.date, 'YYYY/MM/DD').format('YYYY/MM/DD(ddd)') +'</span>';
                } else {
                    self.date = '<span>' + moment(model.date, 'YYYY/MM/DD').format('YYYY/MM/DD(ddd)') +'</span>';
                }
                self.errorCode = model.errorCode;
                self.message = model.boldAtr ? '<span style="font-weight: bold;color: ' + model.messageColor + ';">' + model.message + '</span>' : '<span style="color: ' + model.messageColor + ';">' + model.message + '</span>';
                self.itemId = model.itemId;
                self.itemName = model.itemName;
                self.boldAtr = model.boldAtr;
                self.messageColor = model.messageColor;
            }
        }
    }
}