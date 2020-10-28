package nts.uk.ctx.sys.assist.app.command.autosetting.deletion;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.DeleteCategoryDto;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSetting;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionSelectionCategory;

@Data
public class AddDelPatternCommand implements DataDeletionPatternSetting.MementoGetter {

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
	private List<DeleteCategoryDto> categoriesMaster;
	
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
	public Integer getDailyReferMonth() {
		return dailyReferMonth != 0 ? dailyReferMonth : null;
	}
	@Override
	public Integer getDailyReferYear() {
		return dailyReferYear != 0 ? dailyReferYear : null;
	}
	@Override
	public Integer getMonthlyReferMonth() {
		return monthlyReferMonth != 0 ? monthlyReferMonth : null;
	}
	@Override
	public Integer getMonthlyReferYear() {
		return monthlyReferYear != 0 ? monthlyReferYear : null;
	}
	@Override
	public Integer getAnnualReferYear() {
		return annualReferYear != 0 ? annualReferYear : null;
	}
	@Override
	public List<DataDeletionSelectionCategory> getCategories() {
		return categoriesMaster.stream().map(DataDeletionSelectionCategory::createFromMemento).collect(Collectors.toList());
	}
}

