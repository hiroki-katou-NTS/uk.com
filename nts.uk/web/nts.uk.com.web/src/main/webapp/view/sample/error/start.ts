__viewContext.ready(function () {
    class ScreenModel {
        texteditor: any;
        numbereditor: any;

        constructor() {
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
                option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({grouplength: 3, decimallength: 2})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
        
        setErrorToResidenceCode() {
            $('#residence-code').ntsError('set', 'えらーです');
        }
        
        clearErrorToResidenceCode() {
            $('#residence-code').ntsError('clear');
        }
        
        clearSaveErrors() {
            $('.save-error').ntsError('clear');
        }
    }
    
    this.bind(new ScreenModel());
    
});