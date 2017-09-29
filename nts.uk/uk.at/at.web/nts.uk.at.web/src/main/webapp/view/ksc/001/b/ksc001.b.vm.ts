module nts.uk.at.view.ksc001.b {

    import NtsWizardStep = service.model.NtsWizardStep;
    import PeriodDto = service.model.PeriodDto;
    import UserInfoDto = service.model.UserInfoDto;

    export module viewmodel {
        export class ScreenModel {
            stepList: Array<NtsWizardStep>;
            stepSelected: KnockoutObservable<NtsWizardStep>;
            ccgcomponent: GroupOption;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;

            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

            selectSchedules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            checkAllCase: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            periodStartDate: KnockoutObservable<Date>;
            periodEndDate: KnockoutObservable<Date>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;

            // Employee tab
            lstPersonComponentOption: any;
            selectedEmployeeCode: KnockoutObservableArray<string>;
            employeeName: KnockoutObservable<string>;
            employeeList: KnockoutObservableArray<UnitModel>;
            alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;
            ccgcomponentPerson: GroupOption;
            constructor() {
                var self = this;
                self.enable = ko.observable(true);
                self.required = ko.observable(true);

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
                self.showinfoSelectedEmployee = ko.observable(false);
                self.baseDate = ko.observable(new Date());
                self.periodStartDate = ko.observable(new Date());
                self.periodEndDate = ko.observable(new Date());
                self.checkAllCase = ko.observable(true);
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
                        self.showinfoSelectedEmployee(true);
                        self.applyKCP005ContentSearch(dataEmployee);
                    }

                }
                self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });


                self.selectSchedules = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("KSC001_74") },
                    { code: '2', name: nts.uk.resource.getText("KSC001_75") }
                ]);
                self.selectedRuleCode = ko.observable(1);

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
            private savePersonalSchedule(employeeId: string, data: PersonalSchedule): void{
                nts.uk.characteristics.save("PersonalSchedule_"+employeeId, data);
            }
            
            /**
             * find by client service PersonalSchedule
            */
            private findPersonalScheduleByEmployeeId(employeeId: string):  JQueryPromise<PersonalSchedule>{
                return nts.uk.characteristics.restore("PersonalSchedule_"+employeeId);
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
                    self.next();
                }
                else {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_206' });
                }
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
                nts.uk.ui.windows.sub.modal("/view/ksc/001/f/index.xhtml").onClosed(function() {
                });
            }

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