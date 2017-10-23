module cps001.f {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new vm.ViewModel();

        __viewContext['viewModel'].start().done(() => {
            init();
            __viewContext.bind(__viewContext['viewModel']);
        });

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
            { headerText: 'ID', key: 'id', dataType: 'string', width: '50px', hidden: true },
            { headerText: nts.uk.resource.getText('CPS001_81'), key: 'fileName', dataType: 'string', width: '500px', ntsControl: 'Link1' },
            { headerText: nts.uk.resource.getText('CPS001_82'), key: 'combo', dataType: 'string', width: '250px', ntsControl: 'Combobox' },
            { headerText: nts.uk.resource.getText('CPS001_83'), key: 'open', dataType: 'string', width: '25px', unbound: true, template: '<button onclick="ButtonClick.call(this)" data-id="${id}">' + nts.uk.resource.getText("CPS001_83") + '</button>' }

        ],
        features: [{ name: 'Sorting', type: 'local' }],
        ntsControls: [
            { name: 'Combobox', options: __viewContext['viewModel'].comboItems, optionsValue: 'id', optionsText: 'name', columns: __viewContext['viewModel'].comboColumns, controlType: 'ComboBox', enable: true },
            { name: 'Button', text: nts.uk.resource.getText('CPS001_83'), click: ButtonClick, controlType: 'Button' },
            { name: 'Link1', controlType: 'LinkLabel' }
        ]
    });

    $(document).delegate(".nts-combo-container", "igcomboselectionchanged", function(evt, ui) {
        var rowId: string = String($(ui.owner.element).closest("tr").data("id"));
        let rowItem = _.find(__viewContext['viewModel'].items, function(x: any) { return x.id == rowId; });
        var comboBoxIdNew = ui.items[0].data.id;

        __viewContext['viewModel'].updateCtgItem(rowItem, comboBoxIdNew);

    });
}

function ButtonClick() {
    let id = $(this).data("id");
    let rowItem = _.find(__viewContext['viewModel'].items, function(x: any) { return x.id == id; });
    __viewContext['viewModel'].deleteItem(rowItem);
}

