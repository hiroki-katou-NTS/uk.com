package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.List;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
/**
 * 
 * @author hieult
 *
 */
@Getter
public class ExecutionProcessingCommand {

	/** ＜画面Aから受け取るパラメータ＞ (param nhận từ man A) 締めID */
    private int closureID;
    /** ＜画面Bから受け取るパラメータ＞ */
    /** 日別作成(打刻反映)実施区分 */
    private boolean dailyCreation;
    /** 作成区分 */
     private int creationType;
    /** 再作成区分 */
    private int resetClass;
    /** 計算区分を再度設定 */
    private boolean calClassReset;
    /** マスタ情報を再度設定 */
    private boolean masterReconfiguration;
    /** 特定日区分を再度設定 */
    private boolean specDateClassReset;
    /** 育児・介護短時間再設定 */
    private boolean resetTimeForChildOrNurseCare;
    /** 打刻のみを再度反映 */
    private boolean refNumberFingerCheck;
    /** 休業再設定 */
    private boolean closedHolidays;
    /** 就業時間帯再設定 */
    private boolean resettingWorkingHours;
    /** 申し送り時間再設定 */
    private boolean resetTimeForAssig;
    /** 計算区分 */
    private int calClass;
    /** 承認結果反映実施区分 */
    private boolean refApprovalresult;
    /** 反映区分 */
    private int refClass;
    /** 確定済みの場合にも強制的に反映する */
    private boolean alsoForciblyReflectEvenIfItIsConfirmed;
    /** 月別集計実施区分 */
    private boolean monthlyAggregation;
    /** 集計区分 */
    private int summaryClass;
    /** 就業計算と集計実行ログID*/
    private String empCalAndSumExecLogID;
    /** ＜画面Cから受け取るパラメータ＞ */
    /** List 社員ID */
    private List<String> lstEmployeeID;
    /** 対象期間開始日 */
    private String periodStartDate;
    
    private String periodEndDate;
    
    /** 対象期間終了日 */
    private String targetEndDate;
    /**＜画面 J から受け取るパラメータ＞  */
    /** ケース別実行実施内容ID */
    private String caseSpecExeContentID;
    /** 実行内容*/
    private ExecutionContent excutionContent;
	/** 実行したメニュー */    
    private int executedMenu;
}
