module kmk011.a.viewmodel {

    export class ScreenModel {
        //A_label_x
        label_002: KnockoutObservable<model.Labels>;
        label_003: KnockoutObservable<model.Labels>;
        label_004: KnockoutObservable<model.Labels>;
        label_005: KnockoutObservable<model.Labels>;
        label_006: KnockoutObservable<model.Labels>;
        sel_002: KnockoutObservable<string>;
        columns: KnockoutObservableArray<any>;
        dataSource: KnockoutObservableArray<model.Item>;
        currentCode: KnockoutObservable<any>;
        selectUseSet: KnockoutObservableArray<any>;
        inputUseSet: KnockoutObservableArray<any>;
        useSet: KnockoutObservableArray<any>;
        selectUse: KnockoutObservable<any>;
        selectSel: KnockoutObservable<any>;
        selectInp: KnockoutObservable<any>;
        divTimeName: KnockoutObservable<string>;
        inp_A36: KnockoutObservable<string>;
        inp_A37: KnockoutObservable<string>;
        alarmTime: KnockoutObservable<string>;
        errTime: KnockoutObservable<string>;
        inp_A314: KnockoutObservable<string>;
        checkErrInput: KnockoutObservable<boolean>;
        checkErrSelect: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        divTimeId: KnockoutObservable<number>;
        divergenceTimeObj: KnockoutObservableArray<service.model.DivergenceTime>;
        itemDivTime: KnockoutObservable<service.model.DivergenceTime>;
        itemObject: KnockoutObservable<model.Item>;
        constructor() {
            var self = this;
            self.label_002 = ko.observable(new model.Labels());
            self.label_003 = ko.observable(new model.Labels());
            self.label_004 = ko.observable(new model.Labels());
            self.label_005 = ko.observable(new model.Labels());
            self.label_006 = ko.observable(new model.Labels());
            self.sel_002 = ko.observable('');
            self.currentCode = ko.observable(1);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'divTimeId', width: 100  },
                { headerText: '名称', key: 'divTimeName', width: 150 }
            ]);
            self.dataSource = ko.observableArray([
                new model.Item(1,'月曜日'),
                 new model.Item(2,'火曜日') ,
                 new model.Item(3,'水曜日'),
                 new model.Item(4,'木曜日'),
                 new model.Item(5,'金曜日')
                ]);
            self.divergenceTimeObj = ko.observableArray([
                new model.DivTime(1,1,8,9,0,0,1,0),
                new model.DivTime(2,0,11,2,1,1,0,1),
                new model.DivTime(3,1,7,7,1,1,1,0),
                new model.DivTime(4,1,4,8,0,1,0,1),
                new model.DivTime(5,1,6,7,1,0,1,0)
                ]);
            self.useSet = ko.observableArray([
                    { code: '1', name: '使用する' },
                    { code: '0', name: '使用しない' },
                ]);
            self.selectUseSet = ko.observableArray([
                    { code: '1', name: '使用する' },
                    { code: '0', name: '使用しない' },
                ]);
            self.inputUseSet = ko.observableArray([
                    { code: '1', name: '使用する' },
                    { code: '0', name: '使用しない' },
                ]);
            self.selectUse = ko.observable(self.divergenceTimeObj()[0].divTimeUseSet);
            self.selectSel = ko.observable(self.divergenceTimeObj()[0].selUseSet);
            self.selectInp = ko.observable(self.divergenceTimeObj()[0].inpUseSet);
            self.divTimeName = ko.observable(self.dataSource()[0].divTimeName);    
            self.inp_A36 = ko.observable('富士大学');
            self.inp_A37 = ko.observable('日通会社');
            self.alarmTime = ko.observable(self.divergenceTimeObj()[0].alarmTime.toString());
            self.errTime = ko.observable(self.divergenceTimeObj()[0].errTime.toString());
            self.inp_A314 = ko.observable('選択肢を設定');
            if(self.divergenceTimeObj()[0].cancelErrSelReason==1){
                self.checkErrInput = ko.observable(true);
            }else{
                self.checkErrInput = ko.observable(false);    
            }
            if(self.divergenceTimeObj()[0].cancelErrInpReason==1){
                self.checkErrInput(true);
            }else{
                self.checkErrInput(false);    
            }
            
            self.checkErrSelect = ko.observable(true);
            self.enable = ko.observable(true);
            self.divTimeId = ko.observable(1);
            self.itemDivTime = ko.observable(null);
            self.itemObject = ko.observable(null);
            
            //subscribe currentCode
            self.currentCode.subscribe(function(codeChanged) {
                self.itemDivTime(self.findDivTime(codeChanged));
                self.alarmTime(self.itemDivTime().alarmTime.toString());
                self.errTime(self.itemDivTime().errTime.toString());
                self.selectUse(self.itemDivTime().divTimeUseSet);
                self.selectSel(self.itemDivTime().selUseSet);
                self.selectInp(self.itemDivTime().inpUseSet);
                if(self.itemDivTime().cancelErrInpReason==1){
                    self.checkErrInput(true);
                }else{
                    self.checkErrInput(false);    
                }
                if(self.itemDivTime().cancelErrSelReason==1){
                    self.checkErrSelect(true);
                }else{
                    self.checkErrSelect(false);    
                }
                self.itemObject(self.findItemDivTime(codeChanged));
                self.divTimeName(self.itemObject().divTimeName);
                console.log(self.itemObject());
            });
        }
        /**
         * find Divergence Time is selected
         */
        findDivTime(value: number): any {
            let self = this;
            var itemModel = null;
            return _.find(self.divergenceTimeObj(), function(obj: model.Item) {
                return obj.divTimeId == value;
            })
        }
        /**
         * find item Divergence Time is selected
         */
        findItemDivTime(value: number): any {
            let self = this;
            var itemModel = null;
            return _.find(self.dataSource(), function(obj: model.Item) {
                return obj.divTimeId == value;
            })
        }
        openBDialog(){
            var self = this;
            nts.uk.ui.windows.setShared('KMK011_divTimeId', self.divTimeId(), true);
            nts.uk.ui.windows.sub.modal('/view/kmk/011/b/index.xhtml', { title: '選択肢の設定', })    
        }
    }
    export module model{ 
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
    
        export class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
        
        export class DivTime{
            divTimeId: number;
            divTimeUseSet: number;
            alarmTime: number;
            errTime: number;
            selUseSet: number;
            cancelErrSelReason: number;
            inpUseSet: number;
            cancelErrInpReason: number;
            constructor(divTimeId: number,divTimeUseSet: number,
                        alarmTime: number,errTime: number,
                        selUseSet: number,cancelErrSelReason: number,
                        inpUseSet: number,cancelErrInpReason: number){
                var self = this;
                self.divTimeId = divTimeId;
                self.divTimeUseSet = divTimeUseSet;
                self.alarmTime = alarmTime;
                self.errTime = errTime;
                self.selUseSet = selUseSet;
                self.cancelErrSelReason = cancelErrSelReason;
                self.inpUseSet = inpUseSet;
                self.cancelErrInpReason = cancelErrInpReason;
            }
        }
        
        export class Item{
            divTimeId: number;
            divTimeName:string;  
            constructor(divTimeId: number,divTimeName: string){
                this.divTimeId = divTimeId;
                this.divTimeName = divTimeName;    
            }      
        }
    }
}