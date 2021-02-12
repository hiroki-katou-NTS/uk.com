module nts.uk.at.view.knr002.e {
    
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.knr002.e.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
            setTimeout(() => {
            	var elements = document.getElementsByClassName('split-text');
                elements[0].innerHTML = elements[0].textContent.split('　').filter(item => item.length > 0).join('<br/>');
                elements[1].innerHTML = elements[1].textContent.split('　').filter(item => item.length > 0).join('<br/>');
            }, 1);
        });    
    });
}