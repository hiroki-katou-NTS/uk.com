module nts.uk.ui {
    
    module init {
        var _start: any;
        
        __viewContext.ready = function (callback: () => void) {
            _start = callback;
        };
        
        __viewContext.bind = function (contentViewModel: any) {
            var viewModel = {
                content: contentViewModel // developer's view model
                //kiban: ... kiban's view model
            };
            
            ko.applyBindings(viewModel);
        }
        
        $(function () {
            __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                .map(v => JSON.parse(v));
            
            _start.call(__viewContext);
        });
    }
}