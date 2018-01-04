module kal004.share.model {
    export interface AlarmPatternSettingDto {
        alarmPatternCD: string;
        alarmPatternName: string;
        alarmPerSet:  AlarmPermissionSettingDto;
        checkConList: Array<CheckConditionDto>;
    }    
    export interface AlarmPermissionSettingDto{
        authSetting: boolean;
        roleIds: Array<string>;        
    }
    export interface CheckConditionDto{
        alarmCategory: number;
        checkConditionCodes: Array<string>;  
    }
    
    export interface AlarmCheckConditonCodeDto{
        category: number;
        checkConditonCode: string;
        checkConditionName: string;
        listRoleId: Array<string>;    
    }
    
    
    
}