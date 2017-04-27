module kmk011.a.viewmodel {

    export class ScreenModel {
        //A_label_x
        label_002: KnockoutObservable<model.Labels>;
        label_003: KnockoutObservable<model.Labels>;
        label_004: KnockoutObservable<model.Labels>;
        label_005: KnockoutObservable<model.Labels>;
        label_006: KnockoutObservable<model.Labels>;
        sel_002: KnockoutObservable<string>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        dataSource: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        switchUSe2: KnockoutObservableArray<model.DivTime>;
        switchUSe3: KnockoutObservableArray<model.DivTime>;
        switchUSe1: KnockoutObservableArray<model.DivTime>;
        selectUse: KnockoutObservable<any>;
        inp_A34: KnockoutObservable<string>;
        inp_A36: KnockoutObservable<string>;
        inp_A37: KnockoutObservable<string>;
        inp_A39: KnockoutObservable<string>;
        inp_A311: KnockoutObservable<string>;
        inp_A314: KnockoutObservable<string>;
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
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
                {divTimeId: 1, divTimeName: '四捨五入' },
                {divTimeId: 2,divTimeName: '金曜日'} ,
                {divTimeId: 3,divTimeName: '土曜日'}
                ]);
            self.switchUSe1 = ko.observableArray([
                    { code: '1', name: '使用する' },
                    { code: '2', name: '使用しない' },
                ]);
            self.switchUSe2 = ko.observableArray([
                    { code: '1', name: '使用する' },
                    { code: '2', name: '使用しない' },
                ]);
            self.switchUSe3 = ko.observableArray([
                    { code: '1', name: '使用する' },
                    { code: '2', name: '使用しない' },
                ]);
            self.selectUse = ko.observable(1);    
            self.inp_A34 = ko.observable('時間１');    
            self.inp_A36 = ko.observable('富士大学');
            self.inp_A37 = ko.observable('日通会社');
            self.inp_A39 = ko.observable('08:00');
            self.inp_A311 = ko.observable('09:00');
            self.inp_A314 = ko.observable('選択肢を設定');
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
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
            divTimeId: KnockoutObservable<number>;
            divTimeName: KnockoutObservable<string>;  
            constructor(divTimeId: number,divTimeName: string){
                this.divTimeId = ko.observable(divTimeId);
                this.divTimeName = ko.observable(divTimeName);    
            }      
        }
    }
}