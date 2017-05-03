module kmk011.b.viewmodel {

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
        switchUSe3: KnockoutObservableArray<any>;
        requiredAtr: KnockoutObservable<any>;
        inp_A34: KnockoutObservable<string>;
        divReasoncode: KnockoutObservable<number>;
        divReasonContent: KnockoutObservable<string>;
        enable: KnockoutObservable<boolean>;
        itemDivReason: KnockoutObservable<model.Item>;
        
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
                { headerText: 'コード', key: 'divReasoncode', width: 100  },
                { headerText: '名称', key: 'divReasonContent', width: 150 }
            ]);
            self.dataSource = ko.observableArray([]);
//            self.dataSource = ko.observableArray([
//                new model.Item(1,'月曜日',1),
//                 new model.Item(2,'火曜日',1) ,
//                 new model.Item(3,'水曜日',0),
//                 new model.Item(4,'木曜日',1),
//                 new model.Item(5,'金曜日',0)
//                ]);
            self.switchUSe3 = ko.observableArray([
                    { code: '1', name: '必須する' },
                    { code: '0', name: '必須しない' },
                ]);
            self.requiredAtr = ko.observable(1);    
            self.inp_A34 = ko.observable('時間１');    
            self.divReasoncode = ko.observable(1);
            self.divReasonContent = ko.observable('日通会社');
            self.enable = ko.observable(true);
            self.itemDivReason = ko.observable(null);
                        //subscribe currentCode
            self.currentCode.subscribe(function(codeChanged) {
                self.itemDivReason(self.findItemDivTime(codeChanged));
                self.divReasoncode(self.itemDivReason().divReasoncode);
                self.divReasonContent(self.itemDivReason().divReasonContent);
                if(self.itemDivReason().requiredAtr==1){
                    self.requiredAtr(true);
                }else{
                    self.requiredAtr(false);
                }

            });
        }
        
        /**
         * start page
         * get all divergence reason
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
//            var divTimeId = nts.uk.ui.windows.getShared("KMK011_divTimeId");
            var divTimeId = '1';
            service.getAllDivReason(divTimeId).done(function(lstDivReason: Array<model.Item>) {
                self.dataSource(lstDivReason);
                let reasonFirst = _.first(lstDivReason);
                dfd.resolve();
            })
            return dfd.promise();
        }
        /**
         * find item Divergence Time is selected
         */
        findItemDivTime(value: number): any {
            let self = this;
            var itemModel = null;
            return _.find(self.dataSource(), function(obj: model.Item) {
                return obj.divReasoncode == value;
            })
        }
        refreshData(){
            var self = this;
            self.divReasoncode(null);
            self.divReasonContent("");
            self.requiredAtr(null);    
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
        
        export class Item{
            divReasoncode: number;
            divReasonContent: string;  
            requiredAtr: number;
            constructor(divReasoncode: number,divReasonContent: string,requiredAtr: number){
                this.divReasoncode = divReasoncode;
                this.divReasonContent = divReasonContent;    
                this.requiredAtr = requiredAtr;
            }      
        }
    }
}