package nts.uk.ctx.sys.auth.app.find.roleset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenu;

@Data
public class RoleSetDto {

	/** ロールセットコード. */
	private String roleSetCd;

	/** 会社ID */
	private String companyId;

	/** ロールセット名称*/
	private String roleSetName;

	/** 承認権限*/
	private boolean approvalAuthority;

	/** ロールID: オフィスヘルパーロール */
	private String officeHelperRoleId;

	/** ロールID: マイナンバーロール */
	private String myNumberRoleId;

	/** ロールID: 人事ロール */
	private String hRRoleId;

	/** ロールID: 個人情報ロール */
	private String personInfRoleId;

	/** ロールID: 就業ロール */
	private String employmentRoleId;

	/** ロールID: 給与ロール */
	private String salaryRoleId;
	
	/** list of web menu code */
	private List<WebMenuDto> webMenus;
	
	/**
	 * Transfer data from Domain into Dto to response to client
	 * @param roleSet
	 * @return
	 */
	public static RoleSetDto build(RoleSet roleSet) {
		RoleSetDto result = new RoleSetDto();
		result.setApprovalAuthority(roleSet.hasApprovalAuthority());
		result.setCompanyId(roleSet.getCompanyId());
		result.setEmploymentRoleId(RoleSet.getRoleId(roleSet.getEmploymentRole()));
		result.setHRRoleId(RoleSet.getRoleId(roleSet.getHRRole()));
		result.setMyNumberRoleId(RoleSet.getRoleId(roleSet.getMyNumberRole()));
		result.setOfficeHelperRoleId(RoleSet.getRoleId(roleSet.getOfficeHelperRole()));
		result.setPersonInfRoleId(RoleSet.getRoleId(roleSet.getPersonInfRole()));
		result.setRoleSetCd(roleSet.getRoleSetCd().v());
		result.setRoleSetName(roleSet.getRoleSetName().v());
		result.setSalaryRoleId(RoleSet.getRoleId(roleSet.getSalaryRole()));
		result.setWebMenus(convertWebMenuToWebMunuCd(roleSet.getRoleSetAndWebMenus()));
		return result;
	}
	
	/**
	 * Convert from list of RoleSet and WebMenu.
	 * @param lstWebMenu
	 * @return list of web menu code.
	 */
	private static List<WebMenuDto> convertWebMenuToWebMunuCd(List<RoleSetAndWebMenu> lstWebMenu) {
		if (CollectionUtil.isEmpty(lstWebMenu)) {
			return null;
		}
		//TODO web menu name???
		return lstWebMenu.stream().map(item -> new WebMenuDto(item.getWebMenuCd(), "")).collect(Collectors.toList());
	}
}
