module nts.uk.at.view.kdl006.a {
    
    export module service {
            
        /**
         *  Service paths
         */
        var servicePath: any = {
             findCurrentClosure: "ctx/at/shared/workrule/closure/findCurrentClosure",                    
             findWorkplaceInfo:"bs/employee/workplace/info/findWorkplaceInfo",
             findWorkFixedByWkpIdAndClosureId:"at/record/workfixed/findWorkFixed",
             findWorkFixedInfo: "at/record/workfixed/find",
             saveWorkFixedInfo: "at/record/workfixed/save",
        }
    
        /**
         * findClosureNameAndPeriod
         */
        export function findCurrentClosure(): JQueryPromise<any[]> {
            return nts.uk.request.ajax(servicePath.findCurrentClosure);
        }
        
        /**
         * findWorkplaceInfo
         */
        export function findWorkplaceInfo(baseDate: Date): JQueryPromise<any[]> {
            return nts.uk.request.ajax('com', servicePath.findWorkplaceInfo, {baseDate : baseDate});
        }
        
        /**
         * findWorkFixedByWkpIdAndClosureId
         */
        export function findWorkFixedByWkpIdAndClosureId(workplaceId: string, closureId: number): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findWorkFixedByWkpIdAndClosureId, {
                closureId: closureId,
                wkpId: workplaceId
            });
        }
        
        /**
         * findWorkFixed
         */
        export function findWorkFixedInfo(listWorkFixed: model.WorkFixedDto[]): JQueryPromise<any[]> {
            return nts.uk.request.ajax(servicePath.findWorkFixedInfo, listWorkFixed);
        }    
        
        /**
         * saveWorkFixedInfo
         */
        export function saveWorkFixedInfo(listWorkFixed: model.WorkFixedCommand[]): JQueryPromise<any[]> {
            return nts.uk.request.ajax(servicePath.saveWorkFixedInfo, listWorkFixed);
        }
        
        /**
         * Model namespace.
         */
        export module model {
            
            export class CurrentClosure {

                /** The closure id. */
                closureId: number;
                             
                /** The start date. */
                startDate: string;

                /** The end date. */
                endDate: string;

                /** The closure name. */
                closureName: string;

                /** The view text */
                viewText: string;              
            }     
            
            export class WorkplaceDto {
                
                /** The work place id. */
                workplaceId: string;
                
                /** The work place code. */
                workplaceCode: string;
                
                /** The work place name. */
                workplaceName: string;
                    
                /** The list work fixed dto. */
                workFixedDto: WorkFixedDto[];
                
                /** The view text. */
                viewText: string;
                
                // Columns
                columnText1: string;
                columnCheck1: boolean;
                
                columnText2: string;
                columnCheck2: boolean;
                
                columnText3: string;
                columnCheck3: boolean;
                
                columnText4: string;
                columnCheck4: boolean;
                
                columnText5: string;
                columnCheck5: boolean;
            }   
            
            export class WorkFixedDto {
                
                /** The closure id. */
                closureId: number;
                
                /** The confirm id. */
                confirmPid: string;
                
                /** The work place id. */
                wkpId: string;
                
                /** The confirm closure status. */
                confirmClsStatus: number;
                
                /** The fixed date. */
                fixedDate: string;
                
                /** The process date. */
                processDate: number;
                
                constructor (closureId: number, wkpId: string) {
                    this.closureId = closureId;
                    this.wkpId = wkpId;
                }
            }
            
            export class WorkFixedCommand {
                
                isSave: boolean;
                
                /** The closure id. */
                closureId: number;
                
                /** The work place id. */
                wkpId: string;
                
                /** The confirm closure status. */
                confirmClsStatus: number;
                
                /** The process date. */
                processDate: number;
                
                constructor (isSave: boolean, closureId: number, wkpId: string, confirmClsStatus: number, processDate: number) {
                    this.isSave = isSave;
                    this.closureId = closureId;
                    this.wkpId = wkpId;
                    this.confirmClsStatus = confirmClsStatus;
                    this.processDate = processDate;
                }
            }
        }
    }
}  