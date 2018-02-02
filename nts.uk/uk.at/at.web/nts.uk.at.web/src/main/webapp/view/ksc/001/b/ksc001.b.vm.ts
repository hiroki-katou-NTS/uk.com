module nts.uk.at.view.ksc001.b {

    import NtsWizardStep = service.model.NtsWizardStep;
    import PeriodDto = service.model.PeriodDto;
    import UserInfoDto = service.model.UserInfoDto;
    import ScheduleExecutionLogSaveDto = service.model.ScheduleExecutionLogSaveDto;
    import ScheduleExecutionLogSaveRespone = service.model.ScheduleExecutionLogSaveRespone;
    import baseService = nts.uk.at.view.kdl023.base.service;
    import DailyPatternSetting = baseService.model.DailyPatternSetting;
    import ReflectionSetting = baseService.model.ReflectionSetting;
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
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

            selectImplementAtr: KnockoutObservableArray<RadioBoxModel>;
            selectedImplementAtrCode: KnockoutObservable<number>;
            checkReCreateAtrAllCase: KnockoutObservable<boolean>;
            checkReCreateAtrOnlyUnConfirm: KnockoutObservable<boolean>;
            checkProcessExecutionAtrRebuild: KnockoutObservable<boolean>;
            checkProcessExecutionAtrReconfig: KnockoutObservable<boolean>;
            checkCreateMethodAtrPersonalInfo: KnockoutObservable<number>;
            resetWorkingHours: KnockoutObservable<boolean>;
            resetDirectLineBounce: KnockoutObservable<boolean>;
            resetMasterInfo: KnockoutObservable<boolean>;
            resetTimeChildCare: KnockoutObservable<boolean>;
            resetAbsentHolidayBusines: KnockoutObservable<boolean>;
            resetTimeAssignment: KnockoutObservable<boolean>;
            confirm: KnockoutObservable<boolean>;

            periodDate: KnockoutObservable<any>;
            copyStartDate: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;

            lstLabelInfomation: KnockoutObservableArray<string>;
            infoCreateMethod: KnockoutObservable<string>;
            infoPeriodDate: KnockoutObservable<string>;
            lengthEmployeeSelected: KnockoutObservable<string>;

            // Employee tab
            lstPersonComponentOption: any;
            selectedEmployeeCode: KnockoutObservableArray<string>;
            employeeName: KnockoutObservable<string>;
            employeeList: KnockoutObservableArray<UnitModel>;
            alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;
            ccgcomponentPerson: GroupOption;
            personalScheduleInfo: KnockoutObservable<PersonalSchedule>;
            responeReflectionSetting: KnockoutObservable<ReflectionSetting>;
            responeDailyPatternSetting: KnockoutObservable<DailyPatternSetting>;

            //for control field
            isReCreate: KnockoutObservable<boolean>;
            isReSetting: KnockoutObservable<boolean>;
            
            //list
            lstCreateMethod: KnockoutObservableArray<any>;
            
            constructor() {
                var self = this;

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.stepList = [
                    { content: '.step-1' },
                    { content: '.step-2' },
                    { content: '.step-3' },
                    { content: '.step-4' }
                ];
                self.selectedEmployee = ko.observableArray([]);
                self.selectedEmployeeCode = ko.observableArray([]);
                self.alreadySettingPersonal = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                self.personalScheduleInfo = ko.observable(new PersonalSchedule());
                self.responeReflectionSetting = ko.observable(null);
                self.responeDailyPatternSetting = ko.observable(null);

                self.periodDate = ko.observable({});
                self.checkReCreateAtrOnlyUnConfirm = ko.observable(false);
                self.checkReCreateAtrAllCase = ko.observable(true);
                self.checkProcessExecutionAtrRebuild = ko.observable(true);
                self.checkProcessExecutionAtrReconfig = ko.observable(false);
                self.resetWorkingHours = ko.observable(false);
                self.resetDirectLineBounce = ko.observable(false);
                self.resetMasterInfo = ko.observable(false);
                self.resetTimeChildCare = ko.observable(false);
                self.resetAbsentHolidayBusines = ko.observable(false);
                self.resetTimeAssignment = ko.observable(false);
                self.confirm = ko.observable(false);
                self.checkCreateMethodAtrPersonalInfo = ko.observable(0);
                self.copyStartDate = ko.observable(new Date());
                self.ccgcomponent = {
                    baseDate: self.baseDate,
                    //Show/hide options
                    isQuickSearchTab: true,
                    isAdvancedSearchTab: true,
                    isAllReferableEmployee: true,
                    isOnlyMe: true,
                    isEmployeeOfWorkplace: true,
                    isEmployeeWorkplaceFollow: true,
                    isMutipleCheck: true,
                    isSelectAllEmployee: true,
                    /**
                    * @param dataList: list employee returned from component.
                    * Define how to use this list employee by yourself in the function's body.
                    */
                    onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataList);
                        self.applyKCP005ContentSearch(dataList);
                    },
                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                        var dataEmployee: EmployeeSearchDto[] = [];
                        dataEmployee.push(data);
                        self.selectedEmployee(dataEmployee);
                        self.applyKCP005ContentSearch(dataEmployee);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataList);
                        self.applyKCP005ContentSearch(dataList);
                    },
                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataList);
                        self.applyKCP005ContentSearch(dataList);
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        self.applyKCP005ContentSearch(dataEmployee);
                    }

                }
                self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
                var lstRadioBoxModelImplementAtr: RadioBoxModel[] = [];
                lstRadioBoxModelImplementAtr.push(new RadioBoxModel(ImplementAtr.GENERALLY_CREATED,
                    nts.uk.resource.getText("KSC001_74")));
                lstRadioBoxModelImplementAtr.push(new RadioBoxModel(ImplementAtr.RECREATE,
                    nts.uk.resource.getText("KSC001_75")));
                self.selectImplementAtr = ko.observableArray(lstRadioBoxModelImplementAtr);
                self.selectedImplementAtrCode = ko.observable(ImplementAtr.GENERALLY_CREATED);

                // update ReCreateAtr
                self.checkReCreateAtrAllCase.subscribe(function(check: boolean) {
                    self.checkReCreateAtrOnlyUnConfirm(!check);
                });
                self.checkReCreateAtrOnlyUnConfirm.subscribe(function(check: boolean) {
                    self.checkReCreateAtrAllCase(!check);
                });

                // update ProcessExecutionAtr
                self.checkProcessExecutionAtrRebuild.subscribe(function(check: boolean) {
                    self.checkProcessExecutionAtrReconfig(!check);
                });
                self.checkProcessExecutionAtrReconfig.subscribe(function(check: boolean) {
                    self.checkProcessExecutionAtrRebuild(!check);
                });

                self.lstLabelInfomation = ko.observableArray([]);
                self.infoCreateMethod = ko.observable('');
                self.infoPeriodDate = ko.observable('');
                self.lengthEmployeeSelected = ko.observable('');

                //for control field
                self.isReCreate = ko.computed(function() {
                    return self.selectedImplementAtrCode() == ImplementAtr.RECREATE;
                });

                // for is reseting
                self.isReSetting = ko.computed(function() {
                    return self.checkProcessExecutionAtrReconfig() && self.isReCreate();
                });
                self.periodDate.subscribe((newValue)=>{
                    if(newValue.startDate){
                        self.copyStartDate(newValue.startDate);    
                    }    
                });
                
                self.lstCreateMethod = ko.observableArray(__viewContext.enums.CreateMethodAtr);
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
                var self = this;
                var user: any = __viewContext.user;
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
                var self = this;
                var user: any = __viewContext.user;
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
                var index = $('#wizard').ntsWizard("getCurrentStep");
                index = index + 2;
                return $('#wizard').ntsWizard("goto", index);
            }
            /**
            * function previous wizard by on click button 
            */
            private previousTwo(): JQueryPromise<void> {
                var index = $('#wizard').ntsWizard("getCurrentStep");
                index = index - 2;
                return $('#wizard').ntsWizard("goto", index);
            }
            /**
             * function convert string to Date
             */
            private toDate(strDate: string): Date {
                return moment(strDate, 'YYYY/MM/DD').toDate();
            }
            /**
           * start page data 
           */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                // block ui
                nts.uk.ui.block.invisible();

                // find closure by id = 1
                service.findPeriodById(1).done(function(data) {
                    // update start date end date to ccg001
                    self.periodDate({
                        startDate: data.startDate,
                        endDate: data.endDate
                    });
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            /**
            * apply ccg001 search data to kcp005
            */
            public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
                var self = this;
                self.employeeList([]);
                var employeeSearchs: UnitModel[] = [];
                for (var employeeSearch of dataList) {
                    var employee: UnitModel = {
                        code: employeeSearch.employeeCode,
                        name: employeeSearch.employeeName,
                        workplaceName: employeeSearch.workplaceName
                    };
                    employeeSearchs.push(employee);
                }

                // update employee list by ccg001 search 
                self.employeeList(employeeSearchs);

                // update kc005
                self.lstPersonComponentOption = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedEmployeeCode,
                    isDialog: true,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.alreadySettingPersonal,
                    isShowWorkPlaceName: true,
                    isShowSelectAllButton: false,
                    maxWidth: 550,
                    maxRows: 15,
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
                    self.findPersonalScheduleByEmployeeId(user.employeeId).done(function(data) {
                        self.updatePersonalScheduleData(data);

                        // focus by done
                        self.next().done(function() {
                            $('#inputSelectImplementAtr').focus();
                        });
                    }).fail(function(error) {
                        console.log(error);
                    });
                }
                else {
                    // show message by not choose employee of kcp005
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_206' });
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
                data.resetMasterInfo = self.resetMasterInfo();
                data.resetAbsentHolidayBusines = self.resetAbsentHolidayBusines();

                // set CreateMethodAtr
                data.createMethodAtr = self.checkCreateMethodAtrPersonalInfo();
                
                data.confirm = self.confirm();

                // set ReCreateAtr
                if (self.checkReCreateAtrAllCase()) {
                    data.reCreateAtr = ReCreateAtr.ALLCASE;
                }
                if (self.checkReCreateAtrOnlyUnConfirm()) {
                    data.reCreateAtr = ReCreateAtr.ONLYUNCONFIRM;
                }

                // set ProcessExecutionAtr
                if (self.checkProcessExecutionAtrRebuild()) {
                    data.processExecutionAtr = ProcessExecutionAtr.REBUILD;
                }
                if (self.checkProcessExecutionAtrReconfig()) {
                    data.processExecutionAtr = ProcessExecutionAtr.RECONFIG;
                }

                // set ImplementAtr
                data.implementAtr = self.selectedImplementAtrCode();

                data.resetWorkingHours = self.resetWorkingHours();

                data.resetTimeAssignment = self.resetTimeAssignment();

                data.resetDirectLineBounce = self.resetDirectLineBounce();

                data.employeeId = user.employeeId;

                data.resetTimeChildCare = self.resetTimeChildCare();
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
            private nextPageC(): void {
                var self = this;
                if ((self.selectedImplementAtrCode() == ImplementAtr.RECREATE)
                    && self.checkProcessExecutionAtrReconfig()) {
                    //build string for Screen E
                    self.buildString();
                    //goto screen E
                    self.nextTwo().done(function() {
                        $('#buttonFinishPageE').focus();
                    });
                }
                else {
                    self.next().done(function() {
                        $('#inputCreateMethodAtr').focus();
                    });
                }

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
                if (self.isInValidCopyPasteSchedule()) {
                    return;
                }

                // check D1_4 is checked
                if (self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE) {

                    if (self.responeReflectionSetting()) {

                        // next page E by pattern code of self
                        self.findByPatternCodeAndOpenPageE(self.responeReflectionSetting().selectedPatternCd);
                    }
                    else {
                        self.findPersonalSchedule().done(function(res) {
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
                }

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
                                nts.uk.ui.dialog.alertError({ messageId: 'Msg_531' });
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
                    baseService.findPatternByCode(patternCode).done(function(res) {
                        if (res && res != null) {
                            self.responeDailyPatternSetting(res);
                            self.openDialogPageE();
                        }
                        else {
                            nts.uk.ui.dialog.alertError({ messageId: 'Msg_531' });
                        }
                    }).always(() => nts.uk.ui.block.clear());
                });
            }

            /**
             * open dialog E
             */
            private openDialogPageE(): void {
                var self = this;
                self.buildString();
                self.next().done(function() {
                    $('#buttonFinishPageE').focus();
                });
            }
            /**
             * function previous page by selection employee goto page (E)
             */
            private previousPageE(): void {
                var self = this;

                if ((self.selectedImplementAtrCode() == ImplementAtr.RECREATE)
                    && self.checkProcessExecutionAtrReconfig()) {
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
                service.checkThreeMonth(self.toDate(self.periodDate().startDate)).done(function(check) {
                    nts.uk.ui.block.clear();
                    if (check) {
                        // show message confirm 567
                        nts.uk.ui.dialog.confirm({ messageId: 'Msg_567' }).ifYes(function() {
                            self.createByCheckMaxMonth();
                        }).ifNo(function() {
                            return;
                        });
                    } else {
                        self.createByCheckMaxMonth();
                    }
                }).fail(function(error) {
                    console.log(error);
                });
            }

            /**
             * function build string to page E
             */
            private buildString() {
                var self = this;
                var lstLabelInfomation: string[] = [];

                //NO1
                if (self.selectedImplementAtrCode() == ImplementAtr.GENERALLY_CREATED) {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_35"));
                } else {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_36"));

                    //NO2
                    if (self.checkReCreateAtrAllCase()) {
                        lstLabelInfomation.push(nts.uk.resource.getText("KSC001_37")
                            + nts.uk.resource.getText("KSC001_4"));
                    }
                    if (self.checkReCreateAtrOnlyUnConfirm()) {
                        lstLabelInfomation.push(nts.uk.resource.getText("KSC001_37")
                            + nts.uk.resource.getText("KSC001_5"));
                    }

                    //NO3
                    if (self.checkProcessExecutionAtrRebuild()) {
                        lstLabelInfomation.push(nts.uk.resource.getText("KSC001_37")
                            + nts.uk.resource.getText("KSC001_7"));
                    } else {
                        lstLabelInfomation.push(nts.uk.resource.getText("KSC001_37")
                            + nts.uk.resource.getText("KSC001_8"));

                        //NO4
                        if (self.resetWorkingHours()) {
                            lstLabelInfomation.push(" " + nts.uk.resource.getText("KSC001_38")
                                + nts.uk.resource.getText("KSC001_15"));
                        }

                        //NO5
                        if (self.resetDirectLineBounce()) {
                            lstLabelInfomation.push(" " + nts.uk.resource.getText("KSC001_38")
                                + nts.uk.resource.getText("KSC001_11"));
                        }

                        //NO6
                        if (self.resetMasterInfo()) {
                            lstLabelInfomation.push(" " + nts.uk.resource.getText("KSC001_38")
                                + nts.uk.resource.getText("KSC001_12"));
                        }

                        //NO7
                        if (self.resetTimeChildCare()) {
                            lstLabelInfomation.push(" " + nts.uk.resource.getText("KSC001_38")
                                + nts.uk.resource.getText("KSC001_13"));
                        }

                        //NO8
                        if (self.resetAbsentHolidayBusines()) {
                            lstLabelInfomation.push(" " + nts.uk.resource.getText("KSC001_38")
                                + nts.uk.resource.getText("KSC001_14"));
                        }

                        //NO9
                        if (self.resetTimeAssignment()) {
                            lstLabelInfomation.push(" " + nts.uk.resource.getText("KSC001_38")
                                + nts.uk.resource.getText("KSC001_16"));
                        }
                    }
                }

                if (self.confirm()) {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_17"));
                }
                self.lstLabelInfomation(lstLabelInfomation);

                //reset infoCreateMethod !important
                self.infoCreateMethod('');
                //check select recreate and select resetting
                if (!((self.selectedImplementAtrCode() == ImplementAtr.RECREATE)
                    && self.checkProcessExecutionAtrReconfig())) {

                    // set to view
                    if (self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PERSONAL_INFO) {
                        self.infoCreateMethod(nts.uk.resource.getText("KSC001_22"));
                    }

                    // set to view
                    if (self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE) {
                        self.infoCreateMethod(nts.uk.resource.getText("KSC001_23"));
                    }
                    // set to view
                    if (self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.COPY_PAST_SCHEDULE) {
                        self.infoCreateMethod(nts.uk.resource.getText("KSC001_39",
                            [moment(self.copyStartDate()).format('YYYY/MM/DD')]));
                    }
                }
                // set to view info
                self.infoPeriodDate(nts.uk.resource.getText("KSC001_46",
                    [self.periodDate().startDate, self.periodDate().endDate]));
                self.lengthEmployeeSelected(nts.uk.resource.getText("KSC001_47",
                    [self.selectedEmployeeCode().length]));
            }
            /**
             * function createPersonalSchedule to client by check month max
             */
            private createByCheckMaxMonth(): void {
                var self = this;
                _.defer(() => {
                    nts.uk.ui.block.invisible();
                    service.checkMonthMax(self.toDate(self.periodDate().startDate)).done(checkMax => {
                        nts.uk.ui.block.clear();
                        if (checkMax) {
                            nts.uk.ui.dialog.confirm({ messageId: 'Msg_568' }).ifYes(function() {
                                self.createPersonalSchedule();
                            }).ifNo(function() {
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
                var self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_569' }).ifYes(function() {
                    // C1_5 is check
                    if (self.selectedImplementAtrCode() == ImplementAtr.RECREATE) {
                        nts.uk.ui.dialog.confirm({ messageId: 'Msg_570' }).ifYes(function() {
                            self.savePersonalScheduleData();
                        }).ifNo(function() {
                            return;
                        });
                    }
                    else {
                        self.savePersonalScheduleData();
                    }
                }).ifNo(function() {
                    return;
                });

            }

            /**
             * save PersonalSchedule data
             */
            private savePersonalScheduleData(): void {
                var self = this;
                self.savePersonalSchedule(self.toPersonalScheduleData());
                service.addScheduleExecutionLog(self.collectionData()).done(function(data) {
                    nts.uk.ui.block.clear();
                    nts.uk.ui.windows.setShared('inputData', data);
                    nts.uk.ui.windows.sub.modal("/view/ksc/001/f/index.xhtml").onClosed(function() {
                    });
                });

            }


            /**
             * open dialog KDL023
             */
            private showDialogKDL023(): void {
                var self = this;
                var data: PersonalSchedule = new PersonalSchedule();
                self.findPersonalSchedule().done(function(dataInfo) {
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
                var self = this;
                var dto: ReflectionSetting = {
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
             * find employee id in selection employee code
             */
            public findEmployeeIdsByCode(employeeCodes: string[]): string[] {
                var self = this;
                var employeeIds: string[] = [];
                for (var employeeCode of employeeCodes) {
                    var employeeId = self.findEmployeeIdByCode(employeeCode);
                    if (employeeId && !(employeeId === '')) {
                        employeeIds.push(employeeId);
                    }
                }
                return employeeIds;
            }
            /**
             * collection data => command save
             */
            private collectionData(): ScheduleExecutionLogSaveDto {
                var self = this;
                var data: PersonalSchedule = self.toPersonalScheduleData();
                var dto: ScheduleExecutionLogSaveDto = {
                    periodStartDate: self.toDate(self.periodDate().startDate),
                    periodEndDate: self.toDate(self.periodDate().endDate),
                    implementAtr: data.implementAtr,
                    reCreateAtr: data.reCreateAtr,
                    processExecutionAtr: data.processExecutionAtr,
                    resetWorkingHours: data.resetWorkingHours,
                    resetDirectLineBounce: data.resetDirectLineBounce,
                    resetMasterInfo: data.resetMasterInfo,
                    resetTimeChildCare: data.resetTimeChildCare,
                    resetAbsentHolidayBusines: data.resetAbsentHolidayBusines,
                    resetTimeAssignment: data.resetTimeAssignment,
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
                var data: DayOffSetting = {
                    useClassification: use == UseAtr.USE,
                    workTypeCode: worktypeCode
                };
                return data;
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

        // 利用区分
        export enum UseAtr {
            // 使用しない
            NOTUSE = 0,

            // 使用する
            USE = 1
        }

        // 個人スケジュールの作成
        export class PersonalSchedule {
            // パターンコード
            patternCode: string;

            // パターン開始日
            patternStartDate: Date;

            // マスタ情報再設定
            resetMasterInfo: boolean;

            // 休日反映方法
            holidayReflect: ReflectionMethod;

            // 休職休業再設定
            resetAbsentHolidayBusines: boolean;

            // 作成方法区分
            createMethodAtr: CreateMethodAtr

            // 作成時に確定済みにする
            confirm: boolean;

            // 再作成区分
            reCreateAtr: ReCreateAtr;

            // 処理実行区分
            processExecutionAtr: ProcessExecutionAtr;

            //実施区分
            implementAtr: ImplementAtr;

            // 就業時間帯再設定
            resetWorkingHours: boolean;

            // 法内休日利用区分
            legalHolidayUseAtr: UseAtr

            // 法内休日勤務種類
            legalHolidayWorkType: string;

            // 法外休日利用区分
            statutoryHolidayUseAtr: UseAtr;

            //法外休日勤務種類
            statutoryHolidayWorkType: string

            // 申し送り時間再設定
            resetTimeAssignment: boolean;

            // 直行直帰再設定
            resetDirectLineBounce: boolean;

            // 社員ID
            employeeId: string;

            // 祝日利用区分
            holidayUseAtr: UseAtr;

            // 祝日勤務種類
            holidayWorkType: string;

            // 育児介護時間再設定
            resetTimeChildCare: boolean;

            constructor() {
                var self = this;
                self.patternCode = '02';
                self.patternStartDate = new Date();
                self.resetMasterInfo = false;
                self.holidayReflect = ReflectionMethod.Overwrite;
                self.resetAbsentHolidayBusines = false;
                self.createMethodAtr = CreateMethodAtr.PERSONAL_INFO;
                self.confirm = false;
                self.reCreateAtr = ReCreateAtr.ALLCASE;
                self.processExecutionAtr = ProcessExecutionAtr.REBUILD;
                self.implementAtr = ImplementAtr.GENERALLY_CREATED;
                self.resetWorkingHours = false;
                self.legalHolidayUseAtr = UseAtr.NOTUSE;
                self.legalHolidayWorkType = '';
                self.statutoryHolidayUseAtr = UseAtr.NOTUSE;
                self.statutoryHolidayWorkType = '';
                self.resetTimeAssignment = false;
                self.resetDirectLineBounce = false;
                self.employeeId = '';
                self.holidayUseAtr = UseAtr.NOTUSE;
                self.holidayWorkType = '';
                self.resetTimeChildCare = false;
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