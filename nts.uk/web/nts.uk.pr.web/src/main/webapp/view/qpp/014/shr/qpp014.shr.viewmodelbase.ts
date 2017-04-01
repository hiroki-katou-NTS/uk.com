module qpp014.shr.viewmodelbase {
    export class PayDayProcessing {
        companyCode: string;
        processingNumber: number;
        processingName: string;
        displaySet: number;
        currentProcessing: number;
        bonusAttribute: number;
        bonusCurrentProcessing: number;
        
        constructor(companyCode: string, processingNumber: number, processingName: string,
            displaySet: number, currentProcessing: number, bonusAttribute: number, bonusCurrentProcessing: number) {
            this.companyCode = companyCode;
            this.processingNumber = processingNumber;
            this.processingName = processingName;
            this.displaySet = displaySet;
            this.currentProcessing = currentProcessing;
            this.bonusAttribute = bonusAttribute;
            this.bonusCurrentProcessing = bonusCurrentProcessing;
        }
    } 
    
    export class PersonBankAccount {
        fromLineBankCd1: string;
        fromLineBankCd2: string;
        fromLineBankCd3: string;
        fromLineBankCd4: string;
        fromLineBankCd5: string;   
        
        constructor(fromLineBankCd1: string, fromLineBankCd2: string, fromLineBankCd3: string, fromLineBankCd4: string, fromLineBankCd5: string) {
            this.fromLineBankCd1 = fromLineBankCd1;
            this.fromLineBankCd2 = fromLineBankCd2;
            this.fromLineBankCd3 = fromLineBankCd3;
            this.fromLineBankCd4 = fromLineBankCd4;
            this.fromLineBankCd5 = fromLineBankCd5;       
        }
    }
}