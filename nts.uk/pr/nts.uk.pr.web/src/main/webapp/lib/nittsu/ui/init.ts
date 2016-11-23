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
            _start.call(__viewContext);
        });
    }
}