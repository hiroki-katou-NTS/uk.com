package nts.uk.ctx.at.shared.dom.worktime;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;

/**
 * 所定時間帯設定
 * @author Doan Duy Hung
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class WorkTimeDay {
	
	private String companyID;
	
	private String timeDayCD;
	
	private String workTimeSetCD;
	
	private int timeNumberCnt;
	
	private int a_m_StartTime;
	
	private TimeDayAtr a_m_StartAtr;
	
	private int a_m_EndTime;
	
	private TimeDayAtr a_m_EndAtr;
	
	private UseSetting a_m_UseAtr;
	
	private int p_m_StartTime;
	
	private TimeDayAtr p_m_StartAtr;
	
	private int p_m_EndTime;
	
	private TimeDayAtr p_m_EndAtr;
	
	private UseSetting p_m_UseAtr;

	public WorkTimeDay(String companyID, String timeDayCD, String workTimeSetCD, int timeNumberCnt, int a_m_StartTime,
			TimeDayAtr a_m_StartAtr, int a_m_EndTime, TimeDayAtr a_m_EndAtr, UseSetting a_m_UseAtr, int p_m_StartTime,
			TimeDayAtr p_m_StartAtr, int p_m_EndTime, TimeDayAtr p_m_EndAtr, UseSetting p_m_UseAtr) {
		super();
		this.companyID = companyID;
		this.timeDayCD = timeDayCD;
		this.workTimeSetCD = workTimeSetCD;
		this.timeNumberCnt = timeNumberCnt;
		this.a_m_StartTime = a_m_StartTime;
		this.a_m_StartAtr = a_m_StartAtr;
		this.a_m_EndTime = a_m_EndTime;
		this.a_m_EndAtr = a_m_EndAtr;
		this.a_m_UseAtr = a_m_UseAtr;
		this.p_m_StartTime = p_m_StartTime;
		this.p_m_StartAtr = p_m_StartAtr;
		this.p_m_EndTime = p_m_EndTime;
		this.p_m_EndAtr = p_m_EndAtr;
		this.p_m_UseAtr = p_m_UseAtr;
	}
}
