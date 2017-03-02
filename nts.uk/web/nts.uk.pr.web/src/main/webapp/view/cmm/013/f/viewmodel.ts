module cmm013.f.viewmodel {

    export class ScreenModel {
        
        label_002: KnockoutObservable<Labels>;
        label_003: KnockoutObservable<Labels>;
        label_004: KnockoutObservable<Labels>;
        label_005: KnockoutObservable<Labels>;
        label_006: KnockoutObservable<Labels>;
        test: KnockoutObservable<string>;
        
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
        
        listbox: KnockoutObservableArray<viewmodel.model.ListHistoryDto>;
        itemName: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<any>
        isEnable: KnockoutObservable<boolean>;
        itemHist: KnockoutObservable<viewmodel.model.ListHistoryDto>;        
        index_selected: KnockoutObservable<any>;
        
        currentCode: KnockoutObservable<any>;        
        dataSource: KnockoutObservableArray<viewmodel.model.ListPositionDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        
        
        
        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<viewmodel.model.ListPositionDto>;
        index_of_itemDelete: any;
        constructor() {
            var self = this;

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
            self.test = ko.observable('2');
            
            //lst_001
            self.listbox = ko.observableArray([]);
            self.selectedCode = ko.observable(null); 
            self.isEnable = ko.observable(true);    
            self.itemHist = ko.observable(null);
            self.itemName = ko.observable('');
            self.index_selected = ko.observable('');
            
            
            //lst_002
            self.dataSource = ko.observableArray([]);
            self.currentItem = ko.observable(null);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable();           
            //self.multilineeditor = ko.observable(null);           
            self.currentCodeList = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'jobCode', width: 80 },
                { headerText: '名称', key: 'jobName', width: 100 }

            ]);
            
            self.selectedCode.subscribe((function(codeChanged){
                self.itemHist(self.findHist(codeChanged));
                if (self.itemHist() != null) {
                self.index_selected(self.itemHist().historyId);
                    console.log(self.index_selected());
                    //get position
                var dfd = $.Deferred();
                service.findAllPosition(self.index_selected())
                    .done(function(position_arr: Array<model.ListPositionDto>) {
                        self.dataSource(position_arr);
                        self.currentCode(self.dataSource()[0].jobCode);
                        self.inp_002(self.dataSource()[0].jobCode);
                        self.inp_003(self.dataSource()[0].jobName);
                        self.inp_005(self.dataSource()[0].memo);
                    }).fail(function(error){
                        alert(error.message);

                })
                dfd.resolve();
                return dfd.promise();
                }           
            }));
               //inp_x
            self.currentCode.subscribe((function(codeChanged) {
                self.currentItem(self.findPosition(codeChanged));
                if (self.currentItem() != null) {
                    self.inp_002(self.currentItem().jobCode);
                    self.inp_003(self.currentItem().jobName);
                    self.inp_005(self.currentItem().memo);
                }           
            }));

        }
        
//        find history need to show position
        findHist(value: string): any {
            let self = this;
            var itemModel = null;
            _.find(self.listbox(), function(obj: viewmodel.model.ListHistoryDto) {
                if (obj.startDate == value) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }
        
//        find position need to show detail
        findPosition(value: string): any {
            let self = this;
            var itemModel = null;
            _.find(self.dataSource(), function(obj: viewmodel.model.ListPositionDto) {
                if (obj.jobCode == value) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }
        
        getPositionList(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                self.dataSource(position_arr);
                self.inp_002(self.dataSource()[0].jobCode);
                self.inp_003(self.dataSource()[0].jobName);
                self.inp_005(self.dataSource()[0].memo);
                if (self.dataSource().length > 1) {
                    self.currentCode(self.dataSource()[0].jobCode)
                }

                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }
        startPage(): JQueryPromise<any> {
            var self = this;

            // Page load dfd.
            var dfd = $.Deferred();

            // get all history.     
            service.getAllHistory().done(function(history_arr: Array<model.ListHistoryDto>){
                self.listbox(history_arr);    
                self.selectedCode=ko.observable(self.listbox()[0].historyId);            
            }).fail(function(error) {
               alert(error.message);

            })            
                dfd.resolve();
                return dfd.promise();
           


        }
        
        

        


        openBDialog() {
            nts.uk.ui.windows.sub.modal('/view/cmm/013/b/index.xhtml', { title: '画面ID：B', });
        }
        openCDialog() {
            nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml', { title: '画面ID：c', });
        }
        openDDialog() {
            nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '画面ID：D', });
        }
         
        
        
    }





    export class Labels {
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
                new BoxModel(1, '全員参照可能'),
                new BoxModel(2, '全員参照不可'),
                new BoxModel(3, 'ロール毎に設定')
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
                new BoxModel(1, '対象外'),
                new BoxModel(2, '看護師'),
                new BoxModel(3, '準看護師'),
                new BoxModel(4, '看護補助師')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
        }
    }
   export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }


   export class TextEditor_3 {
        texteditor: any;
        constructor() {
            var self = this;
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "10px",
                    textalign: "center"
                })),
                required: ko.observable(true),
                enable: ko.observable(false),
                readonly: ko.observable(false)
            };
        }
    }

    export class SwitchButton {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '可能' },
                { code: '2', name: '不可' },
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }

    export module model {
        
       export class ListHistoryDto{
           startDate: string;
           endDate: string;
           historyId: string;
           constructor(startDate: string, endDate: string, historyId: string){
               var self = this;
               self.startDate = startDate;
               self.endDate = endDate;
               self.historyId = historyId;
           }    
       }
        
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
        
        export class DeletePositionCommand {
            jobCode: string;
            constructor(jobCode: string) {
                this.jobCode = jobCode;
            }

        }
    }
}
