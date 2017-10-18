import getCell = nts.uk.ui.ig.grid.header.getCell;
module nts.uk.com.view.cps009.a {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ViewModel();

        __viewContext["viewModel"] = screenModel;

        init();

        __viewContext.bind(__viewContext["viewModel"]);

        let helpButton = '<button id=\"A2_012\" style=\'margin-left:20px;\' data-bind=\" click: openBDialog\">' + nts.uk.resource.getText('CPS009_22') + '</button>'
            + '<button style=\'margin-left: 10px;\' data-bind=\"ntsHelpButton: {image: \'helpcps0092.png\',position: \'right top\', enable: true }\">?</button>';

        getCell('grid2', 'combo').append($(helpButton));

        ko.applyBindings(__viewContext['viewModel'], getCell('grid2', 'combo')[0]);

    });
}

function init() {

    $("#grid2").ntsGrid({
        width: '600px',
        height: '400px',
        dataSource: __viewContext["viewModel"].items,
        primaryKey: 'id',
        virtualization: true,
        virtualizationMode: 'continuous',
        columns: [
            { headerText: 'ID', key: 'id', dataType: 'number', width: '100px', ntsControl: 'Label' },
            { headerText: '', key: 'combo', dataType: 'string', width: '300px', ntsControl: 'Combobox' },
            { headerText: nts.uk.resource.getText('CPS009_23'), key: 'id', dataType: 'number', width: '200px' },
        ],
        features: [],
        ntsFeatures: [{ name: 'CopyPaste' },
         { name: 'CellEdit' }],
        ntsControls: [
            { name: 'Combobox', options: __viewContext["viewModel"].comboItems, optionsValue: 'code', optionsText: 'name', columns: __viewContext["viewModel"].comboColumns, controlType: 'ComboBox', enable: true }
        ]
    });
}
