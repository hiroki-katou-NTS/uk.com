package nts.uk.ctx.bs.employee.dom.jobtitle.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class JobTitleMain {
	/**
	 * domain : 職務職位 - JobPositionMain
	 */

	private String jobTitleId;
	
	private String sid;
	
	private GenericHistoryItem genericHistoryItem;
	
	public static JobTitleMain creatFromJavaType(String jobTitleId, String sid , String hisid, GeneralDate startDate, GeneralDate endDate) {
		return new JobTitleMain(jobTitleId, hisid, new GenericHistoryItem(hisid, new Period(startDate, endDate)));
		
	}
	
}
