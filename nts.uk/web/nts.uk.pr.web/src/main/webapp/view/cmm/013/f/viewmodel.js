var cmm013;
(function (cmm013) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.allowClick = ko.observable(true);
                    var self = this;
                    self.listbox = ko.observable(new ListBox());
                    self.dataSource = ko.observableArray([]);
                    self.label_002 = ko.observable(new Labels());
                    self.label_003 = ko.observable(new Labels());
                    self.label_004 = ko.observable(new Labels());
                    self.label_005 = ko.observable(new Labels());
                    self.label_006 = ko.observable(new Labels());
                    self.radiobox = ko.observable(new RadioBox());
                    self.radiobox_2 = ko.observable(new RadioBox2());
                    self.inp_002 = ko.observable(null);
                    self.inp_002_enable = ko.observable(false);
                    self.inp_003 = ko.observable(null);
                    self.inp_004 = ko.observable(new TextEditor_3());
                    self.inp_005 = ko.observable(null);
                    self.sel_0021 = ko.observable(new SwitchButton);
                    self.sel_0022 = ko.observable(new SwitchButton);
                    self.sel_0023 = ko.observable(new SwitchButton);
                    self.sel_0024 = ko.observable(new SwitchButton);
                    self.isEnable = ko.observable(true);
                    //lst_002
                    self.currentItem = ko.observable(null);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(null);
                    self.multilineeditor = ko.observable(null);
                    self.currentCodeList = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: '繧ｳ繝ｼ繝・, key: ', jobCode: ', width: 80 }, },
                        { headerText: '蜷咲ｧｰ', key: 'jobName', width: 100 }
                    ]);
                    //inp_x
                    self.currentCode.subscribe((function (codeChanged) {
                        self.currentItem(self.findObj(codeChanged));
                        if (self.currentItem() != null) {
                            self.inp_002(self.currentItem().jobCode);
                            self.inp_003(self.currentItem().jobName);
                            self.inp_005(self.currentItem().memo);
                        }
                    }));
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    // Resolve start page dfd after load all data.
                    service.findAllPosition().done(function (position_arr) {
                        self.dataSource(position_arr);
                        self.currentCode(self.dataSource()[0].jobCode);
                        self.inp_002(self.dataSource()[0].jobCode);
                        self.inp_003(self.dataSource()[0].jobName);
                        self.inp_005(self.dataSource()[0].memo);
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.findObj = function (value) {
                    var self = this;
                    var itemModel = null;
                    _.find(self.dataSource(), function (obj) {
                        if (obj.jobCode == value) {
                            itemModel = obj;
                        }
                    });
                    return itemModel;
                };
                ScreenModel.prototype.openBDialog = function () {
                    nts.uk.ui.windows.sub.modal('/view/cmmhoa/013/b/index.xhtml', { title: '逕ｻ髱｢ID・咤', });
                };
                ScreenModel.prototype.openCDialog = function () {
                    nts.uk.ui.windows.sub.modal('/view/cmmhoa/013/c/index.xhtml', { title: '逕ｻ髱｢ID・喞', });
                };
                ScreenModel.prototype.openDDialog = function () {
                    nts.uk.ui.windows.sub.modal('/view/cmmhoa/013/d/index.xhtml', { title: '逕ｻ髱｢ID・咼', });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ListBox = (function () {
                function ListBox() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new ItemModel('2015/10/01 ~ 9999/12/31'),
                        new ItemModel('2015/01/01 ~ 2014/12/31'),
                        new ItemModel('2000/10/01 ~ 1999/10/31'),
                        new ItemModel('2015/10/01 ~ 9999/12/31'),
                        new ItemModel('2015/10/01 ~ 9999/12/31'),
                        new ItemModel('2015/10/01 ~ 9999/12/31'),
                        new ItemModel('2015/10/01 ~ 9999/12/31'),
                        new ItemModel('2015/10/01 ~ 9999/12/31'),
                    ]);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(3);
                    self.selectedCode = ko.observable(null);
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                    $('#list-box').on('selectionChanging', function (event) {
                        console.log('Selecting value:' + event.originalEvent.detail);
                    });
                    $('#list-box').on('selectionChanged', function (event) {
                        console.log('Selected value:' + event.originalEvent.detail);
                    });
                }
                return ListBox;
            }());
            var ItemModel = (function () {
                function ItemModel(code) {
                    this.code = code;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            //    export class GirdBox{
            //        dataSource: KnockoutObservableArray<any>;
            //        singleSelectedCode: KnockoutObservable<any>;
            //        selectedCodes: KnockoutObservableArray<any>;
            //        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            //        constructor() {
            //            var self = this;
            //           // service.getallPosition().done
            //            self.dataSource = ko.observableArray([
            //                new Node('0001', 'Hanoi Vietnam', 1),
            //                new Node('0003', 'Bangkok Thailand', 4),
            //                new Node('0004', 'Tokyo Japan', 3),
            //                new Node('0005', 'Jakarta Indonesia', 2),
            //                new Node('0002', 'Seoul Korea', 5),
            //                new Node('0006', 'Paris France', 6),
            //                new Node('0007', 'United States', 9),
            //                new Node('0010', 'Beijing China', 8),
            //                new Node('0011', 'London United Kingdom', 10),
            //                new Node('0012', 'hohoh', 7)
            //            ]);
            //            
            //
            //            self.singleSelectedCode = ko.observable();
            //            self.selectedCodes = ko.observableArray([]);
            //            self.columns = ko.observableArray([
            //                { headerText: '繧ｳ繝ｼ繝・, key: 'code', width: 40 },
            //                { headerText: '蜷咲ｧｰ', key: 'name', width: 150 },
            //               // { headerText: '蠎丞・', prop: 'nodeText', width: 20 }
            //            ])
            //        }
            //        
            //    }
            //    export class ItemDto {
            //        companyCode: KnockoutObservable<String>;
            //        insDate: KnockoutObservable<any>;
            //        insCcd: KnockoutObservable<String>;
            //        insScd: KnockoutObservable<String>;
            //        insPg: KnockoutObservable<String>;
            //        updDate: KnockoutObservable<any>;
            //        updCcd: KnockoutObservable<String>;
            //        updScd: KnockoutObservable<String>;
            //        updPg: KnockoutObservable<String>;
            //        jobCode: KnockoutObservable<String>;
            //        jobName: KnockoutObservable<String>;
            //        memo: KnockoutObservable<String>;
            //        strDate: KnockoutObservable<any>;
            //        endDate: KnockoutObservable<any>;
            //        exclusVersion: KnockoutObservable<String>;
            //        jobOutCode: KnockoutObservable<String>;
            //        memo: KnockoutObservable<String>;
            //        historyId: KnockoutObservable<String>;
            //    }
            //    class ListBox2 {
            //        itemList: KnockoutObservableArray<any>;
            //        isEnable: KnockoutObservable<any>;
            //        itemName: KnockoutObservable<any>;
            //        currentCode: KnockoutObservable<any>;
            //        selectedCode: KnockoutObservable<any>;
            //        selectedCodes: KnockoutObservableArray<any>;
            //        listItemDto: Array<ItemDto>;
            //        constructor(listItemDto) {
            //            var self = this;
            //            // set list item dto
            //            self.listItemDto = listItemDto;
            //            self.itemName = ko.observable('');
            //            self.currentCode = ko.observable(5);
            //            self.selectedCode = ko.observable(null)
            //            self.isEnable = ko.observable(true);
            //            self.selectedCodes = ko.observableArray([]);
            //        }
            //    }
            var Labels = (function () {
                function Labels() {
                    this.constraint = 'LayoutCode';
                    var self = this;
                    self.inline = ko.observable(true);
                    self.required = ko.observable(true);
                    self.enable = ko.observable(true);
                }
                return Labels;
            }());
            var RadioBox = (function () {
                function RadioBox() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new BoxModel(1, '蜈ｨ蜩｡蜿ら・蜿ｯ閭ｽ'),
                        new BoxModel(2, '蜈ｨ蜩｡蜿ら・荳榊庄'),
                        new BoxModel(3, '繝ｭ繝ｼ繝ｫ豈弱↓險ｭ螳・))
                    ]);
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                }
                return RadioBox;
            }());
            var RadioBox2 = (function () {
                function RadioBox2() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new BoxModel(1, '蟇ｾ雎｡螟・),, new BoxModel(2, '逵玖ｭｷ蟶ｫ'), new BoxModel(3, '貅也恚隴ｷ蟶ｫ'), new BoxModel(4, '逵玖ｭｷ陬懷勧蟶ｫ'))
                    ]);
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                }
                return RadioBox2;
            }());
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
            var TextEditor = (function () {
                function TextEditor() {
                    var self = this;
                    self.texteditor = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "B0005",
                            width: "40px",
                            textalign: "center"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(false),
                        readonly: ko.observable(false)
                    };
                }
                return TextEditor;
            }());
            var TextEditor_2 = (function () {
                function TextEditor_2() {
                    var self = this;
                    self.texteditor = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "繝√Ε繝ｳ縲繝・ぅ縲繝帙い",
                            width: "150px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                }
                return TextEditor_2;
            }());
            var TextEditor_3 = (function () {
                function TextEditor_3() {
                    var self = this;
                    self.texteditor = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "1",
                            width: "10px",
                            textalign: "center"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(false),
                        readonly: ko.observable(false)
                    };
                }
                return TextEditor_3;
            }());
            var SwitchButton = (function () {
                function SwitchButton() {
                    var self = this;
                    self.roundingRules = ko.observableArray([
                        { code: '1', name: '蜿ｯ閭ｽ' },
                        { code: '2', name: '荳榊庄' },
                    ]);
                    self.selectedRuleCode = ko.observable(1);
                }
                return SwitchButton;
            }());
            var model;
            (function (model) {
                var ListPositionDto = (function () {
                    function ListPositionDto(code, name, memo) {
                        var self = this;
                        self.jobCode = code;
                        self.jobName = name;
                        self.memo = memo;
                    }
                    return ListPositionDto;
                }());
                model.ListPositionDto = ListPositionDto;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = cmm013.f || (cmm013.f = {}));
})(cmm013 || (cmm013 = {}));
