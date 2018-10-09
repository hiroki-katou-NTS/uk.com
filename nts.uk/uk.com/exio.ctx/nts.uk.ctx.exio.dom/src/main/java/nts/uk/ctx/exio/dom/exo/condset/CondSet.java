package nts.uk.ctx.exio.dom.exo.condset;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.category.CategoryCd;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 条件設定（定型/ユーザ）
 */
@Getter
public class CondSet {
	/**
	 * 定型区分
	 */
	StandardAtr standardAtr;

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
	private ExternalOutputConditionCode conditionSetCode;

	/**
	 * カテゴリID
	 */
	private CategoryCd categoryId;

	/**
	 * 区切り文字
	 */
	private Delimiter delimiter;

	/**
	 * するしない区分
	 */
	private NotUseAtr itemOutputName;

	/**
	 * するしない区分
	 */
	private NotUseAtr autoExecution;

	/**
	 * 条件設定名称
	 */
	private ExternalOutputConditionName conditionSetName;

	/**
	 * するしない区分
	 */
	private NotUseAtr conditionOutputName;

	/**
	 * 文字列形式
	 */
	private StringFormat stringFormat;

	public static CondSet fromStdOutputCondSet(StdOutputCondSet domain) {
		CondSet condSet = new CondSet(StandardAtr.STANDARD, domain.getCid(), null, domain.getConditionSetCode(),
				domain.getCategoryId(), domain.getDelimiter(), domain.getItemOutputName(), domain.getAutoExecution(),
				domain.getConditionSetName(), domain.getConditionOutputName(), domain.getStringFormat());
		return condSet;
	}

	public CondSet(StandardAtr standardAttr, String cid, String userId, ExternalOutputConditionCode conditionSetCode,
			CategoryCd categoryId, Delimiter delimiter, NotUseAtr itemOutputName, NotUseAtr autoExecution,
			ExternalOutputConditionName conditionSetName, NotUseAtr conditionOutputName, StringFormat stringFormat) {
		super();
		this.standardAtr = standardAttr;
		this.cid = cid;
		this.userId = userId;
		this.conditionSetCode = conditionSetCode;
		this.categoryId = categoryId;
		this.delimiter = delimiter;
		this.itemOutputName = itemOutputName;
		this.autoExecution = autoExecution;
		this.conditionSetName = conditionSetName;
		this.conditionOutputName = conditionOutputName;
		this.stringFormat = stringFormat;
	}
}
