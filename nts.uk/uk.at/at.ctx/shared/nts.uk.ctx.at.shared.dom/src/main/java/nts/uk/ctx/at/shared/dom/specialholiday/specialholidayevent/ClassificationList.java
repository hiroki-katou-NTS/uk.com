package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassificationList {
	
	/* 会社ID */
	String companyId;

	/* 特別休暇枠NO */
	int specialHolidayEventNo;

	/* 雇用コード */
	String classificationCd;
}
