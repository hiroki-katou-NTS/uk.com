package nts.uk.ctx.exio.dom.exo.exechist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;

/**
 * 条件設定（定型/ユーザ）
 */
@AllArgsConstructor
@Getter
public class CondSet {
	/**
	 * 定型区分
	 */
	int standardAttr;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * ユーザID
	 */
	private String userId;

	/**
	 * 外部出力条件コード
	 */
	private String conditionSetCode;

	/**
	 * カテゴリID
	 */
	private String categoryId;

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
	 * 外部出力条件名称
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

	public static CondSet fromStdOutputCondSet(StdOutputCondSet domain) {
		CondSet condSet = new CondSet(0, domain.getCid(), null, domain.getConditionSetCode(), domain.getCategoryId(),
				domain.getDelimiter(), domain.getItemOutputName(), domain.getAutoExecution(),
				domain.getConditionSetName(), domain.getConditionOutputName(), domain.getStringFormat());
		return condSet;
	}
}
