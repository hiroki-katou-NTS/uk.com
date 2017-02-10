

__viewContext.ready(function() {
    let screenModel = new qpp014.k.ScreenModel();
    __viewContext.bind(screenModel);
});
function deleteRow(rowId) {
    let grid = $("#K_LST_001").data("igGrid");
    grid.dataSource.deleteRow(rowId);
    grid.commit();
}