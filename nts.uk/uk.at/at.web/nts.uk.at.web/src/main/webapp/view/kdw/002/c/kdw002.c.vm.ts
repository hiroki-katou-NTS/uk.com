module nts.uk.at.view.kdw002.c {
    import alert = nts.uk.ui.dialog.alert;
    export module viewmodel {
        import href = nts.uk.request.jump;
        import getText = nts.uk.resource.getText;
        import infor = nts.uk.ui.dialog.info;
        export class ScreenModel {
            listAttdItem: Array<any>;
            listDailyServiceTypeControl : KnockoutObservableArray<any>; 
            
            
            bussinessCodeItems: KnockoutObservableArray<BusinessType>;
            bussinessColumn: KnockoutObservableArray<any>;
            currentRoleId: KnockoutObservable<any>;
            txtSearch: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.bussinessCodeItems = ko.observableArray([]);
                self.listAttdItem = [];
                self.listDailyServiceTypeControl= ko.observableArray([]);

                this.bussinessColumn = ko.observableArray([
                    { headerText: 'ID', key: 'roleId', width: 100, hidden: true },
                    { headerText: getText('KDW002_12'), key: 'roleCode', width: 100 },
                    { headerText: getText('KDW002_4'), key: 'roleName', width: 150, formatter: _.escape },
                ]);

                self.currentRoleId = ko.observable('');
                self.currentRoleId.subscribe(roleId => {
                    self.currentRoleId(roleId);
                    _.defer(() => nts.uk.ui.block.invisible());
                    
                    let dfdGetAttendanceItems = self.getAttendanceItems();
                    let dfdGetDailyServiceTypeControl = self.getDailyAttdItemByRoleID(roleId);
                    
                    $.when(dfdGetAttendanceItems,dfdGetDailyServiceTypeControl).done(
                        (DailyServiceTypeControls, attendanceItems) => {
                            $('#useCheckAll').prop('checked', false);
                            $('#youCanCheckAll').prop('checked', false);
                            $('#otherCheckAll').prop('checked', false);
//                            if (!nts.uk.util.isNullOrUndefined(DailyServiceTypeControls) && !nts.uk.util.isNullOrUndefined(attendanceItems)) {
//                                var attdItems = _.map(attendanceItems, function(x) { return _.pick(x, ['attendanceItemId', 'attendanceItemName']) });
//                                var dstControls = _(DailyServiceTypeControls).concat(attdItems).groupBy('attendanceItemId').map(_.spread(_.assign)).value();
//                                $("#grid").igGrid("dataSourceObject", _.sortBy(dstControls, 'attendanceItemId')).igGrid("dataBind");
//                                var dataSource = $('#grid').data('igGrid').dataSource;
//                                var filteredData = dataSource.transformedData('afterfilteringandpaging');
//                                var i;
//                                var l = filteredData.length;
//                                for (i = 0; i < l; i++) {
//                                    if (!filteredData[i].userCanSet || !filteredData[i].use) {
//                                        if (!filteredData[i].userCanSet) {
//                                            $("#grid").igGridUpdating("setCellValue", filteredData[i].attendanceItemId, "canBeChangedByOthers", false);
//                                        }
//                                        var rowId = filteredData[i].attendanceItemId;
//                                        var cellYouCanChangeIt = $("#grid").igGrid("cellById", rowId, "youCanChangeIt");
//                                        var cellCanBeChangedByOthers = $("#grid").igGrid("cellById", rowId, "canBeChangedByOthers");
//                                        cellYouCanChangeIt.addClass('readOnlyColorIsUse');
//                                        cellCanBeChangedByOthers.addClass('readOnlyColorIsUse');
//                                        cellYouCanChangeIt.children().prop("disabled", true);
//                                        cellCanBeChangedByOthers.children().prop("disabled", true);
//
//
//                                    }
//                                }
//                            }
                            nts.uk.ui.block.clear();
                        }
                    );
                });

                //loadIgrid();

                service.getEmpRole().done(empRoles => {
                    if (!nts.uk.util.isNullOrUndefined(empRoles) && empRoles.length > 0) {
                        let bussinessCodeItems = [];
                        empRoles.forEach(empRole => {
                            bussinessCodeItems.push(new BusinessType(empRole));
                            //   self.bussinessCodeItems.push(new BusinessType(businessType));
                        });
                        var bTypes = _.sortBy(bussinessCodeItems, 'roleCode');
                        self.bussinessCodeItems(bTypes);
                        var businessTypeCode = bTypes[0].roleId;
                        self.currentRoleId(businessTypeCode);
                    }
                });
                self.txtSearch = ko.observable("");
            }
            /*
            copy(): void {
                var self = this;
                var bussinessCodeItems = self.bussinessCodeItems();
                var currentRoleId = self.currentRoleId();
                var bussinessCodeItem = _.find(self.bussinessCodeItems(), { businessTypeCode: self.currentRoleId() });
                var businessTypeName = bussinessCodeItem.roleName;
                var data = {
                    code: currentRoleId, name: businessTypeName, bussinessItems: bussinessCodeItems
                }
                nts.uk.ui.windows.setShared("KDW002_B_AUTHORITYTYPE", data);
            }
            */


            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.getAttendanceItems();
                dfd.resolve();
                return dfd.promise();
            }
            
            getAttendanceItems(){
                let self = this;
                let dfd = $.Deferred();
                service.getAttendanceItems().done(function(data){
                    self.listAttdItem = data;
                    dfd.resolve();        
                });
                return dfd.promise();
            }
            
            getDailyAttdItemByRoleID(roleID:string){
                let self = this;
                let dfd = $.Deferred();
                service.getDailyAttdItemByRoleID(roleID).done(function(data){
                    self.listDailyServiceTypeControl(data);
                    dfd.resolve();        
                });
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
                        authorityId: self.currentRoleId(),
                        youCanChangeIt: dstc.youCanChangeIt,
                        canBeChangedByOthers: dstc.canBeChangedByOthers,
                        use: dstc.use
                    }));
                });
                nts.uk.ui.block.invisible();
                service.updateDailyService(DailyServiceTypeControls).done(x => {
                    infor(nts.uk.resource.getMessage("Msg_15", []));
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

            navigateView(): void {
                let self = this;
                var path = "/view/kdw/006/a/index.xhtml";
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
                var i;
                var l = filteredData.length;
                for (i = index; i < l; i++) {
                    var rowId = filteredData[i].attendanceItemId;
                    var nameValue = filteredData[i].attendanceItemName;
                    var idValue = rowId.toString();

                    if (_.includes(idValue, self.txtSearch()) || _.includes(nameValue, self.txtSearch())) {
                        $('#grid').igGridSelection('selectRow', i);
                        $('#grid').igGrid("virtualScrollTo", i);
                        keynotExist = false;
                        break;
                    }
                }
                if (keynotExist) {
                    for (i = 0; i < index; i++) {
                        var rowId = filteredData[i].attendanceItemId;
                        var nameValue = filteredData[i].attendanceItemName;
                        var idValue = rowId.toString();
                        if (_.includes(idValue, self.txtSearch()) || _.includes(nameValue, self.txtSearch())) {
                            $('#grid').igGridSelection('selectRow', i);
                            $('#grid').igGrid("virtualScrollTo", i);
                            keynotExist = false;
                            break;
                        }
                    }
                }
                if (keynotExist) {
                    alert("該当する項目が見つかりませんでした");
                }
            }
        }

        interface IBusinessType {
            roleId: string;
            roleName: string;
        }

        class BusinessType {
            roleId: string;
            roleCode: string;
            roleName: string;
            constructor(param: IBusinessType) {
                let self = this;
                self.roleId = param.roleId;
                self.roleCode = param.roleCode;
                self.roleName = param.roleName;
            }
        }
        interface IDailyServiceTypeControl {
            attendanceItemId: number;
            authorityId: string;
            youCanChangeIt: boolean;
            canBeChangedByOthers: boolean;
            use: boolean
        }

        class DailyServiceTypeControl {
            attendanceItemId: number;
            authorityId: string;
            youCanChangeIt: boolean;
            canBeChangedByOthers: boolean;
            use: boolean
            constructor(param: IDailyServiceTypeControl) {
                let self = this;
                self.attendanceItemId = param.attendanceItemId;
                self.authorityId = param.authorityId;
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

    // var rowIndex = $('#grid').igGrid( "getVisibleIndexByKey","2") ;
    // var rowIndex = $('#grid').igGrid("getVisibleIndexByKey", "attendanceItemId");
    //  var row = $('#grid').igGrid("rowById", rowId);
    var cellYouCanChangeIt = $("#grid").igGrid("cellById", rowId, "youCanChangeIt");
    var cellCanBeChangedByOthers = $("#grid").igGrid("cellById", rowId, "canBeChangedByOthers");
    if (!(value != true) && $("#grid").igGrid("getCellValue", rowId, "userCanSet")) {

        cellYouCanChangeIt.addClass('readOnlyColorIsUse');
        cellCanBeChangedByOthers.addClass('readOnlyColorIsUse');
        cellYouCanChangeIt.children().prop("disabled", true);
        cellCanBeChangedByOthers.children().prop("disabled", true);
    } else if (value != true && $("#grid").igGrid("getCellValue", rowId, "userCanSet")) {
        cellYouCanChangeIt.removeClass('readOnlyColorIsUse');
        cellCanBeChangedByOthers.removeClass('readOnlyColorIsUse');
        cellYouCanChangeIt.children().prop("disabled", false);
        cellCanBeChangedByOthers.children().prop("disabled", false);
    } else {
        cellYouCanChangeIt.children().prop("disabled", true);
        cellCanBeChangedByOthers.children().prop("disabled", true);
    }
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

function userCanSetChanged(element, rowId) {
    var value = $("#grid").igGrid("getCellValue", rowId, "userCanSet");
    if ($("#grid").igGridUpdating('isEditing')) {
        $("#grid").igGridUpdating('endEdit', true);
    }
    $("#grid").igGridUpdating("setCellValue", rowId, "userCanSet", value != true);
}

function useHeaderChanged(element) {
    var dataSource = $('#grid').data('igGrid').dataSource;
    var filteredData = dataSource.transformedData('afterfilteringandpaging');
    if (element.checked) {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            $("#grid").igGridUpdating("setCellValue", filteredData[i].attendanceItemId, "use", true);
            var rowId = filteredData[i].attendanceItemId;
            var cellYouCanChangeIt = $("#grid").igGrid("cellById", rowId, "youCanChangeIt");
            var cellCanBeChangedByOthers = $("#grid").igGrid("cellById", rowId, "canBeChangedByOthers");
            if (filteredData[i].userCanSet) {
                if (cellYouCanChangeIt.hasClass('readOnlyColorIsUse')) {
                    cellYouCanChangeIt.removeClass('readOnlyColorIsUse');
                }
                if (cellCanBeChangedByOthers.hasClass('readOnlyColorIsUse')) {
                    cellCanBeChangedByOthers.removeClass('readOnlyColorIsUse');
                }
                cellYouCanChangeIt.children().prop("disabled", false);
                cellCanBeChangedByOthers.children().prop("disabled", false);
            } else {
                cellYouCanChangeIt.children().prop("disabled", true);
                cellCanBeChangedByOthers.children().prop("disabled", true);
            }
        }

    } else {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            $("#grid").igGridUpdating("setCellValue", filteredData[i].attendanceItemId, "use", false);
            var rowId = filteredData[i].attendanceItemId;
            var cellYouCanChangeIt = $("#grid").igGrid("cellById", rowId, "youCanChangeIt");
            var cellCanBeChangedByOthers = $("#grid").igGrid("cellById", rowId, "canBeChangedByOthers");
            if (filteredData[i].userCanSet) {
                if (!cellYouCanChangeIt.hasClass('readOnlyColorIsUse')) {
                    cellYouCanChangeIt.addClass('readOnlyColorIsUse');
                }
                if (!cellCanBeChangedByOthers.hasClass('readOnlyColorIsUse')) {
                    cellCanBeChangedByOthers.addClass('readOnlyColorIsUse');
                }
                cellYouCanChangeIt.children().prop("disabled", true);
                cellCanBeChangedByOthers.children().prop("disabled", true);
            } else {
                cellYouCanChangeIt.children().prop("disabled", true);
                cellCanBeChangedByOthers.children().prop("disabled", true);
            }
        }

    }
}


function youCanChangeItHeaderChanged(element) {
    var dataSource = $('#grid').data('igGrid').dataSource;
    var filteredData = dataSource.transformedData('afterfilteringandpaging');
    if (element.checked) {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            if (filteredData[i].userCanSet && filteredData[i].use) {
                $("#grid").igGridUpdating("setCellValue", filteredData[i].attendanceItemId, "youCanChangeIt", true);
            }
        }
    } else {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            if (filteredData[i].userCanSet && filteredData[i].use) {
                $("#grid").igGridUpdating("setCellValue", filteredData[i].attendanceItemId, "youCanChangeIt", false);
            }
        }
    }

}

