package nts.uk.ctx.exio.dom.exi.condset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.exio.dom.exi.csvimport.ExiCharset;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部受入.受入条件設定.受入条件設定（定型）
 *
 * @author nws-minhnb
 */
@Getter
public class StdAcceptCondSet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 条件設定コード
	 */
	private AcceptanceConditionCode conditionSetCode;

	/**
	 * カテゴリID
	 */
	private Optional<String> categoryId;

	/**
	 * CSVデータの項目名行
	 */
	private Optional<AcceptanceLineNumber> csvDataItemLineNumber;

	/**
	 * システム種類
	 */
	private SystemType systemType;

	/**
	 * 既存データの削除
	 */
	private NotUseAtr deleteExistData;

	/**
	 * CSVデータの取込開始行
	 */
	private Optional<AcceptanceLineNumber> csvDataStartLine;

	/**
	 * 文字コード
	 */
	private Optional<ExiCharset> characterCode;

	/**
	 * 受入モード
	 */
	private Optional<AcceptMode> acceptMode;

	/**
	 * 条件設定名称
	 */
	private AcceptanceConditionName conditionSetName;

	/**
	 * チェック完了
	 */
	private Optional<NotUseAtr> checkCompleted;

	/**
	 * 既存データの削除方法
	 */
	private Optional<DeleteExistDataMethod> deleteExistDataMethod;

	/**
	 * No args constructor.
	 */
	private StdAcceptCondSet() {
	}

	/**
	 * Creates domain from memento.
	 *
	 * @param companyId the company id
	 * @param memento   the Memento getter
	 * @return the domain 受入条件設定（定型）
	 */
	public static StdAcceptCondSet createFromMemento(String companyId, MementoGetter memento) {
		StdAcceptCondSet domain = new StdAcceptCondSet();
		domain.getMemento(memento);
		domain.companyId = companyId;
		return domain;
	}

	/**
	 * Update domain from new domain.
	 *
	 * @param newDomain the new domain 受入条件設定（定型）
	 * @return the domain 受入条件設定（定型）
	 */
	public StdAcceptCondSet updateDomain(StdAcceptCondSet newDomain) {
		if (newDomain != null) {
			this.categoryId = newDomain.getCategoryId();
			this.csvDataItemLineNumber = newDomain.getCsvDataItemLineNumber();
			this.csvDataStartLine = newDomain.getCsvDataStartLine();
			this.characterCode = newDomain.getCharacterCode();
		}
		return this;
	}

	/**
	 * Sets check completed.
	 *
	 * @param notUseAtr the enum NotUseAtr
	 */
	public void setCheckCompleted(NotUseAtr notUseAtr) {
		this.checkCompleted = Optional.of(notUseAtr);
	}

	/**
	 * Gets memento.
	 *
	 * @param memento the Memento getter
	 */
	public void getMemento(MementoGetter memento) {
//		this.companyId = memento.getCompanyId();
		this.conditionSetCode = new AcceptanceConditionCode(memento.getConditionSetCode());
		this.categoryId = Optional.ofNullable(memento.getCategoryId());
		this.csvDataItemLineNumber = Optional.ofNullable(memento.getCsvDataItemLineNumber())
											 .map(AcceptanceLineNumber::new);
		this.systemType = EnumAdaptor.valueOf(memento.getSystemType(), SystemType.class);
		this.deleteExistData = EnumAdaptor.valueOf(memento.getDeleteExistData(), NotUseAtr.class);
		this.csvDataStartLine = Optional.ofNullable(memento.getCsvDataStartLine())
										.map(AcceptanceLineNumber::new);
		this.characterCode = Optional.ofNullable(memento.getCharacterCode())
									 .map(characterCode -> EnumAdaptor.valueOf(characterCode, ExiCharset.class));
		this.acceptMode = Optional.ofNullable(memento.getAcceptMode())
								  .map(acceptMode -> EnumAdaptor.valueOf(acceptMode, AcceptMode.class));
		this.conditionSetName = new AcceptanceConditionName(memento.getConditionSetName());
		this.checkCompleted = Optional.ofNullable(memento.getCheckCompleted())
									  .map(checkCompleted -> EnumAdaptor.valueOf(checkCompleted, NotUseAtr.class));
		this.deleteExistDataMethod = Optional.ofNullable(memento.getDeleteExistDataMethod())
											 .map(deleteExistDataMethod -> EnumAdaptor.valueOf(deleteExistDataMethod,
																							   DeleteExistDataMethod.class));
	}

	/**
	 * Sets memento.
	 *
	 * @param memento the Memento setter
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCompanyId(this.companyId);
		memento.setConditionSetCode(this.conditionSetCode.v());
		memento.setCategoryId(this.categoryId.orElse(null));
		memento.setCsvDataItemLineNumber(this.csvDataItemLineNumber.map(PrimitiveValueBase::v).orElse(null));
		memento.setSystemType(this.systemType.value);
		memento.setDeleteExistData(this.deleteExistData.value);
		memento.setCsvDataStartLine(this.csvDataStartLine.map(AcceptanceLineNumber::v).orElse(null));
		memento.setCharacterCode(this.characterCode.map(exiCharset -> exiCharset.value).orElse(null));
		memento.setAcceptMode(this.acceptMode.map(acceptMode -> acceptMode.value).orElse(null));
		memento.setConditionSetName(this.conditionSetName.v());
		memento.setCheckCompleted(this.checkCompleted.map(notUseAtr -> notUseAtr.value).orElse(null));
		memento.setDeleteExistDataMethod(this.deleteExistDataMethod.map(delExistDataMethod -> delExistDataMethod.value)
																   .orElse(null));
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
		void setCategoryId(String categoryId);

		/**
		 * Sets csv data item line number.
		 *
		 * @param csvDataItemLineNumber the csv data item line number
		 */
		void setCsvDataItemLineNumber(Integer csvDataItemLineNumber);

		/**
		 * Sets system type.
		 *
		 * @param systemType the system type
		 */
		void setSystemType(int systemType);

		/**
		 * Sets delete exist data.
		 *
		 * @param deleteExistData the delete exist data
		 */
		void setDeleteExistData(int deleteExistData);

		/**
		 * Sets csv data start line.
		 *
		 * @param csvDataStartLine the csv data start line
		 */
		void setCsvDataStartLine(Integer csvDataStartLine);

		/**
		 * Sets character code.
		 *
		 * @param characterCode the character code
		 */
		void setCharacterCode(Integer characterCode);

		/**
		 * Sets accept mode.
		 *
		 * @param acceptMode the accept mode
		 */
		void setAcceptMode(Integer acceptMode);

		/**
		 * Sets condition set name.
		 *
		 * @param conditionSetName the condition set name
		 */
		void setConditionSetName(String conditionSetName);

		/**
		 * Sets check completed.
		 *
		 * @param checkCompleted the check completed
		 */
		void setCheckCompleted(Integer checkCompleted);

		/**
		 * Sets delete exist data method.
		 *
		 * @param deleteExistDataMethod the delete exist data method
		 */
		void setDeleteExistDataMethod(Integer deleteExistDataMethod);
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
		String getCategoryId();

		/**
		 * Gets csv data item line number.
		 *
		 * @return the csv data item line number
		 */
		Integer getCsvDataItemLineNumber();

		/**
		 * Gets system type.
		 *
		 * @return the system type
		 */
		int getSystemType();

		/**
		 * Gets delete exist data.
		 *
		 * @return the delete exist data
		 */
		int getDeleteExistData();

		/**
		 * Gets csv data start line.
		 *
		 * @return the csv data start line
		 */
		Integer getCsvDataStartLine();

		/**
		 * Gets character code.
		 *
		 * @return the character code
		 */
		Integer getCharacterCode();

		/**
		 * Gets accept mode.
		 *
		 * @return the accept mode
		 */
		Integer getAcceptMode();

		/**
		 * Gets condition set name.
		 *
		 * @return the condition set name
		 */
		String getConditionSetName();

		/**
		 * Gets check completed.
		 *
		 * @return the check completed
		 */
		Integer getCheckCompleted();

		/**
		 * Gets delete exist data method.
		 *
		 * @return the delete exist data method
		 */
		Integer getDeleteExistDataMethod();
	}

//	public void updateCheckComplete(Integer check) {
//		this.checkCompleted = Optional.of(EnumAdaptor.valueOf(check, NotUseAtr.class));
//	}

}
