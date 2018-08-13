import text = nts.uk.resource.getText;

module nts.uk.com.view.cas001.d {
    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext["viewModel"] = screenModel;
        screenModel.start().done(() => {
            InitIggrid();
            $("#grid").igGrid("option", "dataSource", screenModel.categoryList());
            __viewContext.bind(__viewContext["viewModel"]);
            screenModel.categoryList.subscribe(data => {
                if (data) {
                    $("#grid").igGrid("option", "dataSource", data);
                } else {
                    $("#grid").igGrid("option", "dataSource", []);
                }
            });
            _.defer(() => { $("#input.ntsSearchBox.nts-editor.ntsSearchBox_Component").focus(); });
        });
    });
}

function InitIggrid() {
    let __viewContext: any = window["__viewContext"] || {};
    $("#grid").igGrid({
        columns: [
            { headerText: "Id", key: "categoryId", dataType: "string", width: "100px", height: "40px", hidden: true },
            {
                headerText: text('CAS001_30') + "</br><input class='otherAuth' type='checkbox'  tabindex='2' ></input>",
                key: 'otherAuth', width: "35px", height: "40px",
                template: "<input style='width:30px, height:40px' class='checkRow otherAuth' type='checkbox'"
                + " data-checked='${otherAuth}' data-id='${categoryId}' tabindex='4'/>"
            },
            {
                headerText: text('CAS001_31') + "</br><input class='selfAuth' type='checkbox'  tabindex='3'></input>",
                key: 'selfAuth', width: "35px", height: "40px",
                template: "<input style='width:30px, height:40px'  class='checkRow selfAuth' type='checkbox'"
                + " data-checked='${selfAuth}' data-id='${categoryId}' tabindex='4'/>"
            },
            { headerText: text('CAS001_21'), key: "categoryName", dataType: "string", width: "100px", height: "40px" }

        ],
        primaryKey: 'categoryId',
        autoGenerateColumns: false,
        dataSource: [],
        width: "440px",
        height: "270px",
        features: [{
            name: 'Selection',
            mode: 'row',
            activation: false,
        }
        ],
        dataRendered: function(evt, ui) {
            $("#grid").find("input[type=checkbox]").each(function() {
                let $this = $(this);
                $this.prop('checked', $this.data('checked'));
            });
        }
    });
    $("#grid").closest('.ui-iggrid').addClass('nts-gridlist');

    $(document).on("click", ".selfAuth:not(.checkRow)", function(evt, ui) {
        let _this = $(this);
        $("#grid").find(".checkRow.selfAuth").prop("checked", _this.prop("checked")).trigger("change");
    });

    // khi giá trị checkbox của cột selfAuth thay đổi, giá trị selfAuth của row đó cũng thay đổi theo checkbox
    $(document).on("change", ".selfAuth.checkRow", function(evt, ui) {
        let _this = $(this),
            id = _this.parents('tr').data('id'),
            data: Array<any> = __viewContext["viewModel"].categoryList(),
            item = _.find(data, x => x.categoryId == id);
        if (item) {
            item.selfAuth = _this.prop('checked');
        } else {
            item.selfAuth = _this.removeProp('checked');
        }
    });

    $(document).on("click", ".otherAuth:not(.checkRow)", function(evt, ui) {
        let _this = $(this);

        $("#grid").find(".checkRow.otherAuth").prop("checked", _this.prop("checked")).trigger("change");
    });

    // khi giá trị checkbox của cột otherAuth thay đổi, giá trị otherAuth của row đó cũng thay đổi theo checkbox đó
    $(document).on("change", ".otherAuth.checkRow", function(evt, ui) {
        let _this = $(this),
            id = _this.parents('tr').data('id'),
            data: Array<any> = __viewContext["viewModel"].categoryList(),
            item = _.find(data, x => x.categoryId == id);
        if (item) {
            item.otherAuth = _this.prop('checked');
        } else {
            item.otherAuth = _this.removeProp('checked');
        }
    });
}