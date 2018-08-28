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
        empLst: KnockoutObservableArray<any> = ko.observableArray([]);
        clsLst: KnockoutObservableArray<any>= ko.observableArray([]);
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
        selectedTargetItems: any;
        listSpecialHlFrame: KnockoutObservableArray<any>;
        listAbsenceFrame: KnockoutObservableArray<any>;
        targetItems: KnockoutObservableArray<any>; 
        cdl002Name: KnockoutObservable<String>;
        cdl003Name: KnockoutObservable<String>;
        yearReq: KnockoutObservable<boolean> = ko.observable(true);
        dayReq: KnockoutObservable<boolean> = ko.observable(true);
        newModeEnable: KnockoutObservable<boolean>;
        ageBaseDateReq: KnockoutObservable<boolean>;
        ageBaseDateDefaultValue: KnockoutObservable<boolean>;
        
        constructor() {
            let self = this;
            
            self.sphdList = ko.observableArray([]);
            
            self.listSpecialHlFrame = ko.observableArray([]);
            self.listAbsenceFrame = ko.observableArray([]);
            self.targetItems = ko.observableArray([]);
            
            self.cdl002Name = ko.observableArray([]);
            self.cdl003Name = ko.observableArray([]);
            self.newModeEnable = ko.observable(true);

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
                { headerText: nts.uk.resource.getText('KMF004_6'), key: 'specialHolidayName', width: 150, formatter: _.escape }
            ]);
            
            self.currentCode = ko.observable();
            
            self.specialHolidayCode.subscribe(function(value) {
                if(Number(value) >= 0 && self.selectedMethod() == 1) {
                    self.dialogDEnable(true);
                } else {
                    self.dialogDEnable(false);
                }
            });
            
            self.currentCode.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(value > 0){
                    service.getSpecialHoliday(value).done(function(data) {
                        self.selectedTab('tab-1');
                        
                        self.selectedTargetItems = [];
                        
                        self.isEnable(true);
                        self.isDisable(false);
                        self.specialHolidayCode(data.specialHolidayCode);
                        self.specialHolidayName(data.specialHolidayName);
                        self.memo(data.memo);
                        self.editMode(true);
                        $("#input-name").focus();
                        self.newModeEnable(true);
                        
                        self.selectedGrantDate(data.grantRegularDto.grantDate);
                        self.selectedMethod(data.grantRegularDto.typeTime);
                        self.years(data.grantRegularDto.grantTime.fixGrantDate.interval);
                        self.days(data.grantRegularDto.grantTime.fixGrantDate.grantDays);
                        self.allowDisappear(data.grantRegularDto.allowDisappear);
                        
                        self.selectedTimeMethod(data.grantPeriodicDto.timeSpecifyMethod);
                        self.limitedDays(data.grantPeriodicDto.limitCarryoverDays);
                        self.expYears(data.grantPeriodicDto.expirationDate.years);
                        self.expMonth(data.grantPeriodicDto.expirationDate.months);
                        self.startDate(data.grantPeriodicDto.availabilityPeriod.startDate == "1900/01/01" ? "" : data.grantPeriodicDto.availabilityPeriod.startDate);
                        self.endDate(data.grantPeriodicDto.availabilityPeriod.endDate == "1900/01/01" ? "" : data.grantPeriodicDto.availabilityPeriod.endDate);
                        
                        self.genderSelected(data.specialLeaveRestrictionDto.genderRest == 0 ? true : false);
                        self.selectedGender(data.specialLeaveRestrictionDto.gender);
                        self.empSelected(data.specialLeaveRestrictionDto.restEmp == 0 ? true : false);
                        self.empLst(_.map(data.specialLeaveRestrictionDto.listEmp,item=>{return item}));
                        self.clsSelected(data.specialLeaveRestrictionDto.restrictionCls == 0 ? true : false);
                        self.clsLst(_.map(data.specialLeaveRestrictionDto.listCls,item=>{return item}));
                        self.ageSelected(data.specialLeaveRestrictionDto.ageLimit == 0 ? true : false);
                        self.startAge(data.specialLeaveRestrictionDto.ageRange.ageLowerLimit);
                        self.endAge(data.specialLeaveRestrictionDto.ageRange.ageHigherLimit);
                        self.selectedAgeCriteria(data.specialLeaveRestrictionDto.ageStandard.ageCriteriaCls);
                        let days = data.specialLeaveRestrictionDto.ageStandard.ageBaseDate.day.toString().length > 1 
                                        ? data.specialLeaveRestrictionDto.ageStandard.ageBaseDate.day 
                                        : ("0" + data.specialLeaveRestrictionDto.ageStandard.ageBaseDate.day);
                        self.ageBaseDate(data.specialLeaveRestrictionDto.ageStandard.ageBaseDate.month + "" + days);
                        
                        let targetItems = [];
                        if(data.targetItemDto.absenceFrameNo != null && data.targetItemDto.absenceFrameNo.length > 0) {
                            _.forEach(data.targetItemDto.absenceFrameNo, function(item) {
                                targetItems.push("a" + item);
                            });
                        }
                        
                        if(data.targetItemDto.frameNo != null && data.targetItemDto.frameNo.length > 0) {
                            _.forEach(data.targetItemDto.frameNo, function(item) {
                                targetItems.push("b" + item);
                            });
                        }
                        
                        let temp = [];
                        _.forEach(targetItems, function(code) {
                            let selectedItem = _.find(self.targetItems(), function(o) { return o.code == code; });
                            if(selectedItem){
                            temp.push(selectedItem);
                                }
                        });
                        
                        let text = "";
                        _.forEach(_.orderBy(temp, ['code'], ['asc']), function(item) {
                            text += item.name + " + " ;                    
                        });
                        
                        self.targetItemsName(text.substring(0, text.length - 3));
                        
                        if(self.selectedTargetItems == null || self.selectedTargetItems.length <= 0) {
                            self.selectedTargetItems = targetItems;
                        }
                        
                        nts.uk.ui.errors.clearAll();
                    }).fail(function(res) {
                          
                    });
                }
            });
            
            self.empLst.subscribe((newData)=>{
                if(!_.size(newData)){
                    self.cdl002Name("");
                    return;
                }
                
                service.findEmpByCodes(newData).done((datas)=>{
                    self.cdl002Name(_.map(datas,item=>{return item.name}).join(' + '));
                });
            });
            
            self.clsLst.subscribe((newData)=>{
                if(!_.size(newData)){
                    self.cdl003Name("");
                    return;
                }
                
                service.findClsByCodes(newData).done((datas)=>{
                    self.cdl003Name(_.map(datas,item=>{return item}).join(' + '));
                });
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
                    self.yearReq(true);
                    self.dayReq(true);
                } else {
                    self.years("");
                    self.days("");
                    self.yearEnable(false);
                    self.dayEnable(false);
                    self.yearReq(false);
                    self.dayReq(false);
                    
                    if(!self.newModeEnable()) {
                        self.dialogDEnable(false);
                    } else {
                        self.dialogDEnable(true);
                    }
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
            
            self.startDate.subscribe(function(value) {
                if(value != null || value !== "") {
                    $('.end-date .ntsControl.nts-datepicker-wrapper').removeClass('error');
                }
            });
            
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
            
            self.empLstEnable = ko.observable(false);
            self.clsLstEnable = ko.observable(false);
            
            self.startAge = ko.observable();
            self.startAgeEnable = ko.observable(false);
            self.endAge = ko.observable();
            self.endAgeEnable = ko.observable(false);
            
            self.ageCriteriaCls = ko.observableArray([
                new Items('0', nts.uk.resource.getText('Enum_ReferenceYear_THIS_YEAR')),
                new Items('1', nts.uk.resource.getText('Enum_AgeBaseYearAtr_THIS_MONTH'))
            ]);
    
            self.selectedAgeCriteria = ko.observable('0');
            self.ageCriteriaClsEnable = ko.observable(false);
            
            self.ageBaseDate = ko.observable('');
            self.ageBaseDateEnable = ko.observable(false);
            
            self.ageBaseDateReq = ko.observable(false);
            self.ageBaseDateDefaultValue = ko.observable(true);
            
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
                    self.empLst([]);
                }
            });
            
            self.clsSelected.subscribe(function(value) {
                if(value) {
                    self.clsLstEnable(true);
                } else {
                    self.clsLstEnable(false);
                    self.clsLst([]);
                }
            });
            
            self.ageSelected.subscribe(function(value) {
                if(value) {
                    self.startAgeEnable(true);
                    self.endAgeEnable(true);
                    self.ageCriteriaClsEnable(true);
                    self.ageBaseDateEnable(true);
                    self.ageBaseDateReq(true);
                    self.ageBaseDateDefaultValue(false);
                } else {
                    self.startAgeEnable(false);
                    self.endAgeEnable(false);
                    self.ageCriteriaClsEnable(false);
                    self.ageBaseDateEnable(false);
                    self.ageBaseDateReq(false);
                    self.ageBaseDateDefaultValue(true);
                }
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            $.when(self.getSphdData(), self.getSpecialHolidayFrame(), self.getAbsenceFrame()).done(function() {
                       
                if(self.listSpecialHlFrame().length > 0) {
                    _.forEach(self.listSpecialHlFrame(), function(item) {
                        self.targetItems.push(item);
                    });
                }
                
                if(self.listAbsenceFrame().length > 0) {
                    _.forEach(self.listAbsenceFrame(), function(item) {
                        self.targetItems.push(item);
                    });
                }
                
                if (self.sphdList().length > 0) {
                    self.currentCode(self.sphdList()[0].specialHolidayCode);
                    self.currentCode.valueHasMutated();
                } else {
                    self.newModeEnable(false);
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
        
        openDialogD() {
            let self = this;
            
            if (nts.uk.ui.errors.hasError()) {
                return;    
            }
            
            nts.uk.ui.windows.setShared("KMF004_A_DATA", self.specialHolidayCode());
        
            nts.uk.ui.windows.sub.modal("/view/kmf/004/d/index.xhtml").onClosed(() => {
                
            });
        }
        
        /**
         * Get data absence frame from database
         */
        getAbsenceFrame(): any {
            var self = this;
            var dfd = $.Deferred();
            service.getAllAbsenceFrame().done(function(data) {
                if (data.length != 0) {
                    self.listAbsenceFrame.removeAll();
                    _.forEach(data, function(item) {
                        if (item.deprecateAbsence == 0) {
                            var absenceFrame = new ItemData("b" + item.absenceFrameNo, item.absenceFrameName, 2);
                            self.listAbsenceFrame.push(ko.toJS(absenceFrame));
                        }
                    });
                }
                dfd.resolve();
            }).fail((res) => { });
            return dfd.promise();
        }
        
        /**
         * Get data special holiday frame form database
         */
        getSpecialHolidayFrame(): any {
            var self = this;
            var dfd = $.Deferred();
            service.getAllSpecialHolidayFrame().done(function(data) {
                if (data.length != 0) {
                    self.listSpecialHlFrame.removeAll();
                    _.forEach(data, function(item) {
                        if (item.deprecateSpecialHd == 0) {
                            var specialHlFrame = new ItemData("a" + item.specialHdFrameNo, item.specialHdFrameName, 1);
                            self.listSpecialHlFrame.push(ko.toJS(specialHlFrame));
                        }
                    });
                }
                dfd.resolve();
            }).fail((res) => { });
            return dfd.promise();
        }
        
        openJDialog() {
            let self = this;
            
            let selectedNo = [];
            
            _.forEach(self.selectedTargetItems, function(code) {
                selectedNo.push(code);
            });
            
            nts.uk.ui.windows.setShared("KMF004_A_TARGET_ITEMS", selectedNo);
            
            nts.uk.ui.windows.sub.modal("/view/kmf/004/j/index.xhtml").onClosed(() => {
                let selectedData = nts.uk.ui.windows.getShared("KMF004_J_SELECTED_ITEMS");
                self.selectedTargetItems = selectedData != null ? selectedData : self.selectedTargetItems;
                
                let temp = [];
                _.forEach(self.selectedTargetItems, function(code) {
                    let selectedItem = _.find(self.targetItems(), function(o) { return o.code == code; });
                    temp.push(selectedItem);
                });
                
                let text = "";
                _.forEach(_.orderBy(temp, ['code'], ['asc']), function(item) {
                    text += item.name + " + " ;                    
                });
                
                self.targetItemsName(text.substring(0, text.length - 3));
                
                if(self.selectedTargetItems.length > 0) {
                    nts.uk.ui.errors.clearAll();
                }
            });
        }
        
        openCDL002Dialog() {
            let self = this;
            
            nts.uk.ui.windows.setShared('CDL002Params', {
                isMultiple: true,
                selectedCodes: self.empLst(),
                showNoSelection: false,
            }, true);
            
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/002/a/index.xhtml").onClosed(() => {
                let isCancel = nts.uk.ui.windows.getShared('CDL002Cancel');
                
                if (isCancel) {
                    return;
                }
                
                let output = nts.uk.ui.windows.getShared('CDL002Output');
                
                self.empLst(output);
               
            });
        }
        
        openCDL003Dialog() {
            let self = this;
            nts.uk.ui.windows.setShared('inputCDL003', {
                isMultiple: true,
                selectedCodes: self.clsLst(),
                showNoSelection: false,
            }, true);
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/003/a/index.xhtml").onClosed(() => {
               let data = nts.uk.ui.windows.getShared('outputCDL003');
                if (data) {
                    self.clsLst(data);
                }
            });
        }
        
        preData(): service.SpecialHolidayItem {
            let self = this;
            
            let fixGrantDate : service.FixGrantDate = {
                interval: self.years(),
                grantDays: self.days()
            };
            
            let grantTime : service.GrantTime = {
                fixGrantDate: fixGrantDate,
                grantDateTbl: null
            };
            
            let grantRegular : service.GrantRegular = {
                companyId: "",
                specialHolidayCode: self.specialHolidayCode(),
                typeTime: self.selectedMethod(),
                grantDate: self.selectedGrantDate(),
                allowDisappear: self.allowDisappear(),
                grantTime: grantTime
            };
            
            let start = "";
            let end = "";
            if(self.startDate().indexOf("T") > -1 && self.startDate().indexOf("-") > -1) {
                start = self.startDate() != "" ? self.startDate().substring(0, self.startDate().indexOf('T')).replace("-", "/").replace("-", "/") : "1900/01/01";
            } else {
                start = self.startDate() != "" ? self.startDate() : "1900/01/01";
            }
            
            if(self.endDate().indexOf("T") > -1 && self.endDate().indexOf("-") > -1) {
                end = self.endDate() != "" ? self.endDate().substring(0, self.endDate().indexOf('T')).replace("-", "/").replace("-", "/") : "1900/01/01";
            } else {
                end = self.endDate() != "" ? self.endDate() : "1900/01/01";
            }
            
            let availabilityPeriod : service.AvailabilityPeriod = {
                startDate: start,
                endDate: end
            };
            
            let expirationDate : service.SpecialVacationDeadline = {
                months: self.expMonth(),
                years: self.expYears()
            };
            
            let grantPeriodic : service.GrantPeriodic = {
                companyId: "",
                specialHolidayCode: self.specialHolidayCode(),
                timeSpecifyMethod: self.selectedTimeMethod(),
                availabilityPeriod: availabilityPeriod,
                expirationDate: expirationDate,
                limitCarryoverDays: self.limitedDays()
            };
            
            let ageStandard : service.AgeStandard = {
                ageCriteriaCls: self.selectedAgeCriteria(),
                ageBaseDate: self.ageBaseDate()
            };
            
            let ageRange : service.AgeRange = {
                ageLowerLimit: self.startAge(),
                ageHigherLimit: self.endAge()
            };
            
            let specialLeaveRestriction : service.SpecialLeaveRestriction = {
                companyId: "",
                specialHolidayCode: self.specialHolidayCode(),
                restrictionCls: self.clsSelected() ? 0 : 1,
                ageLimit: self.ageSelected() ? 0 : 1,
                genderRest: self.genderSelected() ? 0 : 1,
                restEmp: self.empSelected() ? 0 : 1,
                listCls: self.clsLst(),
                ageStandard: ageStandard,
                ageRange: ageRange,
                gender: self.selectedGender(),
                listEmp: self.empLst()
            };
            
            let absence = [];
            let frame = [];
            _.forEach(self.selectedTargetItems, function(code) {
                if(code.indexOf("a") > -1) {
                    absence.push(code.slice(1));
                } else {
                    frame.push(code.slice(1));
                }
            });
            
            let targetItem : service.TargetItem = {
                absenceFrameNo: absence,
                frameNo: frame
            };
            
            let dataItem : service.SpecialHolidayItem = {
                companyId: "",
                specialHolidayCode: self.specialHolidayCode(),
                specialHolidayName: self.specialHolidayName(),
                regularCommand: grantRegular,
                periodicCommand: grantPeriodic,
                leaveResCommand: specialLeaveRestriction,
                targetItemCommand: targetItem,
                memo: self.memo()
            };
            
            return dataItem;
        }
        
        saveSpecialHoliday(): JQueryPromise<any> {
            let self = this;
            nts.uk.ui.errors.clearAll();
            nts.uk.ui.block.invisible();
            
            if(self.sphdList().length == 20 && !self.editMode()) {
                self.addListError(["Msg_669"]); 
                nts.uk.ui.block.clear();
                return;
            }
            
            $("#input-code").trigger("validate");
            $("#input-name").trigger("validate");
            
            let dataItem = self.preData();
            
            if(self.yearReq() && self.dayReq()) {
                if(dataItem.regularCommand.grantTime.fixGrantDate.interval == "" && dataItem.regularCommand.grantTime.fixGrantDate.grantDays == "") {
                    $("#years").ntsError("set", "付与周期を入力してください", "FND_E_REQ_INPUT");
                    $("#days").ntsError("set", "付与日数を入力してください", "FND_E_REQ_INPUT");
                }
            }            
            
            if(dataItem.targetItemCommand.absenceFrameNo.length <= 0 && dataItem.targetItemCommand.frameNo.length <= 0) {
                $("#target-items").ntsError("set", nts.uk.resource.getMessage("Msg_93"), "Msg_93");
            }
            
            if(self.selectedTimeMethod() == 0 && dataItem.periodicCommand.limitCarryoverDays === "") {
                $("#limitedDays").ntsError("set", "蓄積上限日数を入力してください", "FND_E_REQ_INPUT");
            }
            
            if(self.selectedTimeMethod() == 3) {
                if(self.startDate() === "") {
                    $("#start-date-inp").ntsError("set", "使用可能期間開始日入力してください", "FND_E_REQ_INPUT");
                }
                
                if(self.endDate() === "") {
                    $("#end-date-inp").ntsError("set", "使用可能期間終了日入力してください", "FND_E_REQ_INPUT");
                }
                
                if(self.startDate() > self.endDate()) {
                    $("#period-date-inp").ntsError("set", "期間入力フォームの開始と終了が逆転しています", "FND_E_SPAN_REVERSED");
                    $('.end-date .ntsControl.nts-datepicker-wrapper').addClass('error');
                }
            }
            
            if (nts.uk.ui.errors.hasError()) {
                nts.uk.ui.block.clear();
                return;    
            }
            
            if(!self.editMode()) {
                service.add(dataItem).done(function(errors) {
                    _.forEach(errors, function(err) {
                        if(err === "Msg_3") {
                            $("#input-code").ntsError("set", {messageId:"Msg_3"});
                        }
                    });
                        
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
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId }).then(() => {
                        if(error.messageId === "Msg_3") {
                            $('#input-code').focus();
                        }
                    }); 
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
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
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
                    
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                        if(self.editMode()) {
                            $("#input-name").focus();
                        } else {
                            $("#input-code").focus();
                        }
                    });
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
            
            self.selectedTargetItems = [];
            
            self.editMode(false);
            self.currentCode("");
            self.specialHolidayCode("");
            self.isEnable(false);
            self.isDisable(true);
            self.specialHolidayName("");
            self.targetItemsName("");
            self.memo("");
            
            self.newModeEnable(false);
            
            self.selectedTab('tab-1');
            
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
            
            self.genderSelected(false);
            self.empSelected(false);
            self.clsSelected(false);
            self.ageSelected(false);
            self.selectedGender(0);
            self.empLst([]);
            self.clsLst([]);
            self.startAge("");
            self.endAge("");
            self.selectedAgeCriteria(0);
            self.ageBaseDate("");
            self.ageBaseDateReq(false);
            self.ageBaseDateDefaultValue(true);
            
            self.yearReq(true);
            self.dayReq(true);
            
            $("#input-code").focus();
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
            
            nts.uk.ui.dialog.bundledErrors({ errors: errors }).then(() => {
//                _.forEach(errors, function(err) {
//                    if(err.messageId === "Msg_3") {
//                        $("#input-code").ntsError("set", {messageId:"Msg_3"});
//                    }
//                });
            });
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
    
    class ItemData {
        code: string;
        name: string;
        types: number;

        constructor(code: string, name: string, types: number) {
            this.code = code;
            this.name = name;
            this.types = types;
        }
    }
}