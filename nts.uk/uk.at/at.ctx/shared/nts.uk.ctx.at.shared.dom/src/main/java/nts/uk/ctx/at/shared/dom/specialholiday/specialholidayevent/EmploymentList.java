package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmploymentList {

	/* 会社ID */
	String companyId;
	
	/* 特別休暇枠NO */
	int specialHolidayEventNo;
	
	/* 雇用コード*/
	EmploymentCode employmentCd;

}
