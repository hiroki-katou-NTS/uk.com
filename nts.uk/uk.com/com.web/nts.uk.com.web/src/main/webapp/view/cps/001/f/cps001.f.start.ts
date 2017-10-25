module cps001.f {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new vm.ViewModel();
        init();
        __viewContext.bind(__viewContext['viewModel']);

        // focus to first input textbox
        $('input:first').focus();
    });

}


function init() {
    $("#grid2").ntsGrid({
        width: '850px',
        height: '400px',
        dataSource: __viewContext['viewModel'].items,
        primaryKey: 'id',
        virtualization: true,
        virtualizationMode: 'continuous',
        columns: [
            { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', hidden: true },
            { headerText: nts.uk.resource.getText('CPS001_81'), key: 'header2', dataType: 'string', width: '500px', ntsControl: 'Link1' },
            { headerText: nts.uk.resource.getText('CPS001_82'), key: 'combo', dataType: 'string', width: '250px', ntsControl: 'Combobox' },
            { headerText: nts.uk.resource.getText('CPS001_83'), key: 'open', dataType: 'string', width: '25px', unbound: true, ntsControl: 'Button' }

        ],
        features: [{ name: 'Sorting', type: 'local' }],
        ntsControls: [
            { name: 'Combobox', options: __viewContext['viewModel'].comboItems, optionsValue: 'code', optionsText: 'name', columns: __viewContext['viewModel'].comboColumns, controlType: 'ComboBox', enable: true },
            { name: 'Button', text: nts.uk.resource.getText('CPS001_83'), click: function() { alert("Button!!"); }, controlType: 'Button' },
            { name: 'Link1', click: function() { alert('Do something.444'); }, controlType: 'LinkLabel' },
        ]

    });

}