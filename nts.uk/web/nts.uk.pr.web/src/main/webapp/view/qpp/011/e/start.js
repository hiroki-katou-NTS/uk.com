__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: 'Item1' },
                { code: '2', name: 'Item2' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
        return ScreenModel;
    }());
    var GridItemModel = (function () {
        function GridItemModel(code, name, description) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
        return GridItemModel;
    }());
    this.bind(new ScreenModel());
});
function closeDialog() {
    nts.uk.ui.windows.close();
}
function submitInfo() {
    nts.uk.ui.windows.close();
}
//# sourceMappingURL=start.js.map