import getCell = nts.uk.ui.ig.grid.header.getCell;
module nts.uk.com.view.cps009.a {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ViewModel();

        __viewContext["viewModel"] = screenModel;
//
//        init();

        __viewContext.bind(__viewContext["viewModel"]);

//        let helpButton = '<button id=\"A2_012\" style=\'margin-left:20px;\' data-bind=\" click: openBDialog\">' + nts.uk.resource.getText('CPS009_22') + '</button>'
//            + '<button style=\'margin-left: 10px;\' data-bind=\"ntsHelpButton: {image: \'helpcps0092.png\',position: \'right top\', enable: true }\">?</button>';
//
//        getCell('grid2', 'refMethodType').append($(helpButton));
//
//        ko.applyBindings(__viewContext['viewModel'], getCell('grid2', 'refMethodType')[0]);

    });
}

//function init() {
//
//    $("#grid2").ntsGrid({
//        width: '700px',
//        height: '400px',
//        dataSource: __viewContext["viewModel"].currentCategory().itemList,
//        primaryKey: 'perInfoItemDefId',
//        virtualization: true,
//        virtualizationMode: 'continuous',
//        virtualrecordsrender: function(evt, ui) {
//            if ($("#grid2").data("igGrid") === undefined) {
//                return;
//            }
//            var ds = ui.owner.dataSource.data();
//            $(ds)
//                .each(
//                function(index, el: any) {
//                    let nameCell = $("#grid2").igGrid("cellAt", 0, index);
//                    let referenceCell = $("#grid2").igGrid("cellAt", 1, index);
//
//                    if (el.isRequired === 1) {
//                        $(nameCell).addClass('requiredCell');
//                        $(referenceCell).addClass('requiredCell');
//                    } else {
//                        $(nameCell).addClass('notrequiredCell');
//                        $(referenceCell).addClass('notrequiredCell');
//                    }
//
//                });
//        },
//        columns: [
//            { headerText: "", key: 'perInfoItemDefId', dataType: 'string', width: '100px', hidden: true },
//            { headerText: '', key: 'isRequired', dataType: 'number', width: '300px', hidden: true },
//            { headerText: nts.uk.resource.getText('CPS009_21'), key: 'itemName', dataType: 'string', width: '100px' },
//            { headerText: '', key: 'refMethodType', dataType: 'number', width: '300px', ntsControl: 'Combobox' },
//            { headerText: nts.uk.resource.getText('CPS009_23'), key: 'value', dataType: 'string', width: '200px' }
//        ],
//        features: [],
//        ntsFeatures: [{ name: 'CopyPaste' },
//            { name: 'CellEdit' }],
//        ntsControls: [
//            { name: 'Combobox', options: __viewContext["viewModel"].comboItems, optionsValue: 'code', optionsText: 'name', columns: __viewContext["viewModel"].comboColumns, controlType: 'ComboBox', enable: true }
//        ]
//    });
//
//    $(document).delegate("#grid2", "ntsgridcontrolvaluechanged", function(evt, ui) {
//
//        console.log(ui);
//
//    });
//
//}
