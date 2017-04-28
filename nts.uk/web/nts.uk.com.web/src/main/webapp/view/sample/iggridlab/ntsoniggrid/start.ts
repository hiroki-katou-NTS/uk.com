module nts.uk.ui.gridlist {
    
    __viewContext.ready(function () {
    
        class ScreenModel {
            
            modes = ko.observableArray([
                { code: '1', name: '四捨五入' },
                { code: '2', name: '切り上げ' },
                { code: '3', name: '切り捨て' }
            ]);
            
            flagTemplate = '<div class="nts-binding" data-bind="ntsCheckBox: { checked: flag }">Enable</div>';
            
            items = (function () {
                var list = [];
                for (var i = 0; i < 500; i++) {
                    list.push(new GridItem(i));
                }
                return ko.observableArray(list);
            })();
            
            rowsRendered(evt, ui) {
                // 
                _.defer(() => {
                    $('.nts-binding').not('.nts-binding-done').each(function () {
                        var $this = $(this).addClass('.nts-binding-done');
                        var rowIndex = ig.grid.getRowIndexFrom($this);
                        var item = model.items()[rowIndex];
                        ko.applyBindings(item, $(this).closest('tr')[0]);
                        
                        $this.one('remove', function (e) {
                            ko.cleanNode(this);
                        }); 
                    });
                });
            }
        }
        
        class GridItem {
            id: number;
            flag: KnockoutObservable<boolean>;
            ruleCode: KnockoutObservable<string>;
            constructor(index: number) {
                this.id = index;
//                this.flag = ko.observable(index % 2 == 0);
//                this.ruleCode = ko.observable(String(index % 3 + 1));
                this.flag = index % 2 == 0;
                this.ruleCode = String(index % 3 + 1);
            }
        }
        
        var model = new ScreenModel();
        $("#grid2").igGridExt({ 
                            width: '600px',
                            height: '400px',
                            dataSource: model.items(),
                            primaryKey: 'id',
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            columns: [
                                { headerText: 'ID', key: 'id', dataType: 'number', width: '200px' },
                                { headerText: 'FLAG', key: 'flag', dataType: 'boolean', width: '200px', ntsControl: 'Checkbox' },
                                { headerText: 'RULECODE', key: 'ruleCode', dataType: 'string', width: '200px' }
                            ], 
                            features: [{ name: 'Sorting', type: 'local' }, { name: 'Updating', enableAddRow: false, enableDeleteRow: false, editMode: 'none' }],
                            ntsControls: [{ name: 'Checkbox', options: { value: 1, text: 'Custom Check' }, optionsValue: 'value', optionsText: 'text', controlType: 'Checkbox' }]
                            });
        $("#run").on("click", function() {
            var source = $("#grid2").igGrid("option", "dataSource");
            alert(source[0].flag);
        });
        this.bind(model);
    });
}