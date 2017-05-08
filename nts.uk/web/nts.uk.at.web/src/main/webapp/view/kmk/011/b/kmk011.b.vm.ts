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
        enableCode: KnockoutObservable<boolean>;
        itemDivReason: KnockoutObservable<model.Item>;
        divTimeId: KnockoutObservable<string>;
        index_of_itemDelete: any;
        objectOld: any;
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
            self.requiredAtr = ko.observable(0);    
            self.inp_A34 = ko.observable('時間１');    
            self.divReasonCode = ko.observable('');
            self.divReasonContent = ko.observable('');
            self.enableCode = ko.observable(false);
            self.itemDivReason = ko.observable(null);
            self.divTimeId = ko.observable(null);
            //subscribe currentCode
            self.currentCode.subscribe(function(codeChanged) {
                self.itemDivReason(self.findItemDivTime(codeChanged));
                if(self.itemDivReason()===undefined||self.itemDivReason()==null){
                    return;
                }else{
                    self.objectOld = self.itemDivReason().divReasonCode + self.itemDivReason().divReasonContent + self.itemDivReason().requiredAtr;
                    self.enableCode(false);
                    self.divReasonCode(self.itemDivReason().divReasonCode);
                    self.divReasonContent(self.itemDivReason().divReasonContent);
                    self.requiredAtr(self.itemDivReason().requiredAtr);
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
            self.divTimeId(nts.uk.ui.windows.getShared("KMK011_divTimeId"));
            service.getAllDivReason(self.divTimeId()).done(function(lstDivReason: Array<model.Item>) {
                if(lstDivReason=== undefined || lstDivReason.length == 0){
                    self.dataSource();
                    self.enableCode(true);
                }else{
                    self.dataSource(lstDivReason);
                    let reasonFirst = _.first(lstDivReason);
                    self.currentCode(reasonFirst.divReasonCode);
                }
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
            self.requiredAtr(0);
            self.enableCode(true);
        }
        RegistrationDivReason(){
            var self = this;
            if(self.enableCode()==false){
                let objectNew = self.divReasonCode()+ self.divReasonContent()+self.requiredAtr();
                if(self.objectOld==objectNew){
                    return;
                }else{
                    if(self.checkInput()){
                        self.updateDivReason();
                    }else{
                        return;
                    }
                }
            }else
            if(self.enableCode()==true){//add divergence
                if(self.checkInput()){
                    self.addDivReason();
                }else{
                    return;
                }
            }
        }
        addDivReason(){
            var self = this;
            var divReason = new model.Item(self.divTimeId(),self.divReasonCode(),self.divReasonContent(),self.requiredAtr());
            service.addDivReason(divReason).done(function() {
                    self.getAllDivReasonNew();
                }).fail(function(res) {
                    alert(res.message);
                    dfd.reject(res);
                });
        }
        updateDivReason(){
            var self = this;
            var divReason = new model.Item(self.divTimeId(),self.divReasonCode(),self.divReasonContent(),self.requiredAtr());
            service.updateDivReason(divReason).done(function() {
                    self.getAllDivReasonNew();
                }).fail(function(res) {
                    alert(res.message);
                    dfd.reject(res);
                });
        }
        //get all divergence reason new
        getAllDivReasonNew(){
            var self = this;
            var dfd = $.Deferred<any>();
            self.dataSource();
            service.getAllDivReason(self.divTimeId()).done(function(lstDivReason: Array<model.Item>) {
                self.currentCode('');
                self.dataSource(lstDivReason);
                self.enableCode(false);
                self.currentCode(self.divReasonCode());
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }
        //delete divergence reason
        deleteDivReason(){
            var self = this;
            let divReason = self.itemDivReason();
            self.index_of_itemDelete = self.dataSource().indexOf(self.itemDivReason());
            service.deleteDivReason(divReason).done(function(){
                self.getDivReasonList_afterDelete();
            });
        }
        //get list divergence reason after Delete 1 divergence reason
        getDivReasonList_afterDelete(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            self.dataSource();
            service.getAllDivReason(self.divTimeId()).done(function(lstDivReason: Array<model.Item>) {
                self.dataSource(lstDivReason);

                if (self.dataSource().length > 0) {
                    if (self.index_of_itemDelete === self.dataSource().length) {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].divReasonCode)
                    } else {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete].divReasonCode)
                    }

                } else {
                    self.refreshData();
                }

                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }
        /**
         * check input: divergence reason code and divergence reason content
         */
        checkInput(): boolean {
            var self = this;
            if (self.divTimeId() == '' || self.divReasonContent() == '') {
                alert("nhap day du thong tin");
                return false;
            } else {
                return true;
            }
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
    
        export class Item{
            divTimeId: number;
            divReasonCode: string;
            divReasonContent: string;  
            requiredAtr: number;
            constructor(divTimeId: number,divReasonCode: string,divReasonContent: string,requiredAtr: number){
                this.divTimeId = divTimeId;
                this.divReasonCode = divReasonCode;
                this.divReasonContent = divReasonContent;    
                this.requiredAtr = requiredAtr;
            }      
        }
    }
}