module qet001.i {
    export module service {
        import WageLedgerOutputSetting = qet001.a.service.model.WageLedgerOutputSetting;

        // Service paths.
        var servicePath = {
            findAggregateItemsByCategory: 'ctx/pr/report/wageledger/aggregateitem/findByCate',
            findAggregateItemDetail: 'ctx/pr/report/wageledger/aggregateitem/findByCode',
            findMasterItems: '???',
            saveAggregateItem: 'ctx/pr/report/wageledger/aggregateitem/save',
            removeAggegateItem: 'ctx/pr/report/wageledger/aggregateitem/remove'
        }
        
        /**
         * Find master items.
         */
        export function findMasterItems() : JQueryPromise<Item[]> {
            var dfd = $.Deferred<Item[]>();
            // Fake data.
            var data = [];
            for (var i = 0; i < 10; i++) {
                data.push({code: 'MI' + i, name: 'Master item' + i, category: 'Payment'});
            }
            for (var i = 0; i < 10; i++) {
                data.push({code: 'MI' + i, name: 'Master item' + i, category: 'Deduction'});
            }
            dfd.resolve(data);
            return dfd.promise();
        }
        
        /**
         * Find Aggregate item by category and payment type.
         */
        export function findAggregateItemsByCategory(category: string, paymentType: string): JQueryPromise<Item[]> {
            return nts.uk.request.ajax(servicePath.findAggregateItemsByCategory + '/' + category + '/' + paymentType);
        }
        
        /**
         * Find aggregate item detail.
         */
        export function findAggregateItemDetail(code: string) : JQueryPromise<Item> {
            return nts.uk.request.ajax(servicePath.findAggregateItemDetail + '/' + code);
        }
        
        /**
         * Save aggregate item.
         */
        export function save(data: viewmodel.AggregateItemDetail): JQueryPromise<void> {
            // Convert to json data.
            var dataJson = {
                category: data.category,
                paymentType: data.paymentType,
                code: data.code(),
                name: data.name(),
                showNameZeroValue: data.showNameZeroValue(),
                showValueZeroValue: data.showValueZeroValue(),
                createMode: data.createMode(),
                subItems: data.subItems().map(item => item.code)
            }
            return nts.uk.request.ajax(servicePath.saveAggregateItem, dataJson);
        }
        
        /**
         * Remove aggregate item.
         */
        export function remove(code: string): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.removeAggegateItem + '/' + code);
        }
        
        /**
         * Aggregate item class.
         */
        export interface Item {
            code: string;
            name: string;
            paymentType?: string;
            category: string;
            orderNumber? : number;
            showNameZeroValue?: boolean;
            showValueZeroValue?: boolean;
            subItems?: SubItem[];
        }
        
        /**
         * Sub item class.
         */
        export interface SubItem {
            code: string;
            name: string;
        }
    }
}
