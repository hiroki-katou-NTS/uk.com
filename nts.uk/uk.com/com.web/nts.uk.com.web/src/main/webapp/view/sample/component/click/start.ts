__viewContext.ready(() => {
    let viewModel = {
        timeClick: ko.observable(500),
        clickEvent: () => {
            $('#clickResult').append($('<pre>', { text: `click at: ${new Date().toJSON()}` }));
        } 
    };
    
    __viewContext.bind(viewModel);
});