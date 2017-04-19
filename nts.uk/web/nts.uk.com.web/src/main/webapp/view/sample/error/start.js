__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.ResidentCode = ko.observable('123');
            self.NumberValue = ko.observable(5);
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
