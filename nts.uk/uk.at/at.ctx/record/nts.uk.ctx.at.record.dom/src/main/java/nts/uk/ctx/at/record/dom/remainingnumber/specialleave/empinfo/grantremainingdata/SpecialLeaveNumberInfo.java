package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.SpecialLeaveGrantNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.SpecialLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.SpecialLeaveUsedNumber;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//特別休暇数情報
public class SpecialLeaveNumberInfo {
	//付与数
	private SpecialLeaveGrantNumber grantNumber;
	
	private SpecialLeaveUsedNumber usedNumber;
	
	private SpecialLeaveRemainingNumber remainingNumber;

}
