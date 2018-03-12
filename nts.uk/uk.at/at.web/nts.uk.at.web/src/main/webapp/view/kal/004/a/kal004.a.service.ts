module nts.uk.at.view.kal004.a.service {
    import format = nts.uk.text.format;
    import share = nts.uk.at.view.kal004.share.model;
        var paths = {
            getAlarmPattern: "at/function/alarm/pattern/setting",
            getCheckConditionCode: "at/function/alarm/check/condition/code",
            addAlarmPattern: "at/function/alarm/add/pattern/setting",
            updateAlarmPattern: "at/function/alarm/update/pattern/setting",
            removeAlarmPattern: "at/function/alarm/remove/pattern/setting",
            getEnumAlarmCategory : "at/function/alarm/get/enum/alarm/category" 
        }
        
        
        export function getAlarmPattern(): JQueryPromise<Array<share.AlarmPatternSettingDto>> {
            return nts.uk.request.ajax("at", paths.getAlarmPattern);
        }        

        export function getCheckConditionCode(): JQueryPromise<Array<share.AlarmCheckConditonCodeDto>> {                              
              return nts.uk.request.ajax("at", paths.getCheckConditionCode);
        }         
        

        /** add Alarm pattern setting */
        export function addAlarmPattern(alarm: share.AddAlarmPatternSettingCommand): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.addAlarmPattern, alarm);
        }
    
        /** remove Alarm pattern setting */
        export function removeAlarmPattern(query: any): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.removeAlarmPattern, query);
        }
        
        /** Update Alarm pattern setting */
        export function updateAlarmPattern(alarm: share.AddAlarmPatternSettingCommand): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.updateAlarmPattern, alarm );
        }
        /** Get enum alarm category */
        export function getEnumAlarm(): JQueryPromise<Array<share.EnumConstantDto>> {
            return nts.uk.request.ajax(paths.getEnumAlarmCategory);
        }
    

    
    
}
