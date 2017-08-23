module nts.uk.at.view.kdw002.b {
    import alert = nts.uk.ui.dialog.alert;
    export module viewmodel {
        import href = nts.uk.request.jump;
        import getText = nts.uk.resource.getText;
        import infor = nts.uk.ui.dialog.info;
        export class ScreenModel {
            bussinessCodeItems: KnockoutObservableArray<BusinessType>;
            bussinessColumn: KnockoutObservableArray<any>;
            bussinessCurrentCode: KnockoutObservable<any>;
            txtSearch: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.bussinessCodeItems = ko.observableArray([]);

                this.bussinessColumn = ko.observableArray([
                    { headerText: getText('KDW002_12'), key: 'businessTypeCode', width: 100 },
                    { headerText: getText('KDW002_4'), key: 'businessTypeName', width: 150 },
                ]);

                self.bussinessCurrentCode = ko.observable('');
                self.bussinessCurrentCode.subscribe(businessTypeCode => {
                    service.getBusinessTypes().done(businessTypes => {

                        if (!nts.uk.util.isNullOrUndefined(businessTypes)) {
                            self.bussinessCurrentCode(businessTypeCode);
                            service.getListDailyServiceTypeControl(businessTypeCode).done(DailyServiceTypeControls => {
                                $("#grid").igGrid("dataSourceObject", DailyServiceTypeControls).igGrid("dataBind");
                            });
                            businessTypes.forEach(businessType => {
                                self.bussinessCodeItems.push(new BusinessType(businessType));
                            });
                        }

                    });

                });



                loadIgrid();

                service.getBusinessTypes().done(businessTypes => {

                    if (!nts.uk.util.isNullOrUndefined(businessTypes)) {
                        var businessTypeCode = businessTypes[0].businessTypeCode;
                        self.bussinessCurrentCode(businessTypeCode);
                        service.getListDailyServiceTypeControl(businessTypeCode).done(DailyServiceTypeControls => {
                            $("#grid").igGrid("dataSourceObject", DailyServiceTypeControls).igGrid("dataBind");
                        });
                        businessTypes.forEach(businessType => {
                            self.bussinessCodeItems.push(new BusinessType(businessType));
                        });
                    }

                });


                self.txtSearch = ko.observable("");

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            submitData(): void {
                var self = this;
                var dataSource = $('#grid').data('igGrid').dataSource;
                var filteredData = dataSource.transformedData('afterfilteringandpaging');
                let DailyServiceTypeControls = [];
                filteredData.forEach(dstc => {
                    DailyServiceTypeControls.push(new DailyServiceTypeControl({
                        attendanceItemId: dstc.attendanceItemId,
                        businessTypeCode: self.bussinessCurrentCode(),
                        youCanChangeIt: dstc.youCanChangeIt,
                        canBeChangedByOthers: dstc.canBeChangedByOthers,
                        use: dstc.use
                    }));
                });

                service.updateDailyService(DailyServiceTypeControls).done(x => {
                    infor(nts.uk.resource.getMessage("Msg_15", []));
                });
            }

            navigateView(): void {
                let self = this;
                var path = "/view/kdw/006/index.xhtml";
                href(path);
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

        interface IBusinessType {
            businessTypeCode: string;
            businessTypeName: string;
        }

        class BusinessType {
            businessTypeCode: string;
            businessTypeName: string;
            constructor(param: IBusinessType) {
                let self = this;
                self.businessTypeCode = param.businessTypeCode;
                self.businessTypeName = param.businessTypeName;
            }
        }
        interface IDailyServiceTypeControl {
            attendanceItemId: number;
            businessTypeCode: string;
            youCanChangeIt: boolean;
            canBeChangedByOthers: boolean;
            use: boolean
        }

        class DailyServiceTypeControl {
            attendanceItemId: number;
            businessTypeCode: string;
            youCanChangeIt: boolean;
            canBeChangedByOthers: boolean;
            use: boolean
            constructor(param: IDailyServiceTypeControl) {
                let self = this;
                self.attendanceItemId = param.attendanceItemId;
                self.businessTypeCode = param.businessTypeCode;
                self.youCanChangeIt = param.youCanChangeIt;
                self.canBeChangedByOthers = param.canBeChangedByOthers;
                self.use = param.use;
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

function loadIgrid() {
    //load igrid
    var DailyServiceTypeControls = [];
    var useTemplate = "<input type='checkbox' {{if ${use} }} checked {{/if}} onclick='useChanged(this, ${attendanceItemId})' />";
    var youCanChangeItTemplate = "<input type='checkbox' {{if ${youCanChangeIt} }} checked {{/if}} onclick='youCanChangeItChanged(this, ${attendanceItemId})' />";
    var canBeChangedByOthersTemplate = "<input type='checkbox' {{if ${canBeChangedByOthers} }} checked {{/if}} onclick='canBeChangedByOthersChanged(this, ${attendanceItemId})' />";

    $("#grid").igGrid({
        primaryKey: "attendanceItemId",
        height: 400,
        dataSource: DailyServiceTypeControls,
        autoGenerateColumns: false,
        alternateRowStyles: false,
        dataSourceType: "json",
        autoCommit: true,
        columns: [
            { key: "attendanceItemId", width: "100px", headerText: nts.uk.resource.getText('KDW002_3'), dataType: "number", columnCssClass: "readOnlyColor" },
            { key: "attendanceItemName", width: "250px", headerText: nts.uk.resource.getText('KDW002_4'), dataType: "string", columnCssClass: "readOnlyColor" },
            { key: "use", width: "150px", headerText: nts.uk.resource.getText('KDW002_5'), dataType: "bool", template: useTemplate },
            { key: "youCanChangeIt", width: "150px", headerText: nts.uk.resource.getText('KDW002_6'), dataType: "bool", template: youCanChangeItTemplate },
            { key: "canBeChangedByOthers", width: "150px", headerText: nts.uk.resource.getText('KDW002_7'), dataType: "bool", template: canBeChangedByOthersTemplate },
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

    //

}




