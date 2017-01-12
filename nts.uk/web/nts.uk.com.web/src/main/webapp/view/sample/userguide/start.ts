__viewContext.ready(function () {
    class ScreenModel {
        inputText: KnockoutObservable<string>;
        checked: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            // Input Text
            self.inputText = ko.observable("a");
            self.inputText.subscribe(function(value){
                if (value.length > 0)
                    $(".userguide-right").ntsUserGuide("hide");
                else
                    $(".userguide-right").ntsUserGuide("show");
                    
            });
            // CheckBox
            self.checked = ko.observable(true);
            self.checked.subscribe(function(value){
                (value) ? $(".userguide-left").ntsUserGuide("hide") : $(".userguide-left").ntsUserGuide("show"); 
            });
            
            // Init UserGuide
            $("[data-toggle='userguide']").ntsUserGuide();
        }
        
        showOverlayTop() {
            $(".userguide-top").ntsUserGuide("toggle");
        }
        
        showOverlayBottom() {
            $(".userguide-bottom").ntsUserGuide("toggle");
        }
    }
    
    this.bind(new ScreenModel());
});