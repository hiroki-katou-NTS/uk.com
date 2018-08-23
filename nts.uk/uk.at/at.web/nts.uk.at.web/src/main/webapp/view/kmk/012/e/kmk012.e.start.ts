module nts.uk.at.view.kmk012.e {
    import getText = nts.uk.resource.getText;

    //When view context ready.
    __viewContext.ready(function() {

        var model = new viewmodel.ScreenModel();

        //When getClosureEmploy done, set data for dataSource on grid.
        model.getClosureEmploy().done(function() {
            //Init data for combobox.
            var comboItems = model.closureEmployDto.closureCdNameList;
            //Insert comboColumnas with value = 0;
            var closureCdNameDto = new viewmodel.ClosureCdNameDto(-1, '');
            comboItems.unshift(closureCdNameDto);
            var comboColumns = [
                { prop: 'name', length: 8 }
            ];

            //View list data on grid.
            $("#gridData").ntsGrid({
                width: '350px',
                height: '380px',
                dataSource: model.items,
                primaryKey: 'code',
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,

                //columns on grid list.
                columns: [
                    { headerText: 'ID', key: 'code', dataType: 'string', width: '50px' },
                    { headerText: getText('KMK012_38'), key: 'name', dataType: 'string', width: '150px' },
                    { headerText: getText('KMK012_39'), key: 'closureId', dataType: 'number', width: '160px', ntsControl: 'Combobox' }
                ],
                features: [{ name: 'Sorting', type: 'local' }],

                //Defind combobox and other control
                ntsControls: [
                    { name: 'Combobox', options: comboItems, optionsValue: 'id', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true }]
            });
           
        });

        //Add function button
        $("#btnRegistry").on("click", function() {
            var source = $("#gridData").igGrid("option", "dataSource");
            
            //Add source data on grid to server
            model.insertDelArray(source);
        });
        
        __viewContext.bind(model);

    });
}