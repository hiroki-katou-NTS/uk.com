package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;

/**
 *  domain 特別休暇基本情報
 * @author Hop.NT
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialLeaveBasicInfo extends AggregateRoot{
	
	private String infoId;
	
	// 社員ID
	private String employeeId;
	
	// 特別休暇コード
	private SpecialLeaveCD specialLeaveCode;

	// 使用区分
	private UseAtr used;
	
	// 適用設定
	private SpecialLeaveAppSetting applicationSet;
	
	// 付与設定
	private SpecialLeaveGrantSetting grantSetting;
	
	public SpecialLeaveBasicInfo(String infoId, String sid, String spLeaCD, int used, int appSet, GeneralDate grantDate,
			Integer grantDay, String grantTbl) {
		this.infoId = infoId;
		this.employeeId = sid;
		this.specialLeaveCode = new SpecialLeaveCD(spLeaCD);
		this.used = EnumAdaptor.valueOf(used, UseAtr.class);
		this.applicationSet = EnumAdaptor.valueOf(appSet, SpecialLeaveAppSetting.class);
		this.grantSetting = new SpecialLeaveGrantSetting(grantDate, grantDay, grantTbl);
	}
	
}
