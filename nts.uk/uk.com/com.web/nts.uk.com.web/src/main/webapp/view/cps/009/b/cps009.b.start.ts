module nts.uk.com.view.cps009.b {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ViewModel();
        __viewContext["viewModel"].start().done(function(data) {
            init();
            $("#grid_B").igGrid("option", "dataSource", __viewContext["viewModel"].itemInitLst);
            __viewContext.bind(__viewContext["viewModel"]);
            $("#grid_B_table_isCheckBox").focus();
            $('td[aria-describedby*="grid_B_table_itemName"]').attr("tabindex", "-1");
            $("#grid_B_table_itemName").attr("tabindex", "-1");
            $("tr").attr("tabindex", "-1");

        });

    });
}

function init() {
    $("#grid_B").igGrid({
        width: '270px',
        height: '365px',
        dataSource: [],
        primaryKey: 'id',
        virtualization: true,
        virtualizationMode: 'continuous',
        virtualrecordsrender: function(evt, ui) {
            if ($("#grid_B").data("igGrid") === undefined) {
                return;
            }
            var ds = ui.owner.dataSource.data();
            $(ds)
                .each(
                function(index, el: any) {
                    let nameCell = $("#grid_B").igGrid("cellAt", 0, index);
                    let referenceCell = $("#grid_B").igGrid("cellAt", 1, index);
                    if (el.isRequired == 1) {
                        $(nameCell).addClass('requiredCell');
                        $(referenceCell).addClass('requiredCell');
                    } else {
                        $(nameCell).addClass('notrequiredCell');
                        $(referenceCell).addClass('notrequiredCell');
                    }

                });
        },
        columns: [
            { headerText: 'id', key: 'id', width: 10, hidden: true },
            { headerText: 'REQUIRED', key: 'isRequired', width: 10, hidden: true },
            {
                headerText: "<input class='isCheckBox' type='checkbox'></input> tabindex='2'",
                key: 'isCheckBox', width: "35px", height: "40px",
                template: "<input style='width:30px, height:40px' class='checkRow isCheckBox' type='checkbox'"
                + " data-checked='${isCheckBox}' data-id='${id}' tabindex='4'/>"
            },
            { headerText: nts.uk.resource.getText('CPS009_33'), key: 'itemName', width: 150 }
        ],
        features: [],
    });

    $("#grid_B").closest('.ui-iggrid').addClass('nts-gridlist');

    $(document).on("click", ".isCheckBox:not(.checkRow)", function(evt, ui) {
        let _this = $(this);
        $("#grid_B").find(".checkRow.isCheckBox").prop("checked", _this.prop("checked")).trigger("change");
    });

    // khi giá trị checkbox của cột CheckBox thay đổi, giá trị isCheckBox của row đó cũng thay đổi theo checkbox
    $(document).on("change", ".isCheckBox.checkRow", function(evt, ui) {
        let _this = $(this),
            id = _this.parents('tr').data('id'),
            data: Array<any> = __viewContext["viewModel"].itemInitLst,
            item = _.find(data, x => x.id == id);
        if (item) {
            item.isCheckBox = _this.prop('checked');
        } else {
            item.isCheckBox = _this.removeProp('checked');
        }
    });
}