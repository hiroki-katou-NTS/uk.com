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
                var fList001 = [];
                var fList002 = [];
                self.fList001 = ko.observable(new ListBoxF(fList001));
                self.fList002 = ko.observable(new ListBoxF(fList002));
            }
            return FScreen;
        }());
        qmm017.FScreen = FScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
