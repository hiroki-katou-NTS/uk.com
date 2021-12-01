package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;

/**
* 外部受入実行結果ログ
*/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExacExeResultLog extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 条件設定コード
    */
    private String conditionSetCd;
    
    /**
    * 外部受入処理ID
    */
    private String externalProcessId;
    
    /**
    * 実行者ID
    */
    private String executorId;
    
    /**
    * ユーザID
    */
    private String userId;
    
    /**
    * 処理開始日時
    */
    private GeneralDateTime processStartDatetime;
    
    /**
    * 定型区分
    */
    private StandardFlg standardAtr;
    
    /**
    * 実行形態
    */
    private ExtExecutionMode executeForm;
    
    /**
    * 対象件数
    */
    private int targetCount;
    
    /**
    * エラー件数
    */
    private int errorCount;
    
    /**
    * ファイル名
    */
    private String fileName;
    
    /**
    * システム種類
    */
    private SystemType systemType;
    
    /**
    * 結果状態
    */
    private Optional<ExtResultStatus> resultStatus;
    
    /**
    * 処理終了日時
    */
    private Optional<GeneralDateTime> processEndDatetime;
    
    /**
    * 処理区分
    */
    private ProcessingFlg processAtr;
    
/*    public static ExacExeResultLog createFromJavaType(Long version, String cid,
    		String conditionSetCd, String externalProcessId, 
    		String executorId, String userId, GeneralDateTime processStartDatetime,
    		int standardAtr, int executeForm, int targetCount, int errorCount,
    		String fileName, int systemType, int resultStatus, GeneralDateTime processEndDatetime, int processAtr)
    {
        ExacExeResultLog  exacExeResultLog =  new ExacExeResultLog(cid, conditionSetCd, externalProcessId, executorId, userId, processStartDatetime, standardAtr, executeForm, targetCount, errorCount, fileName, systemType, resultStatus, processEndDatetime,  processAtr);
        exacExeResultLog.setVersion(version);
        return exacExeResultLog;
    }*/
    
}
