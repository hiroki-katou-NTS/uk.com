module cps003.g.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ScreenModel {
        items = (function() {
            var list = [];
            for (var i = 0; i < 500; i++) {
                list.push(new GridItem(i));
            }
            return list;
        })();
        constructor() {
            let self = this;

        }

        start() {
            let self = this;

            let paramA = getShared("CPS003G_ERROR_LIST"), paramC: GridDtoError = getShared("CPS003G_ERROR_LIST");

        }
        pushData() {
            let self = this;

            setShared('CPS003G_VALUE', {});
            self.close();
        }

        close() {
            close();
        }
    }
    class GridItem {
        id: number;
        flag: boolean;
        ruleCode: string;
        combo: string;
        constructor(index: number) {
            this.id = index;
            this.flag = index % 2 == 0;
            this.ruleCode = String(index % 3 + 1);
            this.combo = String(index % 3 + 1);
        }
    }
    var model = new ScreenModel();
    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
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
            {
                name: 'SwitchButtons', options: [{ value: '1', text: 'Option 1' }, { value: '2', text: 'Option 2' }, { value: '3', text: 'Option 3' }],
                optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true
            },
            { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
            { name: 'Button', text: 'Open', click: function() { alert("Button!!"); }, controlType: 'Button' },
            { name: 'DeleteButton', text: 'Delete', controlType: 'DeleteButton', enable: true }]
    });
    $("#grid2").setupSearchScroll("igGrid", true);
    $("#run").on("click", function() {
        var source = $("#grid2").igGrid("option", "dataSource");
        alert(source[1].flag);
    });
    $("#update-row").on("click", function() {
        $("#grid2").ntsGrid("updateRow", 0, { flag: false, ruleCode: '2', combo: '3' });
    });
    $("#enable-ctrl").on("click", function() {
        $("#grid2").ntsGrid("enableNtsControlAt", 1, "combo", "ComboBox");
    });
    $("#disable-ctrl").on("click", function() {
        $("#grid2").ntsGrid("disableNtsControlAt", 1, "combo", "ComboBox");
    });
    $("#disable-all").on("click", function() {
        $("#grid2").ntsGrid("disableNtsControls", "ruleCode", "SwitchButtons");
    });
    $("#enable-all").on("click", function() {
        $("#grid2").ntsGrid("enableNtsControls", "ruleCode", "SwitchButtons");
    });

    interface IModelDto {
    }
}