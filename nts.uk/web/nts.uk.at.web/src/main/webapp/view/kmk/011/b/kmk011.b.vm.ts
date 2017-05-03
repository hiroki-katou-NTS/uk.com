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
        currentCode: KnockoutObservable<string>;
        switchUSe3: KnockoutObservableArray<any>;
        requiredAtr: KnockoutObservable<any>;
        inp_A34: KnockoutObservable<string>;
        divReasonCode: KnockoutObservable<string>;
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
            self.currentCode = ko.observable('');
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'divReasonCode', width: 100  },
                { headerText: '名称', key: 'divReasonContent', width: 150 }
            ]);
            self.dataSource = ko.observableArray([]);
            self.switchUSe3 = ko.observableArray([
                    { code: '1', name: '必須する' },
                    { code: '0', name: '必須しない' },
                ]);
            self.requiredAtr = ko.observable(1);    
            self.inp_A34 = ko.observable('時間１');    
            self.divReasonCode = ko.observable('');
            self.divReasonContent = ko.observable('');
            self.enable = ko.observable(true);
            self.itemDivReason = ko.observable(null);
            //subscribe currentCode
            self.currentCode.subscribe(function(codeChanged) {
                self.itemDivReason(self.findItemDivTime(codeChanged));
                self.divReasonCode(self.itemDivReason().divReasonCode);
                self.divReasonContent(self.itemDivReason().divReasonContent);
                if(self.itemDivReason().requiredAtr === 1){
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
        startPage(): JQueryPromise<any>{
            var self = this;
            self.currentCode('');
            var dfd = $.Deferred();
            var divTimeId = nts.uk.ui.windows.getShared("KMK011_divTimeId");
            service.getAllDivReason(divTimeId).done(function(lstDivReason: Array<model.Item>) {
                self.dataSource(lstDivReason);
                let reasonFirst = _.first(lstDivReason);
                self.currentCode(reasonFirst.divReasonCode);
                dfd.resolve();
            })
            return dfd.promise();
        }
        /**
         * find item Divergence Time is selected
         */
        findItemDivTime(value: string): any {
            let self = this;
            var itemModel = null;
            return _.find(self.dataSource(), function(obj: model.Item) {
                return obj.divReasonCode == value;
            })
        }
        refreshData(){
            var self = this;
            self.divReasonCode(null);
            self.divReasonContent("");
            self.requiredAtr(null);    
        }
        addDivReason(){
            var self = this;
            var divReason = new model.Item(self.divReasonCode(),self.divReasonContent(),self.requiredAtr());
            service.addDivReason(divReason);
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
            divReasonCode: string;
            divReasonContent: string;  
            requiredAtr: number;
            constructor(divReasonCode: string,divReasonContent: string,requiredAtr: number){
                this.divReasonCode = divReasonCode;
                this.divReasonContent = divReasonContent;    
                this.requiredAtr = requiredAtr;
            }      
        }
    }
}