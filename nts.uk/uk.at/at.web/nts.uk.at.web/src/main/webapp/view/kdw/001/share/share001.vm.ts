module nts.uk.at.view.kdw001 {
    export module share.model { 
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
        
        export interface executionProcessingCommand extends paraScreenA, paraScreenC, paraScreenB, paraScreenJ {
            screen: string;
        }
    
        export interface paraScreenA {
            closure: number;
        }
        
        export interface paraScreenB {
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
            /** 実行内容 */
            excutionContent: number;
        }
        
        export interface paraScreenC {
            lstEmployeeID: Array<string>;
            /** 対象期間開始日 */
            periodStartDate: string;
            periodEndDate: string;
            /** 対象期間終了日 */
            targetEndDate: string;
        }
    
        export interface paraScreenJ {
            caseSpecExeContentID: string;
        }
    }
}