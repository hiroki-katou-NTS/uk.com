package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;

@Getter
@Setter

//特別休暇数情報
public class SpecialLeaveNumberInfo extends LeaveNumberInfo implements Serializable {

//	//付与数
//	private SpecialLeaveGrantNumber grantNumber;
//	//使用数
//	private SpecialLeaveUsedNumber usedNumber;
//	//残数
//	private SpecialLeaveRemainingNumber remainingNumber;
	
	private static final long serialVersionUID = 1L;

	public SpecialLeaveNumberInfo(
			double grantDays, 
			Integer grantMinutes, 
			double usedDays, 
			Integer usedMinutes,
			Double stowageDays, 
			double remainDays, 
			Integer remainMinutes, 
			double usedPercent) {
		
		super(grantDays, grantMinutes, usedDays, usedMinutes,
			stowageDays, remainDays, remainMinutes,usedPercent);
	}
}
