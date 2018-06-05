package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* データ復旧の実行
*/
@AllArgsConstructor
@Getter
public class PerformDatRecover extends AggregateRoot
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
    * 保存処理ID
    */
    private String saveProcessId;
    
    /**
    * アップロードファイルID
    */
    private String uploadfileId;
    
    /**
    * 復旧ファイル名
    */
    private String recoveryFileName;
    
    /**
    * 復旧対象者数
    */
    private int numPeopleBeRestore;
    
    /**
    * 保存対象者数
    */
    private int numPeopleSave;
    
    /**
    * 復旧方法
    */
    private int recoveryMethod;
    
    /**
    * 対象者
    */
    private String target;
    
    /**
    * 別会社復旧
    */
    private int recoverFromAnoCom;
    
    public static PerformDatRecover createFromJavaType(String dataRecoveryProcessId, String cid, String saveProcessId, String uploadfileId, String recoveryFileName, int numPeopleBeRestore, int numPeopleSave, int recoveryMethod, String target, int recoverFromAnoCom)
    {
        PerformDatRecover  performDatRecover =  new PerformDatRecover(dataRecoveryProcessId, cid, saveProcessId, uploadfileId, recoveryFileName, numPeopleBeRestore, numPeopleSave, recoveryMethod, target,  recoverFromAnoCom);
        return performDatRecover;
    }
    
}
