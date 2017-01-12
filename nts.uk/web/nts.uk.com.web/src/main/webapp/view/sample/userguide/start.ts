__viewContext.ready(function () {
    class ScreenModel {
        dataList: KnockoutObservableArray<any>;
        enable: KnockoutObservable<boolean>;
        enableCut: KnockoutObservable<boolean>;
        visibleCopy: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            self.dataList = ko.observableArray([]);
            for(let i = 0; i <= 10; i++) {
                self.dataList.push({id: i, name: "Item " + i});
            }
            // Init UserGuide
            $("[data-toggle='userguide']").ntsUserGuide();
        }
        
        showOverlayLeft() {
            $(".userguide-left").ntsUserGuide("show");
        }
        
        showOverlayRight() {
            $(".userguide-right").ntsUserGuide("show");
        }
        
        showOverlayTop() {
            $(".userguide-top").ntsUserGuide("show");
        }
        
        showOverlayBottom() {
            $(".userguide-bottom").ntsUserGuide("show");
        }
    }
    
    this.bind(new ScreenModel());
});