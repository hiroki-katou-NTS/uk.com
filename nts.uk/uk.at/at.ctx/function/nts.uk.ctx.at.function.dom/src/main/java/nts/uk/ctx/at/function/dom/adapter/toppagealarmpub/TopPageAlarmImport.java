package nts.uk.ctx.at.function.dom.adapter.toppagealarmpub;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

/**
 * トップアラームParam
 *
 */
@Data
@Builder
@AllArgsConstructor
public class TopPageAlarmImport {
	/**
	 * アラーム分類
	 */
	private int alarmClassification;
	
	/**
	 * 発生日時
	 */
	private GeneralDateTime occurrenceDateTime;
	
	/**
	 * 表示社員ID
	 */
	private String displaySId;
	
	/**
	 * 表示社員区分
	 */
	private int displayAtr;
	
	/**
	 * パターンコード
	 */
	private Optional<String> patternCode;
	
	/**
	 * パターン名
	 */
	private Optional<String> patternName;
	
	/**
	 * リンクURL
	 */
	private Optional<String> linkUrl;
	
	/**
	 * 表示メッセージ
	 */
	private Optional<String> displayMessage;

	/**
	 * 部下の社員ID
	 */
	private List<String> subEmployeeIds;
	
	/**
	 * 部下のエラーがない社員ID
	 */
	private List<String> noErrEmployeeIds;
}
