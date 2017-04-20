__viewContext.ready(function () {
    class ScreenModel {
        constructor() {
            var self = this;
            self.ResidentCode = ko.observable('123');
            self.NumberValue = ko.observable(5);
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
