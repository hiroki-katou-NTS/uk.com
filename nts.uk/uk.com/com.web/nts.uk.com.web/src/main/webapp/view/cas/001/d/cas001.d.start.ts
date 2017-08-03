module nts.uk.com.view.cas001.d {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext["viewModel"]);
    });
}
$(function() {
    $("#grid").igGrid({
        columns: [
            { headerText: "Id", key: "categoryId", dataType: "string", width: "50px", height: "40px", hidden: true },
            {
                headerText: "<input id='selfAuth' type='checkbox'  tabindex='2' >他人</input>", key: 'selfAuth',
                width: "40px", height: "40px",
                template: "<input  id='a' style='width:30px, height:40px' class = 'checkRow selfAuth' type='checkbox' data-checked='${selfAuth}' data-id='${categoryId}' tabindex='4'/>"
            },
            {
                headerText: "<input id='otherAuth' type='checkbox'  tabindex='3'>本人</input>", key: 'otherAuth',
                width: "40px", height: "40px",
                template: "<input id='b' style='width:30px, height:40px'  class = 'checkRow otherAuth' type='checkbox' data-checked='${otherAuth}' data-id='${categoryId}' tabindex='4'/>"
            },
            { headerText: "コード", key: "categoryCode", dataType: "string", width: "80px", height: "40px" },
            { headerText: "カテゴリ名", key: "categoryName", dataType: "string", width: "100px", height: "40px" }

        ],
        primaryKey: 'categoryId',
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
                    { columnKey: "categoryId", readOnly: true },
                    { columnKey: "selfAuth", readOnly: true },
                    { columnKey: "otherAuth", readOnly: true },
                    { columnKey: "categoryCode", readOnly: true },
                    { columnKey: "categoryName", readOnly: true }
                ]
            }]
    });
    $(document).on("click", "#selfAuth", function(evt, ui) {
        $("#grid").igGridUpdating("endEdit");
        $("#grid").find("tr").each((index, element) => {
            $(element).find(".selfAuth").prop("checked", $("#selfAuth").prop("checked")).trigger("change");
        });
    });
    $(document).on("click", "#otherAuth", function(evt, ui) {
        $("#grid").igGridUpdating("endEdit");
        $("#grid").find("tr").each((index, element) => {
            $(element).find(".otherAuth").prop("checked", $("#otherAuth").prop("checked")).trigger("change");
        });
    });

    $(document).on('click', '#a', function(evt) {
        let id = $(evt.target).parents('tr').data('id'),
            data: Array<any> = __viewContext["viewModel"].categoryList(),
            item = _.find(data, x => x.categoryId == id);
        if ($(evt.target).is(':checked')) {
            item.Selfselected = true;
        }
        else {
            item.Selfselected = false;
        }
    });

    $(document).on('click', '#b', function(evt) {
        let id = $(evt.target).parents('tr').data('id'),
            data: Array<any> = __viewContext["viewModel"].categoryList(),
            item = _.find(data, x => x.categoryId == id);
        if ($(evt.target).is(':checked')) {
            item.Otherselected = true;
        }
        else {
            item.Otherselected = false;
        }
    });
    $("#grid").igGrid({
        dataRendered: function(evt, ui) {
            $("#grid").find("input[type=checkbox]").each(function() {
                let $this = $(this);
                $this.prop('checked', $this.data('checked'));
            });
        }
    });
})