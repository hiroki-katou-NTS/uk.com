module nts.uk.com.view.cps017.b {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();

        __viewContext["viewModel"].start().done(function() {
            checkBox();
            __viewContext.bind(__viewContext["viewModel"]);
        });
    });
}
function checkBox() {
    var previousId = "";
    var columnSettings = [
        { headerText: '', key: 'selectionID', hidden: true },
        { headerText: '', key: 'id', width: '20px;' },
        {
            headerText: nts.uk.resource.getText('CPS017_18'), key: 'initSelection',
            template: "<input style='width:30px, height:40px' class='checkRow initSelection' type='checkbox'"
            + " data-checked='${initSelection}' data-id='${id}' tabindex='4'/>"
        },
        { headerText: nts.uk.resource.getText('CPS017_16'), key: 'selectionCD', readonly: true },
        { headerText: nts.uk.resource.getText('CPS017_17'), key: 'selectionName' }
    ];
    let source = ko.toJS(__viewContext["viewModel"].listSelection);
    $('#item_register_grid2').igGrid({
        dataSource: source,
        primaryKey: "id",
        dataSourceType: "json",
        enableHoverStyles: false,
        autoGenerateColumns: false,
        columns: columnSettings,
        width: "370px",
        height: "270px",
        features: [{
            name: 'Selection',
            mode: 'row',
            activation: false,
        },
            {
                name: "Updating",
                columnSettings: [
                    {
                        columnKey : "id",
                        readOnly: true,
                        required: true,
                        validation: true
                    },
                    {
                        columnKey : "initSelection",
                        readOnly: true,
                        required: true,
                        validation: true
                    },
                    {
                        columnKey : "selectionCD",
                        readOnly: true,
                        required: true,
                        validation: true
                    },
                    {
                        columnKey : "selectionName",
                        readOnly: true,
                        required: true,
                        validation: true
                    }
                ],
                validation: false,
                enableAddRow: false,
                editMode: "cell",
                excelNavigationMode: true,
                enableDeleteRow: false
            }
        ],
        dataRendered: function(evt, ui) {
            $("#item_register_grid2").find("input[type=checkbox]").each(function() {
                let $this = $(this);
                $this.prop('checked', $this.data('checked'));
            });
        }

    });
    $("#item_register_grid2").closest('.ui-iggrid').addClass('nts-gridlist');
    //su kien khi click vao o check box
    $('#item_register_grid2').on("click", ".checkRow.initSelection", function(evt, ui) {
        let _this = $(this),
            data: Array<any> = ko.toJS(__viewContext["viewModel"].listSelection);
        //chuyen toan bo nhung item da chon -> k chon
        _.each(data, function(item) {
            if (item.id != _this.parents('tr').data('id')) {
                $('#item_register_grid2').igGridUpdating("setCellValue", item.id, "initSelection", false);
                item.initSelection = false;
            } else {
                if (previousId === item.id) {
                    previousId = "";
                    item.initSelection = false;
                } else {
                    item.initSelection = true;
                    previousId = item.id;
                }
            }
        });
        //bind lai data
        let source = ko.toJS(data);
        $('#item_register_grid2').igGrid("option", "dataSource", source);
        $('#item_register_grid2').igGrid("dataBind");
    });
}

