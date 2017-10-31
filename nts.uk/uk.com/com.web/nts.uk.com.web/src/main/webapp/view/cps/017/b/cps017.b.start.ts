module nts.uk.com.view.cps017.b {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();

        __viewContext["viewModel"].start().done(function() {
            //InitIggrid();
            __viewContext.bind(__viewContext["viewModel"]);
        });
    });
}
function InitIggrid() {
    /*
    let __viewContext: any = window["__viewContext"] || {};
    $("#item_register_grid").igGrid({
        columns: [
            { headerText: '', key: 'selectionID', width: 20, hidden: true, },
            {
                headerText: nts.uk.resource.getText('CPS017_18'), key: 'initSelection', width: 100,
                template: "<input data-id='${selectionID}' type='checkbox' tabindex='1'/>"
            },
            { headerText: nts.uk.resource.getText('CPS017_16'), key: 'selectionCD', width: 120 },
            { headerText: nts.uk.resource.getText('CPS017_17'), key: 'selectionName', width: 140 }
        ],
        primaryKey: 'selectionID',
        autoGenerateColumns: false,
        dataSource: __viewContext["viewModel"].listSelection(),
        width: "600px",
        height: "400px",
        features: [],

    });
    $("#item_register_grid").closest('.ui-iggrid').addClass('nts-gridlist');
    $(document).on('click', 'input[type="checkbox"]', function(evt) {
        let id = $(evt.target).parents('tr').data('id'),
            data: Array<any> = __viewContext["viewModel"].listSelection(),
            item = _.find(data, x => x.selectionID == id);
        if ($(evt.target).is(':checked')) {
            item.selected = true;
        }
        else {
            item.selected = false;
        }
    });
   */
}