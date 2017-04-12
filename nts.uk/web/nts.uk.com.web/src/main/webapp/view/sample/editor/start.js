__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.option = {
                value: ko.observable("2")
            };
            self.simpleValue = ko.observable("123");
            // TextEditor
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'WLAggregateItemCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false),
                clear: function () {
                    $("#text-1").ntsError("clear");
                }
            };
            // EmployeeCodeEditor
            self.employeeeditor = {
                value: ko.observable('19'),
                constraint: 'EmployeeCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0"
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // MultilineEditor
            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "Placeholder for text editor",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // NumberEditor
            self.numbereditor = {
                value: ko.observable(12),
                constraint: 'CommonAmount',
                option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    textalign: 'left',
                    width: "500" })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // NumberEditor
            self.numbereditor2 = {
                value: ko.observable(12),
                constraint: 'CommonAmount',
                option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    symbolChar: '%',
                    symbolPosition: 'right' })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // CurrencyEditor
            self.currencyeditor = {
                value: ko.observable(1200),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "JPY"
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // CurrencyEditor
            self.currencyeditor2 = {
                value: ko.observable(200000),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "USD"
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // TimeEditor
            self.timeeditor = {
                value: ko.observable(-1222),
                constraint: 'LayoutCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                    inputFormat: 'time',
                    placeholder: "Enter a valid HH:mm",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // TimeEditor
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
            // File
            self.file = {
                file: new nts.uk.ui.file.FileDownload("/file/company/print"),
                print: function () {
                    this.file.print();
                }
            };
        }
        return ScreenModel;
    }());
    var viewmodel = new ScreenModel();
    var dirty = new nts.uk.ui.DirtyChecker(viewmodel.numbereditor.value);
    nts.uk.ui.confirmSave(dirty);
    this.bind(viewmodel);
});
