import text = nts.uk.resource.getText;

module nts.uk.com.view.cas001.c {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext["viewModel"]);
    });
}

$(function() {
    $("#roles").igGrid({
        columns: [
            {
                headerText: "", key: 'check', width: "30px", height: "40px",
                template: "<input data-id='${roleId}' type='checkbox' tabindex='1'/>"
            }
            ,
            { headerText: "roleID", key: "roleId", dataType: "string", width: "90px", height: "40px", hidden: true},
            { headerText: text('CAS001_8'), key: "roleCode", dataType: "string", width: "80px", height: "40px" },
            { headerText: text('CAS001_9'), key: "roleName", dataType: "string", width: "100px", height: "40px" },
        ],
        primaryKey: 'roleCode',
        autoGenerateColumns: false,
        autoCommit: true,
        dataSource: [],
        width: "300px",
        height: "300px",
        features: [
            {
                name: "Updating",
                enableAddRow: false,
                editMode: "row",
                enableDeleteRow: false,
                columnSettings: [
                    { columnKey: "check", readOnly: true },
                    { columnKey: "roleCode", readOnly: true },
                    { columnKey: "roleName", readOnly: true }
                ]
            }
        ]
    });


    $(document).on('click', 'input[type="checkbox"]', function(evt) {
        let id = $(evt.target).parents('tr').data('id'),
            data: Array<any> = __viewContext["viewModel"].roleList(),
            item = _.find(data, x => x.roleCode == id);
        if ($(evt.target).is(':checked')) {
            item.selected = true;
        }
        else {
            item.selected = false;
        }
    });
});