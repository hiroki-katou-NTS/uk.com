__viewContext.ready(function() {
    class ScreenModel {
        items: KnockoutObservableArray<any>;
        first: any;
        
        constructor() {
            this.items = ko.observableArray(testdata.createHogeArray(100));
            this.first = this.items()[0];
        }
        
        showColumn() {
            $('#grid').igGridHiding('showColumn', 1);
            $('#grid').igGridHiding('showColumn', 2);
            
            $('#grid').igGrid('option', 'width', 400);
        }
        
        hideColumn() {
            $('#grid').igGridHiding('hideColumn', 1);
            $('#grid').igGridHiding('hideColumn', 2);
            
            $('#grid').igGrid('option', 'width', 100);
        }
    }
    
    this.bind(new ScreenModel());
});

