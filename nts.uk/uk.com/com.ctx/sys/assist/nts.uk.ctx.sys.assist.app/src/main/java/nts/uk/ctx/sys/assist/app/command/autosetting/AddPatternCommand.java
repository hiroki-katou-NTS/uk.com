package nts.uk.ctx.sys.assist.app.command.autosetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.sys.assist.app.find.autosetting.CategoryDto;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategory;
import nts.uk.ctx.sys.assist.dom.storage.SurveySettingCategory;

@Data
public class AddPatternCommand implements DataStoragePatternSetting.MementoGetter {

	/**
	 * 画面モード
	 */
	private int screenMode;
	
	/**
	 * 契約コード
	 */
	private String contractCode;
	
	/**
	 * パータンコード
	 */
	private String patternCode;
	
	/**
	 * パターン名称
	 */
	private String patternName;
	
	/**
	 * パターン区分
	 */
	private int patternClassification = 0;
	
	/**
	 * 保存するカテゴリ
	 */
	private List<CategoryDto> categories;
	
	/**
	 * 調査用保存可否
	 */
	private boolean idenSurveyArch;
	
	/**
	 * 日次対象年
	 */
	private Integer dailyReferYear;
	
	/**
	 * 日次対象月
	 */
	private Integer dailyReferMonth;
	
	/**
	 * 月次対象年
	 */
	private Integer monthlyReferYear;
	
	/**
	 * 月次対象月
	 */
	private Integer monthlyReferMonth;
	
	/**
	 * 年次対象年
	 */
	private Integer annualReferYear;
	
	/**
	 * 圧縮パスワード
	 */
	private String patternCompressionPwd;
	
	/**
	 * パスワード有無
	 */
	private int withoutPassword;
	
	/**
	 * 補足説明入力
	 */
	private String patternSuppleExplanation;
	
	@Override
	public List<DataStorageSelectionCategory> getCategories() {
		return categories.stream()
				.map(DataStorageSelectionCategory::createFromMemento)
				.collect(Collectors.toList());
	};

	@Override
	public int getIdenSurveyArch() {
		return idenSurveyArch ? SurveySettingCategory.SAVE_FOR_RESEARCH.value : SurveySettingCategory.DONT_SAVE_FOR_RESEARCH.value;
	}
}
