module nts.uk.com.view.cps009.a {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ViewModel();

        __viewContext["viewModel"] = screenModel;

        init();

        __viewContext.bind(__viewContext["viewModel"]);

        let helpButton = '<button id=\"A2_012\" style=\'margin-left:20px;\'>' + nts.uk.resource.getText('CPS009_22') + '</button>'
            + '<button style=\'margin-left: 10px;\' data-bind=\"ntsHelpButton: {image: \'helpcps0092.png\',position: \'right top\', enable: true }\">?</button>';

        nts.uk.ui.ig.grid.header.getCell('grid2', 'combo').append($(helpButton));

        ko.applyBindings(__viewContext['viewModel'], nts.uk.ui.ig.grid.header.getCell('grid2', 'combo')[0]);

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
            { headerText: 'ID', key: 'id', dataType: 'number', width: '200px', ntsControl: 'Label' },
            { headerText: '', key: 'combo', dataType: 'string', width: '80px', ntsControl: 'Combobox' },
            { headerText: nts.uk.resource.getText('CPS009_23'), key: 'id', dataType: 'number', width: '320px', ntsControl: 'Label' },
        ],
        features: [{ name: 'Resizing' }],
        ntsFeatures: [{ name: 'CopyPaste' }],
        ntsControls: [
            { name: 'Combobox', options: __viewContext["viewModel"].comboItems, optionsValue: 'code', optionsText: 'name', columns: __viewContext["viewModel"].comboColumns, controlType: 'ComboBox', enable: true }
        ]
    });

    //    $("#grid2").ntsGrid({
    //        width: '500px',
    //        height: '400px',
    //        dataSource: __viewContext["viewModel"].initValueList(),
    //        primaryKey: 'id',
    //        virtualization: true,
    //        virtualizationMode: 'continuous',
    //        columns: [
    //            { headerText: 'ID', key: 'id', dataType: 'string', width: '10px', hidden: true },
    //            { headerText: nts.uk.resource.getText('CPS009_21'), key: 'itemName', dataType: 'string', width: '150px' },
    //            {
    //                headerText: '',
    //                key: 'comboxValue', dataType: 'string', width: '190px', ntsControl: 'Combobox'
    //            },
    //            { headerText: nts.uk.resource.getText('CPS009_23'), key: 'value', dataType: 'string', width: '160px' },
    //        ],
    //        features: [{ name: 'Sorting', type: 'local' }],
    //        ntsControls: [
    //            {
    //                name: '',
    //                options: __viewContext["viewModel"].itemValueLst(), optionsValue: 'id', optionsText: 'itemName', columns: __viewContext["viewModel"].selectionColumns, controlType: 'ComboBox', enable: true
    //            }]
    //    });
}
