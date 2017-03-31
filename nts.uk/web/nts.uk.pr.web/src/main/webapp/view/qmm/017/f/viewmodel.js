var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var ListBoxF = (function () {
            function ListBoxF(data) {
                var self = this;
                self.itemList = ko.observableArray(data);
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(3);
                self.selectedCode = ko.observable(null);
                self.isEnable = ko.observable(true);
                self.selectedCodes = ko.observableArray([]);
                $('#list-box-f').on('selectionChanging', function (event) {
                    console.log('Selecting value:' + event.originalEvent.detail);
                });
                $('#list-box-f').on('selectionChanged', function (event) {
                    console.log('Selected value:' + event.originalEvent.detail);
                });
            }
            return ListBoxF;
        }());
        qmm017.ListBoxF = ListBoxF;
        var FScreen = (function () {
            function FScreen() {
                var fList001 = [
                    { code: '1', name: '支給項目（支給＠） f' },
                    { code: '2', name: '控除項目（控除＠） f' },
                    { code: '3', name: '勤怠項目（勤怠＠） f' },
                    { code: '4', name: '明細割増単価項目（割増し単価＠） f' }
                ];
                var fList002 = [
                    { code: '1', name: 'child1' },
                    { code: '2', name: 'child2' },
                    { code: '3', name: 'child3' },
                    { code: '4', name: 'child4' }
                ];
                self.fList001 = ko.observable(new ListBoxF(fList001));
                self.fList002 = ko.observable(new ListBoxF(fList002));
            }
            return FScreen;
        }());
        qmm017.FScreen = FScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map