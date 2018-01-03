module nts.uk.at.view.ksm011.b.viewmodel {
    import blockUI = nts.uk.ui.block;

    export class ScreenModel {
        qualificationMark: KnockoutObservable<string>;
        qualificationMarkEnable: KnockoutObservable<boolean>;
        openDialogEnable: KnockoutObservable<boolean>;
        credentialListEnable: KnockoutObservable<boolean>;
        credentialList: KnockoutObservable<string>;
        leftItems: KnockoutObservableArray<ItemModel>;
        rightItems: KnockoutObservableArray<ItemModel>;
        leftColumns: KnockoutObservableArray<any>;
        rightColumns: KnockoutObservableArray<any>;
        currentLCodeList: KnockoutObservableArray<any>;
        currentRCodeList: KnockoutObservableArray<any>;
        
        halfDayCls: KnockoutObservableArray<any>;
        selectedHalfDayCls: KnockoutObservable<number>;
        empSignCls: KnockoutObservableArray<any>;
        selectedEmpSignCls: KnockoutObservable<number>;
        
        obtainCls: KnockoutObservableArray<any>;
        selectedObtain: KnockoutObservable<number>;
        insufficientCls: KnockoutObservableArray<any>;
        selectedInsufficient: KnockoutObservable<number>;

        constructor() {
            var self = this;
            self.qualificationMark = ko.observable("");
            self.qualificationMarkEnable = ko.observable(false);
            self.openDialogEnable = ko.observable(false);
            self.credentialListEnable = ko.observable(false);
            
            self.credentialList = ko.observable("");
            self.leftItems = ko.observableArray([]);
            self.rightItems = ko.observableArray([]);
            
            self.leftColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KSM011_78"), prop: 'code', width: 10, hidden: true },
                { headerText: nts.uk.resource.getText("KSM011_78"), prop: 'name', width: 120, formatter: _.escape }
            ]);
            
            self.currentLCodeList = ko.observableArray([]);
            
            self.rightColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KSM011_79"), prop: 'code', width: 10, hidden: true },
                { headerText: nts.uk.resource.getText("KSM011_79"), prop: 'name', width: 120, formatter: _.escape }
            ]);
            
            self.currentRCodeList = ko.observableArray([]);
            
            self.halfDayCls = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);

            self.selectedHalfDayCls = ko.observable(0);
            
            self.empSignCls = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);

            self.selectedEmpSignCls = ko.observable(0);
            
            self.obtainCls = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);

            self.selectedObtain = ko.observable(0);
            
            self.insufficientCls = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);

            self.selectedInsufficient = ko.observable(0);
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            

            return dfd.promise();
        }
        
        /**
         * Registration function.
         */
        registration() {
            var self = this;
            
        }
        
        openDialog() {
            var self = this;
            
        }
    }
    
    class ItemModel {
       code: number;
       name: string;
       constructor(code: number, name: string) {
           this.code = code;
           this.name = name;
       }
   }
}
