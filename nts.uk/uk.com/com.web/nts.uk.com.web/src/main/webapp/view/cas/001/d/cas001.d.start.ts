module nts.uk.com.view.cas001.d {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext["viewModel"]);
    });
}
$(function() {
    $("#grid").igGrid({
        columns: [
            {
                headerText: "<input id='selfAuth' type='checkbox'  tabindex='2' >他人</input>", key: 'selfAuth',
                width: "80px", height: "40px", formatter: function(evt: any, row: any) {
                    let cb = $("<input  class = 'checkRow selfAuth' type='checkbox' tabindex='4'/>");
                    cb.attr("data-id", row.roleCode);
                    return cb[0].outerHTML;
                }
            },
            {
                headerText: "<input id='otherAuth' type='checkbox'  tabindex='3'>本人</input>", key: 'otherAuth',
                width: "80px", height: "40px", formatter: function(evt: any, row: any) {
                    let cb = $("<input  class = 'checkRow otherAuth' type='checkbox' tabindex='5'/>");
                    cb.attr("data-id", row.roleCode);
                    return cb[0].outerHTML;
                }
            },
            { headerText: "コード", key: "roleCode", dataType: "string", width: "100px", height: "40px", hidden: true },
            { headerText: "カテゴリ名", key: "roleName", dataType: "string", width: "50px", height: "40px" },
            { headerText: '説明', key: 'description', width: "35px", hidden: true, height: "40px" },

        ],
        primaryKey: 'roleCode',
        autoGenerateColumns: false,
        autoCommit: true,
        dataSource: [],
        width: "300px",
        height: "270px",
        features: [
            {
                name: "Updating",
                enableAddRow: false,
                editMode: "row",
                enableDeleteRow: false,
                columnSettings: [
                    { columnKey: "selfAuth", readOnly: true },
                    { columnKey: "otherAuth", readOnly: true },
                    { columnKey: "roleCode", readOnly: true },
                    { columnKey: "roleName", readOnly: true },
                    { columnKey: "description", readOnly: true },
                ]
            }]
    });
    $(document).on("click", "#selfAuth", function(evt, ui) {
        $("#grid").igGridUpdating("endEdit");
        $("#grid").find("tr").each((index, element) => {
            // change state of all checkbox delete
            $(element).find(".selfAuth").prop("checked", $("#selfAuth").prop("checked")).trigger("change");
        });
    });
    $(document).on("click", "#otherAuth", function(evt, ui) {
        $("#grid").igGridUpdating("endEdit");
        $("#grid").find("tr").each((index, element) => {
            // change state of all checkbox delete
            $(element).find(".otherAuth").prop("checked", $("#otherAuth").prop("checked")).trigger("change");
        });
    });
})