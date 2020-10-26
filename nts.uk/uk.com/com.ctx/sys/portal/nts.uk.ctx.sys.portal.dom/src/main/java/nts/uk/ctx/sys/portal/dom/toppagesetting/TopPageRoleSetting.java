package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class TopPageRoleSetting.
 * Domain 権限別トップページ設定
 */
@Getter
@AllArgsConstructor
@Builder
public class TopPageRoleSetting extends TopPageSettings implements DomainAggregate {

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
		super(topMenuCode, new MenuLogin(system, menuClassification, loginMenuCode), switchingDate);
		this.companyId = companyId;
		this.roleSetCode = roleSetCode;
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
		this.topMenuCode = new TopMenuCode(memento.getTopMenuCode());
		this.switchingDate = new SwitchingDate(memento.getSwitchingDate());
		this.menuLogin = new MenuLogin(
				EnumAdaptor.valueOf(memento.getSystem(), System.class),
				EnumAdaptor.valueOf(memento.getMenuClassification(), MenuClassification.class),
				new LoginMenuCode(memento.getLoginMenuCode()));
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setCompanyId(AppContexts.user().companyId());
		memento.setRoleSetCode(roleSetCode.v());
		memento.setTopMenuCode(topMenuCode.v());
		memento.setSwitchingDate(switchingDate.v());
		memento.setSystem(menuLogin.getSystem().value);
		memento.setMenuClassification(menuLogin.getMenuClassification().value);
		memento.setLoginMenuCode(menuLogin.getLoginMenuCode().v());
	}
	
	public static interface MementoSetter {
		void setCompanyId(String companyID);
		void setRoleSetCode(String roleSetCode);
		void setTopMenuCode(String topMenuCode);
		void setSwitchingDate(Integer switchingDate);
		void setSystem(int system);
		void setMenuClassification(int menuClassification);
		void setLoginMenuCode(String loginMenuCode);
		
	}
	
	public static interface MementoGetter {
		String getCompanyId();
		String getRoleSetCode();
		String getTopMenuCode();
		Integer getSwitchingDate();
		int getSystem();
		int getMenuClassification();
		String getLoginMenuCode();
	}
}
