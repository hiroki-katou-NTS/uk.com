// TreeGrid Node
var qpp014;
(function (qpp014) {
    var k;
    (function (k) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                //DatePicker
                //K_INP_001
                self.date_K_INP_001 = ko.observable(new Date('2016/12/01'));
                //K_INP_002
                self.date_K_INP_002 = ko.observable(new Date('2016/12/01'));
                //gridview
                self.items_K_LST_001 = [];
                for (var i_1 = 1; i_1 < 100; i_1++) {
                    self.items_K_LST_001.push({ 'code': '00' + i_1, 'name': '基本給', 'description': 'description' + i_1 });
                }
                self.currentCode_K_LST_001 = ko.observable();
                $("#K_LST_001").igGrid({
                    dataSource: self.items_K_LST_001,
                    primaryKey: 'code',
                    width: '100%',
                    autoCommit: false,
                    features: [
                        {
                            name: 'Paging',
                            type: "local",
                            pageSize: 12
                        },
                        {
                            name: 'Updating',
                            enableAddRow: false,
                            enableDeleteRow: false,
                            editMode: 'none'
                        },
                        {
                            name: "Selection"
                        }
                    ],
                    autoGenerateColumns: false,
                    columns: [
                        { headerText: '', key: 'Delete', dataType: 'string', width: '7%', formatter: makeK_BTN_002 },
                        { headerText: 'function', key: 'Folder', dataType: 'string', width: '7%', formatter: makeK_BTN_003 },
                        { headerText: 'code', key: 'code', dataType: 'string', width: '25%' },
                        { headerText: 'name', key: 'name', dataType: 'string', width: '25%' },
                        { headerText: 'description', key: 'description', dataType: 'string', width: '25%' }
                    ]
                });
                function makeK_BTN_002(value, record) {
                    if (record.code === '005')
                        return '';
                    return '<button class="small K_BTN_002" onclick="deleteRow(\'' + record.code + '\')"   value="Delete row" >Delete</button>';
                }
                function makeK_BTN_003(value, record) {
                    if (record.code === '004')
                        return '';
                    return '<button  onclick="deleteRow(\'' + record.code + '\')" class="K_BTN_003" value="delete" ></button>';
                }
            }
            return ScreenModel;
        }());
        k.ScreenModel = ScreenModel;
        var ItemModel_K_LST_001 = (function () {
            function ItemModel_K_LST_001(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_K_LST_001;
        }());
        k.ItemModel_K_LST_001 = ItemModel_K_LST_001;
    })(k = qpp014.k || (qpp014.k = {}));
})(qpp014 || (qpp014 = {}));
;
