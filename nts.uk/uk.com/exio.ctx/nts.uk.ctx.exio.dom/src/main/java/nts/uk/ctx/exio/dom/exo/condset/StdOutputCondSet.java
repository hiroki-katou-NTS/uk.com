package nts.uk.ctx.exio.dom.exo.condset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.category.CategoryCd;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部出力.出力条件設定.出力条件設定（定型）
 *
 * @author nws-minhnb
 */
@Getter
public class StdOutputCondSet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyId;

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
	 * 条件名出力
	 */
	private NotUseAtr conditionOutputName;

	/**
	 * 文字列形式
	 */
	private StringFormat stringFormat;

	/**
	 * No args constructor.
	 */
	private StdOutputCondSet() {
	}

	/**
	 * Creates domain from memento.
	 *
	 * @param companyId the company id
	 * @param memento   the Memento getter
	 * @return the domain 出力条件設定（定型）
	 */
	public static StdOutputCondSet createFromMemento(String companyId, MementoGetter memento) {
		StdOutputCondSet domain = new StdOutputCondSet();
		domain.getMemento(memento);
		domain.companyId = companyId;
		return domain;
	}

	/**
	 * Gets memento.
	 *
	 * @param memento the Memento getter
	 */
	public void getMemento(MementoGetter memento) {
//		this.companyId = memento.getCompanyId();
		this.conditionSetCode = new ExternalOutputConditionCode(memento.getConditionSetCode());
		this.categoryId = new CategoryCd(memento.getCategoryId());
		this.delimiter = EnumAdaptor.valueOf(memento.getDelimiter(), Delimiter.class);
		this.itemOutputName = EnumAdaptor.valueOf(memento.getItemOutputName(), NotUseAtr.class);
		this.autoExecution = EnumAdaptor.valueOf(memento.getAutoExecution(), NotUseAtr.class);
		this.conditionSetName = new ExternalOutputConditionName(memento.getConditionSetName());
		this.conditionOutputName = EnumAdaptor.valueOf(memento.getConditionOutputName(), NotUseAtr.class);
		this.stringFormat = EnumAdaptor.valueOf(memento.getStringFormat(), StringFormat.class);
	}

	/**
	 * Sets memento.
	 *
	 * @param memento the Memento setter
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCompanyId(this.companyId);
		memento.setConditionSetCode(this.conditionSetCode.v());
		memento.setCategoryId(this.categoryId.v());
		memento.setDelimiter(this.delimiter.value);
		memento.setItemOutputName(this.itemOutputName.value);
		memento.setAutoExecution(this.autoExecution.value);
		memento.setConditionSetName(this.conditionSetName.v());
		memento.setConditionOutputName(this.conditionOutputName.value);
		memento.setStringFormat(this.stringFormat.value);
	}

	/**
	 * The interface Memento setter.
	 */
	public static interface MementoSetter {
		/**
		 * Sets company id.
		 *
		 * @param companyId the company id
		 */
		void setCompanyId(String companyId);

		/**
		 * Sets condition set code.
		 *
		 * @param conditionSetCode the condition set code
		 */
		void setConditionSetCode(String conditionSetCode);

		/**
		 * Sets category id.
		 *
		 * @param categoryId the category id
		 */
		void setCategoryId(int categoryId);

		/**
		 * Sets delimiter.
		 *
		 * @param delimiter the delimiter
		 */
		void setDelimiter(int delimiter);

		/**
		 * Sets item output name.
		 *
		 * @param itemOutputName the item output name
		 */
		void setItemOutputName(int itemOutputName);

		/**
		 * Sets auto execution.
		 *
		 * @param autoExecution the auto execution
		 */
		void setAutoExecution(int autoExecution);

		/**
		 * Sets condition set name.
		 *
		 * @param conditionSetName the condition set name
		 */
		void setConditionSetName(String conditionSetName);

		/**
		 * Sets condition output name.
		 *
		 * @param conditionOutputName the condition output name
		 */
		void setConditionOutputName(int conditionOutputName);

		/**
		 * Sets string format.
		 *
		 * @param stringFormat the string format
		 */
		void setStringFormat(int stringFormat);
	}

	/**
	 * The interface Memento getter.
	 */
	public static interface MementoGetter {
		/**
		 * Gets company id.
		 *
		 * @return the company id
		 */
		String getCompanyId();

		/**
		 * Gets condition set code.
		 *
		 * @return the condition set code
		 */
		String getConditionSetCode();

		/**
		 * Gets category id.
		 *
		 * @return the category id
		 */
		int getCategoryId();

		/**
		 * Gets delimiter.
		 *
		 * @return the delimiter
		 */
		int getDelimiter();

		/**
		 * Gets item output name.
		 *
		 * @return the item output name
		 */
		int getItemOutputName();

		/**
		 * Gets auto execution.
		 *
		 * @return the auto execution
		 */
		int getAutoExecution();

		/**
		 * Gets condition set name.
		 *
		 * @return the condition set name
		 */
		String getConditionSetName();

		/**
		 * Gets condition output name.
		 *
		 * @return the condition output name
		 */
		int getConditionOutputName();

		/**
		 * Gets string format.
		 *
		 * @return the string format
		 */
		int getStringFormat();
	}

}
