package nts.uk.ctx.at.function.dom.alarm;

import java.util.List;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author dxthuong
 * アラームリスト権限設定
 */
@Getter
public class AlarmPermissionSetting {
	
	/**
	 * alarm pattern code
	 */
	private AlarmPatternCode alarmPatternCD;
	/**
	 *  companyID
	 */
	private CompanyId companyID;
	/**
	 * authentication setting
	 */
	private boolean authSetting;
	/**
	 *  list role
	 */
	private List<String> roleIds;
	
	public AlarmPermissionSetting(String alarmPatternCD, String companyID, boolean authSetting,
			List<String> roleIds) {
		super();
		this.alarmPatternCD = new AlarmPatternCode(alarmPatternCD);
		this.companyID = new CompanyId(companyID);
		this.authSetting = authSetting;
		this.roleIds = roleIds;
	}

	public AlarmPermissionSetting(String alarmPatternCD, String companyID, boolean authSetting) {
		super();
		this.alarmPatternCD = new  AlarmPatternCode(alarmPatternCD);
		this.companyID = new CompanyId(companyID);
		this.authSetting = authSetting;
	}
	
	
}
