package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* データ復旧の結果
*/
@AllArgsConstructor
@Getter
public class DataRecoverResult extends DomainObject
{
    
    /**
    * データ復旧処理ID
    */
    private String dataRecoveryProcessId;
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 保存セットコード
    */
    private String saveSetCd;
    
    /**
    * 実行者
    */
    private String practitioner;
    
    /**
    * 実行結果
    */
    private String executionResult;
    
    /**
    * 開始日時
    */
    private GeneralDateTime startDateTime;
    
    /**
    * 終了日時
    */
    private GeneralDateTime endDateTime;
    
    /**
    * 保存形態
    */
    private int saveForm;
    
    /**
    * 保存名称
    */
    private String saveName;
    
    public static DataRecoverResult createFromJavaType(String dataRecoveryProcessId, String cid, String saveSetCd, String practitioner, String executionResult, GeneralDateTime startDateTime, GeneralDateTime endDateTime, int saveForm, String saveName)
    {
        DataRecoverResult  dataRecoverResult =  new DataRecoverResult(dataRecoveryProcessId, cid, saveSetCd, practitioner, executionResult, startDateTime, endDateTime, saveForm,  saveName);
        return dataRecoverResult;
    }
    
}
