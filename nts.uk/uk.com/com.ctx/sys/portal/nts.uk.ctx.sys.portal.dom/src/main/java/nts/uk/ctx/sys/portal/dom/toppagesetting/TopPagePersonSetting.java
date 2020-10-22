package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;

/**
 * The Class TopPagePersonSetting.
 * Domain 個人別トップページ設定
 */
@Getter
//TODO AggregateRoot OR TopPageSettings??
public class TopPagePersonSetting extends TopPageSettings {

	/**
	 * 社員ID
	 */
	private String employeeId;
	
	public TopPagePersonSetting(
			String employeeId, 
			LoginMenuCode loginMenuCode,
			TopMenuCode topMenuCode,
			MenuClassification menuClassification, 
			System system,
			SwitchingDate switchingDate) {
		this.employeeId = employeeId;
		this.topMenuCode = topMenuCode;
		this.menuLogin = new MenuLogin(system, menuClassification, loginMenuCode);
		this.switchingDate = switchingDate;
	}
	
	private TopPagePersonSetting() {
	}

	public static TopPagePersonSetting createFromJavaType(String employeeId, 
			String loginMenuCode, 
			String topMenuCode, 
			int menuClassification, 
			int system,
			Integer switchingDate) {
		return new TopPagePersonSetting(
				employeeId, 
				new LoginMenuCode(loginMenuCode),
				new TopMenuCode(topMenuCode),
				EnumAdaptor.valueOf(menuClassification, MenuClassification.class), 
				EnumAdaptor.valueOf(system, System.class),
				new SwitchingDate(switchingDate));
	}
	
	public static TopPagePersonSetting createFromMemento(MementoGetter memento) {
		TopPagePersonSetting domain = new TopPagePersonSetting();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.employeeId = memento.getEmployeeId();
		this.switchingDate = new SwitchingDate(memento.getSwitchingDate());
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setEmployeeId(employeeId);
		memento.setSwitchingDate(switchingDate.v());
	}
	
	public static interface MementoSetter {
		void setEmployeeId(String EmployeeId);
		void setSwitchingDate(Integer switchingDate);
	}
	
	public static interface MementoGetter {
		String getEmployeeId();
		Integer getSwitchingDate();
	}
}
