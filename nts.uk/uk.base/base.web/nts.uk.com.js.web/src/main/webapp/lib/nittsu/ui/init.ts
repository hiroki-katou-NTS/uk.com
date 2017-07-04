/// <reference path="../reference.ts"/>

module nts.uk.ui {
     
    import option = nts.uk.ui.option;
    export var _viewModel: any;
    
    /** Event to notify document ready to initialize UI. */
    export var documentReady = $.Callbacks();
    
    /** Event to notify ViewModel built to bind. */
    export var viewModelBuilt = $.Callbacks();

    
    // Kiban ViewModel
    export class KibanViewModel {
        title: KnockoutObservable<string>;
        errorDialogViewModel: errors.ErrorsViewModel;
        
        constructor(dialogOptions?: any){
            this.title = ko.observable('');
            this.errorDialogViewModel = new nts.uk.ui.errors.ErrorsViewModel(dialogOptions);
        }
    }
    
    module init {
        
        var _start: any;
        
        __viewContext.ready = function (callback: () => void) {
            _start = callback;
        };
        
        __viewContext.bind = function (contentViewModel: any, dialogOptions?: any) {
            var kiban = new KibanViewModel(dialogOptions);
            
            _viewModel = {
                content: contentViewModel,
                kiban: kiban,
                errors: {
                    isEmpty: ko.computed(() => !kiban.errorDialogViewModel.occurs())
                }
            };
            
            kiban.title(__viewContext.title || 'THIS IS TITLE');
            
            viewModelBuilt.fire(_viewModel);
            
            ko.applyBindings(_viewModel);
            
            // off event reset for class reset-not-apply
            $(".reset-not-apply").find(".reset-element").off("reset");
//            if (nts.uk.ui.windows.getSelf().isRoot){
//                let windowSize = {
//                    width: $(window).width(), 
//                    height: $(window).height()
//                };
//                $( window ).resize(function() {
//                    let container = nts.uk.ui.windows.container;
//                    let windows = Object.keys(container.windows); 
//                    let mainWindow = container.windows['MAIN_WINDOW']; 
//                    let currentWidth = $(mainWindow.globalContext).width();
//                    let currentHeight = $(mainWindow.globalContext).height(); 
//                    if (windows.length > 1){ 
//                        let diffWidth = currentWidth / windowSize.width;              
//                        let diffHeight = currentHeight / windowSize.height;
//                        _.forEach(windows, function (id){
//                            if (id !== 'MAIN_WINDOW'){
//                                let $dialog = $(container.windows[id].$dialog);
//                                let dialogWidth = $dialog.dialog( "option", "width") * diffWidth;        
//                                let dialogHeight = $dialog.dialog( "option", "height") * diffHeight;
//                                 $dialog.dialog( "option", "width", dialogWidth);
//                                $dialog.dialog( "option", "height", dialogHeight);
//                            }        
//                        });
//                    }
//                    windowSize = { 
//                        width: currentWidth, 
//                        height: currentHeight
//                    };
//                });        
//            }
        }
        
        $(function () {
            documentReady.fire();
            
            __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                .map(v => JSON.parse(v));
            
            _.defer(() => _start.call(__viewContext));
        });
    }
}