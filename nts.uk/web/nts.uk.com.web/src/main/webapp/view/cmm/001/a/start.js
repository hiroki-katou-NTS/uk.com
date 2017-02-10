//module cmm001.a {
//    let screenModel = new ScreenModel(); ;
//    screenModel.start().done(function() {
//        __viewContext.bind(screenModel);
//        //screenModel.bindSortable();
//    });
////    __viewContext.ready(function() {
////        let screenModel = new cmm001.a.ScreenModel() ;
////        __viewContext.bind(screenModel);
////    });
//}
__viewContext.ready(function () {
    //import { ZipCodeValidator } from "./ZipCodeValidator";
    var screenModel = new cmm001.a.ScreenModel();
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
    });
    //    __viewContext.bind(screenModel);
    ////    this.bind(screenModel);
});
