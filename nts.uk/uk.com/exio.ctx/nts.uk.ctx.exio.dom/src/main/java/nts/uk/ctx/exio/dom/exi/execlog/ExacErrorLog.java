package nts.uk.ctx.exio.dom.exi.execlog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 外部受入エラーログ
*/
@AllArgsConstructor
@Getter
@Setter
public class ExacErrorLog extends AggregateRoot
{
    
    /**
    * ログ連番
    */
    private int logSeqNumber;
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 外部受入処理ID
    */
    private String externalProcessId;
    
    /**
    * CSVエラー項目名
    */
    private String csvErrorItemName;
    
    /**
    * CSV受入値
    */
    private String csvAcceptedValue;
    
    /**
    * エラー内容
    */
    private String errorContents;
    
    /**
    * レコード番号
    */
    private int recordNumber;
    
    /**
    * ログ登録日時
    */
    private GeneralDateTime logRegDateTime;
    
    /**
    * 項目名
    */
    private String itemName;
    
    /**
    * エラー発生区分
    */
    private int errorAtr;
    
    public static ExacErrorLog createFromJavaType(Long version, int logSeqNumber, String cid, String externalProcessId, String csvErrorItemName, String csvAcceptedValue, String errorContents, int recordNumber, GeneralDateTime logRegDateTime, String itemName, int errorAtr)
    {
        ExacErrorLog  exacErrorLog =  new ExacErrorLog(logSeqNumber, cid, externalProcessId, csvErrorItemName, csvAcceptedValue, errorContents, recordNumber, logRegDateTime, itemName,  errorAtr);
        exacErrorLog.setVersion(version);
        return exacErrorLog;
    }
    
}
