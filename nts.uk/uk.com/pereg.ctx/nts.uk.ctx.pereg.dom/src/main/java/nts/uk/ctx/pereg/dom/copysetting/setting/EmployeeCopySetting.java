package nts.uk.ctx.pereg.dom.copysetting.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class EmployeeCopySetting extends AggregateRoot {
	
	// domain-name: 社員コピー設定

	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 社員コピーカテゴリ
	 */
	private List<EmployeeCopyCategory> copyCategoryList;

	public EmployeeCopySetting(String companyId, List<EmployeeCopyCategory> copyCategoryList) {
		super();
		this.companyId = companyId;
		this.copyCategoryList = copyCategoryList;
	}
	
	public List<String> getCopySettingCategoryIdList() {
		return this.copyCategoryList.stream().map(x -> x.getCategoryId()).collect(Collectors.toList());
	}
	
	public List<String> getCopySettingItemIdList() {
		List<String> copySettingItemIdList = new ArrayList<>();
		this.copyCategoryList.forEach(category -> copySettingItemIdList.addAll(category.getItemDefIdList()));
		return copySettingItemIdList;
	}
	
}
