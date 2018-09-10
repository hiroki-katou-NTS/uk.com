package nts.uk.ctx.exio.dom.exo.condset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 出力条件設定（定型）
 */
@AllArgsConstructor
@Getter
public class StdOutputCondSet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 外部出力条件コード
	 */
	private String conditionSetCd;

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

	public static StdOutputCondSet toDomain(String cid, String conditionSetCd, String categoryId,
			int delimiter, int itemOutputName, int autoExecution, String conditionSetName, int conditionOutputName,
			int stringFormat) {
		StdOutputCondSet stdOutputCondSet = new StdOutputCondSet(cid, conditionSetCd, categoryId, delimiter,
				itemOutputName, autoExecution, conditionSetName, conditionOutputName, stringFormat);
		return stdOutputCondSet;
	}

}
