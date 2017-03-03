module qrm001.a.viewmodel {
    export class ScreenModel {
        //Employee Process Value
        employeeInfo: KnockoutObservable<EmployeeInfo>;
        employeeList: KnockoutObservableArray<EmployeeInfo>;
        currentEmployee: KnockoutObservable<EmployeeInfo>;
        currentEmployeeCode: KnockoutObservable<any>;
        currentEmployeeIndex: KnockoutObservable<any>;
        previous: KnockoutObservable<any>;
        next: KnockoutObservable<any>;
        
        
        //Retirement Payment Process Value
        SEL_007: KnockoutObservable<any>;
        INP_014: KnockoutObservable<any>;
        SEL_001: KnockoutObservable<any>;
        INP_002: KnockoutObservable<any>;
        INP_003: KnockoutObservable<any>;
        INP_004: KnockoutObservable<any>;
        INP_005: KnockoutObservable<any>;
        INP_006: KnockoutObservable<any>;
        INP_007: KnockoutObservable<any>;
        INP_008: KnockoutObservable<any>;
        SEL_004: KnockoutObservable<any>;
        SEL_005: KnockoutObservable<any>;
        INP_009: KnockoutObservable<any>;
        INP_010: KnockoutObservable<any>;
        INP_011: KnockoutObservable<any>;
        retirementPaymentKeyList: KnockoutObservableArray<RetirementPaymentKey>;
        retirementPaymentKeyCurrent: KnockoutObservable<RetirementPaymentKey>;
        retirementPaymentInfoList: KnockoutObservableArray<RetirementPaymentInfo>;
        retirementPaymentInfoCurrent: KnockoutObservable<RetirementPaymentInfo>;
        transferBankMoneyList: KnockoutObservableArray<Array<TransferBankMoney>>;
        transferBankMoneyCurrent: KnockoutObservableArray<TransferBankMoney>;
        selectList10: KnockoutObservableArray<any>;
        
        bankTransferList: KnockoutObservableArray<BankTransferInfo>;
        moneyTransferList: KnockoutObservableArray<TransferBankMoney>;
        fullSetSelect: KnockoutObservableArray<any>;
        isUpdate: KnockoutObservable<any>;
        constructor() {
            var self = this;
            //Employee Process 
             self.employeeList = ko.observableArray([
                new EmployeeInfo('A0001','A'),
                new EmployeeInfo('A0002','B'),
                new EmployeeInfo('A0003','C')
            ]);
            self.currentEmployeeIndex = ko.observable(0);
            self.currentEmployeeCode = ko.observable('A0001');
            self.currentEmployee = ko.observable(self.employeeList()[self.currentEmployeeIndex()]);
            self.previous = ko.observable(false);
            self.next = ko.observable(true);
            self.currentEmployeeIndex.subscribe(function(value){
                self.previous((value === 0)?false:true);
                self.next((value === (self.employeeList().length-1))?false:true);
                self.currentEmployee(self.employeeList()[value]);
                self.currentEmployeeCode(self.currentEmployee().personId());
                self.getRetirementPaymentByPersonId(self.currentEmployeeCode());
            });
            
            //Retirement Payment Process
            self.fullSetSelect = ko.observableArray([
                { code: 0, name: '使用しない' },
                { code: 1, name: '支給1' },
                { code: 2, name: '支給2' },
                { code: 3, name: '支給3' },
                { code: 4, name: '支給4' },
                { code: 5, name: '支給5' }]);
            self.bankTransferList = ko.observableArray([
                new BankTransferInfo('bank1', 'branch1', '0001'),
                new BankTransferInfo('bank2', 'branch2', '0002'),
                new BankTransferInfo('bank3', 'branch3', '0003'),
                new BankTransferInfo('bank4', 'branch4', '0004'),
                new BankTransferInfo('bank5', 'branch5', '0005')
            ]);
            self.isUpdate = ko.observable(false);
            self.SEL_007 = ko.observable();
            self.INP_014 = ko.observable();
            self.SEL_001 = ko.observable(); 
            self.INP_002 = ko.observable();
            self.INP_003 = ko.observable();
            self.INP_004 = ko.observable();
            self.INP_005 = ko.observable();
            self.INP_006 = ko.observable();
            self.INP_007 = ko.observable();
            self.INP_008 = ko.observable();
            self.SEL_004 = ko.observable(2);
            self.SEL_005 = ko.observable();
            self.INP_009 = ko.observable();
            self.INP_010 = ko.observable();
            self.INP_011 = ko.observable();
            self.SEL_007.subscribe(function(value){ 
                        let index = _.findIndex(self.retirementPaymentKeyList(),function(o){return o.payDate == value});
                        self.retirementPaymentKeyCurrent(self.retirementPaymentKeyList()[index]);
                        self.retirementPaymentInfoCurrent(self.retirementPaymentInfoList()[index]);
                        self.transferBankMoneyCurrent(self.transferBankMoneyList()[index]); 
                        self.INP_014(value);
                        self.SEL_001(self.retirementPaymentInfoCurrent().trialPeriodSet()); 
                        self.INP_002(self.retirementPaymentInfoCurrent().exclusionYears());
                        self.INP_003(self.retirementPaymentInfoCurrent().additionalBoardYears());
                        self.INP_004(self.retirementPaymentInfoCurrent().boardYears());
                        self.INP_005(self.retirementPaymentInfoCurrent().totalPaymentMoney());
                        self.INP_006(self.retirementPaymentInfoCurrent().deduction1Money());
                        self.INP_007(self.retirementPaymentInfoCurrent().deduction2Money());
                        self.INP_008(self.retirementPaymentInfoCurrent().deduction3Money());
                        self.SEL_004(self.retirementPaymentInfoCurrent().retirementPayOption());
                        self.INP_009(self.retirementPaymentInfoCurrent().incomeTaxMoney());
                        self.INP_010(self.retirementPaymentInfoCurrent().cityTaxMoney());
                        self.INP_011(self.retirementPaymentInfoCurrent().prefectureTaxMoney());
                        self.SEL_005(self.retirementPaymentInfoCurrent().taxCalculationMethod());
                    });
            self.SEL_005.subscribe(function(value){
                if(!value){
                    self.autoCaculator();
                    $(".caculator").css('background-color', '#ffc000');
                } else $(".caculator").css('background-color', '#cee6ff');    
            });
            self.INP_005.subscribe(function(valueinp5){if(!self.SEL_005()){self.autoCaculator();}});
            self.INP_006.subscribe(function(valueinp6){if(!self.SEL_005()){self.autoCaculator();}});
            self.INP_007.subscribe(function(valueinp7){if(!self.SEL_005()){self.autoCaculator();}});
            self.INP_008.subscribe(function(valueinp8){if(!self.SEL_005()){self.autoCaculator();}});
            self.SEL_004.subscribe(function(valuesel4){if(!self.SEL_005()){self.autoCaculator();}}); 
            self.retirementPaymentKeyList = ko.observableArray([]);
            self.retirementPaymentKeyCurrent = ko.observable(new RetirementPaymentKey(null,null));
            self.retirementPaymentInfoList = ko.observableArray([]);
            self.retirementPaymentInfoCurrent = ko.observable(new RetirementPaymentInfo(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0) );
            self.transferBankMoneyList = ko.observableArray([[]]);
            self.transferBankMoneyCurrent = ko.observableArray([
                new TransferBankMoney(0,null),new TransferBankMoney(0,null),new TransferBankMoney(0,null),new TransferBankMoney(0,null),new TransferBankMoney(0,null)
            ]);
            self.selectList10 = ko.observableArray(self.refreshSelect(self.fullSetSelect, self.transferBankMoneyCurrent, 5));
            self.transferBankMoneyCurrent().forEach(function(item){
                item.optionSet.subscribe(function(value){
                    if(value){
                        item.money(null);
                        self.selectList10(self.refreshSelect(self.fullSetSelect, self.transferBankMoneyCurrent, 5));
                    } else {
                        item.money(null);
                        self.selectList10(self.refreshSelect(self.fullSetSelect, self.transferBankMoneyCurrent, 5));
                    }
                });
            });
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.getRetirementPaymentByPersonId(self.currentEmployeeCode()).done(function(){
                dfd.resolve();    
            }).fail(function(){
                dfd.resolve();    
            });
            return dfd.promise(); 
        }
        next_emp() {
            var self = this;
            self.currentEmployeeIndex(self.currentEmployeeIndex()+1);
        }
        previous_emp() {
            var self = this;
            self.currentEmployeeIndex(self.currentEmployeeIndex()-1);
        }
        filter(dataset: KnockoutObservableArray<any>,currentSet: KnockoutObservableArray<TransferBankMoney>,index: number): any{
            var self = this;
            return _.filter(dataset(), function(o) { 
                    return  o.code == 0 ||
                            o.code == currentSet()[index].optionSet() ||
                            o.code !== currentSet()[0].optionSet() && 
                            o.code !== currentSet()[1].optionSet() &&
                            o.code !== currentSet()[2].optionSet() &&  
                            o.code !== currentSet()[3].optionSet() && 
                            o.code !== currentSet()[4].optionSet();
            });
        }
        refreshSelect(source1: KnockoutObservableArray<any>, source2: KnockoutObservableArray<TransferBankMoney>, length: number): any{
            var self = this;
            let array = [];
            for(let i=0;i<length;i++){
                array.push(self.filter(source1, source2, i));    
            }
            return array;        
        }
        manualCaculator(){
            var self = this;
            let totalPaymentMoney = parseInt(self.INP_005());
            let deduction1 = parseInt(self.INP_006());
            let deduction2 = parseInt(self.INP_007());
            let deduction3 = parseInt(self.INP_008());
            let incomeTax = parseInt(self.INP_009());
            let cityTax = parseInt(self.INP_010());
            let prefectureTax = parseInt(self.INP_011());
            let totalDeclarationMoney = 
                (deduction1?deduction1:0)+
                (deduction2?deduction2:0)+
                (deduction3?deduction3:0)+
                (incomeTax?incomeTax:0)+
                (cityTax?cityTax:0)+
                (prefectureTax?prefectureTax:0);
            self.retirementPaymentInfoCurrent().totalDeclarationMoney(totalDeclarationMoney);
            self.retirementPaymentInfoCurrent().actualRecieveMoney(totalPaymentMoney - totalDeclarationMoney); 
        }
        autoCaculator(){
            var self = this;
            let year; // LBL003
            let reduction; // Khau tru
            let totalPaymentMoney = parseInt(self.INP_005());
            let rs = (totalPaymentMoney-reduction)/2;
            let tax = (rs<0)?rs:0; // Thue nghi viec
            let payOption = self.SEL_004();
            let deduction1 = parseInt(self.INP_006());
            let deduction2 = parseInt(self.INP_007());
            let deduction3 = parseInt(self.INP_008());
            let incomeTax;
            if(payOption==2){
                incomeTax = totalPaymentMoney*20.42/100;
            } else { 
                switch(true) {
                    case (1000 <= totalPaymentMoney)&&(totalPaymentMoney < 1950000):
                        incomeTax =  totalPaymentMoney*5/100-0;
                        break;
                    case (1950000 <= totalPaymentMoney)&&(totalPaymentMoney < 3300000):
                        incomeTax =  totalPaymentMoney*10/100-97500;
                        break;
                    case (3300000 <= totalPaymentMoney)&&(totalPaymentMoney < 6950000):
                        incomeTax =  totalPaymentMoney*20/100-427500;
                        break;
                    case (6950000 <= totalPaymentMoney)&&(totalPaymentMoney < 9000000):
                        incomeTax =  totalPaymentMoney*23/100-636000;
                        break;
                    case (9000000 <= totalPaymentMoney)&&(totalPaymentMoney < 18000000):
                        incomeTax =  totalPaymentMoney*33/100-1536000;
                        break;
                    case (18000000 <= totalPaymentMoney)&&(totalPaymentMoney < 40000000):
                        incomeTax =  totalPaymentMoney*40/100-2796000;
                        break;
                    case (40000000 <= totalPaymentMoney):
                        incomeTax =  totalPaymentMoney*45/100-4796000;
                        break;
                    default:
                        incomeTax =  totalPaymentMoney*0-0;
                        break;
                }
            }
            let cityTax = (tax*6/100)*90/100;
            let prefectureTax = (tax*4/100)*90/100;
            let totalDeclarationMoney = parseInt(
                (deduction1?deduction1:0)+
                (deduction2?deduction2:0)+
                (deduction3?deduction3:0)+
                (incomeTax?incomeTax:0)+
                (cityTax?cityTax:0)+
                (prefectureTax?prefectureTax:0));
            self.INP_009(incomeTax);
            self.INP_010(cityTax);
            self.INP_011(prefectureTax);
            self.retirementPaymentInfoCurrent().totalDeclarationMoney(totalDeclarationMoney);
            self.retirementPaymentInfoCurrent().actualRecieveMoney(totalPaymentMoney - totalDeclarationMoney); 
        }
        openDialog(){
            nts.uk.ui.windows.sub.modal('/view/qrm/001/b/index.xhtml', {title: '入力欄の背景色について', dialogClass: "no-close"});
        }
        createCommand(): any{
            var self = this;
            let command = {
                personId: self.retirementPaymentKeyCurrent().personId(),
                payDate: self.isUpdate()?self.SEL_007():self.INP_014(),
                trialPeriodSet: self.SEL_001(),
                exclusionYears: self.INP_002(),
                additionalBoardYears: self.INP_003(),
                boardYears: self.INP_004(),
                totalPaymentMoney: self.INP_005(),
                deduction1Money: self.INP_006(),
                deduction2Money: self.INP_007(),
                deduction3Money: self.INP_008(),
                retirementPayOption: self.SEL_004(),
                taxCalculationMethod: self.SEL_005(),
                incomeTaxMoney: self.INP_009(),
                cityTaxMoney: self.INP_010(),
                prefectureTaxMoney: self.INP_011(),
                totalDeclarationMoney: self.retirementPaymentInfoCurrent().totalDeclarationMoney(),
                actualRecieveMoney: self.retirementPaymentInfoCurrent().actualRecieveMoney(),
                bankTransferOption1: self.transferBankMoneyCurrent()[0].optionSet(),
                Option1Money: self.transferBankMoneyCurrent()[0].money(),
                bankTransferOption2: self.transferBankMoneyCurrent()[1].optionSet(),
                Option2Money: self.transferBankMoneyCurrent()[1].money(),
                bankTransferOption3: self.transferBankMoneyCurrent()[2].optionSet(),
                Option3Money: self.transferBankMoneyCurrent()[2].money(),
                bankTransferOption4: self.transferBankMoneyCurrent()[3].optionSet(),
                Option4Money: self.transferBankMoneyCurrent()[3].money(),
                bankTransferOption5: self.transferBankMoneyCurrent()[4].optionSet(),
                Option5Money: self.transferBankMoneyCurrent()[4].money(),  
                withholdingMeno: self.retirementPaymentInfoCurrent().withholdingMeno(),
                statementMemo: self.retirementPaymentInfoCurrent().statementMemo()
            };
            return command;
        }
        getRetirementPaymentByPersonId(personId){
            var self = this;
            var dfd = $.Deferred();
            qrm001.a.service.getRetirementPaymentList(personId).done(function(data) {
                if(!data.length){ 
                    self.isUpdate(false); 
                    self.retirementPaymentKeyList([new RetirementPaymentKey(personId,'')]);
                    self.retirementPaymentInfoCurrent(new RetirementPaymentInfo(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,null,null));
                    self.transferBankMoneyCurrent([
                        new TransferBankMoney(0,null),new TransferBankMoney(0,null),new TransferBankMoney(0,null),new TransferBankMoney(0,null),new TransferBankMoney(0,null)
                    ]);
                    self.SEL_007('');
                    self.INP_014('');
                    self.SEL_001(0); 
                    self.INP_002(0);
                    self.INP_003(0);
                    self.INP_004(0);
                    self.INP_005(0);
                    self.INP_006(0);
                    self.INP_007(0);
                    self.INP_008(0);
                    self.SEL_004(0);
                    self.SEL_005(0);
                    self.INP_009(0);
                    self.INP_010(0);
                    self.INP_011(0);
                    self.SEL_005.subscribe(function(value){
                        if(!value){
                            self.autoCaculator();
                            $(".caculator").css('background-color', '#ffc000');
                        } else $(".caculator").css('background-color', '#cee6ff');    
                    });
                    self.INP_005.subscribe(function(valueinp5){if(!self.SEL_005()){self.autoCaculator();}});
                    self.INP_006.subscribe(function(valueinp6){if(!self.SEL_005()){self.autoCaculator();}});
                    self.INP_007.subscribe(function(valueinp7){if(!self.SEL_005()){self.autoCaculator();}});
                    self.INP_008.subscribe(function(valueinp8){if(!self.SEL_005()){self.autoCaculator();}});
                    self.SEL_004.subscribe(function(valuesel4){if(!self.SEL_005()){self.autoCaculator();}}); 
                    self.selectList10(self.refreshSelect(self.fullSetSelect, self.transferBankMoneyCurrent, 5));
                }
                else { 
                    self.isUpdate(true);
                    let length = self.retirementPaymentKeyList().length;
                    self.retirementPaymentInfoList.removeAll();
                    self.transferBankMoneyList.removeAll();
                    data.forEach(function(item) {
                        self.retirementPaymentInfoList.push(new RetirementPaymentInfo(
                            item.trialPeriodSet,
                            item.exclusionYears,
                            item.additionalBoardYears,
                            item.boardYears,
                            item.totalPaymentMoney,
                            item.deduction1Money,
                            item.deduction2Money,
                            item.deduction3Money,
                            item.retirementPayOption,
                            item.taxCalculationMethod,
                            item.incomeTaxMoney,
                            item.cityTaxMoney,
                            item.prefectureTaxMoney,
                            item.totalDeclarationMoney,
                            item.actualRecieveMoney,
                            item.withholdingMeno,
                            item.statementMemo));
                        self.transferBankMoneyList.push([
                            new TransferBankMoney(item.bankTransferOption1, item.option1Money),
                            new TransferBankMoney(item.bankTransferOption2, item.option2Money),
                            new TransferBankMoney(item.bankTransferOption3, item.option3Money),
                            new TransferBankMoney(item.bankTransferOption4, item.option4Money),
                            new TransferBankMoney(item.bankTransferOption5, item.option5Money)]);
                        self.retirementPaymentKeyList.push(new RetirementPaymentKey(
                            item.personId,
                            item.payDate));
                    });
                    for(let i=0;i<length;i++) {
                        self.retirementPaymentKeyList.remove(self.retirementPaymentKeyList()[i]);    
                    }
                    self.SEL_007(_.first(self.retirementPaymentKeyList()).payDate);
                }
                dfd.resolve();
            }).fail(function(res) {
                dfd.resolve();
            });
            return dfd.promise();        
        }
        saveData(isUpdate){
            var self = this;
            var dfd = $.Deferred();
            var command = self.createCommand();
            if(isUpdate) {
                nts.uk.ui.dialog.confirm("Do you want to Update ?").ifYes(function(){
                    qrm001.a.service.updateRetirementPaymentInfo(command).done(function(data){
                        nts.uk.ui.dialog.alert("Update Success");
                        dfd.resolve();
                    }).fail(function(res){
                        nts.uk.ui.dialog.alert("Update Fail");
                        dfd.resolve();    
                    });   
                }).ifNo(function(){});
            } else {
                nts.uk.ui.dialog.confirm("Do you want to Register ?").ifYes(function(){
                    qrm001.a.service.registerRetirementPaymentInfo(command).done(function(data){
                        nts.uk.ui.dialog.alert("Register Success");
                        dfd.resolve(); 
                    }).fail(function(res){
                        nts.uk.ui.dialog.alert("Register Fail");
                        dfd.resolve();     
                    });  
                }).ifNo(function(){});
            }
            return dfd.promise();
        }
        deleteData(){
            var self = this;
            var dfd = $.Deferred();
            var command = self.createCommand();
            nts.uk.ui.dialog.confirm("Do you want to Remove ?").ifYes(function(){
                qrm001.a.service.removeRetirementPaymentInfo(command).done(function(data){
                    nts.uk.ui.dialog.alert("Remove Success");
                    dfd.resolve(); 
                }).fail(function(res){
                    nts.uk.ui.dialog.alert("Remove Success");
                    dfd.resolve();
                });  
            }).ifCancel(function(){});   
            return dfd.promise();  
        }
    }
    
    class EmployeeInfo {
        personId: KnockoutObservable<any>;
        name: KnockoutObservable<any>;
        constructor(personId: any, name: any){
            this.personId = ko.observable(personId);
            this.name = ko.observable(name);    
        }   
    }
    
    class BankTransferInfo {
        bankName: KnockoutObservable<any>;
        branchName: KnockoutObservable<any>;
        accountNumber: KnockoutObservable<any>;
        constructor(bankName: any, branchName: any, accountNumber: any){
            this.bankName = ko.observable(bankName);
            this.branchName = ko.observable(branchName);    
            this.accountNumber = ko.observable(accountNumber);
        }   
    }
    
    class RetirementPaymentKey {
        personId: KnockoutObservable<any>;
        payDate: any;
        constructor(personId: any, payDate: any){
            this.personId = ko.observable(personId);    
            this.payDate = payDate;
        }   
    }

    class RetirementPaymentInfo {
        trialPeriodSet: KnockoutObservable<any>;
        exclusionYears: KnockoutObservable<any>;
        additionalBoardYears: KnockoutObservable<any>;
        boardYears: KnockoutObservable<any>;
        totalPaymentMoney: KnockoutObservable<any>;
        deduction1Money: KnockoutObservable<any>;
        deduction2Money: KnockoutObservable<any>;
        deduction3Money: KnockoutObservable<any>;
        retirementPayOption: KnockoutObservable<any>;
        taxCalculationMethod: KnockoutObservable<any>;
        incomeTaxMoney: KnockoutObservable<any>;
        cityTaxMoney: KnockoutObservable<any>;
        prefectureTaxMoney: KnockoutObservable<any>;
        totalDeclarationMoney: KnockoutObservable<any>;
        actualRecieveMoney: KnockoutObservable<any>;
        withholdingMeno: KnockoutObservable<any>;
        statementMemo: KnockoutObservable<any>;
        constructor(
            trialPeriodSet: any,
            exclusionYears: any,
            additionalBoardYears: any,
            boardYears: any,
            totalPaymentMoney: any,
            deduction1Money: any,
            deduction2Money: any,
            deduction3Money: any,
            retirementPayOption: any,
            taxCalculationMethod: any,
            incomeTaxMoney: any,
            cityTaxMoney: any,
            prefectureTaxMoney: any,
            totalDeclarationMoney: any,
            actualRecieveMoney: any,
            withholdingMeno: any,
            statementMemo: any ){
                var self = this;
                self.trialPeriodSet = ko.observable(trialPeriodSet);
                self.exclusionYears = ko.observable(exclusionYears);
                self.additionalBoardYears = ko.observable(additionalBoardYears);
                self.boardYears = ko.observable(boardYears);
                self.totalPaymentMoney = ko.observable(totalPaymentMoney);
                self.deduction1Money = ko.observable(deduction1Money);
                self.deduction2Money = ko.observable(deduction2Money);
                self.deduction3Money = ko.observable(deduction3Money);
                self.retirementPayOption = ko.observable(retirementPayOption);
                self.taxCalculationMethod = ko.observable(taxCalculationMethod);
                self.incomeTaxMoney = ko.observable(incomeTaxMoney);
                self.cityTaxMoney = ko.observable(cityTaxMoney);
                self.prefectureTaxMoney = ko.observable(prefectureTaxMoney);     
                self.totalDeclarationMoney = ko.observable(totalDeclarationMoney);
                self.actualRecieveMoney = ko.observable(actualRecieveMoney);
                self.withholdingMeno = ko.observable(withholdingMeno);
                self.statementMemo = ko.observable(statementMemo);
        }
    }
    
    class TransferBankMoney {
        optionSet: KnockoutObservable<any>;
        money: KnockoutObservable<any>;
        constructor(optionSet: any, money: any){
            this.optionSet = ko.observable(optionSet);
            this.money = ko.observable(money);    
        }   
    }
}
