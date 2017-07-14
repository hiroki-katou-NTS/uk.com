__viewContext.ready(function () {
    class ScreenModel {
        
        legendOptions: any;
        
        constructor() {
            var self = this;
            
            this.legendOptions = {
                items: [
                    { colorCode: '#ff0000', labelText: 'RED' },
                    { colorCode: '#00AA00', labelText: 'GREEN' },
                    { colorCode: '#0000FF', labelText: 'BLUE' }
                ]
            }
        }
    }
    
    this.bind(new ScreenModel());
    
});