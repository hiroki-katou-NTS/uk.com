module nts.qmm017 {
    __viewContext.ready(function() {
        var screenModel = new nts.qmm017.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $(document).delegate("#treeGrid", "igtreegridselectionrowselectionchanging", function(evt, ui) {
                //                screenModel.formulaTree_onSelecting(ui.row.id);
                if (screenModel.dirtyCheckScreenB.isDirty() || screenModel.dirtyCheckScreenC.isDirty()) {
                    evt.preventDefault();
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。")
                        .ifYes(function() {
                            $("#treeGrid").igTreeGridSelection("clearSelection");
                            $("#treeGrid").igTreeGridSelection("selectRow", ui.row.index);
                            screenModel.treeGridHistory().singleSelectedCode(ui.row.id);
                            screenModel.bindDataByChanging(ui.row.id);
                        });
                } else {
                    screenModel.treeGridHistory().singleSelectedCode(ui.row.id);
                    screenModel.bindDataByChanging(ui.row.id);
                }
            });
        });

    });
}