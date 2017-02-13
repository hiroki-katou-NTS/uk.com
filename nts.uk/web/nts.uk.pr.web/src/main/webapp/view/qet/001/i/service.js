var qet001;
(function (qet001) {
    var i;
    (function (i_1) {
        var service;
        (function (service) {
            var servicePath = {
                findOutputSettingDetail: 'ctx/pr/report/wageledger/outputsetting/find',
                findAggregateItems: 'ctx/pr/report/wageledger/aggregateitem/findAll',
                findMasterItems: '???',
                saveOutputSetting: 'ctx/pr/report/wageledger/outputsetting/save',
                removeOutputSetting: 'ctx/pr/report/wageledger/outputsetting/remove',
            };
            function findMasterItems() {
                var dfd = $.Deferred();
                var data = [];
                for (var i = 0; i < 10; i++) {
                    data.push({ code: 'MI' + i, name: 'Master item' + i, paymentType: 'Salary', category: 'Payment' });
                }
                for (var i = 0; i < 10; i++) {
                    data.push({ code: 'MI' + i, name: 'Master item' + i, paymentType: 'Salary', category: 'Deduction' });
                }
                dfd.resolve(data);
                return dfd.promise();
            }
            service.findMasterItems = findMasterItems;
        })(service = i_1.service || (i_1.service = {}));
    })(i = qet001.i || (qet001.i = {}));
})(qet001 || (qet001 = {}));
