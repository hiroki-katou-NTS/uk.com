package nts.uk.ctx.at.request.pub.screen;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ApplicationExport {
	private String appID;
	/**
	 * appDate,申請日
	 */
	private GeneralDate appDate;
	/**
	 * appType,申請種類
	 */
	private Integer appType;
	/**
	 * employeeID,申請者
	 */
	private String employeeID;
	/**
	 * appTypeName,申請表示名※
	 */
	private String appTypeName;
	
	/**
	 * 反映状態
	 */
	private Integer reflectState;
	
	/**
	 * 事前事後区分: 0: "事前", 1: "事後"
	 */
	private Integer prePostAtr;
}
