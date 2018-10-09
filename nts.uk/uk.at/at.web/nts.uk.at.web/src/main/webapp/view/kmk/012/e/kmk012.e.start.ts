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

            //View list data on grid.
             var items0 = (function() {
                let list = [];
                _.forEach(model.items, function(value) {
                    list.push(new GridItem(value.code, value.closureId, value.name));
                });
                return list;
            })();

            var comboColumns = [{ prop: 'name', length: 12 }];
                    
            $("#gridData").ntsGrid({
                        width: '390px',
                        height: '373px',
                        dataSource: items0,
                        primaryKey: 'code',
                        virtualization: true,
                        virtualizationMode: 'continuous',
                        hidePrimaryKey: true,
                columns: [
                    { headerText: 'ID', key: 'code', dataType: 'string', width: '5px'},
                    { headerText: getText('KMK012_38'), key: 'name1', dataType: 'string', width: '160px' },    
                    { headerText: getText('KMK012_39'), key: 'closureId1', dataType: 'string', width: '210px', ntsControl: 'Combobox'},
                    
                ], 
                features: [{ name: 'Resizing',
                                            columnSettings: [{
                                                columnKey: 'id', allowResizing: true, minimumWidth: 30
                                            }, {
                                                columnKey: 'flag', allowResizing: false 
                                            }] 
                                        },
                                        { 
                                            name: 'Selection',
                                            mode: 'row',
                                            multipleSelection: true
                                        }],
                ntsFeatures: [],
                ntsControls: [
                    { name: 'Combobox', options: comboItems, optionsValue: 'id', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true }]
                });
            });

        //Add function button
        $("#btnRegistry").on("click", function() {
            var source = $("#gridData").igGrid("option", "dataSource");
            var list = [];
            source = _.forEach(source, function(value) {
                list.push({code: value.code, name: value.name1, closureId: value.closureId1});
            })
            //Add source data on grid to server
            model.insertDelArray(list);
        });
                
        __viewContext.bind(model);

    });
    
    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    
    class GridItem {
        code: string;
        closureId1: number;
        name1: string;
        constructor(code: string, closureId: number, name: string) {
            this.code = code;
            this.closureId1 = closureId;
            this.name1 = name;
        }
    }
}