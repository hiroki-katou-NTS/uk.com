package nts.uk.query.app.exo.condset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;

/**
 * The Standard output condition setting dto.<br>
 * 出力条件設定（定型）
 * 
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class StdOutputCondSetDto {

	/** 会社ID */
	private String companyId;

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
	 * From domain.
	 * 
	 * @param domain the Standard output condition setting domain
	 * @return the <code>StdOutputCondSetDto</code>
	 */
	public static StdOutputCondSetDto fromDomain(StdOutputCondSet domain) {
		return new StdOutputCondSetDto(domain.getCid(),
									   domain.getConditionSetCode().v(),
									   domain.getCategoryId().v(),
									   domain.getDelimiter().value,
									   domain.getItemOutputName().value,
									   domain.getAutoExecution().value,
									   domain.getConditionSetName().v(),
									   domain.getConditionOutputName().value,
									   domain.getStringFormat().value);
	}

}
