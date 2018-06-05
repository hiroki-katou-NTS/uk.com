package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* データ復旧動作管理
*/
@AllArgsConstructor
@Getter
public class DataRecoveryMng extends AggregateRoot
{
    
    /**
    * 処理ID
    */
    private String dataRecoveryProcessId;
    
    /**
    * エラー件数
    */
    private int errorCount;
    
    /**
    * カテゴリカウント
    */
    private int categoryCnt;
    
    /**
    * カテゴリトータルカウント
    */
    private int categoryTotalCount;
    
    /**
    * トータル処理件数
    */
    private int totalNumOfProcesses;
    
    /**
    * 処理件数
    */
    private int numOfProcesses;
    
    /**
    * 処理対象社員コード
    */
    private String processTargetEmpCode;
    
    /**
    * 中断状態
    */
    private int suspendedState;
    
    /**
    * 動作状態
    */
    private int operatingCondition;
    
    /**
    * 復旧日付
    */
    private GeneralDateTime recoveryDate;
    
    public static DataRecoveryMng createFromJavaType(String dataRecoveryProcessId, int errorCount, int categoryCnt, int categoryTotalCount, int totalNumOfProcesses, int numOfProcesses, String processTargetEmpCode, int suspendedState, int operatingCondition, GeneralDateTime recoveryDate)
    {
        DataRecoveryMng  dataRecoveryMng =  new DataRecoveryMng(dataRecoveryProcessId, errorCount, categoryCnt, categoryTotalCount, totalNumOfProcesses, numOfProcesses, processTargetEmpCode, suspendedState, operatingCondition,  recoveryDate);
        return dataRecoveryMng;
    }
    
}
