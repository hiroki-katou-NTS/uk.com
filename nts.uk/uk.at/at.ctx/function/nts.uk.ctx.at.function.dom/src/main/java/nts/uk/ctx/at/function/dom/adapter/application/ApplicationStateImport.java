package nts.uk.ctx.at.function.dom.adapter.application;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class ApplicationStateImport {

	/**
	 * 申請日
	 */
	private GeneralDate appDate;
	
	/**
	 * 申請種類
	 */
	private Integer appType;
	
	/**
	 * 申請者
	 */
	private String employeeID;
	
	/**
	 * 申請表示名
	 */
	private String appTypeName;
	
	/**
	 * 反映状態
	 */
	private Integer reflectState;

	public ApplicationStateImport(GeneralDate appDate, Integer appType, String employeeID, String appTypeName,
			Integer reflectState) {
		this.appDate = appDate;
		this.appType = appType;
		this.employeeID = employeeID;
		this.appTypeName = appTypeName;
		this.reflectState = reflectState;
	}
}
