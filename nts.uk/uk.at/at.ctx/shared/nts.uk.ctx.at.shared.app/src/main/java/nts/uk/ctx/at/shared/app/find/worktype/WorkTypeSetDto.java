package nts.uk.ctx.at.shared.app.find.worktype;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;

@Value
@AllArgsConstructor
public class WorkTypeSetDto {

	private String companyId;

	private String workTypeCd;

	private int workAtr;

	private int digestPublicHd;

	private int holidayAtr;

	private int countHodiday;

	private Integer closeAtr;

	private int sumAbsenseNo;

	private Integer sumSpHodidayNo;

	private int timeLeaveWork;

	private int attendanceTime;

	private int genSubHodiday;

	private int dayNightTimeAsk;
	

	public static WorkTypeSetDto fromDomain(WorkTypeSet domain) {
        return new WorkTypeSetDto(
        		domain.getCompanyId(), 
        		domain.getWorkTypeCd().v(), 
        		domain.getWorkAtr().value, 
        		domain.getDigestPublicHd().value, 
        		domain.getHolidayAtr().value, 
        		domain.getCountHodiday().value, 
        		domain.getCloseAtr() !=null ? domain.getCloseAtr().value : null, 
        		domain.getSumAbsenseNo(), 
        		domain.getSumSpHodidayNo(), 
        		domain.getTimeLeaveWork().value, 
        		domain.getAttendanceTime().value, 
        		domain.getGenSubHodiday().value, 
        	 	domain.getDayNightTimeAsk().value);
	}

}
