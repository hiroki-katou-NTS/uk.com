package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDataRemainUnit;

@Getter
public class UpdateSubstitutionOfHDManaDataCommand {

	
	// 振休データID
		private String subOfHDID;
		
		private String cid;
		
		// 社員ID	
		private String sID;
		
		private GeneralDate  exprirationDate;
		// 振休日
		private CompensatoryDayoffDate holidayDate;
		
		// 必要日数
		private ManagementDataDaysAtr requiredDays;	
		
		// 未相殺日数
		private ManagementDataRemainUnit remainDays;
	
}
