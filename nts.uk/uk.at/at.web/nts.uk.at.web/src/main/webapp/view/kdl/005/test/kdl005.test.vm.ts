module kdl005.test {
    export module viewmodel {
        export class ScreenModel {
            employeeIds: KnockoutObservableArray<string> = ko.observableArray([]);
            baseDate: string;

            constructor(){
                var self = this;

                self.employeeIds.push("0455c7a6-e939-4ac1-a73d-38a944adf42d");
                self.employeeIds.push("05196408-e2b4-442b-927f-926ee4b7370c");
                self.employeeIds.push("09ccb197-07ad-4d90-88f0-5774636214b2");
                self.employeeIds.push("09eb10c7-0cea-4fc0-980e-5629ad57b5c0");
                self.employeeIds.push("0addaac4-7741-4f00-aab0-290d575a7e46");
                self.employeeIds.push("0cf97165-6f43-49af-a0ae-f0dc7c7590e0");
                self.employeeIds.push("118388db-23b6-486f-a65b-679831046b40");
                self.employeeIds.push("1611da7c-9219-4845-bb09-f918ceb19b02");
                self.employeeIds.push("18a38ad1-5a83-4fa0-84af-032885d965b9");
                self.employeeIds.push("1b554e21-72ca-4b98-88e8-ecc5bc6fcb1a");
                
                self.baseDate = "20180505";
            }
            
            openDialog() {
                var self = this;
                
                var param: IEmployeeParam = {
                    employeeIds: self.employeeIds(),
                    baseDate: self.baseDate
                };
                
                service.getEmployeeList(param).done(function(data: DataParam) {
                    nts.uk.ui.windows.setShared('KDL005_DATA', data);
                    
                    if(data.employeeBasicInfo.length > 1) {
                        nts.uk.ui.windows.sub.modal("/view/kdl/005/a/multi.xhtml");
                    } else {
                        nts.uk.ui.windows.sub.modal("/view/kdl/005/a/single.xhtml");
                    }
                });
            }
        }
        
        export class DataParam {
            employeeBasicInfo: Array<EmployeeBasicInfoDto>;
            baseDate: string;
            
            constructor(param: IDataParam) {
                var self = this;
                self.employeeBasicInfo = ko.observable(param.employeeBasicInfo);
                self.baseDate = ko.observable(param.baseDate);
            }
        }
        
        export interface IDataParam {
            employeeBasicInfo: Array<EmployeeBasicInfoDto>;
            baseDate: string;
        }
        
        export class EmployeeBasicInfoDto {
            personId: string;
            employeeId: string;
            businessName: string;
            gender: number;
            birthday: string;
            employeeCode: string;
            jobEntryDate: string;
            retirementDate: string;
            
            constructor(param: IEmployeeBasicInfoDto) {
                var self = this;
                self.personId = ko.observable(param.personId);
                self.employeeId = ko.observable(param.employeeId);
                self.businessName = ko.observable(param.businessName);
                self.gender = ko.observable(param.gender);
                self.birthday = ko.observable(param.birthday);
                self.employeeCode = ko.observable(param.employeeCode);
                self.jobEntryDate = ko.observable(param.jobEntryDate);
                self.retirementDate = ko.observable(param.retirementDate);
            }
        }
        
        export interface IEmployeeBasicInfoDto {
            personId: string;
            employeeId: string;
            businessName: string;
            gender: number;
            birthday: string;
            employeeCode: string;
            jobEntryDate: string;
            retirementDate: string;
        }
        
        export class EmployeeParam {
            employeeIds: Array<string>;
            baseDate: string;
            
            constructor(param: IEmployeeParam) {
                var self = this;
                self.employeeIds = ko.observable(param.employeeIds);
                self.baseDate = ko.observable(param.baseDate);
            }
        }
        
        export interface IEmployeeParam {
            employeeIds: Array<string>;
            baseDate: string;
        }
    }
}