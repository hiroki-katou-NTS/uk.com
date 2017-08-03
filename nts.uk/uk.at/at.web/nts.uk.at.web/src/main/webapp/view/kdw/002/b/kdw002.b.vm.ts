module nts.uk.at.view.kdw002.b {
    import alert = nts.uk.ui.dialog.alert;
    export module viewmodel {
        import getText = nts.uk.resource.getText;
        export class ScreenModel {
            bussinessCodeItems: KnockoutObservableArray<ItemModel>;
            columns2: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            txtSearch: KnockoutObservable<string>;
            constructor() {
                var self = this;
                this.bussinessCodeItems = ko.observableArray([]);

                for (let i = 1; i < 3; i++) {
                    this.bussinessCodeItems.push(new ItemModel('00' + i, '基本給'));
                }
                this.columns2 = ko.observableArray([
                    { headerText: getText('KDW002_12'), key: 'code', width: 100 },
                    { headerText: getText('KDW002_4'), key: 'name', width: 150 },
                ]);
                this.currentCode = ko.observable();

                var data = [
                    {
                        "attendanceItemId": 1,
                        "attendanceItemName": "name1",
                        "use": true,
                        "youCanChangeIt": true,
                        "canBeChangedByOthers": true,
                        "userCanSet": false

                    },
                    {
                        "attendanceItemId": 2,
                        "attendanceItemName": "name2",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 3,
                        "attendanceItemName": "name3",
                        "use": true,
                        "youCanChangeIt": true,
                        "canBeChangedByOthers": true,
                        "userCanSet": false
                    },
                    {
                        "attendanceItemId": 4,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 5,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 6,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 7,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 8,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 9,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 10,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 11,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 12,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 13,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 14,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 15,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 26,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 16,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 17,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 18,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 19,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 20,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 21,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 22,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 23,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 24,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    },
                    {
                        "attendanceItemId": 25,
                        "attendanceItemName": "name4",
                        "use": false,
                        "youCanChangeIt": false,
                        "canBeChangedByOthers": false,
                        "userCanSet": true
                    }

                ];
                //                  var useTemplate = "<span style=\"width:100%;display:inline-block;overflow:hidden;text-align:center;\">" +
                //                    "<span class=\"ui-state-default ui-corner-all ui-igcheckbox-small\">" +
                //                    "<span class=\"ui-icon ui-icon-check ui-igcheckbox-small-on\"" +
                //                    " data-rowid=\"${attendanceItem"useChanged(event, 'use');\"/></span></span>";

                //                var useTemplate = "<span style=\"width:100%;display:inline-block;overflow:hidden;text-align:center;\">" +
                //                    "<span class=\"ui-state-default ui-corner-all ui-igcheckbox-small\">" +
                //                    "<span class=\"ui-icon ui-icon-check ui-igcheckbox-normal-on{{if ${use} === \"true\"}} {{else}} ui-igcheckbox-normal-off{{/if}}\"" +
                //                    " data-rowid=\"${attendanceItemId}\"  onclick=\"useChanged(event, 'use');\"/></span></span>";
                //                var youCanChangeItTemplate = "<span style=\"width:100%;display:inline-block;overflow:hidden;text-align:center;\">" +
                //                    "<span class=\"ui-state-default ui-corner-all ui-igcheckbox-small\">" +
                //                    "<span class=\"ui-icon ui-icon-check ui-igcheckbox-normal-on{{if ${youCanChangeIt} === \"true\"}} {{else}} ui-igcheckbox-normal-off{{/if}}\"" +
                //                    " data-rowid=\"${attendanceItemId}\"  onclick=\"youCanChangeItChanged(event, 'youCanChangeIt');\"/></span></span>";
                //                var canBeChangedByOthersTemplate = "<span style=\"width:100%;display:inline-block;overflow:hidden;text-align:center;\">" +
                //                    "<span class=\"ui-state-default ui-corner-all ui-igcheckbox-small\">" +
                //                    "<span class=\"ui-icon ui-icon-check ui-igcheckbox-normal-on{{dByOthers} === \"true\"}} {{else}} ui-igcheckbox-normal-off{{/if}}\"" +
                //                    " data-rowid=\"${attendanceItemId}\"  onclick=\"canBeChangedByOthersChanged(event, 'canBeChangedByOthers');\"/></span></span>";

                var useTemplate = "<input type='checkbox' {{if ${use} }} checked {{/if}} onclick='useChanged(this, ${attendanceItemId})' />";
                var youCanChangeItTemplate = "<input type='checkbox' {{if ${youCanChangeIt} }} checked {{/if}} onclick='youCanChangeItChanged(this, ${attendanceItemId})' />";
                var canBeChangedByOthersTemplate = "<input type='checkbox' {{if ${canBeChangedByOthers} }} checked {{/if}} onclick='canBeChangedByOthersChanged(this, ${attendanceItemId})' />";

                //                $("#grid").igGrid({
                //                    primaryKey: "attendanceItemId",
                //                    height: 550,
                //                    dataSource: data,
                //                    autoGenerateColumns: false,
                //                    renderCheckboxes: true,
                //                    dataSourceType: "json",
                //                    autoCommit: true,
                //                    alternateRowStyles: false,
                //                    cellClick: function(evt, ui) {
                //                        check(evt, ui);
                //                    },
                //                    columns: [
                //                        { key: "attendanceItemId", width: "50px", headerText: getText('KDW002_3'), dataType: "number" },
                //                        { key: "attendanceItemName", width: "250px", headerText: getText('KDW002_4'), dataType: "string" },
                //                        { key: "use", width: "250px", headerText: getText('KDW002_5'), dataType: "bool" },
                //                        { key: "youCanChangeIt", width: "250px", headerText: getText('KDW002_6'), dataType: "bool" },
                //                        { key: "canBeChangedByOthers", width: "250px", headerText: getText('KDW002_7'), dataType: "bool" }
                //                    ]
                ////                    ,
                ////                    features: [
                ////                        {
                ////                            name: 'Updating',
                ////                            showDoneCancelButtons: false,
                ////                            enableAddRow: false,
                ////                            enableDeleteRow: false,
                ////                            editMode: 'cell',
                ////                            columnSettings: [
                ////                                {
                ////                                    columnKey: 'use',
                ////                                    readOnly: true
                ////                                }
                ////                            ]
                ////                        }, {
                ////                            name: "Updatin     c
                ////                                { columnKey: "attendanceItemId", readOnly: false }
                ////                            ]
                ////                        },
                ////                        {
                ////                            name: "Updating",
                ////                            columnSettings: [
                ////                                { columnKey: "attendanceItemName", readOnly: false }
                ////                            ]
                ////                        }
                ////
                ////                    ]
                //                });    




                $("#grid").igGrid({
                    primaryKey: "attendanceItemId",
                    height: 400,
                    dataSource: data,
                    autoGenerateColumns: false,
                    alternateRowStyles: false,
                    dataSourceType: "json",
                    autoCommit: true,
                    columns: [
                        { key: "attendanceItemId", width: "100px", headerText: getText('KDW002_3'), dataType: "number", columnCssClass: "readOnlyColor" },
                        { key: "attendanceItemName", width: "250px", headerText: getText('KDW002_4'), dataType: "string", columnCssClass: "readOnlyColor" },
                        { key: "use", width: "150px", headerText: getText('KDW002_5'), dataType: "bool", template: useTemplate },
                        { key: "youCanChangeIt", width: "150px", headerText: getText('KDW002_6'), dataType: "bool", template: youCanChangeItTemplate },
                        { key: "canBeChangedByOthers", width: "150px", headerText: getText('KDW002_7'), dataType: "bool", template: canBeChangedByOthersTemplate },
                        { key: "userCanSet", dataType: "bool", hidden: true }
                    ],
                    features: [
                        {
                            name: "Updating",
                            showDoneCancelButtons: false,
                            enableAddRow: false,
                            enableDeleteRow: false,
                            editMode: 'cell',
                            columnSettings: [
                                { columnKey: "attendanceItemId", readOnly: true },
                                { columnKey: "attendanceItemName", readOnly: true },
                                { columnKey: "use", readOnly: true },
                                { columnKey: "youCanChangeIt", readOnly: true },
                                { columnKey: "canBeChangedByOthers", readOnly: true },
                                { columnKey: "userCanSet", allowHiding: false, hidden: true }
                            ]
                        },
                        {
                            name: "Selection",
                            mode: "row",
                            multipleSelection: false,
                            touchDragSelect: false, // this is true by default
                            multipleCellSelectOnClick: false
                        }



                    ]
                });

                var dataSource = $('#grid').data('igGrid').dataSource;
                var filteredData = dataSource.transformedData('afterfilteringandpaging');

                for (var i = 0; i < filteredData.length; i++) {
                    if (!filteredData[i].userCanSet) {
                        var cellYouCanChangeIt = $('#grid').igGrid('cellAt', 3, i);
                        var cellCanBeChangedByOthers = $('#grid').igGrid('cellAt', 4, i);
                        cellYouCanChangeIt.classList.add('readOnlyColorIsUse');
                        cellCanBeChangedByOthers.classList.add('readOnlyColorIsUse');
                        $("#grid").igGridUpdating("setCellValue", i + 1, "youCanChangeIt", false);
                        $("#grid").igGridUpdating("setCellValue", i + 1, "canBeChangedByOthers", false);
                    }
                }

                //  $('#grid').igGridSelection('selectRow', 1);
                self.txtSearch = ko.observable("");


                //   $("ui-iggrid-addrow ui-widget-header").hide();
                //  $("td").removeClass("ui-iggrid-editingcell");


                // $('#grid').igGridUpdating('updateRow', 1, { use: false });


            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            submitData(): void {
                var dataSource = $('#grid').data('igGrid').dataSource;
                var filteredData = dataSource.transformedData('afterfilteringandpaging');
            }
            searchData(): void {
                var self = this;
                if (self.txtSearch().length === 0) {
                    alert("検索キーワードを入力してください");
                    return;
                }
                var dataSource = $('#grid').data('igGrid').dataSource;
                var filteredData = dataSource.transformedData('afterfilteringandpaging');
                var rowIndexSelected = $('#grid').igGridSelection("selectedRow");
                var index = 0;
                if (typeof (rowIndexSelected) != 'undefined' && rowIndexSelected != null) {
                    if ((rowIndexSelected.index + 1) == filteredData.length) {
                        index = 0;
                    } else {
                        index = rowIndexSelected.index + 1;
                    }
                }
                var keynotExist = true;
                for (var i = index; i < filteredData.length; i++) {
                    var rowIndex = i + 1;
                    var idValue = $("#grid").igGrid("getCellValue", rowIndex, "attendanceItemId");
                    var nameValue = $("#grid").igGrid("getCellValue", rowIndex, "attendanceItemName");
                    if (_.includes(idValue, self.txtSearch()) || _.includes(nameValue, self.txtSearch())) {
                        $('#grid').igGridSelection('selectRow', i);
                        keynotExist = false;
                        break;
                    }
                }
                if (keynotExist) {
                    alert("該当する項目が見つかりませんでした");
                }




                // var row1 = $(".selector").igGridSelection("selectedRow");
                // var row2 = $(".selector").get();
                //var cell = $(".selector").igGrid("selectedCell");
                // var rows = $(".selector").igGrid("selectedRows");
                //var row = $(".selector").igGridSelection("activeRow");
            }

        }

        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;

            }
        }


    }
}
function useChanged(element, rowId) {
    var value = $("#grid").igGrid("getCellValue", rowId, "use");
    if ($("#grid").igGridUpdating('isEditing')) {
        $("#grid").igGridUpdating('endEdit', true);
    }
    $("#grid").igGridUpdating("setCellValue", rowId, "use", value != true);
}


function youCanChangeItChanged(element, rowId) {
    var value = $("#grid").igGrid("getCellValue", rowId, "youCanChangeIt");
    if ($("#grid").igGridUpdating('isEditing')) {
        $("#grid").igGridUpdating('endEdit', true);
    }
    $("#grid").igGridUpdating("setCellValue", rowId, "youCanChangeIt", value != true);
}
function canBeChangedByOthersChanged(element, rowId) {
    var value = $("#grid").igGrid("getCellValue", rowId, "canBeChangedByOthers");
    if ($("#grid").igGridUpdating('isEditing')) {
        $("#grid").igGridUpdating('endEdit', true);
    }
    $("#grid").igGridUpdating("setCellValue", rowId, "canBeChangedByOthers", value != true);
}



//function check(evt, ui) {
//    var self = this;
//    var ids = ["use"];
//    if (_.includes(ids, ui.colKey)) {
//        var value = $("#grid").igGrid("getCellValue", ui.rowKey, ui.colKey);
//        if ($("#grid").igGridUpdating('isEditing')) {
//            $("#grid").igGridUpdating('endEdit', true);
//        }
//        $("#grid").igGridUpdating("setCellValue", ui.rowKey, ui.colKey, value != true);
//    }
//}






