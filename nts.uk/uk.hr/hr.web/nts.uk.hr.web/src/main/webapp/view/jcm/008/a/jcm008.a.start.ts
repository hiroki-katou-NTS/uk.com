__viewContext.ready(function() {
    var screenModel = new jcm008.a.ViewModel();
    screenModel.start().done(function(){
        __viewContext.bind(screenModel);
        $('.ntsStartDatePicker').focus();
        screenModel.setScroll();
    });
    $("#retirementDateSetting").on("iggridhidingcolumnhiding", function (e, args) {
        screenModel.hidedColumns.push(args.columnKey);
        console.log(screenModel.hidedColumns);
    });
    $("#retirementDateSetting").on("iggridhidingcolumnshowing", function (e, args) {
        screenModel.hidedColumns = screenModel.hidedColumns.filter(v => v !== args.columnKey); 
        console.log(screenModel.hidedColumns);
    });
});