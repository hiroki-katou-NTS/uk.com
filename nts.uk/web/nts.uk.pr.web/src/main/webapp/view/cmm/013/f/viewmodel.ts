module cmm013.f.viewmodel {

    export class ScreenModel {
        listbox: KnockoutObservable<ListBox>;
        dataSource: KnockoutObservableArray<viewmodel.model.ListPositionDto>;
        label_002: KnockoutObservable<Labels>;
        label_003: KnockoutObservable<Labels>;
        label_004: KnockoutObservable<Labels>;
        label_005: KnockoutObservable<Labels>;
        label_006: KnockoutObservable<Labels>;
        radiobox: KnockoutObservable<RadioBox>;
        radiobox_2: KnockoutObservable<RadioBox2>;
        inp_002: KnockoutObservable<string>;
        inp_002_enable: KnockoutObservable<boolean>;
        inp_003: KnockoutObservable<string>;
        inp_004: KnockoutObservable<TextEditor_3>;
        inp_005: KnockoutObservable<string>;
        sel_0021: KnockoutObservable<SwitchButton>;
        sel_0022: KnockoutObservable<SwitchButton>;
        sel_0023: KnockoutObservable<SwitchButton>;
        sel_0024: KnockoutObservable<SwitchButton>;
        allowClick: KnockoutObservable<boolean> = ko.observable(true);

        itemList: KnockoutObservableArray<any>;
        isEnable: KnockoutObservable<any>;
        itemName: KnockoutObservable<any>;
        currentCode: KnockoutObservable<String>;
        multilineeditor: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<viewmodel.model.ListPositionDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        
        companys: KnockoutObservableArray<cmm013.a.service.model.PositionDto>;
        constructor() {
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
                { headerText: '郢ｧ�ｽｳ郢晢ｽｼ郢昴�ｻ, key: 'jobCode', width: 80 },
                { headerText: '陷ｷ蜥ｲ�ｽｧ�ｽｰ', key: 'jobName', width: 100 }

            ]);
           
            //inp_x
            self.currentCode.subscribe((function(codeChanged) {
                self.currentItem(self.findObj(codeChanged));
                if (self.currentItem() != null) {
                    self.inp_002(self.currentItem().jobCode);
                    self.inp_003(self.currentItem().jobName);
                    self.inp_005(self.currentItem().memo);
                }           
            }));
        }
        startPage(): JQueryPromise<any> {
            var self = this;

            // Page load dfd.
            var dfd = $.Deferred();

            // Resolve start page dfd after load all data.

            
            service.findAllPosition().done(function(position_arr: Array<model.ListPositionDto>) {
                self.dataSource(position_arr);
                self.currentCode(self.dataSource()[0].jobCode)
                self.inp_002(self.dataSource()[0].jobCode);
                self.inp_003(self.dataSource()[0].jobName);
                self.inp_005(self.dataSource()[0].memo);
                dfd.resolve();

            }).fail(function(error) {
                alert(error.message);

            })
            
                dfd.resolve();
                return dfd.promise();
        }
        
        findObj(value: string): any {
            let self = this;
            var itemModel = null;
            _.find(self.dataSource(), function(obj: viewmodel.model.ListPositionDto) {
                if (obj.jobCode == value) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }

        openBDialog() {
            nts.uk.ui.windows.sub.modal('/view/013/b/index.xhtml', { title: '11111', });
        }
        openCDialog() {
            nts.uk.ui.windows.sub.modal('/view/013/c/index.xhtml', { title: '騾包ｽｻ鬮ｱ�ｽ｢ID繝ｻ蝟�', });
        }
        openDDialog() {
            nts.uk.ui.windows.sub.modal('/view/013/d/index.xhtml', { title: '騾包ｽｻ鬮ｱ�ｽ｢ID繝ｻ蜥ｼ', });
        }
    }

    class ListBox {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;
        constructor() {
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
            self.selectedCode = ko.observable(null)
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);


            $('#list-box').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#list-box').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
        }

    }
    export class ItemModel {
        code: string;
        constructor(code: string) {
            this.code = code;
        }
    }

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
    //                { headerText: '郢ｧ�ｽｳ郢晢ｽｼ郢昴�ｻ, key: 'code', width: 40 },
    //                { headerText: '陷ｷ蜥ｲ�ｽｧ�ｽｰ', key: 'name', width: 150 },
    //               // { headerText: '陟惹ｸ槭�ｻ', prop: 'nodeText', width: 20 }
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

    class Labels {
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.enable = ko.observable(true);
        }
    }
    class RadioBox {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, '陷茨ｽｨ陷ｩ�ｽ｡陷ｿ繧峨�ｻ陷ｿ�ｽｯ髢ｭ�ｽｽ'),
                new BoxModel(2, '陷茨ｽｨ陷ｩ�ｽ｡陷ｿ繧峨�ｻ闕ｳ讎雁ｺ�'),
                new BoxModel(3, '郢晢ｽｭ郢晢ｽｼ郢晢ｽｫ雎亥ｼｱ竊馴坎�ｽｭ陞ｳ繝ｻ)
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
        }
    }
    class RadioBox2 {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, '陝�ｽｾ髮趣ｽ｡陞溘�ｻ),
                new BoxModel(2, '騾ｵ邇厄ｽｭ�ｽｷ陝ｶ�ｽｫ'),
                new BoxModel(3, '雋�荵滓★髫ｴ�ｽｷ陝ｶ�ｽｫ'),
                new BoxModel(4, '騾ｵ邇厄ｽｭ�ｽｷ髯ｬ諛ｷ蜍ｧ陝ｶ�ｽｫ')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
        }
    }
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class TextEditor {
        texteditor: any;
        constructor() {
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
    }

    class TextEditor_2 {
        texteditor: any;
        constructor() {
            var self = this;
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "郢昶�墅慕ｹ晢ｽｳ邵ｲﾂ�郢昴�ｻ縺�邵ｲﾂ�郢晏ｸ吶＞",
                    width: "150px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
    }

    class TextEditor_3 {
        texteditor: any;
        constructor() {
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
    }

    class SwitchButton {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '陷ｿ�ｽｯ髢ｭ�ｽｽ' },
                { code: '2', name: '闕ｳ讎雁ｺ�' },
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }

    export module model {
       export class ListPositionDto {
        jobCode: string;
        jobName: string;
           memo: string;
        constructor(code: string, name: string, memo: string) {
            var self = this;
            self.jobCode = code;
            self.jobName = name;
            self.memo = memo;
        }
    }
    }
}
