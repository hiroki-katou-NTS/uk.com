/// <reference path="../qmm005.ts"/>
module qmm005.b {
    __viewContext.ready(() => {
        __viewContext["viewModel"] = new ViewModel();
        __viewContext.bind(__viewContext["viewModel"]);
    });
}