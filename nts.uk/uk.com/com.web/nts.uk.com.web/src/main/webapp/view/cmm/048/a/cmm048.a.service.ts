module nts.uk.com.view.cmm048.a {
    
    export module service {
        
        
        
        
    }
    
    export module model {
        
        export interface MainDto {
            employee: EmployeeDto;
            employeeInfoContact: EmployeeInfoContactDto;
            personContact: PersonContactDto;
            passwordPolicy: PasswordPolicyDto;
            listUserInfoUseMethod: Array<PasswordPolicyDto>;
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
    }
}