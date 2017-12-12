module nts.uk.com.view.ccg026.component {
    import getText = nts.uk.resource.getText;

    export interface IOption {
        roleId: string;
        classification: number;
        maxRow: number;
    }

    export class Option {
        roleId: KnockoutObservable<string> = ko.observable('');
        maxRow: KnockoutObservable<number> = ko.observable(1);    
        classification: KnockoutObservable<number> = ko.observable(0);
        
        constructor(param: IOption) {
            let self = this;
            self.roleId(param.roleId);
            self.maxRow(param.maxRow);
            self.classification(param.classification);
        }
    }

    export module viewmodel {
        export class ComponentModel {
            listFunctionPermissions: Array<model.IDescriptionOfAvailabilityPermission> = [];

            parameterInput: KnockoutObservable<Option> = ko.observable(new Option({
                                                                        roleId: ''
                                                                        ,classification: 0
                                                                        , maxRow: 1 
            }));
            
            constructor(option: IOption) {
                var self = this,
                    parameterInput = self.parameterInput();

                parameterInput.roleId(option.roleId);
                parameterInput.classification(option.classification);
                parameterInput.maxRow(option.maxRow);

                parameterInput.roleId.subscribe((x) => {
                    //alert("Checked count 1: " + self.listFunctionPermissions.filter((item) => item.availability).length);
                    //alert("Checked count 2: " + $('#permissionGrid').data('igGrid').dataSource._data.filter((item) => item.availability).length);
                    self.buildAvialabilityFunctionPermission().done(() => {
                        //bind data into grid
                        self.bindPermissionGrid();
                    });
                });
            }

            /** functiton start page */
            startPage(): JQueryPromise<any> {
                var self = this,
                    parameterInput = self.parameterInput();

                var dfd = $.Deferred();

                //build grid
                self.loadPermissionGrid();

                $.when(self.getListOfFunctionPermission(), self.buildAvialabilityFunctionPermission())
                    .done(() => {
                        //bind data into grid
                        self.bindPermissionGrid();
                        //setting uncheck if function is default
                        self.setCheckBoxIsCanUse();
                        dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                });
                return dfd.promise();
            }//end start page

            /**
             * Get List Of Function Permission
             */
             private getListOfFunctionPermission(): JQueryPromise<any> {
                var self = this,
                    parameterInput = self.parameterInput(),
                    listFunctionPermissions = self.listFunctionPermissions;
                var dfd = $.Deferred();

                service.getListOfDescriptionFunctionPermission(parameterInput.classification())
                    .done((dataDescriptions: Array<model.IDescriptionOfAvailabilityPermission>) => {
                        dataDescriptions = _.orderBy(dataDescriptions, ['assignAtr', 'functionNo'], ['asc', 'asc']);
                        self.listFunctionPermissions = dataDescriptions;
                        dfd.resolve(self.listFunctionPermissions);
                }).fail(function(res: any) {
                    self.listFunctionPermissions = [];
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }

            /**
             * build list of FunctionPermission with avialability value
             */
            private buildAvialabilityFunctionPermission(): JQueryPromise<any> {
                var self = this,
                parameterInput = self.parameterInput(),
                    listFunctionPermissions = self.listFunctionPermissions;
                var dfd = $.Deferred();
                service.getListOfAviabilityFunctionPermission(parameterInput.roleId(), parameterInput.classification())
                    .done((dataAvailability: Array<model.IAvailabilityPermission>) => {
                    //process data
                    //filter get only function have avaible permission
                    dataAvailability = dataAvailability.filter(item => item.availability);
                   //setting check for ListOfFunctionPermission and show
                    for (var i = 0, len = listFunctionPermissions.length; i < len; i++) {
                        var index = _.findIndex(dataAvailability, function (x: model.IAvailabilityPermission) 
                                { return x.functionNo == listFunctionPermissions[i].functionNo});
                        var isAvailability : boolean = (index > -1);
                        listFunctionPermissions[i].availability =  isAvailability || listFunctionPermissions[i].initialValue;
                    }
                    dfd.resolve(listFunctionPermissions);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }

            /**
             * bindPermissionGrid
             */
            private bindPermissionGrid() {
                var self = this,
                listFunctionPermissions = self.listFunctionPermissions;
                $('#useCheckAll').prop('checked', false);
                if (!nts.uk.util.isNullOrUndefined(listFunctionPermissions)) {
                    listFunctionPermissions = _(listFunctionPermissions).groupBy('functionNo').map(_.spread(_.assign)).value();
                    $("#permissionGrid").igGrid("dataSourceObject", _.sortBy(listFunctionPermissions, 'functionNo')).igGrid("dataBind");
                }
            }

            /**
             * Run first time after bind data
             */
            private setCheckBoxIsCanUse() {
                var dataSource = $('#permissionGrid').data('igGrid').dataSource;
                var filteredData = dataSource.transformedData('afterfilteringandpaging');
                var i;
                var l = filteredData.length;
                for (i = 0; i < l; i++) {
                    if (filteredData[i].initialValue) {
                        var cellYouCanChangeIt = $('#permissionGrid').igGrid('cellAt', 1, i);
                        cellYouCanChangeIt.classList.add('readOnlyColorIsUse');
                    }
                }

            }
            /**
             * loadPermissionGrid
             */
            private loadPermissionGrid() {
                var self = this;
                //load igrid
                var DailyServiceTypeControls = [];
                var availabilityTemplate = "<input type='checkbox' {{if ${availability} }} checked {{/if}} onclick='avialabilityChanged(this, ${functionNo})' />";
                var availabilityHeader = "<input type='checkbox' id = 'avialabilityCheckAll' onclick='avialabilityHeaderChanged(this)'/> ";
                if (self.parameterInput.maxRow < 0 ) {
                    self.parameterInput.maxRow = 0;
                }

                $("#permissionGrid").igGrid({
                    primaryKey: "functionNo",
                    height: 300,
                    dataSource: self.listFunctionPermissions,
                    autoGenerateColumns: false,
                    alternateRowStyles: false,
                    dataSourceType: "json",
                    autoCommit: true,
                    // virtualization: true,
                    rowVirtualization: false,
                    // virtualizationMode: "continuous",
                    virtualizationMode: "fixed",
                    columns: [
                        { key: "functionNo",    dataType: "string",  hidden: true},
                        { key: "displayName",   width: "250px", headerText: nts.uk.resource.getText('CCG026_2'), dataType: "string", formatter: _.escape },
                        { key: "availability",  width: "100px", headerText: availabilityHeader + nts.uk.resource.getText('CCG026_3'), dataType: "bool", template: availabilityTemplate },
                        { key: "description",   width: "300px", headerText: nts.uk.resource.getText('CCG026_4'), dataType: "string", formatter: _.escape },
                        { key: "initialValue",    dataType: "bool",  hidden: true}
                    ],
                    features: [
                        {
                            name: "Updating",
                            showDoneCancelButtons: false,
                            enableAddRow: false,
                            enableDeleteRow: false,
                            editMode: 'cell',
                            columnSettings: [
                                { columnKey: "functionNo", hidden: true },
                                { columnKey: "displayName", readOnly: true },
                                { columnKey: "availability", readOnly: true },
                                { columnKey: "description", readOnly: true },
                                { columnKey: "initialValue", hidden: true }
                                
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
                var gridHeight = $("#permissionGrid tbody tr").height()  * (self.parameterInput.maxRow + 1);
                $("#permissionGrid").height = gridHeight;
            }
            
        }//end componentModel
    }//end viewmodel



    //module model
    export module model {

        export interface IDescriptionOfAvailabilityPermission {
            functionNo:     string;
            initialValue:   boolean;
            displayName:    string;
            displayOrder:   number;
            description:    string;
            availability:   boolean;
        }

        export class DescriptionOfAvailabilityPermission {
            functionNo:     string;
            initialValue:   boolean;
            displayName:    string;
            displayOrder:   number;
            description:    string;
            availability:   boolean;
            constructor(param: IDescriptionOfAvailabilityPermission) {
                var self = this;
                self.functionNo     = param.functionNo;
                self.initialValue   = param.initialValue;
                self.displayName    = param.displayName;
                self.displayOrder   = param.displayOrder;
                self.description    = param.description;
                self.availability   = param.availability || false;
            }
        }

        export interface IAvailabilityPermission {
            functionNo: string;
            roleId:     string;
            companyId:  string;
            availability: boolean;
        }

        export class AvailabilityPermission {
            companyId:      string;
            roleId:         string;
            functionNo:     string;
            availability:   boolean;
            constructor(param: IAvailabilityPermission) {
                let self = this;
                self.companyId  = param.companyId;
                self.roleId     = param.roleId;
                self.functionNo = param.functionNo;
                self.availability = param.availability;
            }
        }
    }//end module model

}//end module

/**
 * process when click on avialability check box header
 * @param element
 */
function avialabilityHeaderChanged(element) {
    var dataSource = $('#permissionGrid').data('igGrid').dataSource;
    if (!dataSource || !dataSource._data) {
        return;
    }
    var i;
    var l = dataSource._data.length;
    for (i = 0; i < l; i++) {
        $("#permissionGrid").igGridUpdating("setCellValue", dataSource._data[i].functionNo
                , "availability", dataSource._data[i].initialValue || element.checked);
    }
}

/**
 * process when click on avialability check box
 * @param element
 * @param rowId
 */
function avialabilityChanged(element, rowId) {

    var dataSource = $('#permissionGrid').data('igGrid').dataSource;
    if (!dataSource || !dataSource._data || rowId < 1 || rowId > dataSource._data.length) {
        return;
    }
    var value = $("#permissionGrid").igGrid("getCellValue", dataSource._data[rowId-1].functionNo, "availability");
    if ($("#permissionGrid").igGridUpdating('isEditing')) {
        $("#permissionGrid").igGridUpdating('endEdit', true);
    }
    $("#permissionGrid").igGridUpdating("setCellValue", dataSource._data[rowId-1].functionNo, "availability", dataSource._data[rowId - 1].initialValue || value != true);
    
    if (value) {
        $("#avialabilityCheckAll").attr('checked', false);
        return;
    }
    dataSource = $('#permissionGrid').data('igGrid').dataSource;
    if (!dataSource || !dataSource._data) {
        return;
    }
    var i;
    var l = dataSource._data.length;
    var isAllChecked = true;
    for (i = 0; i < l; i++) {
        if (!$("#permissionGrid").igGrid("getCellValue", dataSource._data[i].functionNo, "availability")) {
            isAllChecked = false;
            break;
        }
    }
    $("#avialabilityCheckAll").attr('checked', isAllChecked);
}
