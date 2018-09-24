module kaf001.a.viewmodel {
    import block  = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import jump   = nts.uk.request.jump;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        //_____CCG001________
        ccg001ComponentOption : GroupOption;
        selectedEmployee      : KnockoutObservableArray<EmployeeSearchDto>      = ko.observableArray([]);;
        //_____KCP005________
        kcp005ComponentOption: any;
        selectedEmployeeCode : KnockoutObservableArray<string>                  = ko.observableArray([]);
        alreadySettingList   : KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        employeeList         : KnockoutObservableArray<UnitModel>               = ko.observableArray([]);
        isVisiableAppWindow  : KnockoutObservable<boolean>                      = ko.observable(false);
        isVisiableOverTimeApp         : KnockoutObservable<boolean>             = ko.observable(false);
        isVisiableAbsenceApp          : KnockoutObservable<boolean>             = ko.observable(false);
        isVisiableWorkChangeApp       : KnockoutObservable<boolean>             = ko.observable(false);
        isVisiableBusinessTripApp     : KnockoutObservable<boolean>             = ko.observable(false);
        isVisiableGoReturnDirectlyApp : KnockoutObservable<boolean>             = ko.observable(false);
        isVisiableBreakTimeApp        : KnockoutObservable<boolean>             = ko.observable(false);
        isVisiableAnnualHolidayApp    : KnockoutObservable<boolean>             = ko.observable(false);
        isVisiableEarlyLeaveCanceApp  : KnockoutObservable<boolean>             = ko.observable(false);
        isVisiableComplementLeaveApp  : KnockoutObservable<boolean>             = ko.observable(false);
        isVisiableStampApp            : KnockoutObservable<boolean>             = ko.observable(false);
        //app Name
        appNameDis: KnockoutObservable<ObjNameDis> = ko.observable(null);
        constructor() {
            let self = this;

            //_____CCG001________
            self.ccg001ComponentOption = {
                /** Common properties */
                systemType: 2,
                showEmployeeSelection: false,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment().toISOString(),
                periodStartDate: moment().toISOString(),
                periodEndDate: moment().toISOString(),
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: false,
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: true,

                /**
                * Self-defined function: Return data from CCG001
                * @param: data: the data return from CCG001
                */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    self.applyKCP005ContentSearch(data.listEmployee);
                    self.selectedEmployee(data.listEmployee);
                }
            };

//            $('#ccg001component').ntsGroupComponent(self.ccg001ComponentOption);

//            self.applyKCP005ContentSearch([]);
//            $('#kcp005component').ntsListComponent(self.kcp005ComponentOption);
        }

        /**
         * Apply CCG001 search data to KCP005
         */
        applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.employeeList.removeAll();
            let employeeSearchs: Array<UnitModel> = [];
            for (let employeeSearch of dataList) {
                let employee: UnitModel = {
                    code: employeeSearch.employeeCode,
                    name: employeeSearch.employeeName,
                    workplaceName: employeeSearch.workplaceName
                };
                employeeSearchs.push(employee);
            }
            self.employeeList(employeeSearchs);
            self.kcp005ComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedEmployeeCode,
                isDialog: false,
                isShowNoSelectRow: false,
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: true,
                maxWidth: 450,
                maxRows: 20
            };
            self.start();
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            self.getAllProxyApplicationSetting();
            self.getNameDisplay();
            block.clear();
            dfd.resolve();
            return dfd.promise();
        }
        //hoatt
        getNameDisplay(){
            let self = this;
            service.getListNameDis().done(function(data){
                let obj: ObjNameDis = new ObjNameDis('','','','','','','','','',''); 
                _.each(data, function(app){
                    switch (app.appType) {
                        case 0: {
                            obj.overTime = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                        case 1: {
                            obj.absence = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                        case 2: {
                            obj.workChange = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                        case 3: {
                            obj.businessTrip = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                        case 4: {
                            obj.goBack = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                        case 6: {
                            obj.holiday = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                        case 8: {
                            obj.annualHd = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                        case 9: {
                            obj.earlyLeaveCancel = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                        case 10: {
                            obj.complt = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                        case 11: {
                            obj.stamp = getText('KAF001_12', [app.dispName]);
                            break;
                        }
                    }
                });
                self.appNameDis(obj);
            });
        }
        getAllProxyApplicationSetting() {
            let self = this;
            service.getAllProxyApplicationSetting().done((result) => {
                if(result){
                    _.each(result, x => {
                        switch (x.appType) {
                            case ApplicationType.OVER_TIME_APPLICATION: {
                                self.isVisiableOverTimeApp(true);
                                break;
                            }
                            case ApplicationType.ABSENCE_APPLICATION: {
                                self.isVisiableAbsenceApp(true);
                                break;
                            }
                            case ApplicationType.WORK_CHANGE_APPLICATION: {
                                self.isVisiableWorkChangeApp(true);
                                break;
                            }
                            case ApplicationType.BUSINESS_TRIP_APPLICATION: {
                                self.isVisiableBusinessTripApp(true);
                                break;
                            }
                            case ApplicationType.GO_RETURN_DIRECTLY_APPLICATION: {
                                self.isVisiableGoReturnDirectlyApp(true);
                                break;
                            }
                            case ApplicationType.BREAK_TIME_APPLICATION: {
                                self.isVisiableBreakTimeApp(true);
                                break;
                            }
                            case ApplicationType.ANNUAL_HOLIDAY_APPLICATION: {
                                self.isVisiableAnnualHolidayApp(true);
                                break;
                            }
                            case ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION: {
                                self.isVisiableEarlyLeaveCanceApp(true);
                                break;
                            }
                            case ApplicationType.COMPLEMENT_LEAVE_APPLICATION: {
                                self.isVisiableComplementLeaveApp(true);
                                break;
                            }
                            case ApplicationType.STAMP_APPLICATION: {
                                self.isVisiableStampApp(true);
                                break;
                            }
                        }
                    });
                }
            });
        }

        /**
         * 申請種類選択
         */
        selectApplicationByType(applicationType: number) {
            block.invisible();
            let self = this;
            let employeeIds : Array<string> = [];
//            _.each(self.selectedEmployeeCode(), x => {
//                let employee = _.find(self.selectedEmployee(), x1 => { return x1.employeeCode === x });
//                if (employee) {
//                    employeeIds.push(employee.employeeId);
//                }
//            })
            //Chuyen multi -> single
            let employee = _.find(self.selectedEmployee(), x1 => { return x1.employeeCode === self.selectedEmployeeCode()});
            if (employee) {
                employeeIds.push(employee.employeeId);
            }
            if (employeeIds.length > 0) {
                let paramFind = {
                    employeeIds: employeeIds,
                    applicationType: applicationType
                }
                service.selectApplicationByType(paramFind).done(() => {
                    let transfer = {
                       uiType: 0,
                       employeeIDs: employeeIds
                    };
                    switch (applicationType) {
                        case ApplicationType.OVER_TIME_APPLICATION: {
                            //open dialog B
                            jump("at", "/view/kaf/001/b/index.xhtml", { employeeIds: employeeIds});
                            break;
                        }
                        case ApplicationType.ABSENCE_APPLICATION: {
                            jump("at", "/view/kaf/006/a/index.xhtml", transfer);
                            break;
                        }
                        case ApplicationType.WORK_CHANGE_APPLICATION: {
                            jump("at", "/view/kaf/007/a/index.xhtml", { employeeIds: employeeIds });
                            break;
                        }
                        case ApplicationType.BUSINESS_TRIP_APPLICATION: {
                            jump("at", "/view/kaf/008/a/index.xhtml", { employeeIds: employeeIds });
                            break;
                        }
                        case ApplicationType.GO_RETURN_DIRECTLY_APPLICATION: {
                            jump("at", "/view/kaf/009/a/index.xhtml", { employeeIds: employeeIds });
                            break;
                        }
                        case ApplicationType.BREAK_TIME_APPLICATION: {
                            jump("at", "/view/kaf/010/a/index.xhtml", transfer);
                            break;
                        }
                        case ApplicationType.ANNUAL_HOLIDAY_APPLICATION: {
                            jump("at", "/view/kaf/012/a/index.xhtml", { employeeIds: employeeIds });
                            break;
                        }
                        case ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION: {
                            jump("at", "/view/kaf/004/a/index.xhtml", { employeeIds: employeeIds });
                            break;
                        }
                        case ApplicationType.COMPLEMENT_LEAVE_APPLICATION: {
                            jump("at", "/view/kaf/011/a/index.xhtml", { employeeIds: employeeIds });
                            break;
                        }
                        case ApplicationType.STAMP_APPLICATION: {
                            jump("at", "/view/kaf/002/a/index.xhtml", { employeeIds: employeeIds });
                            break;
                        }
                    }
                }).fail(error => {
                    dialog.alertError(error);
                }).always(()=>{
                    block.clear();
                });
            }
            block.clear();
        }
    }

    export enum ApplicationType {
        OVER_TIME_APPLICATION                   = 0,    /**残業申請*/
        ABSENCE_APPLICATION                     = 1,    /**休暇申請*/
        WORK_CHANGE_APPLICATION                 = 2,    /**勤務変更申請*/
        BUSINESS_TRIP_APPLICATION               = 3,    /**出張申請*/
        GO_RETURN_DIRECTLY_APPLICATION          = 4,    /**直行直帰申請*/
        BREAK_TIME_APPLICATION                  = 6,    /**休出時間申請*/
        STAMP_APPLICATION                       = 7,    /**打刻申請*/
        ANNUAL_HOLIDAY_APPLICATION              = 8,    /**時間年休申請*/
        EARLY_LEAVE_CANCEL_APPLICATION          = 9,    /**遅刻早退取消申請*/
        COMPLEMENT_LEAVE_APPLICATION            = 10,   /**振休振出申請*/
        STAMP_NR_APPLICATION                    = 11,   /**打刻申請（NR形式）*/
        LONG_BUSINESS_TRIP_APPLICATION          = 12,   /**連続出張申請*/
        BUSINESS_TRIP_APPLICATION_OFFICE_HELPER = 13,   /**出張申請オフィスヘルパー*/
        APPLICATION_36                          = 14,   /**３６協定時間申請*/
    }

    //Interfaces
    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection: boolean; // 検索タイプ
        systemType: number; // システム区分
        showQuickSearchTab: boolean; // クイック検索
        showAdvancedSearchTab: boolean; // 詳細検索
        showBaseDate: boolean; // 基準日利用
        showClosure: boolean; // 就業締め日利用
        showAllClosure: boolean; // 全締め表示
        showPeriod: boolean; // 対象期間利用
        periodFormatYM: boolean; // 対象期間精度
        isInDialog?: boolean;

        /** Required parameter */
        baseDate?: string; // 基準日
        periodStartDate?: string; // 対象期間開始日
        periodEndDate?: string; // 対象期間終了日
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分

        /** Quick search tab options */
        showAllReferableEmployee: boolean; // 参照可能な社員すべて
        showOnlyMe: boolean; // 自分だけ
        showSameWorkplace: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild: boolean; // 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment: boolean; // 雇用条件
        showWorkplace: boolean; // 職場条件
        showClassification: boolean; // 分類条件
        showJobTitle: boolean; // 職位条件
        showWorktype: boolean; // 勤種条件
        isMutipleCheck: boolean; // 選択モード

        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }

    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceName: string;
    }

    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    export class ListType {
        static EMPLOYMENT     = 1;
        static CLASSIFICATION = 2;
        static JOB_TITLE      = 3;
        static EMPLOYEE       = 4;
    }

    export interface UnitModel {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL              = 2;
        static SELECT_FIRST_ITEM       = 3;
        static NO_SELECT               = 4;
    }

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }
    export class ObjNameDis{
        overTime: string;//A2_2
        absence: string;//A2_3
        workChange: string;//A2_4
        businessTrip: string;//A2_5
        goBack: string;//A2_6
        holiday: string;//A2_7
        annualHd: string;//A2_8
        earlyLeaveCancel: string;//A2_9
        complt: string;//A2_10
        stamp: string;//A2_11
        constructor(overTime: string,absence: string,workChange: string,
            businessTrip: string, goBack: string, holiday: string, 
            annualHd: string, earlyLeaveCancel: string,complt: string,stamp: string){
            this.overTime = overTime;
            this.absence = absence;
            this.workChange = workChange;
            this.businessTrip = businessTrip;
            this.goBack = goBack;
            this.holiday = holiday;
            this.annualHd =  annualHd;
            this.earlyLeaveCancel = earlyLeaveCancel;
            this.complt = complt;
            this.stamp = stamp;
        }
    }
}