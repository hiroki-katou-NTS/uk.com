module qet001.i {
    export module service {
        import WageLedgerOutputSetting = qet001.a.service.model.WageLedgerOutputSetting;

        // Service paths.
        var servicePath = {
            findOutputSettingDetail: 'ctx/pr/report/wageledger/outputsetting/find',
            findAggregateItems: 'ctx/pr/report/wageledger/aggregateitem/findAll',
            findMasterItems: '???',
            saveOutputSetting: 'ctx/pr/report/wageledger/outputsetting/save',
            removeOutputSetting: 'ctx/pr/report/wageledger/outputsetting/remove',
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
        export interface Item {
            code: string;
            name: string;
            paymentType: string;
            category: string;
            orderNumber? : number;
            showNameZeroValue: boolean;
            showValueZeroValue: boolean;
        }
    }
}
