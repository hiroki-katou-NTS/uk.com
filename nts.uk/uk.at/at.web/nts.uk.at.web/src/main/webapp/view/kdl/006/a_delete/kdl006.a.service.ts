module nts.uk.at.view.kdl006.a {
    
    export module service {
            
        /**
         *  Service paths
         */
        var servicePath: any = {
             findCurrentPersonName: "at/record/workfixed/currentPerson",
             findCurrentClosure: "ctx/at/shared/workrule/closure/findCurrentClosure",             
             findWorkplaceInfo:"at/record/workfixed/findWkpInfo",
             //findWorkplaceInfo:"bs/employee/workplace/info/findWorkplaceInfo",
             findWorkFixedInfo: "at/record/workfixed/find",
             saveWorkFixedInfo: "at/record/workfixed/save",
        }
        
        /**
         * findCurrentPersonName
         */
        export function findCurrentPersonName(): JQueryPromise<model.PersonInfo> {
            return nts.uk.request.ajax(servicePath.findCurrentPersonName);
        }
        
        /**
         * findClosureNameAndPeriod
         */
        export function findCurrentClosure(): JQueryPromise<model.Closure[]> {
            return nts.uk.request.ajax(servicePath.findCurrentClosure);
        }
        
        /**
         * findWorkplaceInfo
         */
        export function findWorkplaceInfo(): JQueryPromise<model.WorkplaceInfo[]> {
            return nts.uk.request.ajax(servicePath.findWorkplaceInfo);
            //return nts.uk.request.ajax('com', servicePath.findWorkplaceInfo, {baseDate : new Date()});
        }
        
        /**
         * findWorkFixed
         */
        export function findWorkFixedInfo(listWorkFixed: model.WorkFixed[]): JQueryPromise<model.WorkFixed[]> {
            return nts.uk.request.ajax(servicePath.findWorkFixedInfo, listWorkFixed);           
        }    
        
        /**
         * saveWorkFixedInfo
         */
        export function saveWorkFixedInfo(listWorkFixed: model.SaveWorkFixedCommand[]): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveWorkFixedInfo, listWorkFixed);
        }
        
        /**
         * Model namespace.
         */
        export module model {
            
            // Data class
            export class Closure {
                closureId: number;
                startDate: string;
                endDate: string;
                closureName: string;
                processingDate: number;
                viewText: string;              
            }     
            
            export class WorkplaceInfo {               
                workplaceId: string;
                workplaceCode: string;
                workplaceName: string;
                viewText: string;    

                listWorkFixed: WorkFixed[];                
            }   
            
            export class WorkFixed {                          
                closureId: number;
                confirmPid: string;               
                wkpId: string;
                confirmClsStatus: number;
                fixedDate: string;
                processDate: number;
                employeeName: string;
                
                // State
                isEdited: boolean;
                
                // CheckBox
                checked: KnockoutObservable<boolean>;
                text: KnockoutObservable<string>;
                
                constructor (isEdited: boolean, closureId: number, wkpId: string) {
                    this.isEdited = isEdited;
                    this.closureId = closureId;
                    this.wkpId = wkpId;                   
                }
            }
            
            export class PersonInfo {
                employeeId: string;
                employeeName: string;
                
                constructor (employeeId: string, employeeName: string) {
                    this.employeeId = employeeId;
                    this.employeeName = employeeName;                   
                }
            }
            
            export enum ConfirmClsStatus {
                PENDING = 0,
                CONFIRM
            }
            
            export class SaveWorkFixedCommand {       
                isEdited: boolean;         
                closureId: number;              
                wkpId: string;
                confirmClsStatus: number;
                processDate: number;
                
                constructor (isEdited: boolean, closureId: number, wkpId: string, confirmClsStatus: number, processDate: number) {
                    this.isEdited = isEdited;
                    this.closureId = closureId;
                    this.wkpId = wkpId;
                    this.confirmClsStatus = confirmClsStatus;
                    this.processDate = processDate;
                }
            }
            
            // UI class
            export class CheckBoxItem {               
                checked: KnockoutObservable<boolean>;
                visible: KnockoutObservable<boolean>;
                text: KnockoutObservable<string>;
                
                constructor() {                                   
                    this.checked = ko.observable(null);
                    this.visible = ko.observable(null);
                    this.text = ko.observable(null);
                }
                
                updateData(checked: boolean, visible: boolean, text: string) {
                    this.checked(checked);
                    this.visible(visible);
                    this.text(text);
                }
            }         
        }
    }
}  