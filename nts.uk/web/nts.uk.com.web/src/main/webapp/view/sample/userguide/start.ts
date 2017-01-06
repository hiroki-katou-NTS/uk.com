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
        }
        
        showOverlay() {
            $(".userguide-overlay").show();
        }
        
        hideOverlay() {
            $(".userguide-overlay").hide();
        }
        
    }
    
    this.bind(new ScreenModel());
});