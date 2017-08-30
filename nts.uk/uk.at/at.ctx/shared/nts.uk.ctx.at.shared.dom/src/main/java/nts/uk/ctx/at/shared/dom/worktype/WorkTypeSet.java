package nts.uk.ctx.at.shared.dom.worktype;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Getter
public class WorkTypeSet {

	private String companyId;

	private WorkTypeCode workTypeCd;

	private WorkAtr workAtr;

	private WorkTypeSetCheck digestPublicHd;

	private HolidayAtr holidayAtr;

	private WorkTypeSetCheck countHodiday;

	private CloseAtr closeAtr;

	private int sumAbsenseNo ;

	private int sumSpHodidayNo;

	private WorkTypeSetCheck timeLeaveWork;

	private WorkTypeSetCheck attendanceTime;

	private WorkTypeSetCheck genSubHodiday;

	private WorkTypeSetCheck dayNightTimeAsk;

	public WorkTypeSet(String companyId, WorkTypeCode workTypeCd, WorkAtr workAtr, WorkTypeSetCheck digestPublicHd,
			HolidayAtr holidayAtr, WorkTypeSetCheck countHodiday, CloseAtr closeAtr, int sumAbsenseNo,
			int sumSpHodidayNo, WorkTypeSetCheck timeLeaveWork, WorkTypeSetCheck attendanceTime,
			WorkTypeSetCheck genSubHodiday, WorkTypeSetCheck dayNightTimeAsk) {
		super();
		this.companyId = companyId;
		this.workTypeCd = workTypeCd;
		this.workAtr = workAtr;
		this.digestPublicHd = digestPublicHd;
		this.holidayAtr = holidayAtr;
		this.countHodiday = countHodiday;
		this.closeAtr = closeAtr;
		this.sumAbsenseNo = sumAbsenseNo;
		this.sumSpHodidayNo = sumSpHodidayNo;
		this.timeLeaveWork = timeLeaveWork;
		this.attendanceTime = attendanceTime;
		this.genSubHodiday = genSubHodiday;
		this.dayNightTimeAsk = dayNightTimeAsk;
	}

	public static WorkTypeSet createSimpleFromJavaType(String companyId, String workTypeCd, int workAtr, int digestPublicHd,
			int holidayAtr, int countHodiday, int closeAtr, int sumAbsenseNo,
			int sumSpHodidayNo, int timeLeaveWork, int attendanceTime,
			int genSubHodiday, int dayNightTimeAsk) {
		return new WorkTypeSet(companyId, 
				new WorkTypeCode(workTypeCd), 
				EnumAdaptor.valueOf(workAtr, WorkAtr.class), 
				EnumAdaptor.valueOf(digestPublicHd, WorkTypeSetCheck.class), 
				EnumAdaptor.valueOf(holidayAtr, HolidayAtr.class), 
				EnumAdaptor.valueOf(countHodiday, WorkTypeSetCheck.class), 
				EnumAdaptor.valueOf(closeAtr, CloseAtr.class), 
				sumAbsenseNo, 
				sumSpHodidayNo, 
				EnumAdaptor.valueOf(timeLeaveWork, WorkTypeSetCheck.class), 
				EnumAdaptor.valueOf(attendanceTime, WorkTypeSetCheck.class), 
				EnumAdaptor.valueOf(genSubHodiday, WorkTypeSetCheck.class), 
				EnumAdaptor.valueOf(dayNightTimeAsk, WorkTypeSetCheck.class));
	}

}
