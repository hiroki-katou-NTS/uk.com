package nts.uk.ctx.at.shared.app.command.worktype;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;

@AllArgsConstructor
@Data
public class WorkTypeSetBase {
	
	private String workTypeCode;
	private int workAtr;
	private int digestPublicHd;
	private int holidayAtr;
	private int countHodiday;
	private Integer closeAtr;
	private int sumAbsenseNo ;
	private Integer sumSpHodidayNo;
	private int timeLeaveWork;
	private int attendanceTime;
	private int genSubHodiday;
	private int dayNightTimeAsk;
	
	
	public WorkTypeSet toDomainWorkTypeSet(String companyId) {
		WorkTypeSet workTypeSet = WorkTypeSet.createSimpleFromJavaType(
				companyId, 
				workTypeCode, 
				workAtr, 
				digestPublicHd, 
				holidayAtr, 
				countHodiday, 
				closeAtr, 
				sumAbsenseNo, 
				sumSpHodidayNo, 
				timeLeaveWork, 
				attendanceTime, 
				genSubHodiday, 
				dayNightTimeAsk);
		return workTypeSet;
	}

}
