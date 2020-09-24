package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.補助機能.データ保存.パターン設定.データ保存のパターン設定
 */
@Getter
public class DataStoragePatternSetting extends AggregateRoot {
	/**
	 * データ保存の選択カテゴリ
	 */
	private DataStorageSelectionCategory dataStorageSelectionCategory;

	/**
	 * パスワード有無
	 */
	private NotUseAtr withoutPassword;

	/**
	 * パターンコード
	 */
	private PatternCode patternCode;

	/**
	 * パターン区分
	 */
	private PatternClassification patternClassification;

	/**
	 * パターン名
	 */
	private PatternName patternName;

	/**
	 * パターン圧縮パスワード
	 */
	private Optional<FileCompressionPassword> patternCompressionPwd;

	/**
	 * パターン補足説明
	 */
	private Optional<String> patternSuppleExplanation;

	/**
	 * 契約コード
	 */
	private ContractCode contractCode;

	/**
	 * 年次参照月
	 */
	private Optional<TargetYear> annualReferMonth;

	/**
	 * 日次参照年
	 */
	private Optional<TargetYear> dailyReferYear;

	/**
	 * 日次参照月
	 */
	private Optional<TargetMonth> dailyReferMonth;

	/**
	 * 月次参照年
	 */
	private Optional<TargetYear> monthlyReferYear;

	/**
	 * 月次参照月
	 */
	private Optional<TargetMonth> monthlyReferMonth;

	/**
	 * 調査用保存の識別
	 */
	private SurveySettingCategory idenSurveyArch;

	/**
	 * 
	 * @param memento
	 * @return
	 */
	public static DataStoragePatternSetting createFromMemento(MementoGetter memento) {
		DataStoragePatternSetting domain = new DataStoragePatternSetting();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.annualReferMonth = Optional
				.ofNullable(EnumAdaptor.valueOf(memento.getAnnualReferMonth(), TargetYear.class));
		this.contractCode = new ContractCode(memento.getContractCode());
		this.dailyReferMonth = Optional
				.ofNullable(EnumAdaptor.valueOf(memento.getDailyReferMonth(), TargetMonth.class));
		this.dailyReferYear = Optional.ofNullable(EnumAdaptor.valueOf(memento.getDailyReferYear(), TargetYear.class));
//		this.dataStorageSelectionCategory = Dat
		this.idenSurveyArch = EnumAdaptor.valueOf(memento.getIdenSurveyArch(), SurveySettingCategory.class);
		this.monthlyReferMonth = Optional
				.ofNullable(EnumAdaptor.valueOf(memento.getMonthlyReferMonth(), TargetMonth.class));
		this.monthlyReferYear = Optional
				.ofNullable(EnumAdaptor.valueOf(memento.getMonthlyReferYear(), TargetYear.class));
		this.patternClassification = EnumAdaptor.valueOf(memento.getPatternClassfication(),
				PatternClassification.class);
		this.patternCode = new PatternCode(memento.getPatternCode());
		this.patternCompressionPwd = Optional
				.ofNullable(new FileCompressionPassword(memento.getPatternCompressionPwd()));
		this.patternName = new PatternName(memento.getPatternName());
		this.patternSuppleExplanation = Optional.ofNullable(memento.getPatternSuppleExplanation());
		this.withoutPassword = EnumAdaptor.valueOf(memento.getWithoutPassword(), NotUseAtr.class);
	}

	public void setMemento(MementoSetter memento) {
		annualReferMonth.ifPresent(val -> memento.setAnnualReferMonth(val.value));
		memento.setContractCode(contractCode.v());
		dailyReferMonth.ifPresent(val -> memento.setDailyReferMonth(val.value));
		dailyReferYear.ifPresent(val -> memento.setDailyReferYear(val.value));
//		memento.setCategoryId(dataStorageSelectionCategory.getCategoryId().v());
//		memento.setSystemType(dataStorageSelectionCategory.getSystemType().value);
		memento.setIdenSurveyArch(idenSurveyArch.value);
		monthlyReferMonth.ifPresent(val -> memento.setMonthlyReferMonth(val.value));
		monthlyReferYear.ifPresent(val -> memento.setMonthlyReferYear(val.value));
		memento.setPatternClassification(patternClassification.value);
		memento.setPatternCode(patternCode.v());
		patternCompressionPwd.ifPresent(val -> memento.setPatternCompressionPwd(val.v()));
		memento.setPatternName(patternName.v());
		patternSuppleExplanation.ifPresent(val -> memento.setPatternSuppleExplanation(val));
		memento.setWithoutPassword(withoutPassword.value);
	}

	public static interface MementoSetter {
		void setCategoryId(String categoryId);

		void setSystemType(int systemType);

		void setWithoutPassword(int withoutPassword);

		void setPatternCode(String patternCode);

		void setPatternClassification(int patternClassification);

		void setPatternName(String patternName);

		void setPatternCompressionPwd(String patternCompressionPwd);

		void setPatternSuppleExplanation(String patternSuppleExplanation);

		void setContractCode(String contractCode);

		void setAnnualReferMonth(Integer annualReferMonth);

		void setDailyReferYear(Integer dailyReferYear);

		void setDailyReferMonth(Integer dailyReferMonth);

		void setMonthlyReferYear(Integer monthlyReferYear);

		void setMonthlyReferMonth(Integer monthlyReferMonth);

		void setIdenSurveyArch(int idenSurveyArch);
	}

	public static interface MementoGetter {
		String getCategoryId();

		int getSystemType();

		int getWithoutPassword();

		String getPatternCode();

		int getPatternClassfication();

		String getPatternName();

		String getPatternCompressionPwd();

		String getPatternSuppleExplanation();

		String getContractCode();

		Integer getAnnualReferMonth();

		Integer getDailyReferYear();

		Integer getDailyReferMonth();

		Integer getMonthlyReferYear();

		Integer getMonthlyReferMonth();

		int getIdenSurveyArch();
	}
}
