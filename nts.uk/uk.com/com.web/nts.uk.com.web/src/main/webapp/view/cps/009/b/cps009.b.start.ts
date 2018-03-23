module nts.uk.com.view.cps009.b {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ViewModel();
        __viewContext["viewModel"].start().done(function(data) {
            $("#grid0").ntsGrid({
                width: '300px',
                height: '370px',
                dataSource: __viewContext["viewModel"].itemInitLst || [],
                primaryKey: 'perInfoItemDefId',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: '', key: 'perInfoItemDefId', dataType: 'string', width: '50px', hidden: true },
                    { headerText: '', key: 'isRequired', dataType: 'number', width: '50px', hidden: true },
                    { headerText: '', key: 'disabled', dataType: 'boolean', width: '50px', showHeaderCheckbox: true, ntsControl: 'Checkbox' },
                    { headerText: nts.uk.resource.getText('CPS009_33'), key: 'itemName', dataType: 'string', width: '250px' }
                ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true, tabindex: 2 }],
                features: [
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                ntsFeatures: [{
                    name: 'CellState',
                    rowId: 'rowId',
                    columnKey: 'columnKey',
                    state: 'state',
                    states: __viewContext["viewModel"].state
                }]
            });
            __viewContext.bind(__viewContext["viewModel"]);

        });

    });
}

$(document).delegate("#grid0", "iggridrowsrendered", function(evt, ui) {
    if ($("#grid0").data("igGrid") === undefined) {
        return;
    }
    debugger;
    _.each(ui.owner.dataSource.data(), (x, i) => {
        if (x.disabled) {
            $("#grid0").ntsGrid("disableNtsControlAt",
                x.perInfoItemDefId, "disabled", "CheckBox");
        } else {
            $("#grid0").ntsGrid("enableNtsControlAt",
                x.perInfoItemDefId, "disabled", "CheckBox");
        }
    });
});



