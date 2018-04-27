package nts.uk.ctx.pereg.dom.copysetting.setting;

import java.util.List;

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
	
}
