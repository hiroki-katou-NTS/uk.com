package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;

/**
* 外部受入エラーログ
*/
@Getter
@AllArgsConstructor
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
    private Optional<String> csvErrorItemName;
    
    /**
    * CSV受入値
    */
    private Optional<String> csvAcceptedValue;
    
    /**
    * エラー内容
    */
    private Optional<String> errorContents;
    
    /**
    * レコード番号
    */
    private AcceptanceLineNumber recordNumber;
    
    /**
    * ログ登録日時
    */
    private GeneralDateTime logRegDateTime;
    
    /**
    * 項目名
    */
    private Optional<String> itemName;
    
    /**
    * エラー発生区分
    */
    private ErrorOccurrenceIndicator errorAtr;

}
