// Custom binding handle for ntsCheckBox
ko.bindingHandlers.ntsCheckBoxWithHelp = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        ko.bindingHandlers.ntsCheckBox.init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
        $(element).find('.ntsCheckBox').append($('<span>', {
            'class': 'label helper',
            'text': valueAccessor().helper
        }));
    },
    update: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        ko.bindingHandlers.ntsCheckBox.update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
    }
}

__viewContext.ready(function() {
    let screenModel = new nts.uk.pr.view.qmm005.e.viewmodel.viewModel();
    this.bind(screenModel);
});