var qpp014;
(function (qpp014) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    $('.func-btn').css('display', 'none');
                    $('#screenB').css('display', 'none');
                    var self = this;
                    self.a_SEL_001_items = ko.observableArray([
                        new PayDayProcessing('基本給1', '基本給'),
                        new PayDayProcessing('基本給2', '役職手当'),
                        new PayDayProcessing('0003', '基本給')
                    ]);
                    self.a_SEL_001_itemSelected = ko.observable('0003');
                    self.b_stepList = [
                        { content: '.step-1' },
                        { content: '.step-2' },
                        { content: '.step-3' }
                    ];
                    self.b_stepSelected = ko.observable({ id: 'step-2', content: '.step-2' });
                    self.d_SEL_001_selectedCode = ko.observable(1);
                    self.d_SEL_002_selectedCode = ko.observable(1);
                    self.d_INP_001 = {
                        value: ko.observable('')
                    };
                    self.d_LST_001_items = [];
                    for (var i_1 = 1; i_1 < 100; i_1++) {
                        self.d_LST_001_items.push({ 'code': '00' + i_1, 'name': '基本給', 'description': 'description' + i_1 });
                    }
                    $("#D_LST_001").igGrid({
                        dataSource: self.d_LST_001_items,
                        primaryKey: 'code',
                        width: '740px',
                        height: '290px',
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
                            { headerText: 'コード', key: 'code', dataType: 'string', width: '17%' },
                            { headerText: '名称', key: 'code', dataType: 'string', width: '17%' },
                            {
                                headerText: '振込元設定', width: '70%',
                                group: [
                                    { headerText: "支払1", key: "code", dataType: "string", width: "13%" },
                                    { headerText: "支払2", key: "name", dataType: "string", width: "13%" },
                                    { headerText: "支払3", key: "description", dataType: "string", width: "13%" },
                                    { headerText: "支払4", key: "description", dataType: "string", width: "13%" },
                                    { headerText: "支払5", key: "description", dataType: "string", width: "14%" }
                                ]
                            }
                        ]
                    });
                    self.d_LST_001_itemSelected = ko.observable(0);
                    self.g_INP_001 = ko.observable(new Date('2016/12/01'));
                    self.g_SEL_001_items = ko.observableArray([
                        new ItemModel_G_SEL_001('��{��1', '��{��'),
                        new ItemModel_G_SEL_001('��{��2', '��E�蓖'),
                        new ItemModel_G_SEL_001('0003', '��{��')
                    ]);
                    self.g_SEL_001_itemSelected = ko.observable('0002');
                    self.g_SEL_002_items = ko.observableArray([
                        new ItemModel_G_SEL_002('��{��1', '��{��'),
                        new ItemModel_G_SEL_002('��{��2', '��E�蓖'),
                        new ItemModel_G_SEL_002('0003', '��{��')
                    ]);
                    self.g_SEL_002_itemSelected = ko.observable('0002');
                    self.g_INP_002 = {
                        value: ko.observable(12)
                    };
                    self.g_SEL_003_itemSelected = ko.observable('0002');
                    self.h_INP_001 = ko.observable(new Date('2016/12/01'));
                    self.h_LST_001_items = ko.observableArray([]);
                    for (var i_2 = 1; i_2 < 100; i_2++) {
                        self.h_LST_001_items.push(new ItemModel_H_LST_001('00' + i_2, '基本給', "description " + i_2));
                    }
                    self.h_LST_001_itemsSelected = ko.observable();
                }
                ScreenModel.prototype.nextScreen = function () {
                    $("#screenA").css("display", "none");
                    $("#screenB").css("display", "");
                    $("#screenB").ready(function () {
                        $(".func-btn").css("display", "");
                    });
                };
                ScreenModel.prototype.backScreen = function () {
                    $("#screenB").css("display", "none");
                    $("#screenA").css("display", "");
                    $(".func-btn").css("display", "none");
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var PayDayProcessing = (function () {
                function PayDayProcessing(companyCode, processingNumber, processingName, displaySet, currentProcessing, bonusAttribute, bonusCurrentProcessing) {
                    this.companyCode = companyCode;
                    this.processingNumber = processingNumber;
                    this.processingName = processingName;
                    this.displaySet = displaySet;
                    this.currentProcessing = currentProcessing;
                    this.bonusAttribute = bonusAttribute;
                    this.bonusCurrentProcessing = bonusCurrentProcessing;
                }
                return PayDayProcessing;
            }());
            viewmodel.PayDayProcessing = PayDayProcessing;
            var PersonCom = (function () {
                function PersonCom() {
                }
                return PersonCom;
            }());
            viewmodel.PersonCom = PersonCom;
            var PersonBankAccount = (function () {
                function PersonBankAccount() {
                }
                return PersonBankAccount;
            }());
            viewmodel.PersonBankAccount = PersonBankAccount;
            var ItemModel_G_SEL_001 = (function () {
                function ItemModel_G_SEL_001(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel_G_SEL_001;
            }());
            viewmodel.ItemModel_G_SEL_001 = ItemModel_G_SEL_001;
            var ItemModel_G_SEL_002 = (function () {
                function ItemModel_G_SEL_002(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel_G_SEL_002;
            }());
            viewmodel.ItemModel_G_SEL_002 = ItemModel_G_SEL_002;
            var ItemModel_H_LST_001 = (function () {
                function ItemModel_H_LST_001(code, name, description) {
                    this.code = code;
                    this.name = name;
                    this.description = description;
                }
                return ItemModel_H_LST_001;
            }());
            viewmodel.ItemModel_H_LST_001 = ItemModel_H_LST_001;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp014.a || (qpp014.a = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.a.viewmodel.js.map