package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.StringUtil;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;

/**
* 外部受入エラーログ
*/
@Getter
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

	public ExacErrorLog(int logSeqNumber, String cid, String externalProcessId, String csvErrorItemName,
			String csvAcceptedValue, String errorContents, int recordNumber,
			GeneralDateTime logRegDateTime, String itemName, int errorAtr) {
		super();
		this.logSeqNumber = logSeqNumber;
		this.cid = cid;
		this.externalProcessId = externalProcessId;
		if (StringUtil.isNullOrEmpty(csvErrorItemName, true)) {
			this.csvErrorItemName = Optional.empty();
		} else {
			this.csvErrorItemName = Optional.of(csvErrorItemName);
		}
		if (StringUtil.isNullOrEmpty(csvAcceptedValue, true)) {
			this.csvAcceptedValue = Optional.empty();
		} else {
			this.csvAcceptedValue = Optional.of(csvAcceptedValue);
		}
		if (StringUtil.isNullOrEmpty(errorContents, true)) {
			this.errorContents = Optional.empty();
		} else {
			this.errorContents = Optional.of(errorContents);
		}
		this.recordNumber = new AcceptanceLineNumber(recordNumber);
		this.logRegDateTime = logRegDateTime;
		if (StringUtil.isNullOrEmpty(itemName, true)) {
			this.itemName = Optional.empty();
		} else {
			this.itemName = Optional.of(itemName);
		}
		this.errorAtr = EnumAdaptor.valueOf(errorAtr, ErrorOccurrenceIndicator.class);
	} 
}
