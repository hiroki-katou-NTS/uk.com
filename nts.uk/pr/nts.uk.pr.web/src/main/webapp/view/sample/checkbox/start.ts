__viewContext.ready(function () {
    var vm = {
        checkbox: {
            checked: ko.observable(true),
            enable:  ko.observable(true),
        }
    };
    
    this.bind(vm);
    
});