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
            
            //avoid page content overlap header and function area
            var content_height=50+20;//header height+ content area botton padding,top padding
            if($("#functions-area").length!=0){
                content_height +=49;//top function area height
            }
            if($("#functions-area-bottom").length!=0){
                content_height +=74;//top function area height
            }
            $("#contents-area").css("height","calc(100vh - "+content_height+"px)");
//            if($("#functions-area-bottom").length!=0){
//            }
        }
        
        $(function () {
            documentReady.fire();
            
            __viewContext.transferred = uk.sessionStorage.getItem(uk.request.STORAGE_KEY_TRANSFER_DATA)
                .map(v => JSON.parse(v));
            
            _.defer(() => _start.call(__viewContext));
        });
    }
}
