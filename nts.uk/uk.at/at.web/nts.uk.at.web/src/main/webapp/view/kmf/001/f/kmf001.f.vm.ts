module nts.uk.pr.view.kmf001.f {
    export module viewmodel {

        import Enum = service.model.Enum;
        import RadioEnum = service.model.RadioEnum;

        export class ScreenModel {

            compenManage: KnockoutObservable<number>;
            compenPreApply: KnockoutObservable<number>;
            compenTimeManage: KnockoutObservable<number>;
            compenDeadlCheckMonth: KnockoutObservable<number>;

            expirationDateCode: KnockoutObservable<number>;
            timeUnitCode: KnockoutObservable<number>;

            checkWorkTime: KnockoutObservable<boolean>;
            checkOverTime: KnockoutObservable<boolean>;

            selectedOfWorkTime: KnockoutObservable<number>;
            selectedOfOverTime: KnockoutObservable<number>;

            workOneDay: KnockoutObservable<string>;
            workHalfDay: KnockoutObservable<string>;
            workAll: KnockoutObservable<string>;

            overOneDay: KnockoutObservable<string>;
            overHalfDay: KnockoutObservable<string>;
            overAll: KnockoutObservable<string>;

            inputOption: KnockoutObservable<any>;
            isManageCompen: KnockoutObservable<boolean>;
            isManageTime: KnockoutObservable<boolean>;
            enableWorkArea: KnockoutObservable<boolean>;
            enableOverArea: KnockoutObservable<boolean>;

            enableWorkAll: KnockoutObservable<boolean>;
            enableOverAll: KnockoutObservable<boolean>;
            enableDesignWork: KnockoutObservable<boolean>;
            enableDesignOver: KnockoutObservable<boolean>;

            backUpData: KnockoutObservable<any>;
            //enums
            manageDistinctEnums: KnockoutObservableArray<Enum>;
            applyPermissionEnums: KnockoutObservableArray<Enum>;
            expirationTimeEnums: KnockoutObservableArray<Enum>;
            timeVacationDigestiveUnitEnums: KnockoutObservableArray<Enum>;
            compensatoryOccurrenceDivisionEnums: KnockoutObservableArray<Enum>;
            transferSettingDivisionEnums: KnockoutObservableArray<RadioEnum>;
            deadlCheckMonthEnums: KnockoutObservableArray<Enum>;

            //Employment
            employmentBackUpData: KnockoutObservable<any>;
            employmentList: KnockoutObservableArray<ItemModel>;
            columnsSetting: KnockoutObservable<nts.uk.ui.NtsGridListColumn>;
            emSelectedCode: KnockoutObservable<string>;
            emSelectedName: KnockoutObservable<string>;

            emCompenManage: KnockoutObservable<number>;
            emExpirationTime: KnockoutObservable<number>;
            emPreApply: KnockoutObservable<number>;
            emTimeManage: KnockoutObservable<number>;
            emTimeUnit: KnockoutObservable<number>;

            isEmptyEmployment: KnockoutObservable<boolean>;
            isEmManageCompen: KnockoutObservable<boolean>;
            enableDigestiveUnit: KnockoutObservable<boolean>;
            //for list employment
            alreadySettingList: KnockoutObservableArray<any>;
            listComponentOption: KnockoutObservable<ComponentOption>;
            
            firstLoad: KnockoutObservable<boolean>;
            employmentVisible: KnockoutObservable<boolean>;
            
            inputWorkOneDay: any;
            inputWorkHalfDay: any;
            inputOverOneDay: any;
            inputOverHalfDay: any;
            inputOverAll: any;
            inputWorkAll: any;
            
            deleteEnable: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                self.compenManage = ko.observable(1);
                self.compenPreApply = ko.observable(1);
                self.compenTimeManage = ko.observable(1);
                self.compenDeadlCheckMonth = ko.observable(1);
                self.expirationDateCode = ko.observable(0);
                self.timeUnitCode = ko.observable(0);
                self.checkWorkTime = ko.observable(true);
                self.checkOverTime = ko.observable(true);
                self.selectedOfWorkTime = ko.observable(1);
                self.selectedOfOverTime = ko.observable(1);

                self.workOneDay = ko.observable('0000');
                self.workHalfDay = ko.observable('0000');
                self.workAll = ko.observable('0000');

                self.overOneDay = ko.observable('0000');
                self.overHalfDay = ko.observable('0000');
                self.overAll = ko.observable('0000');
                
                self.inputWorkOneDay = $('#workOneDay');
                self.inputOverOneDay = $('#overOneDay');
                self.inputWorkHalfDay = $('#workHalfDay');
                self.inputOverHalfDay = $('#overHalfDay');
                self.inputWorkAll = $('#workAll');
                self.inputOverAll = $('#overAll');
                self.deleteEnable = ko.observable(true);

                self.inputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    width: "50"
                }));

                self.backUpData = ko.observable();
                self.manageDistinctEnums = ko.observableArray([]);
                self.applyPermissionEnums = ko.observableArray([]);
                self.expirationTimeEnums = ko.observableArray([]);
                self.timeVacationDigestiveUnitEnums = ko.observableArray([]);
                self.compensatoryOccurrenceDivisionEnums = ko.observableArray([]);
                self.transferSettingDivisionEnums = ko.observableArray([]);
                self.deadlCheckMonthEnums = ko.observableArray([]);

                self.isManageCompen = ko.computed(function() {
                    return self.compenManage() == UseDivision.Use;
                });

                self.isManageTime = ko.computed(function() {
                    return self.isManageCompen() && self.compenTimeManage() == UseDivision.Use;
                });

                self.enableWorkArea = ko.computed(function() {
                    return self.checkWorkTime() && self.isManageCompen();
                });

                self.enableOverArea = ko.computed(function() {
                    return self.checkOverTime() && self.isManageCompen();
                });

                self.enableWorkAll = ko.computed(function() {
                    return self.enableWorkArea() && self.selectedOfWorkTime() == UseDivision.Use;
                });

                self.enableOverAll = ko.computed(function() {
                    return self.enableOverArea() && self.selectedOfOverTime() == UseDivision.Use;
                });

                self.enableDesignWork = ko.computed(function() {
                    return self.enableWorkArea() && self.selectedOfWorkTime() == UseDivision.NotUse;
                });

                self.enableDesignOver = ko.computed(function() {
                    return self.enableOverArea() && self.selectedOfOverTime() == UseDivision.NotUse;
                });
                
                self.checkWorkTime.subscribe(function(flag) {
                    if (flag) {
                        if (self.enableDesignWork()) {
                            self.inputWorkOneDay.ntsError('check');
                            self.inputWorkHalfDay.ntsError('check');
                        } else {
                            self.inputWorkAll.ntsError('check');
                        }
                    } else {
                        if (self.inputWorkAll.ntsError("hasError")) self.inputWorkAll.ntsError('clear');
                        if (self.inputWorkOneDay.ntsError("hasError")) self.inputWorkOneDay.ntsError('clear');
                        if (self.inputWorkHalfDay.ntsError("hasError")) self.inputWorkHalfDay.ntsError('clear');
                    }
                });
                
                self.enableDesignWork.subscribe(function(flag) {
                    if (flag) {
                        if (self.inputWorkAll.ntsError("hasError")) self.inputWorkAll.ntsError('clear');
                        
                        self.inputWorkOneDay.ntsError('check');
                        self.inputWorkHalfDay.ntsError('check');
                        return true;
                    }
                    if (self.inputWorkOneDay.ntsError("hasError")) self.inputWorkOneDay.ntsError('clear');
                    if (self.inputWorkHalfDay.ntsError("hasError")) self.inputWorkHalfDay.ntsError('clear');
                    
                    self.inputWorkAll.ntsError('check');
                });
                
                self.checkOverTime.subscribe(function(flag) {
                    if (flag) {
                        if (self.enableDesignOver()) {
                            self.inputOverOneDay.ntsError('check');
                            self.inputOverHalfDay.ntsError('check')
                        } else {
                            self.inputOverAll.ntsError('check');
                        }
                    } else {
                        if (self.inputOverAll.ntsError("hasError")) self.inputOverAll.ntsError('clear');
                        if (self.inputOverOneDay.ntsError("hasError")) self.inputOverOneDay.ntsError('clear');
                        if (self.inputOverHalfDay.ntsError("hasError")) self.inputOverHalfDay.ntsError('clear');
                    }
                });
                
                self.enableDesignOver.subscribe(function(flag) {
                    if (flag) {
                        if (self.inputOverAll.ntsError("hasError")) self.inputOverAll.ntsError('clear');
                        
                        self.inputOverOneDay.ntsError('check');
                        self.inputOverHalfDay.ntsError('check');
                        return true;
                    }
                    if (self.inputOverOneDay.ntsError("hasError")) self.inputOverOneDay.ntsError('clear');
                    if (self.inputOverHalfDay.ntsError("hasError")) self.inputOverHalfDay.ntsError('clear');
                    
                    self.inputOverAll.ntsError('check');
                });

                //employment
                self.employmentBackUpData = ko.observable();
                self.employmentList = ko.observableArray<ItemModel>([]);
                self.emSelectedCode = ko.observable('');
                self.emSelectedName = ko.observable('');

                self.emCompenManage = ko.observable(0);
                self.emExpirationTime = ko.observable(0);
                self.emPreApply = ko.observable(0);
                self.emTimeManage = ko.observable(0);
                self.emTimeUnit = ko.observable(0);

                self.isEmptyEmployment = ko.observable(false);
                self.isEmManageCompen = ko.computed(function() {
                    return self.emCompenManage() == UseDivision.Use && !self.isEmptyEmployment();
                });

                self.enableDigestiveUnit = ko.computed(function() {
                    if (self.isEmptyEmployment()) return false;
                    if (self.deleteEnable()) return self.isEmManageCompen();
                    return self.isEmManageCompen() && self.emTimeManage() == UseDivision.Use;
                });

                self.emSelectedCode.subscribe(function(employmentCode: string) {
                    if (employmentCode) {
                        self.loadEmploymentSetting(employmentCode);
                        
                        // Load employment name
                        let employmentList: Array<UnitModel> = $('#list-employ-component').getDataList();  
                        let selectedEmp = _.find(employmentList, { 'code': employmentCode });
                        self.emSelectedName(selectedEmp.name);
                        self.isEmptyEmployment(false);
                        
                        self.checkDeleteAvailability();
                    }
                    else {
                        //not selected item -> disable All
                        self.emSelectedName('');
                        self.isEmptyEmployment(true);
                        self.deleteEnable(false);
                    }
                });

                //for list em
                self.alreadySettingList = ko.observableArray([]);
                self.listComponentOption = {
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.emSelectedCode,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingList
                };
                self.firstLoad = ko.observable(true);
                self.employmentVisible = ko.observable(self.compenManage() == 1);
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                //load all enum and employment setting list
                $.when(self.loadManageDistinctEnums(), self.loadApplyPermissionEnums(), self.loadExpirationTimeEnums(), self.loadTimeVacationDigestiveUnitEnums(),
                    self.loadCompensatoryOccurrenceDivisionEnums(), self.loadTransferSettingDivisionEnums(), self.loadEmploymentList(), self.loadDeadlCheckMonthEnums()).done(function() {
                        self.loadSetting().done(function() {
                            dfd.resolve();
                        });
                    });
                return dfd.promise();
            }

            private loadEmploymentList(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                //get list employment
                self.alreadySettingList([]);
                service.findAllEmploymentSetting().done((data: Array<string>) => {
                    for (let emCode of data) {
                        self.alreadySettingList.push({ code: emCode, isAlreadySetting: true });
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            //switch to com tab
            private switchToCompanyTab() {
                var self = this;
                self.loadSetting();
            }
            
            //switch to em tab
            private switchToEmploymentTab() {
                let self = this;
                self.clearError();
                let dfd = $.Deferred<any>();
                //include list employment
                $.when($('#list-employ-component').ntsListComponent(this.listComponentOption),self.loadEmploymentList()).done(() => {
                    self.employmentList($('#list-employ-component').getDataList());
                    //list employment is empty
                    if (!$('#list-employ-component').getDataList() || $('#list-employ-component').getDataList().length <= 0) {
                        self.deleteEnable(false);
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_146", messageParams: [] }).then(function() {
                            $('a[role="tab-navigator"][href="#company-tab"]').click();
                        });
                        self.isEmptyEmployment(true);
                        dfd.resolve();
                    } else {
                        self.emSelectedCode(self.employmentList()[0].code);
                        
                        self.checkDeleteAvailability();
                        
                        $('#emCompenManage').focus();
                    }
                });
                return dfd.promise();
            }

            //==== LOAD ENUM ====
            private loadManageDistinctEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumManageDistinct().done(function(res: Array<Enum>) {
                    self.manageDistinctEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            private loadApplyPermissionEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumApplyPermission().done(function(res: Array<Enum>) {
                    self.applyPermissionEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            private loadExpirationTimeEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumExpirationTime().done(function(res: Array<Enum>) {
                    self.expirationTimeEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }
            
            private loadDeadlCheckMonthEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumDeadlCheckMonth().done(function(res: Array<Enum>) {
                    self.deadlCheckMonthEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                
                return dfd.promise();
            }

            private loadTimeVacationDigestiveUnitEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumTimeVacationDigestiveUnit().done(function(res: Array<Enum>) {
                    self.timeVacationDigestiveUnitEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            private loadCompensatoryOccurrenceDivisionEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumCompensatoryOccurrenceDivision().done(function(res: Array<Enum>) {
                    self.compensatoryOccurrenceDivisionEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            private loadTransferSettingDivisionEnums(): JQueryPromise<Array<RadioEnum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumTransferSettingDivision().done(function(res: Array<RadioEnum>) {
                    res.forEach(function(item, index) {
                        item.enable = true;
                    });
                    self.transferSettingDivisionEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            //load data to screen(company)
            private loadSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.find().done(function(data: any) {
                    if (data) {
                        self.loadToScreen(data);
                        self.backUpData(data);
                    } else {
                        self.backUpData(self.defaultData());
                    }
                    $('#compenManage').focus();
                    self.employmentVisible(self.compenManage() == 1);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            //load data to screen(employment)
            private loadEmploymentSetting(employmentCode: string): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findEmploymentSetting(employmentCode).done(function(data: any) {
                    if (data) {
                        self.loadEmploymentToScreen(data);
                        self.employmentBackUpData(data);
                    } else {
                        self.loadEmploymentToScreen(self.employmentDefaultData());
                        self.employmentBackUpData(self.employmentDefaultData());
                    }
                    if(self.firstLoad()){
                        $('#emCompenManage').focus();
                        self.firstLoad(false);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            //bind employment 
            private loadEmploymentToScreen(data: any) {
                var self = this;
                if (data) {
                    self.emCompenManage(data.isManaged);
                    self.emExpirationTime(data.compensatoryAcquisitionUse.expirationTime);
                    self.emPreApply(data.compensatoryAcquisitionUse.preemptionPermit);
                    self.emTimeManage(data.compensatoryDigestiveTimeUnit.isManageByTime);
                    self.emTimeUnit(data.compensatoryDigestiveTimeUnit.digestiveUnit);
                }
                else {
                    self.emCompenManage(self.manageDistinctEnums()[0].value);
                    self.emExpirationTime(self.expirationTimeEnums()[0].value);
                    self.emPreApply(self.applyPermissionEnums()[0].value);
                    self.emTimeManage(self.manageDistinctEnums()[0].value);
                    self.emTimeUnit(self.timeVacationDigestiveUnitEnums()[0].value);
                }
            }
            
            //bind company
            private loadToScreen(data: any) {
                let self = this;
                self.compenManage(data.isManaged);

                self.expirationDateCode(data.compensatoryAcquisitionUse.expirationTime);
                self.compenPreApply(data.compensatoryAcquisitionUse.preemptionPermit);
                self.compenDeadlCheckMonth(data.compensatoryAcquisitionUse.deadlCheckMonth);

                self.compenTimeManage(data.compensatoryDigestiveTimeUnit.isManageByTime);
                self.timeUnitCode(data.compensatoryDigestiveTimeUnit.digestiveUnit);

                if (data.compensatoryOccurrenceSetting[1].occurrenceType == OccurrenceDivision.OverTime) {
                    self.loadOverTime(data.compensatoryOccurrenceSetting[1].transferSetting);
                    self.loadWorkTime(data.compensatoryOccurrenceSetting[0].transferSetting);
                }
                else {
                    self.loadOverTime(data.compensatoryOccurrenceSetting[0].transferSetting);
                    self.loadWorkTime(data.compensatoryOccurrenceSetting[1].transferSetting);
                }
            }

            //load data for over time
            private loadOverTime(data: any) {
                let self = this;
                self.checkOverTime(data.useDivision);
                self.selectedOfOverTime(data.transferDivision);
                self.overOneDay(data.oneDayTime);
                self.overHalfDay(data.halfDayTime);
                self.overAll(data.certainTime);
            }

            //load data for work time
            private loadWorkTime(data: any) {
                let self = this;
                self.checkWorkTime(data.useDivision);
                self.selectedOfWorkTime(data.transferDivision);
                self.workOneDay(data.oneDayTime);
                self.workHalfDay(data.halfDayTime);
                self.workAll(data.certainTime);
            }

            //save company
            private saveData() {
                let self = this;
                let dfd = $.Deferred<void>();
                
                self.reCallValidate().done(function() {
                    if (!$('.check_error').ntsError('hasError')){
                        
                        nts.uk.ui.block.grayout();
                        
                        service.update(self.collectData()).done(function() {
                            self.loadSetting().then(function() {
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            });
                        })
                        .fail((err) => {
                            // display error 782 on fields
                            let errors = _.filter(err.errors, (v: any) => {
                                if (v.messageId == 'Msg_782') {
                                    switch (v.supplements.occurrenceType) {
                                        case 0:
                                            self.inputOverHalfDay.ntsError('set', {messageId: 'Msg_782'});
                                            break;
                                        case 1:
                                            self.inputWorkHalfDay.ntsError('set', {messageId: 'Msg_782'});
                                            break;
                                    }
                                    return false;
                                }
                                return true;
                            });
                            
                            // display other errors;
                            errors = _.uniqBy(errors, (v: any) => {
                                let key = v.messageId;
                                for (let param of v.parameterIds) {
                                    key = key + ' ' + param;
                                }
                                return key;
                            });
                            if (errors.length > 0) {
                                err.errors = errors;
                                self.showMessageError(err);
                            }
                        }).always(() => {
                            nts.uk.ui.block.clear();
                        });
                    }
                });
            }
            
            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                let dfd = $.Deferred<any>();
                
                // check error business exception
                if (!res.businessException) {
                    return;
                }
                
                // show error message
                if (Array.isArray(res.errors)) {
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
            
            //recall validate for company
            private reCallValidate(): JQueryPromise<void> {
                var self = this;
                let dfd = $.Deferred<void>();
                if (self.enableDesignWork()) {
                    $('#workOneDay').ntsEditor('validate');
                    $('#workHalfDay').ntsEditor('validate');
                }
                if (self.enableWorkAll()) {
                    $('#workAll').ntsEditor('validate');
                }

                if (self.enableDesignOver()) {
                    $('#overOneDay').ntsEditor('validate');
                    $('#overHalfDay').ntsEditor('validate');
                }
                if (self.enableOverAll()) {
                    $('#overAll').ntsEditor('validate');
                }
                dfd.resolve();
                return dfd.promise();
            }

            //default data company
            private defaultData() {
                var self = this;
                return {
                    companyId: "",
                    isManaged: self.compenManage(),
                    compensatoryAcquisitionUse: {
                        expirationTime: self.expirationDateCode(),
                        preemptionPermit: self.compenPreApply()
                    },
                    compensatoryDigestiveTimeUnit: {
                        isManageByTime: self.compenTimeManage(),
                        digestiveUnit: self.timeUnitCode()
                    },
                    compensatoryOccurrenceSetting: [
                        {
                            occurrenceType: OccurrenceDivision.OverTime,
                            transferSetting: {
                                certainTime: self.overAll(),
                                useDivision: self.checkOverTime(),
                                oneDayTime: self.overOneDay(),
                                halfDayTime: self.overHalfDay(),
                                transferDivision: self.selectedOfOverTime()
                            }
                        },
                        {
                            occurrenceType: OccurrenceDivision.DayOffTime,
                            transferSetting: {
                                certainTime: self.workAll(),
                                useDivision: self.checkWorkTime(),
                                oneDayTime: self.workOneDay(),
                                halfDayTime: self.workHalfDay(),
                                transferDivision: self.selectedOfWorkTime()
                            }
                        }
                    ]
                };
            }

            //collect data company
            private collectData() {
                var self = this;
                var data = self.backUpData();
                var overTime = self.backUpData().compensatoryOccurrenceSetting[0].transferSetting;
                var workTime = self.backUpData().compensatoryOccurrenceSetting[1].transferSetting;
                return {
                    companyId: "",
                    isManaged: self.compenManage(),
                    compensatoryAcquisitionUse: {
                        expirationTime: self.isManageCompen() ? self.expirationDateCode() : data.compensatoryAcquisitionUse.expirationTime,
                        preemptionPermit: self.isManageCompen() ? self.compenPreApply() : data.compensatoryAcquisitionUse.preemptionPermit,
                        deadlCheckMonth: self.isManageCompen() ? self.compenDeadlCheckMonth() : data.compensatoryAcquisitionUse.deadlCheckMonth
                    },
                    compensatoryDigestiveTimeUnit: {
                        isManageByTime: self.isManageCompen() ? self.compenTimeManage() : data.compensatoryDigestiveTimeUnit.isManageByTime,
                        digestiveUnit: self.isManageTime() ? self.timeUnitCode() : data.compensatoryDigestiveTimeUnit.digestiveUnit
                    },
                    compensatoryOccurrenceSetting: [
                        {
                            occurrenceType: OccurrenceDivision.OverTime,
                            transferSetting: {
                                certainTime: self.enableOverAll() ? self.overAll() : overTime.certainTime,
                                useDivision: self.isManageCompen() ? self.checkOverTime() : overTime.useDivision,
                                oneDayTime: self.enableDesignOver() ? self.overOneDay() : overTime.oneDayTime,
                                halfDayTime: self.enableDesignOver() ? self.overHalfDay() : overTime.halfDayTime,
                                transferDivision: self.enableOverArea() ? self.selectedOfOverTime() : overTime.transferDivision,
                            }
                        },
                        {
                            occurrenceType: OccurrenceDivision.DayOffTime,
                            transferSetting: {
                                certainTime: self.enableWorkAll() ? self.workAll() : workTime.certainTime,
                                useDivision: self.isManageCompen() ? self.checkWorkTime() : workTime.useDivision,
                                oneDayTime: self.enableDesignWork() ? self.workOneDay() : workTime.oneDayTime,
                                halfDayTime: self.enableDesignWork() ? self.workHalfDay() : workTime.halfDayTime,
                                transferDivision: self.enableWorkArea() ? self.selectedOfWorkTime() : workTime.transferDivision,
                            }
                        }
                    ]
                };
            }

            //save employment
            private saveEmployment() {
                var self = this;
                
                nts.uk.ui.block.grayout();
                
                service.updateEmploymentSetting(self.collectEmploymentData()).done(function() {
                    self.alreadySettingList.push({ "code": self.collectEmploymentData().employmentCode, "isAlreadySetting": true });
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        self.checkDeleteAvailability();
                    });
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
            
            //delete employment
            private deleteEmployment() {
                var self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                    service.deleteEmploymentSetting(self.collectEmploymentData()).done(function() {
                        self.loadEmploymentSetting(self.emSelectedCode());
                        // Remove item from setting list (un-tick)
                        self.alreadySettingList.remove(function(item){ return item.code == self.emSelectedCode()});
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            self.checkDeleteAvailability();
                        });
                    });
                });
            }

            //default data for employment
            private employmentDefaultData() {
                var self = this;
                return {
                    companyId: '',
                    employmentCode: self.emSelectedCode(),
                    isManaged: self.manageDistinctEnums()[0].value,
                    compensatoryAcquisitionUse: {
                        expirationTime: self.expirationTimeEnums()[0].value,
                        preemptionPermit: self.applyPermissionEnums()[0].value
                    },
                    compensatoryDigestiveTimeUnit: {
                        isManageByTime: self.manageDistinctEnums()[0].value,
                        digestiveUnit: self.timeVacationDigestiveUnitEnums()[0].value
                    }
                };
            }
            
            //collect data for employment
            private collectEmploymentData() {
                var self = this;
                var data = self.employmentBackUpData();
                return {
                    companyId: '',
                    employmentCode: self.emSelectedCode(),
                    isManaged: self.emCompenManage(),
                    compensatoryAcquisitionUse: {
                        expirationTime: self.isEmManageCompen() ? self.emExpirationTime() : data.compensatoryAcquisitionUse.expirationTime,
                        preemptionPermit: self.isEmManageCompen() ? self.emPreApply() : data.compensatoryAcquisitionUse.preemptionPermit,
                        deadlCheckMonth: 0
                    },
                    compensatoryDigestiveTimeUnit: {
                        isManageByTime: self.isEmManageCompen() ? self.emTimeManage() : data.compensatoryDigestiveTimeUnit.isManageByTime,
                        digestiveUnit: self.enableDigestiveUnit() ? self.emTimeUnit() : data.compensatoryDigestiveTimeUnit.digestiveUnit
                    }
                };
            }
            
            // check if delete is available
            private checkDeleteAvailability() {
                var self = this;
                var match = ko.utils.arrayFirst(self.alreadySettingList(), function(item) {
                    return item.code == self.emSelectedCode();
                });
                self.deleteEnable(!!match);
            }

            private gotoVacationSetting() {
                //TODO
            }
            
            private clearError() {
                $('#workOneDay').ntsError('clear');
                $('#workHalfDay').ntsError('clear');
                $('#workAll').ntsError('clear');
                $('#overOneDay').ntsError('clear');
                $('#overHalfDay').ntsError('clear');
                $('#overAll').ntsError('clear');
            }
            
        }

        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }

        /**
         * List Type
         */
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export enum OccurrenceDivision {
            OverTime = 0,
            DayOffTime = 1
        }

        export enum UseDivision {
            NotUse = 0,
            Use = 1
        }
    }
}