package nts.uk.ctx.at.function.app.find.condset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.adapter.condset.StdOutputCondSetImport;

/**
 * The class Standard output condition setting dto.<br>
 * 出力条件設定（定型）
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class StdOutputCondSetDto {

	/** 会社ID */
	private String cid;

	/** 外部出力条件コード */
	private String conditionSetCd;

	/** カテゴリID */
	private int categoryId;

	/** 区切り文字 */
	private int delimiter;

	/** するしない区分 */
	private int itemOutputName;

	/** するしない区分 */
	private int autoExecution;

	/** 外部出力条件名称 */
	private String conditionSetName;

	/** するしない区分 */
	private int conditionOutputName;

	/** 文字列形式 */
	private int stringFormat;

	/**
	 * From import.
	 *
	 * @param condSetImport the Standard output condition setting import
	 * @return the <code>StdOutputCondSetDto</code>
	 */
	public static StdOutputCondSetDto fromImport(StdOutputCondSetImport condSetImport) {
		return new StdOutputCondSetDto(
				condSetImport.getCid(),
				condSetImport.getConditionSetCd(),
				condSetImport.getCategoryId(),
				condSetImport.getDelimiter(),
				condSetImport.getItemOutputName(),
				condSetImport.getAutoExecution(),
				condSetImport.getConditionSetName(),
				condSetImport.getConditionOutputName(),
				condSetImport.getStringFormat());
	}

}
