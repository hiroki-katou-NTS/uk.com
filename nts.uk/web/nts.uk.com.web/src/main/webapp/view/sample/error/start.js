__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            // TextEditor
            self.texteditor = {
                value: ko.observable('123'),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption()),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // NumberEditor
            self.numbereditor = {
                value: ko.observable(12),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({ grouplength: 3, decimallength: 2 })),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
        ScreenModel.prototype.setErrorToResidenceCode = function () {
            $('#residence-code').ntsError('set', 'えらーです');
        };
        ScreenModel.prototype.clearErrorToResidenceCode = function () {
            $('#residence-code').ntsError('clear');
        };
        ScreenModel.prototype.clearSaveErrors = function () {
            $('.save-error').ntsError('clear');
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
