module nts.uk.com.view.cps017.a {
    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        __viewContext["viewModel"] = new viewmodel.ViewModel();

        init();

        __viewContext.bind(__viewContext["viewModel"]);

    });

}

function init() {

    $("#item_register_grid").ntsGrid({
        width: '300px',
        height: '400px',
        dataSource: __viewContext["viewModel"].items,
        primaryKey: 'id',
        virtualization: true,
        virtualizationMode: 'continuous',
        columns: [
            { headerText: '', key: 'id', dataType: 'number', width: '30px', ntsControl: 'Label' },
            { headerText: nts.uk.resource.getText('CPS017_18'), key: 'flag', dataType: 'boolean', width: '50px', ntsControl: 'Checkbox' },
            { headerText: nts.uk.resource.getText('CPS017_16'), key: 'combo', dataType: 'string', width: '110px', ntsControl: 'Combobox' },
            { headerText: nts.uk.resource.getText('CPS017_17'), key: 'id', dataType: 'number', width: '70px', ntsControl: 'Label' },
        ],
        features: [{ name: 'Resizing' }],
        ntsFeatures: [{ name: 'CopyPaste' }],
        ntsControls: [
            {
                name: 'Checkbox', options: { value: 1, text: "" }, optionsValue: 'value',
                optionsText: 'text', controlType: 'CheckBox', enable: true
            },
            {
                name: 'Combobox', options: __viewContext["viewModel"].comboItems,
                optionsValue: 'code', optionsText: 'name', columns: __viewContext["viewModel"].comboColumns,
                controlType: 'ComboBox', enable: true
            }
        ]
    });
}