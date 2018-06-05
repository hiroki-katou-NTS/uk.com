package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* サーバー準備動作管理
*/
@AllArgsConstructor
@Getter
public class ServerPrepareMng extends AggregateRoot
{
    
    /**
    * データ復旧処理ID
    */
    private String dataRecoveryProcessId;
    
    /**
    * データ保存処理ID
    */
    private String dataStoreProcessId;
    
    /**
    * ファイルID
    */
    private String fileId;
    
    /**
    * アップロードファイル名
    */
    private String uploadFileName;
    
    /**
    * アップロードをするしない
    */
    private int doNotUpload;
    
    /**
    * パスワード
    */
    private String password;
    
    /**
    * 動作状態
    */
    private int operatingCondition;
    
    public static ServerPrepareMng createFromJavaType(String dataRecoveryProcessId, String dataStoreProcessId, String fileId, String uploadFileName, int doNotUpload, String password, int operatingCondition)
    {
        ServerPrepareMng  serverPrepareMng =  new ServerPrepareMng(dataRecoveryProcessId, dataStoreProcessId, fileId, uploadFileName, doNotUpload, password,  operatingCondition);
        return serverPrepareMng;
    }
    
}
