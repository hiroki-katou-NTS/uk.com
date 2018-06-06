package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* データ復旧の結果ログ
*/
@AllArgsConstructor
@Getter
public class DataRecoverLog extends AggregateRoot
{
    
    /**
    * データ復旧処理ID
    */
    private String recoveryProcessId;
    
    /**
    * 対象者
    */
    private String target;
    
    /**
    * エラー内容
    */
    private String errorContent;
    
    /**
    * 対象日
    */
    private GeneralDate targetDate;
    
    public static DataRecoverLog createFromJavaType(String recoveryProcessId, String target, String errorContent, GeneralDate targetDate)
    {
        DataRecoverLog  dataRecoverLog =  new DataRecoverLog(recoveryProcessId, target, errorContent,  targetDate);
        return dataRecoverLog;
    }
    
}
