module nts.uk.at.view.kal001.a.service {
    import format = nts.uk.text.format;
        var paths = {
            getAlarmByUser: "at/function/alarm/kal/001/pattern/setting",
            getCheckConditionTime: "at/function/alarm/kal/001/check/condition/time",
        }
        
        export function getAlarmByUser(): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax("at", paths.getAlarmByUser);
        }        

        export function getCheckConditionTime(alarmCode: string): JQueryPromise<Array<CheckConditionTimeDto>> {                              
              return nts.uk.request.ajax("at", paths.getCheckConditionTime, alarmCode);
        }
    
        export interface CheckConditionTimeDto{
            category : number;
            categoryName: string;
            startDate :   string;
            endDate: string;
            startMonth: string;
            endMonth: string;  
        }
}
