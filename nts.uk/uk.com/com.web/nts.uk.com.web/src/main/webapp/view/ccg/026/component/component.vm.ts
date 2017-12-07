module nts.uk.com.view.ccg026.a.component {
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
            listFunctionPermissions: KnockoutObservableArray<model.IDescriptionOfAvailabilityPermission> = ko.observableArray([]);
            //listAviableFunctionPermissions: KnockoutObservableArray<model.IAvailabilityPermission>;

            parameterInput: KnockoutObservable<Option> = ko.observable(new Option({
                                                                        roleId: ''
                                                                        ,classification: 0
                                                                        , maxRow: 1 
            }));

            constructor(option: IOption) {
                let self = this,
                    parameterInput = self.parameterInput();

                parameterInput.roleId(option.roleId);
                parameterInput.classification(option.classification);
                parameterInput.maxRow(option.maxRow);
                parameterInput.roleId.subscribe(() => {
                    self.getListOfFunctionPermission();
                });
                /*
                setting.classification.subscribe(() => {
                    self.getListOfFunctionPermission(self.setting.roleId, self.setting.classification);
                });
                */
            }

            /** functiton start page */
            startPage(): JQueryPromise<any> {
                let self = this,
                parameterInput = self.parameterInput();
                let dfd = $.Deferred();
                self.getListOfFunctionPermission().always(function() {
                    //build grid
                    self.bindPermissionGrid();
                    //load grid
                    self.loadPermissionGrid();
                    dfd.resolve();
                });
                return dfd.promise();
            }//end start page

            /** Get list Role by Type */
            getListOfFunctionPermission(): JQueryPromise<any> {
                let self = this,
                    parameterInput = self.parameterInput(),
                    listFunctionPermissions = self.listFunctionPermissions;
                let dfd = $.Deferred();

                self.listFunctionPermissions.removeAll();
                service.getListOfDescriptionFunctionPermission(parameterInput.roleId(), parameterInput.classification()).done((dataDescriptions: Array<model.IDescriptionOfAvailabilityPermission>) => {
                    dataDescriptions = _.orderBy(dataDescriptions, ['assignAtr', 'functionNo'], ['asc', 'asc']);
                    self.listFunctionPermissions(dataDescriptions);
                    service.getListOfAviabilityFunctionPermission(parameterInput.roleId(), parameterInput.classification()).done((dataAvailability: Array<model.IAvailabilityPermission>) => {
                        //process data
                        //filter get only function have avaible permission
                        dataAvailability = dataAvailability.filter(item => item.availability);
                       // self.listAviableFunctionPermissions(dataAvailability);
                       //setting check for ListOfFunctionPermission and show
                        //loop???
                        for (var i = 0, len = dataAvailability.length; i < len; i++) {
                            let index = _.findIndex(listFunctionPermissions(), function (x: model.IDescriptionOfAvailabilityPermission) 
                                    { return x.functionNo == dataAvailability[i].functionNo});
                            if (index > -1) {
                                self.listFunctionPermissions()[index].initialValue = true;
                            }
                          }

                        dfd.resolve(self.listFunctionPermissions);

                    }).fail(function(res: any) {
                        dfd.reject();
                        nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                    });
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }
        
            bindPermissionGrid() {
                let self = this,
                listFunctionPermissions = self.listFunctionPermissions();
                
                $('#useCheckAll').prop('checked', false);
                if (!nts.uk.util.isNullOrUndefined(listFunctionPermissions)) {

                    //var dstControls = _(listFunctionPermissions).concat(attdItems).groupBy('attendanceItemId').map(_.spread(_.assign)).value();
                    $("#permissionGrid").igGrid("dataSourceObject", _.sortBy(listFunctionPermissions, 'itemId')).igGrid("dataBind");
                    var dataSource = $('#permissionGrid').data('igGrid').dataSource;
                }
            }

            loadPermissionGrid() {
                let self = this;
                //load igrid
                var DailyServiceTypeControls = [];
                var useTemplate = "<input type='checkbox' {{if ${use} }} checked {{/if}} onclick='useChanged(this, ${itemId})' />";
                var useHeader = "<input type='checkbox' id = 'useCheckAll' onclick='useHeaderChanged(this)'/> ";
                if (self.setting.maxRow < 0 ) {
                    self.setting.maxRow = 0;
                }
                var gridHeight = $("#permissionGrid").rows[0].clientHeight * (self.setting.maxRow + 1);
                $("#permissionGrid").igGrid({
                    primaryKey: "attendanceItemId",
                    height: gridHeight,
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
                        { key: "functionNo",    dataType: "string",  hidden: true},
                        { key: "displayName",   width: "250px", headerText: nts.uk.resource.getText('CCG026_2'), dataType: "string" },
                        { key: "initialValue",  width: "100px", headerText: useHeader + nts.uk.resource.getText('CCG026_3'), dataType: "bool", template: useTemplate },
                        { key: "description",   width: "300px", headerText: nts.uk.resource.getText('CCG026_4'), dataType: "string" },
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
                                { columnKey: "initialValue", readOnly: true },
                                { columnKey: "description", readOnly: true },
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
            useChanged(element, rowId) {
                var value = $("#permissionGrid").igGrid("getCellValue", rowId, "use");
                if ($("#permissionGrid").igGridUpdating('isEditing')) {
                    $("#permissionGrid").igGridUpdating('endEdit', true);
                }
                $("#permissionGrid").igGridUpdating("setCellValue", rowId, "use", value != true);
            }

            useHeaderChanged(element) {
                var dataSource = $('#permissionGrid').data('igGrid').dataSource;
                //var filteredData = dataSource.transformedData('afterfilteringandpaging');
                var i;
                var l = dataSource.length;
                for (i = 0; i < l; i++) {
                    $("#permissionGrid").igGridUpdating("setCellValue", dataSource[i].itemId, "use", element.checked);
                }
            }
        }//end screenModel
    }//end viewmodel

    //module model
    export module model {

        export interface IDescriptionOfAvailabilityPermission {
            functionNo:     string;
            initialValue:   boolean;
            displayName:    string;
            displayOrder:   number;
            description:    string;
        }

        export class DescriptionOfAvailabilityPermission {
            functionNo:     string;
            initialValue:   boolean;
            displayName:    string;
            displayOrder:   number;
            description:    string;
            constructor(param: IDescriptionOfAvailabilityPermission) {
                let self = this;
                self.functionNo = param.functionNo;
                self.initialValue = param.initialValue;
                self.displayName = param.displayName;
                self.displayOrder = param.displayOrder;
                self.description = param.description;
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