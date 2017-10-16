module nts.uk.at.view.ksc001.b {

    import NtsWizardStep = service.model.NtsWizardStep;
    import PeriodDto = service.model.PeriodDto;
    import UserInfoDto = service.model.UserInfoDto;
    import ScheduleExecutionLogSaveDto = service.model.ScheduleExecutionLogSaveDto;
    import ScheduleExecutionLogSaveRespone = service.model.ScheduleExecutionLogSaveRespone;

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
            checkCreateMethodAtrPersonalInfo: KnockoutObservable<boolean>;
            checkCreateMethodAtrPatternSchedule: KnockoutObservable<boolean>;
            checkCreateMethodAtrCopyPastSchedule: KnockoutObservable<boolean>;
            resetWorkingHours: KnockoutObservable<boolean>;
            resetDirectLineBounce: KnockoutObservable<boolean>;
            resetMasterInfo: KnockoutObservable<boolean>;
            resetTimeChildCare: KnockoutObservable<boolean>;
            resetAbsentHolidayBusines: KnockoutObservable<boolean>;
            resetTimeAssignment: KnockoutObservable<boolean>;
            confirm: KnockoutObservable<boolean>;
            periodStartDate: KnockoutObservable<Date>;
            periodEndDate: KnockoutObservable<Date>;
            copyStartDate: KnockoutObservable<Date>;
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
                self.periodStartDate = ko.observable(new Date());
                self.periodEndDate = ko.observable(new Date());
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
                self.checkCreateMethodAtrPersonalInfo = ko.observable(true);
                self.checkCreateMethodAtrPatternSchedule = ko.observable(false);
                self.checkCreateMethodAtrCopyPastSchedule = ko.observable(false);
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
                lstRadioBoxModelImplementAtr.push(new RadioBoxModel(ImplementAtr.GENERALLY_CREATED, nts.uk.resource.getText("KSC001_74")));
                lstRadioBoxModelImplementAtr.push(new RadioBoxModel(ImplementAtr.RECREATE, nts.uk.resource.getText("KSC001_75")));
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
                
                // update CreateMethodAtr
                self.checkCreateMethodAtrPersonalInfo.subscribe(function(check: boolean){
                   if(check){
                      self.checkCreateMethodAtrPatternSchedule(!check);      
                      self.checkCreateMethodAtrCopyPastSchedule(!check);      
                   } 
                });
                self.checkCreateMethodAtrPatternSchedule.subscribe(function(check: boolean){
                   if(check){
                      self.checkCreateMethodAtrPersonalInfo(!check);      
                      self.checkCreateMethodAtrCopyPastSchedule(!check);      
                   } 
                });
                self.checkCreateMethodAtrCopyPastSchedule.subscribe(function(check: boolean){
                   if(check){
                      self.checkCreateMethodAtrPersonalInfo(!check);      
                      self.checkCreateMethodAtrPatternSchedule(!check);      
                   } 
                });
                self.lstLabelInfomation = ko.observableArray([]);
                self.infoCreateMethod = ko.observable('');
                self.infoPeriodDate = ko.observable('');
                self.lengthEmployeeSelected = ko.observable('');
            }
            /**
             * get user login
             */
            public getUserLogin(): UserInfoDto {
                var userinfo: UserInfoDto = { companyId: '000000000000-0001', employeeId: '000426a2-181b-4c7f-abc8-6fff9f4f983a' };
                return userinfo;

            }
            /**
             * save to client service PersonalSchedule
            */
            private savePersonalSchedule(employeeId: string, data: PersonalSchedule): void {
                nts.uk.characteristics.save("PersonalSchedule_" + employeeId, data);
            }

            /**
             * find by client service PersonalSchedule
            */
            private findPersonalScheduleByEmployeeId(employeeId: string): JQueryPromise<PersonalSchedule> {
                return nts.uk.characteristics.restore("PersonalSchedule_" + employeeId);
            }
            /**
             * function next wizard by on click button 
             */
            private next(): void {
                $('#wizard').ntsWizard("next");
            }
            /**
             * function previous wizard by on click button 
             */
            private previous(): void {
                $('#wizard').ntsWizard("prev");
            }
            /**
           * start page data 
           */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.findPeriodById(1).done(function(data) {
                    self.periodStartDate(data.startDate);
                    self.periodEndDate(data.endDate);
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
                self.employeeList(employeeSearchs);

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
                    maxRows: 15
                };

            }
            /**
             * function next page by selection employee goto page (C)
             */
            private nextPageEmployee(): void {
                var self = this;
                // check selection employee 
                if (self.selectedEmployeeCode && self.selectedEmployee() && self.selectedEmployeeCode().length > 0) {
                    var user: UserInfoDto = self.getUserLogin();
                    self.findPersonalScheduleByEmployeeId(user.employeeId).done(function(data){
                        self.updatePersonalScheduleData(data);
                        self.next();
                    }).fail(function(error){
                        console.log(error);   
                    });
                }
                else {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_206' });
                }
            }
            
            /**
             * update PersonalSchedule by find by employee id login
             */
            private updatePersonalScheduleData(data: PersonalSchedule): void {
                var self = this;
                if(data){
                        
                }
            }
            /**
             * convert ui to PersonalSchedule
             */
            private toPersonalScheduleData(employeeId: string): PersonalSchedule{
                var self = this;
                var data : PersonalSchedule = new PersonalSchedule();
                data.resetMasterInfo = self.resetMasterInfo();
                data.resetAbsentHolidayBusines = self.resetAbsentHolidayBusines();
                
                // set CreateMethodAtr
                if (self.checkCreateMethodAtrPersonalInfo()) {
                    data.createMethodAtr = CreateMethodAtr.PERSONAL_INFO;
                }
                if (self.checkCreateMethodAtrPatternSchedule()) {
                    data.createMethodAtr = CreateMethodAtr.PATTERN_SCHEDULE;
                }
                if (self.checkCreateMethodAtrCopyPastSchedule()) {
                    data.createMethodAtr = CreateMethodAtr.COPY_PAST_SCHEDULE;
                }
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
                
                data.employeeId = employeeId;
                
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
             * function next page by selection employee goto page (D)
             */
            private nextPageC(): void {
                var self = this;
                self.next();
            }
            /**
             * function previous page by selection employee goto page (D)
             */
            private previousPageD(): void {
                var self = this;
                self.previous();
            }
            /**
             * function next page by selection employee goto page (E)
             */
            private nextPageD(): void {
                var self = this;
                var lstLabelInfomation: string[] = [];
                if (self.selectedImplementAtrCode() == ImplementAtr.GENERALLY_CREATED) {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_35"));
                } else {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_36"));
                }
                self.lstLabelInfomation(lstLabelInfomation);
                if (self.checkReCreateAtrAllCase()) {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_37")+nts.uk.resource.getText("KSC001_4"));
                }
                if (self.checkReCreateAtrOnlyUnConfirm()) {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_37")+nts.uk.resource.getText("KSC001_5"));
                }
                if (self.checkProcessExecutionAtrRebuild()) {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_37")+nts.uk.resource.getText("KSC001_7"));
                }
                if (self.checkProcessExecutionAtrReconfig()) {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_37")+nts.uk.resource.getText("KSC001_8"));
                }
                if (self.resetWorkingHours()) {
                    lstLabelInfomation.push(" "+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_15"));
                }
                if (self.resetDirectLineBounce()) {
                    lstLabelInfomation.push(" "+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_11"));
                }
                if (self.resetMasterInfo()) {
                    lstLabelInfomation.push(" "+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_12"));
                }
                if (self.resetTimeChildCare()) {
                    lstLabelInfomation.push(" "+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_13"));
                }
                if (self.resetAbsentHolidayBusines()) {
                    lstLabelInfomation.push(" "+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_14"));
                }
                if (self.resetTimeAssignment()) {
                    lstLabelInfomation.push(" "+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_16"));
                }
                if (self.confirm()) {
                    lstLabelInfomation.push(nts.uk.resource.getText("KSC001_17"));
                }
                self.lstLabelInfomation(lstLabelInfomation);
                if(self.checkCreateMethodAtrPersonalInfo()){
                    self.infoCreateMethod(nts.uk.resource.getText("KSC001_22"));    
                }
                if(self.checkCreateMethodAtrPatternSchedule()){
                    self.infoCreateMethod(nts.uk.resource.getText("KSC001_23"));    
                }
                if(self.checkCreateMethodAtrCopyPastSchedule()){
                    self.infoCreateMethod(nts.uk.resource.getText("KSC001_39",[moment(self.copyStartDate()).format('YYYY/MM/DD')]));    
                }
                self.infoPeriodDate(nts.uk.resource.getText("KSC001_46",[moment(self.periodStartDate()).format('YYYY/MM/DD'),(moment(self.periodEndDate()).format('YYYY/MM/DD'))]));
                self.lengthEmployeeSelected(nts.uk.resource.getText("KSC001_47",[self.selectedEmployeeCode().length]));
                self.next();
            }
            /**
             * function previous page by selection employee goto page (E)
             */
            private previousPageE(): void {
                var self = this;
                self.previous();
            }
            /**
             * finish next page by selection employee goto page (F)
             */
            private finish(): void {
                var self = this;
                console.log(self.periodStartDate());
                service.checkThreeMonth(self.periodStartDate()).done(function(check) {
                    if (check) {
                        // show message confirm 567
                        nts.uk.ui.dialog.confirm({ messageId: 'Msg_567' }).ifYes(function() {
                            service.checkMonthMax(self.periodStartDate()).done(function(checkMax) {
                                self.createByCheckMaxMonth();
                            });
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
             * function createPersonalSchedule to client by check month max
             */
            private createByCheckMaxMonth(): void {
                var self = this;
                service.checkMonthMax(self.periodStartDate()).done(function(checkMax) {
                    // check max
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
                var user: UserInfoDto = self.getUserLogin();
                self.savePersonalSchedule(user.employeeId, self.toPersonalScheduleData(user.employeeId));
                service.addScheduleExecutionLog(self.collectionData()).done(function(data){
                    nts.uk.ui.windows.setShared('inputData', data);
                    console.log(data);
                    nts.uk.ui.windows.sub.modal("/view/ksc/001/f/index.xhtml").onClosed(function() {
                    });
                });
                
            }
            
            
            /**
             * open dialog KDL023
             */
            private showDialogKDL023(): void{
                var self = this;
                var data: PersonalSchedule = new PersonalSchedule();
                nts.uk.ui.windows.setShared('reflectionSetting', self.convertPersonalScheduleToReflectionSetting(data));
                nts.uk.ui.windows.sub.modal('/view/kdl/023/b/index.xhtml').onClosed(() => {
                    let dto = nts.uk.ui.windows.getShared('returnedData');
                    console.log(dto);
                });
            }
            /**
             * convert data personal schedule to refelctionSetting
             */
            private convertPersonalScheduleToReflectionSetting(data: PersonalSchedule): ReflectionSetting{
                var self = this;    
                var dto: ReflectionSetting = {
                    calendarStartDate: moment(self.periodStartDate()).format('YYYY-MM-DD'),
                    calendarEndDate: moment(self.periodEndDate()).format('YYYY-MM-DD'),
                    selectedPatternCd: data.patternCode,
                    patternStartDate: moment(data.patternStartDate).format('YYYY-MM-DD'),
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
                var employeeIds : string[] = [];
                for(var employeeCode of employeeCodes){
                    var employeeId = self.findEmployeeIdByCode(employeeCode);
                    if(employeeId && !(employeeId ==='')){
                        employeeIds.push(employeeId);
                    }    
                }
                return employeeIds;
            }
            /**
             * collection data => command save
             */
            private collectionData(): ScheduleExecutionLogSaveDto{
                var self = this;
                var data: PersonalSchedule = self.toPersonalScheduleData('');
                var dto: ScheduleExecutionLogSaveDto = {
                    periodStartDate: self.periodStartDate(),
                    periodEndDate: self.periodEndDate(),
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
                    copyStartDate: self.copyStartDate(),
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
        
        export interface DayOffSetting {
            useClassification: boolean;
            workTypeCode: string;
        }
        
        export interface ReflectionSetting {
            calendarStartDate?: string;
            calendarEndDate?: string;
            selectedPatternCd: string;
            patternStartDate: string; // 'YYYY-MM-DD'
            reflectionMethod: ReflectionMethod;
            statutorySetting: DayOffSetting;
            nonStatutorySetting: DayOffSetting;
            holidaySetting: DayOffSetting;
        }

        // 休日反映方法
        export enum ReflectionMethod {
            // 上書き反映
            OVERWRITE = 0,

            // 穴埋め反映
            FILLINTHEBLANK = 1
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
                self.patternCode = '11';
                self.patternStartDate = new Date();
                self.resetMasterInfo = false;
                self.holidayReflect = ReflectionMethod.OVERWRITE;
                self.resetAbsentHolidayBusines = false;
                self.createMethodAtr = CreateMethodAtr.PERSONAL_INFO;
                self.confirm = false;
                self.reCreateAtr = ReCreateAtr.ALLCASE;
                self.processExecutionAtr = ProcessExecutionAtr.REBUILD;
                self.implementAtr = ImplementAtr.GENERALLY_CREATED;
                self.resetWorkingHours =  false;
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