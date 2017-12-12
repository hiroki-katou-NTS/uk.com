module nts.uk.com.view.ccg026.component {
    import getText = nts.uk.resource.getText;

    export module viewmodel {
        export class ComponentModel {
            listFunctionPermissions: Array<model.FunctionPermission> = [];
            parameterInput: model.Option = new model.Option({
                roleId: ''
                , classification: 1
                , maxRow: 3
            });

            constructor(option: model.IOption) {
                let self = this,
                    parameterInput = self.parameterInput;

                parameterInput.roleId(option.roleId);
                parameterInput.classification = option.classification;
                parameterInput.maxRow = option.maxRow;
                parameterInput.roleId.subscribe((x) => {
                    // reset function avialability 
                    self.buildAvialabilityFunctionPermission().done(() => {
                    });
                });
            }

            /** functiton start page */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                // caculate height by row number
                var headerHeight: number = 23;
                var heigth: number = (self.parameterInput.maxRow) * 28 + headerHeight;
                $("#table-permission").ntsFixedTable({ height: heigth });
                $.when(self.getListOfFunctionPermission(), self.buildAvialabilityFunctionPermission()).done(() => {
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
                let self = this,
                    parameterInput = self.parameterInput,
                    listFunctionPermissions = self.listFunctionPermissions;
                let dfd = $.Deferred();

                service.getListOfDescriptionFunctionPermission(parameterInput.classification)
                    .done((dataDescriptions: Array<model.IFunctionPermission>) => {
                        dataDescriptions = _.orderBy(dataDescriptions, ['assignAtr', 'functionNo'], ['asc', 'asc']);
                        for (var i = 0, len = dataDescriptions.length; i < len; i++) {
                            self.listFunctionPermissions.push(new model.FunctionPermission
                                ({
                                    functionNo: dataDescriptions[i].functionNo,
                                    initialValue: dataDescriptions[i].initialValue,
                                    displayName: dataDescriptions[i].displayName,
                                    displayOrder: dataDescriptions[i].displayOrder,
                                    description: dataDescriptions[i].description,
                                    availability: (dataDescriptions[i].availability || false)
                                }));
                        }
                        dfd.resolve();
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
                let self = this,
                    parameterInput = self.parameterInput,
                    listFunctionPermissions = self.listFunctionPermissions;
                let dfd = $.Deferred();
                service.getListOfAviabilityFunctionPermission(parameterInput.roleId(), parameterInput.classification)
                    .done((dataAvailability: Array<model.IAvailabilityPermission>) => {
                        //process data
                        //filter get only function have availability permission
                        dataAvailability = dataAvailability.filter(item => item.availability);
                        //setting check for ListOfFunctionPermission and show
                        for (var i = 0, len = listFunctionPermissions.length; i < len; i++) {
                            var index = _.findIndex(dataAvailability, function(x: model.IAvailabilityPermission)
                            { return x.functionNo == listFunctionPermissions[i].functionNo });
                            var isAvailability: boolean = (index > -1);
                            listFunctionPermissions[i].availability(isAvailability || listFunctionPermissions[i].initialValue);
                        }
                        dfd.resolve();
                    }).fail(function(res: any) {
                        dfd.reject();
                        nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                    });
                return dfd.promise();
            }

        }//end componentModel
    }//end viewmodel

    //module model
    export module model {

        //Model Input parameter
        export interface IOption {
            roleId: string;
            classification: number;
            maxRow: number;
        }

        //Class Input parameter
        export class Option {
            roleId: KnockoutObservable<string> = ko.observable('');
            maxRow: number = 3;
            classification: number = 1; //1: work place

            constructor(param: IOption) {
                let self = this;
                self.roleId(param.roleId);
                self.maxRow = param.maxRow;
                self.classification = param.classification;
            }
        }

        //Model Function Permission
        export interface IFunctionPermission {
            functionNo: string;
            initialValue: boolean;
            displayName: string;
            displayOrder: number;
            description: string;
            availability: boolean;
        }

        //Class Function Permission
        export class FunctionPermission {
            functionNo: string;
            initialValue: boolean;
            displayName: string;
            displayOrder: number;
            description: string;
            availability: KnockoutObservable<boolean> = ko.observable(false);
            constructor(param: IFunctionPermission) {
                let self = this;
                self.functionNo = param.functionNo;
                self.initialValue = param.initialValue || false;
                self.displayName = param.displayName;
                self.displayOrder = param.displayOrder;
                self.description = param.description;
                self.availability(param.availability || self.initialValue);
            }

        }

        //Model Function Availability Permission
        export interface IAvailabilityPermission {
            functionNo: string;
            roleId: string;
            companyId: string;
            availability: boolean;
        }
    }//end module model
}//end module

