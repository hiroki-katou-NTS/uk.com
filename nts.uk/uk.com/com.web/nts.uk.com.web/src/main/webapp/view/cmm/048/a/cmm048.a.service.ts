module nts.uk.com.view.cmm048.a {
    
    export module service {
        
        
        
        
    }
    
    export module model {
        
        export interface MainDto {
            employeeName: string;
            passwordPolicy: PasswordPolicyDto;
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
    }
}