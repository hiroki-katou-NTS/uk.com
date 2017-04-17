var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm016;
                (function (qmm016) {
                    var k;
                    (function (k) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.dialogOptions = nts.uk.ui.windows.getShared('options');
                                    self.demensionItemList = ko.observableArray([]);
                                    self.selectedDemension = ko.observable(undefined);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    k.service.loadDemensionSelectionList().done(function (res) {
                                        var filteredDemensionItemList = _.filter(res, function (item) {
                                            var ignoredItem = _.find(self.dialogOptions.selectedDemensionDto, function (selected) {
                                                return item.type == selected.type && item.code == selected.code;
                                            });
                                            return ignoredItem == undefined;
                                        });
                                        self.demensionItemList(filteredDemensionItemList);
