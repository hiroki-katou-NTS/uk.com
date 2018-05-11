module nts.uk.com.view.cmm048.a {
    
    export module service {
        
        let servicePath: any = {
            getAllEnum: "sys/env/userinfoset/getAllEnum",
        }

        export function getAllEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getAllEnum);
        }
        
    }
    
    export module model {
        
        export interface MainDto {
            employee: EmployeeDto;
            employeeInfoContact: EmployeeInfoContactDto;
            personContact: PersonContactDto;
            passwordPolicy: PasswordPolicyDto;
            listUserInfoUseMethod: Array<PasswordPolicyDto>;
            listUseContactSettingDto: Array<UseContactSettingDto>;
        }
        
        export interface EmployeeDto {
            employeeId: string;
            employeeCode: string;
            employeeName: string;
        }
        
        export interface EmployeeInfoContactDto {
            employeeId: string;
            mailAddress: string;
            mobileMailAddress: string;
            cellPhoneNo: string;
        }
        
        export interface PersonContactDto {
            personId: string;
            mailAddress: string;
            mobileMailAddress: string;
            cellPhoneNo: string;
        }
        
        export interface PasswordPolicyDto {
            isUse: boolean;
            lowestDigits: number;
            complexity: ComplexityDto;
            historyCount: number;
            validityPeriod: number;
        }
        
        export interface ComplexityDto {
            alphabetDigit: number;
            numberOfDigits: number;
            numberOfChar: number;
        }
        
        export interface UserInfoUseMethodDto {
            settingItem: number;
            selfEdit: number;
            settingUseMail: number;
        }
        
        export interface UseContactSettingDto {
            employeeId: string;
            settingItem: number;
            useMailSetting: boolean;
        }
    }
}