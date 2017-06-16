package nts.uk.ctx.at.shared.dom.worktimeset;

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
	
	private String timeDayID;
	
	private String workTimeSetID;
	
	private int timeNumberCnt;
	
	private UseSetting use_atr;
	
	private int a_m_StartCLock;
	
	private TimeDayAtr a_m_StartAtr;
	
	private int a_m_EndClock;
	
	private TimeDayAtr a_m_EndAtr;
	
	private int p_m_StartClock;
	
	private TimeDayAtr p_m_StartAtr;
	
	private int p_m_EndClock;
	
	private TimeDayAtr p_m_EndAtr;

	public WorkTimeDay(String companyID, String timeDayID, String workTimeSetID, int timeNumberCnt, UseSetting use_atr,
			int a_m_StartCLock, TimeDayAtr a_m_StartAtr, int a_m_EndClock, TimeDayAtr a_m_EndAtr, int p_m_StartClock,
			TimeDayAtr p_m_StartAtr, int p_m_EndClock, TimeDayAtr p_m_EndAtr) {
		super();
		this.companyID = companyID;
		this.timeDayID = timeDayID;
		this.workTimeSetID = workTimeSetID;
		this.timeNumberCnt = timeNumberCnt;
		this.use_atr = use_atr;
		this.a_m_StartCLock = a_m_StartCLock;
		this.a_m_StartAtr = a_m_StartAtr;
		this.a_m_EndClock = a_m_EndClock;
		this.a_m_EndAtr = a_m_EndAtr;
		this.p_m_StartClock = p_m_StartClock;
		this.p_m_StartAtr = p_m_StartAtr;
		this.p_m_EndClock = p_m_EndClock;
		this.p_m_EndAtr = p_m_EndAtr;
	}
}
