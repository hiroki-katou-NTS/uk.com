module nts.uk.ui {
     
    import option = nts.uk.ui.option;
    export var _viewModel: any;

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
            ko.applyBindings(_viewModel);
        }
        
        $(function () {
            __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                .map(v => JSON.parse(v));
            
            _start.call(__viewContext);
        });
        
        // Kiban ViewModel
        class KibanViewModel {
            errorDialogViewModel: errors.ErrorsViewModel;
            
            constructor(){
                var self = this;
                self.errorDialogViewModel = new errors.ErrorsViewModel();
                for (var i = 0; i < 20; i++) {
                    self.errorDialogViewModel.errors.push({tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"});
                }
            }
        }
    }
}