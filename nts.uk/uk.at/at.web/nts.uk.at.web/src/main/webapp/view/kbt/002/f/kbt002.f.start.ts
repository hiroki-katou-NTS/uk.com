var kbt002FModel;

module nts.uk.at.view.kbt002.f {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        kbt002FModel = screenModel;
        
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            
        });
        
        $(document).on("click", ".button1", function(event) {
                var key = $(this).attr("data-value");
//                var primaryKey =  $("#igrid").igGrid("option", "primaryKey");
//                var source = _.cloneDeep( $("#igrid").igGrid("option", "dataSource"));
//                _.remove(source, function(current) {
//                    return _.isEqual(current[primaryKey].toString(), key.toString());
//                });
                screenModel.openDialogG(key);
            });
            
            $(document).on("click", ".button2", function(event) {
                var key = $(this).attr("data-value");
//                var primaryKey =  $("#igrid").igGrid("option", "primaryKey");
//                var source = _.cloneDeep( $("#igrid").igGrid("option", "dataSource"));
//                _.remove(source, function(current) {
//                    return _.isEqual(current[primaryKey].toString(), key.toString());
//                });
                screenModel.execute(key);
            });
            
            $(document).on("click", ".button3", function(event) {
//                var key = $(this).attr("data-value");
//                var primaryKey =  $("#igrid").igGrid("option", "primaryKey");
//                var source = _.cloneDeep( $("#igrid").igGrid("option", "dataSource"));
//                _.remove(source, function(current) {
//                    return _.isEqual(current[primaryKey].toString(), key.toString());
//                });
                screenModel.terminate();
            });
            
            $(document).on("click", ".button4", function() {
                var key = $(this).attr("data-value");
//                var primaryKey =  $("#igrid").igGrid("option", "primaryKey");
//                var source = _.cloneDeep( $("#igrid").igGrid("option", "dataSource"));
//                _.remove(source, function(current) {
//                    return _.isEqual(current[primaryKey].toString(), key.toString());
//                });
                screenModel.openDialogH(key);
            });
    });
}