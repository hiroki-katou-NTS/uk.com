module nts.uk.com.view.ksu001.n {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {
        var screenModel = new viewmodel.ViewModel();
        __viewContext["viewModel"] = screenModel;
        init();
        __viewContext.bind(__viewContext["viewModel"]);
        ko.applyBindings(__viewContext['viewModel'], nts.uk.ui.ig.grid.header.getCell('grid2', 'rank')[0]);

    });
}
function init() {

    $("#grid2").ntsGrid({
        width: '360px',
        height: '400px',
        dataSource: __viewContext["viewModel"].listRank(),
        primaryKey: 'rankCode',
        virtualization: true,
        virtualizationMode: 'continuous',
        columns: [
            { headerText: nts.uk.resource.getText("KSU001_1322"), key: 'rankCode', dataType: 'string', width: '100px' },
            { headerText: nts.uk.resource.getText("KSU001_1323"), key: 'rankName', dataType: 'string', width: '100px' },
            { headerText: nts.uk.resource.getText("KSU001_1324"), key: 'rank', dataType: 'string', width: '150px' ,ntsControl: 'Combobox'},
        ],
        features: [{}],
        ntsFeatures: [{ name: 'CopyPaste' }],
        ntsControls: [
            { name: 'Combobox', options: __viewContext["viewModel"].comboItems, optionsValue: 'code', optionsText: 'name', columns: __viewContext["viewModel"].comboColumns, controlType: 'ComboBox', enable: true }
        ]
    });
}
