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

	/**
	 * ログ登録日時
	 */
	private int logNumber;
	
	/**
	 *  契約コード
	 */
	private String contractCd;

	/**
	 * データ保存処理ID
	 */
	private String processingId;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * ログ登録日時
	 */
	private GeneralDateTime logTime;

	/**
	 * 処理内容
	 */
	private ProcessingContent logContent;

	/**
	 * エラー社員	
	 */
	private String errorEmployeeId;

	/**
	 * エラー日付	
	 */
	private GeneralDate errorDate;

	/**
	 * エラー内容	
	 */
	private ErrorContent errorContent;

	
	public static ResultLogSaving createFromJavatype(
			int logNumber, 
			String contractCd, 
			String processingId, 
			String cid, 
			GeneralDateTime logTime,
			String logContent, 
			String errorEmployeeId,  
			GeneralDate errorDate, 
			String errorContent) {
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