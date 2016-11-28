module nts.uk.ui {
     
    import option = nts.uk.ui.option;
    export var _viewModel: any;
    
    /** Event to notify document ready to initialize UI. */
    export var documentReady = $.Callbacks();
    
    /** Event to notify ViewModel built to bind. */
    export var viewModelBuilt = $.Callbacks();

    module init {
        
        var _start: any;
        
        __viewContext.ready = function (callback: () => void) {
            _start = callback;
        };
        
        __viewContext.bind = function (contentViewModel: any) {
            _viewModel = {
                content: contentViewModel, // developer's view model
                kiban: new KibanViewModel() // Kiban's view model
            };
            
            viewModelBuilt.fire(_viewModel);
            
            ko.applyBindings(_viewModel);
        }
        
        $(function () {
            documentReady.fire();
            
            __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                .map(v => JSON.parse(v));
            
            _.defer(() => _start.call(__viewContext));
        });
        
        // Kiban ViewModel
        class KibanViewModel {
            errorDialogViewModel: errors.ErrorsViewModel;
            
            constructor(){
                var self = this;
                self.errorDialogViewModel = new nts.uk.ui.errors.ErrorsViewModel();
            }
        }
    }
}