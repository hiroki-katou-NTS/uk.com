module kal004.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import share = kal004.share.model;
        var paths = {
            getAlarmPattern: "at/function/alarm/pattern/setting",
            getCheckConditionCode: "at/function/alarm/check/condition/code",
            addAlarmPattern: "at/function/alarm/add/pattern/setting",
            updateAlarmPattern: "at/function/alarm/update/pattern/setting",
            removeAlarmPattern: "at/function/alarm/remove/pattern/setting",
        }
        
        
        export function getAlarmPattern(): JQueryPromise<Array<share.AlarmPatternSettingDto>> {
            var alarmPermissionSettingDto ={
                authSetting: true,
                roleIds: ['0001', '0002']    
            }
            var checkConditionDto={
                alarmCategory: 2, 
                checkConditionCodes: ['001', '002']    
            }
            var alarmPatternSettingDto1 ={
                alarmPatternCD : '01',
                alarmPatternName: 'name01',
                alarmPerSet : alarmPermissionSettingDto,
                checkConList : [checkConditionDto]    
            }
            var alarmPatternSettingDto2 ={
                alarmPatternCD : '02',
                alarmPatternName: 'name02',
                alarmPerSet : alarmPermissionSettingDto,
                checkConList : [checkConditionDto]    
            }
            var dfd = $.Deferred();
            dfd.resolve([alarmPatternSettingDto1, alarmPatternSettingDto2]);
            return dfd.promise([alarmPatternSettingDto1, alarmPatternSettingDto2]);
            //return nts.uk.request.ajax("at", paths.getAlarmPattern);
        }        

        export function getCheckConditionCode(): JQueryPromise<Array<share.AlarmCheckConditonCodeDto>> {
            return nts.uk.request.ajax("at", paths.getCheckConditionCode);
        }         
        

        /** add Alarm pattern setting */
        export function addAlarmPattern(alarm: any): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.addAlarmPattern, alarm);
        }
    
        /** remove Alarm pattern setting */
        export function removeAlarmPattern(query: any): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.removeAlarmPattern, query);
        }
        
        /** Update Alarm pattern setting */
        export function updateAlarmPattern(alarm: any): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.updateAlarmPattern, alarm );
        }
 
    
    export module model {
              export interface EnumConstantDto {
                value: number;
                fieldName: string;
                localizedName: string;
              }
    }
    
    
}
