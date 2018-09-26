module nts.uk.at.view.kal003.b {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext["viewModel"].start().done(function(){
            __viewContext.bind(__viewContext["viewModel"]);
            $("#table-group1condition").ntsFixedTable();
            $("#table-group2condition").ntsFixedTable();
            $('#cbxTypeCheckWorkRecordcategory5').focus();
            $('#cbxTypeCheckWorkRecordcategory7').focus();
            $('#cbxTypeCheckWorkRecordcategory9').focus();
        });
    });
}