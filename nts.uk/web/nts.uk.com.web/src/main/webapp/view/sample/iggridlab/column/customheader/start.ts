__viewContext.ready(function() {
    class ScreenModel {
        items: KnockoutObservableArray<any>;
        first: any;
        
        constructor() {
            this.items = ko.observableArray(testdata.createHogeArray(100));
            this.first = this.items()[0];
        }
    }
    
    this.bind(new ScreenModel());
    
    nts.uk.ui.ig.grid.header.getLabel('grid_test', 'code')
        .append($('<button />').text('hello').click(() => { alert('hello'); }));
});

