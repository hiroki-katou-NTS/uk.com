__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.enable = ko.observable(true);
            self.readonly = ko.observable(false);
            self.date = ko.observable("1990/01/01");
            self.time = ko.observable("12:00");
            self.yearmontheditor = {
                value: ko.observable(200001),
                constraint: 'LayoutCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                    inputFormat: 'yearmonth'
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
        return ScreenModel;
    }());
    var viewmodel = new ScreenModel();
    this.bind(viewmodel);
});
