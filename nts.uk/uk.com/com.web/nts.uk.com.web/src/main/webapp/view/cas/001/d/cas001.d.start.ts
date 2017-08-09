module nts.uk.com.view.cas001.d {
    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext["viewModel"]);
    });
}

$(function() {
    let __viewContext: any = window["__viewContext"] || {};
    $("#grid").igGrid({
        columns: [
            { headerText: "Id", key: "categoryId", dataType: "string", width: "50px", height: "40px", hidden: true },
            {
                headerText: "<input class='selfAuth' type='checkbox'  tabindex='2' >他人</input>", key: 'selfAuth',
                width: "40px", height: "40px",
                template: "<input  id='a' style='width:30px, height:40px' class='checkRow selfAuth' type='checkbox' data-checked='${selfAuth}' data-id='${categoryId}' tabindex='4'/>"
            },
            {
                headerText: "<input class='otherAuth' type='checkbox'  tabindex='3'>本人</input>", key: 'otherAuth',
                width: "40px", height: "40px",
                template: "<input id='b' style='width:30px, height:40px'  class='checkRow otherAuth' type='checkbox' data-checked='${otherAuth}' data-id='${categoryId}' tabindex='4'/>"
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

    $(document).on("click", ".selfAuth:not(.checkRow)", function(evt, ui) {
        let _this = $(this);

        $("#grid").find(".checkRow.selfAuth").prop("checked", _this.prop("checked")).trigger("change");
    });

    $(document).on("change", ".selfAuth.checkRow", function(evt, ui) {
        let _this = $(this),
            id = _this.parents('tr').data('id'),
            data: Array<any> = __viewContext["viewModel"].categoryList(),
            item = _.find(data, x => x.categoryId == id);

        if (item) {
            item.selfAuth = _this.prop('checked');
        }else{
            item.selfAuth = _this.removeProp('checked');
        }
    });

    $(document).on("click", ".otherAuth:not(.checkRow)", function(evt, ui) {
        let _this = $(this);

        $("#grid").find(".checkRow.otherAuth").prop("checked", _this.prop("checked")).trigger("change");
    });

    $(document).on("change", ".otherAuth.checkRow", function(evt, ui) {
        let _this = $(this),
            id = _this.parents('tr').data('id'),
            data: Array<any> = __viewContext["viewModel"].categoryList(),
            item = _.find(data, x => x.categoryId == id);

        if (item) {
            item.otherAuth = _this.prop('checked');
        }else{
            item.otherAuth = _this.removeProp('checked');
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