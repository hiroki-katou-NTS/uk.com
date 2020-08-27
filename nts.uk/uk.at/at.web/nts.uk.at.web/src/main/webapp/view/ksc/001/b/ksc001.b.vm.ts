module nts.uk.at.view.ksc001.b {

    import NtsWizardStep = service.model.NtsWizardStep;
    import PeriodDto = service.model.PeriodDto;
    import UserInfoDto = service.model.UserInfoDto;
    import ScheduleExecutionLogSaveDto = service.model.ScheduleExecutionLogSaveDto;
    import ScheduleExecutionLogSaveRespone = service.model.ScheduleExecutionLogSaveRespone;
    import baseService = nts.uk.at.view.kdl023.base.service;
    import DailyPatternSetting = baseService.model.DailyPatternSetting;
    import ReflectionSetting = baseService.model.ReflectionSetting;
    import getText = nts.uk.resource.getText;
    // 休日反映方法
    import ReflectionMethod = baseService.model.ReflectionMethod;
    import DayOffSetting = baseService.model.DayOffSetting;

    export module viewmodel {

        export class ScreenModel {

            // step setup
            stepList: Array<NtsWizardStep>;
            stepSelected: KnockoutObservable<NtsWizardStep>;

            // setup ccg001
            ccgcomponent: GroupOption;

            // Options
            baseDate: KnockoutObservable<Date> = ko.observable(new Date());
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);

            selectRebuildAtr: KnockoutObservableArray<RadioBoxModel>;
            selectImplementAtr: KnockoutObservableArray<RadioBoxModel>;
            selectedImplementAtrCode: KnockoutObservable<number>;
            selectRebuildAtrCode: KnockoutObservable<number>;
            checkReCreateAtrAllCase: KnockoutObservable<number> = ko.observable(0);
            checkProcessExecutionAtrRebuild: KnockoutObservable<number> = ko.observable(0);
            checkCreateMethodAtrPersonalInfo: KnockoutObservable<number> = ko.observable(0);
            resetMasterInfo: KnockoutObservable<boolean> = ko.observable(false);
            confirm: KnockoutObservable<boolean> = ko.observable(false);

            recreateConverter: KnockoutObservable<boolean> = ko.observable(false);
            recreateEmployeeOffWork: KnockoutObservable<boolean> = ko.observable(false);
            recreateDirectBouncer: KnockoutObservable<boolean> = ko.observable(false);
            recreateShortTermEmployee: KnockoutObservable<boolean> = ko.observable(false);
            recreateWorkTypeChange: KnockoutObservable<boolean> = ko.observable(false);
            recreateShortTimeWorkers: KnockoutObservable<boolean> = ko.observable(false);

            overwriteConfirmedData: KnockoutObservable<boolean> = ko.observable(false);
            createAfterDeleting: KnockoutObservable<boolean> = ko.observable(false);

            resetWorkingHours: KnockoutObservable<boolean> = ko.observable(false);
            resetStartEndTime: KnockoutObservable<boolean> = ko.observable(false);
            resetTimeAssignment: KnockoutObservable<boolean> = ko.observable(false);

            periodDate: KnockoutObservable<any> = ko.observable({});
            copyStartDate: KnockoutObservable<any> = ko.observable(new Date());
            startDateString: KnockoutObservable<string> = ko.observable("");
            endDateString: KnockoutObservable<string> = ko.observable("");

            lstLabelInfomationB: KnockoutObservableArray<string> = ko.observableArray([]);
            lstLabelInfomationC: KnockoutObservableArray<string> = ko.observableArray([]);
            infoCreateMethod: KnockoutObservable<string> = ko.observable('');
            infoPeriodDate: KnockoutObservable<string> = ko.observable('');
            lengthEmployeeSelected: KnockoutObservable<string> = ko.observable('');

            // Employee tab
            lstPersonComponentOption: any;
            selectedEmployeeCode: KnockoutObservableArray<string> = ko.observableArray([]);
            employeeName: KnockoutObservable<string>;
            employeeList: KnockoutObservableArray<UnitModel>;
            alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
            ccgcomponentPerson: GroupOption;
            personalScheduleInfo: KnockoutObservable<PersonalSchedule> = ko.observable(new PersonalSchedule());
            responeReflectionSetting: KnockoutObservable<ReflectionSetting> = ko.observable(null);
            responeDailyPatternSetting: KnockoutObservable<DailyPatternSetting> = ko.observable(null);
	        employeeId: KnockoutObservable<string> = ko.observable(null);
            //for control field
            isReCreate: KnockoutObservable<boolean>;
            isReSetting: KnockoutObservable<boolean>;
            isRebuildTargetOnly: KnockoutObservable<boolean>;
            isEnableRadioboxRebuildAtr: KnockoutObservable<boolean>;

            //list
            lstCreateMethod: KnockoutObservableArray<any>;
            lstReCreate: KnockoutObservableArray<any>;
            lstProcessExecution: KnockoutObservableArray<any>;
            periodStartDate: KnockoutObservable<moment.Moment> = ko.observable(moment());
            periodEndDate: KnockoutObservable<moment.Moment> = ko.observable(moment());

            //update
            selectedRadio: KnockoutObservable<number> = ko.observable();
            isFixedSchedules: KnockoutObservable<boolean>;
            isHandleUpdateSchedule: KnockoutObservable<boolean>;
            isMonthlyPatternRq: KnockoutObservable<boolean>;
            monthlyPatternCode: KnockoutObservable<number> = ko.observable();
            creationMethodCode: KnockoutObservable<number> = ko.observable();
            monthlyPatternOpts: KnockoutObservableArray<any> = ko.observable();
            creationMethodReference: KnockoutObservableArray<any> = ko.observable();
            isMonthlyPattern: KnockoutObservable<boolean>;
            isCreationMethod: KnockoutObservable<boolean>;
            isEnableNextPageC: KnockoutObservable<boolean> = ko.observable(true);
            isEnableNextPageD: KnockoutObservable<boolean> = ko.observable(true);
            isConfirmedCreation: KnockoutObservable<boolean> = ko.observable(false);
            isCopyStartDate: KnockoutObservable<boolean> = ko.observable(false);
	        implementAtr: KnockoutObservable<number> = ko.observable(0);
	        createMethodAtr: KnockoutObservable<number> = ko.observable(0);
	        employeeIds: KnockoutObservableArray<string> = ko.observable();

            fullSizeSpace: string = "　　";

            constructor() {
                let self = this;
                let lstRadioBoxModelImplementAtr: RadioBoxModel[] = [];
                let lstRadioBoxModelRebuildAtr: RadioBoxModel[] = [];

                self.stepList = [
                    {content: '.step-1'},
                    {content: '.step-2'},
                    {content: '.step-3'},
                    {content: '.step-4'}
                ];
                self.reloadCcg001();

                self.stepSelected = ko.observable({id: 'step-1', content: '.step-1'});

                //radios Button Group B4_3
                lstRadioBoxModelImplementAtr.push(new RadioBoxModel(ImplementAtr.GENERALLY_CREATED, getText("KSC001_74")));
                lstRadioBoxModelImplementAtr.push(new RadioBoxModel(ImplementAtr.RECREATE, getText("KSC001_75")));
                self.selectImplementAtr = ko.observableArray(lstRadioBoxModelImplementAtr);
                self.selectedImplementAtrCode = ko.observable(ImplementAtr.GENERALLY_CREATED);

                //ntsSwitchButton B4_6
                lstRadioBoxModelRebuildAtr.push(new RadioBoxModel(ReBuildAtr.REBUILD_ALL, getText("KSC001_89")));
                lstRadioBoxModelRebuildAtr.push(new RadioBoxModel(ReBuildAtr.REBUILD_TARGET_ONLY, getText("KSC001_90")));
                self.selectRebuildAtr = ko.observableArray(lstRadioBoxModelRebuildAtr);
                self.selectRebuildAtrCode = ko.observable(ReBuildAtr.REBUILD_ALL);

                //for control field
                self.isReCreate = ko.computed(function () {
                    return self.selectedImplementAtrCode() == ImplementAtr.RECREATE;
                });

                // for is reseting
                self.isReSetting = ko.computed(function () {
                    return self.checkProcessExecutionAtrRebuild() == ProcessExecutionAtr.RECONFIG && self.isReCreate();
                });

                self.isEnableRadioboxRebuildAtr = ko.computed(() => {
                    return self.checkProcessExecutionAtrRebuild() == ProcessExecutionAtr.REBUILD && self.isReCreate();
                });

                self.isRebuildTargetOnly = ko.computed(() => {
                    return self.selectRebuildAtrCode() == ReBuildAtr.REBUILD_TARGET_ONLY
                        && self.isEnableRadioboxRebuildAtr();
                });

                self.isFixedSchedules = ko.computed(() => {
                    /*return self.selectRebuildAtrCode() == ReBuildAtr.REBUILD_TARGET_ONLY
                        && self.isEnableRadioboxRebuildAtr();*/
                    return self.selectedImplementAtrCode() == ImplementAtr.RECREATE;
                });

                //B4_3 => B4_5
                self.isHandleUpdateSchedule = ko.computed(() => {
                    return self.selectedImplementAtrCode() == ImplementAtr.RECREATE;
                });


                self.periodDate.subscribe((newValue) => {
                    let newDate = ({});
                    if (newValue.startDate) {
                        self.copyStartDate(newValue.startDate);
                        newDate = {
                            startDate: moment.utc(newValue.startDate),
                            endDate: moment.utc(newValue.endDate)
                        }
                        self.periodStartDate(newDate.startDate);
                        self.periodEndDate(newDate.endDate);
                    }
                });

                self.lstCreateMethod = ko.observableArray(__viewContext.enums.CreateMethodAtr);
                self.lstReCreate = ko.observableArray(__viewContext.enums.ReCreateAtr);
                self.lstProcessExecution = ko.observableArray(__viewContext.enums.ProcessExecutionAtr);

                self.selectedRadio = ko.observable(1);
                self.isMonthlyPatternRq = ko.computed(() => {
                    return self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE &&
                        self.creationMethodCode() == CreationMethodRef.MONTHLY_PATTERN;
                });

                self.isCreationMethod = ko.computed(() => {
                    return self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE;
                });
                self.isMonthlyPattern = ko.computed(() => {
                    return self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE
                            && self.creationMethodCode() == CreationMethodRef.MONTHLY_PATTERN; //月間パターン
                });

                self.isCopyStartDate = ko.computed(() => {
                    return self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.COPY_PAST_SCHEDULE;
                });

                self.monthlyPatternCode(null);
                self.creationMethodCode(null);

                self.checkCreateMethodAtrPersonalInfo.subscribe((value) => {
                    nts.uk.ui.errors.clearAll();
                    if (value == CreateMethodAtr.PATTERN_SCHEDULE) {
                        $('.monthly-pattern-code').focus();
                    }
                })
                self.isEnableNextPageC = ko.computed(() => {

                    if( self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE
                        && self.creationMethodCode() == CreationMethodRef.MONTHLY_PATTERN
                        && nts.uk.util.isNullOrEmpty(self.monthlyPatternCode()) ) {
                        return false;
                    } else
                        return true;
                });

                self.isEnableNextPageD = ko.computed( () => {
                    return self.selectedEmployeeCode().length > 0;
                });

                self.creationMethodCode.subscribe(( value) => {
                    if( self.creationMethodCode() != CreationMethodRef.MONTHLY_PATTERN ) {
                        nts.uk.ui.errors.clearAll();
                        //self.monthlyPatternCode(null);
                    } else {
                        //self.monthlyPatternCode(null);
                        $('.monthly-pattern-code').focus();
                    }
                });

                self.checkCreateMethodAtrPersonalInfo.subscribe(( value ) => {
                    if( value === CreateMethodAtr.COPY_PAST_SCHEDULE
                        && nts.uk.util.isNullOrEmpty(self.copyStartDate())) {
                        $('#copy-start-date').focus();
                        if(self.isInValidCopyPasteSchedule()) return;
                    } else {
                        if( value === CreateMethodAtr.PATTERN_SCHEDULE
                            && self.creationMethodCode() == CreationMethodRef.MONTHLY_PATTERN
                            && self.monthlyPatternOpts().length <= 0 ) {
                            let msgError = nts.uk.resource.getText('KSC001_111');
                            $('.monthly-pattern-code')
                            .ntsError('clear')
                            .ntsError('set',{ messageId:'MsgB_2', messageParams: [ msgError ] });
                        }

                        nts.uk.ui.errors.clearAll();
                        let copyStartDate = moment(self.periodStartDate()).format('YYYY/MM/DD');
                        self.copyStartDate(copyStartDate);
                    }
                });

                self.creationMethodReference([
                    {code: 0, name: nts.uk.resource.getText('KSC001_108')}, //会社カレンダー
                    {code: 1, name: nts.uk.resource.getText('KSC001_109')}, //職場カレンダー
                    {code: 2, name: nts.uk.resource.getText('KSC001_110')}, //分類カレンダー
                    {code: 3, name: nts.uk.resource.getText('KSC001_111')}, //月間パターン
                ]);

                self.monthlyPatternOpts([]);
	            self.employeeIds([]);
                //get monthly pattern
                self.getMonthlyPattern();
            }

            /**
             * save to client service PersonalSchedule by employeeId
             */
            private savePersonalScheduleByEmployeeId(employeeId: string, data: PersonalSchedule): void {
                nts.uk.characteristics.save("PersonalSchedule_" + employeeId, data);
            }

            /**
             * save to client service PersonalSchedule
             */
            private savePersonalSchedule(data: PersonalSchedule): void {
                let self = this,
                    user: any = __viewContext.user;
                self.savePersonalScheduleByEmployeeId(user.employeeId, data);
            }

            /**
             * find by client service PersonalSchedule by employee
             */
            private findPersonalScheduleByEmployeeId(employeeId: string): JQueryPromise<PersonalSchedule> {
                return nts.uk.characteristics.restore("PersonalSchedule_" + employeeId);
            }

            /**
             * find by client service PersonalSchedule
             */
            private findPersonalSchedule(): JQueryPromise<PersonalSchedule> {
                let self = this,
                    user: any = __viewContext.user;
                return nts.uk.characteristics.restore("PersonalSchedule_" + user.employeeId);
            }

            /**
             * function next wizard by on click button
             */
            private next(): JQueryPromise<void> {
                return $('#wizard').ntsWizard("next");
            }

            /**
             * function previous wizard by on click button
             */
            private previous(): JQueryPromise<void> {
                return $('#wizard').ntsWizard("prev");
            }

            /**
             * function next two page wizard by on click button
             */
            private nextTwo(): JQueryPromise<void> {
                let index = $('#wizard').ntsWizard("getCurrentStep");
                index = index + 2;
                return $('#wizard').ntsWizard("goto", index);
            }

            /**
             * function previous wizard by on click button
             */
            private previousTwo(): JQueryPromise<void> {
                let index = $('#wizard').ntsWizard("getCurrentStep");
                index = index - 2;
                return $('#wizard').ntsWizard("goto", index);
            }

            /**
             * function convert string to Date
             */
            private toDate(strDate: string): Date {
                return moment(strDate, 'YYYY/MM/DD').toDate();
            }

            public reloadCcg001(): void {
                let self = this;
                if ($('.ccg-sample-has-error').ntsError('hasError')) {
                    return;
                }

                self.ccgcomponent = {
                    /** Common properties */
                    systemType: 2, // システム区分
                    showEmployeeSelection: false, // 検索タイプ
                    showQuickSearchTab: true, // クイック検索
                    showAdvancedSearchTab: true, // 詳細検索
                    showBaseDate: false, // 基準日利用
                    showClosure: false, // 就業締め日利用
                    showAllClosure: false, // 全締め表示
                    showPeriod: true, // 対象期間利用
                    periodFormatYM: false, // 対象期間精度

                    /** Required parameter */
                    baseDate: self.baseDate().toISOString(), // 基準日
                    periodStartDate: self.periodStartDate().toISOString(), // 対象期間開始日
                    periodEndDate: self.periodEndDate().toISOString(), // 対象期間終了日
                    inService: true, // 在職区分
                    leaveOfAbsence: true, // 休職区分
                    closed: true, // 休業区分
                    retirement: true, // 退職区分

                    /** Quick search tab options */
                    showAllReferableEmployee: true, // 参照可能な社員すべて
                    showOnlyMe: true, // 自分だけ
                    showSameWorkplace: true, // 同じ職場の社員
                    showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

                    /** Advanced search properties */
                    showEmployment: true, // 雇用条件
                    showWorkplace: true, // 職場条件
                    showClassification: true, // 分類条件
                    showJobTitle: true, // 職位条件
                    showWorktype: true, // 勤種条件
                    isMutipleCheck: true, // 選択モード

                    /** Return data */
                    returnDataFromCcg001: function (data: any) {
                        const mappedEmployeeList = _.map(data.listEmployee, employeeSearch => {
                            return {
                                code: employeeSearch.employeeCode,
                                name: employeeSearch.employeeName,
                                affiliationName: employeeSearch.affiliationName
                            }
                        });

                        self.employeeList(mappedEmployeeList);
                        self.selectedEmployee(data.listEmployee);
                        self.applyKCP005ContentSearch(data.listEmployee);
                    }
                }
            }

            /**
             * start page data
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                // block ui
                nts.uk.ui.block.invisible();
                // find closure by id = 1
                /*service.findPeriodById(1).done(function (data) {
                    // update start date end date to ccg001
                    self.periodDate({
                        startDate: data.startDate,
                        endDate: data.endDate
                    });
                    self.reloadCcg001();
                    dfd.resolve(self);
                });*/

                //get startdate and enddate for a schedule
                service.getInitialDate().done( (data) => {
                    // update start date end date to ccg001
                    self.periodDate({
                        startDate: data.startDate,
                        endDate: data.endDate
                    });
                    self.reloadCcg001();
                    dfd.resolve(self);
                });

                //init Schedule for personal
                self.displayPersonalInfor();

                return dfd.promise();
            }

            /**
             * apply ccg001 search data to kcp005
             */
            public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
                let self = this,
                    employeeSearchs: UnitModel[] = [],
                    listSelectedEmpCode: any = [],
	                employeeIds: Array<string> = [] ;
                self.employeeList([]);
                self.selectedEmployeeCode([]);

                console.log(dataList);
                _.each(dataList, (employeeSearch) => {
                    employeeSearchs.push({
                        code: employeeSearch.employeeCode,
                        name: employeeSearch.employeeName,
                        affiliationName: employeeSearch.affiliationName
                    });
                    listSelectedEmpCode.push(employeeSearch.employeeCode.trim());
	                employeeIds.push(employeeSearch.employeeId);
                });

                // update employee list by ccg001 search
                self.employeeList(employeeSearchs);
                self.selectedEmployeeCode(listSelectedEmpCode);
                self.employeeIds(employeeIds);
                //filter personal with new conditions
                let startDate = moment.utc(self.periodStartDate(), "YYYY/MM/DD");
                let endDate = moment.utc(self.periodEndDate(), "YYYY/MM/DD");
                let listEmployeeFilter: ListEmployeeIds = new ListEmployeeIds(
                    employeeIds,
                    self.recreateConverter(),
                    self.recreateEmployeeOffWork(),
                    self.recreateShortTimeWorkers(),
                    self.recreateDirectBouncer(),
                    startDate, endDate
                );

                //pending
                let newListEmployees = self.listEmployeeFilter(listEmployeeFilter);

                //reset
                employeeSearchs = []; listSelectedEmpCode = [];
                newListEmployees && newListEmployees.map((employeeSearch) => {
                    employeeSearchs.push({
                        code: employeeSearch.employeeCode,
                        name: employeeSearch.employeeName,
                        affiliationName: employeeSearch.affiliationName
                    });
                    listSelectedEmpCode.push(employeeSearch.employeeCode.trim());
                });

                //end
                // update kc005
                self.lstPersonComponentOption = {
                    isShowAlreadySet: false, //設定済表示
                    isMultiSelect: true,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedEmployeeCode,
                    isDialog: true,
                    isShowNoSelectRow: false, //未選択表示
                    alreadySettingList: self.alreadySettingPersonal,
                    isShowWorkPlaceName: true, //職場表示
                    isShowSelectAllButton: false, //全選択表示
                    isSelectAllAfterReload: true,
                    maxWidth: 550,
                    maxRows: 10,
                    tabindex: 5
                };
            }

            /**
             * function next page by selection employee goto page (C)
             */
            private nextPageEmployee(): void {
                var self = this;
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                }
                // check selection employee
                if (self.selectedEmployeeCode && self.selectedEmployee() && self.selectedEmployeeCode().length > 0) {
                    var user: any = __viewContext.user;
                    self.findPersonalScheduleByEmployeeId(user.employeeId).done(function (data) {
                        self.updatePersonalScheduleData(data);

                        // focus by done
                        self.next().done(function () {
                            $('#inputSelectImplementAtr').focus();
                        });
                    }).fail(function (error) {
                        console.log(error);
                    });
                }
                else {
                    // show message by not choose employee of kcp005
                    nts.uk.ui.dialog.alertError({messageId: 'Msg_206'});
                }
            }

            /**
             * update PersonalSchedule by find by employee id login
             */
            private updatePersonalScheduleData(data: PersonalSchedule): void {
                var self = this;
                if (data) {
                    self.personalScheduleInfo(data);
                }
            }

            /**
             * convert ui to PersonalSchedule
             */
            private toPersonalScheduleData(): PersonalSchedule {
                var self = this;
                var user: any = __viewContext.user;
                var data: PersonalSchedule = new PersonalSchedule();

                data.employeeId = user.employeeId;
                data.implementAtr = self.selectedImplementAtrCode();
                data.reCreateAtr = self.checkReCreateAtrAllCase();
                data.processExecutionAtr = self.checkProcessExecutionAtrRebuild();
                data.rebuildTargetAtr = self.selectRebuildAtrCode();
                data.recreateConverter = self.recreateConverter();
                data.recreateEmployeeOffWork = self.recreateEmployeeOffWork();
                data.recreateDirectBouncer = self.recreateDirectBouncer();
                data.recreateShortTermEmployee = self.recreateShortTermEmployee();
                data.recreateWorkTypeChange = self.recreateWorkTypeChange();
                data.recreateShortTimeWorkers = self.recreateShortTimeWorkers();

                data.resetWorkingHours = self.resetWorkingHours();
                data.resetStartEndTime = self.resetStartEndTime();
                data.resetMasterInfo = self.resetMasterInfo();
                data.resetTimeAssignment = self.resetTimeAssignment();
                data.confirm = self.isConfirmedCreation();//self.confirm();
                data.createMethodAtr = self.checkCreateMethodAtrPersonalInfo();

                data.overwriteConfirmedData = self.overwriteConfirmedData();
                data.createAfterDeleting = self.createAfterDeleting();
                data.selectedImplementAtrCode = self.selectedImplementAtrCode();
                data.selectRebuildAtrCode = self.selectRebuildAtrCode();
                data.checkCreateMethodAtrPersonalInfo = self.checkCreateMethodAtrPersonalInfo();
                data.creationMethodCode = self.creationMethodCode();
                data.monthlyPatternCode = self.monthlyPatternCode();
                data.isConfirmedCreation = self.isConfirmedCreation();

                return data;
            }

            /**
             * function previous page by selection employee goto page (C)
             */
            private previousPageC(): void {
                var self = this;
                self.previous();
            }

            /**
             * function next page by selection employee goto next page
             */
            private nextPageB(): void {
                var self = this;

                self.builDataForScreenC();
                
                if ((self.selectedImplementAtrCode() == ImplementAtr.RECREATE)
                    && self.selectRebuildAtrCode() == ReBuildAtr.REBUILD_TARGET_ONLY) {
                    if (!self.recreateConverter() && !self.recreateEmployeeOffWork()
                        && !self.recreateDirectBouncer() && !self.recreateShortTimeWorkers()) {
                        nts.uk.ui.dialog.error({messageId: "Msg_1734"});
                        $('#checkBoxGroup').focus();
                        return;
                    }
                }

                if (self.selectedImplementAtrCode() == ImplementAtr.RECREATE
                    && self.createAfterDeleting()) {
                    nts.uk.ui.dialog.confirmDanger({messageId: "Msg_1735"})
                        .ifYes(() => {
                            //goto screen C
                            self.next().done(function () {
                            });
                        }).ifNo(() => {
                        return;
                    });
                } else {
                    //goto screen C
                    self.next ().done ( function () {
                    } );
                }
            }

            private builDataForScreenC() {
                let self = this;
                let lstLabelInfomation: Array<string> = [];

                //{0}　～　{1} -> KSC001_46
                self.infoPeriodDate(getText("KSC001_46", [self.periodDate().startDate, self.periodDate().endDate]));

                //build from B for E
                //B4_4 -> #KSC001_74, B4_5 -> #KSC001_75
                if(self.selectedImplementAtrCode() == ImplementAtr.GENERALLY_CREATED)
                    lstLabelInfomation.push(getText("KSC001_74"));
                else {
                    lstLabelInfomation.push(getText("KSC001_75"));

                    //B4_7 -> #KSC001_37 + #KSC001_89, B4_8 -> #KSC001_37 + #KSC001_90
                    if (self.selectRebuildAtrCode() == ReBuildAtr.REBUILD_ALL)
                        lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_89"));
                    else {
                        lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_90"));

                        //B5_2 -> 「▲」:fullSizeSpace +  #KSC001_38 + #KSC001_91
                        if (self.recreateConverter())
                            lstLabelInfomation.push(self.fullSizeSpace + getText("KSC001_38") + getText("KSC001_91"));
                        //B5_3 -> 「▲」:fullSizeSpace +  #KSC001_38 + #KSC001_92
                        if (self.recreateEmployeeOffWork())
                            lstLabelInfomation.push(self.fullSizeSpace + getText("KSC001_38") + getText("KSC001_92"));
                        //B5_4 -> 「▲」:fullSizeSpace +  #KSC001_38 + #KSC001_93
                        if (self.recreateShortTimeWorkers())
                            lstLabelInfomation.push(self.fullSizeSpace + getText("KSC001_38") + getText("KSC001_93"));
                        //B5_5 -> 「▲」:fullSizeSpace +  #KSC001_38 + #KSC001_94
                        if (self.recreateDirectBouncer())
                            lstLabelInfomation.push(self.fullSizeSpace + getText("KSC001_38") + getText("KSC001_94"));
                    }

                    //B6_3 Or B6_4: [#KSC001_37 + #KSC001_104] or [#KSC001_37 + #KSC001_105]
                    if (self.overwriteConfirmedData()) {
                        lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_104"));
                    } else if (self.createAfterDeleting())
                        lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_105"));
                    //B6_3
                    if (self.overwriteConfirmedData())
                        lstLabelInfomation.push(self.fullSizeSpace + getText("KSC001_38") + getText("KSC001_105"));
                    //B6_4
                    if (self.createAfterDeleting())
                        lstLabelInfomation.push(self.fullSizeSpace + getText("KSC001_38") + getText("KSC001_106"));
                }
                //apply from B -> E1_6
                self.lstLabelInfomationB(lstLabelInfomation);
            }
            /**
             * function previous page by selection employee goto page (B)
             */
            private previousPageC(): void {
                var self = this;
                self.previous();
            }
            /**
             * function previous page by selection employee goto page (D)
             */
            private nextPageC(): void {
                var self = this;

                if (self.isInValidCopyPasteSchedule()) {
                    return;
                }

                self.buildString();

                self.next().done(function () {
                });
            }

            /**
             * Validate copy paste schedule
             */
            private isInValidCopyPasteSchedule(): boolean {
                let self = this;
                if (self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.COPY_PAST_SCHEDULE) {
                    $('#copy-start-date').ntsEditor('validate');
                }
                return $('.nts-input').ntsError('hasError');
            }

            /**
             * function previous page by selection employee goto page (D)
             */
            private previousPageD(): void {
                var self = this;
                if (self.isInValidCopyPasteSchedule()) {
                    return;
                }
                self.previous();
            }

            /**
             * function next page by selection employee goto page (E)
             */
            private nextPageD(): void {
                var self = this;

                if( self.selectedEmployeeCode().length <= 0 ) {
                    nts.uk.ui.dialog.error({ messageId: "Msg_206" }); //Msg_758
                    return;
                } else {
                    self.lengthEmployeeSelected(getText("KSC001_47", [self.selectedEmployeeCode().length]));
                    self.openDialogPageE();
                }
                /*if (self.isInValidCopyPasteSchedule()) {
                    return;
                }
                // check D1_4 is checked
                if (self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE) {
                    if (self.responeReflectionSetting()) {
                        // next page E by pattern code of self
                        self.findByPatternCodeAndOpenPageE(self.responeReflectionSetting().selectedPatternCd);
                    }
                    else {
                        self.findPersonalSchedule().done(function (res) {
                            if (res && res != null) {
                                // next page E by pattern code of res
                                self.findByPatternCodeAndOpenPageE(res.patternCode);
                            } else {
                                self.findAllPattern();
                            }
                        });
                    }
                } else {
                    self.openDialogPageE();
                }*/
            }

            /**
             * Find all pattern
             */
            private findAllPattern(): void {
                let self = this;
                _.defer(() => {
                    nts.uk.ui.block.invisible();
                    baseService.findAllPattern()
                        .done(allData => {
                            // next page E by pattern code of all data first
                            if (allData && allData.length > 0) {
                                self.responeDailyPatternSetting(allData[0]);
                                self.openDialogPageE();
                            } else {
                                // show message error 531
                                nts.uk.ui.dialog.alertError({messageId: 'Msg_531'});
                            }
                        }).always(() => nts.uk.ui.block.clear());
                });
            }

            /**
             * find by pattern code and open dialog E
             */
            private findByPatternCodeAndOpenPageE(patternCode: string): void {
                var self = this;
                _.defer(() => {
                    nts.uk.ui.block.invisible();
                    baseService.findPatternByCode(patternCode).done(function (res) {
                        if (res && res != null) {
                            self.responeDailyPatternSetting(res);
                            self.openDialogPageE();
                        }
                        else {
                            nts.uk.ui.dialog.alertError({messageId: 'Msg_531'});
                        }
                    }).always(() => nts.uk.ui.block.clear());
                });
            }

            /**
             * open dialog E
             */
            private openDialogPageE(): void {
                var self = this;
                //self.buildString();
                self.next().done(function () {
                    $('#buttonFinishPageE').focus();
                });
            }

            /**
             * function previous page by selection employee goto page (E)
             */
            private previousPageE(): void {
                var self = this;

                if ((self.selectedImplementAtrCode() == ImplementAtr.RECREATE)
                    && self.checkCreateMethodAtrPersonalInfo() ==  CreateMethodAtr.PATTERN_SCHEDULE) { //checkProcessExecutionAtrRebuild == ProcessExecutionAtr.RECONFIG
                    //back screen C
                    self.previousTwo();
                } else {
                    self.previous();
                }
            }

            /**
             * finish next page by selection employee goto page (F)
             */
            private finish(): void {
                var self = this;
                nts.uk.ui.block.invisible();
                service.checkThreeMonth(self.toDate(self.periodDate().startDate)).done(function (check) {
                    nts.uk.ui.block.clear();
                    if (check) {
                        // show message confirm 567
                        nts.uk.ui.dialog.confirm({messageId: 'Msg_567'}).ifYes(function () {
                            self.createByCheckMaxMonth();
                        }).ifNo(function () {
                            return;
                        });
                    } else {
                        self.createByCheckMaxMonth();
                    }
                }).fail(function (error) {
                    console.log(error);
                });
            }

            /**
             * function build string to page E
             */
            private buildString() {
                var self = this;
                var lstLabelInfomation: string[] = [];

                if (self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PERSONAL_INFO) {
                    lstLabelInfomation.push(getText("KSC001_22")); //#KSC001_22
                } else if (self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.COPY_PAST_SCHEDULE) {
                    lstLabelInfomation.push(getText("KSC001_24"));
                    //#KSC001_37+#KSC001_26+#KSC001_114+「C2_15」
                    let copyStartDate = moment(self.copyStartDate()).format('YYYY/MM/DD');
                    lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_26") + getText("KSC001_114") + copyStartDate);
                } else {
                    lstLabelInfomation.push(getText("KSC001_23"));
                    switch (self.creationMethodCode()) {
                        case 0:
                            //#KSC001_37+#KSC001_113+#KSC001_114+#KSC001_108
                            lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_113") + getText("KSC001_114") + getText("KSC001_108"));
                            break
                        case 1:
                            //#KSC001_37+#KSC001_113+#KSC001_114+#KSC001_109
                            lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_113") + getText("KSC001_114") + getText("KSC001_109"));
                            break;
                        case 2:
                            //#KSC001_37+#KSC001_113+#KSC001_114+#KSC001_109
                            lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_113") + getText("KSC001_114") + getText("KSC001_110"));
                            break;
                        case 3:
                            //#KSC001_37+#KSC001_113+#KSC001_114+#KSC001_111
                            lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_113") + getText("KSC001_114") + getText("KSC001_111"));
                            //#KSC001_37+#KSC001_111+#KSC001_114+「C2_12」+「▲」+「C2_13」
                            let monthlyPattern = self.monthlyPatternOpts().find(element => element.code == self.monthlyPatternCode());
                            let monthlyPatternText = self.monthlyPatternCode() + self.fullSizeSpace + monthlyPattern.name;
                            lstLabelInfomation.push(getText("KSC001_37") + getText("KSC001_111") + getText("KSC001_114") + monthlyPatternText);
                            break;
                    }
                }

                //C3_2
                if (self.isConfirmedCreation()) {
                    lstLabelInfomation.push(getText("KSC001_17")); //#KSC001_17
                }

                //apply from C -> E1_9
                self.lstLabelInfomationC(lstLabelInfomation);
            }

            /**
             * function createPersonalSchedule to client by check month max
             */
            private createByCheckMaxMonth(): void {
                let self = this;
                _.defer(() => {
                    nts.uk.ui.block.invisible();
                    service.checkMonthMax(self.toDate(self.periodDate().startDate)).done(checkMax => {
                        nts.uk.ui.block.clear();
                        if (checkMax) {
                            nts.uk.ui.dialog.confirm({messageId: 'Msg_568'}).ifYes(function () {
                                self.createPersonalSchedule();
                            }).ifNo(function () {
                                return;
                            });
                        } else {
                            self.createPersonalSchedule();
                        }
                    });
                });
            }

            /**
             * function createPersonalSchedule to client
             */
            private createPersonalSchedule(): void {
                let self = this;
                nts.uk.ui.dialog.confirm({messageId: 'Msg_569'}).ifYes(function () {
                    // C1_5 is check -> B4_5 is checked
                    if (self.selectedImplementAtrCode() == ImplementAtr.RECREATE) {
                        nts.uk.ui.dialog.confirm({messageId: 'Msg_570'}).ifYes(function () {
                            self.savePersonalScheduleData();
                        }).ifNo(function () {
                            return;
                        });
                    }
                    else {
                        self.savePersonalScheduleData();
                    }
                }).ifNo(function () {
                    return;
                });

            }

            /**
             * save PersonalSchedule data
             */
            private savePersonalScheduleData(): void {
                let self = this;
                self.savePersonalSchedule(self.toPersonalScheduleData());
                service.addScheduleExecutionLog(self.scheduleCollectionData()).done(function (data) {
                    console.log(data);
                    nts.uk.ui.block.clear();
                    nts.uk.ui.windows.setShared('inputData', data);
                    nts.uk.ui.windows.sub.modal("/view/ksc/001/f/index.xhtml").onClosed(function () {
                    });
                });
            }

            /**
             * open dialog KDL023
             */
            private showDialogKDL023(): void {
                let self = this,
                    data: PersonalSchedule = new PersonalSchedule();
                self.findPersonalSchedule().done(function (dataInfo) {
                    if (dataInfo && dataInfo != null) {
                        data = dataInfo;
                    }
                    nts.uk.ui.windows.setShared('reflectionSetting', self.convertPersonalScheduleToReflectionSetting(data));
                    nts.uk.ui.windows.sub.modal('/view/kdl/023/b/index.xhtml').onClosed(() => {
                        let dto = nts.uk.ui.windows.getShared('returnedData');
                        self.responeReflectionSetting(dto);
                    });
                });

            }

            /**
             * convert data personal schedule to refelctionSetting
             */
            private convertPersonalScheduleToReflectionSetting(data: PersonalSchedule): ReflectionSetting {
                let self = this,
                    dto: ReflectionSetting = {
                        calendarStartDate: self.periodDate().startDate,
                        calendarEndDate: self.periodDate().endDate,
                        selectedPatternCd: data.patternCode,
                        patternStartDate: self.periodDate().startDate,
                        reflectionMethod: data.holidayReflect,
                        statutorySetting: self.convertWorktypeSetting(data.statutoryHolidayUseAtr, data.statutoryHolidayWorkType),
                        holidaySetting: self.convertWorktypeSetting(data.holidayUseAtr, data.holidayWorkType),
                        nonStatutorySetting: self.convertWorktypeSetting(data.legalHolidayUseAtr, data.legalHolidayWorkType)
                    };
                return dto;
            }

            /**
             * find employee id in selected
             */
            public findEmployeeIdByCode(employeeCode: string): string {
                let self = this,
                    employeeId = '';
                for (let employee of self.selectedEmployee()) {
                    if (employee.employeeCode === employeeCode) {
                        employeeId = employee.employeeId;
                    }
                }
                return employeeId;
            }

            /**
             * find employee id in selection employee code
             */
            public findEmployeeIdsByCode(employeeCodes: string[]): string[] {
                let self = this,
                    employeeIds: string[] = [];
                for (let employeeCode of employeeCodes) {
                    let employeeId = self.findEmployeeIdByCode(employeeCode);
                    if (employeeId && !(employeeId === '')) {
                        employeeIds.push(employeeId);
                    }
                }
                return employeeIds;
            }

            /**
             * collection data => command save
             */
            private scheduleCollectionData(): ScheduleExecutionLogSaveDto {
                var self = this,
                    user: any = __viewContext.user,
                    data: PersonalSchedule = self.toPersonalScheduleData(),
                    dto: ScheduleExecutionLogSaveDto = {
	                    periodStartDate: self.toDate(self.periodDate().startDate),
                        periodEndDate: self.toDate(self.periodDate().endDate),
                        creationType: data.selectedImplementAtrCode,
                        reTargetAtr: data.selectRebuildAtrCode,
                        referenceMaster: data.creationMethodCode,
                        reTargetTransfer: data.recreateConverter,
                        reTargetLeave: data.recreateEmployeeOffWork,
                        reTargetShortWork: data.recreateShortTimeWorkers,
                        reTargetLaborChange: data.recreateDirectBouncer,
                        reOverwriteConfirmed: data.overwriteConfirmedData,
                        reOverwriteRevised: data.createAfterDeleting,
                        monthlyPatternId: data.monthlyPatternCode,
                        beConfirmed: data.confirm, //confirm
                        creationMethod: data.creationMethodCode,
                        copyStartYmd: self.toDate(self.copyStartDate()), //copyStartDate
                        employeeIds: self.employeeIds(),
	                    employeeIdLogin: user.employeeIdLogin
                    };
                return dto;
            }

	        /**
	         * collection data => command save
	         */
	        private collectionData(): ScheduleExecutionLogSaveDto {
		        var self = this,
			        data: PersonalSchedule = self.toPersonalScheduleData(),
			        dto: ScheduleExecutionLogSaveDto = {
				        periodStartDate: self.toDate(self.periodDate().startDate),
				        periodEndDate: self.toDate(self.periodDate().endDate),
				        implementAtr: data.implementAtr,
				        reCreateAtr: data.reCreateAtr,
				        processExecutionAtr: data.processExecutionAtr,
				        reTargetAtr: data.rebuildTargetAtr,
				        resetWorkingHours: data.resetWorkingHours,
				        resetMasterInfo: data.resetMasterInfo,
				        reTimeAssignment: data.resetTimeAssignment,
				        reConverter: data.recreateConverter,
				        reStartEndTime: data.resetStartEndTime,
				        reEmpOffWork: data.recreateEmployeeOffWork,
				        reShortTermEmp: data.recreateShortTermEmployee,
				        reWorkTypeChange: data.recreateWorkTypeChange,
				        reDirectBouncer: data.recreateDirectBouncer,
				        // TODO
				        protectHandCorrect: null,
				        confirm: data.confirm,
				        createMethodAtr: data.createMethodAtr,
				        copyStartDate: self.toDate(self.copyStartDate()),
				        employeeIds: self.findEmployeeIdsByCode(self.selectedEmployeeCode())
			        };
		        return dto;
	        }
            /**
             * convert work type setting
             */
            private convertWorktypeSetting(use: number, worktypeCode: string): DayOffSetting {
                let data: DayOffSetting = {
                    useClassification: use == UseAtr.USE,
                    workTypeCode: worktypeCode
                };
                return data;
            }

            //get personal infor from local storage
            private displayPersonalInfor() {
                let self = this;
                let user: any = __viewContext.user;
                self.findPersonalScheduleByEmployeeId(user.employeeId).done((data) => {
                    if( typeof data !=='undefined' && data ) {
                        self.recreateConverter(data.recreateConverter);//異動者
                        self.recreateDirectBouncer(data.recreateDirectBouncer); //休職休業者
                        self.recreateShortTimeWorkers(data.recreateShortTimeWorkers);//短時間勤務者
                        self.recreateWorkTypeChange(data.recreateWorkTypeChange);//労働条件変更者

                        self.recreateEmployeeOffWork(data.recreateEmployeeOffWork);
                        self.recreateShortTermEmployee(data.recreateShortTermEmployee);
                        //Page B
                        self.createAfterDeleting(data.createAfterDeleting);
                        self.overwriteConfirmedData(data.overwriteConfirmedData);
                        self.selectedImplementAtrCode(data.selectedImplementAtrCode);
                        self.selectRebuildAtrCode(data.selectRebuildAtrCode);
	                    self.checkCreateMethodAtrPersonalInfo(data.checkCreateMethodAtrPersonalInfo);
                        self.isConfirmedCreation(data.isConfirmedCreation);
                        self.monthlyPatternCode(data.monthlyPatternCode);
                        self.creationMethodCode(data.creationMethodCode);
                        self.confirm(data.confirm);
                        self.createMethodAtr(data.createMethodAtr);
                        self.employeeId(data.employeeId);
                        //self.holidayReflect(data.holidayReflect);
                        ///self.holidayUseAtr(data.holidayUseAtr);
                        //self.holidayWorkType(data.holidayWorkType);
                        self.implementAtr(data.implementAtr);
                        //self.legalHolidayUseAtr(data.legalHolidayUseAtr);
                        //self.legalHolidayWorkType(data.legalHolidayWorkType);
                        //self.patternCode(data.patternCode);
                        //self.patternStartDate(data.patternStartDate);
                        //self.processExecutionAtr(data.processExecutionAtr);
                        //self.reCreateAtr(data.reCreateAtr);
                        self.resetMasterInfo(data.resetMasterInfo);
                        self.resetStartEndTime(data.resetStartEndTime);
                        self.resetTimeAssignment(data.resetTimeAssignment);
                        self.resetWorkingHours(data.resetWorkingHours);
                        //self.statutoryHolidayUseAtr(data.statutoryHolidayUseAtr);
                        //self.statutoryHolidayWorkType(data.statutoryHolidayWorkType);
                    }
                });
            }

            private  getMonthlyPattern() {
                let self = this;
                service.getMonthlyPattern()
                .done( ( response ) => {
                    if( typeof response !== 'undefined' && response.listMonthlyPattern.length > 0 ) {
                        let monthlyOptions = [];
                        monthlyOptions.push( new MonthlyPatternModel());
                        response.listMonthlyPattern.map( ( item, i) => {
                            monthlyOptions.push( new MonthlyPatternModel(
                                item.monthlyPatternCode, item.monthlyPatternName, item.companyId
                            ));
                        });

                        self.monthlyPatternOpts(monthlyOptions);
                    }
                })
                .always()
                .fail((error) => {
                    console.log(error);
                });
            }

            private listEmployeeFilter( data: ListEmployeeIds){
                //getEmployeeListAfterFilter
                let results : Array<string> = [];

                let self = this;
                service.getEmployeeListAfterFilter( data )
                .done( ( response ) => {
                    results = response.listEmployeeId;
                })
                .fail((error) => {
                    results = error;
                });

                return results;
            }
        }


        // 実施区分
        export enum ImplementAtr {
            // 通常作成
            GENERALLY_CREATED = 0,

            // 再作成
            RECREATE = 1
        }

        // 再作成区分
        export enum ReCreateAtr {
            // 全件
            ALLCASE = 0,

            // 未確定データのみ
            ONLYUNCONFIRM = 1
        }

        // 作成方法区分
        export enum CreateMethodAtr {
            // 個人情報
            PERSONAL_INFO = 0,

            // パターンスケジュール
            PATTERN_SCHEDULE = 1,

            // 過去スケジュールコピー
            COPY_PAST_SCHEDULE = 2
        }

        // 処理実行区分
        export enum ProcessExecutionAtr {
            // もう一度作り直す 
            REBUILD = 0,

            // 再設定する
            RECONFIG = 1
        }

        // 再作成対象区分
        export enum ReBuildAtr {
            // 全員
            REBUILD_ALL = 0,

            // 対象者のみ
            REBUILD_TARGET_ONLY = 1
        }

        // 利用区分
        export enum UseAtr {
            // 使用しない
            NOTUSE = 0,

            // 使用する
            USE = 1
        }

        // 作成方法（参照先）
        export enum CreationMethodRef {
            // 会社カレンダー
            COMPANY_CALENDAR = 0,

            // 職場カレンダー
            WORKPLACE_CALENDAR = 1,

            //分類カレンダー
            CLASSIFICATION_CALENDAR = 2,

            //月間パターン
            MONTHLY_PATTERN = 3
        }

        // 個人スケジュールの作成
        export class PersonalSchedule {
            // 社員ID
            employeeId: string;

            //実施区分
            implementAtr: ImplementAtr;

            // 再作成区分
            reCreateAtr: ReCreateAtr;

            // 処理実行区分
            processExecutionAtr: ProcessExecutionAtr;

            // 再作成対象区分
            rebuildTargetAtr: ReBuildAtr;

            // 異動者を再作成
            recreateConverter: boolean;

            // 休職休業者を再作成
            recreateEmployeeOffWork: boolean;

            // 直行直帰者を再作成
            recreateDirectBouncer: boolean;

            // 短時間勤務者を再作成
            recreateShortTermEmployee: boolean;

            // 勤務種別変更者を再作成
            recreateWorkTypeChange: boolean;

            // 勤務開始・終了時刻を再設定
            resetWorkingHours: boolean;

            // 休憩開始・終了時刻を再設定
            resetStartEndTime: boolean;

            // マスタ情報を再設定
            resetMasterInfo: boolean;

            // 申し送り時間を再設定
            resetTimeAssignment: boolean;

            // 作成時に確定済みにする
            confirm: boolean;

            // 作成方法区分
            createMethodAtr: CreateMethodAtr

            // パターンコード
            patternCode: string;

            // 休日反映方法
            holidayReflect: ReflectionMethod;

            // パターン開始日
            patternStartDate: Date;

            // 法内休日利用区分
            legalHolidayUseAtr: UseAtr

            // 法内休日勤務種類
            legalHolidayWorkType: string;

            // 法外休日利用区分
            statutoryHolidayUseAtr: UseAtr;

            //法外休日勤務種類
            statutoryHolidayWorkType: string

            // 祝日利用区分
            holidayUseAtr: UseAtr;

            // 祝日勤務種類
            holidayWorkType: string;

            recreateShortTimeWorkers: boolean;
            //確定データを上書き
            overwriteConfirmedData: boolean;
            //削除してから作成
            createAfterDeleting: boolean;
            selectedImplementAtrCode: number;
            selectRebuildAtrCode: number;
            checkCreateMethodAtrPersonalInfo : number;
            creationMethodCode : number;
            monthlyPatternCode : number;
            isConfirmedCreation : boolean;

            constructor() {
                let self = this;

                self.employeeId = '';
                self.implementAtr = ImplementAtr.GENERALLY_CREATED;
                self.reCreateAtr = ReCreateAtr.ALLCASE;
                self.processExecutionAtr = ProcessExecutionAtr.REBUILD;
                self.rebuildTargetAtr = ReBuildAtr.REBUILD_ALL;
                self.recreateConverter = false;
                self.recreateEmployeeOffWork = false;
                self.recreateDirectBouncer = false;
                self.recreateShortTermEmployee = false;
                self.recreateWorkTypeChange = false;
                self.resetWorkingHours = false;
                self.resetStartEndTime = false;
                self.resetMasterInfo = false;
                self.resetTimeAssignment = false;
                self.confirm = false;
                self.createMethodAtr = CreateMethodAtr.PERSONAL_INFO;
                self.patternCode = '02';
                self.holidayReflect = ReflectionMethod.Overwrite;
                self.patternStartDate = new Date();
                self.legalHolidayUseAtr = UseAtr.NOTUSE;
                self.legalHolidayWorkType = '';
                self.statutoryHolidayUseAtr = UseAtr.NOTUSE;
                self.statutoryHolidayWorkType = '';
                self.holidayUseAtr = UseAtr.NOTUSE;
                self.holidayWorkType = '';

                self.selectedImplementAtrCode = ImplementAtr.GENERALLY_CREATED;
                self.selectRebuildAtrCode = ReBuildAtr.REBUILD_ALL;
                self.overwriteConfirmedData = false;
                self.createAfterDeleting = false;
                self.checkCreateMethodAtrPersonalInfo = 0;
                self.creationMethodCode = 0;
                self.monthlyPatternCode = 0;
                self.isConfirmedCreation = false;
            }
        }

        export class MonthlyPatternModel {
            code: string;
            name: string;
            company: string;

            constructor(code: string = null, name: string = null, company: string = null) {
                this.code = code;
                this.name = ( code !== null) ? code + "　　" + name :  name;
                this.company = company;
            }
        }

        export class RadioBoxModel {
            id: number;
            name: string;

            constructor(id: number, name: string) {
                this.id = id;
                this.name = name;
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
            affiliationName?: string;
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

        export class ListEmployeeIds {

            listEmployeeId: Array<string>;
            /** 異動者. */
            transfer: boolean;
            /** 休職休業者. */
            leaveOfAbsence: boolean;
            /** 短時間勤務者. */
            shortWorkingHours: boolean;
            /** 労働条件変更者. */
            changedWorkingConditions: boolean;
            startDate : string; //YYYY/MM/DD UTC
            endDate : string; //YYYY/MM/DD UTC

            constructor(employeeIds: Array<string>, transfer: boolean,
                        leaveOfAbsence: boolean, shortWorkingHours: boolean,
                        changedWorkingConditions: boolean, startDate : string, endDate : string) {
                this.listEmployeeId = employeeIds;
                this.transfer = transfer;
                this.leaveOfAbsence = leaveOfAbsence;
                this.shortWorkingHours = shortWorkingHours;
                this.changedWorkingConditions = changedWorkingConditions;
                this.startDate = startDate;
                this.endDate = endDate;

            }
        }
    }
}