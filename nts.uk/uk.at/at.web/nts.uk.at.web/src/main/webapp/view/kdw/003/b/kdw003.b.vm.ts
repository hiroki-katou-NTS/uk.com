module nts.uk.at.view.kdw003.b {
    export module viewmodel {
        import getText = nts.uk.resource.getText;
        export class ScreenModel {
            // employeeErrorItems: KnockoutObservableArray<ItemModel>;
            constructor() {
                var self = this;
                //  this.employeeErrorItems = ko.observableArray([]);

                /* var data = [
                     {
                         "idAuto": 1,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 2,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 3,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 4,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 5,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 6,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 7,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 8,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 9,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 10,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 11,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 12,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 13,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 14,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 15,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 16,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 17,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 18,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 19,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
                         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     },
                     {
                         "idAuto": 20,
                         "employeeCode": "A000000000001",
                         "employeeName": "日通三郎",
                         "date": "2017/6/20(火)",
                         "code": 101,
         "message": "Overtime time has exceeded 45H",
                         "itemName": "Attendance embossing"
                     }
 
 
                 ]; */
                
                var data = [];



                $("#grid").igGrid({
                    primaryKey: "idAuto",
                    height: 400,
                    dataSource: data,
                    autoGenerateColumns: false,
                    alternateRowStyles: false,
                    dataSourceType: "json",
                    autoCommit: true,
                    columns: [
                        { key: "idAuto", width: "150px", hidden: true, dataType: "number" },
                        { key: "employeeCode", width: "150px", headerText: getText('KDW003_32'), dataType: "string" },
                        { key: "employeeName", width: "150px", headerText: getText('KDW003_33'), dataType: "string" },
                        { key: "date", width: "150px", headerText: getText('KDW003_34'), dataType: "string" },
                        { key: "code", width: "70px", headerText: "コード", dataType: "number" },
                        { key: "message", width: "300px", headerText: getText('KDW003_36'), dataType: "string" },
                        { key: "itemName", width: "150px", headerText: getText('KDW003_37'), dataType: "string" }
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

                //  var dataSource = $('#grid').data('igGrid').dataSource;
                //var filteredData = dataSource.transformedData('afterfilteringandpaging');


                //  $('#grid').igGridSelection('selectRow', 1);


                //   $("ui-iggrid-addrow ui-widget-header").hide();
                //  $("td").removeClass("ui-iggrid-editingcell");


                // $('#grid').igGridUpdating('updateRow', 1, { use: false });


            }

            closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }



            // var row1 = $(".selector").igGridSelection("selectedRow");
            // var row2 = $(".selector").get();
            //var cell = $(".selector").igGrid("selectedCell");
            // var rows = $(".selector").igGrid("selectedRows");
            //var row = $(".selector").igGridSelection("activeRow");

            /*
             interface IItemModel {
                employeeCode: string;
                employeeName: string;
                date: string;
                code: number;
                message: string;
                itemName: string;
            }
    
            class ItemModel {
                employeeCode: string;
                employeeName: string;
                date: string;
                code: number;
                message: string;
                itemName: string;
    
                constructor(param: IItemModel) {
                    this.employeeCode = param.employeeCode;
                    this.employeeName = param.employeeName;
                    this.date = param.date;
                    this.code = param.code;
                    this.message = param.message;
                    this.itemName = param.itemName;
                }
            }
                */


        }
    }
}