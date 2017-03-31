var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var ListBoxE = (function () {
            function ListBoxE(data) {
                var self = this;
                self.itemList = ko.observableArray(data);
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(3);
                self.selectedCode = ko.observable(null);
                self.isEnable = ko.observable(true);
                self.selectedCodes = ko.observableArray([]);
                $('#list-box-e').on('selectionChanging', function (event) {
                    console.log('Selecting value:' + event.originalEvent.detail);
                });
                $('#list-box-e').on('selectionChanged', function (event) {
                    console.log('Selected value:' + event.originalEvent.detail);
                });
            }
            return ListBoxE;
        }());
        qmm017.ListBoxE = ListBoxE;
        var EScreen = (function () {
            function EScreen() {
                var eList001 = [
                    { code: '1', name: '支給項目（支給＠） e' },
                    { code: '2', name: '控除項目（控除＠） e' },
                    { code: '3', name: '勤怠項目（勤怠＠） e' },
                    { code: '4', name: '明細割増単価項目（割増し単価＠） e' }
                ];
                var eList002 = [
                    { code: '1', name: 'child1' },
                    { code: '2', name: 'child2' },
                    { code: '3', name: 'child3' },
                    { code: '4', name: 'child4' }
                ];
                self.eList001 = ko.observable(new ListBoxE(eList001));
                self.eList002 = ko.observable(new ListBoxE(eList002));
            }
            return EScreen;
        }());
        qmm017.EScreen = EScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
