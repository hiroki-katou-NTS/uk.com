package nts.uk.ctx.pereg.dom.roles.auth;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;

/**
 * @author danpv
 * domain-name: 個人情報ロール権限
 */
@Getter
public class PersonInfoRoleAuth extends AggregateRoot {

	private String roleId;

	private String companyId;
	
	/**
	 * カテゴリ権限
	 */
	private List<PersonInfoCategoryAuth> categoryAuthList;

	/**
	 * @param roleId
	 * @param companyId
	 * @param categoryAuthList
	 */
	public PersonInfoRoleAuth(String roleId, String companyId, List<PersonInfoCategoryAuth> categoryAuthList) {
		super();
		this.roleId = roleId;
		this.companyId = companyId;
		this.categoryAuthList = categoryAuthList;
	}

}
