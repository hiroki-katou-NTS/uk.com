module nts.uk.at.view.kal001.a.service {
    import format = nts.uk.text.format;
    import model = nts.uk.at.view.kal001.a.model;
        var paths = {
            getAlarmByUser: "at/function/alarm/kal/001/pattern/setting",
            getCheckConditionTime: "at/function/alarm/kal/001/check/condition/time",
            extractAlarm: "at/function/alarm/kal/001/extract/alarm",
            isExtracting: "at/function/alarm/kal/001/isextracting",
            extractStarting: "at/function/alarm/kal/001/extractstarting",
            extractFinished: "at/function/alarm/kal/001/extractfinished"
        }
        
        export function getAlarmByUser(): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax("at", paths.getAlarmByUser);
        }        

        export function getCheckConditionTime(alarmCode: string): JQueryPromise<Array<CheckConditionTimeDto>> {                              
              return nts.uk.request.ajax("at", paths.getCheckConditionTime, alarmCode);
        }
        
        export function extractAlarm(listEmployee: Array<model.UnitModel>, alarmCode: string, listPeriodByCategory: Array<model.PeriodByCategory>): JQueryPromise<ExtractedAlarmDto>{
            let command = new ExtractAlarmCommand(listEmployee, alarmCode, 
                                                   _.map(listPeriodByCategory, (item) =>{ return new PeriodByCategoryCommand(item);}));
            
            return nts.uk.request.ajax("at", paths.extractAlarm, command);
        }
    
        export function isExtracting(): JQueryPromise<boolean> {
            return nts.uk.request.ajax("at", paths.isExtracting);
        }        

        export function extractStarting(): JQueryPromise<string> {                              
              return nts.uk.request.ajax("at", paths.extractStarting);
        }
        
        export function extractFinished(statusId): JQueryPromise<void>{
            
            return nts.uk.request.ajax("at", paths.extractFinished, { processStatusId: statusId });
        }
    
        export interface CheckConditionTimeDto{
            category : number;
            categoryName: string;
            startDate :   string;
            endDate: string;
            startMonth: string;
            endMonth: string;  
        }
        export class PeriodByCategoryCommand{
                category: number;  
                name: string;                
                startDate: string;                
                endDate : string; 
                constructor(p: model.PeriodByCategory){
                    if(p.category==2|| p.category==5){
                        this.startDate =nts.uk.time.parseMoment(p.dateValue().startDate).momentObject.toISOString() ;
                        this.endDate = nts.uk.time.parseMoment(p.dateValue().endDate).momentObject.toISOString() ;
                    }else if(p.category ==7){
                        this.startDate = null ;
                        this.endDate = null;
                    }
                    this.category = p.category;
                    this.name = p.categoryName;                            
                }   
        }
        export class ExtractAlarmCommand{
            listEmployee: Array<model.UnitModel>;
            alarmCode: string;
            listPeriodByCategory: Array<PeriodByCategoryCommand>;
            
            constructor(listEmployee: Array<model.UnitModel>,  alarmCode: string, listPeriodByCategory: Array<PeriodByCategoryCommand>){
                this.listEmployee = listEmployee;
                this.alarmCode = alarmCode;
                this.listPeriodByCategory = listPeriodByCategory;
            }
        }
        
        export interface ValueExtractAlarmDto{
            guid : string;
            workplaceID : string;
            hierarchyCd : string;
            workplaceName : string;
            employeeID : string;
            employeeCode : string;
            employeeName : string;
            alarmValueDate : string;
            category : number;
            categoryName: string;
            alarmItem : string;
            alarmValueMessage : string;
            comment : string;            
        }
        
        export interface ExtractedAlarmDto{
            extractedAlarmData: Array<ValueExtractAlarmDto>;
            extracting: boolean;
            nullData: boolean;
        }
    
}
