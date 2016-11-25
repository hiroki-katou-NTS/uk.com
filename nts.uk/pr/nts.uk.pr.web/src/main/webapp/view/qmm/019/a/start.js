__viewContext.ready(function () {
    //import { ZipCodeValidator } from "./ZipCodeValidator";
    var screenModel = new qmm019.a.ScreenModel();
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
        screenModel.bindSortable();
    });
    //    __viewContext.bind(screenModel);
    ////    this.bind(screenModel);
});
