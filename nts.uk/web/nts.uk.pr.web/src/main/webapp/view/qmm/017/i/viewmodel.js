var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var ListBoxI = (function () {
            function ListBoxI(data) {
                var self = this;
                self.itemList = ko.observableArray(data);
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(3);
                self.selectedCode = ko.observable(null);
                self.isEnable = ko.observable(true);
                self.selectedCodes = ko.observableArray([]);
                $('#list-box-i').on('selectionChanging', function (event) {
                    console.log('Selecting value:' + event.originalEvent.detail);
                });
                $('#list-box-i').on('selectionChanged', function (event) {
                    console.log('Selected value:' + event.originalEvent.detail);
                });
            }
            return ListBoxI;
        }());
        qmm017.ListBoxI = ListBoxI;
        var IScreen = (function () {
            function IScreen() {
                var iList001 = [
                    { code: '1', name: '支給項目（支給＠） h' },
                    { code: '2', name: '控除項目（控除＠） h' },
                    { code: '3', name: '勤怠項目（勤怠＠） h' },
                    { code: '4', name: '明細割増単価項目（割増し単価＠） h' }
                ];
                var iList002 = [
                    { code: '1', name: 'child1 i' },
                    { code: '2', name: 'child2 i' },
                    { code: '3', name: 'child3 i' },
                    { code: '4', name: 'child4 i' }
                ];
                self.iList001 = ko.observable(new ListBoxI(iList001));
                self.iList002 = ko.observable(new ListBoxI(iList002));
            }
            return IScreen;
        }());
        qmm017.IScreen = IScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map