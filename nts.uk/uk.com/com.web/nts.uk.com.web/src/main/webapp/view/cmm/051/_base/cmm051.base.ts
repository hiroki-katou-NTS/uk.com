module nts.uk.com.view.cmm051 {
    export module base {
        export interface IWorkplaceManager {
            wkpId: string;
            employeeId: string;
            startDate: string;
            endDate: string;
            wkpManagerId?: string;
            employeeInfo: KnockoutObservable<IEmployee> = ko.observable();
            roleList : Array<IRole>;
            nodeText : string;
        }
        
        export class WorkplaceManager {
            wkpId: string;
            employeeId: string;
            startDate: string;
            endDate: string;
            wkpManagerId?: string;
            employeeInfo: KnockoutObservable<IEmployee> = ko.observable();
            roleList : KnockoutObservableArray<IRole> = ko.observableArray([]);
            nodeText : string;
            constructor(x: IWorkplaceManager) {
                let self = this;
                if (x) {
                    self.wkpId = x.wkpId == '' ? '' : x.wkpId;
                    self.employeeId = x.employeeId == '' ? '' : x.employeeId;
                    self.startDate = x.startDate == '' ? '' : x.startDate;
                    self.endDate = x.endDate == '' ? '' : x.endDate;
                    self.wkpManagerId = x.wkpManagerId == '' ? '' : x.wkpManagerId;
                    self.employeeInfo(x.employeeInfo || {});
                    self.roleList(x.roleList || []);
                    self.nodeText = x.startDate + ' ～ ' + x.endDate;
                } else {
                    self.wkpId = '';
                    self.employeeId = '';
                    self.startDate = '';
                    self.endDate = '';
                    self.wkpManagerId = '';
                    self.employeeInfo({});
                    self.roleList = [];
                    self.nodeText = '';
                }
            }
        }
        
        export interface IEmployee {
            employeeCode: string;
            namePerson: string;
        }
        
        export interface IRole {
            roleId: string;
            companyId: string;
            functionNo: number;
            availability: boolean;
        }
        
        export interface IFunction {
            functionNo: number;
            initialValue: boolean;
            displayName: string;
            displayOrder: number;
            description: string;
            availability: boolean;
        }
        
        export class Function {
            functionNo: number;
            initialValue: boolean;
            displayName: string;
            displayOrder: number;
            description: string;
            availability: boolean;
            
            constructor(x: IFunction) {
                let self = this;
                if (x) {
                    self.functionNo = x.functionNo == null ? null : x.functionNo;
                    self.initialValue = x.initialValue;
                    self.displayName = x.displayName == '' ? '' : x.displayName;
                    self.displayOrder = x.displayOrder == null ? null : x.displayOrder;
                    self.description = x.description == '' ? '' : x.description;
                    self.availability = x.availability;
                } else {
                    self.functionNo = null;
                    self.initialValue = false;
                    self.displayName = '';
                    self.displayOrder = null;
                    self.description = '';
                    self.availability = false;
                }
            }
        }
    }
}