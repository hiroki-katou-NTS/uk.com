// TreeGrid Node
var qpp014;
(function (qpp014) {
    var d;
    (function (d) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                //switch
                //SEL_001
                self.roundingRules_D_SEL_001 = ko.observableArray([
                    { code: '1', name: '�l�̌ܓ�' },
                    { code: '2', name: '�؂��グ' }
                ]);
                self.selectedRuleCode_D_SEL_001 = ko.observable(1);
                //LST_001
                self.items_D_LST_001 = [];
                for (var i_1 = 1; i_1 < 100; i_1++) {
                    self.items_D_LST_001.push({ 'code': '00' + i_1, 'name': '基本給', 'description': 'description' + i_1 });
                }
                $("#D_LST_001").igGrid({
                    dataSource: self.items_D_LST_001,
                    primaryKey: 'code',
                    width: '800px',
                    height: "380px",
                    autoCommit: false,
                    features: [
                        {
                            name: "Selection"
                        },
                        {
                            name: "RowSelectors",
                            enableRowNumbering: true
                        },
                        {
                            name: 'MultiColumnHeaders'
                        }
                    ],
                    autoGenerateColumns: false,
                    columns: [
                        { headerText: 'col1', key: 'code', dataType: 'string', width: '25%' },
                        { headerText: 'col2', key: 'code', dataType: 'string', width: '25%' },
                        {
                            headerText: 'Group1', width: '50%',
                            group: [
                                { headerText: "SubCol1", key: "code", dataType: "string", width: "20%" },
                                { headerText: "SubCol2", key: "name", dataType: "string", width: "20%" },
                                { headerText: "SubCol3", key: "description", dataType: "string", width: "20%" },
                                { headerText: "SubCol4", key: "description", dataType: "string", width: "20%" },
                                { headerText: "SubCol5", key: "description", dataType: "string", width: "20%" }
                            ]
                        }
                    ]
                });
                self.currentCode_D_LST_001 = ko.observable();
            }
            return ScreenModel;
        }());
        d.ScreenModel = ScreenModel;
    })(d = qpp014.d || (qpp014.d = {}));
})(qpp014 || (qpp014 = {}));
;
