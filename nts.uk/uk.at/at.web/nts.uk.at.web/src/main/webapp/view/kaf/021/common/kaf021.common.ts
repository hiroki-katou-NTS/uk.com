/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.common {

    import getMessage = nts.uk.resource.getMessage;
    import parseTime = nts.uk.time.parseTime;

    export interface AgreementOperationSettingDto {
        startingMonth: number;
        useSpecical: boolean;
        useYear: boolean;
    }

    export class CellState {
        rowId: string;
        columnKey: string;
        state: Array<any>
        constructor(rowId: string, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }

    export enum AppTypeEnum {
        CURRENT_MONTH = 1,
        NEXT_MONTH = 2,
        YEARLY = 3
    }

    export enum ApprovalStatusEnum {
        UNAPPROVED = 0,
        APPROVED = 1,
        DENY = 2
    }

    export enum ConfirmationStatusEnum {
        UNCONFIRMED = 0,
        CONFIRMED = 1,
        DENY = 2
    }

    /**
     * ３６協定申請種類
     */
    export enum TypeAgreementApplicationEnum {
        ONE_MONTH = 0,
        ONE_YEAR = 1
    }

    /**
     * 月別実績の36協定時間状態
     */
    export enum AgreementTimeStatusOfMonthly {
        /** 正常 */
        NORMAL = 0,
        /** 限度エラー時間超過 */
        EXCESS_LIMIT_ERROR = 1,
        /** 限度アラーム時間超過 */
        EXCESS_LIMIT_ALARM = 2,
        /** 特例限度エラー時間超過 */
        EXCESS_EXCEPTION_LIMIT_ERROR = 3,
        /** 特例限度アラーム時間超過 */
        EXCESS_EXCEPTION_LIMIT_ALARM = 4,
        /** 正常（特例あり） */
        NORMAL_SPECIAL = 5,
        /** 限度エラー時間超過（特例あり） */
        EXCESS_LIMIT_ERROR_SP = 6,
        /** 限度アラーム時間超過（特例あり） */
        EXCESS_LIMIT_ALARM_SP = 7,
        /** tính Tổng hiệp định 36） */
        EXCESS_BG_GRAY = 8
    }

    export enum AgreTimeYearStatusOfMonthly {
        /** 正常 */
        NORMAL = 0,
        /** 限度超過 */
        EXCESS_LIMIT = 1
    }

    /**
     * 月別実績の36協定上限時間状態
     */
    export enum AgreMaxTimeStatusOfMonthly {
        /** 正常 */
        NORMAL = 0,
        /** 上限時間超過 */
        EXCESS_MAXTIME = 1
    }

    export interface ErrorResultDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        errors: Array<ExcessErrorContentDto>
    }

    export interface ExcessErrorContentDto {
        errorClassification: ErrorClassificationEnum;
        maximumTimeMonth: number;
        maximumTimeYear: number;
        exceedUpperLimit: number;
    }

    export enum ErrorClassificationEnum {
        APPROVER_NOT_SET = 0,
        ONE_MONTH_MAX_TIME = 1,
        TWO_MONTH_MAX_TIME = 2,
        THREE_MONTH_MAX_TIME = 3,
        FOUR_MONTH_MAX_TIME = 4,
        FIVE_MONTH_MAX_TIME = 5,
        SIX_MONTH_MAX_TIME = 6,
        OVERTIME_LIMIT_ONE_YEAR = 7,
        EXCEEDING_MAXIMUM_NUMBER = 8
    }

    export function generateErrors(empErrors: Array<ErrorResultDto>) {
        let errorItems: Array<any> = [];
        _.forEach(empErrors, (empError: ErrorResultDto) => {
            _.forEach(empError.errors, (error: ExcessErrorContentDto) => {
                let message = "";
                let messageId = "";
                switch (error.errorClassification) {
                    case ErrorClassificationEnum.APPROVER_NOT_SET:
                        messageId = "Msg_324";
                        message = getMessage(messageId);
                        break;
                    case ErrorClassificationEnum.ONE_MONTH_MAX_TIME:
                        messageId = "Msg_1888";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.TWO_MONTH_MAX_TIME:
                        messageId = "Msg_1915";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "２", parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.THREE_MONTH_MAX_TIME:
                        messageId = "Msg_1915";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "３", parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.FOUR_MONTH_MAX_TIME:
                        messageId = "Msg_1915";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "４", parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.FIVE_MONTH_MAX_TIME:
                        messageId = "Msg_1915";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "５", parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.SIX_MONTH_MAX_TIME:
                        messageId = "Msg_1915";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "６", parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.OVERTIME_LIMIT_ONE_YEAR:
                        messageId = "Msg_1889";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, parseTime(error.maximumTimeYear, true).format()]);
                        break;
                    case ErrorClassificationEnum.EXCEEDING_MAXIMUM_NUMBER:
                        messageId = "Msg_1916";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, error.exceedUpperLimit == null ? "" : error.exceedUpperLimit.toString()]);
                        break;
                }
                errorItems.push({
                    message: message,
                    messageId: messageId,
                    supplements: {}
                })
            })

        });

        return errorItems;
    }

    export interface SpecialProvisionOfAgreementAppListDto {
        /**
         * 締め終了日
         */
        startDate: string;
        /**
         * 締め開始日
         */
        endDate: string;
        /**
         * 申請一覧
         */
        applications: Array<IApplicationListDto>;
        setting: AgreementOperationSettingDto;
    }

    export interface IApplicationListDto {
        /**
         * 申請ID
         */
        applicantId: string;
        /**
         * 職場
         */
        workplaceName: string;
        /**
         * 個人: code
         */
        employeeCode: string;
        /**
         * 個人: name
         */
        employeeName: string;
        /**
         * 申請時間
         */
        applicationTime: IApplicationTimeDto;
        /**
         * 画面表示情報
         */
        screenDisplayInfo: IScreenDisplayInfoDto;
        /**
         * 申請理由
         */
        reason: string;
        /**
         * コメント
         */
        comment: string;
        /**
         * 申請者
         */
        applicant: string;
        /**
         * 入力日付
         */
        inputDate: string;
        /**
         * 承認者
         */
        approver: string;
        /**
         * 承認状況
         */
        approvalStatus: ApprovalStatusEnum;
        /**
         * 従業員代表
         */
        confirmer: string;
        /**
         * 確認状況
         */
        confirmStatus: ConfirmationStatusEnum;
    }

    export interface IApplicationTimeDto {
        /**
         * ３６協定申請種類
         */
        typeAgreement: TypeAgreementApplicationEnum;
        /**
         * 1ヶ月時間
         */
        oneMonthTime: IOneMonthTimeDto;
        /**
         * 年間時間
         */
        oneYearTime: IOneYearTimeDto;
    }

    export interface IOneMonthTimeDto {
        /**
         * 1ヶ月時間
         */
        errorTime: IErrorAlarmTimeDto;
        /**
         * 年月度
         */
        yearMonth: number;
    }
    export interface IOneYearTimeDto {
        /**
         * 1年間時間
         */
        errorTime: IErrorAlarmTimeDto;
        /**
         * 年度
         */
        year: number;
    }

    export interface IErrorAlarmTimeDto {
        /**
         * エラー時間
         */
        error: number;
        /**
         * アラーム時間
         */
        alarm: number;
    }

    export interface IScreenDisplayInfoDto {
        /**
         * 時間外時間
         */
        overtime: IOvertimeDto;
        /**
         * 時間外時間（法定休出を含む）
         */
        overtimeIncludingHoliday: IOvertimeIncludingHolidayDto;
        /**
         * 超過月数
         */
        exceededMonth: number;
        /**
         * 上限マスタ内容
         */
        upperContents: IUpperLimitBeforeRaisingDto;
    }

    export interface IOvertimeDto {
        /**
         * 対象月度の時間外時間
         */
        overtimeHoursOfMonth: number;
        /**
         * 対象年度の時間外時間
         */
        overtimeHoursOfYear: number;
    }

    export interface IOvertimeIncludingHolidayDto {
        /**
         * 対象月度の時間外時間
         */
        overtimeHoursTargetMonth: number;
        /**
        * 2ヶ月平均の時間外時間
        */
        monthAverage2Str: number;
        /**
         * 3ヶ月平均の時間外時間
         */
        monthAverage3Str: number;
        /**
         * 4ヶ月平均の時間外時間
         */
        monthAverage4Str: number;
        /**
         * 5ヶ月平均の時間外時間
         */
        monthAverage5Str: number;
        /**
         * 6ヶ月平均の時間外時間
         */
        monthAverage6Str: number;
    }

    export interface IUpperLimitBeforeRaisingDto {
        /**
         * 1ヶ月の上限
         */
        oneMonthLimit: IErrorAlarmTimeDto;
        /**
         * 1年間の上限
         */
        oneYearLimit: IErrorAlarmTimeDto;
        /**
         * 平均限度時間
         */
        averageTimeLimit: number;
    }

}
