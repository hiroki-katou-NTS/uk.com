package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@AllArgsConstructor
/**
 * データ削除の結果ログ
 */
public class ResultLogSaving extends DomainObject {

	//field ログ登録日時
	private int logNumber;
	
	//field 契約コード
	private String contractCd;

	//field データ保存処理ID
	private String processingId;

	//field 会社ID
	private String cid;

	//field ログ登録日時
	private GeneralDateTime logTime;

	//field 処理内容
	private ProcessingContent logContent;

	//field エラー社員	
	private String errorEmployeeId;
	
	//field エラー日付	
	private GeneralDate errorDate;
		
	//field エラー内容	
	private ErrorContent errorContent;
	


	public static ResultLogSaving createFromJavatype(int logNumber, String contractCd, String processingId, String cid, GeneralDateTime logTime,
			String logContent, String errorEmployeeId,  GeneralDate errorDate, String errorContent) {
		return new ResultLogSaving(
				logNumber, 
				contractCd,
				processingId, 
				cid, 
				logTime,
				new ProcessingContent(logContent), 
				errorEmployeeId,
				errorDate,
				new ErrorContent(errorContent)
				);
	}
}
