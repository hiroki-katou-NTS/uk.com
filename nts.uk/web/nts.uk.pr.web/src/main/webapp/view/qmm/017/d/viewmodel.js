var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var ListBoxD = (function () {
            function ListBoxD(data) {
                var self = this;
                self.itemList = ko.observableArray(data);
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(3);
                self.selectedCode = ko.observable(null);
                self.isEnable = ko.observable(true);
                self.selectedCodes = ko.observableArray([]);
                $('#list-box-d').on('selectionChanging', function (event) {
                    console.log('Selecting value:' + event.originalEvent.detail);
                });
                $('#list-box-d').on('selectionChanged', function (event) {
                    console.log('Selected value:' + event.originalEvent.detail);
                });
            }
            return ListBoxD;
        }());
        qmm017.ListBoxD = ListBoxD;
        var DScreen = (function () {
            function DScreen() {
                var dList001 = [
                    { code: '1', name: '支給項目（支給＠）' },
                    { code: '2', name: '控除項目（控除＠）' },
                    { code: '3', name: '勤怠項目（勤怠＠）' },
                    { code: '4', name: '明細割増単価項目（割増し単価＠）' }
                ];
                var dList002 = [
                    { code: '1', name: 'child1' },
                    { code: '2', name: 'child2' },
                    { code: '3', name: 'child3' },
                    { code: '4', name: 'child4' }
                ];
                self.dList001 = ko.observable(new ListBoxD(dList001));
                self.dList002 = ko.observable(new ListBoxD(dList002));
            }
            return DScreen;
        }());
        qmm017.DScreen = DScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map