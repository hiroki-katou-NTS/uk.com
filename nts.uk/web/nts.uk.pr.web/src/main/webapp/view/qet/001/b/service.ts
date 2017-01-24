module qet001.b {
    export module service {
        import WageLedgerOutputSetting = qet001.a.service.model.WageLedgerOutputSetting;

        // Service paths.
        var servicePath = {
            findOutputSettingDetail: 'ctx/pr/report/wageledger/outputsetting/find',
            findAggregateItems: 'ctx/pr/report/wageledger/aggregateitem/findAll',
            findMasterItems: '????'
        }
        
        /**
         * Find output setting detail.
         */
        export function findOutputSettingDetail(settingCode: string): JQueryPromise<WageLedgerOutputSetting> {
            return nts.uk.request.ajax(servicePath.findOutputSettingDetail + '/' + settingCode);
        }
        
        /**
         * Find all Aggregate items.
         */
        export function findAggregateItems() : JQueryPromise<Item[]> {
            return nts.uk.request.ajax(servicePath.findAggregateItems);
        }
        
        /**
         * Find master items.
         */
        export function findMasterItems() : JQueryPromise<Item[]> {
            var dfd = $.Deferred<Item[]>();
            // Fake data.
            var data = [];
            for (var i = 0; i < 10; i++) {
                data.push({code: 'MI' + i, name: 'Master item' + i, paymentType: 'Salary', category: 'Payment'});
            }
            for (var i = 0; i < 10; i++) {
                data.push({code: 'MI' + i, name: 'Master item' + i, paymentType: 'Salary', category: 'Deduction'});
            }
            dfd.resolve(data);
            return dfd.promise();
        }
        
        /**
         * Aggregate item class.
         */
        export class Item {
            code: string;
            name: string;
            paymentType: string;
            category: string;
            showNameZeroValue: boolean;
            showValueZeroValue: boolean;
        }
    }
}
