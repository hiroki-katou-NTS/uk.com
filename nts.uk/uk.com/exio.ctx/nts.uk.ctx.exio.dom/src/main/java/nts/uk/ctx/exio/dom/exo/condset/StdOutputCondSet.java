package nts.uk.ctx.exio.dom.exo.condset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.category.CategoryCd;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 出力条件設定（定型）
 */
@Getter
public class StdOutputCondSet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

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

	public StdOutputCondSet(String cid, String conditionSetCode, int categoryId, int delimiter, int itemOutputName,
			int autoExecution, String conditionSetName, int conditionOutputName, int stringFormat) {
		super();
		this.cid = cid;
		this.conditionSetCode = new ExternalOutputConditionCode(conditionSetCode);
		this.categoryId = new CategoryCd(categoryId);
		this.delimiter = EnumAdaptor.valueOf(delimiter, Delimiter.class);
		this.itemOutputName = EnumAdaptor.valueOf(itemOutputName, NotUseAtr.class);
		this.autoExecution = EnumAdaptor.valueOf(autoExecution, NotUseAtr.class);
		this.conditionSetName = new ExternalOutputConditionName(conditionSetName);
		this.conditionOutputName = EnumAdaptor.valueOf(conditionOutputName, NotUseAtr.class);
		this.stringFormat = EnumAdaptor.valueOf(stringFormat, StringFormat.class);
	}
}
