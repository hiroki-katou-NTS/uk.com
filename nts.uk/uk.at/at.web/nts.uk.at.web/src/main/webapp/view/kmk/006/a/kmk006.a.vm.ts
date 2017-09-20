module nts.uk.at.view.kmk006.a {

    import Enum = service.model.Enum;
    import EstimateTimeDto = service.model.EstimateTimeDto;
    import EstimatePriceDto = service.model.EstimatePriceDto;
    import EstimateDaysDto = service.model.EstimateDaysDto;
    import EstablishmentTimeDto = service.model.EstablishmentTimeDto;
    import EstablishmentPriceDto = service.model.EstablishmentPriceDto;
    import EstablishmentDaysDto = service.model.EstablishmentDaysDto;
    import CompanyEstablishmentDto = service.model.CompanyEstablishmentDto;
    import EmploymentEstablishmentDto = service.model.EmploymentEstablishmentDto;
    import PersonalEstablishmentDto = service.model.PersonalEstablishmentDto;
    import UsageSettingDto = service.model.UsageSettingDto;
    //importDto
    import ComAutoCalSettingDto = service.model.ComAutoCalSettingDto;
    import AutoCalOvertimeSettingDto = service.model.AutoCalOvertimeSettingDto;
    import AutoCalRestTimeSettingDto = service.model.AutoCalRestTimeSettingDto;
    import AutoCalFlexOvertimeSettingDto = service.model.AutoCalFlexOvertimeSettingDto;
    import AutoCalSettingDto = service.model.AutoCalSettingDto;





    export module viewmodel {

        export class ScreenModel {
            multiSelectedWorkplaceId: KnockoutObservable<string>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            treeGrid: TreeComponentOption;
            autoCalAtrOvertimeEnum: Array<Enum>;
            valueEnum: KnockoutObservable<number>;
            selectedTab: KnockoutObservable<string>;
            timeLimitUpperLimitEnum: Array<Enum>;
            itemComAutoCal: ComAutoCalSettingModel;





            // Common
            usageSettingModel: UsageSettingModel;
            lstTargetYear: KnockoutObservableArray<any>;
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            defaultCurrencyOption: any;
            defaultDaysOption: any;

            // Flag
            isCompanySelected: KnockoutObservable<boolean>;
            isEmploymentSelected: KnockoutObservable<boolean>;
            isPersonSelected: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;

            // Data model
            companyEstablishmentModel: EstablishmentModel;
            employmentEstablishmentModel: EstablishmentModel;
            personalEstablishmentModel: EstablishmentModel;

            // Employee tab
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
            lstPersonComponentOption: any;
            selectedEmployeeCode: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;
            employeeList: KnockoutObservableArray<UnitModel>;
            alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;

            // Employment tab
            lstEmploymentComponentOption: any;
            selectedEmploymentCode: KnockoutObservable<string>;
            employmentName: KnockoutObservable<string>;
            alreadySettingEmployment: KnockoutObservableArray<UnitAlreadySettingModel>;
            employmentList: KnockoutObservableArray<UnitModel>;

            constructor() {
                var self = this;

                // Initial common data.
                self.usageSettingModel = new UsageSettingModel();
                this.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK006_14"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK006_15"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK006_16"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.lstTargetYear = ko.observableArray([]);
                self.defaultCurrencyOption = {
                    width: "50",
                    grouplength: 3,
                    currencyformat: "JPY"
                };
                self.defaultDaysOption = {
                    width: "50",
                    decimallength: 1
                };

                // Initial data model.
                self.companyEstablishmentModel = new EstablishmentModel();
                self.employmentEstablishmentModel = new EstablishmentModel();
                self.personalEstablishmentModel = new EstablishmentModel();

                // Initial flags.
                self.isCompanySelected = ko.observable(false);
                self.isEmploymentSelected = ko.observable(false);
                self.isPersonSelected = ko.observable(false);
                self.isLoading = ko.observable(false);

                // Employment tab
                self.employmentName = ko.observable('');
                self.employmentList = ko.observableArray([]);
                self.selectedEmploymentCode = ko.observable('');
                self.alreadySettingEmployment = ko.observableArray([]);
                self.employmentList.subscribe(function() {
                    self.employmentName(self.findEmploymentByCode(self.selectedEmploymentCode()).name);
                });

                // Employee tab
                self.alreadySettingPersonal = ko.observableArray([]);
                self.selectedEmployeeCode = ko.observable('');

                self.selectedEmployee = ko.observableArray([]);

                // Component option initial
                self.lstEmploymentComponentOption = {
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: self.selectedEmploymentCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.alreadySettingEmployment,
                    maxRows: 12
                };
                // Selected year subscription.
                self.companyEstablishmentModel.selectedYear.subscribe(year => {
                    self.loadCompanyEstablishment(year, false).done(() => {
                        $('#first-time-input').focus();
                    });
                });
                self.employmentEstablishmentModel.selectedYear.subscribe(year => {
                    self.updateEmploymentEstimateSetting(year);
                    self.loadEmploymentEstablishment(year, self.selectedEmploymentCode(), false).done(() => {
                        $('#first-time-input').focus();
                    });
                });
                self.personalEstablishmentModel.selectedYear.subscribe(year => {
                    self.updatePersonalEstimateSetting(year);
                    self.loadPersonalEstablishment(year, self.selectedEmployeeCode(), false).done(() => {
                        $('#first-time-input').focus();
                    });
                });

                // Selected employee subscription.
                self.selectedEmployeeCode.subscribe(function(employeeCode) {
                    if (!employeeCode) {
                        self.personalEstablishmentModel.disableInput();
                        self.personalEstablishmentModel.enableDelete(false);
                    } else {
                        var employment: UnitModel = self.findByCodeEmployee(employeeCode);
                        if (employment) {
                            self.employeeName(employment.name);
                        }
                        self.personalEstablishmentModel.enableInput();
                        self.loadPersonalEstablishment(self.personalEstablishmentModel.selectedYear(), self.selectedEmployeeCode(), false);

                    }
                });

                // Selected employment subscription.
                self.selectedEmploymentCode.subscribe(function(employmentCode) {
                    if (!employmentCode) {
                        self.employmentEstablishmentModel.disableInput();
                        self.employmentEstablishmentModel.enableDelete(false);
                    }
                    if (employmentCode) {
                        var employment: UnitModel = self.findEmploymentByCode(employmentCode);
                        if (employment) {
                            self.employmentName(employment.name);
                        }
                        self.employmentEstablishmentModel.enableInput();

                        let currentYear = self.employmentEstablishmentModel.selectedYear() ?
                            self.employmentEstablishmentModel.selectedYear() : moment().year();

                        self.loadEmploymentEstablishment(currentYear, employmentCode, false);
                    }
                });
                self.baseDate = ko.observable(new Date());
                self.multiSelectedWorkplaceId = ko.observable('');
                self.alreadySettingList = ko.observableArray([]);
                self.treeGrid = {
                    isShowAlreadySet: true,
                    isMultiSelect: true,
                    treeType: TreeType.WORK_PLACE,
                    selectedWorkplaceId: self.multiSelectedWorkplaceId,
                    baseDate: self.baseDate,
                    selectType: SelectionType.SELECT_FIRST_ITEM,
                    isShowSelectButton: true,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingList,
                    maxRows: 10
                };
                self.autoCalAtrOvertimeEnum = [];
                self.valueEnum = ko.observable(2);
                self.selectedTab = ko.observable('tab-1');
                self.timeLimitUpperLimitEnum = [];
                self.itemComAutoCal = new ComAutoCalSettingModel();
































            }
            // All function
            // load AutoCalAtrOvertimeEnum
            private loadAutoCalAtrOvertimeEnum(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                nts.uk.ui.block.invisible();

                // get setting
                service.findEnumAutoCalAtrOvertime().done(function(dataRes: Array<Enum>) {

                    self.autoCalAtrOvertimeEnum = dataRes;

                    nts.uk.ui.block.clear();

                    dfd.resolve();
                });

                return dfd.promise();
            }
            // All function
            // load AutoCalAtrOvertimeEnum
            private loadTimeLimitUpperLimitSettingEnum(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                nts.uk.ui.block.invisible();

                // get setting
                service.findEnumTimeLimitUpperLimitSetting().done(function(dataRes: Array<Enum>) {

                    self.timeLimitUpperLimitEnum = dataRes;

                    nts.uk.ui.block.clear();

                    dfd.resolve();
                });

                return dfd.promise();
            }


            // load ComAutoCal
            private loadComAutoCal(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                //            nts.uk.ui.block.invisible();

                service.getComAutoCal().done(function(data) {
                    //                nts.uk.ui.block.clear();
                    if (data) {
                        self.itemComAutoCal.updateData(data);
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });

                return dfd.promise();
            }








































            /**
             * call service load UsageSettingModel
             */
            private loadUsageSettingModel(): void {
                var self = this;
                service.findCompanySettingEstimate().done(function(data) {
                    self.usageSettingModel.updateData(data);
                }).fail(res => {
                    nts.uk.ui.dialog.alertError(res);
                });
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();

                // Initial settings.
                self.setSelectableYears();
                self.loadUsageSettingModel();
                self.loadTimeLimitUpperLimitSettingEnum();
                // load all data  Enum
                self.loadAutoCalAtrOvertimeEnum();
                self.loadComAutoCal().done(function() {
                    if (!self.itemComAutoCal) {
                        self.valueEnum(self.autoCalAtrOvertimeEnum[self.itemComAutoCal.normalOTTime.flexOtTime.upLimitOtSet()].value);
                    }
                });
                self.onSelectCompany().done(function() {
                    $('#tree-grid').ntsTreeComponent(self.treeGrid);
                    dfd.resolve(self);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });

                return dfd.promise();
            }

            /**
             * Initial selectable years
             */
            private setSelectableYears(): void {
                let self = this;
                let currentYear = moment();

                // Get 2 years before, 2 years after.
                let arr = [];
                arr.push(currentYear.subtract(2, 'years').year());
                arr.push(currentYear.add(1, 'years').year());
                arr.push(currentYear.add(1, 'years').year());
                arr.push(currentYear.add(1, 'years').year());
                arr.push(currentYear.add(1, 'years').year());

                // Map to model
                let mapped = arr.map(i => {
                    return { year: i }
                });

                // Binding
                self.lstTargetYear(mapped);
            }

            /**
             * load company establishment
             */
            private loadCompanyEstablishment(targetYear: number, isLoading: boolean): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                service.findCompanyEstablishment(targetYear).done(function(data) {
                    self.companyEstablishmentModel.estimateTimeModel.updateData(data.estimateTime);
                    self.companyEstablishmentModel.estimatePriceModel.updateData(data.estimatePrice);
                    self.companyEstablishmentModel.estimateDaysModel.updateData(data.estimateNumberOfDay);
                    if (isLoading) {
                        self.isLoading(false);
                    }
                    self.companyEstablishmentModel.isEnableDelete(data.setting);
                    self.initNextTabFeature();
                    dfd.resolve();
                }).fail(res => {
                    nts.uk.ui.dialog.alertError(res);
                });
                return dfd.promise();
            }

            /**
             * on click tab panel company action event
             */
            public onSelectCompany(): JQueryPromise<void> {
                $('.nts-input').ntsError('clear');
                var self = this;
                nts.uk.ui.block.invisible();
                var dfd = $.Deferred<void>();

                // Update flags.
                self.isEmploymentSelected(false);
                self.isPersonSelected(false);
                self.isCompanySelected(true);
                self.isLoading(true);

                let currentYear = self.companyEstablishmentModel.selectedYear() ?
                    self.companyEstablishmentModel.selectedYear() : moment().year();

                self.loadCompanyEstablishment(currentYear, true).done(function() {
                    dfd.resolve();
                }).always(() => {
                    $('#comboTargetYear').focus();
                    nts.uk.ui.block.clear();
                });
                return dfd.promise();
            }

            /**
             * find all employment setting to view
             */
            private updateEmploymentEstimateSetting(targetYear: number) {
                var self = this;
                service.findAllEmploymentSetting(targetYear).done(function(data) {
                    var employmentSettings: UnitAlreadySettingModel[] = [];
                    for (var employment of data) {
                        var employmentSetting: UnitAlreadySettingModel = { code: employment.employmentCode, isAlreadySetting: true };
                        employmentSettings.push(employmentSetting);
                    }
                    self.alreadySettingEmployment(employmentSettings);
                    self.updateEnableDeleteEmployment(self.selectedEmploymentCode());
                });
            }

            /**
             * check setting employment setting
             */
            private checkEmploymentSetting(employmentCode: string) {
                var self = this;
                for (var employmentSetting of self.alreadySettingEmployment()) {
                    if (employmentSetting.code === employmentCode && employmentSetting.isAlreadySetting) {
                        return true;
                    }
                }
                return false;
            }

            /**
             * load company establishment
             */
            private loadEmploymentEstablishment(targetYear: number, employmentCode: string, isLoading: boolean): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                service.findEmploymentEstablishment(targetYear, employmentCode).done(function(data) {
                    self.employmentEstablishmentModel.estimateTimeModel.updateData(data.estimateTime);
                    self.employmentEstablishmentModel.estimatePriceModel.updateData(data.estimatePrice);
                    self.employmentEstablishmentModel.estimateDaysModel.updateData(data.estimateNumberOfDay);
                    if (isLoading) {
                        self.isLoading(false);
                    }
                    self.initNextTabFeature();
                    self.updateEnableDeleteEmployment(employmentCode);
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                }).fail(res => {
                    nts.uk.ui.dialog.alertError(res);
                });
                return dfd.promise();
            }

            /**
            * on click tab panel employment action event
            */
            public onSelectEmployment(): void {
                $('.nts-input').ntsError('clear');
                nts.uk.ui.block.invisible();
                var self = this;

                // Update flags.
                self.isCompanySelected(false);
                self.isPersonSelected(false);
                self.isEmploymentSelected(true);
                self.isLoading(true);

                $('#tree-grid').ntsTreeComponent(self.treeGrid);
                // Load employment list component.
                $('#employmentSetting').ntsListComponent(self.lstEmploymentComponentOption).done(function() {
                    self.employmentList($('#employmentSetting').getDataList());
                    self.loadEmploymentEstablishment(self.employmentEstablishmentModel.selectedYear(), self.selectedEmploymentCode(), true);
                    self.updateEmploymentEstimateSetting(self.employmentEstablishmentModel.selectedYear());
                }).always(() => {
                    $('#comboTargetYear').focus();
                });

            }

            /**
             * call service find all personal setting => view UI
             */
            private updatePersonalEstimateSetting(targetYear: number) {
                var self = this;
                service.findAllPersonalSetting(targetYear).done(function(data) {
                    var personalSettings: UnitAlreadySettingModel[] = [];
                    for (var personal of data) {
                        var employmentSetting: UnitAlreadySettingModel = { code: self.findEmployeeCodeById(personal.employeeId), isAlreadySetting: true };
                        personalSettings.push(employmentSetting);
                    }
                    self.alreadySettingPersonal(personalSettings);
                    self.updateEnableDeletePersonal(self.selectedEmployeeCode());
                });
            }

            /**
             * update enable delete button by call service
             */
            private updateEnableDeleteEmployment(employementCode: string) {
                var self = this;
                self.employmentEstablishmentModel.enableDelete(self.checkEmploymentSetting(employementCode));
            }

            /**
            * update enable delete button by call service
            */
            private updateEnableDeletePersonal(employeeCode: string) {
                var self = this;
                self.personalEstablishmentModel.enableDelete(self.checkSettingPersonal(employeeCode));
            }

            /**
           * load personal establishment
           */
            private loadPersonalEstablishment(targetYear: number, employeeCode: string, isLoading: boolean): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                var employeeId = self.findEmployeeIdByCode(employeeCode);
                if (employeeId) {
                    service.findPersonalEstablishment(targetYear, employeeId).done(function(data) {
                        self.personalEstablishmentModel.estimateTimeModel.updateData(data.estimateTime);
                        self.personalEstablishmentModel.estimatePriceModel.updateData(data.estimatePrice);
                        self.personalEstablishmentModel.estimateDaysModel.updateData(data.estimateNumberOfDay);
                        if (isLoading) {
                            self.isLoading(false);
                        }
                        self.updateEnableDeletePersonal(employeeCode);
                        self.initNextTabFeature();
                        nts.uk.ui.block.clear();
                        dfd.resolve();
                    }).fail(res => {
                        nts.uk.ui.dialog.alertError(res);
                    });
                }
                else {
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * on click tab panel employment action event
             */
            public onSelectPerson(): void {
                $('.nts-input').ntsError('clear');
                var self = this;
                nts.uk.ui.block.invisible();
                self.employeeName = ko.observable('');

                // Update flags.
                self.isCompanySelected(false);
                self.isEmploymentSelected(false);
                self.isPersonSelected(true);
                self.isLoading(true);


            }
            /**
             * Default personal data
             */
            private viewDefaultPersonal(): void {
                var self = this;
                var employeeDefault: string = "01";
                service.findPersonalEstablishment(moment().year(), employeeDefault).done(function(data) {
                    self.personalEstablishmentModel.estimateTimeModel.updateData(data.estimateTime);
                    self.personalEstablishmentModel.estimatePriceModel.updateData(data.estimatePrice);
                    self.personalEstablishmentModel.estimateDaysModel.updateData(data.estimateNumberOfDay);
                    self.isLoading(false);
                });
            }

            /**
             * update selected employee kcp005 => detail
             */
            public findByCodeEmployee(employeeCode: string): UnitModel {
                var employee: UnitModel;
                var self = this;
                for (var employeeSelect of self.employeeList()) {
                    if (employeeSelect.code === employeeCode) {
                        employee = employeeSelect;
                        break;
                    }
                }
                return employee;
            }

            /**
             * Find employment by code.
             */
            public findEmploymentByCode(employmentCode: string): UnitModel {
                var employment: UnitModel;
                var self = this;
                employment = _.find(self.employmentList(), item => item.code === employmentCode);
                return employment;
            }

            /**
             * find employee id in selected
             */
            public findEmployeeIdByCode(employeeCode: string): string {
                var self = this;
                var employeeId = '';
                for (var employee of self.selectedEmployee()) {
                    if (employee.employeeCode === employeeCode) {
                        employeeId = employee.employeeId;
                    }
                }
                return employeeId;
            }

            /**
             * find employee code in selected
             */
            public findEmployeeCodeById(employeeId: string): string {
                var self = this;
                var employeeCode = '';
                for (var employee of self.selectedEmployee()) {
                    if (employee.employeeId === employeeId) {
                        employeeCode = employee.employeeCode;
                    }
                }
                return employeeCode;
            }



            /**
             * call service find all by employee id
             */
            public findAllByEmployeeIds(employeeIds: string[]): JQueryPromise<UnitAlreadySettingModel[]> {
                var dfd = $.Deferred();
                var dataRes: UnitAlreadySettingModel[] = [];
                dfd.resolve(dataRes);
                return dfd.promise();
            }

            /**
             * function on click saveCompanyEstablishment action
             */
            public saveCompanyEstablishment(): void {
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                };
                nts.uk.ui.block.invisible();
                var self = this;
                var dto: CompanyEstablishmentDto = {
                    estimateTime: self.companyEstablishmentModel.estimateTimeModel.toDto(),
                    estimatePrice: self.companyEstablishmentModel.estimatePriceModel.toDto(),
                    estimateNumberOfDay: self.companyEstablishmentModel.estimateDaysModel.toDto()

                };
                service.saveCompanyEstablishment(self.companyEstablishmentModel.selectedYear(), dto).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // reload pa    
                        self.loadCompanyEstablishment(self.companyEstablishmentModel.selectedYear(), false);
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    $('#comboTargetYear').focus();
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * function on click deleteCompanyEstablishment action
             */
            public deleteCompanyEstablishment(): void {
                var self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    nts.uk.ui.block.invisible();
                    service.deleteCompanyEstablishment(self.companyEstablishmentModel.selectedYear()).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            // reload page
                            self.loadCompanyEstablishment(self.companyEstablishmentModel.selectedYear(), false);
                            $('.nts-input').ntsError('clear');
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                    }).always(() => {
                        $('#comboTargetYear').focus();
                        nts.uk.ui.block.clear();
                    });
                });
            }

            /**
             * function on click saveEmploymentEstablishment action
             */
            public saveEmploymentEstablishment(): void {
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                };
                nts.uk.ui.block.invisible();
                var self = this;
                var dto: EmploymentEstablishmentDto = {
                    estimateTime: self.employmentEstablishmentModel.estimateTimeModel.toDto(),
                    estimatePrice: self.employmentEstablishmentModel.estimatePriceModel.toDto(),
                    estimateNumberOfDay: self.employmentEstablishmentModel.estimateDaysModel.toDto(),
                    employmentCode: self.selectedEmploymentCode()
                };

                service.saveEmploymentEstablishment(self.employmentEstablishmentModel.selectedYear(), dto).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                        self.updateEmploymentEstimateSetting(self.employmentEstablishmentModel.selectedYear());
                    });
                }).fail(res => {
                    nts.uk.ui.dialog.alertError(res);
                }).always(() => {
                    $('#comboTargetYear').focus();
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * function on click deleteEmploymentEstablishment action
             */
            public deleteEmploymentEstablishment(): void {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(() => {
                    nts.uk.ui.block.invisible();
                    service.deleteEmploymentEstablishment(
                        self.employmentEstablishmentModel.selectedYear(),
                        self.selectedEmploymentCode()).done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                                self.updateEmploymentEstimateSetting(self.employmentEstablishmentModel.selectedYear());
                                self.loadEmploymentEstablishment(self.employmentEstablishmentModel.selectedYear(), self.selectedEmploymentCode(), false);
                                $('.nts-input').ntsError('clear');
                            });
                        }).fail(res => {
                            nts.uk.ui.dialog.alertError(res);
                        }).always(() => {
                            $('#comboTargetYear').focus();
                            nts.uk.ui.block.clear();
                        });
                });
            }

            /**
             * function check employee setting
             */
            private checkSettingPersonal(employeeCode: string): boolean {
                var self = this;
                for (var employee of self.alreadySettingPersonal()) {
                    if (employee.code == employeeCode && employee.isAlreadySetting) {
                        return true;
                    }
                }
                return false;
            }

            /**
             * function on click savePersonalEstablishment action
             */
            public savePersonalEstablishment(): void {
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                };
                nts.uk.ui.block.invisible();
                var self = this;
                var dto: PersonalEstablishmentDto = {
                    estimateTime: self.personalEstablishmentModel.estimateTimeModel.toDto(),
                    estimatePrice: self.personalEstablishmentModel.estimatePriceModel.toDto(),
                    estimateNumberOfDay: self.personalEstablishmentModel.estimateDaysModel.toDto(),
                    employeeId: self.findEmployeeIdByCode(self.selectedEmployeeCode())
                };
                service.savePersonalEstablishment(self.personalEstablishmentModel.selectedYear(), dto).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // reload page    
                        self.updatePersonalEstimateSetting(self.personalEstablishmentModel.selectedYear());
                        self.loadPersonalEstablishment(self.personalEstablishmentModel.selectedYear(), self.selectedEmployeeCode(), false);
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    $('#comboTargetYear').focus();
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * function on click deletePersonalEstablishment action
             */
            public deletePersonalEstablishment(): void {
                var self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    nts.uk.ui.block.invisible();
                    service.deletePersonalEstablishment(self.personalEstablishmentModel.selectedYear(), self.findEmployeeIdByCode(self.selectedEmployeeCode())).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            // reload page
                            self.loadPersonalEstablishment(self.personalEstablishmentModel.selectedYear(), self.selectedEmployeeCode(), false);
                            self.updatePersonalEstimateSetting(self.personalEstablishmentModel.selectedYear());
                            $('.nts-input').ntsError('clear');
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                    }).always(() => {
                        $('#comboTargetYear').focus();
                        nts.uk.ui.block.clear();
                    });
                });
            }

            /**
             * open dialog UsageSettingModel (view model E)
             */
            private openDialogUsageSettingModel(): void {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/ksm/001/e/index.xhtml").onClosed(function() {
                    $('#comboTargetYear').focus();
                    self.loadUsageSettingModel();
                });
            }

            /**
             * open dialog CommonSetting (view model F)
             */
            private openDialogCommonSetting(): void {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/ksm/001/f/index.xhtml").onClosed(function() {
                    $('#comboTargetYear').focus();
                });
            }

            /**
             * set next tab index
             */
            public initNextTabFeature() {
                let self = this;
                // Auto next tab when press tab key.
                $("[tabindex='75']").on('keydown', function(e) {
                    if (e.which == 9) {
                        if (self.isCompanySelected()) {
                            self.companyEstablishmentModel.selectedTab('tab-2');
                        }
                        if (self.isEmploymentSelected()) {
                            self.employmentEstablishmentModel.selectedTab('tab-2');
                        }
                        if (self.isPersonSelected()) {
                            self.personalEstablishmentModel.selectedTab('tab-2');
                        }
                    }
                });

                $("[tabindex='141']").on('keydown', function(e) {
                    if (e.which == 9) {
                        if (self.isCompanySelected()) {
                            self.companyEstablishmentModel.selectedTab('tab-3');
                        }
                        if (self.isEmploymentSelected()) {
                            self.employmentEstablishmentModel.selectedTab('tab-3');
                        }
                        if (self.isPersonSelected()) {
                            self.personalEstablishmentModel.selectedTab('tab-3');
                        }
                    }
                });
                $("[tabindex='9']").on('keydown', function(e) {
                    if (e.which == 9 && !$(e.target).parents("[tabindex='9']")[0]) {
                        if (self.isCompanySelected()) {
                            self.companyEstablishmentModel.selectedTab('tab-1');
                        }
                        if (self.isEmploymentSelected()) {
                            self.employmentEstablishmentModel.selectedTab('tab-1');
                        }
                        if (self.isPersonSelected()) {
                            self.personalEstablishmentModel.selectedTab('tab-1');
                        }
                    }
                });
            }

        }
        // exportclass
        export class TreeType {
            static WORK_PLACE = 1;
        }
        //        SELECTIONTYPE
        export class SelectionType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;

        }
        //        ComAutoCalSettingModel
        export class ComAutoCalSettingModel {
            normalOTTime: AutoCalOvertimeSettingModel;
            flexOTTime: AutoCalFlexOvertimeSettingModel;
            restTime: AutoCalRestTimeSettingModel;

            constructor() {
                this.normalOTTime = new AutoCalOvertimeSettingModel();
                this.flexOTTime = new AutoCalFlexOvertimeSettingModel();
                this.restTime = new AutoCalRestTimeSettingModel();

            }

            updateData(dto: ComAutoCalSettingDto) {
                this.normalOTTime.updateData(dto.normalOTTime);
                this.flexOTTime.updateData(dto.flexOTTime);
                this.restTime.updateData(dto.restTime);

            }

            toDto(): ComAutoCalSettingDto {
                var dto: ComAutoCalSettingDto = {
                    normalOTTime: this.normalOTTime.toDto(),
                    flexOTTime: this.flexOTTime.toDto(),
                    restTime: this.restTime.toDto()

                };
                return dto;
            }
            resetData() {
                this.normalOTTime.resetData();
                this.flexOTTime.resetData();
                this.restTime.resetData();
            }
        }
        //        AutoCalOvertimeSettingDto
        export class AutoCalFlexOvertimeSettingModel {
            flexOtTime: AutoCalSettingModel;
            flexOtNightTime: AutoCalSettingModel;

            constructor() {
                this.flexOtTime = new AutoCalSettingModel();
                this.flexOtNightTime = new AutoCalSettingModel();

            }

            updateData(dto: AutoCalFlexOvertimeSettingDto) {
                this.flexOtTime.updateData(dto.flexOtNightTime);
                this.flexOtNightTime.updateData(dto.flexOtNightTime);

            }

            toDto(): AutoCalFlexOvertimeSettingDto {
                var dto: AutoCalFlexOvertimeSettingDto = {
                    flexOtTime: this.flexOtTime.toDto(),
                    flexOtNightTime: this.flexOtNightTime.toDto(),

                };
                return dto;
            }
            resetData() {
                this.flexOtTime.resetData();
                this.flexOtNightTime.resetData();
            }
        }

        //        AutoCalRestTimeSettingDto
        export class AutoCalRestTimeSettingModel {
            restTime: AutoCalSettingModel;
            lateNightTime: AutoCalSettingModel;

            constructor() {
                this.restTime = new AutoCalSettingModel();
                this.lateNightTime = new AutoCalSettingModel();

            }

            updateData(dto: AutoCalRestTimeSettingDto) {
                this.restTime.updateData(dto.restTime);
                this.lateNightTime.updateData(dto.lateNightTime);

            }

            toDto(): AutoCalRestTimeSettingDto {
                var dto: AutoCalRestTimeSettingDto = {
                    restTime: this.restTime.toDto(),
                    lateNightTime: this.lateNightTime.toDto(),

                };
                return dto;
            }
            resetData() {
                this.restTime.resetData();
                this.lateNightTime.resetData();
            }
        }
        //        AutoCalFlexOvertimeSettingDto
        export class AutoCalOvertimeSettingModel {
            earlyOtTime: AutoCalSettingModel;
            earlyMidOtTime: AutoCalSettingModel;
            normalOtTime: AutoCalSettingModel;
            normalMidOtTime: AutoCalSettingModel;
            legalOtTime: AutoCalSettingModel;
            legalMidOtTime: AutoCalSettingModel;

            constructor() {
                this.earlyOtTime = new AutoCalSettingModel();
                this.earlyMidOtTime = new AutoCalSettingModel();
                this.normalOtTime = new AutoCalSettingModel();
                this.normalMidOtTime = new AutoCalSettingModel();
                this.legalOtTime = new AutoCalSettingModel();
                this.legalMidOtTime = new AutoCalSettingModel();

            }

            updateData(dto: AutoCalOvertimeSettingDto) {
                this.earlyOtTime.updateData(dto.earlyOtTime);
                this.earlyMidOtTime.updateData(dto.earlyMidOtTime);
                this.normalOtTime.updateData(dto.normalOtTime);
                this.normalMidOtTime.updateData(dto.normalMidOtTime);
                this.legalOtTime.updateData(dto.legalOtTime);
                this.legalMidOtTime.updateData(dto.legalMidOtTime);

            }

            toDto(): AutoCalOvertimeSettingDto {
                var dto: AutoCalOvertimeSettingDto = {
                    earlyOtTime: this.earlyOtTime.toDto(),
                    earlyMidOtTime: this.earlyMidOtTime.toDto(),
                    normalOtTime: this.normalOtTime.toDto(),
                    normalMidOtTime: this.normalMidOtTime.toDto(),
                    legalOtTime: this.legalOtTime.toDto(),
                    legalMidOtTime: this.legalMidOtTime.toDto()

                };
                return dto;
            }
            resetData() {
                this.earlyOtTime.resetData();
                this.earlyMidOtTime.resetData();
                this.normalOtTime.resetData();
                this.normalMidOtTime.resetData();
                this.legalOtTime.resetData();
                this.legalMidOtTime.resetData();
            }
        }
        //        AutoCalSettingDto
        export class AutoCalSettingModel {
            upLimitOtSet: KnockoutObservable<number>;
            calAtr: KnockoutObservable<number>;
            constructor() {
                this.upLimitOtSet = ko.observable(1);
                this.calAtr = ko.observable(1);
            }
            updateData(dto: AutoCalSettingDto) {
                this.upLimitOtSet(dto.upLimitOtSet);
                this.calAtr(dto.calAtr);
            }

            toDto(): AutoCalSettingDto {
                var dto: AutoCalSettingDto = {
                    upLimitOtSet: this.upLimitOtSet(),
                    calAtr: this.calAtr(),
                };
                return dto;
            }
            resetData() {
                this.upLimitOtSet(0);
                this.calAtr(0);
            }
        }

























































        export class EstimateTimeModel {
            month: KnockoutObservable<number>;
            time1st: KnockoutObservable<number>;
            time2nd: KnockoutObservable<number>;
            time3rd: KnockoutObservable<number>;
            time4th: KnockoutObservable<number>;
            time5th: KnockoutObservable<number>;

            constructor() {
                this.month = ko.observable(1);
                this.time1st = ko.observable(0);
                this.time2nd = ko.observable(0);
                this.time3rd = ko.observable(0);
                this.time4th = ko.observable(0);
                this.time5th = ko.observable(0);
            }

            updateData(dto: EstimateTimeDto) {
                this.month(dto.month);
                this.time1st(dto.time1st);
                this.time2nd(dto.time2nd);
                this.time3rd(dto.time3rd);
                this.time4th(dto.time4th);
                this.time5th(dto.time5th);
            }

            toDto(): EstimateTimeDto {
                var dto: EstimateTimeDto = {
                    month: this.month(),
                    time1st: this.time1st(),
                    time2nd: this.time2nd(),
                    time3rd: this.time3rd(),
                    time4th: this.time4th(),
                    time5th: this.time5th()
                }
                return dto;
            }
        }
        export class EstimatePriceModel {
            month: KnockoutObservable<number>;
            price1st: KnockoutObservable<number>;
            price2nd: KnockoutObservable<number>;
            price3rd: KnockoutObservable<number>;
            price4th: KnockoutObservable<number>;
            price5th: KnockoutObservable<number>;

            constructor() {
                this.month = ko.observable(1);
                this.price1st = ko.observable(0);
                this.price2nd = ko.observable(0);
                this.price3rd = ko.observable(0);
                this.price4th = ko.observable(0);
                this.price5th = ko.observable(0);
            }

            updateData(dto: EstimatePriceDto) {
                this.month(dto.month);
                this.price1st(dto.price1st);
                this.price2nd(dto.price2nd);
                this.price3rd(dto.price3rd);
                this.price4th(dto.price4th);
                this.price5th(dto.price5th);
            }

            toDto(): EstimatePriceDto {
                var dto: EstimatePriceDto = {
                    month: this.month(),
                    price1st: this.price1st(),
                    price2nd: this.price2nd(),
                    price3rd: this.price3rd(),
                    price4th: this.price4th(),
                    price5th: this.price5th()
                }
                return dto;
            }
        }
        export class EstimateDaysModel {
            month: KnockoutObservable<number>;
            days1st: KnockoutObservable<number>;
            days2nd: KnockoutObservable<number>;
            days3rd: KnockoutObservable<number>;
            days4th: KnockoutObservable<number>;
            days5th: KnockoutObservable<number>;

            constructor() {
                this.month = ko.observable(1);
                this.days1st = ko.observable(0);
                this.days2nd = ko.observable(0);
                this.days3rd = ko.observable(0);
                this.days4th = ko.observable(0);
                this.days5th = ko.observable(0);
            }

            updateData(dto: EstimateDaysDto) {
                this.month(dto.month);
                this.days1st(dto.days1st);
                this.days2nd(dto.days2nd);
                this.days3rd(dto.days3rd);
                this.days4th(dto.days4th);
                this.days5th(dto.days5th);
            }

            toDto(): EstimateDaysDto {
                var dto: EstimateDaysDto = {
                    month: this.month(),
                    days1st: this.days1st(),
                    days2nd: this.days2nd(),
                    days3rd: this.days3rd(),
                    days4th: this.days4th(),
                    days5th: this.days5th()
                }
                return dto;
            }
        }

        export class EstablishmentTimeModel {
            monthlyEstimates: EstimateTimeModel[];
            yearlyEstimate: EstimateTimeModel;

            constructor() {
                this.monthlyEstimates = [];
                this.yearlyEstimate = new EstimateTimeModel();
            }

            updateData(dto: EstablishmentTimeDto) {
                if (this.monthlyEstimates.length == 0) {
                    this.monthlyEstimates = [];
                    for (var item of dto.monthlyEstimates) {
                        var model: EstimateTimeModel = new EstimateTimeModel();
                        model.updateData(item);
                        this.monthlyEstimates.push(model);
                    }
                } else {
                    for (var itemDto of dto.monthlyEstimates) {
                        for (var model of this.monthlyEstimates) {
                            if (itemDto.month == model.month()) {
                                model.updateData(itemDto);
                            }
                        }
                    }
                }
                this.yearlyEstimate.updateData(dto.yearlyEstimate);
            }

            toDto(): EstablishmentTimeDto {
                var monthlyEstimateTime: EstimateTimeDto[] = [];
                for (var item of this.monthlyEstimates) {
                    monthlyEstimateTime.push(item.toDto());
                }
                var dto: EstablishmentTimeDto = {
                    monthlyEstimates: monthlyEstimateTime,
                    yearlyEstimate: this.yearlyEstimate.toDto()
                };
                return dto;
            }
        }
        export class EstablishmentPriceModel {
            monthlyEstimates: EstimatePriceModel[];
            yearlyEstimate: EstimatePriceModel;

            constructor() {
                this.monthlyEstimates = [];
                this.yearlyEstimate = new EstimatePriceModel();
            }

            updateData(dto: EstablishmentPriceDto) {
                if (this.monthlyEstimates.length == 0) {
                    this.monthlyEstimates = [];
                    for (var item of dto.monthlyEstimates) {
                        var model: EstimatePriceModel = new EstimatePriceModel();
                        model.updateData(item);
                        this.monthlyEstimates.push(model);
                    }
                } else {
                    for (var itemDto of dto.monthlyEstimates) {
                        for (var model of this.monthlyEstimates) {
                            if (model.month() == itemDto.month) {
                                model.updateData(itemDto);
                            }
                        }
                    }
                }
                this.yearlyEstimate.updateData(dto.yearlyEstimate);
            }

            toDto(): EstablishmentPriceDto {
                var monthlyEstimatePrice: EstimatePriceDto[] = [];
                for (var item of this.monthlyEstimates) {
                    monthlyEstimatePrice.push(item.toDto());
                }
                var dto: EstablishmentPriceDto = {
                    monthlyEstimates: monthlyEstimatePrice,
                    yearlyEstimate: this.yearlyEstimate.toDto()
                };
                return dto;
            }
        }

        export class EstablishmentDaysModel {
            monthlyEstimates: EstimateDaysModel[];
            yearlyEstimate: EstimateDaysModel;

            constructor() {
                this.monthlyEstimates = [];
                this.yearlyEstimate = new EstimateDaysModel();
            }

            updateData(dto: EstablishmentDaysDto) {
                if (this.monthlyEstimates.length == 0) {
                    this.monthlyEstimates = [];
                    for (var item of dto.monthlyEstimates) {
                        var model: EstimateDaysModel = new EstimateDaysModel();
                        model.updateData(item);
                        this.monthlyEstimates.push(model);
                    }
                } else {
                    for (var itemDto of dto.monthlyEstimates) {
                        for (var model of this.monthlyEstimates) {
                            if (model.month() == itemDto.month) {
                                model.updateData(itemDto);
                            }
                        }
                    }
                }
                this.yearlyEstimate.updateData(dto.yearlyEstimate);
            }

            toDto(): EstablishmentDaysDto {
                var monthlyEstimateDays: EstimateDaysDto[] = [];
                for (var item of this.monthlyEstimates) {
                    monthlyEstimateDays.push(item.toDto());
                }
                var dto: EstablishmentDaysDto = {
                    monthlyEstimates: monthlyEstimateDays,
                    yearlyEstimate: this.yearlyEstimate.toDto()
                };
                return dto;
            }
        }

        export class EstablishmentModel {
            estimateTimeModel: EstablishmentTimeModel;
            estimatePriceModel: EstablishmentPriceModel;
            estimateDaysModel: EstablishmentDaysModel;
            selectedTab: KnockoutObservable<string>;
            isEditable: KnockoutObservable<boolean>;
            isEnableDelete: KnockoutObservable<boolean>;
            selectedYear: KnockoutObservable<number>;
            constructor() {
                this.estimateTimeModel = new EstablishmentTimeModel();
                this.estimatePriceModel = new EstablishmentPriceModel();
                this.estimateDaysModel = new EstablishmentDaysModel();
                this.selectedTab = ko.observable('tab-1');
                this.isEditable = ko.observable(true);
                this.isEnableDelete = ko.observable(true);
                this.selectedYear = ko.observable(moment().year());
            }

            enableDelete(isDelete: boolean) {
                this.isEnableDelete(isDelete);
            }
            enableInput(): void {
                this.isEditable(true);
            }
            disableInput(): void {
                this.isEditable(false);
            }
        }


        export class UsageSettingModel {
            settingEmployment: KnockoutObservable<boolean>;
            settingPersonal: KnockoutObservable<boolean>;
            constructor() {
                this.settingEmployment = ko.observable(true);
                this.settingPersonal = ko.observable(true);
            }
            updateData(dto: UsageSettingDto) {
                this.settingEmployment(dto.employmentSetting);
                this.settingPersonal(dto.personalSetting);
            }

            toDto(): UsageSettingDto {
                var dto: UsageSettingDto = {
                    employmentSetting: this.settingEmployment(),
                    personalSetting: this.settingPersonal()
                };
                return dto;
            }
        }

        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }

        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }

        export interface UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
        }

        export interface EmployeeSearchDto {
            employeeId: string;

            employeeCode: string;

            employeeName: string;

            workplaceCode: string;

            workplaceId: string;

            workplaceName: string;
        }

        export interface GroupOption {
            baseDate?: KnockoutObservable<Date>;
            // クイック検索タブ
            isQuickSearchTab: boolean;
            // 参照可能な社員すべて
            isAllReferableEmployee: boolean;
            //自分だけ
            isOnlyMe: boolean;
            //おなじ部門の社員
            isEmployeeOfWorkplace: boolean;
            //おなじ＋配下部門の社員
            isEmployeeWorkplaceFollow: boolean;


            // 詳細検索タブ
            isAdvancedSearchTab: boolean;
            //複数選択 
            isMutipleCheck: boolean;

            //社員指定タイプ or 全社員タイプ
            isSelectAllEmployee: boolean;

            onSearchAllClicked: (data: EmployeeSearchDto[]) => void;

            onSearchOnlyClicked: (data: EmployeeSearchDto) => void;

            onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;

            onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;

            onApplyEmployee: (data: EmployeeSearchDto[]) => void;
        }
    }
}