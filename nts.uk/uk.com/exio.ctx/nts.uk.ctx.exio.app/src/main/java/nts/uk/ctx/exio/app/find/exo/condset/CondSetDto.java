package nts.uk.ctx.exio.app.find.exo.condset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.condset.CondSet;

@AllArgsConstructor
@Value
public class CondSetDto {
	/**
	 * 定型区分
	 */
	int standardAtr;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * ユーザID
	 */
	private String userId;

	/**
	 * 条件設定コード
	 */
	private String conditionSetCode;

	/**
	 * カテゴリID
	 */
	private int categoryId;

	/**
	 * 区切り文字
	 */
	private int delimiter;

	/**
	 * するしない区分
	 */
	private int itemOutputName;

	/**
	 * するしない区分
	 */
	private int autoExecution;

	/**
	 * 条件設定名称
	 */
	private String conditionSetName;

	/**
	 * するしない区分
	 */
	private int conditionOutputName;

	/**
	 * 文字列形式
	 */
	private int stringFormat;

	public static CondSetDto fromDomain(CondSet domain) {
		return new CondSetDto(domain.getStandardAtr().value, domain.getCid(), domain.getUserId(),
				domain.getConditionSetCode().v(), domain.getCategoryId().v(), domain.getDelimiter().value,
				domain.getItemOutputName().value, domain.getAutoExecution().value, domain.getConditionSetName().v(),
				domain.getConditionOutputName().value, domain.getStringFormat().value);
	}
}
