/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.common {

    import getMessage = nts.uk.resource.getMessage;
    import parseTime = nts.uk.time.parseTime;

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

    export function showErrors(empErrors: Array<ErrorResultDto>) {
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
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "2", parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.THREE_MONTH_MAX_TIME:
                        messageId = "Msg_1915";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "3", parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.FOUR_MONTH_MAX_TIME:
                        messageId = "Msg_1915";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "4", parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.FIVE_MONTH_MAX_TIME:
                        messageId = "Msg_1915";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "5", parseTime(error.maximumTimeMonth, true).format()]);
                        break;
                    case ErrorClassificationEnum.SIX_MONTH_MAX_TIME:
                        messageId = "Msg_1915";
                        message = getMessage(messageId, [empError.employeeCode, empError.employeeName, "6", parseTime(error.maximumTimeMonth, true).format()]);
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

        nts.uk.ui.dialog.bundledErrors({ errors: errorItems });
    }
}
