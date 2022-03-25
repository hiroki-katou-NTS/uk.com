package nts.uk.ctx.exio.dom.exo.condset;

import lombok.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
import nts.uk.ctx.exio.dom.exo.category.CategoryCd;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部出力.出力条件設定.出力条件設定（定型）
 * @author nws-minhnb
 */
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

	// ＃123273①
    /**
     * 文字コード交換
     */
    private EncodeType encodeType;

    /**
     * 出力ファイル名
     */
    private Optional<ExternalOutputFileName> fileName;

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
	public static StdOutputCondSet createFromMementoSave(String companyId, MementoGetter memento) {
		StdOutputCondSet domain = new StdOutputCondSet();
		domain.getMementoForSave(memento);
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
		this.encodeType = EnumAdaptor.valueOf(memento.getEncodeType(),EncodeType.class);
		this.fileName = Optional.ofNullable(StringUtil.isNullOrEmpty(memento.getFileName(),false)? null : new ExternalOutputFileName(memento.getFileName()));
	}

	public void getMementoForSave(MementoGetter memento) {
//		this.companyId = memento.getCompanyId();
		this.conditionSetCode = new ExternalOutputConditionCode(memento.getConditionSetCode());
		this.categoryId = new CategoryCd(memento.getCategoryId());
		this.delimiter = EnumAdaptor.valueOf(memento.getDelimiter(), Delimiter.class);
		this.itemOutputName = EnumAdaptor.valueOf(memento.getItemOutputName(), NotUseAtr.class);
		this.autoExecution = EnumAdaptor.valueOf(memento.getAutoExecution(), NotUseAtr.class);
		this.conditionSetName = new ExternalOutputConditionName(memento.getConditionSetName());
		this.conditionOutputName = EnumAdaptor.valueOf(memento.getConditionOutputName(), NotUseAtr.class);
		this.stringFormat = EnumAdaptor.valueOf(memento.getStringFormat(), StringFormat.class);
		this.encodeType = EnumAdaptor.valueOf(memento.getEncodeType(),EncodeType.class);
		if (StringUtil.isNullOrEmpty(memento.getFileName(),false)) {
			this.fileName = Optional.ofNullable(null);
		} else {
			Pattern notAllow1 = Pattern.compile("^[^”*:<>?/\\\\|~”#%&*:<>?/\\\\{|}\"]*$");
			val notAllow2 = Arrays.asList("AUX","PRN", "NUL", "CON", "COM0", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT0", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9");
			Matcher matcher = notAllow1.matcher(memento.getFileName());
			if (!matcher.matches()){
				throw new BusinessException("Msg_3340","CMF002_559");
			}
			if (notAllow2.contains(memento.getFileName().toUpperCase())){
				throw new BusinessException("Msg_3341","CMF002_559");
			}
			this.fileName = Optional.ofNullable(new ExternalOutputFileName(memento.getFileName()));
		}

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
		memento.setEncodeType(this.encodeType.value);
		memento.setFileName(this.fileName.map(i->i.v()).orElse(null));
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

		void setEncodeType(int encodeType);

		void setFileName(String fileName);
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

		int getEncodeType();

		String getFileName();
	}

}
