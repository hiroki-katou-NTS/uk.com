package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class TopPageRoleSetting.
 * Domain 権限別トップページ設定
 */
@Getter
//TODO AggregateRoot OR TopPageSettings??
public class TopPageRoleSetting extends TopPageSettings {

	/** 
	 * The company id. 
	 * 	会社ID
	 * */
	private String companyId;
	
	/** 
	 * The role setcode. 
	 * 	ロールセットコード
	 * */
	private RoleSetCode roleSetCode;
	
	private TopPageRoleSetting() {}
	
	public TopPageRoleSetting(
			String companyId, 
			RoleSetCode roleSetCode, 
			LoginMenuCode loginMenuCode, 
			TopMenuCode topMenuCode, 
			MenuClassification menuClassification, 
			System system, 
			SwitchingDate switchingDate) {
		this.companyId = companyId;
		this.roleSetCode = roleSetCode;
		this.menuLogin = new MenuLogin(system, menuClassification, loginMenuCode);
		this.switchingDate = switchingDate;
		this.topMenuCode = topMenuCode;
	}
	
	public static TopPageRoleSetting createFromJavaType(String companyId, 
			String roleSetCode, 
			String loginMenuCode, 
			String topMenuCode, 
			int menuClassification, 
			int system, 
			Integer switchingDate) {
		return new TopPageRoleSetting(
				companyId, 
				new RoleSetCode(roleSetCode), 
				new LoginMenuCode(loginMenuCode), 
				new TopMenuCode(topMenuCode), 
				EnumAdaptor.valueOf(menuClassification, MenuClassification.class), 
				EnumAdaptor.valueOf(system, System.class), 
				new SwitchingDate(switchingDate));
	}
	
	public static TopPageRoleSetting createFromMemento(MementoGetter memento) {
		TopPageRoleSetting domain = new TopPageRoleSetting();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.companyId = memento.getCompanyId();
		this.roleSetCode = new RoleSetCode(memento.getRoleSetCode());
//		topMenuCode = new TopMenuCode(memento.getTopMenuCode());
//		menuLogin = memento.getMenuLogin();
		switchingDate = new SwitchingDate(memento.getSwitchingDate());
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setCompanyId(AppContexts.user().companyId());
		memento.setRoleSetCode(roleSetCode.v());
//		memento.setTopMenuCode(topMenuCode.v());
//		memento.setMenuLogin(menuLogin);
		memento.setSwitchingDate(switchingDate.v());
	}
	
	public static interface MementoSetter {
		void setCompanyId(String companyID);
		void setRoleSetCode(String roleSetCode);
//		void setTopMenuCode(String topMenuCode);
//		void setMenuLogin(MenuLogin menuLogin);
		void setSwitchingDate(Integer switchingDate);
	}
	
	public static interface MementoGetter {
		String getCompanyId();
		String getRoleSetCode();
//		String getTopMenuCode();
//		MenuLogin getMenuLogin();
		Integer getSwitchingDate();
	}
}
