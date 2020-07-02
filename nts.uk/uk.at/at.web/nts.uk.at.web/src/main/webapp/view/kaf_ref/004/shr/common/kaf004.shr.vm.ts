module nts.uk.at.view.kaf004_ref.shr.common.viewmodel {
    export class WorkManagement {
        // 予定出勤時刻ラベル
        // A6_3
        scheAttendanceTime: KnockoutObservable<String>;

        // 予定退勤時刻ラベル
        // A6_9
        scheWorkTime: KnockoutObservable<String>;

        // 予定出勤時刻２ラベル
        // A6_15
        scheAttendanceTime2: KnockoutObservable<String>;

        // 予定退勤時刻２ラベル
        // A6_21
        scheWorkTime2: KnockoutObservable<String>;

        // 出勤時刻
        // A6_4
        workTime: KnockoutObservable<Number>;

        // 退勤時刻
        // A6_10
        leaveTime: KnockoutObservable<Number>;

        // 出勤時刻２
        // A6_16
        workTime2: KnockoutObservable<Number>;

        // 退勤時刻２
        // A6_22
        leaveTime2: KnockoutObservable<Number>;

        constructor(scheAttendanceTime: string, scheWorkTime: string, scheAttendanceTime2: string, scheWorkTime2: string,
            workTime: number, leaveTime: number, workTime2: number, leaveTime2: number) {
            const self = this;

            self.scheAttendanceTime = ko.observable(scheAttendanceTime);
            self.scheWorkTime = ko.observable(scheWorkTime);
            self.scheAttendanceTime2 = ko.observable(scheAttendanceTime2);
            self.scheWorkTime2 = ko.observable(scheWorkTime2);

            self.workTime = ko.observable(workTime);
            self.leaveTime = ko.observable(leaveTime);
            self.workTime2 = ko.observable(workTime2);
            self.leaveTime2 = ko.observable(leaveTime2);
        }

        clearData() {
            const self = this;

            self.workTime(null);
            self.leaveTime(null);
            self.workTime2(null);
            self.leaveTime2(null);
        }
    }

    export class LateOrEarlyInfo {
        isCheck: KnockoutObservable<Boolean>;
        workNo: KnockoutObservable<Number>;
        isActive: KnockoutObservable<Boolean>;
        isIndicate: KnockoutObservable<Boolean>;
        category: KnockoutObservable<Number>;

        constructor(isCheck: boolean, workNo: number, isActive: boolean, isIndicate: boolean, category: number) {
            const self = this;

            self.isCheck = ko.observable(isCheck);
            self.workNo = ko.observable(workNo);
            self.isActive = ko.observable(isActive);
            self.isIndicate = ko.observable(isIndicate);
            self.category = ko.observable(category);
        }
    }

    export class ArrivedLateLeaveEarlyInfo {
        public static initArrivedLateLeaveEarlyInfo() {
            // 遅刻早退取消申請起動時の表示情報
            return {
                // 取り消す初期情報
                earlyInfos: [],

                // 申請表示情報
                appDispInfoStartupOutput: {
                    // 申請設定（基準日関係なし）
                    appDispInfoNoDateOutput: {
                        employeeInfoLst: [],
                        requestSetting: {},
                        appReasonLst: []
                    },
                    // 申請設定（基準日関係あり）
                    appDispInfoWithDateOutput: {
                        approvalFunctionSet: {},
                        employmentSet: {},
                        workTimeLst: [],
                        listApprovalPhaseState: [],
                        errorFlag: 0,
                        prePostAtr: 1,
                        baseDate: "",
                        achievementOutputLst: [],
                        appDetailContentLst: [],
                        empHistImport: {}
                    },
                    // 申請詳細画面情報
                    appDetailScreenInfo: {
                        application: {},
                        approvalLst: [],
                        authorComment: "",

                        user: 0,
                        reflectPlanState: 0,
                        outputMode: 0,
                        authorizableFlags: false,
                        approvalATR: 0,
                        alternateExpiration: false
                    }
                },

                // 遅刻早退取消申請設定
                arrivedLateLeaveEralySetting: {
                    isResult: true,
                    id: '',
                    cancelCategory: 0
                },

                // エアー情報
                info: '',

                // 遅刻早退取消申請
                arrivedLateLeaveEarly: {
                    // 取消
                    lateCancelation: [],
                    // 時刻報告
                    lateOrLeaveEarlies: []
                }
            }
        }
    }
}