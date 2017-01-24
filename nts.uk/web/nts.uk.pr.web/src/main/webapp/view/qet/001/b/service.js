var qet001;
(function (qet001) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var servicePath = {
                findOutputSettingDetail: 'ctx/pr/report/wageledger/outputsetting/find',
                findAggregateItems: 'ctx/pr/report/wageledger/aggregateitem/findAll',
                findMasterItems: '????'
            };
            function findOutputSettingDetail(settingCode) {
                return nts.uk.request.ajax(servicePath.findOutputSettingDetail + '/' + settingCode);
            }
            service.findOutputSettingDetail = findOutputSettingDetail;
            function findAggregateItems() {
                return nts.uk.request.ajax(servicePath.findAggregateItems);
            }
            service.findAggregateItems = findAggregateItems;
            function findMasterItems() {
                var dfd = $.Deferred();
                var data = [];
                for (var i = 0; i < 10; i++) {
                    data.push({ code: 'MI' + i, name: 'Master item' + i, paymentType: 'Salary', category: 'Payment' });
                }
                dfd.resolve(data);
                return dfd.promise();
            }
            service.findMasterItems = findMasterItems;
            var Item = (function () {
                function Item() {
                }
                return Item;
            }());
            service.Item = Item;
        })(service = b.service || (b.service = {}));
    })(b = qet001.b || (qet001.b = {}));
})(qet001 || (qet001 = {}));
