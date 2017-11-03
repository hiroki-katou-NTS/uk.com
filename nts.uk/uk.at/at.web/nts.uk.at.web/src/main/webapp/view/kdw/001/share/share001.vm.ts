module nts.uk.at.view.kdl001.share.viewmodel {
    export class ScreenModel {



    }

    export interface SpecificExe {
        specificExeID: string;
        sortBy: number;
        settingInfor: number;
        operationCaseName: string;
        executionContents: number;
        executionType: number;
        settingForReflect: boolean;
        alsoForciblyReflectEvenIfItIsConfirmed: boolean;
    }

    export interface SettingForDaily {
        creationType: number;
        materReconfiguration: boolean;
        closedHolidays: boolean;
        resetWorkingHour: boolean;
        refNumberFingerCheck: boolean;
        specDateClassReset: boolean;
        resetTimeForAssig: boolean;
        resetTimeForChildOrNurseCare: boolean;
        calClassReset: boolean;
    }

    export interface paraScreenB {
       
        /** ＜画面Bから受け取るパラメータ＞ */
        /** 日別作成(打刻反映)実施区分 */
        dailyCreation: boolean;
        /** 作成区分 */
        creationType: number;
        /** 再作成区分 */
        resetClass: number;
        /** 計算区分を再度設定 */
        calClassReset: boolean;
        /** マスタ情報を再度設定 */
        masterReconfiguration: boolean;
        /** 特定日区分を再度設定 */
        specDateClassReset: boolean;
        /** 育児・介護短時間再設定 */
        resetTimeForChildOrNurseCare: boolean;
        /** 打刻のみを再度反映 */
        refNumberFingerCheck: boolean;
        /** 休業再設定 */
        closedHolidays: boolean;
        /** 就業時間帯再設定 */
        resettingWorkingHours: boolean;
        /** 申し送り時間再設定 */
        resetTimeForAssig: boolean;
        /** 計算区分 */
        calClass: boolean;
        /** 承認結果反映実施区分 */
        refApprovalresult: boolean;
        /** 反映区分 */
        refClass: number;
        /** 確定済みの場合にも強制的に反映する */
        alsoForciblyReflectEvenIfItIsConfirmed: boolean;
        /** 月別集計実施区分 */
        monthlyAggregation: boolean;
        /** 集計区分 */
        summaryClass: number;

    }

}