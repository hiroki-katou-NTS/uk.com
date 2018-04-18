package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.SpecialLeaveGrantNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.SpecialLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.SpecialLeaveUsedNumber;

@Getter
@Setter
@NoArgsConstructor
//特別休暇数情報
public class SpecialLeaveNumberInfo {
	//付与数
	private SpecialLeaveGrantNumber grantNumber;
	//使用数
	private SpecialLeaveUsedNumber usedNumber;
	//残数
	private SpecialLeaveRemainingNumber remainingNumber;
	
	public SpecialLeaveNumberInfo(int dayNumberOfGrant, Integer timeOfGrant,Double dayNumberOfUse, Integer timeOfUse,
			Double useSavingDays, int numberOverDays, Integer timeOver, Double dayNumberOfRemain, Integer timeOfRemain) {
		this.grantNumber = SpecialLeaveGrantNumber.createFromJavaType(dayNumberOfGrant, timeOfGrant);
		this.usedNumber = SpecialLeaveUsedNumber.createFromJavaType(dayNumberOfUse,timeOfUse,useSavingDays,numberOverDays,timeOver);
		this.remainingNumber = SpecialLeaveRemainingNumber.createFromJavaType(dayNumberOfRemain, timeOfRemain);
	}

}
