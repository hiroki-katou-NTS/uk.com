package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;

/**
 *  domain 特別休暇基本情報
 * @author Hop.NT
 *
 */
@Getter
public class SpecialLeaveBasicInfo {
	
	// 社員ID
	private String employeeId;
	
	// 特別休暇コード
	private SpecialLeaveCode specialLeaveCode;

	// 使用区分
	private UseAtr used;
	
	// 適用設定
	private SpecialLeaveAppSetting appSetting;
	
	// 付与設定
	private SpecialLeaveGrantSetting grantSetting;
	
}
