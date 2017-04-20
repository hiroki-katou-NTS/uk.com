var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        __viewContext.ready(function () {
            var screenModel = new nts.qmm017.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
                $(document).delegate("#treeGrid", "igtreegridselectionrowselectionchanging", function (evt, ui) {
                    if (screenModel.dirtyCheckScreenB.isDirty() || screenModel.dirtyCheckScreenC.isDirty()) {
                        evt.preventDefault();
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。")
                            .ifYes(function () {
                            $("#treeGrid").igTreeGridSelection("clearSelection");
                            $("#treeGrid").igTreeGridSelection("selectRow", ui.row.index);
                            screenModel.treeGridHistory().singleSelectedCode(ui.row.id);
                            screenModel.bindDataByChanging(ui.row.id);
                        });
                    }
                    else {
                        screenModel.treeGridHistory().singleSelectedCode(ui.row.id);
                        screenModel.bindDataByChanging(ui.row.id);
                    }
                });
            });
        });
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
