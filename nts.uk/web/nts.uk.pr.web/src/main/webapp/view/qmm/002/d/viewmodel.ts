module qmm002.d.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        items: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentItem: KnockoutObservable<Bank>;
        currentCodeList: KnockoutObservableArray<any>;
        INP_001: any;
        INP_002: any;
        INP_003: any;
        INP_004: any;
        isCreated: any;

        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.items = ko.observableArray([]);
            self.currentItem = ko.observable(new Bank("", "", "", ""));
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'bankCode', width: 50, key: 'bankCode' },
                { headerText: 'コード', prop: 'bankName', width: 50, key: 'bankName' }
            ]);

            self.currentCode.subscribe(function(codeChanged) {
                self.currentItem(self.getCode(codeChanged));
                self.isCreated(false);
            });

            self.isCreated = ko.observable(true);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            self.getBankList().done(function(data){
                debugger
                var hadData = self.isEmptyList(data);
                self.isCreated(!hadData);
                if (hadData) {
                    self.currentItem(self.selectedFirst(data[0]));
                }    
                dfd.resolve();
            });
            
            return dfd.promise();
        }

        /**
         * Event button 002
         */
        btn_002(): any {
            var self = this;
            var bankInfo = {
                bankCode: self.currentItem().code(),
                bankName: self.currentItem().name(),
                bankNameKana: self.currentItem().nameKana(),
                memo: self.currentItem().memo()
            };
            
            var dfd = $.Deferred();
            qmm002.d.service.addBank(self.isCreated(), bankInfo).done(function() {
                dfd.resolve();                
            }).fail(function(error) {
                alert(error.message);
            }).then(function() {
                $.when(self.getBankList()).done(function() {
                    self.currentCode(bankInfo.bankCode);    
                });
            });
        }
        
        Delete(): any {
            var self = this;
            var bankInfo = {
                bankCode: self.currentItem().code(),
                bankName: self.currentItem().name(),
                bankNameKana: self.currentItem().nameKana(),
                memo: self.currentItem().memo()
            };
            
            var dfd = $.Deferred();
            qmm002.d.service.removeBank(bankInfo).done(function() {
                dfd.resolve();                
            }).fail(function(error) {
                alert(error);
            }).then(function() {
                $.when(self.getBankList()).done(function() {   
                });
            });
        }

        cleanForm(): any {
            var self = this;
            self.currentItem(new Bank("", "", "", ""));
            self.currentCode("");
            self.isCreated(true);
        }

        getCode(codeChange): Bank {
            var self = this;
            var itemBank = _.find(self.items(), function(item) {
                return item.bankCode.trim() === codeChange.trim();
            });
            
            if (!itemBank) {
                 return new Bank("", "", "", "");
            }
            
            return new Bank(itemBank.bankCode, itemBank.bankName, itemBank.bankNameKana, itemBank.memo);
        }
        
        getBankList() : any {
            var self = this;
            var dfd = $.Deferred();
            qmm002.d.service.getBankList().done(function(data) {    
                self.items(data);            
                dfd.resolve(data);
            }).fail(function(res) {
                // error
            });
            
            return dfd.promise();
        }
        
        /**
         * Check item list had data?
         */
        isEmptyList(data) : boolean {
            return data.length > 0;    
        }
        
        /**
         * Selected first item bank
         */
        selectedFirst(item): Bank {
            var self = this;
            self.currentCode(item.bankCode);
            return new Bank(item.bankCode, item.bankName, item.bankNameKana, item.memo); 
        }
    }

    class Bank {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        nameKana: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;

        constructor(bankCode: string, bankName: string, bankNameKana: string, memo: string) {
            this.code = ko.observable(bankCode);
            this.name = ko.observable(bankName);
            this.nameKana = ko.observable(bankNameKana);
            this.memo = ko.observable(memo);
        }
    }
}