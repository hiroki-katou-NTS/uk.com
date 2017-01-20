module qet001.a {
    export module service {

        // Service paths.
        var servicePath = {
            findOutputSettings: 'ctx/pr/report/wageledger/outputsetting/findAll'
        }
        
        /**
         * Find all output setting services.
         */
        export function findOutputSettings(): JQueryPromise<model.WageLedgerOutputSetting[]>{
            return nts.uk.request.ajax(servicePath.findOutputSettings);
         }
        
        /**
        * Model namespace.
        */
        export module model {
            export class WageLedgerOutputSetting {
                code: string;
                name: string;
                onceSheetPerPerson: boolean;
                categorySettings: WageledgerCategorySetting[];
            }
            
            export class WageledgerCategorySetting {
                category: string;
                paymentType: string;
                outputItems: WageLedgerSettingItem[];
            }
            
            export class WageLedgerSettingItem {
                code: string;
                name: string;
                isAggregateItem: boolean
            }
        }
    }
}
