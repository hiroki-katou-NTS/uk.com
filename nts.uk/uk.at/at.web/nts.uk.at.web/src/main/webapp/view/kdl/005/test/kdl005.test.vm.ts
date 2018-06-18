module kdl005.test {
    export module viewmodel {
        export class ScreenModel {
            employeeId: KnockoutObservableArray<string> = ko.observableArray([]);
            employeeIds: KnockoutObservableArray<string> = ko.observableArray([]);
            baseDate: string;

            constructor(){
                var self = this;

                self.employeeId.push("e4123168-0760-44e6-bebd-f68428825615");
                
                self.employeeIds.push("11ade2f5-8eda-4f31-bb68-0b14245b9f2c");
                self.employeeIds.push("a97641de-f09c-4bb7-9749-3d9e89c6b441");
                self.employeeIds.push("0314af45-4d46-457f-8b08-7ad96663e340");
                self.employeeIds.push("0455c7a6-e939-4ac1-a73d-38a944adf42d");
                self.employeeIds.push("e8eb1bdc-2397-4346-8346-812fbff139d4");
                self.employeeIds.push("64074868-9de1-4cf1-a220-68029816e651");
                self.employeeIds.push("43fbef63-0528-456f-912d-f7cfcb9aeb7c");
                self.employeeIds.push("cef59bb8-3aa2-4e1c-a4b3-0e8f25ea3269");
                self.employeeIds.push("26e81903-a2c6-4551-8b42-4fae8f01d67c");
                self.employeeIds.push("c3b8b9fe-f761-46e9-b469-1da745836d39");
                
                self.baseDate = "20160505";
            }
            
            openListDialog() {
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
            
            openSingleDialog() {
                var self = this;
                
                var param: IEmployeeParam = {
                    employeeIds: self.employeeId(),
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