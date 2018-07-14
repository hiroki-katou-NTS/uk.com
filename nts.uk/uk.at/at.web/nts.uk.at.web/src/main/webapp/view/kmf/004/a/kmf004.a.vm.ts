module nts.uk.at.view.kmf004.a.viewmodel {
    export class ScreenModel {
        sphdList: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        specialHolidayCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isDisable: KnockoutObservable<boolean>;
        editMode: KnockoutObservable<boolean>;
        specialHolidayName: KnockoutObservable<string>;
        targetItemsName: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        grantDateOptions: KnockoutObservableArray<any>;
        selectedGrantDate: any;
        methods: KnockoutObservableArray<any>;
        selectedMethod: KnockoutObservable<number>;
        allowDisappear: KnockoutObservable<boolean>;
        years: KnockoutObservable<number>;
        days: KnockoutObservable<number>;
        dialogDEnable: KnockoutObservable<boolean>;
        yearEnable: KnockoutObservable<boolean>;
        dayEnable: KnockoutObservable<boolean>;
        timeMethods: KnockoutObservableArray<any>;
        selectedTimeMethod: KnockoutObservable<number>;
        limitedDays: KnockoutObservable<number>;
        limitedDaysEnable: KnockoutObservable<boolean>;
        expYears: KnockoutObservable<number>;
        expYearEnable: KnockoutObservable<boolean>;
        expMonth: KnockoutObservable<number>;
        expMonthEnable: KnockoutObservable<boolean>;
        startDate: KnockoutObservable<number>;
        startDateEnable: KnockoutObservable<boolean>;
        endDate: KnockoutObservable<number>;
        endDateEnable: KnockoutObservable<boolean>;
        genderSelected: KnockoutObservable<boolean>;
        empSelected: KnockoutObservable<boolean>;
        clsSelected: KnockoutObservable<boolean>;
        ageSelected: KnockoutObservable<boolean>;
        genderOptions: KnockoutObservableArray<any>;
        genderOptionEnable: KnockoutObservable<boolean>;
        selectedGender: any;
        empLst: KnockoutObservable<string>;
        clsLst: KnockoutObservable<string>;
        empLstEnable: KnockoutObservable<boolean>;
        clsLstEnable: KnockoutObservable<boolean>;
        startAge: KnockoutObservable<number>;
        startAgeEnable: KnockoutObservable<boolean>;
        endAge: KnockoutObservable<number>;
        endAgeEnable: KnockoutObservable<boolean>;
        ageCriteriaCls: KnockoutObservableArray<Items>;
        selectedAgeCriteria: KnockoutObservable<string>;
        ageCriteriaClsEnable: KnockoutObservable<boolean>;
        ageBaseDate: KnockoutObservable<string>;
        ageBaseDateEnable: KnockoutObservable<boolean>;
        targetItems: KnockoutObservableArray<any>;
        
        constructor() {
            let self = this;
            
            self.sphdList = ko.observableArray([]);
            self.targetItems = ko.observableArray([]);

            self.specialHolidayCode = ko.observable("");
            self.isEnable = ko.observable(true);
            self.isDisable = ko.observable(true);
            self.editMode = ko.observable(false);
            self.specialHolidayName = ko.observable("");
            self.targetItemsName = ko.observable("");
            self.memo = ko.observable("");
            self.dialogDEnable = ko.observable(false);
            self.yearEnable = ko.observable(true);
            self.dayEnable = ko.observable(true);
                
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KMF004_5'), key: 'specialHolidayCode', width: 100 },
                { headerText: nts.uk.resource.getText('KMF004_6'), key: 'specialHolidayName', width: 150 }
            ]);
            
            self.currentCode = ko.observable();
            
            self.currentCode.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(value > 0){
                    service.getSpecialHoliday(value).done(function(data) {
                        self.isEnable(true);
                        self.isDisable(false);
                        self.specialHolidayCode(data.specialHolidayCode);
                        self.specialHolidayName(data.specialHolidayName);
                        self.memo(data.memo);
                        self.editMode(true);
                        $("#input-name").focus();
                        nts.uk.ui.errors.clearAll();
                    }).fail(function(res) {
                          
                    });
                }
            });  
            
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: nts.uk.resource.getText('KMF004_11'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: nts.uk.resource.getText('KMF004_12'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-3', title: nts.uk.resource.getText('KMF004_13'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            
            self.selectedTab = ko.observable('tab-1');
            
            self.grantDateOptions = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('KMF004_15') },
                { code: '1', name: nts.uk.resource.getText('KMF004_16') },
                { code: '2', name: nts.uk.resource.getText('KMF004_17') }
            ]);
            
            self.selectedGrantDate = ko.observable(0);
            
            self.methods = ko.observableArray([
                new BoxModel(0, nts.uk.resource.getText('KMF004_19')),
                new BoxModel(1, nts.uk.resource.getText('KMF004_20'))
            ]);
            
            self.selectedMethod = ko.observable(0);
            
            self.allowDisappear = ko.observable(false);
            
            self.years = ko.observable();
            self.days = ko.observable();
            
            self.selectedMethod.subscribe(function(value) {
                nts.uk.ui.errors.clearAll();
                
                if(value == 0) {
                    self.yearEnable(true);
                    self.dayEnable(true);
                    self.dialogDEnable(false);
                } else {
                    self.years("");
                    self.days("");
                    self.yearEnable(false);
                    self.dayEnable(false);
                    self.dialogDEnable(true);
                }
            });
            
            self.timeMethods = ko.observableArray([
                new BoxModel(0, nts.uk.resource.getText('KMF004_28')),
                new BoxModel(1, nts.uk.resource.getText('KMF004_29')),
                new BoxModel(2, nts.uk.resource.getText('KMF004_30')),
                new BoxModel(3, nts.uk.resource.getText('KMF004_31'))
            ]);
            
            self.selectedTimeMethod = ko.observable(0);
            
            self.limitedDays = ko.observable();
            self.limitedDaysEnable = ko.observable(true);
            self.expYears = ko.observable();
            self.expYearEnable = ko.observable(false);
            self.expMonth = ko.observable();
            self.expMonthEnable = ko.observable(false);
            self.startDate = ko.observable();
            self.startDateEnable = ko.observable(false);
            self.endDate = ko.observable();
            self.endDateEnable = ko.observable(false);
            
            self.selectedTimeMethod.subscribe(function(value) {
                nts.uk.ui.errors.clearAll();
                
                switch (value) {
                    case 0:
                        self.limitedDaysEnable(true);
                        self.expYearEnable(false);
                        self.expMonthEnable(false);
                        self.startDateEnable(false);
                        self.endDateEnable(false);
                        self.expYears('');
                        self.expMonth('');
                        self.startDate('');
                        self.endDate('');
                        break;
                    case 1:
                        self.limitedDaysEnable(false);
                        self.expYearEnable(true);
                        self.expMonthEnable(true);
                        self.startDateEnable(false);
                        self.endDateEnable(false);
                        self.limitedDays('');
                        self.startDate('');
                        self.endDate('');
                        break;
                    case 2:
                        self.limitedDaysEnable(false);
                        self.expYearEnable(false);
                        self.expMonthEnable(false);
                        self.startDateEnable(false);
                        self.endDateEnable(false);
                        self.limitedDays('');
                        self.expYears('');
                        self.expMonth('');
                        self.startDate('');
                        self.endDate('');
                        break;
                    case 3:
                        self.limitedDaysEnable(false);
                        self.expYearEnable(false);
                        self.expMonthEnable(false);
                        self.startDateEnable(true);
                        self.endDateEnable(true);
                        self.limitedDays('');
                        self.expYears('');
                        self.expMonth('');
                        break;
                }
            });
            
            self.genderSelected = ko.observable(false);
            self.empSelected = ko.observable(false);
            self.clsSelected = ko.observable(false);
            self.ageSelected = ko.observable(false);
            
            self.genderOptions = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('KMF004_55') },
                { code: '1', name: nts.uk.resource.getText('KMF004_56') }
            ]);
            
            self.selectedGender = ko.observable(0);
            self.genderOptionEnable = ko.observable(false);
            
            self.empLst = ko.observable("");
            self.clsLst = ko.observable("");
            self.empLstEnable = ko.observable(false);
            self.clsLstEnable = ko.observable(false);
            
            self.startAge = ko.observable();
            self.startAgeEnable = ko.observable(false);
            self.endAge = ko.observable();
            self.endAgeEnable = ko.observable(false);
            
            self.ageCriteriaCls = ko.observableArray([
                new Items('0', nts.uk.resource.getText('Enum_ReferenceYear_THIS_YEAR')),
                new Items('1', nts.uk.resource.getText('Enum_ReferenceYear_NEXT_YEAR'))
            ]);
    
            self.selectedAgeCriteria = ko.observable('0');
            self.ageCriteriaClsEnable = ko.observable(false);
            
            self.ageBaseDate = ko.observable('101');
            self.ageBaseDateEnable = ko.observable(false);
            
            self.genderSelected.subscribe(function(value) {
                if(value) {
                    self.genderOptionEnable(true);
                } else {
                    self.genderOptionEnable(false);
                    self.selectedGender(0);
                }
            });
            
            self.empSelected.subscribe(function(value) {
                if(value) {
                    self.empLstEnable(true);
                } else {
                    self.empLstEnable(false);
                    self.empLst("");
                }
            });
            
            self.clsSelected.subscribe(function(value) {
                if(value) {
                    self.clsLstEnable(true);
                } else {
                    self.clsLstEnable(false);
                    self.clsLst("");
                }
            });
            
            self.ageSelected.subscribe(function(value) {
                if(value) {
                    self.startAgeEnable(true);
                    self.endAgeEnable(true);
                    self.ageCriteriaClsEnable(true);
                    self.ageBaseDateEnable(true);
                } else {
                    self.startAgeEnable(false);
                    self.endAgeEnable(false);
                    self.ageCriteriaClsEnable(false);
                    self.ageBaseDateEnable(false);
                    self.startAge("");
                    self.endAge("");
                    self.selectedAgeCriteria('0');
                    self.ageBaseDate('101');
                }
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            $.when(self.getSphdData()).done(function() {
                                    
                if (self.sphdList().length > 0) {
                    self.currentCode(self.sphdList()[0].specialHolidayCode);
                    self.currentCode.valueHasMutated();
                } else {
                    self.clearForm();
                }
                
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);    
            });

            return dfd.promise();
        }
        
        getSphdData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            self.sphdList([]);
            service.findByCid().done(function(data) {
                _.forEach(data, function(item) {
                    self.sphdList.push(new ItemModel(item.specialHolidayCode, item.specialHolidayName));
                });
                
                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        openJDialog() {
            let self = this;
            
            nts.uk.ui.windows.setShared("KMF004_A_SELECTED_ITEMS", self.targetItems);
            
            nts.uk.ui.windows.sub.modal("/view/kmf/004/j/index.xhtml").onClosed(() => {
                self.targetItems = nts.uk.ui.windows.getShared("KMF004_J_SELECTED_FRAME");
                
                
            });
        }
        
        openCDL002Dialog() {
            let self = this;
            
        }
        
        openCDL003Dialog() {
            let self = this;
            
        }
        
        saveSpecialHoliday(): JQueryPromise<any> {
            let self = this;
            nts.uk.ui.errors.clearAll();
            
            $("#input-code").trigger("validate");
            $("#input-name").trigger("validate");
            
            if (nts.uk.ui.errors.hasError()) {
                return;    
            }
            
            nts.uk.ui.block.invisible();
            
            var dataItem : service.SpecialHolidayItem = {
                companyId: "",
                specialHolidayCode: self.specialHolidayCode(),
                specialHolidayName: self.specialHolidayName(),
                memo: self.memo()
            };
            
            if(!self.editMode) {
                service.add(dataItem).done(function(errors) {
                    if (errors && errors.length > 0) {
                        self.addListError(errors);    
                    } else {  
                        self.getSphdData();         
                        self.currentCode(self.specialHolidayCode());
                        self.currentCode.valueHasMutated();
                        
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => { 
                            $("#input-name").focus();
                            self.isDisable(false);
                        });
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            } else {
                service.update(dataItem).done(function(errors) {
                    if (errors && errors.length > 0) {
                        self.addListError(errors);    
                    } else {  
                        self.getSphdData();         
                        self.currentCode(self.specialHolidayCode());
                        self.currentCode.valueHasMutated();
                        
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => { 
                            $("#input-name").focus();
                            self.isDisable(false);
                        });
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
        }
        
        deleteSpecialHoliday() {
            let self = this;
            
            nts.uk.ui.block.invisible();
            
            let count = 0;
            for (let i = 0; i <= self.sphdList().length; i++){
                if(self.sphdList()[i].specialHolidayCode == self.currentCode()){
                    count = i;
                    break;
                }
            }
            
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.remove(self.specialHolidayCode()).done(function() {
                    self.getSphdData().done(function(){
                        // if number of item from list after delete == 0 
                        if(self.sphdList().length==0){
                            self.clearForm();
                            return;
                        }
                        // delete the last item
                        if(count == ((self.sphdList().length))){
                            self.currentCode(self.sphdList()[count-1].specialHolidayCode);
                            return;
                        }
                        // delete the first item
                        if(count == 0 ){
                            self.currentCode(self.sphdList()[0].specialHolidayCode);
                            return;
                        }
                        // delete item at mediate list 
                        else if(count > 0 && count < self.sphdList().length){
                            self.currentCode(self.sphdList()[count].specialHolidayCode);    
                            return;
                        }
                    });
                    
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();      
                });
            }).ifNo(()=> { nts.uk.ui.block.clear(); });
        }

        initSpecialHoliday(): void {
            let self = this;
            self.clearForm();
        }
        
        clearForm() {
            let self = this;
            
            self.editMode(false);
            self.currentCode("");
            self.specialHolidayCode("");
            self.isEnable(false);
            self.isDisable(true);
            self.specialHolidayName("");
            self.targetItemsName("");
            self.memo("");
            
            self.selectedGrantDate(0);
            self.selectedMethod(0);
            self.yearEnable(true);
            self.dayEnable(true);
            self.dialogDEnable(false);
            self.allowDisappear(false);
            self.years("");
            self.days("");
            
            self.selectedTimeMethod(0);
            self.limitedDays('');
            self.expYears('');
            self.expMonth('');
            self.startDate('');
            self.endDate('');
        }

        openDialogD() {
            let self = this;
            
        }
        
        /**
         * Set error
         */
        addListError(errorsRequest: Array<string>) {
            let self = this;
            
            let errors = [];
            _.forEach(errorsRequest, function(err) {
                errors.push({ message: nts.uk.resource.getMessage(err), messageId: err, supplements: {} });
            });

            nts.uk.ui.dialog.bundledErrors({ errors: errors });
        }
    }
    
    class Items {
        code: number;
        name: string;
        
        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;       
        }
    }
    
    class ItemModel {
        specialHolidayCode: number;
        specialHolidayName: string;
        
        constructor(specialHolidayCode: number, specialHolidayName: string) {
            this.specialHolidayCode = specialHolidayCode;
            this.specialHolidayName = specialHolidayName;       
        }
    }
    
    class BoxModel {
        id: number;
        name: string;
        
        constructor(id, name){
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}