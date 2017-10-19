import getCell = nts.uk.ui.ig.grid.header.getCell;
module nts.uk.com.view.cps009.a {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ViewModel();

        __viewContext["viewModel"] = screenModel;
        init();
        __viewContext.bind(__viewContext["viewModel"]);
    });
}

function init() {
    let array = Array.prototype.slice.call(document.getElementById("grid").getElementsByTagName("td"));
    for (let i in array) {
        console.log(i);
    }


}
