var qet001;
(function (qet001) {
    var i;
    (function (i_1) {
        var service;
        (function (service) {
            var servicePath = {
                findAggregateItemsByCategory: 'ctx/pr/report/wageledger/aggregateitem/findByCate',
                findAggregateItemDetail: 'ctx/pr/report/wageledger/aggregateitem/findBySubject',
                findMasterItems: '???',
                saveAggregateItem: 'ctx/pr/report/wageledger/aggregateitem/save',
                removeAggegateItem: 'ctx/pr/report/wageledger/aggregateitem/remove'
            };
            function findMasterItems() {
                var dfd = $.Deferred();
                var data = [];
                for (var i = 0; i < 10; i++) {
                    data.push({ code: 'MI' + i, name: 'Master item' + i, category: 'Payment' });
                }
                for (var i = 0; i < 10; i++) {
                    data.push({ code: 'MI' + i, name: 'Master item' + i, category: 'Deduction' });
                }
                dfd.resolve(data);
                return dfd.promise();
            }
            service.findMasterItems = findMasterItems;
            function findAggregateItemsByCategory(category, paymentType) {
                return nts.uk.request.ajax(servicePath.findAggregateItemsByCategory + '/' + category + '/' + paymentType);
            }
            service.findAggregateItemsByCategory = findAggregateItemsByCategory;
            function findAggregateItemDetail(category, paymentType, code) {
                var subject = {
                    code: code,
                    category: category,
                    paymentType: paymentType
                };
                return nts.uk.request.ajax(servicePath.findAggregateItemDetail, subject);
            }
            service.findAggregateItemDetail = findAggregateItemDetail;
            function save(data) {
                var dataJson = {
                    subject: {
                        category: data.category,
                        paymentType: data.paymentType,
                        code: data.code(),
                    },
                    name: data.name(),
                    showNameZeroValue: data.showNameZeroValue(),
                    showValueZeroValue: data.showValueZeroValue(),
                    createMode: data.createMode(),
                    subItems: data.subItems().map(function (item) { return item.code; })
                };
                return nts.uk.request.ajax(servicePath.saveAggregateItem, dataJson);
            }
            service.save = save;
            function remove(category, paymentType, code) {
                var subject = {
                    code: code,
                    category: category,
                    paymentType: paymentType
                };
                return nts.uk.request.ajax(servicePath.removeAggegateItem, { subject: subject });
            }
            service.remove = remove;
        })(service = i_1.service || (i_1.service = {}));
    })(i = qet001.i || (qet001.i = {}));
})(qet001 || (qet001 = {}));
//# sourceMappingURL=qet001.i.service.js.map