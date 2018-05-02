package nts.uk.ctx.pereg.dom.copysetting.setting;

import java.util.List;

import lombok.Getter;

@Getter
public class EmployeeCopyCategory {
	
	/**
	 * 個人情報カテゴリID
	 */
	private String categoryId;
	
	/**
	 * 個人情報項目定義ID
	 */
	private List<String> itemDefIdList;

	public EmployeeCopyCategory(String categoryId, List<String> itemDefIdList) {
		super();
		this.categoryId = categoryId;
		this.itemDefIdList = itemDefIdList;
	}

}
