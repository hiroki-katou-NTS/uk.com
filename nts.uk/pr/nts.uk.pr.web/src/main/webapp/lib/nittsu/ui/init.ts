module nts.uk.ui {
    
    export var _viewModel: any;
    
    module init {
        var _start: any;
        
        __viewContext.ready = function (callback: () => void) {
            _start = callback;
        };
         
        __viewContext.bind = function (viewModel: any) {
            _viewModel = {
                content: viewModel // developer's view model
                //kiban: ... kiban's view model
            };
        }
        
        $(function () {
            _start.call(__viewContext);
            
            ko.applyBindings(_viewModel);
        });
    }
}