function canBeChangedByOthersHeaderChanged(element) {
    var dataSource = $('#grid').data('igGrid').dataSource;
    var filteredData = dataSource.transformedData('afterfilteringandpaging');
    if (element.checked) {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            if (filteredData[i].userCanSet && filteredData[i].use) {
                $("#grid").igGridUpdating("setCellValue", filteredData[i].attendanceItemId, "canBeChangedByOthers", true);
            }
        }
    } else {
        var i;
        var l = filteredData.length;
        for (i = 0; i < l; i++) {
            if (filteredData[i].userCanSet && filteredData[i].use) {
                $("#grid").igGridUpdating("setCellValue", filteredData[i].attendanceItemId, "canBeChangedByOthers", false);
            }
        }

    }
}

function loadIgrid() {
    //load igrid
    var DailyServiceTypeControls = [];
    var useTemplate = "<input type='checkbox' {{if ${use} }} checked {{/if}} onclick='useChanged(this, ${attendanceItemId})' />";
    var youCanChangeItTemplate = "<input type='checkbox' {{if ${youCanChangeIt} }} checked {{/if}} onclick='youCanChangeItChanged(this, ${attendanceItemId})' />";
    var canBeChangedByOthersTemplate = "<input type='checkbox' {{if ${canBeChangedByOthers} }} checked {{/if}} onclick='canBeChangedByOthersChanged(this, ${attendanceItemId})' />";
    var useHeader = "<input type='checkbox' id = 'useCheckAll' onclick='useHeaderChanged(this)'/> ";
    var youCanChangeItHeader = "<input type='checkbox' id = 'youCanCheckAll' onclick='youCanChangeItHeaderChanged(this)'/> ";
    var canBeChangedByOthersHeader = "<input type='checkbox' id = 'otherCheckAll' onclick='canBeChangedByOthersHeaderChanged(this)'/> ";
    $("#grid").igGrid({
        primaryKey: "attendanceItemId",
        height: 400,
        dataSource: DailyServiceTypeControls,
        autoGenerateColumns: false,
        alternateRowStyles: false,
        dataSourceType: "json",
        autoCommit: true,
        // virtualization: true,
        rowVirtualization: false,
        // virtualizationMode: "continuous",
        virtualizationMode: "fixed",
        columns: [
            { key: "attendanceItemId", width: "50px", headerText: nts.uk.resource.getText('KDW002_3'), dataType: "number" },
            { key: "attendanceItemName", width: "250px", headerText: nts.uk.resource.getText('KDW002_4'), dataType: "string" },
            { key: "use", width: "100px", headerText: useHeader + nts.uk.resource.getText('KDW002_5'), dataType: "bool", template: useTemplate },
            { key: "youCanChangeIt", width: "120px", headerText: youCanChangeItHeader + nts.uk.resource.getText('KDW002_6'), dataType: "bool", template: youCanChangeItTemplate },
            { key: "canBeChangedByOthers", width: "145px", headerText: canBeChangedByOthersHeader + nts.uk.resource.getText('KDW002_7'), dataType: "bool", template: canBeChangedByOthersTemplate },
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
                    { columnKey: "userCanSet", hidden: true }
                    //, allowHiding: false,
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
}