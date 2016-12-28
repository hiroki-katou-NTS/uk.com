__viewContext.ready(function () {
    class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        date: KnockoutObservable<Date>;
        show: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            self.date = ko.observable(new Date('2016/12/01'));
            self.show = ko.observable(true);
            self.itemList = ko.observableArray([]);
            for (let i = 1; i <= 12; i++) {
                self.itemList.push({index: i});
            }
        }
        
        toggle() {
            this.show(!this.show());
        }
    }
    
    this.bind(new ScreenModel());
    
});