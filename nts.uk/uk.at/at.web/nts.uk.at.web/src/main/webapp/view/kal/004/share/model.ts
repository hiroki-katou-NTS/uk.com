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
        category: EnumConstantDto;
        checkConditonCode: string;
        checkConditionName: string;
        listRoleId: Array<string>;    
    }
    export class ModelCheckConditonCode {
           GUID: string;
           category: number;
           categoryName: string;
           checkConditonCode: string;
           checkConditionName: string;
           listRoleId: Array<string>; 
           constructor(category: number,categoryName: string, checkConditonCode: string, checkConditionName: string, listRoleId: Array<string>) {
               this.category = category;
               this.categoryName = categoryName;
               this.checkConditonCode = checkConditonCode;
               this.checkConditionName = checkConditionName;
               this.listRoleId = listRoleId;
               this.GUID = nts.uk.util.randomId();               
           }
    }
      export interface EnumConstantDto {
        value: number;
        fieldName: string;
        localizedName: string;
      }  
    
    
}