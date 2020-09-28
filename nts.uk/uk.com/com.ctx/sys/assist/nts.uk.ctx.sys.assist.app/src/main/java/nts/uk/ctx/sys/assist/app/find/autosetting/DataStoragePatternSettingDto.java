package nts.uk.ctx.sys.assist.app.find.autosetting;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategory;

@Data
public class DataStoragePatternSettingDto implements DataStoragePatternSetting.MementoSetter {
	
	/**
	 *List<選択カテゴリ名称> 
	 */
	List<SelectionCategoryNameDto> selectCategories;
	
	/**
	 * 調査用保存可否
	 */
	private int idenSurveyArch;
	
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
	 * 圧縮パスワード有無
	 */
	private int withoutPassword;
	
	/**
	 * パターン圧縮パスワード
	 */
	private String patternCompressionPwd;
	
	/**
	 * パターン補足説明
	 */
	private String patternSuppleExplanation;

	@Override
	public void setPatternCode(String patternCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPatternClassification(int patternClassification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPatternName(String patternName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContractCode(String contractCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAnnualReferMonth(Integer annualReferMonth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCategoryId(String categoryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSystemType(int systemType) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void setCategories(List<DataStorageSelectionCategory> categories) {
//		// TODO Auto-generated method stub
//		
//	}
}
