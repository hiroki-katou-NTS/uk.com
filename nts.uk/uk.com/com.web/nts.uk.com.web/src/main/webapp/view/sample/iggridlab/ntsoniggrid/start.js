var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var gridlist;
            (function (gridlist) {
                __viewContext.ready(function () {
                    var ScreenModel = (function () {
                        function ScreenModel() {
                            //            modes = ko.observableArray([
                            //                { code: '1', name: '四捨五入' },
                            //                { code: '2', name: '切り上げ' },
                            //                { code: '3', name: '切り捨て' }
                            //            ]);
                            //            
                            //            flagTemplate = '<div class="nts-binding" data-bind="ntsCheckBox: { checked: flag }">Enable</div>';
                            this.items = (function () {
                                var list = [];
                                for (var i = 0; i < 500; i++) {
                                    list.push(new GridItem(i));
                                }
                                return list;
                            })();
                        }
                        return ScreenModel;
                    }());
                    var GridItem = (function () {
                        function GridItem(index) {
                            this.id = index;
                            this.flag = index % 2 == 0;
                            this.ruleCode = String(index % 3 + 1);
                            this.combo = String(index % 3 + 1);
                        }
                        return GridItem;
                    }());
                    var model = new ScreenModel();
                    var ItemModel = (function () {
                        function ItemModel(code, name) {
                            this.code = code;
                            this.name = name;
                        }
                        return ItemModel;
                    }());
                    var comboItems = [new ItemModel('1', '基本給'),
                        new ItemModel('2', '役職手当'),
                        new ItemModel('3', '基本給2')];
                    var comboColumns = [{ prop: 'code', length: 4 },
                        { prop: 'name', length: 8 }];
                    $("#grid2").ntsGrid({
                        width: '970px',
                        height: '400px',
                        dataSource: model.items,
                        primaryKey: 'id',
                        virtualization: true,
                        virtualizationMode: 'continuous',
                        columns: [
                            { headerText: 'ID', key: 'id', dataType: 'number', width: '50px' },
                            { headerText: 'FLAG', key: 'flag', dataType: 'boolean', width: '200px', ntsControl: 'Checkbox' },
                            { headerText: 'RULECODE', key: 'ruleCode', dataType: 'string', width: '290px', ntsControl: 'SwitchButtons' },
                            { headerText: 'Combobox', key: 'combo', dataType: 'string', width: '230px', ntsControl: 'Combobox' },
                            { headerText: 'Button', key: 'open', dataType: 'string', width: '80px', unbound: true, ntsControl: 'Button' },
                            { headerText: 'Delete', key: 'delete', dataType: 'string', width: '80px', unbound: true, ntsControl: 'DeleteButton' }
                        ],
                        features: [{ name: 'Sorting', type: 'local' }],
                        ntsControls: [{ name: 'Checkbox', options: { value: 1, text: 'Custom Check' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                            { name: 'SwitchButtons', options: [{ value: '1', text: 'Option 1' }, { value: '2', text: 'Option 2' }, { value: '3', text: 'Option 3' }],
                                optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true },
                            { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
                            { name: 'Button', text: 'Open', click: function () { alert("Button!!"); }, controlType: 'Button' },
                            { name: 'DeleteButton', text: 'Delete', controlType: 'DeleteButton', enable: true }]
                    });
                    $("#run").on("click", function () {
                        var source = $("#grid2").igGrid("option", "dataSource");
                        alert(source[1].flag);
                    });
                    $("#update-row").on("click", function () {
                        $("#grid2").ntsGrid("updateRow", 0, { flag: false, ruleCode: '2', combo: '3' });
                    });
                    $("#enable-ctrl").on("click", function () {
                        $("#grid2").ntsGrid("enableNtsControlAt", 1, "combo", "ComboBox");
                    });
                    $("#disable-ctrl").on("click", function () {
                        $("#grid2").ntsGrid("disableNtsControlAt", 1, "combo", "ComboBox");
                    });
                    this.bind(model);
                });
            })(gridlist = ui.gridlist || (ui.gridlist = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=start.js.map